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
package org.isoe.diagraph.diastyle;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

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
 * @see org.isoe.diagraph.diastyle.DiastyleFactory
 * @model kind="package"
 * @generated
 */
public interface DiastylePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "diastyle";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://isoe-2012-diastyle-dsml";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "diastyle";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DiastylePackage eINSTANCE = org.isoe.diagraph.diastyle.impl.DiastylePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.diastyle.impl.DNodeEdgeStyleImpl <em>DNode Edge Style</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.diastyle.impl.DNodeEdgeStyleImpl
	 * @see org.isoe.diagraph.diastyle.impl.DiastylePackageImpl#getDNodeEdgeStyle()
	 * @generated
	 */
	int DNODE_EDGE_STYLE = 0;

	/**
	 * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE_EDGE_STYLE__EANNOTATIONS = EcorePackage.EMODEL_ELEMENT__EANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE_EDGE_STYLE__NAME = EcorePackage.EMODEL_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE_EDGE_STYLE__COLOR = EcorePackage.EMODEL_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Style Bridges</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE_EDGE_STYLE__STYLE_BRIDGES = EcorePackage.EMODEL_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE_EDGE_STYLE__PARENT = EcorePackage.EMODEL_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Parent Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE_EDGE_STYLE__PARENT_NAME = EcorePackage.EMODEL_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Line</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE_EDGE_STYLE__LINE = EcorePackage.EMODEL_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Line Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE_EDGE_STYLE__LINE_WIDTH = EcorePackage.EMODEL_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Font Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE_EDGE_STYLE__FONT_NAME = EcorePackage.EMODEL_ELEMENT_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Font Style</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE_EDGE_STYLE__FONT_STYLE = EcorePackage.EMODEL_ELEMENT_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Font Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE_EDGE_STYLE__FONT_SIZE = EcorePackage.EMODEL_ELEMENT_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Font Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE_EDGE_STYLE__FONT_COLOR = EcorePackage.EMODEL_ELEMENT_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Text Alignment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE_EDGE_STYLE__TEXT_ALIGNMENT = EcorePackage.EMODEL_ELEMENT_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE_EDGE_STYLE__ICON = EcorePackage.EMODEL_ELEMENT_FEATURE_COUNT + 12;

	/**
	 * The number of structural features of the '<em>DNode Edge Style</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE_EDGE_STYLE_FEATURE_COUNT = EcorePackage.EMODEL_ELEMENT_FEATURE_COUNT + 13;

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.diastyle.impl.DStyleImpl <em>DStyle</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.diastyle.impl.DStyleImpl
	 * @see org.isoe.diagraph.diastyle.impl.DiastylePackageImpl#getDStyle()
	 * @generated
	 */
	int DSTYLE = 1;

	/**
	 * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DSTYLE__EANNOTATIONS = EcorePackage.EMODEL_ELEMENT__EANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Styles</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DSTYLE__STYLES = EcorePackage.EMODEL_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Style Handler</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DSTYLE__STYLE_HANDLER = EcorePackage.EMODEL_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>DGraph</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DSTYLE__DGRAPH = EcorePackage.EMODEL_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>DStyle</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DSTYLE_FEATURE_COUNT = EcorePackage.EMODEL_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.diastyle.impl.DStyleBridgeImpl <em>DStyle Bridge</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.diastyle.impl.DStyleBridgeImpl
	 * @see org.isoe.diagraph.diastyle.impl.DiastylePackageImpl#getDStyleBridge()
	 * @generated
	 */
	int DSTYLE_BRIDGE = 2;

	/**
	 * The feature id for the '<em><b>DGraph Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DSTYLE_BRIDGE__DGRAPH_ELEMENT = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DSTYLE_BRIDGE__NAME = 1;

	/**
	 * The number of structural features of the '<em>DStyle Bridge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DSTYLE_BRIDGE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.diastyle.impl.DNodeStyleImpl <em>DNode Style</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.diastyle.impl.DNodeStyleImpl
	 * @see org.isoe.diagraph.diastyle.impl.DiastylePackageImpl#getDNodeStyle()
	 * @generated
	 */
	int DNODE_STYLE = 3;

	/**
	 * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE_STYLE__EANNOTATIONS = DNODE_EDGE_STYLE__EANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE_STYLE__NAME = DNODE_EDGE_STYLE__NAME;

	/**
	 * The feature id for the '<em><b>Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE_STYLE__COLOR = DNODE_EDGE_STYLE__COLOR;

	/**
	 * The feature id for the '<em><b>Style Bridges</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE_STYLE__STYLE_BRIDGES = DNODE_EDGE_STYLE__STYLE_BRIDGES;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE_STYLE__PARENT = DNODE_EDGE_STYLE__PARENT;

	/**
	 * The feature id for the '<em><b>Parent Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE_STYLE__PARENT_NAME = DNODE_EDGE_STYLE__PARENT_NAME;

	/**
	 * The feature id for the '<em><b>Line</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE_STYLE__LINE = DNODE_EDGE_STYLE__LINE;

	/**
	 * The feature id for the '<em><b>Line Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE_STYLE__LINE_WIDTH = DNODE_EDGE_STYLE__LINE_WIDTH;

	/**
	 * The feature id for the '<em><b>Font Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE_STYLE__FONT_NAME = DNODE_EDGE_STYLE__FONT_NAME;

	/**
	 * The feature id for the '<em><b>Font Style</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE_STYLE__FONT_STYLE = DNODE_EDGE_STYLE__FONT_STYLE;

	/**
	 * The feature id for the '<em><b>Font Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE_STYLE__FONT_SIZE = DNODE_EDGE_STYLE__FONT_SIZE;

	/**
	 * The feature id for the '<em><b>Font Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE_STYLE__FONT_COLOR = DNODE_EDGE_STYLE__FONT_COLOR;

	/**
	 * The feature id for the '<em><b>Text Alignment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE_STYLE__TEXT_ALIGNMENT = DNODE_EDGE_STYLE__TEXT_ALIGNMENT;

	/**
	 * The feature id for the '<em><b>Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE_STYLE__ICON = DNODE_EDGE_STYLE__ICON;

	/**
	 * The feature id for the '<em><b>Size X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE_STYLE__SIZE_X = DNODE_EDGE_STYLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Radius</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE_STYLE__RADIUS = DNODE_EDGE_STYLE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Shape</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE_STYLE__SHAPE = DNODE_EDGE_STYLE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Size Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE_STYLE__SIZE_Y = DNODE_EDGE_STYLE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Figure</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE_STYLE__FIGURE = DNODE_EDGE_STYLE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Shape Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE_STYLE__SHAPE_DATA = DNODE_EDGE_STYLE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Layout</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE_STYLE__LAYOUT = DNODE_EDGE_STYLE_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>DNode Style</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNODE_STYLE_FEATURE_COUNT = DNODE_EDGE_STYLE_FEATURE_COUNT + 7;

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.diastyle.impl.DEdgeStyleImpl <em>DEdge Style</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.diastyle.impl.DEdgeStyleImpl
	 * @see org.isoe.diagraph.diastyle.impl.DiastylePackageImpl#getDEdgeStyle()
	 * @generated
	 */
	int DEDGE_STYLE = 4;

	/**
	 * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEDGE_STYLE__EANNOTATIONS = DNODE_EDGE_STYLE__EANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEDGE_STYLE__NAME = DNODE_EDGE_STYLE__NAME;

	/**
	 * The feature id for the '<em><b>Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEDGE_STYLE__COLOR = DNODE_EDGE_STYLE__COLOR;

	/**
	 * The feature id for the '<em><b>Style Bridges</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEDGE_STYLE__STYLE_BRIDGES = DNODE_EDGE_STYLE__STYLE_BRIDGES;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEDGE_STYLE__PARENT = DNODE_EDGE_STYLE__PARENT;

	/**
	 * The feature id for the '<em><b>Parent Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEDGE_STYLE__PARENT_NAME = DNODE_EDGE_STYLE__PARENT_NAME;

	/**
	 * The feature id for the '<em><b>Line</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEDGE_STYLE__LINE = DNODE_EDGE_STYLE__LINE;

	/**
	 * The feature id for the '<em><b>Line Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEDGE_STYLE__LINE_WIDTH = DNODE_EDGE_STYLE__LINE_WIDTH;

	/**
	 * The feature id for the '<em><b>Font Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEDGE_STYLE__FONT_NAME = DNODE_EDGE_STYLE__FONT_NAME;

	/**
	 * The feature id for the '<em><b>Font Style</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEDGE_STYLE__FONT_STYLE = DNODE_EDGE_STYLE__FONT_STYLE;

	/**
	 * The feature id for the '<em><b>Font Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEDGE_STYLE__FONT_SIZE = DNODE_EDGE_STYLE__FONT_SIZE;

	/**
	 * The feature id for the '<em><b>Font Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEDGE_STYLE__FONT_COLOR = DNODE_EDGE_STYLE__FONT_COLOR;

	/**
	 * The feature id for the '<em><b>Text Alignment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEDGE_STYLE__TEXT_ALIGNMENT = DNODE_EDGE_STYLE__TEXT_ALIGNMENT;

	/**
	 * The feature id for the '<em><b>Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEDGE_STYLE__ICON = DNODE_EDGE_STYLE__ICON;

	/**
	 * The feature id for the '<em><b>Arrow Direction</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEDGE_STYLE__ARROW_DIRECTION = DNODE_EDGE_STYLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Arrow Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEDGE_STYLE__ARROW_SIZE = DNODE_EDGE_STYLE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Shape</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEDGE_STYLE__SHAPE = DNODE_EDGE_STYLE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>DEdge Style</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEDGE_STYLE_FEATURE_COUNT = DNODE_EDGE_STYLE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.diastyle.impl.DBaseStyleImpl <em>DBase Style</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.diastyle.impl.DBaseStyleImpl
	 * @see org.isoe.diagraph.diastyle.impl.DiastylePackageImpl#getDBaseStyle()
	 * @generated
	 */
	int DBASE_STYLE = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DBASE_STYLE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DBASE_STYLE__COLOR = 1;

	/**
	 * The feature id for the '<em><b>Style Bridges</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DBASE_STYLE__STYLE_BRIDGES = 2;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DBASE_STYLE__PARENT = 3;

	/**
	 * The feature id for the '<em><b>Parent Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DBASE_STYLE__PARENT_NAME = 4;

	/**
	 * The number of structural features of the '<em>DBase Style</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DBASE_STYLE_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.diastyle.impl.DNestingEdgeStyleImpl <em>DNesting Edge Style</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.diastyle.impl.DNestingEdgeStyleImpl
	 * @see org.isoe.diagraph.diastyle.impl.DiastylePackageImpl#getDNestingEdgeStyle()
	 * @generated
	 */
	int DNESTING_EDGE_STYLE = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNESTING_EDGE_STYLE__NAME = DBASE_STYLE__NAME;

	/**
	 * The feature id for the '<em><b>Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNESTING_EDGE_STYLE__COLOR = DBASE_STYLE__COLOR;

	/**
	 * The feature id for the '<em><b>Style Bridges</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNESTING_EDGE_STYLE__STYLE_BRIDGES = DBASE_STYLE__STYLE_BRIDGES;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNESTING_EDGE_STYLE__PARENT = DBASE_STYLE__PARENT;

	/**
	 * The feature id for the '<em><b>Parent Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNESTING_EDGE_STYLE__PARENT_NAME = DBASE_STYLE__PARENT_NAME;

	/**
	 * The number of structural features of the '<em>DNesting Edge Style</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DNESTING_EDGE_STYLE_FEATURE_COUNT = DBASE_STYLE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.diastyle.DColor <em>DColor</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.diastyle.DColor
	 * @see org.isoe.diagraph.diastyle.impl.DiastylePackageImpl#getDColor()
	 * @generated
	 */
	int DCOLOR = 7;

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.diastyle.DLayout <em>DLayout</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.diastyle.DLayout
	 * @see org.isoe.diagraph.diastyle.impl.DiastylePackageImpl#getDLayout()
	 * @generated
	 */
	int DLAYOUT = 8;

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.diastyle.DLine <em>DLine</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.diastyle.DLine
	 * @see org.isoe.diagraph.diastyle.impl.DiastylePackageImpl#getDLine()
	 * @generated
	 */
	int DLINE = 9;

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.diastyle.DDirection <em>DDirection</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.diastyle.DDirection
	 * @see org.isoe.diagraph.diastyle.impl.DiastylePackageImpl#getDDirection()
	 * @generated
	 */
	int DDIRECTION = 10;

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.diastyle.DShape <em>DShape</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.diastyle.DShape
	 * @see org.isoe.diagraph.diastyle.impl.DiastylePackageImpl#getDShape()
	 * @generated
	 */
	int DSHAPE = 11;

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.diastyle.DFontStyle <em>DFont Style</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.diastyle.DFontStyle
	 * @see org.isoe.diagraph.diastyle.impl.DiastylePackageImpl#getDFontStyle()
	 * @generated
	 */
	int DFONT_STYLE = 12;

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.diastyle.DFontName <em>DFont Name</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.diastyle.DFontName
	 * @see org.isoe.diagraph.diastyle.impl.DiastylePackageImpl#getDFontName()
	 * @generated
	 */
	int DFONT_NAME = 13;

	/**
	 * The meta object id for the '{@link org.isoe.diagraph.diastyle.DAlignment <em>DAlignment</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.diastyle.DAlignment
	 * @see org.isoe.diagraph.diastyle.impl.DiastylePackageImpl#getDAlignment()
	 * @generated
	 */
	int DALIGNMENT = 14;

	/**
	 * The meta object id for the '<em>DStyle Handler</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.diastyle.helpers.IStyleHandler
	 * @see org.isoe.diagraph.diastyle.impl.DiastylePackageImpl#getDStyleHandler()
	 * @generated
	 */
	int DSTYLE_HANDLER = 15;


	/**
	 * Returns the meta object for class '{@link org.isoe.diagraph.diastyle.DNodeEdgeStyle <em>DNode Edge Style</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>DNode Edge Style</em>'.
	 * @see org.isoe.diagraph.diastyle.DNodeEdgeStyle
	 * @generated
	 */
	EClass getDNodeEdgeStyle();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.diastyle.DNodeEdgeStyle#getLine <em>Line</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Line</em>'.
	 * @see org.isoe.diagraph.diastyle.DNodeEdgeStyle#getLine()
	 * @see #getDNodeEdgeStyle()
	 * @generated
	 */
	EAttribute getDNodeEdgeStyle_Line();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.diastyle.DNodeEdgeStyle#getLineWidth <em>Line Width</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Line Width</em>'.
	 * @see org.isoe.diagraph.diastyle.DNodeEdgeStyle#getLineWidth()
	 * @see #getDNodeEdgeStyle()
	 * @generated
	 */
	EAttribute getDNodeEdgeStyle_LineWidth();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.diastyle.DNodeEdgeStyle#getFontName <em>Font Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Font Name</em>'.
	 * @see org.isoe.diagraph.diastyle.DNodeEdgeStyle#getFontName()
	 * @see #getDNodeEdgeStyle()
	 * @generated
	 */
	EAttribute getDNodeEdgeStyle_FontName();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.diastyle.DNodeEdgeStyle#getFontStyle <em>Font Style</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Font Style</em>'.
	 * @see org.isoe.diagraph.diastyle.DNodeEdgeStyle#getFontStyle()
	 * @see #getDNodeEdgeStyle()
	 * @generated
	 */
	EAttribute getDNodeEdgeStyle_FontStyle();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.diastyle.DNodeEdgeStyle#getFontSize <em>Font Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Font Size</em>'.
	 * @see org.isoe.diagraph.diastyle.DNodeEdgeStyle#getFontSize()
	 * @see #getDNodeEdgeStyle()
	 * @generated
	 */
	EAttribute getDNodeEdgeStyle_FontSize();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.diastyle.DNodeEdgeStyle#getFontColor <em>Font Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Font Color</em>'.
	 * @see org.isoe.diagraph.diastyle.DNodeEdgeStyle#getFontColor()
	 * @see #getDNodeEdgeStyle()
	 * @generated
	 */
	EAttribute getDNodeEdgeStyle_FontColor();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.diastyle.DNodeEdgeStyle#getTextAlignment <em>Text Alignment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Text Alignment</em>'.
	 * @see org.isoe.diagraph.diastyle.DNodeEdgeStyle#getTextAlignment()
	 * @see #getDNodeEdgeStyle()
	 * @generated
	 */
	EAttribute getDNodeEdgeStyle_TextAlignment();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.diastyle.DNodeEdgeStyle#getIcon <em>Icon</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Icon</em>'.
	 * @see org.isoe.diagraph.diastyle.DNodeEdgeStyle#getIcon()
	 * @see #getDNodeEdgeStyle()
	 * @generated
	 */
	EAttribute getDNodeEdgeStyle_Icon();

	/**
	 * Returns the meta object for class '{@link org.isoe.diagraph.diastyle.DStyle <em>DStyle</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>DStyle</em>'.
	 * @see org.isoe.diagraph.diastyle.DStyle
	 * @generated
	 */
	EClass getDStyle();

	/**
	 * Returns the meta object for the containment reference list '{@link org.isoe.diagraph.diastyle.DStyle#getStyles <em>Styles</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Styles</em>'.
	 * @see org.isoe.diagraph.diastyle.DStyle#getStyles()
	 * @see #getDStyle()
	 * @generated
	 */
	EReference getDStyle_Styles();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.diastyle.DStyle#getStyleHandler <em>Style Handler</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Style Handler</em>'.
	 * @see org.isoe.diagraph.diastyle.DStyle#getStyleHandler()
	 * @see #getDStyle()
	 * @generated
	 */
	EAttribute getDStyle_StyleHandler();

	/**
	 * Returns the meta object for the reference '{@link org.isoe.diagraph.diastyle.DStyle#getDGraph <em>DGraph</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>DGraph</em>'.
	 * @see org.isoe.diagraph.diastyle.DStyle#getDGraph()
	 * @see #getDStyle()
	 * @generated
	 */
	EReference getDStyle_DGraph();

	/**
	 * Returns the meta object for class '{@link org.isoe.diagraph.diastyle.DStyleBridge <em>DStyle Bridge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>DStyle Bridge</em>'.
	 * @see org.isoe.diagraph.diastyle.DStyleBridge
	 * @generated
	 */
	EClass getDStyleBridge();

	/**
	 * Returns the meta object for the reference '{@link org.isoe.diagraph.diastyle.DStyleBridge#getDGraphElement <em>DGraph Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>DGraph Element</em>'.
	 * @see org.isoe.diagraph.diastyle.DStyleBridge#getDGraphElement()
	 * @see #getDStyleBridge()
	 * @generated
	 */
	EReference getDStyleBridge_DGraphElement();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.diastyle.DStyleBridge#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.isoe.diagraph.diastyle.DStyleBridge#getName()
	 * @see #getDStyleBridge()
	 * @generated
	 */
	EAttribute getDStyleBridge_Name();

	/**
	 * Returns the meta object for class '{@link org.isoe.diagraph.diastyle.DNodeStyle <em>DNode Style</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>DNode Style</em>'.
	 * @see org.isoe.diagraph.diastyle.DNodeStyle
	 * @generated
	 */
	EClass getDNodeStyle();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.diastyle.DNodeStyle#getSizeX <em>Size X</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Size X</em>'.
	 * @see org.isoe.diagraph.diastyle.DNodeStyle#getSizeX()
	 * @see #getDNodeStyle()
	 * @generated
	 */
	EAttribute getDNodeStyle_SizeX();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.diastyle.DNodeStyle#getRadius <em>Radius</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Radius</em>'.
	 * @see org.isoe.diagraph.diastyle.DNodeStyle#getRadius()
	 * @see #getDNodeStyle()
	 * @generated
	 */
	EAttribute getDNodeStyle_Radius();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.diastyle.DNodeStyle#getShape <em>Shape</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Shape</em>'.
	 * @see org.isoe.diagraph.diastyle.DNodeStyle#getShape()
	 * @see #getDNodeStyle()
	 * @generated
	 */
	EAttribute getDNodeStyle_Shape();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.diastyle.DNodeStyle#getSizeY <em>Size Y</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Size Y</em>'.
	 * @see org.isoe.diagraph.diastyle.DNodeStyle#getSizeY()
	 * @see #getDNodeStyle()
	 * @generated
	 */
	EAttribute getDNodeStyle_SizeY();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.diastyle.DNodeStyle#getFigure <em>Figure</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Figure</em>'.
	 * @see org.isoe.diagraph.diastyle.DNodeStyle#getFigure()
	 * @see #getDNodeStyle()
	 * @generated
	 */
	EAttribute getDNodeStyle_Figure();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.diastyle.DNodeStyle#getShapeData <em>Shape Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Shape Data</em>'.
	 * @see org.isoe.diagraph.diastyle.DNodeStyle#getShapeData()
	 * @see #getDNodeStyle()
	 * @generated
	 */
	EAttribute getDNodeStyle_ShapeData();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.diastyle.DNodeStyle#getLayout <em>Layout</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Layout</em>'.
	 * @see org.isoe.diagraph.diastyle.DNodeStyle#getLayout()
	 * @see #getDNodeStyle()
	 * @generated
	 */
	EAttribute getDNodeStyle_Layout();

	/**
	 * Returns the meta object for class '{@link org.isoe.diagraph.diastyle.DEdgeStyle <em>DEdge Style</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>DEdge Style</em>'.
	 * @see org.isoe.diagraph.diastyle.DEdgeStyle
	 * @generated
	 */
	EClass getDEdgeStyle();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.diastyle.DEdgeStyle#getArrowDirection <em>Arrow Direction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Arrow Direction</em>'.
	 * @see org.isoe.diagraph.diastyle.DEdgeStyle#getArrowDirection()
	 * @see #getDEdgeStyle()
	 * @generated
	 */
	EAttribute getDEdgeStyle_ArrowDirection();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.diastyle.DEdgeStyle#getArrowSize <em>Arrow Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Arrow Size</em>'.
	 * @see org.isoe.diagraph.diastyle.DEdgeStyle#getArrowSize()
	 * @see #getDEdgeStyle()
	 * @generated
	 */
	EAttribute getDEdgeStyle_ArrowSize();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.diastyle.DEdgeStyle#getShape <em>Shape</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Shape</em>'.
	 * @see org.isoe.diagraph.diastyle.DEdgeStyle#getShape()
	 * @see #getDEdgeStyle()
	 * @generated
	 */
	EAttribute getDEdgeStyle_Shape();

	/**
	 * Returns the meta object for class '{@link org.isoe.diagraph.diastyle.DNestingEdgeStyle <em>DNesting Edge Style</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>DNesting Edge Style</em>'.
	 * @see org.isoe.diagraph.diastyle.DNestingEdgeStyle
	 * @generated
	 */
	EClass getDNestingEdgeStyle();

	/**
	 * Returns the meta object for class '{@link org.isoe.diagraph.diastyle.DBaseStyle <em>DBase Style</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>DBase Style</em>'.
	 * @see org.isoe.diagraph.diastyle.DBaseStyle
	 * @generated
	 */
	EClass getDBaseStyle();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.diastyle.DBaseStyle#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.isoe.diagraph.diastyle.DBaseStyle#getName()
	 * @see #getDBaseStyle()
	 * @generated
	 */
	EAttribute getDBaseStyle_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.diastyle.DBaseStyle#getColor <em>Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Color</em>'.
	 * @see org.isoe.diagraph.diastyle.DBaseStyle#getColor()
	 * @see #getDBaseStyle()
	 * @generated
	 */
	EAttribute getDBaseStyle_Color();

	/**
	 * Returns the meta object for the containment reference list '{@link org.isoe.diagraph.diastyle.DBaseStyle#getStyleBridges <em>Style Bridges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Style Bridges</em>'.
	 * @see org.isoe.diagraph.diastyle.DBaseStyle#getStyleBridges()
	 * @see #getDBaseStyle()
	 * @generated
	 */
	EReference getDBaseStyle_StyleBridges();

	/**
	 * Returns the meta object for the reference '{@link org.isoe.diagraph.diastyle.DBaseStyle#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parent</em>'.
	 * @see org.isoe.diagraph.diastyle.DBaseStyle#getParent()
	 * @see #getDBaseStyle()
	 * @generated
	 */
	EReference getDBaseStyle_Parent();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.diastyle.DBaseStyle#getParentName <em>Parent Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Parent Name</em>'.
	 * @see org.isoe.diagraph.diastyle.DBaseStyle#getParentName()
	 * @see #getDBaseStyle()
	 * @generated
	 */
	EAttribute getDBaseStyle_ParentName();

	/**
	 * Returns the meta object for enum '{@link org.isoe.diagraph.diastyle.DColor <em>DColor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>DColor</em>'.
	 * @see org.isoe.diagraph.diastyle.DColor
	 * @generated
	 */
	EEnum getDColor();

	/**
	 * Returns the meta object for enum '{@link org.isoe.diagraph.diastyle.DLayout <em>DLayout</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>DLayout</em>'.
	 * @see org.isoe.diagraph.diastyle.DLayout
	 * @generated
	 */
	EEnum getDLayout();

	/**
	 * Returns the meta object for enum '{@link org.isoe.diagraph.diastyle.DLine <em>DLine</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>DLine</em>'.
	 * @see org.isoe.diagraph.diastyle.DLine
	 * @generated
	 */
	EEnum getDLine();

	/**
	 * Returns the meta object for enum '{@link org.isoe.diagraph.diastyle.DDirection <em>DDirection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>DDirection</em>'.
	 * @see org.isoe.diagraph.diastyle.DDirection
	 * @generated
	 */
	EEnum getDDirection();

	/**
	 * Returns the meta object for enum '{@link org.isoe.diagraph.diastyle.DShape <em>DShape</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>DShape</em>'.
	 * @see org.isoe.diagraph.diastyle.DShape
	 * @generated
	 */
	EEnum getDShape();

	/**
	 * Returns the meta object for enum '{@link org.isoe.diagraph.diastyle.DFontStyle <em>DFont Style</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>DFont Style</em>'.
	 * @see org.isoe.diagraph.diastyle.DFontStyle
	 * @generated
	 */
	EEnum getDFontStyle();

	/**
	 * Returns the meta object for enum '{@link org.isoe.diagraph.diastyle.DFontName <em>DFont Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>DFont Name</em>'.
	 * @see org.isoe.diagraph.diastyle.DFontName
	 * @generated
	 */
	EEnum getDFontName();

	/**
	 * Returns the meta object for enum '{@link org.isoe.diagraph.diastyle.DAlignment <em>DAlignment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>DAlignment</em>'.
	 * @see org.isoe.diagraph.diastyle.DAlignment
	 * @generated
	 */
	EEnum getDAlignment();

	/**
	 * Returns the meta object for data type '{@link org.isoe.diagraph.diastyle.helpers.IStyleHandler <em>DStyle Handler</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>DStyle Handler</em>'.
	 * @see org.isoe.diagraph.diastyle.helpers.IStyleHandler
	 * @model instanceClass="org.isoe.diagraph.diastyle.helpers.IStyleHandler"
	 * @generated
	 */
	EDataType getDStyleHandler();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	DiastyleFactory getDiastyleFactory();

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
		 * The meta object literal for the '{@link org.isoe.diagraph.diastyle.impl.DNodeEdgeStyleImpl <em>DNode Edge Style</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.diastyle.impl.DNodeEdgeStyleImpl
		 * @see org.isoe.diagraph.diastyle.impl.DiastylePackageImpl#getDNodeEdgeStyle()
		 * @generated
		 */
		EClass DNODE_EDGE_STYLE = eINSTANCE.getDNodeEdgeStyle();

		/**
		 * The meta object literal for the '<em><b>Line</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DNODE_EDGE_STYLE__LINE = eINSTANCE.getDNodeEdgeStyle_Line();

		/**
		 * The meta object literal for the '<em><b>Line Width</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DNODE_EDGE_STYLE__LINE_WIDTH = eINSTANCE.getDNodeEdgeStyle_LineWidth();

		/**
		 * The meta object literal for the '<em><b>Font Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DNODE_EDGE_STYLE__FONT_NAME = eINSTANCE.getDNodeEdgeStyle_FontName();

		/**
		 * The meta object literal for the '<em><b>Font Style</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DNODE_EDGE_STYLE__FONT_STYLE = eINSTANCE.getDNodeEdgeStyle_FontStyle();

		/**
		 * The meta object literal for the '<em><b>Font Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DNODE_EDGE_STYLE__FONT_SIZE = eINSTANCE.getDNodeEdgeStyle_FontSize();

		/**
		 * The meta object literal for the '<em><b>Font Color</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DNODE_EDGE_STYLE__FONT_COLOR = eINSTANCE.getDNodeEdgeStyle_FontColor();

		/**
		 * The meta object literal for the '<em><b>Text Alignment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DNODE_EDGE_STYLE__TEXT_ALIGNMENT = eINSTANCE.getDNodeEdgeStyle_TextAlignment();

		/**
		 * The meta object literal for the '<em><b>Icon</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DNODE_EDGE_STYLE__ICON = eINSTANCE.getDNodeEdgeStyle_Icon();

		/**
		 * The meta object literal for the '{@link org.isoe.diagraph.diastyle.impl.DStyleImpl <em>DStyle</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.diastyle.impl.DStyleImpl
		 * @see org.isoe.diagraph.diastyle.impl.DiastylePackageImpl#getDStyle()
		 * @generated
		 */
		EClass DSTYLE = eINSTANCE.getDStyle();

		/**
		 * The meta object literal for the '<em><b>Styles</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DSTYLE__STYLES = eINSTANCE.getDStyle_Styles();

		/**
		 * The meta object literal for the '<em><b>Style Handler</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DSTYLE__STYLE_HANDLER = eINSTANCE.getDStyle_StyleHandler();

		/**
		 * The meta object literal for the '<em><b>DGraph</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DSTYLE__DGRAPH = eINSTANCE.getDStyle_DGraph();

		/**
		 * The meta object literal for the '{@link org.isoe.diagraph.diastyle.impl.DStyleBridgeImpl <em>DStyle Bridge</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.diastyle.impl.DStyleBridgeImpl
		 * @see org.isoe.diagraph.diastyle.impl.DiastylePackageImpl#getDStyleBridge()
		 * @generated
		 */
		EClass DSTYLE_BRIDGE = eINSTANCE.getDStyleBridge();

		/**
		 * The meta object literal for the '<em><b>DGraph Element</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DSTYLE_BRIDGE__DGRAPH_ELEMENT = eINSTANCE.getDStyleBridge_DGraphElement();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DSTYLE_BRIDGE__NAME = eINSTANCE.getDStyleBridge_Name();

		/**
		 * The meta object literal for the '{@link org.isoe.diagraph.diastyle.impl.DNodeStyleImpl <em>DNode Style</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.diastyle.impl.DNodeStyleImpl
		 * @see org.isoe.diagraph.diastyle.impl.DiastylePackageImpl#getDNodeStyle()
		 * @generated
		 */
		EClass DNODE_STYLE = eINSTANCE.getDNodeStyle();

		/**
		 * The meta object literal for the '<em><b>Size X</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DNODE_STYLE__SIZE_X = eINSTANCE.getDNodeStyle_SizeX();

		/**
		 * The meta object literal for the '<em><b>Radius</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DNODE_STYLE__RADIUS = eINSTANCE.getDNodeStyle_Radius();

		/**
		 * The meta object literal for the '<em><b>Shape</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DNODE_STYLE__SHAPE = eINSTANCE.getDNodeStyle_Shape();

		/**
		 * The meta object literal for the '<em><b>Size Y</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DNODE_STYLE__SIZE_Y = eINSTANCE.getDNodeStyle_SizeY();

		/**
		 * The meta object literal for the '<em><b>Figure</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DNODE_STYLE__FIGURE = eINSTANCE.getDNodeStyle_Figure();

		/**
		 * The meta object literal for the '<em><b>Shape Data</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DNODE_STYLE__SHAPE_DATA = eINSTANCE.getDNodeStyle_ShapeData();

		/**
		 * The meta object literal for the '<em><b>Layout</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DNODE_STYLE__LAYOUT = eINSTANCE.getDNodeStyle_Layout();

		/**
		 * The meta object literal for the '{@link org.isoe.diagraph.diastyle.impl.DEdgeStyleImpl <em>DEdge Style</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.diastyle.impl.DEdgeStyleImpl
		 * @see org.isoe.diagraph.diastyle.impl.DiastylePackageImpl#getDEdgeStyle()
		 * @generated
		 */
		EClass DEDGE_STYLE = eINSTANCE.getDEdgeStyle();

		/**
		 * The meta object literal for the '<em><b>Arrow Direction</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEDGE_STYLE__ARROW_DIRECTION = eINSTANCE.getDEdgeStyle_ArrowDirection();

		/**
		 * The meta object literal for the '<em><b>Arrow Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEDGE_STYLE__ARROW_SIZE = eINSTANCE.getDEdgeStyle_ArrowSize();

		/**
		 * The meta object literal for the '<em><b>Shape</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEDGE_STYLE__SHAPE = eINSTANCE.getDEdgeStyle_Shape();

		/**
		 * The meta object literal for the '{@link org.isoe.diagraph.diastyle.impl.DNestingEdgeStyleImpl <em>DNesting Edge Style</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.diastyle.impl.DNestingEdgeStyleImpl
		 * @see org.isoe.diagraph.diastyle.impl.DiastylePackageImpl#getDNestingEdgeStyle()
		 * @generated
		 */
		EClass DNESTING_EDGE_STYLE = eINSTANCE.getDNestingEdgeStyle();

		/**
		 * The meta object literal for the '{@link org.isoe.diagraph.diastyle.impl.DBaseStyleImpl <em>DBase Style</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.diastyle.impl.DBaseStyleImpl
		 * @see org.isoe.diagraph.diastyle.impl.DiastylePackageImpl#getDBaseStyle()
		 * @generated
		 */
		EClass DBASE_STYLE = eINSTANCE.getDBaseStyle();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DBASE_STYLE__NAME = eINSTANCE.getDBaseStyle_Name();

		/**
		 * The meta object literal for the '<em><b>Color</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DBASE_STYLE__COLOR = eINSTANCE.getDBaseStyle_Color();

		/**
		 * The meta object literal for the '<em><b>Style Bridges</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DBASE_STYLE__STYLE_BRIDGES = eINSTANCE.getDBaseStyle_StyleBridges();

		/**
		 * The meta object literal for the '<em><b>Parent</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DBASE_STYLE__PARENT = eINSTANCE.getDBaseStyle_Parent();

		/**
		 * The meta object literal for the '<em><b>Parent Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DBASE_STYLE__PARENT_NAME = eINSTANCE.getDBaseStyle_ParentName();

		/**
		 * The meta object literal for the '{@link org.isoe.diagraph.diastyle.DColor <em>DColor</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.diastyle.DColor
		 * @see org.isoe.diagraph.diastyle.impl.DiastylePackageImpl#getDColor()
		 * @generated
		 */
		EEnum DCOLOR = eINSTANCE.getDColor();

		/**
		 * The meta object literal for the '{@link org.isoe.diagraph.diastyle.DLayout <em>DLayout</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.diastyle.DLayout
		 * @see org.isoe.diagraph.diastyle.impl.DiastylePackageImpl#getDLayout()
		 * @generated
		 */
		EEnum DLAYOUT = eINSTANCE.getDLayout();

		/**
		 * The meta object literal for the '{@link org.isoe.diagraph.diastyle.DLine <em>DLine</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.diastyle.DLine
		 * @see org.isoe.diagraph.diastyle.impl.DiastylePackageImpl#getDLine()
		 * @generated
		 */
		EEnum DLINE = eINSTANCE.getDLine();

		/**
		 * The meta object literal for the '{@link org.isoe.diagraph.diastyle.DDirection <em>DDirection</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.diastyle.DDirection
		 * @see org.isoe.diagraph.diastyle.impl.DiastylePackageImpl#getDDirection()
		 * @generated
		 */
		EEnum DDIRECTION = eINSTANCE.getDDirection();

		/**
		 * The meta object literal for the '{@link org.isoe.diagraph.diastyle.DShape <em>DShape</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.diastyle.DShape
		 * @see org.isoe.diagraph.diastyle.impl.DiastylePackageImpl#getDShape()
		 * @generated
		 */
		EEnum DSHAPE = eINSTANCE.getDShape();

		/**
		 * The meta object literal for the '{@link org.isoe.diagraph.diastyle.DFontStyle <em>DFont Style</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.diastyle.DFontStyle
		 * @see org.isoe.diagraph.diastyle.impl.DiastylePackageImpl#getDFontStyle()
		 * @generated
		 */
		EEnum DFONT_STYLE = eINSTANCE.getDFontStyle();

		/**
		 * The meta object literal for the '{@link org.isoe.diagraph.diastyle.DFontName <em>DFont Name</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.diastyle.DFontName
		 * @see org.isoe.diagraph.diastyle.impl.DiastylePackageImpl#getDFontName()
		 * @generated
		 */
		EEnum DFONT_NAME = eINSTANCE.getDFontName();

		/**
		 * The meta object literal for the '{@link org.isoe.diagraph.diastyle.DAlignment <em>DAlignment</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.diastyle.DAlignment
		 * @see org.isoe.diagraph.diastyle.impl.DiastylePackageImpl#getDAlignment()
		 * @generated
		 */
		EEnum DALIGNMENT = eINSTANCE.getDAlignment();

		/**
		 * The meta object literal for the '<em>DStyle Handler</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.diastyle.helpers.IStyleHandler
		 * @see org.isoe.diagraph.diastyle.impl.DiastylePackageImpl#getDStyleHandler()
		 * @generated
		 */
		EDataType DSTYLE_HANDLER = eINSTANCE.getDStyleHandler();

	}

} //DiastylePackage
