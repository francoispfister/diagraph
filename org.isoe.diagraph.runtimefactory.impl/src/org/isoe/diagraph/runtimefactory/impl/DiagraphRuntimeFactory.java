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
package org.isoe.diagraph.runtimefactory.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecoretools.legacy.diagram.part.EcoreDiagramEditor;
import org.eclipse.emf.ecoretools.legacy.diagram.part.EcoreDiagramEditorUtil;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.INodeEditPart;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.isoe.diagraph.commands.DiagraphCommandHandler;
import org.isoe.diagraph.generic.GenericConstants;
import org.isoe.diagraph.lang.DiagraphKeywords;
import org.isoe.diagraph.preferences.DiagraphPreferences;
import org.isoe.diagraph.runtimefactory.IDiagraphRuntimeFactory;
import org.isoe.diagraph.views.ViewConstants;
import org.isoe.extensionpoint.diagen.IDiagraphAlphabet;
import org.isoe.extensionpoint.gramgen.IAnnotationHelper;
import org.isoe.extensionpoint.gramgen.IDiagraphUtils;
import org.isoe.extensionpoint.gramgen.IGrammarGeneratorService;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.utils.ResourceManager;
import org.isoe.fwk.utils.ResourceUtils;
import org.isoe.fwk.utils.eclipse.WorkbenchUtils;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.parts.DiagramDocumentEditor;
import org.isoe.diastyle.lang.StyleConstants;



/**
 *
 * @author fpfister
 *
 */
import org.isoe.diagraph.controler.IDiagraphControler;  //FP140707refactored
import org.isoe.extensionpoint.diagraph.action.DiagraphService;  //FP140707_c_refactored
public class DiagraphRuntimeFactory implements IAnnotationHelper,
		IDiagraphUtils, IDiagraphRuntimeFactory {

	public enum ActionMode {
		simple, execution, propagation
	};

	private static final boolean LOG = DParams.DiagraphRuntimeFactory_LOG;
	private static final boolean UI_LOG = DParams.UI_LOG;

	private IDiagraphControler diagraphControler;
	private IGrammarGeneratorService grammarGeneratorService;

	private boolean automultiview = false;
	private static final boolean EXEC = true;
	private static final boolean NOT_EXEC = false;

	private Map<EClass, List<String>>[] arguments;

	private void removeDiagraphAnnotationsFromDiagram(EPackage pakag,
			String view) { // FP140421
		if (UI_LOG)
			clogui("removeDiagraphAnnotationsFromDiagram on "+pakag.getName()+"."+view);
		EcoreDiagramEditor diagramEditor = getEcoreDiagramEditor(pakag
				.getName());
		List<EAnnotation> toremove = new ArrayList<EAnnotation>();
		for (EClassifier classifier : pakag.getEClassifiers()) {
			if (classifier instanceof EClass) {
				for (EAnnotation eAnnotation : ((EClass) classifier)
						.getEAnnotations())
					if (eAnnotation.getSource().equals(
							GenericConstants.ANNOT_DIAGRAPH_LITTERAL)
							&& isView(eAnnotation, view)) {
						toremove.add(eAnnotation);
					}
			}
		}
		new DiagraphCommandHandler(this).removeDiagraphAnnotationsFromDiagram(
				diagramEditor, toremove);
	}

	private void saveAllEditors() {
		IWorkbenchPage workbenchPage = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		if (workbenchPage != null)
			workbenchPage.saveAllEditors(false);
	}

	private String modelElementToString(EModelElement me) {
		if (me instanceof EClass)
			return ((EClass) me).getName();
		if (me instanceof EStructuralFeature) {
			EStructuralFeature sf = (EStructuralFeature) me;
			EClass cc = ((EStructuralFeature) me).getEContainingClass();
			return cc.getName() + "." + sf.getName();
		} else if (me instanceof ENamedElement)
			return ((ENamedElement) me).getName();
		else if (me instanceof EAnnotation
				&& ((EAnnotation) me).eContainer() instanceof EClass)
			return ((EClass) ((EAnnotation) me).eContainer()).getName()
					+ ".diagraph.annotation";
		else
			return "";
	}

	private EPackage modelElementToPackage(EModelElement me) {
		if (me instanceof EClass)
			return ((EClass) me).getEPackage();
		else if (me instanceof EStructuralFeature) {
			EStructuralFeature sf = (EStructuralFeature) me;
			EClass cc = ((EStructuralFeature) me).getEContainingClass();
			return cc.getEPackage();
		} else if (me instanceof EAnnotation
				&& ((EAnnotation) me).eContainer() instanceof EClass)
			return ((EClass) ((EAnnotation) me).eContainer()).getEPackage();
		else
			throw new RuntimeException(
					"should not happen in modelElementToPackage");
	}

	private void addOnElement(String action, String arg, String metaclassname) {
		action = getSuffix(action);
		EModelElement currentElement = getCurrentElement();
		EPackage pak = modelElementToPackage(currentElement);
		String clazname = currentElement.getClass().getSimpleName();
		if (clazname.endsWith("Impl"))
			clazname = clazname.substring(0, clazname.length() - 4);
		if (!clazname.equals(metaclassname)) {
			diagraphControler.showMessage("failed with " + arg + " on "
					+ metaclassname + " ("
					+ modelElementToString(currentElement) + ")");
			return;
		} else {
			if (LOG)
				clog("ok with " + arg + " on " + metaclassname + " ("
						+ modelElementToString(currentElement) + ")");
			String curntv = diagraphControler.getCurrentView(12, pak.getName(),
					this.getClass().getName()); // FP140611
			String viewArg = normalize(curntv);
			if (!action.startsWith("exec."))
				register(action + ";" + viewArg + ";"
						+ modelElementToString(currentElement));
			grammarGeneratorService.addDetail(currentElement, curntv, arg);
		}
	}

	private void addNewPov(String action, String metaclassname, boolean auto) {
		action = getSuffix(action);
		EModelElement currentElement = getCurrentElement();
		String clazname = currentElement.getClass().getSimpleName();
		if (clazname.endsWith("Impl"))
			clazname = clazname.substring(0, clazname.length() - 4);
		if (!clazname.equals(metaclassname)) {
			diagraphControler.showMessage("failed with " + "adding navigation"
					+ " on " + metaclassname + " ("
					+ modelElementToString(currentElement) + ")");
			return;
		} else {
			String ppovv = diagraphControler.getPovCandidate(currentElement);
			String viewArg = normalize(ppovv);
			if (!action.startsWith("exec."))
				register(action + ";" + viewArg + ";"
						+ modelElementToString(currentElement));

			diagraphControler.generateNewPov(currentElement, auto); // FP131126xx
			String pov_ = diagraphControler.getPovCandidate(currentElement);

			// String pov=diagraphControler.getPovCandidate(currentElement);
			if (LOG)
				clog("ok with adding navigation " + pov_ + " on "
						+ metaclassname + " ("
						+ modelElementToString(currentElement) + ")");

			// grammarGeneratorService.setRole(currentElement, getCurrentView(),
			// arg_);
			clog(action + "on " + clazname + " " + pov_);
		}
	}

	private EClass getCurrentEClass() { // FP131122
		return (EClass) diagraphControler.getCurrentEObject();
	}

	/*
	 * private String getCurrentView() {// FP131122 return
	 * diagraphControler.getCurrentView(); }
	 */

	private List<String> getAllViews() {
		return diagraphControler.getAllViews();// FP140501
	}

	private IGraphicalEditPart getCurrentGraphicalEditPart() {// FP131122
		return diagraphControler.getCurrentGraphicalEditPart();
	}

	private EModelElement getCurrentElement() {// FP131122
		return (diagraphControler.getElementStack() != null) ? (EModelElement) diagraphControler
				.getElementStack()[0] : null;
	}

	private EClass getSelectedClass() {
		return diagraphControler.getSelectedClass();
	}

	private int getCurrentViewId_() { // FP140605
		return 0;// diagraphControler.getCurrentViewId__();
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	private void register(String cmd) {
		grammarGeneratorService.registerCommand(cmd);
	}

	private void generateGrammar(String action) {
		grammarGeneratorService.generateDefaultAnnotations();
	}

	private void generatePovCandidates() {
		grammarGeneratorService.generatePointOfViewCandidates(this.getControler());
	}

	private void addView(String action) {
		diagraphControler.showMessage("not yet implemented :" + "addView");
	}

	private void clearConcreteSyntax_(String action) {
		String langname = diagraphControler.getLanguageName(); // FP140611
		grammarGeneratorService.clearConcreteSyntax(diagraphControler
				.getCurrentView(13, langname, this.getClass().getName()));
	}

	private void dropConcreteSyntax(String action, EClass toDropFor,
			EPackage epackage, String view) {// String action
		String curntview = diagraphControler.getCurrentView(14,
				epackage.getName(), this.getClass().getName());
		// FP140611
		if (UI_LOG) {
			// String curntview=diagraphControler.getCurrentView(14);
			clogui("dropConcreteSyntax on "+epackage.getName()+"."+(curntview == null ? "currentView=null" : curntview));
			if (false){
			 clogui(action == null ? "action=null" : action);
			 clogui(toDropFor == null ? "toDropFor=null" : "toDropFor="+toDropFor.getName());
			 EClass curntclass = getCurrentEClass();
			 clogui(curntclass == null ? "currentEClass=null" : "currentEClass="+curntclass.getName());
			 EModelElement curel = getCurrentElement();
			 clogui(curel == null ? "currentElement=null" : "currentElement="+curel.getClass().getName());
			 EClass selClass = getSelectedClass();
			 clogui(selClass == null ? "selectedClass=null" : "selectedClass="+selClass.getName());
			}
		}
		grammarGeneratorService.dropConcreteSyntax(curntview, toDropFor,
				action != null);
		diagraphControler.updateColor(epackage.getName(), curntview);// FP140611b
	}

	private void clogui(String mesg) {
		if (UI_LOG)
			System.out.println(mesg);
	}

	private boolean createNodeAnnotation1(ActionMode mode, String cmd,
			String view, // int povid,
			EClass eclaz) {// , int level) {
		boolean result = false;
		// int povid = getControler().getViewId__(view); //FP140605xxx
		// int povid = 0;//getControler().getViewId__(view);
		String viewArg = normalize(view);
		if (mode == ActionMode.simple) // register macro only if simple mode,
										// not if execution (replay) or
										// propagation
			register(getSuffix(cmd) + ";" + viewArg + ";" + eclaz.getName());
		IGraphicalEditPart currentGraphicalEditPart_nu = diagraphControler
				.getCurrentGraphicalEditPart();
		Map<EClass, List<String>> annotations = new HashMap<EClass, List<String>>();
		if (grammarGeneratorService.isElementType(view, "node", eclaz)) // node,link,generic,pov
			System.err.println("node annotation allready exists  for view "
					+ view + " and eclass " + eclaz.getName());
		else if (grammarGeneratorService.createNodeAnnotation1(annotations,
				view, eclaz) == null) {// povid,, level
			System.err.println("createNodeAnnotation failed on " + view
					+ " for " + eclaz.getName());
		} else {
			if (LOG)
				clog("CNAN ok " + cmd);
			result = true;
		}
		return result;
	}

	/*
	 *
	 * private boolean createNodeAnnotation2(ActionMode mode, String cmd, String
	 * view, // int povid, EClass eclaz) {// , int level) { boolean result =
	 * false; int povid = getControler().getViewId(view); String viewArg =
	 * normalize(view); if (mode == ActionMode.simple) // register macro only if
	 * simple mode, // not if execution (replay) or // propagation
	 * register(getSuffix(cmd) + ";" + viewArg + ";" + eclaz.getName());
	 * IGraphicalEditPart currentGraphicalEditPart_nu = diagraphControler
	 * .getCurrentGraphicalEditPart(); Map<EClass, List<String>> annotations =
	 * new HashMap<EClass, List<String>>(); if
	 * (grammarGeneratorService.isElementType(view, "node", eclaz)) //
	 * node,link,generic,pov
	 * System.err.println("node annotation allready exists  for view " + view +
	 * " and eclass " + eclaz.getName());
	 *
	 * //EClassEditPart cannot be cast to
	 * org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart else if
	 * (grammarGeneratorService
	 * .createNodeAnnotation2(currentGraphicalEditPart_nu,annotations, view,
	 * eclaz) == null) {// povid,, level
	 * System.err.println("createNodeAnnotation failed on " + view + " for " +
	 * eclaz.getName()); } else { if (LOG) clog("CNAN ok " + cmd); result =
	 * true; } return result; }
	 */

	private boolean createPovAnnotation(String action, String frompov_,
			String topov, EClass fromClass, EClass toClass, // int povid,
			boolean addNavAnnotation) { // int level,
		boolean result = true;
		action = getSuffix(action);
		String fromViewArg = normalize(frompov_);
		String toViewArg = normalize(topov);
		if (!action.startsWith("exec."))
			register(action + ";" + fromViewArg + ";" + fromClass.getName()
					+ ";" + toViewArg + ";" + toClass.getName());
		Map<EClass, List<String>> annotationsToAdd = new HashMap<EClass, List<String>>();
		Map<EClass, List<String>> annotationsToAddDetail = new HashMap<EClass, List<String>>();
		Map<EClass, List<String>>[] maps = new HashMap[2];
		maps[0] = annotationsToAdd;
		maps[1] = annotationsToAddDetail;
		// maps[1]=annotationsToAdd;
		// if (addNavAnnotation)
		// addDetailToDiagraphAnnotation_(fromClass,frompov,"nav:"+DParams.VIEW_PREFIX+toClass.getName().toLowerCase());

		arguments = maps;
		if (grammarGeneratorService.createPovAnnotation(maps, frompov_, topov,
				fromClass, toClass, addNavAnnotation) != null) { // povid,
																	// //level,

		}
		return result;
	}

	private boolean createLinkAnnotation(String action, String pov, // int
																	// povid,
			EClass eclaz) {// , int level) {
		boolean result = false;
		action = getSuffix(action);
		String npov = normalize(pov);
		if (!action.startsWith("exec."))
			register(action + ";" + npov + ";" + eclaz.getName());
		Map<EClass, List<String>> annotations = new HashMap<EClass, List<String>>();

		if (grammarGeneratorService.isElementType(pov, "link", eclaz)) // node,link,generic,pov
																		// ?
			System.err.println("link annotation allready exists  for view "
					+ pov + " and eclass " + eclaz.getName());
		else if (grammarGeneratorService.createLinkAnnotation(annotations, pov,
				eclaz) == null) {// povid,, level
			System.err.println("createLinkAnnotation failed on " + pov
					+ " for " + eclaz.getName());
		} else {
			if (LOG)
				clog("CLAN ok");
			result = true;
		}
		return result;
	}

	private boolean createGenericAnnotation_(String action, String view,
			EClass eclaz) {
		boolean result = false;
		// int povid = 0;//getControler().getViewId__(view); //FP140605xxx
		String viewArg = normalize(view);
		if (!action.startsWith("exec."))
			register(getSuffix(action) + ";" + viewArg + ";" + eclaz.getName());
		IGraphicalEditPart currentGraphicalEditPart_nu = diagraphControler
				.getCurrentGraphicalEditPart();
		Map<EClass, List<String>> annotations = new HashMap<EClass, List<String>>();
		if (grammarGeneratorService.isElementType(view, null, eclaz)) // node,link,generic,pov
																		// ?
			System.err.println("generic annotation allready exists  for view "
					+ view + " and eclass " + eclaz.getName());
		else if (grammarGeneratorService.createGenericAnnotation(annotations,
				view, eclaz) == null) {// povid,, level
			System.err.println("createGenericAnnotation failed on " + view
					+ " for " + eclaz.getName());
		} else {
			if (LOG)
				clog("CGAN ok");
			result = true;
		}
		return result;
	}

	private List<EClass> propagateNodes(String actionid, String view,
			EClass eclaz) {
		// int povid = 0;//getControler().getViewId__(view); //FP140605xxx
		return grammarGeneratorService.propagateNodes(view, eclaz);
	}

	private List<EClass> propagatePovs(String actionid, String view,
			EClass eclaz) {
		return null;
		// TODO Auto-generated method stub

	}

	private List<EClass> propagateLinks(String actionid, String view,
			EClass eclaz) {
		return null;
		// TODO Auto-generated method stub

	}

	private List<EClass> propagateGenerics(String actionid, String view,
			EClass eclaz) {
		return null;
		// TODO Auto-generated method stub

	}

	private void dropDiagraphAnnotations(EPackage epackage, String view) {
		dropConcreteSyntax(null, null, epackage, view);
	}

	private boolean isDefaultView(EAnnotation anot) {
		for (Map.Entry<String, String> entry : anot.getDetails())
			if (entry.getKey().startsWith("view="))
				return false;
		return true;
	}

	private boolean isView(EAnnotation anot, String view) {
		if (view == null || view.equals(ViewConstants.DIAGRAPH_DEFAULT)
				|| view.isEmpty())
			return isDefaultView(anot);
		for (Map.Entry<String, String> entry : anot.getDetails())
			if (entry.getKey().equals("view=" + view))
				return true;
		return false;
	}

	private void removeAnnotation(String action) {
		EModelElement currentElement = getCurrentElement();// (EModelElement)
															// diagraphControler.getElementStack()[0];
		if (currentElement instanceof EAnnotation) {
			EAnnotation eAnnotation = (EAnnotation) currentElement;
			EClass curnt = (EClass) eAnnotation.getEModelElement();
			grammarGeneratorService
					.removeAnnotation(getCurrentGraphicalEditPart());
			// removeDiagraphAnnotation(curnt, getCurrentView());
		} else if (currentElement instanceof EClass) {
			EClass curnt = (EClass) currentElement;
			// removeDiagraphAnnotation_nu(curnt, getCurrentView());
			String lang = curnt.getEPackage().getName(); // FP140611
			deleteAnnotation(curnt, diagraphControler.getCurrentView(15, lang,
					this.getClass().getName()));
		} else
			diagraphControler.showMessage("failed with " + "removeAnnotation"
					+ " on " + getCurrentEClass().getName() + " ("
					+ modelElementToString(currentElement) + ")");
	}


	//FP141227int

	private boolean[] genericCommand(boolean exec, String actionid) {
		boolean[] result = new boolean[2];
		result[0] = false; // handled
		result[1] = true; // must refresh
		if (actionid.endsWith("cmd.layout")) {
			layout("cmd.layout");
			result[0] = true;
			result[1] = false;
			// dropConcreteSyntax("cmd.drop");
			// save();
		} else if (actionid.endsWith("cmd.auto")) {
			generateGrammar("cmd.auto");
			result[0] = true;
		} else if (actionid.endsWith("cmd.addview")) {
			addView("cmd.addview");
			result[0] = true;
		} else if (actionid.endsWith("cmd.txt")) {
			String reslt = showTextualCS_("cmd.txt");
			openMegamodelEditor();
			result[0] = true;
		} else if (actionid.endsWith("cmd.btndelannot")) {
			removeAnnotation("cmd.btndelannot");
			result[0] = true;
		} else if (actionid.endsWith("cmd.clear")) {
			clearConcreteSyntax_("cmd.clear");
			// save();
			result[0] = true;
		} else if (actionid.endsWith("cmd.drop")) {
			dropConcreteSyntax("cmd.drop", getSelectedClass(),
					getSelectedClass().getEPackage(),
					diagraphControler.getCurrentView(555, getSelectedClass()
							.getEPackage().getName(), "genericCommand")); // TODO
																			// voir
																			// //FP140611
			result[0] = true;
			// save();
		}
		return result;
	}

	private boolean[] createNodeAnnotationAndPropagate1(boolean exec,
			String actionid, String view, EClass eclaz) { // FP140119
		boolean[] result = new boolean[2];
		result[0] = true; // handled
		result[1] = true; // must refresh
		if (!createNodeAnnotation1(exec ? ActionMode.execution
				: ActionMode.simple, actionid, view, eclaz))
			result[1] = false;
		else {
			List<EClass> propagated = propagateNodes(actionid, view, eclaz);
			if (propagated != null)
				for (EClass eClass : propagated)
					if (!createNodeAnnotation1(ActionMode.propagation,
							actionid, view, eClass))
						result[1] = false;

		}
		return result;
	}

	/*
	 *
	 * private boolean[] createNodeAnnotationAndPropagate2_(boolean exec, String
	 * actionid, String view, EClass eclaz) { // FP140119 boolean[] result = new
	 * boolean[2]; result[0] = true; // handled result[1] = true; // must
	 * refresh if (!createNodeAnnotation2(exec ? ActionMode.execution :
	 * ActionMode.simple, actionid, view, eclaz)) result[1] = false; else {
	 * List<EClass> propagated = propagateNodes(actionid, view, eclaz); if
	 * (propagated != null) for (EClass eClass : propagated) if
	 * (!createNodeAnnotation2(ActionMode.propagation, actionid, view, eClass))
	 * result[1] = false;
	 *
	 * } return result; }
	 */

	private boolean[] actionOnCurrentElement(boolean exec, String view,// int
																		// povid,
			String actionid, EClass eclaz) { // int level,
		boolean[] result = new boolean[2];
		result[0] = false; // handled
		result[1] = true; // must refresh

		save();
		EModelElement currentElement = getCurrentElement();
		if (eclaz == null)
			eclaz = getCurrentEClass();

		if (eclaz == null)
			eclaz = diagraphControler.getSelectedClass(); // if not diagraphed
															// //FP131201

		if (actionid.endsWith("add.node")) {

			result = createNodeAnnotationAndPropagate1(exec, actionid, view,
					eclaz);

		}

		if (actionid.endsWith("add.generic")) {
			result[0] = true;
			if (!createGenericAnnotation_(actionid, view, eclaz))
				result[1] = false;
			else
				propagateGenerics(actionid, view, eclaz);
		}

		else if (actionid.endsWith("add.kref")) {
			result[0] = true;
			addOnElement(actionid,
					DiagraphKeywords.KREFERENCE_EQU_ + ((ENamedElement) currentElement).getName(),
					"EReference");
		} else if (actionid.endsWith("add.afx")) {
			result[0] = true;
			addOnElement(actionid,
					DiagraphKeywords.AFFIXED_EQU_ + ((ENamedElement) currentElement).getName(),
					"EReference");
		} else if (actionid.endsWith("add.link")) {
			result[0] = true;
			// createLinkAnnotation(actionid, view, eclaz);
			if (!createLinkAnnotation(actionid, view, eclaz))
				result[1] = false;
			else
				propagateLinks(actionid, view, eclaz);
		} else if (actionid.endsWith("add.cont")) {
			result[0] = true;
			addOnElement(actionid, DiagraphKeywords.CONTAINMENT_EQU + "Foo.bar", "EClass");
		} else if (actionid.endsWith("add.lsrc")) {
			result[0] = true;
			addOnElement(actionid,
					"lsrc=" + ((ENamedElement) currentElement).getName(),
					"EReference");
		} else if (actionid.endsWith("add.ltrg")) {
			result[0] = true;
			addOnElement(actionid,
					"ltrg=" + ((ENamedElement) currentElement).getName(),
					"EReference");
		} else if (actionid.endsWith("add.label")) {
			result[0] = true;
			addOnElement(actionid, "label=" + "name", "EAttribute");
		} else if (actionid.endsWith("add.shape")) {
			result[0] = true;
			addShape(actionid);
		} else if (actionid.endsWith("add.icon")) {
			result[0] = true;
			addIcon(actionid);
		} else if (actionid.endsWith("add.pov")) {
			result[0] = true;
			if (!createPovAnnotation(actionid, "", view, null, eclaz, false))
				result[1] = false;
			else
				propagatePovs(actionid, view, eclaz);
		} else if (actionid.endsWith("add.view")) {
			result[0] = true;
			addOnElement(actionid, "view=" + DParams.VIEW_PREFIX
					+ ((ENamedElement) currentElement).getName().toLowerCase(),
					"EClass");
		} else if (actionid.endsWith("add.nav")) {
			/*
			 * if (level >= 1) { // addOnElement(actionid, "nav:" + //
			 * DParams.VIEW_PREFIX+((ENamedElement) //
			 * currentElement).getName().toLowerCase(), "EClass");
			 * addNewPov(actionid, "EClass", automultiview); result[0] = true; }
			 * else
			 */// FP140118
			{
				result[0] = true;
				addOnElement(actionid, "nav:"
						+ DParams.VIEW_PREFIX
						+ ((ENamedElement) currentElement).getName()
								.toLowerCase(), "EClass");
			}
		}
		// enlever view rouge; rempl add.view par add.nav
		else if (actionid.endsWith("add.lnk")) {
			result[0] = true;
			addOnElement(actionid,
					"lnk=" + ((ENamedElement) currentElement).getName(),
					"EReference");
		} else if (actionid.endsWith("add.cref")) {
			result[0] = true;
			addOnElement(actionid,
					"cref=" + ((ENamedElement) currentElement).getName(),
					"EReference");
		} else if (actionid.endsWith("add.ref")) {
			result[0] = true;
			addOnElement(actionid,
					"ref=" + ((ENamedElement) currentElement).getName(),
					"EReference");
		}
		return result;
	}

	private void layout(String action) {
		diagraphControler.layoutSaveAndRefresh();
	}

	private String getSuffix(String action) {
		String s = action.substring(0, action.lastIndexOf("."));
		s = s.substring(0, s.lastIndexOf("."));
		return action.substring(s.length() + 1);
	}

	// TODO correct these 2 errors at the source
	private String normalize(String viewarg) {
		if (!viewarg.startsWith("view=" + DParams.VIEW_PREFIX))
			viewarg = "view=" + DParams.VIEW_PREFIX + viewarg; // FP1126xx

		if (viewarg.contains("view=" + DParams.VIEW_PREFIX
				+ DParams.VIEW_PREFIX))
			viewarg = "view="
					+ DParams.VIEW_PREFIX
					+ viewarg
							.substring(("view=" + DParams.VIEW_PREFIX + DParams.VIEW_PREFIX)
									.length());
		return viewarg;
	}

	/*
	 * private void createEmptyAnnotation() {
	 * grammarGeneratorService.createEmptyAnnotation(); }
	 */
	private void addIcon(String action) {
		action = getSuffix(action);
		diagraphControler.showMessage("not yet implemented :" + "addIcon");
	}

	private void addShape(String action) {
		action = getSuffix(action);
		diagraphControler.showMessage("not yet implemented :" + "addShape");
	}

	private void save() {
		saveAllEditors();
	}

	private IViewPart findView(String vid) {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().findView(vid);
	}

	private String showTextualCS_(String action) {
		boolean refreshOnlyYes = true; // FP140602see
		boolean genLanguageNo = false;
		String[] args = new String[2];
		args[0] = IDiagraphAlphabet.MEGAMODEL_CHECK_STATUS;
		args[1] = "";
		String result = diagraphControler.invokeMegamodelJob(null,false, args,
				genLanguageNo, refreshOnlyYes);
		if (result == null || result.isEmpty() || result.equals("ok")) {
			diagraphControler.getCurrentPackage();
			File fileToOpen = new File(
					diagraphControler.getPathUnderModel(diagraphControler
							.getCurrentPackage().getName() + "_diagen", "txt"));
			if (fileToOpen.exists() && fileToOpen.isFile()) {
				org.eclipse.core.filesystem.IFileStore fileStore = EFS
						.getLocalFileSystem().getStore(fileToOpen.toURI());
				IWorkbenchPage page = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage();
				try {
					IDE.openEditorOnFileStore(page, fileStore);
				} catch (PartInitException e) {
					System.err.println(e.toString());
				}
			}
		} else {
			System.err.println(result==null?"unknown error":result);
		}
		return result;
	}

	private IEditorPart openMegamodelEditor() {// FP131123
		final IProject project = ResourceManager.getProject("_megamodel");
		IFile diagenfile = ResourceUtils.getFile(project, "src" + "/"
				+ "megamodel", "megamodel");
		IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		try {
			return IDE.openEditor(page, diagenfile);
		} catch (PartInitException e) {
			clog("Open editor failed: " + diagenfile.toString() + " -- " + e);
			return null;
		}
	}

	private void generatexxx(final IProject project_, final IFile file) {
		StructuredSelection selection = new StructuredSelection(project_);
		final IActionDelegate delegate = new IActionDelegate() {
			@Override
			public void run(IAction action) {
				// //IObjectActionDelegate actionDelegate_ =
				// getAction("org.eclipse.ui.popupMenus",
				// "gmf.codegen.ui.executeTemplatesAction");
				clog("_____________");

				// IObjectActionDelegate actionDelegate__ =
				// getAction("org.eclipse.ui.popupMenus",
				// "gmf.codegen.ui.executeTemplatesAction");

				// IObjectActionDelegate actionDelegate__ =
				// getAction("org.eclipse.ui.commands",
				// "org.eclipse.emf.ecoretools.legacy.diagram.CreateShortcutAction");

				// if (actionDelegate__!=null)
				// tb = true;
				/*
				 * if (actionDelegate != null) { //FP131124
				 * actionDelegate.selectionChanged(action, new
				 * StructuredSelection(file));
				 *
				 * actionDelegate.setActivePart(action,
				 * WorkbenchUtils.showNavigator());//FP130108 // FP120522b try {
				 * actionDelegate.run(new Action() { }); } catch (Exception e) {
				 * clog("???? generateDiagram ????"); }
				 *
				 * }
				 */
			}

			public void selectionChanged(IAction action, ISelection selection) {
			}
		};
		delegate.run(null);
	}

	private IFile getModel(String projectname, String modelName) {
		// throws CoreException {
		try {

			IProject project = ResourceManager.getProject(projectname);
			if (project != null) {

				IFolder fo = project
						.getFolder(org.isoe.fwk.core.DParams.MODEL_FOLDER);
				URI ri = URI.createURI("platform:/resource/"
						+ fo.getFullPath().toPortableString());
				String filpath = ri.toFileString();
				if (LOG)
					clog("getModel: " + filpath);

				File modelfoldr = new File(CommonPlugin.resolve(ri)
						.toFileString());
				if (modelfoldr.exists()) {
					if (modelfoldr.isDirectory()) {
						for (File f : modelfoldr.listFiles()) {
							if (f.getName().equals(modelName)) {
								IFile dfile = fo.getFile(f.getName());
								return dfile;
							}
						}
					}
				}
			} else {
				System.err.println("no project : " + projectname);
				return null;
			}
		} catch (Exception e) {
			System.err.println("getModel error : " + e.toString());
			return null;
		}
		return null;

	}

	private void sleep(int timeout) {
		if (Display.getCurrent() == null)
			return;
		final long start = System.currentTimeMillis();
		final long deltaMillis = timeout;
		do {
			while (Display.getCurrent().readAndDispatch())
				;
		} while ((System.currentTimeMillis() - start) < deltaMillis);
	}

	private EcoreDiagramEditor getEcoreDiagramEditor(final String language) {
		EcoreDiagramEditor ecoreDiagramEditor = isDiagramOpen(language
				+ ".ecorediag");
		if (ecoreDiagramEditor == null) {
			openDiagram(ecoreDiagramEditor, language);
			ecoreDiagramEditor = isDiagramOpen(language + ".ecorediag");
		}
		return ecoreDiagramEditor;
	}

	private void selectPackage(EcoreDiagramEditor ecoreDiagramEditor) { // FP131124
		for (EditPart editPart : (Collection<EditPart>) (ecoreDiagramEditor
				.getDiagramGraphicalViewer().getEditPartRegistry()).values()) {
			if (editPart.getModel() instanceof Node) {
				if (((Node) editPart.getModel()).getElement() == ecoreDiagramEditor
						.getDiagram().getElement()) {
					List<EditPart> parts = new ArrayList<EditPart>();
					parts.add(editPart);
					EcoreDiagramEditorUtil.selectElementsInDiagram(
							ecoreDiagramEditor, parts);
				}
			}
		}
	}

	/*
	 * public EcoreDiagramEditor getDiagramEditor_(String language) { //
	 * FP131124 return getEcoreDiagramEditor(language); }
	 */

	private boolean filterExecutableCommand(String cmd) {
		if (cmd.contains("add.node")) {
			return true;
		}
		return false;
	}

	// "exec.add.node"

	private void deleteAnnotation(EClass claz, String view) { // FP131116
		EcoreDiagramEditor diagramEditor = getEcoreDiagramEditor(claz
				.getEPackage().getName());
		EAnnotation annotationToRemove = null;
		for (EAnnotation eAnnotation : claz.getEAnnotations())
			if (eAnnotation.getSource().equals(
					GenericConstants.ANNOT_DIAGRAPH_LITTERAL)
					&& isView(eAnnotation, view)) {
				annotationToRemove = eAnnotation;
				if (LOG)
					clog("removing annotation from "
							+ ((EClass) eAnnotation.getEModelElement())
									.getName() + " (view=" + view + ")");
				break;
			}

		new DiagraphCommandHandler(this).delete(diagramEditor,
				annotationToRemove);
	}

	private boolean[] invokeDiagraphAction(String cmd, String view,
			String eClassName) { // execution
		if (LOG)
			clog("IDA2 " + cmd);
		boolean[] result = new boolean[2];
		result[0] = false; // handled
		result[1] = true; // must refresh
		if (!filterExecutableCommand(cmd)) {
			result[1] = false; // handled but nothing done, so nothing to
								// refresh
			return result;
		}
		grammarGeneratorService.init();
		EClass eclaz = diagraphControler.getEClass(eClassName);
		boolean[] r1 = actionOnCurrentElement(EXEC, view, cmd, eclaz);// povid,,annotationLevel_,
		boolean[] r2 = genericCommand(EXEC, cmd);

		result[0] = r1[0] || r2[0]; // default = failed ; result = one did not
									// fail
		result[1] = r1[1] && r2[1]; // default = must refresh ; result = all doe
									// refresh
		return result;
	}

	/*----------------------------------------- to clean -------------------*/

	private void openDiagram_nu(final int delay, final String language) { // FP131124
		String prefix = DiagraphPreferences.getTeamNamespace();
		final IProject project = ResourceManager.getProject(prefix + "."
				+ language);
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				try {
					if (delay > 0)
						sleep(delay);
					EcoreDiagramEditor ecoreDiagramEditor = getEcoreDiagramEditor(language);
					if (ecoreDiagramEditor != null)
						selectPackage(ecoreDiagramEditor);
				} catch (Exception e) {
					e.printStackTrace();
					System.err.println("OD " + e.toString());
				}
			}
		});

	}

	// org.eclipse.gmf.runtime.common.ui.services.action.contributionItemProviders
	// org.eclipse.gmf.runtime.diagram.ui.providers.DiagramContextMenuProvider
	// deleteFromModelAction

	private void xxx(final IGraphicalEditPart gep) {

		// // FP131124 c'est batard mais c'est comme ça....
		final IActionDelegate delegate = new IActionDelegate() {
			@Override
			public void run(IAction action) {
				IViewPart v = PlatformUI
						.getWorkbench()
						.getActiveWorkbenchWindow()
						.getActivePage()
						.findView(
								"org.eclipse.emf.ecoretools.diagram.part.EcoreDiagramEditorID");
				IObjectActionDelegate actionDelegate = WorkbenchUtils
						.getAction(
								"org.eclipse.gmf.runtime.common.ui.services.action.contributionItemProviders",
								"toto");
				if (actionDelegate != null) {
					actionDelegate.selectionChanged(action,// selection_);
							new StructuredSelection(gep));
					// IViewPart navigator = WorkbenchUtils.showNavigator();

					// gep.getNotationView().getDiagram().get
					actionDelegate.setActivePart(action, v);// FP130108 //
															// FP120522b
					try {
						actionDelegate.run(new Action("deleteFromModelAction") {
						});
					} catch (Exception e) {
						clog("???? xxxx ????");
					}

				}
			}

			public void selectionChanged(IAction action, ISelection selection) {
			}
		};
		delegate.run(null);
	}

	private IActionDelegate getAction2_nu(String extensionPoint, String action) {
		IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		IConfigurationElement[] configurationElements = extensionRegistry
				.getConfigurationElementsFor(extensionPoint);
		for (IConfigurationElement configurationElement : configurationElements) {
			String cfgid = configurationElement.getAttribute("id");
			if (cfgid != null && LOG) // FP131124
				clog("WBU " + cfgid);
			IConfigurationElement[] children = configurationElement
					.getChildren();
			for (IConfigurationElement child : children) {
				String id = child.getAttribute("id");
				if (id != null && LOG)
					clog("WBU    " + id);
				if (action.equals(id)) {
					try {
						return (IActionDelegate) child
								.createExecutableExtension("class");
					} catch (CoreException e) {
						// e.printStackTrace();
						return null;
					}
				}
			}
		}
		return null;
	}

	private IViewPart showNavigator_nu() { // FP130130 err here
		IViewPart navigator = null;
		try {
			navigator = findView("org.eclipse.jdt.ui.PackageExplorer"); // FP130130x
			if (navigator == null)
				navigator = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage()
						.showView("org.eclipse.jdt.ui.PackageExplorer");

		} catch (PartInitException e1) {
			e1.printStackTrace();
			return null;
		}
		return navigator;
	}

	private IObjectActionDelegate getAction_nu(String extensionPoint,
			String action) {
		boolean check;
		IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		IConfigurationElement[] configurationElements = extensionRegistry
				.getConfigurationElementsFor(extensionPoint);
		for (IConfigurationElement configurationElement : configurationElements) {
			String cfgid = configurationElement.getAttribute("id");
			if (cfgid != null && LOG) { // FP131124
				clog("WBUa " + cfgid);
				if (cfgid.contains("ecoretools"))
					check = true;
			}
			IConfigurationElement[] children = configurationElement
					.getChildren();
			for (IConfigurationElement child : children) {
				String id = child.getAttribute("id");
				if (id != null && LOG)
					clog("WBUb    " + id);
				if (action.equals(id)) {
					try {
						return (IObjectActionDelegate) child
								.createExecutableExtension("class");
					} catch (CoreException e) {
						// e.printStackTrace();
						return null;
					}
				}
			}
		}
		return null;
	}

	private IEditorPart openEditor_nu(IWorkbenchPage page, ISelection selection) {// FP131123

		// Get the first element.

		if (!(selection instanceof IStructuredSelection))
			return null;
		Iterator iter = ((IStructuredSelection) selection).iterator();
		if (!iter.hasNext())
			return null;
		Object elem = iter.next();
		// Adapt the first element to a file.

		if (!(elem instanceof IAdaptable))
			return null;

		IFile file = (IFile) ((IAdaptable) elem).getAdapter(IFile.class);
		if (file == null)
			return null;

		// Open an editor on that file.

		try {
			return IDE.openEditor(page, file);
		} catch (PartInitException e) {
			clog("Open editor failed: " + file.toString() + " -- " + e);
			return null;
		}
	}

	private INodeEditPart getNodeEditpart_nu(EcoreDiagramEditor diagramEditor,
			EModelElement me) {
		for (Object o : new ArrayList(diagramEditor.getDiagramGraphicalViewer()
				.getEditPartRegistry().values()))
			if (o instanceof INodeEditPart
					&& ((INodeEditPart) o).getModel() instanceof Node
					&& me == ((Node) ((INodeEditPart) o).getModel())
							.getElement())
				return (INodeEditPart) o;
		return null;
	}

	private void removeDiagraphAnnotation_nu(EClass claz, String view) {
		List<EAnnotation> toremove = new ArrayList<EAnnotation>();
		for (EAnnotation eAnnotation : claz.getEAnnotations())
			if (eAnnotation.getSource().equals(
					GenericConstants.ANNOT_DIAGRAPH_LITTERAL)
					&& isView(eAnnotation, view)) {
				toremove.add(eAnnotation);
				if (LOG)
					clog("removing annotation from "
							+ ((EClass) eAnnotation.getEModelElement())
									.getName() + " (view=" + view + ")");
			}
		EcoreDiagramEditor ecoreDiagramEditor = getEcoreDiagramEditor(claz
				.getEPackage().getName());
		for (Iterator<EAnnotation> iterator = toremove.iterator(); iterator
				.hasNext();)
			ecoreDiagramEditor
					.getEditingDomain()
					.getCommandStack()
					.execute(
							RemoveCommand.create(
									ecoreDiagramEditor.getEditingDomain(),
									iterator.next()));
	}

	/*----------------------------------- end  to clean -------------------*/

	@Override
	public boolean[] invokeDiagraphAction(String actionid) { // manual
		if (LOG)
			clog("IDA1 " + actionid);
		boolean[] result = new boolean[2];
		result[0] = false; // handled
		result[1] = true; // must refresh
		grammarGeneratorService.init();

		String langname = diagraphControler.getLanguageName(); // FP140611
		String pov = diagraphControler.getCurrentView(16, langname, this
				.getClass().getName());// getCurrentView();
		int povid = 0;// diagraphControler.getCurrentViewId__() //FP140606vvv

		EModelElement currentElement = getCurrentElement();
		if (currentElement instanceof ENamedElement) {
			if (LOG)
				clog("IDA current="
						+ ((ENamedElement) currentElement).getName());

		}
		// if (currentElement != null)
		boolean[] r1 = actionOnCurrentElement(NOT_EXEC, pov, actionid, null);// povid,annotationLevel_,
		boolean[] r2 = genericCommand(NOT_EXEC, actionid);
		// if ((r1[0]&&r1[1]) || (r2[0]&&r2[1])) //handled && must refresh
		// diagraphControler.layoutSaveAndRefresh(null);
		result[0] = r1[0] || r2[0]; // default = failed ; result = one did not
									// fail
		result[1] = r1[1] && r2[1]; // default = must refresh ; result = all doe
									// refresh
		// if ((result[0]&&result[1])) //handled && must refresh
		// diagraphControler.layoutSaveAndRefresh(null);
		return result;
	}

	@Override
	public String getLastView(String diagraphactions) {
		boolean[] result = new boolean[2];
		result[0] = false;
		result[1] = false;
		String[] rows = diagraphactions.split("\\|");
		String viewArg = ViewConstants.DIAGRAPH_DEFAULT;
		try {
			for (String row : rows) {
				String[] args = row.split(";");
				if (args.length > 2) {
					if (LOG)
						clog("EDIACT " + row);
					viewArg = args[1];
					if (viewArg.startsWith("view="))
						viewArg = viewArg.substring(5);

				}
			}
		} catch (Exception e) {
			System.err.println("GLV " + e.toString());
		}
		return viewArg;
	}

	@Override
	public boolean[] executeDiagraphActions(String diagraphactions) {
		boolean[] result = new boolean[2];
		result[0] = false;
		result[1] = false;
		String[] rows = diagraphactions.split("\\|");
		try {
			for (String row : rows) {
				String[] args = row.split(";");
				if (args.length > 2) {
					if (LOG)
						clog("EDIACT " + row);
					String viewArg = args[1];
					if (viewArg.startsWith("view="))
						viewArg = viewArg.substring(5);
					boolean[] res = invokeDiagraphAction(args[0], viewArg,
							args[2]);
					if (res[0])
						result[0] = true; // at least one action was handled
					else
						System.err.println("EDAC failed " + row);
					if (res[1])
						result[1] = true; // at least one action asks to refresh
				}
			}
		} catch (Exception e) {
			System.err.println("EDAC " + e.toString());
		}
		return result;
	}

	@Override
	public void redropDiagraphAnnotations(EPackage epackage) { // FP140501
		String view = diagraphControler.getRegisterdView(epackage.getName());// FP140611b
		if (LOG)
			clog((DParams.REDROP?" redrop ":" not redrop ")+"redropDiagraphAnnotations for " + view + "."
					+ epackage.getName());
		if (DParams.REDROP){
		  removeDiagraphAnnotationsFromDiagram(epackage, diagraphControler.getRegisterdView(epackage.getName()));
		  sleep(DParams.REDROP_DELAY); // FP140611aaa
		  dropDiagraphAnnotations(epackage, view);
		}
	}

	@Override
	public boolean addNewPovRoot(String frompov, String topov, int povid,
			EClass fromclaz, EClass toclaz) {
		return createPovAnnotation("new.view.add.pov", frompov, topov, // povid,
				fromclaz, toclaz, true);

	}

	@Override
	public EcoreDiagramEditor isDiagramOpen(String diagramName) {
		for (IEditorReference editorRef : PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage()
				.getEditorReferences()) {
			if (!"org.eclipse.emf.ecoretools.diagram.part.EcoreDiagramEditorID"
					.equals(editorRef.getId()))
				continue;
			EcoreDiagramEditor editor = (EcoreDiagramEditor) editorRef
					.getEditor(false);
			if (editor == null)
				continue;
			if (diagramName.equals(editorRef.getName()))
				return editor;
		}
		return null;
	}

	@Override
	public void openDiagram(EcoreDiagramEditor ecoreDiagramEditor,
			String language) { // FP131124
		try {
			String prefix = DiagraphPreferences.getTeamNamespace();
			IFile diag = getModel(prefix + "." + language, language
					+ ".ecorediag");
			if (diag != null) {
				ResourceSet resourceSet = new ResourceSetImpl();
				URI diagramModelURI = URI.createPlatformResourceURI(diag
						.getFullPath().toString(), false);
				final Resource diagramResource = resourceSet
						.createResource(diagramModelURI);
				EcoreDiagramEditorUtil.openDiagram(diagramResource);
			} else
			   diagraphControler.cerror("no diagram found for " + language
					+ ".ecorediag");
		} catch (Exception e) {
			// e.printStackTrace();
			// WARNING: Prevented recursive attempt to activate part
			// org.eclipse.emf.ecoretools.diagram.part.EcoreDiagramEditorID
			System.err.println("in openDiagram: " + e.toString());
			diagraphControler.cerror(" error in openDiagram: " + e.toString());
		}
	}

	@Override
	public void selectDsml(String name) {
		clog("dsml selected: " + name);
	}

	@Override
	public Object getArguments() {
		return arguments;
	}

	public DiagraphRuntimeFactory(IDiagraphControler diagraphControler) {
		super();
		this.diagraphControler = diagraphControler;
		this.grammarGeneratorService = (IGrammarGeneratorService) diagraphControler
				.getGrammarGeneratorService();
	}

	@Override
	public String addDetailToDiagraphAnnotation(EClass parent, String view,
			String key) {
		return grammarGeneratorService.addDetailToDiagraphAnnotation(parent,
				view, key);
	}

	@Override
	public IDiagraphUtils getDiagraphUtils() {
		return this;
	}

	@Override
	public IDiagraphControler getControler() {
		return diagraphControler;
	}

	@Override
	public EcoreDiagramEditor getEditor() {
		return diagraphControler.getEcoreDiagramEditor();
	}

	@Override
	public boolean checkConcreteSyntax() { // FP140501
		if (LOG)
			clog("checkConcreteSyntax");
		boolean wasDirty = false;
		for (String view : getAllViews())
			if (grammarGeneratorService.checkConcreteSyntax(view))
				wasDirty = true;
		return wasDirty;
	}
}
