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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.common.util.EList;
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
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EClassImpl;
import org.eclipse.emf.ecore.impl.ESuperAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.text.link.LinkedModeUI.ExitFlags;
import org.isoe.diagraph.generic.GenericConstants;
import org.isoe.diagraph.lang.DiagraphKeywords;
import org.isoe.diastyle.lang.StyleConstants;
import org.isoe.diagraph.views.ViewConstants;

/**
 *
 * @author pfister TODO a factory to avoid static implementation
 */
public class EcoreUtilsv0 {

   private static final boolean LOG = false;

   private static Map<String, EClassifier> clones = new HashMap<String, EClassifier>();
   private static EPackage target = null, cloneRoot = null;




   /**
    * copies the temporary clone package pback to the given package
    */
   public static void consolide() {
      target.getEClassifiers().addAll(cloneRoot.getEClassifiers());
      target.getEAnnotations().addAll(cloneRoot.getEAnnotations());
      cloneRoot.getEClassifiers().clear();
      cloneRoot.getEAnnotations().clear();
      cloneRoot = null;
   }

   public static void testLeaf(EPackage domainModel, String classname) {
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

   public static List<EClass> getLeafTypes(EClass claz) {
      List<EClass> result = new ArrayList<EClass>();
      List<EClass> pakcleafs = getLeafTypes(claz.getEPackage());
      for (EClass pakcleaf : pakcleafs)
         if (claz.isSuperTypeOf(pakcleaf))
            result.add(pakcleaf);
      return result;

   }

   public static List<EClass> getLeafTypes(EPackage domainModel) {
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

   public static List<EClass> getSubTypes(EClass eclass) {
      List<EClass> result = new ArrayList<EClass>();
      EPackage pack = eclass.getEPackage();
      for (Iterator<EObject> contents = pack.eAllContents(); contents.hasNext();) {
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

   public static boolean isSuperTypeOf(EClass supe, EClass lower) {
      for (EClass su : lower.getEAllSuperTypes())
         if (su.equals(supe))
            return true;
      return false;
   }

   public static void logReferences(EClass claz) {
      if (claz.getEAllReferences().size() == 0)
         clog("no references for " + claz.getName());
      for (EReference r : claz.getEAllReferences())
         clog(r.getName() + "(" + r.getEType().getName() + ")");
   }

   private static void createInheritance(EClass eClass, EClass superclass) {
      clog("createInheritance " + eClass.getName() + " --> "
            + superclass.getName());
      // eClass.
      eClass.getESuperTypes().add(superclass);
      // sr.getESuperTypes().add(tg);
      // superclass.ge

   }

   public static EReference createReference(EClass sourceClass,
         EClass targetClass, String name, int lowerbound, int upperbound,
         boolean containment) {
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

   public static EClass createClass(EPackage pak, String name) {
      EClass eClass = EcoreFactory.eINSTANCE.createEClass();
      pak.getEClassifiers().add(eClass);
      eClass.setName(name);
      return eClass;
   }

   public static EAttribute findAttribute(EClass eclass, String name) {
      EList<EAttribute> attrs = eclass.getEAllAttributes();
      for (EAttribute eAttribute : attrs)
         if (eAttribute.getName().equals(name))
            return eAttribute;
      return null;
   }

   public static void logAllClasses(List<EModelElement> modelElements) {
      for (EModelElement eModelElement : modelElements)
         if (eModelElement instanceof EClass)
            clog(((EClass) eModelElement).getName());
   }

   public static void checkPendingReferences(List<EModelElement> modelElements) {
      for (EModelElement element : modelElements)
         if (element instanceof EClass)
            for (EReference ref : ((EClass) element).getEAllReferences())
               if (ref.getEType() == null)
                  throw new RuntimeException("!!! error: pending reference: "
                        + ((EClass) element).getName() + "." + ref.getName());
   }

   public static EPackage createPackage(EFactory factory, String xsdNamespace,
         String eName, String ePrefix) {
      EPackage pakag = EcoreFactory.eINSTANCE.createEPackage();
      pakag.setName(eName);
      pakag.setNsPrefix(ePrefix);
      pakag.setNsURI(xsdNamespace);
      pakag.setEFactoryInstance(factory);
      return pakag;
   }

   public static EAttribute createAttribute(EClass eClass, EDataType dataType,
         String name) {
      EAttribute attr = EcoreFactory.eINSTANCE.createEAttribute();
      attr.setName(name);
      attr.setEType(dataType);
      eClass.getEStructuralFeatures().add(attr);
      return attr;
   }

   public static Map.Entry<String, String> findEntry(EAnnotation an,
         String key) {
      for (Map.Entry<String, String> entry : an.getDetails()) {
         if (entry.getKey().equals(key))// && entry.getValue().equals(value))
            return entry;
      }
      return null;
   }

   public static EAnnotation getFirstAnnotation(EClass eClass, String source,
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
         Map.Entry<String, String> e = findEntry(an, key);
         if (e == null)
            an.getDetails().put(key, value);
      }
      return an;
   }

   /*---------------------------*/

   public static void cloneAnnotations(ENamedElement clone, ENamedElement origin) {
      for (EAnnotation orig : origin.getEAnnotations()) {
         String source = orig.getSource();
         // TODO chev if the annotation allready exists in the source, create a
         // merge mechanism
         EAnnotation cloned = EcoreFactory.eINSTANCE.createEAnnotation();
         clone.getEAnnotations().add(cloned);
         cloned.setSource(orig.getSource());
         for (Entry<String, String> orentry : orig.getDetails())
            cloned.getDetails().put(orentry.getKey(),
                  orentry.getValue() == null ? "" : orentry.getValue());
      }
   }

   public static EClass cloneClassWithAttributesAndAnnotations(EClass toMerge) {

      EClassifier result =null;
      try {
        result = findInTarget(toMerge.getName()); // connect to the
         // original if
         // homonimy
      } catch (Exception e) {

      }

      if (result != null && result instanceof EClass)
         return (EClass) result;
      result = clones.get(toMerge.getName());
      if (result != null) {
         if (!(result instanceof EClass))
            throw new RuntimeException(
                  "should not happen in cloneClassWithAttributes");
         return (EClass) result;
      }
      result = createClass(cloneRoot, toMerge.getName());
      cloneAnnotations((EClass) result, toMerge);
      cloneBuildinTypedAttributes_((EClass) result, toMerge);
      for (EAttribute eAttribute : toMerge.getEAttributes()) {
         EClassifier dt = eAttribute.getEType(); // TODO handle enums and other
                                                 // datatypes
         if (dt instanceof EEnum) {
            clog("!!! TODO: atribute type is Enum !!!");
            // EcoreUtils.createAttribute(cloned, (EDataType) dt,
            // eAttribute.getName());
         }
      }
      cloneRoot.getEClassifiers().add(result);
      clones.put(result.getName(), result);
      return (EClass) result;
   }

   public static EReference cloneEReference(EReference reference) {
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
         result = createReference(sr, tg, reference.getName(),
               reference.getLowerBound(), reference.getUpperBound(),
               reference.isContainment());
      }
      return result;
   }

   public static void cloneInheritance(EClass eClass, EClass eSuperclass) {
      EClass sr = getEClass(eClass.getName());
      EClass tg = getEClass(eSuperclass.getName());
      // EClass sr=(EClass) clones.get(eClass.getName());
      // EClass tg=(EClass) clones.get(eSuperclass.getName());
      createInheritance(sr, tg);
   }


   public static void init(EPackage target, EPackage source) {
      if (target == null)
         throw new RuntimeException("should not happen in EcoreUtils.init");
      clones = new HashMap<String, EClassifier>();
      EcoreUtilsv0.target = target;
      cloneRoot = EcoreFactory.eINSTANCE.createEPackage();
      cloneRoot.setName("clonecache");
      // cloneRoot is mandatory to avoid concurrent modification of the target
      // package during the visitor iteration
      // will be consolidated at the end of the process
   }

   /*--------------------------------------*/






   public static EAnnotation createAnnotationAndDetail(EClass eClass,
         String source, String key, String value, String viewkey, String view) {
      if (annotationExists(eClass, source, key, value, view))
         throw new RuntimeException("annotation already exists in view " + view
               + " : " + source + "(" + key + "=" + value + ")");
      EAnnotation an = EcoreFactory.eINSTANCE.createEAnnotation();
      an.setSource(source);
      an.setEModelElement(eClass);
      an.getDetails().put(key, value);
      if (view != null) {
         EAnnotation an2 = EcoreFactory.eINSTANCE.createEAnnotation();
         an2.setSource(source);
         an2.setEModelElement(eClass);
         // an2.getDetails().put(DiagraphKeywords.VIEW_EQ+view, "");
         an2.getDetails().put(viewkey + "=" + view, "");
      }
      return an;
   }

   public static EAnnotation InFirstAnnotation(EClass eClass, String source,
         String key, String value) {
	  if (value==null)
			  value=DParams.NULL_ANNOTATION_VALUE;
      EAnnotation an = eClass.getEAnnotation(source);
      if (an == null)
         throw new RuntimeException("annotation does not exist: " + source);
      Map.Entry<String, String> e = findEntry(an, key);
      if (e != null)
         throw new RuntimeException("entry already exists: " + source + "("
               + key + "=" + value + ")");
      an.getDetails().put(key, value);
      return an;
   }

   /*
    * public static boolean annotationExistsInFirstAnnotation(EClass eClass,
    * String source, String key, String value) { //voir avec view EAnnotation an
    * = eClass.getEAnnotation(source); return an != null && findEntry(an, key,
    * value) != null; }
    */

   private static String parseView(EAnnotation eAnnotation) {
      for (Map.Entry<String, String> entry : eAnnotation.getDetails())
         if (entry.getKey().startsWith(DiagraphKeywords.VIEW_EQ))
            return entry.getKey().split("=")[1];
      return ViewConstants.DIAGRAPH_DEFAULT;
   }

   public static EAnnotation getInViewDiagraphAnnotation(EClass eclass,
         String view) {
      for (EAnnotation eAnnotation : eclass.getEAnnotations()) { // FP121015
         if (eAnnotation.getSource().equals(
               GenericConstants.ANNOT_DIAGRAPH_LITTERAL)) {
            if (parseView(eAnnotation).equals(view))
               return eAnnotation;
         }
      }
      return null;
   }

   public static EAnnotation createEntry(EClass eClass, String source,
         String key, String value, String view) {
	if (value==null)
			  value=DParams.NULL_ANNOTATION_VALUE;
      // EAnnotation an = eClass.getEAnnotation(source);
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

   private static boolean annotationExists(EClass eClass, String source,
         String key, String value, String view) { // FP121015
	  if (value==null)
			  value=DParams.NULL_ANNOTATION_VALUE;
      EAnnotation an = getInViewDiagraphAnnotation(eClass, view);
      return an != null && findEntry(an, key) != null;
   }

   public static void removeReference(EReference ref) {
      EcoreUtil.delete(ref);
   }

   public static String renameReference_(EReference reference) {
      String cname = ((EClass) reference.eContainer()).getName().toLowerCase();
      cname.substring(0, Math.min(3, cname.length()));
      reference.setName(cname.substring(0, Math.min(3, cname.length())) + "_"
            + reference.getName());
      return reference.getName();
   }

   // FP120111
   public static EAnnotation copyEClassAnnotation(EAnnotation oldannot,
         Entry<String, String> exclude) {
      if (!(oldannot.eContainer() instanceof EClass))
         throw new RuntimeException("annotation should belong to an EClass ");
      EClass contnr = (EClass) oldannot.eContainer();
      EAnnotation cloned = EcoreFactory.eINSTANCE.createEAnnotation();
      cloned.setSource(oldannot.getSource());
      for (Entry<String, String> oldentry : oldannot.getDetails())
         if (oldentry != exclude)
            cloned.getDetails().put(oldentry.getKey(), oldentry.getValue()==null?DParams.NULL_ANNOTATION_VALUE:oldentry.getValue()  );
      return cloned;
   }

   public static void removeEClassAnnotationDetail(EAnnotation oldannot,
         Entry<String, String> but) {
      if (!(oldannot.eContainer() instanceof EClass))
         throw new RuntimeException("annotation should belong to an EClass ");
      EClass contnr = (EClass) oldannot.eContainer();
      EAnnotation newupdated = EcoreUtilsv0.copyEClassAnnotation(oldannot, but);
      EcoreUtil.delete(oldannot);
      contnr.getEAnnotations().add(newupdated);
   }

   public static boolean isBuildinDatatype(EClassifier dt) {
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

   private static void cloneBuildinTypedAttributes_(EClass cloned,
         EClass toMerge) {
      List<EAttribute> attributes = toMerge.getEAttributes();
      for (EAttribute eAttribute : attributes) {
         EClassifier dt = eAttribute.getEType();
         if (EcoreUtilsv0.isBuildinDatatype(dt))
            EcoreUtilsv0.createAttribute(cloned, (EDataType) dt,
                  eAttribute.getName());
      }
   }

   private static EClass findInTarget(String name) {
      EClassifier exists = target.getEClassifier(name);
      if (exists != null && exists instanceof EClass)
         return (EClass) exists;
      return null;
   }

   private static void clog(String mesg) {
      if (LOG)
         System.out.println(mesg);
   }

   private static EClass getEClass(String name) {
      EClass sr = (EClass) clones.get(name);
      if (sr == null)
         sr = (EClass) target.getEClassifier(name);
      if (sr == null)
         throw new RuntimeException("should not happen, unregistered EClass "
               + name);
      return sr;
   }

}

// org.isoe.diagraph.utils.EcoreUtils
