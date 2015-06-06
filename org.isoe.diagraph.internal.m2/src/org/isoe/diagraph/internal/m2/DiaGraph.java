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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.isoe.fwk.core.DParams;
import org.isoe.diagraph.runner.IDiagraphRunner;
import org.isoe.diagraph.common.DiaColor;
import org.isoe.diagraph.common.DiaLayout;
import org.isoe.diagraph.internal.api.IDiaConcreteSyntax;
import org.isoe.diagraph.internal.api.IDiaNamedElement;
import org.isoe.diagraph.internal.api.IDiaPointOfView;
import org.isoe.diagraph.internal.api.IDiaSyntaxElement;
import org.isoe.diagraph.common.IDiagraph;
import org.isoe.diagraph.dgi.DConcreteSyntax;
import org.isoe.diagraph.dgi.DContainment;
import org.isoe.diagraph.dgi.DElement;
import org.isoe.diagraph.dgi.DGenericElement;
import org.isoe.diagraph.dgi.DLabel;
import org.isoe.diagraph.dgi.DLabelledElement;
import org.isoe.diagraph.dgi.DLink;
import org.isoe.diagraph.dgi.DNode;
import org.isoe.diagraph.dgi.DPointOfView;
import org.isoe.diagraph.dgi.DPoorReference;
import org.isoe.diagraph.diagraph.DGraph;
//import org.isoe.diagraph.diagraph.helpers.IGraphHandler;
import org.isoe.diagraph.diastyle.DStyle;
import org.isoe.diagraph.internal.m2.api.IDiaContainedElement;
import org.isoe.diagraph.internal.m2.api.IDiaContainment;
import org.isoe.diagraph.internal.m2.api.IDiaEdge;
import org.isoe.diagraph.internal.m2.api.IDiaParser;
import org.isoe.diagraph.parser.api.IDiagraphParser;
import org.isoe.diagraph.toemf.Transform;
import org.isoe.diagraph.views.ViewConstants;
import org.isoe.diagraph.workbench.api.IDiaConverter;
import org.isoe.fwk.log.LogConfig;
import org.isoe.fwk.utils.ResourceManager;

/**
 *
 * @author pfister
 *
 */
public class DiaGraph implements IDiagraph {

	private static final boolean LOG = DParams.DiaGraph_LOG;
	private DStyle style;
	private Transform toEmf;
	private List<IDiaEdge> diaEdges;
	private List<DiaNode> diaNodes;
	private List<DiaGenericElement> diaElements;
	private List<IDiaContainment> diaContainments;
	private List<DiaContainedElement> allDiaNodesAndLinks;
	private List<DiaContainedElement> diaContainedElements_;
	private List<IDiaContainment> compartments;
	private List<DLabelledElement> labelledElements;
	private List<IDiaSyntaxElement> concreteElements;
	private List<DiaReference> allDiaReferences;
	private List<DContainment> allDContainments;
	private StringBuffer log;
	private IDiaConverter convertM2;
	private DGraph diagraph;
	private IDiagraphRunner runner;
	private IDiaParser parser;
	private DConcreteSyntax dSyntax;
	private StringBuffer transformLog;
	private String logContainm;
	private IDiaConcreteSyntax dsl;
	private String[] resourceData;
	private String layer;
	private IDiaPointOfView pov;


	public DiaGraph(IDiaParser parser, String layer, IDiaConcreteSyntax dsl,
			IDiagraphRunner runner, List<DiaContainedElement> diagramElements,
			List<IDiaContainment> containments, IDiaPointOfView pov, String[] resourceData,
			List<String> logs) {
		this.parser = parser;
		this.resourceData = resourceData;
		this.dsl = dsl;
		this.diaContainedElements_ = diagramElements;
		this.diaContainments = containments;
		this.pov = pov;
		this.runner = runner;
		this.layer = layer;
	}

	public DiaGraph(IDiagraphParser parser, List<EModelElement> modelElements,
			String view, String[] resourceData) {
		if (DParams.TODO_LOG_)
		   runner.cerror("TODO implement constructor DiaGraph (IDiagraphParser parser, List<EModelElement> modelElements...");
	}

	private void addContainment(IDiaContainment containment,
			DiaContainedElement element, String nodename) {
		if (containment.getTargetNode() == null) { //FP121224bx
			if (!(element instanceof DiaGenericElement))
				throw new RuntimeException(
						"verify target annotation for Containment "+ containment.getName()+" in "
								+ nodename);
			else {
				transformLog
						.append("TargetNode() == null for "
								+ element.getName()
								+ " instanceof DiaGenericElement, no containment added");
			}
		} else {
			DContainment comp = null;
			if (containment.isPort()){ //FP130319ppp
				comp = toEmf.addPort(containment.getName(),
						findDNode((DiaNode) containment.getSourceNode()),
						findDNode((DiaNode) containment.getTargetNode()),
						containment.isPropagated()//FP130619yab
						);
			} else
			   comp = toEmf.addContainment(containment.getName(),
					findDNode((DiaNode) containment.getSourceNode()),
					findDNode((DiaNode) containment.getTargetNode()),
					containment.isCompartment(),
					containment.isPropagated()

					   );
			if (comp == null)
				transformLog.append("!!!!   added: " + "null Containment")
						.append('\n');
			else
				transformLog.append("added: " + comp).append('\n');
		}
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	private String containmentToString(IDiaContainment containment) {
		String targetNodeName = "empty";
		if (containment.getTargetNode() != null)
			targetNodeName = containment.getTargetNode().getName();

		String compar_ = "   <containment> " + containment.getName()
				+ " [embeds] " + targetNodeName;

		compar_+=containment.isPropagated()?" propag ":" not propag ";

		if (containment.getTargetNode() != null
				&& ((DiaNode)containment.getTargetNode()).getNamedChildren().size() > 0) { // FP120125
			compar_ += " [holding labels] ";
			for (IDiaNamedElement nmel : ((DiaNode)containment.getTargetNode())
					.getNamedChildren()) {
				//containment.isPropagated();

				DiaLabel label = (DiaLabel) nmel;
				compar_ += label.getName()
						+ " [points to] "
						+ label.getEAttribute().getEContainingClass()
								.getEPackage().getName() + "."
						+ label.getEAttribute().getEContainingClass().getName()
						+ "." + label.getEAttribute().getName();
			}
		}
		return compar_;
	}

	public void convertM2(DStyle style) {
		//iGenDiagraphM2 =
				getConvertM2(layer).convert(style);
	}

	private String displayGraph() {
		boolean check;
		log = new StringBuffer();//FP150318t
		log.append("=====================graph==========================")
				.append('\n');

		log.append("===================containments=========================")
				.append('\n');
		for (IDiaContainment contain : diaContainments)
			log.append(containmentToString(contain)).append('\n');
		log.append("===================vertices=========================")
				.append('\n');
		for (DiaNode node : getDiaNodes()) {
			String nod = "<node> " + node.getName();
			nod += " [points to] "
					+ ((EClass) node.getEModelElement()).getEPackage()
							.getName() + "."
					+ ((EClass) node.getEModelElement()).getName();
			if (node.isCanvas())
				nod += " (canvas) ";
			if (node.isDiagramRecursive())
				nod += " (recursive) ";
			if (node.isComponent())
				nod += " (componentrole) ";

			log.append(nod).append('\n');

			if (node.getNamedChildren().size() > 0) {
				for (IDiaNamedElement nmel : node.getNamedChildren()) {
					DiaLabel label = (DiaLabel) nmel;
					log.append(
							"   <label> "
									+ label.getName()
									+ " [points to] "
									+ label.getEAttribute()
											.getEContainingClass()
											.getEPackage().getName()
									+ "."
									+ label.getEAttribute()
											.getEContainingClass().getName()
									+ "." + label.getEAttribute().getName())
							.append('\n');
					;
				}
			}
			if (node.getContainments().size() > 0)
				for (IDiaContainment containment : node.getContainments()){
					if (containment.isPropagated())
						check = true;
					log.append(containmentToString(containment)).append('\n');
				}
			if (node.getReferences().size() > 0) {
				for (DiaReference ref : node.getReferences()) {
					log.append(
							"   <reference> "
									+ ref.getName()
									+ " [points to] "
									+ ref.getTargetReference().getEType()
											.getEPackage().getName()
									+ "."
									+ ref.getTargetReference().getEType()
											.getName() + "."
									+ ref.getTargetReference().getName()
									+ " [to] " + ref.getTargetNode().getName())
							.append('\n');
					;
				}
			}
			if (node.getDiaLinks().size() > 0) {
				for (DiaLink lnk : node.getDiaLinks()) {
					log.append(
							"   <link> "
									+ lnk.getName()
									+ " [points to] "
									+ ((EClass) node.getEModelElement())
											.getEPackage().getName()
									+ "."
									+ ((EClass) lnk.getEModelElement())
											.getName()
									+ " [to] "
									+ lnk.getTargetNode().getName()
									+ " [points to] "
									+ ((EClass) lnk.getTargetNode()
											.getEModelElement()).getEPackage()
											.getName()
									+ "."
									+ ((EClass) lnk.getTargetNode()
											.getEModelElement()).getName())
							.append('\n');
					;
					if (lnk.getNamedChildren().size() > 0) {
						for (IDiaNamedElement nmel : lnk.getNamedChildren()) {
							DiaLabel label = (DiaLabel) nmel;
							log.append(
									"      <label> "
											+ label.getName()
											+ " [points to] "
											+ label.getEAttribute()
													.getEContainingClass()
													.getEPackage().getName()
											+ "."
											+ label.getEAttribute()
													.getEContainingClass()
													.getName() + "."
											+ label.getEAttribute().getName())
									.append('\n');
							;
						}
					}
				}
			}
		}
		log.append("====================edges===========================")
				.append('\n');
		for (IDiaEdge nodeConnection : getDiaEdges()) {
			String targetnode = "DGpb1";

			if (nodeConnection.getTargetNode() != null)
				targetnode = nodeConnection.getTargetNode().getName();


			String k = "<connection> " + nodeConnection.getName() + ":"
					+ nodeConnection.getType() + " [from] "
					+ nodeConnection.getSourceNode().getName() + " [to] "
					+ targetnode;
			 if (LOG && k.equals("<connection> K:Link [from] N [to] N"))
				clog(k);
			log.append(k).append('\n');

			// <connection> K:Link [from] N [to] N
		}
		log.append("======================superelements=================")
				.append('\n');
		for (DiaGenericElement el : getDiaElements()) {
			String del = el.getName();
			List<IDiaContainedElement> lowrs = el.getLowerElements();// getLowerElements_old();
			if (lowrs.size() > 0)
				del += " superElement of ";
			for (IDiaContainedElement dAbstractElement : lowrs) {
				del += dAbstractElement.getName() + ";";
			}
			log.append(del).append('\n');
		}
		log.append("=====================end==========================")
				.append('\n');

		return log.toString();
	}

	public void doLog() {
		if (LOG)
		  clog(transformLog.toString());
	}

	/*
	 * public final List<IDiaEdge> getDiaEdges_() { return diaEdges_; }
	 */

	public void doTransform() {
		getDiaNodes();
		getDiaElements();
		List<IDiaEdge> edgs = getDiaEdges();
		if (LOG ) {
			clog("Edges:");
			for (IDiaEdge edg : edgs)
				if (edg instanceof DiaReference)
					clog("+=+=+= ref: " + edg.asString());
		}
		toEmf = new Transform();
		transform();
		if (LOG ) {
			clog("%%%%%%%%%% toEmf transformation %%%%%%%%%%");
			clog(transformLog.toString());
			try {
				String d = displayGraph();
				clog(d);
			} catch (Exception e) {
				System.out.print(log);
				throw new RuntimeException("error while displaying graph !!!");
			}
		}
	}

	private IDiaContainment findCompartment(String eclassname) {
		List<IDiaContainment> compartments = getCompartments();
		for (IDiaContainment compartmentContainment : compartments) {
			EClass eclass = (EClass) compartmentContainment.getEModelElement();
			if (eclass.getName().equals(eclassname))
				return compartmentContainment;
		}
		return null;
	}

	private DElement findContainedElement(String name) {
		for (DElement dContainedElement : getAllDContainedElements_())
			// getDNodesAndLinks())
			if (dContainedElement.getName().equals(name))
				return dContainedElement;
		return null;
	}

	private DGenericElement findDElement(EClass eclass) {
		for (DGenericElement del : toEmf.getDGenericElements())
			if (del.getEModelElement().equals(eclass))
				return del;
		return null;
	}

	private DGenericElement findDElement(String name) {
		for (DGenericElement del : toEmf.getDGenericElements())
			if (del.getName().equals(name))
				return del;
		return null;
	}

	public DiaLink findDiaLink(String name) {
		for (DiaNode dNode : getDiaNodes()) {
			List<DiaLink> lnk = dNode.getDiaLinks();
			for (DiaLink dLink : lnk)
				if (dLink.getName().equals(name))
					return dLink;
		}
		return null;
	}

	public DiaNode findDiaNode(EClass eclass) {
		for (DiaNode dNode : getDiaNodes()) {
			if (dNode.getEModelElement().equals(eclass))
				return dNode;
		}
		return null;
	}

	public DiaNode findDiaNode(String name) {
		for (DiaNode dNode : getDiaNodes()) {
			if (dNode.getName().equals(name))
				return dNode;
		}
		return null;
	}

	private DNode findDNode(DiaNode node) {
		EList<DNode> dnods = dSyntax.getDNodes();
		for (DNode dNode : dnods) {
			if (dNode.getName().equals(node.getName()))
				return dNode;
		}
		return null;
	}

	// public final List<DiaNode> getDiaNodes() {
	// return diaNodes_;
	// }

	private DLabelledElement findLabelledElement(String name) {
		List<DLabelledElement> labelledElements = getLabelledElements_();
		for (DLabelledElement dLabelledElement : labelledElements) {
			if (dLabelledElement.getName().equals(name))
				return dLabelledElement;
		}
		return null;
	}

	private DiaNode findNode(String eclassname) {
		for (DiaNode nod : getDiaNodes()) {
			// DiaNode node = compartmentContainment.getTargetNode();
			EClass eclass = (EClass) nod.getEModelElement();
			if (eclass.getName().equals(eclassname)) {
				return nod;
			}
		}
		return null;
	}

	private DiaNode findNodeInCompartment(String eclassname) {
		List<IDiaContainment> compartments = getCompartments();
		for (IDiaContainment compartmentContainment : getCompartments()) {
			if (compartmentContainment.isPropagated());//
			DiaNode node = (DiaNode) compartmentContainment.getTargetNode();
			EClass eclass = (EClass) node.getEModelElement();
			if (eclass.getName().equals(eclassname)) {
				return node;
			}
		}
		return null;
	}

	private EReference findTargetReference(List<IDiaContainment> conts,
			String containmentName) {
		for (IDiaContainment cont : conts)
			if (containmentName.equals(cont.getName()))
				return cont.getTargetReference();
		return null;
	}

	public List<IDiaSyntaxElement> getAllConcreteElements() {
		if (concreteElements == null) {
			concreteElements = new ArrayList<IDiaSyntaxElement>();
			concreteElements.addAll(getAllDiaNodesAndLinks());
			concreteElements.addAll(getAllDiaReferences_());
		}
		return concreteElements;
	}

	/*
	 *
	 * public final List<DiaGenericElement> getDiaElements() { return
	 * diaElements_; }
	 */

	List<DElement> getAllDContainedElements_() {
		List<DElement> result = new ArrayList<DElement>();
		result.addAll(getDNodesAndLinks_());
		result.addAll(dSyntax.getDElements());
		return result;
	}

	// FP3012
	private List<DContainment> getAllDContainments_() {

		if (allDContainments == null) {
			allDContainments = new ArrayList<DContainment>();
			EList nods = dSyntax.getDNodes();
			for (Object nod : nods) {
				DNode dNode = (DNode) nod;
				EList conts = dNode.getDContainments();
				for (Object c : conts) {
					DContainment cont = (DContainment) c;
					allDContainments.add(cont); // FP120527
				}
			}
		}

		// for (DNode dNode : dSyntax.getDNodes())
		// for (DContainment cont : dNode.getDContainments())
		// result.add(cont);
		return allDContainments;
	}

	public final List<DiaContainedElement> getAllDiaNodesAndLinks() {

		if (allDiaNodesAndLinks == null) {

			allDiaNodesAndLinks = new ArrayList<DiaContainedElement>();
			for (DiaNode dNode : getDiaNodes()) {
				// clog(">>>>"+dNode.getName());
				allDiaNodesAndLinks.add(dNode);
				List<DiaLink> lnk = dNode.getDiaLinks();
				for (DiaLink dLink : lnk)
					allDiaNodesAndLinks.add(dLink);
			}
		}
		return allDiaNodesAndLinks;
	}

	private List<DiaReference> getAllDiaReferences_() {
		if (allDiaReferences == null) {
			allDiaReferences = new ArrayList<DiaReference>();
			for (IDiaEdge edg : getDiaEdges()) {
				if (edg instanceof DiaReference)
					allDiaReferences.add((DiaReference) edg);
			}
		}
		return allDiaReferences;
	}

	public IDiaContainment getCompartment(String eclassName) {
		for (IDiaContainment compart : getCompartments()) {
			if (LOG )
				clog(compart.getName());
			if (compart.getTargetNode().getName().equals(eclassName)) {
				return compart;
			}
		}
		return null;
	}

	public List<IDiaContainment> getCompartments() { // FP120620
		boolean check;
		if (compartments == null) {
			compartments = new ArrayList<IDiaContainment>();
			for (IDiaContainment diaContainment : diaContainments) {
				if (diaContainment .isPropagated())
					check = true;
				if (diaContainment.isCompartment())
					compartments.add(diaContainment);
			}
		}
		return compartments;
	}
/*
	public IDiaConverter getConvertM2() {
		//if (convertM2 == null)
		//convertM2 = new DiaConvertM2(this, runner, layer);
		return convertM2;
	}
	*/


	public IDiaConverter getConvertM2(String layer) {
		if (convertM2 != null){
			if (convertM2.getView()!=layer)
				 convertM2 = new DiaConvertM2(this, runner, layer);
		} else
		   convertM2 = new DiaConvertM2(this, runner, layer);
		return convertM2;
	}

	public List<IDiaContainment> getDiaContainments() {
		return diaContainments;
	}

	private List<IDiaEdge> getDiaEdges() {
		if (diaEdges == null) {//FP150318t
			diaEdges = new ArrayList<IDiaEdge>();
			for (DiaNode dNode : getDiaNodes()) {
				for (IDiaContainment dContainment : dNode.getContainments())
					diaEdges.add((DiaContainment)dContainment);
				for (DiaReference dReference : dNode.getReferences())
					diaEdges.add(dReference);
				for (DiaLink dLink : dNode.getDiaLinks())
					diaEdges.add(dLink);
			}
		}
		return diaEdges;
	}

	private final List<DiaGenericElement> getDiaElements() {
		if (diaElements == null) {
			diaElements = new ArrayList<DiaGenericElement>();
			for (DiaContainedElement del : diaContainedElements_) {
				if (del instanceof DiaGenericElement) {
					DiaGenericElement el = (DiaGenericElement) del;
					diaElements.add(el);
				}
			}
		}
		return diaElements;
		// checkDouble();
	}

	public final List<DiaNode> getDiaNodes() {
		if (diaNodes == null) {
			diaNodes = new ArrayList<DiaNode>();
			for (DiaContainedElement del : diaContainedElements_) {
				if (del instanceof DiaNode) {
					DiaNode nod = (DiaNode) del;
					diaNodes.add(nod);
				}
			}
		}
		return diaNodes;
		// checkDouble();
	}


	public final IDiaPointOfView getDiaPointOfView () {
		return pov;
	}


	private List<DElement> getDNodesAndLinks_() {
		List<DElement> result = new ArrayList<DElement>();
		EList nods = dSyntax.getDNodes();
		for (Object nod : nods) {
			DNode dNode = (DNode) nod;
			result.add(dNode);
			EList links = dNode.getDLinks();
			for (Object link : links) {
				DLink dlink = (DLink) link;
				result.add(dlink);
			}
		}
		/*
		 * for (DNode dNode : dSyntax.getDNodes()) { result.add(dNode); for
		 * (DLink dlink : dNode.getDLinks()) result.add(dlink); }
		 */// FP120527
		return result;
	}

	public IDiaConcreteSyntax getDsl() {
		return dsl;
	}

	public DConcreteSyntax getDSyntax() {
		return dSyntax;
	}
/*
	public IGraphHandler getGenDiagraph() {
		return iGenDiagraphM2;
	}*/

	public List<DLabelledElement> getLabelledElements_() { // FP120620
		if (labelledElements == null) {
			labelledElements = new ArrayList<DLabelledElement>();
			labelledElements.addAll(getAllDContainedElements_());
			labelledElements.addAll(getAllDContainments_());
		}
		return labelledElements;
	}

	public String[] getResourceData() {
		return resourceData;
	}

	public IDiagraphRunner getRunner() {
		return runner;
	}

	public Transform getToEmf() {
		return toEmf;
	}

	@Override
	public boolean isListLayout(String eclassName) { // FP120620
		for (IDiaContainment dContainment : getCompartments()) {
			if (LOG )
				clog(dContainment.getName());
			if (dContainment.getTargetNode().getName().equals(eclassName)) {
				//dContainment.getTargetNode_().isPromoted();
				if (dContainment.getTargetNode().getLayout() == DiaLayout
						.get(DiaLayout.LIST))
					return true;
				else if (dContainment.getTargetNode().getLayout() == DiaLayout
						.get(DiaLayout.FREE))
					return false;
			}
		}
		return false;
	}

	private void logTransformNavigations_() {
		logContainm = "";
		try {
			for (DiaNode node : getDiaNodes()) {
				DNode dginod = toEmf.findNode(node.getName());
				dginod.getNavigations().add(node.getNavigation()); // FP121019xxx
				EObject contnr = dginod.eContainer();
				DConcreteSyntax cs = (DConcreteSyntax) contnr;
				clog("find pointOfView: " + layer
						+ ViewConstants.PREFIXED_ROOT_SUFFIX + " -> "
						+ node.getNavigation()); // FP121222
			}

		} catch (Exception e) {
			throw new RuntimeException(
					"Navigation keyword: error in syntax (dotted separator needed) !!!");
		}
	}
/*
	public void checkDouble_() {
		int count = 0;
		for (DiaNode node : getDiaNodes()) {
			for (DiaLink diaLink : node.getDiaLinks()) {
				if ("N99".equals(diaLink.getName()))
					count++;
			}
		}
	}*/

	private void propagateInheritance() {
		if (LOG )
			clog("transformSuperElements - propagateInheritance");
		// done elsewhere
	}

	public Resource saveDgiTransformation(String pluginID, String povSuffix,
			String filePath) {
		String suffixedFilePath = filePath;
		if (povSuffix != null)
			suffixedFilePath += povSuffix;
		try {
			return ResourceManager.save(resourceData, suffixedFilePath + "."
					+ DParams.DGI_SUFFIX, getToEmf().getSyntax());
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public Resource saveM2_(String plugin, String povsuffix, String path) {
		IDiaConverter cnv = getConvertM2(layer);
		return cnv.saveDgrf(plugin, povsuffix, path);
	}

	@Override
	public void setBackground(String eclassname, DiaColor backgroundcolor) {
		DiaNode node = findNode(eclassname);
		if (node != null) {
			node.setBackGroundColor(backgroundcolor);
			return;
		}
		throw new RuntimeException("should not happen :setBackground");
	}

	@Override
	public void setLayout(String eclassName, DiaLayout layout) {
		DiaNode node = findNode(eclassName);
		if (node != null) {
			node.setLayout(layout);
			return;
		}
		throw new RuntimeException("should not happen :setLayout");
	}

	@Override
	public void setReferenceOrientation(String eclassName,
			String eReferenceName) {
		DiaNode nod = findNode(eclassName);
		if (nod != null) {
			List<DiaReference> references = nod.getReferences();
			for (DiaReference diaReference : references) {
				if (diaReference.getTargetReference().getName()
						.equals(eReferenceName)) {
					diaReference.setOriented_(true);
					return;
				}
			}
		}
		throw new RuntimeException("should not happen :setReferenceOrientation");

	}

	public void setStyle(DStyle styl) {
		this.style = styl;
		IDiaConverter cnv= getConvertM2(layer);
		cnv.setStyle(styl);
	}

	public String toString() {
		String res = "DGpb2";
		try {
			res = displayGraph();
		} catch (Exception e) {
			res = log.toString();
			clog(res);
			throw new RuntimeException("error while displaying graph ");
		}
		return res;
	}

	private void transform() {
		if (LOG )
			clog("DIA transform");
		dSyntax = toEmf.createConcreteSyntax();

		transformLog = new StringBuffer();
		transformLog.append("root created: " + dSyntax).append('\n');
		//transformPointsOfView_nu();
		transformPointsOfView();
		transformNodes();
		transformLinks();
		transformReferences();
		transformContainments();
		transformSuperElements();
		// propagateInheritance_old();
		transformLabels();
		if (LOG )
		  logTransformNavigations_(); // FP121019xxx
	}

	private void transformPointsOfView() {
		System.err.println("TODO Auto-generated method stub in transformPointsOfView");

	}

	private void transformContainments() {
		if (LOG )
			clog("DIA transformContainments");
		logContainm = "";//FP150318t
		for (DiaNode node : getDiaNodes()) {
			for (IDiaContainment diaContainment : node.getContainments()) {
				logContainm += diaContainment.toString() + "\n";
				DiaContainedElement targetElement = null;
				EReference targref = diaContainment.getTargetReference();
				if (targref != null)
					targetElement = parser.findDiagramElement(targref
							.getEReferenceType());
				if (targetElement != null) {
					if (LOG )
						clog(this.resourceData[2] + ", nodeName: "
										+ node.getName() + "  containment: "
										+ diaContainment.getName()
										+ ", targetElement: "
										+ targetElement.getName());

					addContainment(diaContainment, targetElement,
							node.getName());
				} else {
					String mesg = "transformContainments: targetElement is null for node "
							+ node.getName();
					transformLog.append(mesg).append("\n");
					if (LOG )
						clog(mesg);
				}
			}
		}
	}

	private void transformLabels() {
		if (LOG )
			clog("DIA transformLabels");
		List<DiaContainedElement> dels = getAllDiaNodesAndLinks();
		for (DiaContainedElement diaAbstractElement : dels) {
			for (IDiaNamedElement nmel : diaAbstractElement.getNamedChildren()) {
				DiaLabel diaLabel = (DiaLabel) nmel;
				// transformLog.append("TODO: " + diaLabel).append('\n');
				DLabelledElement owner = findLabelledElement(diaLabel
						.getOwner().getName());
				DLabel dlab = toEmf.addLabel(owner, diaLabel.getName(),
						(EModelElement) diaLabel.getEAttribute(),
						diaLabel.isInSuperType(), diaLabel.isInferred());
				if (dlab == null)
					transformLog.append("!!!!  added: " + "null label").append(
							'\n');
				else
					transformLog.append("added: " + dlab).append('\n');
			}
		}
	}

	private void transformLinks() {
		if (LOG )
			clog("DIA transformLinks");
		for (DiaNode node : getDiaNodes()) {

			for (DiaLink diaLink : node.getDiaLinks()) {

				if (diaLink.getTargetNode() == null)
					throw new RuntimeException(
							"verify target annotation for Link in "
									+ node.getName()+" name="+diaLink.getName()+" target="+diaLink.getTarget());
				if (diaLink.getSourceNode() == null)
					throw new RuntimeException(
							"verify source annotation for Link in "
									+ node.getName());
				// assert
				// diaLink.getTargetNode()!=null:"verify target annotation for Link in "+node.getName();
				// assert
				// diaLink.getSourceNode()!=null:"verify source annotation for Link in "+node.getName();
				DLink dlink = toEmf.addLink(diaLink.getName(),
						diaLink.getEModelElement(),
						(EReference) diaLink.getContainmentReferenceBase(),
						findDNode(diaLink.getSourceNode()),
						findDNode(diaLink.getTargetNode()),
						(EModelElement) diaLink.getSourceReference(),
						(EModelElement) diaLink.getTargetReference());
				if (dlink == null)
					transformLog.append("!!!!added: " + "null link").append(
							'\n');
				else {
					transformLog.append("added: " + dlink).append('\n');
					if (LOG )
						clog("added: " + dlink.getName());
				}
			}
		}
	}

	private void transformNodes() {
		if (LOG )
			clog("DIA transformNodes");
		for (DiaNode node : getDiaNodes()) {
			DNode dnod = toEmf.addNode(node.getName(), node.getEModelElement(),
					node.getContainmentReferenceBase(), node.getContainmentReferenceAlt(),node.isDiagramRecursive(),
					node.isCanvas());//FP140213xxxaaa

			if (dnod instanceof DPointOfView){
				DPointOfView pointOfView = (DPointOfView) dnod;
			    clog("DGI Pov="+dnod.getName());
			}



			if (dnod == null)
				transformLog.append("!!!! added: " + "null node").append('\n');
			else {
				transformLog.append("added: " + dnod).append('\n');
				if (LOG )
					clog("added: " + dnod.getName());
			}
		}
	}

	private void transformPointsOfView_nu_() { //FP140616a
		IDiaPointOfView pov= dsl.getPointOfView(); //FP140615a
		//IDiaPointOfView pov_new = dsl.getPointOfView_new(); //FP140615a


		DPointOfView pv = toEmf.createPointOfView_nu();//, null, false
		/*
		List<IDiaPointOfView> povs = dsl.getPointsOfViewAsList();
		for (IDiaPointOfView pov : povs) {
			DPointOfView pv = toEmf.addPointOfView(logContainm, null, false,
					pov.getDepth_obsolete());
		}*/
	}




	private void transformReferences() {
		if (LOG )
			clog("DIA transformReferences");
		for (DiaNode node : getDiaNodes()) {
			for (DiaReference diaReference : node.getReferences()) {
				if (diaReference.getTargetNode() == null)
					throw new RuntimeException(
							"verify target annotation for Reference "+diaReference.getName()+ " in "
									+ node.getName());
				// assert
				// diaReference.getTargetNode()!=null:"verify target annotation for Reference in "+node.getName();
				DPoorReference dref = toEmf.addReference(
						diaReference.getName(),
						findDNode(diaReference.getSourceNode()),
						findDNode(diaReference.getTargetNode()),
						(EModelElement) diaReference.getTargetReference());
				if (dref == null)
					transformLog.append(
							"!!!!  added: " + "null reference " + "from "
									+ node.getName()).append('\n');
				else
					transformLog.append("added: " + dref).append('\n');
			}
		}
	}

	private void transformSuperElements() {
		if (LOG )
			clog("DIA transformSuperElements");
		for (DiaGenericElement diaGenericElement : getDiaElements()) {
			DGenericElement del = toEmf
					.addSuperElement((EModelElement) diaGenericElement
							.getContainmentReferenceBase(), diaGenericElement
							.getEModelElement(), diaGenericElement.getName());
		}
	}



}
