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

import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>DNode</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.isoe.diagraph.dgi.DNode#getDReferences <em>DReferences</em>}</li>
 *   <li>{@link org.isoe.diagraph.dgi.DNode#getDContainments <em>DContainments</em>}</li>
 *   <li>{@link org.isoe.diagraph.dgi.DNode#getDLinks <em>DLinks</em>}</li>
 *   <li>{@link org.isoe.diagraph.dgi.DNode#isRoot <em>Root</em>}</li>
 *   <li>{@link org.isoe.diagraph.dgi.DNode#isRecursive <em>Recursive</em>}</li>
 *   <li>{@link org.isoe.diagraph.dgi.DNode#getNavigations <em>Navigations</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.isoe.diagraph.dgi.DgiPackage#getDNode()
 * @model
 * @generated
 */
public interface DNode extends DElement {
	/**
	 * Returns the value of the '<em><b>DReferences</b></em>' reference list.
	 * The list contents are of type {@link org.isoe.diagraph.dgi.DPoorReference}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>DReferences</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>DReferences</em>' reference list.
	 * @see org.isoe.diagraph.dgi.DgiPackage#getDNode_DReferences()
	 * @model
	 * @generated
	 */
	EList<DPoorReference> getDReferences();

	/**
	 * Returns the value of the '<em><b>DContainments</b></em>' reference list.
	 * The list contents are of type {@link org.isoe.diagraph.dgi.DContainment}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>DContainments</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>DContainments</em>' reference list.
	 * @see org.isoe.diagraph.dgi.DgiPackage#getDNode_DContainments()
	 * @model
	 * @generated
	 */
	EList<DContainment> getDContainments();

	/**
	 * Returns the value of the '<em><b>DLinks</b></em>' reference list.
	 * The list contents are of type {@link org.isoe.diagraph.dgi.DLink}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>DLinks</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>DLinks</em>' reference list.
	 * @see org.isoe.diagraph.dgi.DgiPackage#getDNode_DLinks()
	 * @model
	 * @generated
	 */
	EList<DLink> getDLinks();

	/**
	 * Returns the value of the '<em><b>Root</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Root</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Root</em>' attribute.
	 * @see #setRoot(boolean)
	 * @see org.isoe.diagraph.dgi.DgiPackage#getDNode_Root()
	 * @model
	 * @generated
	 */
	boolean isRoot();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.dgi.DNode#isRoot <em>Root</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Root</em>' attribute.
	 * @see #isRoot()
	 * @generated
	 */
	void setRoot(boolean value);

	/**
	 * Returns the value of the '<em><b>Recursive</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Recursive</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Recursive</em>' attribute.
	 * @see #setRecursive(boolean)
	 * @see org.isoe.diagraph.dgi.DgiPackage#getDNode_Recursive()
	 * @model
	 * @generated
	 */
	boolean isRecursive();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.dgi.DNode#isRecursive <em>Recursive</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Recursive</em>' attribute.
	 * @see #isRecursive()
	 * @generated
	 */
	void setRecursive(boolean value);

	/**
	 * Returns the value of the '<em><b>Navigations</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Navigations</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Navigations</em>' attribute list.
	 * @see org.isoe.diagraph.dgi.DgiPackage#getDNode_Navigations()
	 * @model
	 * @generated
	 */
	EList<String> getNavigations();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void resolveDiagramRecursion();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void addRootChild(DNode node);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void resolveTargetRef();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void logContainments();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	String logReferences();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	boolean checkReferences();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void resolveReferenceTargetNodes();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void resolveContainmentSourceAndTargetNodes();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	DPoorReference findReference(String name);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	DContainment findContainmentByTargetName(String name);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	DContainment findContainmentbyName(String name);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model diagramElementsMany="true"
	 * @generated
	 */
	EReference inferContainment(EList<DElement> diagramElements);

	//void addNavigation(String n);

} // DNode
