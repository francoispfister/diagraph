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
package org.isoe.diagraph.xsl.transformer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.emf.ecore.EPackage;
import org.isoe.extensionpoint.xsl.transformer.IXslTransformer;

/**
 * 
 * @author fpfister
 * 
 */
public class XslTransformer extends Action implements IXslTransformer {

	private String launcherName;
	// private IDiagraphControler controler;
	// org.isoe.diagraph.xsl.launcher.XslLauncher
	private static final String LAUNCHER_XSL = "org.eclipse.wst.xsl.launching.launchConfigurationType";

	private EPackage ePackage;
	private String project = "my.gmf.prj";
	private String modelfolder = "model";
	private String input = "World.xmi";

	private String parametername = "supermarketname";
	private String parametervalue = "tralala";

	@Override
	public void setParametername(String parametername) {
		this.parametername = parametername;
	}

	@Override
	public void setParametervalue(String parametervalue) {
		this.parametervalue = parametervalue;
	}

	@Override
	public void setProject(String project) {
		this.project = project;
	}

	@Override
	public void setModelfolder(String modelfolder) {
		this.modelfolder = modelfolder;
	}

	@Override
	public void setInput(String input) {
		this.input = input;
	}

	@Override
	public void setLauncherName(String launcherName) {
		this.launcherName = launcherName;
	}

	public void run() {
		try {

			launch();
			// else
			// clog("XSLL**********");
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	public void createFromTemplate_old(String templateSheet, String targetSheet,
			String[][] replace) throws IOException {
		File inclfile = new File(templateSheet);
		InputStream in = new FileInputStream(inclfile);
		StringBuffer buffer = new StringBuffer();
		try {
			InputStreamReader inR = new InputStreamReader(in);
			BufferedReader buf = new BufferedReader(inR);
			String line;
			while ((line = buf.readLine()) != null) {
				String b = null;
				for (int i = 0; i < replace.length; i++) {
					if (line.contains(replace[i][0])) {
						b = line.replaceAll(replace[i][0], replace[i][1]);
						break;
					}
				}
				if (b == null)
					b = line;
				buffer.append(b).append("\n");
			}
		} finally {
			in.close();
		}
		OutputStream os = new FileOutputStream(targetSheet);
		os.write(buffer.toString().getBytes());
		os.close();
	}
	
	
	
	private String replace(String line, String[][] replace){
		String b = null;
		for (int i = 0; i < replace.length; i++) {
			if (line.contains(replace[i][0])) {
				b = line.replaceAll(replace[i][0], replace[i][1]);
				break;
			}
		}
		return b;
	}	

	/**
	 * this method can replace only one item per line...
	 * @param templateSheet
	 * @param targetSheet
	 * @param replace
	 * @throws IOException
	 */
	public void createFromTemplate(String templateSheet, String targetSheet,
			String[][] replace) throws IOException {
		File inclfile = new File(templateSheet);
		InputStream in = new FileInputStream(inclfile);
		StringBuffer buffer = new StringBuffer();
		try {
			InputStreamReader inR = new InputStreamReader(in);
			BufferedReader buf = new BufferedReader(inR);
			String line;
			while ((line = buf.readLine()) != null) {
				String result = line;
				do {
					String sv = result;
					result = replace(result,replace);
					if (result == null){
						result = sv;
						break;
					}
				} while (true);
				buffer.append(result).append("\n");	
			}
		} finally {
			in.close();
		}
		OutputStream os = new FileOutputStream(targetSheet);
		os.write(buffer.toString().getBytes());
		os.close();
	}
		
	



	private void launch() throws CoreException {

		IWorkbenchPage activePage = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		activePage.showView(IConsoleConstants.ID_CONSOLE_VIEW);

		ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();

		ILaunchConfigurationType type = manager
				.getLaunchConfigurationType(LAUNCHER_XSL);
		if (LOG)
			clog(launcherName);

		for (ILaunchConfiguration conf : manager.getLaunchConfigurations(type)) {
			if (conf.getName().equals(launcherName)) {
				conf.delete();
				break;
			}
		}

		ILaunchConfigurationWorkingCopy config = type.newInstance(null,
				launcherName);

		// controler

		// //launcher.setInput("a0.simpleworld");

		String ext = input.substring(input.lastIndexOf("."));
		String trg = input.substring(0, input.lastIndexOf(".")) + "_bis" + ext;
		String folder = "/" + project + "/" + modelfolder;
		String src = folder + "/" + input;
		String stylesheetfolder = "transfo/";
		String ssprj = "org.isoe.fwk.xsl.transformation"; // FP130519
		String refactoreStyleSheet = "ecorerefactor.xsl";
		String pathType = "external";

		File ssfolder = new File(CommonPlugin.resolve(
				org.eclipse.emf.common.util.URI.createPlatformPluginURI(ssprj,
						false)).toFileString());
		String stylesheet = ssfolder.getAbsolutePath() + File.separator
				+ stylesheetfolder + refactoreStyleSheet;

		String inclsheet = ssfolder.getAbsolutePath() + File.separator
				+ stylesheetfolder + "include.xsl";

		String inclsheetv1 = ssfolder.getAbsolutePath() + File.separator
				+ stylesheetfolder + "var.xsl";

		String[][] rs = getSampleTransformation();
		

		try {
			createFromTemplate(inclsheet, inclsheetv1,rs);
		} catch (IOException e1) {
		
			e1.printStackTrace();
		}

		//if (ePackage.getName().equals("simpleworld"))
		//	;

		clog("transfo xslt " + src + " -> " + trg);

		config.setAttribute(
				"org.eclipse.wst.xsl.jaxp.launching.ATTR_ATTRIBUTES",
				"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?> <Attributes/> ");
		config.setAttribute(
				"org.eclipse.wst.xsl.jaxp.launching.ATTR_OUTPUT_PROPERTIES",
				"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?> <Properties/> ");
		config.setAttribute(
				"org.eclipse.wst.xsl.jaxp.launching.ATTR_USE_DEFAULT_PROCESSOR",
				true);
		config.setAttribute(
				"org.eclipse.wst.xsl.jaxp.launching.INVOKER_DESCRIPTOR",
				"org.eclipse.wst.xsl.launching.jaxp.invoke");
		config.setAttribute("org.eclipse.wst.xsl.launching.ATTR_FORMAT_FILE",
				false);
		config.setAttribute("org.eclipse.wst.xsl.launching.ATTR_INPUT_FILE",
				"${workspace_loc:" + src + "}");
		config.setAttribute("org.eclipse.wst.xsl.launching.ATTR_OPEN_FILE",
				false);
		config.setAttribute(
				"org.eclipse.wst.xsl.launching.ATTR_OUTPUT_FILENAME", trg);
		config.setAttribute("org.eclipse.wst.xsl.launching.ATTR_OUTPUT_FOLDER",
				"${workspace_loc:" + folder + "}");
		config.setAttribute("org.eclipse.debug.ui.ATTR_LAUNCH_IN_BACKGROUND",
				false);
		config.setAttribute(
				"org.eclipse.wst.xsl.launching.ATTR_PIPELINE",
				"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?> <Pipeline> <OutputProperties/> <Transform path=\""
						+ stylesheet
						+ "\" pathType=\""
						+ pathType
						+ "\" uriResolver=\"\"> <Parameters> <Parameter name=\""
						+ parametername
						+ "\" value=\""
						+ parametervalue
						+ "\"/> </Parameters> </Transform> </Pipeline> ");
		config.setAttribute(
				"org.eclipse.wst.xsl.launching.ATTR_USE_DEFAULT_OUTPUT_FILE",
				false);

		// save and launch
		ILaunchConfiguration configuration = config.doSave();
		// DebugUITools.launch(configuration, ILaunchManager.);
		DebugUITools.launch(configuration, ILaunchManager.RUN_MODE);

	}

	/**
	 * the renames the xpath designed element with the value 1
	 * @return
	 */
	private String[][] getSampleTransformation() {
	   //String nsUri= ePackage.getNsURI();
		String[] r0 = { "value2",ePackage.getNsURI() };
	//	String[] r0 = { "value2","http://opendsml.org/master/basic/simpleworld.v0" };
		//String[] r1 = { "value0", "ClassmateSystem/school/classrooms/@*" };
		//String[] r1 = { "value0", "ClassmateSystem/school/@classrooms.1/@*" };
		String[] r1 = { "value0", "ClassmateSystem/school/classrooms[*]/students[1]/@name" };
		//String[] r1 = { "value0", "World/things/@*" };
		//String[] r1 = { "value0", "World/@*" };
		//String[] r1 = { "value0", "World/things/@name" };
		//String[] r1 = { "value0", "//@name" };
		String[] r2 = { "value1", "John Doe" };
		String[][] rs = new String[3][2];
		rs[0] = r0;
		rs[1] = r1;
		rs[2] = r2;
		return rs;
	}

	private static final boolean LOG = false;

	private boolean silent = false;

	private void clog(String mesg) {
		if (LOG && !silent)
			System.out.println(mesg);
	}

	/*
	 * @Override public void setControler(IDiagraphControler controler) { //
	 * TODO Auto-generated method stub
	 * 
	 * }
	 */
	@Override
	public void setEPackage(EPackage packag) {
		this.ePackage = packag;
	}

	@Override
	public boolean isQualified() {
		return true;
	}

	@Override
	public boolean isStub() {
		return false;
	}

	@Override
	public void setSilent(boolean silent) {
		this.silent=silent;
	}
}
