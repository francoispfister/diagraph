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

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.isoe.diagraph.dgi.DConcreteSyntax;
import org.isoe.diagraph.dgi.DEdge;
import org.isoe.diagraph.dgi.DGenericElement;
import org.isoe.diagraph.dgi.DNode;
import org.isoe.diagraph.dgi.DPointOfView;
import org.isoe.diagraph.dgi.DgiPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>DConcrete Syntax</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.isoe.diagraph.dgi.impl.DConcreteSyntaxImpl#getDNodes <em>DNodes</em>}</li>
 *   <li>{@link org.isoe.diagraph.dgi.impl.DConcreteSyntaxImpl#getDElements <em>DElements</em>}</li>
 *   <li>{@link org.isoe.diagraph.dgi.impl.DConcreteSyntaxImpl#getDEdges <em>DEdges</em>}</li>
 *   <li>{@link org.isoe.diagraph.dgi.impl.DConcreteSyntaxImpl#getRootPointOfView <em>Root Point Of View</em>}</li>
 *   <li>{@link org.isoe.diagraph.dgi.impl.DConcreteSyntaxImpl#getPointsOfView <em>Points Of View</em>}</li>
 *   <li>{@link org.isoe.diagraph.dgi.impl.DConcreteSyntaxImpl#getCurrentPointOfView <em>Current Point Of View</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DConcreteSyntaxImpl extends EObjectImpl implements DConcreteSyntax {
	/**
	 * The cached value of the '{@link #getDNodes() <em>DNodes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDNodes()
	 * @generated
	 * @ordered
	 */
	protected EList<DNode> dNodes;

	/**
	 * The cached value of the '{@link #getDElements() <em>DElements</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDElements()
	 * @generated
	 * @ordered
	 */
	protected EList<DGenericElement> dElements;

	/**
	 * The cached value of the '{@link #getDEdges() <em>DEdges</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDEdges()
	 * @generated
	 * @ordered
	 */
	protected EList<DEdge> dEdges;

	/**
	 * The cached value of the '{@link #getRootPointOfView() <em>Root Point Of View</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRootPointOfView()
	 * @generated
	 * @ordered
	 */
	protected DPointOfView rootPointOfView;

	/**
	 * The cached value of the '{@link #getPointsOfView() <em>Points Of View</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPointsOfView()
	 * @generated
	 * @ordered
	 */
	protected EList<DPointOfView> pointsOfView;

	/**
	 * The cached value of the '{@link #getCurrentPointOfView() <em>Current Point Of View</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCurrentPointOfView()
	 * @generated
	 * @ordered
	 */
	protected DPointOfView currentPointOfView;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DConcreteSyntaxImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DgiPackage.Literals.DCONCRETE_SYNTAX;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DNode> getDNodes() {
		if (dNodes == null) {
			dNodes = new EObjectContainmentEList<DNode>(DNode.class, this, DgiPackage.DCONCRETE_SYNTAX__DNODES);
		}
		return dNodes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DGenericElement> getDElements() {
		if (dElements == null) {
			dElements = new EObjectContainmentEList<DGenericElement>(DGenericElement.class, this, DgiPackage.DCONCRETE_SYNTAX__DELEMENTS);
		}
		return dElements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DEdge> getDEdges() {
		if (dEdges == null) {
			dEdges = new EObjectContainmentEList<DEdge>(DEdge.class, this, DgiPackage.DCONCRETE_SYNTAX__DEDGES);
		}
		return dEdges;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DPointOfView getRootPointOfView() {
		return rootPointOfView;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRootPointOfView(DPointOfView newRootPointOfView, NotificationChain msgs) {
		DPointOfView oldRootPointOfView = rootPointOfView;
		rootPointOfView = newRootPointOfView;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DgiPackage.DCONCRETE_SYNTAX__ROOT_POINT_OF_VIEW, oldRootPointOfView, newRootPointOfView);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRootPointOfView(DPointOfView newRootPointOfView) {
		if (newRootPointOfView != rootPointOfView) {
			NotificationChain msgs = null;
			if (rootPointOfView != null)
				msgs = ((InternalEObject)rootPointOfView).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DgiPackage.DCONCRETE_SYNTAX__ROOT_POINT_OF_VIEW, null, msgs);
			if (newRootPointOfView != null)
				msgs = ((InternalEObject)newRootPointOfView).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DgiPackage.DCONCRETE_SYNTAX__ROOT_POINT_OF_VIEW, null, msgs);
			msgs = basicSetRootPointOfView(newRootPointOfView, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DgiPackage.DCONCRETE_SYNTAX__ROOT_POINT_OF_VIEW, newRootPointOfView, newRootPointOfView));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DPointOfView> getPointsOfView() {
		if (pointsOfView == null) {
			pointsOfView = new EObjectResolvingEList<DPointOfView>(DPointOfView.class, this, DgiPackage.DCONCRETE_SYNTAX__POINTS_OF_VIEW);
		}
		return pointsOfView;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DPointOfView getCurrentPointOfView() {
		if (currentPointOfView != null && currentPointOfView.eIsProxy()) {
			InternalEObject oldCurrentPointOfView = (InternalEObject)currentPointOfView;
			currentPointOfView = (DPointOfView)eResolveProxy(oldCurrentPointOfView);
			if (currentPointOfView != oldCurrentPointOfView) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DgiPackage.DCONCRETE_SYNTAX__CURRENT_POINT_OF_VIEW, oldCurrentPointOfView, currentPointOfView));
			}
		}
		return currentPointOfView;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DPointOfView basicGetCurrentPointOfView() {
		return currentPointOfView;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCurrentPointOfView(DPointOfView newCurrentPointOfView) {
		DPointOfView oldCurrentPointOfView = currentPointOfView;
		currentPointOfView = newCurrentPointOfView;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DgiPackage.DCONCRETE_SYNTAX__CURRENT_POINT_OF_VIEW, oldCurrentPointOfView, currentPointOfView));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DgiPackage.DCONCRETE_SYNTAX__DNODES:
				return ((InternalEList<?>)getDNodes()).basicRemove(otherEnd, msgs);
			case DgiPackage.DCONCRETE_SYNTAX__DELEMENTS:
				return ((InternalEList<?>)getDElements()).basicRemove(otherEnd, msgs);
			case DgiPackage.DCONCRETE_SYNTAX__DEDGES:
				return ((InternalEList<?>)getDEdges()).basicRemove(otherEnd, msgs);
			case DgiPackage.DCONCRETE_SYNTAX__ROOT_POINT_OF_VIEW:
				return basicSetRootPointOfView(null, msgs);
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
			case DgiPackage.DCONCRETE_SYNTAX__DNODES:
				return getDNodes();
			case DgiPackage.DCONCRETE_SYNTAX__DELEMENTS:
				return getDElements();
			case DgiPackage.DCONCRETE_SYNTAX__DEDGES:
				return getDEdges();
			case DgiPackage.DCONCRETE_SYNTAX__ROOT_POINT_OF_VIEW:
				return getRootPointOfView();
			case DgiPackage.DCONCRETE_SYNTAX__POINTS_OF_VIEW:
				return getPointsOfView();
			case DgiPackage.DCONCRETE_SYNTAX__CURRENT_POINT_OF_VIEW:
				if (resolve) return getCurrentPointOfView();
				return basicGetCurrentPointOfView();
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
			case DgiPackage.DCONCRETE_SYNTAX__DNODES:
				getDNodes().clear();
				getDNodes().addAll((Collection<? extends DNode>)newValue);
				return;
			case DgiPackage.DCONCRETE_SYNTAX__DELEMENTS:
				getDElements().clear();
				getDElements().addAll((Collection<? extends DGenericElement>)newValue);
				return;
			case DgiPackage.DCONCRETE_SYNTAX__DEDGES:
				getDEdges().clear();
				getDEdges().addAll((Collection<? extends DEdge>)newValue);
				return;
			case DgiPackage.DCONCRETE_SYNTAX__ROOT_POINT_OF_VIEW:
				setRootPointOfView((DPointOfView)newValue);
				return;
			case DgiPackage.DCONCRETE_SYNTAX__POINTS_OF_VIEW:
				getPointsOfView().clear();
				getPointsOfView().addAll((Collection<? extends DPointOfView>)newValue);
				return;
			case DgiPackage.DCONCRETE_SYNTAX__CURRENT_POINT_OF_VIEW:
				setCurrentPointOfView((DPointOfView)newValue);
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
			case DgiPackage.DCONCRETE_SYNTAX__DNODES:
				getDNodes().clear();
				return;
			case DgiPackage.DCONCRETE_SYNTAX__DELEMENTS:
				getDElements().clear();
				return;
			case DgiPackage.DCONCRETE_SYNTAX__DEDGES:
				getDEdges().clear();
				return;
			case DgiPackage.DCONCRETE_SYNTAX__ROOT_POINT_OF_VIEW:
				setRootPointOfView((DPointOfView)null);
				return;
			case DgiPackage.DCONCRETE_SYNTAX__POINTS_OF_VIEW:
				getPointsOfView().clear();
				return;
			case DgiPackage.DCONCRETE_SYNTAX__CURRENT_POINT_OF_VIEW:
				setCurrentPointOfView((DPointOfView)null);
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
			case DgiPackage.DCONCRETE_SYNTAX__DNODES:
				return dNodes != null && !dNodes.isEmpty();
			case DgiPackage.DCONCRETE_SYNTAX__DELEMENTS:
				return dElements != null && !dElements.isEmpty();
			case DgiPackage.DCONCRETE_SYNTAX__DEDGES:
				return dEdges != null && !dEdges.isEmpty();
			case DgiPackage.DCONCRETE_SYNTAX__ROOT_POINT_OF_VIEW:
				return rootPointOfView != null;
			case DgiPackage.DCONCRETE_SYNTAX__POINTS_OF_VIEW:
				return pointsOfView != null && !pointsOfView.isEmpty();
			case DgiPackage.DCONCRETE_SYNTAX__CURRENT_POINT_OF_VIEW:
				return currentPointOfView != null;
		}
		return super.eIsSet(featureID);
	}

} //DConcreteSyntaxImpl
