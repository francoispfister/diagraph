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
 * A representation of the model object '<em><b>DEdge Style</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.isoe.diagraph.diastyle.DEdgeStyle#getArrowDirection <em>Arrow Direction</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.DEdgeStyle#getArrowSize <em>Arrow Size</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.DEdgeStyle#getShape <em>Shape</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.isoe.diagraph.diastyle.DiastylePackage#getDEdgeStyle()
 * @model
 * @generated
 */
public interface DEdgeStyle extends DNodeEdgeStyle {
	/**
	 * Returns the value of the '<em><b>Arrow Direction</b></em>' attribute.
	 * The literals are from the enumeration {@link org.isoe.diagraph.diastyle.DDirection}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Arrow Direction</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Arrow Direction</em>' attribute.
	 * @see org.isoe.diagraph.diastyle.DDirection
	 * @see #setArrowDirection(DDirection)
	 * @see org.isoe.diagraph.diastyle.DiastylePackage#getDEdgeStyle_ArrowDirection()
	 * @model
	 * @generated
	 */
	DDirection getArrowDirection();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diastyle.DEdgeStyle#getArrowDirection <em>Arrow Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Arrow Direction</em>' attribute.
	 * @see org.isoe.diagraph.diastyle.DDirection
	 * @see #getArrowDirection()
	 * @generated
	 */
	void setArrowDirection(DDirection value);

	/**
	 * Returns the value of the '<em><b>Arrow Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Arrow Size</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Arrow Size</em>' attribute.
	 * @see #setArrowSize(int)
	 * @see org.isoe.diagraph.diastyle.DiastylePackage#getDEdgeStyle_ArrowSize()
	 * @model
	 * @generated
	 */
	int getArrowSize();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diastyle.DEdgeStyle#getArrowSize <em>Arrow Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Arrow Size</em>' attribute.
	 * @see #getArrowSize()
	 * @generated
	 */
	void setArrowSize(int value);

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
	 * @see org.isoe.diagraph.diastyle.DiastylePackage#getDEdgeStyle_Shape()
	 * @model
	 * @generated
	 */
	DShape getShape();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diastyle.DEdgeStyle#getShape <em>Shape</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Shape</em>' attribute.
	 * @see org.isoe.diagraph.diastyle.DShape
	 * @see #getShape()
	 * @generated
	 */
	void setShape(DShape value);

} // DEdgeStyle
