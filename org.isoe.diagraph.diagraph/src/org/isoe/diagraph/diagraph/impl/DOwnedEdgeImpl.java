/**
 */
package org.isoe.diagraph.diagraph.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.isoe.diagraph.diagraph.DEdge;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.diagraph.DGraphElement;
import org.isoe.diagraph.diagraph.DNode;
import org.isoe.diagraph.diagraph.DOwnedEdge;
import org.isoe.diagraph.diagraph.DiagraphPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>DOwned Edge</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DOwnedEdgeImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DOwnedEdgeImpl#getSemanticRole <em>Semantic Role</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DOwnedEdgeImpl#getIcon <em>Icon</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DOwnedEdgeImpl#getGraph <em>Graph</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DOwnedEdgeImpl#isAbztract <em>Abztract</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DOwnedEdgeImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DOwnedEdgeImpl#getTargetReference <em>Target Reference</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DOwnedEdgeImpl#isPropagated <em>Propagated</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class DOwnedEdgeImpl extends DOwnedElementImpl implements DOwnedEdge {
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
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected DNode target;

	/**
	 * The cached value of the '{@link #getTargetReference() <em>Target Reference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetReference()
	 * @generated
	 * @ordered
	 */
	protected EReference targetReference;

	/**
	 * The default value of the '{@link #isPropagated() <em>Propagated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPropagated()
	 * @generated
	 * @ordered
	 */
	protected static final boolean PROPAGATED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isPropagated() <em>Propagated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPropagated()
	 * @generated
	 * @ordered
	 */
	protected boolean propagated = PROPAGATED_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DOwnedEdgeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DiagraphPackage.Literals.DOWNED_EDGE;
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
			eNotify(new ENotificationImpl(this, Notification.SET, DiagraphPackage.DOWNED_EDGE__NAME, oldName, name));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DiagraphPackage.DOWNED_EDGE__SEMANTIC_ROLE, oldSemanticRole, semanticRole));
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
			eNotify(new ENotificationImpl(this, Notification.SET, DiagraphPackage.DOWNED_EDGE__SEMANTIC_ROLE, oldSemanticRole, semanticRole));
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
			eNotify(new ENotificationImpl(this, Notification.SET, DiagraphPackage.DOWNED_EDGE__ICON, oldIcon, icon));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DGraph getGraph() {
		if (eContainerFeatureID() != DiagraphPackage.DOWNED_EDGE__GRAPH) return null;
		return (DGraph)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetGraph(DGraph newGraph, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newGraph, DiagraphPackage.DOWNED_EDGE__GRAPH, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGraph(DGraph newGraph) {
		if (newGraph != eInternalContainer() || (eContainerFeatureID() != DiagraphPackage.DOWNED_EDGE__GRAPH && newGraph != null)) {
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
			eNotify(new ENotificationImpl(this, Notification.SET, DiagraphPackage.DOWNED_EDGE__GRAPH, newGraph, newGraph));
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
			eNotify(new ENotificationImpl(this, Notification.SET, DiagraphPackage.DOWNED_EDGE__ABZTRACT, oldAbztract, abztract));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DNode getTarget() {
		if (target != null && target.eIsProxy()) {
			InternalEObject oldTarget = (InternalEObject)target;
			target = (DNode)eResolveProxy(oldTarget);
			if (target != oldTarget) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DiagraphPackage.DOWNED_EDGE__TARGET, oldTarget, target));
			}
		}
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DNode basicGetTarget() {
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTarget(DNode newTarget) {
		DNode oldTarget = target;
		target = newTarget;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagraphPackage.DOWNED_EDGE__TARGET, oldTarget, target));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTargetReference() {
		if (targetReference != null && targetReference.eIsProxy()) {
			InternalEObject oldTargetReference = (InternalEObject)targetReference;
			targetReference = (EReference)eResolveProxy(oldTargetReference);
			if (targetReference != oldTargetReference) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DiagraphPackage.DOWNED_EDGE__TARGET_REFERENCE, oldTargetReference, targetReference));
			}
		}
		return targetReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference basicGetTargetReference() {
		return targetReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTargetReference(EReference newTargetReference) {
		EReference oldTargetReference = targetReference;
		targetReference = newTargetReference;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagraphPackage.DOWNED_EDGE__TARGET_REFERENCE, oldTargetReference, targetReference));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isPropagated() {
		return propagated;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPropagated(boolean newPropagated) {
		boolean oldPropagated = propagated;
		propagated = newPropagated;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagraphPackage.DOWNED_EDGE__PROPAGATED, oldPropagated, propagated));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DiagraphPackage.DOWNED_EDGE__GRAPH:
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
			case DiagraphPackage.DOWNED_EDGE__GRAPH:
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
			case DiagraphPackage.DOWNED_EDGE__GRAPH:
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
			case DiagraphPackage.DOWNED_EDGE__NAME:
				return getName();
			case DiagraphPackage.DOWNED_EDGE__SEMANTIC_ROLE:
				if (resolve) return getSemanticRole();
				return basicGetSemanticRole();
			case DiagraphPackage.DOWNED_EDGE__ICON:
				return getIcon();
			case DiagraphPackage.DOWNED_EDGE__GRAPH:
				return getGraph();
			case DiagraphPackage.DOWNED_EDGE__ABZTRACT:
				return isAbztract();
			case DiagraphPackage.DOWNED_EDGE__TARGET:
				if (resolve) return getTarget();
				return basicGetTarget();
			case DiagraphPackage.DOWNED_EDGE__TARGET_REFERENCE:
				if (resolve) return getTargetReference();
				return basicGetTargetReference();
			case DiagraphPackage.DOWNED_EDGE__PROPAGATED:
				return isPropagated();
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
			case DiagraphPackage.DOWNED_EDGE__NAME:
				setName((String)newValue);
				return;
			case DiagraphPackage.DOWNED_EDGE__SEMANTIC_ROLE:
				setSemanticRole((ENamedElement)newValue);
				return;
			case DiagraphPackage.DOWNED_EDGE__ICON:
				setIcon((String)newValue);
				return;
			case DiagraphPackage.DOWNED_EDGE__GRAPH:
				setGraph((DGraph)newValue);
				return;
			case DiagraphPackage.DOWNED_EDGE__ABZTRACT:
				setAbztract((Boolean)newValue);
				return;
			case DiagraphPackage.DOWNED_EDGE__TARGET:
				setTarget((DNode)newValue);
				return;
			case DiagraphPackage.DOWNED_EDGE__TARGET_REFERENCE:
				setTargetReference((EReference)newValue);
				return;
			case DiagraphPackage.DOWNED_EDGE__PROPAGATED:
				setPropagated((Boolean)newValue);
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
			case DiagraphPackage.DOWNED_EDGE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case DiagraphPackage.DOWNED_EDGE__SEMANTIC_ROLE:
				setSemanticRole((ENamedElement)null);
				return;
			case DiagraphPackage.DOWNED_EDGE__ICON:
				setIcon(ICON_EDEFAULT);
				return;
			case DiagraphPackage.DOWNED_EDGE__GRAPH:
				setGraph((DGraph)null);
				return;
			case DiagraphPackage.DOWNED_EDGE__ABZTRACT:
				setAbztract(ABZTRACT_EDEFAULT);
				return;
			case DiagraphPackage.DOWNED_EDGE__TARGET:
				setTarget((DNode)null);
				return;
			case DiagraphPackage.DOWNED_EDGE__TARGET_REFERENCE:
				setTargetReference((EReference)null);
				return;
			case DiagraphPackage.DOWNED_EDGE__PROPAGATED:
				setPropagated(PROPAGATED_EDEFAULT);
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
			case DiagraphPackage.DOWNED_EDGE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case DiagraphPackage.DOWNED_EDGE__SEMANTIC_ROLE:
				return semanticRole != null;
			case DiagraphPackage.DOWNED_EDGE__ICON:
				return ICON_EDEFAULT == null ? icon != null : !ICON_EDEFAULT.equals(icon);
			case DiagraphPackage.DOWNED_EDGE__GRAPH:
				return getGraph() != null;
			case DiagraphPackage.DOWNED_EDGE__ABZTRACT:
				return abztract != ABZTRACT_EDEFAULT;
			case DiagraphPackage.DOWNED_EDGE__TARGET:
				return target != null;
			case DiagraphPackage.DOWNED_EDGE__TARGET_REFERENCE:
				return targetReference != null;
			case DiagraphPackage.DOWNED_EDGE__PROPAGATED:
				return propagated != PROPAGATED_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == DGraphElement.class) {
			switch (derivedFeatureID) {
				case DiagraphPackage.DOWNED_EDGE__NAME: return DiagraphPackage.DGRAPH_ELEMENT__NAME;
				case DiagraphPackage.DOWNED_EDGE__SEMANTIC_ROLE: return DiagraphPackage.DGRAPH_ELEMENT__SEMANTIC_ROLE;
				case DiagraphPackage.DOWNED_EDGE__ICON: return DiagraphPackage.DGRAPH_ELEMENT__ICON;
				case DiagraphPackage.DOWNED_EDGE__GRAPH: return DiagraphPackage.DGRAPH_ELEMENT__GRAPH;
				case DiagraphPackage.DOWNED_EDGE__ABZTRACT: return DiagraphPackage.DGRAPH_ELEMENT__ABZTRACT;
				default: return -1;
			}
		}
		if (baseClass == DEdge.class) {
			switch (derivedFeatureID) {
				case DiagraphPackage.DOWNED_EDGE__TARGET: return DiagraphPackage.DEDGE__TARGET;
				case DiagraphPackage.DOWNED_EDGE__TARGET_REFERENCE: return DiagraphPackage.DEDGE__TARGET_REFERENCE;
				case DiagraphPackage.DOWNED_EDGE__PROPAGATED: return DiagraphPackage.DEDGE__PROPAGATED;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == DGraphElement.class) {
			switch (baseFeatureID) {
				case DiagraphPackage.DGRAPH_ELEMENT__NAME: return DiagraphPackage.DOWNED_EDGE__NAME;
				case DiagraphPackage.DGRAPH_ELEMENT__SEMANTIC_ROLE: return DiagraphPackage.DOWNED_EDGE__SEMANTIC_ROLE;
				case DiagraphPackage.DGRAPH_ELEMENT__ICON: return DiagraphPackage.DOWNED_EDGE__ICON;
				case DiagraphPackage.DGRAPH_ELEMENT__GRAPH: return DiagraphPackage.DOWNED_EDGE__GRAPH;
				case DiagraphPackage.DGRAPH_ELEMENT__ABZTRACT: return DiagraphPackage.DOWNED_EDGE__ABZTRACT;
				default: return -1;
			}
		}
		if (baseClass == DEdge.class) {
			switch (baseFeatureID) {
				case DiagraphPackage.DEDGE__TARGET: return DiagraphPackage.DOWNED_EDGE__TARGET;
				case DiagraphPackage.DEDGE__TARGET_REFERENCE: return DiagraphPackage.DOWNED_EDGE__TARGET_REFERENCE;
				case DiagraphPackage.DEDGE__PROPAGATED: return DiagraphPackage.DOWNED_EDGE__PROPAGATED;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(", propagated: ");
		result.append(propagated);
		result.append(')');
		return result.toString();
	}

} //DOwnedEdgeImpl
