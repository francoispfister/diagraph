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
package org.isoe.diagraph.internal.m2.parser;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.isoe.diagraph.internal.api.IAnnotatedEClass;
import org.isoe.diagraph.internal.api.IAnnotation;
import org.isoe.diagraph.lang.DiagraphKeywords;
import org.isoe.diagraph.runner.IDiagraphRunner;

/**
 *
 * @author pfister
 *
 */
public class DAnnotatedEClass implements IAnnotatedEClass {
	private static final boolean LOG = false;
	private EClass eClass;
	private List<IAnnotation> dAnnotations;
	private IDiagraphRunner runner;

	public DAnnotatedEClass(IDiagraphRunner runner,EClass eclass, List<IAnnotation> annotations) {
		super();
		this.runner = runner;
		this.eClass = eclass;
		this.dAnnotations = annotations;
	}

	@Override
	public EClass getEClass() {
		return eClass;
	}

	@Override
	public List<IAnnotation> geDAnnotations() {
		return dAnnotations;
	}


	private static boolean isNodeOrLink(List<IAnnotation> ans){
		for (IAnnotation an : ans)
			if (an.getLeftHand().equals(DiagraphKeywords.LINK) || an.getLeftHand().equals(DiagraphKeywords.NODE))
				return true;
		return false;
	}

	private static String getElementType(List<IAnnotation> ans){
		for (IAnnotation an : ans)
			if (an.getLeftHand().equals(DiagraphKeywords.LINK) || an.getLeftHand().equals(DiagraphKeywords.NODE))
				return an.getLeftHand();
		return "n-a";
	}


	public static List<IAnnotatedEClass> getAnnotatedClasses(IDiagraphRunner runner,EPackage mmodel,  String[]tokens, String currentView) {
	   DAnnotation.setCurrentView(currentView);
		List<IAnnotatedEClass> result = new ArrayList<IAnnotatedEClass>();
		for (TreeIterator<EObject> it1 = mmodel.eAllContents(); it1.hasNext();) {
			EObject sourceClass = it1.next();
			if (sourceClass instanceof EClass) {
			   List<IAnnotation> ans =null;

			      ans = DAnnotation.findAnnotations(runner,(EClass) sourceClass, tokens); //FP130731w



				if (ans.isEmpty()){
					if (LOG)
					  staticlog(" no annotation for "+((EClass) sourceClass).getName());
				} else
				for (IAnnotation iAnnotation : ans){
					if (LOG)
						staticlog(((EClass) sourceClass).getName()+" "+iAnnotation.getExpression_());
					/*//FP140429
					if (iAnnotation.getExpression_().startsWith(DiagraphKeywords.NODEEQ)) //FP120622
						throw new RuntimeException(DiagraphKeywords.NODE+" annotation currently does not support arguments: "+iAnnotation.getExpression_()+""+((EClass)sourceClass).getName());
					if (iAnnotation.getExpression_().startsWith(DiagraphKeywords.LINKEQ))
						throw new RuntimeException(DiagraphKeywords.LINK+"annotation currently does not support arguments: "+iAnnotation.getExpression_()+""+((EClass)sourceClass).getName());
						*/
				}

				EAnnotation  ean = DAnnotation.getInViewDiagraphAnnotation((EClass) sourceClass);

				if (ean != null && !ans.isEmpty()){
					if (isNodeOrLink(ans) ){
						ean= DAnnotation.getInViewDiagraphAnnotation((EClass) sourceClass);
						String left = ean.getDetails().get(0).getKey().split("=")[0];
						if (!(left.equals(DiagraphKeywords.LINK)||left.equals(DiagraphKeywords.NODE)))
							throw new RuntimeException(getElementType(ans)+" must be the first annotation for "+((EClass) sourceClass).getName()+" !!!!");
					}
					result.add(new DAnnotatedEClass(runner,(EClass) sourceClass, ans));
				}
			}
		}
		return result;
	}

	@Override
	public String toString() {
		String result = "[" + eClass.getName() + "\n";
		for (IAnnotation annotat : dAnnotations)
			result += annotat.getExpression_() + "\n";
		result += "]";
		return result;
	}

	@Override
	public IAnnotation findAnnotation(String key) {
		if (!key.contains(".")) {
			for (IAnnotation iAnnotation : dAnnotations)
				if (key.equals(iAnnotation.getLeftHand()))
					return iAnnotation;
		} else
			for (IAnnotation iAnnotation : dAnnotations)
				if (key.equals(iAnnotation.getLeftHands()))
					return iAnnotation;
		return null;
	}

	@Override
	public boolean references(String key, IAnnotatedEClass other) {
		for (EReference ref : eClass.getEReferences())
			if (this.links(key, ref, other))
				return true;
		return false;
	}


	private boolean links(String key, EReference sref, IAnnotatedEClass other) {
		if (sref.getEReferenceType().equals(other.getEClass())
				&& DAnnotation.findOrCreateAnnotation(runner,eClass, key, sref.getName()) != null) {
			if (LOG)
				clog("key=" + key + " exists:" + eClass.getName()
						+ " ---> " + other.getEClass().getName());
			return true;
		}
		return false;
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	private static void staticlog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

}
