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

import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import org.isoe.mbse.sourcecleaner.Extension;
import org.isoe.mbse.sourcecleaner.ExtensionPoint;
import org.isoe.mbse.sourcecleaner.Plugin;
import org.isoe.mbse.sourcecleaner.SourcecleanerPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Extension Point</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.ExtensionPointImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.ExtensionPointImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.ExtensionPointImpl#getSchema <em>Schema</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.ExtensionPointImpl#isDiagraph <em>Diagraph</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.ExtensionPointImpl#getExtensions <em>Extensions</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.ExtensionPointImpl#getPlugin <em>Plugin</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ExtensionPointImpl extends EObjectImpl implements ExtensionPoint {
	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

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
	 * The default value of the '{@link #getSchema() <em>Schema</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSchema()
	 * @generated
	 * @ordered
	 */
	protected static final String SCHEMA_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSchema() <em>Schema</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSchema()
	 * @generated
	 * @ordered
	 */
	protected String schema = SCHEMA_EDEFAULT;

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
	 * The cached value of the '{@link #getExtensions() <em>Extensions</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtensions()
	 * @generated
	 * @ordered
	 */
	protected EList<Extension> extensions;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExtensionPointImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SourcecleanerPackage.Literals.EXTENSION_POINT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.EXTENSION_POINT__ID, oldId, id));
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
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.EXTENSION_POINT__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSchema() {
		return schema;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSchema(String newSchema) {
		String oldSchema = schema;
		schema = newSchema;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.EXTENSION_POINT__SCHEMA, oldSchema, schema));
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
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.EXTENSION_POINT__DIAGRAPH, oldDiagraph, diagraph));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Extension> getExtensions() {
		if (extensions == null) {
			extensions = new EObjectWithInverseResolvingEList<Extension>(Extension.class, this, SourcecleanerPackage.EXTENSION_POINT__EXTENSIONS, SourcecleanerPackage.EXTENSION__EXTENSION_POINT);
		}
		return extensions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Plugin getPlugin() {
		if (eContainerFeatureID() != SourcecleanerPackage.EXTENSION_POINT__PLUGIN) return null;
		return (Plugin)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPlugin(Plugin newPlugin, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newPlugin, SourcecleanerPackage.EXTENSION_POINT__PLUGIN, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPlugin(Plugin newPlugin) {
		if (newPlugin != eInternalContainer() || (eContainerFeatureID() != SourcecleanerPackage.EXTENSION_POINT__PLUGIN && newPlugin != null)) {
			if (EcoreUtil.isAncestor(this, newPlugin))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newPlugin != null)
				msgs = ((InternalEObject)newPlugin).eInverseAdd(this, SourcecleanerPackage.PLUGIN__EXTENSION_POINTS, Plugin.class, msgs);
			msgs = basicSetPlugin(newPlugin, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.EXTENSION_POINT__PLUGIN, newPlugin, newPlugin));
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
			case SourcecleanerPackage.EXTENSION_POINT__EXTENSIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getExtensions()).basicAdd(otherEnd, msgs);
			case SourcecleanerPackage.EXTENSION_POINT__PLUGIN:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetPlugin((Plugin)otherEnd, msgs);
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
			case SourcecleanerPackage.EXTENSION_POINT__EXTENSIONS:
				return ((InternalEList<?>)getExtensions()).basicRemove(otherEnd, msgs);
			case SourcecleanerPackage.EXTENSION_POINT__PLUGIN:
				return basicSetPlugin(null, msgs);
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
			case SourcecleanerPackage.EXTENSION_POINT__PLUGIN:
				return eInternalContainer().eInverseRemove(this, SourcecleanerPackage.PLUGIN__EXTENSION_POINTS, Plugin.class, msgs);
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
			case SourcecleanerPackage.EXTENSION_POINT__ID:
				return getId();
			case SourcecleanerPackage.EXTENSION_POINT__NAME:
				return getName();
			case SourcecleanerPackage.EXTENSION_POINT__SCHEMA:
				return getSchema();
			case SourcecleanerPackage.EXTENSION_POINT__DIAGRAPH:
				return isDiagraph();
			case SourcecleanerPackage.EXTENSION_POINT__EXTENSIONS:
				return getExtensions();
			case SourcecleanerPackage.EXTENSION_POINT__PLUGIN:
				return getPlugin();
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
			case SourcecleanerPackage.EXTENSION_POINT__ID:
				setId((String)newValue);
				return;
			case SourcecleanerPackage.EXTENSION_POINT__NAME:
				setName((String)newValue);
				return;
			case SourcecleanerPackage.EXTENSION_POINT__SCHEMA:
				setSchema((String)newValue);
				return;
			case SourcecleanerPackage.EXTENSION_POINT__DIAGRAPH:
				setDiagraph((Boolean)newValue);
				return;
			case SourcecleanerPackage.EXTENSION_POINT__EXTENSIONS:
				getExtensions().clear();
				getExtensions().addAll((Collection<? extends Extension>)newValue);
				return;
			case SourcecleanerPackage.EXTENSION_POINT__PLUGIN:
				setPlugin((Plugin)newValue);
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
			case SourcecleanerPackage.EXTENSION_POINT__ID:
				setId(ID_EDEFAULT);
				return;
			case SourcecleanerPackage.EXTENSION_POINT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case SourcecleanerPackage.EXTENSION_POINT__SCHEMA:
				setSchema(SCHEMA_EDEFAULT);
				return;
			case SourcecleanerPackage.EXTENSION_POINT__DIAGRAPH:
				setDiagraph(DIAGRAPH_EDEFAULT);
				return;
			case SourcecleanerPackage.EXTENSION_POINT__EXTENSIONS:
				getExtensions().clear();
				return;
			case SourcecleanerPackage.EXTENSION_POINT__PLUGIN:
				setPlugin((Plugin)null);
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
			case SourcecleanerPackage.EXTENSION_POINT__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case SourcecleanerPackage.EXTENSION_POINT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case SourcecleanerPackage.EXTENSION_POINT__SCHEMA:
				return SCHEMA_EDEFAULT == null ? schema != null : !SCHEMA_EDEFAULT.equals(schema);
			case SourcecleanerPackage.EXTENSION_POINT__DIAGRAPH:
				return diagraph != DIAGRAPH_EDEFAULT;
			case SourcecleanerPackage.EXTENSION_POINT__EXTENSIONS:
				return extensions != null && !extensions.isEmpty();
			case SourcecleanerPackage.EXTENSION_POINT__PLUGIN:
				return getPlugin() != null;
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
		result.append(" (id: ");
		result.append(id);
		result.append(", name: ");
		result.append(name);
		result.append(", schema: ");
		result.append(schema);
		result.append(", diagraph: ");
		result.append(diagraph);
		result.append(')');
		return result.toString();
	}

} //ExtensionPointImpl
