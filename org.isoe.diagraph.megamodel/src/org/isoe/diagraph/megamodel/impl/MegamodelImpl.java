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
package org.isoe.diagraph.megamodel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.isoe.diagraph.megamodel.Dsml;
import org.isoe.diagraph.megamodel.Megamodel;
import org.isoe.diagraph.megamodel.MegamodelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Megamodel</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.isoe.diagraph.megamodel.impl.MegamodelImpl#getDsmls <em>Dsmls</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.impl.MegamodelImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MegamodelImpl extends EObjectImpl implements Megamodel {
   /**
	 * The cached value of the '{@link #getDsmls() <em>Dsmls</em>}' containment reference list.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see #getDsmls()
	 * @generated
	 * @ordered
	 */
   protected EList<Dsml> dsmls;

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
   protected MegamodelImpl() {
		super();
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   @Override
   protected EClass eStaticClass() {
		return MegamodelPackage.Literals.MEGAMODEL;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EList<Dsml> getDsmls() {
		if (dsmls == null) {
			dsmls = new EObjectContainmentEList<Dsml>(Dsml.class, this, MegamodelPackage.MEGAMODEL__DSMLS);
		}
		return dsmls;
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
			eNotify(new ENotificationImpl(this, Notification.SET, MegamodelPackage.MEGAMODEL__NAME, oldName, name));
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   @Override
   public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MegamodelPackage.MEGAMODEL__DSMLS:
				return ((InternalEList<?>)getDsmls()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   @Override
   public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MegamodelPackage.MEGAMODEL__DSMLS:
				return getDsmls();
			case MegamodelPackage.MEGAMODEL__NAME:
				return getName();
		}
		return super.eGet(featureID, resolve, coreType);
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   @SuppressWarnings("unchecked")
   @Override
   public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MegamodelPackage.MEGAMODEL__DSMLS:
				getDsmls().clear();
				getDsmls().addAll((Collection<? extends Dsml>)newValue);
				return;
			case MegamodelPackage.MEGAMODEL__NAME:
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
			case MegamodelPackage.MEGAMODEL__DSMLS:
				getDsmls().clear();
				return;
			case MegamodelPackage.MEGAMODEL__NAME:
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
			case MegamodelPackage.MEGAMODEL__DSMLS:
				return dsmls != null && !dsmls.isEmpty();
			case MegamodelPackage.MEGAMODEL__NAME:
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

} //MegamodelImpl
