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
package org.isoe.diagraph.megamodel;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.isoe.diagraph.megamodel.MegamodelFactory
 * @model kind="package"
 *        annotation="diagen knownas\075megamodel='null' origin\075http://org.isoe.fr='null' leftparent\075simpleworld='' rightparent\075nil='' requires\075nil='' context\075pattern\040container='null' related\075nil='null'"
 * @generated
 */
public interface MegamodelPackage extends EPackage {
   /**
	 * The package name.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   String eNAME = "megamodel";

   /**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   String eNS_URI = "http://megamodel";

   /**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   String eNS_PREFIX = "_megamodel";

   /**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   MegamodelPackage eINSTANCE = org.isoe.diagraph.megamodel.impl.MegamodelPackageImpl.init();

   /**
	 * The meta object id for the '{@link org.isoe.diagraph.megamodel.impl.MegamodelImpl <em>Megamodel</em>}' class.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.megamodel.impl.MegamodelImpl
	 * @see org.isoe.diagraph.megamodel.impl.MegamodelPackageImpl#getMegamodel()
	 * @generated
	 */
   int MEGAMODEL = 0;

   /**
	 * The feature id for the '<em><b>Dsmls</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int MEGAMODEL__DSMLS = 0;

   /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int MEGAMODEL__NAME = 1;

   /**
	 * The number of structural features of the '<em>Megamodel</em>' class.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int MEGAMODEL_FEATURE_COUNT = 2;

   /**
	 * The meta object id for the '{@link org.isoe.diagraph.megamodel.impl.MegamodelElementImpl <em>Element</em>}' class.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.megamodel.impl.MegamodelElementImpl
	 * @see org.isoe.diagraph.megamodel.impl.MegamodelPackageImpl#getMegamodelElement()
	 * @generated
	 */
   int MEGAMODEL_ELEMENT = 2;

   /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int MEGAMODEL_ELEMENT__NAME = 0;

   /**
	 * The feature id for the '<em><b>URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int MEGAMODEL_ELEMENT__URI = 1;

   /**
	 * The number of structural features of the '<em>Element</em>' class.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int MEGAMODEL_ELEMENT_FEATURE_COUNT = 2;

   /**
	 * The meta object id for the '{@link org.isoe.diagraph.megamodel.impl.DsmlImpl <em>Dsml</em>}' class.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.megamodel.impl.DsmlImpl
	 * @see org.isoe.diagraph.megamodel.impl.MegamodelPackageImpl#getDsml()
	 * @generated
	 */
   int DSML = 1;

   /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int DSML__NAME = MEGAMODEL_ELEMENT__NAME;

   /**
	 * The feature id for the '<em><b>URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int DSML__URI = MEGAMODEL_ELEMENT__URI;

   /**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int DSML__ID = MEGAMODEL_ELEMENT_FEATURE_COUNT + 0;

   /**
	 * The feature id for the '<em><b>Notation Bridge</b></em>' reference.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int DSML__NOTATION_BRIDGE = MEGAMODEL_ELEMENT_FEATURE_COUNT + 1;

   /**
	 * The feature id for the '<em><b>Relations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int DSML__RELATIONS = MEGAMODEL_ELEMENT_FEATURE_COUNT + 2;

   /**
	 * The feature id for the '<em><b>Models</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int DSML__MODELS = MEGAMODEL_ELEMENT_FEATURE_COUNT + 3;

   /**
	 * The feature id for the '<em><b>Notations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int DSML__NOTATIONS = MEGAMODEL_ELEMENT_FEATURE_COUNT + 4;

   /**
	 * The feature id for the '<em><b>Known As</b></em>' attribute list.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int DSML__KNOWN_AS = MEGAMODEL_ELEMENT_FEATURE_COUNT + 5;

   /**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int DSML__DOCUMENTATION = MEGAMODEL_ELEMENT_FEATURE_COUNT + 6;

   /**
	 * The feature id for the '<em><b>Origin</b></em>' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int DSML__ORIGIN = MEGAMODEL_ELEMENT_FEATURE_COUNT + 7;

   /**
	 * The feature id for the '<em><b>Problem</b></em>' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int DSML__PROBLEM = MEGAMODEL_ELEMENT_FEATURE_COUNT + 8;

   /**
	 * The feature id for the '<em><b>Context</b></em>' attribute list.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int DSML__CONTEXT = MEGAMODEL_ELEMENT_FEATURE_COUNT + 9;

   /**
	 * The feature id for the '<em><b>Keywords</b></em>' attribute list.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int DSML__KEYWORDS = MEGAMODEL_ELEMENT_FEATURE_COUNT + 10;

   /**
	 * The feature id for the '<em><b>Build In</b></em>' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int DSML__BUILD_IN = MEGAMODEL_ELEMENT_FEATURE_COUNT + 11;

   /**
	 * The feature id for the '<em><b>Left Parent Details</b></em>' attribute list.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int DSML__LEFT_PARENT_DETAILS = MEGAMODEL_ELEMENT_FEATURE_COUNT + 12;

   /**
	 * The feature id for the '<em><b>Right Parent Details</b></em>' attribute list.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int DSML__RIGHT_PARENT_DETAILS = MEGAMODEL_ELEMENT_FEATURE_COUNT + 13;

   /**
	 * The feature id for the '<em><b>Require Details</b></em>' attribute list.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int DSML__REQUIRE_DETAILS = MEGAMODEL_ELEMENT_FEATURE_COUNT + 14;

   /**
	 * The feature id for the '<em><b>Related Details</b></em>' attribute list.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int DSML__RELATED_DETAILS = MEGAMODEL_ELEMENT_FEATURE_COUNT + 15;

   /**
	 * The feature id for the '<em><b>Variant Details</b></em>' attribute list.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int DSML__VARIANT_DETAILS = MEGAMODEL_ELEMENT_FEATURE_COUNT + 16;

   /**
	 * The feature id for the '<em><b>Root Notation</b></em>' reference.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int DSML__ROOT_NOTATION = MEGAMODEL_ELEMENT_FEATURE_COUNT + 17;

   /**
	 * The feature id for the '<em><b>Ecore Diagrams</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int DSML__ECORE_DIAGRAMS = MEGAMODEL_ELEMENT_FEATURE_COUNT + 18;

   /**
	 * The number of structural features of the '<em>Dsml</em>' class.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int DSML_FEATURE_COUNT = MEGAMODEL_ELEMENT_FEATURE_COUNT + 19;

   /**
	 * The meta object id for the '{@link org.isoe.diagraph.megamodel.impl.RelatedToImpl <em>Related To</em>}' class.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.megamodel.impl.RelatedToImpl
	 * @see org.isoe.diagraph.megamodel.impl.MegamodelPackageImpl#getRelatedTo()
	 * @generated
	 */
   int RELATED_TO = 3;

   /**
	 * The feature id for the '<em><b>From</b></em>' container reference.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int RELATED_TO__FROM = 0;

   /**
	 * The feature id for the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int RELATED_TO__TO = 1;

   /**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int RELATED_TO__KIND = 2;

   /**
	 * The number of structural features of the '<em>Related To</em>' class.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int RELATED_TO_FEATURE_COUNT = 3;

   /**
	 * The meta object id for the '{@link org.isoe.diagraph.megamodel.impl.ModelImpl <em>Model</em>}' class.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.megamodel.impl.ModelImpl
	 * @see org.isoe.diagraph.megamodel.impl.MegamodelPackageImpl#getModel()
	 * @generated
	 */
   int MODEL = 4;

   /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int MODEL__NAME = MEGAMODEL_ELEMENT__NAME;

   /**
	 * The feature id for the '<em><b>URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int MODEL__URI = MEGAMODEL_ELEMENT__URI;

   /**
	 * The feature id for the '<em><b>Excerpt</b></em>' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int MODEL__EXCERPT = MEGAMODEL_ELEMENT_FEATURE_COUNT + 0;

   /**
	 * The feature id for the '<em><b>Notation Diagrams</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL__NOTATION_DIAGRAMS = MEGAMODEL_ELEMENT_FEATURE_COUNT + 1;

			/**
	 * The feature id for the '<em><b>Dsml</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL__DSML = MEGAMODEL_ELEMENT_FEATURE_COUNT + 2;

			/**
	 * The number of structural features of the '<em>Model</em>' class.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int MODEL_FEATURE_COUNT = MEGAMODEL_ELEMENT_FEATURE_COUNT + 3;

   /**
	 * The meta object id for the '{@link org.isoe.diagraph.megamodel.impl.NotationImpl <em>Notation</em>}' class.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.megamodel.impl.NotationImpl
	 * @see org.isoe.diagraph.megamodel.impl.MegamodelPackageImpl#getNotation()
	 * @generated
	 */
   int NOTATION = 5;

   /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int NOTATION__NAME = MEGAMODEL_ELEMENT__NAME;

   /**
	 * The feature id for the '<em><b>URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int NOTATION__URI = MEGAMODEL_ELEMENT__URI;

   /**
	 * The feature id for the '<em><b>Navigations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int NOTATION__NAVIGATIONS = MEGAMODEL_ELEMENT_FEATURE_COUNT + 0;

   /**
	 * The feature id for the '<em><b>Notation Bridge</b></em>' reference.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int NOTATION__NOTATION_BRIDGE = MEGAMODEL_ELEMENT_FEATURE_COUNT + 1;

   /**
	 * The feature id for the '<em><b>Dsml</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOTATION__DSML = MEGAMODEL_ELEMENT_FEATURE_COUNT + 2;

			/**
	 * The number of structural features of the '<em>Notation</em>' class.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int NOTATION_FEATURE_COUNT = MEGAMODEL_ELEMENT_FEATURE_COUNT + 3;

   /**
	 * The meta object id for the '{@link org.isoe.diagraph.megamodel.impl.DiagramImpl <em>Diagram</em>}' class.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.megamodel.impl.DiagramImpl
	 * @see org.isoe.diagraph.megamodel.impl.MegamodelPackageImpl#getDiagram()
	 * @generated
	 */
   int DIAGRAM = 8;

   /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int DIAGRAM__NAME = MEGAMODEL_ELEMENT__NAME;

   /**
	 * The feature id for the '<em><b>URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int DIAGRAM__URI = MEGAMODEL_ELEMENT__URI;

   /**
	 * The feature id for the '<em><b>Diagram Picture URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int DIAGRAM__DIAGRAM_PICTURE_URI = MEGAMODEL_ELEMENT_FEATURE_COUNT + 0;

   /**
	 * The number of structural features of the '<em>Diagram</em>' class.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int DIAGRAM_FEATURE_COUNT = MEGAMODEL_ELEMENT_FEATURE_COUNT + 1;

   /**
	 * The meta object id for the '{@link org.isoe.diagraph.megamodel.impl.NotationDiagramImpl <em>Notation Diagram</em>}' class.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.megamodel.impl.NotationDiagramImpl
	 * @see org.isoe.diagraph.megamodel.impl.MegamodelPackageImpl#getNotationDiagram()
	 * @generated
	 */
   int NOTATION_DIAGRAM = 6;

   /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int NOTATION_DIAGRAM__NAME = DIAGRAM__NAME;

   /**
	 * The feature id for the '<em><b>URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int NOTATION_DIAGRAM__URI = DIAGRAM__URI;

   /**
	 * The feature id for the '<em><b>Diagram Picture URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int NOTATION_DIAGRAM__DIAGRAM_PICTURE_URI = DIAGRAM__DIAGRAM_PICTURE_URI;

   /**
	 * The number of structural features of the '<em>Notation Diagram</em>' class.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int NOTATION_DIAGRAM_FEATURE_COUNT = DIAGRAM_FEATURE_COUNT + 0;

   /**
	 * The meta object id for the '{@link org.isoe.diagraph.megamodel.impl.NavigationImpl <em>Navigation</em>}' class.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.megamodel.impl.NavigationImpl
	 * @see org.isoe.diagraph.megamodel.impl.MegamodelPackageImpl#getNavigation()
	 * @generated
	 */
   int NAVIGATION = 7;

   /**
	 * The feature id for the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int NAVIGATION__FROM = 0;

   /**
	 * The feature id for the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int NAVIGATION__TO = 1;

   /**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int NAVIGATION__ID = 2;

   /**
	 * The number of structural features of the '<em>Navigation</em>' class.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int NAVIGATION_FEATURE_COUNT = 3;

   /**
	 * The meta object id for the '{@link org.isoe.diagraph.megamodel.impl.EcoreDiagramImpl <em>Ecore Diagram</em>}' class.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.megamodel.impl.EcoreDiagramImpl
	 * @see org.isoe.diagraph.megamodel.impl.MegamodelPackageImpl#getEcoreDiagram()
	 * @generated
	 */
   int ECORE_DIAGRAM = 9;

   /**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int ECORE_DIAGRAM__NAME = DIAGRAM__NAME;

   /**
	 * The feature id for the '<em><b>URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int ECORE_DIAGRAM__URI = DIAGRAM__URI;

   /**
	 * The feature id for the '<em><b>Diagram Picture URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int ECORE_DIAGRAM__DIAGRAM_PICTURE_URI = DIAGRAM__DIAGRAM_PICTURE_URI;

   /**
	 * The number of structural features of the '<em>Ecore Diagram</em>' class.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
   int ECORE_DIAGRAM_FEATURE_COUNT = DIAGRAM_FEATURE_COUNT + 0;

   /**
	 * The meta object id for the '{@link org.isoe.diagraph.megamodel.Relation <em>Relation</em>}' enum.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see org.isoe.diagraph.megamodel.Relation
	 * @see org.isoe.diagraph.megamodel.impl.MegamodelPackageImpl#getRelation()
	 * @generated
	 */
   int RELATION = 10;


   /**
	 * Returns the meta object for class '{@link org.isoe.diagraph.megamodel.Megamodel <em>Megamodel</em>}'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Megamodel</em>'.
	 * @see org.isoe.diagraph.megamodel.Megamodel
	 * @generated
	 */
   EClass getMegamodel();

   /**
	 * Returns the meta object for the containment reference list '{@link org.isoe.diagraph.megamodel.Megamodel#getDsmls <em>Dsmls</em>}'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Dsmls</em>'.
	 * @see org.isoe.diagraph.megamodel.Megamodel#getDsmls()
	 * @see #getMegamodel()
	 * @generated
	 */
   EReference getMegamodel_Dsmls();

   /**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.megamodel.Megamodel#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.isoe.diagraph.megamodel.Megamodel#getName()
	 * @see #getMegamodel()
	 * @generated
	 */
   EAttribute getMegamodel_Name();

   /**
	 * Returns the meta object for class '{@link org.isoe.diagraph.megamodel.Dsml <em>Dsml</em>}'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dsml</em>'.
	 * @see org.isoe.diagraph.megamodel.Dsml
	 * @generated
	 */
   EClass getDsml();

   /**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.megamodel.Dsml#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.isoe.diagraph.megamodel.Dsml#getId()
	 * @see #getDsml()
	 * @generated
	 */
   EAttribute getDsml_Id();

   /**
	 * Returns the meta object for the reference '{@link org.isoe.diagraph.megamodel.Dsml#getNotationBridge <em>Notation Bridge</em>}'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Notation Bridge</em>'.
	 * @see org.isoe.diagraph.megamodel.Dsml#getNotationBridge()
	 * @see #getDsml()
	 * @generated
	 */
   EReference getDsml_NotationBridge();

   /**
	 * Returns the meta object for the containment reference list '{@link org.isoe.diagraph.megamodel.Dsml#getRelations <em>Relations</em>}'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Relations</em>'.
	 * @see org.isoe.diagraph.megamodel.Dsml#getRelations()
	 * @see #getDsml()
	 * @generated
	 */
   EReference getDsml_Relations();

   /**
	 * Returns the meta object for the containment reference list '{@link org.isoe.diagraph.megamodel.Dsml#getModels <em>Models</em>}'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Models</em>'.
	 * @see org.isoe.diagraph.megamodel.Dsml#getModels()
	 * @see #getDsml()
	 * @generated
	 */
   EReference getDsml_Models();

   /**
	 * Returns the meta object for the containment reference list '{@link org.isoe.diagraph.megamodel.Dsml#getNotations <em>Notations</em>}'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Notations</em>'.
	 * @see org.isoe.diagraph.megamodel.Dsml#getNotations()
	 * @see #getDsml()
	 * @generated
	 */
   EReference getDsml_Notations();

   /**
	 * Returns the meta object for the attribute list '{@link org.isoe.diagraph.megamodel.Dsml#getKnownAs <em>Known As</em>}'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Known As</em>'.
	 * @see org.isoe.diagraph.megamodel.Dsml#getKnownAs()
	 * @see #getDsml()
	 * @generated
	 */
   EAttribute getDsml_KnownAs();

   /**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.megamodel.Dsml#getDocumentation <em>Documentation</em>}'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Documentation</em>'.
	 * @see org.isoe.diagraph.megamodel.Dsml#getDocumentation()
	 * @see #getDsml()
	 * @generated
	 */
   EAttribute getDsml_Documentation();

   /**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.megamodel.Dsml#getOrigin <em>Origin</em>}'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Origin</em>'.
	 * @see org.isoe.diagraph.megamodel.Dsml#getOrigin()
	 * @see #getDsml()
	 * @generated
	 */
   EAttribute getDsml_Origin();

   /**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.megamodel.Dsml#getProblem <em>Problem</em>}'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Problem</em>'.
	 * @see org.isoe.diagraph.megamodel.Dsml#getProblem()
	 * @see #getDsml()
	 * @generated
	 */
   EAttribute getDsml_Problem();

   /**
	 * Returns the meta object for the attribute list '{@link org.isoe.diagraph.megamodel.Dsml#getContext <em>Context</em>}'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Context</em>'.
	 * @see org.isoe.diagraph.megamodel.Dsml#getContext()
	 * @see #getDsml()
	 * @generated
	 */
   EAttribute getDsml_Context();

   /**
	 * Returns the meta object for the attribute list '{@link org.isoe.diagraph.megamodel.Dsml#getKeywords <em>Keywords</em>}'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Keywords</em>'.
	 * @see org.isoe.diagraph.megamodel.Dsml#getKeywords()
	 * @see #getDsml()
	 * @generated
	 */
   EAttribute getDsml_Keywords();

   /**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.megamodel.Dsml#isBuildIn <em>Build In</em>}'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Build In</em>'.
	 * @see org.isoe.diagraph.megamodel.Dsml#isBuildIn()
	 * @see #getDsml()
	 * @generated
	 */
   EAttribute getDsml_BuildIn();

   /**
	 * Returns the meta object for the attribute list '{@link org.isoe.diagraph.megamodel.Dsml#getLeftParentDetails <em>Left Parent Details</em>}'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Left Parent Details</em>'.
	 * @see org.isoe.diagraph.megamodel.Dsml#getLeftParentDetails()
	 * @see #getDsml()
	 * @generated
	 */
   EAttribute getDsml_LeftParentDetails();

   /**
	 * Returns the meta object for the attribute list '{@link org.isoe.diagraph.megamodel.Dsml#getRightParentDetails <em>Right Parent Details</em>}'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Right Parent Details</em>'.
	 * @see org.isoe.diagraph.megamodel.Dsml#getRightParentDetails()
	 * @see #getDsml()
	 * @generated
	 */
   EAttribute getDsml_RightParentDetails();

   /**
	 * Returns the meta object for the attribute list '{@link org.isoe.diagraph.megamodel.Dsml#getRequireDetails <em>Require Details</em>}'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Require Details</em>'.
	 * @see org.isoe.diagraph.megamodel.Dsml#getRequireDetails()
	 * @see #getDsml()
	 * @generated
	 */
   EAttribute getDsml_RequireDetails();

   /**
	 * Returns the meta object for the attribute list '{@link org.isoe.diagraph.megamodel.Dsml#getRelatedDetails <em>Related Details</em>}'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Related Details</em>'.
	 * @see org.isoe.diagraph.megamodel.Dsml#getRelatedDetails()
	 * @see #getDsml()
	 * @generated
	 */
   EAttribute getDsml_RelatedDetails();

   /**
	 * Returns the meta object for the attribute list '{@link org.isoe.diagraph.megamodel.Dsml#getVariantDetails <em>Variant Details</em>}'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Variant Details</em>'.
	 * @see org.isoe.diagraph.megamodel.Dsml#getVariantDetails()
	 * @see #getDsml()
	 * @generated
	 */
   EAttribute getDsml_VariantDetails();

   /**
	 * Returns the meta object for the reference '{@link org.isoe.diagraph.megamodel.Dsml#getRootNotation <em>Root Notation</em>}'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Root Notation</em>'.
	 * @see org.isoe.diagraph.megamodel.Dsml#getRootNotation()
	 * @see #getDsml()
	 * @generated
	 */
   EReference getDsml_RootNotation();

   /**
	 * Returns the meta object for the containment reference list '{@link org.isoe.diagraph.megamodel.Dsml#getEcoreDiagrams <em>Ecore Diagrams</em>}'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Ecore Diagrams</em>'.
	 * @see org.isoe.diagraph.megamodel.Dsml#getEcoreDiagrams()
	 * @see #getDsml()
	 * @generated
	 */
   EReference getDsml_EcoreDiagrams();

   /**
	 * Returns the meta object for class '{@link org.isoe.diagraph.megamodel.MegamodelElement <em>Element</em>}'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Element</em>'.
	 * @see org.isoe.diagraph.megamodel.MegamodelElement
	 * @generated
	 */
   EClass getMegamodelElement();

   /**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.megamodel.MegamodelElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.isoe.diagraph.megamodel.MegamodelElement#getName()
	 * @see #getMegamodelElement()
	 * @generated
	 */
   EAttribute getMegamodelElement_Name();

   /**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.megamodel.MegamodelElement#getURI <em>URI</em>}'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>URI</em>'.
	 * @see org.isoe.diagraph.megamodel.MegamodelElement#getURI()
	 * @see #getMegamodelElement()
	 * @generated
	 */
   EAttribute getMegamodelElement_URI();

   /**
	 * Returns the meta object for class '{@link org.isoe.diagraph.megamodel.RelatedTo <em>Related To</em>}'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Related To</em>'.
	 * @see org.isoe.diagraph.megamodel.RelatedTo
	 * @generated
	 */
   EClass getRelatedTo();

   /**
	 * Returns the meta object for the container reference '{@link org.isoe.diagraph.megamodel.RelatedTo#getFrom <em>From</em>}'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>From</em>'.
	 * @see org.isoe.diagraph.megamodel.RelatedTo#getFrom()
	 * @see #getRelatedTo()
	 * @generated
	 */
   EReference getRelatedTo_From();

   /**
	 * Returns the meta object for the reference '{@link org.isoe.diagraph.megamodel.RelatedTo#getTo <em>To</em>}'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>To</em>'.
	 * @see org.isoe.diagraph.megamodel.RelatedTo#getTo()
	 * @see #getRelatedTo()
	 * @generated
	 */
   EReference getRelatedTo_To();

   /**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.megamodel.RelatedTo#getKind <em>Kind</em>}'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Kind</em>'.
	 * @see org.isoe.diagraph.megamodel.RelatedTo#getKind()
	 * @see #getRelatedTo()
	 * @generated
	 */
   EAttribute getRelatedTo_Kind();

   /**
	 * Returns the meta object for class '{@link org.isoe.diagraph.megamodel.Model <em>Model</em>}'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model</em>'.
	 * @see org.isoe.diagraph.megamodel.Model
	 * @generated
	 */
   EClass getModel();

   /**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.megamodel.Model#getExcerpt <em>Excerpt</em>}'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Excerpt</em>'.
	 * @see org.isoe.diagraph.megamodel.Model#getExcerpt()
	 * @see #getModel()
	 * @generated
	 */
   EAttribute getModel_Excerpt();

   /**
	 * Returns the meta object for the containment reference list '{@link org.isoe.diagraph.megamodel.Model#getNotationDiagrams <em>Notation Diagrams</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Notation Diagrams</em>'.
	 * @see org.isoe.diagraph.megamodel.Model#getNotationDiagrams()
	 * @see #getModel()
	 * @generated
	 */
	EReference getModel_NotationDiagrams();

			/**
	 * Returns the meta object for the container reference '{@link org.isoe.diagraph.megamodel.Model#getDsml <em>Dsml</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Dsml</em>'.
	 * @see org.isoe.diagraph.megamodel.Model#getDsml()
	 * @see #getModel()
	 * @generated
	 */
	EReference getModel_Dsml();

			/**
	 * Returns the meta object for class '{@link org.isoe.diagraph.megamodel.Notation <em>Notation</em>}'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Notation</em>'.
	 * @see org.isoe.diagraph.megamodel.Notation
	 * @generated
	 */
   EClass getNotation();

   /**
	 * Returns the meta object for the containment reference list '{@link org.isoe.diagraph.megamodel.Notation#getNavigations <em>Navigations</em>}'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Navigations</em>'.
	 * @see org.isoe.diagraph.megamodel.Notation#getNavigations()
	 * @see #getNotation()
	 * @generated
	 */
   EReference getNotation_Navigations();

   /**
	 * Returns the meta object for the reference '{@link org.isoe.diagraph.megamodel.Notation#getNotationBridge <em>Notation Bridge</em>}'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Notation Bridge</em>'.
	 * @see org.isoe.diagraph.megamodel.Notation#getNotationBridge()
	 * @see #getNotation()
	 * @generated
	 */
   EReference getNotation_NotationBridge();

   /**
	 * Returns the meta object for the container reference '{@link org.isoe.diagraph.megamodel.Notation#getDsml <em>Dsml</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Dsml</em>'.
	 * @see org.isoe.diagraph.megamodel.Notation#getDsml()
	 * @see #getNotation()
	 * @generated
	 */
	EReference getNotation_Dsml();

			/**
	 * Returns the meta object for class '{@link org.isoe.diagraph.megamodel.NotationDiagram <em>Notation Diagram</em>}'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Notation Diagram</em>'.
	 * @see org.isoe.diagraph.megamodel.NotationDiagram
	 * @generated
	 */
   EClass getNotationDiagram();

   /**
	 * Returns the meta object for class '{@link org.isoe.diagraph.megamodel.Navigation <em>Navigation</em>}'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Navigation</em>'.
	 * @see org.isoe.diagraph.megamodel.Navigation
	 * @generated
	 */
   EClass getNavigation();

   /**
	 * Returns the meta object for the reference '{@link org.isoe.diagraph.megamodel.Navigation#getFrom <em>From</em>}'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>From</em>'.
	 * @see org.isoe.diagraph.megamodel.Navigation#getFrom()
	 * @see #getNavigation()
	 * @generated
	 */
   EReference getNavigation_From();

   /**
	 * Returns the meta object for the reference '{@link org.isoe.diagraph.megamodel.Navigation#getTo <em>To</em>}'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>To</em>'.
	 * @see org.isoe.diagraph.megamodel.Navigation#getTo()
	 * @see #getNavigation()
	 * @generated
	 */
   EReference getNavigation_To();

   /**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.megamodel.Navigation#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.isoe.diagraph.megamodel.Navigation#getId()
	 * @see #getNavigation()
	 * @generated
	 */
   EAttribute getNavigation_Id();

   /**
	 * Returns the meta object for class '{@link org.isoe.diagraph.megamodel.Diagram <em>Diagram</em>}'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Diagram</em>'.
	 * @see org.isoe.diagraph.megamodel.Diagram
	 * @generated
	 */
   EClass getDiagram();

   /**
	 * Returns the meta object for the attribute '{@link org.isoe.diagraph.megamodel.Diagram#getDiagramPictureURI <em>Diagram Picture URI</em>}'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Diagram Picture URI</em>'.
	 * @see org.isoe.diagraph.megamodel.Diagram#getDiagramPictureURI()
	 * @see #getDiagram()
	 * @generated
	 */
   EAttribute getDiagram_DiagramPictureURI();

   /**
	 * Returns the meta object for class '{@link org.isoe.diagraph.megamodel.EcoreDiagram <em>Ecore Diagram</em>}'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Ecore Diagram</em>'.
	 * @see org.isoe.diagraph.megamodel.EcoreDiagram
	 * @generated
	 */
   EClass getEcoreDiagram();

   /**
	 * Returns the meta object for enum '{@link org.isoe.diagraph.megamodel.Relation <em>Relation</em>}'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Relation</em>'.
	 * @see org.isoe.diagraph.megamodel.Relation
	 * @generated
	 */
   EEnum getRelation();

   /**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
   MegamodelFactory getMegamodelFactory();

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
		 * The meta object literal for the '{@link org.isoe.diagraph.megamodel.impl.MegamodelImpl <em>Megamodel</em>}' class.
		 * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.megamodel.impl.MegamodelImpl
		 * @see org.isoe.diagraph.megamodel.impl.MegamodelPackageImpl#getMegamodel()
		 * @generated
		 */
      EClass MEGAMODEL = eINSTANCE.getMegamodel();

      /**
		 * The meta object literal for the '<em><b>Dsmls</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
		 * @generated
		 */
      EReference MEGAMODEL__DSMLS = eINSTANCE.getMegamodel_Dsmls();

      /**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
		 * @generated
		 */
      EAttribute MEGAMODEL__NAME = eINSTANCE.getMegamodel_Name();

      /**
		 * The meta object literal for the '{@link org.isoe.diagraph.megamodel.impl.DsmlImpl <em>Dsml</em>}' class.
		 * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.megamodel.impl.DsmlImpl
		 * @see org.isoe.diagraph.megamodel.impl.MegamodelPackageImpl#getDsml()
		 * @generated
		 */
      EClass DSML = eINSTANCE.getDsml();

      /**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
		 * @generated
		 */
      EAttribute DSML__ID = eINSTANCE.getDsml_Id();

      /**
		 * The meta object literal for the '<em><b>Notation Bridge</b></em>' reference feature.
		 * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
		 * @generated
		 */
      EReference DSML__NOTATION_BRIDGE = eINSTANCE.getDsml_NotationBridge();

      /**
		 * The meta object literal for the '<em><b>Relations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
		 * @generated
		 */
      EReference DSML__RELATIONS = eINSTANCE.getDsml_Relations();

      /**
		 * The meta object literal for the '<em><b>Models</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
		 * @generated
		 */
      EReference DSML__MODELS = eINSTANCE.getDsml_Models();

      /**
		 * The meta object literal for the '<em><b>Notations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
		 * @generated
		 */
      EReference DSML__NOTATIONS = eINSTANCE.getDsml_Notations();

      /**
		 * The meta object literal for the '<em><b>Known As</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
		 * @generated
		 */
      EAttribute DSML__KNOWN_AS = eINSTANCE.getDsml_KnownAs();

      /**
		 * The meta object literal for the '<em><b>Documentation</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
		 * @generated
		 */
      EAttribute DSML__DOCUMENTATION = eINSTANCE.getDsml_Documentation();

      /**
		 * The meta object literal for the '<em><b>Origin</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
		 * @generated
		 */
      EAttribute DSML__ORIGIN = eINSTANCE.getDsml_Origin();

      /**
		 * The meta object literal for the '<em><b>Problem</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
		 * @generated
		 */
      EAttribute DSML__PROBLEM = eINSTANCE.getDsml_Problem();

      /**
		 * The meta object literal for the '<em><b>Context</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
		 * @generated
		 */
      EAttribute DSML__CONTEXT = eINSTANCE.getDsml_Context();

      /**
		 * The meta object literal for the '<em><b>Keywords</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
		 * @generated
		 */
      EAttribute DSML__KEYWORDS = eINSTANCE.getDsml_Keywords();

      /**
		 * The meta object literal for the '<em><b>Build In</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
		 * @generated
		 */
      EAttribute DSML__BUILD_IN = eINSTANCE.getDsml_BuildIn();

      /**
		 * The meta object literal for the '<em><b>Left Parent Details</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
		 * @generated
		 */
      EAttribute DSML__LEFT_PARENT_DETAILS = eINSTANCE.getDsml_LeftParentDetails();

      /**
		 * The meta object literal for the '<em><b>Right Parent Details</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
		 * @generated
		 */
      EAttribute DSML__RIGHT_PARENT_DETAILS = eINSTANCE.getDsml_RightParentDetails();

      /**
		 * The meta object literal for the '<em><b>Require Details</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
		 * @generated
		 */
      EAttribute DSML__REQUIRE_DETAILS = eINSTANCE.getDsml_RequireDetails();

      /**
		 * The meta object literal for the '<em><b>Related Details</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
		 * @generated
		 */
      EAttribute DSML__RELATED_DETAILS = eINSTANCE.getDsml_RelatedDetails();

      /**
		 * The meta object literal for the '<em><b>Variant Details</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
		 * @generated
		 */
      EAttribute DSML__VARIANT_DETAILS = eINSTANCE.getDsml_VariantDetails();

      /**
		 * The meta object literal for the '<em><b>Root Notation</b></em>' reference feature.
		 * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
		 * @generated
		 */
      EReference DSML__ROOT_NOTATION = eINSTANCE.getDsml_RootNotation();

      /**
		 * The meta object literal for the '<em><b>Ecore Diagrams</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
		 * @generated
		 */
      EReference DSML__ECORE_DIAGRAMS = eINSTANCE.getDsml_EcoreDiagrams();

      /**
		 * The meta object literal for the '{@link org.isoe.diagraph.megamodel.impl.MegamodelElementImpl <em>Element</em>}' class.
		 * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.megamodel.impl.MegamodelElementImpl
		 * @see org.isoe.diagraph.megamodel.impl.MegamodelPackageImpl#getMegamodelElement()
		 * @generated
		 */
      EClass MEGAMODEL_ELEMENT = eINSTANCE.getMegamodelElement();

      /**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
		 * @generated
		 */
      EAttribute MEGAMODEL_ELEMENT__NAME = eINSTANCE.getMegamodelElement_Name();

      /**
		 * The meta object literal for the '<em><b>URI</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
		 * @generated
		 */
      EAttribute MEGAMODEL_ELEMENT__URI = eINSTANCE.getMegamodelElement_URI();

      /**
		 * The meta object literal for the '{@link org.isoe.diagraph.megamodel.impl.RelatedToImpl <em>Related To</em>}' class.
		 * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.megamodel.impl.RelatedToImpl
		 * @see org.isoe.diagraph.megamodel.impl.MegamodelPackageImpl#getRelatedTo()
		 * @generated
		 */
      EClass RELATED_TO = eINSTANCE.getRelatedTo();

      /**
		 * The meta object literal for the '<em><b>From</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
		 * @generated
		 */
      EReference RELATED_TO__FROM = eINSTANCE.getRelatedTo_From();

      /**
		 * The meta object literal for the '<em><b>To</b></em>' reference feature.
		 * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
		 * @generated
		 */
      EReference RELATED_TO__TO = eINSTANCE.getRelatedTo_To();

      /**
		 * The meta object literal for the '<em><b>Kind</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
		 * @generated
		 */
      EAttribute RELATED_TO__KIND = eINSTANCE.getRelatedTo_Kind();

      /**
		 * The meta object literal for the '{@link org.isoe.diagraph.megamodel.impl.ModelImpl <em>Model</em>}' class.
		 * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.megamodel.impl.ModelImpl
		 * @see org.isoe.diagraph.megamodel.impl.MegamodelPackageImpl#getModel()
		 * @generated
		 */
      EClass MODEL = eINSTANCE.getModel();

      /**
		 * The meta object literal for the '<em><b>Excerpt</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
		 * @generated
		 */
      EAttribute MODEL__EXCERPT = eINSTANCE.getModel_Excerpt();

      /**
		 * The meta object literal for the '<em><b>Notation Diagrams</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODEL__NOTATION_DIAGRAMS = eINSTANCE.getModel_NotationDiagrams();

						/**
		 * The meta object literal for the '<em><b>Dsml</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODEL__DSML = eINSTANCE.getModel_Dsml();

						/**
		 * The meta object literal for the '{@link org.isoe.diagraph.megamodel.impl.NotationImpl <em>Notation</em>}' class.
		 * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.megamodel.impl.NotationImpl
		 * @see org.isoe.diagraph.megamodel.impl.MegamodelPackageImpl#getNotation()
		 * @generated
		 */
      EClass NOTATION = eINSTANCE.getNotation();

      /**
		 * The meta object literal for the '<em><b>Navigations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
		 * @generated
		 */
      EReference NOTATION__NAVIGATIONS = eINSTANCE.getNotation_Navigations();

      /**
		 * The meta object literal for the '<em><b>Notation Bridge</b></em>' reference feature.
		 * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
		 * @generated
		 */
      EReference NOTATION__NOTATION_BRIDGE = eINSTANCE.getNotation_NotationBridge();

      /**
		 * The meta object literal for the '<em><b>Dsml</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NOTATION__DSML = eINSTANCE.getNotation_Dsml();

						/**
		 * The meta object literal for the '{@link org.isoe.diagraph.megamodel.impl.NotationDiagramImpl <em>Notation Diagram</em>}' class.
		 * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.megamodel.impl.NotationDiagramImpl
		 * @see org.isoe.diagraph.megamodel.impl.MegamodelPackageImpl#getNotationDiagram()
		 * @generated
		 */
      EClass NOTATION_DIAGRAM = eINSTANCE.getNotationDiagram();

      /**
		 * The meta object literal for the '{@link org.isoe.diagraph.megamodel.impl.NavigationImpl <em>Navigation</em>}' class.
		 * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.megamodel.impl.NavigationImpl
		 * @see org.isoe.diagraph.megamodel.impl.MegamodelPackageImpl#getNavigation()
		 * @generated
		 */
      EClass NAVIGATION = eINSTANCE.getNavigation();

      /**
		 * The meta object literal for the '<em><b>From</b></em>' reference feature.
		 * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
		 * @generated
		 */
      EReference NAVIGATION__FROM = eINSTANCE.getNavigation_From();

      /**
		 * The meta object literal for the '<em><b>To</b></em>' reference feature.
		 * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
		 * @generated
		 */
      EReference NAVIGATION__TO = eINSTANCE.getNavigation_To();

      /**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
		 * @generated
		 */
      EAttribute NAVIGATION__ID = eINSTANCE.getNavigation_Id();

      /**
		 * The meta object literal for the '{@link org.isoe.diagraph.megamodel.impl.DiagramImpl <em>Diagram</em>}' class.
		 * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.megamodel.impl.DiagramImpl
		 * @see org.isoe.diagraph.megamodel.impl.MegamodelPackageImpl#getDiagram()
		 * @generated
		 */
      EClass DIAGRAM = eINSTANCE.getDiagram();

      /**
		 * The meta object literal for the '<em><b>Diagram Picture URI</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
		 * @generated
		 */
      EAttribute DIAGRAM__DIAGRAM_PICTURE_URI = eINSTANCE.getDiagram_DiagramPictureURI();

      /**
		 * The meta object literal for the '{@link org.isoe.diagraph.megamodel.impl.EcoreDiagramImpl <em>Ecore Diagram</em>}' class.
		 * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.megamodel.impl.EcoreDiagramImpl
		 * @see org.isoe.diagraph.megamodel.impl.MegamodelPackageImpl#getEcoreDiagram()
		 * @generated
		 */
      EClass ECORE_DIAGRAM = eINSTANCE.getEcoreDiagram();

      /**
		 * The meta object literal for the '{@link org.isoe.diagraph.megamodel.Relation <em>Relation</em>}' enum.
		 * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
		 * @see org.isoe.diagraph.megamodel.Relation
		 * @see org.isoe.diagraph.megamodel.impl.MegamodelPackageImpl#getRelation()
		 * @generated
		 */
      EEnum RELATION = eINSTANCE.getRelation();

   }

} //MegamodelPackage
