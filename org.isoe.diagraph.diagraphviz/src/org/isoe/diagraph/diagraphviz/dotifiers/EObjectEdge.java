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
import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.isoe.diagraph.diagraphviz.dotutils.DotWriter;
import org.isoe.diagraph.diagraph.DNestedEdge;
import org.isoe.diagraph.diagraph.DOwnedEdge;
import org.isoe.diagraph.diagraph.DEdge;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.diagraph.DGraphElement;
import org.isoe.diagraph.diagraph.DLabel;
import org.isoe.diagraph.diagraph.DLabeledEdge;
import org.isoe.diagraph.diagraph.DLabeledElement;
import org.isoe.diagraph.diagraph.DNavigationEdge;
import org.isoe.diagraph.diagraph.DNode;
import org.isoe.diagraph.diagraph.DReference;
import org.isoe.diagraph.diagraph.DSimpleEdge;


/**
 *
 * @author pfister
 *
 */
public class EObjectEdge extends DotWriter {

	public enum AssociationType {
		Object, Root, Link, Reference, Containment, TargetOnly
	};

	private static final boolean LOG = false;
	private EObject source;
	private EObject target;
	private String name;
	private IObjectGraph graph;
	private String sourceAnchor;
	private String targetAnchor;
	private AssociationType associationType;
	private DGraph dsmlConcreteSyntax;
	private boolean visible;
	private EReference eReference;


	public AssociationType getAssociationType() {
		return associationType;
	}

	public EObject getSource() {
		return source;
	}


	public String ecoreEClassName(EObject eobj) {
		if (eobj instanceof EClass){
			 EClass eclaz= (EClass) eobj;
			 if (eclaz.getEPackage()== EcorePackage.eINSTANCE)
				 return eclaz.getName();
		}
		return null;
	}

	public boolean isEcoreObject(EObject eobj) {
		if (eobj instanceof EClass)
			 if (((EClass) eobj).getEPackage()== EcorePackage.eINSTANCE)
				 return true;
		return false;
	}

	public String toString() {
		String sourceName="-";

		String targetName= ecoreEClassName(target);
		if (targetName==null)
			targetName =objectAsString(target);
		if (source!=null){

		sourceName = ecoreEClassName(source);
		if (sourceName==null)
			sourceName = objectAsString(source);
		}

		return sourceName+" | "+getSourceAnchor()+" > "+ targetName +" | "+getTargetAnchor();
	}


	public EObject getTarget() {
		return target;
	}

	public String getSourceAnchor() {
		return sourceAnchor;
	}

	public String getTargetAnchor() {
		return targetAnchor;
	}



	public boolean isVisible() {
		return visible;
	}

	public EObjectEdge(IObjectGraph graph_, DGraph dsmlConcreteSyntax,
			EObject source, EObject target, String sourceAnchor,
			String targetAnchor, String name, AssociationType associationType,
			boolean visible, boolean dummy) {
		this.graph = graph_;
		this.source = source;
		this.target = target;
		this.name = name;
		this.sourceAnchor = sourceAnchor;
		this.targetAnchor = targetAnchor;
		this.associationType = associationType;
		this.dsmlConcreteSyntax = dsmlConcreteSyntax;
		this.visible = visible;


		if (LOG) {
			try {
				String edgeAsString = graph_.getAttributesAsString(source)
						+ " -> " + graph_.getAttributesAsString(target);
				System.out.println(edgeAsString);
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}
	}

	public EObjectEdge(IObjectGraph graph, DGraph dsmlConcreteSyntax,
			EObject source, EObject target, String sourceAnchor,
			String targetAnchor, String name, AssociationType associationType,
			boolean visible, boolean dummy, EReference eref) {
		this.graph = graph;
		this.source = source;
		this.target = target;
		this.name = name;
		this.sourceAnchor = sourceAnchor;
		this.targetAnchor = targetAnchor;
		this.associationType = associationType;
		this.dsmlConcreteSyntax = dsmlConcreteSyntax;
		this.visible = visible;
		this.eReference = eref;
	}

	public EReference geteReference() {
		return eReference;
	}

	public String getName() {
		return name == null ? "undefined" : name;
	}

	private boolean filrered(String v, String[] filter_) { // FP130128
		if (filter_==null)
			return false;
		for (String filt : filter_)
			if (v.equals(filt))
				return true;
		return false;
	}

	public String objectAsString(EObject eObject, String[] filter_) {

		String result = "";
		for (EAttribute attribute : eObject.eClass().getEAllAttributes()) {
			String attvalu = (eObject.eGet(attribute) == null ? "null"
					: eObject.eGet(attribute).toString());
			if (!filrered(attvalu, filter_))
				if (!result.contains(attvalu))
					result += attvalu + ";";
		}
		if (result.length() > 1)
			result = result.substring(0, result.length() - 1);
		if (result.isEmpty())
			result = "-";
		return result;
	}

	public String objectAsString(EObject eObject) {
		String result = "";
		for (EAttribute attribute : eObject.eClass().getEAllAttributes()) {
			String attvalu = (eObject.eGet(attribute) == null ? "null"
					: eObject.eGet(attribute).toString());
			if (!filrered(attvalu, EObjectGraph.FILTER))
				if (!result.contains(attvalu))
					result += attvalu + ";";
		}
		if (result.length() > 1)
			result = result.substring(0, result.length() - 1);
		if (result.isEmpty())
			result = "-";
		//return getName()+":"+result;
		return result;
	}

	private String getAttributesValues(EObject eObject, String[] filter) {
		if (eObject == null)
			return "null";
		else
		    return objectAsString(eObject, filter);
	}

	private String getAttributesAsString(EObject eObject, String[] filter) {
		if (eObject == null)
			return "null";
		return getObjectId(eObject) + "x" + eObject.eClass().getName() + "["
				+ getAttributesValues(eObject, filter) + "]";
	}

	/**
	 * usage
	 * :edge.writeEdge(" [color=brown,arrowhead=dot,arrowsize=0.4 ,label=\""
	 * +edge.getName()+"\"];",FILTER);
	 *
	 * @param style
	 * @param filter
	 * @return
	 */
	public String writeEdge(String style, String[] filter) {
		String edgeAsString = getAttributesAsString(getTarget(), filter);
		if (LOG)
			System.out.println(edgeAsString);
		if (visible)
			return getSourceAnchor() + " -> " + getTargetAnchor() + style
					+ "\n";
		else
			return "";
	}

	public String drawEdgeM1_(boolean rawGraph, boolean linkEdges,
			boolean refEdges) {
		String edg = "";

		switch (associationType) {
		case Containment:
			if (rawGraph)
				edg += diamondArrow(getNodeLabel(source), getNodeLabel(target),
						sourceAnchor, targetAnchor) + "\n";
			break;
		case Root:
			if (rawGraph)
				edg += diamondArrow(getNodeLabel(source), getNodeLabel(target),
						sourceAnchor, targetAnchor) + "\n";
			break;
		case Link:
			if (linkEdges)
				edg += linkArrow(getNodeLabel(source), getNodeLabel(target),
						sourceAnchor, targetAnchor, name) + "\n";
			break;
		case Reference:
			if (refEdges)
				edg += refArrow(getNodeLabel(source), getNodeLabel(target),
						sourceAnchor, targetAnchor) + "\n";
			break;
		}
		return edg;
	}

	public String drawReferenceEdge___bad(EPackage rootp, String color0,
			String color1) {
		boolean isdomain = getTarget().eClass().getEPackage() == rootp;
		return getSource()
				+ " -> "
				+ getTarget()
				+ (geteReference().isContainment() ? dotStartArrow(true,
						isdomain ? color0 : color1) : dotStartArrow(false,
						isdomain ? color0 : color1))
				+ m2CardinalityLabel(geteReference()) + dotEnd();
	}

	protected String drawReferenceEdge__(EPackage rootp, String color0,
			String color1) {
		String st = startArrow(getObjectId(source));
		String tg = endArrow(getObjectId(target));
		boolean isdomain = target.eClass().getEPackage() == rootp;
		String edg = st
				+ " -> "
				+ tg
				+ (eReference.isContainment() ? dotStartArrow(true,
						isdomain ? color0 : color1) : dotStartArrow(false,
						isdomain ? color0 : color1))
				+ m2CardinalityLabel(eReference) + dotEnd();
		return edg;

	}

	protected String drawReferenceEdge_rebad(EPackage rootp, String color0,
			String color1) {
		boolean isdomain=target.eClass().getEPackage()==rootp;
		String st = startArrow(getObjectId(source));
		String tg = endArrow(getObjectId(target.eClass()));
		String edg = st
				+ " -> "
				+ tg
				+ (eReference.isContainment() ? dotStartArrow(true, isdomain?color0:color1)
						: dotStartArrow(false,  isdomain?color0:color1))
				+ m2CardinalityLabel(eReference) + dotEnd();
		return edg;

	}



	protected String drawReferenceEdge_(EPackage rootp,String color0,
			String color1) {
			boolean isdomain=((EClass)target).getEPackage()==rootp;
			String st = startArrow(getObjectId(source));
			String tg = endArrow(getObjectId(target));
			String edg = st
					+ " -> "
					+ tg
					+ (eReference.isContainment() ? dotStartArrow(true, isdomain?color0:color1)
							: dotStartArrow(false,  isdomain?color0:color1))
					+ m2CardinalityLabel(eReference) + dotEnd();
            return edg;

	}











	public String getLinkLabel(EObject eobj) {
		if (eobj == null)
			return "??";
		DLabeledEdge link = getDLabeledEdge(eobj);
		//EAttribute flabatt__ = dsmlConcreteSyntax.getFacade1().getFirstLabelAttribute(link);
		EAttribute flabatt = getFirstLabelAttribute(link);
		if (flabatt != null)
			return (String) eobj.eGet(flabatt);
		else
			return "default";
	}

	public String getNodeLabel(DGraph concreteSyntax, EObject eobj) {
		this.dsmlConcreteSyntax = concreteSyntax;
		return getNodeLabel(eobj);
	}

	public String getNodeLabel(EObject eobj) {
		if (eobj == null)
			return "noobj";

		DNode nod = getDNode(eobj);
		if (nod == null){
			return "no node found for "+eobj.toString(); //FP130401yyy
			//throw new RuntimeException("getNodeLabel: no Node found for "
			//		+ eobj.toString());
		}

		EAttribute flabatt = getFirstLabelAttribute(nod);
		if (flabatt != null)
			return (String) eobj.eGet(flabatt);
		else
			return "default";
	}

	private DLabeledEdge getDLabeledEdge(final EObject eObject) {
		for (DNode dNode : getNodes(dsmlConcreteSyntax))
			for (DEdge dEdge : getEdges(dNode))
				if (dEdge instanceof DLabeledEdge
						&& dEdge.getSemanticRole() == eObject.eClass())
					return (DLabeledEdge) dEdge;
		return null;
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




	private DNode getDNode(final EObject eObject) {
		for (DNode dNode : getNodes(dsmlConcreteSyntax))
			if (dNode.getSemanticRole() == eObject.eClass())
				return dNode;
		return null;
	}

	  private List<DNode> getNodes(DGraph graph){ //FP140518
		   List<DNode> nodes = new ArrayList<DNode>();
		   List<DGraphElement> elements = graph.getElements();
		   for (DGraphElement element : elements) {
			 if(element instanceof DNode)
				 nodes.add((DNode) element);
		   }
		   return nodes;
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






}
