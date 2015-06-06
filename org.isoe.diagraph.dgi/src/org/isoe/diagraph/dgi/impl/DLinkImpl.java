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
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.isoe.diagraph.dgi.DEdge;
import org.isoe.diagraph.dgi.DLink;
import org.isoe.diagraph.dgi.DNode;
import org.isoe.diagraph.dgi.DgiPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>DLink</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.isoe.diagraph.dgi.impl.DLinkImpl#getDSourceNode <em>DSource Node</em>}</li>
 *   <li>{@link org.isoe.diagraph.dgi.impl.DLinkImpl#getDTargetNode <em>DTarget Node</em>}</li>
 *   <li>{@link org.isoe.diagraph.dgi.impl.DLinkImpl#getESourceModelElement <em>ESource Model Element</em>}</li>
 *   <li>{@link org.isoe.diagraph.dgi.impl.DLinkImpl#getETargetModelElement <em>ETarget Model Element</em>}</li>
 *   <li>{@link org.isoe.diagraph.dgi.impl.DLinkImpl#isReversedSource <em>Reversed Source</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DLinkImpl extends DElementImpl implements DLink {
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
	 * The cached value of the '{@link #getESourceModelElement() <em>ESource Model Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getESourceModelElement()
	 * @generated
	 * @ordered
	 */
	protected EModelElement eSourceModelElement;

	/**
	 * The cached value of the '{@link #getETargetModelElement() <em>ETarget Model Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getETargetModelElement()
	 * @generated
	 * @ordered
	 */
	protected EModelElement eTargetModelElement;

	/**
	 * The default value of the '{@link #isReversedSource() <em>Reversed Source</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReversedSource()
	 * @generated
	 * @ordered
	 */
	protected static final boolean REVERSED_SOURCE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isReversedSource() <em>Reversed Source</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReversedSource()
	 * @generated
	 * @ordered
	 */
	protected boolean reversedSource = REVERSED_SOURCE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DLinkImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DgiPackage.Literals.DLINK;
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DgiPackage.DLINK__DSOURCE_NODE, oldDSourceNode, dSourceNode));
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
			eNotify(new ENotificationImpl(this, Notification.SET, DgiPackage.DLINK__DSOURCE_NODE, oldDSourceNode, dSourceNode));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DgiPackage.DLINK__DTARGET_NODE, oldDTargetNode, dTargetNode));
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
			eNotify(new ENotificationImpl(this, Notification.SET, DgiPackage.DLINK__DTARGET_NODE, oldDTargetNode, dTargetNode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EModelElement getESourceModelElement() {
		if (eSourceModelElement != null && eSourceModelElement.eIsProxy()) {
			InternalEObject oldESourceModelElement = (InternalEObject)eSourceModelElement;
			eSourceModelElement = (EModelElement)eResolveProxy(oldESourceModelElement);
			if (eSourceModelElement != oldESourceModelElement) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DgiPackage.DLINK__ESOURCE_MODEL_ELEMENT, oldESourceModelElement, eSourceModelElement));
			}
		}
		return eSourceModelElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EModelElement basicGetESourceModelElement() {
		return eSourceModelElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setESourceModelElement(EModelElement newESourceModelElement) {
		EModelElement oldESourceModelElement = eSourceModelElement;
		eSourceModelElement = newESourceModelElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DgiPackage.DLINK__ESOURCE_MODEL_ELEMENT, oldESourceModelElement, eSourceModelElement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EModelElement getETargetModelElement() {
		if (eTargetModelElement != null && eTargetModelElement.eIsProxy()) {
			InternalEObject oldETargetModelElement = (InternalEObject)eTargetModelElement;
			eTargetModelElement = (EModelElement)eResolveProxy(oldETargetModelElement);
			if (eTargetModelElement != oldETargetModelElement) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DgiPackage.DLINK__ETARGET_MODEL_ELEMENT, oldETargetModelElement, eTargetModelElement));
			}
		}
		return eTargetModelElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EModelElement basicGetETargetModelElement() {
		return eTargetModelElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setETargetModelElement(EModelElement newETargetModelElement) {
		EModelElement oldETargetModelElement = eTargetModelElement;
		eTargetModelElement = newETargetModelElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DgiPackage.DLINK__ETARGET_MODEL_ELEMENT, oldETargetModelElement, eTargetModelElement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isReversedSource() {
		return reversedSource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReversedSource(boolean newReversedSource) {
		boolean oldReversedSource = reversedSource;
		reversedSource = newReversedSource;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DgiPackage.DLINK__REVERSED_SOURCE, oldReversedSource, reversedSource));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DgiPackage.DLINK__DSOURCE_NODE:
				if (resolve) return getDSourceNode();
				return basicGetDSourceNode();
			case DgiPackage.DLINK__DTARGET_NODE:
				if (resolve) return getDTargetNode();
				return basicGetDTargetNode();
			case DgiPackage.DLINK__ESOURCE_MODEL_ELEMENT:
				if (resolve) return getESourceModelElement();
				return basicGetESourceModelElement();
			case DgiPackage.DLINK__ETARGET_MODEL_ELEMENT:
				if (resolve) return getETargetModelElement();
				return basicGetETargetModelElement();
			case DgiPackage.DLINK__REVERSED_SOURCE:
				return isReversedSource();
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
			case DgiPackage.DLINK__DSOURCE_NODE:
				setDSourceNode((DNode)newValue);
				return;
			case DgiPackage.DLINK__DTARGET_NODE:
				setDTargetNode((DNode)newValue);
				return;
			case DgiPackage.DLINK__ESOURCE_MODEL_ELEMENT:
				setESourceModelElement((EModelElement)newValue);
				return;
			case DgiPackage.DLINK__ETARGET_MODEL_ELEMENT:
				setETargetModelElement((EModelElement)newValue);
				return;
			case DgiPackage.DLINK__REVERSED_SOURCE:
				setReversedSource((Boolean)newValue);
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
			case DgiPackage.DLINK__DSOURCE_NODE:
				setDSourceNode((DNode)null);
				return;
			case DgiPackage.DLINK__DTARGET_NODE:
				setDTargetNode((DNode)null);
				return;
			case DgiPackage.DLINK__ESOURCE_MODEL_ELEMENT:
				setESourceModelElement((EModelElement)null);
				return;
			case DgiPackage.DLINK__ETARGET_MODEL_ELEMENT:
				setETargetModelElement((EModelElement)null);
				return;
			case DgiPackage.DLINK__REVERSED_SOURCE:
				setReversedSource(REVERSED_SOURCE_EDEFAULT);
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
			case DgiPackage.DLINK__DSOURCE_NODE:
				return dSourceNode != null;
			case DgiPackage.DLINK__DTARGET_NODE:
				return dTargetNode != null;
			case DgiPackage.DLINK__ESOURCE_MODEL_ELEMENT:
				return eSourceModelElement != null;
			case DgiPackage.DLINK__ETARGET_MODEL_ELEMENT:
				return eTargetModelElement != null;
			case DgiPackage.DLINK__REVERSED_SOURCE:
				return reversedSource != REVERSED_SOURCE_EDEFAULT;
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
				case DgiPackage.DLINK__DSOURCE_NODE: return DgiPackage.DEDGE__DSOURCE_NODE;
				case DgiPackage.DLINK__DTARGET_NODE: return DgiPackage.DEDGE__DTARGET_NODE;
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
				case DgiPackage.DEDGE__DSOURCE_NODE: return DgiPackage.DLINK__DSOURCE_NODE;
				case DgiPackage.DEDGE__DTARGET_NODE: return DgiPackage.DLINK__DTARGET_NODE;
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
		result.append(" (reversedSource: ");
		result.append(reversedSource);
		result.append(')');
		return result.toString();
	}

} //DLinkImpl
