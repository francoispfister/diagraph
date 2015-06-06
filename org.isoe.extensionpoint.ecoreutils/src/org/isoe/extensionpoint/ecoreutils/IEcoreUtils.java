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
package org.isoe.extensionpoint.ecoreutils;

import java.util.List;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;


/**
 *
 * @author fpfister
 *
 */
public interface IEcoreUtils {

   void init(EPackage target, EPackage source);
   void consolide();
  // void cloneAnnotations(ENamedElement clone, ENamedElement origin);
  // EClass cloneClassWithAttributesAndAnnotations(EClass target);
   void cloneClassWithAttributesAndAnnotations(EClass toMerge);
   void cloneInheritance(EClass eClass, EClass eSuperclass);
   EReference cloneEReference(EReference reference);
   EAttribute createAttribute(EClass eClass, EDataType dataType, String name);
   void removeReference(EReference reference);
   EReference createReference(EClass sourceClass, EClass linkClass, String string,
         int i, int oldrefcard, boolean b);
   EClass createClass(EPackage ePackage, String string);
   EPackage createPackage(EFactory factory, String xsdNamespace, String eName,
         String ePrefix);
   EAnnotation createAnnotationAndDetail(EClass eClass, String source,
         String key, String value, String viewkey, String view);
   EAnnotation createEntry(EClass eClass, String annotDiagraphLitteral,
         String string, String string2, String view);
   void logAllClasses(List<EModelElement> modelElements);
   void checkPendingReferences(List<EModelElement> modelElements);
   String renameReference_(EReference dupref);
   Object findAttribute(EClass named, String nameLitteral);
   boolean isStub();
   boolean isQualified();
   boolean isFunctional();
  // void setControler(IDiagraphControler diagraphControler);
 // void cloneAnnotations_(ENamedElement target, ENamedElement source);
  // void cloneAnnotations(ENamedElement copy, ENamedElement original,boolean mergingEClass);
   void clonePackageAnnotations(EPackage copyPack, EPackage originalPack);
   //void cloneAnnotations(EClass copy, EClass original, boolean merging);
   void cloneAnnotations(EClass clone, EClass original);
   EStructuralFeature findReference(EClass claz, String name);


}
