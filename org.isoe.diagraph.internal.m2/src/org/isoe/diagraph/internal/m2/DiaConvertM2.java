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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.isoe.diagraph.dgi.DGenericElement;
import org.isoe.diagraph.dgi.DLabelledElement;
import org.isoe.diagraph.diagraph.DAffixedEdge;
import org.isoe.diagraph.diagraph.DCompartmentEdge;
import org.isoe.diagraph.diagraph.DContainment;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.diagraph.DGraphElement;
import org.isoe.diagraph.diagraph.DLabel;
import org.isoe.diagraph.diagraph.DLabeledEdge;
import org.isoe.diagraph.diagraph.DLabeledElement;
import org.isoe.diagraph.diagraph.DNestedEdge;
import org.isoe.diagraph.diagraph.DNode;
import org.isoe.diagraph.diagraph.DOwnedEdge;
import org.isoe.diagraph.diagraph.DReference;
import org.isoe.diagraph.diagraph.DSimpleEdge;
import org.isoe.diagraph.diagraph.DiagraphFactory;
import org.isoe.diagraph.diagraph.helpers.IGraphHandler;
import org.isoe.diagraph.diastyle.DStyle;
import org.isoe.diagraph.factory.DGraphFactory;
import org.isoe.diagraph.internal.api.IDiaNamedElement;
import org.isoe.diagraph.internal.api.IDiaNode;
import org.isoe.diagraph.internal.api.IDiaPointOfView;
import org.isoe.diagraph.internal.m2.api.IDiaContainment;
import org.isoe.diagraph.internal.m2.parser.DAnnotation;
import org.isoe.diagraph.lang.DiagraphKeywords;
import org.isoe.diagraph.parser.api.IDiagraphNotation;
import org.isoe.diagraph.runner.IDiagraphRunner;
import org.isoe.diagraph.views.ViewConstants;
import org.isoe.diagraph.workbench.api.IDiaConverter;
import org.isoe.diagraph.workbench.api.IDiagraphFactoryClient;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.utils.ResourceManager;

/**
 *
 * @author pfister
 *
 */
public class DiaConvertM2 implements IDiaConverter, IDiagraphFactoryClient {

	private static final boolean LOG = DParams.DiaConvertM2_LOG;

	private DiaGraph diagraph;
	// private IGraphHandler genDiagraphM2;

	private DGraph graph;
	private String logContainm;
	private StringBuffer genLog;
	private DStyle style;
	private IDiagraphRunner runner;
	private String layer;
	private IGraphHandler graphHandler;

	public DiaConvertM2(DiaGraph diaGraph, IDiagraphRunner runner, String layer) {
		this.diagraph = diaGraph;
		this.runner = runner;
		this.layer = layer;
	}

	@Override
	public String getView() {
		return layer;
	}

	public void setStyle(DStyle style) {
		this.style = style;
	}





	public Resource saveDgrf(String pluginID, String povSuffix, String filePath) {
		String suffixedFilePath = filePath;
		if (povSuffix != null)
			suffixedFilePath += povSuffix;


		if (LOG)
		  validateM2_("saveDgrf");

		try {
			return ResourceManager.save(diagraph.getResourceData(),
					suffixedFilePath + "." + DParams.DIAGRAPH_SUFX, graph);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private void genNodesContainements() {
		String n = "";
		for (DiaNode node : diagraph.getDiaNodes()) {
			DNode dgrfnod = graphHandler.findDNode(node.getName());
			if (dgrfnod != null) { // FP150423azvoir
				n = dgrfnod.getName();
				DNode containmentNode = null;
				if (node.getContainmentElement() != null)
					containmentNode = graphHandler.findDNode(node
							.getContainmentElement().getName());
				if (containmentNode != null) {
					clog(n + " cont = " + containmentNode.getName());
					dgrfnod.setOwner(containmentNode);
				} else
					clog(n + " cont = null");
			} else
				clog(n + " not a node: " + node.getName());

		}
	}

	private DAffixedEdge createAffixedEdge(IDiaContainment cont, DNode srcNode,
			DNode tgtNode, boolean abztr) { // FP130319ppp
		DAffixedEdge added = graphHandler.addPortContainment(
				cont.getName(), // FP150526voir
				srcNode, tgtNode, cont.getTargetReference(),
				cont.isPropagated(), abztr); // FP120923//FP120704a

		String toLog = " added AffixedEdge:     " + added.getSource().getName()
				+ "." + added.getName() + " semanticRole="
				+ added.getSemanticRole().getName()
				+ (added.isPropagated() ? " propagated" : "")
				+ (added.isAbztract() ? " abstract" : "");

		genLog.append(toLog).append('\n');
		if (LOG)
			clog(toLog);
		return added;
	}

	@Override
	public Object callBack(Object added) {
		String toLog = "";
		if (added instanceof DContainment) {
			DContainment created = (DContainment) added;
			toLog = " added Containment: " + created.getName() + " to "
					+ created.getNode().getName();
			genLog.append(toLog).append('\n');
			if (LOG)
				clog(toLog);

		}
		return toLog;
	}

	private DNestedEdge createNestedEdge(IDiaContainment cont, DNode srcNode,
			DNode tgtNode, boolean abztr) { // FP120618
		int depth = 0;

		String contarg = cont.getArgument();
		if (cont.isCompartment() && contarg != null)
			depth = Integer.parseInt(contarg);




		DNestedEdge added = graphHandler.addNestedEdge(cont.getName(), srcNode,
				tgtNode, cont.getTargetReference(), cont.isPropagated(),cont.isDerived(),
				cont.isCompartment(), cont.isPort(), depth,// FP150512transp1
				cont.getName(), abztr); // FP120923//FP120704a

		String toLog = "";//

		DContainment containment = added.getSource();

		if (added instanceof DCompartmentEdge) {

			DCompartmentEdge comp = (DCompartmentEdge) added;
			toLog += " added " + "CompartmentEdge: "
					+ added.getSource().getName() + "." + added.getName()
					+ " semanticRole=" + added.getSemanticRole().getName()
					+ (added.isPropagated() ? " propagated" : "")
					+ (added.isAbztract() ? " abstract" : "");
			toLog += " depth=" + comp.getDepth();
			toLog += " partitionName=" + comp.getPartitionName();
		} else {
			toLog += " added " + "NestedEdge:      "
					+ added.getSource().getName() + "." + added.getName()
					+ " semanticRole=" + added.getSemanticRole().getName()
					+ (added.isPropagated() ? " propagated" : "")
					+ (added.isAbztract() ? " abstract" : "");
		}

		genLog.append(toLog).append('\n');
		if (LOG)
			clog(toLog);
		return added;
	}

	private void logContainments() {
		// logContainm = "";
		clog("===========  CONTAINMENTS  ============= ");
		String log = "";
		List<DiaNode> nods = diagraph.getDiaNodes();
		for (DiaNode diaNode : nods)
			log += diaNode.getName() + ";";
		clog("containments for nodes " + log);
		IDiaPointOfView pov_nu = diagraph.getDiaPointOfView();

		IDiaNode povnod_nu=pov_nu.getNode();

		for (DiaNode node : nods) {
			for (IDiaContainment diaContainment : node.getContainments()) {// FP150318t //FP150601 see containment if missingcref
				// logContainm += diaContainment.toString() + "\n";
				DiaNode tnode = (DiaNode) diaContainment.getTargetNode();
				boolean propag = diaContainment.isPropagated();
				DNode tgtnode = null;
				DNode srcnode = null;
				DOwnedEdge edge = null;
				if (tnode != null)
					tgtnode = graphHandler.findDNode(diaContainment
							.getTargetNode().getName());
				if (tgtnode != null) { //
					srcnode = graphHandler.findDNode(diaContainment
							.getSourceNode().getName());
					if (srcnode != null) {
						boolean abztr = ((EClass) srcnode.getSemanticRole())
								.isAbstract();
						// if (!abztr|| !DParams.SKIP_ABSTRACT_nu) {
						if (diaContainment.isPort()) // FP130319ppp
							clog("\n" + (abztr ? "abstract " : "")
									+ " edge port " + srcnode.getName() + "-"
									+ diaContainment.getName() + "->"
									+ tgtnode.getName());
						else
							clog("\n" + (abztr ? "abstract " : "")
									+ " edge containment " + srcnode.getName()
									+ "-" + diaContainment.getName() + "->"
									+ tgtnode.getName());
						// } else // FP150423azvoir
						// if (LOG)
						// clog(srcnode.getName()
						// + " is abstract, no containment generated");
					}
				}

			}
		}
		clog("===========  END CONTAINMENTS  ============= ");
	}

	@SuppressWarnings("restriction")
	private void genContainmentsForNodes() {
		;
		logContainm = "";
		for (DiaNode node : diagraph.getDiaNodes()) {
			for (IDiaContainment diaContainment : node.getContainments()) {// FP150318t
				logContainm += diaContainment.toString() + "\n";
				genContainment(diaContainment,false);
			}
		}
	}


	@SuppressWarnings("restriction")
	private void genContainmentsForPov_nu() {
		;
		IDiaPointOfView  pov = diagraph.getDiaPointOfView();
		logContainm += "\npov\n";
		List ctns=pov.getContainments();
		for (Object obj : ctns) {
			DiaContainment diaContainment = (DiaContainment) obj;
			logContainm += diaContainment.toString() + "\n";
			genContainment(diaContainment,true);
		}
	}

	private void genContainment(IDiaContainment diaContainment, boolean onPov) {
		;
		DiaNode tnode = (DiaNode) diaContainment.getTargetNode();
		// boolean propag = diaContainment.isPropagated();
		DNode tgtnode = null;
		DNode srcnode = null;
		DOwnedEdge edge = null;
		boolean abztr = false;
		if (tnode != null)
			tgtnode = graphHandler.findDNode(diaContainment.getTargetNode()
					.getName());
		if (tgtnode != null) { //
			if (((EClass) tgtnode.getSemanticRole()).isAbstract())
				abztr = true;
			srcnode = graphHandler.findDNode(diaContainment.getSourceNode()
					.getName());
			if (srcnode != null) {
				if (((EClass) srcnode.getSemanticRole()).isAbstract())
					abztr = true;
				if (LOG)
					clog("\n"
							+ (abztr ? "abstract " : "")
							+ (diaContainment.isPort() ? " port" : "")
							+ (diaContainment.isDerived() ? " derived" : "")
							+ (diaContainment.isCompartment() ? " compartment"
									: "")
							// +(diaContainment.isDerivedCompartment()?" derivedCompartment":"")
							+ (diaContainment.isPropagated() ? " propagated"
									: "") + " edge containment="
							+ srcnode.getName() + "-"
							+ diaContainment.getName() + "->" + " target="
							+ tgtnode.getName());
				if (abztr)
					diaContainment.setAbstract(true);

				edge = findContainment(srcnode.getName(),
						diaContainment.getName(), tgtnode.getName()); // FP150526
				if (edge != null) {
					if (LOG)
						clog("allready exists");
				} else
					edge = createContainmentOrPort(diaContainment, srcnode,
							tgtnode, abztr);
			}
		}
		if (edge == null && srcnode != null
				&& !((EClass) srcnode.getSemanticRole()).isAbstract()) {
			String tname = "null";
			try {
				tname = diaContainment.getTargetReference().getName();
			} catch (Exception e) {
				throw new RuntimeException("error in genContainments ("
						+ srcnode.getName() + " - " + diaContainment.getName()
						+ ")");
			}
			genLog.append( // FP140126
					" no containment added: " + diaContainment.getName() + "."
							+ tname).append('\n');
		}

	}



	private DOwnedEdge findContainment(String srcName, String containmentName,
			String targetName) {
		return graphHandler
				.finContainment(srcName, containmentName, targetName);
	}

	private DOwnedEdge createContainmentOrPort(IDiaContainment diaContainment,
			DNode srcnode, DNode tgtnode, boolean abztr) {
		DOwnedEdge result = null;
		if (diaContainment.isPort()) // FP130319ppp
			result = createAffixedEdge(diaContainment, srcnode, // FP150516
					tgtnode, abztr);
		else
			result = createNestedEdge(diaContainment, srcnode, tgtnode, abztr);
		return result;
	}

	private void genContainments_old_nu() {
		logContainm = "";
		for (DiaNode node : diagraph.getDiaNodes()) {
			for (IDiaContainment diaContainment : node.getContainments()) {// FP150318t
				logContainm += diaContainment.toString() + "\n";
				DiaNode tnode = (DiaNode) diaContainment.getTargetNode();
				boolean propag = diaContainment.isPropagated();
				DNode tgtnode = null;
				DNode srcnode = null;
				DOwnedEdge edge_ = null;
				boolean abztr = false;

				if (tnode != null)
					tgtnode = graphHandler.findDNode(diaContainment
							.getTargetNode().getName());
				if (tgtnode != null) { //
					if (((EClass) tgtnode.getSemanticRole()).isAbstract())
						abztr = true;
					srcnode = graphHandler.findDNode(diaContainment
							.getSourceNode().getName());
					if (srcnode != null) {
						if (((EClass) srcnode.getSemanticRole()).isAbstract())
							abztr = true;

						if (LOG)
							clog("\n" + (abztr ? "abstract " : "")
									+ " edge containment " + srcnode.getName()
									+ "-" + diaContainment.getName() + "->"
									+ tgtnode.getName());
						// if (!abztr|| !DParams.SKIP_ABSTRACT) {
						// if (!abztr) {

						if (abztr) {
							diaContainment.setAbstract(true);
							// throw new
							// RuntimeException("should not happen in genContainments");
						}// FP150513transpvoir
						if (diaContainment.isPort()) // FP130319ppp
							edge_ = createAffixedEdge(diaContainment, srcnode, // FP150516
									tgtnode, abztr);
						else
							edge_ = createNestedEdge(diaContainment, srcnode,
									tgtnode, abztr);
						// } else; // FP150423azvoir
						// if (LOG)
						// clog(srcnode.getName()
						// + " is abstract, no containment generated");
					}
				}
				if (edge_ == null && srcnode != null
						&& !((EClass) srcnode.getSemanticRole()).isAbstract()) {
					String tname = "null";
					try {
						tname = diaContainment.getTargetReference().getName();
					} catch (Exception e) {
						runner.cerror("error in genContainments ("
								+ node.getName() + " - "
								+ diaContainment.getName() + ")");
					}

					genLog.append( // FP140126
							" no containment added: "
									+ diaContainment.getName() + "." + tname

					).append('\n');
				}
			}
		}
	}

	public EAttribute getFirstLabelAttribute(DLabeledElement el) { // FP130125
		List<DLabel> dlabels = el.getDlabels();
		if (dlabels.size() > 0)
			return dlabels.get(0).getAttribute();
		return null;
	}

	public List<EAttribute> getLabelAttributes(DLabeledElement el) { // FP130615ok
		List<EAttribute> result = new ArrayList<EAttribute>();
		List<DLabel> dlabels = el.getDlabels();
		for (DLabel dLabel : dlabels)
			result.add(dLabel.getAttribute());
		return result;
	}

	private String getFirstLabelAttributeName_(DLabeledElement LabeledElement) {
		EAttribute flab = getFirstLabelAttribute(LabeledElement);
		if (flab != null)
			return flab.getName();
		else
			return "null label";
	}

	private String getLabelAttributeNames(DLabeledElement LabeledElement) {
		String result = "";
		List<EAttribute> attributes = getLabelAttributes(LabeledElement);
		if (attributes.isEmpty())
			result = "no labels";
		for (EAttribute eAttribute : attributes)
			result += eAttribute.getName() + " ";
		result = result.trim();
		return result;
	}

	private void genLabels() {
		;
		List<DiaContainedElement> dels = diagraph.getAllDiaNodesAndLinks();
		for (DiaContainedElement diaAbstractElement : dels) {
			for (IDiaNamedElement nmel : diaAbstractElement.getNamedChildren()) {
				DiaLabel diaLabel_ = (DiaLabel) nmel;
				DGraphElement owner_ = graphHandler.findElement(diaLabel_
						.getOwner().getName());
				// boolean skip = false;
				boolean abztract = false;
				if (owner_ instanceof DNode) {
					DNode dNode = (DNode) owner_;
					EClass me = (EClass) dNode.getSemanticRole();
					// if (me.isAbstract() && DParams.SKIP_ABSTRACT)
					// skip = true; // FP150423azvoir2
					abztract = me.isAbstract(); // FP150514
				} else if (owner_ instanceof DLabeledEdge) {

					DLabeledEdge edg = (DLabeledEdge) owner_;
					EClass me = (EClass) edg.getSemanticRole();
					abztract = me.isAbstract(); // FP150514

				}
				/*
				 * else if (owner_ instanceof DSimpleEdge){ DSimpleEdge edg =
				 * (DSimpleEdge) owner_; EReference ref= (EReference)
				 * edg.getSemanticRole(); EClass claz=(EClass) ref.getEType();
				 * abztract = claz.isAbstract(); //FP150514 tb = true; }
				 */
				// if (!skip) {
				String name = diaLabel_.getName();
				EAttribute eAttribute = diaLabel_.getEAttribute();
				boolean inSuperType = diaLabel_.isInSuperType();
				boolean inferred = diaLabel_.isInferred();

				DLabeledElement towner = graphHandler.addLabel(owner_, name,
						eAttribute, inSuperType, inferred, abztract);
				if (towner == null)
					genLog.append(" !!!!  added: " + "null label").append('\n');
				else
					genLog.append(
							" added labels: " + towner.getName() + "."
									+ getLabelAttributeNames(towner)).append(
							'\n');
				// } else if (LOG)
				// clog("owner " + owner_.getName()
				// + " is abstract, no label!"); // FP150423azvoir

				// }
			}
		}

	}

	private void logLabels() {
		List<DiaContainedElement> dels = diagraph.getAllDiaNodesAndLinks();
		for (DiaContainedElement diaAbstractElement : dels) {
			for (IDiaNamedElement nmel : diaAbstractElement.getNamedChildren()) {
				DiaLabel diaLabel_ = (DiaLabel) nmel;
				DGraphElement owner_ = graphHandler.findElement(diaLabel_
						.getOwner().getName());
				// boolean skip = false;
				if (owner_ instanceof DNode) {
					DNode dNode = (DNode) owner_;
					EClass me = (EClass) dNode.getSemanticRole();

					// if (me.isAbstract() && DParams.SKIP_ABSTRACT)
					// skip = true; // FP150423azvoir2
				}
				// if (!skip) {
				String name = diaLabel_.getName();
				EAttribute eAttribute = diaLabel_.getEAttribute();
				boolean inSuperType_ = diaLabel_.isInSuperType();
				boolean inferred_ = diaLabel_.isInferred();

				String firstLabelAttributeName = graphHandler.logLabel(owner_,
						name, eAttribute, inSuperType_, inferred_);
				if (firstLabelAttributeName == null)
					genLog.append(" !!!! null label").append('\n');
				else
					genLog.append(
							" added label: " + owner_.getName() + "."
									+ firstLabelAttributeName).append('\n');
				// } else if (LOG)
				// clog("owner " + owner_.getName()
				// + " is abstract, no label!"); // FP150423azvoir

			}
		}

	}

	private void log2(IDiaPointOfView parentPointOfView, IDiaPointOfView pov) {
		clog(pov.getSuffix());
		if (parentPointOfView != null) {
			clog(parentPointOfView.getElementName());
			if (parentPointOfView.getNode() != null)
				clog("parent nodename:" + parentPointOfView.getNode().getName());
		}
	}

	private void allNodesGenNavigation() {
		List<DNode> dnods = graphHandler.getAllNodes();
		boolean isNavigation = false;
		for (DNode dNode : dnods) {
			DiaNode node = diagraph.findDiaNode(dNode.getName());
			String povname = DAnnotation.getPointOfViewName1(node, layer);// FP140216
			genLog.append("genNavigations: " + dNode.getName())
					.append(povname != null ? " povname=" + povname : "")
					.append('\n');
			if (LOG)
				clog("genNavigation: " + dNode.getName());
			String targetView_ = node.getNavigation();
			DGraph dgraph = (DGraph) dNode.eContainer();
			String graphLayerName = dgraph.getViewName(); // FP121102
			if (graphLayerName == null || graphLayerName.isEmpty())
				graphLayerName = ViewConstants.DIAGRAPH_DEFAULT_VIEW_LITTERAL;
			String currentView = graphLayerName
					+ ViewConstants.VIEW_SEPARATOR_1 // FP121102
					+ ViewConstants.ROOT_NAME;

			String toLog = " added navigation: " + currentView + " -> "
					+ targetView_;
			if (LOG)
				clog(toLog);// FP121022xxx
			if (targetView_ != null) {
				isNavigation = true;
				genLog.append(toLog).append('\n');
			}
			if (!isNavigation)
				genLog.append(" no navigation").append('\n');
			dNode.setNavigationLink(targetView_);// FP121124x
		}
	}

	public boolean isDefaultView(EAnnotation anot) {
		for (Map.Entry<String, String> entry : anot.getDetails())
			if (entry.getKey().startsWith(DiagraphKeywords.VIEW_EQU))
				return false;
		return true;
	}

	public boolean isView(EAnnotation anot) { // FP131208
		if (!anot.getSource().equals(DiagraphKeywords.DIAGRAPH_LITTERAL))
			return false;
		if (layer == null || layer.equals(DiagraphKeywords.DIAGRAPH_DEFAULT)
				|| layer.isEmpty())
			return isDefaultView(anot);
		for (Map.Entry<String, String> entry : anot.getDetails())
			if (entry.getKey().equals(DiagraphKeywords.VIEW_EQU + layer))
				return true;
		return false;
	}

	private EAnnotation isView(EClass eclaz) {
		for (EAnnotation eachannot : eclaz.getEAnnotations())
			if (isView(eachannot))
				return eachannot;
		if (LOG)
			clog("EClass " + eclaz.getName()
					+ " has no annotation(2) for the view "
					+ ((layer == null || layer.isEmpty()) ? "default" : layer));
		return null;
	}

	private void genGenerics() {// FP140518zz
		for (DLabelledElement element : diagraph.getLabelledElements_()) {
			if (element instanceof DGenericElement) {
				DGenericElement gel = (DGenericElement) element;
				DLabeledElement exists = graphHandler.findGeneric(element.getName());
				if (exists == null)
				   graphHandler.addGeneric(element.getName(), getLabels(gel),
						(EClass) gel.getEModelElement()); // FP12;
			}
		}
	}

	private List<String> getLabels(DGenericElement el) {
		List<String> labels = new ArrayList<String>();
		EAnnotation anot = isView((EClass) el.getEModelElement());
		if (anot != null)
			for (Map.Entry<String, String> entry : anot.getDetails())
				if (entry.getKey().startsWith(DiagraphKeywords.LABELEQ))
					labels.add(entry.getKey().substring(
							DiagraphKeywords.LABELEQ.length()));
		return labels;
	}

	private List<String> getLabels(DiaNode node) {
		List<String> labels = new ArrayList<String>();
		EAnnotation anot = isView((EClass) node.getEModelElement());
		if (anot != null)
			for (Map.Entry<String, String> entry : anot.getDetails())
				if (entry.getKey().startsWith(DiagraphKeywords.LABELEQ))
					labels.add(entry.getKey().substring(
							DiagraphKeywords.LABELEQ.length()));
		return labels;
	}

	private void genLinks() {
		;
		for (DiaNode node : diagraph.getDiaNodes()) {
			for (DiaLink diaLink : node.getDiaLinks()) {

			     DLabeledEdge exists = graphHandler.findLink(
							diaLink.getName());
			     if (exists==null){

				if (diaLink.getTargetNode() == null)
					throw new RuntimeException(
							"verify target annotation for Link "
									+ diaLink.getName() + " in "
									+ node.getName());
				if (diaLink.getSourceNode() == null)
					throw new RuntimeException(
							"verify source annotation for Link in "
									+ node.getName());
				DNode source = graphHandler.findDNode(diaLink.getSourceNode()
						.getName());
				DNode target = graphHandler.findDNode(diaLink.getTargetNode()
						.getName());
				EClass eClass_ = (EClass) diaLink.getEModelElement();
				EReference sourceReference = diaLink.getSourceReference();
				EReference targetReference = diaLink.getTargetReference();
				EReference containementReference = (EReference) diaLink
						.getContainmentReferenceBase();
				if (sourceReference == null)
					if (LOG)
						clog("genLinks: sourceReference is null for "
								+ diaLink.getName());
				if (targetReference == null)
					throw new RuntimeException(
							"genLinks: targetReference should not be null for "
									+ diaLink.getName() + " !!!!");
				if (containementReference == null)
					throw new RuntimeException(
							"genLinks: containementReference should not be null for "
									+ diaLink.getName() + " !!!!");

				diaLink.getOwnLabels();




				DLabeledEdge labeledEdge = graphHandler.addLink(
						diaLink.getName(), diaLink.getLabels(), eClass_,
						source, target, containementReference, sourceReference,
						targetReference);
				if (labeledEdge == null)
					genLog.append(" !!!!added: " + "null link").append('\n');
				else {
					String toLog = " added link: "
							+ labeledEdge.getName()
							+ ((diaLink.getLabels() != null && !diaLink
									.getLabels().isEmpty()) ? (" labels=" + diaLink
									.getLabels().get(0)) : " no labels");
					genLog.append(toLog).append('\n');

					// ("-" + diaLink.getLabels().get(0)) : "").append('\n')

					if (LOG)
						clog(toLog);
				}
			}
		}
		}

	}

	private void genReferences() {
		for (DiaNode node : diagraph.getDiaNodes()) {
			for (DiaReference diaReference : node.getReferences()) {

				DReference exists = graphHandler.findReference(diaReference.getTargetReference());
				if (exists==null){

				if (diaReference.getTargetNode() == null)
					throw new RuntimeException(
							"verify target annotation for Reference "
									+ diaReference.getName() + " in "
									+ node.getName());
				String name = diaReference.getName();
				DNode source = graphHandler.findDNode(diaReference
						.getSourceNode().getName());
				DNode target = graphHandler.findDNode(diaReference
						.getTargetNode().getName());
				EReference targetReference = diaReference.getTargetReference();

				DReference dref = graphHandler.addReference(name, source,
						target, targetReference, diaReference.isPropagated());
				if (dref == null)
					genLog.append(
							" !!!!  added: " + "null reference " + "from "
									+ node.getName()).append('\n');
				else
					genLog.append(" added ref: " + dref.getName()).append('\n');
			}
			}
		}
	}

	private void genSuperElements() {
		// nothing
	}

	private void convertM2() {
		// graph = genDiagraphM2.getGraph();
		runner.setDGraph(graph); // FP121022
		genLog = new StringBuffer();
		genLog.append("root created, view=" + graph.getViewName()).append('\n');
		int step = -1;
		try {
			graphHandler.initGraph(graph);
			step = 2;
			genNodes();
			//graphHandler.endGraph(graph);
			//genPov();
			//graphHandler.endGraph(graph);
			step = 3;
			genGenerics();
			//graphHandler.endGraph(graph);
			step = 4;
			genLinks();
			//graphHandler.endGraph(graph);
			step = 5;
			genReferences();
			//graphHandler.endGraph(graph);
			step = 6;
			if (LOG)
				logContainments(); // FP150424b
			genContainmentsForNodes();
			//graphHandler.endGraph(graph);
			//genContainmentsForPov();
			step = 7;
			genNodesContainements();
			//graphHandler.endGraph(graph);
			step = 8;
			genSuperElements();
			//graphHandler.endGraph(graph);
			step = 9;
			if (false && LOG)
				logLabels(); // FP150424b
			genLabels();
			//graphHandler.endGraph(graph);
			step = 10;
			allNodesGenNavigation();

			graphHandler.endGraph(graph);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("error " + step + " in gensyntax !!!! "
					+ e.toString());
		}

	}

	public void convert(DStyle style) {
		graph = DiagraphFactory.eINSTANCE.createDGraph();
		graph.setViewName(layer);
		//graphHandler = new DGraphFactory(graph, this); // FP150526
		graphHandler = DGraphFactory.getInstance(graph, this);//FP150601azer
		graph.setFacade1(graphHandler); // FP140520
		graphHandler.setStyle(style);
		if (LOG)
			clog("convert " + graph.getViewName());
		convertM2();// FP140617voir4
        if (LOG)
		  validateM2_("convert");

		if (LOG) {
			clog("\n----------genlog-----------\n");
			clog(genLog.toString());
			clog("----------genlog-----------\n");

		}
	}

	private void validateM2_(String wh) {



	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	@Override
	public int getPhase() {
		return runner.getPhase();
	}

	private void genNodes() {




		for (DiaNode node : diagraph.getDiaNodes()) {


			DNode exists = graphHandler.findDNode(node.getName());
			if (exists == null){


			Entry<String, String> navProperty = DAnnotation
					.getNavigationProperty((EClass) node.getEModelElement());
			String targetView = null;
			if (navProperty != null) {
				targetView = navProperty.getKey();
				targetView = targetView.substring("nav:".length());
				if (LOG)
					clog("navigation=" + targetView);
			}
			List<String> labs = getLabels(node);
			if (LOG && labs.isEmpty())
				clog("node " + node.getName() + " has no labels in view "
						+ layer);
			String viewName = node.isCanvas() ? layer : null;

			if (LOG && viewName != null)
				clog("pov=" + viewName);

			boolean isabstract = ((EClass) node.getEModelElement())
					.isAbstract();


			DNode dnod_ = graphHandler.addNode(node.getName(), labs,
					node.getEModelElement(), viewName, targetView, isabstract); // FP121124y

			if (dnod_ != null) {
				genLog.append(" added node: " + dnod_.getName())
						.append(viewName != null ? " pov=" + viewName : "")
						.append('\n');
				if (LOG)
					clog(" added node: " + dnod_.getName()
							+ (viewName != null ? " pov=" + viewName : ""));
			} else {
				genLog.append(" !!!! added: " + "null node").append('\n');
				throw new RuntimeException("!!!! added null node in genNodes");
			}
			} else
				if (LOG)
					clog(" allready exists node: " + exists.getName());
		}
	}

	private void genPov() {
		for (DiaNode node : diagraph.getDiaNodes()) {
			Entry<String, String> navProperty = DAnnotation
					.getNavigationProperty((EClass) node.getEModelElement());
			String targetView = null;
			if (navProperty != null) {
				targetView = navProperty.getKey();
				targetView = targetView.substring("nav:".length());
				if (LOG)
					clog("navigation=" + targetView);
			}
			List<String> labs = getLabels(node);
			if (LOG && labs.isEmpty())
				clog("node " + node.getName() + " has no labels in view "
						+ layer);
			String viewName = node.isCanvas() ? layer : null;

			if (LOG && viewName != null)
				clog("pov=" + viewName);

			boolean isabstract = ((EClass) node.getEModelElement())
					.isAbstract();
			// ;
			// if(node.getName().equals("Binding"))
			// tb = true;
			DNode dnod_ = graphHandler.addNode(node.getName(), labs,
					node.getEModelElement(), viewName, targetView, isabstract); // FP121124y

			if (dnod_ != null) {
				genLog.append(" added node: " + dnod_.getName())
						.append(viewName != null ? " pov=" + viewName : "")
						.append('\n');
				if (LOG)
					clog(" added node: " + dnod_.getName()
							+ (viewName != null ? " pov=" + viewName : ""));
			} else {
				genLog.append(" !!!! added: " + "null node").append('\n');
				throw new RuntimeException("!!!! added null node in genNodes");
			}

		}
	}

	private void inferMissingCref(DNode dnod) {
		runner.cerror("inferMissingCref - do nothing");
	}

}
