package org.isoe.fwk.archives;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.isoe.mbse.sourcecleaner.Configuration;
import org.isoe.mbse.sourcecleaner.Java;
import org.isoe.mbse.sourcecleaner.Project;
import org.isoe.mbse.sourcecleaner.Source;
import org.isoe.mbse.sourcecleaner.SourcecleanerFactory;
import org.isoe.mbse.sourcecleaner.SourcecleanerPackage;

/**
 * @author Pfister
 *
 */
public class DiagraphPluginUpdater {
	// private static final boolean LINE = true;
	// private static final boolean SOURCE = false;
	private enum Mode {
		line, source
	};

	private static final boolean LOG = true;
	private static final boolean HANDLE_COPYRIGHTS = false;

	private static final String USER_FOLDER_ = "C:\\Users\\Pfister";
	private static final String BASE_FOLDER_ = "git-diagraph-local";
	private static final String REPO_FOLDER_ = "git-diagraph-local";

	private static final String USER_FOLDER = "C:\\workspaces";
	// private static final String BASE_FOLDER = "git-diagraph-local";
	// private static final String REPO_FOLDER = "v130926";

	private static final String TARGET_ = "ter";
	private static final int HEADER_LENGTH = 10;

	private static final String PRJ_FOLDER = "v130926";
	private static final int PRJ_FOLDER_LEN = PRJ_FOLDER.length();
	static final String JAVA_EXT = ".java";

	private static final String TARGET_2 = "ter2";
	// private static final String BASE_FOLDER_2 = TARGET;
	private static final String REPO_FOLDER_2 = TARGET_;

	// private static final String VERSION = "1.0.0.20140227";
	// private static final String OLD_VERSION = "1.0.0.qualifier";

	private static final String OLD_VERSION___ = "1.0.0.20140227";
	private static final String VERSION___ = "1.0.0.20140305";

	private static final String OLD_VERSION__ = "1.0.0.20140305";
	private static final String VERSION_MAJ__ = "1.0.1.20140305";
	private static final String VERSION_MIN__ = "1117";

	/*
	 * private static final String OLD_VERSION_MAJ_ = "1.0.1.20140305"; private
	 * static final String OLD_VERSION_MIN_ = "1117";
	 *
	 * private static final String VERSION_MAJ = "1.0.1.20140305"; private
	 * static final String VERSION_MIN = "1204";
	 */

	/*
	 * private static final String OLD_VERSION_MAJ = "1.0.1.20140305"; private
	 * static final String OLD_VERSION_MIN = "1204";
	 *
	 * private static final String VERSION_MAJ = "1.0.1.20140317"; private
	 * static final String VERSION_MIN = "0157";
	 */

	private static final String OLD1_VERSION_MAJ = "1.0.1.20140317";
	private static final String OLD1_VERSION_MIN = "0158";

	private static final String OLD_VERSION_MAJ = "1.0.1.20140317";
	private static final String OLD_VERSION_MIN = "0159";

	private static final String VERSION_MAJ = "1.0.1.20140428";
	private static final String VERSION_MIN = "1111";

	private List<String> javaSrcs;
	private List<String> projekts_;
	private Map<String, List<String>> projects;// = new HashMap<String,
												// List<String>>();

	private ResourceSet resourceSet;

	private URI cleanerUri;

	private static int target = 1;

	private Configuration configuration;

	private DiagraphPluginUpdater() {
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

	}

	private static Configuration load(ResourceSet resourceSet, URI uri) {
		Resource resource = resourceSet.getResource(uri, true);
		Configuration result = (Configuration) resource.getContents().get(0);
		return result;
	}

	private static void save(ResourceSet resourceSet, URI uri, Configuration configuration)
			throws IOException {
		Resource resource = resourceSet.createResource(uri);
		resource.getContents().add(configuration);
		resource.save(null);
	}

	// C:\Users\Pfister\git-diagraph-local\git-diagraph-local\v130926\
	// private static final String WS_ = USER_FOLDER + "\\" + BASE_FOLDER + "\\"
	// + REPO_FOLDER + "\\"
	// + PRJ_FOLDER + "\\";

	private static final String getWorkspace1() {
		return USER_FOLDER + "\\" + PRJ_FOLDER + "\\";
	}

	private static final String getWorkspace2() {
		return USER_FOLDER + "\\" + REPO_FOLDER_2 + "\\" + PRJ_FOLDER + "\\";
	}

	private static String getWorkspace() {
		if (target == 1)
			return getWorkspace1();
		else
			return getWorkspace2();
	}

	private static String getRepoFolder() {
		if (target == 1)
			return PRJ_FOLDER;
		else
			return REPO_FOLDER_2;
	}

	private static String getTarget() {
		if (target == 1)
			return TARGET_;
		else
			return TARGET_2;
	}

	private String getPackage(String path) {
		// C:\Users\Pfister\git-diagraph-local\git-diagraph-local\v130926\org.isoe.fwk.megamodel.deploy\repository-bundle\TestCategorie.java
		String result = "";
		try {
			String p = path.substring(path.indexOf(PRJ_FOLDER) + PRJ_FOLDER_LEN
					+ 1);
			result = p.substring(0, p.indexOf("\\src"));

		} catch (Exception e) {
			return null;
		}
		return result;
	}

	private void listprj(String prj) {
		File javafile = new File(prj);// C:\Users\Pfister\git-diagraph-local\git-diagraph-local\v130926\isoe.mail.rcp\.project
		try {
			InputStream in = new FileInputStream(javafile);

			try {
				InputStreamReader inR = new InputStreamReader(in);
				BufferedReader buf = new BufferedReader(inR);
				String line;
				while ((line = buf.readLine()) != null)
					clog(line);

			} finally {
				in.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
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

	private void initFiles() {

		projects = new HashMap<String, List<String>>();
		configuration = SourcecleanerFactory.eINSTANCE.createConfiguration();

		javaSrcs = new ArrayList<String>();
		projekts_ = getProjects();

		for (String excluded : EXCLUDE_PACKAGES_2) {
			projekts_.remove(excluded);
		}
		for (String prj : projekts_)
			javaSrcs.addAll(getJavaFilesInProject3_(prj));

		mark("Falleri");
		mark("acceleo");
		mark("Obeo");
		// mark("iaml");
		// mark("borrowed");
		mark("mia-software");
		clog(projekts_.size() + " projects, " + javaSrcs.size() + " java files");
	}

	private void mark(String excl) {
		List<String> excls = go(excl, false);
		for (String sr : excls) {
			Source source = findSource(sr);
			if (source != null)
				source.setMark(true);
		}
	}

	private void copyProjects() throws IOException {
		for (String prj : projekts_) {
			File sprjfile = new File(getWorkspace1() + prj + "\\.project");
			String targpath = getWorkspace2() + prj;
			File targdir = new File(targpath);
			if (targdir.exists())
				delete(targdir);
			new File(targpath).mkdirs();
			File tprjfile = new File(targpath + "\\.project");
			copyFile(sprjfile, tprjfile);
		}
	}

	private void updateMetaFiles_(List<String> prjs, String ws)
			throws IOException {
		for (String prj : prjs) {
			if (LOG)
				clog("---------- handling project " + prj);

			File manifestfile = new File(getWorkspace1() + prj
					+ "\\META-INF\\MANIFEST.MF");
			if (manifestfile.exists() && prj.startsWith("org.isoe")) { //FP140428 prj.startsWith("org.isoe")
				String targpath = ws + prj;
				// File targdir = new File(targpath);
				File targmdir = new File(targpath + "\\META-INF");
				if (!targmdir.exists())
					new File(targpath + "\\META-INF").mkdirs();
				File tmanifestfile = new File(targpath
						+ "\\META-INF\\MANIFEST.MF");

				  updateMetaFile(prj, manifestfile, tmanifestfile);
			}
		}
		System.out.println(corr);
	}

	private void updateFeatures_(String prj, String ws) throws IOException {

		File featurefile = new File(getWorkspace1() + prj + "\\feature.xml");
		if (featurefile.exists()) {
			String targpath = ws + prj;

			File tfeatfile = new File(targpath + "\\feature.xml");
			updateFeatureFile(featurefile, tfeatfile);
		}

	}

	private void updateFeatureFile(File infile, File outfile) {

		try {
			InputStream in = new FileInputStream(infile);
			StringBuffer buffer = new StringBuffer();
			try {
				InputStreamReader inR = new InputStreamReader(in);
				BufferedReader buf = new BufferedReader(inR);
				String line;
				while ((line = buf.readLine()) != null) {

					String linetrim = line.trim();

					if (linetrim.equals("version=\"0.0.0\"")) {
						line = line.replace("0.0.0", VERSION_MAJ);
					}
					/*
					 * if (linetrim.equals("Bundle-Version: 1.0.0.qualifier")){
					 * line = line.replace("1.0.0.qualifier",
					 * VERSION_MAJ+VERSION_MIN); }
					 */

					if (linetrim.equals("version=\"" + OLD_VERSION_MAJ + "\"")) {
						line = line.replace(OLD_VERSION_MAJ, VERSION_MAJ);
					}

					String result = line;
					buffer.append(result).append("\n");
				}
			} finally {
				in.close();
			}
			OutputStream os = new FileOutputStream(outfile);
			os.write(buffer.toString().getBytes());
			os.close();
		} catch (IOException e) {
			clog("error " + e.toString());
		}
	}

	String corr="";
	private void updateMetaFile(String prj, File infile, File outfile) {// C:\workspaces\v130926\isoe.mail.rcp\META-INF\MANIFEST.MF

		boolean check_;
		//if (prj.startsWith("org.isoe")) {
			try {
				InputStream in = new FileInputStream(infile);
				StringBuffer buffer = new StringBuffer();
				try {
					InputStreamReader inR = new InputStreamReader(in);
					BufferedReader buf = new BufferedReader(inR);
					String line;
					while ((line = buf.readLine()) != null) {

						String linetrim = line.trim();

						if (line.equals("Bundle-RequiredExecutionEnvironment: JavaSE-1.7"))
							line = "Bundle-RequiredExecutionEnvironment: JavaSE-1.6";

						if (line.startsWith("Bundle-RequiredExecutionEnvironment")
								&& !line.equals("Bundle-RequiredExecutionEnvironment: JavaSE-1.6")
								&& !line.equals("Bundle-RequiredExecutionEnvironment: J2SE-1.5"))
							check_ = true;

						if (line.startsWith("Bundle-Version:")
								&& !line.equals("Bundle-Version: 1.0.0.qualifier")

						)
							check_ = true;

						if (line.equals("Bundle-Version: " + OLD_VERSION_MAJ
								+ OLD_VERSION_MIN))
							line = "Bundle-Version: " + VERSION_MAJ
									+ VERSION_MIN;

						if (line.equals("Bundle-Version: " + "1.0.0.qualifier"))
							line = "Bundle-Version: " + VERSION_MAJ
									+ VERSION_MIN;

						/*
						 * org.isoe.extensionpoint.transformation;bundle-version=
						 * "1.0.1.20140317" Bundle-Vendor: LGi2P Isoe
						 * Bundle-Version: 1.0.1.201403170157 Bundle-Version:
						 * 1.0.0.qualifier
						 */

						if (linetrim.contains(";bundle-version=\"1.0.1\"")){

							corr += "\n"+prj + " - " + linetrim;

						}

						// TODO use regexps
						if (linetrim.startsWith("org.isoe.")
								|| linetrim
										.startsWith("Require-Bundle: org.isoe.")) {
							if (linetrim.contains(";bundle-version=\""
									+ OLD_VERSION_MAJ + "\"")
									|| linetrim
											.contains(";bundle-version=\"1.0.0\"")
									|| linetrim
											.contains(";bundle-version=\"1.0.1\""))

							{
								String st = line.substring(0,
										line.indexOf(";bundle-version"));
								String t = st + ";bundle-version=\""
										+ VERSION_MAJ + "\"";
								if (line.endsWith(","))
									t += ",";

								line = t;

								// org.isoe.extensionpoint.similarity;bundle-version="1.0.0",
								// org.isoe.extensionpoint.similarity;bundle-version="1.0.1.20140305",

							}

							// org.isoe.fwk.log;bundle-version="1.0.0",
						}

						if (line.startsWith("Bundle-Vendor")
								&& !line.equals("Bundle-Vendor")
								&& (line.toLowerCase().contains("isoe") || line
										.toLowerCase().contains("lgi2p")))
							line = "Bundle-Vendor: LGi2P Isoe";

						// if (line.startsWith("Bundle-Vendor") &&
						// line.contains("%providerName"))
						// line = "Bundle-Vendor: LGi2P Isoe";
						String result = line;
						buffer.append(result).append("\n");
					}

				} finally {
					in.close();
				}
				OutputStream os = new FileOutputStream(outfile);
				os.write(buffer.toString().getBytes());
				os.close();

			} catch (IOException e) {
				clog("error " + e.toString());
			}
		//}
	}

	public static void main(String[] args) {
		DiagraphPluginUpdater c = new DiagraphPluginUpdater();
		try {
			c.exec();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
	}

	// static final String[] EXCLUDED_PACKAGES_ = { "org.eclipse.gmf.codegen" };
	static final String[] EXCLUDE_PACKAGES_1_ = { "org.eclipse.gmf.codegen",
			"org.eclipse.gmf", "org.isoe.fwk.utils.borrowed" };
	static final String[] EXCLUDE_PACKAGES_2 = { "org.eclipse.gmf.codegen",
			"org.eclipse.gmf" };

	private void exec() throws IOException {
		// clons("ter");
		target = 1;
		initFiles();
		copyProjects();
		String ws = getWorkspace1();// getWorkspace2();
		List<String> prjs = projekts_;
		updateMetaFiles_(prjs, ws);
		// updateFeatures("org.isoe.diagraph.features", ws);
		if (HANDLE_COPYRIGHTS)
			handleCopyrights();
	}

	private void handleCopyrights() {
		insertNastovPfisterEplLicenseIfNotCopyrighted();
		insertPfisterEplLicenseIfNotCopyrighted();
		insertNastovEplLicenseIfNotCopyrighted();
		changeEmptyCopyrightToEplLicense();
		insertEplLicenseRemainings();
		getNotCopyrighted2();
	}

	private List<String> insertNastovEplLicenseIfNotCopyrighted() {
		String[] srcs = { "nastov" };
		String[] targs = { "Blazo Nastov" };
		// List<String> r = getSources(srcs, Mode.source, true);

		List<String> result = insertEplLicenseIfNotCopyrighted(srcs, targs);
		register(result, targs, "EplLicenseIfNotCopyrighted");
		return result;

	}

	private List<String> insertPfisterEplLicenseIfNotCopyrighted() {
		String[] srcs = { "pfister" };
		String[] targs = { "Francois Pfister" };
		// List<String> r = getSources(srcs, Mode.source, true);
		List<String> result = insertEplLicenseIfNotCopyrighted(srcs, targs);
		register(result, targs, "EplLicenseIfNotCopyrighted");
		return result;
	}

	private List<String> getNotCopyrighted1() {
		List<String> r = goNotCopyrighted(Mode.source, true);
		return r;
	}

	private List<String> getNotCopyrighted2() {
		clog("**** NOT COPYRIGHTED ****");
		List<String> result = new ArrayList<String>();
		List<String> kwds = new ArrayList<String>();
		String[] kw = new String[1];
		kw[0] = "opyright";
		kwds.add(kw[0]);
		List<Project> prjs = configuration.getProjects();
		for (Project project : prjs) {
			List<Java> src = project.getSources();
			for (Java source : src) {
				if (!source.isHandled()) {
					try {
						if (!containsPerSource(source.getAbsolutePath(), kwds)) {
							result.add(source.getAbsolutePath());
							clog("________" + source.getAbsolutePath());
						}
					} catch (IOException e) {
						throw new RuntimeException(e.toString());
					}
				}
			}
		}
		return result;
	}

	private List<String> insertNastovPfisterEplLicenseIfNotCopyrighted() {
		String[] srcs = { "bnastov", "pfister" };
		String[] targs = { "Blazo Nastov", "Francois Pfister" };
		// List<String> r = getSources(srcs, Mode.source, true);
		List<String> result = insertEplLicenseIfNotCopyrighted(srcs, targs);
		register(result, targs, "EplLicenseIfNotCopyrighted");
		return result;
	}

	private void register(List<String> srcs, String[] keys_, String mesg) {
		String com = "";
		if (keys_ != null)
			for (String k : keys_)
				com += k + " ";
		com += mesg;
		for (String srcName : srcs) {
			Source s = findSource(srcName);
			s.setHandled(true);
			s.setComment(com);
		}
	}

	private List<String> insertEplLicenseIfNotCopyrighted(String[] srcs,
			String[] trgs) {
		String[] excludedPakages = null;// { "org.eclipse.gmf.codegen" };//
										// ,"matcher.mtbe","mtbe.fr"};
		List<String> epl = getEplLicense(trgs);
		List<String> handled = insertEplLicenseIfNotCopyrighted(getTarget(),
				excludedPakages, srcs, trgs, epl);
		for (String f : handled) {
			System.out.println(f);
		}
		return handled;
	}

	private List<String> changeEmptyCopyrightToEplLicense() {
		String[] excludedPakages = { "org.eclipse.gmf.codegen" };// ,"matcher.mtbe","mtbe.fr"};
		List<String> epl = getEplLicense();
		epl.add(" */");
		List<String> handled = handleEmptyCopyrightsSrc(getTarget(),
				excludedPakages, epl);
		register(handled, null, "EmptyCopyrightToEplLicense");
		return handled;
	}

	private List<String> insertEplLicenseRemainings() {
		List<String> epl = new ArrayList<String>();
		epl.add(" /**");
		epl.addAll(getEplLicense());
		epl.add(" */");
		List<String> handled = insertEplLicenseRemainings(getTarget(), epl);
		register(handled, null, "insertEplLicenseRemainings");
		return handled;
	}

	private List<String> getEplLicense(String[] contributors) {
		List<String> result = new ArrayList<String>();
		result.add(" /**");
		result.addAll(getEplLicense());
		result.add(" */");
		String contrib = " *    ";
		for (int i = 0; i < contributors.length; i++) {
			contrib += contributors[i];
			if (i < contributors.length - 1)
				contrib += " , ";
		}
		contrib += " (ISOE-LGI2P) - initial API and implementation";
		result.set(9, contrib);
		return result;
	}

	private static List<String> getEplLicense() {
		List<String> result = new ArrayList<String>();
		result.add(" * Copyright (c) 2014 Laboratoire de Genie Informatique et Ingenierie de Production - Ecole des Mines d'Ales");
		result.add(" * ");
		result.add(" * All rights reserved. This program and the accompanying materials");
		result.add(" * are made available under the terms of the Eclipse private License v1.0");
		result.add(" * which accompanies this distribution, and is available at");
		result.add(" * http://www.eclipse.org/legal/epl-v10.html");
		result.add(" * ");
		result.add(" * Contributors:");
		result.add(" *    Francois Pfister (ISOE-LGI2P) - initial API and implementation");
		return result;
	}

	private void clons_(String folder) {

		String[] obss = { "copyright", "obeo" };
		List<String> obssss = getSources(obss, Mode.line, false);

		/*
		 * for (String ob : obssss) { clog_(ob); }
		 */

		String accs = "acceleo";
		List<String> accss = go(accs, false);

		String iams = "iaml";
		List<String> iamss = go(iams, false);

		String[] bps = { "borrowed", "pfister" };
		List<String> bpss = getSources(bps, Mode.source, false);

		String mias = "mia-software";
		List<String> miass = go(mias, false);

		String[] jrfbnp = { "bnastov", "pfister" };
		List<String> jrfbnps = getSources(jrfbnp, Mode.source, false);

		String[] jrfbn = { "bnastov" };
		List<String> jrfbns = getSources(jrfbn, Mode.line, false);

		String jrf1 = "Falleri";
		List<String> jrfs1 = go(jrf1, false);

		String[] r = { "<copyright>", "</copyright>", "* $Id$", "/**" };
		List<String> gens = getSources(r, Mode.source, false);

		String g = "package org.eclipse.gmf";
		List<String> gmfs = go(g, false);
		String[] r0 = { "//FP", "//tralala" };
		String[][] trafo = getTransformation1(r0);
		// clog_("start");
		for (String gen : gens) {
			if (!gmfs.contains(gen) && !jrfs1.contains(gen)) {
				clog(gen);
				clone(gen, folder, trafo);
			}
		}

		// clog_("end");
	}

	private void log(List<String> fils, String[] words, Mode mode,
			boolean detail, String mesg) {
		String search = "";
		for (String word : words)
			search += word + " ";
		clog(fils.size()
				+ " files "
				+ mesg
				+ " "
				+ search
				+ " "
				+ (mode == Mode.source ? " in the whole source"
						: " on the same line"));
		if (detail)
			for (String fil : fils)
				clog(fil);
	}

	private void log(List<String> fils, String word, Mode mode) {
		clog(fils.size()
				+ " files contain "
				+ word
				+ " "
				+ (mode == Mode.source ? " in the whole source"
						: " on the same line"));
	}

	private boolean isNotCopyRighted(String file) {
		return !inHeader(file, "copyright");
	}

	private boolean isEmptyCopyright(String file) {
		String s0 = "<copyright>";
		String s1 = "</copyright>";
		List<String> frags = fragment_(file, s0, s1);
		boolean result = frags.size() == 2 && frags.get(0).endsWith(s0)
				&& frags.get(1).endsWith(s1);
		return result;
	}

	private boolean isIn(String[] a, String b) {
		if (a == null)
			return false;
		for (String c : a)
			if (c.startsWith(b))
				return true;
		return false;
	}

	private List<String> insertEplLicenseRemainings(String folder,
			List<String> license) {

		clog("insertEplLicenseRemainings");
		List<String> results = new ArrayList<String>();
		List<String> remains = getRemainings();
		clog("remains: " + remains.size());
		for (String remain : remains) {
			String pak = getPackage(remain);
			if (pak == null)
				clog("not in a source folder :" + remain);

			Source source = findSource(remain);
			if (source != null && !source.isMark()) {
				if (pak != null && !isIn(EXCLUDE_PACKAGES_2, pak)) {
					boolean notcopyr = isNotCopyRighted(remain);

					if (notcopyr) {
						clog("file " + remain + " not Copyrighted");
						insert_(remain, targetFileName(remain, folder), license);
						results.add(remain);
					}
				}
			}
		}
		clog("end");
		return results;
	}

	private List<String> getRemainings() {
		List<String> result = new ArrayList<String>();
		List<String> kwds = new ArrayList<String>();
		String[] kw = new String[1];
		kw[0] = "opyright";
		kwds.add(kw[0]);
		List<Project> prjs = configuration.getProjects();
		for (Project project : prjs) {
			List<Java> src = project.getSources();
			for (Java source : src) {
				if (!source.isHandled() && !source.isMark()) {
					try {
						if (!containsPerSource(source.getAbsolutePath(), kwds)) {
							result.add(source.getAbsolutePath());
							clog("________" + source.getAbsolutePath());
						}
					} catch (IOException e) {
						throw new RuntimeException(e.toString());
					}
				}
			}
		}
		return result;
	}

	private List<String> insertEplLicenseIfNotCopyrighted(String folder,
			String[] excludedPakages, String[] srcs, String[] trgs,
			List<String> license) {

		clog("insertEplLicense");
		List<String> results = new ArrayList<String>();
		List<String> toCopyrights = getSources(srcs, Mode.source, false);
		for (String toCopyright : toCopyrights) {
			String pak = getPackage(toCopyright);
			if (pak == null)
				clog("not in a source folder :" + toCopyright);

			Source source = findSource(toCopyright);
			if (source != null && !source.isMark()) {
				if (pak != null && !isIn(excludedPakages, pak)) {
					boolean notcopyr = isNotCopyRighted(toCopyright);
					if (notcopyr) {
						clog("file " + toCopyright + " not Copyrighted:");
						insert_(toCopyright,
								targetFileName(toCopyright, folder), license);
						results.add(toCopyright);
					}
				}
			}
		}
		clog("end");
		return results;
	}

	private List<String> handleEmptyCopyrightsSrc(String folder,
			String[] excludedPakages, List<String> newText) {
		clog("handleEmptyCopyrightsSrc");
		List<String> results = new ArrayList<String>();
		String[] words = { "<copyright>", "</copyright>", "* $Id$", "/**" };
		List<String> copyrighteds = getSources(words, Mode.source, false);
		for (String copyrighted : copyrighteds) {
			String pak = getPackage(copyrighted);
			if (pak == null)
				clog("not in a source folder :" + copyrighted);
			if (pak != null && !isIn(excludedPakages, pak)) {
				Source source = findSource(copyrighted);
				if (source != null && !source.isMark()) {
					boolean emptycopyr = isEmptyCopyright(copyrighted);

					if (emptycopyr) {
						clog("file " + copyrighted + " emptyCopyright");
						replace(copyrighted,
								targetFileName(copyrighted, folder),
								interval(copyrighted, "<copyright>", "*/"),
								newText);
						results.add(copyrighted);
					}
				}

			}

		}
		clog("end");
		return results;
	}

	private void snippet(String folder) {

		List<String> javs = new ArrayList<String>();
		List<String> projs = getProjects();

		for (String prj : projs) {
			javs.addAll(getJavaFilesInProject3_(prj));
		}

		clog(projs.size() + " projects, " + javs.size() + " java files");

		String[] words = { "<copyright>", "</copyright>", "* $Id$", "/**" };

		List<String> newText = new ArrayList<String>();
		newText.add(" * <copyright>");
		newText.add(" * foo");
		newText.add(" * bar baz");
		newText.add(" * </copyright>");

		// boolean bysource = SOURCE;
		List<String> copyrighteds = getSources(words, Mode.source, false);

		for (String copyrighted : copyrighteds) {
			boolean emptycopyr = isEmptyCopyright(copyrighted);

			if (!emptycopyr) {

				clog("file " + copyrighted + " emptyCopyright");

				String[] rexamp = { "<copyright>", "xxx", "</copyright>", "xxx" };
				String[][] trafo = getTransformation2_(rexamp);

				List<String> frags = fragment_(copyrighted, "<copyright>",
						"</copyright>");
				for (String frag : frags) {
					clog(frag);
				}

				int[] lims = interval(copyrighted, "<copyright>",
						"</copyright>");

				String trgfileName = targetFileName(copyrighted, folder);
				replace(copyrighted, trgfileName, lims, newText);

			}

		}

		String g = "package org.eclipse.gmf";
		List<String> gmfs = go(g, false);

		List<String> excl = copyrighteds;

		for (String gmf : gmfs) {
			if (!excl.contains(gmf))
				excl.add(gmf);
		}
		String[] r1 = { "//FP", "//tralala" };
		String[][] trafo__ = getTransformation1(r1);
		clog("start");
		for (String jav : javs) {
			if (!excl.contains(jav)) {

				clog(jav);
				clone(jav, folder, trafo__);
			}
		}

		clog("end");
	}

	private List<String> go(String req, boolean detail) {
		String[] r = new String[1];
		r[0] = req;
		List<String> result = getSources(r, Mode.line, detail);
		log(result, req, Mode.line);
		return result;
	}

	private List<String> goNotCopyrighted(Mode mode, boolean detail) {
		List<String> result = new ArrayList<String>();
		List<String> kwds = new ArrayList<String>();
		String[] kw = new String[1];
		kw[0] = "opyright";
		kwds.add(kw[0]);
		try {
			for (String prj : projekts_) {
				List<String> javs = getJavaFilesInProject3_(prj);// getJavaFiles(new
																	// File(WS +
																	// prj));
				for (String jav : javs) {
					if (!containsPerSource(jav, kwds))
						result.add(jav);
				}
				// System.out.println("------------------");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		log(result, kw, mode, detail, "NotCopyrighted");
		return result;
	}

	// package org.eclipse.gmf.codegen
	private List<String> getSources(String[] req, Mode mode, boolean detail) {
		List<String> result = new ArrayList<String>();
		// List<String> prjs_ = getProjects();
		List<String> templs = new ArrayList<String>();
		for (String r : req)
			templs.add(r);
		try {
			for (String prj : projekts_) {
				List<String> javs = getJavaFilesInProject3_(prj);// getJavaFiles(new
																	// File(WS +
																	// prj));
				for (String jav : javs) {
					boolean contains = mode == Mode.line ? containsPerLine(jav,
							templs) : containsPerSource(jav, templs);
					if (contains) {
						result.add(jav);
					}
				}
				// System.out.println("------------------");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		log(result, req, mode, detail, (mode == Mode.line ? "containsPerLine"
				: "containsPerSource"));
		return result;
	}

	private Project findProject(String prj) {
		for (Project cproject : configuration.getProjects())
			if (cproject.getAbsolutePath().equals(prj))
				return cproject;
		return null;
	}

	private Source findSource(String srcName) {
		for (Project cproject : configuration.getProjects())
			for (Source src : cproject.getSources())
				if (src.getAbsolutePath().equals(srcName))
					return src;
		return null;
	}

	private List<String> getJavaFilesInProject1orig(String prj) {
		List<String> result = projects.get(prj);
		if (result != null)
			return result;
		else {
			result = getFiles(new File(getWorkspace() + prj), JAVA_EXT);
			projects.put(prj, result);
			return result;
		}
	}

	private List<String> getJavaFilesInProject3_(String prj) {
		List<String> result = new ArrayList<String>();
		List<Java> src = getJavaFilesInProjectb(prj);
		for (Java source : src)
			result.add(source.getAbsolutePath());
		return result;
	}

	private List<Java> getJavaFilesInProjecta_(String prj) {
		Project f = findProject(prj);
		if (f == null) {
			f = addProject(prj);
			configuration.getProjects().add(f);
			List<String> jfiles = getFiles(new File(getWorkspace() + prj),
					JAVA_EXT);
			for (String jfile : jfiles) {

				String pak = getPackage(jfile);
				if (pak != null && !isIn(EXCLUDE_PACKAGES_1_, pak)) {
					Java source = SourcecleanerFactory.eINSTANCE
							.createJava();
					source.setAbsolutePath(jfile);
					f.getSources().add(source);
				}
			}
		}
		return f.getSources();
	}

	private List<Java> getJavaFilesInProjectb(String prj) {
		Project f = findProject(prj);
		if (f == null) {
			f = addProject(prj);
			configuration.getProjects().add(f);
			List<String> jfiles = getFiles(new File(getWorkspace() + prj),
					JAVA_EXT);
			for (String jfile : jfiles) {

				String pak = getPackage(jfile);
				if (pak != null && !isIn(EXCLUDE_PACKAGES_2, pak)) {
					Java source = SourcecleanerFactory.eINSTANCE
							.createJava();
					source.setAbsolutePath(jfile);
					f.getSources().add(source);
				}
			}
		}
		return f.getSources();
	}

	private Project addProject(String name) {
		Project project = SourcecleanerFactory.eINSTANCE.createProject();
		project.setAbsolutePath(name);
		return project;
	}

	private boolean containsPerSource(String jav, List<String> kwds)
			throws IOException {
		File javafile = new File(jav);
		InputStream in = new FileInputStream(javafile);
		List<String> found = new ArrayList<String>();
		try {
			InputStreamReader inR = new InputStreamReader(in);
			BufferedReader buf = new BufferedReader(inR);
			String line;
			while ((line = buf.readLine()) != null)
				for (String kwd : kwds) {
					String k = kwd.toLowerCase();
					if (line.toLowerCase().contains(k))
						if (!found.contains(k))
							found.add(k);
				}
		} finally {
			in.close();
		}
		return found.size() == kwds.size();
	}

	private boolean containsPerLine(String jav, List<String> kwds)
			throws IOException {
		boolean result = false;
		File javafile = new File(jav);
		InputStream in = new FileInputStream(javafile);
		try {
			InputStreamReader inR = new InputStreamReader(in);
			BufferedReader buf = new BufferedReader(inR);
			String line;
			while ((line = buf.readLine()) != null) {
				List<String> found = new ArrayList<String>();
				for (String kwd : kwds) {
					String k = kwd.toLowerCase();
					if (line.toLowerCase().contains(k))
						if (!found.contains(k))
							found.add(k);
				}
				if (found.size() == kwds.size()) {
					result = true;
					break;
				}
			}
		} finally {
			in.close();
		}
		return result;
	}

	private String targetFileName(String srcfileName, String folder) {
		String trgfileName = srcfileName;
		trgfileName = trgfileName
				.substring(0, trgfileName.lastIndexOf(".") + 1);
		trgfileName = trgfileName + "java";
		trgfileName = trgfileName.replaceAll(getRepoFolder(), folder);
		return trgfileName;
	}

	private void clone(String srcfileName, String folder, String[][] trafo) {
		String trgfileName = targetFileName(srcfileName, folder);
		try {
			createFromTemplate(srcfileName, trgfileName, trafo);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private boolean inHeader(String fil, String term) {
		boolean resul = false;
		try {
			File inp = new File(fil);
			InputStream in = new FileInputStream(inp);
			try {
				InputStreamReader inR = new InputStreamReader(in);
				BufferedReader buf = new BufferedReader(inR);
				String line;
				int i = 0;
				while (((line = buf.readLine()) != null)) {
					String result = line.trim();
					boolean found = result.toLowerCase().contains(
							term.toLowerCase());
					if (found) {
						resul = true;
						break;
					}
					if (i == HEADER_LENGTH)
						break;
					i++;
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return resul;
	}

	private List<String> fragment_(String fil, String start, String end) {
		List<String> resul = new ArrayList<String>();
		try {
			File inp = new File(fil);
			InputStream in = new FileInputStream(inp);
			try {
				InputStreamReader inR = new InputStreamReader(in);
				BufferedReader buf = new BufferedReader(inR);
				String line;
				boolean started = false;
				boolean stopped = false;
				while (!stopped && ((line = buf.readLine()) != null)) {
					String result = line.trim();
					result = lineContains_(result, start, end);
					if ((!started && !stopped) && result != null)
						started = true;
					else if (started && result != null) {
						stopped = true;
						resul.add(line);
					}
					if ((started && !stopped))
						resul.add(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return resul;
	}

	private int[] interval(String fil, String start, String end) {
		int[] resul = new int[2];
		try {
			File inp = new File(fil);
			InputStream in = new FileInputStream(inp);
			try {
				InputStreamReader inR = new InputStreamReader(in);
				BufferedReader buf = new BufferedReader(inR);
				String line;
				int i = 0;
				boolean started = false;
				boolean stopped = false;
				while (!stopped && ((line = buf.readLine()) != null)) {
					String result = line.trim();
					result = lineContains_(result, start, end);
					if ((!started && !stopped) && result != null) {
						started = true;
						resul[0] = i;
					} else if (started && result != null) {
						stopped = true;
						resul[1] = i;
					}
					i++;
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return resul;
	}

	private String allLineContains(String input, String[][] replace) {

		StringBuffer buffer = new StringBuffer();
		try {
			File inp = new File(input);
			InputStream in = new FileInputStream(inp);
			try {
				InputStreamReader inR = new InputStreamReader(in);
				BufferedReader buf = new BufferedReader(inR);
				String line_;
				int count = 0;
				while ((line_ = buf.readLine()) != null) {
					String result = line_;
					result = lineContains_(result, replace);
					if (result != null) {
						buffer.append(count + " | | " + line_).append("\n");
					}
					count++;
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	private void createFromTemplate(String templateSheet, String targetSheet,
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
					result = replace__(result, replace);
					if (result == null) {
						result = sv;
						break;
					}
				} while (true);
				buffer.append(result).append("\n");
			}
		} finally {
			in.close();
		}
		String folder = targetSheet.substring(0,
				targetSheet.lastIndexOf(File.separator));
		new File(folder).mkdirs();
		OutputStream os = new FileOutputStream(targetSheet);
		os.write(buffer.toString().getBytes());
		os.close();
	}

	private void insert_(String inf, String outf, List<String> license) {
		try {
			File infile = new File(inf);
			InputStream in = new FileInputStream(infile);
			StringBuffer buffer_ = new StringBuffer();
			try {
				InputStreamReader inR = new InputStreamReader(in);
				BufferedReader buf = new BufferedReader(inR);
				String line;
				for (String lic : license) {
					buffer_.append(lic).append("\n");
				}
				while ((line = buf.readLine()) != null) {
					String result_ = line;
					buffer_.append(result_).append("\n");
				}
			} finally {
				in.close();
			}
			String folder = outf.substring(0, outf.lastIndexOf(File.separator));
			new File(folder).mkdirs();
			OutputStream os = new FileOutputStream(outf);
			os.write(buffer_.toString().getBytes());
			os.close();
		} catch (IOException e) {
			clog("error " + e.toString());
		}
	}

	private void replace(String inf, String outf, int[] lims,
			List<String> replace) {
		try {
			boolean done = false;
			File infile = new File(inf);
			InputStream in = new FileInputStream(infile);
			StringBuffer buffer_ = new StringBuffer();
			try {
				InputStreamReader inR = new InputStreamReader(in);
				BufferedReader buf = new BufferedReader(inR);
				String line;
				int i = 0;
				while ((line = buf.readLine()) != null) {
					String result_ = line;
					if (i < lims[0] || i > lims[1])
						buffer_.append(result_).append("\n");
					else if (!done) {
						done = true;
						for (String replac : replace) {
							buffer_.append(replac).append("\n");
						}
					}
					i++;
				}
			} finally {
				in.close();
			}
			String folder = outf.substring(0, outf.lastIndexOf(File.separator));
			new File(folder).mkdirs();
			OutputStream os = new FileOutputStream(outf);
			os.write(buffer_.toString().getBytes());
			os.close();
		} catch (IOException e) {
			clog("error " + e.toString());
		}
	}

	private String replace__(String line, String[][] replace) {
		String b = null;
		for (int i = 0; i < replace.length; i++) {
			if (line.contains(replace[i][0])) {
				b = line.replaceAll(replace[i][0], replace[i][1]);
				break;
			}
		}
		return b;
	}

	private String lineContains_(String line, String[][] replace) {
		String b = null;
		for (int i = 0; i < replace.length; i++) {
			if (line.contains(replace[i][0])) {
				b = line;
				break;
			}
		}
		return b;
	}

	private String lineContains_(String line, String start, String end) {
		String result = null;
		String[] r = new String[2];
		r[0] = start;
		r[1] = end;
		for (int i = 0; i < r.length; i++) {
			if (line.contains(r[i])) {
				result = line;
				break;
			}
		}
		return result;
	}

	private List<String> getProjects() {
		List<String> projects = new ArrayList<String>();
		File ws = new File(getWorkspace());
		File[] files = ws.listFiles();
		for (int i = 0; i < files.length; i++)
			if (files[i].isDirectory()) {
				File[] subfiles = files[i].listFiles();
				if (subfiles != null)
					for (File file : subfiles) {
						// System.out.println(file.getAbsolutePath());
						if (file.getName().equals(".project")) {
							projects.add(files[i].getName());
							break;
						}
					}
			}
		return projects;
	}

	private String[][] getTransformation2_(String[] rexamp) {
		String[][] rs_ = new String[2][rexamp.length / 2];
		for (int j = 0; j < rexamp.length; j += 2) {
			String[] x = new String[2];
			x[0] = rexamp[j];
			x[1] = rexamp[j + 1];
			rs_[j / 2] = x;
		}
		if (LOG) {
			int i = 0;
			for (String[] r : rs_) {
				clog("gt2 " + rs_[i][0] + " -> " + rs_[i][1]);
				i++;
			}
		}
		return rs_;
	}

	private String[][] getTransformation1(String[] r0) {
		String[][] rs = new String[1][2];
		rs[0] = r0;
		if (LOG) {
			int i = 0;
			for (String[] r : rs) {
				clog("replacing " + rs[i][0] + " with " + rs[i][1]);
				i++;
			}
		}
		return rs;
	}

	private static void clog(String m) {
		if (LOG)
			System.out.println(m);
	}

	private List<String> getFiles(File fil, final String extension) {
		List<String> javas = new ArrayList<String>();
		if (fil.isDirectory()) {
			File[] files = fil.listFiles();
			for (int i = 0; i < files.length; i++)
				if (files[i].isDirectory()) {
					File[] subfiles = files[i].listFiles();
					if (subfiles != null)
						for (File file : subfiles)
							javas.addAll(getFiles(file, extension));
				} else if (files[i].getAbsolutePath().endsWith(extension))
					javas.add(files[i].getAbsolutePath());
		} else if (fil.getAbsolutePath().endsWith(extension))
			javas.add(fil.getAbsolutePath());
		return javas;
	}

	private static void snippet() {
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet
				.getResourceFactoryRegistry()
				.getExtensionToFactoryMap()
				.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
						new XMIResourceFactoryImpl());
		resourceSet.getPackageRegistry().put(SourcecleanerPackage.eNS_URI,
				SourcecleanerPackage.eINSTANCE);
		URI uri = URI.createFileURI(new File("here.sourcecleaner")
				.getAbsolutePath());
		Configuration configuration = SourcecleanerFactory.eINSTANCE.createConfiguration();
		try {
			save(resourceSet, uri, configuration);

			Configuration reloaded = load(resourceSet, uri);

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

}
