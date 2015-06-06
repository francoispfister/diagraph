/**
 */
package org.isoe.diagraph.diagraph.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.isoe.diagraph.diagraph.DLabel;
import org.isoe.diagraph.diagraph.DiagraphPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>DLabel</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DLabelImpl#getAttribute <em>Attribute</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DLabelImpl#isPropagated <em>Propagated</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DLabelImpl#isInferred <em>Inferred</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DLabelImpl#isAbztract <em>Abztract</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DLabelImpl extends MinimalEObjectImpl.Container implements DLabel {
	/**
	 * The cached value of the '{@link #getAttribute() <em>Attribute</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttribute()
	 * @generated
	 * @ordered
	 */
	protected EAttribute attribute;

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
	 * The default value of the '{@link #isInferred() <em>Inferred</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isInferred()
	 * @generated
	 * @ordered
	 */
	protected static final boolean INFERRED_EDEFAULT = false;
	/**
	 * The cached value of the '{@link #isInferred() <em>Inferred</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isInferred()
	 * @generated
	 * @ordered
	 */
	protected boolean inferred = INFERRED_EDEFAULT;

	/**
	 * The default value of the '{@link #isAbztract() <em>Abztract</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAbztract()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ABZTRACT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isAbztract() <em>Abztract</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAbztract()
	 * @generated
	 * @ordered
	 */
	protected boolean abztract = ABZTRACT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DLabelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DiagraphPackage.Literals.DLABEL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAttribute() {
		if (attribute != null && attribute.eIsProxy()) {
			InternalEObject oldAttribute = (InternalEObject)attribute;
			attribute = (EAttribute)eResolveProxy(oldAttribute);
			if (attribute != oldAttribute) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DiagraphPackage.DLABEL__ATTRIBUTE, oldAttribute, attribute));
			}
		}
		return attribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute basicGetAttribute() {
		return attribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAttribute(EAttribute newAttribute) {
		EAttribute oldAttribute = attribute;
		attribute = newAttribute;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagraphPackage.DLABEL__ATTRIBUTE, oldAttribute, attribute));
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
			eNotify(new ENotificationImpl(this, Notification.SET, DiagraphPackage.DLABEL__PROPAGATED, oldPropagated, propagated));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isInferred() {
		return inferred;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInferred(boolean newInferred) {
		boolean oldInferred = inferred;
		inferred = newInferred;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagraphPackage.DLABEL__INFERRED, oldInferred, inferred));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAbztract() {
		return abztract;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAbztract(boolean newAbztract) {
		boolean oldAbztract = abztract;
		abztract = newAbztract;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagraphPackage.DLABEL__ABZTRACT, oldAbztract, abztract));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DiagraphPackage.DLABEL__ATTRIBUTE:
				if (resolve) return getAttribute();
				return basicGetAttribute();
			case DiagraphPackage.DLABEL__PROPAGATED:
				return isPropagated();
			case DiagraphPackage.DLABEL__INFERRED:
				return isInferred();
			case DiagraphPackage.DLABEL__ABZTRACT:
				return isAbztract();
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
			case DiagraphPackage.DLABEL__ATTRIBUTE:
				setAttribute((EAttribute)newValue);
				return;
			case DiagraphPackage.DLABEL__PROPAGATED:
				setPropagated((Boolean)newValue);
				return;
			case DiagraphPackage.DLABEL__INFERRED:
				setInferred((Boolean)newValue);
				return;
			case DiagraphPackage.DLABEL__ABZTRACT:
				setAbztract((Boolean)newValue);
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
			case DiagraphPackage.DLABEL__ATTRIBUTE:
				setAttribute((EAttribute)null);
				return;
			case DiagraphPackage.DLABEL__PROPAGATED:
				setPropagated(PROPAGATED_EDEFAULT);
				return;
			case DiagraphPackage.DLABEL__INFERRED:
				setInferred(INFERRED_EDEFAULT);
				return;
			case DiagraphPackage.DLABEL__ABZTRACT:
				setAbztract(ABZTRACT_EDEFAULT);
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
			case DiagraphPackage.DLABEL__ATTRIBUTE:
				return attribute != null;
			case DiagraphPackage.DLABEL__PROPAGATED:
				return propagated != PROPAGATED_EDEFAULT;
			case DiagraphPackage.DLABEL__INFERRED:
				return inferred != INFERRED_EDEFAULT;
			case DiagraphPackage.DLABEL__ABZTRACT:
				return abztract != ABZTRACT_EDEFAULT;
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
		result.append(", inferred: ");
		result.append(inferred);
		result.append(", abztract: ");
		result.append(abztract);
		result.append(')');
		return result.toString();
	}

} //DLabelImpl
