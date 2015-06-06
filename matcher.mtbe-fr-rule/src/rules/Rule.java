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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link rules.Rule#getChildren <em>Children</em>}</li>
 *   <li>{@link rules.Rule#getParents <em>Parents</em>}</li>
 *   <li>{@link rules.Rule#getPremise <em>Premise</em>}</li>
 *   <li>{@link rules.Rule#getConclusion <em>Conclusion</em>}</li>
 *   <li>{@link rules.Rule#getNodes <em>Nodes</em>}</li>
 *   <li>{@link rules.Rule#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see rules.RulesPackage#getRule()
 * @model
 * @generated
 */
public interface Rule extends EObject {
	/**
	 * Returns the value of the '<em><b>Children</b></em>' reference list.
	 * The list contents are of type {@link rules.Rule}.
	 * It is bidirectional and its opposite is '{@link rules.Rule#getParents <em>Parents</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Children</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Children</em>' reference list.
	 * @see rules.RulesPackage#getRule_Children()
	 * @see rules.Rule#getParents
	 * @model type="rules.Rule" opposite="parents"
	 * @generated
	 */
	EList getChildren();

	/**
	 * Returns the value of the '<em><b>Parents</b></em>' reference list.
	 * The list contents are of type {@link rules.Rule}.
	 * It is bidirectional and its opposite is '{@link rules.Rule#getChildren <em>Children</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parents</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parents</em>' reference list.
	 * @see rules.RulesPackage#getRule_Parents()
	 * @see rules.Rule#getChildren
	 * @model type="rules.Rule" opposite="children"
	 * @generated
	 */
	EList getParents();

	/**
	 * Returns the value of the '<em><b>Premise</b></em>' reference list.
	 * The list contents are of type {@link rules.Node}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Premise</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Premise</em>' reference list.
	 * @see rules.RulesPackage#getRule_Premise()
	 * @model type="rules.Node"
	 * @generated
	 */
	EList getPremise();

	/**
	 * Returns the value of the '<em><b>Conclusion</b></em>' reference list.
	 * The list contents are of type {@link rules.Node}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Conclusion</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Conclusion</em>' reference list.
	 * @see rules.RulesPackage#getRule_Conclusion()
	 * @model type="rules.Node"
	 * @generated
	 */
	EList getConclusion();

	/**
	 * Returns the value of the '<em><b>Nodes</b></em>' containment reference list.
	 * The list contents are of type {@link rules.Node}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Nodes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nodes</em>' containment reference list.
	 * @see rules.RulesPackage#getRule_Nodes()
	 * @model type="rules.Node" containment="true"
	 * @generated
	 */
	EList getNodes();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see rules.RulesPackage#getRule_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link rules.Rule#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // Rule
