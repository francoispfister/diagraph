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
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;

import org.isoe.diagraph.dgi.DLabel;
import org.isoe.diagraph.dgi.DLabelledElement;
import org.isoe.diagraph.dgi.DgiPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>DLabel</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.isoe.diagraph.dgi.impl.DLabelImpl#getDLabelledElement <em>DLabelled Element</em>}</li>
 *   <li>{@link org.isoe.diagraph.dgi.impl.DLabelImpl#isFromSuperElement <em>From Super Element</em>}</li>
 *   <li>{@link org.isoe.diagraph.dgi.impl.DLabelImpl#isInferred <em>Inferred</em>}</li>
 *   <li>{@link org.isoe.diagraph.dgi.impl.DLabelImpl#getEAttributeModelElement <em>EAttribute Model Element</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DLabelImpl extends DNamedElementImpl implements DLabel {
	/**
	 * The default value of the '{@link #isFromSuperElement() <em>From Super Element</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFromSuperElement()
	 * @generated
	 * @ordered
	 */
	protected static final boolean FROM_SUPER_ELEMENT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isFromSuperElement() <em>From Super Element</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFromSuperElement()
	 * @generated
	 * @ordered
	 */
	protected boolean fromSuperElement = FROM_SUPER_ELEMENT_EDEFAULT;

	/**
	 * The default value of the '{@link #isInferred() <em>Inferred</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isInferred()
	 * @generated
	 * @ordered
	 */
	protected static final boolean INFERRED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isInferred() <em>Inferred</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isInferred()
	 * @generated
	 * @ordered
	 */
	protected boolean inferred = INFERRED_EDEFAULT;

	/**
	 * The cached value of the '{@link #getEAttributeModelElement() <em>EAttribute Model Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEAttributeModelElement()
	 * @generated
	 * @ordered
	 */
	protected EModelElement eAttributeModelElement;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DLabelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DgiPackage.Literals.DLABEL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DLabelledElement getDLabelledElement() {
		if (eContainerFeatureID() != DgiPackage.DLABEL__DLABELLED_ELEMENT) return null;
		return (DLabelledElement)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDLabelledElement(DLabelledElement newDLabelledElement, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newDLabelledElement, DgiPackage.DLABEL__DLABELLED_ELEMENT, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDLabelledElement(DLabelledElement newDLabelledElement) {
		if (newDLabelledElement != eInternalContainer() || (eContainerFeatureID() != DgiPackage.DLABEL__DLABELLED_ELEMENT && newDLabelledElement != null)) {
			if (EcoreUtil.isAncestor(this, newDLabelledElement))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newDLabelledElement != null)
				msgs = ((InternalEObject)newDLabelledElement).eInverseAdd(this, DgiPackage.DLABELLED_ELEMENT__DLABELS, DLabelledElement.class, msgs);
			msgs = basicSetDLabelledElement(newDLabelledElement, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DgiPackage.DLABEL__DLABELLED_ELEMENT, newDLabelledElement, newDLabelledElement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isFromSuperElement() {
		return fromSuperElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFromSuperElement(boolean newFromSuperElement) {
		boolean oldFromSuperElement = fromSuperElement;
		fromSuperElement = newFromSuperElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DgiPackage.DLABEL__FROM_SUPER_ELEMENT, oldFromSuperElement, fromSuperElement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isInferred() {
		return inferred;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInferred(boolean newInferred) {
		boolean oldInferred = inferred;
		inferred = newInferred;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DgiPackage.DLABEL__INFERRED, oldInferred, inferred));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EModelElement getEAttributeModelElement() {
		if (eAttributeModelElement != null && eAttributeModelElement.eIsProxy()) {
			InternalEObject oldEAttributeModelElement = (InternalEObject)eAttributeModelElement;
			eAttributeModelElement = (EModelElement)eResolveProxy(oldEAttributeModelElement);
			if (eAttributeModelElement != oldEAttributeModelElement) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DgiPackage.DLABEL__EATTRIBUTE_MODEL_ELEMENT, oldEAttributeModelElement, eAttributeModelElement));
			}
		}
		return eAttributeModelElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EModelElement basicGetEAttributeModelElement() {
		return eAttributeModelElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEAttributeModelElement(EModelElement newEAttributeModelElement) {
		EModelElement oldEAttributeModelElement = eAttributeModelElement;
		eAttributeModelElement = newEAttributeModelElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DgiPackage.DLABEL__EATTRIBUTE_MODEL_ELEMENT, oldEAttributeModelElement, eAttributeModelElement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DgiPackage.DLABEL__DLABELLED_ELEMENT:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetDLabelledElement((DLabelledElement)otherEnd, msgs);
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
			case DgiPackage.DLABEL__DLABELLED_ELEMENT:
				return basicSetDLabelledElement(null, msgs);
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
			case DgiPackage.DLABEL__DLABELLED_ELEMENT:
				return eInternalContainer().eInverseRemove(this, DgiPackage.DLABELLED_ELEMENT__DLABELS, DLabelledElement.class, msgs);
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
			case DgiPackage.DLABEL__DLABELLED_ELEMENT:
				return getDLabelledElement();
			case DgiPackage.DLABEL__FROM_SUPER_ELEMENT:
				return isFromSuperElement();
			case DgiPackage.DLABEL__INFERRED:
				return isInferred();
			case DgiPackage.DLABEL__EATTRIBUTE_MODEL_ELEMENT:
				if (resolve) return getEAttributeModelElement();
				return basicGetEAttributeModelElement();
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
			case DgiPackage.DLABEL__DLABELLED_ELEMENT:
				setDLabelledElement((DLabelledElement)newValue);
				return;
			case DgiPackage.DLABEL__FROM_SUPER_ELEMENT:
				setFromSuperElement((Boolean)newValue);
				return;
			case DgiPackage.DLABEL__INFERRED:
				setInferred((Boolean)newValue);
				return;
			case DgiPackage.DLABEL__EATTRIBUTE_MODEL_ELEMENT:
				setEAttributeModelElement((EModelElement)newValue);
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
			case DgiPackage.DLABEL__DLABELLED_ELEMENT:
				setDLabelledElement((DLabelledElement)null);
				return;
			case DgiPackage.DLABEL__FROM_SUPER_ELEMENT:
				setFromSuperElement(FROM_SUPER_ELEMENT_EDEFAULT);
				return;
			case DgiPackage.DLABEL__INFERRED:
				setInferred(INFERRED_EDEFAULT);
				return;
			case DgiPackage.DLABEL__EATTRIBUTE_MODEL_ELEMENT:
				setEAttributeModelElement((EModelElement)null);
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
			case DgiPackage.DLABEL__DLABELLED_ELEMENT:
				return getDLabelledElement() != null;
			case DgiPackage.DLABEL__FROM_SUPER_ELEMENT:
				return fromSuperElement != FROM_SUPER_ELEMENT_EDEFAULT;
			case DgiPackage.DLABEL__INFERRED:
				return inferred != INFERRED_EDEFAULT;
			case DgiPackage.DLABEL__EATTRIBUTE_MODEL_ELEMENT:
				return eAttributeModelElement != null;
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
		result.append(" (fromSuperElement: ");
		result.append(fromSuperElement);
		result.append(", inferred: ");
		result.append(inferred);
		result.append(')');
		return result.toString();
	}

} //DLabelImpl
