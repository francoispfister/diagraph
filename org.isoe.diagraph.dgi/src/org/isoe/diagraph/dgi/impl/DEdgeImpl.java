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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.isoe.diagraph.dgi.DEdge;
import org.isoe.diagraph.dgi.DNode;
import org.isoe.diagraph.dgi.DgiPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>DEdge</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.isoe.diagraph.dgi.impl.DEdgeImpl#getDSourceNode <em>DSource Node</em>}</li>
 *   <li>{@link org.isoe.diagraph.dgi.impl.DEdgeImpl#getDTargetNode <em>DTarget Node</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class DEdgeImpl extends EObjectImpl implements DEdge {
	/**
	 * The cached value of the '{@link #getDSourceNode() <em>DSource Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDSourceNode()
	 * @generated
	 * @ordered
	 */
	protected DNode dSourceNode;

	/**
	 * The cached value of the '{@link #getDTargetNode() <em>DTarget Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDTargetNode()
	 * @generated
	 * @ordered
	 */
	protected DNode dTargetNode;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DEdgeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DgiPackage.Literals.DEDGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DNode getDSourceNode() {
		if (dSourceNode != null && dSourceNode.eIsProxy()) {
			InternalEObject oldDSourceNode = (InternalEObject)dSourceNode;
			dSourceNode = (DNode)eResolveProxy(oldDSourceNode);
			if (dSourceNode != oldDSourceNode) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DgiPackage.DEDGE__DSOURCE_NODE, oldDSourceNode, dSourceNode));
			}
		}
		return dSourceNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DNode basicGetDSourceNode() {
		return dSourceNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDSourceNode(DNode newDSourceNode) {
		DNode oldDSourceNode = dSourceNode;
		dSourceNode = newDSourceNode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DgiPackage.DEDGE__DSOURCE_NODE, oldDSourceNode, dSourceNode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DNode getDTargetNode() {
		if (dTargetNode != null && dTargetNode.eIsProxy()) {
			InternalEObject oldDTargetNode = (InternalEObject)dTargetNode;
			dTargetNode = (DNode)eResolveProxy(oldDTargetNode);
			if (dTargetNode != oldDTargetNode) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DgiPackage.DEDGE__DTARGET_NODE, oldDTargetNode, dTargetNode));
			}
		}
		return dTargetNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DNode basicGetDTargetNode() {
		return dTargetNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDTargetNode(DNode newDTargetNode) {
		DNode oldDTargetNode = dTargetNode;
		dTargetNode = newDTargetNode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DgiPackage.DEDGE__DTARGET_NODE, oldDTargetNode, dTargetNode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DgiPackage.DEDGE__DSOURCE_NODE:
				if (resolve) return getDSourceNode();
				return basicGetDSourceNode();
			case DgiPackage.DEDGE__DTARGET_NODE:
				if (resolve) return getDTargetNode();
				return basicGetDTargetNode();
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
			case DgiPackage.DEDGE__DSOURCE_NODE:
				setDSourceNode((DNode)newValue);
				return;
			case DgiPackage.DEDGE__DTARGET_NODE:
				setDTargetNode((DNode)newValue);
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
			case DgiPackage.DEDGE__DSOURCE_NODE:
				setDSourceNode((DNode)null);
				return;
			case DgiPackage.DEDGE__DTARGET_NODE:
				setDTargetNode((DNode)null);
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
			case DgiPackage.DEDGE__DSOURCE_NODE:
				return dSourceNode != null;
			case DgiPackage.DEDGE__DTARGET_NODE:
				return dTargetNode != null;
		}
		return super.eIsSet(featureID);
	}

} //DEdgeImpl
