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
package org.isoe.diagraph.dgi;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>DContainment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.isoe.diagraph.dgi.DContainment#isCompartment <em>Compartment</em>}</li>
 *   <li>{@link org.isoe.diagraph.dgi.DContainment#isPort <em>Port</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.isoe.diagraph.dgi.DgiPackage#getDContainment()
 * @model
 * @generated
 */
public interface DContainment extends DLabelledElement, DEdge {
	/**
	 * Returns the value of the '<em><b>Compartment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Compartment</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Compartment</em>' attribute.
	 * @see #setCompartment(boolean)
	 * @see org.isoe.diagraph.dgi.DgiPackage#getDContainment_Compartment()
	 * @model
	 * @generated
	 */
	boolean isCompartment();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.dgi.DContainment#isCompartment <em>Compartment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Compartment</em>' attribute.
	 * @see #isCompartment()
	 * @generated
	 */
	void setCompartment(boolean value);

	/**
	 * Returns the value of the '<em><b>Port</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Port</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Port</em>' attribute.
	 * @see #setPort(boolean)
	 * @see org.isoe.diagraph.dgi.DgiPackage#getDContainment_Port()
	 * @model
	 * @generated
	 */
	boolean isPort();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.dgi.DContainment#isPort <em>Port</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Port</em>' attribute.
	 * @see #isPort()
	 * @generated
	 */
	void setPort(boolean value);

} // DContainment
