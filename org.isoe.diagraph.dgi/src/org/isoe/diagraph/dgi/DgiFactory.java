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
package org.isoe.diagraph.dgi;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.isoe.diagraph.dgi.DgiPackage
 * @generated
 */
public interface DgiFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DgiFactory eINSTANCE = org.isoe.diagraph.dgi.impl.DgiFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>DNode</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>DNode</em>'.
	 * @generated
	 */
	DNode createDNode();

	/**
	 * Returns a new object of class '<em>DLink</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>DLink</em>'.
	 * @generated
	 */
	DLink createDLink();

	/**
	 * Returns a new object of class '<em>DPoor Reference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>DPoor Reference</em>'.
	 * @generated
	 */
	DPoorReference createDPoorReference();

	/**
	 * Returns a new object of class '<em>DContainment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>DContainment</em>'.
	 * @generated
	 */
	DContainment createDContainment();

	/**
	 * Returns a new object of class '<em>DLabel</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>DLabel</em>'.
	 * @generated
	 */
	DLabel createDLabel();

	/**
	 * Returns a new object of class '<em>DGeneric Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>DGeneric Element</em>'.
	 * @generated
	 */
	DGenericElement createDGenericElement();

	/**
	 * Returns a new object of class '<em>DConcrete Syntax</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>DConcrete Syntax</em>'.
	 * @generated
	 */
	DConcreteSyntax createDConcreteSyntax();

	/**
	 * Returns a new object of class '<em>DPoint Of View</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>DPoint Of View</em>'.
	 * @generated
	 */
	DPointOfView createDPointOfView();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	DgiPackage getDgiPackage();

} //DgiFactory
