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
package org.isoe.diagraph.gen;

//import java.lang.reflect.InvocationTargetException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecoretools.legacy.diagram.part.EcoreDiagramEditor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gef.TreeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.PageBookView;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.views.contentoutline.ContentOutline;
import org.isoe.diagraph.common.IDiagraphInvoker;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.diagraph.DGraphElement;
import org.isoe.diagraph.diagraph.DNode;
import org.isoe.diagraph.diagraph.DiagraphPackage;
import org.isoe.diagraph.factory.DGraphFactory;
import org.isoe.diagraph.generic.GenericConstants;
import org.isoe.diagraph.internal.m2.DiaPointOfView;
import org.isoe.diagraph.lang.DiagraphKeywords;
import org.isoe.diagraph.parser.DiagraphNotation;
import org.isoe.diagraph.parser.api.IDiagraphNotation;
//import org.isoe.diagraph.parser.DiagraphAnnotationParser;
import org.isoe.diagraph.parser.api.IDiagraphView;
import org.isoe.diagraph.preferences.DiagraphPreferences;
import org.isoe.diagraph.runner.IDiagraphRunner;
import org.isoe.diagraph.views.ViewConstants;
import org.isoe.diagraph.workbench.api.DiagraphNotifiable;
import org.isoe.diagraph.workbench.api.IDiagraphFactoryClient;
import org.isoe.extensionpoint.diagraph.generator.IDiagraphGen;
import org.isoe.extensionpoint.ecoreutils.IEcoreUtils;
import org.isoe.extensionpoint.parsers.DiagraphParserConnector;
import org.isoe.extensionpoint.parsers.IDiagraphProvider;
import org.isoe.extensionpoint.parsers.IRuntimeDiagraphParser;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.utils.ResourceUtils;
import org.isoe.diagraph.gmfgen.IGmfDiagramGen;
import  org.isoe.diastyle.lang.StyleConstants;




import org.isoe.diagraph.gen.gmf.main.DiagraphRunner;

//import org.isoe.diagraph.factory.DGraphFactory;
/**
 *
 * @author pfister
 *
 */
//import org.isoe.diagraph.controler.IDiagraphControler;  //FP140707refactored
//FP140707_b_refactored


public class DiagraphGenerator implements IDiagraphProvider, IDiagraphInvoker,
		IDiagraphGen ,IDiagraphFactoryClient{





	private static final boolean LOG = DParams.DiagraphGenerator_LOG;

	private class IdGenerator {
		boolean isnode;
		String name;
		int id;
	}

	private IDiagraphNotation notation;
	private List<IdGenerator> idgen = new ArrayList<IdGenerator>();
	private String[] views;
	private String metamodelResourcepath;
	private DiagraphNotifiable owner;
	private List<EAnnotation> diagraphAnnotations = new ArrayList<EAnnotation>();
	private List<EAnnotation> allAnnotations = new ArrayList<EAnnotation>();
	private Diagram selectedDiagram;
	private Diagram workdiagram;
	private Diagram currentDiagram;
	private DGraph diagraph;
	private DGraphElement currentGraphElement;
	private IRuntimeDiagraphParser parser;
	private String genLog = "";
	private List<String> diagraphRunnerDiagraphLogs;
	private EcoreDiagramEditor ecoreDiagramEditor;
	private IDiagraphRunner diagraphrunner;

	private Node currentDiagraphedNode;

	private EPackage pakage;
	private TransactionalEditingDomain editingDomain;
	private GenNotationAction genNotationAction;
	private DGraph mockDiagraph;

	private Map<String, DGraph> diagraphviews_ = new HashMap<String, DGraph>();
	private Map<String, DGraph> diagraphmocks = new HashMap<String, DGraph>();
	private boolean save;
	private boolean atRuntime;
	private boolean done;
	private boolean refactor; // FP140124
	private DiagraphNotifiable diagraphNotifiable;
	private boolean headless;
	//private Resource diagraphResource_; //FP150514

	private static final boolean CERROR = DParams.LOG_AT_RUNTIME;

	@Override
	public void initView(EPackage packag, String view) {


	}



	@Override
	public DiagraphNotifiable getOwner() { // FP140701
		return owner;
	}

	@Override
	public void logStatements(String view, String logstatements) {
		owner.logStatements(view, logstatements);
	}

	@Override
	public void setHeadless(boolean headless) {
		this.headless = headless;
	}

	@Override
	public void registerCurrentView(String langName, String view, int sender) {
		owner.registerCurrentView(langName, view, sender);
	}

	@Override
	public String getRegisterdView(String language) {
		return owner.getRegisterdView(language);
	}

	@Override
	public String getCurrentView(int sender, String language, String mesg) {
		return owner.getCurrentView(sender, language, mesg);
	}

	@Override
	public Diagram getSelectedDiagram() {
		return selectedDiagram;
	}

	@Override
	public Diagram getWorkdiagram() {
		return workdiagram;
	}

	@Override
	public Diagram getCurrentDiagram() {
		return currentDiagram;
	}

	public void setEcoreDiagramEditor(EcoreDiagramEditor part) {
		this.ecoreDiagramEditor = part;
	}

	public EcoreDiagramEditor getEcoreDiagramEditor() {
		return ecoreDiagramEditor;
	}

	public EPackage getPackage() {
		return pakage;
	}

	@Override
	public Node getCurrentDiagraphedNode() {
		return currentDiagraphedNode;
	}

	public List<String> getDiagraphRunnerDiagraphLog() {
		if (diagraphRunnerDiagraphLogs == null)
			diagraphRunnerDiagraphLogs = new ArrayList<String>(); // FP120603
		return diagraphRunnerDiagraphLogs;
	}

	public String getGenLog() {
		return genLog;
	}

	@Override
	public DGraph getDiagraph() {
		return diagraph;
	}

	@Override
	public DGraph getDiagraph(boolean mock, String view) {
		if (mock)
			return diagraphmocks.get(view);
		else
			return diagraphviews_.get(view);

	}

	@Override
	public void setDiagraph(boolean mock, String view, DGraph diagraph) {
		if (mock)
			diagraphmocks.put(view, diagraph);
		else
			diagraphviews_.put(view, diagraph);
	}

	@Override
	public void setDiagraph(DGraph diagraph) {
		this.diagraph = diagraph;
	}

	@Override
	public DGraph getMockDiagraph() {
		return mockDiagraph;
	}

	@Override
	public void setMockDiagraph(DGraph diagraph) {
		this.mockDiagraph = diagraph;
	}



	private void createNotation(IDiagraphGen diagraphGenerator) { // FP140505xx
		notation = new DiagraphNotation(this);
	}



	public IDiagraphView getView_(String view) {
		return notation.getView_(view);
		// return getDiagraphView(view);
	}

	@Override
	public void setEcoreUtilService(IEcoreUtils ecoreUtilService) {
		diagraphrunner.setEcoreUtils(ecoreUtilService);
		notation.setEcoreUtils(ecoreUtilService);
	}

	private void logBounds(Bounds bnds, int target) {
		String log = "x: " + bnds.getX() + " y: " + bnds.getY() + " w: "
				+ bnds.getWidth() + " h: " + bnds.getHeight();
		log(target, log);
	}

	private void log(int target, String mesg) {
		owner.log(target, mesg);
	}

	@Override
	public void log(String mesg) {
		log(3, mesg);
	}

	private void clearLog(int target) {
		owner.clearLog(target);
	}

	private void parseAnnotationDetails(EAnnotation anot, String layer,
			int target) {
		for (Map.Entry<String, String> entry : anot.getDetails()) {
			parse("dg1", layer, entry.getKey());
			log(target,
					entry.getKey()
							+ (entry.getValue() != null
									&& !entry.getValue().isEmpty() ? "->"
									+ entry.getValue() : ""));
		}
	}

	public boolean isDefaultView(EAnnotation anot) {
		for (Map.Entry<String, String> entry : anot.getDetails())
			if (entry.getKey().startsWith(DiagraphKeywords.VIEW_EQU))
				return false;
		return true;
	}

	public boolean isView(String viewid, EAnnotation anot) { // FP131208
		if (!anot.getSource().equals(DiagraphKeywords.DIAGRAPH_LITTERAL))
			return false;
		if (viewid == null || viewid.equals(DiagraphKeywords.DIAGRAPH_DEFAULT)
				|| viewid.isEmpty())
			return isDefaultView(anot);
		for (Map.Entry<String, String> entry : anot.getDetails())
			if (entry.getKey().equals(DiagraphKeywords.VIEW_EQU + viewid))
				return true;
		return false;
	}

	private void parseNode(String from_, String layer, Node gmfnode,
			boolean store, int target) {
		LayoutConstraint lc = gmfnode.getLayoutConstraint();
		if (lc instanceof Bounds)
			logBounds((Bounds) lc, target);
		EObject el = gmfnode.getElement();
		if (el != null) {
			if (el instanceof EClass) {
				EClass eclaz = (EClass) el;
				log(target, eclaz.getName());
				EAnnotation diagraphanot = null;
				for (EAnnotation ean : eclaz.getEAnnotations())
					// FP140603modif
					if (isView(layer, ean))
						diagraphanot = ean;
				// EAnnotation diagraphanot = eclaz
				// .getEAnnotation(GenericConstants.ANNOT_DIAGRAPH_LITTERAL);

				if (diagraphanot != null) {
					if (store)
						diagraphAnnotations.add(diagraphanot);
					log(target, eclaz.getName() + "{");
					setcurrentGraphElement(parse(from_ + ".dg2", layer, eclaz));
					parse("dg3", layer, diagraphanot);
					parseAnnotationDetails(diagraphanot, layer, target);
					log(target, "}");
					log(target, "");
					if (!done) {
						done = true;
						if (LOG)
							clog("done"); // first diagraphed EClass encountered
					}

				} else
					log(target, "no "
							+ GenericConstants.ANNOT_DIAGRAPH_LITTERAL
							+ " annotation");
			} else {
				if (el instanceof org.eclipse.emf.ecore.EAnnotation) {
					EAnnotation annotation = (EAnnotation) el;
					EModelElement me = annotation.getEModelElement();
					if (store)
						allAnnotations.add(annotation);
				} else
					log(target, "<<not a known node " + el.getClass().getName()
							+ ">>(2)");
			}
		} else {
			if (gmfnode instanceof Shape) {
				Shape sh = (Shape) gmfnode;
				log(target, "Shape " + sh.getDescription());
				logBounds((Bounds) sh.getLayoutConstraint(), target);
			} else {
				log(target, "[[not an semantic node " + gmfnode.toString()
						+ "]]");
			}
		}
	}

	private String analyseResources(ResourceSet rs, String layer,
			boolean atRuntime) { // getMetamodelResourcePath
		if (atRuntime)
			for (Resource resource : rs.getResources()) {
				log(1, resource.getURI().toString());
				parse("dg4", layer, resource.getURI());
			}
		for (Resource resource : rs.getResources()) {
			String path = resource.getURI().toString();
			if (path.endsWith(".ecore"))
				return path;
		}
		return null;
	}

	@Override
	public String[] getViews() {
		return views;
	}

	private int getId(boolean isNode, String name) {
		for (IdGenerator idGenerator : idgen) {
			if (idGenerator.isnode == isNode && idGenerator.name.equals(name))
				return idGenerator.id;
		}
		return -1;
	}

	private int getNextId_(boolean isNode, String name) {
		int id = getId(isNode, name);
		if (id == -1) {
			IdGenerator idg = new IdGenerator();
			idg.name = name;
			idg.isnode = isNode;
			idg.id = getMaxId(isNode) + 1;
			idgen.add(idg);
			return idg.id;
		}
		return id;
	}

	private int getMaxId(boolean isNode) {
		int max = 0;
		for (IdGenerator idGenerator : idgen) {
			if (idGenerator.isnode == isNode)
				if (idGenerator.id > max)
					max = idGenerator.id;
		}
		return max;
	}

	// FP131205
	@Override
	public void clearIdGenerator() {
		idgen = new ArrayList<IdGenerator>();
	}

	@Override
	public int getNextNodeId(String name) {
		int result = getNextId_(true, name);
		if (LOG)
			clog("Id for Node " + name + " = " + result);
		return result;
	}

	@Override
	public int getNextEdgeId(String name) {
		int result = getNextId_(false, name);
		if (LOG)
			clog("Id for Edge " + name + " = " + result);
		return result;
	}

	@Override
	public void execDiagraphTransformation(String layer, String resourcepath,
			boolean validate, boolean refreshArtifactsOnly,
			IProgressMonitor progressMonitor) { // FP130107a
		boolean rcpNo = false; // FP130507
		boolean atRuntimeNo = false;
		GenModel nullGenmodel = null;
		boolean saveYes = true;
		execDiagraphTransformation(layer, atRuntimeNo, nullGenmodel,
				resourcepath, saveYes, rcpNo, validate, refreshArtifactsOnly,
				progressMonitor);// 0,
	}

	private void execDiagraphTransformation(String layer, boolean atRuntime,
			GenModel genmodel, String resourcepath, boolean save, boolean rcp,
			boolean validate, boolean refreshArtifactsOnly,
			IProgressMonitor progressMonitor) {
		if (LOG)
			clog(" view=" + layer + " resourcepath=" + resourcepath
					+ (refreshArtifactsOnly ? " refreshArtifactsOnly" : ""));
		String plugin = resourcepath.substring(0, resourcepath.indexOf("/"));
		if (progressMonitor != null) {
			progressMonitor.subTask("handling " + plugin + " view:" + layer);
			progressMonitor.worked(1);
		}
		URI uri = URI.createPlatformResourceURI(resourcepath, true);
		final boolean notDeleteOldJavaSrc = false;
		final boolean notChangeTargetFolder = false;
		boolean genFromDiagraph = !refactor; // FP140124xxxxaaaa
		String abspath = CommonPlugin.resolve(uri).toFileString();

		genLog = diagraphrunner.manageFoldersAndGenerateAllPointsOfViewCommon(
				layer, atRuntime, genFromDiagraph, genmodel, plugin, abspath,
				notDeleteOldJavaSrc, notChangeTargetFolder, rcp, validate,
				refreshArtifactsOnly, progressMonitor); // FP121031
		// //
		// FP120427
		diagraphRunnerDiagraphLogs = new ArrayList<String>();
		diagraphRunnerDiagraphLogs.addAll(diagraphrunner.getLogDiagraph());
	}

	private String addMultiviewSegmentAtName(String name, String layer) { // FP121102
		String lrt = DiaPointOfView.getLayeredRootName(layer);
		if (lrt == null)
			return null;
		if (!name.contains(ViewConstants.VIEW_SEPARATOR_1)) { // test101.diagraph
																// // FP121102
			String[] names = name.split("\\.");
			name = names[0] + ViewConstants.VIEW_SEPARATOR_1 + lrt + "."
					+ names[1];
		}
		return name; // test101_default_root.diagraph
	}

	private String addMultiviewSegmentAtName_new_nu(String name, String layer) { // FP140216
		if (layer.equals("default"))
			layer = "";
		if (!layer.isEmpty()) {
			layer = "_" + layer;
			String[] names = name.split("\\.");
			name = names[0] + layer + names[1];
		}
		return name;
	}

	private DGraph readOrGenerateDiagraphModel_old_nu(String layer,
			boolean atRuntime, ResourceSet resourceSet, String dpath,
			boolean forceGeneration, boolean save, boolean rcp,
			boolean validate, boolean refreshArtifactsOnly,
			IProgressMonitor progressMonitor) {
		// boolean refreshArtifactsOnly = false;
		String sdpath = dpath;
		String ecoreName = dpath.substring(dpath.lastIndexOf("/") + 1);
		String s2dpath = dpath
				.substring(0, dpath.length() - ecoreName.length());
		String diagraphName = ecoreName.replace("ecore", DiagraphPackage.eNAME);
		diagraphName = addMultiviewSegmentAtName(diagraphName, layer);
		s2dpath = s2dpath + diagraphName;

		if (validate == true)
			if (DiagraphPreferences
					.getBooleanPreference(DParams.KEY_DIAGRAPH_AUTO_GENERATION)
					|| forceGeneration) {
				String resourcepath = sdpath.substring("platform:/resource/"
						.length());
				execDiagraphTransformation(layer, atRuntime, null,
						resourcepath, save, rcp, validate,
						refreshArtifactsOnly, progressMonitor);// FP121031www
			}
		String modelName = s2dpath.substring(s2dpath.lastIndexOf("/") + 1);
		String projname = s2dpath.substring("platform:/resource/".length());
		if (!projname.contains("/" + DParams.MODEL_FOLDER + "/")) { // FP120603
			clog("not in a diagraph context");
			return null;
		}
		projname = projname.substring(0,
				projname.lastIndexOf("/" + DParams.MODEL_FOLDER + "/"));
		String modelAbsolutePath = ResourceUtils.modelExists(projname,
				DParams.MODEL_FOLDER, modelName); // FP130620

		if (modelAbsolutePath != null) {
			Resource diagraphResource = ResourceUtils.loadNotEmptyModel(
					modelAbsolutePath, DiagraphPackage.eNS_URI,
					DiagraphPackage.eINSTANCE);
			if (diagraphResource == null) {
				String modelAbsolutePat = ResourceUtils
						.modelExists(
								diagraphName.substring(0,
										diagraphName.lastIndexOf(".")),
								DParams.MODEL_FOLDER, modelName);
				// if (modelAbsolutePat == null)
				// System.out.print("??readOrGenerateDiagraphModel_new_29_");
			}
			if (diagraphResource != null)
				return (DGraph) (diagraphResource.getContents().get(0));
		}
		return null;
	}

	private DGraph readOrGenerateDiagraphModel(String layer, boolean atRuntime,
			ResourceSet resourceSet, String dpath, boolean forceGeneration,
			boolean save, boolean rcp, boolean validate,
			boolean refreshArtifactsOnly, IProgressMonitor progressMonitor) {

		String sdpath = dpath;
		String ecoreName = dpath.substring(dpath.lastIndexOf("/") + 1);
		String s2dpath = dpath
				.substring(0, dpath.length() - ecoreName.length());
		String diagraphName = ecoreName.replace("ecore", DiagraphPackage.eNAME);
		diagraphName = addMultiviewSegmentAtName(diagraphName, layer);
		s2dpath = s2dpath + diagraphName;

		if (validate == true || forceGeneration) // FP140602xxx ||
													// forceGeneration
			if (DiagraphPreferences
					.getBooleanPreference(DParams.KEY_DIAGRAPH_AUTO_GENERATION)
					|| forceGeneration) {
				String resourcepath = sdpath.substring("platform:/resource/"
						.length());
				execDiagraphTransformation(layer, atRuntime, null,
						resourcepath, save, rcp, validate,
						refreshArtifactsOnly, progressMonitor);// FP121031www
			}
		String modelName = s2dpath.substring(s2dpath.lastIndexOf("/") + 1);
		String projname = s2dpath.substring("platform:/resource/".length());
		if (!projname.contains("/" + DParams.MODEL_FOLDER + "/")) { // FP120603
			clog("not in a diagraph context");
			return null;
		}
		projname = projname.substring(0,
				projname.lastIndexOf("/" + DParams.MODEL_FOLDER + "/"));
		String modelAbsolutePath = ResourceUtils.modelExists(projname,
				DParams.MODEL_FOLDER, modelName); // FP130620
//controler




		//Resource diagraphResource_ = getOwner().getCurrentResource(); //FP150514voir

		if (modelAbsolutePath != null  && !DParams.RELOAD_ALWAYS){ //FP150514
			String langname = projname;
			langname = langname.substring(langname.lastIndexOf(".")+1);
			DGraph result = owner.get(langname, layer);
			if (result!=null)
				return result;
		}


		if (modelAbsolutePath != null ) { //FP150514voir && diagraphResource == null
			Resource diagraphResource_ = ResourceUtils.loadNotEmptyModel(
					modelAbsolutePath, DiagraphPackage.eNS_URI,
					DiagraphPackage.eINSTANCE);
			if (diagraphResource_ == null) {
				String modelAbsolutePat = ResourceUtils
						.modelExists(
								diagraphName.substring(0,
										diagraphName.lastIndexOf(".")),
								DParams.MODEL_FOLDER, modelName);
				// if (modelAbsolutePat == null)
				// System.out.print("??readOrGenerateDiagraphModel_new_29_");
			}
			if (diagraphResource_ != null)
				return (DGraph) (diagraphResource_.getContents().get(0));
		}
		return null;
	}





	public DiagraphGenerator() {
		super();
		DiagraphNotifiable notif = getOwner();

	}



	private DGraph getOrGenerateDiagraphModel_(String layer, ResourceSet rs,
			boolean forceGeneration, boolean save, boolean atRuntime,
			boolean rcp, boolean validate, boolean refreshArtifactsOnly,
			IProgressMonitor progressMonitor) { // FP120527c
		// boolean refreshArtifactsOnly = !forceGeneration; //FP140602

		metamodelResourcepath = analyseResources(rs, layer, atRuntime); // FP120923
		if (metamodelResourcepath == null
				|| !metamodelResourcepath.endsWith(".ecore")) {
			if (metamodelResourcepath == null)
				clog("metamodelResourcepath is null");
			else
				clog("not a metamodel: " + metamodelResourcepath);
			throw new RuntimeException("no metamodel");
		}
		String projectName = metamodelResourcepath
				.substring("platform:/resource".length() + 1);
		if (!projectName.contains("/" + DParams.MODEL_FOLDER + "/")) { // FP120603ok
			clog("metamodel is not in a diagraph project");
			return null;
		}
		projectName = projectName.substring(0,
				projectName.lastIndexOf(DParams.MODEL_FOLDER + "/") - 1);
		String modelName = metamodelResourcepath.substring(
				metamodelResourcepath.lastIndexOf("/") + 1).replace(".ecore",
				".diagraph");
		modelName = addMultiviewSegmentAtName(modelName, layer);
		if (modelName == null)
			return null;
		String modelAbsolutePath = ResourceUtils.modelExists(projectName,
				DParams.MODEL_FOLDER, modelName);
		if (modelAbsolutePath == null) {
			forceGeneration = true;
			clog("diagraph model missing or empty, regenerate !");
		}
		DGraph dgraph = readOrGenerateDiagraphModel(layer, atRuntime,
				rs, // FP120607
				metamodelResourcepath, forceGeneration, save, rcp, validate,
				refreshArtifactsOnly, progressMonitor);

		if (dgraph != null) {
			//dgraph.setFacade1(new DGraphFactory(dgraph,this));
			dgraph.setFacade1(DGraphFactory.getInstance(dgraph,this));//FP150601azer
			if (LOG)
				for (DNode nod : dgraph.getFacade1().getNodes())
					clog("ROGDM " + nod.getName());
			return dgraph;
		}
		clog("Diagraph model is null");
		return null;
	}



	private void logError() {
		String msg = null;
		if (pakage != null) {
			if (pakage.getName() == null || pakage.getName().isEmpty())
				msg = "package without name in the ecore file"; // FP121122xx
			else
				msg = "(2) model must be under " + DParams.MODEL_FOLDER
						+ "/ error while checking ";
			log(1, msg);
			diagraphrunner.cerror(msg);
		} else {
			msg = "not diagraphed (1)";
			log(1, msg);
			diagraphrunner.cerror(msg);
		}
	}


	private boolean initializeParser(Diagram diag, boolean save,
			boolean atRuntime) {
		if (DParams.LanguageTransformer_4_LOG) {
			EPackage p = (EPackage) diag.getElement();
			clog4("AKW initializeParser=" + p.getName());
		}
		// if (CERROR)
		owner.change(selectedDiagram);
		try {
			if (this.selectedDiagram == null) {
				diagraphrunner.cinfo("initializeParser on "
						+ ((EPackage) diag.getElement()).getName()); // FP140703
			} else if (diag != this.selectedDiagram)
				diagraphrunner.cinfo("changed "
						+ ((EPackage) diag.getElement()).getName()); // FP140703
		} catch (Exception e) {
			System.err.println("INIPAR "+e.toString());
		}
		currentDiagraphedNode = null;
		diagraphmocks.clear();
		diagraphviews_.clear();
		pakage = (EPackage) diag.getElement();
		editingDomain = TransactionUtil.getEditingDomain(diag);
		this.atRuntime = atRuntime;
		this.save = save;
		this.selectedDiagram = diag;
		this.done = false;
		boolean result = ResourceUtils.checkDiagraphConsistence(editingDomain,pakage);

		return result;
	}

	private void parseDiagram(String from, String layer, Diagram diag,
			EObject ob) { // FP120923
		getDiagraphParser().begin(
				this.getClass().getSimpleName() + ".parseAtRuntimeAndLog()");
		parse(from + "dg5", layer, ob);
		log(1, "all nodes :");
		parse(from + "dg6", layer, diag.getChildren());
		if (LOG)
			clog("diagraph parsing start");
		parseGmfNodes(diag, layer);
		if (LOG)
			clog("diagraph parsing end");
		parseOtherAnnotations(layer);
		getDiagraphParser().end();
	}

	private void parseGmfNodes(Diagram diag, String layer) { // FP120923
		int childc = 0;
		for (Object c : diag.getChildren()) {
			if (c instanceof Node) {
				Node gmfnode = (Node) c;
				parseNode("gmfnode" + (childc++), layer, gmfnode, true, 1);
			} else
				log(1, c.getClass().getName());
		}
	}

	private void parseOtherAnnotations(String layer) { // FP120923
		log(1, "----- other annotations -----");
		for (EAnnotation annot : allAnnotations) {
			if (!diagraphAnnotations.contains(annot)) {
				log(1, annot.getSource());
				parseAnnotationDetails(annot, layer, 1);
			}
		}

	}



	@Override
	public void creport(List<String> log) {
		owner.creport(log);
	}


	private DGraph parseDiagram_(String from, Diagram diag, String layer,
			boolean forceGeneration, boolean save, boolean atRuntime,
			boolean rcp, boolean validate_, boolean refreshArtifactsOnly,
			IProgressMonitor progressMonitor) { // FP120528

		EPackage pack = null;
		try {
			pack = (EPackage) diag.getElement();
			owner.clearLog(1);
			editingDomain = TransactionUtil.getEditingDomain(diag);
			ResourceSet resourceSet = editingDomain.getResourceSet();
			if (progressMonitor != null) {
				progressMonitor.subTask("parseDiagram " + layer + " "
						+ ((EPackage) diag.getElement()).getName());
				progressMonitor.worked(1);
			}
			DGraph result = getOrGenerateDiagraphModel_(layer, resourceSet,
					forceGeneration, save, atRuntime, rcp, validate_,
					refreshArtifactsOnly, progressMonitor);


			if (diagraphrunner != null)
				diagraphrunner.setDGraph(result);
			if (result == null && !forceGeneration) {
				if (!atRuntime)
					clog(" diagram is not in a diagraphed context ");
				else
					clog(" parse error  ");// clog_(" not a layer...  ");
				return null;
			}
			EObject ob = diag.getElement();
			log(1, ob.getClass().getName());

			if (atRuntime)
				parseDiagram(from, layer, diag, ob);
			return result;
		} catch (Exception e) { // FP140108


			int errlen = e.getMessage().length();
			errlen = Math.min(DParams.MAX_ERRLEN, errlen);
			String error = e.getMessage().substring(0,errlen);

			diagraphrunner.cerror("error in parseDiagram: " + error);

			// if
			// (error.contains("The default value literal '' must be a valid literal")){

			// EPackage pack_ = currentDiagram==null?null:(EPackage)
			// currentDiagram.getElement(); //FP140411

			if (false && pack != null) { //FP150325
				// && error.equals("unknown error")
				String packerror = analyseError(pack);
				if (!packerror.equals("unknown error"))
					error += packerror;
			}

			log(1, "parseDiagram exception: " + error);
			owner.setParseErrorLog(error);
			diagraphrunner.setParseError(error);
			//String emesg = e.getMessage();
			if (atRuntime && error.startsWith("error with file"))
				throw new RuntimeException(error);
			if (atRuntime && error.startsWith("unable to find project"))
				throw new RuntimeException(error);
			if (atRuntime && !error.equals("unknown error"))
				throw new RuntimeException(error);
			diagraphrunner.cerror(error);
			return null;
		}
	}

	private String analyseError(EPackage pack) { // FP140227
		List<EClassifier> clazes = pack.getEClassifiers();
		for (EClassifier claz : clazes) {
			if (claz instanceof EClass) {
				EClass eClass = (EClass) claz;
				List<EStructuralFeature> feats = eClass
						.getEAllStructuralFeatures();
				for (EStructuralFeature eStructuralFeature : feats) {
					if (eStructuralFeature.getName().trim().isEmpty())
						return " feature name is empty for "
								+ eStructuralFeature.getName();
					if (eStructuralFeature instanceof EAttribute) {
						EAttribute attr = (EAttribute) eStructuralFeature;
						if (attr.getDefaultValueLiteral() != null
								&& attr.getDefaultValueLiteral().isEmpty())
							return " default value litteral is empty for "
									+ eStructuralFeature.getName();
					}
				}
			}
		}
		return "unknown error";
	}

	private String getStackTrace(Exception e) { // FP140108x
		String result = "";
		// InvocationTargetException ite =
		// (java.lang.reflect.InvocationTargetException) e;
		// Throwable exc = e.getTargetException();
		if (e != null) {
			if (e.getCause() != null)
				result += "cause: " + e.getCause().toString() + "\r\n";
			if (e.getMessage() != null)
				result += "message: " + e.getMessage().toString() + "\r\n";
			StackTraceElement[] ste = e.getStackTrace();
			result += "stacktrace: ";
			for (StackTraceElement stackTraceElement : ste) {
				result += stackTraceElement.toString() + "\r\n";
			}
		}
		return result;
	}




	@Override
	public int adapt_nu(Diagram changedDiag, IWorkbenchPart part,
			String layer_, ISelection selection, boolean save, boolean old) {
		clearLog(2);
		if (LOG)
			clog("---adapt---");

		if (DParams.LanguageTransformer_4_LOG) {
			EPackage p = (EPackage) changedDiag.getElement();
			clog4("AKW changedDiag=" + p.getName());
		}

		if (selection != null) {
			if (part instanceof org.eclipse.ui.part.PageBookView) {
				PageBookView pbk = (PageBookView) part;
				if (LOG)
					clog(pbk.getTitle()); // Outline

				if (part instanceof ContentOutline) {
					ContentOutline outl = (ContentOutline) part;
					if (selection instanceof StructuredSelection) {
						if (LOG)
							clog("#-+" + selection.getClass().getName());
						StructuredSelection ss = (StructuredSelection) selection;
						if (ss instanceof TreeEditPart) {
							TreeEditPart tep = (TreeEditPart) ss;
							if (LOG)
								clog("#-+" + tep.getModel().toString());
						}
					} else {
						TreeSelection trsel = (TreeSelection) selection;
						EObject sel = (EObject) trsel.getFirstElement();

						if (sel instanceof EObject) {
							EObject eo = (EObject) sel;
							if (LOG)
								clog("#+" + eo.getClass().getName());

							if (eo instanceof ENamedElement) {
								ENamedElement en = (ENamedElement) eo;
								if (LOG)
									clog(en.getName());
							}

						}
					}
				}
			}
		}

		// get the editing domain
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			// Walk selection
			for (Iterator i = structuredSelection.iterator(); i.hasNext();) {
				// Try to adapt the selection to a view
				Object selectedObject = i.next();
				if (selectedObject instanceof EObject) {
					EObject eobj = (EObject) selectedObject;
					if (eobj instanceof ENamedElement) {
						ENamedElement namd = (ENamedElement) eobj;
						log(3, "ENamedElement=" + namd.getName());
						if (LOG)
							clog("DGADPT " + "ENamedElement=" + namd.getName());
					} else if (eobj instanceof EAnnotation) {
						EAnnotation ann = (EAnnotation) eobj;
						log(3, " EAnnotation=" + ann.getSource());
						if (LOG)
							clog("DGADPT " + "EAnnotation=" + ann.getSource());
					} else {
						log(3, "EObject=" + eobj.toString());
						if (LOG)
							clog("DGADPT " + "EObject=" + eobj.toString());
					}
				}

				if (selectedObject instanceof IAdaptable) {
					View view = (View) ((IAdaptable) selectedObject)
							.getAdapter(View.class);
					if (view != null)
						return handleGmfElement(view, changedDiag, layer_,
								save, true, old);
				}
			}
		} else if (selection instanceof ISelection
				&& part instanceof EditorPart) {
			String docname = ((EditorPart) part).getTitle();
			log(3, "created=" + docname);
			if (LOG)
				clog("DGADPT " + docname);
		}

		if (selection != null && selection instanceof IStructuredSelection) {
			IStructuredSelection structured = (IStructuredSelection) selection;
			Object o = structured.getFirstElement();
			if (o != null && o instanceof IFile) {
				IFile file = (IFile) o;
				log(3, "file=" + file.getName());
				if (LOG)
					clog("DGADPT " + file.getName());
			}
		}
		return 5;
	}

	private void reset(Diagram diag) {
		setcurrentdiag(null);
		setdiagraph(null);
		setcurrentGraphElement(null);
		String mesg = "reset";
		if (diag != null) {
			try {
				mesg = "package " + ((EPackage) diag.getElement()).getName()
						+ " is not diagraphed (3)";
			} catch (Exception e) {
				mesg = "no package ...";
			}
		}
		if (LOG)
			clog(mesg);
		log(3, mesg);
	}

	private void init() {
		diagraphAnnotations.clear();
		allAnnotations.clear();
		diagraph = null;
		currentGraphElement = null;
		genLog = "";
		currentDiagraphedNode = null;
		// currentLayer_ = ViewConstants.DIAGRAPH_DEFAULT;
	}

	private boolean handleGmfDiagram(Diagram diag, Diagram changedDiag,
			String layer, boolean save, boolean atRuntime, boolean old) {
		if (changedDiag != null) {
			init();
			getDiagraphParser().begin(
					this.getClass().getSimpleName() + ".handleGmfDiagram()");
		}
		if (initializeParser(diag, save, atRuntime)) {
			if (diagraph == null)
				selectDiagraph(diag, null, layer, save);// , old
			currentDiagraphedNode = findCurrentOrRootDiagraphedNode(diag);
			if (currentDiagraphedNode != null) {
				newNode_("hgd_", layer, currentDiagraphedNode);
				return true;
			}
		}
		reset(diag);
		return false;
	}

	private int handleGmfElement(View view, Diagram changedDiag, String layer,
			boolean save, boolean atRuntime, boolean old) {
		boolean iod = view instanceof Diagram;
		if (iod
				&& !handleGmfDiagram((Diagram) view, changedDiag, layer, save,
						atRuntime, old)) // diagram selection with
			return 2;
		if (!iod) {
			if (!handleGmfView(view, changedDiag, layer, save, atRuntime, old)) // diagram
				return 1;
			if (!selectDiagraph(view.getDiagram(), changedDiag, layer, save)) // node
				return 4;
		}
		return -1;
	}

	private boolean handleGmfView(View view, Diagram changedDiag,
			String layer, boolean save, boolean atRuntime, boolean old) {
		if (changedDiag != null) {
			init();
			getDiagraphParser().begin(
					this.getClass().getSimpleName() + ".handleGmfView()");
		}
		Diagram diag = view.getDiagram();
		if (diag.getElement() instanceof EPackage) {
			if (atRuntime || initializeParser(diag, save, atRuntime)) {
				if (currentDiagram == null && diag != null && diagraph == null)
					selectDiagraph(diag, null, layer, save);// , old
				setcurrentdiag(diag);
				if (LOG)
					clog("diagram element");
				clogDiagraph_(currentDiagram);
				if (view instanceof Node)
					newNode_("hgv_", layer, (Node) view);
				else if (view instanceof Edge)
					nothing((Edge) view);
				else
					nothing(null);
			} else {
				reset(diag);
				return false;
			}
		}
		return true;
	}

	private void nothing(Edge edge) {
		try {
			if (edge != null)
				clog("edge:" + edge.getElement() != null ? edge.getElement()
						.toString() : "");
			else
				clog("nothing");
		} catch (Exception e) {
			clog("error in nothing");
		}
	}

	private void setcurrentGraphElement(DGraphElement graphElement) {
		currentGraphElement = graphElement;
	}

	private void setdiagraph(DGraph dGraph) {
		String oldd = "null";
		String newd = "null";
		try {
			if (diagraph != null)
				oldd = diagraph.getPointOfView().getSemanticRole().getName();

		} catch (Exception e) {
			if (diagraph != null && diagraph.getPointOfView() != null) // FP130623
				oldd = diagraph.getPointOfView().getName();
		}
		diagraph = dGraph;
		try {
			if (diagraph != null)
				newd = diagraph.getPointOfView().getSemanticRole().getName();
		} catch (Exception e) {
			if (diagraph != null && diagraph.getPointOfView() != null) // FP130623
				newd = diagraph.getPointOfView().getName();
		}
		if (diagraph.getPointOfView() == null)
			clog("!!! pov = null !!!");
		if (LOG)
			clog("current diagraph:" + oldd + "->" + newd);
	}

	private Node findCurrentOrRootDiagraphedNode(Diagram diag) {
		Node currentDiagraphedNode = findCurrentDiagraphedNode(diag);
		if (currentDiagraphedNode != null)
			clog("new -current- node from diagram");
		else {
			currentDiagraphedNode = findRootPointOfView(diag);
			if (currentDiagraphedNode != null)
				clog("new -first- node from diagram");
			else
				clog("no node from diagram");
		}
		clogDiagraph_(diag);
		return currentDiagraphedNode;
	}

	private Node findRootPointOfView(Diagram diag) {
		if (diag.getElement() instanceof EPackage) {
			for (Object ch : diag.getPersistedChildren()) {
				if (ch instanceof Node) {
					Node chNode = (Node) ch;
					EObject el = chNode.getElement();
					if (currentGraphElement != null)
						if (LOG)
							clog(currentGraphElement.getName());
					if (el instanceof EClass) {
						for (EAnnotation eAnnotation : ((EClass) el)
								.getEAnnotations()) {
							if (eAnnotation.getSource().equals(
									GenericConstants.ANNOT_DIAGRAPH_LITTERAL)) {
								for (Map.Entry<String, String> entry : eAnnotation
										.getDetails())
									if (entry.getKey() != null
											&& entry.getKey().equals("pov"))
										return chNode;
							}
						}
					}
				}
			}
		}
		return null;
	}

	private Node findCurrentDiagraphedNode(Diagram diag) {
		if (diag.getElement() instanceof EPackage) {
			for (Object ch : diag.getPersistedChildren()) {
				if (ch instanceof Node) {
					Node chNode = (Node) ch;
					EObject el = chNode.getElement();
					if (el instanceof EClass) {
						EClass eClass = (EClass) el;
						for (EAnnotation eAnnotation : eClass.getEAnnotations()) {
							if ((eAnnotation.getSource()
									.equals(GenericConstants.ANNOT_DIAGRAPH_LITTERAL))
									&& (currentGraphElement != null && currentGraphElement
											.getName().equals(eClass.getName())))
								return chNode;
						}
					}
				}
			}
		}
		return null;
	}

	private void clogDiagraph_(Diagram diag) {
		TransactionalEditingDomain ted_ = TransactionUtil
				.getEditingDomain(diag);
		ResourceSet rs = ted_.getResourceSet();
		if (LOG)
			for (Resource resource : rs.getResources())
				clog(resource.getURI().toString());
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	private void newNode_(String from, String view, Node gmfnode) {
		parse("dg7", view, gmfnode);
		parseNode(from, view, gmfnode, true, 2);
		parseEnd();
	}

	private IRuntimeDiagraphParser getDiagraphParser() {
		if (parser == null) {
			parser = new DiagraphParserConnector().getRuntimeDiagraphParser();
			if (parser == null)
				throw new RuntimeException(
						"IRuntimeDiagraphParser should not be null !!!"); // FP130723
			parser.init(owner, this);
		}
		return parser;
	}

	private void parseEnd() {
		getDiagraphParser().parseEnd();
	}

	@Override
	public String[] getViews(boolean force) {// FP121014
		try {
			if (allAnnotations.size() == 0 || force)
				allAnnotations = getDiagraphParser().parseDiagraphAnnotations(
						pakage); // FP13
			getDiagraphParser().parseViews(allAnnotations);
			String[] viouzes = getDiagraphParser().getViewIds();
			if (LOG) {
				String log = "";
				for (String viu : viouzes)
					log += viu + ";";
				clog("getViews:" + log);
			}
			return viouzes;
		} catch (Exception e) {
			// throw new RuntimeException("parse error 3 !!!"); //FP121111
			diagraphrunner.cerror("parse error 3a !!!"); // FP121111
			String[] result = new String[1];
			result[0] = ViewConstants.DIAGRAPH_DEFAULT;
			return result; // FP121116
		}
	}

	private DGraphElement parse(String from, String view, Object o) {
		IRuntimeDiagraphParser rtparser = getDiagraphParser();
		// rtparser.clear();
		// rtparser.setCurrentView("default");
		rtparser.setCurrentView(view); // FP140603modif
		// rtparser.setDiagraphNotifiable(diagraphNotifiable);
		// this.diagraphNotifiable
		DGraphElement result = rtparser.parse(from, o);
		DNode current_ = rtparser.getCurrent();
		// DGraph graph_ = rtparser.getGraph_(); //FP140115x
		// rtparser.end();
		// if (result != null && (!current.getName().equals(result.getName())))
		// clog(result.getName()+"---->"+current.getName());
		return result;
	}

	private String logClasses(Diagram diag) {
		String content = "";
		if (diag != null)
			for (Object child : diag.getPersistedChildren())
				content += ((((Node) child).getElement()) instanceof EClass) ? (((EClass) (((Node) child)
						.getElement())).getName() + "  ") : "";
		else
			content = "null";
		content = content.trim();
		if (LOG)
			clog(content);
		return content;
	}

	@Override
	public String generateDiagraphwold(String from, boolean forceGeneration,
			Object object, String layer, boolean save, Diagram diag,
			boolean atRuntime, IProgressMonitor nullProgressMonitor,
			boolean rcp, boolean validate, boolean refactor,// boolean old,
			boolean refreshArtifactsOnly) {
		if (diag == null) {
			if (LOG)
				clog(DParams.NO_DIAGRAM_ERROR);
			return DParams.NO_DIAGRAM_ERROR;
		} else
			return generateDiagraph(from, forceGeneration, null, layer, save,
					diag, atRuntime, rcp, validate, refactor, // old,
					refreshArtifactsOnly, nullProgressMonitor);
	}

	private DGraph parseConcreteSyntaxOldGen(String from, Diagram diagram,
			String layer, boolean save, boolean forceParse, boolean rcp,
			boolean validate, IProgressMonitor progressMonitor) {
		if (LOG)
			clog("parseConcreteSyntaxOldGen");
		if (CERROR)
			diagraphrunner.cerror("parseConcreteSyntaxOldGen");
		DGraph result = null;
		boolean refreshArtifactsOnly = true;
		initializeGeneration(diagram, layer, forceParse);
		workdiagram = diagram;
		if (initializeParser(workdiagram, save, forceParse))
			result = parseDiagram_(from, workdiagram, layer, false, save,
					forceParse, rcp, validate, refreshArtifactsOnly,
					progressMonitor);
		else
			clog(DParams.PRJ_CONSTRAINT_ERROR);
		parseEnd(diagram, result);
		return result;
	}

	public String generateDiagraph(String from, boolean forceGeneration,
			GenModel genmodel, String layer, boolean save, Diagram diag,
			boolean atRuntime, boolean rcp, boolean validate, boolean refactor,
			boolean refreshArtifactsOnly, IProgressMonitor progressMonitor) {// boolean

		this.refactor = refactor;
		parser.checkAnnotations(layer);// FP140430
		if (currentDiagram == null)
			parseConcreteSyntaxOldParser(from, diag, layer, save, atRuntime,
					rcp, validate, refactor, progressMonitor);// old,
		if (LOG)
			logClasses(diag); // FP131207
		if (!initializeParser(diag, save, atRuntime)) {
			if (LOG)
				clog(DParams.PRJ_CONSTRAINT_ERROR);
			return DParams.PRJ_CONSTRAINT_ERROR;
		}
		if (parseDiagram_(from, diag, layer, forceGeneration, save, atRuntime,
				rcp, validate, refreshArtifactsOnly, progressMonitor) == null) {
			String msg = "parse error (1) in diagraph: "
					+ diagraphrunner.getParseError() == null ? " (unknown) "
					: diagraphrunner.getParseError();
			if (LOG)
				clog(msg);
			return msg;
		}
		return "";
	}

	private void alert(String message) {
		owner.alert(headless, message); // FP140202
		/*
		 * if (!headless)
		 * MessageDialog.openInformation(PlatformUI.getWorkbench()
		 * .getActiveWorkbenchWindow().getShell(), "Diagraph", message); else{
		 *
		 * if (LOG) clog_(message); else diagraphrunner.cerror(message); }
		 */
	}

	private String diagramToString(Diagram diag) {
		String result = "";
		try {
			List<Edge> edges = diag.getEdges();
			for (Edge edge : edges) {
				View v = edge.getSource();
				if (v instanceof Node) {
					Node n = (Node) v;
					EModelElement me = (EModelElement) n.getElement();
					if (me != null && me instanceof ENamedElement) {
						ENamedElement ne = (ENamedElement) me;
						if (!result.contains(ne.getName() + ";"))
							result += (ne.getName() + ";");
					}
				}
			}
			return result;
		} catch (Exception e) {
			return "error in diagramToString " + result;
		}
	}

	private void setcurrentdiag(Diagram diag) {
		String oldd = "null";
		String newd = "null";

		if (DParams.LanguageTransformer_4_LOG) {
			EPackage p = (EPackage) diag.getElement();
			clog4("AKW setcurrentdiag=" + p.getName());
		}

		try {
			if (currentDiagram != null)
				oldd = ((EPackage) currentDiagram.getElement()).getName();
		} catch (Exception e) {
			oldd = "not a diagraphed diagram";
		}
		currentDiagram = diag; // FP120528b //FP120515
		try {
			if (currentDiagram != null)
				newd = ((EPackage) currentDiagram.getElement()).getName();
		} catch (Exception e) {
			newd = "not a diagraphed diagram";
		}
		if (DParams.LanguageTransformer_4_LOG)
			clog4("current diagram:" + oldd + "->" + newd);

	}

	@Override
	public void endParsed() {
		owner.endParsed(currentDiagram);
		boolean force = true;
		views = getViews(force);

		String lang = ((EPackage) selectedDiagram.getDiagram().getElement())
				.getName();
		String currentview = getCurrentView(1, lang, this.getClass().getName()); // FP140611
		// if
		// (!currentview.equals(org.isoe.diagraph.views.ViewConstants.DIAGRAPH_ALL_VIEW_LITTERAL))
		owner.setViews(this, 1, lang, views, currentview); // FP140611 //
															// FP121014xxxc
	}

	private boolean startParser(Diagram diagram) {
		parser.parseStart();
		if (currentDiagram != null && diagram != currentDiagram) {
			parser.begin(this.getClass().getSimpleName() + ".startParser()");
			return true;
		}
		return false;
	}

	public List<DGraph> doSelectDiagraph(Diagram diagram, String currentLayer,
			boolean save, boolean atRuntime, boolean rcp, boolean validate,
			// boolean old,
			IProgressMonitor progressMonitor) {
		List<DGraph> result = parseConcreteSyntaxOldParser("sda_", diagram,
				currentLayer, save, atRuntime, rcp, validate, refactor, // old,
				progressMonitor);
		return result;
	}

	private void log(Diagram diagram) {
		if (DParams.LanguageTransformer_4_LOG) {
			String wd = (workdiagram != null) ? diagramToString(workdiagram)
					: "";
			String cd = (currentDiagram != null) ? diagramToString(currentDiagram)
					: "";
			String od = "";// (oldDiag_!=null)?diagramToString(oldDiag_):"";
			String nd = (diagram != null) ? diagramToString(diagram) : "";
			clog4("*************************   " + od + " ** " + nd);
			clog4("+++++++++++++++++++++++++   " + wd + " ++ " + cd);
		}

	}

	private void initializeGeneration(Diagram diagram, String view,
			boolean forceParse) {
		if (DParams.LanguageTransformer_4_LOG)
			log(diagram);
		if (CERROR)
			diagraphrunner.cerror("initializeGeneration");
		diagraphAnnotations.clear();
		allAnnotations.clear();
		getDiagraphParser().begin(
				this.getClass().getSimpleName()
						+ ".selectAndParseNewDiagraph()");
		if (forceParse)
			parse("dg8", view, diagram);
	}

	private void parseEnd(Diagram diagram, DGraph dGraph) {
		if (CERROR)
			diagraphrunner.cerror("parseEnd");
		if (dGraph != null)
			setdiagraph(dGraph);
		parseEnd();
		getDiagraphParser().end();
		if (dGraph == null && !atRuntime)
			getDiagraphRunnerDiagraphLog().add("not in a diagraph context");
		if (dGraph != null)
			setcurrentdiag(diagram);
	}

	/**
	 *
	 * @param from
	 * @param diagram
	 * @param currentLayer
	 * @param save
	 * @param forceParse
	 * @param rcp
	 * @param progressMonitor
	 * @return the views
	 */
	private List<DGraph> parseConcreteSyntax_old_(String from, Diagram diagram,
			String layer, boolean save, boolean forceParse, boolean rcp,
			boolean validate, boolean refactor, boolean old,
			IProgressMonitor progressMonitor) {
		if (DParams.LanguageTransformer_4_LOG) {
			EPackage p = (EPackage) diagram.getElement();
			clog4("AKW (" + from + ") parseConcreteSyntax for " + p.getName());
		}

		this.refactor = refactor;// FP140124
		DGraph parsed = null;
		if (old)
			parsed = parseConcreteSyntaxOldGen(from, diagram, layer, save,
					forceParse, rcp, validate, progressMonitor);
		List<DGraph> alt = null;
		List<DGraph> result_ = new ArrayList<DGraph>();

		if (parsed != null) {
			result_.add(parsed);
			if (DParams.TextGen6_LOG) {
				clog6("parsed(old) "
						+ ((EPackage) diagram.getElement()).getName() + "."
						+ parsed.getViewName());
			}
		} else if (old)
			if (LOG)
				clog("parseConcreteSyntax: parse error with oldGen");

		try {
			alt = parseConcreteSyntaxNewGen(from, diagram, layer, save,
					forceParse, rcp, progressMonitor);
			if (alt != null && !alt.isEmpty()) {
				for (DGraph g : alt) {
					if (DParams.TextGen6_LOG)
						clog6("parsed(new) " + g.getViewName());
				}

			}

		} catch (Exception e) {
			diagraphrunner.cerror("error while parsing with new parser ("
					+ parsed.getPointOfView().getName() + ")");
		}
		// TODO refactor (reengineer) with the new parser
		if (LOG) {
			clog("concrete syntax parsed - current:"
					+ (parsed == null ? " parse error " : parsed
							.getPointOfView().getName()));
			if (alt != null)
				clog("concrete syntax parsed with new parser:"
						+ alt.get(0).getPointOfView().getName());
		}
		if (!old && alt != null) {
			result_ = alt;

		}
		return result_;
	}

	private List<DGraph> parseConcreteSyntaxOldParser(String from,
			Diagram diagram, String layer, boolean save, boolean forceParse,
			boolean rcp, boolean validate, boolean refactor,
			IProgressMonitor progressMonitor) {
		if (DParams.LanguageTransformer_4_LOG) {
			EPackage p = (EPackage) diagram.getElement();
			clog4("AKW (" + from + ") parseConcreteSyntax for " + p.getName());
		}
		if (CERROR)
			diagraphrunner.cerror("parseConcreteSyntaxOldParser");
		this.refactor = refactor;// FP140124
		DGraph parsed = parseConcreteSyntaxOldGen(from, diagram, layer, save,
				forceParse, rcp, validate, progressMonitor);
		List<DGraph> alt = null;
		List<DGraph> result = new ArrayList<DGraph>();

		if (parsed != null) {
			result.add(parsed);
			if (CERROR)
				diagraphrunner
						.cerror("parseConcreteSyntax: parse ok with OldParser");
			if (DParams.TextGen6_LOG)
				clog6("parsed(old) "
						+ ((EPackage) diagram.getElement()).getName() + "."
						+ parsed.getViewName());
		} else
			diagraphrunner
					.cerror("parseConcreteSyntax: parse error with OldParser");
		// TODO refactor (reengineer) with the new parser
		if (LOG) {
			clog("concrete syntax parsed - current:"
					+ (parsed == null ? " parse error " : parsed
							.getPointOfView().getName()));
			if (alt != null)
				clog("concrete syntax parsed with new parser:"
						+ alt.get(0).getPointOfView().getName());
		}

		return result;
	}

	private List<DGraph> parseConcreteSyntaxNewParser(String from,
			Diagram diagram, String layer, boolean save, boolean forceParse,
			boolean rcp, boolean validate, boolean refactor,
			IProgressMonitor progressMonitor) {
		if (DParams.LanguageTransformer_4_LOG) {
			EPackage p = (EPackage) diagram.getElement();
			clog4("AKW (" + from + ") parseConcreteSyntax for " + p.getName());
		}

		this.refactor = refactor;// FP140124
		List<DGraph> result = null;
		try {
			result = parseConcreteSyntaxNewGen(from, diagram, layer, save,
					forceParse, rcp, progressMonitor);
			if (DParams.TextGen6_LOG)
				if (result != null && !result.isEmpty())
					for (DGraph g : result)
						clog6("parsed(new) " + g.getViewName());

		} catch (Exception e) {
			diagraphrunner.cerror("error while parsing with new parser ("
					+ ((EPackage) diagram.getElement()).getName() + ")");
		}
		// TODO refactor (reengineer) with the new parser
		if (LOG) {
			// clog("concrete syntax parsed - current:"
			// + (parsed == null ? " parse error " : parsed
			// .getPointOfView().getName()));
			if (result != null)
				clog("concrete syntax parsed with new parser:"
						+ result.get(0).getPointOfView().getName());
		}

		return result;
	}

	private List<DGraph> parseConcreteSyntaxNewGen(String from,
			Diagram diagram, String currentLayer, boolean save,
			boolean forceParse, boolean rcp, IProgressMonitor progressMonitor) {
		if (LOG)
			clog("parseConcreteSyntaxNewGen");
		return owner.newParse(diagram);// FP130725
	}

	private boolean selectDiagraph(Diagram diag, Diagram changedDiag,
			String layer, boolean save) {
		boolean atRuntime = true;
		boolean rcp = false;
		if (changedDiag != null) {
			init();
			getDiagraphParser().begin(
					this.getClass().getSimpleName() + ".selectDiagraph()");
		}
		if (workdiagram == null || workdiagram != currentDiagram) {
			if (currentDiagram == null) {
				IProgressMonitor nullProgressMonitor = new NullProgressMonitor(); // FP121017c
				parseConcreteSyntaxOldParser("dgn_", diag, layer, save,
						atRuntime, rcp, false, refactor,
						// old,
						nullProgressMonitor);
			}
			return false;
		}
		return true;
	}

	@Override
	public int getPhase() {
		if (diagraphrunner != null)
			return diagraphrunner.getPhase();
		else
			return 999;
	}

	@Override
	public List<EAnnotation> getAllAnnotations() { // FP121014
		return allAnnotations;
	}

	@Override
	public int getViewId() {
		return 0;
	}

	@Override
	public String getMetamodelResourcepath() {
		return metamodelResourcepath;
	}

	@Override
	public String[] parseViews(List<EAnnotation> allAnnotations) {
		return getDiagraphParser().parseViews(allAnnotations);
	}

	static int errid;

	private String getErrorPath() {
		File here = new File(".");
		String path = here.getAbsolutePath();// C:\workspaces\vobeo1\fr.obeo.formation.ales.uml\.
		path = path.substring(0, path.length() - 1);
		path += "_" + errid++ + "_2_error.txt";
		return path;
	}

	private void writeErrorInFile(String content) {
		if (content != null && !content.isEmpty()) {
			FileWriter fw;
			try {
				fw = new FileWriter(new File(getErrorPath()), false);
				fw.write(content);
				fw.close();
			} catch (IOException e) {
				System.err.println("error " + e.toString());
			}
		} else
			System.err.println("nothing to save ");
	}

	public void showDebugMessage(String message) {

		MessageDialog.openInformation(PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getShell(), "Debug", message);

	}

	@Override
	public DGraph selectDiagraphByView(
			EPackage pak, // FP140603
			String view,
			Diagram diagram, // FP140522String currentLayer,
			boolean save, boolean atRuntime, boolean rcp, boolean validate,
			boolean headless,
			// boolean old,
			IProgressMonitor progressMonitor) {
		int idx = 0;
		if (diagraphrunner == null)
			writeErrorInFile("diagraphrunner==null in selectDiagraphByView");

		if (CERROR)
			diagraphrunner.cerror("selectDiagraphByView step=" + idx);
		try {

			if (DParams.TextGen6_LOG) {
				String log1 = ((EPackage) diagram.getElement()).getName() + "."
						+ view;
				String log2 = pak == null ? " - " : " - " + pak.getName();
				clog6("AKW selectDiagraph " + log1 + "-" + log2);
			}


			List<DGraph> result_ = parseConcreteSyntaxOldParser("sd33_",
					diagram, view, save, atRuntime, rcp, validate, refactor,
					// old,
					progressMonitor);


			idx = 1;
			if (CERROR)
				diagraphrunner.cerror("selectDiagraphByView step=" + idx);

			if (result_ == null) {
				if (DParams.TextGen6_LOG)
					clog6("AKW dgraph parseConcreteSyntax NULL");
				diagraphrunner.cerror("AKW dgraph parseConcreteSyntax NULL");
				idx = 2;

			} else if (result_.isEmpty()) {
				if (DParams.TextGen6_LOG)
					clog6("AKW dgraph parseConcreteSyntax empty");
				diagraphrunner.cerror("AKW dgraph parseConcreteSyntax empty");
				idx = 3;

			} else {
				if (result_.size() > 1) {
					diagraphrunner
							.cerror("should not happen in selectDiagraph2");
					throw new RuntimeException(
							"should not happen in selectDiagraph2");
				}
				if (CERROR)
					diagraphrunner.cerror("selectDiagraphByView step=" + idx);
				String m = "SDGBVpb1";
				try {
					m = "AKW parsed dgraph "
							+ result_.get(0).getPointOfView().getEClaz()
									.getEPackage().getName() + "."
							+ result_.get(0).getViewName();
				} catch (Exception e) {
					diagraphrunner.cerror(e.toString());
				}
				idx = 4;
				if (CERROR)
					diagraphrunner.cerror(m);
				if (DParams.TextGen6_LOG)
					clog6(m);
				idx = 5;
				if (CERROR)
					diagraphrunner.cerror("selectDiagraphByView step=" + idx);
				DGraph res = result_.get(0);
				idx = 6;
				if (CERROR)
					diagraphrunner.cerror("selectDiagraphByView step=" + idx);
				return res;
			}

		} catch (Exception e) {
			diagraphrunner.cerror("error in selectDiagraphByView idx=" + idx
					+ " " + e.toString());
			return null;
		}
		diagraphrunner.cerror("null in selectDiagraphByView idx=" + idx);
		// }
		return null;
	}

	private void clog6(String mesg) {
		if (DParams.TextGen6_LOG)
			System.out.println(mesg);
	}

	private void clog4_old(String mesg) {
		if (DParams.LanguageTransformer_4_LOG)
			System.out.println(mesg);
	}

	private void clog4(String mesg) {
		if (DParams.LanguageTransformer_4_LOG) { // && !silent
			if (mesg == null)
				mesg = "null";
				System.out.println(mesg);
		}
	}

	@Override
	public void logBeforeExec(String log, List<String> keywordFilters) {
		diagraphrunner.logBeforeExec(log, keywordFilters);

	}

	@Override
	public void logAfterExec(String log, List<String> keywordFilters) {
		diagraphrunner.logAfterExec(log, keywordFilters);
	}

	@Override
	public List<String> getLog() {
		return diagraphrunner.getLog();
	}

	@Override
	public List<String> getLogDiagraph() {
		return diagraphrunner.getLogDiagraph();
	}

	@Override
	public boolean runGenNotation(String layer, boolean refactor_,
			boolean headless, boolean refreshOnly) { // FP130417
		IWorkbenchPartSite site = ((ViewPart) this.owner).getSite();
		this.headless = headless;
		if (genNotationAction == null)
			// genNotationAction = new GenNotationAction((IDiagraphInvoker)
			// owner,
			// site);
			genNotationAction = new GenNotationAction(this, site);
		genNotationAction.init_(this, layer, refactor_, refreshOnly);// FP130417
		// diagraphrunner);
		genNotationAction.run();
		if (!genNotationAction.getLog().isEmpty()
				&& !genNotationAction.getLog().equals("null")) {
			alert(genNotationAction.getLog()); // FP131004
			return false;
		}
		return true;
	}

	public String getParseError() {
		return diagraphrunner.getParseError();
	}

	public void setParseError(String error) {
		diagraphrunner.setParseError(error);
	}

	@Override
	public DiagramEditor findActiveGenericDiagramEditor() {
		IWorkbenchPage workbenchPage = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		IEditorPart editorPart = workbenchPage.getActiveEditor();
		if (editorPart == null) {
			diagraphrunner.cerror(" no editor opened");
			return null;
		}
		IWorkbenchPart workbenchPart = editorPart.getEditorSite().getPart();
		if (!(workbenchPart instanceof EcoreDiagramEditor)) {
			workbenchPage.saveAllEditors(false);
			if (LOG)
				clog(" opened GenericDiagramEditor: "
						+ workbenchPart.toString());
			return (DiagramEditor) workbenchPart;
		} else {
			if (LOG)
				clog(" opened Editor is not a GenericDiagramEditor: "
						+ workbenchPart.toString());
			return null;
		}
	}

	@Override
	public EcoreDiagramEditor findActiveEcoreEditor(String msg) { // FP120621
		IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		if (page.getActiveEditor() == null) {
			if (msg != null && !msg.isEmpty())
				alert(msg);
			diagraphrunner.cinfo(" no editor opened");
			return null;
		}
		IWorkbenchPart workbenchPart = page.getActiveEditor().getEditorSite()
				.getPart();
		if (workbenchPart instanceof EcoreDiagramEditor) {
			page.saveAllEditors(false);
			return (EcoreDiagramEditor) workbenchPart;
		} else {
			if (LOG)
				clog(" (1)opened Editor is not a n EcoreDiagramEditor: "
						+ workbenchPart.toString());
			return closeOtherEditorAndOpenFirstEcoreDiagram(page);
		}
	}

	private EcoreDiagramEditor closeOtherEditorAndOpenFirstEcoreDiagram(
			IWorkbenchPage page) { // FP140618
		if (!(page.getActiveEditor().getEditorSite().getPart() instanceof EcoreDiagramEditor))
			page.closeEditor((EditorPart) (page.getActiveEditor()
					.getEditorSite().getPart()), false);
		for (IEditorReference ref : page.getEditorReferences()) {// FP140618
			IEditorPart editor = ref.getEditor(false);
			if (editor instanceof EcoreDiagramEditor) {
				EcoreDiagramEditor ecoreDiagramEditor = (EcoreDiagramEditor) editor;
				page.bringToTop(ecoreDiagramEditor);
				if (LOG)
					clog(" (1)opened Editor is not now the first EcoreDiagramEditor: "
							+ ecoreDiagramEditor.toString());
				return ecoreDiagramEditor;
			}
		}
		return null;
	}

	@Override
	public boolean isFunctional() {
		return true;
	}

	@Override
	public boolean isStub() {
		return false;
	}

	@Override
	public String getMetadata(String id) {
		return owner.getMetadata(id);
	}

	@Override
	public void setNotifiable(DiagraphNotifiable diagraphNotifiable) {
		this.diagraphNotifiable = diagraphNotifiable;
	}

	@Override
	public List<DGraph> selectDiagraphNewParserTodo(EPackage pak_nu,
			Diagram diagram, boolean save, boolean atRuntime, boolean rcp,
			boolean validate, IProgressMonitor progressMonitor) {
		String lang = ((EPackage) diagram.getElement()).getName();
		String currentview = getCurrentView(2, lang, this.getClass().getName()); // FP140611
		if (DParams.LanguageTransformer_4_LOG) {

			String log1 = ((EPackage) diagram.getElement()).getName() + "."
					+ currentview;// currentLayer_;
			String log2 = pak_nu == null ? " - " : " - " + pak_nu.getName();// +"."+currentview;//currentLayer_;

			clog4("(new)AKW selectDiagraph " + log1 + "-" + log2); // FP140522
		}

		List<DGraph> result = parseConcreteSyntaxNewParser("sd2_new", diagram,
				currentview, save, atRuntime, rcp, validate, refactor,
				// old,
				progressMonitor);
		if (DParams.LanguageTransformer_4_LOG) {
			if (result == null)
				clog4("(new)AKW dgraph parseConcreteSyntax NULL");
			else if (result.isEmpty())
				clog4("(new)AKW dgraph parseConcreteSyntax empty");
			else
				for (DGraph dGraph : result)
					clog4("(new)AKW dgraph "
							+ dGraph.getViewName()
							+ "."
							+ dGraph.getPointOfView().getEClaz().getEPackage()
									.getName());
		}
		return result;
	}

	@Override
	public List<DGraph> selectDiagraph(EPackage pak_nu_, Diagram diagram, // FP140522String
																			// currentLayer,
			boolean save, boolean atRuntime, boolean rcp, boolean validate,
			// boolean old,
			IProgressMonitor progressMonitor) {

		if (CERROR)
			diagraphrunner.cerror("selectDiagraph ");
		String lang = ((EPackage) diagram.getElement()).getName();
		if (CERROR)
			diagraphrunner.cerror("lang= " + lang);

		String currentview = getCurrentView(3, lang, this.getClass().getName()); // FP140611
		if (CERROR)
			diagraphrunner.cerror("view= " + currentview);

		if (DParams.LanguageTransformer_4_LOG) {

			String log1 = ((EPackage) diagram.getElement()).getName() + "."
					+ currentview;// currentLayer_;
			String log2 = pak_nu_ == null ? " - " : " - " + pak_nu_.getName();// +"."+currentview;//currentLayer_;

			clog4("(old)AKW selectDiagraph " + log1 + "-" + log2); // FP140522
		}

		List<DGraph> result = parseConcreteSyntaxOldParser("sd2_old", diagram,
				currentview, save, atRuntime, rcp, validate, refactor,
				// old,
				progressMonitor);
		if (DParams.LanguageTransformer_4_LOG) {
			if (result == null)
				clog4("(old)AKW dgraph parseConcreteSyntax NULL");
			else if (result.isEmpty())
				clog4("(old)AKW dgraph parseConcreteSyntax empty");
			else
				for (DGraph dGraph : result)
					clog4("(old)AKW dgraph "
							+ dGraph.getViewName()
							+ "."
							+ dGraph.getPointOfView().getEClaz().getEPackage()
									.getName());
		}
		return result;
	}

	@Override
	public boolean mustThrowExceptions() {
		return diagraphNotifiable.mustThrowExceptions();

	}

	@Override
	public void cerror(String mesg) {
		owner.cerror(mesg);
	}

	@Override
	public void cinfo(String mesg) {
		owner.cinfo(mesg);
	}

	@Override
	public void cwarn(String mesg) {
		owner.cwarn(mesg);
	}

	@Override
	public void setOwner(ViewPart owner) {
		this.owner = (DiagraphNotifiable) owner;

	}



	@Override
	public void initialize() {
		createNotation(this);
		this.diagraphrunner = new DiagraphRunner(this, notation, null, true);// FP140507

	}



	@Override
	public boolean isLayouting() {
		return owner.isLayouting();
	}



	@Override
	public Object callBack(Object o) {
		// TODO Auto-generated method stub
		return null;
	}












}
