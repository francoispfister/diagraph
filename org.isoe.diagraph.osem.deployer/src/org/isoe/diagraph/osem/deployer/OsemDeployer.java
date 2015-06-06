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
package org.isoe.diagraph.osem.deployer;

import java.io.File;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.codegen.ecore.genmodel.GenJDKLevel;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.gmt.mod.infra.common.core.internal.utils.ProjectUtils;
import org.isoe.diagraph.osem.constants.OsemConfiguration;
import org.isoe.diagraph.preferences.DiagraphPreferences;
import org.isoe.diagraph.views.ViewConstants;
import org.isoe.ep.osem.deployer.IOsemDeployer;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.deployer.M2Handler;
import org.isoe.fwk.deployer.ModelSnippet;
import org.osgi.framework.Bundle;

/**
 *
 * @author fpfister
 *
 */
import org.isoe.diagraph.controler.IDiagraphControler;  //FP140707refactored
public class OsemDeployer implements IOsemDeployer, OsemConfiguration {
	// FP140321z

	private static String REPLACE_SEPARATOR;
	static {
		if (REPLACE_SEPARATOR == null) {
			REPLACE_SEPARATOR = File.separator;
			if (REPLACE_SEPARATOR.equals("\\"))
				REPLACE_SEPARATOR = "\\\\";
		}
	}

	private static final boolean LOG = DParams.OsemDeployer_LOG;
	private BundleCopier unzipper;
	private static OsemDeployer instance;
	private String examplePackageName = "examples";
	private String basePackageName = DiagraphPreferences.getTeamNamespace();
	private String baseModelFolder = "model";
	private URI megamodelSource;
	private IDiagraphControler controler;

	public static IOsemDeployer getInstance(IDiagraphControler controler) {
		if (instance == null){
			instance = new OsemDeployer();
			instance.controler = controler;
		}
		return instance;
	}

	private OsemDeployer() {
		super();
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}


	private String getDiagramEditPartPatch(String language){
		return basePackageName + "." + language + "."
				+ ViewConstants.DIAGRAM_SUFFIX_ + "/src/"
				+ basePackageName.replaceAll("\\.", "/") + "/" + language
				+ "/" + ViewConstants.DIAGRAM_SUFFIX_ + "/edit/parts";
	}

	private String getDiagramRelatedPlugin(String language, String suffix) {
		if (suffix.equals(ViewConstants.DIAGRAM_SUFFIX_LITTERAL
				+ ViewConstants.DIAGRAM_DEFAULT))
			return basePackageName + "." + language + "."
					+ ViewConstants.DIAGRAM_SUFFIX_LITTERAL
					+ ViewConstants.DIAGRAM_DEFAULT;
		else
			return null;
	}


	private boolean deployPatch(String which, String srcplugin,
			String language, String targetplugin, String patchSourcefolder) {
		if (patchSourcefolder == null)
			return false;
		String patchTargetLocation = null;
		String targetPatchPlugin = null;
		if (which.equals(ViewConstants.DIAGRAM_SUFFIX_LITTERAL
				+ ViewConstants.DIAGRAM_DEFAULT)){
			patchTargetLocation = getDiagramEditPartPatch(language);
			targetPatchPlugin = getDiagramRelatedPlugin(language,
					ViewConstants.DIAGRAM_SUFFIX_LITTERAL
							+ ViewConstants.DIAGRAM_DEFAULT);
		}
		if (patchTargetLocation != null) {
			boolean overWrite = true;
			if (LOG)
				clog(" patchTargetLocation " + patchTargetLocation
						+ " overwrite "
						+ (overWrite ? " overwrite" : " not overwrite"));
			IProject project = ResourcesPlugin.getWorkspace().getRoot()
					.getProject(targetplugin); // lang.m2.kerm.ext.kfsm
			IPath p = project.getLocation();
			File pfile = p.toFile();
			clog(pfile.getAbsolutePath()); // C:\workspaces\mintel213\lang.m2.kerm.ext.kfsm
			try {
				File targetProjectfile = new File(project.getLocation()
						.toString());
				File targetProjectWorkspace = targetProjectfile.getParentFile();
				String targetPatch = targetProjectWorkspace.getAbsolutePath()
						+ File.separator + patchTargetLocation;
				targetPatch = targetPatch.replaceAll("/", REPLACE_SEPARATOR);
				boolean result = new BundleCopier(this).copyFromBundle(srcplugin,
						patchSourcefolder, targetPatchPlugin, targetPatch,
						overWrite);
				project.refreshLocal(IFile.DEPTH_INFINITE,
						new NullProgressMonitor());
				return result;
			} catch (Exception e1) {
				e1.printStackTrace();
				return false;
			}
		}
		return false;
	}

	private boolean deploy(IProgressMonitor monitor, String srclocation,
			String srcplugin, String extension, String sourceFolder,
			String language, String targetplugin) {// lang.m2.kerm.simpleworld.samplerun
		boolean result = false;
		String archive = sourceFolder + language + "/" + srclocation
				+ targetplugin + extension;
		if (unzipper == null)
			unzipper = new BundleCopier(this);
		result = unzipper.unzipProject(srcplugin, archive, targetplugin,
				monitor);
		monitor.worked(1);
		return result;
	}

	private void createSnippetM2Project_(String lang) {
		String prjsufx = lang + "_test";
		GenJDKLevel jdklevel = DParams.GEN_JDK_LEVEL;
		try {
			URI targPrjUri = URI.createPlatformResourceURI(basePackageName
					+ "." + prjsufx, true);// C:\workspaces\v130926\lang.m2.kfsm\model
			IProject project = (IProject) getPluginProject(basePackageName,
					prjsufx);
			M2Handler m2Handler = new M2Handler(project, megamodelSource,
					targPrjUri, basePackageName, jdklevel.toString());
			m2Handler.init(lang, baseModelFolder, "ecore");
			m2Handler.copyM2();
			m2Handler.generateSnippet(new ModelSnippet(basePackageName));
			project.refreshLocal(IFile.DEPTH_INFINITE,
					new NullProgressMonitor());
			// instanceHandler.restoreWorkspace_();
		} catch (Exception e) {
			throw new RuntimeException("unable to create project " + prjsufx); // e.printStackTrace();
		}
	}

	private void createSnippetM1Project_(String modelName) { //FP150313
		GenJDKLevel jdklevel = DParams.GEN_JDK_LEVEL;
		try {
			// String[] m_=modelName.split("\\.");
			URI targPrjUri = URI.createPlatformResourceURI(basePackageName
					+ "." + modelName, true);// C:\workspaces\v130926\lang.m2.kfsm\model
			IProject project = (IProject) getPluginProject(basePackageName,
					modelName);
			M2Handler m2Handler = new M2Handler(project, megamodelSource,
					targPrjUri, basePackageName, jdklevel.toString());
			m2Handler.init(modelName, baseModelFolder, null);
			// m2Handler.loadModel();
			m2Handler.copyM1(modelName);
			m2Handler.copyM1(modelName + ViewConstants.SUFFIX_DIAGRAM);
			m2Handler.generateSnippet(new ModelSnippet(examplePackageName));
			project.refreshLocal(IFile.DEPTH_INFINITE,
					new NullProgressMonitor());
			// instanceHandler.restoreWorkspace_();
		} catch (Exception e) {
			clog("error " + "unable to create project " + modelName);
			throw new RuntimeException("unable to create project " + modelName); // e.printStackTrace();
		}
	}

	private IProject getPluginProject(String base, String name) {
		Bundle bundleContainingResources = null;
		try {
			IProject result = ProjectUtils.getProject(base + "." + name);
			if (result == null)
				result = ProjectUtils.getPluginProject(base + "." + name,
						bundleContainingResources, "dir");
			if (LOG)
				clog("project created: " + result.getLocationURI());
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void deployModels(String language, URI megamodelSource,
			List<String> instancesdeployed) {
		URI uri = URI.createPlatformPluginURI(basePackageName + "." + language,
				true);
		String langProjPath = CommonPlugin.resolve(uri).toFileString();
		if (langProjPath != null) { // project open on the lwb side ?
									// //FP140323x
			this.megamodelSource = megamodelSource;
			if (DParams.CREATE_SNIPPET_M2_PROJECT)
			    createSnippetM2Project_(language);

			doOsemJob_(OsemConfiguration.OSEM_M1, language);
			String mesg = "";
			for (String instance : instancesdeployed) {

				instance = instance.substring(instance
						.indexOf(DParams.INSTANCE_FOLDER + File.separator)
						+ DParams.INSTANCE_FOLDER.length() + 1);
				if (instance.endsWith("." + language)) {//FP150313aa
					if (DParams.CREATE_SNIPPET_M1_PROJECT)
					    createSnippetM1Project_(instance);
					mesg += instance + " ; ";
				}
			}
			if (LOG)
				clog("deploying models for language " + language + " " + mesg);
		} else if (LOG)
			clog("no opened project on the plugin side named " + language);
	}

	@Override
	public void doOsemJob_(String mode, String language) {
		generateOsem(DSL0_M2_EXAMPLES_COP, DSL0_M1_EXAMPLES, mode, this,
				language);
		generateOsem(DSL1_M2_EXAMPLES_COP, DSL1_M1_EXAMPLES, mode, this,
				language);
		generateOsem(DSL2_M2_EXAMPLES_COP, DSL2_M1_EXAMPLES, mode, this,
				language);
	}

	private boolean generateOsem(final String[] m2examples,
			final String[] m1examples, final String mode, final Object sender,
			final String languag) {
		WorkspaceJob job = new WorkspaceJob("Deploying Osem ") { //FP150315
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor)
					throws CoreException {

				if (mode.equals(OSEM_M2)) {
					monitor.beginTask("Deploying Osem (M2)", m2examples.length);
					for (String targetplugin : m2examples) {
						String lang = targetplugin.substring(targetplugin
								.lastIndexOf(".") + 1);
						if (lang.equals(languag)) {

							if (deploy(monitor, OSEM_M2, OSEM_ID, OSEM_ZIP,
									OSEM_ZIPS,
									lang, targetplugin)) {
								if (lang.equals("kfsm")) {
									String cpySrc = OSEM_ZIPS + languag
											+ "/src/";
									if (LOG)
										clog("copy patch "
												+ ViewConstants.DIAGRAM_SUFFIX_LITTERAL
												+ ViewConstants.DIAGRAM_DEFAULT);
									if (!deployPatch(
											ViewConstants.DIAGRAM_SUFFIX_LITTERAL
													+ ViewConstants.DIAGRAM_DEFAULT,
											OSEM_ID, lang, targetplugin, cpySrc))
										;
								}
							}
						}

					}
				} else {
					if (LOG)
					  for (String examp:m1examples)
					   clog(examp);
					monitor.beginTask("Deploying Osem (M1)", m1examples.length);
					for (String example : m1examples) {
						if (example.contains("." + languag + ".")) // TODO
																	// filter
							deploy(monitor, OSEM_M1, OSEM_ID, OSEM_ZIP,
									OSEM_ZIPS,
									languag, example);
					}
				}
				return Status.OK_STATUS;
			}
		};
		job.setRule(ResourcesPlugin.getWorkspace().getRoot());
		job.schedule();
		return true;
	}

	@Override
	public void cerror(String mesg) {
		controler.cerror(mesg);

	}


}
