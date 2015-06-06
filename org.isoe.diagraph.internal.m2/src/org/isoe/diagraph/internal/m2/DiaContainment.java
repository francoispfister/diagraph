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
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EReference;
import org.isoe.diagraph.internal.api.IDiaNamedElement;
import org.isoe.diagraph.internal.api.IDiaPlatformDelegate;
import org.isoe.diagraph.internal.m2.api.IDiaContainment;
import org.isoe.diagraph.internal.m2.api.IDiaEdge;
import org.isoe.diagraph.internal.m2.api.IDiaParser;
import org.isoe.diagraph.internal.api.IDiaNode;
import org.isoe.diagraph.parser.api.IDiagraphParser;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.log.LogConfig;

/**
 *
 * @author pfister
 *
 */

public class DiaContainment implements IDiaEdge, IDiaContainment {

	private static final boolean LOG = DParams.DiaContainment_LOG;
	private String name_;
	private IDiaNode sourceNode;
	private IDiaNode targetNode;
	private EReference targetReference_;
	private String deferredHost;
	private String errorMessage;
	private String concreteTypeName;
	private String targetRefName;
	private IDiaParser parser;
	private IDiaPlatformDelegate platformDelegate;
	private int order;
	private boolean compartment_;
	private boolean oriented; // could not be if compartment
	private boolean port; // FP130319ppp
	private boolean propagated_; // by visual inheritance
	private Object compartmentMapping;
	private Object childReference;
	private boolean derived;
	private IDiagraphParser parser2;
	private boolean containment;
	private String containmentName;
	//private IDiaNode targetTypeNode;


	private String toLog_nu() {
		String log = "[Containment \n";
		EClass me = (EClass) getEModelElement();
		if (me == null)
			log+="no semantic mapping for " + getName();
		EReference scrbase = getSourceContainmentReferenceBase();
		EReference scralt = getSourceContainmentReferenceAlt();
		EReference tr = getTargetReference();
		IDiaNode sn = getSourceNode();
		IDiaNode tn_ = getTargetNode();
		log+="ACRF_base[" + sn.getName() + "-"
				+ (scrbase == null ? "null" : scrbase.getName()) + "->"
				+ (tn_==null?"":tn_.getName()) + "]";
		log+="ACRF_alt [" + sn.getName() + "-"
				+ (scralt == null ? "null" : scralt.getName()) + "->"
				+ (tn_==null?"":tn_.getName()) + "]";
		log+="ACRF_tr  [" + sn.getName() + "-"
				+ (tr == null ? "null" : tr.getName()) + "->" + (tn_==null?"":tn_.getName()) + "]";

		if (log.contains("Composite-astates->Item"))
			 throw new RuntimeException("should not happen in containment");
        return log;
	}


	@Override
	public String toLog() {

		String log = "[Containment \n";
		log += name_ + " - "+containmentName+" ("
				+ (sourceNode == null ? "no src" : sourceNode.getName()) + "->"
				+ (targetNode == null ? "no trg" : targetNode.getName()) + ")";
		log += "\n";
		log += " propagated= "
				+ (isPropagated() ? "propagated" : "not propagated");
		log += "\n";
		log += targetRefName != null ? " targetRefName= " + targetRefName
				: " ";
		log += "\n";
		log += targetReference_ != null ? " TargetReference= "
				+ targetReference_.getName() : " ";
		log += "\n";
		log += concreteTypeName != null ? " concreteTypeName= "
						+ concreteTypeName : " ";
		log += "\n";
		log += "Containment]";
/*
		String check = (sourceNode == null ? "no src" : sourceNode.getName()) + "."
				+ (targetReference_ == null ? "no ref" : targetReference_.getName())+"."+
		 (targetNode == null ? "no trg" : targetNode.getName());


		check += "\n"+(sourceNode == null ? "no src" : sourceNode.getName()) + "."
				+ (targetRefName == null ? "no ref" : targetRefName)+"."+
		 (targetNode == null ? "no trg" : targetNode.getName());

		check += "\n"+ (sourceNode == null ? "no src" : sourceNode.getName()) + "."
				+ (targetReference_ == null ? "no ref" : targetReference_.getName())+"."+
		 (targetTypeName == null ? "no trg" : targetTypeName);
*/
		//if (check.contains("Composite.astates.Item"))
		//	 throw new RuntimeException("should not happen in containment");

		return log;
	}






	/**
	 *
	 * @return true if created by visual inheritance
	 */
	public boolean isPropagated() { // FP130619ya
		return propagated_;
	}

	@Override
	public boolean isDerived() {
		return derived;
	}

	@Override
	public boolean isCompartment() {
		return compartment_;
	}

	@Override
	public boolean isContainment() {
		return containment;
	}


/*
	@Override
	public boolean isCompartment(int depth) {
		return compartment;
	}
*/

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}


	@Override
	public boolean isDerivedCompartment() {
		return compartment_ && derived;
	}

	public void setDerived(boolean derived) {
		this.derived = derived;
	}

	@Override
	public IDiaPlatformDelegate getPlatformDelegate() {
		return null;
	}

	public EReference getTargetReference() {
		if (targetReference_ == null) {// FP140417
			targetReference_ = (EReference) getSourceNode().getSemanticRole()
					.getEStructuralFeature(targetRefName);
		}
		return targetReference_;
	}

	public void setTargetReference(EReference reference) {
		targetReference_ = reference;
	}
/*
	@Override
	public IDiaNode getTargetTypeNode() {
		return targetTypeNode;
	}
*/

	public DiaContainment(IDiaParser parser, IDiagraphParser parser2,
			boolean propagated, boolean compartment,//,boolean containment,
			boolean port,
			String targetRefName,String concreteTypeName,
			//IDiaNode targetTypeNode,
			String name_nu,IDiaNode sourceNode,boolean dummy) {
		;
		if (LOG){




			boolean c0 = concreteTypeName.isEmpty();
			if (c0)
				throw new RuntimeException("TODO1 in DiaContainment");
			boolean c1 =concreteTypeName.substring(0, 1) .equals(concreteTypeName.substring(0, 1).toUpperCase());
			if (!c1)
				throw new RuntimeException("TODO2 in DiaContainment");
			boolean c2 =targetRefName.substring(0, 1) .equals(targetRefName.substring(0, 1).toUpperCase());
			if (c2)
				throw new RuntimeException("TODO3 in DiaContainment");



		}

        this.containmentName = compartment?targetRefName:concreteTypeName;

		this.sourceNode = sourceNode; //FP150503
		this.name_=targetRefName;
		this.concreteTypeName = concreteTypeName;//FP150516  targetName;
		//this.targetTypeNode=targetTypeNode;

		this.targetRefName = targetRefName;//FP150517
		this.parser = parser; // FP150318t
		this.parser2 = parser2;
		this.compartment_ = compartment;
		this.containment = true;
		this.port = port;
		this.propagated_ = propagated;
		if (LOG) {


			clog("KDiaContainment "+sourceNode.getName()+"->"+name_+" ("+name_nu+")"); //
			if (propagated)
				clog(" containment propagated "); // FP131008yy
			if (port)
				clog(" containment is port "); // FP131008yy
		}
		this.platformDelegate = parser.getPlatformDelegateProvider()
				.createSpecificPlatform(this);

		//if (toLog().contains("Composite-astates->Item"))
		//	 throw new RuntimeException("should not happen in containment");
	}



	@Override
    public String getContainmentName_() {
		return containmentName;
	}


	@Override
	public String getNameIfShared() {
		return concreteTypeName;
	}
    @Override
	public String getNameIfCompartment() {
		return targetRefName;
	}

	@Override
	public boolean isPort() {
		return port; // FP130319ppp
	}

	public String toString() {
		if (LOG)
			return asString();
		else
			return super.toString();
	}

	public String getSourceContainmentReferenceBaseName() {
		String result = ((DiaNode) getSourceNode()).getContainmentReferenceBase() == null ? null
				: ((DiaNode) getSourceNode()).getContainmentReferenceBase().getName();
		return result;
	}

	public EReference getSourceContainmentReferenceBase() { // FP120126
		if (((DiaNode) getSourceNode()).getContainmentReferenceBase() != null)
			return ((DiaNode) getSourceNode()).getContainmentReferenceBase();
		return null;
	}

	public EReference getSourceContainmentReferenceAlt() { // FP140218
		if (((DiaNode) getSourceNode()).getContainmentReferenceAlt() != null)
			return ((DiaNode) getSourceNode()).getContainmentReferenceAlt();
		return null;
	}

	@Override
	public String asString() {

		String log = "[Containment" + " name:" + getName();
		log += "\n";
		log += getDeferredHost() != null ? " DeferredHost= "
				+ getDeferredHost() : " " + "\n";
		log += getSourceNode() != null ? (" SourceNode= "
				+ getSourceNode().getName() + " where containmentBase=" + (((DiaNode) getSourceNode()).getContainmentReferenceBase() == null ? " null"
				: ((EReference) ((DiaNode) getSourceNode()).getContainmentReferenceBase())
						.getName())) : " ?? no SourceNodeBase";
		log += "\n";
		log += getSourceNode() != null ? (" SourceNode= "
				+ getSourceNode().getName() + " where containmentAlt=" + (((DiaNode) getSourceNode()).getContainmentReferenceAlt() == null ? " null"
				: ((EReference) ((DiaNode) getSourceNode()).getContainmentReferenceAlt())
						.getName())) : " ?? no SourceNodeAlt";
		log += "\n";
		// SourceNode= Bar where containment=bar T

		log += getTargetNode() != null ? " TargetNode= "
				+ getTargetNode().getName()
				+ " - containment="
				+ (getTargetNode() != null
						&& ((DiaNode)getTargetNode()).getContainmentReferenceBase() != null ?
								(((DiaNode)getTargetNode()).getContainmentReferenceBase().getName())
						: " ??? no TargetNode containement ")
				: " ??? no TargetNode ";

		// log += "\n";
		// log += " figureEmbedded= " +(figureEmbedded_ ? " fig embedded" :
		// " fig own");

		log += "\n";
		log += " propagated= "
				+ (isPropagated() ? "propagated" : "not propagated");
		log += "\n";
		log += targetRefName != null ? " targetRefName= " + targetRefName
				: " ";
		log += "\n";
		log += targetReference_ != null ? " TargetReference= "
				+ targetReference_.getName() : " ";
		log += "\n";
		log += "Containment]";
		//targetTypeName
		return log;
	}

	public void logTargetNode() {


		EReference etref= targetReference_;
		if (etref != null) {
			for (DiaContainedElement el : parser.getDiagramElements()) {
				if ((el instanceof DiaNode)
						&& etref.getEType() == el.getEModelElement()) {
					if (this.targetNode != el
							|| !((DiaNode) el).getContainments().contains(this)) // FP0911
						clog("!!!!!  logTargetNode:"
								+ this.getClass().getSimpleName());
					break;
				}
			}
		}
	}

	private String asString2() {
		String result = getName() + ":" + getType();
		result += sourceNode != null ? " source elementName:"
				+ sourceNode.getElementName() : " source=null";
		result += targetNode != null ? " target elementName:"
				+ targetNode.getElementName() : " target=null";
		return result;
	}



	@Override
	public String getName() {
		String result = name_ != null ? name_ : targetRefName; //FP150517
		return result;
	}

	public void setSourceNode(IDiaNode node) {
		this.sourceNode = node;
	}


	public void setTargetNode(IDiaNode node) {
		if (LOG)
			clog("setTargetNode: " + node.getName());
		this.targetNode = node;
		((DiaNode) node).addSource(this); // FP1411
		//if (toLog().contains("Composite-astates->Item"))
		//	 throw new RuntimeException("should not happen in containment");
	}

	@Override
	public IDiaNode getSourceNode() {

		return sourceNode;
	}

	@Override
	public IDiaNode getTargetNode() {

		return targetNode;
	}

	@Override
	public String getType() {

		String result = this.getClass().getSimpleName();
		if (result.startsWith("Dia"))
			result = result.substring(3);
		return result;
	}

	public void setDeferredHost(String hostname) {
		this.deferredHost = hostname;
	}

	public String getDeferredHost() {
		return deferredHost;
	}

	public void setError(String msg) {
		errorMessage = msg;
	}

	public String getError() {
		return errorMessage;
	}

	public void resolveTargetRef() {


		if (sourceNode == null)
			throw new RuntimeException(
					"resolveTargetRef() - sourceNode == null for containment "
							+ this.toString());
		if (targetReference_ == null && targetRefName != null
				&& targetRefName.length() > 0) {
			if (!(((EClass) sourceNode.getEModelElement()) instanceof EClass))
				throw new RuntimeException(
						"sourceNode.EModelElement is not a Class "
								+ this.toString());
			targetReference_ = (EReference) ((EClass) sourceNode
					.getEModelElement()).getEStructuralFeature(targetRefName);
		}
		//if (toLog().contains("Composite-astates->Item"))
		//	 throw new RuntimeException("should not happen in containment");
	}

	// TODO FP3010 source and not target
	public void resolveSourceNode() {
		if (!sourceNode.getContainments().contains(this)) {
			EClass src = sourceNode.getSemanticRole();
			if (DParams.Parser_CONTAINMENT1_LOG)
				System.out.println("resolveSourceNode " + src.getName());
			((DiaNode) sourceNode).addContainment(1, this, -1, true);
			EReference dc = ((DiaNode) sourceNode).getDeclaredContainment(); // FP140408
			if (LOG)
				clog("!!!!! containment:" + this.getName() + " added to node:"
						+ sourceNode.getName());
		}
		//if (toLog().contains("Composite-astates->Item"))
		//	 throw new RuntimeException("should not happen in containment");
	}

	private List<EClass> subtargets = new ArrayList<EClass>();
	private EClass superTarget;
	private String argument;
	private boolean abztract;

	public List<EClass> getSubTargets() {

		return subtargets;
	}

	public EClass getSuperTarget() {

		return superTarget;
	}

	@Override
	public void resolveTargets() {
		EReference targref = targetReference_;
		if (targref != null) {
			EClass reftyp = (EClass) targref.getEType();
			if (reftyp == targetNode.getEModelElement()) {
				if (LOG)
					clog(" abstract, subtargets are: ");
				for (EClass subtarget : parser2.getConcreteSubNodes_(reftyp)) {
					subtargets.add(subtarget);
					if (LOG)
						clog(subtarget.getName());
				}
			} else if (reftyp.isSuperTypeOf((EClass) targetNode
					.getEModelElement())) {
				superTarget = reftyp;
				if (LOG)
					clog(" abstract, is a subtarget of: " + reftyp.getName());
			}
		}
		//if (toLog().contains("Composite-astates->Item"))
		//	 throw new RuntimeException("should not happen in containment");
	}

	@Override
	public void resolveTargetNode() {
		if (sourceNode == null)
			throw new RuntimeException(
					"resolveTargetNode() - sourceNode == null for containment "
							+ this.toString());
		if (LOG)
			clog("resolveTargetNode for containment:" + sourceNode.getName()
					+ "." + getName());
		if (targetReference_ != null && targetNode == null) {
			for (DiaContainedElement el : parser.getDiagramElements()) {
				if ((el instanceof DiaNode)
						&& targetReference_.getEType() == el.getEModelElement()) {

					EClass t = (EClass) el.getEModelElement();
					if (t.isAbstract())
						((DiaNode) el).setAbstragt();
					setTargetNode((DiaNode) el);
					if (LOG)
						clog("resolveTargetNode:"
								+ this.getClass().getSimpleName() + ": "
								+ sourceNode.getName() + "->" + getName()
								+ "->" + targetNode.getName());
					break;
				}
			}
			if (targetNode == null) {
				for (DiaContainedElement el : parser.getDiagramElements()) {
					if (el instanceof DiaNode) {
						if (targetReference_.getEType() == el.getEModelElement()) {
							setTargetNode((DiaNode) el);
							if (LOG)
								clog(this.getClass().getSimpleName() + ": "
										+ sourceNode.getName() + "->"
										+ getName() + "->"
										+ targetNode.getName());
							break;
						}
					}
				}
			}
			DiaContainedElement targetElement = parser
					.findDiagramElement(targetReference_.getEReferenceType());
			if (targetNode == null) {
				if (targetElement == null
						|| !(targetElement instanceof DiaGenericElement)) { // FP130618
					throw new RuntimeException(
							"missing node annotation for target of "
									+ getSourceNode().getName() + "."
									+ this.getName() + " in view "
									+ parser.getView());
				} else if (LOG)
					clog(" no target node for containment - target is a generic element :"
							+ targetElement.getName());
			}
		}
		//if (toLog().contains("Composite-astates->Item"))
		//	 throw new RuntimeException("should not happen in containment");
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	@Override
	public List<IDiaNamedElement> getNamedChildren() {
		return null;
	}

	@Override
	public EClass getEModelElement() {
			return (EClass) (targetReference_ != null ? targetReference_.getEType()
				: null);
	}

	@Override
	public void setOriented_(boolean value) {
		oriented = value;
	}

	@Override
	public boolean getOriented_() {
		return oriented;
	}

	public Object getCompartmentMapping() { // FP140121
		//if (toLog().contains("Composite-astates->Item"))
		//	 throw new RuntimeException("should not happen in containment");
		return compartmentMapping;
	}

	public void setCompartmentMapping(Object compartmentMapping) {
		this.compartmentMapping = compartmentMapping;
	}

	public void setChildReference(Object chref) {
		childReference = chref;
	}

	public Object getChildReference() {
		return childReference;
	}


	@Override
	public String getArgument() {
		//if (toLog().contains("Composite-astates->Item"))
		//	 throw new RuntimeException("should not happen in containment");
		return argument;
	}

	@Override
	public void setArgument(String arg) {
		this.argument = arg;
	}

	@Override
	public void setAbstract(boolean value) {
		this.abztract=value;
	}

	public boolean isAbstract() {
		return abztract;
	}








}
