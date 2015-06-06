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
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import org.isoe.mbse.sourcecleaner.ExtensionReference;
import org.isoe.mbse.sourcecleaner.Project;
import org.isoe.mbse.sourcecleaner.Schema;
import org.isoe.mbse.sourcecleaner.SourcecleanerPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Schema</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.SchemaImpl#getReferences <em>References</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.SchemaImpl#getProject <em>Project</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.SchemaImpl#getExtensionName <em>Extension Name</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.SchemaImpl#getExtensionId <em>Extension Id</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.SchemaImpl#getPluginName <em>Plugin Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SchemaImpl extends SourceImpl implements Schema {
	/**
	 * The cached value of the '{@link #getReferences() <em>References</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReferences()
	 * @generated
	 * @ordered
	 */
	protected EList<ExtensionReference> references;

	/**
	 * The default value of the '{@link #getExtensionName() <em>Extension Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtensionName()
	 * @generated
	 * @ordered
	 */
	protected static final String EXTENSION_NAME_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getExtensionName() <em>Extension Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtensionName()
	 * @generated
	 * @ordered
	 */
	protected String extensionName = EXTENSION_NAME_EDEFAULT;
	/**
	 * The default value of the '{@link #getExtensionId() <em>Extension Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtensionId()
	 * @generated
	 * @ordered
	 */
	protected static final String EXTENSION_ID_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getExtensionId() <em>Extension Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtensionId()
	 * @generated
	 * @ordered
	 */
	protected String extensionId = EXTENSION_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getPluginName() <em>Plugin Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPluginName()
	 * @generated
	 * @ordered
	 */
	protected static final String PLUGIN_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPluginName() <em>Plugin Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPluginName()
	 * @generated
	 * @ordered
	 */
	protected String pluginName = PLUGIN_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SchemaImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SourcecleanerPackage.Literals.SCHEMA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ExtensionReference> getReferences() {
		if (references == null) {
			references = new EObjectContainmentWithInverseEList<ExtensionReference>(ExtensionReference.class, this, SourcecleanerPackage.SCHEMA__REFERENCES, SourcecleanerPackage.EXTENSION_REFERENCE__SCHEMA);
		}
		return references;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Project getProject() {
		if (eContainerFeatureID() != SourcecleanerPackage.SCHEMA__PROJECT) return null;
		return (Project)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProject(Project newProject, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newProject, SourcecleanerPackage.SCHEMA__PROJECT, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProject(Project newProject) {
		if (newProject != eInternalContainer() || (eContainerFeatureID() != SourcecleanerPackage.SCHEMA__PROJECT && newProject != null)) {
			if (EcoreUtil.isAncestor(this, newProject))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newProject != null)
				msgs = ((InternalEObject)newProject).eInverseAdd(this, SourcecleanerPackage.PROJECT__SCHEMA, Project.class, msgs);
			msgs = basicSetProject(newProject, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.SCHEMA__PROJECT, newProject, newProject));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getExtensionName() {
		return extensionName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExtensionName(String newExtensionName) {
		String oldExtensionName = extensionName;
		extensionName = newExtensionName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.SCHEMA__EXTENSION_NAME, oldExtensionName, extensionName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getExtensionId() {
		return extensionId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExtensionId(String newExtensionId) {
		String oldExtensionId = extensionId;
		extensionId = newExtensionId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.SCHEMA__EXTENSION_ID, oldExtensionId, extensionId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPluginName() {
		return pluginName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPluginName(String newPluginName) {
		String oldPluginName = pluginName;
		pluginName = newPluginName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.SCHEMA__PLUGIN_NAME, oldPluginName, pluginName));
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
			case SourcecleanerPackage.SCHEMA__REFERENCES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getReferences()).basicAdd(otherEnd, msgs);
			case SourcecleanerPackage.SCHEMA__PROJECT:
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
			case SourcecleanerPackage.SCHEMA__REFERENCES:
				return ((InternalEList<?>)getReferences()).basicRemove(otherEnd, msgs);
			case SourcecleanerPackage.SCHEMA__PROJECT:
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
			case SourcecleanerPackage.SCHEMA__PROJECT:
				return eInternalContainer().eInverseRemove(this, SourcecleanerPackage.PROJECT__SCHEMA, Project.class, msgs);
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
			case SourcecleanerPackage.SCHEMA__REFERENCES:
				return getReferences();
			case SourcecleanerPackage.SCHEMA__PROJECT:
				return getProject();
			case SourcecleanerPackage.SCHEMA__EXTENSION_NAME:
				return getExtensionName();
			case SourcecleanerPackage.SCHEMA__EXTENSION_ID:
				return getExtensionId();
			case SourcecleanerPackage.SCHEMA__PLUGIN_NAME:
				return getPluginName();
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
			case SourcecleanerPackage.SCHEMA__REFERENCES:
				getReferences().clear();
				getReferences().addAll((Collection<? extends ExtensionReference>)newValue);
				return;
			case SourcecleanerPackage.SCHEMA__PROJECT:
				setProject((Project)newValue);
				return;
			case SourcecleanerPackage.SCHEMA__EXTENSION_NAME:
				setExtensionName((String)newValue);
				return;
			case SourcecleanerPackage.SCHEMA__EXTENSION_ID:
				setExtensionId((String)newValue);
				return;
			case SourcecleanerPackage.SCHEMA__PLUGIN_NAME:
				setPluginName((String)newValue);
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
			case SourcecleanerPackage.SCHEMA__REFERENCES:
				getReferences().clear();
				return;
			case SourcecleanerPackage.SCHEMA__PROJECT:
				setProject((Project)null);
				return;
			case SourcecleanerPackage.SCHEMA__EXTENSION_NAME:
				setExtensionName(EXTENSION_NAME_EDEFAULT);
				return;
			case SourcecleanerPackage.SCHEMA__EXTENSION_ID:
				setExtensionId(EXTENSION_ID_EDEFAULT);
				return;
			case SourcecleanerPackage.SCHEMA__PLUGIN_NAME:
				setPluginName(PLUGIN_NAME_EDEFAULT);
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
			case SourcecleanerPackage.SCHEMA__REFERENCES:
				return references != null && !references.isEmpty();
			case SourcecleanerPackage.SCHEMA__PROJECT:
				return getProject() != null;
			case SourcecleanerPackage.SCHEMA__EXTENSION_NAME:
				return EXTENSION_NAME_EDEFAULT == null ? extensionName != null : !EXTENSION_NAME_EDEFAULT.equals(extensionName);
			case SourcecleanerPackage.SCHEMA__EXTENSION_ID:
				return EXTENSION_ID_EDEFAULT == null ? extensionId != null : !EXTENSION_ID_EDEFAULT.equals(extensionId);
			case SourcecleanerPackage.SCHEMA__PLUGIN_NAME:
				return PLUGIN_NAME_EDEFAULT == null ? pluginName != null : !PLUGIN_NAME_EDEFAULT.equals(pluginName);
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
		result.append(" (extensionName: ");
		result.append(extensionName);
		result.append(", extensionId: ");
		result.append(extensionId);
		result.append(", pluginName: ");
		result.append(pluginName);
		result.append(')');
		return result.toString();
	}

} //SchemaImpl
