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
package org.isoe.diagraph.dgi.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.isoe.diagraph.dgi.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.isoe.diagraph.dgi.DgiPackage
 * @generated
 */
public class DgiAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static DgiPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DgiAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = DgiPackage.eINSTANCE;
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
	protected DgiSwitch<Adapter> modelSwitch =
		new DgiSwitch<Adapter>() {
			@Override
			public Adapter caseDElement(DElement object) {
				return createDElementAdapter();
			}
			@Override
			public Adapter caseDLabelledElement(DLabelledElement object) {
				return createDLabelledElementAdapter();
			}
			@Override
			public Adapter caseDNode(DNode object) {
				return createDNodeAdapter();
			}
			@Override
			public Adapter caseDLink(DLink object) {
				return createDLinkAdapter();
			}
			@Override
			public Adapter caseDPoorReference(DPoorReference object) {
				return createDPoorReferenceAdapter();
			}
			@Override
			public Adapter caseDContainment(DContainment object) {
				return createDContainmentAdapter();
			}
			@Override
			public Adapter caseDLabel(DLabel object) {
				return createDLabelAdapter();
			}
			@Override
			public Adapter caseDGenericElement(DGenericElement object) {
				return createDGenericElementAdapter();
			}
			@Override
			public Adapter caseDConcreteSyntax(DConcreteSyntax object) {
				return createDConcreteSyntaxAdapter();
			}
			@Override
			public Adapter caseDEdge(DEdge object) {
				return createDEdgeAdapter();
			}
			@Override
			public Adapter caseDNamedElement(DNamedElement object) {
				return createDNamedElementAdapter();
			}
			@Override
			public Adapter caseDPointOfView(DPointOfView object) {
				return createDPointOfViewAdapter();
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
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.dgi.DElement <em>DElement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.dgi.DElement
	 * @generated
	 */
	public Adapter createDElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.dgi.DLabelledElement <em>DLabelled Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.dgi.DLabelledElement
	 * @generated
	 */
	public Adapter createDLabelledElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.dgi.DNode <em>DNode</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.dgi.DNode
	 * @generated
	 */
	public Adapter createDNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.dgi.DLink <em>DLink</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.dgi.DLink
	 * @generated
	 */
	public Adapter createDLinkAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.dgi.DPoorReference <em>DPoor Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.dgi.DPoorReference
	 * @generated
	 */
	public Adapter createDPoorReferenceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.dgi.DContainment <em>DContainment</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.dgi.DContainment
	 * @generated
	 */
	public Adapter createDContainmentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.dgi.DLabel <em>DLabel</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.dgi.DLabel
	 * @generated
	 */
	public Adapter createDLabelAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.dgi.DGenericElement <em>DGeneric Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.dgi.DGenericElement
	 * @generated
	 */
	public Adapter createDGenericElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.dgi.DConcreteSyntax <em>DConcrete Syntax</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.dgi.DConcreteSyntax
	 * @generated
	 */
	public Adapter createDConcreteSyntaxAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.dgi.DEdge <em>DEdge</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.dgi.DEdge
	 * @generated
	 */
	public Adapter createDEdgeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.dgi.DNamedElement <em>DNamed Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.dgi.DNamedElement
	 * @generated
	 */
	public Adapter createDNamedElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.dgi.DPointOfView <em>DPoint Of View</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.dgi.DPointOfView
	 * @generated
	 */
	public Adapter createDPointOfViewAdapter() {
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

} //DgiAdapterFactory
