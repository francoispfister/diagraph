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

import org.eclipse.emf.ecore.EModelElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>DElement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.isoe.diagraph.dgi.DElement#getEContainmentModelElement <em>EContainment Model Element</em>}</li>
 *   <li>{@link org.isoe.diagraph.dgi.DElement#getEModelElement <em>EModel Element</em>}</li>
 *   <li>{@link org.isoe.diagraph.dgi.DElement#getDLowerElements <em>DLower Elements</em>}</li>
 *   <li>{@link org.isoe.diagraph.dgi.DElement#getDSuperElements <em>DSuper Elements</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.isoe.diagraph.dgi.DgiPackage#getDElement()
 * @model abstract="true"
 * @generated
 */
public interface DElement extends DLabelledElement {
	/**
	 * Returns the value of the '<em><b>EContainment Model Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>EContainment Model Element</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>EContainment Model Element</em>' reference.
	 * @see #setEContainmentModelElement(EModelElement)
	 * @see org.isoe.diagraph.dgi.DgiPackage#getDElement_EContainmentModelElement()
	 * @model
	 * @generated
	 */
	EModelElement getEContainmentModelElement();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.dgi.DElement#getEContainmentModelElement <em>EContainment Model Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>EContainment Model Element</em>' reference.
	 * @see #getEContainmentModelElement()
	 * @generated
	 */
	void setEContainmentModelElement(EModelElement value);

	/**
	 * Returns the value of the '<em><b>EModel Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>EModel Element</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>EModel Element</em>' reference.
	 * @see #setEModelElement(EModelElement)
	 * @see org.isoe.diagraph.dgi.DgiPackage#getDElement_EModelElement()
	 * @model
	 * @generated
	 */
	EModelElement getEModelElement();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.dgi.DElement#getEModelElement <em>EModel Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>EModel Element</em>' reference.
	 * @see #getEModelElement()
	 * @generated
	 */
	void setEModelElement(EModelElement value);

	/**
	 * Returns the value of the '<em><b>DLower Elements</b></em>' reference list.
	 * The list contents are of type {@link org.isoe.diagraph.dgi.DElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>DLower Elements</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>DLower Elements</em>' reference list.
	 * @see org.isoe.diagraph.dgi.DgiPackage#getDElement_DLowerElements()
	 * @model
	 * @generated
	 */
	EList<DElement> getDLowerElements();

	/**
	 * Returns the value of the '<em><b>DSuper Elements</b></em>' reference list.
	 * The list contents are of type {@link org.isoe.diagraph.dgi.DElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>DSuper Elements</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>DSuper Elements</em>' reference list.
	 * @see org.isoe.diagraph.dgi.DgiPackage#getDElement_DSuperElements()
	 * @model
	 * @generated
	 */
	EList<DElement> getDSuperElements();

} // DElement
