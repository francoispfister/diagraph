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

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EReference;
import org.isoe.diagraph.internal.api.IDiaNamedElement;
import org.isoe.diagraph.internal.api.IDiaPlatformDelegate;
import org.isoe.diagraph.internal.api.IDiaPlatformDelegateProvider;
import org.isoe.fwk.log.LogConfig;
import org.isoe.diagraph.internal.m2.api.IDiaEdge;


/**
 *
 * @author pfister
 *
 */
public class DiaReference implements IDiaEdge {

	private static final boolean LOG = false;
	private String targetName;
	protected String defaultName;
	protected EReference targetReference;
	protected DiaNode sourceNode;
	protected DiaNode targetNode;
	private IDiaPlatformDelegate platformDelegate;
	private boolean oriented_;


	@Override
	public IDiaPlatformDelegate getPlatformDelegate() {
		return platformDelegate;
	}

	public void setDefaultName(String defaultName) {
		this.defaultName = defaultName;
	}

	@Override
	public String getName() {
		if (defaultName != null && defaultName.length() > 0) {
			return defaultName;
		}
		return targetReference.getName();
	}
	private IDiaPlatformDelegateProvider pdp;
	private boolean propagated;

	public DiaReference(DiaNode source, boolean propagated,IDiaPlatformDelegateProvider diaPlatformDelegateProvider) {
		super();
		this.sourceNode = source;
		this.platformDelegate = diaPlatformDelegateProvider.createSpecificPlatform(this);
		this.pdp = diaPlatformDelegateProvider;
		this.propagated = propagated; //FP150516
	}

	public void resolveTargetRef() {
		//assert (sourceNode != null);
		if (sourceNode==null)
			throw new RuntimeException("sourceNode == null for "+ this.getName());
		if (targetName != null && targetName.length() > 0) {
			//assert ((EClass)
			if (!(sourceNode.getEModelElement() instanceof EClass))
					throw new RuntimeException("sourceNode.EModelElement should be an EClass");
			targetReference = (EReference) ((EClass) sourceNode
					.getEModelElement()).getEStructuralFeature(targetName);
		}
	}



	public void resolveTargetNode_() {

		if (sourceNode==null)
			throw new RuntimeException("sourceNode == null for "+ getSourceNode().getName()+"."+this.getName());
		if (targetReference==null)
			throw new RuntimeException("reference does not exist: "+ getSourceNode().getName()+"."+this.getName());
		for (DiaContainedElement sourcecontainedelement : sourceNode.parser.getDiagramElements()) {
			if (LOG )
			  System.out.println(sourcecontainedelement.getClass().getSimpleName()+":"+sourcecontainedelement.getName()+"-"+((EClass)(sourcecontainedelement.getEModelElement())).getName());
			if (sourcecontainedelement instanceof DiaGenericElement)
				if (LOG )
				   System.out.println(sourcecontainedelement.getName()+" is a DiaGenericElement !!!");
			if ((sourcecontainedelement instanceof DiaNode) && targetReference.getEType() == sourcecontainedelement.getEModelElement()) {
				targetNode = (DiaNode) sourcecontainedelement;
				if (LOG )
					System.out.println(this.getClass().getSimpleName()+": "+sourceNode.getName() + "->"
							+ defaultName + "->" + targetNode.getName());
			}
		}
		if (targetNode == null) {
			for (DiaContainedElement el : sourceNode.parser.getDiagramElements()) {
				if (el instanceof DiaNode) {
					if (LOG )
					   System.out.println("targetref:"+targetReference.getEType().getName()+ " modelelement:"+((ENamedElement)el.getEModelElement()).getName());
					if (targetReference.getEType() == el.getEModelElement()) {
						targetNode = (DiaNode) el;
						if (LOG )
							System.out
									.println(this.getClass().getSimpleName()+": "+sourceNode.getName() + "->"
											+ defaultName + "->"
											+ targetNode.getName());
					}
				}
			}
		}
		//assert targetNode != null : "targetNode should not be null !!!";
		if (targetNode==null) {//FP121017{
			if (pdp.getRunner().mustThrowExceptions())
			  throw new RuntimeException("targetNode == null for "+ this.getName()+" (see annotation "+targetReference.getName()+" on "+this.getSourceNode().getName()+")");
			else
				pdp.getRunner().cerror("targetNode == null for "+ this.getName()+" (see annotation "+targetReference.getName()+" on "+this.getSourceNode().getName()+")");
	     }

	}

	public String toString() {
		return "[" + this.getClass().getSimpleName() + "] " + "name: "
				+ defaultName + " targetName:" + targetName;
	}

	@Override
	public String asString() {
		String result = getName()+":"+getType();
		result += sourceNode!=null?" source elementName:"+sourceNode.getElementName():" source=null";
		result += targetNode!=null?" target elementName:"+targetNode.getElementName():" target=null";
		return result;
	}


	public EReference getTargetReference() {
		return targetReference;
	}

	public String getDefaultName() {
		return defaultName;
	}

	public String getTargetName() {
		return targetName;
	}

	@Override
	public DiaNode getTargetNode() {
		return targetNode;
	}


	//@Override
	public void setTargetNode(DiaNode targetNode) { //FP150516
		this.targetNode = targetNode;
	}

	@Override
	public DiaNode getSourceNode() {
		return sourceNode;
	}

	@Override
	public String getType() {
		String result = this.getClass().getSimpleName();
		if (result.startsWith("Dia"))
			result = result.substring(3);
		return result;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	@Override
	public EModelElement getEModelElement() {
		return null;
	}


	@Override
	public List<IDiaNamedElement> getNamedChildren() {
		return null;
	}

	@Override
	public void setOriented_(boolean value) {
		oriented_=value;

	}

	@Override
	public boolean getOriented_() {
		return oriented_;
	}

	public boolean isPropagated() {
		return propagated;
	}





}
