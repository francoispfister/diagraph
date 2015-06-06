/**
 */
package org.isoe.diagraph.diagraph.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.diagraph.DGraphElement;
import org.isoe.diagraph.diagraph.DPointOfView;
import org.isoe.diagraph.diagraph.DiagraphPackage;

import org.isoe.diagraph.diagraph.helpers.IGraphHandler;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>DGraph</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DGraphImpl#getElements <em>Elements</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DGraphImpl#getPointOfView <em>Point Of View</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DGraphImpl#getViewName <em>View Name</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DGraphImpl#getFacade1 <em>Facade1</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DGraphImpl#getFacade2 <em>Facade2</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DGraphImpl extends MinimalEObjectImpl.Container implements DGraph {
	/**
	 * The cached value of the '{@link #getElements() <em>Elements</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElements()
	 * @generated
	 * @ordered
	 */
	protected EList<DGraphElement> elements;

	/**
	 * The cached value of the '{@link #getPointOfView() <em>Point Of View</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPointOfView()
	 * @generated
	 * @ordered
	 */
	protected DPointOfView pointOfView;

	/**
	 * The default value of the '{@link #getViewName() <em>View Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getViewName()
	 * @generated
	 * @ordered
	 */
	protected static final String VIEW_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getViewName() <em>View Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getViewName()
	 * @generated
	 * @ordered
	 */
	protected String viewName = VIEW_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getFacade1() <em>Facade1</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFacade1()
	 * @generated
	 * @ordered
	 */
	protected static final IGraphHandler FACADE1_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFacade1() <em>Facade1</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFacade1()
	 * @generated
	 * @ordered
	 */
	protected IGraphHandler facade1 = FACADE1_EDEFAULT;

	/**
	 * The default value of the '{@link #getFacade2() <em>Facade2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFacade2()
	 * @generated
	 * @ordered
	 */
	protected static final IGraphHandler FACADE2_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFacade2() <em>Facade2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFacade2()
	 * @generated
	 * @ordered
	 */
	protected IGraphHandler facade2 = FACADE2_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DGraphImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DiagraphPackage.Literals.DGRAPH;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DGraphElement> getElements() {
		if (elements == null) {
			elements = new EObjectContainmentWithInverseEList<DGraphElement>(DGraphElement.class, this, DiagraphPackage.DGRAPH__ELEMENTS, DiagraphPackage.DGRAPH_ELEMENT__GRAPH);
		}
		return elements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DPointOfView getPointOfView() {
		if (pointOfView != null && pointOfView.eIsProxy()) {
			InternalEObject oldPointOfView = (InternalEObject)pointOfView;
			pointOfView = (DPointOfView)eResolveProxy(oldPointOfView);
			if (pointOfView != oldPointOfView) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DiagraphPackage.DGRAPH__POINT_OF_VIEW, oldPointOfView, pointOfView));
			}
		}
		return pointOfView;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DPointOfView basicGetPointOfView() {
		return pointOfView;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPointOfView(DPointOfView newPointOfView) {
		DPointOfView oldPointOfView = pointOfView;
		pointOfView = newPointOfView;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagraphPackage.DGRAPH__POINT_OF_VIEW, oldPointOfView, pointOfView));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getViewName() {
		return viewName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setViewName(String newViewName) {
		String oldViewName = viewName;
		viewName = newViewName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagraphPackage.DGRAPH__VIEW_NAME, oldViewName, viewName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IGraphHandler getFacade1() {
		return facade1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFacade1(IGraphHandler newFacade1) {
		IGraphHandler oldFacade1 = facade1;
		facade1 = newFacade1;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagraphPackage.DGRAPH__FACADE1, oldFacade1, facade1));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IGraphHandler getFacade2() {
		return facade2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFacade2(IGraphHandler newFacade2) {
		IGraphHandler oldFacade2 = facade2;
		facade2 = newFacade2;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagraphPackage.DGRAPH__FACADE2, oldFacade2, facade2));
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
			case DiagraphPackage.DGRAPH__ELEMENTS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getElements()).basicAdd(otherEnd, msgs);
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
			case DiagraphPackage.DGRAPH__ELEMENTS:
				return ((InternalEList<?>)getElements()).basicRemove(otherEnd, msgs);
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
			case DiagraphPackage.DGRAPH__ELEMENTS:
				return getElements();
			case DiagraphPackage.DGRAPH__POINT_OF_VIEW:
				if (resolve) return getPointOfView();
				return basicGetPointOfView();
			case DiagraphPackage.DGRAPH__VIEW_NAME:
				return getViewName();
			case DiagraphPackage.DGRAPH__FACADE1:
				return getFacade1();
			case DiagraphPackage.DGRAPH__FACADE2:
				return getFacade2();
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
			case DiagraphPackage.DGRAPH__ELEMENTS:
				getElements().clear();
				getElements().addAll((Collection<? extends DGraphElement>)newValue);
				return;
			case DiagraphPackage.DGRAPH__POINT_OF_VIEW:
				setPointOfView((DPointOfView)newValue);
				return;
			case DiagraphPackage.DGRAPH__VIEW_NAME:
				setViewName((String)newValue);
				return;
			case DiagraphPackage.DGRAPH__FACADE1:
				setFacade1((IGraphHandler)newValue);
				return;
			case DiagraphPackage.DGRAPH__FACADE2:
				setFacade2((IGraphHandler)newValue);
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
			case DiagraphPackage.DGRAPH__ELEMENTS:
				getElements().clear();
				return;
			case DiagraphPackage.DGRAPH__POINT_OF_VIEW:
				setPointOfView((DPointOfView)null);
				return;
			case DiagraphPackage.DGRAPH__VIEW_NAME:
				setViewName(VIEW_NAME_EDEFAULT);
				return;
			case DiagraphPackage.DGRAPH__FACADE1:
				setFacade1(FACADE1_EDEFAULT);
				return;
			case DiagraphPackage.DGRAPH__FACADE2:
				setFacade2(FACADE2_EDEFAULT);
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
			case DiagraphPackage.DGRAPH__ELEMENTS:
				return elements != null && !elements.isEmpty();
			case DiagraphPackage.DGRAPH__POINT_OF_VIEW:
				return pointOfView != null;
			case DiagraphPackage.DGRAPH__VIEW_NAME:
				return VIEW_NAME_EDEFAULT == null ? viewName != null : !VIEW_NAME_EDEFAULT.equals(viewName);
			case DiagraphPackage.DGRAPH__FACADE1:
				return FACADE1_EDEFAULT == null ? facade1 != null : !FACADE1_EDEFAULT.equals(facade1);
			case DiagraphPackage.DGRAPH__FACADE2:
				return FACADE2_EDEFAULT == null ? facade2 != null : !FACADE2_EDEFAULT.equals(facade2);
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
		result.append(" (viewName: ");
		result.append(viewName);
		result.append(", facade1: ");
		result.append(facade1);
		result.append(", facade2: ");
		result.append(facade2);
		result.append(')');
		return result.toString();
	}

} //DGraphImpl
