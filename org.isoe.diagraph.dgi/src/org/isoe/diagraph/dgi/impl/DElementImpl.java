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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.isoe.diagraph.dgi.DElement;
import org.isoe.diagraph.dgi.DgiPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>DElement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.isoe.diagraph.dgi.impl.DElementImpl#getEContainmentModelElement <em>EContainment Model Element</em>}</li>
 *   <li>{@link org.isoe.diagraph.dgi.impl.DElementImpl#getEModelElement <em>EModel Element</em>}</li>
 *   <li>{@link org.isoe.diagraph.dgi.impl.DElementImpl#getDLowerElements <em>DLower Elements</em>}</li>
 *   <li>{@link org.isoe.diagraph.dgi.impl.DElementImpl#getDSuperElements <em>DSuper Elements</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class DElementImpl extends DLabelledElementImpl implements DElement {
	/**
	 * The cached value of the '{@link #getEContainmentModelElement() <em>EContainment Model Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEContainmentModelElement()
	 * @generated
	 * @ordered
	 */
	protected EModelElement eContainmentModelElement;

	/**
	 * The cached value of the '{@link #getEModelElement() <em>EModel Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEModelElement()
	 * @generated
	 * @ordered
	 */
	protected EModelElement eModelElement;

	/**
	 * The cached value of the '{@link #getDLowerElements() <em>DLower Elements</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDLowerElements()
	 * @generated
	 * @ordered
	 */
	protected EList<DElement> dLowerElements;

	/**
	 * The cached value of the '{@link #getDSuperElements() <em>DSuper Elements</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDSuperElements()
	 * @generated
	 * @ordered
	 */
	protected EList<DElement> dSuperElements;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DgiPackage.Literals.DELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EModelElement getEContainmentModelElement() {
		if (eContainmentModelElement != null && eContainmentModelElement.eIsProxy()) {
			InternalEObject oldEContainmentModelElement = (InternalEObject)eContainmentModelElement;
			eContainmentModelElement = (EModelElement)eResolveProxy(oldEContainmentModelElement);
			if (eContainmentModelElement != oldEContainmentModelElement) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DgiPackage.DELEMENT__ECONTAINMENT_MODEL_ELEMENT, oldEContainmentModelElement, eContainmentModelElement));
			}
		}
		return eContainmentModelElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EModelElement basicGetEContainmentModelElement() {
		return eContainmentModelElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEContainmentModelElement(EModelElement newEContainmentModelElement) {
		EModelElement oldEContainmentModelElement = eContainmentModelElement;
		eContainmentModelElement = newEContainmentModelElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DgiPackage.DELEMENT__ECONTAINMENT_MODEL_ELEMENT, oldEContainmentModelElement, eContainmentModelElement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EModelElement getEModelElement() {
		if (eModelElement != null && eModelElement.eIsProxy()) {
			InternalEObject oldEModelElement = (InternalEObject)eModelElement;
			eModelElement = (EModelElement)eResolveProxy(oldEModelElement);
			if (eModelElement != oldEModelElement) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DgiPackage.DELEMENT__EMODEL_ELEMENT, oldEModelElement, eModelElement));
			}
		}
		return eModelElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EModelElement basicGetEModelElement() {
		return eModelElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEModelElement(EModelElement newEModelElement) {
		EModelElement oldEModelElement = eModelElement;
		eModelElement = newEModelElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DgiPackage.DELEMENT__EMODEL_ELEMENT, oldEModelElement, eModelElement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DElement> getDLowerElements() {
		if (dLowerElements == null) {
			dLowerElements = new EObjectResolvingEList<DElement>(DElement.class, this, DgiPackage.DELEMENT__DLOWER_ELEMENTS);
		}
		return dLowerElements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DElement> getDSuperElements() {
		if (dSuperElements == null) {
			dSuperElements = new EObjectResolvingEList<DElement>(DElement.class, this, DgiPackage.DELEMENT__DSUPER_ELEMENTS);
		}
		return dSuperElements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DgiPackage.DELEMENT__ECONTAINMENT_MODEL_ELEMENT:
				if (resolve) return getEContainmentModelElement();
				return basicGetEContainmentModelElement();
			case DgiPackage.DELEMENT__EMODEL_ELEMENT:
				if (resolve) return getEModelElement();
				return basicGetEModelElement();
			case DgiPackage.DELEMENT__DLOWER_ELEMENTS:
				return getDLowerElements();
			case DgiPackage.DELEMENT__DSUPER_ELEMENTS:
				return getDSuperElements();
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
			case DgiPackage.DELEMENT__ECONTAINMENT_MODEL_ELEMENT:
				setEContainmentModelElement((EModelElement)newValue);
				return;
			case DgiPackage.DELEMENT__EMODEL_ELEMENT:
				setEModelElement((EModelElement)newValue);
				return;
			case DgiPackage.DELEMENT__DLOWER_ELEMENTS:
				getDLowerElements().clear();
				getDLowerElements().addAll((Collection<? extends DElement>)newValue);
				return;
			case DgiPackage.DELEMENT__DSUPER_ELEMENTS:
				getDSuperElements().clear();
				getDSuperElements().addAll((Collection<? extends DElement>)newValue);
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
			case DgiPackage.DELEMENT__ECONTAINMENT_MODEL_ELEMENT:
				setEContainmentModelElement((EModelElement)null);
				return;
			case DgiPackage.DELEMENT__EMODEL_ELEMENT:
				setEModelElement((EModelElement)null);
				return;
			case DgiPackage.DELEMENT__DLOWER_ELEMENTS:
				getDLowerElements().clear();
				return;
			case DgiPackage.DELEMENT__DSUPER_ELEMENTS:
				getDSuperElements().clear();
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
			case DgiPackage.DELEMENT__ECONTAINMENT_MODEL_ELEMENT:
				return eContainmentModelElement != null;
			case DgiPackage.DELEMENT__EMODEL_ELEMENT:
				return eModelElement != null;
			case DgiPackage.DELEMENT__DLOWER_ELEMENTS:
				return dLowerElements != null && !dLowerElements.isEmpty();
			case DgiPackage.DELEMENT__DSUPER_ELEMENTS:
				return dSuperElements != null && !dSuperElements.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //DElementImpl
