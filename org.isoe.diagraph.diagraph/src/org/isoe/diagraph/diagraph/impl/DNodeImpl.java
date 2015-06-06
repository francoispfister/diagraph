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

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.isoe.diagraph.diagraph.DContainment;
import org.isoe.diagraph.diagraph.DNode;
import org.isoe.diagraph.diagraph.DOwnedElement;
import org.isoe.diagraph.diagraph.DShape;
import org.isoe.diagraph.diagraph.DViewNavigation;
import org.isoe.diagraph.diagraph.DiagraphPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>DNode</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DNodeImpl#getOwner <em>Owner</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DNodeImpl#getViewNavigation <em>View Navigation</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DNodeImpl#getShape <em>Shape</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DNodeImpl#isLayout <em>Layout</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DNodeImpl#getNavigationLink <em>Navigation Link</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DNodeImpl#getContainments <em>Containments</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DNodeImpl extends DLabeledElementImpl implements DNode {
	/**
	 * The cached value of the '{@link #getOwner() <em>Owner</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwner()
	 * @generated
	 * @ordered
	 */
	protected DNode owner;

	/**
	 * The cached value of the '{@link #getViewNavigation() <em>View Navigation</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getViewNavigation()
	 * @generated
	 * @ordered
	 */
	protected DViewNavigation viewNavigation;

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
	 * The default value of the '{@link #isLayout() <em>Layout</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLayout()
	 * @generated
	 * @ordered
	 */
	protected static final boolean LAYOUT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isLayout() <em>Layout</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLayout()
	 * @generated
	 * @ordered
	 */
	protected boolean layout = LAYOUT_EDEFAULT;

	/**
	 * The default value of the '{@link #getNavigationLink() <em>Navigation Link</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNavigationLink()
	 * @generated
	 * @ordered
	 */
	protected static final String NAVIGATION_LINK_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getNavigationLink() <em>Navigation Link</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNavigationLink()
	 * @generated
	 * @ordered
	 */
	protected String navigationLink = NAVIGATION_LINK_EDEFAULT;

	/**
	 * The cached value of the '{@link #getContainments() <em>Containments</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContainments()
	 * @generated
	 * @ordered
	 */
	protected EList<DContainment> containments;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DNodeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DiagraphPackage.Literals.DNODE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DNode getOwner() {
		if (owner != null && owner.eIsProxy()) {
			InternalEObject oldOwner = (InternalEObject)owner;
			owner = (DNode)eResolveProxy(oldOwner);
			if (owner != oldOwner) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DiagraphPackage.DNODE__OWNER, oldOwner, owner));
			}
		}
		return owner;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DNode basicGetOwner() {
		return owner;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOwner(DNode newOwner) {
		DNode oldOwner = owner;
		owner = newOwner;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagraphPackage.DNODE__OWNER, oldOwner, owner));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DViewNavigation getViewNavigation() {
		return viewNavigation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetViewNavigation(DViewNavigation newViewNavigation, NotificationChain msgs) {
		DViewNavigation oldViewNavigation = viewNavigation;
		viewNavigation = newViewNavigation;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DiagraphPackage.DNODE__VIEW_NAVIGATION, oldViewNavigation, newViewNavigation);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setViewNavigation(DViewNavigation newViewNavigation) {
		if (newViewNavigation != viewNavigation) {
			NotificationChain msgs = null;
			if (viewNavigation != null)
				msgs = ((InternalEObject)viewNavigation).eInverseRemove(this, DiagraphPackage.DVIEW_NAVIGATION__NAVIGATION_SOURCE, DViewNavigation.class, msgs);
			if (newViewNavigation != null)
				msgs = ((InternalEObject)newViewNavigation).eInverseAdd(this, DiagraphPackage.DVIEW_NAVIGATION__NAVIGATION_SOURCE, DViewNavigation.class, msgs);
			msgs = basicSetViewNavigation(newViewNavigation, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagraphPackage.DNODE__VIEW_NAVIGATION, newViewNavigation, newViewNavigation));
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
			eNotify(new ENotificationImpl(this, Notification.SET, DiagraphPackage.DNODE__SHAPE, oldShape, shape));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isLayout() {
		return layout;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLayout(boolean newLayout) {
		boolean oldLayout = layout;
		layout = newLayout;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagraphPackage.DNODE__LAYOUT, oldLayout, layout));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getNavigationLink() {
		return navigationLink;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNavigationLink(String newNavigationLink) {
		String oldNavigationLink = navigationLink;
		navigationLink = newNavigationLink;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagraphPackage.DNODE__NAVIGATION_LINK, oldNavigationLink, navigationLink));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DContainment> getContainments() {
		if (containments == null) {
			containments = new EObjectContainmentWithInverseEList<DContainment>(DContainment.class, this, DiagraphPackage.DNODE__CONTAINMENTS, DiagraphPackage.DCONTAINMENT__NODE);
		}
		return containments;
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
			case DiagraphPackage.DNODE__VIEW_NAVIGATION:
				if (viewNavigation != null)
					msgs = ((InternalEObject)viewNavigation).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DiagraphPackage.DNODE__VIEW_NAVIGATION, null, msgs);
				return basicSetViewNavigation((DViewNavigation)otherEnd, msgs);
			case DiagraphPackage.DNODE__CONTAINMENTS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getContainments()).basicAdd(otherEnd, msgs);
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
			case DiagraphPackage.DNODE__VIEW_NAVIGATION:
				return basicSetViewNavigation(null, msgs);
			case DiagraphPackage.DNODE__CONTAINMENTS:
				return ((InternalEList<?>)getContainments()).basicRemove(otherEnd, msgs);
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
			case DiagraphPackage.DNODE__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case DiagraphPackage.DNODE__VIEW_NAVIGATION:
				return getViewNavigation();
			case DiagraphPackage.DNODE__SHAPE:
				return getShape();
			case DiagraphPackage.DNODE__LAYOUT:
				return isLayout();
			case DiagraphPackage.DNODE__NAVIGATION_LINK:
				return getNavigationLink();
			case DiagraphPackage.DNODE__CONTAINMENTS:
				return getContainments();
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
			case DiagraphPackage.DNODE__OWNER:
				setOwner((DNode)newValue);
				return;
			case DiagraphPackage.DNODE__VIEW_NAVIGATION:
				setViewNavigation((DViewNavigation)newValue);
				return;
			case DiagraphPackage.DNODE__SHAPE:
				setShape((DShape)newValue);
				return;
			case DiagraphPackage.DNODE__LAYOUT:
				setLayout((Boolean)newValue);
				return;
			case DiagraphPackage.DNODE__NAVIGATION_LINK:
				setNavigationLink((String)newValue);
				return;
			case DiagraphPackage.DNODE__CONTAINMENTS:
				getContainments().clear();
				getContainments().addAll((Collection<? extends DContainment>)newValue);
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
			case DiagraphPackage.DNODE__OWNER:
				setOwner((DNode)null);
				return;
			case DiagraphPackage.DNODE__VIEW_NAVIGATION:
				setViewNavigation((DViewNavigation)null);
				return;
			case DiagraphPackage.DNODE__SHAPE:
				setShape(SHAPE_EDEFAULT);
				return;
			case DiagraphPackage.DNODE__LAYOUT:
				setLayout(LAYOUT_EDEFAULT);
				return;
			case DiagraphPackage.DNODE__NAVIGATION_LINK:
				setNavigationLink(NAVIGATION_LINK_EDEFAULT);
				return;
			case DiagraphPackage.DNODE__CONTAINMENTS:
				getContainments().clear();
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
			case DiagraphPackage.DNODE__OWNER:
				return owner != null;
			case DiagraphPackage.DNODE__VIEW_NAVIGATION:
				return viewNavigation != null;
			case DiagraphPackage.DNODE__SHAPE:
				return shape != SHAPE_EDEFAULT;
			case DiagraphPackage.DNODE__LAYOUT:
				return layout != LAYOUT_EDEFAULT;
			case DiagraphPackage.DNODE__NAVIGATION_LINK:
				return NAVIGATION_LINK_EDEFAULT == null ? navigationLink != null : !NAVIGATION_LINK_EDEFAULT.equals(navigationLink);
			case DiagraphPackage.DNODE__CONTAINMENTS:
				return containments != null && !containments.isEmpty();
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
		if (baseClass == DOwnedElement.class) {
			switch (derivedFeatureID) {
				case DiagraphPackage.DNODE__OWNER: return DiagraphPackage.DOWNED_ELEMENT__OWNER;
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
		if (baseClass == DOwnedElement.class) {
			switch (baseFeatureID) {
				case DiagraphPackage.DOWNED_ELEMENT__OWNER: return DiagraphPackage.DNODE__OWNER;
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
		result.append(" (shape: ");
		result.append(shape);
		result.append(", layout: ");
		result.append(layout);
		result.append(", navigationLink: ");
		result.append(navigationLink);
		result.append(')');
		return result.toString();
	}

} //DNodeImpl
