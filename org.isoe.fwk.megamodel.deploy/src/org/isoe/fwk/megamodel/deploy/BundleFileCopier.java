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
package org.isoe.fwk.megamodel.deploy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.URI;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.core.Separator;
import org.osgi.framework.Bundle;

/**
 * copies resources from the platform:/plugin space to the current workbench
 *
 * @author pfister
 *
 */
public class BundleFileCopier {

	private static final boolean LOG = DParams.BundleFileCopier_LOG;
	private String sourceFolder;
	private String targetFolder;
	private String endFilter;
	private String startFilter;
	private static String SVN_FOLDER = ".svn/";
	private static String CVS_FOLDER = "CVS/";


	private static BundleFileCopier instance;

	public static BundleFileCopier getInstance() {
		if (instance == null)
			instance = new BundleFileCopier();
		return instance;
	}

	private BundleFileCopier() {
		super();
	}

	private String append(String location, String file) {
		if (!location.endsWith(File.separator))
			location += File.separator;
		location += file;
		location = location.replaceAll("/", Separator.SEPARATOR);
		return location;
	}

	private void copy3(URL source, String targetPath) {
		if (LOG)
			clog("copying (2) file from " + source.getPath() + " to "
					+ targetPath);
		try {
			InputStream in = new FileInputStream(new File(FileLocator.resolve(
					FileLocator.toFileURL(source)).getFile()));
			OutputStream out = new FileOutputStream(new File(targetPath));
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0)
				out.write(buf, 0, len);
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("error while copying "
					+ source.toString() + " to " + targetPath);
		}
	}

	private List<URL> getSubElements(Bundle bundle, String path,
			String extension, boolean absoluteURL, boolean nested) {
		List<URL> result = new ArrayList<URL>();
		Enumeration entryPaths = bundle.getEntryPaths(path);
		if (entryPaths == null)
			return result;
		for (Enumeration entry = entryPaths; entry.hasMoreElements();) {
			String fileName = (String) entry.nextElement();
			if (extension == null
					|| (extension != null && fileName.endsWith(extension))) {
				URL url = bundle.getEntry(fileName);
				if (!url.toString().endsWith(SVN_FOLDER)
						&& !url.toString().endsWith(CVS_FOLDER)) {
					if (absoluteURL) {
						try {
							result.add(FileLocator.toFileURL(url));
						} catch (IOException e) {
							e.printStackTrace();
							throw new RuntimeException("Exception "
									+ e.getMessage() + " !!!!");
						}
					} else
						result.add(url);
				}
			} else if (nested) {
				List<URL> urls = getSubElements(bundle, fileName, extension,
						absoluteURL, nested);
				result.addAll(urls);
			}
		}
		return result;
	}

	private List<String> filesInUri(URI uri) {
		List<String> result = new ArrayList<String>();
		String location = CommonPlugin.resolve(uri).toFileString();
		if (location == null)
			return result; // folder has been deleted by the user...//throw new
							// RuntimeException("filesInUri: "+uri.toString()+" does not exist");
		File f = new File(location);
		if (!f.exists())
			throw new RuntimeException("filesInUri:must exist "
					+ uri.toString());
		if (!f.isDirectory())
			throw new RuntimeException("filesInUri:must be a directory "
					+ uri.toString());
		File[] dets = f.listFiles();
		for (File file : dets)
			result.add(file.getAbsolutePath());
		return result;
	}

	public List<String> getFilesInRepository(String projectName, String path,
			String exclude, int i) {
		if (exclude != null && projectName.equals(exclude)) {
			clog("--GIFIR exclude --");
			return new ArrayList<String>();
		}
		if (i == 1)
			return filesInUri(URI.createPlatformPluginURI(projectName + "/"
					+ path, true));
		else
			return filesInUri(URI.createPlatformResourceURI(projectName + "/"
					+ path, true));
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	private String getRepositoryPath(Bundle bundle, String repositoryProject) {
		String loc = bundle.getLocation(); // reference:file:/E:/Apps/eclipse-diagraph-13/workspace/org.isoe.fwk2.megamodel.deployer/
		String location = loc.substring("reference:file:/".length());
		location = location.substring(0, location.length() - 1);
		location = location.substring(0, location.lastIndexOf("/"));
		location = location + "/" + repositoryProject;
		location = location.replaceAll("/", Separator.SEPARATOR);
		return location;
	}

	public List<String> getItemsFromBundle(Bundle bundle, String path) {
		List<String> result = new ArrayList<String>();
		for (URL url : getSubElements(bundle, path, null, true, false)) {
			String urstring = url.toString();
			urstring = urstring.substring(urstring.indexOf(path));
			result.add(urstring);
		}
		return result;
	}

	// TODO unify copy1 && copy2
	private void copy2(Bundle srcbundle, String sourceDir, File targetFile,
			boolean overWrite) {
		List<String> srcitems = getItemsFromBundle(srcbundle, sourceDir);
		if (srcitems.size() == 0)
			return;
		for (String srcitem : srcitems) {
			String targetPath = targetFile.getAbsolutePath();
			String filename = append(targetPath,
					srcitem.substring(sourceFolder.length() + 1));
			if (srcitem.endsWith("/")) {
				if (!srcitem.endsWith(CVS_FOLDER)
						&& !srcitem.endsWith(SVN_FOLDER)) {
					File ff = new File(filename);
					if (ff.isDirectory()) // FP150602
						throw new RuntimeException(filename
								+ " is a directory, should not happen in copy2");
					copy1(srcbundle, srcitem, ff, overWrite);
				}
			} else {
				boolean c1 = (endFilter == null || filename.endsWith(endFilter));
				boolean c2 = (startFilter == null || filename.contains(startFilter));
				if (c1 && c2) {
					if (!targetFile.exists())
						targetFile.mkdir();
					URL url = srcbundle.getEntry(srcitem);
					if (url == null)
						throw new RuntimeException(
								"URL error while copying - bug to fix: the source folder name must not exist within the source plugin name");
					if (url != null) {
						if (!(new File(filename).exists() && !overWrite))
							copy3(url, filename);
						else if (LOG)
							clog(filename + " allready exists"); // FP130416
					}
				}
			}
		}
	}

	private void copy1(Bundle srcbundle, String sourceDir, File targetFile,
			boolean overWrite) {

		List<String> srcitems = getItemsFromBundle(srcbundle, sourceDir);
		if (srcitems.size() == 0)
			return;
		for (String srcitem : srcitems) {
			String targetPath = targetFile.getAbsolutePath();
			targetPath = targetPath.substring(
					0,
					targetPath.lastIndexOf(targetFolder)
							+ targetFolder.length());
			String filename = append(targetPath,
					srcitem.substring(sourceFolder.length() + 1));
			if (srcitem.endsWith("/")) {
				if (!srcitem.endsWith(CVS_FOLDER)
						&& !srcitem.endsWith(SVN_FOLDER))
					copy1(srcbundle, srcitem, new File(filename), overWrite);
			} else {
				boolean c1 = (endFilter == null || filename.endsWith(endFilter));
				boolean c2 = (startFilter == null || filename
						.contains(startFilter));
				if (c1 && c2) {
					if (!targetFile.exists())
						targetFile.mkdir();
					URL url = srcbundle.getEntry(srcitem);
					if (url == null)
						throw new RuntimeException(
								"URL error while copying - bug to fix: the source folder name must not exist within the source plugin name");
					if (url != null) {
						if (!(new File(filename).exists() && !overWrite))
							copy3(url, filename);
						else if (LOG)
							clog(filename + " allready exists"); // FP130416
					}
				}
			}
		}
	}

	public void copyFromBundle(String srcplugin, String sourcefolder,
			String targetplugin, String targetfolder, String startFilter,
			String endFilter, boolean overWrite) {
		if (sourcefolder.endsWith("/"))
			sourcefolder = sourcefolder.substring(0, sourcefolder.length() - 1);
		if (targetfolder.endsWith("/"))
			targetfolder = targetfolder.substring(0, targetfolder.length() - 1);
		String log = "(1) copying all files from plugin " + srcplugin + "/"
				+ sourcefolder + " to destination " + targetplugin + "/"
				+ targetfolder;
		log += startFilter == null ? "" : " with startFilter (1) [*"
				+ startFilter + "]";
		log += endFilter == null ? "" : " with endFilter (1) [*" + endFilter
				+ "]";
		if (LOG)
			clog(log);
		this.sourceFolder = sourcefolder;
		this.targetFolder = targetfolder;
		this.endFilter = endFilter;
		this.startFilter = startFilter;
		copy1(Platform.getBundle(srcplugin),
				sourcefolder,
				new File(CommonPlugin.resolve(
						URI.createPlatformResourceURI(targetplugin + "/"
								+ targetfolder, true)).toFileString()),
				overWrite);
	}

	private boolean targetFolderExists(String targetPath) {
		if (targetPath.endsWith(Separator.SEPARATOR))
			targetPath = targetPath.substring(0, targetPath.length() - 1);
		File folder = new File(targetPath);
		return folder.exists();
	}

	// srcplugin = org.isoe.fwk.megamodel.deploy
	// sourcefolder = repository-bundle
	// targetPath=C:\workspaces\mintel213\_megamodel\repository

	public void copyFromBundle(String srcplugin, String sourcefolder,
			String targetPath, String startFilter, String endFilter,
			boolean overWrite) {
		if (targetPath.endsWith(Separator.SEPARATOR))
			targetPath = targetPath.substring(0, targetPath.length() - 1);
		if (sourcefolder.endsWith("/"))
			sourcefolder = sourcefolder.substring(0, sourcefolder.length() - 1);
		String log = "(2) copying all files from plugin " + srcplugin + "/"
				+ sourcefolder + " to destination " + targetPath;
		if (LOG) {
			log += startFilter == null ? "" : " with startFilter (1) [*"
					+ startFilter + "]";
			log += endFilter == null ? "" : " with endFilter (1) [*"
					+ endFilter + "]";
			clog(log);
		}
		this.sourceFolder = sourcefolder;
		this.targetFolder = null;
		this.endFilter = endFilter;
		this.startFilter = startFilter;
		copy2(Platform.getBundle(srcplugin), sourcefolder,
				new File(targetPath), overWrite);
	}

}
