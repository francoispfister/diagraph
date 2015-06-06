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

import org.isoe.mbse.sourcecleaner.Build;
import org.isoe.mbse.sourcecleaner.Java;
import org.isoe.mbse.sourcecleaner.Manifest;
import org.isoe.mbse.sourcecleaner.Plugin;
import org.isoe.mbse.sourcecleaner.Project;
import org.isoe.mbse.sourcecleaner.Schema;
import org.isoe.mbse.sourcecleaner.SourcecleanerPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Project</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.ProjectImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.ProjectImpl#getSources <em>Sources</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.ProjectImpl#getManifest <em>Manifest</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.ProjectImpl#getBuild <em>Build</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.ProjectImpl#getPlugin <em>Plugin</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.ProjectImpl#getSchema <em>Schema</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.ProjectImpl#getWorkspace <em>Workspace</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProjectImpl extends LocatedElementImpl implements Project {
	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final int ID_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected int id = ID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSources() <em>Sources</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSources()
	 * @generated
	 * @ordered
	 */
	protected EList<Java> sources;

	/**
	 * The cached value of the '{@link #getManifest() <em>Manifest</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getManifest()
	 * @generated
	 * @ordered
	 */
	protected Manifest manifest;

	/**
	 * The cached value of the '{@link #getBuild() <em>Build</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBuild()
	 * @generated
	 * @ordered
	 */
	protected Build build;

	/**
	 * The cached value of the '{@link #getPlugin() <em>Plugin</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPlugin()
	 * @generated
	 * @ordered
	 */
	protected Plugin plugin;

	/**
	 * The cached value of the '{@link #getSchema() <em>Schema</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSchema()
	 * @generated
	 * @ordered
	 */
	protected Schema schema;

	/**
	 * The default value of the '{@link #getWorkspace() <em>Workspace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWorkspace()
	 * @generated
	 * @ordered
	 */
	protected static final String WORKSPACE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getWorkspace() <em>Workspace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWorkspace()
	 * @generated
	 * @ordered
	 */
	protected String workspace = WORKSPACE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProjectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SourcecleanerPackage.Literals.PROJECT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(int newId) {
		int oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.PROJECT__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Java> getSources() {
		if (sources == null) {
			sources = new EObjectContainmentWithInverseEList<Java>(Java.class, this, SourcecleanerPackage.PROJECT__SOURCES, SourcecleanerPackage.JAVA__PROJECT);
		}
		return sources;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Manifest getManifest() {
		return manifest;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetManifest(Manifest newManifest, NotificationChain msgs) {
		Manifest oldManifest = manifest;
		manifest = newManifest;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.PROJECT__MANIFEST, oldManifest, newManifest);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setManifest(Manifest newManifest) {
		if (newManifest != manifest) {
			NotificationChain msgs = null;
			if (manifest != null)
				msgs = ((InternalEObject)manifest).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SourcecleanerPackage.PROJECT__MANIFEST, null, msgs);
			if (newManifest != null)
				msgs = ((InternalEObject)newManifest).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SourcecleanerPackage.PROJECT__MANIFEST, null, msgs);
			msgs = basicSetManifest(newManifest, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.PROJECT__MANIFEST, newManifest, newManifest));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Build getBuild() {
		return build;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBuild(Build newBuild, NotificationChain msgs) {
		Build oldBuild = build;
		build = newBuild;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.PROJECT__BUILD, oldBuild, newBuild);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBuild(Build newBuild) {
		if (newBuild != build) {
			NotificationChain msgs = null;
			if (build != null)
				msgs = ((InternalEObject)build).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SourcecleanerPackage.PROJECT__BUILD, null, msgs);
			if (newBuild != null)
				msgs = ((InternalEObject)newBuild).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SourcecleanerPackage.PROJECT__BUILD, null, msgs);
			msgs = basicSetBuild(newBuild, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.PROJECT__BUILD, newBuild, newBuild));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Plugin getPlugin() {
		return plugin;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPlugin(Plugin newPlugin, NotificationChain msgs) {
		Plugin oldPlugin = plugin;
		plugin = newPlugin;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.PROJECT__PLUGIN, oldPlugin, newPlugin);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPlugin(Plugin newPlugin) {
		if (newPlugin != plugin) {
			NotificationChain msgs = null;
			if (plugin != null)
				msgs = ((InternalEObject)plugin).eInverseRemove(this, SourcecleanerPackage.PLUGIN__PROJECT, Plugin.class, msgs);
			if (newPlugin != null)
				msgs = ((InternalEObject)newPlugin).eInverseAdd(this, SourcecleanerPackage.PLUGIN__PROJECT, Plugin.class, msgs);
			msgs = basicSetPlugin(newPlugin, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.PROJECT__PLUGIN, newPlugin, newPlugin));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Schema getSchema() {
		return schema;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSchema(Schema newSchema, NotificationChain msgs) {
		Schema oldSchema = schema;
		schema = newSchema;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.PROJECT__SCHEMA, oldSchema, newSchema);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSchema(Schema newSchema) {
		if (newSchema != schema) {
			NotificationChain msgs = null;
			if (schema != null)
				msgs = ((InternalEObject)schema).eInverseRemove(this, SourcecleanerPackage.SCHEMA__PROJECT, Schema.class, msgs);
			if (newSchema != null)
				msgs = ((InternalEObject)newSchema).eInverseAdd(this, SourcecleanerPackage.SCHEMA__PROJECT, Schema.class, msgs);
			msgs = basicSetSchema(newSchema, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.PROJECT__SCHEMA, newSchema, newSchema));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getWorkspace() {
		return workspace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWorkspace(String newWorkspace) {
		String oldWorkspace = workspace;
		workspace = newWorkspace;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.PROJECT__WORKSPACE, oldWorkspace, workspace));
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
			case SourcecleanerPackage.PROJECT__SOURCES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getSources()).basicAdd(otherEnd, msgs);
			case SourcecleanerPackage.PROJECT__PLUGIN:
				if (plugin != null)
					msgs = ((InternalEObject)plugin).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SourcecleanerPackage.PROJECT__PLUGIN, null, msgs);
				return basicSetPlugin((Plugin)otherEnd, msgs);
			case SourcecleanerPackage.PROJECT__SCHEMA:
				if (schema != null)
					msgs = ((InternalEObject)schema).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SourcecleanerPackage.PROJECT__SCHEMA, null, msgs);
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
			case SourcecleanerPackage.PROJECT__SOURCES:
				return ((InternalEList<?>)getSources()).basicRemove(otherEnd, msgs);
			case SourcecleanerPackage.PROJECT__MANIFEST:
				return basicSetManifest(null, msgs);
			case SourcecleanerPackage.PROJECT__BUILD:
				return basicSetBuild(null, msgs);
			case SourcecleanerPackage.PROJECT__PLUGIN:
				return basicSetPlugin(null, msgs);
			case SourcecleanerPackage.PROJECT__SCHEMA:
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
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SourcecleanerPackage.PROJECT__ID:
				return getId();
			case SourcecleanerPackage.PROJECT__SOURCES:
				return getSources();
			case SourcecleanerPackage.PROJECT__MANIFEST:
				return getManifest();
			case SourcecleanerPackage.PROJECT__BUILD:
				return getBuild();
			case SourcecleanerPackage.PROJECT__PLUGIN:
				return getPlugin();
			case SourcecleanerPackage.PROJECT__SCHEMA:
				return getSchema();
			case SourcecleanerPackage.PROJECT__WORKSPACE:
				return getWorkspace();
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
			case SourcecleanerPackage.PROJECT__ID:
				setId((Integer)newValue);
				return;
			case SourcecleanerPackage.PROJECT__SOURCES:
				getSources().clear();
				getSources().addAll((Collection<? extends Java>)newValue);
				return;
			case SourcecleanerPackage.PROJECT__MANIFEST:
				setManifest((Manifest)newValue);
				return;
			case SourcecleanerPackage.PROJECT__BUILD:
				setBuild((Build)newValue);
				return;
			case SourcecleanerPackage.PROJECT__PLUGIN:
				setPlugin((Plugin)newValue);
				return;
			case SourcecleanerPackage.PROJECT__SCHEMA:
				setSchema((Schema)newValue);
				return;
			case SourcecleanerPackage.PROJECT__WORKSPACE:
				setWorkspace((String)newValue);
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
			case SourcecleanerPackage.PROJECT__ID:
				setId(ID_EDEFAULT);
				return;
			case SourcecleanerPackage.PROJECT__SOURCES:
				getSources().clear();
				return;
			case SourcecleanerPackage.PROJECT__MANIFEST:
				setManifest((Manifest)null);
				return;
			case SourcecleanerPackage.PROJECT__BUILD:
				setBuild((Build)null);
				return;
			case SourcecleanerPackage.PROJECT__PLUGIN:
				setPlugin((Plugin)null);
				return;
			case SourcecleanerPackage.PROJECT__SCHEMA:
				setSchema((Schema)null);
				return;
			case SourcecleanerPackage.PROJECT__WORKSPACE:
				setWorkspace(WORKSPACE_EDEFAULT);
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
			case SourcecleanerPackage.PROJECT__ID:
				return id != ID_EDEFAULT;
			case SourcecleanerPackage.PROJECT__SOURCES:
				return sources != null && !sources.isEmpty();
			case SourcecleanerPackage.PROJECT__MANIFEST:
				return manifest != null;
			case SourcecleanerPackage.PROJECT__BUILD:
				return build != null;
			case SourcecleanerPackage.PROJECT__PLUGIN:
				return plugin != null;
			case SourcecleanerPackage.PROJECT__SCHEMA:
				return schema != null;
			case SourcecleanerPackage.PROJECT__WORKSPACE:
				return WORKSPACE_EDEFAULT == null ? workspace != null : !WORKSPACE_EDEFAULT.equals(workspace);
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
		result.append(", workspace: ");
		result.append(workspace);
		result.append(')');
		return result.toString();
	}

} //ProjectImpl
