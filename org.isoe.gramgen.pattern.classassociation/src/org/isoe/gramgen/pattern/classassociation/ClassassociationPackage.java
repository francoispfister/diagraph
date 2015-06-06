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
package org.isoe.gramgen.pattern.classassociation;

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
 * @see org.isoe.gramgen.pattern.classassociation.ClassassociationFactory
 * @model kind="package"
 * @generated
 */
public interface ClassassociationPackage extends EPackage {
	//org.isoe.gramgen.pattern.classassociation.ClassassociationPackage
	
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "classassociation";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://org.isoe.gramgen.pattern.classassociation.v0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "classassociation";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ClassassociationPackage eINSTANCE = org.isoe.gramgen.pattern.classassociation.impl.ClassassociationPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.isoe.gramgen.pattern.classassociation.impl.SourceImpl <em>Source</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.gramgen.pattern.classassociation.impl.SourceImpl
	 * @see org.isoe.gramgen.pattern.classassociation.impl.ClassassociationPackageImpl#getSource()
	 * @generated
	 */
	int SOURCE = 0;

	/**
	 * The feature id for the '<em><b>Sl</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE__SL = 0;

	/**
	 * The number of structural features of the '<em>Source</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.isoe.gramgen.pattern.classassociation.impl.LabelImpl <em>Label</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.gramgen.pattern.classassociation.impl.LabelImpl
	 * @see org.isoe.gramgen.pattern.classassociation.impl.ClassassociationPackageImpl#getLabel()
	 * @generated
	 */
	int LABEL = 1;

	/**
	 * The feature id for the '<em><b>Lt</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL__LT = 0;

	/**
	 * The number of structural features of the '<em>Label</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.isoe.gramgen.pattern.classassociation.impl.TargetImpl <em>Target</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.gramgen.pattern.classassociation.impl.TargetImpl
	 * @see org.isoe.gramgen.pattern.classassociation.impl.ClassassociationPackageImpl#getTarget()
	 * @generated
	 */
	int TARGET = 2;

	/**
	 * The number of structural features of the '<em>Target</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET_FEATURE_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.isoe.gramgen.pattern.classassociation.Source <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Source</em>'.
	 * @see org.isoe.gramgen.pattern.classassociation.Source
	 * @generated
	 */
	EClass getSource();

	/**
	 * Returns the meta object for the containment reference list '{@link org.isoe.gramgen.pattern.classassociation.Source#getSl <em>Sl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sl</em>'.
	 * @see org.isoe.gramgen.pattern.classassociation.Source#getSl()
	 * @see #getSource()
	 * @generated
	 */
	EReference getSource_Sl();

	/**
	 * Returns the meta object for class '{@link org.isoe.gramgen.pattern.classassociation.Label <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Label</em>'.
	 * @see org.isoe.gramgen.pattern.classassociation.Label
	 * @generated
	 */
	EClass getLabel();

	/**
	 * Returns the meta object for the reference '{@link org.isoe.gramgen.pattern.classassociation.Label#getLt <em>Lt</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Lt</em>'.
	 * @see org.isoe.gramgen.pattern.classassociation.Label#getLt()
	 * @see #getLabel()
	 * @generated
	 */
	EReference getLabel_Lt();

	/**
	 * Returns the meta object for class '{@link org.isoe.gramgen.pattern.classassociation.Target <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Target</em>'.
	 * @see org.isoe.gramgen.pattern.classassociation.Target
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
	ClassassociationFactory getClassassociationFactory();

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
		 * The meta object literal for the '{@link org.isoe.gramgen.pattern.classassociation.impl.SourceImpl <em>Source</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.gramgen.pattern.classassociation.impl.SourceImpl
		 * @see org.isoe.gramgen.pattern.classassociation.impl.ClassassociationPackageImpl#getSource()
		 * @generated
		 */
		EClass SOURCE = eINSTANCE.getSource();

		/**
		 * The meta object literal for the '<em><b>Sl</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOURCE__SL = eINSTANCE.getSource_Sl();

		/**
		 * The meta object literal for the '{@link org.isoe.gramgen.pattern.classassociation.impl.LabelImpl <em>Label</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.gramgen.pattern.classassociation.impl.LabelImpl
		 * @see org.isoe.gramgen.pattern.classassociation.impl.ClassassociationPackageImpl#getLabel()
		 * @generated
		 */
		EClass LABEL = eINSTANCE.getLabel();

		/**
		 * The meta object literal for the '<em><b>Lt</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LABEL__LT = eINSTANCE.getLabel_Lt();

		/**
		 * The meta object literal for the '{@link org.isoe.gramgen.pattern.classassociation.impl.TargetImpl <em>Target</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.gramgen.pattern.classassociation.impl.TargetImpl
		 * @see org.isoe.gramgen.pattern.classassociation.impl.ClassassociationPackageImpl#getTarget()
		 * @generated
		 */
		EClass TARGET = eINSTANCE.getTarget();

	}

} //ClassassociationPackage
