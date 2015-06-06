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
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.isoe.diagraph.internal.api.IAnnotatedEClass;
import org.isoe.diagraph.internal.api.IAnnotationPattern;
import org.isoe.diagraph.internal.api.IAnnotationPatternFinder;
import org.isoe.diagraph.internal.m2.parser.DAnnotation;
import org.isoe.diagraph.lang.DiagraphKeywords;
//import org.isoe.fwk.core.IDiagraphRunner;
import org.isoe.diagraph.runner.IDiagraphRunner;
// import org.isoe.fwk.log.LogConfig;

/**
 *
 * @author pfister
 *
 */
public class AnnotationPatternFinder implements IAnnotationPatternFinder {

	private static final boolean LOG = false;
	private List<IAnnotationPattern> patterns = new ArrayList<IAnnotationPattern>();

	private IDiagraphRunner runner;


	public AnnotationPatternFinder(IDiagraphRunner runner) {
		super();
		this.runner=runner;
	}

	@Override
	public List<IAnnotationPattern> getPatterns() {
		return patterns;
	}

	@Override
	public List<IAnnotationPattern> findAnnotationPatterns(
			List<IAnnotatedEClass> annotatedClasses, String key) {
		if (key.equals(DiagraphKeywords.LNK))
			findAnnotationLinkPatternsLNK(annotatedClasses);
		else if (key.equals(DiagraphKeywords.LINK))
			findAnnotationLinkPatternsLINK(annotatedClasses);
		else
			throw new RuntimeException(
					"AnnotationPatternFinder: unknown key !!!!");
		return patterns;
	}
/*
	@Override
	public List<IAnnotationPattern> findAnnotationPatterns(EPackage mmodel,
			String key) {
		if (key.equals(Constants.LNK))
			findAnnotationLinkPatterns1(mmodel);
		//else if (key.equals(Constants.LINK))
	//		findAnnotationLinkPatterns2(mmodel);
		else
			throw new RuntimeException(
					"AnnotationPatternFinder: unknown key !!!!");
		return patterns;
	}

*/

	private void findDiagraphLnkAnnotationPatterns(EClass sourceClass,
			EClass targetClass) {
		EAnnotation srcan = DAnnotation.getInViewDiagraphAnnotation(sourceClass);
		//EAnnotation srcan_ = Constants.getDiagraphAnnotation(sourceClass,Constants.DIAGRAPH_DEFAULT_VIEW_LITTERAL);
		//EAnnotation targan_ = Constants.getDiagraphAnnotation(targetClass,Constants.DIAGRAPH_DEFAULT_VIEW_LITTERAL);
		EAnnotation targan = DAnnotation.getInViewDiagraphAnnotation(targetClass);
		if (targan != null && srcan != null) {
			EList<EReference> refs = sourceClass.getEReferences();
			for (EReference ref : refs) {
				if (ref.getEReferenceType().equals(targetClass)
						&& DAnnotation.findOrCreateAnnotation(runner,sourceClass,
								DiagraphKeywords.LNK, ref.getName()) != null) {
					if (LOG)
						System.out.println("link exists:"
								+ sourceClass.getName() + " ---> "
								+ targetClass.getName());
					patterns.add(new AnnotationPattern(null, null, sourceClass,
							targetClass, DiagraphKeywords.LNK));
				}
			}
		}
	}


/*
	@Override
	public List<IAnnotationPattern> findAllAnnotationPatterns(EPackage mmodel) {
		List<IAnnotatedEClass> ancls = DAnnotatedEClass.findAllAnnotatedEClass(
				mmodel, Constants.VALID_TOKENS);
		for (IAnnotatedEClass annotatedEClass : ancls) {
			EClass sourceClass = annotatedEClass.getEClass();
			patterns.add(new AnnotationPattern(annotatedEClass, null, null,
					null, null));
		}
		return patterns;
	}
*/





	private void findAnnotationLinkPatternsLNK(
			List<IAnnotatedEClass> annotatedClasses) {
		for (IAnnotatedEClass sourceAClass : annotatedClasses)
			for (IAnnotatedEClass targetAClass : annotatedClasses)
				if (sourceAClass.references(DiagraphKeywords.LNK,targetAClass))
					patterns.add(new AnnotationPattern(sourceAClass,
							targetAClass, null, null, DiagraphKeywords.LNK));
	}


	private void findAnnotationLinkPatternsLINK(
			List<IAnnotatedEClass> annotatedClasses) {
		for (IAnnotatedEClass sourceAClass : annotatedClasses)
			for (IAnnotatedEClass targetAClass : annotatedClasses)
				if (sourceAClass.references( DiagraphKeywords.LINK_TARGET,targetAClass))
					patterns.add(new AnnotationPattern(sourceAClass,
							targetAClass, null, null, DiagraphKeywords.LINK));
	}

	private List<IAnnotationPattern> findAnnotationLinkPatterns1(EPackage mmodel) {
		for (TreeIterator<EObject> it1 = mmodel.eAllContents(); it1.hasNext();) {
			EObject sourceClass = it1.next();
			if (sourceClass instanceof EClass) {
				for (TreeIterator<EObject> it2 = mmodel.eAllContents(); it2
						.hasNext();) {
					EObject targetClass = it2.next();
					if (targetClass instanceof EClass)
						findDiagraphLnkAnnotationPatterns((EClass) sourceClass,
								(EClass) targetClass);
				}
			}
		}
		return patterns;
	}

	/*
	@Override
	public List<EClass> getAnnotatedClasses(EPackage mmodel) {
		List<EClass> annotateds = new ArrayList<EClass>();
		for (TreeIterator<EObject> it = mmodel.eAllContents(); it.hasNext();) {
			EObject o = it.next();
			if (o instanceof EClass && isDiagraphAnnotated((EClass) o))
				annotateds.add((EClass) o);
		}
		return annotateds;
	}
	*/

	private boolean isDiagraphAnnotated(EClass claz) {
		return DAnnotation.getDiagraphAnnotation(claz,DiagraphKeywords.DIAGRAPH_DEFAULT) != null;
	}

}
