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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.isoe.diagraph.internal.api.IReferencePattern;
import org.isoe.diagraph.internal.api.IReferencePatternFinder;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.core.EcoreUtilsv0;
import org.isoe.fwk.log.LogConfig;

import org.isoe.diagraph.internal.m2.parser.DAnnotation;
//import org.isoe.fwk.core.IDiagraphRunner;
import org.isoe.diagraph.runner.IDiagraphRunner;
//import org.isoe.extensionpoint.ecoreutils.IEcoreUtils;

/**
 *
 * @author "François.Pfister francois.pfister@gmail.com"
 *
 */
public class ReferencePatternFinder implements IReferencePatternFinder{

	private static final boolean LOG = false;
	private List<IReferencePattern> patterns;// = new ArrayList<IReferencePattern>();
	private IDiagraphRunner runner;




	public ReferencePatternFinder(IDiagraphRunner runner) {
		super();
		this.runner = runner;
	}

	@Override
	public List<IReferencePattern> getPatterns() {
		return patterns;
	}

	/*
	 * looks for poor references, creates links instead
	 */
	@Override
	public void getPoorReferencePatterns(EPackage mmodel) {
		patterns = new ArrayList<IReferencePattern>();
		for (TreeIterator<EObject> it1 = mmodel.eAllContents(); it1.hasNext();) {
			EObject sourceClass = it1.next();
			if (sourceClass instanceof EClass) { // FP120111
				for (TreeIterator<EObject> it2 = mmodel.eAllContents(); it2
						.hasNext();) {
					EObject targetClass = it2.next();
					if (targetClass instanceof EClass) // FP120111
						checkPoorReferencePatterns((EClass) sourceClass, (EClass) targetClass);
				}
			}
		}
	}



	/*
	 * looks for poor references, creates links instead
	 */

	@Override
	public void getBadNamePatterns(EPackage mmodel) {
		patterns = new ArrayList<IReferencePattern>();
		for (TreeIterator<EObject> it2 = mmodel.eAllContents(); it2.hasNext();) {
			EObject o2 = it2.next();
			if (DAnnotation.isDiaObject(o2)) {
				EClass targetClass = (EClass) o2;
				if (DAnnotation.isLinkDefinedInSourceNode_(runner,targetClass)
						|| DAnnotation.isNodeOrLink(runner,targetClass)) { // isDiagraphLink2(targetClass)||
					if (EcoreUtilsv0.findAttribute(targetClass,
							DParams.DEFAULT_NAME) == null) // revoir
						patterns.add(new ReferencePattern(runner,null, targetClass,
								null));
				}
			}
		}
	}


	private void checkPoorReferencePatterns(EClass sourceClass, EClass targetClass) {// FP120111
		EList<EReference> refs = sourceClass.getEReferences(); // FP120111
																// getEAllReferences
		for (EReference ref : refs) {
			IReferencePattern rp = new ReferencePattern(runner,sourceClass,
					targetClass, ref);
			if (rp.isPoorReference()) {
				if (LOG)
					System.out.println("detected poor reference pattern: "
							+ sourceClass.getName() + "-" + ref.getName()
							+ "->" + targetClass.getName());
				patterns.add(rp);
			}
		}
	}


}
