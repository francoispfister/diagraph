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
package org.isoe.diagraph.diastyle.util;

import java.util.List;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.isoe.diagraph.diastyle.*;

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
 * @see org.isoe.diagraph.diastyle.DiastylePackage
 * @generated
 */
public class DiastyleSwitch<T> extends Switch<T>  {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static DiastylePackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DiastyleSwitch() {
		if (modelPackage == null) {
			modelPackage = DiastylePackage.eINSTANCE;
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
			case DiastylePackage.DNODE_EDGE_STYLE: {
				DNodeEdgeStyle dNodeEdgeStyle = (DNodeEdgeStyle)theEObject;
				T result = caseDNodeEdgeStyle(dNodeEdgeStyle);
				if (result == null) result = caseEModelElement(dNodeEdgeStyle);
				if (result == null) result = caseDBaseStyle(dNodeEdgeStyle);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DiastylePackage.DSTYLE: {
				DStyle dStyle = (DStyle)theEObject;
				T result = caseDStyle(dStyle);
				if (result == null) result = caseEModelElement(dStyle);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DiastylePackage.DSTYLE_BRIDGE: {
				DStyleBridge dStyleBridge = (DStyleBridge)theEObject;
				T result = caseDStyleBridge(dStyleBridge);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DiastylePackage.DNODE_STYLE: {
				DNodeStyle dNodeStyle = (DNodeStyle)theEObject;
				T result = caseDNodeStyle(dNodeStyle);
				if (result == null) result = caseDNodeEdgeStyle(dNodeStyle);
				if (result == null) result = caseEModelElement(dNodeStyle);
				if (result == null) result = caseDBaseStyle(dNodeStyle);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DiastylePackage.DEDGE_STYLE: {
				DEdgeStyle dEdgeStyle = (DEdgeStyle)theEObject;
				T result = caseDEdgeStyle(dEdgeStyle);
				if (result == null) result = caseDNodeEdgeStyle(dEdgeStyle);
				if (result == null) result = caseEModelElement(dEdgeStyle);
				if (result == null) result = caseDBaseStyle(dEdgeStyle);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DiastylePackage.DNESTING_EDGE_STYLE: {
				DNestingEdgeStyle dNestingEdgeStyle = (DNestingEdgeStyle)theEObject;
				T result = caseDNestingEdgeStyle(dNestingEdgeStyle);
				if (result == null) result = caseDBaseStyle(dNestingEdgeStyle);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DiastylePackage.DBASE_STYLE: {
				DBaseStyle dBaseStyle = (DBaseStyle)theEObject;
				T result = caseDBaseStyle(dBaseStyle);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>DNode Edge Style</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>DNode Edge Style</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDNodeEdgeStyle(DNodeEdgeStyle object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>DStyle</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>DStyle</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDStyle(DStyle object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>DStyle Bridge</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>DStyle Bridge</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDStyleBridge(DStyleBridge object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>DNode Style</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>DNode Style</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDNodeStyle(DNodeStyle object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>DEdge Style</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>DEdge Style</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDEdgeStyle(DEdgeStyle object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>DNesting Edge Style</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>DNesting Edge Style</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDNestingEdgeStyle(DNestingEdgeStyle object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>DBase Style</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>DBase Style</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDBaseStyle(DBaseStyle object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EModel Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EModel Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEModelElement(EModelElement object) {
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

} //DiastyleSwitch
