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
package org.isoe.fwk.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jdt.internal.core.JavaProject;
import org.isoe.diagraph.preferences.DiagraphPreferences;
import org.isoe.fwk.core.DParams;
import org.osgi.framework.Bundle;

/**
 *
 * @author pfister
 *
 */

public class ResourceUtils {

	public static final String PLUGIN_ID = "org.isoe.fwk.utils";
	private static final boolean LOG = DParams.ResourceUtils_LOG;
	private static final String ECORETOOLSEDITINGDOMAIN_ID = "org.eclipse.emf.ecoretools.legacy.diagram.EditingDomain";
	private static final String ECORETOOLSEDITINGDOMAIN_PREFIX = ECORETOOLSEDITINGDOMAIN_ID
			+ "@";
	private static final int ECORETOOLSEDITINGDOMAIN_PREFIX_LENGTH = ECORETOOLSEDITINGDOMAIN_PREFIX
			.length();

	private static Bundle bundle;

	/*
	 * public static String REPLACE_SEPARATOR; static { REPLACE_SEPARATOR =
	 * File.separator; if (REPLACE_SEPARATOR.equals("\\")) REPLACE_SEPARATOR =
	 * "\\\\"; }
	 */

	public static String getWorkspaceDirectory() {
		// if (deployed)
		return getDWorkspaceDirectory();
		// else
		// return getNDWorkspaceDirectory_();
	}

	public static List<String> getPluginProjects() {
		// if (deployed)
		return getOpenedProjects();
		// else
		// return getPluginNDProjects();
	}

	public static List<String> getOpenedProjects() {
		List<String> prjs = new ArrayList<String>();
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot wsroot = workspace.getRoot();
		IProject[] projects = wsroot.getProjects();
		for (IProject project : projects) {
			if (project.isOpen())
				prjs.add(project.getName());
		}
		return prjs;
	}

	public static IProject getOpenedProject(String name) {
		List<String> prjs = new ArrayList<String>();
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot wsroot = workspace.getRoot();
		IProject[] projects = wsroot.getProjects();
		for (IProject project : projects) {
			if (project.isOpen()){
				try {
					String prjname=project.getName();
					String lastsegment = prjname
							.substring(prjname.lastIndexOf(".") + 1);
					if (lastsegment.equals(name))
						return project;
				} catch (Exception e) {
					//System.err.println("error while getOpenedProject " + project);
				}
			}
		}
		return null;
	}

	public static List<IProject> getWorkspaceProjects() {
		List<IProject> prjs = new ArrayList<IProject>();
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot wsroot = workspace.getRoot();
		IProject[] projects = wsroot.getProjects();
		for (IProject project : projects) {
			if (project.isOpen())
				prjs.add(project);
		}
		return prjs;
	}

	private static List<String> getPluginNDProjects() {
		List<String> projects = new ArrayList<String>();
		try {
			File pluginsDirectory = new File(getPluginFilePath())
					.getParentFile();
			File[] files = pluginsDirectory.listFiles();
			for (int i = 0; i < files.length; i++)
				if (files[i].isDirectory()) {
					File[] subfiles = files[i].listFiles();
					for (File file : subfiles) {
						if (file.getName().equals(".project")) {
							projects.add(files[i].getName());
							break;
						}
					}
				}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return projects;
	}

	private static String getDWorkspaceDirectory() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot wsroot = workspace.getRoot();
		IPath loc = wsroot.getLocation();
		String worspaceRoot = loc.toString();
		File workspaceDirectory = null;
		workspaceDirectory = new File(worspaceRoot);
		if (LOG)
			clog("GDWSD " + worspaceRoot);
		return workspaceDirectory.getAbsolutePath();
	}

	private static String getNDWorkspaceDirectory_obs() {
		try {
			return new File(getPluginFilePath()).getParentFile()
					.getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	// org.isoe.fwk.utils.getPluginFilePath
	public static String getPluginFilePath() throws IOException {
		String className = ResourceUtils.class.getName();
		int index = className.lastIndexOf(".");
		String claz = index == -1 ? className : className.substring(index + 1)
				+ ".class";
		URL ur = ResourceUtils.class.getResource(claz);
		String furl = ur.toString();
		if (furl.startsWith("file:/"))
			furl = furl.substring("file:/".length());
		if (EMFPlugin.IS_ECLIPSE_RUNNING) {
			URL url = FileLocator.toFileURL(ur);
			furl = FileLocator.toFileURL(url).getFile();
		}
		furl = furl.substring(0, furl.indexOf(PLUGIN_ID) + PLUGIN_ID.length())
				+ "/";
		File f = new File(furl);
		return f.toString();
	}

	private static String getPluginDirectory(String pluginid)
			throws IOException {
		File pluginsDirectory = new File(getPluginFilePath()).getParentFile();
		File[] plugins = pluginsDirectory.listFiles();
		for (int i = 0, maxi = plugins.length; i < maxi; i++) {
			if (plugins[i].isDirectory()) {
				String name = plugins[i].getName();
				if (name.equals(pluginid)) {
					return plugins[i].getAbsolutePath();
				}
			}
		}
		throw new RuntimeException("Unable to find the " + pluginid
				+ " directory");
	}

	public static Resource loadModel(ResourceSet resourceSet, URI modelUri,
			String nsUri, EPackage pakag) throws IOException {
		loadhook(modelUri);
		if (!EMFPlugin.IS_ECLIPSE_RUNNING) {
			resourceSet
					.getResourceFactoryRegistry()
					.getExtensionToFactoryMap()
					.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
							new XMIResourceFactoryImpl());
			resourceSet.getPackageRegistry().put(nsUri, pakag);
		}
		Resource res = resourceSet.getResource(modelUri, true);
		return res;
	}

	private static void loadhook(URI uri) {
		// Resource result = null;
		// String mname = uri.toString().substring(
		// uri.toString().lastIndexOf("/") + 1);
	}

	public static Resource loadMetamodel(ResourceSet metamodelSet,
			URI metamodelURI) {
		Resource result = null;
		loadhook(metamodelURI);
		try {
			metamodelSet
					.getResourceFactoryRegistry()
					.getExtensionToFactoryMap()
					.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
							new EcoreResourceFactoryImpl());
			result = metamodelSet.getResource(metamodelURI, true);
		} catch (Exception e) {
			clog(metamodelURI.toFileString());
		}
		return result;
	}

	public static EPackage loadMetamodel(ResourceSet resourceSet,
			String plugin, String folder, String modelName, String nsUri,
			EPackage pakage) {
		URI uri = null;
		if (!EMFPlugin.IS_ECLIPSE_RUNNING) {
			File f = new File(".");
			String fp = f.getAbsolutePath();
			fp = fp.substring(0, fp.length() - 2);
			fp = fp.substring(0, fp.lastIndexOf(File.separator));
			fp += File.separator + plugin + File.separator + folder
					+ File.separator + modelName + ".ecore";
			uri = URI.createFileURI(fp);
		} else
			uri = URI.createURI("platform:/resource/" + plugin + "/" + folder
					+ "/" + modelName + ".ecore");
		resourceSet.getPackageRegistry().put(nsUri, pakage);
		Resource resource = resourceSet.getResource(uri, true);
		if (resource != null)
			return (EPackage) resource.getContents().get(0);
		else
			return null;
	}

	public static URI createURI(String plugin, String folder, String modelName,
			String nsUri, EPackage pakage) {
		if (!EMFPlugin.IS_ECLIPSE_RUNNING) {
			File f = new File(".");
			String fp = f.getAbsolutePath();
			fp = fp.substring(0, fp.length() - 2);
			fp = fp.substring(0, fp.lastIndexOf(File.separator));
			fp += File.separator + plugin + File.separator + folder
					+ File.separator + modelName + ".ecore";
			return URI.createFileURI(fp);
		} else
			return URI.createURI("platform:/resource/" + plugin + "/" + folder
					+ "/" + modelName + ".ecore");
	}

	public static URI createFileUri(String modelPath) {
		try {
			String pluginid = modelPath.substring(0, modelPath.indexOf("/"));
			modelPath = modelPath.substring(modelPath.indexOf("/") + 1);
			String targetpath = getPluginDirectory(pluginid);
			URI uri = URI.createFileURI(targetpath + "/" + modelPath);
			return uri;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static void registerNsUri(ResourceSet modelSet, Resource metamodel) {
		Iterator<EObject> i = metamodel.getAllContents();
		EPackage p = (EPackage) i.next();
		modelSet.getPackageRegistry().put(p.getNsURI(), p);
	}

	public static Resource loadModel(ResourceSet rs, Resource metamodel,
			URI modelURI) {
		loadhook(modelURI);
		registerNsUri(rs, metamodel);
		return rs.getResource(modelURI, true);
	}

	public static Resource loadNotEmptyModel(String absolutePath_,
			String metamodelNsUri, EPackage metamodelPackage) { // FP120529
		;
		//if (absolutePath_ !=null && absolutePath_.endsWith(".diagraph"))
		//	 tb = true;

		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet
				.getResourceFactoryRegistry()
				.getExtensionToFactoryMap()
				.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
						new XMIResourceFactoryImpl());
		resourceSet.getPackageRegistry().put(metamodelNsUri, metamodelPackage);
		File file = new File(absolutePath_);


		URI uri = file.isFile() ? URI.createFileURI(file.getAbsolutePath())
				: URI.createURI(absolutePath_);
		try {
			Resource resource = resourceSet.getResource(uri, true);
			if (LOG)
				clog("Loaded " + uri);
			for (Iterator j = resource.getContents().iterator(); j.hasNext();) {
				EObject eObject = (EObject) j.next();
				Diagnostic diagnostic = Diagnostician.INSTANCE
						.validate(eObject);
				if (diagnostic.getSeverity() != Diagnostic.OK) {
					System.err.println("error " + diagnostic);
					throw new RuntimeException("validation error in  "
							+ metamodelPackage.getName() + "."
							+ eObject.getClass().getSimpleName() + " - "
							+ diagnostic + " !!!"); // FP121214
				}
			}
			return resource;
		} catch (RuntimeException exception) {
			throwException(exception); // FP160617
			return null;
		}
		// return null;
	}

	private static void throwException(RuntimeException exception) {
		try {
			if (exception.getMessage().contains("FeatureNotFoundException")
					|| exception.getMessage().contains(
							"PackageNotFoundException"))
				throw new RuntimeException(
						"(1) Diagraph metamodel has changed!!! delete all .diagraph files or force regeneration !!!"); // FP121124x
			else if (exception.getMessage().contains("The required feature")
					&& exception.getMessage().contains("must be set")) {
				String msg = exception.getMessage();
				int p = msg.indexOf("The required feature '");
				String miss = msg.substring(p + 22);
				p = miss.indexOf("' of");
				miss = miss.substring(0, p);
				throw new RuntimeException("The required feature " + miss
						+ " must be set"); // FP121124x
			} else
				throw new RuntimeException("unknown error "
						+ exception.getMessage()); // FP121124x
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public static String modelExists(String projectName, String modelFolder,
			String modelName) { // FP120528
		IProject project = null;
		String prjPath = null;
		String modelPath = null;
		File modelFile = null;

		try {
			project = ResourcesPlugin.getWorkspace().getRoot()
					.getProject(projectName);
			prjPath = project.getLocation().toOSString();
			modelPath = prjPath + File.separator + modelFolder + File.separator
					+ modelName;
			modelFile = new File(modelPath);
			if (modelFile.exists())
				return modelPath;
			else {
				// if (LOG)
				//System.err.println("MNE - no resource " + modelPath);
				return null;
			}
		} catch (Exception e) {
			if (prjPath == null)
				throw new RuntimeException("unable to find project  "
						+ projectName + " !!!!"); // FP120929 //FP120724
			else
				throw new RuntimeException("error with file " + modelName
						+ " !!!!"); // FP120929 //FP120724
		}

	}

	public static Resource getResource(ResourceSet rs, URI metaModel, URI model) {
		Resource mr = loadModel(rs, loadMetamodel(rs, metaModel), model);
		return mr;
	}

	private static String dirEnd(String dir) {
		String x = dir.substring(0, dir.lastIndexOf(File.separator));
		String y = dir.substring(0, x.lastIndexOf(File.separator));
		return dir.substring(y.substring(0, y.lastIndexOf(File.separator))
				.length() + 1);
	}

	static Resource save_(URI uri, EObject toSave) throws IOException {
		new File(uri.toFileString()).delete();
		Resource resource = new XMIResourceFactoryImpl().createResource(uri);
		resource.getContents().add(toSave);
		Map<String, String> options = new HashMap<String, String>();
		options.put(XMLResource.OPTION_ENCODING, "UTF-8");
		resource.save(options); // FP140225

		if (LOG) {
			String d = dirEnd(uri.toFileString());
			clog(d + " saved ok(5)");
		}

		if (DParams.TextGen6_LOG) {
			String d = dirEnd(uri.toFileString());
			if (d.endsWith(".diagraph"))
				clog6(d + " (diagraph saved)");
		}

		return resource;
	}

	private static void clog6(String mesg) {
		if (DParams.TextGen6_LOG)
			System.out.println("" + mesg);
	}

	public static IFolder createIfNotExists(String projectName, String folder) {
		IFolder iFolder = getFolder(projectName, folder);
		if (LOG)
			clog("createIfNotExists: " + folder);
		try {
			if (!iFolder.exists())
				iFolder.create(true, true, new NullProgressMonitor());
		} catch (CoreException e) {
			throw new RuntimeException(e);
		}
		return iFolder;
	}

	public static IFolder getFolder(String projectName, String folder) {
		IWorkspaceRoot wsroot = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = wsroot.getProject(projectName);
		IFolder iFolder = project.getFolder(folder);
		return iFolder;
	}

	private static void copy(File source, String targetpath) {
		File target = null;
		InputStream in = null;
		OutputStream out = null;
		try {
			target = new File(targetpath);
			in = new FileInputStream(source);
			out = new FileOutputStream(target);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0)
				out.write(buf, 0, len);
			in.close();
			out.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			if (out == null) {
				String msg = "error with target file "
						+ target.getAbsolutePath();
				throw new RuntimeException(msg);
			} else
				throw new RuntimeException(ex);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static void duplicate(String sourcePath, String targetPath) {
		File dir = new File(sourcePath);
		File[] lfiles = dir.listFiles();
		List<File> files = Arrays.asList(lfiles);
		for (File file : files) {
			String target = targetPath
					+ file.getAbsolutePath().substring(sourcePath.length());
			if (!file.isFile()) {
				if (file.getName().equals(".svn"))
					clog((file.getAbsolutePath() + " - svn data not handled")); // FP120401
				else {
					File folder = new File(target);
					if (!folder.exists())
						folder.mkdir();
					duplicate(sourcePath, targetPath);
				}
			} else
				copy(file, target);
		}
	}

	public static void duplicateSameDir(File dir, String sourcePath,
			String targetPath) {
		File[] lfiles = dir.listFiles();
		List<File> files = Arrays.asList(lfiles);
		for (File file : files) {
			String target = targetPath
					+ file.getAbsolutePath().substring(sourcePath.length());
			target = target.replaceAll("\\\\", "/"); // win //FP121017b
			target = target.replaceAll("//", "/"); // linux cleanup please
			if (!file.isFile()) {
				if (!file.getName().equals(".svn")) {
					File folder = new File(target);
					if (!folder.exists())
						folder.mkdir();
					duplicateSameDir(file, sourcePath, targetPath);
				}
			} else
				copy(file, target);
		}
	}

	public static boolean projectExists(String projectName) { // FP130404bb
		IWorkspace ws = ResourcesPlugin.getWorkspace();
		try {
			ws.getRoot().refreshLocal(IResource.DEPTH_INFINITE,
					new NullProgressMonitor());
		} catch (CoreException e) {
			e.printStackTrace();
			System.err.println(e.toString());
		}
		IProject p = ws.getRoot().getProject(projectName);
		boolean result = p.exists();
		return result;
	}

	public static IProject getProject(String projectName) { // FP130404bb
		IWorkspace ws = ResourcesPlugin.getWorkspace();
		try {
			ws.getRoot().refreshLocal(IResource.DEPTH_INFINITE,
					new NullProgressMonitor());
		} catch (CoreException e) {
			System.err.println(e.toString());
		}
		IProject p = ws.getRoot().getProject(projectName);
		boolean result = p.exists();
		if (result)
			return p;
		else
			return null;
	}

	public static void copyFolder(String projectName, String projectPath,
			String workPath, String sourcefolder, String targetFolder) {
		String separator = "/"; // cleanup please
		if (sourcefolder.contains(File.separator))
			separator = File.separator;
		IFolder workFolder = getFolder(projectName, workPath);
		createIfNotExists(projectName, targetFolder);
		sourcefolder = sourcefolder + separator;
		sourcefolder = sourcefolder.replaceAll("\\\\", "/"); // win
		sourcefolder = sourcefolder.replaceAll("//", "/"); // linux //FP121017b
		String targFolder = projectPath + separator + targetFolder + separator;
		String wfl = workFolder.getLocation().toOSString();
		duplicateSameDir(new File(wfl), sourcefolder, targFolder);
	}

	public static IFile getFile(IProject project, String modelPath,
			String extension) {
		IFile fil = project.getFile(modelPath + "." + extension);
		String pat = fil.getFullPath().toString();
		File found = new File(ResourcesPlugin.getWorkspace().getRoot()
				.getLocation().toString()
				+ pat);
		if (!found.exists()) {
			System.err.println("no file " + found.getAbsolutePath()); // FP120523
			return null;
		}
		return fil;
	}

	private static void clog(String mesg) {
		if (LOG)
			System.out.println("" + mesg);
	}

	/*************************     //FP140706        ************************************/

	public static boolean isDiagraphedM2(EPackage pakage) { //FP140706
		String mesg = "diagram for package " + pakage.getName();
		//String mm=pakage.eResource().getURI().toString();//file:/C:/workspaces/june315/lang.m2.simplestatechart/model/simplestatechart.ecore
        //String w="/"+DiagraphPreferences.getTeamNamespace()+"."+pakage.getName();
		if (pakage.eResource().getURI().toString().contains("/"+DiagraphPreferences.getTeamNamespace()+"."+pakage.getName()))
		//if (pakage.eResource().getURI().toString().contains("platform:/resource/"+DiagraphPreferences.getTeamNamespace()+"."+pakage.getName()))

		{ //FP140706
		  for (EClassifier eClassifier : pakage.getEClassifiers()) {
			EAnnotation diagraphannot = eClassifier
					.getEAnnotation("diagraph");
			if (diagraphannot != null)
				return true;
		  }
		}
		clog(mesg + " not diagraphed");
		return false;
	}

	public static boolean isProjectWellNamed(EPackage pakage) {
		Resource r= pakage.eResource();
		URI suri=null;
		if (r!=null){
		   suri=r.getURI();
		   clog(r.getURI().toString());
		}
		String uri = pakage.eResource().getURI().toString();
		String prjsuf = getProjectSuffix(uri);
		if (prjsuf == null)
			return false;
		else if (!prjsuf.equals(getEcoreName(uri))) {
			String msg = null;
			if (pakage.getName().isEmpty() || pakage.getName() == null)
				msg = "package without name in the ecore file";
			else {
				prjsuf = getProjectSuffix(pakage.eResource().getURI()
						.toString());
				if (prjsuf == null)
					return false;
				msg = "1)project: " + prjsuf + "   2)ecore: "
						+ getEcoreName(pakage.eResource().getURI().toString())
						+ "   3)package: " + pakage.getName()
						+ "    - Naming convention: the three must match ";
			}

			if (LOG)
				clog(msg);
			return false;
		} else
			return true;
	}

	private static String getEcoreName(String uri) {
		return uri.substring(uri.lastIndexOf("/") + 1, uri.length()
				- DParams.ECORE_SUFX_LEN);
	}

	private static String getProjectSuffix(String uri) {
		try {
			String projectSuffix = uri.substring(0,
					uri.lastIndexOf(DParams.SEP_MODEL_FOLDER_SEP)); // /model/
			return projectSuffix.substring(projectSuffix.lastIndexOf(".") + 1);
		} catch (Exception e) {
			return null;
		}
	}

	public static String[] getProjectLocation(IProject prj) {
		String[] result = new String[2];
		String langname = prj.getLocation().lastSegment();
		langname = langname.substring(langname.lastIndexOf(".")+1);
		String projectPath = prj.getLocation().toFile().getAbsolutePath();
		String modelFolderPath = projectPath+File.separator+DParams.MODEL_FOLDER+File.separator;
		result[0] = modelFolderPath;
		result[1] = prj.getLocation().lastSegment();
		return result;
	}

	public static List<String> getDiagraphProjectNames() { // TODO find a better method
		List<String> result = new ArrayList<String>();
		List<String> prjs = getOpenedProjects();
		for (String prj : prjs) {
			if (!prj.equals("_megamodel")){
				try {
					prj = prj.substring(prj.lastIndexOf(".") + 1);
					if (!prj.equals("edit") && !prj.equals("editor")
							&& !prj.equals("tests")
							&& !prj.startsWith("diagram")){
						EPackage p = getPackage(prj); //FP140705
						if (p!=null)
						  if (isDiagraphedM2(p))
						    result.add(prj);
					}

				} catch (Exception e) {
					cerror("error while getting projects " + prj);
				}
			}
		}
		return result;
	}



	private static void cerror(String mesg) {
		System.err.println(mesg);

	}

	public static EPackage getPackage(String prj) { //FP140705
	 try {
		IProject p = getOpenedProject(prj);
		IFolder f = p.getFolder("model");
		IFile m2 = f.getFile(prj+".ecore");
		java.net.URI m2uri= m2.getLocationURI();
		String modelPath = m2uri.getPath();
		ResourceSet resourceSet = new ResourceSetImpl();
		URI uri = URI.createFileURI(modelPath);
		EPackage mmodel = (EPackage) ResourceUtils
				.loadMetamodel(resourceSet, uri).getContents().get(0);
		return mmodel;
	 } catch (Exception e) {
		return null;
	 }
	}


	public static  String getDiagraphProjectName(IProject project) {
		if (project.isAccessible()) {
			IPath path = project.getFullPath();
			if (DParams.LanguageTransformer_4_LOG)
				clog("AKW projectpath=" + path.toPortableString());
			if (path.toFile().getAbsolutePath()
					.contains(DiagraphPreferences.getTeamNamespace() + ".")) {
				try {
					String prjn = project.getName().substring(
							project.getName().lastIndexOf(".") + 1);
					EPackage p = getPackage(prjn); // FP140705
					if (isDiagraphedM2(p)) {
						if (DParams.LanguageTransformer_4_LOG)
							clog("***  AKW lang=" + p.getName());
						return p.getName();
					}
				} catch (Exception e) {

				}
			}
		}
		return null;
	}


	public static List<IProject> getDiagraphProjects() {  // TODO find a better method
		List<IProject> result = new ArrayList<IProject>();
		List<IProject> prjs = ResourceUtils.getWorkspaceProjects();
		for (IProject prj : prjs) {
			if (!prj.getName().equals("_megamodel")) {
				try {
					String pn= getDiagraphProjectName(prj);
					if (pn!=null){

					//String prjn = prj.getName().substring(prj.getName().lastIndexOf(".") + 1);
				//	if (!prjn.equals("edit") && !prjn.equals("editor")&& !prjn.equals("tests") && !prjn.startsWith("diagram"))
				//	{
						EPackage p = getPackage(pn); //FP140705
						if (p!=null)
						  if (isDiagraphedM2(p))
						    result.add(prj);
				//	}
					}
				} catch (Exception e) {
					cerror("error while getting projects " + prj.getName());
				}
			}
		}
		return result;
	}


	public static String getDiagraphLanguageName_(JavaProject javaProject) {

		IProject project = javaProject.getProject();
		if (project.isAccessible()) {
			IPath path = javaProject.getPath();
			if (DParams.LanguageTransformer_4_LOG)
				clog("AKW projectpath=" + path.toPortableString());
			if (path.toFile().getAbsolutePath()
					.contains(DiagraphPreferences.getTeamNamespace() + ".")) {
				try {
					String prjn = project.getName().substring(
							project.getName().lastIndexOf(".") + 1);
					if (prjn.equals("edit") || prjn.equals("editor")
							|| prjn.equals("tests")
							|| prjn.startsWith("diagram")){
						prjn=  project.getName();
						prjn =  prjn.substring(DiagraphPreferences.getTeamNamespace().length()+1);
						prjn =  prjn.substring(0,prjn.lastIndexOf("."));
					}
					EPackage p = getPackage(prjn); // FP140705
					if (isDiagraphedM2(p)) {
						if (DParams.LanguageTransformer_4_LOG)
							clog("***  AKW lang=" + p.getName());
						return p.getName();
					}
				} catch (Exception e) {

				}
			}
		}
		return null;
	}




	public static EPackage getPackage(JavaProject javaProject) {

		IProject project = javaProject.getProject();
		if (project.isAccessible()) {
			IPath path = javaProject.getPath();
			if (DParams.LanguageTransformer_4_LOG)
				clog("AKW projectpath=" + path.toPortableString());
			if (path.toFile().getAbsolutePath()
					.contains(DiagraphPreferences.getTeamNamespace() + ".")) {
				try {
					String prjn = project.getName().substring(
							project.getName().lastIndexOf(".") + 1);
					if (prjn.equals("edit") || prjn.equals("editor")
							|| prjn.equals("tests")
							|| prjn.startsWith("diagram")){
						prjn=  project.getName();
						prjn =  prjn.substring(DiagraphPreferences.getTeamNamespace().length()+1);
						prjn =  prjn.substring(0,prjn.lastIndexOf("."));
					}
					return getPackage(prjn); // FP140705

				} catch (Exception e) {

				}
			}
		}
		return null;
	}



	public static boolean checkDiagraphConsistence(TransactionalEditingDomain editingDomain,EPackage pakage) {
		// model must be under model/ error while checking
		try {
			boolean result = isAnEcoreDiagram(editingDomain);
			result = result && isUnderModelFolder(editingDomain);
			result = result && isProjectWellNamed(pakage);
			result = result && isDiagraphedM2(pakage);
			return result;
		} catch (Exception e) {
			cerror(e.toString());
			return false;
		}
	}


	private static boolean isAnEcoreDiagram(TransactionalEditingDomain editingDomain) {
		if (!editingDomain.getID().startsWith(ECORETOOLSEDITINGDOMAIN_ID)) {
			String msg = "must be an Ecore Diagram ";
			cerror(msg);
			return false;
		}
		return true;
	}

	private static boolean isUnderModelFolder(TransactionalEditingDomain editingDomain) {
		String abspath = editingDomain.getID().substring(
				ECORETOOLSEDITINGDOMAIN_PREFIX_LENGTH);
		String pref = abspath.substring(0, abspath.lastIndexOf("/"));
		String modelFolder = pref.substring(pref.lastIndexOf("/") + 1);
		if (!modelFolder.equals(DParams.MODEL_FOLDER)) {
			String msg = "(1) model must be under " + DParams.MODEL_FOLDER
					+ "/ but is currently under " + modelFolder + "/";
			cerror(msg);
			return false;
		}
		return true;
	}



}
