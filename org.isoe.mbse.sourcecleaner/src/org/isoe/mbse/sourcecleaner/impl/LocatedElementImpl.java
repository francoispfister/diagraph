/**
 */
package org.isoe.mbse.sourcecleaner.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.isoe.mbse.sourcecleaner.LocatedElement;
import org.isoe.mbse.sourcecleaner.SourcecleanerPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Located Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.LocatedElementImpl#getAbsolutePath <em>Absolute Path</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.LocatedElementImpl#getName <em>Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class LocatedElementImpl extends EObjectImpl implements LocatedElement {
	/**
	 * The default value of the '{@link #getAbsolutePath() <em>Absolute Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAbsolutePath()
	 * @generated
	 * @ordered
	 */
	protected static final String ABSOLUTE_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAbsolutePath() <em>Absolute Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAbsolutePath()
	 * @generated
	 * @ordered
	 */
	protected String absolutePath = ABSOLUTE_PATH_EDEFAULT;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LocatedElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SourcecleanerPackage.Literals.LOCATED_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAbsolutePath() {
		return absolutePath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAbsolutePath(String newAbsolutePath) {
		String oldAbsolutePath = absolutePath;
		absolutePath = newAbsolutePath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.LOCATED_ELEMENT__ABSOLUTE_PATH, oldAbsolutePath, absolutePath));
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
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.LOCATED_ELEMENT__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SourcecleanerPackage.LOCATED_ELEMENT__ABSOLUTE_PATH:
				return getAbsolutePath();
			case SourcecleanerPackage.LOCATED_ELEMENT__NAME:
				return getName();
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
			case SourcecleanerPackage.LOCATED_ELEMENT__ABSOLUTE_PATH:
				setAbsolutePath((String)newValue);
				return;
			case SourcecleanerPackage.LOCATED_ELEMENT__NAME:
				setName((String)newValue);
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
			case SourcecleanerPackage.LOCATED_ELEMENT__ABSOLUTE_PATH:
				setAbsolutePath(ABSOLUTE_PATH_EDEFAULT);
				return;
			case SourcecleanerPackage.LOCATED_ELEMENT__NAME:
				setName(NAME_EDEFAULT);
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
			case SourcecleanerPackage.LOCATED_ELEMENT__ABSOLUTE_PATH:
				return ABSOLUTE_PATH_EDEFAULT == null ? absolutePath != null : !ABSOLUTE_PATH_EDEFAULT.equals(absolutePath);
			case SourcecleanerPackage.LOCATED_ELEMENT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
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
		result.append(" (absolutePath: ");
		result.append(absolutePath);
		result.append(", name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //LocatedElementImpl
