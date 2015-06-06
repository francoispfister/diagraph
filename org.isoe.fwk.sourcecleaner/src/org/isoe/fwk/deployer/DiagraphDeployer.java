/**
 * Copyright (c) 2014 Laboratoire de Genie Informatique et Ingenierie de Production - Ecole des Mines d'Ales
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Francois Pfister (ISOE-LGI2P)
 *    adapted from http://javalib.wordpress.com/2010/07/05/parsing-xmi/
 */
package org.isoe.fwk.deployer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.isoe.fwk.integration.FeatureBase;
import org.isoe.fwk.integration.FeatureGenerator;
import org.isoe.fwk.integration.IntegrationConfiguration;
import org.isoe.fwk.integration.UpdateSiteGenerator;
import org.isoe.mbse.sourcecleaner.ClassPath;
import org.isoe.mbse.sourcecleaner.Configuration;
import org.isoe.mbse.sourcecleaner.Dependency;
import org.isoe.mbse.sourcecleaner.Export;
import org.isoe.mbse.sourcecleaner.ExtensionPoint;
import org.isoe.mbse.sourcecleaner.ExtensionReference;
import org.isoe.mbse.sourcecleaner.Java;
import org.isoe.mbse.sourcecleaner.Manifest;
import org.isoe.mbse.sourcecleaner.Extension;
import org.isoe.mbse.sourcecleaner.Plugin;
import org.isoe.mbse.sourcecleaner.Project;
import org.isoe.mbse.sourcecleaner.Schema;
import org.isoe.mbse.sourcecleaner.Source;
import org.isoe.mbse.sourcecleaner.SourcecleanerFactory;
import org.isoe.mbse.sourcecleaner.SourcecleanerPackage;

/**
 * @author fpfister
 *
 */
public class DiagraphDeployer implements IntegrationConfiguration, FeatureBase {
	// private static final boolean LINE = true;
	// private static final boolean SOURCE = false;

	private enum Mode {
		line, source
	};

	// private static final String OLD_VERSION_MAJ = "1.0.1.20140523";
	// private static final String OLD_VERSION_MIN = "1112";

	private static final boolean GEN_UPDATE_SITE = true;

	private static final String __PRIOR_VERSION_MAJ_MIN_MICRO = "1.0.20140712";
	private static final String __PRIOR_VERSION_QUALIFIER = "1612";

	private static final String _1PRIOR_VERSION_MAJ_MIN_MICRO = "1.0.20140723";
	private static final String _1PRIOR_VERSION_QUALIFIER = "2148";

	private static final String _2PRIOR_VERSION_MAJ_MIN_MICRO = "1.0.20140724";
	private static final String _2PRIOR_VERSION_QUALIFIER = "0020";

	private static final String _3PRIOR_VERSION_MAJ_MIN_MICRO = "1.0.20140724b";
	private static final String _3PRIOR_VERSION_QUALIFIER = "0936";


	private static final String _4PRIOR_VERSION_MAJ_MIN_MICRO = "1.0.201407241";
	private static final String _4PRIOR_VERSION_QUALIFIER = "0942";

	private static final String _5PRIOR_VERSION_MAJ_MIN_MICRO = "1.0.201407242";
	private static final String _5PRIOR_VERSION_QUALIFIER = "1354";
	
	
	private static final String PRIOR_VERSION_MAJ_MIN_MICRO = "1.0.3";
	private static final String PRIOR_VERSION_QUALIFIER = "201412160051";
	

	private static final String VERSION_MAJ_MIN_MICRO_ = "1.0.4";
	private static final String VERSION_QUALIFIER = "2014121712009";

	private static final String VERSION_THIRD_PARTY = "1.0.20141217";
	
	
	private static final String USER_FOLDER3_ = "C:\\workspaces_mars_temp";
	private static final String PRJ_FOLDER3 = "ws_mars";
	private static final String WORKSPACE = "C:\\workspaces_mars\\ws_mars\\";
	

	private List<String> errors = new ArrayList<String>();
	private List<String> extensionpoints = new ArrayList<String>();
	private PluginParser pluginParser;

	private List<String> pluginsToDeploy = new ArrayList<String>();
	private List<String> thirdpartyToDeploy = new ArrayList<String>();
	private List<String> javaSrcs;
	private List<String> manifests;
	private List<String> plugins;
	private List<String> projekts;
	private Map<String, List<String>> projects;
	private ResourceSet resourceSet;
	private URI cleanerUri;
	private static int target = 3;
	private Configuration configuration;
	private String[] copyrightedOrPached;
	private String[] copyrightedOrPachedOrExcluded;
	private String[] copyrightedOrExcluded;
	private boolean tb;
	private List<String> notDeployables = new ArrayList<String>();
	private static final boolean LOG = true;
	private static final boolean LOG2 = false;
	private static final boolean LOG3 = false;
	private static final boolean LOG4 = false;
	private static final boolean LOG5 = false;
	private static final boolean LOG6 = true;

	// 1.0.1.201405231112

	private static final String USER_FOLDER_ = "C:\\Users\\Pfister";
	private static final String BASE_FOLDER_ = "git-diagraph-local";
	private static final String REPO_FOLDER_ = "git-diagraph-local";

	private static final String USER_FOLDER1 = "C:\\workspaces";
	
	
	

	private static final String TARGET_ = "ter";
	private static final int HEADER_LENGTH = 10;

	private static final String PRJ_FOLDER1 = "v130926";
	
	
	
	
	private static final int PRJ_FOLDER_LEN = PRJ_FOLDER1.length();
	static final String JAVA_EXT_ = ".java";
	static final String PLUGIN_NAME = "plugin.xml";
	static final String BUILD_NAME = "build.properties";
	static final String MANIFEST_NAME = "MANIFEST.MF";

	static final String[] KW_ALL = { "Require-Bundle:", "Bundle-ClassPath:",
			"Export-Package:", "Manifest-Version:", "Bundle-ManifestVersion:",
			"Eclipse-LazyStart:", "Bundle-Localization:",
			"Bundle-ActivationPolicy:", "Bundle-Name:", "Bundle-Vendor:",
			"Bundle-SymbolicName:", "Bundle-Version:", "Bundle-Vendor:",
			"Bundle-RequiredExecutionEnvironment:", "Bundle-Activator:" };

	private String[] keywordsWoRequireBundle;
	private String[] keywordsWoBundleClasspath;
	private String[] keywordsWoExportPackage;

	private static final String TARGET_2 = "ter2";
	private static final String REPO_FOLDER_2 = TARGET_;
	private static final String REPO_FOLDER_3 = TARGET_;

	private static final String INCOMPLETE_VERSION_A_ = "1.0.0";
	private static final String INCOMPLETE_VERSION_B_ = "1.0.1";



	static final String[] EXTERNAL_PLUGINS = { "org.junit", "org.eclipse",
			"org.kermeta", "com.ibm.icu", "org.apache.commons" };

	// static final String[] EXCLUDED_PACKAGES_ = { "org.eclipse.gmf.codegen" };
	static final String[] EXCLUDE_PACKAGES_nu_ = { "org.eclipse.gmf.codegen",
			"org.eclipse.gmf", "org.isoe.fwk.utils.borrowed" };
	static final String[] COPYRIGHTED1_ = { "org.eclipse.gmf.codegen",
			"org.eclipse.gmf", "org.isoe.fwk.utils.borrowed" };

	static final String[] COPYRIGHTED2_ = { "org.isoe.fwk.utils.borrowed" };

	// EXCLUDE_PRJ COPYRIGHTED

	static final String[] PATCHED = { "org.eclipse.gmf.codegen",
			"org.eclipse.gmf" };
	static final String[] EXCLUDE_PRJ = { "abc.trafo",
			"Eclipse Monkey Examples", "emfatix", "figures_these",
			"GroovyMonkeyScripts", "isoe.mail.rcp", "javadiff",
			"my.example.editor", "my.expression.evaluator", "my.testswt",
			"myscripts", "simpleJavaEditor", "styled", "svn",
			"org.isoe.diagraph.features", "org.isoe.diagraph.thirdparty",
			"org.isoe.diagraph.updatesite", "org.isoe.fwk.sourcecleaner",
			"SelectionExplorer", "genmymodel.shoppingcart" };

	static final String[] EXCLUDE_PRJ_PREFIX = { "lang.m2", "Math", "misc",
			"org.eclipse.emf", "org.eclipse.swt", "org.isoe.mbse",
			"_megamodel", "org.eclipse.gmf.codegen", "org.eclipse.gmf",
			"org.isoe.fwk.sourcecleaner","de.itemis","org.antlr","org.eclipse.xsd" };


	static final String[] NOT_DEPLOY = { "org.isoe.fwk.sourcecleaner",
			"org.isoe.mbse.sourcecleaner", "org.isoe.another",
			"org.isoe.fwk.bsh", "org.isoe.fwk.httpServer", "org.isoe.my",
			"org.isoe.fwk.rcp", "my.xml.parser",
			"org.isoe.diagraph.check.release",
			"org.isoe.diagraph.diagraph.tests",
			"org.isoe.diagraph.diagraphconsole",
			"org.isoe.extensionpoint.textual",
			"org.kermeta.kp.launcher.eclipse", };

	static final String[] NO_SOURCE = { "org.isoe.diagraph.interpreter" };

	static final String[] DEPLOY = { "org.isoe", "lirmm", "mtbe", "kermeta" };

	static final String[] _nu = { "Falleri", "acceleo", "Obeo", "mia-software" };

	static final String[] THIRD_PARTY_ = { "org.eclipse.gmf.bridge",
			"org.eclipse.gmf.bridge.source", "org.eclipse.gmf.bridge.trace",
			"org.eclipse.gmf.bridge.ui", "org.eclipse.gmf.bridge.ui.dashboard",
			"org.eclipse.gmf.bridge.ui.dashboard.source",
			"org.eclipse.gmf.bridge.ui.source", "org.eclipse.gmf.codegen",
			"org.eclipse.gmf.codegen.edit",
			"org.eclipse.gmf.codegen.edit.source",
			"org.eclipse.gmf.codegen.source", "org.eclipse.gmf.codegen.ui",
			"org.eclipse.gmf.codegen.ui.source", "org.eclipse.gmf.common",
			"org.eclipse.gmf.common.source", "org.eclipse.gmf.doc",
			"org.eclipse.gmf.doc.ui", "org.eclipse.gmf.graphdef",
			"org.eclipse.gmf.graphdef.codegen",
			"org.eclipse.gmf.graphdef.codegen.source",
			"org.eclipse.gmf.graphdef.codegen.ui",
			"org.eclipse.gmf.graphdef.codegen.ui.source",
			"org.eclipse.gmf.graphdef.edit",
			"org.eclipse.gmf.graphdef.edit.source",
			"org.eclipse.gmf.graphdef.source", "org.eclipse.gmf.map",
			"org.eclipse.gmf.map.edit", "org.eclipse.gmf.map.edit.source",
			"org.eclipse.gmf.map.source", 
			//"org.eclipse.gmf.runtime.lite.svg",
			"org.eclipse.gmf.sdk", "org.eclipse.gmf.tooldef",
			"org.eclipse.gmf.tooldef.edit",
			"org.eclipse.gmf.tooldef.edit.source",
			"org.eclipse.gmf.tooldef.source", "org.eclipse.gmf.tooling",
			"org.eclipse.gmf.tooling.runtime",
			"org.eclipse.gmf.tooling.runtime.source",
			"org.eclipse.gmf.validate", "org.eclipse.gmf.validate.source",
			"org.eclipse.gmf.xpand", "org.eclipse.gmf.xpand.editor",
			"org.eclipse.gmf.xpand.editor.source",
			"org.eclipse.gmf.xpand.qvtlibrary",
			"org.eclipse.gmf.xpand.qvtlibrary.source",
			"org.eclipse.gmf.xpand.source", "org.eclipse.m2m.atl",
			/*
			"org.eclipse.m2m.atl.adt", "org.eclipse.m2m.atl.adt.debug",
			"org.eclipse.m2m.atl.adt.debug.source",
			"org.eclipse.m2m.atl.adt.editor",
			"org.eclipse.m2m.atl.adt.editor.source",
			"org.eclipse.m2m.atl.adt.source", "org.eclipse.m2m.atl.adt.ui",
			"org.eclipse.m2m.atl.adt.ui.source",
			"org.eclipse.m2m.atl.cheatsheets", "org.eclipse.m2m.atl.common",
			"org.eclipse.m2m.atl.common.source", "org.eclipse.m2m.atl.core",
			"org.eclipse.m2m.atl.core.ant",
			"org.eclipse.m2m.atl.core.ant.source",
			"org.eclipse.m2m.atl.core.emf",
			"org.eclipse.m2m.atl.core.emf.source",
			"org.eclipse.m2m.atl.core.source", "org.eclipse.m2m.atl.core.ui",
			"org.eclipse.m2m.atl.core.ui.source",
			"org.eclipse.m2m.atl.core.ui.vm",
			"org.eclipse.m2m.atl.core.ui.vm.source",
			"org.eclipse.m2m.atl.debug.core",
			"org.eclipse.m2m.atl.debug.core.source", "org.eclipse.m2m.atl.doc",
			"org.eclipse.m2m.atl.drivers.emf4atl",
			"org.eclipse.m2m.atl.drivers.emf4atl.source",
			"org.eclipse.m2m.atl.drivers.uml24atl",
			"org.eclipse.m2m.atl.drivers.uml24atl.source",
			"org.eclipse.m2m.atl.dsls", "org.eclipse.m2m.atl.dsls.source",
			"org.eclipse.m2m.atl.engine", "org.eclipse.m2m.atl.engine.emfvm",
			"org.eclipse.m2m.atl.engine.emfvm.launch",
			"org.eclipse.m2m.atl.engine.emfvm.launch.source",
			"org.eclipse.m2m.atl.engine.emfvm.source",
			"org.eclipse.m2m.atl.engine.source",
			"org.eclipse.m2m.atl.engine.vm",
			"org.eclipse.m2m.atl.engine.vm.source",
			"org.eclipse.m2m.atl.examples",
			"org.eclipse.m2m.atl.examples.source",
			"org.eclipse.m2m.atl.profiler.core",
			"org.eclipse.m2m.atl.profiler.core.source",
			"org.eclipse.m2m.atl.profiler.emfvm",
			"org.eclipse.m2m.atl.profiler.emfvm.source",
			"org.eclipse.m2m.atl.profiler.exportmodel",
			"org.eclipse.m2m.atl.profiler.exportmodel.editor",
			"org.eclipse.m2m.atl.profiler.exportmodel.editor.source",
			"org.eclipse.m2m.atl.profiler.exportmodel.source",
			"org.eclipse.m2m.atl.profiler.model",
			"org.eclipse.m2m.atl.profiler.model.source",
			"org.eclipse.m2m.atl.profiler.ui",
			"org.eclipse.m2m.atl.profiler.ui.source",
			"org.eclipse.m2m.atl.profiler.vm",
			"org.eclipse.m2m.atl.profiler.vm.source",*/
			"org.eclipse.m2m.qvt.oml", "org.eclipse.m2m.qvt.oml.common",
			"org.eclipse.m2m.qvt.oml.common.source",
			"org.eclipse.m2m.qvt.oml.common.ui",
			"org.eclipse.m2m.qvt.oml.common.ui.source",
			"org.eclipse.m2m.qvt.oml.cst.parser",
			"org.eclipse.m2m.qvt.oml.cst.parser.source",
			"org.eclipse.m2m.qvt.oml.debug.core",
			"org.eclipse.m2m.qvt.oml.debug.core.source",
			"org.eclipse.m2m.qvt.oml.debug.ui",
			"org.eclipse.m2m.qvt.oml.debug.ui.source",
			"org.eclipse.m2m.qvt.oml.doc",
			"org.eclipse.m2m.qvt.oml.ecore.imperativeocl",
			"org.eclipse.m2m.qvt.oml.ecore.imperativeocl.source",
			"org.eclipse.m2m.qvt.oml.editor.ui",
			"org.eclipse.m2m.qvt.oml.editor.ui.source",
			"org.eclipse.m2m.qvt.oml.emf.util",
			"org.eclipse.m2m.qvt.oml.emf.util.source",
			"org.eclipse.m2m.qvt.oml.emf.util.ui",
			"org.eclipse.m2m.qvt.oml.emf.util.ui.source",
			"org.eclipse.m2m.qvt.oml.ocl",
			"org.eclipse.m2m.qvt.oml.ocl.source",
			"org.eclipse.m2m.qvt.oml.project",
			"org.eclipse.m2m.qvt.oml.project.source",
			"org.eclipse.m2m.qvt.oml.runtime",
			"org.eclipse.m2m.qvt.oml.runtime.source",
			"org.eclipse.m2m.qvt.oml.runtime.ui",
			"org.eclipse.m2m.qvt.oml.runtime.ui.source",
			"org.eclipse.m2m.qvt.oml.samples",
			"org.eclipse.m2m.qvt.oml.source",
			"org.eclipse.m2m.qvt.oml.trace.edit",
			"org.eclipse.m2m.qvt.oml.trace.edit.source",
			"org.eclipse.m2m.qvt.oml.ui",
				
			"org.eclipse.emf.ecoretools.legacy",
			"org.eclipse.emf.ecoretools.legacy.diagram",
			"org.eclipse.emf.ecoretools.legacy.diagram.ui.outline",
			"org.eclipse.emf.ecoretools.legacy.filters",
			"org.eclipse.emf.ecoretools.legacy.properties",
			"org.eclipse.emf.ecoretools.legacy.tabbedproperties"

	};

	


	@Override
	public Configuration getConfiguration() {
		return configuration;
	}



	private String[] getCopyrightedOrPatched_() {
		if (copyrightedOrPached == null) {
			copyrightedOrPached = new String[COPYRIGHTED2_.length
					+ PATCHED.length];
			int i = 0;
			for (String cop : COPYRIGHTED2_)
				copyrightedOrPached[i++] = cop;
			for (String pat : PATCHED)
				copyrightedOrPached[i++] = pat;
		}
		return copyrightedOrPached;
	}

	private String[] getCopyrightedOrExcluded_() {
		if (copyrightedOrExcluded == null) {
			copyrightedOrExcluded = new String[COPYRIGHTED2_.length
					+ EXCLUDE_PRJ.length];
			int i = 0;
			for (String cop : COPYRIGHTED2_)
				copyrightedOrExcluded[i++] = cop;
			for (String pat : EXCLUDE_PRJ)
				copyrightedOrExcluded[i++] = pat;
		}
		return copyrightedOrExcluded;
	}

	private DiagraphDeployer() {
		resourceSet = new ResourceSetImpl();
		resourceSet
				.getResourceFactoryRegistry()
				.getExtensionToFactoryMap()
				.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
						new XMIResourceFactoryImpl());
		resourceSet.getPackageRegistry().put(SourcecleanerPackage.eNS_URI,
				SourcecleanerPackage.eINSTANCE);
		cleanerUri = URI.createFileURI(new File("my.sourcecleaner")
				.getAbsolutePath());

		keywordsWoRequireBundle = new String[KW_ALL.length - 1];
		int i = 0;
		for (String kw : KW_ALL)
			if (!kw.equals("Require-Bundle:"))
				keywordsWoRequireBundle[i++] = kw;

		keywordsWoBundleClasspath = new String[KW_ALL.length - 1];
		i = 0;
		for (String kw : KW_ALL)
			if (!kw.equals("Bundle-ClassPath:"))
				keywordsWoBundleClasspath[i++] = kw;


		keywordsWoExportPackage = new String[KW_ALL.length - 1];
		i = 0;
		for (String kw : KW_ALL)
			if (!kw.equals("Export-Package:"))
				keywordsWoExportPackage[i++] = kw;


	}

	// C:\Users\Pfister\git-diagraph-local\git-diagraph-local\v130926\
	// private static final String WS_ = USER_FOLDER + "\\" + BASE_FOLDER + "\\"
	// + REPO_FOLDER + "\\"
	// + PRJ_FOLDER + "\\";

	private String getWorkspace(){
		return WORKSPACE;//"C:\\workspaces_mars\\ws_mars\\";
	}
	
	private static final String getWorkspace1__() {
		return USER_FOLDER1 + "\\" + PRJ_FOLDER1 + "\\";
	}

	private static final String getWorkspace2__() {
		return USER_FOLDER1 + "\\" + REPO_FOLDER_2 + "\\" + PRJ_FOLDER1 + "\\";
	}
	
	private static final String getWorkspace3__() {//C:\workspaces_mars\ws_mars
		return USER_FOLDER3_ + "\\" + REPO_FOLDER_3 + "\\" + PRJ_FOLDER3 + "\\";
	}
	
	private static  String getWorkspace(int n) {
		switch (n) {
		case 1: return getWorkspace1__();
		case 2: return getWorkspace2__();
		case 3: return getWorkspace3__();
		}
		throw new RuntimeException("error getWorkspace");
	}

/*
	private static String getWorkspace() {
		if (target_ == 1)
			return getWorkspace1_();
		else if (target_ == 2)
			return getWorkspace2_();
		else //if (target_ == 3)
			return getWorkspace3_();
	}*/
/*
	private static String getRepoFolder() {
		if (target == 1)
			return PRJ_FOLDER1;
		else if (target == 2)
			return REPO_FOLDER_2;
		else //if (target_ == 3)
			return REPO_FOLDER_3;
	}*/

	private void addToDeploy_(String plugin) {
		if (pluginsToDeploy.contains(plugin))
			throw new RuntimeException("should not happen in addToDeploy "
					+ plugin);
		if (!pluginsToDeploy.contains(plugin))
			pluginsToDeploy.add(plugin);
	}

	private void addThirdParty(String plugin) {
		thirdpartyToDeploy.add(plugin);
	}

	private String getPackage(Project project, String path) {
		// C:\workspaces\v130926\lirmm.dotutils\src\lirmm\dotutils\AbstractExportDotAction.java
		String result = "";
		try {
			String pref = getWorkspace() + project.getName() + File.separator
					+ "src" + File.separator;
			if (!path.startsWith(pref))
				throw new RuntimeException("should not happen in getPackage");
			result = path.substring(pref.length());
			result = result.substring(0, result.lastIndexOf(File.separator));
			result = result.replace(File.separator, ".");

		} catch (Exception e) {
			return null;
		}
		return result;
	}

	private static void copyFile(File source, File dest) throws IOException {
		InputStream input = null;
		OutputStream output = null;
		try {
			input = new FileInputStream(source);
			output = new FileOutputStream(dest);
			if (LOG)
				clog("copying " + source + " to " + dest);
			byte[] buf = new byte[1024];
			int bytesRead;
			while ((bytesRead = input.read(buf)) > 0) {
				output.write(buf, 0, bytesRead);
			}
		} finally {
			if (input != null)
				input.close();
			if (output != null)
				output.close();
		}
	}

	private static void delete(File file) throws IOException {
		if (file.isDirectory()) {
			if (file.list().length == 0) {
				file.delete();
			} else {
				String files[] = file.list();
				for (String temp : files) {
					File fileDelete = new File(file, temp);
					delete(fileDelete);
				}
				if (file.list().length == 0)
					file.delete();
			}
		} else
			file.delete();
	}

	private void copyProjects() throws IOException {
		for (Project p : configuration.getProjects()) {
			
			//String ws="C:\\workspaces_mars\\ws_mars\\";
			
			File sprjfile = new File(getWorkspace() + p.getName()
					+ "\\.project");
			String targpath = getWorkspace(target) + p.getName();
			File targdir = new File(targpath);
			if (targdir.exists()) {
				clog("deleting temporary folder: " + targdir);
				delete(targdir);
			}
			new File(targpath).mkdirs();
			File tprjfile = new File(targpath + "\\.project");
			copyFile(sprjfile, tprjfile);
		}
	}

	private void updateSourceFiles(Project project, String importToReplace,
			String newImport, String comment) {
		List<Java> sources = project.getSources();
		for (Java src : sources) {
			File javafile = new File(src.getAbsolutePath());
			if (javafile.exists()) // FP140428
				updateJavaFileReplace(project, javafile, javafile,
						importToReplace, newImport, comment);
		}
	}

	private void doWithManifestFiles(List<String> prjs, String ws, String old,
			String niu) {
		for (String manifestsrc : manifests) {
			File manifestFile = new File(manifestsrc);
			if (manifestFile.exists())
				updateManifestFile(manifestsrc, manifestFile, old, niu);
		}
	}

	private void doWithPluginFiles(List<String> prjs, String ws, String old,
			String niu) {
		for (String pluginsrc : plugins) {
			File pluginFile = new File(pluginsrc);
			if (pluginFile.exists())
				updatePluginFile(pluginsrc, pluginFile, old, niu);
		}
	}

	private void updateMetaFiles(String ws_, String find, String add,
			boolean remove) throws IOException {

		List<Project> projects = configuration.getProjects();
		for (Project project : projects) {

			File manifestfile = new File(getWorkspace() + project.getName()
					+ "\\META-INF\\MANIFEST.MF");
			if (manifestfile.exists()
					&& project.getName().startsWith("org.isoe")) { // FP140428
				// prj.startsWith("org.isoe")
				String targpath = ws_ + project.getName();
				// File targdir = new File(targpath);
				File targmdir = new File(targpath + "\\META-INF");
				if (!targmdir.exists())
					new File(targpath + "\\META-INF").mkdirs();

				File tmanifestfile = new File(targpath
						+ "\\META-INF\\MANIFEST.MF");

				if (LOG)
					clog("---------- handling project " + project.getName()
							+ " target= " + tmanifestfile.getAbsolutePath());

				String mftext = readMetaFile(manifestfile);
				updateMetaFile(project, manifestfile, tmanifestfile);

				if (add != null && !mftext.contains(add))
					updateMetaFileReplace(project, tmanifestfile,
							tmanifestfile, find, add, remove);
			}
		}

	}

	private String readMetaFile(File infile) {// C:\workspaces\v130926\isoe.mail.rcp\META-INF\MANIFEST.MF
		String result = "";
		try {
			InputStream in = new FileInputStream(infile);
			StringBuffer buffer = new StringBuffer();
			try {
				InputStreamReader inR = new InputStreamReader(in);
				BufferedReader buf = new BufferedReader(inR);
				String line;
				while ((line = buf.readLine()) != null) {
					result += line + "\n";
				}
			} finally {
				in.close();
			}
		} catch (IOException e) {
			addError("error " + e.toString());
		}
		return result;
	}

	private String readPluginFile(File infile) {
		String result = "";
		try {
			InputStream in = new FileInputStream(infile);
			StringBuffer buffer = new StringBuffer();
			try {
				InputStreamReader inR = new InputStreamReader(in);
				BufferedReader buf = new BufferedReader(inR);
				String line;
				while ((line = buf.readLine()) != null) {
					result += line + "\n";
				}
			} finally {
				in.close();
			}
		} catch (IOException e) {
			addError("error " + e.toString());
		}
		return result;
	}

	private void updateJavaFileReplace(Project project, File infile,
			File outfile, String importToReplace, String newImport,
			String comment) {

		boolean added = false;
		boolean addImport = false;

		String test = "C:\\workspaces\\v130926\\org.isoe.extensionpoint.gramgen\\src\\org\\isoe\\extensionpoint\\gramgen\\IAnnotationHelper.java";
		boolean debug = false;
		;
		// if (prj.equals(test))
		// debug = true;

		try {
			InputStream in = new FileInputStream(infile);
			StringBuffer buffer = new StringBuffer();
			try {
				InputStreamReader inR = new InputStreamReader(in);
				BufferedReader buf = new BufferedReader(inR);
				String line;
				boolean stateClass = false;
				boolean statePackage = true;
				while ((line = buf.readLine()) != null) {

					if (statePackage && !stateClass) {

						if (line.startsWith("public class ")
								|| line.startsWith("public interface ")) {
							stateClass = true;
							statePackage = false;
							if (!added && addImport) {
								added = true;
								buffer.append(newImport).append("  //")
										.append(comment).append("\n");
								clog("\nadded import " + newImport + " to "
										+ outfile.getAbsolutePath());
								// clog(buffer.toString());
							}
						}
					}

					String linetrim = line.trim();
					if (debug)
						clog(linetrim);

					if (statePackage && !added
							&& linetrim.startsWith(importToReplace)) // org.isoe.extensionpoint.diagraph;
						addImport = true;

					String result = line;

					if (!result.contains(importToReplace))
						buffer.append(result).append("\n");
				}

			} finally {
				in.close();
			}
			if (added) {
				OutputStream os = new FileOutputStream(outfile);
				os.write(buffer.toString().getBytes());
				os.close();
				clog("added import " + newImport + " to "
						+ outfile.getAbsolutePath());
			}

		} catch (IOException e) {
			clog("error " + e.toString());
		}
		// }
	}

	private void updateMetaFileReplace(Project project, File infile,
			File outfile_, String find, String add, boolean remove) {// C:\workspaces\v130926\isoe.mail.rcp\META-INF\MANIFEST.MF
		boolean check_;

		boolean added = false;
		boolean addDependency = false;

		String test = "org.isoe.diagraph.annotationhelper";

		boolean debug = false;
		// if (prj.equals(test))
		// debug = true;
		// if (prj.startsWith("org.isoe")) {
		try {
			InputStream in = new FileInputStream(infile);
			StringBuffer buffer = new StringBuffer();
			try {
				InputStreamReader inR = new InputStreamReader(in);
				BufferedReader buf = new BufferedReader(inR);
				String line;
				boolean stateRequireBundle = false;

				String rfind = "Require-Bundle: " + find;
				while ((line = buf.readLine()) != null) {
					if (debug) {
						clog(line);

					}
					if (line.startsWith("Require-Bundle:"))
						stateRequireBundle = true;

					// if (stateRequireBundle_ && !line.startsWith(" "))
					// stateRequireBundle_ = false;

					if (line.startsWith("Manifest-Version:")
							|| line.startsWith("Bundle-ManifestVersion:")
							|| line.startsWith("Bundle-Name:")
							|| line.startsWith("Bundle-SymbolicName:")
							|| line.startsWith("Bundle-Version:")
							|| line.startsWith("Bundle-Vendor:")
							|| line.startsWith("Bundle-RequiredExecutionEnvironment:")
							|| line.startsWith("Export-Package:")
							|| line.startsWith("Bundle-Activator:")) {

						if (stateRequireBundle && !added && addDependency) {
							// clog(buffer.toString());
							added = true;
							buffer.deleteCharAt(buffer.length() - 1)
									.append(",\n ").append(add).append("\n");
							clog("added dependency " + add + " to "
									+ outfile_.getAbsolutePath());
							clog(buffer.toString());
						}
						stateRequireBundle = false;
					}

					String linetrim = line.trim();

					if (stateRequireBundle && !added
							&& linetrim.startsWith(find)
							|| linetrim.startsWith(rfind)) // org.isoe.extensionpoint.diagraph;
						addDependency = true;

					String result = line;

					if (!remove || (remove && !result.contains(find)))
						buffer.append(result).append("\n");
				}

				if (stateRequireBundle && !added && addDependency) {
					// clog(buffer.toString());
					added = true;
					buffer.deleteCharAt(buffer.length() - 1).append(",\n ")
							.append(add).append("\n");
					clog("added dependency " + add + " to "
							+ outfile_.getAbsolutePath());
					clog(buffer.toString());
				}

			} finally {
				in.close();
			}
			if (added) {
				OutputStream os = new FileOutputStream(outfile_);
				os.write(buffer.toString().getBytes());
				os.close();
				clog("added dependency " + add + " to "
						+ outfile_.getAbsolutePath());
				// C:\workspaces\v130926\org.isoe.diagrapg.diagraphviz.actions\META-INF\MANIFEST.MF
				// C:\workspaces\v130926\org.isoe.diagraph.adapter.impl\META-INF\MANIFEST.MF

			}

		} catch (IOException e) {
			clog("error " + e.toString());
		}
		// }
	}

	public static void main(String[] args) {

		execWithoutReplace();

	}

	private void save() {
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet
				.getResourceFactoryRegistry()
				.getExtensionToFactoryMap()
				.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
						new XMIResourceFactoryImpl());

		resourceSet.getPackageRegistry().put(SourcecleanerPackage.eNS_URI,
				SourcecleanerPackage.eINSTANCE);

		File f = new File("my.sourcecleaner");
		URI uri = URI.createFileURI(f.getAbsolutePath());

		Resource resource = resourceSet.createResource(uri);
		resource.getContents().add(configuration);
		try {
			resource.save(null);
			clog2("saved: " + f.getAbsolutePath());
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	private static Configuration load(ResourceSet resourceSet, URI uri) {
		Resource resource = resourceSet.getResource(uri, true);
		Configuration result = (Configuration) resource.getContents().get(0);
		return result;
	}

	private static void save(ResourceSet resourceSet, URI uri,
			Configuration configuration) throws IOException {
		Resource resource = resourceSet.createResource(uri);
		resource.getContents().add(configuration);
		resource.save(null);
	}

	private void exec(String initialDependency, String newDependency,
			String importToReplace, String newImport, String comment,
			boolean remove, boolean mark) throws IOException {
		// clons("ter");
		target = 3;
		prepare();
		doit();

		clog(projekts.size() + " projects, " + javaSrcs.size() + " java files");

		copyProjects();
		String ws = getWorkspace(target);// getWorkspace2();
		List<String> prjs = projekts;

		updateMetaFiles(ws, initialDependency, newDependency, remove);

		for (Project project : configuration.getProjects())
			updateSourceFiles(project, importToReplace, newImport, comment);

	}

	private void exec() throws IOException {
		// clons("ter");
		target = 3;
		prepare();
		doit();
		resolve();
		validate();

		// clog(projekts.size() + " projects, " + javaSrcs_.size() +
		// " java files");

		// copyProjects();

		updateMetaFiles(getWorkspace(target));
		resolveDependencies();

		clog2("******  not deployables:");
		for (String ndep : notDeployables)
			clog2(ndep);
		save();

		for (String error : errors) {
			System.err.println(error);
		}

		createDeployables();

		System.out.println("---------deploy");
		for (String plugin : pluginsToDeploy) {
			System.out.println(plugin);
		}

		createThirdParty();

		try {

			System.out.println("---------deploy plugins");

			String featureContent = new FeatureGenerator(this, false)
					.generate();

			writeFile(getWorkspace(target) + "org.isoe.diagraph.features"
					+ File.separator + "feature.xml", featureContent);

			System.out.println("---------deploy third party");

			String thirdpartyContent = new FeatureGenerator(this, true)
					.generate();

			writeFile(getWorkspace(target) + "org.isoe.diagraph.thirdparty"
					+ File.separator + "feature.xml", thirdpartyContent);

			System.out.println("---------deploy update site");

			if (GEN_UPDATE_SITE) {

				String updateSiteContent = new UpdateSiteGenerator(this)
						.generate();

				writeFile(getWorkspace(target) + "org.isoe.diagraph.updatesite"
						+ File.separator + "site.xml", updateSiteContent);

				deleteFile(getWorkspace(target) + "org.isoe.diagraph.updatesite"
						+ File.separator + "artifacts.jar");
				deleteFile(getWorkspace(target) + "org.isoe.diagraph.updatesite"
						+ File.separator + "content.jar");
				deleteDirectory(getWorkspace(target) + "org.isoe.diagraph.updatesite"
						+ File.separator + "features");
				deleteDirectory(getWorkspace(target) + "org.isoe.diagraph.updatesite"
						+ File.separator + "plugins");
			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}

		// updateFeatures("org.isoe.diagraph.features", ws);

	}

	private void deleteDirectory(String path) throws IOException {
		if (path != null && !path.isEmpty()) {
			File f = new File(path);
			if (f.exists() & f.isDirectory())
				delete(f);
			clog6("deleted: " + path);
		}
	}

	private void deleteFile(String path) {
		if (path != null && !path.isEmpty()) {
			File f = new File(path);
			if (f.exists())
				f.delete();
			clog6("deleted: " + path);
		}
	}

	private void writeFile(String path, String content) {
		if (path != null && !path.isEmpty() && content != null
				&& !content.isEmpty()) {
			FileWriter fw;
			try {
				
				String dir = path.substring(0,path.lastIndexOf(File.separator));
				File p= new File(dir);
				if (!p.exists())
					p.mkdir();
					
				fw = new FileWriter(new File(path), false);
				fw.write(content);
				fw.close();
				clog("saved ok: " + path);
			} catch (IOException e) {
				System.err.println("DFEAT_WF  " + e.toString());
			}
		} else
			System.err.println("nothing to save (" + path + ")");
	}

	private Java findSource(Project proj, String classpackage, String classname) {
		List<Java> srcs = proj.getSources();
		for (Java java : srcs)
			if (java.getName().equals(classname))
				if (classpackage == null
						|| (java.getPackage().equals(classpackage)))
					return java;
		return null;
	}

	private void resolve() {
		tb = true;

		for (Project proj : configuration.getProjects()) {
			Plugin plugin = proj.getPlugin();
			if (plugin != null) {
				List<Extension> extensions = plugin.getExtensions();
				for (Extension extension : extensions) {
					String pointId = extension.getPointId();
					if (pointId.startsWith("org.isoe"))
						setIsoeExtensionPoint(proj, extension, pointId);
				}
			}
		}
		/*
		 * for (Project proj : configuration.getProjects()) { Plugin plugin =
		 * proj.getPlugin(); if (plugin != null) for (Extension extension :
		 * plugin.getExtensions()) if (extension.getExtensionPoint() != null)
		 * for (ExtensionReference extensionReference : extension
		 * .getExtensionPoint().getPlugin().getProject()
		 * .getSchema().getReferences()){
		 *
		 * Project p=getProject(extensionReference); if (p==null)
		 * System.err.println("no project for "+extensionReference.getJava());
		 * else extensionReference.setProject(p.getName()); } }
		 */
	}

	private void validate() {
		tb = true;

		for (Project proj : configuration.getProjects()) {
			String projName = proj.getName();
			Plugin plugin = proj.getPlugin();
			if (plugin != null) {
				String plugname = plugin.getName();
				List<Extension> extensions = plugin.getExtensions();
				for (Extension extension : extensions) {
					String extName = extension.getName();
					String pid = extension.getPointId();// org.isoe.diagraph.graphviz
					String clazz = extension.getClazz();// org.isoe.diagraph.diagraphviz.actions.LanguageDotifyAction
					if (!clazz.isEmpty()) {
						int e = clazz.lastIndexOf(".");
						String classname = null;
						if (e != -1)
							classname = clazz.substring(e + 1);
						else
							classname = clazz;
						String classpackage = null;
						if (e != -1)
							classpackage = clazz.substring(0, e);
						Java sr = findSource(proj, classpackage, classname);
						if (sr != null)
							extension.setImplements(sr);
						else
							addError(classname + " does not exist in project "
									+ projName + " ext=" + pid);
					} else
						addError("no class for extension in project "
								+ projName + " ext=" + pid);

					// Java java = extension.getImplements();// null
					String id = extension.getId();// empty

					String pointId = extension.getPointId();// org.isoe.diagraph.graphviz

					if (extension.getExtensionPoint() != null) {
						ExtensionPoint extensionPoint = extension
								.getExtensionPoint();
						String extid = extensionPoint.getId();// org.isoe.diagraph.graphviz
						String extname = extensionPoint.getName();// Dotifier

						Schema schema = extensionPoint.getPlugin().getProject()
								.getSchema();

						String schname = schema.getName();// org.isoe.extensionpoint.graphviz
						String schext = schema.getExtensionName();// Dotifier

						if (schext != null && !schext.isEmpty())
							tb = true;

						String schid = schema.getExtensionId();// org.isoe.diagraph.graphviz

						if (schid == null || schid.isEmpty()) {
							System.err
									.println("----  schema id is empty for project "
											+ extensionPoint.getPlugin()
													.getProject().getName());
						}

						if (schext == null || schext.isEmpty()) {
							System.err
									.println("----  schema name is empty for project "
											+ extensionPoint.getPlugin()
													.getProject().getName());
						} // else
							// clog5("++++  schema extensionName " + schext);

						List<ExtensionReference> erfs = schema.getReferences();
						for (ExtensionReference extensionReference : erfs) {
							// String prjname =
							// extensionReference.getProject();//
							// org.isoe.extensionpoint.graphviz

							String projct = extensionReference.getProject();
							String jav = extensionReference.getJava(); // ILanguageDotifyService
							String pack = extensionReference.getPackage();

							Java javaobj = extensionReference.getJavaclass();
							tb = true;
						}
					} else {
						if (pointId.startsWith("org.isoe"))
							addError("no extension point for " + proj.getName()
									+ " - " + pointId);
						// else
						// clog4("extension "+pointId+" defined within Eclipse");
					}

					// tb = true;

				}

			}

		}

	}

	@Override
	public Project getProject(ExtensionReference extensionReference) {
		String jav = extensionReference.getJava();
		String pack = extensionReference.getPackage();
		List<Project> prjs = configuration.getProjects();
		for (Project project : prjs) {
			List<Java> srcs = project.getSources();
			for (Java java : srcs) {
				if (java.getProject() == project && java.getName().equals(jav)
						&& java.getPackage().equals(pack))
					return project;
			}
		}
		return null;
	}

	private void clog5(String mesg) {
		if (LOG5)
			System.out.println(mesg);

	}

	public static void execWithReplace() {

		String find_ = "org.isoe.extensionpoint.diagraph";
		String add_ = "org.isoe.diagraph.controler;bundle-version=\"1.0.1.20140523\"";

		String importTofind_ = "import org.isoe.extensionpoint.diagraph.IDiagraphControler;";
		String importToReplace_ = "import org.isoe.diagraph.controler.IDiagraphControler;";
		String comment_ = "FP140707refactored";
		boolean removeNo_ = false;
		boolean removeYes = true;

		String find___ = "org.isoe.extensionpoint.diagraph";
		String add___ = "org.isoe.extensionpoint.diagraph.action;bundle-version=\"1.0.1.20140523\"";

		String importTofind___ = "import org.isoe.extensionpoint.diagraph.DiagraphService;";
		String importToReplace___ = "import org.isoe.extensionpoint.diagraph.action.DiagraphService;";
		String comment___ = "FP140707_c_refactored";

		String find____ = "org.isoe.extensionpoint.diagraph";
		String add____ = "org.isoe.extensionpoint.diagraph.generator;bundle-version=\"1.0.1.20140523\"";

		String importTofind____ = "import org.isoe.extensionpoint.diagraph.IDiagraphGenerator;";
		String importToReplace____ = "import org.isoe.extensionpoint.diagraph.generator.IDiagraphGen;";
		String comment____ = "FP140707_b_refactored";

		String find = null;
		String add = null;
		String importTofind = null;
		String importToReplace = null;
		String comment = null;
		DiagraphDeployer c = new DiagraphDeployer();

		try {

			c.exec(find, add, importTofind, importToReplace, comment,
					removeYes, false);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
	}

	public static void execWithoutReplace() {
		DiagraphDeployer c = new DiagraphDeployer();

		try {

			c.exec();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
	}

	private void prepare() {
		// projects = new HashMap<String, List<String>>();
		pluginParser = new PluginParser(this);
		configuration = SourcecleanerFactory.eINSTANCE.createConfiguration();

		String[] exclude = new String[EXCLUDE_PRJ_PREFIX.length
				+ NOT_DEPLOY.length];
		for (int i = 0; i < EXCLUDE_PRJ_PREFIX.length; i++)
			exclude[i] = EXCLUDE_PRJ_PREFIX[i];
		for (int i = EXCLUDE_PRJ_PREFIX.length; i < EXCLUDE_PRJ_PREFIX.length
				+ NOT_DEPLOY.length; i++)
			exclude[i] = NOT_DEPLOY[i - EXCLUDE_PRJ_PREFIX.length];

		projekts = getAllProjects(EXCLUDE_PRJ, exclude);

		for (String projekt : projekts) {
			clog(projekt);
			if (projekt.contains("borrowed"))
				tb = true;
		}

		tb = true;
	}

	private void doit() {
		clog("_____projects");
		for (String prj : projekts)
			createProject(prj);
		for (Project cproject : configuration.getProjects()) {
			if (LOG3) {
				clog3("=========== start pluginparse phase 1 ==============");
			}
			clog(cproject.getName());
			clog2(cproject.getName());
			clog3(cproject.getName());
			addJavaSources(cproject);
			setManifestFile(cproject);
			setPluginFile(cproject);
		}

		for (Project cproject : configuration.getProjects()) {
			if (LOG3) {
				clog3("=========== start pluginparse  phase 2 ==============");
			}
			parsePluginFile_(cproject);
			if (LOG3) {
				clog3("=========== end pluginparse  phase 2 ==============");
			}
		}
	}

	private boolean setIsoeExtensionPoint(Project proj, Extension extension,
			String pointId) {
		ExtensionPoint extensionPoint = findIsoeExtensionPoint(pointId);
		if (extensionPoint == null) {
			if (extension.getClazz().equals(
					"org.isoe.diagraph.operations.views.SandboxView"))
				tb = true;
			addError("to remove: " + extension.getClazz() + " in project "
					+ proj.getName());
			return false;
		} else {
			extension.setExtensionPoint(extensionPoint);
			extensionPoint.getExtensions().add(extension);

			String extName = extension.getName();
			if (extName.equals("EclipseAutomationServer"))
				tb = true;
			if (extName.equals(extensionPoint.getName()))
				tb = true;

			clog("set extension point for " + pointId);
			return true;
		}
	}

	public ExtensionPoint findIsoeExtensionPoint(String pointId) {
		for (ExtensionPoint ep : getIsoeExtensionPoints())
			if (ep.getId().equals(pointId))
				return ep;
		return null;
	}

	private boolean isIn(String[] a, String b) {
		if (a == null)
			return false;
		for (String c : a)
			if (c.equals(b))
				return true;
		return false;
	}

	private boolean isInPrefix(String[] a, String b) {
		if (a == null)
			return false;
		for (String c : a)
			if (b.startsWith(c))
				return true;
		return false;
	}

	private boolean isInContains(String[] a, String b) {
		if (a == null || b==null)
			return false;
		for (String c : a)
			if (b.contains(c))
				return true;
		return false;
	}

	private Project findProject_(String prj) {
		for (Project cproject : configuration.getProjects())
			if (cproject.getName().equals(prj))
				return cproject;
		return null;
	}

	private void createProject(String prjname) {
		Project prj = addProject(prjname);
		configuration.getProjects().add(prj);
	}

	private List<Java> addJavaSources(Project cproject) {
		List<String> jfiles = getFiles(
				new File(getWorkspace() + cproject.getName()), JAVA_EXT_, null);
		for (String jfile : jfiles) {
			if (jfile.contains("ISimilarityService")) // ISimilarityService
				tb = true;

			String pak = getPackage(cproject, jfile);
			if (pak != null) {
				Java source = SourcecleanerFactory.eINSTANCE.createJava();
				source.setProject(cproject);
				source.setAbsolutePath(jfile);
				int e = jfile.lastIndexOf(File.separator);
				String javaname = jfile.substring(e + 1);
				if (javaname.contains("DiagraphWorkbenchAction"))
					tb = true;

				javaname = javaname.substring(0, javaname.lastIndexOf("."));
				// String packag = jfile.substring(0,e);
				source.setName(javaname);
				source.setPackage(pak);
				cproject.getSources().add(source);
			}
		}
		return cproject.getSources();
	}

	private void parsePluginFile_(Project cproject) {
		int count = 0;
		List<String> files = getFiles(
				new File(getWorkspace() + cproject.getName()), PLUGIN_NAME,
				cproject.getName());
		for (String path : files) {

			String pltext = readPluginFile(new File(path));
			clog3(pltext);

			pluginParser.initPlugin(cproject, path);
			pluginParser.parsePluginAndSchema();

			count++;
		}
		if (count > 1)
			throw new RuntimeException(
					"should not happen: more than one plugin file for "
							+ cproject.getName());
	}

	private void clog3(String mesg) {
		if (LOG3)
			System.out.println(mesg);
	}


	
	private Plugin setPluginFile(Project pr) {
		// Project pr = findProject_(prj);
		int count = 0;
		List<String> jfiles = getFiles(new File(getWorkspace() + pr.getName()),
				PLUGIN_NAME, pr.getName());
		for (String jfile : jfiles) {
			Plugin plugin = SourcecleanerFactory.eINSTANCE.createPlugin();
			plugin.setAbsolutePath(jfile);
			plugin.setName(pr.getName());
			pr.setPlugin(plugin);
			plugin.setProject(pr);
			count++;
		}
		if (count > 1)
			throw new RuntimeException(
					"should not happen: more than one plugin file");
		return pr.getPlugin();
	}

	
	//C:\workspaces_mars\ws_mars\org.eclipse.m2m.qvt.oml.common\schema\resourceSetProviders.exsd
	
	@Override
	public Schema createSchema(Project cproject, String schemaPath) {
		File scf = new File(schemaPath);
		if (!scf.exists()){
			if (!"org.eclipse.m2m.qvt.oml.common".equals(cproject.getName())
					
				&& !"org.eclipse.m2m.qvt.oml.editor.ui".equals(cproject.getName())
				
					
					)
			   throw new RuntimeException("no schemaPath "+schemaPath+" , should not happen in setSchema");
		}
		Schema schema = SourcecleanerFactory.eINSTANCE.createSchema();
		schema.setAbsolutePath(schemaPath);

		int e = schemaPath.lastIndexOf(File.separator);
		String sname = schemaPath.substring(e + 1);
		sname = sname.substring(0, sname.lastIndexOf("."));

		if (sname.endsWith(".egstantion"))
			tb = true;

		if (!cproject.getName().equals(sname))
			tb = true;

		if (cproject.getName().equals(sname))
			tb = true;

		clog4("schema: "
				+ sname
				+ (!cproject.getName().equals(sname) ? (" ("
						+ cproject.getName() + ") ") : ""));

		// String packag = schemaPath.substring(0,e);
		// schema.setName(sname);
		// schema.setPackage(packag);

		cproject.setSchema(schema);
		schema.setName(sname);// cproject.getName());
		// schema.setProjectName(cproject.getName());
		schema.setProject(cproject);
		return cproject.getSchema();
	}

	@Override
	public void addError(String mesg) {
		if (!errors.contains(mesg))
			errors.add(mesg);
	}

	private void clog4(String mesg) {
		if (LOG4)
			System.out.println(mesg);
	}

	private Manifest setManifestFile(Project cproject) {
		List<String> jfiles = getFiles(
				new File(getWorkspace() + cproject.getName()), MANIFEST_NAME,
				"META-INF");
		int count = 0;
		for (String jfile : jfiles) {
			Manifest manifest = SourcecleanerFactory.eINSTANCE.createManifest();
			manifest.setAbsolutePath(jfile);
			cproject.setManifest(manifest);
			manifest.setName(cproject.getName());
			count++;
		}
		if (count > 1 && !"org.eclipse.m2m.qvt.oml.samples".equals(cproject.getName()))
			throw new RuntimeException(
					"should not happen: more than one manifest file for "
							+ cproject.getName());
		return cproject.getManifest();
	}

	private Project addProject(String name) {
		Project project = SourcecleanerFactory.eINSTANCE.createProject();
		project.setAbsolutePath(getWorkspace() + name);
		String fn = project.getAbsolutePath();
		project.setWorkspace(fn.substring(0, fn.lastIndexOf(File.separator)));
		project.setName(name);
		return project;
	}

	private List<String> getAllProjects(String[] excludeName,
			String[] excludePrefix) {
		List<String> projects = new ArrayList<String>();
		File ws = new File("C:\\workspaces_mars\\ws_mars");
		File[] files = ws.listFiles();
		for (int i = 0; i < files.length; i++)
			if (files[i].isDirectory()) {
				File[] subfiles = files[i].listFiles();
				if (subfiles != null)
					for (File subFile : subfiles) {
						// System.out.println(file.getName());
						if (subFile.getName().equals(".project")) {

							String prj = files[i].getName();
							if (!isIn(excludeName, prj))
								if (!isInPrefix(excludePrefix, prj))
									projects.add(prj);
							break;
						}
					}
			}
		return projects;
	}

	private static void clog(String m) {
		if (LOG)
			System.out.println(m);
	}

	private List<String> getFiles(File fil, String extension, String parent_) {
		boolean tb;
		boolean exact = parent_ != null;
		List<String> filez = new ArrayList<String>();
		if (fil.isDirectory()) {
			File[] files = fil.listFiles();
			for (int i = 0; i < files.length; i++)
				if (files[i].isDirectory()) {
					File[] subfiles = files[i].listFiles();
					if (subfiles != null)
						for (File file : subfiles)
							// FP140709
							filez.addAll(getFiles(file, extension, parent_));
				} else {
					// if (files[i].getAbsolutePath().endsWith(
					// extension))
					// tb = true;
					if ((!exact && files[i].getAbsolutePath().endsWith(
							extension))
							|| files[i].getName().equals(extension)) {
						String paren = files[i].getParent();
						paren = paren.substring(paren
								.lastIndexOf(File.separator) + 1);
						if (!exact || parent_.equals(paren))
							filez.add(files[i].getAbsolutePath());
					}
				}
		} else {
			// if (fil.getAbsolutePath().endsWith(
			// extension))
			// tb = true;
			if ((!exact && fil.getAbsolutePath().endsWith(extension))
					|| fil.getName().equals(extension)) {
				String paren = fil.getParent();
				paren = paren.substring(paren.lastIndexOf(File.separator) + 1);
				if (!exact || parent_.equals(paren))
					filez.add(fil.getAbsolutePath());
			}
		}
		return filez;
	}

	/******************************/

	private void resolveDependencies() {
		for (Project proj : configuration.getProjects()) {
			// for (String prj : prjs) {
			if (proj.getName().startsWith(
					"org.isoe.extensionpoint.diagraph.action"))
				tb = true;
			if (proj.getName().startsWith("org.isoe.mbse.sourcecleaner"))
				tb = true;

			if (proj.getName().equals("org.isoe.fwk.utils"))
				tb = true;

			// listManifests();

			for (Dependency dependency : proj.getManifest().getDependencies()) {
				String version = dependency.getVersion();
				String name = dependency.getName();

				Manifest target = findManifest(name, version);
				if (target != null)
					dependency.setDependency(target);
				else {
					// if (!dependency.getName().startsWith("org.eclipse"))

					if (!isInPrefix(EXTERNAL_PLUGINS, dependency.getName()))
						addError("no dependency for " + proj.getName()
								+ " dependency=" + dependency.getName()
								+ " version=" + version);
				}

			}

		}

	}

	private Manifest findManifest(String name, String version) {
		for (Project proj : configuration.getProjects())
			if (name.equals(proj.getManifest().getSymbolicName()))
				if (version == null
						|| version.equals(proj.getManifest().getVersionId()))
					return proj.getManifest();
		return null;
	}

	private void listManifests() {
		for (Project proj : configuration.getProjects()) {
			Manifest m = proj.getManifest();
			clog2("manifest: name="
					+ proj.getManifest().getName()
					+ " symbolicName="
					+ proj.getManifest().getSymbolicName()
					+ " version="
					+ (proj.getManifest().getVersion() == null ? "null" : proj
							.getManifest().getVersion())
					+ " versionId="
					+ (proj.getManifest().getVersionId() == null ? "null"
							: proj.getManifest().getVersionId())
					+ " versionQualifier="
					+ (proj.getManifest().getVersionQualifier() == null ? "null"
							: proj.getManifest().getVersionQualifier())

			)

			;
		}
	}

	private void updateMetaFiles(String ws) {

		for (Project proj : configuration.getProjects()) {
			if (!proj.getName().startsWith("org.eclipse")){
			
			
			// for (String prj : prjs) {
			if (proj.getName().startsWith(
					"org.isoe.extensionpoint.diagraph.action"))
				tb = true;
			if (proj.getName().startsWith("org.isoe.mbse.sourcecleaner"))
				tb = true;

			if (proj.getName().contains("borrowed"))
				tb = true;
			if (proj.getManifest() == null) {
				clog2("not deployable: " + proj.getName());
				notDeployables.add(proj.getName());
			}
			if (proj.getManifest() != null) {

				clog5("reading manifest "
						+ proj.getManifest().getAbsolutePath());
				File manifestfile = new File(proj.getManifest()
						.getAbsolutePath());// getWorkspace1()
											// +
											// proj.getName()
											// +
											// "\\META-INF\\MANIFEST.MF");
				Manifest man = proj.getManifest();
				if (proj.getName().startsWith("org.isoe"))
					man.setDiagraph(true);

				if (manifestfile.exists()) { // &&
												// proj.getName().startsWith("org.isoe")
					// prj.startsWith("org.isoe")
					String targpath = ws + proj.getName();
					// File targdir = new File(targpath);
					File targmdir = new File(targpath + "\\META-INF");
					if (!targmdir.exists())
						new File(targpath + "\\META-INF").mkdirs();

					File tmanifestfile = new File(targpath
							+ "\\META-INF\\MANIFEST.MF");

					if (LOG)
						clog("---------- handling project " + proj.getName()
								+ " target= " + tmanifestfile.getAbsolutePath());

					String mftext = readMetaFile(manifestfile);
					updateMetaFile(proj, manifestfile, tmanifestfile);
				}
			}
			}
		}
	}

	private void clog6(String mesg) {
		if (LOG6)
			System.out.println(mesg);

	}

	private List<String> content;

	private String bundleVendor;

	private String getBundleSymbolicName(){
		for (String lin : content)
			if (lin.startsWith("Bundle-SymbolicName:"))
			  return parseSymbolicName(lin);
		return null;
	}

	private void read(Project project, InputStream in, StringBuffer buffer)
			throws IOException {
		boolean tb = false;
		 boolean stateRequireBundle = false;
		 boolean stateBundleClasspath = false;
		 boolean stateExportPackage = false;
		 content = readManifest(project, in);
		 bundleVendor = null;
		 for (String line : content) {
			 
			    clog(line);
				if (line.startsWith("Require-Bundle:"))
					stateRequireBundle = true;

				if (line.startsWith("Bundle-ClassPath:"))
					stateBundleClasspath = true;

				if (line.startsWith("Export-Package:"))
					stateExportPackage = true;

				if (stateRequireBundle
						&& isInPrefix(keywordsWoRequireBundle, line))
					stateRequireBundle = false;

				if (stateBundleClasspath
						&& isInPrefix(keywordsWoBundleClasspath, line))
					stateBundleClasspath = false;

				if (stateExportPackage && isInPrefix(keywordsWoExportPackage, line))
					stateExportPackage = false;

				if (line.startsWith("Eclipse-LazyStart:"))
					setLazy(project);

				if (line.startsWith("Bundle-Name:"))
					line = setBundleName(project, line);

				if (line.startsWith("Bundle-RequiredExecutionEnvironment:"))
					line = setBundleExcutionEnvironment_(project, line);

				if (line.startsWith("Bundle-Version:"))
					line = setBundleVersion(project, line);

				if (line.startsWith("Bundle-SymbolicName:"))
					setBundleSymbolicName(project, line);

				if (line.startsWith("Bundle-Vendor:")){
					line = setBundleVendor(project, line);
					bundleVendor = line;
				}

				checks(project, line, stateRequireBundle);

				if (stateRequireBundle)
					line = addRequired(project, line);

				if (stateBundleClasspath)
					addBundleClasspath(project, line);

				if (stateExportPackage)
					addExportPackage(project, line);

				String result = line;
				buffer.append(result).append("\n");
		}

		if (bundleVendor == null){
			//buffer.deleteCharAt(buffer.length() - 1);
			//buffer.append(",\n ");
			buffer.append(getBundleVendorLine());
			buffer.append("\n");
			tb = true;
		}
	}






	private List<String> readManifest(Project project, InputStream in)
			throws IOException {
		List<String> result = new ArrayList<String>();
		InputStreamReader inR = new InputStreamReader(in);
		BufferedReader bufr = new BufferedReader(inR);
		String line;
		while ((line = bufr.readLine()) != null)
			result.add(line);
		return result;
	}

	private void read_old(Project project, InputStream in, StringBuffer buffer)
			throws IOException {
		InputStreamReader inR = new InputStreamReader(in);
		BufferedReader bufr = new BufferedReader(inR);
		String line;
		boolean stateRequireBundle = false;
		boolean stateBundleClasspath = false;
		boolean stateExportPackage = false;
		while ((line = bufr.readLine()) != null) {

			if (line.startsWith("Require-Bundle:"))
				stateRequireBundle = true;

			if (line.startsWith("Bundle-ClassPath:"))
				stateBundleClasspath = true;

			if (line.startsWith("Export-Package:"))
				stateExportPackage = true;

			if (stateRequireBundle
					&& isInPrefix(keywordsWoRequireBundle, line))
				stateRequireBundle = false;

			if (stateBundleClasspath
					&& isInPrefix(keywordsWoBundleClasspath, line))
				stateBundleClasspath = false;

			if (stateExportPackage && isInPrefix(keywordsWoExportPackage, line))
				stateExportPackage = false;

			if (line.startsWith("Eclipse-LazyStart:"))
				setLazy(project);

			if (line.startsWith("Bundle-Name:"))
				line = setBundleName(project, line);

			if (line.startsWith("Bundle-RequiredExecutionEnvironment:"))
				line = setBundleExcutionEnvironment_(project, line);

			if (line.startsWith("Bundle-Version:"))
				line = setBundleVersion(project, line);

			if (line.startsWith("Bundle-SymbolicName:"))
				setBundleSymbolicName(project, line);

			if (line.startsWith("Bundle-Vendor:"))
				line = setBundleVendor(project, line);

			checks(project, line, stateRequireBundle);

			if (stateRequireBundle)
				line = addRequired(project, line);

			if (stateBundleClasspath)
				addBundleClasspath(project, line);

			if (stateExportPackage)
				addExportPackage(project, line);

			String result = line;
			buffer.append(result).append("\n");
		}

	}

	private void setLazy(Project project) {
		project.getManifest().setLazy(true);
	}

	// Bundle-Version: 1.0.1.201405231112
	private void updateMetaFile(Project project, File infile, File outfile) {// C:\workspaces\v130926\isoe.mail.rcp\META-INF\MANIFEST.MF
		boolean tb = true;
		boolean check = true;
		//bundleVendor = null;
		//if (project.getName().startsWith("lirmm"))
		//	tb = true;
		try {
			InputStream in = new FileInputStream(infile);
			StringBuffer buffer = new StringBuffer();
			read(project, in, buffer);
			in.close();
			OutputStream os = new FileOutputStream(outfile);
			os.write(buffer.toString().getBytes());
			os.close();
		} catch (Exception e) {
			addError("error " + e.toString());
			throw new RuntimeException("error " + e.toString());
		}
		// }
	}

	private String updateBundleVersion(Project project, String line) {
		boolean check;
		if (line.equals("Bundle-Version: " + INCOMPLETE_VERSION_A_))
			check = true;
		if (line.equals("Bundle-Version: " + INCOMPLETE_VERSION_B_))
			check = true;

		if (project.getName().equals("org.isoe.fwk.utils")
				&& line.startsWith("Bundle-Version"))
			tb = true;

		if (line.equals("Bundle-Version: 1.0.1.201405231112"))// Bundle-Version:
																// 1.0.1.20140523
			tb = true;

		if (line.equals("Bundle-Version: 1.0.1.20140523"))// Bundle-Version:
															// 1.0.1.20140523
			tb = true;

		if (line.contains("1.0.0.qualifier")
				|| line.equals("Bundle-Version: " + PRIOR_VERSION_MAJ_MIN_MICRO
						+ "." + PRIOR_VERSION_QUALIFIER)
				|| line.equals("Bundle-Version: " + PRIOR_VERSION_MAJ_MIN_MICRO))

			line = upgradeVersion(line);

		return line;
	}

	private void checks(Project project, String line, boolean stateRequireBundle) {
		boolean check;
		String linetrim = line.trim();
		if (line.startsWith("Bundle-Version:")
				&& !line.equals("Bundle-Version: 1.0.0.qualifier"))
			check = true;

		if (linetrim.startsWith("org.isoe"))
			check = true;

		if (stateRequireBundle)
			if (linetrim.startsWith("org.isoe.extensionpoint.diagraph")
					|| linetrim
							.startsWith("Require-Bundle: org.isoe.extensionpoint.diagraph")) {// org.isoe.extensionpoint.diagraph;

				clog("====toreplace= "
						+ (project == null ? "" : project.getName()) + " for "
						+ linetrim);
			}

		if (stateRequireBundle)
			if (linetrim.startsWith("org.isoe.")
					|| linetrim.startsWith("Require-Bundle: org.isoe.")) {// org.isoe.extensionpoint.diagraph;
				if (!linetrim.contains(";bundle-version"))
					clog("====noversion in "
							+ (project == null ? "" : project.getName())
							+ " for " + linetrim);
			}

		if (linetrim.startsWith("org.isoe.")
				|| linetrim.startsWith("Require-Bundle: org.isoe.")) {
			if ((linetrim.contains(";bundle-version=\"" + INCOMPLETE_VERSION_A_
					+ "\""))
					|| (linetrim.contains(";bundle-version=\""
							+ INCOMPLETE_VERSION_B_ + "\""))

			)

			{
				check = true;
			}

		}

	}

	private String updateDependency(String line) {
		String result = line;
		String l = caseUpdate(line); // FP140507
		if (l != null)
			result = l;
		l = caseIncomplete(line);
		if (l != null)
			result = l;
		return result;
	}

	private void clog2(String mesg) {
		if (LOG2)
			System.out.println(mesg);

	}

	private String setBundleExcutionEnvironment_(Project project, String line) {
		String result = line;
		boolean check = false;
		if (line.equals("Bundle-RequiredExecutionEnvironment: JavaSE-1.7"))
			result = "Bundle-RequiredExecutionEnvironment: JavaSE-1.6";
		if (line.startsWith("Bundle-RequiredExecutionEnvironment")
				&& !line.equals("Bundle-RequiredExecutionEnvironment: JavaSE-1.6")
				&& !line.equals("Bundle-RequiredExecutionEnvironment: J2SE-1.5"))
			check = true;
		String env = line.substring("Bundle-RequiredExecutionEnvironment: "
				.length());
		project.getManifest().setExecutionEnvironment(env);
		return result;
	}

	private String cut(String in, String what) {
		return in.substring(0, in.indexOf(what))
				+ in.substring(in.indexOf(what) + what.length(), in.length());
	}

	private void setBundleClasspath_(Project project, String line) {
		// Bundle-ClassPath: lib/simmetrics_jar_v1_6_2_d07_02_07.jar,
		// .
		String result = line;
		String classpath = line.substring("Bundle-ClassPath: ".length());
		if (classpath.endsWith(","))
			classpath = classpath.substring(classpath.length() - 1);
		// project.getManifest().getClasspathes().add
	}

	private void addBundleClasspath(Project project, String line) {
		String linetrim = line.trim();
		if (linetrim.startsWith("Bundle-ClassPath: "))
			linetrim = linetrim.substring("Bundle-ClassPath: ".length());
		if (linetrim.endsWith(","))
			linetrim = linetrim.substring(0, linetrim.length() - 1);
		String path = linetrim;
		ClassPath classPath = SourcecleanerFactory.eINSTANCE.createClassPath();
		classPath.setName(path);
		project.getManifest().getClasspathes().add(classPath);
	}

	private void addExportPackage(Project project, String line) {
		String linetrim = line.trim();
		if (linetrim.startsWith("Export-Package:"))
			linetrim = linetrim.substring("Export-Package: ".length());
		if (linetrim.endsWith(","))
			linetrim = linetrim.substring(0, linetrim.length() - 1);
		// uses:=&quot;o
		if (linetrim.contains("uses")) // uses:="org.eclipse.jface.action
			throw new RuntimeException("should not happen in exportPackage: "
					+ linetrim);
		Export export = SourcecleanerFactory.eINSTANCE.createExport();
		export.setName(linetrim);
		project.getManifest().getExports().add(export);
	}

	private String addRequired(Project project, String line) {
		String linetrim = line.trim();
		String result = null;
		boolean isDiagaphDependency = false;
		if ((linetrim.startsWith("org.isoe.") || linetrim
				.startsWith("Require-Bundle: org.isoe."))) {
			isDiagaphDependency = true;
		}
		result = updateDependency(line);

		if (linetrim.startsWith("Require-Bundle: "))
			linetrim = linetrim.substring("Require-Bundle: ".length());
		if (linetrim.endsWith(","))
			linetrim = linetrim.substring(0, linetrim.length() - 1);
		String bundleName = linetrim;

		String bundleVersion = null;

		// mtbe.fr.trace;visibility:=reexport

		if (bundleName.contains("visibility")
				&& bundleName.contains("bundle-version"))
			tb = true;
		else if (bundleName.contains("visibility"))
			tb = true;
		boolean reexport = false;
		if (bundleName.contains(";visibility:=reexport")) {
			bundleName = cut(bundleName, ";visibility:=reexport");
			reexport = true;
		}

		if (bundleName.contains(";bundle-version")) {
			bundleVersion = bundleName.substring(bundleName
					.indexOf(";bundle-version") + 1);// bundle-version="3.7.101"
			bundleVersion = bundleVersion.substring("bundle-version=\""
					.length());
			bundleVersion = bundleVersion.substring(0,
					bundleVersion.length() - 1);
			bundleName = bundleName.substring(0, bundleName.indexOf(";"));
		} else
			tb = true;

		Dependency dependency = SourcecleanerFactory.eINSTANCE
				.createDependency();
		if (reexport)
			dependency.setReexport(true);
		dependency.setName(bundleName);
		if (bundleVersion != null)
			dependency.setVersion(bundleVersion);
		dependency.setDiagraph(isDiagaphDependency);
		project.getManifest().getDependencies().add(dependency);
		dependency.setRequerant(project.getManifest());
		return result;
	}


	private String setBundleVersion(Project project, String line) {
		// the correct format (major.minor.micro.qualifier)
		String result = updateBundleVersion(project, line);
		String version = line.substring("Bundle-Version: ".length());
		if (version.contains(";"))
			version = version.substring(0, version.indexOf(";")).trim();
		String versionId = version;
		String versionQualifier = null;
		int th = thirdIndexOf(".", version);
		if (th != -1) {
			versionId = version.substring(0, th - 1);
			versionQualifier = (version.length() > th) ? version.substring(th)
					: null;
		}
		project.getManifest().setVersion(version);
		project.getManifest().setVersionId(versionId);
		if (versionQualifier == null)
			throw new RuntimeException(
					"should not happen: no qualifier for version " + versionId
							+ " in project " + project.getName());
		project.getManifest().setVersionQualifier(versionQualifier);
		return result;
	}

	private int thirdIndexOf(String what, String in) {
		if (!in.contains(what))
			return -1;
		String ch = in;
		int i = 0;
		while (true) {
			if (!ch.contains(what) || i > 3)
				break;
			ch = ch.substring(ch.indexOf(what) + 1);
			i++;
		}
		if (i < 3)
			return -1;
		else
			return in.indexOf(ch);
	}


	private String parseSymbolicName(String line) {
		String name = line.substring("Bundle-SymbolicName: ".length());
		if (name.contains(";"))
			name = name.substring(0, name.indexOf(";"));
		name = name.trim();
		return name;
	}

	private void setBundleSymbolicName(Project project, String line) {
		project.getManifest().setSymbolicName(parseSymbolicName(line));
		if (line.contains("singleton:=true"))
			project.getManifest().setSingleton(true);
	}

	private void createDeployables() {
		clog6("createDeployables");
		for (Project proj : configuration.getProjects()) {
			String pluginname = proj.getManifest().getSymbolicName();
			if (isInContains(DEPLOY, pluginname)
					&& !isInPrefix(NOT_DEPLOY, pluginname))
				addToDeploy_(pluginname);
		}
	}

	private void createThirdParty() {
		clog6("createThirdParty");
		for (String third : THIRD_PARTY_) {
			addThirdParty(third);
		}
	}


	private String getBundleVendorLine() {
		String bname = getBundleSymbolicName();
		boolean isLirmm_ = false;;
		if (bname.toLowerCase().contains("lirmm") || bname.toLowerCase().contains("mtbe"))
			isLirmm_ = true;
		String vendor = (isLirmm_?"Lirmm Marel":"LGi2P Isoe");
		return "Bundle-Vendor: "+vendor;
	}

	private String setBundleVendor(Project project, String line) {
      if (project.getName().contains("preferences"))
    	  tb = true;
        System.out.println("                    "+project.getName());
		String result = line;
		if (result.startsWith("Bundle-Vendor")
				&& !result.equals("Bundle-Vendor")
				&& (result.toLowerCase().contains("isoe") || result.toLowerCase()
						.contains("lgi2p"))) {
			result = "Bundle-Vendor: LGi2P Isoe";
		}
		String vendor = result.substring("Bundle-Vendor :".length()).trim();
		if (vendor.equals("%providerName") || vendor.equals("www.example.org") || vendor.trim().isEmpty()){
			String bname = getBundleSymbolicName();
			boolean isLirmm_ = false;;
			if (bname.toLowerCase().contains("lirmm") || bname.toLowerCase().contains("mtbe"))
				isLirmm_ = true;
			vendor = (isLirmm_?"Lirmm Marel":"LGi2P Isoe");
		}
        if (vendor.trim().isEmpty())
        	tb = true;
		project.getManifest().setVendor(vendor);
		result = "Bundle-Vendor: "+vendor;
 		return result;
	}

	private String setBundleName(Project project, String line) {
		boolean modified = false;
		String result = line;
		String name = line.substring("Bundle-Name: ".length());
		if (name.equals("%pluginName")) {
			name = "";
			modified = true;
		}

		if (name.trim().isEmpty()) {
			name = project.getName();
			name = name.substring(name.lastIndexOf(".") + 1);
			name = name.substring(0, 1).toUpperCase() + name.substring(1);
			modified = true;
		}

		if (!name.toLowerCase().contains("diagraph")
				&& !name.toLowerCase().contains("isoe")) {
			name = "Diagraph " + name;
			modified = true;
		}

		if (name.contains("DIAGRAPH")) {
			name = name.replace("DIAGRAPH", "Diagraph");
			modified = true;
		}

		if (name.contains("ISOE")) {
			name = name.replace("ISOE", "Isoe");
			modified = true;
		}

		project.getManifest().setName(name.trim());
		if (modified)
			result = "Bundle-Name: " + name;
		return result;
	}

	private void updateManifestFile(String src, File File, String old,
			String niu) {
		// TODO Auto-generated method stub

	}

	private void updatePluginFile(String src, File File, String old, String niu) {
		// TODO Auto-generated method stub

	}

	/*--------------------*/

	private String upgradeVersion(String line) {
		String newVersion = "Bundle-Version: " + VERSION_MAJ_MIN_MICRO_ + "."
				+ VERSION_QUALIFIER;
		// the correct format (major.minor.micro.qualifier)
		clog2("upgradeVersion from:" + line + " to:" + newVersion);
		return newVersion;
	}

	private String caseIncomplete(String line) {
		String result = null;
		if (!line.contains("x-internal:=true")) {
			String linetrim = line.trim();
			if (!linetrim.contains(";bundle-version")) {
				if (line.endsWith(",")) {
					result = line.substring(0, line.length() - 1);
					result += ";bundle-version=\"" + VERSION_MAJ_MIN_MICRO_
							+ "\"";
					result += ",";
					clog2("caseUpdate(1) from:" + line + " to:" + result);
					// return result;
				} else {
					result = line;
					result += ";bundle-version=\"" + VERSION_MAJ_MIN_MICRO_
							+ "\"";
					clog2("caseUpdate(2) from:" + line + " to:" + result);
					// return line;
				}
			}
		}
		return result;
	}

	private String caseUpdate(String line) {
		String result = null;
		String linetrim = line.trim();
		if (linetrim.contains(";bundle-version=\""
				+ PRIOR_VERSION_MAJ_MIN_MICRO)
				|| linetrim.contains(";bundle-version=\"1.0.0\"")
				|| linetrim.contains(";bundle-version=\"1.0.1\"")) {
			String st = line.substring(0, line.indexOf(";bundle-version"));
			String t = st + ";bundle-version=\"" + VERSION_MAJ_MIN_MICRO_
					+ "\"";
			if (line.endsWith(","))
				t += ",";
			result = t;
			clog2("caseUpdate(0) from:" + line + " to:" + result);
			// return result;
		}
		return result;
	}

	@Override
	public List<String> getExtensionPointList() {
		return extensionpoints;
	}

	@Override
	public void addExtension(Project project, Extension extension) {
		project.getPlugin().getExtensions().add(extension);
		// project.setExtension(extension);
	}

	@Override
	public List<ExtensionPoint> getIsoeExtensionPoints() {
		List<ExtensionPoint> result = new ArrayList<ExtensionPoint>();
		for (Project project : configuration.getProjects())
			if (project.getPlugin() != null
					&& project.getPlugin().getExtensionPoints() != null && !project.getPlugin().getExtensionPoints().isEmpty())
				result.addAll(project.getPlugin().getExtensionPoints()); //FP141216
		return result;// configuration.getExtensionPoints();
	}

	@Override
	public List<String> getPluginsToDeploy() {
		return pluginsToDeploy;
	}

	@Override
	public List<String> getThirdpartyToDeploy() {
		return thirdpartyToDeploy;
	}

	@Override
	public String getVersion() {
		return VERSION_MAJ_MIN_MICRO_ + "." + VERSION_QUALIFIER;
	}

	@Override
	public boolean deploySource(String plugin) {
		return !isIn(NO_SOURCE, plugin);
	}

	@Override
	public String getThirdPartyVersion_() {
		return VERSION_THIRD_PARTY;
	}

}
