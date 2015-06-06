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
package org.isoe.diagraph.dgi.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.isoe.diagraph.dgi.DNamedElement;
import org.isoe.diagraph.dgi.DPoorReference;
import org.isoe.diagraph.dgi.DgiPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>DPoor Reference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.isoe.diagraph.dgi.impl.DPoorReferenceImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.isoe.diagraph.dgi.impl.DPoorReferenceImpl#getETargetModelElement <em>ETarget Model Element</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DPoorReferenceImpl extends DEdgeImpl implements DPoorReference {
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
	 * The cached value of the '{@link #getETargetModelElement() <em>ETarget Model Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getETargetModelElement()
	 * @generated
	 * @ordered
	 */
	protected EModelElement eTargetModelElement;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DPoorReferenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DgiPackage.Literals.DPOOR_REFERENCE;
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
			eNotify(new ENotificationImpl(this, Notification.SET, DgiPackage.DPOOR_REFERENCE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EModelElement getETargetModelElement() {
		if (eTargetModelElement != null && eTargetModelElement.eIsProxy()) {
			InternalEObject oldETargetModelElement = (InternalEObject)eTargetModelElement;
			eTargetModelElement = (EModelElement)eResolveProxy(oldETargetModelElement);
			if (eTargetModelElement != oldETargetModelElement) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DgiPackage.DPOOR_REFERENCE__ETARGET_MODEL_ELEMENT, oldETargetModelElement, eTargetModelElement));
			}
		}
		return eTargetModelElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EModelElement basicGetETargetModelElement() {
		return eTargetModelElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setETargetModelElement(EModelElement newETargetModelElement) {
		EModelElement oldETargetModelElement = eTargetModelElement;
		eTargetModelElement = newETargetModelElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DgiPackage.DPOOR_REFERENCE__ETARGET_MODEL_ELEMENT, oldETargetModelElement, eTargetModelElement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DgiPackage.DPOOR_REFERENCE__NAME:
				return getName();
			case DgiPackage.DPOOR_REFERENCE__ETARGET_MODEL_ELEMENT:
				if (resolve) return getETargetModelElement();
				return basicGetETargetModelElement();
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
			case DgiPackage.DPOOR_REFERENCE__NAME:
				setName((String)newValue);
				return;
			case DgiPackage.DPOOR_REFERENCE__ETARGET_MODEL_ELEMENT:
				setETargetModelElement((EModelElement)newValue);
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
			case DgiPackage.DPOOR_REFERENCE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case DgiPackage.DPOOR_REFERENCE__ETARGET_MODEL_ELEMENT:
				setETargetModelElement((EModelElement)null);
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
			case DgiPackage.DPOOR_REFERENCE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case DgiPackage.DPOOR_REFERENCE__ETARGET_MODEL_ELEMENT:
				return eTargetModelElement != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == DNamedElement.class) {
			switch (derivedFeatureID) {
				case DgiPackage.DPOOR_REFERENCE__NAME: return DgiPackage.DNAMED_ELEMENT__NAME;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == DNamedElement.class) {
			switch (baseFeatureID) {
				case DgiPackage.DNAMED_ELEMENT__NAME: return DgiPackage.DPOOR_REFERENCE__NAME;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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

} //DPoorReferenceImpl
