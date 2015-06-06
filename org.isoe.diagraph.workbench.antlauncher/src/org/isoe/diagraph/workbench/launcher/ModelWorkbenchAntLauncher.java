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
package org.isoe.diagraph.workbench.launcher;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jface.action.Action;
import org.eclipse.pde.core.plugin.TargetPlatform;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.IConsoleConstants;
import org.isoe.diagraph.preferences.DiagraphPreferences;
import org.isoe.extensionpoint.mwb.launcher.IModelWorkbenchLauncher;
import org.isoe.fwk.core.DParams;

/**
 *
 * @author "fpfister"
 *
 */
import org.isoe.diagraph.controler.IDiagraphControler;  //FP140707refactored
import org.isoe.extensionpoint.diagraph.action.DiagraphService;  //FP140707_c_refactored
public class ModelWorkbenchAntLauncher extends Action implements
		IJavaLaunchConfigurationConstants, ILauncherSettings, IModelWorkbenchLauncher {

	private String launcherName;
	private IDiagraphControler controler;
	private static final String LAUNCHER_PDE_RUNTIME = "org.eclipse.pde.ui.RuntimeWorkbench";

	public void setLauncherName(String launcherName) {
		this.launcherName = launcherName;
	}

	public void run() {
		try {
			launch();
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	private void launch() throws CoreException {

		IWorkbenchPage activePage = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		activePage.showView(IConsoleConstants.ID_CONSOLE_VIEW);

		ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();

		ILaunchConfigurationType type = manager
				.getLaunchConfigurationType(LAUNCHER_PDE_RUNTIME);
		if (LOG)
		  clog(launcherName);

		ILaunchConfiguration[] configurations = manager
				.getLaunchConfigurations(type);
		for (int i = 0; i < configurations.length; i++) {
			ILaunchConfiguration configuration = configurations[i];
			if (configuration.getName().equals(launcherName)) {
				configuration.delete();
				break;
			}
		}

		ILaunchConfigurationWorkingCopy workingCopy = type.newInstance(null,
				launcherName);

		if (LOG) {
			String defaultLocation = TargetPlatform.getDefaultLocation();
			clog("defaultLocation " + defaultLocation); // //E:\Apps\eclipse-diagraph-deploy-130320\eclipse
			String locat = TargetPlatform.getLocation();
			clog("Location " + locat); // E:\Apps\eclipse-diagraph-deploy-130320\eclipse
			String blist = TargetPlatform.getBundleList();
			clog("BundleList " + blist);// org.eclipse.equinox.simpleconfigurator@1:start
			String defaultproduct = TargetPlatform.getDefaultProduct();
			clog("DefaultProduct " + defaultproduct);// org.eclipse.platform.ide
			String[] apps = TargetPlatform.getApplications();
			if (LOG){
			 clog("Applications ");
			 String aps="";
			 for (String app : apps)
				//rg.eclipse.emf.cdo.server.app org.eclipse.emf.codegen.CodeGen org.eclipse.emf.codegen.JMerger org.eclipse.emf.codegen.ecore.Generator
				aps+=(app + " "); // isoe.case4.diagram.Case4Application
												// org.eclipse.emf.cdo.server.app

			  clog(aps);
			}
		}

		String userName = "fpfister";
		String teamName = "ISOE team - LGI2P EMA - Nimes (France)";
		String teamNamespace = "isoe.lang";

		userName = DiagraphPreferences.getUserName__(); // FP130511
		teamName = DiagraphPreferences.getTeamName__();
		teamNamespace = DiagraphPreferences.getTeamNamespace();

		String initialstate = DiagraphPreferences.getConnectState();
		if (!initialstate.equals(DParams.CONNECTED_TO_REMOTE_REPOSITORY))
			// controler.showMessage("you are not connected to a remote repository");
			controler.log("no-remote-repository",
					"you are not connected to a remote repository");
/*
		String vmargs = "-Dosgi.requiredJavaVersion=1.5 -Duser.name=\""
				+ userName + "\" -Dteam.namespace=\"" + teamNamespace
				+ "\" -Dteam.name=\"" + teamName + "\" -Xms40m -Xmx512m";
		*/
		//FP131204

		String vmargs = "-Dosgi.requiredJavaVersion=1.5 -Duser.name=\""
				+ userName + "\" -Dteam.namespace=\"" + teamNamespace
				+ "\" -Dteam.name=\"" + teamName + "\" -Xms128m -Xmx512m -XX:MaxPermSize=350m -XX:ReservedCodeCacheSize=180m";
		// -Xms256m  -Xmx512m  -XX:MaxPermSize=350m  -XX:ReservedCodeCacheSize=180m
		workingCopy.setAttribute(VMARGS, vmargs);

		String progargs = "-os ${target.os} -ws ${target.ws} -arch ${target.arch} -nl ${target.nl} -consoleLog";
		workingCopy.setAttribute(PROGARGS, progargs);

		workingCopy.setAttribute(ASKCLEAR, true);
		workingCopy.setAttribute("automaticAdd", true);
		workingCopy.setAttribute("automaticValidate", false);
		workingCopy.setAttribute("bootstrap", "");
		workingCopy.setAttribute(TRACING_CHECKED, TRACING_NONE);

		// Configuration tab
		workingCopy.setAttribute(CONFIG_CLEAR_AREA, false);
		workingCopy.setAttribute("clearwslog", false);
		workingCopy.setAttribute(CONFIG_LOCATION,
				"${workspace_loc}/.metadata/.plugins/org.eclipse.pde.core/"
						+ launcherName);
		workingCopy.setAttribute(CONFIG_GENERATE_DEFAULT, true);
		workingCopy.setAttribute(CONFIG_USE_DEFAULT_AREA, true);

		// Program to run
		// workingCopy.setAttribute(PRODUCT, "isoe.case4.diagram.KASE4");
		workingCopy.setAttribute(USE_PRODUCT, false);

		workingCopy.setAttribute("default", true);
		workingCopy.setAttribute("includeOptional", true);
		workingCopy.setAttribute("location", "${workspace_loc}/../"
				+ launcherName);
		workingCopy.setAttribute(
				"org.eclipse.jdt.launching.SOURCE_PATH_PROVIDER",
				"org.eclipse.pde.ui.workbenchClasspathProvider");
		workingCopy.setAttribute("pde.version", "3.3");

		workingCopy.setAttribute("show_selected_only", false);
		workingCopy.setAttribute("tracing", false);
		workingCopy.setAttribute("useCustomFeatures", false);

		// save and launch
		ILaunchConfiguration configuration = workingCopy.doSave();
		DebugUITools.launch(configuration, ILaunchManager.RUN_MODE);

	}

	@Override
	public void setControler(Object controler) {
		this.controler = (IDiagraphControler) controler;
		this.controler.registerService(this);
	}


	@Override
	public boolean isStub() {
		return false;
	}

	@Override
	public boolean isQualified() {
		return true;
	}

	private static final boolean LOG = false;

	private boolean silent;

	@Override
	public void setSilent(boolean value) {
		silent = value;
	}

	private void clog(String mesg) {
		if (LOG && !silent)
			System.out.println(mesg);
	}

	@Override
	public void setPort(int port) {
		// TODO Auto-generated method stub

	}


}
