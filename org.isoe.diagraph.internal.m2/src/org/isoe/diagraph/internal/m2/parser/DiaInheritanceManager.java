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
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.isoe.diagraph.parser.api.IDiagraphParser;//.ContainmentType.ContainmentTypeEx;
import org.isoe.diagraph.internal.m2.DiaContainedElement;
import org.isoe.diagraph.internal.m2.DiaGenericElement;
import org.isoe.diagraph.internal.m2.DiaNode;
import org.isoe.diagraph.internal.m2.DiaReference;
import org.isoe.diagraph.internal.m2.api.IDiaContainedElement;
import org.isoe.diagraph.internal.m2.api.IDiaContainment;
import org.isoe.diagraph.internal.m2.api.IDiaParser;
import org.isoe.diagraph.parser.api.IDiagraphAssociation;
import org.isoe.diagraph.parser.api.IDiagraphNotation;
import org.isoe.diagraph.parser.api.IDiagraphView;
import org.isoe.diagraph.parser.api.IDiagraphNode;
import org.isoe.fwk.core.DParams;

/**
 *
 * @author pfister
 *
 */
public class DiaInheritanceManager implements IDiaInheritance {
	public static final boolean DEFAULT_EMBEDDED_CONTAINMENT = true;
	// final static boolean DIRECT_CONTAINEMENT = true;
	// final static boolean INHERITED_CONTAINEMEN = false;
	private static final boolean LOG = DParams.DiaInheritance_LOG;
	private IDiaParser parser;
	ContainmentPattern containmentPattern;
	private List<DiaContainedElement> diaContainedElementsForContainmentInference;
	private IDiagraphNotation notation;

	// private IDiagraphView grammar;

	public DiaInheritanceManager(IDiaParser parser, IDiagraphNotation notation) {
		super();
		this.parser = parser;
		// this.grammar = grammar;
		this.notation = notation;
		containmentPattern = new ContainmentPattern(parser, notation);
	}

	/*
	 * EReference er = anode.getContainerReference(); for (DiaNode subnode :
	 * anode.getSubNodes()) { EClass subclass = (EClass)
	 * subnode.getEModelElement(); String cname = er.getName() +
	 * subclass.getName(); if (LOG) clog("deriv cont: " + arsrc.getName() + "."
	 * + cname + "." + subnode.getName()); DiaContainment derived =
	 * arsrc.getContainment(subnode, er, cname, false, false); if (LOG)
	 * clog(derived.getSourceNode().getName() + "-*-" + derived.getName() +
	 * "-*-" + derived.getTargetNode().getName()); }
	 */

	private void clog2(String mesg) {
		if (DParams.Parser_CONTAINMENT2_LOG)
			System.out.println(mesg);
	}

	private void logSubNodes(DiaNode anode, List<DiaNode> subnodes) {
		if (!subnodes.isEmpty()) {
			String subs = "";
			for (DiaNode subnode : subnodes)
				subs += subnode.getName() + " ";
			clog2((anode.isAbstract() ? "abstract " : "concrete ")
					+ anode.getName() + " has " + subnodes.size()
					+ " subnodes: " + subs);
		} else
			clog2((anode.isAbstract() ? "abstract " : "concrete ")
					+ anode.getName() + " has  no  subnodes ");
		for (DiaNode subnode : subnodes)
			clog2((anode.isAbstract() ? "abstract " : "concrete ") + "node: "
					+ anode.getName() + " <-- "
					+ (subnode.isAbstract() ? "abstract " : "concrete ")
					+ "subnode: " + subnode.getName());
	}

	@Override
	public void logNodesAndSubnodes() { // FP140124
		if (!DParams.Parser_CONTAINMENT2_LOG)
			return;
		clog2("----SubNodes");
		for (DiaNode anode : parser.getAllNodes())
			logSubNodes(anode, anode.getSubNodes1());
		/*
		 * clog2("----SubNodes2"); for (DiaNode anode : parser.getAllNodes())
		 * logSubNodes(anode, anode.getSubNodes1());
		 */
	}

	// @Override
	private boolean compareSubnodes_nu() { // FP140124
		/*
		 * try { for (DiaNode anode : parser.getAllNodes()) { List<DiaNode> sn1
		 * = anode.getSubNodes1(); List<DiaNode> sn2 = anode.getSubNodes2(); if
		 * (sn1.size() != sn1.size()) return false; for (int i = 0; i <
		 * sn1.size(); i++) { if (sn1.get(i) != sn2.get(i)) return false; } } }
		 * catch (Exception e) { return false; }
		 */
		return true;
	}

	private IDiaContainment createContainement_(
			IDiagraphAssociation.AssociationType containmentType, DiaNode src,
			DiaNode trgConcrete, EReference tgRef, boolean derived_) {
		// DiaContainment result = null;
		//String cname_ = tgRef.getName() + "-"+trgConcrete.getName(); //FP150516azer
		String cname_ = tgRef.getName()+trgConcrete.getName(); //FP150516azer

		if (DParams.Parser_CONTAINMENT2_LOG)
			clog2((derived_ ? "deriv " : "not_deriv ")
					+ notation.getParser().ctToString(containmentType) + " "
					+ src.getName() + "." + cname_ + "-c->"
					+ trgConcrete.getName() + "(c)");
		IDiaContainment result = src.getContainment_(containmentType,
				trgConcrete, tgRef, tgRef.getName(), false, derived_); //FP150516azer

		if (DParams.Parser_CONTAINMENT2_LOG && result != null)
			clog2(result.getSourceNode().getName() + "-*-" + result.getName()
					+ "-*-" + result.getTargetNode().getName());
		return result;
	}

	private String logNode(DiaNode anode_) {

		try {
		IDiaContainment cont = anode_.getContainer();
		return ("   DACTN: " + (anode_.isAbstract() ? "Abstr " : "Concr ")
				+ (cont != null ? "YK " : "NK ")
				+ anode_.getName() + "-" + ((cont == null ? "~"
				: ("[["
						+ (cont.getName()
								+ "-"
								+ cont.getSourceNode()
										.getName() + "-" + (cont.getSourceNode().isAbstract() ? "KSA"
									: "KSC")) + "]]")

		)));
		} catch (Exception e) {
			return null;
		}
	}

	private void handleremoves(DiaNode anode, List<IDiaContainment> toremove) {
		IDiaContainment contner = anode.getContainer();
		if (!toremove.contains(contner)) {
			toremove.add(contner);
			if (LOG)
				clog("remove: " + contner.getName());
		}
	}

	private void logAssociations(List<IDiagraphAssociation> assocs_,
			List<IDiagraphNode> nodes) {
		clog2("-----associations");
		try {

			// clog2("createDerivedContainments");

			for (IDiagraphAssociation assoc : assocs_) {
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

	private List<IDiaContainment> createDerivedContainments(
			List<DiaNode> subnodes_,
			IDiagraphAssociation.AssociationType containmentType, DiaNode src,
			DiaNode trg) {
		boolean check;
		;
		if (DParams.Parser_CONTAINMENT2_LOG)
			clog2("createDerivedContainments(2): " + "(" + subnodes_.size()
					+ ") " + notation.getParser().ctToString(containmentType)
					+ " " + src.getName() + "-c->" + trg.getName()
					+ "(a)");





		List<IDiagraphAssociation> assocs_ = notation
				.getView_(parser.getView()).getAssocs();
		List<IDiagraphNode> nodes = notation.getView_(parser.getView())
				.getNodes();
		if (DParams.Parser_CONTAINMENT2_LOG)
			logAssociations(assocs_, nodes);
		List<IDiaContainment> result = new ArrayList<IDiaContainment>();
		for (DiaNode trgConcrete : subnodes_) {
			EReference contref = trg.getContainerReference();
			IDiaContainment dc = null;
			if (contref == null) {
				EClass sr = trg.getSemanticRole();
				List<EReference> refs = sr.getEAllReferences();
				for (EReference eReference : refs) {
					EClass rc = (EClass) eReference.eContainer();
					if (rc == src.getSemanticRole()
							|| rc.isSuperTypeOf(src.getSemanticRole()))
						contref = eReference;
				}
			}
			if (contref != null)
				dc = createContainement_(containmentType, src, trgConcrete,
						contref, true);
			else
				check = true; // FP140109bbb
			if (dc != null)
				result.add(dc);
			else
				check = true;
		}
		return result;
	}

	private void createDerivedSubNodesAndContainments(List<DiaNode> subnodes,
			DiaNode anode) {
		IDiaContainment contner = anode.getContainer();
		if (contner == null) {
			if (DParams.Parser_CONTAINMENT2_LOG)
				clog2("contner is null ");
		} else {
			DiaNode contnersrc = (DiaNode) contner.getSourceNode();
			if (contnersrc.isAbstract()) {
				parser.cerror("TODO derivateAbstractContainements when both abstract: "
						+ contnersrc.getName() + " -> " + anode.getName());
				return;
			}
			if (DParams.Parser_CONTAINMENT2_LOG)
				clog2("before: " + logNode(anode));
			anode.addDerivedSubNodes(subnodes);
			IDiagraphAssociation.AssociationType containmentType = IDiagraphAssociation.AssociationType.VOID_;
			if (!contner.isCompartment() && !contner.isPort())
				containmentType = IDiagraphAssociation.AssociationType.SHARED_COMPARTMENT_; // FP150512////
																							// //
																							// CREF;
			else if (contner.isCompartment())
				if (!((DiaNode) contner.getSourceNode()).isOrSubNodeCanvas()) // FP140201
					containmentType = IDiagraphAssociation.AssociationType.TYPED_COMPARTMENT;// Ex.KREF;
				else
					containmentType = IDiagraphAssociation.AssociationType.SHARED_COMPARTMENT_; // FP150512//
																								// //
																								// CREF;
			else if (contner.isPort())
				containmentType = IDiagraphAssociation.AssociationType.AT_PORT;// AFX;
			createDerivedContainments(subnodes, containmentType, contnersrc,
					anode);
			if (DParams.Parser_CONTAINMENT2_LOG)
				clog2("after: " + logNode(anode));
		}
	}

	@Override
	public void derivateContainements() { // FP140124

		if (!DParams.PARSER_PROPAGATE_INHERITED_CONTAINMENTS_)
			return;
		if (DParams.Parser_CONTAINMENT2_LOG)
			clog2("--- derivateContainements ---");
		List<DiaNode> allnodes = parser.getAllNodes();
		if (DParams.Parser_CONTAINMENT2_LOG) {
			clog2("aa{");
			for (DiaNode anode : allnodes)
				clog(logNode(anode));
			clog2("}aa");
		}
		for (DiaNode anode : allnodes) {
			if (DParams.Parser_CONTAINMENT2_LOG)
				clog2("derived for " + anode.getName() + " ?");
			List<DiaNode> subnodes = anode.getSubNodes1();
			if (DParams.Parser_CONTAINMENT2_LOG) {
				if (!subnodes.isEmpty()) {
					String subs = "";
					for (DiaNode subnode : subnodes)
						subs += subnode.getName() + " ";
					clog2("has " + subnodes.size() + " subnodes: " + subs);
				} else
					clog2("has no  subnodes ");
			}
			for (DiaNode subnode : subnodes) {
				if (DParams.Parser_CONTAINMENT2_LOG)
					clog2("\nderivateAbstractContainementss(1) for  "
							+ subnode.getName() + " ?");
				createDerivedSubNodesAndContainments(subnodes, subnode);
			}
		}
	}

	public void derivateAbstractContainements__() { // FP140124

		if (!DParams.PARSER_PROPAGATE_INHERITED_CONTAINMENTS_)
			return;
		if (DParams.Parser_CONTAINMENT2_LOG)
			clog2("--- derivateAbstractContainements ---");
		// List<IDiaContainment> toremove = new ArrayList<IDiaContainment>();
		List<DiaNode> allnodes = parser.getAllNodes();
		if (DParams.Parser_CONTAINMENT2_LOG) {

			clog2("aa{");
			for (DiaNode anode : allnodes)
				clog(logNode(anode));
			clog2("}aa");
		}
		for (DiaNode anode : allnodes) {// formaliser en ocl ??
			if (DParams.Parser_CONTAINMENT2_LOG)
				clog2("derived for " + anode.getName() + " ?");
			List<DiaNode> subnodes = anode.getSubNodes1();

			if (DParams.Parser_CONTAINMENT2_LOG) {
				if (!subnodes.isEmpty()) {
					String subs = "";
					for (DiaNode subnode : subnodes)
						subs += subnode.getName() + " ";
					clog2("has " + subnodes.size() + " subnodes: " + subs);
				} else
					clog2("has no  subnodes ");
			}
			if (anode.isAbstract()) {
				if (DParams.Parser_CONTAINMENT2_LOG)
					clog2("node.isAbstract " + anode.getName());
				for (DiaNode subnode : subnodes) {
					if (subnode.isAbstract()) {
						if (DParams.Parser_CONTAINMENT2_LOG)
							clog2("\nderivateAbstractContainementss(1) for abstract "
									+ subnode.getName() + " ?");
						if (LOG)
							clog("DACTN: AC " + logNode(subnode));

						createDerivedSubNodesAndContainments(subnodes, subnode);
					} else {
						if (DParams.Parser_CONTAINMENT2_LOG)
							clog2("subnode " + subnode.getName()
									+ " is not abstract");
					}
				}
			} else {
				if (DParams.Parser_CONTAINMENT2_LOG)
					clog2("no derived for " + anode.getName() + " !");
				if (LOG)
					clog("CDCTN: NAC " + logNode(anode));
			}
		}

		if (LOG) {
			clog("b{");
			for (DiaNode anode : allnodes)
				clog(logNode(anode));
			clog("}b");
		}
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	private void setTargetNode(DiaNode node, IDiaContainment original,
			IDiaContainment cloned) {
		try {
			cloned.setTargetNode((DiaNode) parser
					.findDiagramElement(((EClass) node.getEModelElement())
							.getEStructuralFeature(original.getNameIfCompartment())
							.getEType()));

		} catch (Exception e) {
			throw new RuntimeException(
					"("
							+ node.getName()
							+ "."
							+ original.getName()
							+ ") unable(1) to set target node for cloned containment !!!");
		}
	}

	private void setTargetNode(DiaNode node, DiaReference original,
			DiaReference cloned) { // FP150516
		try {
			cloned.setTargetNode((DiaNode) parser
					.findDiagramElement(((EClass) node.getEModelElement())
							.getEStructuralFeature(original.getTargetName())
							.getEType()));

		} catch (Exception e) {
			throw new RuntimeException("(" + node.getName() + "."
					+ original.getName()
					+ ") unable(1) to set target node for cloned reference !!!");
		}
	}

	private boolean isTargetAbstract(IDiaContainment containment) {
		EReference trg = null;
		if (containment.getTargetReference() != null)
			trg = containment.getTargetReference();
		if (trg == null && containment.getNameIfCompartment() != null)
			trg = (EReference) (containment.getSourceNode().getSemanticRole())
					.getEStructuralFeature(containment.getNameIfCompartment());
		if (trg != null && ((EClass) trg.getEType()).isAbstract())
			return true;
		return false;
	}

	private void propagateInheritedReference(DiaNode node, DiaNode superNode) { // FP150516voir1
		List<DiaReference> references = superNode.getReferences();
		for (DiaReference reference : references) {

			if (reference.getName().equals("azing")) {
				if (LOG)
					clog(reference.getSourceNode().getName() + "->"
							+ reference.getName());
			}

			String clonedReferenceName = reference.getName();// +
																// node.getName();//FP140202see
			if (LOG) {
				clog(clonedReferenceName + " n: " + node.getName() + " d: "
						+ node.getDepth());
			}
			DiaNode dn = null;
			DiaNode dnd = (DiaNode) reference.getSourceNode();
			boolean propagated = true; // FP140409
			parser.getNodeWithReference_((EClass) node.getEModelElement(),
					clonedReferenceName, null);
			/*
			 * dn = parser.getNodeWithCReference( (EClass)
			 * node.getEModelElement(),// containment.getTargetName(),
			 *
			 * clonedReferenceName, (EClass) node.getEModelElement(),//
			 * FP150331a305 false, propagated); // FP120920
			 */

			List<DiaReference> cloneds = parser.getCreatedReferences();
			for (DiaReference cloned : cloneds) {
				DiaNode originalTargetNode = (DiaNode) reference
						.getTargetNode();
				if (originalTargetNode != null)
					cloned.setTargetNode(originalTargetNode); // FP140202xxxaaa
				else
					setTargetNode(node, reference, cloned);
				if (LOG)
					clog("ClonedReference: " + cloned.getName());
			}
		}

	}

	private void logInheritedContainment(DiaNode node, DiaNode superNode) {

		List<IDiaContainment> containments = superNode.getContainments();// FP150318t
		for (IDiaContainment containment : containments) {
			clog(containment.getSourceNode().getName() + "->"
					+ containment.getName() + "(" + containment.getNameIfShared()
					+ ")");

			String clonedContainmentName = containment.getName();
			clog(clonedContainmentName + " n: " + node.getName() + " d: "
					+ node.getDepth());


		}
	}

	private void propagateInheritedContainment(DiaNode node, DiaNode superNode) {
		List<IDiaContainment> containments = superNode.getContainments();// FP150318t
		for (IDiaContainment containment : containments) {
			if (LOG)
				clog(containment.getSourceNode().getName() + "->"
						+ containment.getName() + "("
						+ containment.getNameIfShared()

						+ ")");

			String clonedContainmentName = containment.getName();// +
																	// node.getName();//FP140202see
			if (LOG) {
				clog(clonedContainmentName + " n: " + node.getName() + " d: "
						+ node.getDepth());
			}
			DiaNode dn = null;
			DiaNode dnd = (DiaNode) containment.getSourceNode();
			boolean propagated = true; // FP140409

			if (containment.isCompartment()) {
				if (!dnd.isOrSubNodeCanvas() || node.getDepth() > 0) {
					// FP150503
																		// compartment
																		// level
					dn = parser.getNodeWithKReference(node.getEModelElement(),
							containment.getNameIfCompartment(),
							containment.getArgument(),
							// null,
							clonedContainmentName,
							(EClass) node.getEModelElement(),// FP150331a305
							DEFAULT_EMBEDDED_CONTAINMENT, propagated); // FP120920
				} else {

					dn = parser.getNodeWithCReference(
							(EClass) node.getEModelElement(),// containment.getTargetName(),

							clonedContainmentName,
							(EClass) node.getEModelElement(),// FP150331a305
							false, propagated); // FP120920

				}
			} else if (containment.isPort()) {

				EReference erf = (EReference) ((EClass) node.getEModelElement())
						.getEStructuralFeature(containment.getNameIfCompartment());// FP150505

				dn = parser.getNodeWithPReference(
						(EClass) node.getEModelElement(),
						(EClass) erf.getEType(),
						containment.getNameIfCompartment(),
						clonedContainmentName, propagated);
			} else {

				EReference erf = (EReference) ((EClass) node.getEModelElement())
						.getEStructuralFeature(containment.getNameIfCompartment());// FP150505

				dn = parser.getNodeWithCReference(
						(EClass) node.getEModelElement(),// containment.getTargetName(),
						erf.getName(), (EClass) erf.getEType(),// (EClass)
																// node.getEModelElement(),//FP150331a305
						false, propagated);
			}

			List<IDiaContainment> cloneds = parser.getCreatedContainments();
			for (IDiaContainment cloned : cloneds) {
				DiaNode originalTargetNode = (DiaNode) containment
						.getTargetNode();
				if (originalTargetNode != null)
					cloned.setTargetNode(originalTargetNode); // FP140202xxxaaa
				else
					setTargetNode(node, containment, cloned);
				if (LOG)
					clog("(1)ClonedContainment: " + cloned.getName());
			}
		}
	}

	@Override
	public String parseNodes(String view) {
		if (DParams.Parser_CONTAINMENT2_LOG)
			clog2("-----------  resolveContainements for " + view);
		String result = "";
		for (DiaNode diaNode : getDiaNodes()) {
			result += containmentPattern.parseNode(diaNode, view);
		}
		return result;

		// for (DiaContainedElement diagramElement :
		// getdiaContainedElementsForContainmentInference_()) {

		// logContainment(diagramElement, view);
		/*
		 * if (LOG) if (diagramElement.getContainmentReferenceBase() == null) {
		 * if (diagramElement instanceof DiaGenericElement) // may // happen
		 * clog("containement for DiaGenericElement: " +
		 * diagramElement.getName() + " is null for view " + view); else if
		 * (diagramElement.isAbstract()) clog("containement for abstract : " +
		 * diagramElement.getName() + " is null for view " + view); else
		 * clog("containement for : " + diagramElement.getName() +
		 * " is null for view " + view); }
		 */
		// }
	}

	private List<DiaContainedElement> getdiaContainedElementsForContainmentInference_nu() {

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

	private List<DiaNode> getDiaNodes() {
		List<DiaNode> result = new ArrayList<DiaNode>();

		for (DiaContainedElement diaContainedElement : parser
				.getDiagramElements()) {
			if (diaContainedElement instanceof DiaNode)
				result.add((DiaNode) diaContainedElement);
		}
		return result;
	}

	private void propagateInheritedReferences_nu_nu() {
		if (!DParams.PARSER_PROPAGATE_INHERITED_REFERENCES_nu)
			return;
		if (LOG)
			clog(" --- propagateInheritedReferences --- ");
		for (DiaGenericElement diaGenericElement : parser
				.getAllGenericElements()) {
			clog("diaGenericElement: " + diaGenericElement.getName());
			for (IDiaContainedElement lowerElement : diaGenericElement
					.getLowerElements()) {
				clog("DiaGenericElement.lowerElement: "
						+ lowerElement.getName());
				for (DiaReference diaReference : diaGenericElement
						.getReferences()) {
					clog("DiaReference: " + diaReference.getName());
					if (lowerElement instanceof DiaNode) {
						/*
						 *
						 * boolean propagated = diaReference.isPropagated();
						 * DiaReference newref = createNodeReferenceAndResolve_(
						 * //FP150516voir (DiaNode) lowerElement,
						 * diaReference.getTargetName(), propagated,
						 * diaReference.getName()); if (LOG) {
						 * clog("DiaReference created (4): " +
						 * newref.getSourceNode().getName() + "." +
						 * newref.getName());
						 * clog("propagated from generic element: " +
						 * diaGenericElement.getName() + " to node " +
						 * lowerElement.getName()); }
						 */
					}
				}
			}
		}
	}

	@Override
	public void propagateInheritedReferences_nu() { // FP150516
		if (!DParams.PARSER_PROPAGATE_INHERITED_REFERENCES_nu)
			return;
		if (LOG)
			clog(" --- propagateInheritedReferences --- ");
		if (DParams.Parser_CONTAINMENT2_LOG)
			clog2("(2)propagateInheritedReferences");
		for (DiaNode node : parser.getAllNodes()) {
			for (IDiaContainedElement supernode : node.getSuperElements())
				if (supernode instanceof DiaNode) {
					if (DParams.Parser_CONTAINMENT2_LOG)
						clog2("propagate " + node.getName() + " -> "
								+ supernode.getName());
					propagateInheritedReference(node, (DiaNode) supernode); // FP150516
				}
		}
	}

	private void logInheritedContainments() {
		if (DParams.Parser_CONTAINMENT2_LOG)
			clog2("logInheritedContainments{");
		for (DiaNode node : parser.getAllNodes()) {
			for (IDiaContainedElement supernode : node.getSuperElements())
				if (supernode instanceof DiaNode) {
					if (DParams.Parser_CONTAINMENT2_LOG)
						clog2("propagate " + node.getName() + " -> "
								+ supernode.getName());
					logInheritedContainment(node, (DiaNode) supernode); // FP130619xa
				}
		}
		if (DParams.Parser_CONTAINMENT2_LOG)
			clog2("}logInheritedContainments");
	}

	private void doPropagateInheritedContainments() {
		if (DParams.Parser_CONTAINMENT2_LOG)
			clog2("(2)propagateInheritedContainments");
		for (DiaNode node : parser.getAllNodes()) {
			for (IDiaContainedElement supernode : node.getSuperElements())
				if (supernode instanceof DiaNode) {
					if (DParams.Parser_CONTAINMENT2_LOG)
						clog2("propagate " + node.getName() + " -> "
								+ supernode.getName());
					propagateInheritedContainment(node, (DiaNode) supernode); // FP130619xa
				}
		}
	}

	@Override
	public void propagateInheritedContainments_() {
		if (!DParams.PARSER_PROPAGATE_INHERITED_CONTAINMENTS_)
			return;
		if (LOG)
			logInheritedContainments();
		doPropagateInheritedContainments();

	}



}
