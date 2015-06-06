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
package org.isoe.diagraph.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.isoe.diagraph.lang.DiagraphKeywords;
import org.isoe.diagraph.parser.api.IDiagraphAnnotation;
import org.isoe.diagraph.parser.api.IDiagraphAssociation;
import org.isoe.diagraph.parser.api.IDiagraphAssociation.AssociationCandidateType;
import org.isoe.diagraph.parser.api.IDiagraphAssociation.AssociationType;
import org.isoe.diagraph.parser.api.IDiagraphClassAssociation;
import org.isoe.diagraph.parser.api.IDiagraphElement;
import org.isoe.diagraph.parser.api.IDiagraphNode;
import org.isoe.diagraph.parser.api.IDiagraphNotation;
import org.isoe.diagraph.parser.api.IDiagraphObject;
import org.isoe.diagraph.parser.api.IDiagraphParser;
import org.isoe.diagraph.parser.api.IDiagraphReferenceAssociation;
import org.isoe.diagraph.parser.api.IDiagraphView;
import org.isoe.fwk.core.DParams;

/**
 *
 * @author fpfister
 *
 */
public class DiagraphAnnotationParser implements IDiagraphParser {

	public static final boolean DEFAULT_EMBEDDED_CONTAINMENT = true;

	private final boolean LOG = DParams.DiagraphAnnotationParser_LOG;
	private static final String spaces = "                                                                                                                 ";
	private String reportLog = "";
	private List<String> report;
	private static DiagraphAnnotationParser instance;
	private IDiagraphNotation notation;
	private IDiagraphNode canvasNode;
	private Map<EClass, List> referenceRealizations = new HashMap<EClass, List>();
	private Map<EClass, List<IDiagraphReferenceAssociation>> containmentAssociations;
	private Map<EClass, List<IDiagraphReferenceAssociation>> simpleAssociations;
	private String logBuffer = "";

	private List<List<IDiagraphAnnotation>> diagraphAnnotations;

	@Override
	public void logAnnotations() {
		String log = "";
		for (List<IDiagraphAnnotation> annots : diagraphAnnotations) {
			log += "___________________\n";
			for (IDiagraphAnnotation annot : annots) {
				log += annot.toLog() + "\n";
			}
		}
		clog15(log);
	}

	@Override
	public String getLog() {
		return logBuffer;
	}

	@Override
	public void initView(EPackage packag, String view) {
		diagraphAnnotations = null;
	}

	@Override
	public List<List<IDiagraphAnnotation>> getDiagraphAnnotations() {
		return diagraphAnnotations;
	}

	@Override
	public IDiagraphView getCurrentPointOfView() {
		return notation.getCurrentView();
	}

	private void setCurrentPointOfView(IDiagraphView view) {
		notation.setCurrentView_(view);
	}

	public Map<String, EClass> getClasses(EPackage packag) {
		Map<String, EClass> classs = new HashMap<String, EClass>();
		List<EClass> clz = getClassesInView(packag);
		for (EClass eClaz : clz) {
			EClass g = classs.get(eClaz.getName());
			if (g == null)
				classs.put(eClaz.getName(), eClaz);
		}
		return classs;
	}

	public List<IDiagraphAssociation> getAllAssociations() {
		if (getCurrentPointOfView() != null)
			return getCurrentPointOfView().getAssocs();
		else
			return new ArrayList<IDiagraphAssociation>();
	}

	public List<IDiagraphNode> getAllNodes() {
		if (getCurrentPointOfView() != null)
			return getCurrentPointOfView().getNodes();
		else
			return new ArrayList<IDiagraphNode>();
	}

	private List<IDiagraphElement> getAllGenerics() {
		if (getCurrentPointOfView() != null)
			return getCurrentPointOfView().getGenerics();
		else
			return new ArrayList<IDiagraphElement>();
	}

	@Override
	public IDiagraphAssociation findAssociation_(EClass source, EClass target) {
		for (IDiagraphAssociation assoc : getAllAssociations()) {
			if (assoc.getSource() == source && assoc.getTarget() == target
					&& assoc.getView() == notation.getCurrentView().getViewId())
				return assoc;
		}
		return null;
	}

	public static IDiagraphParser getInstance(IDiagraphNotation diagraphNotation) {
		if (instance == null)
			instance = new DiagraphAnnotationParser(diagraphNotation);
		return instance;
	}

	public String getReport() {
		return reportLog;
	}

	public void setReport(String report) {
		this.reportLog = report;
	}

	private DiagraphAnnotationParser(IDiagraphNotation diagraphNotation) {
		// this();
		// if (diagraphNotation != null) {
		this.notation = diagraphNotation;
		report = new ArrayList<String>();
	}

	@Override
	public void cerror(String mesg) {
		notation.cerror(mesg);
	}

	@Override
	public List<IDiagraphReferenceAssociation> guessContainments(String view,
			EClass eclaz) {
		IDiagraphNode diagraphNode = getDiagraphNode(eclaz, 0);// FP150508
		if (LOG) {
			if (!diagraphNode.getAllContainments().isEmpty()) {
				clog("(B) containments for " + diagraphNode.getName() + "{");
				for (IDiagraphReferenceAssociation cont : diagraphNode
						.getAllContainments()) {
					clog(cont.toString());
				}
				clog("}(containments_a for " + diagraphNode.getName() + ")");
			} else
				clog("(B) no containments for " + diagraphNode.getName());
		}
		return diagraphNode.getAllContainments();
	}

	private List<EClass> getOtherNodeOrGenericClasses_(EAnnotation owner,
			String view) {// FP150519
		EClass claz = (EClass) owner.getEModelElement();
		EPackage p = claz.getEPackage();
		List<EClass> classez = new ArrayList<EClass>();
		List<EClassifier> classifs = p.getEClassifiers();
		for (EClassifier eClassifier : classifs) {
			if (eClassifier instanceof EClass) {
				EClass cla = (EClass) eClassifier;
				if (claz != cla) {
					List<EAnnotation> anotz = cla.getEAnnotations();
					for (EAnnotation anot : anotz) {
						if (isNode_(anot, view) || isGeneric(anot, view))
							classez.add(cla);
					}
				}
			}
		}
		return classez;
	}

	private List<EClass> getOtherSubNodeOrGenericClasses(EAnnotation owner,
			String view) {
		EClass claz = (EClass) owner.getEModelElement();
		EPackage p = claz.getEPackage();
		List<EClass> classez = new ArrayList<EClass>();
		List<EClassifier> classifs = p.getEClassifiers();
		for (EClassifier eClassifier : classifs) {
			if (eClassifier instanceof EClass) {
				EClass cla = (EClass) eClassifier;
				if (claz != cla && claz.isSuperTypeOf(cla)) {
					List<EAnnotation> anotz = cla.getEAnnotations();
					for (EAnnotation anot : anotz) {
						if (isNode_(anot, view) || isGeneric(anot, view))
							classez.add(cla);
					}
				}
			}
		}
		return classez;
	}

	private List<EClass> getOtherNodeClasses(EAnnotation owner, String view) {// FP150519
		EClass claz = (EClass) owner.getEModelElement();
		EPackage p = claz.getEPackage();
		List<EClass> classez = new ArrayList<EClass>();
		List<EClassifier> classifs = p.getEClassifiers();
		for (EClassifier eClassifier : classifs) {
			if (eClassifier instanceof EClass) {
				EClass cla = (EClass) eClassifier;
				if (claz != cla) {
					List<EAnnotation> anotz = cla.getEAnnotations();
					for (EAnnotation anot : anotz) {
						if (isNode_(anot, view))
							classez.add(cla);
					}
				}
			}
		}
		return classez;
	}

	private List<EClass> getOtherLinkClasses(EAnnotation owner, String view) {// FP150519
		EClass claz = (EClass) owner.getEModelElement();
		EPackage p = claz.getEPackage();
		List<EClass> classez = new ArrayList<EClass>();
		List<EClassifier> classifs = p.getEClassifiers();
		for (EClassifier eClassifier : classifs) {
			if (eClassifier instanceof EClass) {
				EClass cla = (EClass) eClassifier;
				// System.out.println(claz.getName()+" == "+cla.getName()+" ?");
				if (claz != cla) {
					System.out.println(cla.getName());
					List<EAnnotation> anotz = cla.getEAnnotations();
					for (EAnnotation anot : anotz) {
						if (isLink(anot, view))
							classez.add(cla);
					}
				}
			}
		}
		return classez;
	}

	private List<EClass> allSubNodesClasses(EAnnotation annotation, String view) { // FP150519
																					// modif
		EClass claz = (EClass) annotation.getEModelElement();
		List<EClass> nodclasses = getOtherNodeClasses(annotation, view);
		// List<EClass> linkclasses = getOtherLinks(annotation, view);
		List<EClass> inhNodclasses = new ArrayList<EClass>();
		for (EClass eClass : nodclasses)
			if (claz != eClass && claz.isSuperTypeOf(eClass))
				inhNodclasses.add(eClass);
		// return linkclasses.isEmpty() && !inhNodclasses.isEmpty();
		return inhNodclasses;// .isEmpty();
	}

	private List<EClass> allSubLinkClasses(EAnnotation annotation, String view) { // FP150519
																					// //
																					// mofif
		EClass claz = (EClass) annotation.getEModelElement();
		// List<EClass> nodclasses = getOtherNodes(annotation, view);
		List<EClass> linkclasses = getOtherLinkClasses(annotation, view);
		List<EClass> inhLinkclasses = new ArrayList<EClass>();
		for (EClass eClass : linkclasses)
			if (claz != eClass && claz.isSuperTypeOf(eClass))
				inhLinkclasses.add(eClass);
		// return nodclasses.isEmpty() && !inhLinkclasses.isEmpty();
		return inhLinkclasses;// .isEmpty();
	}

	private boolean isLink(EAnnotation eAnnotation, String viewid) {
		if (isView(eAnnotation, viewid))
			for (Map.Entry<String, String> entry : eAnnotation.getDetails())
				if (entry.getKey().equals("link"))
					return true;
		return false;
	}

	private boolean isView(EAnnotation anot, String viewid) { // FP131208
		if (!anot.getSource().equals(DiagraphKeywords.DIAGRAPH_LITTERAL))
			return false;
		if (viewid == null || viewid.equals(DiagraphKeywords.DIAGRAPH_DEFAULT)
				|| viewid.isEmpty())
			return isDefaultView(anot);
		for (Map.Entry<String, String> entry : anot.getDetails())
			if (entry.getKey().equals(DiagraphKeywords.VIEW_EQU + viewid))
				return true;
		return false;
	}

	@Override
	public List<IDiagraphReferenceAssociation> guessContainments(EClass eclaz,
			boolean abztract) {

		List<IDiagraphReferenceAssociation> result = new ArrayList<IDiagraphReferenceAssociation>();
		IDiagraphNode diagraphNode = getDiagraphNode(eclaz, 0);// FP140505
		if (DParams.Parser_15_LOG)
			clog15("(B) containments " + (abztract ? "abstract" : "concrete")
					+ " for " + diagraphNode.getName() + "{");
		if (!diagraphNode.getAllContainments().isEmpty()) {
			for (IDiagraphReferenceAssociation cont : diagraphNode
					.getAllContainments()) {
				clog15(cont.toString());
				result.add(cont);
			}
		}
		if (result.isEmpty())
			clog15("}(no containments for " + diagraphNode.getName());
		else
			clog15("}(containments_c for " + diagraphNode.getName() + ")");

		return result;
	}

	public void logContainments_(EClass eclaz) {
		IDiagraphNode diagraphNode = getDiagraphNode(eclaz, 0);// FP140505
		clog15("containments " + " for " + diagraphNode.getName() + ":");
		if (!diagraphNode.getAllContainments().isEmpty())
			for (IDiagraphReferenceAssociation cont : diagraphNode
					.getAllContainments())
				clog15(cont.toString());
	}

	public List<IDiagraphReferenceAssociation> guessContainments2(EClass eclaz) {
		IDiagraphNode diagraphNode = getDiagraphNode(eclaz, 0);// FP140505
		if (DParams.Parser_15_LOG) {
			if (!diagraphNode.getAllContainments().isEmpty()) {
				clog15("(B) containments for " + diagraphNode.getName() + "{");
				for (IDiagraphReferenceAssociation cont : diagraphNode
						.getAllContainments()) {
					clog15(cont.toString());
				}
				clog15("}(containments_d for " + diagraphNode.getName() + ")");
			} else
				clog15("(B) no containments for " + diagraphNode.getName());
		}
		return diagraphNode.getAllContainments();
	}

	private void clog2(String mesg) {
		if (DParams.Parser_15_LOG || DParams.Parser_29_LOG)
			System.out.println(mesg);
	}

	private void propagateInheritedContainment(IDiagraphNode node,
			IDiagraphNode superNode) {
		// FP1503129
		List<IDiagraphReferenceAssociation> containments_ = superNode
				.getContainments(); // FP140505x
		for (IDiagraphReferenceAssociation containment_ : containments_) {
			EClass targetClass = containment_.getTargetNode().getEClass(); // FP150505
			String clonedContainmentName = containment_.getName();
			if (LOG) {
				clog(clonedContainmentName + " n: " + node.getName() + " d: "
						+ node.getDepth());
			}
			IDiagraphNode dn = null;
			IDiagraphNode dnd = containment_.getSourceNode();
			boolean propagated = true;

			if (containment_.isCompartment()) {
				if (!dnd.isOrSubNodeCanvas())
					dn = getNodeWithKReference(node.getEClass(),
							containment_.getTargetName(),
							node.getEClass(),// FP150331a304
							containment_.getArgument(), // FP150512transp
							clonedContainmentName,
							DEFAULT_EMBEDDED_CONTAINMENT, propagated); // FP120920
				else
					dn = getNodeWithCReferenc_e(
							node.getEClass(), // FP150505
							containment_.getTargetName(), node.getEClass(),
							clonedContainmentName, propagated);
			} else if (containment_.isPort()) {
				if (!dnd.isOrSubNodeCanvas())
					dn = getNodeWithPReference(node.getEClass(),
							containment_.getTargetName(),
							clonedContainmentName, propagated);
				else
					dn = getNodeWithCReferenc_e(node.getEClass(),
							containment_.getTargetName(), node.getEClass(),
							clonedContainmentName, propagated);
			} else
				dn = getNodeWithCReferenc_e(node.getEClass(),
						containment_.getTargetName(), node.getEClass(),
						clonedContainmentName, propagated);

			IDiagraphReferenceAssociation cloned = (dn == null ? null : dn
					.getCreatedContainment());
			// IDiagraphReferenceAssociation cloned = createdContainment;//
			// FP140406
			if (cloned != null) {

				IDiagraphNode originalTargetNode = containment_.getTargetNode();
				if (originalTargetNode != null)
					cloned.setTargetNode(originalTargetNode); // FP140202xxxaaa
				else
					setTargetNode(node, containment_, cloned);
				if (LOG)
					clog("(2)ClonedContainment: " + cloned.getName());
			} else {
				if (LOG)
					clog("(3)ClonedContainment: ....propagateInheritedContainment failed");
			}

		}

	}

	private void setTargetNode(IDiagraphNode node,
			IDiagraphReferenceAssociation original,
			IDiagraphReferenceAssociation cloned) {
		try {
			EClass targ = (EClass) (node.getEClass()).getEStructuralFeature(
					original.getTargetName()).getEType();
			cloned.setTargetNode(findNode_(targ)); // , node.getView()
		} catch (Exception e) {
			throw new RuntimeException(
					"("
							+ node.getName()
							+ "."
							+ original.getName()
							+ ") unable(2) to set target node for cloned contaiment !!!");
		}

	}

	@Override
	public IDiagraphNode getDiagraphNode(EClass eclaz, int depth) {
		IDiagraphNode result = findNode_(eclaz);
		if (result == null)
			result = createDiagraphNode(eclaz, false, depth);
		return result;
	}

	@Override
	public IDiagraphElement getDiagraphGeneric(EClass eclaz, int depth) {
		IDiagraphElement result = findGeneric(eclaz);
		if (result == null)
			result = createDiagraphGeneric(eclaz, false, depth);
		return result;
	}

	/*
	 * @Override public void createDiagraphProperties(IDiagraphNode
	 * diagraphNode) { createDiagraphProperties_(diagraphNode); }
	 */

	@Override
	public IDiagraphAssociation getDiagraphClassAssociation(EClass c) {
		if (LOG) {
			clog("IDiagraphAssociation:");
			List<IDiagraphAssociation> assocs = getAllAssociations();
			for (IDiagraphAssociation assoc : assocs)
				clog(((assoc instanceof IDiagraphClassAssociation) ? "+++"
						: "---") + assoc.toString());

		}
		IDiagraphAssociation result = findClassAssociation(c);
		if (result == null)
			result = createDiagraphClassAssociation_(c);
		return result;
	}

	private void getDiagraphClassAssociation2(EClass element_, EClass s) {
		throw new RuntimeException(
				"TODO implement getDiagraphClassAssociation2");

	}

	// key.equals(CONTAINMENT)//cont
	private IDiagraphNode getContainedElement(EClass eclaz,
			String containementName) {
		IDiagraphNode del = findNode_(eclaz);

		if (del == null)
			del = createGenericElement(eclaz);

		if (containementName != null && del != null)
			del.setContainmentName(containementName);
		return del;
	}

	private IDiagraphNode createGenericElement(EClass eclaz) {
		// TODO Auto-generated method stub

		if (DParams.TODO_LOG_)
			cerror("TODO implement createGenericElement");
		return null;
	}

	// key.equals(KREFERENCE)

	private IDiagraphNode getNodeWithKReference(EClass srcClaz, String refname,
			EClass targ, String argument, String label, boolean figureEmbedded,
			boolean propagated) { // FP150512transp

		int caze = 1;
		IDiagraphReferenceAssociation createdContainment = null;

		EReference ref = (EReference) srcClaz.getEStructuralFeature(refname);
		EClass refsource = (EClass) ref.eContainer();
		EClass reftarget = (EClass) ref.getEType();

		checkKReferenceCorrectness(srcClaz, refname);

		IDiagraphNode nod = getDiagraphNode(srcClaz, 0);

		IDiagraphNode srcnod = findNode_(refsource);

		int order = propagated ? (nod.getDepth() - srcnod.getDepth()) : 0;

		IDiagraphReferenceAssociation exists_ = nod
				.findContainmentByTargetName(refname); // FP121008x

		if (exists_ == null) { // FP150330a100
			if (reftarget.isAbstract()) {
				caze = 2;
				// targ = getFirstConcreteTarget(claz, refname);//FP150331a304
				exists_ = createNodeCompartmentOnAbstract(srcClaz, targ, ref,
						refname, label, figureEmbedded, order, propagated); // FP150329a111
			} else {
				caze = 3;
				targ = reftarget;
				exists_ = createNodeCompartmentOnConcrete(srcClaz, refname,
						label, figureEmbedded, order, propagated);
			}
			createdContainment = exists_; // FP150329a116
			nod.setCreatedContainment(createdContainment);
		}
		if (DParams.Parser_15_LOG || DParams.Parser_29_LOG)
			logContainment(srcClaz, targ, refname, label, propagated, exists_,
					caze);
		return nod;
	}

	// key.equals(CREF)
	private IDiagraphNode getNodeWithCReferenc_e(EClass srcClaz_,
			String target, EClass targetClass, String label, boolean propagated) { // FP150330a300
																					// yy5

		int caze = 1;
		IDiagraphReferenceAssociation createdContainment = null;
		EReference ref = (EReference) srcClaz_.getEStructuralFeature(target);
		EClass refsource = (EClass) ref.eContainer();
		EClass reftarget = (EClass) ref.getEType();
		// checkKReferenceCorrectness(claz, refname);
		IDiagraphNode nod = getDiagraphNode(srcClaz_, 0);
		// IDiagraphNode containernode = findNode_((EClass) ((EReference) esf)
		// .eContainer());
		IDiagraphNode containernode = findNode_(srcClaz_);

		// DiaNode nod = getNode(claz);
		int order = propagated ? (nod.getDepth() - containernode.getDepth())
				: 0;
		IDiagraphAssociation exists = nod.findContainmentByTargetName(target); // FP140202see2
		if (DParams.Parser_CONTAINMENT2_LOG)
			clog2("GN_CREF(4) " + ((EClass) srcClaz_).getName()
					+ " propagated " + (propagated ? " ypropa " : " npropa ")
					+ target
					+ (exists == null ? " cont not exixts " : " cont exists "));
		if (exists == null) {
			if (LOG)
				clog("GNWCR createNodeContainment " + containernode.getName()
						+ "-" + nod.getName() + "-" + targetClass + "-"
						+ target);
			createdContainment = createNodeContainment(srcClaz_, target, label,
					false, propagated, true, false);
			nod.setCreatedContainment(createdContainment);

		} else if (LOG)
			clog("GNWCR existsNodeContainment " + containernode.getName() + "-"
					+ nod.getName() + "-" + target + "-" + label);

		IDiagraphReferenceAssociation exists_ = nod
				.findContainmentByTargetName(target); // FP121008x

		if (DParams.Parser_15_LOG || DParams.Parser_29_LOG)
			logContainment(srcClaz_, targetClass, target, label, propagated,
					exists_, caze);
		return nod;
	}

	// key.equals(PREFERENCE)
	private IDiagraphNode getNodeWithPReference(EClass element, String target,
			String label, boolean propagated) { // FP130319ppp
		// boolean dummy = false;// FP150329
		IDiagraphReferenceAssociation createdContainment = null;
		if (label == null)
			label = target;
		checkKReferenceCorrectness(element, target);
		IDiagraphNode nod = getDiagraphNode(element, 0);
		IDiagraphAssociation exists = nod.findContainmentByTargetName(label); // wxcvs234
		if (DParams.Parser_CONTAINMENT2_LOG)
			clog2("GN_PREF " + element.getName() + " propagated "
					+ (propagated ? " ypropa " : " npropa ") + label
					+ (exists == null ? " cont not exixts " : " cont exists "));
		if (exists == null) {
			createdContainment = createNodeContainment(element, target, label,
					true, propagated, true, false); // FP140406
			nod.setCreatedContainment(createdContainment);
		}
		return nod;
	}

	private IDiagraphReferenceAssociation parseContainment_(EClass eclaz,
			IDiagraphAnnotation annotation,
			List<IDiagraphAnnotation> otherAnnotations_) {
		// DGraphElement nodtype =

		guessNodeOrLink(otherAnnotations_);
		IDiagraphNode result = getContainedElement(eclaz, annotation.getValue());
		if (DParams.TODO_LOG_)
			cerror("TODO implement parseContainment");
		return null;
	}

	private void guessNodeOrLink(List<IDiagraphAnnotation> otherAnnotations_) {
		if (DParams.TODO_LOG_)
			cerror("TODO guessNodeOrLink");

	}

	private IDiagraphClassAssociation parseLink(EClass eclaz,
			IDiagraphAnnotation annotation) {
		if (LOG) {
			clog("DAP-parseLink " + eclaz.getName() + " for view "
					+ notation.getCurrentView().getViewId());
		}
		IDiagraphClassAssociation result = getLink(eclaz, annotation,
				annotation.getOther());// , linkLabels)
		if (result == null)
			annotation.setProblem();
		addAnnotations_(annotation);
		return result;

	}

	private void addAnnotations_(IDiagraphAnnotation annotation) {
		List<IDiagraphAnnotation> annots = new ArrayList<IDiagraphAnnotation>();
		annots.add(annotation);
		for (IDiagraphAnnotation other : annotation.getOther())
			if (!annots.contains(other)) // TODO voir
				annots.add(other);
		if (!diagraphAnnotations.contains(annots))
			diagraphAnnotations.add(annots);
	}

	private IDiagraphClassAssociation getLink__nu(EClass eclaz) {
		for (EAnnotation eAnnotation : eclaz.getEAnnotations())
			if (eAnnotation.getSource().equals(
					DiagraphKeywords.DIAGRAPH_LITTERAL)
					&& isView(eAnnotation)) {

			}
		return null;
	}

	private EReference getLinkContainer(String container, String cont,
			EClass eclaz) {
		int count = 0;
		EReference result = null;
		boolean containmentYes = true;
		if (container != null)
			for (EReference ref : getAllReferences(eclaz.getEPackage(),
					containmentYes))
				if (ref.getName().equals(cont)
						&& ((EClass) ref.getEType()).isSuperTypeOf(eclaz) // FP150520
						&& ref.isContainment()
						&& ((EClass) ref.eContainer()).getName().equals(
								container)) {
					result = ref;
					count++;
				}
		return count == 1 ? result : null;
	}

	private IDiagraphClassAssociation getLink(EClass eclaz,
			IDiagraphAnnotation annotation,
			List<IDiagraphAnnotation> otherAnnotations) { // FP150521link
		String cont = null;
		String src_ = null;
		String trg = null;
		EReference tref_ = null, sref_ = null, contref = null;
		IDiagraphElement srcEl = null;
		IDiagraphElement trgEl = null;
		IDiagraphNode contNode = null;
		for (IDiagraphAnnotation annot : otherAnnotations) {
			String k = annot.getKey();
			if (k.equals(DiagraphKeywords.CONTAINMENT))
				cont = annot.getValue();
			else if (k.equals(DiagraphKeywords.LINK_SOURCE))
				src_ = annot.getValue();
			else if (k.equals(DiagraphKeywords.LINK_TARGET))
				trg = annot.getValue();
		}
		if (src_ == null || trg == null || cont == null) {
			String[] r = guessLink(eclaz, src_, trg, cont);
			if (r != null) {
				src_ = r[0];
				trg = r[1];
				cont = r[2];
			}
		}
		if (trg == null && cont != null) {
			EReference t = getUniqueLinkTarget(eclaz);
			trg = t == null ? null : t.getName();
		}
		if (trg != null && cont != null) {
			List<EReference> erefs = eclaz.getEAllReferences(); //FP150526azer
			for (EReference eRef : erefs) {
				if (src_ != null && eRef.getName().equals(src_))
					sref_ = eRef;
				if (eRef.getName().equals(trg))
					tref_ = eRef;
			}
			String container = null;
			if (cont.contains(".")) {
				container = cont.substring(0, cont.indexOf("."));
				cont = cont.substring(cont.indexOf(".") + 1, cont.length());
			}
			if (container != null)
				contref = getLinkContainer(container, cont, eclaz);
			if (tref_ !=null && isNodeOrGeneric((EClass) tref_.getEType()))
				trgEl = findNodeOrGeneric((EClass) tref_.getEType());
			if (isNode((EClass) contref.eContainer()))
				contNode = findNode_((EClass) contref.eContainer());
			if (sref_ != null && isNodeOrGeneric((EClass) sref_.getEType()))
				srcEl = findNodeOrGeneric((EClass) sref_.getEType());
			else
				srcEl = contNode;

			if (srcEl != null && trgEl != null && contNode != null) {
				EClass refinstancesource = eclaz;
				return createDiagraphClassAssociation_(contNode, srcEl, trgEl,
						eclaz, refinstancesource, tref_);
			} else {
				String error = "class association not created for "
						+ eclaz.getName() + " (";
				error += (srcEl == null ? "source not tagged " : "")
						+ (trgEl == null ? "target not tagged " : "")
						+ (contNode == null ? "containment not tagged" : "");
				throw new RuntimeException(error);
			}
		} else
			throw new RuntimeException("class association not created for "
					+ eclaz.getName()); // FP150520
	}

	private EReference getUniqueLinkContainment(EClass eclaz, String cont) {
		if (cont == null)
			return getUniqueLinkContainment(eclaz);
		else {
			EPackage pak = eclaz.getEPackage();
			String[] c = cont.split("\\.");
			EClass cc = (EClass) pak.getEClassifier(c[0]);
			EReference ref = (EReference) cc.getEStructuralFeature(c[1]);
			if (ref.getEType() == eclaz && ref.isContainment())
				return ref;
		}
		return null;
	}

	private EReference getUniqueLinkTarget(EClass eclaz) {
		int found = 0;
		EReference result = null;
		for (EStructuralFeature r : eclaz.getEAllStructuralFeatures())
			if ((r instanceof EReference) && r.getUpperBound() == 1
					&& isNode((EClass) r.getEType())) {
				result = (EReference) r;
				found++;
			}
		return found == 1 ? result : null;
	}

	private EReference getUniqueLinkContainment(EClass eclaz) {
		EReference result = null;
		List<EReference> refz = new ArrayList<EReference>();
		EPackage pak = eclaz.getEPackage();
		EList<EClassifier> classifs = pak.getEClassifiers();
		for (EClassifier classif : classifs) {
			if (classif instanceof EClass) {
				EClass claz = (EClass) classif;
				EList<EReference> refs = claz.getEAllReferences();
				for (EReference ref : refs)
					if (((EClass) ref.getEType()).isSuperTypeOf(eclaz)
							&& ref.isContainment())
						if (!refz.contains(ref))
							refz.add(ref);
			}
		}
		List<EReference> refz2 = new ArrayList<EReference>();
		for (EReference ref : refz) {
			EClass src = (EClass) ref.eContainer();
			if (isNode(src))
				refz2.add(ref);
		}
		if (refz2.size() == 1)
			result = refz2.get(0);
		return result;
	}

	private EReference getLinkSource(EClass eclaz, EReference containment,
			String src) {
		if (src == null)
			return getLinkSource(eclaz, containment);
		else
			return (EReference) eclaz.getEStructuralFeature(src);
	}

	private EReference getLinkTarget(EClass eclaz, EReference containment,
			String trg) {
		if (trg == null)
			return getLinkTarget(eclaz, containment);
		else
			return (EReference) eclaz.getEStructuralFeature(trg);
	}

	private EReference getLinkSource(EClass eclaz, EReference containment) {
		EReference op = containment.getEOpposite();
		if (op == null)
			return null;
		List<EReference> refz = new ArrayList<EReference>();
		EReference result = null;
		EList<EReference> refs = eclaz.getEReferences();
		for (EReference ref : refs)
			if (ref == op)
				refz.add(ref);
		if (refz.size() == 1)
			result = refz.get(0);
		return result;
	}

	private EReference getLinkTarget(EClass eclaz, EReference src) {
		List<EReference> refz = new ArrayList<EReference>();
		EReference result = null;
		EList<EReference> refs = eclaz.getEReferences();
		for (EReference ref : refs)
			if (ref != src)
				refz.add(ref);

		if (refz.size() == 1)
			result = refz.get(0);
		return result;
	}

	private String[] guessLink(EClass eclaz, String src, String trg, String cont) {// FP150508
		String[] result = new String[3];
		result[0] = src;
		result[1] = trg;
		result[2] = cont;
		EReference contref = null;
		if (cont != null)
			contref = getUniqueLinkContainment(eclaz, cont);
		else
			contref = getUniqueLinkContainment(eclaz);
		if (contref != null) {
			result[2] = ((EClass) contref.eContainer()).getName() + "."
					+ contref.getName();
			EReference s = getLinkSource(eclaz, contref, src);
			if (s != null) { // FP150520
				result[0] = s.getName();
				EReference t = getLinkTarget(eclaz, s, trg);
				if (t != null) {
					result[1] = t.getName();
					return result;
				}
			}
		}
		if (result[0] != null || result[1] != null || result[2] != null)
			return result;
		else
			return null;
	}

	private IDiagraphNode parseNode(EClass eclaz,
			IDiagraphAnnotation annotation,
			List<IDiagraphAnnotation> otherAnnotations) {
		if (LOG)
			clog("DAP-parseNode " + eclaz.getName() + " for view "
					+ notation.getCurrentView().getViewId());
		String value = annotation.getValue();
		String key = annotation.getKey();
		boolean isCurrentCanvas = false; // FP130610n
		IDiagraphView currentview = notation.getCurrentView();
		isCurrentCanvas = eclaz.getName().equals(currentview.getPovName());
		if (LOG) {
			if (isCurrentCanvas)
				clog("DA_DBNT-DAPND-" + " - currentcanvas- "); // FP120926
			else
				clog("DA_DBNT-DAPND-" + " - NOT currentcanvas- "); // FP120926
		}
		boolean pointOfView = key.equals(DiagraphKeywords.POINT_OF_VIEW);
		// boolean notvalid = annotation.key_.startsWith(POINT_OF_VIEW_PREFIX);
		if (pointOfView) {
			annotation.setValue(null);
			if (!isCurrentCanvas)
				if (LOG)
					clog("canvas but not current: " + eclaz.getName());
		}
		String[] labelledNode_ = new String[2];
		String nodename__ = null;

		if (value != null && !value.equals("_") && value.length() > 0) {
			nodename__ = value;
			cerror("obsolete in DAP-parseNode");
		} else if (key.startsWith(DiagraphKeywords.NODE + ":")) { // FP121007
			labelledNode_ = key.split(":"); // FP140429 tosee
			nodename__ = labelledNode_[1];
			cerror("obsolete in DAP-parseNode");
		} else
			labelledNode_[0] = (key);

		IDiagraphNode result = null;
		String nodeLabels = null;

		if (isCurrentCanvas)
			result = getCanvas(eclaz);
		else
			result = getNamedNode(eclaz, nodeLabels);

		String navigation = parseNavigation(otherAnnotations);

		if (navigation != null)
			result.setNavigation(navigation); // FP121124k

		EReference xcontref = parseExtraContainmentReference(eclaz);// FP131009
																	// TODO
		// validate this
		// case
		if (xcontref != null) {
			result.setXtraDeclaredContainmentReference_nu(xcontref); // FP131020
																		// not
			// used at the
			// moment
			// //FP131009x//in
			// case of
			// multiple
			// containments
		}

		return result;
	}

	private List<IDiagraphReferenceAssociation> parseContainementReferenceAssociationToAbstract( // FP150516y
			EReference targetReference) {
		if (targetReference == null)
			throw new RuntimeException("target reference should not be null !");
		EClass targetClass = (EClass) targetReference.getEType();
		EClass eclazs = (EClass) targetReference.eContainer();
		String targetValue = targetReference.getName();
		boolean cazAbstract = targetClass.isAbstract();
		if (!cazAbstract)
			return new ArrayList<IDiagraphReferenceAssociation>();
		else
			return parseContainmentAssociationsOnTarget(targetClass,
					targetValue);
	}

	private List<IDiagraphReferenceAssociation> parseContainementReferenceAssociationAorC_(
			EReference targetReference) {
		EClass targetClass = (EClass) targetReference.getEType();
		EClass eclazs = (EClass) targetReference.eContainer();
		String targetValue = targetReference.getName();
		boolean cazAbstract = targetClass.isAbstract();// "NamedElement".equals(eclaz.getName())?true:false;
		List<IDiagraphReferenceAssociation> result_ = null;
		if (cazAbstract)
			result_ = parseContainmentAssociationsAbstractTo(targetClass,
					targetValue);
		else
			result_ = parseContainmentAssociationsConcreteTo_(targetClass,
					targetValue);
		return result_;
	}

	/*
	 * private List<IDiagraphReferenceAssociation>
	 * parseContainementReferenceAssociationAorC_pb( EClass eclaz, String
	 * targetValue, EReference targetReference) { boolean cazAbstract =
	 * eclaz.isAbstract();// "NamedElement".equals(eclaz.getName())?true:false;
	 * List<IDiagraphReferenceAssociation> result = null; if (cazAbstract)
	 * result = parseContainmentAssociationsAbstractTo(eclaz, targetValue); else
	 * result = parseContainmentAssociationsConcreteTo(eclaz, targetReference);
	 * return result; }
	 */

	private List<IDiagraphReferenceAssociation> parseContainmentAssociationsOnTarget(
			EClass abstractClass, String targetValue) {
		AssociationCandidateType caz_nu = AssociationCandidateType.CAND_CONT_ABSTR_TARGET__;
		// AssociationType typ = AssociationType.TYPED_COMPARTMENT;
		AssociationType[] typs = { AssociationType.TYPED_COMPARTMENT,
				AssociationType.SHARED_COMPARTMENT_ };
		if (DParams.Parser_15_LOG) {

			clog2("IDiagraphReferenceAssociation added with abstract class: "
					+ abstractClass.getName());
		}
		List<IDiagraphReferenceAssociation> rassocs = null;
		if (targetValue != null && !targetValue.isEmpty())
			rassocs = getCurrentPointOfView()
					.findReferenceAssociationsByAssociationName(
							abstractClass.getName(), targetValue, typs);// FP150516x
		else
			rassocs = getCurrentPointOfView()
					.findReferenceAssociationsByTargetName(
							abstractClass.getName(), typs);// FP150516x
		if (rassocs == null || rassocs.isEmpty()) {// FP150516x filtrer
													// autoréférence ?
			rassocs = parseContainementReferenceAssociationOnClass(
					abstractClass, targetValue); // FP150521voir
			getCurrentPointOfView().addReferenceAssociations(
			// abstractClass.getName(),
					rassocs);
		}
		if (rassocs.isEmpty())// || l.size() > 1)
			throw new RuntimeException(
					"should not happen in parseContainmentAssociationsOnTarget");
		return rassocs;
	}

	@Override
	public List<IDiagraphReferenceAssociation> parseContainmentAssociationsAbstractTo(
			EClass eclaz_, String targetValue) {
		List<IDiagraphReferenceAssociation> result = new ArrayList<IDiagraphReferenceAssociation>();
		AssociationCandidateType caz_nu = AssociationCandidateType.CAND_CONT_ABSTR__;
		// AssociationType typ = AssociationType.TYPED_COMPARTMENT;
		AssociationType[] typs = { AssociationType.TYPED_COMPARTMENT,
				AssociationType.SHARED_COMPARTMENT_ };

		List<EClass> concreteSubclasses = new ArrayList<EClass>();
		if (eclaz_.isAbstract()) {
			EPackage p = eclaz_.getEPackage();
			List<EClassifier> cs = p.getEClassifiers();
			for (EClassifier c : cs) {
				if (c instanceof EClass) {
					EClass clazz = (EClass) c;
					if (eclaz_.isSuperTypeOf(clazz) && eclaz_ != clazz
							&& !clazz.isAbstract()
							&& !concreteSubclasses.contains(clazz)) {
						concreteSubclasses.add(clazz);
						if (DParams.Parser_15_LOG)
							clog2("concrete subclass: " + clazz.getName());
					}
				}
			}
		}
		List<IDiagraphReferenceAssociation> x_ = new ArrayList<IDiagraphReferenceAssociation>();
		for (EClass concreteSubclass : concreteSubclasses) {
			List<IDiagraphReferenceAssociation> rassocs = getCurrentPointOfView()
					.findReferenceAssociationsByTargetName(
							concreteSubclass.getName(), typs);// FP150516x
			if (rassocs == null || rassocs.isEmpty()) {
				rassocs = parseContainementReferenceAssociationOnAbstracttt(
						concreteSubclass, targetValue); // FP150521voir
				getCurrentPointOfView().addReferenceAssociations(
				// concreteSubclass.getName(), //FP150521
						rassocs);

			}
			for (IDiagraphReferenceAssociation _raf : rassocs) {
				if (!x_.contains(_raf))
					x_.add(_raf);
			}
		}

		for (IDiagraphReferenceAssociation raf : x_)
			if (raf.getTargetReference().getName().equals(targetValue)) // FP150327
				result.add(raf);
		/*
		 * for (IDiagraphReferenceAssociation raf : result) { if
		 * (!raf.contains(caz)) { raf.addCase(caz); if (DParams.Parser_15_LOG)
		 * clog15("4wwww" + raf.toString()); } }
		 */
		return result;
	}

	@Override
	public List<IDiagraphReferenceAssociation> parseContainmentAssociationsConcreteTo_(
			EClass eclaz, String targetValue) {
		List<IDiagraphReferenceAssociation> result = new ArrayList<IDiagraphReferenceAssociation>();
		AssociationCandidateType caz_nu = AssociationCandidateType.CAND_CONT_CONCRETE;
		AssociationType[] typs = { AssociationType.TYPED_COMPARTMENT,
				AssociationType.SHARED_COMPARTMENT_ };
		List<IDiagraphReferenceAssociation> rassocs = getCurrentPointOfView()
				.findReferenceAssociationsByTargetName(eclaz.getName(), typs);// FP150516x
		if (rassocs == null || rassocs.isEmpty()) {
			rassocs = parseContainementReferenceAssociationOnConcrete2t1(eclaz,
					targetValue);
			getCurrentPointOfView().addReferenceAssociations(
			// eclaz.getName(),//FP150521
					rassocs);
		}
		for (IDiagraphReferenceAssociation raf : rassocs)
			if (raf.getTargetReference().getName().equals(targetValue)) { // name
																			// is
																			// unique
																			// //FP150327
				result.add(raf);
				if (DParams.Parser_15_LOG)
					clog2("containmentAssociationsConcreteTo: "
							+ raf.getSourceName() + "->" + raf.getTargetName());
			}
		/*
		 * for (IDiagraphReferenceAssociation raf : result) { if
		 * (!raf.contains(caz)) { raf.addCase(caz); if (DParams.Parser_15_LOG)
		 * clog15("2wwww" + raf.toString()); } }
		 */
		return result;
	}

	private List<EClass> getConcreteETypes(EReference ref, boolean containment) {
		List<EClass> result = new ArrayList<EClass>();
		EClass rtyp = (EClass) ref.getEType();
		if (!containment || containment && ref.isContainment()) {
			if (!rtyp.isAbstract()) {
				clog15(((EClass) ref.eContainer()).getName() + "->"
						+ ref.getName() + " ("
						+ ((EClass) ref.getEType()).getName() + ")");
				result.add((EClass) ref.getEType());
			} else {
				List<EClass> subclasses = new ArrayList<EClass>();
				List<EClassifier> clsss = rtyp.getEPackage().getEClassifiers();
				for (EClassifier classifier : clsss) {
					if (classifier instanceof EClass) {
						EClass ecla = (EClass) classifier;
						if (rtyp != ecla && rtyp.isSuperTypeOf(ecla))
							subclasses.add(ecla);
					}
				}
				for (EClass subClass : subclasses) {
					clog15(((EClass) ref.eContainer()).getName() + "->"
							+ ref.getName() + " (" + subClass.getName() + ")");
					result.add(subClass);
				}
			}
		}
		return result;
	}

	private List<EClass> getAllConcreteTargets(EClass claz, String target) {
		List<EClass> result = new ArrayList<EClass>();
		EReference t = (EReference) claz.getEStructuralFeature(target);
		EClass targ = (EClass) t.getEType();
		if (targ.isAbstract()) {
			EPackage p = claz.getEPackage();
			List<EClassifier> cs = p.getEClassifiers();
			for (EClassifier c : cs) {
				if (c instanceof EClass) {
					EClass clazz = (EClass) c;
					if (targ.isSuperTypeOf(clazz) && targ != clazz
							&& !clazz.isAbstract()) {
						result.add(clazz);
					}
				}
			}
		}
		return result;
	}

	private EClass getFirstConcreteTarget(EClass claz, String target) { // FP150330a112
		EClass targ = null;
		EReference t = (EReference) claz.getEStructuralFeature(target);
		targ = (EClass) t.getEType();
		if (targ.isAbstract()) {
			EPackage p = claz.getEPackage();
			List<EClassifier> cs = p.getEClassifiers();
			for (EClassifier c : cs) {
				if (c instanceof EClass) {
					EClass clazz = (EClass) c;
					if (targ.isSuperTypeOf(clazz) && targ != clazz
							&& !clazz.isAbstract()) {
						targ = clazz;
						break;
					}
				}
			}
		}
		return targ;
	}

	private List<IDiagraphReferenceAssociation> parseContainementReferenceAssociationOnClass(// FP150321voir
			EClass eclaz, String targetValue) {// FP150505voir
		if (DParams.Parser_15_LOG) {
			String nam = notation.getCurrentView().getViewId() + "."
					+ eclaz.getName();
			clog15("parseContainementReferenceAssociationOnClass " + nam
					+ " target=" + targetValue);
		}
		if (!isNode(eclaz))
			throw new RuntimeException(
					"parseContainementReferenceAssociationOnClass only on nodes");
		EClass target = eclaz;
		if (target == null)
			throw new RuntimeException("c-kref(2) error"); // FP150321
		else {
			if (DParams.Parser_15_LOG)
				clog15("ckref=" + targetValue + " ok");
		}
		IDiagraphReferenceAssociation exists = null;
		List<IDiagraphReferenceAssociation> conts_ = guessContainments(target,
				true); // FP150329zzz
		for (IDiagraphReferenceAssociation raf : conts_)
			raf.addCase(AssociationCandidateType.CAND_CONT_3);
		for (IDiagraphReferenceAssociation raf : conts_)
			if (raf.getTargetReference().getName().equals(targetValue)) {
				exists = raf;
				break;
			}
		if (exists == null) // FP150518yy
			throw new RuntimeException("c-kref(4) error");// FP150321b
		return conts_;
	}

	private List<IDiagraphReferenceAssociation> parseContainementReferenceAssociationOnAbstracttt(// FP150321voir
			EClass eclaz, String targetValue) {
		if (DParams.Parser_15_LOG) {
			String nam = notation.getCurrentView().getViewId() + "."
					+ eclaz.getName();
			clog15("getContainementReferenceAssociation " + nam + " target="
					+ targetValue);
		}
		if (!isNode(eclaz))
			throw new RuntimeException(
					"getContainementReferenceAssociation only on nodes");

		boolean containmentYes = true;
		EReference creference1 = null;

		EClass concreteTarget = null;

		for (EReference ref : getAllReferences(eclaz.getEPackage(),
				containmentYes))
			if (ref.getName().equals(targetValue))
				for (EClass c : getConcreteETypes(ref, containmentYes))
					if (eclaz.isSuperTypeOf(c)) {
						concreteTarget = c;
						clog15(((EClass) ref.eContainer()).getName() + "-"
								+ ref.getName() + "->" + c.getName());
						creference1 = ref;
					}

		if (concreteTarget == null)
			throw new RuntimeException("c-kref1 error"); // FP150321
		else {
			if (DParams.Parser_15_LOG)
				clog15("ckref=" + targetValue + " ok");
		}
		IDiagraphReferenceAssociation exists = null;

		List<IDiagraphReferenceAssociation> conts_ = guessContainments(
				concreteTarget, false); // FP150329zzz
		for (IDiagraphReferenceAssociation raf : conts_)
			raf.addCase(AssociationCandidateType.CAND_CONT_ABSTR_2);

		for (IDiagraphReferenceAssociation raf : conts_)
			if (raf.getTargetReference().getName().equals(targetValue)) {
				exists = raf;
				break;
			}
		if (exists == null)
			throw new RuntimeException("c-kref2 error");// FP150321b
		return conts_;
	}

	private void clog15(String mesg) {
		if (DParams.Parser_15_LOG || DParams.Parser_29_LOG)
			System.out.println(mesg);
	}

	public boolean isSuperTypeOf(EClass supe, EClass lower) {
		for (EClass su : lower.getEAllSuperTypes())
			if (su.equals(supe))
				return true;
		return false;
	}

	private boolean isContainer(EReference ref, EClass eclaz, boolean inherited) { // FP150316
		EClass containerClass = (EClass) ref.eContainer();
		boolean result = containerClass == eclaz;
		if (!result && inherited)
			result = isSuperTypeOf(containerClass, eclaz);
		return result;
	}

	private boolean isContainer2(EReference ref, EClass eclaz, boolean inherited) { // FP150316
		EClass containerClass = (EClass) ref.eContainer();
		boolean result = containerClass == eclaz;
		if (!result && inherited)
			result = isSuperTypeOf(eclaz, containerClass);
		return result;
	}

	private List<IDiagraphReferenceAssociation> getContainementReferenceAssociation_orig(
			EClass eclaz, String targetValue) {
		if (LOG)
			clog("parseC-KReference " + notation.getCurrentView().getViewId()
					+ "." + eclaz.getName());
		if (!isNode(eclaz))
			throw new RuntimeException("parseC-KReference only on nodes");
		IDiagraphReferenceAssociation firstReferenceAssociation = null;
		boolean containmentYes = true;
		EReference creference = null;
		for (EReference ref : getAllReferences(eclaz.getEPackage(),
				containmentYes))
			if (ref.getName().equals(targetValue)
					&& ((EClass) ref.eContainer() == eclaz))
				creference = ref;
		if (creference == null)
			throw new RuntimeException("c-kref3 error");// FP150321
		else {
			if (LOG)
				clog("ckref=" + targetValue + " ok");
		}
		String view = getCurrentPointOfView().getViewId();
		List<IDiagraphReferenceAssociation> conts = guessContainments(view,
				(EClass) creference.getEType());
		for (IDiagraphReferenceAssociation raf : conts)
			if (raf.getTargetReference().getName().equals(targetValue)) {
				firstReferenceAssociation = raf;
				break;
			}
		if (firstReferenceAssociation == null)
			throw new RuntimeException("c-kref4 error"); // FP150321
		return conts;
	}

	private IDiagraphReferenceAssociation createBaseCompartmentOnConcrete(
			EClass element, String target, String label, int order,
			boolean isPropagated) { // FP140330
		EReference ref = (EReference) element.getEStructuralFeature(target);
		boolean compartment = true;
		boolean noport = false;
		IDiagraphReferenceAssociation containment = createContainment(ref,
				isPropagated, compartment, noport);
		containment.setTargetName(target);
		IDiagraphNode dnode = getDiagraphNode(element, 0);
		containment.setSourceNode(dnode);
		if (DParams.Parser_CONTAINMENT1_LOG) {
			EClass src = dnode.getEClass();
			System.out.println("createBaseCompartment " + src.getName());
		}
		dnode.addContainment(4, containment, order, true);
		return containment;
	}

	private IDiagraphReferenceAssociation createBaseCompartmentOnAbstract(
			EClass element, EClass targ, EReference eref, String target,
			String label, int order, boolean isPropagated) { // FP150330a201
		EReference ref = (EReference) element.getEStructuralFeature(target);
		boolean compartment = true;
		boolean noport = false;
		IDiagraphReferenceAssociation containment = createContainment(ref,
				isPropagated, // true,
				compartment, noport);// !DParams.NO_COMPARTMENTS);

		containment.setTargetName(target);

		IDiagraphNode tarn = findNode_(targ);// FP150329a117
		containment.setTargetName(targ.getName());// target); //FP150329a113
		containment.setTargetNode(tarn); // FP150329a117
		containment.setTargetReference(eref);

		IDiagraphNode dnode = getDiagraphNode(element, 0);
		containment.setSourceNode(dnode);
		// dnode.parserAddContainment1(containment);
		if (DParams.Parser_CONTAINMENT1_LOG) {
			EClass src = dnode.getEClass();
			System.out.println("createBaseCompartment " + src.getName());
		}

		dnode.addContainment(4, containment, order, true);
		return containment;
	}

	private IDiagraphReferenceAssociation createRecursiveCompartment(
			EClass element, String target, String label, int order,
			boolean isPropagated) {
		EReference ref = (EReference) element.getEStructuralFeature(target);
		boolean compartment = true;
		boolean notport = false;
		boolean check;

		IDiagraphReferenceAssociation containment = createContainment(ref,
				isPropagated, // figureEmbedded,
				compartment, notport);// !DParams.NO_COMPARTMENTS);
		// containment.setName(label);
		containment.setTargetName(target);
		IDiagraphNode dnode = getDiagraphNode(element, 0);
		containment.setSourceNode(dnode);
		if (DParams.Parser_CONTAINMENT2_LOG)
			clog2("createRecursiveCompartment " + dnode.getEClass().getName());
		dnode.addContainment(5, containment, order, true);
		EReference dc = dnode.getDeclaredContainment(); // FP140408
		if (dc == null)
			check = true; // FP140329
		return containment;
	}

	// CREF || AFX
	// @Override
	private IDiagraphReferenceAssociation createNodeContainment(EClass element,
			String target, String containmentName, boolean isPort,
			boolean propagated, boolean doAdd, boolean derived) {
		boolean knameinferred = false;
		boolean kcreated = false;
		boolean isCompartment = false;
		boolean check;
		if (DParams.Parser_CONTAINMENT2_LOG) {
			String tocreeate = ((EClass) element).getName() + "." + target;
			clog2("tocreate" + tocreeate);
			if (tocreeate.equals("Root.ds"))
				check = true;
		}

		// if (DParams.RAISE_TODO)
		// if (isRecursiveCompartment(element, target)) // FP140329
		// throw new RuntimeException("TODO in createNodeContainment");

		IDiagraphNode nod = getDiagraphNode(element, 0);
		IDiagraphReferenceAssociation containment = nod == null ? null : nod
				.findContainmentByTargetName(target);
		if (DParams.Parser_CONTAINMENT2_LOG) {
			if (doAdd)
				clog2("-----createNodeContainment "
						+ ((EClass) element).getName()
						+ "."
						+ target
						+ " "
						+ (propagated ? " ypropa " : " not_propa ")
						+ (containment == null ? " cont not exixts "
								: " cont exists "));
			else
				clog2("(" + ((EClass) element).getName() + "." + target
						+ " ok)");
		}
		if (containment != null)
			throw new RuntimeException(
					"should not happen in createNodeContainment");

		// EReference r = null; //FP140617pbici

		EReference r = (EReference) element.getEStructuralFeature(target); // FP140617voir2
		containment = createContainment(r, propagated, isCompartment, isPort);
		containment.setTargetName(target);
		containment.setDerived(derived);
		IDiagraphNode dnode = getDiagraphNode(element, 0);
		containment.setSourceNode(dnode);
		dnode.addContainment(3, containment, -1, doAdd);
		EReference dc_nu = dnode.getDeclaredContainment(); // FP140408
		if (LOG) {
			// DiaUtils.log("createNodeContainment", containmentName, kcreated,
			// knameinferred, containment, null);
		}
		return containment;
	}

	private IDiagraphReferenceAssociation createContainment(EReference ref,
			boolean propagated, boolean compartment, boolean port) {// boolean
		// figureEmbedded,
		boolean simple_no = false;
		List<IDiagraphReferenceAssociation> containments_ = new ArrayList<IDiagraphReferenceAssociation>();
		IDiagraphReferenceAssociation comptm = createReferenceAssociation(ref,
				simple_no);
		comptm.setPropagated(propagated);
		comptm.setCompartment(compartment);
		comptm.setPort(port);

		containments_.add(comptm);
		return comptm;
	}

	public boolean isRecursiveCompartment(EClass element, String target) { // FP140329
		if (hasKrefDirect((EClass) element, target)) {
			for (IDiagraphReferenceAssociation association : getContainmentAssociationsAndSubHierTo(element))
				if (association.getTargetReference().getName().equals(target)
						&& association.getSource() == element)
					return true;
		}
		return false;
	}

	private boolean hasKrefDirect(EClass eModelElement, String name) {
		return isAnnotated(eModelElement, name,
				IDiagraphAssociation.AssociationType.TYPED_COMPARTMENT,
				IDiagraphParser.InheritanceType.DIRECT);
	}

	private void checkKReferenceCorrectness(EClass claz, String name) {
		EList<EReference> allrefs = claz.getEAllReferences();
		for (EReference ref : allrefs) {
			if (ref.getName().equals(name))
				return;
		}
		throw new RuntimeException("reference " + name + " does not exist for "
				+ claz.getName() + " !!!");
	}

	/*
	 * private void parserParse1(List<IDiagraphAnnotation> anns,
	 * IDiagraphAnnotation anot, EClass eclaz) { parse(eclaz, anot, anns); }
	 *
	 *
	 * private void parserParse2_(List<IDiagraphAnnotation> anns,
	 * IDiagraphAnnotation anot, EClass eclaz) {
	 * anot.parse(anns,eclaz,diagraphNotation); }
	 */

	private void parse_(EClass eclaz, IDiagraphAnnotation annotation,
			List<IDiagraphAnnotation> otherAnnotations) {// 140505ppp

		annotation.setOther(otherAnnotations);
		String annotationKey = annotation.getKey();
		String annotationValue_ = annotation.getValue();
		String label = annotation.getArgument1();
		String annotationArgument2 = annotation.getArgument2();
		String[] annotationArguments = annotation.parse();

		String viewid_ = notation.getCurrentView().getViewId();

		if (LOG) {
			String log = annotationKey + "="

			+ annotationValue_ + " (" + label + " " + annotationArgument2 + ")";
			clog("   parse-----" + log);

			// parse-----link=_=null
		}
		IDiagraphNode nod_ = null;
		IDiagraphReferenceAssociation rassoc = null;
		IDiagraphClassAssociation classoc = null;
		IDiagraphElement elem = null;

		if (annotationKey.equals(DiagraphKeywords.NODE)
				|| annotationKey.startsWith(DiagraphKeywords.NODE + ":"))
			nod_ = parseNode(eclaz, annotation, otherAnnotations);
		else if (annotationKey.equals(DiagraphKeywords.CONTAINMENT_HOST_NU))
			rassoc = parseHostContainment(eclaz);
		else if (annotationKey.equals(DiagraphKeywords.CONTAINMENT))
			rassoc = parseContainment_(eclaz, annotation, otherAnnotations);
		else if (annotationKey.equals(DiagraphKeywords.LABEL))
			elem = parseLabel(eclaz);
		else if (annotationKey.equals(DiagraphKeywords.LABELS))
			elem = parseLabels(eclaz);
		else if (annotationKey.equals(DiagraphKeywords.POINT_OF_VIEW))
			nod_ = parseNode(eclaz, annotation, otherAnnotations);
		else if (annotationKey.equals(DiagraphKeywords.OPEN_DIAGRAM))
			nod_ = parseNode(eclaz, annotation, otherAnnotations);
		else if (annotationKey.equals(DiagraphKeywords.LINK)
				|| annotationKey.startsWith(DiagraphKeywords.LINK + ":"))
			classoc = parseLink(eclaz, annotation);
		else if (annotationKey.equals(DiagraphKeywords.REFERENCE))
			rassoc = parseReference(eclaz);
		else if (annotationKey.equals(DiagraphKeywords.KREFERENCE_)) {
			List<IDiagraphReferenceAssociation> rassocs = parseKReferences_new_(// FP150505voir
					eclaz, annotationValue_, annotationArgument2, label);// FP150407cc

		} else if (annotationKey.equals(DiagraphKeywords.LNK)) {

			classoc = parseLnk_new(eclaz, annotationValue_,
					annotationArgument2, label);
		} else if (annotationKey.equals(DiagraphKeywords.CREFERENCE_)) {

			// rassoc = parseCReference_new_u(eclaz, annotationValue,
			// annotationArguments);
			List<IDiagraphReferenceAssociation> rassocs = parseCReferences_new_(// FP150505voir
					eclaz, annotationValue_, label);// FP150407cc

		} else if (annotationKey.equals(DiagraphKeywords.AFFIXED_))
			rassoc = parsePReference(eclaz, annotationValue_);
		else if (annotationKey.equals(DiagraphKeywords.LINK_SOURCE))
			classoc = getLinkWithSource(eclaz);
		else if (annotationKey.equals(DiagraphKeywords.LINK_TARGET))
			classoc = parseLnkAssocs(eclaz); // FP121008
		else if (annotationKey.equals(DiagraphKeywords.IN_COMPARTMENT_NU))
			rassoc = parseCompartment(eclaz);

	}

	private IDiagraphReferenceAssociation parseCompartment(EClass eclaz) {
		if (DParams.TODO_LOG_)
			cerror("TODO implement parse... ");
		return null;
	}

	private IDiagraphClassAssociation parseLnkAssocs(EClass eclaz) {
		if (DParams.TODO_LOG_)
			cerror("TODO implement parse... ");
		return null;
	}

	private IDiagraphClassAssociation getLinkWithSource(EClass eclaz) {
		if (DParams.TODO_LOG_)
			cerror("TODO implement parse... ");
		return null;
	}

	private IDiagraphReferenceAssociation parsePReference(EClass eclaz,
			String value) {
		if (DParams.TODO_LOG_)
			cerror("TODO implement parse... ");
		return null;
	}

	private IDiagraphClassAssociation parseLnk_new(EClass eclaz,
			String annotationValue_, String annotationArgument, String label) {

		if (DParams.TODO_LOG_)
			cerror("TODO implement parse... ");
		/*
		 * if (annotationValue_ == null) throw new RuntimeException(
		 * "target reference should not be null for " + eclaz.getName() + "." +
		 * annotationValue_); // FP150427
		 */
		// List<IDiagraphReferenceAssociation> assocabs =
		// parseContainmentReferenceIfAbstract_(
		// eclaz, annotationValue_);

		// if (DParams.Parser_CONTAINMENT2_LOG);
		return null;
	}

	private IDiagraphReferenceAssociation parseReference(EClass eclaz) {
		if (DParams.TODO_LOG_)
			cerror("TODO implement parse... ");
		return null;
	}

	private IDiagraphElement parseLabels(EClass eclaz) {
		if (DParams.TODO_LOG_)
			cerror("TODO implement parse... ");
		return null;
	}

	private IDiagraphElement parseLabel(EClass eclaz) {
		if (DParams.TODO_LOG_)
			cerror("TODO implement parseLabel");
		return null;
	}

	private IDiagraphReferenceAssociation parseHostContainment(EClass eclaz) {
		notation.getCurrentView().getViewId();
		if (DParams.TODO_LOG_)
			cerror("TODO implement parseHostContainment");
		return null;
	}

	/*
	 * private IDiagraphNode parseNode(EClass eclaz) {
	 * cerror("TODO implement parseNode"); return null; }
	 */

	@Override
	public EReference getDeclaredContainment(EClass eclaz, int depth) {
		int caze = 0;
		EReference declaredContainerReference = null;
		String declaredContention = getTagValues(eclaz, "cont").get(0);
		String declaredContentionClass = (declaredContention == null ? null
				: declaredContention.substring(0,
						declaredContention.indexOf(".")));

		if (declaredContentionClass != null) {
			caze = 2;
			boolean inherited = false;
			if (!eclaz.getName().equals(declaredContentionClass)) {
				EClass declaredContainmentClass = (EClass) eclaz.getEPackage()
						.getEClassifier(declaredContentionClass);

				if (declaredContainmentClass.isSuperTypeOf(eclaz)) {
					inherited = true;
					caze = 3;
				}
			}
			List<EReference> srcrefs1 = inherited ? eclaz.getEAllReferences()
					: getAllDiagraphContainers(eclaz);
			for (EReference srcref : srcrefs1) {
				if (srcref.isContainment()
						&& ((EClass) srcref.eContainer()).getName().equals(
								declaredContentionClass)) {
					declaredContainerReference = srcref;
					break;
				}
			}
			if (declaredContainerReference == null) {
				List<EReference> srcrefs2 = getAllDiagraphContainers(eclaz);
				for (EReference srcref : srcrefs2) {
					if (srcref.isContainment()
							&& ((EClass) srcref.eContainer()).getName().equals(
									declaredContentionClass)) {
						declaredContainerReference = srcref;
						break;
					}
				}
			}
			if (declaredContainerReference != null) {
				if (!(((EClass) (declaredContainerReference.eContainer()))
						.getName() + "." + declaredContainerReference.getName())
						.equals(declaredContention))
					declaredContainerReference = null;
			}
			if (declaredContainerReference == null) {
				List<EClass> supes = getSuperGenericsOrNode(eclaz, false);// not_only_concrete
				for (EClass supe : supes) {
					List<EReference> refs = getAllDiagraphContainers(supe);
					for (EReference srcref : refs) {
						if (srcref.isContainment()
								&& ((EClass) srcref.eContainer()).getName()
										.equals(declaredContentionClass)) {
							declaredContainerReference = srcref;
							caze = 4;
							break;
						}
					}

				}
			}
		}

		IDiagraphNode srcDiagraphNode = getDiagraphNode(eclaz, depth);// ,
																		// false);
		String n = srcDiagraphNode.getEClass().getName();
		clog(srcDiagraphNode.getEClass().getName());

		List<EReference> acrfs = srcDiagraphNode.getAllContainmentReferences();
		clog("containmentReferences to " + srcDiagraphNode.getName());
		int i = 1;
		for (EReference eReference : acrfs) {
			clog(i++ + "                   :" + eReference.getName());
		}
		clog("declared containment:"
				+ (declaredContainerReference == null ? "none"
						: declaredContainerReference.getName()));
		return declaredContainerReference;
	}

	@Override
	public EReference getContainment(EClass source) {
		List<EReference> allconts = parseAllContainementReferences(source);
		if (allconts.size() > 1) {
			for (EReference srcref : allconts) {
				EClass srclaz = (EClass) srcref.eContainer();
				EReference dcl = getDeclaredContainment(srclaz, 0); // FP140505
				if (dcl != null)
					return dcl;
			}
		} else
			return allconts.isEmpty() ? null : allconts.get(0);
		return null;
	}

	@Override
	public List<EReference> getAllDiagraphContainers(EClass eclass) {
		List<EReference> containers = getAllDiagraphContainers(
				getClassesInView(eclass.getEPackage()), eclass);
		return containers;
	}

	private List<EClass> getClassesInView(EPackage pack) {
		List<EClass> result = new ArrayList<EClass>();
		for (EClassifier eClassifier : pack.getEClassifiers()) {
			if (eClassifier instanceof EClass) {
				for (EAnnotation eAnnotation : ((EClass) eClassifier)
						.getEAnnotations())
					if (eAnnotation.getSource().equals(
							DiagraphKeywords.DIAGRAPH_LITTERAL)
							&& isView(eAnnotation)) {
						if (!result.contains(eClassifier))
							result.add((EClass) eClassifier);
					}
			}
		}
		return result;
	}

	@Override
	public boolean isAnnotated(EClass eclaz, String name,
			IDiagraphAssociation.AssociationType c, InheritanceType inhtype) {
		if (inhtype == InheritanceType.DIRECT)
			return isAnnotated(eclaz, name, c);
		else
			for (EClass superclaz : getSuperGenericsOrNode(eclaz, false))
				// not only concrete
				if (isAnnotated(superclaz, name, c))
					return true;
		return false;
	}

	public boolean isAnnotated(EClass eclaz, String name,
			IDiagraphAssociation.AssociationType c) { // FP131209
		if (!isNode(eclaz) && !isGeneric(eclaz))
			throw new RuntimeException("in view "
					+ getCurrentPointOfView().getViewId() + " - "
					+ eclaz.getName() + "." + name
					+ ": -- isAssociationType must apply on a node but "
					+ eclaz.getName() + " is not");
		for (EAnnotation eAnnotation : eclaz.getEAnnotations()) {
			if (isView(eAnnotation)) {

				String key = "void";
				// if (c==AssociationType.TYPED_COMPARTMENT)//FP150512transp
				// tb = true;
				switch (c) {
				case TYPED_COMPARTMENT:
					key = DiagraphKeywords.KREFERENCE_;
					break;
				case SHARED_COMPARTMENT_:
					key = DiagraphKeywords.CREFERENCE_;
					break;
				case AT_CONTAINMENT:
					key = DiagraphKeywords.CONTAINMENT; // FP150512
					break;
				case AT_PORT:
					key = DiagraphKeywords.AFFIXED_;
					break;
				case SIMPLE:
					key = DiagraphKeywords.REFERENCE;
					break;
				case CLASSOC_:
					key = DiagraphKeywords.LNK;

					break;
				// case LINK:
				// key = "link_nu";
				// break;
				case EXTERNAL:
					key = "nav"; // TODO
					break;
				case VOID_:
					key = "void"; // TODO
					break;
				default:
					key = "unknown";
					break;
				}
				for (Map.Entry<String, String> entry : eAnnotation.getDetails()) {
					if (entry.getKey().startsWith(key + "=")) {
						String[] k = entry.getKey().split("=");
						String k1 = k[1];
						String arg = null;
						if (k[0].equals(DiagraphKeywords.KREFERENCE_)
								&& k[1].contains(",")) {
							String[] sub = k[1].split(",");// FP150512transp
							k1 = sub[0];
							arg = sub[1];
							if (LOG)
								clog("kref with depth arg: kref=" + k1 + ","
										+ arg);
						}
						if (k1.equals(name))
							return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public List<EReference> getAllReferences(EPackage pack, boolean containment) {
		List<EReference> allReferences = new ArrayList<EReference>();
		List<EClassifier> classifiers = pack.getEClassifiers();
		for (EClassifier eClassifier : classifiers) {
			if (eClassifier instanceof EClass)
				for (EReference eRef : ((EClass) eClassifier)
						.getEAllReferences())
					if (((!containment && !eRef.isContainment()) || (containment && eRef
							.isContainment())) && !allReferences.contains(eRef))
						allReferences.add(eRef);
		}
		return allReferences;
	}

	@Override
	public List<EReference> getAllReferences(EClass eClass, boolean containment) {
		List<EReference> allReferences = new ArrayList<EReference>();
		for (EReference eRef : eClass.getEAllReferences())
			if (((!containment && !eRef.isContainment()) || (containment && eRef
					.isContainment())) && !allReferences.contains(eRef))
				allReferences.add(eRef);
		return allReferences;
	}

	@Override
	public List<EClass> getSubDiagraphedClasses_(NodeType nodeType, EClass c,
			boolean concrete) {

		List<EClass> diagraphedClasses_ = getDiagraphedClasses(nodeType,
				concrete, c.getEPackage());
		List<EClass> result = new ArrayList<EClass>();
		if (diagraphedClasses_ != null)
			for (EClass diagraphedClass : diagraphedClasses_)
				if (!diagraphedClass.equals(c)
						&& c.isSuperTypeOf(diagraphedClass))
					result.add(diagraphedClass);
		return result;
	}

	@Override
	public List<EClass> getConcreteSubNodes_(EClass claz) {
		return getSubDiagraphedClasses_(NodeType.NODE, claz, true);
	}

	@Override
	public List<EClass> getSubNodes(EClass claz, boolean concrete) {
		return getSubDiagraphedClasses_(NodeType.NODE, claz, concrete);
	}

	@Override
	public List<EClass> getSubLinks(EClass claz, boolean concrete) {
		return getSubDiagraphedClasses_(NodeType.LINK, claz, concrete);
	}

	// TODO rename concrete
	@Override
	public List<EClass> getConcreteSubLinks(EClass claz) {
		return getSubDiagraphedClasses_(NodeType.LINK, claz, true);
	}

	@Override
	public List<EClass> getSubGenericsOrNodes_(EClass claz) {
		if (LOG)
			clog("getSubGenericsOrNodes");
		return getSubDiagraphedClasses_(NodeType.GENERIC_OR_NODE, claz, true);
	}

	@Override
	public List<EClass> getSubGenericsOrLinks(EClass claz) {
		return getSubDiagraphedClasses_(NodeType.GENERIC_OR_LINK, claz, true);
	}

	@Override
	public List<EClass> getSubGenerics(EClass claz) {
		return getSubDiagraphedClasses_(NodeType.GENERIC, claz, true);
	}

	@Override
	public List<EClass> getSuperNodes(EClass claz, boolean concrete) {
		return getSuperDiagraphedClasses(NodeType.NODE, claz, concrete);
	}

	@Override
	public List<EClass> getSuperLinks(EClass claz) {
		return getSuperDiagraphedClasses(NodeType.LINK, claz, true);
	}

	@Override
	public List<EClass> getSuperGenerics(EClass claz) {
		return getSuperDiagraphedClasses(NodeType.GENERIC, claz, true);
	}

	@Override
	public List<EClass> getSuperGenericsOrLinks(EClass claz) {
		return getSuperDiagraphedClasses(NodeType.GENERIC_OR_LINK, claz, true);
	}

	@Override
	public List<EClass> getSuperGenericsOrNode(EClass claz, boolean concrete) {
		if (LOG)
			clog("getSuperGenericsOrNode");
		return getSuperDiagraphedClasses(NodeType.GENERIC_OR_NODE, claz,
				concrete);
	}

	@Override
	public List<EClass> getSuperDiagraphedClasses(NodeType nodeType, EClass c,
			boolean concrete) {
		List<EClass> diagraphedClasses = getDiagraphedClasses(nodeType,
				concrete, c.getEPackage());
		List<EClass> result = new ArrayList<EClass>();
		for (EClass diagraphedClass : diagraphedClasses) {
			if (!diagraphedClass.equals(c) && diagraphedClass.isSuperTypeOf(c))
				result.add(diagraphedClass);
		}
		Collections.reverse(result);
		return result;
	}

	@Override
	public List<EClass> getDiagraphedClasses(NodeType nodeType,
			boolean concrete, EPackage pack) {
		List<EClass> clazes = new ArrayList<EClass>();
		List<EAnnotation> eans = new ArrayList<EAnnotation>();
		for (EClassifier eClassifier : pack.getEClassifiers())
			if (eClassifier instanceof EClass) {
				EAnnotation toAdd = null, ean = getDiagraphAnnotation((EClass) eClassifier);
				if (ean != null) {
					switch (nodeType) {
					case NODE:
						toAdd = isNode(ean) ? ean : null;
						break;
					case LINK:
						toAdd = isLink(ean) ? ean : null;
						break;
					case GENERIC:
						toAdd = isGeneric(ean) ? ean : null;
						break;
					case GENERIC_OR_LINK:
						toAdd = isGenericOrLink(ean) ? ean : null;
						break;
					case GENERIC_OR_NODE:
						toAdd = isGenericOrNode(ean) ? ean : null;
						break;
					default:
						toAdd = null;
						break;
					}
					if (toAdd != null)
						eans.add(toAdd);
				}
			}

		for (EAnnotation eAnnotation : eans) {
			EClass toAdd = (EClass) eAnnotation.getEModelElement();
			if ((concrete && !toAdd.isAbstract()) || !concrete)
				if (!clazes.contains(eAnnotation.getEModelElement()))
					clazes.add((EClass) eAnnotation.getEModelElement());
		}

		return clazes;
	}

	@Override
	public boolean isNode(EAnnotation eAnnotation) {
		for (Map.Entry<String, String> entry : eAnnotation.getDetails())
			if (entry.getKey().equals("node"))
				return true;
		return false;
	}

	@Override
	public boolean isLink(EAnnotation eAnnotation) {
		for (Map.Entry<String, String> entry : eAnnotation.getDetails())
			if (entry.getKey().equals("link"))
				return true;
		return false;
	}

	@Override
	public boolean isGenericOrLink(EAnnotation eAnnotation) {
		boolean link = false;
		boolean node = false;
		for (Map.Entry<String, String> entry : eAnnotation.getDetails()) {
			if (entry.getKey().equals("node"))
				node = true;
			if (entry.getKey().equals("link"))
				link = true;
		}
		return link || (!node && !link);
	}

	@Override
	public boolean isGenericOrNode(EAnnotation eAnnotation) {
		boolean link = false;
		boolean node = false;
		for (Map.Entry<String, String> entry : eAnnotation.getDetails()) {
			if (entry.getKey().equals("node"))
				node = true;
			if (entry.getKey().equals("link"))
				link = true;
		}
		return node || (!node && !link);
	}

	private boolean isNode_(EAnnotation eAnnotation, String viewid) {
		EClass ecl = (EClass) eAnnotation.getEModelElement();
		if (isView(eAnnotation, viewid))
			for (Map.Entry<String, String> entry : eAnnotation.getDetails())
				if (entry.getKey().equals("node"))
					return true;
		return false;
	}

	private boolean isGeneric(EAnnotation eAnnotation, String viewid) {
		boolean link = false;
		boolean node = false;
		if (isView(eAnnotation, viewid)) {
			for (Map.Entry<String, String> entry : eAnnotation.getDetails()) {
				if (entry.getKey().equals("node"))
					node = true;
				if (entry.getKey().equals("link"))
					link = true;
			}
			return !node && !link;
		} else
			return false;
	}

	@Override
	public List<EClass> subClasses(EClass c) {
		List<EClass> result = new ArrayList<EClass>();
		for (EClassifier eClassif : c.getEPackage().getEClassifiers()) {
			if (eClassif instanceof EClass)
				if (!eClassif.equals(c) && c.isSuperTypeOf((EClass) eClassif))
					result.add((EClass) eClassif);

		}
		return result;
	}

	@Override
	public EAnnotation getDiagraphAnnotation(EClass eclass) {
		EAnnotation result = null;
		// if (viewid == null || viewid.isEmpty())
		// viewid = DiagraphKeywords.DIAGRAPH_DEFAULT;
		int count = 0;
		for (EAnnotation e : eclass.getEAnnotations())
			if (e.getSource().equals(DiagraphKeywords.DIAGRAPH_LITTERAL)
					&& getViewName(e).equals(
							notation.getCurrentView().getViewId())) {
				result = e;
				count++;
			}
		if (count > 1)
			throw new RuntimeException(
					"more than one Diagraph Annotation for EClass: "
							+ eclass.getName() + " and view "
							+ notation.getCurrentView().getViewId());
		return result;
	}

	@Override
	public boolean patternContainedThroughInheritance(EClass claz) {
		if (isSelfContained(claz)) {
			if (LOG)
				clog("isContainedThroughInheritance (sterr100s)");
			return false;
		}
		if (LOG)
			clog("NOT isContainedThroughInheritance (sterr100s)");
		List<EReference> sterr100s = multipleContentionThroughInheritancePattern(claz);
		return sterr100s != null;
	}

	@Override
	public boolean isSelfContained(EClass claz) {
		for (EReference cont : getDirectContainementReferences(claz)) {
			// EClass c = (EClass) (cont.getEType());
			EClass c = (EClass) (cont.eContainer());
			if (c == claz)
				return true;
		}
		return false;
	}

	private List<EReference> multipleContentionThroughInheritancePattern(
			EClass claz) {
		List<EReference> result = parseAllContainementReferences(claz);
		for (EReference cont : result) {
			EClass rt = (EClass) (cont.getEType());
			if (rt != null && rt.isSuperTypeOf(claz) && rt != claz)
				return result;
		}
		return null;
	}

	/*
	 * private void inferLinkTargetReferences() { // FP140503
	 * List<IDiagraphClassAssociation> assocs = getClassAssociations(); for
	 * (IDiagraphClassAssociation link : assocs) { if (link.getTargetReference()
	 * == null) inferLinkTargetReference(link); } }
	 */

	@Override
	public boolean isDirectTopNode(EClass claz) {
		for (EReference cont : getDirectContainementReferences(claz))
			if (isPov((EClass) cont.eContainer())) {
				if (LOG)
					clog(claz.getName() + " is contained (directly) by POV: "
							+ ((EClass) cont.eContainer()).getName());
				return true;
			}
		return false;
	}

	private void inferClassAssociationTargetReference(
			IDiagraphClassAssociation classoc) {
		if (LOG)
			clog("inferClassAssociationTargetReference for "
					+ classoc.getName());
		classoc.resolveTargetReference();
		if (classoc.getTargetReference() == null) {
			if (!((EClass) classoc.getType()).isAbstract())// FP130731
				throw new RuntimeException("link.TargetReference == null for "
						+ classoc.getName());
		}
		// assert link.getTargetReference() != null;
		if (LOG)
			clog("inferred targetReference for " + classoc.getName() + " : ["
					+ classoc.getTargetReference().getName() + "]");
	}

	private void inferReferenceAssociationTargetReference(
			IDiagraphReferenceAssociation rassoc) {
		if (LOG)
			clog("inferReferenceAssociationTargetReference for "
					+ rassoc.getName());

		IDiagraphAssociation.AssociationType ct = rassoc.getAssociationType();
		switch (ct) {
		case SIMPLE:
			break;
		case TYPED_COMPARTMENT:
			break;
		case SHARED_COMPARTMENT_:
			break;
		case AT_CONTAINMENT:
			break;
		case AT_PORT:
			break;
		default:
			break;
		}
		rassoc.resolveTargetReference();
		if (rassoc.getTargetReference() == null) {
			if (!((EClass) rassoc.getType()).isAbstract())// FP130731
				throw new RuntimeException("link.TargetReference == null for "
						+ rassoc.getName());
		}
		// assert link.getTargetReference() != null;
		if (LOG)
			clog("inferred targetReference for " + rassoc.getName() + " : ["
					+ rassoc.getTargetReference().getName() + "]");
	}

	private IDiagraphAssociation createDiagraphReferenceAssociation(
			IDiagraphAssociation.AssociationType containmentType_,
			EReference reference, EClass srcClaz, EClass trgClaz) {
		IDiagraphNode src = getDiagraphNode(srcClaz, 0);
		IDiagraphNode trg = getDiagraphNode(trgClaz, 0);
		List<EClass> emptySubHierarchy = new ArrayList<EClass>();
		// SIMPLE, TYPED_COMPARTMENT, SHARED_COMPARTMENT, PORT, EXTERNAL, VOID}
		// ContainmentType containmentType = ContainmentType.VOID;
		String info = "";

		boolean container = containmentType_ == IDiagraphAssociation.AssociationType.SIMPLE;
		IDiagraphReferenceAssociation diagraphReferenceAssociation = new DiagraphReferenceAssociation(
				this, notation.getCurrentView().getViewId(),

				src.getEClass(), src.getEClass(), trg.getEClass(),
				emptySubHierarchy, reference, containmentType_, false,
				container, info);

		int i = 0;
		try {

			inferRefContainment(diagraphReferenceAssociation);
			if (diagraphReferenceAssociation.getContainment() == null)
				cerror("containement for DiagraphRefAssociation: "
						+ diagraphReferenceAssociation.getName()
						+ " is null for view "
						+ notation.getCurrentView().getViewId());
			i = 1;

			inferReferenceAssociationTargetReference(diagraphReferenceAssociation);
			if (diagraphReferenceAssociation.getTargetReference() == null)
				cerror("targetReference for DiagraphRefAssociation: "
						+ diagraphReferenceAssociation.getName()
						+ " is null for view "
						+ notation.getCurrentView().getViewId());

		} catch (Exception e) {
			throw new RuntimeException("error: " + i
					+ " in createDiagraphReferenceAssociation " + " for "
					+ reference.getName() + " (" + e.toString() + ")");
		}

		return diagraphReferenceAssociation;
	}

	/************ -------------------- ************/
	private void inferRefContainment(
			IDiagraphReferenceAssociation refAssociation) {
		IDiagraphAssociation.AssociationType ct = refAssociation
				.getAssociationType();
		// //SIMPLE, TYPED_COMPARTMENT, SHARED_COMPARTMENT, PORT, EXTERNAL,
		// VOID}
		throw new RuntimeException("TODO implement inferRefContainment");
	}

	private void inferReferenceAssociationContainment(
			IDiagraphReferenceAssociation rassoc) {

		IDiagraphAssociation.AssociationType assocType = rassoc
				.getAssociationType();

		if (assocType == null)
			assocType = IDiagraphAssociation.AssociationType.VOID_;
		if (DParams.Parser_CONTAINMENT2_LOG)
			clog2("inferRefContainment");

		switch (assocType) {
		case SIMPLE:
			if (DParams.Parser_CONTAINMENT2_LOG)
				clog2(DiagraphKeywords.REFERENCE);
			break;
		case TYPED_COMPARTMENT:
			if (DParams.Parser_CONTAINMENT2_LOG)
				clog2(DiagraphKeywords.KREFERENCE_);
			break;
		case SHARED_COMPARTMENT_:
			if (DParams.Parser_CONTAINMENT2_LOG)
				clog2(DiagraphKeywords.CREFERENCE_);
			break;
		case AT_CONTAINMENT:
			if (DParams.Parser_CONTAINMENT2_LOG)
				clog2(DiagraphKeywords.CONTAINMENT);
			break;
		case AT_PORT:
			if (DParams.Parser_CONTAINMENT2_LOG)
				clog2(DiagraphKeywords.AFFIXED_);
			break;
		/*
		 * case CLASSOC: if (DParams.Parser_CONTAINMENT2_LOG) clog2("link");
		 * break;
		 */
		case EXTERNAL:
			if (DParams.Parser_CONTAINMENT2_LOG)
				clog2(DiagraphKeywords.OPEN_DIAGRAM);
			break;
		case VOID_:
			if (DParams.Parser_CONTAINMENT2_LOG)
				clog2("nil");
			break;

		default:
			break;
		}

		List<EReference> containements = getSimpleReferencesTo_(rassoc
				.getType());
		List<EReference> allContainements = getAllReferencesTo((EClass) rassoc
				.getType());
		if (containements.size() == 0 && allContainements.size() == 0) { // FP140423xxx
			cerror("(8)no containment for reference association"
					+ rassoc.getName() + " in view " + rassoc.getView());
			return;
		} else {
			if (DParams.Parser_CONTAINMENT2_LOG) {
				clog2("containments for " + rassoc.getName());
				for (EReference containement : containements)
					clog2(containement.getName());

				clog2("allContainements for " + rassoc.getName());
				for (EReference containement : allContainements)
					clog2(containement.getName());

			}
		}

		boolean hasContainements = containements.size() > 0;

		boolean isContainedInheritance = isContainedThroughInheritance(rassoc);

		if (!hasContainements)
			containements = allContainements;
		if (containements.size() == 1)
			inferAssociationContainment(rassoc, containements.get(0));
		else
			throw new RuntimeException(
					"unable to infer containment for ReferenceAssociation "
							+ rassoc.getName());
	}

	private void inferClassAssociationContainment(
			IDiagraphClassAssociation classoc) {
		List<EReference> containements = getSimpleReferencesTo_(classoc
				.getType());
		List<EReference> allContainements = getAllReferencesTo((EClass) classoc
				.getType());
		if (containements.size() == 0 && allContainements.size() == 0) { // FP140423xxx
			cerror("(8)no containment for class association"
					+ classoc.getName() + " in view " + classoc.getView());
			return;
		} else {
			if (DParams.Parser_CONTAINMENT2_LOG) {
				clog2("containments for " + classoc.getName());
				for (EReference containement : containements)
					clog2(containement.getName());

				clog2("allContainements for " + classoc.getName());
				for (EReference containement : allContainements)
					clog2(containement.getName());

			}
		}
		boolean hasContainements = containements.size() > 0;
		boolean isContainedInheritance = isContainedThroughInheritance(classoc);
		if (!hasContainements)
			containements = allContainements;
		if (containements.size() == 1)
			inferAssociationContainment(classoc, containements.get(0));
		else
			throw new RuntimeException(
					"unable to infer containment for ClassAssociation "
							+ classoc.getName());

	}

	private void inferNodeContainments_() { // FP140505ppp
		try {
			for (IDiagraphNode node : getAllNodes()) {
				inferNodeContainment(node, 0);
			}
		} catch (Exception e) {
			cerror("error while inferNodeContainments " + e.toString());
		}
	}

	private void handlePatterns() {
		List<IDiagraphNode> nodes = getAllNodes();
		try {
			for (IDiagraphNode node : nodes) {
				handlePatterns(node);
			}
		} catch (Exception e) {
			cerror("error while inferNodeContainments " + e.toString());
		}
	}

	public void inferLinkContainments() {
		if (LOG)
			clog("----------  inferLinkContainments ");
		try {

			List<IDiagraphClassAssociation> classocs = getClassAssociations();
			for (IDiagraphClassAssociation classociation : classocs) {
				inferClassAssociationContainment(classociation);
				if (classociation.getContainment() == null)
					cerror("containement for DiagraphClassAssociation: "
							+ classociation.getName() + " is null ");

			}
			List<IDiagraphReferenceAssociation> rassocs = getReferenceAssociations();
			for (IDiagraphReferenceAssociation rassociation : rassocs) {
				inferReferenceAssociationContainment(rassociation);
				if (rassociation.getContainment() == null)
					cerror("containement for DiagraphReferenceAssociation: "
							+ rassociation.getName() + " is null ");

			}
		} catch (Exception e) {
			cerror("error in  inferLinkContainments");
		}
	}

	private void inferAssociationContainment(IDiagraphAssociation assoc,
			EReference containement) {// FP140503
		assoc.setContainment(containement);
		EReference declaredcontRef = getDeclaredContainment(assoc.getType(), 0); // FP140505
		EReference contRef = (EReference) assoc.getContainment();
		if (declaredcontRef != null && declaredcontRef != contRef) { // FP131009x
			if (LOG)
				clog("containing reference is now " + declaredcontRef.getName()
						+ " vs " + contRef.getName());
			contRef = declaredcontRef;
			assoc.setContainment(contRef);
		}
	}

	/************ -------------------- ************/

	private boolean isContainedThroughInheritance(IDiagraphAssociation link) {
		List<EReference> conts = upperLinkContainment(link);
		return conts != null;
	}

	private List<EReference> upperLinkContainment(IDiagraphAssociation link) {
		return upperLinkContainmentPure((EClass) link.getType());
		/*
		 * List<EReference> allContainements = getSuperReferencesTo((EClass)
		 * link .getType()); for (EReference eReference : allContainements) { if
		 * (LOG) clog(" upperContainment " + eReference.getName()); EClass rt =
		 * (EClass) (eReference.getEType()); IDiagraphNode dn = findNode_(rt);
		 * if (dn != null && rt.isSuperTypeOf((EClass) link.getType()) && rt !=
		 * link.getType()) return allContainements; } return null;
		 */
	}

	/*
	 *
	 * private List<EReference> upperContainmentNp(EClass claz) {
	 * List<EReference> result = new ArrayList<EReference>(); List<EReference>
	 * allContainements = getSuperReferencesTo(claz); for (EReference eReference
	 * : allContainements) { if (LOG) clog(" upperContainment " +
	 * eReference.getName()); EClass rt = (EClass) (eReference.getEType());
	 * IDiagraphNode dn = findNode_(rt); if (dn != null &&
	 * rt.isSuperTypeOf(claz) && rt != claz) if (isContainment(eReference))
	 * result.add(eReference); } return result; }
	 */

	public List<IDiagraphClassAssociation> getClassAssociations() {
		if (getCurrentPointOfView() != null)
			return getCurrentPointOfView().getClassAssociations();
		else
			return new ArrayList<IDiagraphClassAssociation>();
		// return allAssociations;
	}

	private List<IDiagraphReferenceAssociation> getReferenceAssociations() {
		if (getCurrentPointOfView() != null)
			return getCurrentPointOfView().getReferenceAssociations();
		else
			return new ArrayList<IDiagraphReferenceAssociation>();
	}

	@Override
	public boolean isInheritedTopNode(EClass claz) {
		if (LOG)
			clog("isInheritedTopNode");

		if (isDirectTopNode(claz)) {
			clog("isDirectTopNode");
			return false;
		}
		if (LOG)
			clog("NOT isDirectTopNode");
		for (EReference cont : parseAllContainementReferences(claz)) {
			EClass container = (EClass) cont.eContainer();
			if (container != null) {
				if (!claz.isAbstract() && isPov(container)) {
					if (LOG)
						clog(claz.getName()
								+ " is contained (through inheritance) by POV: "
								+ ((EClass) cont.eContainer()).getName()
								+ " through " + cont.getName());
					return true;
				}
				for (EClass taggedSubnode : getConcreteSubNodes_(container))
					if (isPov(container)) {
						if (LOG)
							clog(claz.getName()
									+ " is contained (through inheritance) by POV: "
									+ container.getName()
									+ " through subclass "
									+ taggedSubnode.getName());
						return true;
					}
			}
		}
		return false;
	}

	@Override
	public EClass getPovIfSuperPov(EClass claz) {
		for (EClass sub : getConcreteSubNodes_(claz))
			// FP140413 getSubGenericsOrNodes_
			if (isPov(sub))
				return sub;
		return null;
	}

	@Override
	public List<EClass> getTaggedClasses(EPackage pack) {
		List<EClass> result = new ArrayList<EClass>();
		// List<EClassifier> classifiers = pack.getEClassifiers();
		for (EClassifier eClassifier : pack.getEClassifiers())
			if (eClassifier instanceof EClass)
				if (getDiagraphAnnotation((EClass) eClassifier) != null)
					result.add((EClass) eClassifier);
		return result;
	}

	private String subNodesToString(EClass eclaz) {
		String result = "";
		List<EClass> subnodes = getConcreteSubNodes_(eclaz);

		// if (subnodes.size() != getSubNodes(eclaz).size()) // FP140403
		// throw new RuntimeException("refactoring error (getSubNodes)");

		for (EClass subclass : subnodes)
			result += nodeToString(subclass) + ";";
		result = result.substring(0, result.length() - 1);
		return result;
	}

	private String nodeToString(EClass eclaz) {
		String result = eclaz.getName();
		if (eclaz.isAbstract()) {
			result += " _a[\n";
			result += subNodesToString(eclaz);
			result += "\n]";
		}
		return result;
	}

	private EClass node(EClass eclaz) {
		if (isNode(eclaz))
			return eclaz;
		else
			return null;
	}

	private List<EClass> subNodes(EClass eclaz) {
		List<EClass> result = new ArrayList<EClass>();
		List<EClass> subnodes = getConcreteSubNodes_(eclaz);
		// if (subnodes.size() != getSubNodes(eclaz).size()) // FP140403
		// throw new RuntimeException("refactoring error (getSubNodes)");
		result.addAll(subnodes);
		for (EClass subclass : subnodes) {
			if (subclass.isAbstract()) {
				result.addAll(getConcreteSubNodes_(subclass));
				// if (getConcreteSubNodes_(subclass).size() !=
				// getSubNodes(
				// subclass).size()) // FP140403
				// throw new RuntimeException(
				// "refactoring error (getSubNodes)");
			}
		}
		return result;
	}

	private List<EClass> getSubHierarchy(EClass eclaz, boolean includeRoot) {
		List<EClass> result = new ArrayList<EClass>();
		if (includeRoot)
			result.add(eclaz);
		if (eclaz.isAbstract())
			for (EClass subclass : subNodes(eclaz))
				if (!result.contains(subclass))
					result.add(subclass);
		return result;
	}

	/*
	 * private List<List<IDiagraphReferenceAssociation>> getAssociationsTo_(
	 * EAnnotation annotation, boolean containment) { EClass eclaz = (EClass)
	 * annotation.getEModelElement(); List<EReference> refs =
	 * getNodeRefsTo_(eclaz, containment);
	 * List<List<IDiagraphReferenceAssociation>> conts = getAssociationsTo(
	 * eclaz, refs); return conts; }
	 */

	@Override
	public List<EReference> getDirectReferencesTo(EClass eclaz) {
		return getReferencesTo(eclaz, true);
	}

	@Override
	public List<EReference> getAllReferencesTo(EClass eclaz) {
		return getReferencesTo(eclaz, false);
	}

	public List<EReference> getReferencesTo(EClass eclaz, boolean direct) {
		List<EReference> result = new ArrayList<EReference>();
		EPackage p = eclaz.getEPackage();
		List<EClassifier> classes = p.getEClassifiers();
		for (EClassifier c : classes) {
			if (c instanceof EClass) {
				EClass eClass = (EClass) c;
				if (isView(eClass) != null) {
					for (EReference eRef : direct ? eClass.getEReferences()
							: eClass.getEAllReferences()) {
						if (direct && eRef.getEType().equals(eclaz)) {
							if (!result.contains(eRef))
								result.add(eRef);
						} else if (!direct
								&& ((EClass) eRef.getEType())
										.isSuperTypeOf(eclaz)) {
							if (!result.contains(eRef))
								result.add(eRef);
						}
					}
				}
			}
		}
		return result;
	}

	private List<IDiagraphReferenceAssociation> createReferenceSubContainingAssociations(
			EClass source, EClass target, EReference containment) {
		List<EReference> contrefs = getNodeRefsToNodeOrGeneric(target, true);
		boolean derived = true;
		List<IDiagraphReferenceAssociation> assocs = new ArrayList<IDiagraphReferenceAssociation>();
		boolean simple_no = false;
		for (EClass subsource : getConcreteSubNodes_(source)) {// FP140411
			assocs.add(parseReferenceAssociation_(source, subsource, target,
					containment, derived, simple_no));

			// createReferenceSubContainingAssociations_(subsource,viewid);

		}
		return assocs;

	}

	private List<IDiagraphReferenceAssociation> createReferenceSubSimpleAssociations_wh(
			EClass source, EClass target, EReference containment) {
		List<IDiagraphReferenceAssociation> assocs = new ArrayList<IDiagraphReferenceAssociation>();
		boolean derived = true;
		boolean simple_yes = true;
		for (EClass subsource : getConcreteSubNodes_(source)) {// FP140411
			assocs.add(parseReferenceAssociation_(source, subsource, target,
					containment, derived, simple_yes));
			if (LOG)
				reportLog += assocs.get(assocs.size() - 1).toString() + "\n";// " _ICTN_sub "

		}
		return assocs;

	}

	public boolean toAbstract(EReference r, EClass target) {
		boolean result = (((EClass) r.getEType()).isAbstract()
				&& target != null && target.isAbstract() && r.getEType() == target);

		return result;
	}

	private List<List<IDiagraphReferenceAssociation>> parseSimpleAssocsAndSubHierTo(
			EClass target) {
		List<EReference> relations = getNodeRefsToNodeOrGeneric(target, false);
		List<List<IDiagraphReferenceAssociation>> result = new ArrayList<List<IDiagraphReferenceAssociation>>(); // FP140330
		boolean simple_yes = true;
		boolean derived_no = false;
		if (!relations.isEmpty())
			for (EReference rel : relations) {
				if (!rel.isContainment()) {
					List<IDiagraphReferenceAssociation> assocs = new ArrayList<IDiagraphReferenceAssociation>();
					EClass sourceclaz = (EClass) rel.eContainer();
					if (!sourceclaz.isAbstract())
						assocs.add(parseReferenceAssociation_(sourceclaz,
								sourceclaz, target, rel, derived_no, simple_yes));
					for (IDiagraphReferenceAssociation sub : createReferenceSubSimpleAssociations_wh(
							sourceclaz, target, rel))
						if (!assocs.contains(sub))
							assocs.add(sub);
					result.add(assocs);
				}
			}

		if (LOG && reportLog != null && reportLog.isEmpty())
			reportLog = "empty";
		return result;
	}

	private List<IDiagraphAssociation.AssociationType> _containmentTypes(
			EClass source, EReference containment) {
		List<IDiagraphAssociation.AssociationType> result = new ArrayList<IDiagraphAssociation.AssociationType>();
		for (IDiagraphAssociation.AssociationType type : IDiagraphAssociation.ALL_CONTAINMENT_TYPES) {
			if (isAnnotated(source, containment.getName(), type))
				result.add(type);
		}
		return result;
	}

	private boolean hasK(EClass eclaz, String refName) {
		return (isAnnotated(eclaz, refName,
				IDiagraphAssociation.AssociationType.TYPED_COMPARTMENT));
	}

	protected boolean hasKref(EClass eclaz, String refName) {
		if (hasK(eclaz, refName))
			return true;
		for (EClass supr : getSuperNodes(eclaz, false)) { // FP140414qqqq
			if (hasK(supr, refName))
				return true;
		}
		return false;
	}

	@Override
	public IDiagraphReferenceAssociation getRecursiveCompartment(EClass eclaz,
			String refName) { // FP140329
		for (EClass supr : getSuperNodes(eclaz, false)) { // FP140414qqqq
			IDiagraphReferenceAssociation compart = getRecurCompartment(supr,
					refName);
			if (compart != null)
				return compart;
		}
		return null;
	}

	private List<EReference> getRefsTo_(EClass eclaz, boolean containment) {

		List<EReference> result = new ArrayList<EReference>();

		List<EReference> alrefs = getAllReferences(eclaz.getEPackage(),
				containment);
		if (false) {// FP140616voir3 //FP150318
			String ar = "";
			for (EReference eRef : alrefs)
				ar += ((EClass) eRef.eContainer()).getName() + "."
						+ eRef.getName() + ":"
						+ ((EClass) eRef.getEType()).getName() + ";";
			clog((containment ? " containment" : " not containment")
					+ " allRefs in package " + eclaz.getEPackage().getName()
					+ "=" + (ar.isEmpty() ? "none" : ar));
		}

		for (EReference eRef : alrefs) {
			if (eRef.getEType() == eclaz)
				if (!result.contains(eRef))
					result.add(eRef);

			if (subClasses((EClass) eRef.getEType()).contains(eclaz))
				if (!result.contains(eRef))
					result.add(eRef);

			for (EClass sup : eclaz.getESuperTypes()) {
				if (eRef.getEType() == sup)
					if (!result.contains(eRef))
						result.add(eRef);
			}
		}
		if (false) { // FP150318
			String rt = "";
			for (EReference eReference : result)
				// FP140616voir2
				rt += eReference.getName() + ";";
			clog((containment ? " containment" : " not containment")
					+ " getRefsTo " + eclaz.getName() + "="
					+ (rt.isEmpty() ? "none" : rt));
		}

		return result;
	}

	@Override
	public List<EReference> getNodeRefsToNodeOrGeneric(EClass eclaz,
			boolean containment) {
		List<EReference> result = new ArrayList<EReference>();
		for (EReference eReference : getRefsTo_(eclaz, containment)) {
			if ((isNode((EClass) eReference.getEType()) || isGeneric((EClass) eReference
					.getEType())) && (isNode((EClass) eReference.eContainer())))
				if (!result.contains(eReference))
					result.add(eReference);
		}
		return result;
	}

	public List<EReference> getNodeRefsToOld_nu(EClass eclaz,
			boolean containment) {
		List<EReference> result = new ArrayList<EReference>();
		for (EReference eReference : getRefsTo_(eclaz, containment)) {
			if ((isNode((EClass) eReference.getEType()) || isGeneric((EClass) eReference
					.getEType())) && (isNode((EClass) eReference.eContainer())))
				if (!result.contains(eReference))
					result.add(eReference);
		}
		return result;
	}

	@Override
	public List<IDiagraphReferenceAssociation> parseAllAssociationsTo(
			EClass eclaz, boolean containment) {
		List<IDiagraphReferenceAssociation> result = getAssociationsAndSubHierTo(
				eclaz, containment);
		if (LOG)
			for (IDiagraphReferenceAssociation assoc : result)
				clog(assoc.toString());
		return result;
	}

	public List<IDiagraphReferenceAssociation> handlePatternSibling(
			List<IDiagraphReferenceAssociation> rafs, EClass eclaz) {
		List<IDiagraphReferenceAssociation> result = new ArrayList<IDiagraphReferenceAssociation>();
		for (IDiagraphReferenceAssociation raf : rafs) {
			if (patternSibling(raf, eclaz)) {
				if (DParams.Parser_15_LOG)
					clog15("pattern sibling for " + raf.toId());
				result.add(raf); // FP140408 //FP150324pattern
				raf.addSibling(eclaz);
			}
		}
		return result;
	}

	@Override
	public List<IDiagraphReferenceAssociation> patternSiblingAllContainmentAssociations(
			EClass eclaz) {
		boolean containment = true;
		// boolean abztract = false;// FP150518azer
		return handlePatternSibling(parseAllAssociationsTo(eclaz, containment),
				eclaz);
	}

	public List<IDiagraphReferenceAssociation> parseSimpleAssociationsToparseDirectContainingAssociationsTo_(
			EClass eclaz) {
		return handlePatternxTargetAbstract_(parsexAssociationsTo(eclaz), eclaz);
	}

	private List<IDiagraphReferenceAssociation> handlePatternxTargetAbstract_(
			List<IDiagraphReferenceAssociation> rafs, EClass eclaz) {
		// TODO Auto-generated method stub
		return null;
	}

	private List<IDiagraphReferenceAssociation> parsexAssociationsTo(
			EClass eclaz) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * for (IDiagraphReferenceAssociation raf : l) if (!raf.contains(caz))
	 * raf.addCase(caz);
	 */

	private List<IDiagraphReferenceAssociation> getAssociationsAndSubHierTo(
			EClass eclaz, boolean containment) {
		if (containment)
			return getContainmentAssociationsAndSubHierTo(eclaz);
		else
			return getSimpleAssociationsAndSubHierTo(eclaz);
	}

	@Override
	public List<EReference> parseAllContainementReferences(EClass eclaz) {// getAllContainements
		if (LOG)
			clog("getAllContainementReferences");
		List<EReference> result = new ArrayList<EReference>();
		for (IDiagraphReferenceAssociation assoc : getContainmentAssociationsAndSubHierTo(eclaz))
			if (!result.contains(assoc.getTargetReference()))
				result.add(assoc.getTargetReference());
		return result;
	}

	private List<EReference> union(List<EReference> a, List<EReference> b) {
		List<EReference> result = new ArrayList<EReference>();
		result.addAll(a);
		for (EReference r : b)
			if (!result.contains(r))
				result.add(r);
		return result;
	}

	@Override
	public List<EReference> getAllDiagraphContainers(EClass eclass,
			EClass target) {
		List<EReference> containers = getAllDiagraphContainers(
				getConcreteSubNodes_(eclass), target);

		// if (containers.size() != getSubNodes(eclass).size()) //
		// FP140403
		// throw new RuntimeException("refactoring error (getSubNodes)");

		return containers; // TODO verify
	}

	@Override
	public EReference getPovDiagraphContainer(EClass eclass) {
		return getPovDiagraphContainer(getConcreteSubNodes_(eclass), eclass);// TODO
																				// verify
	}

	private EReference getPovDiagraphContainer(List<EClass> viu, EClass target) {
		for (EClass eclaz : viu) {
			for (EReference eReference : eclaz.getEReferences())
				if (eReference.isContainment()
						&& eReference.getEType() == target) {
					EAnnotation annot = getNodeAnnotation((EClass) eReference
							.eContainer());
					if (annot != null && isPov(annot))
						return eReference;
				}
		}
		return null;
	}

	private List<EReference> getAllDiagraphContainers(List<EClass> viu,
			EClass target) { // FP140402
		List<EReference> result_ = new ArrayList<EReference>();
		for (EClass eclaz : viu) {
			for (EReference eReference : eclaz.getEReferences())
				if (eReference.isContainment()
						&& eReference.getEType() == target) {
					EAnnotation annot = getNodeAnnotation((EClass) eReference
							.eContainer());
					if (annot != null) {
						String mat = matches(DiagraphKeywords.KREFERENCE_EQU_,
								annot, eReference)
								+ matches(DiagraphKeywords.CREFERENCE_EQU,
										annot, eReference)
								+ matches(DiagraphKeywords.AFFIXED_EQU_, annot,
										eReference);
						if ((!mat.isEmpty() || isPov(annot))
								&& !result_.contains(eReference)) {
							result_.add(eReference);
						}
					}
				}
		}
		return result_;
	}

	private boolean isPov(EAnnotation annot) {
		for (Map.Entry<String, String> entry : annot.getDetails())
			if (entry.getKey().equals("pov"))
				return true;
		return false;
	}

	private String matches(String kw, EAnnotation annot, EReference ref) {
		for (Map.Entry<String, String> entry : annot.getDetails())
			if (entry.getKey().startsWith(kw)) {
				String k = entry.getKey();
				if (k != null) {
					String equ = k.substring(kw.length());
					if (equ.equals(ref.getName()))
						return equ;
				}
			}
		return "";
	}

	private EAnnotation getLinkAnnotation(EClass eClaz) {
		for (EAnnotation eAnnotation : eClaz.getEAnnotations())
			if (eAnnotation.getSource().equals(
					DiagraphKeywords.DIAGRAPH_LITTERAL))
				if (isView(eAnnotation))
					if (hasKey(eAnnotation, DiagraphKeywords.LINK))
						return eAnnotation;
		return null;
	}

	private EAnnotation getNodeAnnotation(EClass eClaz) {
		for (EAnnotation eAnnotation : eClaz.getEAnnotations())
			if (eAnnotation.getSource().equals(
					DiagraphKeywords.DIAGRAPH_LITTERAL))
				if (isView(eAnnotation))
					if (hasKey(eAnnotation, DiagraphKeywords.NODE))
						return eAnnotation;
		return null;
	}

	@Override
	public boolean isCurrentView(EClass c) {
		return getNodeAnnotation(c) != null || getLinkAnnotation(c) != null;
	}

	private boolean hasKey(EAnnotation anot, String key) {
		for (Map.Entry<String, String> entry : anot.getDetails())
			if (entry.getKey().equals(key))
				return true;
		return false;
	}

	@Override
	public List<EReference> getDirectContainementReferences(EClass eclaz) { // getDirectContainements
		if (LOG)
			clog("getDirectContainementReferences");
		List<EReference> result = new ArrayList<EReference>();
		for (IDiagraphReferenceAssociation assoc : getContainmentAssociationsAndSubHierTo(eclaz))
			if (!assoc.isTargetAbstract()
					&& !result.contains(assoc.getTargetReference()))
				result.add(assoc.getTargetReference());
		return result;
	}

	@Override
	public List<EReference> getAllSimpleReferences(EClass eclaz) {// getAllContainements
		List<EReference> result = new ArrayList<EReference>();
		for (IDiagraphReferenceAssociation assoc : getSimpleAssociationsAndSubHierTo(eclaz))
			if (!result.contains(assoc.getTargetReference()))
				result.add(assoc.getTargetReference());
		return result;
	}

	@Override
	public List<EReference> getDirectSimpleReferences(EClass eclaz) { // getDirectContainements
		List<EReference> result = new ArrayList<EReference>();
		for (IDiagraphReferenceAssociation assoc : getSimpleAssociationsAndSubHierTo(eclaz))
			if (!assoc.isTargetAbstract()
					&& !result.contains(assoc.getTargetReference()))
				result.add(assoc.getTargetReference());
		return result;
	}

	@Override
	public List<EReference> getSimpleReferencesTo_(EClass eclaz) {
		List<EReference> result = new ArrayList<EReference>();
		boolean direct_yes = true;
		for (EReference ref : getReferencesTo(eclaz, direct_yes))
			// getDirectReferencesTo(eclaz))
			if (!isByInheritance(eclaz, ref) && !result.contains(ref))// remove
																		// isByInheritance
				result.add(ref);
		return result;
	}

	/*
	 * private boolean isTaggedContainement_(EReference ref) { if
	 * (isContainment( ref)) return true; return false; }
	 */
	/*
	 * @Override public List<EReference> getAllReferencesTo(EClass eclaz) {
	 * List<EReference> result = new ArrayList<EReference>(); for (EReference
	 * ref : getAllReferencesTo_(eclaz)) if (!result.contains(ref))
	 * result.add(ref); return result; }
	 */
	@Override
	public List<EReference> getSuperReferencesTo(EClass eclaz) {
		List<EReference> result = new ArrayList<EReference>();
		for (EReference ref : getAllReferencesTo(eclaz))
			if (isByInheritance(eclaz, ref) && !result.contains(ref))
				result.add(ref);
		return result;
	}

	private boolean isByInheritance(EClass eclaz, EReference ref) {// FP140503
		return (ref.getEType() != eclaz);
	}

	@Override
	public boolean isDefaultView(EAnnotation anot) {
		for (Map.Entry<String, String> entry : anot.getDetails())
			if (entry.getKey().startsWith(DiagraphKeywords.VIEW_EQU))
				return false;
		return true;
	}

	@Override
	public boolean isView(EAnnotation anot) { // FP131208
		String viewid = notation.getCurrentView().getViewId();
		if (!anot.getSource().equals(DiagraphKeywords.DIAGRAPH_LITTERAL))
			return false;
		if (viewid == null || viewid.equals(DiagraphKeywords.DIAGRAPH_DEFAULT)
				|| viewid.isEmpty())
			return isDefaultView(anot);
		for (Map.Entry<String, String> entry : anot.getDetails())
			if (entry.getKey().equals(DiagraphKeywords.VIEW_EQU + viewid))
				return true;
		return false;
	}

	@Override
	public EReference parseExtraContainmentReference(EClass eclaz) {
		EAnnotation annot = getDiagraphAnnotation(eclaz);
		for (Map.Entry<String, String> entry : annot.getDetails()) {
			if (entry.getKey().startsWith(DiagraphKeywords.XCONTAINMENT_EQU)) { // FP131009//XCONTAINMENT
				String[] pars = entry.getKey().split("=");
				String contname = pars[1];
				pars = contname.split("\\.");
				return (EReference) ((EClass) eclaz.getEPackage()
						.getEClassifier(pars[0]))
						.getEStructuralFeature(pars[1]);
			}
		}
		return null;
	}

	@Override
	public String parseNavigation(List<IDiagraphAnnotation> annotations) {
		try {
			for (IDiagraphAnnotation dAnnotation : annotations) {
				if (dAnnotation.getKey().startsWith(
						DiagraphKeywords.POINT_OF_VIEW_PREFIX_OBSOLETE)) // FP130105
					throw new RuntimeException(
							"obsolete key: pov:[PointOfViewName], change to nav:[PointOfViewName] in multiple views !!!"); // FP130105x
				if (dAnnotation
						.keyFirstSegmentEquals(DiagraphKeywords.OPEN_DIAGRAM)) { // FP121017
					String k = dAnnotation.getKey();
					if (LOG)
						clog("parse Diagram Navigation: " + k);
					String[] odbs = k.split(":");
					String di = odbs[1];
					if (di.contains("."))
						throw new RuntimeException(
								"should not happen (in parseNavigation)");
					return di + DiagraphKeywords.VIEW_SEPARATOR_1
							+ DiagraphKeywords.ROOT_NAME;
				}
			}
		} catch (Exception e) {
			String err = e.getMessage(); // FP130105x
			if (err == null || err.isEmpty())
				err = " use a dot and not an equal ";
			throw new RuntimeException("error while parsing navigation  " + err);// " use a dot and not an equal ???");
		}
		return null;
	}

	@Override
	public EAnnotation isView(EClass eclaz) {
		for (EAnnotation eachannot : eclaz.getEAnnotations())
			if (isView(eachannot))
				return eachannot;
		if (LOG) {
			String view = notation.getCurrentView().getViewId();
			clog("EClass " + eclaz.getName()
					+ " has no annotation(1) for the view "
					+ ((view == null || view.isEmpty()) ? "default" : view));
		}
		return null;
	}

	@Override
	public boolean isPov(EClass eclaz) { // FP131209
		if (eclaz == null)
			return false;
		if (!isNode(eclaz))
			return false;
		for (EAnnotation eAnnotation : eclaz.getEAnnotations())
			if (isView(eAnnotation))
				for (Map.Entry<String, String> entry : eAnnotation.getDetails())
					if (entry.getKey().equals(DiagraphKeywords.POINT_OF_VIEW))
						return true;
		return false;
	}

	@Override
	public boolean isSubPov(EClass eclaz) {// FP150424
		for (EClass sub : getConcreteSubNodes_(eclaz))
			if (isPov(sub))
				return true;
		return false;
	}

	@Override
	public boolean isNode(EClass eclaz) {
		for (EAnnotation eAnnotation : eclaz.getEAnnotations())
			if (isView(eAnnotation))
				for (Map.Entry<String, String> entry : eAnnotation.getDetails())
					if (entry.getKey().equals(DiagraphKeywords.NODE))
						return true;
		return false;
	}


	private boolean isCanvas(EClass eclaz) {
		for (EAnnotation eAnnotation : eclaz.getEAnnotations())
			if (isView(eAnnotation))
				for (Map.Entry<String, String> entry : eAnnotation.getDetails())
					if (entry.getKey().equals(DiagraphKeywords.POINT_OF_VIEW))
						return true;
		return false;
	}


	public boolean isDeclaredCref(EReference refFromNode) {
		EClass eclaz = (EClass) refFromNode.eContainer();
		if (isNode(eclaz))
		  for (EAnnotation eAnnotation : eclaz.getEAnnotations())
			if (isView(eAnnotation))
				for (Map.Entry<String, String> entry : eAnnotation.getDetails()){
					if (entry.getKey().equals(DiagraphKeywords.CREFERENCE_EQU+refFromNode.getName()))
						return true;
				}
		return false;
	}

	public boolean isImplicitCrefOnCanvas(EReference refFromNode) {
		return isCanvas((EClass) refFromNode.eContainer());
	}



	@Override
	public boolean isCref(EReference refFromNode) {
		return isDeclaredCref(refFromNode) || isImplicitCrefOnCanvas(refFromNode);
	}

	private boolean isNodeOrGeneric(EClass eclaz) {
		return isNode(eclaz) || isGeneric(eclaz);
	}

	private boolean isImplicitNodeContainment_(EReference r) {
		EClass ctnr = (EClass) r.eContainer();
		return (r.isContainment() && (isPov(ctnr) || isSubPov(ctnr)));
	}

	public boolean isNodeContainment(EReference r) {
		EAnnotation eAnnotation = getDiagraphAnnotation((EClass) r.eContainer());
		for (Map.Entry<String, String> entry : eAnnotation.getDetails()) {
			String ekey = entry.getKey();
			if (ekey.startsWith(DiagraphKeywords.KREFERENCE_)
					|| ekey.startsWith(DiagraphKeywords.CREFERENCE_)
					|| ekey.startsWith(DiagraphKeywords.AFFIXED_)) {
				String[] kv = ekey.split("=");
				if (kv.length == 2 && kv[1].equals(r.getName()))
					return true;// FP150407cc
			}
		}
		return false;
	}

	private boolean isImplicitLinkContainment_(EReference r) {
		return (r.isContainment() && isNode((EClass) r.eContainer()));
	}

	private boolean isLinkContainment(EReference r) {
		EAnnotation eAnnotation = getDiagraphAnnotation((EClass) r.getEType());
		for (Map.Entry<String, String> entry : eAnnotation.getDetails()) {
			String ekey = entry.getKey();
			if (ekey.startsWith(DiagraphKeywords.CONTAINMENT_EQU)) {
				String[] kv = ekey.split("=");
				String ownername = null;
				String val = kv[1];
				if (val.contains(".")) {
					String[] q = val.split("\\.");
					val = q[1];
					ownername = q[0];
					return val.equals(r.getName())
							&& ownername.equals(((EClass) r.eContainer())
									.getName());
				}
				return val.equals(r.getName());

			}
		}
		return false;
	}

	@Override
	public boolean isClassAssociation(EClass eclaz) {
		for (EAnnotation eAnnotation : eclaz.getEAnnotations())
			if (isView(eAnnotation))
				for (Map.Entry<String, String> entry : eAnnotation.getDetails())
					if (entry.getKey().equals(DiagraphKeywords.LINK))
						return true;
		return false;
	}

	private EClass isSourceOfClassAssociation(EClass element_) {
		throw new RuntimeException("TODO implement isSourceOfClassAssociation");
	}

	@Override
	public boolean isGeneric(EClass eclaz) {
		boolean isview = false;
		for (EAnnotation eAnnotation : eclaz.getEAnnotations())
			if (isView(eAnnotation)) {
				isview = true;
				for (Map.Entry<String, String> entry : eAnnotation.getDetails())
					if (entry.getKey().equals(DiagraphKeywords.NODE)
							|| entry.getKey().equals(DiagraphKeywords.LINK))
						return false;
			}
		return isview;
	}

	@Override
	public boolean isGeneric(EAnnotation eAnnotation) {
		boolean link = false;
		boolean node = false;
		for (Map.Entry<String, String> entry : eAnnotation.getDetails()) {
			if (entry.getKey().equals("node"))
				node = true;
			if (entry.getKey().equals("link"))
				link = true;
		}
		return (!node && !link);
	}

	private List<EReference> upperLinkContainmentPure(EClass c) {
		List<EReference> result = new ArrayList<EReference>();
		List<EReference> allContainements = getSuperReferencesTo(c);
		for (EReference eReference : allContainements) {
			EClass rt = (EClass) (eReference.getEType());
			if (rt.isSuperTypeOf(c) && rt != c)
				if (isLinkContainment(eReference)
						|| isImplicitLinkContainment_(eReference))
					result.add(eReference);
		}
		return result;
	}

	private List<EReference> directLinkContainment(EClass c) {
		List<EReference> result = new ArrayList<EReference>();
		for (EReference ref : getSimpleReferencesTo_(c)) {
			if (ref.isContainment()
					&& (isLinkContainment(ref) || isImplicitLinkContainment_(ref)))
				result.add(ref);
		}
		return result;
	}

	private List<EReference> upperNodeContainmentPure(EClass c) {
		List<EReference> result = new ArrayList<EReference>();
		List<EReference> allContainements = getSuperReferencesTo(c);
		for (EReference eReference : allContainements) {
			EClass rt = (EClass) (eReference.getEType());
			if (rt.isSuperTypeOf(c) && rt != c)
				if (isNodeContainment(eReference)
						|| isImplicitNodeContainment_(eReference))
					result.add(eReference);
		}
		return result;
	}

	private List<EReference> directNodeContainment(EClass c) {
		List<EReference> result = new ArrayList<EReference>();
		for (EReference ref : getSimpleReferencesTo_(c)) {
			if (LOG) {
				boolean rc = ref.isContainment();
				boolean nc = isNodeContainment(ref);
				boolean ic = isImplicitNodeContainment_(ref);
			}

			if (ref.isContainment()
					&& (isNodeContainment(ref) || isImplicitNodeContainment_(ref)))
				result.add(ref); // FP150407cc
		}
		return result;
	}

	// définit directContainment->c pour toute simple référence r ->c où r est
	// contenant et (r est isContainment ou r est implicitContainment)

	private List<EReference> getAllLinkContainementReferences(EClass c) {
		return union(directLinkContainment(c), upperLinkContainmentPure(c));
	}

	private List<EReference> getAllNodeContainementReferences(EClass c) {
		return union(directNodeContainment(c), upperNodeContainmentPure(c));
	}

	// définit AllContainementReferences->c union des références r
	// directContainment->c et upperContainment->c
	// définit AllContainementReferences->c union des références r appà(toute
	// simple référence r ->c où r est contenant et (r est isContainment ou r
	// est implicitContainment)) et upperContainment->c

	public List<EReference> getContainingReferencesIfNode(EClass c) { // FP150326
		List<EReference> result = new ArrayList<EReference>();
		for (EAnnotation eAnnotation : c.getEAnnotations())
			if (isView(eAnnotation)) {
				for (Map.Entry<String, String> entry : eAnnotation.getDetails())
					if (entry.getKey().equals(DiagraphKeywords.NODE)) {
						List<EReference> conts = getAllNodeContainementReferences(c);
						for (EReference cont : conts)
							if (!result.contains(cont))
								result.add(cont);
					}
			}
		return result;
	}

	// FP150512
	// définition de ContainingReferences : pour toute annotation a appartenant
	// à c où a correspond à la vue v, pour toute entrée e égale à NODE,
	// l'ensemble des références e ContainementReferences ->c
	// définition de ContainingReferences : pour toute annotation a appartenant
	// à c où a correspond à la vue v, pour toute entrée e égale à NODE,
	// l'ensemble des références e (union des références r directContainment->c
	// et upperContainment->c) ->c
	// définition de ContainingReferences : pour toute annotation a appartenant
	// à c où a correspond à la vue v, pour toute entrée e égale à NODE,
	// l'ensemble des références e appà (union des références r appà(toute
	// simple référence r ->c où r est contenant et (r est isContainment ou r
	// est implicitContainment)) et upperContainment->c) ->c

	private List<EReference> getContainingReferencesIfLink(EClass c) {
		List<EReference> result = new ArrayList<EReference>();
		for (EAnnotation eAnnotation : c.getEAnnotations())
			if (isView(eAnnotation)) {
				for (Map.Entry<String, String> entry : eAnnotation.getDetails())
					if (entry.getKey().equals(DiagraphKeywords.LINK)) {
						List<EReference> conts = getAllLinkContainementReferences(c);
						for (EReference cont : conts)
							if (!result.contains(cont))
								result.add(cont);
					}
			}
		return result;
	}

	@Override
	public boolean isNodeInstanciable(EClass c) {
		return (isPov(c) || isSubPov(c) || !getContainingReferencesIfNode(c)
				.isEmpty()); // FP150424
	}

	@Override
	public boolean isLinkInstanciable(EClass c) {
		return (!getContainingReferencesIfLink(c).isEmpty());
	}

	@Override
	public boolean isLink(EClass eclaz) {
		for (EAnnotation eAnnotation : eclaz.getEAnnotations())
			if (isView(eAnnotation))
				for (Map.Entry<String, String> entry : eAnnotation.getDetails())
					if (entry.getKey().equals(DiagraphKeywords.LINK))
						return true;
		return false;
	}

	private List<EClass> getSubTypes(EClass eclass) {
		List<EClass> result = new ArrayList<EClass>();
		EPackage pack = eclass.getEPackage();
		for (Iterator<EObject> contents = pack.eAllContents(); contents
				.hasNext();) {
			EObject el = contents.next();
			if (el instanceof EClass) {
				EClass other = (EClass) el;
				if (eclass.isSuperTypeOf(other) && !result.contains(other)
						&& other != eclass) {
					result.add(other);
				}
			}
		}
		return result;
	}

	private List<IDiagraphReferenceAssociation> patternContainmentAssociationsKrefAutoCBySuperNodes(
			EClass eclaz, boolean abstragt) {
		List<IDiagraphReferenceAssociation> result = new ArrayList<IDiagraphReferenceAssociation>();
		if (!abstragt && eclaz.isAbstract())
			return result;
		for (IDiagraphReferenceAssociation raf : getContainmentAssociationsAndSubHierTo(eclaz)) {
			if (patternKrefAutoCBySuperNodes(raf, eclaz)) {
				if (!result.contains(raf))
					result.add(raf);
				raf.addKrefBySuper(eclaz);
				if (DParams.Parser_15_LOG)
					clog15("pattern KrefAutoCBySuperNodes for " + raf.toId()
							+ " " + eclaz.getName());

			}
		}
		return result;
	}

	@Override
	public List<EReference> patternContainmentAssociationsKrefAutoCont(
			EClass eclaz, boolean abstragt) {
		List<EReference> result = new ArrayList<EReference>();
		List<IDiagraphReferenceAssociation> rs = patternContainmentAssociationsKrefAutoC(
				eclaz, abstragt);
		for (IDiagraphReferenceAssociation r : rs)
			if (!result.contains(r.getTargetReference()))
				result.add(r.getTargetReference());
		return result;
	} // FP150430

	private List<IDiagraphReferenceAssociation> patternContainmentAssociationsKrefAutoC(
			EClass eclaz, boolean abstragt) {
		List<IDiagraphReferenceAssociation> result = new ArrayList<IDiagraphReferenceAssociation>();
		if (!abstragt && eclaz.isAbstract())
			return result;
		for (IDiagraphReferenceAssociation raf : getContainmentAssociationsAndSubHierTo(eclaz)) {
			if (patternKrefAutoC(raf, eclaz)) {
				result.add(raf);
				// raf.addKrefBySuper(eclaz);
				if (DParams.Parser_15_LOG)
					clog15("pattern KrefAutoC for " + raf.toId());
			}
		}
		return result;
	}

	@Override
	public List<EReference> patternKrefContainmentAlt(EClass eclaz) {
		if (LOG)
			clog("getKrefContainmentAlt");
		List<EReference> result = new ArrayList<EReference>();
		for (IDiagraphReferenceAssociation raf : getContainmentAssociationsAndSubHierTo(eclaz))
			if (patternKrefContainmentAlt_(raf, eclaz, result)) {
				if (!result.contains(raf.getTargetReference()))
					result.add(raf.getTargetReference());
				raf.addKrefContainmentAlt(eclaz);
			}
		return result;
	}

	private List<IDiagraphReferenceAssociation> patternContainmentAssociationsKrefAutoCBySubNodes(
			EClass eclaz, boolean abstragt) {
		List<IDiagraphReferenceAssociation> result = new ArrayList<IDiagraphReferenceAssociation>();
		if (!abstragt && eclaz.isAbstract())
			return result;
		for (IDiagraphReferenceAssociation raf : getContainmentAssociationsAndSubHierTo(eclaz)) {

			if (patternKrefAutoCBySubNodes(raf, eclaz)) {
				result.add(raf);
				raf.addKrefBySub(eclaz);
				if (DParams.Parser_15_LOG)
					clog15("pattern KrefAutoCBySubNodes for " + raf.toId());
			}

		}
		return result;
	}

	@Override
	public IDiagraphReferenceAssociation getBaseContainment(EClass eclaz) {
		List<IDiagraphReferenceAssociation> asscs = getContainmentAssociationsAndSubHierTo(eclaz);
		if (LOG) {
			clog("---- BaseContainment for " + eclaz.getName() + " - view: "
					+ notation.getCurrentView().getViewId() + " ----------");
			clog("--------------");
			for (IDiagraphReferenceAssociation assoc : asscs) {
				if (LOG)
					clog(assoc.toString());
			}
			clog("--------------");
		}
		List<IDiagraphReferenceAssociation> r1 = patternContainmentAssociationsKrefAutoCBySubNodes(
				eclaz, false);
		List<IDiagraphReferenceAssociation> r2 = patternContainmentAssociationsKrefAutoCBySuperNodes(
				eclaz, false);
		removeAll(asscs, r1);
		removeAll(asscs, r2);
		if (LOG) {
			clog("--------------");
			for (IDiagraphReferenceAssociation assoc : asscs) {
				if (LOG)
					clog(assoc.toString());
			}
			clog("--------------");
		}
		if (asscs.isEmpty()) {

			cerror(("(10)no containment for " + eclaz.getName()));
			return null;
		}
		if (asscs.size() > 1)
			for (IDiagraphReferenceAssociation assc : asscs)
				if (assc.getSource() != assc.getTarget())
					return assc;
		if (asscs.size() > 1)
			return null;
		return asscs.get(0);
	}

	private void removeAll(List<IDiagraphReferenceAssociation> target,
			List<IDiagraphReferenceAssociation> src) {
		if (src.isEmpty())
			return;
		List<IDiagraphReferenceAssociation> toRemove = new ArrayList<IDiagraphReferenceAssociation>();
		for (IDiagraphReferenceAssociation targ : target)
			for (IDiagraphReferenceAssociation sr : src)
				if (targ.getInstanceSource() == sr.getInstanceSource()
						&& targ.getTargetReference().getEType() == sr
								.getTargetReference().getEType())
					toRemove.add(targ);
		target.removeAll(toRemove);

	}

	private IDiagraphReferenceAssociation getRecurCompartment(EClass eclaz,
			String name) { // FP140329
		boolean iscompartment = isAnnotated(eclaz, name,
				IDiagraphAssociation.AssociationType.TYPED_COMPARTMENT);
		if (iscompartment) {
			for (IDiagraphReferenceAssociation association : getContainmentAssociationsAndSubHierTo(eclaz)) {
				if (association.getName().equals(name)) {
					if (association.getSource() == eclaz)
						return association;
				}
			}
		}
		return null;
	}

	@Override
	public IDiagraphReferenceAssociation getTopLevelAssociation(EClass eclaz) {
		List<IDiagraphReferenceAssociation> rootAssociations = new ArrayList<IDiagraphReferenceAssociation>();
		if (!isNode(eclaz) && !isGeneric(eclaz))
			throw new RuntimeException("getPov must apply on a node");
		List<IDiagraphReferenceAssociation> assocs = getContainmentAssociationsAndSubHierTo(eclaz);
		for (IDiagraphReferenceAssociation assoc : assocs)
			if (isPov(assoc.getSource())) {
				rootAssociations.add(assoc);
				// assoc.setSourcePov();
			}
		if (rootAssociations.size() > 1)
			throw new RuntimeException("more than one container from pov");
		return rootAssociations.isEmpty() ? null : rootAssociations.get(0);
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	@Override
	public IDiagraphNode findNode_(EClass claz) {
		List<IDiagraphNode> nodes = getAllNodes();
		for (IDiagraphNode eachNode : nodes)
			if (eachNode.getEClass() == claz
					&& eachNode.getView().equals(
							notation.getCurrentView().getViewId()))
				return eachNode;
		return null;
	}

	private IDiagraphElement findGeneric(EClass claz) {
		List<IDiagraphElement> gens = getAllGenerics();
		for (IDiagraphElement gen : gens)
			if (gen.getEClass() == claz
					&& gen.getView().equals(
							notation.getCurrentView().getViewId()))
				return gen;
		return null;
	}

	private IDiagraphElement findNodeOrGeneric(EClass claz) {
		IDiagraphElement result = findNode_(claz);
		if (result == null)
			result = findGeneric(claz);
		return result;
	}

	@Override
	public String getCurrentView() {
		return notation.getCurrentView().getViewId();
	}

	@Override
	public IDiagraphAssociation findClassAssociation(EClass c) {
		List<IDiagraphAssociation> assocs = getAllAssociations();
		for (IDiagraphAssociation assoc : assocs)
			if (assoc instanceof IDiagraphClassAssociation) {
				IDiagraphClassAssociation classoc = (IDiagraphClassAssociation) assoc;
				if (classoc.getLink() == c
						&& assoc.getView().equals(
								notation.getCurrentView().getViewId()))
					return assoc;
			}
		return null;
	}

	private IDiagraphReferenceAssociation findReferenceAssociation(
			EClass element) {
		assert element instanceof EClass;
		for (IDiagraphAssociation el : getAllAssociations()) {
			if (el instanceof IDiagraphReferenceAssociation
					&& el.getSource() == element
					&& el.getView().equals(
							notation.getCurrentView().getViewId()))
				return (IDiagraphReferenceAssociation) el;
		}
		return null;
	}

	private void inferNodeContainment(IDiagraphNode node, int depth) { // FP140502

		if (DParams.Parser_CONTAINMENT2_LOG)
			clog2("inferNodeContainment " + node.getName() + " depth= " + depth
					+ " [");
		depth++;
		if (depth > DParams.MAX_DEPTH_ * 2)
			throw new RuntimeException("loop error in parser !"); // FP140505
		List<EReference> containements = getDirectContainementReferences(node
				.getEClass());
		List<EReference> allContainements = parseAllContainementReferences(node
				.getEClass());

		if (containements.size() == 0 && allContainements.size() == 0) { // FP140423xxx
			cerror("(8)no containment for node" + node.getName() + " in view "
					+ node.getView());
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
		boolean isTopNode = isTopNode(node);
		boolean isContainedThroughInheritance = patternContainedThroughInheritance(node
				.getEClass());
		boolean isSelfContained = isSelfContained(node.getEClass());

		if (hasContainements && isTopNode && isContainedThroughInheritance
				|| isSelfContained)
			if (inferContainmentAlt(node, containements))
				return;
		if (containements.size() == 0)
			containements = allContainements;
		if (containements.size() == 1)
			inferNodeContainmentSimple(node, containements, depth);
		else
			inferNodeContainmentMultiple(node, containements);

		if (DParams.Parser_CONTAINMENT2_LOG)
			clog2(" ] inferNodeContainment " + node.getName() + " depth= "
					+ depth);

	}

	private EReference getDeclaredContainment(IDiagraphNode node) {
		// FP131009x
		EReference containment = null;
		try {
			containment = parseContainmentReferenceLate(node.getEClass()); // FP131008
		} catch (Exception e) {
			throw new RuntimeException("error in getDeclaredContainment ");
		}
		if (containment != null) { // FP131008
			// element.setContainmentReference(containment);
			if (LOG)
				clog("declared containment found " + containment + " for view "
						+ node.getView());

		}
		return containment; // FP131008
	}

	private EReference parseContainmentReferenceLate(EClass eClass) {
		EAnnotation annot = getDiagraphAnnotation(eClass);
		for (Map.Entry<String, String> entry : annot.getDetails()) {
			if (entry.getKey().startsWith(DiagraphKeywords.XCONTAINMENT_EQU)) {
				String[] pars = entry.getKey().split("=");
				String contname = pars[1];
				pars = contname.split("\\.");
				return (EReference) ((EClass) eClass.getEPackage()
						.getEClassifier(pars[0]))
						.getEStructuralFeature(pars[1]);
			}
		}
		return null;
	}

	private boolean isTopNode(IDiagraphNode node) {
		for (EReference eReference : getDirectContainementReferences(node
				.getEClass())) {
			IDiagraphNode aNode = findNode_(((EClass) eReference.eContainer()));
			if (aNode != null && aNode.isCanvas()) {
				if (LOG)
					clog("contained (1) by canvas " + aNode.getName());
				return true;
			}
		}
		IDiagraphNode canvas = node.getCanvasContainment();
		if (canvas != null) {
			if (LOG)
				clog("contained (2) by canvas " + canvas.getName());
			return true;
		}
		return false;
	}

	private void inferNodeContainmentMultiple(IDiagraphNode node,
			List<EReference> containements) {
		EReference declaredcontRef = getDeclaredContainment(node);
		if (declaredcontRef != null) { // FP131008
			node.setDeclaredContainment(declaredcontRef);
			if (LOG)
				clog("late containment found " + declaredcontRef + " for view "
						+ node.getView()); // TODO validate this
		} else {
			IDiagraphReferenceAssociation containment = getBaseContainment(node
					.getEClass());
			if (containment != null)
				node.setContainment(containment.getTargetReference());
			else
				containerError(node, containements);
		}
	}

	private void containerError(IDiagraphNode node,
			List<EReference> containements) {
		String conts = "";
		for (EReference containr : containements)
			conts += ((EClass) containr.eContainer()).getName() + "."
					+ containr.getName() + " ; "; // FP130929
		throw new RuntimeException("containment error for: " + node.getName()
				+ " : " + conts + " - add a discriminant <cont> annotation to "
				+ node.getName() + " for each view");
	}

	private void inferNodeContainmentSimple(IDiagraphNode node,
			List<EReference> containements, int depth) {
		node.setContainment(containements.get(0));
		EReference declaredcontRef = getDeclaredContainment(node.getEClass(),
				depth);
		EReference contRef = node.getContainment();
		if (declaredcontRef != null && declaredcontRef != contRef) { // FP131009x
			if (LOG)
				clog("containing reference is now " + declaredcontRef.getName()
						+ " vs " + contRef.getName());
			contRef = declaredcontRef;
			node.setContainment(contRef);
		}
	}

	private boolean inferContainmentAlt(IDiagraphNode node,
			List<EReference> containements) { // FP140218
		try {
			EReference eref = null;
			List<EReference> stp = multipleContentionThroughInheritancePattern(node
					.getEClass());
			if (stp == null)
				return false;
			for (EReference eReference : stp)
				if (eReference != containements.get(0))
					eref = eReference;
			if (eref == null)
				return false;
			stp.remove(containements.get(0));
			EReference r0 = containements.get(0);
			EReference r1 = eref;
			EClass cl0 = r0.getEContainingClass();
			EClass cl1 = r1.getEContainingClass();
			EClass cl3 = node.getEClass();
			IDiagraphNode d0 = findNode_(cl0);
			IDiagraphNode d1 = findNode_(cl1);
			IDiagraphNode d3 = findNode_(cl3);
			if (d0.isCanvas())
				inferNodeContainments(node, r0, r1);
			else if (d1.isCanvas())
				inferNodeContainments(node, r1, r0);
			else if (cl0.isSuperTypeOf(cl3))
				inferNodeContainments(node, r1, r0); // FP150517
			else if (cl1.isSuperTypeOf(cl3))
				inferNodeContainments(node, r0, r1); // FP150517
			else {
				cerror("(3)should not happen in inferContainmentAlt");
				return false;
			}
		} catch (Exception e) {
			cerror("(3)err while inferContainmentAlt");
			return false;
		}
		return true;
	}

	private void inferNodeContainments(IDiagraphNode node,
			EReference containement, EReference containementAlt) {
		node.setContainment(containement);
		node.setContainmentAlt(containementAlt);
	}

	private IDiagraphClassAssociation createDiagraphClassAssociation_(
			IDiagraphNode contNode, IDiagraphElement srcNode,
			IDiagraphElement trgNode, EClass link, EClass refinstancesource,
			EReference targetref) {
		boolean derived = false;
		String info = "";
		IDiagraphClassAssociation diagraphClassAssociation = new DiagraphClassAssociation(
				this, notation.getCurrentView().getViewId(), contNode, srcNode,
				trgNode, link, refinstancesource, targetref, derived, info);
		if (getCurrentPointOfView() != null)
			getCurrentPointOfView().addClassAssociation(
					diagraphClassAssociation);
		return diagraphClassAssociation;
	}

	private IDiagraphAssociation createDiagraphClassAssociation_(EClass eclaz) {
		IDiagraphAnnotation dian = parseDiagraphAnnotation(eclaz,
				DiagraphKeywords.LINK);
		IDiagraphClassAssociation classoc = parseLink(eclaz, dian);
		setDiagraphClassAssociationProperties(classoc);
		IDiagraphView curpov = getCurrentPointOfView();
		if (curpov != null) { // && !curpov.getAssocs().contains(classoc))
			curpov.addClassAssociation(classoc);// FP150520
			if (DParams.Parser_15_LOG)
				clog15("diagraphClassAssociation created");
		} else if (LOG)
			clog("no current pov");
		return classoc;
	}

	private IDiagraphNode createDiagraphNode(EClass eclaz, boolean derived,
			int depth) { // FP140218
		if (DParams.Parser_15_LOG)
			clog15("createDiagraphNode " + eclaz.getName()
					+ (derived ? " derived" : " not derived") + " depth="
					+ depth);
		DiagraphNode diagraphNode = new DiagraphNode(this, notation
				.getCurrentView().getViewId(), eclaz, derived);
		setDiagraphNodeProperties(diagraphNode);
		IDiagraphView curpov = getCurrentPointOfView();
		if (curpov != null) {// && !curpov.getNodes().contains(diagraphNode))
			curpov.addNode(diagraphNode);
			if (DParams.Parser_15_LOG)
				clog15("diagraph node created");
		} else if (DParams.Parser_15_LOG)
			clog15("no current pov");
		return diagraphNode;
	}

	private IDiagraphElement createDiagraphGeneric(EClass eclaz,
			boolean derived, int depth) { // FP140218
		if (DParams.Parser_15_LOG)
			clog15("createDiagraphGeneric " + eclaz.getName()
					+ (derived ? " derived" : " not derived") + " depth="
					+ depth);
		DiagraphElement diagraphElement = new DiagraphElement(this, notation
				.getCurrentView().getViewId(), eclaz, derived);
		setDiagraphGenericProperties(diagraphElement);
		IDiagraphView curpov = getCurrentPointOfView();
		if (curpov != null) {// && !curpov.getNodes().contains(diagraphElement))
			curpov.addGeneric(diagraphElement);
			if (DParams.Parser_15_LOG)
				clog15("diagraph generic created");
		} else if (DParams.Parser_15_LOG)
			clog15("no current pov");
		return diagraphElement;
	}

	private void setDiagraphGenericProperties(DiagraphElement diagraphElement) {
		throw new RuntimeException(
				"TODO implement setDiagraphGenericProperties");

	}

	@Override
	public IDiagraphReferenceAssociation createReferenceAssociation(
			EReference ref, boolean simple) {
		return parseReferenceAssociation_((EClass) ref.eContainer(),
				(EClass) ref.eContainer(), (EClass) ref.getEType(), ref, false,
				simple);
	}

	private void handlePatterns(IDiagraphNode diagraphNode) {
		EClass eclaz = diagraphNode.getEClass();
		String viewid = diagraphNode.getView();

		String p1log = diagraphNode.handleSelfCompartmentContainementPattern();

		if (!p1log.isEmpty())
			cerror("handlePattern SelfCompartmentContainement = \n" + p1log);

		for (EReference eReference : diagraphNode.getAllContainmentReferences()) {// FP1404qqqq

			IDiagraphReferenceAssociation compart = getRecursiveCompartment(
					(EClass) eclaz, eReference.getName());
			boolean recurcompart = compart != null;
			clog("handlePatterns " + eReference.getName()
					+ (recurcompart ? " recurcompart" : ""));

			IDiagraphReferenceAssociation compart2 = diagraphNode
					.getRecursiveCompartment(eReference);

			boolean isrecurs = compart2 != null;// //FP1404qqqq
		}

	}

	public IDiagraphNode createDiagraphNodeDebug(EClass eclaz) { // FP140218

		return null;
	}

	@Override
	public IDiagraphNode getPointOfView(EPackage pack) {
		EClass povclass = getPointOfViewClass(pack);// FP140417
		if (povclass != null) { // FP140615b
			IDiagraphNode povNode = getDiagraphNode(povclass, 0);
			povNode.setPov();
			return povNode;
		}
		return null;
	}

	@Override
	public void startPointOfView() {
		logBuffer = "";

		// getCurrentPointOfView().clear();
	}

	@Override
	public String getReports() {
		String resp = "";

		resp += "______view " + getCurrentView() + " ______\n";
		resp += "______nodes______\n";

		for (IDiagraphNode node : getAllNodes()) {
			resp += "\n___________________________________\n" + node.toLog();
		}

		resp += "\n___________________________________\n";
		resp += "______ref assocs______\n";

		for (IDiagraphAssociation association : getAllAssociations()) {
			if (association instanceof IDiagraphReferenceAssociation) {
				IDiagraphReferenceAssociation raf = (IDiagraphReferenceAssociation) association;
				resp += "\n___________________________________\n"
						+ raf.toLogCompl();
			}
		}

		resp += "\n___________________________________\n";
		resp += "______class assocs______\n";

		for (IDiagraphAssociation association : getAllAssociations()) {
			if (association instanceof IDiagraphClassAssociation) {
				IDiagraphClassAssociation cas = (IDiagraphClassAssociation) association;
				resp += cas.toLogCompl() + '\n';
			}
		}
		return resp;
	}

	@Override
	public EClass getPointOfViewClass(EPackage ePackage) {
		for (EClassifier eClassifier : ePackage.getEClassifiers())
			if (eClassifier instanceof EClass)
				if (isPov((EClass) eClassifier))
					return (EClass) eClassifier;
		return null;
	}

	@Override
	public EAnnotation getInViewDiagraphAnnotation(EClass eclaz) {
		for (EAnnotation eAnnotation : eclaz.getEAnnotations())
			if (eAnnotation.getSource().equals(
					DiagraphKeywords.DIAGRAPH_LITTERAL)
					&& parseView(eAnnotation).equals(
							notation.getCurrentView().getViewId()))
				return eAnnotation;

		return null;
	}

	private String parseView(EAnnotation eAnnotation) {
		for (Map.Entry<String, String> entry : eAnnotation.getDetails())
			if (entry.getKey().startsWith(DiagraphKeywords.VIEW_EQU))
				return entry.getKey().split("=")[1];
		return DiagraphKeywords.DIAGRAPH_DEFAULT;
	}

	@Override
	public List<IDiagraphAnnotation> parseElementAnnotations(EClass element,
			IProgressMonitor progressMonitor) {
		String elementName = element.getName();
		if (LOG) {
			clog("(1)parseElementAnnotations for view "
					+ notation.getCurrentView().getViewId() + " and element "
					+ elementName);
		}
		List<IDiagraphAnnotation> allAnnotations = new ArrayList<IDiagraphAnnotation>();
		for (EAnnotation annotation : (List<? extends EAnnotation>) element
				.getEAnnotations()) {
			if (annotation.getSource().equals(
					DiagraphKeywords.DIAGRAPH_LITTERAL)) {
				String id = parseView(annotation);
				if (id.equals(notation.getCurrentView().getViewId())) {
					List<IDiagraphAnnotation> anns = new ArrayList<IDiagraphAnnotation>();
					try {
						anns = createAnnotations(annotation);
						allAnnotations.addAll(anns);
					} catch (Exception e) {
						throw new RuntimeException("bad annotation syntax "
								+ annotation.toString() + " for element :"
								+ elementName + " !!!!");
					}
					checkValidToken(anns, elementName);
					if (isGenericElement(anns))
						checkGenericElement(elementName, element);
					for (IDiagraphAnnotation anot : anns) {
						if (LOG) {
							clog("parse annotation " + anot.getKey());
						}
						parse_(element, anot, anns);
					}

				}
			}
		}
		return allAnnotations;
	}

	// DiagraphKeywords.LINK
	// //parseLink(element_, anot, anns);
	private IDiagraphAnnotation parseDiagraphAnnotation(EClass eclaz, String key) {

		String elementName = eclaz.getName();

		if (LOG)
			clog("parseLink for view " + notation.getCurrentView().getViewId()
					+ " and element " + elementName);
		IDiagraphAnnotation result = null;

		List<IDiagraphAnnotation> allAnnotations = new ArrayList<IDiagraphAnnotation>();
		for (EAnnotation annotation : (List<? extends EAnnotation>) eclaz
				.getEAnnotations()) {
			if (result == null
					&& annotation.getSource().equals(
							DiagraphKeywords.DIAGRAPH_LITTERAL)) {
				String id = parseView(annotation);
				if (id.equals(notation.getCurrentView().getViewId())) {
					List<IDiagraphAnnotation> anns = new ArrayList<IDiagraphAnnotation>();
					try {
						anns = createAnnotations(annotation);
						allAnnotations.addAll(anns);
					} catch (Exception e) {
						throw new RuntimeException("bad annotation syntax "
								+ annotation.toString() + " for element :"
								+ elementName + " !!!!");
					}
					checkValidToken(anns, elementName);
					if (isGenericElement(anns))
						checkGenericElement(elementName, eclaz);
					for (IDiagraphAnnotation anot : anns) {
						if (LOG)
							clog("parse annotation " + anot.getKey());

						if (key.equals(anot.getKey())) {// FP150506voir
							anot.setOther(anns);
							result = anot;
							break;
							// return anot;// parseLink(element_, anot, anns);
						}
						// parserParse1(anns, anot, element);
						// parserParse2(anns, anot, element);
					}

				}
			}
		}
		return result;

	}

	private void checkGenericElement(String elementName, EClass element) {
		if (DParams.TODO_LOG_)
			cerror("TODO implement checkGenericElement");

	}

	private boolean isGenericElement(List<IDiagraphAnnotation> anns) {

		for (IDiagraphAnnotation anot : anns)
			if (anot.keyFirstSegmentEquals(DiagraphKeywords.NODE)
					|| anot.keyFirstSegmentEquals(DiagraphKeywords.LINK)
					|| anot.keyFirstSegmentEquals(DiagraphKeywords.OPEN_DIAGRAM))
				return false;
		return true;
	}

	private void checkValidToken(List<IDiagraphAnnotation> anns,
			String elementName) {
		for (IDiagraphAnnotation anot : anns)
			if (!isCurrentValidToken(anot.getKey()))
				throw new RuntimeException(
						"parseElementAnnotations - token is not valid: "
								+ anot.getKey() + "-" + " for element :"
								+ elementName + " !!!!");

	}

	private boolean isCurrentValidToken(String tok) {
		String t = tok;
		String[] composedToken = tok.split(":");
		if (composedToken.length == 2)
			t = composedToken[0];
		for (String key : DiagraphKeywords.VALID_TOKENS) {
			if (key.equals(t))
				return true;
			else if (key.endsWith("*")
					&& t.startsWith(key.substring(0, key.length() - 1))) // FP120716
				return true;

		}
		return false;
	}

	private List<IDiagraphAnnotation> createAnnotations(EAnnotation annotation) {
		List<IDiagraphAnnotation> annotations = new ArrayList<IDiagraphAnnotation>();
		if (annotation == null)
			return annotations; // empty //FP120111
		if (!(annotation.eContainer() instanceof EClass))
			throw new RuntimeException(
					"annotation container should be an EClass !!!!");
		if ((DiagraphKeywords.DIAGRAPH_LITTERAL).equals(annotation.getSource())
				&& parseView(annotation).equals(
						notation.getCurrentView().getViewId())) {
			if (LOG)
				clog("[" + ((EClass) annotation.eContainer()).getName() + "]");
			for (Map.Entry<String, String> entry : annotation.getDetails()) {
				IDiagraphAnnotation an = createAnnotation(entry, annotation);
				if (an != null)
					annotations.add(an);
			}
		}
		return annotations;
	}

	private IDiagraphAnnotation createSimpleAnnotation(
			Map.Entry<String, String> entry, EAnnotation annot) {
		;
		String viewname = DiagraphKeywords.DIAGRAPH_DEFAULT;
		for (Map.Entry<String, String> entr : annot.getDetails())
			if (entr.getKey().startsWith(DiagraphKeywords.VIEW_EQU))
				viewname = entr.getKey().split("=")[1];
		String argument2 = "";
		String argument1 = null;
		IDiagraphAnnotation a = new DiagraphAnnotation(notation, annot,
				entry.getKey(), entry.getValue(), argument2, argument1,
				viewname);
		if (false)
			clog("*a{" + a.toString() + "}a* -"
					+ ((EClass) annot.getEModelElement()).getName());
		return a;
	}

	public IDiagraphAnnotation createAnnotation(
			Map.Entry<String, String> entry, EAnnotation toAdd) {
		if (entry.getKey().contains("="))
			return createEgalAnnotation(entry, toAdd);
		else
			return createSimpleAnnotation(entry, toAdd);
	}

	private IDiagraphAnnotation createEgalAnnotation(
			Map.Entry<String, String> entry, EAnnotation annot) {
		;
		String arg1 = null;
		String arg2 = null;
		String[] args = entry.getKey().split("=");
		if (args.length > 2)
			throw new RuntimeException("parseAnnotation: only 2 arguments for "
					+ annot.toString());
		for (int i = 0; i < args.length; i++)
			args[i] = args[i].trim();

		if (!args[0].equals("labels")) {
			if (args[1].contains(":")) {
				String[] sargs = args[1].split(":");
				if (sargs.length > 2)
					throw new RuntimeException(
							"parseAnnotation: only 2 sub arguments for "
									+ args[1]);
				for (int i = 0; i < sargs.length; i++)
					sargs[i] = sargs[i].trim();
				args[1] = sargs[0];
				arg1 = sargs[1];
				// throw new RuntimeException("TODO DAnnotation createEgal");
			}
		}

		if (args[0].equals(DiagraphKeywords.KREFERENCE_)) { // FP150513transp
			if (args[1].contains(",")) {
				String[] sargs = args[1].split(",");
				if (sargs.length > 2)
					throw new RuntimeException(
							"parseAnnotation: only 2 sub arguments for "
									+ args[1]);
				for (int i = 0; i < sargs.length; i++)
					sargs[i] = sargs[i].trim();
				args[1] = sargs[0];
				arg2 = sargs[1];

			}
		}

		String viewname = DiagraphKeywords.DIAGRAPH_DEFAULT;
		for (Map.Entry<String, String> entr : annot.getDetails())
			if (entr.getKey().startsWith(DiagraphKeywords.VIEW_EQU))
				viewname = entr.getKey().split("=")[1];

		IDiagraphAnnotation a = new DiagraphAnnotation(notation, annot,
				args[0], args[1], arg2, arg1, viewname);
		if (LOG)
			clog("-{" + a.toString() + "}-");
		return a;
	}

	private void derivateAbstractContainements_todo() {// FP140124
		if (DParams.TODO_LOG_)
			cerror("TODO implement derivateAbstractContainements");
		if (false) {

			if (!DParams.PARSER_PROPAGATE_INHERITED_CONTAINMENTS_)
				return;
			if (DParams.Parser_CONTAINMENT2_LOG)
				clog2("--- derivateAbstractContainements ---");

			List<IDiagraphReferenceAssociation> toremove_ = new ArrayList<IDiagraphReferenceAssociation>();
			List<IDiagraphNode> allnodes = getAllNodes();

			if (LOG) {

				clog("aa{");
				for (IDiagraphNode anode : allnodes)
					clog(anode.toString());
				clog("}aa");
			}

			for (IDiagraphNode anode : allnodes) {// formaliser en ocl ??
				if (DParams.Parser_CONTAINMENT2_LOG)
					clog2("derived for " + anode.getName() + " ?");
				List<IDiagraphNode> subnodes = anode.getSubNodes();

				if (DParams.Parser_CONTAINMENT2_LOG) {
					if (!subnodes.isEmpty()) {
						String subs = "";
						for (IDiagraphNode subnode : subnodes)
							subs += subnode.getName() + " ";
						clog2("has " + subnodes.size() + " subnodes: " + subs);
					} else
						clog2("has no  subnodes ");
				}
				if (anode.isAbstract()) {
					if (DParams.Parser_CONTAINMENT2_LOG)
						clog2("node.isAbstract " + anode.getName());
					for (IDiagraphNode subnode : subnodes) {

						if (subnode.isAbstract())
						// /if (subnode.isConcrete()) //FP140423 waserror

						{// FP140418 waserror
							if (DParams.Parser_CONTAINMENT2_LOG)
								clog2("\ncreateDerivedContainments(1) for abstract "
										+ subnode.getName() + " ?");
							if (LOG)
								clog2("DACTN: AC " + subnode.toString());

							createDerivedContainments1(subnodes, subnode);
						} else {
							if (DParams.Parser_CONTAINMENT2_LOG)
								clog2("subnode " + subnode.getName()
										+ " is not abstract");
						}

					}
				}

				/*
				 * if (anode.isAbstract() && anode.get_Container() != null) { if
				 * (LOG) clog("DACTN: AC " + logNode(anode));
				 * handleremoves(anode, toremove);
				 * createDerivedContainments_new(subnodes,anode); }
				 */

				// FP140408x100

				else {
					if (DParams.Parser_CONTAINMENT2_LOG)
						clog2("no derived for " + anode.getName() + " !");
					if (LOG)
						clog2("CDCTN: NAC " + anode.toString());
				}

			}

			if (LOG) {
				clog("b{");
				for (IDiagraphNode anode : allnodes)
					clog(anode.toString());
				clog("}b");
			}

			for (IDiagraphReferenceAssociation remove : toremove_) {
				remove.getSourceNode().getContainments().remove(remove);
				if (getCurrentPointOfView() != null)
					getCurrentPointOfView().getAssocs().remove(remove);
			}
		}
	}

	private void createDerivedContainments1(List<IDiagraphNode> subnodes,
			IDiagraphNode anode) {
		;
		IDiagraphNode contner_ = anode.getContainer();
		if (contner_ == null)
			clog("contner is null ");
		else {
			IDiagraphNode contnersrc = contner_.getSourceNode();
			if (contnersrc.isAbstract()) {
				cerror("TODO derivateAbstractContainements when both abstract: "
						+ contnersrc.getName() + " -> " + anode.getName());
				return;
			}
			if (LOG)
				clog("before: " + anode.toString());

			anode.addDerivedSubNodes(subnodes);

			IDiagraphAssociation.AssociationType containmentType = IDiagraphAssociation.AssociationType.VOID_;
			if (!contner_.isCompartment() && !contner_.isPort())
				containmentType = IDiagraphAssociation.AssociationType.SHARED_COMPARTMENT_;// FP150512//CONTAINMENT;//
																							// CREF;
			else if (contner_.isCompartment())
				if (!contner_.getSourceNode().isOrSubNodeCanvas()) // FP140201
					containmentType = IDiagraphAssociation.AssociationType.TYPED_COMPARTMENT;// Ex.KREF;
				else
					containmentType = IDiagraphAssociation.AssociationType.SHARED_COMPARTMENT_;// FP150512//
																								// CREF;Ex.CREF;
			else if (contner_.isPort())
				containmentType = IDiagraphAssociation.AssociationType.AT_PORT;// Ex.AFX;

			createDerivedContainments2___nu(subnodes, containmentType,
					contnersrc, anode);
			if (LOG)
				clog("after: " + anode.toString());
		}

	}

	@Override
	public String ctToString(
			IDiagraphAssociation.AssociationType containmentType) {
		String result = "nil";
		switch (containmentType) {
		case SIMPLE:
			return DiagraphKeywords.REFERENCE;
		case CLASSOC_:
			return DiagraphKeywords.LNK;// FP150512
		case TYPED_COMPARTMENT:
			return DiagraphKeywords.KREFERENCE_;
		case SHARED_COMPARTMENT_:
			return DiagraphKeywords.CREFERENCE_;
		case AT_CONTAINMENT:
			return DiagraphKeywords.CONTAINMENT;
		case AT_PORT:
			return DiagraphKeywords.AFFIXED_;
		case EXTERNAL:
			return DiagraphKeywords.OPEN_DIAGRAM;
		case VOID_:
			return "void";
		}
		return result;
	}

	private void createDerivedContainments2___nu(List<IDiagraphNode> subnodes,
			IDiagraphAssociation.AssociationType containmentType,
			IDiagraphNode contnersrc, IDiagraphNode anode) {
		// TODO Auto-generated method stub

	}

	private List<IDiagraphReferenceAssociation> createDerivedContainments2_a(
			List<IDiagraphNode> subnodes_,
			IDiagraphAssociation.AssociationType containmentType,
			IDiagraphNode src, IDiagraphNode trgAbtract) {
		boolean check;

		if (DParams.Parser_CONTAINMENT2_LOG)
			clog2("createDerivedContainments(2): " + "(" + subnodes_.size()
					+ ") " + ctToString(containmentType) + " " + src.getName()
					+ "-c->" + trgAbtract.getName() + "(a)");

		List<IDiagraphAssociation> assocs = getCurrentPointOfView().getAssocs();

		List<IDiagraphNode> nodes = getCurrentPointOfView().getNodes();

		if (DParams.Parser_CONTAINMENT2_LOG) {
			clog2("-----associations");
			try {

				// clog2("createDerivedContainments");

				for (IDiagraphAssociation assoc : assocs) {
					clog2(assoc.getSourceNode().getName() + ""
							+ assoc.getTargetNode().getName());
				}
				clog2("-----nodes");
				for (IDiagraphNode node : nodes) {
					clog2(node.getName());
				}
				clog2("-----end");
			} catch (Exception e) {
				clog2("-----error");
			}
		}

		// Cccc-c->RootExpression(a)
		if (src.getName().equals("Cccc")
				&& trgAbtract.getName().equals("RootExpression"))
			check = true; // FP140410
		// impossible casebd31

		List<IDiagraphReferenceAssociation> result = new ArrayList<IDiagraphReferenceAssociation>();
		// EReference tgRef = trgAbtract.getContainerReference();
		for (IDiagraphNode trgConcrete : subnodes_) {
			// result.add(createContainement_(src, trgConcrete, tgRef));
			EReference contref = trgAbtract.getContainerReference();
			IDiagraphReferenceAssociation dc = null;
			if (contref == null) {
				EClass sr = trgAbtract.getEClass();
				List<EReference> refs = sr.getEAllReferences();
				for (EReference eReference : refs) {
					EClass rc = (EClass) eReference.eContainer();
					if (rc == src.getEClass()
							|| rc.isSuperTypeOf(src.getEClass()))
						contref = eReference;
					// if (rc==src.getSemanticRole())
					// contref=eReference;
				}
			}
			boolean derived = true;
			if (contref != null)
				dc = createContainement(containmentType, src, trgConcrete,
						contref, derived);
			else
				check = true; // FP140109bbb
			if (dc != null)
				result.add(dc);
			else
				check = true;
		}
		return result;
	}

	private IDiagraphReferenceAssociation createContainement(
			IDiagraphAssociation.AssociationType containmentType,
			IDiagraphNode src, IDiagraphNode trgConcrete, EReference contref,
			boolean derived) {
		// TODO Auto-generated method stub
		return null;
	}

	private void logSubNodes(IDiagraphNode anode, List<IDiagraphNode> subnodes) {
		if (!subnodes.isEmpty()) {
			String subs = "";
			for (IDiagraphNode subnode : subnodes)
				subs += subnode.getName() + " ";
			clog((anode.isAbstract() ? "abstract " : "concrete ")
					+ anode.getName() + " has " + subnodes.size()
					+ " subnodes: " + subs);
		} else
			clog((anode.isAbstract() ? "abstract " : "concrete ")
					+ anode.getName() + " has  no  subnodes ");
		for (IDiagraphNode subnode : subnodes)
			clog((anode.isAbstract() ? "abstract " : "concrete ") + "node: "
					+ anode.getName() + " <-- "
					+ (subnode.isAbstract() ? "abstract " : "concrete ")
					+ "subnode: " + subnode.getName());
	}

	@Override
	public void logNodesAndSubnodes() { // FP140124
		if (!DParams.Parser_CONTAINMENT2_LOG)
			return;
		clog("----SubNodes");
		for (IDiagraphNode anode : getAllNodes())
			logSubNodes(anode, anode.getSubNodes());
		/*
		 * clog("----SubNodes2"); for (DiaNode anode : parser.getAllNodes())
		 * logSubNodes(anode, anode.getSubNodes2());
		 */
	}

	@Override
	public IDiagraphReferenceAssociation parseReferenceAssociation_(
			EClass refsource, EClass refinstancesource, EClass target,
			EReference ref, boolean derived, boolean container) {
		List<EClass> targetSubHierarchy = getSubHierarchy(
				(EClass) ref.getEType(), false);
		String info = (!LOG ? null : "sub"
				+ (target.isAbstract() ? " abs" : " concr") + " -> "
				+ nodeToString((EClass) ref.getEType()));
		List<IDiagraphAssociation.AssociationType> cts = _containmentTypes(
				refsource, ref);
		if (cts.size() > 1)
			throw new RuntimeException(
					"should not happen in createReferenceAssociation_");
		IDiagraphAssociation.AssociationType ct_ = cts.isEmpty() ? null : cts
				.get(0);
		String ss = refsource.getName();
		String sss = getCurrentPointOfView().getPovName();
		boolean isCurrentCanvas = refsource.getName().equals(
				getCurrentPointOfView().getPovName());
		if (ct_ == null && isCurrentCanvas)
			ct_ = IDiagraphAssociation.AssociationType.SHARED_COMPARTMENT_; // FP150322voir
		DiagraphReferenceAssociation result = new DiagraphReferenceAssociation(
				this, notation.getCurrentView().getViewId(), refsource,
				refinstancesource, target, targetSubHierarchy, ref, ct_,
				derived, container, info);
		if (getCurrentPointOfView() != null)
			getCurrentPointOfView().addAssociation(result);
		else if (LOG)
			clog("no current pov");
		return result;
	}

	/*-----------------------------------------*/

	@Override
	public boolean isDiagraphAnnotated(EClass eclass) {
		// if (viewid == null || viewid.isEmpty())
		// viewid = "default";
		for (EAnnotation e : eclass.getEAnnotations())
			if (e.getSource().equals(DiagraphKeywords.DIAGRAPH_LITTERAL)
					&& getViewName(e).equals(
							notation.getCurrentView().getViewId()))
				return true;
		return false;
	}

	@Override
	public String getViewName(EAnnotation annotation) {
		String viewid = "";
		viewid = DiagraphKeywords.DIAGRAPH_DEFAULT;
		for (Map.Entry<String, String> srcdets : annotation.getDetails()) {
			if (srcdets.getKey().startsWith(DiagraphKeywords.VIEW_EQU)) {
				String[] p = srcdets.getKey().split("=");
				viewid = p[1];
			}
		}
		return viewid;
	}

	@Override
	public String getPointOfViewCandidate(EClass eclaz_) {
		for (EClass cls : getSyntaxElements().keySet()) {
			// All classes that have node associated annotation
			List<String> annots = getSyntaxElements().get(cls);
			if (annots.contains(DiagraphKeywords.NODE)) {
				// Excluding the non containing classes
				List<EReference> containmentRefs = cls.getEAllContainments();
				if (containmentRefs.size() > 0) {
					// Excluding the pov class
					if (!annots.contains(DiagraphKeywords.POINT_OF_VIEW)) {
						if (eclaz_ == cls) {
							if (LOG)
								clog("GPOVC " + DParams.VIEW_PREFIX
										+ cls.getName().toLowerCase());
							return DParams.VIEW_PREFIX
									+ cls.getName().toLowerCase();
						}
					}
				}
			}
		}
		return "unknown";
	}

	private EClass getClass(EPackage packag, String name) {
		return getClasses(packag).get(name);
	}

	@Override
	public List<String> getPointOfViewCandidates() {
		Map eClazs = new HashMap<String, EClass>();// FP130511
		for (EClass cls : getSyntaxElements().keySet()) {
			List<String> annots = getSyntaxElements().get(cls);
			if (annots.contains(DiagraphKeywords.NODE)) {
				if (cls.getEAllContainments().size() > 0) {
					if (!annots.contains(DiagraphKeywords.POINT_OF_VIEW)) {
						if (LOG)
							clog("GPOVCs put " + cls.getName());
						eClazs.put(cls.getName(), cls);
					}
				}
			}
		}
		List<String> povablez = new ArrayList<String>();
		for (Object povable : eClazs.keySet().toArray())
			povablez.add((String) povable);
		return povablez;
	}

	@Override
	public List<String> getPointOfViewCandidates(List<EClass> classes) {
		Map eClazs = new HashMap<String, EClass>();// FP130511
		for (EClass cls : getSyntaxElements().keySet()) {
			if (classes.contains(classes)) {
				List<String> annots = getSyntaxElements().get(cls);
				if (annots.contains(DiagraphKeywords.NODE)) {
					if (cls.getEAllContainments().size() > 0) {
						if (!annots.contains(DiagraphKeywords.POINT_OF_VIEW)) {
							if (LOG)
								clog("GPOVCs put " + cls.getName());
							eClazs.put(cls.getName(), cls);
						}
					}
				}
			}
		}
		List<String> povablez = new ArrayList<String>();
		for (Object povable : eClazs.keySet().toArray())
			povablez.add((String) povable);
		return povablez;
	}

	private Map<EClass, List<String>> getSyntaxElements() {
		Map<EClass, List<String>> syntaxElements = null;
		// List<? extends IDiagraphObject> aln = getAllNodes();
		// List<? extends IDiagraphObject> assocs = getAllAssociations();

		List<IDiagraphObject> allElements = new ArrayList<IDiagraphObject>();
		allElements.addAll(getAllNodes());
		allElements.addAll(getAllAssociations());

		for (IDiagraphObject obj : allElements) {
			if (obj instanceof IDiagraphElement) {
				IDiagraphElement element = (IDiagraphElement) obj;
				EClass cl = element.getEClass();
				List<String> entries = new ArrayList<String>();
				EList<EAnnotation> allanots = cl.getEAnnotations();
				for (EAnnotation eachanot : allanots) {
					if (eachanot.getSource().equals("diagraph")) {
						for (Map.Entry<String, String> entry : eachanot
								.getDetails())
							// if
							// (entry.getKey().startsWith(DiagraphKeywords.NODE)
							// ||
							// entry.getKey().startsWith(DiagraphKeywords.LINK))
							entries.add(entry.getKey());
						syntaxElements.put(cl, entries);
					}
				}
			}
		}

		if (LOG) {
			clog("concrete syntax elements:");
			logElements(syntaxElements);
		}
		// argument = elements;
		return syntaxElements;
	}

	private void logElements(Map<EClass, List<String>> m) {
		StringBuffer log = new StringBuffer();
		for (EClass cls : m.keySet()) {
			log.append(cls.getName()).append("\n");
			for (String annot : m.get(cls))
				log.append("    ").append(annot).append("\n");
		}
		clog(log.toString());
	}

	@Override
	public Set<EClass> getElements() {
		Map<EClass, List<String>> sel = getSyntaxElements();
		return sel.keySet();
	}

	@Override
	public EClass getPointOfView(String eclassName) { // TODO revoir
		List<String> povc = getPointOfViewCandidates();
		if (povc != null && povc.contains(eclassName)) {
			List<IDiagraphNode> aln = getAllNodes();
			for (IDiagraphNode node : aln)
				if (node.getEClass().getName().equals(eclassName))
					return node.getEClass();
		}
		return null;
	}

	private String parseView__(EAnnotation eAnnotation) {
		for (Map.Entry<String, String> entry : eAnnotation.getDetails())
			if (entry.getKey().startsWith(DiagraphKeywords.VIEW_EQU))
				return entry.getKey().split("=")[1];
		return DiagraphKeywords.DIAGRAPH_DEFAULT;
	}

	private EAnnotation getInViewDiagraphAnnotation__(EClass eclaz) {
		try {
			for (EAnnotation eAnnotation : eclaz.getEAnnotations()) { // FP121015
				if (eAnnotation.getSource().equals(
						DiagraphKeywords.DIAGRAPH_LITTERAL)) {
					if (parseView(eAnnotation).equals(
							notation.getCurrentView().getViewId()))
						return eAnnotation;
				}
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	@Override
	public void parsePov() {
		if (DParams.Parser_15_LOG)
			clog15("parsePov");
		diagraphAnnotations = new ArrayList<List<IDiagraphAnnotation>>();
		getCurrentPointOfView().parsePov();
		List<IDiagraphAnnotation> povannots = getCurrentPointOfView()
				.getRootAnnotations();
		diagraphAnnotations.add(povannots);
		// if (DParams.Parser_15_LOG)
		// logAnnotations();
	}

	@Override
	public void parse22NodeAnnotations(List<EModelElement> modelElements,
			IProgressMonitor progressMonitor) { // FP140505ppp
		if (LOG)
			clog("parse22Nodes");// FP150525

		// try {
		for (EModelElement element_ : modelElements) {
			EClass eclaz1 = null;
			EClass eclaz2 = null;
			if (element_ instanceof EClass) {
				eclaz1 = (EClass) element_;
				;
				if (LOG)
					clog("parsing EClass:" + eclaz1.getName());
			} else if (element_ instanceof EReference) {
				EReference ref = (EReference) element_;
				eclaz1 = (EClass) ref.eContainer();
				eclaz2 = (EClass) ref.getEType();
				if (LOG)
					clog("parsing EReference:" + eclaz1.getName() + "."
							+ ref.getName());
			}

			// try {
			if (eclaz1 != null && isNode(eclaz1)) {
				List<IDiagraphAnnotation> annots = parseElementAnnotations(
						eclaz1, progressMonitor);
				if (diagraphAnnotations.contains(annots))
					diagraphAnnotations.add(annots);

				if (LOG) {
					String k = "";
					for (IDiagraphAnnotation annot : annots)
						k += annot.getKey() + ";";

					clog("P22N added " + k);
				}

			}

			// } catch (Exception e) {
			// cerror("(1) error while parsing " + e.toString());
			// }

		}
		// dview.setParsedAnnotations(lannots);
		// postParse_();
		// } catch (RuntimeException e) { // FP131004 to remove
		// throw e;
		// }

	}

	@Override
	public void parse22LinkAnnotations(List<EModelElement> modelElements,
			IProgressMonitor progressMonitor) { // FP150329

		if (LOG) {
			// check with publication101
			// allnodes non remis à jour au changement de vue

			List<IDiagraphView> vs = notation.getViews();
			for (IDiagraphView view : vs) {
				clog(view.getViewId() + " - " + view.getPovName());
			}

			clog("parse22LinkAnnotations currentview=" + getCurrentView()
					+ " node count=" + getAllNodes().size());
			String cv = getCurrentView();
			int nc = getAllNodes().size();

		}

		for (EModelElement element_ : modelElements)
			if (element_ instanceof EClass
					&& isClassAssociation((EClass) element_))
				try {
					getDiagraphClassAssociation((EClass) element_); // FP150521link
				} catch (Exception e) {
					throw new RuntimeException("(2) error while parsing "
							+ e.toString());
				}
	}

	@Override
	public void parse22LnkAnnotations(List<EModelElement> modelElements,
			IProgressMonitor progressMonitor) { // FP150521
		if (LOG)
			clog("TODO implement parse22LnkAnnotations");
		if (false)
			for (EModelElement element_ : modelElements) {
				if (element_ instanceof EClass) {
					EClass s = isSourceOfClassAssociation((EClass) element_);
					if (s != null) {
						try {
							getDiagraphClassAssociation2((EClass) element_, s); // FP150521link
						} catch (Exception e) {
							cerror("(2) error while parse22LnkAnnotations "
									+ e.toString());

						}
					}
				}
			}
	}

	@Override
	public void parse21NodesAndRelations(List<EModelElement> modelElements,
			IProgressMonitor progressMonitor) { // FP140505ppp
		if (LOG)
			clog("parse21");
		try {
			for (EModelElement element : modelElements) {
				EClass eclaz1 = null;
				EClass eclaz2 = null;
				if (element instanceof EClass) {
					eclaz1 = (EClass) element;
					if (LOG)
						clog("parsing EClass:" + eclaz1.getName());
				} else if (element instanceof EReference) {
					EReference ref = (EReference) element;
					eclaz1 = (EClass) ref.eContainer();
					eclaz2 = (EClass) ref.getEType();
					if (LOG)
						clog("parsing EReference:" + eclaz1.getName() + "."
								+ ref.getName());
				}

				try {
					if (eclaz1 != null && isNode(eclaz1)) {
						getDiagraphNode(eclaz1, 0);
					}
				} catch (Exception e) {
					cerror("(3) error while parsing " + eclaz1.getName() + " ("
							+ e.toString() + ")");
				}

				try {
					if (eclaz2 != null && isNode(eclaz2)) {
						getDiagraphNode(eclaz2, 0);
					}
				} catch (Exception e) {
					cerror("(33) error while parsing " + eclaz2.getName());
				}
			}
		} catch (RuntimeException e) { // FP131004 to remove
			throw e;
		}

	}

	private void ruleReferenceNaming(List<EModelElement> modelElements) {
		if (LOG)
			clog("ruleReferenceNaming");
		for (EModelElement element : modelElements)
			if (element instanceof EReference) {
				EReference eReference = (EReference) element;
				if (eReference.getName() != null
						&& eReference.getName().equals(""))
					throw new RuntimeException("A Reference Name is empty for "
							+ ((EClass) eReference.eContainer()).getName()
							+ "---->" + eReference.getEType().getName());
			}
	}

	@Override
	public void preParse(List<EModelElement> modelElements) {
		if (LOG)
			clog("------- preParse ----------- view "
					+ getCurrentPointOfView().getViewId());
		checkBeforeParsing();
		checkInheritance(modelElements);
		checkPendingReferences(modelElements);
		ruleReferenceNaming(modelElements);

	}

	@Override
	public void postParse() { // FP140521
		resolveTargets();
		if (LOG)
			clog("------- postParse ----------- view "
					+ getCurrentPointOfView().getViewId());
	}

	private void resolveTargets() {
		for (IDiagraphAssociation af : notation.getCurrentView().getAssocs())
			if (af instanceof IDiagraphReferenceAssociation)
				((IDiagraphReferenceAssociation) af).resolveTargets();
	}

	public void postParse_nu_() { // FP140521xxx
		if (LOG)
			clog("------- postParse ----------- view "
					+ getCurrentPointOfView().getViewId());

		// ruleReferenceNaming();
		/*
		 * propagateEcoreInheritance(); logInheritance(); setNodesDepth();
		 * resolveContainmentsBaseWithName(); resolveDiagramRecursion();
		 * parseNodes(viewid); resolveLinkBaseContainment();
		 */
		handlePatterns();// diagraphNode)
		inferNodeContainments_();
		inferLinkContainments();
		// parseLinks();
		/*
		 * logContainments(); logDomainContainments();
		 * resolveContainmentsWithDeferredHosts(); checkContainer();
		 * createLabelsWithName(); cloneInheritedContainments();
		 * derivateVisualInheritance(); propagateInheritedContainments();
		 * resolveTargetRefs(); checkReferenceSyntax(); if
		 * (DParams.CHECK_POV_CONSISTENCE) resolveMissingCRefsIfCanvas();
		 * resolveReferenceTargetNodes();
		 * resolveContainmentSourceAndTargetNodes();
		 * checkMultipleContainementsForContainmentInference(); // FP131020
		 * inferContainments1(); resolveContainmentsAlt();
		 * cleanMultipleContainements(); resolveLinkBaseContainment();
		 * sortContainments(); logContainments(); checkLinkSource();
		 * inferLinkSourceReferences(); inferLinkTargetReferences();
		 * reparseLinksWithSuperClasses(); resolveLinkSourceAndTarget();
		 * checkLinkSourceAndTarget(); resolveInheritedFeatures();
		 * resolveLinks(); validateContainment();
		 * resolveContainmentReferences(); resolveContainmentNode();
		 * checkContainments(); logContainments1(); checkTargetContainments();
		 * if (DParams.CHECK_POV_CONSISTENCE) checkPointOfViewConsistence(); //
		 * FP130124 // FP121224 String pn2 = parseNodes(viewid); //FP140414//
		 * FP140328 if (DParams.Parser_CONTAINMENT2_LOG) clog(pn2);
		 */
		logNodesAndSubnodes();
		derivateAbstractContainements_todo(); // FP140124
	}

	@Override
	public EClass getPointOfView(List<EModelElement> modelElements) {
		for (EModelElement el : modelElements)
			if (el instanceof EClass)
				if (isPov((EClass) el))
					return (EClass) el;
		return null;
	}

	@Override
	public IDiagraphView setPointOfView_(EPackage packag,
			List<EModelElement> modelElements, String view) {
		// startPointOfView();//FP150325

		// String currentView = packag.getName()+"."+view;

		// if (!currentView.equals(priorView)){
		if (DParams.Parser_15_LOG)
			clog15("point of view = " + packag.getName() + "." + view);
		notation.initView(packag, view); // FP150325
		// }

		// priorView = currentView;

		IDiagraphView cv = notation.getView_(view);
		cv.init(packag);
		setCurrentPointOfView(cv);// FP140515b
		EClass pov = getPointOfView(modelElements);
		cv.setElements(modelElements, pov);
		// cv.parsePov();
		return cv;
	}

	/*
	 * @Override public String getParserLog_(String view){ return
	 * notation.getView_(view).getNotation().getParser().getLog(); }
	 */

	@Override
	public void checkBeforeParsing() { // FP150325
		if (LOG)
			clog("checkBeforeParsing");
		notation.getSyntaxRules().checkBeforeParsing();
	}

	@Override
	public void validate() {
		if (LOG)
			clog("validate");
		notation.getSyntaxRules().validate();
	}

	private void checkDirectInheritance(EClass eclass, EClass upper,
			List<EModelElement> modelElements, int depth) {
		if (eclass.getESuperTypes() != null
				&& eclass.getESuperTypes().contains(upper)) {
			if (LOG) {
				String sp = "";
				if (depth > 0)
					sp = spaces.substring(0, depth * 3);
				clog(sp + ((EClass) upper).getName() + "<-*-"
						+ eclass.getName());
			}
			EAnnotation eclassg = getInViewDiagraphAnnotation(eclass);
			EAnnotation uclassg = getInViewDiagraphAnnotation((EClass) upper);
			if (eclassg != null && uclassg == null)
				throw new RuntimeException(
						"checkDirectInheritance: Super Node is not tagged: "
								+ ((EClass) upper).getName() + "<-*-"
								+ eclass.getName() + " for view "
								+ notation.getCurrentView().getViewId()); // FP131019
			checkInheritanceTree(eclass, modelElements, ++depth);
		}
	}

	private void checkInheritanceTree(EClass supertype,
			List<EModelElement> modelElements, int depth) {
		for (EModelElement eModelElement : modelElements)
			if (eModelElement instanceof EClass)
				checkDirectInheritance((EClass) eModelElement, supertype,
						modelElements, depth);
	}

	private void checkInheritance(List<EModelElement> elems) {
		if (LOG)
			clog("checkInheritance");
		for (EModelElement me : elems)
			if ((me instanceof EClass)
					&& (((EClass) me).getEAllSuperTypes().size() == 0))
				checkInheritanceTree((EClass) me, elems, 0);
	}

	private void checkPendingReferences(List<EModelElement> elems) {
		if (LOG)
			clog("checkPendingReferences");
		notation.getEcoreUtils().checkPendingReferences(elems);
	}

	/******************************/

	private List<String> getTagValues(EClass eclaz, String key) {
		List<String> result = new ArrayList<String>();
		if (!isNode(eclaz) && !!isGeneric(eclaz))
			throw new RuntimeException("getTagValue must apply on a node");
		for (EAnnotation eAnnotation : eclaz.getEAnnotations())
			if (isView(eAnnotation))
				for (Map.Entry<String, String> entry : eAnnotation.getDetails())
					if (entry.getKey().startsWith(key + "="))
						result.add(entry.getKey().split("=")[1]);
		return result;
	}

	private IDiagraphNode getKrefNode(IDiagraphReferenceAssociation raf,
			EClass eclaz, String val, EClass concreteClass,// FP150318t
			String arg2) { // FP140617
		boolean not_propagated = false;

		String value = val;
		String arg1 = null;
		if (val.contains(",")) {
			String[] vz = val.split(",");
			value = vz[0];
			arg1 = vz[1];
		}

		IDiagraphNode nod = getNodeWithKReference(eclaz, value, concreteClass,// FP150331a304
				arg1, arg2, false, not_propagated);
		boolean sourceCanvas = raf.getSource().getName() == getCurrentPointOfView()
				.getPovName();
		if (sourceCanvas) {
			raf.setSourcePov(); // FP150322voir
			if (DParams.Parser_29_LOG)
				clog15(raf + " isSourcePov");
		}
		return nod;
	}

	private IDiagraphNode getCrefNode(IDiagraphReferenceAssociation raf,
			EClass srcclaz, String value, EClass concreteClass,// FP150318t
			String arg_) { // FP140617
		boolean not_propagated = false;
		IDiagraphNode nod = getNodeWithCReferenc_e(srcclaz, value,
				concreteClass,// FP150331a304
				arg_, not_propagated);
		boolean sourceCanvas = raf.getSource().getName() == getCurrentPointOfView()
				.getPovName();
		if (sourceCanvas) {
			raf.setSourcePov(); // FP150322voir
			if (DParams.Parser_29_LOG)
				clog15(raf + " isSourcePov");
		}
		return nod;
	}

	/******************************/

	private void logContainment(EClass claz, EClass targetClass, String target,
			String label, boolean propagated,
			IDiagraphReferenceAssociation exists, int caze) {

		if (DParams.Parser_15_LOG || DParams.Parser_29_LOG) {
			clog2("(a)GN_KREF_" + caze + " " + claz.getName() + " -> " + target
					+ " (" + targetClass.getName() + ")" + " ["
					+ (propagated ? " ypropa " : " npropa ")
					+ (label == null ? " null_label " : label)
					+ (exists == null ? " ko " : " ok ") + "]");

		}

	}

	private List<EClass> getTargetClasses(EClass claz, String target) { // FP150329a120
																		// yy5
		List<EClass> result = new ArrayList<EClass>();

		if (((EClass) ((EReference) claz.getEStructuralFeature(target))
				.getEType()).isAbstract()) {

			List<EClass> targs = getAllConcreteTargets(claz, target);// FP150330a302
			for (EClass targ : targs) {
				if (!result.contains(targ))
					result.add(targ);
			}

		} else {
			result.add(((EClass) ((EReference) claz
					.getEStructuralFeature(target)).getEType()));
		}

		return result;
	}

	@Override
	public IDiagraphReferenceAssociation createContainment(
			IDiagraphAssociation.AssociationType containmentType,
			EClass element, String target, String containmentName, // boolean
																	// isPort,
			boolean propagated, boolean doit) {
		IDiagraphReferenceAssociation result = null;
		switch (containmentType) {
		case SHARED_COMPARTMENT_:// FP150512 CONTAINMENT:
			result = createNodeContainment(element, target, containmentName,
					false, propagated, true, doit);
			break;
		case TYPED_COMPARTMENT:
			result = createNodeCompartment_2(element, target, containmentName,
					propagated, true, doit);
			break;
		case AT_PORT:
			result = createNodeContainment(element, target, containmentName,
					true, propagated, true, doit);
			break;
		default:
			break;
		}
		return result;
	}

	private IDiagraphReferenceAssociation createNodeCompartmentOnConcrete(
			EClass element, String target, String label,
			boolean figureEmbedded, int order, boolean isPropagated) {
		String viewid = notation.getCurrentView().getViewId();
		boolean knameinferred = false;
		boolean kcreated = false;
		if (label == null) // compartment name
			label = target;
		IDiagraphReferenceAssociation containment = null;
		if (isRecursiveCompartment(element, target))
			containment = createRecursiveCompartment(element, target, label,
					order, isPropagated);
		else
			containment = createBaseCompartmentOnConcrete(element, target,
					label, order, isPropagated);
		return containment;
	}

	private IDiagraphReferenceAssociation createNodeCompartmentOnAbstract(
			EClass element, EClass targ, EReference erf, String target,
			String label, boolean figureEmbedded, int order,
			boolean isPropagated) {
		boolean knameinferred = false;
		boolean kcreated = false;

		if (label == null) // compartment name
			label = target;
		IDiagraphReferenceAssociation containment = null;
		if (isRecursiveCompartment(element, target))
			containment = createRecursiveCompartment(element, target, label,
					order, isPropagated);
		else
			containment = createBaseCompartmentOnAbstract(element, targ, erf,
					target, label, order, isPropagated);
		return containment;
	}

	// KREF
	private IDiagraphReferenceAssociation createNodeCompartment_2(
			EClass element, String target, String label, boolean propagated,
			boolean doAdd, boolean derived) {
		boolean knameinferred = false;
		boolean kcreated = false;
		;
		if (label == null) // compartment name
			label = target;
		EReference ref = (EReference) element.getEStructuralFeature(target);

		if (DParams.Parser_CONTAINMENT2_LOG) {
			String tocreeate = (element).getName() + "." + target;
			clog2("tocreeate=" + tocreeate);
		}

		// if (DParams.RAISE_TODO)
		// if (isRecursiveCompartment(element, target)) // FP140329
		// throw new RuntimeException("TODO in createNodeContainment");

		IDiagraphNode nod = getDiagraphNode(element, 0);
		IDiagraphReferenceAssociation containment = nod == null ? null : nod
				.findContainmentByTargetName(target);

		if (DParams.Parser_CONTAINMENT2_LOG) {
			String t = ((EClass) element).getName() + "." + target;
			if (doAdd)
				clog2("-----(2)createNodeCompartment "
						+ ((EClass) element).getName()
						+ "."
						+ target
						+ " "
						+ (propagated ? " ypropa " : " not_propa ")
						+ (containment == null ? " cont not exixts "
								: " cont exists "));
			else
				clog2("(" + ((EClass) element).getName() + "." + target
						+ " ok)");
		}
		if (containment != null)
			throw new RuntimeException("should not happen in ");

		containment = createContainment(ref, propagated, // true,
				true, false);// !DParams.NO_COMPARTMENTS);

		// containment.setName(label);
		containment.setDerived(derived);
		containment.setTargetName(target);
		IDiagraphNode dnode = getDiagraphNode(element, 0);
		containment.setSourceNode(dnode);
		// EClass src = dnode.getSemanticRole();
		dnode.addContainment(6, containment, -1, doAdd);
		EReference dc = dnode.getDeclaredContainment(); // FP140408

		// if (LOG)
		// DiaUtils.log("createNodeCompartment", label, kcreated,
		// knameinferred, containment, null);
		return containment;
	}

	// key.equals(CANVAS)
	@Override
	public IDiagraphNode getCanvas(EClass eclaz) { // FP140615c
		IDiagraphNode nod = null;// getNamedNode_(eclaz, null); //FP140521
		// FP140521yyy
		if (canvasNode != null
				&& canvasNode.getView().equals(
						notation.getCurrentView().getViewId())
				&& canvasNode.getEClass() == eclaz
				&& canvasNode.getDepth() == -1)
			nod = canvasNode;
		else
			nod = getNamedNode(eclaz, null);
		if (nod == null)
			throw new RuntimeException("no canvas");

		// FP140617voir3
		/*
		 * if (this.currentCanvasNode__ != null && currentCanvasNode__ != nod)
		 * cerror("(1) Trying to set a canvas: " + nod.getName() +
		 * " but a Canvas node already exists: " + currentCanvasNode__.getName()
		 * + " !!!!");
		 */

		if (canvasNode == null || canvasNode != nod) {
			canvasNode = nod;

			IDiagraphNode exists = getCurrentPointOfView().getPov();
			if (exists == null) {
				nod.setPov();
				getCurrentPointOfView().setPov(nod);
				if (LOG)
					clog("canvas is " + nod.getName() + " for view "
							+ nod.getView());
			}
		} else if (LOG)
			clog("canvas was already " + nod.getName() + " for view "
					+ nod.getView());

		return nod;
	}

	// key.equals(NODE)
	@Override
	public IDiagraphNode getNamedNode(EClass eclaz, String nodeLabels) {
		IDiagraphNode nod = findNode_(eclaz);
		if (nodeLabels != null && !nodeLabels.isEmpty()) {// FP140429
			if (nod != null) {
				if (nod.getLabels().isEmpty())
					nod.getLabels().add(nodeLabels); // TODO refactor for
														// multiple labels
				else
					nod.getLabels().set(0, nodeLabels);
			}
		}
		return nod;
	}

	private List<IDiagraphReferenceAssociation> handlePatternTargetConcrete(
			List<IDiagraphReferenceAssociation> rafs, EClass eclaz) {
		List<IDiagraphReferenceAssociation> result = new ArrayList<IDiagraphReferenceAssociation>();
		for (IDiagraphReferenceAssociation raf : rafs) {
			if (!patternTargetAbstract_(raf)) {
				result.add(raf);
			} else
				raf.addTargetAbstract_(eclaz);
		}
		return result;
	}

	@Override
	public List<IDiagraphReferenceAssociation> patternTargetConcreteContainmentAssociationsTo(
			EClass eclaz) {
		return handlePatternTargetConcrete(
				getContainmentAssociationsAndSubHierTo(eclaz), eclaz);
	}

	@Override
	public List<IDiagraphReferenceAssociation> parseDirectSimpleAssociationsTo(
			EClass eclaz) {
		List<IDiagraphReferenceAssociation> result = new ArrayList<IDiagraphReferenceAssociation>();
		for (IDiagraphReferenceAssociation ass : getSimpleAssociationsAndSubHierTo(eclaz))
			if (!ass.isTargetAbstract())
				result.add(ass);
		return result;
	}

	@Override
	public List<EReference> patternContainmentAssociationsKrefAutoCBySuper(
			EClass eclaz, boolean abstragt) {
		List<EReference> result = new ArrayList<EReference>();
		List<IDiagraphReferenceAssociation> rs = patternContainmentAssociationsKrefAutoCBySuperNodes(
				eclaz, abstragt);
		for (IDiagraphReferenceAssociation r : rs)
			if (!result.contains(r.getTargetReference()))
				result.add(r.getTargetReference());
		return result;
	}

	@Override
	public List<EReference> patternContainmentAssociationsKrefAutoCBySubAbstract(
			EClass eclaz, boolean abstrag) {
		List<EReference> result = new ArrayList<EReference>();
		List<IDiagraphReferenceAssociation> rs = patternContainmentAssociationsKrefAutoCBySuperNodes(
				eclaz, abstrag);
		for (IDiagraphReferenceAssociation r : rs)
			if (!result.contains((r.getTargetReference())))
				result.add(r.getTargetReference());
		return result;
	}

	@Override
	public List<EReference> patternContainmentAssociationsKrefAutoCBySub(
			EClass eclaz, boolean abstragt) {
		List<EReference> result = new ArrayList<EReference>();
		List<IDiagraphReferenceAssociation> rs = patternContainmentAssociationsKrefAutoCBySuperNodes(
				eclaz, abstragt);
		for (IDiagraphReferenceAssociation r : rs)
			if (!result.contains((r.getTargetReference())))
				result.add(r.getTargetReference());
		return result;
	}

	private boolean patternSibling(IDiagraphReferenceAssociation raf,
			EClass eclaz) {
		EClass supctner = (EClass) raf.getTargetReference().eContainer();
		boolean result = (!raf.getInstanceSource().isAbstract() // FP150518azer
				&& supctner.isSuperTypeOf(eclaz) && !(eclaz == raf.getSource()) && !eclaz
				.isSuperTypeOf(raf.getSource()));
		if (result)
			if (DParams.Parser_15_LOG)
				clog15("pattern Sibling for " + raf.toId());
		return result;
	}

	private boolean patternTargetAbstract_(IDiagraphReferenceAssociation raf) {
		boolean result = raf.isTargetAbstract();
		if (result)
			if (DParams.Parser_15_LOG)
				clog15("pattern Target Abstract for " + raf.toId());
		return result;
	}

	private boolean patternKrefAutoCBySubNodes(
			IDiagraphReferenceAssociation raf, EClass eclaz) {
		boolean result = isAnnotated(eclaz, raf.getTargetReference().getName(),
				IDiagraphAssociation.AssociationType.TYPED_COMPARTMENT,
				InheritanceType.INHERITANCE)
				&& (subNodes(eclaz).contains(raf.getSource())
						&& raf.isRefContainment() && raf.getType() == eclaz);
		if (result)
			if (DParams.Parser_15_LOG)
				clog15("pattern KrefAutoCBySubNodes for " + raf.toId());
		return result;
	}

	private boolean patternKrefAutoCBySuperNodes(
			IDiagraphReferenceAssociation raf, EClass eclaz) {
		boolean result = (isAnnotated(eclaz,
				raf.getTargetReference().getName(),
				IDiagraphAssociation.AssociationType.TYPED_COMPARTMENT,
				InheritanceType.INHERITANCE))
				&& (((EClass) raf.getTargetReference().eContainer())
						.isSuperTypeOf(eclaz) && raf.getSource().isSuperTypeOf(
						eclaz));
		if (result)
			if (DParams.Parser_15_LOG)
				clog15("pattern KrefAutoCBySuperNodes for " + raf.toId());
		return result;
	}

	private boolean patternKrefAutoC(IDiagraphReferenceAssociation raf,
			EClass eclaz) {
		boolean result = isAnnotated(eclaz, raf.getTargetReference().getName(),
				IDiagraphAssociation.AssociationType.TYPED_COMPARTMENT)
				&& ((EClass) raf.getTargetReference().eContainer()) == eclaz
				&& raf.getSource() == eclaz;
		if (result)
			if (DParams.Parser_15_LOG)
				clog15("pattern KrefAutoCBySuperNodes for " + raf.toId());
		return result;
	}

	static int counter = 0;

	private boolean patternKrefContainmentAlt_(
			IDiagraphReferenceAssociation raf, EClass eclaz,
			List<EReference> refs) {
		;
		boolean result = (raf.getTarget() == eclaz)
				&& (hasKref(eclaz, raf.getTargetReference().getName()))
				&& (!refs.contains(raf.getTargetReference()));
		if (result)
			if (DParams.Parser_15_LOG) {

				clog15("[" + counter++ + "] pattern KrefContainmentAlt for "
						+ raf.toId());
			}
		return result;
	}

	@Override
	public void endParse_2(List<EModelElement> modelElements,
			IProgressMonitor progressMonitor) {
		EClass c = null;
		try {
			for (EModelElement element : modelElements) {
				if (element instanceof EClass) {
					c = (EClass) element;
					if (isNode(c)) {
						List<IDiagraphReferenceAssociation> s = getSimpleAssociationsAndSubHierTo(c);// FP150407ccccc
						if (!s.isEmpty())
							for (IDiagraphReferenceAssociation d : s) {
								if (report.isEmpty())
									report.add(d.toCsvHeader() + "\n");
								report.add(d.toCsv() + "\n");
							}
					}
				}
			}
			for (EModelElement element : modelElements) {
				if (element instanceof EClass) {
					c = (EClass) element;
					if (isNode(c)) {
						List<IDiagraphReferenceAssociation> l = getContainmentAssociationsAndSubHierTo(c);// FP150407ccccc
						if (!l.isEmpty())
							for (IDiagraphReferenceAssociation d : l) {
								if (report.isEmpty())
									report.add(d.toCsvHeader() + "\n");
								report.add(d.toCsv() + "\n");
							}
					}
				}
			}
		} catch (Exception e) {
			report.add("error endParse_2;" + e.toString());
			cerror("error endParse_2;" + e.toString());
		}
		notation.creport(report);
	}

	@Override
	public void startParse_2() {
		report = new ArrayList<String>();
		containmentAssociations = new HashMap<EClass, List<IDiagraphReferenceAssociation>>();
		simpleAssociations = new HashMap<EClass, List<IDiagraphReferenceAssociation>>();

	}

	private void setDiagraphClassAssociationProperties(
			IDiagraphClassAssociation classoc) {
		EClass eclaz = classoc.getLink();
		classoc.setAbstract(!isLinkInstanciable(eclaz)); // FP150326

	}

	private IDiagraphNode setDiagraphNodeProperties(DiagraphNode diagraphNode) { // FP140218
		EClass eclaz = diagraphNode.getEClass();
		;
		boolean check = false;

		boolean abstragt = true;
		boolean not_abstract = false;

		int i = 0;
		try {

			List<IDiagraphReferenceAssociation> siblingKs = patternSiblingAllContainmentAssociations(eclaz);
			if (!siblingKs.isEmpty())
				check = true;
			diagraphNode.setAllSiblingContainements(siblingKs);
			i = 1;


			List<IDiagraphReferenceAssociation> directContainments = patternTargetConcreteContainmentAssociationsTo(eclaz);
			if (!directContainments.isEmpty())
				check = true;

			diagraphNode.setContainements(directContainments);

			i = 2;

			List<IDiagraphReferenceAssociation> di = getSimpleAssociationsAndSubHierTo(eclaz);
			if (!di.isEmpty())
				check = true;
			diagraphNode.setAllSimpleReferences(di, eclaz);

			List<IDiagraphReferenceAssociation> disa = parseDirectSimpleAssociationsTo(eclaz);
			if (!disa.isEmpty())
				check = true;
			diagraphNode.setSimpleReferences(disa, eclaz);

			// diagraphNode.setAbstract(isAbstractNode(eclaz));
			boolean notinst = !isNodeInstanciable(eclaz);

			if (notinst)
				check = true;

			diagraphNode.setAbstract(notinst);

			// diagraphNode.setPov(isPov(eclaz));

			boolean isgen = isGeneric(eclaz);

			if (isgen)
				check = true;

			diagraphNode.setGeneric(isgen);

			List<EReference> rs = patternContainmentAssociationsKrefAutoCBySuper(
					(EClass) eclaz, abstragt);

			if (!rs.isEmpty())
				check = true;

			diagraphNode.setAbstractAutoCompartmentBySupers(rs);

			i = 2;
			List<EReference> autocs = patternContainmentAssociationsKrefAutoCBySuper(
					(EClass) eclaz, not_abstract);
			if (!autocs.isEmpty())
				check = true;
			diagraphNode.setAutoCompartmentBySupers(autocs);

			List<EReference> pats = patternContainmentAssociationsKrefAutoCBySub(
					(EClass) eclaz, abstragt);

			if (!pats.isEmpty())
				check = true;
			i = 21;
			diagraphNode.setAbstractAutoCompartments(pats);
			i = 22;
			List<EReference> pats2 = patternContainmentAssociationsKrefAutoCBySub(
					(EClass) eclaz, not_abstract);
			if (!pats2.isEmpty())
				check = true;

			diagraphNode.setAutoCompartments(pats2);
			i = 3;

			List<EReference> kca = patternKrefContainmentAlt((EClass) eclaz);
			if (!kca.isEmpty())
				check = true;

			diagraphNode.setKrefAltContainments(kca);
			i = 31;

			List<EReference> dcr = getDirectContainementReferences(eclaz);
			if (!dcr.isEmpty())
				check = true;

			diagraphNode.setDirectContainmentReferences_(dcr);
			i = 32;

			List<EReference> acr = parseAllContainementReferences(eclaz);
			if (!acr.isEmpty())
				check = true;

			diagraphNode.setAllContainmentReferences(acr);
			i = 33;

			List<IDiagraphReferenceAssociation> cash = getContainmentAssociationsAndSubHierTo(eclaz);// FP150407ccccc
			if (!cash.isEmpty())
				check = true;

			diagraphNode.setAllContainements(cash); // FP140417
			// depl

			i = 34;

			boolean idon = isDirectTopNode(eclaz);
			if (idon)
				check = true;
			diagraphNode.setDirectTopNode(idon);
			i = 35;
			boolean iton = isInheritedTopNode(eclaz);
			if (iton)
				check = true;

			diagraphNode.setInheritedTopNode(iton);
			i = 36;

			boolean isef = isSelfContained(eclaz);
			if (isef)
				check = true;

			diagraphNode.setSelfContained(isef);
			i = 37;

			boolean incont = patternContainedThroughInheritance(eclaz);

			if (incont)
				check = true;
			diagraphNode.setSelfContainedThroughInheritance(incont);
			i = 4;

			List<EClass> sgeno = getSuperGenericsOrNode(eclaz, false);
			if (!sgeno.isEmpty())
				check = true;

			diagraphNode.setSuperGenericsOrNode(sgeno);
			i = 41;

			List<EClass> subgeno = getSubGenericsOrNodes_(eclaz);
			if (!subgeno.isEmpty())
				check = true;

			diagraphNode.setSubGenericsOrNode(subgeno);
			i = 42;
			EClass spo = getPovIfSuperPov(eclaz);
			if (spo != null)
				check = true;// FP150429

			diagraphNode.setPovIfSuperPov(spo);
			i = 43;

			// int dep = diagraphNode.computeDepth(); //FP150428

			i = 44;
			// handlePatterns(diagraphNode);

			i = 45;

			// inferNodeContainment_(diagraphNode, depth);
			/*
			 * IDiagraphView curpov = getCurrentPointOfView();
			 *
			 * if (curpov != null && !curpov.getNodes().contains(diagraphNode))
			 * curpov.addNode(diagraphNode);
			 */

			i = 6;

			if (eclaz.getName().equals("Toz"))
				check = true;

			// FP150428
			boolean isPov = isPov(eclaz);
			if (isPov)
				diagraphNode.setPov();
			boolean isSubPov = isSubPov(eclaz);
			if (isSubPov)
				diagraphNode.setPov();
			if (isPov)
				check = false;

			if (eclaz.getName().equals("Toz"))
				check = true;

			int dep = diagraphNode.computeDepth(); // FP150428
			// List<EReference> all = parseAllContainementReferences(eclaz);
			// List<EReference> directs =
			// getDirectContainementReferences(eclaz);

			int directscountb = dcr.size();
			int inheritedscountb = acr.size();

			boolean isGeneric = isGeneric(eclaz);
			boolean isAbstractNode = isNodeInstanciable(eclaz);

			boolean isDirectTopNode = isDirectTopNode(eclaz);
			boolean isInhTopNode = isInheritedTopNode(eclaz);
			boolean isSelfContained = isSelfContained(eclaz);
			check = true;
			boolean isContainedThroughInheritance = notation.getParser()
					.patternContainedThroughInheritance(eclaz);
			// .patternSelfContainedThroughInheritance(eclaz);

			if (DParams.Parser_15_LOG) {
				String s3b = (isPov ? " isPov " : "")
						+ (isGeneric ? " isGeneric " : "")
						+ (isAbstractNode ? " isAbstractNode " : "")
						+ (isDirectTopNode ? " isTopNode " : "")
						+ (isInhTopNode ? " isInheritedTopNode  " : "")
						+ (isContainedThroughInheritance ? " isContainedThroughInheritance "
								: "")
						+ (isSelfContained ? " isSelfContained " : "") + ("")
						+ ("");
				String result = "directscount=" + directscountb
						+ " inheritedscount=" + inheritedscountb;
				result = "s3=" + s3b + result;
				clog15("-----\n" + result + "-----\n");
			}

		} catch (Exception e) {
			throw new RuntimeException("error: " + i
					+ " in createDiagraphNode " + " for " + eclaz.getName()
					+ " (" + e.toString() + ")");
		}
		return diagraphNode;
	}

	private List<IDiagraphReferenceAssociation> parseContainementReferenceAssociationOnConcrete2t1( // FP150321voir
			EClass eclaz, String targetValue) {
		List<IDiagraphReferenceAssociation> contassocs = getContainmentAssociationsAndSubHierTo(eclaz);
		List<IDiagraphReferenceAssociation> result = new ArrayList<IDiagraphReferenceAssociation>();

		for (IDiagraphReferenceAssociation rassoc : contassocs) {
			if (rassoc.getTargetReference().getName().equals(targetValue)) {
				result.add(rassoc);
				// rassoc.setId("cont_concrete");
				// rassoc.addCase(AssociationCandidateType.CAND_CONT_CONCRETE_);
			}
		}
		return result;
	}

	// //////////////////////////////////////

	@Override
	public List<IDiagraphReferenceAssociation> getContainmentAssociationsAndSubHierTo(
			EClass eclaz) { // FP150329// FP150318zzz


		List<IDiagraphReferenceAssociation> result = containmentAssociations
				.get(eclaz);
		if (result == null) {
			result = doParseContainmentAssociationsAndSubHierTo(eclaz);
			containmentAssociations.put(eclaz, result);
		}
		return result;
	}

	@Override
	public List<IDiagraphReferenceAssociation> getSimpleAssociationsAndSubHierTo(
			EClass eclaz) {
		List<IDiagraphReferenceAssociation> result = simpleAssociations
				.get(eclaz);
		if (result == null) {
			result = doParseSimpleAssociationsAndSubHierTo(eclaz);
			simpleAssociations.put(eclaz, result);
		}
		return result;
	}

	private List<IDiagraphReferenceAssociation> doParseSimpleAssociationsAndSubHierTo(
			EClass eclaz) { // FP150318zzz
		// AssociationCandidateType caz = AssociationCandidateType.CAND_SIMPLE_;
		AssociationType[] typs = { AssociationType.SIMPLE };
		List<IDiagraphReferenceAssociation> l = getCurrentPointOfView()
				.findReferenceAssociationsByTargetName(eclaz.getName(), typs);// FP150516x
		if (l == null || l.isEmpty()) {
			l = doParseSimpleAssociationsAndSubHierTo2t1(eclaz);
			if (!l.isEmpty())
				getCurrentPointOfView().addReferenceAssociations(
				// eclaz.getName(), //FP150521
						l);
		}
		/*
		 * for (IDiagraphReferenceAssociation raf : l) { if (!raf.contains(caz))
		 * { raf.addCase(caz); if (DParams.Parser_15_LOG) clog15("2wwww" +
		 * raf.toString()); } }
		 */
		return l;
	}

	private List<IDiagraphReferenceAssociation> doParseContainmentAssociationsAndSubHierTo(
			EClass eclaz) {


		AssociationCandidateType caz = AssociationCandidateType.CAND_CONTAINMENT;
		List<IDiagraphReferenceAssociation> result = null;
		// if ((result == null || result.isEmpty())) {
		result = doParseContainmentAssociationsAndSubHierTo2t1(eclaz);
		if (!result.isEmpty())
			getCurrentPointOfView().addReferenceAssociations(
			// eclaz.getName(), //FP150521
					result);
		for (IDiagraphReferenceAssociation raf : result)
			if (!raf.contains(caz)) {
				raf.addCase(caz);
				if (DParams.Parser_15_LOG)
					clog15("1wwww" + raf.toString());
			}
		// }

		return result;
	}

	private boolean isView(String viewid, EAnnotation anot) { // FP131208
		if (!anot.getSource().equals(DiagraphKeywords.DIAGRAPH_LITTERAL))
			return false;
		if (viewid == null || viewid.equals(DiagraphKeywords.DIAGRAPH_DEFAULT)
				|| viewid.isEmpty())
			return isDefaultView(anot);
		for (Map.Entry<String, String> entry : anot.getDetails())
			if (entry.getKey().equals(DiagraphKeywords.VIEW_EQU + viewid))
				return true;
		return false;
	}

	private List<EClass> getOtherNodeClasses(EClass target) {
		String view = this.getCurrentView();
		List<EAnnotation> anots = target.getEAnnotations();
		for (EAnnotation anot : anots)
			if (isView(view, anot))
				return getOtherNodeClasses(anot, view);
		return new ArrayList<EClass>();
	}

	private List<EClass> getOtherNodeOrGenericClasses(EClass target) {// FP150520
		String view = this.getCurrentView();
		List<EAnnotation> anots = target.getEAnnotations();
		for (EAnnotation anot : anots)
			if (isView(view, anot))
				return getOtherNodeOrGenericClasses_(anot, view);
		return new ArrayList<EClass>();
	}

	// //////////////////////////////////////

	// 18_19h6
	private List<List<IDiagraphReferenceAssociation>> parseContainmentAssocsAndSubHierTo_old_0(
			EClass target, boolean abztract_) {

		boolean derived_no = false;
		List<EReference> contrefs = getNodeRefsToNodeOrGeneric(target, true);

		for (EReference ref_ : contrefs) {
			EClass refcont = (EClass) ref_.eContainer();
			EClass reftyp = (EClass) ref_.getEType();

			if (LOG)
				clog(refcont.getName() + "." + ref_.getName() + "."
						+ reftyp.getName() + "("
						+ ((EClass) ref_.eContainer()).getName() + "->"
						+ ((EClass) ref_.getEType()).getName() + ")");
		}

		List<List<IDiagraphReferenceAssociation>> result = new ArrayList<List<IDiagraphReferenceAssociation>>(); // FP140330
		if (!contrefs.isEmpty())
			for (EReference ref : contrefs) {
				if (ref.isContainment()) {
					List<IDiagraphReferenceAssociation> assocs = new ArrayList<IDiagraphReferenceAssociation>();
					EClass sourcecontner = (EClass) ref.eContainer();
					boolean simple_no = false;
					if (abztract_ || !toAbstract(ref, target)) {// !sourcecontner.isAbstract()
																// &&
																// //FP150320voir
						IDiagraphReferenceAssociation dir = parseReferenceAssociation_(
								sourcecontner, sourcecontner, target, ref,
								derived_no, simple_no);
						if (!assocs.contains(dir)) {
							assocs.add(dir);
							if (DParams.Parser_15_LOG) {
								if (dir.isTargetAbstract())
									clog2("target abstract for :" + dir.toLog());
								logBuffer += dir.toLog() + '\n';
							}
						}

						for (IDiagraphReferenceAssociation sub : createReferenceSubContainingAssociations(
								sourcecontner, target, ref))
							if (!assocs.contains(sub)) {
								assocs.add(sub);
								if (DParams.Parser_15_LOG)
									logBuffer += "sub contaiment assoc:"
											+ sub.toLog() + '\n';
							}
					} // FP150320voir
					result.add(assocs);
				}
			}
		if (LOG && reportLog != null && reportLog.isEmpty())
			reportLog = "empty";
		return result;
	}

	private List<IDiagraphReferenceAssociation> parseContainmentAssocsAndSubHierTo(
			EClass target) {

		boolean derived_no = false;
		boolean simple_no = false;
		List contrefs = getNodeReferenceRealizations_new(target); // FP150523b
		// List contrefs = getNodeReferenceRealizations__old_nu(target);
		if (LOG)
			if (contrefs != null && !contrefs.isEmpty()) {
				for (Object cnrf_ : contrefs) {
					Object[] r_ = (Object[]) cnrf_;
					EClass refcont = (EClass) r_[0];
					EReference ref_ = (EReference) r_[1];
					EClass reftyp = (EClass) r_[2];

					clog(refcont.getName() + "." + ref_.getName() + "."
							+ reftyp.getName() + "("
							+ ((EClass) ref_.eContainer()).getName() + "->"
							+ ((EClass) ref_.getEType()).getName() + ")");
				}
			}

		List<IDiagraphReferenceAssociation> result = new ArrayList<IDiagraphReferenceAssociation>(); // FP140330
		if (!contrefs.isEmpty()) {
			for (Object cnrf_ : contrefs) {
				Object[] r_ = (Object[]) cnrf_;
				EClass refcont = (EClass) r_[0];
				EReference ref = (EReference) r_[1];
				EClass reftyp = (EClass) r_[2];
				EClass sourcecontner = refcont;
				IDiagraphReferenceAssociation dir = parseReferenceAssociation_(
						sourcecontner, sourcecontner, reftyp, ref, derived_no,
						simple_no);
				if (!result.contains(dir)) {
					result.add(dir);
					if (DParams.Parser_15_LOG) {
						if (dir.isTargetAbstract())
							clog2("target abstract for :" + dir.toLog());
						logBuffer += dir.toLog() + '\n';
					}
				}
			}
		}
		if (LOG && reportLog != null && reportLog.isEmpty())
			reportLog = "empty";
		return result;
	}

	private List<IDiagraphReferenceAssociation> doParseContainmentAssociationsAndSubHierTo2t1(
			EClass eclaz) {// boolean abztract,
		if (LOG)
			clog("parseContainmentAssociationsTo " + eclaz.getName() + "."
					+ notation.getCurrentView().getViewId() + " [");
		List<IDiagraphReferenceAssociation> assocs = parseContainmentAssocsAndSubHierTo(eclaz);
		if (LOG) {
			for (IDiagraphReferenceAssociation ass : assocs)
				clog(ass.toLogCompl() + "." + ass.hashCode());
			clog("]");
		}
		return assocs;
	}

	private List<IDiagraphReferenceAssociation> doParseSimpleAssociationsAndSubHierTo2t1(
			EClass eclaz) {
		List<IDiagraphReferenceAssociation> result = new ArrayList<IDiagraphReferenceAssociation>();
		List<List<IDiagraphReferenceAssociation>> conts = parseSimpleAssocsAndSubHierTo(eclaz);
		for (List<IDiagraphReferenceAssociation> assocs : conts)
			for (IDiagraphReferenceAssociation ass : assocs)
				if (!result.contains(ass)) {
					result.add(ass);
				}
		return result;
	}

	// ////////////////////////

	private void logContainmentReferences_(EClass eclaz, String value) { // FP140617

		String viewid = notation.getCurrentView().getViewId();// FP150318ta

		if (DParams.Parser_15_LOG)
			clog15("KReference_new " + viewid + "." + eclaz.getName() + "/"
					+ value);

		EReference targetReference = (EReference) eclaz
				.getEStructuralFeature(value);
		EClass rkt = (EClass) targetReference.getEType();
		List<EClass> cc = getTargetClasses(eclaz, value);
		if (cc.isEmpty())
			throw new RuntimeException(
					"should not happen in parseKReference_new");

		if (DParams.Parser_15_LOG) {
			if (cc.size() > 1) {
				for (EClass c : cc) {
					if (c != targetReference.getEType())
						clog15("A:" + rkt.getName() + " <- C:" + c.getName());
				}
			} else {
				if (cc.get(0) != targetReference.getEType())
					clog15("A:" + rkt.getName() + " <- C:"
							+ cc.get(0).getName());
			}
		}

	}

	private List<IDiagraphReferenceAssociation> parseContainmentReferenceIfAbstract_(
			EClass eclaz, String val) {
		String log = "";
		String value = val;
		String arg = null;
		if (val.contains(",")) {
			String[] vz = val.split(",");
			value = vz[0];
			arg = vz[1];
		}

		EReference targetReference = (EReference) eclaz
				.getEStructuralFeature(value);
		if (targetReference == null)
			throw new RuntimeException("target reference is not correct for "
					+ eclaz.getName() + "." + value); // FP150427
		if (DParams.Parser_29_LOG)
			logKReferenceIfAbstract_(eclaz, value);
		// EReference targetReference = (EReference) eclaz
		// .getEStructuralFeature(value);
		List<IDiagraphReferenceAssociation> cont = parseContainementReferenceAssociationToAbstract(targetReference);

		if (arg != null)
			for (IDiagraphReferenceAssociation c : cont) {
				c.setArgument(arg); // FP150512transp //FP150516y
			}

		if (DParams.Parser_29_LOG) {
			for (IDiagraphReferenceAssociation c : cont) {
				log += c.toId() + ", ";
			}

		}

		// parseContainementReferenceAssociationAorC_(targetReference);

		return cont;
	}

	private void logKReferenceIfAbstract_(EClass eclaz, String value) {
		String viewid = notation.getCurrentView().getViewId();// FP150318ta
		if (DParams.Parser_15_LOG)
			clog15("logKReferenceIfAbstract " + viewid + "." + eclaz.getName()
					+ "/" + value);
		EReference targetReference_ = (EReference) eclaz
				.getEStructuralFeature(value);
		EClass rkt = (EClass) targetReference_.getEType();
		if (DParams.Parser_15_LOG)
			clog15("A:" + rkt.getName() + " <- K:" + eclaz.getName());

	}

	private List<IDiagraphReferenceAssociation> parseContainmentReferences__(
			EClass eclaz, String val) { // FP140617
		String valu = val;
		String arg = null;
		if (val.contains(",")) {
			String[] vz = val.split(",");
			valu = vz[0];
			arg = vz[1];
		}

		if (DParams.Parser_29_LOG)
			logContainmentReferences_(eclaz, valu);

		EReference targetReference = (EReference) eclaz
				.getEStructuralFeature(valu);

		List<IDiagraphReferenceAssociation> conts = parseContainementReferenceAssociationAorC_(targetReference);

		if (arg != null) // FP150512transp
			for (IDiagraphReferenceAssociation cont : conts) {
				cont.setArgument(arg);
			}

		if (DParams.Parser_29_LOG) {
			String log = "";
			for (IDiagraphReferenceAssociation raf : conts) {
				log += raf.toId() + ", ";
			}
			clog15("conts=" + (log.isEmpty() ? "empty !" : log));
		}

		return conts;
	}

	private List<IDiagraphReferenceAssociation> parseCReferences_new_(
			EClass eclaz, String annotationValue, String annotationArgument_) {

		if (annotationValue == null)
			throw new RuntimeException(
					"target reference should not be null for "
							+ eclaz.getName() + "." + annotationValue); // FP150427

		List<IDiagraphReferenceAssociation> assocabs = parseContainmentReferenceIfAbstract_(
				eclaz, annotationValue);

		List<IDiagraphReferenceAssociation> assocs_ = parseContainmentReferences__(
				eclaz, annotationValue);

		for (IDiagraphReferenceAssociation assocab : assocabs) { // FP150516y
			if (!assocs_.contains(assocab))
				assocs_.add(assocab);
		}

		if (DParams.Parser_CONTAINMENT2_LOG)
			if (assocs_.size() > 1) {
				clog2("multiple contaiment for containment " + annotationValue);
				for (IDiagraphReferenceAssociation cont : assocs_) {
					clog2(cont.toString());
					clog2("------");
				}
				clog2("------");
				// clog2("res=" + raf.toString());
			}

		for (IDiagraphReferenceAssociation cont : assocs_) {
			clog2(cont.toString());
			clog2("------");
			EClass concrtarg = cont.getTarget();// FP150331a304
			IDiagraphNode nod_ = getCrefNode(cont, eclaz, annotationValue,
					concrtarg, annotationArgument_);
		}
		return assocs_;
	}

	@SuppressWarnings("unused")
	private List<IDiagraphReferenceAssociation> parseKReferences_new_(
			// FP150512transp
			EClass eclaz, String annotationValue_, String annotationArgument,
			String label) {

		if (annotationValue_ == null)
			throw new RuntimeException(
					"target reference should not be null for "
							+ eclaz.getName() + "." + annotationValue_); // FP150427

		List<IDiagraphReferenceAssociation> assocabs = parseContainmentReferenceIfAbstract_(
				eclaz, annotationValue_);

		List<IDiagraphReferenceAssociation> assocs = parseContainmentReferences__(
				eclaz, annotationValue_);
		for (IDiagraphReferenceAssociation assocab : assocabs) {
			if (!assocs.contains(assocab))
				assocs.add(assocab); // FP150516y
		}

		if (DParams.Parser_CONTAINMENT2_LOG)
			if (assocs.size() > 1) {
				clog2("multiple contaiment for kref " + annotationValue_);
				for (IDiagraphReferenceAssociation cont : assocs) {
					clog2(cont.toString());
					clog2("------");
				}
				clog2("------");
				// clog2("res=" + raf.toString());
			}

		for (IDiagraphReferenceAssociation cont : assocs) {
			clog2(cont.toString());
			clog2("------");
			EClass targ = cont.getTarget();// FP150331a304
			IDiagraphNode nod_ = getKrefNode(cont, eclaz, annotationValue_,
					targ, label);
		}
		return assocs;
	}

	private List getNodeReferenceRealizations__old_nu(EClass target) {
		;

		boolean found_ = false;
		List result = referenceRealizations.get(target);
		if (result != null)
			return result;
		else {

			result = new ArrayList();
			for (EReference ref : getNodeRefsToNodeOrGeneric(target, true)) {
				EClass refcont = (EClass) ref.eContainer();
				EClass reftyp_ = (EClass) ref.getEType();

				if (refcont.getName().equals("Member"))
					if (reftyp_.getName().equals("Post"))
						found_ = true; // FP150520voir

				List<EClass> contclasses = new ArrayList<EClass>();
				contclasses.add(refcont);
				contclasses.addAll(getOtherNodeOrGenericClasses(refcont));
				List<EClass> targetClasses = new ArrayList<EClass>();
				targetClasses.add(reftyp_);
				targetClasses.addAll(getOtherNodeOrGenericClasses(reftyp_));
				if (LOG) {
					String logg = "";
					for (EClass typClass : targetClasses) {
						logg += typClass.getName() + ";";
					}
					clog(logg);
				}

				for (EClass contclass : contclasses) {
					if (!contclass.isAbstract()
							&& refcont.isSuperTypeOf(contclass))
						for (EClass typclass : targetClasses) {
							if (!typclass.isAbstract()
									&& reftyp_.isSuperTypeOf(typclass)) {
								Object[] real = new Object[3];
								real[0] = contclass;
								real[1] = ref;
								real[2] = typclass;
								if (found_)
									System.out.println(contclass.getName()
											+ "." + ref.getName() + "."
											+ typclass.getName());
								result.add(real);
							}
						}
				}
			}
			referenceRealizations.put(target, result);
			return result;
		}
	}

	private List getNodeReferenceRealizations_new(EClass target) {

		List result = referenceRealizations.get(target);
		if (result != null)
			return result;
		else {

			result = new ArrayList();
			for (EReference ref : getNodeRefsToNodeOrGeneric(target, true)) {
				EClass refcont = (EClass) ref.eContainer();
				EClass reftyp = (EClass) ref.getEType();


				List<EClass> contclasses = new ArrayList<EClass>();
				contclasses.add(refcont);
				contclasses.addAll(getOtherNodeOrGenericClasses(refcont));
				List<EClass> targetClasses = new ArrayList<EClass>();
				targetClasses.add(target);
				targetClasses.addAll(getOtherSubNodeOrGenericClasses(target));
				if (LOG) {
					String logg = "";
					for (EClass typClass : targetClasses) {
						logg += typClass.getName() + ";";
					}
					clog(logg);
				}

				for (EClass contclass : contclasses) {
					if (!contclass.isAbstract()
							&& refcont.isSuperTypeOf(contclass))
						for (EClass typclass : targetClasses) {
							if (!typclass.isAbstract()
									&& reftyp.isSuperTypeOf(typclass)) {
								Object[] real = new Object[3];
								real[0] = contclass;
								real[1] = ref;
								real[2] = typclass;

								result.add(real);
							}
						}
				}
			}
			referenceRealizations.put(target, result);
			return result;
		}
	}

	private List<EClass> getOtherSubNodeOrGenericClasses(EClass c) {
		String view = this.getCurrentView();
		List<EAnnotation> anots = c.getEAnnotations();
		for (EAnnotation anot : anots)
			if (isView(view, anot))
				return getOtherSubNodeOrGenericClasses(anot, view);
		return new ArrayList<EClass>();
	}



	// //////////////////// nu ///////////////

	/*
	 * private IDiagraphReferenceAssociation parseCReference_new_nu(EClass
	 * eclaz, String value, String[] args) {// FP150317avoir = true; //
	 * FP150505voir String viewid = notation.getCurrentView().getViewId(); if
	 * (LOG) {
	 *
	 * if ("State".equals(eclaz.getName())) tb = true; if
	 * ("Region".equals(eclaz.getName())) tb = true;
	 *
	 * clog("parseCReference " + viewid + "." + eclaz.getName()); } // String
	 * targetValue = getTagValues(eclaz, "cref").get(0); // // FP150317aaa
	 *
	 * EReference targetReference = (EReference) eclaz
	 * .getEStructuralFeature(value);
	 *
	 * IDiagraphReferenceAssociation referenceAssociation = null;
	 * List<IDiagraphReferenceAssociation> conts =
	 * parseContainementReferenceAssociationAorC_(targetReference); for
	 * (IDiagraphReferenceAssociation raf : conts) {
	 *
	 * EReference targ = raf.getTargetReference(); if
	 * (targ.getName().equals(value) && isContainer(targ, eclaz, true)) {//
	 * FP150316q EClass ixrc = raf.getInstanceSource();// && //
	 * raf.getInstanceSource()==eclaz
	 *
	 * if (ixrc == eclaz) referenceAssociation = raf; // FP150316az // break; }
	 * }
	 *
	 *
	 *
	 * boolean propagated_false = false;
	 *
	 * // FP150317a IDiagraphNode nod_ = getNodeWithCReferenc_e(eclaz, value,
	 * eclaz, args[0], propagated_false); // , //
	 * FIGURE_EMBEDDED_ARGUMENT.equals(args[1]), // false // boolean
	 * canva=nod_.isCanvas(); List<IDiagraphReferenceAssociation> cnts =
	 * nod_.getContainments(); // FP150316zz
	 *
	 * // FP150316az
	 *
	 * boolean izcanvas = nod_.isCanvas(); // if ((!nod_.isCanvas()) &&
	 * nod_.getContainments().size() == 0) // //FP140701cn // FP130107b // throw
	 * new RuntimeException("(4)syntax error on " + nod_.getName() // +
	 * " check if there is  a [node] annotation !!!!");
	 *
	 * return referenceAssociation;
	 *
	 * }
	 *
	 * private List<IDiagraphReferenceAssociation>
	 * doParseContainmentAssociationsAndSubHierTo_old_0( EClass eclaz) { //
	 * FP150329// FP150318zzz
	 *
	 * AssociationCandidateType caz = AssociationCandidateType.CAND_CONTAINMENT;
	 * List<IDiagraphReferenceAssociation> l_ = null;//
	 * getCurrentPointOfView().findReferenceAssociationsByTargetName
	 * (eclaz.getName(), // caz); //FP150516x
	 * List<IDiagraphReferenceAssociation> notabstracts = new
	 * ArrayList<IDiagraphReferenceAssociation>(); // FP150518azer if ((l_ ==
	 * null || l_.isEmpty())) { // FP150326 cond ??? l_ =
	 * doParseContainmentAssociationsAndSubHierTo2t_old_0(eclaz); // l_ =
	 * doParseContainmentAssociationsAndSubHierTo2t_ec_1(eclaz); for
	 * (IDiagraphReferenceAssociation ass : l_) if
	 * (!ass.getInstanceSource().isAbstract()) notabstracts.add(ass); // l =
	 * parseContainmentAssocsAndSubHierTo_(eclaz); if (!notabstracts.isEmpty())
	 * getCurrentPointOfView().addReferenceAssociations( eclaz.getName(),
	 * notabstracts); for (IDiagraphReferenceAssociation raf : notabstracts) {
	 * if (!raf.contains(caz)) { raf.addCase(caz); if (DParams.Parser_15_LOG)
	 * clog15("1wwww" + raf.toString()); } } }
	 *
	 * return notabstracts; }
	 *
	 * private List<EClass> allSubElements_nu(EAnnotation annotation, String
	 * view) { // FP150519 // mofif EClass claz = (EClass)
	 * annotation.getEModelElement(); List<EClass> nodclasses =
	 * getOtherNodeClasses(annotation, view); List<EClass> linkclasses =
	 * getOtherLinkClasses(annotation, view); List<EClass> inhclasses = new
	 * ArrayList<EClass>(); for (EClass eClass : linkclasses) if (claz != eClass
	 * && claz.isSuperTypeOf(eClass)) inhclasses.add(eClass); for (EClass eClass
	 * : nodclasses) if (claz != eClass && claz.isSuperTypeOf(eClass))
	 * inhclasses.add(eClass); // return nodclasses.isEmpty() &&
	 * !inhLinkclasses.isEmpty(); return inhclasses;// .isEmpty(); }
	 *
	 * private int dynamicGuessNodeOrLink_nu(EAnnotation annotation, String
	 * view) { // FP150508 int result = 3; if (!allSubNodesClasses(annotation,
	 * view).isEmpty()) result = 1; else if (!allSubLinkClasses(annotation,
	 * view).isEmpty()) result = 2; return result; }
	 *
	 * // FP140421 // @Override private List<IDiagraphReferenceAssociation>
	 * guessContainments_nu( EClass eclaz) {
	 *
	 * IDiagraphNode diagraphNode = getDiagraphNode(eclaz, 0);// FP140505 if
	 * (LOG) {
	 *
	 * if (!diagraphNode.getAllContainments().isEmpty()) {
	 *
	 * clog("(B) containments for " + diagraphNode.getName() + "{"); for
	 * (IDiagraphReferenceAssociation cont : diagraphNode .getAllContainments())
	 * { clog(cont.toString()); } clog("}(containments_b for " +
	 * diagraphNode.getName() + ")"); } else clog("(B) no containments for " +
	 * diagraphNode.getName());
	 *
	 * } return diagraphNode.getAllContainments(); }
	 *
	 * private IDiagraphReferenceAssociation parseCReference_new_nu_(EClass
	 * eclaz, String value, String[] args) {// FP150317avoir = true; //
	 * FP150505voirvoir String viewid = notation.getCurrentView().getViewId();
	 * EReference targetReference = (EReference) eclaz
	 * .getEStructuralFeature(value); IDiagraphReferenceAssociation
	 * referenceAssociation = null; List<IDiagraphReferenceAssociation> conts_ =
	 * parseContainementReferenceAssociationAorC_(targetReference); for
	 * (IDiagraphReferenceAssociation raf : conts_) { EReference targ =
	 * raf.getTargetReference(); if (targ.getName().equals(value) &&
	 * isContainer(targ, eclaz, true)) { EClass ixrc = raf.getInstanceSource();
	 * if (ixrc == eclaz) referenceAssociation = raf; // FP150316az } }
	 *
	 * boolean propagated_false = false; IDiagraphNode nod_ =
	 * getNodeWithCReferenc_e(eclaz, value, eclaz, args[0], propagated_false);
	 * // , List<IDiagraphReferenceAssociation> cnts = nod_.getContainments();
	 * // FP150316zz boolean izcanvas = nod_.isCanvas(); return
	 * referenceAssociation; }
	 *
	 *
	 * public void parse22LinkAnnotations_old_nu( List<EModelElement>
	 * modelElements, IProgressMonitor progressMonitor) { // FP140505ppp
	 *
	 * = true; try { for (EModelElement element_ : modelElements) { // EClass
	 * eclaz1 = null; EClass eclaz2 = null; EReference ref = null; if (element_
	 * instanceof EReference) { ref = (EReference) element_; eclaz2 = (EClass)
	 * ref.getEType(); if (LOG) clog("parsing EReference:" + ref.getName() +
	 * "->" + eclaz2.getName()); } try { if (eclaz2 != null &&
	 * isAssociation(eclaz2)) { IDiagraphAssociation assoc =
	 * getDiagraphAssociation_(eclaz2); }
	 *
	 * } catch (Exception e) { cerror("(22) error while parsing"); }
	 *
	 * } } catch (RuntimeException e) { // FP131004 to remove throw e; }
	 *
	 * }
	 *
	 * private boolean inferNodeContainmentAlt_old_nu(IDiagraphNode node,
	 * List<EReference> containements) { try { EReference eref = null;
	 * List<EReference> stp = multipleContentionThroughInheritancePattern(node
	 * .getEClass()); // superContainmentPattern(node.getEClass()); if (stp ==
	 * null) return false; for (EReference eReference : stp) if (eReference !=
	 * containements.get(0)) eref = eReference;
	 * stp.remove(containements.get(0)); EReference r0 = containements.get(0);
	 * EReference r1 = eref; EClass cl0 = r0.getEContainingClass(); EClass cl1 =
	 * r1.getEContainingClass(); IDiagraphNode dn0 = findNode_(cl0);
	 * IDiagraphNode dn1 = findNode_(cl1); if (dn0.isCanvas())
	 * inferNodeContainments(node, r0, r1); else if (dn1.isCanvas())
	 * inferNodeContainments(node, r1, r0); else {
	 * cerror("should not happen in inferContainmentAlt"); return false; } }
	 * catch (Exception e) { cerror("(3)err while inferContainmentAlt"); return
	 * false; } return true; }
	 *
	 * public List<EClass> getConcreteSubLinks_nu(EClass claz) { List<EClass>
	 * result = new ArrayList<EClass>(); List<EClass> subclasses =
	 * subClasses(claz); for (EClass subClass : subclasses) { if
	 * (!subClass.isAbstract() && isLink(subClass)) result.add(subClass); }
	 * return result; }
	 *
	 *
	 * // key.equals(CREFERENCE) private IDiagraphNode
	 * getNodeWithCReference_0_nu(EClass claz, String target_, String label,
	 * boolean propagated) { //
	 *
	 * IDiagraphReferenceAssociation createdContainment = null; if (label ==
	 * null) label = target_;
	 *
	 * checkKReferenceCorrectness((EClass) claz, target_); // FP131009
	 * EStructuralFeature esf = ((EClass) claz).getEStructuralFeature(target_);
	 *
	 * IDiagraphNode nod = getDiagraphNode(claz, 0);// TotoSet IDiagraphNode
	 * containernode = findNode_((EClass) ((EReference) esf) .eContainer());
	 * IDiagraphAssociation exists = nod.findContainmentByTargetName(target_);
	 * // FP140202see2 if (DParams.Parser_CONTAINMENT2_LOG) clog2("GN_CREF(3) "
	 * + ((EClass) claz).getName() + " propagated " + (propagated ? " ypropa " :
	 * " npropa ") + target_ + (exists == null ? " cont not exixts " :
	 * " cont exists "));
	 *
	 * if (exists == null) {
	 *
	 * if (LOG) clog("GNWCR createNodeContainment " + containernode.getName() +
	 * "-" + nod.getName() + "-" + target_ + "-" + label); createdContainment =
	 * createNodeContainment_(claz, target_, label, false, propagated, true,
	 * false); nod.setCreatedContainment(createdContainment);
	 *
	 * } else if (LOG) clog("GNWCR existsNodeContainment " +
	 * containernode.getName() + "-" + nod.getName() + "-" + target_ + "-" +
	 * label); return nod; }
	 *
	 *
	 *
	 *
	 * private String getTagValue_nu(EClass eclaz, String key) { if
	 * (!isNode(eclaz) && !!isGeneric(eclaz)) throw new
	 * RuntimeException("getTagValue must apply on a node"); for (EAnnotation
	 * eAnnotation : eclaz.getEAnnotations()) if (isView(eAnnotation)) for
	 * (Map.Entry<String, String> entry : eAnnotation.getDetails()) if
	 * (entry.getKey().startsWith(key + "=")) return
	 * entry.getKey().split("=")[1]; return null; }
	 *
	 * public List<EClass> getConcreteSubNodes_nu(EClass claz) { List<EClass>
	 * result = new ArrayList<EClass>(); List<EClass> subclasses =
	 * subClasses(claz); for (EClass subClass : subclasses) { if
	 * (!subClass.isAbstract() && isNode(subClass)) result.add(subClass); }
	 * return result; }
	 *
	 *
	 *
	 * private List<List<IDiagraphReferenceAssociation>>
	 * parseContainmentAssocsAndSubHierTo_new_nu( EClass target, boolean
	 * abztract) {
	 *
	 * boolean derived_no = false; List contrefs_ =
	 * getReferenceRealizations(target);
	 * List<List<IDiagraphReferenceAssociation>> result = new
	 * ArrayList<List<IDiagraphReferenceAssociation>>(); // FP140330 if
	 * (contrefs_ != null && !contrefs_.isEmpty()) for (Object cnrf_ :
	 * contrefs_) { Object[] r_ = (Object[]) cnrf_; EClass refcont = (EClass)
	 * r_[0]; EReference ref_ = (EReference) r_[1]; EClass reftyp = (EClass)
	 * r_[2]; if (ref_.isContainment()) { List<IDiagraphReferenceAssociation>
	 * assocs = new ArrayList<IDiagraphReferenceAssociation>(); EClass
	 * sourcecontner = refcont;// (EClass) // ref_.eContainer();
	 *
	 * if (abztract || !toAbstract(ref_, target)) {
	 *
	 * IDiagraphReferenceAssociation dir = parseReferenceAssociation_(
	 * sourcecontner, sourcecontner, reftyp, ref_, derived_no); if
	 * (!assocs.contains(dir)) { assocs.add(dir); if (DParams.Parser_15_LOG) {
	 * if (dir.isTargetAbstract()) clog2("target abstract for :" + dir.toLog());
	 * logBuffer += dir.toLog() + '\n'; } } for (IDiagraphReferenceAssociation
	 * sub : createReferenceSubContainingAssociations( sourcecontner, target,
	 * ref_)) if (!assocs.contains(sub)) { assocs.add(sub); if
	 * (DParams.Parser_15_LOG) logBuffer += "sub contaiment assoc:" +
	 * sub.toLog() + '\n'; } result.add(assocs); } }
	 *
	 * } if (LOG && report_ != null && report_.isEmpty()) report_ = "empty";
	 * return result; }
	 *
	 * private List<IDiagraphReferenceAssociation>
	 * doParseContainmentAssociationsAndSubHierTo2t_old_0( EClass eclaz) {//
	 * boolean abztract, if (LOG) clog("parseContainmentAssociationsTo " +
	 * eclaz.getName() + "." + notation.getCurrentView().getViewId() + " [");
	 * boolean abztract = true; List<IDiagraphReferenceAssociation> result = new
	 * ArrayList<IDiagraphReferenceAssociation>();
	 * List<List<IDiagraphReferenceAssociation>> conts =
	 * parseContainmentAssocsAndSubHierTo_old_0( eclaz, abztract); for
	 * (List<IDiagraphReferenceAssociation> assocs : conts) for
	 * (IDiagraphReferenceAssociation ass : assocs) if (!result.contains(ass)) {
	 * if (!ass.getInstanceSource().isAbstract()) { result.add(ass); if (LOG)
	 * clog(ass.toLogCompl() + "." + ass.hashCode()); } } if (LOG) clog("]");
	 * return result; }
	 */
}
