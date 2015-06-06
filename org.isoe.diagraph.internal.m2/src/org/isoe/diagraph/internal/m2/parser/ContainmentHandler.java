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
package org.isoe.diagraph.internal.m2.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.isoe.diagraph.internal.api.IDiaNode;
import org.isoe.diagraph.internal.m2.DiaContainedElement;
import org.isoe.diagraph.internal.m2.DiaContainment;
import org.isoe.diagraph.internal.m2.DiaGenericElement;
import org.isoe.diagraph.internal.m2.DiaLink;
import org.isoe.diagraph.internal.m2.DiaNode;
import org.isoe.diagraph.internal.m2.api.IDiaContainedElement;
import org.isoe.diagraph.internal.m2.api.IDiaContainment;
import org.isoe.diagraph.internal.m2.api.IDiaElementVisitor;
import org.isoe.diagraph.internal.m2.api.IDiaParser;
import org.isoe.diagraph.lang.DiagraphKeywords;
import org.isoe.diagraph.parser.api.IDiagraphAssociation;
import org.isoe.diagraph.parser.api.IDiagraphNotation;
import org.isoe.diagraph.parser.api.IDiagraphView;
import org.isoe.diagraph.parser.api.IDiagraphParser;
import org.isoe.diagraph.parser.api.IDiagraphReferenceAssociation;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.utils.DLog;

/**
 *
 * @author pfister
 *
 */
public class ContainmentHandler {
	private static final boolean LOG = DParams.ContainmentHandler_LOG;

	private static final boolean CONTAINMENT = true;
	private IDiaParser parser;
	private List<DiaContainedElement> diaContainedElementsForContainmentInference;
	private List<DiaContainedElement> diaContainedElements;

	// final static boolean DIRECT_CONTAINEMEN = true;
	// final static boolean INHERITED_CONTAINEMEN = false;
	private static final String spaces = "                                                                                                   ";

	private ContainmentPattern containmentPattern;// = new
													// ContainmentPattern(parser);
	// private IDiagraphParser parser2;//
	// =DiagraphAnnotationUtils.getInstance();

	private IDiagraphNotation notation;

	public ContainmentHandler(IDiaParser parser, IDiagraphNotation notation) {
		this.parser = parser;
		this.notation = notation;

		containmentPattern = new ContainmentPattern(parser, notation);
		// parser2 = notation.getParser();//
		// DiagraphAnnotationParser.getInstance();
	}

	void resolveContainmentsBaseWithName() {
		if (LOG)
			clog("resolveContainmentsWithName");
		for (DiaContainedElement el : parser.getDiagramElements()) {
			el.resolveContainmentBaseWithName();
		}
	}

	void resolveContainmentsAlt_() {
		if (LOG)
			clog("resolveContainmentsAlt");
		for (DiaContainedElement el_ : parser.getDiagramElements()) {// FP150518yy
			if (el_ instanceof DiaNode) { // FP140402
				List<IDiagraphReferenceAssociation> as = notation.getParser()
						.getContainmentAssociationsAndSubHierTo(
								(EClass) el_.getEModelElement());
				for (IDiagraphReferenceAssociation association : as) {
					if (hasKrefDirect((EClass) el_.getEModelElement(),
							parser.getView(), association.getTargetReference()
									.getName()))
						el_.setContainmentReferenceAlt(association
								.getTargetReference());
				}
			}
		}
	}

	private boolean hasKrefDirect(EClass eModelElement, String view, String name) {
		return notation.getParser().isAnnotated(eModelElement, name,
				IDiagraphAssociation.AssociationType.TYPED_COMPARTMENT,
				IDiagraphParser.InheritanceType.DIRECT);
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	/*--------------- */

	private boolean isSelfContained_(DiaContainedElement element) {
		List<EReference> icontainements = notation.getParser()
				.getDirectContainementReferences(
						(EClass) element.getEModelElement());
		for (EReference eReference : icontainements) {
			EClass rt = (EClass) (eReference.getEType());
			if (rt == element.getEModelElement())
				return true;
		}
		return false;
	}

	void inferNodeContainments() {
		if (LOG)
			clog("inferContainments");
		for (DiaContainedElement el : parser.getDiagramElements()) {
			if (el.isAbstract()) // FP120123
				if (LOG)
					clog(el.getName() + " is abstract...");
			List<EReference> allcontainements = notation.getParser()
					.parseAllContainementReferences(
							(EClass) el.getEModelElement());
			if ((el instanceof DiaNode) && el.getCanvasContainment() == null
					&& allcontainements.size() > 0
					&& ((DiaNode) el).getSources().size() == 0) {
				if (LOG)
					clog("mark as containment " + ((DiaNode) el).getName());
				((DiaNode) el).markAsContainment();

				if (LOG)
					clog(el.getName()
							+ " should be in a containmentt... is marked");
			}
		}
	}

	void resolveContainmentNode() {
		if (LOG) {
			clog("---resolveContainmentNode---");
			for (DiaNode containerNode : parser.getAllNodes())
				clog("-" + containerNode.getName());

		}

		for (DiaNode containerNode : parser.getAllNodes()) {
			;
			for (DiaNode containedNode : parser.getAllNodes()) {
				if (containedNode.getContainmentReferenceBase() != null) {
					EReference ccref = containedNode
							.getContainmentReferenceBase();

					EClass eclaz = ((EReference) containedNode
							.getContainmentReferenceBase())
							.getEContainingClass();
					if (containerNode.getEModelElement() == eclaz) {


						containedNode.setContainmentElement(containerNode);




						if (LOG) {
							clog("[(<"
									+ containerNode.getName()
									+ ".(cbase)"
									+ ((EReference) containedNode
											.getContainmentReferenceBase())
											.getName()
									+ ".(calt)"
									+ ((EReference) containedNode
											.getContainmentReferenceAlt() == null ? "null"
											: (((EReference) containedNode
													.getContainmentReferenceAlt())
													.getName())
													+ " "
													+ (containerNode.isCanvas() ? " canvas "
															: "")
													+ " --> "
													+ containedNode.getName()
													+ ">)]"));
							clog(containedNode.getName() + ".containment="
									+ containerNode.getName());
						}
					}
				}
			}
		}
	}

	void validateContainment() {
		if (!checkAllContainments())
			throw new RuntimeException(parser.getErrorMessage());
	}

	private boolean checkAllContainments() {
		int count = 0;
		if (LOG)
			clog("---checkAllContainments---");
		for (DiaNode nod : parser.getAllNodes()) {

			List<EReference> contrefs = notation.getParser()
					.getDirectContainementReferences(
							(EClass) nod.getEModelElement());

			if (contrefs.size() == 0)
				contrefs = notation.getParser().parseAllContainementReferences(
						(EClass) nod.getEModelElement());
			if (contrefs.size() == 0) {
				if (LOG)
					clog(nod.getName() + (nod.isCanvas() ? " (canvas) " : " ")
							+ " has no containment");
				if (!(nod.isCanvas() && !nod.isDiagramRecursive())) {
					if (LOG)
						clog(nod.getName() + " has no containment !!!");
					if (DParams.THROW_CONTAINMENT_ERRORS) {
						count++;
						parser.addErrorMessage(nod.getName()
								+ " has no containment");
					} else if (DParams.WARN_CONTAINMENT_ERRORS) {
						DLog.addWarning(parser.getErrorMessage());
						if (LOG)
							clog(parser.getErrorMessage());
					}
				}
			}
		}
		return count == 0;
	}

	void resolveContainmentReferences() {
		;
		if (LOG)
			clog("---resolveContainmentReferences---");
		for (DiaNode containerNode : parser.getAllNodes()) {
			for (DiaNode containedNode : parser.getAllNodes()) {
				for (EReference containerReference : ((EClass) containerNode
						.getEModelElement()).getEReferences()) {

					if (((EClass) containerReference.getEType())
							.isSuperTypeOf((EClass) containedNode
									.getEModelElement())
							&& containerReference.isContainment()) {
						if (!containedNode.getContainerReferences().contains(
								containerReference)) { // FP2811
							if (containerNode.isOrSubNodeCanvas()) // FP140201
								containerNode.addRootChild(containedNode);
						}
					}
				}
			}
		}
	}

	public void resolveContainmentReferences_test() { // FP130806xy
		;
		if (LOG)
			clog("---resolveContainmentReferences---");
		for (DiaNode containerNode : parser.getAllNodes()) {
			for (DiaNode containedNode : parser.getAllNodes()) {
				for (EReference ref : ((EClass) containerNode
						.getEModelElement()).getEReferences()) {
					if (ref.isContainment()) {

						EClass ecltrg = (EClass) ref.getEType();
						EClass ecld = (EClass) containedNode.getEModelElement();

						if (ref.getName().equals("totos")
								&& ecltrg.getName().equals("TargetElement")
								&& ecld.getName().equals("TotoSet")) {
							if (!containedNode.getContainerReferences()
									.contains(ref))
								clog("missing " + ecld.getName() + "--"
										+ ref.getName() + "-->"
										+ ecltrg.getName());
						}

						if (ref.getName().equals("totos")
								&& ecltrg.getName().equals("TargetElement")
								&& ecld.getName().equals("TotoSet")) {
							if (!ecltrg.isSuperTypeOf(ecld))
								clog("targ: "
										+ ecltrg.getName()
										+ (ecltrg.isAbstract() ? " abstr "
												: " concr ")
										+ " is not SuperTypeOf "
										+ "contd: "
										+ ecld.getName()
										+ (ecld.isAbstract() ? " abstr "
												: " concr "));
						}

						if (ref.getName().equals("totos")
								&& ecltrg.getName().equals("TargetElement")
								&& ecld.getName().equals("TotoSet")) {
							if (ecltrg.isSuperTypeOf(ecld))
								clog("targ: "
										+ ecltrg.getName()
										+ (ecltrg.isAbstract() ? " abstr "
												: " concr ")
										+ " is SuperTypeOf "
										+ "contd: "
										+ ecld.getName()
										+ (ecld.isAbstract() ? " abstr "
												: " concr "));
						}

						if (!containedNode.getContainerReferences().contains(
								ref))
							if (ecltrg.isSuperTypeOf(ecld))
								clog("targ: "
										+ ecltrg.getName()
										+ (ecltrg.isAbstract() ? " abstr "
												: " concr ")
										+ " isSuperTypeOf "
										+ "contd: "
										+ ecld.getName()
										+ (ecld.isAbstract() ? " abstr "
												: " concr "));
						clog(ecld.getName() + "--" + ref.getName() + "-->"
								+ ecltrg.getName());

						if (ecltrg.isSuperTypeOf(ecld)) {
							// if
							// (!containedNode.getContainerReferences().contains(containerReference))
							// { // FP2811

							addContentionIfMissing(containerNode,
									containedNode, ref);

							/*
							 * containedNode.addContainerReference(
							 * containerReference); if
							 * (containerNode.isCanvas())
							 * containerNode.addRootChild(containedNode);
							 */
						}
						// }
					}
				}
			}
		}
	}

	private void addContentionIfMissing(DiaNode containerNode,
			DiaNode containedNode, EReference containerReference) { // FP130806
		if (!containedNode.getContainerReferences()
				.contains(containerReference)) {
			containedNode.addContainerReference(containerReference);
			if (containerNode.isOrSubNodeCanvas()) // FP140201
				containerNode.addRootChild(containedNode); // TotoSet--elementSet-->TotoSet
		}
	}

	void checkContainments() {
		for (DiaNode nod : parser.getAllNodes()) {
			if ((nod.getContainmentElement() == null && !(nod.isCanvas() && !nod
					.isDiagramRecursive())))
				if (LOG)
					clog(nod.getName() + " has no containment ??");
		}
	}

	void checkTargetContainments() { // FP120126 //FP151318t
		for (DiaNode node : parser.getAllNodes()) {
			for (IDiaContainment diaContainment : node.getContainments()) {
				DiaContainedElement targetElement = null;
				EReference targref = diaContainment.getTargetReference();
				if (targref != null)
					targetElement = parser.findDiagramElement(targref
							.getEReferenceType());
				if (targetElement == null) {
					String mesg = "transformContainments: targetElement is null for node "
							+ node.getName();
					if (DParams.ASSERT_NOT_NULL_CONTAINEMENT_TARGET)
						throw new RuntimeException(mesg);
				}
			}
		}
	}

	void logContainments1() {
		if (LOG)
			for (DiaNode nod : parser.getAllNodes()) {
				String nodContainement = nod.getContainmentReferenceBase() != null ? "nod.getContainmentBase:"
						+ ((ENamedElement) nod.getContainmentReferenceBase())
								.getName()
						: "no nod.getContainment ";// ((ENamedElement)nod.getContainment()).getName();

				nodContainement += nod.getContainmentReferenceAlt() != null ? "nod.getContainmentAlt:"
						+ ((ENamedElement) nod.getContainmentReferenceAlt())
								.getName()
						: "no nod.getContainmentAlt ";// ((ENamedElement)nod.getContainment()).getName();

				String krefstr = "nod.getContainerReferences: ";
				List<EReference> krefs = nod.getContainerReferences();
				for (EReference k : krefs) {
					krefstr += ((EClass) k.eContainer()).getName() + "."
							+ k.getName() + " (" + k.getEType().getName() + ")";
				}
				String conteinm = "nod.getContainmentElement: "
						+ (nod.getContainmentElement() != null ? nod
								.getContainmentElement().getName()
								: "ContainmentElement ?");
				clog("[[[[|");
				clog("nod=" + nod.getName());
				clog(conteinm);
				clog(nodContainement);
				clog(krefstr);
				clog("|[[[[");
			}
	}

	void logContainments() {

		if (!(LOG))
			return;
		clog("log ContainerReferences");

		// if (containment.getSourceNode().getName().equals("Bar") &&
		// ((EReference)containment.getSourceNode().getContainmentReference()).getName().equals("bar"))
		// tb = true; //FP140217
		for (DiaNode nod : parser.getAllNodes()) {
			if (nod.getContainerReferences().size() > 1) {
				clog(" Warning !!! " + nod.getName()
						+ " has more than one container:");
				for (EReference k : nod.getContainerReferences()) {
					clog("    " + ((EClass) k.eContainer()).getName() + "."
							+ k.getName() + " (" + k.getEType().getName() + ")");
				}
			}
		}

		for (DiaNode nod : parser.getAllNodes()) {
			if (nod.getContainerReferences().size() > 1) {
				clog(" Warning !!! " + nod.getName()
						+ " has more than one container:");
				for (EReference k : nod.getContainerReferences()) {
					clog("    " + ((EClass) k.eContainer()).getName() + "."
							+ k.getName() + " (" + k.getEType().getName() + ")");
				}
			}
		}
		for (DiaNode nod : parser.getAllNodes()) {
			if (nod.isCanvas()) {
				clog(" Canvas root children for " + nod.getName());
				List<IDiaNode> rootch = nod.getRootChildren();
				for (IDiaNode diaNode : rootch) {
					clog(diaNode.getName());
				}
			}
			break;
		}
	}

	private List<DiaContainedElement> getdiaContainedElementsForContainmentInference_() {

		if (diaContainedElementsForContainmentInference == null) {
			diaContainedElementsForContainmentInference = new ArrayList<DiaContainedElement>();
			if (LOG)
				clog("getDiaContainedElementsForContainmentInference");
			for (DiaContainedElement diaContainedElement : parser
					.getDiagramElements()) { // FP130806
				if (!diaContainedElement.isAbstract()
						&& diaContainedElement.getContainmentReferenceBase() == null
						&& parser
								.isDianodeOrRecursiveCanvasOrDialinkOrGeneric(diaContainedElement)) {
					if (LOG)
						clog("must infer containment for: "
								+ diaContainedElement.getName());
					diaContainedElementsForContainmentInference
							.add(diaContainedElement);
				} else if (LOG)
					clog("not infer containment for: "
							+ diaContainedElement.getName());
			}
		}
		return diaContainedElementsForContainmentInference;
	}

	private List<DiaContainedElement> getDiaContainedElements_() {

		if (diaContainedElements == null) {
			diaContainedElements = new ArrayList<DiaContainedElement>();
			for (DiaContainedElement diaContainedElement : parser
					.getDiagramElements()) { // FP130806
				if (!diaContainedElement.isAbstract()
						&& parser
								.isDianodeOrRecursiveCanvasOrDialinkOrGeneric(diaContainedElement)) {
					diaContainedElements.add(diaContainedElement);
				}
			}
		}
		return diaContainedElements;
	}

	private List<DiaContainedElement> getdiaContainedElementsForContainmentInferencenew_nu_() {

		if (diaContainedElementsForContainmentInference == null) {
			diaContainedElementsForContainmentInference = new ArrayList<DiaContainedElement>();
			if (LOG)
				clog("getDiaContainedElementsForContainmentInference");
			for (DiaContainedElement elr : parser.getDiagramElements()) {

				if (elr.getName().equals("TargetElement")) { // FP130806xx

					if (!elr.isAbstract() && // FP120123

							elr.getContainmentReferenceBase() == null
							&& parser
									.isDianodeOrRecursiveCanvasOrDialinkOrGeneric(elr)) {
						if (LOG)
							clog("must infer containment for: " + elr.getName());

						diaContainedElementsForContainmentInference.add(elr);
					} else if (LOG)
						clog("not infer containment for: " + elr.getName());
				}
			}
		}
		return diaContainedElementsForContainmentInference;
	}

	private String getContainments__no(DiaNode de) {
		String result = "";
		// DiaNode de=(DiaNode) diagramElement;
		EClass claz = (EClass) de.getEModelElement();
		EPackage pak = claz.getEPackage();
		List<EClassifier> classifs = pak.getEClassifiers();
		for (EClassifier eClassifier : classifs) {
			if (eClassifier instanceof EClass) {
				for (EReference eReference : ((EClass) eClassifier)
						.getEAllReferences()) {
					if (eReference.getEType() == claz) {
						// if (eReference.isContainer())
						result += ((EClass) eClassifier).getName() + "."
								+ eReference.getName() + " ; ";
					}
				}
			}
		}
		return result;
	}

	public void cleanMultipleContainements() {

		if (LOG)
			clog("cleanMultipleContainements");

		List<DiaContainedElement> toClean = new ArrayList<DiaContainedElement>();
		for (DiaContainedElement diagramElement : getDiaContainedElements_()) {
			EClass myClass = (EClass) diagramElement.getEModelElement();

			if (!diagramElement.isAbstract()) {

				int conts = diagramElement.getContainmentCount(); // FP131020
																	// //
																	// FP130929
				if (conts > 1) {
					toClean.add(diagramElement);// diagramElement.cleanMultipleContainment();
				}
			} else if (LOG)
				clog("checking multiple containement ... "
						+ diagramElement.getName() + " is abstract");

		}
		for (DiaContainedElement cln : toClean) {
			cln.cleanMultipleContainment();
		}

	}

	private void handleMultipleContainment(DiaContainedElement diagramElement) { // FP131020
		boolean nodeIsInContainment = false;
		if (diagramElement instanceof DiaNode)
			if (((DiaNode) diagramElement).getSources().size() > 0)
				nodeIsInContainment = true;
		if (!nodeIsInContainment || DParams.CONTAINMENT_CHECK_STRICT) {
			String multipleconts = diagramElement.getMultipleContainments();
			/*
			 * DLog.addWarning("multiple containments for:" +
			 * diagramElement.getName() + " :specify unique containment " + "["
			 * + multipleconts + "]");
			 * clog("!! warning: multiple containments for:" +
			 * diagramElement.getName() + " :specify unique containment !! " +
			 * "[" + multipleconts + "]");
			 */

		}
	}

	public void checkMultipleContainementsForContainmentInference() {
		if (LOG)
			clog("checkMultipleContainementsForContainmentInference");
		for (DiaContainedElement diagramElement : getdiaContainedElementsForContainmentInference_()) {
			if (!diagramElement.isAbstract()) {
				int conts = diagramElement.getContainmentCount(); // FP130929
				if (conts > 1)
					handleMultipleContainment(diagramElement);
			} else if (LOG)
				clog("checking multiple containement ... "
						+ diagramElement.getName() + " is abstract");
		}
	}

	void logDomainContainments() {
		if (!(LOG))
			return;
		clog("logDomainContainments");
		for (DiaContainedElement diagramElement : parser.getDiagramElements()) {
			clog("domain containments for " + diagramElement.getName());

			for (EModelElement mel : diagramElement
					.getInheritedContainmentsBase()) {
				clog("inherited domain containment base for "
						+ diagramElement.getName() + ": "
						+ ((EReference) mel).getEContainingClass().getName()
						+ "." + ((EReference) mel).getName());
			}

			for (EModelElement mel : diagramElement
					.getInheritedContainmentsAlt()) {
				clog("inherited domain containment alt for "
						+ diagramElement.getName() + ": "
						+ ((EReference) mel).getEContainingClass().getName()
						+ "." + ((EReference) mel).getName());
			}

			EModelElement directk_ = diagramElement
					.getContainmentReferenceBase();
			if (directk_ != null)
				clog("direct domain containment for "
						+ diagramElement.getName()
						+ ": "
						+ ((EReference) directk_).getEContainingClass()
								.getName() + "."
						+ ((EReference) directk_).getName());

			if (directk_ == null
					&& diagramElement.getInheritedContainmentsBase().size() == 0)
				clog("no domain containment base for: "
						+ diagramElement.getName());

			if (diagramElement.getInheritedContainmentsAlt().size() == 0)
				clog("no domain containment alt for: "
						+ diagramElement.getName());

			EModelElement altk = diagramElement.getContainmentReferenceAlt();
			if (altk != null)
				clog("alternate domain containment for "
						+ diagramElement.getName() + ": "
						+ ((EReference) altk).getEContainingClass().getName()
						+ "." + ((EReference) altk).getName());
			if (altk == null)
				clog("no alternate domain containment for: "
						+ diagramElement.getName());
		}
	}

	void logContainedElementTree(IDiaElementVisitor visitor,
			IDiaContainedElement container, boolean generic, int level) {
		level++;
		String sp = level > 0 ? spaces.substring(0, 3 * level) : "";
		for (IDiaContainedElement diaContainedElement : container
				.getLowerElements()) {
			visitor.visit(container, diaContainedElement, level);
			if (!generic
					|| (generic && diaContainedElement instanceof DiaGenericElement)) {
				logContainedElementTree(visitor, diaContainedElement, generic,
						level);
			}
		}
	}

	void visitContainedElementTree(IDiaElementVisitor visitor,
			IDiaContainedElement container, int level) {
		level++;
		for (IDiaContainedElement diaContainedElement : container
				.getLowerElements()) {
			visitor.visit(container, diaContainedElement, level);
			visitContainedElementTree(visitor, diaContainedElement, level);
		}
	}

	public EReference findContainmentReference_bad_nu(DiaContainedElement elem) { // FP140114xxxxx
		EClass thisclass = (EClass) (elem.getEModelElement());
		for (DiaContainedElement other : parser.getDiagramElements()) {
			if (other != elem) {
				EClass otherclass = (EClass) (other.getEModelElement());
				for (EReference otherReference : otherclass.getEAllReferences()) {
					if (otherReference.getEType() == thisclass
							&& otherReference.isContainment()) {
						if (LOG)
							clog("Element " + elem.getElementName()
									+ " has a container :"
									+ otherclass.getName() + "."
									+ otherReference.getName());
						return otherReference;
					}
				}
			}
		}
		return null;
	}

	public String parseNodes(String view) {
		String result = "";
		if (LOG)
			clog("----------  parse for " + view);
		for (DiaContainedElement diagramElement : getdiaContainedElementsForContainmentInference_()) {
			// name=diagramElement.getName()
			if (diagramElement instanceof DiaNode) // FP140414
				// if (diagramElement instanceof DiaNode || diagramElement
				// instanceof DiaGenericElement)
				result += containmentPattern.parseNode(diagramElement, view); // FP140409xxxyyy
		}
		return result;

	}

	String parseLinks(String view) {
		String result = "";
		List<DiaContainedElement> infer = getdiaContainedElementsForContainmentInference_();
		// FP150518yy
		if (LOG) {
			clog("----------  parse Linksfor " + view);
			clog("inferContainements for");
			for (DiaContainedElement diagramElement : infer) {
				clog(diagramElement.getName());
			}

		}

		for (DiaContainedElement diagramElement : infer) {

			// if (diagramElement instanceof DiaLink || diagramElement
			// instanceof DiaGenericElement) //FP140413
			// result+=containmentPattern.parseLink__(diagramElement, view);
			// //FP140409xxxyyy

			// if (diagramElement instanceof DiaLink || diagramElement
			// instanceof DiaGenericElement);
			/*
			 * if (diagramElement instanceof DiaNode){ //FP140503
			 * inferNodeContainment((DiaNode) diagramElement, view); }
			 */

			if (diagramElement instanceof DiaLink) // FP140503
				inferLinkContainment((DiaLink) diagramElement, view);
			else if (diagramElement instanceof DiaNode) // FP140503
				inferNodeContainment((DiaNode) diagramElement, view);

			// if (LOG)
			if (diagramElement.getContainmentReferenceBase() == null) { //FP150522 cerror replaced with exception
				if (diagramElement instanceof DiaGenericElement) // may
																	// happen
					throw new RuntimeException("containement for DiaGenericElement: "
							+ diagramElement.getName() + " is null for view "
							+ view);
				else if (diagramElement.isAbstract())
					throw new RuntimeException("containement for abstract : "
							+ diagramElement.getName() + " is null for view "
							+ view);
				else
					throw new RuntimeException("containement for : "
							+ diagramElement.getName() + " is null for view "
							+ view);
			}

		}
		return result;
	}

	/************** --------------******************--------------------- *****************/

	/*--------------------------------------------------*/

	private boolean isTopNode_tomove(DiaContainedElement element, String view) {
		// FP140401aa
		for (EReference eReference : notation.getParser()
				.getDirectContainementReferences(
						(EClass) element.getEModelElement())) {
			// FP140401 for (EReference eReference :
			// element.getDirectContainements()) {
			DiaNode diaNode = parser
					.findNode(((EClass) eReference.eContainer()).getName());
			if (diaNode != null) {

				if (diaNode.isCanvas(view)) {
					if (LOG)
						clog("contained (1) by canvas " + diaNode.getName());
					return true;
				}
			}
		}

		DiaNode canvas = element.getCanvasContainment();
		if (canvas != null) {
			if (LOG)
				clog("contained (2) by canvas " + canvas.getName());
			return true;
		}

		return false;
	}

	private boolean isContainedThroughInheritance_tomov( // FP150518voir
			DiaContainedElement element) {
		if (isSelfContained_(element))
			return false;
		List<EReference> sterr100s = sterr100PatternMultipleContentionThroughInheritance_tomov(element);
		return sterr100s != null;
	}

	private List<EReference> sterr100PatternMultipleContentionThroughInheritance_tomov(
			DiaContainedElement element) {
		// FP140401aa
		List<EReference> allContainements = notation.getParser()
				.parseAllContainementReferences(
						(EClass) element.getEModelElement());
		for (EReference eReference : allContainements) {
			if (LOG)
				clog(" sterr100Pattern " + eReference.getName());
			EClass rt = (EClass) (eReference.getEType());
			DiaNode dn = parser.findNode(rt.getName());
			if (dn != null
					&& rt.isSuperTypeOf((EClass) element.getEModelElement())
					&& rt != element.getEModelElement())
				return allContainements;
		}
		return null;
	}

	private boolean isContainedThroughInheritance(DiaLink link) {
		List<EReference> conts = upperContainment(link);
		return conts != null;
	}

	private List<EReference> upperContainment(DiaLink link) {
		List<EReference> allContainements = notation.getParser()
				.getSuperReferencesTo((EClass) link.getEModelElement());
		for (EReference eReference : allContainements) {
			if (LOG)
				clog(" upperContainment " + eReference.getName());
			EClass rt = (EClass) (eReference.getEType());
			DiaNode dn = parser.findNode(rt.getName());
			if (dn != null
					&& rt.isSuperTypeOf((EClass) link.getEModelElement())
					&& rt != link.getEModelElement())
				return allContainements;
		}
		return null;
	}

	private List<EReference> containment(DiaLink link) {

		List<EReference> allContainements = notation.getParser()
				.getSimpleReferencesTo_((EClass) link.getEModelElement());
		for (EReference eReference : allContainements) {
			if (LOG)
				clog(" upperContainment " + eReference.getName());
			EClass rt = (EClass) (eReference.getEType());
			DiaNode dn = parser.findNode(rt.getName());
			if (dn != null
					&& rt.isSuperTypeOf((EClass) link.getEModelElement())
					&& rt != link.getEModelElement())
				return allContainements;
		}
		return null;
	}

	// in case of multiple containments
	private EReference getDeclaredContainment_tomove(
			DiaContainedElement element, String view) { // FP131009x
		EReference containment = null;
		try {
			containment = parseContainmentReferenceLate_nu_tomove(
					(EClass) element.getEModelElement(), view); // FP131008
		} catch (Exception e) {
			throw new RuntimeException("error in getDeclaredContainment");
		}
		if (containment != null) { // FP131008
			if (LOG)
				clog("declared containment found " + containment + " for view "
						+ view);
		}
		return containment; // FP131008
	}



	private void inferLinkContainment(DiaLink link, String view) {
		List<EReference> simplecontainements = notation.getParser()
				.getSimpleReferencesTo_((EClass) link.getEModelElement());
		List<EReference> direfs_ = notation.getParser().getDirectReferencesTo(
				(EClass) link.getEModelElement());
		List<EReference> allrefs = notation.getParser().getAllReferencesTo(
				(EClass) link.getEModelElement());
		if (simplecontainements.size() == 0 && direfs_.size() == 0
				&& allrefs.size() == 0) { // FP140423xxx
			parser.cerror("(8)no containment for link " + link.getName()
					+ " in view " + view);
			return;
		} else {
			if (DParams.Parser_CONTAINMENT2_LOG) {
				clog2("simple containments for " + link.getName());
				for (EReference containement : simplecontainements)
					clog2(containement.getName());

				clog2("direct containements for " + link.getName());
				for (EReference containement : direfs_)
					clog2(containement.getName());

				clog2("inherited containements for " + link.getName());
				for (EReference containement : direfs_)
					clog2(containement.getName());

			}
		}
		boolean hasContainements = simplecontainements.size() > 0;
		boolean isContainedInheritance = isContainedThroughInheritance(link);

		List<EReference> conts = simplecontainements;
		if (conts.isEmpty())
			conts = direfs_;
		if (conts.isEmpty())
			conts = allrefs;

		for (Iterator contiterator = conts.iterator(); contiterator.hasNext();) { //FP150520
			EReference eReference = (EReference) contiterator.next();
			if (!eReference.isContainment())
				contiterator.remove();
		}

		if (conts.size() == 1)
			inferLinkContainment(link, conts, view);
		else
			throw new RuntimeException("unable to infer containment for link "
					+ link.getName());

	}

	private void inferNodeContainment(DiaNode node, String view) { // FP140503zz
																	// //
																	// FP140218

		List<EReference> containements = notation.getParser()
				.getDirectContainementReferences(
						(EClass) node.getEModelElement());
		List<EReference> allContainements = notation.getParser()
				.parseAllContainementReferences(
						(EClass) node.getEModelElement());

		if (containements.size() == 0 && allContainements.size() == 0) { // FP140423xxx
			parser.cerror("(8)no containment for node " + node.getName()
					+ " in view " + view);
			return;
		} else {
			if (DParams.Parser_CONTAINMENT2_LOG) {
				clog2("containments for " + node.getName());
				for (EReference containement : containements)
					clog2(containement.getName());

				clog2("allContainements for " + node.getName());
				for (EReference containement : allContainements)
					clog2(containement.getName());

			}
		}
		boolean hasContainements = containements.size() > 0;
		boolean isTopNode = isTopNode_tomove(node, view);
		boolean isContainedThroughInheritance = isContainedThroughInheritance_tomov(node);
		boolean isSelfContained = isSelfContained_(node);

		if (hasContainements && isTopNode && isContainedThroughInheritance
				|| isSelfContained)
			if (inferContainmentAlt_tomove(node, containements, view)) // FP150517voir
				return;
		if (containements.size() == 0)
			containements = allContainements;
		if (containements.size() == 1)
			inferContainmentSimple_tomove(node, containements, view);
		else
			inferContainmentMultiple_tomove(node, containements, view);
	}

	private void clog2(String mesg) {
		if (DParams.Parser_CONTAINMENT2_LOG)
			System.out.println(mesg);
	}

	private boolean inferContainmentAlt_tomove(DiaContainedElement element,
			List<EReference> containements, String view) { // FP140218


		try {
			EReference eref = null;
			List<EReference> stp = sterr100PatternMultipleContentionThroughInheritance_tomov(element);
			if (stp == null)
				return false;
			for (EReference eReference : stp)
				if (eReference != containements.get(0))
					eref = eReference;
			if (eref == null)
				return false;// FP150518
			stp.remove(containements.get(0));
			EReference r0 = containements.get(0);
			EReference r1 = eref;
			EClass cl0 = r0.getEContainingClass();
			EClass cl1 = r1.getEContainingClass();
			DiaNode d0 = (DiaNode) parser.findDiagramElement(cl0);
			DiaNode d1 = (DiaNode) parser.findDiagramElement(cl1);
			DiaNode d3 = (DiaNode) element;

			// FP150518yy
			if (d0.isCanvas(view))
				inferContainments_tomove(element, r0, r1);
			else if (d1.isCanvas(view))
				inferContainments_tomove(element, r1, r0);
			else if (((EClass) d0.getEModelElement())
					.isSuperTypeOf(((EClass) d3.getEModelElement())))
				inferContainments_tomove(element, r1, r0); // FP150517
			else if (((EClass) d1.getEModelElement())
					.isSuperTypeOf(((EClass) d3.getEModelElement())))
				inferContainments_tomove(element, r0, r1);

			else {
				parser.cerror("should not happen in inferContainmentAlt");
				return false;
			}
			// inferContainments(element, containements.get(0), eref);
		} catch (Exception e) {
			parser.cerror("(1)err while inferContainmentAlt");
			return false;
		}
		return true;
	}

	private void inferContainments_tomove(DiaContainedElement element,
			EReference containement, EReference containementAlt) {
		element.setContainmentReferenceBase(containement);
		element.setContainmentReferenceAlt(containementAlt);
	}

	// FP140503
	private void inferLinkContainment(DiaLink link,
			List<EReference> containements, String view) {// FP140503
		link.setContainmentReferenceBase(containements.get(0));
		EReference declaredcontRef = getDeclaredContainment_tomove(link, view);
		EReference contRef = (EReference) link.getContainmentReferenceBase();
		if (declaredcontRef != null && declaredcontRef != contRef) { // FP131009x
			if (LOG)
				clog("containing reference is now " + declaredcontRef.getName()
						+ " vs " + contRef.getName());
			contRef = declaredcontRef;
			link.setContainmentReferenceBase(contRef);
		}
	}

	private void inferContainmentSimple_tomove(DiaNode element,
			List<EReference> containements, String view) {
		element.setContainmentReferenceBase(containements.get(0));
		EReference declaredcontRef = getDeclaredContainment_tomove(element,
				view);
		EReference contRef = (EReference) element.getContainmentReferenceBase();
		if (declaredcontRef != null && declaredcontRef != contRef) { // FP131009x
			if (LOG)
				clog("containing reference is now " + declaredcontRef.getName()
						+ " vs " + contRef.getName());
			contRef = declaredcontRef;
			element.setContainmentReferenceBase(contRef);
		}
	} // FP140218b

	private void inferContainmentMultiple_tomove(DiaContainedElement element,
			List<EReference> containements, String view) {
		EReference declaredcontRef = getDeclaredContainment_tomove(element,
				view);
		if (declaredcontRef != null) { // FP131008
			element.setContainmentReferenceBase(declaredcontRef);
			if (LOG)
				clog("late containment found " + declaredcontRef + " for view "
						+ view); // TODO validate this
		} else {

			// IDiagraphReferenceAssociation cp =
			// parser2.getTopLevelAssociation((EClass)
			// element.getEModelElement(), view);
			IDiagraphReferenceAssociation containment = notation.getParser()
					.getBaseContainment((EClass) element.getEModelElement());
			if (containment != null)
				element.setContainmentReferenceBase(containment
						.getTargetReference());
			else
				containerError_tomove(element, containements);

		}
	} // FP140218b

	private void containerError_tomove(DiaContainedElement element,
			List<EReference> containements) {
		String conts = "";
		for (EReference containr : containements)
			conts += ((EClass) containr.eContainer()).getName() + "."
					+ containr.getName() + " ; "; // FP130929
		throw new RuntimeException("containment error for: "
				+ element.getName() + " : " + conts
				+ " - add a discriminant <cont> annotation to "
				+ element.getName() + " for each view");
	}

	private int isDiagraphDefaultView_tomove(EAnnotation annot) {
		if (annot.getSource().equals(DiagraphKeywords.ANNOT_DIAGRAPH_LITTERAL)) {
			for (Map.Entry<String, String> entry : annot.getDetails()) {
				if (entry.getKey().startsWith(DiagraphKeywords.VIEW_EQ))
					return 0; // a view but not default
			}
			return 1; // default view
		}
		return -1; // not a diagraph annotation
	}

	private boolean isDiagraphView_tomove(EAnnotation annot, String view) {
		String viewname = "default";// FP121010
		if (annot.getSource().equals(DiagraphKeywords.ANNOT_DIAGRAPH_LITTERAL)) {
			for (Map.Entry<String, String> entry : annot.getDetails()) {
				if (entry.getKey().startsWith(DiagraphKeywords.VIEW_EQ)) {
					String[] pars = entry.getKey().split("=");
					viewname = pars[1];
				}
			}
			return view.equals(viewname);
		} else
			return false;
	}

	private EAnnotation getDiagraphAnnotation_tomove(EClass eclass, String view) {
		EList<EAnnotation> annots = eclass.getEAnnotations();
		EAnnotation result = null;
		for (EAnnotation annot : annots) {
			if (isDiagraphView_tomove(annot, view)) {
				result = annot;
				break;
			}
		}
		if (result == null && view.equals("default"))
			for (EAnnotation annot : annots) {
				if (isDiagraphDefaultView_tomove(annot) == 1) {
					result = annot;
					break;
				}
			}
		return result;
	}

	private EReference parseContainmentReferenceLate_nu_tomove(EClass ecl, // FP131008
			String view) {
		// EClass ecl = (EClass) contained_.getEModelElement();
		EAnnotation annot = getDiagraphAnnotation_tomove(ecl, view);
		for (Map.Entry<String, String> entry : annot.getDetails()) {
			if (entry.getKey().startsWith(DiagraphKeywords.XCONTAINMENT_EQU)) { // FP131009
																				// //XCONTAINMENT
				String[] pars = entry.getKey().split("=");
				String contname = pars[1];
				pars = contname.split("\\.");
				return (EReference) ((EClass) ecl.getEPackage().getEClassifier(
						pars[0])).getEStructuralFeature(pars[1]);
			}
		}
		return null;
	}

	/*------------------------------------------------------*/

}
