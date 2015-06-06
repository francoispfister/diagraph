/**
 * Copyright (c) 2014 Laboratoire de Genie Informatique et Ingenierie de Production - Ecole des Mines d'Ales
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *    Francois Pfister (ISOE-LGI2P) - initial API and implementation
 */
package org.isoe.diagraph.operations.views;

import gary.lewandowski.RandomString;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import mtbe.model.matcher.helpers.AccentRemover;

import org.eclipse.core.internal.resources.Workspace;
import org.eclipse.core.internal.resources.WorkspaceRoot;
import org.eclipse.core.internal.utils.Queue;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecoretools.legacy.diagram.edit.parts.EAnnotationEditPart;
import org.eclipse.emf.ecoretools.legacy.diagram.edit.parts.EPackageEditPart;
import org.eclipse.emf.ecoretools.legacy.diagram.part.EcoreDiagramEditor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.parts.DiagramDocumentEditor;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jdt.internal.core.JavaProject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveRegistry;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchPage;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.WorkbenchWindow;
import org.eclipse.ui.internal.registry.ActionSetRegistry;
import org.eclipse.ui.internal.registry.IActionSetDescriptor;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.progress.UIJob;
import org.eclipse.wb.swt.SWTResourceManager;
import org.isoe.diagraph.adapter.IDiagraphAdapter;
import org.isoe.diagraph.adapter.impl.DiagraphAdapter;
import org.isoe.diagraph.annotationhelper.AnnotationHelper;
import org.isoe.diagraph.annotationhelper.IAnnotationHelper;
import org.isoe.diagraph.console.logger.views.ConsoleLogger;
import org.isoe.diagraph.context.IDiagraphContext;
import org.isoe.diagraph.context.impl.DiagraphContext;
import org.isoe.diagraph.controler.IDiagraphControler;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.diastyle.helpers.IErrorReporter;
import org.isoe.diagraph.generic.GenericConstants;
import org.isoe.diagraph.interpreter.DiagraphInterpreter;
import org.isoe.diagraph.layerhelper.ILayerHelper;
import org.isoe.diagraph.layerhelper.impl.LayerHelper;
import org.isoe.diagraph.megamodel.Dsml;
import org.isoe.diagraph.megamodel.EcoreDiagram;
import org.isoe.diagraph.megamodel.Megamodel;
import org.isoe.diagraph.megamodel.presentation.MegamodelEditor;
import org.isoe.diagraph.megamodelhelper.IMegaModelBuilder;
import org.isoe.diagraph.megamodelhelper.IMegaModelMan;
import org.isoe.diagraph.megamodelhelper.IMegamodelParser;
import org.isoe.diagraph.megamodelhelper.impl.MegaModelBuilder;
import org.isoe.diagraph.megamodelhelper.impl.MegaModelMan;
import org.isoe.diagraph.osem.constants.OsemConfiguration;
import org.isoe.diagraph.osem.deployer.OsemDeployer;
import org.isoe.diagraph.preferences.DiagraphPreferences;
import org.isoe.diagraph.runtimefactory.IDiagraphRuntimeFactory;
import org.isoe.diagraph.runtimefactory.impl.DiagraphRuntimeFactory;
import org.isoe.diagraph.views.ViewConstants;
import org.isoe.diagraph.workbench.api.DiagraphNotifiable;
import org.isoe.diagraph.workbench.api.IDiagraphActionWiper;
import org.isoe.ep.osem.deployer.IOsemDeployer;
import org.isoe.extensionpoint.automation.AutomationConnector;
import org.isoe.extensionpoint.automation.IAutomationControler;
import org.isoe.extensionpoint.automation.IAutomationService;
import org.isoe.extensionpoint.diagen.IDiagraphAlphabet;
import org.isoe.extensionpoint.diagen.IMegamodelService;
import org.isoe.extensionpoint.diagen.MegamodelConnector;
import org.isoe.extensionpoint.diagraph.action.DiagraphService; //FP140707_c_refactored
import org.isoe.extensionpoint.diagraph.action.IGenDiagraphServ;
import org.isoe.extensionpoint.diagraph.generator.IDiagraphGen; //FP140707_b_refactored
import org.isoe.extensionpoint.ecoreutils.IEcoreUtils;
import org.isoe.extensionpoint.generator.ISyntaxInference;
import org.isoe.extensionpoint.generator.ISyntaxRefactoring;
import org.isoe.extensionpoint.generator.SyntaxGeneratorConnector;
import org.isoe.extensionpoint.gramgen.IGrammarGeneratorService;
import org.isoe.extensionpoint.graphviz.DotifyConnector;
import org.isoe.extensionpoint.graphviz.IDotifier;
import org.isoe.extensionpoint.graphviz.ILanguageDotifyService;
import org.isoe.extensionpoint.graphviz.IModelDotifyService;
import org.isoe.extensionpoint.interpreter.IDiagraphInterpreter;
import org.isoe.extensionpoint.languagehandler.ILanguageHandler;
import org.isoe.extensionpoint.languagehandler.LanguageHandlerConnector;
import org.isoe.extensionpoint.layout.ILayoutService;
import org.isoe.extensionpoint.layout.LayoutConnector;
import org.isoe.extensionpoint.megamodel.DeployMegamodelConnector;
import org.isoe.extensionpoint.megamodel.IMegamodelDeployService;
import org.isoe.extensionpoint.megamodel.IMegamodelManager;
import org.isoe.extensionpoint.megamodel.IModelDeployService;
import org.isoe.extensionpoint.mwb.launcher.IModelWorkbenchLauncher;
import org.isoe.extensionpoint.mwb.launcher.LaucherConnector;
import org.isoe.extensionpoint.parsers.IRuntimeDiagraphParser;
import org.isoe.extensionpoint.remoting.IDiagraphRemotingService;
import org.isoe.extensionpoint.rules.ISyntaxRules;
import org.isoe.extensionpoint.transformation.ITransformationService;
import org.isoe.extensionpoint.transformation.TransformationServiceConnector;
import org.isoe.extensionpoint.xmi.IMetamodelRetriever;
import org.isoe.extensionpoint.xmi.MetamodelRetrieverConnector;
import org.isoe.extensionpoint.xsl.transformer.IXslTransformer;
import org.isoe.extensionpoint.xsl.transformer.XslTransformerConnector;
import org.isoe.fwk.basic.controler.DiagraphComponentConnector;
import org.isoe.fwk.basic.editortracker.viewers.utils.ActiveEditorTracker;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.core.Separator;
import org.isoe.fwk.megamodel.handler.MegamodelHandler;
import org.isoe.fwk.pathes.PathPreferences;
import org.isoe.fwk.platform.resolver.Activator;
import org.isoe.fwk.uihelper.INavigatorHelper;
import org.isoe.fwk.uihelper.impl.NavigatorHelper;
import org.isoe.fwk.utils.DLog;
import org.isoe.fwk.utils.FilesUtils;
import org.isoe.fwk.utils.ResourceUtils;
import org.isoe.fwk.utils.eclipse.EclipseUtil;
import org.isoe.fwk.utils.eclipse.WorkbenchUtils;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.parts.DiagramDocumentEditor;

/**
 *
 * @author fpfister
 *
 */
import org.isoe.extensionpoint.diagraph.action.DiagraphActionConnector;

@SuppressWarnings("restriction")
public class SandboxView extends ViewPart implements ISelectionListener,
		IErrorReporter, IResourceChangeListener, IDiagraphControler,
		// ILanguageControler,// IPerspectiveListener,
		DParams, DiagraphNotifiable, IAutomationControler, IMegamodelParser,
		Listener { // FP130523
	// implements
	// ISimilarityControler,

	/**
	 * The ID of the view as specified by the extension.
	 */
	// public static final String ID =
	// "org.isoe.fwk.basic.view.views.SandboxView";
	public static final String ID = "org.isoe.diagraph.operations.views.SandboxView";

	static {
		if (!GenericConstants.DIAGRAPH_BASIC_CONTROLER_ID_.equals(ID))
			throw new RuntimeException("keep basic view ID synchronized !!!");
		// check in plugin.xml
	}

	private static final Random RAND = new Random();

	private static final boolean EXPE = false;
	private static final boolean DEV_CONFIG = false;

	private static final boolean LOG = SandboxView_LOG;
	private static final boolean LOG5 = SandboxView_5_LOG;
	private static final boolean DO_LOG = LOG || ModelDeployService_LOG
			|| sleep_LOG_ || Megamodel_LOG;
	private static final boolean LOG6 = SandboxView_6_LOG;
	private static final boolean LOG7 = SandboxView_7_LOG;
	private static final boolean LOG8 = SandboxView_8_LOG;

	private static final boolean JOB_LOG = MegaModelBuilderJOB_LOG;

	private static final boolean CERROR = LOG_AT_RUNTIME;

	/*-------------------------- controler members -----------------------------*/

	/*------------------ state --------------------------*/

	Diagram lastFocusedDiagram;
	EClass selectedClass;
	EModelElement currentElement;
	String currentPov;
	int currentPovId;
	String langageName;
	EPackage currentPackage;
	String focusedlanguage;
	String newPov;
	int newPovId;

	// Map<String, String> viewReg_ = new HashMap<String, String>();
	String[] layers_;
	Diagram changedDiagram;
	EcoreDiagramEditor ecoreDiagramEditor;
	ISelection ecoreSelection;
	EModelElement[] elementStack;
	String previouslog;
	boolean layoutDone;
	List<String> deployedLanguages_;
	List<IProject> deployedProjects;
	IGraphicalEditPart currentGraphicalEditPart;
	EObject prevObj;
	String currentModel;
	String langageNsUri;
	String langageNsPrefix;
	EObject currentEObject;
	boolean is_headless;
	String megamodeluri;
	String megamodelPath;
	boolean redraw = true;
	int lwbPport;

	// private String currentlanguage_;

	private List<String> buildLog_;
	private boolean building = false;

	private String languageList;
	private MegamodelEditor megamodelEditor;
	private int[] ports; // FP140526
	private String logGeneration;
	private int listens_;
	private List<IWorkbenchPage> pagestowipe = new ArrayList<IWorkbenchPage>();
	private IGraphicalEditPart host;
	private boolean notUnder;
	private boolean languageConfiguration;
	private boolean modelConfiguration;
	private boolean existsLanguageRepository_;
	private String instanceRepositoryLocation;
	private String instanceRepositoryPath;
	private Queue<EModelElement> elemenQueue = new Queue<EModelElement>(100,
			false);
	private String matcherLeftPath_;
	private String matcherRightPath_;
	private String lastRightChoice_;
	private String lastLeftChoice_;
	private String priorDeltafFullPath = "";
	private int changes;
	private boolean ko;
	private boolean simple = false;
	private String error;
	private boolean buildInitialLanguageDone;
	private boolean inhibit; // hack
	private boolean newLwb;
	private DiagraphComponentConnector componentConnector;
	private List<DiagraphService> diagraphServices = new ArrayList<DiagraphService>();
	private Job serverJob;
	int wid = 878;
	private String logDiagraph = "";
	private IDiagraphAdapter diagraphAdapter;
	private DiagraphState prior;
	private int mwbPort = -1;
	private int logcount = -1;
	private String runningCmd;
	private EPackage clonedPackage_;
	private boolean readyToWork;// FP140626
	private String lastView;
	private String basePath;
	private EPackage mergePackage;
	private Diagram mergeDiagram;
	private EPackage sourcePackage;
	private Diagram sourceDiagram;
	private ConsoleLogger console;
	private boolean stateless = false;
	private IProject lastOtherProject;
	private boolean listening_;
	private boolean active = true;

	/*------------------ services --------------------------*/

	private IGenDiagraphServ genDiagraphAction;
	private ISyntaxInference syntaxInferenceService;
	private ISyntaxRefactoring syntaxRefactoringService;
	private ISyntaxRules syntaxRulesService;
	private IGrammarGeneratorService grammarGeneratorService;
	private IEcoreUtils ecoreUtilService;
	private ITransformationService transformationService;
	private ILanguageDotifyService languageDotifyService;
	private IModelDotifyService modelDotifyService;
	private IModelDeployService deployModelService_;
	private IMegamodelDeployService deployMegamodelService__;
	private IDiagraphRemotingService diagraphRemotingService_;
	private IDiagraphGen diagraphGenerator;
	private ILayoutService layoutService;
	private ILanguageHandler languageCloner_nu;
	private ILanguageHandler languageParser;
	private IAutomationService automationService;
	private IModelWorkbenchLauncher modelWorkbenchLauncher;
	private IMegamodelManager megamodelManager;
	private IMetamodelRetriever metamodelRetriever;
	private IMegamodelService megamodelService; // FP130903
	private IXslTransformer xslTransformationService;
	private IRuntimeDiagraphParser runtimeDiagraphParser; // TODO wire here

	/*------------------ service candidates --------------------------*/

	private IMegaModelBuilder megamodelBuilder;
	private IDiagraphInterpreter interpreter_;
	private IMegaModelMan megamodelMan;

	private IDiagraphActionWiper actionWiper;
	private IOsemDeployer osemDeployer;
	private IDiagraphRuntimeFactory diagraphRuntimeFactory_;
	private ActiveEditorTracker activeEditorTracker;
	private IAnnotationHelper annotationHelper;
	private ILayerHelper layerHelper;
	private IDiagraphContext context;
	private INavigatorHelper navigatorHelper;

	/*---------------------------- gui ---------------------------------*/

	private SashForm sashForm_whole;
	private SashForm sashForm_bottom;
	private SashForm sashForm_left;
	private SashForm sashForm_right;
	private SashForm sashForm_top;
	private SashForm sashForm;
	private SashForm sashFormFooter;

	private Button buttonOnlyCurrentView;
	private Button btnLayout;
	private Button btnDeployLocalLanguages;
	private Button btnDeployAllLanguages;
	private Button btnDeployLocalModels;
	private Button btnUploadModelAndLanguage;
	private Button btnDownloadLanguageAndModels;
	private Button btnSimilarityAction;
	private Button btnDotify;
	private Button btnRefreshModelCombos;
	private Button btnRetrieveM2;
	private Button btnStopAutomationServer;
	private Button btnTransform;
	private Button btnStartTomcat;
	private Button btnGenerate;
	private Button btnNewPointOfView;
	private Button btnGenerateLanguage;
	private Button btnUseLanguage;
	private Button btnDeployFromLwb;
	private Button btnPutTo;
	private Button btnReplicateModelRepository;
	private Button btnClone;
	private Button btnLocalQuery;
	private Button btnSimilarityRemove;
	private Button btnSimilaritySearch;
	private Button btnStopTomcat;
	private Button btnLaunchAnt;
	private Button btnLwbQuery;
	private Button btnStartAutomationServer;
	private Button checkBoxRcp;
	private Button btnTransfXslt;
	private Button btnConsolidateRepositoryWprojects;
	private Button btnGetFromOpenDsml;
	private Button btnPutToOpenDsml;
	private Button buttonGetBuildinPrototypes;
	private Button btnMerge;
	private Button btnGenerateAuto;
	private Button btnBuildMeg;
	private Button cbRefactor;
	private Button checkPinToMerge;

	private Combo pointOfViewComboDev;
	private Combo matcherComboLeft;
	private Combo matcherComboRight;
	private Combo dotifyTypeCombo;

	private Composite compositeVisualGrammarGeneration;
	private Composite compositeLanguageDesign;
	private Composite compositeLanguageImplementation;
	private Composite compositeDeployers;
	private Composite compositeModelDeployer;
	private Composite compositeDev;
	private Composite compositeTabOp;
	private Composite compositeTabDev_nu;
	private Composite parentComposite_nu;
	private Composite composite_10_put_language;
	private Composite composite_8_get_buildin;
	private Composite panelTu;
	private Composite composite_9_getlanguage;
	private Composite composite_17;
	private Composite panelUpper;
	private Composite compositeHidden;
	private Composite panelTl;

	private Text text1;
	private Text text2;
	private Text logTxt;
	private Text txtQuery;
	private Text textSimilaritySearch;
	private Text txtLanguageName;
	private Text textLangageVersion;
	private Text txtModelDescription;
	private Text txtPutToLwb;
	private Text pointOfViewNameText;
	private Text textWorkbenchName;
	private Text txtLanguageNsUri;
	private Text textStatus;
	private Text txtModelName;
	private Text txtLanguageDeploy2;
	private Text txtLwbQuery;
	private Text txtCloneDev;
	private Text txtLanguageNsPrefix;
	private Text logTxtError;
	private Text textLog;
	private Text txtClone;
	// private Text text_2;

	private Label lblChange;
	private Label lblModelName;
	private Label lblUserEmail;
	private Label loggerLabel1;
	private Label loggerLabel2;
	private Label labelTeamNamespace_;
	private Label lblLanguageName_1;
	private Label lblUserName;
	private Label lblTeamName;
	private Label lblPassword;
	private Label lblConnectState;
	private Label modeLabel;
	private Label m2PackageLabel;
	private Label lblStack;
	private Label lblModelunderdesign;
	private Label lblDesign;
	private Label lblNewLabel;
	private Label lblMegamodelFeatures;
	private Label lblEclass;
	private Label lblLanguageToMerge_;
	private Label lblCurrentPart;
	// private Label lblPovId_;
	private Label lblPov;

	private TabFolder mainTab;
	private TabItem tabItemOperation;
	private TabItem tabItemDev;

	private Combo comboSyntaxLayer;
	private Combo viewCombo_nu_;

	private AlignmentTable alignmentTable;

	private Button btnCloneLanguage2;
	private Composite composite_4;
	private Label lblArtefactName;
	private Label lblLanguageDesign_1;
	private Composite composite_7;
	private Button btnDesign;
	private Text textDesign;
	private Label lblDesignThe;
	private Composite composite_8;
	private Button btnAutomatedGramgen;
	private Label lblInferThe;
	private Label lblHandyOr;
	private Button btnLayoutAnots;
	private Composite composite_9;
	private Button btnLaunchMWB;
	private Text textLaunchMDWB;
	private Label lblDesignModels;
	private Button btnGenerateModeler;
	private Label lblGenerateA;
	private Composite composite_R2;
	private Composite composite_11;
	private Label lblAdvancedFeatures;
	private Composite composite_12;
	private Button btnNewPov;
	private Text textNewPov;
	private Label lblNewPoint;
	private Composite composite_13a;
	private Button btnLayoutLayer;
	private Label lblConcreteSyntax;
	private Text textGetFromOpenDsml;
	private Text textPutToOpenDsml;
	private Combo comboPov_;
	private Composite composite_11_putodsml;
	private Label lblPutA;
	private Button btnPutModelToOdsml;
	private Text textModelDesc;
	private Composite composite_13;
	private Button btnDotify_1;
	private Label lblaGenerateGraphviz;
	private Combo comboGraphViz;
	private Composite composite_19;
	private Label lblGetA;
	private Button buttonGetModelFromOdsml;
	private Text textGetModelFromOdsml;
	private Text textPrototype;

	private Button btnDiagraphExpe;
	private Text text_1;
	// private Label lblCPov;
	// private Label lblCPovId_;
	private Text txtDiagraphActions;
	private Button btnGetDiagraphActions;
	private Button btnExecActions;

	private TabFolder diagraphFolder;
	private TabItem tabActions;
	private TabItem tabConsole;
	private Composite compositeTabEditor;
	private Composite composite_10;
	private Text textDiagraphStatements;
	private StyledText textDiagraphResponse_;
	private StyledText textDiagraphCmds_;
	private Button btnExecute;
	private Composite composite_txtsyntax;

	private int cntest;

	private String lastNotDiagraphedLanguage;

	private boolean layouting;
	private boolean focusing; //FP150530

	private Resource currentResource;

	private boolean enableGeneration_;



	private void clogLang(String language) {
		if (DiagraphLanguage_LOG)
			System.out.println(language);
	}

	private void doclog(String mesg) {
		if (DO_LOG)
			System.out.println(mesg);
	}

	private void clog(String mesg) {
		if (DO_LOG) { // && !silent
			if (LOG_ON_CONSOLE)
				logConsole(mesg);
			else
				System.out.println(mesg);
		}
	}

	private void clog2(String mesg) {
		if (CheckAnnotationCommand_LOG)
			System.out.println(mesg);
	}

	private void clog4__(String mesg) {
		if (LanguageTransformer_4_LOG)
			System.out.println(mesg);
	}

	private void clog4(String mesg) {
		if (LanguageTransformer_4_LOG) { // && !silent
			if (mesg == null)
				mesg = "null";
			if (LOG_ON_CONSOLE)
				logConsole(mesg);// FP140630aaa
			else
				System.out.println(mesg);
		}
	}

	private void clog5(String mesg) {
		if (LOG5)
			System.out.println(mesg);
	}

	private void clog6(String mesg) {
		if (LOG6)
			System.out.println(mesg);
	}

	private void clog7(String mesg) {
		if (LOG7)
			System.out.println(mesg);
	}

	private void clog8(String mesg) {
		if (LOG8)
			System.out.println(mesg);
	}

	private void clogState(String mesg) {
		if (DiagraphState_LOG)
			System.out.println(mesg);
	}

	public List<String> getDeployedLanguages() {
		return deployedLanguages_;
	}

	public List<IProject> getDeployedProjects() {
		return deployedProjects;
	}

	/*------------------------ connections to services  -------------------*/

	@Override
	public EClass getSelectedClass() {
		return selectedClass;
	}

	/**
	 * contribution wiring is done here
	 */
	private void getAllServices() {

		// 1) wire components to the controler

		componentConnector = new DiagraphComponentConnector(this); // TODO
																	// delegate
		// all the
		// following to
		// the
		// DiagraphComponentConnector
		syntaxRefactoringService = getSyntaxRefactoringService();
		syntaxRulesService = getSyntaxRulesService();
		// similarityAction = getSimilarityAction(); //FP130523
		deployModelService_ = getDeployMegamodelInstanceService();
		deployMegamodelService__ = getMegamodelDeployService();
		layoutService = getLayoutService();
		// layoutWorkbenchAction = getLayoutWorkbenchAction();
		modelDotifyService = getModelDotifyService();
		languageDotifyService = getLanguageDotifyService();
		grammarGeneratorService = (IGrammarGeneratorService) getGrammarGeneratorService();
		ecoreUtilService = getEcoreUtilService();
		diagraphRemotingService_ = getDiagraphRemotingService();
		genDiagraphAction = getGenDiagraphAction();
		syntaxInferenceService = getSyntaxInferenceService();
		transformationService = getTransformationService();
		// languageCloner = getClonerService();
		languageParser = getParserService();
		automationService = getAutomationService();
		modelWorkbenchLauncher = getModelWorkbenchLauncher();
		megamodelManager = getMegamodelManager();
		xslTransformationService = getXslTransformationService();
		metamodelRetriever = getMetamodelRetriever();
		megamodelService = getMegamodelService();

		diagraphGenerator = componentConnector.getDiagraphGeneratorService();// getDiagraphGenerator();
																				// //FP140702

		if (diagraphGenerator == null)
			throw new RuntimeException("no diagraphGenerator");
		diagraphGenerator.initialize(); // FP140705

		// 2) wire some components to others
		deployModelService_.setMetamodelRetriever(metamodelRetriever);
		deployMegamodelService__.setMetamodelRetriever(metamodelRetriever);
		diagraphRemotingService_.setMetamodelRetriever(metamodelRetriever);
		modelDotifyService.setMetamodelRetriever(metamodelRetriever);
		languageDotifyService.setMetamodelRetriever(metamodelRetriever);
		diagraphGenerator.setEcoreUtilService(ecoreUtilService);
		diagraphGenerator.setNotifiable(this);
		languageParser.setEcoreUtils(ecoreUtilService);
		// similarityAction.setMetamodelRetriever(metamodelRetriever);

		// 3) wire candidate components to the controler
		megamodelMan = new MegaModelMan(this);
		megamodelBuilder = new MegaModelBuilder(this);
		annotationHelper = new AnnotationHelper(this);
		layerHelper = new LayerHelper(this);
		diagraphAdapter = new DiagraphAdapter(this);
		diagraphRuntimeFactory_ = new DiagraphRuntimeFactory(this);
		context = new DiagraphContext(this);
		navigatorHelper = new NavigatorHelper(this);
		interpreter_ = new DiagraphInterpreter(this);

		if (DParams.DEPLOY_OSEM)
			osemDeployer = OsemDeployer.getInstance(this);

		// 4) wire candidate components together
		MegamodelHandler mh = MegamodelHandler.getInstance();
		megamodelMan.setHandler(mh);

		cinfo("services initialized");

	}

	private ILayoutService getLayoutService() {
		if (layoutService == null) {
			layoutService = new LayoutConnector().getLayoutService();
			// seek the layout plugin through the Eclipse Extension Mechanism
			if (layoutService == null) {
				if (LOG)
					clog("No layout service is available !");
				throw new RuntimeException("no Layout");
				// use a mock if the real service is not discovered by the
				// Eclipse Extension Mechanism
			} else if (LOG)
				clog("Using Layout Feature for diagraph layout");

			layoutService.setControler(this);
		}
		return layoutService;
	}

	@Override
	public void registerService(Object diagraphService) {
		if (diagraphServices.contains(diagraphService))
			// throw new
			// RuntimeException("registerService: allready registered "+diagraphService.getClass().getName());
			if (LOG)
				clog("registerService: allready registered "
						+ diagraphService.getClass().getName());
		diagraphServices.add((DiagraphService) diagraphService);
		((DiagraphService) diagraphService).setSilent(!LOG);
	}

	/*------------------------------ implementation ----------------------------*/

	public SandboxView() {

	}

	@Override
	public boolean isLanguageConfiguration() {
		return languageConfiguration;
	}

	public boolean isDev() {
		if (mainTab == null)
			return false;
		return tabItemDev == mainTab.getItem(mainTab.getSelectionIndex());
	}

	@Override
	public String getCloneName(boolean allowRandom) {
		String result = isDev() ? txtCloneDev.getText() : txtClone.getText();
		result = normalize(result, allowRandom);
		// cloneCount ++;
		// if (cloneCount >1 ) //FP140623
		// return null;
		// else
		return result;
	}

	private String normalize(String str, boolean allowRandom) {

		if (str.isEmpty()) {
			if (allowRandom)
				str = RandomString.generateStringAlpha(6);
			else
				str = null;
		}

		if (str != null && !str.isEmpty()) {

			str = str.trim().toLowerCase();
			str = AccentRemover.getInstance().normalize(str);

			str = str.replaceAll("-", "t");
			str = str.replaceAll("_", "u");
			str = str.replaceAll(" ", "s");
			str = str.replaceAll("\\.", "d"); // TODO: check if starts with a
			// number, and so on...
		}
		return str;
	}

	public void setMatcherLeftPath_(String cp) {
		this.matcherLeftPath_ = cp;
		loggerLabel1.setText(cp == null ? "" : cp); // FP131116
	}

	public void setMatcherRightPath_(String cp) {
		this.matcherRightPath_ = cp;
		loggerLabel2.setText(cp == null ? "" : cp);// FP131116
	}

	public void refreshMatcherRightPathL_(String cp) {
		if (matcherRightPath_ == null
				|| (matcherRightPath_ != null && matcherRightPath_ != cp)) {
			setMatcherRightPath_(cp);
			refreshLanguageCombos();
		}
	}

	public void refreshMatcherLeftPathM_(String cp) {
		if (matcherLeftPath_ == null
				|| (matcherLeftPath_ != null && matcherLeftPath_ != cp)) {
			setMatcherLeftPath_(cp);
			if (this.matcherRightPath_ == null)
				setMatcherRightPath_(cp);
			refreshModelCombos(false);
		}
	}

	private void refreshModelRepositoryProject() {
		IProject mwbRepositoryProject = ResourcesPlugin
				.getWorkspace()
				.getRoot()
				.getProject(
						PathPreferences.getPreferences()
								.getInstanceRepositoryLocation()); // FP130518
		if (mwbRepositoryProject != null) {
			try {
				if (!mwbRepositoryProject.isOpen()) // this should not happen
					mwbRepositoryProject.open(new NullProgressMonitor());
				if (LOG)
					clog("refreshing "
							+ mwbRepositoryProject.getLocation().toOSString());
				mwbRepositoryProject.refreshLocal(IResource.DEPTH_INFINITE,
						new NullProgressMonitor());
			} catch (CoreException e) {
				e.printStackTrace();
				if (LOG)
					clog("should not happen in refreshModelRepositoryProject");
			}
		} else if (LOG)
			clog("no project for refreshModelRepositoryProject");
	}

	@Override
	public void refreshProject(String proj) {
		IProject aproj = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(proj);
		if (aproj != null) {
			try {
				if (!aproj.isOpen()) // this should not happen
					aproj.open(new NullProgressMonitor());
				org.isoe.fwk.utils.debug.WorkspaceUtils.getInstance()
						.refreshProject(proj, false);
				// aproj.refreshLocal(IResource.DEPTH_INFINITE,new
				// NullProgressMonitor());

			} catch (Exception e) {
				e.printStackTrace();
				cerror("should not happen in refreshProject");
			}
		} else if (LOG)
			clog("no project for refreshProject");

	}

	@Override
	public void refreshLanguageRepositoryProject() {
		IProject lwbRepositoryProject1 = getlwbRepositoryProject();
		boolean notjoin = false;
		if (lwbRepositoryProject1 != null)
			org.isoe.fwk.utils.debug.WorkspaceUtils.getInstance()
					.refreshProject(lwbRepositoryProject1.getName(), notjoin);
	}

	@Override
	public void refreshMegamodelProject() {
		IProject lwbRepositoryProject1 = getlwbRepositoryProject();
		boolean notjoin = false;
		if (lwbRepositoryProject1 != null)
			org.isoe.fwk.utils.debug.WorkspaceUtils.getInstance()
					.refreshProject(lwbRepositoryProject1.getName(), notjoin);
	}

	private IProject getlwbRepositoryProject() {
		IProject lwbRepositoryProject = ResourcesPlugin
				.getWorkspace()
				.getRoot()
				.getProject(
						PathPreferences.getPreferences()
								.getUniverseProjectName()); // FP130518
															// //FP131220
															// nawel
		if (lwbRepositoryProject != null && lwbRepositoryProject.exists()) {

			try {
				if (!lwbRepositoryProject.isOpen()) // this should not happen
					lwbRepositoryProject.open(new NullProgressMonitor());
				return lwbRepositoryProject;
			} catch (CoreException e) {
				cerror("should not happen in refreshLanguageRepositoryProject");
				e.printStackTrace();
				return null;
			}
		} else {
			cerror("the megamodel is missing !");
			return null;
		}
	}

	@Override
	public void sendCommand(String cmd) {
		if (CMD_REFRESH_LWB.equals(cmd))
			automationService.sendLWBQuery(cmd);
		else if (LOG)
			cerror("(3) unknown command: " + cmd);
	}

	protected void logMegamodelRepository(String target) { // FP130518
		URI uri = URI.createPlatformResourceURI(target, false);
		File repofolder = new File(CommonPlugin.resolve(uri).toFileString());
		File[] files = repofolder.listFiles();
		for (File file : files) {
			if (file.getName().startsWith("a0")) // FP130515zzz
				if (LOG)
					clog("RFMR " + file.getName());
		}
	}

	protected void refreshMegamodelRepository(String cmd) {
		if (languageConfiguration) {
			if (CMD_ARG_LWB.equals(cmd)) {
				sleep(100, "refreshMegamodelRepository start");
				String targetProject = PathPreferences.getPreferences()
						.getUniverseProjectName();
				String target = targetProject + "/"
						+ PathPreferences.getPreferences().getUniverseFolder();
				if (false)
					logMegamodelRepository(target);// FP130518
				refreshLanguageRepositoryProject(); // FP130518
				if (LOG)
					clog("refreshing "
							+ PathPreferences.getPreferences()
									.getUniverseProjectName());
				sleep(100, "refreshMegamodelRepository end");
			}
		}
	}

	@Override
	public void diagraphMegamodel_(boolean deployBuildInPlugin,
			boolean deployLocalPlugin, boolean deployLocalWorkspace,
			String languagesToDiagraph) {
		deployedLanguages_ = null;
		deployedProjects = null;
		deployMegamodelService__.setDeployBuildInPlugin(deployBuildInPlugin);
		deployMegamodelService__.setDeployLocalPlugin(deployLocalPlugin);
		deployMegamodelService__.setLanguageToAll(false);
		deployMegamodelService__.setAllprototypes(false);
		// deployMegamodelService.setLanguageToDeploy(languageToDeploy);
		deployMegamodelService__.setLanguagesToDiagraph(languagesToDiagraph);
		// deployMegamodelService.setOperation(operation);
		deployMegamodelService__.setDeployLocalWorkspace(deployLocalWorkspace);

		deployMegamodelService__.run();

		deployedLanguages_ = deployMegamodelService__.getDeployedLanguages();
		deployedProjects = deployMegamodelService__.getDeployedProjects();
		// FP140126td
		// saveUniverse();
	}

	@Override
	public void deployMegamodelClone_(boolean deployBuildInPlugin,
			boolean deployLocalPlugin, boolean deployLocalWorkspace,
			String languageToDeploy) { // boolean
										// generateEmfGen,
		deployedLanguages_ = null;
		deployedProjects = null;
		deployMegamodelService__.setDeployBuildInPlugin(deployBuildInPlugin);
		deployMegamodelService__.setDeployLocalPlugin(deployLocalPlugin);
		deployMegamodelService__.setLanguageToAll(false);
		deployMegamodelService__.setAllprototypes(false);
		deployMegamodelService__.setLanguageToDeploy(languageToDeploy);
		deployMegamodelService__.setDeployLocalWorkspace(deployLocalWorkspace);
		// deployMegamodelService.setGenerateEmfGen(generateEmfGen); //FP140415
		deployMegamodelService__.run();
		deployedLanguages_ = deployMegamodelService__.getDeployedLanguages();
		deployedProjects = deployMegamodelService__.getDeployedProjects(); // FP140126td
	}

	@Override
	public void deployMegamodel_(boolean deployBuildInPlugin,
			boolean deployLocalPlugin, boolean deployLocalWorkspace,
			boolean all, String filter) {
		deployedLanguages_ = null;
		deployedProjects = null;
		deployMegamodelService__.setLanguageToDeploy("");
		deployMegamodelService__.setLanguageFilter(filter);
		deployMegamodelService__.setLanguageToAll(false);
		deployMegamodelService__.setAllprototypes(all);
		deployMegamodelService__.setDeployBuildInPlugin(deployBuildInPlugin);
		deployMegamodelService__.setDeployLocalPlugin(deployLocalPlugin);
		deployMegamodelService__.setDeployLocalWorkspace(deployLocalWorkspace);
		deployMegamodelService__.run();
		deployMegamodelService__.setLanguageFilter(null);
		deployedLanguages_ = deployMegamodelService__.getDeployedLanguages();
		deployedProjects = deployMegamodelService__.getDeployedProjects();

		sleep(100, "deployMegamodel");
	}

	private void getBuildInPrototypes_() {
		boolean deployBuildInPlugin = true;
		boolean deployLocalPlugin = true;
		boolean deployLocalWorkspace = true;
		boolean refreshOnlyYes = true; // FP140602see
		boolean genLanguageNo = false;
		boolean isempty = textPrototype.getText().trim().isEmpty();
		if (isempty) {
			textPrototype.setText("give a name!");
			timedClear( 500, textPrototype);
			return;
		}
		if (textPrototype.getText().equals("autotest"))
			textPrototype.setText("");
		boolean build = btnBuildMeg.getSelection();
		String filter = textPrototype.getText();
		if (LOG)
			clog("GBI prototypes " + (build ? "build" : "nobuild") + " filter:"
					+ filter);
		deployMegamodel_(deployBuildInPlugin, deployLocalPlugin,
				deployLocalWorkspace, true, filter);// txtPutToLwb.getText());

		if (LOG) {
			clog("deployment result:"); // FP140126td
			for (IProject deplp : deployedProjects)
				clog("prj=" + deplp.getName());

			for (String depll : deployedLanguages_)
				clog("prj=" + depll);

		}

		boolean headless = false;
		// if (DO_MEGAMODEL_CHECK_STATUS_AFTER_PROTOTYPES) //FP131112
		if (build) { // FP140126td //FP131207

			String[] args = new String[2];
			args[0] = IDiagraphAlphabet.MEGAMODEL_CHECK_STATUS;
			args[1] = "*"; // FP140605

			String result = invokeMegamodelJob(null, headless, args,// IDiagraphAlphabet.MEGAMODEL_CHECK_STATUS
																	// + " *",
					genLanguageNo, refreshOnlyYes); // FP130823
		}
		textPrototype.setText("");
	}



	private List<String> getModelProjectNames() {
		List<String> result = new ArrayList<String>();
		List<String> prjs = ResourceUtils.getOpenedProjects();
	    String prefix = DiagraphPreferences.getTeamName__();
		for (String prj : prjs) {
			if (prj.startsWith(prefix) && !prj.endsWith("instances_models")&& !prj.endsWith("_test")){
				try {
					prj = prj.substring(prefix .length()+1);
                    result.add(prj);
				} catch (Exception e) {
					cerror("error while getting projects " + prj);
				}
			}
		}
		return result;
	}




	protected void consolidateModels(String sourcePlugin, String sourceFolder, String targetfolder) {

		 if (LOG)
				clog("consolidate all items to the repository");





		//String repositorypath = "platform:/plugin/" + sourcePlugin + "/"
		//		+ PathPreferences.getPreferences().getModelInUniverseFolder();
		String repositorypath = "platform:/plugin/" + sourcePlugin ;

		URI repositorypathURI_ = URI.createURI(repositorypath);
		String p = CommonPlugin.resolve(repositorypathURI_).toFileString();
		 //C:\apps\lunamod_pre_diagraph\eclipse\..\..\..\workspaces_mars\ws_mar_kit_7\_megamodel\src\
		File megafoldr_=  new File(CommonPlugin.resolve(repositorypathURI_).toFileString());



		File mmmm = null;
		if (megafoldr_.exists()) {
			mmmm = new File(megafoldr_ + File.separator + targetfolder);
			if (!mmmm.exists())
				mmmm.mkdir();
		}


		File ws =  new File(CommonPlugin
				.resolve(
						URI.createURI("platform:/resource/")).toFileString());

      //C:\workspaces_mars\mwb-2015-05-10



        String prefix=DiagraphPreferences.getTeamName__();

		File instancesfolder = new File(CommonPlugin
				.resolve(
						URI.createURI("platform:/resource/"+prefix+".instances_models")).toFileString());




		//mmmm = new File(megafoldr + File.separator + targetfolder);
		if (mmmm.exists())
		for (String model : getModelProjectNames()) {
			try {

				String result = copyModelsAndDiagrams(ws,mmmm,model);
				if (LOG)
					clog(result);
			} catch (Exception e) {
				cerror("error while consolidating " + model);
			}
		}
	}

	public void consolidate___(URI repositorypathURI, String sourcePlugin,
			String sourceFolder, String language) {
		if (LOG)
			clog("consolidate " + repositorypathURI + ";" + sourcePlugin + ";"
					+ sourceFolder + ";" + language);
		URI ruri = CommonPlugin.resolve(repositorypathURI);
		if (ruri == null)
			cerror("error in consolidate, " + repositorypathURI
					+ " not exists");
		if (LOG)
			clog(ruri.toFileString()); // E:\Apps\workspaces\ws-integr-9a\_megamodel\src\repository\
										// -
										// E:\Apps\workspaces\ws-integr-9a\_megamodel\src\repository\megamodel.megamodel

		if (language.equals("megamodel")) { // FP131003
			//consolidateMegamodel();
		}

	}


	void consdqsfd(){

			if (LOG)
				clog("consolidation");

/*
			List<String> copied = copyModels(
					controler, target, sourcePlugin,
					sourceFolder, consolidate, monitor);// FP130517

			if (LOG)
				for (String copy : copied) //FP140320
					clog("REPLC " +copy + " has been created");
						*/

		    sendCommand(IDiagraphControler.CMD_REFRESH_LWB);

	}

	public String copyModelsAndDiagrams(File ws, File mmmm, String model) { // FP150510


		    clog("copyModels " + model);
			copyModels(ws.getAbsolutePath(),model,mmmm.getAbsolutePath());



		return "ok";
	}


	private void copyModels(String workspace, String model, String targetDir){
		String tns = PathPreferences.getPreferences().getTeamNamespace();
		String extension = "."+model.split("\\.")[1];
		String modeldir = workspace + File.separator + tns+"." + model + File.separator+ "model";
        File modelfoldr = new File(modeldir);
        if (!modelfoldr.exists()  || !modelfoldr.isDirectory())
        	throw new RuntimeException("should not happen in copymodel");
        for (File f_ : modelfoldr.listFiles())
			if (f_.getName().endsWith(extension) || (f_.getName().contains(extension) && f_.getName().endsWith("_diagram")))
				copyfile(targetDir+File.separator+f_.getName() ,f_);

	}

/**
 *
 * @param sourcePlugin _megamodel
 * @param sourceFolder  repository
 * @param language
 */
	public void invokeConsolidation_(String sourcePlugin, String sourceFolder) { //FP150510

		String repositorypath = "platform:/plugin/" + sourcePlugin + "/"
				+ PathPreferences.getPreferences().getModelInUniverseFolder();

		URI repositorypathURI_ = URI.createURI(repositorypath);
		String p = CommonPlugin.resolve(repositorypathURI_).toFileString();

		try {
			deployModelService_.setSourceFolder(sourceFolder);
			deployModelService_.setSourcePlugin(sourcePlugin);
			deployModelService_.setDirection(true);
			deployModelService_.setMode(false);
			// deployModelService.forceOverwrite(overwrite);
			deployModelService_.setLanguage("");
			megamodelBuilder.consolidate(repositorypathURI_, sourcePlugin,
					sourceFolder, "megamodel");
			deployModelService_.run();
			sleep(100, "consolidate");
		} catch (Exception ex) {
			// showMessage("error " + i + " on consolidation (2) !!!");
			cerror("error " + " on consolidation (2) !!!"); // FP131002
		}

	}


	@Override
	public void consolidate(String sourcePlugin, String sourceFolder,
			String language_nu) { //FP150510

		if (!DParams.MEGAMODEL_CONSOLIDATE){
			cinfo("no consolidation see DParams.MEGAMODEL_CONSOLIDATE");
			return;
		}
		String repositorypath = "platform:/plugin/" + sourcePlugin + "/"
				+ PathPreferences.getPreferences().getModelInUniverseFolder();

		// E:\Apps\workspaces\ws-integr-9a\_megamodel\src\repository\megamodel.megamodel
		URI repositorypathURI_ = URI.createURI(repositorypath);
		String p = CommonPlugin.resolve(repositorypathURI_).toFileString();

		p = p == null ? (megamodeluri == null ? megamodelPath : megamodeluri)
				: p;

		if (LOG) {
			clog("language = " + getLanguageUri());
			clog("consolidate model to " + p);//C:\apps\lunamod_pre_diagraph\eclipse\..\..\..\workspaces_mars\ws_mar_kit_7\_megamodel\src\
		}

		if (p == null) {
			showMessage("Bad Configuration (2), no repository: "
					+ PathPreferences.getPreferences()
							.getModelInUniverseFolder() + " does not exist !!!");
		} else if (LOG)
			clog("repositorypathURI = " + p);
		try {

			invokeConsolidation_(sourcePlugin, sourceFolder);
			consolidateModels(sourcePlugin, sourceFolder, "m2");
			sleep(100, "consolidate");
		} catch (Exception ex) {
			// showMessage("error " + i + " on consolidation (2) !!!");
			cerror("error " + " on consolidation (2) !!!"); // FP131002
		}

	}

	private void deployModels_(String sourcePlugin, String sourceFolder,
			String languageToDeploy, boolean mode) { //FP150510

		// boolean forceOverwrite = true;
		boolean buildin = sourcePlugin.equals(MEGAMODEL_DEPLOYER_PLUGIN);
		if (buildin)
			if (LOG || ModelDeployService_LOG)
				clog("********** BUiLDIN **************");
		if (languageToDeploy.equals(PROMPT_LANG_TO_DEPLOY))
			languageToDeploy = "";

		URI uri = URI.createPlatformPluginURI(
				sourcePlugin + "/" + sourceFolder, false);

		String repositorypath = CommonPlugin.resolve(uri).toFileString();

		if (LOG || ModelDeployService_LOG)
			clog("deploy models from " + repositorypath);

		if (repositorypath == null)
			showMessage("Bad Configuration (1), no repository: "
					+ repositorypath + " does not exist !!!");
		try {
			deployModelService_.setSourceFolder(sourceFolder);
			deployModelService_.setSourcePlugin(sourcePlugin);
			deployModelService_.setDirection(false);
			deployModelService_.setMode(mode);
			deployModelService_.setLanguage(languageToDeploy);
			// deployMegamodelInstanceService.forceOverwrite(forceOverwrite);
			if (LOG || ModelDeployService_LOG)
				clog("run deployModelService ");
			deployModelService_.run();
			sleep(100, "deployModels");
		} catch (Exception ex) {
			showMessage("not in a modeler configuration (2a) !!!");
		}
	}

	private boolean notGenEmfGmf_nu() {
		return !DiagraphPreferences
				.getBooleanPreference(KEY_EMFGMF_GENERATION_);
	}

	private String getInstanceRepositoryPath() {
		if (instanceRepositoryPath == null) {
			instanceRepositoryPath = PathPreferences.getPreferences()
					.getInstanceRepositoryPath();
			instanceRepositoryPath = instanceRepositoryPath.replaceAll("/",
					Separator.SEPARATOR);
		}
		return instanceRepositoryPath;
	}

	private String getInstanceRepositoryLocation() {
		if (instanceRepositoryLocation == null) {
			instanceRepositoryLocation = PathPreferences.getPreferences()
					.getInstanceRepositoryLocation();
		}
		return instanceRepositoryLocation;
	}

	/*------------------------------------------------------*/

	@Override
	public void refreshPathes(String folder) {
		if (languageConfiguration) {
			if (folder.endsWith(MODEL_FOLDER)
					|| folder.endsWith(DiagraphPreferences
							.getStringPreference(KEY_UNIVERSE_FOLDER)))
				refreshMatcherRightPathL_(folder + File.separator);
		} else if (modelConfiguration) {
			if (folder.endsWith(getInstanceRepositoryPath()))
				refreshMatcherLeftPathM_(folder + File.separator);
		}
	}

	@Override
	public Diagram getDiagramToLayout() {
		Diagram result = diagraphGenerator.getWorkdiagram();
		String pname = ((EPackage) result.getElement()).getName();
		if (focusedlanguage != null && !pname.equals(focusedlanguage)){
			cerror("DiagramToLayout pb: " + pname + " VS " + focusedlanguage);
			restoreListeners(true);//FP150528
		}
		if (LanguageTransformer_4_LOG) {
			clog4("AKW DiagramToLayout=" + pname);
			if (focusedlanguage == null)
				cerror("AKW ******  focusedlanguage is null");
			if (focusedlanguage != null && !pname.equals(focusedlanguage)) {
				cerror("AKW ******    DiagramToLayout pb: " + pname + " VS "
						+ focusedlanguage);
				Diagram cur = diagraphGenerator.getCurrentDiagram();
				Diagram sel = diagraphGenerator.getSelectedDiagram();
				String curd = cur == null ? "" : ((EPackage) cur.getElement())
						.getName();
				String seld = sel == null ? "" : ((EPackage) sel.getElement())
						.getName();
				cerror("AKW ******    DiagramToLayout pb: " + pname + " VS "
						+ focusedlanguage + " c=" + curd + " s=" + seld);
			}
		}
		return result;
	}

	@Override
	public String getLanguageName() {
		return langageName;
	}

	@Override
	public String getFocusedLanguage() {
		return focusedlanguage;
	}

	@Override
	public void setFocusedLanguage(String apath) {
		focusedlanguage = apath;
	}

	@Override
	public EPackage getCurrentPackage() {// FP140604
		if (currentPackage == null)
			bringToTop(getDsmlProjects().get(0));
		if (currentPackage == null)
			throw new RuntimeException(
					"should not happen in getCurrentPackage()");
		if (currentPackage.eResource() == null) {
			cerror("Resource'/" + currentPackage.getName()
					+ "'+ does not exist");// ;//refreshProject(currentPackage.getName());
											// //FP140628aa
			currentPackage = null;
			bringToTop(getDsmlProjects().get(0)); // FP140701voir
		}
		return currentPackage;
	}

	@Override
	public Diagram getEcoreDiagram(String name) {// FP140604
		IProject project = findProject(name);
		if (project == null)
			throw new RuntimeException("no project for language " + name);
		IEditorPart opened = bringToTop(project);
		if (currentPackage == null)
			throw new RuntimeException("should not happen in getPackage()");
		return ((EcoreDiagramEditor) opened).getDiagram();
	}

	/*
	 * private IEditorPart bringToTop(IProject project, String langName) {
	 *
	 * langName = langName.substring(langName.lastIndexOf(".")+1); IFile
	 * diagramfile = ResourceUtils.getFile(project, "model" + "/" + langName,
	 * "ecorediag");//errstatechart.ecorediag IWorkbenchPage page =
	 * PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage(); try
	 * { IEditorPart openedEditor = IDE.openEditor(page, diagramfile);
	 * page.bringToTop(openedEditor); return openedEditor; } catch
	 * (PartInitException e) { clog("Open editor failed: " +
	 * diagramfile.toString() + " -- " + e); return null; }
	 *
	 * }
	 */

	@Override
	public void bringToTop(EPackage p) {
		String language = p.getName();
		String view = getRegisterdView(language);

		Diagram newd_ = navigatorHelper.bringToTop(language);
		Diagram old_ = diagraphGenerator.getCurrentDiagram();

		// DGraph cu =
		context.getCurrentDiagraph(newd_, old_, view);

		// String curnt =
		context.updateCurrentDiagraph(newd_, old_, view);
		String curnt = context.getCurrentAsString(language + "." + view);
		updateLanguageToTransform_(curnt);// FP130613
		registerCurrentView(language, view, 487);
	}

	@Override
	public Diagram focusDiagram(String language) {
		String view = getRegisterdView(language);
		Diagram d = navigatorHelper.bringToTop(language);
		// updateLanguageToTransform_(d,p);// FP130613
		registerCurrentView(language, view, 488);
		return d;
	}

	@Override
	public EPackage get(String language) {
		return (EPackage) focusDiagram(language).getElement();
	}

	private IProject findProject(String name) {
		List<IProject> projects = getDsmlProjects();
		for (IProject project : projects) {
			String langName = project.getName();
			langName = langName.substring(langName.lastIndexOf(".") + 1);
			if (name.equals(langName))
				return project;
		}
		return null;
	}

	private IProject firstProject() {
		List<IProject> projects = getDsmlProjects();
		return projects.isEmpty() ? null : projects.get(0);
	}

	private void removeListeners_nu(int delay) { // FP140522
		if (LanguageTransformer_4_LOG)
			clog4("AKW  ---  listeners removed");
		removeListeners_();
		timedRestoreListeners_nu(delay);
	}

	@Override
	public boolean update(IWorkbenchPart part, ISelection selection) {
		boolean result = false;
		if (modelConfiguration)
			result = updateModel(part, selection);
		else if (languageConfiguration)
			result = updateLanguage(part, selection);
		if (result)
			lblChange.setText("" + changes++);
		return result;
	}



	@Override
	public String getMetadata(String id) {
		String result = "";
		try {
			result = currentPackage == null ? "" : readFile(
					getPathUnderModel(id, "txt"), ";");
		} catch (Exception e) {
			result = "";
		}
		if (result == null)
			result = "";
		return result;
	}

	@Override
	public Diagram getChangedDiagram() {
		return changedDiagram;
	}

	private String[] getProjectLocation(EPackage pak) { // FP140117

		if (pak == null) {
			cerror("no package (in locateProject)");
			return null;
		}
		if (!ResourceUtils.isDiagraphedM2(pak)
				|| !ResourceUtils.isProjectWellNamed(pak)) {
			cerror("not diagraphed (in locateProject)");
			clearLanguage();// FP131130
			return null; // FP140227
		}
		URI ecoreFileURI = null;
		try {
			ecoreFileURI = CommonPlugin.resolve((pak).eResource().getURI());
		} catch (Exception e) {
			cerror("error (1) in locateProject");
			return null;
		}

		String modelFolderPath = null;
		String projectName = null;
		try {

			modelFolderPath = ecoreFileURI.toFileString();
			modelFolderPath = modelFolderPath.substring(0,
					modelFolderPath.lastIndexOf(MODEL_FOLDER))
					+ MODEL_FOLDER + File.separator;
			projectName = ecoreFileURI.toString();
			projectName = projectName.substring(0,
					projectName.indexOf("/" + MODEL_FOLDER + "/"));
			projectName = projectName
					.substring(projectName.lastIndexOf("/") + 1);
		} catch (Exception e) {
			cerror("error (2) in locateProject");
			return null;
		}
		String[] result = new String[2];
		result[0] = modelFolderPath;
		result[1] = projectName;
		return result;

	}

	private boolean setupLanguage(EPackage pak, Diagram newd, String view) { // FP130928
		boolean result_ = true;

		if (LOG5)
			clog5("setupLanguage " + pak.getName());
		String[] loc = getProjectLocation(pak);
		if (loc == null) {
			clearLanguage(); // FP140227
			if (merge_LOG)
				clogmerge("project not located (" + pak.getName() + "");
			return false;
		}
		String path = loc[0];
		String projectName = loc[1];
		changedDiagram = null;
		lblEclass.setText("");
		clearForm(pak.getName(), view); // FP140611b// FP130710
		currentEObject = null;
		currentElement = null;
		currentGraphicalEditPart = null;
		lblPov.setText(view);
		// lblPovId_.setText("0");
		// lblCPov.setText(view);
		// lblCPovId_.setText("0");
		elemenQueue = new Queue<EModelElement>(100, false);
		elementStack = null;
		currentPackage = pak;
		if (LOG)
			logEObject(currentPackage);
		setLanguageName(currentPackage.getName()); // FP140527
		langageNsUri = currentPackage.getNsURI();
		langageNsPrefix = currentPackage.getNsPrefix();
		// currentDiagram_ = null; //FP140610
		setMatcherLeftPath_(path);
		txtLanguageName.setText(langageName);
		lblArtefactName.setText(langageName + getPovSuffix());
		txtLanguageNsUri.setText(langageNsUri);
		txtLanguageNsPrefix.setText(langageNsPrefix);
		txtPutToLwb.setText(langageNsUri);
		textLangageVersion.setText("");
		notUnder = false;
		if (!isMerging())
			txtClone.setText("");
		textDesign.setText("");
		// cloneLanguage ="";
		logDiagraph = "";
		// megamodelBuilder.saveRepository();
		megamodelBuilder.setup(pak);
		if (building){
			languageStartBuild(pak);

		}
		Diagram old_ = diagraphGenerator.getCurrentDiagram();
		context.getCurrentDiagraphs(newd, old_);
		List<DGraph> graphs=context.getCurrentDiagraphs();
		String views="";
		for (DGraph dGraph : graphs)
			views+=dGraph.getViewName()+", ";
		views = views.trim();
		views = views.substring(0, views.length()-1);
		cinfo("current language = "+pak.getName()+" views = "+views);
		if (building)
			logGeneration(" views = "+views);

		// context.getCurrentDiagraph(newd_,old_,layerHelper.getCurrentView());//
		// FP140612
		// FP140626zzz
		// String
		// curnt=pak.getName()+"."+layerHelper.getCurrentView();//context.updateCurrentDiagraph(newd_,old_,layerHelper.getCurrentView());

		String curnt = context.getCurrentAsString(pak.getName() + "." + view);// layerHelper.getCurrentView());

		if (!isMerging())
			context.updateCurrentDiagraph(newd, old_, view);

		String mesg = updateLanguageToTransform_(curnt);// pak,
		if (mesg.equals("error: no current diagraph"))
			result_ = false;
		if (LOG_PARSED && result_)
			writeFile_(path + langageName + "." + "parsed", logDiagraph);
		if (runningCmd == null) {
			interpreter_.clear();
			// 147 textDiagraphStatements.setText("");
		}
		refreshProject(projectName);
		megamodelBuilder.done(projectName);
		if (LOG)
			clog("SCED" + " setupLanguage" + langageName
					+ (result_ ? " setup" : " not setup"));
		return result_;
	}

	private void setLanguageName(String name) {
		langageName = name;
	}

	@Override
	public boolean isMerging() {
		return checkPinToMerge.getSelection();
	}

	private void clearLanguage() {
		if (LOG5)
			clog5("clearLanguage");
		ecoreDiagramEditor = null; // FP131018
		ecoreSelection = null; // FP131018
		if (!isMerging()) {

			lblLanguageToMerge_.setText("");
			if (context.isMerging())
				context.endMerge();
			// if (merge_LOG)
			// clogmerge("merge end");
			// merging = false;
			lblEclass.setText("");
			lblCurrentPart.setText("");
			langageName = "";
			langageNsUri = "";
			langageNsPrefix = "";
			txtLanguageName.setText("");
			lblArtefactName.setText("");
			txtLanguageNsUri.setText("");
			txtLanguageNsPrefix.setText("");
			txtPutToLwb.setText("");
			textLangageVersion.setText("");
			notUnder = true;
			if (languageConfiguration) {
				txtClone.setText("not diagraphed");
				textDesign.setText("");// "under the project !!");
			}
			if (LOG)
				clog("SCED" + " clearLanguage");
		}
	}

	@Override
	public void done(IProject project) {
		if (LOG)
			clog("done " + project.getName());
	}

	private void logEObject(EObject tolog) {
		for (EObject cont : tolog.eContents())
			clog("urifragment=" + tolog.eResource().getURIFragment(cont));// FP130520
	}

	private boolean updateModel(IWorkbenchPart part, ISelection selection) {
		if ((part instanceof DiagramEditor)
				&& !(part instanceof EcoreDiagramEditor)) {
			if (diagramChanged((DiagramEditor) part)) {// FP130513
				refreshState();

				try {
					if (prevObj != null) {
						String uri = prevObj.eResource().getURI().toString();
						if (setCurrentModel(uri
								.substring(uri.lastIndexOf("/") + 1)))
							return true;
					}
				} catch (Exception e) {
					cerror("error while updateModel");
					return false;
				}

			}
		}
		return false;
	}

	private boolean languageOk(EPackage packag) {
		if (packag != null && packag.eResource() != null) {
			URI uri = packag.eResource().getURI();
			String suri = uri.toString();
			String pluginid = suri.substring("platform:/resource/".length());
			pluginid = pluginid.substring(0, pluginid.indexOf("/model/"));
			String lang = pluginid.substring(pluginid.lastIndexOf(".") + 1);
			if (lang.equals(packag.getName()))
				return true;
		}
		return false;
	}

	@Override
	public void checkLanguage() {
		if (langageName == null || langageName.isEmpty()) // FP131116 inverse
			initForLanguageConfig(); // FP130618
	}

	private void pushEModelElement(EModelElement me) {
		elemenQueue.add(me);
		int qsize = elemenQueue.size();
		if (qsize > QUEUE_SIZE)
			elemenQueue.remove();
		String t = "";
		int stackidx = Math.min(QUEUE_SIZE, qsize);
		elementStack = new EModelElement[stackidx];
		Iterator<EModelElement> it = elemenQueue.iterator();
		while (it.hasNext()) {
			elementStack[stackidx - 1] = (EModelElement) it.next();
			stackidx--;
		}
		t = "";
		for (EModelElement b : elementStack)
			t += currentToString(b) + " - ";
		lblStack.setText(t);
		if (LOG) {
			String es = "stack=" + "[";
			for (EModelElement element : elementStack)
				es += currentToString(element) + ";";
			es += "]";
			clog(es);
		}
	}

	private String currentToString(EModelElement me) {
		if (me instanceof ENamedElement)
			return ((ENamedElement) me).getName();
		else if (me instanceof EAnnotation) {
			if (((EAnnotation) me).eContainer() instanceof EClass)
				return ((EClass) ((EAnnotation) me).eContainer()).getName()
						+ ".diagraph.annotation";
		}
		return "";
	}

	@Override
	public EModelElement[] getElementStack() {
		return elementStack;
	}

	private boolean updateLanguage(IWorkbenchPart part, ISelection selection) { // FP130606
		boolean result = false;
		if (part instanceof EcoreDiagramEditor) {
			if (diagramChanged((EcoreDiagramEditor) part)
					|| langageName == null || langageName.isEmpty()) {
				refreshState();

				refreshLanguageCombos();
				// initForLanguageConfig();
				// clearCurrentEClass();
				result = true;

			}
			if (notUnder)
				initForLanguageConfig();
		}
		return result;
	}

	private EPackage getModel(Diagram diag) {
		EPackage pakage = null;
		try {
			TransactionalEditingDomain ted = TransactionUtil
					.getEditingDomain(diag);
			if (!ted.getID().startsWith(ECORETOOLSEDITINGDOMAIN_ID)) {
				String msg = "must be an Ecore Diagram ";
				log(1, msg);
				logError(7, msg);
				log("get model error", msg);
				return null;
			}
			String abspath = ted.getID().substring(
					ECORETOOLSEDITINGDOMAIN_PREFIX_LENGTH);
			String pref = abspath.substring(0, abspath.lastIndexOf("/"));
			String modelFolder = pref.substring(pref.lastIndexOf("/") + 1);
			if (!modelFolder.equals(MODEL_FOLDER)) {
				String msg = "(3) model must be under " + MODEL_FOLDER
						+ "/ but is currently under " + modelFolder + "/";
				log(1, msg);
				logError(8, msg);
				log("working location error", msg);
				return null;
			}
			boolean result = (diag.getElement() instanceof EPackage);
			if (result) {
				pakage = (EPackage) diag.getElement();
				pref = pref.replaceAll("/", Separator.SEPARATOR);
				setMatcherLeftPath_(pref + File.separator);
			}
			return pakage;
		} catch (Exception e) {
			String msg = null;
			if (pakage != null) {
				if (pakage.getName().isEmpty() || pakage.getName() == null)
					msg = "package without name in the ecore file";
				else
					msg = "(4) model must be under " + MODEL_FOLDER
							+ "/ error while checking ";
				log(1, msg);
				logError(10, msg);
				log("working location error", msg);
			} else {
				msg = "not diagraphed";
				log(1, msg);
				logError(11, msg);
				log("working location error", msg);
			}
			return null;
		}
	}

	private void logError(int i, String msg) {
		if (LOG)
			clog(msg);
	}

	private void logSelected(int changed) {
		String[] views = diagraphGenerator.getViews();
		if (views != null) {
			String log = "";
			for (String view : views)
				log += view + " - ";
			if (LOG)
				clog(" event result: " + changed + " views=" + log);
		} else if (LOG)
			clog(" event result: " + changed);
	}

	// @Override
	public void refreshTree() {
		// getViewSite().getPage().removeSelectionListener(this);
		// TreePath[] tp = treeViewer.getExpandedTreePaths();
		// treeViewer.getContentProvider().inputChanged(treeViewer, null, null);
		// treeViewer.refresh();
		// treeViewer.setExpandedTreePaths(tp);
		// getViewSite().getPage().addSelectionListener(this);

	}

	class LayAction extends Action {// FP131119
		public LayAction() {
			this.setId("org.isoe.fwk.runtime.diagraph.layout.action.LayoutAction");
		}
	}


	private void timedSave(final int delay) { //FP150524
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				try {
					sleep(delay, "timedSave");
					saveAllEditors();
				} catch (Exception e) {
				}
			}
		});
	}

	private void timedClear(final int delay,
			final Text toClear) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				try {
					sleep(delay, "timedClear");
					toClear.setText("");
				} catch (Exception e) {
				}
			}
		});
	}

	/*
	 * private void timedFocus_nu(final EcoreDiagramEditor ecoreDiagramEditor) {
	 * // FP131110 Display.getDefault().asyncExec(new Runnable() { public void
	 * run() { try { sleep(100,"timedFocus_nu"); if
	 * (setCurrentLanguageDiagram(ecoreDiagramEditor.getDiagram())){
	 * //FP140227// FP131130a DiagramEditPart part = ecoreDiagramEditor
	 * .getDiagramEditPart(); IWorkbenchPage page = getActivePage(); if (page !=
	 * null) { page.bringToTop(ecoreDiagramEditor); } } } catch (Exception e) {
	 * } } });
	 *
	 * }
	 */

	private List<Dsml> getMegamodelDsmls() {// FP140622voiraaa

		List<Dsml> result = megamodelMan.getDsmls();
		if (Initialisation_LOG) {
			String log = "";
			for (Dsml dsml : result) {
				log += dsml.getName()
						+ (dsml.getNotations_().isEmpty() ? " no view " : ("["
								+ dsml.getNotations_().size() + " views ] "))
						+ "; ";
			}
			initclog("getMegamodelDsmls: " + log);
		}
		return result;
	}

	private void initclog(String mesg) {
		if (Initialisation_LOG)
			System.out.println(mesg);
	}

	protected void checkData() { // FP140624
		getMegamodelDsmls();
	}

	private void timedClear(final int delay,
			final Composite composite) { // FP130606
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				try {
					sleep(delay, "timedClear");
					if (composite instanceof Combo)
						((Combo) composite).setText("");
					else if (LOG)
						clog("unable to clear" + composite.toString());
				} catch (Exception e) {
				}
			}
		});

	}

	/* --------------------------------------------------- */

	public void createPartControl(Composite parent_) {
		// this.parentComposite_nu = parent;
		Composite cdev = null;
		// parent.setLayout(new BorderLayout(0, 0));

		/*-----------------------------------*/

		diagraphFolder = new TabFolder(parent_, SWT.NONE);// az
		compositeHidden = new Composite(diagraphFolder, SWT.NONE);
		if (!DEV_CONFIG)
			compositeHidden.setVisible(false); // FP130606
		cdev = compositeHidden;
		compositeTabOp = new Composite(diagraphFolder, SWT.NONE);// new
																	// Composite(diagraphFolder,
																	// SWT.NONE);
		compositeTabOp.setLayout(null);

		compositeTabEditor = new Composite(diagraphFolder, SWT.NONE);
		compositeTabEditor.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_DARK_GRAY));

		tabActions = new TabItem(diagraphFolder, SWT.NONE);// az
		tabActions.setText("Actions");// az
		tabActions.setControl(compositeTabOp);// az

		tabConsole = new TabItem(diagraphFolder, SWT.NONE);// az
		tabConsole.setText("Console");// az

		tabConsole.setControl(compositeTabEditor);// az
		compositeTabEditor.setLayout(new GridLayout(1, false));

		composite_10 = new Composite(compositeTabEditor, SWT.NONE);
		GridData gd_composite_10 = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 1);
		gd_composite_10.heightHint = 26;
		gd_composite_10.widthHint = 580;
		composite_10.setLayoutData(gd_composite_10);

		btnExecute = new Button(composite_10, SWT.NONE);
		btnExecute.setBounds(0, 0, 75, 25);
		btnExecute.setText("Execute !");

		textDiagraphStatements = new Text(compositeTabEditor, SWT.BORDER
				| SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL
				| SWT.MULTI);
		GridData gd_textDiagraphStatements = new GridData(SWT.FILL, SWT.CENTER,
				true, false, 1, 1);
		gd_textDiagraphStatements.heightHint = 46;
		textDiagraphStatements.setLayoutData(gd_textDiagraphStatements);

		int heightHint_ = 250;
		int widthHint_ = 410;

		composite_txtsyntax = new Composite(compositeTabEditor, SWT.NONE);
		// composite_txtsyntax.setFont(SWTResourceManager.getFont("Courier New",
		// 9, SWT.NORMAL));
		// composite_lwer.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		composite_txtsyntax.setLayout(new GridLayout(2, false));
		GridData gd_composite = new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1);
		gd_composite.heightHint = heightHint_ + 4;
		composite_txtsyntax.setLayoutData(gd_composite);

		Color bg = Display.getDefault().getSystemColor(SWT.COLOR_GRAY);
		Font courierFont9 = new Font(getShell().getDisplay(), "Courier", 9,
				SWT.NORMAL);
		Font courierNewFont9 = SWTResourceManager.getFont("Courier New", 9,
				SWT.NORMAL);// new
							// Font(getShell().getDisplay(),
							// "Courier New",
							// 9,
							// SWT.NORMAL);

		textDiagraphResponse_ = new StyledText(composite_txtsyntax,
				SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		// textDiagraphResponse_.setFont(SWTResourceManager.getFont("Courier New",
		// 9, SWT.NORMAL));
		GridData gd_1 = new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1);
		gd_1.heightHint = heightHint_;
		gd_1.widthHint = widthHint_;
		gd_1.horizontalAlignment = GridData.FILL;
		gd_1.verticalAlignment = GridData.FILL;
		textDiagraphResponse_.setLayoutData(gd_1);
		// textDiagraphResponse_.addLineStyleListener(new
		// DiagraphLineStyler(IDiagraphAlphabet.DIAGRAPH_KEYWORDS));
		textDiagraphResponse_.setBackground(bg);
		// textDiagraphResponse.setFont(courierNewFont9);

		textDiagraphCmds_ = new StyledText(composite_txtsyntax, SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.MULTI);
		GridData gd_2 = new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1);
		gd_2.heightHint = heightHint_;
		gd_2.widthHint = widthHint_;
		gd_2.horizontalAlignment = GridData.FILL;
		gd_2.verticalAlignment = GridData.FILL;
		textDiagraphCmds_.setLayoutData(gd_2);
		// textDiagraphCmds.addLineStyleListener(new
		// DiagraphLineStyler(IDiagraphAlphabet.DIAGRAPH_COMMANDS));
		textDiagraphCmds_.setBackground(bg);
		// textDiagraphCmds.setFont(courierFont9);

		// exec=generate sans templates

		// emfatic

		/*-----------------------------------*/

		/*-----------------------------------*/

		panelUpper = new Composite(compositeTabOp, SWT.NONE);
		panelUpper.setBounds(0, 0, 1000, 230);
		panelUpper.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		panelUpper.setLayout(null);

		if (!DEV_CONFIG)
			panelUpper.setVisible(false); // FP130606

		/*-----------------------*/

		composite_R2 = new Composite(panelUpper, SWT.NONE);
		composite_R2.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		composite_R2.setBounds(0, 92, 1000, 57);

		composite_13 = new Composite(composite_R2, SWT.NONE);
		composite_13.setLayout(null);
		composite_13.setBounds(682, 0, 308, 57);

		btnDotify_1 = new Button(composite_13, SWT.NONE);
		btnDotify_1.setText("Dotify !");
		btnDotify_1.setBounds(161, 22, 85, 25);

		lblaGenerateGraphviz = new Label(composite_13, SWT.NONE);
		lblaGenerateGraphviz.setText("4a. Generate Graphviz (experimental)");
		lblaGenerateGraphviz.setFont(SWTResourceManager.getFont("Segoe UI", 9,
				SWT.BOLD));
		lblaGenerateGraphviz.setBounds(9, 0, 257, 15);

		comboGraphViz = new Combo(composite_13, SWT.NONE);
		comboGraphViz.setBounds(9, 23, 145, 23);

		composite_12 = new Composite(composite_R2, SWT.NONE);
		composite_12.setLayout(null);
		composite_12.setBounds(121, 0, 308, 57);

		btnNewPov = new Button(composite_12, SWT.NONE);
		btnNewPov.setText("New !");
		btnNewPov.setBounds(242, 22, 51, 25);

		textNewPov = new Text(composite_12, SWT.BORDER);
		textNewPov.setBounds(162, 22, 76, 25);

		lblNewPoint = new Label(composite_12, SWT.NONE);
		lblNewPoint.setText("6. New point of view");
		lblNewPoint
				.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lblNewPoint.setBounds(5, 0, 219, 15);

		comboPov_ = new Combo(composite_12, SWT.NONE);
		comboPov_.setToolTipText("_");

		comboPov_.setBounds(12, 23, 144, 23);

		composite_13a = new Composite(composite_R2, SWT.NONE);
		composite_13a.setLocation(430, 0);
		composite_13a.setSize(570, 57);
		composite_13a.setLayout(null);

		btnLayoutLayer = new Button(composite_13a, SWT.NONE);
		btnLayoutLayer.setText("Choose !");
		btnLayoutLayer.setBounds(161, 22, 85, 25);

		lblConcreteSyntax = new Label(composite_13a, SWT.NONE);
		lblConcreteSyntax.setText("7. Concrete syntax layers");
		lblConcreteSyntax.setFont(SWTResourceManager.getFont("Segoe UI", 9,
				SWT.BOLD));
		lblConcreteSyntax.setBounds(9, 0, 172, 15);

		comboSyntaxLayer = new Combo(composite_13a, SWT.NONE);
		comboSyntaxLayer.setBounds(9, 23, 145, 23);

		composite_11 = new Composite(composite_R2, SWT.NONE);
		composite_11.setLocation(0, 0);
		composite_11.setSize(120, 57);

		lblAdvancedFeatures = new Label(composite_11, SWT.NONE);
		lblAdvancedFeatures.setForeground(SWTResourceManager
				.getColor(SWT.COLOR_DARK_BLUE));
		lblAdvancedFeatures.setText("Advanced features");
		lblAdvancedFeatures.setFont(SWTResourceManager.getFont("Segoe UI", 9,
				SWT.BOLD));
		lblAdvancedFeatures.setBounds(5, 0, 112, 15);

		Composite composite_R1 = new Composite(panelUpper, SWT.NONE);
		composite_R1.setBounds(0, 0, 1000, 91);
		composite_R1.setLayout(null);
		composite_R1.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));

		composite_7 = new Composite(composite_R1, SWT.NONE);
		composite_7.setBounds(308, 0, 193, 91);
		composite_7.setLayout(null);

		btnDesign = new Button(composite_7, SWT.NONE);

		btnDesign.setText("Design !");
		btnDesign.setBounds(10, 16, 72, 22);

		textDesign = new Text(composite_7, SWT.BORDER);
		textDesign.setBounds(10, 40, 173, 21);

		lblDesignThe = new Label(composite_7, SWT.NONE);
		lblDesignThe.setText("2. Design the current language");
		lblDesignThe.setFont(SWTResourceManager
				.getFont("Segoe UI", 9, SWT.BOLD));
		lblDesignThe.setBounds(10, 0, 219, 15);

		btnLayoutAnots = new Button(composite_7, SWT.NONE);

		btnLayoutAnots.setText("Layout anots !");
		btnLayoutAnots.setBounds(88, 16, 95, 22);

		lblLanguageToMerge_ = new Label(composite_7, SWT.NONE);
		lblLanguageToMerge_.setBounds(10, 67, 173, 15);

		panelTu = new Composite(composite_R1, SWT.NONE);
		panelTu.setBounds(121, 0, 186, 91);
		panelTu.setLayout(null);

		btnCloneLanguage2 = new Button(panelTu, SWT.NONE);

		btnCloneLanguage2.setBounds(10, 16, 75, 22);
		btnCloneLanguage2.setText("Clone !");

		txtClone = new Text(panelTu, SWT.BORDER);
		txtClone.setBounds(10, 40, 165, 21);

		Label lblCloneALanguage = new Label(panelTu, SWT.NONE);
		lblCloneALanguage.setFont(SWTResourceManager.getFont("Segoe UI", 9,
				SWT.BOLD));
		lblCloneALanguage.setBounds(5, 0, 193, 15);
		lblCloneALanguage.setText("1. Clone the current language");

		btnMerge = new Button(panelTu, SWT.NONE);
		btnMerge.setBounds(100, 16, 75, 22);
		btnMerge.setText("Merge !");

		checkPinToMerge = new Button(panelTu, SWT.CHECK);
		checkPinToMerge.setBounds(82, 67, 104, 16);
		checkPinToMerge.setText("pin current :");

		composite_4 = new Composite(composite_R1, SWT.NONE);
		composite_4.setBounds(0, 0, 120, 91);

		lblArtefactName = new Label(composite_4, SWT.NONE);
		lblArtefactName.setForeground(SWTResourceManager
				.getColor(SWT.COLOR_DARK_RED));
		lblArtefactName.setText("no active language");
		lblArtefactName.setFont(SWTResourceManager.getFont("Segoe UI", 9,
				SWT.NORMAL));
		lblArtefactName.setBounds(5, 56, 122, 15);

		lblLanguageDesign_1 = new Label(composite_4, SWT.NONE);
		lblLanguageDesign_1.setForeground(SWTResourceManager
				.getColor(SWT.COLOR_DARK_BLUE));
		lblLanguageDesign_1.setText("Language under");
		lblLanguageDesign_1.setFont(SWTResourceManager.getFont("Segoe UI", 9,
				SWT.BOLD));
		lblLanguageDesign_1.setBounds(5, 21, 115, 15);

		lblDesign = new Label(composite_4, SWT.NONE);
		lblDesign.setForeground(SWTResourceManager
				.getColor(SWT.COLOR_DARK_BLUE));
		lblDesign.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lblDesign.setBounds(5, 38, 55, 15);
		lblDesign.setText("design");

		lblNewLabel = new Label(composite_4, SWT.NONE);
		lblNewLabel.setForeground(SWTResourceManager
				.getColor(SWT.COLOR_DARK_BLUE));
		lblNewLabel
				.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lblNewLabel.setBounds(5, 0, 115, 15);
		lblNewLabel.setText("Basic features");

		lblEclass = new Label(composite_4, SWT.NONE);
		lblEclass.setBounds(5, 75, 105, 15);

		composite_9 = new Composite(composite_R1, SWT.NONE);
		composite_9.setBounds(689, 0, 312, 91);

		btnLaunchMWB = new Button(composite_9, SWT.NONE);

		btnLaunchMWB.setText("Design !");
		btnLaunchMWB.setBounds(9, 17, 95, 25);

		textLaunchMDWB = new Text(composite_9, SWT.BORDER);
		textLaunchMDWB.setBounds(9, 48, 137, 25);

		lblDesignModels = new Label(composite_9, SWT.NONE);
		lblDesignModels.setText("5. Design models");
		lblDesignModels.setFont(SWTResourceManager.getFont("Segoe UI", 9,
				SWT.BOLD));
		lblDesignModels.setBounds(10, 0, 198, 15);

		composite_8 = new Composite(composite_R1, SWT.NONE);
		composite_8.setBounds(502, 0, 186, 91);

		btnAutomatedGramgen = new Button(composite_8, SWT.NONE);
		btnAutomatedGramgen.setText("Automated !");
		btnAutomatedGramgen.setBounds(83, 17, 95, 25);

		lblInferThe = new Label(composite_8, SWT.NONE);
		lblInferThe.setText("3. Infer the graphical notation");
		lblInferThe
				.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lblInferThe.setBounds(10, 0, 176, 15);

		lblHandyOr = new Label(composite_8, SWT.NONE);
		lblHandyOr.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lblHandyOr.setBounds(11, 22, 66, 15);
		lblHandyOr.setText("manual or ...");

		btnGenerateModeler = new Button(composite_8, SWT.NONE);
		btnGenerateModeler.setText("Generate !");
		btnGenerateModeler.setBounds(83, 59, 95, 25);

		lblGenerateA = new Label(composite_8, SWT.NONE);
		lblGenerateA.setText("4. Generate a modeler");
		lblGenerateA.setFont(SWTResourceManager
				.getFont("Segoe UI", 9, SWT.BOLD));
		lblGenerateA.setBounds(10, 42, 176, 15);

		Composite composite_R3 = new Composite(panelUpper, SWT.NONE);
		composite_R3.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		composite_R3.setBounds(0, 150, 1000, 81);

		composite_17 = new Composite(composite_R3, SWT.NONE);
		composite_17.setBounds(0, 0, 120, 78);

		lblMegamodelFeatures = new Label(composite_17, SWT.NONE);
		lblMegamodelFeatures.setForeground(SWTResourceManager
				.getColor(SWT.COLOR_DARK_BLUE));
		lblMegamodelFeatures.setText("Repository features");
		lblMegamodelFeatures.setFont(SWTResourceManager.getFont("Segoe UI", 9,
				SWT.BOLD));
		lblMegamodelFeatures.setBounds(5, 0, 112, 15);

		composite_19 = new Composite(composite_R3, SWT.NONE);
		composite_19.setBounds(800, 0, 218, 78);

		lblGetA = new Label(composite_19, SWT.NONE);
		lblGetA.setText("12. Get a model from OpenDsml");
		lblGetA.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lblGetA.setBounds(9, 0, 198, 15);

		buttonGetModelFromOdsml = new Button(composite_19, SWT.NONE);
		buttonGetModelFromOdsml.setText("Get from OpenDsml !");
		buttonGetModelFromOdsml.setBounds(9, 17, 155, 25);

		textGetModelFromOdsml = new Text(composite_19, SWT.BORDER);
		textGetModelFromOdsml.setBounds(9, 48, 198, 21);

		composite_9_getlanguage = new Composite(composite_R3, SWT.NONE);
		composite_9_getlanguage.setBounds(331, 0, 218, 78);

		Label lblGetLanguage = new Label(composite_9_getlanguage, SWT.NONE);
		lblGetLanguage.setText("9. Get a language from OpenDsml");
		lblGetLanguage.setFont(SWTResourceManager.getFont("Segoe UI", 9,
				SWT.BOLD));
		lblGetLanguage.setBounds(9, 0, 198, 15);

		btnGetFromOpenDsml = new Button(composite_9_getlanguage, SWT.NONE);
		btnGetFromOpenDsml.setText("Get from OpenDsml !");
		btnGetFromOpenDsml.setBounds(9, 17, 155, 25);

		textGetFromOpenDsml = new Text(composite_9_getlanguage, SWT.BORDER);
		textGetFromOpenDsml.setBounds(9, 48, 198, 21);

		composite_11_putodsml = new Composite(composite_R3, SWT.NONE);
		composite_11_putodsml.setBounds(400, 0, 298, 78);

		lblPutA = new Label(composite_11_putodsml, SWT.NONE);
		lblPutA.setText("11. Put a model to OpenDsml");
		lblPutA.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lblPutA.setBounds(10, 0, 198, 15);

		btnPutModelToOdsml = new Button(composite_11_putodsml, SWT.NONE);

		btnPutModelToOdsml.setText("Put to OpenDsml !");
		btnPutModelToOdsml.setBounds(10, 17, 129, 25);

		textModelDesc = new Text(composite_11_putodsml, SWT.BORDER);
		textModelDesc.setBounds(10, 48, 198, 21);

		lblModelunderdesign = new Label(composite_11_putodsml, SWT.NONE);
		lblModelunderdesign.setForeground(SWTResourceManager
				.getColor(SWT.COLOR_DARK_RED));
		lblModelunderdesign.setBounds(145, 21, 114, 15);
		lblModelunderdesign.setText("no active model");

		composite_10_put_language = new Composite(composite_R3, SWT.NONE);
		composite_10_put_language.setLocation(549, 0);
		composite_10_put_language.setSize(237, 78);

		Label lblPutLanguages = new Label(composite_10_put_language, SWT.NONE);
		lblPutLanguages.setText("10. Put a language to OpenDsml");
		lblPutLanguages.setFont(SWTResourceManager.getFont("Segoe UI", 9,
				SWT.BOLD));
		lblPutLanguages.setBounds(10, 0, 198, 15);

		btnPutToOpenDsml = new Button(composite_10_put_language, SWT.NONE);
		btnPutToOpenDsml.setText("Put to OpenDsml !");
		btnPutToOpenDsml.setBounds(10, 17, 129, 25);

		textPutToOpenDsml = new Text(composite_10_put_language, SWT.BORDER);
		textPutToOpenDsml.setBounds(10, 48, 198, 21);

		composite_8_get_buildin = new Composite(composite_R3, SWT.NONE);
		composite_8_get_buildin.setLocation(121, 0);
		composite_8_get_buildin.setSize(208, 78);

		buttonGetBuildinPrototypes = new Button(composite_8_get_buildin,
				SWT.NONE);
		buttonGetBuildinPrototypes.setText("Prototypes !");
		buttonGetBuildinPrototypes.setBounds(80, 17, 95, 25);

		Label lblGetBuildin = new Label(composite_8_get_buildin, SWT.NONE);
		lblGetBuildin.setText("8. Get build-in language prototypes");
		lblGetBuildin.setFont(SWTResourceManager.getFont("Segoe UI", 9,
				SWT.BOLD));
		lblGetBuildin.setBounds(5, 0, 200, 15);

		textPrototype = new Text(composite_8_get_buildin, SWT.BORDER);
		textPrototype.setBounds(10, 47, 165, 21);

		SashForm sashFormLower = new SashForm(compositeTabOp, SWT.VERTICAL);
		sashFormLower.setBounds(0, 230, 827, 229);

		Composite toolbarLower = new Composite(sashFormLower, SWT.NONE);
		toolbarLower.setBackground(SWTResourceManager.getColor(216, 191, 216));
		toolbarLower.setLayout(null);

		panelTl = new Composite(toolbarLower, SWT.NONE);
		panelTl.setVisible(false);
		panelTl.setBounds(0, 0, 1000, 189);
		panelTl.setLayout(null);
		panelTl.setBackground(SWTResourceManager.getColor(SWT.COLOR_YELLOW));

		logTxtError = new Text(panelTl, SWT.BORDER);
		logTxtError.setForeground(SWTResourceManager
				.getColor(SWT.COLOR_DARK_RED));
		logTxtError.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		logTxtError.setBounds(0, 0, 1000, 21);

		btnDiagraphExpe = new Button(panelTl, SWT.NONE);

		btnDiagraphExpe.setBounds(0, 17, 85, 25);
		btnDiagraphExpe.setText("Diagraph expe");

		text_1 = new Text(panelTl, SWT.BORDER);
		text_1.setBounds(89, 19, 76, 21);

		lblCurrentPart = new Label(panelTl, SWT.NONE);
		lblCurrentPart.setBounds(171, 27, 245, 15);

		lblStack = new Label(panelTl, SWT.NONE);
		lblStack.setBounds(171, 48, 375, 15);

		lblPov = new Label(panelTl, SWT.NONE);
		lblPov.setBounds(10, 48, 69, 15);

		// lblPovId_ = new Label(panelTl, SWT.NONE);
		// lblPovId_.setBounds(89, 48, 55, 15);

		// lblCPov = new Label(panelTl, SWT.NONE);
		// lblCPov.setBounds(10, 69, 69, 15);

		// lblCPovId_ = new Label(panelTl, SWT.NONE);
		// lblCPovId_.setBounds(89, 69, 55, 15);

		btnGenerateAuto = new Button(panelTl, SWT.CHECK);

		btnGenerateAuto.setBounds(171, 69, 93, 16);
		btnGenerateAuto.setText("generate auto");

		btnBuildMeg = new Button(panelTl, SWT.CHECK);
		btnBuildMeg.setBounds(278, 69, 199, 16);
		btnBuildMeg.setText("build megamodel on proto");

		btnGetDiagraphActions = new Button(panelTl, SWT.NONE);

		btnGetDiagraphActions.setBounds(0, 90, 75, 25);
		btnGetDiagraphActions.setText("Get actions");

		txtDiagraphActions = new Text(panelTl, SWT.BORDER | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.CANCEL);
		txtDiagraphActions.setBounds(89, 90, 480, 87);

		btnExecActions = new Button(panelTl, SWT.NONE);
		btnExecActions.setBounds(0, 121, 75, 25);
		btnExecActions.setText("Exec actions");

		cbRefactor = new Button(panelTl, SWT.CHECK);

		cbRefactor.setBounds(10, 152, 69, 16);
		cbRefactor.setText("Refactor");

		Composite panelLog = new Composite(sashFormLower, SWT.NONE);
		panelLog.setBackground(SWTResourceManager.getColor(250, 235, 215));
		panelLog.setLayout(new FillLayout(SWT.HORIZONTAL));

		textLog = new Text(panelLog, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP
				| SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		sashFormLower.setWeights(new int[] { 190, 36 });

		/*--------------- tab 1 end ---------------------*/

		/*--------------- tab 2---------------------*/

		// sashForm_whole = new SashForm(compositeTabDev, SWT.VERTICAL);
		sashForm_whole = new SashForm(cdev, SWT.VERTICAL);

		sashForm_top = new SashForm(sashForm_whole, SWT.NONE);
		compositeDev = new Composite(sashForm_top, SWT.NONE);
		compositeDev
				.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		// compositeDev.setVisible(false);

		Composite composite_3 = new Composite(compositeDev, SWT.NONE);
		composite_3.setBounds(10, 0, 934, 91);

		Label lblRootNamespace = new Label(composite_3, SWT.NONE);
		lblRootNamespace.setText("Root Namespace:");
		lblRootNamespace.setBounds(10, 5, 93, 15);

		Label label_1 = new Label(composite_3, SWT.NONE);
		label_1.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.ITALIC));
		label_1.setText("opendsml.org");
		label_1.setBounds(107, 7, 76, 15);

		Label label_2 = new Label(composite_3, SWT.NONE);
		label_2.setText("Team Namespace:");
		label_2.setBounds(191, 7, 109, 15);

		labelTeamNamespace_ = new Label(composite_3, SWT.NONE);
		labelTeamNamespace_.setFont(SWTResourceManager.getFont("Segoe UI", 9,
				SWT.ITALIC));
		labelTeamNamespace_.setText(DEFAULT_TEAM_NAME + ".org");
		labelTeamNamespace_.setBounds(306, 7, 76, 15);

		lblLanguageName_1 = new Label(composite_3, SWT.NONE);
		lblLanguageName_1.setText("Language Name:");
		lblLanguageName_1.setBounds(388, 7, 93, 15);

		txtLanguageName = new Text(composite_3, SWT.BORDER);
		txtLanguageName.setBounds(487, 4, 107, 21);

		Label lblLanguageName = new Label(composite_3, SWT.NONE);
		lblLanguageName.setText("version:");
		lblLanguageName.setBounds(600, 7, 48, 15);

		textLangageVersion = new Text(composite_3, SWT.BORDER);
		textLangageVersion.setBounds(652, 4, 76, 21);

		Label label = new Label(composite_3, SWT.NONE);
		label.setText("Model Description:");
		label.setBounds(10, 63, 107, 15);

		txtModelDescription = new Text(composite_3, SWT.BORDER);
		txtModelDescription.setBounds(117, 60, 265, 21);

		btnUploadModelAndLanguage = new Button(composite_3, SWT.NONE);

		btnUploadModelAndLanguage.setText("Upload Model And Language");
		btnUploadModelAndLanguage.setBounds(207, 32, 172, 25);

		btnDownloadLanguageAndModels = new Button(composite_3, SWT.NONE);

		btnDownloadLanguageAndModels
				.setText("Download Language and Model Exemples");
		btnDownloadLanguageAndModels.setBounds(499, 32, 233, 25);

		lblUserName = new Label(composite_3, SWT.NONE);
		lblUserName.setBounds(747, 0, 253, 15);
		lblUserName.setText("User Name:");

		lblTeamName = new Label(composite_3, SWT.NONE);
		lblTeamName.setBounds(747, 31, 253, 15);
		lblTeamName.setText("Team Name:");

		lblPassword = new Label(composite_3, SWT.NONE);
		lblPassword.setBounds(747, 15, 253, 15);
		lblPassword.setText("Password:");

		lblConnectState = new Label(composite_3, SWT.NONE);
		lblConnectState.setBounds(749, 63, 253, 15);
		lblConnectState.setText("Connect State:");

		lblUserEmail = new Label(composite_3, SWT.NONE);
		lblUserEmail.setBounds(626, 63, 118, 15);
		lblUserEmail.setText("user email:");

		txtLanguageNsUri = new Text(composite_3, SWT.BORDER);
		txtLanguageNsUri.setBounds(386, 34, 107, 21);

		lblModelName = new Label(composite_3, SWT.NONE);
		lblModelName.setBounds(10, 37, 76, 15);
		lblModelName.setText("Model Name:");

		txtModelName = new Text(composite_3, SWT.BORDER);
		txtModelName.setBounds(92, 34, 109, 21);

		txtLanguageNsPrefix = new Text(composite_3, SWT.BORDER);
		txtLanguageNsPrefix.setBounds(417, 60, 143, 21);

		compositeDeployers = new Composite(compositeDev, SWT.NONE);
		compositeDeployers.setBounds(10, 210, 533, 51);

		txtPutToLwb = new Text(compositeDeployers, SWT.BORDER);
		txtPutToLwb.setBounds(10, 2, 171, 21);

		btnDeployLocalLanguages = new Button(compositeDeployers, SWT.NONE);
		btnDeployLocalLanguages.setText("Deploy Local Languages");
		btnDeployLocalLanguages.setBounds(202, 0, 148, 25);

		btnDeployAllLanguages = new Button(compositeDeployers, SWT.NONE);

		btnDeployAllLanguages.setText("Deploy All Languages");
		btnDeployAllLanguages.setBounds(337, 0, 165, 25);

		btnDeployLocalModels = new Button(compositeDeployers, SWT.NONE);

		btnDeployLocalModels.setText("Deploy From LWB");
		btnDeployLocalModels.setBounds(163, 26, 122, 25);

		btnPutTo = new Button(compositeDeployers, SWT.NONE);

		btnPutTo.setBounds(10, 26, 137, 25);
		btnPutTo.setText("Consolidate To LWB");

		btnConsolidateRepositoryWprojects = new Button(compositeDeployers,
				SWT.NONE);

		btnConsolidateRepositoryWprojects.setBounds(290, 26, 212, 25);
		btnConsolidateRepositoryWprojects
				.setText("Consolidate Repository w/Projects");

		matcherComboLeft = new Combo(compositeDev, SWT.NONE);

		matcherComboLeft.setBounds(100, 326, 177, 23);

		btnSimilarityAction = new Button(compositeDev, SWT.NONE);

		btnSimilarityAction.setBounds(10, 324, 84, 25);
		btnSimilarityAction.setText("Similarity");

		matcherComboRight = new Combo(compositeDev, SWT.NONE);
		matcherComboRight.setBounds(283, 326, 177, 23);

		btnDotify = new Button(compositeDev, SWT.NONE);

		btnDotify.setBounds(906, 326, 114, 25);
		btnDotify.setText("Dotify");

		btnRefreshModelCombos = new Button(compositeDev, SWT.NONE);

		btnRefreshModelCombos.setBounds(617, 392, 246, 25);
		btnRefreshModelCombos.setText("Refresh Model Combos");

		dotifyTypeCombo = new Combo(compositeDev, SWT.NONE);

		dotifyTypeCombo.setBounds(723, 328, 177, 23);

		btnRetrieveM2 = new Button(compositeDev, SWT.NONE);

		btnRetrieveM2.setBounds(10, 352, 84, 25);
		btnRetrieveM2.setText("Find M2");

		loggerLabel1 = new Label(compositeDev, SWT.NONE);
		loggerLabel1.setBackground(SWTResourceManager.getColor(255, 255, 224));
		loggerLabel1.setBounds(283, 376, 584, 15);

		loggerLabel2 = new Label(compositeDev, SWT.NONE);
		loggerLabel2.setBounds(293, 355, 584, 15);

		modeLabel = new Label(compositeDev, SWT.NONE);
		modeLabel.setBounds(10, 383, 36, 15);

		btnLocalQuery = new Button(compositeDev, SWT.NONE);

		btnLocalQuery.setBounds(607, 225, 75, 25);
		btnLocalQuery.setText("Local Query");

		txtQuery = new Text(compositeDev, SWT.BORDER);
		txtQuery.setText("deploy case4");
		txtQuery.setBounds(702, 227, 174, 21);

		Composite composite_1 = new Composite(compositeDev, SWT.NONE);
		composite_1.setBounds(10, 264, 473, 25);

		textSimilaritySearch = new Text(composite_1, SWT.BORDER);
		textSimilaritySearch.setBounds(258, 2, 184, 21);

		btnSimilaritySearch = new Button(composite_1, SWT.NONE);

		btnSimilaritySearch.setBounds(177, 0, 75, 25);
		btnSimilaritySearch.setText("Search");

		btnSimilarityRemove = new Button(composite_1, SWT.NONE);

		btnSimilarityRemove.setBounds(96, 0, 75, 25);
		btnSimilarityRemove.setText("Remove");

		Label lblSimilarity = new Label(composite_1, SWT.NONE);
		lblSimilarity.setBounds(10, 5, 55, 15);
		lblSimilarity.setText("Similarity");

		m2PackageLabel = new Label(compositeDev, SWT.NONE);
		m2PackageLabel.setBounds(101, 357, 188, 15);

		btnStopAutomationServer = new Button(compositeDev, SWT.NONE);

		btnStopAutomationServer.setBounds(607, 287, 148, 25);
		btnStopAutomationServer.setText("Stop Automation Server");

		btnStartAutomationServer = new Button(compositeDev, SWT.NONE);
		btnStartAutomationServer.setSelection(true);
		btnStartAutomationServer.setGrayed(true);

		btnStartAutomationServer.setBounds(607, 256, 148, 25);
		btnStartAutomationServer.setText("Start Automation Server");

		btnTransform = new Button(compositeDev, SWT.NONE);

		btnTransform.setBounds(468, 291, 75, 25);
		btnTransform.setText("Transform");

		btnStartTomcat = new Button(compositeDev, SWT.NONE);

		btnStartTomcat.setBounds(10, 291, 84, 25);
		btnStartTomcat.setText("start Tomcat");

		btnStopTomcat = new Button(compositeDev, SWT.NONE);

		btnStopTomcat.setBounds(100, 291, 75, 25);
		btnStopTomcat.setText("stop Tomcat");

		btnLaunchAnt = new Button(compositeDev, SWT.NONE);

		btnLaunchAnt.setBounds(607, 324, 75, 25);
		btnLaunchAnt.setText("launch Ant");

		checkBoxRcp = new Button(compositeDev, SWT.CHECK);
		checkBoxRcp.setSelection(false);
		checkBoxRcp.setBounds(284, 295, 93, 16);
		checkBoxRcp.setText("Rcp");

		compositeVisualGrammarGeneration = new Composite(compositeDev, SWT.NONE);
		compositeVisualGrammarGeneration.setBounds(10, 97, 279, 86);

		btnGenerate = new Button(compositeVisualGrammarGeneration, SWT.NONE);

		btnGenerate.setText("Generate");
		btnGenerate.setBounds(10, 26, 67, 25);

		pointOfViewComboDev = new Combo(compositeVisualGrammarGeneration,
				SWT.NONE);

		pointOfViewComboDev.setBounds(130, 59, 134, 23);

		Label povlbl = new Label(compositeVisualGrammarGeneration, SWT.NONE);
		povlbl.setText("Pov Name:");
		povlbl.setBounds(130, 33, 55, 15);

		pointOfViewNameText = new Text(compositeVisualGrammarGeneration,
				SWT.BORDER);
		pointOfViewNameText.setBounds(191, 30, 76, 21);

		btnNewPointOfView = new Button(compositeVisualGrammarGeneration,
				SWT.NONE);

		btnNewPointOfView.setText("New Point Of View");
		btnNewPointOfView.setBounds(10, 57, 114, 25);

		Label lblVisualGrammarGeneration = new Label(
				compositeVisualGrammarGeneration, SWT.NONE);
		lblVisualGrammarGeneration.setBounds(10, 5, 164, 15);
		lblVisualGrammarGeneration.setText("Visual Grammar Generation");

		compositeLanguageDesign = new Composite(compositeDev, SWT.NONE);
		compositeLanguageDesign.setBounds(295, 97, 273, 86);

		viewCombo_nu_ = new Combo(compositeLanguageDesign, SWT.NONE);
		viewCombo_nu_.setBounds(10, 53, 177, 23);

		btnLayout = new Button(compositeLanguageDesign, SWT.NONE);

		btnLayout.setText("Layout");
		btnLayout.setBounds(193, 51, 66, 25);

		Label lblLanguageDesign = new Label(compositeLanguageDesign, SWT.NONE);
		lblLanguageDesign.setBounds(10, 5, 108, 15);
		lblLanguageDesign.setText("Language Design");

		compositeLanguageImplementation = new Composite(compositeDev, SWT.NONE);
		compositeLanguageImplementation.setBounds(574, 97, 343, 86);

		Label lblLanguageUsage = new Label(compositeLanguageImplementation,
				SWT.NONE);
		lblLanguageUsage.setBounds(12, 5, 140, 15);
		lblLanguageUsage.setText("Language Implementation");

		buttonOnlyCurrentView = new Button(compositeLanguageImplementation,
				SWT.CHECK);
		buttonOnlyCurrentView.setText("only the current view");
		buttonOnlyCurrentView.setBounds(85, 29, 139, 16);

		btnGenerateLanguage = new Button(compositeLanguageImplementation,
				SWT.NONE);

		btnGenerateLanguage.setText("Generate");
		btnGenerateLanguage.setBounds(12, 25, 67, 25);

		btnUseLanguage = new Button(compositeLanguageImplementation, SWT.NONE);

		btnUseLanguage.setText("Edit Models");
		btnUseLanguage.setBounds(12, 51, 156, 25);

		textWorkbenchName = new Text(compositeLanguageImplementation,
				SWT.BORDER);
		textWorkbenchName.setText(PROMPT_MODEL_WORKBENCH_NAME_);
		textWorkbenchName.setBounds(189, 53, 144, 21);

		textStatus = new Text(compositeDev, SWT.BORDER);
		textStatus.setBounds(790, 97, 266, 21);

		lblChange = new Label(compositeDev, SWT.NONE);
		lblChange.setBounds(52, 383, 84, 15);

		compositeModelDeployer = new Composite(compositeDev, SWT.NONE);
		compositeModelDeployer.setBounds(774, 260, 246, 64);

		txtLanguageDeploy2 = new Text(compositeModelDeployer, SWT.BORDER);
		txtLanguageDeploy2.setText("all (or specify a name)");
		txtLanguageDeploy2.setBounds(10, 29, 152, 21);

		btnDeployFromLwb = new Button(compositeModelDeployer, SWT.NONE);

		btnDeployFromLwb.setBounds(168, 27, 68, 25);
		btnDeployFromLwb.setText("Deploy");

		Label lblDeployFromLanguage = new Label(compositeModelDeployer,
				SWT.NONE);
		lblDeployFromLanguage.setBounds(10, 10, 211, 15);
		lblDeployFromLanguage.setText("Deploy from Language Workbench");

		btnLwbQuery = new Button(compositeDev, SWT.NONE);
		btnLwbQuery.setBounds(607, 189, 75, 25);
		btnLwbQuery.setText("LWB Query");

		txtLwbQuery = new Text(compositeDev, SWT.BORDER);
		txtLwbQuery.setText("refresh lwb");
		txtLwbQuery.setBounds(702, 193, 175, 21);

		btnReplicateModelRepository = new Button(compositeDev, SWT.NONE);

		btnReplicateModelRepository.setBounds(413, 185, 225, 19);
		btnReplicateModelRepository.setText("Replicate Model Repository");

		btnClone = new Button(compositeDev, SWT.NONE);

		btnClone.setBounds(151, 181, 120, 25);
		btnClone.setText("Clone Language");

		txtCloneDev = new Text(compositeDev, SWT.BORDER);
		txtCloneDev.setText("cloningName");
		txtCloneDev.setBounds(10, 183, 135, 21);

		btnTransfXslt = new Button(compositeDev, SWT.NONE);

		btnTransfXslt.setBounds(283, 179, 75, 25);
		btnTransfXslt.setText("Transf. xslt");
		sashForm_top.setWeights(new int[] { 5 });

		sashForm_bottom = new SashForm(sashForm_whole, SWT.NONE);

		sashForm_left = new SashForm(sashForm_bottom, SWT.VERTICAL);
		alignmentTable = new AlignmentTable(sashForm_left, SWT.NONE);

		sashForm_left.setWeights(new int[] { 1 });

		sashForm_right = new SashForm(sashForm_bottom, SWT.VERTICAL);

		text1 = new Text(sashForm_right, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		text1.setBackground(SWTResourceManager.getColor(255, 255, 255));

		text2 = new Text(sashForm_right, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		sashForm_right.setWeights(new int[] { 59, 37 });
		sashForm_bottom.setWeights(new int[] { 480, 289 });

		sashFormFooter = new SashForm(sashForm_whole, SWT.NONE);

		sashForm = new SashForm(sashFormFooter, SWT.NONE);

		Composite composite_2 = new Composite(sashForm, SWT.NONE);

		logTxt = new Text(sashForm, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		sashForm.setWeights(new int[] { 367, 402 });
		sashFormFooter.setWeights(new int[] { 1 });
		sashForm_whole.setWeights(new int[] { 421, 0, 63 });

		/*--------------- tab 2 end ---------------------*/

		if (!isDevMode_()) { // FP150305

			try {
				initView();
			} catch (Exception e) {
				throw new RuntimeException(
						"at least one diagraph component is not available.. mailto francois.pfister@mines-ales.fr");
			}
		} else
			doclog("operations view not initialized, starting in dev mod");

	}

	private boolean isDevMode_() { // FP141227
		return "dev".equals(System.getProperty("start.mode"));
	}

	void redrawGui_new() {
		/*
		 * mainTab = new TabFolder(parentComposite, SWT.NONE);
		 * mainTab.setLayoutData(BorderLayout.CENTER);
		 *
		 * tabItem_2 = new TabItem(mainTab, SWT.NONE);
		 * tabItem_2.setText("New Item2");
		 *
		 * tabItem_1 = new TabItem(mainTab, SWT.NONE);
		 * tabItem_1.setText("New Item1");
		 *
		 *
		 *
		 * compositeTab1 = new Composite(mainTab, SWT.NONE); compositeTab2 = new
		 * Composite(mainTab, SWT.NONE);
		 *
		 *
		 * tabItem_1.setControl(compositeTab2);
		 * tabItem_2.setControl(compositeTab1);
		 *
		 * sashForm_whole.setParent(compositeTab1);
		 */

	}

	protected void redrawGui() {

		if (LOG)
			clog("redrawgui() " + (isLanguageConfiguration() ? "LWB" : "MWB"));

		if (EXPE)
			panelTl.setVisible(true);

		txtCloneDev.setText("");

		String prompt = null;
		String fixed = FIXED_MODEL_WORKBENCH_NAME_;
		if (fixed==null)
		    prompt = PROMPT_MODEL_WORKBENCH_NAME_
				+ "-"
				+ new SimpleDateFormat("yyyy-MM-dd").format(Calendar
						.getInstance().getTime());
		else
			 prompt = PROMPT_MODEL_WORKBENCH_NAME_
				+ "-"
				+ fixed;


		textWorkbenchName.setText(prompt);
		textLaunchMDWB.setText(prompt);
		// my-model-workspace_Wed May 22 10:05:42 CEST 2013

		if (languageConfiguration) {

		} else {
			btnDeployLocalLanguages.setVisible(false);
			btnDeployAllLanguages.setVisible(false);
			// btneployAllModels.setVisible(false);
		}

		if (!redraw)
			return;

		txtLanguageDeploy2.setText(PROMPT_LANG_TO_DEPLOY);
		txtLwbQuery.setText(CMD_REFRESH_LWB);
		if (languageConfiguration) {
			compositeModelDeployer.setVisible(false);
		} else {
			if (LOG)
				clog("redrawgui MWB");
			compositeVisualGrammarGeneration.setVisible(false);
			compositeLanguageDesign.setVisible(false);
			compositeLanguageImplementation.setVisible(false);
			compositeDeployers.setVisible(false);
			compositeModelDeployer.setVisible(true);
			Rectangle compositeDeployersBounds = compositeModelDeployer
					.getBounds();
			compositeDeployersBounds.y = 100;
			compositeDeployersBounds.x = 20;
			compositeModelDeployer.setBounds(compositeDeployersBounds);
		}

	}

	private void connectGuiListeners(final ViewPart dview) {
		if (LOG)
			clog("connectGuiListeners");

		btnExecute.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				executeCommandLine();
			}

		});

		btnExecActions.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				executeDiagraphActions();
			}
		});

		btnGetDiagraphActions.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				getDiagraphActions();
			}
		});

		btnPutModelToOdsml.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				uploadModelSoonComing();
			}
		});

		buttonGetModelFromOdsml.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				downloadModelSoonComing();
			}
		});
		btnAutomatedGramgen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				generateGrammarAndSaveLayouts();
			}
		});

		btnLaunchMWB.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				useLanguage();
			}
		});

		btnLayoutAnots.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				layout(true, true);
			}
		});

		btnDesign.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				doIdeJob();/*
							 * checkPinToMerge.setSelection(false);
							 * if(merging)if (merge_LOG) clogmerge("merge end");
							 * merging = false;
							 */
			}
		});

		btnConsolidateRepositoryWprojects.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				consolidateRepositoryWithProjects();
			}
		});

		btnTransfXslt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				launchXsltTransfo();
			}
		});

		btnMerge.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				mergeLanguages();
			}
		});

		btnClone.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				// cloneCount = 0;
				cloneLanguage();
			}
		});

		btnCloneLanguage2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				// cloneCount = 0;
				cloneLanguage();
			}
		});

		btnReplicateModelRepository.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				// automationService.consolidateModelRepository();
				automationService.consolidate();
			}
		});

		btnLwbQuery.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				saveAllEditors();
				automationService.sendLWBQuery(txtLwbQuery.getText());
			}
		});

		btnLocalQuery.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				automationService.sendLocalQuery(txtQuery.getText());
			}
		});

		btnPutTo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				consolidate(PathPreferences.getPreferences()
						.getUniverseProjectName(), MEGAMODEL_FOLDER_LOCAL,
						txtPutToLwb.getText());

			}
		});

		btnLayout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				layout(true, true);
			}
		});

		btnLayoutLayer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				layout(true, true);
			}
		});

		btnDeployLocalLanguages.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				deployLocalLanguages_();

			}
		});

		btnDeployAllLanguages.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				deployAllLanguages_();
			}
		});

		buttonGetBuildinPrototypes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				getBuildInPrototypes_();
			}
		});

		btnDeployLocalModels.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {

				if (!DParams.MEGAMODEL_DEPLOY){
					cinfo("no deployment see DParams.DParams.MEGAMODEL_DEPLOY");
					return;
				}
				deployModels_(PathPreferences.getPreferences()
						.getUniverseProjectName(), MEGAMODEL_FOLDER_LOCAL,
						txtPutToLwb.getText(), false);
			}
		});

		btnDeployFromLwb.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if (!DParams.MEGAMODEL_DEPLOY){
					cinfo("no deployment see .DParams.MEGAMODEL_DEPLOY");
					return;
				}
				deployModels_(PathPreferences.getPreferences()
						.getUniverseProjectName(), MEGAMODEL_FOLDER_LOCAL,
						txtLanguageDeploy2.getText(), false);
			}
		});
		/*
		 * btnDeployAllModels.addMouseListener(new MouseAdapter() {
		 *
		 * @Override public void mouseUp(MouseEvent e) {
		 *
		 * } });
		 */
		btnUploadModelAndLanguage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				uploadModelAndLanguage();
			}
		});

		btnPutToOpenDsml.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				uploadModelAndLanguageSoonComing();
			}
		});

		btnDownloadLanguageAndModels.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				downloadModelsAndLanguage_();
			}
		});

		btnGetFromOpenDsml.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				downloadModelsAndLanguageSoonComing();
			}
		});

		btnSimilarityAction.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				try {
					runSimilarity();
				} catch (Exception ex) {
					showMessage("not in a modeler configuration (4)!!!");
				}
			}
		});
		btnDotify.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				runDotifyAction();
			}
		});

		btnDotify_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				runDotifyAction();
			}
		});

		btnRefreshModelCombos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if (!refreshModelCombos(false))
					showMessage("not in a modeler configuration (5) !!!");
			}
		});

		btnRetrieveM2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				retrieveM2(matcherComboLeft.getText());
			}
		});
		btnStopAutomationServer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				automationService.sendLocalQuery(txtQuery.getText());
			}
		});

		btnTransform.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				doTransform();
			}
		});

		btnStartTomcat.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				// startTomcat();
			}
		});

		btnStopTomcat.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				// stopTomcat();
			}
		});

		btnLaunchAnt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				// runAnt();
			}
		});

		btnGenerate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				forceActive();
				generateGrammarAndSaveLayouts();
			}
		});

		btnNewPointOfView.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				forceActive();
				generatePointOfView(null, null, true);
			}
		});

		btnNewPov.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				forceActive();
				generatePointOfView(null, null, true);
			}
		});

		btnGenerateLanguage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				forceActive();
				generateLanguage(false, false, this, null);
			}
		});

		btnGenerateModeler.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				forceActive();
				generateLanguage(false, false, this, null);
			}
		});

		btnUseLanguage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				forceActive();
				useLanguage();
			}
		});

		btnDiagraphExpe.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				// runtimeAction_nu("");
			}
		});

		matcherComboLeft.addSelectionListener(new SelectionAdapter() {
			String priorText;

			@Override
			public void widgetSelected(SelectionEvent e) {
				String txt = matcherComboLeft.getText();
				if (txt != null && !txt.isEmpty() && !txt.equals(priorText)) {
					boolean doit = priorText != null;
					priorText = txt;
					if (doit) {
						lastLeftChoice_ = txt;
						if (LOG)
							clog("combo changed " + txt);// ;//
					}
				}
			}
		});

		matcherComboRight.addSelectionListener(new SelectionAdapter() {
			String priorText;

			@Override
			public void widgetSelected(SelectionEvent e) {
				String txt = matcherComboRight.getText();
				if (txt != null && !txt.isEmpty() && !txt.equals(priorText)) {
					boolean doit = priorText != null;
					priorText = txt;
					if (doit) {
						lastRightChoice_ = txt;
						if (LOG)
							clog("right combo changed " + txt);// ;//

					}

				}
			}
		});
		configureViewCombos();

		pointOfViewComboDev.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String txt = pointOfViewComboDev.getText();
				if (txt != null && !txt.isEmpty()) {
					String proposedViewName = VIEW_PREFIX + txt.toLowerCase();
					pointOfViewNameText.setText(proposedViewName);
					textNewPov.setText(proposedViewName);
				}
			}
		});

		comboPov_.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectPov();
			}
		});

		comboPov_.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				generateSaveAllAndGeneratePovCandidates(); // FP140117
			}
		});

		getShell().getDisplay().addFilter(SWT.KeyDown, this); // overload
																// keyboard
																// strokes
	}

	// FP141217 thèse il y a un an déjà
	protected void forceActive() {
		if (!active || !listening_) {
			restoreListeners(true); //FP150528
			active = true;
		}
	}

	@Override
	public void generateAllDiagraphArtifacts(List<String> projsToCompile,
			Megamodel megamodel, boolean refreshArtifactsOnly,
			IProgressMonitor progressMonitor) {

		// getDiagraphGenerator();

		String log = "";
		List<String> keywordFilters = new ArrayList<String>();
		if (LOG)
			clog("generateAllDiagraphArtifacts");
		diagraphGenerator.logBeforeExec(null, keywordFilters);
		for (String proj : projsToCompile) {
			String domain = getDomainName(proj);
			if (domain != null) {
				progressMonitor.subTask("transforming " + domain + ".ecore");
				progressMonitor.worked(1);
				String ecoreLocation = proj + "/" + MODEL_FOLDER + "/" + domain
						+ ".ecore";
				if (LOG)
					clog("GADA start " + ecoreLocation);
				String logs = "generation disabled";

				if (btnGenerateAuto.getSelection()) {
					logs = execAllViews(ecoreLocation, refreshArtifactsOnly,
							progressMonitor);
					if (LOG)
						clog("GADA done " + ecoreLocation + " (" + logs + ")");
				} else if (LOG)
					clog("gmf generation is disabled");

				// EPackage model = null;
				URI ecoreuri = URI.createPlatformResourceURI(proj + "/"
						+ "model" + File.separator + domain + "." + "ecore",
						true);
				// CommonPlugin.resolve(ecoreuri).toFileString();
				ResourceSet metamodelSet = new ResourceSetImpl();
				metamodelSet
						.getResourceFactoryRegistry()
						.getExtensionToFactoryMap()
						.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
								new EcoreResourceFactoryImpl());
				URI metamodelURI = URI.createFileURI(new File(CommonPlugin
						.resolve(ecoreuri).toFileString()).getAbsolutePath());
				EPackage model = (EPackage) metamodelSet
						.getResource(metamodelURI, true).getContents().get(0);

				// String
				// ff=CommonPlugin.resolve(URI.createPlatformResourceURI(proj,
				// true)).toFileString()+File.separator+"model"+File.separator+domain+"."+"ecore";

				addDsml_(proj, model);

				textLog.setText(logs);
				String logToLog = logs;
				logToLog += "\n1-getLog-----------------------------------------\n";
				logToLog += diagraphGenerator.getLog();
				logToLog += "\n2-getGenLog--------------------------------------\n";
				logToLog += diagraphGenerator.getGenLog();
				logToLog += "\n3-getLogDiagraph---------------------------------\n";
				logToLog += diagraphGenerator.getLogDiagraph();
				saveDeployLog(proj, domain,log);
				log += logs;
			}
		}

		String targetProject = PathPreferences.getPreferences()
				.getUniverseProjectName();
		String target = targetProject + "/"
				+ PathPreferences.getPreferences().getUniverseFolder();
		saveTrace(target,log);
		/*
		writeFile_(
				CommonPlugin.resolve(
						URI.createPlatformResourceURI(target, true))
						.toFileString()
						+ File.separator + "deployment" + "." + "trace3", log);*/
		//refreshLanguageRepositoryProject(); // FP130518
		diagraphGenerator.logAfterExec(log, keywordFilters);
	}



	private void saveTrace(String target, String log) {
		writeFile_(
				CommonPlugin.resolve(
						URI.createPlatformResourceURI(target, true))
						.toFileString()
						+ File.separator + "deployment" + "." + "trace3", log);
		refreshLanguageRepositoryProject(); // FP130518
	}

	/*
	 * private boolean diagramParsing; private boolean jobbing; private String
	 * diagramCsv;
	 */

	@Override
	public void csvTrace(String csv) {
		megamodelBuilder.csvTrace(csv);
	}

	/*
	 *
	 * @Override public void saveCsvTrace() { System.out.println(diagramCsv); }
	 */

	/*-----------------------------------------*/

	/*
	 *
	 * public void doIt(String text) { jobbing = true; diagramCsv = ""; if
	 * (text.equals("parse")) { clearForm(); saveAllEditors(); parseLanguage();
	 * } else if (text.startsWith("find")) { String langs =
	 * text.substring("find".length()).trim(); exportImagesJob(langs); } else if
	 * (text.startsWith("diagraph")) { String langs =
	 * text.substring("diagraph".length()).trim(); diagraphLanguages(langs); }
	 * else if (text.startsWith("ex")) { String langs =
	 * text.substring("ex".length()).trim(); exportExamplesJob(); } else if
	 * (text.startsWith("all")) { String langs =
	 * text.substring("all".length()).trim(); diagraphLanguagesJob(langs);
	 * exportImagesJob(langs); exportExamplesJob(); } saveUniverse();
	 * saveCsvTrace(); jobbing = false; }
	 */
	/*
	 * protected void doIt_old(String text) { jobbing=true; diagramCsv=""; if
	 * (textDesign.getText().equals("parse")){ clearForm(); saveAllEditors();
	 * parseLanguage(); } else if (textDesign.getText().startsWith("find")){
	 * String langs = textDesign.getText().substring("find".length()).trim();
	 * //FP130814y exportImagesJob(langs);
	 *
	 * } else if (textDesign.getText().startsWith("diagraph")){ String langs =
	 * textDesign.getText().substring("diagraph".length()).trim();
	 * diagraphLanguages(langs);
	 *
	 * } else if (textDesign.getText().startsWith("ex")){ String langs =
	 * textDesign.getText().substring("ex".length()).trim(); //modelingUniverse_
	 * = createUniverse("_megamodel"); //
	 * org.isoe.fwk.utils.debug.WorkspaceUtils
	 * .getInstance().disableRefresh(true); //exportImagesJob(langs);
	 * //diagraphLanguagesJob(langs); exportExamplesJob();
	 *
	 * //
	 * org.isoe.fwk.utils.debug.WorkspaceUtils.getInstance().disableRefresh(false
	 * ); //FP130816
	 *
	 * } else if (textDesign.getText().startsWith("all")){ String langs =
	 * textDesign.getText().substring("all".length()).trim();
	 * //modelingUniverse= createUniverse("_megamodel"); // org.isoe.fwk.utils.
	 * debug.WorkspaceUtils.getInstance().disableRefresh(true);
	 * diagraphLanguagesJob(langs); exportImagesJob(langs);
	 *
	 * exportExamplesJob();
	 *
	 *
	 *
	 * //
	 * org.isoe.fwk.utils.debug.WorkspaceUtils.getInstance().disableRefresh(false
	 * ); //FP130816
	 *
	 * } saveCsvTrace(); jobbing=false; saveUniverse(); }
	 */

	private String[] parseStatement(String statement) { // FP140605
		String[] args = new String[2];
		// String statement_ = owner.getDiagraphStatements().trim();
		if (statement.contains(" ")) {
			args = statement.split(("\\s+"));
		} else if (statement.contains("(")) {
			args = statement.split("\\(");
			args[1] = args[1].substring(0, args[1].length() - 1);
		} else {
			args[0] = statement;
			args[1] = "";
		}
		return args;
	}

	private String getOpenedProjectName(String packageName) {// FP140705

		IProject oprj = ResourceUtils.getOpenedProject(packageName);
		return oprj == null ? null : oprj.getName();
		/*
		 * List<String> prjs = ResourceUtils.getOpenedProjects(); for (String
		 * prj : prjs) { if (!prj.equals("_megamodel")) { try { String
		 * lastsegment = prj .substring(prj.lastIndexOf(".") + 1); if
		 * (lastsegment.equals(packageName)) return prj; } catch (Exception e) {
		 * cerror("error while getCurrentProject " + prj); }
		 *
		 * } } return null;
		 */
	}

	private void clogTest(String mesg) {
		if (SandboxView_TEST_LOG)
			System.out.println(mesg);
	}

	protected void uploadModelSoonComing() {
		textModelDesc.setText("Soon coming");
		timedClear(1000, textModelDesc);
	}

	protected void downloadModelSoonComing() {
		textGetModelFromOdsml.setText("Soon coming");
		timedClear(1000, textGetModelFromOdsml);
	}

	protected void deployAllLanguages_() {
		boolean deployBuildInPlugin = true;
		boolean deployLocalPlugin = true;
		boolean deployLocalWorkspace = true;
		deployMegamodel_(deployBuildInPlugin, deployLocalPlugin,
				deployLocalWorkspace, true, "");// txtPutToLwb.getText());
		if (LOG) {
			clog("deployment result:"); // FP140126td
			for (IProject deplp : deployedProjects)
				clog("prj=" + deplp.getName());

			for (String depll : deployedLanguages_)
				clog("prj=" + depll);
		}
	}

	private ILanguageHandler getParserService() {
		if (languageParser == null) {
			languageParser = new LanguageHandlerConnector().getParseService();
			if (languageParser.isStub()) {
				if (LOG)
					clog("No Parse Service is available, using a non functional mock  !");

			} else if (LOG)
				clog("Using Isoe Parserer for parsing languages");
		}
		languageParser.setControler(this);
		return languageParser;
	}

	/*
	 * private ILanguageHandler getClonerService() { if (languageCloner == null)
	 * { languageCloner = new LanguageHandlerConnector().getCloneService(); if
	 * (languageCloner.isStub()) { if (LOG)
	 * clog("No Clone Service is available, using a non functional mock  !");
	 *
	 * } else if (LOG) clog("Using Isoe Cloner for cloning languages"); }
	 * languageCloner.setControler(this); return languageCloner; }
	 */

	@Override
	public List<DGraph> newParse(Diagram diagram) {
		EPackage epak = (EPackage) diagram.getElement();
		EClass aclass = null;
		EList<EClassifier> classifs = epak.getEClassifiers();
		for (EClassifier eClassifier : classifs) {
			if (eClassifier instanceof EClass) { // FP140423
				aclass = (EClass) eClassifier;
				break;
			}
		}

		// EClass eclaz_ = (EClass) epak.getEClassifiers().get(0);
		boolean headless = true;
		String rp = languageParser.newParse_nu(aclass, headless); // FP131207
		List<DGraph> result = null;

		if (!(rp == null || rp.isEmpty())) {
			result = languageParser.getConcreteSyntax();
			for (DGraph dGraph : result) {
				String viewname = dGraph.getViewName();
				if (LOG)
					clog(viewname);
			}
		}

		if (result != null && !result.isEmpty()) {
			String[] viuz = new String[result.size()];
			for (int i = 0; i < result.size(); i++)
				// DGraph dGraph : result) {
				viuz[i] = result.get(i).getViewName();

			setViews(this, 55, langageName, viuz,
					getCurrentView(28, langageName, "______SB_newparse")); // FP140611
																			// //
																			// FP140216
			diagraphAdapter.parsed(epak);
		}

		return result;
	}

	@Override
	public DGraph selectDiagraphByView(EPackage pak, String view,
			Diagram diagram, boolean save, boolean atRuntime, boolean rcp,
			boolean validate, boolean headless,
			NullProgressMonitor progressMonitor) {

		if (CERROR)
			cerror("(2)<selectDiagraphByView "
					+ (pak == null ? "" : pak.getName()));
		DGraph result = diagraphGenerator.selectDiagraphByView(pak, view,
				diagram, save, atRuntime, rcp, validate, headless,
				progressMonitor);
		if (CERROR)
			cerror("(2)>selectDiagraphByView "
					+ (result == null ? "" : result.getFacade1()
							.getLanguageName()));
		return result;
	}

	private void executeLayoutAndSave(boolean redrop) {
		// EPackage curnt = getCurrentPackage();

		if (diagraphGenerator.findActiveEcoreEditor("") != null) {

			/*
			 * String oldcviu = getCurrentView(31,getCurrentPackage().getName(),
			 * "______SB_executeLayoutAndSave"); //FP140611 if(LOG)
			 * clog("executeLayoutAndSave -p="
			 * +getCurrentPackage().getName()+" -v="
			 * +cviu+" -redrop="+(redrop?"true":"false")); if
			 * (CheckAnnotationCommand_LOG)
			 * clog2("executeLayoutAndSave -p="+getCurrentPackage
			 * ().getName()+" -v="+cviu+" -redrop="+(redrop?"true":"false"));
			 */

			// FP140611b
			if (redrop) {
				// diagraphRuntimeFactory.removeDiagraphAnnotationsFromDiagram(curnt,
				// view);
				// diagraphRuntimeFactory.dropDiagraphAnnotations();
				layoutDone = true;
				diagraphRuntimeFactory_
						.redropDiagraphAnnotations(getCurrentPackage());
			}
			runlayout();
			timedSave(500);
			//saveAllEditors();
		}
	}



	private void checkDiagraphAnnotationsAndSave() {
		if (CheckAnnotationCommand_LOG)
			clog2("checkDiagraphAnnotationsAndSave");

		if (diagraphGenerator.findActiveEcoreEditor("") != null)
			if (diagraphRuntimeFactory_.checkConcreteSyntax())
				saveAllEditors();
	}

	private void timedLayout(final ViewPart dview, final int delay) { // FP140421
		timedInhibit(dview, LAYOUT_INHIBIT_DELAY);
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				try {
					sleep(delay, "timedLayout");
					if (CheckAnnotationCommand_LOG)
						clog2("timedLayout");
					checkDiagraphAnnotationsAndSave();
					executeLayoutAndSave(true);
				} catch (Exception e) {

				}
			}

		});

	}

	private void timedInhibit(final ViewPart dview, final int delay) { // FP140423
																		// hack
																		// of
																		// the
																		// day
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				try {
					inhibit = true;
					sleep(delay, "timedInhibit");
					inhibit = false;
				} catch (Exception e) {
				}
			}
		});

	}

	private void timedRestoreListeners_nu(final int delay) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				try {
					sleep(delay, "timedRestoreListeners");
					restoreListeners(false);
				} catch (Exception e) {

				}
			}
		});

	}

	/*
	 * private void stoplayout(final ViewPart dview, final int delay){ //
	 * FP140421 Display.getDefault().asyncExec(new Runnable() { public void
	 * run() { try { stoplayout=true; sleep(delay); stoplayout=false;
	 *
	 * } catch (Exception e) { } } });
	 *
	 * }
	 */

	protected void launchXsltTransfo() {
		xslTransformationService.setEPackage(currentPackage);
		if (modelConfiguration) {
			xslTransformationService.setLauncherName("xsl100");
			xslTransformationService.setProject(DEFAULT_TEAM_NAME
					+ ".instances_models");
			xslTransformationService.setModelfolder("instances");
			xslTransformationService.setInput(currentModel);
			xslTransformationService.setParametername("supermarketname");
			xslTransformationService.setParametervalue("tralala");
		}
		xslTransformationService.run();
		if (languageConfiguration)
			refreshLanguageRepositoryProject();
		else
			refreshModelRepositoryProject();
	}

	/*
	 * protected void startTomcat() { IHttpService tomcatLauncher = new
	 * TomcatService(); tomcatLauncher.setVm(TOMCAT_JVM);
	 * tomcatLauncher.setTomcatHome(TOMCAT_HOME); tomcatLauncher.setStart();
	 * tomcatLauncher.run(); }
	 *
	 * protected void stopTomcat() { IHttpService tomcatLauncher = new
	 * TomcatService(); tomcatLauncher.setVm(TOMCAT_JVM);
	 * tomcatLauncher.setTomcatHome(TOMCAT_HOME); tomcatLauncher.setStop();
	 * tomcatLauncher.run(); }
	 *
	 * protected void runAnt() { RcpPackager l = new RcpPackager(); l.run(); }
	 */

	private void generateGrammarAndSaveLayouts() {
		saveAllEditors();
		generateGrammar();
		saveAllEditors();
		layout(false, true);
	}

	private void buildInitialLanguage_(boolean stateless) { // FP140521
		buildInitialLanguageDone = true;
		if (LOG)
			clog("buildInitialLanguage");
		else if (UI_LOG)
			clogui("buildInitialLanguage - start");
		timedBuild(stateless);
		if (UI_LOG)
			clogui("buildInitialLanguage - end");
	}

	@Override
	public void change(Diagram diagram) {
		if (!buildInitialLanguageDone && diagram == null)
			cinfo("workbench will be in use ");
	}

	private void getDiagraphActions() {
		String diagraphactions = grammarGeneratorService.getDiagraphActions();
		txtDiagraphActions.setText(diagraphactions);
		saveDiagraphActions(diagraphactions);
	}

	protected void doTransform() {
		resolveMegamodelConfiguration();
		if (languageConfiguration) {
			transformationService.init(matcherLeftPath_, matcherRightPath_,
					languageConfiguration);
			transformationService.run();
		}

	}

	@Override
	public void execute(String cmd, int method, URI[] uri) {
		if (cmd.equals("traceAlignAndSave") && method == 2) {
			if (LOG)
				clog("********** exec traceAlignAndSave()");
			// similarityService.alignAndSave(uri[0], uri[1], uri[2],
			// uri[3],uri[4]);// FP130523
		} else if (cmd.equals("setTraceUri") && method == 2) {
			if (LOG)
				clog("setTraceUri");
			// similarityService.setTrace(uri[0]);// FP130502
		}
	}

	protected void similaritySearch() {

		if (!"".equals(textSimilaritySearch.getText())) // FP130502
			alignmentTable.searchPattern(textSimilaritySearch.getText());
	}

	protected void similarityRemove() {

		// similarityService.removeAll(alignmentTable.getToRemove());////
		// FP130523
		// buildTable(similarityService.getTrace().getTraces());
	}

	private void sleep(int timeout, String sender) {
		if (Display.getCurrent() == null)
			return;
		final long start = System.currentTimeMillis();
		final long deltaMillis = timeout;
		do {
			while (Display.getCurrent().readAndDispatch()) {
				;
			}
		} while ((System.currentTimeMillis() - start) < deltaMillis);
		if (sleep_LOG_)
			clog("sleep " + timeout + " " + sender);
	}

	private List<View> getSelection() {
		List<View> viewSelected = new ArrayList<View>();
		ISelection selection = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getSelectionService()
				.getSelection();
		if (!(selection instanceof IStructuredSelection)) {
			return Collections.emptyList();
		}
		for (Object object : ((IStructuredSelection) selection).toList()) {
			if (object instanceof IGraphicalEditPart
					&& !(object instanceof DiagramEditPart)) {
				viewSelected.add(((IGraphicalEditPart) object)
						.getNotationView());
			}
		}
		return viewSelected;
	}

	private boolean existsLWB__() { // FP140218
		boolean result = false;
		String pluginMegamodel = "platform:/plugin/"
				+ DiagraphPreferences.getStringPreference(KEY_UNIVERSE_NAME);
		megamodeluri = CommonPlugin.resolve(URI.createURI(pluginMegamodel))
				.toFileString();
		String fp = org.isoe.fwk.platform.resolver.Activator.getWorkspacePath()
				+ DiagraphPreferences.getStringPreference(KEY_UNIVERSE_NAME);
		File megamodelFolder = new File(fp);
		megamodelPath = megamodelFolder == null ? "" : megamodelFolder
				.getAbsolutePath();

		boolean megamodelPluginExists = megamodeluri != null;
		boolean megamodelPathExists = megamodelFolder.exists()
				&& megamodelFolder.isDirectory();

		result = megamodelPluginExists;

		if (CHECK_MEGAMODEL_OPENESS_ON_DEPLOYED_FEATURE) {
			if (!megamodelPluginExists && megamodelPathExists) // use physical
																// path and not
																// resources
				throw new RuntimeException(
						"the project \""
								+ DiagraphPreferences
										.getStringPreference(KEY_UNIVERSE_NAME)
								+ "\" must be open !!!");
		}

		return result;
	}

	private void resolveMegamodelConfiguration() {

		// String initialState =
		// PathPreferences.getPreferences().getConnectState();
		String connectState = DiagraphPreferences.getConnectState();
		String teamName_ = DiagraphPreferences.getTeamName__();
		String passWord = DiagraphPreferences.getPassword();
		String email = DiagraphPreferences.getEmail();
		String teamNamespace = DiagraphPreferences.getTeamNamespace();
		String userName_ = DiagraphPreferences.getUserName__();

		labelTeamNamespace_.setText(teamNamespace);
		lblUserName.setText("User Name: " + userName_);
		lblTeamName.setText("Team Name: " + teamName_);
		lblPassword.setText("Password: "
				+ (passWord == null ? "none" : "*****"));
		lblConnectState.setText("Connect State: " + connectState);
		lblUserEmail
				.setText("User e-mail: " + (email == null ? "none" : email));

		existsLanguageRepository_ = existsLWB__();// megamodelFolder.exists() &&
													// megamodelFolder.isDirectory();

		// if (DEPLOY)//FP140218
		// ResourceManager.openProjectIfExists("_megamodel");//FP140218

		// existsLanguageRepository= megamodeluri != null;

		// is a
		// metamodelside-megamodel
		// is
		// deployed
		// on
		// the
		// eclipse
		// side
		// ?
		// existsLanguageRepository
		// =ResourceUtils.projectExists(DiagraphPreferences.getStringPreference(KEY_UNIVERSE_NAME));
		// //is a metamodelside-megamodel is deployed on the eclipse side ?

		String instanceRepositoryLocation = getInstanceRepositoryLocation();

		modelConfiguration = ResourceUtils
				.projectExists(instanceRepositoryLocation); // is
															// the
		// instance
		// repository
		// created ?

		languageConfiguration = ResourceUtils.projectExists(DiagraphPreferences
				.getStringPreference(KEY_UNIVERSE_NAME)); // if
															// a
		// metamodelside-megamodel
		// is
		// deployed
		// on the
		// workspace//
		// side
		if (modelConfiguration && languageConfiguration) // FP130430
			languageConfiguration = false;

		if (languageConfiguration)
			modeLabel.setText("LWB");
		else
			modeLabel.setText("pbsbw");
		if (DParams.Startup_LOG)
			clogStartup("resolveMegamodelConfiguration " + modeLabel.getText());// FP141227
	}

	private void clogStartup(String mesg) {
		if (DParams.Startup_LOG)
			if (LOG_ON_CONSOLE)
				logConsole(mesg);
			else
				System.out.println(mesg);

	}

	private void initDotifier() {
		final String[] dotifyoptions = new String[6];
		dotifyoptions[0] = IDotifier.DOT_OPT_M1_RAW;
		dotifyoptions[1] = IDotifier.DOT_OPT_M1_COMPOUND;
		dotifyoptions[2] = IDotifier.DOT_OPT_M2;
		dotifyoptions[3] = IDotifier.DOT_OPT_DM2;
		dotifyoptions[4] = IDotifier.DOT_OPT_M0;
		dotifyoptions[5] = IDotifier.DOT_OPT_ALL;

		dotifyTypeCombo.setItems(dotifyoptions);
		dotifyTypeCombo.setText(dotifyoptions[5]);

		comboGraphViz.setItems(dotifyoptions);
		comboGraphViz.setText(dotifyoptions[5]);

		loggerLabel1.setText("init...");
	}

	private void initLanguageDiagram() {
		Diagram ecoreDiagram = getEcoreDiag();
	}

	private void refreshWorkspace() {
		try {
			ResourcesPlugin
					.getWorkspace()
					.getRoot()
					.refreshLocal(IResource.DEPTH_INFINITE,
							new NullProgressMonitor());
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	private void initPanelsForLanguageConfig() {// FP130606
		compositeDev.setBackground(SWTResourceManager.getColor(255, 235, 205));
		Rectangle r15 = composite_10_put_language.getBounds();
		r15.width = 451;
		composite_10_put_language.setBounds(r15);
		Rectangle r14 = composite_9_getlanguage.getBounds();
		r14.x = composite_17.getBounds().width
				+ composite_8_get_buildin.getBounds().width + 2;
		r14.y = composite_17.getBounds().y;
		r14.width = wid - composite_8_get_buildin.getBounds().width;// 670;
		composite_9_getlanguage.setBounds(r14);
		composite_11_putodsml.setVisible(false);
		composite_13.setVisible(false);
		composite_19.setVisible(false);
		panelUpper.setVisible(true);
		btnDiagraphExpe.setVisible(true);
		text_1.setVisible(true);

	}

	@Override
	public boolean isUnderProject(EcoreDiagramEditor part) { // FP130618
		boolean resul = false;
		EcoreDiagramEditor ecoreDiagramEditor = getEcoreDiagramEditor();
		if (ecoreDiagramEditor != null) {
			Resource r = ecoreDiagramEditor.getEditingDomain().getResourceSet()
					.getResources().get(0);
			resul = !r
					// not under _megamodel
					.getURI()
					.toPlatformString(false)
					.startsWith(
							"/"
									+ UNIVERSE_PROJECT_NAME
									+ "/"
									+ DiagraphPreferences
											.getStringPreference(KEY_UNIVERSE_FOLDER)
									+ "/");

			resul = resul && languageOk(getModel(part.getDiagram())); // FP140217
		}
		return resul;
	}

	private void initForLanguageConfig() {// FP130520zz1
		if (LOG)
			clog("start initForLanguageConfig ");
		else if (UI_LOG)
			clogui("start initForLanguageConfig ");
		initLanguageDiagram();
		// getViews(); //FP140626removed //FP140421
		initDotifier();
		EcoreDiagramEditor ecoreDiagramEditor = getEcoreDiagramEditor();
		if (ecoreDiagramEditor != null) {
			if ((EPackage) ecoreDiagramEditor.getDiagram().getElement() != null) {
				EPackage p = (EPackage) ecoreDiagramEditor.getDiagram()
						.getElement();
				if (isUnderProject(ecoreDiagramEditor)) {
					setCurrentEcoreDiagram(null,
							ecoreDiagramEditor.getDiagram()); // FP130618
					if (UI_LOG)
						clogui("current=" + p.getName());
				} else {
					setCurrentEcoreDiagram(null, null);
					cerror("initialization error " + p.getName());
				}
			}
			refreshLanguageCombos();
			if (LOG)
				clog("initForLanguageConfig "
						+ ecoreDiagramEditor.getDiagram().getElement()
								.toString());
		}
		lblLanguageDesign_1.setText("Language under");
		initPanelsForLanguageConfig();

		checkData();
		// readyToWork = true;
		// if (UI_LOG)
		// clogui("(1)ready to work now");
		// setMenubarVisible(false);
		// if (diagraphRuntimeFactory == null)
		// diagraphRuntimeFactory = new DiagraphRuntimeFactory_(this);
		if (DO_MEGAMODEL_CHECK_STATUS_AFTER_LANGUAGE_CONFIG)
			afterLanguageInit();
		if (LOG)
			clog("end initForLanguageConfig ");
	}

	private void clogui(String mesg) {
		if (UI_LOG)
			System.out.println(mesg);
	}

	private void initForModelConfig() { // FP130520zz1
		initDotifier();
		Diagram diagram = getCurrentDiagram();
		if (diagram != null) {
			try {
				setCurrentGenericDiagram_(diagram);
			} catch (Exception e) {
				cerror("error in initForModelConfig");
			}
			initializeCurrentModel(diagram);
			if (LOG)
				clog("initForModelConfig " + diagram.getElement().toString());
		}
		compositeDev.setBackground(SWTResourceManager.getColor(173, 216, 230));
		Rectangle r6 = composite_11_putodsml.getBounds();
		r6.x = composite_8_get_buildin.getBounds().x;
		r6.y = composite_8_get_buildin.getBounds().y;
		composite_11_putodsml.setBounds(r6);
		Rectangle r19 = composite_19.getBounds();
		r19.x = composite_11_putodsml.getBounds().width
				+ composite_17.getBounds().width + 2;
		r19.y = composite_11_putodsml.getBounds().y;
		r19.width = wid - composite_11_putodsml.getBounds().width - 1;
		composite_19.setBounds(r19);
		panelTu.setVisible(false);
		btnLayoutAnots.setVisible(false);
		lblDesignThe.setText("2a. Design the current model");
		Rectangle r7 = composite_7.getBounds();
		r7.x = panelTu.getBounds().x;
		r7.y = panelTu.getBounds().y;
		r7.width = wid;
		composite_7.setBounds(r7);
		Rectangle r13 = composite_13.getBounds();
		r13.x = composite_12.getBounds().x;
		r13.y = composite_12.getBounds().y;
		r13.width = wid;
		composite_13.setBounds(r13);
		Rectangle r15 = composite_10_put_language.getBounds();
		r15.x = 150;
		r15.y = composite_17.getBounds().y;
		composite_10_put_language.setBounds(r15);
		composite_8.setVisible(false);
		composite_9.setVisible(false);
		composite_12.setVisible(false);
		composite_13a.setVisible(false);
		composite_8_get_buildin.setVisible(false);
		composite_9_getlanguage.setVisible(false);
		composite_10_put_language.setVisible(false);
		lblLanguageDesign_1.setText("Model under");
		lblNewLabel.setForeground(SWTResourceManager
				.getColor(SWT.COLOR_DARK_GREEN));
		lblLanguageDesign_1.setForeground(SWTResourceManager
				.getColor(SWT.COLOR_DARK_GREEN));
		lblDesign.setForeground(SWTResourceManager
				.getColor(SWT.COLOR_DARK_GREEN));
		lblAdvancedFeatures.setForeground(SWTResourceManager
				.getColor(SWT.COLOR_DARK_GREEN));
		lblMegamodelFeatures.setForeground(SWTResourceManager
				.getColor(SWT.COLOR_DARK_GREEN));
		panelUpper.setVisible(true);

		wipePages();

	}

	private void wipePages() {
		if (actionWiper != null)
			for (IWorkbenchPage page : pagestowipe) {
				actionWiper.wipeActions(page);
			}
	}

	protected void initControler() {
		// Diagram diagram = getCurrentDiagram();
		if (LOG) {

			if (modelConfiguration && languageConfiguration) // FP130627
				clog("????**** init both ****????");
			if (modelConfiguration) {
				clog("init MWB");
			}
			if (languageConfiguration)
				clog("init LWB");
		}

	}

	private void initializeCurrentMetaModel(Diagram diagram) {
		// refreshLanguageCombos();
		if (LOG)
			clog("TODO initializeCurrentMetaModel");
	}

	private void initializeCurrentModel(Diagram diagram) {
		EObject rootobj = diagram.getElement();
		URI uri = rootobj.eResource().getURI();
		if (LOG)
			clog(uri.toString());
		String modelName = uri.toString();
		modelName = modelName.substring(modelName.lastIndexOf("/") + 1);
		setCurrentModel(modelName);
	}

	protected void deployLocalModelsFromLWBIfNotDeployed_() {// 2 // if
		if (ModelDeployService_LOG)
			clog("deployLocalModelsFromLWBIfNotDeployed");
		if (!DParams.MEGAMODEL_DEPLOY){
			cinfo("no deployment see DParams.MEGAMODEL_DEPLOY");
			return;
		}

		deployModels_(PathPreferences.getPreferences().getUniverseProjectName(),
				MEGAMODEL_FOLDER_LOCAL, "", false);
	}

	private void deployLocalModelsFromLWBIfnewMwb() { // 1 // prepare a new MWB
		if (!DParams.MEGAMODEL_DEPLOY){
			cinfo("no deployment see DParams.MEGAMODEL_DEPLOY");
			return;
		}
		deployModels_(PathPreferences.getPreferences().getUniverseProjectName(),
				MEGAMODEL_FOLDER_LOCAL, "", true);
	}

	private boolean initDeployer() {
		if (deployModelService_ == null) {
			setKo(true, "No Deployment Service");
			return false;
		}
		return true;
	}

	private void initialise_(final IDiagraphControler view) {
		final IDiagraphControler thisControler = this;
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				int step = 0;
				try {
					if (!initDeployer())
						return;
					sleep(50, "initialise 1");
					step = 1;
					clog("initialise 1");
					initConfig_(); // FP131220 nawel
					step = 2;
					clog("initialise 2");
					sleep(5, "initialise 2");

					initServer();

					if (!automationService.isRunning())
						cerror("no automation server");

					step = 3;
					clog("initialise 3");
					restoreListeners(true);
					step = 4;
					clog("initialise 3");
					if (modelConfiguration)
						deployLocalModelsFromLWBIfNotDeployed_();//FP150510
					step = 5;
					initControler();
					step = 6;
					redrawGui();
					step = 7;
					readyToWork = true;
					if (UI_LOG)
						clogui("(1)ready to work now");
					layout(true, true); // FP140211a
					if (LOG)
						clog("initialized ...");
				} catch (Exception e) {
					String msg = e.getMessage();
					if (msg == null)
						msg = e.toString();
					cerror("error while initialise!! "
							+ (msg == null ? "?" : msg));
					thisControler.setKo(true,
							"error " + step + " " + e.toString());
				}
			}

		});
	}

	@Override
	public void handleEvent(Event event) {
		// filterKey(event);
	}

	public void filterKey_nu__(Event event) {
		// clog("key="+event.keyCode);
		if (event.keyCode == 127 && currentGraphicalEditPart != null
				&& currentGraphicalEditPart instanceof EAnnotationEditPart) { // ESC
																				// pressed
			// timedAction_nu(this, 100,
			// currentGraphicalEditPart,"delete.annotation");
			event.keyCode = 32;
		}
		/*
		 * if (event.keyCode == 114 && currentGraphicalEditPart !=null &&
		 * currentGraphicalEditPart instanceof EReferenceEditPart) { // ESC
		 * pressed timedAction(this, 100, currentGraphicalEditPart,"add.ref");
		 * event.keyCode = 32; }
		 */
	}

	@Override
	public void setFocus() {
		// viewer.getControl().setFocus();
		// if (parentComposite!=null)
		// parentComposite.setFocus();
		logTxtError.setFocus();
		// text1.setFocus();
	}

	private String execAllViews(String ecoreLocation,
			boolean refreshArtifactsOnly, IProgressMonitor progressMonitor) {
		String result = "";
		// boolean refreshArtifactsOnly=false;
		for (String view : getViews(ecoreLocation))
			try {
				if (LOG)
					clog("EALV " + view);
				diagraphGenerator.execDiagraphTransformation(view,
						ecoreLocation, false, refreshArtifactsOnly,
						progressMonitor);

			} catch (Exception e) {
				result += "error while execAllTransfo for " + ecoreLocation
						+ " and view " + view + " , not generated !!!"
						+ " cause: " + e.toString() + "\r\n";
				if (LOG)
					clog("******* " + result + " ********");
			}
		return result;
	}

	public void showDiagraphLog() {
		String mesg = diagraphGenerator.getGenLog();
		if (org.isoe.fwk.utils.DLog.getWarnings().length() > 0)
			if (!mesg.contains(DLog.getWarnings())) {
				String warn = "Warnings :" + DLog.getWarnings();
				mesg += warn;
			}
		List<String> logs = new ArrayList<String>();
		logs = diagraphGenerator.getDiagraphRunnerDiagraphLog(); // FP120529
		if (logs != null)
			for (String log : logs)
				mesg += log + "\n";
		// IWorkbenchPage page = PlatformUI.getWorkbench()
		// .getActiveWorkbenchWindow().getActivePage();

		// page.showView(DiagraphConsole.ID);//FP120529 do not show !!!
		/*
		 * IViewPart view =
		 * page.findView(org.isoe.diagraph.diagraphconsole.views
		 * .DiagraphConsole.ID); // FP120529 if (view != null)
		 * ((org.isoe.diagraph.diagraphconsole.views.DiagraphConsole)
		 * view).showText(mesg);
		 */
	}

	@Override
	public void dispose() {
		removeListeners_();
		// stopServer(); //TODO the server stopping process
		super.dispose();
	}

	protected void retrieveM2(String name) {
		if (name != null && !name.isEmpty())
			retrieveM2ForModel(name);// ("default.food");
		else
			showMessage("choose a model !");

	}

	private void runDotifyAction() {
		resolveMegamodelConfiguration();
		if (languageConfiguration) {
			languageDotifyService.init(matcherLeftPath_, matcherRightPath_,
					languageConfiguration);

			languageDotifyService.run();
		} else
			modelDotifyService.run();
	}

	private void runSimilarity() { // FP130523
		/*
		 * if (matcherLeftPath != null && matcherRightPath != null) {
		 * resolveMegamodelConfiguration();
		 * similarityAction.init(matcherLeftPath, matcherRightPath,
		 * languageConfiguration);
		 * similarityAction.setMetamodelRetriever(metamodelRetriever); //
		 * //FP130519 similarityAction.run(); } else
		 * showMessage("choose right and left models to compare !!!");
		 */
	}

	/*
	 *
	 * public ISimilarityService getSimilarityService() { //FP130523 if
	 * (similarityService == null) similarityService = new SimilarityConnector()
	 * .getSimilarityService(); // seek the similarity service through the
	 * Eclipse Extension Mechanism if (similarityService == null) { if (LOG)
	 * clog("No similarity service is available !"); similarityService = new
	 * SimilarityStub(); // use a mock if the real service is not discovered by
	 * the Eclipse // Extension Mechanism } else if (LOG)
	 * clog("Using Isoe Similarity Feature for comparing models");
	 * similarityService.setOwner(this); return similarityService; }
	 */

	private void retrieveM2ForModel(String name) {
		m2PackageLabel.setText("");
		if (name == null || name.isEmpty()) {
			m2PackageLabel.setText("not a model");
			return;
		}
		try {
			String[] pl = name.split("\\.");
			name = pl[0];

			String leftExtension = pl[1];
			URI leftMUri = createplatformResourceModelURI(PathPreferences
					.getPreferences().getInstanceRepositoryLocation(),
					INSTANCE_FOLDER, name, leftExtension);
			EPackage p2 = getMetamodelRetriever().findRegisteredMetamodel(
					leftMUri, new NullProgressMonitor());
			if (p2 != null) {
				Resource rr = p2.eResource(); // FP130430xx
				URI ruri = CommonPlugin.resolve(p2.eResource().getURI());
				String path = ruri.toFileString();

				m2PackageLabel.setText(path);
				// m2PackageLabel.setText(p2.eResource().getURI().toString());
			} else
				m2PackageLabel.setText("not a model");
		} catch (Exception e) {
			m2PackageLabel.setText("error");
		}
	}

	private URI createplatformResourceModelURI(String plugin, String folder,
			String traceModelName, String traceExtension) {
		URI result = null;
		if (!EMFPlugin.IS_ECLIPSE_RUNNING) {
			String fp = Activator.getWorkspacePath() + plugin + File.separator
					+ folder + File.separator + traceModelName + "."
					+ traceExtension;
			result = URI.createFileURI(fp);
			return result;
		} else {
			folder = folder.replaceAll(Separator.SEPARATOR, "/");
			result = URI.createURI("platform:/resource/" + plugin + "/"
					+ folder + "/" + traceModelName + "." + traceExtension);
			return result;
		}
	}

	@Override
	public String[] getViews() {
		if (layers_ == null)
			try {
				runlayout();
				// layoutService.run();
			} catch (Exception e) {
				loggerLabel1.setText(e.toString());
				cerror("error in  getViews()");
			}
		return layers_;
	}

	private Resource loadMetamodel(String metamodelPath) {
		ResourceSet metamodelSet = new ResourceSetImpl();
		metamodelSet
				.getResourceFactoryRegistry()
				.getExtensionToFactoryMap()
				.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
						new EcoreResourceFactoryImpl());
		URI metamodelURI = URI.createFileURI(new File(metamodelPath)
				.getAbsolutePath());
		return metamodelSet.getResource(metamodelURI, true);
	}

	private String getViewName(EAnnotation anot) {
		EMap<String, String> entries = anot.getDetails();
		for (Entry<String, String> entry : entries) {
			String k = entry.getKey();
			if (k.startsWith("view")) {
				String[] kv = k.split("=");
				return kv[1];
			}
		}
		return ViewConstants.DIAGRAPH_DEFAULT;
	}

	@Override
	public void showMessage(String message) {
		if (!message.equals("Index: 0, Size: 0")) // TODO an exception
													// hierarchy...
			MessageDialog.openInformation(getSite().getShell(),
					"Diagraph View", message);
	}

	private void setRepositoryRihtCombo(String[] entries) {
		matcherComboRight.setItems(entries);
		String last = entries.length == 0 ? "" : entries[entries.length - 1]; // FP131116
		if (lastRightChoice_ != null
				&& Arrays.toString(entries).contains(lastRightChoice_))
			matcherComboRight.setText(lastRightChoice_);
		else {
			lastRightChoice_ = last;
			matcherComboRight.setText(lastRightChoice_);
		}
	}

	private void setRepositoryLeftCombo(String[] entries) {
		matcherComboLeft.setItems(entries);
		String cleft = entries.length == 0 ? "" : entries[entries.length - 1]; // FP131116
		matcherComboLeft.setText(cleft);
	}

	@Override
	public Shell getShell() {
		return getSite().getShell();
	}

	@Override
	public void log(String dest, String what) {
		// if ("retrievedM2Label".equals(dest)) // TODO a real Exception
		// hierarchy // and MVC paradigm
		if ("m2PackageLabel".equals(dest)) // TODO a real Exception hierarchy //
			// and MVC paradigm
			m2PackageLabel.setText(what);
		else if (dest != null && dest.contains("error")) {
			text1.setText(what);
			text1.setBackground(SWTResourceManager.getColor(255, 99, 71));
			logTxt.setText(what);
			logTxtError.setText(what);
			timedClear( 2000, logTxtError);
		} else if (dest != null && dest.equals("m2-uploaded")) {
			textStatus.setText(what + " uploaded");
			// txtLanguageNamespace.setText(what);
		} else if (dest != null && dest.equals("dg-remoting-save-model")) {
			textStatus.setText(what);
		} else if (dest != null && dest.equals("dg-remoting-save-meta-model")) {
			textStatus.setText(what);
		} else if (dest != null && dest.equals("dg-remoting-load-meta-model")) {
			textStatus.setText(what);
		} else if (dest != null && dest.equals("dg-remoting-load-model")) {
			textStatus.setText(what);
		} else if (dest != null && dest.equals("no-remote-repository")) {
			textStatus.setText(what);
		} else {
			if (isDev())
				logTxt.setText(logTxt.getText() + what);
			else {
				textLog.setText(textLog.getText() + what + "\r\n");
			}
		}
	}

	@Override
	public void setLastChoice(String text) {
		lastRightChoice_ = text;
	}

	@Override
	public String getCommandId() {
		return isDev() ? dotifyTypeCombo.getText() : comboGraphViz.getText();
	}

	private String getDomainName(String proj) {
		if (!proj.endsWith(".edit") && !proj.endsWith(".editor")
				&& !proj.endsWith(".tests"))
			return proj.substring(proj.lastIndexOf(".") + 1);
		else
			return null;
	}

	@Override
	public String getParseErrorLog() { // FP120715
		return " ????? getParseErrorLog";// diagraphGenerator.getParseError();
	}

	@Override
	public void setParseErrorLog(String error) {
		if (error != null)
			loggerLabel1.setText(error);
		else
			loggerLabel1.setText("unknown error");
	}

	@Override
	public void endParsed(Diagram currentDiagram) {
	}

	@Override
	public List<String> getAllViews() {
		List<String> result = new ArrayList<String>();
		for (String view : layers_)
			// if (!view.equals(ViewConstants.DIAGRAPH_ALL_VIEW_LITTERAL_))
			result.add(view);
		return result;
	}

	@Override
	public void removeView(String view) {
		try {
			List<String> oldLayers_ = new ArrayList<String>();
			// if (!view.equals(ViewConstants.DIAGRAPH_ALL_VIEW_LITTERAL_))

			for (String layer : layers_) {
				if (!layer.equals(view))
					oldLayers_.add(layer);
			}
			// else
			// oldLayers_.add(ViewConstants.DIAGRAPH_DEFAULT);

			int i = 0;
			String[] laz = new String[oldLayers_.size()];
			for (String ol : oldLayers_) {
				laz[i++] = ol;
			}
			String curnt = ViewConstants.DIAGRAPH_DEFAULT;
			String cviu = getCurrentView(31, langageName, "______SB_removeView"); // FP140611
			if (!view.equals(cviu))
				curnt = cviu;
			setViews(this, 0, langageName, laz, curnt); // FP140611
		} catch (Exception e) {
			cerror("error in removeView " + view);
		}

	}

	@Override
	public int getCurrentPovId() {
		// if (currentPov.equals(ViewConstants.DIAGRAPH_ALL_VIEW_LITTERAL_))
		// return 0;
		// else
		return currentPovId;
	}

	public String getCurrentPovName() {
		// if (currentPov.equals(ViewConstants.DIAGRAPH_ALL_VIEW_LITTERAL_))
		// return ViewConstants.DIAGRAPH_DEFAULT;
		// else
		return currentPov;
	}

	@Override
	public EClass getCurrentPov() {
		return languageParser.getPovForView(
				currentPackage,
				getCurrentView(40, currentPackage.getName(),
						"______SB_getCurrentPov")); // FP140611
													// //
													// FP131008xx
	}

	@Override
	public EClass getEClass(String eClassName) {
		return (EClass) currentPackage.getEClassifier(eClassName);
	}

	@Override
	public Object getRuntimeFactory() {
		return diagraphRuntimeFactory_;
	}

	private boolean refreshLanguageCombos() {
		try {
			String[] lefts = megamodelManager.getLanguageFiles(
					matcherLeftPath_, null);
			String[] rights = megamodelManager.getLanguageFiles(
					matcherRightPath_, null);
			if (lefts.length > 0) {
				setRepositoryLeftCombo(lefts);
				matcherComboLeft.setText(lefts.length == 0 ? "" : lefts[0]); // FP131116
			}
			if (rights.length > 0) {
				setRepositoryRihtCombo(rights);
				matcherComboRight.setText(rights.length == 0 ? "" : rights[0]);
			} else {
				setMatcherRightPath_(matcherLeftPath_);
				setRepositoryRihtCombo(lefts);
				matcherComboRight.setText(lefts.length == 0 ? "" : lefts[0]);
			}
			return true;
		} catch (Exception e) {
			cerror("error RLC " + e.toString());
			return false;
		}
	}

	// @Override
	private IWorkbenchPage getActivePage_() { // FP130130
		IWorkbenchWindow window = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		if (window == null) {
			return null;
		}
		return window.getActivePage();
	}

	private URI getCurrentResourceURI() {
		IWorkbenchPage page = getActivePage_();
		if (page != null) {
			IWorkbenchPart workbenchPart = page.getActivePart();
			if (workbenchPart != null) {
				if (LOG)
					clog(workbenchPart.getClass().getName());
				if (workbenchPart instanceof DiagramDocumentEditor) {
					DiagramDocumentEditor ddocumentEditor = (DiagramDocumentEditor) workbenchPart;
					TransactionalEditingDomain transactionalEditingDomain = ddocumentEditor
							.getEditingDomain();
					ResourceSet rs = transactionalEditingDomain
							.getResourceSet();
					List<Resource> rss = rs.getResources();
					for (Resource resource : rss) {
						if (LOG)
							clog(resource.getURI().toString());
						EObject o = resource.getContents().get(0);
						if (o instanceof Diagram) {
							Diagram diagram = (Diagram) o;
							Resource mResource = diagram.getElement()
									.eResource();
							if (LOG) {
								clog(mResource.getURI().toString());
								clog(diagram.getElement().toString()); // isoe.bibtex.impl.BibTeXFileImpl@19e9750
							}
							return mResource.getURI();
						}
					}
				}
			}
		}
		return null;
	}

	public boolean refreshModelCombos(boolean init) {
		if (init) {

			String ws = ResourceUtils.getWorkspaceDirectory();

			URI curent = getCurrentResourceURI();
			if (curent != null) {
				String f = curent.toString();
				f = f.substring(f.lastIndexOf("/") + 1);
				String[] fs = new String[1];
				fs[0] = f; // FP130425
			}
		}
		try {
			String[] vs = megamodelManager.getModelsInRepository(null);
			if (vs.length > 0) {
				setRepositoryLeftCombo(vs);
				matcherComboLeft.setText(vs[0]);
				setRepositoryRihtCombo(vs);
				matcherComboRight.setText(vs[0]);
				setModelMatcherPath();
			}
			return true;
		} catch (Exception e) {
			if (LOG)
				clog("error in refreshModelCombos " + e.toString());
			return false;
		}
	}

	private void setModelMatcherPath() {
	}

	@Override
	public String getChoice(int i) {
		setModelMatcherPath();
		return i == 1 ? matcherComboLeft.getText() : matcherComboRight
				.getText();
	}

	private void refreshModelCombo(String selected) {
		if (refreshModelCombos(false)) {
			matcherComboLeft.setText(selected);
			if (matcherComboRight.getText() == null
					|| matcherComboRight.getText().trim().isEmpty())
				matcherComboRight.setText(selected);
		}
	}

	@Override
	public void clearLog(int target) {
		switch (target) {
		case 1:
			error = "";
			if (text1 != null)
				text1.setText("");
			break;
		case 2:
			if (text2 != null)
				text2.setText("");
			break;
		/*
		 * case 3: if (text3 != null) text3.setText(""); break; case 4: if
		 * (text4 != null) text4.setText(""); break;
		 */
		}
	}

	@Override
	public void log(int target, String mesg) {
		switch (target) {
		case 1:
			if (mesg.contains("should not contain any space"))
				showMessage(mesg); // FP131226
			if (text1 != null) {
				text1.setText(text1.getText() + mesg + "\r\n");
				error = error + "  -   " + mesg;
			}
			break;
		case 2:
			if (text2 != null)
				text2.setText(text2.getText() + mesg + "\r\n");
			break;

		case 4:
			logDiagraph += mesg + "\r\n";
			break;
		/*
		 * case 3: if (text3 != null) text3.setText(text3.getText() + mesg +
		 * "\r\n"); break; case 4: if (text4 != null)
		 * text4.setText(text4.getText() + mesg + "\r\n"); break;
		 */
		default:
			if (LOG)
				clog("LBW " + target + " -- " + mesg);
		}
	}

	@Override
	public void log(String mesg) { // FP120715
		log(2, mesg);
	}

	@Override
	public String getError() {
		return error;
	}

	@Override
	public void clearErrorLog() {
		text1.setText("");
		logTxt.setText("");
		text1.setBackground(SWTResourceManager.getColor(255, 255, 255));
	}

	@Override
	public void logResponse_(final String mesg) {
		if (Automation_LOG)
			clogAutomation("logresponse: " + mesg);
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				logTxt.setText(logTxt.getText() + "\r\n cli:" + mesg);
			}
		});
	}

	private void clogAutomation(String mesg) {
		if (Automation_LOG)
			System.out.println(mesg);
	}

	@Override
	public void logServer(final String mesg) {
		if (Automation_LOG)
			clogAutomation("logserver: " + mesg);
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				logTxt.setText(logTxt.getText() + "\r\n srv:" + mesg);
			}
		});

	}

	private String drawCircle(String cmd) {
		if (Automation_LOG)
			clogAutomation("drawCircle(: " + cmd);
		String response = "";
		try {
			String[] c = cmd.split(" ");
			if (c.length == 2) {
				response = "drawing circle" + c[1] + " OK";
				drawCircle(10, 10, Integer.parseInt(c[1]));
				// response = cmd + " OK";
			}
		} catch (Exception e) {
			response = cmd + " Syntax Error";
		}
		return response;
	}

	private String execRefresh(String cmd) {
		String response = "";
		try {
			final String[] c = cmd.split(" ");
			if (c.length == 2) {
				response = "refreshing " + c[1] + " OK";
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						textStatus.setText("refreshing megamodel repository");
						refreshMegamodelRepository(c[1]);
					}
				});
			}
		} catch (Exception e) {
			response = cmd + " Syntax Error";
		}
		return response;
	}

	@Override
	public String parseCommand(String clientRequest) {
		if (Automation_LOG)
			clogAutomation("parseCommand: " + clientRequest);
		String response = "unknown command: " + clientRequest;
		if (clientRequest.toLowerCase().contains("circle"))
			response = drawCircle(clientRequest);
		else if (clientRequest.toLowerCase().startsWith(CMD_DEPLOY))
			response = execDeploy_(clientRequest);
		else if (clientRequest.toLowerCase().startsWith(CMD_DIAGRAPH))
			response = execDiagraph_(clientRequest);
		else if (clientRequest.toLowerCase().startsWith(CMD_REFRESH))
			response = execRefresh(clientRequest);

		// if (modelConfiguration &&
		// clientRequest.toLowerCase().startsWith(CMD_REFRESH))
		// response = execRefresh(clientRequest);

		return response;
	}

	private void drawCircle(int x, int y, int radius) {
		logResponse_("circle " + x + "-" + y + "-" + radius);
	}

	@Override
	public void stopServerJob() {
		if (Automation_LOG)
			clogAutomation("stopServerJob");
		if (serverJob != null)
			Job.getJobManager().cancel(serverJob);
		// job.cancel();
	}

	@Override
	public void startup(String basePath) {// FP141223
		this.basePath = basePath;
		cinfo("base path = " + basePath);
		registerWorkbenchListener();
	}

	/**************** automation server operations *************************************/

	// @Override
	/*
	 * public int getPort_old() { int port = 0; if (languageConfiguration) port
	 * = LANGAGE_SERVER_PORT__nu; else port = MODEL_SERVER_PORT__nu; return
	 * port; }
	 */

	@Override
	public int getPort() { // FP140526
		lwbPport = -1;
		if (Automation_LOG)
			clogAutomation("getPort");
		if (languageConfiguration) {
			lwbPport = getPorts_()[0];// LANGAGE_SERVER_PORT;
			if (Automation_LOG)
				clogAutomation("lwb server port = " + lwbPport);
			return lwbPport;
		} else {
			String sport = System.getProperty("port"); // FP140526
			int iport = -1;
			try {
				iport = Integer.parseInt(sport);
				iport += RAND.nextInt(50); // must be different
				if (Automation_LOG)
					clogAutomation("model server =" + iport);
			} catch (Exception e) {
				cerror("port error");
				return -1;
			}
			mwbPort = iport;// MODEL_SERVER_PORT__;
			if (Automation_LOG)
				clogAutomation("mwb server port = " + mwbPort);
			return mwbPort;
		}

	}

	private int[] getPorts_() { // FP140526
		if (ports == null) {
			ports = new int[2];
			ports[0] = 8200 + RAND.nextInt(300);
			ports[1] = ports[0] + 1; // not used
		}
		if (Automation_LOG)
			clogAutomation("port=" + ports[0]);
		return ports;
	}

	@Override
	public void endServerJob() {
		if (Automation_LOG)
			clogAutomation("endServerJob");
		automationService.stopJob();
	}

	private void hide() {
		IViewPart vp = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage()
				.findView(GenericConstants.DIAGRAPH_BASIC_CONTROLER_ID_);
		if (vp != null) {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().hideView(vp);
		}
	}

	private void registerWorkbenchListener() {
		if (LOG)
			clog("registerWorkbenchListener() ");
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow activeWorkbenchWindow = workbench
				.getActiveWorkbenchWindow();
		if (activeWorkbenchWindow == null)
			return;
		IWorkbenchPage page = activeWorkbenchWindow.getActivePage();
		if (page != null) {
			workbench.addWorkbenchListener(new IWorkbenchListener() {
				public boolean preShutdown(IWorkbench workbench, boolean forced) {
					if (LOG)
						clog("shutting down");
					// megamodelBuilder.saveRepository(); //FP140618
					if (automationService != null)
						automationService.stopService();
					hide(); // FP131201
					return true;
				}

				public void postShutdown(IWorkbench workbench) {
				}
			});
		}
	}

	/****************
	 * end(automation server operations),
	 *************************************/
	@Override
	public void setKo(boolean value, String mesg) {
		ko = value;
		if (value) {
			// composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			// composite.setVisible(false);
			showMessage(mesg + ", the wokbench will not work, please open the project named _megamodel and restart Eclipse !!!");
		}

	}

	@Override
	public EcoreDiagramEditor getEcoreDiagramEditor() {
		return diagraphGenerator.findActiveEcoreEditor(null);
	}

	@Override
	public DiagramEditor getGenericDiagramEditor() {
		return diagraphGenerator.findActiveGenericDiagramEditor();
	}

	/*------------------------  recently ---------------------------------*/

	// blazo
	private void generateGrammar() {
		grammarGeneratorService.generateDefaultAnnotations();
	}

	@Override
	public String getViewName() {
		String result = isDev() ? pointOfViewNameText.getText() : textNewPov
				.getText();
		result = result.replaceAll(" ", "_");
		return result;
	}

	@Override
	public void setViewText(String txt) {
		pointOfViewNameText.setText(txt);
		textNewPov.setText(txt);
	}

	@Override
	public boolean isModelConfiguration() {
		return !languageConfiguration;
	}

	private void runlayout() {
		layoutService.run();
	}

	@Override
	public void deployModels(String language, URI megamodelSource,
			List<String> instancesdeployed) {
		if (osemDeployer == null)
			cerror("osemDeployer not installed");
		else
		    osemDeployer
				.deployModels(language, megamodelSource, instancesdeployed);
	}

	private void doOsemJob(String mode) {
		if (LOG)
			clog("doOsemJob " + mode);
		if (osemDeployer == null)
			cerror("osemDeployer not installed");
		else
		    osemDeployer.doOsemJob_(mode, currentPackage.getName());
	}

	@Override
	public void alert(boolean headless, String message) { // FP140202
		logGeneration += message;
		if (!headless)
			showMessage(message);
		// MessageDialog.openInformation(PlatformUI.getWorkbench()
		// .getActiveWorkbenchWindow().getShell(), "Diagraph", message);
		else {
			if (LOG)
				clog(message);
			else
				cerror(message);
		}
	}

	private void afterLanguageInit() { // FP130903
		boolean headless = false;
		boolean genLanguageNo = false;
		boolean refreshOnlyYes = true;
		String[] args = new String[2];
		args[0] = IDiagraphAlphabet.MEGAMODEL_CHECK_STATUS;
		args[1] = "*"; // FP140605
		String result = invokeMegamodelJob(null, headless, args,// IDiagraphAlphabet.MEGAMODEL_CHECK_STATUS
																// + " *",
				genLanguageNo, refreshOnlyYes);
	}

	private void clogmegamodel(String mesg) {
		System.out.println(mesg);
	}

	protected void useLanguage() {
		cleanRepository();

		 if (LOG)
				clog("copy all items to the repository");
		List<String> prjs = getDiagraphProjectNames();
		for (String prj_ : prjs) {

			try {
				String[] args = new String[2];
				args[0] = IDiagraphAlphabet.MEGAMODEL_GEN_DSML;
				args[1] = prj_; // FP140605
				// megamodelMan.clearMegamodel_(); //FP140622
				String result = invokeCopyJob(false, args,"repository");
				result = invokeCopyJob(false, args,"m2");
				if (LOG)
					clog(result);
			} catch (Exception e) {
				cerror("error while copying " + prj_);
			}
		}


		modelWorkbenchLauncher.setLauncherName(isDev() ? textWorkbenchName
				.getText() : textLaunchMDWB.getText());
		modelWorkbenchLauncher.setPort(getPort()); // FP140526
		modelWorkbenchLauncher.run();
	}

	private void cleanRepository() {
		if (languageConfiguration) {
			consolidateRepositoryWithProjects();
		} else if (LOG)
			clog("not active in modelConfiguration");
	}

	protected void consolidateRepositoryWithProjects() {
		// TODO Auto-generated method stub
	}

	protected void uploadModelAndLanguage() {
		diagraphRemotingService_.setSave();
		diagraphRemotingService_.setPackage(currentPackage);
		String desc = isDev() ? txtModelDescription.getText() : textModelDesc
				.getText();
		diagraphRemotingService_.setModelDescription(desc);
		diagraphRemotingService_.run();
		txtModelDescription.setText(currentPackage.getName() + "." + desc
				+ " uploaded");
	}

	protected void uploadModelAndLanguageSoonComing() {
		textPutToOpenDsml.setText("Soon coming");
		timedClear( 1000, textPutToOpenDsml);
	}

	/************* deployMegamodel *************************/

	private void downloadModelsAndLanguage_() {
		diagraphRemotingService_.setModelName(langageName);// FP130519
		diagraphRemotingService_.setModelUri(langageNsUri);// FP130509
		diagraphRemotingService_.setModelNsPrefix(langageNsPrefix);// FP130519
		diagraphRemotingService_.setLoad();
		diagraphRemotingService_.run(); // first deployed to the local
										// repository
		if (languageConfiguration) {
			boolean deployBuildInPlugin = false;
			boolean deployLocalPlugin = false;
			boolean deployLocalWorkspace = true; // deployBuildInPlugin,
			// boolean genEmfGen = true; // FP131207
			deployMegamodelClone_(deployBuildInPlugin, deployLocalPlugin,
					deployLocalWorkspace, ""); // second
												// deployed
												// to
												// a
												// project
		}
	}

	private String execDeploy_(String cmd) {
		String response = "";
		try {
			final String[] c = cmd.split(" ");
			if (c.length == 2) {
				response = "deploying language " + c[1] + " OK";
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						txtPutToLwb.setText(c[1]);

						boolean deployBuildInPlugin = false;
						boolean deployLocalPlugin = false;
						boolean deployLocalWorkspace = true; // deployBuildInPlugin,
						// deployLocalPlugin,
						// deployLocalWorkspace
						// boolean genEmfGen = true; // FP131207

						deployMegamodelClone_(deployBuildInPlugin,
								deployLocalPlugin, deployLocalWorkspace, c[1]);
					}
				});
			}
		} catch (Exception e) {
			response = cmd + " Syntax Error";
		}
		return response;
	}

	private String execDiagraph_(String cmd) {
		String response = "";
		try {
			final String[] c = cmd.split(" ");
			if (c.length == 2) {
				response = "diagraphing languages " + c[1] + " OK";
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						txtPutToLwb.setText(c[1]);

						boolean deployBuildInPlugin = false;
						boolean deployLocalPlugin = false;
						boolean deployLocalWorkspace = true; // deployBuildInPlugin,
						// deployLocalPlugin,
						// deployLocalWorkspace

						diagraphMegamodel_(deployBuildInPlugin,
								deployLocalPlugin, deployLocalWorkspace, c[1]);
					}
				});
			}
		} catch (Exception e) {
			response = cmd + " Syntax Error";
		}
		return response;
	}

	private void deployLocalLanguages_() {
		boolean deployBuildInPlugin = false;
		boolean deployLocalPlugin = true;
		boolean deployLocalWorkspace = false;
		// boolean genEmfGen = true; // FP131207
		deployMegamodelClone_(deployBuildInPlugin, deployLocalPlugin,
				deployLocalWorkspace, txtPutToLwb.getText());
	}

	private void initNewMwb_() {
		if (LOG)
			clog("initializing a new MWB");
		deployLocalModelsFromLWBIfnewMwb(); // prepare a new MWB
		resolveMegamodelConfiguration();
		if (existsLanguageRepository_ && modelConfiguration)
			initForModelConfig();
	}

	private void logInitialDeployment() { // FP140126xxxaaa
		List<String> deployed = new ArrayList<String>();

		String l = "";

		String[] initials = INITIAL_LANGUAGES;
		for (String initial : initials) {
			l += initial + ";";
		}
		clog("initial languages=" + l);
		l = "";
		if (deployedLanguages_ != null)
			for (String deployedLanguage : deployedLanguages_) {
				l += deployedLanguage + ";";
				String lang = deployedLanguage.substring(0,
						deployedLanguage.indexOf(".ecore"));
				deployed.add(lang);
			}
		clog("(1)deployedLanguages=" + l);

		l = "";
		if (deployedProjects != null)
			for (IProject deployedProject : deployedProjects) {
				l += deployedProject.getName() + ";";
			}
		clog("deployedProjects=" + l);

		clog("deployment report:");
		for (String initial : initials) {
			if (!deployed.contains(initial)) {
				if (!LOG)
					cerror("failed to deploy " + initial);
				else
					clog("failed to deploy " + initial);
			}
		}
	}

	private void initNewLwb__() {
		if (LOG)
			clog("initializing LWB");
		boolean deployBuildInPlugin = true;
		boolean deployLocalPlugin = true;
		boolean deployLocalWorkspace = true;
		// boolean genEmfGen = true; // FP131207
		deployMegamodelClone_(deployBuildInPlugin, deployLocalPlugin,
				deployLocalWorkspace, "");// ,MEGAMODEL_BUILDIN_LANG_EXAMPLES);
		logInitialDeployment();
		resolveMegamodelConfiguration();
		if (languageConfiguration && !modelConfiguration) {
			initForLanguageConfig();
			megamodelBuilder.saveRepository();
		}
		newLwb = true;
	}

	private void initConfig_() {
		resolveMegamodelConfiguration();
		if (!languageConfiguration && !modelConfiguration) {
			if (existsLanguageRepository_)
				initNewMwb_();
			else
				initNewLwb__();
		} else if (languageConfiguration)
			initForLanguageConfig();
		else if (modelConfiguration)
			initForModelConfig();
	}

	/*********** end deployMegamodel ******************************/

	protected void downloadModelsAndLanguageSoonComing() {
		textGetFromOpenDsml.setText("Soon coming");
		timedClear( 1000, textGetFromOpenDsml);
	}

	private IModelWorkbenchLauncher getModelWorkbenchLauncher() {
		if (modelWorkbenchLauncher == null) {
			modelWorkbenchLauncher = new LaucherConnector().getService();
			if (modelWorkbenchLauncher.isStub()) {
				if (LOG)
					clog("No modelWorkbenchLauncherr is available, using a non functional mock  !");

			} else if (LOG)
				clog("Using Isoe Model Workbench Launcher for lauching the model workbench");
		}
		modelWorkbenchLauncher.setControler(this);
		return modelWorkbenchLauncher;
	}

	private IModelDeployService getDeployMegamodelInstanceService() {
		if (deployModelService_ == null) {
			deployModelService_ = new DeployMegamodelConnector()
					.getModelDeployService();
			// seek the deployMegamodel plugin through the Eclipse Extension
			// Mechanism
			if (deployModelService_ == null) {
				if (LOG)
					clog("No Model deployer service is available !");
				throw new RuntimeException(" no DeployMegamodelInstanceService");
				// use a mock if the real service is not discovered by the
				// Eclipse Extension Mechanism
			} else if (LOG)
				clog("Using Isoe Model deployer for deploying models");
			deployModelService_.setControler(this);

			deployModelService_.setTarget(PathPreferences.getPreferences()
					.getInstanceRepositoryLocation());
			// diagraphServices.add(deployMegamodelInstanceService);

		}
		return deployModelService_;
	}

	private ILanguageDotifyService getLanguageDotifyService() {
		if (languageDotifyService == null) {
			languageDotifyService = new DotifyConnector()
					.getLanguageDotifyService();
			// seek the languageDotify plugin through the Eclipse Extension
			// Mechanism
			if (languageDotifyService == null) {
				if (LOG)
					clog("No language dotifier service is available !");
				throw new RuntimeException("no LanguageDotify");
				// use a mock if the real service is not discovered by the
				// Eclipse Extension Mechanism

			} else if (LOG)
				clog("Using LanguageDotify Feature for graphviz generation");
			languageDotifyService.setControler(this);
		}
		return languageDotifyService;
	}

	private IModelDotifyService getModelDotifyService() {
		if (modelDotifyService == null) {
			modelDotifyService = new DotifyConnector().getModelDotifyService();
			// seek the modelDotify plugin through the Eclipse Extension
			// Mechanism
			if (modelDotifyService == null) {
				if (LOG)
					clog("No model dotifier service is available !");

				// setKo(true,"no ModelDotifyService"); // use this for checking
				// //FP130519
				throw new RuntimeException("no ModelDotify");
				// use a mock if the real service is not discovered by the
				// Eclipse Extension Mechanism
			} else if (LOG)
				clog("Using ModelDotify Feature for graphviz generation");
			modelDotifyService.setControler(this);
		}
		return modelDotifyService;
	}

	private IEcoreUtils getEcoreUtilService() {
		if (ecoreUtilService == null)
			ecoreUtilService = componentConnector.getEcoreUtilService();
		return ecoreUtilService;
	}

	private IDiagraphRemotingService getDiagraphRemotingService() {
		if (diagraphRemotingService_ == null)
			diagraphRemotingService_ = componentConnector
					.getDiagraphRemotingService(); // and
													// so
													// on
		return diagraphRemotingService_;
	}

	private ISyntaxRules getSyntaxRulesService() {
		if (syntaxRulesService == null) {
			syntaxRulesService = componentConnector.getSyntaxRulesService();
			// diagraphServices.add(syntaxRulesService);
		}
		return syntaxRulesService;
	}

	private ISyntaxRefactoring getSyntaxRefactoringService() {
		if (syntaxRefactoringService == null) {
			syntaxRefactoringService = componentConnector
					.getSyntaxRefactoringService(); // and
													// so
													// on
			// diagraphServices.add(syntaxRefactoringService);
		}
		return syntaxRefactoringService;
	}

	private ISyntaxInference getSyntaxInferenceService() {
		if (syntaxInferenceService == null) {
			syntaxInferenceService = new SyntaxGeneratorConnector()
					.getSyntaxInferenceService();
			if (syntaxInferenceService == null)
				throw new RuntimeException("no syntaxInferenceService");
			syntaxInferenceService.setControler(this);
			if (syntaxInferenceService.isStub()) // is a mock object, presently
				// unusable, for further
				// implementation
				if (LOG) {
					clog("syntaxInferenceService is Stub");
					String willDo = syntaxInferenceService.exposeContract();
					if (LOG)
						clog("should do: " + willDo);
				}
			if (!syntaxInferenceService.isQualified()) // is not a mock object
				// but not usable
				if (LOG)
					clog("syntaxInferenceService is Not Qualified");
		}
		return syntaxInferenceService;
	}

	private IGenDiagraphServ getGenDiagraphAction() {
		if (genDiagraphAction == null) {
			genDiagraphAction = new DiagraphActionConnector()
					.GenDiagraphAction();
			// seek the gramgen plugin through the Eclipse Extension Mechanism
			if (genDiagraphAction == null || genDiagraphAction.isStub()) {
				if (LOG)
					clog("No Diagraph Action is available !");
				// genDiagraphAction = new DiagraphActionStub();

				throw new RuntimeException("No Diagraph Action is available");
				// use a mock if the real service is not discovered by the
				// Eclipse Extension Mechanism
			} else if (LOG)
				clog("Using Diagraph Action for invoking Diagraph");
			genDiagraphAction.setControler(this);
		}
		return genDiagraphAction;
	}

	private ITransformationService getTransformationService() {
		if (transformationService == null) {
			transformationService = new TransformationServiceConnector()
					.getTransformationService();
			// seek the similarity action through the Eclipse Extension
			// Mechanism
			if (transformationService == null) {
				if (LOG)
					clog("No Transformation Service  is available, using a mock action !");
				throw new RuntimeException("no TransformationService");
				// use a mock if the real action is not discovered by the
				// Eclipse
				// Extension Mechanism
			} else if (LOG)
				clog("Using Isoe Transformation Service for transforming models");
		}
		transformationService.setControler(this);
		return transformationService;
	}

	private IMegamodelDeployService getMegamodelDeployService() {
		if (deployMegamodelService__ == null) {
			deployMegamodelService__ = new DeployMegamodelConnector()
					.getMegamodelDeployService();
			// seek the deployMegamodel plugin through the Eclipse Extension
			// Mechanism
			if (deployMegamodelService__ == null) {
				if (LOG)
					clog("No deploy Megamodel service is available !");
				throw new RuntimeException(" no DeployMegamodelService");
				// use a mock if the real service is not discovered by the
				// Eclipse Extension Mechanism
			} else if (LOG)
				clog("Using DeployMegamodelService Feature for deploying megamodels");
			deployMegamodelService__.setControler(this);
			// diagraphServices.add(deployMegamodelService);
		}
		return deployMegamodelService__;
	}

	private IMegamodelManager getMegamodelManager() {
		if (megamodelManager == null) {
			megamodelManager = new DeployMegamodelConnector()
					.getMegamodelManager();

			if (megamodelManager == null)
				throw new RuntimeException("no megamodelManager");
			megamodelManager.setControler(this);
			if (megamodelManager.isStub()) // is a mock object, presently
											// unusable, for further
											// implementation
				if (LOG) {
					clog("megamodelManager is Stub");
					// String willDo = megamodelManager.exposeContract();
					// clog("should do: " + willDo);
				}
			if (!megamodelManager.isQualified()) // is not a mock object
				// but not usable
				if (LOG)
					clog("megamodelManager is Not Qualified");
		}
		return megamodelManager;
	}

	private IXslTransformer getXslTransformationService() {
		if (xslTransformationService == null) {
			xslTransformationService = new XslTransformerConnector()
					.getXslTransformerService();

			if (xslTransformationService == null)
				throw new RuntimeException("no xslTransformationService");

			// xslTransformationService.setControler(this);

			if (xslTransformationService.isStub()) // is a mock object,
													// presently
				// unusable, for further
				// implementation
				if (LOG) {
					clog("xslTransformationService is Stub");
					// String willDo =
					// xslTransformationService.exposeContract();
					// clog("should do: " + willDo);
				}
			if (!xslTransformationService.isQualified()) // is not a mock object
				// but not usable
				if (LOG)
					clog("xslTransformationService is Not Qualified");
		}
		return xslTransformationService;
	}

	@Override
	public IMegamodelService getMegamodelService() {
		if (megamodelService == null) {
			megamodelService = new MegamodelConnector().getMegamodelService();
			if (megamodelService == null)
				throw new RuntimeException("no megamodelService");
			if (megamodelService.isStub()) // is a mock object, presently
											// unusable, for
											// furtherimplementation
				if (LOG)
					clog("megamodelService is Stub");
			if (!megamodelService.isQualified()) // is not a mock object but not
													// usable
				if (LOG)
					clog("megamodelService is Not Qualified");
		}
		megamodelService.setControler(this);
		return megamodelService;
	}

	public IMetamodelRetriever getMetamodelRetriever() {
		if (metamodelRetriever == null) {
			metamodelRetriever = new MetamodelRetrieverConnector()
					.getMetamodelRetriever();

			if (metamodelRetriever == null)
				throw new RuntimeException("no metamodelRetriever_");

			if (metamodelRetriever.isStub()) // is a mock object, presently
												// unusable, for further
												// implementation
				if (LOG) {
					clog("metamodelRetriever_ is Stub");
				}
			if (!metamodelRetriever.isQualified()) // is not a mock object
				// but not usable
				if (LOG)
					clog("metamodelRetriever_ is Not Qualified");
		}

		metamodelRetriever.setControler(this);
		return metamodelRetriever;
	}

	@Override
	public void setLayoutActionDelegate(IActionDelegate layoutAction) {
		// not used this.layoutActionDelegate = layoutAction;
	}

	@Override
	public IDiagraphGen getDiagraphGenerator() {
		/*
		 * if (diagraphGenerator == null) { //WorkbenchUtils.showNavigator();
		 * diagraphGenerator = componentConnector
		 * .getDiagraphGeneratorService();
		 * //diagraphGenerator.setEcoreUtilService(ecoreUtilService);
		 * diagraphGenerator.setNotifiable(this); }
		 */
		return diagraphGenerator;
	}

	@Override
	public EObject getCurrentEObject() { // FP130606
		return currentEObject;
	}

	/*
	 *
	 * void f(){ // hide the launch menu if it is empty String path =
	 * IWorkbenchActionConstants.M_WINDOW + IWorkbenchActionConstants.SEP +
	 * IWorkbenchActionConstants.M_LAUNCH; IMenuManager manager =
	 * getMenuBarManager().findMenuUsingPath(path); IContributionItem item =
	 * getMenuBarManager().findUsingPath(path);
	 *
	 * // TODO remove: updateActiveActions(); IActionSet actionSets[] =
	 * actionPresentation.getActionSets(); registerActionSets(actionSets);
	 *
	 * if (manager == null || item == null) return;
	 * item.setVisible(manager.getItems().length >= 2); // there is a separator
	 * for the additions group thus >= 2 }
	 */

	private void setMenubarVisible(boolean visible) {
		IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		IContributionItem[] items = ((WorkbenchWindow) workbenchWindow)
				.getMenuBarManager().getItems();
		for (IContributionItem item : items) {
			item.setVisible(visible);
			clog(item.getId());
		}
		((WorkbenchWindow) workbenchWindow).getMenuBarManager().setVisible(
				visible);
	}

	public String getToolbarLabel(String actionSetId) {
		ActionSetRegistry registry = WorkbenchPlugin.getDefault()
				.getActionSetRegistry();
		IActionSetDescriptor actionSet = registry.findActionSet(actionSetId);

		if (actionSet != null) {
			return actionSet.getLabel();
		}

		if (IWorkbenchActionConstants.TOOLBAR_FILE
				.equalsIgnoreCase(actionSetId)) {
			return WorkbenchMessages.WorkbenchWindow_FileToolbar;
		}

		if (IWorkbenchActionConstants.TOOLBAR_NAVIGATE
				.equalsIgnoreCase(actionSetId)) {
			return WorkbenchMessages.WorkbenchWindow_NavigateToolbar;
		}

		return null;
	}

	// FP140516 voir
	public void handleActionSet(IPerspectiveDescriptor perspectiveDescriptor) {
		if (perspectiveDescriptor.getId().indexOf(
				"org.isoe.diagraph.workbench.perspectives.DiagraphPerspective") > -1) {
			IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow();
			if (workbenchWindow.getActivePage() instanceof WorkbenchPage) {
				WorkbenchPage worbenchPage = (WorkbenchPage) workbenchWindow
						.getActivePage();

				/*
				 * Perspective perspective =
				 * worbenchPage.findPerspective(perspectiveDescriptor);
				 * ArrayList<IActionSetDescriptor> toRemove = new
				 * ArrayList<IActionSetDescriptor>(); if (perspective != null) {
				 * for (IActionSetDescriptor actionSetDescriptor :
				 * perspective.getAlwaysOnActionSets()) { if
				 * (actionSetDescriptor
				 * .getId().indexOf("org.isoe.fwk.runtime.diagraph.actions") >
				 * -1) { toRemove.add(actionSetDescriptor); } }
				 * perspective.turnOffActionSets((IActionSetDescriptor[])
				 * toRemove.toArray(new IActionSetDescriptor[toRemove.size()]));
				 * }
				 */

			}
		}
	}

	/*
	 *
	 * @Override public void perspectiveActivated(IWorkbenchPage page,
	 * IPerspectiveDescriptor perspectiveDescriptor) {
	 * handleActionSet(perspectiveDescriptor); }
	 *
	 * @Override public void perspectiveChanged(IWorkbenchPage page,
	 * IPerspectiveDescriptor perspectiveDescriptor, String changeId) {
	 * handleActionSet(perspectiveDescriptor); }
	 */

	/*------------------------------------------------------*/

	@Override
	public String getPathUnderModel(String filename, String extension) {
		try {
			String thisloc = currentPackage.eResource().getURI().toString();
			thisloc = thisloc.substring(0, thisloc.lastIndexOf("model/"));
			thisloc += "model/" + filename + "." + extension;
			URI ruri = URI.createURI(thisloc);
			return CommonPlugin.resolve(ruri).toFileString();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public EModelElement getCurrentElement() {
		return currentElement;
	}

	@Override
	public IGraphicalEditPart getCurrentGraphicalEditPart() {
		return currentGraphicalEditPart;
	}

	@Override
	public List<IProject> getDsmlProjects() {
		List<IProject> dsmlProjects = new ArrayList<IProject>();
		List<IProject> prjs = getOpenedProjects();
		try {
			for (IProject iProject : prjs)
				if (getModel(iProject, "ecore") != null)
					dsmlProjects.add(iProject);

		} catch (CoreException e) {
			e.printStackTrace();
		}
		return dsmlProjects;
	}

	protected List<IProject> getOpenedProjects() {
		List<IProject> prjs = new ArrayList<IProject>();
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot wsroot = workspace.getRoot();
		IProject[] projects = wsroot.getProjects();
		for (IProject project : projects)
			if (project.isOpen())
				prjs.add(project);
		return prjs;
	}

	protected IFile getModel(IProject project, String extension)
			throws CoreException {
		IFolder fo = project.getFolder(MODEL_FOLDER);
		if (fo != null) {
			URI ri = URI.createURI("platform:/resource"
					+ fo.getFullPath().toPortableString());
			File modelfoldr = new File(CommonPlugin.resolve(ri).toFileString());
			if (modelfoldr.exists())
				if (modelfoldr.isDirectory())
					for (File f : modelfoldr.listFiles())
						if (f.getName().endsWith(extension))
							return fo.getFile(f.getName());
		}
		return null;
	}

	@Override
	public void refreshNewPovAndLayoutAndSave() { // FP130511
		String povViewName = isDev() ? pointOfViewNameText.getText()
				: textNewPov.getText();
		if (Arrays.asList(getViews()).contains(povViewName))
			showMessage("Point of View " + povViewName + " allready exists ");
		else {
			int i = 0;
			String[] newlayers = new String[getViews().length + 1];
			for (String oldLayer : getViews())
				newlayers[i++] = oldLayer;
			newlayers[i] = povViewName;
			setViews(this, 3, langageName, newlayers, povViewName); // FP140611
			executeTimedLayoutAndSave(this, 20, false,
					"refreshNewPovAndLayoutAndSave");
		}
	}

	@Override
	public Object getGrammarGeneratorService() {
		if (grammarGeneratorService == null)
			grammarGeneratorService = componentConnector
					.getGrammarGeneratorService(); // and
													// so
													// on
		return grammarGeneratorService;
	}

	// public void layoutSaveAndRefresh(IWorkbenchWindowActionDelegate
	// diagraphWorkbenchAction) {
	// FP140211

	@Override
	public void layoutSaveAndRefresh() {
		final IActionDelegate delegate = new IActionDelegate() {
			@Override
			public void run(IAction action) {
				IActionDelegate actionDelegate = WorkbenchUtils
						.getAction2("org.eclipse.ui.actionSets",
								"org.isoe.fwk.runtime.diagraph.layout.action.LayoutAction");
				if (actionDelegate != null) { // FP131124
					try {
						actionDelegate
								.run(new Action(
										"org.isoe.fwk.runtime.diagraph.layout.action.LayoutAction") {
								});// FP120522b // FP120510
					} catch (Exception e) {
						clog("???? layout ????");
					}

				}
			}

			public void selectionChanged(IAction action, ISelection selection) {
			}
		};
		delegate.run(null);

	}

	@Override
	public String getPovCandidate(EModelElement eModelElement) {
		if (eModelElement instanceof EClass) {
			// FP131126xx
			return grammarGeneratorService
					.getPovCandidate((EClass) eModelElement);

		} else
			return "unknown";
	}

	@Override
	public void generateNewPov(EModelElement eModelElement, boolean auto) {
		if (eModelElement instanceof EClass) {
			// FP131126xx
			generatePointOfView((EClass) eModelElement,
					((EClass) eModelElement).getName(), auto);
		} else
			cerror("not a EClass " + eModelElement.toString());
	}

	public EClass addManualView(EClass povclass) {
		EClass curntClass = (EClass) getCurrentElement();
		String curntView = getCurrentView(41, povclass.getEPackage().getName(),
				"______SB_addManualView"); // FP140611
		String newView = ViewConstants.DIAGRAPH_DEFAULT;
		int newId = 0;
		String newpov = getNewPov();
		if (!newpov.isEmpty() && !newpov.equals(ViewConstants.DIAGRAPH_DEFAULT)) {
			newView = VIEW_PREFIX + getNewPov(); // "view=" + //FP131126
			newId = getNewPovId();
		}
		try {
			if (!diagraphRuntimeFactory_.addNewPovRoot(curntView, newView,
					newId, curntClass, povclass))
				showMessage("addManualView failed");
		} catch (Exception e) {
			showMessage(e.toString());
		}
		return curntClass;

	}

	/*
	 * private boolean addDiagraphNode(String view, String eClassName) { return
	 * grammarGeneratorService.addDiagraphNode(view, eClassName); //FP140117 }
	 */

	private EClass addAutoView() { // FP140116
		try {
			String newpov = getNewPov();
			if (!newpov.isEmpty()
					&& !newpov.equals(ViewConstants.DIAGRAPH_DEFAULT))
				return grammarGeneratorService.addView();
		} catch (Exception e) {
			showMessage("addAutoView " + e.toString());
		}
		return null;
	}


	private boolean containsView(String view) {
		/*
		 * String vn = ""; List<DGraph> cds = getCurrentDiagraphs(); for (DGraph
		 * dGraph : cds) { vn= dGraph.getViewName(); }
		 */
		String[] vius = getViews();
		if (vius != null)
			for (String viu : vius)
				if (viu.equals(view))
					return true;
		return false;
	}

	private void addView(String view) { // FP140211
		int i = 0;
		String[] oldlayers = getViews();
		if (oldlayers == null)
			return;
		String[] newlayers = new String[oldlayers.length + 1];
		for (String oldLayer : oldlayers)
			newlayers[i++] = oldLayer;
		newlayers[i] = view;
		setViews(this, 3, langageName, newlayers, view); // FP140611
	}

	@Override
	public void refreshLayoutAndSave(String view, boolean redrop,
			boolean handleListeners) { // FP140117
		if (!readyToWork) {
			if (UI_LOG)
				clogui("no refresh while not ready to work");
			return;
		}
		if (handleListeners)
			removeListeners_();
		if (!containsView(view))
			addView(view);
		executeLayoutAndSave(redrop);
		if (handleListeners)
			restoreListeners(false); // FP140522
	}

	@Override
	public Diagram getEcoreDiagram() {
		EcoreDiagramEditor ecoreDiagramEditor = diagraphGenerator
				.findActiveEcoreEditor("");
		if (ecoreDiagramEditor == null) {
			Diagram d = getCurrentDiagram();
			if (d instanceof EcoreDiagramEditor)
				ecoreDiagramEditor = (EcoreDiagramEditor) getCurrentDiagram();
		}
		if (ecoreDiagramEditor != null)
			return ecoreDiagramEditor.getDiagram();
		return null;
	}

	@Override
	public boolean[] invokeDiagraphAction(String actionid) { // manual called
																// from menu:
																// DiagraphWorkbenchAction
		boolean[] result = diagraphRuntimeFactory_
				.invokeDiagraphAction(actionid);
		if ((result[0] && result[1])) // handled && must refresh
			layoutSaveAndRefresh();
		return result;
	}

	@Override
	public EcoreDiagramEditor isDiagramOpen(String apath) {
		return diagraphRuntimeFactory_.isDiagramOpen(apath + ".ecorediag");
	}

	@Override
	public void wipeActions(IDiagraphActionWiper wiper, IWorkbenchPage page) {
		this.actionWiper = wiper;
		if (page != null)
			pagestowipe.add(page);
	}

	@Override
	public void openDiagram(EcoreDiagramEditor ecoreDiagramEditor, String apath) {
		diagraphRuntimeFactory_.openDiagram(ecoreDiagramEditor, apath);
	}

	@Override
	public void unknownEdge(Edge edg) {
		try {
			lblCurrentPart.setText("UCPT3**** unbknown Edge "
					+ edg.getElement().getClass().getName());
		} catch (Exception e) {
			lblCurrentPart.setText("UCPT4**** unbknown Edge " + edg.toString());
			View v = (View) edg.getStyles().get(0);
			EObject contnr = v.eContainer();
			lblCurrentPart.setText("kk" + contnr.getClass().getName());
		}

	}

	@Override
	public void unknownModel(Object model) {
		if (model instanceof Diagram) {
			Diagram d = (Diagram) model;
			if (d instanceof EcoreDiagram) {
				EPackage p = (EPackage) d.getElement();
				lblCurrentPart.setText("diagram " + p.getName());
			}
		} else
			lblCurrentPart.setText("UCPT6**** unbknown Model "
					+ model.toString());

	}

	private void setCurrentGenericDiagram_(Diagram diagram) {
		// currentDiagram_ = diagram;

		context.setCurrentDiagram(diagram);
		refreshState();
		try {
			EObject rootobj = diagram.getElement();
			URI uri = rootobj.eResource().getURI();
			EPackage pakage = getMetamodelRetriever().findRegisteredMetamodel(
					uri, new NullProgressMonitor());
			if (pakage != null) {
				currentPackage = pakage;
				currentEObject = diagram.getElement();// FP130520zz
				if (LOG)
					logEObject(currentEObject);
				setLanguageName(pakage.getName());
				langageNsUri = pakage.getNsURI();
				langageNsPrefix = pakage.getNsPrefix();
				txtLanguageName.setText(langageName);
				lblArtefactName.setText(langageName + getPovSuffix());
				txtLanguageNsUri.setText(langageNsUri);
				txtLanguageNsPrefix.setText(langageNsPrefix);
				txtPutToLwb.setText(langageNsUri);
				textLangageVersion.setText("");
				if (LOG)
					clog("*******Generic diag **************** " + langageName);
			} else if (LOG)
				clog("*******Generic diag **************** " + "not found for "
						+ diagram.getElement().toString());

		} catch (Exception e) {
			cerror("******error***** setCurrentGenericDiagram ***"
					+ e.toString());
		}
	}

	/************ ----------------- ****************/

	private void clearPovCombos() {
		if (LOG5)
			clog5("clearPovCombos: " + comboPov_.getText());
		if (comboPov_.getText() != null && !comboPov_.getText().isEmpty()) {
			comboPov_.setText("");
			textNewPov.setText(""); // reset
		}
	}

	@Override
	public void setPointOfViewCandidates(List<String> povCandidates) {
		if (LOG5) {
			String log = "";
			for (String povCandidate : povCandidates)
				log += povCandidate + ";";
			clog5("setPointOfViewCandidates: " + log);
		}
		pointOfViewComboDev.removeAll();
		for (String povCandidate : povCandidates)
			pointOfViewComboDev.add(povCandidate);
		comboPov_.removeAll();
		for (String povCandidate : povCandidates)
			comboPov_.add(povCandidate);
		if (povCandidates.isEmpty()) {
			pointOfViewComboDev.setText("no point of view !!!");
			comboPov_.setText("no possible pov");
			if (LOG5)
				clog5("no possible pov");
			if (isDev())
				timedClear( 100, pointOfViewComboDev);
			else
				timedClear( 100, comboPov_);// FP140526

		}
	}

	// blazo
	@Override
	public EClass getAddedPov() {
		if (isDev()) {
			if (pointOfViewComboDev.getSelectionIndex() != -1)
				return grammarGeneratorService.getViewRoot(pointOfViewComboDev
						.getText());
		} else {
			if (comboPov_.getSelectionIndex() != -1) {
				if (LOG5)
					clog5("getAddedPov: " + comboPov_.getText());
				return grammarGeneratorService.getViewRoot(comboPov_.getText());
			}
		}
		return null;
	}

	@Override
	public String getNewPov() {
		if (LOG5)
			clog5("newPov: " + comboPov_.getText().toLowerCase());
		return comboPov_.getText().toLowerCase();
	}

	@Override
	public int getNewPovId() {
		if (LOG5)
			clog5("newPovId: " + comboPov_.getSelectionIndex());
		return comboPov_.getSelectionIndex();// FP131126////+1;
	}

	private void generateSaveAllAndGeneratePovCandidates() {
		if (LOG5)
			clog5("generateSaveAllAndGeneratePovCandidates()");
		saveAllEditors();
		grammarGeneratorService.generatePointOfViewCandidates(this);
	}

	private void selectPov() {
		// generatePovCandidates();
		if (LOG5)
			clog5("selectPov()--");
		String txt = comboPov_.getText();
		if (txt != null && !txt.isEmpty()) {
			String proposedViewName = VIEW_PREFIX + txt.toLowerCase();
			pointOfViewNameText.setText(proposedViewName);
			textNewPov.setText(proposedViewName);
			lblPov.setText(proposedViewName);
			// lblPovId_.setText(Integer.toString(comboPov_.getSelectionIndex()
			// + 1));
			newPov = proposedViewName;
			newPovId = comboPov_.getSelectionIndex() + 1;

			if (LOG5) {
				clog5("proposedViewName=" + proposedViewName);
				clog5("newPovId=" + newPovId);
			}

		} else {
			;// generatePovCandidates();
		}
	}

	private void selectPov(String povclaz) {
		if (LOG5)
			clog5("selectPov():" + povclaz);
		// if (comboPov.getItems().length==0)
		generateSaveAllAndGeneratePovCandidates();
		int i = 0;
		for (String it : comboPov_.getItems())
			if (it.equals(povclaz))
				break;
			else
				i++;
		comboPov_.setItem(i, povclaz);
		comboPov_.select(i);
		pointOfViewNameText.setText(VIEW_PREFIX + povclaz.toLowerCase());
		textNewPov.setText(VIEW_PREFIX + povclaz.toLowerCase());
		if (LOG5)
			clog5("newpov=:" + VIEW_PREFIX + povclaz.toLowerCase());
	}

	protected void generatePointOfView(EClass povclass, String povclaz,
			boolean auto) { // FP130511
		if (LOG5)
			clog5("generatePointOfView:" + povclaz);
		// EClass povclass = null;
		if (povclaz == null && comboPov_.getItems().length == 0) {
			generateSaveAllAndGeneratePovCandidates();
			showMessage("choose a view ... ");
		} else {
			if (povclaz != null)
				selectPov(povclaz);
			if (!Arrays.asList(getViews()).contains(getViewName())) {
				if (auto)
					povclass = addAutoView();// grammarGeneratorService.addView(povclass);
				else {
					povclass = addManualView(null); // TODO, take in controller
				}
				grammarGeneratorService.verifyAfterPov(povclass);
				// if (getAddedPov()!=null)
				refreshNewPovAndLayoutAndSave();
			} else
				showMessage("Point of View " + getViewName()
						+ " allready exists ");
		}
	}

	/*--------------------------------*/

	@Override
	public void firstLayout() {
		if (!is_headless && !layoutDone) {
			layoutDone = true;
			if (LOG)
				cinfo("firstLayout");
			executeTimedLayoutAndSave(this, 500, true, "firstLayout"); // FP150322
																		// delay//
																		// FP140527
			// IViewPart probs =
			// findAndShowView("org.eclipse.ui.view.ProblemView"); //FP140603
			// FP140603
		}
	}

	@Override
	public void logLayout(Object layoutAction) {
		layoutDone = true;
	}

	protected IViewPart findAndShowView(String vid) {
		IViewPart result = null;
		try { // FP130627
			result = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().findView(vid);
			PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().showView(vid);
			if (result == null) // FP131206
				result = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage().findView(vid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private void clearCurrentEObject() {
		if (LOG5)
			clog5("clearCurrentEObject");
		currentEObject = null;
		lblEclass.setText("");
	}

	private void setCurrentEClass(EClass eclaz, String currentPartlabel) {
		if (LOG5)
			clog5("setCurrentEClass label="
					+ (currentPartlabel == null ? "pbclass" : currentPartlabel));
		currentEObject = eclaz;
		lblEclass.setText(eclaz.getName());
		lblCurrentPart
				.setText(currentPartlabel == null ? "" : currentPartlabel);
	}

	private void setCurrentEModelElement(EModelElement me,
			IGraphicalEditPart currentpart, String currentPartlabel) {
		if (LOG5)
			clog5("setCurrentEModelElement "
					+ ((me instanceof ENamedElement) ? ((ENamedElement) me)
							.getName() : me.toString()) + "" + "label="
					+ currentPartlabel);
		currentElement = me;
		currentGraphicalEditPart = currentpart;
		lblCurrentPart.setText(currentPartlabel);
		pushEModelElement(me);
		if (LOG5) {
			String st = "";
			for (EModelElement mel : elementStack)
				if (mel instanceof ENamedElement)
					st += ((ENamedElement) mel).getName() + ";";
			clog5(st);
		}
	}

	@Override
	public void setCurrent(int i, EModelElement modelElement_, EClass claz,
			EditPart editPart_, String label) {
		if (LOG5)
			clog5("setCurrent " + i + " claz=" + claz.getName() + " label="
					+ label);
		setCurrentEClass(claz, claz.getName());
		setCurrentEModelElement(modelElement_, (editPart_ == null ? null
				: (IGraphicalEditPart) editPart_), label); // FP131115
		if (i == 1)
			clearPovCombos();
		selectedClass = claz;
	}

	private String getPovSuffix() {
		String result = ((currentPov == null || currentPov.isEmpty() || currentPov
				.equals(ViewConstants.DIAGRAPH_DEFAULT)) ? ("." + ViewConstants.DIAGRAPH_DEFAULT)
				: ("." + currentPov));
		if (LOG5)
			clog5("PovSuffix=" + result);
		return result;
	}

	private boolean setCurrentModel(String modelName) {
		if (megamodelManager.filterModelsInRepository(modelName)) {
			if (LOG5)
				clog5("setCurrentModel=" + modelName);
			refreshModelCombo(modelName);
			txtModelName.setText(modelName); // FP130513
			lblModelunderdesign.setText(modelName);
			lblArtefactName.setText(modelName);
			currentModel = modelName;
			return true;
		}
		if (LOG5)
			clog5("setCurrentModel=" + "false");
		return false;
	}

	/************************************/

	@Override
	public EPackage getEPackage() { // FP131225
		EPackage result = activeEditorTracker.getActiveEPackage();
		if (LOG5)
			clog5("getEPackage()=" + result == null ? "null" : result.getName());
		return result;
	}

	private Diagram getEcoreDiag() {// TODO use activeeditortracker
		if (LOG5)
			clog5("getEcoreDiag()");
		Diagram diagram = getCurrentDiagram();
		boolean is = diagram != null
				&& EPackageEditPart.MODEL_ID.equals(diagram.getType());
		if (LOG5)
			clog5("getEcoreDiag()" + (is ? "true" : "false"));
		return is ? diagram : null;
	}

	@Override
	public Diagram getCurrentDiagram() {
		if (LOG5)
			clog5("getCurrentDiagram()");
		IEditorPart editorPart = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if (editorPart instanceof DiagramEditor) {
			if (LOG5)
				clog5("getCurrentDiagram():" + editorPart.getTitle());
			host = ((DiagramEditor) editorPart).getDiagramEditPart();
			return ((View) host.getModel()).getDiagram();
		}
		return null;
	}

	@Override
	public void setActiveEditor(IEditorPart editor) {
		// if (LOG5)
		// clog5("setActiveEditor: "+editor.getTitle());
		/*
		 * EcoreDiagramEditor ecoreDiagramEditor=((EcoreDiagramEditor) editor);
		 * IEditorInput editorInput=ecoreDiagramEditor.getEditorInput();
		 * IProject prj=activeEditorTracker_.getLastActiveEditorProject();
		 * setCurrentLanguageDiagram(((EcoreDiagramEditor)
		 * editor).getDiagram()); //FP140527zz
		 */

	}

	private boolean diagramChanged(DiagramEditor part) {
		Diagram diagram = part.getDiagram();
		if (LOG5)
			clog5("diagramChanged: " + part.getTitle());
		// if (diagram != null && diagram != currentDiagram) {
		if (diagram != null && diagram != context.getCurrentDiagram()) {
			if (languageConfiguration && !modelConfiguration) {
				if (isUnderProject((EcoreDiagramEditor) part)) {
					EPackage pack = getModel(((EcoreDiagramEditor) part)
							.getDiagram());
					if (pack != null) {

						// toMergeDiagram_ = diagram;
						context.setMergeDiagram(diagram);
						setCurrentEcoreDiagram(part, diagram);
						return true;
					}
				} else {
					setCurrentEcoreDiagram(null, null);
					return false;
				}
			} else {
				EObject rootobj = diagram.getElement();
				if (rootobj != null && rootobj != prevObj) {
					prevObj = rootobj;
					try {
						setCurrentGenericDiagram_(diagram);
					} catch (Exception e) {
						cerror("error in diagramChanged_");
					}
					return true;
				}
			}
		}
		return false;
	}

	/************************************/

	private void logs(String packview) {
		if (DiagraphState_LOG && isLanguageConfiguration())
			logCurrents_(packview);
		if (LanguageTransformer_4_LOG)
			clog4("AKW setCurrentLanguageDiagram " + packview); // FP140521ccc
		if (LOG5)
			clog5("AKW setCurrentLanguageDiagram " + packview);
		if (merge_LOG)
			clogmerge("AKW setCurrentLanguageDiagram " + packview
					+ (context.isMerging() ? " merging" : ""));
	}

	@Override
	// FP140626modified
	public boolean setCurrentView(Diagram diagram, String view) {// FP130814
		EPackage pak = (EPackage) diagram.getElement();
		String vw = pak.getName() + "." + view;
		if ((lastView == null || !lastView.equals(vw))) {

			lastView = vw;
			if (merge_LOG)
				clogmerge("\n\nsetCurrentView " + vw
						+ (context.isMerging() ? " merging" : " not merging"));

			if (context.isMerging() && !isMerging())
				context.endMerge();

			logs(view);
			boolean seted = setupLanguage(pak, diagram, view);
			if (!seted)
				return false;

			context.setMergeDiagram(diagram);
			if (!isMerging()) {
				context.setCurrentDiagram(diagram);
			}

			if (merge_LOG) {
				clogmerge("--MergeDiagram= "
						+ ((EPackage) context.getMergeDiagram().getElement())
								.getName());
				clogmerge("--CurrentDiagram= "
						+ ((EPackage) context.getCurrentDiagram().getElement())
								.getName());
				if (context.getMergeDiagraph() != null
						&& context.getCurrentDiagraph() != null) {
					clogmerge("**MergeDiagraph= "
							+ context.getMergeDiagraph().getFacade1()
									.getLanguageName());
					clogmerge("**CurrentDiagraph= "
							+ context.getCurrentDiagraph().getFacade1()
									.getLanguageName());
				}
			}

			text_1.setText(pak.getName());
			lblPov.setText(view);
			currentEObject = null;
			currentElement = null;
			elemenQueue = new Queue<EModelElement>(100, false);
			elementStack = null;
			currentGraphicalEditPart = null;
			lblCurrentPart.setText("");
			if (ecoreSelection != null
					&& ecoreSelection instanceof StructuredSelection) {
				StructuredSelection ssel = (StructuredSelection) ecoreSelection;
				Object fel = ssel.getFirstElement();
				if (fel instanceof IGraphicalEditPart) {
					EClass currentclass = diagraphAdapter
							.updateCurrentPart((IGraphicalEditPart) fel);
					if (currentclass != null)
						checkLanguage();
				}
			}
			refreshState();
			if (!layoutDone) {
				firstLayout();
				refreshState();
			}
		} else
			return true;

		return true;
	}

	// @Override //FP140626modified
	public boolean setCurrentView_old_nu(Diagram diagram, String view) {// FP130814

		EPackage pak = (EPackage) diagram.getElement();
		String vw = pak.getName() + "." + view;
		if ((lastView == null || !lastView.equals(vw))) {

			lastView = vw;
			if (merge_LOG)
				clogmerge("\n\nsetCurrentView " + vw
						+ (context.isMerging() ? " merging" : " not merging"));

			if (context.isMerging() && !isMerging())
				context.endMerge();

			logs(view);
			boolean seted = setupLanguage(pak, diagram, view);
			if (!seted)
				return false;

			context.setCurrentDiagram(diagram);
			if (!context.isMerging()) {
				context.setMergeDiagram(diagram);
				// context.setMergeDiagraph(context.getCurrentDiagraph());

			} else {

				// DGraph ex= context.get(vw);
				// if (ex!=null)
				// context.setCurrentDiagraph(ex);

				if (merge_LOG) {
					// clogmerge("__MergeDiagraph= "+context.getMergeDiagraph().getFacade1().getLanguageName());
					// clogmerge("__CurrentDiagraph= "+context.getCurrentDiagraph().getFacade1().getLanguageName());

					clogmerge("--MergeDiagram= "
							+ ((EPackage) context.getMergeDiagram()
									.getElement()).getName());
					clogmerge("--CurrentDiagram= "
							+ ((EPackage) context.getCurrentDiagram()
									.getElement()).getName());
				}

			}

			text_1.setText(pak.getName());
			lblPov.setText(view);
			currentEObject = null;
			currentElement = null;
			elemenQueue = new Queue<EModelElement>(100, false);
			elementStack = null;
			currentGraphicalEditPart = null;
			lblCurrentPart.setText("");
			if (ecoreSelection != null
					&& ecoreSelection instanceof StructuredSelection) {
				StructuredSelection ssel = (StructuredSelection) ecoreSelection;
				Object fel = ssel.getFirstElement();
				if (fel instanceof IGraphicalEditPart) {
					EClass currentclass = diagraphAdapter
							.updateCurrentPart((IGraphicalEditPart) fel);
					if (currentclass != null)
						checkLanguage();
				}
			}
			refreshState();
			if (!layoutDone) {
				firstLayout();
				refreshState();
			}
		} else
			return true;

		return true;
	}

	public boolean setCurrentLanguageDiagram_old_140626_b(Diagram diagram,
			String view, int sender, boolean dummy) {// FP130814
		EPackage pak = (EPackage) diagram.getElement();
		String vw = pak.getName() + "." + view;
		if (lastView == null || !lastView.equals(vw)) {
			lastView = vw;

			if (context.isMerging())
				;
			if (!checkPinToMerge.getSelection())
				context.endMerge();

			logs(view);
			boolean seted = setupLanguage(pak, diagram, view);
			if (!seted)
				return false;
			context.setMergeDiagram(context.getCurrentDiagram());

			context.setCurrentDiagram(diagram);
			text_1.setText(((EPackage) diagram.getElement()).getName());
			lblPov.setText(view);
			currentEObject = null;
			currentElement = null;
			elemenQueue = new Queue<EModelElement>(100, false);
			elementStack = null;
			currentGraphicalEditPart = null;
			lblCurrentPart.setText("");
			if (ecoreSelection != null
					&& ecoreSelection instanceof StructuredSelection) {
				StructuredSelection ssel = (StructuredSelection) ecoreSelection;
				Object fel = ssel.getFirstElement();
				if (fel instanceof IGraphicalEditPart) {
					EClass currentclass = diagraphAdapter
							.updateCurrentPart((IGraphicalEditPart) fel);
					if (currentclass != null)
						checkLanguage();
				}
			}
			refreshState();
			if (!layoutDone) {
				firstLayout();
				refreshState();
			}
		} else
			return true;

		return true;
	}

	public boolean setCurrentLanguageDiagram_old_140626_a(Diagram diagram,
			String view, int sender, boolean dummy) {// FP130814
		EPackage pak = (EPackage) diagram.getElement();

		// if (view.equals(ViewConstants.DIAGRAPH_ALL_VIEW_LITTERAL_))
		// tb = true;

		String vw = pak.getName() + "." + view;

		if (DiagraphState_LOG && isLanguageConfiguration())
			logCurrents_(vw);
		if (LanguageTransformer_4_LOG)
			clog4("AKW setCurrentLanguageDiagram " + vw); // FP140521ccc
		if (LOG5)
			clog5("AKW setCurrentLanguageDiagram " + vw);
		if (merge_LOG && (lastView == null || !lastView.equals(vw)))
			clogmerge("AKW setCurrentLanguageDiagram " + vw
					+ (context.isMerging() ? " merging" : ""));
		lastView = vw;
		boolean seted = setupLanguage(pak, diagram, view);
		if (!seted)
			return false;
		context.setMergeDiagram(context.getCurrentDiagram());
		context.setCurrentDiagram(diagram);
		text_1.setText(((EPackage) diagram.getElement()).getName());
		lblPov.setText(view);
		currentEObject = null;
		currentElement = null;
		elemenQueue = new Queue<EModelElement>(100, false);
		elementStack = null;
		currentGraphicalEditPart = null;
		lblCurrentPart.setText("");
		if (ecoreSelection != null
				&& ecoreSelection instanceof StructuredSelection) {
			StructuredSelection ssel = (StructuredSelection) ecoreSelection;
			Object fel = ssel.getFirstElement();
			if (fel instanceof IGraphicalEditPart) {
				EClass currentclass = diagraphAdapter
						.updateCurrentPart((IGraphicalEditPart) fel);
				if (currentclass != null)
					checkLanguage();
			}
		}
		refreshState();
		if (!layoutDone) {
			firstLayout();
			refreshState();
		}
		return true;
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if (!isLanguageConfiguration())
			return;
		if (SandboxView_LOG_RESOURCECHANGED)
			clog("(2) selectionChanged " + part.getTitle());
		try {
			diagraphAdapter.change(part, selection); // FP140525 // FP130627
		} catch (Exception e) {
			restoreListeners(false);
			cerror("*********error while diagraph event ("
					+ (part == null ? "" : part.getTitle()) + ")"); // FP140410
		}
	}

	/*-------------------------------*/

	private boolean isDifferentLanguage(String languageName) {
		return (languageName != null && !languageName.isEmpty()
				&& getCurrentPackage() == null || !languageName
					.equals(getCurrentPackage().getName()));
	}

	private boolean isCurrentLanguage(String languageName) {
		return languageName != null
				&& languageName.equals(getCurrentPackage() == null ? null
						: getCurrentPackage().getName());
	}

	@Override
	public Display getDisplay() {
		return getShell().getDisplay();
	}

	@Override
	public void saveAllEditors() {
		IWorkbenchPage workbenchPage = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		if (workbenchPage != null)
			workbenchPage.saveAllEditors(false);
	}

	/*
	 * @Override public List<String> getViewsForLanguage_(String language) {
	 * //FP140603 List<String> result = new ArrayList<String>(); if
	 * (currentPackage!=null && currentPackage.getName().equals(language)){ for
	 * (EClassifier claz : currentPackage.getEClassifiers()) { if (claz
	 * instanceof EClass) { List<EAnnotation> anots = ((EClass)
	 * claz).getEAnnotations(); for (EAnnotation anot : anots) if
	 * (anot.getSource().equals( GenericConstants.ANNOT_DIAGRAPH_LITTERAL)) for
	 * (Entry<String, String> entry : anot.getDetails()) if (entry .getKey()
	 * .equals
	 *
	 * (org.isoe.diagraph.lang.DiagraphKeywords.POINT_OF_VIEW))
	 * result.add(getViewName(anot)); } }
	 *
	 * } return result; }
	 */

	private List<String> getViews(String ecoreLocation) {
		URI uri = URI.createPlatformResourceURI(ecoreLocation, true);
		List<String> result = new ArrayList<String>();
		EPackage mainp = (EPackage) loadMetamodel(
				CommonPlugin.resolve(uri).toFileString()).getContents().get(0);
		// List<EClassifier> clsses = mainp.getEClassifiers();
		for (EClassifier claz : mainp.getEClassifiers()) {
			if (claz instanceof EClass) {
				List<EAnnotation> anots = ((EClass) claz).getEAnnotations();
				for (EAnnotation anot : anots)
					if (anot.getSource().equals(
							GenericConstants.ANNOT_DIAGRAPH_LITTERAL))
						for (Entry<String, String> entry : anot.getDetails())
							if (entry
									.getKey()
									.equals

									(org.isoe.diagraph.lang.DiagraphKeywords.POINT_OF_VIEW))
								result.add(getViewName(anot));

			}
		}
		return result;
	}

	/*--------------------------------*/

	@Override
	public void diagraphLanguages(String[] langs) {
		if (LOG)
			clog("diagraphLanguages ");
		boolean deployBuildInPlugin = true;
		boolean deployLocalPlugin = true;
		boolean deployLocalWorkspace = true;
		diagraphMegamodel_(deployBuildInPlugin, deployLocalPlugin,
				deployLocalWorkspace, arg(langs));
	}

	private String arg(String[] args) {
		String arg = "";
		for (int i = 0; i < args.length; i++) {
			arg += args[i];
			if (i < args.length - 1)
				arg += ";";
		}
		return arg;
	}

	@Override
	public Megamodel getMegamodel_(boolean headless) {
		return megamodelMan.getMegamodel(headless);
	}

	@Override
	public Megamodel getMegamodel(String name, boolean recreate) {
		return megamodelMan.getMegamodel(name, recreate);
	}

	private Dsml addDsml_(String projectName, EPackage model) { // keep
		return megamodelMan.addDsml(projectName, model);
	}

	@Override
	public IMegaModelMan getMegamodelMan() {
		return megamodelMan;
	}

	@Override
	public void clearForm(String lang, String view) { // FP130710
		if (LOG5)
			clog5("clearForm " + lang + "." + view);
		textPrototype.setText("");
		String[] v = new String[1];
		currentPov = view; // FP140605
		v[0] = currentPov;
		setViews(this, 6, lang, v, v[0]);// FP140611b

	}

	public void clearForm_old_nu() { // FP130710
		if (LOG6)
			clog6("clearForm");
		textPrototype.setText("");
		String[] v = { ViewConstants.DIAGRAPH_DEFAULT };
		currentPov = ViewConstants.DIAGRAPH_DEFAULT; // FP140527
		setViews(this, 6, langageName, v, v[0]);// FP140611
	}

	// //////////////--------------

	private void configureViewCombos() {
		comboSyntaxLayer.addSelectionListener(new SelectionAdapter() {
			String priorText = null;

			@Override
			public void widgetSelected(SelectionEvent e) {
				priorText = layerEvent(priorText);
			}
		});
	}

	// ////////////--//////////////////////

	@Override
	public void setCurrentEcoreDiagram(DiagramEditor part, Diagram diagram) {// FP130814
		if (LOG5)
			clog5("setCurrentEcoreDiagram");
		if (diagram != null) {
			if (part != null) {
				ecoreDiagramEditor = (EcoreDiagramEditor) part;
				ecoreSelection = ecoreDiagramEditor.getDiagramGraphicalViewer()
						.getSelection();
				if (ecoreSelection instanceof StructuredSelection) { // FP131018
					Object fel = ((StructuredSelection) ecoreSelection)
							.getFirstElement();
					if (LOG5)
						clog5(fel.getClass().getName());
					if (fel instanceof IGraphicalEditPart) {
						EClass currentclass = diagraphAdapter
								.updateCurrentPart((IGraphicalEditPart) fel);
						if (currentclass != null)
							checkLanguage();
					}
				}
			} else
				clearCurrentEObject();
			setCurrentView(diagram,
					getRegisterdView(((EPackage) diagram.getElement())
							.getName())); // FP140606ab
		} else
			clearLanguage();
	}

	@Override
	public boolean setCurrentLanguageDiagram() { // FP131201
		if (LOG5)
			clog5("setCurrentLanguageDiagram()");
		boolean result = false;
		IEditorPart editorPart = activeEditorTracker.getLastActiveEditor();
		if (editorPart instanceof EcoreDiagramEditor) {
			Diagram diagram = ((EcoreDiagramEditor) editorPart).getDiagram();
			EPackage p = (EPackage) diagram.getElement();
			if (LOG5)
				clog5("SLCD diagram: title=" + editorPart.getTitle()
						+ " lang= " + p.getName());
			// result=setCurrentLanguageDiagram(((EcoreDiagramEditor)
			// editorPart).getDiagram(),getCurrentView(), 5,false); //FP140606a
			result = setCurrentView(diagram, getRegisterdView(p.getName())); // FP140606ab
		}
		return result;
	}

	@Override
	public String getCurrentLanguageName() {
		return getCurrentDGraph().getFacade1().getLanguageName();
	}

	public String getCurrentLanguage() {
		String result = null;
		Diagram ecorediag = getEcoreDiag();
		if (ecorediag == null) {
			IProject first = firstProject();
			if (first != null) {
				result = first.getName();
				result = result.substring(result.lastIndexOf(".") + 1);
				return result;
			}
		}
		result = currentPackage == null ? ((EPackage) getEcoreDiag()
				.getElement()).getName() : currentPackage.getName();
		return result;
	}

	@Override
	public String getLanguageUri() {
		try {
			String result = txtPutToLwb.getText();
			return result;
		} catch (Exception e) {
			return "";
		}
	}

	private void invokeLog1(String lang, boolean headless, boolean genLanguage,
			boolean refreshOnly, String[] arguments) {
		String dsls = "";
		Megamodel mmod = megamodelMan.getMegamodel();
		List<Dsml> dsmls_ = mmod.getDsmls();
		for (Dsml dsml : dsmls_)
			dsls += dsml.getName() + "; ";
		String args = "";
		for (String argument : arguments)
			args += argument + "; ";
		if (LOG)
			clog("invokeMegamodelJob language=" + lang + " "
					+ (headless ? "headless " : "")
					+ (genLanguage ? "genLanguage " : "")
					+ (refreshOnly ? "refreshOnly " : "") + " statements= "
					+ args);
		if (JOB_LOG)
			jobclog("invokeMegamodelJob language=" + lang + " "
					+ (headless ? "headless " : "")
					+ (genLanguage ? "genLanguage " : "")
					+ (refreshOnly ? "refreshOnly " : "") + " statements= "
					+ args);

	}

	private void invokeLog(String lang, String view, String[] arguments) {
		String dsls = "";
		Megamodel mmod = megamodelMan.getMegamodel();
		List<Dsml> dsmls_ = mmod.getDsmls();
		for (Dsml dsml : dsmls_)
			dsls += dsml.getName() + "; ";
		if (LOG)
			clog("Invoke MMJ language=" + lang + " view=" + view + " dsmls="
					+ dsls); // FP140622aaa

		if (MegaModelBuilderJOB_LOG)
			jobclog("Invoke MMJ language=" + lang + " view=" + view + " dsmls="
					+ dsls);

		if (LOG)
			clog("invokeMegamodelJob " + " language=" + lang + " view=" + view);
		if (Megamodel_LOG)
			clog("invokeMegamodelJob " + " language=" + lang + " view=" + view);
		if (JOB_LOG) {
			String args = "";
			for (String argument : arguments)
				args += argument + "; ";
			jobclog("invokeMegamodelJob " + " language=" + lang
					+ " currentView=" + view + " args=" + args);
		}
	}

	private void jobclog(String mesg) {
		if (JOB_LOG)
			System.out.println(mesg);
	}

	@Override
	public void generateLanguage(boolean headless, boolean refreshOnly,
			Object sender, String priorLanguage) {
		if (priorLanguage == null)
			this.logGeneration = "";
		if (currentPackage != null && (priorLanguage == null
				|| !priorLanguage.equals(currentPackage.getName()))) {
			boolean can = canGenerate (currentPackage.getName());
			if (building){
		      String mesg= "generate language " + currentPackage.getName();
		      cinfo(mesg);
		      logGeneration(mesg);
		      setCurrentLanguageDiagram();
		      if (!can){
		    	    continueGeneration(mesg);
				    return;
		      }
			}
		}

		boolean genLanguageNo = false; // FP140602see

		Date start = new Date();
		long st = start.getTime();
		if (currentPackage != null) {

			String m = currentPackage.getName() + " generation started "
					+ Long.toString(st / 1000);
			//if (headless && buildLog != null)
			if (building)
				logGeneration(m);
			logList(currentPackage.getName());

			this.logGeneration += m;
			if (SandboxView_TEST_LOG)
				clogTest("Building " + m);
			loggerLabel1.setText("");
			boolean refactor = cbRefactor.getSelection();
			boolean all = !buttonOnlyCurrentView.getSelection();

			checkDiagraphAnnotationsAndSave();// FP140430

			if (all)
				genDiagraphAction.setAllViews();
			else
				genDiagraphAction.setCurrentView();

			genDiagraphAction.setDiagraphGenerator(diagraphGenerator);
			genDiagraphAction.setRcp(DParams.GEN_RCP);// checkBoxRcp.getSelection());
			genDiagraphAction.setRefactor(refactor);
			genDiagraphAction.setHeadless(headless);
			genDiagraphAction.setRefreshOnly(refreshOnly);
			genDiagraphAction.run();

			// Diagram currentDiagram = getEcoreDiagram();
			// if (currentDiagram==null)
			// currentDiagram = getCurrentDiagram();

			// megamodelBuilder.saveRepository(); //FP140618

			Diagram newd_ = getEcoreDiagram(currentPackage.getName());
			if (newd_ == null)
				newd_ = getCurrentDiagram();

			Diagram old_ = diagraphGenerator.getCurrentDiagram();

			String oldview = getRegisterdView(priorLanguage);
			// DGraph cu =
			context.getCurrentDiagraph(newd_, old_, oldview);

			// String curnt =

			context.updateCurrentDiagraph(newd_, old_, oldview);// layerHelper.getCurrentView());

			String curnt = context.getCurrentAsString(priorLanguage + "." + oldview);

			updateLanguageToTransform_(curnt); // FP140612 // FP130613



			doOsemJob(OsemConfiguration.OSEM_M2); // unzip if exist...

			if (DO_MEGAMODEL_CHECK_STATUS_AFTER_GEN_LANGUAGE && !headless) {

				String[] args = new String[2];
				args[0] = IDiagraphAlphabet.MEGAMODEL_CHECK_STATUS;
				args[1] = currentPackage.getName(); // FP140605

				String result = invokeMegamodelJob(null, headless, args,// IDiagraphAlphabet.MEGAMODEL_CHECK_STATUS
																		// +
																		// " "+
																		// currentPackage.getName(),
						genLanguageNo, refreshOnly); // FP130823
			}
		} else
			cerror("current package not set");

		Date end = new Date();
		long delta = end.getTime() - start.getTime();
		String meg = currentPackage == null ? "error: no current language "
				: (currentPackage.getName() + " handled in " + delta / 1000
						+ " seconds (" + "" + ") - ("
						+ Long.toString(end.getTime() / 1000) + ")");

		if (building)
		   languageEndBuild(currentPackage,meg);

		if (LOG)
			clog(meg);
		else if (SandboxView_TEST_LOG)
			clogTest("Building " + meg);
		this.logGeneration += meg + "\n";
	}

	/*
	 * public void doMegamodelJob_old(boolean headless, String[] arguments,
	 * boolean genLanguage, boolean refreshOnly) { // FP131003xx if (LOG)
	 * clog("doMegamodelJob "
	 * +(headless?"headless ":"")+(genLanguage?"genLanguage "
	 * :"")+(refreshOnly?"refreshOnly ":"")+" statements= "+arguments[0] + "-" +
	 * arguments[1]); refreshLanguageRepositoryProject();
	 * sleep(20,"doMegamodelJob 1"); getMegamodel(headless); if
	 * (megamodelMan.getMegamodel() == null) {
	 * cerror("**********  megamodel should not be null !!"); // FP130902
	 * return; } String lang=currentPackage == null ? "" :
	 * currentPackage.getName(); megamodelBuilder.doJob(arguments, lang,
	 *
	 * "",getCurrentView(42,lang,"______SB_old"),-1);//FP140611
	 * refreshLanguageRepositoryProject(); sleep(20,"doMegamodelJob 2"); if
	 * (genLanguage) { generateLanguage(headless, refreshOnly, this,
	 * currentPackage.getName()); } }
	 */






	private void logCurrent_nu(String mesg, EModelElement me) {
		if (LOG)
			clog(mesg + ":" + currentToString(me));
	}

	private void refreshState() {
		if (isLanguageConfiguration()) {
			if (DiagraphState_LOG) {
				String language = getCurrentLanguage();
				logCurrents_(language + "." + getRegisterdView(language));
			}
		}
	}

	private void logCurrents_(String language_view) {
		DiagraphState dg = new DiagraphState(this);
		if (prior != null) {
			previouslog = dg.logDelta(language_view, prior, previouslog);
		} else
			dg.logInitial(language_view);
		prior = dg;
	}

	private void logCurrents_old(String language_view) {
		String log = "";
		logcount++;
		try {
			log += "language_view="
					+ (language_view == null ? "" : language_view);
			log += "\nlangageName.view="
					+ (langageName == null ? "" : langageName + ""
							+ getRegisterdView(langageName));
			// String languag=getLanguag();
			// log+="languag.view="+(languag==null?"":languag+""+getRegisterdView(languag));
			log += "\nselectedClass="
					+ (selectedClass == null ? "" : selectedClass.getName());
			DGraph currentDiagraph = context.getCurrentDiagraph();

			log += "\ncurrentDiagraph.name="
					+ (currentDiagraph == null ? "" : ((EClass) currentDiagraph
							.getPointOfView().getSemanticRole()).getEPackage()
							.getName());
			log += "\ncurrentDiagraph.view="
					+ (currentDiagraph == null ? "" : currentDiagraph
							.getViewName());

			EClass currentRoot = context.getCurrentRoot();
			log += "\ncurrentRoot="
					+ (currentRoot == null ? "" : currentRoot.getName());

			Diagram currentDiagram = context.getCurrentDiagram();
			log += "\ncurrentDiagram="
					+ (currentDiagram == null ? "" : ((EPackage) currentDiagram
							.getElement()).getName());
			if (currentElement instanceof ENamedElement) {
				ENamedElement ne = (ENamedElement) currentElement;
				log += "\ncurrentElement=" + ne.getName();
			} else
				log += "\ncurrentElement=";
			log += "\nlangageName=" + (langageName == null ? "" : langageName);
			log += "\ncurrentPackage="
					+ (currentPackage == null ? "" : currentPackage.getName());
			log += "\nfocusedlanguage="
					+ (focusedlanguage == null ? "" : focusedlanguage);

			log += "\nlastFocusedDiagram="
					+ (lastFocusedDiagram == null ? ""
							: ((EPackage) lastFocusedDiagram.getElement())
									.getName());
			String logk = "";
			Map<String, String> viewmap = layerHelper.getLayerMap();
			Set<String> ks = viewmap.keySet();
			for (String k : ks)
				logk += k + "=" + viewmap.get(k) + " ; ";
			log += "\nviewReg=" + logk;
			String logl = "";
			for (String layer : layers_)
				logl += layer + " ;";
			log += "\nviews=" + logl;
			String settedView = layerHelper.getCurrentView_();
			log += "\nsettedView=" + (settedView == null ? "" : settedView);
			log += "\ncurrentPov=" + (currentPov == null ? "" : currentPov);
			log += "\ncurrentPovId=" + currentPovId;
			log += "\nnewPov=" + (newPov == null ? "" : newPov);
			log += "\nnewPovId=" + newPovId;

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (previouslog == null || !previouslog.equals(log)) {
			clogState(logcount + ">>-------logCurrents-------");
			clogState(log);
			// clog7(logcount+"<<-------logCurrents-------");
			previouslog = log;
		}

	}

	// /////////////************//////////////////

	@Override
	public String getCurrentView(int sender, String langag, String message) { // FP121013
		String result = getRegisterdView(langag);
		if (DiagraphLanguage_LOG)
			clogLang(langag + "." + result + " (" + sender + ")");
		if (DiagraphState_LOG)
			logCurrents_(langag + "." + result);
		if (LOG8)
			clog8("("
					+ (sender < 10 ? " " + sender : "" + sender)
					+ ")CV="
					+ langag
					+ "."
					+ result
					+ " "
					+ ((message.contains(".")) ? message.substring(message
							.lastIndexOf(".") + 1) : message));
		return result;
	}

	@Override
	public String getRegisterdView(String language) {
		return layerHelper.getRegisterdView(language);
	}

	@Override
	public void registerCurrentView(String langName, String view, int sender) {
		setFocusedLanguage(langName);
		layerHelper.registerCurrentView(langName, view, sender);
	}

	private String layerEvent(String priorView) {
		String view = comboSyntaxLayer.getText();
		if (view != null && !view.isEmpty() && !view.equals(priorView)) {
			boolean doit = priorView != null;
			priorView = view;
			if (doit) {
				if (UI_LOG)
					clogui("selectLayer:" + view);
				currentPov = view;
				currentPovId = comboSyntaxLayer.getSelectionIndex() + 1;
				String viewnam = langageName + getPovSuffix();
				lblArtefactName.setText(viewnam);
				registerCurrentView(langageName, view, 100); // FP140606
				if (UI_LOG)
					clogui("ArtefactName=" + viewnam);
				logCurrents_(viewnam);
				EPackage p = (EPackage) getEcoreDiagram().getElement();
				if (p.getName().equals(langageName)) {
					layouting = true;
					setCurrentView(getEcoreDiagram(), view);// FP140626modified
					executeTimedLayoutAndSave(this, 50, false, "selectLayer");
				} else
					cerror("should not happen in layerEvent");
			} else if (UI_LOG)
				clogui("selectLayer: idem");
		}
		return priorView;
	}

	@Override
	public int getViewId(String view) { // FP140605xxx
		getCurrentView(43, getCurrentLanguage(), "______SB_getViewId"); // FP140611
		String[] items = comboSyntaxLayer.getItems();
		for (int i = 0; i < items.length; i++)
			if (items[i].equals(view))
				return i;
		return 0;
	}

	@Override
	public void updateColor(String language, String view) {
		annotationHelper.updateColor(language, view);
	}

	@Override
	public void setLayerItems(String[] views) {
		layers_ = new String[views.length];
		for (int i = 0; i < views.length; i++)
			layers_[i] = views[i];
		comboSyntaxLayer.setItems(layers_);
	}

	@Override
	public String getLayer() {
		return comboSyntaxLayer.getText();
	}

	@Override
	public void setLayer(String view) {
		comboSyntaxLayer.setText(view);
	}

	@Override
	public void setViews(Object sender, int k, String langname,
			String[] viewids, String current) {
		layerHelper.setViews(sender, k, langname, viewids, current);
	}

	public String getCurrentView() {
		return layerHelper.getCurrentView_();
	}

	public Map<String, String> getLayerMap() {
		return layerHelper.getLayerMap();
	}

	/*******
	 * -------------**************----/
	 *
	 *
	 */

	@Override
	public void newParseLanguage_nu() {
		doNewParseLanguage_nu();
	}

	private void doNewParseLanguage_nu() { // FP130611
		EClass currentRoot_ = context.getCurrentRoot();
		// Object[] args_ = new Object[1];
		if (currentRoot_ != null) {
			String mname = currentRoot_.getEPackage().getName();
			// args_[0] = currentRoot;
			boolean headless = true; // FP131207

			// args[1]=getCurrentDiagraph();
			// args[0]=getCurrentDiagraph();

			String result = languageParser.newParse_nu(currentRoot_, headless);
			// String result = languageParser.request(ecoreUtilService,
			// "parse",
			// args);
			if (result == null || result.isEmpty()) {
				result = "wrong parse operation !";
				textDesign.setText(result);
				timedClear( 3000, textDesign);
			} else {
				textDesign.setText(result);
				List<DGraph> cs = languageParser.getConcreteSyntax();
			}
		} else {
			textDesign.setText("select a language !");
			timedClear( 3000, textDesign);
		}
	}

	@Override
	public String getLanguageToMergeName() {
		return getDGraphName_(context.getMergeDiagraph());
	}

	private String getDGraphName_(DGraph dgraph) {
		return dgraph == null ? null : ((EClass) dgraph.getPointOfView()
				.getSemanticRole()).getEPackage().getName();
	}

	private String getDGraphView_(DGraph dgraph) {
		return dgraph == null ? null : dgraph.getViewName();
	}

	@Override
	public String getCurrentRootName() {
		EClass currentRoot_ = context.getCurrentRoot();
		return currentRoot_ == null ? null : currentRoot_.getName();
	}

	private String getMergeRootName_() {
		EClass toMergeRoot = context.getMergeRoot();
		return toMergeRoot == null ? null : toMergeRoot.getName();
	}

	private String updateLanguageToTransform_(String curnt) { // FP140521

		String result = "";
		if (merge_LOG && context.isMerging())
			clogmerge("updateLanguageToTransform "
					+ (context.isMerging() ? "merging" : ""));
		if (!isMerging()) {
			/*
			 * Diagram ex=diagraphGenerator.getCurrentDiagram(); String
			 * v=layerHelper.getCurrentView(); String
			 * curnt=context.updateCurrentDiagraph(diag,ex,v);
			 */

			updateCurrent(curnt);
			result = curnt;
		} else
			context.startMerge(lastView);
		return result;
	}

	private String updateLanguageToTransform_old(Diagram diag_, EPackage pak___) { // FP140521

		String result = "";
		if (merge_LOG && context.isMerging())
			clogmerge("updateLanguageToTransform "
					+ (context.isMerging() ? "merging" : ""));
		if (!checkPinToMerge.getSelection()) {

			Diagram ex = diagraphGenerator.getCurrentDiagram();
			String v = layerHelper.getCurrentView_();

			// if (v.equals("all layers"))
			// tb = true;

			DGraph cu = context.getCurrentDiagraph(diag_, ex, v);

			// String curnt=
			context.updateCurrentDiagraph(diag_, ex, v);
			String curnt = context.getCurrentAsString(pak___.getName() + "."
					+ v);
			updateCurrent(curnt);
			result = curnt;
		} else
			context.startMerge(lastView);
		return result;
	}

	private void updateCurrent(String toMerge) {
		if (toMerge == null || toMerge.isEmpty()) {
			checkPinToMerge.setText("select for merge :");
			lblLanguageToMerge_.setText("");
		} else {
			lblLanguageToMerge_.setText(toMerge);
		}

	}

	@Override
	public void updateAnnotations() {
		if (!readyToWork) { // FP140626
			if (UI_LOG)
				clogui("no updateAnnotations while not ready to work");
			return;
		}
		if (is_headless) {
			checkDiagraphAnnotationsAndSave(); // FP140430
		} else {
			if (REDROP) {
				if (!inhibit)
					timedLayout(this, 10);
				else if (LOG)
					clog("layout inhibited"); // FP140521bbb
			}
		}
	}

	private void clogmerge(String mesg) {
		if (merge_LOG)
			System.out.println(mesg);
	}

	@Override
	public boolean isHeadless() {
		return is_headless;
	}

	@Override
	public void endMerge() {
		checkPinToMerge.setSelection(false);
		checkPinToMerge.setText(("pin current :"));
	}

	@Override
	public void startMerge() {
		checkPinToMerge.setText("merge with :");
	}

	@Override
	public List<String> getViewsForLanguage(String lang) {
		return context.getViewsForLanguage(lang);
	}

	public DGraph getCurrentDiagraph() {
		return context.getCurrentDiagraph();
	}

	@Override
	public DGraph getMergeDGraph() {
		return context.getMergeDiagraph();
	}

	@Override
	public DGraph getCurrentDGraph() {
		return context.getCurrentDiagraph();

	}

	@Override
	public void setSourceDGraph(DGraph sourceDGraph) {
		context.setSourceDiagraph(sourceDGraph);

	}

	@Override
	public void setMergeDGraph(DGraph mergeDGraph) {
		context.setMergeDiagraph(mergeDGraph);

	}

	@Override
	public EClass getCurrentRoot() {
		return context.getCurrentRoot();
	}

	public List<DGraph> getCurrentDiagraphs() {
		return context.getCurrentDiagraphs();
	}

	@Override
	public DGraph getDGraph(String language, String view) {
		List<DGraph> currents = getCurrentDiagraphs();
		for (DGraph dGraph : currents) {
			if (!language.equals(dGraph.getFacade1().getLanguageName()))
				throw new RuntimeException("should not happen in getDGraph "
						+ language + " vs "
						+ dGraph.getFacade1().getLanguageName());
			if (dGraph.getViewName().equals(view))
				return dGraph;
		}
		return null;
	}

	@Override
	public void starts(String cmd) {
		runningCmd = cmd;
	}

	@Override
	public void ends(String cmd) {
		runningCmd = null;
	}

	@Override
	public void setCmdResponse(String text) {
		textDiagraphResponse_.setText(text);
	}

	@Override
	public StyledText getDiagraphScriptText() {
		return textDiagraphCmds_;
	}

	@Override
	public StyledText getDiagraphResponseText() {
		return textDiagraphResponse_;
	}

	@Override
	public String getDiagraphStatements() {
		return textDiagraphStatements.getText();
	}

	@Override
	public void setDiagraphStatements(String statement) {
		textDiagraphStatements.setText(statement);
	}

	@Override
	public void selectDsml(Dsml dsml) {
		// if (diagraphRuntimeFactory != null)
		diagraphRuntimeFactory_.selectDsml(dsml.getName());
		// selectedDsml = dsml;
	}

	@Override
	public void setEcoreDiagramEditor(EcoreDiagramEditor ecoreDiagramEd) {
		megamodelEditor = null;
		ecoreDiagramEditor = ecoreDiagramEd;
		changedDiagram = ecoreDiagramEditor.getDiagram();
		ecoreSelection = ecoreDiagramEditor.getDiagramGraphicalViewer()
				.getSelection();
	}

	private List<IProject> getOpenedProjects_nu(String prefix) {
		List<IProject> result = new ArrayList<IProject>();
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot wsroot = workspace.getRoot();
		IProject[] projects = wsroot.getProjects();
		for (IProject project : projects)
			if (project.isOpen())
				if (project.getName().startsWith(prefix))
					result.add(project);
		return result;
	}

	/*----------------------------*/

	private String getMergeName() {
		return lblLanguageToMerge_.getText();
	}

	@Override
	public boolean prepareMerge(boolean byscript, EClass currentRoot,
			EPackage withPackage) {
		// megamodelMan.clearMegamodel_(); //FP140622
		if (this.clonedPackage_ != null)
			return false;
		// cloneLanguage = null;
		if (MegaModelBuilderJOB_LOG)
			jobclog(" prepareMerge");
		clearForm(currentPackage.getName(), ViewConstants.DIAGRAPH_DEFAULT); // FP140611b
		saveAllEditors();
		if (!checkPinToMerge.getSelection() && !byscript) {
			String mesg = "nothing to merge ....";
			txtCloneDev.setText(mesg);
			txtClone.setText(mesg);
			timedClear( 3000, txtCloneDev);
			timedClear( 3000, txtClone);
		} else {
			// EClass currentRoot = context.getCurrentRoot();
			if (currentRoot == null
					|| (currentRoot.getEPackage().getName().equals(withPackage
							.getName()))) {
				String mesg = "choose another language ....";
				txtCloneDev.setText(mesg);
				txtClone.setText(mesg);
				timedClear( 3000, txtCloneDev);
				timedClear( 3000, txtClone);
				return false;
			} else
				return true;
		}
		return false;
	}

	@Override
	public void finalizeMerge(EPackage merged) {
		context.endMerge();
		finalizeClone(merged);
	}

	@Override
	public void finalizeClone(EPackage cloned) {
		String newname = cloned == null ? null : cloned.getName();
		if (newname == null) {
			newname = "clone error !";
			txtCloneDev.setText(newname);
			txtClone.setText(newname);
			timedClear( 3000, txtCloneDev);
			timedClear( 3000, txtClone);
		} else {
			txtCloneDev.setText(newname);
			txtClone.setText(newname);
			// cloneLanguage = newname;
			// textDiagraphStatements_.setText(""); //FP140623
		}
		/*
		 * Diagram d = new NavigatorHelper(this).bringToTop(newname); if (d ==
		 * null) d = getCurrentDiagram();
		 * updateLanguageToTransform_(d,cloned);// FP130613
		 */
	}

	/*
	 * @Override public void clearMerge() { if (JOB_LOG) jobclog("clearMerge");
	 * txtClone_.setText(""); txtCloneDev.setText(""); cloneLanguage = ""; }
	 *
	 *
	 * @Override public void clearClone_() { if (JOB_LOG) jobclog("clearClone");
	 * txtClone_.setText(""); cloneLanguage = ""; txtCloneDev.setText(""); }
	 */

	/*
	 * private void buildAll(boolean generateEditors) {
	 * interpreter_.buildAll(generateEditors,true); }
	 */

	@Override
	public boolean prepareClone() {
		// megamodelMan.clearMegamodel_(); //FP140622
		if (this.clonedPackage_ != null)
			return false;
		// cloneLanguage = null;
		if (MegaModelBuilderJOB_LOG)
			jobclog(" prepareClone");
		clearForm(currentPackage.getName(), ViewConstants.DIAGRAPH_DEFAULT); // FP140611
		if (checkPinToMerge.getSelection()) {
			String mesg = "clear the merge choice !";
			if (context.isMerging())
				context.endMerge();
			txtCloneDev.setText(mesg);
			txtClone.setText(mesg);
			timedClear( 3000, txtCloneDev);
			timedClear( 3000, txtClone);
			return false;
		} else
			return true;
	}

	@Override
	public boolean prepareBuild(boolean headless) {
		is_headless = headless;
		this.logGeneration = "";
		return true;
	}

	@Override
	public void endBuild(boolean headless) {
		is_headless = headless;
	}

	@Override
	public void clearMegamodel() {
		megamodelMan.clearMegamodel();
	}

	@Override
	public EPackage getClonedPackage() {
		return this.clonedPackage_;
	}

	@Override
	public void setClonedPackage(EPackage ePackage) {
		if (JOB_LOG)
			jobclog("setClonedPackage "
					+ (ePackage == null ? "null" : ePackage.getName()));
		this.clonedPackage_ = ePackage;
	}

	private void setClonelanguage(String cloneLanguage) {
		// this.cloneLanguage = cloneLanguage;
		if (cloneLanguage != null && !cloneLanguage.trim().isEmpty()) {
			txtClone.setText(cloneLanguage);
			txtCloneDev.setText(cloneLanguage);
		}
	}

	/* events */

	private void executeCommandLine() {
		interpreter_.executeCommandLine(textDiagraphStatements.getText()); // FP150305
																			// //
																			// FP141227

		/*
		 * String content = textDiagraphStatements.getText(); if (JOB_LOG)
		 * jobclog("executeCommandLine "+content); String cmd="unknown_command";
		 * if (content.contains(IDiagraphAlphabet.MEGAMODEL_GEN_DSML))
		 * cmd=IDiagraphAlphabet.MEGAMODEL_GEN_DSML; //FP140623 else if
		 * (content.contains(IDiagraphAlphabet.MEGAMODEL_ACTION_CLONE)){
		 * cmd=IDiagraphAlphabet.MEGAMODEL_ACTION_CLONE; //FP140623
		 * this.clonedPackage_ = null; } else if
		 * (content.contains(IDiagraphAlphabet.MEGAMODEL_ACTION_MERGE)){
		 * cmd=IDiagraphAlphabet.MEGAMODEL_ACTION_MERGE; //FP140623
		 * this.clonedPackage_ = null;
		 *
		 * } else if (content.contains(IDiagraphAlphabet.MEGAMODEL_FIND)){
		 * cmd=IDiagraphAlphabet.MEGAMODEL_FIND; //FP140625 } else if
		 * (content.contains(IDiagraphAlphabet.MEGAMODEL_DIAGRAPH)){
		 * cmd=IDiagraphAlphabet.MEGAMODEL_DIAGRAPH; //FP140625 } else if
		 * (content.contains(IDiagraphAlphabet.MEGAMODEL_ALL)){
		 * cmd=IDiagraphAlphabet.MEGAMODEL_ALL; //FP140625 } else if
		 * (content.contains(IDiagraphAlphabet.MEGAMODEL_GEN_ROLES)){
		 * cmd=IDiagraphAlphabet.MEGAMODEL_GEN_ROLES; //FP140623 }
		 *
		 * String result = interpreter_.executeCommandLine(false,cmd,content);
		 * if (!result.equals("ok")) cerror("error "+result+" "+cmd); else
		 * cinfo(">ok");
		 */
	}

	private void mergeLanguages() {
		if (JOB_LOG)
			jobclog("event mergeLanguages");
		this.clonedPackage_ = null;

		boolean headlessNo = false;
		EClass currentRoot = context.getCurrentRoot();
		// interpreter_.mergeLanguagesAndGenGrammar_(currentRoot.getEPackage().getName(),"statechart","montonton2",headlessNo);
		// find initial
		interpreter_.mergeLanguagesAndGenGrammar("", "", "", headlessNo);

		// merge(helloworld,statechart,azert)

	}

	private void cloneLanguage() {
		if (JOB_LOG)
			jobclog("event cloneLanguage");
		this.clonedPackage_ = null;

		interpreter_.cloneLanguageAndGenGrammar(txtClone.getText(), false);
		// clone(helloworld,bonjour);

	}

	/*************************/

	@Override
	public EPackage clone(String cloneLanguage, boolean headless) { // FP140627cmd
		if (MegaModelBuilderJOB_LOG)
			jobclog("Sandbox.clone "
					+ getCurrentDGraph().getFacade1().getLanguageName());
		setClonelanguage(cloneLanguage);
		EPackage result = languageParser.clone(getCurrentDGraph(), headless);
		if (MegaModelBuilderJOB_LOG)
			jobclog("clone result ="
					+ (result == null ? "null" : result.getName()));
		return result;
	}

	@Override
	public EPackage merge(String cloneLanguage, DGraph sourceDGraph,
			DGraph mergeDGraph, boolean headless, boolean byscript) {
		if (MegaModelBuilderJOB_LOG)
			jobclog("Sandbox.merge "
					+ getCurrentDGraph().getFacade1().getLanguageName());
		setClonelanguage(cloneLanguage);
		EPackage result = languageParser.merge(sourceDGraph, mergeDGraph,
				headless, byscript);// getMergeDGraph()
		if (MegaModelBuilderJOB_LOG)
			jobclog("merge result ="
					+ (result == null ? "null" : result.getName()));
		return result;
	}

	@Override
	public void setMergePackage(EPackage mergePackage) {
		this.mergePackage = mergePackage;
	}

	@Override
	public void setMergeDiagram(Diagram mergeDiagram) {
		this.mergeDiagram = mergeDiagram;
	}

	@Override
	public EPackage getMergePackage() {
		return mergePackage;
	}

	@Override
	public Diagram getMergeDiagram() {
		return mergeDiagram;
	}

	@Override
	public void setSourcePackage(EPackage sourcePackage) {
		this.sourcePackage = sourcePackage;

	}

	@Override
	public void setSourceDiagram(Diagram sourceDiagram) {
		this.sourceDiagram = sourceDiagram;
	}

	@Override
	public EPackage getSourcePackage() {
		return sourcePackage;
	}

	@Override
	public Diagram getSourceDiagram() {
		return sourceDiagram;
	}

	@Override
	public boolean mustThrowExceptions() {

		return !layouting & !focusing; //FP150530
		//return true;
	}

	private ConsoleLogger getConsole() {
		if (console == null)
			console = (ConsoleLogger) PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage()
					.findView(GenericConstants.DIAGRAPH_BASIC_CONSOLE_ID);
		return console;
	}

	@Override
	public void logConsole(String mesg) {
		try {
			getConsole().clog(mesg);
		} catch (Exception e) {
			if (!DUPLICATE_LOGCONSOLE)
				System.err.println(mesg);
		}
		if (DUPLICATE_LOGCONSOLE)
			System.out.println(mesg);

	}

	@Override
	public void cerror(String mesg) {

		boolean t = true;
		if (mesg.contains("NullPointerException"))// generation failed
			t = false;
		if (mesg.contains("generation failed"))// generation failed
			t = false;
		if (mesg.contains("error in parseDiagram")){
			buildError(mesg);
			t = false;
		}
		if (mesg.contains("error while parsing"))
			t = false;
		if (mesg.contains("containement for"))
			t = false;
		if (mesg.contains("err while inferContainmentAlt"))
			t = false;
		if (mesg.contains("no containment"))
			t = false;
		if (mesg.contains("race problem"))
			t = false;
		if (mesg.contains("(no target node while infer"))
			t = false;
		if (mesg.contains("error while diagraph event"))
			t = false;
		if (mesg.contains("compartment not created"))
			t = false;
		if (mesg.contains("is not instanciable"))
			t = false;
		if (mesg.contains("validation error"))
			t = false;
		if (mesg.contains("dGraph == null for view"))
			t = false;
		if (mesg.contains("current dgraph is null"))
			t = false;
		if (mesg.contains("error: no current diagraph"))
			t = false;
		if (mesg.contains("does not exist"))
			t = false;
		if (mesg.contains("should not happen"))
			t = false;
		if (mesg.contains("not diagraphed"))
			t = false;
		if (mesg.contains("failed adding compartment mapping"))
			t = false;
		if (mesg.contains("createCompartmentMapping: no compartment"))
			t = false;
		if (mesg.contains("should not happen in parseContainmentAssociationsAbstractTarget"))
			t = false;

		if (mesg.contains("Error while GmfGen validation"))
			t = false;



		if (buildLog_ != null)
			logGeneration((t ? "****** " : "------ ") + mesg);

		logConsole("e_______[" + mesg + "]");
	}


	public String getLogFolder() {//FP150510
		IProject lwbRepositoryProject = getlwbRepositoryProject();
		File megafoldr = new File(CommonPlugin
				.resolve(
						URI.createURI("platform:/resource/"
								+ lwbRepositoryProject.getFullPath()
										.toPortableString())).toFileString());
		File reportfolder = null;
		if (!megafoldr.exists())
			throw new RuntimeException("should not happen in getLogFolder");
		reportfolder = new File(megafoldr + File.separator + "log");
		if (!reportfolder.exists())
				reportfolder.mkdir();
		return reportfolder.getAbsolutePath();
	}







	private void logGeneration(String mesg) {
		if (buildLog_!=null)
		   buildLog_.add(mesg);
	}

	private void logList(String name) {
		languageList+="\""+name+"\",";
	}

	@Override
	public void cinfo(String mesg) {
		;
		logConsole("i_______[" + mesg + "]");

		if (mesg.contains("focus diagraphed:helloworld"))
			cntest++;

	}

	@Override
	public void cwarn(String mesg) {
		logConsole("w_______[" + mesg + "]");
	}

	@Override
	public String[] getDiagraphedProjectLocation(String language) {
		if ((language == null || language.isEmpty())
				|| isCurrentLanguage(language))
			return getProjectLocation(getCurrentPackage());
		else
			for (IProject prj : ResourceUtils.getDiagraphProjects()) {
				if (prj.getName().substring(prj.getName().lastIndexOf(".") + 1)
						.equals(language))
					return ResourceUtils.getProjectLocation(prj);
			}
		return null;
	}

	@Override
	public List<String[]> getAllDiagraphedProjectLocations() {
		List<IProject> allProjects = ResourceUtils.getDiagraphProjects();
		List<String[]> result = new ArrayList<String[]>();
		for (IProject prj : allProjects)
			result.add(ResourceUtils.getProjectLocation(prj));
		return result;
	}

	@Override
	public List<String> getDiagraphProjectNames() { // TODO find a better method
		return ResourceUtils.getDiagraphProjectNames();

	}

	@Override
	public boolean focusNotDiagraphed(EcoreDiagramEditor diagramEditor,
			IProject project, String language, boolean diagramOpened) {
		if (LOG7)
			clog7("AKW focusNotDiagraphed");
		lastFocusedDiagram = null;
		lastOtherProject = null;
		if (!language.equals(lastNotDiagraphedLanguage)) {
			lastNotDiagraphedLanguage = language;
			removeListeners_();
			try {
				Diagram fd = diagramEditor.getDiagram();
				if (fd.getElement() instanceof EPackage) {
					if (LOG7) {
						String log = ((EPackage) fd.getElement()).getName();
						log += " cand lang=" + language;
						log += diagramOpened ? " new opend " : " yet opend";
						clog7("AKW focusNotDiagraphed " + log);
					}
					// bringToTop((EPackage)fd.getElement()); //FP140706
					bringToTop(project);
				}
			} catch (Exception e) {
				cerror("focusNotDiagraphed");
			}
			restoreListeners(false);
			return false;
		}
		return true;
	}

	@Override
	public void focusOther(IProject project) {
		if (LOG7)
			clog7("AKW focusOther");
		lastFocusedDiagram = null;
		lastNotDiagraphedLanguage = null;
		focusedlanguage = null;
		if (project != this.lastOtherProject) {
			this.lastOtherProject = project;
			lblArtefactName.setText("other not diagraphed");
			bringToTop(project);
			cinfo("not a diagraphed project " + project.getName());
		}
		if (!listening_)
			restoreListeners(false);
	}

	@Override
	public boolean focusDiagraph(EcoreDiagramEditor diagramEditor,
			String language, boolean diagramOpened) {
		if (LOG7)
			clog7("AKW focusDiagraph");
		focusing=true;
		lastNotDiagraphedLanguage = null;
		lastOtherProject = null;
		String registerdView = getRegisterdView(language);
		removeListeners_();
		lastFocusedDiagram = diagramEditor.getDiagram();
		if (lastFocusedDiagram.getElement() instanceof EPackage) {
			if (LOG7) {
				String log = ((EPackage) lastFocusedDiagram.getElement())
						.getName();
				log += " lang=" + language;
				log += diagramOpened ? " new opend " : " yet opend";
				clog7("AKW focusDiagraph " + log);
			}
			if (setCurrentView(diagramEditor.getDiagram(), registerdView)) { // FP140606ab
				getActivePage_().bringToTop(diagramEditor);
				restoreListeners(false);
				if (DiagraphState_LOG) // FP140610
					logCurrents_(language + "." + registerdView);
				lastOtherProject = null;
				restoreListeners(false);
				focusing = false;
				return true;
			}
		}
		restoreListeners(false);
		focusing = false;
		return false;
	}

	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		if (isDevMode_()) // FP150311
			return;
		if (SandboxView_LOG_RESOURCECHANGED)
			clog("(3)**resourceChanged**");
		IResourceDelta delta = event.getDelta();
		if (delta != null) {
			IResourceDelta[] ch = delta.getAffectedChildren();
			for (IResourceDelta rDelta : ch) {
				String fp = rDelta.getFullPath().toString();
				if (!priorDeltafFullPath.equals(fp)) {
					if (SandboxView_LOG_RESOURCECHANGED)
						clog("(4)resourceChanged: " + fp);
					if (newLwb && !buildInitialLanguageDone)
						buildInitialLanguage_(stateless);
				}
				priorDeltafFullPath = fp;
			}
		}
	}

	// ///////////////////////

	private void clogListen(String mesg) {
		if (LISTEN_LOG)
			System.out.println(mesg);
	}

	@Override
	public void removeListeners_() {
		// lineStyler.disposeColors(); //FP140601
		// textDiagraphResponse.removeLineStyleListener(lineStyler);
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		getViewSite().getPage().removeSelectionListener(this);
		listens_--;
		listening_ = false;
		if (LISTEN_LOG)
			clogListen("AKW  ---  listeners removed " + listens_ + " listening "
					+ (listening_ ? "true" : "false"));
		cinfo("listeners removed " + listens_ + " listening "
				+ (listening_ ? "true" : "false"));
		if (LISTEN_LOG)
			clogListen("listeners removed " + listens_ + " listening "
					+ (listening_ ? "true" : "false"));
	}

	protected void initServer() {

		if (Automation_LOG)
			clogAutomation("initServer");
		if (SERVER) {
			automationService.initServer();
			if (!automationService.isRunning())
				if (Automation_LOG)
					clogAutomation("no automation server");
		}
	}

	@Override
	public void restoreListeners(boolean raz) {
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this,
				IResourceChangeEvent.POST_CHANGE);
		getViewSite().getPage().addSelectionListener(this);
		if (raz)
			listens_=0;
		else
		    listens_++;
		listening_ = true;
		if (LISTEN_LOG)
			clogListen("AKW  ---  listeners restored " + listens_
					+ " listening " + (listening_ ? "true" : "false"));
		cinfo("listeners restored " + listens_ + " listening "
				+ (listening_ ? "true" : "false"));
		if (LISTEN_LOG)
			clogListen("listeners restored " + listens_ + " listening "
					+ (listening_ ? "true" : "false"));
	}

	private void executeTimedLayoutAndSave(final ViewPart dview,
			final int delay, final boolean redrop, final String sender) {
		if (diagraphGenerator.findActiveEcoreEditor("") != null) {
			// if ("selectLayer".equals(sender))//if (UI_LOG &&
			// "selectLayer".equals(sender))
			cinfo("timed layout "
					+ ("selectLayer".equals(sender) ? "on layer change " : "")
					+ (redrop ? " with redrop" : " without redrop"));

			if (("selectLayer".equals(sender))) { // FP150322 delay
				layoutJob(delay, redrop);

				if (false)
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							try {
								sleep(delay, sender);
								layout(redrop, false); // FP140211
							} catch (Exception e) {
								cerror("(1) error during layout");
							}
							// cinfo("listeners "+listens+" listening "+(listening?"true":"false"));
						}
					});
			}

		} else
			cinfo("timed layout - no active editor");
	}

	private void initView() {
		final IDiagraphControler dc = this;
		final SandboxView dview = this;
		WorkbenchUtils.showNavigator(); // FP140614

		final UIJob initUIJob = new UIJob("Diagraph Initialization") {
			@SuppressWarnings({ "restriction", "deprecation" })
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				int i = 1;
				try {
					dview.sleep(1000, "init");//FP150531

					getAllServices();
					i = 2;
					initialise_(dc);
					i = 3;
					connectGuiListeners(dview);
					i = 4;
					activeEditorTracker = new ActiveEditorTracker(dc);
					i = 5;
					IEditorPart IEditorPart = ActiveEditorTracker
							.getLastActiveEditor();
					i = 6;
					IViewPart view = EclipseUtil
							.findView("org.eclipse.ui.internal.introview");
					if (view != null)
						view.dispose(); // FP130304
					// logConsole("(1)basePath="+basePath);
					i = 7;
					if (basePath != null && basePath.contains(" "))
						showMessage("the installation path should not contain any space character:"
								+ basePath
								+ " ; change this for a proper operation !");
					if (basePath == null)
						cerror("race problem in initview");
					i = 8;
					// checkData();
				} catch (Exception e) {
					showMessage("step: " + i + " ,an error occurred:"
							+ e.toString());
				}
				return Status.OK_STATUS;
			}
		};
		initUIJob.setUser(false);
		initUIJob.schedule(500); // FP150324init

		if (false)
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					try {
						sleep(20, "init");
						getAllServices();
						initialise_(dc);
						connectGuiListeners(dview);
						activeEditorTracker = new ActiveEditorTracker(dc);
						IEditorPart IEditorPart = ActiveEditorTracker
								.getLastActiveEditor();
						IViewPart view = EclipseUtil
								.findView("org.eclipse.ui.internal.introview");
						if (view != null)
							view.dispose(); // FP130304
						// logConsole("(1)basePath="+basePath);
						if (basePath.contains(" "))
							showMessage("the installation path should not contain any space character:"
									+ basePath
									+ " ; change this for a proper operation !");
						// checkData();
					} catch (Exception e) {
						showMessage("an error occurred:" + e.toString());
					}
				}
			});
	}

	public void refresh_old() {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				try {
					sleep(100, "refresh");
					generateSaveAllAndGeneratePovCandidates(); // FP131119
				} catch (Exception e) {
				}
			}
		});
	}

	private void layoutJob(int delay, final boolean redrop) { // FP150323
		final UIJob layoutUIJob = new UIJob("Diagraph Layout") {
			@SuppressWarnings({ "restriction", "deprecation" })
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				layout(redrop, false);
				return Status.OK_STATUS;
			}
		};
		layoutUIJob.setUser(false);
		layoutUIJob.schedule(delay);
	}

	@Override
	public void refresh(int delay) {
		final UIJob refreshUIJob = new UIJob("Diagraph Refresh") {
			@SuppressWarnings({ "restriction", "deprecation" })
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				generateSaveAllAndGeneratePovCandidates();
				return Status.OK_STATUS;
			}
		};
		refreshUIJob.setUser(false);
		refreshUIJob.schedule(delay);
	}

	@Override
	public void retryLayout() {
		layoutJob(1000, true);
	}

	@Override
	public void layout(boolean redrop, boolean handleListeners) {
		try { // FP140216
			String currentview = comboSyntaxLayer.getText();
			if (currentview == null || currentview.isEmpty())
				currentview = ViewConstants.DIAGRAPH_DEFAULT;
			if (UI_LOG)
				clogui("layout=" + currentview + " redrop="
						+ (redrop ? "true" : "false"));
			refreshLayoutAndSave(currentview, redrop, handleListeners); // FP140211
		} catch (Exception e) {
			cerror("while layout " + e.toString());
		}
		cinfo("(1) listeners " + listens_ + " listening "
				+ (listening_ ? "true" : "false"));
		if (LISTEN_LOG)
			clogListen("(1) listeners " + listens_ + " listening "
					+ (listening_ ? "true" : "false"));
		layouting = false;
	}

	private void executeDiagraphActions() {// FP140117xxx called from here
		String[] loc = getProjectLocation(getCurrentPackage());
		if (loc != null) {
			String diagraphactions = readFile(loc[0] + langageName + "."
					+ "cmds", ";");
			if (diagraphactions == null || diagraphactions.isEmpty()) {
				cerror("nothing to do with " + langageName + "." + "cmds");
				return;
			}
			txtDiagraphActions.setText(diagraphactions);
			boolean[] result = diagraphRuntimeFactory_
					.executeDiagraphActions(diagraphactions);
			if (result[0] && result[1])
				refreshLayoutAndSave(
						diagraphRuntimeFactory_.getLastView(diagraphactions),
						false, true);
		}
		cinfo("(3) listeners " + listens_ + " listening "
				+ (listening_ ? "true" : "false"));
		if (LISTEN_LOG)
			clogListen("(3) listeners " + listens_ + " listening "
					+ (listening_ ? "true" : "false"));
	}

	@Override
	public String getArtifact() {
		String result = getCurrentLanguage()
				+ "."
				+ getCurrentView(98, getCurrentLanguage(),
						"______SB_getArtifact"); // FP140611
		if (LOG8)
			clog8("get artifact " + result);
		cinfo("GART " + result);
		cinfo("(2) listeners " + listens_ + " listening "
				+ (listening_ ? "true" : "false"));
		if (LISTEN_LOG)
			clogListen("(2) listeners " + listens_ + " listening "
					+ (listening_ ? "true" : "false"));
		return result;
	}

	@Override
	public void layoutError(String mesg) {
		if (LISTEN_LOG)
			clogListen("layoutError (" + mesg + ") (4) listeners " + listens_
					+ " listening " + (listening_ ? "true" : "false"));
		if (!listening_)
			restoreListeners(true);
	}

	private IEditorPart bringToTop(IProject project) {
		String langName = project.getName();
		langName = langName.substring(langName.lastIndexOf(".") + 1);
		IFile diagramfile = ResourceUtils.getFile(project, "model" + "/"
				+ langName, "ecorediag");// errstatechart.ecorediag
		IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		if (diagramfile != null) {
			try {
				IEditorPart openedEditor = IDE.openEditor(page, diagramfile);
				page.bringToTop(openedEditor);
				return openedEditor;
			} catch (Exception e) {
				cerror("Open editor failed: " + diagramfile.toString() + " -- "
						+ e);
				if (!listening_)
					restoreListeners(false);
				return null;
			}
		} else {
			cinfo("no diagram for " + langName);
			return null;
		}
	}

	@Override
	public void perspectiveChanged(IPerspectiveDescriptor perspective,
			String perspectiverId, String changeId) {
		if (!isModelConfiguration()) // FP141214
			doPerspectiveChanged(perspective, perspectiverId, changeId);
	}

	public void doPerspectiveChanged(IPerspectiveDescriptor perspective,
			String perspectiverId, String changeId) {
		if (DParams.INFO_PERSPECTIVE_CHANGE)
		   cinfo("perspective changed: " + perspectiverId + " " + changeId);
		if (perspectiverId.equals(GenericConstants.DIAGRAPH_PERSPECTIVE_ID)
				&& (changeId.equals("viewShow") || changeId
						.equals("actionSetShow"))) {
			if (!listening_) {
				restoreListeners(false);
				active = true;
			}
		} else {
			removeListeners_();
			active = false;
		}
	}

	@Override
	public void earlyStartup() {
		cinfo("early  startup");
	}

	@Override
	public void setActiveEcoreDiagramEditor(
			EcoreDiagramEditor ecoreDiagramEditor) { // FP140527zz
		EPackage epack = (EPackage) ecoreDiagramEditor.getDiagram()
				.getElement();
		if (active) {
			if (isUnderProject(ecoreDiagramEditor)) {
				if (LOG7)
					clog7("setActiveEcoreDiagramEditor: " + epack.getName()
							+ " title=" + ecoreDiagramEditor.getTitle());
				setCurrentView(ecoreDiagramEditor.getDiagram(),
						getRegisterdView(epack.getName()));
			} else
				cerror("not under project: " + epack.getName());
		} else
			cinfo("inactive " + epack.getName());
	}

	@Override
	public void setOtherEditor(IEditorPart editor) {
		if (active) {
			if (editor instanceof MegamodelEditor)
				megamodelEditor = (MegamodelEditor) editor;
		} else
			cinfo("inactive");
	}

	private IAutomationService getAutomationService() {
		if (automationService == null) {
			automationService = new AutomationConnector()
					.getAutomationHandler();
			if (automationService.isStub()) {
				if (LOG)
					clog("No automationService is available, using a non functional mock  !");

			} else if (LOG)
				clog("Using Isoe Eclipse Automation Service for automating the platform");
		}
		automationService.setControler(this);
		// automationService.run(); //FP140724
		return automationService;
	}

	// FP141227int
	// ///////////////////////////////////////////////////////////////////

	private void timedBuild(final boolean stateless) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				try {
					sleep(100, "(1)timedBuild");
					if (JOB_LOG)
						jobclog("initialization - build all - generate editors");
					boolean generateEditorsNo = false;
					interpreter_.buildAll(generateEditorsNo, true); // FP140626
					sleep(100, "(2)timedBuild");
					if (JOB_LOG)
						jobclog("end initialization - ok");
					cinfo("workbench initialized on "
							+ currentPackage.getName());
					if (!stateless)
						showMessage("the new Diagraph workspace has been initialized");
				} catch (Exception e) {
					cerror("error during buildInitialLanguage");
				}
			}
		});

	}

	protected void doIdeJob() {
		boolean refreshOnly = true; // FP140602see
		boolean genLanguage = false;
		boolean headless = false;
		boolean headfull = true;
		boolean genEditor = true;
		String arg = textDesign.getText();
		if (arg.isEmpty()) {
			textDesign.setText("Manual phase, do the job !");
			timedClear( 1000, textDesign);
		} else if (arg.equals(IDiagraphAlphabet.MEGAMODEL_NEW_PARSE_NU)
				|| arg.startsWith(IDiagraphAlphabet.MEGAMODEL_FIND)//
				|| arg.startsWith(IDiagraphAlphabet.MEGAMODEL_ALL)
				|| arg.startsWith(IDiagraphAlphabet.MEGAMODEL_DIAGRAPH)//
				|| arg.startsWith(IDiagraphAlphabet.MEGAMODEL_GEN_DSML)//
				|| arg.startsWith(IDiagraphAlphabet.MEGAMODEL_GEN_ROLES)//

				|| arg.startsWith(IDiagraphAlphabet.MEGAMODEL_ACTION_CLONE)//

				|| arg.startsWith(IDiagraphAlphabet.MEGAMODEL_ACTION_MERGE)//

				|| arg.startsWith(IDiagraphAlphabet.MEGAMODEL_CHECK_STATUS)
				|| arg.startsWith(IDiagraphAlphabet.MEGAMODEL_ARTIFACTS)) {

			if (arg.startsWith(IDiagraphAlphabet.MEGAMODEL_ALL))
				refreshOnly = false;
			String[] args = parseStatement(arg);// //FP140605
			String result = invokeMegamodelJob(null, headless, args,
					genLanguage, refreshOnly);// null,false,[find,
												// matoto],false,false
		} else if (textDesign.getText().equals(
				IDiagraphAlphabet.MEGAMODEL_ACTION_BUILD)) {
			refreshOnly = false;
			startBuild();
			interpreter_.buildAll(genEditor, headfull);
			interpreter_.copyAll(genEditor, headfull);
			endBuild();
		} else
			cerror("(4) unknown statement " + textDesign.getText());
	}



	private void timedShowMessage(final int delay, final String mesg) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				try {
					sleep(delay, "timedShowMessage");
					showMessage(mesg);
				} catch (Exception e) {
				}
			}
		});
	}


	@Override
	public String invokeCopyJob(boolean headless, String[] args, String folder_) { // FP150508
		File ws =  new File(CommonPlugin
				.resolve(
						URI.createURI("platform:/resource/")).toFileString());
		IProject lwbRepositoryProject = getlwbRepositoryProject();
		File megafoldr = new File(CommonPlugin
				.resolve(
						URI.createURI("platform:/resource/"
								+ lwbRepositoryProject.getFullPath()
										.toPortableString())).toFileString());
		if (megafoldr.exists()) {
			File mmmm = new File(megafoldr + File.separator + folder_);
			if (!mmmm.exists())
				mmmm.mkdir();
			String language = args[1];
			String givenLanguage = (language == null || language.isEmpty()) ? getCurrentLanguage()
					: language;
			copy(ws.getAbsolutePath(),givenLanguage,mmmm.getAbsolutePath(),"ecore");
			copy(ws.getAbsolutePath(),givenLanguage,mmmm.getAbsolutePath(),"ecorediag");
			clog("invokeCopyJob " + language);
		}
		return "ok";
	}


	private void copy(String workspace, String lang, String targetDir, String suffix){
		String tns = PathPreferences.getPreferences().getTeamNamespace();
		String path = workspace + File.separator + tns+"." + lang + File.separator+"model"+File.separator
				+ lang + "."+suffix;// + File.separator
		File file = new File(path);
		if (file.exists())
			copyfile(targetDir+File.separator+lang + "."+suffix,file);
	}



	private void copyfile(String target, File file) {
		try {
			FilesUtils.copyFile(file, new File(target));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	// FP140606
	public String invokeMegamodelJob(String language, boolean headless,
			String[] arguments, boolean genLanguage, boolean refreshOnly) { // FP131003xx
		String givenLanguage = (language == null || language.isEmpty()) ? getCurrentLanguage()
				: language;
		if (LOG || JOB_LOG)
			invokeLog1(language, headless, genLanguage, refreshOnly, arguments);
		String result = "ok";
		try {
			refreshLanguageRepositoryProject();
			sleep(20, "doMegamodelJob 1");
			getMegamodel_(headless);
			if (megamodelMan.getMegamodel() == null) {
				result = "**********  megamodel should not be null !!";
				cerror(result); // FP130902
				return result;
			} else {
				String option = "";
				String currentView = getCurrentView(42, givenLanguage,
						"______SB_invokeMegamodelJob"); // FP140611
				if (LOG || JOB_LOG || Megamodel_LOG)
					invokeLog(givenLanguage, currentView, arguments);
				result = megamodelBuilder.build(arguments, givenLanguage,
						currentView, option, 1);// FP140606
				refreshLanguageRepositoryProject();// "_megamodel"
				sleep(20, "doMegamodelJob 2");
				if (genLanguage && "ok".equals(result))
					generateLanguage(headless, refreshOnly, this, givenLanguage);
			}
		} catch (Exception e) {
			result = "error while invokeMegamodelJob on " + givenLanguage
					+ " (" + e.toString() + ")";
		}
		return result;
	}

	@Override
	public boolean isLayouting() {
		return layouting;
	}

	@Override
	public void setLayouting(boolean value) {
		layouting = value;
	}

	@Override
	public void setCurrentResource(Resource eResource) { //FP150514
		this.currentResource = eResource;
	}

	@Override
	public Resource getCurrentResource() {
		return currentResource;
	}

	@Override
	public DGraph get(String language, String view) {
		return context.get(language, view); //FP150514voir
	}



/******************** saves ****************************/



	private String readFile(String path, String lineSeparator) {
		File fil = new File(path);
		StringBuffer buffer = new StringBuffer();
		try {
			InputStream in = new FileInputStream(fil);
			try {
				InputStreamReader inR = new InputStreamReader(in);
				BufferedReader buf = new BufferedReader(inR);
				String line;
				while ((line = buf.readLine()) != null)
					buffer.append(line).append(lineSeparator);// ";"
			} finally {
				in.close();
			}
		} catch (IOException e) {
			if (LOG)
				clog("no file " + path);
			return "";
		}
		return buffer.toString();
	}

	private void writeFile_(String path, String content) {

		cinfo("saving "+path);
		if (path != null && !path.isEmpty() && content != null
				&& !content.isEmpty()) {
			File fil = new File(path);
			FileWriter fw;
			try {
				fw = new FileWriter(fil, false);
				fw.write(content);
				fw.close();
			} catch (IOException e) {
				// e.printStackTrace();
				cerror("SDBV_WF  " + e.toString());
			}
			cinfo("saved "+fil.getAbsolutePath());
		//	System.out.println(fil.getAbsolutePath());
		} else
			cerror("nothing to save (" + path + ")");
	}

	private void saveDiagraphActions(String actions) {// FP140117
		String[] loc = getProjectLocation(getCurrentPackage());
		if (loc != null) {
			String[] rows = actions.split("\\n");
			actions = "";
			for (String row : rows) {
				if (LOG)
					clog(row);
				actions += row + "|";
			}
			writeFile_(loc[0] + langageName + "." + "cmds", actions);
			refreshProject(loc[1]);
		}
	}


	private void saveDeployLog(String proj, String domain,String log) {//FP150510
		writeFile_(
				CommonPlugin.resolve(
						URI.createPlatformResourceURI(proj, true))
						.toFileString()
						+ File.separator
						+ "model"
						+ File.separator
						+ domain + "." + "deploy", log);
		refreshProject(proj); // FP130518
	}

	private void saveBuildLog() {//FP150510
		String w = "";
		for (String log : buildLog_)
			w += log + "\r\n";
		String path = getLogFolder() +File.separator + "buildlog.txt";// + File.separator
		clog("saveBuildLog " + path);
		writeFile_(path, w);
		path = getLogFolder() +File.separator + "languages.txt";// + File.separator
		writeFile_(path, languageList);
	}

	@Override
	public void creport(List<String> log) {
		String view = layerHelper.getCurrentView_();
		String toLog="";
		for (String line : log)
			toLog+=line;
		String currentProject = getOpenedProjectName(getCurrentPackage()
				.getName());
		if (currentProject != null)
			writeFile_(
					CommonPlugin
							.resolve(
									URI.createPlatformResourceURI(
											currentProject, true))
							.toFileString()
							+ File.separator
							+ "model"
							+ File.separator
							+ "report_"
							+ getCurrentPackage().getName()+"_"+view
							+ "."
							+ "csv", toLog);
		refreshProject(currentProject);
	}


	@Override
	public void logStatements(String view, String logstatements) { // FP140209
		if (!WRITE_STATEMENTS_)
			return;
		String currentProject = getOpenedProjectName(getCurrentPackage()
				.getName());
		if (currentProject != null)
			writeFile_(
					CommonPlugin
							.resolve(
									URI.createPlatformResourceURI(
											currentProject, true))
							.toFileString()
							+ File.separator
							+ "model"
							+ File.separator
							+ view
							+ "_"
							+ getCurrentPackage().getName()
							+ "."
							+ "stmts", logstatements);

	}



	public void buildError(String mesg){

		if (building)
		  enableGeneration_ = false;
	}


	public void languageStartBuild(EPackage pak){
		logGeneration("current language = "+pak.getName()); //FP150603
	}

	public void languageEndBuild(EPackage pak, String mesg){
		logGeneration(mesg);
	}

	private void startBuild() {
		buildLog_ = new ArrayList<String>();
		languageList = "static final String[] LANGUAGES_XX = { ";
		building = true;
		enableGeneration_ = true;
	}

	private void endBuild() {
		languageList +="};";
		saveBuildLog();
		refreshLanguageRepositoryProject();
		timedShowMessage(1000, "end of build all");
		building = false;
		enableGeneration_ = false;
	}


	private void continueGeneration(String mesg) {
	    enableGeneration_ = true;
	    logGeneration("unable to "+mesg);
	}


	private boolean canGenerate(String language) {

		return enableGeneration_;
	}

}
