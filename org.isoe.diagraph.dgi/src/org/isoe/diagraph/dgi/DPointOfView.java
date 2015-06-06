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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>DPoint Of View</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.isoe.diagraph.dgi.DPointOfView#getChildren <em>Children</em>}</li>
 *   <li>{@link org.isoe.diagraph.dgi.DPointOfView#getParent <em>Parent</em>}</li>
 *   <li>{@link org.isoe.diagraph.dgi.DPointOfView#getDRootNode <em>DRoot Node</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.isoe.diagraph.dgi.DgiPackage#getDPointOfView()
 * @model
 * @generated
 */
public interface DPointOfView extends EObject {
	/**
	 * Returns the value of the '<em><b>Children</b></em>' containment reference list.
	 * The list contents are of type {@link org.isoe.diagraph.dgi.DPointOfView}.
	 * It is bidirectional and its opposite is '{@link org.isoe.diagraph.dgi.DPointOfView#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Children</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Children</em>' containment reference list.
	 * @see org.isoe.diagraph.dgi.DgiPackage#getDPointOfView_Children()
	 * @see org.isoe.diagraph.dgi.DPointOfView#getParent
	 * @model opposite="parent" containment="true"
	 * @generated
	 */
	EList<DPointOfView> getChildren();

	/**
	 * Returns the value of the '<em><b>Parent</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.isoe.diagraph.dgi.DPointOfView#getChildren <em>Children</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent</em>' container reference.
	 * @see #setParent(DPointOfView)
	 * @see org.isoe.diagraph.dgi.DgiPackage#getDPointOfView_Parent()
	 * @see org.isoe.diagraph.dgi.DPointOfView#getChildren
	 * @model opposite="children" transient="false"
	 * @generated
	 */
	DPointOfView getParent();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.dgi.DPointOfView#getParent <em>Parent</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent</em>' container reference.
	 * @see #getParent()
	 * @generated
	 */
	void setParent(DPointOfView value);

	/**
	 * Returns the value of the '<em><b>DRoot Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>DRoot Node</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>DRoot Node</em>' reference.
	 * @see #setDRootNode(DNode)
	 * @see org.isoe.diagraph.dgi.DgiPackage#getDPointOfView_DRootNode()
	 * @model required="true"
	 * @generated
	 */
	DNode getDRootNode();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.dgi.DPointOfView#getDRootNode <em>DRoot Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>DRoot Node</em>' reference.
	 * @see #getDRootNode()
	 * @generated
	 */
	void setDRootNode(DNode value);

} // DPointOfView
