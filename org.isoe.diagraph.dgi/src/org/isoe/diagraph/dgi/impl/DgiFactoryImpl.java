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
package org.isoe.diagraph.dgi.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.isoe.diagraph.dgi.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DgiFactoryImpl extends EFactoryImpl implements DgiFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DgiFactory init() {
		try {
			DgiFactory theDgiFactory = (DgiFactory)EPackage.Registry.INSTANCE.getEFactory("http://dgi.isoe.org"); 
			if (theDgiFactory != null) {
				return theDgiFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new DgiFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DgiFactoryImpl() {
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
			case DgiPackage.DNODE: return createDNode();
			case DgiPackage.DLINK: return createDLink();
			case DgiPackage.DPOOR_REFERENCE: return createDPoorReference();
			case DgiPackage.DCONTAINMENT: return createDContainment();
			case DgiPackage.DLABEL: return createDLabel();
			case DgiPackage.DGENERIC_ELEMENT: return createDGenericElement();
			case DgiPackage.DCONCRETE_SYNTAX: return createDConcreteSyntax();
			case DgiPackage.DPOINT_OF_VIEW: return createDPointOfView();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
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
	public DLink createDLink() {
		DLinkImpl dLink = new DLinkImpl();
		return dLink;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DPoorReference createDPoorReference() {
		DPoorReferenceImpl dPoorReference = new DPoorReferenceImpl();
		return dPoorReference;
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
	public DLabel createDLabel() {
		DLabelImpl dLabel = new DLabelImpl();
		return dLabel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DGenericElement createDGenericElement() {
		DGenericElementImpl dGenericElement = new DGenericElementImpl();
		return dGenericElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DConcreteSyntax createDConcreteSyntax() {
		DConcreteSyntaxImpl dConcreteSyntax = new DConcreteSyntaxImpl();
		return dConcreteSyntax;
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
	public DgiPackage getDgiPackage() {
		return (DgiPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static DgiPackage getPackage() {
		return DgiPackage.eINSTANCE;
	}

} //DgiFactoryImpl
