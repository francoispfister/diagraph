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
package org.isoe.diagraph.megamodel.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.isoe.diagraph.megamodel.Diagram;
import org.isoe.diagraph.megamodel.Dsml;
import org.isoe.diagraph.megamodel.EcoreDiagram;
import org.isoe.diagraph.megamodel.Megamodel;
import org.isoe.diagraph.megamodel.MegamodelElement;
import org.isoe.diagraph.megamodel.MegamodelFactory;
import org.isoe.diagraph.megamodel.MegamodelPackage;
import org.isoe.diagraph.megamodel.Model;
import org.isoe.diagraph.megamodel.Navigation;
import org.isoe.diagraph.megamodel.Notation;
import org.isoe.diagraph.megamodel.NotationDiagram;
import org.isoe.diagraph.megamodel.RelatedTo;
import org.isoe.diagraph.megamodel.Relation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class MegamodelPackageImpl extends EPackageImpl implements MegamodelPackage {
   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   private EClass megamodelEClass = null;

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   private EClass dsmlEClass = null;

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   private EClass megamodelElementEClass = null;

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   private EClass relatedToEClass = null;

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   private EClass modelEClass = null;

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   private EClass notationEClass = null;

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   private EClass notationDiagramEClass = null;

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   private EClass navigationEClass = null;

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   private EClass diagramEClass = null;

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   private EClass ecoreDiagramEClass = null;

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   private EEnum relationEEnum = null;

   /**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.isoe.diagraph.megamodel.MegamodelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
   private MegamodelPackageImpl() {
		super(eNS_URI, MegamodelFactory.eINSTANCE);
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   private static boolean isInited = false;

   /**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link MegamodelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
   public static MegamodelPackage init() {
		if (isInited) return (MegamodelPackage)EPackage.Registry.INSTANCE.getEPackage(MegamodelPackage.eNS_URI);

		// Obtain or create and register package
		MegamodelPackageImpl theMegamodelPackage = (MegamodelPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof MegamodelPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new MegamodelPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theMegamodelPackage.createPackageContents();

		// Initialize created meta-data
		theMegamodelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theMegamodelPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(MegamodelPackage.eNS_URI, theMegamodelPackage);
		return theMegamodelPackage;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EClass getMegamodel() {
		return megamodelEClass;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EReference getMegamodel_Dsmls() {
		return (EReference)megamodelEClass.getEStructuralFeatures().get(0);
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EAttribute getMegamodel_Name() {
		return (EAttribute)megamodelEClass.getEStructuralFeatures().get(1);
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EClass getDsml() {
		return dsmlEClass;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EAttribute getDsml_Id() {
		return (EAttribute)dsmlEClass.getEStructuralFeatures().get(0);
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EReference getDsml_NotationBridge() {
		return (EReference)dsmlEClass.getEStructuralFeatures().get(1);
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EReference getDsml_Relations() {
		return (EReference)dsmlEClass.getEStructuralFeatures().get(2);
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EReference getDsml_Models() {
		return (EReference)dsmlEClass.getEStructuralFeatures().get(3);
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EReference getDsml_Notations() {
		return (EReference)dsmlEClass.getEStructuralFeatures().get(4);
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EAttribute getDsml_KnownAs() {
		return (EAttribute)dsmlEClass.getEStructuralFeatures().get(5);
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EAttribute getDsml_Documentation() {
		return (EAttribute)dsmlEClass.getEStructuralFeatures().get(6);
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EAttribute getDsml_Origin() {
		return (EAttribute)dsmlEClass.getEStructuralFeatures().get(7);
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EAttribute getDsml_Problem() {
		return (EAttribute)dsmlEClass.getEStructuralFeatures().get(8);
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EAttribute getDsml_Context() {
		return (EAttribute)dsmlEClass.getEStructuralFeatures().get(9);
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EAttribute getDsml_Keywords() {
		return (EAttribute)dsmlEClass.getEStructuralFeatures().get(10);
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EAttribute getDsml_BuildIn() {
		return (EAttribute)dsmlEClass.getEStructuralFeatures().get(11);
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EAttribute getDsml_LeftParentDetails() {
		return (EAttribute)dsmlEClass.getEStructuralFeatures().get(12);
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EAttribute getDsml_RightParentDetails() {
		return (EAttribute)dsmlEClass.getEStructuralFeatures().get(13);
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EAttribute getDsml_RequireDetails() {
		return (EAttribute)dsmlEClass.getEStructuralFeatures().get(14);
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EAttribute getDsml_RelatedDetails() {
		return (EAttribute)dsmlEClass.getEStructuralFeatures().get(15);
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EAttribute getDsml_VariantDetails() {
		return (EAttribute)dsmlEClass.getEStructuralFeatures().get(16);
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EReference getDsml_RootNotation() {
		return (EReference)dsmlEClass.getEStructuralFeatures().get(17);
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EReference getDsml_EcoreDiagrams() {
		return (EReference)dsmlEClass.getEStructuralFeatures().get(18);
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EClass getMegamodelElement() {
		return megamodelElementEClass;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EAttribute getMegamodelElement_Name() {
		return (EAttribute)megamodelElementEClass.getEStructuralFeatures().get(0);
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EAttribute getMegamodelElement_URI() {
		return (EAttribute)megamodelElementEClass.getEStructuralFeatures().get(1);
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EClass getRelatedTo() {
		return relatedToEClass;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EReference getRelatedTo_From() {
		return (EReference)relatedToEClass.getEStructuralFeatures().get(0);
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EReference getRelatedTo_To() {
		return (EReference)relatedToEClass.getEStructuralFeatures().get(1);
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EAttribute getRelatedTo_Kind() {
		return (EAttribute)relatedToEClass.getEStructuralFeatures().get(2);
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EClass getModel() {
		return modelEClass;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EAttribute getModel_Excerpt() {
		return (EAttribute)modelEClass.getEStructuralFeatures().get(0);
	}

   /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getModel_NotationDiagrams() {
		return (EReference)modelEClass.getEStructuralFeatures().get(1);
	}

			/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getModel_Dsml() {
		return (EReference)modelEClass.getEStructuralFeatures().get(2);
	}

			/**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EClass getNotation() {
		return notationEClass;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EReference getNotation_Navigations() {
		return (EReference)notationEClass.getEStructuralFeatures().get(0);
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EReference getNotation_NotationBridge() {
		return (EReference)notationEClass.getEStructuralFeatures().get(1);
	}

   /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNotation_Dsml() {
		return (EReference)notationEClass.getEStructuralFeatures().get(2);
	}

			/**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EClass getNotationDiagram() {
		return notationDiagramEClass;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EClass getNavigation() {
		return navigationEClass;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EReference getNavigation_From() {
		return (EReference)navigationEClass.getEStructuralFeatures().get(0);
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EReference getNavigation_To() {
		return (EReference)navigationEClass.getEStructuralFeatures().get(1);
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EAttribute getNavigation_Id() {
		return (EAttribute)navigationEClass.getEStructuralFeatures().get(2);
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EClass getDiagram() {
		return diagramEClass;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EAttribute getDiagram_DiagramPictureURI() {
		return (EAttribute)diagramEClass.getEStructuralFeatures().get(0);
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EClass getEcoreDiagram() {
		return ecoreDiagramEClass;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EEnum getRelation() {
		return relationEEnum;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public MegamodelFactory getMegamodelFactory() {
		return (MegamodelFactory)getEFactoryInstance();
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   private boolean isCreated = false;

   /**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		megamodelEClass = createEClass(MEGAMODEL);
		createEReference(megamodelEClass, MEGAMODEL__DSMLS);
		createEAttribute(megamodelEClass, MEGAMODEL__NAME);

		dsmlEClass = createEClass(DSML);
		createEAttribute(dsmlEClass, DSML__ID);
		createEReference(dsmlEClass, DSML__NOTATION_BRIDGE);
		createEReference(dsmlEClass, DSML__RELATIONS);
		createEReference(dsmlEClass, DSML__MODELS);
		createEReference(dsmlEClass, DSML__NOTATIONS);
		createEAttribute(dsmlEClass, DSML__KNOWN_AS);
		createEAttribute(dsmlEClass, DSML__DOCUMENTATION);
		createEAttribute(dsmlEClass, DSML__ORIGIN);
		createEAttribute(dsmlEClass, DSML__PROBLEM);
		createEAttribute(dsmlEClass, DSML__CONTEXT);
		createEAttribute(dsmlEClass, DSML__KEYWORDS);
		createEAttribute(dsmlEClass, DSML__BUILD_IN);
		createEAttribute(dsmlEClass, DSML__LEFT_PARENT_DETAILS);
		createEAttribute(dsmlEClass, DSML__RIGHT_PARENT_DETAILS);
		createEAttribute(dsmlEClass, DSML__REQUIRE_DETAILS);
		createEAttribute(dsmlEClass, DSML__RELATED_DETAILS);
		createEAttribute(dsmlEClass, DSML__VARIANT_DETAILS);
		createEReference(dsmlEClass, DSML__ROOT_NOTATION);
		createEReference(dsmlEClass, DSML__ECORE_DIAGRAMS);

		megamodelElementEClass = createEClass(MEGAMODEL_ELEMENT);
		createEAttribute(megamodelElementEClass, MEGAMODEL_ELEMENT__NAME);
		createEAttribute(megamodelElementEClass, MEGAMODEL_ELEMENT__URI);

		relatedToEClass = createEClass(RELATED_TO);
		createEReference(relatedToEClass, RELATED_TO__FROM);
		createEReference(relatedToEClass, RELATED_TO__TO);
		createEAttribute(relatedToEClass, RELATED_TO__KIND);

		modelEClass = createEClass(MODEL);
		createEAttribute(modelEClass, MODEL__EXCERPT);
		createEReference(modelEClass, MODEL__NOTATION_DIAGRAMS);
		createEReference(modelEClass, MODEL__DSML);

		notationEClass = createEClass(NOTATION);
		createEReference(notationEClass, NOTATION__NAVIGATIONS);
		createEReference(notationEClass, NOTATION__NOTATION_BRIDGE);
		createEReference(notationEClass, NOTATION__DSML);

		notationDiagramEClass = createEClass(NOTATION_DIAGRAM);

		navigationEClass = createEClass(NAVIGATION);
		createEReference(navigationEClass, NAVIGATION__FROM);
		createEReference(navigationEClass, NAVIGATION__TO);
		createEAttribute(navigationEClass, NAVIGATION__ID);

		diagramEClass = createEClass(DIAGRAM);
		createEAttribute(diagramEClass, DIAGRAM__DIAGRAM_PICTURE_URI);

		ecoreDiagramEClass = createEClass(ECORE_DIAGRAM);

		// Create enums
		relationEEnum = createEEnum(RELATION);
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   private boolean isInitialized = false;

   /**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		dsmlEClass.getESuperTypes().add(this.getMegamodelElement());
		modelEClass.getESuperTypes().add(this.getMegamodelElement());
		notationEClass.getESuperTypes().add(this.getMegamodelElement());
		notationDiagramEClass.getESuperTypes().add(this.getDiagram());
		diagramEClass.getESuperTypes().add(this.getMegamodelElement());
		ecoreDiagramEClass.getESuperTypes().add(this.getDiagram());

		// Initialize classes and features; add operations and parameters
		initEClass(megamodelEClass, Megamodel.class, "Megamodel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMegamodel_Dsmls(), this.getDsml(), null, "dsmls", null, 0, -1, Megamodel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMegamodel_Name(), ecorePackage.getEString(), "name", null, 0, 1, Megamodel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dsmlEClass, Dsml.class, "Dsml", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDsml_Id(), ecorePackage.getEInt(), "id", null, 0, 1, Dsml.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDsml_NotationBridge(), ecorePackage.getEObject(), null, "notationBridge", null, 0, 1, Dsml.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDsml_Relations(), this.getRelatedTo(), this.getRelatedTo_From(), "relations", null, 0, -1, Dsml.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDsml_Models(), this.getModel(), this.getModel_Dsml(), "models", null, 0, -1, Dsml.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDsml_Notations(), this.getNotation(), this.getNotation_Dsml(), "notations", null, 0, -1, Dsml.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDsml_KnownAs(), ecorePackage.getEString(), "knownAs", null, 0, -1, Dsml.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDsml_Documentation(), ecorePackage.getEString(), "documentation", null, 0, 1, Dsml.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDsml_Origin(), ecorePackage.getEString(), "origin", null, 0, 1, Dsml.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDsml_Problem(), ecorePackage.getEString(), "problem", null, 0, 1, Dsml.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDsml_Context(), ecorePackage.getEString(), "context", null, 0, -1, Dsml.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDsml_Keywords(), ecorePackage.getEString(), "keywords", null, 0, -1, Dsml.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDsml_BuildIn(), ecorePackage.getEBoolean(), "buildIn", null, 0, 1, Dsml.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDsml_LeftParentDetails(), ecorePackage.getEString(), "leftParentDetails", null, 0, -1, Dsml.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDsml_RightParentDetails(), ecorePackage.getEString(), "rightParentDetails", null, 0, -1, Dsml.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDsml_RequireDetails(), ecorePackage.getEString(), "requireDetails", null, 0, -1, Dsml.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDsml_RelatedDetails(), ecorePackage.getEString(), "relatedDetails", null, 0, -1, Dsml.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDsml_VariantDetails(), ecorePackage.getEString(), "variantDetails", null, 0, -1, Dsml.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDsml_RootNotation(), this.getNotation(), null, "rootNotation", null, 0, 1, Dsml.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDsml_EcoreDiagrams(), this.getEcoreDiagram(), null, "ecoreDiagrams", null, 0, -1, Dsml.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(megamodelElementEClass, MegamodelElement.class, "MegamodelElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMegamodelElement_Name(), ecorePackage.getEString(), "name", null, 0, 1, MegamodelElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMegamodelElement_URI(), ecorePackage.getEString(), "URI", null, 0, 1, MegamodelElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(relatedToEClass, RelatedTo.class, "RelatedTo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRelatedTo_From(), this.getDsml(), this.getDsml_Relations(), "from", null, 0, 1, RelatedTo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRelatedTo_To(), this.getDsml(), null, "to", null, 0, 1, RelatedTo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRelatedTo_Kind(), this.getRelation(), "kind", null, 0, 1, RelatedTo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(modelEClass, Model.class, "Model", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getModel_Excerpt(), ecorePackage.getEString(), "excerpt", null, 0, 1, Model.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getModel_NotationDiagrams(), this.getNotationDiagram(), null, "notationDiagrams", null, 0, -1, Model.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getModel_Dsml(), this.getDsml(), this.getDsml_Models(), "dsml", null, 1, 1, Model.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(notationEClass, Notation.class, "Notation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getNotation_Navigations(), this.getNavigation(), null, "navigations", null, 0, -1, Notation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNotation_NotationBridge(), ecorePackage.getEObject(), null, "notationBridge", null, 0, 1, Notation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNotation_Dsml(), this.getDsml(), this.getDsml_Notations(), "dsml", null, 1, 1, Notation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(notationDiagramEClass, NotationDiagram.class, "NotationDiagram", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(navigationEClass, Navigation.class, "Navigation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getNavigation_From(), this.getNotation(), null, "from", null, 0, 1, Navigation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNavigation_To(), this.getNotation(), null, "to", null, 0, 1, Navigation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNavigation_Id(), ecorePackage.getEString(), "id", null, 0, 1, Navigation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(diagramEClass, Diagram.class, "Diagram", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDiagram_DiagramPictureURI(), ecorePackage.getEString(), "diagramPictureURI", null, 0, 1, Diagram.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ecoreDiagramEClass, EcoreDiagram.class, "EcoreDiagram", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Initialize enums and add enum literals
		initEEnum(relationEEnum, Relation.class, "Relation");
		addEEnumLiteral(relationEEnum, Relation.REQUIRED);
		addEEnumLiteral(relationEEnum, Relation.RELATED);
		addEEnumLiteral(relationEEnum, Relation.VARIANT);
		addEEnumLiteral(relationEEnum, Relation.LEFT_PARENT);
		addEEnumLiteral(relationEEnum, Relation.RIGHT_PARENT);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// diagen
		createDiagenAnnotations();
		// diagraph
		createDiagraphAnnotations();
	}

   /**
	 * Initializes the annotations for <b>diagen</b>.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   protected void createDiagenAnnotations() {
		String source = "diagen";		
		addAnnotation
		  (this, 
		   source, 
		   new String[] {
			 "knownas=megamodel", null,
			 "origin=http://org.isoe.fr", null,
			 "leftparent=simpleworld", "",
			 "rightparent=nil", "",
			 "requires=nil", "",
			 "context=pattern container", null,
			 "related=nil", null
		   });										
	}

   /**
	 * Initializes the annotations for <b>diagraph</b>.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   protected void createDiagraphAnnotations() {
		String source = "diagraph";			
		addAnnotation
		  (megamodelEClass, 
		   source, 
		   new String[] {
			 "node", null,
			 "pov", null
		   });		
		addAnnotation
		  (dsmlEClass, 
		   source, 
		   new String[] {
			 "node", null,
			 "kref=models", null,
			 "kref=notations", null,
			 "kref=ecoreDiagrams", null
		   });		
		addAnnotation
		  (megamodelElementEClass, 
		   source, 
		   new String[] {
			 "label=name", null
		   });		
		addAnnotation
		  (relatedToEClass, 
		   source, 
		   new String[] {
			 "link", null,
			 "cont=Dsml.relations", null,
			 "lsrc=from", null,
			 "ltrg=to", null
		   });		
		addAnnotation
		  (modelEClass, 
		   source, 
		   new String[] {
			 "node", null,
			 "label=name", null,
			 "label=excerpt", null,
			 "kref=notationDiagrams", null
		   });		
		addAnnotation
		  (notationEClass, 
		   source, 
		   new String[] {
			 "node", null
		   });		
		addAnnotation
		  (notationDiagramEClass, 
		   source, 
		   new String[] {
			 "node", null,
			 "label=URI", null
		   });		
		addAnnotation
		  (navigationEClass, 
		   source, 
		   new String[] {
			 "link", null,
			 "lsrc=from", null,
			 "ltrg=to", null
		   });		
		addAnnotation
		  (diagramEClass, 
		   source, 
		   new String[] {
			 "node", null
		   });		
		addAnnotation
		  (ecoreDiagramEClass, 
		   source, 
		   new String[] {
			 "node", null
		   });
	}

} //MegamodelPackageImpl
