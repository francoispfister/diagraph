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
package org.isoe.fwk.megamodel.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.isoe.diagraph.megamodel.Dsml;
import org.isoe.diagraph.megamodel.Megamodel;
import org.isoe.diagraph.megamodel.MegamodelPackage;
import org.isoe.extensionpoint.megamodel.IModelDeployService;
import org.isoe.extensionpoint.xmi.IMetamodelRetriever;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.deployer.MegamodelPersistence;
import org.isoe.fwk.megamodel.deploy.EmfDeployr;
import org.isoe.fwk.pathes.PathPreferences;
import org.isoe.fwk.utils.eclipse.WorkbenchUtils;

/**
 *
 * @author pfister
 *
 */
import org.isoe.diagraph.controler.IDiagraphControler; //FP140707refactored
import org.isoe.extensionpoint.diagraph.action.DiagraphService; //FP140707_c_refactored

public class ModelDeployService extends Action implements IModelDeployService,
		DParams {
	private static final boolean LOG = DParams.ModelDeployService_LOG;
	private boolean silent;
	private static final boolean SHOW_MESSAGE = false;
	private static final boolean SAVE_ON_DEPLOY = false; // FP130903
	private IDiagraphControler controler;
	private String target;
	private String sourceFolder;
	private String sourcePlugin;
	private String language;
	private boolean consolidate;
	private boolean initialization;
	private IMetamodelRetriever metamodelRetriever;

	private EmfDeployr createModelDeployer(final List<String> projsToCompile,
			String sourcePlugin, String sourceFolder) { // FP121215
		final boolean deployModelsOnly = true;
		final EmfDeployr emfDeployer = new EmfDeployr(controler,
				metamodelRetriever, projsToCompile, deployModelsOnly,
				sourcePlugin, PathPreferences.getPreferences()
						.getModelInUniverseFolder(), UNIVERSE_MODEL_ROOT_NAME,
				PathPreferences.getPreferences().getInstanceRoot(),
				sourceFolder, MEGAMODEL_FOLDER_LOCAL, MODEL_FOLDER,
				JAVA_VERSION, INSTANCE_FOLDER, EDIT_PLUGIN_SUFFIX_,
				EDITOR_PLUGIN_SUFFIX, TEST_PLUGIN_SUFFIX, GEN_JDK_LEVEL,
				EMF_LEVEL);
		return emfDeployer;
	}

	@Override
	public void setDirection(boolean value) {
		this.consolidate = value;
	}

	@Override
	public void setLanguage(String language) {
		this.language = language;
	}

	@Override
	public void setMode(boolean mode) {
		this.initialization = mode;
	}

	public ModelDeployService() {
		setText("Model Deployment");
		setToolTipText("Model Deployment");
	}

	@Override
	public void setControler(Object controler) {
		this.controler = (IDiagraphControler) controler;
		this.controler.registerService(this);
	}

	@Override
	public void setTarget(String modelPlugin) {
		this.target = modelPlugin;
		if (LOG)
			clog("target will be: " + modelPlugin);
	}

	public void run() {// FP130421z
		final IViewPart navigator = WorkbenchUtils.showNavigator(); // FP120522
		replicate();
		// clog(consolidate ? "consolidate done" : "deploy done");
		// else
		// System.err.println("no replication");
	}

	private void replicate() {// FP130421z
		// boolean result_ = false;
		controler.removeListeners_();
		ProgressMonitorDialog dialog = new ProgressMonitorDialog(
				controler.getShell());
		try {// FP150313aa
			dialog.run(false, true,
					new org.eclipse.jface.operation.IRunnableWithProgress() {
						public void run(IProgressMonitor monitor) {

								if (initialization && !consolidate)
									clog("initialization of a new model workbench");

								List<String> copied = copyModels(controler,
										target, sourcePlugin, sourceFolder,
										consolidate, monitor);// FP130517
								if (LOG)
									for (String copy : copied)
										// FP140320
										clog("REPLC " + copy
												+ " has been created");
								if (!initialization) {
									if (consolidate) {
										if (LOG)
											clog("replication :" + "LWB<-MWB");
										controler
												.sendCommand(IDiagraphControler.CMD_REFRESH_LWB);
									} else {
										if (LOG)
											clog("replication :" + "LWB->MWB");
									}
								}
								monitor.done();
						}
					});
		} catch (Exception e) {
			if (!(e instanceof OperationCanceledException)) {
				System.err.println("exception while replicating " + e);
			} else
				System.err
						.println("replicating the Model Repository: operation cancelled");
		}

		controler.restoreListeners(false);
		// return result_;
	}

	/*
	 * private void alert(String mesg) { clog(mesg); if (SHOW_MESSAGE)
	 * controler.showMessage(mesg); else{ controler.log(mesg);
	 * System.err.println(mesg); } }
	 */

	@Override
	public void setSourceFolder(String sourceFolder) {
		this.sourceFolder = sourceFolder;
	}

	@Override
	public void setSourcePlugin(String sourcePlugin) {
		this.sourcePlugin = sourcePlugin;
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

	private List<String> copyModels(final IDiagraphControler controler,
			final String instancePlugin, final String megamodelPlugin,
			final String megamodelFolder, final boolean consolidate,
			final IProgressMonitor progressMonitor) { // FP121215
		final List<String> projsToCompile = new ArrayList<String>();
		String info = "\n [instancePlugin=" + instancePlugin
				+ "] \n[megamodelPlugin=" + megamodelPlugin
				+ "] \n[megamodelFolder=" + megamodelFolder + "]";
		String mesg = consolidate ? "Starting repository consolidation from Model Workbench to Language Workbench "
				: "Starting model deployment from Language Workbench to Model Workbench ";
		mesg += info;

		if (LOG)
			clog("copyModels "+mesg); //FP150606
		progress(progressMonitor, mesg);
		final EmfDeployr emfDeployer = createModelDeployer(projsToCompile,
				megamodelPlugin, megamodelFolder);
		List<String> result = null;
		if (consolidate) {
			result = consolidate(emfDeployer, true, progressMonitor); // FP131128
		} else {// FP150313aa
			boolean overwrite = DParams.OVERWRITE_ON_DEPLOY; // FP131128
			result = deploy(emfDeployer, !overwrite, progressMonitor);// FP131128
		}
		forceAutobuildOn();
		return result;
	}

	private void progress(IProgressMonitor progressMonitor, String label) {
		// idle();
		if (progressMonitor.isCanceled()) {
			throw new OperationCanceledException("Operation Canceled");
		}
		progressMonitor.subTask(label);
		progressMonitor.worked(1);
		if (LOG)
			clog(label);
	}

	private URI getLocalMegamodelUri() {
		return URI.createURI("platform:/resource/"
				+ PathPreferences.getPreferences()
						.getInstanceRepositoryLocation() + "/src/"
				+ UNIVERSE_MODEL_ROOT_NAME + "." + MegamodelPackage.eNAME);
	}

	private URI getBundleMegamodelUri() {
		return URI
				.createURI("platform:/plugin/"
						+ PathPreferences.getPreferences()
								.getUniverseProjectName()
						+ "/"
						+ PathPreferences.getPreferences()
								.getModelInUniverseFolder() + "/"
						+ UNIVERSE_MODEL_ROOT_NAME + "."
						+ MegamodelPackage.eNAME);
	}

	private List<String> consolidate(EmfDeployr emfDeployer,
			boolean notForceOverwrite, IProgressMonitor progressMonitor) {
		String unusedNS = language;

		clog("consolidate megamodel to LWB");
		URI bmuri = getBundleMegamodelUri();
		Megamodel bundleMegamodel = MegamodelPersistence.load(bmuri, controler);
		List<String> result = new ArrayList<String>();
		if (DParams.CONSOLIDATE_ && bundleMegamodel != null) { // FP131128
																// //FP140624
			result.addAll(emfDeployer.putModelToBundleRepository(target,
					bundleMegamodel,
					// language,
					progressMonitor));
			MegamodelPersistence.save(bmuri, bundleMegamodel, true, controler);
		}
		return result;
	}

	private List<String> deploy(EmfDeployr emfDeployer, boolean notOverwrite,
			IProgressMonitor progressMonitor) {
		URI bundleMegamodelUri = getBundleMegamodelUri();
		URI localMegamodelUri = getLocalMegamodelUri();
		clog("deploy megamodel from LWB");
		Megamodel mm = MegamodelPersistence.load(bundleMegamodelUri, controler);
		if (mm == null)
			 throw new RuntimeException("should not happen in deploy: "+bundleMegamodelUri.toString()+" (bundleMegamodelUri is missing)");
		//FP150606
		boolean deployModelsOnly = true;
		// FP150313aa
		List<String> deployed = emfDeployer.deployModelFromBundleRepository(
				initialization, !notOverwrite, deployModelsOnly, target,
				language, progressMonitor);
		clog("saving to MWB");
		MegamodelPersistence.save(localMegamodelUri, mm, true, controler); // do
		return deployed;
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
	public void setMetamodelRetriever(IMetamodelRetriever metamodelRetriever) {
		this.metamodelRetriever = metamodelRetriever;
	}

	/*------------- not used ***************/

	private List<String> consolidateModel_old_nu(EmfDeployr emfDeployer,
			boolean notForceOverwrite, IProgressMonitor progressMonitor) {
		String upn = PathPreferences.getPreferences().getUniverseProjectName();

		IProject repositoryProject_ = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(upn); // FP130518
		if (repositoryProject_ == null)
			throw new RuntimeException(
					" sould not happen (in consolidateModel)");
		if (!repositoryProject_.isOpen())
			try {
				repositoryProject_.open(new NullProgressMonitor());// this
																	// should
																	// not
																	// happen
			} catch (CoreException e1) {
			}

		String repositorypath = "platform:/plugin/" + upn + "/"
				+ PathPreferences.getPreferences().getModelInUniverseFolder();
		URI repositorypathURI = URI.createURI(repositorypath);
		String p = CommonPlugin.resolve(repositorypathURI).toFileString();
		if (p == null)
			throw new OperationCanceledException(
					"Not in a modeler configuration (6), " + repositorypath
							+ " does not exist !!!");

		URI megamodelUri = URI.createURI(repositorypath + "/"
				+ UNIVERSE_MODEL_ROOT_NAME + "." + MegamodelPackage.eNAME);
		URI modellUri = URI.createURI("platform:/resource/"
				+ PathPreferences.getPreferences()
						.getInstanceRepositoryLocation() + "/src/"
				+ UNIVERSE_MODEL_ROOT_NAME + "." + MegamodelPackage.eNAME);
		Megamodel mm = null;
		Megamodel megamodel = null;
		try {
			megamodel = MegamodelPersistence.load(megamodelUri, controler); // not
																			// xx
																			// used
																			// at
																			// the
																			// moment
		} catch (Exception e) {
			clog("Megamodel metamodel has changed !!!");
			// throw new RuntimeException("Not in a modeler configuration !!!");
		}

		// List<IProject> result = new ArrayList<IProject>();
		List<String> result = new ArrayList<String>();

		boolean deployModelsOnly = true;

		if (DParams.CONSOLIDATE_) { // FP131128
			result.addAll(emfDeployer.putModelToBundleRepository(
			// notForceOverwrite,
			// deployModelsOnly,
					target, megamodel,// mm,
					// language,
					progressMonitor));// FP121017c
			try {
				if (megamodel != null) {
					MegamodelPersistence.save(modellUri, megamodel, true,
							controler);
				}
			} catch (Exception e) {
				clog("error while saving megamodel");
			}
		}

		return result;
	}

	private void refreshRepositoryProject() throws CoreException {
		IProject lwbRepositoryProject = ResourcesPlugin
				.getWorkspace()
				.getRoot()
				.getProject(
						PathPreferences.getPreferences()
								.getUniverseProjectName()); // FP130518
		if (lwbRepositoryProject == null)
			throw new RuntimeException(
					" sould not happen (in consolidateModel)");
		if (!lwbRepositoryProject.isOpen())
			lwbRepositoryProject.open(new NullProgressMonitor());// this should
																	// not
																	// happen
		org.isoe.fwk.utils.debug.WorkspaceUtils.getInstance().refreshProject(
				lwbRepositoryProject.getName(), false);

		// lwbRepositoryProject.refreshLocal(IResource.DEPTH_ONE,new
		// NullProgressMonitor());
	}

	private List<IProject> consolidateModel_nothing__nu(EmfDeployr emfDeployer,
			boolean notForceOverwrite, IProgressMonitor progressMonitor) {
		return new ArrayList<IProject>();
	}

	private URI getBundleMegamodelUri__nu() {
		String bundleMegamodelProject = PathPreferences.getPreferences()
				.getUniverseProjectName();
		if (LOG)
			clog("getBundleMegamodelUri");
		String bundleRepositorypath = "platform:/plugin/"
				+ bundleMegamodelProject + "/"
				+ PathPreferences.getPreferences().getModelInUniverseFolder();
		if (LOG)
			clog("repositorypath = " + bundleRepositorypath);
		URI bundleRepositorypathURI = URI.createURI(bundleRepositorypath);
		String p = CommonPlugin.resolve(bundleRepositorypathURI).toFileString();
		if (p == null)
			throw new OperationCanceledException(
					"Not in a modeler configuration (6), "
							+ bundleRepositorypath + " does not exist !!!");
		if (LOG)
			clog("megamodel physical location = " + p);// C:\workspaces\v130926\_megamodel\src\megamodel.megamodel
		return URI.createURI(bundleRepositorypath + "/"
				+ UNIVERSE_MODEL_ROOT_NAME + "." + MegamodelPackage.eNAME);
	}

	private void xx() {
		String upn = PathPreferences.getPreferences().getUniverseProjectName();

		IProject repositoryProject_ = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(upn); // FP130518
		if (repositoryProject_ == null)
			throw new RuntimeException(
					" sould not happen (in consolidateModel)");
		if (!repositoryProject_.isOpen())
			try {
				repositoryProject_.open(new NullProgressMonitor());// this
																	// should
																	// not
																	// happen
			} catch (CoreException e1) {
			}
	}

	private void idle() {
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell()
					.getDisplay().readAndDispatch();
		} catch (Exception e) {
		}
	}

}
