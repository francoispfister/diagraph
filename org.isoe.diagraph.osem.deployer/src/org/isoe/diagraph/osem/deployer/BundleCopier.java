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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.isoe.ep.osem.deployer.IOsemDeployer;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.core.Separator;
import org.osgi.framework.Bundle;

/**
 *
 * @author fpfister
 *
 */
public class BundleCopier {
	// FP140321z
	// org.isoe.diagraph.osem.deployer.BundleCopier
	private static final boolean LOG = DParams.OsemDeployer_LOG;

	private String targetFolder;

	private String sourceFolder;

	private IOsemDeployer deployer;

	public BundleCopier(IOsemDeployer deployer) {
		super();
		this.deployer = deployer;
		// this.targetFolder = targetFolder;
		// this.sourceFolder = sourceFolder;
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}



	private List<URL> getSubElements(Bundle bundle, String path,
			String extension, boolean absoluteURL, boolean nested) {
		List<URL> result = new ArrayList<URL>();
		Enumeration<String> entryPaths = bundle.getEntryPaths(path);
		if (entryPaths == null)
			return result;
		for (Enumeration<String> entry = entryPaths; entry.hasMoreElements();) {
			String fileName = (String) entry.nextElement();
			if (extension == null
					|| (extension != null && fileName.endsWith(extension))) {
				URL url = bundle.getEntry(fileName);

				// if (!url.toString().endsWith(SVN_FOLDER)
				// && !url.toString().endsWith(CVS_FOLDER))

				{
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

	public List<String> getItemsFromBundle(Bundle bundle, String path) {
		List<String> result = new ArrayList<String>();
		for (URL url : getSubElements(bundle, path, null, true, false)) {
			String urstring = url.toString();
			urstring = urstring.substring(urstring.indexOf(path));
			result.add(urstring);
		}
		return result;
	}

	private String append(String location, String file) {
		if (!location.endsWith(File.separator))
			location += File.separator;
		location += file;
		location = location.replaceAll("/", Separator.SEPARATOR);
		return location;
	}

	public boolean copyFromBundle(String srcplugin, String sourcefolder,
			String targetplugin, String targetfolder, boolean overWrite) {
		if (targetplugin == null) {
			deployer.cerror("cannot deploy " + targetfolder);
			return false;
		}
		if (sourcefolder.endsWith("/"))
			sourcefolder = sourcefolder.substring(0, sourcefolder.length() - 1);
		if (targetfolder.endsWith(File.separator))
			targetfolder = targetfolder.substring(0, targetfolder.length() - 1);

		String log = "(x) copying all files from plugin " + srcplugin + "/"
				+ sourcefolder + " to destination " // + targetplugin_ + "/"
				+ targetfolder;
		if (LOG)
			clog(log);
		this.sourceFolder = sourcefolder;
		this.targetFolder = targetfolder;
		File targ = new File(targetfolder);
		copy(Platform.getBundle(srcplugin), sourcefolder, targ, overWrite);
		return true;
	}

	private void copy(Bundle srcbundle, String sourceDir, File targetFile,
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
				// if (!srcitem.endsWith(CVS_FOLDER)
				// && !srcitem.endsWith(SVN_FOLDER))
				copy(srcbundle, srcitem, new File(filename), overWrite);
			} else {

				if (!targetFile.exists())
					targetFile.mkdir();
				URL url = srcbundle.getEntry(srcitem);
				if (url == null)
					throw new RuntimeException(
							"URL error while copying - bug to fix: the source folder name must not exist within the source plugin name");
				if (url != null) {
					if (!(new File(filename).exists() && !overWrite))
						copy(url, filename);
					else if (LOG)
						clog(filename + " allready exists"); // FP130416
				}
			}
		}
	}

	private void copy(URL source, String targetPath) {
		if (LOG)
			clog("copying (y) file from " + source.getPath() + " to "
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

	/**
	 * borrowed from "org.eclipse.ui.newWizards"
	 *
	 * @param bundleName
	 * @param zipLocation
	 * @param projectName
	 * @param monitor
	 */
	boolean unzipProject(String bundleName, String zipLocation,// zips/kfsm/m1/lang.m2.kerm.foo.samplerun.zip
			String projectName, IProgressMonitor monitor) {

		if (LOG) clog("zipLocation: "
				+ zipLocation);

		URL interpreterZipUrl = FileLocator.find(
				Platform.getBundle(bundleName), new Path(zipLocation), null);

		if (LOG) clog("zipLocation: "
				+ zipLocation
				+ " interpreterZipUrl= "
				+ (interpreterZipUrl == null ? "null" : interpreterZipUrl
						.toString()));

		if (interpreterZipUrl == null) {
			clog("failed");
			return false;
		}

		IProject project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(projectName);

		if (project.exists()) {
			return false;
		}

		try {
			// We make sure that the project is created from this point forward.
			project.create(monitor);

			// open the zip file stream
			// InputStream theFile = new
			// FileInputStream(zipuri_.toFileString());
			// ZipInputStream zipFileStream = new ZipInputStream(theFile);

			ZipInputStream zipFileStream = new ZipInputStream(
					interpreterZipUrl.openStream());
			ZipEntry zipEntry = zipFileStream.getNextEntry();

			// We derive a regexedProjectName so that the dots don't end up
			// being
			// interpreted as the dot operator in the regular expression
			// language.
			String regexedProjectName = projectName.replaceAll("\\.", "\\."); //$NON-NLS-1$ //$NON-NLS-2$

			while (zipEntry != null) {
				// We will construct the new file but we will strip off the
				// project
				// directory from the beginning of the path because we have
				// already
				// created the destination project for this zip.

				File file = new File(project.getLocation().toString(), zipEntry
						.getName().replaceFirst(
								"^" + regexedProjectName + "/", "")); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$

				if (false == zipEntry.isDirectory()) {

					/*
					 * Copy files (and make sure parent directory exist)
					 */
					File parentFile = file.getParentFile();
					if (null != parentFile && false == parentFile.exists()) {
						parentFile.mkdirs();
					}
					OutputStream os = null;

					try {
						os = new FileOutputStream(file);

						byte[] buffer = new byte[102400];
						while (true) {
							int len = zipFileStream.read(buffer);
							if (zipFileStream.available() == 0)
								break;
							os.write(buffer, 0, len);
						}
					} finally {
						if (null != os) {
							os.close();
						}
					}
				}

				zipFileStream.closeEntry();
				zipEntry = zipFileStream.getNextEntry();
			}

			project.open(monitor);
			// in order to make sure the project natures are correctly
			// identified close and reopen the project
			project.close(monitor);
			project.open(monitor);
			project.refreshLocal(IFile.DEPTH_INFINITE, monitor);
			// project.refreshLocal(IResource.DEPTH_ONE, monitor);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
