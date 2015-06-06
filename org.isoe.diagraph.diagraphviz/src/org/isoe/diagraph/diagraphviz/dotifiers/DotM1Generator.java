 /**
 * Copyright (c) 2014 Laboratoire de Genie Informatique et Ingenierie de Production - Ecole des Mines d'Ales
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Francois Pfister (ISOE-LGI2P) - initial API and implementation
 */
package org.isoe.diagraph.diagraphviz.dotifiers;

import java.io.FileWriter;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.isoe.diagraph.diagraph.DCompartmentEdge;
import org.isoe.diagraph.diagraph.DEdge;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.diagraph.DGraphElement;
import org.isoe.diagraph.diagraph.DNestedEdge;
import org.isoe.diagraph.diagraph.DLabel;
import org.isoe.diagraph.diagraph.DLabeledEdge;
import org.isoe.diagraph.diagraph.DNode;
import org.isoe.diagraph.diagraph.DReference;
import org.isoe.diagraph.diagraph.DLabeledElement;
import org.isoe.diagraph.diagraphviz.dotifiers.EObjectEdge.AssociationType;
import org.isoe.diagraph.diagraphviz.dotutils.DotConstants;
import org.isoe.diagraph.diagraphviz.dotutils.DotWriter;
import org.isoe.diagraph.diagraphviz.dotutils.GraphVizConverter;
import org.isoe.diagraph.diagraphviz.dotutils.GraphwizInvoker;

/**
 *
 * @author pfister
 *
 */
public class DotM1Generator extends DotWriter implements DotVisitable,
		DotConstants {

	// private static final boolean TOP_NODES = true; // topnodes on canvas

	private static final boolean DRAW_REF_EDGES = true;
	private static final boolean DRAW_LINK_EDGES_ = true;

	private static final boolean SUBGRAPH_FRAME = false;
	private static final String EDGE_FILTER_ = null;// "Humain";//"LeMonde";

	// private static final boolean DRAW_CONTAINMENT_EDGES = true;
	// private static final boolean DRAW_TOP_EDGES = true;

	// TODO: separate visitor in ModelToGraph and visitable in DotGenerator


	private StringBuffer nodes = new StringBuffer();
	private StringBuffer rawNodes = new StringBuffer();

	private DGraph dgraph;
	private GraphVizConverter diagraphVisitor;
	private String outputFile;
	private String asDot;

	private Map<EObject, String> oidmap = new HashMap<EObject, String>();
	private Map<DNode, String> colormap = new HashMap<DNode, String>();

	private List<EObjectEdge> edges = new ArrayList<EObjectEdge>();


	private EObject rootNode;
	private List<EObject> topnodes = new ArrayList<EObject>();
	private List<EObject> childNodes = new ArrayList<EObject>();
	private boolean rawGraph;

	private void addEdge(EObject seObject, EObject teObject, String label,
			AssociationType association) {
		String[] edg = getEdgeOid(seObject, teObject, association);
		EObjectEdge eObjectEdge = new EObjectEdge(null, dgraph,seObject, teObject,
				edg[0], edg[1], label, association,true,false);
		edges.add(eObjectEdge);
	}

	private void addCompositeNodes(EObject eContainerObject, EObject eObject,
			int depth, boolean isTopNode) {
		depth++;
		DNode node_ = dgraph.getFacade1().getNode(eObject);
		if (LOG)
			log2(eContainerObject, eObject, isTopNode);
		if (depth == 1)
			addEdge(rootNode, eObject, getNodeLabel(eObject),
					AssociationType.Root);
		addStartNode(eObject, depth);
		addStartSugar(getColor(eObject));
		if (node_==null){ //FP130401yyy
			System.out.println("**************  !!!! error in addCompositeNodes "+eContainerObject.toString()+" -> "+eObject.toString());
			addEndSugar(depth);
			depth--;
			return;
		}
		addContent(eObject, node_.getName());
		addChildrenNodes(eObject, getNestingEdges(node_), depth);
		if (!isTopNode && eContainerObject != null) {
			if (!filtered(eContainerObject))
				addEdge(eContainerObject, eObject, getNodeLabel(eObject),
						AssociationType.Containment);
		}
		addEndSugar(depth);
		depth--;
	}

	private void adDLabeledEdgeEdge(EObject eObject, DLabeledEdge link) {
		Object src = eObject.eGet(link.getSourceReference());
		if (src instanceof EList)
			throw new RuntimeException("should not happen in adDLabeledEdgeEdge");
		EObject eSrc = (EObject) src;
		Object targ = eObject.eGet(link.getTargetReference());
		if (targ instanceof EList)
			throw new RuntimeException("should not happen in adDLabeledEdgeEdge");
		EObject eTarget = (EObject) targ;
		register(eObject);
		register(eSrc);
		register(eTarget);
		if (!filtered(eSrc))
			addEdge(eSrc, eTarget, getLinkLabel(eObject), AssociationType.Link);
	}

	private boolean filtered(EObject eObject) {
		return (!(EDGE_FILTER_ == null || (EDGE_FILTER_ != null && eObject
				.eClass().getName().equals(EDGE_FILTER_))));
	}

	private void addReferenceEdge(EObject eSourceObject, EObject eTargetObject) {
		if (eSourceObject != null && eTargetObject != null) {
			register(eSourceObject);
			register(eTargetObject);
			if (!filtered(eSourceObject)) {
				addEdge(eSourceObject, eTargetObject, null,
						AssociationType.Reference);
			}
		}
	}

	@Override
	public String getNodesAsString(int i) {
		return null;
	}

	@Override
	public String getEdgesAsString(int i) {
		return null;
	}

	@Override
	public Map<EObject, String> getEdgeEnds(int i) {
		return oidmap;
	}

	@Override
	public List<EObjectEdge> getEdges(int section) {
		return edges;
	}

	@Override
	public List<EObject> getNodes(int section) {
		List<EObject> result = new ArrayList<EObject>();
		result.addAll(oidmap.keySet());
		return result;
	}

	private String getColor(EObject eObject) {// getDNode(eObject)
		return colormap.get(dgraph.getFacade1().getNode(eObject));
	}

	void iterate(EObject eObject) { // snippet
		EObject container = eObject.eContainer();
		EList<EReference> refs = container.eClass().getEReferences();
		for (EReference er : refs) {
			if (container.eGet(er) != null) {
				Object featuredObject = container.eGet(er);
				if (LOG){
				 if (featuredObject instanceof EList) {
					EList<EObject> kl = (EList<EObject>) featuredObject;
					for (EObject ek : kl) {
						System.out.println(ek);
						if (ek == eObject)
							;
					}
				} else {
					EObject ek = (EObject) featuredObject;
					System.out.println(ek);
					if (ek == eObject)
						;
				}
				}
			}
		}
	}

	/************************* logs **************************************/

	private void logEdge(EObject eContainerObject, EObject eObject, int level) {
		String containerlabel = "null";
		String label = "null";
		label = getNodeLabel(eObject);
		if (eContainerObject != null)
			containerlabel = getNodeLabel(eContainerObject);

		String srcnode = containerlabel
				+ ":"
				+ (eContainerObject == null ? "notype" : eContainerObject
						.eClass().getName());
		String trgnode = label + ":" + eObject.eClass().getName();
		System.out.println("EDGE= " + srcnode + " --> " + trgnode + " (L="
				+ level + ")"
				+ (level == 1 ? " root" : (level == 2 ? " top" : " child")));

		// String srclabel = getNodeLabel(eContainerObject);

	}

	private void log2(EObject eContainerObj, EObject eObj, boolean istop) {
		String containerlabel = "null";
		String label = "null";
		label = getNodeLabel(eObj);
		if (eContainerObj != null)
			containerlabel = getNodeLabel(eContainerObj);
		System.out.println();
		System.out.println(containerlabel
				+ ":"
				+ (eContainerObj == null ? "notype" : eContainerObj.eClass()
						.getName()));
		System.out.println(label + ":" + eObj.eClass().getName());
		int subOid = 0;
		String nodeid = oidmap.get(eObj);
		String zOid = nodeid + SOURCE_OID_SUFFIX;
		String oid = nodeid + getOidSuffix(++subOid);
		String lab2 = "Type" + ":" + "obj";
		if (SECOND_OBJ_) {
			subOid++;
			// addLine();
			// addContent(nodeid + getOidSuffix(subOid), lab2);
		}
		String srclabel = getNodeLabel(eContainerObj);
	}

	private void logReferenceEdges(EObject eParentObj, EObject eObj) {
		String parentLabel = "null";
		String label = getNodeLabel(eObj);
		System.out.println(label);
		if (eParentObj != null)
			parentLabel = getNodeLabel(eParentObj);
		System.out.println();
		String qParentLabel = parentLabel
				+ ":"
				+ (eParentObj == null ? "notype" : eParentObj.eClass()
						.getName());
		System.out.println(qParentLabel);

		String qlabel = label + ":" + eObj.eClass().getName();
		System.out.println(qlabel);
	}

	private void log4(EObject eParentObj, EObject eObj) {
		String parentLabel = "null";
		String label = getNodeLabel(eObj);
		System.out.println(label);
		if (eParentObj != null)
			parentLabel = getNodeLabel(eParentObj);
		System.out.println();
		String qParentLabel = parentLabel
				+ ":"
				+ (eParentObj == null ? "notype" : eParentObj.eClass()
						.getName());
		System.out.println(qParentLabel);

		String qlabel = label + ":" + eObj.eClass().getName();
		System.out.println(qlabel);
	}

	/************************** end logs *******************************/

	private void setColor(EObject eObject) {
		DNode n = dgraph.getFacade1().getNode(eObject);
		if (colormap.get(n) == null)
			colormap.put(n, COLORS[nodeId++]);
	}

	private String getOidSuffix(int id) {
		return PORT_PREFIX + Integer.toString(id);
	}

	// static final String
	// pattern="[^\\d\\p{L}!#$€%&'`(),;:/@... '-_’\\.,;\\\\/]";

	/**
	 * useful for debugging
	 *
	 * @param s
	 * @return
	 */
	public static String semanticId(String s) {
		if (s == null)
			s = "emptyx";
		s = s.substring(0, Math.min(10, s.length()));
		String result = PATTERN.matcher(
				Normalizer.normalize(s, Normalizer.Form.NFD)).replaceAll("");

		result = result.replaceAll(" ", "x");
		result = result.replaceAll("'", "x");
		result = result.replaceAll("-", "x");
		result = result.replaceAll("_", "x");
		result = result.replaceAll("’", "x");
		result = result.replaceAll("\\.", "x");
		result = result.replaceAll(",", "x");
		result = result.replaceAll(";", "x");
		result = result.replaceAll("\\\\", "x");
		result = result.replaceAll("/", "x");
		// TODO improve

		// result = result.replaceAll(pattern, "x");

		return "id" + result.toLowerCase(); // be shure to not start with a
											// number, to avoid a
		// 587 error
	}

	private void toDotFile(String output) throws IOException {
		FileWriter fw = new FileWriter(output);
		fw.append(asDot);
		fw.close();
	}

	@Override
	public CompiledGraph createDotGraph() {
		try {
			asDot = toDot();
			toDotFile(outputFile);
			GraphwizInvoker.toDot(outputFile, "jpg");
		} catch (Exception e) {
			throw new RuntimeException("dotify error for " + outputFile + " "
					+ e);
		}
		return null;
	}

	@Override
	public void setRoot(EObject root) {
		this.dgraph = (DGraph) root;
		if (LOG)
			System.out.println("root="
					+ (root == null ? "null" : root.toString()));

	}

	/***************************************************************/

	public DotM1Generator(String outputFile, boolean rawGraph) {
		super();
		// this.iDiagraphInstanceView = iDiagraphInstanceView;
		this.outputFile = outputFile;
		this.rawGraph = rawGraph;
		if (LOG)
			System.out.println("DotM1Generator ->" + outputFile
					+ (rawGraph ? " r" : " c"));

	}

	private String getIndentation(int i) {
		if (i > 0) {
			tableDepth += i;
			return SPACES.substring(0, tableDepth * 3);
		} else if (i < 0) {
			String result = SPACES.substring(0, tableDepth * 3);
			tableDepth -= i;
			return result;
		} else
			return SPACES.substring(0, tableDepth * 3);
	}

	@Override
	public void setVisitor(GraphVizConverter diagraphVisitor) {
		this.diagraphVisitor = diagraphVisitor;

	}

	private String startTableInRow() {
		tableDepth++;
		String spaces = SPACES.substring(0, tableDepth * 3);
		return spaces + "<TR><TD><TABLE  border=\"0\" cellspacing=\"-1\" >\n";
	}

	private void addEndSugar(int depth) {
		nodes.append(endTableInRow());
		nodes.append(endTable());
		nodes.append(depth == 1 ? endTopNode() : endChildNode());
	}

	private void addStartSugar(String color) {
		nodes.append(startTable(color));
		nodes.append(startTableInRow());
	}

	private void addLine() {
		nodes.append(newLine());
	}



	@SuppressWarnings("unchecked")
	private void addCompositeNodes(EObject model) {
		DNode nod = dgraph.getFacade1().getNode(model);
		if (nod==null){
			System.out.println("error in addCompositeNodes "+model.toString());
			return;
		}
		List<DEdge> edges = dgraph.getFacade1().getEdges(nod);
		for (DEdge dEdge : edges) {
			EReference topcref = dEdge.getTargetReference();
			Object featuredObject = model.eGet(topcref);
			if (featuredObject instanceof EList) {
				EList<EObject> topobjects = (EList<EObject>) featuredObject;
				for (EObject eObject : topobjects) {
					addCompositeNodes(model, eObject, 0, true);
				}
			} else {
				EObject eObject = (EObject) featuredObject;
				addCompositeNodes(model, eObject, 0, true);
			}
		}
	}

	private void addReferences() {
		for (EObject eObject : childNodes)
			addReferenceEdges(eObject);
	}

	private void adDLabeledEdges() {
		for (EObject eObject : childNodes)
			adDLabeledEdgeEdges(eObject);
	}

	private boolean existClusters(List<DNestedEdge> nedges) {
		for (DNestedEdge DNestedEdge : nedges)
			if (DNestedEdge instanceof DCompartmentEdge)
				return true;
		return false;
	}

	private List<DNode> getClusters(List<DNestedEdge> nedges) {
		List<DNode> result = new ArrayList<DNode>();
		for (DNestedEdge DNestedEdge : nedges)
			if (DNestedEdge instanceof DCompartmentEdge) {
				DNode t = DNestedEdge.getTarget();
				if (!result.contains(t))
					result.add(t);
			}
		return result;

	}

	private List<DNestedEdge> getFilteredEdges(List<DNestedEdge> nedges,
			DNode filter) {
		List<DNestedEdge> result = new ArrayList<DNestedEdge>();
		for (DNestedEdge DNestedEdge : nedges)
			if (DNestedEdge instanceof DCompartmentEdge) {
				DNode t = DNestedEdge.getTarget();
				if (t == filter)
					result.add(DNestedEdge);
			}
		return result;
	}

	private List<DNestedEdge> getNestingEdges(DNode node) {
		List<DNestedEdge> result = new ArrayList<DNestedEdge>();
		result = new ArrayList<DNestedEdge>();
		boolean hasCompartmentingEdges = false;
		for (DEdge dEdge : dgraph.getFacade1().getEdges(node)) {
			if (dEdge instanceof DCompartmentEdge) {
				result.add((DNestedEdge) dEdge);
				hasCompartmentingEdges = true;
			} else if (dEdge instanceof DNestedEdge) {
				if (hasCompartmentingEdges)
					throw new RuntimeException(
							"CompartmentingEdge may not be mixed wih simple NestingEdges");
				result.add((DNestedEdge) dEdge);
			}
		}
		return result;
	}



	/*

	private List<DEdge> getEdges(DGraph graph) { // FP140518
		List<DEdge> result = new ArrayList<DEdge>();
		for (DGraphElement element : graph.getElements())
			if (element instanceof DEdge)
				result.add((DEdge) element);
		return result;
	}

	protected List<DEdge> getEdges(DNode nod) {
		List<DEdge> result = new ArrayList<DEdge>();
		List<DEdge> all = getEdges(nod.getGraph());
		for (DEdge dEdge : all)
			if (dEdge.getSource() == nod)
				result.add(dEdge);
		return result;
	}*/
	private String startCompartment(String title) {
		tableDepth++;
		String spaces = SPACES.substring(0, tableDepth * 3);
		String result = "\n" + spaces + "<!-- start compartment -->" + "\n"
				+ spaces + "<TR><TD><!--k=" + ++clusterid + "-->" + "\n"
				+ spaces + "<TABLE   bgcolor=\"yellow\"  border=\"0\">"
				+ "<TR><TD>" + title + "</TD></TR>\n";
		return result;
	}

	private String addEmptyRow() {
		// String spaces= SPACES.substring(0,tableDepth*3);
		String spaces = SPACES.substring(0, tableDepth * 3);
		return "\n" + spaces + "<TR><TD></TD></TR>";
	}

	private String endCompartment() {
		String spaces = SPACES.substring(0, tableDepth * 3);
		String result = "\n" + spaces + "</TABLE></TD></TR>" + "\n" + spaces
				+ "<!-- end compartment -->";
		tableDepth--;
		return result;
	}

	private boolean isEmpty(Object featuredObject) {
		return ((featuredObject == null) || (featuredObject instanceof EList && ((EList) featuredObject)
				.isEmpty()));
	}

	private void addChildrenNodes(EObject eobj, List<DNestedEdge> nedges,
			int depth) {
		if (existClusters(nedges))
			for (DNode clustnode : getClusters(nedges))
				addMixedOrClusteredChildrenNodes(clustnode, eobj,
						getFilteredEdges(nedges, clustnode), depth);
		else
			addMixedOrClusteredChildrenNodes(null, eobj, nedges, depth);
	}

	private void registerRoot(EObject model) {
		this.rootNode = model;
		DNode modelnode = dgraph.getFacade1().getNode(model);
		if (LOG)
			System.out.println("####   registerRoot: modelnode="
					+ modelnode.getName());

		oidmap.put(model, SEMANTIC_IDS ? getNodeSemanticObjectId(model)
				: getObjectId(model));
		registerNodes(null, model, true, 0);
	}

	private void registerContainedNodes(EObject eObject,
			List<DNestedEdge> nedges, boolean istop, int level) {
		String sp = SPACES.substring(0, level * 3);
		oidmap.put(eObject, SEMANTIC_IDS ? getNodeSemanticObjectId(eObject)
				: getObjectId(eObject));// getLinkObjectId(eObject));
		childNodes.add(eObject);
		if (LOG)
			System.out.println(sp + "registerContainedNodes - child: "
					+ eObject.toString());
		setColor(eObject);

		for (DNestedEdge dEdge : nedges) {
			if (dEdge instanceof DNestedEdge) {
				Object featuredObject = eObject
						.eGet(dEdge.getTargetReference());
				if (!isEmpty(featuredObject)) {
					if (featuredObject instanceof EList)
						for (EObject contained : (EList<EObject>) featuredObject)
							registerNodes(eObject, contained, false, level);
					else
						registerNodes(eObject, (EObject) featuredObject,
								false, level);
				}
			}
		}
	}

	/*
	 * repeated for each feature
	 */
	private void addMixedOrClusteredChildrenNodes(DNode groupingNode,
			EObject eobj, List<DNestedEdge> nedges, int depth) {
		if (groupingNode != null) // there is a cluster, so start a compartment
			nodes.append(startCompartment(groupingNode.getName()));
		for (DNestedEdge dEdge : nedges) {
			if (dEdge instanceof DNestedEdge) {
				Object featuredObject = eobj.eGet(dEdge.getTargetReference());
				if (isEmpty(featuredObject)) {
					if (groupingNode != null)
						nodes.append(addEmptyRow());
				} else if (featuredObject instanceof EList)
					for (EObject eo : (EList<EObject>) featuredObject)
						addCompositeNodes(eobj, eo, depth, false);
				else
					addCompositeNodes(eobj, (EObject) featuredObject, depth,
							false);
			}
		}
		if (groupingNode != null) // there is a cluster, so close the
									// compartment
			nodes.append(endCompartment());
	}

	private List<EObject> getHierarchy(EObject eObject, List<EObject> stack) {
		EObject container = eObject.eContainer();
		if (container != null) {
			stack.add(container);
			return getHierarchy(container, stack);
		} else
			return stack;
	}

	private EObject getTopNode(EObject trg) {
		List<EObject> stack = getHierarchy(trg, new ArrayList<EObject>());
		for (EObject eObject : stack)
			if (topnodes.contains(eObject))
				return eObject;
		return trg;
	}

	private String getNodeSemanticObjectId(EObject eObject) {
		return semanticId(getNodeLabel(eObject)) + "NN"
				+ eObject.eClass().getName();
	}

	private String getLinkSemanticObjectId(EObject eObject) {
		return semanticId(getLinkLabel(eObject)) + "LL"
				+ eObject.eClass().getName();
	}

	private void addStartNode(EObject eObject, int depth) {
		String nodeid = oidmap.get(eObject);
		String suffixedOid = nodeid + SOURCE_OID_SUFFIX;
		nodes.append(depth == 1 ? startTopNode(nodeid, suffixedOid)
				: startChildNode(nodeid, suffixedOid));

	}

	private void addContent(EObject eObject, String type) {
		String toid = getTargetOid(eObject);
		String content = getNodeLabel(eObject)
				+ (type == null ? "" : ":" + type);
		if (rawGraph) {
			addRawContent(toid, content, getColor(eObject));
		} else
			addContent(toid, content);
		int subOid = TARGET_PREFIX;
		if (SECOND_OBJ_)
			subOid = addContent(eObject, "Type" + ":" + "obj", subOid);
	}

	private void addRoot(String label) {
		if (SUBGRAPH_FRAME)
			nodes.append(startSubGraph(oidmap.get(rootNode),// rootNode,
					(rawGraph ? "" : label), true) + "\n");
		if (rawGraph)
			rawNodes.append(dotNode(label, getColor(rootNode),
					oidmap.get(rootNode))
					+ "\n");
		if (!rawGraph)
			nodes.append(dotLabel(label) + ";" + "\n");

		/*
		 * if (DRAW_TOP_EDGES) { if (TOP_NODES) nodes.append(dotNode(rootNode,
		 * label) + "\n"); } else nodes.append(dotLabel(label) + ";" + "\n");
		 */
	}

	static final String SPACES = "                                                                                                                          ";

	private void registerNodes(EObject eContainerObject, EObject eObject,
			boolean istop, int level) {
		level++;
		String sp = SPACES.substring(0, level * 3);
		DNode node = dgraph.getFacade1().getNode(eObject);
		if (node==null){ //FP130401yy  TODO error in registerNodes: node is null
			System.out.println("********** !!!! error in registerNodes: node is null "+(eContainerObject==null?"null":eContainerObject.toString())+" -> "+(eObject==null?"null":eObject.toString()));
			level--;
			return;
		}
		// logEdge(eContainerObject, eObject, level);
		if (level == 1) {
			if (LOG)
				System.out.println(sp + "1 - root->" + node.getName());
		}
		if (level == 2) {
			topnodes.add(eObject);
			if (LOG)
				System.out.println(sp + "2 - topnode->" + node.getName());
		} else {
			if (LOG)
				System.out.println(sp + level + " - root->" + node.getName());
		}
		List<DNestedEdge> nestingEdges = getNestingEdges(node);
		for (DNestedEdge DNestedEdge : nestingEdges) {
			System.out.println(sp + "    (nesting edge; "
					+ DNestedEdge.getName() + ")");
		}
		registerContainedNodes(eObject, nestingEdges, istop, level);
		level--;
	}

	private int addContent(EObject eObject, String label, int oid) {
		oid++;
		// addLine();
		addContent(getTargetOid(eObject, oid), label);
		return oid;
	}

	private void addContent(String oid, String content) {
		nodes.append(writeRow_(oid, content));
	}

	private void addRawContent(String oid, String content, String color) {
		rawNodes.append(writeRawNode(oid, content, color));
	}

	public EAttribute getFirstLabelAttribute(DLabeledElement el) { // FP130125
		/*
		if (el.getLabelAttributes() != null
				&& el.getLabelAttributes().size() > 0)
			return el.getLabelAttributes().get(0);*/ //FP130615ok

		List<DLabel> dlabels = el.getDlabels();
		if (dlabels.size()>0)
			return dlabels.get(0).getAttribute();
		return null;
	}

	public List<EAttribute> getLabelAttributes(DLabeledElement el) { //FP130615ok
		List<EAttribute> result = new ArrayList<EAttribute>();
		List<DLabel> dlabels = el.getDlabels();
		for (DLabel dLabel : dlabels)
			result.add(dLabel.getAttribute());
		return result;
	}



	private String getPrefixedTargetId(EObject trg) {
		EObject topn = getTopNode(trg);
		return oidmap.get(topn) + ":" + oidmap.get(trg) + TARGET_OID_SUFFIX;
	}

	private String getTargetOid(EObject eObject) {
		return oidmap.get(eObject) + getOidSuffix(1);
	}

	private String getTargetOid(EObject eObject, int id) {
		return oidmap.get(eObject) + getOidSuffix(id);
	}

	private String getSrcId(EObject src) {
		return oidmap.get(getTopNode(src)) + ":" + oidmap.get(src)
				+ SOURCE_OID_SUFFIX;
	}

	private String[] getEdgeOid(EObject src, EObject trg,
			AssociationType association) { // TODO
		String[] edg = new String[2];
		if (association == AssociationType.Link
				|| association == AssociationType.Reference
				|| association == AssociationType.Containment) {
			if (rawGraph) {
				edg[0] = oidmap.get(src) + TARGET_OID_SUFFIX;
				edg[1] = oidmap.get(trg) + TARGET_OID_SUFFIX;
			} else {
				edg[0] = getSrcId(src);
				edg[1] = getPrefixedTargetId(trg);
			}
		} else if (association == AssociationType.Root) {
			if (rawGraph) {
				edg[0] = oidmap.get(src);
				edg[1] = oidmap.get(trg) + TARGET_OID_SUFFIX;
			} else {
				edg[0] = oidmap.get(src);
				edg[1] = getSrcId(trg);
			}
		} else
			;// cannot happen

		return edg;
	}

	public String toDot() {
		String dot = startGraph(rawGraph ? "box" : "none") + "\n";
		if (rawGraph)
			dot += rawNodes.toString() + "\n";
		else
			dot += nodes.toString() + "\n";
		dot += toEdges();
		dot += endGraph();
		return dot;
	}

	private String toEdges() {
		String buf = "";
		for (EObjectEdge eObjectEdge : edges)
			buf += eObjectEdge.drawEdgeM1_( rawGraph, DRAW_LINK_EDGES_,
				DRAW_REF_EDGES);
		return buf;
	}

	private String getLinkLabel(EObject eobj) {
		if (eobj == null)
			return "??";
		DLabeledEdge link = dgraph.getFacade1().getDLabeledEdge(eobj);
		EAttribute flabatt = getFirstLabelAttribute(link);
		if (flabatt != null)
			return (String) eobj.eGet(flabatt);
		else
			return "default";
	}


	private String getNodeLabel(EObject eobj) {
		if (eobj == null)
			return "noobj";

		DNode nod = dgraph.getFacade1().getNode(eobj);
		if (nod == null){
			return "not available"; //FP130401yyy
			//throw new RuntimeException("getNodeLabel: no Node found for "+ eobj.toString());
		}

		EAttribute flabatt = getFirstLabelAttribute(nod);
		if (flabatt != null)
			return (String) eobj.eGet(flabatt);
		else
			return "default";
	}

	private String register(EObject eObject) {
		String oid = oidmap.get(eObject);
		if (oid == null) {
			oid = SEMANTIC_IDS ? getLinkSemanticObjectId(eObject)
					: getObjectId(eObject);
			oidmap.put(eObject, oid);// getLinkObjectId(eObject));
		}
		return oid;
	}

	private void addReferenceObject(EObject eObject, DReference dref) {
		Object featuredObject = eObject.eGet(dref.getTargetReference());
		if (featuredObject instanceof EList) {
			EList<EObject> eChildObjects = (EList<EObject>) featuredObject;
			for (EObject eobj : eChildObjects)
				addReferenceEdge(eObject, eobj);
		} else
			addReferenceEdge(eObject, (EObject) featuredObject);
	}

	private void adDLabeledEdgeObject(EObject eObject, DLabeledEdge link) {
		EReference featureToAssoclass = null;
		try {
			featureToAssoclass = (EReference) eObject.eClass()
					.getEStructuralFeature(
							link.getSourceReference().getEOpposite().getName());
		} catch (Exception e) {
			System.out.println("error on " + e.toString());
			return;
		}

		Object featuredObject = eObject.eGet(featureToAssoclass);
		if (featuredObject instanceof EList)
			for (EObject eobj : (EList<EObject>) featuredObject)
				adDLabeledEdgeEdge(eobj, link);
		else
			adDLabeledEdgeEdge((EObject) featuredObject, link);
	}

	private void adDLabeledEdgeEdges(EObject eObject) {
		for (DLabeledEdge DLabeledEdge : dgraph.getFacade1().getLabeledEdges(dgraph.getFacade1().getNode(eObject)))
			adDLabeledEdgeObject(eObject, DLabeledEdge);
	}

	private void addReferenceEdges(EObject eObject) {
		for (DReference dref : dgraph.getFacade1().getReferenceEdges(dgraph.getFacade1().getNode(eObject)))
			addReferenceObject(eObject, dref);
	}

	private void addEdges() {
		addReferences();
		adDLabeledEdges();
	}

	private void addFooter() {
		if (SUBGRAPH_FRAME)
			nodes.append(endSubGraph() + "\n");
	}

	private void addAllNodes() {
		// if (TOP_NODES)
		try {
			addCompositeNodes(rootNode);
		} catch (Exception e) {

			String m=e.getMessage();
			if (m==null)
				m=e.toString();
			throw new RuntimeException(m);
		}
	}


	@Override
	public void accept(EObject model) {
		// setDsmlConcreteSyntax((DGraph) model);
		addRoot(model);
		addAllNodes();
		addFooter();
		addEdges();
	}

	private void addRoot(EObject model) {
		initializeIds();
		registerRoot(model); // All Nodes, give them an identifier
		DNode dnod=dgraph.getFacade1().getNode(model);
		addRoot(getNodeLabel(model) + ":" + (dnod==null?"null node":dnod.getName()));
	}

	@Override
	public String asDot() {
		return asDot;
	}

}
