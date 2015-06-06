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
package org.isoe.extensionpoint.diagraph.generator;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecoretools.legacy.diagram.part.EcoreDiagramEditor;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.workbench.api.DiagraphNotifiable;
import org.isoe.extensionpoint.ecoreutils.IEcoreUtils;



/**
 *
 * @author fpfister
 *
 */
public interface IDiagraphGen {//FP130926x depl //FP130725x //does not extends DiagraphService
//org.isoe.extensionpoint.diagraph.generator.IDiagraphGen
//org.isoe.extensionpoint.diagraph.generator.IDiagraphGen
	String[] parseViews(List<EAnnotation> allAnnotations);
	String getMetamodelResourcepath();
	String getGenLog();
	List<String> getDiagraphRunnerDiagraphLog();
	void setOwner(ViewPart viewpart);
	void logBeforeExec(String log, List<String> keywordFilters);
	void logAfterExec(String log, List<String> keywordFilters);
	List<String> getLog();
	List<String> getLogDiagraph();
	void setEcoreDiagramEditor(EcoreDiagramEditor ecoreDiagramEditor);
	EPackage getPackage();
	String getCurrentView(int sender, String language, String message);
	String getRegisterdView(String language);
	EcoreDiagramEditor getEcoreDiagramEditor();
	DiagramEditor findActiveGenericDiagramEditor();
	EcoreDiagramEditor findActiveEcoreEditor(String inviteToOpen);
    void setEcoreUtilService(IEcoreUtils ecoreUtilService);
    boolean isFunctional();
    boolean isStub();
    void setNotifiable(DiagraphNotifiable diagraphNotifiable);
	void setHeadless(boolean headless);
	int adapt_nu(Diagram changedDiag, IWorkbenchPart part, String view,
			ISelection selection, boolean save, boolean old);
	Diagram getCurrentDiagram();
	String[] getViews(boolean force);
	String[] getViews();
	Diagram getWorkdiagram();
	Diagram getSelectedDiagram();
	void execDiagraphTransformation(String view, String resourcepath,
			boolean validate, boolean refreshArtifactsOnly,
			IProgressMonitor progressMonitor);

	String generateDiagraphwold(String from, boolean forceGeneration,
			Object object, String view, boolean save, Diagram diag,
			boolean atRuntime, IProgressMonitor nullProgressMonitor,
			boolean rcp, boolean validate, boolean refactor,
			boolean refreshArtifactsOnly);

	boolean runGenNotation(String view, boolean refactor, boolean headless,
			boolean refreshOnly);

	DGraph selectDiagraphByView(EPackage pak, String view, Diagram diagram,
			boolean save, boolean atRuntime, boolean rcp, boolean validate,boolean headless,
			IProgressMonitor progressMonitor);

	List<DGraph> selectDiagraph(EPackage pak, Diagram diagram,
			boolean save, boolean atRuntime, boolean rcp, boolean validate,
			IProgressMonitor progressMonitor);

	List<DGraph> selectDiagraphNewParserTodo(EPackage pak, Diagram diagram,
			boolean save, boolean atRuntime, boolean rcp, boolean validate,
			IProgressMonitor progressMonitor);

	void cerror(String mesg);

	void initialize();
	void initView(EPackage packag, String view);
	void creport(List<String> log);



}

