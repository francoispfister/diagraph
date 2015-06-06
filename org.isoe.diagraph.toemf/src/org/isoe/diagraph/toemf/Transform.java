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
package org.isoe.diagraph.toemf;

import java.io.IOException;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.isoe.diagraph.dgi.DElement;
import org.isoe.diagraph.dgi.DContainment;
import org.isoe.diagraph.dgi.DGenericElement;
import org.isoe.diagraph.dgi.DLabel;
import org.isoe.diagraph.dgi.DLabelledElement;
import org.isoe.diagraph.dgi.DLink;
import org.isoe.diagraph.dgi.DNode;
import org.isoe.diagraph.dgi.DPointOfView;
import org.isoe.diagraph.dgi.DPoorReference;
import org.isoe.diagraph.dgi.DConcreteSyntax;
import org.isoe.diagraph.dgi.DgiFactory;
import org.isoe.diagraph.dgi.DgiPackage;
import org.isoe.fwk.utils.ResourceUtils;


/**
 *
 * @author fpfister
 *
 */
public class Transform {

	private DConcreteSyntax syntax;

	public Transform() {
		super();
	}

	public DConcreteSyntax createConcreteSyntax() {
		syntax = DgiFactory.eINSTANCE.createDConcreteSyntax();
		return syntax;
	}

	public DPointOfView createPointOfView_nu() {
		DPointOfView pov = DgiFactory.eINSTANCE.createDPointOfView();
		return pov;
	}



	public DNode addNode(String name,EModelElement modelElement, EModelElement containmentBase, EModelElement containmentAlt,boolean recursive, boolean isroot) {
		DNode nod = DgiFactory.eINSTANCE.createDNode();
		nod.setName(name);
		nod.setEContainmentModelElement(containmentBase);
		//nod.setEContainmentModelElementAlt(containmentAlt); //FP140213xxxaaa
		nod.setEModelElement(modelElement);
		nod.setRecursive(recursive);
		nod.setRoot(isroot);
		this.syntax.getDNodes().add(nod);
	    return nod;
	}


	public void load(String pluginid, String modelFilePath) throws IOException {
		ResourceSet resourceSet = new ResourceSetImpl();
		URI uri=ResourceUtils.createFileUri(pluginid + "/" + modelFilePath);
		Resource r = ResourceUtils.loadModel(resourceSet, uri, DgiPackage.eNS_URI,DgiPackage.eINSTANCE);
		System.out.println(modelFilePath+ " loaded: "+r.getContents().get(0));
		syntax = (DConcreteSyntax) r.getContents().get(0);
	}

	public DLabel addLabel(DLabelledElement labelledElement, String name, EModelElement attribute,
			boolean inSuperType, boolean inferred) {
		DLabel labl = DgiFactory.eINSTANCE.createDLabel();
		labelledElement.getDLabels().add(labl);
		labl.setDLabelledElement(labelledElement);
		labl.setName(name);
		labl.setEAttributeModelElement(attribute);
		labl.setFromSuperElement(inSuperType);
		labl.setInferred(inferred);
		return labl;
	}

	//FP3012
	public DContainment addContainment(String name, DNode sourceNode, DNode targetNode, boolean compartment,boolean isPropagated) { //FP130619yab
		DContainment cont = DgiFactory.eINSTANCE.createDContainment();
		cont.setName(name);
		cont.setCompartment(compartment);
		cont.setDTargetNode(targetNode);
		cont.setDSourceNode(sourceNode);
		sourceNode.getDContainments().add(cont);
		this.syntax.getDEdges().add(cont);
		return cont;
	}



	//FP130319ppp
	public DContainment addPort(String name, DNode sourceNode, DNode targetNode, boolean isPropagated) { //FP130619yab
		DContainment cont = DgiFactory.eINSTANCE.createDContainment();
		cont.setName(name);
		cont.setCompartment(false);
		cont.setPort(true);
		cont.setDTargetNode(targetNode);
		cont.setDSourceNode(sourceNode);
		sourceNode.getDContainments().add(cont);
		this.syntax.getDEdges().add(cont);
		return cont;
	}

	public DPoorReference addReference(String name, DNode sourceNode,
			DNode targetNode, EModelElement targetReference) {
		DPoorReference dref = DgiFactory.eINSTANCE.createDPoorReference();
		dref.setName(name);
		dref.setDSourceNode(sourceNode);
		sourceNode.getDReferences().add(dref);
		dref.setDTargetNode(targetNode);
		dref.setETargetModelElement(targetReference);
		this.syntax.getDEdges().add(dref);
		return dref;
	}

	public DLink addLink(String name, EModelElement modelElement,
			EModelElement containment, DNode sourceNode, DNode targetNode,
			EModelElement sourceReference, EModelElement targetReference) {
		DLink dlink = DgiFactory.eINSTANCE.createDLink();
		dlink.setDSourceNode(sourceNode);
		sourceNode.getDLinks().add(dlink);
		dlink.setDTargetNode(targetNode);
		dlink.setEContainmentModelElement(containment);
		dlink.setEModelElement(modelElement);
		dlink.setESourceModelElement(sourceReference);
		dlink.setETargetModelElement(targetReference);
		dlink.setName(name);
		this.syntax.getDEdges().add(dlink);
		return dlink;
	}

	public DGenericElement addSuperElement(EModelElement containment, EModelElement modelElement, String name) {
		DGenericElement delement = DgiFactory.eINSTANCE.createDGenericElement();
		delement.setName(name);
		delement.setEModelElement(modelElement);
		delement.setEContainmentModelElement(containment);
		this.syntax.getDElements().add(delement);
		return delement;
	}

	public EList<DGenericElement> getDGenericElements(){
		return this.syntax.getDElements();
	}

	public EList<DElement> getDElements_(){
		return null;//this.syntax.get;
	}


	public DConcreteSyntax getSyntax() {
		return syntax;
	}

	public DNode findNode(String name) { //FP121019xxx
		for (Object o : syntax.getDNodes()) {
			DNode nod=(DNode) o;
			if (nod.getName().equals(name))
				return nod;
		}
		return null;
	}




}
