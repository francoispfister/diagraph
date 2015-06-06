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

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.isoe.diagraph.diastyle.DDirection;
import org.isoe.diagraph.diastyle.DEdgeStyle;
import org.isoe.diagraph.diastyle.DShape;
import org.isoe.diagraph.diastyle.DiastylePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>DEdge Style</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.isoe.diagraph.diastyle.impl.DEdgeStyleImpl#getArrowDirection <em>Arrow Direction</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.impl.DEdgeStyleImpl#getArrowSize <em>Arrow Size</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.impl.DEdgeStyleImpl#getShape <em>Shape</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DEdgeStyleImpl extends DNodeEdgeStyleImpl implements DEdgeStyle {
	/**
	 * The default value of the '{@link #getArrowDirection() <em>Arrow Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArrowDirection()
	 * @generated
	 * @ordered
	 */
	protected static final DDirection ARROW_DIRECTION_EDEFAULT = DDirection.NONE;

	/**
	 * The cached value of the '{@link #getArrowDirection() <em>Arrow Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArrowDirection()
	 * @generated
	 * @ordered
	 */
	protected DDirection arrowDirection = ARROW_DIRECTION_EDEFAULT;

	/**
	 * The default value of the '{@link #getArrowSize() <em>Arrow Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArrowSize()
	 * @generated
	 * @ordered
	 */
	protected static final int ARROW_SIZE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getArrowSize() <em>Arrow Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArrowSize()
	 * @generated
	 * @ordered
	 */
	protected int arrowSize = ARROW_SIZE_EDEFAULT;

	/**
	 * The default value of the '{@link #getShape() <em>Shape</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getShape()
	 * @generated
	 * @ordered
	 */
	protected static final DShape SHAPE_EDEFAULT = DShape.RECTANGLE;

	/**
	 * The cached value of the '{@link #getShape() <em>Shape</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getShape()
	 * @generated
	 * @ordered
	 */
	protected DShape shape = SHAPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DEdgeStyleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DiastylePackage.Literals.DEDGE_STYLE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DDirection getArrowDirection() {
		return arrowDirection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setArrowDirection(DDirection newArrowDirection) {
		DDirection oldArrowDirection = arrowDirection;
		arrowDirection = newArrowDirection == null ? ARROW_DIRECTION_EDEFAULT : newArrowDirection;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiastylePackage.DEDGE_STYLE__ARROW_DIRECTION, oldArrowDirection, arrowDirection));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getArrowSize() {
		return arrowSize;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setArrowSize(int newArrowSize) {
		int oldArrowSize = arrowSize;
		arrowSize = newArrowSize;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiastylePackage.DEDGE_STYLE__ARROW_SIZE, oldArrowSize, arrowSize));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DShape getShape() {
		return shape;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setShape(DShape newShape) {
		DShape oldShape = shape;
		shape = newShape == null ? SHAPE_EDEFAULT : newShape;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiastylePackage.DEDGE_STYLE__SHAPE, oldShape, shape));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DiastylePackage.DEDGE_STYLE__ARROW_DIRECTION:
				return getArrowDirection();
			case DiastylePackage.DEDGE_STYLE__ARROW_SIZE:
				return getArrowSize();
			case DiastylePackage.DEDGE_STYLE__SHAPE:
				return getShape();
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
			case DiastylePackage.DEDGE_STYLE__ARROW_DIRECTION:
				setArrowDirection((DDirection)newValue);
				return;
			case DiastylePackage.DEDGE_STYLE__ARROW_SIZE:
				setArrowSize((Integer)newValue);
				return;
			case DiastylePackage.DEDGE_STYLE__SHAPE:
				setShape((DShape)newValue);
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
			case DiastylePackage.DEDGE_STYLE__ARROW_DIRECTION:
				setArrowDirection(ARROW_DIRECTION_EDEFAULT);
				return;
			case DiastylePackage.DEDGE_STYLE__ARROW_SIZE:
				setArrowSize(ARROW_SIZE_EDEFAULT);
				return;
			case DiastylePackage.DEDGE_STYLE__SHAPE:
				setShape(SHAPE_EDEFAULT);
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
			case DiastylePackage.DEDGE_STYLE__ARROW_DIRECTION:
				return arrowDirection != ARROW_DIRECTION_EDEFAULT;
			case DiastylePackage.DEDGE_STYLE__ARROW_SIZE:
				return arrowSize != ARROW_SIZE_EDEFAULT;
			case DiastylePackage.DEDGE_STYLE__SHAPE:
				return shape != SHAPE_EDEFAULT;
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
		result.append(" (arrowDirection: ");
		result.append(arrowDirection);
		result.append(", arrowSize: ");
		result.append(arrowSize);
		result.append(", shape: ");
		result.append(shape);
		result.append(')');
		return result.toString();
	}

} //DEdgeStyleImpl
