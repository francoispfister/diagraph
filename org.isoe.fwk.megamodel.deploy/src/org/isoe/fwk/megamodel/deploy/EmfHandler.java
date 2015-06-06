/*
 * Copyright (c) 2005, 2008 Borland Software Corporation
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Artem Tikhomirov (Borland) - initial API and implementation
 *    François Pfister Adaptation to DIAGRAPH Megamodel deployment
 */
package org.isoe.fwk.megamodel.deploy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.codegen.ecore.genmodel.GenClassifier;
import org.eclipse.emf.codegen.ecore.genmodel.GenJDKLevel;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.codegen.ecore.genmodel.GenRuntimeVersion;
import org.eclipse.emf.codegen.ecore.genmodel.generator.GenBaseGeneratorAdapter;
import org.eclipse.emf.codegen.util.CodeGenUtil;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gmf.internal.bridge.genmodel.BasicGenModelAccess;
//import org.eclipse.gmf.internal.bridge.genmodel.BasicGenModelAccess;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.ui.PlatformUI;
//import org.isoe.diagraph.DParams;
import org.isoe.diagraph.megamodel.Diagram;
import org.isoe.diagraph.megamodel.EcoreDiagram;
import org.isoe.diagraph.megamodel.Megamodel;
import org.isoe.diagraph.megamodel.MegamodelFactory;
import org.isoe.diagraph.megamodel.Dsml;
import org.isoe.diagraph.megamodel.Model;
import org.isoe.diagraph.megamodel.NotationDiagram;
//import org.isoe.diagraph.megamodel.ModelingUniverse;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.deployer.ProjectFactory;
import org.isoe.fwk.megamodel.handler.MegamodelHandler;
import org.isoe.fwk.utils.ResourceUtils;
import org.isoe.fwk.utils.debug.WorkspaceUtils;
import org.isoe.fwk.utils.eclipse.PluginResourceCopy;
//import org.isoe.fwk.utils.workspace.BundleFileCopier;
import org.isoe.fwk.utils.workspace.RuntimeWorkspaceSetup;
//import org.isoe.fwk.utils.workspace.WorkspaceFileCopier;
import org.osgi.framework.Constants;

/**
 * Generates and (by default) compiles emf plugins.
 *
 * @author artem
 * @author pfister //TODO refactor, this is too confuse
 */
public class EmfHandler {

	private static final boolean LOG = DParams.EmfHandler_LOG;

	private String megamodelFolderSrc;
	private String instanceFolderInPrj;
	private String modelFolder;
	private String editPluginSuffix;
	private String editorPluginSuffix;
	private String testPluginSuffix;
	private String javaVersion;
	private GenJDKLevel genJdkLevel;
	private GenRuntimeVersion emfLev;

	private final List<String> projects;// = new ArrayList<String>();

	private static final String INTERFACE_TEMPLATE = "{0} public interface {1} '{ }'"; // FP150313

	private static final boolean DEPLOY_INSTANCES_WITH_META_MODELS = true;

	private EmfDeployr emfDeployer;
	private URI modeluri;
	private EPackage model;
	private String targetPlugin;
	private String sourcePlugin_;
	private GenModel domainGenModel;
	private RuntimeWorkspaceSetup runtimeWorkspaceSetup;
	private String modelName;
	private String universeNameSrc;
	private Dsml dsml; // FP130616
	private String megamodelFolderLocal;

	public Dsml getDsml() {
		return dsml;
	}

	public EmfHandler(EmfDeployr deployer, final List<String> projectsToInit,
			String universeNameSrc, String megamodelFolderPlugin,
			String megamodelFolderLocal, String instanceFolder,
			String modelFolder, String editPluginSuffix,
			String editorPluginSuffix, String testPluginSuffix,
			String javaVersion, GenJDKLevel genJdkLevel,
			GenRuntimeVersion emfLev) {
		this.universeNameSrc = universeNameSrc;
		this.megamodelFolderSrc = megamodelFolderPlugin;
		this.megamodelFolderLocal = megamodelFolderLocal;
		this.instanceFolderInPrj = instanceFolder;
		this.modelFolder = modelFolder;
		this.editPluginSuffix = editPluginSuffix;
		this.editorPluginSuffix = editorPluginSuffix;
		this.testPluginSuffix = testPluginSuffix;
		this.javaVersion = javaVersion;
		this.genJdkLevel = genJdkLevel;
		this.emfLev = emfLev;
		this.emfDeployer = deployer;
		this.projects = projectsToInit;

		if (LOG){
			clog("creating EmfHandler "+
				     "\n universeNameSrc = "+universeNameSrc+
				     "\n megamodelFolderSrc = "+megamodelFolderSrc+
				     "\n megamodelFolderLocal = "+megamodelFolderLocal+
				     "\n instanceFolderInPrj = "+instanceFolderInPrj+
				     "\n modelFolder = "+modelFolder+
				     "\n editPluginSuffix = "+editPluginSuffix+
				     "\n editorPluginSuffix = "+editorPluginSuffix+
				     "\n testPluginSuffix = "+testPluginSuffix+
				     "\n javaVersion = "+javaVersion+
				     "\n genJdkLevel = "+genJdkLevel+
				     "\n emfLevel = "+emfLev

					);
		}



		runtimeWorkspaceSetup = RuntimeWorkspaceSetup.getInstance(javaVersion);
	}

	private void addToProjects(String pluginid) {
		projects.add(pluginid);
		if (LOG)
			clog("adding " + pluginid + " to projectsToInit");
	}

	protected boolean existModel(String plugin, String filename,
			String fileExtension) {
		try {
			File f = new File(ResourcesPlugin.getWorkspace().getRoot()
					.getLocation().toString()
					+ '/' + plugin + '/' + filename + '.' + fileExtension);
			boolean result = f.exists();
			if (LOG)
				clog(f.getAbsolutePath() + (result ? " exists" : " not exists"));
			return result;
		} catch (Exception e) {
			return false;
		}
	}

	protected URI saveModel(EObject tosave, String plugin_, String filename,
			String fileExtension) {
		if (fileExtension == null)
			throw new RuntimeException("file extension is null for " + filename);
		ResourceSet rs = new ResourceSetImpl();

		// E:\Apps\eclipse-diagraph-deploy-130124\runtime-c2b\_megamodel\repository

		String fileFolder = ResourcesPlugin.getWorkspace().getRoot()
				.getLocation().toString();
		String path = fileFolder + '/' + plugin_ + '/' + filename + '.'
				+ fileExtension;
		URI platformResourceURI = URI.createPlatformResourceURI(plugin_ + '/'
				+ filename + '.' + fileExtension, false);
		URI furi = URI.createFileURI(path);
		Resource r = rs.createResource(furi, "");
		r.getContents().add(tosave);
		try {
			r.save(null);
			if (LOG)
				clog(path + " saved ok");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("error saving " + filename);
		}
		return platformResourceURI;
	}

	private void copyDomainModel(String domainName, boolean fromBundle) {

		String path = sourcePlugin_ + "/";// + sourceFolder;
		// addRelatedFiles(dsml, targetPlugin, domainName,fromBundle);

		saveModel(model, targetPlugin, modelFolder + "/"
				+ emfDeployer.lastsegment, domainName);
	}

	public void deployFilesWithFilter(String source, String target,
			String startFilter, String endFilter, boolean overWrite) {
		URI ptftargeturi = URI.createPlatformResourceURI(target, false);
		URI targeturi = CommonPlugin.resolve(ptftargeturi);
		String targetpath = targeturi.toFileString();
		// String start = "." + model.getName() + "_";
		// String end = "_diagram";
		String sourcefolder = source;
		// BundleFileCopier bundleFileCopier = new BundleFileCopier();
		BundleFileCopier.getInstance().copyFromBundle(sourcePlugin_,
				sourcefolder, targetpath, startFilter, endFilter, overWrite); // FP130407zzz
	}

	private void idle_() {
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell()
					.getDisplay().readAndDispatch();
		} catch (Exception e) {
		}
	}

	private void dsmlAddRelatedFiles(Dsml dsml, String path, String domain,
			boolean fromBundle) {
		URI uri = URI.createPlatformPluginURI(path, false);
		File dir = new File(CommonPlugin.resolve(uri).toFileString());
		File[] sub = dir.listFiles();
		for (File file : sub) {
			String fname = file.getName();
			String extension = file.getName().substring(
					file.getName().indexOf("."));
			if (dsml != null)// not used at the moment
				if (extension.startsWith("." + domain))
					if (file.getName().endsWith("_diagram"))
						addDsmlDiagramToModel(dsml, file, fromBundle);
					else
						getModel(dsml, file, fromBundle);
		}
	}

	private void addEcoreDiagramToDsml_nu(Dsml mm, File file, boolean fromBundle) {
		String fileName = file.getName();
		String modelname = fileName.substring(0, fileName.indexOf("."));
		if (LOG)
			clog("add ecore diagram to dsml " + file.getAbsolutePath());
		EcoreDiagram ecorediag = MegamodelFactory.eINSTANCE
				.createEcoreDiagram();
		ecorediag.setURI_(file.getName());
		mm.getEcoreDiagrams().add(ecorediag);// FP130828
	} // FP130903

	// FP140624see
	private void addDsmlDiagramToModel(Dsml mm, File file, boolean fromBundle) {
		String fileName = file.getName();
		String modelname = fileName.substring(0, fileName.indexOf("."));

		Model m = getModel(mm, file, fromBundle);
		// Diagram diag = MegamodelFactory.eINSTANCE.createDiagram();
		NotationDiagram notationdiag = MegamodelFactory.eINSTANCE
				.createNotationDiagram();
		notationdiag.setURI_(file.getName());
		m.getNotationDiagrams().add(notationdiag);
		if (LOG)
			clog("add notation diagram " + file.getAbsolutePath()
					+ " to model " + m.getName());
		// m.getDiagrams().add(diag);//FP130828

		// mm.getEcoreDiagrams().add(dsmldiag);//FP130828
	}// FP130903

	private Model getModel(Dsml dsml, File file, boolean fromBundle) {
		String fileName = file.getName();
		String modelname = fileName.substring(0, fileName.indexOf("."));
		Model m = MegamodelHandler.getInstance().findModel(dsml, modelname);
		if (m == null)
			m = addModelToDsml(dsml, file, fromBundle);
		return m;
	}

	private Model addModelToDsml(Dsml dsml, File file, boolean fromBundle) {
		String fileName = file.getName();
		Model m = MegamodelFactory.eINSTANCE.createModel();
		m.setURI_(fileName);
		String modelName = fileName.substring(0, fileName.indexOf("."));
		m.setName(modelName);
		// m.setURI(modelName+"."+dsml.getName());
		if (dsml != null) {
			dsml.getModels().add(m); // FP140622
			if (DParams.MegaModelBuilderJOB_LOG)
				jobclog("(3) add model " + modelName + " to dsml "
						+ dsml.getName());
		}
		return m;

	}

	private void jobclog(String mesg) {
		if (DParams.MegaModelBuilderJOB_LOG)
			System.out.println(mesg);
	}

	/*-*-------------------------------------------*/

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	static IProject getProject(String name) {
		return ResourceUtils.getProject(name);
		// return null;
	}

	static IProject createProject_(String projname) {
		try {
			IProject p = ProjectFactory.createPluginProject(projname);
			return p;
		} catch (Exception e) {
			throw new RuntimeException("unable to create project " + projname); // e.printStackTrace();
		}
	}

	public IProject handleDomainM1Model(URI sourceUri, String deployerPluginID,
			boolean fromBundle, String targetPlugin, boolean generate,
			IProgressMonitor progressMonitor) throws Exception {
		IProject result = null;
		this.targetPlugin = targetPlugin;
		// idle();

		if (progressMonitor.isCanceled()) {
			throw new OperationCanceledException("Operation Canceled");
		}
		progressMonitor.subTask("handleDomainModel " + targetPlugin);
		progressMonitor.worked(1);
		boolean pexists = org.isoe.fwk.utils.ResourceUtils
				.projectExists(targetPlugin);
		if (generate)
			result = getProject(targetPlugin);
		modeluri = sourceUri;
		sourcePlugin_ = deployerPluginID;
		loadModel();

		// FP130421xxx1
		String start = null;
		String end = "." + model.getName();

		if (LOG)
			clog("1deployRelatedFiles -filter= " + start + " -- " + end);
		deployRelatedFiles(fromBundle, null, megamodelFolderSrc, null,
				instanceFolderInPrj, start, end, progressMonitor);
		if (LOG)
			clog("1deployRelatedFiles -end");

		String start_ = "." + model.getName() + "_";
		String end_ = "_diagram";
		if (LOG)
			clog("2deployRelatedFiles -filter= " + start_ + " -- " + end_);

		deployRelatedFiles(fromBundle, null, megamodelFolderSrc, null,
				instanceFolderInPrj, start, end, progressMonitor);
		if (LOG)
			clog("2deployRelatedFiles -end");

		refreshTargetProject(); // FP130105
		return result;
	}

	private void deployModelFilesFromBundleToWorkspace(String src, String tgt,
			boolean overWrite) throws CoreException {
		deployFilesWithFilter(src, tgt, null, model.getName() + "." + "ecore",
				overWrite);
		deployFilesWithFilter(src, tgt, null, model.getName() + "."
				+ "ecorediag", overWrite);
		deployFilesWithFilter(src, tgt, null, "." + model.getName(), overWrite);
		deployFilesWithFilter(src, tgt, "." + model.getName() + "_",
				"_diagram", overWrite);
	}

	private void deployModelFiles(String domainName_, String sourcefolderPath,
			String targetfolderPath, boolean fromBundle,
			IProgressMonitor progressMonitor) throws CoreException {
		try {

			String foldr = instanceFolderInPrj;

			// FP130421xxx
			deployRelatedFiles(fromBundle,
					sourcefolderPath,
					megamodelFolderSrc,// FP130407xx
					targetfolderPath, foldr, null, "." + model.getName(),
					progressMonitor);
			String start = "." + model.getName() + "_";
			String end = "_diagram";
			deployRelatedFiles(fromBundle, sourcefolderPath,
					megamodelFolderSrc,// FP130407xx
					targetfolderPath, foldr, start, end, progressMonitor);
		} catch (Exception e) {
			if (domainName_!=null && !domainName_.equals("ecore"))//FP150330 TODO see
			   emfDeployer.getControler().cerror("unable to deploy related files for "+domainName_); //FP150530
		}
	}

	private void deployEcoreAndEcoreDiag(String domainName,
			String sourcefolderPath, String targetfolderPath,
			boolean fromBundle, IProgressMonitor progressMonitor)
			throws CoreException {
		copyDomainModel(domainName, fromBundle);// ecore
		deployRelatedFiles(fromBundle, sourcefolderPath, megamodelFolderSrc,
				targetfolderPath, modelFolder, null, model.getName() + "."
						+ "ecorediag", progressMonitor);

	}

	private void openTargetProject(String domainName, String sourcefolderPath,
			String targetfolderPath, boolean fromBundle,
			IProgressMonitor progressMonitor) throws CoreException {
		IProject targetProject = getTargetProject();
		if (LOG)
			System.out
					.println("** " + targetProject.getFullPath().toOSString());
		if (!targetProject.isAccessible())
			targetProject.open(new NullProgressMonitor());

	}

	// cleanup please
	public IProject handleDomainM2Model(String domainName,
			String sourcefolderPath, String targetfolderPath_, URI sourceUri,
			String deployerPluginID, boolean fromBundle, boolean generate,
			boolean generateEmfEditor_, boolean instanceOnly,
			IProgressMonitor progressMonitor) throws Exception {
		dsml = null;
		targetPlugin = emfDeployer.basePackage + "." + modelName;
		progress(progressMonitor, "handleDomainModel " + targetPlugin);
		boolean pexists = org.isoe.fwk.utils.ResourceUtils
				.projectExists(targetPlugin);
		IProject result = null;
		if (generate) {
			IProject p = getProject(targetPlugin);
			if (!pexists) // FP130404
				result = p; // new prj is created
		}
		if (result == null)
			clog(targetPlugin + " has not been created"); // FP150530
		modeluri = sourceUri; // platform:/resource/_megamodel/repository/food.ecore
		sourcePlugin_ = deployerPluginID;
		loadModel();

		if (LOG)
			clog("absolute location is "
					+ ResourcesPlugin.getWorkspace().getRoot().getLocation()
							.toString() + '/' + targetPlugin);

		if (!pexists) {
			if (LOG)
				clog("handleDomainModel for " + (generate ? "-generate " : "")
						+ (pexists ? "-pexists " : ""));
			handleDomainModel_new(generateEmfEditor_, pexists); // FP140415genemf
																// // genmodel
																// etc

			dsml = MegamodelHandler.getInstance().addDsml(
					// FP140618voir4
					emfDeployer.getMegamodel(), universeNameSrc, model,
					targetPlugin, fromBundle);
			// String s_nu = targetPlugin + "/" + modelFolder + "/" +
			// emfDeployer.lastsegment;
			if (generate) {
				deployEcoreAndEcoreDiag(domainName, sourcefolderPath,
						targetfolderPath_, fromBundle, progressMonitor);
			} else
				openTargetProject(domainName, sourcefolderPath,
						targetfolderPath_, fromBundle, progressMonitor);
			if (DEPLOY_INSTANCES_WITH_META_MODELS)
				deployModelFiles(domainName, sourcefolderPath,
						targetfolderPath_, fromBundle, progressMonitor);
		} else if (LOG)
			clog("*********allready exists"); // FP130407xxy
		refreshTargetProject(); // FP130105
		return result;
	}

	private void progress(IProgressMonitor progressMonitor, String label) {
		if (progressMonitor.isCanceled())
			throw new OperationCanceledException("Operation Canceled");
		progressMonitor.subTask(label);
		progressMonitor.worked(1);
		if (LOG)
			clog(label);
	}

	public boolean deployArtefacts_(String source, String target,
			URI sourceUri, String deployerPluginID, boolean overWrite,
			IProgressMonitor progressMonitor) throws Exception {
		modeluri = sourceUri;
		sourcePlugin_ = deployerPluginID;
		loadModel();
		deployModelFilesFromBundleToWorkspace(source, target, overWrite);
		return (model != null);
	}

	/*-*-------------------------------------------*/

	private void loadModel() {
		model = (EPackage) new ResourceSetImpl().getResource(modeluri, true)
				.getContents().get(0);
	}

	private void handleDomainModel_old(boolean generate,
			boolean projectAlreadyExists) { // FP130105 //FP131207
		if (!generate)
			generate = true;

		BasicGenModelAccess gmAccess = new BasicGenModelAccess(model);
		IStatus s = gmAccess.createDummy(); // paradox: use a gmf feature to
		// build the pure emf plugin set
		if (!s.isOK())
			throw new RuntimeException("BasicGenModelAccess creation failed ");
		domainGenModel = gmAccess.model();
		// String pluginID = emfDeployer.basePackage + "." +
		// emfDeployer.lastsegment;
		domainGenModel.setModelPluginID(targetPlugin);
		domainGenModel.setModelDirectory("/" + targetPlugin + "/src/");
		if (LOG)
			clog("Creating GenModel: " + targetPlugin);

		domainGenModel.setEditDirectory("/" + targetPlugin + "."
				+ editPluginSuffix + "/src/");
		domainGenModel.setEditorDirectory("/" + targetPlugin + "."
				+ editorPluginSuffix + "/src/");
		domainGenModel.setTestsDirectory("/" + targetPlugin + "."
				+ testPluginSuffix + "/src/");

		domainGenModel.setComplianceLevel(genJdkLevel); // FP120518
		domainGenModel.setRuntimeVersion(emfLev);// FP120521

		domainGenModel.setCanGenerate(true);
		domainGenModel.setGenerateSchema(false);
		domainGenModel.setCodeFormatting(true);
		domainGenModel.setNonNLSMarkers(true);
		GenPackage genPackage = domainGenModel.findGenPackage(model);
		genPackage.setBasePackage(emfDeployer.basePackage);

		if (generate) {
			org.eclipse.emf.codegen.ecore.generator.Generator gen = new org.eclipse.emf.codegen.ecore.generator.Generator();
			gen.setInput(domainGenModel);
			gen.generate(domainGenModel,
					GenBaseGeneratorAdapter.MODEL_PROJECT_TYPE,
					new BasicMonitor());
			gen.generate(domainGenModel,
					GenBaseGeneratorAdapter.EDIT_PROJECT_TYPE,
					new BasicMonitor());
			gen.generate(domainGenModel,
					GenBaseGeneratorAdapter.EDITOR_PROJECT_TYPE,
					new BasicMonitor());
			gen.generate(domainGenModel,
					GenBaseGeneratorAdapter.TESTS_PROJECT_TYPE,
					new BasicMonitor());
			fixInstanceClasses_(domainGenModel);

			runtimeWorkspaceSetup.getReadyToStartAsBundle(ResourcesPlugin
					.getWorkspace().getRoot()
					.getProject(domainGenModel.getModelPluginID()));
			// FP140319
			runtimeWorkspaceSetup.removeExportJava(ResourcesPlugin
					.getWorkspace().getRoot()
					.getProject(domainGenModel.getModelPluginID()));

			if (!domainGenModel.getModelPluginID().equals(
					domainGenModel.getEditPluginID()))
				runtimeWorkspaceSetup.getReadyToStartAsBundle(ResourcesPlugin
						.getWorkspace().getRoot()
						.getProject(domainGenModel.getEditPluginID()));
			saveModel(domainGenModel, targetPlugin, modelFolder + "/"
					+ emfDeployer.lastsegment, "genmodel");
		}
		if (!projectAlreadyExists) { // FP130404cc
			addToProjects(domainGenModel.getModelPluginID());
			addToProjects(domainGenModel.getEditPluginID());
			addToProjects(domainGenModel.getEditorPluginID());
			addToProjects(domainGenModel.getTestsPluginID());
		} else if (LOG)
			clog("allready exists " + domainGenModel.getModelPluginID());

		// return domainGenModel;
	}

	private void handleDomainModel_new(boolean generate_,
			boolean projectAlreadyExists) { // FP130105 //FP131207
		BasicGenModelAccess gmAccess = new BasicGenModelAccess(model);
		IStatus s = gmAccess.createDummy(); // paradox: use a gmf feature to
		// build the pure emf plugin set

		if (!s.isOK())
			throw new RuntimeException("BasicGenModelAccess creation failed ");
		domainGenModel = gmAccess.model();
		// String pluginID = emfDeployer.basePackage + "." +
		// emfDeployer.lastsegment;
		domainGenModel.setModelPluginID(targetPlugin);
		domainGenModel.setModelDirectory("/" + targetPlugin + "/src/");
		if (LOG)
			clog("Creating GenModel: " + targetPlugin);

		domainGenModel.setComplianceLevel(genJdkLevel); // FP120518
		domainGenModel.setRuntimeVersion(emfLev);// FP120521

		domainGenModel.setGenerateSchema(false);
		domainGenModel.setCodeFormatting(true);
		domainGenModel.setNonNLSMarkers(true);
		GenPackage genPackage = domainGenModel.findGenPackage(model);
		genPackage.setBasePackage(emfDeployer.basePackage);
		domainGenModel.setCanGenerate(true);
		org.eclipse.emf.codegen.ecore.generator.Generator gen = new org.eclipse.emf.codegen.ecore.generator.Generator();
		gen.setInput(domainGenModel);
		gen.generate(domainGenModel,
				GenBaseGeneratorAdapter.MODEL_PROJECT_TYPE, new BasicMonitor());

		if (generate_) {

			// FP140415 depl
			// FP140415emfgen
			domainGenModel.setEditDirectory("/" + targetPlugin + "."
					+ editPluginSuffix + "/src/");
			domainGenModel.setEditorDirectory("/" + targetPlugin + "."
					+ editorPluginSuffix + "/src/");
			domainGenModel.setTestsDirectory("/" + targetPlugin + "."
					+ testPluginSuffix + "/src/");

			/*
			 * org.eclipse.emf.codegen.ecore.generator.Generator gen = new
			 * org.eclipse.emf.codegen.ecore.generator.Generator();
			 * gen.setInput(domainGenModel); gen.generate(domainGenModel,
			 * GenBaseGeneratorAdapter.MODEL_PROJECT_TYPE, new BasicMonitor());
			 */
			gen.generate(domainGenModel,
					GenBaseGeneratorAdapter.EDIT_PROJECT_TYPE,
					new BasicMonitor());
			gen.generate(domainGenModel,
					GenBaseGeneratorAdapter.EDITOR_PROJECT_TYPE,
					new BasicMonitor());
			gen.generate(domainGenModel,
					GenBaseGeneratorAdapter.TESTS_PROJECT_TYPE,
					new BasicMonitor());

		}

		fixInstanceClasses_(domainGenModel);

		runtimeWorkspaceSetup.getReadyToStartAsBundle(ResourcesPlugin
				.getWorkspace().getRoot()
				.getProject(domainGenModel.getModelPluginID()));
		// FP140319
		runtimeWorkspaceSetup.removeExportJava(ResourcesPlugin.getWorkspace()
				.getRoot().getProject(domainGenModel.getModelPluginID()));

		if (!domainGenModel.getModelPluginID().equals(
				domainGenModel.getEditPluginID()))
			runtimeWorkspaceSetup.getReadyToStartAsBundle(ResourcesPlugin
					.getWorkspace().getRoot()
					.getProject(domainGenModel.getEditPluginID()));
		saveModel(domainGenModel, targetPlugin, modelFolder + "/"
				+ emfDeployer.lastsegment, "genmodel");

		if (!projectAlreadyExists) { // FP130404cc
			addToProjects(domainGenModel.getModelPluginID());

			if (generate_) {
				addToProjects(domainGenModel.getEditPluginID());
				addToProjects(domainGenModel.getEditorPluginID());
				addToProjects(domainGenModel.getTestsPluginID());
			}

		} else if (LOG)
			clog("allready exists " + domainGenModel.getModelPluginID());

		// return domainGenModel;
	}

	private void log(IOException e) {
		System.err.println(e);
	}

	private IProject getTargetProject() {
		IProject pluginProject = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(targetPlugin);
		return pluginProject;
	}

	private void fixInstanceClasses_(GenModel domainGenModel) {
		final Set<String> allInstanceClassNames = new HashSet<String>();
		for (GenPackage nextPackage : domainGenModel.getGenPackages()) {
			for (GenClassifier nextGenClassifier : nextPackage
					.getGenClassifiers()) {
				if (nextGenClassifier.getEcoreClassifier().eIsSet(
						EcorePackage.Literals.ECLASSIFIER__INSTANCE_CLASS_NAME)) {
					allInstanceClassNames.add(nextGenClassifier
							.getEcoreClassifier().getInstanceClassName());
				}
			}
		}
		if (allInstanceClassNames.isEmpty())
			return;
		IPackageFragmentRoot theRoot = null;
		IFile manifestFile = null;
		try {
			String pluginID = domainGenModel.getModelPluginID();
			IProject pluginProject = getTargetProject();
			IJavaProject javaProject = JavaCore.create(pluginProject);
			if (javaProject == null)
				throw new RuntimeException(
						"Generated EMF model project is not a java project");
			IPackageFragmentRoot[] roots = javaProject
					.getPackageFragmentRoots();
			for (int i = 0; i < roots.length && theRoot == null; i++)
				if (!roots[i].isReadOnly())
					theRoot = roots[i];
			manifestFile = pluginProject.getFile(JarFile.MANIFEST_NAME);
			if (theRoot == null)
				throw new RuntimeException(
						"Writable project root not found in the generated project");
			if (!(manifestFile != null && manifestFile.exists()))
				throw new RuntimeException("Manifest was not generated");
			Manifest manifest = new Manifest(manifestFile.getContents());
			Attributes attributes = manifest.getMainAttributes();
			StringBuffer exportedPackages = new StringBuffer(
					attributes.getValue(Constants.EXPORT_PACKAGE));
			for (String instanceClassName : allInstanceClassNames)
				generateUserInterface(instanceClassName, theRoot,
						exportedPackages);
			attributes.putValue(Constants.EXPORT_PACKAGE,
					exportedPackages.toString());
			ByteArrayOutputStream contents = new ByteArrayOutputStream();
			manifest.write(contents);
			manifestFile.setContents(
					new ByteArrayInputStream(contents.toByteArray()), true,
					true, new NullProgressMonitor());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("error in" + "fixInstanceClasses "
					+ e.getMessage());
		}
	}

	// FIXME turn verification back once figure templates are ok
	protected void hookJDTStatus(IProject p) throws Exception {
		// JDTUtil jdtUtil = new JDTUtil(p);
		// IStatus jdtStatus = jdtUtil.collectProblems();
		// if (!jdtStatus.isOK()) {
		// Plugin.logError(jdtStatus.getMessage());
		// Assert.fail(jdtStatus.getMessage());
		// }
	}

	protected void hookGeneratorStatus(IStatus generatorStatus) {
		if (!generatorStatus.isOK())
			Activator.getDefault().getLog().log(generatorStatus);
		if (generatorStatus.getSeverity() == IStatus.ERROR) {
			throw new RuntimeException("GMF editor generation produced errors:"
					+ generatorStatus.toString());
		}
	}

	public void restoreWorkspace() {
		RuntimeWorkspaceSetup.releaseInstance();
	}

	private void generateUserInterface(String fqClassName, // FP150313
			IPackageFragmentRoot projectRoot, StringBuffer exportedPackages) {
		String className = CodeGenUtil.getSimpleClassName(fqClassName);
		String packageName = CodeGenUtil.getPackageName(fqClassName);
		if (packageName == null) {
			packageName = "";
		}
		String packagePrefix = packageName;
		try {
			IPackageFragment pkgFragment = projectRoot.createPackageFragment(
					packageName, true, new NullProgressMonitor());
			if (packagePrefix.length() > 0)
				packagePrefix = "package " + packagePrefix + ";";
			pkgFragment.createCompilationUnit(
					className + ".java",
					MessageFormat.format(INTERFACE_TEMPLATE, new Object[] {
							packagePrefix, className }), true,
					new NullProgressMonitor());
		} catch (JavaModelException e) {
			throw new RuntimeException(e.getMessage());
		}
		if (packageName.length() > 0
				&& exportedPackages.indexOf(packageName) == -1) {
			exportedPackages.append(",");
			exportedPackages.append(packageName);
		}
	}

	private void refreshSourceProject(IProject sourceProject) {
		try {
			sourceProject.refreshLocal(IResource.DEPTH_ONE,
					new NullProgressMonitor());
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * IProject sourceProject= ???? sourceProject.refreshLocal
		 */
		// TODO use eclipse automation to refresh a project in a different
		// instance

	}

	public void refreshTargetProject() {
		WorkspaceUtils.getInstance().refreshProject(
				getTargetProject().getName(), true);
	}

	public void deployRelatedFiles(boolean fromBundle, String sourceFolderPath,
			String sourceFolder, String targetFolderPath, String targetFolder,
			String startFilter, String endFilter,
			IProgressMonitor progressMonitor) throws CoreException {
		if (progressMonitor.isCanceled()) {
			throw new OperationCanceledException("Operation Canceled");
		}
		progressMonitor.subTask("deployRelatedFiles ");
		progressMonitor.worked(1);

		IProject targetProject = getTargetProject();
		IFolder tf = targetProject.getFolder(targetFolder);
		if (!tf.exists())
			tf.create(true, true, null);
		String repo = tf.getRawLocation().toFile().getAbsolutePath();
		if (LOG)
			clog("repository folder= " + repo);
		String domain = null;
		String path = null;
		if (startFilter == null && endFilter != null
				&& endFilter.startsWith(".")) {
			domain = endFilter.substring(1);
			path = sourcePlugin_ + "/" + sourceFolder;
		}
		if (domain != null)
			dsmlAddRelatedFiles(dsml, path, domain, fromBundle);
		boolean overWrite = false;
		if (fromBundle)
			BundleFileCopier.getInstance().copyFromBundle(sourcePlugin_,
					sourceFolder, targetPlugin, targetFolder, startFilter,
					endFilter, overWrite); // FP130416xx
		else {
			if (sourceFolder == null)
				sourceFolder = "repository";
			new WorkspaceFileCopier().copyFromWorkspace(sourcePlugin_,
					sourceFolderPath, sourceFolder, targetPlugin,
					targetFolderPath, targetFolder, endFilter, progressMonitor);
		}
	}

	private void copyfile(File src, File trg) {
		try {
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(trg);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
			if (LOG)
				clog(src.getName() + " -> " + trg.getName() + " copied");
		} catch (FileNotFoundException ex) {
			System.err.println("copyfile error " + ex.getMessage());
		} catch (IOException e) {
			System.err.println("copyfile error " + e.getMessage());
		}
	}

	void copyFiltered(File src, String targ) {
		String fname = src.getName();
		String extension = fname.substring(fname.lastIndexOf(".") + 1);
		if (extension.startsWith(modelName))
			copyfile(src, new File(targ + fname));
	}

	void copyNotFiltered(File src, String targ) {
		String fname = src.getName();
		File trg = new File(targ + fname);
		copyfile(src, trg);
	}

	/**
	 * replicate the model in the Language Workbench Repository, where they are
	 * not plugins, just files
	 *
	 * @param sourcePlugin
	 * @param sourceFolder
	 * @param targetPlugin
	 * @param targetFolder
	 * @param languageToDeploy
	 * @param dsml
	 * @param progressMonitor
	 * @return
	 * @throws CoreException
	 */
	private boolean consolidateInstancesInFolder(String sourceFolder,
			String targetPlugin, String targetFolder, String language,
			IProgressMonitor progressMonitor) throws CoreException {
		this.targetPlugin = targetPlugin;
		URI truri = null;
		String targetRepositoryFolder = targetPlugin + "/" + targetFolder;
		if (emfDeployer.getControler().isModelConfiguration())
			truri = URI.createPlatformPluginURI(targetRepositoryFolder, false);
		else
			truri = URI
					.createPlatformResourceURI(targetRepositoryFolder, false);
		URI trruri = CommonPlugin.resolve(truri);
		String targetPath = trruri.toFileString();
		if (LOG)
			clog("lwb repository=" + targetPath);// E:\Apps\workspaces\ws-integr-c6a\_megamodel\repository\
		String trecore = targetPath + modelName + ".ecore";
		File ecorefile = new File(trecore);
		if (!ecorefile.exists())
			clog(modelName + ".ecore" + " should exist in the repository");
		List<String> artefacts = emfDeployer.getCopyableArtefacts();
		int copies = 0;
		for (String artefact : artefacts) {
			progress(progressMonitor, "copying elements for " + artefact);
			File src = new File(artefact);
			if (src.exists()) {
				if (language != null && !language.isEmpty())
					copyFiltered(src, targetPath);
				else
					copyNotFiltered(src, targetPath);
				copies++;
			} else if (LOG)
				clog("artefact does not exist: " + artefact);

		}
		if (LOG)
			clog("putting instances for language: " + modelName);
		return copies > 0;
	}

	private List<String> deployInstancesInFolder(String sourcePlugin,
			String sourceFolder, String targetPlugin, String targetFolder,
			String languageToDeploy, Dsml dsml, boolean overWrite)
			throws CoreException {
		this.targetPlugin = targetPlugin;
		if (languageToDeploy != null && !languageToDeploy.isEmpty()) {
			URI truri = null;
			String targetRepositoryFolder = targetPlugin + "/" + targetFolder;
			if (emfDeployer.getControler().isModelConfiguration())
				truri = URI.createPlatformPluginURI(targetRepositoryFolder,
						false);
			else
				truri = URI.createPlatformResourceURI(targetRepositoryFolder,
						false);
			URI trruri = CommonPlugin.resolve(truri);
			String trabspath = trruri.toFileString();
			if (LOG)
				clog(trabspath);
			String trecore = trabspath + modelName + ".ecore";
			File ecorefile = new File(trecore);
			if (!ecorefile.exists()) {
				System.err.println(modelName + ".ecore"
						+ " should exist in he repository");
			}

		}
		clog("deploying instances for language: " + modelName);
		IProject targetProject = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(targetPlugin);
		if (!targetProject.isAccessible())
			targetProject.open(new NullProgressMonitor());
		IFolder target = targetProject.getFolder(targetFolder);
		if (!target.exists())
			target.create(true, true, null);
		String[] filter = { ".ecore", ".ecorediag" };

		String repo = target.getRawLocation().toFile().getAbsolutePath();
		if (LOG)
			clog("repository folder= " + repo);
		dsmlAddRelatedFiles(dsml, sourcePlugin + "/" + sourceFolder, modelName,
				false);// not used at the moment
		// boolean overwrite = true; // FP130416zz

		PluginResourceCopy copier = PluginResourceCopy.getInstance();

		copier.copy(sourcePlugin, sourceFolder, targetPlugin, targetFolder,
				overWrite, filter, modelName); // FP130518// replaceexisting
		String log = copier.getLogs();

		// String log = PluginResourceCopy.getInstance().copy(sourcePlugin,
		// sourceFolder,
		// targetPlugin, targetFolder, overWrite, filter, modelName);
		// //FP130518// replaceexisting

		if (LOG)
			clog("DPIF " + log); // FP120623c
		return copier.getCopied();
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	public List<String> deployInstance(String srcplug, String srcfoldr,
			String targetPlug, String targetfoldr, String languageToDeploy,
			Dsml dsml, boolean overWrite) {
		EmfHandler.getProject(targetPlug);
		try {
			List<String> copied = deployInstancesInFolder(srcplug, srcfoldr,
					targetPlug, targetfoldr, languageToDeploy, dsml, overWrite);
			if (copied != null) {
				refreshTargetProject();
				return copied;
			} else
				return null;
		} catch (CoreException e1) {
			e1.printStackTrace();
			throw new RuntimeException("error while deployInstance " + e1);
		}

	}

	public boolean consolidateInstance(String srcfoldr, String targetPlug,
			String targetfoldr, String language,
			IProgressMonitor progressMonitor) {// FP130514
		EmfHandler.getProject(targetPlug);
		try {
			if (consolidateInstancesInFolder(
			// srcplug,
					srcfoldr, targetPlug, targetfoldr, language,
					// dsml,
					progressMonitor)) {
				// refreshSourceProject();
				return true;
			} else
				return false;
		} catch (CoreException e1) {
			e1.printStackTrace();
			throw new RuntimeException("error while deployInstance " + e1);
		}
	}

}
