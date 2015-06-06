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
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;

import org.isoe.diagraph.megamodel.Dsml;
import org.isoe.diagraph.megamodel.MegamodelPackage;
import org.isoe.diagraph.megamodel.RelatedTo;
import org.isoe.diagraph.megamodel.Relation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Related To</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.isoe.diagraph.megamodel.impl.RelatedToImpl#getFrom <em>From</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.impl.RelatedToImpl#getTo <em>To</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.impl.RelatedToImpl#getKind <em>Kind</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RelatedToImpl extends EObjectImpl implements RelatedTo {
   /**
	 * The cached value of the '{@link #getTo() <em>To</em>}' reference.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see #getTo()
	 * @generated
	 * @ordered
	 */
   protected Dsml to;

   /**
	 * The default value of the '{@link #getKind() <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see #getKind()
	 * @generated
	 * @ordered
	 */
   protected static final Relation KIND_EDEFAULT = Relation.REQUIRED;

   /**
	 * The cached value of the '{@link #getKind() <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see #getKind()
	 * @generated
	 * @ordered
	 */
   protected Relation kind = KIND_EDEFAULT;

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   protected RelatedToImpl() {
		super();
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   @Override
   protected EClass eStaticClass() {
		return MegamodelPackage.Literals.RELATED_TO;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public Dsml getFrom() {
		if (eContainerFeatureID() != MegamodelPackage.RELATED_TO__FROM) return null;
		return (Dsml)eContainer();
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public NotificationChain basicSetFrom(Dsml newFrom, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newFrom, MegamodelPackage.RELATED_TO__FROM, msgs);
		return msgs;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public void setFrom(Dsml newFrom) {
		if (newFrom != eInternalContainer() || (eContainerFeatureID() != MegamodelPackage.RELATED_TO__FROM && newFrom != null)) {
			if (EcoreUtil.isAncestor(this, newFrom))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newFrom != null)
				msgs = ((InternalEObject)newFrom).eInverseAdd(this, MegamodelPackage.DSML__RELATIONS, Dsml.class, msgs);
			msgs = basicSetFrom(newFrom, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MegamodelPackage.RELATED_TO__FROM, newFrom, newFrom));
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public Dsml getTo() {
		if (to != null && to.eIsProxy()) {
			InternalEObject oldTo = (InternalEObject)to;
			to = (Dsml)eResolveProxy(oldTo);
			if (to != oldTo) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MegamodelPackage.RELATED_TO__TO, oldTo, to));
			}
		}
		return to;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public Dsml basicGetTo() {
		return to;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public void setTo(Dsml newTo) {
		Dsml oldTo = to;
		to = newTo;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MegamodelPackage.RELATED_TO__TO, oldTo, to));
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public Relation getKind() {
		return kind;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public void setKind(Relation newKind) {
		Relation oldKind = kind;
		kind = newKind == null ? KIND_EDEFAULT : newKind;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MegamodelPackage.RELATED_TO__KIND, oldKind, kind));
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   @Override
   public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MegamodelPackage.RELATED_TO__FROM:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetFrom((Dsml)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   @Override
   public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MegamodelPackage.RELATED_TO__FROM:
				return basicSetFrom(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   @Override
   public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case MegamodelPackage.RELATED_TO__FROM:
				return eInternalContainer().eInverseRemove(this, MegamodelPackage.DSML__RELATIONS, Dsml.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   @Override
   public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MegamodelPackage.RELATED_TO__FROM:
				return getFrom();
			case MegamodelPackage.RELATED_TO__TO:
				if (resolve) return getTo();
				return basicGetTo();
			case MegamodelPackage.RELATED_TO__KIND:
				return getKind();
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
			case MegamodelPackage.RELATED_TO__FROM:
				setFrom((Dsml)newValue);
				return;
			case MegamodelPackage.RELATED_TO__TO:
				setTo((Dsml)newValue);
				return;
			case MegamodelPackage.RELATED_TO__KIND:
				setKind((Relation)newValue);
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
			case MegamodelPackage.RELATED_TO__FROM:
				setFrom((Dsml)null);
				return;
			case MegamodelPackage.RELATED_TO__TO:
				setTo((Dsml)null);
				return;
			case MegamodelPackage.RELATED_TO__KIND:
				setKind(KIND_EDEFAULT);
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
			case MegamodelPackage.RELATED_TO__FROM:
				return getFrom() != null;
			case MegamodelPackage.RELATED_TO__TO:
				return to != null;
			case MegamodelPackage.RELATED_TO__KIND:
				return kind != KIND_EDEFAULT;
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
		result.append(" (kind: ");
		result.append(kind);
		result.append(')');
		return result.toString();
	}

} //RelatedToImpl
