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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.isoe.diagraph.diagraphviz.dotifiers.EObjectEdge.AssociationType;
import org.isoe.diagraph.diagraphviz.dotutils.DotWriter;
import org.isoe.diagraph.diagraphviz.dotutils.GraphVizConverter;
import org.isoe.diagraph.diagraphviz.dotutils.GraphwizInvoker;
import org.isoe.diagraph.diagraphviz.utils.DiaGraphvizResourceUtils;
import org.isoe.extensionpoint.graphviz.IDotifier;

/**
 *
 * @author pfister parcourir un M1
 *
 */
public abstract class EObjectGraph extends DotWriter implements GraphVizConverter,
		IObjectGraph {


	private String asDot;

	private String[] referencefilter = {};

	private String[] ecoreClassfilter = {};

	private String[][] edgefilters = {{}};

	private String[][] nodeStyles = {{}};

	private String[][] nodeLabelReplacements = {{}};

	private String[][] edgeLabelReplacements = {{}};



	public void setEcoreClassfilter(String[] ecoreClassfilter_) {
		this.ecoreClassfilter = ecoreClassfilter_;
	}

	public void setReferencefilter(String[] referencefilter) {
		this.referencefilter = referencefilter;
	}

	public void setEdgefilters(String[][] edgefilters) {
		this.edgefilters = edgefilters;
	}

	public void setNodeStyles(String[][] nodeStyles) {
		this.nodeStyles = nodeStyles;
	}

	public void setNodeLabelReplacements(String[][] nodeLabelReplacements) {
		this.nodeLabelReplacements = nodeLabelReplacements;
	}

	public void setEdgeLabelReplacements(String[][] edgeLabelReplacements) {
		this.edgeLabelReplacements = edgeLabelReplacements;
	}

	private final static boolean LOG = false;

	public final static String[] FILTER = { "null", "true", "false", "0", "1",
			"[]" };

	// private boolean hideProxiesToEcore_;

	private List<EObject> nodes = new ArrayList<EObject>();
	private List<EObjectEdge> edges = new ArrayList<EObjectEdge>();
	private Map<EObject, String> edgeEnd0 = new HashMap<EObject, String>();
	private Map<EObject, String> edgeEnd1 = new HashMap<EObject, String>();

	private DiaGraphvizResourceUtils utils;
	private EObject root;
	private DotVisitable visitable;
	private String dotOutput;
	private String xmlns;
	private String snodes;
	private String sedges;



	@Override
	public List<EObjectEdge> getEdges() {
		return edges;
	}


	@Override
	public List<EObjectEdge> getEdges(int i) {
		return edges;
	}

	@Override
	public String getNodesAsString(int i) {
		return snodes;
	}

	@Override
	public String getEdgesAsString(int i) {
		return sedges;
	}

	@Override
	public Map<EObject, String> getEdgeEnds(int i) {
		if (i == 0)
			return edgeEnd0;
		else
			return edgeEnd1;
	}

	private EObject loadModels(ResourceSet rs, URI dslM2Uri, String domain,
			String xmlns, String nsURI, boolean diagraph) {
		this.xmlns = xmlns;
		return utils.loadEmfModels(dslM2Uri, domain, xmlns, nsURI, diagraph);
	}

	@Override
	public ResourceSet handleModels(ResourceSet rs, URI uriDslM2,
			String domain, String xmlns, String eNS_URI, String view,
			DotVisitable visitable, boolean diagraph) {
		this.visitable = visitable;
		if (this.visitable != null)
			this.visitable.setVisitor(this);
		EObject model = loadModels(rs, uriDslM2, domain, xmlns, eNS_URI,
				diagraph);
		createGraph(model);
		return model.eResource().getResourceSet();
	}

	@Override
	public EObject getRoot() {
		return root;
	}

	@Override
	public void handleModel(EObject model) {

	}

	@Override
	public String getOuputPath() {
		return this.dotOutput;
	}

	public EObjectGraph(ResourceSet rs, String inputFile, String outputFile,
			String dotOutputPath, IDotifier handler) {
		utils = new DiaGraphvizResourceUtils(rs, inputFile, outputFile, handler);
		dotOutput = dotOutputPath;
		if (LOG)
			System.out.println("M0ToGraph (" + inputFile + " -log "
					+ outputFile + ")");
	}

	private EObject findNode(EObject eobj) {
		for (EObject eObject : nodes)
			if (eObject == eobj)
				return eObject;
		return null;
	}

	private void addNode(EObject node) {
		nodes.add(node);
	}

	public List<EObject> getEObjects(int id) {
		return nodes;
	}

	private EObjectEdge findEdge(EObject source, EObject target) {
		for (EObjectEdge edge : edges)
			if ((source != null && edge.getSource() == source && edge
					.getTarget() == target)
					|| (source == null && edge.getTarget() == target))
				return edge;
		return null;
	}

	private void visitAttributes_(EObject eObject) {
		EClass eClass = eObject.eClass();

		for (EAttribute attribute : eClass.getEAttributes()) {

			String attval = (eObject.eGet(attribute) == null ? "null" : eObject
					.eGet(attribute).toString());

			if (LOG)
				System.out
						.println("attr=" + attribute.getName() + "=" + attval);
		}
	}

	private void visitAllAttributes(EObject eObject) {
		EClass eClass = eObject.eClass();
		for (EAttribute attribute : eClass.getEAllAttributes()) {
			String attval = (eObject.eGet(attribute) == null ? "null" : eObject
					.eGet(attribute).toString());
			if (LOG)
				if (!filrered(attval))
					System.out.println("attr=" + attribute.getName() + "="
							+ attval);
		}
	}

	private String getIdAttribute(EObject eObject) {
		if (eObject == null)
			return "null";
		EClass eClass = eObject.eClass();
		String result = ":" + eClass.getName() + "[";
		for (EAttribute attribute : eClass.getEAttributes()) {
			if (attribute.isID()) {
				result = (eObject.eGet(attribute) == null ? "null" : eObject
						.eGet(attribute).toString());
				break;
			}
		}
		return result;
	}

	@Override
	public String getAttributesAsString(EObject eObject) {
		if (eObject == null)
			return "null";
		return getObjectId(eObject) + "x" + eObject.eClass().getName() + "["
				+ getAttributesValues_(eObject) + "]";
	}

	private boolean filrered(String v) { // FP130128
		for (String filt : FILTER)
			if (v.equals(filt))
				return true;
		return false;
	}

	private String objectAsString(EObject eObject) {
		String result = "";
		for (EAttribute attribute : eObject.eClass().getEAllAttributes()) {
			String attvalu = (eObject.eGet(attribute) == null ? "null"
					: eObject.eGet(attribute).toString());
			if (!filrered(attvalu))
				if (!result.contains(attvalu))
					result += attvalu + ";";
		}
		if (result.length() > 1)
			result = result.substring(0, result.length() - 1);
		if (result.isEmpty())
			result = "-";
		return result;
	}

	public String getAttributesValues_(EObject eObject) {
		if (eObject == null)
			return "null";
		return objectAsString(eObject);
	}

	private String writeNode(EObject eObject, String shape) {
		return getObjectId(eObject) + " [ " + shape + " label= \""
				+ getAttributesValues_(eObject) + ":"
				+ eObject.eClass().getName() + "\" ]\n";
	}



	private boolean isNodeVisible(EObject eObject) {
		boolean r1 = !Utils.isEcoreObject(eObject);
		boolean r2 = Utils.isEcoreObject(eObject)
				&& Utils.isVisibleEcoreObject(eObject);
		return r1 || r2;
	}

	private boolean isEdgeFiltered(EObject source, EObject target) {
		for (String[] edgefilter : edgefilters)
			if (edgefilter.length>1 && source.eClass().getName().equals(edgefilter[0])
					&& target.eClass().getName().equals(edgefilter[1]))
				return true;
		return false;
	}

	private String getNodeLabel(String label) {
		for (String[] lab : nodeLabelReplacements)
			if (lab.length>1)
				label=label.replace(lab[0], lab[1]);
		return label;
	}

	private String getEdgeLabel(String label) {
		for (String[] lab : edgeLabelReplacements)
			if (lab.length>1)
			  label=label.replace(lab[0], lab[1]);
		return label;
	}

	private String getNodeStyle(EObject eObject) {
		for (String[] style : nodeStyles)
			if ( style.length>1 &&  eObject.eClass().getName().equals(style[0]))
				return style[1];
		return " shape=\"box\" " ;
	}

	private String toDot(String digraphName, String filepath) {
		String result = "digraph " + digraphName + " {    rankdir=BT  node [shape=box, color=black] \n";
		snodes = "";
		sedges = "";

		for (EObject eObject : nodes)
			if (isNodeVisible(eObject))
				snodes += getNodeLabel(writeNode(eObject,getNodeStyle(eObject)));

		for (EObjectEdge edge : edges)
			if (edge.isVisible())
				sedges += edge.writeEdge(
						" [color=brown,arrowhead=\"vee\",arrowsize=\"1\" ,label=\""
								+ getEdgeLabel(edge.getName()) + "\"];", FILTER);

		result += snodes;
		result += sedges;

		result += "}\n";
		try {
			FileWriter fw = new FileWriter(filepath);
			fw.append(result);
			fw.close();
			GraphwizInvoker.toDot(filepath, "jpg");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}


	private boolean isEClassFilterd(final EObject eObject) {
		for (String filtr : ecoreClassfilter)
			if (eObject.eClass().getName().equals(filtr))
				return true;
		return false;
	}

	private boolean isReferenceFilterd(final String rname) {
		for (String filtr : referencefilter)
			if (rname.equals(filtr))
				return true;
		return false;
	}


	private boolean isEdgeVisible(EObject source, EObject target,
			String referenceName) {
		boolean visibt = isNodeVisible(target);
		visibt = visibt && !isEClassFilterd(target);
		boolean visibs = isNodeVisible(source);
		visibs = visibs && !isEClassFilterd(source);
		boolean result = visibt && visibs;
		if (isEdgeFiltered(source, target))
			result = false;
		if (isReferenceFilterd(referenceName))
			result = false;
		return result;
	}



	boolean notExistsEdge(EObject source, EObject target){
		return findEdge(source, target) == null;
	}

	boolean notExistsNode(EObject eObject){
		return findNode(eObject) == null;
	}

	boolean isVisibleNode(EObject eObject){
		return  (!isEClassFilterd(eObject))
				&& (!Utils.isEcoreObject(eObject) || Utils
						.isVisibleEcoreObject(eObject));
	}


	private void visitRelation(EObject source, EObject target,
			String referenceName, int depth) {
		if (target != null && isNodeVisible(target)) {
			if (notExistsEdge(source, target)) {
				if (source != null)
					visitAllAttributes(source);
				depth++;
				if (target != null) {
					if (notExistsNode(target) && isVisibleNode(target)){

						String n = getAttributesValues_(target) + ":"
								+ target.eClass().getName();
						addNode(target);
					}

					if (source != null) {

						boolean visible=isEdgeVisible(source, target, referenceName);
						EObjectEdge oe=new EObjectEdge(this, null, source, target,
								getObjectId(source), getObjectId(target),
								referenceName, AssociationType.Object,
								visible,
								false);

						//if (visible)  //??? stackoverflow if
						edges.add(oe);
					}

					for (EReference reference : (target.eClass())
							.getEAllReferences()) {
						Object feature = target.eGet(reference);
						if (reference.isMany())
							for (EObject ref : (EList<EObject>) feature)
								visitRelation(target, (EObject) ref,
										reference.getName(), depth);
						else if (feature != null)
							visitRelation(target, (EObject) feature,
									reference.getName(), depth);
					}
				}
				depth--;
			}
		}
	}

	private void createGraph(EObject model) {
		root = model;
		visit(root);
		for (EObject eObject : nodes) {
			edgeEnd0.put(eObject, DotWriter.getObjectId(eObject));
		}
	}

	private void visit(EObject root) {
		visitRelation(null, root, "", 1);
	}

	@Override
	public void toLogFile() {

	}

	@Override
	public void setVisitable(DotVisitable dotGenerator) {

	}

	@Override
	public CompiledGraph createDotGraph() {
		asDot = toDot(xmlns, dotOutput);
		return new CompiledGraph(this, root.eResource().getResourceSet());
	}

	@Override
	public String getStyleForEdgesToForeignGraph(EObject eObject) {
		return " [color=gray,arrowhead=none ,label=\"instance of "
				+ eObject.eClass().getName() + "   \"];";
	}

	@Override
	public List<EObject> getNodes(int section) {
		return nodes;
	}

	@Override
	public String getMatchWith(EObject eObject) {
		System.out.println("getMatchWith " +"in EObjectGraph");

		for (EObjectEdge eObjectEdge : edges) {
			System.out.println(eObjectEdge.getSource().toString());
			System.out.println(eObjectEdge.getTarget().toString());






		}

		return "";
	}

	@Override
	public EObject find(String cn) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public String asDot() {
		return asDot;
	}



}
