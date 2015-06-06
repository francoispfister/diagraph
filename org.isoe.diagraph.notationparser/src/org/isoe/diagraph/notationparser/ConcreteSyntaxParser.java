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
package org.isoe.diagraph.notationparser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.isoe.diagraph.diagrammable.IDiagrammable;
import org.isoe.diagraph.diagraph.DAffixedEdge;
import org.isoe.diagraph.diagraph.DCompartmentEdge;
import org.isoe.diagraph.diagraph.DContainment;
import org.isoe.diagraph.diagraph.DEdge;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.diagraph.DLabel;
import org.isoe.diagraph.diagraph.DLabeledEdge;
import org.isoe.diagraph.diagraph.DLabeledElement;
import org.isoe.diagraph.diagraph.DNavigationEdge;
import org.isoe.diagraph.diagraph.DNestedEdge;
import org.isoe.diagraph.diagraph.DNode;
import org.isoe.diagraph.diagraph.DOwnedElement;
import org.isoe.diagraph.diagraph.DReference;
import org.isoe.diagraph.diagraph.DiagraphFactory;
import org.isoe.diagraph.factory.DGraphFactory;
import org.isoe.diagraph.generic.GenericConstants;
import org.isoe.diagraph.lang.DiagraphKeywords;
import org.isoe.diagraph.views.ViewConstants;
import org.isoe.diagraph.workbench.api.IDiagraphFactoryClient;
import org.isoe.fwk.core.DParams;

/**
 *
 * @author fpfister
 *
 */
public class ConcreteSyntaxParser implements IDiagraphFactoryClient{
	// org.isoe.diagraph.notationparser.ConcreteSyntaxParser
	/*
	 * another parser : org.isoe.diagraph.internal.m2.parser.DiaParser is
	 * currently used this is a rewrite of the DiaParser, not in use at the
	 * moment, under work
	 */

	private static final boolean LOG = DParams.ConcreteSyntaxParser_LOG;
	private IDiagrammable languageTransformer;
	private EPackage epakage;
	private List<EAnnotation> diagraphAnnotations = new ArrayList<EAnnotation>();
	private List<EAnnotation> allAnnotations = new ArrayList<EAnnotation>();
	private Map<String, DGraph> dgraphs;
	private String views = "";
	private String errorMessage;
	private DGraph dgraph;

	public ConcreteSyntaxParser(IDiagrammable languageTransformer) {
		this.languageTransformer = languageTransformer;
	}

	public List<DGraph> generateConcreteSyntax_(EPackage epackage) {
		prepare(epackage);
		addNodes();
		addEdges();
		addAllNodeLabels();
		addEdgeLabels();
		postParse();
		saveConcreteSyntax(); // n//FP140519
		return getConcreteSyntax();
	}

	private void prepare(EPackage epackage) {
		dgraphs = new HashMap<String, DGraph>();
		epakage = epackage;
		// getDiagraphFactory_();
		readDiagraphAnnotation();
	}

	public List<EAnnotation> parseDiagraphAnnotations() {
		List<EAnnotation> result = new ArrayList<EAnnotation>();
		for (EClassifier eClassifier : epakage.getEClassifiers())
			if (eClassifier instanceof EClass)
				for (EAnnotation annot : ((EClass) eClassifier)
						.getEAnnotations())
					if (annot.getSource().equals(
							GenericConstants.ANNOT_DIAGRAPH_LITTERAL))
						result.add(annot);
		return result;
	}

	public String[] parseViews(List<EAnnotation> annotations) {
		views += "default;";
		for (EAnnotation eAnnotation : annotations)
			for (Entry<String, String> entry : eAnnotation.getDetails()) {
				if (entry.getKey().startsWith(ViewConstants.VIEW_EQ)) {
					String[] pv = entry.getKey().split("=");
					if (!views.contains(pv[1] + ";"))
						views += pv[1] + ";";
				}
			}
		return views.split(";");
	}

	private boolean matchView(String view, EAnnotation eAnnotation) {
		String v = ViewConstants.DIAGRAPH_DEFAULT;
		for (Entry<String, String> entry : eAnnotation.getDetails()) {
			if (entry.getKey().startsWith(ViewConstants.VIEW_EQ)) {
				String[] pv = entry.getKey().split("=");
				v = pv[1];
			}
		}
		return v.equals(view);
	}

	/**
	 * Adds automatically cref roles even if they are not annotated
	 *
	 * @param dgraph
	 * @param eAnnotation
	 * @param owner
	 * @return
	 */
	private List<EReference> getNotTaggeDNestedEdgeReferences(
			EAnnotation eAnnotation, EClass owner) {
		List<EReference> result = new ArrayList<EReference>();
		List<EStructuralFeature> refs = owner.getEStructuralFeatures();
		for (EStructuralFeature sf : refs) {
			if (sf instanceof EReference) {
				EReference ref = (EReference) sf;
				if (ref.isContainment() && !containsEdge(ref.getName())) {
					result.add(ref);
				}
			}
		}
		return result;
	}

	private List<EAttribute> getAttributeLabels_(EAnnotation eAnnotation,
			EClass owner) {
		List<EAttribute> attributes = new ArrayList<EAttribute>();
		for (Entry<String, String> entry : eAnnotation.getDetails()) {
			if (entry.getKey().startsWith(DiagraphKeywords.LABELEQ)) {
				String[] pv = entry.getKey().split("=");
				EAttribute attr = (EAttribute) owner
						.getEStructuralFeature(pv[1]);
				attributes.add(attr);
			}
		}
		return attributes;
	}

	private List<EAttribute> getAttributeMultiLabels(String view,
			EAnnotation eAnnotation, EClass owner) {
		List<EAttribute> attributes = new ArrayList<EAttribute>();
		for (Entry<String, String> entry : eAnnotation.getDetails()) {
			if (entry.getKey().startsWith(DiagraphKeywords.LABELSEQ)) {
				String[] pv = entry.getKey().split("=");
				EAttribute attr = (EAttribute) owner
						.getEStructuralFeature(pv[1]);
				attributes.add(attr);
			}
		}
		return attributes;
	}

	private List<EReference> getEdgeReferences(String keyword,
			EAnnotation eAnnotation, EClass owner) {
		List<EReference> result = new ArrayList<EReference>();
		for (Entry<String, String> entry : eAnnotation.getDetails()) {
			if (entry.getKey().startsWith(keyword + "=")) {
				String[] pv = entry.getKey().split("=");
				EReference erf = (EReference) owner
						.getEStructuralFeature(pv[1]);
				result.add(erf);
			}
		}
		return result;
	}

	private String[] getLabeldLinkParams(EAnnotation eAnnotation) {
		String[] result = new String[3];
		for (Entry<String, String> entry : eAnnotation.getDetails()) {
			if (entry.getKey().startsWith(DiagraphKeywords.LINK_SOURCE_EQU)) {
				String[] pv = entry.getKey().split("=");
				result[DiagraphKeywords.LINK_SOURCE_ID] = pv[1];
			}
			if (entry.getKey().startsWith(DiagraphKeywords.LINK_TARGET_EQU)) {
				String[] pv = entry.getKey().split("=");
				result[DiagraphKeywords.LINK_TARGET_ID] = pv[1];
			}
			if (entry.getKey().startsWith(DiagraphKeywords.CONTAINMENT_EQU)) {
				String[] pv = entry.getKey().split("=");
				result[DiagraphKeywords.CONTAINMENT_ID] = pv[1];
			}
		}
		return result;
	}

	public String[] getViewIds() {
		if (views.isEmpty())
			views = ViewConstants.DIAGRAPH_DEFAULT_SEP;
		return views.split(";");
	}

	private String[] getViews() {// FP121014
		try {
			allAnnotations = parseDiagraphAnnotations();
			parseViews(allAnnotations);
			return getViewIds();
		} catch (Exception e) {
			clog("parse error 3b !!!");
			String[] result = new String[1];
			result[0] = DiagraphKeywords.DIAGRAPH_DEFAULT;
			return result;
		}
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	private boolean exists(EAnnotation eAnnotation, String k) {
		for (String key : eAnnotation.getDetails().keySet())
			if (key.startsWith(k))
				return true;
		return false;
	}

	private String getPath(EClass claz) {
		claz.getEPackage().eResource().getResourceSet();
		for (Resource resource : claz.getEPackage().eResource()
				.getResourceSet().getResources()) {
			String path = resource.getURI().toString();
			if (path.endsWith(".ecore"))
				return path;
		}
		return null;
	}

	private IProject getProject(String name) {
		IWorkspace ws = ResourcesPlugin.getWorkspace();
		org.eclipse.core.runtime.Path rootlocation = (Path) ws.getRoot()
				.getLocation();
		IProjectDescription description = null;
		IProject project = null;
		try {
			description = ResourcesPlugin.getWorkspace()
					.loadProjectDescription(
							new Path(rootlocation.toPortableString() + "/"
									+ name + "/.project"));
			project = ResourcesPlugin.getWorkspace().getRoot()
					.getProject(description.getName());
		} catch (CoreException e) {
			return null;
		}
		return project;
	}

	/*
	 * private void refresh(String projectName) { try {
	 * getProject(projectName).refreshLocal(IResource.DEPTH_INFINITE, new
	 * NullProgressMonitor());
	 * Job.getJobManager().join(ResourcesPlugin.FAMILY_MANUAL_REFRESH, null);
	 * Job.getJobManager().join(ResourcesPlugin.FAMILY_AUTO_REFRESH, null); }
	 * catch (Exception e) {
	 * System.out.println("error while refreching project " + projectName); } }
	 */

	private boolean containsNode(String name) {
		for (DNode dNode : dgraph.getFacade1().getNodes())
			if (dNode.getName().equals(name))
				return true;
		return false;
	}

	private boolean containsEdge(String edgeName) {
		DEdge edge = dgraph.getFacade1().getEdge(edgeName);
		return edge != null;
	}

	private DNode getNode(String name) {
		for (DNode dNode : dgraph.getFacade1().getNodes())
			// getNodes(graph))
			if (dNode.getName().equals(name))
				return dNode;
		return null;
	}

	/*
	 * private List<DNode> getNodes_(DGraph graph) { List<DNode> result = new
	 * ArrayList<DNode>(); List<DGraphElement> elements = graph.getElements();
	 * for (DGraphElement element : elements) { if (element instanceof DNode)
	 * result.add((DNode) element); } return result; }
	 */

	private DNode addNode(EAnnotation eAnnotation) {
		if (exists(eAnnotation, DiagraphKeywords.POINT_OF_VIEW))
			return dgraph.getFacade1().addPointOfView(eAnnotation);
		else
			return dgraph.getFacade1().addSimpleNode(eAnnotation);
	}


	private DNestedEdge getNestedEdge(EClass eclazOwner, EReference ref) {
		DNestedEdge cref = DiagraphFactory.eINSTANCE.createDNestedEdge();
		DNode ownerNode = findNode(eclazOwner);
		if (ownerNode != null) {
			// ownerNode.getEdges().add(cref);
			dgraph.getElements().add(cref);
			cref.setGraph(dgraph); // FP140518
		} else
			cerror("no owner found for DNestedEdge "
					+ ((EClass) ref.getEType()).getName());
		cref.setName(ref.getName());
		//cref.setSource(ownerNode);
		cref.setSource(getContainment(ownerNode, cref)); //FP150420
		DNode targetNode = findNode((EClass) ref.getEType());
		cref.setTarget(targetNode);
		return cref;
	}




	private void cerror(String mesg) {
	   System.err.println(mesg);
	}

	private DAffixedEdge getAffixingEdge(EClass owner, EReference ref) {
		EClass semantic_ = (EClass) ref.getEType();
		DAffixedEdge afx = DiagraphFactory.eINSTANCE.createDAffixedEdge();
		DNode ownerNode = findNode(owner);
		if (ownerNode != null) {
			// ownerNode.getEdges().add(afx);
			dgraph.getElements().add(afx);
			afx.setGraph(dgraph); // FP140518
		} else
			cerror("no owner found for DCompartmentEdge "
					+ semantic_.getName());
		afx.setName(ref.getName());
		//afx.setSource(ownerNode);
		afx.setSource(getContainment(ownerNode, afx)); //FP150418
		DNode targetNode = findNode((EClass) ref.getEType());
		afx.setTarget(targetNode);
		return afx;
	}


	private String containementToString(DContainment c) {
		String result ="";
		result+=" node="+c.getNode().getName();
		result+=c.getName()==null?" name=null":(" name="+c.getName());
		return  result;
	}


	private String getRelationKind(DEdge edge) { //FP150420b
		if (edge instanceof DNavigationEdge)
		  return DiagraphKeywords.OPEN_DIAGRAM;
		else if (edge instanceof DAffixedEdge)
			 return DiagraphKeywords.AFFIXED_;
		else if (edge instanceof DCompartmentEdge)
			 return DiagraphKeywords.KREFERENCE_;
		else if (edge instanceof DReference)
			 return DiagraphKeywords.REFERENCE;
		else if (edge instanceof DNestedEdge) //FP150516
			 return DiagraphKeywords.CREFERENCE_;
		else if (edge instanceof DLabeledEdge)
			 return DiagraphKeywords.LINK;
		else return null;
	}


	private DContainment getContainment(DNode source, DEdge edge) { //FP150420//FP150418// FP150412ay
		boolean isCompartment = edge instanceof DCompartmentEdge;
		DContainment cont = null;
		for (DContainment c : source.getContainments()) {
			if (c.getName()==null)
				  System.err.println("name is null for containment " + containementToString(c));
			if (c.getName().equals(edge.getName())) {
				cont = c;
				break;
			}
		}
		if (cont == null || !isCompartment) {
			cont = DiagraphFactory.eINSTANCE.createDContainment();
			cont.setName(edge.getName());
			source.getContainments().add(cont);
			cont.setNode(source);
		}
		return cont;
	}



	private DCompartmentEdge getCompartmentingEdge(EClass owner,
			EReference ref) {

		EClass semantic_ = (EClass) ref.getEType();
		DCompartmentEdge kref = DiagraphFactory.eINSTANCE
				.createDCompartmentEdge();
		DNode ownerNode = findNode(owner);
		if (ownerNode != null) {
			// ownerNode.getEdges().add(kref);
			dgraph.getElements().add(kref);
			kref.setGraph(dgraph); // FP140518
		} else if (LOG)
			clog("no owner found for DCompartmentEdge "
					+ semantic_.getName());
		kref.setName(ref.getName());
		kref.setSource(getContainment(ownerNode, kref)); //FP150420
		DNode targetNode = findNode((EClass) ref.getEType());
		kref.setTarget(targetNode);
		kref.setPartitionName(targetNode.getName());
		return kref;
	}

	private DReference getReferenceEdge(EClass owner, EReference ref) {
		EClass semantic_ = (EClass) ref.getEType();
		DReference derf = DiagraphFactory.eINSTANCE.createDReference();
		DNode ownerNode = findNode(owner);
		if (ownerNode != null) {
			// ownerNode.getEdges().add(derf);
			dgraph.getElements().add(derf);
			derf.setGraph(dgraph); // FP140518
		} else if (LOG)
			clog("no owner found for ReferenceEdge " + semantic_.getName());
		derf.setName(ref.getName());
		derf.setSource(ownerNode);
		//derf.setContainment(getContainment(ownerNode, derf)); //FP150420
		DNode targetNode = findNode((EClass) ref.getEType());
		derf.setTarget(targetNode);
		return derf;
	}




	private DNode findNode(EClass containmentClass) {
		return containmentClass == null ? null : getNode(containmentClass
				.getName());
	}

	private EClass findContainement(String containmentName) {
		if (containmentName == null)
			return null;
		String[] containment = containmentName.split("\\.");
		String eclassname = containment[0];
		String erefname = containment[1];
		EClass cont = (EClass) epakage.getEClassifier(eclassname);
		EReference ref = (EReference) cont.getEStructuralFeature(erefname);
		return cont;
	}

	private EReference findReference(String srcname) {
		return null;
	}



	private void getRegisteredDGraph(String view) {
		DGraph result = dgraphs.get(view);
		if (result == null) {
			result = DiagraphFactory.eINSTANCE.createDGraph();
			result.setFacade1(DGraphFactory.getInstance(result,this));//FP150601azer
			//result.setFacade1(new org.isoe.diagraph.factory.DGraphFactory(result,this));//FP140701ccc languageTransformer.getControler()
		//	languageTransformer.getControler();
			result.setViewName(view);
			dgraphs.put(view, result);

		}
		dgraph = result;
		// return result;
	}

	private List<DNode> addNodes() {
		List<DNode> result = new ArrayList<DNode>();
		for (String view : getViews()) {
			getRegisteredDGraph(view);
			for (EAnnotation eAnnotation : diagraphAnnotations)
				if (matchView(view, eAnnotation)) {
					//EClass owner = (EClass) eAnnotation.eContainer();
					if (exists(eAnnotation, DiagraphKeywords.NODE))
						if (!containsNode(((EClass) eAnnotation.eContainer()).getName()))
							result.add(addNode(eAnnotation));
				}
		}
		return result;
	}



	private void addAllNodeLabels() { // FP140204 revoir
		for (String view : getViews()) {
			getRegisteredDGraph(view);
			for (EAnnotation eAnnotation : diagraphAnnotations)
				if (matchView(view, eAnnotation)
						&& exists(eAnnotation, DiagraphKeywords.NODE)
						//&& exists(eAnnotation, DiagraphKeywords.LABELEQ__)
						)
					dgraph.getFacade1().addNodeLabels(eAnnotation);
		}
	}

	private boolean containsAttribute(DLabeledElement labeledElement,
			String name) {
		for (DLabel dLabel : labeledElement.getDlabels())
			if (dLabel.getAttribute().getName().equals(name))
				return true;
		return false;
	}

	private void postParse() {
		for (String view : getViews()) {
			getRegisteredDGraph(view);
			doPostParse();
		}
	}

	private void addEdgeLabels() { // FP140204revoir
		for (String view : getViews()) {
			getRegisteredDGraph(view);
			for (EAnnotation eAnnotation : diagraphAnnotations) {
				if (matchView(view, eAnnotation)) {
					EClass owner = (EClass) eAnnotation.eContainer();
					if (exists(eAnnotation, DiagraphKeywords.LINK))
						if (exists(eAnnotation, DiagraphKeywords.LABELEQ)) {
							DEdge dedg = dgraph.getFacade1().getEdge(
									owner.getName());
							if (dedg != null) {
								DLabeledEdge lbledge = (DLabeledEdge) dedg;
								for (EAttribute eAttribute : getAttributeLabels_(
										eAnnotation, owner)) {
									if (!containsAttribute(lbledge,
											eAttribute.getName())) {
										DLabel label = DiagraphFactory.eINSTANCE
												.createDLabel();
										label.setAttribute(eAttribute);
										lbledge.getDlabels().add(label);
									}
								}
							}
						}
				}
			}
		}
	}

	private void parseAllEdges(EAnnotation eAnnotation, EClass owner) {
		String done = "";
		try {
			done = "addLabeledEdges";
			addLabeledEdges(eAnnotation, owner); // LINK
			done = "addReferenceEdges";
			addReferenceEdges(eAnnotation, owner); // REFERENCE
			done = "adDCompartmentEdges";
			adDCompartmentEdges(eAnnotation, owner); // KREFERENCE
			done = "addAffixedEdges";
			addAffixedEdges(eAnnotation, owner); // AFFIXED
			done = "addContainmentEdges";
			addContainmentEdges(eAnnotation, owner); // CREFERENCE
			done = "addImplicitContainmentEdges";
			addImplicitContainmentEdges(eAnnotation, owner); // CREFERENCE
		} catch (Exception e) {
			throw new RuntimeException(
					"Concrete Syntax Parsing - error on edge "
							+ owner.getName() + " in view "
							+ dgraph.getViewName() + "while " + done);
		}

	}

	private void addLabeledEdges(EAnnotation eAnnotation, EClass owner) {
		if (exists(eAnnotation, DiagraphKeywords.LINK)) {
			if (!containsEdge(owner.getName()))
				addlabelledEdge(owner,getLabeldLinkParams(eAnnotation));
		}
		if (exists(eAnnotation, DiagraphKeywords.LNK)) {
			if (DParams.LNK_OBSOLETE)
				languageTransformer.getControler().cerror("lnk annotation obsolete for "
						+ owner.getName()); // FP140503
			for (EReference erf : getEdgeReferences(DiagraphKeywords.LNK,
					eAnnotation, owner))
				if (!containsEdge(erf.getEType().getName()))
					addlabelledEdge(owner, erf);
		}
	}

	private void addReferenceEdges(EAnnotation eAnnotation, EClass owner) {
		if (exists(eAnnotation, DiagraphKeywords.REFERENCE)) {
			List<EReference> erfs = getEdgeReferences(
					DiagraphKeywords.REFERENCE, eAnnotation, owner);
			for (EReference erf : erfs)
				if (!containsEdge(erf.getName()))
					addReferenceEdge(owner, erf);
		}
	}

	private void adDCompartmentEdges(EAnnotation eAnnotation, EClass owner) {
		if (exists(eAnnotation, DiagraphKeywords.KREFERENCE_)) {
			List<EReference> erfs = getEdgeReferences(
					DiagraphKeywords.KREFERENCE_, eAnnotation, owner);
			for (EReference erf : erfs)
				if (!containsEdge(erf.getName())) // FP140221xxx222
					addKReferenceEdge(owner, erf);
		}
	}

	private void addContainmentEdges(EAnnotation eAnnotation, EClass owner) {
		if (exists(eAnnotation, DiagraphKeywords.CREFERENCE_)) {
			for (EReference erf : getEdgeReferences(
					DiagraphKeywords.CREFERENCE_, eAnnotation, owner))
				if (!containsEdge(erf.getName()))
					addCReferenceEdge(owner, erf);
		}
	}

	private void addImplicitContainmentEdges(EAnnotation eAnnotation,
			EClass owner) {
		List<EReference> erfs = getNotTaggeDNestedEdgeReferences(eAnnotation,
				owner);
		for (EReference erf : erfs)
			addCReferenceEdge(owner, erf);
	}

	void addAffixedEdges(EAnnotation eAnnotation, EClass owner) {
		if (exists(eAnnotation, DiagraphKeywords.AFFIXED_)) {
			for (EReference erf : getEdgeReferences(DiagraphKeywords.AFFIXED_,
					eAnnotation, owner))
				if (!containsEdge(erf.getName()))
					addAffixedEdge(owner, erf);
		}
	}

	private void addEdges() {
		for (String view : getViews()) {
			// dgraph = getDGraph(view);
			getRegisteredDGraph(view);
			for (EAnnotation eAnnotation : diagraphAnnotations) {
				EClass owner = (EClass) eAnnotation.eContainer();
				if (matchView(view, eAnnotation)) {
					// try {
					parseAllEdges(eAnnotation, owner);
					// } catch (Exception e) {
					// throw new
					// RuntimeException("Concrete Syntax Parsing - error on edge "+owner.getName()+" in view "+view);
					// }
				}
			}
		}
	}

	private DLabeledEdge addlabelledEdge(EClass owner, EReference erf) {
		DLabeledEdge labeledEdge = dgraph.getFacade1().createLabeledEdge(owner, erf);
		if (LOG)
			clog("labeledEdge (1) " + labeledEdge.getName());
		return labeledEdge;
	}


	private DLabeledEdge addlabelledEdge(EClass owner, String[]params) {
		DLabeledEdge labeledEdge = dgraph.getFacade1().createLabeledEdge(owner, params);
		if (LOG)
			clog("labeledEdge (2) " + labeledEdge.getName());
		return labeledEdge;
	}


	private DReference addReferenceEdge(EClass owner, EReference erf) {
		DReference refEdge = getReferenceEdge(owner, erf);
		if (LOG)
			clog("referenceEdge " + refEdge.getName());
		return refEdge;
	}

	private DAffixedEdge addAffixedEdge(EClass owner, EReference erf) {
		DAffixedEdge afxedge = getAffixingEdge(owner, erf);
		if (LOG)
			clog("afxEdge " + afxedge.getName());
		return afxedge;
	}

	private DCompartmentEdge addKReferenceEdge(EClass owner, EReference erf) {
		DCompartmentEdge krefEdge = getCompartmentingEdge(owner, erf);
		if (LOG)
			clog("compart_referenceEdge " + krefEdge.getName());
		return krefEdge;
	}

	private DNestedEdge addCReferenceEdge(EClass owner, EReference erf) {
		DNestedEdge crefEdge = getNestedEdge(owner, erf);
		if (LOG)
			clog("creferenceEdge " + crefEdge.getName());
		return crefEdge;
	}

	private void saveConcreteSyntax() {
		for (String view : dgraphs.keySet()) {
			dgraph = dgraphs.get(view);
			save();
		}
	}

	private List<DGraph> getConcreteSyntax() {
		List<DGraph> result = new ArrayList<DGraph>();
		result.add(dgraphs.get(ViewConstants.DIAGRAPH_DEFAULT));
		for (String view : dgraphs.keySet())
			if (!result.contains(dgraphs.get(view)))
				result.add(dgraphs.get(view));
		return result;
	}

	private void logDetails(EAnnotation eAnnotation) {
		EMap<String, String> details = eAnnotation.getDetails();
		Set<String> keys = details.keySet();
		for (String key : keys)
			if (LOG)
				clog("DP_DET " + "key=" + key);// + " value=" +
												// details.get(key));
	}

	private void readDiagraphAnnotation() {
		EList<EClassifier> classes = epakage.getEClassifiers();
		for (EClassifier eClassifier : classes) {
			if (eClassifier instanceof EClass) {
				EClass eclaz = (EClass) eClassifier;
				if (LOG)
					clog("DP_EC " + eclaz.getName());
				eclaz.getEReferences();
				eclaz.getEAttributes();
				List<EAnnotation> eAnnots = eclaz.getEAnnotations();
				allAnnotations.addAll(eAnnots);
				for (EAnnotation eAnnotation : eAnnots) {
					String source = eAnnotation.getSource();
					if (source.equals(GenericConstants.ANNOT_DIAGRAPH_LITTERAL)) {
						diagraphAnnotations.add(eAnnotation);
						if (LOG)
							logDetails(eAnnotation);
					}
				}
			}
		}
	}

	/*
	 * public IGraphHandler getDiagraphFactory_() { if (dGraphFactory == null) {
	 * //dGraphFactory_ = new DGraphFactoryV2(0); //FP140519
	 * //dGraphFactory_.setStyle(null); // todo: implement styles } return
	 * dGraphFactory; }
	 */
	private void save() {
		if (LOG)
			clog("DRXP " + "saving " + dgraph.getViewName());
		String path = null;
		try {
			path = getPath((EClass) dgraph.getFacade1().getNodes().get(0)
					.getSemanticRole());// getNodes_(dGraph)
		} catch (Exception e) {
			if (LOG)
				clog("path error");
			return;
		}
		if (path == null) {
			if (LOG)
				clog("path error");
			return;
		}
		boolean urischeme = false;
		if (path.startsWith("file:/"))
			path = path.substring("file:/".length());
		else {
			path = path.substring("platform:/resource/".length());
			urischeme = true;
		}
		path = path.substring(0, path.length() - ("ecore".length() + 1));
		path = path + "_" + dgraph.getViewName() + "_cs.diagraph";

		String projectName_nu = null;
		URI uri = null;
		if (urischeme) {
			projectName_nu = path.substring(0, path.indexOf("/"));
			uri = CommonPlugin.resolve(URI
					.createPlatformResourceURI(path, true));
		} else {
			uri = URI.createFileURI(path);
			projectName_nu = uri.toString();
			projectName_nu = projectName_nu.substring(0,
					projectName_nu.indexOf("/model/"));
			projectName_nu = projectName_nu.substring(projectName_nu
					.lastIndexOf("/") + 1);
		}
		dgraph.getFacade1().save(uri);
		/*
		 * resource.getContents().add(dGraph); try { resource.save(null);
		 * org.isoe.fwk.utils.debug.WorkspaceUtils.getInstance()
		 * .refreshProject(projectName, false); } catch (IOException e) {
		 * e.printStackTrace(); }
		 */
		// DNode: RelatedTo is not contained in a resource
	}

	private void doPostParse() {
		// this.dgraph = dgraph;
		checkReferenceNaming();
		resolveContainmentNode();
		resolveContainmentReferences();
		resolveInheritedFeatures();
		inferLinkTargetReferences();
		inferLinkSourceReferences();
		sortContainments();
		inferContainments();
		resolveTargetRefs();
		resolveMissingCRefsIfCanvas();
		inferMissingCref();
		propagateInheritedLabelsIfNoLabel();
		inferMissingLabels();
		cloneInheritedContainments();
		propagateAllInheritedLabels();
		resolveLinkSourceAndTarget();
		resolveOwnLabelAttributes();
	}

	private void checkReferenceNaming() {
		List<DNode> dnodes = dgraph.getFacade1().getNodes();// getNodes(currentGraph);
		for (DNode containerNode : dnodes) {
			EClass claz = (EClass) (containerNode.getSemanticRole());
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

	/***************** to implement, see org.isoe.diagraph.internal.m2.parser.DiaParser ****************************************************/

	private void resolveTargetRefs() {
		if (DParams.TODO_LOG_)
			languageTransformer.getControler().cerror("TODO implement resolveTargetRefs in ConcreteSyntaxParser");

	}

	protected void resolveContainmentNode() {
		if (DParams.TODO_LOG_)
			languageTransformer.getControler().cerror("TODO implement resolveContainmentNode in ConcreteSyntaxParser");
	}

	protected void resolveContainmentReferences() {
		if (DParams.TODO_LOG_)
			languageTransformer.getControler().cerror("TODO implement resolveContainmentReferences in ConcreteSyntaxParser");
	}

	protected void resolveInheritedFeatures() {
		if (DParams.TODO_LOG_)
			languageTransformer.getControler().cerror("TODO implement resolveInheritedFeatures in ConcreteSyntaxParser");
	}

	protected void inferLinkTargetReferences() {
		if (DParams.TODO_LOG_)
			languageTransformer.getControler().cerror("TODO implement inferLinkTargetReferences in ConcreteSyntaxParser");
	}

	protected void inferLinkSourceReferences() {
		if (DParams.TODO_LOG_)
			languageTransformer.getControler().cerror("TODO implement inferLinkSourceReferences in ConcreteSyntaxParser");
	}

	protected void sortContainments() {
		if (DParams.TODO_LOG_)
			languageTransformer.getControler().cerror("TODO implement sortContainments in ConcreteSyntaxParser");
	}

	protected void inferContainments() {
		if (DParams.TODO_LOG_)
			languageTransformer.getControler().cerror("TODO implement inferContainments in ConcreteSyntaxParser");
	}

	protected void resolveMissingCRefsIfCanvas() {
		if (DParams.TODO_LOG_)
			languageTransformer.getControler().cerror("TODO implement resolveMissingCRefsIfCanvas in ConcreteSyntaxParser");
	}

	protected void inferMissingCref() {
		if (DParams.TODO_LOG_)
			languageTransformer.getControler().cerror("TODO implement inferMissingCref in ConcreteSyntaxParser");
	}

	protected void propagateInheritedLabelsIfNoLabel() {
		if (DParams.TODO_LOG_)
			languageTransformer.getControler().cerror("TODO implement propagateInheritedLabelsIfNoLabel in ConcreteSyntaxParser");
	}

	protected void inferMissingLabels() {
		if (DParams.TODO_LOG_)
			languageTransformer.getControler().cerror("TODO implement inferMissingLabels in ConcreteSyntaxParser");
	}

	protected void cloneInheritedContainments() {
		if (DParams.TODO_LOG_)
			languageTransformer.getControler().cerror("TODO implement cloneInheritedContainments in ConcreteSyntaxParser");
	}

	protected void propagateAllInheritedLabels() {
		if (DParams.TODO_LOG_)
			languageTransformer.getControler().cerror("TODO implement propagateAllInheritedLabels in ConcreteSyntaxParser");
	}

	protected void resolveLinkSourceAndTarget() {
		if (DParams.TODO_LOG_)
			languageTransformer.getControler().cerror("TODO implement resolveLinkSourceAndTarget in ConcreteSyntaxParser");
	}

	protected void resolveOwnLabelAttributes() {
		if (DParams.TODO_LOG_)
			languageTransformer.getControler().cerror("TODO implement resolveOwnLabelAttributes in ConcreteSyntaxParser");
	}


	///////////////////// _nu ///////////////////////

	private void addNodeLabels_nu(EAnnotation eAnnotation) { // FP140204 revoir
		EClass owner = (EClass) eAnnotation.eContainer();
		DNode dnod = getNode(owner.getName());
		if (dnod == null)
			throw new RuntimeException("no node " + owner.getName());
		for (EAttribute eAttribute : getAttributeLabels_(eAnnotation, owner)) {
			if (!containsAttribute(dnod, eAttribute.getName())) {
				DLabel labl = DiagraphFactory.eINSTANCE.createDLabel();
				labl.setAttribute(eAttribute);
				dnod.getDlabels().add(labl);
			}
		}
	}

	private List<DNode> getUpperContainedElements_nu() {
		List<DNode> result = new ArrayList<DNode>();
		for (DNode el : dgraph.getFacade1().getNodes())
			// getNodes( currentGraph))
			if ((el instanceof DOwnedElement)
					&& (((EClass) el.getSemanticRole()).getEAllSuperTypes()
							.size() == 0))
				result.add(el);
		return result;
	}

	@Override
	public Object callBack(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

}
