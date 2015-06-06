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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.isoe.diagraph.diagraph.DCompartmentEdge;
import org.isoe.diagraph.diagraph.DOwnedEdge;
import org.isoe.diagraph.diagraph.DEdge;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.diagraph.DGraphElement;
import org.isoe.diagraph.diagraph.DNavigationEdge;
import org.isoe.diagraph.diagraph.DNestedEdge;
import org.isoe.diagraph.diagraph.DLabel;
import org.isoe.diagraph.diagraph.DLabeledEdge;
import org.isoe.diagraph.diagraph.DLabeledElement;
import org.isoe.diagraph.diagraph.DNode;
import org.isoe.diagraph.diagraph.DPointOfView;
import org.isoe.diagraph.diagraph.DReference;
import org.isoe.diagraph.diagraph.DSimpleEdge;
import org.isoe.diagraph.diagraphviz.dotutils.GraphVizConverter;
import org.isoe.diagraph.diagraphviz.utils.DiaGraphvizResourceUtils;
import org.isoe.extensionpoint.graphviz.IDotifier;

/**
 *
 * @author pfister
 *
 */
public class M1ToGraphBis implements GraphVizConverter {

	DiaGraphvizResourceUtils utils;

	class VisitResult {
		String caption;
		String[] labels;
		boolean result;

		public VisitResult(boolean result, String caption, String[] labels) {
			this.labels = labels;
			this.result = result;
			this.caption = caption;
		}

		public VisitResult(boolean result, String caption) {
			this.result = result;
			this.caption = caption;
		}
	}

	private static final boolean LOG_ = false;
	private Set<String> visitedNodes;
	private Set<String> visitedEdges;

	private int nodeOrder;
	private int edgeOrder;
	private static int counter;
	private final static String SPACES = "                                                                                                     ";

	private static final boolean LOG_OBJ = false;
	private static final boolean LOG_TRACE = true;



	private DGraph dsmlConcreteSyntax;
	private EObject dsmlAbstractSyntax;
	private String outputFileName_;
	private String dotContent_;
	//private DotVisitable visitable;


	private final static String VN="visualNotation";
	private static final String ZERO_OID_SUFFIX = null;
	private static final boolean SECOND_OBJ = false;
	private static final boolean VISIT1 = true;


	@Override
	public List<EObjectEdge> getEdges() {
		return edges;
	}

    private String asDot;

	@Override
	public ResourceSet handleModels(ResourceSet rs,URI uriDslM2, String domain, String xmlns,
			String eNS_URI, String view, DotVisitable visitable,boolean diagraph) {
		if (visitable!=null)
			throw new RuntimeException("should not happen in handleModels");
		//this.visitable=visitable;
		//if (this.visitable!=null)
		//   this.visitable.setVisitor(this);
		EObject aModel=loadModels_(uriDslM2, domain, xmlns, eNS_URI, view);
		//URI diagraphUri=loadModels_(uriDslM2, domain, xmlns, eNS_URI, view);
		if (aModel!=null){
		 // String result = null;
		  if (VISIT1)
		    asDot = findRootNodeAndCreateVisualNotation1();
		  else
		    asDot = findRootNodeAndCreateVisualNotation2();
		if (LOG_TRACE)
		   System.out.println(asDot);
		} else
			System.out.println("failed loading diagraph resources");
	   return aModel.eResource().getResourceSet();

	}

	public M1ToGraphBis(ResourceSet rs,String inputFile, String outputFile, IDotifier handler) {
		this.outputFileName_ = outputFile;
		utils = new DiaGraphvizResourceUtils(rs,inputFile, outputFile,handler);
	}

	@Override
	public String getOuputPath() {
		return outputFileName_;
	}


	@Override
	public EObject getRoot() {
		return dsmlConcreteSyntax;
	}


	private String getPointOfViewName(DGraph dgraph) {
		String name = dgraph.getPointOfView().getName();
		return name == null ? "default" : name;
	}


	private List<DNode> getNodes(DGraph graph){ //FP140518aa
		   List<DNode> nodes = new ArrayList<DNode>();
		   for (DGraphElement element : graph.getElements())
			 if(element instanceof DNode)
				 nodes.add((DNode) element);
		   return nodes;
	}

	private DNode getDNode(String eclazname) {
		List<DNode> dnodes = getNodes(dsmlConcreteSyntax);//.getNodes();
		for (DNode dNode : dnodes)
			if (dNode.getSemanticRole().getName().equals(eclazname))
				return dNode;
		return null;
	}

	private String getObjectId_(EObject eobj) {
		return Integer.toString(eobj.hashCode()) + ":"
				+ eobj.eClass().getName();
	}

	private VisitResult visitNodesOrNot_(StringBuffer buf, DEdge edge, EObject srcobj, EObject trgobj, EObject eObject, int depth) {
		String objid = getObjectId_(eObject);// Integer.toString(child.hashCode())+":"+child.eClass().getName();
		if (!visitedNodes.contains(objid)) {
			visitedNodes.add(objid);

			visitEdgeToNode_(buf,edge,
					getDNode(eObject.eClass().getName()), srcobj,trgobj,eObject, depth);
			return new VisitResult(true, visitChildrenNodes(buf,edge,
					getDNode(eObject.eClass().getName()), eObject, depth));
		} else
			return new VisitResult(false, visitAllreadyExistingNode(buf,
					getDNode(eObject.eClass().getName()), eObject, depth));
	}



	private VisitResult visitEdge(StringBuffer buf, DEdge edge,String soid,EObject eSemanticObject,  DLabeledElement el, String toid,boolean create,int depth){
		depth++;
		String spaces = SPACES.substring(0, (depth) * 3);
		String txt=null;
		if (edge instanceof DLabeledEdge)
			txt=getText(el, eSemanticObject, null);
		else
			txt="undefined";
		if (!create)
			txt=" alreadyExists "+txt;
		buf.append("\n" + spaces + startEdge(edge,soid, toid,true));
		String spaces2 = SPACES.substring(0, (depth+1) * 3);
		buf.append("\n" + spaces2 + txt);


		buf.append("\n" + spaces + endEdge(edge,soid, toid));
		VisitResult result = new VisitResult(true, txt);
		depth--;
		return result;
	}

	private VisitResult visitEdgeOrNot(StringBuffer buf, DEdge edge,EObject source,
			 EObject eSemanticObject,  DLabeledElement el,EObject target
			, int depth) {

		//if (LOG){
		int kase = 0;
		if (edge instanceof DReference)
			kase = 2;
		else if (edge instanceof DNestedEdge)
			kase = 3;
		else if (edge instanceof DLabeledEdge)
			kase = 4;
		//}

		String soid = getObjectId_(source);
		String toid = getObjectId_(target);
		String edgeid = soid + "-" + toid;
		if (!visitedEdges.contains(edgeid)) {
			visitedEdges.add(edgeid);
			VisitResult result=visitEdge(buf,edge,soid,eSemanticObject, el, toid,true,depth);
			return result;
		} else
			return visitEdge(buf,edge,soid,eSemanticObject, el, toid,false,depth);
		//depth--;
		//return result;
	}

	private void visitLinkedNodesAnDLabeledEdgeEdge(DLabeledEdge link, StringBuffer buf, String spac,
			EObject parent, EReference feature, DNode node, int depth) {
		Object featuredObject = parent.eGet(feature);
		String trace;
		if (featuredObject instanceof EList)
			for (EObject eobj : (EList<EObject>) featuredObject)
				trace = visitLinkedNodesAnDLabeledEdgeEdge(buf, spac, eobj, link,
						node, depth).caption;
		else
			trace = visitLinkedNodesAnDLabeledEdgeEdge(buf, spac,
					(EObject) featuredObject, link, node, depth).caption;
	}



	private void visitRelatedNodesAndReferenceEdges(DEdge edge_, StringBuffer buf,
			String spac, EObject eObject, EReference feature, DNode node,
			int depth) {
		Object featuredObject = eObject.eGet(feature);

		boolean chk;
		if (edge_ instanceof DReference)
			chk = true;
		else if (edge_ instanceof DCompartmentEdge)
			chk = true;
		else if (edge_ instanceof DNestedEdge)
			chk = true;
		else
			throw new RuntimeException(
					"should not happen in visitOneOrManyNodeIfMissing");

		if (featuredObject instanceof EList) {
			for (EObject chobj : (EList<EObject>) featuredObject) {
				DNode pnode = getDNode(((EObject) chobj).eClass().getName());
				boolean r = visitNodesOrNot_(buf, edge_, eObject,chobj,chobj, depth).result;
			}
		} else if (featuredObject instanceof EObject) {
			DNode pnode = getDNode(((EObject) featuredObject).eClass()
					.getName());
			boolean r = visitNodesOrNot_(buf, edge_, eObject, (EObject) featuredObject,(EObject) featuredObject, depth).result;
		}
	}

	private void visitFeaturedElement(DEdge edge, StringBuffer buf, String spac,
			EObject parent, EReference feature, DNode node, int depth) {

		int kase_ = 0;
		if (edge instanceof DReference) // FP130110 //TODO: ajouter les
										// DReference en dernier ???
			kase_ = 1;
		else if (edge instanceof DNestedEdge)
			kase_ = 2;
		else if (edge instanceof DLabeledEdge)
			kase_ = 3;

		if (kase_ == 0)
			throw new RuntimeException(
					"should not happen in visitFeaturedElement");

		if (edge instanceof DLabeledEdge)
			visitLinkedNodesAnDLabeledEdgeEdge((DLabeledEdge) edge, buf, spac, parent,
					feature, node, depth);
		else
			visitRelatedNodesAndReferenceEdges(edge, buf, spac, parent,
					feature, node, depth);
	}

	private static final String CLUSPARCHIL = "visitClusteringPartitionedEdgesChildrenNodes ";

	private void visitClusteringPartitionedEdgesChildrenNodes(StringBuffer buf,
			String spac, EObject eObject, DNode node,
			DCompartmentEdge DCompartmentEdge, int depth) {
		EReference feature = DCompartmentEdge.getTargetReference();
		buf.append("\n" + spac + "<" + CLUSPARCHIL + "for " + node.getName()
				+ ">");
		try {
			visitFeaturedElement(DCompartmentEdge, buf, spac, eObject,
					feature, node, depth);
		} catch (Exception e) {
			buf.append("\n***************-3-error " + e.toString());
		}
		buf.append("\n" + spac + "</" + CLUSPARCHIL + node.getName() + ">");
	}

	private static final String CLUSEDCHIL = "visitClusteringEdgeChildrenNodes ";

	private void visitClusteringEdgeChildrenNodes(StringBuffer buf,
			String spac, EObject eObject, DNode node,
			DNestedEdge DNestedEdge, int depth) {
		EReference feature = DNestedEdge.getTargetReference();
		buf.append("\n" + spac + "<" + CLUSEDCHIL + "for " + node.getName()
				+ ">");
		try {
			visitFeaturedElement(DNestedEdge, buf, spac, eObject, feature,
					node, depth);
		} catch (Exception e) {
			buf.append("\n***************-1-error " + e.toString());
		}
		//buf.append("\n" + spac + "<" +"*** EDGES HERE ***"+ ">");
		buf.append("\n" + spac + "</" + CLUSEDCHIL + node.getName() + ">");
	}

	private static final String REFCHILCAPT = "visitReferenceChildren ";


	private static final String LINKEDCHIL = "visitLinkEdgeChildren ";



	private void visitClusteringEdgeChildrenNodes(StringBuffer buf, DNode node,
	/* List<DNestedEdge> nedges, */EObject eObject, int depth, boolean canvas) {
		depth++;
		String spaces = SPACES.substring(0, depth * 3);
		List<DNestedEdge> nedges = getNestingEdges_(node);
		if (canvas && nedges.isEmpty())
			throw new RuntimeException(
					"the notation root must have at least one element !!!!");
		for (DNestedEdge DNestedEdge : nedges) {
			logEdge(DNestedEdge);
			if (DNestedEdge instanceof DCompartmentEdge) {
				if (canvas)
					throw new RuntimeException(
							"no PartitioningClusteringEdges on the notation root !!!!");
				visitClusteringPartitionedEdgesChildrenNodes(buf, spaces,
						eObject, node, (DCompartmentEdge) DNestedEdge,
						depth);
			} else if (DNestedEdge instanceof DNestedEdge) {
				visitClusteringEdgeChildrenNodes(buf, spaces, eObject, node,
						DNestedEdge, depth);
			}
		}
		depth--;
	}




	private String visitAllreadyExistingNode(StringBuffer buf, DNode node,
			EObject eObject_, int depth) {
		if (eObject_ == dsmlAbstractSyntax)
			System.out.println("graph root, do not add any node !!!");
		depth++;
		String spaces = SPACES.substring(0, depth * 3);


		String objectid = eObject_ != null ? getObjectId_(eObject_) : "0000:0000";// Integer.toString(parent.hashCode());
		buf.append("\n" + spaces + startNode(objectid, false));

		String text = getText(node, eObject_, null);
		String asString = "allready exists: " + text + ":" + node.getName();

		buf.append("\n" + spaces + asString);
		buf.append("\n" + spaces + "(not visiting nested objects)");

		buf.append("\n" + spaces + endNode(objectid, false));
		depth--;
		return asString;
	}

	private void visitRootNode(StringBuffer buf, DPointOfView node,
			EObject eObject, int depth) {
		depth++;
		String spaces = SPACES.substring(0, depth * 3);
		String objectid = "0000";
		String text = "root";
		String asString = text + ":" + "canvas";
		buf.append("\n" + startNode(objectid, true));
		buf.append("\n" + spaces + asString);
		List<DNestedEdge> nedges = getNestingEdges_(node);
		if (nedges.isEmpty())
			throw new RuntimeException("A model should have elements !!!!");
		if (!nedges.isEmpty())
			visitClusteredNodes(buf, node, eObject, depth);
		buf.append("\n" + endNode(objectid, true));
		//String result = buf.toString();
		depth--;
		//return result;
	}

	@SuppressWarnings("unchecked")
	private String visitChildrenNodes(StringBuffer buf, DEdge edge,DNode node,
			EObject eObject_, int depth) {
		depth++;
		boolean chk;
		if (edge instanceof DCompartmentEdge)
			chk = true;
		else
			if (edge instanceof DNestedEdge)
				chk = true;
			else
				throw new RuntimeException("should not happen in visitChildrenNodes "+edge.getClass().getSimpleName());
		String spaces = SPACES.substring(0, depth * 3);
		String objectid = eObject_ != null ? getObjectId_(eObject_) : "0000:0000";// Integer.toString(parent.hashCode());
		String text = getText(node, eObject_, null);
		String asString = text + ":" + node.getName();
		buf.append("\n" + spaces + startNode(objectid, true));
		buf.append("\n    " + spaces + asString);
		// List<DNestedEdge> nedges = getNestingEdges(node);

		//visitableAccept(edge,node,eObject_,text);
		if (!getNestingEdges_(node).isEmpty())
			visitClusteredNodes(buf, node, eObject_, depth);

	//	if (!node.getEdges().isEmpty())
	//		visitLinkedNodes_(buf, node, eObject_, depth);
		buf.append("\n" + spaces + endNode(objectid, true));
		depth--;
		return asString;
	}






	private String visitEdgeToNode_(StringBuffer buf, DEdge edge,DNode node,
			EObject srcobj,EObject targ,EObject eObject_, int depth) {
        boolean nu=visitEdgeOrNot(buf, edge,srcobj,  null, null,targ,  depth).result;
		return "";
	}


	private String startNode(String id, boolean create) {
		String result = (create ? "<node " : "<existing node ") + nodeOrder
				+ " id:" + id + ">";
		if (create)
			nodeOrder++;
		return result;
	}

	private String endNode(String id, boolean create) {
		return (create ? "</node " : "</existing node ") + id + ">";
	}

	static final String CLUSTERWTOPNODES = "visitClusteredNodesOnCanvas";
	static final String CLUSTERWNODES = "visitClusteredNodes";

	private void visitClusteredNodes(StringBuffer buf, DNode node,
			EObject eObject, int depth) {
		// List<DNestedEdge> nedges_ = getNestingEdges(node);
		boolean top = node instanceof DPointOfView;
		depth++;
		// checkClusters(nedges_);
		String spaces = SPACES.substring(0, depth * 3);
		buf.append("\n" + spaces + "<"
				+ (top ? CLUSTERWTOPNODES : CLUSTERWNODES) + " for "
				+ node.getName() + ">");

		visitClusteringEdgeChildrenNodes(buf, node, eObject, depth, top);
		buf.append("\n" + spaces + "</"
				+ (top ? CLUSTERWTOPNODES : CLUSTERWNODES) + " for "
				+ node.getName() + ">");
		depth--;
	}

	private List<DNestedEdge> getNestingEdges_(DNode node) {
		List<DNestedEdge> result = new ArrayList<DNestedEdge>();
		result = new ArrayList<DNestedEdge>();
		boolean hasCompartmentingEdges = false;
		for (DEdge dEdge : getEdges(node)) {
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



	private List<DEdge> getEdges(DGraph graph) { // FP140518
		List<DEdge> result = new ArrayList<DEdge>();
		for (DGraphElement element : graph.getElements())
			if (element instanceof DEdge)
				result.add((DEdge) element);
		return result;
	}



	public List<DEdge> getEdges(DNode node) {
		List<DEdge> result = new ArrayList<DEdge>();
		for (DGraphElement element : node.getGraph().getElements()){
			if (element instanceof DNestedEdge){
				if (((DNestedEdge) element).getSource().getNode() == node)
					result.add((DEdge) element);
			} else if(element instanceof DSimpleEdge){
				 if (((DSimpleEdge) element).getSource() == node)
					result.add((DEdge) element); //FP150423b
			}
		}
		return result;
	}


	private void logEdge(DEdge dEdge) {
		EReference child = dEdge.getTargetReference();
		if (LOG_) {
			if (dEdge instanceof DNestedEdge)
			  System.out.println(((DNestedEdge)dEdge).getSource().getNode().getSemanticRole().getName());
			else //FP150423b
				if(dEdge instanceof DSimpleEdge)
					  System.out.println(((DSimpleEdge) dEdge).getSource().getSemanticRole().getName());
			System.out.println(dEdge.getClass().getName());
		}
	}
	/*
	EAttribute labattr(DNode node){
		if (node.getLabelAttributes().size()>0)
		   return node.getLabelAttributes().get(0);
		return null;
	}

	EAttribute labattr(DLabeledElement el){
		if (el.getLabelAttributes().size()>0)
		   return el.getLabelAttributes().get(0);
		return null;
	}*/




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


	private String getText(DLabeledElement el, EObject eObject,
			String defaultValue) {
		String text = "-";
		EAttribute lattr = getFirstLabelAttribute(el);//labattr(el);
		if (eObject != null && lattr != null) {
			text = (String) eObject.eGet(lattr);
			if (text == null)
				text = defaultValue == null ? "[empty]" : defaultValue;
		} else
			text = defaultValue == null ? "[no label]" : defaultValue;
		//return "\"" + text + "\"";
		return text ;
	}

	@Override
	public void handleModel(EObject model) {
		//if (visitable!=null)
		 //   visitable.accept(model);
	}


	private String findRootNodeAndCreateVisualNotation2() {
		visitedNodes = new HashSet<String>();
		visitedEdges = new HashSet<String>();
		DPointOfView root = dsmlConcreteSyntax.getPointOfView();
		//visitable.setRoot(dsmlConcreteSyntax);
		String povname = getPointOfViewName(dsmlConcreteSyntax);
		String text = getText(root, dsmlAbstractSyntax, povname);
		StringBuffer buf = new StringBuffer();
		buf.append("<"+VN+">" + "\n");
		buf.append("root: " + text + " ;" + "\n");
		handleModel(dsmlAbstractSyntax);
		visitRootNode(buf, root, dsmlAbstractSyntax, 0);
		buf.append("</"+VN+">" + "\n");
		return  buf.toString() + "\n";
	}


	private String findRootNodeAndCreateVisualNotation1() {
		//visitable.setRoot(dsmlConcreteSyntax);
		handleModel(dsmlAbstractSyntax);
		return  "";
	}


	private VisitResult visitLinkedNodesAnDLabeledEdgeEdge(StringBuffer buf,
			String spac, EObject eObject, DLabeledEdge link, DNode node_, int depth) {

		depth++;
		String spaces = SPACES.substring(0, depth * 3);
		EReference trf = link.getTargetReference();
		EReference srf = link.getSourceReference();

		Object featuredObject = eObject.eGet(trf);
		if (featuredObject instanceof EList)
			throw new RuntimeException("should not happen in visitLinkEdge_");
		Object sroj = eObject.eGet(srf);
		Object trobj = eObject.eGet(trf);

		VisitResult result = visitEdgeOrNot(buf, link,(EObject) sroj,
				eObject, link, (EObject) trobj,  depth);
		depth--;
		return result;
	}



	private String startEdge(DEdge edge,String soid, String toid, boolean create) {
		String result = "<edge " + edgeOrder+" "+ edge.getClass().getSimpleName().substring(0,edge.getClass().getSimpleName().length()-4) + " id:" + soid + "-" + toid + ">";
		if (create)
			edgeOrder++;
		return result;
	}

	private String endEdge(DEdge edge,String soid, String toid) {
		String result = "</edge " /*+ edge.getClass().getSimpleName()*/ + ">";
		return result;
	}


	private EObject loadModels_(URI dslM2Uri, String domain, String xmlns,
			String nsURI, String viewName) {
		URI diagraphUri=utils.loadDiagraphModels(dslM2Uri, domain, xmlns, nsURI, viewName,false);
		if (diagraphUri!=null){
		  dsmlAbstractSyntax = utils.getDsmlAbstractSyntax();
		  dsmlConcreteSyntax = utils.getDsmlConcreteSyntax();
		}
		//return diagraphUri;
		return dsmlConcreteSyntax;
	}



	@Override
	public void toLogFile() {

	}

	@Override
	public void setVisitable(DotVisitable dotGenerator) {
	//	this.visitable = dotGenerator; //TODO move some parts of dotGenerator here (separate visitor && visitable)
	}

	@Override
	public String getNodesAsString(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getEdgesAsString(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<EObject, String> getEdgeEnds(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	@Override
	public void setEdgeEnds(int section, Map<EObject, String> edgeEnd) {
		// TODO Auto-generated method stub

	}

*/
	@Override
	public String getStyleForEdgesToForeignGraph(EObject m0Object) {
		// TODO Auto-generated method stub
		return null;
	}

	private List<EObjectEdge> edges = new ArrayList<EObjectEdge>();

	@Override
	public List<EObjectEdge> getEdges(int i) {
		// TODO FP130201
		return edges;
	}

	@Override
	public CompiledGraph createDotGraph() {
		return null;
	}

	@Override
	public List<EObject> getNodes(int section) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public String getMatchWith(EObject eObject) {
		System.out.println("getMatchWith " +"in M1ToGraphBis");

		for (EObjectEdge eObjectEdge : getEdges(0)) {
			System.out.println(eObjectEdge.getSource().toString());
			System.out.println(eObjectEdge.getTarget().toString());
		}

		for (EObjectEdge eObjectEdge : getEdges(1)) {
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
