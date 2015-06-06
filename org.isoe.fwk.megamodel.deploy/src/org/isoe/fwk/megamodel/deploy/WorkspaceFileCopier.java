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
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.isoe.fwk.utils.FilesUtils;
import org.isoe.fwk.utils.ResourceUtils;

/**
 * 
 * @author pfister
 * 
 */
public class WorkspaceFileCopier {


	private static final boolean LOG = false;
	private static String SVN_FOLDER = ".svn/";
	private static String CVS_FOLDER = "CVS/";

	public static List<String> getItemsFromWorkspaceRepository(
			IProject wsresourceRepositoryProject_, String wsrepositoryFolder) {
		List<String> result = new ArrayList<String>();
		try {
			if (!wsresourceRepositoryProject_.isAccessible())
				wsresourceRepositoryProject_.open(new NullProgressMonitor());
			IFolder repofolder = wsresourceRepositoryProject_
					.getFolder(wsrepositoryFolder);
			if (!repofolder.exists())
				repofolder.create(true, true, null);
			if (LOG){
				String repo=repofolder.getRawLocation().toFile().getAbsolutePath();
			    clog("repository folder= "+repo);
			}
			
			IPath loc = wsresourceRepositoryProject_.getLocation();
			String projectAbsolutePath = new File(loc.toPortableString())
					.getAbsolutePath();
			File reposfolder = new File(projectAbsolutePath + File.separator
					+ wsrepositoryFolder);
			File[] lfiles = reposfolder.listFiles();
			List<File> files = Arrays.asList(lfiles);
			if (LOG && files.isEmpty())
				clog("ItemsFromWorkspaceRepository: empty");
			for (File file : files) {
				if (LOG) 
					clog("ItemsFromWorkspaceRepository: "+file.getName());
				result.add(file.getName());
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return result;
	}

	public void copyFromWorkspace(String srcplugin, String sourceFolderpath,
			String sourcefolder, String targetplugin, String targetFolderPath,
			String targetfolder, String filter, IProgressMonitor progressMonitor) {
		if ( sourcefolder.endsWith("/"))
			sourcefolder = sourcefolder.substring(0, sourcefolder.length() - 1);
		if (targetfolder.endsWith("/"))
			targetfolder = targetfolder.substring(0, targetfolder.length() - 1);
	
		copy(new File(sourceFolderpath+"/"+sourcefolder), new File(
				targetFolderPath+"/"+targetfolder),filter,progressMonitor);
	}

	class EndWithFileFilter implements FileFilter {
		private String description;
		private String end;
		public EndWithFileFilter(String description, String ending) {
			if (description == null || ending == null) {
				throw new NullPointerException(
						"description needed");
			}
			this.description = description;
			this.end = ending;
		}
		@Override
		public boolean accept(File file) {
			if (file.isDirectory()) {
				return true;
			}
			return file.getName().endsWith(end);
		}
		public String getDescription() {
			return description;
		}
	}

	private void copy(File sourceFolder,
			File targetFolder, String filter, IProgressMonitor progressMonitor) {
		String log = "(3) copying all files from workspace "
				+ sourceFolder.getAbsolutePath() + " to destination "
				+ targetFolder.getAbsolutePath();
		log += filter == null ? "" : " with filter (2) [*" + filter + "]";
		try {
			FilesUtils.copyFolder(sourceFolder,
					targetFolder, false, null, new EndWithFileFilter("*" + filter, filter), true, progressMonitor);//new NullProgressMonitor());
		} catch (IOException e) {
			throw new RuntimeException("unable to copy repository");
		}
		clog(log);
	}

	private static void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}
}
