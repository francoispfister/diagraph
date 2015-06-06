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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>DBase Style</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.isoe.diagraph.diastyle.DBaseStyle#getName <em>Name</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.DBaseStyle#getColor <em>Color</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.DBaseStyle#getStyleBridges <em>Style Bridges</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.DBaseStyle#getParent <em>Parent</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.DBaseStyle#getParentName <em>Parent Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.isoe.diagraph.diastyle.DiastylePackage#getDBaseStyle()
 * @model abstract="true"
 * @generated
 */
public interface DBaseStyle extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.isoe.diagraph.diastyle.DiastylePackage#getDBaseStyle_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diastyle.DBaseStyle#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Color</b></em>' attribute.
	 * The literals are from the enumeration {@link org.isoe.diagraph.diastyle.DColor}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Color</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Color</em>' attribute.
	 * @see org.isoe.diagraph.diastyle.DColor
	 * @see #setColor(DColor)
	 * @see org.isoe.diagraph.diastyle.DiastylePackage#getDBaseStyle_Color()
	 * @model
	 * @generated
	 */
	DColor getColor();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diastyle.DBaseStyle#getColor <em>Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Color</em>' attribute.
	 * @see org.isoe.diagraph.diastyle.DColor
	 * @see #getColor()
	 * @generated
	 */
	void setColor(DColor value);

	/**
	 * Returns the value of the '<em><b>Style Bridges</b></em>' containment reference list.
	 * The list contents are of type {@link org.isoe.diagraph.diastyle.DStyleBridge}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Style Bridges</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Style Bridges</em>' containment reference list.
	 * @see org.isoe.diagraph.diastyle.DiastylePackage#getDBaseStyle_StyleBridges()
	 * @model containment="true"
	 * @generated
	 */
	EList<DStyleBridge> getStyleBridges();

	/**
	 * Returns the value of the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent</em>' reference.
	 * @see #setParent(DBaseStyle)
	 * @see org.isoe.diagraph.diastyle.DiastylePackage#getDBaseStyle_Parent()
	 * @model
	 * @generated
	 */
	DBaseStyle getParent();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diastyle.DBaseStyle#getParent <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent</em>' reference.
	 * @see #getParent()
	 * @generated
	 */
	void setParent(DBaseStyle value);

	/**
	 * Returns the value of the '<em><b>Parent Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent Name</em>' attribute.
	 * @see #setParentName(String)
	 * @see org.isoe.diagraph.diastyle.DiastylePackage#getDBaseStyle_ParentName()
	 * @model
	 * @generated
	 */
	String getParentName();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diastyle.DBaseStyle#getParentName <em>Parent Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent Name</em>' attribute.
	 * @see #getParentName()
	 * @generated
	 */
	void setParentName(String value);

} // DBaseStyle
