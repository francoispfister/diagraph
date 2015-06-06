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
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.isoe.diagraph.megamodel.MegamodelPackage;
import org.isoe.diagraph.megamodel.Navigation;
import org.isoe.diagraph.megamodel.Notation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Navigation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.isoe.diagraph.megamodel.impl.NavigationImpl#getFrom <em>From</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.impl.NavigationImpl#getTo <em>To</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.impl.NavigationImpl#getId <em>Id</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NavigationImpl extends EObjectImpl implements Navigation {
   /**
	 * The cached value of the '{@link #getFrom() <em>From</em>}' reference.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see #getFrom()
	 * @generated
	 * @ordered
	 */
   protected Notation from;

   /**
	 * The cached value of the '{@link #getTo() <em>To</em>}' reference.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see #getTo()
	 * @generated
	 * @ordered
	 */
   protected Notation to;

   /**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
   protected static final String ID_EDEFAULT = null;

   /**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
   protected String id = ID_EDEFAULT;

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   protected NavigationImpl() {
		super();
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   @Override
   protected EClass eStaticClass() {
		return MegamodelPackage.Literals.NAVIGATION;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public Notation getFrom() {
		if (from != null && from.eIsProxy()) {
			InternalEObject oldFrom = (InternalEObject)from;
			from = (Notation)eResolveProxy(oldFrom);
			if (from != oldFrom) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MegamodelPackage.NAVIGATION__FROM, oldFrom, from));
			}
		}
		return from;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public Notation basicGetFrom() {
		return from;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public void setFrom(Notation newFrom) {
		Notation oldFrom = from;
		from = newFrom;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MegamodelPackage.NAVIGATION__FROM, oldFrom, from));
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public Notation getTo() {
		if (to != null && to.eIsProxy()) {
			InternalEObject oldTo = (InternalEObject)to;
			to = (Notation)eResolveProxy(oldTo);
			if (to != oldTo) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MegamodelPackage.NAVIGATION__TO, oldTo, to));
			}
		}
		return to;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public Notation basicGetTo() {
		return to;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public void setTo(Notation newTo) {
		Notation oldTo = to;
		to = newTo;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MegamodelPackage.NAVIGATION__TO, oldTo, to));
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public String getId() {
		return id;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MegamodelPackage.NAVIGATION__ID, oldId, id));
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   @Override
   public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MegamodelPackage.NAVIGATION__FROM:
				if (resolve) return getFrom();
				return basicGetFrom();
			case MegamodelPackage.NAVIGATION__TO:
				if (resolve) return getTo();
				return basicGetTo();
			case MegamodelPackage.NAVIGATION__ID:
				return getId();
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
			case MegamodelPackage.NAVIGATION__FROM:
				setFrom((Notation)newValue);
				return;
			case MegamodelPackage.NAVIGATION__TO:
				setTo((Notation)newValue);
				return;
			case MegamodelPackage.NAVIGATION__ID:
				setId((String)newValue);
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
			case MegamodelPackage.NAVIGATION__FROM:
				setFrom((Notation)null);
				return;
			case MegamodelPackage.NAVIGATION__TO:
				setTo((Notation)null);
				return;
			case MegamodelPackage.NAVIGATION__ID:
				setId(ID_EDEFAULT);
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
			case MegamodelPackage.NAVIGATION__FROM:
				return from != null;
			case MegamodelPackage.NAVIGATION__TO:
				return to != null;
			case MegamodelPackage.NAVIGATION__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
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
		result.append(" (id: ");
		result.append(id);
		result.append(')');
		return result.toString();
	}

} //NavigationImpl
