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
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import org.isoe.mbse.sourcecleaner.Extension;
import org.isoe.mbse.sourcecleaner.ExtensionPoint;
import org.isoe.mbse.sourcecleaner.Plugin;
import org.isoe.mbse.sourcecleaner.Project;
import org.isoe.mbse.sourcecleaner.SourcecleanerPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Plugin</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.PluginImpl#getExtensions <em>Extensions</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.PluginImpl#getExtensionPoints <em>Extension Points</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.PluginImpl#getProject <em>Project</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.PluginImpl#getExtra <em>Extra</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PluginImpl extends SourceImpl implements Plugin {
	/**
	 * The cached value of the '{@link #getExtensions() <em>Extensions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtensions()
	 * @generated
	 * @ordered
	 */
	protected EList<Extension> extensions;

	/**
	 * The cached value of the '{@link #getExtensionPoints() <em>Extension Points</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtensionPoints()
	 * @generated
	 * @ordered
	 */
	protected EList<ExtensionPoint> extensionPoints;

	/**
	 * The default value of the '{@link #getExtra() <em>Extra</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtra()
	 * @generated
	 * @ordered
	 */
	protected static final String EXTRA_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getExtra() <em>Extra</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtra()
	 * @generated
	 * @ordered
	 */
	protected String extra = EXTRA_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PluginImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SourcecleanerPackage.Literals.PLUGIN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Extension> getExtensions() {
		if (extensions == null) {
			extensions = new EObjectContainmentEList<Extension>(Extension.class, this, SourcecleanerPackage.PLUGIN__EXTENSIONS);
		}
		return extensions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ExtensionPoint> getExtensionPoints() {
		if (extensionPoints == null) {
			extensionPoints = new EObjectContainmentWithInverseEList<ExtensionPoint>(ExtensionPoint.class, this, SourcecleanerPackage.PLUGIN__EXTENSION_POINTS, SourcecleanerPackage.EXTENSION_POINT__PLUGIN);
		}
		return extensionPoints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Project getProject() {
		if (eContainerFeatureID() != SourcecleanerPackage.PLUGIN__PROJECT) return null;
		return (Project)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProject(Project newProject, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newProject, SourcecleanerPackage.PLUGIN__PROJECT, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProject(Project newProject) {
		if (newProject != eInternalContainer() || (eContainerFeatureID() != SourcecleanerPackage.PLUGIN__PROJECT && newProject != null)) {
			if (EcoreUtil.isAncestor(this, newProject))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newProject != null)
				msgs = ((InternalEObject)newProject).eInverseAdd(this, SourcecleanerPackage.PROJECT__PLUGIN, Project.class, msgs);
			msgs = basicSetProject(newProject, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.PLUGIN__PROJECT, newProject, newProject));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getExtra() {
		return extra;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExtra(String newExtra) {
		String oldExtra = extra;
		extra = newExtra;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.PLUGIN__EXTRA, oldExtra, extra));
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
			case SourcecleanerPackage.PLUGIN__EXTENSION_POINTS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getExtensionPoints()).basicAdd(otherEnd, msgs);
			case SourcecleanerPackage.PLUGIN__PROJECT:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetProject((Project)otherEnd, msgs);
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
			case SourcecleanerPackage.PLUGIN__EXTENSIONS:
				return ((InternalEList<?>)getExtensions()).basicRemove(otherEnd, msgs);
			case SourcecleanerPackage.PLUGIN__EXTENSION_POINTS:
				return ((InternalEList<?>)getExtensionPoints()).basicRemove(otherEnd, msgs);
			case SourcecleanerPackage.PLUGIN__PROJECT:
				return basicSetProject(null, msgs);
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
			case SourcecleanerPackage.PLUGIN__PROJECT:
				return eInternalContainer().eInverseRemove(this, SourcecleanerPackage.PROJECT__PLUGIN, Project.class, msgs);
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
			case SourcecleanerPackage.PLUGIN__EXTENSIONS:
				return getExtensions();
			case SourcecleanerPackage.PLUGIN__EXTENSION_POINTS:
				return getExtensionPoints();
			case SourcecleanerPackage.PLUGIN__PROJECT:
				return getProject();
			case SourcecleanerPackage.PLUGIN__EXTRA:
				return getExtra();
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
			case SourcecleanerPackage.PLUGIN__EXTENSIONS:
				getExtensions().clear();
				getExtensions().addAll((Collection<? extends Extension>)newValue);
				return;
			case SourcecleanerPackage.PLUGIN__EXTENSION_POINTS:
				getExtensionPoints().clear();
				getExtensionPoints().addAll((Collection<? extends ExtensionPoint>)newValue);
				return;
			case SourcecleanerPackage.PLUGIN__PROJECT:
				setProject((Project)newValue);
				return;
			case SourcecleanerPackage.PLUGIN__EXTRA:
				setExtra((String)newValue);
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
			case SourcecleanerPackage.PLUGIN__EXTENSIONS:
				getExtensions().clear();
				return;
			case SourcecleanerPackage.PLUGIN__EXTENSION_POINTS:
				getExtensionPoints().clear();
				return;
			case SourcecleanerPackage.PLUGIN__PROJECT:
				setProject((Project)null);
				return;
			case SourcecleanerPackage.PLUGIN__EXTRA:
				setExtra(EXTRA_EDEFAULT);
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
			case SourcecleanerPackage.PLUGIN__EXTENSIONS:
				return extensions != null && !extensions.isEmpty();
			case SourcecleanerPackage.PLUGIN__EXTENSION_POINTS:
				return extensionPoints != null && !extensionPoints.isEmpty();
			case SourcecleanerPackage.PLUGIN__PROJECT:
				return getProject() != null;
			case SourcecleanerPackage.PLUGIN__EXTRA:
				return EXTRA_EDEFAULT == null ? extra != null : !EXTRA_EDEFAULT.equals(extra);
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
		result.append(" (extra: ");
		result.append(extra);
		result.append(')');
		return result.toString();
	}

} //PluginImpl
