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
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.isoe.diagraph.diagraph.DNestedEdge;
import org.isoe.diagraph.diagraph.DOwnedEdge;
import org.isoe.diagraph.diagraph.DEdge;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.diagraph.DGraphElement;
import org.isoe.diagraph.diagraph.DNavigationEdge;
import org.isoe.diagraph.diagraph.DNode;
import org.isoe.diagraph.diagraph.DPointOfView;
import org.isoe.diagraph.diagraph.DReference;
import org.isoe.diagraph.diagraph.DSimpleEdge;
import org.isoe.diagraph.diastyle.DStyle;
//import org.isoe.diagraph.internal.api.IDiaNode.ContainmentType;
import org.isoe.diagraph.internal.api.IDiaPlatformDelegateProvider;
import org.isoe.diagraph.internal.api.IDiaPointOfView;
import org.isoe.diagraph.internal.m2.DiaContainedElement;
import org.isoe.diagraph.internal.m2.DiaContainment;
import org.isoe.diagraph.internal.m2.DiaGenericElement;
import org.isoe.diagraph.internal.m2.DiaLabel;
import org.isoe.diagraph.internal.m2.DiaLink;
import org.isoe.diagraph.internal.m2.DiaNode;
import org.isoe.diagraph.internal.m2.DiaPointOfView;
import org.isoe.diagraph.internal.m2.DiaReference;
import org.isoe.diagraph.internal.m2.DiaUtils;
import org.isoe.diagraph.internal.m2.api.IDiaContainedElement;
import org.isoe.diagraph.internal.m2.api.IDiaContainment;
import org.isoe.diagraph.internal.m2.api.IDiaElementVisitor;
import org.isoe.diagraph.internal.m2.api.IDiaParser;
import org.isoe.diagraph.internal.api.IDiaNode;
import org.isoe.diagraph.lang.DiagraphKeywords;
import org.isoe.diagraph.parser.api.IDiagraphAssociation;
import org.isoe.diagraph.parser.api.IDiagraphNotation;
import org.isoe.diagraph.parser.api.IDiagraphParser;
import org.isoe.diagraph.parser.api.IDiagraphReferenceAssociation;
import org.isoe.diagraph.runner.IDiagraphRunner;
import org.isoe.diagraph.views.ViewConstants;
import org.isoe.extensionpoint.rules.ISyntaxRules;
import org.isoe.extensionpoint.rules.SyntaxRulesConnector;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.core.IDiagraphRuler;

//FP140707_c_refactored

/**
 *
 * @author pfister
 *
 */

public class DiaParser implements IDiaParser, IDiagraphRuler {

	// private static final boolean CONTAINMENT_LOG =
	// DParams.Parser_CONTAINMENT_LOG;
	private static final boolean LOG = DParams.DiaParser_LOG; // FP130731
																// error10 case
	// private static final boolean LOG_FACTORY = false; // FP121124j;
	private boolean nodesdirty = true;
	private List<DiaContainedElement> diagramElements = new ArrayList<DiaContainedElement>();
	private List<DiaNode> allNodes = null;

	private List<IDiaContainment> containments_ = new ArrayList<IDiaContainment>();

	private List<EModelElement> modelElements;

	private DStyle stylesheet;
	private IDiaPlatformDelegateProvider platformDelegateProvider;
	private String errorMessage = "";
	private ContainmentHandler containmentHandler;
	private EPackage domainModel;
	private IDiaPointOfView pointOfView;
	private boolean disabled;
	private DiaNode canvasNode;
	private String view;
	private List<DiaContainedElement> upperContainedElement; // FP150515
	private List<IDiaContainment> createdContainments_ = new ArrayList<IDiaContainment>();
	private List<DiaContainedElement> containedElements = new ArrayList<DiaContainedElement>();

	private List<DiaReference> createdReferences_ = new ArrayList<DiaReference>();

	private static final boolean KSUFFIX = false;
	private static final String KSUFFIX_LITTERAL = "K";
	public static final boolean DEFAULT_EMBEDDED_CONTAINMENT = true;
	private IDiagraphRunner runner;
	private ISyntaxRules syntaxRules;
	private IDiaInheritance inheritanceMan;
	private IDiagraphNotation notation;
	// private IDiagraphParser parser2;// =
	// DiagraphAnnotationUtils.getInstance();

	private static final String spaces = "                                                                                                   ";

	public ISyntaxRules getSyntaxRules() {
		if (syntaxRules == null) {
			syntaxRules = new SyntaxRulesConnector().getService();
			syntaxRules.setParser(this);
		}
		return syntaxRules;
	}

	@Override
	public List<IDiaContainment> getContainments() {
		return containments_;
	}

	public DiaParser(EPackage domainModel, String layer,
			IDiagraphRunner runner, IDiagraphNotation notation,
			IDiaPlatformDelegateProvider platformDelegateProvider,
			DStyle stylesheet) { // IDiaPointOfView pointOfView,
		// this.pointOfView = pointOfView;

		this.domainModel = domainModel;
		this.platformDelegateProvider = platformDelegateProvider;
		this.view = layer;

		this.containmentHandler = new ContainmentHandler(this, notation);
		this.stylesheet = stylesheet;
		this.runner = runner;
		this.inheritanceMan = new DiaInheritanceManager(this, notation); // FP140125
		this.notation = notation;// grammar.getParser();//DiagraphAnnotationParser.getInstance_(null,this);
	}

	public EClass getPointOfView(List<EModelElement> modelElements) {
		for (EModelElement el : modelElements)
			if (el instanceof EClass)
				if (isPov((EClass) el))
					return (EClass) el;
		return null;
	}

	public void setPointOfView(IDiaPointOfView pointOfView) {
		this.pointOfView = pointOfView;// getPointOfView(getModelElements());//pointOfView;
	}

	@Override
	public DStyle getStyleSheet() {
		return stylesheet;
	}

	public List<EModelElement> getModelElements() {
		if (modelElements == null) {
			modelElements = new ArrayList<EModelElement>();
			for (Iterator<EObject> contents = domainModel.eAllContents(); contents
					.hasNext();) {
				EObject el = contents.next();
				if (!(el instanceof EModelElement)
						|| (el instanceof EAnnotation))
					continue;
				modelElements.add((EModelElement) el);
			}
		}
		return modelElements;
	}

	@Override
	public DiaNode findNode(String nodeName) {
		for (DiaContainedElement elem : diagramElements) {
			if (elem instanceof DiaNode) {
				if (((DiaNode) elem).getName().equals(nodeName))
					return (DiaNode) elem;
			}
		}
		return null;
	}

	private DiaNode findNode(EModelElement mel) {
		for (DiaContainedElement elem : diagramElements) {
			if (elem instanceof DiaNode) {
				if (((DiaNode) elem).getEModelElement() == mel)
					return (DiaNode) elem;
			}
		}
		return null;
	}

	private DiaContainedElement findElement(String nodeName) {
		for (DiaContainedElement elem : diagramElements)
			if (elem.getName().equals(nodeName))
				return elem;
		return null;
	}

	private List<DiaNode> getAllAbstractNodes() {
		List<DiaNode> result = new ArrayList<DiaNode>();
		for (DiaContainedElement elem : diagramElements)
			if (elem instanceof DiaNode && ((DiaNode) elem).isAbstract())
				result.add((DiaNode) elem);
		return result;
	}

	private List<DiaLink> getAllLinks() {
		List<DiaLink> result = new ArrayList<DiaLink>();
		for (DiaContainedElement elem : diagramElements)
			if (elem instanceof DiaLink)
				result.add((DiaLink) elem);
		return result;
	}

	@Override
	public List<DiaGenericElement> getAllGenericElements() {
		List<DiaGenericElement> result = new ArrayList<DiaGenericElement>();
		for (DiaContainedElement elem : diagramElements)
			if (elem instanceof DiaGenericElement)
				result.add((DiaGenericElement) elem);
		return result;
	}

	@Override
	public List<EReference> getAllReferences() {
		List<EReference> result = new ArrayList<EReference>();
		for (EModelElement mel : getModelElements())
			if (mel instanceof EReference)
				result.add((EReference) mel);
		return result;
	}

	@Override
	public DiaLabel createInferredLabel(DiaContainedElement del, String name,
			String attributeName) {
		DiaLabel lb = new DiaLabel(del, attributeName, false, false);
		lb.setInferred_(true);
		if (name != null)
			lb.setDefaultName(name);
		del.addInferredLabel(lb);
		lb.createWithNames();
		if (LOG)
			clog("inferred label for :" + del.getElementName() + " ["
					+ lb.getName() + "]");
		return lb;
	}

	private void setNodesDepth() {
		if (LOG)
			clog("setNodeDepth");
		for (DiaNode node : getAllNodes()) {
			int order = getGreatestDepth(node); // FP2512
			node.setDepth(order);
		}
	}

	private void sortContainments() {
		if (LOG)
			clog("sortContainments");
		List<DiaNode> nodes = getAllNodes();// FP150318t
		for (DiaNode diaNode : nodes) {
			List<IDiaContainment> containments = diaNode.getContainments();
			if (LOG)
				for (IDiaContainment containment : containments) {
					clog(">>>to sort: " + containment.getSourceNode().getName()
							+ " -- " + containment.getName() + " - order: "
							+ containment.getOrder());
				}
			Collections.sort(containments, new Comparator<IDiaContainment>() {
				@Override
				public int compare(IDiaContainment o1, IDiaContainment o2) {
					return o2.getOrder() - o1.getOrder();
				}
				/*
				 * private void sortNodes_not_used() { clog("sortNodes");
				 * clog("  before:"); for (DiaNode node : allNodes) {
				 * clog(node.getName() + " (" + node.getDepth() + ") "); }
				 * Collections.sort(allNodes, new Comparator<DiaNode>() {
				 *
				 * @Override public int compare(DiaNode o1, DiaNode o2) { return
				 * o2.getDepth() - o1.getDepth(); } }); clog("  after:"); for
				 * (DiaNode node : allNodes) { clog(node.getName() + " (" +
				 * node.getDepth() + ") "); } }
				 */
			});
			if (LOG)
				for (IDiaContainment containment : containments) {
					clog(">>>sorted: " + containment.getSourceNode().getName()
							+ " -- " + containment.getName() + " - order: "
							+ containment.getOrder());
				}

		}
	}

	private void logContainments() { // FP150427voir
		if (!(LOG))
			return;
		clog("logContainments");
		for (IDiaContainment containment : containments_)
			clog("(cont)" + containment.toString());
	}

	/*
	 * private List<DiaNode> findContainers_nu(DiaNode node) { List<DiaNode>
	 * result = new ArrayList<DiaNode>(); for (EReference eReference : ((EClass)
	 * node.getEModelElement()) .getEReferences()) if
	 * (eReference.isContainment()) { DiaNode cnode = findNode(eReference); if
	 * (cnode != null) { result.add(cnode); if (LOG && LogConfig.LOG_GLOBAL)
	 * clog(cnode.getName() + " == " + eReference.getName() + ":" +
	 * eReference.getEType().getName()); } } return result; }
	 */

	private List<EReference> findContainers_old(DiaNode node) {
		List<EReference> result = new ArrayList<EReference>();
		for (EReference eReference : ((EClass) node.getEModelElement())
				.getEReferences())
			if (eReference.isContainment()) {
				DiaNode cnode = findNode(eReference);
				// if (cnode!=null){
				result.add(eReference);
				if (LOG)
					clog("-->" + eReference.getName() + ":"
							+ eReference.getEType().getName());
				// }
			}
		return result;
	}

	private List<EReference> findContainers_new(DiaNode node) {
		List<EReference> result = new ArrayList<EReference>();
		for (EReference eReference : ((EClass) node.getEModelElement())
				.getEAllReferences())
			// FP140201 EAll
			if (eReference.isContainment()) {
				DiaNode cnode = findNode(eReference);
				// if (cnode!=null){
				result.add(eReference);
				if (LOG)
					clog("-->" + eReference.getName() + ":"
							+ eReference.getEType().getName());
				// }
			}
		return result;
	}

	@Override
	public void postTransform(DGraph dgraph) {
		if (DParams.CHECK_POV_CONSISTENCE) // FP130124
			checkPointOfViewConsistence(dgraph);
	}

	private void checkPointOfViewConsistence(DGraph dgraph) { // FP120924
		List<DEdge> povEdges = getPovEdges(dgraph);
		if (povEdges.isEmpty())
			// clog("no edges for point of view !!!!");
			throw new RuntimeException("no edges for point of view  !!!!");
	}

	private List<DNode> getNodes(DGraph graph) { // FP140518
		List<DNode> nodes = new ArrayList<DNode>();
		for (DGraphElement element : graph.getElements())
			if (element instanceof DNode)
				nodes.add((DNode) element);
		return nodes;
	}

	public List<DEdge> getEdges(DNode node) {
		List<DEdge> result = new ArrayList<DEdge>();
		for (DGraphElement element : node.getGraph().getElements()) {
			if (element instanceof DNestedEdge) {
				if (((DNestedEdge) element).getSource().getNode() == node)
					result.add((DEdge) element);
			} else if (element instanceof DSimpleEdge) {
				if (((DSimpleEdge) element).getSource() == node)
					result.add((DEdge) element); // FP150423b
			}
		}
		return result;
	}

	protected List<DEdge> getEdges(DGraph graph) { // FP140518
		List<DEdge> result = new ArrayList<DEdge>();
		for (DGraphElement element : graph.getElements())
			if (element instanceof DEdge)
				result.add((DEdge) element);
		return result;
	}

	private List<DEdge> getPovEdges(DGraph graph) {
		List<DEdge> result = new ArrayList<DEdge>();
		if (graph != null)
			for (DNode dNode : getNodes(graph)) {
				if (LOG)
					clog(dNode.toString());
				if (dNode instanceof DPointOfView) {
					DPointOfView pov = (DPointOfView) dNode;
					List<DEdge> edges = getEdges(pov);
					for (DEdge dEdge : edges) {
						result.add(dEdge);
						if (LOG)
							clog(dEdge.toString()); // FP121224
						// klkj
					}
				}
			}

		return result;
	}

	private void checkPointOfViewConsistence1_new() { // FP120924 //FP131018

		for (DiaNode node : getAllNodes()) {
			if (LOG)
				clog("CPVC " + node.getName());
			// if (node.getName().equals("T1"))
			// tb = true;
			if (node.isCanvas()) {
				List<EReference> cont = findContainers_new(node);
				if (cont == null || cont.isEmpty()) {
					if (LOG)
						clog("point of view " + node.getName()
								+ " should have at least one contained Node");
					throw new RuntimeException(
							node.getName()
									+ ": a point of view should have at least one contained Node !!!!"); // FP131020xxx
				} else {
					if (LOG)
						clog("point of view " + node.getName()
								+ " is consistent (1)");
					break;
				}
			}
		}
		// = true;
	}

	private void checkPointOfViewConsistence1_old() { // FP120924 //FP131018

		for (DiaNode node : getAllNodes()) {
			if (LOG)
				clog("CPVC " + node.getName());
			// if (node.getName().equals("T1"))
			// tb = true;
			if (node.isCanvas()) {
				List<EReference> cont = findContainers_old(node);
				if (cont == null || cont.isEmpty()) {
					if (LOG)
						clog("point of view " + node.getName()
								+ " should have at least one contained Node");
					throw new RuntimeException(
							node.getName()
									+ ": a point of view should have at least one contained Node !!!!"); // FP131020xxx
				} else {
					if (LOG)
						clog("point of view " + node.getName()
								+ " is consistent");
					break;
				}
			}
		}
		// = true;
	}

	private void checkPointOfViewConsistence2() { // FP131020xxx // FP121224x
		for (DiaNode node : getAllNodes()) {
			if (node.isCanvas()) {
				List<IDiaNode> embeddednods = node.getEmbeddedNodes();// FP150318t
				if (embeddednods.isEmpty())// if (!found)
					throw new RuntimeException(
							node.getName()
									+ ": a point of view should have at least one embedded Node !!!!");
				else if (LOG)
					clog("point of view " + node.getName() + " is consistent");

			}
		}
	}

	private void logContainments2() {
		if (!(LOG))
			return;
		clog("logContainments -2-");
		for (DiaNode dianode : getAllNodes())
			dianode.logContainments();
	}

	private void resolveContainmentsWithDeferredHosts() { // TODO FP2910
		boolean check;
		for (IDiaContainment containment : containments_) {
			if (containment.getDeferredHost() != null) {
				DiaNode nod = findNode(containment.getDeferredHost());
				if (nod == null)
					throw new RuntimeException("containment "
							+ containment.getDeferredHost() + " not found");
				containment.setSourceNode(nod);
				if (containment.getName() == null)
					check = true;
				if (containment.getNameIfCompartment() == null)
					check = true;
				EClass src = nod.getSemanticRole();
				nod.addContainment(2, containment, -1, true);
				EReference dc = nod.getDeclaredContainment(); // FP140408
				containment.setDeferredHost(null);
				if (LOG)
					clog("resolveContainments: containment "
							+ containment.getName() + " added to DiaNode"
							+ " [" + nod.getName() + "]");
			}
		}
	}

	IDiaElementVisitor elementVisitor = new DiaElementVisitorImpl(null) {
		@Override
		public void visit(IDiaContainedElement superElement,
				IDiaContainedElement lowerElement, int depth) {
			if (LOG)
				clog((depth > 0 ? spaces.substring(0, 3 * depth) : "")
						+ lowerElement.getName());

		}
	};

	private String log;
	private String label___;
	private int caze_;

	@Override
	public String getLog() {
		return log;
	}

	@Override
	public List<IDiaContainment> getCreatedContainments() { // FP140202
		return createdContainments_;
	}

	@Override
	public List<DiaReference> getCreatedReferences() {
		return createdReferences_;
	}

	@Override
	public String getView() {
		return view;
	}

	@Override
	public void postParse() {
		if (LOG)
			clog("------- postParse -----------");
		checkReferenceNaming();
		// propagateEcoreInheritanceOld();
		propagateEcoreInheritance();
		logInheritance(true, elementVisitor);
		logInheritance(false, elementVisitor);
		setNodesDepth();
		containmentHandler.resolveContainmentsBaseWithName();

		resolveDiagramRecursion_();
		if (DParams.RESOLVE_COMPOSITE)
			resolveCompositePattern(); // FP150531

		String pn1 = containmentHandler.parseNodes(view); // FP140414 ///---//
															// FP140401
		resolveLinkBaseContainment();
		String pl1 = containmentHandler.parseLinks(view); // FP140414 ///---

		containmentHandler.logContainments();
		containmentHandler.logDomainContainments();
		resolveContainmentsWithDeferredHosts();
		// resolveContainmentsX();
		checkContainer(); // FP140115
		createLabelsWithName();

		// inheritanceMan.cloneInheritedContainments();//derivateVisualInheritance();
		// //FP140125 down...

		inheritanceMan.propagateInheritedContainments_(); // FP140206inh
		// inheritanceMan.propagateInheritedReferences_nu(); // FP150516

		resolveTargetRefs();
		checkReferenceSyntax();
		if (DParams.CHECK_POV_CONSISTENCE)
			resolveMissingCRefsIfCanvas_(); // FP130124 // FP121224y
		resolveReferenceTargetNodes();

		resolveContainmentSourceAndTargetNodes_();

		// replaceAllTargetAbstractByConcrete(); //FP150329a107

		containmentHandler.checkMultipleContainementsForContainmentInference(); // FP131020
		containmentHandler.inferNodeContainments();
		containmentHandler.resolveContainmentsAlt_(); // FP140331

		containmentHandler.cleanMultipleContainements(); // FP131020

		// resolveLinkBaseContainment();

		sortContainments();
		logContainments(); // FP150427
		logContainments2();
		// resolveLinkSourceReference(); //FP130915zzxx
		checkLinkSource();

		inferLinkSourceReferences();
		inferLinkTargetReferences();// FP140115xx
		reparseLinksWithSuperClasses();
		// inferLinkTargetReferences_();
		resolveLinkSourceAndTarget();
		checkLinkSourceAndTarget();
		resolveInheritedFeatures();
		resolveLinks();
		containmentHandler.validateContainment();

		containmentHandler.resolveContainmentReferences();
		// containmentHandler.resolveContainmentReferences_test();
		containmentHandler.resolveContainmentNode();
		containmentHandler.checkContainments();
		containmentHandler.logContainments1();
		containmentHandler.checkTargetContainments();
		checkPointOfViewConsistence1_new(); // FP140201// FP120924
		if (DParams.CHECK_POV_CONSISTENCE)
			checkPointOfViewConsistence2(); // FP130124 // FP121224

		String pn2 = inheritanceMan.parseNodes(view); // FP140414// FP140328

		if (DParams.Parser_CONTAINMENT2_LOG)
			clog2(pn2);

		// boolean compar=inheritanceMan.compareSubnodes();

		// if (!compar)
		// tb = true;

		inheritanceMan.logNodesAndSubnodes();
		inheritanceMan.derivateAbstractContainements__(); // FP150516azerazer //
															// FP140124
		// inheritanceMan.derivateContainements(); //FP150516voir2 // FP140124

	}

	/*
	 * private void derivateVisualInheritance_() { //
	 * derivateAbstractContainements(); //FP140124
	 * inheritanceMan.cloneInheritedContainments(); // FP140124 // FP131008www }
	 */

	private void checkReferenceNaming() {
		for (DiaNode containerNode : getAllNodes()) {
			EClass claz = (EClass) (containerNode.getEModelElement());
			EList<EReference> refs = claz.getEReferences();
			for (EReference eReference : refs) {
				if (eReference.getName() != null
						&& eReference.getName().equals("")) {
					errorMessage += "A Reference Name is empty for "
							+ claz.getName() + "---->"
							+ eReference.getEType().getName() + "\n";
					throw new RuntimeException(errorMessage);
				}
			}
		}
	}

	public void disable() {
		disabled = true;
	}

	@Override
	public boolean isDisabled() {
		return disabled;
	}

	private List<DiaGenericElement> getUpperGenericElements() {
		List<DiaGenericElement> result = new ArrayList<DiaGenericElement>();
		for (DiaContainedElement el : diagramElements)
			if ((el instanceof DiaGenericElement)
					&& (((EClass) el.getEModelElement()).getEAllSuperTypes()
							.size() == 0))
				result.add((DiaGenericElement) el);
		return result;
	}

	private List<DiaContainedElement> getUpperContainedElementsOld() {
		List<DiaContainedElement> result = new ArrayList<DiaContainedElement>();
		for (DiaContainedElement el : diagramElements)
			if ((el instanceof DiaContainedElement)
					&& (((EClass) el.getEModelElement()).getEAllSuperTypes()
							.size() == 0))
				result.add(el);
		return result;
	}

	private List<DiaContainedElement> getUpperContainedElementsNew_() { // FP150515
		if (upperContainedElement == null) {
			upperContainedElement = new ArrayList<DiaContainedElement>();
			for (DiaContainedElement el : diagramElements)
				if ((el instanceof DiaContainedElement)
						&& (((EClass) el.getEModelElement())
								.getEAllSuperTypes().size() == 0))
					upperContainedElement.add(el);
			if (LOG) {
				String log = "";
				for (DiaContainedElement el : upperContainedElement)
					log += el.getName() + ",";
				clog("UpperContainedElements: " + log);
			}
		}
		return upperContainedElement;
	}

	private List<DiaContainedElement> getContainedElement() { // FP150515
		if (containedElements == null) {
			containedElements = new ArrayList<DiaContainedElement>();
			for (DiaContainedElement el : diagramElements)
				if (el instanceof DiaContainedElement)
					containedElements.add(el);
			if (LOG) {
				String log = "";
				for (DiaContainedElement el : containedElements)
					log += el.getName() + ",";
				clog("containedElements: " + log);
			}
		}
		return containedElements;
	}

	private void iterate(IDiaElementVisitor visitor,
			IDiaContainedElement container, int level) {
		level++;
		for (IDiaContainedElement diaContainedElement : container
				.getLowerElements()) {
			visitor.visit(container, diaContainedElement, level);
			iterate(visitor, diaContainedElement, level);
		}
	}

	private void propagateEcoreInheritance() {
		for (DiaContainedElement el : getDiagramElements())
			el.propagateEcoreInheritance();
	}

	/*
	 * private void propagateEcoreInheritanceOld() { List<DiaContainedElement>
	 * cels = getUpperContainedElementsNew_(); if (LOG) { String log = ""; for
	 * (DiaContainedElement el : cels) log += el.getName() + ",";
	 * clog("propagate Ecore Inheritance for All Contained Elements: " + log); }
	 * for (DiaContainedElement el : cels) el.propagateEcoreInheritanceOld(0); }
	 */

	@Override
	public void traverseInheritance(IDiaElementVisitor elementVisitor) {
		for (DiaContainedElement upperElement : getUpperContainedElementsNew_()) {
			elementVisitor.visit(null, upperElement, 0);
			iterate(elementVisitor, upperElement, 0);
		}
	}

	public void visit(IDiaElementVisitor elementVisitor) {
		for (DiaContainedElement diaContainedElement : getUpperContainedElementsNew_()) {
			elementVisitor.visit(null, diaContainedElement, 0);
			containmentHandler.visitContainedElementTree(elementVisitor,
					diaContainedElement, 0);
		}
	}

	public void logInheritance(boolean generic,
			IDiaElementVisitor elementVisitor) {
		if (LOG)
			clog("ContainedElement Tree " + (generic ? " (generic)" : " (all)")
					+ ":");
		for (DiaContainedElement diaContainedElement : getUpperContainedElementsNew_()) {
			if (!generic
					|| (generic && diaContainedElement instanceof DiaGenericElement)) {
				// clog(diaContainedElement.getName());
				elementVisitor.visit(null, diaContainedElement, 0);
				containmentHandler.logContainedElementTree(elementVisitor,
						diaContainedElement, generic, 0);
			}
		}
		if (LOG)
			clog("End (ContainedElement Tree)");
	}

	private int getGreatestDepth(DiaNode node) {
		IDiaElementVisitor v = new DiaElementVisitorImpl(node) {
			@Override
			public void visit(IDiaContainedElement superElement,
					IDiaContainedElement lowerElement, int depth) {
				if (element != null && lowerElement == element) {
					if (object == null)
						object = 0;
					int currentDepth = (Integer) object;
					if (depth > currentDepth) // handles multiple inheritance
												// (greatest depth)
						object = depth;
				}
			}
		};
		visit(v);
		if (v.getObject() != null)
			return (Integer) v.getObject();
		else
			return -1;
	}

	private void logNode_nu(DiaNode node) {
		IDiaElementVisitor v = new DiaElementVisitorImpl(node) {
			@Override
			public void visit(IDiaContainedElement superElement,
					IDiaContainedElement lowerElement, int depth) {
				// clog((depth > 0 ? spaces.substring(0, 3 *
				// depth) : "") + lowerElement.getName());
				if (element != null && lowerElement == element)
					object = depth;
			}
		};

		logInheritance(false, v);

		int nodeDepth = (Integer) v.getObject();
		clog("node " + node.getName() + " depth =" + nodeDepth);

		clog("--logNode for " + node.getName() + "--");
		for (IDiaContainedElement supel : node.getSuperElements()) {
			clog("SuperElement:" + supel.getName());
			// clog("SuperSuperElement:"
			// +supel.getSuperElements().get(0).getName());
		}

		if (node.getInheritedContainmentsBase().size() == 0) // FP2611
			clog("no InheritedContainment base : for " + node.getName());
		else
			for (EModelElement inhercont : node.getInheritedContainmentsBase()) {
				// assert inhercont instanceof EReference;
				clog("InheritedContainment base:"
						+ ((EReference) inhercont).getName());
			}

		if (node.getInheritedContainmentsAlt().size() == 0) // FP2611
			clog("no InheritedContainment alt: for " + node.getName());
		else
			for (EModelElement inhercont : node.getInheritedContainmentsAlt()) {
				// assert inhercont instanceof EReference;
				clog("InheritedContainment alt:"
						+ ((EReference) inhercont).getName());
			}

		EReference contn_ = node.getContainmentReferenceBase();
		// assert contn instanceof EReference;
		if (contn_ == null) {
			clog("node.getContainmentReferenceBase == null for "
					+ node.getName());
			if (node.getInheritedContainmentsBase().size() == 0)
				clog("node " + node.getName() + " has no containment !!!!");
		} else
			clog("Containment:" + ((EClass) contn_.eContainer()).getName()
					+ "." + contn_.getName());

		EReference contalt = node.getContainmentReferenceAlt();

		if (contalt == null) {
			clog("node.getContainmentReferenceAlt == null for "
					+ node.getName());

		} else
			clog("ContainmentAlt:" + ((EClass) contalt.eContainer()).getName()
					+ "." + contalt.getName());

		if (node.getContainments().size() > 0)
			clog(node.getContainments().size() + " containments");
	}

	private void logSuperContainments() {
		for (DiaNode node : getAllNodes())
			for (IDiaContainedElement supernode : node.getSuperElements())
				if (supernode instanceof DiaNode)
					clog("node: " + node.getName() + " -> "
							+ supernode.getName());
	}

	@Override
	public DiaContainedElement findDiagramElement(EModelElement element) {
		for (DiaContainedElement diagramElement : diagramElements)
			if (diagramElement.getEModelElement() == element)
				return diagramElement;
		return null;
	}

	private DiaContainedElement findDiagramElement_(EModelElement element) {
		assert element instanceof EClass;
		EClass elclaz = (EClass) element;
		for (DiaContainedElement el : diagramElements) {
			if (el.getEModelElement() == elclaz && el.getView().equals(view))
				return el;
		}
		return null;
	}

	private void resolveLinks() {
		for (DiaLink ln : getAllLinks())
			if (ln.getSourceNode() != null
					&& !ln.getSourceNode().getDiaLinks().contains(ln)) // FP120107
				ln.getSourceNode().addLink(ln);
	}

	private void resolveContainmentSourceAndTargetNodes_() {
		for (DiaNode nod : getAllNodes())
			nod.resolveContainmentSourceAndTargetNodes_();
	}

	/*
	 * private void replaceAllTargetAbstractByConcrete() { for (DiaNode nod :
	 * getAllNodes()) nod.replaceTargetAbstractByConcrete(); }
	 */

	private void resolveMissingCRefsIfCanvas_() {
		for (DiaNode nod : getAllNodes())
			nod.resolveMissingCRefsIfCanvas();
	}

	private void resolveReferenceTargetNodes() {
		for (DiaNode nod : getAllNodes())
			nod.resolveReferenceTargetNodes();
	}

	private void resolveLinkSourceReference() {
		for (DiaLink diaLink : getAllLinks()) {
			diaLink.resolveSourceReference_();
			// diaLink.setSourceReference(resolveLinkSource(diaLink));//FP150513
		}
	}

	private void checkReferenceSyntax() {
		if (LOG)
			clog("checking All Nodes");
		boolean result = true;
		for (DiaNode node : getAllNodes())
			if (!node.checkReferences())
				result = false;
		if (LOG) {
			clog("!!! check your annotations\n" + result);
			for (DiaNode node : getAllNodes())
				clog(node.logReferences());
		} else
			getAllNodes(); // keep getAllNodes() if not LOG

	}

	private void checkLinkSourceAndTarget() {
		for (DiaLink ln : getAllLinks())
			if (ln.checkSourceAndTarget())
				if (LOG)
					clog(" warning:"
							+ ln.getName()
							+ " check your annotations (missing source for link ??)\n");
	}

	// TODO FP2910
	private void resolveTargetRefs() {
		for (DiaContainedElement diagramElement : diagramElements)
			diagramElement.resolveTargetRef();
	}

	private void createLabelsWithName() {
		for (DiaContainedElement diagramElement : diagramElements)
			diagramElement.createLabelsWithName();
	}

	private void resolveDiagramRecursion_() {
		for (DiaContainedElement diagramElement : diagramElements) {
			if (diagramElement instanceof DiaNode)
				((DiaNode) diagramElement).resolveDiagramRecursion_();
		}
	}

	private void resolveCompositePattern() {
		for (DiaContainedElement diagramElement : diagramElements) {
			if (diagramElement instanceof DiaNode)
				((DiaNode) diagramElement).resolveCompositePattern();
		}
		int count = 0;

		for (DiaContainedElement diagramElement : diagramElements) {
			if (diagramElement instanceof DiaNode)
				if (((DiaNode) diagramElement).isComponent())
					count++;
		}
		if (count > 1)
			throw new RuntimeException(
					"should not happen in resolveCompositePattern");

	}

	private void resolveInheritedFeatures() {
		propagateInheritedLabelsIfNoLabel();
		propagateAllInheritedLabels();
		// propagateInheritedReferences_nu();
		inferMissingLabels();
	}

	private void propagateInheritedLabelsIfNoLabel() {
		if (LOG)
			clog(" --- propagateInheritedLabelsIfNoLabel --- ");
		for (DiaContainedElement diagramElement : diagramElements)
			diagramElement.propagateInheritedLabelsIfNoLabel();
	}

	private void propagateAllInheritedLabels() {
		if (LOG)
			clog(" --- propagateAllInheritedLabelss --- ");
		for (DiaContainedElement diagramElement : diagramElements)
			diagramElement.propagateAllInheritedLabels();
	}

	private void propagateInheritedReferences_nu() {
		if (!DParams.PARSER_PROPAGATE_INHERITED_REFERENCES_nu)
			return;
		if (LOG)
			clog(" --- propagateInheritedReferences --- ");
		for (DiaGenericElement diaGenericElement : getAllGenericElements()) {
			clog("diaGenericElement: " + diaGenericElement.getName());
			for (IDiaContainedElement lowerElement : diaGenericElement
					.getLowerElements()) {
				clog("DiaGenericElement.lowerElement: "
						+ lowerElement.getName());
				for (DiaReference diaReference : diaGenericElement
						.getReferences()) {
					clog("DiaReference: " + diaReference.getName());
					if (lowerElement instanceof DiaNode) {
						boolean propagated = diaReference.isPropagated();
						DiaReference newref = createNodeReferenceAndResolve_(
								// FP150516voir
								(DiaNode) lowerElement,
								diaReference.getTargetName(), propagated,
								diaReference.getName());
						if (LOG) {
							clog("DiaReference created (4): "
									+ newref.getSourceNode().getName() + "."
									+ newref.getName());
							clog("propagated from generic element: "
									+ diaGenericElement.getName() + " to node "
									+ lowerElement.getName());
						}
					}
				}
			}
		}
	}

	private IDiaContainment createMissingCRef(EReference cref,
			boolean propagated) {
		// if (LOG)
		// clog("createMissingCRef: " + cref.getName());
		IDiaContainment result = createMissingCReference(cref,
		// (EClass) cref.eContainer(), cref.getName(),
		// cref.getName(),
				propagated);
		// result.setTargetReference(cref);// FP121224zz
		return result;
	}

	@Override
	public void inferMissingCrefs(DiaPointOfView povnode) {
		boolean doit = false;
		for (IDiaContainment diaContainment : getContainments())
			if (diaContainment.getSourceNode() == null)
				doit = true;
		if (!doit)
			return;
		EClass povclass = povnode.getEClass(); // FP150513
		for (EReference ref : povclass.getEAllReferences()) {
			if (ref.isContainment()) {
				DiaContainedElement el = findElement(ref.getEType().getName());
				if (el instanceof DiaNode) { // FP150523
					if (LOG)
						clog("IMCREF " + ((EClass) ref.eContainer()).getName()
								+ "." + ref.getName() + "->"
								+ ref.getEType().getName());
					IDiaContainment missing = createMissingCRef(ref, false);
					DiaNode targ = findNode(ref.getEType().getName());
					if (targ != null) // FP140415
						missing.setTargetNode(targ);
					else {
						String what = (((EClass) ref.eContainer()).getName()
								+ "." + ref.getName() + "->" + ref.getEType()
								.getName());
						throw new RuntimeException(
								"(1)no target node while infer " + what
										+ " in view " + view);
					}
				}
			}
		}
	}

	private void inferMissingLabels() { // FP121122x
		if (LOG)
			clog(" --- inferMissingLabels --- ");
		for (DiaContainedElement diagramElement : diagramElements) {
			if (!((diagramElement instanceof DiaNode)
					&& ((DiaNode) diagramElement).isCanvas() && !((DiaNode) diagramElement)
						.isDiagramRecursive())) {
				boolean inferred = diagramElement.inferLabelIfMissing();
				if (LOG)
					if (diagramElement.getInferredLabels().size() > 0) {
						String log = diagramElement.getName() + " -> ";
						for (DiaLabel diaLabel : diagramElement
								.getInferredLabels())
							log += diaLabel.getAttributeName_() + ";";
						clog(log);
					}
			}
		}
	}

	private boolean checkLinkSource() {
		boolean result = true;
		for (DiaContainedElement diagramElement : diagramElements) {
			if (diagramElement instanceof DiaLink) {
				DiaLink link = (DiaLink) diagramElement;
				if (link.getSourceReference() == null) {
					if (LOG)
						clog("checkLinkSource " + link.getName());
					result = false;
				}
			}
		}
		return result;
	}

	private void inferLinkTargetReferences() { // FP140503
		for (DiaContainedElement el : diagramElements) {
			if (el instanceof DiaLink) {
				DiaLink link = (DiaLink) el;
				if (link.getTargetReference() == null) {
					if (LOG)
						clog("missing targetReference for " + link.getName());
					link.resolveTargetReference();
					if (link.getTargetReference() == null) {
						if (!((EClass) link.getEModelElement()).isAbstract())// FP130731
							throw new RuntimeException(
									"link.TargetReference == null for "
											+ link.getName());
					}
					// assert link.getTargetReference() != null;
					if (LOG)
						clog("inferred targetReference for " + link.getName()
								+ " : [" + link.getTargetReference().getName()
								+ "]");
				}
			}
		}
	}

	/*
	 * private EReference getSourceReferenceWithContainingClass(DiaLink link) {
	 * EReference crefbase = link.getContainmentReferenceBase(); if (crefbase !=
	 * null) for (EReference eReference : ((EClass) link.getEModelElement())
	 * .getEAllReferences()) { if (eReference.getEType() ==
	 * crefbase.getEContainingClass()) return eReference; } return null; }
	 *
	 *
	 * private EReference getUniqueLinkSource(EClass eclaz,EReference target) {
	 * int found = 0; EReference result = null; for (EStructuralFeature r :
	 * eclaz.getEAllStructuralFeatures()) if ((r instanceof EReference) &&
	 * r!=target && r.getUpperBound() == 1 && isNode((EClass) r.getEType())) {
	 * result = (EReference) r; found++; } return found == 1 ? result : null; }
	 *
	 * private EReference resolveLinkSource(DiaLink link) { String t =
	 * link.getTarget(); EReference tr=link.getTargetReference(); EReference
	 * result =
	 * getUniqueLinkSource(link.getSemanticRole(),link.getTargetReference()); if
	 * (result==null) result = getSourceReferenceWithContainingClass(link);
	 * return result; }
	 */
	private void inferLinkSourceReferences() {

		for (DiaContainedElement diagramElement : diagramElements) {
			if (LOG)
				clog(diagramElement.getName());
			if (diagramElement instanceof DiaLink) {
				DiaLink link = (DiaLink) diagramElement;
				// clog(link.getName());
				if (link.getSourceReference() == null) {
					link.resolveSourceReference_();
					// link.resolveSourceReferenceWithContainingClass();
					// link.setSourceReference(resolveLinkSource(link));//FP150513
					if (link.getSourceReference() == null) {
						if (LOG)
							clog("ok, no sourceReference for link:"
									+ link.getName());
					} else if (LOG)
						clog("inferred sourceReference (1) for :"
								+ link.getName() + " : ["
								+ link.getSourceReference().getName() + "]");
				}
			}
		}
		// return linkparams;
	}

	private void checkLinkBaseContainment_() { // FP140423bb
		for (DiaContainedElement diagramElement : diagramElements) {
			if (LOG)
				clog(diagramElement.getName());
			if (diagramElement instanceof DiaLink) {
				((DiaLink) diagramElement).checkBaseContainment_();
			}
		}
		// return linkparams;
	}

	private void resolveLinkBaseContainment() { // FP140423bb
		for (DiaContainedElement diagramElement : diagramElements) {
			if (LOG)
				clog(diagramElement.getName());
			if (diagramElement instanceof DiaLink) {
				((DiaLink) diagramElement).resolveBaseContainment();
			}
		}
		// return linkparams;
	}

	private String[] reparseWithUpperEClasses(DiaLink link) {
		String[] linkparams = new String[3];
		int c = 0;
		List<EAnnotation> uppers = reparse(link);
		for (EAnnotation eAnnotation : uppers) {
			clog(eAnnotation.toString());
			String[] r = parseLink(eAnnotation);
			for (int i = 0; i < 3; i++) {
				if (linkparams[i] == null && r[i] != null) {
					linkparams[i] = r[i];
					clog("found " + i + " " + r[i]);
				}
			}
			if (linkparams[0] != null && linkparams[1] != null
					&& linkparams[2] != null)
				return linkparams;
		}
		return linkparams;
	}

	private void reparseLinksWithSuperClasses() {
		;
		if (LOG)
			clog("reparseLinkWithSuperClasses");
		for (DiaContainedElement diagramElement : diagramElements) {
			if (LOG)
				clog(diagramElement.getName());
			if (diagramElement instanceof DiaLink) {
				DiaLink link = (DiaLink) diagramElement;



				if (link.getSourceReference() == null
						|| link.getTargetReference() == null
						|| link.getContainmentReferenceBase() == null) {

					String[] repa = reparseWithUpperEClasses(link);
					EClass eclaz = (EClass) link.getEModelElement();
					if (link.getSourceReference() == null) {
						link.setSourceReference((EReference) eclaz
								.getEStructuralFeature(repa[0]));
					}
					if (link.getTargetReference() == null) {
						link.setTargetReference((EReference) eclaz
								.getEStructuralFeature(repa[1]));
					}
					if (link.getContainmentReferenceBase() == null) {
						String[] clz = repa[2].split("\\.");
						EReference s = link.getSourceReference();
						EClass et = (EClass) s.getEType();
						EPackage pak = et.getEPackage();
						EClass owner = (EClass) pak.getEClassifier(clz[0]);
						EReference cnt = (EReference) owner
								.getEStructuralFeature(clz[1]);
						link.setContainmentReferenceBase(cnt);
					}
					link.resolveSourceAndTarget();
				}
			}
		}
		// return linkparams;
	}

	private String getView(EAnnotation anot) {
		String view = "default";
		for (java.util.Map.Entry<String, String> entry : anot.getDetails()) {
			clog(entry.getKey());
			String k = entry.getKey();
			if (k.startsWith("view=")) {
				String[] vw = k.split("=");
				view = vw[1];
				break;
			}
			;
		}
		return view;
	}

	private List<EAnnotation> reparse(DiaLink link) {
		String theview = view;
		if (theview.isEmpty())
			theview = "default";
		List<EAnnotation> diagraphAnnots = new ArrayList<EAnnotation>();
		EClass eclaz = (EClass) link.getEModelElement();
		List<EClass> superclasses = eclaz.getEAllSuperTypes();
		for (EClass superclass : superclasses) {
			EList<EAnnotation> annots = superclass.getEAnnotations();
			for (EAnnotation eAnnotation : annots) {
				if (eAnnotation.getSource().equals("diagraph"))
					if (getView(eAnnotation).equals(theview))
						diagraphAnnots.add(eAnnotation);
			}

		}
		return diagraphAnnots;
	}

	private boolean isDefaultView(EAnnotation anot) {
		for (Map.Entry<String, String> entry : anot.getDetails())
			if (entry.getKey().startsWith("view="))
				return false;
		return true;
	}

	private boolean isView(EAnnotation anot, String view) { // FP131208

		if (!anot.getSource().equals("diagraph"))
			return false;
		if (view == null || view.equals(ViewConstants.DIAGRAPH_DEFAULT)
				|| view.isEmpty())
			return isDefaultView(anot);
		for (Map.Entry<String, String> entry : anot.getDetails())
			if (entry.getKey().equals("view=" + view))
				return true;
		return false;
	}

	@Override
	public boolean isPov(EClass eclaz) { // FP131209
		EList<EAnnotation> annots = eclaz.getEAnnotations();
		for (EAnnotation eAnnotation : annots) {
			if (isView(eAnnotation, view))
				for (Map.Entry<String, String> entry : eAnnotation.getDetails())
					if (entry.getKey().equals("pov"))
						return true;
		}
		return false;
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

	private boolean isPovOrSubclassPov(DiaNode nod) { // FP131209zz
		EClass eclaz = (EClass) nod.getEModelElement();
		boolean result = isPov(eclaz);
		if (result)
			return result;
		else
			for (EClass subclaz : subClasses(eclaz))
				if (isPov(subclaz))
					return true;
		return false;
	}

	private boolean isPov_old(DiaNode nod) { // FP131208

		EClass eclaz = (EClass) nod.getEModelElement();

		EList<EAnnotation> annots = eclaz.getEAnnotations();
		for (EAnnotation eAnnotation : annots) {
			if (isView(eAnnotation, view))
				for (Map.Entry<String, String> entry : eAnnotation.getDetails())
					if (entry.getKey().equals("pov"))
						return true;
		}
		return false;
	}

	private void parseAnnotationDetails(EAnnotation anot) {
		for (Map.Entry<String, String> entry : anot.getDetails()) {
			if (LOG)
				clog(entry.getKey()
						+ (entry.getValue() != null
								&& !entry.getValue().isEmpty() ? "->"
								+ entry.getValue() : ""));
		}
	}

	private String[] parseLink(EAnnotation eAnnotation) {
		String[] result = new String[3];
		for (java.util.Map.Entry<String, String> entry : eAnnotation
				.getDetails()) {
			String k = entry.getKey();
			if (k.startsWith(DiagraphKeywords.LINK_SOURCE_EQU)) {
				String[] vw = k.split("=");
				result[0] = vw[1];
			}

			if (k.startsWith(DiagraphKeywords.LINK_TARGET_EQU)) {
				String[] vw = k.split("=");
				result[1] = vw[1];
			}

			if (k.startsWith(DiagraphKeywords.CONTAINMENT_EQU)) {
				String[] vw = k.split("=");
				result[2] = vw[1];
			}

		}
		return result;
	}

	private void resolveLinkSourceAndTarget() {
		for (DiaContainedElement del : diagramElements) {
			if (del instanceof DiaLink) {
				DiaLink l = (DiaLink) del;
				l.resolveSourceAndTarget();
				if (l.getSourceNode() == null || l.getTargetNode() == null)
					if (LOG)
						clog("!!!!!!!!!!!!!!!!! resolveLinkTargets for " + l);
			}
		}
	}

	public String toString() {
		String result = "[Diagram]\n";
		for (DiaContainedElement el : diagramElements) {
			result += el.toString() + "\n";
		}
		result += "[End Diagram]";
		return result;
	}

	private DiaGenericElement getGenericElement(EClass element) {
		DiaContainedElement delement = findDiagramElement_(element);
		if (delement != null)
			if (!(delement instanceof DiaGenericElement)
					&& (delement instanceof DiaContainedElement))
				throw new RuntimeException(
						"should not happen: DiaGenericElement getGenericElement()");
		if (delement == null)
			delement = createGenericElement(element);
		return (DiaGenericElement) delement;
	}

	@Override
	public DiaNode getNode(EModelElement element) {
		DiaNode result = null;
		DiaContainedElement delement = findDiagramElement_(element);
		if (delement != null) {
			if (!(delement instanceof DiaNode)
					&& (delement instanceof DiaContainedElement)) {
				delement = DiaNode.cloneNode(this, view, delement);
				nodesdirty = true;
			}
			result = (DiaNode) delement;
		} else {
			result = createNode(element);
		}
		return result;
	}

	// key.equals(LINK)
	@Override
	public DiaLink getNamedLink(EClass element, String target, String label) {
		DiaLink result = null;
		DiaContainedElement delement = findDiagramElement_(element);
		if (delement != null) {
			if (!(delement instanceof DiaLink)
					&& (delement instanceof DiaContainedElement)
					&& !(delement instanceof DiaNode)) // FP120106
				delement = DiaLink.cloneLink(this, view, delement);
			if (!(delement instanceof DiaNode))
				result = (DiaLink) delement;
		} else {
			result = createLink(element);
		}
		// if (l != null && name != null)
		// l.setDefaultName(name);
		if (result != null) {
			if (label != null) {
				if (result.getLabels().isEmpty())
					result.getLabels().add(label); // FP130121
				else
					result.getLabels().set(0, label);
			}
			if (target != null)
				result.setTarget(target); // FP150523
		}
		return result;
	}

	// key.equals(LINK_SOURCE)
	public DiaLink getLinkWithSource(EModelElement element, String source) {
		DiaLink link = getNamedLink((EClass) element, null, null);
		link.setSource(source);
		return link;
	}

	// key.equals(LINK_TARGET)
	@Override
	public DiaLink getLinkWithtarget(EModelElement element, String target,
			String label) {
		DiaLink link = getNamedLink((EClass) element, target, label); // FP1212008
		return link;
	}

	// key.equals(CONTAINMENT)
	public DiaContainedElement getContainedElement(EModelElement element,
			String containementName) {
		DiaContainedElement del = findDiagramElement_(element);

		if (del == null)
			del = createGenericElement(element);

		if (containementName != null)
			del.setContention_(containementName);
		return del;
	}

	public DiaContainedElement getGenericElement(EModelElement element) {
		DiaContainedElement del = findDiagramElement_(element);
		if (del == null)
			del = createGenericElement(element);
		return del;
	}

	// key.equals(LABEL)
	@Override
	public DiaContainedElement getLabelledElement(EModelElement container,
			String attributeName, String name) {
		// EClass claz = (EClass) container;
		DiaContainedElement del = findDiagramElement_(container);
		if (del == null)
			del = createGenericElement(container);
		if (del.getOwnLabel(attributeName) == null) {

			List<String> attnames = splitAttributeNames(attributeName); // FP140220

			createLabels_(del, name, attributeName, attnames.size() > 1);
		}
		return del;
	}

	// key.equals(LABELS)
	@Override
	public DiaContainedElement getLabelledElements(EModelElement container,
			String attributeNames, String name) {

		DiaContainedElement del = findDiagramElement_(container);
		if (del == null)
			del = createGenericElement(container);

		if (del.getOwnLabels_(attributeNames) == null) {
			createLabels_(del, name, attributeNames, true);
		}
		return del;
	}

	private void cloglabels(String mesg) {
		if (DParams.COMPOSITE_LABELS_LOG_)
			System.out.println(mesg);
	}

	private static char[] toArray(String s) {
		char[] result = new char[s.length()];
		for (int i = 0; i < s.length(); i++)
			result[i] = s.charAt(i);
		return result;

	}

	private static char[] SEPS = toArray(DParams.LABEL_SEPARATORS);

	private List<String> splitAttributeNames(String names) {
		StringBuffer buf = new StringBuffer();
		List<String> atrs = new ArrayList<String>();
		List<String> seps = new ArrayList<String>();
		char[] cs = toArray(names);
		for (char c : cs) {
			for (char l : SEPS) {
				if (l == c) {
					atrs.add(buf.toString());
					seps.add(new StringBuffer().append(c).toString());
					buf = new StringBuffer();
					break;
				}
			}
			if (DParams.LABEL_SEPARATORS.indexOf(c) == -1)
				buf.append(c);
		}
		atrs.add(buf.toString());
		return atrs;
	}

	void checkContainmentReferenceCorrectness(EClass claz, String refname) {
		EList<EReference> erefs = claz.getEReferences();
		for (EReference eReference : erefs) {
			if (eReference.getName().equals(refname)) {
				if (eReference.isContainment())
					throw new RuntimeException("reference "
							+ claz.getName()
							+ "." // FP130929
							+ eReference.getName()
							+ " should not be a containment");
				// assert !eReference.isContainment() : "reference " +
				// eReference.getName() + " should not be a containment";
			}
		}
	}

	private void checkStyleCorrectness(EClass element, String name) {
		// TODO

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

	private void checkLinkCorrectness(EClass claz, String name) {
		EList<EReference> allrefs = claz.getEAllReferences();
		for (EReference ref : allrefs) {
			if (ref.getName().equals(name))
				return;

		}
		throw new RuntimeException("reference " + name + " does not exist for "
				+ claz.getName() + " !!!");
	}

	// key.equals(REFERENCE)
	@Override
	public DiaGenericElement getGenericElementWithReference(
			EModelElement element, String ref, String name) {
		if (name == null)
			name = ref;
		clog(this.getClass().getSimpleName()
				+ ".getGenericElementWithReference - "
				+ ((ENamedElement) element).getName() + " _ " + name);
		checkContainmentReferenceCorrectness((EClass) element, name);
		DiaGenericElement gel = getGenericElement((EClass) element);
		DiaReference reference = gel.findReference(name);
		if (reference == null) {
			reference = createGenericElementAndReference_(gel, ref, name);
			if (LOG) {
				clog("DiaReference created (6) (generic to be propagated): "
						+ gel.getName() + "." + reference.getName());
			}
		}
		// later would be propogated to a concrete node
		return gel;
	}

	// key.equals(REFERENCE)
	public DiaNode getNodeWithReference_(EModelElement element, String ref,
			String name) {
		if (name == null)
			name = ref;
		createdReferences_.clear();
		checkContainmentReferenceCorrectness((EClass) element, name);
		DiaNode nod = getNode(element);
		DiaReference exists = nod.findReference(name);
		boolean propagated = false; // FP150516
		if (exists == null)
			createNodeReference_(nod, ref, propagated, name);
		createdReferences_.add(exists);
		return nod;
	}

	@Override
	public DiaNode getNodeWithStyle(EModelElement element, String style,
			String name) { // FP120715
		if (name == null)
			name = style;
		checkStyleCorrectness((EClass) element, name);
		DiaNode nod = getNode(element);
		// not handled here
		return nod;
	}

	private DiaLabel createLabels_(DiaContainedElement del, String name,
			String attributeNames, boolean multiple) {
		DiaLabel lb = new DiaLabel(del, attributeNames, multiple, false);
		if (name != null)
			lb.setDefaultName(name);
		del.addOwnLabel(lb);
		return lb;
	}

	// key.equals(CANVAS)
	@Override
	public DiaNode getCanvas(EModelElement element) {// , String nodeLabels) {
		DiaNode nod = null;// getNamedNode_(eclaz, null); //FP140521
		if (canvasNode != null && canvasNode.getEModelElement() == element
				&& canvasNode.getDepth() == -1)
			nod = canvasNode;
		else
			nod = getNamedNode(element, null);
		if (nod == null)
			throw new RuntimeException("no canvas");
		if (this.canvasNode != null && canvasNode != nod)
			throw new RuntimeException("(2) Trying to set a canvas: " // FP140521xxxx
					+ nod.getName()
					+ " but a Canvas node already exists: "
					+ canvasNode.getName() + " !!!!");
		nod.setCanvas_(true);
		this.canvasNode = nod;
		pointOfView.setDNode(nod);
		if (LOG)
			clog("canvas is " + nod.getName());
		return nod;
	}

	// key.equals(NODE)
	@Override
	public DiaNode getNamedNode(EModelElement element, String nodeLabel) {


		DiaNode nod = getNode(element);
		if (nodeLabel != null && nodeLabel.isEmpty()) {// FP140429
			if (nod != null && nodeLabel != null) {
				if (nod.getLabels().isEmpty())
					nod.getLabels().add(nodeLabel); // FP130121
				else
					nod.getLabels().set(0, nodeLabel);
			}
		}
		return nod;
	}

	@Override
	public IDiaPlatformDelegateProvider getPlatformDelegateProvider() {
		return platformDelegateProvider;
	}

	private IDiaContainment findContainment(String hostName,
			String containmentName) {// , boolean figureEmbedded
		for (IDiaContainment c : containments_) {
			if (containmentName.equals(c.getName())
					&& (hostName.equals(c.getDeferredHost()) || hostName
							.equals(c.getSourceNode().getName()))) {
				/*
				 *
				 * if (c.isFigureEmbedded() != figureEmbedded) {
				 * c.setError(" embedded mode must be uniform...."); throw new
				 * RuntimeException(c.getError()); }
				 */

				return c;
			}
		}
		return null;
	}

	private IDiaContainment findContainment(String name) {// ,boolean
															// figureEmbedded
		for (IDiaContainment c : containments_) {
			if (name.equals(c.getName())) {
				/*
				 * if (c.isFigureEmbedded() != figureEmbedded) {
				 * c.setError(" embedded mode must be uniform...."); throw new
				 * RuntimeException(c.getError()); }
				 */
				return c;
			}
		}
		return null;
	}

	@Override
	// FP2612
	public List<DiaNode> getAllNodes() {
		if (allNodes == null || nodesdirty) {
			allNodes = new ArrayList<DiaNode>();
			for (DiaContainedElement elem : diagramElements)
				if (elem instanceof DiaNode)
					allNodes.add((DiaNode) elem);
		}
		nodesdirty = false;
		return allNodes;
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public void addErrorMessage(String message) {
		errorMessage += message;
	}

	@Override
	public boolean isDianodeOrRecursiveCanvasOrDialinkOrGeneric(
			DiaContainedElement diagramElement) {
		boolean dianode = diagramElement instanceof DiaNode;
		boolean diaNodeAndRecursivecanvas = dianode
				&& (((DiaNode) diagramElement).isCanvas() && ((DiaNode) diagramElement)
						.isDiagramRecursive());
		boolean canvas = dianode && ((DiaNode) diagramElement).isCanvas();
		boolean diaNodeAndNotcanvas = dianode
				&& !((DiaNode) diagramElement).isCanvas();
		boolean diaNodeOrRecursiveCanvas = diaNodeAndRecursivecanvas
				|| diaNodeAndNotcanvas;
		boolean dialink = diagramElement instanceof DiaLink;
		boolean diageneric = diagramElement instanceof DiaGenericElement;
		return (diaNodeOrRecursiveCanvas || dialink || (diageneric && !canvas));
	}

	public boolean isInContainment_nu(DiaContainedElement element,
			IDiaContainment containment) {
		// assert element != null : "element should not be null";
		if (element == null)
			throw new RuntimeException(" element should not be null!!!");

		boolean result = element == containment.getTargetNode();
		if (!result) {
			EReference targr = findReference__nu(containment
					.getNameIfCompartment());
			EClass tclaz = (EClass) targr.getEType();
			DiaContainedElement el = findDiagramElement(tclaz);
			result = element == el;

			// result = element == findDiagramElement((EClass)
			// findReference_(containment.getTargetName()).getEType());
		}
		return result;
	}

	// @Override
	private IDiaContainment findContainmentByTarget_nu(
			DiaContainedElement element) {
		for (IDiaContainment containment : getContainments()) {
			if (isInContainment_nu(element, containment))
				return containment;
		}
		return null;
	}

	private EReference findReference__nu(String refname) { // FP120106 pb,
															// revoir,
															// not unique
		throw new RuntimeException("should not call findReference_");
		/*
		 * for (EModelElement eModelElement : getModelElements()) { if
		 * ((eModelElement instanceof EReference) && ((ENamedElement)
		 * eModelElement).getName() .equals(refname)) return (EReference)
		 * eModelElement; }
		 *
		 * return null;
		 */
	}

	private EReference findReference(EClass claz, String target) {
		for (EReference ref : claz.getEAllReferences()) {
			if (ref.getName().equals(target))
				return ref;
		}
		return null;
	}

	@Override
	public IDiaContainment createContainment_(
			IDiagraphAssociation.AssociationType containmentType,
			EClass element, EClass targetClass, String target,
			String containmentName, // boolean
			// isPort,
			boolean propagated, boolean doit) {
		IDiaContainment result = null;
		switch (containmentType) {
		// case CONTAINMENT://FP150512
		case SHARED_COMPARTMENT_: // FP150512
			result = createNodeContainment(element, targetClass, target,
					containmentName, false, propagated, true, doit);
			break;
		case TYPED_COMPARTMENT:
			result = createNodeCompartment_(element, target, containmentName,
					propagated, true, doit);
			break;
		case AT_PORT:
			result = createNodeContainment(element, targetClass, target,
					containmentName, true, propagated, true, doit);
			break;
		default:
			break;
		}
		return result;
	}

	private DiaReference createGenericElementAndReference_(
			DiaGenericElement owner, String target, String name) {
		DiaReference reftoadd = new DiaReference(null, false,
				platformDelegateProvider);
		reftoadd.setDefaultName(name);
		reftoadd.setTargetName(target);
		owner.addReference(reftoadd);
		return reftoadd;
	}

	private DiaReference createNodeReference_(DiaNode owner, String target,
			boolean propagated, String name) {
		DiaReference reftoadd = new DiaReference(owner, propagated,
				platformDelegateProvider);
		reftoadd.setDefaultName(name);
		reftoadd.setTargetName(target);
		owner.addReference(reftoadd);
		return reftoadd;
	}

	// TODO FP2910
	private DiaReference createNodeReferenceAndResolve_(DiaNode owner,
			String target, boolean propagated, String name) {
		DiaReference reftoadd = new DiaReference(owner, propagated,
				platformDelegateProvider);
		reftoadd.setDefaultName(name);
		reftoadd.setTargetName(target);
		owner.addReference(reftoadd);
		reftoadd.resolveTargetRef();
		reftoadd.resolveTargetNode_();
		return reftoadd;
	}

	private IDiaContainment createNodeContainmentOnAbstract_(EClass element,
			EClass targ, EReference erf, String target, String typname,
			int order, boolean isPropagated, boolean dummy) {

		if (DParams.COMPOSITE_LOG)
			clog("createNodeContainmentOnAbstrac typname=" + typname + " - "
					+ target);
		List<IDiaContainment> result = new ArrayList<IDiaContainment>();
		if (typname == null) // default shared containment name is source name;
								// in
								// case of
								// compartment: target;
			typname = element.getName();

		if (isRecursiveContainement(element, target)) {
			return createRecursiveContainment(element, target, targ.getName(),
					typname, order, isPropagated, false, true, dummy);

		} else
			return createBaseContainment____(element, targ, erf, target,
					typname, order, isPropagated, false, dummy);

	}

	@Override
	/**
	 *
	 * @param compartment
	 * @param port
	 * @return
	 */
	public DiaContainment createContainment(boolean propagated,
			boolean compartment, boolean port, String refname, String typName,
			// IDiaNode targetTypeNode,
			String targetName, IDiaNode sourceNode, boolean dummy) {// boolean
																	// figureEmbedded,

		if (DParams.COMPOSITE_LOG)
			clog("createContainment " + " type "
					+ (compartment ? " compartment " : " shared ") + "typname="
					+ typName + " - target=" + refname);
		DiaContainment comptm = new DiaContainment(this, getParser2(),
				propagated, compartment, port, refname, typName,
				// targetTypeNode,
				targetName, sourceNode, dummy);
		containments_.add(comptm);
		return comptm;
	}

	private IDiaContainment createNodeContainmentOnConcrete(EClass element,
			EClass targetClass, String target, String typname, int order,
			boolean isPropagated, boolean dummy) {

		if (DParams.COMPOSITE_LOG)
			clog("createNodeContainmentOnConcrete typname=" + typname
					+ " - target=" + target);
		if (typname == null) // compartment name
			typname = target;

		DiaContainment containment = null;
		if (isRecursiveContainement(element, target))
			containment = createRecursiveContainment(element, target,
					targetClass.getName(), typname, order, isPropagated, false,
					true, dummy); // FP150516z
		else
			containment = createBaseContainmentOnConcrete(element, targetClass,
					target, typname, order, isPropagated, false, true, dummy);
		return containment;
	}



	private IDiaContainment createNodeCompartment(EClass element, EClass targ,
			EReference erf, String targetRel, String typName,
			boolean figureEmbedded, int order, boolean isPropagated,
			boolean dummy) {
		if (DParams.COMPOSITE_LOG)
			clog("createNodeCompartment typname=" + typName + " - target="
					+ targetRel);

		boolean knameinferred = false;
		boolean kcreated = false;
		// String targetNode = targ.getName();

		if (LOG && typName == null) // compartment name
			throw new RuntimeException(
					"TODO refactor in createNodeCompartmentOnAbstract");
		DiaContainment containment = null;
		if (false && isRecursiveCompartment(element, targetRel)){ //FP150601voir
			 boolean compartment_yes = true;
			 boolean contain_yes=true;

			containment = createRecursiveContainment(element, targetRel,
					targetRel, typName, order, isPropagated, compartment_yes, contain_yes, dummy);
		}
		else
			// containment = createBaseCompartmentOnAbstract(element, targ, erf,
			// targetRel, typName, order, isPropagated);
			containment = createBaseCompartment(element, targ, erf, targetRel,
					typName, order, isPropagated, dummy);
		return containment;
	}

	/*
	 * private IDiaContainment createNodeCompartmentOnAbstractOld_nu( EClass
	 * element, EClass targ, EReference erf, String targetRel, String typName,
	 * boolean figureEmbedded, int order, boolean isPropagated) { boolean
	 * knameinferred = false; boolean kcreated = false; // String targetNode =
	 * targ.getName();
	 *
	 * if (LOG && typName == null) // compartment name throw new
	 * RuntimeException( "TODO refactor in createNodeCompartmentOnAbstract");
	 * DiaContainment containment = null; if (isRecursiveCompartment(element,
	 * targetRel)) containment = createRecursiveContainment(element, targetRel,
	 * targetRel, typName, order, isPropagated, true,true); else // containment
	 * = createBaseCompartmentOnAbstract(element, targ, erf, // targetRel,
	 * typName, order, isPropagated); containment =
	 * createBaseCompartment(element, targ, erf, targetRel, typName, order,
	 * isPropagated); return containment; }
	 */



	private DiaContainment createBaseContainmentOnConcrete(EClass element,
			EClass targetClass, String refName, String typName, int order,
			boolean isPropagated, boolean compartment, boolean contain,
			boolean dummy) { // FP140330

		DiaNode dnode = getNode(element);
		DiaNode tnode = getNode(targetClass);

		// DiaNode typnode = getNode(typName);

		DiaContainment containment = createContainment(isPropagated, // true,
				compartment, false, refName, typName, typName, dnode, dummy); // FP150517
		// //
		// !DParams.NO_COMPARTMENTS);

		containment.setTargetNode(tnode);

		dnode.parserAddContainment1(containment);
		dnode.addContainment(4, containment, order, true);
		if (DParams.Parser_15_LOG) {
			EClass src = dnode.getSemanticRole();
			clog15("createBaseContainmentOnConcrete " + src.getName());
		}

		return containment;
	}

	private DiaNode getNode(String name) {
		if (name == null)
			return null;
		List<DiaNode> nods = getAllNodes();
		for (DiaNode diaNode : nods) {
			if (diaNode.getName().equals(name))
				return diaNode;
		}
		return null;
	}

	private IDiaContainment createBaseContainment____(EClass element,
			EClass targ, EReference eref, String target_, String typname,
			int order, boolean isPropagated, boolean compartment__nu,
			boolean dummy) {
		// DiaNode targnode = getNode(targ);
		if (DParams.Parser_15_LOG)
			clog15("createBaseContainment " + "typname=" + typname + " "
					+ element.getName() + "->" + targ.getName());
		IDiaContainment containment = null;
		if (isRecursiveContainement(element, target_))
			containment = createRecursiveContainment(element, target_,
					targ.getName(), typname, order, isPropagated, false, true,
					dummy);
		else
			containment = createBaseContainmentOnConcrete(element, targ,
					target_, typname, order, isPropagated, false, true, dummy);
		return containment;
	}

	private DiaContainment createBaseCompartment(EClass element, EClass targ,
			EReference eref, String refName, String typName, int order,
			boolean isPropagated, boolean dummy) {
		boolean compartment = true; // FP150429
		if (DParams.Parser_15_LOG)
			clog15("createBaseCompartment " + "typname=" + typName + " "
					+ element.getName() + "->" + targ.getName());
		DiaNode dnode = getNode(element);
		// DiaNode typnode = getNode(typName);

		DiaContainment containment = createContainment(isPropagated, // true,
				compartment, false, refName, targ.getName(), // typnode,
				targ.getName(), dnode, dummy);// FP150517 //
												// !DParams.NO_COMPARTMENTS);
		DiaNode tarn = getNode(targ);// FP150329a117
		containment.setTargetNode(tarn); // FP150329a117
		containment.setTargetReference(eref);
		dnode.parserAddContainment1(containment);
		if (containment.getTargetReference() == null)
			containment.setTargetReference(eref); // FP150329a114
		dnode.addContainment(4, containment, order, true);
		if (DParams.Parser_15_LOG)
			clog15("createBaseContainment " + dnode.getSemanticRole().getName());
		return containment;
	}

	private DiaContainment createRecursiveContainment(
			EClass element,// EClass targetClass,
			String targetRel, String targetNode, String typName, int order,
			boolean isPropagated, boolean compartment, boolean contain,
			boolean dummy) {
		if (DParams.COMPOSITE_LOG)
			clog("createRecursiveContainment " + typName + " - " + targetNode
					+ (compartment ? "compartment" : "shared"));

		boolean notport = false;
		boolean check;
		DiaNode dnode = getNode(element);
		EReference erf = (EReference) element.getEStructuralFeature(targetRel);
		DiaNode tnode = getNode(erf.getEType()); // FP150506
		// DiaNode typnode = getNode(typName);
		DiaContainment containment = createContainment(
				isPropagated, // figureEmbedded,
				compartment, notport, targetRel, typName, targetNode, dnode,
				dummy);// FP150517
		containment.setTargetNode(tnode);
		dnode.addContainment(5, containment, order, true);
		EReference dc = dnode.getDeclaredContainment(); // FP140408
		if (dc == null)
			check = true; // FP140329
		if (DParams.Parser_15_LOG) {
			clog15("createRecursiveContainment "
					+ dnode.getSemanticRole().getName());
			clog15(containment.toLog()
					+ (dc == null ? "" : (" dc=" + dc.getName())));
		}
		return containment;
	}

	private boolean hasKrefDirect(EClass eModelElement, String view, String name) {
		return notation.getParser().isAnnotated(eModelElement, name,
				IDiagraphAssociation.AssociationType.TYPED_COMPARTMENT,
				IDiagraphParser.InheritanceType.DIRECT);
	}

	private boolean hasCrefDirect(EClass eModelElement, String view, String name) {
		return notation.getParser().isAnnotated(eModelElement, name,
				IDiagraphAssociation.AssociationType.SHARED_COMPARTMENT_,
				IDiagraphParser.InheritanceType.DIRECT);
	}

	private boolean isRecursiveContainement(EModelElement element, String target) { // FP140329
		// IDiagraphAnnotationUtils u = DiagraphAnnotationUtils.getInstance();
		if (hasCrefDirect((EClass) element, view, target)) {
			List<IDiagraphReferenceAssociation> associations = notation
					.getParser().getContainmentAssociationsAndSubHierTo(
							(EClass) element);
			for (IDiagraphReferenceAssociation association : associations) {
				if (LOG)
					clog(association.toString());
				if (association.getTargetReference().getName().equals(target)) {
					if (association.getSource() == element) {
						association.setRecursive();
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean isRecursiveCompartment(EModelElement element, String target) { // FP140329
		// IDiagraphAnnotationUtils u = DiagraphAnnotationUtils.getInstance();
		if (hasKrefDirect((EClass) element, view, target)) {
			List<IDiagraphReferenceAssociation> associations = notation
					.getParser().getContainmentAssociationsAndSubHierTo(
							(EClass) element);
			for (IDiagraphReferenceAssociation association : associations) {
				if (LOG)
					clog(association.toString());
				if (association.getTargetReference().getName().equals(target)) {
					if (association.getSource() == element) {
						association.setRecursive();
						return true;
					}
				}
			}
		}
		return false;
	}

	private DiaLink createNodeLink(EModelElement element, String target,
			String label) { // FP120106
		DiaLink lnk = null;
		EClass claz = (EClass) element;
		EReference ref = findReference(claz, target);
		EClass targetClass = (EClass) ref.getEType();
		if (targetClass == null)
			throw new RuntimeException(" error while creating link " + target
					+ " , no reference " + target + " !!!!");
		if (targetClass.getEAllReferences().size() > 1) {
			// net nes voir ici
			EList<EReference> refs = targetClass.getEAllReferences();
			if (LOG)
				for (EReference ref_ : refs)
					clog(ref_.getName());
		}
		if (targetClass.getEAllReferences().size() > 1
				|| targetClass.getEAllReferences().size() == 0)
			throw new RuntimeException(
					" error while creating link "
							+ target
							+ " , 1) more than one target, must specify |or| 2) remove lnk annotation in source metaclass !!!!");
		if (targetClass != null) {
			lnk = getLinkWithtarget(targetClass, targetClass
					.getEAllReferences().get(0).getName(), label); // FP121008
			DiaNode dnode = getNode(element);
			dnode.addLink(lnk);
			lnk.setSourceNode(dnode);
			if (LOG)
				clog("createNodeLink :" + lnk.getName());
		}
		return lnk;
	}

	// key.equals(COMPARTMENT)
	// TODO FP2910
	@Override
	public IDiaContainment getNamedContainment(EModelElement element,
			String containmentName, boolean isPropagated) {// boolean
		boolean dummy = true; // figureEmbedded,
		IDiaContainment containment = null;
		boolean knameinferred = false;
		boolean kcreated = false;
		// boolean isPropagated =false;
		String kname = containmentName;
		DiaNode dnode = getNode(element);
		if (containmentName != null)
			containment = findContainment(containmentName);// , figureEmbedded
		String typName = "";

		if (containment == null) { // FP150517
			containment = createContainment(isPropagated,
					true,// true,// figureEmbedded,
					false, containmentName != null ? containmentName : null,
					typName, null, dnode, dummy);// !DParams.NO_COMPARTMENTS);
			// if (containmentName != null)
			// containment.setName(containmentName);
			// containment.setTargetName(target);

			kcreated = true;
		}
		// containment.setSourceNode(dnode); // TODO FP2910 vérif
		EClass src = dnode.getSemanticRole();
		if (DParams.Parser_CONTAINMENT1_LOG)
			System.out.println("getNamedContainment " + src.getName());

		dnode.addContainment(7, containment, -1, true);// containedCompartment
														// =
														// c;
		EReference dc = dnode.getDeclaredContainment(); // FP140408
		if (LOG)
			DiaUtils.log("getNamedContainment", kname, kcreated, knameinferred,
					containment, dnode);
		return containment;
	}

	// key.equals(IN)
	@Override
	public IDiaContainment getContainerContainment(EModelElement element,
			String containmentName, boolean isPropagated) {// boolean
															// figureEmbedded,
		boolean dummy = true;
		boolean knameinferred = false;
		boolean kcreated = false;
		// boolean isPropagated = false;
		IDiaContainment containment = null;
		DiaNode dnode = getNode(element);
		String hostName = null;
		String kname = containmentName;
		if (containmentName.contains(".")) {
			// if (containmentName.contains("third"))
			// tb = true;

			hostName = containmentName.split("\\.")[0];
			containmentName = containmentName.split("\\.")[1];
			if ("*".equals(containmentName)) {
				containmentName = ((ENamedElement) element).getName();// + "K";
				if (KSUFFIX)
					containmentName += KSUFFIX_LITTERAL;
				// TODO FP3010 "s"->"K"
				knameinferred = true;
			}
			containment = findContainment(hostName, containmentName);
		} else
			containment = findContainment(containmentName);
		String typName = "";

		if (containment == null) {
			kcreated = true;
			containment = createContainment(isPropagated, true, false,
					containmentName, typName, null, null, dummy); // FP150517 //
			// !DParams.NO_COMPARTMENTS);
			// containment.setName(containmentName);
			if (hostName != null)
				containment.setDeferredHost(hostName);
		}
		containment.setTargetNode(dnode);
		DiaUtils.log("getContainerCoontainment", kname, kcreated,
				knameinferred, containment, dnode);
		return containment;
	}

	// key.equals(LNK)
	@Override
	public DiaNode getNodeWithLink(EModelElement element, String target,
			String label) {
		// if (name_ == null)
		// name_ = target;
		checkLinkCorrectness((EClass) element, target);
		DiaNode nod = getNode(element);
		DiaLink exists = nod.findLinkByTargetName(target);// name_);

		EClass claz = (EClass) element;
		EReference ref = findReference(claz, target);
		EClass targetClass = (EClass) ref.getEType();
		if (targetClass == null)
			throw new RuntimeException(" error while creating link " + target
					+ " , no reference " + target + " !!!!");

		DiaContainedElement other = findDiagramElement(targetClass);

		DiaLink added = null;
		if (exists == null)
			added = createNodeLink(element, target, label);
		if (added == null && exists == null)
			throw new RuntimeException(" error on link creation: " + target);
		return nod; // FP120106
	}

	@Override
	public IDiaContainment createMissingCReference(EReference cref,
			boolean isPropagated) {
		IDiaContainment result = null;
		EClass element = (EClass) cref.eContainer();
		String target = cref.getName();
		String name = cref.getName();
		checkKReferenceCorrectness((EClass) element, target);
		DiaNode nod = getNode(element);
		DiaNode targ = getNode(cref.getEType());
		result = nod.findContainmentByTargetRefName(name); // FP150515voir
		if (result == null) {
			result = createNodeContainment(element, targ.getEClass(), target,
					name, false, isPropagated, false, false);
			if (LOG)
				clog("cref " + result.getName() + " created");
		} else if (LOG)
			clog("cref " + result.getName() + " allready exists");
		result.setTargetReference(cref);
		return result;
	}

	// key.equals(CONTAINMENT)
	// @Override
	private DiaNode getNodeWithCanvasContainment_nu(EModelElement element,
			String target, String name, boolean figureEmbedded, boolean clone) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DiaNode getNodeWithCReference(EClass element, String target,
			EClass targ_, boolean figureEmbedded, boolean propagated) {
		boolean dummy = false;
		// default shared containment name //in case of compartment: target;
		String typename = element.getName();
		int caze = 1;
		createdContainments_.clear();
		EClass claz = (EClass) element;
		EReference ref = (EReference) claz.getEStructuralFeature(target);
		EClass refsource = (EClass) ref.eContainer();
		EClass reftarget_abstr = (EClass) ref.getEType();
		DiaNode nod = getNode(claz);
		EStructuralFeature esf_ = (element).getEStructuralFeature(target);
		DiaNode containernode = findNode((EClass) ((EReference) esf_)
				.eContainer());
		DiaNode targnod = getNode(targ_);// FP150502
		int order = propagated ? (nod.getDepth() - findNode(refsource)
				.getDepth()) : 0;
		if (DParams.COMPOSITE_LOG)
			clog("getNodeWithCReference typename=" + typename + " "
					+ refsource.getName() + "-" + target + "->"
					+ targ_.getName());
		if (reftarget_abstr.isAbstract()) {
			if (reftarget_abstr.isSuperTypeOf(refsource) && isPov(refsource)) {
				caze = 2; // FP150429
				targ_ = refsource;
				if (DParams.COMPOSITE_LOG)
					clog("CAZE 2");
				IDiaContainment exists = nod.findContainmentByName(
						refsource.getName(), target, targ_.getName());
				if (exists == null) {
					exists = createNodeContainmentOnConcrete(claz, targ_,
							target, typename, order, propagated, dummy);
					createdContainments_.add(exists);
				}

			} else {
				caze = 3;
				if (DParams.COMPOSITE_LOG)
					clog("CAZE 3");
				IDiaContainment exists = nod.findContainmentByName(
						refsource.getName(), target, targ_.getName());
				if (exists == null) {
					exists = createNodeContainmentOnAbstract_(claz, targ_, ref,
							target, typename, order, propagated, dummy); // FP150329a111

					createdContainments_.add(exists);
				}
			}

		} else {
			caze = 4;
			if (DParams.COMPOSITE_LOG)
				clog("CAZE 4");
			targ_ = reftarget_abstr;
			IDiaContainment exists = nod.findContainmentByName(
					refsource.getName(), target, targ_.getName());
			if (exists == null) {

				exists = createNodeContainmentOnConcrete(claz, targ_, target,
						typename, order, propagated, dummy);
				createdContainments_.add(exists);
			}
		}
		if (DParams.Parser_15_LOG || DParams.Parser_29_LOG) {

			for (IDiaContainment c : createdContainments_) {
				logContainment(claz, targ_, target, propagated, c, caze);
			}
		}
		nod.setCase(caze);
		return nod;
	}

	// key.equals(PREFERENCE)
	@Override
	public DiaNode getNodeWithPReference(EClass element, EClass targetClass,
			String target, String refname, boolean propagated) { // FP130319ppp
		// createdContainment = null;
		createdContainments_.clear();
		if (refname == null)
			refname = target;
		checkKReferenceCorrectness((EClass) element, target);
		DiaNode nod = getNode(element);
		IDiaContainment exists = nod.findContainmentByTargetRefName(refname); // FP150517
																				// //
																				// wxcvs234
		if (DParams.Parser_CONTAINMENT2_LOG)
			clog2("GN_PREF " + ((EClass) element).getName() + " propagated "
					+ (propagated ? "yes " : "no ") + " name=" + refname
					+ (exists == null ? " cont not exists " : " cont exists "));

		if (exists == null)
			createdContainments_
					.add(createNodeContainment(element, targetClass, target,
							refname, true, propagated, true, false)); // FP140406
		return nod;
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

	private List<EClass> getConcreteETypes_old_nu(EReference ref,
			boolean containment) {
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

	@Override
	public List<EClass> getSubClasses(EReference ref, boolean containment) {
		List<EClass> result = new ArrayList<EClass>();
		EClass rtyp = (EClass) ref.getEType();
		if (!containment || containment && ref.isContainment()) {
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
		return result;
	}

	private List<EReference> getAllReferences(EPackage pack, boolean containment) {
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

	/*
	 * @Override public List<EClass> getConcreteEClasses(EReference ref, boolean
	 * containment) { // return getAllConcreteTargets((EClass) ref.getEType(),
	 * ref.getName()) //// ; //return getConcreteETypes(ref, containment);//
	 * FP150330a302 return getSubClasses(ref, containment);// FP150330a302
	 *
	 * }
	 */

	private EClass getFirstConcreteTarget(EClass claz, String target) { // FP150329a112
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

	private void logContainment(EClass claz, EClass targ, String target,
			boolean propagated, IDiaContainment cont, int caze) {

		String typname = cont == null ? "" : cont.getNameIfShared();
		String compname = cont == null ? "" : cont.getNameIfCompartment();

		clog15("(b)GN_KREF_" + caze + " " + claz.getName() + " -> " + target
				+ " (" + targ.getName() + ") " + " ["
				+ (propagated ? " ypropa " : " npropa ") + "typname=" + typname
				+ "compname=" + compname
				+ (cont == null ? " cont_not_exists " : " cont_yes_exists ")
				+ "]");
	}

	// key.equals(KREFERENCE)
	@Override
	public DiaNode getNodeWithKReference(EModelElement element,
			String targetRel, String argument2_, String __label_nu,
			EClass targ, boolean figureEmbedded, boolean propagated) { // FP150329a120

		boolean dummy = true;// yy5
		String typName = targ.getName();


		int caze = 1;
		createdContainments_.clear();
		EClass claz = (EClass) element;
		EReference ref = (EReference) claz.getEStructuralFeature(targetRel);
		EClass refsource = (EClass) ref.eContainer();
		EClass reftarget = (EClass) ref.getEType();
		checkKReferenceCorrectness(claz, targetRel);
		DiaNode nod = getNode(claz);
		DiaNode targnod = getNode(targ);// FP150502
		int order = propagated ? (nod.getDepth() - findNode(refsource)
				.getDepth()) : 0;
		IDiaContainment cont = nod.findContainmentByTargetName(
				targnod.getName(), targetRel); // FP121008x
		if (DParams.Parser_15_LOG)
			clog15("getNodeWithKReference typName=" + typName + " "
					+ refsource.getName()
					+ "-" // FP150526kkk
					+ targetRel + "." + targnod.getName() + "->"
					+ reftarget.getName());

		if (cont == null) { // FP150428zzz // FP150330a100
			if (reftarget.isAbstract()) {
				if (reftarget.isSuperTypeOf(refsource) && isPov(refsource)) {
					caze = 2; // FP150429
					targ = refsource;
					// cont = createNodeCompartmentOnConcrete(claz, targ,
					// targetRel,typName, figureEmbedded, order, propagated);
					cont = createNodeCompartment(claz, targ, ref, targetRel,
							typName, figureEmbedded, order, propagated, dummy);
				} else if (reftarget.isSuperTypeOf(refsource)) {
					caze = 5;
					cont = createNodeCompartment(claz, targ, ref, targetRel,
							typName, figureEmbedded, order, propagated, dummy); // FP150329a111
				} else {
					caze = 3;
					cont = createNodeCompartment(claz, targ, ref, targetRel,
							typName, figureEmbedded, order, propagated, dummy); // FP150329a111
				}

			} else {
				caze = 4;
				// targ = reftarget_; //FP150517voir
				cont = createNodeCompartment(claz, targ, ref, targetRel,
						typName, figureEmbedded, order, propagated, dummy);
			}

			if (argument2_ != null)
				cont.setArgument(argument2_); // FP150512transp1
			createdContainments_.add(cont);
		}
		if (DParams.Parser_15_LOG || DParams.Parser_29_LOG)
			logContainment(claz, targ, targetRel, propagated, cont, caze);
		nod.setCase(caze);
		return nod;
	}

	// KREF
	@Override
	public IDiaContainment createNodeCompartment_(EModelElement element,
			String target, String refname, boolean propagated, boolean doAdd,
			boolean derived) {
		boolean dummy = true;
		boolean knameinferred = false;
		boolean kcreated = false;
		if (refname == null) // compartment name
			refname = target;
		if (DParams.Parser_CONTAINMENT2_LOG) {
			String tocreeate = ((EClass) element).getName() + "." + target;
		}

		// if (DParams.RAISE_TODO)
		// if (isRecursiveCompartment(element, target)) // FP140329
		// throw new RuntimeException("TODO in createNodeContainment");
		DiaNode nod = getNode(element);
		IDiaContainment containment = nod == null ? null : nod
				.findContainmentByTargetRefName(target);

		if (DParams.Parser_15_LOG) {
			String t = ((EClass) element).getName() + "." + target;
			// if (doAdd)
			clog("-----(1)createNodeCompartment "
					+ ((EClass) element).getName()
					+ "."
					+ target
					+ " "
					+ (propagated ? " ypropa " : " not_propa ")
					+ (containment == null ? " cont not exixts "
							: " cont exists "));
			// else
			// clog("(" + ((EClass) element).getName() + "." + target
			// + " ok)");
		}
		if (containment != null)
			throw new RuntimeException("should not happen in ");
		DiaNode dnode = getNode(element);
		String typName = ""; // FP150517

		if (DParams.Parser_15_LOG)
			clog("typname=" + typName);

		containment = createContainment(propagated,// true,
				true, false, refname, typName, target, dnode, dummy);
		containment.setDerived(derived);
		dnode.addContainment(6, containment, -1, doAdd);
		EReference dc = dnode.getDeclaredContainment(); // FP140408
		if (LOG)
			DiaUtils.log("createNodeCompartment", refname, kcreated,
					knameinferred, containment, null);
		return containment;
	}

	// CREF || AFX
	@Override
	public IDiaContainment createNodeContainment(EClass element,
			EClass targClass, String target, String containmentName,
			boolean isPort, boolean propagated, boolean doAdd, boolean derived) {
		boolean knameinferred = false;
		boolean kcreated = false;
		boolean isCompartment = false;
		boolean dummy = true;
		boolean check;
		if (DParams.Parser_CONTAINMENT2_LOG) {
			String tocreeate = ((EClass) element).getName() + "." + target;
			clog2("tocreate " + tocreeate);
			if (tocreeate.equals("Component.requiredInterfaces"))
				check = true;
		}

		DiaNode nod = getNode(element);
		IDiaContainment containment = nod == null ? null : nod
				.findContainmentByTargetRefName(target);
		if (DParams.COMPOSITE_LOG) {
			// if (doAdd)
			clog("-----createNodeContainment "
					+ ((EClass) element).getName()
					+ "."
					+ target
					+ " "
					+ (propagated ? " ypropa " : " not_propa ")
					+ (isPort ? " yport " : " not_port ")
					+ (containment == null ? " cont not exixts "
							: " cont exists "));
			// else
			// clog2("(" + ((EClass) element).getName() + "." + target
			// + " ok)");
		}
		if (containment != null)
			throw new RuntimeException("should not happen in ");
		DiaNode dnode = getNode(element);
		String typName = targClass.getName();

		// DiaNode typnode = getNode(typName);
		containment = createContainment(
				propagated, // FP150517 // false,
				isCompartment, isPort, containmentName, typName, target, dnode,
				dummy);
		DiaNode tnode = getNode(targClass);
		containment.setTargetNode(tnode); // FP150505
		containment.setDerived(derived);
		dnode.addContainment(3, containment, -1, doAdd);
		EReference dc_nu = dnode.getDeclaredContainment(); // FP140408
		if (LOG)
			DiaUtils.log("createNodeContainment", containmentName, kcreated,
					knameinferred, containment, null);
		return containment;
	}

	private void clog2(String mesg) {
		if (DParams.Parser_CONTAINMENT2_LOG || DParams.Parser_15_LOG
				|| DParams.Parser_29_LOG)
			System.out.println(mesg);

	}

	private void clog15(String mesg) {
		if (DParams.Parser_15_LOG || DParams.Parser_29_LOG) {
			clog2(mesg);
			log += mesg + '\n';
		}
	}

	@Override
	public IDiaPointOfView getPointOfView() {
		return pointOfView;
	}

	/** ------------------------------- **/
	@Override
	public List<DiaContainedElement> getDiagramElements() {
		return diagramElements;
	}

	private void addDiagramElements_(List<DiaContainedElement> elements) {
		diagramElements.addAll(elements);
	}

	private void addDiagramElement_(DiaContainedElement element) {
		diagramElements.add(element);
	}

	private EClass getEcoreContainer(EClass eclaz) {
		for (EClassifier eClassifier : eclaz.getEPackage().getEClassifiers()) {
			if (eClassifier instanceof EClass)
				for (EReference otherclazref : ((EClass) eClassifier)
						.getEReferences()) {
					EClass otherClass = (EClass) otherclazref.getEType();
					if ((otherClass).isSuperTypeOf(eclaz)
							&& otherclazref.isContainment()) {
						return (EClass) eClassifier;
					}
				}
		}
		return null;
	}

	private List<DiaNode> getInheritedRepresentableNodes(DiaNode dNode) { // FP140114x
		List<DiaNode> result = new ArrayList<DiaNode>();
		EClass nclaz = (EClass) dNode.getSemanticRole();
		for (DiaNode no : getOtherNodes(dNode)) {
			EClass nsc = (EClass) no.getSemanticRole();
			if (nsc == null) {
				clog("getInheritedRepresentableNodes nosemantic role for "
						+ no.getName());
			}

			if (nsc != null && nsc != nclaz
					// zz && !nsc.isAbstract()
					&& getEcoreContainer(nsc) != null
					&& ((EClass) nclaz).isSuperTypeOf(nsc)) {
				clog(nsc.getName());
				result.add(no);
			}
		}
		return result;
	}

	private List<DiaNode> getOtherNodes(DiaNode dNode) { // FP140114x
		List<DiaNode> result = new ArrayList<DiaNode>();
		for (DiaContainedElement diaContainedElement : diagramElements) {
			if (diaContainedElement != dNode
					&& diaContainedElement instanceof DiaNode)
				result.add((DiaNode) diaContainedElement);
		}
		return result;
	}

	private boolean isAbstract(DiaNode el) { // FP140114x
		EModelElement me = el.getEModelElement();
		if (me instanceof EClass && ((EClass) me).isAbstract())
			return true;
		return false;
	}

	private boolean isRepresentable(DiaNode el) { // FP140114xx
		List<DiaNode> result = getInheritedRepresentableNodes(el);
		return !result.isEmpty();
	}

	private void checkContainer() {
		List<DiaNode> errors = new ArrayList<DiaNode>();
		List<DiaContainedElement> elems = diagramElements;
		Iterator<DiaContainedElement> it = elems.iterator();
		boolean contained = false;
		while (it.hasNext()) {
			DiaContainedElement each = it.next();
			EReference cref = (EReference) each.getContainmentReferenceBase();
			if (cref != null) { // FP140503 voir
				EClass cl = (EClass) (cref.getEContainingClass());
				DiaContainedElement containing = findDiagramElement(cl);
				if (!(containing instanceof DiaLink))
					contained = true;
			}
			if ((each instanceof DiaNode) && !contained
					&& !isPovOrSubclassPov((DiaNode) each)
					// && false
					&& !isRepresentable((DiaNode) each) // FP140115 xxyy
			)
				errors.add((DiaNode) each);

		}// while (it.hasNext()) {
		if (!errors.isEmpty()) {
			String msg = "no DNode container for ";
			for (DiaNode ern : errors) {
				msg += ern.getName() + ";";
			}
			cerror(msg);// FP140414
		}
	}

	/*
	 * if (!contained && (each instanceof DiaNode) &&
	 * !isPovOrSubclassPov((DiaNode) each)) // FP131209 throw new
	 * RuntimeException( "should not happen... in DiaParser.checkSyntax()\n" +
	 * " has not an DNode container: " + each.getName());
	 */

	private DiaNode createNode(EModelElement element) {
		DiaNode nod = new DiaNode(this, view, element);
		nod.setElementName(((EClass) element).getName());
		addDiagramElement_(nod);
		return nod;
	}

	private DiaLink createLink(EModelElement element) {
		DiaLink link = new DiaLink(this, view, element); // xyzx
		link.setElementName(((EClass) element).getName());
		addDiagramElement_(link);
		return link;
	}

	private DiaNode createDiaNode(EModelElement element) {
		DiaNode nod = new DiaNode(this, view, element);
		nod.setElementName(((EClass) element).getName());
		addDiagramElement_(nod);
		if (DParams.PARSER_ASSERT_NOT_ABSTRACT)
			if (nod.isAbstract())
				throw new RuntimeException("DiaNode should not be abstract "
						+ nod.getName());
			// assert !nod.isAbstract() : "DiaNode should not be abstract";
			else if (nod.isAbstract())
				if (LOG)
					clog("DiaNode is abstract: "
							+ ((ENamedElement) element).getName());
		return nod;
	}

	private void clog(String mesg) {
		if (DParams.COMPOSITE_LOG)// LOG
			System.out.println(mesg);

	}

	private DiaLink createDiaLink(EModelElement element) {
		DiaLink link = new DiaLink(this, view, element);
		link.setElementName(((EClass) element).getName());
		addDiagramElement_(link);
		return link;
	}

	@Override
	public DiaGenericElement createGenericElement(EModelElement element) {
		DiaGenericElement del = new DiaGenericElement(this, element, view);
		del.setElementName(((EClass) element).getName());
		addDiagramElement_(del);
		return del;
	}

	@Override
	public int getPhase() {
		return runner.getPhase();
	}

	/*---------------------------------------------------*/
	// @Override
	private void checkBeforeParsing_nu() {
		// getSyntaxRules().checkBeforeParsing_();
		checkBeforeParsing(); // FP150325z2
	}

	/*---------------------------------------------------*/
	@Override
	public void checkBeforeParsing() { // TODO: refactor null values in
		// EAnnotations
		for (Iterator<EObject> contents = domainModel.eAllContents(); contents
				.hasNext();) {
			EObject el = contents.next();
			if (el instanceof EReference) {
				EReference ref = (EReference) el;
				if (ref.getName() != null && ref.getName().trim().equals("")) {
					errorMessage += " A Reference has an empty name: "
							+ ((EClass) (ref.eContainer())).getName()
							+ " ----> " + ref.getEType().getName();
					throw new RuntimeException(errorMessage);
				}
			}
		}
	}

	@Override
	public EPackage getDomainModel() {
		return domainModel;
	}

	@Override
	public IDiagraphParser getParser2() {
		return notation.getParser();
	}

	@Override
	public DiaContainedElement getLabelledElement(EModelElement container,
			org.isoe.diagraph.internal.m2.parser.DGraphElementType elmtype,
			boolean elmPromoted, String attributeName, String name) {
		DiaContainedElement del = findDiagramElement_(container);
		if (del == null) {
			if (elmtype == DGraphElementType.GENERIC)
				del = createGenericElement(container);
			else if (elmtype == DGraphElementType.NODE)
				del = createDiaNode(container);
			else if (elmtype == DGraphElementType.LINK)
				del = createDiaLink(container);
			del.setPromoted(elmPromoted);
			if (elmPromoted)
				if (LOG)
					clog(del.getName() + " promoted=true");
		}

		if (del.getOwnLabel(attributeName) == null) {
			createLabels_(del, name, attributeName, false);
		}
		return del;
	}

	@Override
	public void cerror(String mesg) {
		runner.cerror(mesg);
		// System.err.println(mesg);
	}

	@Override
	public boolean isNodeInstanciable(EClass c) {
		return getParser2().isNodeInstanciable(c);
	}

	@Override
	public boolean isLinkInstanciable(EClass c) {
		return getParser2().isLinkInstanciable(c);
	}

	@Override
	public boolean isView(EClass c) {
		return getParser2().isCurrentView(c);
	}

	@Override
	public boolean isNode(EClass c) {
		return getParser2().isNode(c);
	}

	@Override
	public boolean isLink(EClass c) {
		return getParser2().isLink(c);
	}

	@Override
	public String getViewId() {
		return getParser2().getCurrentView();
	}

	@Override
	public boolean isCref(EReference ref) {
		return getParser2().isCref(ref);
	}

}
