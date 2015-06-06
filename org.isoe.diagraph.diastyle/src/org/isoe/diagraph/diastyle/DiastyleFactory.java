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

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.isoe.diagraph.diastyle.DiastylePackage
 * @generated
 */
public interface DiastyleFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DiastyleFactory eINSTANCE = org.isoe.diagraph.diastyle.impl.DiastyleFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>DStyle</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>DStyle</em>'.
	 * @generated
	 */
	DStyle createDStyle();

	/**
	 * Returns a new object of class '<em>DStyle Bridge</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>DStyle Bridge</em>'.
	 * @generated
	 */
	DStyleBridge createDStyleBridge();

	/**
	 * Returns a new object of class '<em>DNode Style</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>DNode Style</em>'.
	 * @generated
	 */
	DNodeStyle createDNodeStyle();

	/**
	 * Returns a new object of class '<em>DEdge Style</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>DEdge Style</em>'.
	 * @generated
	 */
	DEdgeStyle createDEdgeStyle();

	/**
	 * Returns a new object of class '<em>DNesting Edge Style</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>DNesting Edge Style</em>'.
	 * @generated
	 */
	DNestingEdgeStyle createDNestingEdgeStyle();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	DiastylePackage getDiastylePackage();

} //DiastyleFactory
