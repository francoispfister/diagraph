 /**
 * Copyright (c) 2014 Laboratoire de Genie Informatique et Ingenierie de Production - Ecole des Mines d'Ales
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Blazo Nastov (ISOE-LGI2P) - initial API and implementation
 */
package org.isoe.diagraph.commands.services;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecoretools.legacy.diagram.part.EcoreDiagramEditor;
import org.eclipse.jface.action.Action;
import org.isoe.diagraph.lang.internal.utils.DiagraphAnnotationUtils;
import org.isoe.extensionpoint.gramgen.IAnnotationHelper;
import org.isoe.extensionpoint.gramgen.IDiagraphService;
import org.isoe.extensionpoint.gramgen.IDiagraphUtils;
import org.isoe.fwk.core.DParams;

/**
 *
 * @author fpfister
 *
 */
import org.isoe.diagraph.controler.IDiagraphControler;  //FP140707refactored
import org.isoe.extensionpoint.diagraph.action.DiagraphService;  //FP140707_c_refactored
public class DiagraphGenService extends Action implements
		IDiagraphService, IDiagraphUtils,
		IAnnotationHelper,DiagraphService
	{

	private Map<EClass, List<String>>[] arguments;

	@Override
	public Object getArguments() {
		return arguments;
	}


	private static final boolean LOG = DParams.GrammGenService_LOG;

	// String[] NAME_CANDIDATES_ = { "name", "id", "description", "designation",
	// "type" };

	private IDiagraphControler controler;
	private EClass viewRoot;
	private EcoreDiagramEditor ecoreDiagramEditor;
	private DiagraphRuntimeHelper concreteSyntaxGenerator;
	private String view;
	// private String argument;
	private EModelElement modelElement;
	// private IGraphicalEditPart graphicalEditPart;
	private boolean silent;
	// private List<EAnnotation> diagraphAnnotations;

	private String viewName;
	private int viewId;
	private Map<EClass, List<String>>[] concreteSyntax;

	private DiagraphAnnotationUtils utils;// = LanguageUtils.getInstance(this);

	@Override
	public String getView() {
		return view;
	}

	@Override
	public EcoreDiagramEditor getEcoreDiagramEditor() {
		return ecoreDiagramEditor;
	}


	// old=public EClass getViewRoot(String eclassName)
	@Override
	public EClass getViewRoot(String eclassName) {
		return concreteSyntaxGenerator.getRootOfView(eclassName);
	}


	/* ----------------------------------- */

	@Override
	public String getViewName() {
		return viewName;
	}

	@Override
	public int getViewId() {
		return viewId;
	}

	@Override
	public Map<EClass, List<String>>[] getConcreteSyntax() {
		return concreteSyntax;
	}

	@Override
	public void setView(String view) { // FP131008
		this.view = view;
	}

	public DiagraphGenService() {
		setText("Grammar Generator Service");
		setToolTipText("Grammar Generator Service");
		utils = DiagraphAnnotationUtils.getInstance(this);
	}

	public void run() {
		throw new RuntimeException("not implemented");
	}


	@Override
	public void setEModelElement(EModelElement eModelElement) {
		this.modelElement = eModelElement; // FP131112
	}

	@Override
	public void init() {
		if (!controler.isModelConfiguration()) {
			ecoreDiagramEditor = controler.getEcoreDiagramEditor();
			viewRoot = null;
			if (ecoreDiagramEditor == null) {
				controler.cerror("no opened M2 editor");
				throw new RuntimeException("no opened M2 editor");
			}
		}
	}



	@Override
	public boolean isStub() {
		return false;
	}

	@Override
	public boolean isQualified() {
		return true;
	}

	@Override
	public void setSilent(boolean value) {
		silent = value;
	}

	private void clog(String mesg) {
		if (LOG)// && !silent)
			System.out.println(mesg);
	}

	@Override
	public String addDetailToDiagraphAnnotation(EClass parent, String view,
			String key) {
		return utils.addDetailToDiagraphAnnotation(parent, view, key);
	}

	@Override
	public IDiagraphControler getControler() {
		return controler;
	}

	@Override
	public IDiagraphUtils getDiagraphUtils() {
		return this;
	}

	@Override
	public EcoreDiagramEditor getEditor() { // FP140117
		if (ecoreDiagramEditor == null)
			init();
		return ecoreDiagramEditor;
	}



	@Override
	public boolean isElementType(String view,String key, EClass eclaz) {
		return utils.isElementType(view, key, eclaz); //pov,node,link,generic
	}

	@Override
	public Map<EClass, List<String>> createLinkAnnotation(
			Map<EClass, List<String>> annotations, String pov, //int povid,
			EClass target){//, int level) {
		return utils.createLinkAnnotation(annotations, pov, target);
	}

	@Override
	public void dropConcreteSyntax(String view, EClass selectedClass,boolean register) {
		utils.dropConcreteSyntax(view, selectedClass,register);
	}


	@Override
	public boolean checkConcreteSyntax(String view) {
	//FP140430
		return utils.checkConcreteSyntax_(view);
	}


	@Override
	public void registerCommand(String cmd) { // FP140117
		utils.registerCommand(cmd);
	}

	@Override
	public String getDiagraphActions() {
		return utils.getDiagraphActions();
	}

	@Override
	public List<EClass> propagateNodes(String view,  EClass c) { //FP140605xxx
		return utils.propagateNodes(view, c);
	}


	/*
	@Override
	public void setControler(Object controler) {
		this.controler = (IDiagraphControler) controler;
		this.controler.registerService(this);
	}*/

	@Override
	public void setControler(Object controler) {
		this.controler = (IDiagraphControler) controler;
		this.controler.registerService(this);

	}


}
