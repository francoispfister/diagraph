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

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.isoe.diagraph.megamodel.Dsml;
import org.isoe.diagraph.megamodel.MegamodelPackage;
import org.isoe.diagraph.megamodel.Model;
import org.isoe.diagraph.megamodel.NotationDiagram;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.isoe.diagraph.megamodel.impl.ModelImpl#getExcerpt <em>Excerpt</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.impl.ModelImpl#getNotationDiagrams <em>Notation Diagrams</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.impl.ModelImpl#getDsml <em>Dsml</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ModelImpl extends MegamodelElementImpl implements Model {
   /**
	 * The default value of the '{@link #getExcerpt() <em>Excerpt</em>}' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see #getExcerpt()
	 * @generated
	 * @ordered
	 */
   protected static final String EXCERPT_EDEFAULT = null;

   /**
	 * The cached value of the '{@link #getExcerpt() <em>Excerpt</em>}' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see #getExcerpt()
	 * @generated
	 * @ordered
	 */
   protected String excerpt = EXCERPT_EDEFAULT;

   /**
	 * The cached value of the '{@link #getNotationDiagrams() <em>Notation Diagrams</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNotationDiagrams()
	 * @generated
	 * @ordered
	 */
	protected EList<NotationDiagram> notationDiagrams;

			/**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   protected ModelImpl() {
		super();
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   @Override
   protected EClass eStaticClass() {
		return MegamodelPackage.Literals.MODEL;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public String getExcerpt() {
		return excerpt;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public void setExcerpt(String newExcerpt) {
		String oldExcerpt = excerpt;
		excerpt = newExcerpt;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MegamodelPackage.MODEL__EXCERPT, oldExcerpt, excerpt));
	}

   /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<NotationDiagram> getNotationDiagrams() {
		if (notationDiagrams == null) {
			notationDiagrams = new EObjectContainmentEList<NotationDiagram>(NotationDiagram.class, this, MegamodelPackage.MODEL__NOTATION_DIAGRAMS);
		}
		return notationDiagrams;
	}

			/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Dsml getDsml() {
		if (eContainerFeatureID() != MegamodelPackage.MODEL__DSML) return null;
		return (Dsml)eContainer();
	}

			/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDsml(Dsml newDsml, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newDsml, MegamodelPackage.MODEL__DSML, msgs);
		return msgs;
	}

			/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDsml(Dsml newDsml) {
		if (newDsml != eInternalContainer() || (eContainerFeatureID() != MegamodelPackage.MODEL__DSML && newDsml != null)) {
			if (EcoreUtil.isAncestor(this, newDsml))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newDsml != null)
				msgs = ((InternalEObject)newDsml).eInverseAdd(this, MegamodelPackage.DSML__MODELS, Dsml.class, msgs);
			msgs = basicSetDsml(newDsml, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MegamodelPackage.MODEL__DSML, newDsml, newDsml));
	}

			/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MegamodelPackage.MODEL__DSML:
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
			case MegamodelPackage.MODEL__NOTATION_DIAGRAMS:
				return ((InternalEList<?>)getNotationDiagrams()).basicRemove(otherEnd, msgs);
			case MegamodelPackage.MODEL__DSML:
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
			case MegamodelPackage.MODEL__DSML:
				return eInternalContainer().eInverseRemove(this, MegamodelPackage.DSML__MODELS, Dsml.class, msgs);
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
			case MegamodelPackage.MODEL__EXCERPT:
				return getExcerpt();
			case MegamodelPackage.MODEL__NOTATION_DIAGRAMS:
				return getNotationDiagrams();
			case MegamodelPackage.MODEL__DSML:
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
			case MegamodelPackage.MODEL__EXCERPT:
				setExcerpt((String)newValue);
				return;
			case MegamodelPackage.MODEL__NOTATION_DIAGRAMS:
				getNotationDiagrams().clear();
				getNotationDiagrams().addAll((Collection<? extends NotationDiagram>)newValue);
				return;
			case MegamodelPackage.MODEL__DSML:
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
			case MegamodelPackage.MODEL__EXCERPT:
				setExcerpt(EXCERPT_EDEFAULT);
				return;
			case MegamodelPackage.MODEL__NOTATION_DIAGRAMS:
				getNotationDiagrams().clear();
				return;
			case MegamodelPackage.MODEL__DSML:
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
			case MegamodelPackage.MODEL__EXCERPT:
				return EXCERPT_EDEFAULT == null ? excerpt != null : !EXCERPT_EDEFAULT.equals(excerpt);
			case MegamodelPackage.MODEL__NOTATION_DIAGRAMS:
				return notationDiagrams != null && !notationDiagrams.isEmpty();
			case MegamodelPackage.MODEL__DSML:
				return getDsml() != null;
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
		result.append(" (excerpt: ");
		result.append(excerpt);
		result.append(')');
		return result.toString();
	}

} //ModelImpl
