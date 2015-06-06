/**
 */
package org.isoe.diagraph.diagraph.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.isoe.diagraph.diagraph.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.isoe.diagraph.diagraph.DiagraphPackage
 * @generated
 */
public class DiagraphAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static DiagraphPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DiagraphAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = DiagraphPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DiagraphSwitch<Adapter> modelSwitch =
		new DiagraphSwitch<Adapter>() {
			@Override
			public Adapter caseDEdge(DEdge object) {
				return createDEdgeAdapter();
			}
			@Override
			public Adapter caseDGraphElement(DGraphElement object) {
				return createDGraphElementAdapter();
			}
			@Override
			public Adapter caseDNode(DNode object) {
				return createDNodeAdapter();
			}
			@Override
			public Adapter caseDLabeledEdge(DLabeledEdge object) {
				return createDLabeledEdgeAdapter();
			}
			@Override
			public Adapter caseDReference(DReference object) {
				return createDReferenceAdapter();
			}
			@Override
			public Adapter caseDNestedEdge(DNestedEdge object) {
				return createDNestedEdgeAdapter();
			}
			@Override
			public Adapter caseDGraph(DGraph object) {
				return createDGraphAdapter();
			}
			@Override
			public Adapter caseDLabeledElement(DLabeledElement object) {
				return createDLabeledElementAdapter();
			}
			@Override
			public Adapter caseDOwnedElement(DOwnedElement object) {
				return createDOwnedElementAdapter();
			}
			@Override
			public Adapter caseDOwnedEdge(DOwnedEdge object) {
				return createDOwnedEdgeAdapter();
			}
			@Override
			public Adapter caseDCompartmentEdge(DCompartmentEdge object) {
				return createDCompartmentEdgeAdapter();
			}
			@Override
			public Adapter caseDPointOfView(DPointOfView object) {
				return createDPointOfViewAdapter();
			}
			@Override
			public Adapter caseDViewNavigation(DViewNavigation object) {
				return createDViewNavigationAdapter();
			}
			@Override
			public Adapter caseDAffixedEdge(DAffixedEdge object) {
				return createDAffixedEdgeAdapter();
			}
			@Override
			public Adapter caseDLabel(DLabel object) {
				return createDLabelAdapter();
			}
			@Override
			public Adapter caseDLineEdge(DLineEdge object) {
				return createDLineEdgeAdapter();
			}
			@Override
			public Adapter caseDNavigationEdge(DNavigationEdge object) {
				return createDNavigationEdgeAdapter();
			}
			@Override
			public Adapter caseDGeneric(DGeneric object) {
				return createDGenericAdapter();
			}
			@Override
			public Adapter caseDContainment(DContainment object) {
				return createDContainmentAdapter();
			}
			@Override
			public Adapter caseDSimpleEdge(DSimpleEdge object) {
				return createDSimpleEdgeAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.diagraph.DEdge <em>DEdge</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.diagraph.DEdge
	 * @generated
	 */
	public Adapter createDEdgeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.diagraph.DGraphElement <em>DGraph Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.diagraph.DGraphElement
	 * @generated
	 */
	public Adapter createDGraphElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.diagraph.DNode <em>DNode</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.diagraph.DNode
	 * @generated
	 */
	public Adapter createDNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.diagraph.DLabeledEdge <em>DLabeled Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.diagraph.DLabeledEdge
	 * @generated
	 */
	public Adapter createDLabeledEdgeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.diagraph.DReference <em>DReference</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.diagraph.DReference
	 * @generated
	 */
	public Adapter createDReferenceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.diagraph.DNestedEdge <em>DNested Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.diagraph.DNestedEdge
	 * @generated
	 */
	public Adapter createDNestedEdgeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.diagraph.DGraph <em>DGraph</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.diagraph.DGraph
	 * @generated
	 */
	public Adapter createDGraphAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.diagraph.DLabeledElement <em>DLabeled Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.diagraph.DLabeledElement
	 * @generated
	 */
	public Adapter createDLabeledElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.diagraph.DOwnedElement <em>DOwned Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.diagraph.DOwnedElement
	 * @generated
	 */
	public Adapter createDOwnedElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.diagraph.DOwnedEdge <em>DOwned Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.diagraph.DOwnedEdge
	 * @generated
	 */
	public Adapter createDOwnedEdgeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.diagraph.DCompartmentEdge <em>DCompartment Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.diagraph.DCompartmentEdge
	 * @generated
	 */
	public Adapter createDCompartmentEdgeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.diagraph.DPointOfView <em>DPoint Of View</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.diagraph.DPointOfView
	 * @generated
	 */
	public Adapter createDPointOfViewAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.diagraph.DViewNavigation <em>DView Navigation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.diagraph.DViewNavigation
	 * @generated
	 */
	public Adapter createDViewNavigationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.diagraph.DAffixedEdge <em>DAffixed Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.diagraph.DAffixedEdge
	 * @generated
	 */
	public Adapter createDAffixedEdgeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.diagraph.DLabel <em>DLabel</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.diagraph.DLabel
	 * @generated
	 */
	public Adapter createDLabelAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.diagraph.DLineEdge <em>DLine Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.diagraph.DLineEdge
	 * @generated
	 */
	public Adapter createDLineEdgeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.diagraph.DNavigationEdge <em>DNavigation Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.diagraph.DNavigationEdge
	 * @generated
	 */
	public Adapter createDNavigationEdgeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.diagraph.DGeneric <em>DGeneric</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.diagraph.DGeneric
	 * @generated
	 */
	public Adapter createDGenericAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.diagraph.DContainment <em>DContainment</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.diagraph.DContainment
	 * @generated
	 */
	public Adapter createDContainmentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.diagraph.DSimpleEdge <em>DSimple Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.diagraph.DSimpleEdge
	 * @generated
	 */
	public Adapter createDSimpleEdgeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //DiagraphAdapterFactory
