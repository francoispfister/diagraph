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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EReference;
import org.isoe.diagraph.internal.api.IDiaNamedElement;
import org.isoe.diagraph.internal.api.IDiaPlatformDelegate;
import org.isoe.diagraph.internal.api.IDiaSyntaxElement;
import org.isoe.diagraph.internal.m2.api.IDiaContainedElement;
import org.isoe.diagraph.internal.m2.api.IDiaParser;
import org.isoe.fwk.core.DParams;

/**
 *
 * @author pfister
 *
 */
public abstract class DiaContainedElement implements IDiaContainedElement,
		IDiaSyntaxElement {

	private static final boolean LOG = DParams.DiaContainedElement_LOG;
	private static final String spaces = "                                                                                                                ";

	protected IDiaParser parser;
	protected String errorMessage;
	private String contention_;
	private EReference containmentReferenceBase;
	private EReference containmentReferenceAlt_;
	protected EReference declaredContainmentReference;
	private String elementName;
	protected EModelElement eModelElement;
	private boolean promoted;
	private List<IDiaContainedElement> superElements_ = new ArrayList<IDiaContainedElement>();
	private List<IDiaContainedElement> lowerElements = new ArrayList<IDiaContainedElement>();
	private IDiaContainedElement containmentElement;
	private List<EReference> containerReferences = new ArrayList<EReference>();
	protected String view;
	protected IDiaPlatformDelegate platformDelegate;
	private boolean abztract;
	private String name_;
	private List<IDiaContainedElement> superGenericElements;
	private List<IDiaContainedElement> superNodeElements;

	private List<String> labels = new ArrayList<String>(); // FP121007
	private List<DiaLabel> ownLabels = new ArrayList<DiaLabel>();
	private List<DiaLabel> inheritedLabels = new ArrayList<DiaLabel>();
	private List<DiaLabel> inferredLabels = new ArrayList<DiaLabel>();
	private boolean abstragt;

	private static void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	private static void log(DiaContainedElement upperClass,
			DiaContainedElement lowerClass, int depth) {
		if (LOG) {
			String uc = "";
			if (upperClass != null){
				uc = upperClass.getClass().getSimpleName() + ":"
						+ upperClass.getName();
				clog(((depth > 0) ? spaces.substring(0, depth * 3) : "") + uc
						+ " <-- " + lowerClass.getClass().getSimpleName() + ":"
						+ lowerClass.getName());
			}/*else
				uc = "(top)";

			clog(((depth > 0) ? spaces.substring(0, depth * 3) : "") + uc
					+ " <-- " + lowerClass.getClass().getSimpleName() + ":"
					+ lowerClass.getName());*/
		}
	}

	protected DiaContainedElement(IDiaParser parser, EModelElement element,
			String view, List<String> labels) {
		this.parser = parser;
		this.eModelElement = element;
		this.view = view;
		this.platformDelegate = parser.getPlatformDelegateProvider()
				.createSpecificPlatform(this);
		this.name_ = ((ENamedElement) eModelElement).getName();
		this.labels = labels; // FP121007
	}

	/*
	 * public EReference getDeclaredContainmentReference_nu_() { return
	 * declaredContainmentReference_nu_nu; }
	 */

	public void addContainerReference(EReference reference) {
		containerReferences.add(reference);
		if (LOG)
			clog("container reference "
					+ ((EClass) reference.eContainer()).getName() + "."
					+ reference.getName() + " added to " + getName());
	}

	private void checkContainment() {
		boolean error = false;
		if (LOG) {
			clog("c_base:"
					+ containmentReferenceBase.getEContainingClass().getName()); // Concept
			clog("c_alt: "
					+ (containmentReferenceAlt_ == null ? "no alt"
							: containmentReferenceAlt_.getEContainingClass()
									.getName())); // Concept

		}
		List<DiaContainedElement> els = parser.getDiagramElements();
		// TODO résoudre avec eContainment
		for (DiaContainedElement element : els) {
			if (element.getEModelElement() == containmentReferenceBase
					.getEContainingClass()
					&& element.getClass() != this.getClass()) {
				error = element.getClass() == DiaGenericElement.class
						&& this.getClass() == DiaLink.class;
				if (error)
					throw new RuntimeException("not allowed:  container of "
							+ getName() + "(" + this.getClass().getSimpleName()
							+ ")" + " is " + element.getName() + "("
							+ element.getClass().getSimpleName() + ")");
			}
		}
	}

	public void cleanMultipleContainment() {
		EClass myClass_ = (EClass) eModelElement;
		for (DiaContainedElement other_ : parser.getDiagramElements()) {
			if (LOG)
				clog(other_.getName());
			for (EReference otherclazref : ((EClass) other_.getEModelElement())
					.getEReferences()) {
				EClass otherClass = (EClass) otherclazref.getEType();
				if ((otherClass).isSuperTypeOf(myClass_)
						&& otherclazref.isContainment()) {
					String m_ = otherclazref.getEContainingClass().getName()
							+ "-(" + otherclazref.getName() + ")->"
							+ myClass_.getName();
					if (otherclazref.getEContainingClass().isSuperTypeOf(
							myClass_))
						m_ = "(selfcontained) " + m_;
				}
			}
		}
	}

	protected void clone(DiaContainedElement other) {
		contention_ = other.contention_;
		containmentReferenceBase = other.containmentReferenceBase;
		containmentReferenceAlt_ = other.containmentReferenceAlt_;
		elementName = other.elementName;
		// label_ = other.label_;
		// defaultName = other.defaultName;
		eModelElement = other.eModelElement;
		parser = other.parser;
		cloneLabels(other);
		errorMessage = other.errorMessage;
		promoted = other.promoted;
		superElements_.addAll(other.superElements_);
		lowerElements.addAll(other.lowerElements);
	}

	private DiaLabel cloneFromSuperElement_(DiaLabel superlabel,
			IDiaContainedElement superElement) { // FP121122x
		;
		DiaLabel copyOfSuper = new DiaLabel(this,
				superlabel.getAttributeName_(), superlabel.isMultiple(), false);
		if (superlabel.getDefaultName() != null
				&& superlabel.getDefaultName().length() > 0)
			copyOfSuper.setDefaultName(superlabel.getDefaultName());
		copyOfSuper.inSuperType_ = true;
		if (superlabel.isMultiple()) {
			copyOfSuper.setEAttributes(superlabel.getEAttributes_());
			copyOfSuper.setAttributeSeparators(superlabel
					.getAttributeSeparators_()); // FP140220
			copyOfSuper.setAttributeNames(superlabel.getAttributeNames());
		} else {
			copyOfSuper.setEAttribute(superlabel.getEAttribute());
		}
		copyOfSuper.setInferred_(superlabel.isInferred());

		if (LOG){
			clog("label " + copyOfSuper.getAttributeName_() + " in "
					+ this.getClass().getSimpleName() + ":" + this.getName()
					+ " inherited from "
					+ superElement.getClass().getSimpleName() + ":"
					+ superElement.getName());
		}
		return copyOfSuper;
	}

	public List<EReference> getAllContainements_nu() {
		return getContainements_(false);
	}

	public DiaNode getCanvasContainment() {
		for (DiaContainedElement other : parser.getDiagramElements())
			for (EReference otherclazref : ((EClass) other.getEModelElement())
					.getEReferences()) {
				boolean containment = (((EClass) otherclazref.getEType())
						.isSuperTypeOf((EClass) eModelElement) && otherclazref
						.isContainment());
				if (containment) {
					DiaContainedElement element = parser
							.findDiagramElement((EClass) otherclazref
									.eContainer());
					if (element != null && (element instanceof DiaNode)
							&& ((DiaNode) element).isCanvas())
						return (DiaNode) element;
				}
			}
		return null;
	}

	public DiaNode getCanvasContainment(EReference ref) {// FP140221
		if (ref.isContainment()) {
			EClass contner = (EClass) ref.eContainer();
			if (LOG)
				clog("IDK " + contner.getName() + "." + ref.getName());
			DiaContainedElement element = parser.findDiagramElement(contner);
			if (element instanceof DiaNode)
				if (element != null && (element instanceof DiaNode)
						&& ((DiaNode) element).isCanvas())
					return (DiaNode) element;
		}
		return null;
	}

	public List<EReference> getContainements(
			List<EReference> otherContainement, boolean direct) {
		String log = "";
		List<EReference> result = new ArrayList<EReference>();
		for (DiaContainedElement other : parser.getDiagramElements())
			for (EReference ref : ((EClass) other.getEModelElement())
					.getEReferences()) {
				boolean alreadycontained = (otherContainement == null ? false
						: otherContainement.contains(ref));
				if (!alreadycontained && ref.isContainment()) {

					boolean inheritedMatch = ((EClass) ref.getEType())
							.isSuperTypeOf((EClass) eModelElement);
					boolean directMatch = ((EClass) ref.getEType()) == ((EClass) eModelElement);
					boolean inherited = !direct;
					boolean containment = false;
					if (direct && directMatch)
						containment = true;
					if (inherited && inheritedMatch)
						containment = true;
					if (inherited && !directMatch && inheritedMatch)
						containment = true;
					if (containment) {
						DiaContainedElement container = parser
								.findDiagramElement((EClass) ref.eContainer());
						if (container != null) {

							log += this.getName() + " contained by Element: "
									+ container.getName() + "-" + ref.getName()
									+ "\n";
							result.add(ref);
						} else
							log += this.getName() + " contained by EClass"
									+ ((EClass) ref.eContainer()).getName()
									+ "-" + ref.getName() + "\n";

					}
				} else if (LOG)
					clog("~");
			}

		if (LOG)
			clog(log);

		return result;
	}

	private List<EReference> getContainements_(boolean direct) {
		String warning = "", log = "";
		List<EReference> result = new ArrayList<EReference>();
		for (DiaContainedElement other : parser.getDiagramElements())
			for (EReference ref : ((EClass) other.getEModelElement())
					.getEReferences()) {
				boolean inheritedMatch = ((EClass) ref.getEType())
						.isSuperTypeOf((EClass) eModelElement);
				boolean directMatch = ((EClass) ref.getEType()) == ((EClass) eModelElement);
				boolean containment = false;
				if (direct && directMatch && ref.isContainment())
					containment = true;
				if (!direct && inheritedMatch && ref.isContainment())
					containment = true;
				if (!direct && inheritedMatch && ref.isContainment()) // FP140327bbb
																		// !directMatch
					containment = true;
				if (containment) {

					DiaContainedElement container = parser
							.findDiagramElement((EClass) ref.eContainer());
					if (container != null) {

						log += this.getName() + " contained by Element: "
								+ container.getName() + "-" + ref.getName()
								+ "\n";

						result.add(ref);
					} else
						log += this.getName() + " contained by EClass"
								+ ((EClass) ref.eContainer()).getName() + "-"
								+ ref.getName() + "\n";

				}
			}
		return result;
	}

	public List<EReference> getContainerReferences() {
		return containerReferences;
	}

	public int getContainmentCount() { // FP131020
		String result = "checkMultipleContainments ";
		int containmentcount_ = 0;
		String log = "";
		if (parser.isDisabled())
			throw new RuntimeException("parser is disabled!");
		String ll = "";
		if (LOG) {
			List<DiaContainedElement> diagramElements = parser
					.getDiagramElements();
			for (DiaContainedElement diaContainedElement : diagramElements)
				ll += diaContainedElement.getName() + " , ";

			clog("isUniqueContainment ? (" + this.getClass().getSimpleName()
					+ ") " + getName() + " - diagramElements are: " + ll);

		}
		String what_ = "";

		EClass myClass_ = (EClass) eModelElement;

		for (DiaContainedElement other_ : parser.getDiagramElements()) {
			if (LOG)
				clog(other_.getName());
			for (EReference otherclazref : ((EClass) other_.getEModelElement())
					.getEReferences()) {
				EClass otherClass = (EClass) otherclazref.getEType();
				if ((otherClass).isSuperTypeOf(myClass_)
						&& getDiagraphContainment(otherclazref) != null) {
					containmentcount_++;
					String m_ = // "checkMultipleContainments ????  "
					otherclazref.getEContainingClass().getName() + "-("
							+ otherclazref.getName() + ")->"
							+ myClass_.getName();
					if (otherclazref.getEContainingClass().isSuperTypeOf(
							myClass_))
						m_ = "(selfcontained) " + m_;
					result += m_ + " ; ";
					if (LOG) {
						what_ += m_ + "\n";
						clog(what_);
					}
					log += m_ + "\n";
				}
			}
		}
		if (containmentcount_ > 1) {
			if (LOG)
				clog("checkMultipleContainments; there are  multiple containments for "
						+ this.getName() + "\n" + log);
			return containmentcount_;
		} else {
			if (LOG)
				clog("checkMultipleContainments; only  " + containmentcount_
						+ " containment for " + this.getName() + "\n" + log);
			return containmentcount_;
		}
	}

	public IDiaContainedElement getContainmentElement() {
		return containmentElement;
	}

	@Override
	public EReference getContainmentReferenceAlt() {
		return containmentReferenceAlt_;
	}

	@Override
	public EReference getContainmentReferenceBase() {// FP131009x
		return containmentReferenceBase;
	}

	public String getContention_() {
		return contention_;
	}

	public DiaNode getDiagraphContainment(EReference ref) {// FP140221
		if (ref.isContainment()) {
			EClass contner = (EClass) ref.eContainer();
			if (LOG)
				clog("IDK " + contner.getName() + "." + ref.getName());
			DiaContainedElement element = parser.findDiagramElement(contner);
			if (element instanceof DiaNode)
				if (element != null && (element instanceof DiaNode)
						&& ((DiaNode) element).containmentExists_(ref))
					return (DiaNode) element;
		}
		return null;
	}

	public List<EReference> getDirectContainements_nu() {
		return getContainements_(true);
	}

	public String getElementName() {
		return elementName;
	}

	@Override
	public EModelElement getEModelElement() {
		return eModelElement;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public List<EModelElement> getInheritedContainmentsAlt() {
		List<EModelElement> result = new ArrayList<EModelElement>();
		for (IDiaContainedElement superelement : getSuperElements()) {
			if (superelement.getContainmentReferenceAlt() != null)
				result.add(superelement.getContainmentReferenceAlt());
		}
		return result;
	}

	public List<EModelElement> getInheritedContainmentsBase() {
		List<EModelElement> result = new ArrayList<EModelElement>();
		for (IDiaContainedElement superelement : getSuperElements()) {
			if (superelement.getContainmentReferenceBase() != null)
				result.add(superelement.getContainmentReferenceBase());
		}
		return result;
	}

	public List<String> getLabels() {
		return labels;
	}

	@Override
	public List<IDiaContainedElement> getLowerElements() {
		return lowerElements;
	}

	// FP120123
	public String getMultipleContainments() {
		String result = "checkMultipleContainments ";
		int containmentcount = 0;
		String log = "";
		if (parser.isDisabled())
			throw new RuntimeException("parser is disabled!");
		String ll = "";
		if (LOG) {
			List<DiaContainedElement> diagramElements = parser
					.getDiagramElements();
			for (DiaContainedElement diaContainedElement : diagramElements)
				ll += diaContainedElement.getName() + " , ";

			clog("isUniqueContainment ? (" + this.getClass().getSimpleName()
					+ ") " + getName() + " - diagramElements are: " + ll);

		}
		String what_ = "";
		EClass myClass_ = (EClass) eModelElement;
		for (DiaContainedElement other_ : parser.getDiagramElements()) {
			if (LOG)
				clog(other_.getName());
			for (EReference otherclazref : ((EClass) other_.getEModelElement())
					.getEReferences()) {
				EClass myClass = (EClass) eModelElement;

				EClass otherClass = (EClass) otherclazref.getEType();
				if ((otherClass).isSuperTypeOf(myClass)
						&& otherclazref.isContainment()) {
					containmentcount++;
					String m = // "checkMultipleContainments ????  "
					otherclazref.getEContainingClass().getName() + "-("
							+ otherclazref.getName() + ")->"
							+ ((EClass) eModelElement).getName() + "\n";
					if (otherclazref.getEContainingClass().isSuperTypeOf(
							myClass_))
						m = "(selfcontained) " + m;
					result += otherclazref.getEContainingClass().getName()
							+ "-(" + otherclazref.getName() + ")->"
							+ ((EClass) eModelElement).getName() + " ; ";
					if (LOG) {
						what_ += m;
						clog(what_);
					}
					log += m;
				}
			}
		}
		if (containmentcount > 1) {
			clog("checkMultipleContainments ???  multiple containments for "
					+ this.getName() + "\n" + log);
			return result;
		} else
			return "DCEpb1";
	}

	public String getName() {
		return name_;
		// if (defaultName != null && defaultName.length() > 0)
		// return defaultName;
		// return ((ENamedElement) eModelElement).getName();
	}

	public IDiaParser getParser() {
		return parser;
	}

	@Override
	public IDiaPlatformDelegate getPlatformDelegate() {
		return platformDelegate;
	}

	@Override
	public List<IDiaContainedElement> getSuperElements() {
		return superElements_;
	}

	public List<IDiaContainedElement> getSuperGenericElements() { // FP121122
		if (superGenericElements == null) {
			superGenericElements = new ArrayList<IDiaContainedElement>();
			for (IDiaContainedElement nod : superElements_) {
				if (nod instanceof DiaGenericElement)
					superGenericElements.add(nod);
			}
		}
		return superGenericElements;
	}

	@Override
	public List<IDiaContainedElement> getSuperNodeElementsOld() { // FP121122
		if (superNodeElements == null) {
			superNodeElements = new ArrayList<IDiaContainedElement>();
			for (IDiaContainedElement nod : superElements_) {
				if (nod instanceof DiaNode && !getSuperGenericElements().contains(nod))
						superNodeElements.add(nod);
				else
					if (LOG)
						clog ("excluded from getSuperNodeElements: "+nod.getName());
			}
		}
		return superNodeElements;
	}


	@Override
	public List<IDiaContainedElement> getSuperNodeElementsNew() { // FP121122
		if (superNodeElements == null) {
			superNodeElements = new ArrayList<IDiaContainedElement>();
			for (IDiaContainedElement nod : superElements_) {
				if (nod instanceof DiaNode && !getSuperGenericElements().contains(nod))
						superNodeElements.add(nod);
				else
					if (LOG)
						clog ("excluded from getSuperNodeElements: "+nod.getName());
			}
		}
		return superNodeElements;
	}

	public String getView() {
		return view;
	}

	public boolean isAbstract() {
		return ((EClass) eModelElement).isAbstract()
				|| ((EClass) eModelElement).isInterface();
	}



	public void setAbstragt() { //FP150329a108
	   this.abstragt = true;
	}

	public boolean isConcrete() {
		return !isAbstract();
	}

	public boolean isPromoted() {
		return promoted;
	}

	private void propagateEcoreInheritance(DiaContainedElement lo) { //FP150515voir
		if (((EClass) this.getEModelElement()).isSuperTypeOf((EClass) lo.getEModelElement()) && lo.getEModelElement()!=this.getEModelElement()) {
		    if (!lo.superElements_.contains(this))
			   lo.superElements_.add(this);
		    if (!this.lowerElements.contains(lo))
			   this.lowerElements.add(lo);
		}
	}

	public void propagateEcoreInheritance() {
		for (DiaContainedElement el : parser.getDiagramElements())
				propagateEcoreInheritance(el);
	}
	private String refToString(EModelElement mel) {
		return ((EReference) mel).getEContainingClass().getName() + "."
				+ ((EReference) mel).getName();
	}

	private List<EClass> subClasses(EClass c) {
		List<EClass> result = new ArrayList<EClass>();
		for (EClassifier eClassif : c.getEPackage().getEClassifiers()) {
			if (eClassif instanceof EClass)
				if (!eClassif.equals(c) && c.isSuperTypeOf((EClass) eClassif))
					result.add((EClass) eClassif);
		}
		return result;
	}

	public EReference resolveContainmentBaseWithName() {// FP140424
		List<String> clazes = new ArrayList<String>();
		for (EReference ref : parser.getAllReferences()) {
			EClass cont = ref.getEContainingClass();
			clazes.add(cont.getName());
			List<EClass> subs = subClasses(cont);
			for (EClass subClass : subs) {
				clazes.add(subClass.getName());
				if (LOG)
					clog("(" + parser.getView() + ") " + subClass.getName());
			}
			for (String claz : clazes) {
				boolean b1 = ((EClass) ref.getEType())
						.isSuperTypeOf((EClass) this.eModelElement);
				boolean b2 = ((EClass) ref.getEType()) == ((EClass) this.eModelElement);

				if ((b1 || b2)

						&& this.contention_ != null // FP2711
						&& this.contention_
								.equals((claz + "." + ref.getName()))) {
					containmentReferenceBase = (EReference) ref;
					checkContainment();
					if (LOG)
						clog("containement for: " + getName() + " is now: "
								+ refToString(containmentReferenceBase));
					return containmentReferenceBase;
				}
			}
		}
		if (contention_ != null)// a contention declared but not found
			throw new RuntimeException(contention_
					+ " containement does not exist for " + getName());
		return null;
	}

	public EReference resolveContainmentBaseWithName_old() {
		for (EReference ref : parser.getAllReferences()) {
			if (((EClass) ref.getEType())
					.isSuperTypeOf((EClass) this.eModelElement)
					&& this.contention_ != null // FP2711
					&& this.contention_.equals((ref.getEContainingClass()
							.getName() + "." + ref.getName()))) {
				containmentReferenceBase = (EReference) ref;
				checkContainment();
				if (LOG)
					clog("containement for: " + getName() + " is now: "
							+ refToString(containmentReferenceBase));
				return containmentReferenceBase;
			}
		}
		if (contention_ != null)
			throw new RuntimeException(contention_
					+ " containement does not exist for " + getName());
		return null;
	}

	public void resolveTargetRef() {
		// nothing in abstract class
	}

	public void setContainmentElement(IDiaContainedElement element) {

		this.containmentElement = element;
		//this.sou
	}

	public void setContainmentReferenceAlt(EReference containmentAlt) { // FP140218
		this.containmentReferenceAlt_ = containmentAlt;
	}

	public void setContainmentReferenceBase(EReference containment) {// FP131009x
		this.containmentReferenceBase = containment;
		if (declaredContainmentReference != null
				&& declaredContainmentReference != containmentReferenceBase) {
			containmentReferenceBase = declaredContainmentReference;
			clog("DCESCR changed " + declaredContainmentReference.getName()); // TODO
																				// validate
		}

	}

	public void setContention_(String contention) {
		this.contention_ = contention;
		if (LOG)
			clog("containement name = " + contention);
	}

	public void setDeclaredContainmentReference(EReference ref) { // FP131009x
		declaredContainmentReference = ref;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	protected void setEModelElement(EModelElement eModelElement) {
		this.eModelElement = eModelElement;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	/*
	 * public void setMapping(Object nodeMapping) { this.nodeMapping =
	 * nodeMapping; //FP140403 }
	 */

	/*
	 * public String getLabel() { return label_; }
	 *
	 * public void setLabel(String label) { this.label_ = label; }
	 */

	public void setPromoted(boolean promoted) {
		this.promoted = true;
	}

	public void setView(String view) {
		this.view = view;
	}

	private String superElementsToString() {
		String result = "";
		List<IDiaContainedElement> suprgs = getSuperGenericElements();
		if (suprgs.size() > 0) {
			result += " super generic elements: ";
			for (IDiaContainedElement supel : suprgs) {
				result += supel.getName() + " ";
			}
		}
		return result;
	}

	public EClass getSemanticRole() {
		return (EClass) getEModelElement();
	}

	public String toString() {
		String result = " name: "
				+ getName()
				+ " elementName: "
				+ elementName
				+ " containmentName: "
				+ contention_
				+ " containment base: "
				+ (contention_ != null ? contention_
						: (containmentReferenceBase != null ? containmentReferenceBase
								.getName() : " null "))
				+ (containmentReferenceAlt_ == null ? " no alt "
						: containmentReferenceAlt_.getName());
		if (eModelElement instanceof EClass)
			result += " modelElement: " + ((EClass) eModelElement).getName();
		else
			result += " modelElement: " + eModelElement.toString();

		result += labelsToString(ownLabels, "ownLabels");
		result += labelsToString(inheritedLabels, "inheritedLabels");
		result += labelsToString(inferredLabels, "inferredLabels");

		result += "labels:";
		if (labels != null)
		  for (String labl : labels)
			result += labl + "; ";
		else
			result += "NULL ";

		result += superElementsToString();
		return result;
	}

	/*-----------*/
	public void addInferredLabel(DiaLabel lb) {
		inferredLabels.add(lb);
	}

	private void logLabel(DiaLabel lb, String mesg) {
		if (DParams.COMPOSITE_LABELS_LOG_) {
			String log = "";
			String ln = lb.getName();
			log += ln + " ";
			String an = lb.getAttributeName_();
			log += an + " ";

			if (lb.getAttributeNames() != null) {
				String[] ans = lb.getAttributeNames();
				for (String _an : ans) {
					log += _an + " ";
				}
			}

			if (lb.getAttributeSeparators_() != null) {

				log += "<";
				String[] seps = lb.getAttributeSeparators_();
				for (String sep : seps) {
					log += sep + " ";
				}

				log += ">";
			}
			cloglabels(mesg+" " + log);
		}
		// ownLabels) {//minTime,maxTime

	}

	private void cloglabels(String mesg) {
		if (DParams.COMPOSITE_LABELS_LOG_)
			System.out.println(mesg);

	}

	public void addOwnLabel(DiaLabel lb) {
		if (DParams.COMPOSITE_LABELS_LOG_)
			logLabel(lb,"addOwnLabel");
		ownLabels.add(lb);
	}

	private void cloneLabels(DiaContainedElement other) {
		ownLabels.addAll(other.ownLabels);
		inheritedLabels.addAll(other.inheritedLabels);
		inferredLabels.addAll(other.inferredLabels);
		labels.addAll(other.labels); // FP140628
	}

	public void createLabelsWithName() {
		for (DiaLabel lb : ownLabels) {// minTime,maxTime
			lb.createWithNames();
			if (LOG) {
				clog(lb.getName() + " " + lb.multipleAsString() + " ["
						+ lb.hashCode() + "]");
			}
		}
	}

	private DiaLabel findInheritedLabel(String name) {
		for (DiaLabel diaLabel : inheritedLabels)
			if (diaLabel.getAttributeName_().equals(name))
				return diaLabel;
		return null;
	}

	public List<DiaLabel> getInferredLabels() {
		return inferredLabels;
	}

	@Override
	public List<IDiaNamedElement> getNamedChildren() {
		List<IDiaNamedElement> allNamedChildren = new ArrayList<IDiaNamedElement>();
		allNamedChildren.addAll(ownLabels);
		allNamedChildren.addAll(inheritedLabels);
		allNamedChildren.addAll(inferredLabels);
		return allNamedChildren;
	}

	private String labelsToString(List<DiaLabel> labels, String lib) {
		String result = "";
		if (labels.size() > 0) {
			result += " " + lib + ": ";
			for (DiaLabel label : labels) {
				result += label.toString()
						+ (label.isMultiple() ? " multiple" : "") + "\n";
			}
		}
		return result;
	}

	public DiaLabel getOwnLabel(String name) {
		for (DiaLabel l : ownLabels) {
			if (l.getAttributeName_().equals(name))
				return l;
		}
		return null;
	}

	public List<DiaLabel> getOwnLabels() {
		return ownLabels;
	}

	public void setOwnLabels(List<DiaLabel> labels) {
		this.ownLabels = labels;
	}

	public Object getOwnLabels_(String attributeNames) { // FP140204
		for (DiaLabel l : ownLabels) {
			if (l.getAttributeName_().equals(attributeNames))
				return l;
		}
		return null;
	}

	public boolean inferLabelIfMissing() {
		if (!DParams.INFER_LABELS_IF_MISSING)
			return false;
		// String[] nameattrs = { "name", "description", "title", "id" };
		if (ownLabels.size() == 0 && inheritedLabels.size() == 0
				&& inferredLabels.size() == 0) {
			if (LOG)
				clog("creating inferred label for " + this.getName());
			EList<EAttribute> attributes = ((EClass) eModelElement)
					.getEAllAttributes();
			for (EAttribute attr : attributes) {
				for (String nameattr : DParams.NAME_ATTRIBUTES)
					if (nameattr.equals(attr.getName().toLowerCase())) {
						parser.createInferredLabel(this, null, attr.getName());
						return true;
					}
			}
			for (EAttribute attr : attributes) {
				for (String nameattr : DParams.NAME_ATTRIBUTES)
					if (attr.getName() != null
							&& attr.getName().toLowerCase().contains(nameattr)) {
						parser.createInferredLabel(this, null, attr.getName());
						return true;
					}
			}

			if (attributes.size() > 0) {
				parser.createInferredLabel(this, null,
						attributes.get(attributes.size() - 1).getName());
				return true;
			}
		}
		return false;
	}



	@Override
	public List<IDiaContainedElement> getAllSuperElementsNew(IDiaContainedElement element){ //FP150514
		List<IDiaContainedElement> result = new ArrayList<IDiaContainedElement>();
		for (IDiaContainedElement sup : element.getSuperNodeElementsNew())
			if (!result.contains(sup))
				 result.add(sup);
		return result;
	}

	@Override
	public List<IDiaContainedElement> getAllSuperElementsOld(IDiaContainedElement element){ //FP150514
		List<IDiaContainedElement> result = new ArrayList<IDiaContainedElement>();
		for (IDiaContainedElement sup : element.getSuperNodeElementsOld())
			if (!result.contains(sup))
				 result.add(sup);
		return result;
	}

	public void propagateAllInheritedLabels() {
		;
		if (LOG)
			clog(getName());
		if (LOG) {
			clog("propagateAllInheritedLabels");
			for (DiaLabel ownLabel : ownLabels) {
				clog(ownLabel.getAttributeName_()
						+ (ownLabel.isMultiple() ? " multiple" : ""));
			}
			for (DiaLabel inheritedLabel : inheritedLabels)
				clog(inheritedLabel.getAttributeName_());
		}


		if(LOG && this.getName().equals("Bouz")){
			clog("superElements for "+this.getName());
			for (IDiaContainedElement superElement : getAllSuperElementsNew(this)){
				clog(superElement.getName());
			}
		}

		for (IDiaContainedElement superElement : getAllSuperElementsNew(this)) { //FP150514
			for (DiaLabel superlabel : superElement.getOwnLabels()) {
				if (findInheritedLabel(superlabel.getAttributeName_()) == null) {
					DiaLabel cloned = cloneFromSuperElement_(superlabel,
							superElement);
					inheritedLabels.add(cloned); // FP121122x
					if (LOG)
						clog("added inherited label:"
								+ superlabel.getAttributeName_());
				}

			}
		}

	}

	public void propagateInheritedLabelsIfNoLabel() {
		if (LOG)
			clog(getName());
		if (ownLabels.size() == 0 && inheritedLabels.size() == 0
				&& inferredLabels.size() == 0)
			for (IDiaContainedElement superElement : getSuperGenericElements()) {
				if (LOG)
					clog(getName());
				if (ownLabels.size() == 0 && inheritedLabels.size() == 0
						&& inferredLabels.size() == 0)
					for (DiaLabel inheritedlabel : superElement.getOwnLabels()) {
						DiaLabel copyOfInherited = new DiaLabel(this,
								inheritedlabel.getAttributeName_(),
								inheritedlabel.isMultiple(), false);
						if (inheritedlabel.getDefaultName() != null
								&& inheritedlabel.getDefaultName().length() > 0)
							copyOfInherited.setDefaultName(inheritedlabel
									.getDefaultName());
						copyOfInherited.inSuperType_ = true;
						copyOfInherited.setEAttribute(inheritedlabel
								.getEAttribute());
						copyOfInherited
								.setInferred_(inheritedlabel.isInferred());
						inheritedLabels.add(copyOfInherited);
						if (LOG)
							clog("label in " + this.getClass().getSimpleName()
									+ ":" + this.getName() + " inherited from "
									+ superElement.getClass().getSimpleName()
									+ ":" + superElement.getName());
					}
			}
	}
}
