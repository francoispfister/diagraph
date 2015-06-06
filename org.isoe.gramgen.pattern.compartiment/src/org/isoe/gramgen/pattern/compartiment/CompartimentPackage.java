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
package org.isoe.gramgen.pattern.compartiment;

import org.eclipse.emf.ecore.EClass;
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
 * @see org.isoe.gramgen.pattern.compartiment.CompartimentFactory
 * @model kind="package"
 * @generated
 */
public interface CompartimentPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "compartiment";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://org.isoe.gramgen.pattern.compartiment.v0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "compartiment";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CompartimentPackage eINSTANCE = org.isoe.gramgen.pattern.compartiment.impl.CompartimentPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.isoe.gramgen.pattern.compartiment.impl.ContainerImpl <em>Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.gramgen.pattern.compartiment.impl.ContainerImpl
	 * @see org.isoe.gramgen.pattern.compartiment.impl.CompartimentPackageImpl#getContainer()
	 * @generated
	 */
	int CONTAINER = 0;

	/**
	 * The feature id for the '<em><b>Contient</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER__CONTIENT = 0;

	/**
	 * The number of structural features of the '<em>Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.isoe.gramgen.pattern.compartiment.impl.ContenuImpl <em>Contenu</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.gramgen.pattern.compartiment.impl.ContenuImpl
	 * @see org.isoe.gramgen.pattern.compartiment.impl.CompartimentPackageImpl#getContenu()
	 * @generated
	 */
	int CONTENU = 1;

	/**
	 * The number of structural features of the '<em>Contenu</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTENU_FEATURE_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.isoe.gramgen.pattern.compartiment.Container <em>Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Container</em>'.
	 * @see org.isoe.gramgen.pattern.compartiment.Container
	 * @generated
	 */
	EClass getContainer();

	/**
	 * Returns the meta object for the containment reference list '{@link org.isoe.gramgen.pattern.compartiment.Container#getContient <em>Contient</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Contient</em>'.
	 * @see org.isoe.gramgen.pattern.compartiment.Container#getContient()
	 * @see #getContainer()
	 * @generated
	 */
	EReference getContainer_Contient();

	/**
	 * Returns the meta object for class '{@link org.isoe.gramgen.pattern.compartiment.Contenu <em>Contenu</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Contenu</em>'.
	 * @see org.isoe.gramgen.pattern.compartiment.Contenu
	 * @generated
	 */
	EClass getContenu();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	CompartimentFactory getCompartimentFactory();

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
		 * The meta object literal for the '{@link org.isoe.gramgen.pattern.compartiment.impl.ContainerImpl <em>Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.gramgen.pattern.compartiment.impl.ContainerImpl
		 * @see org.isoe.gramgen.pattern.compartiment.impl.CompartimentPackageImpl#getContainer()
		 * @generated
		 */
		EClass CONTAINER = eINSTANCE.getContainer();

		/**
		 * The meta object literal for the '<em><b>Contient</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTAINER__CONTIENT = eINSTANCE.getContainer_Contient();

		/**
		 * The meta object literal for the '{@link org.isoe.gramgen.pattern.compartiment.impl.ContenuImpl <em>Contenu</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.gramgen.pattern.compartiment.impl.ContenuImpl
		 * @see org.isoe.gramgen.pattern.compartiment.impl.CompartimentPackageImpl#getContenu()
		 * @generated
		 */
		EClass CONTENU = eINSTANCE.getContenu();

	}

} //CompartimentPackage
