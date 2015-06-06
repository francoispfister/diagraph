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

import org.isoe.mbse.sourcecleaner.Dependency;
import org.isoe.mbse.sourcecleaner.Manifest;
import org.isoe.mbse.sourcecleaner.SourcecleanerPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Dependency</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.DependencyImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.DependencyImpl#getVersion <em>Version</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.DependencyImpl#getDependency <em>Dependency</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.DependencyImpl#getRequerant <em>Requerant</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.DependencyImpl#isReexport <em>Reexport</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.DependencyImpl#isDiagraph <em>Diagraph</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DependencyImpl extends EObjectImpl implements Dependency {
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
	 * The cached value of the '{@link #getDependency() <em>Dependency</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDependency()
	 * @generated
	 * @ordered
	 */
	protected Manifest dependency;

	/**
	 * The default value of the '{@link #isReexport() <em>Reexport</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReexport()
	 * @generated
	 * @ordered
	 */
	protected static final boolean REEXPORT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isReexport() <em>Reexport</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReexport()
	 * @generated
	 * @ordered
	 */
	protected boolean reexport = REEXPORT_EDEFAULT;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DependencyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SourcecleanerPackage.Literals.DEPENDENCY;
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
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.DEPENDENCY__NAME, oldName, name));
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
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.DEPENDENCY__VERSION, oldVersion, version));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Manifest getDependency() {
		if (dependency != null && dependency.eIsProxy()) {
			InternalEObject oldDependency = (InternalEObject)dependency;
			dependency = (Manifest)eResolveProxy(oldDependency);
			if (dependency != oldDependency) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SourcecleanerPackage.DEPENDENCY__DEPENDENCY, oldDependency, dependency));
			}
		}
		return dependency;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Manifest basicGetDependency() {
		return dependency;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDependency(Manifest newDependency) {
		Manifest oldDependency = dependency;
		dependency = newDependency;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.DEPENDENCY__DEPENDENCY, oldDependency, dependency));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Manifest getRequerant() {
		if (eContainerFeatureID() != SourcecleanerPackage.DEPENDENCY__REQUERANT) return null;
		return (Manifest)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRequerant(Manifest newRequerant, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newRequerant, SourcecleanerPackage.DEPENDENCY__REQUERANT, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRequerant(Manifest newRequerant) {
		if (newRequerant != eInternalContainer() || (eContainerFeatureID() != SourcecleanerPackage.DEPENDENCY__REQUERANT && newRequerant != null)) {
			if (EcoreUtil.isAncestor(this, newRequerant))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newRequerant != null)
				msgs = ((InternalEObject)newRequerant).eInverseAdd(this, SourcecleanerPackage.MANIFEST__DEPENDENCIES, Manifest.class, msgs);
			msgs = basicSetRequerant(newRequerant, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.DEPENDENCY__REQUERANT, newRequerant, newRequerant));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isReexport() {
		return reexport;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReexport(boolean newReexport) {
		boolean oldReexport = reexport;
		reexport = newReexport;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.DEPENDENCY__REEXPORT, oldReexport, reexport));
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
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.DEPENDENCY__DIAGRAPH, oldDiagraph, diagraph));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SourcecleanerPackage.DEPENDENCY__REQUERANT:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetRequerant((Manifest)otherEnd, msgs);
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
			case SourcecleanerPackage.DEPENDENCY__REQUERANT:
				return basicSetRequerant(null, msgs);
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
			case SourcecleanerPackage.DEPENDENCY__REQUERANT:
				return eInternalContainer().eInverseRemove(this, SourcecleanerPackage.MANIFEST__DEPENDENCIES, Manifest.class, msgs);
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
			case SourcecleanerPackage.DEPENDENCY__NAME:
				return getName();
			case SourcecleanerPackage.DEPENDENCY__VERSION:
				return getVersion();
			case SourcecleanerPackage.DEPENDENCY__DEPENDENCY:
				if (resolve) return getDependency();
				return basicGetDependency();
			case SourcecleanerPackage.DEPENDENCY__REQUERANT:
				return getRequerant();
			case SourcecleanerPackage.DEPENDENCY__REEXPORT:
				return isReexport();
			case SourcecleanerPackage.DEPENDENCY__DIAGRAPH:
				return isDiagraph();
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
			case SourcecleanerPackage.DEPENDENCY__NAME:
				setName((String)newValue);
				return;
			case SourcecleanerPackage.DEPENDENCY__VERSION:
				setVersion((String)newValue);
				return;
			case SourcecleanerPackage.DEPENDENCY__DEPENDENCY:
				setDependency((Manifest)newValue);
				return;
			case SourcecleanerPackage.DEPENDENCY__REQUERANT:
				setRequerant((Manifest)newValue);
				return;
			case SourcecleanerPackage.DEPENDENCY__REEXPORT:
				setReexport((Boolean)newValue);
				return;
			case SourcecleanerPackage.DEPENDENCY__DIAGRAPH:
				setDiagraph((Boolean)newValue);
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
			case SourcecleanerPackage.DEPENDENCY__NAME:
				setName(NAME_EDEFAULT);
				return;
			case SourcecleanerPackage.DEPENDENCY__VERSION:
				setVersion(VERSION_EDEFAULT);
				return;
			case SourcecleanerPackage.DEPENDENCY__DEPENDENCY:
				setDependency((Manifest)null);
				return;
			case SourcecleanerPackage.DEPENDENCY__REQUERANT:
				setRequerant((Manifest)null);
				return;
			case SourcecleanerPackage.DEPENDENCY__REEXPORT:
				setReexport(REEXPORT_EDEFAULT);
				return;
			case SourcecleanerPackage.DEPENDENCY__DIAGRAPH:
				setDiagraph(DIAGRAPH_EDEFAULT);
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
			case SourcecleanerPackage.DEPENDENCY__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case SourcecleanerPackage.DEPENDENCY__VERSION:
				return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
			case SourcecleanerPackage.DEPENDENCY__DEPENDENCY:
				return dependency != null;
			case SourcecleanerPackage.DEPENDENCY__REQUERANT:
				return getRequerant() != null;
			case SourcecleanerPackage.DEPENDENCY__REEXPORT:
				return reexport != REEXPORT_EDEFAULT;
			case SourcecleanerPackage.DEPENDENCY__DIAGRAPH:
				return diagraph != DIAGRAPH_EDEFAULT;
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
		result.append(", version: ");
		result.append(version);
		result.append(", reexport: ");
		result.append(reexport);
		result.append(", diagraph: ");
		result.append(diagraph);
		result.append(')');
		return result.toString();
	}

} //DependencyImpl
