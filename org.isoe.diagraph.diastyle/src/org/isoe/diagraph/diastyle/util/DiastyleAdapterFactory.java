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
package org.isoe.diagraph.diastyle.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;

import org.isoe.diagraph.diastyle.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.isoe.diagraph.diastyle.DiastylePackage
 * @generated
 */
public class DiastyleAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static DiastylePackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DiastyleAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = DiastylePackage.eINSTANCE;
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
	protected DiastyleSwitch<Adapter> modelSwitch =
		new DiastyleSwitch<Adapter>() {
			@Override
			public Adapter caseDNodeEdgeStyle(DNodeEdgeStyle object) {
				return createDNodeEdgeStyleAdapter();
			}
			@Override
			public Adapter caseDStyle(DStyle object) {
				return createDStyleAdapter();
			}
			@Override
			public Adapter caseDStyleBridge(DStyleBridge object) {
				return createDStyleBridgeAdapter();
			}
			@Override
			public Adapter caseDNodeStyle(DNodeStyle object) {
				return createDNodeStyleAdapter();
			}
			@Override
			public Adapter caseDEdgeStyle(DEdgeStyle object) {
				return createDEdgeStyleAdapter();
			}
			@Override
			public Adapter caseDNestingEdgeStyle(DNestingEdgeStyle object) {
				return createDNestingEdgeStyleAdapter();
			}
			@Override
			public Adapter caseDBaseStyle(DBaseStyle object) {
				return createDBaseStyleAdapter();
			}
			@Override
			public Adapter caseEModelElement(EModelElement object) {
				return createEModelElementAdapter();
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
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.diastyle.DNodeEdgeStyle <em>DNode Edge Style</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.diastyle.DNodeEdgeStyle
	 * @generated
	 */
	public Adapter createDNodeEdgeStyleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.diastyle.DStyle <em>DStyle</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.diastyle.DStyle
	 * @generated
	 */
	public Adapter createDStyleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.diastyle.DStyleBridge <em>DStyle Bridge</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.diastyle.DStyleBridge
	 * @generated
	 */
	public Adapter createDStyleBridgeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.diastyle.DNodeStyle <em>DNode Style</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.diastyle.DNodeStyle
	 * @generated
	 */
	public Adapter createDNodeStyleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.diastyle.DEdgeStyle <em>DEdge Style</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.diastyle.DEdgeStyle
	 * @generated
	 */
	public Adapter createDEdgeStyleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.diastyle.DNestingEdgeStyle <em>DNesting Edge Style</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.diastyle.DNestingEdgeStyle
	 * @generated
	 */
	public Adapter createDNestingEdgeStyleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.diastyle.DBaseStyle <em>DBase Style</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.diastyle.DBaseStyle
	 * @generated
	 */
	public Adapter createDBaseStyleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emf.ecore.EModelElement <em>EModel Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emf.ecore.EModelElement
	 * @generated
	 */
	public Adapter createEModelElementAdapter() {
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

} //DiastyleAdapterFactory
