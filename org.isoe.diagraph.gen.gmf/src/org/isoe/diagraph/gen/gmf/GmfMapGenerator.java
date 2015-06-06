/**
 * Copyright (c) 2014 Laboratoire de Genie Informatique et Ingenierie de Production - Ecole des Mines d'Ales
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse private License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Francois Pfister (ISOE-LGI2P) - initial API and implementation
 */
package org.isoe.diagraph.gen.gmf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.gmf.gmfgraph.Canvas;
import org.eclipse.gmf.gmfgraph.Compartment;
import org.eclipse.gmf.gmfgraph.DiagramLabel;
import org.eclipse.gmf.gmfgraph.Node;
import org.eclipse.gmf.mappings.AuditRule;
import org.eclipse.gmf.mappings.CanvasMapping;
import org.eclipse.gmf.mappings.ChildReference;
import org.eclipse.gmf.mappings.CompartmentMapping;
import org.eclipse.gmf.mappings.Constraint;
import org.eclipse.gmf.mappings.FeatureLabelMapping;
import org.eclipse.gmf.mappings.FeatureSeqInitializer;
import org.eclipse.gmf.mappings.LabelMapping;
import org.eclipse.gmf.mappings.LinkMapping;
import org.eclipse.gmf.mappings.Mapping;
import org.eclipse.gmf.mappings.MappingEntry;
import org.eclipse.gmf.mappings.NodeMapping;
import org.eclipse.gmf.mappings.NodeReference;
import org.eclipse.gmf.mappings.TopNodeReference;
import org.eclipse.gmf.tooldef.CreationTool;
import org.eclipse.gmf.tooldef.ToolRegistry;
import org.isoe.diagraph.diagraph.DLabeledEdge;
import org.isoe.diagraph.diagraph.DNode;
import org.isoe.diagraph.diastyle.helpers.IErrorReporter;
import org.isoe.diagraph.gen.gmf.util.DGenHelpers;
import org.isoe.diagraph.internal.api.DPhase;
import org.isoe.diagraph.internal.api.IDiaNamedElement;
import org.isoe.diagraph.internal.api.IDiaNode;
import org.isoe.diagraph.internal.api.IDiaSyntaxElement;
import org.isoe.diagraph.internal.m2.DiaContainedElement;
import org.isoe.diagraph.internal.m2.DiaLabel;
import org.isoe.diagraph.internal.m2.DiaLink;
import org.isoe.diagraph.internal.m2.DiaNode;
import org.isoe.diagraph.internal.m2.DiaReference;
import org.isoe.diagraph.internal.m2.api.IDiaContainment;
import org.isoe.diagraph.internal.m2.parser.DAnnotationParser;
import org.isoe.diagraph.internal.m2.parser.DStatement;
import org.isoe.diagraph.internal.m2.parser.DStatement.DCommand_;
import org.isoe.diagraph.parser.DiagraphMapping;
import org.isoe.diagraph.parser.api.IDiagraphMapping;
import org.isoe.diagraph.parser.api.IDiagraphNode;
import org.isoe.diagraph.parser.api.IDiagraphParser;
import org.isoe.diagraph.parser.api.IDiagraphReferenceAssociation;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.utils.DLog;

/**
 *
 */

public class GmfMapGenerator extends GmfBaseGenerator {
	private Mapping mapping;
	private ToolRegistry toolRegistry;
	private Canvas canvas;
	private List<IDiaSyntaxElement> prepared = new ArrayList<IDiaSyntaxElement>();
	private static final boolean LOG = DParams.GmfMapGenerator_LOG;// .false; //
																	// FP130731
																	// error10
																	// case
	private static final String SPACES_ = "                                                                                                                                                                                                                                                                            ";
	private List<NodeMapping> topNodeMappings_ = new ArrayList<NodeMapping>();
	private List<NodeMapping> childrenMappings = new ArrayList<NodeMapping>();
	private List<CompartmentMapping> compartmentMappings = new ArrayList<CompartmentMapping>();
	private String logMap;
	private IPaletteManager paletteManager;
	private int id = 0;
	private int uniqueid;
	// private NodeMapping nodeMapping;
	// private ChildReference childReference;
	private GmfGraphGenerator gmfgraphgenerator;

	private Object[] addNodeAndContainmentMappings(
			NodeMapping parentNodeMapping, IDiaContainment containment,
			IDiaNode targetNodeCoA, int id, int depth, String sp) {
		Object[] result = new Object[3];
		DiaNode targ = (DiaNode) containment.getTargetNode();
		NodeMapping nodeMapping = null;
		ChildReference childReference = null;
		DiaNode concreteNode = null;
		Object[] result2 = null;
		DiaNode targetnod = (DiaNode) containment.getTargetNode();
		result2 = createNodeMapping(targetnod, false, id, depth); // FP150514composite
		nodeMapping = (NodeMapping) result2[0];
		concreteNode = (DiaNode) result2[1];
		if (nodeMapping != null) {
			result[2] = concreteNode;
			result[0] = nodeMapping;

			childReference = (ChildReference) nodeMapping.eContainer();
			result[1] = childReference;
			// childReference.setCompartment((CompartmentMapping)
			// containment.getCompartmentMapping()); // FP140331yyy
			parentNodeMapping.getChildren().add(childReference);
			setCompartment(containment, nodeMapping);
			if (LOG)
				clog(sp + "ACNP adding nodeMapping "
						+ nodeMapping.getDiagramNode().getName() + " to "
						+ parentNodeMapping.getDiagramNode().getName());
			targetNodeCoA.setMapped(true);

			// if (containment.getTargetNode().isRecursive())
			// nodeMapping.getRelatedDiagrams().add(mapping.getDiagram());
			// //FP140120yyy?
			return result;
		} else {
			if (LOG)
				clog(sp + " no mapping for " + targetNodeCoA.getName());
			// nodeMapping = null;
			// childReference = null;
			return null;// childNodeMapping;
		}
	}

	@Override
	public void pass3(DPhase phase) {
		if (LOG)
			logtopnodemappings();
		validateTopNodeMappings();
		createAllNodeMappings_();
		if (LOG)
			clog(countCompartmentMappings() + " compartmentMappings");
		// allCompartmentMappingAddChildren_nu();
		// checkCompartmentMappings_wait();
	}

	private boolean createBykeValidators() {
		int valcount = 0;
		if (LOG)
			clog("createValidators");
		for (NodeMapping nodeMapping : topNodeMappings_) {
			;// clog(nodeMapping.getDiagramNode().getName());
		}

		if (createBykeValidator())
			valcount++;

		String eclassName = "Library";
		String rulename = "cyclic dependancy";
		String ruleDescrition = "cyclic dependancy";
		String oclRule = "self.dependancies->forAll(r1, r2 |  r1.after = r2.before  implies r2.after <> r1.before)";

		if (createValidator(eclassName, rulename, ruleDescrition, oclRule))
			valcount++;
		return valcount > 0;

	}

	private boolean createBykeValidator() { // FP130914
		if (LOG)
			clog("createBykeValidator");
		String povclaz = "Bike";
		DNode dnod = findDNode(povclaz);
		if (dnod != null) {
			String ruleContainer = povclaz.toLowerCase();
			String rulename = "unicycle";
			String ruleDescrition = "Unicycle has no front wheels, handlebars and can only have uniframe and unisaddle";
			String oclRule = "self.frame.type = FrameType::uniFrame implies self.saddle.type = SaddleType::unicycleSaddle and self.handlebars = null and self.frontWheel = null and self.frontBrake = null";
			AuditRule auditRule = DGenHelpers.createAuditRule_(mapping,
					dnod.getEClaz(), ruleContainer, rulename, ruleDescrition,
					oclRule);
			if (LOG)
				clog("creating rule " + povclaz + "." + rulename + " : "
						+ oclRule);
			return true;
		}
		return false;

	}

	// eclassName="Library" =ruleName="dependancy.check" ruleDescrition =
	// "avoid cyclic dependancy"
	// oclRule
	// ="self.dependancies->forAll(r1, r2 |  r1.after = r2.before  implies r2.after <> r1.before)"
	private boolean createValidator(String eclassName, String rulename,
			String ruleDescrition, String oclRule) { // FP130914
		clog("createValidator");
		DNode dnod = findDNode(eclassName);
		if (dnod != null) {
			String ruleContainer = eclassName.toLowerCase();
			AuditRule auditRule = DGenHelpers.createAuditRule_(mapping,
					dnod.getEClaz(), ruleContainer, rulename, ruleDescrition,
					oclRule);
			if (LOG)
				clog("creating rule " + eclassName + "." + rulename + " : "
						+ oclRule);
			return true;
		}
		return false;
	}

	@Override
	public boolean createValidators(DPhase phase) { // FP130914
		if (DParams.EXPERIMENT_OCL)
			return createBykeValidators();
		else
			return false;
	}

	private void createAllNodeMappings_() {
		logMap = "";
		for (NodeMapping topnode : topNodeMappings_) {

			TopNodeReference tnr = DGenHelpers.createTopNodeReference(topnode);
			IDiagraphNode temp = null;
			addTopNode_(tnr, temp);
		}
	}

	/*--------------- end  handle top nodes --------------------*/

	private void logtopnodemappings() {
		clog("top node mappings (" + topNodeMappings_.size() + ") top nodes");
		int n = 0;
		for (NodeMapping nodeMapping : topNodeMappings_) {
			++n;
			clog("LTNMP node mapping "
					+ n
					+ " : "
					+ (nodeMapping.getDiagramNode() == null ? "null"
							: nodeMapping.getDiagramNode().getName()));
		}
	}

	private void validateTopNodeMappings() {
		for (NodeMapping nodeMapping : topNodeMappings_)
			if (nodeMapping == null)
				throw new RuntimeException(
						"error while validateTopNodeMappings");
	}

	private void specialLog(String mesg) {
		System.out.println(mesg);
	}

	private void logEmbeds(String sp, DiaNode el) {// FP140329

		if (!(LOG || DParams.Special_LOG))
			return;

		if (DParams.Special_LOG) {
			/*
			 * specialLog("----------"); List<EReference> edir =
			 * el.getDirectContainements(); for (EReference eReference : edir) {
			 * specialLog(eReference.getName()); // subExpressions_Aaaa;as }
			 */

			specialLog("----------");
			List<EReference> dirc = parser2.getDirectContainementReferences(el
					.getSemanticRole());
			for (EReference eReference : dirc) {
				specialLog(eReference.getName()); // subExpressions_Aaaa;as
			}
			/*
			 * specialLog("----------"); List<EReference> einh =
			 * el.getAllContainements(); for (EReference eReference : einh) {
			 * specialLog(eReference.getName());// //subExpressions_Aaaa;as }
			 */

			specialLog("----------");
			List<EReference> arc = parser2.parseAllContainementReferences(el
					.getSemanticRole());
			for (EReference eReference : arc) {
				specialLog(eReference.getName());// //subExpressions_Aaaa;as
			}

			specialLog("----------");
			// FP140415
			// List<IDiagraphReferenceAssociation> assocs =
			// parser2.getAllAssociations(el.getSemanticRole(), true, view);
			List<IDiagraphReferenceAssociation> assocs = parser2
					.getContainmentAssociationsAndSubHierTo(el
							.getSemanticRole());
			for (IDiagraphReferenceAssociation iAssociation : assocs) {
				specialLog("{" + iAssociation.toString() + "}"); // Aaaa -
				// subExpressions_Aaaa
				// -> Aaaa (dir
				// concr) ;
				// RootExpression -
				// as -> Aaaa (dir
				// concr)
			}
			specialLog("----------");
		}

		List<IDiaNode> embedded = el.getEmbeddedNodes();
		for (IDiaNode embed_ : embedded) {
			if (embed_ == null)
				cerror("should not happen: embedded node is null");
			if (LOG) {
				clog(sp + "GMFMGN embedded in " + el.getName() + ": "
						+ (embed_ == null ? " null " : embed_.getName())
						+ (el == embed_ ? " auto content " : ""));
			}
		}

		List<IDiaNode> srcs = el.getContainmentSources();// getEmbeddingNodes();
		for (IDiaNode src : srcs) {
			if (LOG)
				clog(sp + "GMFMGN embedding for " + el.getName() + ": "
						+ src.getName() + (el == src ? " auto content " : ""));
		}
	}

	@SuppressWarnings({ "restriction", "unused" })
	private boolean isPropagatedOnCanvas(IDiaContainment containment) {
		boolean result = containment.isPropagated()
				&& containment.getSourceNode().isCanvas();
		if (result && LOG){
			clog("ACRCV is propagated en canvas: "
					+ containment.getSourceNode().getName() + " -> "
					+ containment.getTargetNode().getName());
		}
		return result;
	}

	private CompartmentMapping addCompartmentMapping(DiaNode containment,
			String containmentName, int level, NodeMapping nodeMapping) {
		CompartmentMapping cm = createCompartmentMapping(containment,
				containmentName);
		if (cm != null) { // FP140109
			compartmentMappings.add(cm);
			nodeMapping.getCompartments().add(cm);
			containment.addCompartmentMapping(cm, containmentName, level); // FP150428
			// containment.addCompartmentMapping(cm);
			if (LOG)
				clog("ADCMAP add compartment mapping "
						+ cm.getCompartment().getName());
			return cm;
		} else {
			cerror("ADCMAP failed adding compartment mapping "
					+ containmentName); // FP140415 // FP140120
			return null;
		}
	}

	@Override
	public void initTool() {
		// nothing //FP131205
	}

	@SuppressWarnings({ "restriction" })
	private DiaNode findCanvasNode() {
		DiaNode result = null;
		for (DStatement statement : annotationParser.getDStatements()) {
			if (statement.getCommand() == DCommand_.MAP_CREATE_) {
				result = (DiaNode) statement.getElement();
				break;
			}
		}
		return result;
	}

	@SuppressWarnings({ "restriction" })
	private DiaNode findCompositePattern_nu(DiaNode canvasNode) {
		for (DStatement statement : annotationParser.getDStatements()) {
			if (statement.getCommand() == DCommand_.MAP_NODE_) {
				DiaNode n = (DiaNode) statement.getElement();
				if (n != canvasNode) {
					EClass nc = n.getEClass();
					EClass cn = canvasNode.getEClass();
					if (nc.isAbstract() && nc.isSuperTypeOf(cn)) {
						if (LOG)
							clog("pattern composite found " + nc.getName() + ""
									+ cn.getName());
						return n;
					}

				}
			}
		}
		return null;
	}


	public void log() {
		if (LOG) {
			clog("LOGSTA map-canvas");
			logStatements(DCommand_.MAP_CREATE_);

			clog("LOGSTA map-nodes");
			logStatements(DCommand_.MAP_NODE_);

			clog("LOGSTA map-contains");
			logStatements(DCommand_.MAP_CONTAIN);

			clog("LOGSTA map-links");
			 logStatements(DCommand_.GRAPH_LINK);
		}
	}


	@SuppressWarnings({ "restriction" })
	@Override
	public void executeCommands(DPhase phase) {//FP150531

	    log();
		DiaNode canvasNode = findCanvasNode();
		//DiaNode compo_nu = findCompositePattern(canvasNode);
		processCanvas();
		processTopNodes();
		processLink_();

	}



	private List<DStatement> nodesSortedByDepth() {
		List<DStatement> result = getNodeStatements();
		if (LOG) {
			clog(" before sorting:");
			for (DStatement statement : result) {
				clog(statement.getDiagramElementName()
						+ " "
						+ annotationParser.getInternalModel()
								.findDiaNode(statement.getDiagramElementName())
								.getDepth());
			}
		}
		if (LOG)
			clog("sort nodes by depth");
		Collections.sort(result, new Comparator<DStatement>() {
			public int compare(DStatement s1, DStatement s2) {
				DiaNode n1 = annotationParser.getInternalModel().findDiaNode(
						s1.getDiagramElementName());
				DiaNode n2 = annotationParser.getInternalModel().findDiaNode(
						s2.getDiagramElementName());
				return n2.getDepth() - n1.getDepth();
			}
		});

		if (LOG)
			clog(" after sorting:");
		for (DStatement statement : result) {
			if (LOG)
				clog(statement.getDiagramElementName()
						+ " "
						+ annotationParser.getInternalModel()
								.findDiaNode(statement.getDiagramElementName())
								.getDepth());
		}
		return result;
	}

	private List<DiaNode> processTopNodes(List<DStatement> statements) {
		if (LOG) {
			clog("top nodes:");
			for (DStatement statement : statements) {
				logTopNode(statement);
			}
		}
		List<DiaNode> result = new ArrayList<DiaNode>();
		if (LOG)
			clog("process top nodes");
		for (DStatement statement : statements) {// FP150524composite
			if (statement.getCommand() == DCommand_.MAP_NODE_) {
				if (LOG)
					clog("findDiaNode " + statement.getDiagramElementName());
				DiaNode node = annotationParser.getInternalModel().findDiaNode(
						statement.getDiagramElementName());
				if (LOG)
					clog(statement.toString());
				if (LOG)
					clog(node == null ? "NULL" : node.toLog());
				Object[] r = (Object[]) processNodeTop(statement, null);
				if (r != null) {
					MappingEntry map = (MappingEntry) r[0];
					if (map != null)
						result.add(node); // FP150329
				}
			}
		}
		return result;
	}

	private void processCanvas() {
		if (LOG) {
			clog("LOGSTA map-canvas");
			logStatements(DCommand_.MAP_CREATE_);
		}
		if (LOG)
			clog("process canvas");
		for (DStatement statement : annotationParser.getDStatements()) {
			if (statement.getCommand() == DCommand_.MAP_CREATE_) {
				processCanvas(statement);
				if (canvasComposite_nu)
					processNodeTop(statement, DCommand_.MAP_NODE_);// FP150524composite
			}
		}
	}

	private List<DStatement> getNodeStatements() {
		List<DStatement> result = new ArrayList<DStatement>();
		for (DStatement statement : annotationParser.getDStatements()) {
			if (statement.getCommand() == DCommand_.MAP_NODE_) {
				result.add(statement);
			}
		}
		return result;
	}

	private void processLink_() {
		if (LOG) {
			clog("LOGSTA map-link");
			logStatements(DCommand_.MAP_LINK_);
		}
		if (LOG)
			clog("process links");
		for (DStatement statement : annotationParser.getDStatements()) {
			if (statement.getCommand() == DCommand_.MAP_LINK_)
				processLink(statement, true); // FP120621a
		}
	}


	private void processTopNodes() {
		if (LOG) {
			clog("LOGSTA map-topnodes");
			logStatements(DCommand_.MAP_NODE_);
		}
		if (LOG)
			clog("process TopNodes");
		List<DStatement> nodesSortedByDepth = nodesSortedByDepth();
		List<DiaNode> topNodes = processTopNodes(nodesSortedByDepth);
		processTopNodeCanvasInheritance(topNodes);
	}

	private void processTopNodeCanvasInheritance(List<DiaNode> added) {
		if (LOG)
			clog("process canvas inheritance");// see3 //FP140202okok
												// //FP150518voir zfsm
		for (DStatement statement : annotationParser.getDStatements()) {
			if (statement.getCommand() == DCommand_.GRAPH_NODE_) {
				// processSpecialNode1(statement); //FP130319ok
				DiaNode node = annotationParser.getInternalModel().findDiaNode(
						statement.getDiagramElementName());
				if (LOG)
					clog("====" + node.getName() + "========");
				if (!added.contains(node)) {
					processTopNodeCanvasInheritance(statement, id++);
				}
				if (LOG)
					clog("============");
			}
		}
	}

	private void clog29_(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	@Override
	public Object processLink(DStatement statement, boolean oriented) {
		if (LOG)
			clog("PLK" + this.getClass().getSimpleName() + " processLink - "
					+ statement);
		return createLinkMapping(statement);
	}

	@Override
	public Object processNodeTop(DStatement statement, DCommand_ force) {
		;
		if (LOG)
			clog("GMMGPN " + (force != null ? " FORCE " : "")
					+ this.getClass().getSimpleName() + " processNode - "
					+ statement);



		Object[] r = processTopNode(statement, force);
		return r; // FP150524compo

	}

	private Object[] addSubTopNodeMappings_(Object[] arg, IDiagraphNode dn_nu,
			int depth, String sp) { // FP150524composite

		DiaNode diaNode = (DiaNode) arg[1];
		NodeMapping parentNodeMapping = (NodeMapping) arg[0];
		CompartmentMapping compartMapping_ = null;
		if (DParams.COMPOSITE_LOG) {
			clog("contaiments for " + diaNode.getName());
			for (IDiaContainment cont : diaNode.getContainments()) {
				logContainment(cont);
			}
		}

		IDiaContainment compositeContainment = getCanvasCompositeContainment(diaNode);
		if (compositeContainment != null
				&& compositeContainment.isCompartment()) {//
			// if (diaNode.getContainments().get(0).isCompartment()) { //
			// FP150526voir1
			containment2Compartment(diaNode, diaNode.getContainments().get(0),
					dn_nu, parentNodeMapping, id, 0, "", false, true);
			compartMapping_ = (CompartmentMapping) diaNode.getContainments()
					.get(0).getCompartmentMapping();
		}
		ChildReference childref = DGenHelpers.createChildReference_(null,
				diaNode.getContainmentReferenceAlt());
		parentNodeMapping.getChildren().add(childref);
		if (compartMapping_ != null)
			childref.setCompartment(compartMapping_);
		// if (!compartMapping.getChildren().contains(childref))
		// compartMapping.getChildren().add(childref);
		IDiagraphNode dn_nu2 = null;
		Object[] result = createNodeMapping_(diaNode, dn_nu2, false, depth);
		NodeMapping childNodeMapping = (NodeMapping) result[0];
		childref.setOwnedChild(childNodeMapping);
		DiaNode rdiaNode = (DiaNode) result[1];
		addLabels(rdiaNode, rdiaNode, childNodeMapping, sp);
		return result;
	}

	private List<NodeMapping> getSubNodeMappings(NodeMapping nm) {
		List<NodeMapping> nodeMappings = new ArrayList<NodeMapping>();
		EList<ChildReference> children = nm.getChildren();
		for (ChildReference childReference : children) {
			nodeMappings.add(childReference.getOwnedChild());
			NodeMapping child = childReference.getOwnedChild();
			if (child != null)
				nodeMappings.addAll(getSubNodeMappings(child));
		}
		return nodeMappings;
	}

	private List<NodeMapping> getNodeMappings() {
		List<NodeMapping> nodeMappings = new ArrayList<NodeMapping>();
		EList<TopNodeReference> topnodes = mapping.getNodes();
		for (TopNodeReference topNodeReference : topnodes) {
			NodeMapping child = topNodeReference.getOwnedChild();
			nodeMappings.add(child);
			nodeMappings.addAll(getSubNodeMappings(child));
		}
		return nodeMappings;
	}

	private NodeMapping findNodeMapping(List<NodeMapping> nodes, String name) {
		for (NodeMapping nodeMapping : nodes) {
			String nm = nodeMapping.getDiagramNode().getName();
			if (nm.equals(name))
				return nodeMapping;
		}
		return null;
	}

	private void validateNode(NodeMapping mapping) {
		for (ChildReference childNodeRef : mapping.getChildren()) {
			// Currently childNodeMapping should have compartment
			validate(childNodeRef.getChild() != null, "validateNode",
					"childNodeRef.getChild() is null ");
		}
	}

	private int getDepth(NodeReference noderef) {
		if (noderef instanceof TopNodeReference)
			return 1;
		if (noderef instanceof ChildReference)
			return getDepth((NodeReference) noderef.eContainer().eContainer()) + 1;
		else
			throw new RuntimeException(
					"should not happen in getDepth(NodeReference)");
	}

	public void validate2() {
		List<NodeMapping> nmaps = getNodeMappings();
		for (NodeMapping nodeMapping : nmaps) {
			if (nodeMapping != null && nodeMapping.getDiagramNode() != null) { // FP130209
				clog(nodeMapping.getDiagramNode().getName() + " depth="
						+ getDepth((NodeReference) nodeMapping.eContainer()));
			} else
				clog("!!! no nodeMapping");
		}
		for (NodeMapping nodeMapping : nmaps) {
			if (nodeMapping != null)
				validateNode(nodeMapping);
		}

	}

	@Override
	public void pass4(DPhase phase) {
		/*
		 * clog("----------------------");
		 * GmfLogger.logNodeMapping(findNodeMapping(getNodeMappings(),
		 * "Genius"), 0); clog("----------------------");
		 * GmfLogger.logNodeMapping(findNodeMapping(getNodeMappings(), "Jo"),
		 * 0); clog("----------------------");
		 */
		// createMissing3();
	}

	private void addtopNodeMappingAndTool(TopNodeReference topNodeReference,
			DiaNode diaNode, IDiagraphNode dn, NodeMapping nodeMapping) {

		Node node = nodeMapping.getDiagramNode();
		if (LOG)
			clog("ATP " + node.getName());
		DNode dnod = findDNode(node.getName()); // FP121008
		if (LOG)
			clog("ATP2 " + dnod.getName());
		if (nodeMapping.getTool() == null) // in case of label != name
			nodeMapping.setTool(paletteManager.findTool(dnod));// //FP121008
		mapping.getNodes().add(topNodeReference);
	}

	private FeatureLabelMapping getSimpleLabelMapping(DiaLabel lab) {
		String elname = lab.getName();
		EAttribute eat = lab.getEAttribute();
		DiagramLabel diagramLabel = DGenHelpers.findLabel(canvas, elname);
		List<EAttribute> attrs = new ArrayList<EAttribute>();
		attrs.add(eat);
		return DGenHelpers.createFeatureLabelMapping(attrs, null, diagramLabel);
	}

	private LabelMapping getMultipleLabelMapping_(DiaLabel lab) { // FP140206xxxaaaxxx
		String elname = lab.getName();
		List<EAttribute> attrs = lab.getEAttributes_();
		DiagramLabel diagramLabel = DGenHelpers.findLabel(canvas, elname);
		if (LOG)
			clog("getMultipleLabelMapping" + "" + lab.getName() + " sep= "
					+ lab.getAttributeSeparators_());
		return DGenHelpers.createFeatureLabelMapping(attrs,
				lab.getAttributeSeparators_(), diagramLabel);
	}

	private LinkMapping createLinkMapping(DStatement statement) {
		DiaLink link = annotationParser.getInternalModel().findDiaLink(
				statement.getDiagramElementName());

		DLabeledEdge DLabeledEdge = findDLabeledEdge(statement
				.getDiagramElementName()); // FP121008

		CreationTool lct = DGenHelpers.findCreationToolByTitle(toolRegistry,
				link.getName());

		if (lct == null) // in case of label != name{
			lct = paletteManager.findTool(DLabeledEdge);// //FP121008

		LinkMapping lm = DGenHelpers.createLinkMapping(link.getEModelElement(),
				(EReference) link.getContainmentReferenceBase(),
				link.getSourceReference(), link.getTargetReference(), lct,
				DGenHelpers.findConnection(canvas, link.getName()));
		// FP120621d
		if (lm.getDiagramLink() == null)
			throw new RuntimeException("fatal error in createLinkMapping for "
					+ statement.toString());

		for (IDiaNamedElement lab : link.getNamedChildren()) {
			DiaLabel diaLabel = (DiaLabel) lab;
			if (diaLabel.isMultiple())
				lm.getLabelMappings().add(getSimpleLabelMapping(diaLabel)); // FP140206xxxaaaz
			else
				lm.getLabelMappings().add(getMultipleLabelMapping_(diaLabel));

		}
		addToMapping(lm);
		return lm;
	}

	private void logs(DiaNode concreteNode, DiaNode diaNode_,
			NodeMapping nodeMapping, IDiagraphNode dn, String sp) {
		if (LOG)
			logMapping(nodeMapping, sp);

		if (LOG)
			logEmbeds(sp, diaNode_);
		// diaNode.addNodeMapping(nodeMapping,((EClass)nodeMapping.getDomainMetaElement()).getName());//
		// , null);// FP140408 //FP150318t
		if (LOG) {
			clog(diaNode_.getName() + " - " + diaNode_.getContainments().size());
		}
	}

	private IDiaNode getConcreteCanvas(IDiaNode diaNode) {
		if (diaNode.isAbstract()) {
			List<IDiaNode> nods = diaNode.getSubNodes();
			for (IDiaNode subnod : nods) {
				if (subnod.isCanvas())
					return subnod;
			}
		}
		return null;
	}

	private void logChildrenNodes(DiaNode diaNode, NodeMapping nodeMapping,
			IDiagraphNode dn, int id, int depth, String sp) {
		IDiaNode cnv = getConcreteCanvas(diaNode); // FP150430
		if (cnv != null) {
			diaNode = (DiaNode) cnv;
			if (LOG)
				clog("abstract, concrete canvas = " + cnv.getName());
		}
		logChildNodeMappings(diaNode, dn, nodeMapping, // null,
				id, depth, sp);
	}

	private Object[] addNodeAndContainmentMappingAndChildren__(
			IDiaContainment containment, DiaNode diaNode,
			IDiagraphNode dn,
			NodeMapping parentNodeMapping, // ChildReference child,
			int id, int depth, String sp, int n, boolean hasderiv,
			boolean recursive) {

		if (containment.isCompartment()) {
			containment2Compartment(diaNode, containment, dn,
					parentNodeMapping, id, depth, sp, hasderiv, false);
			return addNodeAndContainmentMappingsAndChildren__(
					parentNodeMapping, containment, ++depth, sp, n, recursive,
					true);

		} else
			return addNodeAndContainmentMappingsAndChildren__(
					parentNodeMapping, containment, ++depth, sp, n, recursive,
					false);

	}

	private Object[] createMapping(DiaNode diaNode, DiaNode targetNode_nu,
			IDiagraphNode dn_nu, boolean top, int depth) {
		if (LOG && diaNode.getContainments() != null
				&& diaNode.getContainments().size() > 1)
			clog(diaNode.getContainments().size() + " containements for "
					+ diaNode.getName());

		Object[] result = createNodeMapping(diaNode, top, id++, depth);
		MappingEntry nodeMapping_ = (MappingEntry) result[0];
		if (nodeMapping_ != null) { // FP150518modif
			// topNodeMappings_.add((NodeMapping) nodeMapping_);
			diaNode.setMapped(true);
			if (diaNode.isDiagramRecursive())
				nodeMapping_.getRelatedDiagrams().add(mapping.getDiagram());
			return result;
		} else {
			if (LOG)
				clog("nodemapping is null for " + diaNode.getName()); // FP150518modif
			return null;// nodeMapping;
		}
	}

	private Object[] addNodeAndContainmentMappingsAndChildren__(
			// FP150429b
			NodeMapping parentNodeMapping, IDiaContainment containment,
			int depth, String sp, int n_, boolean recursive,
			boolean isCompartment) {
		IDiaNode tn = containment.getTargetNode();
		if (tn.isAbstract()) {// FP150430
			if (LOG)
				clog("node is abstract");
		}
		DiaNode targ = (DiaNode) containment.getTargetNode();
		Object[] nodemap = addNodeAndContainmentMappings(parentNodeMapping,
				containment, tn, n_, depth, sp); // FP140417yyy
		if (nodemap != null) {
			NodeMapping addednmp_ = (NodeMapping) nodemap[0];
			// childrenMappings.add((NodeMapping) addednmp_);

			ChildReference addedchild = (ChildReference) nodemap[1];
			DiaNode concreteNode = (DiaNode) nodemap[2];
			if (addednmp_ != null) { // FP130806
				if (LOG)
					logContainment(containment);
				IDiagraphNode temp = null;
				// NodeMapping parentMapping=((ChildReference)
				// addednmp_.eContainer()).getOwnedChild();
				tn.addNodeMapping(addednmp_, tn.getName());// , null);//
															// FP140408
															// //FP150318t
				// String sp = getSpaces_(depth);

				if (LOG)
					logs(concreteNode, (DiaNode) tn, addednmp_, temp, sp);// FP150501
				addLabels(concreteNode, (DiaNode) tn, addednmp_, sp);

				if (recursive)
					addchildNodeMappings__((DiaNode) tn, addednmp_, temp, ++n_,
							depth, sp);

				return nodemap;
			} else
				clog("inexpected stop with child reference mapping");
		} else
			clog("inexpected stop with child reference mapping");
		return null;
	}

	private void logChildNodeMappings(DiaNode diaNode, IDiagraphNode dn,
			NodeMapping nodeMapping, // ChildReference child,
			int id, int depth, String sp) {
		int subid = 0;
		;
		boolean hasderiv = diaNode.hasDerivedSubNode();
		for (IDiaContainment containment : diaNode.getContainments()) {
			if (LOG)
				clog(" containment " + containment.getSourceNode().getName()
						+ "--" + containment.getName() + "->"
						+ containment.getTargetNode().getName());
		}
	}

	private void setCompartment(IDiaContainment containment,
			NodeMapping nodeMapping_) {
		Object reference = nodeMapping_.eContainer();
		if (reference instanceof ChildReference) {
			ChildReference childReference = (ChildReference) reference;
			Object cmap = containment.getCompartmentMapping();
			if (cmap != null)
				childReference.setCompartment((CompartmentMapping) cmap); // FP140331yyy
		} else if (reference instanceof TopNodeReference) {
			TopNodeReference topnodeReference = (TopNodeReference) reference;
		}
	}

	private void addLabels(DiaNode concreteNode_, DiaNode diaNode,
			NodeMapping nodeMapping, String sp) {
		if (LOG)
			clog(sp + "ACRFS addind labels to node " + concreteNode_.getName()
					+ " " + nodeMapping.getDiagramNode().getName()
					+ (diaNode.isAbstract() ? (" (abstract)") : ""));

		List<IDiaNamedElement> labs = concreteNode_.getNamedChildren();
		for (IDiaNamedElement lab : labs) { // FP150501
			DiaLabel diaLabel = (DiaLabel) lab;
			if (!diaLabel.isMultiple())
				nodeMapping.getLabelMappings().add(
						getSimpleLabelMapping(diaLabel));
			else
				nodeMapping.getLabelMappings().add(
						getMultipleLabelMapping_(diaLabel));
			if (LOG)
				clog(sp + "ACRF addind label: " + diaLabel.getName());
		}
	}

	private boolean ok_(DiaNode nod, IDiaContainment containment) {
		// boolean hasderiv = nod.hasDerivedSubNode();
		IDiaNode derived = nod.getDerivedSubNode(nod.getName());
		IDiaNode src = containment.getSourceNode();
		return (!containment.isDerived() || (containment.isDerived() && src == derived));
	}

	private boolean matchDerive(DiaNode nod, IDiaContainment containment) {
		return ((containment.isDerived() && containment.getSourceNode() == nod
				.getDerivedSubNode(nod.getName())));
	}

	private CompartmentMapping findCompartmentMapping1(
			IDiaContainment containment, int level) { // //FP150331a306
		String compartmentName = containment.getTargetReference().getName();
		Compartment compartment = DGenHelpers.findCompartment(canvas,
				compartmentName);
		return (CompartmentMapping) ((DiaNode) containment.getSourceNode())
				.getCompartmentMapping(compartmentName, level);
	}

	private CompartmentMapping findCompartmentMapping2(// FP150405
			IDiaContainment containment, int depth) { // FP150404a323
		String compartmentName = containment.getTargetReference().getName();
		Compartment compartment = DGenHelpers.findCompartment(canvas,
				compartmentName);
		if (containment.isPropagated())
			clog("containment propagated");
		CompartmentMapping map = (CompartmentMapping) ((DiaNode) containment
				.getSourceNode()).getCompartmentMapping(compartmentName, depth);
		return map;
	}

	private void containment2Compartment(DiaNode diaNode,
			IDiaContainment containment, IDiagraphNode dn,
			NodeMapping parentNodeMapping, int id, int depth, String sp,
			boolean hasderiv, boolean force) { // FP150423azvoir
		boolean check;

		if (DParams.COMPOSITE_LOG)
			clog(containment.toLog());

		int dep = DParams.MAX_DEPTH_;
		String arg = containment.getArgument();
		if (arg != null && !arg.isEmpty())
			dep = Integer.parseInt(arg); // FP150512transp1

		if (depth < dep) { // FP150512transp1
			// if (containment.isCompartment(depth)) { //FP150503
			if (containment.isCompartment()) { // FP150513transp //FP150503

				boolean propagated = isPropagatedOnCanvas(containment);
				if (propagated)
					check = true;
				boolean cdc = containment.isDerivedCompartment();
				if (cdc)
					check = true;
				boolean mdr = matchDerive(diaNode, containment);
				if (mdr)
					check = true;
				boolean cdr = containment.isDerived();
				if (cdr)
					check = true;
				boolean derived = hasderiv && cdc && mdr;
				boolean initial = !hasderiv && !cdr;
				boolean autocont = containment.getTargetReference() != null
						&& containment.getTargetReference() == containment
								.getSourceContainmentReferenceBase();// FP150405

				if (LOG) {
					String dname = diaNode.getName();
					String targname = containment.getNameIfShared();
					String compartmentName = containment.getTargetReference()
							.getName();
					clog("mapping adding - compartment:" + dname + "."
							+ compartmentName);
					if (LOG && "Region".equals(dname)) {
						if ("Region".equals(targname)) {

							check = true;
						}
					}

					clog("mapping adding - compartment; conditions1 ="
							+ (force ? " force " : " ")
							+ (propagated ? " propagated " : " ")
							+ (cdc ? " derivedk " : " ")
							+ (mdr ? " mdr " : " ")
							+ (cdr ? " derivedc " : " ")
							+ (derived ? " __derived " : " ")
							+ (initial ? " __initial " : " ")
							+ (autocont ? " __autocont " : " ")

					);

				}

				if (parentNodeMapping != null && (derived || initial)) {
					if (LOG && propagated)
						clog("compartment propagated on canvas: "
								+ containment.getName());
					CompartmentMapping cm_ = findCompartmentMapping2(
							containment, depth);
					boolean lastcondition = cm_ == null || autocont || force;

					if (LOG)
						clog("mapping adding - compartment: "
								+ (lastcondition ? " lastcondition=true "
										: " lastcondition=false ") + "("
								+ (cm_ == null ? "" : " allready exists ")
								+ (autocont ? " autocont " : "")
								+ (force ? " force " : "") + ")");

					if (lastcondition) { // FP150428voir2//
											// FP150420//
						// FP//FP150405
						// if (!diaNode.isCanvas() && (cm_ == null || autocont))
						// { // FP150420// FP//FP150405

						cm_ = addCompartmentMapping(diaNode,
								containment.getName(), depth, parentNodeMapping); // FP150429zzzzz
						if (LOG)
							clog("mapping added - compartment="
									+ cm_.getCompartment().getName());
					}
					if (cm_ != null)
						containment.setCompartmentMapping(cm_);
					else if (LOG)
						clog("CompartmentMapping failed "
								+ containment.getName());
				} else if (LOG)
					clog("CompartmentMapping failed " + containment.getName());
			} else if (LOG)
				clog("*********    not a compartment containment "
						+ containment.getName());
		}
	}

	private void logContainment(IDiaContainment containment) {
		/*
		 * EClass me = (EClass) containment.getEModelElement(); if (me == null)
		 * clog("no semantic mapping for " + containment.getName()); EReference
		 * scrbase = containment.getSourceContainmentReferenceBase(); EReference
		 * scralt = containment.getSourceContainmentReferenceAlt(); EReference
		 * tr = containment.getTargetReference(); IDiaNode sn =
		 * containment.getSourceNode(); IDiaNode tn =
		 * containment.getTargetNode(); clog("ACRF_base[" + sn.getName() + "-" +
		 * (scrbase == null ? "null" : scrbase.getName()) + "->" + tn.getName()
		 * + "]"); clog("ACRF_alt [" + sn.getName() + "-" + (scralt == null ?
		 * "null" : scralt.getName()) + "->" + tn.getName() + "]");
		 * clog("ACRF_tr  [" + sn.getName() + "-" + (tr == null ? "null" :
		 * tr.getName()) + "->" + tn.getName() + "]");
		 */
		if (DParams.COMPOSITE_LOG)
			clog(containment.toLog());

	}

	private String getSpaces_(int depth) {
		String sp = "";
		try {
			if (LOG && depth > 0)
				sp = SPACES_.substring(0, depth * 3); // FP130124
		} catch (Exception e) {
			sp = SPACES_;
		}
		return sp;
	}

	private void logMapping(NodeMapping nodeMapping, String sp) {
		if (LOG)
			clog(sp + "ACRFS createNodes: "
					+ nodeMapping.getDiagramNode().getName());
	}

	private void addToMapping(LinkMapping linkMapping) {
		if (LOG) {
			try {
				clog("add Link toMapping: "
						+ linkMapping.getDiagramLink().getName());
			} catch (Exception e) {
				clog("add Link toMapping: ??? "); // FP120621c
			}
		}
		mapping.getLinks().add(linkMapping);
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

	// ///

	protected DiaContainedElement findDiaElement(EModelElement mel) {
		for (DiaContainedElement el : annotationParser.getInternalModel()
				.getAllDiaNodesAndLinks())
			if (el.getEModelElement() == mel)
				return el;
		return null;
	}

	private List<DiaNode> getSubNodes_(DiaNode node) {
		List<DiaNode> result = new ArrayList<DiaNode>();
		EClass c = (EClass) node.getEModelElement();
		List<EClass> subClasses = getSubTypes(c);
		for (EClass sub : subClasses) {
			DiaContainedElement el = findDiaElement(sub);
			if (el instanceof DiaNode)
				result.add((DiaNode) el);
		}
		return result;
	}

	private boolean isOrSubNodeCanvas(DiaNode node) {
		if (node.isCanvas())
			return true;
		for (DiaNode sub : getSubNodes_(node))
			if (sub.isCanvas())
				return true;
		return false;
	}

	private void createCanvasMapping(DStatement statement) {
		mapping = DGenHelpers.createMapping();
		CanvasMapping canvasMapping = DGenHelpers.createCanvasMapping(
				ePackage,
				toolRegistry.getPalette(),
				canvas,
				annotationParser.getInternalModel()
						.findDiaNode(statement.getDiagramElementName())
						.getEModelElement());
		mapping.setDiagram(canvasMapping);
	}

	@Override
	public void processCanvas(DStatement statement) {
		if (LOG)
			clog("~~~~~ " + this.getClass().getSimpleName()
					+ " processCanvas - " + statement);
		createCanvasMapping(statement);
	}

	private void logNotMapped(DiaNode el, boolean addWarning) {
		String mesg = "";
		if (((DiaNode) el).containmentMark())
			mesg = el.getName() + " is not mapped (1) ";
		else
			mesg = el.getName() + " is not mapped (2) ";
		if (addWarning)
			DLog.addWarning(mesg);
		clog("!!! " + mesg + " !!!");
	}

	private void removeNotMapped(DiaNode el) {
		if (DParams.REMOVE_NOT_MAPPED)
			el.removeFromTool_();
	}

	@Override
	public void checkNode(DStatement statement) {

		IDiaSyntaxElement el = findDiaSyntaxElement(statement
				.getDiagramElementName());
		if (!((DiaNode) el).isMapped()) {
			logNotMapped((DiaNode) el, true);
			removeNotMapped((DiaNode) el); // FP140201
		}
	}

	private void validate_(EReference containRef, DiaNode node,
			boolean containmentNotNull, boolean notCommonGeneric,
			boolean containingNotNull, boolean top, int depth,
			int recursivedepth) {

		if (LOG) {
			String log = "GMFMAP_VAL " + (top ? "TOP " : " CHILD ") + "rdepth="
					+ recursivedepth + " validating " + "Containment feature ["
					+ containRef.getName() + ":"
					+ containRef.getEType().getName() + "] -> "
					+ node.getName();

			clog(log);
		}

		if (containmentNotNull)
			validate(containRef != null, "2_createNodeMapping",
					"Containment should not be null for " + node.getName());
		if (notCommonGeneric)
			validate(
					((EClass) containRef.getEType()).isSuperTypeOf((EClass) node
							.getEModelElement()), "3_createNodeMapping",
					"Containment feature [" + containRef.getName() + ":"
							+ containRef.getEType().getName()
							+ "] should contain instances of node class "
							+ node.getName());

		// FP131120xx set
		// FP131208 removed - related with error10case ?
		if (containingNotNull)
			validate(containRef.getEContainingClass() != null,
					"4_createNodeMapping",
					"containment.getEContainingClass should not be null for "
							+ node.getName());

	}

	// private DiaNode concreteNode;

	private void logNodeMapping(EClass eclaz_, EReference containmentRefBase,
			EReference containmentRefAlt) {
		clog(eclaz_.getName());
		clog(eclaz_.eContainer().getClass().getName());
		String msg = "validating " + "Containment feature ["
				+ containmentRefBase.getName() + ":"
				+ containmentRefBase.getEType().getName() + "] -> "
				+ eclaz_.getName();
		clog(msg);
		boolean test1 = containmentRefBase.getEType() == eclaz_;
		boolean test2 = ((EClass) containmentRefBase.getEType())
				.isSuperTypeOf(eclaz_);
		clog((test1 ? " test1" : "") + (test2 ? " test2" : ""));
	}

	private void createLibraryConstraint(DiaNode node, NodeMapping nodeMapping) {
		if (node.getName().equals("Media")) { // FP130914
			String rule = "library.loans->select((media = self))->size() <= copies";
			Constraint constr = DGenHelpers.createConstraint(nodeMapping, rule); // FP130914
			nodeMapping.setDomainSpecialization(constr);

			FeatureSeqInitializer initer = DGenHelpers
					.createAttributeInitializer(nodeMapping, "copies", "1");
			nodeMapping.setDomainInitializer(initer);
		}
	}

	private CompartmentMapping createCompartmentMapping(DiaNode containment,
			String compartmentName) {

		if (LOG)
			clog("---------- createCompartmentMapping: " + compartmentName
					+ " (containment: " + containment.getName() + ")");
		Compartment compartment = DGenHelpers.findCompartment(canvas,
				compartmentName);
		CompartmentMapping compartmentMapping = null;
		if (compartment != null) {
			compartmentMapping = DGenHelpers
					.createCompartmentMapping(compartment);
		} else
			cerror("---------- createCompartmentMapping: no compartment ("
					+ containment.getName() + " - " + compartmentName + ")"); // FP140109//FP140120
		return compartmentMapping;
	}

	public GmfMapGenerator(GmfGraphGenerator gmfgraphgenerator,
			IErrorReporter logger, String layer,
			IPaletteManager paletteManager, String pluginId, String filePath,
			EPackage ePackage, DAnnotationParser parser,
			IDiagraphParser diagraphParser, ToolRegistry registry,
			Canvas canvas, boolean validate, boolean canvasComposite) {
		super(logger, layer, pluginId, filePath, ePackage, parser,
				diagraphParser, canvasComposite);
		this.gmfgraphgenerator = gmfgraphgenerator;
		this.toolRegistry = registry;
		this.canvas = canvas;
		this.paletteManager = paletteManager;
		this.mustValidate = validate;
	}

	private int countCompartmentMappings() {
		int r = 0;
		for (TopNodeReference topNodeReference : mapping.getNodes()) {
			r += countCompartmentMapping(topNodeReference.getOwnedChild()); // FP140121zz
		}
		return r;
	}

	private void checkCompartmentMappings_wait_nu() {
		EList<TopNodeReference> nodrefs = mapping.getNodes();
		for (TopNodeReference noderef : nodrefs) {
			if (LOG)
				clog("check topnoderef:"
						+ (noderef.getContainmentFeature() != null ? noderef
								.getContainmentFeature().getName() : "????"));
			// NodeMapping nm = noderef.getChild();
			NodeMapping oc = noderef.getOwnedChild();
			// EReference cf_ = noderef.getContainmentFeature();
			EList<ChildReference> crefs = oc.getChildren();
			for (ChildReference childReference : crefs) {
				if (childReference.getContainmentFeature() != null) {
					// clog(childReference.getContainmentFeature().getName());
					CompartmentMapping cmap = childReference.getCompartment();
					// if (cmap == null)
					// System.out
					// .println("!!!!!  no compartment mapping for child reference in "
					// + noderef.getContainmentFeature()
					// .getName());
					if (LOG && cmap != null)
						clog("compartment mapping="
								+ cmap.getCompartment().getName()
								+ " for child reference in "
								+ noderef.getContainmentFeature().getName());
				}
			}
			// EReference ref = noderef.getChildrenFeature();
		}
	}

	private boolean mustValidate = false;

	void log_(NodeMapping nm, int depth) {
		GmfLogger.logNodeMapping(nm, depth);
		// for (ChildReference childReference : nm.getChildren())
		// log(childReference.getChild(),++depth);
	}

	private int countCompartmentMapping(NodeMapping nodeMapping) {
		int r = nodeMapping.getCompartments().size();
		for (ChildReference child : nodeMapping.getChildren())
			r += countCompartmentMapping(child.getChild());
		return r;
	}

	public Mapping getMapping() {

		if (mapping == null) {
			mesgcount = 0;
			if (LOG) {

				clog("getMappings");
			}
			List<DiaNode> nodes = annotationParser.getInternalModel()
					.getDiaNodes();

			processDomainModel(DPhase.MAP);
			validateDomainModel(DPhase.MAP);

			mustValidate = createValidators(DPhase.MAP);

			// FP2612 sortTopNodes(mapping);
			if (LOG) {
				clog("NodeMappings");
				for (TopNodeReference topnodeReference : mapping.getNodes()) {
					// GmfLogger.logNodeMapping(topnodeReference.getOwnedChild(),
					// 0);
					log_(topnodeReference.getOwnedChild(), 0);
				}

				clog("LinkMappings");
				EList<LinkMapping> linkm = mapping.getLinks();
				for (LinkMapping linkMapping : linkm)
					GmfLogger.logLinkMapping(linkMapping);

			}

			if (LOG) {
				clog("logs 140408");
				String log = "";
				for (DiaNode diaNode : nodes) {
					log += "\n"
							+ (diaNode.getEClass().isAbstract() ? "(abstract) "
									: "           ") + diaNode.getName()
							+ " containments:[";
					for (IDiaContainment c : diaNode.getContainments())
						log += c.getName() + " ";
					log += "] nodeMappings:"
							+ diaNode.getNodeMappings_().size()
							+ " compartmentMappings:"
							+ diaNode.getCompartmentMappingSize()
							+ " childrefs:"
							+ diaNode.getChildReferences_().size();
				}
				clog(log);

			}

		}
		return mapping;
	}

	private void clog2_(String mesg) {
		if (LOG)
			System.out.println(mesg);

	}

	// @Override
	private EObject getRoot() {
		return getMapping();
	}

	@Override
	public Object processReference_(IDiaSyntaxElement ref, boolean oriented,
			boolean unique_) {// FP120621a
		DiaReference reference = (DiaReference) ref;
		if (!unique_
				|| DGenHelpers.findLinkMapping(mapping.getLinks(), reference
						.getTargetReference().getName()) == null) {
			CreationTool ct = DGenHelpers.findCreationToolByTitle(toolRegistry,
					reference.getDefaultName());
			LinkMapping lmapping = DGenHelpers.createReferenceMapping(
					reference.getTargetReference(), ct,
					DGenHelpers.findConnection(canvas, reference.getName()));
			if (lmapping.getDiagramLink() == null)
				throw new RuntimeException(
						"fatal error in processReference for " + ref.getName()); // FP120621d
			addToMapping(lmapping);
			return lmapping;
		}
		return null;
	}

	/****************** snippets ***************************/
	private void createMissing3_test_() {
		NodeMapping jonode = findNodeMapping(getNodeMappings(), "Jo");
		NodeMapping smartynode = findNodeMapping(getNodeMappings(), "Smarty");
		DiaNode smartydianode = (DiaNode) findDiaSyntaxElement("Smarty");
		ChildReference smartyref = org.isoe.diagraph.gen.gmf.util.DGenHelpers
				.createChildReference_(smartynode, (EReference) smartydianode
						.getContainmentReferenceBase());
		jonode.getChildren().add(smartyref);
		Compartment smartiesJocompartment = DGenHelpers.findCompartment(canvas,
				"smartiesJo");
		CompartmentMapping smartiesJocompartmentMapping = DGenHelpers
				.createCompartmentMapping(smartiesJocompartment);
		smartyref.setCompartment(smartiesJocompartmentMapping);
	}

	private void snippet_createMissing2_test_() {
		NodeMapping jonode = findNodeMapping(getNodeMappings(), "Jo");

		DiaLabel jolabel = (DiaLabel) findDiaSyntaxLabel("Jo", "JoValue");

		EList<LabelMapping> labelMappings = jonode.getLabelMappings();

		Compartment smartiesJocompartment = DGenHelpers.findCompartment(canvas,
				"smartiesJo");
		clog(smartiesJocompartment.getName());

		NodeMapping smartynode = findNodeMapping(getNodeMappings(), "Smarty");
		DiaNode smartydianode = (DiaNode) findDiaSyntaxElement("Smarty");

		DiaLabel smartylabel = (DiaLabel) findDiaSyntaxLabel("Smarty",
				"SmartyBarbaz");

		DiagramLabel diagramLabel = DGenHelpers.findLabel(canvas,
				smartylabel.getName());

		List<EAttribute> attrs = new ArrayList<EAttribute>();
		attrs.add(smartylabel.getEAttribute());

		FeatureLabelMapping smartyLabel = DGenHelpers
				.createFeatureLabelMapping(attrs,
						(smartylabel).getAttributeSeparators_(), diagramLabel);

		smartynode.getLabelMappings().add(smartyLabel);

		ChildReference smartyref = DGenHelpers.createChildReference_(
				smartynode,
				(EReference) smartydianode.getContainmentReferenceBase());
		smartyref.setOwnedChild(smartynode);
		jonode.getChildren().add(smartyref);
		CompartmentMapping smartiesJocompartmentMapping = DGenHelpers
				.createCompartmentMapping(smartiesJocompartment);
		smartyref.setCompartment(smartiesJocompartmentMapping);
	}

	private void snippet_createMissing_test() {
		NodeMapping jonode = findNodeMapping(getNodeMappings(), "Jo");
		NodeMapping smartynode = findNodeMapping(getNodeMappings(), "Smarty");

		GmfLogger.logNodeMapping(jonode, 0);
		GmfLogger.logNodeMapping(smartynode, 0);

		EList<LabelMapping> labelMappings = jonode.getLabelMappings();
		for (LabelMapping labelMapping : labelMappings) {
			clog(labelMapping.getDiagramLabel().getName());
		}
		Compartment smartiesJocompartment = DGenHelpers.findCompartment(canvas,
				"smartiesJo");
		clog(smartiesJocompartment.getName());
		DiaNode smartydnode = (DiaNode) findDiaSyntaxElement("Smarty");

		DiaLabel label = (DiaLabel) findDiaSyntaxLabel("Smarty", "SmartyBarbaz");

		DiagramLabel diagramLabel = DGenHelpers.findLabel(canvas,
				label.getName());

		List<EAttribute> attrs = new ArrayList<EAttribute>();
		attrs.add(label.getEAttribute());

		FeatureLabelMapping smartyLabel = DGenHelpers
				.createFeatureLabelMapping(attrs,
						label.getAttributeSeparators_(), diagramLabel);

		smartynode.getLabelMappings().add(smartyLabel);

		ChildReference smartyref = DGenHelpers.createChildReference_(
				smartynode,
				(EReference) smartydnode.getContainmentReferenceBase());
		smartyref.setOwnedChild(smartynode);
		jonode.getChildren().add(smartyref);

		CompartmentMapping smartiesJocompartmentMapping = DGenHelpers
				.createCompartmentMapping(smartiesJocompartment);

		smartyref.setCompartment(smartiesJocompartmentMapping);
	}

	static int mesgcount = 0;

	protected void clog(String mesg) {
		;
		boolean num = true;
		if (DParams.COMPOSITE_LOG) {

			System.out.println((num ? "LOG_GmfMapGenerator " + mesgcount++
					+ " - " : "")
					+ mesg);
		}

	}

	// /////////// mappings new ///////////////////////

	private DiaNode isComponentRoleOfCanvas(DiaNode targetNode) {
		DiaNode result = null;
		int count = 0;
		if (targetNode.isAbstract()) {
			List<IDiaNode> nods = targetNode.getSubNodes();
			for (IDiaNode subnod : nods) {
				if (subnod.isCanvas()) {
					result = (DiaNode) subnod;
					count++;
				}
			}
		}
		return count == 1 ? result : null;
	}

	private IDiaContainment getCanvasCompositeContainment(DiaNode diaNode) {
		if (diaNode == null)
			throw new RuntimeException(
					"should not happen in getCanvasCompositeContainment");
		if (diaNode.getContainments().isEmpty())
			return null;
		IDiaContainment result = null;
		List<IDiaContainment> conts = diaNode.getContainments();
		for (IDiaContainment cont : conts) {
			boolean match = isComponentRoleOfCanvas((DiaNode) cont
					.getTargetNode()) != null
					&& cont.getSourceNode().getName()
							.equals(cont.getNameIfShared());
			if (match)
				result = cont;
			if (LOG)
				clog((match ? "** " : "--") + cont.getSourceNode().getName()
						+ "->" + cont.getContainmentName_() + " - "
						+ cont.getTargetReference().getName() + " - "
						+ cont.getNameIfShared());
		}
		return result;
	}

	private DiaNode getCanvasCompositeRole(DiaNode diaNode) {// FP150527

		if (LOG)
			clog("getNodeIfSubnodeCanvas for " + diaNode.getName());
		if (diaNode.getContainments().isEmpty()) {
			if (LOG)
				clog("containments empty");
			return null;
		}
		IDiaContainment containm = getCanvasCompositeContainment(diaNode);
		IDiaNode reslt = containm != null ? containm.getTargetNode() : null;
		return (DiaNode) reslt;
	}/*
	 *
	 * if (LOG) { List<IDiaContainment> conts = diaNode.getContainments(); for
	 * (IDiaContainment cont : conts) {
	 *
	 *
	 * if (cont.getNameIfShared().equals("Leaf")) tb = true; boolean ok =
	 * isComponentRoleOfCanvas((DiaNode) cont .getTargetNode()) != null; if (ok)
	 * tb = true;
	 *
	 *
	 * boolean ok2 = ok &&
	 * cont.getSourceNode().getName().equals(cont.getNameIfShared());
	 *
	 *
	 * clog((ok2 ? "** " : "--") + cont.getSourceNode().getName() + "->" +
	 * cont.getContainmentName_() + " - " + cont.getTargetReference().getName()
	 * + " - " + cont.getNameIfShared());
	 *
	 * } }
	 *
	 *
	 *
	 *
	 * IDiaContainment containmen = diaNode.getContainments().get(0); //
	 * FP150526voir1 DiaNode targetNode = (DiaNode) containmen.getTargetNode();
	 * DiaNode result = null; int count = 0; if (targetNode.isAbstract()) {
	 * List<IDiaNode> nods = targetNode.getSubNodes(); for (IDiaNode subnod :
	 * nods) { if (subnod.isCanvas()) { result = (DiaNode) subnod; count++; } }
	 * } if (count == 1) return result; else return null; }
	 */

	private Object[] createNodeMapping_(DiaNode diaNode, IDiagraphNode dn_nu,
			boolean top, int depth) {
		DiaNode targetNode = getCanvasCompositeRole(diaNode);
		Object[] result = createMapping(diaNode, targetNode, dn_nu, top, depth);
		NodeMapping parentNodeMapping = result != null ? (NodeMapping) result[0]
				: null;
		return result;
	}

	private void logTopNode(DStatement statement) {
		;
		DiaNode node = (DiaNode) statement.getElement();
		if (node.isTopNode())
			clog("tn=" + node.getName());
		else
			clog("not tn=" + node.getName());

		clog("PTN " + this.getClass().getSimpleName() + " processTopNode - "
				+ statement);


		// PTN GmfMapGenerator processTopNode - [2001]MAP_NODE - Foo -name Foo
		clog("containments ");
		if (node.getContainments().size() > 1)
			for (IDiaContainment iDiaContainment : node.getContainments()) {
				clog(iDiaContainment.getSourceNode().getName() + "-"
						+ iDiaContainment.getTargetReference().getName() + "-"
						+ iDiaContainment.getTargetNode().getName());
			}
	}

	@SuppressWarnings("restriction")
	private Object[] processTopNode(DStatement statement, DCommand_ force) {// FP150527voir
		Object[] result = null;
		MappingEntry mapping = null;
		String sp = "";
		int depth = 0;
		if (LOG)
			logTopNode(statement);
		DiaNode node = (DiaNode) statement.getElement();

		IDiaContainment containment_ = node.getContainments().isEmpty() ? null
				: getCanvasCompositeContainment(node); // FP150526voir1
														// //FP150527
		// DiaNode targ = (DiaNode) containment_.getTargetNode();

		if (node.isTopNode() || (force != null && node.isCanvas())) { // FP150524

			IDiagraphNode dn_nu = null;

			NodeMapping nm = findTopNodeMapping((EClass) node
					.getEModelElement());
			if (nm != null) {
				result = new Object[2];
				result[0] = nm;
				result[1] = node;
				if (LOG)
					clog("in processTopNode allready exists nodemapping for "
							+ (EClass) node.getEModelElement());
			} else
				result = createTopNodeMappingAndSubNodes(node, dn_nu, 0);

			if (result != null && force != null) {// FP150525composite
				Object[] args1 = new Object[2];
				args1[0] = (NodeMapping) result[0];
				args1[1] = (DiaNode) result[1];
				DiaNode r = getCanvasCompositeRole((DiaNode) result[1]);


				if (LOG)
					clog("addRecursiveSubTopNodeMappings");
				if (containment_ != null)
					addRecursiveSubTopNodeMappings__(args1, dn_nu, sp, depth);

			}
		} else if (LOG)
			clog("is not a TopNode " + statement.getDiagramElementName());

		return result;
	}

	private void addRecursiveSubTopNodeMappings__(Object[] args,
			IDiagraphNode dn_nu, String sp, int depth) {
		depth++;
		if (LOG)
			clog("addRecursiveSubTopNodeMappins depth= " + depth);
		Object[] result = addSubTopNodeMappings_(args, dn_nu, depth, sp);
		if (result != null && depth < 8)
			addRecursiveSubTopNodeMappings__(result, dn_nu, sp, depth);
	}

	private void addTopNode_(TopNodeReference topNodeReference, IDiagraphNode dn) {
		if (LOG)
			clog("add TopNode toMapping: "
					+ topNodeReference.getChild().getDiagramNode().getName());
		NodeMapping nodeMapping_ = topNodeReference.getOwnedChild(); // FP130914
		EClass eclaz = nodeMapping_.getDomainMetaElement();
		DiaNode diaNode = (DiaNode) findDiaSyntaxElement(eclaz);
		addtopNodeMappingAndTool(topNodeReference, diaNode, dn, nodeMapping_);
		diaNode.addNodeMapping(nodeMapping_, eclaz.getName());
		if (LOG)
			logs(diaNode, diaNode, nodeMapping_, dn, "");
		addLabels(diaNode, diaNode, nodeMapping_, "");
		logChildrenNodes(diaNode, nodeMapping_, dn, 0, 0, ""); // FP150430
		addchildNodeMappings__(diaNode, nodeMapping_, dn, 0, 0, ""); // FP150524compo
	}

	private Object[] createTopNodeMappingAndSubNodes(DiaNode node,
			IDiagraphNode dn_nu, int depth) {
		if (LOG)
			clog("getTopNodeMappingAndSubNodes " + node.getName());
		return createNodeMapping_(node, dn_nu, true, depth); // FP150502
	}

	private List<NodeMapping> getAllMappings() {
		List<NodeMapping> nm = new ArrayList<NodeMapping>();
		nm.addAll(topNodeMappings_);
		nm.addAll(childrenMappings);// FP150414compo
		return nm;
	}

	@SuppressWarnings("restriction")
	private void processTopNodeCanvasInheritance(DStatement statement, int id) {
		DiaNode node = annotationParser.getInternalModel().findDiaNode(// FP150318t
				statement.getDiagramElementName());
		for (IDiaContainment containment : node.getContainments()) {
			if (containment.getSourceNode().isCanvas()
					&& containment.isPropagated()) {
				// if (LOG)
				if (LOG)
					clog("PCanvasINHeritance " + containment.getName());
				DiaNode tnod = (DiaNode) containment.getTargetNode();
				if (tnod == null) {
					throw new RuntimeException(
							"target node is null for containment propagated on canvas: "
									+ containment.getName());
				}
				// if (LOG)
				if (LOG)
					clog("PCanvasINHeritance " + tnod.getName());
				IDiagraphNode dn_nu = null;
				// addTopNodeMapping(tnod, temp, id++); //FP150502 // FP130620

				if (tnod.getContainments().size() > 1) // FP150524
					throw new RuntimeException(
							"should not happen in processCanvasInheritance");

				// IDiaContainment containmen =
				// getCanvasCompositeContainment(tnod); //FP150627
				//IDiaContainment containmen_nu = tnod.getContainments().get(0); // FP150526voir1
				// DiaNode targ_ = containmen!=null?(DiaNode)
				// containmen.getTargetNode():null;

				//DiaNode targ_nu = getCanvasCompositeRole(tnod);

				Object[] r = new Object[2];

				NodeMapping nm = findTopNodeMapping((EClass) node
						.getEModelElement());
				if (nm != null) {
					r[0] = nm;
					r[1] = node;
					if (LOG)
						clog("in processCanvasInheritance allready exists nodemapping for "
								+ (EClass) node.getEModelElement());
				} else

					r = createTopNodeMappingAndSubNodes(tnod, dn_nu, 0);
			}
		}
	}

	private void addchildNodeMappings__(DiaNode diaNode,
			NodeMapping nodeMapping, IDiagraphNode dn, int id, int depth,
			String sp) {
		IDiaNode cnv = getConcreteCanvas(diaNode); // FP150430
		if (cnv != null) {
			diaNode = (DiaNode) cnv;
			if (LOG)
				clog("abstract, concrete canvas = " + cnv.getName());
		}
		doAddchildNodeMappings_(diaNode, dn, nodeMapping, // null,
				id, depth, sp);
	}

	private void doAddchildNodeMappings_(DiaNode diaNode, IDiagraphNode dn_,
			NodeMapping nodeMapping, // ChildReference child,
			int id, int depth, String sp) {
		int subid_ = 0;
		boolean hasderiv = diaNode.hasDerivedSubNode();
		if (LOG) {
			clog("addchildNodeMappings " + diaNode.getName() + ":");
			for (IDiaContainment containment : diaNode.getContainments())
				clog(" containment " + containment.getSourceNode().getName()
						+ "--" + containment.getName() + "->"
						+ containment.getTargetNode().getName());
		}
		for (IDiaContainment containment : diaNode.getContainments()) {
			DiaNode targ = (DiaNode) containment.getTargetNode();
			if (!targ.isAbstract()) { // FP150514bb
				addNodeAndContMappingAndChildren__(diaNode, containment, targ,
						dn_, nodeMapping, // ChildReference child,
						id, subid_, depth, sp);
			}
		}
	}

	private void doAddchildNodeMappings2___(DiaNode diaNode,
			IDiagraphNode dn_nu, NodeMapping nodeMapping, // ChildReference
															// child,
			int id, int depth, String sp) {
		addNodeAndContMappingAndChildren__(diaNode, diaNode.getContainments()
				.get(0), diaNode, dn_nu, nodeMapping, id, 0, depth, sp);
	}

	private Object[] addNodeAndContMappingAndChildren__(DiaNode diaNode,
			IDiaContainment containment, DiaNode targ, IDiagraphNode dn_,
			NodeMapping nodeMapping, // ChildReference child,
			int id, int subid, int depth, String sp) { // FP150514bb
		boolean hasderiv = diaNode.hasDerivedSubNode();
		int dep = DParams.MAX_DEPTH_;
		String arg = containment.getArgument();
		if (arg != null && !arg.isEmpty())
			dep = Integer.parseInt(arg); // FP150512transp1

		// DiaNode targ = (DiaNode) containment.getTargetNode();
		IDiagraphNode idiagraphNode = parser2.getDiagraphNode(
				targ.getSemanticRole(), 0);
		int recursivedepth = idiagraphNode.getRecursiveDepth();

		if (depth < dep && recursivedepth < dep) { // FP150519voir //
													// FP140121
													// FP140331xxx
			int n = id + (subid++);

			return addNodeAndContainmentMappingAndChildren__(containment,
					diaNode, dn_, nodeMapping, id, depth, sp, n, hasderiv, true);// FP150505
		} else if (LOG)
			clog("recursion stopped, mas depth = " + dep);
		return null;

	}

	void log2(IDiagraphNode idiagraphNode, EClass modelElement) {

		// FP140407
		// EReference aoc_ = idiagraphNode
		// .getAutoCompartmentByAbstractUpper_nu();

		List<IDiagraphReferenceAssociation> sibls = idiagraphNode
				.getAllSiblingContainments();
		for (IDiagraphReferenceAssociation sibl : sibls) {
			clog(sibl.toString());

		}

		List<EReference> aotkbsups = idiagraphNode
				.getAbstractAutoCompartmentBySupers();
		for (EReference aotkbsup : aotkbsups) {
			if (aotkbsup != null) {
				clog(aotkbsup.getName());
				EClass cc = aotkbsup.getEContainingClass();
			}
		}

		// String icn=cc.getInstanceClassName();
		// EStructuralFeature ssft=aotkbsup.eContainingFeature();

		// idiagraphNode.getAbstractAutoCompartment();
		// clog(aoc_ == null ? "" : aoc_.getName());
		List<EReference> kacrs = idiagraphNode
				.getKrefAltContainmentReferences();
		for (EReference kacr : kacrs) {
			clog(kacr.getName());
		}

		List<EReference> autocontes = parser2
				.patternContainmentAssociationsKrefAutoCBySub(modelElement,
						true);

		if (DParams.containment_LOG)
			clogk(uniqueid + " ]" + (sibls.isEmpty() ? "" : "sibls") + " "
					+ (kacrs.isEmpty() ? "" : "kacrs") + " "
					+ (aotkbsups.isEmpty() ? "" : "aotkbsups") + " "
					+ (autocontes.isEmpty() ? "" : "autocontes") + " ");

	}

	void log1(int depth, int recursivedepth, DiaNode concreteNode) {
		// adding nodeMapping Required to NestedComponent
		clog("CNM---" + (depth == 0 ? "TOP" : "CHILD")

		+ " depth=" + depth + " rdepth=" + recursivedepth
				+ " ------- createNodeMapping: " + concreteNode.getName());
		IDiagraphNode pov = parser2.getCurrentPointOfView().getPov();
	}

	// FP150424composite
	private Object[] createNodeMapping(DiaNode node, boolean top, int id,
			int depth) { // FP130914

		Object[] result = new Object[2];
		// DiaNode concreteNode_ = node;
		if (LOG)
			// adding nodeMapping Required to NestedComponent
			clog("CNM for " + parser2.getCurrentView() + "." + node.getName()); //
		boolean check = false;
		if (LOG)
			clog("\n[---");
		uniqueid++;

		IDiagraphNode idiagraphNode = parser2.getDiagraphNode(
				node.getSemanticRole(), 0);

		int recursivedepth = idiagraphNode.getRecursiveDepth();

		if (LOG)
			log1(depth, recursivedepth, node);

		if (false && ((EClass) node.getEModelElement()).isAbstract()) // FP131120xx
			throw new RuntimeException(
					"see error10 case: abstract class cannot be a concrete node for "
							+ node.getName());

		// EModelElement modelElement = node.getEModelElement();

		EReference containmentRefBase = null;
		EReference oncanvas = null;
		EReference containmentRefAlt = null;

		if (!node.isCanvas()) {

			if (LOG)
				clog(idiagraphNode.toString());

			containmentRefBase = (EReference) node
					.getContainmentReferenceBase();


			// idiagraphNode.isRecursiveCompartment(eReference);

			oncanvas = node.getContainmentOnCanvas(); // FP140221xxxaaabbb
			containmentRefAlt = (EReference) node.getContainmentReferenceAlt();

			if (oncanvas != null
					&& containmentRefAlt == null
					&& (containmentRefBase == null || node
							.getDiagraphContainment(containmentRefBase) == null)) {// FP130431B//isDiagraphContainment_(containmentRef)){

				containmentRefBase = (EReference) node.getContainmentOnCanvas();
			}

			if (oncanvas != null && containmentRefBase == null
					&& containmentRefAlt != null) { // FP150514b //done too late
				containmentRefBase = containmentRefAlt;
				if (LOG)
					clog(containmentRefBase.getName() + " b");
			}

			if (containmentRefBase == null) { // FP130209 //done too
												// late
				EClass eclaz = (EClass) node.getEModelElement();
				EReference cf = (EReference) eclaz.eContainingFeature();
				containmentRefBase = cf;
				if (LOG)
					clog(containmentRefBase.getName() + " a");
			}

			if (LOG) {
				EClass eclaz_ = (EClass) node.getEModelElement();
				clog(eclaz_.getName());
				// clog(eclaz_.eContainer().getClass().getName());
				String msg = "validating " + "Containment feature ["
						+ containmentRefBase.getName() + ":"
						+ containmentRefBase.getEType().getName() + "] -> "
						+ node.getName();

				clog(msg);

			}

		} else {

			if (LOG) {
				EClass eclaz_ = (EClass) node.getEModelElement();
				clog(eclaz_.getName());
				// clog(eclaz_.eContainer().getClass().getName());
				String msg = "validating " + node.getName();

				clog(msg);

			}
		}

		if (!node.isCanvas()) {

			boolean containmentNotNull_ = true;
			boolean notCommonGeneric = false;
			boolean containingNotNull = true;

			validate_(containmentRefBase, node, containmentNotNull_,
					notCommonGeneric, containingNotNull, depth == 0, depth,
					recursivedepth); // FP140121
		}

		String title = node.getName();
		if (node.getLabels() != null && !node.getLabels().isEmpty())
			title = node.getLabels().get(0);// FP121008
		CreationTool gmftool = DGenHelpers.findCreationToolByTitle(
				toolRegistry, title);
		Node gmfnode = DGenHelpers.findNode(canvas, node.getName());
		// NodeMapping nodeMapping = null;
		if (gmfnode != null) { // FP130806
			if (LOG)
				log2(idiagraphNode, (EClass) node.getEModelElement());

			List<EReference> autocontes = parser2
					.patternContainmentAssociationsKrefAutoCBySub(
							(EClass) node.getEModelElement(), true);
			// FP140407
			EReference autocont = autocontes.isEmpty() ? null : autocontes
					.get(0); // FP140415
			// parser2.getKrefAutoContainmentBySubNode((EClass) modelElement,
			// true, view);

			if (!node.isCanvas()) {

				if (autocont != null) {
					if (parser2.getRecursiveCompartment(
							(EClass) node.getEModelElement(), title) != null) // FP140415
						containmentRefAlt = autocont;
				}

				if (autocont == null) {// FP140407b
					List<EReference> superautoconts = idiagraphNode
							.getAbstractAutoCompartmentBySupers(); // FP150318
					autocont = superautoconts.isEmpty() ? null : superautoconts
							.get(0);// sorted ?
					containmentRefAlt = autocont == null ? containmentRefAlt
							: autocont; // FP140416
				}
			}

			int instancedepth = depth - idiagraphNode.getDepth();

			boolean first_ = instancedepth < 1;
			boolean ttb = false;
			EReference cont = null;
			EReference decl = idiagraphNode.getDeclaredContainment();

			if (decl == null) {
				cont = containmentRefBase;
				if (!first_ && containmentRefAlt != null)
					cont = containmentRefAlt; // FP140218
				if (DParams.containment_LOG)
					clogk(node.getName() + " ]  decl="
							+ (decl == null ? "null" : decl.getName())
							+ " instancedepth=" + instancedepth + " cont="
							+ cont.getName());
			} else {
				cont = decl; // FP140417aaa
				if (!first_ && containmentRefAlt != null)
					cont = containmentRefAlt; // FP140218
				if (DParams.containment_LOG)
					clogk(node.getName() + " ]  decl="
							+ (decl == null ? "null" : decl.getName())
							+ " instancedepth=" + instancedepth + " cont="
							+ cont.getName());

			}

			// FP140416aaaa

			String log = "";
			if (LOG || DParams.containment_LOG) {
				try {

					log = "depth="
							+ depth
							+ " instancedepth="
							+ instancedepth
							+ " cont="
							+ (node.getContainer() == null ? "nil" : node
									.getContainer().getSourceNode().getName())
							+ " " + ((EClass) cont.eContainer()).getName()
							+ "." + cont.getName() + ":"
							+ ((EClass) node.getEModelElement()).getName();

					if (DParams.containment_LOG)
						clogk(log);

				} catch (Exception e) {
					cerror("err before hook");
				}
			}

			idiagraphNode.hook_("5_createNodeMapping", log);

			if (node.isAbstract()) {
				List<IDiaNode> acs_ = node.getSubNodes();
				if (acs_.isEmpty())
					throw new RuntimeException(
							"should not happen in addNodeAndCompartmentMappingsAndChildren");

			}
			/*
			 * result = new Object[2]; NodeMapping nm =
			 * findTopNodeMapping((EClass) node.getEModelElement());
			 *
			 * if (top && nm!=null){ result[0] = nm; result[1] = node; if (LOG)
			 * clog("allready exists topnoemapping for "+((EClass)
			 * node.getEModelElement()).getName()); } else{ result =
			 * addNodeMappingAndChildReference( top, //nodeMapping, gmfnode,
			 * gmftool, node, idiagraphNode, //(EClass) modelElement, cont,
			 * depth, log); ChildReference added = (ChildReference) result[1];
			 * result[1] = node; }
			 */

			result = addNodeMappingAndChildReference(top,
			// nodeMapping,
					gmfnode, gmftool, node, idiagraphNode,
					// (EClass) modelElement,
					cont, depth, log);
			ChildReference added = (ChildReference) result[1];
			result[1] = node;

			/*
			 * Object[] mapping =
			 * DGenHelpers.createNodeMappingAndChildReference_( // FP140121pbici
			 * gmftool, gmfnode, (EClass) modelElement, cont); if (LOG)
			 * clog("\n---]"); nodeMapping = (NodeMapping) mapping[0]; if
			 * (nodeMapping != null) if (top) topNodeMappings_.add(nodeMapping);
			 * else childrenMappings.add(nodeMapping);
			 *
			 * ChildReference justadded = (ChildReference) mapping[1];
			 *
			 * concreteNode.addNodeMapping(nodeMapping, ((EClass)
			 * nodeMapping.getDomainMetaElement()).getName());// , //
			 * justadded); // // // FP140417xy
			 *
			 * idiagraphNode.addNodeMapping(nodeMapping, justadded);
			 *
			 * IDiagraphMapping dnm_ = new DiagraphMapping(idiagraphNode,
			 * nodeMapping, justadded, uniqueid, depth, "NodeMapping_info " +
			 * log);
			 *
			 * if (LOG) GmfLogger.logNodeMapping(nodeMapping, 0);
			 */

			if (DParams.EXPERIMENT_OCL)
				createLibraryConstraint(node, (NodeMapping) result[0]);
		} else {
			if (!node.getSemanticRole().isAbstract())
				cerror("no gmfnode found for " + node.getName());
		}

		// result[0] = mapping[0];
		// result[1] = node;
		return result;
	}

	private NodeMapping findTopNodeMapping(EClass eclaz) {
		NodeMapping result = null;
		int count = 0;
		for (NodeMapping m : topNodeMappings_) {
			if (m.getDomainMetaElement() == eclaz) {
				result = m;
				count++;
			}
		}
		return count == 1 ? result : null;
	}

	private Object[] addNodeMappingAndChildReference(boolean top,

	Node gmfnode, CreationTool gmftool, DiaNode node,
			IDiagraphNode idiagraphNode, EReference cont, int depth, String log) {

		NodeMapping nodeMapping = null;
		if (LOG)
			clog("creating " + (top ? "TOP" : "CHILD") + "nodeMapping for "
					+ ((EClass) node.getEModelElement()).getName());

		Object[] result_ = DGenHelpers.createNodeMappingAndChildReference_(

		gmftool, gmfnode, (EClass) node.getEModelElement(), cont);
		if (LOG)
			clog("\n---]");

		// node.getEModelElement()
		nodeMapping = (NodeMapping) result_[0];
		if (nodeMapping != null)
			if (top)
				topNodeMappings_.add(nodeMapping);
			else
				childrenMappings.add(nodeMapping);
		ChildReference justadded = (ChildReference) result_[1];
		node.addNodeMapping(nodeMapping,
				((EClass) nodeMapping.getDomainMetaElement()).getName());// ,
																			// justadded);
																			// //
																			// FP140417xy
		idiagraphNode.addNodeMapping(nodeMapping, justadded);
		IDiagraphMapping dnm_ = new DiagraphMapping(idiagraphNode, nodeMapping,
				justadded, uniqueid, depth, "NodeMapping_info " + log);
		if (LOG)
			GmfLogger.logNodeMapping(nodeMapping, 0);
		return result_;
	}

	/***********
	 * not used***************
	 *
	 */

	@Override
	public boolean processContainment(DStatement statement) {
		// nothing, is done with nodemappings
		return false;// FP150331a307

	}

	// //////// not used ////////////////////////////

}
