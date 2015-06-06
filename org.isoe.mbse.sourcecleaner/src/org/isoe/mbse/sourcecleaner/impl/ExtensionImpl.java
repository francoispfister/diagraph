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

import org.isoe.mbse.sourcecleaner.Extension;
import org.isoe.mbse.sourcecleaner.ExtensionAttribute;
import org.isoe.mbse.sourcecleaner.ExtensionPoint;
import org.isoe.mbse.sourcecleaner.Java;
import org.isoe.mbse.sourcecleaner.SourcecleanerPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Extension</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.ExtensionImpl#getExtensionPoint <em>Extension Point</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.ExtensionImpl#getPointId <em>Point Id</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.ExtensionImpl#getClazz <em>Clazz</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.ExtensionImpl#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.ExtensionImpl#getImplements <em>Implements</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.ExtensionImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.ExtensionImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.ExtensionImpl#getExtra <em>Extra</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.ExtensionImpl#isDiagraph <em>Diagraph</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ExtensionImpl extends EObjectImpl implements Extension {
	/**
	 * The cached value of the '{@link #getExtensionPoint() <em>Extension Point</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtensionPoint()
	 * @generated
	 * @ordered
	 */
	protected ExtensionPoint extensionPoint;

	/**
	 * The default value of the '{@link #getPointId() <em>Point Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPointId()
	 * @generated
	 * @ordered
	 */
	protected static final String POINT_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPointId() <em>Point Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPointId()
	 * @generated
	 * @ordered
	 */
	protected String pointId = POINT_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getClazz() <em>Clazz</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClazz()
	 * @generated
	 * @ordered
	 */
	protected static final String CLAZZ_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getClazz() <em>Clazz</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClazz()
	 * @generated
	 * @ordered
	 */
	protected String clazz = CLAZZ_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAttributes() <em>Attributes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributes()
	 * @generated
	 * @ordered
	 */
	protected EList<ExtensionAttribute> attributes;

	/**
	 * The cached value of the '{@link #getImplements() <em>Implements</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplements()
	 * @generated
	 * @ordered
	 */
	protected Java implements_;

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
	protected ExtensionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SourcecleanerPackage.Literals.EXTENSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExtensionPoint getExtensionPoint() {
		if (extensionPoint != null && extensionPoint.eIsProxy()) {
			InternalEObject oldExtensionPoint = (InternalEObject)extensionPoint;
			extensionPoint = (ExtensionPoint)eResolveProxy(oldExtensionPoint);
			if (extensionPoint != oldExtensionPoint) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SourcecleanerPackage.EXTENSION__EXTENSION_POINT, oldExtensionPoint, extensionPoint));
			}
		}
		return extensionPoint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExtensionPoint basicGetExtensionPoint() {
		return extensionPoint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExtensionPoint(ExtensionPoint newExtensionPoint, NotificationChain msgs) {
		ExtensionPoint oldExtensionPoint = extensionPoint;
		extensionPoint = newExtensionPoint;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.EXTENSION__EXTENSION_POINT, oldExtensionPoint, newExtensionPoint);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExtensionPoint(ExtensionPoint newExtensionPoint) {
		if (newExtensionPoint != extensionPoint) {
			NotificationChain msgs = null;
			if (extensionPoint != null)
				msgs = ((InternalEObject)extensionPoint).eInverseRemove(this, SourcecleanerPackage.EXTENSION_POINT__EXTENSIONS, ExtensionPoint.class, msgs);
			if (newExtensionPoint != null)
				msgs = ((InternalEObject)newExtensionPoint).eInverseAdd(this, SourcecleanerPackage.EXTENSION_POINT__EXTENSIONS, ExtensionPoint.class, msgs);
			msgs = basicSetExtensionPoint(newExtensionPoint, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.EXTENSION__EXTENSION_POINT, newExtensionPoint, newExtensionPoint));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getClazz() {
		return clazz;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setClazz(String newClazz) {
		String oldClazz = clazz;
		clazz = newClazz;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.EXTENSION__CLAZZ, oldClazz, clazz));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPointId() {
		return pointId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPointId(String newPointId) {
		String oldPointId = pointId;
		pointId = newPointId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.EXTENSION__POINT_ID, oldPointId, pointId));
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
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.EXTENSION__EXTRA, oldExtra, extra));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ExtensionAttribute> getAttributes() {
		if (attributes == null) {
			attributes = new EObjectContainmentEList<ExtensionAttribute>(ExtensionAttribute.class, this, SourcecleanerPackage.EXTENSION__ATTRIBUTES);
		}
		return attributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Java getImplements() {
		if (implements_ != null && implements_.eIsProxy()) {
			InternalEObject oldImplements = (InternalEObject)implements_;
			implements_ = (Java)eResolveProxy(oldImplements);
			if (implements_ != oldImplements) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SourcecleanerPackage.EXTENSION__IMPLEMENTS, oldImplements, implements_));
			}
		}
		return implements_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Java basicGetImplements() {
		return implements_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImplements(Java newImplements) {
		Java oldImplements = implements_;
		implements_ = newImplements;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.EXTENSION__IMPLEMENTS, oldImplements, implements_));
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
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.EXTENSION__DIAGRAPH, oldDiagraph, diagraph));
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
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.EXTENSION__ID, oldId, id));
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
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.EXTENSION__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SourcecleanerPackage.EXTENSION__EXTENSION_POINT:
				if (extensionPoint != null)
					msgs = ((InternalEObject)extensionPoint).eInverseRemove(this, SourcecleanerPackage.EXTENSION_POINT__EXTENSIONS, ExtensionPoint.class, msgs);
				return basicSetExtensionPoint((ExtensionPoint)otherEnd, msgs);
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
			case SourcecleanerPackage.EXTENSION__EXTENSION_POINT:
				return basicSetExtensionPoint(null, msgs);
			case SourcecleanerPackage.EXTENSION__ATTRIBUTES:
				return ((InternalEList<?>)getAttributes()).basicRemove(otherEnd, msgs);
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
			case SourcecleanerPackage.EXTENSION__EXTENSION_POINT:
				if (resolve) return getExtensionPoint();
				return basicGetExtensionPoint();
			case SourcecleanerPackage.EXTENSION__POINT_ID:
				return getPointId();
			case SourcecleanerPackage.EXTENSION__CLAZZ:
				return getClazz();
			case SourcecleanerPackage.EXTENSION__ATTRIBUTES:
				return getAttributes();
			case SourcecleanerPackage.EXTENSION__IMPLEMENTS:
				if (resolve) return getImplements();
				return basicGetImplements();
			case SourcecleanerPackage.EXTENSION__ID:
				return getId();
			case SourcecleanerPackage.EXTENSION__NAME:
				return getName();
			case SourcecleanerPackage.EXTENSION__EXTRA:
				return getExtra();
			case SourcecleanerPackage.EXTENSION__DIAGRAPH:
				return isDiagraph();
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
			case SourcecleanerPackage.EXTENSION__EXTENSION_POINT:
				setExtensionPoint((ExtensionPoint)newValue);
				return;
			case SourcecleanerPackage.EXTENSION__POINT_ID:
				setPointId((String)newValue);
				return;
			case SourcecleanerPackage.EXTENSION__CLAZZ:
				setClazz((String)newValue);
				return;
			case SourcecleanerPackage.EXTENSION__ATTRIBUTES:
				getAttributes().clear();
				getAttributes().addAll((Collection<? extends ExtensionAttribute>)newValue);
				return;
			case SourcecleanerPackage.EXTENSION__IMPLEMENTS:
				setImplements((Java)newValue);
				return;
			case SourcecleanerPackage.EXTENSION__ID:
				setId((String)newValue);
				return;
			case SourcecleanerPackage.EXTENSION__NAME:
				setName((String)newValue);
				return;
			case SourcecleanerPackage.EXTENSION__EXTRA:
				setExtra((String)newValue);
				return;
			case SourcecleanerPackage.EXTENSION__DIAGRAPH:
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
			case SourcecleanerPackage.EXTENSION__EXTENSION_POINT:
				setExtensionPoint((ExtensionPoint)null);
				return;
			case SourcecleanerPackage.EXTENSION__POINT_ID:
				setPointId(POINT_ID_EDEFAULT);
				return;
			case SourcecleanerPackage.EXTENSION__CLAZZ:
				setClazz(CLAZZ_EDEFAULT);
				return;
			case SourcecleanerPackage.EXTENSION__ATTRIBUTES:
				getAttributes().clear();
				return;
			case SourcecleanerPackage.EXTENSION__IMPLEMENTS:
				setImplements((Java)null);
				return;
			case SourcecleanerPackage.EXTENSION__ID:
				setId(ID_EDEFAULT);
				return;
			case SourcecleanerPackage.EXTENSION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case SourcecleanerPackage.EXTENSION__EXTRA:
				setExtra(EXTRA_EDEFAULT);
				return;
			case SourcecleanerPackage.EXTENSION__DIAGRAPH:
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
			case SourcecleanerPackage.EXTENSION__EXTENSION_POINT:
				return extensionPoint != null;
			case SourcecleanerPackage.EXTENSION__POINT_ID:
				return POINT_ID_EDEFAULT == null ? pointId != null : !POINT_ID_EDEFAULT.equals(pointId);
			case SourcecleanerPackage.EXTENSION__CLAZZ:
				return CLAZZ_EDEFAULT == null ? clazz != null : !CLAZZ_EDEFAULT.equals(clazz);
			case SourcecleanerPackage.EXTENSION__ATTRIBUTES:
				return attributes != null && !attributes.isEmpty();
			case SourcecleanerPackage.EXTENSION__IMPLEMENTS:
				return implements_ != null;
			case SourcecleanerPackage.EXTENSION__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case SourcecleanerPackage.EXTENSION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case SourcecleanerPackage.EXTENSION__EXTRA:
				return EXTRA_EDEFAULT == null ? extra != null : !EXTRA_EDEFAULT.equals(extra);
			case SourcecleanerPackage.EXTENSION__DIAGRAPH:
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
		result.append(" (pointId: ");
		result.append(pointId);
		result.append(", clazz: ");
		result.append(clazz);
		result.append(", id: ");
		result.append(id);
		result.append(", name: ");
		result.append(name);
		result.append(", extra: ");
		result.append(extra);
		result.append(", diagraph: ");
		result.append(diagraph);
		result.append(')');
		return result.toString();
	}

} //ExtensionImpl
