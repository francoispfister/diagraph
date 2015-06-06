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
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.isoe.diagraph.internal.api.IDiaLabel;
import org.isoe.diagraph.internal.m2.api.IDiaEdge;
import org.isoe.diagraph.internal.m2.api.IDiaParser;
import org.isoe.fwk.core.DParams;

/**
 *
 * @author pfister
 *
 */
public class DiaLink extends DiaContainedElement implements IDiaEdge {

	private static final boolean LOG = DParams.DiaLink_LOG;
	private String source;
	private String target;
	private EReference sourceReference;
	protected EReference targetReference;
	private DiaNode sourceNode;
	private DiaNode targetNode;
	private boolean oriented;

	public void setSourceNode(DiaNode sourceNode) {
		this.sourceNode = sourceNode;
	}

	public String toString() {
		return "[" + this.getClass().getSimpleName() + "] " + super.toString()
				+ " source: " + source + " target: " + target;
	}

	@Override
	public String asString() {
		String result = getName() + ":" + getType();
		result += sourceNode != null ? " source elementName:"
				+ sourceNode.getElementName() : " source=null";
		result += targetNode != null ? " target elementName:"
				+ targetNode.getElementName() : " target=null";
		return result;
	}

	public DiaLink(IDiaParser owner, String view,
			List<String> slabels,
			String elementName, // String linkLabel,
			EModelElement element, String containmentName,
			EReference containment, List<DiaLabel> labels) {
		super(owner, element, view, slabels);
		this.view = view;
		this.setContention_(containmentName);
		this.setContainmentReferenceBase(containment);
		this.setElementName(elementName);
		// this.setLabel(linkLabel); //FP121007
		// this.setDefaultName(defaultName);
		this.setOwnLabels(labels);
	}

	public DiaLink(IDiaParser diagram, String view, EModelElement element) {
		super(diagram, element, view, null);// xyz
		this.view = view;
	}

	public static DiaContainedElement cloneLink(IDiaParser owner, String view,
			DiaContainedElement delement) {
		return new DiaLink(owner, view, delement.getLabels(),
				delement.getElementName(), delement.getEModelElement(),
				delement.getContention_(),
				delement.getContainmentReferenceBase(), delement.getOwnLabels());
	}

	public String getSource() {
		return source;
	}

	public String getTarget() {
		return target;
	}

	public EReference getSourceReference() {
		return sourceReference;
	}

	public void checkBaseContainment_() { // FP140423
		if (getContainmentReferenceBase() == null)
			throw new RuntimeException("error: no base containment for "
					+ ((EClass) getEModelElement()).getName());
	}

	public void resolveBaseContainment() { // FP140423aaa
		if (getContainmentReferenceBase() == null) {
			if (LOG)
				clog("no base containment for "
						+ ((EClass) getEModelElement()).getName());
			EClass thisclass = (EClass) getEModelElement();
			for (EClassifier classifier : thisclass.getEPackage().getEClassifiers()) {
				if (classifier instanceof EClass) {
					for (EReference eRef : ((EClass) classifier).getEAllReferences()) {
						if ((eRef.getEType() == thisclass || ((EClass) eRef.getEType())
								.isSuperTypeOf(thisclass))
								&& (eRef.isContainment() && parser
										.findNode(((EClass) eRef.eContainer())
												.getName()) != null)) {
							setContainmentReferenceBase(eRef);
							if (LOG)
								clog("containment for link " + getName()
										+ " is now " + eRef.getName());
							return;
						}

					}
				}
			}

		}

	}

	private EReference resolveSourceReferenceWithContainingClass() { //FP150513
		EReference crefbase = getContainmentReferenceBase();
		if (crefbase != null)
			for (EReference eReference : ((EClass) getEModelElement())
					.getEAllReferences()) {
				if (eReference.getEType() == crefbase.getEContainingClass() && !(isPov((EClass) crefbase.getEType()))) {

					return eReference;
					//break;
				}
			}
		return null;
	}


	private EReference getUniqueLinkSource(EClass eclaz,EReference target) { //FP150513
		int found = 0;
		EReference result = null;
		for (EStructuralFeature r : eclaz.getEAllStructuralFeatures()){
			if (r instanceof EReference){
			EReference ref = (EReference) r;
			if (  ref!=target &&   ref.getUpperBound() == 1
					&& isPov((EClass) ref.getEType()) && isNode((EClass) ref.getEType())) {
				result = ref;
				found++;
			}
			}
		}
		return found == 1 ? result : null;
	}


	public  void resolveSourceReference_() { //FP150513
	//	String t = getTarget();
		//EReference tr= getTargetReference();
		EReference result = getUniqueLinkSource(getSemanticRole(),getTargetReference());
		if (result==null)
			 result = resolveSourceReferenceWithContainingClass();
		if (result!=null)
			sourceReference = result;
	}


	public void resolveTargetReference_old130619() {
		List<EReference> lrefs = ((EClass) getEModelElement())
				.getEAllReferences();
		int otherRefCount = 0;
		for (EReference eReference : lrefs)
			if (eReference != sourceReference)
				otherRefCount++;
		if (otherRefCount == 1) {
			for (EReference eReference : lrefs)
				if (eReference != sourceReference)
					targetReference = eReference;
		} else if (otherRefCount > 1) {
			setErrorMessage("(Layer " + view + ") DiaLink " + this.getName()
					+ " Fix Target Reference Error !!");
			throw new RuntimeException(getErrorMessage());
		}

		else if (otherRefCount == 0) {
			setErrorMessage("(Layer " + view + ") DiaLink " + this.getName()
					+ " Fix Target Reference Error : no target for Link !!");
			throw new RuntimeException(getErrorMessage());
		}
	}



	public List<EReference> getTargetNodeReferences() { //FP140503b
		List<EReference> result = new ArrayList<EReference>();
		for (EReference eReference : ((EClass) getEModelElement()).getEAllReferences())
			if (eReference.getUpperBound()==1 && isNode((EClass) eReference.getEType()) && eReference != sourceReference)
				     result.add(eReference);
		return result;
	}


	public void resolveTargetReference() { //FP140503b
		if (LOG)
			clog("RTG " + ((EClass) getEModelElement()).getName());
		List<EReference> lrefs = getTargetNodeReferences();
		if (lrefs.size()==1)
			targetReference = lrefs.get(0);
		else if (lrefs.size()>1){
			setErrorMessage("(Layer " + view + ") DiaLink " + this.getName()
					+ " Fix Target Reference Error !!");
			throw new RuntimeException(getErrorMessage());
		} else
			if (((EClass) getEModelElement()).isAbstract())
				clog("RTG " + " no reference, is abstract, ok");
			else {
				setErrorMessage("(Layer " + view + ") DiaLink "
						+ this.getName()
						+ " Fix Target Reference Error : no target for Link !!");
				throw new RuntimeException(getErrorMessage());
			}
	}


	private boolean isNode(EClass eClass) {//FP140503
		return parser.findNode(eClass.getName())!=null;
	}


	private boolean isPov(EClass eClass) { //FP150513
		return parser.isPov(eClass);
	}



	private void clog(String msg) {
		if (LOG)
			System.out.println(msg);
	}

	public void setSourceReference(EReference sourceReference) {
		this.sourceReference = sourceReference;
	}

	public void setTargetReference(EReference targetReference) {
		this.targetReference = targetReference;
	}

	public EReference getTargetReference() {
		return targetReference;
	}

	@Override
	public void resolveTargetRef() {
		if (source != null && source.length() > 0)
			sourceReference = (EReference) ((EClass) getEModelElement())
					.getEStructuralFeature(source);
		if (target != null && target.length() > 0)
			targetReference = (EReference) ((EClass) getEModelElement())
					.getEStructuralFeature(target);
	}

	public String logSourceAndTarget_() {
		String result = "";
		if (sourceReference == null)
			result += "\nsourceReference==null for " + this.getName() + "."
					+ (this.getSource() != null ? this.getSource() : "DLpb1");
		if (targetReference == null)
			result += "\ntargetReference==null for " + this.getName() + "."
					+ (this.getTarget() != null ? this.getTarget() : "DLpb2");
		return result;
	}

	public boolean checkSourceAndTarget() {
		return (getSource() == null && getTarget() != null);
	}


	public void resolveSourceAndTarget() {
		if (LOG)
			clog(this.getType());
		boolean ref = this.getType().equals("Reference");
		boolean abstractLink = !ref
				&& (this.getType().equals("Link") && ((EClass) this
						.getEModelElement()).isAbstract());
		EReference containmentRef = null;
		if (targetReference == null && !abstractLink) // FP130731
			throw new RuntimeException(" targetReference==null for "
					+ getName());
		if (getContainmentReferenceBase() == null && !abstractLink)
			throw new RuntimeException(" containment==null for " + getName());
		else
			containmentRef = (EReference) getContainmentReferenceBase();
		for (DiaContainedElement other : parser.getDiagramElements()) {
			if (other instanceof DiaNode) {
				if (sourceNode != null
						&& (sourceReference != null && sourceReference
								.getEType() == other.getEModelElement()))
					setSourceNode((DiaNode) other);
				else if (sourceNode == null && containmentRef != null) { // FP130731
					setSourceNode ((DiaNode) parser
							.findDiagramElement((containmentRef)
									.getEContainingClass()));
				}
				if (!(targetReference == null && abstractLink)
						&& targetReference.getEType() == other
								.getEModelElement()) // FP130731
					targetNode = (DiaNode) other;
			} else if (other instanceof DiaGenericElement) {
				// System.out.println(el.toString());
			} else {
				// System.out.println(el.toString());
			}
		}
		if (!abstractLink) {
			if (sourceNode == null) // FP130731 //FP140628aa
				throw new RuntimeException("sourceNode should not be null for "+sourceReference.getName()+" in "
						+ getName());
			if (targetNode == null) // FP130731 //FP140628aa
				throw new RuntimeException("targetNode should not be null for " +targetReference.getName()+" in "
						+ getName());
		}
	}

	public boolean checkLinks() {
		boolean result = true;
		if (getSourceNode() == null)
			result = false;
		if (getTargetNode() == null)
			result = false;
		if (getSourceNode().getEModelElement() != sourceReference.getEType())
			result = false;
		if (getTargetNode().getEModelElement() != targetReference.getEType())
			result = false;
		return result;
	}

	@Override
	public DiaNode getSourceNode() {
		return sourceNode;
	}

	@Override
	public DiaNode getTargetNode() {
		return targetNode;
	}

	/*
	 * @Override public String getDefaultName(){ return super.getDefaultName();
	 * }
	 */

	@Override
	public String getType() {
		String result = this.getClass().getSimpleName();
		if (result.startsWith("Dia"))
			result = result.substring(3);
		return result;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Override
	public void setOriented_(boolean value) {
		oriented = value;

	}

	@Override
	public boolean getOriented_() {
		return oriented;
	}

	@Override
	public String getView() {
		return view;
	}

	@Override
	public void addInferredLabel(IDiaLabel lb) {
		// tb = true;

	}

}
