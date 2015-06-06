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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
//import org.isoe.diagraph.ecoreutils.IEcoreUtils;
import org.isoe.diagraph.internal.api.IReferencePattern;
import org.isoe.diagraph.internal.m2.parser.DAnnotation;
import org.isoe.diagraph.views.ViewConstants;
import org.isoe.fwk.core.EcoreUtilsv0;
//import org.isoe.fwk.core.IDiagraphRunner;
import org.isoe.diagraph.runner.IDiagraphRunner;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.log.LogConfig;


public class ReferencePattern implements IReferencePattern {

	private static final boolean LOG = false;
	private EClass sourceClass;
	private EClass targetClass;
	private EReference reference;
	private IDiagraphRunner runner;





	public ReferencePattern(IDiagraphRunner runner,EClass eSourceClass, EClass eTargetClass,
			EReference eReference) {
		this.runner = runner;
		this.sourceClass = eSourceClass;
		this.targetClass = eTargetClass;
		this.reference = eReference;
	}

	@Override
	public EClass getSourceEClass() {
		return sourceClass;
	}

	@Override
	public EClass getTargetEClass(){
		return targetClass;
	}

	@Override
	public EReference getEReference() {
		return reference;
	}

	@Override
    public boolean isPoorReference() {
		return DAnnotation.isDiaObject(sourceClass)
				&& DAnnotation.isDiaObject(targetClass)
				&& reference.getEReferenceType() == targetClass
				&& !reference.isContainment() && DAnnotation.isReference(runner,reference)
				&& !DAnnotation.isContainer(runner,sourceClass)
				&& DAnnotation.isNode1(runner,sourceClass)
				&& DAnnotation.isNode1(runner,targetClass);
	}


	private void setNameable(EClass namedClass, EClass eClass) {
		if (namedClass == null) {
		   EcoreUtilsv0.createAttribute(eClass,
               EcorePackage.eINSTANCE.getEString(), DParams.DEFAULT_NAME); //FP130723
			//EcoreUtils.createAttribute(eClass,
					//EcorePackage.eINSTANCE.getEString(), DParams.DEFAULT_NAME);
			// TODO ajouter l'annotation label=
			return;
		}
		if (!namedClass.isSuperTypeOf(eClass))
			eClass.getESuperTypes().add(namedClass);
	}


	@Override
	public void applySemanticLinkPattern(EClass namedClass) {
		if (LOG)
			System.out.println("refactoring " + sourceClass.getName() + "-"
					+ reference.getName() + "->" + targetClass.getName());
		String oldrefname = reference.getName();
		int oldrefcard = reference.getUpperBound();
		EcoreUtilsv0.removeReference(reference);//FP130723
		org.isoe.diagraph.internal.m2.parser.DAnnotation.removeDetail(runner,sourceClass, DAnnotation.REFERENCE, oldrefname);
		EClass linkClass = EcoreUtilsv0.createClass(sourceClass.getEPackage(),
				sourceClass.getName() + "_" + targetClass.getName());//FP130723
		EcoreUtilsv0.createReference(sourceClass, linkClass,
				oldrefname + "_", 0,oldrefcard, true);//FP130723
		setNameable(namedClass, linkClass);
		EcoreUtilsv0.createReference(linkClass, targetClass,
				oldrefname + "_", 0,1, false);//FP130723
		DAnnotation.addSourceAnnotation(linkClass);
		DAnnotation.createEntry(sourceClass, DAnnotation.LNK, oldrefname + "_",DAnnotation.getCurrent_View());
		DAnnotation.createEntry(sourceClass, org.isoe.diagraph.views.ViewConstants.VIEW, DAnnotation.getCurrent_View(),DAnnotation.getCurrent_View());
	}





}
