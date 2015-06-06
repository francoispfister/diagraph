 /**
 * Copyright (c) 2014 Laboratoire de Genie Informatique et Ingenierie de Production - Ecole des Mines d'Ales
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Blazo Nastov (ISOE-LGI2P) - initial API and implementation
 */
package org.isoe.diagraph.remoting;

import gary.lewandowski.RandomString;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecoretools.legacy.diagram.part.EcoreDiagramEditor;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.isoe.diagraph.views.ViewConstants;
import org.isoe.extensionpoint.xmi.IMetamodelRetriever;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.core.Separator;
import org.isoe.fwk.pathes.PathPreferences;

/**
 *
 * @author bnastov
 *
 */
import org.isoe.diagraph.controler.IDiagraphControler;  //FP140707refactored
public class DiagraphRemoting {

	private static final boolean LOG = false;

	private static final String DEFAULT_DIAGR_SUFFIX = ViewConstants.VIEW_SEPARATOR_0
			+ ViewConstants.DIAGRAPH_DEFAULT_VIEW_LITTERAL
			+ ViewConstants.VIEW_SEPARATOR_1
			+ ViewConstants.ROOT_NAME
			+ "_diagram";



	public static void load(String uri, boolean modelConfig,
			IDiagraphControler controler) {
		if (modelConfig)
			clog("loadInModelConfig");
		else
			clog("loadInLanguageConfig");
		try {
			loadMetamodelV2(uri, modelConfig, controler);
			loadModelsV2(uri, modelConfig, controler);
			if (modelConfig)
			   org.isoe.fwk.utils.debug.WorkspaceUtils.getInstance().refreshProject(PathPreferences.getPreferences()
						.getInstanceRepositoryLocation(),false);
			else
			   org.isoe.fwk.utils.debug.WorkspaceUtils.getInstance().refreshProject(PathPreferences.getPreferences()
						.getUniverseProjectName(),false);
		} catch (IOException e) {
			e.printStackTrace();
			controler.log("error in DiagraphRemoting.load", e.toString());
		}
	}

	private static void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////SAVING
	// DATA/////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static void saveInLanguageConfig(
			EPackage packag, EcoreDiagramEditor ecoreDiagramEditor_nu, String description,
			IDiagraphControler controler) {
		if (description == null || description.isEmpty())
			description = RandomString.generateStringAlpha(15);

		//EPackage pakag = (EPackage) ecoreDiagramEditor.getDiagram().getElement();
		try {
		URI uri = packag.eResource().getURI();
		URI ruri = CommonPlugin.resolve(uri);
		String MMPath = ruri.toFileString();

			// save the meta model
			saveMetamodel(MMPath, description, controler);
			saveModelsInLWB(packag, MMPath, description, controler); // FP130514
			// controler.log("m2-uploaded",pakag.getNsURI());
		} catch (IOException e) {
			controler.showMessage("you cannot work inside the repository; you should open the diagram within the plugin containing the language "+packag.getName());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// todo unifier deploy upload avec combo
	// FP130513 deploy local juste après init seconde instance

	public static void saveInModelConfig(DiagramEditor diagramEditor,
			String description, IDiagraphControler controler, IMetamodelRetriever mr) {
		if (description == null || description.isEmpty())
			description = RandomString.generateStringAlpha(15);
		// get the diagram
		EObject pack = diagramEditor.getDiagram().getElement();
		// get the corresponding meta model
	//	MetamodelRetriever mr = MetamodelRetriever.getRetriever_();
		EPackage pakag = mr.findRegisteredMetamodel(pack.eResource().getURI(), new NullProgressMonitor());
		String pakpath = getPathFromClass(pakag.getClass());
		if (pakpath.contains("/plugins/") && pakpath.endsWith(".jar")) {
			controler
					.showMessage(pakag.getName()
							+ " is already deployed and not under design, operation aborted; remove "
							+ pakpath);
			return;
		}
		String mMPath = pakpath + DParams.MODEL_FOLDER + "/" + mr.getDomain()
				+ ".ecore";
		if (pakag == EcorePackage.eINSTANCE) {
			clog("in a language config, model is a metamodel, => not saved ");
			return;
		}
		clog(mMPath);
		clog(mr.getCurrentFile().getLocationURI().getPath());
		try {
			// save the meta model
			saveMetamodel(mMPath, description, controler);
			// save the model
			String modelPath = mr.getCurrentFile().getLocationURI().getPath();
			saveModel(modelPath, mr.getNsURI(), description, controler);
		} catch (IOException e) {
			controler.showMessage(e.toString());
			e.printStackTrace();
		}
	}

	private static String getPathFromClass(Class c) {
		URL location = c.getProtectionDomain().getCodeSource().getLocation();
		return location.getFile();
	}

	private static String[] getModelsInRepository(EPackage pakag,
			String exclude, File[] files) {// TODO Optimize
		List<String> fs = new ArrayList<String>();
		for (File f : files) {

			String filename = f.getName();
			String extension = filename
					.substring(filename.lastIndexOf(".") + 1);
			if (extension.startsWith(pakag.getName()))
				fs.add(f.getName());
			/*
			 * if (f.getName().equals(INSTANCE_FOLDER)) {// REPOSITORY_DATA for
			 * (File sub : f.listFiles()) { String fn = sub.getName(); if
			 * (filterModelsInRepository(fn) && !sub.isDirectory()) { String op
			 * = sub.getName(); if (exclude == null || (exclude != null &&
			 * !op.equals(exclude))) fs.add(op); } } }
			 */
		}
		String[] vs = new String[fs.size()];
		int i = 0;
		for (String op : fs)
			vs[i++] = op;
		return vs;
	}





	private static void saveModelsInLWB(EPackage pakag, String metamodePath,
			String description, IDiagraphControler controler)
			throws IOException {
		// clog(PathPreferences.getPreferences().log());
		String path = PathPreferences.getPreferences().getUniverseProjectName()
				+ "/" + PathPreferences.getPreferences().getUniverseFolder();
		URI uri = URI.createPlatformResourceURI(path, false);
		String path_ = CommonPlugin.resolve(uri).toFileString();
		File foldr = new File(path_);
		clog(foldr.getAbsolutePath());
		if (foldr.isDirectory()) {
			File[] files = foldr.listFiles();
			if (files != null) {
				String[] vs = getModelsInRepository(pakag, null, files);
				for (String mp : vs) {
					String modelPath = path_ + File.separator + mp;
					if (!modelPath.endsWith(DEFAULT_DIAGR_SUFFIX) && !modelPath.endsWith(".ecore")&& !modelPath.endsWith(".ecorediag")) {
						String modelContent = readFile(modelPath);
						String diagramContent = "";
						File diag = new File(modelContent
								+ DEFAULT_DIAGR_SUFFIX);
						if (diag.exists())
							diagramContent = readFile(modelContent
									+ DEFAULT_DIAGR_SUFFIX);
						clog("uploading "+modelPath);
						save(modelPath, pakag.getNsURI(), description,
								controler, modelContent, diagramContent);
					}
				}
			}
		}
		clog(foldr.getAbsolutePath());
	}

	private static void saveModel(String modelPath, String nsUri,
			String description, IDiagraphControler controler)
			throws IOException {
		String modelContent = readFile(modelPath);
		String diagramContent = readFile(modelPath + DEFAULT_DIAGR_SUFFIX);// "_default_root_diagram");
		save(modelPath, nsUri, description, controler, modelContent,
				diagramContent);
		// TODO: save icons
	}

	private static void save(String modelPath, String nsUri,
			String description, IDiagraphControler controler,
			String modelContent, String diagramContent) throws IOException {
		File f = new File(modelPath);
		String fileName = f.getName().substring(0, f.getName().indexOf('.'));
		if (RESTApiCaller.callPutModelAction(nsUri, fileName, description,
				modelContent, diagramContent) != null) {
			clog("Model uploaded");
			controler.log("dg-remoting-save-model", "model " + nsUri + " ("
					+ description + ") uploaded");
		} else {
			clog("Model uploading has failed");
			controler.log("dg-remoting-save-model", "model " + nsUri + " ("
					+ description + ") uploading has failed");
		}
		// TODO: save icons
	}

	private static void saveMetamodel(String MMPath, String description,
			IDiagraphControler controler) throws IOException {
		EPackage pack = ((EPackage) loadMetamodel(MMPath).getContents().get(0));
		String nsUri = pack.getNsURI();
		String nsPrefix = pack.getNsPrefix();
		String name = pack.getName();

		String ecorePath = getAbsolutePathFromPackage(pack);
		File ecoreFile = new File(ecorePath);

		String ecorediagPath = ecoreFile.getParentFile().getAbsolutePath()
				+ File.separator + name + ".ecorediag";

		String ecoreContent = readFile(ecorePath);
		String ecorediagContent = readFile(ecorediagPath);

		clog("Meta-model content : " + ecoreContent);
		clog("Ecore Diagram content : " + ecorediagContent);

		String result = RESTApiCaller.callPutAction(nsUri, nsPrefix, name,
				description, ecoreContent, ecorediagContent);

		if (result != null) {
			clog("Meta-model Uploaded");
			controler.log("dg-remoting-save-meta-model", "metamodel " + nsUri
					+ " (" + description + ") uploaded");
		} else {
			clog("Meta-model allready exists in the remote repository");
			controler.log("dg-remoting-save-meta-model", "metamodel " + nsUri
					+ " (" + description
					+ ") allready exists in the remote repository");
		}
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////LOADING
	// DATA/////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////

	private static void loadModelsV2(String uri, boolean isModelConfig,
			IDiagraphControler controler) throws IOException {
		// get the name
		String name = RESTApiCaller.callGetNameByUriAction(uri);
		// get the contents
		ArrayList<ArrayList<String>> contents = RESTApiCaller
				.callGetAllModelContentByUriAction(uri);
		if (contents == null) {
			clog("No models available for : " + name);
			controler.log("dg-remoting-load-model",
					"No models available for : " + name);
			return;
		}
		boolean alablazo = false;
		String projectPath = getInstanceDeploymentRepoPath(isModelConfig);
		File repo = null;
		if (isModelConfig)
			repo = new File(projectPath
					+ (alablazo ? (File.separator + name) : ""));
		else
			repo = new File(projectPath);

		if (!repo.exists()) {
			if (repo.mkdir()) {
				clog("Local repository created");
				controler.log("dg-remoting-load-model", "repository created");
			} else {
				clog("Unable to create the local repository: " + repo);
				controler.log("dg-remoting-load-model",
						"failed repository creation");
				return;
			}
		} else {
			clog("the local repository allready exists: " + repo);
			controler.log("dg-remoting-load-model",
					"repository allready exists");
		}

		// File model,diagram;
		for (int i = 0; i < contents.size(); i++) {
			String modelFilename = contents.get(i).get(2) + "." + name;
			String diagramFilename = contents.get(i).get(2) + "." + name
					+ "_default_root_diagram";
			File model = new File(repo.getAbsolutePath() + File.separator
					+ modelFilename);
			File diagram = new File(repo.getAbsolutePath() + File.separator
					+ diagramFilename);

			if (!model.exists()) {
				model.createNewFile();
			}
			if (!diagram.exists()) {
				diagram.createNewFile();
			}

			// Writing
			PrintWriter modelOut = new PrintWriter(model.getAbsolutePath());
			PrintWriter diagramOut = new PrintWriter(diagram.getAbsolutePath());

			modelOut.print(contents.get(i).get(0));
			diagramOut.print(contents.get(i).get(1));

			modelOut.close();
			diagramOut.close();

		}
	}

	private static void loadMetamodelV2(String uri, boolean isModelConfig,
			IDiagraphControler controler) throws IOException {
		// Getting the data
		ArrayList<ArrayList<String>> contents = RESTApiCaller
				.callGetAllMetaModelContentByUriAction(uri);
		if (contents == null) {
			clog("error: contents==null for " + uri); // FP130508
			return;
		}
		// get the name
		String name = contents.get(0).get(2);
		// Resolving path ans see if files already exists
		String path = getMegaModelDeploymentRepoPath(isModelConfig);

		// File ecore, ecorediag;
		File ecore = new File(path + "/" + name + ".ecore");
		File ecorediag = new File(path + "/" + name + ".ecorediag");

		if (ecore.exists() || ecorediag.exists()) {
			clog("Meta-model already exists in the mega-model repository");
			controler.log("dg-remoting-load-meta-model", "already exists");
			return;
		}

		String ecoreContent = contents.get(0).get(0);
		String ecorediagContent = contents.get(0).get(1);

		// clog(ecoreContent);
		// clog(ecorediagContent);
		// clog(name);

		if (ecoreContent != null && ecorediagContent != null && name != null) {
			// creating files
			ecore.createNewFile();
			ecorediag.createNewFile();

			// Writing
			PrintWriter ecoreOut = new PrintWriter(path + "/" + name + ".ecore");
			PrintWriter ecorediagOut = new PrintWriter(path + "/" + name
					+ ".ecorediag");

			ecoreOut.print(ecoreContent);
			ecorediagOut.print(ecorediagContent);

			ecoreOut.close();
			ecorediagOut.close();
			controler.log("dg-remoting-load-meta-model", name + " saved");

		} else {
			clog("Data not found");
			controler.log("dg-remoting-load-meta-model", "not found");
		}

	}

	public static Resource loadMetamodel(String metamodelPath) {
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

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////UTILITIE/////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////

	private static String readFile(String path) {
		String result = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String strLine = null;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				result += strLine;
			}
			// Close the input stream
			br.close();

		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		return result;
	}

	private static String getAbsolutePathFromPackage(EPackage pack) {
		URI fileURI = CommonPlugin.resolve(pack.eResource().getURI());
		return fileURI.toFileString();
	}

	private static String getMegaModelDeploymentRepoPath(boolean isModelConfig) {
		String loc = PathPreferences.getPreferences().getUniverseProjectName()
				+ "/" + PathPreferences.getPreferences().getUniverseFolder();

		URI uri = null;
		if (isModelConfig)
			uri = URI.createPlatformPluginURI(loc, false);
		else
			uri = URI.createPlatformResourceURI(loc, false);
		URI curi = CommonPlugin.resolve(uri);
		File f = new File(curi.toFileString());
		return f.getAbsolutePath();
	}

	private static String getInstanceDeploymentRepoPath(boolean isModelConfig) {
		String wsroot = ResourcesPlugin.getWorkspace().getRoot().getLocation()
				.toFile().getAbsolutePath();
		if (isModelConfig) {
			String instanceRepositoryProj = PathPreferences.getPreferences()
					.getInstanceRepositoryPath()
					.replaceAll("/", Separator.SEPARATOR);// "\\isoe.instances_models\\instances";
			String instanceRepositoryProject = wsroot + instanceRepositoryProj;// "\\isoe.instances_models\\instances";
			return instanceRepositoryProject;
		} else {
			String universeprojProj = PathPreferences.getPreferences()
					.getUniverseProjectName()
					+ "/"
					+ PathPreferences.getPreferences().getUniverseFolder();
			universeprojProj = universeprojProj.replaceAll("/",
					Separator.SEPARATOR);
			String universeRepositoryProject = wsroot + File.separator
					+ universeprojProj;
			return universeRepositoryProject;
		}
	}

	public static void save(EPackage packag, DiagramEditor diagramEditor,
			String modelDescription, boolean modelConfig,
			IDiagraphControler controler,IMetamodelRetriever mr) {
		if (modelConfig)
			saveInModelConfig(diagramEditor, modelDescription, controler, mr);
		else
			saveInLanguageConfig(packag,(EcoreDiagramEditor) diagramEditor,
					modelDescription, controler);
	}

}
