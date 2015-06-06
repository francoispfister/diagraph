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

import org.eclipse.emf.ecore.impl.EModelElementImpl;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.isoe.diagraph.diastyle.DAlignment;
import org.isoe.diagraph.diastyle.DBaseStyle;
import org.isoe.diagraph.diastyle.DColor;
import org.isoe.diagraph.diastyle.DFontName;
import org.isoe.diagraph.diastyle.DFontStyle;
import org.isoe.diagraph.diastyle.DLine;
import org.isoe.diagraph.diastyle.DNodeEdgeStyle;
import org.isoe.diagraph.diastyle.DStyleBridge;
import org.isoe.diagraph.diastyle.DiastylePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>DNode Edge Style</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.isoe.diagraph.diastyle.impl.DNodeEdgeStyleImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.impl.DNodeEdgeStyleImpl#getColor <em>Color</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.impl.DNodeEdgeStyleImpl#getStyleBridges <em>Style Bridges</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.impl.DNodeEdgeStyleImpl#getParent <em>Parent</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.impl.DNodeEdgeStyleImpl#getParentName <em>Parent Name</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.impl.DNodeEdgeStyleImpl#getLine <em>Line</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.impl.DNodeEdgeStyleImpl#getLineWidth <em>Line Width</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.impl.DNodeEdgeStyleImpl#getFontName <em>Font Name</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.impl.DNodeEdgeStyleImpl#getFontStyle <em>Font Style</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.impl.DNodeEdgeStyleImpl#getFontSize <em>Font Size</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.impl.DNodeEdgeStyleImpl#getFontColor <em>Font Color</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.impl.DNodeEdgeStyleImpl#getTextAlignment <em>Text Alignment</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.impl.DNodeEdgeStyleImpl#getIcon <em>Icon</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class DNodeEdgeStyleImpl extends EModelElementImpl implements DNodeEdgeStyle {
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
	 * The default value of the '{@link #getLine() <em>Line</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLine()
	 * @generated
	 * @ordered
	 */
	protected static final DLine LINE_EDEFAULT = DLine.SOLID;

	/**
	 * The cached value of the '{@link #getLine() <em>Line</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLine()
	 * @generated
	 * @ordered
	 */
	protected DLine line = LINE_EDEFAULT;

	/**
	 * The default value of the '{@link #getLineWidth() <em>Line Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLineWidth()
	 * @generated
	 * @ordered
	 */
	protected static final int LINE_WIDTH_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getLineWidth() <em>Line Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLineWidth()
	 * @generated
	 * @ordered
	 */
	protected int lineWidth = LINE_WIDTH_EDEFAULT;

	/**
	 * The default value of the '{@link #getFontName() <em>Font Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFontName()
	 * @generated
	 * @ordered
	 */
	protected static final DFontName FONT_NAME_EDEFAULT = DFontName.ARIAL;

	/**
	 * The cached value of the '{@link #getFontName() <em>Font Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFontName()
	 * @generated
	 * @ordered
	 */
	protected DFontName fontName = FONT_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getFontStyle() <em>Font Style</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFontStyle()
	 * @generated
	 * @ordered
	 */
	protected static final DFontStyle FONT_STYLE_EDEFAULT = DFontStyle.NORMAL;

	/**
	 * The cached value of the '{@link #getFontStyle() <em>Font Style</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFontStyle()
	 * @generated
	 * @ordered
	 */
	protected DFontStyle fontStyle = FONT_STYLE_EDEFAULT;

	/**
	 * The default value of the '{@link #getFontSize() <em>Font Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFontSize()
	 * @generated
	 * @ordered
	 */
	protected static final int FONT_SIZE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getFontSize() <em>Font Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFontSize()
	 * @generated
	 * @ordered
	 */
	protected int fontSize = FONT_SIZE_EDEFAULT;

	/**
	 * The default value of the '{@link #getFontColor() <em>Font Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFontColor()
	 * @generated
	 * @ordered
	 */
	protected static final DColor FONT_COLOR_EDEFAULT = DColor.WHITE;

	/**
	 * The cached value of the '{@link #getFontColor() <em>Font Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFontColor()
	 * @generated
	 * @ordered
	 */
	protected DColor fontColor = FONT_COLOR_EDEFAULT;

	/**
	 * The default value of the '{@link #getTextAlignment() <em>Text Alignment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTextAlignment()
	 * @generated
	 * @ordered
	 */
	protected static final DAlignment TEXT_ALIGNMENT_EDEFAULT = DAlignment.BEGINNING;

	/**
	 * The cached value of the '{@link #getTextAlignment() <em>Text Alignment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTextAlignment()
	 * @generated
	 * @ordered
	 */
	protected DAlignment textAlignment = TEXT_ALIGNMENT_EDEFAULT;

	/**
	 * The default value of the '{@link #getIcon() <em>Icon</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIcon()
	 * @generated
	 * @ordered
	 */
	protected static final String ICON_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIcon() <em>Icon</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIcon()
	 * @generated
	 * @ordered
	 */
	protected String icon = ICON_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DNodeEdgeStyleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DiastylePackage.Literals.DNODE_EDGE_STYLE;
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
			eNotify(new ENotificationImpl(this, Notification.SET, DiastylePackage.DNODE_EDGE_STYLE__NAME, oldName, name));
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
			eNotify(new ENotificationImpl(this, Notification.SET, DiastylePackage.DNODE_EDGE_STYLE__COLOR, oldColor, color));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DStyleBridge> getStyleBridges() {
		if (styleBridges == null) {
			styleBridges = new EObjectContainmentEList<DStyleBridge>(DStyleBridge.class, this, DiastylePackage.DNODE_EDGE_STYLE__STYLE_BRIDGES);
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DiastylePackage.DNODE_EDGE_STYLE__PARENT, oldParent, parent));
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
			eNotify(new ENotificationImpl(this, Notification.SET, DiastylePackage.DNODE_EDGE_STYLE__PARENT, oldParent, parent));
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
			eNotify(new ENotificationImpl(this, Notification.SET, DiastylePackage.DNODE_EDGE_STYLE__PARENT_NAME, oldParentName, parentName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DLine getLine() {
		return line;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLine(DLine newLine) {
		DLine oldLine = line;
		line = newLine == null ? LINE_EDEFAULT : newLine;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiastylePackage.DNODE_EDGE_STYLE__LINE, oldLine, line));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getLineWidth() {
		return lineWidth;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLineWidth(int newLineWidth) {
		int oldLineWidth = lineWidth;
		lineWidth = newLineWidth;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiastylePackage.DNODE_EDGE_STYLE__LINE_WIDTH, oldLineWidth, lineWidth));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DFontName getFontName() {
		return fontName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFontName(DFontName newFontName) {
		DFontName oldFontName = fontName;
		fontName = newFontName == null ? FONT_NAME_EDEFAULT : newFontName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiastylePackage.DNODE_EDGE_STYLE__FONT_NAME, oldFontName, fontName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DFontStyle getFontStyle() {
		return fontStyle;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFontStyle(DFontStyle newFontStyle) {
		DFontStyle oldFontStyle = fontStyle;
		fontStyle = newFontStyle == null ? FONT_STYLE_EDEFAULT : newFontStyle;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiastylePackage.DNODE_EDGE_STYLE__FONT_STYLE, oldFontStyle, fontStyle));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getFontSize() {
		return fontSize;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFontSize(int newFontSize) {
		int oldFontSize = fontSize;
		fontSize = newFontSize;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiastylePackage.DNODE_EDGE_STYLE__FONT_SIZE, oldFontSize, fontSize));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DColor getFontColor() {
		return fontColor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFontColor(DColor newFontColor) {
		DColor oldFontColor = fontColor;
		fontColor = newFontColor == null ? FONT_COLOR_EDEFAULT : newFontColor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiastylePackage.DNODE_EDGE_STYLE__FONT_COLOR, oldFontColor, fontColor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DAlignment getTextAlignment() {
		return textAlignment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTextAlignment(DAlignment newTextAlignment) {
		DAlignment oldTextAlignment = textAlignment;
		textAlignment = newTextAlignment == null ? TEXT_ALIGNMENT_EDEFAULT : newTextAlignment;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiastylePackage.DNODE_EDGE_STYLE__TEXT_ALIGNMENT, oldTextAlignment, textAlignment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIcon(String newIcon) {
		String oldIcon = icon;
		icon = newIcon;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiastylePackage.DNODE_EDGE_STYLE__ICON, oldIcon, icon));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DiastylePackage.DNODE_EDGE_STYLE__STYLE_BRIDGES:
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
			case DiastylePackage.DNODE_EDGE_STYLE__NAME:
				return getName();
			case DiastylePackage.DNODE_EDGE_STYLE__COLOR:
				return getColor();
			case DiastylePackage.DNODE_EDGE_STYLE__STYLE_BRIDGES:
				return getStyleBridges();
			case DiastylePackage.DNODE_EDGE_STYLE__PARENT:
				if (resolve) return getParent();
				return basicGetParent();
			case DiastylePackage.DNODE_EDGE_STYLE__PARENT_NAME:
				return getParentName();
			case DiastylePackage.DNODE_EDGE_STYLE__LINE:
				return getLine();
			case DiastylePackage.DNODE_EDGE_STYLE__LINE_WIDTH:
				return getLineWidth();
			case DiastylePackage.DNODE_EDGE_STYLE__FONT_NAME:
				return getFontName();
			case DiastylePackage.DNODE_EDGE_STYLE__FONT_STYLE:
				return getFontStyle();
			case DiastylePackage.DNODE_EDGE_STYLE__FONT_SIZE:
				return getFontSize();
			case DiastylePackage.DNODE_EDGE_STYLE__FONT_COLOR:
				return getFontColor();
			case DiastylePackage.DNODE_EDGE_STYLE__TEXT_ALIGNMENT:
				return getTextAlignment();
			case DiastylePackage.DNODE_EDGE_STYLE__ICON:
				return getIcon();
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
			case DiastylePackage.DNODE_EDGE_STYLE__NAME:
				setName((String)newValue);
				return;
			case DiastylePackage.DNODE_EDGE_STYLE__COLOR:
				setColor((DColor)newValue);
				return;
			case DiastylePackage.DNODE_EDGE_STYLE__STYLE_BRIDGES:
				getStyleBridges().clear();
				getStyleBridges().addAll((Collection<? extends DStyleBridge>)newValue);
				return;
			case DiastylePackage.DNODE_EDGE_STYLE__PARENT:
				setParent((DBaseStyle)newValue);
				return;
			case DiastylePackage.DNODE_EDGE_STYLE__PARENT_NAME:
				setParentName((String)newValue);
				return;
			case DiastylePackage.DNODE_EDGE_STYLE__LINE:
				setLine((DLine)newValue);
				return;
			case DiastylePackage.DNODE_EDGE_STYLE__LINE_WIDTH:
				setLineWidth((Integer)newValue);
				return;
			case DiastylePackage.DNODE_EDGE_STYLE__FONT_NAME:
				setFontName((DFontName)newValue);
				return;
			case DiastylePackage.DNODE_EDGE_STYLE__FONT_STYLE:
				setFontStyle((DFontStyle)newValue);
				return;
			case DiastylePackage.DNODE_EDGE_STYLE__FONT_SIZE:
				setFontSize((Integer)newValue);
				return;
			case DiastylePackage.DNODE_EDGE_STYLE__FONT_COLOR:
				setFontColor((DColor)newValue);
				return;
			case DiastylePackage.DNODE_EDGE_STYLE__TEXT_ALIGNMENT:
				setTextAlignment((DAlignment)newValue);
				return;
			case DiastylePackage.DNODE_EDGE_STYLE__ICON:
				setIcon((String)newValue);
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
			case DiastylePackage.DNODE_EDGE_STYLE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case DiastylePackage.DNODE_EDGE_STYLE__COLOR:
				setColor(COLOR_EDEFAULT);
				return;
			case DiastylePackage.DNODE_EDGE_STYLE__STYLE_BRIDGES:
				getStyleBridges().clear();
				return;
			case DiastylePackage.DNODE_EDGE_STYLE__PARENT:
				setParent((DBaseStyle)null);
				return;
			case DiastylePackage.DNODE_EDGE_STYLE__PARENT_NAME:
				setParentName(PARENT_NAME_EDEFAULT);
				return;
			case DiastylePackage.DNODE_EDGE_STYLE__LINE:
				setLine(LINE_EDEFAULT);
				return;
			case DiastylePackage.DNODE_EDGE_STYLE__LINE_WIDTH:
				setLineWidth(LINE_WIDTH_EDEFAULT);
				return;
			case DiastylePackage.DNODE_EDGE_STYLE__FONT_NAME:
				setFontName(FONT_NAME_EDEFAULT);
				return;
			case DiastylePackage.DNODE_EDGE_STYLE__FONT_STYLE:
				setFontStyle(FONT_STYLE_EDEFAULT);
				return;
			case DiastylePackage.DNODE_EDGE_STYLE__FONT_SIZE:
				setFontSize(FONT_SIZE_EDEFAULT);
				return;
			case DiastylePackage.DNODE_EDGE_STYLE__FONT_COLOR:
				setFontColor(FONT_COLOR_EDEFAULT);
				return;
			case DiastylePackage.DNODE_EDGE_STYLE__TEXT_ALIGNMENT:
				setTextAlignment(TEXT_ALIGNMENT_EDEFAULT);
				return;
			case DiastylePackage.DNODE_EDGE_STYLE__ICON:
				setIcon(ICON_EDEFAULT);
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
			case DiastylePackage.DNODE_EDGE_STYLE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case DiastylePackage.DNODE_EDGE_STYLE__COLOR:
				return color != COLOR_EDEFAULT;
			case DiastylePackage.DNODE_EDGE_STYLE__STYLE_BRIDGES:
				return styleBridges != null && !styleBridges.isEmpty();
			case DiastylePackage.DNODE_EDGE_STYLE__PARENT:
				return parent != null;
			case DiastylePackage.DNODE_EDGE_STYLE__PARENT_NAME:
				return PARENT_NAME_EDEFAULT == null ? parentName != null : !PARENT_NAME_EDEFAULT.equals(parentName);
			case DiastylePackage.DNODE_EDGE_STYLE__LINE:
				return line != LINE_EDEFAULT;
			case DiastylePackage.DNODE_EDGE_STYLE__LINE_WIDTH:
				return lineWidth != LINE_WIDTH_EDEFAULT;
			case DiastylePackage.DNODE_EDGE_STYLE__FONT_NAME:
				return fontName != FONT_NAME_EDEFAULT;
			case DiastylePackage.DNODE_EDGE_STYLE__FONT_STYLE:
				return fontStyle != FONT_STYLE_EDEFAULT;
			case DiastylePackage.DNODE_EDGE_STYLE__FONT_SIZE:
				return fontSize != FONT_SIZE_EDEFAULT;
			case DiastylePackage.DNODE_EDGE_STYLE__FONT_COLOR:
				return fontColor != FONT_COLOR_EDEFAULT;
			case DiastylePackage.DNODE_EDGE_STYLE__TEXT_ALIGNMENT:
				return textAlignment != TEXT_ALIGNMENT_EDEFAULT;
			case DiastylePackage.DNODE_EDGE_STYLE__ICON:
				return ICON_EDEFAULT == null ? icon != null : !ICON_EDEFAULT.equals(icon);
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
		if (baseClass == DBaseStyle.class) {
			switch (derivedFeatureID) {
				case DiastylePackage.DNODE_EDGE_STYLE__NAME: return DiastylePackage.DBASE_STYLE__NAME;
				case DiastylePackage.DNODE_EDGE_STYLE__COLOR: return DiastylePackage.DBASE_STYLE__COLOR;
				case DiastylePackage.DNODE_EDGE_STYLE__STYLE_BRIDGES: return DiastylePackage.DBASE_STYLE__STYLE_BRIDGES;
				case DiastylePackage.DNODE_EDGE_STYLE__PARENT: return DiastylePackage.DBASE_STYLE__PARENT;
				case DiastylePackage.DNODE_EDGE_STYLE__PARENT_NAME: return DiastylePackage.DBASE_STYLE__PARENT_NAME;
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
		if (baseClass == DBaseStyle.class) {
			switch (baseFeatureID) {
				case DiastylePackage.DBASE_STYLE__NAME: return DiastylePackage.DNODE_EDGE_STYLE__NAME;
				case DiastylePackage.DBASE_STYLE__COLOR: return DiastylePackage.DNODE_EDGE_STYLE__COLOR;
				case DiastylePackage.DBASE_STYLE__STYLE_BRIDGES: return DiastylePackage.DNODE_EDGE_STYLE__STYLE_BRIDGES;
				case DiastylePackage.DBASE_STYLE__PARENT: return DiastylePackage.DNODE_EDGE_STYLE__PARENT;
				case DiastylePackage.DBASE_STYLE__PARENT_NAME: return DiastylePackage.DNODE_EDGE_STYLE__PARENT_NAME;
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
		result.append(" (name: ");
		result.append(name);
		result.append(", color: ");
		result.append(color);
		result.append(", parentName: ");
		result.append(parentName);
		result.append(", line: ");
		result.append(line);
		result.append(", lineWidth: ");
		result.append(lineWidth);
		result.append(", fontName: ");
		result.append(fontName);
		result.append(", fontStyle: ");
		result.append(fontStyle);
		result.append(", fontSize: ");
		result.append(fontSize);
		result.append(", fontColor: ");
		result.append(fontColor);
		result.append(", textAlignment: ");
		result.append(textAlignment);
		result.append(", icon: ");
		result.append(icon);
		result.append(')');
		return result.toString();
	}

} //DNodeEdgeStyleImpl
