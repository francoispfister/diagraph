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
package org.isoe.diagraph.controler;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecoretools.legacy.diagram.part.EcoreDiagramEditor;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchPage;
//import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.megamodel.Dsml;
import org.isoe.diagraph.megamodel.Megamodel;
import org.isoe.diagraph.workbench.api.IDiagraphActionWiper;
//import org.isoe.diagraph.runner.IDiagraphRunner;
//import org.isoe.extensionpoint.diagraph.generator.IDiagraphGen;
//import org.isoe.extensionpoint.diagraph.DiagraphService;


/**
 *
 * @author fpfister
 *
 */
public interface IDiagraphControler {
//org.isoe.diagraph.controler.IDiagraphControler
	//org.isoe.extensionpoint.diagraph.action.IDiagraphControler


	static final String CMD_ARG_LWB = "lwb";
	static final String CMD_DEPLOY = "deploy";
	static final String CMD_DIAGRAPH = "diagraph";
	static final String CMD_REFRESH = "refresh";
	static final String CMD_REFRESH_LWB = CMD_REFRESH + " " + CMD_ARG_LWB;

	void checkLanguage();
	void clearErrorLog();

	void deployMegamodel_(boolean deployBuildInPlugin,
			boolean deployLocalPlugin, boolean deployLocalWorkspace,
			boolean all, String filter);
	void deployMegamodelClone_(boolean deployBuildInPlugin,
			boolean deployLocalPlugin, boolean deployLocalWorkspace,
			String languageToDeploy);
	void deployModels(String language, URI srcMegamodelFolder, List<String> instancesdeployed);
	void diagraphMegamodel_(boolean buildIn, boolean localPlugin, boolean localWorkspace, String languagesToDiagraph);

	void endMerge();
	void ends(String cmd);
	void execute(String cmd, int method, URI[] uri);
	void firstLayout();
	boolean focusDiagraph(EcoreDiagramEditor ecoreDiagramEditor, String apath,
			boolean diagramOpened);
	void generateAllDiagraphArtifacts(List<String> projsToCompile,
			Megamodel megamodel, boolean refreshArtifactsOnly,
			IProgressMonitor progressMonitor);
	void generateNewPov(EModelElement eModelElement, boolean auto);
	EClass getAddedPov();
	public List<String> getAllViews();
	String getChoice(int choice);
	String getCloneName(boolean allowRandom);
	String getCommandId();
	Diagram getCurrentDiagram();
	EModelElement getCurrentElement();
	EObject getCurrentEObject();
	IGraphicalEditPart getCurrentGraphicalEditPart();
	EPackage getCurrentPackage();
	EClass getCurrentPov();
	int getCurrentPovId();
	String getCurrentView(int sender, String language, String message);
	Diagram getDiagramToLayout();
	Object getDiagraphGenerator();
	StyledText getDiagraphResponseText();
	//IDiagraphRunner getDiagraphrunner();
	StyledText getDiagraphScriptText();
	String getDiagraphStatements();
	Display getDisplay();
	EClass getEClass(String eClassName);
	Diagram getEcoreDiagram();
	Diagram getEcoreDiagram(String name);
	EcoreDiagramEditor getEcoreDiagramEditor();
	EModelElement[] getElementStack();
	String getError();
	String getFocusedLanguage();
	DiagramEditor getGenericDiagramEditor();
	Object getGrammarGeneratorService();
	String getLanguageName();
	String getLanguageToMergeName();
	String getLayer();
	Megamodel getMegamodel(String name, boolean recreate);
	String getNewPov();
	int getNewPovId();
	String getPathUnderModel(String filename, String extension);
	String getPovCandidate(EModelElement eModelElement);
	String[] getDiagraphedProjectLocation(String language);
	String getRegisterdView(String language);
	Object getRuntimeFactory();
	EClass getSelectedClass();
	Shell getShell();
	int getViewId(String view);
	String getViewName();
	EcoreDiagramEditor isDiagramOpen(String apath);
	boolean isHeadless();
	boolean isLanguageConfiguration();
	boolean isModelConfiguration();
	boolean isUnderProject(EcoreDiagramEditor ecoreDiagramEditor);
	void layout(boolean redrop, boolean handleListeners);
	void layoutSaveAndRefresh();
	void log(int i, String msg);
	void log(String message);
	void log(String id, String message);
	void logLayout(Object layoutAction);
	void openDiagram(EcoreDiagramEditor ecoreDiagramEditor, String apath);
	void refreshLanguageRepositoryProject();
	void refreshLayoutAndSave(String view, boolean redrop,
			boolean handleListeners);
	void refreshNewPovAndLayoutAndSave();
	void refreshPathes(String absolutePath);
	void refreshProject(String proj);
	void registerCurrentView(String langName, String view, int sender);
	void registerService(Object diagraphService);
	void removeListeners_();
	void removeView(String view);
	void saveAllEditors();
	DGraph selectDiagraphByView(EPackage pak, String view, Diagram diagram,
			boolean save, boolean atRuntime, boolean rcp, boolean validate,
			boolean headless, NullProgressMonitor progressMonitor);
	void sendCommand(String cmdRefreshLwb);
	void setActiveEcoreDiagramEditor(EcoreDiagramEditor ecoreDiagramEditor);
	void setActiveEditor(IEditorPart editor);
	void setCmdResponse(String result);
	void setCurrent(int i, EModelElement modelElement, EClass eclaz, EditPart editPart, String name);
	void setCurrentEcoreDiagram(DiagramEditor part, Diagram diagram);
	boolean setCurrentLanguageDiagram();
	void setDiagraphStatements(String statement);
	void setEcoreDiagramEditor(EcoreDiagramEditor editPart);
	void setFocusedLanguage(String apath);
	void setKo(boolean value, String mesg);
	void setLastChoice(String choice);
	void setLayer(String view);
	void setLayerItems(String[] views);
	void setLayoutActionDelegate(IActionDelegate layoutAction);
	void setPointOfViewCandidates(List<String> povCandidates);
	void setViewText(String string);
	void showDiagraphLog();
	void showMessage(String message);
    void startMerge();
	void starts(String cmd);
	void startup(String basePath);
	void unknownEdge(Edge edg);
	void unknownModel(Object object);
	boolean update(IWorkbenchPart part, ISelection selection);
	void updateAnnotations();
	void updateColor(String language, String view);
	void wipeActions(IDiagraphActionWiper aActionWiper,IWorkbenchPage page );
	List<IProject> getDsmlProjects();
	void setOtherEditor(IEditorPart editor);
	void selectDsml(Dsml dsml);
	String[] getViews();
	boolean prepareClone();
	DGraph getCurrentDGraph();
	String getCurrentRootName();
	void finalizeClone(EPackage cloned);
	void finalizeMerge(EPackage p);
	DGraph getMergeDGraph();
	void refreshMegamodelProject();
	void bringToTop(EPackage packag);
	boolean prepareBuild(boolean headless);
	void clearMegamodel();
	List<String> getDiagraphProjectNames();
	void endBuild(boolean b);
	EPackage getClonedPackage();
	void setClonedPackage(EPackage ePackage);
	EPackage get(String language);
	EPackage clone(String cloneLanguage, boolean headless);
	Diagram focusDiagram(String language);
	boolean isMerging();
	EClass getCurrentRoot();
	boolean prepareMerge(boolean byscript, EClass currentRoot,
			EPackage withPackage);
	void setMergePackage(EPackage mergePackage);
	void setMergeDiagram(Diagram mergeDiagram);
	void setSourcePackage(EPackage sourcePackage);
	void setSourceDiagram(Diagram sourceDiagram);
	EPackage getSourcePackage();
	Diagram getSourceDiagram();
	EPackage getMergePackage();
	Diagram getMergeDiagram();
	void setSourceDGraph(DGraph sourceDGraph);
	void setMergeDGraph(DGraph mergeDGraph);
	EPackage merge(String cloneLanguage, DGraph sourceDGraph,
			DGraph mergeDGraph, boolean headless, boolean byscript);
	boolean mustThrowExceptions();
	void logConsole(String mesg);
	void cerror(String mesg);
	void cinfo(String mesg);
	void cwarn(String mesg);
	void focusOther(IProject project);
	//boolean focusNotDiagraphed(EcoreDiagramEditor diagramEditor,
	//		String language, boolean diagramOpened);
	boolean focusNotDiagraphed(EcoreDiagramEditor diagramEditor,
			IProject project, String language, boolean diagramOpened);
	void layoutError(String mesg);
	void perspectiveChanged(IPerspectiveDescriptor perspective, String perspectiveId, String changeId);
	void earlyStartup();
	List<String[]> getAllDiagraphedProjectLocations();
	void retryLayout();
	void refresh(int delay);
	void setLayouting(boolean value);
	boolean[] invokeDiagraphAction(String actionid);
	String invokeMegamodelJob(String language, boolean headless, String[] arguments, boolean genLanguage, boolean refreshOnly);
	String invokeCopyJob(boolean headless, String[] args, String folder);
	void setCurrentResource(Resource eResource);
	Resource getCurrentResource();
	void restoreListeners(boolean raz);


}
