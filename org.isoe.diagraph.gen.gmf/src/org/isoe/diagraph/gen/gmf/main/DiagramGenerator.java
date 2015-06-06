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
package org.isoe.diagraph.gen.gmf.main;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gmf.codegen.gmfgen.FeatureLinkModelFacet;
import org.eclipse.gmf.codegen.gmfgen.GenDiagram;
import org.eclipse.gmf.codegen.gmfgen.GenEditorGenerator;
import org.eclipse.gmf.codegen.gmfgen.GenLink;
import org.eclipse.gmf.codegen.gmfgen.TypeLinkModelFacet;
import org.eclipse.gmf.gmfgraph.Canvas;
import org.eclipse.gmf.mappings.Mapping;
import org.eclipse.gmf.tooldef.AbstractTool;
import org.eclipse.gmf.tooldef.BundleImage;
import org.eclipse.gmf.tooldef.CreationTool;
import org.eclipse.gmf.tooldef.Image;
import org.eclipse.gmf.tooldef.ToolGroup;
import org.eclipse.gmf.tooldef.ToolRegistry;
//import org.isoe.diagraph.checker.MetamodelChecker;
import org.isoe.diagraph.common.IconCopier;
import org.isoe.diagraph.diagraph.DNestedEdge;
import org.isoe.diagraph.diagraph.DOwnedEdge;
import org.isoe.diagraph.diagraph.DEdge;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.diagraph.DGraphElement;
import org.isoe.diagraph.diagraph.DNavigationEdge;
import org.isoe.diagraph.diagraph.DNode;
import org.isoe.diagraph.diagraph.DReference;
import org.isoe.diagraph.diagraph.DSimpleEdge;
import org.isoe.diagraph.diastyle.DColor;
import org.isoe.diagraph.diastyle.DEdgeStyle;
import org.isoe.diagraph.diastyle.DLayout;
import org.isoe.diagraph.diastyle.DNodeStyle;
import org.isoe.diagraph.diastyle.DStyle;
import org.isoe.diagraph.diastyle.DStyleBridge;
import org.isoe.diagraph.diastyle.helpers.StyleHandler;
import org.isoe.diagraph.gen.gmf.GenModelGenerator;
import org.isoe.diagraph.gen.gmf.GmfGenGenerator;
import org.isoe.diagraph.gen.gmf.GmfGraphGenerator;
import org.isoe.diagraph.gen.gmf.GmfMapGenerator;
import org.isoe.diagraph.gen.gmf.GmfMappings;
import org.isoe.diagraph.gen.gmf.GmfToolGenerator;
import org.isoe.diagraph.gen.gmf.IPaletteManager;
import org.isoe.diagraph.gen.gmf.PaletteManager;
import org.isoe.diagraph.gmf.templates.DiagraphTemplates;
import org.isoe.diagraph.internal.api.IAnnotatedEClass;
import org.isoe.diagraph.internal.api.IDiaConcreteSyntax;
import org.isoe.diagraph.internal.api.IDiaNamedElement;
import org.isoe.diagraph.internal.api.IDiaPlatformDelegate;
import org.isoe.diagraph.internal.api.IDiaPlatformDelegateProvider;
import org.isoe.diagraph.internal.api.IDiaPointOfView;
import org.isoe.diagraph.internal.m2.DiaGraph;
import org.isoe.diagraph.internal.m2.DiaNode;
import org.isoe.diagraph.internal.m2.api.IDiaParser;
import org.isoe.diagraph.internal.m2.parser.DAnnotatedEClass;
import org.isoe.diagraph.internal.m2.parser.DAnnotation;
import org.isoe.diagraph.internal.m2.parser.DAnnotationParser;
import org.isoe.diagraph.internal.m2.parser.DStatement;
import org.isoe.diagraph.internal.m2.parser.DStatement.DCommand_;
import org.isoe.diagraph.parser.api.IDiagraphNotation;
//import org.isoe.diagraph.parser.DiagraphGrammar;
import org.isoe.diagraph.parser.api.IDiagraphParser;
import org.isoe.diagraph.preferences.DiagraphPreferences;
//import org.isoe.diagraph.refactoring.MetamodelRefactoring; //x
import org.isoe.diagraph.views.ViewConstants;
import org.isoe.diastyle.lang.StyleConstants;
import org.isoe.diagraph.diastyle.helpers.IErrorReporter;
import org.isoe.extensionpoint.parsers.DiagraphParserConnector;
import org.isoe.extensionpoint.parsers.IStyleParser;
import org.isoe.extensionpoint.transformation.IAbtractTransformation;
import org.isoe.fwk.core.DParams;
import org.isoe.diagraph.runner.IDiagraphRunner;
import org.isoe.fwk.utils.ResourceManager;
import org.isoe.fwk.utils.ResourceUtils;

/**
 *
 * @author pfister
 *
 *
 */
public class DiagramGenerator implements IDiaPlatformDelegateProvider, IErrorReporter {

	private List<IconCopier> iconsToCopy = new ArrayList<IconCopier>();

	private static boolean LOG = DParams.DiagramGenerator_LOG;
	private static final boolean LOG_EXTENDED = DParams.DiagramGeneratorEXTENDED_LOG;
	// private static final boolean LOG_GEN = false;

	private static final String TEMPLATE_PLUGIN_SOURCE_ = DiagraphTemplates.DIAGRAPH_TEMPLATES_PLUGIN_ID_;
	private static final String TEMPLATE_FOLDER_SOURCE_ = DiagraphTemplates.DIAGRAPH_TEMPLATES_FOLDER_;
	public static final String TEMPLATE_FOLDER_TARGET_ = DiagraphTemplates.DIAGRAPH_TEMPLATES_TARGET_;

	private static final String ICON_PLUGIN_SOURCE = "org.isoe.diagraph.icons";
	private static final String ICON_FOLDER_SOURCE_ = "icons";

	private IDiaConcreteSyntax dsl;

	private ResourceSet resourceSet;
	private String[] resourceData;
	private IDiagraphRunner runner;
	private List<GenDiagram> diagrams = new ArrayList<GenDiagram>();

	private DAnnotationParser annotationParser;
	private IStyleParser styleParser;
	private StyleHandler styleHandler;
	private DStyle dstyle;

	private DiaGraph internalModel;

	private Mapping map;

	private GmfGraphGenerator gmfGraphGenerator;
	private GmfToolGenerator gmfToolGenerator;
	private GmfMapGenerator gmfMapGenerator;
	private IDiagraphNotation notation;// grammar;

	private static final boolean IAML_SAMPLE = false;

	// private boolean mustvalidate;
	/*
	 * public boolean mustvalidate() { return mustvalidate; }
	 */

	private IAbtractTransformation transformation; // future

	public List<IconCopier> getIconsToCopy() {
		return iconsToCopy;
	}

	public List<GenDiagram> getDiagrams() {
		return diagrams;
	}

	public Mapping getMap() {
		return map;
	}

	@Override
	public IDiagraphRunner getRunner() {
		return runner;
	}

	@Override
	public IDiaPlatformDelegate createSpecificPlatform(
			IDiaNamedElement namedElement) {
		return new GmfMappings(namedElement);
	}

	private String genEmfGenModelAndGmfMapModel(String view, boolean atRuntime,
			boolean genFromDiagraph, GenModel genmodel, EPackage mmodel,
			EPackage addmmodel, String[] resourceData, boolean rcp,
			boolean validate_, List<String> log,
			IProgressMonitor progressMonitor) {
		if (progressMonitor != null) {
			progressMonitor.subTask("genEmfGenModelAndGmfMapModel");
			progressMonitor.worked(1);
		}
		String result = null;

		if (genmodel == null) {
			clog("creating a genmodel for "
					+ mmodel.getName()
					+ (addmmodel == null ? ""
							: (" with " + addmmodel.getName())));
			genmodel = new GenModelGenerator(mmodel, addmmodel, resourceData)
					.getGenModel();
			if (addmmodel == null && genmodel.getForeignModel().isEmpty())
				genmodel.getForeignModel().add(mmodel.getName() + ".ecore");// FP140225
			genmodel.setImporterID("org.eclipse.emf.importer.ecore");// FP140225

			// genmodel_.getForeignModel().add("");
		} else {
			clog("existing genmodel for "
					+ mmodel.getName()
					+ (addmmodel == null ? ""
							: (" with " + addmmodel.getName())));
			// genmodel_ = new GenModelGenerator(mmodel,addmmodel_,
			// resourceData).getGenModel_(); //FP180829xx
		}

		getGmfMap(view, genmodel, mmodel, genFromDiagraph, resourceData, log,
				progressMonitor);// FP121015a

		/*
		 * AuditContainer auditContainer_ = map.getAudits(); GenAuditContainer
		 * genAuditContainer_ = null;// map.getAudits();
		 */


		if (genFromDiagraph)
			result = generateGmfGenAndGmfPlugins(view, atRuntime, genmodel,
					resourceData, rcp, validate_, progressMonitor);
		else {

			result = readGmfGenAndGmfPlugins(view, atRuntime, genmodel,
					resourceData, rcp, validate_, progressMonitor);
		}

		if (result != null && result.contains("GmfGen generation error"))
			throw new RuntimeException(result);
		boolean genres = runner.generatePlugins();

		if (genFromDiagraph)
			saveGmfGen(genres, view);

		return result;
	}

	public DiagramGenerator(IDiagraphRunner runner, IDiaConcreteSyntax dsl,
			IDiagraphNotation notation, String[] resourceData) {
		this.resourceData = resourceData;
		this.dsl = dsl;
		this.runner = runner;
		transformation = getGmfToDiagraph();
		this.notation = notation;

	}

	private IAbtractTransformation getGmfToDiagraph() {
		// TODO Auto-generated method stub
		// FP130506zzz
		// new Gmf2Diagraph(DParams.MODEL_FOLDER);
		return null;
	}

	private List<IAnnotatedEClass> getAnnotatedClassesAndCheckOrder(
			String layer, EPackage pakag) {
		List<IAnnotatedEClass> result = DAnnotatedEClass.getAnnotatedClasses(
				runner, pakag, DAnnotation.VALID_TOKENS, layer);
		return result;
	}

	public void generatePointOfView(String view, boolean atRuntime,
			GenModel genmodel, EPackage pakag, EPackage additionalpakag,
			boolean genFromDiagraph, boolean rcp, boolean validate_,
			List<String> logs, IProgressMonitor progressMonitor) {
		if (LOG && LOG_EXTENDED)
			logAnnotations(annotatedClasses, logs);
		if (progressMonitor != null) {
			progressMonitor.subTask("generatePointOfView");
			progressMonitor.worked(1);
		}
				//parsePointsOfView_obsolete_(pakag, view); //FP140615a
		if (dsl.parse(view,pakag)) { // FP131207xxx
			String logresult = genEmfGenModelAndGmfMapModel(view, atRuntime,
					genFromDiagraph, genmodel, pakag, additionalpakag,
					resourceData, rcp, validate_, logs, progressMonitor); // FP121031
			logs.add(logresult);
		} else
			throw new RuntimeException("There is no point of view !"); // FP130806
	}

	private void checkMetamodelPathAndName(EPackage pakag) {

		if (!resourceData[ResourceManager.RSRC_DIRECTORY].endsWith("/"
				+ DParams.MODEL_FOLDER)) {
			throw new RuntimeException(pakag.getName()
					+ " metamodel should be located in a folder named "
					+ DParams.MODEL_FOLDER);
		}

		if (!pakag.getName().equals(resourceData[ResourceManager.RSRC_MODEL])) { // FP120514
			String rightplace = resourceData[ResourceManager.RSRC_PLUGIN]
					.substring(0, resourceData[ResourceManager.RSRC_PLUGIN]
							.lastIndexOf("."))
					+ "."
					+ pakag.getName()
					+ "/"
					+ DParams.MODEL_FOLDER
					+ "/"
					+ pakag.getName() + ".ecore";
			String badPlace = resourceData[ResourceManager.RSRC_PLUGIN] + "/"
					+ DParams.MODEL_FOLDER + "/"
					+ resourceData[ResourceManager.RSRC_MODEL] + ".ecore";
			throw new RuntimeException(pakag.getName()
					+ " metamodel should be located in " + rightplace
					+ " , not in " + badPlace);
		}
	}

	public void parseStyle2(EPackage pakag) {
		// styleParser = new StyleParser(); //FP130506yy
		if (styleParser == null) {
			styleParser = new DiagraphParserConnector().getStyleParser();
			styleParser.init(pakag, internalModel);
		}
		styleParser.parse();
	}

	public EPackage getMetaModel() {
		if (LOG)
			clog("---------------          getMetaModel() "
					+ resourceData[ResourceManager.RSRC_PLUGIN]
					+ "            ----------------");
		resourceSet = new ResourceSetImpl();
		URI uri = ResourceManager.prepareResource(resourceSet, resourceData);
		EPackage mmodel = (EPackage) ResourceUtils
				.loadMetamodel(resourceSet, uri).getContents().get(0);
		checkMetamodelPathAndName(mmodel);
		return mmodel;
	}

	List<IAnnotatedEClass> annotatedClasses;

	private String styleBridgeTrace;

	public boolean refactor(String layer, EPackage mmodel,
			boolean changeTargetFolder_, List<String> logs, boolean save) {
		if (LOG)
			clog("---------------           refactorAndGenerateAllPointsOfView "
					+ resourceData[ResourceManager.RSRC_PLUGIN]
					+ "            ----------------");

		annotatedClasses = getAnnotatedClassesAndCheckOrder(layer, mmodel);
		// allowing refactoring is set in preferences, but can be overriden
		// by refactoring annotations in each ecore metamodel

		// MetamodelChecker checker = new MetamodelChecker(mmodel, runner);
		// handleRefactoring(mmodel, checker, annotatedClasses, resourceData,
		// changeTargetFolder_, logs, save);
		// if
		// (DiagraphPreferences.getBooleanPreference(DParams.KEY_CHANGE_LANGUAGE_VERSION)
		// && ResourceManager.isVersionChanged()) {
		/*
		 * if (DParams.CHANGE_LANGUAGE_VERSION &&
		 * ResourceManager.isVersionChanged()) { String msg =
		 * "Language Version changed, exit..."; DLog.addWarning(msg); clog(msg);
		 * logs.add(msg); return false; // return;// resourceSet; } else
		 */{
			if (changeTargetFolder_ && save)
				ResourceManager.setParams(changeTargetFolder_, resourceData);
			// generateAllPointsOfView(mmodel, logs);
			return true;//
		}
		// return resourceSet;
	}

	private void logAnnotations(List<IAnnotatedEClass> annotatedClasses,
			List<String> logs) {
		for (IAnnotatedEClass annotatedEClass : annotatedClasses)
			DAnnotation.logAnnotation(annotatedEClass.getEClass(), logs);
	}

	/*
	 * private void handleRefactoring(EPackage mmodel, MetamodelChecker checker,
	 * List<IAnnotatedEClass> annotatedClasses, String[] resourceData, boolean
	 * changeTargetFolder, List<String> logs, boolean save) {
	 *
	 * // checker.readStyleAnnotation(); checker.checkMetamodel();
	 * MetamodelRefactoring mrf = new MetamodelRefactoring(runner, mmodel,
	 * resourceData, checker, annotatedClasses, transformation);// FP120324x //
	 * , // transformation); if (mrf.refactorIsOverride())
	 * mrf.refactorLanguageXOrModels(changeTargetFolder, logs, save); }
	 */
/*
	private boolean parsePointsOfView(EPackage mmodel, String layer) {

		return dsl.parse(layer, mmodel); //FP140615d

	}
*/

	private IconCopier find(String t) {
		for (IconCopier iconCopier : iconsToCopy)
			if (iconCopier.getTargetPath().equals(t))
				return iconCopier;
		return null;
	}

	private void getGmfMap(String view, GenModel genmodel, EPackage mmodel,
			boolean genFromDiagraph, String[] resourceData, List<String> log,
			IProgressMonitor progressMonitor) {
		if (progressMonitor != null) {
			progressMonitor.subTask("generateGmfMap");
			progressMonitor.worked(1);
		}
		if (view == null)
			view = ViewConstants.DIAGRAPH_DEFAULT;
		IDiaPointOfView pointOfView = dsl.getPointOfView();//FP140615d
		//IDiaPointOfView pov_new = dsl_.getPointOfView_new(); //FP140615d


		if (pointOfView == null)
			throw new RuntimeException("there is no point of view"); // FP120331
		// FP130610x
		if (genFromDiagraph)
			parseWithDiagraphAndgenerateGmfModels_(mmodel, view, pointOfView,
					genmodel, resourceData, log, progressMonitor); // FP140124
		else
			loadGmfModels(mmodel, view, pointOfView, genmodel, resourceData,
					log, progressMonitor);



		if (genFromDiagraph && StyleHandler.HANDLE_GMF_PALETTE){ // FP121031www
			Mapping map = getMap();
			prepareExportIconsToEdit(view, map);
		}
	}

	List<DEdge> edges = null;

	private GmfGenGenerator gmfGenGenerator;

	private Canvas canvas;

	private ToolRegistry toolr;

	private boolean canvasComposite;




	public boolean isCanvasComposite() {
		return canvasComposite;
	}

	List<DEdge> getEdges() {
		if (edges == null) {
			edges = new ArrayList<DEdge>();
			for (DGraphElement element : runner.getDGraph().getElements())
				if (element instanceof DEdge)
					edges.add((DEdge) element);
		}
		return edges;
	}


	public List<DEdge> getEdges(DNode node) {
		List<DEdge> result = new ArrayList<DEdge>();
		for (DGraphElement element : node.getGraph().getElements()){
			if (element instanceof DNestedEdge){
				if (((DNestedEdge) element).getSource().getNode() == node)
					result.add((DEdge) element);
			} else if(element instanceof DSimpleEdge){
				 if (((DSimpleEdge) element).getSource() == node)
					result.add((DEdge) element); //FP150423b
			}
		}
		return result;
	}


	private List<DNode> getNodes() { // FP140518
		List<DNode> nodes = new ArrayList<DNode>();
		for (DGraphElement element : runner.getDGraph().getElements())
			if (element instanceof DNode)
				nodes.add((DNode) element);
		return nodes;
	}

	DNode getRootNode() {
		// DGraph dGraph = runner.getDGraph();
		return runner.getDGraph().getPointOfView();
	}

	private void registerToExport(BundleImage bundleImage, String name) {

		String target = "/icons/full/obj16/" + name + "."
				+ org.isoe.fwk.core.DParams.ICON_TYPE;
		String backup = "/icons-backup/" + name + "."
				+ org.isoe.fwk.core.DParams.ICON_TYPE;
		String icon = "/icons/" + name + "."
				+ org.isoe.fwk.core.DParams.ICON_TYPE;

		String rootName = ((IDiaConcreteSyntax) runner.getDsl())
				.getPointOfView().getElementName(); //FP140615d  // FP130321

	//	String rootName = ((IDiaConcreteSyntax) runner.getDsl())
	//			.getPointsOfViewAsTree().getElementName(); //FP140615d// FP130321

		if (find(target) == null && !name.equals(rootName)) {
			clog("registerToExport: " + icon + ";" + backup + ";" + target); // FP131204
			iconsToCopy.add(new IconCopier(bundleImage.getBundle(), bundleImage
					.getPath(), icon, backup,
					bundleImage.getBundle() + ".edit", target, false));
		} else
			clog("no registerToExport: " + target); // FP131204
	}

	private void prepareExportIconsToEdit(String layer, Mapping m) {
		if (LOG)
			clog("prepareExportIconsToEdit "+layer);
		for (CreationTool ct : getCreationTools()) {
			Image smicon = ct.getSmallIcon();
			if (smicon instanceof BundleImage)
				registerToExport((BundleImage) smicon, ct.getTitle());
		}
	}

	private List<CreationTool> getCreationTools() {
		List<CreationTool> result = new ArrayList<CreationTool>();
		for (AbstractTool tool : getMap().getDiagram().getPalette().getTools()) {
			if (tool instanceof ToolGroup) {
				ToolGroup toolgroup = (ToolGroup) tool;
				for (AbstractTool toul : toolgroup.getTools())
					if (toul instanceof CreationTool)
						result.add((CreationTool) toul);
			}
		}
		return result;
	}

	/*
	 * //should be private void createGmfGeneratorAndGmfPlugins(Mapping map,
	 * String view, List<DGraph> dGraphs,GenModel genmodel) {
	 * createGmfGenerator(mapping, genmodel, view, dGraph);
	 * createOpenDiagramBehaviors(dGraphs);
	 * generatePlugins(view,gmfGenGenerator); }
	 */

	// GenAuditContainer genAuditContainer = null;

	private StringBuffer createGmfGeneratorAndGmfPlugins(GenModel genmodel_,
			String[] resourceData, String layer, boolean rcp,
			boolean mustValidate_notused, IProgressMonitor progressMonitor) {

		StringBuffer log = new StringBuffer();
		String plugin = resourceData[ResourceManager.RSRC_PLUGIN];
		String path = resourceData[ResourceManager.RSRC_PATH_];

		IDiaPointOfView pointOfView= dsl.getPointOfView();//FP140615d
		//IDiaPointOfView pov_new = dsl_.getPointOfView_new_(); //FP140615d


		if (LOG)
			clog("---- GmfGen (1) createGmfGenGenerator : "
					+ pointOfView.toString());
		gmfGenGenerator = new GmfGenGenerator(runner, this, plugin,
				TEMPLATE_FOLDER_TARGET_, TEMPLATE_PLUGIN_SOURCE_,
				TEMPLATE_FOLDER_SOURCE_, ICON_PLUGIN_SOURCE,
				ICON_FOLDER_SOURCE_, path,
				(Mapping) pointOfView.getSyntaxMapping(), resourceData);

		gmfGenGenerator.setGenModel_(genmodel_);// FP130829

		if (LOG)
			clog("---- GmfGen (2) generateAndValidateAndModifyGmfGen for view : "
					+ pointOfView.toString());

		gmfGenGenerator.generateAndValidateAndModifyGmfGen(log, pointOfView,
				layer, rcp, progressMonitor);

		if (LOG)
			clog("---- GmfGen (4) [" + layer + "] CloneODBToTopLabels : "
					+ pointOfView.toString());

		// List<IDiaPointOfView> ch=pointOfView_.getChildren_(); //FP121124m
		// obsolete
		// for (IDiaPointOfView iDiaPointOfView : ch) //FP121124k
		// clog("---- GmfGen (4) ["+layer+"] child pov:"+iDiaPointOfView.getName());

		gmfGenGenerator
				.modifyGenEditorGeneratorCreateOpenDiagramBehavioursToTopLabels_(
						pointOfView, layer, progressMonitor);// FP121124k

		clog(pointOfView.getName());

		if (DParams.VALIDATE_GMF_MAP) { // FP130914x
			if (LOG)
				clog("validation");
			gmfGenGenerator.setupValidation(pointOfView, layer,
					progressMonitor);
		} else if (LOG)
			clog("no validation");

		if (LOG)
			clog("---- GmfGen (6) removeIconsIfMultipleLabels : "
					+ pointOfView.toString());
		// GmfGenGenerator gmfGenGenerator = findGmfGen((Mapping)
		// pointOfView.getSyntaxMapping());
		if (DParams.REMOVE_ICONS_IF_MULTIPLE_LABELS) {
			gmfGenGenerator
					.removeIconsFromNodesIfMultipleLabels(progressMonitor);
			gmfGenGenerator
					.removeIconsFromEdgesIfMultipleLabels(progressMonitor); // FP140206
		}
		return log;
	}

	private String readGmfGenAndGmfPlugins(String layer, boolean atRuntime,
			GenModel genmodel_, String[] resourceData, boolean rcp,
			boolean mustValidate_, IProgressMonitor progressMonitor) {
		IDiagraphRunner runr = this.runner;
		DGraph dGraph = runr.getDGraph();
		StringBuffer log = new StringBuffer();
		if (!atRuntime) {
			log = readGmfGeneratorAndxxx(genmodel_, resourceData, layer, rcp,
					mustValidate_, progressMonitor);
		}
		/*
		 * if (!atRuntime) { // FP121104 log =
		 * readGmfGeneratorAndCreateGmfPlugins(genmodel_, resourceData, layer,
		 * rcp, mustValidate_, progressMonitor);
		 * gmfGenGenerator.createOpenDiagramBehaviors(); // FP121124k }
		 *
		 * if (runner.generatePlugins() && gmfGenGenerator != null) { //
		 * FP130124 IDiaPointOfView pointOfView = dsl.getPointOfView();
		 *
		 * generatePlugin_(layer, gmfGenGenerator, pointOfView,
		 * DParams.DYNAMIC_TEMPLATES, log); // FP130419 }
		 *
		 * String logresult = log.toString(); if (logresult.isEmpty()) logresult
		 * = " GmfGen generation ok"; // FP120429
		 */
		return "";
	}

	private StringBuffer readGmfGeneratorAndxxx(GenModel genmodel,
			String[] resourceData, String layer, boolean rcp,
			boolean mustValidate_notused, IProgressMonitor progressMonitor) {

		StringBuffer log = new StringBuffer();
		String plugin = resourceData[ResourceManager.RSRC_PLUGIN];
		String path = resourceData[ResourceManager.RSRC_PATH_];

		GenEditorGenerator gmfgen = loadGmfGen(layer);
		return null;
	}

	private String generateGmfGenAndGmfPlugins(String layer, boolean atRuntime,
			GenModel genmodel_, String[] resourceData, boolean rcp,
			boolean mustValidate_, IProgressMonitor progressMonitor) {
		IDiagraphRunner runr = this.runner;
		DGraph dGraph = runr.getDGraph();
		StringBuffer log = new StringBuffer();
		if (!atRuntime) { // FP121104
			log = createGmfGeneratorAndGmfPlugins(genmodel_, resourceData,
					layer, rcp, mustValidate_, progressMonitor);
			gmfGenGenerator.createOpenDiagramBehaviors(); // FP121124k
		}

		if (runner.generatePlugins() && gmfGenGenerator != null) { // FP130124
			IDiaPointOfView pointOfView = dsl.getPointOfView();//FP140615d
			//IDiaPointOfView pov_new = dsl_.getPointOfView_new_(); //FP140615d

			generatePlugin_(layer, gmfGenGenerator, pointOfView,
					DParams.DYNAMIC_TEMPLATES, log); // FP130419
		}

		String logresult = log.toString();
		if (logresult.isEmpty())
			logresult = " GmfGen generation ok"; // FP120429
		return logresult;
	}

	private void handleGenLinks(GenDiagram diagram) {
		for (GenLink link : diagram.getLinks()) {
			if (link.getModelFacet() instanceof TypeLinkModelFacet) {
				TypeLinkModelFacet modelFacet = (TypeLinkModelFacet) link
						.getModelFacet();
				modelFacet.getContainmentMetaFeature().getEcoreFeature()
						.getUpperBound();
				// link.setOutgoingCreationAllowed(false);
			} else if (link.getModelFacet() instanceof FeatureLinkModelFacet) {
				FeatureLinkModelFacet modelFacet = (FeatureLinkModelFacet) link
						.getModelFacet();
				modelFacet.getMetaFeature().getEcoreFeature().getUpperBound();
			}
		}
	}

	private void generatePlugin_(String layer, GmfGenGenerator gmfGenGenerator,
			IDiaPointOfView pointOfView, boolean dynamicTemplates,
			StringBuffer log) {
		try {

			if (ViewConstants.DIAGRAPH_DEFAULT.equals(layer)) // FP130725
																// viewid
																// ==
																// 0
																// &&//
																// FP121031www
																// the
																// first
				// time only
				if (dynamicTemplates)
					gmfGenGenerator.deployTemplates_();

		} catch (CoreException e) {
			e.printStackTrace();
			throw new RuntimeException(
					"error while setting Dynamic Templates !!!!");
		}
		gmfGenGenerator.setDynamicTemplates(dynamicTemplates); // FP120702
		if (StyleHandler.HANDLE_GMF_PALETTE) // FP121031www the first time only
												// //&&

			gmfGenGenerator.deployIcons(layer);

		GenDiagram diagram = gmfGenGenerator.createDiagram(pointOfView, log);// FP120625
		diagrams.add(diagram);
		handleGenLinks(diagram);
	}

	private void saveGmfGen(boolean save, String layer) {
		if (LOG)
			clog("saveGmfGen");
		IDiaPointOfView pointOfView = dsl.getPointOfView();//FP140615a
		//IDiaPointOfView pov_new = dsl_.getPointOfView_new_(); //FP140615d

		GmfGenGenerator genr = (GmfGenGenerator) pointOfView
				.getEditorGenerator();
		if (save && genr != null) // FP120324
			resourceSet.getResources().add(
					genr.save(layer, pointOfView.getSuffix(), "gmfgen"));
	}

	private GenEditorGenerator loadGmfGen(String view) {
		if (LOG)
			clog("loadGmfGen");
		IDiaPointOfView pointOfView = dsl.getPointOfView();//FP140615a
		//IDiaPointOfView pov_new = dsl.getPointOfView_new_(); //FP140615d

		String plugin = resourceData[ResourceManager.RSRC_PLUGIN];
		String path = resourceData[ResourceManager.RSRC_PATH_];
		String povsuffix = pointOfView != null ? pointOfView.getSuffix() : "";

		GenEditorGenerator genr = (GenEditorGenerator) ResourceManager
				.loadModel(resourceData, povsuffix, "gmfgen").get(0);
		return genr;
	}

	private String traceNodeBridge(DStyleBridge DStyleBridge,
			DNodeStyle dNodeStyle, String dgraphname) {
		DColor backgroundColor = dNodeStyle.getColor();
		DLayout layout = dNodeStyle.getLayout();
		DColor fontColor = dNodeStyle.getFontColor();
		String result = dgraphname + "-" + DStyleBridge.getName() + "-"
				+ StyleConstants.STYLE_BACKGROUND + "-"
				+ backgroundColor.getLiteral() + "\n";
		styleBridgeTrace += result;
		return result;
	}

	private String traceEdgeBridge(DStyleBridge DStyleBridge,
			DEdgeStyle dEdgeStyle, String dgraphname) {
		DColor backgroundColor = dEdgeStyle.getColor();
		DColor fontColor = dEdgeStyle.getFontColor();
		String result = dgraphname + "-" + DStyleBridge.getName() + "-"
				+ StyleConstants.STYLE_BACKGROUND + "-"
				+ backgroundColor.getLiteral() + "\n";
		styleBridgeTrace += result;
		return result;
	}

	/*
	 * private void doIamlSample(String path) { // FP120109 gmfConfig =
	 * SimpleGMFFactory.eINSTANCE.createGMFConfiguration(); // to // remove...
	 * // sample: // IAML // initiative try { ResourceManager.save(resourceData,
	 * path + "." + "simplegmf", gmfConfig); } catch (IOException e) {
	 * e.printStackTrace(); } // FP120109 }
	 */


	private void parseAndTokenize(EPackage mmodel, String view, String plugin,
			String path, IDiaPointOfView pointOfView,
			IProgressMonitor progressMonitor, List<String> logs) {
		annotationParser = new DAnnotationParser(this, dsl, mmodel, notation,
				resourceData);
		annotationParser.setPointOfView(pointOfView);
		IDiaParser diaparser = annotationParser.initParser(view,
				progressMonitor);
		if (!runner.isLayouting()) //FP150325
		  annotationParser.parse_2(mmodel,diaparser.getModelElements(), view,
				progressMonitor); // FP140505ppp
		internalModel = annotationParser.parse_1(view, progressMonitor); // FP121011//
		runner.setInternalModel(internalModel);
		if (LOG)
			clog("PTK-parseStyle");
		try {
			parseStyle2(mmodel); // old style mode // FP120621c //FP120718
		} catch (Exception e) {
			String mesg = "error parsing Diagraph style 2 - " + e.getMessage()
					+ " !!!!";
			runner.cerror(mesg);
			logs.add(mesg);
		}
		parseStyle(mmodel, plugin); // new style mode //FP120719
		if (LOG)
			clog("PTK-transform");

		DGraph dgraph = annotationParser.transform(dstyle); // FP121224 - 2 //
															// FP120620
		// //FP120628
		annotationParser.checkAfterTransfo(); // jamais trop tard pour bien
												// faire...

		styleHandler.bridgeToTopologyMapping(dgraph, view); // FP130915voir


		if (!runner.isLayouting()) //FP150325
			  annotationParser.endParse_2(diaparser.getModelElements(), progressMonitor);


		annotationParser.prepareGeneration(view);
		clog("----------------  is now parsed and tokenized  ----------------");
	}


/*
	private void parseAndTokenize_new_pb(EPackage mmodel, String view, String plugin,
			String path, IDiaPointOfView pointOfView,
			IProgressMonitor progressMonitor, List<String> logs) {

		annotationParser = new DAnnotationParser(this, dsl, mmodel, notation,
				resourceData);
		//annotationParser.setPointOfView(pointOfView);
		IDiaParser diaparser = annotationParser.initParser(view,
				progressMonitor);
		annotationParser.setPointOfView_new_pb_(pointOfView,mmodel,diaparser.getModelElements(), view);//FP150325voir

		//annotationParser.parser1CheckBeforeParsing();  //FP150325voir

		annotationParser.parse_2(mmodel,diaparser.getModelElements(), view,
				progressMonitor); // FP140505ppp
		internalModel = annotationParser.parse_1(view, progressMonitor); // FP121011//
		runner.setInternalModel(internalModel);
		if (LOG)
			clog("PTK-parseStyle");
		try {
			parseStyle2(mmodel); // old style mode // FP120621c //FP120718
		} catch (Exception e) {
			String mesg = "error parsing Diagraph style 2 - " + e.getMessage()
					+ " !!!!";
			runner.cerror(mesg);
			logs.add(mesg);
		}
		parseStyle(mmodel, plugin); // new style mode //FP120719
		if (LOG)
			clog("PTK-transform");

		DGraph dgraph = annotationParser.transform(dstyle); // FP121224 - 2 //
															// FP120620
		// //FP120628
		annotationParser.checkAfterTransfo(); // jamais trop tard pour bien
												// faire...

		styleHandler.bridgeToTopologyMapping(dgraph, view); // FP130915voir

		annotationParser.prepareGeneration(view);
		clog("----------------  is now parsed and tokenized  ----------------");
	}
*/
	private void generate(EPackage mmodel, String view, String pov,
			String plugin, String path) {
		IDiagraphParser parser2 = notation.getParser();// DiagraphAnnotationParser.getInstance_(null,this);

		if (LOG) clog("----------------  gmf generation begins  ----------------");
		// FP120102 d�plac� genmodel avant
		if (LOG)
			clog("---- GmfGraphGenerator  ----");
		gmfGraphGenerator = new GmfGraphGenerator(this,view, plugin, path, mmodel,
				annotationParser, parser2,isCanvasComposite());
		if (LOG) clog("----------------  begin generation (gmfgraph) ----------------");

		//gmfGraphGenerator.isComposite();
		canvas = gmfGraphGenerator.getCanvas_();
		if (LOG)
			clog("---- GmfToolGenerator  ----");

		IPaletteManager paletteManager = new PaletteManager(runner,
				styleHandler, plugin, org.isoe.fwk.core.DParams.ICON_TYPE);
		gmfToolGenerator = new GmfToolGenerator(this,view, paletteManager, plugin,
				path, mmodel, annotationParser, parser2, runner,
				org.isoe.fwk.core.DParams.ICON_TYPE,isCanvasComposite());

		if (LOG) clog("----------------        generation (gmftool) ----------------");

		toolr = gmfToolGenerator.getToolRegistry();

		if (LOG)
			clog("generating mapping for layer " + view + " point of view:"
					+ pov);



		gmfMapGenerator = new GmfMapGenerator(gmfGraphGenerator,this,view, paletteManager, plugin,
				path, mmodel, annotationParser, parser2, toolr, canvas,
				DParams.VALIDATE_GMF_MAP,isCanvasComposite());

		if (LOG) clog("----------------        generation (gmfmap) ----------------");

		map = gmfMapGenerator.getMapping();
		// map.setMappingExtension(dgraph_); //FP120702 //FP120628 //we undo
		// modification of Gmf Mapping metamodel
		gmfToolGenerator.checkNotMappedNodes();

		if (LOG) clog("----------------  gmf generation ends      ----------------");


	}

	private boolean generatePlugins() {
		return runner.generatePlugins();
	}

	void saveDiagraphArtifacts(String pov, String plugin, String path) {
		// runner.isSave()
		resourceSet.getResources().add(
				annotationParser.getInternalModel().saveM2_(plugin, pov, path)); // *.diagraph
																					// file

		resourceSet.getResources().add( // FP120719
				ResourceManager.saveModel(styleHandler.getModel(), pov,
						"diastyle", resourceData,
						gmfGraphGenerator.getFilePath()));
	}

	private void saveGmfArtifacts_(String povsuffix, String plugin, String path) {
		resourceSet.getResources().add( // FP120320
				ResourceManager.saveModel(canvas, povsuffix, "gmfgraph",
						resourceData, gmfGraphGenerator.getFilePath()));
		resourceSet.getResources().add(
				ResourceManager.saveModel(toolr, povsuffix, "gmftool",
						resourceData, gmfToolGenerator.getFilePath()));
		resourceSet.getResources().add(
				ResourceManager.saveModel(map, povsuffix, "gmfmap",
						resourceData, gmfMapGenerator.getFilePath()));
	}

	private void loadGmfArtifacts(String povsuffix, String plugin, String path) {
		canvas = (Canvas) ResourceManager.loadModel(resourceData, povsuffix,
				"gmfgraph").get(0);
		toolr = (ToolRegistry) ResourceManager.loadModel(resourceData,
				povsuffix, "gmftool").get(0);
		map = (Mapping) ResourceManager.loadModel(resourceData, povsuffix,
				"gmfmap").get(0);
	}




	@SuppressWarnings("restriction")
	private boolean  detectComposite_(){

		DiaNode canvasNode=null;
		for (DStatement statement : annotationParser.getDStatements()) {
			if (statement.getCommand() == DCommand_.MAP_CREATE_){
				canvasNode = (DiaNode) statement.getElement();
				break;
			}
		}
		int count =0;
		EClass cn= null;
		EClass nc =null;
		EClass r =null;

		List<DGraphElement> allelems = annotationParser.getAllElements();
		for (DGraphElement elemt : allelems) {
			nc =  annotationParser.getEClassMapping(elemt);
			if (nc!=null){
				EClass c= canvasNode.getEClass();
				if (annotationParser.isNode(nc) &&  nc.isAbstract() && nc.isSuperTypeOf(c) && contains(c,nc)){
					if (cn==null || cn!=c){
						r = nc;
					    count++;
					}
					cn= c;
				}
			}
		}
		if (count>1)
			throw new RuntimeException("should not happen in detectComposite");
		else
			if(LOG && count == 1)
				clog("pattern composite found "+r.getName()+" - "+canvasNode.getName());
		return count == 1;
    }


	private boolean contains(EClass k, EClass c) {
		List<EStructuralFeature> sfs =k.getEStructuralFeatures();
		for (EStructuralFeature eStructuralFeature : sfs) {
			if (eStructuralFeature instanceof EReference){
				EReference erf = (EReference) eStructuralFeature;
				if (erf.getEType() == c && erf.isContainment())
					if (annotationParser.isCref(erf))
						return true; //FP150531voir
			}
		}
		return false;
	}

	private DAnnotationParser parseWithDiagraphAndgenerateGmfModels_(
			EPackage mmodel, String view, IDiaPointOfView pointOfView,
			GenModel genmodel, String[] resourceData, List<String> logs,
			IProgressMonitor progressMonitor) {
		;

		gmfGraphGenerator = null;
		gmfToolGenerator = null;
		gmfMapGenerator = null;
		canvas = null;
		toolr = null;

		if (progressMonitor != null) {
			progressMonitor.subTask("generatePointOfViewEditor");
			progressMonitor.worked(1);
		}
		String plugin = resourceData[ResourceManager.RSRC_PLUGIN];
		String path = resourceData[ResourceManager.RSRC_PATH_];
//FP150422
		//cast emodelelement error
		parseAndTokenize(mmodel, view, plugin, path, pointOfView,
				progressMonitor, logs);

		this.canvasComposite = detectComposite_();//FP150524composite


		if (!DParams.MODE_COMPOSITE_PATTERN)
		    this.canvasComposite =false;//FP150524composite
//FP150529
		String povsuffix = pointOfView != null ? pointOfView.getSuffix() : "";
		generate(mmodel, view, povsuffix, plugin, path);

		if (LOG)
			clog("---- save diagraph ----");

		saveDiagraphArtifacts(povsuffix, plugin, path);

		if (generatePlugins()) {
			saveGmfArtifacts_(povsuffix, plugin, path);

		}
		if (LOG && LOG_EXTENDED)
			gmfGraphGenerator.logDiagramElements();

		if (LOG)
			clog("---- validsate2 ----");

		gmfMapGenerator.validate2();

		if (pointOfView != null)
			pointOfView.setSyntaxMapping(map);

		if (DiagraphPreferences
				.getBooleanPreference(DParams.KEY_SAVE_EMF_TRANSFO)) { // old
			// dgi
			// model

			if (DParams.SAVE_DGI)
				resourceSet
						.getResources()
						.add( // revoir
						annotationParser.getInternalModel()
								.saveDgiTransformation(plugin, povsuffix, path));
		}
		return annotationParser;
	}

	private void loadGmfModels(EPackage mmodel, String view,
			IDiaPointOfView pointOfView_, GenModel genmodel,
			String[] resourceData, List<String> logs,
			IProgressMonitor progressMonitor) {

		gmfGraphGenerator = null;
		gmfToolGenerator = null;
		gmfMapGenerator = null;
		canvas = null;
		toolr = null;

		if (progressMonitor != null) {
			progressMonitor.subTask("loadGmfModels");
			progressMonitor.worked(1);
		}
		String plugin = resourceData[ResourceManager.RSRC_PLUGIN];
		String path = resourceData[ResourceManager.RSRC_PATH_];

		String povsuffix = pointOfView_ != null ? pointOfView_.getSuffix() : "";

		loadGmfArtifacts(povsuffix, plugin, path);

	}

	private void parseStyle(EPackage mmodel) {
		// TODO Auto-generated method stub

	}

	public void parseStyle(EPackage mmodel, String pluginId) {
		styleHandler = new StyleHandler(this,mmodel, pluginId);// this,
		try {

			dstyle = styleHandler.createModel();
			dstyle.setStyleHandler(styleHandler);
			runner.setStyleHandler(styleHandler);
			runner.setDStyle(dstyle);
			annotationParser.setStyle(dstyle);

		} catch (Exception e) {
			String mesg = "error parsing Diagraph: " + e.getMessage() + " !!!!";
			clog(mesg);
			throw new RuntimeException(mesg);
		}
		annotationParser.setStyleHandler_(styleHandler);
	}

	@Override
	public void cerror(String mesg) {
		runner.cerror(mesg);
	}


	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	public DAnnotationParser getAnnotationParser() {
		return annotationParser;
	}

	private final static String DEFAULT = "default";

	public ResourceSet getResourceSet() {
		return resourceSet;
	}

	@Override
	public IDiaPointOfView getPointOfView() { //FP140617voir// FP120318
		//if (dsl != null)
			return dsl.getPointOfView();
		//else
			//return null;
	}
}
