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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see rules.RulesFactory
 * @model kind="package"
 * @generated
 */
public interface RulesPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "rules";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.lirmm.fr/mtbe/1.0.1/rules.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "rules";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	RulesPackage eINSTANCE = rules.impl.RulesPackageImpl.init();

	/**
	 * The meta object id for the '{@link rules.impl.RulesLatticeImpl <em>Lattice</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see rules.impl.RulesLatticeImpl
	 * @see rules.impl.RulesPackageImpl#getRulesLattice()
	 * @generated
	 */
	int RULES_LATTICE = 0;

	/**
	 * The feature id for the '<em><b>Rules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULES_LATTICE__RULES = 0;

	/**
	 * The feature id for the '<em><b>Source</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULES_LATTICE__SOURCE = 1;

	/**
	 * The feature id for the '<em><b>Target</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULES_LATTICE__TARGET = 2;

	/**
	 * The number of structural features of the '<em>Lattice</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULES_LATTICE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link rules.impl.RuleImpl <em>Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see rules.impl.RuleImpl
	 * @see rules.impl.RulesPackageImpl#getRule()
	 * @generated
	 */
	int RULE = 1;

	/**
	 * The feature id for the '<em><b>Children</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__CHILDREN = 0;

	/**
	 * The feature id for the '<em><b>Parents</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__PARENTS = 1;

	/**
	 * The feature id for the '<em><b>Premise</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__PREMISE = 2;

	/**
	 * The feature id for the '<em><b>Conclusion</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__CONCLUSION = 3;

	/**
	 * The feature id for the '<em><b>Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__NODES = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__NAME = 5;

	/**
	 * The number of structural features of the '<em>Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link rules.impl.NodeImpl <em>Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see rules.impl.NodeImpl
	 * @see rules.impl.RulesPackageImpl#getNode()
	 * @generated
	 */
	int NODE = 2;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE__TYPE = 0;

	/**
	 * The feature id for the '<em><b>Related Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE__RELATED_NODES = 1;

	/**
	 * The number of structural features of the '<em>Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link rules.impl.NodeRelationImpl <em>Node Relation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see rules.impl.NodeRelationImpl
	 * @see rules.impl.RulesPackageImpl#getNodeRelation()
	 * @generated
	 */
	int NODE_RELATION = 3;

	/**
	 * The feature id for the '<em><b>Relation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_RELATION__RELATION = 0;

	/**
	 * The feature id for the '<em><b>Source</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_RELATION__SOURCE = 1;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_RELATION__TARGET = 2;

	/**
	 * The feature id for the '<em><b>Lower Bound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_RELATION__LOWER_BOUND = 3;

	/**
	 * The feature id for the '<em><b>Upper Bound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_RELATION__UPPER_BOUND = 4;

	/**
	 * The feature id for the '<em><b>Relation Tgt</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_RELATION__RELATION_TGT = 5;

	/**
	 * The number of structural features of the '<em>Node Relation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_RELATION_FEATURE_COUNT = 6;


	/**
	 * Returns the meta object for class '{@link rules.RulesLattice <em>Lattice</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Lattice</em>'.
	 * @see rules.RulesLattice
	 * @generated
	 */
	EClass getRulesLattice();

	/**
	 * Returns the meta object for the containment reference list '{@link rules.RulesLattice#getRules <em>Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Rules</em>'.
	 * @see rules.RulesLattice#getRules()
	 * @see #getRulesLattice()
	 * @generated
	 */
	EReference getRulesLattice_Rules();

	/**
	 * Returns the meta object for the attribute '{@link rules.RulesLattice#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Source</em>'.
	 * @see rules.RulesLattice#getSource()
	 * @see #getRulesLattice()
	 * @generated
	 */
	EAttribute getRulesLattice_Source();

	/**
	 * Returns the meta object for the attribute '{@link rules.RulesLattice#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Target</em>'.
	 * @see rules.RulesLattice#getTarget()
	 * @see #getRulesLattice()
	 * @generated
	 */
	EAttribute getRulesLattice_Target();

	/**
	 * Returns the meta object for class '{@link rules.Rule <em>Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rule</em>'.
	 * @see rules.Rule
	 * @generated
	 */
	EClass getRule();

	/**
	 * Returns the meta object for the reference list '{@link rules.Rule#getChildren <em>Children</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Children</em>'.
	 * @see rules.Rule#getChildren()
	 * @see #getRule()
	 * @generated
	 */
	EReference getRule_Children();

	/**
	 * Returns the meta object for the reference list '{@link rules.Rule#getParents <em>Parents</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Parents</em>'.
	 * @see rules.Rule#getParents()
	 * @see #getRule()
	 * @generated
	 */
	EReference getRule_Parents();

	/**
	 * Returns the meta object for the reference list '{@link rules.Rule#getPremise <em>Premise</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Premise</em>'.
	 * @see rules.Rule#getPremise()
	 * @see #getRule()
	 * @generated
	 */
	EReference getRule_Premise();

	/**
	 * Returns the meta object for the reference list '{@link rules.Rule#getConclusion <em>Conclusion</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Conclusion</em>'.
	 * @see rules.Rule#getConclusion()
	 * @see #getRule()
	 * @generated
	 */
	EReference getRule_Conclusion();

	/**
	 * Returns the meta object for the containment reference list '{@link rules.Rule#getNodes <em>Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Nodes</em>'.
	 * @see rules.Rule#getNodes()
	 * @see #getRule()
	 * @generated
	 */
	EReference getRule_Nodes();

	/**
	 * Returns the meta object for the attribute '{@link rules.Rule#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see rules.Rule#getName()
	 * @see #getRule()
	 * @generated
	 */
	EAttribute getRule_Name();

	/**
	 * Returns the meta object for class '{@link rules.Node <em>Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Node</em>'.
	 * @see rules.Node
	 * @generated
	 */
	EClass getNode();

	/**
	 * Returns the meta object for the attribute '{@link rules.Node#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see rules.Node#getType()
	 * @see #getNode()
	 * @generated
	 */
	EAttribute getNode_Type();

	/**
	 * Returns the meta object for the containment reference list '{@link rules.Node#getRelatedNodes <em>Related Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Related Nodes</em>'.
	 * @see rules.Node#getRelatedNodes()
	 * @see #getNode()
	 * @generated
	 */
	EReference getNode_RelatedNodes();

	/**
	 * Returns the meta object for class '{@link rules.NodeRelation <em>Node Relation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Node Relation</em>'.
	 * @see rules.NodeRelation
	 * @generated
	 */
	EClass getNodeRelation();

	/**
	 * Returns the meta object for the attribute '{@link rules.NodeRelation#getRelation <em>Relation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Relation</em>'.
	 * @see rules.NodeRelation#getRelation()
	 * @see #getNodeRelation()
	 * @generated
	 */
	EAttribute getNodeRelation_Relation();

	/**
	 * Returns the meta object for the container reference '{@link rules.NodeRelation#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Source</em>'.
	 * @see rules.NodeRelation#getSource()
	 * @see #getNodeRelation()
	 * @generated
	 */
	EReference getNodeRelation_Source();

	/**
	 * Returns the meta object for the reference '{@link rules.NodeRelation#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see rules.NodeRelation#getTarget()
	 * @see #getNodeRelation()
	 * @generated
	 */
	EReference getNodeRelation_Target();

	/**
	 * Returns the meta object for the attribute '{@link rules.NodeRelation#getLowerBound <em>Lower Bound</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Lower Bound</em>'.
	 * @see rules.NodeRelation#getLowerBound()
	 * @see #getNodeRelation()
	 * @generated
	 */
	EAttribute getNodeRelation_LowerBound();

	/**
	 * Returns the meta object for the attribute '{@link rules.NodeRelation#getUpperBound <em>Upper Bound</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Upper Bound</em>'.
	 * @see rules.NodeRelation#getUpperBound()
	 * @see #getNodeRelation()
	 * @generated
	 */
	EAttribute getNodeRelation_UpperBound();

	/**
	 * Returns the meta object for the attribute '{@link rules.NodeRelation#getRelationTgt <em>Relation Tgt</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Relation Tgt</em>'.
	 * @see rules.NodeRelation#getRelationTgt()
	 * @see #getNodeRelation()
	 * @generated
	 */
	EAttribute getNodeRelation_RelationTgt();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	RulesFactory getRulesFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link rules.impl.RulesLatticeImpl <em>Lattice</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see rules.impl.RulesLatticeImpl
		 * @see rules.impl.RulesPackageImpl#getRulesLattice()
		 * @generated
		 */
		EClass RULES_LATTICE = eINSTANCE.getRulesLattice();

		/**
		 * The meta object literal for the '<em><b>Rules</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULES_LATTICE__RULES = eINSTANCE.getRulesLattice_Rules();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULES_LATTICE__SOURCE = eINSTANCE.getRulesLattice_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULES_LATTICE__TARGET = eINSTANCE.getRulesLattice_Target();

		/**
		 * The meta object literal for the '{@link rules.impl.RuleImpl <em>Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see rules.impl.RuleImpl
		 * @see rules.impl.RulesPackageImpl#getRule()
		 * @generated
		 */
		EClass RULE = eINSTANCE.getRule();

		/**
		 * The meta object literal for the '<em><b>Children</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE__CHILDREN = eINSTANCE.getRule_Children();

		/**
		 * The meta object literal for the '<em><b>Parents</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE__PARENTS = eINSTANCE.getRule_Parents();

		/**
		 * The meta object literal for the '<em><b>Premise</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE__PREMISE = eINSTANCE.getRule_Premise();

		/**
		 * The meta object literal for the '<em><b>Conclusion</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE__CONCLUSION = eINSTANCE.getRule_Conclusion();

		/**
		 * The meta object literal for the '<em><b>Nodes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE__NODES = eINSTANCE.getRule_Nodes();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE__NAME = eINSTANCE.getRule_Name();

		/**
		 * The meta object literal for the '{@link rules.impl.NodeImpl <em>Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see rules.impl.NodeImpl
		 * @see rules.impl.RulesPackageImpl#getNode()
		 * @generated
		 */
		EClass NODE = eINSTANCE.getNode();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NODE__TYPE = eINSTANCE.getNode_Type();

		/**
		 * The meta object literal for the '<em><b>Related Nodes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE__RELATED_NODES = eINSTANCE.getNode_RelatedNodes();

		/**
		 * The meta object literal for the '{@link rules.impl.NodeRelationImpl <em>Node Relation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see rules.impl.NodeRelationImpl
		 * @see rules.impl.RulesPackageImpl#getNodeRelation()
		 * @generated
		 */
		EClass NODE_RELATION = eINSTANCE.getNodeRelation();

		/**
		 * The meta object literal for the '<em><b>Relation</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NODE_RELATION__RELATION = eINSTANCE.getNodeRelation_Relation();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE_RELATION__SOURCE = eINSTANCE.getNodeRelation_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE_RELATION__TARGET = eINSTANCE.getNodeRelation_Target();

		/**
		 * The meta object literal for the '<em><b>Lower Bound</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NODE_RELATION__LOWER_BOUND = eINSTANCE.getNodeRelation_LowerBound();

		/**
		 * The meta object literal for the '<em><b>Upper Bound</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NODE_RELATION__UPPER_BOUND = eINSTANCE.getNodeRelation_UpperBound();

		/**
		 * The meta object literal for the '<em><b>Relation Tgt</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NODE_RELATION__RELATION_TGT = eINSTANCE.getNodeRelation_RelationTgt();

	}

} //RulesPackage
