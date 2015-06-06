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
package org.isoe.diagraph.preferences;

import java.util.List;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.isoe.fwk.core.DParams;

/**
 *
 * @author pfister
 *
 */
public class DiagraphPreferences extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {
//org.isoe.diagraph.preferences.DiagraphPreferences
	private static String userName;
	private static String teamName;
	private static String teamNameSpace_;
	private static String email;
	private static String password;
	private static String connectState;


	// org.isoe.diagraph.preferences.getPreferences();

	public DiagraphPreferences() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Diagraph Preferences");
	}

	public void createFieldEditors() {

		addField(new BooleanFieldEditor(DParams.KEY_ANOTHER_FOLDER,
				DParams.LABEL_ANOTHER_FOLDER, getFieldEditorParent()));
		addField(new BooleanFieldEditor(DParams.KEY_SAVE_OLD,
				DParams.LABEL_SAVE_OLD, getFieldEditorParent()));

		addField(new BooleanFieldEditor(DParams.KEY_DELETE_OLD_JAVA_SRC_,
				DParams.LABEL_DELETE_OLD_JAVA_SRC, getFieldEditorParent()));
		addField(new BooleanFieldEditor(DParams.KEY_LAYOUT_AT_STARTUP,
				DParams.LABEL_LAYOUT_AT_STARTUP, getFieldEditorParent()));
		addField(new StringFieldEditor(DParams.KEY_TARGET_MODEL_DIR,
				DParams.LABEL_TARGET_MODEL_DIR, getFieldEditorParent()));
		addField(new StringFieldEditor(DParams.KEY_SAVED_MODEL_DIR,
				DParams.LABEL_SAVED_MODEL_DIR, getFieldEditorParent()));

		addField(new StringFieldEditor(DParams.KEY_UNIVERSE_FOLDER,
				DParams.LABEL_UNIVERSE_FOLDER, getFieldEditorParent()));
		addField(new StringFieldEditor(DParams.KEY_UNIVERSE_NAME,
				DParams.LABEL_UNIVERSE_NAME, getFieldEditorParent()));
		addField(new StringFieldEditor(DParams.TEAM_NAMESPACE,
				DParams.LABEL_TEAM_NAMESPACE, getFieldEditorParent()));
		addField(new StringFieldEditor(DParams.KEY_INSTANCES_NAME,
				DParams.LABEL_INSTANCES_NAME, getFieldEditorParent()));

		addField(new BooleanFieldEditor(
				DParams.KEY_ALLOW_PATTERN_BASED_REFACTORING,
				DParams.LABEL_ALLOW_PATTERN_BASED_REFACTORING,
				getFieldEditorParent()));
		addField(new BooleanFieldEditor(
				DParams.KEY_REFACTOR_DUPLICATE_REFERENCES,
				DParams.LABEL_REFACTOR_DUPLICATE_REFERENCES,
				getFieldEditorParent()));
		addField(new BooleanFieldEditor(DParams.KEY_REFACTOR_POOR_REFERENCES,
				DParams.LABEL_REFACTOR_POOR_REFERENCES, getFieldEditorParent()));
		addField(new BooleanFieldEditor(DParams.KEY_REFACTOR_NAMES,
				DParams.LABEL_REFACTOR_NAMES, getFieldEditorParent()));

		addField(new BooleanFieldEditor(DParams.KEY_FILTER_TEST_PROJECTS,
				DParams.LABEL_FILTER_TEST_PROJECTS, getFieldEditorParent()));

		addField(new BooleanFieldEditor(DParams.KEY_SAVE_EMF_TRANSFO,
				DParams.LABEL_SAVE_EMF_TRANSFO, getFieldEditorParent()));
		addField(new BooleanFieldEditor(
				DParams.KEY_SAVE_GENERATED_ANNOTATIONS_IF_DIFFERENT_TARGET,
				DParams.LABEL_SAVE_GENERATED_ANNOTATIONS_IF_DIFFERENT_TARGET,
				getFieldEditorParent()));
		// addField(new
		// BooleanFieldEditor(DParams.KEY_SAVE_M2_,DParams.LABEL_SAVE_M2_,
		// getFieldEditorParent()));
		// addField(new BooleanFieldEditor(DParams.KEY_CHANGE_LANGUAGE_VERSION_,
		// DParams.LABEL_CHANGE_LANGUAGE_VERSION, getFieldEditorParent()));
		addField(new BooleanFieldEditor(DParams.KEY_ADD_MISSING_ANNOTATIONS,
				DParams.LABEL_ADD_MISSING_ANNOTATIONS, getFieldEditorParent()));

		addField(new BooleanFieldEditor(DParams.KEY_DIAGRAPH_AUTO_GENERATION,
				DParams.LABEL_DIAGRAPH_AUTO_GENERATION, getFieldEditorParent()));
		addField(new BooleanFieldEditor(DParams.KEY_EMFGMF_GENERATION_,
				DParams.LABEL_EMFGMF_GENERATION, getFieldEditorParent()));
		/*
		 * addField(new IntegerFieldEditor(
		 * DParams.KEY_DIAGRAPH_LAYOUT_LEFT_OFFSET,
		 * DParams.LABEL_DIAGRAPH_LAYOUT_LEFT_OFFSET, getFieldEditorParent()));
		 * addField(new IntegerFieldEditor(
		 * DParams.KEY_DIAGRAPH_LAYOUT_WIDTH_OFFSET,
		 * DParams.LABEL_DIAGRAPH_LAYOUT_WIDTH_OFFSET, getFieldEditorParent()));
		 * addField(new IntegerFieldEditor(
		 * DParams.KEY_DIAGRAPH_LAYOUT_VERTICAL_OFFSET,
		 * DParams.LABEL_DIAGRAPH_LAYOUT_VERTICAL_OFFSET,
		 * getFieldEditorParent()));
		 */

	}

	public static void getPreferences() {

		throw new RuntimeException("do not use getPreferences()");
		/*
		 * //FP120523 seek directly in preference store TODO instanciate the
		 * DParams class to force Activator loading ???
		 *
		 * IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		 * DParams.resultInAnotherFolder =
		 * store.getBoolean(DParams.KEY_ANOTHER_FOLDER); DParams.saveOld =
		 * store.getBoolean(DParams.KEY_SAVE_OLD); DParams.deleteOldJavaSrc =
		 * store.getBoolean(DParams.KEY_DELETE_OLD_JAVA_SRC);
		 * DParams.targetModelDir =
		 * store.getString(DParams.KEY_TARGET_MODEL_DIR); DParams.savedModelDir
		 * = store.getString(DParams.KEY_SAVED_MODEL_DIR); DParams.megamodelName
		 * = store.getString(DParams.KEY_MEGAMODEL_NAME);
		 *
		 * DParams.allowPatternBasedRefactoring =
		 * store.getBoolean(DParams.KEY_ALLOW_PATTERN_BASED_REFACTORING);
		 * DParams.refactorDuplicateReferences =
		 * store.getBoolean(DParams.KEY_REFACTOR_DUPLICATE_REFERENCES);
		 * DParams.refactorPoorReferences =
		 * store.getBoolean(DParams.KEY_REFACTOR_POOR_REFERENCES);
		 * DParams.refactorNames = store.getBoolean(DParams.KEY_REFACTOR_NAMES);
		 * DParams.filterTestProjects =
		 * store.getBoolean(DParams.KEY_FILTER_TEST_PROJECTS);
		 *
		 * DParams.saveEmfTransfo =
		 * store.getBoolean(DParams.KEY_SAVE_EMF_TRANSFO);
		 * DParams.saveGeneratedAnnotations = store.getBoolean(DParams.
		 * KEY_SAVE_GENERATED_ANNOTATIONS_IF_DIFFERENT_TARGET); DParams.saveM2 =
		 * store.getBoolean(DParams.KEY_SAVE_M2); DParams.changeLanguageVersion
		 * = store.getBoolean(DParams.KEY_CHANGE_LANGUAGE_VERSION);
		 * DParams.addMissingAnnotations =
		 * store.getBoolean(DParams.KEY_ADD_MISSING_ANNOTATIONS);
		 *
		 * DParams.generateAutomatically =
		 * store.getBoolean(DParams.KEY_DIAGRAPH_AUTO_GENERATION);
		 * DParams.generateEmfAndGmf =
		 * store.getBoolean(DParams.KEY_EMFGMF_GENERATION);
		 */

	}

	public static Object getPreference(Class type, String key) {
		if (type == Boolean.class) {
			return Activator.getDefault().getPreferenceStore().getBoolean(key);
		} else if (type == String.class) {
			return Activator.getDefault().getPreferenceStore().getString(key);
		}
		return null;
	}

	// usage
	// org.isoe.diagraph.preferences.getBooleanPreference(DParams.KEY_ADD_MISSING_ANNOTATIONS)
	public static String getStringPreference(String key) {
		return Activator.getDefault().getPreferenceStore().getString(key);
	}

	public static int getIntegerPreference(String key) {
		return Activator.getDefault().getPreferenceStore().getInt(key);
	}

	public static boolean getBooleanPreference(String key) {
		return Activator.getDefault().getPreferenceStore().getBoolean(key);
	}

	@Override
	public boolean performOk() {
		boolean result = super.performOk();
		// does not work when coupled with DParams because DParams is static
		// getPreferences();
		return result;
	}

	@Override
	public void init(IWorkbench workbench) {

	}

	private static void getUserFromRemoteRepository(String email, String password) { // TODO
		userName = null;
		teamName = null;
		teamNameSpace_ = null;
	}

	/*
	 * userName = System.getProperty("user.name"); teamName =
	 * System.getProperty("team.name"); teamNameSpace =
	 * System.getProperty("team.namespace");
	 */

	public static String getUserName__() { // FP130511
		getTeamNamespace();
		if (userName==null)
			userName ="me";
		return userName;
	}

	public static String getTeamName__() { // FP130511
		getTeamNamespace();
		if (teamName==null)
			teamName = DParams.DEFAULT_TEAM_NAME;
		return teamName;
	}


	public static void setEmail(String email) {
		DiagraphPreferences.email = email;
	}

	public static void setPassword(String password) {
		DiagraphPreferences.password = password;
	}
/*
	public static String getTeamNameSpace() {
		return teamNameSpace;
	}*/

	public static String getEmail() {
		return email;
	}

	public static String getPassword() {
		return password;
	}

	public static String getTeamNamespace() { // FP130511
 		String result = teamNameSpace_;
		if (result == null || result.isEmpty()) {
			getUserFromRemoteRepository(email, password);
			connectState=DParams.CONNECTED_TO_REMOTE_REPOSITORY;//"FromOpendsml";
			result = teamNameSpace_;
			if (result == null || result.isEmpty()) {
				getUserDataFromVmArgs();
				result = teamNameSpace_;
				connectState = DParams.CONNECTED_LOCAL_FROM_VMARGS;//"FromVmArgs";
				if (result == null || result.isEmpty()){
					result = getStringPreference(DParams.KEY_TEAM_NAMESPACE);
					connectState = DParams.CONNECTED_LOCAL_FROM_PREFS;//"FromPreferences";
					teamNameSpace_ = result;
				}
			}
		}
		if (result == null){
			teamNameSpace_ = DParams.DEFAULT_TEAM_NAME;
			connectState = DParams.CONNECTED_LOCAL_FROM_HARD;//"HardDefined";
			result = teamNameSpace_;
		}
		return result;
	}





	public static String getConnectState() {
		return connectState;
	}

	private static void getUserDataFromVmArgs() { // FP130511

		/*
		 * set this in eclipse.ini -Duser.name="johndoe"
		 * -Dteam.name="MY team - MY Lab - Nimes (France)"
		 * -Dteam.namespace="johns.team" TODO: get this from the user account on
		 * the opendsml site
		 */

		userName = System.getProperty("user.name");
		teamName = System.getProperty("team.name");
		teamNameSpace_ = System.getProperty("team.namespace");
	}

	public static void logOptions(List<String> logDiagraph) {
		logDiagraph.add("Diagraph options{");
		logDiagraph.add("output options:");
		logDiagraph
				.add("   deleteOldJavaSrc = "
						+ (getBooleanPreference(DParams.KEY_DELETE_OLD_JAVA_SRC_) ? " true"
								: " false"));
		logDiagraph
				.add("   layout at startup = "
						+ (getBooleanPreference(DParams.KEY_LAYOUT_AT_STARTUP) ? " true"
								: " false"));
		logDiagraph.add("   resultInAnotherFolder = "
				+ (getBooleanPreference(DParams.KEY_ANOTHER_FOLDER) ? " true"
						: " false"));
		logDiagraph.add("       targetModelDir = "
				+ getStringPreference(DParams.KEY_TARGET_MODEL_DIR));
		logDiagraph
				.add("   saveOld = "
						+ (getBooleanPreference(getStringPreference(DParams.KEY_SAVE_OLD)) ? " true"
								: " false"));
		logDiagraph.add("       savedModelDir = "
				+ getStringPreference(DParams.KEY_SAVED_MODEL_DIR));
		logDiagraph.add("       UniverseFolder = "
				+ getStringPreference(DParams.KEY_UNIVERSE_FOLDER));

		// String universeName_ =
		// org.isoe.fwk.pathes.Preferences.getUniverseProjectName_();

		// logDiagraph.add("       UniverseName = " + universeName_);

		// if (universeName_.equals("_megamodel"))
		// throw new RuntimeException("universeName error 2");

		// String teamNamespace=getTeamNamespace();

		logDiagraph.add("       teamNamespace = " + getTeamNamespace());
		logDiagraph.add("       userName = " + getUserName__());
		logDiagraph.add("       teamName = " + getTeamName__());

		logDiagraph.add("       initialState = " + getConnectState());

		logDiagraph.add("       instancesName = "
				+ getStringPreference(DParams.KEY_INSTANCES_NAME));

		logDiagraph.add("refactoring options:");
		logDiagraph
				.add("   allowPatternBasedRefactoring = "
						+ (getBooleanPreference(DParams.KEY_ALLOW_PATTERN_BASED_REFACTORING) ? " true"
								: " false"));
		logDiagraph
				.add("       refactorDuplicateReferences = "
						+ (getBooleanPreference(DParams.KEY_REFACTOR_DUPLICATE_REFERENCES) ? " true"
								: " false"));
		logDiagraph
				.add("       refactorPoorReferences = "
						+ (getBooleanPreference(DParams.KEY_REFACTOR_POOR_REFERENCES) ? " true"
								: " false"));
		logDiagraph.add("       refactorNames = "
				+ (getBooleanPreference(DParams.KEY_REFACTOR_NAMES) ? " true"
						: " false"));
		logDiagraph
				.add("       addMissingAnnotations = "
						+ (getBooleanPreference(DParams.KEY_ADD_MISSING_ANNOTATIONS) ? " true"
								: " false"));
		logDiagraph.add("save and filtering options:");
		logDiagraph.add("   save Emf Transformation = "
				+ (getBooleanPreference(DParams.KEY_SAVE_EMF_TRANSFO) ? " true"
						: " false"));
		logDiagraph
				.add("   save Generated Annotations = "
						+ (getBooleanPreference(DParams.KEY_SAVE_GENERATED_ANNOTATIONS_IF_DIFFERENT_TARGET) ? " true"
								: " false"));
		// logDiagraph.add("   saveM2 = "+
		// (getBooleanPreference(DParams.KEY_SAVE_M2_) ? " true": " false"));
		logDiagraph
				.add("   filterTestProjects = "
						+ (getBooleanPreference(DParams.KEY_FILTER_TEST_PROJECTS) ? " true"
								: " false"));
		logDiagraph
				.add("   generate plugins Automatically = "
						+ (getBooleanPreference(DParams.KEY_DIAGRAPH_AUTO_GENERATION) ? " true"
								: " false"));
		logDiagraph
				.add("   "+DParams.LABEL_EMFGMF_GENERATION+" = "
						+ (getBooleanPreference(DParams.KEY_EMFGMF_GENERATION_) ? " true"
								: " false"));
		logDiagraph.add("language version:");
		/*
		 * logDiagraph .add("   changeLanguageVersion = " +
		 * (getBooleanPreference(DParams.KEY_CHANGE_LANGUAGE_VERSION_) ? " true"
		 * : " false"));
		 */
		/*
		 * logDiagraph.add("layout options:"); logDiagraph
		 * .add("   layout left offset = " +
		 * getIntegerPreference(DParams.KEY_DIAGRAPH_LAYOUT_LEFT_OFFSET));
		 * logDiagraph .add("   layout width offset = " +
		 * getIntegerPreference(DParams.KEY_DIAGRAPH_LAYOUT_WIDTH_OFFSET));
		 * logDiagraph .add("   layout vertical offset = " +
		 * getIntegerPreference(DParams.KEY_DIAGRAPH_LAYOUT_VERTICAL_OFFSET));
		 */
		logDiagraph.add("}");
	}
	/*
	 *
	 *
	 * //FP130409 TODO public static void createOptions(ModelingUniverse
	 * modelingUniverse) {
	 *
	 * org.isoe.megamodel.Platform platform =
	 * MegamodelFactory.eINSTANCE.createPlatform();
	 * platform.setName("eclipse-modeling-indigo-SR1-win32 + GMF + ATL");
	 * modelingUniverse.getPlatform().add(platform);
	 *
	 * Location location = MegamodelFactory.eINSTANCE.createLocation();
	 * location.setName(getStringPreference(DParams.KEY_UNIVERSE_NAME));
	 * location.setCoreMegamodel(DParams.DEFAULT_MEGAMODEL_NAME);
	 * location.setInstanceLocation
	 * (getStringPreference(DParams.KEY_INSTANCES_NAME));
	 * location.setMegamodelLocation
	 * (getStringPreference(DParams.KEY_MEGAMODEL_NAME));
	 * location.setUniverseLocation
	 * (getStringPreference(DParams.KEY_UNIVERSE_FOLDER));
	 * platform.setLocationParameters(location);
	 *
	 * Refactoring refactoring= MegamodelFactory.eINSTANCE.createRefactoring();
	 * refactoring.setPatternBased(getBooleanPreference(DParams.
	 * KEY_ALLOW_PATTERN_BASED_REFACTORING));
	 * refactoring.setDuplicateReferences(
	 * getBooleanPreference(DParams.KEY_REFACTOR_DUPLICATE_REFERENCES));
	 * refactoring.setMissingAnnotations(getBooleanPreference(DParams.
	 * KEY_ADD_MISSING_ANNOTATIONS));
	 * refactoring.setNames(getBooleanPreference(DParams.KEY_REFACTOR_NAMES));
	 * refactoring
	 * .setPoorReferences(getBooleanPreference(DParams.KEY_REFACTOR_POOR_REFERENCES
	 * )); refactoring.setTargetLocation(getStringPreference(DParams.
	 * KEY_TARGET_MODEL_DIR));
	 * refactoring.setVersionedLocation(getStringPreference
	 * (DParams.KEY_SAVED_MODEL_DIR));
	 * platform.setRefactoringParameters(refactoring);
	 *
	 * Generation generation = MegamodelFactory.eINSTANCE.createGeneration();
	 * generation
	 * .setConcreteSyntax(getBooleanPreference(DParams.KEY_DIAGRAPH_AUTO_GENERATION
	 * ));
	 * generation.setDiagram(getBooleanPreference(DParams.KEY_EMFGMF_GENERATION
	 * )); platform.setGenerationParameters(generation);
	 *
	 * }
	 */

}
