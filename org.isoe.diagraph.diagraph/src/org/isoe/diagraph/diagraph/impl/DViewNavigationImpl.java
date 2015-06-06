/**
 */
package org.isoe.diagraph.diagraph.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;

import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.diagraph.DNode;
import org.isoe.diagraph.diagraph.DViewNavigation;
import org.isoe.diagraph.diagraph.DiagraphPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>DView Navigation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DViewNavigationImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DViewNavigationImpl#getNavigationTarget <em>Navigation Target</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DViewNavigationImpl#getNavigationSource <em>Navigation Source</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DViewNavigationImpl extends MinimalEObjectImpl.Container implements DViewNavigation {
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
	 * The cached value of the '{@link #getNavigationTarget() <em>Navigation Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNavigationTarget()
	 * @generated
	 * @ordered
	 */
	protected DGraph navigationTarget;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DViewNavigationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DiagraphPackage.Literals.DVIEW_NAVIGATION;
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
			eNotify(new ENotificationImpl(this, Notification.SET, DiagraphPackage.DVIEW_NAVIGATION__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DGraph getNavigationTarget() {
		if (navigationTarget != null && navigationTarget.eIsProxy()) {
			InternalEObject oldNavigationTarget = (InternalEObject)navigationTarget;
			navigationTarget = (DGraph)eResolveProxy(oldNavigationTarget);
			if (navigationTarget != oldNavigationTarget) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DiagraphPackage.DVIEW_NAVIGATION__NAVIGATION_TARGET, oldNavigationTarget, navigationTarget));
			}
		}
		return navigationTarget;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DGraph basicGetNavigationTarget() {
		return navigationTarget;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNavigationTarget(DGraph newNavigationTarget) {
		DGraph oldNavigationTarget = navigationTarget;
		navigationTarget = newNavigationTarget;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagraphPackage.DVIEW_NAVIGATION__NAVIGATION_TARGET, oldNavigationTarget, navigationTarget));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DNode getNavigationSource() {
		if (eContainerFeatureID() != DiagraphPackage.DVIEW_NAVIGATION__NAVIGATION_SOURCE) return null;
		return (DNode)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNavigationSource(DNode newNavigationSource, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newNavigationSource, DiagraphPackage.DVIEW_NAVIGATION__NAVIGATION_SOURCE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNavigationSource(DNode newNavigationSource) {
		if (newNavigationSource != eInternalContainer() || (eContainerFeatureID() != DiagraphPackage.DVIEW_NAVIGATION__NAVIGATION_SOURCE && newNavigationSource != null)) {
			if (EcoreUtil.isAncestor(this, newNavigationSource))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newNavigationSource != null)
				msgs = ((InternalEObject)newNavigationSource).eInverseAdd(this, DiagraphPackage.DNODE__VIEW_NAVIGATION, DNode.class, msgs);
			msgs = basicSetNavigationSource(newNavigationSource, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagraphPackage.DVIEW_NAVIGATION__NAVIGATION_SOURCE, newNavigationSource, newNavigationSource));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DiagraphPackage.DVIEW_NAVIGATION__NAVIGATION_SOURCE:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetNavigationSource((DNode)otherEnd, msgs);
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
			case DiagraphPackage.DVIEW_NAVIGATION__NAVIGATION_SOURCE:
				return basicSetNavigationSource(null, msgs);
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
			case DiagraphPackage.DVIEW_NAVIGATION__NAVIGATION_SOURCE:
				return eInternalContainer().eInverseRemove(this, DiagraphPackage.DNODE__VIEW_NAVIGATION, DNode.class, msgs);
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
			case DiagraphPackage.DVIEW_NAVIGATION__ID:
				return getId();
			case DiagraphPackage.DVIEW_NAVIGATION__NAVIGATION_TARGET:
				if (resolve) return getNavigationTarget();
				return basicGetNavigationTarget();
			case DiagraphPackage.DVIEW_NAVIGATION__NAVIGATION_SOURCE:
				return getNavigationSource();
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
			case DiagraphPackage.DVIEW_NAVIGATION__ID:
				setId((String)newValue);
				return;
			case DiagraphPackage.DVIEW_NAVIGATION__NAVIGATION_TARGET:
				setNavigationTarget((DGraph)newValue);
				return;
			case DiagraphPackage.DVIEW_NAVIGATION__NAVIGATION_SOURCE:
				setNavigationSource((DNode)newValue);
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
			case DiagraphPackage.DVIEW_NAVIGATION__ID:
				setId(ID_EDEFAULT);
				return;
			case DiagraphPackage.DVIEW_NAVIGATION__NAVIGATION_TARGET:
				setNavigationTarget((DGraph)null);
				return;
			case DiagraphPackage.DVIEW_NAVIGATION__NAVIGATION_SOURCE:
				setNavigationSource((DNode)null);
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
			case DiagraphPackage.DVIEW_NAVIGATION__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case DiagraphPackage.DVIEW_NAVIGATION__NAVIGATION_TARGET:
				return navigationTarget != null;
			case DiagraphPackage.DVIEW_NAVIGATION__NAVIGATION_SOURCE:
				return getNavigationSource() != null;
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

} //DViewNavigationImpl
