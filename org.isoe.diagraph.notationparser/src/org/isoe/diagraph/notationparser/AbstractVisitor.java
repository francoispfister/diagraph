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
package org.isoe.diagraph.notationparser;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.diagraph.DLabel;
import org.isoe.diagraph.diagraph.DLabeledElement;
import org.isoe.diagraph.diagrammable.IDiagrammable;


/**
 *
 * @author pfister
 *
 */
public abstract class AbstractVisitor {//implements ILanguageTriple
	private static final boolean LOG = false;
	protected static final boolean COMMENT = false;

	protected EObject target;
	private String label;
	protected String targetAnchor;
	private boolean visible;
	protected IDiagrammable parser;
	protected int arrowid;
	private List<DGraph> dGraphs_;

	protected abstract void logit();

	public String ecoreEClassName_(EObject eobj) {
		if (eobj instanceof EClass){
			 EClass eclaz= (EClass) eobj;
			 if (eclaz.getEPackage()== EcorePackage.eINSTANCE)
				 return eclaz.getName();
		}
		return null;
	}

	public boolean isEcoreObject(EObject eobj) {
		if (eobj instanceof EClass)
			 if (((EClass) eobj).getEPackage() == EcorePackage.eINSTANCE)
				 return true;
		return false;
	}

	public String toString() {
		String targetName = objectAsString(target);
		return  targetName +" | " +getTargetAnchor();
	}

	public String getTargetAnchor() {
		return targetAnchor;
	}

	public EObject getTarget() {
		return target;
	}

	public boolean isVisible() {
		return visible;
	}

	public AbstractVisitor(IDiagrammable parser, EClassifier eclassifier, String targetAnchor,
			String label, boolean visible) {//, EPackage srcPackage,EPackage toMergePackage
		this.parser = parser;
		this.target = eclassifier;
		this.label = label;
		this.visible = visible;
		this.targetAnchor = targetAnchor;
	}

	public void visit(){
	    logit();
	    accept();
	}

	public List<DGraph> getConcreteSyntax_() {
      return dGraphs_;
   }

   public void accept(){
       dGraphs_=null;
	    if (parser.isCloning())
	         parser.getEcoreService().cloneClassWithAttributesAndAnnotations((EClass) target);
	     else if (parser.isParsing())
	        dGraphs_=parser.parseClassWithAttributesAndAnnotations((EClass) target,this);
	}


	protected void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	public String getName() {
		return label == null ? "undefined" : label;
	}

	private boolean filrered(String v, String[] filter_) { // FP130128
		if (filter_==null)
			return false;
		for (String filt : filter_)
			if (v.equals(filt))
				return true;
		return false;
	}

	public String objectAsString_(EObject eObject, String[] filter_) {
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
			//if (!filrered(attvalu, EObjectGraph.FILTER))
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
		EClass eClass = eObject.eClass();

		// if (eClass.getName().equals("DPartitioningNestingEdge"))
		// tb = true;

		String result = objectAsString_(eObject, filter);
		return result;
	}

	private String getAttributesAsString(EObject eObject, String[] filter) {
		if (eObject == null)
			return "null";
		return eObject.hashCode() + "x" + eObject.eClass().getName() + "["
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
	 *//*
	public String writeEdge(String style, String[] filter) {
		String edgeAsString = getAttributesAsString(getTarget(), filter);
		if (LOG)
			System.out.println(edgeAsString);
		if (visible)
			return getSourceAnchor() + " -> " + getTargetAnchor() + style
					+ "\n";
		else
			return "";
	}*/

	public abstract String drawEdgeM1_(boolean rawGraph, boolean linkEdges,
			boolean refEdges, boolean inheredges);



	protected String inherArrowtodel(String srclabel, String trglabel, String startoid,
			String endOid) {
		String result = (COMMENT ? "/*" + srclabel + " => " + trglabel
				+ "*/   " : "")
				+ startoid + " -> " + endOid
				// + " [dir=both arrowtail=odot];"
				+ " [color=red];" + (COMMENT ? " /*" + arrowid++ + "*/" : "");
		return result;
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
