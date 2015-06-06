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
package org.isoe.diagraph.factory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.isoe.diagraph.diagraph.DAffixedEdge;
import org.isoe.diagraph.diagraph.DCompartmentEdge;
import org.isoe.diagraph.diagraph.DContainment;
import org.isoe.diagraph.diagraph.DEdge;
import org.isoe.diagraph.diagraph.DGeneric;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.diagraph.DGraphElement;
import org.isoe.diagraph.diagraph.DLabel;
import org.isoe.diagraph.diagraph.DLabeledEdge;
import org.isoe.diagraph.diagraph.DLabeledElement;
import org.isoe.diagraph.diagraph.DNavigationEdge;
import org.isoe.diagraph.diagraph.DNestedEdge;
import org.isoe.diagraph.diagraph.DNode;
import org.isoe.diagraph.diagraph.DOwnedEdge;
import org.isoe.diagraph.diagraph.DPointOfView;
import org.isoe.diagraph.diagraph.DReference;
import org.isoe.diagraph.diagraph.DSimpleEdge;
import org.isoe.diagraph.diagraph.DiagraphFactory;
import org.isoe.diagraph.diagraph.helpers.IGraphHandler;
import org.isoe.diagraph.diastyle.DStyle;
import org.isoe.diagraph.lang.DiagraphKeywords;
import org.isoe.diagraph.workbench.api.IDiaConverter;
import org.isoe.diagraph.workbench.api.IDiagraphFactoryClient;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.core.EcoreUtilsv0;



/**
 *
 * @author pfister
 *
 */
public class DGraphFactory implements IGraphHandler {

	private static final boolean LOG = DParams.DGraphFactory_LOG;
	private static final boolean CHECK_LINK_ON_CANVAS = false; // FP130418
	private static DGraph graph;
	private static DStyle style;
	//private static IDiagraphFactoryClient generatorOwner;
	//private static IDiagraphFactoryClient parserOwner;
	private static IDiagraphFactoryClient owner;
	static DNode testn_=null;


	//DGraphFactory instanciated by org.isoe.diagraph.gen.DiagraphGenerator
	//DGraphFactory instanciated by org.isoe.diagraph.notationparser.DiagramParser

	private static DGraphFactory instance;


	@Override
	public void initGraph(DGraph dgraph) {
		if (dgraph!=null && dgraph != graph){
			if (LOG)
				 clog("initgraph "
			+(graph==null?"null":(graph.getPointOfView().getEClaz().getEPackage().getName()+"."+graph.getViewName()))
			+" -> "
			+(dgraph.getPointOfView().getEClaz().getEPackage().getName()+"."+dgraph.getViewName()));
		    graph = dgraph;
		}
	}



	@Override
	public void endGraph(DGraph dgraph) {
	   validate();
	}

	private void validate() {
		//EList<DGraphElement> els =dgraph.getElements();
		;
		List<DNode> nods=getNodes();
		int count=0;
		for (DNode dNode : nods) {
			if (dNode.getName().equals("Owner")){
				count++;
			}
		}


	}



	public static DGraphFactory getInstance(DGraph dGraph, IDiagraphFactoryClient owner){
		if (instance==null)
			instance = new DGraphFactory();


		if (dGraph !=graph &&  owner!=DGraphFactory.owner){
			if (owner.getClass().getName().contains("DiaConvertM2")){
			  testn_=null;
			  if (LOG)
				 clog("DGraphFactory, new DGraph, instanciated by "+owner.getClass().getName());
			}
		}
		graph = dGraph;
		DGraphFactory.owner = owner; //FP150526
		return instance;
	}


	private static void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);

	}

	public EAttribute getFirstLabelAttribute(DLabeledElement el) { // FP130125
		List<DLabel> dlabels = el.getDlabels();
		if (dlabels.size() > 0)
			return dlabels.get(0).getAttribute();
		return null;
	}

	public List<EAttribute> getLabelAttributes(DLabeledElement el) { // FP130615ok
		List<EAttribute> result = new ArrayList<EAttribute>();
		List<DLabel> dlabels = el.getDlabels();
		for (DLabel dLabel : dlabels)
			result.add(dLabel.getAttribute());
		return result;
	}



	private DGraphFactory( ) { //FP140701ccc

	}

	@Override
	public void setStyle(Object style) {
		this.style = (DStyle) style;
		if (graph != null && style != null)
			this.style.setDGraph(graph); // FP120724
	}

	@Override
	public DGeneric addGeneric(String name, List<String> labels, EClass eclaz) {
		DGeneric g = createGenericElement(name, labels, eclaz);
		graph.getElements().add(g); // FP140518zz
		g.setGraph(graph);
		return g;
	}

	@Override
	public DNode addNode(String name, List<String> labels,
			EModelElement modelElement, String pointOfViewName_,
			String navLink, boolean abztr) { // FP121124y

		DNode node = createNode(name, labels, modelElement, pointOfViewName_,
				navLink, abztr); // FP130319 //
								// involvedInPointOfView,containmentNode,
		graph.getElements().add(node); // FP140518
		// graph.getNodes().add(node);
		node.setGraph(graph);
		//String povname = pointOfViewName;
		if (pointOfViewName_ != null) {
			if (node instanceof DPointOfView)
				graph.setViewName(pointOfViewName_); // FP121125x
		}
		if (LOG)
		 if (labels.isEmpty() && !(node instanceof DPointOfView))
			clog("node "+node.getName()+" has no labels in view "+pointOfViewName_);

		return node;
	}



	@Override
	public DLabeledElement addLabel(DGraphElement owner, String name,
			EAttribute eAttribute, boolean propagated, boolean inferred,boolean abztract) {
		if (owner instanceof DLabeledElement) {
			DLabeledElement towner = (DLabeledElement) owner;
			DLabel dlabel = DiagraphFactory.eINSTANCE.createDLabel();
			dlabel.setAttribute(eAttribute);
			dlabel.setInferred(inferred); //FP150514
			dlabel.setPropagated(propagated);
			dlabel.setAbztract(abztract);
			towner.getDlabels().add(dlabel); // FP130615zz
			return towner;
		}
		return null;
	}

	@Override
	public String logLabel(DGraphElement owner, String name,
			EAttribute eAttribute, boolean inSuperType, boolean inferred) {
		if (owner instanceof DLabeledElement) {
			DLabeledElement towner = (DLabeledElement) owner;
			//DLabel dlabel = DiagraphFactory.eINSTANCE.createDLabel();
			//dlabel.setAttribute(eAttribute);
			//towner.getDlabels().add(dlabel); // FP130615zz
			return towner.getName()+""+eAttribute.getName()+" "+(inSuperType?"":"")+" "+(inferred?"":"");
		}
		return "not a DLabeledElement: "+owner.getClass().getName();
	}

	/*
	 *

setEClaz(EClass);
setSemanticRole(ENamedElement);
setSourceReference(EReferencve);
setTargetReference(EReference);

	*/


	@Override
	public DLabeledEdge createLink(String name, List<String> labels,
			EClass eClass, DNode source, DNode target,
			EReference containementReference, EReference sourceReference,
			EReference targetReference) {


		DLabeledEdge link = DiagraphFactory.eINSTANCE.createDLabeledEdge();
		link.setEClaz(eClass);
		link.setSemanticRole(eClass); // FP120618
		link.setName(name);
		link.setSource(source); //FP150423c
		if (sourceReference != null)
			link.setSourceReference(sourceReference);
		link.setTargetReference(targetReference);
		link.setTarget(target);
		link.setOwner(source);
		if (labels != null && !labels.isEmpty())
			link.getLabls().addAll(labels); // FP130121
		return link;
	}

	private DGeneric createGenericElement(String name, List<String> labels,
			EClass eclaz) { // FP140518zz
		DGeneric el = DiagraphFactory.eINSTANCE.createDGeneric();
		el.setGraph(graph);
		el.setName(name);
		// el.setExpression(value);
		if (eclaz != null) {
			el.setEClaz(eclaz);
			el.setSemanticRole(eclaz); // FP120618
		} else
			throw new RuntimeException(
					"GenericElement.setSemanticRole - should not happen !!!");
		if (labels != null && !labels.isEmpty())
			el.getLabls().addAll(labels);
		return el;

	}


	@Override
	public DNode createNode(String name, List<String> labels,
			EModelElement modelElement, String pointOfViewName,
			String navigationLink, boolean abztr) { // FP121124y //boolean
 //FP150601voirvoir
		DNode node = null;
		;
		if (name.equals("Owner") && pointOfViewName == null){

			testn_ = node;
			if (LOG)

				clog("create "+name+" "+labels.size());
		}



		if (pointOfViewName != null && navigationLink == null) { // FP121124
			DPointOfView pointOfV = DiagraphFactory.eINSTANCE
					.createDPointOfView();
			pointOfV.setGraph(graph);
			graph.setPointOfView(pointOfV); //FP140617voir4
			node = pointOfV;
		} else {
			node = DiagraphFactory.eINSTANCE.createDNode();
			node.setGraph(graph);
			if (navigationLink!=null) //FP140617voir4
				node.setNavigationLink(navigationLink);
		}
		node.setName(name);
		if (modelElement != null) {
			node.setEClaz((EClass) modelElement);
			node.setSemanticRole((ENamedElement) modelElement); // FP120618
		} else
			throw new RuntimeException(
					"node.setSemanticRole - should not happen !!!");
		if (labels != null && !labels.isEmpty())
			node.getLabls().addAll(labels); // FP130121
		node.setOwner(null); // FP121124
		node.setAbztract(abztr);
		return node;
	}



	@Override
	public EClass getEClassMapping(DGraphElement element) {
		if (element.getSemanticRole()!= null && element.getSemanticRole() instanceof EClass)
			return ((EClass) element.getSemanticRole());
		else
			return null;
	}



	@Override
	public DReference addReference(String name, DNode source, DNode target,
			EReference targetReference,boolean propagated) {
		DReference ref = DiagraphFactory.eINSTANCE.createDReference();
		graph.getElements().add(ref);
		ref.setGraph(graph); // FP140518
		ref.setTargetReference(targetReference);
		ref.setName(name);
		ref.setSource(source);//FP150423b
		ref.setTarget(target);
		ref.setSemanticRole(targetReference);
		ref.setPropagated(propagated);
		return ref;
	}





	@Override
	public DOwnedEdge finContainment(String srcName, String containmentName,
			String targetName) {
		EList<DGraphElement> els = graph.getElements();
		DOwnedEdge result = null;
		int count = 0;
		for (DGraphElement element : els) {
			if (element instanceof  DOwnedEdge){
				DOwnedEdge ownedEdge = (DOwnedEdge) element;
				if (ownedEdge.getOwner().getName().equals(srcName) &&
				ownedEdge.getName().equals(containmentName)
				//&& ownedEdge.getOwner().getName().equals(srcName)

						){
					 result = ownedEdge;
					 count++;
			    }
			}
		}
		return count==1?result:null;
	}


	@Override
	public DNestedEdge addNestedEdge(String name, DNode source,
			DNode target, EReference targetReference, boolean isPropagated_, boolean isDerived,
			boolean isCompartment_, boolean isPort, int depth,
			String compartmentName,boolean abztract_) { // FP130319pppa



		if (source instanceof DPointOfView && isCompartment_) {
			if (LOG)
				clog("DGF source instanceof DPointOfView && isCompartment for "
						+ name);
		}
		if (target instanceof DPointOfView && isCompartment_) {
			if (LOG)
				clog("DGF target instanceof DPointOfView && isCompartment for "
						+ name);
		}



		DNestedEdge cont = isCompartment_ ? DiagraphFactory.eINSTANCE
				.createDCompartmentEdge() : DiagraphFactory.eINSTANCE
				.createDNestedEdge();

		if (isCompartment_)
			((DCompartmentEdge) cont).setDepth(depth); //FP150512transp1


		graph.getElements().add(cont); // FP140518
		cont.setGraph(graph);
		if (isCompartment_ && compartmentName != null)
			((DCompartmentEdge) cont).setPartitionName(compartmentName); // FP120618
		cont.setOwner(source);
		cont.setName(name);
		cont.setAbztract(abztract_);
		cont.setPropagated(isPropagated_);
		cont.setTargetReference(targetReference);
		target.setOwner(source); // FP121124
		cont.setSemanticRole(targetReference); // FP120923 //FP120704 ??
		DContainment ct= getContainment(source, cont);
		cont.setSource(ct); //FP150420
		cont.setTarget(target);
		if (LOG){
			clog("containment " + toLog(ct) + " created");
			clog(edgeToString(cont));
		}
		return cont;
	}

	@Override
	public DAffixedEdge addPortContainment(String name, DNode source,
			DNode target, EReference targetReference,boolean propagated,boolean abztract) { // FP130319pppa
		DAffixedEdge cont = DiagraphFactory.eINSTANCE.createDAffixedEdge();
		graph.getElements().add(cont); // FP140518
		cont.setGraph(graph);
		cont.setOwner(source);
		cont.setName(name);
		cont.setAbztract(abztract);
		cont.setTargetReference(targetReference);
		target.setOwner(source); // FP121124
		cont.setSemanticRole(targetReference); // FP120923 //FP120704 ??
		cont.setSource(getContainment(source, cont)); //FP150420
		cont.setTarget(target);
		cont.setPropagated(propagated); //FP150516
		return cont;
	}



	private String containementToString(DContainment c) {
		String result ="";
		result+=" node="+c.getNode().getName();
		result+=c.getName()==null?" name=null":(" name="+c.getName());
		return  result;
	}




	private String getRelationKind(DEdge edge) { //FP150420b
		if (edge instanceof DNavigationEdge)
		  return DiagraphKeywords.OPEN_DIAGRAM;
		else if (edge instanceof DAffixedEdge)
			 return DiagraphKeywords.AFFIXED_;
		else if (edge instanceof DCompartmentEdge)
			 return DiagraphKeywords.KREFERENCE_;
		else if (edge instanceof DReference)
			 return DiagraphKeywords.REFERENCE;
		else if (edge instanceof DNestedEdge) //FP150516
			 return DiagraphKeywords.CREFERENCE_;
		else if (edge instanceof DLabeledEdge)
			 return DiagraphKeywords.LINK;
		else return null;
	}



	private DContainment getContainment(DNode source, DEdge edge) { //FP150422 //FP150420//FP150418// FP150412ay
		boolean isCompartment = edge instanceof DCompartmentEdge;


		DContainment cont = null;
		String contName = isCompartment?edge.getName():source.getName();//FP150422

		if (LOG)
			clog("DGF getContainment "+contName+" "+(isCompartment?"compartment ":"")+source.getName()+"."+edge.getName());


		for (DContainment c : source.getContainments()) {
			if (c.getName()==null)
				  System.err.println("name is null for containment " + containementToString(c));
			if (c.getName().equals(contName)) {
				cont = c;


				break;
			}
		}
		if (cont == null) {
			cont = DiagraphFactory.eINSTANCE.createDContainment();
			if (isCompartment){
			   cont.setName(edge.getName());
			}else
			   cont.setName(source.getName()); //FP150423azvoir
			source.getContainments().add(cont);
			cont.setNode(source);

			if (LOG)
				clog("containment " + toLog(cont) + " created");
			owner.callBack(cont);

		} else
			if (LOG)
				clog("containment found");

		return cont;
	}


	private String toLog(DContainment cont) {
		return cont.getNode().getName()+"."+cont.getName()+"."+cont.getEdges().size()+")";//+  (cont.isCompartment()?"(compartment)":"");
	}


	public String edgeToString(DNestedEdge cont) {
		String result = " [" + cont.getClass().getSimpleName() + "]";
		result += " name=" + cont.getName();
		result += " source=" + cont.getSource().getNode().getName();
		result += " target=" + cont.getTarget().getName();
		result += " compartment="
				+ ((cont instanceof DCompartmentEdge) ? "yes" : "no");
		result += " owner=" + cont.getOwner().getName();
		if (cont.getTargetReference() != null)
			result += " targetReference=" + cont.getTargetReference().getName();// .getName();
		else
			result += " targetReference=" + "null";// .getName();
		return result;
	}

	@Override
	public String getLanguageName() {
		try {
			return graph.getPointOfView().getEClaz().getEPackage().getName();
		} catch (Exception e) {
			throw new RuntimeException("should not happen in DGraphFactory");
			//return "error_lang"; //FP150601
		}

	}

	private boolean edgeExists(DPointOfView pov, EReference cref) { // FP130319ppp
		List<DEdge> edges = getEdges(pov);
		for (DEdge dEdge : edges) {
			if (CHECK_LINK_ON_CANVAS)
				if (!(dEdge instanceof DNestedEdge))
					throw new RuntimeException(
							"should not happen in resolveMissingTopNodes: edge "
									+ dEdge.getName()
									+ " should be a Implicit Edge but is a "
									+ dEdge.getClass().getSimpleName()); // FP130418
			EReference tref = dEdge.getTargetReference();
			if (tref == cref)
				return true;
		}
		return false;
	}







	public static EAnnotation createEntry(EClass eClass, String key,
			String value, String view) {
		return EcoreUtilsv0.createEntry(eClass,
				DiagraphKeywords.ANNOT_DIAGRAPH_LITTERAL, key + "=" + value,
				"", view); // FP130723
	}

	public static EAnnotation addSourceAnnotation(EClass eclass) {
		EAnnotation tn = eclass
				.getEAnnotation(DiagraphKeywords.ANNOT_DIAGRAPH_LITTERAL);
		if (tn == null) {
			EAnnotation an = EcoreFactory.eINSTANCE.createEAnnotation();
			an.setSource(DiagraphKeywords.ANNOT_DIAGRAPH_LITTERAL);
			an.setEModelElement(eclass);
		}
		return tn;
	}

	@Override
	public DNode findDNode(String nodeName) { // FP120527
		for (DNode nod : getNodes())
			if (nod.getName().equals(nodeName))
				return nod;
		return null;
	}

	private DNode findDNode(EClass eclaz) {
		for (DNode nod : getNodes())
			if (nod.getSemanticRole() == eclaz)
				return nod;
		return null;
	}


	@Override
	public DLabeledElement findGeneric(String name) { //FP150601voir
		DGraphElement f = findElement(name);
		if ( f instanceof DLabeledElement)
		    return (DLabeledElement) f;
		else
			return null;
	}

	@Override
	public DLabeledEdge findLink(String name) {
		DGraphElement f = findElement(name);
		if ( f instanceof DLabeledEdge)
		    return (DLabeledEdge) f;
		else
			return null;
	}

	@Override
	public DReference findReference(EReference reference) {

		List<DGraphElement> elems = getAllConcreteElements();
		for (DGraphElement graphElement : elems){
			if (graphElement instanceof DReference){
				DReference dref = (DReference) graphElement;
				if (dref != null && dref == reference)
					return dref;
			}
		}
		return null;

		//DReference ref //= DiagraphFactory.eINSTANCE.createDReference();
		//graph.getElements(
		//		getTargetReference
	}




	@Override
	public DLabeledEdge addLink(String name, List<String> labels,
			EClass eClass, DNode source, DNode target,
			EReference containementReference, EReference sourceReference,
			EReference targetReference) {
		DLabeledEdge DLabeledEdge = createLink(name, labels, eClass, source,
				target, containementReference, sourceReference, targetReference);
		// source.getEdges().add(DLabeledEdge); // FP121008
		graph.getElements().add(DLabeledEdge); // FP140518
		DLabeledEdge.setGraph(graph);// FP140518
		return DLabeledEdge;
	}




	@Override
	public DGraphElement findElement(String name) {
		List<DGraphElement> elems = getAllConcreteElements();
		for (DGraphElement graphElement : elems)
			if (graphElement.getName().equals(name))
				return graphElement;
		return null;
	}

	@Override
	public List<DGraphElement> getAllConcreteElements() {
		List<DGraphElement> result = new ArrayList<DGraphElement>();
		for (DGraphElement element : graph.getElements()) {
			if (!(element instanceof DGeneric))
				result.add(element);
		}
		return result;
	}


	@Override
	public List<DGraphElement> getAllElements() {
		return graph.getElements();
	}

	@Override
	public List<DNode> getAllNodes() {
		return getNodes(); // FP120927
	}



	@Override
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

	@Override
	public List<DEdge> getEdgesTo(DNode node) {
		List<DEdge> result = new ArrayList<DEdge>();
		for (DGraphElement element : graph.getElements())
			if (element instanceof DEdge)
				if (((DEdge) element).getTarget() == node)
					result.add((DEdge) element);
		return result;
	}

	@Override
	public List<DNode> getNodes() { // FP140518
		List<DNode> nodes = new ArrayList<DNode>();
		List<DGraphElement> elements = graph.getElements();
		for (DGraphElement element : elements) {
			if (element instanceof DNode)
				nodes.add((DNode) element);
		}
		return nodes;
	}

	@Override
	public List<DGeneric> getGenericElements() { // FP140518zz
		List<DGeneric> els = new ArrayList<DGeneric>();
		List<DGraphElement> elements = graph.getElements();
		for (DGraphElement element : elements)
			if (element instanceof DGeneric)
				els.add((DGeneric) element);
		return els;
	}

	@Override
	public List<DEdge> getEdges() { // FP140518
		List<DEdge> edges = new ArrayList<DEdge>();
		List<DGraphElement> elements = graph.getElements();
		for (DGraphElement element : elements) {
			if (element instanceof DEdge)
				edges.add((DEdge) element);
		}
		return edges;
	}


	@Override
	public List<DReference> getReferenceEdges(DNode node) {
		List<DReference> result = new ArrayList<DReference>();
		result = new ArrayList<DReference>();
		for (DEdge dEdge : getEdges(node))
			if (dEdge instanceof DReference)
				result.add((DReference) dEdge);
		return result;
	}

	@Override
	public List<DLabeledEdge> getLabeledEdges(DNode node) {
		List<DLabeledEdge> result = new ArrayList<DLabeledEdge>();
		for (DEdge dEdge : getEdges(node))
			if (dEdge instanceof DLabeledEdge)
				result.add((DLabeledEdge) dEdge);
		return result;
	}


	@Override
	public DLabeledEdge getDLabeledEdge(EObject eObject) {
		for (DEdge dEdge : getEdges())
			if (dEdge instanceof DLabeledEdge
					&& dEdge.getSemanticRole() == eObject.eClass())
				return (DLabeledEdge) dEdge;
		return null;
	}

	@Override
	public DNode getNode(EObject eObject) {
		for (DNode dNode : getNodes())
			if (dNode.getSemanticRole() == eObject.eClass())
				return dNode;
		return null;
	}

	@Override
	public DEdge getLabeledEdge(String edgeName) {
		for (DEdge dEdge : getEdges()) {
			//if (dEdge instanceof DLabeledEdge) {
			//	DLabeledEdge labeledEdge = (DLabeledEdge) dEdge;
			//}
			if (edgeName.equals(dEdge.getName()))
				return dEdge;
		}

		return null;
	}

	@Override
	public DEdge getEdge(String edgeName) {
		for (DEdge dEdge : getEdges())
			if (edgeName.equals(dEdge.getName()))
				return dEdge;
		return null;
	}

	@Override
	public void save(URI uri) {
		Resource resource = new XMIResourceFactoryImpl().createResource(uri);
		resource.getContents().add(graph);
		try {
			resource.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean exists(EAnnotation eAnnotation, String k) {
		for (String key : eAnnotation.getDetails().keySet())
			if (key.startsWith(k))
				return true;
		return false;
	}

	/*--------------------------*/

	public DNode getNode(String name) {
		for (DNode dNode : getNodes())
			if (dNode.getName().equals(name))
				return dNode;
		return null;
	}

	private List<EAttribute> getAttributeLabels(EAnnotation eAnnotation,
			EClass owner) {
		List<EAttribute> attributes = new ArrayList<EAttribute>();
		for (Entry<String, String> entry : eAnnotation.getDetails()) {
			if (entry.getKey().startsWith(DiagraphKeywords.LABELEQ)) {
				String[] pv = entry.getKey().split("=");
				EAttribute attr = (EAttribute) owner
						.getEStructuralFeature(pv[1]);
				attributes.add(attr);
			}
		}
		return attributes;
	}

	private EReference findReference(String srcname) {
		if (DParams.TODO_LOG_);//FP140701ccc
		// controler.cerror("***  TODO implement findReference " + " (" + srcname
		//		+ ")");
		return null;
	}

	private boolean containsAttributeLabel(DLabeledElement labeledElement,
			String name) {
		for (DLabel dLabel : labeledElement.getDlabels())
			if (dLabel.getAttribute().getName().equals(name))
				return true;
		return false;
	}

	private DNode findNode(EClass claz) {
		return claz == null ? null : getNode(claz.getName());
	}

	private EClass findContainement(EPackage epakage, String containmentName) {
		if (containmentName == null)
			return null;
		String[] containment = containmentName.split("\\.");
		String eclassname = containment[0];
		String erefname = containment[1];
		EClass cont = (EClass) epakage.getEClassifier(eclassname);
		EReference ref = (EReference) cont.getEStructuralFeature(erefname);
		return cont;
	}

	@Override
	public void addNodeLabels(EAnnotation eAnnotation) {
		EClass owner = (EClass) eAnnotation.eContainer();
		DNode dnod = getNode(owner.getName());
		if (dnod == null)
			throw new RuntimeException("no node " + owner.getName());
		for (EAttribute eAttribute : getAttributeLabels(eAnnotation, owner)) {
			if (!containsAttributeLabel(dnod, eAttribute.getName())) {
				DLabel labl = DiagraphFactory.eINSTANCE.createDLabel();
				labl.setAttribute(eAttribute);
				dnod.getDlabels().add(labl);
			}
		}
	}

	@Override
	public DPointOfView addPointOfView(EAnnotation eAnnotation) {
		EClass owner = (EClass) eAnnotation.eContainer();
		if (LOG)
			clog("DGRFC addPointOfView "+owner.getName());
		DPointOfView pov = DiagraphFactory.eINSTANCE.createDPointOfView();
		pov.setName(owner.getName());
		pov.setSemanticRole(owner);
		pov.setEClaz(owner);
		graph.getElements().add(pov);
		pov.setGraph(graph);
		graph.setPointOfView(pov);
		return pov;
	}

	@Override
	public DNode addSimpleNode(EAnnotation eAnnotation) {
		EClass owner = (EClass) eAnnotation.eContainer();
		DNode nod = DiagraphFactory.eINSTANCE.createDNode();
		nod.setName(owner.getName());
		nod.setSemanticRole(owner);
		nod.setEClaz(owner);
		graph.getElements().add(nod);
		nod.setGraph(graph);
		return nod;
	}

	@Override
	public DLabeledEdge createLabeledEdge(EClass owner, EReference ref) {
		EClass semantic = (EClass) ref.getEType();
		DLabeledEdge link = DiagraphFactory.eINSTANCE.createDLabeledEdge();
		DNode ownerNode = findNode(owner);
		if (ownerNode != null) {
			graph.getElements().add(link);
			link.setGraph(graph); // FP140518
		} else if (LOG)
			clog("no owner found for LabelledEdge " + semantic.getName());
		link.setSourceReference(ref);
		link.setName(semantic.getName());
		link.setSemanticRole(semantic);
		link.setEClaz(semantic);
		return link;
	}

	@Override
	public DLabeledEdge createLabeledEdge(EClass eclaz, String[] p) {
		DLabeledEdge link = DiagraphFactory.eINSTANCE.createDLabeledEdge();
		String srcname = p[DiagraphKeywords.LINK_SOURCE_ID];
		String targetName = p[DiagraphKeywords.LINK_TARGET_ID];
		String containmentName = p[DiagraphKeywords.CONTAINMENT_ID];
		if (LOG)
			clog(containmentName);
		EClass containmentClass = findContainement(eclaz.getEPackage(),
				containmentName);
		DNode ownerNode = findNode(containmentClass);
		if (ownerNode != null) {
			graph.getElements().add(link);
			link.setGraph(graph); // FP140518
		} else if (LOG)
			clog("no owner found for LabelledEdge " + eclaz.getName());
		link.setSourceReference(findReference(srcname));
		link.setTargetReference(findReference(targetName));
		link.setName(eclaz.getName());
		link.setSemanticRole(eclaz);//voir
		link.setEClaz(eclaz);
		return link;
	}

	@Override
	public List<DNode> resolveMissingTopNodes_(String nodeName,
			List<EReference> shouldExists) { // FP130125y
		boolean isCompartment = false;
		boolean isPropagated = false;
		boolean isDerived = false;
		boolean isPort = false;
		List<DNode> addedTopNodes = new ArrayList<DNode>();
		DPointOfView pov = (DPointOfView) (findDNode(nodeName));
		for (EReference eReference : shouldExists) {
			if (!edgeExists(pov, eReference)) {
				EClass claz = (EClass) eReference.getEType();
				DNode tnode = findDNode(claz);
				if (tnode != null) // FP130319a créer des cref sur le canvas
									// pour tous les topnodes
					//voir MissingCref
					createContainmentWhenMissingTopNode(eReference.getName(), eReference, pov,
							tnode, isCompartment, isPort, isPropagated,isDerived,0,
							eReference.getName(),claz.isAbstract());
			}
		}
		return addedTopNodes;
	}

	private DNestedEdge createContainmentWhenMissingTopNode(String name, EReference targetRef,
			DNode srcNode, DNode tgtNode, boolean isCompartment,
			boolean isPort, boolean isPropagated, boolean isDerived, int depth,String containmentName,boolean abztract) { // FP120618
		DNestedEdge containment = addNestedEdge(name, srcNode, tgtNode,
				targetRef, isPropagated, isDerived,isCompartment, isPort, depth,containmentName,abztract); // FP120923//FP120704a
		return containment;
	}



	@Override
	public String getQualifiedLanguageName() {
		return getLanguageName() + "." + graph.getViewName();
	}
















	//////////////// _nu //////////////////////////


}
