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

import org.eclipse.emf.ecore.EObject;

import org.isoe.diagraph.diagraph.DGraphElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>DStyle Bridge</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.isoe.diagraph.diastyle.DStyleBridge#getDGraphElement <em>DGraph Element</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.DStyleBridge#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.isoe.diagraph.diastyle.DiastylePackage#getDStyleBridge()
 * @model
 * @generated
 */
public interface DStyleBridge extends EObject {
	/**
	 * Returns the value of the '<em><b>DGraph Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>DGraph Element</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>DGraph Element</em>' reference.
	 * @see #setDGraphElement(DGraphElement)
	 * @see org.isoe.diagraph.diastyle.DiastylePackage#getDStyleBridge_DGraphElement()
	 * @model
	 * @generated
	 */
	DGraphElement getDGraphElement();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diastyle.DStyleBridge#getDGraphElement <em>DGraph Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>DGraph Element</em>' reference.
	 * @see #getDGraphElement()
	 * @generated
	 */
	void setDGraphElement(DGraphElement value);

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
	 * @see org.isoe.diagraph.diastyle.DiastylePackage#getDStyleBridge_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diastyle.DStyleBridge#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // DStyleBridge
