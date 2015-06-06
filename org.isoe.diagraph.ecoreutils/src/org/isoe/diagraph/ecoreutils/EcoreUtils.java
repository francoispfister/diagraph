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
package org.isoe.diagraph.ecoreutils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.isoe.diagraph.generic.GenericConstants;
import org.isoe.diagraph.lang.DiagraphKeywords;
import org.isoe.diagraph.views.ViewConstants;
import org.isoe.extensionpoint.ecoreutils.IEcoreUtils;
import org.isoe.fwk.core.DParams;

import org.isoe.extensionpoint.parsers.IRuntimeDiagraphParser;
import org.isoe.diastyle.lang.StyleConstants;





/**
 *
 * @author pfister
 */
public class EcoreUtils implements IEcoreUtils {

	private final static boolean LOG = DParams.EcoreUtils_LOG;
	private final static boolean DEV_THESIS = true;

	private Map<String, EClassifier> clones = new HashMap<String, EClassifier>();
	private EPackage targetPack = null, cloneRoot = null;

	// private EClass result_;
	// private boolean merge;
	// private static IEcoreUtils instance;

	// private EPackage sourcePackage;
	// private EPackage targetPackage;

	/*---------------------------*/

	private String ecoreName(EClass c) {
		String uri = c.getEPackage().eResource().getURI().toString();
		uri = uri.substring(uri.lastIndexOf("/") + 1);
		uri = uri.substring(0, uri.indexOf("."));
		return uri;
	}


	private void mergeAnnotationsWhenExistesInTarget() {
		// TODO do not handle here but in a visitor event
	}


	@Override
	public void cloneClassWithAttributesAndAnnotations(EClass toMerge) {

		EClassifier cl = null;
		cl = clones.get(toMerge.getName());
		if (cl != null) {
			if (!(cl instanceof EClass))
				throw new RuntimeException(
						"should not happen in cloneClassWithAttributes");
			return;
		}
		EClassifier fit = findEClassInTarget(toMerge.getName()); // connect to														// the
		if (LOG && fit != null)
			clog(toMerge.getName() + " also found in target ("
					+ fit.getEPackage().eResource().getURI().toString() + ")");
		EClass result= cloneAndRegister(toMerge);
		if (result == null)
			if (LOG)
				clog(toMerge.getName()+" not cloned");
	}


	private EClass cloneAndRegister(EClass claz) {
		if (LOG){
			clog("CANDR cloneAndRegister " + claz.getName());
		}
		EClass result = cloneEClass(claz);
		if (result != null) {
			if (LOG)
				clog("******** cloned " + result.getName());
			// if (!merge)
			cloneRoot.getEClassifiers().add(result);
			clones.put(result.getName(), result);
		}
		return result;
	}

	private boolean isReferenceExcluded(String relation) {
		if (!DEV_THESIS)
			return false;

		String[] DPSE_REF_EXCLUDE = { "___Function.decompositions.Function",
				"Foo.refto.Bar" }; //read as parameters

		for (String excluded : DPSE_REF_EXCLUDE) {
			if (relation.equals(excluded))
				return true;
		}
		return false;
	}

	private boolean isClassExcluded(String name) {
		if (!DEV_THESIS)
			return false;

		String[] DPSE_CLASS_EXCLUDE = { "___Function",
				"Foo","Bar" }; //read as parameters

		for (String excluded : DPSE_CLASS_EXCLUDE) {
			if (name.equals(excluded))
				return true;
		}
		return false;
	}


	private boolean isEntryExcluded(String relation) {
		if (!DEV_THESIS)
			return false;
		// private
		String[] DPSE_ANNOT_EXCLUDE_ = { "[diagraph]TotoTiti.default.node",
				"dum" };//read as parameters
		for (String excluded : DPSE_ANNOT_EXCLUDE_) {
			if (relation.equals(excluded))
				return true;
		}
		return false;
	}

	private String getTransformed(String req) {
		if (DEV_THESIS) {
			// private
			String[] DPSE_ANNOT_TRAF_ = {//read as parameters
					"[diagraph]TotoTiti.default=TotoTiti.functionalView",
					"[diagraph]FunctionKind.default.node=view:foobar" };
			for (String trafo : DPSE_ANNOT_TRAF_) {
				String t[] = trafo.split("=");
				if (t[0].equals(req))
					return t[1];
			}
		}
		return req;
	}

	private String annotationToString(EAnnotation sourceAnnotation) {
		String result = "-------------------\n";
		result += sourceAnnotation.getSource() + "\n";
		for (Entry<String, String> orentry : sourceAnnotation.getDetails()) {
			result += "  " + orentry.getKey() + "="
					+ (orentry.getValue() == null ? "" : orentry.getValue())
					+ "\n";
		}
		result += "-------------------";
		return result;
	}

	@Override
	public void clonePackageAnnotations(EPackage copyPack, EPackage originalPack) {
		if (LOG)
			clog("clonePackageAnnotations " + originalPack.getName());

		for (EAnnotation sourceAnnotation : originalPack.getEAnnotations()) {
			String sourceAnotsrc = sourceAnnotation.getSource();
			EAnnotation cloned = clonePackageAnnot(sourceAnnotation,
					originalPack);
			copyPack.getEAnnotations().add(cloned);
			if (LOG)
				clog("-------------------\n" + "cloned annotation\n"
						+ annotationToString(cloned));

		}
	}

	@Override
	public void cloneAnnotations(EClass clone, EClass original) {// ,boolean
																	// mergingEClass
		if (LOG)
			clog("cloneAnnotations " // + (mergingEClass ? " merge " :
										// " clone ")
					+ original.getName());

		try {
			clog("copy " + clone.getEPackage().eResource().getURI().toString());
		} catch (Exception e) {
			clog("copy " + clone.getEPackage().getName() + " no resource");
		}

		clog("original "
				+ original.getEPackage().eResource().getURI().toString());

		for (EAnnotation sourceAnnotation : original.getEAnnotations()) {
			// String source=orig.getSource();
			String view = getView(sourceAnnotation, original);
			String sourceAnotsrc = sourceAnnotation.getSource();
			EAnnotation copyAnnotation_ = findAnnotation(original,
					sourceAnotsrc, view);
			// EAnnotation cloned = null;
			/*
			 * if (mergingEClass && copyAnnotation_ != null) { cloned =
			 * mergeAnnot_nu(view, sourceAnnotation, copyAnnotation_, original);
			 * if (LOG) clog("-------------------\n" + "merged annotation\n" +
			 * annotationToString(cloned));
			 *
			 * } else
			 */{
				EAnnotation cloned = cloneAnnot(view, sourceAnnotation,
						original);
				clone.getEAnnotations().add(cloned);
				if (LOG)
					clog("-------------------\n" + "cloned annotation\n"
							+ annotationToString(cloned));
			}
		}
	}

	private EAnnotation findAnnotation(ENamedElement copy,
			String sourceAnotsrc, String origview) {
		for (EAnnotation copyAnnotation : copy.getEAnnotations())
			if (getView(copyAnnotation, copy).equals(origview)
					&& copyAnnotation.getSource().equals(sourceAnotsrc))
				return copyAnnotation;
		return null;
	}

	private String getView(EAnnotation originalAnnotation,
			ENamedElement original) {
		String view = "";
		if (original instanceof EClass) {
			view = "default";

			for (Entry<String, String> srcdets : originalAnnotation
					.getDetails()) {
				if (srcdets.getKey().startsWith("view=")) {
					String[] p = srcdets.getKey().split("=");
					view = p[1];
				}
			}
		}
		return view;
	}

	private String getView(EAnnotation originalAnnotation) {
		String view = "default";
		for (Entry<String, String> srcdets : originalAnnotation.getDetails()) {
			if (srcdets.getKey().startsWith("view=")) {
				String[] p = srcdets.getKey().split("=");
				view = p[1];
			}
		}
		return view;
	}

	private EAnnotation clonePackageAnnot(EAnnotation sourceAnnotation,
			EPackage originalPack) {
		String mesg = "cloneAnnotation " + "[" + sourceAnnotation + "]"
				+ originalPack.getName();
		if (LOG)
			clog(mesg);
		EAnnotation cloned = EcoreFactory.eINSTANCE.createEAnnotation();
		cloned.setSource(sourceAnnotation.getSource());
		for (Entry<String, String> orentry : sourceAnnotation.getDetails()) {
			// String relation =
			// clone.getName()+"."+nature+"["+source_+"]"+(view.isEmpty()?"":view+".")+orentry.getKey();
			String req = "[" + sourceAnnotation.getSource() + "]"
					+ originalPack.getName() + "." + orentry.getKey();
			if (LOG)
				clog("cloneAnnot " + req);

			if (!isEntryExcluded(req)) {
				String ent = orentry.getKey();

				String res = getTransformed(req);
				if (!res.equals(req)) {
					String[] t = res.split(":");
					if (t[0].equals("dofoobar")) {
						clog("change foobar to " + t[1]);
					}
				}

				cloned.getDetails().put(ent,
						orentry.getValue() == null ? DParams.NULL_ANNOTATION_VALUE : orentry.getValue());
			}
		}
		return cloned;

	}

	private EAnnotation cloneAnnot(String view, EAnnotation originalAnnotation,
			EClass original) {

		String mesg = "cloneAnnotation " + "[" + originalAnnotation.getSource()
				+ "]" + (view.isEmpty() ? "default" : view + ".")
				+ original.getName();
		if (LOG)
			clog(mesg);

		EAnnotation cloned = EcoreFactory.eINSTANCE.createEAnnotation();

		cloned.setSource(originalAnnotation.getSource());
		for (Entry<String, String> orentry : originalAnnotation.getDetails()) {
			String req = "[" + originalAnnotation.getSource() + "]"
					+ original.getName() + "."
					+ (view.isEmpty() ? "" : view + ".") + orentry.getKey();
			if (LOG)
				clog("cloneAnnot " + req);

			if (!isEntryExcluded(req)) {
				String ent = orentry.getKey();

				String res = getTransformed(req);
				if (!res.equals(req)) {
					String[] t = res.split(":");
					if (t[0].equals("view")) {
						clog("change view to " + t[1]);
					}
				}

				cloned.getDetails().put(ent,
						orentry.getValue() == null ? DParams.NULL_ANNOTATION_VALUE : orentry.getValue());
			}
		}
		return cloned;

	}

	private void completePackageAnnotationsNullValuesToNullValue(EPackage metaModel) {
		for (EAnnotation eAnnotation : metaModel.getEAnnotations()) {
			String src = eAnnotation.getSource();
			// System.out.println(src);
			EMap<String, String> details = eAnnotation.getDetails();
			Iterator<Entry<String, String>> iterator = details.iterator();
			while (iterator.hasNext()) {
				Entry entry = iterator.next();
				if (entry.getValue() == null)
					entry.setValue(DParams.NULL_ANNOTATION_VALUE);
			}
		}
	}

	private Entry<String, String> findEntry(EAnnotation eAnnotation, String k) {
		Iterator<Entry<String, String>> iterator = eAnnotation.getDetails().iterator();
		while (iterator.hasNext()) {
			Entry entry = iterator.next();
			if (entry.getKey().equals(k))
				return entry;
		}
		return null;
	}



	public Map.Entry<String, String> findEntry_nu(EAnnotation an, String key,
			String value_) {
		for (Map.Entry<String, String> entry : an.getDetails()) {
			if (entry.getKey().equals(key) && entry.getValue().equals(value_))
				return entry;
		}
		return null;
	}




	private EAnnotation mergeAnnot_nu(String view, EAnnotation existing_,
			EAnnotation originalAnnotation, EClass original) {
		String mesg = "mergeAnnotation " + "[" + originalAnnotation.getSource()
				+ "]" + (view.isEmpty() ? "default" : view + ".")
				+ original.getName();
		if (LOG)
			clog(mesg);

		for (Entry<String, String> orentry : originalAnnotation.getDetails()) {
			// String relation =
			// clone.getName()+"."+nature+"["+source_+"]"+(view.isEmpty()?"":view+".")+orentry.getKey();
			String orkey = orentry.getKey();
			String req = "[" + originalAnnotation.getSource() + "]"
					+ original.getName() + "."
					+ (view.isEmpty() ? "" : view + ".") + orkey;
			if (LOG)
				clog("mergeAnnot " + req);

			if (!isEntryExcluded(req)) {

				Entry<String, String> existingEntry = findEntry(existing_,
						orkey);

				String res = getTransformed(req);
				if (!res.equals(req)) {
					String[] t = res.split(":");
					if (t[0].equals("view")) {
						clog("change view to " + t[1]);
					}
				}
			    if (existingEntry == null) {
					originalAnnotation.getDetails().put(
							orkey,
							orentry.getValue() == null ? DParams.NULL_ANNOTATION_VALUE : orentry
									.getValue());
					if (LOG)
						clog("merging, entry added: " + orkey);
				} else if (LOG)
					clog("merging, entry not added: " + orkey
							+ " allready exists");
			} else if (LOG)
				clog("merging, but excluded: " + orkey);
		}

		return originalAnnotation;

	}

	private boolean referenceExistsInTarget(EReference reference) {
		EClassifier eType = reference.getEType();
		// EClass eclaz=reference.getEReferenceType();
		// EReference op=reference.getEOpposite();
		EObject eContainer = reference.eContainer();
		EClass sr = getEClass(((EClass) eContainer).getName());
		EClass tg = getEClass(((EClass) eType).getName());
		EReference result = null;
		EClass srexists = (EClass) targetPack.getEClassifier(sr.getName());
		EClass trexists = (EClass) targetPack.getEClassifier(tg.getName());
		if (srexists != null && trexists != null) {
			result = (EReference) findReference(srexists, reference.getName());

			if (result != null){

				return true;
			}
		}
		return false;
	}


	private boolean referenceExists_nu(EClass sr, EClass tg, EReference reference) {
		EReference er = null;
		EClass srexists = (EClass) targetPack.getEClassifier(sr.getName());
		EClass trexists = (EClass) targetPack.getEClassifier(tg.getName());
		if (srexists != null && trexists != null) {
			er = (EReference) findReference(srexists, reference.getName());
			if (er != null)
				return true;
		}
		return false;
	}

	private boolean classExistsInTarget(EClass orig) {
		EClass srexists = (EClass) targetPack.getEClassifier(orig.getName());
		return srexists != null;
	}

	private EClass cloneEClass(EClass orig) {
		EClass targetClaz = findEClassInTarget(orig.getName());
		if (LOG) {
			if (targetClaz == null)
				clog(orig.getName() + " not exists in target meta-model");
			clog("annotation in source:");
			for (EAnnotation eAnnotation : orig.getEAnnotations()) {
				clog(annotationToString(eAnnotation));
			}

			if (targetClaz != null) {
				clog(targetClaz.getName()
						+ " allready exists in target meta-model");
				clog("annotation in target:");
				for (EAnnotation eAnnotation : targetClaz.getEAnnotations()) {
					clog(annotationToString(eAnnotation));
				}
			}
		}
		EClass copy = null;
		boolean classexists_ = classExistsInTarget(orig);
		if (!classexists_  && ! isClassExcluded(orig.getName()) ) {
			copy = createClass(cloneRoot, orig.getName());
			cloneAnnotations((EClass) copy, orig);
			cloneBuildinTypedAttributes_((EClass) copy, orig, false);
			for (EAttribute eAttribute : orig.getEAttributes()) {
				EClassifier dt = eAttribute.getEType(); // TODO handle enums and
														// other
														// datatypes
				if (dt instanceof EEnum) {
					clog("!!! TODO: atribute type is Enum !!!");
					// EcoreUtils.createAttribute(cloned, (EDataType) dt,
					// eAttribute.getName());
				}
			}
		} else
			mergeAnnotationsWhenExistesInTarget();
		return copy;
	}




	@Override
	public EReference cloneEReference(EReference reference) {
		if (reference == null)
			throw new RuntimeException("should not happen - in cloneEReference");
		EReference result = null;
		EClassifier eType = reference.getEType();

		EObject eContainer = reference.eContainer();
		if (eContainer instanceof EClass && eType instanceof EClass) {
			EClass sr = getEClass(((EClass) eContainer).getName());
			EClass tg = getEClass(((EClass) eType).getName());
			String relation = sr.getName() + "." + reference.getName() + "."
					+ tg.getName();
			boolean refex = referenceExistsInTarget(reference);
			if (!isReferenceExcluded(relation)
					&& !refex) {
				result = createReference(sr, tg, reference.getName(),
						reference.getLowerBound(), reference.getUpperBound(),
						reference.isContainment());
				if (LOG)
					clog("cloneEReference " + relation);
			} else
				clog("excluded " + relation);
		}
		return result;
	}


	@Override
	public void cloneInheritance(EClass eClass, EClass eSuperclass) {
		EClass sr = getEClass(eClass.getName());
		EClass tg = getEClass(eSuperclass.getName());
		createInheritance_(sr, tg);
	}

	@Override
	public void init(EPackage target, EPackage source) {
		if (target == null)
			throw new RuntimeException("should not happen in EcoreUtils.init");
		clones = new HashMap<String, EClassifier>();
		this.targetPack = target;
		cloneRoot = EcoreFactory.eINSTANCE.createEPackage();
		cloneRoot.setName("clonecache");
		// cloneRoot is mandatory to avoid concurrent modification of the target
		// package during the visitor iteration
		// will be consolidated at the end of the process
	}


	private void consolidateAnnotations(EClass srcl, EClass trcl) {
		for (EAnnotation an : srcl.getEAnnotations()) {
			EAnnotation anot = findAnnotation(trcl, an);
			if (anot == null)
				trcl.getEAnnotations().add(an);
			else
				consolidateAnnotion(anot, an);
		}
	}

	private void consolidateAnnotion(EAnnotation f, EAnnotation an) {
		EMap<String, String> details = f.getDetails();
		for (Entry<String, String> entry : details) {
			Entry<String, String> existEntry = findEntry(an, entry.getKey());
			if (existEntry == null) {
				f.getDetails().put(entry.getKey(),
						entry.getValue() == null ? DParams.NULL_ANNOTATION_VALUE : entry.getValue());
			}
		}
	}

	private EAnnotation findAnnotation(EClass cl, EAnnotation an) {
		String view = getView(an);
		EList<EAnnotation> ans = cl.getEAnnotations();
		for (EAnnotation ann : ans)
			if (an.getSource().equals(ann.getSource())
					&& view.equals(getView(ann)))
				return ann;
		return null;
	}



	public EReference cloneEReferen(EReference reference) {
		if (reference == null)
			throw new RuntimeException("should not happen - in cloneEReference");
		EReference result = null;
		EClassifier eType = reference.getEType();
		// EClass eclaz=reference.getEReferenceType();
		// EReference op=reference.getEOpposite();
		EObject eContainer = reference.eContainer();
		if (eContainer instanceof EClass && eType instanceof EClass) {
			EClass sr = getEClass(((EClass) eContainer).getName());
			EClass tg = getEClass(((EClass) eType).getName());
			// EClass tg=(EClass) clones.get(((EClass) eType).getName());
			String relation = sr.getName() + "." + reference.getName() + "."
					+ tg.getName();

			if (!isReferenceExcluded(relation)) {
				result = createReference(sr, tg, reference.getName(),
						reference.getLowerBound(), reference.getUpperBound(),
						reference.isContainment());
				if (LOG)
					clog("cloneEReference " + relation);
			} else
				clog("excluded " + relation);
		}
		return result;
	}

	/**
	 * copies the temporary clone package  to the given package
	 */
	@Override
	public void consolide() {
		//do not anything here, everything must be done in the visitor events
		targetPack.getEClassifiers().addAll(cloneRoot.getEClassifiers());
		targetPack.getEAnnotations().addAll(cloneRoot.getEAnnotations());
		cloneRoot.getEClassifiers().clear();
		cloneRoot.getEAnnotations().clear();
		cloneRoot = null;
	}



	private EStructuralFeature findStructuralFeature(EClass claz,
			EStructuralFeature sf) {
		List<EStructuralFeature> sfs = claz.getEStructuralFeatures();
		for (EStructuralFeature sf_ : sfs) {
			if (sf instanceof EAttribute && sf_ instanceof EAttribute
					&& sf.getName().equals(sf_.getName())) {
				return sf_;
			}
		}
		for (EStructuralFeature sf_ : sfs) {
			if (sf instanceof EReference && sf_ instanceof EReference
					&& sf.getName().equals(sf_.getName())) {
				return sf_;
			}
		}
		return null;
	}

	/*--------------------------------------*/

	public EcoreUtils() {
		super();
	}

	public void testLeaf(EPackage domainModel, String classname) {
		List<EClass> leafs = getLeafTypes(domainModel);
		for (EClass leaf : leafs) {
			clog(leaf.getName());
		}
		for (Iterator<EObject> contents = domainModel.eAllContents(); contents
				.hasNext();) {
			EObject el = contents.next();
			if (el instanceof EClass) {
				if (((EClass) el).getName().equals(classname)) {
					List<EClass> le = getLeafTypes((EClass) el);
					for (EClass elc : le) {
						clog(elc.getName());
					}
				}
			}
		}
	}

	public List<EClass> getLeafTypes(EClass claz) {
		List<EClass> result = new ArrayList<EClass>();
		List<EClass> pakcleafs = getLeafTypes(claz.getEPackage());
		for (EClass pakcleaf : pakcleafs)
			if (claz.isSuperTypeOf(pakcleaf))
				result.add(pakcleaf);
		return result;

	}

	public List<EClass> getLeafTypes(EPackage domainModel) {
		List<EClass> result = new ArrayList<EClass>();
		for (Iterator<EObject> contents = domainModel.eAllContents(); contents
				.hasNext();) {
			EObject el = contents.next();
			if (el instanceof EClass) {
				if (getSubTypes((EClass) el).size() == 0)
					result.add((EClass) el);
			}
		}
		return result;
	}

	public List<EClass> getSubTypes(EClass eclass) {
		List<EClass> result = new ArrayList<EClass>();
		EPackage pack = eclass.getEPackage();
		for (Iterator<EObject> contents = pack.eAllContents(); contents
				.hasNext();) {
			EObject el = contents.next();
			if (el instanceof EClass) {
				EClass other = (EClass) el;
				if (eclass.isSuperTypeOf(other) && !result.contains(other)
						&& other != eclass) {
					result.add(other);
				}
			}
		}
		return result;
	}

	public boolean isSuperTypeOf(EClass supe, EClass lower) {
		for (EClass su : lower.getEAllSuperTypes())
			if (su.equals(supe))
				return true;
		return false;
	}

	public void logReferences(EClass claz) {
		if (claz.getEAllReferences().size() == 0)
			clog("no references for " + claz.getName());
		for (EReference r : claz.getEAllReferences())
			clog(r.getName() + "(" + r.getEType().getName() + ")");
	}

	private void createInheritance_(EClass eClass, EClass superclass) {
		clog("createInheritance " + eClass.getName() + " --> "
				+ superclass.getName());
		// eClass.
		eClass.getESuperTypes().add(superclass);
		// sr.getESuperTypes().add(tg);
		// superclass.ge

	}

	@Override
	public EReference createReference(EClass sourceClass, EClass targetClass,
			String name, int lowerbound, int upperbound, boolean containment) {
		EReference ref = EcoreFactory.eINSTANCE.createEReference();
		ref.setContainment(containment);
		ref.setName(name);
		ref.setEType(targetClass);
		ref.setUpperBound(upperbound);
		ref.setLowerBound(lowerbound);
		sourceClass.getEStructuralFeatures().add(ref);
		return ref;
		// logAnnotations(sourceClass);
	}

	@Override
	public EClass createClass(EPackage pak, String name) {
		EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		pak.getEClassifiers().add(eClass);
		eClass.setName(name);
		return eClass;
	}


	public EAttribute findAttributeInAll(EClass eclass, String name) {
		EList<EAttribute> attrs = eclass.getEAllAttributes();
		for (EAttribute eAttribute : attrs)
			if (eAttribute.getName().equals(name))
				return eAttribute;
		return null;
	}

    @Override
	public EStructuralFeature findAttribute(EClass claz, String name) {
		List<EStructuralFeature> sfs = claz.getEStructuralFeatures();
		for (EStructuralFeature sf : sfs) {
			if (sf instanceof EAttribute && name.equals(sf.getName())) {
				return sf;
			}
		}
		return null;
	}

    @Override
	public EStructuralFeature findReference(EClass claz, String name) {
		List<EStructuralFeature> sfs = claz.getEStructuralFeatures();
		for (EStructuralFeature sf : sfs) {
			if (sf instanceof EReference && name.equals(sf.getName())) {
				return sf;
			}
		}
		return null;
	}



	public void logAllClasses(List<EModelElement> modelElements) {
		for (EModelElement eModelElement : modelElements)
			if (eModelElement instanceof EClass)
				clog(((EClass) eModelElement).getName());
	}

	public void checkPendingReferences(List<EModelElement> modelElements) {
		for (EModelElement element : modelElements)
			if (element instanceof EClass)
				for (EReference ref : ((EClass) element).getEAllReferences())
					if (ref.getEType() == null)
						throw new RuntimeException(
								"!!! error: pending reference: "
										+ ((EClass) element).getName() + "."
										+ ref.getName());
	}

	@Override
	public EPackage createPackage(EFactory factory, String xsdNamespace,
			String eName, String ePrefix) {
		EPackage pakag = EcoreFactory.eINSTANCE.createEPackage();
		pakag.setName(eName);
		pakag.setNsPrefix(ePrefix);
		pakag.setNsURI(xsdNamespace);
		pakag.setEFactoryInstance(factory);
		return pakag;
	}

	@Override
	public EAttribute createAttribute(EClass eClass, EDataType dataType,
			String name) {
		EAttribute attr = EcoreFactory.eINSTANCE.createEAttribute();
		attr.setName(name);
		attr.setEType(dataType);
		eClass.getEStructuralFeatures().add(attr);
		return attr;
	}



	public EAnnotation getFirstAnnotation(EClass eClass, String source,
			String key, String value) {
		if (value==null)
			value=DParams.NULL_ANNOTATION_VALUE;
		EAnnotation an = eClass.getEAnnotation(source);
		if (an == null) {
			an = EcoreFactory.eINSTANCE.createEAnnotation();
			an.setSource(source);
			an.setEModelElement(eClass);
			an.getDetails().put(key, value);
		} else {
			Map.Entry<String, String> e = findEntry(an, key);//FP140527, value
			if (e == null)
				an.getDetails().put(key, value);
		}
		return an;
	}

	@Override
	public EAnnotation createAnnotationAndDetail(EClass eClass, String source,
			String key, String value, String viewkey, String view) {


		if (annotationExists(eClass, source, key, value, view))
			throw new RuntimeException("annotation already exists in view "
					+ view + " : " + source + "(" + key + "=" + value + ")");
		EAnnotation an = EcoreFactory.eINSTANCE.createEAnnotation();
		an.setSource(source);
		an.setEModelElement(eClass);
		an.getDetails().put(key, value==null?DParams.NULL_ANNOTATION_VALUE:value);
		if (view != null) {
			EAnnotation an2 = EcoreFactory.eINSTANCE.createEAnnotation();
			an2.setSource(source);
			an2.setEModelElement(eClass);
			// an2.getDetails().put(DiagraphKeywords.VIEW_EQ+view, "");
			an2.getDetails().put(viewkey + "=" + view, DParams.NULL_ANNOTATION_VALUE);
		}
		return an;
	}

	public EAnnotation InFirstAnnotation(EClass eClass, String source,
			String key, String value) {
		if (value==null)
			value=DParams.NULL_ANNOTATION_VALUE;
		EAnnotation an = eClass.getEAnnotation(source);
		if (an == null)
			throw new RuntimeException("annotation does not exist: " + source);
		Map.Entry<String, String> e = findEntry(an, key);//FP140527, value
		if (e != null)
			throw new RuntimeException("entry already exists: " + source + "("
					+ key + "=" + value + ")");
		an.getDetails().put(key, value);
		return an;
	}

	/*
	 * public boolean annotationExistsInFirstAnnotation(EClass eClass, String
	 * source, String key, String value) { //voir avec view EAnnotation an =
	 * eClass.getEAnnotation(source); return an != null && findEntry(an, key,
	 * value) != null; }
	 */

	private String parseView(EAnnotation eAnnotation) {
		for (Map.Entry<String, String> entry : eAnnotation.getDetails())
			if (entry.getKey().startsWith(DiagraphKeywords.VIEW_EQ))
				return entry.getKey().split("=")[1];
		return ViewConstants.DIAGRAPH_DEFAULT;
	}

	public EAnnotation getInViewDiagraphAnnotation(EClass eclass, String view) {
		for (EAnnotation eAnnotation : eclass.getEAnnotations()) { // FP121015
			if (eAnnotation.getSource().equals(
					GenericConstants.ANNOT_DIAGRAPH_LITTERAL)) {
				if (parseView(eAnnotation).equals(view))
					return eAnnotation;
			}
		}
		return null;
	}

	@Override
	public EAnnotation createEntry(EClass eClass, String source, String key,
			String value, String view) {
		// EAnnotation an = eClass.getEAnnotation(source);
		if (value==null)
			value=DParams.NULL_ANNOTATION_VALUE;
		EAnnotation an = getInViewDiagraphAnnotation(eClass, view);
		if (an == null)
			throw new RuntimeException("annotation does not exist: " + source);
		Map.Entry<String, String> e = findEntry(an, key);
		if (e != null)
			throw new RuntimeException("entry already exists: " + source + "("
					+ key + "=" + value + ")");
		an.getDetails().put(key, value);
		return an;
	}

	private boolean annotationExists(EClass eClass, String source, String key,
			String value, String view) { // FP121015
		EAnnotation an = getInViewDiagraphAnnotation(eClass, view);
		return an != null && findEntry(an, key) != null;
	}

	public void removeReference(EReference ref) {
		EcoreUtil.delete(ref);
	}

	public String renameReference_(EReference reference) {
		String cname = ((EClass) reference.eContainer()).getName()
				.toLowerCase();
		cname.substring(0, Math.min(3, cname.length()));
		reference.setName(cname.substring(0, Math.min(3, cname.length())) + "_"
				+ reference.getName());
		return reference.getName();
	}

	// FP120111
	public EAnnotation copyEClassAnnotation(EAnnotation oldannot,
			Entry<String, String> exclude) {
		if (!(oldannot.eContainer() instanceof EClass))
			throw new RuntimeException("annotation should belong to an EClass ");
		EClass contnr = (EClass) oldannot.eContainer();
		EAnnotation cloned = EcoreFactory.eINSTANCE.createEAnnotation();
		cloned.setSource(oldannot.getSource());
		for (Entry<String, String> oldentry : oldannot.getDetails())
			if (oldentry != exclude)
				cloned.getDetails().put(oldentry.getKey(), oldentry.getValue()==null?DParams.NULL_ANNOTATION_VALUE:oldentry.getValue());
		return cloned;
	}

	public void removeEClassAnnotationDetail(EAnnotation oldannot,
			Entry<String, String> but) {
		if (!(oldannot.eContainer() instanceof EClass))
			throw new RuntimeException("annotation should belong to an EClass ");
		EClass contnr = (EClass) oldannot.eContainer();
		EAnnotation newupdated = copyEClassAnnotation(oldannot, but);
		EcoreUtil.delete(oldannot);
		contnr.getEAnnotations().add(newupdated);
	}

	public boolean isBuildinDatatype(EClassifier dt) {
		return (dt == EcorePackage.eINSTANCE.getEString()
				|| dt == EcorePackage.eINSTANCE.getEInt()
				|| dt == EcorePackage.eINSTANCE.getEIntegerObject()
				|| dt == EcorePackage.eINSTANCE.getEBoolean()
				|| dt == EcorePackage.eINSTANCE.getEBooleanObject()
				|| dt == EcorePackage.eINSTANCE.getEByte()
				|| dt == EcorePackage.eINSTANCE.getEByteObject()
				|| dt == EcorePackage.eINSTANCE.getEChar()
				|| dt == EcorePackage.eINSTANCE.getECharacterObject()
				|| dt == EcorePackage.eINSTANCE.getEBigDecimal()
				|| dt == EcorePackage.eINSTANCE.getEBigInteger()
				|| dt == EcorePackage.eINSTANCE.getEByteArray()
				|| dt == EcorePackage.eINSTANCE.getEDouble()
				|| dt == EcorePackage.eINSTANCE.getEDoubleObject()
				|| dt == EcorePackage.eINSTANCE.getEDate()
				|| dt == EcorePackage.eINSTANCE.getEFloat() || dt == EcorePackage.eINSTANCE
					.getEFloatObject());
	}

	private void cloneBuildinTypedAttributes_(EClass copy_, EClass original,
			boolean merge) {
		// boolean merge = copy_ == original;
		List<EAttribute> attributes = original.getEAttributes();
		for (EAttribute eAttribute : attributes) {
			EStructuralFeature sf = copy_.getEStructuralFeature(eAttribute
					.getName());
			if (merge && sf != null && sf instanceof EAttribute) {
				if (LOG)
					clog("merging, " + eAttribute.getName()
							+ " allready exists");
			} else {
				EClassifier dt = eAttribute.getEType();
				if (isBuildinDatatype(dt))
					createAttribute(copy_, (EDataType) dt, eAttribute.getName());
			}
		}
	}

	private EClass findEClassInTarget(String name) {
		EClassifier exists = targetPack.getEClassifier(name);
		if (exists != null && exists instanceof EClass)
			return (EClass) exists;
		return null;
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	private EClass getEClass(String name) {
		EClass sr = (EClass) clones.get(name);
		if (sr == null)
			sr = (EClass) targetPack.getEClassifier(name);
		if (sr == null)
			throw new RuntimeException(
					"should not happen, unregistered EClass " + name);
		return sr;
	}

	@Override
	public boolean isStub() {
		return false;
	}

	@Override
	public boolean isQualified() {
		return true;
	}

	@Override
	public boolean isFunctional() {
		return true;
	}

}

// org.isoe.diagraph.utils.EcoreUtils
