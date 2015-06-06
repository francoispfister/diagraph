/**
 */
package org.isoe.diagraph.diagraph.impl;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.isoe.diagraph.diagraph.DContainment;
import org.isoe.diagraph.diagraph.DGraphElement;
import org.isoe.diagraph.diagraph.DNestedEdge;
import org.isoe.diagraph.diagraph.DNode;
import org.isoe.diagraph.diagraph.DiagraphPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>DContainment</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DContainmentImpl#getNode <em>Node</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DContainmentImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DContainmentImpl#getEdges <em>Edges</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DContainmentImpl extends MinimalEObjectImpl.Container implements DContainment {
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
	 * The cached value of the '{@link #getEdges() <em>Edges</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEdges()
	 * @generated
	 * @ordered
	 */
	protected EList<DNestedEdge> edges;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DContainmentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DiagraphPackage.Literals.DCONTAINMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DNode getNode() {
		if (eContainerFeatureID() != DiagraphPackage.DCONTAINMENT__NODE) return null;
		return (DNode)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNode(DNode newNode, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newNode, DiagraphPackage.DCONTAINMENT__NODE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNode(DNode newNode) {
		if (newNode != eInternalContainer() || (eContainerFeatureID() != DiagraphPackage.DCONTAINMENT__NODE && newNode != null)) {
			if (EcoreUtil.isAncestor(this, newNode))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newNode != null)
				msgs = ((InternalEObject)newNode).eInverseAdd(this, DiagraphPackage.DNODE__CONTAINMENTS, DNode.class, msgs);
			msgs = basicSetNode(newNode, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagraphPackage.DCONTAINMENT__NODE, newNode, newNode));
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
			eNotify(new ENotificationImpl(this, Notification.SET, DiagraphPackage.DCONTAINMENT__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<DNestedEdge> getEdges() {
	//	if (edges == null) {
	//		edges = new EObjectResolvingEList<DNestedEdge>(DNestedEdge.class, this, DiagraphPackage.DCONTAINMENT__EDGES);
//		}
	//	return getNode().getGraph().getFacade1().getEdges(this);
	//	return edges;
		EList<DNestedEdge> result = new BasicEList<DNestedEdge>();  //FP150423azvoir
		for (DGraphElement element : getNode().getGraph().getElements()) {
			if (element instanceof DNestedEdge)
				 if (((DNestedEdge) element).getSource()==this)
				   result.add((DNestedEdge) element);
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DiagraphPackage.DCONTAINMENT__NODE:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetNode((DNode)otherEnd, msgs);
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
			case DiagraphPackage.DCONTAINMENT__NODE:
				return basicSetNode(null, msgs);
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
			case DiagraphPackage.DCONTAINMENT__NODE:
				return eInternalContainer().eInverseRemove(this, DiagraphPackage.DNODE__CONTAINMENTS, DNode.class, msgs);
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
			case DiagraphPackage.DCONTAINMENT__NODE:
				return getNode();
			case DiagraphPackage.DCONTAINMENT__NAME:
				return getName();
			case DiagraphPackage.DCONTAINMENT__EDGES:
				return getEdges();
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
			case DiagraphPackage.DCONTAINMENT__NODE:
				setNode((DNode)newValue);
				return;
			case DiagraphPackage.DCONTAINMENT__NAME:
				setName((String)newValue);
				return;
			case DiagraphPackage.DCONTAINMENT__EDGES:
				getEdges().clear();
				getEdges().addAll((Collection<? extends DNestedEdge>)newValue);
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
			case DiagraphPackage.DCONTAINMENT__NODE:
				setNode((DNode)null);
				return;
			case DiagraphPackage.DCONTAINMENT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case DiagraphPackage.DCONTAINMENT__EDGES:
				getEdges().clear();
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
			case DiagraphPackage.DCONTAINMENT__NODE:
				return getNode() != null;
			case DiagraphPackage.DCONTAINMENT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case DiagraphPackage.DCONTAINMENT__EDGES:
				return edges != null && !edges.isEmpty();
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
		result.append(')');
		return result.toString();
	}

} //DContainmentImpl
