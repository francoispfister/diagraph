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
package org.eclipse.jdt.launching.examples.tomcat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jdt.launching.IRuntimeClasspathEntry;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.IVMInstallType;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.IConsoleConstants;
import org.isoe.extensionpoint.deployer.IHttpService;



public class TomcatService extends Action implements IHttpService, IJavaLaunchConfigurationConstants {
	//org.eclipse.jdt.launching.examples.tomcat.TomcatService
	
	private  IVMInstall jre;
	private String vmName;
	private IPath tomcatHome;
	private boolean start;
	private static final String START_LAUNCHER_NAME = "Start Tomcat";
	private static final String STOP_LAUNCHER_NAME =  "Stop Tomcat";
	
	@Override
	public void setVm(String location) { //"E:\\Java\\jdk1.7.0_03"
		IVMInstallType[] types = JavaRuntime.getVMInstallTypes();
		for (int i = 0; i < types.length; i++) {
			IVMInstallType type = types[i];
			System.out.println(type.toString());
			IVMInstall[] jres_ = type.getVMInstalls();
			for (IVMInstall ivmInstall : jres_) {
				System.out.println(ivmInstall.getInstallLocation().getPath().toString());
				if (ivmInstall.getInstallLocation().getPath().equals(location)){
				    setVm(ivmInstall);
					break;
				}
			}		
		}
		if (jre==null)
			System.out.println("no jvm for Tomcat !!!!");
	}
	

	@Override
	public void setTomcatHome(String tomcatHome) { //"E:\\Apps\\apache-tomcat-7.0.39"
		this.tomcatHome = new Path(tomcatHome);
		try {
			JavaCore.setClasspathVariable("TOMCAT_HOME", this.tomcatHome);
		} catch (Exception e) {
			System.out.println("bad TOMCAT_HOME !!!!");
		}	
	}
	@Override
	public void setStart(){
		start=true;
	}

	@Override
	public void setStop(){
		start=false;
	}
	
	
	private  void setVm(IVMInstall vm) {
		jre = vm;
		vmName = vm.getName();	
		System.out.println("using VM: " + vmName);
	}

	
	/*
start "Tomcat" "E:\Java\jre7\bin\java"  
-Djava.util.logging.config.file="E:\Apps\apache-tomcat-7.0.39\conf\logging.properties" 
-Djava.util.logging.manager=org.apache.juli.ClassLoaderLogManager   
-Djava.endorsed.dirs="E:\Apps\apache-tomcat-7.0.39\endorsed" 
-classpath "E:\Apps\apache-tomcat-7.0.39\bin\bootstrap.jar;E:\Apps\apache-tomcat-7.0.39\bin\tomcat-juli.jar" 
-Dcatalina.base="E:\Apps\apache-tomcat-7.0.39" 
-Dcatalina.home="E:\Apps\apache-tomcat-7.0.39" -Djava.io.tmpdir="E:\Apps\apache-tomcat-7.0.39\temp" org.apache.catalina.startup.Bootstrap  
start
	 */
	

	
	
	
		
	public void run() {
		final IWorkbenchPage activePage = PlatformUI.getWorkbench()
		        .getActiveWorkbenchWindow()
		        .getActivePage();
		
		try {
			activePage.showView(IConsoleConstants.ID_CONSOLE_VIEW);
			
			if (jre==null){
				ErrorDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Error", "Unable to find a JRE",
						new Status(IStatus.ERROR, "1654654", 0, "Must use an installed JRE.", null));
				return;
			}
			
			IPath path = JavaCore.getClasspathVariable("TOMCAT_HOME");
			if (path == null) {
					ErrorDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Error", "Unable to launch Tomcat",
					new Status(IStatus.ERROR, "1654654", 0, "Must define Java classpath variable TOMCAT_HOME to reference root of Tomcat installation.", null));
				return;
			}
			
			ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
			ILaunchConfigurationType type = manager.getLaunchConfigurationType(ID_JAVA_APPLICATION);
		
			String launcherName_=start?START_LAUNCHER_NAME:STOP_LAUNCHER_NAME;
			ILaunchConfiguration[] configurations = manager.getLaunchConfigurations(type);
			for (int i = 0; i < configurations.length; i++) {
				ILaunchConfiguration configuration = configurations[i];
				if (configuration.getName().equals(launcherName_)) {
					configuration.delete();
					break;
				}
			}
			ILaunchConfigurationWorkingCopy workingCopy = type.newInstance(null, launcherName_);
			
			// specify a JRE
			workingCopy.setAttribute(ATTR_VM_INSTALL_NAME, jre.getName());
			workingCopy.setAttribute(ATTR_VM_INSTALL_TYPE, jre.getVMInstallType().getId());
			
			// specify main type and program arguments
			workingCopy.setAttribute(ATTR_MAIN_TYPE_NAME, "org.apache.catalina.startup.Bootstrap");
			String cmd=start?"start":"stop";
			workingCopy.setAttribute(ATTR_PROGRAM_ARGUMENTS, cmd);
			
			List classpath = new ArrayList();
			
			// specify classpath
			File jdkHome = jre.getInstallLocation();
			IPath toolsPath = new Path(jdkHome.getAbsolutePath()).append("lib").append("tools.jar");
			IRuntimeClasspathEntry toolsEntry = JavaRuntime.newArchiveRuntimeClasspathEntry(toolsPath);
			toolsEntry.setClasspathProperty(IRuntimeClasspathEntry.USER_CLASSES);
			classpath.add(toolsEntry.getMemento());
			
			IPath bootstrapPath = new Path("TOMCAT_HOME").append("bin").append("bootstrap.jar");
			IRuntimeClasspathEntry bootstrapEntry = JavaRuntime.newVariableRuntimeClasspathEntry(bootstrapPath);
			bootstrapEntry.setClasspathProperty(IRuntimeClasspathEntry.USER_CLASSES);
			classpath.add(bootstrapEntry.getMemento());
	
			IPath loggerPath = new Path("TOMCAT_HOME").append("bin").append("tomcat-juli.jar");
			IRuntimeClasspathEntry loggerentry = JavaRuntime.newVariableRuntimeClasspathEntry(loggerPath);
			loggerentry.setClasspathProperty(IRuntimeClasspathEntry.USER_CLASSES);
			classpath.add(loggerentry.getMemento());

	
			IPath systemLibsPath = new Path(JavaRuntime.JRE_CONTAINER);
			IRuntimeClasspathEntry systemLibsEntry = JavaRuntime.newRuntimeContainerClasspathEntry(systemLibsPath, IRuntimeClasspathEntry.STANDARD_CLASSES);
			classpath.add(systemLibsEntry.getMemento());
			
			workingCopy.setAttribute(ATTR_CLASSPATH, classpath);
			workingCopy.setAttribute(ATTR_DEFAULT_CLASSPATH, false);
			
			// specify System properties
			workingCopy.setAttribute(ATTR_VM_ARGUMENTS, "-Djava.endorsed.dirs=\"..\\common\\endorsed\" -Dcatalina.base=\"..\" -Dcatalina.home=\"..\" -Djava.io.tmpdir=\"..\\temp\"");
			
			// specify working diretory
			File workingDir = JavaCore.getClasspathVariable("TOMCAT_HOME").append("bin").toFile();
			workingCopy.setAttribute(ATTR_WORKING_DIRECTORY, workingDir.getAbsolutePath());
			
			// save and launch
			ILaunchConfiguration configuration = workingCopy.doSave();
			DebugUITools.launch(configuration, ILaunchManager.RUN_MODE);
			
		} catch (CoreException e) {
		}
	}


	

}
