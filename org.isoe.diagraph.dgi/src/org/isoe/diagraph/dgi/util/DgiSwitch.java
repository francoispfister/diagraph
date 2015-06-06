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
package org.isoe.diagraph.dgi.util;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.isoe.diagraph.dgi.*;

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
 * @see org.isoe.diagraph.dgi.DgiPackage
 * @generated
 */
public class DgiSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static DgiPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DgiSwitch() {
		if (modelPackage == null) {
			modelPackage = DgiPackage.eINSTANCE;
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
			case DgiPackage.DELEMENT: {
				DElement dElement = (DElement)theEObject;
				T result = caseDElement(dElement);
				if (result == null) result = caseDLabelledElement(dElement);
				if (result == null) result = caseDNamedElement(dElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DgiPackage.DLABELLED_ELEMENT: {
				DLabelledElement dLabelledElement = (DLabelledElement)theEObject;
				T result = caseDLabelledElement(dLabelledElement);
				if (result == null) result = caseDNamedElement(dLabelledElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DgiPackage.DNODE: {
				DNode dNode = (DNode)theEObject;
				T result = caseDNode(dNode);
				if (result == null) result = caseDElement(dNode);
				if (result == null) result = caseDLabelledElement(dNode);
				if (result == null) result = caseDNamedElement(dNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DgiPackage.DLINK: {
				DLink dLink = (DLink)theEObject;
				T result = caseDLink(dLink);
				if (result == null) result = caseDElement(dLink);
				if (result == null) result = caseDEdge(dLink);
				if (result == null) result = caseDLabelledElement(dLink);
				if (result == null) result = caseDNamedElement(dLink);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DgiPackage.DPOOR_REFERENCE: {
				DPoorReference dPoorReference = (DPoorReference)theEObject;
				T result = caseDPoorReference(dPoorReference);
				if (result == null) result = caseDEdge(dPoorReference);
				if (result == null) result = caseDNamedElement(dPoorReference);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DgiPackage.DCONTAINMENT: {
				DContainment dContainment = (DContainment)theEObject;
				T result = caseDContainment(dContainment);
				if (result == null) result = caseDLabelledElement(dContainment);
				if (result == null) result = caseDEdge(dContainment);
				if (result == null) result = caseDNamedElement(dContainment);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DgiPackage.DLABEL: {
				DLabel dLabel = (DLabel)theEObject;
				T result = caseDLabel(dLabel);
				if (result == null) result = caseDNamedElement(dLabel);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DgiPackage.DGENERIC_ELEMENT: {
				DGenericElement dGenericElement = (DGenericElement)theEObject;
				T result = caseDGenericElement(dGenericElement);
				if (result == null) result = caseDElement(dGenericElement);
				if (result == null) result = caseDLabelledElement(dGenericElement);
				if (result == null) result = caseDNamedElement(dGenericElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DgiPackage.DCONCRETE_SYNTAX: {
				DConcreteSyntax dConcreteSyntax = (DConcreteSyntax)theEObject;
				T result = caseDConcreteSyntax(dConcreteSyntax);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DgiPackage.DEDGE: {
				DEdge dEdge = (DEdge)theEObject;
				T result = caseDEdge(dEdge);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DgiPackage.DNAMED_ELEMENT: {
				DNamedElement dNamedElement = (DNamedElement)theEObject;
				T result = caseDNamedElement(dNamedElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DgiPackage.DPOINT_OF_VIEW: {
				DPointOfView dPointOfView = (DPointOfView)theEObject;
				T result = caseDPointOfView(dPointOfView);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>DElement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>DElement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDElement(DElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>DLabelled Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>DLabelled Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDLabelledElement(DLabelledElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>DNode</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>DNode</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDNode(DNode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>DLink</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>DLink</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDLink(DLink object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>DPoor Reference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>DPoor Reference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDPoorReference(DPoorReference object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>DContainment</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>DContainment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDContainment(DContainment object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>DLabel</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>DLabel</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDLabel(DLabel object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>DGeneric Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>DGeneric Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDGenericElement(DGenericElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>DConcrete Syntax</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>DConcrete Syntax</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDConcreteSyntax(DConcreteSyntax object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>DEdge</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>DEdge</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDEdge(DEdge object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>DNamed Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>DNamed Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDNamedElement(DNamedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>DPoint Of View</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>DPoint Of View</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDPointOfView(DPointOfView object) {
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

} //DgiSwitch
