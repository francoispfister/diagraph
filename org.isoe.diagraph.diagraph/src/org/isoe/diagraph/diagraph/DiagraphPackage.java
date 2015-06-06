/**
 */
package org.isoe.diagraph.diagraph;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.isoe.diagraph.diagraph.DiagraphFactory
 * @model kind="package"
 * @generated
 */
public interface DiagraphPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "diagraph";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://isoe-2012-diagraph-dsmlv4";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "diagraph";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DiagraphPackage eINSTANCE = org.isoe.diagraph.diagraph.impl.DiagraphPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.diagraph.impl.DGraphElementImpl <em>DGraph Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.diagraph.impl.DGraphElementImpl
	 * @see org.isoe.diagraph.diagraph.impl.DiagraphPackageImpl#getDGraphElement()
	 * @generated
	 */
	int DGRAPH_ELEMENT = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DGRAPH_ELEMENT__NAME = 0;

	/**
	 * The feature id for the '<em><b>Semantic Role</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DGRAPH_ELEMENT__SEMANTIC_ROLE = 1;

	/**
	 * The feature id for the '<em><b>Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DGRAPH_ELEMENT__ICON = 2;

	/**
	 * The feature id for the '<em><b>Graph</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DGRAPH_ELEMENT__GRAPH = 3;

	/**
	 * The feature id for the '<em><b>Abztract</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DGRAPH_ELEMENT__ABZTRACT = 4;

	/**
	 * The number of structural features of the '<em>DGraph Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DGRAPH_ELEMENT_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>DGraph Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DGRAPH_ELEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.diagraph.impl.DEdgeImpl <em>DEdge</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.diagraph.impl.DEdgeImpl
	 * @see org.isoe.diagraph.diagraph.impl.DiagraphPackageImpl#getDEdge()
	 * @generated
	 */
	int DEDGE = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEDGE__NAME = DGRAPH_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Semantic Role</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEDGE__SEMANTIC_ROLE = DGRAPH_ELEMENT__SEMANTIC_ROLE;

	/**
	 * The feature id for the '<em><b>Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEDGE__ICON = DGRAPH_ELEMENT__ICON;

	/**
	 * The feature id for the '<em><b>Graph</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEDGE__GRAPH = DGRAPH_ELEMENT__GRAPH;

	/**
	 * The feature id for the '<em><b>Abztract</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEDGE__ABZTRACT = DGRAPH_ELEMENT__ABZTRACT;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEDGE__TARGET = DGRAPH_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Target Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEDGE__TARGET_REFERENCE = DGRAPH_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Propagated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEDGE__PROPAGATED = DGRAPH_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>DEdge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEDGE_FEATURE_COUNT = DGRAPH_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>DEdge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEDGE_OPERATION_COUNT = DGRAPH_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.diagraph.impl.DLabeledElementImpl <em>DLabeled Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.diagraph.impl.DLabeledElementImpl
	 * @see org.isoe.diagraph.diagraph.impl.DiagraphPackageImpl#getDLabeledElement()
	 * @generated
	 */
	int DLABELED_ELEMENT = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLABELED_ELEMENT__NAME = DGRAPH_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Semantic Role</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLABELED_ELEMENT__SEMANTIC_ROLE = DGRAPH_ELEMENT__SEMANTIC_ROLE;

	/**
	 * The feature id for the '<em><b>Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLABELED_ELEMENT__ICON = DGRAPH_ELEMENT__ICON;

	/**
	 * The feature id for the '<em><b>Graph</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLABELED_ELEMENT__GRAPH = DGRAPH_ELEMENT__GRAPH;

	/**
	 * The feature id for the '<em><b>Abztract</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLABELED_ELEMENT__ABZTRACT = DGRAPH_ELEMENT__ABZTRACT;

	/**
	 * The feature id for the '<em><b>EClaz</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLABELED_ELEMENT__ECLAZ = DGRAPH_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Dlabels</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLABELED_ELEMENT__DLABELS = DGRAPH_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Labls</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLABELED_ELEMENT__LABLS = DGRAPH_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLABELED_ELEMENT__EXPRESSION = DGRAPH_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>DLabeled Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLABELED_ELEMENT_FEATURE_COUNT = DGRAPH_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>DLabeled Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLABELED_ELEMENT_OPERATION_COUNT = DGRAPH_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.diagraph.impl.DNodeImpl <em>DNode</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.diagraph.impl.DNodeImpl
	 * @see org.isoe.diagraph.diagraph.impl.DiagraphPackageImpl#getDNode()
	 * @generated
	 */
	int DNODE = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE__NAME = DLABELED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Semantic Role</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE__SEMANTIC_ROLE = DLABELED_ELEMENT__SEMANTIC_ROLE;

	/**
	 * The feature id for the '<em><b>Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE__ICON = DLABELED_ELEMENT__ICON;

	/**
	 * The feature id for the '<em><b>Graph</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE__GRAPH = DLABELED_ELEMENT__GRAPH;

	/**
	 * The feature id for the '<em><b>Abztract</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE__ABZTRACT = DLABELED_ELEMENT__ABZTRACT;

	/**
	 * The feature id for the '<em><b>EClaz</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE__ECLAZ = DLABELED_ELEMENT__ECLAZ;

	/**
	 * The feature id for the '<em><b>Dlabels</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE__DLABELS = DLABELED_ELEMENT__DLABELS;

	/**
	 * The feature id for the '<em><b>Labls</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE__LABLS = DLABELED_ELEMENT__LABLS;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE__EXPRESSION = DLABELED_ELEMENT__EXPRESSION;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE__OWNER = DLABELED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>View Navigation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE__VIEW_NAVIGATION = DLABELED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Shape</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE__SHAPE = DLABELED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Layout</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE__LAYOUT = DLABELED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Navigation Link</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE__NAVIGATION_LINK = DLABELED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Containments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE__CONTAINMENTS = DLABELED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>DNode</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE_FEATURE_COUNT = DLABELED_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The number of operations of the '<em>DNode</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE_OPERATION_COUNT = DLABELED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.diagraph.impl.DOwnedElementImpl <em>DOwned Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.diagraph.impl.DOwnedElementImpl
	 * @see org.isoe.diagraph.diagraph.impl.DiagraphPackageImpl#getDOwnedElement()
	 * @generated
	 */
	int DOWNED_ELEMENT = 8;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOWNED_ELEMENT__OWNER = 0;

	/**
	 * The number of structural features of the '<em>DOwned Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOWNED_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>DOwned Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOWNED_ELEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.diagraph.impl.DOwnedEdgeImpl <em>DOwned Edge</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.diagraph.impl.DOwnedEdgeImpl
	 * @see org.isoe.diagraph.diagraph.impl.DiagraphPackageImpl#getDOwnedEdge()
	 * @generated
	 */
	int DOWNED_EDGE = 9;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOWNED_EDGE__OWNER = DOWNED_ELEMENT__OWNER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOWNED_EDGE__NAME = DOWNED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Semantic Role</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOWNED_EDGE__SEMANTIC_ROLE = DOWNED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOWNED_EDGE__ICON = DOWNED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Graph</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOWNED_EDGE__GRAPH = DOWNED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Abztract</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOWNED_EDGE__ABZTRACT = DOWNED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOWNED_EDGE__TARGET = DOWNED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Target Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOWNED_EDGE__TARGET_REFERENCE = DOWNED_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Propagated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOWNED_EDGE__PROPAGATED = DOWNED_ELEMENT_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>DOwned Edge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOWNED_EDGE_FEATURE_COUNT = DOWNED_ELEMENT_FEATURE_COUNT + 8;

	/**
	 * The number of operations of the '<em>DOwned Edge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOWNED_EDGE_OPERATION_COUNT = DOWNED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.diagraph.impl.DLabeledEdgeImpl <em>DLabeled Edge</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.diagraph.impl.DLabeledEdgeImpl
	 * @see org.isoe.diagraph.diagraph.impl.DiagraphPackageImpl#getDLabeledEdge()
	 * @generated
	 */
	int DLABELED_EDGE = 3;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLABELED_EDGE__OWNER = DOWNED_EDGE__OWNER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLABELED_EDGE__NAME = DOWNED_EDGE__NAME;

	/**
	 * The feature id for the '<em><b>Semantic Role</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLABELED_EDGE__SEMANTIC_ROLE = DOWNED_EDGE__SEMANTIC_ROLE;

	/**
	 * The feature id for the '<em><b>Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLABELED_EDGE__ICON = DOWNED_EDGE__ICON;

	/**
	 * The feature id for the '<em><b>Graph</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLABELED_EDGE__GRAPH = DOWNED_EDGE__GRAPH;

	/**
	 * The feature id for the '<em><b>Abztract</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLABELED_EDGE__ABZTRACT = DOWNED_EDGE__ABZTRACT;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLABELED_EDGE__TARGET = DOWNED_EDGE__TARGET;

	/**
	 * The feature id for the '<em><b>Target Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLABELED_EDGE__TARGET_REFERENCE = DOWNED_EDGE__TARGET_REFERENCE;

	/**
	 * The feature id for the '<em><b>Propagated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLABELED_EDGE__PROPAGATED = DOWNED_EDGE__PROPAGATED;

	/**
	 * The feature id for the '<em><b>EClaz</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLABELED_EDGE__ECLAZ = DOWNED_EDGE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Dlabels</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLABELED_EDGE__DLABELS = DOWNED_EDGE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Labls</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLABELED_EDGE__LABLS = DOWNED_EDGE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLABELED_EDGE__EXPRESSION = DOWNED_EDGE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLABELED_EDGE__SOURCE = DOWNED_EDGE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Arrows</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLABELED_EDGE__ARROWS = DOWNED_EDGE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Source Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLABELED_EDGE__SOURCE_REFERENCE = DOWNED_EDGE_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>DLabeled Edge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLABELED_EDGE_FEATURE_COUNT = DOWNED_EDGE_FEATURE_COUNT + 7;

	/**
	 * The number of operations of the '<em>DLabeled Edge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLABELED_EDGE_OPERATION_COUNT = DOWNED_EDGE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.diagraph.impl.DSimpleEdgeImpl <em>DSimple Edge</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.diagraph.impl.DSimpleEdgeImpl
	 * @see org.isoe.diagraph.diagraph.impl.DiagraphPackageImpl#getDSimpleEdge()
	 * @generated
	 */
	int DSIMPLE_EDGE = 19;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DSIMPLE_EDGE__NAME = DEDGE__NAME;

	/**
	 * The feature id for the '<em><b>Semantic Role</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DSIMPLE_EDGE__SEMANTIC_ROLE = DEDGE__SEMANTIC_ROLE;

	/**
	 * The feature id for the '<em><b>Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DSIMPLE_EDGE__ICON = DEDGE__ICON;

	/**
	 * The feature id for the '<em><b>Graph</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DSIMPLE_EDGE__GRAPH = DEDGE__GRAPH;

	/**
	 * The feature id for the '<em><b>Abztract</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DSIMPLE_EDGE__ABZTRACT = DEDGE__ABZTRACT;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DSIMPLE_EDGE__TARGET = DEDGE__TARGET;

	/**
	 * The feature id for the '<em><b>Target Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DSIMPLE_EDGE__TARGET_REFERENCE = DEDGE__TARGET_REFERENCE;

	/**
	 * The feature id for the '<em><b>Propagated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DSIMPLE_EDGE__PROPAGATED = DEDGE__PROPAGATED;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DSIMPLE_EDGE__SOURCE = DEDGE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>DSimple Edge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DSIMPLE_EDGE_FEATURE_COUNT = DEDGE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>DSimple Edge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DSIMPLE_EDGE_OPERATION_COUNT = DEDGE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.diagraph.impl.DLineEdgeImpl <em>DLine Edge</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.diagraph.impl.DLineEdgeImpl
	 * @see org.isoe.diagraph.diagraph.impl.DiagraphPackageImpl#getDLineEdge()
	 * @generated
	 */
	int DLINE_EDGE = 15;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLINE_EDGE__NAME = DSIMPLE_EDGE__NAME;

	/**
	 * The feature id for the '<em><b>Semantic Role</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLINE_EDGE__SEMANTIC_ROLE = DSIMPLE_EDGE__SEMANTIC_ROLE;

	/**
	 * The feature id for the '<em><b>Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLINE_EDGE__ICON = DSIMPLE_EDGE__ICON;

	/**
	 * The feature id for the '<em><b>Graph</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLINE_EDGE__GRAPH = DSIMPLE_EDGE__GRAPH;

	/**
	 * The feature id for the '<em><b>Abztract</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLINE_EDGE__ABZTRACT = DSIMPLE_EDGE__ABZTRACT;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLINE_EDGE__TARGET = DSIMPLE_EDGE__TARGET;

	/**
	 * The feature id for the '<em><b>Target Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLINE_EDGE__TARGET_REFERENCE = DSIMPLE_EDGE__TARGET_REFERENCE;

	/**
	 * The feature id for the '<em><b>Propagated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLINE_EDGE__PROPAGATED = DSIMPLE_EDGE__PROPAGATED;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLINE_EDGE__SOURCE = DSIMPLE_EDGE__SOURCE;

	/**
	 * The feature id for the '<em><b>Arrows</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLINE_EDGE__ARROWS = DSIMPLE_EDGE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>DLine Edge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLINE_EDGE_FEATURE_COUNT = DSIMPLE_EDGE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>DLine Edge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLINE_EDGE_OPERATION_COUNT = DSIMPLE_EDGE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.diagraph.impl.DReferenceImpl <em>DReference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.diagraph.impl.DReferenceImpl
	 * @see org.isoe.diagraph.diagraph.impl.DiagraphPackageImpl#getDReference()
	 * @generated
	 */
	int DREFERENCE = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DREFERENCE__NAME = DLINE_EDGE__NAME;

	/**
	 * The feature id for the '<em><b>Semantic Role</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DREFERENCE__SEMANTIC_ROLE = DLINE_EDGE__SEMANTIC_ROLE;

	/**
	 * The feature id for the '<em><b>Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DREFERENCE__ICON = DLINE_EDGE__ICON;

	/**
	 * The feature id for the '<em><b>Graph</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DREFERENCE__GRAPH = DLINE_EDGE__GRAPH;

	/**
	 * The feature id for the '<em><b>Abztract</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DREFERENCE__ABZTRACT = DLINE_EDGE__ABZTRACT;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DREFERENCE__TARGET = DLINE_EDGE__TARGET;

	/**
	 * The feature id for the '<em><b>Target Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DREFERENCE__TARGET_REFERENCE = DLINE_EDGE__TARGET_REFERENCE;

	/**
	 * The feature id for the '<em><b>Propagated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DREFERENCE__PROPAGATED = DLINE_EDGE__PROPAGATED;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DREFERENCE__SOURCE = DLINE_EDGE__SOURCE;

	/**
	 * The feature id for the '<em><b>Arrows</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DREFERENCE__ARROWS = DLINE_EDGE__ARROWS;

	/**
	 * The number of structural features of the '<em>DReference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DREFERENCE_FEATURE_COUNT = DLINE_EDGE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>DReference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DREFERENCE_OPERATION_COUNT = DLINE_EDGE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.diagraph.impl.DNestedEdgeImpl <em>DNested Edge</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.diagraph.impl.DNestedEdgeImpl
	 * @see org.isoe.diagraph.diagraph.impl.DiagraphPackageImpl#getDNestedEdge()
	 * @generated
	 */
	int DNESTED_EDGE = 5;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNESTED_EDGE__OWNER = DOWNED_EDGE__OWNER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNESTED_EDGE__NAME = DOWNED_EDGE__NAME;

	/**
	 * The feature id for the '<em><b>Semantic Role</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNESTED_EDGE__SEMANTIC_ROLE = DOWNED_EDGE__SEMANTIC_ROLE;

	/**
	 * The feature id for the '<em><b>Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNESTED_EDGE__ICON = DOWNED_EDGE__ICON;

	/**
	 * The feature id for the '<em><b>Graph</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNESTED_EDGE__GRAPH = DOWNED_EDGE__GRAPH;

	/**
	 * The feature id for the '<em><b>Abztract</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNESTED_EDGE__ABZTRACT = DOWNED_EDGE__ABZTRACT;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNESTED_EDGE__TARGET = DOWNED_EDGE__TARGET;

	/**
	 * The feature id for the '<em><b>Target Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNESTED_EDGE__TARGET_REFERENCE = DOWNED_EDGE__TARGET_REFERENCE;

	/**
	 * The feature id for the '<em><b>Propagated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNESTED_EDGE__PROPAGATED = DOWNED_EDGE__PROPAGATED;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNESTED_EDGE__SOURCE = DOWNED_EDGE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>DNested Edge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNESTED_EDGE_FEATURE_COUNT = DOWNED_EDGE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>DNested Edge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNESTED_EDGE_OPERATION_COUNT = DOWNED_EDGE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.diagraph.impl.DGraphImpl <em>DGraph</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.diagraph.impl.DGraphImpl
	 * @see org.isoe.diagraph.diagraph.impl.DiagraphPackageImpl#getDGraph()
	 * @generated
	 */
	int DGRAPH = 6;

	/**
	 * The feature id for the '<em><b>Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DGRAPH__ELEMENTS = 0;

	/**
	 * The feature id for the '<em><b>Point Of View</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DGRAPH__POINT_OF_VIEW = 1;

	/**
	 * The feature id for the '<em><b>View Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DGRAPH__VIEW_NAME = 2;

	/**
	 * The feature id for the '<em><b>Facade1</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DGRAPH__FACADE1 = 3;

	/**
	 * The feature id for the '<em><b>Facade2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DGRAPH__FACADE2 = 4;

	/**
	 * The number of structural features of the '<em>DGraph</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DGRAPH_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>DGraph</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DGRAPH_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.diagraph.impl.DCompartmentEdgeImpl <em>DCompartment Edge</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.diagraph.impl.DCompartmentEdgeImpl
	 * @see org.isoe.diagraph.diagraph.impl.DiagraphPackageImpl#getDCompartmentEdge()
	 * @generated
	 */
	int DCOMPARTMENT_EDGE = 10;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DCOMPARTMENT_EDGE__OWNER = DNESTED_EDGE__OWNER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DCOMPARTMENT_EDGE__NAME = DNESTED_EDGE__NAME;

	/**
	 * The feature id for the '<em><b>Semantic Role</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DCOMPARTMENT_EDGE__SEMANTIC_ROLE = DNESTED_EDGE__SEMANTIC_ROLE;

	/**
	 * The feature id for the '<em><b>Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DCOMPARTMENT_EDGE__ICON = DNESTED_EDGE__ICON;

	/**
	 * The feature id for the '<em><b>Graph</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DCOMPARTMENT_EDGE__GRAPH = DNESTED_EDGE__GRAPH;

	/**
	 * The feature id for the '<em><b>Abztract</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DCOMPARTMENT_EDGE__ABZTRACT = DNESTED_EDGE__ABZTRACT;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DCOMPARTMENT_EDGE__TARGET = DNESTED_EDGE__TARGET;

	/**
	 * The feature id for the '<em><b>Target Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DCOMPARTMENT_EDGE__TARGET_REFERENCE = DNESTED_EDGE__TARGET_REFERENCE;

	/**
	 * The feature id for the '<em><b>Propagated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DCOMPARTMENT_EDGE__PROPAGATED = DNESTED_EDGE__PROPAGATED;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DCOMPARTMENT_EDGE__SOURCE = DNESTED_EDGE__SOURCE;

	/**
	 * The feature id for the '<em><b>Partition Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DCOMPARTMENT_EDGE__PARTITION_NAME = DNESTED_EDGE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Depth</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DCOMPARTMENT_EDGE__DEPTH = DNESTED_EDGE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>DCompartment Edge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DCOMPARTMENT_EDGE_FEATURE_COUNT = DNESTED_EDGE_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>DCompartment Edge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DCOMPARTMENT_EDGE_OPERATION_COUNT = DNESTED_EDGE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.diagraph.impl.DPointOfViewImpl <em>DPoint Of View</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.diagraph.impl.DPointOfViewImpl
	 * @see org.isoe.diagraph.diagraph.impl.DiagraphPackageImpl#getDPointOfView()
	 * @generated
	 */
	int DPOINT_OF_VIEW = 11;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DPOINT_OF_VIEW__NAME = DNODE__NAME;

	/**
	 * The feature id for the '<em><b>Semantic Role</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DPOINT_OF_VIEW__SEMANTIC_ROLE = DNODE__SEMANTIC_ROLE;

	/**
	 * The feature id for the '<em><b>Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DPOINT_OF_VIEW__ICON = DNODE__ICON;

	/**
	 * The feature id for the '<em><b>Graph</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DPOINT_OF_VIEW__GRAPH = DNODE__GRAPH;

	/**
	 * The feature id for the '<em><b>Abztract</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DPOINT_OF_VIEW__ABZTRACT = DNODE__ABZTRACT;

	/**
	 * The feature id for the '<em><b>EClaz</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DPOINT_OF_VIEW__ECLAZ = DNODE__ECLAZ;

	/**
	 * The feature id for the '<em><b>Dlabels</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DPOINT_OF_VIEW__DLABELS = DNODE__DLABELS;

	/**
	 * The feature id for the '<em><b>Labls</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DPOINT_OF_VIEW__LABLS = DNODE__LABLS;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DPOINT_OF_VIEW__EXPRESSION = DNODE__EXPRESSION;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DPOINT_OF_VIEW__OWNER = DNODE__OWNER;

	/**
	 * The feature id for the '<em><b>View Navigation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DPOINT_OF_VIEW__VIEW_NAVIGATION = DNODE__VIEW_NAVIGATION;

	/**
	 * The feature id for the '<em><b>Shape</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DPOINT_OF_VIEW__SHAPE = DNODE__SHAPE;

	/**
	 * The feature id for the '<em><b>Layout</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DPOINT_OF_VIEW__LAYOUT = DNODE__LAYOUT;

	/**
	 * The feature id for the '<em><b>Navigation Link</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DPOINT_OF_VIEW__NAVIGATION_LINK = DNODE__NAVIGATION_LINK;

	/**
	 * The feature id for the '<em><b>Containments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DPOINT_OF_VIEW__CONTAINMENTS = DNODE__CONTAINMENTS;

	/**
	 * The number of structural features of the '<em>DPoint Of View</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DPOINT_OF_VIEW_FEATURE_COUNT = DNODE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>DPoint Of View</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DPOINT_OF_VIEW_OPERATION_COUNT = DNODE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.diagraph.impl.DViewNavigationImpl <em>DView Navigation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.diagraph.impl.DViewNavigationImpl
	 * @see org.isoe.diagraph.diagraph.impl.DiagraphPackageImpl#getDViewNavigation()
	 * @generated
	 */
	int DVIEW_NAVIGATION = 12;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DVIEW_NAVIGATION__ID = 0;

	/**
	 * The feature id for the '<em><b>Navigation Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DVIEW_NAVIGATION__NAVIGATION_TARGET = 1;

	/**
	 * The feature id for the '<em><b>Navigation Source</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DVIEW_NAVIGATION__NAVIGATION_SOURCE = 2;

	/**
	 * The number of structural features of the '<em>DView Navigation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DVIEW_NAVIGATION_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>DView Navigation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DVIEW_NAVIGATION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.diagraph.impl.DAffixedEdgeImpl <em>DAffixed Edge</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.diagraph.impl.DAffixedEdgeImpl
	 * @see org.isoe.diagraph.diagraph.impl.DiagraphPackageImpl#getDAffixedEdge()
	 * @generated
	 */
	int DAFFIXED_EDGE = 13;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DAFFIXED_EDGE__OWNER = DNESTED_EDGE__OWNER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DAFFIXED_EDGE__NAME = DNESTED_EDGE__NAME;

	/**
	 * The feature id for the '<em><b>Semantic Role</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DAFFIXED_EDGE__SEMANTIC_ROLE = DNESTED_EDGE__SEMANTIC_ROLE;

	/**
	 * The feature id for the '<em><b>Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DAFFIXED_EDGE__ICON = DNESTED_EDGE__ICON;

	/**
	 * The feature id for the '<em><b>Graph</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DAFFIXED_EDGE__GRAPH = DNESTED_EDGE__GRAPH;

	/**
	 * The feature id for the '<em><b>Abztract</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DAFFIXED_EDGE__ABZTRACT = DNESTED_EDGE__ABZTRACT;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DAFFIXED_EDGE__TARGET = DNESTED_EDGE__TARGET;

	/**
	 * The feature id for the '<em><b>Target Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DAFFIXED_EDGE__TARGET_REFERENCE = DNESTED_EDGE__TARGET_REFERENCE;

	/**
	 * The feature id for the '<em><b>Propagated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DAFFIXED_EDGE__PROPAGATED = DNESTED_EDGE__PROPAGATED;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DAFFIXED_EDGE__SOURCE = DNESTED_EDGE__SOURCE;

	/**
	 * The number of structural features of the '<em>DAffixed Edge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DAFFIXED_EDGE_FEATURE_COUNT = DNESTED_EDGE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>DAffixed Edge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DAFFIXED_EDGE_OPERATION_COUNT = DNESTED_EDGE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.diagraph.impl.DLabelImpl <em>DLabel</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.diagraph.impl.DLabelImpl
	 * @see org.isoe.diagraph.diagraph.impl.DiagraphPackageImpl#getDLabel()
	 * @generated
	 */
	int DLABEL = 14;

	/**
	 * The feature id for the '<em><b>Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLABEL__ATTRIBUTE = 0;

	/**
	 * The feature id for the '<em><b>Propagated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLABEL__PROPAGATED = 1;

	/**
	 * The feature id for the '<em><b>Inferred</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLABEL__INFERRED = 2;

	/**
	 * The feature id for the '<em><b>Abztract</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLABEL__ABZTRACT = 3;

	/**
	 * The number of structural features of the '<em>DLabel</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLABEL_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>DLabel</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLABEL_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.diagraph.impl.DNavigationEdgeImpl <em>DNavigation Edge</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.diagraph.impl.DNavigationEdgeImpl
	 * @see org.isoe.diagraph.diagraph.impl.DiagraphPackageImpl#getDNavigationEdge()
	 * @generated
	 */
	int DNAVIGATION_EDGE = 16;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNAVIGATION_EDGE__NAME = DSIMPLE_EDGE__NAME;

	/**
	 * The feature id for the '<em><b>Semantic Role</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNAVIGATION_EDGE__SEMANTIC_ROLE = DSIMPLE_EDGE__SEMANTIC_ROLE;

	/**
	 * The feature id for the '<em><b>Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNAVIGATION_EDGE__ICON = DSIMPLE_EDGE__ICON;

	/**
	 * The feature id for the '<em><b>Graph</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNAVIGATION_EDGE__GRAPH = DSIMPLE_EDGE__GRAPH;

	/**
	 * The feature id for the '<em><b>Abztract</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNAVIGATION_EDGE__ABZTRACT = DSIMPLE_EDGE__ABZTRACT;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNAVIGATION_EDGE__TARGET = DSIMPLE_EDGE__TARGET;

	/**
	 * The feature id for the '<em><b>Target Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNAVIGATION_EDGE__TARGET_REFERENCE = DSIMPLE_EDGE__TARGET_REFERENCE;

	/**
	 * The feature id for the '<em><b>Propagated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNAVIGATION_EDGE__PROPAGATED = DSIMPLE_EDGE__PROPAGATED;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNAVIGATION_EDGE__SOURCE = DSIMPLE_EDGE__SOURCE;

	/**
	 * The number of structural features of the '<em>DNavigation Edge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNAVIGATION_EDGE_FEATURE_COUNT = DSIMPLE_EDGE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>DNavigation Edge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNAVIGATION_EDGE_OPERATION_COUNT = DSIMPLE_EDGE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.diagraph.impl.DGenericImpl <em>DGeneric</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.diagraph.impl.DGenericImpl
	 * @see org.isoe.diagraph.diagraph.impl.DiagraphPackageImpl#getDGeneric()
	 * @generated
	 */
	int DGENERIC = 17;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DGENERIC__NAME = DLABELED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Semantic Role</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DGENERIC__SEMANTIC_ROLE = DLABELED_ELEMENT__SEMANTIC_ROLE;

	/**
	 * The feature id for the '<em><b>Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DGENERIC__ICON = DLABELED_ELEMENT__ICON;

	/**
	 * The feature id for the '<em><b>Graph</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DGENERIC__GRAPH = DLABELED_ELEMENT__GRAPH;

	/**
	 * The feature id for the '<em><b>Abztract</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DGENERIC__ABZTRACT = DLABELED_ELEMENT__ABZTRACT;

	/**
	 * The feature id for the '<em><b>EClaz</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DGENERIC__ECLAZ = DLABELED_ELEMENT__ECLAZ;

	/**
	 * The feature id for the '<em><b>Dlabels</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DGENERIC__DLABELS = DLABELED_ELEMENT__DLABELS;

	/**
	 * The feature id for the '<em><b>Labls</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DGENERIC__LABLS = DLABELED_ELEMENT__LABLS;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DGENERIC__EXPRESSION = DLABELED_ELEMENT__EXPRESSION;

	/**
	 * The number of structural features of the '<em>DGeneric</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DGENERIC_FEATURE_COUNT = DLABELED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>DGeneric</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DGENERIC_OPERATION_COUNT = DLABELED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.diagraph.impl.DContainmentImpl <em>DContainment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.diagraph.impl.DContainmentImpl
	 * @see org.isoe.diagraph.diagraph.impl.DiagraphPackageImpl#getDContainment()
	 * @generated
	 */
	int DCONTAINMENT = 18;

	/**
	 * The feature id for the '<em><b>Node</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DCONTAINMENT__NODE = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DCONTAINMENT__NAME = 1;

	/**
	 * The feature id for the '<em><b>Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DCONTAINMENT__EDGES = 2;

	/**
	 * The number of structural features of the '<em>DContainment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DCONTAINMENT_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>DContainment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DCONTAINMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.diagraph.DShape <em>DShape</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.diagraph.DShape
	 * @see org.isoe.diagraph.diagraph.impl.DiagraphPackageImpl#getDShape()
	 * @generated
	 */
	int DSHAPE = 20;

	/**
	 * The meta object id for the '<em>DGraph Handler</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.diagraph.helpers.IGraphHandler
	 * @see org.isoe.diagraph.diagraph.impl.DiagraphPackageImpl#getDGraphHandler()
	 * @generated
	 */
	int DGRAPH_HANDLER = 21;


	/**
	 * Returns the meta object for class '{@link org.isoe.diagraph.diagraph.DEdge <em>DEdge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>DEdge</em>'.
	 * @see org.isoe.diagraph.diagraph.DEdge
	 * @generated
	 */
	EClass getDEdge();

	/**
	 * Returns the meta object for the reference '{@link org.isoe.diagraph.diagraph.DEdge#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see org.isoe.diagraph.diagraph.DEdge#getTarget()
	 * @see #getDEdge()
	 * @generated
	 */
	EReference getDEdge_Target();

	/**
	 * Returns the meta object for the reference '{@link org.isoe.diagraph.diagraph.DEdge#getTargetReference <em>Target Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target Reference</em>'.
	 * @see org.isoe.diagraph.diagraph.DEdge#getTargetReference()
	 * @see #getDEdge()
	 * @generated
	 */
	EReference getDEdge_TargetReference();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.diagraph.DEdge#isPropagated <em>Propagated</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Propagated</em>'.
	 * @see org.isoe.diagraph.diagraph.DEdge#isPropagated()
	 * @see #getDEdge()
	 * @generated
	 */
	EAttribute getDEdge_Propagated();

	/**
	 * Returns the meta object for class '{@link org.isoe.diagraph.diagraph.DGraphElement <em>DGraph Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>DGraph Element</em>'.
	 * @see org.isoe.diagraph.diagraph.DGraphElement
	 * @generated
	 */
	EClass getDGraphElement();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.diagraph.DGraphElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.isoe.diagraph.diagraph.DGraphElement#getName()
	 * @see #getDGraphElement()
	 * @generated
	 */
	EAttribute getDGraphElement_Name();

	/**
	 * Returns the meta object for the reference '{@link org.isoe.diagraph.diagraph.DGraphElement#getSemanticRole <em>Semantic Role</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Semantic Role</em>'.
	 * @see org.isoe.diagraph.diagraph.DGraphElement#getSemanticRole()
	 * @see #getDGraphElement()
	 * @generated
	 */
	EReference getDGraphElement_SemanticRole();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.diagraph.DGraphElement#getIcon <em>Icon</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Icon</em>'.
	 * @see org.isoe.diagraph.diagraph.DGraphElement#getIcon()
	 * @see #getDGraphElement()
	 * @generated
	 */
	EAttribute getDGraphElement_Icon();

	/**
	 * Returns the meta object for the container reference '{@link org.isoe.diagraph.diagraph.DGraphElement#getGraph <em>Graph</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Graph</em>'.
	 * @see org.isoe.diagraph.diagraph.DGraphElement#getGraph()
	 * @see #getDGraphElement()
	 * @generated
	 */
	EReference getDGraphElement_Graph();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.diagraph.DGraphElement#isAbztract <em>Abztract</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Abztract</em>'.
	 * @see org.isoe.diagraph.diagraph.DGraphElement#isAbztract()
	 * @see #getDGraphElement()
	 * @generated
	 */
	EAttribute getDGraphElement_Abztract();

	/**
	 * Returns the meta object for class '{@link org.isoe.diagraph.diagraph.DNode <em>DNode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>DNode</em>'.
	 * @see org.isoe.diagraph.diagraph.DNode
	 * @generated
	 */
	EClass getDNode();

	/**
	 * Returns the meta object for the containment reference '{@link org.isoe.diagraph.diagraph.DNode#getViewNavigation <em>View Navigation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>View Navigation</em>'.
	 * @see org.isoe.diagraph.diagraph.DNode#getViewNavigation()
	 * @see #getDNode()
	 * @generated
	 */
	EReference getDNode_ViewNavigation();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.diagraph.DNode#getShape <em>Shape</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Shape</em>'.
	 * @see org.isoe.diagraph.diagraph.DNode#getShape()
	 * @see #getDNode()
	 * @generated
	 */
	EAttribute getDNode_Shape();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.diagraph.DNode#isLayout <em>Layout</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Layout</em>'.
	 * @see org.isoe.diagraph.diagraph.DNode#isLayout()
	 * @see #getDNode()
	 * @generated
	 */
	EAttribute getDNode_Layout();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.diagraph.DNode#getNavigationLink <em>Navigation Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Navigation Link</em>'.
	 * @see org.isoe.diagraph.diagraph.DNode#getNavigationLink()
	 * @see #getDNode()
	 * @generated
	 */
	EAttribute getDNode_NavigationLink();

	/**
	 * Returns the meta object for the containment reference list '{@link org.isoe.diagraph.diagraph.DNode#getContainments <em>Containments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Containments</em>'.
	 * @see org.isoe.diagraph.diagraph.DNode#getContainments()
	 * @see #getDNode()
	 * @generated
	 */
	EReference getDNode_Containments();

	/**
	 * Returns the meta object for class '{@link org.isoe.diagraph.diagraph.DLabeledEdge <em>DLabeled Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>DLabeled Edge</em>'.
	 * @see org.isoe.diagraph.diagraph.DLabeledEdge
	 * @generated
	 */
	EClass getDLabeledEdge();

	/**
	 * Returns the meta object for the reference '{@link org.isoe.diagraph.diagraph.DLabeledEdge#getSourceReference <em>Source Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source Reference</em>'.
	 * @see org.isoe.diagraph.diagraph.DLabeledEdge#getSourceReference()
	 * @see #getDLabeledEdge()
	 * @generated
	 */
	EReference getDLabeledEdge_SourceReference();

	/**
	 * Returns the meta object for class '{@link org.isoe.diagraph.diagraph.DReference <em>DReference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>DReference</em>'.
	 * @see org.isoe.diagraph.diagraph.DReference
	 * @generated
	 */
	EClass getDReference();

	/**
	 * Returns the meta object for class '{@link org.isoe.diagraph.diagraph.DNestedEdge <em>DNested Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>DNested Edge</em>'.
	 * @see org.isoe.diagraph.diagraph.DNestedEdge
	 * @generated
	 */
	EClass getDNestedEdge();

	/**
	 * Returns the meta object for the reference '{@link org.isoe.diagraph.diagraph.DNestedEdge#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see org.isoe.diagraph.diagraph.DNestedEdge#getSource()
	 * @see #getDNestedEdge()
	 * @generated
	 */
	EReference getDNestedEdge_Source();

	/**
	 * Returns the meta object for class '{@link org.isoe.diagraph.diagraph.DGraph <em>DGraph</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>DGraph</em>'.
	 * @see org.isoe.diagraph.diagraph.DGraph
	 * @generated
	 */
	EClass getDGraph();

	/**
	 * Returns the meta object for the containment reference list '{@link org.isoe.diagraph.diagraph.DGraph#getElements <em>Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Elements</em>'.
	 * @see org.isoe.diagraph.diagraph.DGraph#getElements()
	 * @see #getDGraph()
	 * @generated
	 */
	EReference getDGraph_Elements();

	/**
	 * Returns the meta object for the reference '{@link org.isoe.diagraph.diagraph.DGraph#getPointOfView <em>Point Of View</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Point Of View</em>'.
	 * @see org.isoe.diagraph.diagraph.DGraph#getPointOfView()
	 * @see #getDGraph()
	 * @generated
	 */
	EReference getDGraph_PointOfView();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.diagraph.DGraph#getViewName <em>View Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>View Name</em>'.
	 * @see org.isoe.diagraph.diagraph.DGraph#getViewName()
	 * @see #getDGraph()
	 * @generated
	 */
	EAttribute getDGraph_ViewName();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.diagraph.DGraph#getFacade1 <em>Facade1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Facade1</em>'.
	 * @see org.isoe.diagraph.diagraph.DGraph#getFacade1()
	 * @see #getDGraph()
	 * @generated
	 */
	EAttribute getDGraph_Facade1();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.diagraph.DGraph#getFacade2 <em>Facade2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Facade2</em>'.
	 * @see org.isoe.diagraph.diagraph.DGraph#getFacade2()
	 * @see #getDGraph()
	 * @generated
	 */
	EAttribute getDGraph_Facade2();

	/**
	 * Returns the meta object for class '{@link org.isoe.diagraph.diagraph.DLabeledElement <em>DLabeled Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>DLabeled Element</em>'.
	 * @see org.isoe.diagraph.diagraph.DLabeledElement
	 * @generated
	 */
	EClass getDLabeledElement();

	/**
	 * Returns the meta object for the reference '{@link org.isoe.diagraph.diagraph.DLabeledElement#getEClaz <em>EClaz</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>EClaz</em>'.
	 * @see org.isoe.diagraph.diagraph.DLabeledElement#getEClaz()
	 * @see #getDLabeledElement()
	 * @generated
	 */
	EReference getDLabeledElement_EClaz();

	/**
	 * Returns the meta object for the containment reference list '{@link org.isoe.diagraph.diagraph.DLabeledElement#getDlabels <em>Dlabels</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Dlabels</em>'.
	 * @see org.isoe.diagraph.diagraph.DLabeledElement#getDlabels()
	 * @see #getDLabeledElement()
	 * @generated
	 */
	EReference getDLabeledElement_Dlabels();

	/**
	 * Returns the meta object for the attribute list '{@link org.isoe.diagraph.diagraph.DLabeledElement#getLabls <em>Labls</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Labls</em>'.
	 * @see org.isoe.diagraph.diagraph.DLabeledElement#getLabls()
	 * @see #getDLabeledElement()
	 * @generated
	 */
	EAttribute getDLabeledElement_Labls();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.diagraph.DLabeledElement#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Expression</em>'.
	 * @see org.isoe.diagraph.diagraph.DLabeledElement#getExpression()
	 * @see #getDLabeledElement()
	 * @generated
	 */
	EAttribute getDLabeledElement_Expression();

	/**
	 * Returns the meta object for class '{@link org.isoe.diagraph.diagraph.DOwnedElement <em>DOwned Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>DOwned Element</em>'.
	 * @see org.isoe.diagraph.diagraph.DOwnedElement
	 * @generated
	 */
	EClass getDOwnedElement();

	/**
	 * Returns the meta object for the reference '{@link org.isoe.diagraph.diagraph.DOwnedElement#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Owner</em>'.
	 * @see org.isoe.diagraph.diagraph.DOwnedElement#getOwner()
	 * @see #getDOwnedElement()
	 * @generated
	 */
	EReference getDOwnedElement_Owner();

	/**
	 * Returns the meta object for class '{@link org.isoe.diagraph.diagraph.DOwnedEdge <em>DOwned Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>DOwned Edge</em>'.
	 * @see org.isoe.diagraph.diagraph.DOwnedEdge
	 * @generated
	 */
	EClass getDOwnedEdge();

	/**
	 * Returns the meta object for class '{@link org.isoe.diagraph.diagraph.DCompartmentEdge <em>DCompartment Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>DCompartment Edge</em>'.
	 * @see org.isoe.diagraph.diagraph.DCompartmentEdge
	 * @generated
	 */
	EClass getDCompartmentEdge();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.diagraph.DCompartmentEdge#getPartitionName <em>Partition Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Partition Name</em>'.
	 * @see org.isoe.diagraph.diagraph.DCompartmentEdge#getPartitionName()
	 * @see #getDCompartmentEdge()
	 * @generated
	 */
	EAttribute getDCompartmentEdge_PartitionName();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.diagraph.DCompartmentEdge#getDepth <em>Depth</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Depth</em>'.
	 * @see org.isoe.diagraph.diagraph.DCompartmentEdge#getDepth()
	 * @see #getDCompartmentEdge()
	 * @generated
	 */
	EAttribute getDCompartmentEdge_Depth();

	/**
	 * Returns the meta object for class '{@link org.isoe.diagraph.diagraph.DPointOfView <em>DPoint Of View</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>DPoint Of View</em>'.
	 * @see org.isoe.diagraph.diagraph.DPointOfView
	 * @generated
	 */
	EClass getDPointOfView();

	/**
	 * Returns the meta object for class '{@link org.isoe.diagraph.diagraph.DViewNavigation <em>DView Navigation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>DView Navigation</em>'.
	 * @see org.isoe.diagraph.diagraph.DViewNavigation
	 * @generated
	 */
	EClass getDViewNavigation();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.diagraph.DViewNavigation#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.isoe.diagraph.diagraph.DViewNavigation#getId()
	 * @see #getDViewNavigation()
	 * @generated
	 */
	EAttribute getDViewNavigation_Id();

	/**
	 * Returns the meta object for the reference '{@link org.isoe.diagraph.diagraph.DViewNavigation#getNavigationTarget <em>Navigation Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Navigation Target</em>'.
	 * @see org.isoe.diagraph.diagraph.DViewNavigation#getNavigationTarget()
	 * @see #getDViewNavigation()
	 * @generated
	 */
	EReference getDViewNavigation_NavigationTarget();

	/**
	 * Returns the meta object for the container reference '{@link org.isoe.diagraph.diagraph.DViewNavigation#getNavigationSource <em>Navigation Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Navigation Source</em>'.
	 * @see org.isoe.diagraph.diagraph.DViewNavigation#getNavigationSource()
	 * @see #getDViewNavigation()
	 * @generated
	 */
	EReference getDViewNavigation_NavigationSource();

	/**
	 * Returns the meta object for class '{@link org.isoe.diagraph.diagraph.DAffixedEdge <em>DAffixed Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>DAffixed Edge</em>'.
	 * @see org.isoe.diagraph.diagraph.DAffixedEdge
	 * @generated
	 */
	EClass getDAffixedEdge();

	/**
	 * Returns the meta object for class '{@link org.isoe.diagraph.diagraph.DLabel <em>DLabel</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>DLabel</em>'.
	 * @see org.isoe.diagraph.diagraph.DLabel
	 * @generated
	 */
	EClass getDLabel();

	/**
	 * Returns the meta object for the reference '{@link org.isoe.diagraph.diagraph.DLabel#getAttribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Attribute</em>'.
	 * @see org.isoe.diagraph.diagraph.DLabel#getAttribute()
	 * @see #getDLabel()
	 * @generated
	 */
	EReference getDLabel_Attribute();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.diagraph.DLabel#isPropagated <em>Propagated</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Propagated</em>'.
	 * @see org.isoe.diagraph.diagraph.DLabel#isPropagated()
	 * @see #getDLabel()
	 * @generated
	 */
	EAttribute getDLabel_Propagated();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.diagraph.DLabel#isInferred <em>Inferred</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Inferred</em>'.
	 * @see org.isoe.diagraph.diagraph.DLabel#isInferred()
	 * @see #getDLabel()
	 * @generated
	 */
	EAttribute getDLabel_Inferred();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.diagraph.DLabel#isAbztract <em>Abztract</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Abztract</em>'.
	 * @see org.isoe.diagraph.diagraph.DLabel#isAbztract()
	 * @see #getDLabel()
	 * @generated
	 */
	EAttribute getDLabel_Abztract();

	/**
	 * Returns the meta object for class '{@link org.isoe.diagraph.diagraph.DLineEdge <em>DLine Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>DLine Edge</em>'.
	 * @see org.isoe.diagraph.diagraph.DLineEdge
	 * @generated
	 */
	EClass getDLineEdge();

	/**
	 * Returns the meta object for the attribute list '{@link org.isoe.diagraph.diagraph.DLineEdge#getArrows <em>Arrows</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Arrows</em>'.
	 * @see org.isoe.diagraph.diagraph.DLineEdge#getArrows()
	 * @see #getDLineEdge()
	 * @generated
	 */
	EAttribute getDLineEdge_Arrows();

	/**
	 * Returns the meta object for class '{@link org.isoe.diagraph.diagraph.DNavigationEdge <em>DNavigation Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>DNavigation Edge</em>'.
	 * @see org.isoe.diagraph.diagraph.DNavigationEdge
	 * @generated
	 */
	EClass getDNavigationEdge();

	/**
	 * Returns the meta object for class '{@link org.isoe.diagraph.diagraph.DGeneric <em>DGeneric</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>DGeneric</em>'.
	 * @see org.isoe.diagraph.diagraph.DGeneric
	 * @generated
	 */
	EClass getDGeneric();

	/**
	 * Returns the meta object for class '{@link org.isoe.diagraph.diagraph.DContainment <em>DContainment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>DContainment</em>'.
	 * @see org.isoe.diagraph.diagraph.DContainment
	 * @generated
	 */
	EClass getDContainment();

	/**
	 * Returns the meta object for the container reference '{@link org.isoe.diagraph.diagraph.DContainment#getNode <em>Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Node</em>'.
	 * @see org.isoe.diagraph.diagraph.DContainment#getNode()
	 * @see #getDContainment()
	 * @generated
	 */
	EReference getDContainment_Node();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.diagraph.DContainment#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.isoe.diagraph.diagraph.DContainment#getName()
	 * @see #getDContainment()
	 * @generated
	 */
	EAttribute getDContainment_Name();

	/**
	 * Returns the meta object for the reference list '{@link org.isoe.diagraph.diagraph.DContainment#getEdges <em>Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Edges</em>'.
	 * @see org.isoe.diagraph.diagraph.DContainment#getEdges()
	 * @see #getDContainment()
	 * @generated
	 */
	EReference getDContainment_Edges();

	/**
	 * Returns the meta object for class '{@link org.isoe.diagraph.diagraph.DSimpleEdge <em>DSimple Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>DSimple Edge</em>'.
	 * @see org.isoe.diagraph.diagraph.DSimpleEdge
	 * @generated
	 */
	EClass getDSimpleEdge();

	/**
	 * Returns the meta object for the reference '{@link org.isoe.diagraph.diagraph.DSimpleEdge#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see org.isoe.diagraph.diagraph.DSimpleEdge#getSource()
	 * @see #getDSimpleEdge()
	 * @generated
	 */
	EReference getDSimpleEdge_Source();

	/**
	 * Returns the meta object for enum '{@link org.isoe.diagraph.diagraph.DShape <em>DShape</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>DShape</em>'.
	 * @see org.isoe.diagraph.diagraph.DShape
	 * @generated
	 */
	EEnum getDShape();

	/**
	 * Returns the meta object for data type '{@link org.isoe.diagraph.diagraph.helpers.IGraphHandler <em>DGraph Handler</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>DGraph Handler</em>'.
	 * @see org.isoe.diagraph.diagraph.helpers.IGraphHandler
	 * @model instanceClass="org.isoe.diagraph.diagraph.helpers.IGraphHandler"
	 * @generated
	 */
	EDataType getDGraphHandler();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	DiagraphFactory getDiagraphFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.isoe.diagraph.diagraph.impl.DEdgeImpl <em>DEdge</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.diagraph.impl.DEdgeImpl
		 * @see org.isoe.diagraph.diagraph.impl.DiagraphPackageImpl#getDEdge()
		 * @generated
		 */
		EClass DEDGE = eINSTANCE.getDEdge();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEDGE__TARGET = eINSTANCE.getDEdge_Target();

		/**
		 * The meta object literal for the '<em><b>Target Reference</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEDGE__TARGET_REFERENCE = eINSTANCE.getDEdge_TargetReference();

		/**
		 * The meta object literal for the '<em><b>Propagated</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEDGE__PROPAGATED = eINSTANCE.getDEdge_Propagated();

		/**
		 * The meta object literal for the '{@link org.isoe.diagraph.diagraph.impl.DGraphElementImpl <em>DGraph Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.diagraph.impl.DGraphElementImpl
		 * @see org.isoe.diagraph.diagraph.impl.DiagraphPackageImpl#getDGraphElement()
		 * @generated
		 */
		EClass DGRAPH_ELEMENT = eINSTANCE.getDGraphElement();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DGRAPH_ELEMENT__NAME = eINSTANCE.getDGraphElement_Name();

		/**
		 * The meta object literal for the '<em><b>Semantic Role</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DGRAPH_ELEMENT__SEMANTIC_ROLE = eINSTANCE.getDGraphElement_SemanticRole();

		/**
		 * The meta object literal for the '<em><b>Icon</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DGRAPH_ELEMENT__ICON = eINSTANCE.getDGraphElement_Icon();

		/**
		 * The meta object literal for the '<em><b>Graph</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DGRAPH_ELEMENT__GRAPH = eINSTANCE.getDGraphElement_Graph();

		/**
		 * The meta object literal for the '<em><b>Abztract</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DGRAPH_ELEMENT__ABZTRACT = eINSTANCE.getDGraphElement_Abztract();

		/**
		 * The meta object literal for the '{@link org.isoe.diagraph.diagraph.impl.DNodeImpl <em>DNode</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.diagraph.impl.DNodeImpl
		 * @see org.isoe.diagraph.diagraph.impl.DiagraphPackageImpl#getDNode()
		 * @generated
		 */
		EClass DNODE = eINSTANCE.getDNode();

		/**
		 * The meta object literal for the '<em><b>View Navigation</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DNODE__VIEW_NAVIGATION = eINSTANCE.getDNode_ViewNavigation();

		/**
		 * The meta object literal for the '<em><b>Shape</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DNODE__SHAPE = eINSTANCE.getDNode_Shape();

		/**
		 * The meta object literal for the '<em><b>Layout</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DNODE__LAYOUT = eINSTANCE.getDNode_Layout();

		/**
		 * The meta object literal for the '<em><b>Navigation Link</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DNODE__NAVIGATION_LINK = eINSTANCE.getDNode_NavigationLink();

		/**
		 * The meta object literal for the '<em><b>Containments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DNODE__CONTAINMENTS = eINSTANCE.getDNode_Containments();

		/**
		 * The meta object literal for the '{@link org.isoe.diagraph.diagraph.impl.DLabeledEdgeImpl <em>DLabeled Edge</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.diagraph.impl.DLabeledEdgeImpl
		 * @see org.isoe.diagraph.diagraph.impl.DiagraphPackageImpl#getDLabeledEdge()
		 * @generated
		 */
		EClass DLABELED_EDGE = eINSTANCE.getDLabeledEdge();

		/**
		 * The meta object literal for the '<em><b>Source Reference</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DLABELED_EDGE__SOURCE_REFERENCE = eINSTANCE.getDLabeledEdge_SourceReference();

		/**
		 * The meta object literal for the '{@link org.isoe.diagraph.diagraph.impl.DReferenceImpl <em>DReference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.diagraph.impl.DReferenceImpl
		 * @see org.isoe.diagraph.diagraph.impl.DiagraphPackageImpl#getDReference()
		 * @generated
		 */
		EClass DREFERENCE = eINSTANCE.getDReference();

		/**
		 * The meta object literal for the '{@link org.isoe.diagraph.diagraph.impl.DNestedEdgeImpl <em>DNested Edge</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.diagraph.impl.DNestedEdgeImpl
		 * @see org.isoe.diagraph.diagraph.impl.DiagraphPackageImpl#getDNestedEdge()
		 * @generated
		 */
		EClass DNESTED_EDGE = eINSTANCE.getDNestedEdge();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DNESTED_EDGE__SOURCE = eINSTANCE.getDNestedEdge_Source();

		/**
		 * The meta object literal for the '{@link org.isoe.diagraph.diagraph.impl.DGraphImpl <em>DGraph</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.diagraph.impl.DGraphImpl
		 * @see org.isoe.diagraph.diagraph.impl.DiagraphPackageImpl#getDGraph()
		 * @generated
		 */
		EClass DGRAPH = eINSTANCE.getDGraph();

		/**
		 * The meta object literal for the '<em><b>Elements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DGRAPH__ELEMENTS = eINSTANCE.getDGraph_Elements();

		/**
		 * The meta object literal for the '<em><b>Point Of View</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DGRAPH__POINT_OF_VIEW = eINSTANCE.getDGraph_PointOfView();

		/**
		 * The meta object literal for the '<em><b>View Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DGRAPH__VIEW_NAME = eINSTANCE.getDGraph_ViewName();

		/**
		 * The meta object literal for the '<em><b>Facade1</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DGRAPH__FACADE1 = eINSTANCE.getDGraph_Facade1();

		/**
		 * The meta object literal for the '<em><b>Facade2</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DGRAPH__FACADE2 = eINSTANCE.getDGraph_Facade2();

		/**
		 * The meta object literal for the '{@link org.isoe.diagraph.diagraph.impl.DLabeledElementImpl <em>DLabeled Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.diagraph.impl.DLabeledElementImpl
		 * @see org.isoe.diagraph.diagraph.impl.DiagraphPackageImpl#getDLabeledElement()
		 * @generated
		 */
		EClass DLABELED_ELEMENT = eINSTANCE.getDLabeledElement();

		/**
		 * The meta object literal for the '<em><b>EClaz</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DLABELED_ELEMENT__ECLAZ = eINSTANCE.getDLabeledElement_EClaz();

		/**
		 * The meta object literal for the '<em><b>Dlabels</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DLABELED_ELEMENT__DLABELS = eINSTANCE.getDLabeledElement_Dlabels();

		/**
		 * The meta object literal for the '<em><b>Labls</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DLABELED_ELEMENT__LABLS = eINSTANCE.getDLabeledElement_Labls();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DLABELED_ELEMENT__EXPRESSION = eINSTANCE.getDLabeledElement_Expression();

		/**
		 * The meta object literal for the '{@link org.isoe.diagraph.diagraph.impl.DOwnedElementImpl <em>DOwned Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.diagraph.impl.DOwnedElementImpl
		 * @see org.isoe.diagraph.diagraph.impl.DiagraphPackageImpl#getDOwnedElement()
		 * @generated
		 */
		EClass DOWNED_ELEMENT = eINSTANCE.getDOwnedElement();

		/**
		 * The meta object literal for the '<em><b>Owner</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOWNED_ELEMENT__OWNER = eINSTANCE.getDOwnedElement_Owner();

		/**
		 * The meta object literal for the '{@link org.isoe.diagraph.diagraph.impl.DOwnedEdgeImpl <em>DOwned Edge</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.diagraph.impl.DOwnedEdgeImpl
		 * @see org.isoe.diagraph.diagraph.impl.DiagraphPackageImpl#getDOwnedEdge()
		 * @generated
		 */
		EClass DOWNED_EDGE = eINSTANCE.getDOwnedEdge();

		/**
		 * The meta object literal for the '{@link org.isoe.diagraph.diagraph.impl.DCompartmentEdgeImpl <em>DCompartment Edge</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.diagraph.impl.DCompartmentEdgeImpl
		 * @see org.isoe.diagraph.diagraph.impl.DiagraphPackageImpl#getDCompartmentEdge()
		 * @generated
		 */
		EClass DCOMPARTMENT_EDGE = eINSTANCE.getDCompartmentEdge();

		/**
		 * The meta object literal for the '<em><b>Partition Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DCOMPARTMENT_EDGE__PARTITION_NAME = eINSTANCE.getDCompartmentEdge_PartitionName();

		/**
		 * The meta object literal for the '<em><b>Depth</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DCOMPARTMENT_EDGE__DEPTH = eINSTANCE.getDCompartmentEdge_Depth();

		/**
		 * The meta object literal for the '{@link org.isoe.diagraph.diagraph.impl.DPointOfViewImpl <em>DPoint Of View</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.diagraph.impl.DPointOfViewImpl
		 * @see org.isoe.diagraph.diagraph.impl.DiagraphPackageImpl#getDPointOfView()
		 * @generated
		 */
		EClass DPOINT_OF_VIEW = eINSTANCE.getDPointOfView();

		/**
		 * The meta object literal for the '{@link org.isoe.diagraph.diagraph.impl.DViewNavigationImpl <em>DView Navigation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.diagraph.impl.DViewNavigationImpl
		 * @see org.isoe.diagraph.diagraph.impl.DiagraphPackageImpl#getDViewNavigation()
		 * @generated
		 */
		EClass DVIEW_NAVIGATION = eINSTANCE.getDViewNavigation();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DVIEW_NAVIGATION__ID = eINSTANCE.getDViewNavigation_Id();

		/**
		 * The meta object literal for the '<em><b>Navigation Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DVIEW_NAVIGATION__NAVIGATION_TARGET = eINSTANCE.getDViewNavigation_NavigationTarget();

		/**
		 * The meta object literal for the '<em><b>Navigation Source</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DVIEW_NAVIGATION__NAVIGATION_SOURCE = eINSTANCE.getDViewNavigation_NavigationSource();

		/**
		 * The meta object literal for the '{@link org.isoe.diagraph.diagraph.impl.DAffixedEdgeImpl <em>DAffixed Edge</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.diagraph.impl.DAffixedEdgeImpl
		 * @see org.isoe.diagraph.diagraph.impl.DiagraphPackageImpl#getDAffixedEdge()
		 * @generated
		 */
		EClass DAFFIXED_EDGE = eINSTANCE.getDAffixedEdge();

		/**
		 * The meta object literal for the '{@link org.isoe.diagraph.diagraph.impl.DLabelImpl <em>DLabel</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.diagraph.impl.DLabelImpl
		 * @see org.isoe.diagraph.diagraph.impl.DiagraphPackageImpl#getDLabel()
		 * @generated
		 */
		EClass DLABEL = eINSTANCE.getDLabel();

		/**
		 * The meta object literal for the '<em><b>Attribute</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DLABEL__ATTRIBUTE = eINSTANCE.getDLabel_Attribute();

		/**
		 * The meta object literal for the '<em><b>Propagated</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DLABEL__PROPAGATED = eINSTANCE.getDLabel_Propagated();

		/**
		 * The meta object literal for the '<em><b>Inferred</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DLABEL__INFERRED = eINSTANCE.getDLabel_Inferred();

		/**
		 * The meta object literal for the '<em><b>Abztract</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DLABEL__ABZTRACT = eINSTANCE.getDLabel_Abztract();

		/**
		 * The meta object literal for the '{@link org.isoe.diagraph.diagraph.impl.DLineEdgeImpl <em>DLine Edge</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.diagraph.impl.DLineEdgeImpl
		 * @see org.isoe.diagraph.diagraph.impl.DiagraphPackageImpl#getDLineEdge()
		 * @generated
		 */
		EClass DLINE_EDGE = eINSTANCE.getDLineEdge();

		/**
		 * The meta object literal for the '<em><b>Arrows</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DLINE_EDGE__ARROWS = eINSTANCE.getDLineEdge_Arrows();

		/**
		 * The meta object literal for the '{@link org.isoe.diagraph.diagraph.impl.DNavigationEdgeImpl <em>DNavigation Edge</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.diagraph.impl.DNavigationEdgeImpl
		 * @see org.isoe.diagraph.diagraph.impl.DiagraphPackageImpl#getDNavigationEdge()
		 * @generated
		 */
		EClass DNAVIGATION_EDGE = eINSTANCE.getDNavigationEdge();

		/**
		 * The meta object literal for the '{@link org.isoe.diagraph.diagraph.impl.DGenericImpl <em>DGeneric</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.diagraph.impl.DGenericImpl
		 * @see org.isoe.diagraph.diagraph.impl.DiagraphPackageImpl#getDGeneric()
		 * @generated
		 */
		EClass DGENERIC = eINSTANCE.getDGeneric();

		/**
		 * The meta object literal for the '{@link org.isoe.diagraph.diagraph.impl.DContainmentImpl <em>DContainment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.diagraph.impl.DContainmentImpl
		 * @see org.isoe.diagraph.diagraph.impl.DiagraphPackageImpl#getDContainment()
		 * @generated
		 */
		EClass DCONTAINMENT = eINSTANCE.getDContainment();

		/**
		 * The meta object literal for the '<em><b>Node</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DCONTAINMENT__NODE = eINSTANCE.getDContainment_Node();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DCONTAINMENT__NAME = eINSTANCE.getDContainment_Name();

		/**
		 * The meta object literal for the '<em><b>Edges</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DCONTAINMENT__EDGES = eINSTANCE.getDContainment_Edges();

		/**
		 * The meta object literal for the '{@link org.isoe.diagraph.diagraph.impl.DSimpleEdgeImpl <em>DSimple Edge</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.diagraph.impl.DSimpleEdgeImpl
		 * @see org.isoe.diagraph.diagraph.impl.DiagraphPackageImpl#getDSimpleEdge()
		 * @generated
		 */
		EClass DSIMPLE_EDGE = eINSTANCE.getDSimpleEdge();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DSIMPLE_EDGE__SOURCE = eINSTANCE.getDSimpleEdge_Source();

		/**
		 * The meta object literal for the '{@link org.isoe.diagraph.diagraph.DShape <em>DShape</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.diagraph.DShape
		 * @see org.isoe.diagraph.diagraph.impl.DiagraphPackageImpl#getDShape()
		 * @generated
		 */
		EEnum DSHAPE = eINSTANCE.getDShape();

		/**
		 * The meta object literal for the '<em>DGraph Handler</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.diagraph.helpers.IGraphHandler
		 * @see org.isoe.diagraph.diagraph.impl.DiagraphPackageImpl#getDGraphHandler()
		 * @generated
		 */
		EDataType DGRAPH_HANDLER = eINSTANCE.getDGraphHandler();

	}

} //DiagraphPackage
