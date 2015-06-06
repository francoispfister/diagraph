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
package org.isoe.diagraph.refresher.actions;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;
import org.isoe.diagraph.osem.constants.OsemConfiguration;

/**
 *
 * @author fpfister
 *
 */
public class Refresher  {


	public void runRefresh() {

		Job job = new Job("Diagraph Refresher") {
			@Override
			protected IStatus run(org.eclipse.core.runtime.IProgressMonitor monitor) {
				try {
					while(true){
						sleep(2000);
						//refreshProject("lang.m2.kerm.kfsm.samplerun",false);
						refreshProject(OsemConfiguration.DSL1_M1_EXAMPLES[0],false); //FP140428
					}
				} catch (Exception e) {
					System.err.println("error in runRefresh");
				}
				return Status.OK_STATUS;
			}

			public IProject getProject(String name) {
			      IWorkspace ws = ResourcesPlugin.getWorkspace();
			      org.eclipse.core.runtime.Path rootlocation = (org.eclipse.core.runtime.Path) ws.getRoot()
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

			 private void refreshProject(String name, boolean join) { //FP130816 // FP120314 // FP120318

			      try {

			         IProject project = getProject(name);
			         if (project==null)
			            throw new RuntimeException("Project "+name+" does not exist");
			         project.refreshLocal(IResource.DEPTH_INFINITE,
			               new NullProgressMonitor());
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


			private void sleep(int timeout) {
				if (org.eclipse.swt.widgets.Display.getCurrent() == null)
					return;
				final long start = System.currentTimeMillis();
				final long deltaMillis = timeout;
				do {
					while (Display.getCurrent().readAndDispatch()) {
						;
					}
				} while ((System.currentTimeMillis() - start) < deltaMillis);
			}

		};
		job.setUser(false);
		job.schedule(0);
	}


	/*
	public static void main(String[] a) {
		Refresher refresher = new Refresher();
		refresher.runRefresh();
	}
*/



}
