/**
 */
package org.isoe.diagraph.diagraph.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.isoe.diagraph.diagraph.DAffixedEdge;
import org.isoe.diagraph.diagraph.DCompartmentEdge;
import org.isoe.diagraph.diagraph.DContainment;
import org.isoe.diagraph.diagraph.DEdge;
import org.isoe.diagraph.diagraph.DGeneric;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.diagraph.DGraphElement;
import org.isoe.diagraph.diagraph.DLabel;
import org.isoe.diagraph.diagraph.DLabeledEdge;
import org.isoe.diagraph.diagraph.DLabeledElement;
import org.isoe.diagraph.diagraph.DLineEdge;
import org.isoe.diagraph.diagraph.DNavigationEdge;
import org.isoe.diagraph.diagraph.DNestedEdge;
import org.isoe.diagraph.diagraph.DNode;
import org.isoe.diagraph.diagraph.DOwnedEdge;
import org.isoe.diagraph.diagraph.DOwnedElement;
import org.isoe.diagraph.diagraph.DPointOfView;
import org.isoe.diagraph.diagraph.DReference;
import org.isoe.diagraph.diagraph.DShape;
import org.isoe.diagraph.diagraph.DSimpleEdge;
import org.isoe.diagraph.diagraph.DViewNavigation;
import org.isoe.diagraph.diagraph.DiagraphFactory;
import org.isoe.diagraph.diagraph.DiagraphPackage;

import org.isoe.diagraph.diagraph.helpers.IGraphHandler;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DiagraphPackageImpl extends EPackageImpl implements DiagraphPackage {
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
	private EClass dGraphElementEClass = null;

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
	private EClass dLabeledEdgeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dReferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dNestedEdgeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dGraphEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dLabeledElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dOwnedElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dOwnedEdgeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dCompartmentEdgeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dPointOfViewEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dViewNavigationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dAffixedEdgeEClass = null;

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
	private EClass dLineEdgeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dNavigationEdgeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dGenericEClass = null;

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
	private EClass dSimpleEdgeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum dShapeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType dGraphHandlerEDataType = null;

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
	 * @see org.isoe.diagraph.diagraph.DiagraphPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private DiagraphPackageImpl() {
		super(eNS_URI, DiagraphFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link DiagraphPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static DiagraphPackage init() {
		if (isInited) return (DiagraphPackage)EPackage.Registry.INSTANCE.getEPackage(DiagraphPackage.eNS_URI);

		// Obtain or create and register package
		DiagraphPackageImpl theDiagraphPackage = (DiagraphPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof DiagraphPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new DiagraphPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theDiagraphPackage.createPackageContents();

		// Initialize created meta-data
		theDiagraphPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theDiagraphPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(DiagraphPackage.eNS_URI, theDiagraphPackage);
		return theDiagraphPackage;
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
	public EReference getDEdge_Target() {
		return (EReference)dEdgeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDEdge_TargetReference() {
		return (EReference)dEdgeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDEdge_Propagated() {
		return (EAttribute)dEdgeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDGraphElement() {
		return dGraphElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDGraphElement_Name() {
		return (EAttribute)dGraphElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDGraphElement_SemanticRole() {
		return (EReference)dGraphElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDGraphElement_Icon() {
		return (EAttribute)dGraphElementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDGraphElement_Graph() {
		return (EReference)dGraphElementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDGraphElement_Abztract() {
		return (EAttribute)dGraphElementEClass.getEStructuralFeatures().get(4);
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
	public EReference getDNode_ViewNavigation() {
		return (EReference)dNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDNode_Shape() {
		return (EAttribute)dNodeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDNode_Layout() {
		return (EAttribute)dNodeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDNode_NavigationLink() {
		return (EAttribute)dNodeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDNode_Containments() {
		return (EReference)dNodeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDLabeledEdge() {
		return dLabeledEdgeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDLabeledEdge_SourceReference() {
		return (EReference)dLabeledEdgeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDReference() {
		return dReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDNestedEdge() {
		return dNestedEdgeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDNestedEdge_Source() {
		return (EReference)dNestedEdgeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDGraph() {
		return dGraphEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDGraph_Elements() {
		return (EReference)dGraphEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDGraph_PointOfView() {
		return (EReference)dGraphEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDGraph_ViewName() {
		return (EAttribute)dGraphEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDGraph_Facade1() {
		return (EAttribute)dGraphEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDGraph_Facade2() {
		return (EAttribute)dGraphEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDLabeledElement() {
		return dLabeledElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDLabeledElement_EClaz() {
		return (EReference)dLabeledElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDLabeledElement_Dlabels() {
		return (EReference)dLabeledElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDLabeledElement_Labls() {
		return (EAttribute)dLabeledElementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDLabeledElement_Expression() {
		return (EAttribute)dLabeledElementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDOwnedElement() {
		return dOwnedElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDOwnedElement_Owner() {
		return (EReference)dOwnedElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDOwnedEdge() {
		return dOwnedEdgeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDCompartmentEdge() {
		return dCompartmentEdgeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDCompartmentEdge_PartitionName() {
		return (EAttribute)dCompartmentEdgeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDCompartmentEdge_Depth() {
		return (EAttribute)dCompartmentEdgeEClass.getEStructuralFeatures().get(1);
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
	public EClass getDViewNavigation() {
		return dViewNavigationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDViewNavigation_Id() {
		return (EAttribute)dViewNavigationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDViewNavigation_NavigationTarget() {
		return (EReference)dViewNavigationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDViewNavigation_NavigationSource() {
		return (EReference)dViewNavigationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDAffixedEdge() {
		return dAffixedEdgeEClass;
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
	public EReference getDLabel_Attribute() {
		return (EReference)dLabelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDLabel_Propagated() {
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
	public EAttribute getDLabel_Abztract() {
		return (EAttribute)dLabelEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDLineEdge() {
		return dLineEdgeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDLineEdge_Arrows() {
		return (EAttribute)dLineEdgeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDNavigationEdge() {
		return dNavigationEdgeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDGeneric() {
		return dGenericEClass;
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
	public EReference getDContainment_Node() {
		return (EReference)dContainmentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDContainment_Name() {
		return (EAttribute)dContainmentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDContainment_Edges() {
		return (EReference)dContainmentEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDSimpleEdge() {
		return dSimpleEdgeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDSimpleEdge_Source() {
		return (EReference)dSimpleEdgeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getDShape() {
		return dShapeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getDGraphHandler() {
		return dGraphHandlerEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DiagraphFactory getDiagraphFactory() {
		return (DiagraphFactory)getEFactoryInstance();
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
		dEdgeEClass = createEClass(DEDGE);
		createEReference(dEdgeEClass, DEDGE__TARGET);
		createEReference(dEdgeEClass, DEDGE__TARGET_REFERENCE);
		createEAttribute(dEdgeEClass, DEDGE__PROPAGATED);

		dGraphElementEClass = createEClass(DGRAPH_ELEMENT);
		createEAttribute(dGraphElementEClass, DGRAPH_ELEMENT__NAME);
		createEReference(dGraphElementEClass, DGRAPH_ELEMENT__SEMANTIC_ROLE);
		createEAttribute(dGraphElementEClass, DGRAPH_ELEMENT__ICON);
		createEReference(dGraphElementEClass, DGRAPH_ELEMENT__GRAPH);
		createEAttribute(dGraphElementEClass, DGRAPH_ELEMENT__ABZTRACT);

		dNodeEClass = createEClass(DNODE);
		createEReference(dNodeEClass, DNODE__VIEW_NAVIGATION);
		createEAttribute(dNodeEClass, DNODE__SHAPE);
		createEAttribute(dNodeEClass, DNODE__LAYOUT);
		createEAttribute(dNodeEClass, DNODE__NAVIGATION_LINK);
		createEReference(dNodeEClass, DNODE__CONTAINMENTS);

		dLabeledEdgeEClass = createEClass(DLABELED_EDGE);
		createEReference(dLabeledEdgeEClass, DLABELED_EDGE__SOURCE_REFERENCE);

		dReferenceEClass = createEClass(DREFERENCE);

		dNestedEdgeEClass = createEClass(DNESTED_EDGE);
		createEReference(dNestedEdgeEClass, DNESTED_EDGE__SOURCE);

		dGraphEClass = createEClass(DGRAPH);
		createEReference(dGraphEClass, DGRAPH__ELEMENTS);
		createEReference(dGraphEClass, DGRAPH__POINT_OF_VIEW);
		createEAttribute(dGraphEClass, DGRAPH__VIEW_NAME);
		createEAttribute(dGraphEClass, DGRAPH__FACADE1);
		createEAttribute(dGraphEClass, DGRAPH__FACADE2);

		dLabeledElementEClass = createEClass(DLABELED_ELEMENT);
		createEReference(dLabeledElementEClass, DLABELED_ELEMENT__ECLAZ);
		createEReference(dLabeledElementEClass, DLABELED_ELEMENT__DLABELS);
		createEAttribute(dLabeledElementEClass, DLABELED_ELEMENT__LABLS);
		createEAttribute(dLabeledElementEClass, DLABELED_ELEMENT__EXPRESSION);

		dOwnedElementEClass = createEClass(DOWNED_ELEMENT);
		createEReference(dOwnedElementEClass, DOWNED_ELEMENT__OWNER);

		dOwnedEdgeEClass = createEClass(DOWNED_EDGE);

		dCompartmentEdgeEClass = createEClass(DCOMPARTMENT_EDGE);
		createEAttribute(dCompartmentEdgeEClass, DCOMPARTMENT_EDGE__PARTITION_NAME);
		createEAttribute(dCompartmentEdgeEClass, DCOMPARTMENT_EDGE__DEPTH);

		dPointOfViewEClass = createEClass(DPOINT_OF_VIEW);

		dViewNavigationEClass = createEClass(DVIEW_NAVIGATION);
		createEAttribute(dViewNavigationEClass, DVIEW_NAVIGATION__ID);
		createEReference(dViewNavigationEClass, DVIEW_NAVIGATION__NAVIGATION_TARGET);
		createEReference(dViewNavigationEClass, DVIEW_NAVIGATION__NAVIGATION_SOURCE);

		dAffixedEdgeEClass = createEClass(DAFFIXED_EDGE);

		dLabelEClass = createEClass(DLABEL);
		createEReference(dLabelEClass, DLABEL__ATTRIBUTE);
		createEAttribute(dLabelEClass, DLABEL__PROPAGATED);
		createEAttribute(dLabelEClass, DLABEL__INFERRED);
		createEAttribute(dLabelEClass, DLABEL__ABZTRACT);

		dLineEdgeEClass = createEClass(DLINE_EDGE);
		createEAttribute(dLineEdgeEClass, DLINE_EDGE__ARROWS);

		dNavigationEdgeEClass = createEClass(DNAVIGATION_EDGE);

		dGenericEClass = createEClass(DGENERIC);

		dContainmentEClass = createEClass(DCONTAINMENT);
		createEReference(dContainmentEClass, DCONTAINMENT__NODE);
		createEAttribute(dContainmentEClass, DCONTAINMENT__NAME);
		createEReference(dContainmentEClass, DCONTAINMENT__EDGES);

		dSimpleEdgeEClass = createEClass(DSIMPLE_EDGE);
		createEReference(dSimpleEdgeEClass, DSIMPLE_EDGE__SOURCE);

		// Create enums
		dShapeEEnum = createEEnum(DSHAPE);

		// Create data types
		dGraphHandlerEDataType = createEDataType(DGRAPH_HANDLER);
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
		dEdgeEClass.getESuperTypes().add(this.getDGraphElement());
		dNodeEClass.getESuperTypes().add(this.getDLabeledElement());
		dNodeEClass.getESuperTypes().add(this.getDOwnedElement());
		dLabeledEdgeEClass.getESuperTypes().add(this.getDOwnedEdge());
		dLabeledEdgeEClass.getESuperTypes().add(this.getDLabeledElement());
		dLabeledEdgeEClass.getESuperTypes().add(this.getDLineEdge());
		dReferenceEClass.getESuperTypes().add(this.getDLineEdge());
		dNestedEdgeEClass.getESuperTypes().add(this.getDOwnedEdge());
		dLabeledElementEClass.getESuperTypes().add(this.getDGraphElement());
		dOwnedEdgeEClass.getESuperTypes().add(this.getDOwnedElement());
		dOwnedEdgeEClass.getESuperTypes().add(this.getDEdge());
		dCompartmentEdgeEClass.getESuperTypes().add(this.getDNestedEdge());
		dPointOfViewEClass.getESuperTypes().add(this.getDNode());
		dAffixedEdgeEClass.getESuperTypes().add(this.getDNestedEdge());
		dLineEdgeEClass.getESuperTypes().add(this.getDSimpleEdge());
		dNavigationEdgeEClass.getESuperTypes().add(this.getDSimpleEdge());
		dGenericEClass.getESuperTypes().add(this.getDLabeledElement());
		dSimpleEdgeEClass.getESuperTypes().add(this.getDEdge());

		// Initialize classes, features, and operations; add parameters
		initEClass(dEdgeEClass, DEdge.class, "DEdge", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDEdge_Target(), this.getDNode(), null, "target", null, 1, 1, DEdge.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDEdge_TargetReference(), ecorePackage.getEReference(), null, "targetReference", null, 0, 1, DEdge.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDEdge_Propagated(), ecorePackage.getEBoolean(), "propagated", null, 0, 1, DEdge.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dGraphElementEClass, DGraphElement.class, "DGraphElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDGraphElement_Name(), ecorePackage.getEString(), "name", null, 0, 1, DGraphElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDGraphElement_SemanticRole(), ecorePackage.getENamedElement(), null, "semanticRole", null, 0, 1, DGraphElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDGraphElement_Icon(), ecorePackage.getEString(), "icon", null, 0, 1, DGraphElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDGraphElement_Graph(), this.getDGraph(), this.getDGraph_Elements(), "graph", null, 0, 1, DGraphElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDGraphElement_Abztract(), ecorePackage.getEBoolean(), "abztract", null, 0, 1, DGraphElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dNodeEClass, DNode.class, "DNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDNode_ViewNavigation(), this.getDViewNavigation(), this.getDViewNavigation_NavigationSource(), "viewNavigation", null, 0, 1, DNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDNode_Shape(), this.getDShape(), "shape", null, 0, 1, DNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDNode_Layout(), ecorePackage.getEBoolean(), "layout", null, 0, 1, DNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDNode_NavigationLink(), ecorePackage.getEString(), "navigationLink", null, 0, 1, DNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDNode_Containments(), this.getDContainment(), this.getDContainment_Node(), "containments", null, 0, -1, DNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dLabeledEdgeEClass, DLabeledEdge.class, "DLabeledEdge", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDLabeledEdge_SourceReference(), ecorePackage.getEReference(), null, "sourceReference", null, 0, 1, DLabeledEdge.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dReferenceEClass, DReference.class, "DReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(dNestedEdgeEClass, DNestedEdge.class, "DNestedEdge", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDNestedEdge_Source(), this.getDContainment(), null, "source", null, 1, 1, DNestedEdge.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dGraphEClass, DGraph.class, "DGraph", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDGraph_Elements(), this.getDGraphElement(), this.getDGraphElement_Graph(), "elements", null, 0, -1, DGraph.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDGraph_PointOfView(), this.getDPointOfView(), null, "pointOfView", null, 1, 1, DGraph.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDGraph_ViewName(), ecorePackage.getEString(), "viewName", null, 0, 1, DGraph.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDGraph_Facade1(), this.getDGraphHandler(), "facade1", null, 0, 1, DGraph.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDGraph_Facade2(), this.getDGraphHandler(), "facade2", null, 0, 1, DGraph.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dLabeledElementEClass, DLabeledElement.class, "DLabeledElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDLabeledElement_EClaz(), ecorePackage.getEClass(), null, "eClaz", null, 0, 1, DLabeledElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDLabeledElement_Dlabels(), this.getDLabel(), null, "dlabels", null, 0, -1, DLabeledElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDLabeledElement_Labls(), ecorePackage.getEString(), "labls", null, 0, -1, DLabeledElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDLabeledElement_Expression(), ecorePackage.getEString(), "expression", null, 0, 1, DLabeledElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dOwnedElementEClass, DOwnedElement.class, "DOwnedElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDOwnedElement_Owner(), this.getDNode(), null, "owner", null, 0, 1, DOwnedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dOwnedEdgeEClass, DOwnedEdge.class, "DOwnedEdge", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(dCompartmentEdgeEClass, DCompartmentEdge.class, "DCompartmentEdge", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDCompartmentEdge_PartitionName(), ecorePackage.getEString(), "partitionName", null, 0, 1, DCompartmentEdge.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDCompartmentEdge_Depth(), ecorePackage.getEInt(), "depth", null, 0, 1, DCompartmentEdge.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dPointOfViewEClass, DPointOfView.class, "DPointOfView", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(dViewNavigationEClass, DViewNavigation.class, "DViewNavigation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDViewNavigation_Id(), ecorePackage.getEString(), "id", null, 0, 1, DViewNavigation.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDViewNavigation_NavigationTarget(), this.getDGraph(), null, "navigationTarget", null, 0, 1, DViewNavigation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDViewNavigation_NavigationSource(), this.getDNode(), this.getDNode_ViewNavigation(), "navigationSource", null, 0, 1, DViewNavigation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dAffixedEdgeEClass, DAffixedEdge.class, "DAffixedEdge", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(dLabelEClass, DLabel.class, "DLabel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDLabel_Attribute(), ecorePackage.getEAttribute(), null, "attribute", null, 0, 1, DLabel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDLabel_Propagated(), ecorePackage.getEBoolean(), "propagated", null, 0, 1, DLabel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDLabel_Inferred(), ecorePackage.getEBoolean(), "inferred", null, 0, 1, DLabel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDLabel_Abztract(), ecorePackage.getEBoolean(), "abztract", null, 0, 1, DLabel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dLineEdgeEClass, DLineEdge.class, "DLineEdge", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDLineEdge_Arrows(), this.getDShape(), "arrows", null, 0, 2, DLineEdge.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dNavigationEdgeEClass, DNavigationEdge.class, "DNavigationEdge", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(dGenericEClass, DGeneric.class, "DGeneric", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(dContainmentEClass, DContainment.class, "DContainment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDContainment_Node(), this.getDNode(), this.getDNode_Containments(), "node", null, 1, 1, DContainment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDContainment_Name(), ecorePackage.getEString(), "name", null, 0, 1, DContainment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDContainment_Edges(), this.getDNestedEdge(), null, "edges", null, 0, -1, DContainment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dSimpleEdgeEClass, DSimpleEdge.class, "DSimpleEdge", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDSimpleEdge_Source(), this.getDNode(), null, "source", null, 1, 1, DSimpleEdge.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(dShapeEEnum, DShape.class, "DShape");
		addEEnumLiteral(dShapeEEnum, DShape.RECTANGLE);
		addEEnumLiteral(dShapeEEnum, DShape.VEE);
		addEEnumLiteral(dShapeEEnum, DShape.TRIANGLE);
		addEEnumLiteral(dShapeEEnum, DShape.DOT);
		addEEnumLiteral(dShapeEEnum, DShape.CIRCLE);
		addEEnumLiteral(dShapeEEnum, DShape.ROUNDED_RECT);

		// Initialize data types
		initEDataType(dGraphHandlerEDataType, IGraphHandler.class, "DGraphHandler", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //DiagraphPackageImpl
