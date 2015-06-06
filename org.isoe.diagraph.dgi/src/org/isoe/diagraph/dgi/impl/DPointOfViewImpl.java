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
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import org.isoe.diagraph.dgi.DNode;
import org.isoe.diagraph.dgi.DPointOfView;
import org.isoe.diagraph.dgi.DgiPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>DPoint Of View</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.isoe.diagraph.dgi.impl.DPointOfViewImpl#getChildren <em>Children</em>}</li>
 *   <li>{@link org.isoe.diagraph.dgi.impl.DPointOfViewImpl#getParent <em>Parent</em>}</li>
 *   <li>{@link org.isoe.diagraph.dgi.impl.DPointOfViewImpl#getDRootNode <em>DRoot Node</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DPointOfViewImpl extends EObjectImpl implements DPointOfView {
	/**
	 * The cached value of the '{@link #getChildren() <em>Children</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildren()
	 * @generated
	 * @ordered
	 */
	protected EList<DPointOfView> children;

	/**
	 * The cached value of the '{@link #getDRootNode() <em>DRoot Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDRootNode()
	 * @generated
	 * @ordered
	 */
	protected DNode dRootNode;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DPointOfViewImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DgiPackage.Literals.DPOINT_OF_VIEW;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DPointOfView> getChildren() {
		if (children == null) {
			children = new EObjectContainmentWithInverseEList<DPointOfView>(DPointOfView.class, this, DgiPackage.DPOINT_OF_VIEW__CHILDREN, DgiPackage.DPOINT_OF_VIEW__PARENT);
		}
		return children;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DPointOfView getParent() {
		if (eContainerFeatureID() != DgiPackage.DPOINT_OF_VIEW__PARENT) return null;
		return (DPointOfView)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParent(DPointOfView newParent, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newParent, DgiPackage.DPOINT_OF_VIEW__PARENT, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParent(DPointOfView newParent) {
		if (newParent != eInternalContainer() || (eContainerFeatureID() != DgiPackage.DPOINT_OF_VIEW__PARENT && newParent != null)) {
			if (EcoreUtil.isAncestor(this, newParent))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newParent != null)
				msgs = ((InternalEObject)newParent).eInverseAdd(this, DgiPackage.DPOINT_OF_VIEW__CHILDREN, DPointOfView.class, msgs);
			msgs = basicSetParent(newParent, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DgiPackage.DPOINT_OF_VIEW__PARENT, newParent, newParent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DNode getDRootNode() {
		if (dRootNode != null && dRootNode.eIsProxy()) {
			InternalEObject oldDRootNode = (InternalEObject)dRootNode;
			dRootNode = (DNode)eResolveProxy(oldDRootNode);
			if (dRootNode != oldDRootNode) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DgiPackage.DPOINT_OF_VIEW__DROOT_NODE, oldDRootNode, dRootNode));
			}
		}
		return dRootNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DNode basicGetDRootNode() {
		return dRootNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDRootNode(DNode newDRootNode) {
		DNode oldDRootNode = dRootNode;
		dRootNode = newDRootNode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DgiPackage.DPOINT_OF_VIEW__DROOT_NODE, oldDRootNode, dRootNode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
		@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DgiPackage.DPOINT_OF_VIEW__CHILDREN:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getChildren()).basicAdd(otherEnd, msgs);
			case DgiPackage.DPOINT_OF_VIEW__PARENT:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetParent((DPointOfView)otherEnd, msgs);
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
			case DgiPackage.DPOINT_OF_VIEW__CHILDREN:
				return ((InternalEList<?>)getChildren()).basicRemove(otherEnd, msgs);
			case DgiPackage.DPOINT_OF_VIEW__PARENT:
				return basicSetParent(null, msgs);
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
			case DgiPackage.DPOINT_OF_VIEW__PARENT:
				return eInternalContainer().eInverseRemove(this, DgiPackage.DPOINT_OF_VIEW__CHILDREN, DPointOfView.class, msgs);
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
			case DgiPackage.DPOINT_OF_VIEW__CHILDREN:
				return getChildren();
			case DgiPackage.DPOINT_OF_VIEW__PARENT:
				return getParent();
			case DgiPackage.DPOINT_OF_VIEW__DROOT_NODE:
				if (resolve) return getDRootNode();
				return basicGetDRootNode();
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
			case DgiPackage.DPOINT_OF_VIEW__CHILDREN:
				getChildren().clear();
				getChildren().addAll((Collection<? extends DPointOfView>)newValue);
				return;
			case DgiPackage.DPOINT_OF_VIEW__PARENT:
				setParent((DPointOfView)newValue);
				return;
			case DgiPackage.DPOINT_OF_VIEW__DROOT_NODE:
				setDRootNode((DNode)newValue);
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
			case DgiPackage.DPOINT_OF_VIEW__CHILDREN:
				getChildren().clear();
				return;
			case DgiPackage.DPOINT_OF_VIEW__PARENT:
				setParent((DPointOfView)null);
				return;
			case DgiPackage.DPOINT_OF_VIEW__DROOT_NODE:
				setDRootNode((DNode)null);
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
			case DgiPackage.DPOINT_OF_VIEW__CHILDREN:
				return children != null && !children.isEmpty();
			case DgiPackage.DPOINT_OF_VIEW__PARENT:
				return getParent() != null;
			case DgiPackage.DPOINT_OF_VIEW__DROOT_NODE:
				return dRootNode != null;
		}
		return super.eIsSet(featureID);
	}

} //DPointOfViewImpl
