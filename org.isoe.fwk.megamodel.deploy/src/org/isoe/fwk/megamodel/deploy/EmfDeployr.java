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
package org.isoe.fwk.megamodel.deploy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.internal.resources.ResourceException;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.codegen.ecore.genmodel.GenJDKLevel;
import org.eclipse.emf.codegen.ecore.genmodel.GenRuntimeVersion;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.swt.widgets.Display;
import org.isoe.diagraph.megamodel.Dsml;
import org.isoe.diagraph.megamodel.Megamodel;
import org.isoe.diagraph.megamodel.MegamodelFactory;
import org.isoe.diagraph.megamodel.MegamodelPackage;
import org.isoe.diagraph.megamodel.Model;
import org.isoe.diagraph.megamodel.NotationDiagram;
import org.isoe.extensionpoint.xmi.IMetamodelRetriever;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.deployer.MegamodelPersistence;
import org.isoe.fwk.deployer.ProjectFactory;
import org.isoe.fwk.megamodel.handler.MegamodelHandler;
import org.isoe.fwk.pathes.PathPreferences;
import org.isoe.fwk.utils.ResourceUtils;
import org.isoe.fwk.utils.workspace.CompileUtil;
import org.isoe.fwk.utils.workspace.RuntimeWorkspaceSetup;
import org.isoe.diagraph.preferences.DiagraphPreferences;

/**
 *
 * @author pfister TODO refactor this , too confused
 *
 */
import org.isoe.diagraph.controler.IDiagraphControler;  //FP140707refactored
public class EmfDeployr {

	private static final boolean LOG = DParams.EmfDeployr_LOG;

	private static final boolean THROW_EXCEPTION = false;

	private static final String M3_DOMAIN = "ecore";
	private static final String M3_DOMAIN_SUFFIX = "." + M3_DOMAIN;

	private String universeName;
	private String modelInUniverseFolder;
	private String universeModel;
	private String megamodelName;
	private String megamodelFolderPlugin;// megamodelFolder2;
	private String megamodelFolderLocal;
	private String modelFolder;
	private String javaVersion;
	protected String modelPath;
	protected String basePackage;
	protected String lastsegment;
	private String instanceFolderInM2;
	private String editPluginSuffix;
	private String editorPluginSuffix;
	private String testPluginSuffix;
	private GenJDKLevel genJdkLevel;
	private GenRuntimeVersion emfLev;
	private String modelName;
	protected EmfHandler emfHandler;
	private CompileUtil compileUtil;
	private final List<String> projectsToCompile; // TODO change the name
	private Megamodel megamodel;
	private IMetamodelRetriever metamodelRetriever;

	public Megamodel getMegamodel() {
		return megamodel;
	}

	public List<String> getProjectsToCompile() {
		return projectsToCompile;
	}

	private IDiagraphControler controler;

	private List<String> files;

	private String teamNameSpace;

	public IDiagraphControler getControler() {
		return controler;
	}

	public EmfDeployr(IDiagraphControler controler,
			IMetamodelRetriever metamodelRetriever, List<String> projects,
			boolean deployInstancesOnly, String universeName,
			String universeFolder, String universeModel, String megamodelName,
			File file, String modelFolder, String javaVersion,
			String instanceFolderInM2, String editPluginSuffix,
			String editorPluginSuffix, String testPluginSuffix,
			GenJDKLevel genJdkLevel, GenRuntimeVersion emfLev, boolean dummy) {

		this.teamNameSpace = org.isoe.diagraph.preferences.DiagraphPreferences
				.getTeamNamespace();

		this.controler = controler;
		this.metamodelRetriever = metamodelRetriever;
		projectsToCompile = projects;
		this.universeName = universeName;
		this.modelInUniverseFolder = universeFolder;// DiagraphPreferences.getStringPreference(DParams.KEY_UNIVERSE_FOLDER);
		this.universeModel = universeModel;// DParams.UNIVERSE_MODEL;
		this.megamodelName = megamodelName;

		this.megamodelFolderPlugin = null;

		this.modelFolder = modelFolder;
		this.javaVersion = javaVersion;
		this.instanceFolderInM2 = instanceFolderInM2;
		this.editPluginSuffix = editPluginSuffix;
		this.editorPluginSuffix = editorPluginSuffix;
		this.testPluginSuffix = testPluginSuffix;
		this.genJdkLevel = genJdkLevel;
		this.emfLev = emfLev;

		if (LOG) {
			clog("EmfDeployr" + "\nuniverseName=" + universeName
					+ "\nmodel_in_universeFolder=" + universeFolder
					+ "\nmegamodelName=" + megamodelName + "\nmodelFolder="
					+ modelFolder + "\ninstanceFolder=" + instanceFolderInM2
					+ "\ninstanceOnly="
					+ (deployInstancesOnly ? "true" : "false"));
			clog("(a)projects:");
			String m = "";
			for (String proj : projects) {
				m += proj + " ";
			}
			clog(m);
		}
	}



	public EmfDeployr(IDiagraphControler controler,
			IMetamodelRetriever metamodelRetriever, List<String> projects,
			boolean deployInstancesOnly, String universeName,
			String universeFolder, String universeModel, String megamodelName,
			String megamodelFolderPlugin, String localRepositoryFoldr,
			String modelFolder, String javaVersion, String instanceFolderInM2,
			String editPluginSuffix_, String editorPluginSuffix_,
			String testPluginSuffix, GenJDKLevel genJdkLevel,
			GenRuntimeVersion emfLev) { // , boolean dummy
		this.teamNameSpace = DiagraphPreferences.getTeamNamespace();
		this.metamodelRetriever = metamodelRetriever;
		this.controler = controler;
		projectsToCompile = projects;
		this.universeName = universeName;
		this.modelInUniverseFolder = universeFolder;
		this.universeModel = universeModel;
		this.megamodelName = megamodelName;
		this.megamodelFolderPlugin = megamodelFolderPlugin; // FP130421pb
		this.megamodelFolderLocal = localRepositoryFoldr;
		this.modelFolder = modelFolder;
		this.javaVersion = javaVersion;
		this.instanceFolderInM2 = instanceFolderInM2;
		this.editPluginSuffix = editPluginSuffix_;
		this.editorPluginSuffix = editorPluginSuffix_;
		this.testPluginSuffix = testPluginSuffix;
		this.genJdkLevel = genJdkLevel;
		this.emfLev = emfLev;

		if (LOG) {
			clog("\nEmfDeployr" + "\nuniverseName=" + universeName
					+ "\nmodel_in_universeFolder=" + universeFolder
					+ "\nmegamodelName=" + megamodelName + "\nmodelFolder="
					+ modelFolder + "\ninstanceFolder=" + instanceFolderInM2
					+ "\ninstanceOnly="
					+ (deployInstancesOnly ? "true" : "false"));
			clog("(b)projects:");
			String m = "";
			for (String proj : projects)
				m += proj + " ";
			clog(m);

		}
	}
/*
	public Megamodel saveUniverse(Megamodel universe) {
		String minfolder = modelInUniverseFolder;
		String modelname = universeModel;
		URI uri = URI.createPlatformResourceURI(universeName + "/" + minfolder
				+ "/" + modelname + "." + MegamodelPackage.eNAME, true);
		clog(uri.toString());
		MegamodelPersistence.save(uri, universe, false);
		return universe;
	}
*/
	public List<IProject> deployModelFromBundle(boolean force,
			boolean instanceonly, String instancePlugin, Megamodel mm_,
			IProgressMonitor progressMonitor) {
		List<IProject> result = new ArrayList<IProject>();
		emfHandler = new EmfHandler(this, projectsToCompile, universeName,
				megamodelFolderPlugin, megamodelFolderLocal,
				instanceFolderInM2, modelFolder, editPluginSuffix,
				editorPluginSuffix, testPluginSuffix, javaVersion,
				genJdkLevel, emfLev);
		basePackage = megamodelName;// DiagraphPreferences.getStringPreference(DParams.KEY_MEGAMODEL_NAME);
		IProject targetRepo = getWorkspaceRepositoryProject(getInstanceRepositoryName());
		if (targetRepo != null && !targetRepo.isOpen())
			try {
				targetRepo.open(null);
			} catch (CoreException e) {
				throw new RuntimeException(
						" unable to get a diagraph repository: "
								+ getInstanceRepositoryName());
			}

		if (targetRepo == null)
			throw new RuntimeException(" unable to get a diagraph repository: "
					+ getInstanceRepositoryName());

		List<String> srcitems = BundleFileCopier.getInstance()
				.getItemsFromBundle(Activator.getDefault().getBundle(),
						megamodelFolderPlugin);// FP130518
		for (String srcitem : srcitems) {
			srcitem = srcitem.substring(megamodelFolderPlugin.length() + 1);

			if (srcitem.endsWith(M3_DOMAIN_SUFFIX)) {
				srcitem = srcitem.substring(0, srcitem.lastIndexOf("."));// iml2.ecore
				modelName = srcitem; // FP130106
				Dsml dsml = null;
				if (mm_ != null)
					dsml = MegamodelHandler.getInstance().findDsml(mm_,
							modelName);

				modelPath = "/" + megamodelFolderPlugin + "/" + srcitem
						+ M3_DOMAIN_SUFFIX;
				lastsegment = srcitem;
				emfHandler.setModelName(modelName);
				IProject created = null;
				String domainName = M3_DOMAIN;
				String sourcefolderpath = null;
				String targetfolderpath = null;
				URI sourceUri = Activator.createURI(modelPath);
				String deployerPluginId = Activator.PLUGIN_ID;
				boolean fromBundle = true;
				boolean generate = true;// FP130404aa false;
				boolean genemf_1 = false;// FP130415
				if (!instanceonly) {
					created = handleDomainM2Model(domainName,
							sourcefolderpath, targetfolderpath, sourceUri,
							deployerPluginId, fromBundle, generate, genemf_1,
							instanceonly, progressMonitor);
					if (created != null) {
						result.add(created);
						if (LOG)
							clog("project " + created + " was created"); // FP130404
					}
				} else {
					created = handleDomainM1Model(
							Activator.createURI(modelPath),
							Activator.PLUGIN_ID, instancePlugin, fromBundle,
							generate, dsml, progressMonitor);
					// not concerned by project created to compile
				}
			}
		}
		emfHandler.restoreWorkspace(); // restore autobuild and so on
		emfHandler.getDsml();
		return result;
	}

	private void sleep(int timeout) {
		if (Display.getCurrent() == null)
			return;
		final long start = System.currentTimeMillis();
		final long deltaMillis = timeout;
		do {
			while (Display.getCurrent().readAndDispatch()) {
				;
			}
		} while ((System.currentTimeMillis() - start) < deltaMillis);
	}

	// FP130404
	private IProject handleDomainM2Model(String domainName,
			String sourcefolderpath, String targetfolderpath, URI sourceUri,
			String deployerPluginId, boolean fromBundle, boolean generate,
			boolean genemf, boolean instanceOnly,
			IProgressMonitor progressMonitor) {
		IProject result = null;
		clog("HDM2 deploying " + lastsegment); // FP130814
		//sleep(100);
		try {
			String prjname = targetfolderpath.substring(targetfolderpath.lastIndexOf("/")+1); //FP150530
			//controler.refreshProject(prjname);
			controler.refreshMegamodelProject();//FP150530
			result = emfHandler.handleDomainM2Model(domainName,
					sourcefolderpath, targetfolderpath, sourceUri,
					deployerPluginId, fromBundle, generate, genemf,
					instanceOnly, progressMonitor);
			//controler.refreshProject(prjname);
		} catch (Exception e) {
			if (THROW_EXCEPTION)
				throw new RuntimeException("unable to open " + lastsegment);
			else {
				System.err.println("error " + " unable to open " + lastsegment);
				if (e.toString().startsWith("Invalid name specified")
						&& e.toString().endsWith(".java"))
					;
				if (e.toString().trim()
						.endsWith("GenModelAccess creation failed"))
					System.err.println("Emf GenModel creation failed ");
				else {
					System.err.println("error " + e.toString());
					if (e.toString().contains("NullPointerException"))
						;
					System.err.println("missing external resource ? "
							+ e.toString());
				}
			}
		}
		return result;
	}

	private boolean deployDomainArtefacts(String src, String tgt,
			URI sourceUri, String deployerPluginId, boolean overWrite,
			IProgressMonitor progressMonitor) {
		try {
			return emfHandler.deployArtefacts_(src, tgt, sourceUri,
					deployerPluginId, overWrite, progressMonitor);
		} catch (Exception e) {
			if (THROW_EXCEPTION)
				throw new RuntimeException("unable to deployArtefacts " + src
						+ " -> " + tgt);
			else
				cerror("error " + " unable to deployArtefacts " + src + " -> "
						+ tgt);
		}
		return false;
	}

	private void cerror(String mesg) {
		System.err.println("e_______[" + mesg + "]");
	}

	private IProject handleDomainM1Model(URI sourceUri,
			String deployerPluginID, String deployerPluginId,
			boolean fromBundle, boolean generate, Dsml dsml,
			IProgressMonitor progressMonitor) {
		IProject result = null;
		try {

			result = emfHandler.handleDomainM1Model(sourceUri,
					deployerPluginID, fromBundle, deployerPluginId, generate,
					progressMonitor);
		} catch (Exception e) {
			if (THROW_EXCEPTION)
				throw new RuntimeException(e.getMessage());
			else {
				if ((e instanceof ResourceException)
						|| (e instanceof NullPointerException)) {
					e.printStackTrace();
					throw new RuntimeException(e.getMessage());
				} else
					clog("error " + e.getMessage());
			}
		}
		return result;
	}

	private String getInstanceRepositoryName() {
		return basePackage + "_models";
	}

	private String getArtefactName(String filePath) {
		String result = filePath
				.substring(filePath.lastIndexOf(File.separator) + 1);
		return result.substring(0, result.indexOf("."));
	}

	private int consolidate(List<EPackage> languages, String srcfoldr,
			String targetPlug, String targetfoldr,
			IProgressMonitor progressMonitor) {
		int count = 0;
		for (EPackage ePackage : languages) {
			if (LOG)
				clog("language found in model repository: "
						+ ePackage.getName());
			progress(progressMonitor, "consolidation for " + ePackage.getName());
			// String nsuri = ePackage.getNsURI();
			// String langname = ePackage.getName();//NsURI();
			modelName = ePackage.getName();
			// if (oneLang == null || oneLang.isEmpty() ||
			// langname.equals(oneLang))
			// {
			modelPath = "/" + megamodelFolderPlugin + "/" + "*." + modelName;
			lastsegment = modelName;
			emfHandler.setModelName(modelName);
			if (emfHandler.consolidateInstance(srcfoldr, targetPlug,
					targetfoldr, modelName, progressMonitor)) {
				Dsml dsml = (megamodel == null ? null : MegamodelHandler
						.getInstance().findDsml(megamodel, modelName));
				if (dsml != null) {
					String m1suffix = "." + modelName;
					consolidateModels(dsml, m1suffix);
					String diagramSuffix_ = "." + modelName
							+ "_default_root_diagram";
					consolidateDiagrams(dsml, diagramSuffix_);
				}
				count++;
				// }
			}
		}
		return count;
	}



	private void consolidateModels(Dsml dsml, String suffix){
		for (String file : files) {
			if (file.endsWith(suffix)) {
				String mName = getArtefactName(file);
				if (findModel(dsml, mName) == null){
					addModelToDsml(dsml, mName,readExcerpt(new File(file)));
				}else if (LOG)
					clog("dsml allready contains " + mName);
			}
		}
	}


	private void consolidateDiagrams(Dsml dsml, String suffix){
		for (String file : files) {
			if (file.endsWith(suffix)) {
				String mn = file.substring(file
						.lastIndexOf(File.separator) + 1);
				mn = mn.substring(0, mn.indexOf(suffix));
				Model m = findModel(dsml, mn);
				String dName = getArtefactName(file);
				if (m != null) {
					NotationDiagram nd = findDiagram(m, dName);
					if (nd == null)
						addDiagramToModel(m, dName,suffix);
				}
			}
		}
	}



	private NotationDiagram findDiagram(Model model, String diagramName) {
		for (NotationDiagram diagram : model.getNotationDiagrams())
			if (diagram.getName().equals(diagramName))
				return diagram;
		return null;
	}

	private void addDiagramToModel(Model m, String modelname, String diagramSuffix) {
		NotationDiagram notationdiag = MegamodelFactory.eINSTANCE
				.createNotationDiagram();
		notationdiag.setURI_(modelname+diagramSuffix);
		notationdiag.setName(modelname);
		m.getNotationDiagrams().add(notationdiag);
		if (LOG)
			clog("add notation diagram " + modelname + " to model "
					+ m.getName());
	}

	private String readExcerpt(File file) {
		InputStream in = null;
		try {
			String line3 = "";
			in = new FileInputStream(file);
			StringBuffer buffer_ = new StringBuffer();
			InputStreamReader inR = new InputStreamReader(in);
			BufferedReader buf = new BufferedReader(inR);
			String line;
			int lines = 0;
			while ((line = buf.readLine()) != null) {
				if (lines == 3)
					line3 = line;
				if (line == null || lines == 3)
					break;
				lines++;
				buffer_.append(line).append("\n");
			}
			in.close();
			return line3;// buffer_.toString();
		} catch (Exception e) {
			try {
				in.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return "";
		}
	}

	private List<EPackage> getRegisteredLanguages(
			IProgressMonitor progressMonitor) {
		List<EPackage> result = new ArrayList<EPackage>();
		for (String file : files) {
			if (!file.endsWith(".ecore"))
				if (!file.endsWith(".ecorediag"))
					if (!file
							.startsWith(org.isoe.fwk.core.DParams.REPOSITORY_FILTER_PREFIX_))
						if (!file.endsWith("_diagram")) {
							EPackage packag = metamodelRetriever
									.findRegisteredMetamodel(
											URI.createFileURI(file),
											progressMonitor);
							if (metamodelRetriever.isInterrupted())
								break;
							if (packag != null) {
								if (!result.contains(packag))
									result.add(packag);
							}
						}
		}
		return result;
	}

	// TODO headache code, refactor the whole feature
	public List<String> putModelToBundleRepository(String targetPlugin,
			Megamodel megamodel,
			// String language_,
			IProgressMonitor progressMonitor) {
		List<String> deployed = new ArrayList<String>();
		emfHandler = new EmfHandler(this, projectsToCompile, universeName,
				megamodelFolderPlugin, megamodelFolderLocal,
				instanceFolderInM2, modelFolder, editPluginSuffix,
				editorPluginSuffix, testPluginSuffix, javaVersion,
				genJdkLevel, emfLev);
		basePackage = megamodelName;
		this.megamodel = megamodel; // FP140624
		files = BundleFileCopier.getInstance().getFilesInRepository(
				targetPlugin, instanceFolderInM2, null, 2); // FP130518
		List<EPackage> languages = getRegisteredLanguages(progressMonitor);
		metamodelRetriever.markUnregisteredModels(progressMonitor);
		int putted = consolidate(languages, instanceFolderInM2, universeName,
				megamodelFolderPlugin, progressMonitor);
		if (LOG && putted == 0)
			clog("nothing put");
		emfHandler.restoreWorkspace(); // restore autobuild and so on
		emfHandler.getDsml();
		return deployed; // TODO propagate deployed
	}

	private Model findModel(Dsml dsml, String name) {
		List<Model> models = dsml.getModels();
		for (Model model : models)
			if (model.getName().equals(name))
				return model;
		return null;
	}

	// FP140624
	private Model addModelToDsml(Dsml dsml, String name, String excrpt) {
		Model m = MegamodelFactory.eINSTANCE.createModel();
		m.setName(name);
		m.setURI_(name + "." + dsml.getName());
		m.setDsml(dsml);
		m.setExcerpt(excrpt);
		dsml.getModels().add(m); // FP140622
		if (LOG)
			clog("(8) add model " + name + "." + dsml.getName());
		return m;
	}



	public List<String> getCopyableArtefacts() {
		return files;
	}

	// FP140320xx
	public List<String> deployModelFromBundleRepository(boolean mode,
			boolean overwrite, boolean instanceOnly, String targetPlugin,
			String languageToDeploy__, IProgressMonitor progressMonitor) { // FP150313

		emfHandler = new EmfHandler(this, projectsToCompile, universeName,
				megamodelFolderPlugin, megamodelFolderLocal,
				instanceFolderInM2, modelFolder, editPluginSuffix,
				editorPluginSuffix, testPluginSuffix, javaVersion,
				genJdkLevel, emfLev);
		basePackage = megamodelName;
		IProject targetRepo = getWorkspaceRepositoryProject(getInstanceRepositoryName());
		if (targetRepo == null)
			throw new OperationCanceledException(" unable to get a "
					+ getInstanceRepositoryName() + " repository");

		int deployed = 0;
		List<String> srcitems = new ArrayList<String>();
		try {
			srcitems = BundleFileCopier.getInstance().getFilesInRepository(
					universeName, megamodelFolderPlugin, null, 1); // FP130518
		} catch (Exception e) {
			e.printStackTrace();
			clog("error wile deployModelFromBundleRepository " + e.toString());
		}
		if (LOG)
			clog("deployModelFromBundleRepository");
		List<String> items = new ArrayList<String>();
		String langs = "";
		for (String srcitem : srcitems) {
			if (srcitem.endsWith(M3_DOMAIN_SUFFIX)) {
				String itm = srcitem.substring(0, srcitem.lastIndexOf("."));
				itm = itm.substring(itm.lastIndexOf(File.separator) + 1);
				items.add(itm); // FP130106
				if (LOG)
					clog("" + itm);
				// project!!! //FP130520
				langs += " " + itm;
			}
		}

		clog("DFBR: " + langs);
		List<String> result = new ArrayList<String>();
		for (String item : items) {
			//FP150313aa
			List<String> instancesdeployed = deployModels(overwrite,
					targetPlugin, item, languageToDeploy__, progressMonitor);
			deployed += instancesdeployed.size();

		}
		if (deployed == 0) {
			IProject prj = ResourceUtils.getProject(targetPlugin);
			IFolder modelrepositoryfolder = prj.getFolder(instanceFolderInM2);
			if (!modelrepositoryfolder.exists())
				try {
					modelrepositoryfolder.create(true, true,
							new NullProgressMonitor());
				} catch (CoreException e) {
					e.printStackTrace();
					controler.log("unable to create folder "
							+ instanceFolderInM2);
				}
			controler.log("nothing to deploy");
		} else
			controler.log(deployed + " models have been deployed");
		emfHandler.restoreWorkspace(); // restore autobuild and so on
		Dsml dsml = emfHandler.getDsml(); // prototype not used at the moment
		return result;
	}

	private List<String> deployModels(boolean overwrite, String targetPlugin,
			String lang, String languageToDeploy_,
			IProgressMonitor progressMonitor) {
		boolean tb;
		List<String> result = new ArrayList<String>();
		modelName = lang; // FP130106
		modelPath = "/" + megamodelFolderPlugin + "/" + "*." + lang;
		progress(progressMonitor, "copying " + modelPath);
		lastsegment = modelName;
		emfHandler.setModelName(modelName);
		Dsml dsml = null;
		if (megamodel != null)
			dsml = MegamodelHandler.getInstance()
					.findDsml(megamodel, modelName);
		// URI uri = URI.createPlatformPluginURI("lang.m2." + modelName, false);
		// String abspath = CommonPlugin.resolve(uri).toFileString(); //FP140323

		URI uriMegamodelFolder = URI.createPlatformPluginURI(universeName + "/"
				+ megamodelFolderPlugin, true);
		File megamodelFolder = new File(CommonPlugin
				.resolve(uriMegamodelFolder).toFileString());
		boolean megamodelFolderExists = megamodelFolder != null;

		URI languageProjectUri = URI.createPlatformPluginURI(DParams.M1_PREFIX
				+ "." + lang, true);
		String languageProject = CommonPlugin.resolve(languageProjectUri)
				.toFileString();
		boolean languageProjectAvailable_ = languageProject != null;
		// languageProjectAvailable = true;

		if (megamodelFolderExists && languageProjectAvailable_) {
			List<String> instancesdeployed = emfHandler.deployInstance(
					universeName, megamodelFolderPlugin, targetPlugin,
					instanceFolderInM2, languageToDeploy_, dsml, overwrite);
			if (instancesdeployed != null) {
				result.addAll(instancesdeployed);
				if (LOG){
					clog("language deployed :" + lang + " with "
							+ instancesdeployed.size() + " instances"); // FP150606
				   if (!instancesdeployed.isEmpty())
					    tb = true;

					if (lang.equals("adl"))
						tb = false;
				}


				if (!instancesdeployed.isEmpty()) {
					controler.deployModels(lang, uriMegamodelFolder,
							instancesdeployed);
					// deployed++;
				} // FP140320 if (!instancesdeployed.isEmpty()){
			}
			if (LOG)
				clog("deploying models(0) for language " + modelName);
		} else if (LOG)
			clog("no opened project on the plugin side named " + modelName);

		return result;
	}

	private void deployingModels_nu(String language,
			List<String> instancesdeployed) {

		URI uri_ = URI.createPlatformPluginURI("lang.m2." + language, true);
		String abspath_ = CommonPlugin.resolve(uri_).toFileString();
		if (abspath_ != null) {

			// createM2Project(language);

			// if (LOG) {
			String mesg = "";
			for (String instance : instancesdeployed) {

				instance = instance.substring(instance
						.indexOf(DParams.INSTANCE_FOLDER + File.separator)
						+ DParams.INSTANCE_FOLDER.length() + 1);
				if (instance.endsWith("." + language)) {
					// createProject(instance);
					// if (instance.contains("simpleworld.simpleworld"))
					// tb = true;
					// createM1Project(instance);
					mesg += instance + " ; ";
				}
			}
			if (LOG)
				clog("deploying models for language " + language + " " + mesg);
		} else if (LOG)
			clog("no opened project on the plugin side named " + language);
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);

	}

	private boolean isConfig(String filter) { // FP130903
		for (String config : DParams.CONFIGS_)
			if (config.equals(filter))
				return true;
		return false;
	}

	private boolean match(String filter) { // FP130903
		for (int i = 0; i < DParams.CONFIGS_.length; i++)
			if (DParams.CONFIGS_[i].equals(filter))
				for (String language : DParams.CONFIG_LANGUAGES_[i])
					if (language.equals(modelName))
						return true;
		return false;
	}

	private List<String> filterAndDeployDomain(boolean all, String filter) {
		List<String> done = new ArrayList<String>();
		for (String srcitem : BundleFileCopier.getInstance()
				.getItemsFromBundle(Activator.getDefault().getBundle(),
						megamodelFolderPlugin)) {
			String dsml = srcitem.substring(megamodelFolderPlugin.length() + 1);
			if (dsml.endsWith(M3_DOMAIN_SUFFIX)) {
				boolean matches = false;
				for (String initiallanguage : DParams.INITIAL_LANGUAGES) {
					if (dsml.equals(initiallanguage + M3_DOMAIN_SUFFIX)) {
						matches = true;
						if (LOG)
							clog("filterAndDeployDomain - matches " + dsml);
						break;
					}
				}
				if (filter != null)
					matches = match(filter);
				if (all || (!all && matches))
					if (deployEligibleDomainArtefacts(dsml, filter))
						done.add(dsml);
			}
		}
		return done;
	}

	private final boolean isModelEligible(String filter) {
		if (filter != null && !filter.isEmpty()) {
			if (filter.endsWith("*")) {
				filter = filter.substring(0, filter.length() - 1);
				return modelName.startsWith(filter);
			}
			if (isConfig(filter))
				return match(filter);
			if (filter.equals("autotest"))
				clog("IMEL " + filter);
			return modelName.equals(filter);
		}
		return true;
	}

	public List<String> deployBuildInDomainArtefacts(boolean all,
			String filter) { // FP121215
		List<String> result = null;
		emfHandler = new EmfHandler(this, projectsToCompile, universeName,
				megamodelFolderPlugin, megamodelFolderLocal,
				instanceFolderInM2, modelFolder, editPluginSuffix,
				editorPluginSuffix, testPluginSuffix, javaVersion,
				genJdkLevel, emfLev);
		basePackage = megamodelName;
		getWorkspaceRepositoryProject(universeName);
		result = filterAndDeployDomain(all, filter);
		emfHandler.restoreWorkspace(); // restore autobuild and so on
		emfHandler.getDsml();
		return result;
	}

	private boolean deployEligibleDomainArtefacts(String dsml, String filter) {
		dsml = dsml.substring(0, dsml.lastIndexOf("."));
		modelName = dsml;
		if (isModelEligible(filter)) {
			emfHandler.setModelName(modelName);
			modelPath = "/" + megamodelFolderPlugin + "/" + dsml
					+ M3_DOMAIN_SUFFIX;
			lastsegment = dsml;
			String mm = basePackage + "." + lastsegment;
			String tgt = PathPreferences.getPreferences()
					.getUniverseProjectName()
					+ "/"
					+ PathPreferences.getPreferences().getUniverseFolder();
			if (LOG)
				clog("candidate to deployment: " + mm + " to " + tgt);
			return deployDomainArtefacts(megamodelFolderPlugin, tgt,
					Activator.createURI(modelPath), Activator.PLUGIN_ID,
					DParams.DPEDA_OVERWRITE_ON_DEPLOY, null);
		} else if (LOG)
			clog(modelName + " is out of filter: " + filter);
		return false;
	}

	private void progress(IProgressMonitor progressMonitor, String label) {
		if (progressMonitor.isCanceled())
			throw new OperationCanceledException("Operation Canceled");
		progressMonitor.subTask(label);
		progressMonitor.worked(1);
	}

	public List<IProject> deployMegamodelFromPlugin(boolean force,
			Megamodel megamodel, IProgressMonitor progressMonitor) { // FP121215
		// //
		// FP120522
		// - workspace parameterization (the job turns out autobuild and so on)
		// ==> not shareable
		progress(progressMonitor, "deployMegamodelFromPlugin");
		boolean fromBundle = true;
		this.megamodel = megamodel;
		List<IProject> result = new ArrayList<IProject>();
		emfHandler = new EmfHandler(this, projectsToCompile, universeName,
				megamodelFolderPlugin, megamodelFolderLocal,
				instanceFolderInM2, modelFolder, editPluginSuffix,
				editorPluginSuffix, testPluginSuffix, javaVersion,
				genJdkLevel, emfLev);

		basePackage = megamodelName;// DiagraphPreferences.getStringPreference(DParams.KEY_MEGAMODEL_NAME);
		boolean pexists = org.isoe.fwk.utils.ResourceUtils
				.projectExists(universeName);
		IProject resourceRepo = getWorkspaceRepositoryProject(universeName);
		if (resourceRepo == null)
			throw new RuntimeException(" unable to get a diagraph repository");
		List<String> srcitems = BundleFileCopier.getInstance()
				.getItemsFromBundle(Activator.getDefault().getBundle(),
						megamodelFolderPlugin); // //FP130518

		for (String srcitem : srcitems) {
			srcitem = srcitem.substring(megamodelFolderPlugin.length() + 1);
			if (srcitem.endsWith(M3_DOMAIN_SUFFIX)) {
				srcitem = srcitem.substring(0, srcitem.lastIndexOf("."));// iml2.ecore
				modelName = srcitem; // FP130106
				emfHandler.setModelName(modelName);
				modelPath = "/" + megamodelFolderPlugin + "/" + srcitem
						+ M3_DOMAIN_SUFFIX;
				lastsegment = srcitem;
				String mm = basePackage + "." + lastsegment;
				boolean alreadyExists = emfHandler.existModel(mm, modelFolder
						+ "/" + srcitem, M3_DOMAIN);
				boolean genemf_2 = false;
				if (alreadyExists) {
					if (LOG)
						clog(mm + M3_DOMAIN_SUFFIX + " allready exists");
					boolean generate = false;

					boolean instanceOnly = false;
					IProject r = handleDomainM2Model(M3_DOMAIN, null, null,
							Activator.createURI(modelPath),
							Activator.PLUGIN_ID, fromBundle, generate,
							genemf_2, instanceOnly, progressMonitor);
					if (r != null) {
						result.add(r);
					}

				} else if (!alreadyExists || force) {// FP130105
					boolean generate = true;
					boolean instanceOnly = false;
					IProject r = handleDomainM2Model(M3_DOMAIN, null, null,
							Activator.createURI(modelPath),
							Activator.PLUGIN_ID, fromBundle, generate,
							genemf_2, instanceOnly, progressMonitor);
					if (r != null) {
						result.add(r);
						// addMetaModel1(fromBundle);
					}

				}
			}
		}
		emfHandler.restoreWorkspace(); // restore autobuild and so on
		Dsml dsml = emfHandler.getDsml();
		return result;
	}

	public IProject getWorkspaceRepositoryProject(String name) { // FP130104
		IProject result = ResourceUtils.getProject(name);
		if (result == null) {
			try {
				result = ProjectFactory.createPluginProject(name);
			} catch (Exception e) {
				throw new RuntimeException("unable to create project " + name);
			}
		}
		if (!result.isOpen())
			try {
				result.open(new NullProgressMonitor());
			} catch (CoreException e) {
				clog(e.toString());
				e.printStackTrace();
			}
		if (result == null)
			throw new RuntimeException(" unable to get a diagraph repository");
		return result;
	}

	public List<IProject> deployMegamodelFromWorkspace(boolean force,
			boolean generate, boolean genemf, Megamodel megamodel,
			String localFolder, String languageToDeploy, String filter_,
			IProgressMonitor progressMonitor) { // FP120529
		List<IProject> result = new ArrayList<IProject>();
		boolean fromBundle = false;
		progress(progressMonitor, "deployMegamodelFromWorkspace");
		// this.modelingUniverse = modelingUniverse;// createUniverse();
		this.megamodel = megamodel;

		emfHandler = new EmfHandler(this, projectsToCompile, universeName,
				localFolder, megamodelFolderLocal, instanceFolderInM2,
				modelFolder, editPluginSuffix, editorPluginSuffix,
				testPluginSuffix, javaVersion, genJdkLevel, emfLev);

		boolean pexists = org.isoe.fwk.utils.ResourceUtils
				.projectExists(universeName);
		IProject resourceRepo = getWorkspaceRepositoryProject(universeName);
		if (resourceRepo == null)
			throw new RuntimeException("  unable to get a diagraph repository");
		String deployerPluginId = resourceRepo.getFullPath().toPortableString()
				.substring(1);
		if (LOG)
			clog("DPMFW source repository=" + resourceRepo.toString()
					+ " , all projects: ");
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject[] prjcts = root.getProjects();
		if (LOG) {
			String m = "";
			for (IProject project : prjcts)
				m += project.getName() + " ";
			clog(m);
		}
		basePackage = megamodelName;

		List<String> srcitems = WorkspaceFileCopier
				.getItemsFromWorkspaceRepository(resourceRepo,
						megamodelFolderLocal);
		String sourcefolderpath = resourceRepo.getLocation().toPortableString();// +
		// "/"+
		// DParams.MEGAMODEL_FOLDER;

		for (String srcitem : srcitems) {
			if (LOG)
				clog("exists:" + srcitem);
			if (srcitem.endsWith(M3_DOMAIN_SUFFIX)) {
				srcitem = srcitem.substring(0, srcitem.lastIndexOf("."));// iml2.ecore
				modelPath = "/" + megamodelFolderLocal + "/" + srcitem
						+ M3_DOMAIN_SUFFIX;
				lastsegment = srcitem;
				modelName = srcitem; // FP130106
				if (isModelEligible(filter_)) { // FP130606
					emfHandler.setModelName(modelName);
					String mm = basePackage + "." + lastsegment;
					String targetfolderpath = root.getLocation()
							.toPortableString()
							+ "/"
							+ basePackage
							+ "."
							+ srcitem;
					URI sourceUri = URI.createPlatformResourceURI(resourceRepo
							.getFullPath().toPortableString()
							+ "/"
							+ megamodelFolderLocal
							+ "/"
							+ srcitem
							+ M3_DOMAIN_SUFFIX, true);

					if (LOG)
						clog("MM source to deploy:" + sourceUri);
					boolean alreadyExists = emfHandler.existModel(mm,
							modelFolder + "/" + srcitem, M3_DOMAIN);
					if (alreadyExists) {
						if (LOG)
							clog(mm + M3_DOMAIN_SUFFIX + " allready exists");
					} else if (!alreadyExists || force) {
						if ((languageToDeploy == null || languageToDeploy
								.isEmpty())
								|| languageToDeploy != null
								&& lastsegment.equals(languageToDeploy)) { // FP130429
							if (LOG)
								clog(mm
										+ " project is to be generated from local repository");
							// boolean generate = true;
							boolean instanceOnly = false; // if not exists we
							// must
							// force project
							// generation
							IProject deployed = handleDomainM2Model(M3_DOMAIN,
									sourcefolderpath, targetfolderpath,
									sourceUri, deployerPluginId, fromBundle,
									generate, genemf, instanceOnly,
									progressMonitor);
							if (deployed == null) {
								if (LOG)
									clog(mm + " has an error");
							}

						} else if (LOG)
							clog(mm + " project is not to be deployed");
					}
				} else if (LOG)
					clog("model " + modelName + " is not eligible with "
							+ filter_);
			}
		}
		emfHandler.restoreWorkspace(); // restore autobuild and so on
		Dsml dsml = emfHandler.getDsml();
		return result;
	}

	public static IProject createProject_(String projectName) { // FP130105
		IProject project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(projectName);
		if (!project.isAccessible())
			try {
				project.open(new NullProgressMonitor());
			} catch (CoreException e) {
				throw new RuntimeException("project " + projectName
						+ " must exist");
			}
		if (!project.isAccessible())
			throw new RuntimeException("project " + projectName + " must exist");
		return project;
	}

	protected void buildProject(IProject p) {
		try {
			IStatus s = compileUtil.build(p);
			if (!s.isOK()) {
				Activator.getDefault().getLog().log(s);
				if (s.getException() != null)
					s.getException().printStackTrace(System.err);
				else {
					System.err.println("buildProject failed without exception:"
							+ s);
					LinkedList<IStatus> ch = new LinkedList<IStatus>(
							Arrays.asList(s.getChildren()));
					while (!ch.isEmpty()) {
						IStatus f = ch.removeFirst();
						if (f.getException() != null) {
							System.err
									.println("============> Nested exception in the status:");
							f.getException().printStackTrace(System.err);
						}
						ch.addAll(Arrays.asList(f.getChildren()));
					}
				}
				System.err.println(s.getMessage());
				// throw new RuntimeException(s.getMessage());
			}
		} catch (Exception e) {
			System.err.println("error in compilation phase");
		}
	}

	public void compileAllProjects_(IProgressMonitor progressMonitor) { // FP120522
		progress(progressMonitor, "compileAllProjects");
		// workspace parameterization makes a non shareable resource
		if (projectsToCompile.size() > 0) {
			RuntimeWorkspaceSetup rws = RuntimeWorkspaceSetup // FP140415
					.getInstance(javaVersion);
			compileUtil = new CompileUtil();
			for (String pluginID : projectsToCompile) {
				progress(progressMonitor, "compiling project " + pluginID);
				buildProject(ResourcesPlugin.getWorkspace().getRoot()
						.getProject(pluginID));
			}
			RuntimeWorkspaceSetup.releaseInstance();
		}
	}
	/*
	 * public void foobarAllProjects(IProgressMonitor progressMonitor) { //
	 * FP120522 // synchronized // // ?? progress(progressMonitor,
	 * "foobarAllProjects"); // workspace parameterization makes a non shareable
	 * resource if (projectsToCompile.size() > 0) { RuntimeWorkspaceSetup rws =
	 * RuntimeWorkspaceSetup .getInstance(javaVersion); compileUtil = new
	 * CompileUtil(); for (String pluginID : projectsToCompile)
	 * buildProject(ResourcesPlugin.getWorkspace().getRoot()
	 * .getProject(pluginID)); RuntimeWorkspaceSetup.releaseInstance(); } }
	 */

}
