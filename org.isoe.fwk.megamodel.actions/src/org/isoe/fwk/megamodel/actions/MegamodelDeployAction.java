/*******************************************************************************
 * Copyright (c) 2013 .
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     fpfister - initial API and implementation
 ******************************************************************************/
package org.isoe.fwk.megamodel.actions;

import java.io.File;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.isoe.diagraph.generic.GenericConstants;
import org.isoe.diagraph.lang.DiagraphKeywords;
import org.isoe.extensionpoint.megamodel.IMegamodelDeployService;
import org.isoe.extensionpoint.xmi.IMetamodelRetriever;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.deployer.MegamodelPersistence;
import org.isoe.fwk.deployer.ProjectFactory;
import org.isoe.diagraph.megamodel.Megamodel;
import org.isoe.diagraph.megamodel.MegamodelFactory;
import org.isoe.diagraph.megamodel.MegamodelPackage;
//import org.isoe.diagraph.megamodel.ModelingUniverse;
import org.isoe.fwk.megamodel.deploy.EmfDeployr;
import org.isoe.fwk.megamodel.deploy.EmfHandler;
import org.isoe.fwk.pathes.PathPreferences;
import org.isoe.fwk.utils.ResourceUtils;
import org.isoe.fwk.utils.eclipse.WorkbenchUtils;

/**
 *
 * @author pfister
 *
 */
import org.isoe.diagraph.controler.IDiagraphControler;  //FP140707refactored
public class MegamodelDeployAction implements
		IMegamodelDeployService, DParams { //extends Action //FP150305

	private static final boolean LOG = DParams.MegamodelDeployAction_LOG;
	private boolean silent;
	private boolean all;
	private String languageFilter;
	private IDiagraphControler controler;
	private Megamodel megamodel;
	private String languageToDeploy;
	private boolean deployBuildInPlugin;
	private boolean deployLocalPlugin;
	private boolean deployLocalWorkspace;
	private boolean allprototypes;
	private IMetamodelRetriever metamodelRetriever;
	private List<String> projects;
	private String operation;
	private List<IProject> deployedProjects;// FP140126
	private List<String> deployedLanguages;

	@Override
	public void setAllprototypes(boolean allprototypes) {
		this.allprototypes = allprototypes;
	}

	@Override
	public void setDeployBuildInPlugin(boolean deployBuildInPlugin) {
		this.deployBuildInPlugin = deployBuildInPlugin;
	}

	@Override
	public void setDeployLocalPlugin(boolean deployLocalPlugin) {
		this.deployLocalPlugin = deployLocalPlugin;
	}

	@Override
	public void setDeployLocalWorkspace(boolean deployLocalWorkspace) {
		this.deployLocalWorkspace = deployLocalWorkspace;
	}

	private Megamodel getModelingUniverse(boolean recreate) {
		if (megamodel == null || recreate)
			megamodel = createMegamodel(PathPreferences.getPreferences()
					.getUniverseProjectName(),recreate);
		return megamodel;
	}

	public MegamodelDeployAction() {
		//setText("Megamodel Deployment");
		//setToolTipText("Megamodel Deployment");
	}

	@Override
	public void setMetamodelRetriever(IMetamodelRetriever metamodelRetriever) {
		this.metamodelRetriever = metamodelRetriever;
	}

	private EmfDeployr createMetaModelDeployer(
			final List<String> projsToCompile, final String sourceFolder) { // FP121215
		boolean deployInstancesOnly = false;

		String upn = PathPreferences.getPreferences().getUniverseProjectName();

		String initialstate = PathPreferences.getPreferences()
				.getConnectState();
		if (!initialstate.equals("FromOpendsml"))
			clog("not connected to OpenDslm");

		// FP130627

		String modelInUniverseFolder = PathPreferences.getPreferences()
				.getModelInUniverseFolder();
		String teamNameSpace = PathPreferences.getPreferences()
				.getTeamNamespace();
		clog("modelInUniverseFolder= " + modelInUniverseFolder);
		clog("teamNameSpace= " + teamNameSpace);

		EmfDeployr emfDeployer = new EmfDeployr(controler, metamodelRetriever,
				projsToCompile, deployInstancesOnly, upn,
				modelInUniverseFolder, UNIVERSE_MODEL_ROOT_NAME, teamNameSpace,
				sourceFolder, MEGAMODEL_FOLDER_LOCAL, MODEL_FOLDER,
				JAVA_VERSION, INSTANCE_FOLDER, EDIT_PLUGIN_SUFFIX_,
				EDITOR_PLUGIN_SUFFIX, TEST_PLUGIN_SUFFIX,
				DParams.GEN_JDK_LEVEL, DParams.EMF_LEVEL);
		return emfDeployer;
	}

	private String getDomainName(String proj) {
		if (!proj.endsWith(".edit") && !proj.endsWith(".editor")
				&& !proj.endsWith(".tests"))
			return proj.substring(proj.lastIndexOf(".") + 1);
		else
			return null;
	}

	private String getViewName(EAnnotation anot) {
		EMap<String, String> entries = anot.getDetails();
		for (Entry<String, String> entry : entries) {
			String k = entry.getKey();
			if (k.startsWith("view")) {
				String[] kv = k.split("=");
				return kv[1];
			}
		}
		return "default";
	}

	private List<String> getViews(String ecoreLocation) {
		URI uri = URI.createPlatformResourceURI(ecoreLocation, true);
		List<String> result = new ArrayList<String>();
		EPackage mainp = (EPackage) loadMetamodel(
				CommonPlugin.resolve(uri).toFileString()).getContents().get(0);
		List<EClassifier> clsses = mainp.getEClassifiers();
		for (EClassifier claz : clsses) {
			if (claz instanceof EClass) {
				for (EAnnotation anot : ((EClass) claz).getEAnnotations())
					if (anot.getSource().equals(
							GenericConstants.ANNOT_DIAGRAPH_LITTERAL))
						for (Entry<String, String> entry : anot.getDetails())
							if (entry.getKey().equals(
									DiagraphKeywords.POINT_OF_VIEW))
								result.add(getViewName(anot));

			}
		}
		return result;
	}

	private Resource loadMetamodel(String metamodelPath) {
		ResourceSet metamodelSet = new ResourceSetImpl();
		metamodelSet
				.getResourceFactoryRegistry()
				.getExtensionToFactoryMap()
				.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
						new EcoreResourceFactoryImpl());
		URI metamodelURI = URI.createFileURI(new File(metamodelPath)
				.getAbsolutePath());
		return metamodelSet.getResource(metamodelURI, true);
	}

	private void idle() {
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell()
					.getDisplay().readAndDispatch();
		} catch (Exception e) {
		}
	}

	private void progress(IProgressMonitor progressMonitor, String label) {
		// idle();
		if (progressMonitor.isCanceled()) {
			throw new OperationCanceledException("Operation Canceled");
		}
		progressMonitor.subTask(label);
		progressMonitor.worked(1);
	}

	private void forceAutobuildOn() {
		try {
			org.eclipse.core.resources.IWorkspaceDescription wd = ResourcesPlugin
					.getWorkspace().getDescription();
			wd.setAutoBuilding(true);
			ResourcesPlugin.getWorkspace().setDescription(wd);
		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}

	private static void createProject_(String projname) {
		try {
			ProjectFactory.createPluginProject(projname);
		} catch (Exception e) {
			throw new RuntimeException("unable to create project " + projname); // e.printStackTrace();
		}
	}



	private Megamodel createUniverse_nu(String universeName) {
		boolean pexists = ResourceUtils.projectExists(universeName);
		if (!pexists)
			createProject_(universeName);
		return createMegamodel(universeName,false);
	}

	private Megamodel loadUniverse_nu() {
		String upn = PathPreferences.getPreferences().getUniverseProjectName();
		URI uri = URI.createPlatformResourceURI(
				upn
						+ "/"
						+ PathPreferences.getPreferences()
								.getModelInUniverseFolder() + "/"
						+ UNIVERSE_MODEL_ROOT_NAME + "."
						+ MegamodelPackage.eNAME, true);
		clog(uri.toString());
		return MegamodelPersistence.load(uri,controler);
	}

	public Megamodel saveUniverse(Megamodel mm_) {
		if (LOG)
			clog(PathPreferences.getPreferences().log());
		String upn = PathPreferences.getPreferences().getUniverseProjectName();
		String p = upn + "/"
				+ PathPreferences.getPreferences().getModelInUniverseFolder()
				+ "/" + UNIVERSE_MODEL_ROOT_NAME + "." + MegamodelPackage.eNAME;
		URI uri = URI.createPlatformResourceURI(p, true);
		clog(uri.toString());
		MegamodelPersistence.save(uri, mm_, false,controler);
		return mm_;
	}

	/**********************/



	private void logBuildInDeployment(List<String> langs) { // FP140126xxxaaa
		List<String> deployed = new ArrayList<String>();
		String l = "";
		String[] initials = DParams.INITIAL_LANGUAGES; //FP150530
		for (String initial : initials) {
			l += initial + ";";
		}
		clog("initial languages=" + l);
		l = "";
		if (langs != null)
			for (String deployedLanguage : langs) {
				l += deployedLanguage + ";";
				String lang = deployedLanguage.substring(0,
						deployedLanguage.indexOf(".ecore"));
				deployed.add(lang);
			}
		clog("(2)deployedLanguages=" + l);

		if (!LOG)
			clog(" ------ deployment report: ------------");
		else
			controler.cerror(" ------ deployment report: ------------");
		for (String initial : initials) {
			if (!deployed.contains(initial)) {
				//if (!LOG)
				//	controler.cerror("failed to deploy " + initial);
				//else
					if (LOG) clog("failed to deploy " + initial);
			}
			if (!LOG)
				clog(" ------ end report --------------------");
			else
				controler.cerror(" ------ end report --------------------");
		}
	}

	private List<String> deployBuildInFilteredInDomainArtefacts(
			EmfDeployr emfDeployer, Megamodel megamodel, boolean all,
			IProgressMonitor progressMonitor) {
		if (LOG)
			clog("deployBuildInFilteredInDomainArtefacts: \n");
		List<String> result = emfDeployer.deployBuildInDomainArtefacts(all,
				languageFilter); // FP130821
		logBuildInDeployment(result);
		return result;
	}



	/***************************/

	private List<String> deployBuildInMegamodelFiles(EmfDeployr emfDeployer,
			boolean all, IProgressMonitor progressMonitor) {
		return deployBuildInFilteredInDomainArtefacts(emfDeployer, null, all,
				progressMonitor);
	}




	public void generateAllDiagraphArtifacts(
			final IDiagraphControler controler, final List<String> projects,
			final boolean recreate, final boolean refreshOnly, final IProgressMonitor progressMonitor) { // FP130814x
		String msg = "generate All Diagraph Artifacts ";
		//boolean refreshArtifactysOnly = false;
		getModelingUniverse(recreate);
		progress(progressMonitor, msg);
		controler.generateAllDiagraphArtifacts(projects, megamodel,refreshOnly,
				progressMonitor);// FP130814x [ecmfa_2013.lang.statechart101,
									// ecmfa_2013.lang.statechart101.edit,
									// ecmfa_2013.lang.statechart101.editor,
									// ecmfa_2013.lang.statechart101.tests]
		forceAutobuildOn();
		progressMonitor.done();
		saveUniverse(megamodel);
	}

	private void logdeployed() { // FP130814
		clog("TODO logdeployed()");
		/*
		 * List<Megamodel> megamodels =
		 * getModelingUniverse_(false).getMegamodels(); for (Megamodel megamodel
		 * : megamodels) { clog(megamodel.getName());
		 * List<org.isoe.diagraph.megamodel.Dsml> dsmls = megamodel
		 * .getModelingLanguages(); for (org.isoe.diagraph.megamodel.Dsml dsml :
		 * dsmls) { clog(dsml.getName());
		 * List<org.isoe.diagraph.megamodel.Notation> notations =
		 * dsml.getNotations(); for (org.isoe.diagraph.megamodel.Notation
		 * notation : notations) { clog(notation.getName()); } } }
		 */
	}

	@Override
	public List<IProject> getDeployedProjects() {
		return deployedProjects;
	}

	@Override
	public List<String> getDeployedLanguages() {
		return deployedLanguages;
	}

	private void logResult() {
		String l = "";
		if (deployedLanguages != null)
			for (String deployedLanguage : deployedLanguages) {
				l += deployedLanguage + ";";
			}
		clog("(3)deployedLanguages=" + l);
		l = "";
		if (deployedProjects != null)
			for (IProject deployedProject : deployedProjects) {
				l += deployedProject.getName() + ";";
			}
		clog("deployedProjects=" + l);
	}


	@Override
	public void setLanguageToDeploy(String languageToDeploy) {
		this.languageToDeploy = languageToDeploy;
		operation = "deploy";
	}
/*
	@Override
	public void setGenerateEmfGen(boolean value) { // FP131207
		this.generateEmfGen = value;
	}*/ //FP140415

	@Override
	public void setLanguagesToDiagraph(String languages) { // FP130814
		operation = "diagraph";
		projects = new ArrayList<String>();
		String[] ls = languages.split(";");
		for (String l : ls) {
			projects.add(l);
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

	public void setSilent(boolean value) {
		silent = value;
	}

	private void clog(String mesg) {
		if (LOG)// && !silent)
			System.out.println(mesg);
	}

	@Override
	public void setControler(Object controler) {
		this.controler = (IDiagraphControler) controler;
		this.controler.registerService(this);
	}

	@Override
	public void setLanguageToAll(boolean all) {
		this.all = all;
	}

	@Override
	public void setLanguageFilter(String filter) {
		this.languageFilter = filter;
	}


	private Megamodel createMegamodel(String name, boolean recreate) {
		return controler.getMegamodel(name, recreate);
	}

	private List<IProject> deployMegamodelFromPlugin_(EmfDeployr emfDeployer,
			Megamodel megamodel, boolean notForceOverwrite,
			IProgressMonitor progressMonitor) {

		List<IProject> prjs = emfDeployer.deployMegamodelFromPlugin(
				notForceOverwrite, megamodel, progressMonitor);// FP121017c
		if (LOG) {
			clog("deployMegamodelFromPlugin: ");
			if (prjs.isEmpty())
				System.out.print(" nothing (re)deployed");
			else
				System.out.print("new projects:");
			for (IProject iProject : prjs)
				System.out.print(iProject.toString() + " ");
			clog("");
		}

		return prjs;
	}

	private List<IProject> deployMegamodelFromWorkspace(EmfDeployr emfDeployer,
			Megamodel megamodel, boolean notForceOverwrite, boolean generate,boolean genemf,
			String localFolder, String languageToDeploy, String languageFilter,
			IProgressMonitor progressMonitor) {
		List<IProject> prjs = emfDeployer.deployMegamodelFromWorkspace(
				notForceOverwrite, generate,genemf, megamodel, localFolder,
				languageToDeploy, languageFilter, progressMonitor);
		if (LOG) {
			clog("deployMegamodelFromWorkspace: ");
			if (prjs.isEmpty())
				clog(" nothing (re)deployed");
			else
				System.out.print("new projects:");
			for (IProject iProject : prjs) {
				clog(iProject.toString() + " ");
			}
			clog("");
		}
		return prjs;
	}


	private List<IProject> deployMegamodel(EmfDeployr emfDeployer,
			boolean notForceOverwrite, boolean generate, boolean genemf,
			IProgressMonitor progressMonitor) {
		// String upn =
		// PathPreferences.getPreferences().getUniverseProjectName();
		// String localFolder = DParams.MEGAMODEL_FOLDER_LOCAL;
		// modelingUniverse_ =
		getModelingUniverse(false);// createUniverse(upn);
		List<IProject> prjs = new ArrayList<IProject>(); // FP130515zzzz
		if (deployBuildInPlugin)
			prjs.addAll(deployMegamodelFromPlugin_(emfDeployer, megamodel,
					notForceOverwrite, progressMonitor)); // FP130421pbbbb
		if (deployLocalWorkspace)
			prjs.addAll(deployMegamodelFromWorkspace(emfDeployer, megamodel,
					notForceOverwrite, generate,genemf,
					DParams.MEGAMODEL_FOLDER_LOCAL, languageToDeploy,
					languageFilter, progressMonitor));
		return prjs;
	}

	private void deployMegamodelFromPluginAndFromWorkspace(
			final IDiagraphControler controler, final boolean all,
			final boolean recreate, boolean generate, boolean genemf,
			final IProgressMonitor progressMonitor) { // FP121215
		boolean generationDisabled = false; // FP131207
		deployedLanguages = null;
		deployedProjects = null;
		String msg = "deploy language repository ";
		if (languageFilter != null && !languageFilter.isEmpty())
			msg = msg + " for " + languageFilter;
		if (LOG)
			clog(msg);
		progress(progressMonitor, msg);
		boolean notForceOverwrite = true; // FP130416
		final IViewPart navigator = WorkbenchUtils.showNavigator(); // FP120522
		final List<String> projsToCompile = new ArrayList<String>();
		if (deployLocalPlugin) {
			EmfDeployr emfDeployer2 = createMetaModelDeployer(projsToCompile,// FP130627
					MEGAMODEL_REPOSITORY_BUNDLE_); // FP130412ccc
			deployedLanguages = deployBuildInMegamodelFiles(emfDeployer2, all,
					progressMonitor); // FP130416
		}
		deployedProjects = new ArrayList<IProject>();
		if (deployBuildInPlugin || deployLocalWorkspace) {
			EmfDeployr emfDeployer1 = createMetaModelDeployer(projsToCompile, // FP130627
					MEGAMODEL_BUILDIN_LANG_EXAMPLES_);// MEGAMODEL_FOLDER_LOCAL_);//MEGAMODEL_BUILDIN_LANG_EXAMPLES);
														// //FP130421pb
			deployedProjects = deployMegamodel(emfDeployer1, notForceOverwrite,
					generate, genemf,progressMonitor);
			if (!generationDisabled && generate && DParams.COMPILE_PROJECTS) //FP140415
				emfDeployer1.compileAllProjects_(progressMonitor);
		}
		//progressMonitor.done();
		/*
		if (!generationDisabled && generate){
			if (DParams.GENERATE_DIAGRAPH_ARTIFACTS_ON_DEPLOY)
			generateAllDiagraphArtifacts(controler, projsToCompile, recreate,
					progressMonitor);
		}*/ //FP140416
		progressMonitor.done();
	}

	@Override
	public void run() { // FP130916
		final boolean instances = true;
		final boolean refreshOnly = false;
		//final boolean generate = generateEmfGen;
		controler.removeListeners_();

		ProgressMonitorDialog dialog = new ProgressMonitorDialog(
				controler.getShell());
		try {
			dialog.run(false, true,
					new org.eclipse.jface.operation.IRunnableWithProgress() {
						public void run(IProgressMonitor monitor) {
							boolean notrecreate = false;
							if (operation.equals("deploy")) {
								deployMegamodelFromPluginAndFromWorkspace(
										controler, allprototypes, notrecreate,
										//DParams.GENERATE_EMF_ON_DEPLOY, monitor); // FP130814
								        true, DParams.GENERATE_EMF_ON_DEPLOY_,monitor); //FP140415xxxx   // FP130814
															// //FP130626
								if (LOG)
									logResult();
							} else if (operation.equals("diagraph")) {
								generateAllDiagraphArtifacts(controler,
										projects, notrecreate, refreshOnly ,monitor); // FP130817b
							} else
								throw new RuntimeException("unknown operation "
										+ operation);
						}
					});
		} catch (Exception e) {
			try {
				if (!(e instanceof InterruptedException)) { // FP130916
					e.printStackTrace();
					if (e instanceof InvocationTargetException) {
						String exclog = "";
						InvocationTargetException ite = (java.lang.reflect.InvocationTargetException) e;
						Throwable exc = ite.getTargetException();
						if (exc != null) {
							if (exc.getCause() != null)
								exclog += "cause: " + exc.getCause().toString()
										+ "\r\n";
							if (exc.getMessage() != null)
								exclog += "message: "
										+ exc.getCause().toString() + "\r\n";
							StackTraceElement[] ste = exc.getStackTrace();
							for (StackTraceElement stackTraceElement : ste) {
								exclog += stackTraceElement.toString() + "\r\n";
							}
						}
						controler
								.showMessage("error in MMDA (1) while deploying Metamodel Repository "
										+ exclog); // FP140108
					} else
						controler
								.showMessage("error in MMDA (2) while deploying Metamodel Repository "
										+ e.toString());

				} else
					controler
							.showMessage("deploy Metamodel Repository: operation cancelled");
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		if (LOG)
			logdeployed();
		controler.restoreListeners(false);
	}


}
