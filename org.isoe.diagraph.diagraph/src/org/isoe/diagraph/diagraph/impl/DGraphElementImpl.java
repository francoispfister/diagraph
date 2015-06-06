/**
 */
package org.isoe.diagraph.diagraph.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;

import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.diagraph.DGraphElement;
import org.isoe.diagraph.diagraph.DiagraphPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>DGraph Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DGraphElementImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DGraphElementImpl#getSemanticRole <em>Semantic Role</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DGraphElementImpl#getIcon <em>Icon</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DGraphElementImpl#getGraph <em>Graph</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DGraphElementImpl#isAbztract <em>Abztract</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class DGraphElementImpl extends MinimalEObjectImpl.Container implements DGraphElement {
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
	 * The cached value of the '{@link #getSemanticRole() <em>Semantic Role</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSemanticRole()
	 * @generated
	 * @ordered
	 */
	protected ENamedElement semanticRole;

	/**
	 * The default value of the '{@link #getIcon() <em>Icon</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIcon()
	 * @generated
	 * @ordered
	 */
	protected static final String ICON_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIcon() <em>Icon</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIcon()
	 * @generated
	 * @ordered
	 */
	protected String icon = ICON_EDEFAULT;

	/**
	 * The default value of the '{@link #isAbztract() <em>Abztract</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAbztract()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ABZTRACT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isAbztract() <em>Abztract</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAbztract()
	 * @generated
	 * @ordered
	 */
	protected boolean abztract = ABZTRACT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DGraphElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DiagraphPackage.Literals.DGRAPH_ELEMENT;
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
			eNotify(new ENotificationImpl(this, Notification.SET, DiagraphPackage.DGRAPH_ELEMENT__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ENamedElement getSemanticRole() {
		if (semanticRole != null && semanticRole.eIsProxy()) {
			InternalEObject oldSemanticRole = (InternalEObject)semanticRole;
			semanticRole = (ENamedElement)eResolveProxy(oldSemanticRole);
			if (semanticRole != oldSemanticRole) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DiagraphPackage.DGRAPH_ELEMENT__SEMANTIC_ROLE, oldSemanticRole, semanticRole));
			}
		}
		return semanticRole;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ENamedElement basicGetSemanticRole() {
		return semanticRole;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSemanticRole(ENamedElement newSemanticRole) {
		ENamedElement oldSemanticRole = semanticRole;
		semanticRole = newSemanticRole;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagraphPackage.DGRAPH_ELEMENT__SEMANTIC_ROLE, oldSemanticRole, semanticRole));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIcon(String newIcon) {
		String oldIcon = icon;
		icon = newIcon;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagraphPackage.DGRAPH_ELEMENT__ICON, oldIcon, icon));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DGraph getGraph() {
		if (eContainerFeatureID() != DiagraphPackage.DGRAPH_ELEMENT__GRAPH) return null;
		return (DGraph)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetGraph(DGraph newGraph, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newGraph, DiagraphPackage.DGRAPH_ELEMENT__GRAPH, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGraph(DGraph newGraph) {
		if (newGraph != eInternalContainer() || (eContainerFeatureID() != DiagraphPackage.DGRAPH_ELEMENT__GRAPH && newGraph != null)) {
			if (EcoreUtil.isAncestor(this, newGraph))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newGraph != null)
				msgs = ((InternalEObject)newGraph).eInverseAdd(this, DiagraphPackage.DGRAPH__ELEMENTS, DGraph.class, msgs);
			msgs = basicSetGraph(newGraph, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagraphPackage.DGRAPH_ELEMENT__GRAPH, newGraph, newGraph));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAbztract() {
		return abztract;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAbztract(boolean newAbztract) {
		boolean oldAbztract = abztract;
		abztract = newAbztract;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagraphPackage.DGRAPH_ELEMENT__ABZTRACT, oldAbztract, abztract));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DiagraphPackage.DGRAPH_ELEMENT__GRAPH:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetGraph((DGraph)otherEnd, msgs);
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
			case DiagraphPackage.DGRAPH_ELEMENT__GRAPH:
				return basicSetGraph(null, msgs);
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
			case DiagraphPackage.DGRAPH_ELEMENT__GRAPH:
				return eInternalContainer().eInverseRemove(this, DiagraphPackage.DGRAPH__ELEMENTS, DGraph.class, msgs);
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
			case DiagraphPackage.DGRAPH_ELEMENT__NAME:
				return getName();
			case DiagraphPackage.DGRAPH_ELEMENT__SEMANTIC_ROLE:
				if (resolve) return getSemanticRole();
				return basicGetSemanticRole();
			case DiagraphPackage.DGRAPH_ELEMENT__ICON:
				return getIcon();
			case DiagraphPackage.DGRAPH_ELEMENT__GRAPH:
				return getGraph();
			case DiagraphPackage.DGRAPH_ELEMENT__ABZTRACT:
				return isAbztract();
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
			case DiagraphPackage.DGRAPH_ELEMENT__NAME:
				setName((String)newValue);
				return;
			case DiagraphPackage.DGRAPH_ELEMENT__SEMANTIC_ROLE:
				setSemanticRole((ENamedElement)newValue);
				return;
			case DiagraphPackage.DGRAPH_ELEMENT__ICON:
				setIcon((String)newValue);
				return;
			case DiagraphPackage.DGRAPH_ELEMENT__GRAPH:
				setGraph((DGraph)newValue);
				return;
			case DiagraphPackage.DGRAPH_ELEMENT__ABZTRACT:
				setAbztract((Boolean)newValue);
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
			case DiagraphPackage.DGRAPH_ELEMENT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case DiagraphPackage.DGRAPH_ELEMENT__SEMANTIC_ROLE:
				setSemanticRole((ENamedElement)null);
				return;
			case DiagraphPackage.DGRAPH_ELEMENT__ICON:
				setIcon(ICON_EDEFAULT);
				return;
			case DiagraphPackage.DGRAPH_ELEMENT__GRAPH:
				setGraph((DGraph)null);
				return;
			case DiagraphPackage.DGRAPH_ELEMENT__ABZTRACT:
				setAbztract(ABZTRACT_EDEFAULT);
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
			case DiagraphPackage.DGRAPH_ELEMENT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case DiagraphPackage.DGRAPH_ELEMENT__SEMANTIC_ROLE:
				return semanticRole != null;
			case DiagraphPackage.DGRAPH_ELEMENT__ICON:
				return ICON_EDEFAULT == null ? icon != null : !ICON_EDEFAULT.equals(icon);
			case DiagraphPackage.DGRAPH_ELEMENT__GRAPH:
				return getGraph() != null;
			case DiagraphPackage.DGRAPH_ELEMENT__ABZTRACT:
				return abztract != ABZTRACT_EDEFAULT;
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
		result.append(", icon: ");
		result.append(icon);
		result.append(", abztract: ");
		result.append(abztract);
		result.append(')');
		return result.toString();
	}

} //DGraphElementImpl
