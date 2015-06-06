/**
 */
package org.isoe.diagraph.diagraph.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.isoe.diagraph.diagraph.DEdge;
import org.isoe.diagraph.diagraph.DNode;
import org.isoe.diagraph.diagraph.DiagraphPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>DEdge</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DEdgeImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DEdgeImpl#getTargetReference <em>Target Reference</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DEdgeImpl#isPropagated <em>Propagated</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class DEdgeImpl extends DGraphElementImpl implements DEdge {
	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected DNode target;

	/**
	 * The cached value of the '{@link #getTargetReference() <em>Target Reference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetReference()
	 * @generated
	 * @ordered
	 */
	protected EReference targetReference;

	/**
	 * The default value of the '{@link #isPropagated() <em>Propagated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPropagated()
	 * @generated
	 * @ordered
	 */
	protected static final boolean PROPAGATED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isPropagated() <em>Propagated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPropagated()
	 * @generated
	 * @ordered
	 */
	protected boolean propagated = PROPAGATED_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DEdgeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DiagraphPackage.Literals.DEDGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DNode getTarget() {
		if (target != null && target.eIsProxy()) {
			InternalEObject oldTarget = (InternalEObject)target;
			target = (DNode)eResolveProxy(oldTarget);
			if (target != oldTarget) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DiagraphPackage.DEDGE__TARGET, oldTarget, target));
			}
		}
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DNode basicGetTarget() {
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTarget(DNode newTarget) {
		DNode oldTarget = target;
		target = newTarget;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagraphPackage.DEDGE__TARGET, oldTarget, target));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTargetReference() {
		if (targetReference != null && targetReference.eIsProxy()) {
			InternalEObject oldTargetReference = (InternalEObject)targetReference;
			targetReference = (EReference)eResolveProxy(oldTargetReference);
			if (targetReference != oldTargetReference) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DiagraphPackage.DEDGE__TARGET_REFERENCE, oldTargetReference, targetReference));
			}
		}
		return targetReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference basicGetTargetReference() {
		return targetReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTargetReference(EReference newTargetReference) {
		EReference oldTargetReference = targetReference;
		targetReference = newTargetReference;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagraphPackage.DEDGE__TARGET_REFERENCE, oldTargetReference, targetReference));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isPropagated() {
		return propagated;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPropagated(boolean newPropagated) {
		boolean oldPropagated = propagated;
		propagated = newPropagated;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagraphPackage.DEDGE__PROPAGATED, oldPropagated, propagated));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DiagraphPackage.DEDGE__TARGET:
				if (resolve) return getTarget();
				return basicGetTarget();
			case DiagraphPackage.DEDGE__TARGET_REFERENCE:
				if (resolve) return getTargetReference();
				return basicGetTargetReference();
			case DiagraphPackage.DEDGE__PROPAGATED:
				return isPropagated();
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
			case DiagraphPackage.DEDGE__TARGET:
				setTarget((DNode)newValue);
				return;
			case DiagraphPackage.DEDGE__TARGET_REFERENCE:
				setTargetReference((EReference)newValue);
				return;
			case DiagraphPackage.DEDGE__PROPAGATED:
				setPropagated((Boolean)newValue);
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
			case DiagraphPackage.DEDGE__TARGET:
				setTarget((DNode)null);
				return;
			case DiagraphPackage.DEDGE__TARGET_REFERENCE:
				setTargetReference((EReference)null);
				return;
			case DiagraphPackage.DEDGE__PROPAGATED:
				setPropagated(PROPAGATED_EDEFAULT);
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
			case DiagraphPackage.DEDGE__TARGET:
				return target != null;
			case DiagraphPackage.DEDGE__TARGET_REFERENCE:
				return targetReference != null;
			case DiagraphPackage.DEDGE__PROPAGATED:
				return propagated != PROPAGATED_EDEFAULT;
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
		result.append(" (propagated: ");
		result.append(propagated);
		result.append(')');
		return result.toString();
	}

} //DEdgeImpl
