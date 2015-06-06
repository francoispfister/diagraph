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

import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.diagraph.DGraphElement;

import org.isoe.diagraph.diastyle.DBaseStyle;
import org.isoe.diagraph.diastyle.DStyle;
import org.isoe.diagraph.diastyle.DiastylePackage;

import org.isoe.diagraph.diastyle.helpers.IStyleHandler;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>DStyle</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.isoe.diagraph.diastyle.impl.DStyleImpl#getStyles <em>Styles</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.impl.DStyleImpl#getStyleHandler <em>Style Handler</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.impl.DStyleImpl#getDGraph <em>DGraph</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DStyleImpl extends EModelElementImpl implements DStyle {
	/**
	 * The cached value of the '{@link #getStyles() <em>Styles</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStyles()
	 * @generated
	 * @ordered
	 */
	protected EList<DBaseStyle> styles;

	/**
	 * The default value of the '{@link #getStyleHandler() <em>Style Handler</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStyleHandler()
	 * @generated
	 * @ordered
	 */
	protected static final IStyleHandler STYLE_HANDLER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getStyleHandler() <em>Style Handler</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStyleHandler()
	 * @generated
	 * @ordered
	 */
	protected IStyleHandler styleHandler = STYLE_HANDLER_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDGraph() <em>DGraph</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDGraph()
	 * @generated
	 * @ordered
	 */
	protected DGraph dGraph;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DStyleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DiastylePackage.Literals.DSTYLE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DBaseStyle> getStyles() {
		if (styles == null) {
			styles = new EObjectContainmentEList<DBaseStyle>(DBaseStyle.class, this, DiastylePackage.DSTYLE__STYLES);
		}
		return styles;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IStyleHandler getStyleHandler() {
		return styleHandler;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setStyleHandler(IStyleHandler newStyleHandler) {
		//in DStyleImpl
		IStyleHandler oldStyleHandler = styleHandler;
		styleHandler = newStyleHandler;
		styleHandler.setStyle(this);//FP120923
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiastylePackage.DSTYLE__STYLE_HANDLER, oldStyleHandler, styleHandler));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DGraph getDGraph() {
		if (dGraph != null && dGraph.eIsProxy()) {
			InternalEObject oldDGraph = (InternalEObject)dGraph;
			dGraph = (DGraph)eResolveProxy(oldDGraph);
			if (dGraph != oldDGraph) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DiastylePackage.DSTYLE__DGRAPH, oldDGraph, dGraph));
			}
		}
		return dGraph;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DGraph basicGetDGraph() {
		return dGraph;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDGraph(DGraph newDGraph) {
		DGraph oldDGraph = dGraph;
		dGraph = newDGraph;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiastylePackage.DSTYLE__DGRAPH, oldDGraph, dGraph));
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getBackgroundColor(DGraphElement element){
		if (element!=null && getStyleHandler()!=null){
			String result = getStyleHandler().getBackgroundColor(element.getName(), this);
		    return result; //FP120724xxx //FP121210
		}else
			return "error";
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getFigure(DGraphElement element) {
		if (element!=null && getStyleHandler()!=null){
			String result = getStyleHandler().getFigure(element,this);
		    return result; //FP121210
		}else
			return "error";
	}



	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getIcon(DGraphElement element) {
		if (element!=null && getStyleHandler()!=null){
			String result = getStyleHandler().getIcon(element,this);
		    return result;//FP121210
		}else
			return "error";
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DiastylePackage.DSTYLE__STYLES:
				return ((InternalEList<?>)getStyles()).basicRemove(otherEnd, msgs);
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
			case DiastylePackage.DSTYLE__STYLES:
				return getStyles();
			case DiastylePackage.DSTYLE__STYLE_HANDLER:
				return getStyleHandler();
			case DiastylePackage.DSTYLE__DGRAPH:
				if (resolve) return getDGraph();
				return basicGetDGraph();
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
			case DiastylePackage.DSTYLE__STYLES:
				getStyles().clear();
				getStyles().addAll((Collection<? extends DBaseStyle>)newValue);
				return;
			case DiastylePackage.DSTYLE__STYLE_HANDLER:
				setStyleHandler((IStyleHandler)newValue);
				return;
			case DiastylePackage.DSTYLE__DGRAPH:
				setDGraph((DGraph)newValue);
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
			case DiastylePackage.DSTYLE__STYLES:
				getStyles().clear();
				return;
			case DiastylePackage.DSTYLE__STYLE_HANDLER:
				setStyleHandler(STYLE_HANDLER_EDEFAULT);
				return;
			case DiastylePackage.DSTYLE__DGRAPH:
				setDGraph((DGraph)null);
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
			case DiastylePackage.DSTYLE__STYLES:
				return styles != null && !styles.isEmpty();
			case DiastylePackage.DSTYLE__STYLE_HANDLER:
				return STYLE_HANDLER_EDEFAULT == null ? styleHandler != null : !STYLE_HANDLER_EDEFAULT.equals(styleHandler);
			case DiastylePackage.DSTYLE__DGRAPH:
				return dGraph != null;
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
		result.append(" (styleHandler: ");
		result.append(styleHandler);
		result.append(')');
		return result.toString();
	}

} //DStyleImpl
