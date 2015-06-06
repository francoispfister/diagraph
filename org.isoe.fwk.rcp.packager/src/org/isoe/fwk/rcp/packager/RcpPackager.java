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
package org.isoe.fwk.rcp.packager;
import org.eclipse.ant.internal.launching.launchConfigurations.AntProcess;
import org.eclipse.ant.launching.IAntLaunchConstants;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.ILaunchesListener2;
import org.eclipse.debug.core.IStreamListener;
import org.eclipse.debug.core.model.IStreamMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.externaltools.internal.model.IExternalToolConstants;


/*
http://pweclipse.blogspot.fr/2010/09/simple-rcp-product-build.html
http://www.vogella.com/articles/EclipseTycho/article.html
http://help.eclipse.org/galileo/index.jsp?topic=/org.eclipse.platform.doc.isv/guide/p2_repositorytasks.htm
http://blog.ankursharma.org/2010/06/headless-build-for-beginners-part-i.html
http://www.eclipse.org/articles/Article-Launch-Framework/launch.html
http://www.eclipse.org/articles/Article-Java-launch/launching-java.html
http://www.ibm.com/developerworks/opensource/tutorials/os-ecl-rcpapp/
http://www.ibm.com/developerworks/opensource/library/os-eclipse-brand/
http://www.eclipsezone.com/eps/10minute-rcp/
http://wiki.eclipse.org/FAQ_How_do_I_create_an_Eclipse_product%3F
http://wiki.eclipse.org/FAQ_How_do_I_create_an_Eclipse_product%3F
http://eclipse.org/articles/product-guide/guide.html
http://www.ant4eclipse.org/node/81
 */

public class RcpPackager extends Action {
	//org.isoe.fwk.antlaucher.AntLauncher
	static final String LAUNCHER_NAME="my ant launcher";
	static final String PATH_TO_ANT_SCRIPT="E:\\Apps\\workspaces\\ws-integr-c6a\\mytest\\projectBuilder.xml";

	public void run(){
		try {
			launchAntTask();
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void launchAntTask() throws CoreException{
	
		final IWorkbenchPage activePage = PlatformUI.getWorkbench()
		        .getActiveWorkbenchWindow()
		        .getActivePage();
		activePage.showView(IConsoleConstants.ID_CONSOLE_VIEW);
	
		final ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
	
		ILaunchConfigurationType type = manager
				.getLaunchConfigurationType(IAntLaunchConstants.ID_ANT_LAUNCH_CONFIGURATION_TYPE);
		
		//org.eclipse.pde.ui.RuntimeWorkbench
		
		
		final ILaunchConfigurationWorkingCopy workingCopy = type.newInstance(null, LAUNCHER_NAME);
		workingCopy.setAttribute(ILaunchManager.ATTR_PRIVATE, true);
		workingCopy.setAttribute(IExternalToolConstants.ATTR_LOCATION, PATH_TO_ANT_SCRIPT);
		final ILaunch launch = workingCopy.launch(ILaunchManager.RUN_MODE, null);
		// make sure the build doesnt fail
		final boolean[] buildSucceeded = new boolean[] { true };
		((AntProcess) launch.getProcesses()[0]).getStreamsProxy()
		        .getErrorStreamMonitor()
		        .addListener(new IStreamListener() {
		            @Override
		            public void streamAppended(String text, IStreamMonitor monitor) {
		                if (text.indexOf("BUILD FAILED") > -1) {
		                    buildSucceeded[0] = false;
		                }
		            }
		        });
		manager.addLaunchListener(new ILaunchesListener2(){

			@Override
			public void launchesRemoved(ILaunch[] launches) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void launchesAdded(ILaunch[] launches) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void launchesChanged(ILaunch[] launches) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void launchesTerminated(ILaunch[] launches) {
		        boolean patchSuccess = false;
		        try {
		            if (!buildSucceeded[0]) {
		                throw new Exception("Build FAILED!");
		            }
		            for (int i = 0; i < launches.length; i++) {
		                if (launches[i].equals(launch)
		                        && buildSucceeded[0]
		                        && !((IProgressMonitor) launches[i].getProcesses()[0]).isCanceled()) {
		                    //[*** DO YOUR THING... ***]
		                    break;
		                }
		            }
		        } catch (Exception e) {
		            //[*** DO YOUR THING... ***]
		        } finally {
		            // get rid of this listener
		            manager.removeLaunchListener(this);
		            //[*** DO YOUR THING... ***]
		        }
		    }
			
		});
	

		

	}


}
