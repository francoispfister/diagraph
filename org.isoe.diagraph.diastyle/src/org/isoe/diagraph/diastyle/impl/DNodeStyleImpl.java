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

import org.isoe.diagraph.diastyle.DLayout;
import org.isoe.diagraph.diastyle.DNodeStyle;
import org.isoe.diagraph.diastyle.DShape;
import org.isoe.diagraph.diastyle.DiastylePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>DNode Style</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.isoe.diagraph.diastyle.impl.DNodeStyleImpl#getSizeX <em>Size X</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.impl.DNodeStyleImpl#getRadius <em>Radius</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.impl.DNodeStyleImpl#getShape <em>Shape</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.impl.DNodeStyleImpl#getSizeY <em>Size Y</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.impl.DNodeStyleImpl#getFigure <em>Figure</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.impl.DNodeStyleImpl#getShapeData <em>Shape Data</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.impl.DNodeStyleImpl#getLayout <em>Layout</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DNodeStyleImpl extends DNodeEdgeStyleImpl implements DNodeStyle {
	/**
	 * The default value of the '{@link #getSizeX() <em>Size X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSizeX()
	 * @generated
	 * @ordered
	 */
	protected static final int SIZE_X_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getSizeX() <em>Size X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSizeX()
	 * @generated
	 * @ordered
	 */
	protected int sizeX = SIZE_X_EDEFAULT;

	/**
	 * The default value of the '{@link #getRadius() <em>Radius</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRadius()
	 * @generated
	 * @ordered
	 */
	protected static final int RADIUS_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getRadius() <em>Radius</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRadius()
	 * @generated
	 * @ordered
	 */
	protected int radius = RADIUS_EDEFAULT;

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
	 * The default value of the '{@link #getSizeY() <em>Size Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSizeY()
	 * @generated
	 * @ordered
	 */
	protected static final int SIZE_Y_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getSizeY() <em>Size Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSizeY()
	 * @generated
	 * @ordered
	 */
	protected int sizeY = SIZE_Y_EDEFAULT;

	/**
	 * The default value of the '{@link #getFigure() <em>Figure</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFigure()
	 * @generated
	 * @ordered
	 */
	protected static final String FIGURE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFigure() <em>Figure</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFigure()
	 * @generated
	 * @ordered
	 */
	protected String figure = FIGURE_EDEFAULT;

	/**
	 * The default value of the '{@link #getShapeData() <em>Shape Data</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getShapeData()
	 * @generated
	 * @ordered
	 */
	protected static final String SHAPE_DATA_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getShapeData() <em>Shape Data</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getShapeData()
	 * @generated
	 * @ordered
	 */
	protected String shapeData = SHAPE_DATA_EDEFAULT;

	/**
	 * The default value of the '{@link #getLayout() <em>Layout</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLayout()
	 * @generated
	 * @ordered
	 */
	protected static final DLayout LAYOUT_EDEFAULT = DLayout.NONE;

	/**
	 * The cached value of the '{@link #getLayout() <em>Layout</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLayout()
	 * @generated
	 * @ordered
	 */
	protected DLayout layout = LAYOUT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DNodeStyleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DiastylePackage.Literals.DNODE_STYLE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getSizeX() {
		return sizeX;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSizeX(int newSizeX) {
		int oldSizeX = sizeX;
		sizeX = newSizeX;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiastylePackage.DNODE_STYLE__SIZE_X, oldSizeX, sizeX));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRadius(int newRadius) {
		int oldRadius = radius;
		radius = newRadius;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiastylePackage.DNODE_STYLE__RADIUS, oldRadius, radius));
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
			eNotify(new ENotificationImpl(this, Notification.SET, DiastylePackage.DNODE_STYLE__SHAPE, oldShape, shape));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getSizeY() {
		return sizeY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSizeY(int newSizeY) {
		int oldSizeY = sizeY;
		sizeY = newSizeY;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiastylePackage.DNODE_STYLE__SIZE_Y, oldSizeY, sizeY));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFigure() {
		return figure;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFigure(String newFigure) {
		String oldFigure = figure;
		figure = newFigure;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiastylePackage.DNODE_STYLE__FIGURE, oldFigure, figure));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getShapeData() {
		return shapeData;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setShapeData(String newShapeData) {
		String oldShapeData = shapeData;
		shapeData = newShapeData;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiastylePackage.DNODE_STYLE__SHAPE_DATA, oldShapeData, shapeData));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DLayout getLayout() {
		return layout;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLayout(DLayout newLayout) {
		DLayout oldLayout = layout;
		layout = newLayout == null ? LAYOUT_EDEFAULT : newLayout;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiastylePackage.DNODE_STYLE__LAYOUT, oldLayout, layout));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DiastylePackage.DNODE_STYLE__SIZE_X:
				return getSizeX();
			case DiastylePackage.DNODE_STYLE__RADIUS:
				return getRadius();
			case DiastylePackage.DNODE_STYLE__SHAPE:
				return getShape();
			case DiastylePackage.DNODE_STYLE__SIZE_Y:
				return getSizeY();
			case DiastylePackage.DNODE_STYLE__FIGURE:
				return getFigure();
			case DiastylePackage.DNODE_STYLE__SHAPE_DATA:
				return getShapeData();
			case DiastylePackage.DNODE_STYLE__LAYOUT:
				return getLayout();
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
			case DiastylePackage.DNODE_STYLE__SIZE_X:
				setSizeX((Integer)newValue);
				return;
			case DiastylePackage.DNODE_STYLE__RADIUS:
				setRadius((Integer)newValue);
				return;
			case DiastylePackage.DNODE_STYLE__SHAPE:
				setShape((DShape)newValue);
				return;
			case DiastylePackage.DNODE_STYLE__SIZE_Y:
				setSizeY((Integer)newValue);
				return;
			case DiastylePackage.DNODE_STYLE__FIGURE:
				setFigure((String)newValue);
				return;
			case DiastylePackage.DNODE_STYLE__SHAPE_DATA:
				setShapeData((String)newValue);
				return;
			case DiastylePackage.DNODE_STYLE__LAYOUT:
				setLayout((DLayout)newValue);
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
			case DiastylePackage.DNODE_STYLE__SIZE_X:
				setSizeX(SIZE_X_EDEFAULT);
				return;
			case DiastylePackage.DNODE_STYLE__RADIUS:
				setRadius(RADIUS_EDEFAULT);
				return;
			case DiastylePackage.DNODE_STYLE__SHAPE:
				setShape(SHAPE_EDEFAULT);
				return;
			case DiastylePackage.DNODE_STYLE__SIZE_Y:
				setSizeY(SIZE_Y_EDEFAULT);
				return;
			case DiastylePackage.DNODE_STYLE__FIGURE:
				setFigure(FIGURE_EDEFAULT);
				return;
			case DiastylePackage.DNODE_STYLE__SHAPE_DATA:
				setShapeData(SHAPE_DATA_EDEFAULT);
				return;
			case DiastylePackage.DNODE_STYLE__LAYOUT:
				setLayout(LAYOUT_EDEFAULT);
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
			case DiastylePackage.DNODE_STYLE__SIZE_X:
				return sizeX != SIZE_X_EDEFAULT;
			case DiastylePackage.DNODE_STYLE__RADIUS:
				return radius != RADIUS_EDEFAULT;
			case DiastylePackage.DNODE_STYLE__SHAPE:
				return shape != SHAPE_EDEFAULT;
			case DiastylePackage.DNODE_STYLE__SIZE_Y:
				return sizeY != SIZE_Y_EDEFAULT;
			case DiastylePackage.DNODE_STYLE__FIGURE:
				return FIGURE_EDEFAULT == null ? figure != null : !FIGURE_EDEFAULT.equals(figure);
			case DiastylePackage.DNODE_STYLE__SHAPE_DATA:
				return SHAPE_DATA_EDEFAULT == null ? shapeData != null : !SHAPE_DATA_EDEFAULT.equals(shapeData);
			case DiastylePackage.DNODE_STYLE__LAYOUT:
				return layout != LAYOUT_EDEFAULT;
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
		result.append(" (sizeX: ");
		result.append(sizeX);
		result.append(", radius: ");
		result.append(radius);
		result.append(", shape: ");
		result.append(shape);
		result.append(", sizeY: ");
		result.append(sizeY);
		result.append(", figure: ");
		result.append(figure);
		result.append(", shapeData: ");
		result.append(shapeData);
		result.append(", layout: ");
		result.append(layout);
		result.append(')');
		return result.toString();
	}

} //DNodeStyleImpl
