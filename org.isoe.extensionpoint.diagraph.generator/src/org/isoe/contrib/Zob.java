package org.isoe.contrib;

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
import org.isoe.extensionpoint.diagraph.generator.IDiagraphGen;
import org.isoe.extensionpoint.ecoreutils.IEcoreUtils;



public class Zob implements IDiagraphGen {

	public Zob() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String[] parseViews(List<EAnnotation> allAnnotations) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMetamodelResourcepath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getGenLog() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getDiagraphRunnerDiagraphLog() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOwner(ViewPart viewpart) {
		// TODO Auto-generated method stub

	}

	@Override
	public void logBeforeExec(String log, List<String> keywordFilters) {
		// TODO Auto-generated method stub

	}

	@Override
	public void logAfterExec(String log, List<String> keywordFilters) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<String> getLog() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getLogDiagraph() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEcoreDiagramEditor(EcoreDiagramEditor ecoreDiagramEditor) {
		// TODO Auto-generated method stub

	}

	@Override
	public EPackage getPackage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCurrentView(int sender, String language, String message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRegisterdView(String language) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EcoreDiagramEditor getEcoreDiagramEditor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DiagramEditor findActiveGenericDiagramEditor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EcoreDiagramEditor findActiveEcoreEditor(String inviteToOpen) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEcoreUtilService(IEcoreUtils ecoreUtilService) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isFunctional() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isStub() {

		return true;
	}

	@Override
	public void setNotifiable(DiagraphNotifiable diagraphNotifiable) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setHeadless(boolean headless) {
		// TODO Auto-generated method stub

	}

	@Override
	public int adapt_nu(Diagram changedDiag, IWorkbenchPart part, String view,
			ISelection selection, boolean save, boolean old) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Diagram getCurrentDiagram() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getViews(boolean force) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getViews() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Diagram getWorkdiagram() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Diagram getSelectedDiagram() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void execDiagraphTransformation(String view, String resourcepath,
			boolean validate, boolean refreshArtifactsOnly,
			IProgressMonitor progressMonitor) {
		// TODO Auto-generated method stub

	}

	@Override
	public String generateDiagraphwold(String from, boolean forceGeneration,
			Object object, String view, boolean save, Diagram diag,
			boolean atRuntime, IProgressMonitor nullProgressMonitor,
			boolean rcp, boolean validate, boolean refactor,
			boolean refreshArtifactsOnly) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean runGenNotation(String view, boolean refactor,
			boolean headless, boolean refreshOnly) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DGraph selectDiagraphByView(EPackage pak, String view,
			Diagram diagram, boolean save, boolean atRuntime, boolean rcp,
			boolean validate, boolean headless, IProgressMonitor progressMonitor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DGraph> selectDiagraph(EPackage pak, Diagram diagram,
			boolean save, boolean atRuntime, boolean rcp, boolean validate,
			IProgressMonitor progressMonitor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DGraph> selectDiagraphNewParserTodo(EPackage pak,
			Diagram diagram, boolean save, boolean atRuntime, boolean rcp,
			boolean validate, IProgressMonitor progressMonitor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void cerror(String mesg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initView(EPackage packag, String view) {
		// TODO Auto-generated method stub

	}

	@Override
	public void creport(List<String> log) {
		// TODO Auto-generated method stub

	}

}
