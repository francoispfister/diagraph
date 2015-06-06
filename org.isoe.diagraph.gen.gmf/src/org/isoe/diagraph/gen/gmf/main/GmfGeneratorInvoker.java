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
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.common.util.URI;
import org.isoe.diagraph.diastyle.helpers.StyleHandler;
import org.isoe.diagraph.internal.m2.DiaPointOfView;
import org.isoe.diagraph.preferences.DiagraphPreferences;
import org.isoe.diagraph.views.ViewConstants;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.utils.EmfValidator;
import org.isoe.fwk.utils.ResourceManager;
import org.isoe.fwk.utils.ResourceUtils;
import org.isoe.diagraph.gmfgen.IGmfDiagramGen;
import org.isoe.diagraph.gmfgen.IGmfGeneratorInvoker;

/**
 *
 * @author pfister
 *
 */
public class GmfGeneratorInvoker implements IGmfGeneratorInvoker {

	private static final boolean LOG = DParams.GmfGeneratorInvoker_LOG;
	private static final boolean EMF_VALIDATE = true; // DO not set to false !!!
	private IGmfDiagramGen runner;

	public GmfGeneratorInvoker(IGmfDiagramGen runner) {
		super();
		this.runner = runner;
	}

	@Override
	public String manageFoldersAndGenerateAllPointsOfViewCommon(String layer,
			boolean atRuntime, boolean genFromDiagraph, GenModel genmodel,
			String projectName, String absoluteLocation,
			boolean deleteOldJavaSrc_, boolean changeTargetFolder, boolean rcp,
			boolean validate, boolean save, boolean refreshArtifactsOnly, IProgressMonitor progressMonitor) {


		if (progressMonitor != null) {
			progressMonitor
					.subTask("manageFoldersAndGenerateAllPointsOfViewCommon");
			progressMonitor.worked(1);
		}
		absoluteLocation = URI.createFileURI(absoluteLocation).toString();
		if (absoluteLocation.startsWith("file:/"))
			absoluteLocation = absoluteLocation.substring(6);

		final String[] rd = ResourceManager.parseFileLocation(absoluteLocation,
				DiagraphPreferences
						.getStringPreference(DParams.KEY_TARGET_MODEL_DIR),
				DiagraphPreferences
						.getStringPreference(DParams.KEY_SAVED_MODEL_DIR));

		final IProject project = ResourceManager.getProject(projectName);
		deleteOldJavaSrc_ = save
				&& (deleteOldJavaSrc_ || DiagraphPreferences
						.getBooleanPreference(DParams.KEY_DELETE_OLD_JAVA_SRC_)
						&& !projectName.endsWith(".megamodel")); // FP130828

		if (refreshArtifactsOnly)
			deleteOldJavaSrc_ = false; //FP140602

		changeTargetFolder = save
				&& (changeTargetFolder || DiagraphPreferences
						.getBooleanPreference(DParams.KEY_ANOTHER_FOLDER));
		String opt = "";
		if (deleteOldJavaSrc_)
			opt += " -delete old java source ";
		if (changeTargetFolder)
			opt += " -change target folder ";
		String logmesg = "Validating ";
		if (save)
			logmesg = "Generating ";
		logmesg += "Editor for " + projectName
				+ (opt.isEmpty() ? "" : " with options:" + opt);
		List<String> logs = runner.getLog();
		if (logs != null)
			logs.add(logmesg);
		if (LOG)
			clog(logmesg);
		// FP120429
		if (save
				&& DiagraphPreferences
						.getBooleanPreference(DParams.KEY_SAVE_OLD)
				&& !refreshArtifactsOnly
				)
			ResourceUtils.copyFolder(projectName,
					rd[ResourceManager.RSRC_PROJECT], ResourceManager
							.getWorkFolder(rd),
					rd[ResourceManager.RSRC_DIRECTORY], DiagraphPreferences
							.getStringPreference(DParams.KEY_SAVED_MODEL_DIR));

		String result = runner.generateAllPointsOfView(layer, atRuntime,
				genmodel, genFromDiagraph, rd, deleteOldJavaSrc_,
				changeTargetFolder, rcp, validate, refreshArtifactsOnly,progressMonitor);// FP121031
		// FP120511b
		boolean error = result.contains("!!!!Error!!!!"); // FP120513//TODO be
															// serious: create
															// an exception
															// hierarchy
		// TODO create a specific exception hierarchy
		if (save && changeTargetFolder) {
			String pathName = rd[ResourceManager.RSRC_ABSOLUTE_];
			String filename = pathName.substring(pathName
					.lastIndexOf(File.separator) + 1);
			String path = rd[ResourceManager.RSRC_PROJECT] + File.separator
					+ rd[ResourceManager.RSRC_TARGET_FOLDER] + File.separator
					+ filename;
		}
		// if no error, gmfgen files are now generated

		if (genFromDiagraph && runner.isGenError()) {
			// if (diagrams == null) {
			result = "there is an error:" + result;
			return result; // FP120522
		} else if (!genFromDiagraph) {
			runner.refresh(projectName); // FP120330
			clog("not genFromDiagraph");
			return "not genFromDiagraph";
		}
		if (!error) {
			if (StyleHandler.HANDLE_GMF_PALETTE) // && viewid==0//FP121031www//
													// the// first time only
				runner.exportIconsToEdit("", layer);
			result += "exporting icons to edit plugin\n";
			runner.refresh(projectName); // FP120330
			if (!refreshArtifactsOnly && save && DiagraphPreferences.getBooleanPreference(DParams.KEY_EMFGMF_GENERATION_)) { //FP140602
				generateEmfAndGmfPlugins(project, layer, rd, progressMonitor);
			} else
				validateEmf(project, layer, rd, progressMonitor); // FP121122
		}
		// FP120506
		return result;
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	@Override
	public void generateEmfAndGmfPlugins(IProject project, String view,
			String[] resourceInfo, IProgressMonitor progressMonitor) {

		IFile genmodelfile = ResourceUtils.getFile(project,
				DParams.MODEL_FOLDER + "/"
						+ resourceInfo[ResourceManager.RSRC_MODEL], "genmodel");

		if (genmodelfile == null) {
			if (LOG)
				clog("genmodelfile == null");
			return;
		}
		runner.progress(progressMonitor, "generateEmfAndGmfPlugins "
				+ DParams.MODEL_FOLDER + "/"
				+ resourceInfo[ResourceManager.RSRC_MODEL]);

		String genModelPath = genmodelfile.getFullPath().toString(); // FP121122
																		// /aigle.idm.diagraph.tp4v/model/tp4v.genmodel

		try {
			EmfValidator.generate(URI.createPlatformResourceURI(genModelPath,
					true));
		} catch (Exception e) {
			String validationError = e.toString();
			runner.cerror(validationError);
			if (EMF_VALIDATE)
				throw new RuntimeException("Emf validation error :"
						+ validationError);
		}

		String gmfgenrootpath = DParams.MODEL_FOLDER + "/"
				+ resourceInfo[ResourceManager.RSRC_MODEL]
				+ ViewConstants.VIEW_SEPARATOR_0
				+ DiaPointOfView.getLayeredRootName(view); // FP140216 //
															// FP121102
															// model/multv_default_root

		IFile gmfgenfileRoot = ResourceUtils.getFile(project, gmfgenrootpath,
				"gmfgen");// FP121018

		if (gmfgenfileRoot == null) { // root first
			clog("no diagram generator " + DParams.MODEL_FOLDER + "/"
					+ resourceInfo[ResourceManager.RSRC_MODEL] + ".gmfgen");
			return;
		} else if (LOG)
			clog("generateDiagram - root "
					+ gmfgenfileRoot.getFullPath().toString());

		runner.generateDiagram(project, gmfgenfileRoot, progressMonitor); // root
		// unique &&
		// only
	}

	private void validateEmf(IProject project, String layer,
			String[] resourceInfo, IProgressMonitor progressMonitor) {
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

}
