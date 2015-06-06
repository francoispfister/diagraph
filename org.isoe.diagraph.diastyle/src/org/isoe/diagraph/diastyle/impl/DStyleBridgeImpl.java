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
package org.isoe.diagraph.diastyle.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.isoe.diagraph.diagraph.DGraphElement;

import org.isoe.diagraph.diastyle.DStyleBridge;
import org.isoe.diagraph.diastyle.DiastylePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>DStyle Bridge</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.isoe.diagraph.diastyle.impl.DStyleBridgeImpl#getDGraphElement <em>DGraph Element</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.impl.DStyleBridgeImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DStyleBridgeImpl extends EObjectImpl implements DStyleBridge {
	/**
	 * The cached value of the '{@link #getDGraphElement() <em>DGraph Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDGraphElement()
	 * @generated
	 * @ordered
	 */
	protected DGraphElement dGraphElement;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DStyleBridgeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DiastylePackage.Literals.DSTYLE_BRIDGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DGraphElement getDGraphElement() {
		if (dGraphElement != null && dGraphElement.eIsProxy()) {
			InternalEObject oldDGraphElement = (InternalEObject)dGraphElement;
			dGraphElement = (DGraphElement)eResolveProxy(oldDGraphElement);
			if (dGraphElement != oldDGraphElement) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DiastylePackage.DSTYLE_BRIDGE__DGRAPH_ELEMENT, oldDGraphElement, dGraphElement));
			}
		}
		return dGraphElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DGraphElement basicGetDGraphElement() {
		return dGraphElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDGraphElement(DGraphElement newDGraphElement) {
		DGraphElement oldDGraphElement = dGraphElement;
		dGraphElement = newDGraphElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiastylePackage.DSTYLE_BRIDGE__DGRAPH_ELEMENT, oldDGraphElement, dGraphElement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiastylePackage.DSTYLE_BRIDGE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DiastylePackage.DSTYLE_BRIDGE__DGRAPH_ELEMENT:
				if (resolve) return getDGraphElement();
				return basicGetDGraphElement();
			case DiastylePackage.DSTYLE_BRIDGE__NAME:
				return getName();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case DiastylePackage.DSTYLE_BRIDGE__DGRAPH_ELEMENT:
				setDGraphElement((DGraphElement)newValue);
				return;
			case DiastylePackage.DSTYLE_BRIDGE__NAME:
				setName((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case DiastylePackage.DSTYLE_BRIDGE__DGRAPH_ELEMENT:
				setDGraphElement((DGraphElement)null);
				return;
			case DiastylePackage.DSTYLE_BRIDGE__NAME:
				setName(NAME_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case DiastylePackage.DSTYLE_BRIDGE__DGRAPH_ELEMENT:
				return dGraphElement != null;
			case DiastylePackage.DSTYLE_BRIDGE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //DStyleBridgeImpl
