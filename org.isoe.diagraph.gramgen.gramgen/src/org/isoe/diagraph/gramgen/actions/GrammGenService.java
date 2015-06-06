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
package org.isoe.diagraph.gramgen.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecoretools.legacy.diagram.edit.parts.EAnnotationEditPart;
import org.eclipse.emf.ecoretools.legacy.diagram.part.EcoreDiagramEditor;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.jface.action.Action;
import org.isoe.diagraph.commands.DiagraphCommandHandler;
import org.isoe.diagraph.diastyle.helpers.IErrorReporter;
import org.isoe.diagraph.gramgen.similarity.ConcreteSyntaxGenerator;
import org.isoe.diagraph.gramgen.similarity.ViewHandler;
import org.isoe.diagraph.gramgen.similarity.subGraphIsomorphism.EcoreSimilarityChecker;
import org.isoe.diagraph.lang.DiagraphKeywords;
import org.isoe.diagraph.langage.internal.utils.AnnotationUtils;
import org.isoe.extensionpoint.gramgen.IAnnotationHelper;
import org.isoe.extensionpoint.gramgen.IDiagraphUtils;
import org.isoe.extensionpoint.gramgen.IGrammarGeneratorService;
import org.isoe.extensionpoint.gramgen.IViewGenerator;
import org.isoe.fwk.core.DParams;


/**
 *
 * @author bnastov initial contribution
 * @author fpfister adaptation to diagraph
 *
 */
import org.isoe.diagraph.controler.IDiagraphControler;  //FP140707refactored
import org.isoe.extensionpoint.diagraph.action.DiagraphService;  //FP140707_c_refactored
public class GrammGenService extends Action implements
		IGrammarGeneratorService, IDiagraphUtils, IAnnotationHelper,
		IViewGenerator {

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
	private ConcreteSyntaxGenerator concreteSyntaxGenerator;
	private String view;
	// private String argument;
	private EModelElement modelElement;
	// private IGraphicalEditPart graphicalEditPart;
	private boolean silent;
	// private List<EAnnotation> diagraphAnnotations;

	private String viewName;
	private int viewId;
	private Map<EClass, List<String>>[] concreteSyntax;

	private AnnotationUtils utils;// = LanguageUtils.getInstance(this);

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
		return concreteSyntaxGenerator.getRootOfView_(eclassName);
	}

	// old=public void run() {
	@Override
	public void generateDefaultAnnotations() {
		init();
		concreteSyntaxGenerator = ConcreteSyntaxGenerator.getInstance(this);
		EcoreSimilarityChecker.cleanCache();
		EClass fallbackPov = (EClass) controler.getCurrentEObject();
		if (LOG) {
			if (fallbackPov != null)
				clog("fallbackPov = " + fallbackPov.getName());
			else
				clog("fallbackPov is null !!!");
		}
		generateAutomaticConcreteSyntaxForDefaultView(fallbackPov);
	}

	// old
	@Override
	public void generatePointOfViewCandidates(IDiagraphControler iDiagraphControler) {
		if (LOG)
			clog("generatePointOfViewCandidates");
		init();
		concreteSyntaxGenerator = ConcreteSyntaxGenerator.getInstance(this);
		EcoreSimilarityChecker.cleanCache();
		EClass fallbackPov = (EClass) controler.getCurrentEObject();
		if (LOG) {
			if (fallbackPov != null)
				clog("fallbackPov = " + fallbackPov.getName());
			else
				clog("fallbackPov is null !!!");
		}
		generatePovCandidates(fallbackPov);
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

	public GrammGenService() {
		setText("Grammar Generator Service");
		setToolTipText("Grammar Generator Service");
		utils = AnnotationUtils.getInstance(this);
	}

	@Override
	public void removeAnnotation(IGraphicalEditPart graphicalEditPart) {
		// argument = "";
		this.modelElement = (EModelElement) ((Node) graphicalEditPart
				.getModel()).getElement();
		// this.graphicalEditPart = graphicalEditPart;
		utils.deleteAnnotation_((EAnnotation) modelElement,
				(EAnnotationEditPart) graphicalEditPart);
	}

	@Override
	public void addDetail(EModelElement element, String view, String argument) {
		this.view = view;
		// this.argument = argument;
		this.modelElement = element;
		utils.addDetail(element, modelElement, view, argument);
	}

	@Override
	public EClass addView() {
		init();
		concreteSyntaxGenerator = ConcreteSyntaxGenerator.getInstance(this);
		EcoreSimilarityChecker.cleanCache(); // FP130611
		return doAddView();
	}

	@Override
	public void computePov() {
		init();
		concreteSyntaxGenerator = ConcreteSyntaxGenerator.getInstance(this);
		EcoreSimilarityChecker.cleanCache(); // FP130611
		viewRoot = computePov((EClass) controler.getCurrentEObject()); // FP130611
		if (viewRoot != null) {
			controler.log("computed pov:", viewRoot.getName());
			if (LOG)
				clog("computed pov: " + viewRoot.getName());
		} else {
			controler.log("computed pov:", "null");
			if (LOG)
				clog("computed pov: null");
		}
	}

	public void run() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public void clearConcreteSyntax(String view) {
		utils.clearConcreteSyntax(view);
	}

	@Override
	public void addManualView(EClass povclass) {
		String curntView = controler.getCurrentView(20, ((EClass) controler
				.getCurrentElement()).getEPackage().getName(), this.getClass()
				.getName()); //FP140611
		String newView = "default";
		int newId = 0;
		String newpov = controler.getNewPov();
		if (!newpov.isEmpty() && !newpov.equals("default")) {
			newView = DParams.VIEW_PREFIX + controler.getNewPov(); // "view=" +
																	// //FP131126
			newId = controler.getNewPovId();
		}
	}

	@Override
	public EClass getComputedPov() { // FP130611
		return viewRoot;
	}

	/*
	 * @Override public void setArgument(String arg) { //this.argument = arg; //
	 * FP131112 }
	 */
	/*
	 * @Override public void setGraphicalEditPart(IGraphicalEditPart
	 * graphicalEditPart) { this.graphicalEditPart = graphicalEditPart; }
	 */

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
	public void setControler(Object controler) {
		this.controler = (IDiagraphControler) controler;
		this.controler.registerService(this);
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
	public void verifyAfterPov(EClass rootclass) {
		utils.verifyAfterPov(rootclass);
	}

	@Override
	public String getPovCandidate(EClass eclaz) {
		return concreteSyntaxGenerator.getPointOfViewCandidate(eclaz);
	}

	@Override
	public Map<EClass, List<String>> createPovAnnotation(
			Map<EClass, List<String>>[] maps, String srcpov, String targetpov,
			EClass source, EClass target, boolean addNavAnnotation) {
		return utils.createPovAnnotation_(maps, srcpov, targetpov, source,
				target, addNavAnnotation);
	}

	@Override
	public Map<EClass, List<String>> createGenericAnnotation(
			Map<EClass, List<String>> annotations, String pov, EClass target) {
		return utils.createGenericAnnotation(annotations, pov, target, -1);
	}

	@Override
	public Map<EClass, List<String>> createNodeAnnotation1(
			Map<EClass, List<String>> annotations, String pov,// int povid,
			EClass target) {// , int level) {
		return utils.createNodeAnnotation1(annotations, pov, target);
	}

	/*
	 * @Override public Map<EClass, List<String>> createNodeAnnotation2(EditPart
	 * editPart, Map<EClass, List<String>> annotations, String pov,// int povid,
	 * EClass target){//, int level) { return
	 * utils.createNodeAnnotation2(editPart,annotations, pov, target); }
	 */
	@Override
	public boolean isElementType(String view, String key, EClass eclaz) {
		return utils.isElementType(view, key, eclaz); // pov,node,link,generic
	}

	@Override
	public Map<EClass, List<String>> createLinkAnnotation(
			Map<EClass, List<String>> annotations, String pov, // int povid,
			EClass target) {// , int level) {
		return utils.createLinkAnnotation(annotations, pov, target);
	}

	@Override
	public void dropConcreteSyntax(String view, EClass selectedClass,
			boolean register) {
		// FP140429xxxx
		// utils.checkConcreteSyntax(view, selectedClass);
		utils.dropConcreteSyntax(view, selectedClass, register);
	}

	@Override
	public boolean checkConcreteSyntax(String view) {
		// FP140430
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

	private void generatePovCandidates(EClass fallBackmodelRoot) {

		concreteSyntaxGenerator.generate((IErrorReporter) getControler(),ecoreDiagramEditor, fallBackmodelRoot,
				true);
		List<String> cands = concreteSyntaxGenerator.getPointOfViewCandidates();
		controler.setPointOfViewCandidates(cands);
	}

	@Override
	public void generate() {
		concreteSyntaxGenerator.generate((IErrorReporter) getControler(),ecoreDiagramEditor, null, false);
	}

	private void computeView() {
		//FP140611
		ViewHandler mv = new ViewHandler(this, controler.getCurrentView(21,controler.getAddedPov().getEPackage().getName(),
				this.getClass().getName()), controler.getViewName(),
				controler.getAddedPov(), concreteSyntaxGenerator.getElements(),
				controler.getEcoreDiagramEditor());
		mv.generateSyntax();
		concreteSyntax = new HashMap[2];
		concreteSyntax[0] = mv.getElements();
		viewName = "view=" + DParams.VIEW_PREFIX + getControler().getNewPov(); // FP131126
		viewId = getControler().getNewPovId(); // / 2??
	}

	// old=public void generate(EClass fallBackmodelRoot)
	private void generateAutomaticConcreteSyntaxForDefaultView(
			EClass fallBackRoot) {
		clearConcreteSyntax(view);
		concreteSyntaxGenerator
				.generate((IErrorReporter) getControler(),ecoreDiagramEditor, fallBackRoot, true);
		String pov = "default";
		int povid = 0;
		Map<EClass, List<String>>[] concreteSyntax = new HashMap[2];
		concreteSyntax[0] = concreteSyntaxGenerator.getElements();

		arguments = concreteSyntax;

		new DiagraphCommandHandler(this).sGenerateAll(null, pov, povid,
				DiagraphKeywords.UNKNOWN);
		controler.setPointOfViewCandidates(concreteSyntaxGenerator
				.getPointOfViewCandidates());
		if (!controler.setCurrentLanguageDiagram())
			clog("pb whith setCurrentLanguageDiagram");
	}

	// old=public void multiview() {
	private EClass doAddView() {
		EClass cls = controler.getAddedPov();
		if (cls == null)
			controler.showMessage("No added view");
		else {
			if (concreteSyntaxGenerator.getElements() == null)
				controler
						.showMessage("Run an automatic generation of annotations before choosing the multiview generation feature");
			else if (controler.getEcoreDiagramEditor() != null) {
				String newpov = getControler().getNewPov();
				if (newpov.isEmpty() || newpov.equals("default")) {
					controler.log("no point of view to compute");
					clog("no point of view to compute");
					return cls;
				}
				computeView();
				new DiagraphCommandHandler(this).sGenerateAll();
			} else
				controler.showMessage("Open the diagram (.ecorediag)");
		}
		return cls;
	}

	// old=public EClass computePov(EClass fallBackmodelRoot)
	private EClass computePov(EClass fallback) {// FP130611null;similarityChecker.computePov(ecoreDiagramEditor,fallBackmodelRoot);
		EClass result = null;
		try {
			result = concreteSyntaxGenerator.computePov(ecoreDiagramEditor,
					fallback);
		} catch (Exception e) {
			clog("no pov was found !");
		}
		return result;
	}

	@Override
	public List<EClass> propagateNodes(String view, EClass c) {
		return utils.propagateNodes(view, c);
	}

}
