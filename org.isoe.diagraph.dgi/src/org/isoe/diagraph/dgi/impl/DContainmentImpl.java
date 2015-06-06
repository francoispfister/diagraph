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

import org.isoe.diagraph.dgi.DContainment;
import org.isoe.diagraph.dgi.DEdge;
import org.isoe.diagraph.dgi.DNode;
import org.isoe.diagraph.dgi.DgiPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>DContainment</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.isoe.diagraph.dgi.impl.DContainmentImpl#getDSourceNode <em>DSource Node</em>}</li>
 *   <li>{@link org.isoe.diagraph.dgi.impl.DContainmentImpl#getDTargetNode <em>DTarget Node</em>}</li>
 *   <li>{@link org.isoe.diagraph.dgi.impl.DContainmentImpl#isCompartment <em>Compartment</em>}</li>
 *   <li>{@link org.isoe.diagraph.dgi.impl.DContainmentImpl#isPort <em>Port</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DContainmentImpl extends DLabelledElementImpl implements DContainment {
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
	 * The default value of the '{@link #isCompartment() <em>Compartment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCompartment()
	 * @generated
	 * @ordered
	 */
	protected static final boolean COMPARTMENT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isCompartment() <em>Compartment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCompartment()
	 * @generated
	 * @ordered
	 */
	protected boolean compartment = COMPARTMENT_EDEFAULT;

	/**
	 * The default value of the '{@link #isPort() <em>Port</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPort()
	 * @generated
	 * @ordered
	 */
	protected static final boolean PORT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isPort() <em>Port</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPort()
	 * @generated
	 * @ordered
	 */
	protected boolean port = PORT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DContainmentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DgiPackage.Literals.DCONTAINMENT;
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DgiPackage.DCONTAINMENT__DSOURCE_NODE, oldDSourceNode, dSourceNode));
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
			eNotify(new ENotificationImpl(this, Notification.SET, DgiPackage.DCONTAINMENT__DSOURCE_NODE, oldDSourceNode, dSourceNode));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DgiPackage.DCONTAINMENT__DTARGET_NODE, oldDTargetNode, dTargetNode));
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
			eNotify(new ENotificationImpl(this, Notification.SET, DgiPackage.DCONTAINMENT__DTARGET_NODE, oldDTargetNode, dTargetNode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isCompartment() {
		return compartment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCompartment(boolean newCompartment) {
		boolean oldCompartment = compartment;
		compartment = newCompartment;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DgiPackage.DCONTAINMENT__COMPARTMENT, oldCompartment, compartment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isPort() {
		return port;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPort(boolean newPort) {
		boolean oldPort = port;
		port = newPort;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DgiPackage.DCONTAINMENT__PORT, oldPort, port));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DgiPackage.DCONTAINMENT__DSOURCE_NODE:
				if (resolve) return getDSourceNode();
				return basicGetDSourceNode();
			case DgiPackage.DCONTAINMENT__DTARGET_NODE:
				if (resolve) return getDTargetNode();
				return basicGetDTargetNode();
			case DgiPackage.DCONTAINMENT__COMPARTMENT:
				return isCompartment();
			case DgiPackage.DCONTAINMENT__PORT:
				return isPort();
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
			case DgiPackage.DCONTAINMENT__DSOURCE_NODE:
				setDSourceNode((DNode)newValue);
				return;
			case DgiPackage.DCONTAINMENT__DTARGET_NODE:
				setDTargetNode((DNode)newValue);
				return;
			case DgiPackage.DCONTAINMENT__COMPARTMENT:
				setCompartment((Boolean)newValue);
				return;
			case DgiPackage.DCONTAINMENT__PORT:
				setPort((Boolean)newValue);
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
			case DgiPackage.DCONTAINMENT__DSOURCE_NODE:
				setDSourceNode((DNode)null);
				return;
			case DgiPackage.DCONTAINMENT__DTARGET_NODE:
				setDTargetNode((DNode)null);
				return;
			case DgiPackage.DCONTAINMENT__COMPARTMENT:
				setCompartment(COMPARTMENT_EDEFAULT);
				return;
			case DgiPackage.DCONTAINMENT__PORT:
				setPort(PORT_EDEFAULT);
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
			case DgiPackage.DCONTAINMENT__DSOURCE_NODE:
				return dSourceNode != null;
			case DgiPackage.DCONTAINMENT__DTARGET_NODE:
				return dTargetNode != null;
			case DgiPackage.DCONTAINMENT__COMPARTMENT:
				return compartment != COMPARTMENT_EDEFAULT;
			case DgiPackage.DCONTAINMENT__PORT:
				return port != PORT_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == DEdge.class) {
			switch (derivedFeatureID) {
				case DgiPackage.DCONTAINMENT__DSOURCE_NODE: return DgiPackage.DEDGE__DSOURCE_NODE;
				case DgiPackage.DCONTAINMENT__DTARGET_NODE: return DgiPackage.DEDGE__DTARGET_NODE;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == DEdge.class) {
			switch (baseFeatureID) {
				case DgiPackage.DEDGE__DSOURCE_NODE: return DgiPackage.DCONTAINMENT__DSOURCE_NODE;
				case DgiPackage.DEDGE__DTARGET_NODE: return DgiPackage.DCONTAINMENT__DTARGET_NODE;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(" (compartment: ");
		result.append(compartment);
		result.append(", port: ");
		result.append(port);
		result.append(')');
		return result.toString();
	}

} //DContainmentImpl
