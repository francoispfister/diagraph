/**
 */
package org.isoe.diagraph.diagraph.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.isoe.diagraph.diagraph.*;

import org.isoe.diagraph.diagraph.helpers.IGraphHandler;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DiagraphFactoryImpl extends EFactoryImpl implements DiagraphFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DiagraphFactory init() {
		try {
			DiagraphFactory theDiagraphFactory = (DiagraphFactory)EPackage.Registry.INSTANCE.getEFactory(DiagraphPackage.eNS_URI);
			if (theDiagraphFactory != null) {
				return theDiagraphFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new DiagraphFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DiagraphFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case DiagraphPackage.DNODE: return createDNode();
			case DiagraphPackage.DLABELED_EDGE: return createDLabeledEdge();
			case DiagraphPackage.DREFERENCE: return createDReference();
			case DiagraphPackage.DNESTED_EDGE: return createDNestedEdge();
			case DiagraphPackage.DGRAPH: return createDGraph();
			case DiagraphPackage.DCOMPARTMENT_EDGE: return createDCompartmentEdge();
			case DiagraphPackage.DPOINT_OF_VIEW: return createDPointOfView();
			case DiagraphPackage.DVIEW_NAVIGATION: return createDViewNavigation();
			case DiagraphPackage.DAFFIXED_EDGE: return createDAffixedEdge();
			case DiagraphPackage.DLABEL: return createDLabel();
			case DiagraphPackage.DNAVIGATION_EDGE: return createDNavigationEdge();
			case DiagraphPackage.DGENERIC: return createDGeneric();
			case DiagraphPackage.DCONTAINMENT: return createDContainment();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case DiagraphPackage.DSHAPE:
				return createDShapeFromString(eDataType, initialValue);
			case DiagraphPackage.DGRAPH_HANDLER:
				return createDGraphHandlerFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case DiagraphPackage.DSHAPE:
				return convertDShapeToString(eDataType, instanceValue);
			case DiagraphPackage.DGRAPH_HANDLER:
				return convertDGraphHandlerToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DNode createDNode() {
		DNodeImpl dNode = new DNodeImpl();
		return dNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DLabeledEdge createDLabeledEdge() {
		DLabeledEdgeImpl dLabeledEdge = new DLabeledEdgeImpl();
		return dLabeledEdge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DReference createDReference() {
		DReferenceImpl dReference = new DReferenceImpl();
		return dReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DNestedEdge createDNestedEdge() {
		DNestedEdgeImpl dNestedEdge = new DNestedEdgeImpl();
		return dNestedEdge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DGraph createDGraph() {
		DGraphImpl dGraph = new DGraphImpl();
		return dGraph;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DCompartmentEdge createDCompartmentEdge() {
		DCompartmentEdgeImpl dCompartmentEdge = new DCompartmentEdgeImpl();
		return dCompartmentEdge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DPointOfView createDPointOfView() {
		DPointOfViewImpl dPointOfView = new DPointOfViewImpl();
		return dPointOfView;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DViewNavigation createDViewNavigation() {
		DViewNavigationImpl dViewNavigation = new DViewNavigationImpl();
		return dViewNavigation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DAffixedEdge createDAffixedEdge() {
		DAffixedEdgeImpl dAffixedEdge = new DAffixedEdgeImpl();
		return dAffixedEdge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DLabel createDLabel() {
		DLabelImpl dLabel = new DLabelImpl();
		return dLabel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DNavigationEdge createDNavigationEdge() {
		DNavigationEdgeImpl dNavigationEdge = new DNavigationEdgeImpl();
		return dNavigationEdge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DGeneric createDGeneric() {
		DGenericImpl dGeneric = new DGenericImpl();
		return dGeneric;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DContainment createDContainment() {
		DContainmentImpl dContainment = new DContainmentImpl();
		return dContainment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DShape createDShapeFromString(EDataType eDataType, String initialValue) {
		DShape result = DShape.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDShapeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IGraphHandler createDGraphHandlerFromString(EDataType eDataType, String initialValue) {
		return (IGraphHandler)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDGraphHandlerToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DiagraphPackage getDiagraphPackage() {
		return (DiagraphPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static DiagraphPackage getPackage() {
		return DiagraphPackage.eINSTANCE;
	}

} //DiagraphFactoryImpl
