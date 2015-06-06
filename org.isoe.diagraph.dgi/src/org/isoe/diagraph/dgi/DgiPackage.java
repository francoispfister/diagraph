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
package org.isoe.diagraph.dgi;

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
 * @see org.isoe.diagraph.dgi.DgiFactory
 * @model kind="package"
 * @generated
 */
public interface DgiPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "dgi";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://dgi.isoe.org";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "dgi";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DgiPackage eINSTANCE = org.isoe.diagraph.dgi.impl.DgiPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.dgi.impl.DNamedElementImpl <em>DNamed Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.dgi.impl.DNamedElementImpl
	 * @see org.isoe.diagraph.dgi.impl.DgiPackageImpl#getDNamedElement()
	 * @generated
	 */
	int DNAMED_ELEMENT = 10;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNAMED_ELEMENT__NAME = 0;

	/**
	 * The number of structural features of the '<em>DNamed Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNAMED_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.dgi.impl.DLabelledElementImpl <em>DLabelled Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.dgi.impl.DLabelledElementImpl
	 * @see org.isoe.diagraph.dgi.impl.DgiPackageImpl#getDLabelledElement()
	 * @generated
	 */
	int DLABELLED_ELEMENT = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLABELLED_ELEMENT__NAME = DNAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>DLabels</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLABELLED_ELEMENT__DLABELS = DNAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>DLabelled Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLABELLED_ELEMENT_FEATURE_COUNT = DNAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.dgi.impl.DElementImpl <em>DElement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.dgi.impl.DElementImpl
	 * @see org.isoe.diagraph.dgi.impl.DgiPackageImpl#getDElement()
	 * @generated
	 */
	int DELEMENT = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEMENT__NAME = DLABELLED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>DLabels</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEMENT__DLABELS = DLABELLED_ELEMENT__DLABELS;

	/**
	 * The feature id for the '<em><b>EContainment Model Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEMENT__ECONTAINMENT_MODEL_ELEMENT = DLABELLED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>EModel Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEMENT__EMODEL_ELEMENT = DLABELLED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>DLower Elements</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEMENT__DLOWER_ELEMENTS = DLABELLED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>DSuper Elements</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEMENT__DSUPER_ELEMENTS = DLABELLED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>DElement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEMENT_FEATURE_COUNT = DLABELLED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.dgi.impl.DNodeImpl <em>DNode</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.dgi.impl.DNodeImpl
	 * @see org.isoe.diagraph.dgi.impl.DgiPackageImpl#getDNode()
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
	int DNODE__NAME = DELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>DLabels</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE__DLABELS = DELEMENT__DLABELS;

	/**
	 * The feature id for the '<em><b>EContainment Model Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE__ECONTAINMENT_MODEL_ELEMENT = DELEMENT__ECONTAINMENT_MODEL_ELEMENT;

	/**
	 * The feature id for the '<em><b>EModel Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE__EMODEL_ELEMENT = DELEMENT__EMODEL_ELEMENT;

	/**
	 * The feature id for the '<em><b>DLower Elements</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE__DLOWER_ELEMENTS = DELEMENT__DLOWER_ELEMENTS;

	/**
	 * The feature id for the '<em><b>DSuper Elements</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE__DSUPER_ELEMENTS = DELEMENT__DSUPER_ELEMENTS;

	/**
	 * The feature id for the '<em><b>DReferences</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE__DREFERENCES = DELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>DContainments</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE__DCONTAINMENTS = DELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>DLinks</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE__DLINKS = DELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Root</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE__ROOT = DELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Recursive</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE__RECURSIVE = DELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Navigations</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE__NAVIGATIONS = DELEMENT_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>DNode</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE_FEATURE_COUNT = DELEMENT_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.dgi.impl.DLinkImpl <em>DLink</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.dgi.impl.DLinkImpl
	 * @see org.isoe.diagraph.dgi.impl.DgiPackageImpl#getDLink()
	 * @generated
	 */
	int DLINK = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLINK__NAME = DELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>DLabels</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLINK__DLABELS = DELEMENT__DLABELS;

	/**
	 * The feature id for the '<em><b>EContainment Model Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLINK__ECONTAINMENT_MODEL_ELEMENT = DELEMENT__ECONTAINMENT_MODEL_ELEMENT;

	/**
	 * The feature id for the '<em><b>EModel Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLINK__EMODEL_ELEMENT = DELEMENT__EMODEL_ELEMENT;

	/**
	 * The feature id for the '<em><b>DLower Elements</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLINK__DLOWER_ELEMENTS = DELEMENT__DLOWER_ELEMENTS;

	/**
	 * The feature id for the '<em><b>DSuper Elements</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLINK__DSUPER_ELEMENTS = DELEMENT__DSUPER_ELEMENTS;

	/**
	 * The feature id for the '<em><b>DSource Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLINK__DSOURCE_NODE = DELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>DTarget Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLINK__DTARGET_NODE = DELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>ESource Model Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLINK__ESOURCE_MODEL_ELEMENT = DELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>ETarget Model Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLINK__ETARGET_MODEL_ELEMENT = DELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Reversed Source</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLINK__REVERSED_SOURCE = DELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>DLink</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLINK_FEATURE_COUNT = DELEMENT_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.dgi.impl.DEdgeImpl <em>DEdge</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.dgi.impl.DEdgeImpl
	 * @see org.isoe.diagraph.dgi.impl.DgiPackageImpl#getDEdge()
	 * @generated
	 */
	int DEDGE = 9;

	/**
	 * The feature id for the '<em><b>DSource Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEDGE__DSOURCE_NODE = 0;

	/**
	 * The feature id for the '<em><b>DTarget Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEDGE__DTARGET_NODE = 1;

	/**
	 * The number of structural features of the '<em>DEdge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEDGE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.dgi.impl.DPoorReferenceImpl <em>DPoor Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.dgi.impl.DPoorReferenceImpl
	 * @see org.isoe.diagraph.dgi.impl.DgiPackageImpl#getDPoorReference()
	 * @generated
	 */
	int DPOOR_REFERENCE = 4;

	/**
	 * The feature id for the '<em><b>DSource Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DPOOR_REFERENCE__DSOURCE_NODE = DEDGE__DSOURCE_NODE;

	/**
	 * The feature id for the '<em><b>DTarget Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DPOOR_REFERENCE__DTARGET_NODE = DEDGE__DTARGET_NODE;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DPOOR_REFERENCE__NAME = DEDGE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>ETarget Model Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DPOOR_REFERENCE__ETARGET_MODEL_ELEMENT = DEDGE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>DPoor Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DPOOR_REFERENCE_FEATURE_COUNT = DEDGE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.dgi.impl.DContainmentImpl <em>DContainment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.dgi.impl.DContainmentImpl
	 * @see org.isoe.diagraph.dgi.impl.DgiPackageImpl#getDContainment()
	 * @generated
	 */
	int DCONTAINMENT = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DCONTAINMENT__NAME = DLABELLED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>DLabels</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DCONTAINMENT__DLABELS = DLABELLED_ELEMENT__DLABELS;

	/**
	 * The feature id for the '<em><b>DSource Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DCONTAINMENT__DSOURCE_NODE = DLABELLED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>DTarget Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DCONTAINMENT__DTARGET_NODE = DLABELLED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Compartment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DCONTAINMENT__COMPARTMENT = DLABELLED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Port</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DCONTAINMENT__PORT = DLABELLED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>DContainment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DCONTAINMENT_FEATURE_COUNT = DLABELLED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.dgi.impl.DLabelImpl <em>DLabel</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.dgi.impl.DLabelImpl
	 * @see org.isoe.diagraph.dgi.impl.DgiPackageImpl#getDLabel()
	 * @generated
	 */
	int DLABEL = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLABEL__NAME = DNAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>DLabelled Element</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLABEL__DLABELLED_ELEMENT = DNAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>From Super Element</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLABEL__FROM_SUPER_ELEMENT = DNAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Inferred</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLABEL__INFERRED = DNAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>EAttribute Model Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLABEL__EATTRIBUTE_MODEL_ELEMENT = DNAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>DLabel</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DLABEL_FEATURE_COUNT = DNAMED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.dgi.impl.DGenericElementImpl <em>DGeneric Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.dgi.impl.DGenericElementImpl
	 * @see org.isoe.diagraph.dgi.impl.DgiPackageImpl#getDGenericElement()
	 * @generated
	 */
	int DGENERIC_ELEMENT = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DGENERIC_ELEMENT__NAME = DELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>DLabels</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DGENERIC_ELEMENT__DLABELS = DELEMENT__DLABELS;

	/**
	 * The feature id for the '<em><b>EContainment Model Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DGENERIC_ELEMENT__ECONTAINMENT_MODEL_ELEMENT = DELEMENT__ECONTAINMENT_MODEL_ELEMENT;

	/**
	 * The feature id for the '<em><b>EModel Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DGENERIC_ELEMENT__EMODEL_ELEMENT = DELEMENT__EMODEL_ELEMENT;

	/**
	 * The feature id for the '<em><b>DLower Elements</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DGENERIC_ELEMENT__DLOWER_ELEMENTS = DELEMENT__DLOWER_ELEMENTS;

	/**
	 * The feature id for the '<em><b>DSuper Elements</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DGENERIC_ELEMENT__DSUPER_ELEMENTS = DELEMENT__DSUPER_ELEMENTS;

	/**
	 * The number of structural features of the '<em>DGeneric Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DGENERIC_ELEMENT_FEATURE_COUNT = DELEMENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.dgi.impl.DConcreteSyntaxImpl <em>DConcrete Syntax</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.dgi.impl.DConcreteSyntaxImpl
	 * @see org.isoe.diagraph.dgi.impl.DgiPackageImpl#getDConcreteSyntax()
	 * @generated
	 */
	int DCONCRETE_SYNTAX = 8;

	/**
	 * The feature id for the '<em><b>DNodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DCONCRETE_SYNTAX__DNODES = 0;

	/**
	 * The feature id for the '<em><b>DElements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DCONCRETE_SYNTAX__DELEMENTS = 1;

	/**
	 * The feature id for the '<em><b>DEdges</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DCONCRETE_SYNTAX__DEDGES = 2;

	/**
	 * The feature id for the '<em><b>Root Point Of View</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DCONCRETE_SYNTAX__ROOT_POINT_OF_VIEW = 3;

	/**
	 * The feature id for the '<em><b>Points Of View</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DCONCRETE_SYNTAX__POINTS_OF_VIEW = 4;

	/**
	 * The feature id for the '<em><b>Current Point Of View</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DCONCRETE_SYNTAX__CURRENT_POINT_OF_VIEW = 5;

	/**
	 * The number of structural features of the '<em>DConcrete Syntax</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DCONCRETE_SYNTAX_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.dgi.impl.DPointOfViewImpl <em>DPoint Of View</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.dgi.impl.DPointOfViewImpl
	 * @see org.isoe.diagraph.dgi.impl.DgiPackageImpl#getDPointOfView()
	 * @generated
	 */
	int DPOINT_OF_VIEW = 11;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DPOINT_OF_VIEW__CHILDREN = 0;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DPOINT_OF_VIEW__PARENT = 1;

	/**
	 * The feature id for the '<em><b>DRoot Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DPOINT_OF_VIEW__DROOT_NODE = 2;

	/**
	 * The number of structural features of the '<em>DPoint Of View</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DPOINT_OF_VIEW_FEATURE_COUNT = 3;


	/**
	 * Returns the meta object for class '{@link org.isoe.diagraph.dgi.DElement <em>DElement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>DElement</em>'.
	 * @see org.isoe.diagraph.dgi.DElement
	 * @generated
	 */
	EClass getDElement();

	/**
	 * Returns the meta object for the reference '{@link org.isoe.diagraph.dgi.DElement#getEContainmentModelElement <em>EContainment Model Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>EContainment Model Element</em>'.
	 * @see org.isoe.diagraph.dgi.DElement#getEContainmentModelElement()
	 * @see #getDElement()
	 * @generated
	 */
	EReference getDElement_EContainmentModelElement();

	/**
	 * Returns the meta object for the reference '{@link org.isoe.diagraph.dgi.DElement#getEModelElement <em>EModel Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>EModel Element</em>'.
	 * @see org.isoe.diagraph.dgi.DElement#getEModelElement()
	 * @see #getDElement()
	 * @generated
	 */
	EReference getDElement_EModelElement();

	/**
	 * Returns the meta object for the reference list '{@link org.isoe.diagraph.dgi.DElement#getDLowerElements <em>DLower Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>DLower Elements</em>'.
	 * @see org.isoe.diagraph.dgi.DElement#getDLowerElements()
	 * @see #getDElement()
	 * @generated
	 */
	EReference getDElement_DLowerElements();

	/**
	 * Returns the meta object for the reference list '{@link org.isoe.diagraph.dgi.DElement#getDSuperElements <em>DSuper Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>DSuper Elements</em>'.
	 * @see org.isoe.diagraph.dgi.DElement#getDSuperElements()
	 * @see #getDElement()
	 * @generated
	 */
	EReference getDElement_DSuperElements();

	/**
	 * Returns the meta object for class '{@link org.isoe.diagraph.dgi.DLabelledElement <em>DLabelled Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>DLabelled Element</em>'.
	 * @see org.isoe.diagraph.dgi.DLabelledElement
	 * @generated
	 */
	EClass getDLabelledElement();

	/**
	 * Returns the meta object for the containment reference list '{@link org.isoe.diagraph.dgi.DLabelledElement#getDLabels <em>DLabels</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>DLabels</em>'.
	 * @see org.isoe.diagraph.dgi.DLabelledElement#getDLabels()
	 * @see #getDLabelledElement()
	 * @generated
	 */
	EReference getDLabelledElement_DLabels();

	/**
	 * Returns the meta object for class '{@link org.isoe.diagraph.dgi.DNode <em>DNode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>DNode</em>'.
	 * @see org.isoe.diagraph.dgi.DNode
	 * @generated
	 */
	EClass getDNode();

	/**
	 * Returns the meta object for the reference list '{@link org.isoe.diagraph.dgi.DNode#getDReferences <em>DReferences</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>DReferences</em>'.
	 * @see org.isoe.diagraph.dgi.DNode#getDReferences()
	 * @see #getDNode()
	 * @generated
	 */
	EReference getDNode_DReferences();

	/**
	 * Returns the meta object for the reference list '{@link org.isoe.diagraph.dgi.DNode#getDContainments <em>DContainments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>DContainments</em>'.
	 * @see org.isoe.diagraph.dgi.DNode#getDContainments()
	 * @see #getDNode()
	 * @generated
	 */
	EReference getDNode_DContainments();

	/**
	 * Returns the meta object for the reference list '{@link org.isoe.diagraph.dgi.DNode#getDLinks <em>DLinks</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>DLinks</em>'.
	 * @see org.isoe.diagraph.dgi.DNode#getDLinks()
	 * @see #getDNode()
	 * @generated
	 */
	EReference getDNode_DLinks();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.dgi.DNode#isRoot <em>Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Root</em>'.
	 * @see org.isoe.diagraph.dgi.DNode#isRoot()
	 * @see #getDNode()
	 * @generated
	 */
	EAttribute getDNode_Root();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.dgi.DNode#isRecursive <em>Recursive</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Recursive</em>'.
	 * @see org.isoe.diagraph.dgi.DNode#isRecursive()
	 * @see #getDNode()
	 * @generated
	 */
	EAttribute getDNode_Recursive();

	/**
	 * Returns the meta object for the attribute list '{@link org.isoe.diagraph.dgi.DNode#getNavigations <em>Navigations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Navigations</em>'.
	 * @see org.isoe.diagraph.dgi.DNode#getNavigations()
	 * @see #getDNode()
	 * @generated
	 */
	EAttribute getDNode_Navigations();

	/**
	 * Returns the meta object for class '{@link org.isoe.diagraph.dgi.DLink <em>DLink</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>DLink</em>'.
	 * @see org.isoe.diagraph.dgi.DLink
	 * @generated
	 */
	EClass getDLink();

	/**
	 * Returns the meta object for the reference '{@link org.isoe.diagraph.dgi.DLink#getESourceModelElement <em>ESource Model Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>ESource Model Element</em>'.
	 * @see org.isoe.diagraph.dgi.DLink#getESourceModelElement()
	 * @see #getDLink()
	 * @generated
	 */
	EReference getDLink_ESourceModelElement();

	/**
	 * Returns the meta object for the reference '{@link org.isoe.diagraph.dgi.DLink#getETargetModelElement <em>ETarget Model Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>ETarget Model Element</em>'.
	 * @see org.isoe.diagraph.dgi.DLink#getETargetModelElement()
	 * @see #getDLink()
	 * @generated
	 */
	EReference getDLink_ETargetModelElement();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.dgi.DLink#isReversedSource <em>Reversed Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Reversed Source</em>'.
	 * @see org.isoe.diagraph.dgi.DLink#isReversedSource()
	 * @see #getDLink()
	 * @generated
	 */
	EAttribute getDLink_ReversedSource();

	/**
	 * Returns the meta object for class '{@link org.isoe.diagraph.dgi.DPoorReference <em>DPoor Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>DPoor Reference</em>'.
	 * @see org.isoe.diagraph.dgi.DPoorReference
	 * @generated
	 */
	EClass getDPoorReference();

	/**
	 * Returns the meta object for the reference '{@link org.isoe.diagraph.dgi.DPoorReference#getETargetModelElement <em>ETarget Model Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>ETarget Model Element</em>'.
	 * @see org.isoe.diagraph.dgi.DPoorReference#getETargetModelElement()
	 * @see #getDPoorReference()
	 * @generated
	 */
	EReference getDPoorReference_ETargetModelElement();

	/**
	 * Returns the meta object for class '{@link org.isoe.diagraph.dgi.DContainment <em>DContainment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>DContainment</em>'.
	 * @see org.isoe.diagraph.dgi.DContainment
	 * @generated
	 */
	EClass getDContainment();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.dgi.DContainment#isCompartment <em>Compartment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Compartment</em>'.
	 * @see org.isoe.diagraph.dgi.DContainment#isCompartment()
	 * @see #getDContainment()
	 * @generated
	 */
	EAttribute getDContainment_Compartment();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.dgi.DContainment#isPort <em>Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Port</em>'.
	 * @see org.isoe.diagraph.dgi.DContainment#isPort()
	 * @see #getDContainment()
	 * @generated
	 */
	EAttribute getDContainment_Port();

	/**
	 * Returns the meta object for class '{@link org.isoe.diagraph.dgi.DLabel <em>DLabel</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>DLabel</em>'.
	 * @see org.isoe.diagraph.dgi.DLabel
	 * @generated
	 */
	EClass getDLabel();

	/**
	 * Returns the meta object for the container reference '{@link org.isoe.diagraph.dgi.DLabel#getDLabelledElement <em>DLabelled Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>DLabelled Element</em>'.
	 * @see org.isoe.diagraph.dgi.DLabel#getDLabelledElement()
	 * @see #getDLabel()
	 * @generated
	 */
	EReference getDLabel_DLabelledElement();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.dgi.DLabel#isFromSuperElement <em>From Super Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>From Super Element</em>'.
	 * @see org.isoe.diagraph.dgi.DLabel#isFromSuperElement()
	 * @see #getDLabel()
	 * @generated
	 */
	EAttribute getDLabel_FromSuperElement();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.dgi.DLabel#isInferred <em>Inferred</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Inferred</em>'.
	 * @see org.isoe.diagraph.dgi.DLabel#isInferred()
	 * @see #getDLabel()
	 * @generated
	 */
	EAttribute getDLabel_Inferred();

	/**
	 * Returns the meta object for the reference '{@link org.isoe.diagraph.dgi.DLabel#getEAttributeModelElement <em>EAttribute Model Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>EAttribute Model Element</em>'.
	 * @see org.isoe.diagraph.dgi.DLabel#getEAttributeModelElement()
	 * @see #getDLabel()
	 * @generated
	 */
	EReference getDLabel_EAttributeModelElement();

	/**
	 * Returns the meta object for class '{@link org.isoe.diagraph.dgi.DGenericElement <em>DGeneric Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>DGeneric Element</em>'.
	 * @see org.isoe.diagraph.dgi.DGenericElement
	 * @generated
	 */
	EClass getDGenericElement();

	/**
	 * Returns the meta object for class '{@link org.isoe.diagraph.dgi.DConcreteSyntax <em>DConcrete Syntax</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>DConcrete Syntax</em>'.
	 * @see org.isoe.diagraph.dgi.DConcreteSyntax
	 * @generated
	 */
	EClass getDConcreteSyntax();

	/**
	 * Returns the meta object for the containment reference list '{@link org.isoe.diagraph.dgi.DConcreteSyntax#getDNodes <em>DNodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>DNodes</em>'.
	 * @see org.isoe.diagraph.dgi.DConcreteSyntax#getDNodes()
	 * @see #getDConcreteSyntax()
	 * @generated
	 */
	EReference getDConcreteSyntax_DNodes();

	/**
	 * Returns the meta object for the containment reference list '{@link org.isoe.diagraph.dgi.DConcreteSyntax#getDElements <em>DElements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>DElements</em>'.
	 * @see org.isoe.diagraph.dgi.DConcreteSyntax#getDElements()
	 * @see #getDConcreteSyntax()
	 * @generated
	 */
	EReference getDConcreteSyntax_DElements();

	/**
	 * Returns the meta object for the containment reference list '{@link org.isoe.diagraph.dgi.DConcreteSyntax#getDEdges <em>DEdges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>DEdges</em>'.
	 * @see org.isoe.diagraph.dgi.DConcreteSyntax#getDEdges()
	 * @see #getDConcreteSyntax()
	 * @generated
	 */
	EReference getDConcreteSyntax_DEdges();

	/**
	 * Returns the meta object for the containment reference '{@link org.isoe.diagraph.dgi.DConcreteSyntax#getRootPointOfView <em>Root Point Of View</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Root Point Of View</em>'.
	 * @see org.isoe.diagraph.dgi.DConcreteSyntax#getRootPointOfView()
	 * @see #getDConcreteSyntax()
	 * @generated
	 */
	EReference getDConcreteSyntax_RootPointOfView();

	/**
	 * Returns the meta object for the reference list '{@link org.isoe.diagraph.dgi.DConcreteSyntax#getPointsOfView <em>Points Of View</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Points Of View</em>'.
	 * @see org.isoe.diagraph.dgi.DConcreteSyntax#getPointsOfView()
	 * @see #getDConcreteSyntax()
	 * @generated
	 */
	EReference getDConcreteSyntax_PointsOfView();

	/**
	 * Returns the meta object for the reference '{@link org.isoe.diagraph.dgi.DConcreteSyntax#getCurrentPointOfView <em>Current Point Of View</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Current Point Of View</em>'.
	 * @see org.isoe.diagraph.dgi.DConcreteSyntax#getCurrentPointOfView()
	 * @see #getDConcreteSyntax()
	 * @generated
	 */
	EReference getDConcreteSyntax_CurrentPointOfView();

	/**
	 * Returns the meta object for class '{@link org.isoe.diagraph.dgi.DEdge <em>DEdge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>DEdge</em>'.
	 * @see org.isoe.diagraph.dgi.DEdge
	 * @generated
	 */
	EClass getDEdge();

	/**
	 * Returns the meta object for the reference '{@link org.isoe.diagraph.dgi.DEdge#getDSourceNode <em>DSource Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>DSource Node</em>'.
	 * @see org.isoe.diagraph.dgi.DEdge#getDSourceNode()
	 * @see #getDEdge()
	 * @generated
	 */
	EReference getDEdge_DSourceNode();

	/**
	 * Returns the meta object for the reference '{@link org.isoe.diagraph.dgi.DEdge#getDTargetNode <em>DTarget Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>DTarget Node</em>'.
	 * @see org.isoe.diagraph.dgi.DEdge#getDTargetNode()
	 * @see #getDEdge()
	 * @generated
	 */
	EReference getDEdge_DTargetNode();

	/**
	 * Returns the meta object for class '{@link org.isoe.diagraph.dgi.DNamedElement <em>DNamed Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>DNamed Element</em>'.
	 * @see org.isoe.diagraph.dgi.DNamedElement
	 * @generated
	 */
	EClass getDNamedElement();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.dgi.DNamedElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.isoe.diagraph.dgi.DNamedElement#getName()
	 * @see #getDNamedElement()
	 * @generated
	 */
	EAttribute getDNamedElement_Name();

	/**
	 * Returns the meta object for class '{@link org.isoe.diagraph.dgi.DPointOfView <em>DPoint Of View</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>DPoint Of View</em>'.
	 * @see org.isoe.diagraph.dgi.DPointOfView
	 * @generated
	 */
	EClass getDPointOfView();

	/**
	 * Returns the meta object for the containment reference list '{@link org.isoe.diagraph.dgi.DPointOfView#getChildren <em>Children</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Children</em>'.
	 * @see org.isoe.diagraph.dgi.DPointOfView#getChildren()
	 * @see #getDPointOfView()
	 * @generated
	 */
	EReference getDPointOfView_Children();

	/**
	 * Returns the meta object for the container reference '{@link org.isoe.diagraph.dgi.DPointOfView#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Parent</em>'.
	 * @see org.isoe.diagraph.dgi.DPointOfView#getParent()
	 * @see #getDPointOfView()
	 * @generated
	 */
	EReference getDPointOfView_Parent();

	/**
	 * Returns the meta object for the reference '{@link org.isoe.diagraph.dgi.DPointOfView#getDRootNode <em>DRoot Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>DRoot Node</em>'.
	 * @see org.isoe.diagraph.dgi.DPointOfView#getDRootNode()
	 * @see #getDPointOfView()
	 * @generated
	 */
	EReference getDPointOfView_DRootNode();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	DgiFactory getDgiFactory();

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
		 * The meta object literal for the '{@link org.isoe.diagraph.dgi.impl.DElementImpl <em>DElement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.dgi.impl.DElementImpl
		 * @see org.isoe.diagraph.dgi.impl.DgiPackageImpl#getDElement()
		 * @generated
		 */
		EClass DELEMENT = eINSTANCE.getDElement();

		/**
		 * The meta object literal for the '<em><b>EContainment Model Element</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DELEMENT__ECONTAINMENT_MODEL_ELEMENT = eINSTANCE.getDElement_EContainmentModelElement();

		/**
		 * The meta object literal for the '<em><b>EModel Element</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DELEMENT__EMODEL_ELEMENT = eINSTANCE.getDElement_EModelElement();

		/**
		 * The meta object literal for the '<em><b>DLower Elements</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DELEMENT__DLOWER_ELEMENTS = eINSTANCE.getDElement_DLowerElements();

		/**
		 * The meta object literal for the '<em><b>DSuper Elements</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DELEMENT__DSUPER_ELEMENTS = eINSTANCE.getDElement_DSuperElements();

		/**
		 * The meta object literal for the '{@link org.isoe.diagraph.dgi.impl.DLabelledElementImpl <em>DLabelled Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.dgi.impl.DLabelledElementImpl
		 * @see org.isoe.diagraph.dgi.impl.DgiPackageImpl#getDLabelledElement()
		 * @generated
		 */
		EClass DLABELLED_ELEMENT = eINSTANCE.getDLabelledElement();

		/**
		 * The meta object literal for the '<em><b>DLabels</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DLABELLED_ELEMENT__DLABELS = eINSTANCE.getDLabelledElement_DLabels();

		/**
		 * The meta object literal for the '{@link org.isoe.diagraph.dgi.impl.DNodeImpl <em>DNode</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.dgi.impl.DNodeImpl
		 * @see org.isoe.diagraph.dgi.impl.DgiPackageImpl#getDNode()
		 * @generated
		 */
		EClass DNODE = eINSTANCE.getDNode();

		/**
		 * The meta object literal for the '<em><b>DReferences</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DNODE__DREFERENCES = eINSTANCE.getDNode_DReferences();

		/**
		 * The meta object literal for the '<em><b>DContainments</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DNODE__DCONTAINMENTS = eINSTANCE.getDNode_DContainments();

		/**
		 * The meta object literal for the '<em><b>DLinks</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DNODE__DLINKS = eINSTANCE.getDNode_DLinks();

		/**
		 * The meta object literal for the '<em><b>Root</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DNODE__ROOT = eINSTANCE.getDNode_Root();

		/**
		 * The meta object literal for the '<em><b>Recursive</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DNODE__RECURSIVE = eINSTANCE.getDNode_Recursive();

		/**
		 * The meta object literal for the '<em><b>Navigations</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DNODE__NAVIGATIONS = eINSTANCE.getDNode_Navigations();

		/**
		 * The meta object literal for the '{@link org.isoe.diagraph.dgi.impl.DLinkImpl <em>DLink</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.dgi.impl.DLinkImpl
		 * @see org.isoe.diagraph.dgi.impl.DgiPackageImpl#getDLink()
		 * @generated
		 */
		EClass DLINK = eINSTANCE.getDLink();

		/**
		 * The meta object literal for the '<em><b>ESource Model Element</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DLINK__ESOURCE_MODEL_ELEMENT = eINSTANCE.getDLink_ESourceModelElement();

		/**
		 * The meta object literal for the '<em><b>ETarget Model Element</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DLINK__ETARGET_MODEL_ELEMENT = eINSTANCE.getDLink_ETargetModelElement();

		/**
		 * The meta object literal for the '<em><b>Reversed Source</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DLINK__REVERSED_SOURCE = eINSTANCE.getDLink_ReversedSource();

		/**
		 * The meta object literal for the '{@link org.isoe.diagraph.dgi.impl.DPoorReferenceImpl <em>DPoor Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.dgi.impl.DPoorReferenceImpl
		 * @see org.isoe.diagraph.dgi.impl.DgiPackageImpl#getDPoorReference()
		 * @generated
		 */
		EClass DPOOR_REFERENCE = eINSTANCE.getDPoorReference();

		/**
		 * The meta object literal for the '<em><b>ETarget Model Element</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DPOOR_REFERENCE__ETARGET_MODEL_ELEMENT = eINSTANCE.getDPoorReference_ETargetModelElement();

		/**
		 * The meta object literal for the '{@link org.isoe.diagraph.dgi.impl.DContainmentImpl <em>DContainment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.dgi.impl.DContainmentImpl
		 * @see org.isoe.diagraph.dgi.impl.DgiPackageImpl#getDContainment()
		 * @generated
		 */
		EClass DCONTAINMENT = eINSTANCE.getDContainment();

		/**
		 * The meta object literal for the '<em><b>Compartment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DCONTAINMENT__COMPARTMENT = eINSTANCE.getDContainment_Compartment();

		/**
		 * The meta object literal for the '<em><b>Port</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DCONTAINMENT__PORT = eINSTANCE.getDContainment_Port();

		/**
		 * The meta object literal for the '{@link org.isoe.diagraph.dgi.impl.DLabelImpl <em>DLabel</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.dgi.impl.DLabelImpl
		 * @see org.isoe.diagraph.dgi.impl.DgiPackageImpl#getDLabel()
		 * @generated
		 */
		EClass DLABEL = eINSTANCE.getDLabel();

		/**
		 * The meta object literal for the '<em><b>DLabelled Element</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DLABEL__DLABELLED_ELEMENT = eINSTANCE.getDLabel_DLabelledElement();

		/**
		 * The meta object literal for the '<em><b>From Super Element</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DLABEL__FROM_SUPER_ELEMENT = eINSTANCE.getDLabel_FromSuperElement();

		/**
		 * The meta object literal for the '<em><b>Inferred</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DLABEL__INFERRED = eINSTANCE.getDLabel_Inferred();

		/**
		 * The meta object literal for the '<em><b>EAttribute Model Element</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DLABEL__EATTRIBUTE_MODEL_ELEMENT = eINSTANCE.getDLabel_EAttributeModelElement();

		/**
		 * The meta object literal for the '{@link org.isoe.diagraph.dgi.impl.DGenericElementImpl <em>DGeneric Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.dgi.impl.DGenericElementImpl
		 * @see org.isoe.diagraph.dgi.impl.DgiPackageImpl#getDGenericElement()
		 * @generated
		 */
		EClass DGENERIC_ELEMENT = eINSTANCE.getDGenericElement();

		/**
		 * The meta object literal for the '{@link org.isoe.diagraph.dgi.impl.DConcreteSyntaxImpl <em>DConcrete Syntax</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.dgi.impl.DConcreteSyntaxImpl
		 * @see org.isoe.diagraph.dgi.impl.DgiPackageImpl#getDConcreteSyntax()
		 * @generated
		 */
		EClass DCONCRETE_SYNTAX = eINSTANCE.getDConcreteSyntax();

		/**
		 * The meta object literal for the '<em><b>DNodes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DCONCRETE_SYNTAX__DNODES = eINSTANCE.getDConcreteSyntax_DNodes();

		/**
		 * The meta object literal for the '<em><b>DElements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DCONCRETE_SYNTAX__DELEMENTS = eINSTANCE.getDConcreteSyntax_DElements();

		/**
		 * The meta object literal for the '<em><b>DEdges</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DCONCRETE_SYNTAX__DEDGES = eINSTANCE.getDConcreteSyntax_DEdges();

		/**
		 * The meta object literal for the '<em><b>Root Point Of View</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DCONCRETE_SYNTAX__ROOT_POINT_OF_VIEW = eINSTANCE.getDConcreteSyntax_RootPointOfView();

		/**
		 * The meta object literal for the '<em><b>Points Of View</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DCONCRETE_SYNTAX__POINTS_OF_VIEW = eINSTANCE.getDConcreteSyntax_PointsOfView();

		/**
		 * The meta object literal for the '<em><b>Current Point Of View</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DCONCRETE_SYNTAX__CURRENT_POINT_OF_VIEW = eINSTANCE.getDConcreteSyntax_CurrentPointOfView();

		/**
		 * The meta object literal for the '{@link org.isoe.diagraph.dgi.impl.DEdgeImpl <em>DEdge</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.dgi.impl.DEdgeImpl
		 * @see org.isoe.diagraph.dgi.impl.DgiPackageImpl#getDEdge()
		 * @generated
		 */
		EClass DEDGE = eINSTANCE.getDEdge();

		/**
		 * The meta object literal for the '<em><b>DSource Node</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEDGE__DSOURCE_NODE = eINSTANCE.getDEdge_DSourceNode();

		/**
		 * The meta object literal for the '<em><b>DTarget Node</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEDGE__DTARGET_NODE = eINSTANCE.getDEdge_DTargetNode();

		/**
		 * The meta object literal for the '{@link org.isoe.diagraph.dgi.impl.DNamedElementImpl <em>DNamed Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.dgi.impl.DNamedElementImpl
		 * @see org.isoe.diagraph.dgi.impl.DgiPackageImpl#getDNamedElement()
		 * @generated
		 */
		EClass DNAMED_ELEMENT = eINSTANCE.getDNamedElement();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DNAMED_ELEMENT__NAME = eINSTANCE.getDNamedElement_Name();

		/**
		 * The meta object literal for the '{@link org.isoe.diagraph.dgi.impl.DPointOfViewImpl <em>DPoint Of View</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.dgi.impl.DPointOfViewImpl
		 * @see org.isoe.diagraph.dgi.impl.DgiPackageImpl#getDPointOfView()
		 * @generated
		 */
		EClass DPOINT_OF_VIEW = eINSTANCE.getDPointOfView();

		/**
		 * The meta object literal for the '<em><b>Children</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DPOINT_OF_VIEW__CHILDREN = eINSTANCE.getDPointOfView_Children();

		/**
		 * The meta object literal for the '<em><b>Parent</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DPOINT_OF_VIEW__PARENT = eINSTANCE.getDPointOfView_Parent();

		/**
		 * The meta object literal for the '<em><b>DRoot Node</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DPOINT_OF_VIEW__DROOT_NODE = eINSTANCE.getDPointOfView_DRootNode();

	}

} //DgiPackage
