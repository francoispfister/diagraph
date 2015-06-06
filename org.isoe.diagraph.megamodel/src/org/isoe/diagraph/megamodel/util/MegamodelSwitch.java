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
package org.isoe.diagraph.megamodel.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.isoe.diagraph.megamodel.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.isoe.diagraph.megamodel.MegamodelPackage
 * @generated
 */
public class MegamodelSwitch<T> extends Switch<T> {
   /**
	 * The cached model package
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   protected static MegamodelPackage modelPackage;

   /**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public MegamodelSwitch() {
		if (modelPackage == null) {
			modelPackage = MegamodelPackage.eINSTANCE;
		}
	}

   /**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @parameter ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
   @Override
   protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

   /**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
   @Override
   protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case MegamodelPackage.MEGAMODEL: {
				Megamodel megamodel = (Megamodel)theEObject;
				T result = caseMegamodel(megamodel);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MegamodelPackage.DSML: {
				Dsml dsml = (Dsml)theEObject;
				T result = caseDsml(dsml);
				if (result == null) result = caseMegamodelElement(dsml);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MegamodelPackage.MEGAMODEL_ELEMENT: {
				MegamodelElement megamodelElement = (MegamodelElement)theEObject;
				T result = caseMegamodelElement(megamodelElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MegamodelPackage.RELATED_TO: {
				RelatedTo relatedTo = (RelatedTo)theEObject;
				T result = caseRelatedTo(relatedTo);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MegamodelPackage.MODEL: {
				Model model = (Model)theEObject;
				T result = caseModel(model);
				if (result == null) result = caseMegamodelElement(model);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MegamodelPackage.NOTATION: {
				Notation notation = (Notation)theEObject;
				T result = caseNotation(notation);
				if (result == null) result = caseMegamodelElement(notation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MegamodelPackage.NOTATION_DIAGRAM: {
				NotationDiagram notationDiagram = (NotationDiagram)theEObject;
				T result = caseNotationDiagram(notationDiagram);
				if (result == null) result = caseDiagram(notationDiagram);
				if (result == null) result = caseMegamodelElement(notationDiagram);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MegamodelPackage.NAVIGATION: {
				Navigation navigation = (Navigation)theEObject;
				T result = caseNavigation(navigation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MegamodelPackage.DIAGRAM: {
				Diagram diagram = (Diagram)theEObject;
				T result = caseDiagram(diagram);
				if (result == null) result = caseMegamodelElement(diagram);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MegamodelPackage.ECORE_DIAGRAM: {
				EcoreDiagram ecoreDiagram = (EcoreDiagram)theEObject;
				T result = caseEcoreDiagram(ecoreDiagram);
				if (result == null) result = caseDiagram(ecoreDiagram);
				if (result == null) result = caseMegamodelElement(ecoreDiagram);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

   /**
	 * Returns the result of interpreting the object as an instance of '<em>Megamodel</em>'.
	 * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Megamodel</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
   public T caseMegamodel(Megamodel object) {
		return null;
	}

   /**
	 * Returns the result of interpreting the object as an instance of '<em>Dsml</em>'.
	 * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Dsml</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
   public T caseDsml(Dsml object) {
		return null;
	}

   /**
	 * Returns the result of interpreting the object as an instance of '<em>Element</em>'.
	 * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
   public T caseMegamodelElement(MegamodelElement object) {
		return null;
	}

   /**
	 * Returns the result of interpreting the object as an instance of '<em>Related To</em>'.
	 * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Related To</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
   public T caseRelatedTo(RelatedTo object) {
		return null;
	}

   /**
	 * Returns the result of interpreting the object as an instance of '<em>Model</em>'.
	 * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Model</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
   public T caseModel(Model object) {
		return null;
	}

   /**
	 * Returns the result of interpreting the object as an instance of '<em>Notation</em>'.
	 * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Notation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
   public T caseNotation(Notation object) {
		return null;
	}

   /**
	 * Returns the result of interpreting the object as an instance of '<em>Notation Diagram</em>'.
	 * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Notation Diagram</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
   public T caseNotationDiagram(NotationDiagram object) {
		return null;
	}

   /**
	 * Returns the result of interpreting the object as an instance of '<em>Navigation</em>'.
	 * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Navigation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
   public T caseNavigation(Navigation object) {
		return null;
	}

   /**
	 * Returns the result of interpreting the object as an instance of '<em>Diagram</em>'.
	 * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Diagram</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
   public T caseDiagram(Diagram object) {
		return null;
	}

   /**
	 * Returns the result of interpreting the object as an instance of '<em>Ecore Diagram</em>'.
	 * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch.
    * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Ecore Diagram</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
   public T caseEcoreDiagram(EcoreDiagram object) {
		return null;
	}

   /**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
    * This implementation returns null;
    * returning a non-null result will terminate the switch, but this is the last case anyway.
    * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
   @Override
   public T defaultCase(EObject object) {
		return null;
	}

} //MegamodelSwitch
