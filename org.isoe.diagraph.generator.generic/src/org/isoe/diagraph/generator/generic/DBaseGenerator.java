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
package org.isoe.diagraph.generator.generic;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.diastyle.DStyle;
import org.isoe.diagraph.diastyle.helpers.IErrorReporter;
import org.isoe.diagraph.diastyle.helpers.IStyleHandler;
import org.isoe.diagraph.internal.api.DPhase;
import org.isoe.diagraph.internal.api.IDiaNamedElement;
import org.isoe.diagraph.internal.api.IDiaSyntaxElement;
import org.isoe.diagraph.internal.m2.DiaContainedElement;
import org.isoe.diagraph.internal.m2.DiaLink;
import org.isoe.diagraph.internal.m2.DiaNode;
import org.isoe.diagraph.internal.m2.DiaReference;
import org.isoe.diagraph.internal.m2.api.IDiaContainment;
import org.isoe.diagraph.internal.m2.parser.DAnnotation;
import org.isoe.diagraph.internal.m2.parser.DAnnotationParser;
import org.isoe.diagraph.internal.m2.parser.DStatement;
import org.isoe.diagraph.internal.m2.parser.DStatement.DCommand_;
import org.isoe.diagraph.internal.api.IDiaNode;
import org.isoe.fwk.core.DParams;


/**
 * @author fpfister
 *
 */
public abstract class DBaseGenerator implements IDiaGenerator {// BaseGenerator

	private static final boolean LOG = DParams.DBaseGenerator_LOG;
	static final boolean REFERENCE_UNIQUE_GEN = false;// FP130329 true;

	protected String pluginId;
	protected String filePath;
	protected EPackage ePackage;
	protected DAnnotationParser annotationParser;
	protected IStyleHandler styleHandler;
	protected DGraph dGraph;
	protected DStyle dStyle;
	protected String view;
	private IErrorReporter logger;
	protected static final boolean INTERNAL_METAMODEL_BASED = false; // TODO:
																		// remove
																		// the
																		// internal
																		// metamodel
																		// for
																		// Diagraph,
																		// base
																		// the
																		// whole
																		// on
																		// the
																		// Ecore
																		// metamodel

	public String getFilePath() {
		return filePath;
	}

	public DBaseGenerator(IErrorReporter errorLogger, String view, String pluginId, String filePath,
			EPackage ePackage, DAnnotationParser parser) {
		super();
        this.logger = errorLogger;
		this.view = view;
		this.pluginId = pluginId;
		this.filePath = filePath;
		this.ePackage = ePackage;
		this.annotationParser = parser;
		this.dGraph = annotationParser.getDGraph(); // FP120719
		this.dStyle = annotationParser.getDStyle();
		this.styleHandler = annotationParser.getStyleHandler();
	}

	protected void validate(boolean condition, String method, String message) {
		if (!condition)
			throw new RuntimeException(message + " in " + method);
	}

	protected IDiaSyntaxElement findDiaSyntaxElement(EModelElement mel) {
		if (mel == null)
			throw new RuntimeException("Model Element is null ");
		for (IDiaSyntaxElement el : annotationParser.getInternalModel()
				.getAllConcreteElements()) {
			if (el.getEModelElement() == mel) // FP1111 ????
				return el;
		}
		return null;
	}

	protected IDiaSyntaxElement findDiaSyntaxElement(String name) {
		for (IDiaSyntaxElement el : annotationParser.getInternalModel()
				.getAllConcreteElements()) {
			if (el.getName().equals(name))
				return el;
		}
		return null;
	}

	protected List<IDiaNode> findEmbeddedDiaNodes(DiaNode element) {
		List<IDiaNode> result = new ArrayList<IDiaNode>();
		List<IDiaContainment> conts = element.getContainments();
		for (IDiaContainment diaContainment : conts) {
			result.add(diaContainment.getSourceNode());
		}
		return result;
	}

	protected IDiaSyntaxElement findParentDiaSyntaxElement_a_voir(
			IDiaSyntaxElement childElement) {
		List<IDiaSyntaxElement> allelements = annotationParser
				.getInternalModel().getAllConcreteElements();
		for (IDiaSyntaxElement diaSyntaxElement : allelements)
			if (diaSyntaxElement.getNamedChildren() == childElement)
				return (DiaNode) diaSyntaxElement;
		return null;
	}

	protected IDiaNamedElement findDiaSyntaxLabel(String elementName,
			String name) {
		for (IDiaSyntaxElement el : annotationParser.getInternalModel()
				.getAllConcreteElements()) {
			if (el.getName().equals(elementName)) {
				List<IDiaNamedElement> nameds = el.getNamedChildren();
				for (IDiaNamedElement diaNamedElement : nameds)
					if (diaNamedElement.getName().equals(name))
						return diaNamedElement;
			}
		}
		return null;
	}

	protected IDiaNamedElement findDiaSyntaxLabel(IDiaSyntaxElement owner,
			String name) {
		for (IDiaNamedElement diaNamedElement : owner.getNamedChildren())
			if (diaNamedElement.getName().equals(name))
				return diaNamedElement;
		return null;
	}

	protected DiaContainedElement findDiaElement(EModelElement mel) {
		for (DiaContainedElement el : annotationParser.getInternalModel()
				.getAllDiaNodesAndLinks())
			if (el.getEModelElement() == mel)
				return el;
		return null;
	}

	protected DiaContainedElement findDiaElement_(String diagramElementName) {
		for (DiaContainedElement el : annotationParser.getInternalModel()
				.getAllDiaNodesAndLinks())
			if (el.getName() == diagramElementName)
				return el;
		return null;
	}

	@Override
	public void pass2(DPhase phase) {
		if (DParams.Parser_15_LOG){
			clog("process labels");
		}
		for (DiaNode dNode : annotationParser.getInternalModel().getDiaNodes()) {
			processLabels(dNode);
			for (DiaLink dLink : dNode.getDiaLinks())
				processLabels(dLink);
		}
		if (DParams.Parser_15_LOG){
			clog("process references");
		}
		for (DiaNode dNode : annotationParser.getInternalModel().getDiaNodes())
			processReferences(dNode);
	}

	/*
	 *
	 * public Resource saveModel(EObject root, String povSuffix, String
	 * extension,String[] resourceData) { String suffixedFilePath = filePath; if
	 * (povSuffix!=null) suffixedFilePath += povSuffix; try { //URI uri =
	 * URI.createFileURI(suffixedFilePath + "." + extension); return
	 * ResourceManager.save(resourceData,suffixedFilePath + "." + extension,
	 * root); } catch (IOException e) { e.printStackTrace(); throw new
	 * RuntimeException(e); } }
	 */

	void log(EModelElement elemen, DAnnotation annotation_) {
		String elname = "";
		if (elemen instanceof EClass)
			elname = "EClass " + ((EClass) elemen).getName();
		else if (elemen instanceof EAttribute) {
			elname = "EAttribute "
					+ ((EClass) ((EAttribute) elemen).eContainer()).getName()
					+ "." + ((EAttribute) elemen).getName();
		} else if (elemen instanceof EReference) {
			elname = "EReference "
					+ ((EClass) ((EReference) elemen).eContainer()).getName()
					+ "." + ((EReference) elemen).getName();
		} else
			elname = elemen.getClass().getName() + ".???";// FP120603 //FP120531
		if (LOG) clog("element name=" + elname + " annotation:" + annotation_.toString());
	}

	/****************/
	// FP131205xxx

	protected void processReferences(DiaNode node) {
		List<DiaReference> refs = node.getReferences();
		if (LOG) {
			clog("DBGNPR " + this.getClass().getSimpleName()
					+ " processReferences for " + node.getName());
		}
		for (DiaReference reference : refs) {

			Object result = processReference_(reference, true,
					REFERENCE_UNIQUE_GEN);// FP120621a


				if (result == null) {
					this.annotationParser.cerror("reference " + reference.getName()
							+ " already exists in the model");
					//this.annotationParser.cerror("reference " + reference.getName()
					//		+ " already exists in the model");
				} else
					if (LOG) clog("reference " + reference.getName() + " created");


			if (result == null)
				throw new RuntimeException("reference " + reference.getName()
						+ " already exists in the model !!!!");

		}
	}

	@Override
	public void processLabels(IDiaSyntaxElement diagramElement) {
		// nothing
	}





	@Override
	public Object processLink(DStatement statement, boolean oriented) {
		return null;

	}

	@Override
	public void processCanvas(DStatement statement) {
		// nothing
	}

	@Override
	public Object processReference_(IDiaSyntaxElement reference,
			boolean oriented, boolean unique) {
		return null;
	}

	/***********************/

	private void clog(String mesg) {
		if (LOG || DParams.Parser_15_LOG)
			System.out.println(mesg);

	}

	@Override
	public void processDomainModel(DPhase phase) {
		;
		if (INTERNAL_METAMODEL_BASED)
			throw new RuntimeException(
					" old method INTERNAL_METAMODEL_BASED no more supported in createCompartmentRectangleWBackground");// nodeBackgroundcolor
																														// =
																														// annotationParser.getInternalModel().findDiaNode(diagramElementName).getBackGroundColor().getLiteral();
		if (LOG)
			clog("~~~~~ " + this.getClass().getSimpleName()
					+ " processDomainModel - " + phase);
		String claz = this.getClass().getSimpleName();
		clog(claz);
		executeCommands(phase);
		if (LOG)
			clog("pass2");
		pass2(phase); // map:labels links references
		if (LOG)
			clog("pass3");
		pass3(phase); // map:labels && references
		if (LOG)
			clog("pass4");
		pass4(phase); // map:nothing
		if (LOG)
			clog("end pass");
	}

	@Override
	public void validateDomainModel(DPhase phase) {
		for (DStatement statement : annotationParser.getDStatements()) {
			if (phase == DPhase.MAP
					&& statement.getCommand() == DCommand_.MAP_NODE_)
				checkNode(statement);
		}
	}

	@Override
	public void pass3(DPhase phase) {

	}

	@Override
	public void executeCommands(DPhase phase) {

	}

	@Override
	public void initTool() { // FP131205

	}

	public void checkNode(DStatement statement) {

	}

	public void cerror(String mesg){
	  logger.cerror(mesg);
	}

	@Override
	public Object processNodeTop(DStatement statement, DCommand_ force) {
		return null;
	}




}
