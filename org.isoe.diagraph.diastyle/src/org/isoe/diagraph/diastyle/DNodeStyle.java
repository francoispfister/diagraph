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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>DNode Style</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.isoe.diagraph.diastyle.DNodeStyle#getSizeX <em>Size X</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.DNodeStyle#getRadius <em>Radius</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.DNodeStyle#getShape <em>Shape</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.DNodeStyle#getSizeY <em>Size Y</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.DNodeStyle#getFigure <em>Figure</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.DNodeStyle#getShapeData <em>Shape Data</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.DNodeStyle#getLayout <em>Layout</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.isoe.diagraph.diastyle.DiastylePackage#getDNodeStyle()
 * @model
 * @generated
 */
public interface DNodeStyle extends DNodeEdgeStyle {
	/**
	 * Returns the value of the '<em><b>Size X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Size X</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Size X</em>' attribute.
	 * @see #setSizeX(int)
	 * @see org.isoe.diagraph.diastyle.DiastylePackage#getDNodeStyle_SizeX()
	 * @model
	 * @generated
	 */
	int getSizeX();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diastyle.DNodeStyle#getSizeX <em>Size X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Size X</em>' attribute.
	 * @see #getSizeX()
	 * @generated
	 */
	void setSizeX(int value);

	/**
	 * Returns the value of the '<em><b>Radius</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Radius</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Radius</em>' attribute.
	 * @see #setRadius(int)
	 * @see org.isoe.diagraph.diastyle.DiastylePackage#getDNodeStyle_Radius()
	 * @model
	 * @generated
	 */
	int getRadius();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diastyle.DNodeStyle#getRadius <em>Radius</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Radius</em>' attribute.
	 * @see #getRadius()
	 * @generated
	 */
	void setRadius(int value);

	/**
	 * Returns the value of the '<em><b>Shape</b></em>' attribute.
	 * The literals are from the enumeration {@link org.isoe.diagraph.diastyle.DShape}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Shape</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Shape</em>' attribute.
	 * @see org.isoe.diagraph.diastyle.DShape
	 * @see #setShape(DShape)
	 * @see org.isoe.diagraph.diastyle.DiastylePackage#getDNodeStyle_Shape()
	 * @model
	 * @generated
	 */
	DShape getShape();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diastyle.DNodeStyle#getShape <em>Shape</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Shape</em>' attribute.
	 * @see org.isoe.diagraph.diastyle.DShape
	 * @see #getShape()
	 * @generated
	 */
	void setShape(DShape value);

	/**
	 * Returns the value of the '<em><b>Size Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Size Y</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Size Y</em>' attribute.
	 * @see #setSizeY(int)
	 * @see org.isoe.diagraph.diastyle.DiastylePackage#getDNodeStyle_SizeY()
	 * @model
	 * @generated
	 */
	int getSizeY();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diastyle.DNodeStyle#getSizeY <em>Size Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Size Y</em>' attribute.
	 * @see #getSizeY()
	 * @generated
	 */
	void setSizeY(int value);

	/**
	 * Returns the value of the '<em><b>Figure</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Figure</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Figure</em>' attribute.
	 * @see #setFigure(String)
	 * @see org.isoe.diagraph.diastyle.DiastylePackage#getDNodeStyle_Figure()
	 * @model
	 * @generated
	 */
	String getFigure();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diastyle.DNodeStyle#getFigure <em>Figure</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Figure</em>' attribute.
	 * @see #getFigure()
	 * @generated
	 */
	void setFigure(String value);

	/**
	 * Returns the value of the '<em><b>Shape Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Shape Data</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Shape Data</em>' attribute.
	 * @see #setShapeData(String)
	 * @see org.isoe.diagraph.diastyle.DiastylePackage#getDNodeStyle_ShapeData()
	 * @model
	 * @generated
	 */
	String getShapeData();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diastyle.DNodeStyle#getShapeData <em>Shape Data</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Shape Data</em>' attribute.
	 * @see #getShapeData()
	 * @generated
	 */
	void setShapeData(String value);

	/**
	 * Returns the value of the '<em><b>Layout</b></em>' attribute.
	 * The literals are from the enumeration {@link org.isoe.diagraph.diastyle.DLayout}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Layout</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Layout</em>' attribute.
	 * @see org.isoe.diagraph.diastyle.DLayout
	 * @see #setLayout(DLayout)
	 * @see org.isoe.diagraph.diastyle.DiastylePackage#getDNodeStyle_Layout()
	 * @model
	 * @generated
	 */
	DLayout getLayout();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diastyle.DNodeStyle#getLayout <em>Layout</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Layout</em>' attribute.
	 * @see org.isoe.diagraph.diastyle.DLayout
	 * @see #getLayout()
	 * @generated
	 */
	void setLayout(DLayout value);

} // DNodeStyle
