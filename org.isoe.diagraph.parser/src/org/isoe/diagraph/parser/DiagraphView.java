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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EPackage;
import org.isoe.diagraph.lang.DiagraphKeywords;
import org.isoe.diagraph.parser.api.IDiagraphAnnotation;
import org.isoe.diagraph.parser.api.IDiagraphAssociation;
import org.isoe.diagraph.parser.api.IDiagraphAssociation.AssociationType;
import org.isoe.diagraph.parser.api.IDiagraphClassAssociation;
import org.isoe.diagraph.parser.api.IDiagraphElement;
import org.isoe.diagraph.parser.api.IDiagraphNode;
import org.isoe.diagraph.parser.api.IDiagraphNotation;
import org.isoe.diagraph.parser.api.IDiagraphReferenceAssociation;
import org.isoe.diagraph.parser.api.IDiagraphView;
import org.isoe.fwk.core.DParams;

/**
 *
 * @author fpfister
 *
 */
public class DiagraphView implements IDiagraphView {

	private static final boolean LOG = DParams.DiagraphGrammar_LOG;

	private DiagraphNotation notation;
	private List<List<IDiagraphAnnotation>> parsedAnnotations;
	private List<IDiagraphAnnotation> rootannots_;
	private List<IDiagraphNode> nodes_;// = new ArrayList<IDiagraphNode>();
	private List<IDiagraphElement> generics;
	private IDiagraphNode povNode;
	private List<IDiagraphAssociation> associations_;// = new
														// ArrayList<IDiagraphAssociation>();
	private EClass pov_;
	private String viewId;
	private List<EModelElement> modelElements;
	private EPackage packag;



	private void clog15(String mesg) {
		if (DParams.Parser_15_LOG)
			System.out.println(mesg);
	}

	public DiagraphView() {
		super();
		associations_ = new ArrayList<IDiagraphAssociation>();
		nodes_ = new ArrayList<IDiagraphNode>();
		generics = new ArrayList<IDiagraphElement>();
		// this.packag = packag;
	}

	@Override
	public void addNode(IDiagraphNode node) {
		if (nodes_.contains(node))
			throw new RuntimeException("should not happen in addNode");
		this.nodes_.add(node);
	}

	@Override
	public void addGeneric(IDiagraphElement el) {
		if (generics.contains(el))
			throw new RuntimeException("should not happen in addGeneric");
		this.generics.add(el);
	}

	@Override
	public List<IDiagraphAnnotation> getRootAnnotations() {
		return rootannots_;
	}


	public IDiagraphNode addPointOfView_new_nu() {
		notation.getParser().startPointOfView();
		IDiagraphNode pov = notation.getParser().getPointOfView(packag);
		IDiagraphNode exists = findNode(pov.getEClass());
		if (exists == null) { // FP140617
			if (LOG)
				clog(pov + " pov added");
			nodes_.add(pov);
			rootannots_ = notation.getParser().parseElementAnnotations(
					pov.getEClass(), new NullProgressMonitor());
		} else// if (LOG)
			throw new RuntimeException(pov + " pov already exists"); //FP150521
		return pov;
	}
	@Override
	public IDiagraphNode addPointOfView() {
		notation.getParser().startPointOfView();
		IDiagraphNode pov = notation.getParser().getPointOfView(packag);
		IDiagraphNode exists = findNode(pov.getEClass());
		if (exists == null) { // FP140617
			if (LOG)
				clog(pov + " pov added");
			nodes_.add(pov);
		} else if (LOG)
			clog(pov + " pov already exists");
		rootannots_ = notation.getParser().parseElementAnnotations(
				pov.getEClass(), new NullProgressMonitor());
		return pov;
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	private IDiagraphNode findNod(EClass eclaz) {
		for (IDiagraphNode node : nodes_)
			if (node.getEClass() == eclaz
					&& node.getView().equals(
							notation.getCurrentView().getViewId()))
				return node;
		return null;
	}

	@Override
	public IDiagraphNode findNode(EClass eclaz) {
		return DParams.VERIFY ? findUniqueNod(eclaz) : findNod(eclaz);
	}

	@Override
	public IDiagraphElement findGeneric(EClass eclaz) {
		return DParams.VERIFY ? findUniqueGeneric(eclaz) : findGener(eclaz);
	}


	private IDiagraphElement findUniqueGeneric(EClass eclaz) {
		List<IDiagraphElement> result = new ArrayList<IDiagraphElement>();
		for (IDiagraphElement el : generics)
			if (el.getEClass() == eclaz
					&& el.getView().equals(
							notation.getCurrentView().getViewId()))
				result.add(el);
		if (result.size() > 1)
			throw new RuntimeException("result should be unique in findUniqueGeneric");
		if (result.size() == 1)
			return result.get(0);
		return null;
	}

	private IDiagraphElement findGener(EClass eclaz) {
		for (IDiagraphElement el : generics)
			if (el.getEClass() == eclaz
					&& el.getView().equals(
							notation.getCurrentView().getViewId()))
				return el;
		return null;
	}

	public IDiagraphNode findPov(EClass eclaz) {
		IDiagraphNode pov = findNode(eclaz);
		if (pov == null || !pov.isPov())
			throw new RuntimeException(eclaz.getName()
					+ " is not a Pov for the view "
					+ notation.getCurrentView().getViewId());
		return pov;
	}

	private IDiagraphNode findUniqueNod(EClass eclaz) {
		List<IDiagraphNode> result = new ArrayList<IDiagraphNode>();
		for (IDiagraphNode node : nodes_)
			if (node.getEClass() == eclaz
					&& node.getView().equals(
							notation.getCurrentView().getViewId()))
				result.add(node);
		if (result.size() > 1)
			throw new RuntimeException("result should be unique in findUniqueNod");
		if (result.size() == 1)
			return result.get(0);
		return null;
	}

	@Override
	public List<EModelElement> getModelElements() {
		return modelElements;
	}

	@Override
	public String getPovName() {
		return pov_.getName();
	}

	@Override
	public List<IDiagraphNode> getNodes() {
		return nodes_;
	}


	@Override
	public List<IDiagraphElement> getGenerics() {
		return generics;
	}

	/*
	 * @Override public IDiagraphParser getParser() { return diagraphParser; }
	 */

	@Override
	public IDiagraphNotation getNotation() {
		return notation;
	}

	@Override
	public EClass getPointOfView() {
		return pov_;
	}

	@Override
	public String getViewId() {
		return viewId.isEmpty() ? DiagraphKeywords.DIAGRAPH_DEFAULT : viewId;
	}

	public boolean isValidKeyword(String keyw) {
		for (String kw : DiagraphKeywords.VALID_TOKENS)
			if (kw.equals(keyw))
				return true;
		return false;
	}

	public Map<String, Map.Entry<String, String>> parse(EAnnotation annotation) {
		Map<String, Map.Entry<String, String>> result = new HashMap<String, Map.Entry<String, String>>();
		if (annotation != null)
			for (Map.Entry<String, String> entry : annotation.getDetails()) {
				String[] ks = entry.getKey().split("=");
				if (!isValidKeyword(ks[0])) {
					if (LOG)
						clog("invalid keyword:" + ks[0]);

				}
				for (String kwd : DiagraphKeywords.VALID_TOKENS) {
					if (ks[0].equals(kwd)) {
						result.put(kwd, entry);
						if (LOG)
							clog("keyword:" + kwd + "=" + entry);
					}
				}
			}
		return result;
	}

	@Override
	public EPackage getDomainModel() {
		return getPointOfView().getEPackage();
	}

	public void setNotation(DiagraphNotation notation) {
		this.notation = notation;
	}

	@Override
	public void setParsedAnnotations(
			List<List<IDiagraphAnnotation>> parsedAnnotations) {
		this.parsedAnnotations = parsedAnnotations;
	}

	@Override
	public void setViewId(String viewId) {
		this.viewId = viewId;
	}

	@Override
	public void setPov(IDiagraphNode node) {
		if (LOG)
			clog("(1) point of view added for view "
					+ notation.getCurrentView().getViewId() + " :"
					+ node.getName());
		this.povNode = node;
	}

	@Override
	public IDiagraphNode getPov() {
		return povNode;
	}

	@Override
	public IDiagraphView parsePov() {
		IDiagraphNode node = addPointOfView();
		if (LOG)
			clog("(2) point of view added for view "
					+ notation.getCurrentView().getViewId() + " :"
					+ node.getName());
		return this;
	}

	@Override
	public void setElements(List<EModelElement> modelElements, EClass pov) {
		this.pov_ = pov;
		this.modelElements = modelElements;
	}

	/*------------------------------------------------*/


	private void logReferenceAssociations() {
		for (IDiagraphAssociation raf : associations_)
			if (raf instanceof IDiagraphReferenceAssociation)
				clog15(raf.toLog());
	}

	@Override
	public void addAssociation(IDiagraphReferenceAssociation association) { // a
		if (associations_.contains(association))
			throw new RuntimeException("should not happen in addAssociation");
		this.associations_.add(association);
	}

	@Override
	public List<IDiagraphAssociation> getAssocs() {
		return associations_;
	}

	@Override
	public final List<IDiagraphClassAssociation> getClassAssociations() {
		List<IDiagraphClassAssociation> result = new ArrayList<IDiagraphClassAssociation>();
		for (IDiagraphAssociation assoc : associations_)
			if (assoc instanceof IDiagraphClassAssociation)
				result.add((IDiagraphClassAssociation) assoc);
		return result;
	}

	@Override
	public List<IDiagraphReferenceAssociation> findReferenceAssociationsByTargetName(
			String name, AssociationType[] associationTypes) {// FP150520
																// //AssociationCandidateType
																// caze) {

		if (LOG)
			clog("findReferenceAssociations to " + name + " ");// +
																// IDiagraphAssociation.TYPE_NAMES[associationType.ordinal()]);

		List<IDiagraphReferenceAssociation> result = new ArrayList<IDiagraphReferenceAssociation>();
		for (IDiagraphAssociation raf : associations_)
			if (raf instanceof IDiagraphReferenceAssociation) {
				for (AssociationType associationType : associationTypes) {
					if (raf.getAssociationType() == associationType) { // FP150520

						String tname = ((IDiagraphReferenceAssociation) raf)
								.getTarget().getName();

						if (LOG)
							clog(name + " eqs? " + tname);

						if (tname.equals(name))
							result.add((IDiagraphReferenceAssociation) raf);
					}
				}
			}
		return result;
	}

	@Override
	public List<IDiagraphReferenceAssociation> findReferenceAssociationsByAssociationName(
			String targetName, String associationName, AssociationType[] types) {

		if (LOG) {

			clog("findReferenceAssociations to " + targetName + "."
					+ associationName + " ");//
			// + IDiagraphAssociation.TYPE_NAMES[type.ordinal()]);
		}
		List<IDiagraphReferenceAssociation> result = new ArrayList<IDiagraphReferenceAssociation>();
		for (IDiagraphAssociation raf : associations_)
			if (raf instanceof IDiagraphReferenceAssociation) { // FP150520
				for (AssociationType type : types) {
					if (raf.getAssociationType() == type) { // FP150520
						String aname = ((IDiagraphReferenceAssociation) raf)
								.getName();
						String target = (raf.getTarget() == null ? "" : raf
								.getTarget().getName());
						if (LOG)
							clog(raf.getName()+"-"+targetName + "." + associationName + " eqs? "
									+ target + "." + aname);
						if (aname.equals(associationName)) { // TODO check
																// targetname
							boolean match = true;
							if (!target.equals(targetName))
								match = checkInheritance(raf, targetName,
										target);
							if (match)
								result.add((IDiagraphReferenceAssociation) raf); // FP150516x
						}
					}
				}
			}
		return result;
	}

	private boolean checkInheritance(IDiagraphAssociation raf, String upper,
			String lower) {
		EPackage p = raf.getSource().getEPackage();
		return ((EClass) p.getEClassifier(upper)).isSuperTypeOf((EClass) p
				.getEClassifier(lower));
	}

	@Override
	public final List<IDiagraphReferenceAssociation> getReferenceAssociations() {
		List<IDiagraphReferenceAssociation> result = new ArrayList<IDiagraphReferenceAssociation>();
		for (IDiagraphAssociation assoc : associations_)
			if (assoc instanceof IDiagraphReferenceAssociation)
				result.add((IDiagraphReferenceAssociation) assoc);
		return result;
	}

	@Override
	public void init(EPackage packag) {
		this.packag = packag;
		parsedAnnotations = new ArrayList<List<IDiagraphAnnotation>>();
		nodes_ = new ArrayList<IDiagraphNode>();
		associations_ = new ArrayList<IDiagraphAssociation>();
		generics = new ArrayList<IDiagraphElement>();
	}

	@Override
	public void addReferenceAssociations(
			//String targetName,
			List<IDiagraphReferenceAssociation> list) {
		for (IDiagraphReferenceAssociation assoc : list) // b
			addReferenceAssociation(assoc);
	}

	@Override
	public void addReferenceAssociation(
			//String targetName_nu,
			IDiagraphReferenceAssociation assoc) {
		if (!associations_.contains(assoc))
			//throw new RuntimeException("should not happen in addReferenceAssociation");
		associations_.add(assoc);
		 else
		if (DParams.Parser_15_LOG)
				clog15("allready contained "+assoc.toLog());
	}

	@Override
	public void addClassAssociation(IDiagraphClassAssociation classoc) {
		if (!associations_.contains(classoc))
		   associations_.add(classoc);
		else
			if (DParams.Parser_15_LOG)
				clog15("allready contained "+classoc.toLog());

	}



}
