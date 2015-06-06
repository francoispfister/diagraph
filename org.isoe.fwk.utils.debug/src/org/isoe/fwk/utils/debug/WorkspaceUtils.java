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
package org.isoe.fwk.utils.debug;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.EMFPlugin;

/**
 *
 * @author pfister
 *
 */
public class WorkspaceUtils implements ResourceLocation {

   //org.isoe.fwk.utils.debug.WorkspaceUtils
	private static final boolean LOG = false;
	private static final String PLUGIN_ID = "org.isoe.fwk.utils.debug";////"org.isoe.eval.popup";
	private static WorkspaceUtils instance;
	private String hostWorkspace;
	private String workspace;
	private List<String> hostProjects;
	private List<String> workspaceprojects;
	private List<String> logs = new ArrayList<String>();
	private boolean deployed;
   private boolean refreshDisabled;




	public void disableRefresh(boolean refreshDisabled) {
     this.refreshDisabled = refreshDisabled;
   }

   public void initLog() {
		logs = new ArrayList<String>();
	}

	public List<String> getWorkspaceprojects() {
		return workspaceprojects;
	}

	public String getWorkspace() {
		return workspace;
	}

	private WorkspaceUtils() {
		super();
		hostWorkspace = getWorkspaceDirectory(checkDeployed())
				+ File.separator;
	}


	private void log_Projects() {
		Date d = new Date();
		if (deployed)
			log("--- deployed mode "+d.toLocaleString());
		else
			log("--- debugging mode "+d.toLocaleString());
		loghr();
		int srccount = 0;
		log("projects");
		for (String prj : workspaceprojects) {
			log(prj);
			if (srccount++ == 5) {
				log("...");
				break;
			}
		}
		loghr();
		if (!deployed) {
			log("host projects");
			srccount = 0;
			List<String> srcprjs = hostProjects;
			for (String srcprj : srcprjs) {
				log(srcprj);
				if (srccount++ == 5) {
					log("...");
					break;
				}
			}
		}
		loghr();
	}


	public void setWorkspace(String workspace) {
		if (workspace != this.workspace){
		   this.workspace = workspace;
		   log("hostWorkspace=" + hostWorkspace);
		   log("workspace=" + workspace);
		   loghr();
		   deployed = hostWorkspace.equals(workspace);
		   workspaceprojects = getOpenProjects();
		   log_Projects();
		}
	}

	private String getWorkspaceDirectory(boolean deployed) {
		if (deployed)
			return getDWorkspaceDirectory();
		else
			return getNDWorkspaceDirectory();
	}

	public List<String> getPluginProjects() {
		return getOpenProjects();
	}

	private List<String> getPluginProjects(boolean deployed) {
		if (deployed)
			return getOpenProjects();
		else
			return getHostPluginProjects();
	}

	private List<String> getOpenProjects() {
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

	  public List<IProject> getOpenedProjects() {
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


	private List<String> getHostPluginProjects() {
		List<String> projects = new ArrayList<String>();
		try {
			File hostPluginsDirectory = new File(getHostPluginFilePath())
					.getParentFile();
			File[] files = hostPluginsDirectory.listFiles();
			if (files!=null)
			for (int i = 0; i < files.length; i++)
				if (files[i].isDirectory()) {
					File[] subfiles_ = files[i].listFiles(); //FP140108x
					if (subfiles_!=null)
					for (File file : subfiles_) {
						if (file.getName().equals(".project")) {
							projects.add(files[i].getName());
							break;
						}
					}
				}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return projects;
	}

	private String getDWorkspaceDirectory() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot wsroot = workspace.getRoot();
		IPath loc = wsroot.getLocation();
		String worspaceRoot = loc.toString();
		File workspaceDirectory = null;
		workspaceDirectory = new File(worspaceRoot);
		return workspaceDirectory.getAbsolutePath();
	}

	private String getNDWorkspaceDirectory() {
		try {
			//String pfp = getPluginFilePath();
			//File pf = new File(pfp);
			//File pfpf = pf.getParentFile();
			//String ap = pfpf.getAbsolutePath();

			return new File(getHostPluginFilePath()).getParentFile()
					.getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private String getHostPluginFilePath() throws IOException {
		String className = WorkspaceUtils.class.getName();
		int index = className.lastIndexOf(".");
		String claz = index == -1 ? className : className.substring(index + 1)
				+ ".class";
		URL ur = WorkspaceUtils.class.getResource(claz);
		String fileUri = ur.toString();
		if (fileUri.startsWith("file:/"))
			fileUri = fileUri.substring("file:/".length());
		if (EMFPlugin.IS_ECLIPSE_RUNNING) {
			URL url = FileLocator.toFileURL(ur);
			fileUri = FileLocator.toFileURL(url).getFile();
		}
		fileUri = fileUri.substring(0, fileUri.indexOf(PLUGIN_ID) + PLUGIN_ID.length())
				+ "/";
		File f = new File(fileUri);
		return f.toString();
	}

	private String getHostPluginDirectory(String pluginid) throws IOException {
		File pluginsDirectory = new File(getHostPluginFilePath()).getParentFile();
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

	private boolean checkDeployed() {
		hostProjects = getPluginProjects(false);
		boolean deployed = !findMe(hostProjects);
		if (deployed)
			log("deployed as a plugin\n");
		else {
			workspaceprojects = getOpenProjects();
			log("under test\n");
		}
		return deployed;
	}

	private boolean findMe(List<String> projects) {
		for (String prj : projects) {
			if (prj.equals(PLUGIN_ID))
				return true;
		}
		return false;
	}

	public boolean isDeployed() {
		return deployed;
	}

	public List<String> getHostProjects() {
		return hostProjects;
	}

	public List<String> getWorkspaceProjects() {
		return workspaceprojects;
	}


	public String getHostWorkspace() {
		return hostWorkspace;
	}

	public static WorkspaceUtils getInstance() {
		if (instance == null)
			instance = new WorkspaceUtils();
		return instance;
	}



	public  IProject getProject(String name) {
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


   public void refreshProject(String name, SubProgressMonitor subProgressMonitor) {
      if (refreshDisabled)
         return;
      try {
       IProject project = getProject(name);
       if (project==null)
          throw new RuntimeException("Project "+name+" does not exist");
       project.refreshLocal(IResource.DEPTH_INFINITE,
             subProgressMonitor);
    } catch (CoreException e) {
       e.printStackTrace();
    }
   }



   public void refreshProject(String name, boolean join) { //FP130816 // FP120314 // FP120318
	  if (refreshDisabled)
         return;
      try {
        IProject project = getProject(name);
		if (project!= null)
        	 project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
         if (join){
         try {
            Job.getJobManager().join(ResourcesPlugin.FAMILY_MANUAL_REFRESH,
                  null);

            Job.getJobManager().join(ResourcesPlugin.FAMILY_AUTO_REFRESH, null);
         } catch (OperationCanceledException e) {
            e.printStackTrace();
            System.err.println("********* while refreshProject *************");
         } catch (InterruptedException e) {
            System.err.println("********* while refreshProject *************");
            e.printStackTrace();
         }
         }
       } catch (CoreException e) {
         System.err.println("********* while refreshProject *************");
         e.printStackTrace();
      }
   }



   public void refreshIfExists(String projectName, boolean j) {
      if (refreshDisabled)
         return;
      try {
         IProject project = getProject(projectName);
         if (project != null) {
            project.refreshLocal(IResource.DEPTH_INFINITE,
                  new NullProgressMonitor());
          if (j){
            Job.getJobManager().join(ResourcesPlugin.FAMILY_MANUAL_REFRESH,
                  null);
            Job.getJobManager().join(ResourcesPlugin.FAMILY_AUTO_REFRESH,
                  null);
          }
         }
      } catch (CoreException e) {
         System.err.println(" in refreshIfExists");
         e.printStackTrace();
      } catch (OperationCanceledException e) {
         System.err.println(" in refreshIfExists");
         e.printStackTrace();
      } catch (InterruptedException e) {
         System.err.println(" in refreshIfExists");
         e.printStackTrace();
      }
   }


	public List<String> getLogs() {
		return logs;
	}

	private void log(String m) {
		if (!LOG)
			return;
		logs.add(m);
		System.out.println(m);
	}

	private void logln() {
		if (!LOG)
			return;
		logs.add("<br>");
		System.out.println();
	}

	private void loghr() {
		if (!LOG)
			return;
		logs.add("<hr>");
		System.out.println("-------------------------");
	}


}
