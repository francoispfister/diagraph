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

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.isoe.diagraph.megamodel.MegamodelElement;
import org.isoe.diagraph.megamodel.MegamodelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.isoe.diagraph.megamodel.impl.MegamodelElementImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.impl.MegamodelElementImpl#getURI <em>URI</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class MegamodelElementImpl extends EObjectImpl implements MegamodelElement {
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
	 * The default value of the '{@link #getURI() <em>URI</em>}' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see #getURI()
	 * @generated
	 * @ordered
	 */
   protected static final String URI_EDEFAULT = null;

   /**
	 * The cached value of the '{@link #getURI() <em>URI</em>}' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see #getURI()
	 * @generated
	 * @ordered
	 */
   protected String uri = URI_EDEFAULT;

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   protected MegamodelElementImpl() {
		super();
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   @Override
   protected EClass eStaticClass() {
		return MegamodelPackage.Literals.MEGAMODEL_ELEMENT;
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
			eNotify(new ENotificationImpl(this, Notification.SET, MegamodelPackage.MEGAMODEL_ELEMENT__NAME, oldName, name));
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public String getURI() {
		return uri;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public void setURI_(String newURI) {
		String oldURI = uri;
		uri = newURI;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MegamodelPackage.MEGAMODEL_ELEMENT__URI, oldURI, uri));
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   @Override
   public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MegamodelPackage.MEGAMODEL_ELEMENT__NAME:
				return getName();
			case MegamodelPackage.MEGAMODEL_ELEMENT__URI:
				return getURI();
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
			case MegamodelPackage.MEGAMODEL_ELEMENT__NAME:
				setName((String)newValue);
				return;
			case MegamodelPackage.MEGAMODEL_ELEMENT__URI:
				setURI_((String)newValue);
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
			case MegamodelPackage.MEGAMODEL_ELEMENT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case MegamodelPackage.MEGAMODEL_ELEMENT__URI:
				setURI_(URI_EDEFAULT);
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
			case MegamodelPackage.MEGAMODEL_ELEMENT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case MegamodelPackage.MEGAMODEL_ELEMENT__URI:
				return URI_EDEFAULT == null ? uri != null : !URI_EDEFAULT.equals(uri);
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
		result.append(", URI: ");
		result.append(uri);
		result.append(')');
		return result.toString();
	}

} //MegamodelElementImpl
