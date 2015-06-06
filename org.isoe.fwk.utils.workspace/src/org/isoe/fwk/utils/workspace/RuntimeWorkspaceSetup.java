/*
 * Copyright (c) 2005, 2008 Borland Software Corporation
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Artem Tikhomirov (Borland) - initial API and implementation
 *    François Pfister (EMA) Adaptation to DIAGRAPH Megamodel deployment (2012)
 */
package org.isoe.fwk.utils.workspace;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.JavaCore;

//import org.isoe.diagraph.DParams;

/**
 * Running tests within PDE, we face two major problems:
 * <ol>
 * <li>Compile generated plugins against projects in our development workspaces.
 * We are not alone here, suffice it to mention {@linkplain https
 * ://bugs.eclipse.org/bugs/show_bug.cgi?id=109137} and {@linkplain https
 * ://bugs.eclipse.org/bugs/show_bug.cgi?id=182537}. Though it's possible to
 * inject development plugins into target configuration
 *
 * <pre>
 * URL url = FileLocator.resolve(Platform.getBundle(&quot;org.eclipse.gmf.validate&quot;)
 * 		.getEntry(&quot;/&quot;));
 * TargetPlatformHelper.getPDEState().addAdditionalBundles(new URL[] {});
 * </pre>
 *
 * it doesn't help, unless
 * <code>org.eclipse.jdt.internal.core.builder.NameEnvironment#computeClasspathLocations<code>
 * [can be|is] modified to support folders with classes.
 *
 * <p> For now, we managed to compile against linked binary folders, although using linked content instead of plugins
 * requires us to explicitly add some plugins earlier available through plugin re-export (namely, oe.jface.text)</p>
 *
 * <p>UPDATE: As of Eclipse 3.4 M7, PDE got better support for self-hosting and is capable compiling plugins
 * in runtime workspace against target platform! Hooray!</p>
 *
 * <li>Loading compiled classes. This can be solved either with "-dev bin" command-line argument or using
 * {@link #getReadyToStartAsBundle(IProject)} hack that updates classpath specified in manifest.mf.
 * Eclipse Testing Framework used to specify "-dev bin, runtime" (org.eclipse.test_3.1.0/library.xml), but
 * these days (as of 3.3 M6) seem to abandon this practice.
 * </ol>
 *
 * Running tests as part of the build, we don't experience troubles with
 * compiling, and classloading is solved with
 * {@link #getReadyToStartAsBundle(IProject)} hack now.
 *
 * @author artem
 * @author fpfister - adaptation to DIAGRAPH Megamodel deployment
 */

public class RuntimeWorkspaceSetup {

	//FP140321z
	private static final boolean LOG = org.isoe.fwk.core.DParams.RuntimeWorkspaceSetup_LOG;
	private final boolean isDevBinPresent;
	private String javaVersion;
	private static RuntimeWorkspaceSetup instance;
	private static long savedfileStateLongevity = -1;
	private static int savedmaxFileStates = -1;
	private static long savedmaxFileStateSize = -1;
	private static long savedsnapshotInterval = -1;

	public static RuntimeWorkspaceSetup getInstance(String javaVersion) {// do
																			// not
																			// forget
																			// to
																			// restore
																			// the
																			// workbench
																			// (unget)
		if (instance == null) {
			instance = new RuntimeWorkspaceSetup(javaVersion);
			instance.init();
		}
		return instance;
	}

	public static void releaseInstance() {
		if (instance != null) {
			instance.terminate();
			instance = null;
		}
	}

	private RuntimeWorkspaceSetup(String javaVersion) {
		this.javaVersion = javaVersion;
		List<String> l = Arrays.asList(Platform.getCommandLineArgs());
		int i;
		if ((i = l.indexOf("-dev")) != -1) {
			isDevBinPresent = i + 1 < l.size()
					&& l.get(i + 1).startsWith("bin");
		} else {
			// String osgiDevProp =
			// Plugin.getBundleContext().getProperty("osgi.dev");
			isDevBinPresent = false;// osgiDevProp!= null &&
									// osgiDevProp.contains("bin");
		}
	}

	private void init() {
		ensureJavaVersion(javaVersion); // FP120518
		try {
			IWorkspaceDescription wd = ResourcesPlugin.getWorkspace()
					.getDescription();
			turnWorkspaceHistoryOff(wd);
			switchAutobuildOff(wd);
			ResourcesPlugin.getWorkspace().setDescription(wd);
		} catch (CoreException ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}

	private void terminate() {
		try {
			IWorkspaceDescription wd = ResourcesPlugin.getWorkspace()
					.getDescription();
			restoreWorkspaceHistory(wd);
			switchAutobuildOn(wd);
			ResourcesPlugin.getWorkspace().setDescription(wd);
		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}

	}


	private String readManifest(IFile manifest) {
		String result = "";
		try {
			BufferedReader r = new BufferedReader(new InputStreamReader(
					manifest.getContents(), manifest.getCharset()));
			String line;
			while ((line = r.readLine()) != null)
				result += line + "\n";

		} catch (Exception e) {

			e.printStackTrace();
		}
		return result;
	}

	private int lengthManifest(IFile manifest) {
		String result = "";
		int len = 0;
		try {
			BufferedReader r = new BufferedReader(new InputStreamReader(
					manifest.getContents(), manifest.getCharset()));
			String line;
			while ((line = r.readLine()) != null){
				result += line + "\n";
				len++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return len;
	}



	public void exportPackages(IProject project, String[] packages) {
		IFile manifest = project.getFile("META-INF/MANIFEST.MF");
		String content = readManifest(manifest);
		if (content.contains("Export-Package:"))
			completeExport(project,packages);
		else
		    appendExport(project, packages);
	}


	public void getReadyToStartAsBundle(IProject project) {
		clog("getReadyToStartAsBundle" + " - " + project.getName());
		if (isDevBinPresent) {
			return; // no sense
		}
		try {
			IFile manifest = project.getFile("META-INF/MANIFEST.MF");
			if (manifest.exists()) {
				BufferedReader r = new BufferedReader(new InputStreamReader(
						manifest.getContents(), manifest.getCharset()));
				String line;
				boolean found = false;
				StringBuilder result = new StringBuilder();
				// XXX not so good assumption that Bundle-ClassPath fits single
				// line
				// If classpath spans few lines, we might not notice bin/ and
				// append it twice, with some bogus empty entries in between,
				// which may lead to smth like
				// java.lang.IllegalArgumentException: Path must include project
				// and resource name: /org.sample.prim.diagram
				while ((line = r.readLine()) != null) {
					if (LOG)
						clog(line);
					result.append(line);
					if (!found && line.startsWith("Bundle-ClassPath:")) {
						if (line.indexOf("bin/") == -1 && line.indexOf(",bin") == -1) {
							result.append(", bin/");
							clog("added bin/ to Bundle-ClassPath");
						}
						found = true;
					}
					result.append("\n");
				}
				if (!found) {
					result.insert(0, "Bundle-ClassPath: bin/, .\n");
					clog("added Bundle-ClassPath: bin/, .");
				}
				manifest.setContents(new ByteArrayInputStream(result.toString()
						.getBytes(manifest.getCharset())), true, false,
						new NullProgressMonitor());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}


	private void append(StringBuffer bufr,String what){
		bufr.append(what);
	}

	private void appendExport_(StringBuffer bufr,String[] packages){
		for (int i = 0; i < packages.length; i++) {
			if (i == 0){
				if (i < packages.length - 1)
					bufr.append("Export-Package: ").append(packages[i]).append (",\n");
				else
					bufr.append("Export-Package: ").append( packages[i]) .append( "\n");
			} else{
			  if (i < packages.length - 1)
				bufr.append(" ").append( packages[i]).append( ",\n");
			  else
				bufr.append(" ").append( packages[i] ).append( "\n");
			}
		}
	}


	private void addExport(StringBuffer bufr,List<String> packages){
		for (int i = 0; i < packages.size(); i++) {
		    if (i < packages.size() - 1)
				bufr.append(" ").append( packages.get(i)).append( ",\n");
			else
				bufr.append(" ").append( packages.get(i) ).append( "\n");
		}
	}




	private List<String> getExports(IProject project) {
		clog("getExports" + " - " + project.getName());
		List<String> result = new ArrayList<String>();
		try {
			IFile manifest = project.getFile("META-INF/MANIFEST.MF");
			if (manifest.exists()) {
				BufferedReader r = new BufferedReader(new InputStreamReader(
						manifest.getContents(), manifest.getCharset()));
				String line;
				boolean stateExportPackage = false;
				String exportSection = "";
				while ((line = r.readLine()) != null) {
					clog(line);
					if (!stateExportPackage
							&& line.startsWith("Export-Package:")) {
						stateExportPackage = true;
						exportSection = line;
					}
					if (stateExportPackage) {
						if (line.startsWith(" ") || line.isEmpty()){
							if (!exportSection.trim().endsWith(","))
								  exportSection+=",";
							exportSection += line.trim();
							if (line.isEmpty()){ //end of the file
								stateExportPackage = false;

							}
						}else if (!exportSection.equals(line) ) { //FP150511
							stateExportPackage = false;

						}
					} else {

					}
				}
				//Export-Package: lang.m2.x.y
				exportSection = exportSection.substring("Export-Package: ".length());
				String[] ezports = exportSection.split(",");
				for (String ezport : ezports) {
					result.add(ezport.trim());
				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}



	public void completeExport(IProject project,String[] packages) { //FP150511

		clog("completeExport" + " - " + project.getName());
		 List<String> exports=getExports(project);
		 List<String> paks=new ArrayList<String>();
		 for (String pak : packages)
			if (!exports.contains(pak))
				paks.add(pak);
		try {
			IFile manifest = project.getFile("META-INF/MANIFEST.MF");
			if (manifest.exists()) {
				BufferedReader r = new BufferedReader(new InputStreamReader(
						manifest.getContents(), manifest.getCharset()));
				String line;
				boolean stateExportPackage = false;
				String exportSection_ = "";
				StringBuffer result = new StringBuffer();
				while ((line = r.readLine()) != null) {
					clog(line);
					if (!stateExportPackage
							&& line.startsWith("Export-Package:")) {
						stateExportPackage = true;
						exportSection_ = line;
						//if (!exportSection.endsWith(","))
					//		exportSection += ",";
					}
					if (stateExportPackage) {
						if (line.startsWith(" ") || line.isEmpty()){
							if (!exportSection_.trim().endsWith(","))
								  exportSection_+=",";
							exportSection_ += line.trim();
							if (line.isEmpty()){ //end of the file
								stateExportPackage = false;
								result.append(exportSection_);
								result.append("\n");
								if (!paks.isEmpty()){
								  addExport(result,paks);
								  result.append("\n");
								}
							}
						}else if (!exportSection_.equals(line) ) { //FP150511
							stateExportPackage = false;
							result.append(exportSection_);
							result.append("\n");
							if (!paks.isEmpty()){
							  addExport(result,paks);
							  result.append("\n");
							}
						}
					} else {
						result.append(line);
						result.append("\n");
					}
				}

				if (LOG) clog(result.toString());
				manifest.setContents(new ByteArrayInputStream(result.toString()
						.getBytes(manifest.getCharset())), true, false,
						new NullProgressMonitor());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}


	private void appendExport(IProject project,String[] packages) {
		clog("appendExport" + " - " + project.getName());

		try {
			IFile manifest = project.getFile("META-INF/MANIFEST.MF");
			if (manifest.exists()) {
				BufferedReader r = new BufferedReader(new InputStreamReader(
						manifest.getContents(), manifest.getCharset()));
				String line;
				StringBuffer result = new StringBuffer();
				int len = lengthManifest(manifest);
				for (int i=0;i<len;i++){
					line = r.readLine();
					if (LOG)
						clog(line);
					result.append(line).append("\n");
				}
				appendExport_(result,packages);
				result.append("\n");
				manifest.setContents(new ByteArrayInputStream(result.toString()
						.getBytes(manifest.getCharset())), true, false,
						new NullProgressMonitor());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}




	public void removeExportJava(IProject project) {
		clog("removeExportJava" + " - " + project.getName());
		try {
			IFile manifest = project.getFile("META-INF/MANIFEST.MF");
			if (manifest.exists()) {
				BufferedReader r = new BufferedReader(new InputStreamReader(
						manifest.getContents(), manifest.getCharset()));
				String line;
				// boolean found = false;
				boolean stateExportPackage = false;
				String exportSection = "";
				StringBuilder result = new StringBuilder();
				while ((line = r.readLine()) != null) {
					clog(line);
					if (!stateExportPackage
							&& line.startsWith("Export-Package:")) {
						stateExportPackage = true;
						exportSection = line;
					}
					if (stateExportPackage) {
						if (line.startsWith(" "))
							exportSection += line.trim();
						else if (!exportSection.equals(line)) {
							stateExportPackage = false;
							// not legal, happens if a datatype is a java type
							// the problem is that the statement can be cut over
							// 2 lines
							if (exportSection.contains(",java.lang"))
								exportSection = exportSection.replace(
										",java.lang", "");
							result.append(exportSection); // FP140318
							result.append("\n");
							if (!line.isEmpty()){
							  result.append(line);
							  result.append("\n");
							}
						}
					} else {
						result.append(line);
						result.append("\n");
					}
				}
				manifest.setContents(new ByteArrayInputStream(result.toString()
						.getBytes(manifest.getCharset())), true, false,
						new NullProgressMonitor());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);

	}

	private static void ensureJavaVersion(String javaVersion) {
		// TODO: save and restore java version
		if (!javaVersion.equals(JavaCore.getOption(JavaCore.COMPILER_SOURCE))) {
			@SuppressWarnings("unchecked")
			Hashtable<String, String> options = JavaCore.getOptions();
			options.put(JavaCore.COMPILER_COMPLIANCE, javaVersion);
			options.put(JavaCore.COMPILER_SOURCE, javaVersion);
			options.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, javaVersion);
			JavaCore.setOptions(options);
		}
	}

	private void switchAutobuildOn(IWorkspaceDescription wd) { // FP120518
		wd.setAutoBuilding(true);
	}

	private static void switchAutobuildOff(IWorkspaceDescription wd) {
		wd.setAutoBuilding(false);
	}

	private void restoreWorkspaceHistory(IWorkspaceDescription wd) { // FP120518
		if (savedfileStateLongevity != -1)
			wd.setFileStateLongevity(savedfileStateLongevity);
		if (savedmaxFileStates != -1)
			wd.setMaxFileStates(savedmaxFileStates);
		if (savedmaxFileStateSize != -1)
			wd.setMaxFileStateSize(savedmaxFileStateSize);
		if (savedsnapshotInterval != -1)
			wd.setSnapshotInterval(savedsnapshotInterval);
	}

	/**
	 * No need to track history for workspace resources
	 */
	private static void turnWorkspaceHistoryOff(IWorkspaceDescription wd) {
		savedfileStateLongevity = wd.getFileStateLongevity();
		savedmaxFileStates = wd.getMaxFileStates();
		savedmaxFileStateSize = wd.getMaxFileStateSize();
		savedsnapshotInterval = wd.getSnapshotInterval();
		wd.setFileStateLongevity(0);
		wd.setMaxFileStates(0);
		wd.setMaxFileStateSize(0);
		wd.setSnapshotInterval(60 * 60 * 1000);
	}





}
