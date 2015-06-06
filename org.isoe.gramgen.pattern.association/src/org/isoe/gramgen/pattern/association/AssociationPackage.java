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
package org.isoe.gramgen.pattern.association;

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
 * @see org.isoe.gramgen.pattern.association.AssociationFactory
 * @model kind="package"
 * @generated
 */
public interface AssociationPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "association";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://org.isoe.gramgen.pattern.association.v0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "association";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	AssociationPackage eINSTANCE = org.isoe.gramgen.pattern.association.impl.AssociationPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.isoe.gramgen.pattern.association.impl.SourceImpl <em>Source</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.gramgen.pattern.association.impl.SourceImpl
	 * @see org.isoe.gramgen.pattern.association.impl.AssociationPackageImpl#getSource()
	 * @generated
	 */
	int SOURCE = 0;

	/**
	 * The feature id for the '<em><b>Ref</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE__REF = 0;

	/**
	 * The number of structural features of the '<em>Source</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.isoe.gramgen.pattern.association.impl.TargetImpl <em>Target</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.gramgen.pattern.association.impl.TargetImpl
	 * @see org.isoe.gramgen.pattern.association.impl.AssociationPackageImpl#getTarget()
	 * @generated
	 */
	int TARGET = 1;

	/**
	 * The number of structural features of the '<em>Target</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET_FEATURE_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.isoe.gramgen.pattern.association.Source <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Source</em>'.
	 * @see org.isoe.gramgen.pattern.association.Source
	 * @generated
	 */
	EClass getSource();

	/**
	 * Returns the meta object for the reference list '{@link org.isoe.gramgen.pattern.association.Source#getRef <em>Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Ref</em>'.
	 * @see org.isoe.gramgen.pattern.association.Source#getRef()
	 * @see #getSource()
	 * @generated
	 */
	EReference getSource_Ref();

	/**
	 * Returns the meta object for class '{@link org.isoe.gramgen.pattern.association.Target <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Target</em>'.
	 * @see org.isoe.gramgen.pattern.association.Target
	 * @generated
	 */
	EClass getTarget();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	AssociationFactory getAssociationFactory();

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
		 * The meta object literal for the '{@link org.isoe.gramgen.pattern.association.impl.SourceImpl <em>Source</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.gramgen.pattern.association.impl.SourceImpl
		 * @see org.isoe.gramgen.pattern.association.impl.AssociationPackageImpl#getSource()
		 * @generated
		 */
		EClass SOURCE = eINSTANCE.getSource();

		/**
		 * The meta object literal for the '<em><b>Ref</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOURCE__REF = eINSTANCE.getSource_Ref();

		/**
		 * The meta object literal for the '{@link org.isoe.gramgen.pattern.association.impl.TargetImpl <em>Target</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.gramgen.pattern.association.impl.TargetImpl
		 * @see org.isoe.gramgen.pattern.association.impl.AssociationPackageImpl#getTarget()
		 * @generated
		 */
		EClass TARGET = eINSTANCE.getTarget();

	}

} //AssociationPackage
