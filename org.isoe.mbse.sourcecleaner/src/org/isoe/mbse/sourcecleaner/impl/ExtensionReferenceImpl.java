/**
 */
package org.isoe.mbse.sourcecleaner.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;

import org.isoe.mbse.sourcecleaner.ExtensionReference;
import org.isoe.mbse.sourcecleaner.Java;
import org.isoe.mbse.sourcecleaner.Schema;
import org.isoe.mbse.sourcecleaner.SourcecleanerPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Extension Reference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.ExtensionReferenceImpl#getSchema <em>Schema</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.ExtensionReferenceImpl#getJavaclass <em>Javaclass</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.ExtensionReferenceImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.ExtensionReferenceImpl#getJava <em>Java</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.ExtensionReferenceImpl#getPackage <em>Package</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.ExtensionReferenceImpl#getProject <em>Project</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ExtensionReferenceImpl extends EObjectImpl implements ExtensionReference {
	/**
	 * The cached value of the '{@link #getJavaclass() <em>Javaclass</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJavaclass()
	 * @generated
	 * @ordered
	 */
	protected Java javaclass;

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
	 * The default value of the '{@link #getJava() <em>Java</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJava()
	 * @generated
	 * @ordered
	 */
	protected static final String JAVA_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getJava() <em>Java</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJava()
	 * @generated
	 * @ordered
	 */
	protected String java = JAVA_EDEFAULT;

	/**
	 * The default value of the '{@link #getPackage() <em>Package</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackage()
	 * @generated
	 * @ordered
	 */
	protected static final String PACKAGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPackage() <em>Package</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackage()
	 * @generated
	 * @ordered
	 */
	protected String package_ = PACKAGE_EDEFAULT;

	/**
	 * The default value of the '{@link #getProject() <em>Project</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProject()
	 * @generated
	 * @ordered
	 */
	protected static final String PROJECT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProject() <em>Project</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProject()
	 * @generated
	 * @ordered
	 */
	protected String project = PROJECT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExtensionReferenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SourcecleanerPackage.Literals.EXTENSION_REFERENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Schema getSchema() {
		if (eContainerFeatureID() != SourcecleanerPackage.EXTENSION_REFERENCE__SCHEMA) return null;
		return (Schema)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSchema(Schema newSchema, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newSchema, SourcecleanerPackage.EXTENSION_REFERENCE__SCHEMA, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSchema(Schema newSchema) {
		if (newSchema != eInternalContainer() || (eContainerFeatureID() != SourcecleanerPackage.EXTENSION_REFERENCE__SCHEMA && newSchema != null)) {
			if (EcoreUtil.isAncestor(this, newSchema))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newSchema != null)
				msgs = ((InternalEObject)newSchema).eInverseAdd(this, SourcecleanerPackage.SCHEMA__REFERENCES, Schema.class, msgs);
			msgs = basicSetSchema(newSchema, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.EXTENSION_REFERENCE__SCHEMA, newSchema, newSchema));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Java getJavaclass() {
		if (javaclass != null && javaclass.eIsProxy()) {
			InternalEObject oldJavaclass = (InternalEObject)javaclass;
			javaclass = (Java)eResolveProxy(oldJavaclass);
			if (javaclass != oldJavaclass) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SourcecleanerPackage.EXTENSION_REFERENCE__JAVACLASS, oldJavaclass, javaclass));
			}
		}
		return javaclass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Java basicGetJavaclass() {
		return javaclass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setJavaclass(Java newJavaclass) {
		Java oldJavaclass = javaclass;
		javaclass = newJavaclass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.EXTENSION_REFERENCE__JAVACLASS, oldJavaclass, javaclass));
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
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.EXTENSION_REFERENCE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getJava() {
		return java;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setJava(String newJava) {
		String oldJava = java;
		java = newJava;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.EXTENSION_REFERENCE__JAVA, oldJava, java));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPackage() {
		return package_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPackage(String newPackage) {
		String oldPackage = package_;
		package_ = newPackage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.EXTENSION_REFERENCE__PACKAGE, oldPackage, package_));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getProject() {
		return project;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProject(String newProject) {
		String oldProject = project;
		project = newProject;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.EXTENSION_REFERENCE__PROJECT, oldProject, project));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SourcecleanerPackage.EXTENSION_REFERENCE__SCHEMA:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetSchema((Schema)otherEnd, msgs);
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
			case SourcecleanerPackage.EXTENSION_REFERENCE__SCHEMA:
				return basicSetSchema(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case SourcecleanerPackage.EXTENSION_REFERENCE__SCHEMA:
				return eInternalContainer().eInverseRemove(this, SourcecleanerPackage.SCHEMA__REFERENCES, Schema.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SourcecleanerPackage.EXTENSION_REFERENCE__SCHEMA:
				return getSchema();
			case SourcecleanerPackage.EXTENSION_REFERENCE__JAVACLASS:
				if (resolve) return getJavaclass();
				return basicGetJavaclass();
			case SourcecleanerPackage.EXTENSION_REFERENCE__NAME:
				return getName();
			case SourcecleanerPackage.EXTENSION_REFERENCE__JAVA:
				return getJava();
			case SourcecleanerPackage.EXTENSION_REFERENCE__PACKAGE:
				return getPackage();
			case SourcecleanerPackage.EXTENSION_REFERENCE__PROJECT:
				return getProject();
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
			case SourcecleanerPackage.EXTENSION_REFERENCE__SCHEMA:
				setSchema((Schema)newValue);
				return;
			case SourcecleanerPackage.EXTENSION_REFERENCE__JAVACLASS:
				setJavaclass((Java)newValue);
				return;
			case SourcecleanerPackage.EXTENSION_REFERENCE__NAME:
				setName((String)newValue);
				return;
			case SourcecleanerPackage.EXTENSION_REFERENCE__JAVA:
				setJava((String)newValue);
				return;
			case SourcecleanerPackage.EXTENSION_REFERENCE__PACKAGE:
				setPackage((String)newValue);
				return;
			case SourcecleanerPackage.EXTENSION_REFERENCE__PROJECT:
				setProject((String)newValue);
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
			case SourcecleanerPackage.EXTENSION_REFERENCE__SCHEMA:
				setSchema((Schema)null);
				return;
			case SourcecleanerPackage.EXTENSION_REFERENCE__JAVACLASS:
				setJavaclass((Java)null);
				return;
			case SourcecleanerPackage.EXTENSION_REFERENCE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case SourcecleanerPackage.EXTENSION_REFERENCE__JAVA:
				setJava(JAVA_EDEFAULT);
				return;
			case SourcecleanerPackage.EXTENSION_REFERENCE__PACKAGE:
				setPackage(PACKAGE_EDEFAULT);
				return;
			case SourcecleanerPackage.EXTENSION_REFERENCE__PROJECT:
				setProject(PROJECT_EDEFAULT);
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
			case SourcecleanerPackage.EXTENSION_REFERENCE__SCHEMA:
				return getSchema() != null;
			case SourcecleanerPackage.EXTENSION_REFERENCE__JAVACLASS:
				return javaclass != null;
			case SourcecleanerPackage.EXTENSION_REFERENCE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case SourcecleanerPackage.EXTENSION_REFERENCE__JAVA:
				return JAVA_EDEFAULT == null ? java != null : !JAVA_EDEFAULT.equals(java);
			case SourcecleanerPackage.EXTENSION_REFERENCE__PACKAGE:
				return PACKAGE_EDEFAULT == null ? package_ != null : !PACKAGE_EDEFAULT.equals(package_);
			case SourcecleanerPackage.EXTENSION_REFERENCE__PROJECT:
				return PROJECT_EDEFAULT == null ? project != null : !PROJECT_EDEFAULT.equals(project);
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
		result.append(" (name: ");
		result.append(name);
		result.append(", java: ");
		result.append(java);
		result.append(", package: ");
		result.append(package_);
		result.append(", project: ");
		result.append(project);
		result.append(')');
		return result.toString();
	}

} //ExtensionReferenceImpl
