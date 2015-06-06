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
package org.isoe.diagraph.refactoring;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
//import org.isoe.extensionpoint.ecoreutils.IEcoreUtils;
import org.isoe.diagraph.internal.api.IAnnotatedEClass;
import org.isoe.diagraph.internal.api.IAnnotationPattern;
import org.isoe.diagraph.internal.api.IMetamodelChecker;
import org.isoe.diagraph.internal.api.IReferencePattern;
import org.isoe.diagraph.internal.m2.parser.DAnnotation;
import org.isoe.diagraph.lang.DiagraphKeywords;
import org.isoe.extensionpoint.transformation.IAbtractTransformation;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.core.EcoreUtilsv0;
//import org.isoe.fwk.core.IDiagraphRunner;
import org.isoe.diagraph.runner.IDiagraphRunner;
//import org.isoe.fwk.model.transformation.AbtractTransformation;
import org.isoe.fwk.utils.ResourceManager;
import org.isoe.diastyle.lang.StyleConstants;
import org.isoe.diagraph.generic.GenericConstants;





//org.isoe.diagraph.refactoring.MetamodelRefactoring

/**
 *
 * @author pfister Refactoring Ecore models using patterns
 */
public class MetamodelRefactoring {

	private static final boolean LOG = false;
	// private ResourceSet resourceSet;
	private EPackage metaModel;
	private String[] resourceData;

	private IMetamodelChecker checker;

	private boolean refactorDuplicateReferences = false;// DiagraphPreferences.getBooleanPreference(DParams.KEY_REFACTOR_DUPLICATE_REFERENCES);//DParams.refactorDuplicateReferences;
	private boolean refactorPoorReferences_ = false;// DiagraphPreferences.getBooleanPreference(DParams.KEY_REFACTOR_POOR_REFERENCES);//DParams.refactorPoorReferences;
	private boolean refactorNames = false;// DiagraphPreferences.getBooleanPreference(DParams.KEY_REFACTOR_NAMES);//DParams.refactorNames;
	private boolean changeLanguageVersion = false;// DiagraphPreferences.getBooleanPreference(DParams.KEY_CHANGE_LANGUAGE_VERSION);//DParams.changeLanguageVersion;
	private boolean refactorMissingAnnotations = false;// DiagraphPreferences.getBooleanPreference(DParams.KEY_ADD_MISSING_ANNOTATIONS);//DParams.addMissingAnnotations;

	private boolean patternBasedRefactoring = false;// =
													// DiagraphPreferences.getBooleanPreference(DParams.KEY_ALLOW_PATTERN_BASED_REFACTORING);

	private List<IAnnotatedEClass> annotatedClasses;
	// FP120324x
	private IAbtractTransformation transformation;

	private List<IReferencePattern> poorReferences;
	private List<IReferencePattern> renames;
	private List<EReference> duplicates;
	private List badLinkPatterns;
	private IDiagraphRunner runner;

	public MetamodelRefactoring(IDiagraphRunner runner,EPackage mmodel, String[] resourceData,
			IMetamodelChecker checker, List<IAnnotatedEClass> annotatedClasses,
			IAbtractTransformation transformation) {// ,AbtractTransformation
													// transformation)
													// {//FP120324x
		super();
		// FP120324x
		this.runner = runner;
		this.transformation = transformation;
		this.checker = checker;
		this.metaModel = mmodel;
		this.resourceData = resourceData;
		this.annotatedClasses = annotatedClasses;
	}

	private void completePackageAnnotationsNullValuesToNullValue() {
		for (EAnnotation eAnnotation : metaModel.getEAnnotations()) {
			String src = eAnnotation.getSource();
			System.out.println(src);
			EMap<String, String> details = eAnnotation.getDetails();
			Iterator<Entry<String, String>> iterator = details.iterator();
			while (iterator.hasNext()) {
				Entry entry = iterator.next();
				if (entry.getValue() == null)
					entry.setValue(DParams.NULL_ANNOTATION_VALUE);
			}
		}
	}
	/*

	private void refactorLanguage_nu_(boolean changeTargetFolder,
			List<String> logs, boolean save) {
		if (save)
			ResourceManager.setParams(changeTargetFolder, resourceData);
		doRefactorLanguage_obsol(changeTargetFolder, logs);
		completePackageAnnotationsNullValuesToEmpty();
		try {
			if (save)
				ResourceManager.save(resourceData, resourceData[0], metaModel);
		} catch (IOException e) {
			throw new RuntimeException(
					"error while saving refactored version !!!!");
		}
		ResourceManager.setVersionChanged(true);

	}
*/
	private void doRefactorLanguage_obsol(boolean changeTargetFolder,
			List<String> logs) {
		// TODO Auto-generated method stub

	}

	public void refactorLanguageXOrModels(boolean changeTargetFolder,
			List<String> logs, boolean save) {
		/*
		if (changeLanguageVersion)
			refactorLanguage(changeTargetFolder, logs, save);
		else*/
			refactor(changeTargetFolder, save);
	}

	public boolean refactorIsOverride() {
		if (checker.isOverrideDefaults()) {
			patternBasedRefactoring = true; // overrides defaut set in
											// preferences
			refactorDuplicateReferences = checker
					.isRefactorDupplicateReferences();
			refactorNames = checker.isRefactorNames();
			refactorPoorReferences_ = checker.isRefactorPoorReferences();
			refactorMissingAnnotations = checker.isRefactorMissingAnnotations();
			// changeLanguageVersion = checker.isRefactorVersion_();
		}
		return patternBasedRefactoring || changeLanguageVersion;
	}

	private boolean mustRefactor() {
		poorReferences = checker.getPoorReferencePatterns(metaModel,
				resourceData);
		renames = checker.getBadNamePatterns(metaModel, resourceData);
		duplicates = checker.checkDuplicatReferenceNames();

		badLinkPatterns = checker.getBadLinkPatterns();
		/*
		 * for (EReference eReference : duplicates) {
		 * System.out.println(eReference.getName()); }
		 */
		// if (duplicates.size()>0) { //revoir cet algo. Des besoins peuvent
		// apparaitre ou disparaitre en cours de route...
		return ((refactorDuplicateReferences && poorReferences.size() > 0)
				|| (refactorNames && renames.size() > 0)
				|| (refactorPoorReferences_ && duplicates.size() > 0) || (refactorMissingAnnotations));
	}

	private void refactor(boolean changeTargetFolder, boolean save) {
		if (mustRefactor()) {
			if (save)
				ResourceManager.setParams(changeTargetFolder, resourceData);
			if (refactorMissingAnnotations)
				if (DAnnotation.refactorAnnotations(runner,metaModel))
					;
			if (refactorPoorReferences_ && poorReferences.size() > 0)
				applySemanticLinkPatterns(poorReferences);
			if (refactorNames) {
				renames = checker.getBadNamePatterns(metaModel, resourceData);
				if (renames.size() > 0)
					refactorNames(renames);
			}
			if (refactorDuplicateReferences) {
				duplicates = checker.checkDuplicatReferenceNames();
				if (duplicates.size() > 0)
					refactorDuplicateReferences_obsolete(duplicates); // FP120320 à
																// regarder
			}
			if (save) {
				removeRefactorAnnotation();
				saveRefactoredMetaModel();
			}
		}
	}

	// FP120623b
	private void saveRefactoredMetaModel() {
		try {
			ResourceManager.save(resourceData, resourceData[0], metaModel);
			org.isoe.fwk.utils.debug.WorkspaceUtils.getInstance().refreshProject(resourceData[2],false);
		} catch (IOException e) {
			throw new RuntimeException(
					"error while saving refactored meta-model !!!!");
		}
	}



	private void removeRefactorAnnotation() {
		// TODO removeRefactorAnnotation
	}

	private void refactorLitteral(String oldLitteral, String newLitteral,
			List<String> logs) {
		for (IAnnotatedEClass annotatedEClass : annotatedClasses)
			DAnnotation.changeLitteral(annotatedEClass.getEClass(),
					oldLitteral, newLitteral, logs);
	}

	/*

	private void doRefactorLanguage(boolean changeTargetFolder,
			List<String> logs) {

		refactorLitteral(DiagraphKeywords.OLD_ANNOT_LITERAL,
				Constants.ANNOT_DIAGRAPH_LITTERAL_, logs);
		refactorEntry(DiagraphKeywords.OLD_POINT_OF_VIEW, Constants.POINT_OF_VIEW,
				logs);
		refactorEntry(DiagraphKeywords.OLD_REFERENCE, Constants.REFERENCE, logs);
		refactorEntry(DiagraphKeywords.OLD_CONTAINMENT, Constants.CONTAINMENT,
				logs);
		refactorEntry(DiagraphKeywords.OLD_LINK_SOURCE, Constants.LINK_SOURCE,
				logs);
		refactorEntry(DiagraphKeywords.OLD_LINK_TARGET, Constants.LINK_TARGET,
				logs);
		refactorEntry(DiagraphKeywords.OLD_IN_COMPARTMENT,
				Constants.IN_COMPARTMENT, logs);
		completeNullValuesToEmpty();
	}
	*/

	private void completeNullValuesToNullValue() {
		for (IAnnotatedEClass annotatedEClass : annotatedClasses)
			DAnnotation.completeNullValuesToNullValue(annotatedEClass.getEClass());
	}

	private void refactorEntry(String oldKey, String newKey, List<String> logs) {
		for (IAnnotatedEClass annotatedEClass : annotatedClasses)
			DAnnotation.changeKey(annotatedEClass.getEClass(), oldKey, newKey,
					logs);
	}

	private void refactorAnnotationLinks() {
		for (IAnnotationPattern patrn : checker
				.getAnnotationLinkPatterns(annotatedClasses))
			patrn.applyChangeAnnotationLinkPattern2b();
	}
/*
	private void refactorDuplicateReferences_new_nu(List<EReference> dups) {
		List<EReference> duplicates = checker.checkDuplicatReferenceNames();
		for (EReference dupref : duplicates) {
			String oldName = dupref.getName();
			String nexname = EcoreUtils.renameReference_(dupref);
			DAnnotation.renameAnnotation_new_nu(runner,dupref, oldName);
		}
	}*/

	private void refactorDuplicateReferences_obsolete(List<EReference> dups) {
		List<EReference> duplicates = checker.checkDuplicatReferenceNames();
		for (EReference dupref : duplicates) {
			String oldName = dupref.getName();
			String nexname = EcoreUtilsv0.renameReference_(dupref);
			DAnnotation.renameAnnotation_obsolete(runner,dupref, oldName);
		}
	}

	// sauvegarder systématiuement
	// voir à la fin si dirty, et supprimer éventuellement le fichier sauvegardé

	private EClass applyGenericNamedPattern(EPackage pak, String name) {
		EClass result = checker.findNamed(pak, name, DParams.DEFAULT_NAME);
		if (result == null)
			result = createNamed(pak, name,null);//FP121015a
		return result;
	}

	private void applySemanticLinkPatterns(List<IReferencePattern> ref) {
		EClass namedClass = applyGenericNamedPattern(
				ref.get(0).getSourceEClass().getEPackage(), DParams.NAMED_NODE);
		for (IReferencePattern referencePattern : ref)
			referencePattern.applySemanticLinkPattern(namedClass);
	}

	/*
	 * private void refactorMissingAnnotations(EPackage mmodel) { //if
	 * (!DParams.changeLanguageVersion);
	 * DAnnotation.refactorAnnotations(mmodel); }
	 */

	private void refactorNames(List<IReferencePattern> data) {
		EClass namedClass = applyGenericNamedPattern(data.get(0).getTargetEClass()
				.getEPackage(), DParams.NAMED_NODE);
		for (IReferencePattern refactorData : data)
			setNameable(namedClass, refactorData.getTargetEClass());
	}

	private EClass createNamed(EPackage pak, String name, String view) {//FP140204ok
		EClass namedClass = EcoreUtilsv0.createClass(pak, name);
		EcoreUtilsv0.createAttribute(namedClass,
				EcorePackage.eINSTANCE.getEString(), DParams.DEFAULT_NAME);
		if (view==null || view.isEmpty())
			view = org.isoe.diagraph.views.ViewConstants.DIAGRAPH_DEFAULT;
		DAnnotation.createAnnotationAndDetail(namedClass, DiagraphKeywords.LABEL,
				DParams.DEFAULT_NAME, view);
		return namedClass;
	}

	private void setNameable(EClass namedClass, EClass eClass) {
		if (namedClass == null) {
		   EcoreUtilsv0.createAttribute(eClass,
					EcorePackage.eINSTANCE.getEString(), DParams.DEFAULT_NAME);
			return;
		}
		if (!namedClass.isSuperTypeOf(eClass))
			eClass.getESuperTypes().add(namedClass);
	}

}
