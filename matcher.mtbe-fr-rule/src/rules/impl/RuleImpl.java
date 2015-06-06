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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import rules.Node;
import rules.Rule;
import rules.RulesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link rules.impl.RuleImpl#getChildren <em>Children</em>}</li>
 *   <li>{@link rules.impl.RuleImpl#getParents <em>Parents</em>}</li>
 *   <li>{@link rules.impl.RuleImpl#getPremise <em>Premise</em>}</li>
 *   <li>{@link rules.impl.RuleImpl#getConclusion <em>Conclusion</em>}</li>
 *   <li>{@link rules.impl.RuleImpl#getNodes <em>Nodes</em>}</li>
 *   <li>{@link rules.impl.RuleImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RuleImpl extends EObjectImpl implements Rule {
	/**
	 * The cached value of the '{@link #getChildren() <em>Children</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildren()
	 * @generated
	 * @ordered
	 */
	protected EList children;

	/**
	 * The cached value of the '{@link #getParents() <em>Parents</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParents()
	 * @generated
	 * @ordered
	 */
	protected EList parents;

	/**
	 * The cached value of the '{@link #getPremise() <em>Premise</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPremise()
	 * @generated
	 * @ordered
	 */
	protected EList premise;

	/**
	 * The cached value of the '{@link #getConclusion() <em>Conclusion</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConclusion()
	 * @generated
	 * @ordered
	 */
	protected EList conclusion;

	/**
	 * The cached value of the '{@link #getNodes() <em>Nodes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNodes()
	 * @generated
	 * @ordered
	 */
	protected EList nodes;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return RulesPackage.Literals.RULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getChildren() {
		if (children == null) {
			children = new EObjectWithInverseResolvingEList.ManyInverse(Rule.class, this, RulesPackage.RULE__CHILDREN, RulesPackage.RULE__PARENTS);
		}
		return children;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getParents() {
		if (parents == null) {
			parents = new EObjectWithInverseResolvingEList.ManyInverse(Rule.class, this, RulesPackage.RULE__PARENTS, RulesPackage.RULE__CHILDREN);
		}
		return parents;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getPremise() {
		if (premise == null) {
			premise = new EObjectResolvingEList(Node.class, this, RulesPackage.RULE__PREMISE);
		}
		return premise;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getConclusion() {
		if (conclusion == null) {
			conclusion = new EObjectResolvingEList(Node.class, this, RulesPackage.RULE__CONCLUSION);
		}
		return conclusion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getNodes() {
		if (nodes == null) {
			nodes = new EObjectContainmentEList(Node.class, this, RulesPackage.RULE__NODES);
		}
		return nodes;
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
			eNotify(new ENotificationImpl(this, Notification.SET, RulesPackage.RULE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RulesPackage.RULE__CHILDREN:
				return ((InternalEList)getChildren()).basicAdd(otherEnd, msgs);
			case RulesPackage.RULE__PARENTS:
				return ((InternalEList)getParents()).basicAdd(otherEnd, msgs);
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
			case RulesPackage.RULE__CHILDREN:
				return ((InternalEList)getChildren()).basicRemove(otherEnd, msgs);
			case RulesPackage.RULE__PARENTS:
				return ((InternalEList)getParents()).basicRemove(otherEnd, msgs);
			case RulesPackage.RULE__NODES:
				return ((InternalEList)getNodes()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RulesPackage.RULE__CHILDREN:
				return getChildren();
			case RulesPackage.RULE__PARENTS:
				return getParents();
			case RulesPackage.RULE__PREMISE:
				return getPremise();
			case RulesPackage.RULE__CONCLUSION:
				return getConclusion();
			case RulesPackage.RULE__NODES:
				return getNodes();
			case RulesPackage.RULE__NAME:
				return getName();
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
			case RulesPackage.RULE__CHILDREN:
				getChildren().clear();
				getChildren().addAll((Collection)newValue);
				return;
			case RulesPackage.RULE__PARENTS:
				getParents().clear();
				getParents().addAll((Collection)newValue);
				return;
			case RulesPackage.RULE__PREMISE:
				getPremise().clear();
				getPremise().addAll((Collection)newValue);
				return;
			case RulesPackage.RULE__CONCLUSION:
				getConclusion().clear();
				getConclusion().addAll((Collection)newValue);
				return;
			case RulesPackage.RULE__NODES:
				getNodes().clear();
				getNodes().addAll((Collection)newValue);
				return;
			case RulesPackage.RULE__NAME:
				setName((String)newValue);
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
			case RulesPackage.RULE__CHILDREN:
				getChildren().clear();
				return;
			case RulesPackage.RULE__PARENTS:
				getParents().clear();
				return;
			case RulesPackage.RULE__PREMISE:
				getPremise().clear();
				return;
			case RulesPackage.RULE__CONCLUSION:
				getConclusion().clear();
				return;
			case RulesPackage.RULE__NODES:
				getNodes().clear();
				return;
			case RulesPackage.RULE__NAME:
				setName(NAME_EDEFAULT);
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
			case RulesPackage.RULE__CHILDREN:
				return children != null && !children.isEmpty();
			case RulesPackage.RULE__PARENTS:
				return parents != null && !parents.isEmpty();
			case RulesPackage.RULE__PREMISE:
				return premise != null && !premise.isEmpty();
			case RulesPackage.RULE__CONCLUSION:
				return conclusion != null && !conclusion.isEmpty();
			case RulesPackage.RULE__NODES:
				return nodes != null && !nodes.isEmpty();
			case RulesPackage.RULE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
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
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //RuleImpl
