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

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.isoe.fwk.core.DParams;



/**
 *
 * @author pfister
 *
 */
public class ResourceManager {

	private static final boolean CHECK = true;
	private static final boolean LOG = DParams.ResourceManager_LOG;
	//private static final boolean LOG_GEN = true;

	public final static int RSRC_ABSOLUTE_ = 0;
	public final static int RSRC_DIRECTORY = 1;
	public final static int RSRC_MODEL = 2;
	public final static int RSRC_WORKSPACE_ = 3;
	public final static int RSRC_PROJECT = 4;
	public final static int RSRC_PLUGIN = 5;
	public final static int RSRC_PATH_ = 6;
	public final static int RSRC_MODEL_PATH = 6; // D:\pfister\workspaces\ws-isoe-gmf-1-02\org.isoe.diagraph.test.refinher3\model\refinher3
	public final static int RSRC_CHECKSUM_0 = 7;
	public final static int RSRC_CHECKSUM_1 = 8;
	public final static int RSRC_CHECKSUM_2 = 9;
	public final static int RSRC_TARGET_FOLDER = 10; //
	public static final int RSRC_SAVE_FOLDER = 11;

	public final static String  DIAGRAM_EXT = ".diagram";

	public final static String[] RELATED_PROJECT_SUFFIXES={".edit",".editor",DIAGRAM_EXT,".tests"};



	//targetDiagramPlugin_=targetDiagramPlugin_+DIAGRAM_EXT+Style_Keywords.VIEW_SEPARATOR_0+layer+Style_Keywords.VIEW_SEPARATOR_1+Style_Keywords.ROOT_NAME_;

	private static boolean changeTargetFolder;
	private static boolean versionChanged;



	/*------------------directory && file  deletion -------------------------------------*/


	private static void deleteDomainSourceFiles(URI uri) {
		//assert uri.scheme().equals("file");
		if (!uri.scheme().equals("file"))
			throw new RuntimeException("uri.scheme should be: file");
		String path = uri.toFileString();
		String srcDirectory = path + File.separator + "src";
		try {
			deleteSourcePackages(srcDirectory, "domain");
		} catch (Exception e) {
			clog("error in deleteDomainSourceFiles " + uri.toString());
		}
	}



	//TODO F0121104  Option for keeping the domain model



	private static void deleteRelatedProjectsSourceFiles(URI uri, String pov) { // file:/D:/pfister/workspaces/ws-isoe-temp/org.isoe.diagraph.test.multiview
		if (!uri.scheme().equals("file"))
			throw new RuntimeException("uri.scheme should be: file");
		String path = uri.toFileString();
		for (String relatedSuffix : RELATED_PROJECT_SUFFIXES){  //FP121216y
			if (relatedSuffix.equals(DIAGRAM_EXT)) //FP121021
				relatedSuffix+=pov;
			String dir=path.substring(0, path.length()) + relatedSuffix
					+ File.separator + "src";
			deleteSourcePackages(dir, relatedSuffix.substring(1));
		}
	}


	private static void deleteDomainSourceFiles(String plugin) {
		URI ur = ResourceUtils.createFileUri(plugin + "/");
		if (ur.scheme().equals("file")) {
			String path = ur.toFileString();
			String srcDirectory = path + "src"; // File.separator +
			deleteSourcePackages(srcDirectory, "domain");
		}
	}


	private static void deleteXPandTemplateFiles(URI uri, String diagraphTemplateTarget) {
		if (!uri.scheme().equals("file"))
			throw new RuntimeException("uri.scheme should be: file");
		String path = uri.toFileString();
		String srcDirectory = path + File.separator + diagraphTemplateTarget;//org.isoe.diagraph.gmf.templates.DiagraphTemplates.DIAGRAPH_TEMPLATES_TARGET;
		try {
			deleteSourcePackages(srcDirectory, "templates");
		} catch (Exception e) {
			clog("error while deleting xpand templates !!!");
		}

	}

	/*------------------ end directory && file  deletion -------------------------------------*/



	public static String getEcoreLocation(String workspaceLocation,String modelFolder,
			String projectName) {
		String ecoreName=projectName;
			if (ecoreName.contains("."))
				ecoreName = ecoreName
						.substring(ecoreName.lastIndexOf(".") + 1);
		String ecoreLocation = workspaceLocation + File.separator + projectName
				+ File.separator + modelFolder + File.separator + ecoreName  // DParams.MODEL_FOLDER
				+ ".ecore";
		return ecoreLocation;
	}




	public static void check(String[] resourceData_, boolean regen_) {
		String metamodelfp_ = resourceData_[RSRC_DIRECTORY] + File.separator
				+ resourceData_[RSRC_MODEL];

		String path = metamodelfp_;
		String dir = resourceData_[1];
		if (changeTargetFolder) {
			path = resourceData_[RSRC_PROJECT] + File.separator
					+ resourceData_[RSRC_TARGET_FOLDER] + File.separator
					+ resourceData_[RSRC_MODEL];
			dir = resourceData_[RSRC_PROJECT] + File.separator
					+ resourceData_[RSRC_TARGET_FOLDER];
		}
		if (LOG)
		  clog("checking dir " + dir); // D:\pfister\workspaces\ws-isoe-gmf-1\org.isoe.diagraph.test.k4\model\k4

		String targetPlugin = resourceData_[RSRC_PLUGIN];
		if (LOG)
		  clog("checking target " + targetPlugin);
		String[] checksums = null;

		// D:\pfister\workspaces\ws-isoe-gmf-1\org.isoe.diagraph.test.k4\refact-model\checksum.txt
		if (resourceData_[RSRC_CHECKSUM_0] != null) {
			checksums = new String[3];
			checksums[0] = resourceData_[RSRC_CHECKSUM_0];
			checksums[1] = resourceData_[RSRC_CHECKSUM_1];
			checksums[2] = resourceData_[RSRC_CHECKSUM_2];
		} else
			if (LOG)
			  clog("no checksum found");

		ResourceSet resourceSet = new ResourceSetImpl();
		String gmfgraphchecksum = "", gmftoolchecksum = "", gmfmapchecksum = "";
		try {
			gmfgraphchecksum = checksum(new File(loadModel(resourceSet,
					URI.createFileURI(path + ".gmfgraph")).getURI()
					.toFileString()));
			gmftoolchecksum = checksum(new File(loadModel(resourceSet,
					URI.createFileURI(path + ".gmftool")).getURI()
					.toFileString()));
			gmfmapchecksum = checksum(new File(loadModel(resourceSet,
					URI.createFileURI(path + ".gmfmap")).getURI()
					.toFileString()));
		} catch (IOException e) {
			throw new RuntimeException("error while checking " + targetPlugin);
		}
		if (LOG){
		  clog("new checksums: ");
		  clog("checksums[0]=\"" + gmfgraphchecksum + "\";");
		  clog("checksums[1]=\"" + gmftoolchecksum + "\";");
		  clog("checksums[2]=\"" + gmfmapchecksum + "\";");
		}
		String error = "";
		if (checksums != null) {
			if (!checksums[0].equals(gmfgraphchecksum))
				error += "gmfgraph checksum for " + targetPlugin + "\n";
			if (!checksums[1].equals(gmftoolchecksum))
				error += "gmftool checksum for " + targetPlugin + "\n";
			if (!checksums[2].equals(gmfmapchecksum))
				error += "gmfmap checksum for " + targetPlugin + "\n";
		}
		if (error.length() > 0 || checksums == null) {
			checksums = new String[3];
			checksums[0] = gmfgraphchecksum;
			checksums[1] = gmftoolchecksum;
			checksums[2] = gmfmapchecksum;
			// writeChecksum_(dir, checksums);
			writeChecksum(resourceData_[RSRC_PROJECT], dir, checksums);
			if (error.length() > 0) {
				if (CHECK && !regen_)
					throw new RuntimeException(error);
				else {
					if (LOG)
					  clog("warning!!! " + error);
					DLog.addWarning("warning... " + error);
				}
			} else if (LOG)
				clog("checksum ok");
		} else if (LOG)
			clog("checksum done");
	}



	public static List<EObject>  loadModel(String[] resourceData, String povsuffix_, String suffix) { //FP140124
		String metamodelfp_ = resourceData[RSRC_DIRECTORY] + File.separator;

		metamodelfp_ +=  resourceData[RSRC_MODEL];

		if (povsuffix_!=null)
			metamodelfp_ +=  povsuffix_;

		String path = metamodelfp_;
		String dir = resourceData[1];
		if (changeTargetFolder) {
			path = resourceData[RSRC_PROJECT] + File.separator
					+ resourceData[RSRC_TARGET_FOLDER] + File.separator
					+ resourceData[RSRC_MODEL];
			dir = resourceData[RSRC_PROJECT] + File.separator
					+ resourceData[RSRC_TARGET_FOLDER];
		}
		if (LOG)
		  clog("load " + dir); //C:\workspaces\democgeiet55\org.isoe.mbse.k9\model\k9_default_root.gmfgraph

		String targetPlugin = resourceData[RSRC_PLUGIN];

		ResourceSet resourceSet = new ResourceSetImpl();
        Resource result = null;
		try {
			result = loadModel(resourceSet,URI.createFileURI(path + "."+suffix));
			return result.getContents();
		} catch (IOException e) {
			System.err.println("error while loading " + targetPlugin);
		}
		return null;

	}


	private static Resource loadModel(ResourceSet resourceSet, URI uri)
			throws IOException {
		Map<String, Object> xt = resourceSet.getResourceFactoryRegistry()
				.getExtensionToFactoryMap();
		xt.put("ecore", new XMIResourceFactoryImpl());
		Resource srcRes = ResourceUtils.loadMetamodel(resourceSet, uri);
		return srcRes;
	}

	public static String getWorkFolder(String[] data){
		String separator="/";
		if (data[ResourceManager.RSRC_ABSOLUTE_].contains(File.separator))
			separator = File.separator;
		String workfolder = data[ResourceManager.RSRC_ABSOLUTE_].substring(0,
				data[ResourceManager.RSRC_ABSOLUTE_].lastIndexOf(separator));
		workfolder = workfolder.substring(workfolder.lastIndexOf(separator) + 1);
		return workfolder;
	}


	public static String[] parseFileLocation(String abspath, String targetPath, String savePath) {
		String[] result = new String[RSRC_SAVE_FOLDER + 1];
		String directory = null;
		String modelname = null;
		String workspace = null;
		String project = null;
		String plugin = null;
		String path = null;
		String separator = "/";
		if (abspath.contains(File.separator))
			separator = File.separator;
		if (!abspath.endsWith(".ecore"))
			throw new RuntimeException(abspath+" -- Not an .ecore File !!!!");
		boolean isLinux = File.separator.equals("/");
		if (isLinux)
			abspath="/"+abspath;
		result[RSRC_ABSOLUTE_] = abspath;
		directory = abspath.substring(0, abspath.lastIndexOf(separator));
		result[RSRC_DIRECTORY] = directory;
		project = directory.substring(0, directory.lastIndexOf(separator));
		if (project.contains("%"))
			throw new RuntimeException("workspace path ("+project+") should not contain any space nor any special character !!!"); //FP131226
		result[RSRC_PROJECT] = project;
		plugin = project.substring(project.lastIndexOf(separator) + 1);
		result[RSRC_PLUGIN] = plugin;
		workspace = project.substring(0, project.lastIndexOf(separator));
		if (isLinux)
			workspace="/"+workspace;
		result[RSRC_WORKSPACE_] = workspace;
		modelname = abspath.substring(abspath.lastIndexOf(separator) + 1,
				abspath.lastIndexOf("."));
		result[RSRC_MODEL] = modelname;
		path = directory + separator + modelname;
		result[RSRC_PATH_] = path;
		result[RSRC_TARGET_FOLDER]=targetPath;
		result[RSRC_SAVE_FOLDER]=savePath;
		readCheckSum(null, result);

		if (LOG){
			clog("abspath= "+abspath);
			for (String r : result)
				clog(r);
		}
		return result;

	}

	public static void readCheckSum(String directory, String[] resourceData) {
		if (directory == null)
			directory = resourceData[RSRC_DIRECTORY]; // D:\pfister\workspaces\ws-isoe-gmf-1\org.isoe.diagraph.test.k4\model
		String[] cs = readCheckSum(directory);
		if (cs != null) {
			resourceData[RSRC_CHECKSUM_0] = cs[0];
			resourceData[RSRC_CHECKSUM_1] = cs[1];
			resourceData[RSRC_CHECKSUM_2] = cs[2];
		} else {
			resourceData[RSRC_CHECKSUM_0] = null;
			resourceData[RSRC_CHECKSUM_1] = null;
			resourceData[RSRC_CHECKSUM_2] = null;
		}
	}

	private static String[] readCheckSum(String directory) {
		String checksumpath = directory + File.separator + "checksum.txt"; // D:\pfister\workspaces\runtime-New_configuration-30\org.isoe.diagraph.mydsl\model\checksum.txt
		BufferedReader in;
		try {
			String[] strs = new String[3];
			in = new BufferedReader(new FileReader(checksumpath));
			int line = 0;
			String readlin = "";
			while ((readlin = in.readLine()) != null) {
				strs[line] = readlin;
				if (LOG)
				  clog(readlin);
				line++;
			}
			in.close();
			return strs;
		} catch (Exception e) {
			clog("no file: " + checksumpath);
		}
		return null;
	}



	// projectName==D:\pfister\workspaces\runtime-diagraph\org.isoe.diagraph.test.dp4se
	// directory==D:\pfister\workspaces\runtime-diagraph\org.isoe.diagraph.test.dp4se\model
	private static void writeChecksum(String projectName, String directory,
			String[] cs) {
		String separator = "/";
		if (projectName.contains(File.separator))
			separator = File.separator;
		try {
			IProject prj = ResourcesPlugin
					.getWorkspace()
					.getRoot()
					.getProject(
							projectName.substring(projectName
									.lastIndexOf(separator) + 1));
			IFile file = prj.getFile(directory.substring(directory
					.lastIndexOf(separator) + 1)
					+ separator
					+ "checksum(2).txt");
			if (file.exists())
				file.delete(true, null);
			String out = "";
			for (String c : cs)
				out += c + "\n";
			file.create(new ByteArrayInputStream(out.getBytes()),
					IResource.NONE, null);
			if (LOG)
			  clog("file " + file.getLocation().toPortableString()
					+ " saved");
		} catch (CoreException e) {
			throw new RuntimeException("unable to write file: "
					+ "checksum(2).txt " + e.getMessage());
		}
	}

	private static String checksum(File file) {
		try {
			InputStream fin = new FileInputStream(file);
			java.security.MessageDigest md5er = MessageDigest
					.getInstance("MD5");
			byte[] buffer = new byte[1024];
			int read;
			do {
				read = fin.read(buffer);
				if (read > 0)
					md5er.update(buffer, 0, read);
			} while (read != -1);
			fin.close();
			byte[] digest = md5er.digest();
			if (digest == null)
				return null;
			String strDigest = "0x";
			for (int i = 0; i < digest.length; i++) {
				strDigest += Integer.toString((digest[i] & 0xff) + 0x100, 16)
						.substring(1).toUpperCase();
			}
			return strDigest;
		} catch (Exception e) {
			return null;
		}
	}

	private static boolean deleteDirectory(File path) {
		if (path.exists()) {
			File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++)
				if (files[i].isDirectory()) {
					deleteDirectory(files[i]);
				} else
					files[i].delete();
		}
		return (path.delete());
	}

	public static String logFile="";

	public static void openProjectIfExists(String projectPath)  { //FP120401
		if (LOG){
			//if (defaultroot_)
			 // clog("opening related project if exists " + projectPath);
		//	else
			  clog("opening project if exists " + projectPath);
		  logFile+=projectPath+"\n";
		}
		try {
			IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		    IProject p = workspaceRoot.getProject(projectPath);
		    if (p!=null && !p.isOpen())
		    	p.open(null);
		} catch (CoreException e) {
			if (LOG){
			  clog("project "+projectPath+" does not exist");
			  logFile+= "project "+projectPath+" does not exist"+"\n";
			}
		}
	}


	private static  void logdir_(String dir, String name){
		  String x=dir.substring(0,dir.lastIndexOf(File.separator));
		  String msg="deleting  " + name + " source directory " + dir.substring(x.substring(0,x.lastIndexOf(File.separator)).length()+1);
		  clog(msg);
		  logFile+=msg+"\n";
		}



	private static void deleteSourcePackages(String dir, String name) {
		if (LOG)
			logdir_(dir,name);
		String pak="";
		try {
			File[] packages = new File(dir).listFiles();
			for (int i = 0, maxi = packages.length; i < maxi; i++) {
				if (packages[i].isDirectory()) {
					pak= packages[i].getAbsolutePath();
					if (!".svn".equals(pak))
					    deleteDirectory(packages[i]); //FP120401
				}
			}
		} catch (Exception e) {
			String msg="error while deleting "+name+" dir:"+dir+" package:"+pak;
			clog(msg);
			logFile+=msg+"\n";
		}
	}



	//FP120623b
	public static Resource saveModel(EObject tosave, String povSuffix, String extension,String[] resourceData, String suffixedFilePath) {
		if (povSuffix!=null)
			suffixedFilePath += povSuffix;
		try {
			return save(resourceData,suffixedFilePath + "." + extension, tosave);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}


	public static Resource save(String[] resourceData, String pathName,
			EObject toSave) throws IOException {
		String path = pathName;
		if (changeTargetFolder) {
			String filename = pathName.substring(pathName
					.lastIndexOf(File.separator) + 1);
			path = resourceData[RSRC_PROJECT] + File.separator
					+ resourceData[RSRC_TARGET_FOLDER] + File.separator + filename;
		}
		URI furi = URI.createFileURI(path);
		return ResourceUtils.save_(furi, toSave);
	}

	public static URI prepareResource(ResourceSet resourceSet,
			String[] resourceData) {
		if (LOG) {
			clog("plugin: "
					+ resourceData[ResourceManager.RSRC_PLUGIN]);
			clog("filePath: "
					+ resourceData[ResourceManager.RSRC_PATH_]);
		}
		Map<String, Object> xt = resourceSet.getResourceFactoryRegistry()
				.getExtensionToFactoryMap();
		xt.put("ecore", new XMIResourceFactoryImpl());
		return URI.createFileURI(resourceData[ResourceManager.RSRC_ABSOLUTE_]);
	}


	private static void openRelatedProjects(String plugin, String pov) { //FP121021
		  for (String relatedSuffix : RELATED_PROJECT_SUFFIXES){  //FP121216y
			if (relatedSuffix.equals(DIAGRAM_EXT))
				openProjectIfExists(plugin + DIAGRAM_EXT + pov);
			else
			    openProjectIfExists(plugin + relatedSuffix);
		  }
	}



	public static boolean endsWithRelatedSuffix(String name) {
		for (String relatedSuffix : RELATED_PROJECT_SUFFIXES)
			if (name.endsWith(relatedSuffix))
				return true;
		return false;
	}


	public static void deleteOldSrcFiles_(String[] resourceData) {
		deleteDomainSourceFiles(URI.createFileURI(resourceData[ResourceManager.RSRC_PROJECT]));
	}

	public static void deleteOldXpandFiles(String[] resourceData, String templatesFolder) {
		deleteXPandTemplateFiles(URI.createFileURI(resourceData[ResourceManager.RSRC_PROJECT]),templatesFolder); //FP121216//FP120627
	}


	public static void deleteOldGraphFiles(String[] resourceData, String suffix) {
		deleteRelatedProjectsSourceFiles(URI.createFileURI(resourceData[ResourceManager.RSRC_PROJECT]), suffix);
	}

	public static void openRelatedProjects(String[] resourceData, String suffix) { //FP120401
		openRelatedProjects(resourceData[ResourceManager.RSRC_PLUGIN],suffix);
	}

	public static void deleteOldSourceFiles(String plugin) {
		deleteDomainSourceFiles(plugin);
	}

	public static boolean isTargetChanged() {
		return changeTargetFolder;
	}

	public static void setParams(boolean changetf, String[] resourceData) {
		if (!changeTargetFolder && changetf && resourceData != null) {
			String dir = resourceData[RSRC_PROJECT] + File.separator + resourceData[RSRC_TARGET_FOLDER];
			if (LOG)
				clog("reloading checksums in refactoring directory");
			readCheckSum(dir, resourceData);
		}
		changeTargetFolder = changetf;
	}

	public static void setVersionChanged(boolean b) {
		versionChanged = b;
	}

	public static void initOperation(boolean save) {
		if (save)
		 setParams(false, null);
		setVersionChanged(false);
		//setAnnotationsRefactored(false);
	}

	public static boolean isVersionChanged() {
		return versionChanged;
	}



	public static boolean projectExists(String name) {
		return getProject(name) != null;
	}



	public static IProject getProject(String name) {
		IWorkspace ws = ResourcesPlugin.getWorkspace();
		org.eclipse.core.runtime.Path rootlocation = (Path) ws.getRoot()
				.getLocation();
		IProjectDescription description = null;
		IProject project = null;
		try {
			description = ResourcesPlugin.getWorkspace()
					.loadProjectDescription(
							new Path(rootlocation.toPortableString() + "/"
									+ name + "/.project"));
			project = ResourcesPlugin.getWorkspace().getRoot()
					.getProject(description.getName());
		} catch (CoreException e) {
			return null;
		}
		return project;
	}

	private static void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);

	}


   public static EPackage getDiagraphMetaModel( ResourceSet resourceSet, URI uri) { //FP130829
      if (LOG)
         clog("---------------          getAdditionalMetaModel() ");
      //ResourceSet resourceSet = new ResourceSetImpl();
      if (uri==null)
         uri=URI.createURI("http://isoe-2012-diagraph-dsmlv4");


      EPackage diagraphMModel = (EPackage)resourceSet.getResource(uri, true).getContents().get(0);
     // EPackage diagraphMModel = (EPackage) ResourceUtils.loadMetamodel(resourceSet, uri).getContents().get(0);
      if (LOG){
        if (diagraphMModel!=null)
            clog(diagraphMModel.getName()+" found");
        else
           clog("diagraphMModel not found");
      }
      return diagraphMModel;
   }









}
