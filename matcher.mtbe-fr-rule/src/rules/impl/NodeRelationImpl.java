/**
 * Copyright (c) 2014 Laboratoire de Genie Informatique et Ingenierie de Production - Ecole des Mines d'Ales
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Francois Pfister (ISOE-LGI2P) - initial API and implementation
 */
package rules.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;

import rules.Node;
import rules.NodeRelation;
import rules.RulesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Node Relation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link rules.impl.NodeRelationImpl#getRelation <em>Relation</em>}</li>
 *   <li>{@link rules.impl.NodeRelationImpl#getSource <em>Source</em>}</li>
 *   <li>{@link rules.impl.NodeRelationImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link rules.impl.NodeRelationImpl#getLowerBound <em>Lower Bound</em>}</li>
 *   <li>{@link rules.impl.NodeRelationImpl#getUpperBound <em>Upper Bound</em>}</li>
 *   <li>{@link rules.impl.NodeRelationImpl#getRelationTgt <em>Relation Tgt</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NodeRelationImpl extends EObjectImpl implements NodeRelation {
	/**
	 * The default value of the '{@link #getRelation() <em>Relation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRelation()
	 * @generated
	 * @ordered
	 */
	protected static final String RELATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRelation() <em>Relation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRelation()
	 * @generated
	 * @ordered
	 */
	protected String relation = RELATION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected Node target;

	/**
	 * The default value of the '{@link #getLowerBound() <em>Lower Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLowerBound()
	 * @generated
	 * @ordered
	 */
	protected static final int LOWER_BOUND_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getLowerBound() <em>Lower Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLowerBound()
	 * @generated
	 * @ordered
	 */
	protected int lowerBound = LOWER_BOUND_EDEFAULT;

	/**
	 * The default value of the '{@link #getUpperBound() <em>Upper Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUpperBound()
	 * @generated
	 * @ordered
	 */
	protected static final int UPPER_BOUND_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getUpperBound() <em>Upper Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUpperBound()
	 * @generated
	 * @ordered
	 */
	protected int upperBound = UPPER_BOUND_EDEFAULT;

	/**
	 * The default value of the '{@link #getRelationTgt() <em>Relation Tgt</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRelationTgt()
	 * @generated
	 * @ordered
	 */
	protected static final String RELATION_TGT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRelationTgt() <em>Relation Tgt</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRelationTgt()
	 * @generated
	 * @ordered
	 */
	protected String relationTgt = RELATION_TGT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NodeRelationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return RulesPackage.Literals.NODE_RELATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRelation() {
		return relation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRelation(String newRelation) {
		String oldRelation = relation;
		relation = newRelation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulesPackage.NODE_RELATION__RELATION, oldRelation, relation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node getSource() {
		if (eContainerFeatureID() != RulesPackage.NODE_RELATION__SOURCE) return null;
		return (Node)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSource(Node newSource, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newSource, RulesPackage.NODE_RELATION__SOURCE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSource(Node newSource) {
		if (newSource != eInternalContainer() || (eContainerFeatureID() != RulesPackage.NODE_RELATION__SOURCE && newSource != null)) {
			if (EcoreUtil.isAncestor(this, newSource))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newSource != null)
				msgs = ((InternalEObject)newSource).eInverseAdd(this, RulesPackage.NODE__RELATED_NODES, Node.class, msgs);
			msgs = basicSetSource(newSource, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulesPackage.NODE_RELATION__SOURCE, newSource, newSource));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node getTarget() {
		if (target != null && target.eIsProxy()) {
			InternalEObject oldTarget = (InternalEObject)target;
			target = (Node)eResolveProxy(oldTarget);
			if (target != oldTarget) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RulesPackage.NODE_RELATION__TARGET, oldTarget, target));
			}
		}
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node basicGetTarget() {
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTarget(Node newTarget) {
		Node oldTarget = target;
		target = newTarget;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulesPackage.NODE_RELATION__TARGET, oldTarget, target));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getLowerBound() {
		return lowerBound;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLowerBound(int newLowerBound) {
		int oldLowerBound = lowerBound;
		lowerBound = newLowerBound;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulesPackage.NODE_RELATION__LOWER_BOUND, oldLowerBound, lowerBound));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getUpperBound() {
		return upperBound;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUpperBound(int newUpperBound) {
		int oldUpperBound = upperBound;
		upperBound = newUpperBound;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulesPackage.NODE_RELATION__UPPER_BOUND, oldUpperBound, upperBound));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRelationTgt() {
		return relationTgt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRelationTgt(String newRelationTgt) {
		String oldRelationTgt = relationTgt;
		relationTgt = newRelationTgt;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulesPackage.NODE_RELATION__RELATION_TGT, oldRelationTgt, relationTgt));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RulesPackage.NODE_RELATION__SOURCE:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetSource((Node)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RulesPackage.NODE_RELATION__SOURCE:
				return basicSetSource(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case RulesPackage.NODE_RELATION__SOURCE:
				return eInternalContainer().eInverseRemove(this, RulesPackage.NODE__RELATED_NODES, Node.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RulesPackage.NODE_RELATION__RELATION:
				return getRelation();
			case RulesPackage.NODE_RELATION__SOURCE:
				return getSource();
			case RulesPackage.NODE_RELATION__TARGET:
				if (resolve) return getTarget();
				return basicGetTarget();
			case RulesPackage.NODE_RELATION__LOWER_BOUND:
				return new Integer(getLowerBound());
			case RulesPackage.NODE_RELATION__UPPER_BOUND:
				return new Integer(getUpperBound());
			case RulesPackage.NODE_RELATION__RELATION_TGT:
				return getRelationTgt();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case RulesPackage.NODE_RELATION__RELATION:
				setRelation((String)newValue);
				return;
			case RulesPackage.NODE_RELATION__SOURCE:
				setSource((Node)newValue);
				return;
			case RulesPackage.NODE_RELATION__TARGET:
				setTarget((Node)newValue);
				return;
			case RulesPackage.NODE_RELATION__LOWER_BOUND:
				setLowerBound(((Integer)newValue).intValue());
				return;
			case RulesPackage.NODE_RELATION__UPPER_BOUND:
				setUpperBound(((Integer)newValue).intValue());
				return;
			case RulesPackage.NODE_RELATION__RELATION_TGT:
				setRelationTgt((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(int featureID) {
		switch (featureID) {
			case RulesPackage.NODE_RELATION__RELATION:
				setRelation(RELATION_EDEFAULT);
				return;
			case RulesPackage.NODE_RELATION__SOURCE:
				setSource((Node)null);
				return;
			case RulesPackage.NODE_RELATION__TARGET:
				setTarget((Node)null);
				return;
			case RulesPackage.NODE_RELATION__LOWER_BOUND:
				setLowerBound(LOWER_BOUND_EDEFAULT);
				return;
			case RulesPackage.NODE_RELATION__UPPER_BOUND:
				setUpperBound(UPPER_BOUND_EDEFAULT);
				return;
			case RulesPackage.NODE_RELATION__RELATION_TGT:
				setRelationTgt(RELATION_TGT_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case RulesPackage.NODE_RELATION__RELATION:
				return RELATION_EDEFAULT == null ? relation != null : !RELATION_EDEFAULT.equals(relation);
			case RulesPackage.NODE_RELATION__SOURCE:
				return getSource() != null;
			case RulesPackage.NODE_RELATION__TARGET:
				return target != null;
			case RulesPackage.NODE_RELATION__LOWER_BOUND:
				return lowerBound != LOWER_BOUND_EDEFAULT;
			case RulesPackage.NODE_RELATION__UPPER_BOUND:
				return upperBound != UPPER_BOUND_EDEFAULT;
			case RulesPackage.NODE_RELATION__RELATION_TGT:
				return RELATION_TGT_EDEFAULT == null ? relationTgt != null : !RELATION_TGT_EDEFAULT.equals(relationTgt);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (relation: ");
		result.append(relation);
		result.append(", lowerBound: ");
		result.append(lowerBound);
		result.append(", upperBound: ");
		result.append(upperBound);
		result.append(", relationTgt: ");
		result.append(relationTgt);
		result.append(')');
		return result.toString();
	}

} //NodeRelationImpl
