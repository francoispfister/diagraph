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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>DEdge</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.isoe.diagraph.dgi.DEdge#getDSourceNode <em>DSource Node</em>}</li>
 *   <li>{@link org.isoe.diagraph.dgi.DEdge#getDTargetNode <em>DTarget Node</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.isoe.diagraph.dgi.DgiPackage#getDEdge()
 * @model abstract="true"
 * @generated
 */
public interface DEdge extends EObject {
	/**
	 * Returns the value of the '<em><b>DSource Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>DSource Node</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>DSource Node</em>' reference.
	 * @see #setDSourceNode(DNode)
	 * @see org.isoe.diagraph.dgi.DgiPackage#getDEdge_DSourceNode()
	 * @model
	 * @generated
	 */
	DNode getDSourceNode();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.dgi.DEdge#getDSourceNode <em>DSource Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>DSource Node</em>' reference.
	 * @see #getDSourceNode()
	 * @generated
	 */
	void setDSourceNode(DNode value);

	/**
	 * Returns the value of the '<em><b>DTarget Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>DTarget Node</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>DTarget Node</em>' reference.
	 * @see #setDTargetNode(DNode)
	 * @see org.isoe.diagraph.dgi.DgiPackage#getDEdge_DTargetNode()
	 * @model
	 * @generated
	 */
	DNode getDTargetNode();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.dgi.DEdge#getDTargetNode <em>DTarget Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>DTarget Node</em>' reference.
	 * @see #getDTargetNode()
	 * @generated
	 */
	void setDTargetNode(DNode value);

} // DEdge
