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
package org.isoe.diagraph.diastyle.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.isoe.diagraph.diastyle.DBaseStyle;
import org.isoe.diagraph.diastyle.DColor;
import org.isoe.diagraph.diastyle.DStyleBridge;
import org.isoe.diagraph.diastyle.DiastylePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>DBase Style</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.isoe.diagraph.diastyle.impl.DBaseStyleImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.impl.DBaseStyleImpl#getColor <em>Color</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.impl.DBaseStyleImpl#getStyleBridges <em>Style Bridges</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.impl.DBaseStyleImpl#getParent <em>Parent</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.impl.DBaseStyleImpl#getParentName <em>Parent Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class DBaseStyleImpl extends EObjectImpl implements DBaseStyle {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getColor() <em>Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColor()
	 * @generated
	 * @ordered
	 */
	protected static final DColor COLOR_EDEFAULT = DColor.WHITE;

	/**
	 * The cached value of the '{@link #getColor() <em>Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColor()
	 * @generated
	 * @ordered
	 */
	protected DColor color = COLOR_EDEFAULT;

	/**
	 * The cached value of the '{@link #getStyleBridges() <em>Style Bridges</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStyleBridges()
	 * @generated
	 * @ordered
	 */
	protected EList<DStyleBridge> styleBridges;

	/**
	 * The cached value of the '{@link #getParent() <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParent()
	 * @generated
	 * @ordered
	 */
	protected DBaseStyle parent;

	/**
	 * The default value of the '{@link #getParentName() <em>Parent Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParentName()
	 * @generated
	 * @ordered
	 */
	protected static final String PARENT_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getParentName() <em>Parent Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParentName()
	 * @generated
	 * @ordered
	 */
	protected String parentName = PARENT_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DBaseStyleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DiastylePackage.Literals.DBASE_STYLE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiastylePackage.DBASE_STYLE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DColor getColor() {
		return color;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setColor(DColor newColor) {
		DColor oldColor = color;
		color = newColor == null ? COLOR_EDEFAULT : newColor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiastylePackage.DBASE_STYLE__COLOR, oldColor, color));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DStyleBridge> getStyleBridges() {
		if (styleBridges == null) {
			styleBridges = new EObjectContainmentEList<DStyleBridge>(DStyleBridge.class, this, DiastylePackage.DBASE_STYLE__STYLE_BRIDGES);
		}
		return styleBridges;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DBaseStyle getParent() {
		if (parent != null && parent.eIsProxy()) {
			InternalEObject oldParent = (InternalEObject)parent;
			parent = (DBaseStyle)eResolveProxy(oldParent);
			if (parent != oldParent) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DiastylePackage.DBASE_STYLE__PARENT, oldParent, parent));
			}
		}
		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DBaseStyle basicGetParent() {
		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParent(DBaseStyle newParent) {
		DBaseStyle oldParent = parent;
		parent = newParent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiastylePackage.DBASE_STYLE__PARENT, oldParent, parent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getParentName() {
		return parentName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParentName(String newParentName) {
		String oldParentName = parentName;
		parentName = newParentName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiastylePackage.DBASE_STYLE__PARENT_NAME, oldParentName, parentName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DiastylePackage.DBASE_STYLE__STYLE_BRIDGES:
				return ((InternalEList<?>)getStyleBridges()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DiastylePackage.DBASE_STYLE__NAME:
				return getName();
			case DiastylePackage.DBASE_STYLE__COLOR:
				return getColor();
			case DiastylePackage.DBASE_STYLE__STYLE_BRIDGES:
				return getStyleBridges();
			case DiastylePackage.DBASE_STYLE__PARENT:
				if (resolve) return getParent();
				return basicGetParent();
			case DiastylePackage.DBASE_STYLE__PARENT_NAME:
				return getParentName();
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
			case DiastylePackage.DBASE_STYLE__NAME:
				setName((String)newValue);
				return;
			case DiastylePackage.DBASE_STYLE__COLOR:
				setColor((DColor)newValue);
				return;
			case DiastylePackage.DBASE_STYLE__STYLE_BRIDGES:
				getStyleBridges().clear();
				getStyleBridges().addAll((Collection<? extends DStyleBridge>)newValue);
				return;
			case DiastylePackage.DBASE_STYLE__PARENT:
				setParent((DBaseStyle)newValue);
				return;
			case DiastylePackage.DBASE_STYLE__PARENT_NAME:
				setParentName((String)newValue);
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
			case DiastylePackage.DBASE_STYLE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case DiastylePackage.DBASE_STYLE__COLOR:
				setColor(COLOR_EDEFAULT);
				return;
			case DiastylePackage.DBASE_STYLE__STYLE_BRIDGES:
				getStyleBridges().clear();
				return;
			case DiastylePackage.DBASE_STYLE__PARENT:
				setParent((DBaseStyle)null);
				return;
			case DiastylePackage.DBASE_STYLE__PARENT_NAME:
				setParentName(PARENT_NAME_EDEFAULT);
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
			case DiastylePackage.DBASE_STYLE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case DiastylePackage.DBASE_STYLE__COLOR:
				return color != COLOR_EDEFAULT;
			case DiastylePackage.DBASE_STYLE__STYLE_BRIDGES:
				return styleBridges != null && !styleBridges.isEmpty();
			case DiastylePackage.DBASE_STYLE__PARENT:
				return parent != null;
			case DiastylePackage.DBASE_STYLE__PARENT_NAME:
				return PARENT_NAME_EDEFAULT == null ? parentName != null : !PARENT_NAME_EDEFAULT.equals(parentName);
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
		result.append(" (name: ");
		result.append(name);
		result.append(", color: ");
		result.append(color);
		result.append(", parentName: ");
		result.append(parentName);
		result.append(')');
		return result.toString();
	}

} //DBaseStyleImpl
