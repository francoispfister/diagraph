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
package org.isoe.diagraph.megamodelhelper.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.isoe.diagraph.megamodel.Megamodel;
import org.isoe.diagraph.megamodel.MegamodelPackage;
import org.isoe.diagraph.megamodelhelper.IMegaModelBuilder;
import org.isoe.diagraph.megamodelhelper.IMegaModelMan;
import org.isoe.diagraph.megamodelhelper.IMegamodelParser;
import org.isoe.extensionpoint.diagen.IDiagraphAlphabet;
import org.isoe.extensionpoint.diagen.IMegamodelService;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.deployer.MegamodelPersistence;
import org.isoe.fwk.pathes.PathPreferences;
import org.isoe.diagraph.workbench.api.DiagraphNotifiable;




/**
 *
 * @author fpfister
 *
 */
public class MegaModelBuilder implements IMegaModelBuilder {

	public static final boolean MBLOG = DParams.MegaModelBuilder_LOG
			|| DParams.SandboxView_8_LOG;

	public static final boolean MBCSVLOG = DParams.MegaModelBuilderCsv_LOG;
	public static final boolean JOB_LOG = DParams.MegaModelBuilderJOB_LOG;

	private static final boolean LOG_CSV = false;
	private static final boolean GEN_LANGUAGE = true; // FP140126

	private IMegamodelParser megamodelParser;
	private IMegamodelService megamodelService;
	private IMegaModelMan megamodelMan;
	// private boolean jobbing_;
	private String diagramCsv = "";
	private boolean diagramParsing;
	private Map<String, String> logs = new HashMap<String, String>();
	private String domain;

	private String from = "";
	private String to = "";
	private String scont = "";
	private String snod = "";
	private String slink = "";
	private boolean pov = false;
	private boolean nod = false;
	private boolean link = false;

	private void init() {
		pov = false;
		nod = false;
		link = false;
		from = "";
		to = "";
		scont = "";
		snod = "";
		slink = "";
	}

	public MegaModelBuilder(IMegamodelParser megamodelParser) {
		this.megamodelParser = megamodelParser;
		this.megamodelService = megamodelParser.getMegamodelService();
		this.megamodelMan = megamodelParser.getMegamodelMan();
	}

	private String[] setCurrentLanguageIfNot_(String statement,
			String currentLanguage, String[] args) { // FP140625
		String cmd = statement;
		if (statement.contains(" "))
			cmd = statement.split("\\s+")[0];
		String slang = currentLanguage;
		String tlang = currentLanguage;
		if (args.length == 2
				&& (statement.equals(cmd) || statement.startsWith(cmd + " "))) {
			tlang = args[1];
			if (!currentLanguage.equals(tlang) && !tlang.isEmpty()){
				if (DParams.merge_LOG)
					clogmerge("setCurrentLanguageIfNot :"+currentLanguage+"->"+tlang);
				megamodelParser.get(tlang);
			}
		}
		String[] result = new String[2];
		result[0] = slang;
		result[1] = tlang.isEmpty()?slang:tlang;
		return result;
	}

	private void clogmerge(String mesg) {
		if (DParams.merge_LOG)
			System.out.println(mesg);
	}

	
	//FP141227int
	
	@Override
	public String build(String[] arguments, String currentLanguage,
			String currentView, String option, int sender) {
		String result = "";
		String statements = "";
		for (String arg : arguments)
			statements += arg + " ";
		statements = statements.trim();

		if (MBLOG) {
			clog("(" + sender + ")build " + statements + " currentLanguage="
					+ currentLanguage + " opt=" + option);
		}
		if (statements.isEmpty()) {
			result = "no statement";
			megamodelService.getControler().cerror(result);
			return result;
		}
		diagramCsv = "";
		logs = new HashMap<String, String>();

		String[] done = null;
		String[] langs=setCurrentLanguageIfNot_(statements, currentLanguage,arguments);
		if (statements.startsWith(IDiagraphAlphabet.MEGAMODEL_NEW_PARSE_NU)) {
			done = newParse_nu(arguments, langs, currentView);
		} else if (statements.startsWith(IDiagraphAlphabet.MEGAMODEL_FIND)) {
			done = findAndImage(arguments, langs);
		} else if (statements.startsWith(IDiagraphAlphabet.MEGAMODEL_DIAGRAPH)) {
			done = generateDiagraph__(arguments, langs);
		} else if (statements.startsWith(IDiagraphAlphabet.MEGAMODEL_ARTIFACTS)) {
			done = generateArtifacts(arguments, langs);
		} else if (statements.startsWith(IDiagraphAlphabet.MEGAMODEL_ALL)) {
			done = generateMegamodelAll__(arguments, langs);
		} else if (statements
				.startsWith(IDiagraphAlphabet.MEGAMODEL_CHECK_STATUS)) {
			done = generateGrammarWithStatus(arguments, langs);
		} else if (statements
				.startsWith(IDiagraphAlphabet.MEGAMODEL_GEN_DSML)) {
			done = generateGrammar(arguments, langs);
			if (done.length == 0)
				result = "language " + arguments[1] + " does not exist";
		} else if (statements
				.startsWith(IDiagraphAlphabet.MEGAMODEL_GEN_ROLES)) {
			done = generateRoles(arguments, langs);
		}

		if (done == null || done.length == 0 && result.isEmpty())
			result = "nothing done";
		if (MBLOG)
			for (String lang : done) {
				clog("lang=" + lang);
			}

		// megamodelMan.saveUniverse_();
		if (LOG_CSV)
			logTrace();
		if (result.isEmpty())
			result = "ok";
		return result;
	}

	@Override
	public void saveRepository() { // FP140618
		megamodelMan.saveUniverse();
	}

	// FP140619voiraaa
	private String[] prepare(String[] arguments, String language,
			String operation) {
		String langz = language;
		if (arguments.length == 1 || arguments.length > 1
				&& (arguments[1] == null || arguments[1].isEmpty())) {
			if (MBLOG)
				clog("no language, using current");
			// langs_ = language;
		} else
			langz = arguments[1];
		if (MBLOG)
			clog("prepare " + operation + " ( " + langz + " )");
		return megamodelMan.prepare(langz, operation);
	}

	private void close(String[] arguments, String language, String operation, boolean save) {
		megamodelMan.close(language, operation, save);
	}

	private void saveAllEditors() { // FP131116
		IWorkbenchPage workbenchPage = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		if (workbenchPage != null)
			workbenchPage.saveAllEditors(false);
	}

	@Override
	public void csvTrace(String csv) {
		if (csv == null || csv.isEmpty())
			return;
		if (csv.equals("diagram;")) {
			diagramCsv = "";
			diagramParsing = true;
			domain = "";
			if (MBCSVLOG)
				clog("*******csvTrace   start");
		}
		if (diagramParsing) {
			if (csv.startsWith("package;")) {
				String[] s = csv.split(";");
				domain = s[1];
			}
			diagramCsv += csv + "]";
		}
		if (csv.equals("end;")) {
			diagramParsing = false;
			String d = logs.get(domain);
			if (d == null) {
				logs.put(domain, diagramCsv);
			} else if (MBCSVLOG)
				clog(domain + " allready registered");
			diagramCsv = "";
			if (MBCSVLOG)
				clog("*******csvTrace   end");
		}
	}

	private void logTrace() {
		if (MBLOG)
			clog("diagramCsv[");
		Set<String> k = logs.keySet();
		for (String key : k) {
			String l = logs.get(key);
			quickAndDirtyParse(l);
		}
		if (MBLOG)
			clog("]diagramCsv");
	}

	private void quickAndDirtyParse(String csv) {
		init();
		String[] lines = csv.split("]");
		for (String line : lines) {
			if (MBLOG)
				clog(line);
		}
		String[] buf;
		buf = lines[1].split(";");
		String diagramUri = buf[1];
		buf = lines[2].split(";");
		String metamodelUri = buf[1];
		buf = lines[3].split(";");
		String packag = buf[1];
		if (MBLOG)
			clog("diagramUri: " + diagramUri);
		if (MBLOG)
			clog("metamodelUri: " + metamodelUri);
		if (MBLOG)
			clog("packag: " + packag);

		// platform:/resource/ecmfa_2013.lang.simpleworld/model/simpleworld.ecore
		// platform:/resource/ecmfa_2013.lang.simpleworld/model/simpleworld_default_root.diagraph

		for (String lin : lines) {
			if (lin.startsWith("diagraph;") || lin.startsWith("end;")) {
				if (nod) {
					if (MBLOG)
						clog("node" + " " + snod + " " + (pov ? "pov" : ""));
				} else if (link) {
					if (MBLOG)
						clog("link" + " " + slink + " from " + from + " to "
								+ to + " cont " + scont);
				}
				init();
			}
			if (lin.startsWith("diagraph;") && lin.endsWith(";node")) {
				String[] l = lin.split(";");
				snod = l[1];
				nod = true;
			}
			if (lin.startsWith("diagraph;") && lin.endsWith(";link")) {
				String[] l = lin.split(";");
				slink = l[1];
				link = true;
			}
			if (lin.contains(";pov"))
				pov = true;
			if (lin.startsWith("eclass;")) {
				String[] l = lin.split(";");
				String claz = l[1];
			}
			if (lin.contains(";lsrc")) {
				String[] l = lin.split(";");
				String s = l[1];
				String[] ss = s.split("=");
				from = ss[1];
			}
			if (lin.contains(";ltrg")) {
				String[] l = lin.split(";");
				String s = l[1];
				String[] ss = s.split("=");
				to = ss[1];
			}
			if (lin.contains(";cont")) {
				String[] l = lin.split(";");
				String s = l[1];
				String[] ss = s.split("=");
				scont = ss[1];
			}
			if (lin.contains(";link"))
				link = true;
		}
	}

	private void consolidateMegamodel() { // FP130903
		int i = 1;
		try {

			URI megamodelUri_ = URI.createURI("platform:/plugin/"
					+ PathPreferences.getPreferences().getUniverseProjectName()
					+ "/"
					+ PathPreferences.getPreferences()
							.getModelInUniverseFolder() + "/"
					+ DParams.UNIVERSE_MODEL_ROOT_NAME + "."
					+ MegamodelPackage.eNAME);
			i = 2;
			URI modellUri_ = URI.createURI("platform:/resource/"
					+ PathPreferences.getPreferences()
							.getInstanceRepositoryLocation() + "/src/"
					+ DParams.UNIVERSE_MODEL_ROOT_NAME + "."
					+ MegamodelPackage.eNAME);
			i = 3;

			Megamodel mm_ = MegamodelPersistence.load(modellUri_,megamodelService.getControler());
			i = 4;
			MegamodelPersistence.save(megamodelUri_, mm_, true,megamodelService.getControler());
			i = 5;

			if (MBLOG)
				clog("consolidateMegamodel " + megamodelUri_ + " <-- "
						+ modellUri_);

		} catch (Exception e) {
			megamodelService.getControler().cerror("error in consolidateMegamodel :" + i);
		}

	}

	private void deployMegamodel_nu() { // FP130903
		URI megamodelUri = URI.createURI("platform:/plugin/"
				+ PathPreferences.getPreferences().getUniverseProjectName()
				+ "/"
				+ PathPreferences.getPreferences().getModelInUniverseFolder()
				+ "/" + DParams.UNIVERSE_MODEL_ROOT_NAME + "."
				+ MegamodelPackage.eNAME);
		URI modellUri = URI.createURI("platform:/resource/"
				+ PathPreferences.getPreferences()
						.getInstanceRepositoryLocation() + "/src/"
				+ DParams.UNIVERSE_MODEL_ROOT_NAME + "."
				+ MegamodelPackage.eNAME);
		Megamodel mm_ = MegamodelPersistence.load(megamodelUri,megamodelService.getControler());
		MegamodelPersistence.save(modellUri, mm_, true,megamodelService.getControler());
		if (MBLOG)
			clog("deployMegamodel " + megamodelUri + " --> " + modellUri);
	}

	@Override
	public void consolidate(URI repositorypathURI, String sourcePlugin,
			String sourceFolder, String language) {
		if (MBLOG)
			clog("consolidate " + repositorypathURI + ";" + sourcePlugin + ";"
					+ sourceFolder + ";" + language);
		URI ruri = CommonPlugin.resolve(repositorypathURI);
		if (ruri == null)
			megamodelService.getControler().cerror("error in consolidate, " + repositorypathURI
					+ " not exists");
		if (MBLOG)
			clog(ruri.toFileString()); // E:\Apps\workspaces\ws-integr-9a\_megamodel\src\repository\
										// -
										// E:\Apps\workspaces\ws-integr-9a\_megamodel\src\repository\megamodel.megamodel

		if (language.equals("megamodel")) { // FP131003
			consolidateMegamodel();
		}

	}

	private void clog(String mesg) {
		if (MBLOG || MBCSVLOG)
			System.out.println(mesg);
	}

	/*---------------------------------------------------*/

	protected List<IProject> getOpenedProjects() {
		List<IProject> prjs = new ArrayList<IProject>();
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot wsroot = workspace.getRoot();
		IProject[] projects = wsroot.getProjects();
		for (IProject project : projects)
			if (project.isOpen())
				prjs.add(project);
		return prjs;
	}

	protected IFile getModel(IProject project, String extension)
			throws CoreException {
		IFolder fo = project.getFolder(org.isoe.fwk.core.DParams.MODEL_FOLDER);
		if (fo != null) {
			File modelfoldr = new File(CommonPlugin.resolve(
					URI.createURI("platform:/resource/"
							+ fo.getFullPath().toPortableString()))
					.toFileString());
			if (modelfoldr.exists() && modelfoldr.isDirectory())
				for (File f : modelfoldr.listFiles())
					if (f.getName().endsWith(extension))
						return fo.getFile(f.getName());

		}
		return null;
	}

	protected List<IProject> getDsmlProjects() {
		List<IProject> dsmlProjects = new ArrayList<IProject>();
		try {
			for (IProject iProject : getOpenedProjects())
				if (getModel(iProject, "ecore") != null)
					dsmlProjects.add(iProject);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return dsmlProjects;
	}

	private boolean isJavaProject(final IProject project) {
		try {
			if (!project.isAccessible())
				return false;
			return project.getNature(JavaCore.NATURE_ID) != null;
		} catch (CoreException e) {
			megamodelService.getControler().cerror("error ISJP: "+e.toString());
			return false;
		}
	}

	private List<IProject> toHandle(String langs_) {
		List<IProject> selectedProjects = new ArrayList<IProject>();
		List<IProject> opendprjs = getDsmlProjects();
		if (langs_.endsWith("*")) {
			for (IProject prj : opendprjs)
				if (isJavaProject(prj))
					selectedProjects.add(prj);
		} else {
			String[] prjs = langs_.split(";");
			for (IProject prj : opendprjs)
				if (isJavaProject(prj))
					for (String prjct : prjs)
						if (prj.getName().endsWith("." + prjct))
							selectedProjects.add(prj);
		}
		return selectedProjects;

	}

	private String arg(String[] args) {
		String arg = "";
		for (int i = 0; i < args.length; i++) {
			arg += args[i];
			if (i < args.length - 1)
				arg += ";";
		}
		return arg;
	}

	private List<IProject> getDsmlProjects_nu(String name) {
		List<IProject> dsmlProjects = new ArrayList<IProject>();
		List<IProject> prjs = getDsmlProjects();
		if (name.isEmpty())
			return prjs;
		for (IProject iProject : prjs) {
			if (iProject.getName().startsWith(
					name.substring(0, name.length() - 1)))
				dsmlProjects.add(iProject);
		}
		return dsmlProjects;
	}

	@Override
	public void setup(EPackage pak) {
		if (MBLOG)
			clog("setup " + pak.getName() + " ("
					+ megamodelParser.getArtifact() + ")");

	}

	@Override
	public void done(String projectName) {
		if (MBLOG)
			clog("done " + projectName + " (" + megamodelParser.getArtifact()
					+ ")");
	}



	/**
	 * @param language
	 ********************************/

	private String[] findAndImage(String[] arguments, String[] languages) {
		String currentlanguage = languages[0];
		String givenlanguage = languages[1];
		String[] langz = prepare(arguments, givenlanguage,
				IDiagraphAlphabet.MEGAMODEL_FIND);
		if (langz.length == 0){
			close(arguments, givenlanguage, IDiagraphAlphabet.MEGAMODEL_FIND,false);
			return langz;
		}
		_3_exportImagesJob(langz);
		close(arguments, givenlanguage, IDiagraphAlphabet.MEGAMODEL_FIND,true);
		return langz;
	}

	//
	private String[] generateArtifacts(String[] arguments, String[] languages) {
		String currentlanguage = languages[0];
		String givenlanguage = languages[1];
		String[] langz = prepare(arguments, givenlanguage,
				IDiagraphAlphabet.MEGAMODEL_ARTIFACTS);
		if (langz.length == 0){
			close(arguments, givenlanguage, IDiagraphAlphabet.MEGAMODEL_ARTIFACTS,false);
			return langz;
		}
		_2_exportArtifactsJob();
		close(arguments, givenlanguage, IDiagraphAlphabet.MEGAMODEL_ARTIFACTS,true);
		return langz;
	}




	/*
	 * private String[] clone(String[] arguments, String currentLanguage, String
	 * cloneLanguage,boolean headless,int sender) { String[] langz =
	 * prepare(arguments, currentLanguage,
	 * IDiagraphAlphabet.MEGAMODEL_AFTER_CLONE_); if (langz.length == 0) return
	 * langz;
	 * megamodelParser.cloneLanguageAndGenGrammar(arguments,currentLanguage
	 * ,cloneLanguage,headless,++sender); close(arguments, currentLanguage,
	 * IDiagraphAlphabet.MEGAMODEL_AFTER_CLONE_); return langz; }
	 */

	private String[] generateGrammar(String[] arguments, String[] languages) {
		String currentlanguage = languages[0];
		String givenlanguage = languages[1];
		String[] langz = prepare(arguments, givenlanguage,
				IDiagraphAlphabet.MEGAMODEL_GEN_DSML);
		if (langz.length == 0){
			close(arguments, givenlanguage,
					IDiagraphAlphabet.MEGAMODEL_GEN_DSML,false);
			return langz;
		}
		_2_exportArtifactsJob();
		_3_exportImagesJob(langz);
		_4_weaverJob(langz);
		_6_grammarWithoutStatusJob();
		close(arguments, givenlanguage,
				IDiagraphAlphabet.MEGAMODEL_GEN_DSML,true);
		return langz;
	}


	private String[] generateGrammarWithStatus(String[] arguments,
			String[] languages) {
		String currentlanguage = languages[0];
		String givenlanguage = languages[1];
		String[] langz = prepare(arguments, givenlanguage,
				IDiagraphAlphabet.MEGAMODEL_CHECK_STATUS);
		if (langz.length == 0){
			close(arguments, givenlanguage,
					IDiagraphAlphabet.MEGAMODEL_CHECK_STATUS,false);
			return langz;
		}

		_2_exportArtifactsJob();
		_3_exportImagesJob(langz);
		_4_weaverJob(langz);
		_5_grammarWithStatusJob();
		close(arguments, givenlanguage,
				IDiagraphAlphabet.MEGAMODEL_CHECK_STATUS,true);
		return langz;
	}

	private String[] generateRoles(String[] arguments, String[] languages) {
		String currentlanguage = languages[0];
		String givenlanguage = languages[1];
		String[] langz = prepare(arguments, givenlanguage,
				IDiagraphAlphabet.MEGAMODEL_GEN_ROLES);
		if (langz.length == 0){
			close(arguments, givenlanguage, IDiagraphAlphabet.MEGAMODEL_GEN_ROLES,false);
			return langz;
		}
		_7_genEditorJob(langz);
		close(arguments, givenlanguage, IDiagraphAlphabet.MEGAMODEL_GEN_ROLES,true);
		return langz;
	}

	private String[] newParse_nu(String[] arguments, String[] languages,
			String currentView) {

		String currentlanguage = languages[0];
		String givenlanguage = languages[1];

		String[] langz = prepare(arguments, givenlanguage,
				IDiagraphAlphabet.MEGAMODEL_NEW_PARSE_NU);
		if (langz.length == 0){
			close(arguments, givenlanguage,
					IDiagraphAlphabet.MEGAMODEL_NEW_PARSE_NU,false);
			return langz;
		}
		megamodelParser.clearForm(givenlanguage, currentView);// FP140611b
		saveAllEditors();
		megamodelParser.newParseLanguage_nu();
		close(arguments, givenlanguage,
				IDiagraphAlphabet.MEGAMODEL_NEW_PARSE_NU,true);
		return langz;
	}

	private void jobclog(String mesg) {
		if (JOB_LOG)
			System.out.println(mesg);
	}









	private String[] generateMegamodelAll__(String[] arguments, String[] languages) {
		String currentlanguage = languages[0];
		String givenlanguage = languages[1];
		String[] langz = prepare(arguments, givenlanguage,
				IDiagraphAlphabet.MEGAMODEL_ALL);
		if (langz.length == 0){
			close(arguments, givenlanguage, IDiagraphAlphabet.MEGAMODEL_ALL,false);
			return langz;
		}
		_1_diagraphLanguagesJob(langz);
		_2_exportArtifactsJob();
		_3_exportImagesJob(langz);
		_4_weaverJob(langz);
		_5_grammarWithStatusJob();
		close(arguments, givenlanguage, IDiagraphAlphabet.MEGAMODEL_ALL,true);
		return langz;
		// if (GEN_LANGUAGE)
		// generateLanguage();
	}


	private String[] generateDiagraph__(String[] arguments, String[] languages) {
		String currentlanguage = languages[0];
		String givenlanguage = languages[1];
		String[] langz = prepare(arguments, givenlanguage,
				IDiagraphAlphabet.MEGAMODEL_DIAGRAPH);
		if (langz.length == 0){
			close(arguments, givenlanguage, IDiagraphAlphabet.MEGAMODEL_DIAGRAPH,false);
			return langz;
		}
		_a_diagraphLanguages(langz);
		close(arguments, givenlanguage, IDiagraphAlphabet.MEGAMODEL_DIAGRAPH,true);
		return langz;
	}




	/**********************************/

	private void _2_exportArtifactsJob() {
		if (JOB_LOG)
			jobclog("_2_exportArtifactsJob ");
		megamodelService.setMegaModelProject(ResourcesPlugin.getWorkspace()
				.getRoot().getProject("_megamodel"));
		megamodelService.setHeadless(true);
		megamodelService.run(IDiagraphAlphabet.MEGAMODEL_CMD_MODEL_WEAVER);
	}

	private void _5_grammarWithStatusJob() {
		// CompileMegamodelAction compileMegamodel = new
		// CompileMegamodelAction(this);
		if (JOB_LOG)
			jobclog("_5_grammarWithStatusJob ");
		megamodelService.setMegaModelProject(ResourcesPlugin.getWorkspace()
				.getRoot().getProject("_megamodel"));
		megamodelService.setLegended(true);
		megamodelService.setLineNumbered(true);
		megamodelService.setShowStatus(true);
		megamodelService.setHeadless(true);
		megamodelService.run(IDiagraphAlphabet.MEGAMODEL_CMD_GRAMMAR);
	}

	private void _6_grammarWithoutStatusJob() {
		if (JOB_LOG)
			jobclog("_6_grammarWithoutStatusJob ");
		megamodelService.setMegaModelProject(ResourcesPlugin.getWorkspace()
				.getRoot().getProject("_megamodel"));
		megamodelService.setLegended(true);
		megamodelService.setLineNumbered(true);
		megamodelService.setShowStatus(false);
		megamodelService.setHeadless(true);
		megamodelService.run(IDiagraphAlphabet.MEGAMODEL_CMD_GRAMMAR);
	}

	private void _7_genEditorJob(String[] langs) {
		String args = arg(langs);
		List<IProject> selectedProjects = toHandle(args);
		if (!selectedProjects.isEmpty()) {
			if (JOB_LOG)
				jobclog("_7_genEditorJob " + args);
			megamodelService.setSelection(selectedProjects);
			megamodelService.setMegaModelProject(ResourcesPlugin.getWorkspace()
					.getRoot().getProject("_megamodel"));
			megamodelService.setHeadless(true);
			megamodelService.run(IDiagraphAlphabet.MEGAMODEL_CMD_GEN_EDITOR);
		} else if (JOB_LOG)
			jobclog("applyDiagraphJob - no selected language");
	}

	private void _a_diagraphLanguages(String[] langz) {
		if (JOB_LOG)
			jobclog("_a_diagraphLanguages ");
		megamodelParser.diagraphLanguages(langz);
	}

	private void _1_diagraphLanguagesJob(String[] langs) {
		String args = arg(langs);
		List<IProject> selectedProjects = toHandle(args);
		if (!selectedProjects.isEmpty()) {
			if (JOB_LOG)
				jobclog("_1_diagraphLanguagesJob " + args);
			megamodelService.setSelection(selectedProjects);
			megamodelService.setHeadless(true);
			megamodelService.run(IDiagraphAlphabet.MEGAMODEL_CMD_LANGUAGES);
		} else if (JOB_LOG)
			jobclog("diagraphLanguagesJob - no selected language");
	}

	private void _4_weaverJob(String[] langs) {
		String args = arg(langs);
		List<IProject> selectedProjects = toHandle(args);
		if (!selectedProjects.isEmpty()) {
			// DiagraphWeaverAction ac = new DiagraphWeaverAction(this);
			if (JOB_LOG)
				jobclog("_4_weaverJob " + args);
			megamodelService.setSelection(selectedProjects);
			megamodelService.setMegaModelProject(ResourcesPlugin.getWorkspace()
					.getRoot().getProject("_megamodel"));
			megamodelService.setHeadless(true);
			megamodelService.run(IDiagraphAlphabet.MEGAMODEL_CMD_WEAVER_);
		} else if (JOB_LOG)
			jobclog("exportDiagraphJob - no selected language");
	}

	private void _3_exportImagesJob(String[] langs) {
		String args = arg(langs);
		List<IProject> selectedProjects = toHandle(args);
		if (!selectedProjects.isEmpty()) {
			if (JOB_LOG)
				jobclog("_3_exportImagesJob " + args);
			megamodelService.setHeadless(true);
			megamodelService.setSelection(selectedProjects);
			megamodelService.run(IDiagraphAlphabet.MEGAMODEL_CMD_EXPORT_IMAGE);
		} else if (JOB_LOG)
			jobclog("exportImagesJob - no selected language");
	}

}
