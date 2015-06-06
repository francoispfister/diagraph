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
package org.isoe.diagraph.diastyle.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.isoe.diagraph.diastyle.*;

import org.isoe.diagraph.diastyle.helpers.IStyleHandler;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DiastyleFactoryImpl extends EFactoryImpl implements DiastyleFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DiastyleFactory init() {
		try {
			DiastyleFactory theDiastyleFactory = (DiastyleFactory)EPackage.Registry.INSTANCE.getEFactory("http://isoe-2012-diastyle-dsml"); 
			if (theDiastyleFactory != null) {
				return theDiastyleFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new DiastyleFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DiastyleFactoryImpl() {
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
			case DiastylePackage.DSTYLE: return createDStyle();
			case DiastylePackage.DSTYLE_BRIDGE: return createDStyleBridge();
			case DiastylePackage.DNODE_STYLE: return createDNodeStyle();
			case DiastylePackage.DEDGE_STYLE: return createDEdgeStyle();
			case DiastylePackage.DNESTING_EDGE_STYLE: return createDNestingEdgeStyle();
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
			case DiastylePackage.DCOLOR:
				return createDColorFromString(eDataType, initialValue);
			case DiastylePackage.DLAYOUT:
				return createDLayoutFromString(eDataType, initialValue);
			case DiastylePackage.DLINE:
				return createDLineFromString(eDataType, initialValue);
			case DiastylePackage.DDIRECTION:
				return createDDirectionFromString(eDataType, initialValue);
			case DiastylePackage.DSHAPE:
				return createDShapeFromString(eDataType, initialValue);
			case DiastylePackage.DFONT_STYLE:
				return createDFontStyleFromString(eDataType, initialValue);
			case DiastylePackage.DFONT_NAME:
				return createDFontNameFromString(eDataType, initialValue);
			case DiastylePackage.DALIGNMENT:
				return createDAlignmentFromString(eDataType, initialValue);
			case DiastylePackage.DSTYLE_HANDLER:
				return createDStyleHandlerFromString(eDataType, initialValue);
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
			case DiastylePackage.DCOLOR:
				return convertDColorToString(eDataType, instanceValue);
			case DiastylePackage.DLAYOUT:
				return convertDLayoutToString(eDataType, instanceValue);
			case DiastylePackage.DLINE:
				return convertDLineToString(eDataType, instanceValue);
			case DiastylePackage.DDIRECTION:
				return convertDDirectionToString(eDataType, instanceValue);
			case DiastylePackage.DSHAPE:
				return convertDShapeToString(eDataType, instanceValue);
			case DiastylePackage.DFONT_STYLE:
				return convertDFontStyleToString(eDataType, instanceValue);
			case DiastylePackage.DFONT_NAME:
				return convertDFontNameToString(eDataType, instanceValue);
			case DiastylePackage.DALIGNMENT:
				return convertDAlignmentToString(eDataType, instanceValue);
			case DiastylePackage.DSTYLE_HANDLER:
				return convertDStyleHandlerToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DStyle createDStyle() {
		DStyleImpl dStyle = new DStyleImpl();
		return dStyle;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DStyleBridge createDStyleBridge() {
		DStyleBridgeImpl dStyleBridge = new DStyleBridgeImpl();
		return dStyleBridge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DNodeStyle createDNodeStyle() {
		DNodeStyleImpl dNodeStyle = new DNodeStyleImpl();
		return dNodeStyle;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DEdgeStyle createDEdgeStyle() {
		DEdgeStyleImpl dEdgeStyle = new DEdgeStyleImpl();
		return dEdgeStyle;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DNestingEdgeStyle createDNestingEdgeStyle() {
		DNestingEdgeStyleImpl dNestingEdgeStyle = new DNestingEdgeStyleImpl();
		return dNestingEdgeStyle;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DColor createDColorFromString(EDataType eDataType, String initialValue) {
		DColor result = DColor.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDColorToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DLayout createDLayoutFromString(EDataType eDataType, String initialValue) {
		DLayout result = DLayout.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDLayoutToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DLine createDLineFromString(EDataType eDataType, String initialValue) {
		DLine result = DLine.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDLineToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DDirection createDDirectionFromString(EDataType eDataType, String initialValue) {
		DDirection result = DDirection.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDDirectionToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
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
	public DFontStyle createDFontStyleFromString(EDataType eDataType, String initialValue) {
		DFontStyle result = DFontStyle.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDFontStyleToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DFontName createDFontNameFromString(EDataType eDataType, String initialValue) {
		DFontName result = DFontName.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDFontNameToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DAlignment createDAlignmentFromString(EDataType eDataType, String initialValue) {
		DAlignment result = DAlignment.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDAlignmentToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IStyleHandler createDStyleHandlerFromString(EDataType eDataType, String initialValue) {
		return (IStyleHandler)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDStyleHandlerToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DiastylePackage getDiastylePackage() {
		return (DiastylePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static DiastylePackage getPackage() {
		return DiastylePackage.eINSTANCE;
	}

} //DiastyleFactoryImpl
