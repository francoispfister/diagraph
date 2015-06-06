/**
 */
package org.isoe.diagraph.diagraph.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.isoe.diagraph.diagraph.DNode;
import org.isoe.diagraph.diagraph.DOwnedElement;
import org.isoe.diagraph.diagraph.DiagraphPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>DOwned Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DOwnedElementImpl#getOwner <em>Owner</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class DOwnedElementImpl extends MinimalEObjectImpl.Container implements DOwnedElement {
	/**
	 * The cached value of the '{@link #getOwner() <em>Owner</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwner()
	 * @generated
	 * @ordered
	 */
	protected DNode owner;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DOwnedElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DiagraphPackage.Literals.DOWNED_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DNode getOwner() {
		if (owner != null && owner.eIsProxy()) {
			InternalEObject oldOwner = (InternalEObject)owner;
			owner = (DNode)eResolveProxy(oldOwner);
			if (owner != oldOwner) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DiagraphPackage.DOWNED_ELEMENT__OWNER, oldOwner, owner));
			}
		}
		return owner;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DNode basicGetOwner() {
		return owner;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOwner(DNode newOwner) {
		DNode oldOwner = owner;
		owner = newOwner;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagraphPackage.DOWNED_ELEMENT__OWNER, oldOwner, owner));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DiagraphPackage.DOWNED_ELEMENT__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
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
			case DiagraphPackage.DOWNED_ELEMENT__OWNER:
				setOwner((DNode)newValue);
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
			case DiagraphPackage.DOWNED_ELEMENT__OWNER:
				setOwner((DNode)null);
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
			case DiagraphPackage.DOWNED_ELEMENT__OWNER:
				return owner != null;
		}
		return super.eIsSet(featureID);
	}

} //DOwnedElementImpl
