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
package org.isoe.diagraph.checker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.isoe.diagraph.internal.api.IAnnotatedEClass;
import org.isoe.diagraph.internal.api.IAnnotationPattern;
import org.isoe.diagraph.internal.api.IAnnotationPatternFinder;
import org.isoe.diagraph.internal.api.IDiagraphParameters;
import org.isoe.diagraph.internal.api.IMetamodelChecker;
import org.isoe.diagraph.internal.api.IReferencePattern;
import org.isoe.diagraph.internal.api.IReferencePatternFinder;
import org.isoe.fwk.core.EcoreUtilsv0;
import org.isoe.fwk.log.LogConfig;

import org.isoe.diagraph.internal.m2.parser.DAnnotation;
//import org.isoe.fwk.core.IDiagraphRunner;
import org.isoe.diagraph.runner.IDiagraphRunner;


//org.isoe.diagraph.checker.MetamodelChecker

/**
 *
 * @author pfister
 *
 */
public class MetamodelChecker implements IMetamodelChecker, IDiagraphParameters {

	private static final boolean LOG = false;
	private EPackage metaModel;
	private IDiagraphRunner runner;
	private boolean refactorDupplicateReferences_;
	private boolean refactorPoorReferences;
	private boolean refactorNames;
	private boolean refactorVersion_;
	private boolean refactorMissingAnnotations;
	private boolean overrideDefaults;


	private IAnnotationPatternFinder annotationPatternFinder = new AnnotationPatternFinder(runner);
	private IReferencePatternFinder referencePatternFinder = new ReferencePatternFinder(runner);




	public boolean isRefactorMissingAnnotations() {
		return refactorMissingAnnotations;
	}

	@Override
	public boolean isRefactorDupplicateReferences() {
		return refactorDupplicateReferences_;
	}

	@Override
	public boolean isRefactorPoorReferences() {
		return refactorPoorReferences;
	}

	@Override
	public boolean isRefactorNames() {
		return refactorNames;
	}

	@Override
	public boolean isOverrideDefaults() {
		return overrideDefaults;
	}

	public MetamodelChecker(EPackage mmodel, IDiagraphRunner runner) {
		this.metaModel = mmodel;
		this.runner = runner;
		runner.setRunParameters(this);
	}

	public void checkReferenceInDiaNodes() {
		for (TreeIterator<EObject> it1 = metaModel.eAllContents(); it1
				.hasNext();) {
			EObject o = it1.next();
			if (DAnnotation.isDiaObject(o))
				DAnnotation.checkReferenceInDiaNode(runner,(EClass) o);
		}
	}



	@Override
	public List<IAnnotationPattern> getAnnotationLinkPatterns(
			List<IAnnotatedEClass> annotatedClasses) {
		return annotationPatternFinder.findAnnotationPatterns(annotatedClasses, DAnnotation.LINK);
	}



	@Override
	public List<IReferencePattern> getPoorReferencePatterns(EPackage metaModel,
			String[] resourceData) {
		referencePatternFinder.getPoorReferencePatterns(metaModel);
		return referencePatternFinder.getPatterns();
	}


	@Override
	public List<IReferencePattern> getBadNamePatterns(EPackage metaModel,
			String[] resourceData) {
		referencePatternFinder.getBadNamePatterns(metaModel);
		return referencePatternFinder.getPatterns();
	}



	@Override
	public List getBadLinkPatterns() {

		// TODO getBadLinkPatterns

		/*
		linkPatternFinder.getBadLinkPatterns(metamodel);
		if (link.getSourceReference().getName().equals("ns") && link.getTargetReference().getName().equals("nes"))
			tb = true;
		if (link.getSourceReference().eContainer() != link.getTargetReference().eContainer())
			System.out.println("possible mis-construction: "+link.getName());*/
		return null;
	}



	public void checkAllReferences() {
		for (TreeIterator<EObject> it1 = metaModel.eAllContents(); it1
				.hasNext();) {
			EObject o = it1.next();
			if (DAnnotation.isDiaObject(o))
				DAnnotation.checkAllReferences(runner,(EClass) o);
		}
	}

	@Override
	public List<EReference> checkDuplicatReferenceNames() {
		List<EReference> duplicates = new ArrayList<EReference>();
		for (TreeIterator<EObject> it1 = metaModel.eAllContents(); it1
				.hasNext();) {
			EObject o = it1.next();
			if (DAnnotation.isDiaObject(o))
				duplicates.addAll(DAnnotation
						.checkDuplicatReferenceNames(runner,(EClass) o));
		}
		return duplicates;
	}

	// sauvegarder systématiuement
	// voir à la fin si dirty, et supprimer éventuellement le fichier sauvegardé

	@Override
	public EClass findNamed(EPackage pak, String name, String nameLitteral) {
		for (TreeIterator<EObject> it1 = pak.eAllContents(); it1.hasNext();) {
			EObject named = it1.next();
			EAnnotation ean = null;
			if (named instanceof EClass){
			    ean = DAnnotation.getInViewDiagraphAnnotation((EClass) named);
				if (((EClass) named).getName().equals(name)
						&& EcoreUtilsv0.findAttribute((EClass) named, nameLitteral) != null
						&& ean != null)
					return (EClass) named;
			}
		}
		return null;
	}

	private void checkPoorReference_not_used(EClass sourceClass, EReference reference) {
		DAnnotation.checkDetail(runner,sourceClass, DAnnotation.REFERENCE,
				reference.getName());
	}



	public void readParameterAnnotation() {
		for (EAnnotation parameter : metaModel.getEAnnotations()) {
			if (parameter.getSource().equals("refactor")) {
				if (LOG)
					System.out.println("refactor annotation found ");
				overrideDefaults = true;
				EMap<String, String> details = parameter.getDetails();
				for (Entry<String, String> entry : details) {
					if (entry.getKey().equals("dupref"))
						refactorDupplicateReferences_ = true;
					if (entry.getKey().equals("pooref"))
						refactorPoorReferences = true;
					if (entry.getKey().equals("name"))
						refactorNames = true;
					if (entry.getKey().equals("annotation.missing"))
						refactorMissingAnnotations = true;
				}
				break;
			}
		}
	}




	public void checkMetamodel() {
		readParameterAnnotation();
		checkReferenceInDiaNodes();
		checkAllReferences();
		List<EReference> duplicates = checkDuplicatReferenceNames();

	}
/*
	@Override
	public void applyChangeLitteral(EClass annotated, String newLitteral) {
		DAnnotation.changeLitteral(annotated, newLitteral);
	}
*/









}
