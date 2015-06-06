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
package rules;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Node Relation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link rules.NodeRelation#getRelation <em>Relation</em>}</li>
 *   <li>{@link rules.NodeRelation#getSource <em>Source</em>}</li>
 *   <li>{@link rules.NodeRelation#getTarget <em>Target</em>}</li>
 *   <li>{@link rules.NodeRelation#getLowerBound <em>Lower Bound</em>}</li>
 *   <li>{@link rules.NodeRelation#getUpperBound <em>Upper Bound</em>}</li>
 *   <li>{@link rules.NodeRelation#getRelationTgt <em>Relation Tgt</em>}</li>
 * </ul>
 * </p>
 *
 * @see rules.RulesPackage#getNodeRelation()
 * @model
 * @generated
 */
public interface NodeRelation extends EObject {
	/**
	 * Returns the value of the '<em><b>Relation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Relation</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Relation</em>' attribute.
	 * @see #setRelation(String)
	 * @see rules.RulesPackage#getNodeRelation_Relation()
	 * @model
	 * @generated
	 */
	String getRelation();

	/**
	 * Sets the value of the '{@link rules.NodeRelation#getRelation <em>Relation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Relation</em>' attribute.
	 * @see #getRelation()
	 * @generated
	 */
	void setRelation(String value);

	/**
	 * Returns the value of the '<em><b>Source</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link rules.Node#getRelatedNodes <em>Related Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' container reference.
	 * @see #setSource(Node)
	 * @see rules.RulesPackage#getNodeRelation_Source()
	 * @see rules.Node#getRelatedNodes
	 * @model opposite="relatedNodes" required="true" transient="false"
	 * @generated
	 */
	Node getSource();

	/**
	 * Sets the value of the '{@link rules.NodeRelation#getSource <em>Source</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' container reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(Node value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(Node)
	 * @see rules.RulesPackage#getNodeRelation_Target()
	 * @model required="true"
	 * @generated
	 */
	Node getTarget();

	/**
	 * Sets the value of the '{@link rules.NodeRelation#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(Node value);

	/**
	 * Returns the value of the '<em><b>Lower Bound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lower Bound</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lower Bound</em>' attribute.
	 * @see #setLowerBound(int)
	 * @see rules.RulesPackage#getNodeRelation_LowerBound()
	 * @model
	 * @generated
	 */
	int getLowerBound();

	/**
	 * Sets the value of the '{@link rules.NodeRelation#getLowerBound <em>Lower Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lower Bound</em>' attribute.
	 * @see #getLowerBound()
	 * @generated
	 */
	void setLowerBound(int value);

	/**
	 * Returns the value of the '<em><b>Upper Bound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Upper Bound</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Upper Bound</em>' attribute.
	 * @see #setUpperBound(int)
	 * @see rules.RulesPackage#getNodeRelation_UpperBound()
	 * @model
	 * @generated
	 */
	int getUpperBound();

	/**
	 * Sets the value of the '{@link rules.NodeRelation#getUpperBound <em>Upper Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Upper Bound</em>' attribute.
	 * @see #getUpperBound()
	 * @generated
	 */
	void setUpperBound(int value);

	/**
	 * Returns the value of the '<em><b>Relation Tgt</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Relation Tgt</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Relation Tgt</em>' attribute.
	 * @see #setRelationTgt(String)
	 * @see rules.RulesPackage#getNodeRelation_RelationTgt()
	 * @model
	 * @generated
	 */
	String getRelationTgt();

	/**
	 * Sets the value of the '{@link rules.NodeRelation#getRelationTgt <em>Relation Tgt</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Relation Tgt</em>' attribute.
	 * @see #getRelationTgt()
	 * @generated
	 */
	void setRelationTgt(String value);

} // NodeRelation
