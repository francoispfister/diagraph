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
package org.isoe.diagraph.gen.gmf.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gmf.codegen.gmfgen.GenChildNode;
import org.eclipse.gmf.codegen.gmfgen.GenDiagram;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.isoe.diagraph.common.IDiagraphInvoker;
import org.isoe.diagraph.common.IconCopier;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.diastyle.DStyle;
import org.isoe.diagraph.diastyle.helpers.IStyleHandler;
import org.isoe.diagraph.gmfgen.IGmfDiagramGen;
import org.isoe.diagraph.gmfgen.IGmfGeneratorInvoker;
import org.isoe.diagraph.internal.api.IDiaConcreteSyntax;
import org.isoe.diagraph.internal.api.IDiaPointOfView;
import org.isoe.diagraph.internal.api.IDiagraphParameters;
import org.isoe.diagraph.internal.m2.DiaConcreteSyntax;
import org.isoe.diagraph.internal.m2.DiaGraph;
import org.isoe.diagraph.internal.m2.DiaPointOfView;
import org.isoe.diagraph.internal.m2.parser.DAnnotation;
import org.isoe.diagraph.internal.m2.parser.Stats;
import org.isoe.diagraph.lang.DiagraphKeywords;
import org.isoe.diagraph.parser.api.IDiagraphNotation;
import org.isoe.diagraph.preferences.DiagraphPreferences;
//import org.isoe.diagraph.runner.IDiagraphControler;
import org.isoe.diagraph.runner.IDiagraphRunner;
import org.isoe.diagraph.templateaction.actions.ExecuteTemplateAction;
import org.isoe.diagraph.views.ViewConstants;
import org.isoe.extensionpoint.ecoreutils.IEcoreUtils;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.utils.DLog;
import org.isoe.fwk.utils.EmfValidator;
import org.isoe.fwk.utils.ResourceManager;
import org.isoe.fwk.utils.ResourceUtils;
import org.isoe.fwk.utils.debug.WorkspaceUtils;
import org.isoe.fwk.utils.eclipse.WorkbenchUtils;

/**
 *
 * @author pfister
 *
 */
public class DiagraphRunner implements IDiagraphRunner, IGmfDiagramGen {
	private static final boolean LOG = DParams.DiagraphRunner_LOG;
	private static final boolean EMF_VALIDATE = true; // DO not set to false !!!

	// private static final boolean LOG_LANGUAGE = true;

	private static final String ERROR_ = "!!!!Error!!!! ";
	private static final String DIAGRAM_EXT = ".diagram";

	private IGmfGeneratorInvoker gmfGeneratorInvoker;
	private IDiagraphInvoker diagraphInvoker;
	private List<GenDiagram> diagrams;
	List<String> logs;
	protected List<String> logDiagraph;

	@Override
	public void logStatements(String view, String logstatements) {
		diagraphInvoker.logStatements(view, logstatements);
	}

	@Override
	public void clearIdGenerator() {
		diagraphInvoker.clearIdGenerator(); // FP131205r
	}

	@Override
	public int getNextNodeId_(String name) {
		return diagraphInvoker.getNextNodeId(name);
	}

	@Override
	public int getNextEdgeId_(String name) {
		return diagraphInvoker.getNextEdgeId(name);
	}

	public void setEcoreUtils(IEcoreUtils ecoreUtils) {
		this.ecoreUtils_ = ecoreUtils;

	}

	@Override
	public IDiaConcreteSyntax getDsl() {
		return dsl;
	}

	private IDiaConcreteSyntax dsl;
	private IDiagraphParameters runParameters;
	private DiagramGenerator diagen;
	private boolean optionsLogged;
	private List<String> projectsToUpdate;
	private DiaGraph internalM2; // FP120620
	private boolean save;
	private DGraph dgraph;
	private DStyle dstyle;
	private IStyleHandler styleHandler;
	private String parseError = "DR_parse_error";
	private int phase = -1;
	// private IDiagraphView grammar;

	private IEcoreUtils ecoreUtils_;
	private IDiagraphNotation notation;

	// FP120715
	public void log(String mesg) {
		if (diagraphInvoker != null)
			diagraphInvoker.log(mesg);
	}

	public DiagraphRunner(IDiagraphInvoker invoker, IDiagraphNotation notation,
			List<String> projectsToUpdate, boolean save) {
		diagraphInvoker = invoker;
		/*
		 * if (invoker.getClass().getName()
		 * .equals("org.isoe.diagraph.gui.popup.actions.Generate")) caze = 1;
		 */
		this.save = save;
		this.dsl = new DiaConcreteSyntax(); // FP140615
		this.projectsToUpdate = projectsToUpdate;
		this.gmfGeneratorInvoker = new GmfGeneratorInvoker(this);
		this.notation = notation;
	}

	public List<GenDiagram> getDiagrams() {
		return diagrams;
	}

	public void cerror(String mesg){
		diagraphInvoker.cerror(mesg);
	}
/*
	@Override
	public void creport(List<String> log) {
		diagraphInvoker.creport(log);
	}
*/

	/*
	 * public static String getFilePath() throws IOException { String className
	 * = DiagraphRunner.class.getName(); int index = className.lastIndexOf(".");
	 * String claz = index == -1 ? className : className.substring(index + 1) +
	 * ".class"; URL ur = DiagraphRunner.class.getResource(claz); String furl =
	 * ur.toString();
	 *
	 * if (furl.startsWith("bundleresource:/")) furl =
	 * furl.substring("bundleresource:/".length());
	 *
	 * if (furl.startsWith("file:/")) furl = furl.substring("file:/".length());
	 * if (EMFPlugin.IS_ECLIPSE_RUNNING) { URL url = FileLocator.toFileURL(ur);
	 * furl = FileLocator.toFileURL(url).getFile(); } furl = furl.substring(0,
	 * furl.indexOf(PLUGIN_ID) + PLUGIN_ID.length()) + "/"; File f = new
	 * File(furl); return f.toString(); }
	 */

	// @Override

	private String manageFoldersAndGenerateAllPointsOfViewPop(
			GenModel genmodel, boolean atRuntime, String layer,
			String projectName, String workspace, boolean deleteOldJavaSrc,
			boolean changeTargetFolder, boolean rcp, boolean validate,
			IProgressMonitor progressMonitor) {
		progressMonitor.subTask("manageFoldersAndGenerateAllPointsOfView-2");
		progressMonitor.worked(1);
		String mesg = "";
		String cause = "";
		DLog.clearWarnings();
		String log = "";
		// final IViewPart navigator = WorkbenchUtils.showNavigator(); //
		// FP120522
		try {
			// log = doRun_(workspace, projectName, false, false);
			String ecoreLocation = ResourceManager.getEcoreLocation(workspace,
					DParams.MODEL_FOLDER,// FP120527
					projectName);
			if (LOG)
				clog("generating graphical editor for " + ecoreLocation);
			ResourceManager.initOperation(save);

			// DGraph dgraph=null; //FP120702
			boolean genFromDiagraph = true;
			log = manageFoldersAndGenerateAllPointsOfViewCommon(layer,
					atRuntime, genFromDiagraph, genmodel,
					projectName, // FP121015a
					ecoreLocation, deleteOldJavaSrc, changeTargetFolder, rcp,
					validate, false, progressMonitor); // //FP121031

			if (log.length() > 0)
				mesg += log; // FP2012
			if (diagrams != null)
				for (GenDiagram genDiagram : diagrams) { // FP120518*
					if (LOG) {
						clog(genDiagram.toString());
						EList<GenChildNode> chnodes = genDiagram
								.getChildNodes();
						for (GenChildNode genChildNode : chnodes) {
							clog(genChildNode.toString());
						}
					}
				}

		} catch (Exception e) {
			e.printStackTrace();
			String msg = "Error-!!!! " + e.getMessage();
			mesg += msg;
			clog(msg);
			if (e.getCause() != null && cause.length() > 0)
				cause += e.getCause().getMessage();
		}
		if (DLog.getWarnings().length() > 0
				&& !mesg.contains(DLog.getWarnings()))// FP2112
			mesg += DLog.getWarnings();
		return mesg;
	}

	/*
	 * @Override public String
	 * manageFoldersAndGenerateAllPointsOfViewCommon_(String layer, boolean
	 * atRuntime, GenModel genmodel_, String projectName, String
	 * absoluteLocation, boolean deleteOldJavaSrc, boolean changeTargetFolder,
	 * boolean rcp, boolean validate_, IProgressMonitor progressMonitor) { if
	 * (progressMonitor != null) { progressMonitor
	 * .subTask("manageFoldersAndGenerateAllPointsOfViewCommon");
	 * progressMonitor.worked(1); } return
	 * gmfGeneratorInvoker.manageFoldersAndGenerateAllPointsOfViewCommon(layer,
	 * atRuntime, genmodel_, projectName, absoluteLocation, deleteOldJavaSrc,
	 * changeTargetFolder, rcp, validate_, validate_, progressMonitor, logs_); }
	 */

	@Override
	public String manageFoldersAndGenerateAllPointsOfViewCommon(String layer,
			boolean atRuntime, boolean genFromDiagraph, GenModel genmodel_,
			String projectName, String absoluteLocation,
			boolean deleteOldJavaSrc, boolean changeTargetFolder, boolean rcp,
			boolean validate_, boolean refreshArtifactsOnly,
			IProgressMonitor progressMonitor) {
		return gmfGeneratorInvoker
				.manageFoldersAndGenerateAllPointsOfViewCommon(layer,
						atRuntime, genFromDiagraph, genmodel_, projectName,
						absoluteLocation, deleteOldJavaSrc, changeTargetFolder,
						rcp, validate_, save, refreshArtifactsOnly,
						progressMonitor);// , logs_);

	}

	@Override
	public void progress(IProgressMonitor progressMonitor, String label) {
		// idle();
		if (progressMonitor == null)
			return;
		if (progressMonitor.isCanceled()) {
			throw new OperationCanceledException("Operation Canceled");
		}
		progressMonitor.subTask(label);
		progressMonitor.worked(1);
		if (LOG)
			clog(label);
	}

	@Override
	public void generateDiagram(final IProject project, final IFile gmfgenFile,
			IProgressMonitor progressMonitor) {

		if (LOG) {
			String loc = gmfgenFile.getLocation().toString();
			loc = loc.substring(loc.lastIndexOf("/") + 1);

			clog("Generating Diagram for project:" + project.getName() + " :"
					+ loc);
		}

		if (progressMonitor != null) {
			progressMonitor.subTask("diagram generation with "
					+ gmfgenFile.getLocation().toString());
			progressMonitor.worked(1);
		}
		StructuredSelection selection = new StructuredSelection(project); // FP120523
		// ((ISetSelectionTarget) navigator).selectReveal(selection); //FP120529

		final IActionDelegate delegate = new IActionDelegate() { // FP140304

			// // FP120522b was opaque...
			public void run_old(IAction action) {
				IObjectActionDelegate actionDelegate = WorkbenchUtils
						.getAction("org.eclipse.ui.popupMenus",
								"gmf.codegen.ui.executeTemplatesAction");
				if (actionDelegate != null) { // FP131124
					actionDelegate.selectionChanged(action,
							new StructuredSelection(gmfgenFile));
					// IViewPart navigator = WorkbenchUtils.showNavigator();
					actionDelegate.setActivePart(action,
							WorkbenchUtils.showNavigator());// FP130108 //
															// FP120522b
					try {
						actionDelegate.run(new Action() {
						});// FP120522b // FP120510
					} catch (Exception e) {
						clog("???? generateDiagram ????");
					}

				}
			}

			// FP130304 clean now
			@Override
			public void run(IAction action) {
				WorkbenchUtils.showNavigator();
				ExecuteTemplateAction templateAction = new ExecuteTemplateAction();
				templateAction.setGmFile(gmfgenFile);
				templateAction.run(null);
			}

			public void selectionChanged(IAction action, ISelection selection) {
			}
		};
		delegate.run(null);
	}

	private boolean copy(String sourcePath, String targetPath) {
		try {
			InputStream in = new FileInputStream(new File(sourcePath));
			OutputStream out = new FileOutputStream(new File(targetPath));
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0)
				out.write(buf, 0, len);
			in.close();
			out.close();
			return true;
		} catch (IOException e) {
			clog("(1) error while copying " + sourcePath + " to " + targetPath);
			return false;
		}
	}

	public void copy(String srcplugin, String sourcefile, String targetplugin,
			String targetfile, boolean replace) {
		if (sourcefile.startsWith("/"))
			sourcefile = sourcefile.substring(1);
		if (targetfile.startsWith("/"))
			targetfile = targetfile.substring(1);
		String sr = srcplugin + "/" + sourcefile;
		String tg = targetplugin + "/" + targetfile;
		if (sr.equals(tg)) {
			if (LOG) {
				clog("idem: " + sr + " to " + tg);
			}
			return;
		}
		if (LOG) {
			clog("copy: " + sr + " to " + tg);
		}
		File targetAbsoluteFile = new File(CommonPlugin.resolve(
				URI.createPlatformResourceURI(targetplugin + "/" + targetfile,
						true)).toFileString());

		if (targetAbsoluteFile.exists() && !replace)
			return;
		File sourceAbsoluteFile = new File(CommonPlugin.resolve(
				URI.createPlatformResourceURI(srcplugin + "/" + sourcefile,
						true)).toFileString());
		String targpath = targetAbsoluteFile.getAbsolutePath();
		String path = targpath;
		String pref = path.substring(0, path.lastIndexOf(File.separator));
		File directory = new File(pref);
		if (!directory.exists())
			directory.mkdir();
		copy(sourceAbsoluteFile.getAbsolutePath(), targpath);
	}

	public boolean exists(String srcplugin, String sourcefile) {
		if (sourcefile.startsWith("/"))
			sourcefile = sourcefile.substring(1);
		File sourceAbsoluteFile = new File(CommonPlugin.resolve(
				URI.createPlatformResourceURI(srcplugin + "/" + sourcefile,
						true)).toFileString());
		return sourceAbsoluteFile.exists();
	}

	@Override
	public void exportIconsToEdit(String view, String layer) { // FP121110 //
																// FP121003
		boolean replace = true;
		List<IconCopier> tocopy = diagen.getIconsToCopy();
		for (IconCopier iconCopier : tocopy) {

			try { // FP131204zzz

				copy(iconCopier.getSrcPlugin(), iconCopier.getSrcPath(),
						iconCopier.getSrcPlugin(), iconCopier.getIconPath(),
						!replace);

				copy(iconCopier.getSrcPlugin(), iconCopier.getSrcPath(),
						iconCopier.getSrcPlugin(), iconCopier.getBackupPath(),
						!replace); // backup, preserve existing

				copy(iconCopier.getSrcPlugin(), iconCopier.getSrcPath(),
						iconCopier.getTargetPlugin(),
						iconCopier.getTargetPath(), replace);
				// copy to editplugin,includes preserved from the first
				// operation

				String targetDiagramPlugin_ = iconCopier.getTargetPlugin();
				targetDiagramPlugin_ = targetDiagramPlugin_.substring(0,
						targetDiagramPlugin_.lastIndexOf("."));// hem
				targetDiagramPlugin_ = targetDiagramPlugin_ + DIAGRAM_EXT
						+ ViewConstants.VIEW_SEPARATOR_0 + layer
						+ ViewConstants.VIEW_SEPARATOR_1
						+ ViewConstants.ROOT_NAME;
				String diagIconpath = iconCopier.getIconPath(); // /icons/Mode2Mode.gif
				diagIconpath = "/icons/canvas/" + diagIconpath.substring(7);

				copy(iconCopier.getSrcPlugin(), iconCopier.getSrcPath(),
						targetDiagramPlugin_, diagIconpath, !replace);
				// FP121112

			} catch (Exception e) {
				clog("(2) error while copying icons "
						+ iconCopier.getSrcPlugin() + " - "
						+ iconCopier.getSrcPath() + " - "
						+ iconCopier.getTargetPlugin() + " - "
						+ iconCopier.getTargetPath()); // FP131204

				// normal error
				// the
				// first
				// pass
			}
		}
	}

	/*
	 * @Override public void refresh(String projectName) { // FP121216
	 * org.isoe.fwk.utils.debug.WorkspaceUtils.getInstance().refreshProject(
	 * projectName, true); for (String relatedSuffix :
	 * ResourceManager.RELATED_PROJECT_SUFFIXES)
	 * WorkspaceUtils.getInstance().refreshIfExists( projectName +
	 * relatedSuffix, false);
	 *
	 *
	 *
	 * if (diagen.getPointsOfView() != null) for (IDiaPointOfView diaPointOfView
	 * : diagen.getPointsOfView()) { if (diaPointOfView.getName() != null) {
	 * String diagname = projectName + DIAGRAM_EXT +
	 * ViewConstants.VIEW_SEPARATOR_0 + diaPointOfView.getName(); //
	 * ".diagram_"+ if (LOG) clog("refreshing project " + diagname);
	 * WorkspaceUtils.getInstance().refreshIfExists(diagname, false); } } }
	 */

	@Override
	public void refresh(String projectName) { // FP121216

		WorkspaceUtils.getInstance().refreshProject(projectName, true);

		for (String relatedSuffix : ResourceManager.RELATED_PROJECT_SUFFIXES)
			WorkspaceUtils.getInstance().refreshIfExists(
					projectName + relatedSuffix, false);

		WorkspaceUtils.getInstance().refreshIfExists(
				projectName + DIAGRAM_EXT + ViewConstants.VIEW_SEPARATOR_0
						+ diagen.getPointOfView().getName(), false); // FP160617voir
																		// ok
	}

	public void logBeforeExec(String metamodel, List<String> keywordFilters) {
		List<String> logList = new ArrayList<String>();
		Stats.clearKeyStats();
		logs = new ArrayList<String>();
		logDiagraph = logList; // FP120522 todo: log condition in preferences
		if (!optionsLogged) {
			optionsLogged = true;
			DiagraphPreferences.logOptions(logDiagraph);
		}
		if (metamodel == null) {
			if (LOG)
				clog("building the whole megamodel");
			logDiagraph.add("building the whole megamodel");
		} else {
			if (LOG)
				clog("building the metamodel: " + metamodel);
			logDiagraph.add("building the metamodel" + metamodel);
		}

		String[] vt = DiagraphKeywords.VALID_TOKENS;
		logDiagraph.add("Diagraph valid tokens{");
		for (String token : vt) {
			logDiagraph.add("     " + token);
			keywordFilters.add(token);
		}
		logDiagraph.add("}");

		logDiagraph.add("");
		logDiagraph.add("Diagraph tokens arguments{");
		// logDiagraph.add("     " + DiagraphKeywords.FIGURE_EMBEDDED_ARGUMENT);
		// logDiagraph.add("     " + DiagraphKeywords.FIGURE_OWN_ARGUMENT);
		logDiagraph.add("     " + DiagraphKeywords.CONTAINMENT_HOST_NU);
		// logDiagraph.add("     " + Constants.COMPARTMENT_FIGURE_EMBEDDED_);
		// logDiagraph.add("     " + Constants.COMPARTMENT_FIGURE_OWN_);
		logDiagraph.add("}");

		String[] stylTokens = DiagraphKeywords.DIASTYLE_TOKENS;
		logDiagraph.add("Diagraph style tokens{");
		for (String stoken : stylTokens) {
			logDiagraph.add("     " + stoken);
			keywordFilters.add(stoken);
		}
		logDiagraph.add("}");

		// keywordFilters.add(DiagraphKeywords.FIGURE_EMBEDDED_ARGUMENT);
		// keywordFilters.add(DiagraphKeywords.FIGURE_OWN_ARGUMENT);
		keywordFilters.add(DiagraphKeywords.CONTAINMENT_HOST_NU);
		// keywordFilters.add(Constants.COMPARTMENT_FIGURE_EMBEDDED_);
		// keywordFilters.add(Constants.COMPARTMENT_FIGURE_OWN_);
		// return logs_;
	}

	public void logAfterExec(String runlog, List<String> keywordFilters) {
		logDiagraph.add("--------------- cross references ---------------");
		logDiagraph.addAll(Stats.logStats(keywordFilters));
		logDiagraph.add("------------------------------------------------");
		logDiagraph.add(runlog);
		// logDiagraph.add("------------------------------------------------");
		// if (!LOG_LANGUAGE_)
		// logDiagraph = new ArrayList<String>();
		// else
		logDiagraph.add("------------------log language-------------------");
		if (logs != null && !logs.isEmpty())
			logDiagraph.addAll(logs);
		// return logs_;
	}

	@Override
	public List<String> getLog() {
		return logs;
	}

	@Override
	public String execDiagraphTransformation(GenModel genmodel, String layer,
			boolean atRuntime, String plugin, boolean rcp,
			IProgressMonitor progressMonitor) {
		progressMonitor.subTask("execDiagraphTransformation");
		progressMonitor.worked(1);
		String log = "";
		boolean notDeleteOldJavaSrc = false;
		boolean notChangeTargetFolder = false;

		List<String> prjs = ResourceUtils.getOpenedProjects();
		if (DParams.LOG_TESTABLES) {
			clog("{");
			for (String prj : prjs)
				if (isMegamodelProject(prj))
					clog("\"" + prj + "\"" + ",");
			clog("};\n");
		}
		for (String prj : prjs) {
			boolean go = false;
			if (plugin == null)
				go = isMegamodelProject(prj) && mustUpdate(prj);
			else
				go = prj.equals(plugin);
			if (go)
				log = manageFoldersAndGenerateAllPointsOfViewAndLogPop(
						genmodel, atRuntime, layer, prj, notDeleteOldJavaSrc,
						notChangeTargetFolder, rcp, progressMonitor);// FP121031
		}

		clog(DAnnotation.logparse); // FP120123
		return log;
	}

	private String manageFoldersAndGenerateAllPointsOfViewAndLogPop(
			GenModel genmodel, boolean atRuntime, String layer, String prj,
			boolean deleteOldJavaSrc, boolean changeTargetFolder, boolean rcp,
			IProgressMonitor progressMonitor) {
		progressMonitor.subTask("manageFoldersAndGenerateAllPointsOfView");
		progressMonitor.worked(1);
		// boolean dirty = true;
		String log = manageFoldersAndGenerateAllPointsOfViewPop(genmodel,
				atRuntime, layer, prj, ResourceUtils.getWorkspaceDirectory(),
				deleteOldJavaSrc, changeTargetFolder, rcp, false,
				progressMonitor);
		if (log.length() < 2) { // because \n
			log = "GMF Generation OK for " + prj;
			clog(log);
		} else {
			logs.add(log);
			clog("GMF Generation with messages for " + prj + " :" + log);
		}
		return log;
	}

	private boolean mustUpdate(String prj) {
		if (projectsToUpdate == null)
			return true;
		for (String project : projectsToUpdate)
			if (project.equals(prj))
				return true;
		return false;
	}

	private boolean isMegamodelProject(String name) {
		boolean result = (name.startsWith(DiagraphPreferences
				.getTeamNamespace() + ".") || name.equals(DParams.DGRF_PATH)) // FP120314
																				// dogfooding
																				// now
				&& !ResourceManager.endsWithRelatedSuffix(name)
				&& !name.endsWith(".model");
		result = result && !name.contains(".diagram_");
		String initialstate = DiagraphPreferences.getConnectState();

		if (!initialstate.equals(DParams.CONNECTED_TO_REMOTE_REPOSITORY))
			clog("project" + " " + name
					+ " is not connected to a remote repository");
		return result;
	}

	public List<String> getLogDiagraph() {
		if (logDiagraph == null)
			logDiagraph = new ArrayList<String>();
		return logDiagraph;
	}

	@Override
	public int getPhase() {
		return phase;
	}

	public void setPhase(int phase) {
		this.phase = phase;
		if (LOG)
			clog("************** phase " + phase + " *****************");
	}

	public EPackage getDiagraphMetaModel_(ResourceSet resourceSet, URI uri) { // FP130828
		if (LOG)
			clog("---------------          getAdditionalMetaModel() ");
		// ResourceSet resourceSet = new ResourceSetImpl();
		if (uri == null)
			uri = URI.createURI("http://isoe-2012-diagraph-dsmlv4");
		EPackage diagraphMModel = (EPackage) ResourceUtils
				.loadMetamodel(resourceSet, uri).getContents().get(0);
		if (LOG)
			clog(diagraphMModel.getName() + " found");
		return diagraphMModel;
	}

	/*
	 * caller: layer, atRuntime, genmodel, resourceData, deleteOldJavaSrc, rcp,
	 * validate, changeTargetFolder, progressMonitor
	 *
	 * called: private List<GenDiagram> generateAllDiagrams__( String layer,
	 * boolean atRuntime, GenModel genmodel, String[] resourceInfo, boolean
	 * deleteOldJavaSrc_, boolean changeTargetFolder, boolean rcp,boolean
	 * validate_,IProgressMonitor progressMonitor)
	 */
	private List<GenDiagram> generateAllDiagrams(String layer,
			boolean atRuntime, GenModel genmodel_, boolean genFromDiagraph,
			String[] resourceInfo, boolean deleteOldJavaSrc, boolean rcp,
			boolean validate_, boolean changeTargetFolder,
			boolean refreshArtifactsOnly, IProgressMonitor progressMonitor) { // FPsub
		if (progressMonitor != null) {
			progressMonitor.subTask("generateAllDiagrams");
			progressMonitor.worked(1);
		}
		setPhase(1);
		getLogDiagraph().add("");
		logDiagraph.add("model = " + resourceInfo[ResourceManager.RSRC_MODEL]
				+ ".ecore" + "{");
		diagen = new DiagramGenerator(this, dsl, notation, resourceInfo);
		EPackage pakag_ = diagen.getMetaModel();
		EPackage diagraphM2 = null;
		if (false) {
			if (pakag_.getName().equals("megamodel")) {
				ResourceSet rs = pakag_.eResource().getResourceSet();
				// URI
				// diagraphModelUri=URI.createPlatformResourceURI(resourceInfo[5]+"/model/megamodel.diagraph",
				// false);
				// et pas de proxy
				// E:\Apps\workspaces\ws1306\runtime-130828\org.isoe.diagraph.diagraph\model\diagraph.ecore
				URI diagraphMetaModelUri_ = URI.createPlatformResourceURI(
						"org.isoe.diagraph.diagraph/model/diagraph.ecore",
						false);
				URI ruri = CommonPlugin.resolve(diagraphMetaModelUri_); // FP130829a
				clog(ruri.toFileString());
				clog("loading diagraph.diagraph from "
						+ diagraphMetaModelUri_.toString());
				diagraphM2 = ResourceManager.getDiagraphMetaModel(rs,
						diagraphMetaModelUri_);// FP130829y
				ResourceSet resourceSet = new ResourceSetImpl();
				URI u = URI.createPlatformResourceURI(resourceInfo[5]
						+ "/model/megamodel.genmodel", false);
				Resource resou = resourceSet.getResource(u, true);
				genmodel_ = (GenModel) resou.getContents().get(0);
			}

			if (pakag_.getName().equals("diastyle")) {
				ResourceSet rs = pakag_.eResource().getResourceSet();
				// URI
				// diagraphModelUri=URI.createPlatformResourceURI(resourceInfo[5]+"/model/megamodel.diagraph",
				// false);
				// et pas de proxy
				// E:\Apps\workspaces\ws1306\runtime-130828\org.isoe.diagraph.diagraph\model\diagraph.ecore
				URI diagraphMetaModelUri_ = URI.createPlatformResourceURI(
						"org.isoe.diagraph.diagraph/model/diagraph.ecore",
						false);
				URI ruri = CommonPlugin.resolve(diagraphMetaModelUri_); // FP130829a
				clog(ruri.toFileString());
				clog("loading diagraph.diagraph from "
						+ diagraphMetaModelUri_.toString());
				diagraphM2 = ResourceManager.getDiagraphMetaModel(rs,
						diagraphMetaModelUri_);// FP130829y
				ResourceSet resourceSet = new ResourceSetImpl();
				URI u = URI.createPlatformResourceURI(resourceInfo[5]
						+ "/model/diatyle.genmodel", false);
				Resource resou = resourceSet.getResource(u, true);
				genmodel_ = (GenModel) resou.getContents().get(0);
			}
		}

		// boolean continiu = true; //FP130124xx //diagen.refactor_(layer, m2,
		// changeTargetFolder, logDiagraph, save);
		boolean continiu = diagen.refactor(layer, pakag_, changeTargetFolder,
				logDiagraph, save);
		setPhase(2);
		if (continiu) {
			if (ViewConstants.DIAGRAPH_DEFAULT.equals(layer)
					&& DParams.DYNAMIC_TEMPLATES && genFromDiagraph) { // FP130419

				if (LOG)
					clog("deleting old templates");
				ResourceManager.deleteOldXpandFiles(resourceInfo,
						DiagramGenerator.TEMPLATE_FOLDER_TARGET_);
			}// DiagraphTemplates.DIAGRAPH_TEMPLATES_TARGET);
				// //
				// FP120711
			// diagen.parseStyle3(m2); // FP120707
			diagen.generatePointOfView(layer, atRuntime, genmodel_, pakag_,
					diagraphM2, genFromDiagraph,

					rcp, validate_, logDiagraph, progressMonitor);// FP130828xx
																	// //
																	// getDiagraphMetaModel(),
		}
		logDiagraph.add("}");
		setPhase(3);
		if (ResourceManager.isVersionChanged()) {
			logDiagraph.add("version changed");
			return null;
		}
		IDiaPointOfView pointOfView = dsl.getPointOfView();// FP140615d

		// IDiaPointOfView pov_new = dsl.getPointOfView_new_(); //FP140615d

		if (pointOfView == null) {
			clog("Not a Diagraph item");
			return null;
		}
		if (save) {
			ResourceManager.logFile = "";

			String povSuffix = pointOfView.getSuffix();
			if (povSuffix.isEmpty())
				throw new RuntimeException(
						"should not happen in generateAllDiagrams-1 !!!");
			ResourceManager.openRelatedProjects(resourceInfo, povSuffix); // FP120401
			if (LOG)
				clog("openRelatedProjects for layer: " + layer + " && pov: "
						+ povSuffix);

			if (LOG)
				clog(ResourceManager.logFile); // FP121021
		}
		setPhase(4);
		ResourceManager.logFile = "";
		if (!refreshArtifactsOnly && genFromDiagraph && save
				&& deleteOldJavaSrc
				&& ViewConstants.DIAGRAPH_DEFAULT.equals(layer))// FP130725
																// &&
																// viewid
																// ==
																// 0//
																// FP121031www
			// the
			// first
			// time
			// only
			ResourceManager.deleteOldSrcFiles_(resourceInfo);

		if (!refreshArtifactsOnly && save && deleteOldJavaSrc) { // FP121031www
																	// the first
																	// time only
			logDiagraph.add("option = " + "deleteOldSources");
			String povSuffix = pointOfView.getSuffix();
			if (povSuffix.isEmpty())
				throw new RuntimeException(
						"should not happen in generateAllDiagrams-2 !!!");
			if (genFromDiagraph)
				ResourceManager.deleteOldGraphFiles(resourceInfo, povSuffix);
			if (LOG)
				clog("deleteOldGraphFiles for layer: " + layer + " && pov: "
						+ povSuffix);
			// }
			if (LOG)
				clog("------------------\n" + ResourceManager.logFile
						+ "------------------\n"); // FP121021
		}

		EList<Resource> rsrcs = diagen.getResourceSet().getResources();
		if (LOG)
			if (save) {
				clog("Resources in resourceSet:");
				for (Resource resource_ : rsrcs)
					clog(resource_.getURI().toString());
			}
		setPhase(5);
		if (!refreshArtifactsOnly && save && DParams.CHECK_SUM) {
			try {
				ResourceManager.check(resourceInfo, deleteOldJavaSrc);
			} catch (Exception e) {
				clog("checksum error !!!");
				DLog.addWarning("checksum error");
			}
		}
		List<GenDiagram> result = diagen.getDiagrams();
		setPhase(6);
		return result;
	}

	/**
	 * heavy hack.... TODO: specific exception hierarchy please
	 *
	 * @param e
	 * @param mesg
	 * @param layer
	 * @return
	 */
	@Override
	public String handleException(Exception e, String mesg, String layer) {

		String lay = " (in view " + layer + ") ";
		int caze = 0;
		if (e.getMessage() == null && e instanceof NullPointerException) {
			caze = 1;
			mesg += ERROR_ + "NullPointerException" + " in layer " + layer;
		} else if (e.getMessage() != null
				&& !e.getMessage().contains(" checksum ")) {
			caze = 2;
			if (!mesg.contains(e.getMessage()))
				mesg += ERROR_ + e.getMessage() + " in layer " + layer;
		}
		parseError = mesg;
		if (caze == 1 || caze == 2
				|| e.toString().contains("ConcurrentModificationException")) { // clean
																				// the
																				// hack
			if (mesg.contains("error parsing Diagraph style 3 - unknown")) {
				parseError = mesg.substring(mesg.lastIndexOf("unknown"));

				throw (new RuntimeException(lay + e.getMessage() == null ? mesg
						: (e.getMessage())));
			} else if (mesg.contains("annotation does not support arguments")) {
				parseError = mesg;
				throw (new RuntimeException(lay
						+ (e.getMessage() == null ? mesg : (e.getMessage()))));
			} else if (mesg.contains("must be the first annotation")) {
				parseError = mesg.substring(mesg.lastIndexOf(ERROR_)
						+ ERROR_.length());
				throw (new RuntimeException(lay
						+ (e.getMessage() == null ? mesg : (e.getMessage()))));
			} else if (mesg.contains("should have 2 arguments")) {
				parseError = "should have 2 arguments";
				throw (new RuntimeException(lay
						+ (e.getMessage() == null ? mesg : (e.getMessage()))));
			} else if (mesg.contains("NullPointerException")) {
				parseError = "NullPointerException";
				throw (new RuntimeException(lay
						+ (e.getMessage() == null ? mesg : (e.getMessage()))));
			} else if (mesg.contains("token is not valid")) {
				parseError = mesg
						.substring(mesg.indexOf("token is not valid") + 19); // FP120715
				throw (new RuntimeException(lay
						+ (e.getMessage() == null ? mesg : (e.getMessage()))));
			} else if (mesg.contains("no style found")) {
				parseError = mesg
						.substring(mesg.indexOf("no style found") + 15); // FP120717
				throw (new RuntimeException(lay
						+ (e.getMessage() == null ? mesg : (e.getMessage()))));
			} else if (mesg.contains("reference")
					&& mesg.contains("does not exist for")) {
				parseError = mesg.substring(mesg.lastIndexOf(ERROR_)
						+ ERROR_.length());
				throw (new RuntimeException(lay
						+ (e.getMessage() == null ? mesg : (e.getMessage()))));
			} else if (mesg.contains("no attribute found for")
					&& mesg.contains(" in ")) {
				parseError = mesg.substring(mesg.lastIndexOf(ERROR_)
						+ ERROR_.length());
				throw (new RuntimeException(lay
						+ (e.getMessage() == null ? mesg : (e.getMessage()))));
			} else if (mesg
					.contains("a point of view should have at least one contained Node")) {
				parseError = mesg.substring(mesg.lastIndexOf(ERROR_)
						+ ERROR_.length());
				throw (new RuntimeException(lay
						+ (e.getMessage() == null ? mesg : (e.getMessage()))));
			} else if (mesg.contains("bad annotation syntax")) {
				parseError = mesg.substring(mesg.lastIndexOf(ERROR_)
						+ ERROR_.length());
				throw (new RuntimeException(lay
						+ (e.getMessage() == null ? mesg : (e.getMessage()))));
			} else if (mesg
					.contains("parseElementAnnotations - token is not valid")) {
				parseError = mesg.substring(mesg.lastIndexOf(ERROR_)
						+ ERROR_.length());
				throw (new RuntimeException(lay
						+ (e.getMessage() == null ? mesg : (e.getMessage()))));
			} else if (mesg
					.contains("Error while gmfgen Generation Can't evaluate LET expression")) {
				parseError = mesg.substring(mesg.lastIndexOf(ERROR_)
						+ ERROR_.length());
				// FP140322
				throw (new RuntimeException(lay
						+ (e.getMessage() == null ? mesg : (e.getMessage()))));
			} else if (mesg
					.contains("checkDirectInheritance: Super Node is not tagged")) {
				parseError = mesg.substring(mesg.lastIndexOf(ERROR_)
						+ ERROR_.length());
				throw (new RuntimeException(lay
						+ (e.getMessage() == null ? mesg : (e.getMessage()))));
			} else if (mesg.contains("reference")
					&& mesg.contains(" should exist  in layer")) {
				parseError = mesg.substring(mesg.lastIndexOf(ERROR_)
						+ ERROR_.length());
				throw (new RuntimeException(lay
						+ (e.getMessage() == null ? mesg : (e.getMessage()))));
			} else if (mesg.contains("should not happen in")) {
				parseError = mesg.substring(mesg.lastIndexOf(ERROR_)
						+ ERROR_.length());
				throw (new RuntimeException(lay
						+ (e.getMessage() == null ? mesg : (e.getMessage()))));
			} else if (mesg.contains("multiple containments not allowed for")) {

				parseError = mesg.substring(mesg.lastIndexOf(ERROR_)
						+ ERROR_.length()); // FP130823
				// clog(parseError);//FP130823

				throw (new RuntimeException(lay
						+ (e.getMessage() == null ? mesg : (e.getMessage()))));
			} else {
				if (mesg.contains(ERROR_))
					parseError = mesg.substring(mesg.lastIndexOf(ERROR_)
							+ ERROR_.length());
				throw (new RuntimeException(lay
						+ (e.getMessage() == null ? mesg : (e.getMessage()))));
			}
		} else
			mesg += "Checksum error " + e.getMessage();
		return mesg;
	}

	@Override
	public String generateAllPointsOfView(String layer, boolean atRuntime,
			GenModel genmodel_, boolean genFromDiagraph, String[] resourceData,
			boolean deleteOldJavaSrc_, boolean changeTargetFolder, boolean rcp,
			boolean validate_, boolean refreshArtifactsOnly,
			IProgressMonitor progressMonitor) {
		// TODO: specific exception hierarchy
		if (progressMonitor != null) {
			progressMonitor.subTask("generateAllPointsOfView");
			progressMonitor.worked(1);
		}
		String mesg = "";
		DLog.clearWarnings();
		try {
			diagrams = generateAllDiagrams(layer, atRuntime, genmodel_,
					genFromDiagraph, resourceData, deleteOldJavaSrc_, rcp,
					validate_, changeTargetFolder, refreshArtifactsOnly,
					progressMonitor);
		} catch (Exception e) {


		    if (mustThrowExceptions())
				mesg = handleException(e, mesg, layer); // FP140109
			else
				cerror("error while generateAllPointsOfView "
						+ mesg + " " + " in view " + layer + " ("
						+ e.toString() + ")");
		}
		if (DLog.getWarnings().length() > 0)
			mesg += DLog.getWarnings();
		// if (APPEND_LOG) {
		mesg += "---- APPEND_LOG ----\n";
		for (String log : logDiagraph) {
			mesg += log + "\n";
			// }
		}
		return mesg;
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	@Override
	public void setRunParameters(Object parameters) { // FP130321
		this.runParameters = (IDiagraphParameters) parameters;
	}

	@Override
	public void setInternalModel(Object diaGraph) {// FP130321
		internalM2 = (DiaGraph) diaGraph;
	}

	@Override
	public boolean generatePlugins() {
		return save;
	}

	@Override
	public void setDGraph(DGraph dgraph) {
		this.dgraph = dgraph;// this=org.isoe.diagraph.generator.gmf.main.DiagraphRunner@11a637
								// //dgraph=org.isoe.diagraph.diagraph.impl.DGraphImpl@1e921a2
	}

	@Override
	public DGraph getDGraph() {
		return dgraph; // this=org.isoe.diagraph.generator.gmf.main.DiagraphRunner@b21be7
						// //dgraph=null
	}

	@Override
	public DStyle getDStyle() {
		return dstyle;
	}

	@Override
	public void setDStyle(DStyle dstyle) {
		this.dstyle = dstyle;
	}

	@Override
	public String getParseError() {
		return parseError;
	}

	@Override
	public void setParseError(String error) {
		parseError = error;
	}

	@Override
	public void setStyleHandler(IStyleHandler styleHandler) {
		this.styleHandler = styleHandler;

	}

	@Override
	public IStyleHandler getStyleHandler() {
		return styleHandler;
	}

	@Override
	public Object getCompartment(String nodename) {
		return internalM2.getCompartment(nodename);
	}

	@Override
	public boolean isListLayout(String eclassName) {
		return internalM2.isListLayout(eclassName); // FP120620
	}

	@Override
	public void execDiagraphTransformation(String diagraphDefaultViewLitteral,
			String string, boolean validate, boolean refreshArtifactsOnly,
			IProgressMonitor progressMonitor) {
		diagraphInvoker.execDiagraphTransformation(diagraphDefaultViewLitteral,
				string, validate, refreshArtifactsOnly, progressMonitor);
	}

	@Override
	public String getMetadata(String id) {
		return diagraphInvoker.getMetadata(id);
	}

	@Override
	public IEcoreUtils getEcoreUtils() {
		return ecoreUtils_;
	}

	@Override
	public boolean isGenError() {
		return diagrams == null || diagrams.isEmpty();
	}

	/*--------------------------------------*/

	private void validateEmf(IProject project, String layer,
			String[] resourceInfo, IProgressMonitor progressMonitor) {// FP121122
																		// //
																		// FP120523
																		// //
		// FP120516
		IFile genmodelfile_ = ResourceUtils.getFile(project,
				DParams.MODEL_FOLDER + "/"
						+ resourceInfo[ResourceManager.RSRC_MODEL], "genmodel");

		if (genmodelfile_ == null) {
			throw new RuntimeException("genmodel is null");
		}
		if (progressMonitor != null) {
			progressMonitor.subTask("validateEmf " + DParams.MODEL_FOLDER + "/"
					+ resourceInfo[ResourceManager.RSRC_MODEL]);
			progressMonitor.worked(1);
		}

		String genModelPath = genmodelfile_.getFullPath().toString(); // FP121122
																		// /aigle.idm.diagraph.tp4v/model/tp4v.genmodel

		EmfValidator
				.validate(URI.createPlatformResourceURI(genModelPath, true));
	}

	private void generateEmfAndGmfPlugins_nu(IProject project, String layer,
			String[] resourceInfo, IProgressMonitor progressMonitor) { // FP120523
		// FP120516
		IFile genmodelfile = ResourceUtils.getFile(project,
				DParams.MODEL_FOLDER + "/"
						+ resourceInfo[ResourceManager.RSRC_MODEL], "genmodel");

		if (genmodelfile == null) {
			if (LOG)
				clog("genmodelfile == null");
			return;
		}
		progress(progressMonitor, "generateEmfAndGmfPlugins "
				+ DParams.MODEL_FOLDER + "/"
				+ resourceInfo[ResourceManager.RSRC_MODEL]);

		String genModelPath = genmodelfile.getFullPath().toString(); // FP121122
																		// /aigle.idm.diagraph.tp4v/model/tp4v.genmodel

		try {
			EmfValidator.generate(URI.createPlatformResourceURI(genModelPath,
					true));
		} catch (Exception e) {
			String validationError = e.toString();
			clog(validationError);
			if (EMF_VALIDATE)
				throw new RuntimeException("Emf validation error :"
						+ validationError);
		}

		String gmfgenrootpath = DParams.MODEL_FOLDER + "/"
				+ resourceInfo[ResourceManager.RSRC_MODEL]
				+ ViewConstants.VIEW_SEPARATOR_0
				+ DiaPointOfView.getLayeredRootName(layer); // FP140216 //
															// FP121102
															// model/multv_default_root

		IFile gmfgenfileRoot = ResourceUtils.getFile(project, gmfgenrootpath,
				"gmfgen");// FP121018

		if (LOG)
			clog("generateDiagram - root "
					+ gmfgenfileRoot.getFullPath().toString());

		if (gmfgenfileRoot == null) { // root first
			// if (LOG_GEN || LOG)
			clog("no diagram generator " + DParams.MODEL_FOLDER + "/"
					+ resourceInfo[ResourceManager.RSRC_MODEL] + ".gmfgen");
			return;
		}
		generateDiagram(project, gmfgenfileRoot, progressMonitor); // root
																	// unique &&
																	// only

	}

	@Override
	public boolean mustThrowExceptions() {
		return diagraphInvoker.mustThrowExceptions();
	}

	@Override
	public void cinfo(String mesg) {
		diagraphInvoker.cinfo(mesg);
	}

	@Override
	public void cwarn(String mesg) {
		diagraphInvoker.cwarn(mesg);
	}

	@Override
	public boolean isLayouting() {
		return diagraphInvoker.isLayouting();
	}



}
