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
package org.isoe.gramgen.pattern.classassociation.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.isoe.gramgen.pattern.classassociation.ClassassociationPackage;
import org.isoe.gramgen.pattern.classassociation.Label;
import org.isoe.gramgen.pattern.classassociation.Target;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Label</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.isoe.gramgen.pattern.classassociation.impl.LabelImpl#getLt <em>Lt</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LabelImpl extends EObjectImpl implements Label {
	/**
	 * The cached value of the '{@link #getLt() <em>Lt</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLt()
	 * @generated
	 * @ordered
	 */
	protected Target lt;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LabelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ClassassociationPackage.Literals.LABEL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Target getLt() {
		if (lt != null && lt.eIsProxy()) {
			InternalEObject oldLt = (InternalEObject)lt;
			lt = (Target)eResolveProxy(oldLt);
			if (lt != oldLt) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ClassassociationPackage.LABEL__LT, oldLt, lt));
			}
		}
		return lt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Target basicGetLt() {
		return lt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLt(Target newLt) {
		Target oldLt = lt;
		lt = newLt;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ClassassociationPackage.LABEL__LT, oldLt, lt));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ClassassociationPackage.LABEL__LT:
				if (resolve) return getLt();
				return basicGetLt();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ClassassociationPackage.LABEL__LT:
				setLt((Target)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(int featureID) {
		switch (featureID) {
			case ClassassociationPackage.LABEL__LT:
				setLt((Target)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ClassassociationPackage.LABEL__LT:
				return lt != null;
		}
		return super.eIsSet(featureID);
	}

} //LabelImpl
