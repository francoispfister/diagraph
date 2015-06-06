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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.isoe.diagraph.megamodel.Dsml;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.isoe.diagraph.megamodel.MegamodelPackage;
import org.isoe.diagraph.megamodel.Navigation;
import org.isoe.diagraph.megamodel.Notation;
import org.isoe.diagraph.megamodel.NotationDiagram;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Notation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.isoe.diagraph.megamodel.impl.NotationImpl#getNavigations <em>Navigations</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.impl.NotationImpl#getNotationBridge <em>Notation Bridge</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.impl.NotationImpl#getDsml <em>Dsml</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NotationImpl extends MegamodelElementImpl implements Notation {
   /**
	 * The cached value of the '{@link #getNavigations() <em>Navigations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see #getNavigations()
	 * @generated
	 * @ordered
	 */
   protected EList<Navigation> navigations;

   /**
	 * The cached value of the '{@link #getNotationBridge() <em>Notation Bridge</em>}' reference.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see #getNotationBridge()
	 * @generated
	 * @ordered
	 */
   protected EObject notationBridge;

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   protected NotationImpl() {
		super();
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   @Override
   protected EClass eStaticClass() {
		return MegamodelPackage.Literals.NOTATION;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EList<Navigation> getNavigations() {
		if (navigations == null) {
			navigations = new EObjectContainmentEList<Navigation>(Navigation.class, this, MegamodelPackage.NOTATION__NAVIGATIONS);
		}
		return navigations;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EObject getNotationBridge() {
		if (notationBridge != null && notationBridge.eIsProxy()) {
			InternalEObject oldNotationBridge = (InternalEObject)notationBridge;
			notationBridge = eResolveProxy(oldNotationBridge);
			if (notationBridge != oldNotationBridge) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MegamodelPackage.NOTATION__NOTATION_BRIDGE, oldNotationBridge, notationBridge));
			}
		}
		return notationBridge;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EObject basicGetNotationBridge() {
		return notationBridge;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public void setNotationBridge(EObject newNotationBridge) {
		EObject oldNotationBridge = notationBridge;
		notationBridge = newNotationBridge;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MegamodelPackage.NOTATION__NOTATION_BRIDGE, oldNotationBridge, notationBridge));
	}

   /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Dsml getDsml() {
		if (eContainerFeatureID() != MegamodelPackage.NOTATION__DSML) return null;
		return (Dsml)eContainer();
	}

			/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDsml(Dsml newDsml, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newDsml, MegamodelPackage.NOTATION__DSML, msgs);
		return msgs;
	}

			/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDsml(Dsml newDsml) {
		if (newDsml != eInternalContainer() || (eContainerFeatureID() != MegamodelPackage.NOTATION__DSML && newDsml != null)) {
			if (EcoreUtil.isAncestor(this, newDsml))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newDsml != null)
				msgs = ((InternalEObject)newDsml).eInverseAdd(this, MegamodelPackage.DSML__NOTATIONS, Dsml.class, msgs);
			msgs = basicSetDsml(newDsml, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MegamodelPackage.NOTATION__DSML, newDsml, newDsml));
	}

			/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MegamodelPackage.NOTATION__DSML:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetDsml((Dsml)otherEnd, msgs);
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
			case MegamodelPackage.NOTATION__NAVIGATIONS:
				return ((InternalEList<?>)getNavigations()).basicRemove(otherEnd, msgs);
			case MegamodelPackage.NOTATION__DSML:
				return basicSetDsml(null, msgs);
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
			case MegamodelPackage.NOTATION__DSML:
				return eInternalContainer().eInverseRemove(this, MegamodelPackage.DSML__NOTATIONS, Dsml.class, msgs);
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
			case MegamodelPackage.NOTATION__NAVIGATIONS:
				return getNavigations();
			case MegamodelPackage.NOTATION__NOTATION_BRIDGE:
				if (resolve) return getNotationBridge();
				return basicGetNotationBridge();
			case MegamodelPackage.NOTATION__DSML:
				return getDsml();
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
			case MegamodelPackage.NOTATION__NAVIGATIONS:
				getNavigations().clear();
				getNavigations().addAll((Collection<? extends Navigation>)newValue);
				return;
			case MegamodelPackage.NOTATION__NOTATION_BRIDGE:
				setNotationBridge((EObject)newValue);
				return;
			case MegamodelPackage.NOTATION__DSML:
				setDsml((Dsml)newValue);
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
			case MegamodelPackage.NOTATION__NAVIGATIONS:
				getNavigations().clear();
				return;
			case MegamodelPackage.NOTATION__NOTATION_BRIDGE:
				setNotationBridge((EObject)null);
				return;
			case MegamodelPackage.NOTATION__DSML:
				setDsml((Dsml)null);
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
			case MegamodelPackage.NOTATION__NAVIGATIONS:
				return navigations != null && !navigations.isEmpty();
			case MegamodelPackage.NOTATION__NOTATION_BRIDGE:
				return notationBridge != null;
			case MegamodelPackage.NOTATION__DSML:
				return getDsml() != null;
		}
		return super.eIsSet(featureID);
	}

} //NotationImpl
