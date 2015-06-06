/**
 */
package org.isoe.mbse.sourcecleaner.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.isoe.mbse.sourcecleaner.Configuration;
import org.isoe.mbse.sourcecleaner.Project;
import org.isoe.mbse.sourcecleaner.SourcecleanerPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.ConfigurationImpl#getProjects <em>Projects</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.ConfigurationImpl#getLocation <em>Location</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.ConfigurationImpl#getTemp <em>Temp</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConfigurationImpl extends EObjectImpl implements Configuration {
	/**
	 * The cached value of the '{@link #getProjects() <em>Projects</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProjects()
	 * @generated
	 * @ordered
	 */
	protected EList<Project> projects;

	/**
	 * The default value of the '{@link #getLocation() <em>Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocation()
	 * @generated
	 * @ordered
	 */
	protected static final String LOCATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLocation() <em>Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocation()
	 * @generated
	 * @ordered
	 */
	protected String location = LOCATION_EDEFAULT;

	/**
	 * The default value of the '{@link #getTemp() <em>Temp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTemp()
	 * @generated
	 * @ordered
	 */
	protected static final String TEMP_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTemp() <em>Temp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTemp()
	 * @generated
	 * @ordered
	 */
	protected String temp = TEMP_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SourcecleanerPackage.Literals.CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Project> getProjects() {
		if (projects == null) {
			projects = new EObjectContainmentEList<Project>(Project.class, this, SourcecleanerPackage.CONFIGURATION__PROJECTS);
		}
		return projects;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLocation(String newLocation) {
		String oldLocation = location;
		location = newLocation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.CONFIGURATION__LOCATION, oldLocation, location));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTemp() {
		return temp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTemp(String newTemp) {
		String oldTemp = temp;
		temp = newTemp;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.CONFIGURATION__TEMP, oldTemp, temp));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SourcecleanerPackage.CONFIGURATION__PROJECTS:
				return ((InternalEList<?>)getProjects()).basicRemove(otherEnd, msgs);
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
			case SourcecleanerPackage.CONFIGURATION__PROJECTS:
				return getProjects();
			case SourcecleanerPackage.CONFIGURATION__LOCATION:
				return getLocation();
			case SourcecleanerPackage.CONFIGURATION__TEMP:
				return getTemp();
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
			case SourcecleanerPackage.CONFIGURATION__PROJECTS:
				getProjects().clear();
				getProjects().addAll((Collection<? extends Project>)newValue);
				return;
			case SourcecleanerPackage.CONFIGURATION__LOCATION:
				setLocation((String)newValue);
				return;
			case SourcecleanerPackage.CONFIGURATION__TEMP:
				setTemp((String)newValue);
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
			case SourcecleanerPackage.CONFIGURATION__PROJECTS:
				getProjects().clear();
				return;
			case SourcecleanerPackage.CONFIGURATION__LOCATION:
				setLocation(LOCATION_EDEFAULT);
				return;
			case SourcecleanerPackage.CONFIGURATION__TEMP:
				setTemp(TEMP_EDEFAULT);
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
			case SourcecleanerPackage.CONFIGURATION__PROJECTS:
				return projects != null && !projects.isEmpty();
			case SourcecleanerPackage.CONFIGURATION__LOCATION:
				return LOCATION_EDEFAULT == null ? location != null : !LOCATION_EDEFAULT.equals(location);
			case SourcecleanerPackage.CONFIGURATION__TEMP:
				return TEMP_EDEFAULT == null ? temp != null : !TEMP_EDEFAULT.equals(temp);
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
		result.append(" (location: ");
		result.append(location);
		result.append(", temp: ");
		result.append(temp);
		result.append(')');
		return result.toString();
	}

} //ConfigurationImpl
