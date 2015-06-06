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
package org.isoe.fwk.core;

import org.eclipse.emf.codegen.ecore.genmodel.GenJDKLevel;
import org.eclipse.emf.codegen.ecore.genmodel.GenRuntimeVersion;
import org.eclipse.jdt.core.JavaCore;
import org.isoe.diagraph.generic.GenericConstants;

/**
 *
 * @author pfister
 *
 */
public interface DParams {

	static final boolean CHECK_MEGAMODEL_OPENESS_ON_DEPLOYED_FEATURE = false;
	static final int MAX_DEPTH_ = 8; // FP140121
	static final boolean VERIFY = true;
	static final String[] DEPLOY_INITIAL = { "helloworld","statechart","ecommerce104" };
	static final String[] LANGUAGES_PUBLI_PATTERNS = {
		"simpleworld",
		"tracability",
		"helloworld",
		"adl",
		"composite",
		"effbd103",
		"statechart",
		"iml7",
		"classmate",
		"adl",
		"yy6"
	};


		static final String[] LANGUAGES_PUBLI_PATTERNS_ = { "simpleworld",
			"tracability", "helloworld", "adl", "composite",
			"effbd103", "system101", "rel2rel","comerr",
			"yy6","statechart","yfsm","xfsm","zfsm",
			"uml30","kfsm","errstatechart","simplestatechart","iml7",
			"visualinher","classmate","compmultinher","adl","adl200","adl301",
			"yy6","yyf","yyp","yypp","yyppp",
			"yyq","yyz","z4fsm","z5fsm","z81s","z8e","z8s"


	};

	static final String[] REGRESSIONS = { "yyh","yyl","yym","composite101","yyr"
		,"yyz","yy","z3fsm","z6fsm"};


	static final String[] INITIAL_LANGUAGES = LANGUAGES_PUBLI_PATTERNS;//DEPLOY_INITIAL;// INITIAL_LANGUAGES_THESIS;//DEPLOY_LANGUAGES_0;


	//static final String MWB_LAUNCH_MODE = "debug";
	static final String MWB_LAUNCH_MODE = "run";
	// static final String LAUNCHER_VM_PARAMS
	// ="-Xms128m -Xmx1400m -XX:MaxPermSize=350m -Xss1m -XX:ReservedCodeCacheSize=180m";
	static final String LAUNCHER_VM_PARAMS_0 = "-Xms128m -Xmx1000m -XX:MaxPermSize=350m -Xss1m -XX:ReservedCodeCacheSize=180m";
	static final String LAUNCHER_VM_PARAMS_1 = "-Xms128m -Xmx800m -XX:MaxPermSize=350m -Xss1m -XX:ReservedCodeCacheSize=180m";
	static final String LAUNCHER_VM_PARAMS = "-Xms256m -Xmx900m -XX:MaxPermSize=400m";

	// -Xms512m -Xmx800m -XX:MaxPermSize=512m



	static final String[] INITIAL_DEBUG = { "statechart203" };
	static final String[] INITIAL_CGEIET = { "simpleworld", "research",
			"statechart203", "effbd104", "ptntim102", "tank201", "tank202",
			"tank203", "tank204", "reseausocio", "facebook103" };
	static final String[] INITIAL_LANGUAGES_NAWEL_ = { "simpleworld",
			"research", "cluster", "sipme" };
	static final String[] INITIAL_LANGUAGES_STD = { "simpleworld", "research",
			"cluster" };// ,"rel2rel"};
	static final String[] INITIAL_LANGUAGES_THESIS = { "simpleworld",
			"d4pse101", "effbd103", "system101", "workbench101",
			"systemworkbench102", "rel2rel" };





	static final String[] INITIAL_LANGUAGES_AERES_PREPA = { "simpleworld",
			"d4pse101", "effbd103", "system101", "workbench101",
			"systemworkbench102", "research", "research13", "cluster",
			"rel2rel", "effbd104", "system101", "workbench101", "syswb101",
			"syswb103", "adl200", "itemflow101", "ptn103", "ptntim101",
			"ctrlflow101", "syswbeff106", "syswb106", "syswbee106", "schol",
			"scholar", "semlink", "visualinher", "classmate", "facebook102",
			"friends", "compmultinher", "cluster", "k5", "k7", "test100",
			"test101", "km3", "sipme" };
	// static final String[] INITIAL_LANGUAGES_THESIS =
	// {"simpleworld","d4pse101","effbd103","system101","workbench101","systemworkbench102","adl200","itemflow101","ptn103","ptntim101","ctrlflow101","effbd102","effbd2"};
	static final String[] INITIAL_LANGUAGES_THESIS_1 = { "simpleworld",
			"d4pse101", "effbd104", "system101", "workbench101", "syswb101",
			"syswb103", "adl200", "itemflow101", "ptn103", "ptntim101",
			"ctrlflow101", "syswbeff106", "syswb106", "effbd106", "syswbee106",
			"syswbeff106prepa", "syswbeff1065ok", "effbdpattern" };
	static final String[] INITIAL_LANGUAGES_THESIS_2 = { "simpleworld",
			"effbd106", "d4pse101", "effbdpattern" };

	static final String CONFIG_MINITEST = "cfg.minitest";
	static final String CONFIG_THESIS_ = "cfg.thesis";
	static final String CONFIG_PATTERNS = "cfg.patterns";
	static final String CONFIG_SIJIA = "cfg.sijia";
	static final String CONFIG_ICEIS = "cfg.iceis";

	static final String CONFIG_TEST = "cfg.test";
	static final String CONFIG_OCL = "cfg.ocl";
	static final String CONFIG_AIGLE = "cfg.aigle";
	static final String CONFIG_EFFBD = "cfg.effbd";
	static final String CONFIG_NAWEL = "cfg.nawel";
	static final String CONFIG_AERES = "cfg.aeres";

	static final String CONFIG_INFORSID0 = "cfg.inforsid0";
	static final String CONFIG_INFORSID1 = "cfg.inforsid1";
	static final String CONFIG_INFORSID2 = "cfg.inforsid2";
	static final String CONFIG_INFORSID3 = "cfg.inforsid3";
	static final String CONFIG_DEPLOY2 = "cfg.deploy2";
	static final String CONFIG_DEPLOY3 = "cfg.deploy3";

	static final String[] INFORSID_LANGUAGES_0 = { "sipme", "nested102",
			"nested103", "conf", "conf101", "company104", "company106",
			"workflow", "iml7", "k7", "k23", "compmultinher", "research",
			"adl301", "case4", "facebook102", "facebook103", "inforsid4",
			"statchart311", "testcompat103", "classmate", "friends",
			"inherlink", "manypov2", "simpleworld", "cluster", "fragdial101",
			"ecommerce104" };
	static final String[] INFORSID_LANGUAGES_1 = { "sipme", "testcompat103",
			"nested102", "manypov2", "ktest313", "ktest206", "ktest401",
			"ktest400", "ktest301", "k23", "k7", "visualinheritance",
			"visualinher", "inforsid4", "facebook103", "company106", "case4",
			"adl301", "effbd104", "compmultinher", "fragdial", "iml7",
			"fragdial101", "simpleworld", "adl301", "ecommerce104" };

	static final String[] INFORSID_LANGUAGES_2 = { "sipme", "ktest400",
			"visualinheritance", "visualinher", "inforsid4", "facebook103",
			"company106", "adl301", "effbd104", "compmultinher", "fragdial",
			"fragdial101", "simpleworld", "adl301", "ecommerce104" };

	static final String[] INFORSID_LANGUAGES_3 = { "nested102", "nested103" };

	static final String[] OCL_LANGUAGES = { "ocldriven", "bike", "simplepdl",
			"mimap", "graphe" , "library"};
	static final String[] MINITEST_LANGUAGES = { "adl", "statemachine",
			"activity02" };

	static final String[] THESIS_LANGUAGES_ = { "adl", "fsmkerm", "statechart",
			"statechart201", "statechart203", "statemachine",
			"statemachine103", "activity02", "ptn103", "petrinet", "cat7",
			"relationworld", "relationpattern", "effbd2", "errorstm", "ggram",
			"classmate", "friends", "inherlink", "manypov2", "recette1",
			"recipe", "recipe2", "schol", "scholar", "semlink", "visualinher",
			"wikipedia", "workflow", "iml4", "typegraph", "refinher",
			"multiview4", "library", "imof", "iml7", "k4", "iritptn", "gemos1",
			"gemos106", "gmosb", "dp4se", "compmultinher", "cluster", "k5",
			"k7", "test100", "test101", "km3", "simpleworld", "research",
			"effbd104", "ptntim102", "tank201", "tank202", "tank203",
			"tank204", "reseausocio", "facebook103", "sipme" };

	static final String[] TEST_LANGUAGES = { "iml7", "simpleworld",
			"mintel224", "systemworkbench101", "sipme", "company106",
			"statechart", "statechart201", "statechart203", "statemachine103" };

	static final String[] SIJIA_LANGUAGES_ = { "kreq102", "kreq103", "kreq108c",
			"kreq205", "kst204", "mincase314b", "statemachine", "fragdial",
			"statechart203", "statechart205" };

	static final String[] ICEIS_LANGUAGES = {
		"statechart203", "statechart205","publication103","publication105","publication2014","ptntim101","kfsm","library","ocldriven","research18" };

	static final String[] DEPLOY_LANGUAGES_1 = { "adl", "fsm", "statechart",
			"statemachine", "classmate", "simpleworld", "effbd", "activity",
			"ptn", "iml" };

	static final String[] DEPLOY_LANGUAGES_3 = { "adl", "fsmkerm",
			"statechart", "statemachine", "classmate", "statemachine103",
			"activity02", "petrinet", "relationworld", "recipe", "iml7",
			"simpleworld", "research", "effbd104", "facebook103" };

	static final String[] DEPLOY_LANGUAGES_2 = { "adl", "fsmkerm",
			"statechart", "statemachine", "classmate", "statemachine103",
			"activity02", "petrinet", "cat7", "relationworld", "recipe",
			"iml7", "simpleworld", "research", "effbd104", "facebook103" };


	// DEPLOY_LANGUAGES_0;//INFORSID_LANGUAGES;//
	// INITIAL_LANGUAGES_STD;//INITIAL_LANGUAGES_NAWEL_;//THESIS_LANGUAGES;
	// //INITIAL_CGEIET;//INITIAL_LANGUAGES_AERES_PREPA_;//INITIAL_LANGUAGES_NAWEL;//INITIAL_LANGUAGES_AERES_PREPA;

	static final String[] AIGLE_LANGUAGES = { "tp4", "tp4v", "tp5", "tp6",
			"tpv", "tpc", "tpd", "hello", "helloworld103" };
	static final String[] EFFBD_LANGUAGES_1 = { "adl200", "itemflow101",
			"ptn103", "ptntim101", "ctrlflow101", "effbd103", "effbd102",
			"effbd2" };
	static final String[] EFFBD_LANGUAGES_2 = { "adl200", "itemflow101",
			"ptn103", "ptntim101", "ctrlflow101", "d4pse101", "effbd103",
			"effbd102", "effbd2" };
	static final String[] EFFBD_LANGUAGES_0 = { "adl200", "itemflow101",
			"ptn103", "ptntim101", "ctrlflow101", "d4pse101", "effbd103",
			"effbd102", "effbd2", "syswbeff106", "syswb106", "effbd106",
			"syswbee106" };
	static final String[] NAWEL_LANGUAGES_ = INITIAL_LANGUAGES_NAWEL_;


	static final String[] CONFIGS_ = { CONFIG_NAWEL, CONFIG_SIJIA, CONFIG_ICEIS,
			CONFIG_MINITEST, CONFIG_THESIS_,CONFIG_PATTERNS, CONFIG_TEST, CONFIG_OCL,
			CONFIG_AIGLE, CONFIG_EFFBD, CONFIG_INFORSID0, CONFIG_INFORSID1,
			CONFIG_INFORSID2, CONFIG_INFORSID3, CONFIG_DEPLOY2, CONFIG_DEPLOY3,

	};

	static final String[][] CONFIG_LANGUAGES_ = { NAWEL_LANGUAGES_,
		SIJIA_LANGUAGES_,ICEIS_LANGUAGES, MINITEST_LANGUAGES, THESIS_LANGUAGES_,
		    LANGUAGES_PUBLI_PATTERNS,
			TEST_LANGUAGES, OCL_LANGUAGES, AIGLE_LANGUAGES, EFFBD_LANGUAGES_0,
			INFORSID_LANGUAGES_0, INFORSID_LANGUAGES_1, INFORSID_LANGUAGES_2,
			INFORSID_LANGUAGES_3, DEPLOY_LANGUAGES_2, DEPLOY_LANGUAGES_3 };

	// the poor man's logger is the best logger, due to the "final" qualifier
	// which removes the
	// bytecode when false
	// equivalent to the conditional compilation constant directives (C language)


	static final boolean LOG_AT_RUNTIME = false;
	static final boolean DUPLICATE_LOGCONSOLE = true;
	static final boolean LOG_ON_CONSOLE = true;

	static final boolean LISTEN_LOG = false && !DUPLICATE_LOGCONSOLE;


	static final boolean Initialisation_LOG = false;



	static final boolean COMPOSITE_LABELS_LOG_ = false;


	static final boolean DiagraphLayout_LOG = false;
	static final boolean LayoutAction_LOG = false;
	static final boolean LanguageTransformer_4_LOG = false;
 //FP150316

	static final boolean DiagraphLineStyler_LOG = false; //FP150316; //FP141227
	static final boolean RoleManager_LOG = false; //FP150316;
	static final boolean Interpreter_LOG = false; //FP150316;


	static final boolean DiagraphGenerator_LOG = false;// y



	static final boolean merge_LOG = false; //FP150316; //FP141227

	static final boolean UI_LOG = false;









	static final boolean ExecuteTemplatesOperation_LOG = false;


	//////////////////





	//////////////////////////////
	static final boolean DiaLink_LOG = false;


	// ////////////////////////////////////






	static final boolean DiagraphMapping_LOG = false;
	static final boolean DiagraphElement_LOG = false;
///////////////////////////////////////////////////



	static final boolean Megamodel_LOG = false;
/**/


	static final boolean ConcreteSyntaxParser_LOG = false;

	static final boolean TextGen6_LOG = false;

	static final boolean TextParser_LOG = false;
	static final boolean TextGenerator_LOG = false;
	static final boolean DiagraphToTextGrammarJob_LOG = false;
	static final boolean DiagraphInterpreter_LOG = false;
	static final boolean DiaContainedElement_LOG = false;


	/**/
	static final boolean MegaModelBuilder_LOG = false;
	static final boolean SandboxView_8_LOG = false;
	static final boolean HandleMegamodelAction_LOG = false;
	static final boolean MegaModelBuilderJOB_LOG = false;
	static final boolean DiagraphContext_LOG = false;

//////////////////////////////////////////////////






	// ////////////////////////////////////






	static final boolean RuntimeWorkspaceSetup_LOG = false;
	static final boolean Automation_LOG = false;
	static final boolean SandboxView_LOG = false;

	//16 ////////////////////////////////////


	static final boolean COMPOSITE_LOG = false;



	static final boolean GmfMapGenerator_LOG = false;
	static final boolean GmfGraphGenerator_LOG = false;//true;

	static final boolean Parser_29_LOG = false;//true;
	//26 ////////////////////////////////////
	static final boolean ContainmentHandler_LOG = false;
	static final boolean DiagraphAnnotationParser_LOG = false;//FP150316
	static final boolean DiagraphNode_LOG = false;


	static final boolean DiaContainment_LOG = false;
	static final boolean Parser_CONTAINMENT2_LOG = false;//true;



	static final boolean DiagraphGrammar_LOG = false;
	static final boolean DiaInheritance_LOG = false;
	static final boolean containment_LOG = false;
	static final boolean DiaNode_LOG = false;
	static final boolean DiagramGenerator_LOG = false;//true;

	//26 ////////////////////////////////////

	static final boolean DiaGraph_LOG = false;
	static final boolean DiagraphObject_LOG = false;


	static final boolean DTokenizer_LOG = false;//true;
	static final boolean BundleFileCopier_LOG = false;

	////ggg
	static final boolean DAnnotation_LOG = false;//true;
	static final boolean DGenHelpers_LOG = false;
	static final boolean Parser_15_LOG = false;//true;
	static final boolean DiaParser_LOG = false; // error10 case

	////ggg
	static final boolean TextGenerator22_LOG = false;
	static final boolean TextGenerator2_LOG = false;
	static final boolean DGraphFactory_LOG = false;
	static final boolean DiaConvertM2_LOG = false;
	////ggg

	static final boolean Special_LOG = false;
	static final boolean GmfGeneratorInvoker_LOG = false;//true;


	static final boolean _StyleUtils_LOG = false;


    ////////////////////////////endcurrent




    ////////////////////////////

	static final boolean DiaConcreteSyntax_LOG = false;
	static final boolean DiagraphLanguage_LOG = false;
	static final boolean DiagraphCommand_LOG = false;
	static final boolean DiagraphState_LOG = false;
	static final boolean AnnotationHelper_LOG = false;
	static final boolean LayerHelper_LOG = false;


	/****/

	static final boolean _DiagraphAnnotationFactory_LOG = false;
	static final boolean _DiagraphCommandHandler_LOG = false;

	static final boolean SandboxView_TEST_LOG = false;

	/****/
	static final boolean SandboxView_6_LOG = false;
	static final boolean SandboxView_7_LOG = false; //FP150316; //FP141227

	static final boolean SandboxView_5_LOG = false;





	///
	static final boolean MegaModelMan_LOG = false;
	static final boolean HandleMegamodelJob_LOG = false;

	static final boolean DiagramParser_LOG = false;

	////

	static final boolean MegaModelBuilderCsv_LOG = false;




	static final boolean GenDiagraphAction_LOG = false;







	//////////////140605





	static final boolean sleep_LOG_ = false;
	static final boolean TODO_LOG_ = false;

	static final boolean AbstractDiagenJob_LOG = false;





	static final boolean DiagraphAdapter_LOG = false;
//////////////




	static final boolean ModelWorkbenchLauncher_LOG = false;


	static final boolean ActiveEditorTracker_LOG = false;


	//////////////////


	static final boolean DropAnnotationCommand_LOG = false;



	static final boolean CheckAnnotationCommand_LOG = false;


	static final boolean DiagraphAnnotation_LOG = false;




	static final boolean LayoutWorkbenchAction_LOG = false;



	static final boolean ContainmentPattern_LOG = false;




	static final boolean AnnotationPA_LOG = false;


	static final boolean Parser_CONTAINMENT1_LOG = false;
	static final boolean AnnotationUtils_LOG = false;





	// ///////////
	static final boolean DBaseGenerator_LOG = false;


	static final boolean DStatement_LOG = false;




	static final boolean InstanceHandler_LOG = false;

	static final boolean InterpretModelAction_LOG = false;


	static final boolean LanguageUtils_LOG = false; // y



	static final boolean DiagramGeneratorEXTENDED_LOG = false;
	static final boolean GmfLogger_LOG = false;
	static final boolean GmfGenGenerator_LOG = false;

	static final boolean KermetaLauncher_LOG = false;

	static final boolean AbstractExampleWizard_LOG = false;

	static final boolean AnnotationUtils2_LOG = false;
	static final boolean ResourceManager_LOG = false;

	static final boolean AnnotationParser_LOG = false;

	// /FP140209



	static final boolean DiaUtils_LOG = false;

	static final boolean MapRefactor_LOG = false;

	static final boolean CompiledGraph_LOG = false;


	static final boolean GrammGenService_LOG = false;
	static final boolean SimilarityChecker_LOG = false;
	static final boolean AddAnnotationAttributeCommand_LOG = false;
	static final boolean MultyView_LOG = false;

	/****/



	static final boolean GmfToolGenerator_LOG = false;

	static final boolean GmfBaseGenerator_LOG = false;
	static final boolean PaletteManager_LOG = false;



	static final boolean Startup_LOG = false; //FP150316; //FP141227
	static final boolean GenNotationAction_LOG = false;

	static final boolean DiagraphRunner_LOG = false;
	static final boolean ResourceUtils_LOG = false;



	static final boolean DiagraphWeaverJob_LOG = false;

	static final boolean DiagenParser_LOG = false;

	// followings at true to debug language merging
	static final boolean LanguageTransformer_LOG = false;
	static final boolean EcoreUtils_LOG = false;

	// followings at true to debug views

	// followings at true to debug consolidation



	static final boolean MetamodelRetriever_LOG = false;
	static final boolean PluginResourceCopy_LOG = false;
	static final boolean MegamodelDeployAction_LOG = false; //FP150316;
	static final boolean MegamodelPersistence_LOG = false;
	static final boolean EmfHandler_LOG = false; //FP150316;



//////////////////////////////////////
	static final boolean DiagraphRuntimeFactory_LOG = false;
	static final boolean WorkbenchUtils_LOG = false;

	static final boolean SandboxView_LOG_RESOURCECHANGED = false;

	static final boolean LOG_PARSED = false;


	/*------------------------------- consolidation ----------------------------------*/

	static final boolean OVERWRITE_ON_DEPLOY = false;
	static final boolean DPEDA_OVERWRITE_ON_DEPLOY = false;
	static final boolean CONSOLIDATE_ = true;

	/*---------------------------------------------  server -----------------------------*/

	static final String HOST_NAME = "localhost"; // TODO migrate to
													// preferences
	// static final String HOST_NAME= "146.19.4.107";
	//static final int LANGAGE_SERVER_PORT__nu = 8261; // language config
	//static final int MODEL_SERVER_PORT__nu = 8262; // model config
	static final boolean SERVER = true;

	static final String REST_POINT_SERVER_NAME = "localhost";
	// todo: user and password for mysql

	static final String CONNECTED_TO_REMOTE_REPOSITORY = "From OpenDsml";
	static final String CONNECTED_LOCAL_FROM_VMARGS = "From VmArgs";
	static final String CONNECTED_LOCAL_FROM_PREFS = "From Preferences";
	static final String CONNECTED_LOCAL_FROM_HARD = "Hard Defined";

	static final String ECORETOOLSEDITINGDOMAIN_ID = "org.eclipse.emf.ecoretools.legacy.diagram.EditingDomain";
	static final String ECORETOOLSEDITINGDOMAIN_PREFIX = ECORETOOLSEDITINGDOMAIN_ID
			+ "@";
	static final int ECORETOOLSEDITINGDOMAIN_PREFIX_LENGTH = ECORETOOLSEDITINGDOMAIN_PREFIX
			.length();

	/*------------------------------- static --------------------------------------------*/

	static final String ICON_TYPE = "gif";

	// static final boolean LOG_LANGUAGE = false;

	static final String ECORE_SUFX = "ecore";
	static final int ECORE_SUFX_LEN = ECORE_SUFX.length() + 1;
	static final String DIAGRAPH_SUFX = "diagraph";
	static final String DOTTED_DGRF_SUFFIX = "." + DIAGRAPH_SUFX;
	static final String DGRF_PREFIX_ = "org.isoe.diagraph.diagraph";
	static final String DGRF_PATH = DGRF_PREFIX_ + "." + DIAGRAPH_SUFX;
	static final String DGI_SUFFIX = "dgi";
	static final boolean SAVE_DGI = false;

	static final String DOTIFY_ACTION_PREFIX = "org.isoe.diagraph.dotify.";

	static final String REPOSITORY_FILTER_PREFIX_ = "no_M2_for_";

	/*
	 * REFACTOR_xxx constants are overriden by - refactor - annotations in
	 * meta-models
	 *
	 * @see org.isoe.diagraph.refactoring.MetamodelRefactoring
	 */

	// static final boolean PARSER_ASSERT_UNIQUE_CONTAINMENT = false;//
	// FP130612xx
	// false;//
	// FP2611




	static final boolean PARSER_PROPAGATE_INHERITED_REFERENCES_nu = false;
	static final boolean PARSER_PROPAGATE_INHERITED_CONTAINMENTS_ = true; // FP140130
	static final boolean PARSER_CHECK_LINKS = false;
	static final boolean PARSER_ASSERT_NOT_ABSTRACT = false;
	static final boolean CONTAINMENT_CHECK_STRICT = true; // FP130823
															// false;
	static final boolean INFER_LABELS_IF_MISSING = true;
	static final boolean DELETE_OLD_SOURCES = true;
	static final boolean THROW_CONTAINMENT_ERRORS = false;
	static final boolean WARN_CONTAINMENT_ERRORS = false;
	static final boolean REMOVE_NOT_MAPPED = true;
	static final String NAMED_NODE = "Named";
	static final String DEFAULT_NAME = "name";

	// static final String[] NAME_ATTRIBUTES = { DEFAULT_NAME,
	// "nom","description", "title", "id", "nom" };

	static final String[] NAME_ATTRIBUTES_nu = { DEFAULT_NAME, "nom",
		"description", "titre", "id", "name", "label", "subject", "title",
		"topic" };
	static final String[] NAME_ATTRIBUTES = { DEFAULT_NAME,  "title", "topic" };
	// org.isoe.fwk.core.DParams.NAME_ATTRIBUTES_REVERSED //FP130502

	static final String[] NAME_ATTRIBUTES_REVERSED_ = { "topic", "title", DEFAULT_NAME };
	static final String[] NAME_ATTRIBUTES_REVERSED_nu = { "topic", "title",
			"subject", "label", "name", "id", "titre", "description", "nom",
			DEFAULT_NAME }; // FP130330 à l'envers

	static final boolean LOG_TESTABLES = false; // FP121124j

	static final boolean ASSERT_NOT_NULL_CONTAINEMENT_TARGET = false;

	static final int DEFAULT_NODE_SIZE_WIDTH = 70;
	static final int DEFAULT_NODE_SIZE_HEIGHT = 50;

	// org.isoe.diagraph.DParams
	/*-------------------------------editable with the preference page -------------------------------------*/

	static final boolean DEFAULT_DELETE_OLD_JAVA_SRC = true;
	static final String LABEL_DELETE_OLD_JAVA_SRC = "Delete old java sources";
	static final String KEY_DELETE_OLD_JAVA_SRC_ = "DELETE_OLD_JAVA_SRC";

	static final boolean DEFAULT_LAYOUT_AT_STARTUP = true;
	static final String LABEL_LAYOUT_AT_STARTUP = "Layout at startup";
	static final String KEY_LAYOUT_AT_STARTUP = "LAYOUT_AT_STARTUP";

	static final boolean DEFAULT_ADD_MISSING_ANNOTATIONS = false;
	static final String LABEL_ADD_MISSING_ANNOTATIONS = "Add Missing Annotations";
	static final String KEY_ADD_MISSING_ANNOTATIONS = "DELETE_ADD_MISSING_ANNOTATIONS";
	// static boolean addMissingAnnotations;

	static final boolean DEFAULT_CHANGE_LANGUAGE_VERSION___ = false;
	static final boolean CHANGE_LANGUAGE_VERSION = false;
	static final String LABEL_CHANGE_LANGUAGE_VERSION = "Change language version";
	static final String KEY_CHANGE_LANGUAGE_VERSION = "CHANGE_LANGUAGE_VERSION";
	// static boolean changeLanguageVersion =
	// DEFAULT_CHANGE_LANGUAGE_VERSION;

	/*
	 * //FP120320 addedDEFAULT_SAVE_EMF_TRANSFO
	 * DEFAULT_SAVE_GENERATED_ANNOTATIONS_IF_DIFFERENT_TARGET DEFAULT_SAVE_M2
	 * DEFAULT_REFACTOR_VERSION
	 */

	static final boolean DEFAULT_SAVE_EMF_TRANSFO = true;
	static final String LABEL_SAVE_EMF_TRANSFO = "Save Emf Transformation";
	static final String KEY_SAVE_EMF_TRANSFO = "SAVE_EMF_TRANSFO";
	// static boolean saveEmfTransfo = DEFAULT_SAVE_EMF_TRANSFO;

	static final boolean DEFAULT_SAVE_GENERATED_ANNOTATIONS_IF_DIFFERENT_TARGET = true;
	static final String LABEL_SAVE_GENERATED_ANNOTATIONS_IF_DIFFERENT_TARGET = "Save Generated Annotations";
	static final String KEY_SAVE_GENERATED_ANNOTATIONS_IF_DIFFERENT_TARGET = "SAVE_GENERATED_ANNOTATIONS_IF_DIFFERENT_TARGET";
	// static boolean saveGeneratedAnnotations =
	// DEFAULT_SAVE_GENERATED_ANNOTATIONS_IF_DIFFERENT_TARGET;

	static final boolean DEFAULT_SAVE_M2_ = true;
	static final String LABEL_SAVE_M2_ = "Save M2";
	static final String KEY_SAVE_M2_ = "SAVE_M2";
	// static boolean saveM2 = DEFAULT_SAVE_M2;

	static final boolean DEFAULT_ANOTHER_FOLDER = false;
	static final String LABEL_ANOTHER_FOLDER = "Result in a different folder";
	static final String KEY_ANOTHER_FOLDER = "ANOTHER_FOLDER";
	// static boolean resultInAnotherFolder = DEFAULT_ANOTHER_FOLDER;

	static final boolean DEFAULT_SAVE_OLD = true;
	static final String LABEL_SAVE_OLD = "Save old model";
	static final String KEY_SAVE_OLD = "SAVE_OLD";
	// static boolean saveOld = DEFAULT_SAVE_OLD;

	static final String DEFAULT_TARGET_MODEL_DIR = "target-model";
	static final String LABEL_TARGET_MODEL_DIR = "Result model folder";
	static final String KEY_TARGET_MODEL_DIR = "REFACT_MODEL_DIR";
	// static String targetModelDir = DEFAULT_TARGET_MODEL_DIR;

	static final String DEFAULT_SAVED_MODEL_DIR = "saved-model";
	static final String LABEL_SAVED_MODEL_DIR = "Saved model folder";
	static final String KEY_SAVED_MODEL_DIR = "SAVED_MODEL_DIR";

	static final String UNIVERSE_PROJECT_NAME = "_megamodel";
	static final String DEFAULT_UNIVERSE_NAME = UNIVERSE_PROJECT_NAME;
	static final String LABEL_UNIVERSE_NAME = "Universe name";
	static final String KEY_UNIVERSE_NAME = "UNIVERSE_NAME"; // TODO -
																// remove
																// from
																// preferences,
																// configure
																// in
																// eclipse.ini

	// static final String TEAM_NAMESPACE = "my.team"; //
	// FP130511//"diagraph.zoo";
	static final String TEAM_NAMESPACE = "lang.m2"; // FP130511//"diagraph.zoo";
	static final String DEFAULT_TEAM_NAMESPACE = TEAM_NAMESPACE;
	static final String LABEL_TEAM_NAMESPACE = "Team namespace";
	static final String KEY_TEAM_NAMESPACE = "TEAM_NAMESPACE";

	static final String INSTANCE_NAME = "instances";
	static final String DEFAULT_INSTANCES_NAME = INSTANCE_NAME;// DEFAULT_MEGAMODEL_NAME+".instance";
	static final String LABEL_INSTANCES_NAME = "Instances name";
	static final String KEY_INSTANCES_NAME = "INSTANCES_NAME";

	// static final String
	// DEFAULT_UNIVERSE_FOLDER="src/org/isoe/diagraph/mg";
	// static final String DEFAULT_UNIVERSE_FOLDER = "src/repository";
	static final String DEFAULT_UNIVERSE_FOLDER_ = "repository";
	static final String LABEL_UNIVERSE_FOLDER = "Universe folder";
	static final String KEY_UNIVERSE_FOLDER = "UNIVERSE_FOLDER"; // ajo
																	// "src/"

	/*
	 * static boolean DEFAULT_REGENERATE=false; static String
	 * LABEL_REGENERATE="REGENERATE"; static String KEY_REGENERATE="REGENERATE";
	 * static boolean regenerate=DEFAULT_REGENERATE;
	 */

	static final boolean DEFAULT_ALLOW_PATTERN_BASED_REFACTORING = false;
	static final String LABEL_ALLOW_PATTERN_BASED_REFACTORING = "allow pattern based refactoring";
	static final String KEY_ALLOW_PATTERN_BASED_REFACTORING = "ALLOW_PATTERN_BASED_REFACTORING";
	// static boolean allowPatternBasedRefactoring =
	// DEFAULT_ALLOW_PATTERN_BASED_REFACTORING;

	static final boolean DEFAULT_REFACTOR_DUPLICATE_REFERENCES = false;
	static final String LABEL_REFACTOR_DUPLICATE_REFERENCES = "refactor duplicate references";
	static final String KEY_REFACTOR_DUPLICATE_REFERENCES = "REFACTOR_DUPLICATE_REFERENCES";
	// static boolean refactorDuplicateReferences =
	// DEFAULT_REFACTOR_DUPLICATE_REFERENCES;

	static boolean DEFAULT_REFACTOR_POOR_REFERENCES = false;
	// static boolean refactorPoorReferences =
	// DEFAULT_REFACTOR_POOR_REFERENCES;
	static final String LABEL_REFACTOR_POOR_REFERENCES = "refactor poor references";
	static final String KEY_REFACTOR_POOR_REFERENCES = "REFACTOR_POOR_REFERENCESS";

	static boolean DEFAULT_REFACTOR_NAMES = false;
	// static boolean refactorNames = DEFAULT_REFACTOR_NAMES;
	static final String LABEL_REFACTOR_NAMES = "refactor names";
	static final String KEY_REFACTOR_NAMES = "REFACTOR_NAMES";

	static boolean DEFAULT_DIAGRAPH_AUTO_GENERATION = false;
	// static boolean generateAutomatically =
	// DEFAULT_DIAGRAPH_AUTO_GENERATION;
	static final String LABEL_DIAGRAPH_AUTO_GENERATION = "generate automatically gmf plugin";
	static final String KEY_DIAGRAPH_AUTO_GENERATION = "DIAGRAPH_AUTO_GENERATION";

	static boolean DEFAULT_EMFGMF_GENERATION_ = true;
	static final String LABEL_EMFGMF_GENERATION = "generate EMF and GMF plugins";
	static final String KEY_EMFGMF_GENERATION_ = "EMFGMF_GENERATION";

	static final boolean DEFAULT_FILTER_TEST_PROJECTS = false;
	// static boolean filterTestProjects = DEFAULT_FILTER_TEST_PROJECTS;
	static final String LABEL_FILTER_TEST_PROJECTS = "filter test projects";
	static final String KEY_FILTER_TEST_PROJECTS = "FILTER_TEST_PROJECTS";

	/**
	 * --------------------------------- layout constants
	 * ---------------------------------------
	 **/

	static final String KEY_DIAGRAPH_LAYOUT_ = "Arrange Diagram as Diagraph";
	static final String DIAGRAPH_LAYOUT_ = "Diagraph";
	/*
	 * static int DEFAULT_DIAGRAPH_LAYOUT_VERTICAL_OFFSET = 0; public static
	 * String KEY_DIAGRAPH_LAYOUT_VERTICAL_OFFSET =
	 * "DIAGRAPH_LAYOUT_VERTICAL_OFFSET"; static String
	 * LABEL_DIAGRAPH_LAYOUT_VERTICAL_OFFSET = "layout vertical offset";
	 *
	 * static int DEFAULT_DIAGRAPH_LAYOUT_WIDTH_OFFSET = 0; static String
	 * KEY_DIAGRAPH_LAYOUT_WIDTH_OFFSET = "DIAGRAPH_LAYOUT_WIDTH_OFFSET"; static
	 * String LABEL_DIAGRAPH_LAYOUT_WIDTH_OFFSET = "layout width offset";
	 *
	 * static int DEFAULT_DIAGRAPH_LAYOUT_LEFT_OFFSET = 0; static String
	 * KEY_DIAGRAPH_LAYOUT_LEFT_OFFSET = "DIAGRAPH_LAYOUT_LEFT_OFFSET"; static
	 * String LABEL_DIAGRAPH_LAYOUT_LEFT_OFFSET = "layout left offset";
	 */

	/**
	 * ------------------------------- compliance constants
	 * -------------------------------------
	 **/

	static final GenJDKLevel GEN_JDK_LEVEL = GenJDKLevel.JDK60_LITERAL;// FP140225
																		// GenJDKLevel.JDK50_LITERAL;
																		// //
																		// FP120521
	static final GenRuntimeVersion EMF_LEVEL = GenRuntimeVersion.EMF27;
	static final String JAVA_VERSION = JavaCore.VERSION_1_6;

	/**
	 * ------------------------------- folders
	 * -------------------------------------
	 **/

	static final String MEGAMODEL_DEPLOYER_PLUGIN = "org.isoe.fwk.megamodel.deploy"; // FP130627
	// static final String MEGAMODEL_DEPLOYER_PLUGIN_ =
	// "org.isoe.fwk.megamodel.deployer";
	// keep in sync with org.isoe.fwk.megamodel.deployer.Activator.PLUGIN_ID;

	// org.isoe.fwk.core.DParams.MODEL_FOLDER
	static final String MODEL_FOLDER = "model";
	static final String MODEL_FOLDER_SEP = MODEL_FOLDER + "/";

	static final String SEP_MODEL_FOLDER_SEP = "/" + MODEL_FOLDER_SEP;

	// static final String DIAGRAPH_NAME = "diagraph";
	static final String DIAGRAPH_M2_PLUGIN = "org.isoe.diagraph.diagraph";
	static final String DIAGRAPH_NSURI = "http://isoe-2012-diagraph-dsmlv4";
	static String DIAGRAPH_M2_LOCATION_ = MODEL_FOLDER_SEP + DIAGRAPH_SUFX
			+ ".ecore";

	;
	static final String TESTS_PLUGIN_SUFFIX = "tests";

	static final String MEGAMODEL_FOLDER_LOCAL = "repository";

	// static final String MEGAMODEL_LOCAL_LANG_EXAMPLES=
	// "repository-le-lo";

	static final String MEGAMODEL_REPOSITORY_BUNDLE_ = "repository-bundle";
	static final String MEGAMODEL_BUILDIN_LANG_EXAMPLES_ = "repository-le-bi";

	/** models **/

	static final String UNIVERSE_MODEL = "megamodel";

	static final boolean ALLOW_DECLASSMENT = false;

	static final boolean CHECK_POV_CONSISTENCE = false;

	/* style */
	static final int RECTANGLE_BORDER_WIDTH_ = 5; // FP130318
	static final int COMPARTMENT_BORDER_WIDTH_ = 9; // FP130318
	static final int COMPARTMENT_INSETS_ = 12;
	static final String RECTANGLE_FOREGROUND_ = "lightGray"; // black
	static final int NODE_INSETS_ = 7;

	static final int RECTANGLE_BORDER_WIDTH = 1; // FP130318
	static final int COMPARTMENT_BORDER_WIDTH = 1; // FP130318
	static final int COMPARTMENT_INSETS = 3;
	static final String RECTANGLE_FOREGROUND = "black";// "lightGray";
														// //black
	static final int NODE_INSETS = 3;

	static final String DIAGRAPH_PREFIX = "org.isoe.diagraph.diagraph";// DParams.DEFAULT_MEGAMODEL_NAME;
	static final String DIAGRAPH_SUFFIX = GenericConstants.ANNOT_DIAGRAPH_LITTERAL;// "dgi";//
																					// DiagraphPreferences.getStringPreference(DParams.DEFAULT_MEGAMODEL_NAME);
	static final String EDIT_PLUGIN_SUFFIX_ = "edit";
	static final String EDITOR_PLUGIN_SUFFIX = "editor";
	static final String TEST_PLUGIN_SUFFIX = "tests";
	static final String INSTANCE_FOLDER = "instances"; // TODO :
														// configure in
														// Eclipse .ini
														// //different
														// from
														// INSTANCE_NAME
														// !!!
	static final String UNIVERSE_MODEL_ROOT_NAME = "megamodel";

	// static final String MEGAMODEL_SOURCE1 =
	// MEGAMODEL_BUILDIN_LANG_EXAMPLES;//MEGAMODEL_FOLDER_;
	// static final String MEGAMODEL_SOURCE2 =
	// MEGAMODEL_LOCAL_LANG_EXAMPLES;//"repository2";
	static final String REPOSITORY_TRACE = "trace";
	static final String TRACE_DATA = "trace";
	static final String MODELS_FOLDER = "models";


	static final String PRJ_CONSTRAINT_ERROR = "must be diagraphed and in the right folder ...";
	static final String NO_DIAGRAM_ERROR = "select a diagram ...";
	static final boolean GENERATOR_PRINT_STACK_TRACE = false;

	static final boolean DO_MEGAMODEL_CHECK_STATUS_AFTER_GEN_LANGUAGE = false;
	static final boolean DO_MEGAMODEL_CHECK_STATUS_AFTER_LANGUAGE_CONFIG = false;
	static final boolean DO_MEGAMODEL_CHECK_STATUS_AFTER_PROTOTYPES = true;

	static final boolean DYNAMIC_TEMPLATES = true; // FP130914

	static final String VIEW_PREFIX = "vw"; // FP131116
	static final int VIEW_PREFIX_LEN = VIEW_PREFIX.length(); // FP140121

	static final boolean EXPERIMENT_OCL = false; // FP140122
	static final boolean CHECK_SUM = false;
	static final boolean REMOVE_ICONS_IF_MULTIPLE_LABELS = false;
	static final boolean WRITE_STATEMENTS_ = false; // FP140209
	static final boolean VALIDATE_GMF_MAP = false;
	static final String DEFAULT_TEAM_NAME = TEAM_NAMESPACE;
	static final String LABEL_SEPARATORS = ":/=[],-";

	//static final boolean RAISE_TODO = false;
	// static final boolean GENERATE_EMF_ON_DEPLOY_ = false; //FP140415
	// static final boolean GENERATE_DIAGRAPH_ARTIFACTS_ON_DEPLOY = false;
	static final boolean GENERATE_EMF_ON_DEPLOY_ = false;
	static final boolean COMPILE_PROJECTS = false;

	static final String NULL_ANNOTATION_VALUE = "_";

	static final boolean DEPLOY_OSEM = true;
	static final boolean CREATE_SNIPPET_M2_PROJECT = true; //FP150606
	static final boolean ModelDeployService_LOG = false; //FP150316;
	static final boolean EmfDeployr_LOG = false; //FP150316;
	static final boolean OsemDeployer_LOG = false; //FP150316;


	static final String TOMCAT_JVM_NOT_USED = "E:\\Java\\jdk1.7.0_03";
	static final String TOMCAT_HOME_NOT_USED = "E:\\Apps\\apache-tomcat-7.0.39";
	static final int QUEUE_SIZE = 10;
	static final int REDROP_DELAY = 10;
	static final boolean REDROP = true;
	static final int LAYOUT_INHIBIT_DELAY = 500;
	static final boolean WEAVER_WRITE_CSV = true;
	static final boolean EXIT_ON_SERVER_ERROR = false;
	static final boolean EXCEPTION_ON_NON_FUNCTIONAL_CONTRIBUTION = true;
	//static final boolean SKIP_ABSTRACT_nu = false; //FP150423azvoir
	static final boolean RELOAD_ALWAYS = true;  //FP150514
	static final boolean GEN_RCP = false;
	static final int MAX_ERRLEN = 500;



	static final boolean CREATE_SNIPPET_M1_PROJECT = true;
	static final String M1_PREFIX = "lang.m2";
	static final boolean LNK_OBSOLETE = true;
	static final String PROMPT_LANG_TO_DEPLOY = "all languages (or specify which)";



	static final boolean INFO_PERSPECTIVE_CHANGE = false;

	static final boolean MODE_COMPOSITE_PATTERN = false;
	static final boolean RESOLVE_COMPOSITE = false;

	static final String PROMPT_MODEL_WORKBENCH_NAME_ = "mwb";
	static final String FIXED_MODEL_WORKBENCH_NAME_ = "TSI";
	static final boolean MEGAMODEL_CONSOLIDATE = true;
	static final boolean MEGAMODEL_DEPLOY = true;























































}

// args for testing: -Dosgi.requiredJavaVersion=1.5 -Xms128m -Xmx1024m
// -XX:MaxPermSize=512m -ea
