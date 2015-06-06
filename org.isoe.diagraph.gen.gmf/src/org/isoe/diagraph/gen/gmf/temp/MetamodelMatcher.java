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
package org.isoe.diagraph.gen.gmf.temp;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.isoe.diagraph.internal.m2.parser.DAnnotation;
import org.isoe.diagraph.runner.IDiagraphRunner;

public class MetamodelMatcher {
	private static final String USED_BY_ANNOTATION_ID__ = "UsedBy";
	// private static final String NODE = "node";

	// private Collection<EClass> usedClasses_;
	private Collection<EClass> nodeClasses;
	private Collection<EClass> rootClasses;
	private Collection<EClass> requiredClasses;
	private Collection<EStructuralFeature> requiredFeatures;

	private HashMap<ENamedElement, ENamedElement> c2fMapping;
	private HashMap<ENamedElement, ENamedElement> f2cMapping;

	private IDiagraphRunner runner;

	public MetamodelMatcher(IDiagraphRunner runner,EPackage complete, EPackage pruned) {
		nodeClasses = new HashSet<EClass>();
		// usedClasses_ = new HashSet<EClass>();
		rootClasses = new HashSet<EClass>();
		requiredClasses = new HashSet<EClass>();
		requiredFeatures = new HashSet<EStructuralFeature>();

		c2fMapping = new HashMap<ENamedElement, ENamedElement>();
		f2cMapping = new HashMap<ENamedElement, ENamedElement>();

		matchedWith(complete, pruned);
		findRootClasses();
	}

	public boolean isClassRequired(EClass cl) {
		return requiredClasses.contains(cl);
	}

	/*
	 * public boolean isClassUsed(EClass cl) { if(usedClasses_.isEmpty()) {
	 * return requiredClasses.contains(cl); } else { return
	 * usedClasses_.contains(cl); } }
	 */

	public boolean isClassNode(EClass cl) {
		if (nodeClasses.isEmpty()) {
			return requiredClasses.contains(cl);
		} else {
			return nodeClasses.contains(cl);
		}
	}

	public boolean isFeatureRequired(EStructuralFeature feat) {
		return requiredFeatures.contains(feat);
	}

	public boolean isRootClass(EClass cl) {
		return rootClasses.contains(cl);
	}

	private void matchedWith(EClass c, EClass f) {
		if (f != null) {
			requiredClasses.add(c);
			c2fMapping.put(c, f);
			f2cMapping.put(f, c);
			if (DAnnotation.isNode1(runner,f)) //vérifier
				nodeClasses.add(c);
			for (EStructuralFeature next : c.getEStructuralFeatures()) {
				if (next instanceof EAttribute) {
					EAttribute nextC = (EAttribute) next;
					EAttribute nextP = findAttributeInClass(f, nextC.getName());
					matchedWith(nextC, nextP);
				} else if (next instanceof EReference) {
					EReference nextC = (EReference) next;
					EReference nextP = findReferenceInClass(f, nextC.getName());
					matchedWith(nextC, nextP);
				}
			}
		}
	}

	private void matchedWith(EReference c, EReference f) {
		if (f != null) {
			requiredFeatures.add(c);
			c2fMapping.put(c, f);
			f2cMapping.put(f, c);
		}
	}

	private void matchedWith(EPackage c, EPackage f) {
		if (f != null) {
			c2fMapping.put(c, f);
			f2cMapping.put(f, c);
			for (EClassifier cclassifier : c.getEClassifiers()) {
				if (cclassifier instanceof EClass) {
					EClass cclass = (EClass) cclassifier;
					EClass pclass = findClassInPackage(f, cclass.getName());
					matchedWith(cclass, pclass);
				}

				if (cclassifier instanceof EDataType) {
					EDataType cdatatyp = (EDataType) cclassifier;
					EDataType pdatatyp = findDataTypeInPackage(f,
							cdatatyp.getName());
					matchedWith(cdatatyp, pdatatyp);
				}
			}
		}
	}

	private void matchedWith(EAttribute c, EAttribute f) {
		if (f != null) {
			requiredFeatures.add(c);
			c2fMapping.put(c, f);
			f2cMapping.put(f, c);
		}
	}

	private void matchedWith(EDataType c, EDataType f) {
		if (f != null) {
			c2fMapping.put(c, f);
			f2cMapping.put(f, c);

			if (c instanceof EEnum) {
				EEnum enumC = (EEnum) c;
				EEnum enumP = (EEnum) f;

				for (EEnumLiteral nextC : enumC.getELiterals()) {
					EEnumLiteral nextP = enumP.getEEnumLiteral(nextC.getName());
					matchedWith(nextC, nextP);
				}
			}
		}
	}

	private void matchedWith(EEnumLiteral c, EEnumLiteral f) {
		if (f != null) {
			c2fMapping.put(c, f);
			f2cMapping.put(f, c);
		}
	}

	private EClass findClassInPackage(EPackage pkg, String name) {
		for (EClassifier next : pkg.getEClassifiers()) {
			if (next instanceof EClass && name.equals(next.getName()))
				return (EClass) next;
		}
		return null;
	}

	private EDataType findDataTypeInPackage(EPackage pkg, String name) {
		for (EClassifier next : pkg.getEClassifiers()) {
			if (next instanceof EDataType && name.equals(next.getName()))
				return (EDataType) next;
		}
		return null;
	}

	private EAttribute findAttributeInClass(EClass cl, String name) {
		for (EAttribute next : cl.getEAttributes()) {
			if (name.equals(next.getName()))
				return next;
		}
		return null;
	}

	private EReference findReferenceInClass(EClass cl, String name) {
		for (EReference next : cl.getEReferences()) {
			if (name.equals(next.getName()))
				return next;
		}
		return null;
	}

	private void findRootClasses() {
		rootClasses.addAll(requiredClasses);
		for (EStructuralFeature feat : requiredFeatures) {
			if (feat instanceof EReference) {
				EReference ref = (EReference) feat;
				if (ref.isContainment()) {
					EClass contained = ref.getEReferenceType();
					EClass container = ref.getEContainingClass();

					for (EClass reqClass : requiredClasses) {
						if (contained != container) {
							rootClasses.remove(contained);
						}
						if (reqClass.getEAllSuperTypes().contains(contained)) {
							if (reqClass != container) {
								rootClasses.remove(reqClass);
							}
						}
					}
				}
			}
		}
		/*
		 * if(!usedClasses_.isEmpty()) { rootClasses.retainAll(usedClasses_); }
		 */

		if (!nodeClasses.isEmpty()) {
			rootClasses.retainAll(nodeClasses);
		}
	}

	public void diagnose() {
		StringBuffer buf = new StringBuffer();
		for (EClass cl : requiredClasses) {
			buf.append(cl.getName());

			if (!isClassNode(cl)) {
				buf.append(" [NOT NODE]");
			} else
				buf.append(" [NODE]");
			/*
			 * if(!isClassUsed(cl)) { buf.append(" [NOT USED]"); }
			 */
			if (isRootClass(cl)) {
				buf.append(" [ROOT]");
			}

			EClass pClass = (EClass) c2fMapping.get(cl);
			if (!pClass.getName().equals(cl.getName())) {
				buf.append(" (--> " + pClass.getName() + ")");
			}
			buf.append('\n');

			for (EStructuralFeature feat : cl.getEStructuralFeatures()) {
				if (isFeatureRequired(feat)) {
					if (feat instanceof EReference)
						buf.append("  ref: ");
					else
						buf.append("  att: ");
					buf.append(feat.getName());
					buf.append('\n');
				}
			}
		}
		System.out.println(buf.toString());
	}

	public ENamedElement getFootprintCorrespondance(ENamedElement en) {
		return c2fMapping.get(en);
	}

	public ENamedElement getCompleteCorrespondance(ENamedElement en) {
		return f2cMapping.get(en);
	}

}
