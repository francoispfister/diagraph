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
package org.isoe.fwk.megamodel.manager;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.URI;
import org.isoe.extensionpoint.megamodel.IMegamodelManager;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.pathes.PathPreferences;
import org.isoe.fwk.utils.FilesUtils;
import org.isoe.fwk.utils.ResourceUtils;
import org.eclipse.core.runtime.IPath;


/**
 *
 * @author fpfister
 *
 */
import org.isoe.diagraph.controler.IDiagraphControler;  //FP140707refactored
public class MegamodelManager implements IMegamodelManager{


	private static final boolean LOG = false;
	private IDiagraphControler controler;
	private List<String> languages;
	private File repositoryLocation;
	private boolean silent;



    @Override
	public void consolidateRepositoryWithProjects() {

			/*
			String langageName =controler.getLangageName();
			if (langageName != null && !langageName.isEmpty()) {
				URI ecoruri = URI.createPlatformResourceURI(PathPreferences
						.getPreferences().getTeamNamespace() + "." + langageName,
						false);
				String ecorpath = CommonPlugin.resolve(ecoruri).toFileString()
						+ DParams.SEP_MODEL_FOLDER_SEP + langageName + ".ecore";
				ecorpath = ecorpath.replaceAll("/", Separator.SEPARATOR);
				clog(ecorpath);
			}*/
			List<String> languagesInRepository = getLanguagesInRepository();
			List<String> languageProjects = getLanguagesInProjects();// ResourceUtils.getOpenedProjects();
			for (String proj : languageProjects) {
				String lang = proj.substring(proj.lastIndexOf(".") + 1);
				lang = lang + ".ecore";
				if (!languagesInRepository.contains(lang)) {
					clog(lang + " is missing in repository");
					String language = proj.substring(proj.lastIndexOf(".") + 1);

					File projloc = org.eclipse.core.resources.ResourcesPlugin.getWorkspace().getRoot().getProject(proj).getLocation().toFile();
					String ecorePath = projloc.getAbsolutePath() + File.separator
							+ "model" + File.separator + language + "." + "ecore";
					copyToRepository(ecorePath);
					String ecoreDiagPath = ecorePath + "diag";
					copyToRepository(ecoreDiagPath);
				}
			}
			List<String> langsremoved = new ArrayList<String>();
			for (String langInRepo : languagesInRepository) {
				if (!existsInProjects(languageProjects, langInRepo)) {
					clog(langInRepo + " should be removed from repository");
					removeFromRepository(langInRepo);
					removeFromRepository(langInRepo + "diag");
					langsremoved.add(langInRepo);
				}
			}
			for (String langremoved : langsremoved)
				removeInstances(langremoved);
			languages = getLanguagesInRepository();
			removeUnknownInstances();
			controler.refreshLanguageRepositoryProject();
		}



	private File getRepositoryLocation() {
		if (repositoryLocation == null) {
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IWorkspaceRoot wsroot = workspace.getRoot();
			IProject project = wsroot.getProject(PathPreferences
					.getPreferences().getInstanceRepositoryLocation());
			IPath p = project.getLocation();
			try {
				repositoryLocation = p.toFile();
			} catch (Exception e) {
				throw new RuntimeException(
						"not in a modeler configuration (3), no project containing models !!!");
			}
		}
		return repositoryLocation;
	}

	@Override
	public boolean filterModelsInRepository(String name) {
		boolean result = !name.endsWith(".svn") && !name.endsWith(".svn")
				&& !name.endsWith("_diagram") && !name.endsWith(".dot")
				&& !name.endsWith(".jpg") && !name.endsWith(".trace");
		return result;
	}

	@Override
	public String[] getLanguageFiles(String folder, String exclude) {
		List<String> fs = new ArrayList<String>();
		if (folder != null) {
			File modelFolder = new File(folder);
			if (modelFolder.exists())
				for (File f : modelFolder.listFiles()) {
					String fn = f.getName();
					if (filterModelsInRepository(fn) && !f.isDirectory()) {
						String op = f.getName();
						if (exclude == null
								|| (exclude != null && !op.equals(exclude)))
							fs.add(op);
					}
				}
		}
		String[] vs = new String[fs.size()];
		int i = 0;
		for (String op : fs)
			vs[i++] = op;
		return vs;
	}

	@Override
	public String[] getModelsInRepository(String exclude) {// TODO Optimize
		List<String> fs = new ArrayList<String>();
		for (File f : getRepositoryLocation().listFiles()) {
			if (f.getName().equals(DParams.INSTANCE_FOLDER)) {// REPOSITORY_DATA
				for (File sub : f.listFiles()) {
					String fn = sub.getName();
					if (filterModelsInRepository(fn) && !sub.isDirectory()) {
						String op = sub.getName();
						if (exclude == null
								|| (exclude != null && !op.equals(exclude)))
							fs.add(op);
					}
				}
			}
		}
		String[] vs = new String[fs.size()];
		int i = 0;
		for (String op : fs)
			vs[i++] = op;
		return vs;
	}



	public File getInstancesRepositoryLocation() {
		if (repositoryLocation == null) {
			    String rpl=PathPreferences.getPreferences().getInstanceRepositoryLocation();
			    try {
			    	repositoryLocation = ResourcesPlugin.getWorkspace().getRoot().getProject(rpl).getLocation().toFile();
				} catch (Exception e) {
					if (LOG)
						  System.out.println("no instances repositoryLocation "+rpl);
					return null;
				}

			if (LOG)
			  System.out.println("repositoryLocation="+repositoryLocation.getAbsolutePath());
		}
		return repositoryLocation;
	}

	private String[] getRepositoryFiles(String exclude) {// Optimize please
		List<String> fs = new ArrayList<String>();
		File repo = getInstancesRepositoryLocation();
		if (repo!=null)
		 for (File f : repo.listFiles()) {
			if (f.getName().equals(DParams.INSTANCE_FOLDER)) {
				for (File sub : f.listFiles()) {
					String fn = sub.getName();
					if (filterRepositoryFileNames(fn) && !sub.isDirectory()) {
						String op = sub.getName();
						if (exclude == null
								|| (exclude != null && !op.equals(exclude)))
							fs.add(op);
					}
				}
			}
		}
		String[] vs = new String[fs.size()];
		int i = 0;
		for (String op : fs)
			vs[i++] = op;
		return vs;
	}


	private boolean filterRepositoryFileNames(String fn) {
		boolean result=!fn.endsWith(".svn") && !fn.endsWith(".svn")
				&& !fn.endsWith("_diagram") && !fn.endsWith(".dot")
				&& !fn.endsWith(".jpg") && !fn.endsWith(".trace");
		return result;
	}



		private void clog(String mesg) {
		if (LOG && !silent)
			System.out.println(mesg);
	    }

        @Override
		public void setSilent(boolean silent) {
			this.silent = silent;
		}



		private File getRepositoryFolder() {
			return new File(CommonPlugin.resolve(
					URI.createPlatformResourceURI(PathPreferences.getPreferences()
							.getUniverseProjectName()
							+ "/"
							+ PathPreferences.getPreferences().getUniverseFolder(),
							false)).toFileString());
		}

		private List<String> getLanguagesInRepository() {
			List<String> result = new ArrayList<String>();
			File repofold = getRepositoryFolder();
			clog(repofold.getAbsolutePath());
			File[] contents = repofold.listFiles();
			for (File file : contents) {
				String filname = file.getName();
				String ext = filname.substring(filname.lastIndexOf(".") + 1);
				if (ext.equals("ecore"))
					result.add(filname);
			}
			return result;
		}

		private boolean existsInProjects(List<String> languageProjects, String ecor) {
			for (String proj : languageProjects) {
				String lang = proj.substring(proj.lastIndexOf(".") + 1);
				lang = lang + ".ecore";
				if (lang.equals(ecor))
					return true;
			}
			return false;
		}

		private boolean existsLanguage(String lang) {
			for (String language : languages)
				if (lang.equals(language.substring(0, language.lastIndexOf("."))))
					return true;
			return false;
		}

		private void removeUnknownInstances() {
			List<File> toremove = new ArrayList<File>();
			for (File file : getRepositoryFolder().listFiles()) {
				String fname = file.getName();

				String ext_ = fname.substring(fname.lastIndexOf(".") + 1);
				if (!ext_.equals("ecore") && !ext_.equals("ecorediag")) {
					if (ext_.endsWith("diagram"))
						ext_ = ext_.substring(0, ext_.indexOf("_"));
					if (!existsLanguage(ext_)) {
						toremove.add(file);
					}
				}
			}
			for (File fileToremove : toremove) {
				if (!fileToremove.delete())
					clog("failed to remove " + fileToremove);
				else
					clog(fileToremove + " deleted from the repository");
			}
		}

		private void removeInstances(String langremoved) {
			String lang = langremoved.substring(0, langremoved.lastIndexOf("."));
			List<File> toremove = new ArrayList<File>();
			for (File file : getRepositoryFolder().listFiles()) {
				String fname = file.getName();
				String ext = fname.substring(fname.lastIndexOf(".") + 1);
				if (ext.equals(lang))
					toremove.add(file);
				if (ext.startsWith(lang) && ext.endsWith("diagram"))
					toremove.add(file);
			}
			for (File fileToremove : toremove) {
				if (!fileToremove.delete())
					clog("failed to remove " + fileToremove);
				else
					clog(fileToremove + " deleted from the repository");
			}
		}

		private void removeFromRepository(String src) {
			clog("remove from repo " + src);
			File fil = new File(getRepositoryFolder().getAbsolutePath()
					+ File.separator + src);
			if (!fil.delete())
				clog("failed to remove " + fil);
			else
				clog(fil + " deleted from the repository");
		}

		private void copyToRepository(String src) {
			File fsrc = new File(src);
			if (fsrc.exists()) {
				File inRepo = new File(getRepositoryFolder().getAbsolutePath()
						+ File.separator + fsrc.getName());
				clog(" copying " + fsrc.getAbsolutePath() + " -> "
						+ inRepo.getAbsolutePath());
				try {
					FilesUtils.copyFile(fsrc, inRepo);
				} catch (IOException e) {
					clog("(4) error while copying " + fsrc);
					e.printStackTrace();
				}
			}

		}

		private List<String> getLanguagesInProjects() {
			List<String> result = new ArrayList<String>();
			for (String op : ResourceUtils.getOpenedProjects()) {
				String ext = op.substring(op.lastIndexOf(".") + 1);
				if (!op.equals(PathPreferences.getPreferences()
						.getUniverseProjectName())
						&& !ext.startsWith("diagram")
						&& !ext.startsWith("edit")
						&& !ext.startsWith("editor")
						&& !ext.startsWith("tests"))
					result.add(op);
			}
			return result;
		}


		@Override
		public void setControler(IDiagraphControler controler) {
			this.controler = controler;
		}

		@Override
		public boolean isQualified() {
			return true;
		}

		@Override
		public boolean isStub() {
			return false;
		}






}
