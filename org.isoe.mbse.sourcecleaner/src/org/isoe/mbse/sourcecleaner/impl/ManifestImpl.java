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

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.isoe.mbse.sourcecleaner.ClassPath;
import org.isoe.mbse.sourcecleaner.Dependency;
import org.isoe.mbse.sourcecleaner.Export;
import org.isoe.mbse.sourcecleaner.Manifest;
import org.isoe.mbse.sourcecleaner.SourcecleanerPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Manifest</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.ManifestImpl#getSymbolicName <em>Symbolic Name</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.ManifestImpl#isSingleton <em>Singleton</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.ManifestImpl#getVendor <em>Vendor</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.ManifestImpl#getVersion <em>Version</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.ManifestImpl#getVersionId <em>Version Id</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.ManifestImpl#getVersionQualifier <em>Version Qualifier</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.ManifestImpl#getDependencies <em>Dependencies</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.ManifestImpl#isLazy <em>Lazy</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.ManifestImpl#getExecutionEnvironment <em>Execution Environment</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.ManifestImpl#isDiagraph <em>Diagraph</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.ManifestImpl#getClasspathes <em>Classpathes</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.ManifestImpl#getExports <em>Exports</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ManifestImpl extends SourceImpl implements Manifest {
	/**
	 * The default value of the '{@link #getSymbolicName() <em>Symbolic Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSymbolicName()
	 * @generated
	 * @ordered
	 */
	protected static final String SYMBOLIC_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSymbolicName() <em>Symbolic Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSymbolicName()
	 * @generated
	 * @ordered
	 */
	protected String symbolicName = SYMBOLIC_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #isSingleton() <em>Singleton</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSingleton()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SINGLETON_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isSingleton() <em>Singleton</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSingleton()
	 * @generated
	 * @ordered
	 */
	protected boolean singleton = SINGLETON_EDEFAULT;

	/**
	 * The default value of the '{@link #getVendor() <em>Vendor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVendor()
	 * @generated
	 * @ordered
	 */
	protected static final String VENDOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVendor() <em>Vendor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVendor()
	 * @generated
	 * @ordered
	 */
	protected String vendor = VENDOR_EDEFAULT;

	/**
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected String version = VERSION_EDEFAULT;

	/**
	 * The default value of the '{@link #getVersionId() <em>Version Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersionId()
	 * @generated
	 * @ordered
	 */
	protected static final String VERSION_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVersionId() <em>Version Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersionId()
	 * @generated
	 * @ordered
	 */
	protected String versionId = VERSION_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getVersionQualifier() <em>Version Qualifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersionQualifier()
	 * @generated
	 * @ordered
	 */
	protected static final String VERSION_QUALIFIER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVersionQualifier() <em>Version Qualifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersionQualifier()
	 * @generated
	 * @ordered
	 */
	protected String versionQualifier = VERSION_QUALIFIER_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDependencies() <em>Dependencies</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDependencies()
	 * @generated
	 * @ordered
	 */
	protected EList<Dependency> dependencies;

	/**
	 * The default value of the '{@link #isLazy() <em>Lazy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLazy()
	 * @generated
	 * @ordered
	 */
	protected static final boolean LAZY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isLazy() <em>Lazy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLazy()
	 * @generated
	 * @ordered
	 */
	protected boolean lazy = LAZY_EDEFAULT;

	/**
	 * The default value of the '{@link #getExecutionEnvironment() <em>Execution Environment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExecutionEnvironment()
	 * @generated
	 * @ordered
	 */
	protected static final String EXECUTION_ENVIRONMENT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getExecutionEnvironment() <em>Execution Environment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExecutionEnvironment()
	 * @generated
	 * @ordered
	 */
	protected String executionEnvironment = EXECUTION_ENVIRONMENT_EDEFAULT;

	/**
	 * The default value of the '{@link #isDiagraph() <em>Diagraph</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDiagraph()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DIAGRAPH_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDiagraph() <em>Diagraph</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDiagraph()
	 * @generated
	 * @ordered
	 */
	protected boolean diagraph = DIAGRAPH_EDEFAULT;

	/**
	 * The cached value of the '{@link #getClasspathes() <em>Classpathes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClasspathes()
	 * @generated
	 * @ordered
	 */
	protected EList<ClassPath> classpathes;

	/**
	 * The cached value of the '{@link #getExports() <em>Exports</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExports()
	 * @generated
	 * @ordered
	 */
	protected EList<Export> exports;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ManifestImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SourcecleanerPackage.Literals.MANIFEST;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSymbolicName() {
		return symbolicName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSymbolicName(String newSymbolicName) {
		String oldSymbolicName = symbolicName;
		symbolicName = newSymbolicName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.MANIFEST__SYMBOLIC_NAME, oldSymbolicName, symbolicName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSingleton() {
		return singleton;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSingleton(boolean newSingleton) {
		boolean oldSingleton = singleton;
		singleton = newSingleton;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.MANIFEST__SINGLETON, oldSingleton, singleton));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getVendor() {
		return vendor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVendor(String newVendor) {
		String oldVendor = vendor;
		vendor = newVendor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.MANIFEST__VENDOR, oldVendor, vendor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVersion(String newVersion) {
		String oldVersion = version;
		version = newVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.MANIFEST__VERSION, oldVersion, version));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getVersionId() {
		return versionId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVersionId(String newVersionId) {
		String oldVersionId = versionId;
		versionId = newVersionId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.MANIFEST__VERSION_ID, oldVersionId, versionId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getVersionQualifier() {
		return versionQualifier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVersionQualifier(String newVersionQualifier) {
		String oldVersionQualifier = versionQualifier;
		versionQualifier = newVersionQualifier;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.MANIFEST__VERSION_QUALIFIER, oldVersionQualifier, versionQualifier));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Dependency> getDependencies() {
		if (dependencies == null) {
			dependencies = new EObjectContainmentWithInverseEList<Dependency>(Dependency.class, this, SourcecleanerPackage.MANIFEST__DEPENDENCIES, SourcecleanerPackage.DEPENDENCY__REQUERANT);
		}
		return dependencies;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isLazy() {
		return lazy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLazy(boolean newLazy) {
		boolean oldLazy = lazy;
		lazy = newLazy;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.MANIFEST__LAZY, oldLazy, lazy));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getExecutionEnvironment() {
		return executionEnvironment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExecutionEnvironment(String newExecutionEnvironment) {
		String oldExecutionEnvironment = executionEnvironment;
		executionEnvironment = newExecutionEnvironment;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.MANIFEST__EXECUTION_ENVIRONMENT, oldExecutionEnvironment, executionEnvironment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isDiagraph() {
		return diagraph;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDiagraph(boolean newDiagraph) {
		boolean oldDiagraph = diagraph;
		diagraph = newDiagraph;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.MANIFEST__DIAGRAPH, oldDiagraph, diagraph));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ClassPath> getClasspathes() {
		if (classpathes == null) {
			classpathes = new EObjectContainmentEList<ClassPath>(ClassPath.class, this, SourcecleanerPackage.MANIFEST__CLASSPATHES);
		}
		return classpathes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Export> getExports() {
		if (exports == null) {
			exports = new EObjectContainmentEList<Export>(Export.class, this, SourcecleanerPackage.MANIFEST__EXPORTS);
		}
		return exports;
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
			case SourcecleanerPackage.MANIFEST__DEPENDENCIES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getDependencies()).basicAdd(otherEnd, msgs);
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
			case SourcecleanerPackage.MANIFEST__DEPENDENCIES:
				return ((InternalEList<?>)getDependencies()).basicRemove(otherEnd, msgs);
			case SourcecleanerPackage.MANIFEST__CLASSPATHES:
				return ((InternalEList<?>)getClasspathes()).basicRemove(otherEnd, msgs);
			case SourcecleanerPackage.MANIFEST__EXPORTS:
				return ((InternalEList<?>)getExports()).basicRemove(otherEnd, msgs);
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
			case SourcecleanerPackage.MANIFEST__SYMBOLIC_NAME:
				return getSymbolicName();
			case SourcecleanerPackage.MANIFEST__SINGLETON:
				return isSingleton();
			case SourcecleanerPackage.MANIFEST__VENDOR:
				return getVendor();
			case SourcecleanerPackage.MANIFEST__VERSION:
				return getVersion();
			case SourcecleanerPackage.MANIFEST__VERSION_ID:
				return getVersionId();
			case SourcecleanerPackage.MANIFEST__VERSION_QUALIFIER:
				return getVersionQualifier();
			case SourcecleanerPackage.MANIFEST__DEPENDENCIES:
				return getDependencies();
			case SourcecleanerPackage.MANIFEST__LAZY:
				return isLazy();
			case SourcecleanerPackage.MANIFEST__EXECUTION_ENVIRONMENT:
				return getExecutionEnvironment();
			case SourcecleanerPackage.MANIFEST__DIAGRAPH:
				return isDiagraph();
			case SourcecleanerPackage.MANIFEST__CLASSPATHES:
				return getClasspathes();
			case SourcecleanerPackage.MANIFEST__EXPORTS:
				return getExports();
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
			case SourcecleanerPackage.MANIFEST__SYMBOLIC_NAME:
				setSymbolicName((String)newValue);
				return;
			case SourcecleanerPackage.MANIFEST__SINGLETON:
				setSingleton((Boolean)newValue);
				return;
			case SourcecleanerPackage.MANIFEST__VENDOR:
				setVendor((String)newValue);
				return;
			case SourcecleanerPackage.MANIFEST__VERSION:
				setVersion((String)newValue);
				return;
			case SourcecleanerPackage.MANIFEST__VERSION_ID:
				setVersionId((String)newValue);
				return;
			case SourcecleanerPackage.MANIFEST__VERSION_QUALIFIER:
				setVersionQualifier((String)newValue);
				return;
			case SourcecleanerPackage.MANIFEST__DEPENDENCIES:
				getDependencies().clear();
				getDependencies().addAll((Collection<? extends Dependency>)newValue);
				return;
			case SourcecleanerPackage.MANIFEST__LAZY:
				setLazy((Boolean)newValue);
				return;
			case SourcecleanerPackage.MANIFEST__EXECUTION_ENVIRONMENT:
				setExecutionEnvironment((String)newValue);
				return;
			case SourcecleanerPackage.MANIFEST__DIAGRAPH:
				setDiagraph((Boolean)newValue);
				return;
			case SourcecleanerPackage.MANIFEST__CLASSPATHES:
				getClasspathes().clear();
				getClasspathes().addAll((Collection<? extends ClassPath>)newValue);
				return;
			case SourcecleanerPackage.MANIFEST__EXPORTS:
				getExports().clear();
				getExports().addAll((Collection<? extends Export>)newValue);
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
			case SourcecleanerPackage.MANIFEST__SYMBOLIC_NAME:
				setSymbolicName(SYMBOLIC_NAME_EDEFAULT);
				return;
			case SourcecleanerPackage.MANIFEST__SINGLETON:
				setSingleton(SINGLETON_EDEFAULT);
				return;
			case SourcecleanerPackage.MANIFEST__VENDOR:
				setVendor(VENDOR_EDEFAULT);
				return;
			case SourcecleanerPackage.MANIFEST__VERSION:
				setVersion(VERSION_EDEFAULT);
				return;
			case SourcecleanerPackage.MANIFEST__VERSION_ID:
				setVersionId(VERSION_ID_EDEFAULT);
				return;
			case SourcecleanerPackage.MANIFEST__VERSION_QUALIFIER:
				setVersionQualifier(VERSION_QUALIFIER_EDEFAULT);
				return;
			case SourcecleanerPackage.MANIFEST__DEPENDENCIES:
				getDependencies().clear();
				return;
			case SourcecleanerPackage.MANIFEST__LAZY:
				setLazy(LAZY_EDEFAULT);
				return;
			case SourcecleanerPackage.MANIFEST__EXECUTION_ENVIRONMENT:
				setExecutionEnvironment(EXECUTION_ENVIRONMENT_EDEFAULT);
				return;
			case SourcecleanerPackage.MANIFEST__DIAGRAPH:
				setDiagraph(DIAGRAPH_EDEFAULT);
				return;
			case SourcecleanerPackage.MANIFEST__CLASSPATHES:
				getClasspathes().clear();
				return;
			case SourcecleanerPackage.MANIFEST__EXPORTS:
				getExports().clear();
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
			case SourcecleanerPackage.MANIFEST__SYMBOLIC_NAME:
				return SYMBOLIC_NAME_EDEFAULT == null ? symbolicName != null : !SYMBOLIC_NAME_EDEFAULT.equals(symbolicName);
			case SourcecleanerPackage.MANIFEST__SINGLETON:
				return singleton != SINGLETON_EDEFAULT;
			case SourcecleanerPackage.MANIFEST__VENDOR:
				return VENDOR_EDEFAULT == null ? vendor != null : !VENDOR_EDEFAULT.equals(vendor);
			case SourcecleanerPackage.MANIFEST__VERSION:
				return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
			case SourcecleanerPackage.MANIFEST__VERSION_ID:
				return VERSION_ID_EDEFAULT == null ? versionId != null : !VERSION_ID_EDEFAULT.equals(versionId);
			case SourcecleanerPackage.MANIFEST__VERSION_QUALIFIER:
				return VERSION_QUALIFIER_EDEFAULT == null ? versionQualifier != null : !VERSION_QUALIFIER_EDEFAULT.equals(versionQualifier);
			case SourcecleanerPackage.MANIFEST__DEPENDENCIES:
				return dependencies != null && !dependencies.isEmpty();
			case SourcecleanerPackage.MANIFEST__LAZY:
				return lazy != LAZY_EDEFAULT;
			case SourcecleanerPackage.MANIFEST__EXECUTION_ENVIRONMENT:
				return EXECUTION_ENVIRONMENT_EDEFAULT == null ? executionEnvironment != null : !EXECUTION_ENVIRONMENT_EDEFAULT.equals(executionEnvironment);
			case SourcecleanerPackage.MANIFEST__DIAGRAPH:
				return diagraph != DIAGRAPH_EDEFAULT;
			case SourcecleanerPackage.MANIFEST__CLASSPATHES:
				return classpathes != null && !classpathes.isEmpty();
			case SourcecleanerPackage.MANIFEST__EXPORTS:
				return exports != null && !exports.isEmpty();
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
		result.append(" (symbolicName: ");
		result.append(symbolicName);
		result.append(", singleton: ");
		result.append(singleton);
		result.append(", vendor: ");
		result.append(vendor);
		result.append(", version: ");
		result.append(version);
		result.append(", versionId: ");
		result.append(versionId);
		result.append(", versionQualifier: ");
		result.append(versionQualifier);
		result.append(", lazy: ");
		result.append(lazy);
		result.append(", executionEnvironment: ");
		result.append(executionEnvironment);
		result.append(", diagraph: ");
		result.append(diagraph);
		result.append(')');
		return result.toString();
	}

} //ManifestImpl
