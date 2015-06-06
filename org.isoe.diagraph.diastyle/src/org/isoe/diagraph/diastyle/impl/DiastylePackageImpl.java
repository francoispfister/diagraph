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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.isoe.diagraph.diagraph.DiagraphPackage;

import org.isoe.diagraph.diastyle.DAlignment;
import org.isoe.diagraph.diastyle.DBaseStyle;
import org.isoe.diagraph.diastyle.DColor;
import org.isoe.diagraph.diastyle.DDirection;
import org.isoe.diagraph.diastyle.DEdgeStyle;
import org.isoe.diagraph.diastyle.DFontName;
import org.isoe.diagraph.diastyle.DFontStyle;
import org.isoe.diagraph.diastyle.DLayout;
import org.isoe.diagraph.diastyle.DLine;
import org.isoe.diagraph.diastyle.DNestingEdgeStyle;
import org.isoe.diagraph.diastyle.DNodeEdgeStyle;
import org.isoe.diagraph.diastyle.DNodeStyle;
import org.isoe.diagraph.diastyle.DShape;
import org.isoe.diagraph.diastyle.DStyle;
import org.isoe.diagraph.diastyle.DStyleBridge;
import org.isoe.diagraph.diastyle.DiastyleFactory;
import org.isoe.diagraph.diastyle.DiastylePackage;

import org.isoe.diagraph.diastyle.helpers.IStyleHandler;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DiastylePackageImpl extends EPackageImpl implements DiastylePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dNodeEdgeStyleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dStyleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dStyleBridgeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dNodeStyleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dEdgeStyleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dNestingEdgeStyleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dBaseStyleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum dColorEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum dLayoutEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum dLineEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum dDirectionEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum dShapeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum dFontStyleEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum dFontNameEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum dAlignmentEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType dStyleHandlerEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.isoe.diagraph.diastyle.DiastylePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private DiastylePackageImpl() {
		super(eNS_URI, DiastyleFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link DiastylePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static DiastylePackage init() {
		if (isInited) return (DiastylePackage)EPackage.Registry.INSTANCE.getEPackage(DiastylePackage.eNS_URI);

		// Obtain or create and register package
		DiastylePackageImpl theDiastylePackage = (DiastylePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof DiastylePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new DiastylePackageImpl());

		isInited = true;

		// Initialize simple dependencies
		DiagraphPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theDiastylePackage.createPackageContents();

		// Initialize created meta-data
		theDiastylePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theDiastylePackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(DiastylePackage.eNS_URI, theDiastylePackage);
		return theDiastylePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDNodeEdgeStyle() {
		return dNodeEdgeStyleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDNodeEdgeStyle_Line() {
		return (EAttribute)dNodeEdgeStyleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDNodeEdgeStyle_LineWidth() {
		return (EAttribute)dNodeEdgeStyleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDNodeEdgeStyle_FontName() {
		return (EAttribute)dNodeEdgeStyleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDNodeEdgeStyle_FontStyle() {
		return (EAttribute)dNodeEdgeStyleEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDNodeEdgeStyle_FontSize() {
		return (EAttribute)dNodeEdgeStyleEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDNodeEdgeStyle_FontColor() {
		return (EAttribute)dNodeEdgeStyleEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDNodeEdgeStyle_TextAlignment() {
		return (EAttribute)dNodeEdgeStyleEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDNodeEdgeStyle_Icon() {
		return (EAttribute)dNodeEdgeStyleEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDStyle() {
		return dStyleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDStyle_Styles() {
		return (EReference)dStyleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDStyle_StyleHandler() {
		return (EAttribute)dStyleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDStyle_DGraph() {
		return (EReference)dStyleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDStyleBridge() {
		return dStyleBridgeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDStyleBridge_DGraphElement() {
		return (EReference)dStyleBridgeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDStyleBridge_Name() {
		return (EAttribute)dStyleBridgeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDNodeStyle() {
		return dNodeStyleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDNodeStyle_SizeX() {
		return (EAttribute)dNodeStyleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDNodeStyle_Radius() {
		return (EAttribute)dNodeStyleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDNodeStyle_Shape() {
		return (EAttribute)dNodeStyleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDNodeStyle_SizeY() {
		return (EAttribute)dNodeStyleEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDNodeStyle_Figure() {
		return (EAttribute)dNodeStyleEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDNodeStyle_ShapeData() {
		return (EAttribute)dNodeStyleEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDNodeStyle_Layout() {
		return (EAttribute)dNodeStyleEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDEdgeStyle() {
		return dEdgeStyleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDEdgeStyle_ArrowDirection() {
		return (EAttribute)dEdgeStyleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDEdgeStyle_ArrowSize() {
		return (EAttribute)dEdgeStyleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDEdgeStyle_Shape() {
		return (EAttribute)dEdgeStyleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDNestingEdgeStyle() {
		return dNestingEdgeStyleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDBaseStyle() {
		return dBaseStyleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDBaseStyle_Name() {
		return (EAttribute)dBaseStyleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDBaseStyle_Color() {
		return (EAttribute)dBaseStyleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDBaseStyle_StyleBridges() {
		return (EReference)dBaseStyleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDBaseStyle_Parent() {
		return (EReference)dBaseStyleEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDBaseStyle_ParentName() {
		return (EAttribute)dBaseStyleEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getDColor() {
		return dColorEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getDLayout() {
		return dLayoutEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getDLine() {
		return dLineEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getDDirection() {
		return dDirectionEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getDShape() {
		return dShapeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getDFontStyle() {
		return dFontStyleEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getDFontName() {
		return dFontNameEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getDAlignment() {
		return dAlignmentEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getDStyleHandler() {
		return dStyleHandlerEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DiastyleFactory getDiastyleFactory() {
		return (DiastyleFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		dNodeEdgeStyleEClass = createEClass(DNODE_EDGE_STYLE);
		createEAttribute(dNodeEdgeStyleEClass, DNODE_EDGE_STYLE__LINE);
		createEAttribute(dNodeEdgeStyleEClass, DNODE_EDGE_STYLE__LINE_WIDTH);
		createEAttribute(dNodeEdgeStyleEClass, DNODE_EDGE_STYLE__FONT_NAME);
		createEAttribute(dNodeEdgeStyleEClass, DNODE_EDGE_STYLE__FONT_STYLE);
		createEAttribute(dNodeEdgeStyleEClass, DNODE_EDGE_STYLE__FONT_SIZE);
		createEAttribute(dNodeEdgeStyleEClass, DNODE_EDGE_STYLE__FONT_COLOR);
		createEAttribute(dNodeEdgeStyleEClass, DNODE_EDGE_STYLE__TEXT_ALIGNMENT);
		createEAttribute(dNodeEdgeStyleEClass, DNODE_EDGE_STYLE__ICON);

		dStyleEClass = createEClass(DSTYLE);
		createEReference(dStyleEClass, DSTYLE__STYLES);
		createEAttribute(dStyleEClass, DSTYLE__STYLE_HANDLER);
		createEReference(dStyleEClass, DSTYLE__DGRAPH);

		dStyleBridgeEClass = createEClass(DSTYLE_BRIDGE);
		createEReference(dStyleBridgeEClass, DSTYLE_BRIDGE__DGRAPH_ELEMENT);
		createEAttribute(dStyleBridgeEClass, DSTYLE_BRIDGE__NAME);

		dNodeStyleEClass = createEClass(DNODE_STYLE);
		createEAttribute(dNodeStyleEClass, DNODE_STYLE__SIZE_X);
		createEAttribute(dNodeStyleEClass, DNODE_STYLE__RADIUS);
		createEAttribute(dNodeStyleEClass, DNODE_STYLE__SHAPE);
		createEAttribute(dNodeStyleEClass, DNODE_STYLE__SIZE_Y);
		createEAttribute(dNodeStyleEClass, DNODE_STYLE__FIGURE);
		createEAttribute(dNodeStyleEClass, DNODE_STYLE__SHAPE_DATA);
		createEAttribute(dNodeStyleEClass, DNODE_STYLE__LAYOUT);

		dEdgeStyleEClass = createEClass(DEDGE_STYLE);
		createEAttribute(dEdgeStyleEClass, DEDGE_STYLE__ARROW_DIRECTION);
		createEAttribute(dEdgeStyleEClass, DEDGE_STYLE__ARROW_SIZE);
		createEAttribute(dEdgeStyleEClass, DEDGE_STYLE__SHAPE);

		dNestingEdgeStyleEClass = createEClass(DNESTING_EDGE_STYLE);

		dBaseStyleEClass = createEClass(DBASE_STYLE);
		createEAttribute(dBaseStyleEClass, DBASE_STYLE__NAME);
		createEAttribute(dBaseStyleEClass, DBASE_STYLE__COLOR);
		createEReference(dBaseStyleEClass, DBASE_STYLE__STYLE_BRIDGES);
		createEReference(dBaseStyleEClass, DBASE_STYLE__PARENT);
		createEAttribute(dBaseStyleEClass, DBASE_STYLE__PARENT_NAME);

		// Create enums
		dColorEEnum = createEEnum(DCOLOR);
		dLayoutEEnum = createEEnum(DLAYOUT);
		dLineEEnum = createEEnum(DLINE);
		dDirectionEEnum = createEEnum(DDIRECTION);
		dShapeEEnum = createEEnum(DSHAPE);
		dFontStyleEEnum = createEEnum(DFONT_STYLE);
		dFontNameEEnum = createEEnum(DFONT_NAME);
		dAlignmentEEnum = createEEnum(DALIGNMENT);

		// Create data types
		dStyleHandlerEDataType = createEDataType(DSTYLE_HANDLER);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		DiagraphPackage theDiagraphPackage = (DiagraphPackage)EPackage.Registry.INSTANCE.getEPackage(DiagraphPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		dNodeEdgeStyleEClass.getESuperTypes().add(ecorePackage.getEModelElement());
		dNodeEdgeStyleEClass.getESuperTypes().add(this.getDBaseStyle());
		dStyleEClass.getESuperTypes().add(ecorePackage.getEModelElement());
		dNodeStyleEClass.getESuperTypes().add(this.getDNodeEdgeStyle());
		dEdgeStyleEClass.getESuperTypes().add(this.getDNodeEdgeStyle());
		dNestingEdgeStyleEClass.getESuperTypes().add(this.getDBaseStyle());

		// Initialize classes and features; add operations and parameters
		initEClass(dNodeEdgeStyleEClass, DNodeEdgeStyle.class, "DNodeEdgeStyle", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDNodeEdgeStyle_Line(), this.getDLine(), "line", null, 0, 1, DNodeEdgeStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDNodeEdgeStyle_LineWidth(), ecorePackage.getEInt(), "lineWidth", null, 0, 1, DNodeEdgeStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDNodeEdgeStyle_FontName(), this.getDFontName(), "fontName", null, 0, 1, DNodeEdgeStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDNodeEdgeStyle_FontStyle(), this.getDFontStyle(), "fontStyle", null, 0, 1, DNodeEdgeStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDNodeEdgeStyle_FontSize(), ecorePackage.getEInt(), "fontSize", null, 0, 1, DNodeEdgeStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDNodeEdgeStyle_FontColor(), this.getDColor(), "fontColor", null, 0, 1, DNodeEdgeStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDNodeEdgeStyle_TextAlignment(), this.getDAlignment(), "textAlignment", null, 0, 1, DNodeEdgeStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDNodeEdgeStyle_Icon(), ecorePackage.getEString(), "icon", null, 0, 1, DNodeEdgeStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dStyleEClass, DStyle.class, "DStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDStyle_Styles(), this.getDBaseStyle(), null, "styles", null, 0, -1, DStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDStyle_StyleHandler(), this.getDStyleHandler(), "styleHandler", null, 0, 1, DStyle.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDStyle_DGraph(), theDiagraphPackage.getDGraph(), null, "dGraph", null, 0, 1, DStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = addEOperation(dStyleEClass, ecorePackage.getEString(), "getBackgroundColor", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theDiagraphPackage.getDGraphElement(), "element", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(dStyleEClass, ecorePackage.getEString(), "getFigure", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theDiagraphPackage.getDGraphElement(), "element", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(dStyleEClass, ecorePackage.getEString(), "getIcon", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theDiagraphPackage.getDGraphElement(), "element", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(dStyleBridgeEClass, DStyleBridge.class, "DStyleBridge", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDStyleBridge_DGraphElement(), theDiagraphPackage.getDGraphElement(), null, "dGraphElement", null, 0, 1, DStyleBridge.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDStyleBridge_Name(), ecorePackage.getEString(), "name", null, 0, 1, DStyleBridge.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dNodeStyleEClass, DNodeStyle.class, "DNodeStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDNodeStyle_SizeX(), ecorePackage.getEInt(), "sizeX", null, 0, 1, DNodeStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDNodeStyle_Radius(), ecorePackage.getEInt(), "radius", null, 0, 1, DNodeStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDNodeStyle_Shape(), this.getDShape(), "shape", null, 0, 1, DNodeStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDNodeStyle_SizeY(), ecorePackage.getEInt(), "sizeY", null, 0, 1, DNodeStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDNodeStyle_Figure(), ecorePackage.getEString(), "figure", null, 0, 1, DNodeStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDNodeStyle_ShapeData(), ecorePackage.getEString(), "shapeData", null, 0, 1, DNodeStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDNodeStyle_Layout(), this.getDLayout(), "layout", null, 0, 1, DNodeStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dEdgeStyleEClass, DEdgeStyle.class, "DEdgeStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDEdgeStyle_ArrowDirection(), this.getDDirection(), "arrowDirection", null, 0, 1, DEdgeStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDEdgeStyle_ArrowSize(), ecorePackage.getEInt(), "arrowSize", null, 0, 1, DEdgeStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDEdgeStyle_Shape(), this.getDShape(), "shape", null, 0, 1, DEdgeStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dNestingEdgeStyleEClass, DNestingEdgeStyle.class, "DNestingEdgeStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(dBaseStyleEClass, DBaseStyle.class, "DBaseStyle", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDBaseStyle_Name(), ecorePackage.getEString(), "name", null, 0, 1, DBaseStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDBaseStyle_Color(), this.getDColor(), "color", null, 0, 1, DBaseStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDBaseStyle_StyleBridges(), this.getDStyleBridge(), null, "styleBridges", null, 0, -1, DBaseStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDBaseStyle_Parent(), this.getDBaseStyle(), null, "parent", null, 0, 1, DBaseStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDBaseStyle_ParentName(), ecorePackage.getEString(), "parentName", null, 0, 1, DBaseStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(dColorEEnum, DColor.class, "DColor");
		addEEnumLiteral(dColorEEnum, DColor.WHITE);
		addEEnumLiteral(dColorEEnum, DColor.BLACK);
		addEEnumLiteral(dColorEEnum, DColor.LIGHT_GRAY);
		addEEnumLiteral(dColorEEnum, DColor.GRAY);
		addEEnumLiteral(dColorEEnum, DColor.DARK_GRAY);
		addEEnumLiteral(dColorEEnum, DColor.RED);
		addEEnumLiteral(dColorEEnum, DColor.ORANGE);
		addEEnumLiteral(dColorEEnum, DColor.YELLOW);
		addEEnumLiteral(dColorEEnum, DColor.GREEN);
		addEEnumLiteral(dColorEEnum, DColor.LIGHT_GREEN);
		addEEnumLiteral(dColorEEnum, DColor.DARK_GREEN);
		addEEnumLiteral(dColorEEnum, DColor.CYAN);
		addEEnumLiteral(dColorEEnum, DColor.LIGHT_BLUE);
		addEEnumLiteral(dColorEEnum, DColor.BLUE);
		addEEnumLiteral(dColorEEnum, DColor.DARK_BLUE);

		initEEnum(dLayoutEEnum, DLayout.class, "DLayout");
		addEEnumLiteral(dLayoutEEnum, DLayout.NONE);
		addEEnumLiteral(dLayoutEEnum, DLayout.FREE);
		addEEnumLiteral(dLayoutEEnum, DLayout.HORIZONTAL);
		addEEnumLiteral(dLayoutEEnum, DLayout.VERTICAL);

		initEEnum(dLineEEnum, DLine.class, "DLine");
		addEEnumLiteral(dLineEEnum, DLine.SOLID);
		addEEnumLiteral(dLineEEnum, DLine.DASH);
		addEEnumLiteral(dLineEEnum, DLine.DOT);
		addEEnumLiteral(dLineEEnum, DLine.DASHDOT);
		addEEnumLiteral(dLineEEnum, DLine.DASHDOTDOT);
		addEEnumLiteral(dLineEEnum, DLine.CUSTOM);

		initEEnum(dDirectionEEnum, DDirection.class, "DDirection");
		addEEnumLiteral(dDirectionEEnum, DDirection.NONE);
		addEEnumLiteral(dDirectionEEnum, DDirection.LEFT);
		addEEnumLiteral(dDirectionEEnum, DDirection.RIGHT);
		addEEnumLiteral(dDirectionEEnum, DDirection.BIDIRECTIONAL);

		initEEnum(dShapeEEnum, DShape.class, "DShape");
		addEEnumLiteral(dShapeEEnum, DShape.RECTANGLE);
		addEEnumLiteral(dShapeEEnum, DShape.ROUNDED_RECTANGLE);
		addEEnumLiteral(dShapeEEnum, DShape.ELLIPSE);
		addEEnumLiteral(dShapeEEnum, DShape.DOT);
		addEEnumLiteral(dShapeEEnum, DShape.CUSTOM);
		addEEnumLiteral(dShapeEEnum, DShape.ARROW);
		addEEnumLiteral(dShapeEEnum, DShape.TRIANGLE);

		initEEnum(dFontStyleEEnum, DFontStyle.class, "DFontStyle");
		addEEnumLiteral(dFontStyleEEnum, DFontStyle.NORMAL);
		addEEnumLiteral(dFontStyleEEnum, DFontStyle.BOLD);
		addEEnumLiteral(dFontStyleEEnum, DFontStyle.ITALIC);

		initEEnum(dFontNameEEnum, DFontName.class, "DFontName");
		addEEnumLiteral(dFontNameEEnum, DFontName.ARIAL);
		addEEnumLiteral(dFontNameEEnum, DFontName.COURIER);
		addEEnumLiteral(dFontNameEEnum, DFontName.TIMES);

		initEEnum(dAlignmentEEnum, DAlignment.class, "DAlignment");
		addEEnumLiteral(dAlignmentEEnum, DAlignment.BEGINNING);
		addEEnumLiteral(dAlignmentEEnum, DAlignment.CENTER);
		addEEnumLiteral(dAlignmentEEnum, DAlignment.END);
		addEEnumLiteral(dAlignmentEEnum, DAlignment.FILL);

		// Initialize data types
		initEDataType(dStyleHandlerEDataType, IStyleHandler.class, "DStyleHandler", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //DiastylePackageImpl
