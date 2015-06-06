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
package org.isoe.diagraph.dgi.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.isoe.diagraph.dgi.DConcreteSyntax;
import org.isoe.diagraph.dgi.DContainment;
import org.isoe.diagraph.dgi.DEdge;
import org.isoe.diagraph.dgi.DElement;
import org.isoe.diagraph.dgi.DGenericElement;
import org.isoe.diagraph.dgi.DLabel;
import org.isoe.diagraph.dgi.DLabelledElement;
import org.isoe.diagraph.dgi.DLink;
import org.isoe.diagraph.dgi.DNamedElement;
import org.isoe.diagraph.dgi.DNode;
import org.isoe.diagraph.dgi.DPointOfView;
import org.isoe.diagraph.dgi.DPoorReference;
import org.isoe.diagraph.dgi.DgiFactory;
import org.isoe.diagraph.dgi.DgiPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DgiPackageImpl extends EPackageImpl implements DgiPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dLabelledElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dLinkEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dPoorReferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dContainmentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dLabelEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dGenericElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dConcreteSyntaxEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dEdgeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dNamedElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dPointOfViewEClass = null;

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
	 * @see org.isoe.diagraph.dgi.DgiPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private DgiPackageImpl() {
		super(eNS_URI, DgiFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link DgiPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static DgiPackage init() {
		if (isInited) return (DgiPackage)EPackage.Registry.INSTANCE.getEPackage(DgiPackage.eNS_URI);

		// Obtain or create and register package
		DgiPackageImpl theDgiPackage = (DgiPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof DgiPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new DgiPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theDgiPackage.createPackageContents();

		// Initialize created meta-data
		theDgiPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theDgiPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(DgiPackage.eNS_URI, theDgiPackage);
		return theDgiPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDElement() {
		return dElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDElement_EContainmentModelElement() {
		return (EReference)dElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDElement_EModelElement() {
		return (EReference)dElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDElement_DLowerElements() {
		return (EReference)dElementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDElement_DSuperElements() {
		return (EReference)dElementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDLabelledElement() {
		return dLabelledElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDLabelledElement_DLabels() {
		return (EReference)dLabelledElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDNode() {
		return dNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDNode_DReferences() {
		return (EReference)dNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDNode_DContainments() {
		return (EReference)dNodeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDNode_DLinks() {
		return (EReference)dNodeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDNode_Root() {
		return (EAttribute)dNodeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDNode_Recursive() {
		return (EAttribute)dNodeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDNode_Navigations() {
		return (EAttribute)dNodeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDLink() {
		return dLinkEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDLink_ESourceModelElement() {
		return (EReference)dLinkEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDLink_ETargetModelElement() {
		return (EReference)dLinkEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDLink_ReversedSource() {
		return (EAttribute)dLinkEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDPoorReference() {
		return dPoorReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDPoorReference_ETargetModelElement() {
		return (EReference)dPoorReferenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDContainment() {
		return dContainmentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDContainment_Compartment() {
		return (EAttribute)dContainmentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDContainment_Port() {
		return (EAttribute)dContainmentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDLabel() {
		return dLabelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDLabel_DLabelledElement() {
		return (EReference)dLabelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDLabel_FromSuperElement() {
		return (EAttribute)dLabelEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDLabel_Inferred() {
		return (EAttribute)dLabelEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDLabel_EAttributeModelElement() {
		return (EReference)dLabelEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDGenericElement() {
		return dGenericElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDConcreteSyntax() {
		return dConcreteSyntaxEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDConcreteSyntax_DNodes() {
		return (EReference)dConcreteSyntaxEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDConcreteSyntax_DElements() {
		return (EReference)dConcreteSyntaxEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDConcreteSyntax_DEdges() {
		return (EReference)dConcreteSyntaxEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDConcreteSyntax_RootPointOfView() {
		return (EReference)dConcreteSyntaxEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDConcreteSyntax_PointsOfView() {
		return (EReference)dConcreteSyntaxEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDConcreteSyntax_CurrentPointOfView() {
		return (EReference)dConcreteSyntaxEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDEdge() {
		return dEdgeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDEdge_DSourceNode() {
		return (EReference)dEdgeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDEdge_DTargetNode() {
		return (EReference)dEdgeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDNamedElement() {
		return dNamedElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDNamedElement_Name() {
		return (EAttribute)dNamedElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDPointOfView() {
		return dPointOfViewEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDPointOfView_Children() {
		return (EReference)dPointOfViewEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDPointOfView_Parent() {
		return (EReference)dPointOfViewEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDPointOfView_DRootNode() {
		return (EReference)dPointOfViewEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DgiFactory getDgiFactory() {
		return (DgiFactory)getEFactoryInstance();
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
		dElementEClass = createEClass(DELEMENT);
		createEReference(dElementEClass, DELEMENT__ECONTAINMENT_MODEL_ELEMENT);
		createEReference(dElementEClass, DELEMENT__EMODEL_ELEMENT);
		createEReference(dElementEClass, DELEMENT__DLOWER_ELEMENTS);
		createEReference(dElementEClass, DELEMENT__DSUPER_ELEMENTS);

		dLabelledElementEClass = createEClass(DLABELLED_ELEMENT);
		createEReference(dLabelledElementEClass, DLABELLED_ELEMENT__DLABELS);

		dNodeEClass = createEClass(DNODE);
		createEReference(dNodeEClass, DNODE__DREFERENCES);
		createEReference(dNodeEClass, DNODE__DCONTAINMENTS);
		createEReference(dNodeEClass, DNODE__DLINKS);
		createEAttribute(dNodeEClass, DNODE__ROOT);
		createEAttribute(dNodeEClass, DNODE__RECURSIVE);
		createEAttribute(dNodeEClass, DNODE__NAVIGATIONS);

		dLinkEClass = createEClass(DLINK);
		createEReference(dLinkEClass, DLINK__ESOURCE_MODEL_ELEMENT);
		createEReference(dLinkEClass, DLINK__ETARGET_MODEL_ELEMENT);
		createEAttribute(dLinkEClass, DLINK__REVERSED_SOURCE);

		dPoorReferenceEClass = createEClass(DPOOR_REFERENCE);
		createEReference(dPoorReferenceEClass, DPOOR_REFERENCE__ETARGET_MODEL_ELEMENT);

		dContainmentEClass = createEClass(DCONTAINMENT);
		createEAttribute(dContainmentEClass, DCONTAINMENT__COMPARTMENT);
		createEAttribute(dContainmentEClass, DCONTAINMENT__PORT);

		dLabelEClass = createEClass(DLABEL);
		createEReference(dLabelEClass, DLABEL__DLABELLED_ELEMENT);
		createEAttribute(dLabelEClass, DLABEL__FROM_SUPER_ELEMENT);
		createEAttribute(dLabelEClass, DLABEL__INFERRED);
		createEReference(dLabelEClass, DLABEL__EATTRIBUTE_MODEL_ELEMENT);

		dGenericElementEClass = createEClass(DGENERIC_ELEMENT);

		dConcreteSyntaxEClass = createEClass(DCONCRETE_SYNTAX);
		createEReference(dConcreteSyntaxEClass, DCONCRETE_SYNTAX__DNODES);
		createEReference(dConcreteSyntaxEClass, DCONCRETE_SYNTAX__DELEMENTS);
		createEReference(dConcreteSyntaxEClass, DCONCRETE_SYNTAX__DEDGES);
		createEReference(dConcreteSyntaxEClass, DCONCRETE_SYNTAX__ROOT_POINT_OF_VIEW);
		createEReference(dConcreteSyntaxEClass, DCONCRETE_SYNTAX__POINTS_OF_VIEW);
		createEReference(dConcreteSyntaxEClass, DCONCRETE_SYNTAX__CURRENT_POINT_OF_VIEW);

		dEdgeEClass = createEClass(DEDGE);
		createEReference(dEdgeEClass, DEDGE__DSOURCE_NODE);
		createEReference(dEdgeEClass, DEDGE__DTARGET_NODE);

		dNamedElementEClass = createEClass(DNAMED_ELEMENT);
		createEAttribute(dNamedElementEClass, DNAMED_ELEMENT__NAME);

		dPointOfViewEClass = createEClass(DPOINT_OF_VIEW);
		createEReference(dPointOfViewEClass, DPOINT_OF_VIEW__CHILDREN);
		createEReference(dPointOfViewEClass, DPOINT_OF_VIEW__PARENT);
		createEReference(dPointOfViewEClass, DPOINT_OF_VIEW__DROOT_NODE);
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

		// Obtain other dependent packages
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		dElementEClass.getESuperTypes().add(this.getDLabelledElement());
		dLabelledElementEClass.getESuperTypes().add(this.getDNamedElement());
		dNodeEClass.getESuperTypes().add(this.getDElement());
		dLinkEClass.getESuperTypes().add(this.getDElement());
		dLinkEClass.getESuperTypes().add(this.getDEdge());
		dPoorReferenceEClass.getESuperTypes().add(this.getDEdge());
		dPoorReferenceEClass.getESuperTypes().add(this.getDNamedElement());
		dContainmentEClass.getESuperTypes().add(this.getDLabelledElement());
		dContainmentEClass.getESuperTypes().add(this.getDEdge());
		dLabelEClass.getESuperTypes().add(this.getDNamedElement());
		dGenericElementEClass.getESuperTypes().add(this.getDElement());

		// Initialize classes and features; add operations and parameters
		initEClass(dElementEClass, DElement.class, "DElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDElement_EContainmentModelElement(), theEcorePackage.getEModelElement(), null, "eContainmentModelElement", null, 0, 1, DElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDElement_EModelElement(), theEcorePackage.getEModelElement(), null, "eModelElement", null, 0, 1, DElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDElement_DLowerElements(), this.getDElement(), null, "dLowerElements", null, 0, -1, DElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDElement_DSuperElements(), this.getDElement(), null, "dSuperElements", null, 0, -1, DElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dLabelledElementEClass, DLabelledElement.class, "DLabelledElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDLabelledElement_DLabels(), this.getDLabel(), this.getDLabel_DLabelledElement(), "dLabels", null, 0, -1, DLabelledElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dNodeEClass, DNode.class, "DNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDNode_DReferences(), this.getDPoorReference(), null, "dReferences", null, 0, -1, DNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDNode_DContainments(), this.getDContainment(), null, "dContainments", null, 0, -1, DNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDNode_DLinks(), this.getDLink(), null, "dLinks", null, 0, -1, DNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDNode_Root(), ecorePackage.getEBoolean(), "root", null, 0, 1, DNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDNode_Recursive(), ecorePackage.getEBoolean(), "recursive", null, 0, 1, DNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDNode_Navigations(), theEcorePackage.getEString(), "navigations", null, 0, -1, DNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(dNodeEClass, null, "resolveDiagramRecursion", 0, 1, IS_UNIQUE, IS_ORDERED);

		EOperation op = addEOperation(dNodeEClass, null, "addRootChild", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getDNode(), "node", 0, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(dNodeEClass, null, "resolveTargetRef", 0, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(dNodeEClass, null, "logContainments", 0, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(dNodeEClass, ecorePackage.getEString(), "logReferences", 0, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(dNodeEClass, ecorePackage.getEBoolean(), "checkReferences", 0, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(dNodeEClass, null, "resolveReferenceTargetNodes", 0, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(dNodeEClass, null, "resolveContainmentSourceAndTargetNodes", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(dNodeEClass, this.getDPoorReference(), "findReference", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "name", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(dNodeEClass, this.getDContainment(), "findContainmentByTargetName", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "name", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(dNodeEClass, this.getDContainment(), "findContainmentbyName", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "name", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(dNodeEClass, theEcorePackage.getEReference(), "inferContainment", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getDElement(), "diagramElements", 0, -1, IS_UNIQUE, IS_ORDERED);

		initEClass(dLinkEClass, DLink.class, "DLink", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDLink_ESourceModelElement(), theEcorePackage.getEModelElement(), null, "eSourceModelElement", null, 0, 1, DLink.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDLink_ETargetModelElement(), theEcorePackage.getEModelElement(), null, "eTargetModelElement", null, 0, 1, DLink.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDLink_ReversedSource(), ecorePackage.getEBoolean(), "reversedSource", null, 0, 1, DLink.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dPoorReferenceEClass, DPoorReference.class, "DPoorReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDPoorReference_ETargetModelElement(), theEcorePackage.getEModelElement(), null, "eTargetModelElement", null, 0, 1, DPoorReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dContainmentEClass, DContainment.class, "DContainment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDContainment_Compartment(), ecorePackage.getEBoolean(), "compartment", null, 0, 1, DContainment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDContainment_Port(), theEcorePackage.getEBoolean(), "port", null, 0, 1, DContainment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dLabelEClass, DLabel.class, "DLabel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDLabel_DLabelledElement(), this.getDLabelledElement(), this.getDLabelledElement_DLabels(), "dLabelledElement", null, 0, 1, DLabel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDLabel_FromSuperElement(), ecorePackage.getEBoolean(), "fromSuperElement", null, 0, 1, DLabel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDLabel_Inferred(), ecorePackage.getEBoolean(), "inferred", null, 0, 1, DLabel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDLabel_EAttributeModelElement(), theEcorePackage.getEModelElement(), null, "eAttributeModelElement", null, 0, 1, DLabel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dGenericElementEClass, DGenericElement.class, "DGenericElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(dConcreteSyntaxEClass, DConcreteSyntax.class, "DConcreteSyntax", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDConcreteSyntax_DNodes(), this.getDNode(), null, "dNodes", null, 0, -1, DConcreteSyntax.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDConcreteSyntax_DElements(), this.getDGenericElement(), null, "dElements", null, 0, -1, DConcreteSyntax.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDConcreteSyntax_DEdges(), this.getDEdge(), null, "dEdges", null, 0, -1, DConcreteSyntax.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDConcreteSyntax_RootPointOfView(), this.getDPointOfView(), null, "rootPointOfView", null, 0, 1, DConcreteSyntax.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDConcreteSyntax_PointsOfView(), this.getDPointOfView(), null, "pointsOfView", null, 0, -1, DConcreteSyntax.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDConcreteSyntax_CurrentPointOfView(), this.getDPointOfView(), null, "currentPointOfView", null, 0, 1, DConcreteSyntax.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(dEdgeEClass, DEdge.class, "DEdge", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDEdge_DSourceNode(), this.getDNode(), null, "dSourceNode", null, 0, 1, DEdge.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDEdge_DTargetNode(), this.getDNode(), null, "dTargetNode", null, 0, 1, DEdge.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dNamedElementEClass, DNamedElement.class, "DNamedElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDNamedElement_Name(), ecorePackage.getEString(), "name", null, 0, 1, DNamedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dPointOfViewEClass, DPointOfView.class, "DPointOfView", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDPointOfView_Children(), this.getDPointOfView(), this.getDPointOfView_Parent(), "children", null, 0, -1, DPointOfView.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDPointOfView_Parent(), this.getDPointOfView(), this.getDPointOfView_Children(), "parent", null, 0, 1, DPointOfView.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDPointOfView_DRootNode(), this.getDNode(), null, "dRootNode", null, 1, 1, DPointOfView.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //DgiPackageImpl
