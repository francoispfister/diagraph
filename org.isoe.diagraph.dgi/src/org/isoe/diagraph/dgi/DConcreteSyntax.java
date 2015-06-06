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
 * A representation of the model object '<em><b>DConcrete Syntax</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.isoe.diagraph.dgi.DConcreteSyntax#getDNodes <em>DNodes</em>}</li>
 *   <li>{@link org.isoe.diagraph.dgi.DConcreteSyntax#getDElements <em>DElements</em>}</li>
 *   <li>{@link org.isoe.diagraph.dgi.DConcreteSyntax#getDEdges <em>DEdges</em>}</li>
 *   <li>{@link org.isoe.diagraph.dgi.DConcreteSyntax#getRootPointOfView <em>Root Point Of View</em>}</li>
 *   <li>{@link org.isoe.diagraph.dgi.DConcreteSyntax#getPointsOfView <em>Points Of View</em>}</li>
 *   <li>{@link org.isoe.diagraph.dgi.DConcreteSyntax#getCurrentPointOfView <em>Current Point Of View</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.isoe.diagraph.dgi.DgiPackage#getDConcreteSyntax()
 * @model
 * @generated
 */
public interface DConcreteSyntax extends EObject {
	/**
	 * Returns the value of the '<em><b>DNodes</b></em>' containment reference list.
	 * The list contents are of type {@link org.isoe.diagraph.dgi.DNode}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>DNodes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>DNodes</em>' containment reference list.
	 * @see org.isoe.diagraph.dgi.DgiPackage#getDConcreteSyntax_DNodes()
	 * @model containment="true"
	 * @generated
	 */
	EList<DNode> getDNodes();

	/**
	 * Returns the value of the '<em><b>DElements</b></em>' containment reference list.
	 * The list contents are of type {@link org.isoe.diagraph.dgi.DGenericElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>DElements</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>DElements</em>' containment reference list.
	 * @see org.isoe.diagraph.dgi.DgiPackage#getDConcreteSyntax_DElements()
	 * @model containment="true"
	 * @generated
	 */
	EList<DGenericElement> getDElements();

	/**
	 * Returns the value of the '<em><b>DEdges</b></em>' containment reference list.
	 * The list contents are of type {@link org.isoe.diagraph.dgi.DEdge}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>DEdges</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>DEdges</em>' containment reference list.
	 * @see org.isoe.diagraph.dgi.DgiPackage#getDConcreteSyntax_DEdges()
	 * @model containment="true"
	 * @generated
	 */
	EList<DEdge> getDEdges();

	/**
	 * Returns the value of the '<em><b>Root Point Of View</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Root Point Of View</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Root Point Of View</em>' containment reference.
	 * @see #setRootPointOfView(DPointOfView)
	 * @see org.isoe.diagraph.dgi.DgiPackage#getDConcreteSyntax_RootPointOfView()
	 * @model containment="true"
	 * @generated
	 */
	DPointOfView getRootPointOfView();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.dgi.DConcreteSyntax#getRootPointOfView <em>Root Point Of View</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Root Point Of View</em>' containment reference.
	 * @see #getRootPointOfView()
	 * @generated
	 */
	void setRootPointOfView(DPointOfView value);

	/**
	 * Returns the value of the '<em><b>Points Of View</b></em>' reference list.
	 * The list contents are of type {@link org.isoe.diagraph.dgi.DPointOfView}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Points Of View</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Points Of View</em>' reference list.
	 * @see org.isoe.diagraph.dgi.DgiPackage#getDConcreteSyntax_PointsOfView()
	 * @model transient="true" derived="true"
	 * @generated
	 */
	EList<DPointOfView> getPointsOfView();

	/**
	 * Returns the value of the '<em><b>Current Point Of View</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Current Point Of View</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Current Point Of View</em>' reference.
	 * @see #setCurrentPointOfView(DPointOfView)
	 * @see org.isoe.diagraph.dgi.DgiPackage#getDConcreteSyntax_CurrentPointOfView()
	 * @model transient="true" derived="true"
	 * @generated
	 */
	DPointOfView getCurrentPointOfView();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.dgi.DConcreteSyntax#getCurrentPointOfView <em>Current Point Of View</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Current Point Of View</em>' reference.
	 * @see #getCurrentPointOfView()
	 * @generated
	 */
	void setCurrentPointOfView(DPointOfView value);

} // DConcreteSyntax
