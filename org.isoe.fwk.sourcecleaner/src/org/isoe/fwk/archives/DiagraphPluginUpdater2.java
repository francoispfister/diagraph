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
import java.util.Collection;
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
import org.isoe.mbse.sourcecleaner.Manifest;
import org.isoe.mbse.sourcecleaner.Extension;
import org.isoe.mbse.sourcecleaner.Plugin;
import org.isoe.mbse.sourcecleaner.Project;
import org.isoe.mbse.sourcecleaner.Source;
import org.isoe.mbse.sourcecleaner.SourcecleanerFactory;
import org.isoe.mbse.sourcecleaner.SourcecleanerPackage;

/**
 * @author Pfister
 *
 */
public class DiagraphPluginUpdater2 {
	// private static final boolean LINE = true;
	// private static final boolean SOURCE = false;
	private enum Mode {
		line, source
	};


	private List<String> javaSrcs;
	private List<String> manifests;
	private List<String> plugins;
	private List<String> projekts;
	private Map<String, List<String>> projects;
	private ResourceSet resourceSet;
	private URI cleanerUri;
	private static int target = 1;
	private Configuration configuration;
	private String[] copyrightedOrPached;
	private String[] copyrightedOrPachedOrExcluded;
	private String[] copyrightedOrExcluded;
	private static final boolean LOG = true;
	private static final boolean HANDLE_COPYRIGHTS = false;

	// 1.0.1.201405231112

	private static final String USER_FOLDER_ = "C:\\Users\\Pfister";
	private static final String BASE_FOLDER_ = "git-diagraph-local";
	private static final String REPO_FOLDER_ = "git-diagraph-local";

	private static final String USER_FOLDER = "C:\\workspaces";

	private static final String TARGET_ = "ter";
	private static final int HEADER_LENGTH = 10;

	private static final String PRJ_FOLDER = "v130926";
	private static final int PRJ_FOLDER_LEN = PRJ_FOLDER.length();
	static final String JAVA_EXT_ = ".java";
	static final String EXTENSION_NAME = "plugin.xml";
	static final String BUILD_NAME = "build.properties";
	static final String MANIFEST_NAME = "MANIFEST.MF";

	private static final String TARGET_2 = "ter2";
	private static final String REPO_FOLDER_2 = TARGET_;

	private static final String INCOMPLETE_VERSION_A_ = "1.0.0";
	private static final String INCOMPLETE_VERSION_B_ = "1.0.1";

	private static final String OLD_VERSION_MAJ_old = "1.0.1.20140428";
	private static final String OLD_VERSION_MIN_old = "1111";

	private static final String OLD_VERSION_MAJ = "1.0.1.20140523";
	private static final String OLD_VERSION_MIN = "1112";

	private static final String VERSION_MAJ = "1.0.2.20140709";
	private static final String VERSION_MIN = "1051";

	// static final String[] EXCLUDED_PACKAGES_ = { "org.eclipse.gmf.codegen" };
	static final String[] EXCLUDE_PACKAGES_nu = { "org.eclipse.gmf.codegen",
			"org.eclipse.gmf", "org.isoe.fwk.utils.borrowed" };
	static final String[] COPYRIGHTED____ = { "org.eclipse.gmf.codegen",
			"org.eclipse.gmf", "org.isoe.fwk.utils.borrowed" };

	static final String[] COPYRIGHTED = { "org.isoe.fwk.utils.borrowed" };

	// EXCLUDE_PRJ COPYRIGHTED

	static final String[] PATCHED = { "org.eclipse.gmf.codegen",
	"org.eclipse.gmf" };
	static final String[] EXCLUDE_PRJ = {
	"abc.trafo", "Eclipse Monkey Examples", "emfatix", "figures_these",
			"GroovyMonkeyScripts", "isoe.mail.rcp", "javadiff",
			"my.example.editor", "my.expression.evaluator", "my.testswt",
			"myscripts", "simpleJavaEditor", "styled", "svn",

	};

	static final String[] EXCLUDE_PRJ_PREFIX_ = { "lang.m2", "Math", "misc",
			"org.eclipse.emf", "org.eclipse.swt", "org.isoe.mbse",
			"_megamodel", "org.eclipse.gmf.codegen", "org.eclipse.gmf" };
	// "org.isoe.fwk.utils.borrowed" };

	static final String[] TO_MARK = { "Falleri", "acceleo", "Obeo",
			"mia-software" };
	// "iaml","borrowed"



	private String[] getCopyrightedOrPatched_() {
		if (copyrightedOrPached == null) {
			copyrightedOrPached = new String[COPYRIGHTED.length
					+ PATCHED.length];
			int i = 0;
			for (String cop : COPYRIGHTED)
				copyrightedOrPached[i++] = cop;
			for (String pat : PATCHED)
				copyrightedOrPached[i++] = pat;
		}
		return copyrightedOrPached;
	}

	private String[] getCopyrightedOrExcluded_() {
		if (copyrightedOrExcluded == null) {
			copyrightedOrExcluded = new String[COPYRIGHTED.length
					+ EXCLUDE_PRJ.length];
			int i = 0;
			for (String cop : COPYRIGHTED)
				copyrightedOrExcluded[i++] = cop;
			for (String pat : EXCLUDE_PRJ)
				copyrightedOrExcluded[i++] = pat;
		}
		return copyrightedOrExcluded;
	}


	private String[] getCopyrightedOrPatchedOrExcluded_() {
		if (copyrightedOrPachedOrExcluded == null) {
			copyrightedOrPachedOrExcluded = new String[COPYRIGHTED.length
					+ PATCHED.length + EXCLUDE_PRJ.length];
			int i = 0;
			for (String cop : COPYRIGHTED)
				copyrightedOrPachedOrExcluded[i++] = cop;
			for (String pat : PATCHED)
				copyrightedOrPachedOrExcluded[i++] = pat;
			for (String pat : EXCLUDE_PRJ)
				copyrightedOrPachedOrExcluded[i++] = pat;
		}
		return copyrightedOrPachedOrExcluded;
	}



	private DiagraphPluginUpdater2() {
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



	private boolean startsWith(String[] lizt, String what) {
		for (String li : lizt)
			if (li.startsWith(what))
				return true;
		return false;
	}

	private boolean contains(String[] lizt, String what) {
		for (String li : lizt)
			if (li.equals(what))
				return true;
		return false;
	}

	private void mark(String excl, String[]excluded) {
		System.out.println("______mark  " + excl);
		List<String> excls = go(excl, false,excluded);
		for (String sr : excls) {
			Source source = findSource(sr);
			if (source != null) {
				source.setMark(true);
				System.out.println(source.getAbsolutePath());
			}
		}
	}

	private void copyProjects() throws IOException {
		for (String prj : projekts) {
			File sprjfile = new File(getWorkspace1() + prj + "\\.project");
			String targpath = getWorkspace2() + prj;
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

	private void updateSourceFiles_(List<String> prjs, String ws,
			String importToReplace, String newImport, String comment) {
		for (String javasrc : javaSrcs) {
			File javafile = new File(javasrc);
			if (javafile.exists()) // FP140428
				updateJavaFileReplace(javasrc, javafile, javafile,
						importToReplace, newImport, comment);
		}
	}

	private void doWithManifestFiles(List<String> prjs, String ws, String old,
			String niu) {
		for (String manifestsrc : manifests) {
			File manifestFile = new File(manifestsrc);
			if (manifestFile.exists())
				updateManifestFile_(manifestsrc, manifestFile, old, niu);
		}
	}

	private void doWithExtensionFiles(List<String> prjs, String ws, String old,
			String niu) {
		for (String pluginsrc : plugins) {
			File pluginFile = new File(pluginsrc);
			if (pluginFile.exists())
				updatePluginFile_(pluginsrc, pluginFile, old, niu);
		}
	}

	private void updateMetaFiles(List<String> prjs, String ws) {
		for (String prj : prjs) {

			File manifestfile = new File(getWorkspace1() + prj
					+ "\\META-INF\\MANIFEST.MF");
			if (manifestfile.exists() && prj.startsWith("org.isoe")) { // FP140428
																		// prj.startsWith("org.isoe")
				String targpath = ws + prj;
				// File targdir = new File(targpath);
				File targmdir = new File(targpath + "\\META-INF");
				if (!targmdir.exists())
					new File(targpath + "\\META-INF").mkdirs();

				File tmanifestfile = new File(targpath
						+ "\\META-INF\\MANIFEST.MF");

				if (LOG)
					clog("---------- handling project " + prj + " target= "
							+ tmanifestfile.getAbsolutePath());

				String mftext = readMetaFile(prj, manifestfile);
				updateMetaFile_tag(prj, manifestfile, tmanifestfile);
			}
		}
		System.out.println(corr_);
	}

	private void updateMetaFiles(List<String> prjs, String ws, String find,
			String add, boolean remove) throws IOException {
		for (String prj : prjs) {

			File manifestfile = new File(getWorkspace1() + prj
					+ "\\META-INF\\MANIFEST.MF");
			if (manifestfile.exists() && prj.startsWith("org.isoe")) { // FP140428
																		// prj.startsWith("org.isoe")
				String targpath = ws + prj;
				// File targdir = new File(targpath);
				File targmdir = new File(targpath + "\\META-INF");
				if (!targmdir.exists())
					new File(targpath + "\\META-INF").mkdirs();

				File tmanifestfile = new File(targpath
						+ "\\META-INF\\MANIFEST.MF");

				if (LOG)
					clog("---------- handling project " + prj + " target= "
							+ tmanifestfile.getAbsolutePath());

				String mftext = readMetaFile(prj, manifestfile);
				updateMetaFile_tag(prj, manifestfile, tmanifestfile);

				if (add != null && !mftext.contains(add))
					updateMetaFileReplace(prj, tmanifestfile, tmanifestfile,
							find, add, remove);
			}
		}
		System.out.println(corr_);
	}

	private void updateFeatures_nu_(String prj, String ws) throws IOException {

		File featurefile = new File(getWorkspace1() + prj + "\\feature.xml");
		if (featurefile.exists()) {
			String targpath = ws + prj;

			File tfeatfile = new File(targpath + "\\feature.xml");
			updateFeatureFile_nu_(featurefile, tfeatfile);
		}

	}

	private void updateFeatureFile_nu_(File infile, File outfile) {
		boolean check = false;
		try {
			InputStream in = new FileInputStream(infile);
			StringBuffer buffer = new StringBuffer();
			try {
				InputStreamReader inR = new InputStreamReader(in);
				BufferedReader buf = new BufferedReader(inR);
				String line;
				while ((line = buf.readLine()) != null) {

					String linetrim = line.trim();

					if (linetrim.equals("version=\"1.0.1\"")) {
						check = true;
					}

					if (linetrim.equals("version=\"0.0.0\"")) {
						line = line.replace("0.0.0", VERSION_MAJ);
					}
					/*
					 * if (linetrim.equals("Bundle-Version: 1.0.0.qualifier")){
					 * line = line.replace("1.0.0.qualifier",
					 * VERSION_MAJ+VERSION_MIN); }
					 */

					if (!line.contains(";bundle-version")) {
						check = true;
					}

					if (linetrim.equals("version=\"" + INCOMPLETE_VERSION_A_
							+ "\"")) {
						check = true;
					}
					if (linetrim.equals("version=\"" + INCOMPLETE_VERSION_B_
							+ "\"")) {
						check = true;
					}

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

	String corr_ = "";

	private String readMetaFile(String prj, File infile) {// C:\workspaces\v130926\isoe.mail.rcp\META-INF\MANIFEST.MF
		String result = "";
		try {
			InputStream in = new FileInputStream(infile);
			StringBuffer buffer = new StringBuffer();
			try {
				InputStreamReader inR = new InputStreamReader(in);
				BufferedReader buf = new BufferedReader(inR);
				String line;
				boolean stateRequireBundle = false;
				while ((line = buf.readLine()) != null) {
					result += line + "\n";
				}
			} finally {
				in.close();
			}
		} catch (IOException e) {
			System.err.println("error " + e.toString());
		}
		return result;
	}

	private void updateMetaFile_tag(String prj, File infile, File outfile) {// C:\workspaces\v130926\isoe.mail.rcp\META-INF\MANIFEST.MF
		boolean check;
		// if (prj.startsWith("org.isoe")) {
		try {
			InputStream in = new FileInputStream(infile);
			StringBuffer buffer = new StringBuffer();
			try {
				InputStreamReader inR = new InputStreamReader(in);
				BufferedReader buf = new BufferedReader(inR);
				String line;
				boolean stateRequireBundle = false;
				while ((line = buf.readLine()) != null) {

					if (line.startsWith("Export-Package"))
						check = true;

					if (line.startsWith("Require-Bundle:"))
						stateRequireBundle = true;

					if (line.startsWith("Manifest-Version:")
							|| line.startsWith("Bundle-ManifestVersion:")
							|| line.startsWith("Bundle-Name:")
							// || line.startsWith("Bundle-ClassPath:")
							// || line.startsWith("Bundle-Vendor:")
							|| line.startsWith("Bundle-SymbolicName:")
							|| line.startsWith("Bundle-Version:")
							|| line.startsWith("Bundle-Vendor:")
							|| line.startsWith("Bundle-RequiredExecutionEnvironment:")
							|| line.startsWith("Export-Package:")
							|| line.startsWith("Bundle-Activator:"))
						stateRequireBundle = false;

					String linetrim = line.trim();

					if (line.equals("Bundle-RequiredExecutionEnvironment: JavaSE-1.7"))
						line = "Bundle-RequiredExecutionEnvironment: JavaSE-1.6";

					if (line.startsWith("Bundle-RequiredExecutionEnvironment")
							&& !line.equals("Bundle-RequiredExecutionEnvironment: JavaSE-1.6")
							&& !line.equals("Bundle-RequiredExecutionEnvironment: J2SE-1.5"))
						check = true;

					if (line.startsWith("Bundle-Version:")
							&& !line.equals("Bundle-Version: 1.0.0.qualifier")

					)
						check = true;

					if (linetrim.startsWith("org.isoe"))
						check = true;

					if (stateRequireBundle)
						if (linetrim
								.startsWith("org.isoe.extensionpoint.diagraph")
								|| linetrim
										.startsWith("Require-Bundle: org.isoe.extensionpoint.diagraph")) {// org.isoe.extensionpoint.diagraph;

							clog("====toreplace= " + prj + " for " + linetrim);
						}

					if (line.equals("Bundle-Version: " + INCOMPLETE_VERSION_A_))
						check = true;
					if (line.equals("Bundle-Version: " + INCOMPLETE_VERSION_B_))
						check = true;

					if (line.equals("Bundle-Version: " + OLD_VERSION_MAJ
							+ OLD_VERSION_MIN))
						line = "Bundle-Version: " + VERSION_MAJ + VERSION_MIN;

					if (stateRequireBundle)
						if (linetrim.startsWith("org.isoe.")
								|| linetrim
										.startsWith("Require-Bundle: org.isoe.")) {// org.isoe.extensionpoint.diagraph;
							if (!linetrim.contains(";bundle-version"))
								clog("====noversion in " + prj + " for "
										+ linetrim);
						}

					if (linetrim.startsWith("org.isoe.")
							|| linetrim.startsWith("Require-Bundle: org.isoe.")) {
						if ((linetrim.contains(";bundle-version=\""
								+ INCOMPLETE_VERSION_A_ + "\""))
								|| (linetrim.contains(";bundle-version=\""
										+ INCOMPLETE_VERSION_B_ + "\""))

						)

						{
							check = true;
						}

					}

					if (line.equals("Bundle-Version: " + "1.0.0.qualifier"))
						line = "Bundle-Version: " + VERSION_MAJ + VERSION_MIN;

					/*
					 * org.isoe.extensionpoint.transformation;bundle-version=
					 * "1.0.1.20140317" Bundle-Vendor: LGi2P Isoe
					 * Bundle-Version: 1.0.1.201403170157 Bundle-Version:
					 * 1.0.0.qualifier
					 */

					if (linetrim.contains(";bundle-version=\"1.0.1\"")) {

						corr_ += "\n" + prj + " - " + linetrim;

					}

					// TODO use regexps
					if (stateRequireBundle
							&& (linetrim.startsWith("org.isoe.") || linetrim
									.startsWith("Require-Bundle: org.isoe."))) {

						String l = caseUpdate_(line); // FP140507
						line = l == null ? line : l;

						l = caseIncomplete(line);
						line = l == null ? line : l;

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
		// }
	}

	private void updateManifestFile_(String src, File File, String old,
			String niu) {
		// TODO Auto-generated method stub

	}

	private void updatePluginFile_(String src, File File, String old, String niu) {
		// TODO Auto-generated method stub

	}

	private void updateJavaFileReplace(String prj, File infile, File outfile,
			String importToReplace, String newImport, String comment) {

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

	private void updateMetaFileReplace(String prj, File infile, File outfile_,
			String find, String add, boolean remove) {// C:\workspaces\v130926\isoe.mail.rcp\META-INF\MANIFEST.MF
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

	private String caseIncomplete(String line) {
		if (line.contains("x-internal:=true"))
			return null;
		String linetrim = line.trim();
		if (!linetrim.contains(";bundle-version")) {
			if (line.endsWith(",")) {
				line = line.substring(0, line.length() - 1);
				line += ";bundle-version=\"" + VERSION_MAJ + "\"";
				line += ",";
				return line;
			} else {
				line += ";bundle-version=\"" + VERSION_MAJ + "\"";
				return line;
			}
		}
		return null;
	}

	private String caseUpdate_(String line) {
		String linetrim = line.trim();
		if (linetrim.contains(";bundle-version=\"" + OLD_VERSION_MAJ + "\"")
				|| linetrim.contains(";bundle-version=\"1.0.0\"")
				|| linetrim.contains(";bundle-version=\"1.0.1\"")) {
			String st = line.substring(0, line.indexOf(";bundle-version"));
			String t = st + ";bundle-version=\"" + VERSION_MAJ + "\"";
			if (line.endsWith(","))
				t += ",";
			line = t;
			return line;
		}
		return null;
	}

	public static void main(String[] args) {
		execWithoutReplace();
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
		DiagraphPluginUpdater2 c = new DiagraphPluginUpdater2();
		String[] excluded = c.getCopyrightedOrExcluded_();

		try {
			c.exec_(find, add, importTofind, importToReplace, comment,
					removeYes,excluded,false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
	}

	public static void execWithoutReplace() {
		DiagraphPluginUpdater2 c = new DiagraphPluginUpdater2();
		String[] excluded = c.getCopyrightedOrExcluded_();
		try {
			c.exec(excluded,false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
	}




	private void exec_(String initialDependancy, String newDependancy,
			String importToReplace, String newImport, String comment,
			boolean remove,String[] excluded,boolean mark) throws IOException {
		// clons("ter");
		target = 1;
		initFiles(excluded);
        if (mark)
		  markFiles(excluded);


		clog(projekts.size() + " projects, " + javaSrcs.size() + " java files");

		// if (false)
		for (String javasrc : javaSrcs)
			System.out.println(javasrc);// C:\workspaces\v130926\lirmm.dotutils\src\lirmm\dotutils\AbstractExportDotAction.java
		for (String manifest : manifests)
			System.out.println(manifest);
		for (String plugin : plugins)
			System.out.println(plugin);


		copyProjects();
		String ws = getWorkspace1();// getWorkspace2();
		List<String> prjs = projekts;

		updateMetaFiles(prjs, ws, initialDependancy, newDependancy, remove);

		updateSourceFiles_(prjs, ws, importToReplace, newImport, comment);

		// updateFeatures("org.isoe.diagraph.features", ws);
		if (HANDLE_COPYRIGHTS)
			handleCopyrights(excluded);
	}





	private void initFiles(String[] excluded) {

		projects = new HashMap<String, List<String>>();
		configuration = SourcecleanerFactory.eINSTANCE.createConfiguration();

		javaSrcs = new ArrayList<String>();
		manifests = new ArrayList<String>();
		plugins = new ArrayList<String>();
		projekts = getAllProjects(EXCLUDE_PRJ,EXCLUDE_PRJ_PREFIX_);

		// for (String excluded : EXCLUDE_PACKAGES_2___)
		// projekts.remove(excluded);
/*
		for (String exclprj : EXCLUDE_PRJ)
			projekts.remove(exclprj);

		List<String> temps = new ArrayList<String>();
		for (String excludedpref : EXCLUDE_PRJ_PREFIX_)
			for (String p : projekts)
				if (p.startsWith(excludedpref))
					temps.add(p);

		for (String temp : temps)
			projekts.remove(temp);
*/
		System.out.println("_____projects");
		for (String prj : projekts) {
			// System.out.println("java sources for:" + prj);
			// if (!contains(excludeprj,prj) && !startsWith(excludePrefix,prj)){
			System.out.println(prj);
			javaSrcs.addAll(getJavaSourcesForProjectAsString(prj,excluded));
			String mf = getManifestFileForProject_(prj);
			if (mf != null)
				manifests.add(mf);

			boolean tb = true;
			if (prj.equals("org.isoe.diagraph.diastyle"))
				  tb = true;

			String pl = getPluginPath(prj);
			if (pl != null)
				plugins.add(pl);
		}


	}

//	String[] excluded=getCopyrightedOrExcluded();
	private void exec(String[] excluded, boolean mark) throws IOException {
		// clons("ter");
		target = 1;
		initFiles(excluded);

	    if (mark)
			  markFiles(excluded);


		clog(projekts.size() + " projects, " + javaSrcs.size() + " java files");

		// if (false)
		for (String javasrc : javaSrcs)
			System.out.println(javasrc);// C:\workspaces\v130926\lirmm.dotutils\src\lirmm\dotutils\AbstractExportDotAction.java
		for (String manifest : manifests)
			System.out.println(manifest);
		for (String plugin : plugins)
			System.out.println(plugin);


		copyProjects();
		String ws = getWorkspace1();// getWorkspace2();
		List<String> prjs = projekts;
		updateMetaFiles(prjs, ws);
		// updateFeatures("org.isoe.diagraph.features", ws);
		if (HANDLE_COPYRIGHTS)
			handleCopyrights(excluded);
	}

	private void markFiles(String[] excluded) {
		for (String tomark : TO_MARK)
			mark(tomark,excluded);
    }

	private void handleCopyrights(String[] excluded) {

		insertNastovPfisterEplLicenseIfNotCopyrighted(excluded);
		insertPfisterEplLicenseIfNotCopyrighted(excluded);
		insertNastovEplLicenseIfNotCopyrighted(excluded);
		changeEmptyCopyrightToEplLicense(excluded);
		insertEplLicenseRemainings();
		getNotCopyrighted2();
	}

	private List<String> insertNastovEplLicenseIfNotCopyrighted(String[] excluded) {

		String[] srcs = { "nastov" };
		String[] targs = { "Blazo Nastov" };
		// List<String> r = getSources(srcs, Mode.source, true);

		List<String> result = insertEplLicenseIfNotCopyrighted(srcs, targs,excluded);
		register(result, targs, "EplLicenseIfNotCopyrighted");
		return result;

	}
////	String[] excluded=getCopyrightedOrExcluded();
	private List<String> insertPfisterEplLicenseIfNotCopyrighted(String[] excluded) {
		//String[] excluded=getCopyrightedOrExcluded();
		String[] srcs = { "pfister" };
		String[] targs = { "Francois Pfister" };
		// List<String> r = getSources(srcs, Mode.source, true);
		List<String> result = insertEplLicenseIfNotCopyrighted(srcs, targs,excluded);
		register(result, targs, "EplLicenseIfNotCopyrighted");
		return result;
	}
	//getCopyrightedOrExcluded_()
	private List<String> getNotCopyrighted1(String[] excluded) {
		List<String> r = goNotCopyrightedOrExcluded(Mode.source, true,excluded);
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


////getCopyrightedOrExcluded_()
	private List<String> insertNastovPfisterEplLicenseIfNotCopyrighted(String[]excluded) {
		String[] srcs = { "bnastov", "pfister" };
		String[] targs = { "Blazo Nastov", "Francois Pfister" };
		// List<String> r = getSources(srcs, Mode.source, true);
		List<String> result = insertEplLicenseIfNotCopyrighted(srcs, targs,excluded);
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
////getCopyrightedOrExcluded_()
	private List<String> insertEplLicenseIfNotCopyrighted(String[] srcs,
			String[] trgs,String[] excluded) {
		String[] excludedPakages = null;// { "org.eclipse.gmf.codegen" };//
										// ,"matcher.mtbe","mtbe.fr"};
		List<String> epl = getEplLicense(trgs);
		List<String> handled = insertEplLicenseIfNotCopyrighted(getTarget(),
				excludedPakages, srcs, trgs, epl,excluded);
		for (String f : handled) {
			System.out.println(f);
		}
		return handled;
	}

////getCopyrightedOrExcluded_()
	private List<String> changeEmptyCopyrightToEplLicense(String[] excluded) {
		String[] excludedPakages = { "org.eclipse.gmf.codegen" };// ,"matcher.mtbe","mtbe.fr"};
		List<String> epl = getEplLicense();
		epl.add(" */");
		List<String> handled = handleEmptyCopyrightsSrc(getTarget(),
				excludedPakages, epl,excluded);
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

	//getCopyrightedOrExcluded_()
	private void clons_(String folder, String[] excluded) {

		String[] obss = { "copyright", "obeo" };
		List<String> obssss = getSources(obss, Mode.line, false,excluded);

		/*
		 * for (String ob : obssss) { clog_(ob); }
		 */

		String accs = "acceleo";
		List<String> accss = go(accs, false,excluded);

		String iams = "iaml";
		List<String> iamss = go(iams, false,excluded);

		String[] bps = { "borrowed", "pfister" };
		List<String> bpss = getSources(bps, Mode.source, false,excluded);

		String mias = "mia-software";
		List<String> miass = go(mias, false,excluded);

		String[] jrfbnp = { "bnastov", "pfister" };
		List<String> jrfbnps = getSources(jrfbnp, Mode.source, false,excluded);

		String[] jrfbn = { "bnastov" };
		List<String> jrfbns = getSources(jrfbn, Mode.line, false,excluded);

		String jrf1 = "Falleri";
		List<String> jrfs1 = go(jrf1, false,excluded);

		String[] r = { "<copyright>", "</copyright>", "* $Id$", "/**" };
		List<String> gens = getSources(r, Mode.source, false,excluded);

		String g = "package org.eclipse.gmf";
		List<String> gmfs = go(g, false,excluded);
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



	private boolean isInPrefix(String[] a, String b) {
		if (a == null)
			return false;
		for (String c : a)
			if (b.startsWith(c))
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
				if (pak != null && !isIn(getCopyrightedOrPatched_(), pak)) {
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
//getCopyrightedOrExcluded_()
	private List<String> insertEplLicenseIfNotCopyrighted(String folder,
			String[] excludedPakages, String[] srcs, String[] trgs,
			List<String> license, String[]excluded) {

		clog("insertEplLicense");
		List<String> results = new ArrayList<String>();
		List<String> toCopyrights = getSources(srcs, Mode.source, false,excluded);
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

	//getCopyrightedOrExcluded_()
	private List<String> handleEmptyCopyrightsSrc(String folder,
			String[] excludedPakages, List<String> newText, String[]excluded) {
		clog("handleEmptyCopyrightsSrc");
		List<String> results = new ArrayList<String>();
		String[] words = { "<copyright>", "</copyright>", "* $Id$", "/**" };
		List<String> copyrighteds = getSources(words, Mode.source, false,excluded);
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
		List<String> projs = getAllProjects(null,null);

		String[] excluded=getCopyrightedOrExcluded_();

		for (String prj : projs) {
			javs.addAll(getJavaSourcesForProjectAsString(prj,excluded));
		}

		clog(projs.size() + " projects, " + javs.size() + " java files");

		String[] words = { "<copyright>", "</copyright>", "* $Id$", "/**" };

		List<String> newText = new ArrayList<String>();
		newText.add(" * <copyright>");
		newText.add(" * foo");
		newText.add(" * bar baz");
		newText.add(" * </copyright>");

		// boolean bysource = SOURCE;
		List<String> copyrighteds = getSources(words, Mode.source, false,excluded);

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
		List<String> gmfs = go(g, false,excluded);

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

	private List<String> go(String req, boolean detail,String[] excluded) {

		String[] r = new String[1];
		r[0] = req;
		List<String> result = getSources(r, Mode.line, detail,excluded);
		log(result, req, Mode.line);
		return result;
	}
//,getCopyrightedOrExcluded_()
	private List<String> goNotCopyrightedOrExcluded(Mode mode, boolean detail, String[] excluded) {
		List<String> result = new ArrayList<String>();
		List<String> kwds = new ArrayList<String>();
		String[] kw = new String[1];
		kw[0] = "opyright";
		kwds.add(kw[0]);
		try {
			for (String prj : projekts) {
				List<String> javs = getJavaSourcesForProjectAsString(prj,excluded);// getJavaFiles(new
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

	// getCopyrightedOrExcluded_()
	private List<String> getSources(String[] req, Mode mode, boolean detail, String[] excluded) {
		List<String> result = new ArrayList<String>();
		// List<String> prjs_ = getProjects();
		List<String> templs = new ArrayList<String>();
		for (String r : req)
			templs.add(r);
		try {
			for (String prj : projekts) {
				List<String> javs = getJavaSourcesForProjectAsString(prj,excluded);// getJavaFiles(new
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

	private List<String> _nu_getJavaFilesInProject1orig(String prj) {
		List<String> result = projects.get(prj);
		if (result != null)
			return result;
		else {
			result = getFiles(new File(getWorkspace() + prj), JAVA_EXT_, null);
			projects.put(prj, result);
			return result;
		}
	}

	private List<String> getJavaSourcesForProjectAsString(String prj, String[] excluded) {
		List<String> result = new ArrayList<String>();
		List<Java> src = getJavaSourcesForProject(prj,excluded);
		for (Java source : src)
			result.add(source.getAbsolutePath());
		return result;
	}

	private String getPluginPath(String prj) {
		Source src = getPluginFileInProject(prj);
		return src == null ? null : src.getAbsolutePath();
	}

	private String getManifestFileForProject_(String prj) {
		Source src = getManifestFileInProject(prj);
		return src == null ? null : src.getAbsolutePath();
	}


//(getCopyrightedOrExcluded_() getCopyrightedOrPatchedOrExcluded_()
	private List<Java> getJavaSourcesForProject(String prjname, String[] excluded) {
		Project prj = findProject(prjname);
		if (prj == null) {
			prj = addProject(prjname);
			configuration.getProjects().add(prj);
			List<String> jfiles = getFiles(new File(getWorkspace() + prjname),
					JAVA_EXT_, null);
			for (String jfile : jfiles) {
				String pak = getPackage(jfile);
				if (pak != null && !isIn(excluded, pak)) {
					Java source = SourcecleanerFactory.eINSTANCE.createJava();
					source.setAbsolutePath(jfile);
					prj.getSources().add(source);
				}
			}
		}
		return prj.getSources();
	}

	private Plugin getPluginFileInProject(String prj) {
		Project pr = findProject(prj);
		int count = 0;
		List<String> jfiles = getFiles(new File(getWorkspace() + prj),
				EXTENSION_NAME, prj);
		for (String jfile : jfiles) {
			Plugin plugin = SourcecleanerFactory.eINSTANCE.createPlugin();
			plugin.setAbsolutePath(jfile);
			pr.setPlugin(plugin);
			count++;
		}
		if (count > 1)
			throw new RuntimeException(
					"should not happen: more than one plugin file");
		return pr.getPlugin();
	}

	private Manifest getManifestFileInProject(String prj) {

		Project f_ = findProject(prj);
		int count = 0;
		List<String> jfiles = getFiles(new File(getWorkspace() + prj),
				MANIFEST_NAME, "META-INF");
		for (String jfile : jfiles) {
			System.out.println(jfile);
			Manifest manifest = SourcecleanerFactory.eINSTANCE.createManifest();
			manifest.setAbsolutePath(jfile);
			f_.setManifest(manifest);
			count++;
		}


		if (count > 1)
			throw new RuntimeException(
					"should not happen: more than one manifest file");
		return f_.getManifest();
	}

	private Project addProject(String path) {
		Project project = SourcecleanerFactory.eINSTANCE.createProject();
		project.setAbsolutePath(path);
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



	private List<String> getAllProjects(String[] excludeName, String[] excludePrefix) {
		List<String> projects = new ArrayList<String>();
		File ws = new File(getWorkspace());
		File[] files = ws.listFiles();
		for (int i = 0; i < files.length; i++)
			if (files[i].isDirectory()) {
				File[] subfiles = files[i].listFiles();
				if (subfiles != null)
					for (File subFile : subfiles) {
						// System.out.println(file.getName());
						if (subFile.getName().equals(".project")) {

							String prj=files[i].getName();
							if (!isIn(excludeName, prj))
								if (!isInPrefix(excludePrefix, prj))
							   projects.add(prj);
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

	private List<String> getFiles(File fil, String extension, String parent_) {
		boolean tb;
		boolean exact = parent_!=null;
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
					//if (files[i].getAbsolutePath().endsWith(
						//	extension))
					//	tb = true;
					if ((!exact && files[i].getAbsolutePath().endsWith(
							extension))
							|| files[i].getName().equals(extension)){
						String paren=files[i].getParent();
						paren=paren.substring(paren.lastIndexOf(File.separator)+1);
						if (!exact || parent_.equals(paren))
						  filez.add(files[i].getAbsolutePath());
					}
				}
		} else {
			//if (fil.getAbsolutePath().endsWith(
			//		extension))
			//	tb = true;
			if ((!exact && fil.getAbsolutePath().endsWith(extension))
					|| fil.getName().equals(extension)){
				String paren=fil.getParent();
				paren=paren.substring(paren.lastIndexOf(File.separator)+1);
				if (!exact || parent_.equals(paren))
				  filez.add(fil.getAbsolutePath());
			}
		}
		return filez;
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
