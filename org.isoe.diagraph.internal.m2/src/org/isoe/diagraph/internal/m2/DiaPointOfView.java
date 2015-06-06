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
package org.isoe.diagraph.internal.m2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.isoe.diagraph.internal.api.IDiaNode;
import org.isoe.diagraph.internal.api.IDiaPointOfView;
import org.isoe.diagraph.internal.m2.parser.DAnnotation;
import org.isoe.diagraph.views.ViewConstants;
import org.isoe.fwk.log.LogConfig;

/**
 *
 * @author pfister
 *
 */
public class DiaPointOfView implements IDiaPointOfView {

	private static final String sp = "                                                                                ";
	private static final boolean LOG = false;



	private List<IDiaPointOfView> children_obsolete = new ArrayList<IDiaPointOfView>();
	private IDiaNode dNode;

	private String namx_;
	private String elementName_;
	private EModelElement eClass;


	@Override
	public EClass getEClass() {
		return (EClass) eClass;
	}



	private String genModelId;
	private String genEditorViewId;
	private Object syntaxMapping;
	private Object editorGenerator;

	private String layer;

	// private EObject generator; //gmfgen

	public DiaPointOfView(String layer) {
		this.layer = layer; // FP121018y
	}

	public IDiaNode getNode() {
		return dNode;
	}


	@Override
	public List getContainments() {
		return this.dNode.getContainments();
	}

	private void logTree_obsolete(int depth) {
		depth++;
		String s = "";
		if (depth > 0)
			s = sp.substring(0, depth * 8);
		clog(s + ((ENamedElement) eClass).getName() + ":"
				+ (getName()));
		for (IDiaPointOfView child : children_obsolete)
			// FP121124m obsolete
			((DiaPointOfView) child).logTree_obsolete(depth);
	}

	//@Override
	/*
	public List<DiaPointOfView> asList_obsolete_nu() {
		List<DiaPointOfView> result = new ArrayList<DiaPointOfView>();
		result.add(this);
		for (IDiaPointOfView child : getChildren_obsolete_nu()) { // FP121124m obsolete
			List<DiaPointOfView> lizt = ((DiaPointOfView) child).asList_obsolete_nu();
			if (lizt.size() > 0)
				result.addAll(lizt);
		}
		return result;
	}*/

	@Override
	public void setDNode(IDiaNode node) {
		this.dNode = node;
		this.dNode.setCanvas_(true);
		this.dNode.setPointOfView(this);
	}

	public static IDiaPointOfView getPointOfView(EClass pov, String view) {
		return new DiaPointOfView(view).createPov(pov,view);
	}


	private static void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}


	private DiaPointOfView createTree_(EClass claz, String layer) {
		eClass = claz;
		Entry<String, String> poventry = DAnnotation
				.getPointOfViewProperty((EClass) eClass);
		if (poventry != null) {
			String subval = DAnnotation.subValue(poventry);
			namx_ = parsePovEntry(subval); // ""->root
			return this;
		}
		return null;
	}


	private IDiaPointOfView createPov(EClass claz, String layer) {
		if (claz==null)
			 throw new RuntimeException("a point of view is not defined");
		eClass = claz;
		elementName_ = ((ENamedElement) eClass).getName();
		this.layer = layer;
		return this;
	}

	private IDiaPointOfView findPov_obsolete(String name) {
		for (IDiaPointOfView dpov : children_obsolete)
			if (dpov.getName().equals(name))
				return dpov;
		return null;
	}

	@Override
	public String getElementName() {
		return elementName_;
	}

	@Override
	public String getNodeName() {
		return dNode.getName();
	}

	@Override
	public String getName() { // FP121018
		return layer + ViewConstants.VIEW_SEPARATOR_1
				+ (namx_ != null ? namx_ : ViewConstants.ROOT_NAME);
	}

	private String parsePovEntry(String poventry) { // FP121102third
		String result = poventry;
		if (result.contains("."))
			result = result.substring(result.indexOf(".") + 1);// FP121022kkk
		else
			result = ViewConstants.ROOT_NAME;
		return result; // FP121123z "" -> root
	}

	public static String getLayeredRootName(String layer) {
		String result = ViewConstants.ROOT_NAME;
		//if (layer.equals(ViewConstants.DIAGRAPH_ALL_VIEW_LITTERAL))
		//	return null;
		result = layer + ViewConstants.VIEW_SEPARATOR_1 + result;// FP121018xxx
		return result; // FP121123z default -> default_root
	}

	@Override
	public String getGenModelId() {
		return genModelId;
	}

	@Override
	public String getGenEditorViewId() {
		return genEditorViewId;
	}


	@Override
	public void setSyntaxMapping(Object map) {
		this.syntaxMapping = map;
	}

	@Override
	public Object getSyntaxMapping() {
		return syntaxMapping;
	}

	@Override
	public Object getEditorGenerator() {
		return editorGenerator;
	}

	@Override
	public void setGenModelId(String id) {
		genModelId = id;
	}

	@Override
	public void setGenEditorViewId(String id) {
		genEditorViewId = id;
	}


	public List<IDiaPointOfView> getChildren_obsolete_nu() {
		return children_obsolete;
	}

	@Override
	public String getSuffix() { // FP121018
		return "_" + getName();
	}

	@Override
	public void setEditorGenerator(Object editorGenerator) {
		this.editorGenerator = editorGenerator;
	}


	public void logTree_obsolete_nu() {
		logTree_obsolete(0);
	}

	@Override
	public String toString() {
		String result_ = "(" + getName() + ")" + elementName_ + "\n";
		if (getNode() != null)
			result_ += "nodename:" + getNode().getName() + "\n"; // B

		/*//remv1406
		List<IDiaPointOfView> ch = getChildren();
		if (ch.size() > 0)
			result += "children: ";
		for (IDiaPointOfView chpov : ch)
			result += chpov.getElementName() + ";";
		*/


		result_ += "\n";
	//	result += "depth:" + getDepth_obsolete_nu() + "\n"; // 2
		result_ += "elementName:" + getElementName() + "\n"; // B
		//IDiaPointOfView parpov = getParent_obsolete();
		//if (parpov != null)
	//		result += "parent:" + parpov.getElementName() + "\n";
		result_ += "pov suffix:" + getSuffix() + "\n"; // _sub1
		if (LOG)
			return result_;
		 else
			return super.toString();
	}





}
