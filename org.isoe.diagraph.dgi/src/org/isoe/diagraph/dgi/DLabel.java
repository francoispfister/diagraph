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

import org.eclipse.emf.ecore.EModelElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>DLabel</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.isoe.diagraph.dgi.DLabel#getDLabelledElement <em>DLabelled Element</em>}</li>
 *   <li>{@link org.isoe.diagraph.dgi.DLabel#isFromSuperElement <em>From Super Element</em>}</li>
 *   <li>{@link org.isoe.diagraph.dgi.DLabel#isInferred <em>Inferred</em>}</li>
 *   <li>{@link org.isoe.diagraph.dgi.DLabel#getEAttributeModelElement <em>EAttribute Model Element</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.isoe.diagraph.dgi.DgiPackage#getDLabel()
 * @model
 * @generated
 */
public interface DLabel extends DNamedElement {
	/**
	 * Returns the value of the '<em><b>DLabelled Element</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.isoe.diagraph.dgi.DLabelledElement#getDLabels <em>DLabels</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>DLabelled Element</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>DLabelled Element</em>' container reference.
	 * @see #setDLabelledElement(DLabelledElement)
	 * @see org.isoe.diagraph.dgi.DgiPackage#getDLabel_DLabelledElement()
	 * @see org.isoe.diagraph.dgi.DLabelledElement#getDLabels
	 * @model opposite="dLabels" transient="false"
	 * @generated
	 */
	DLabelledElement getDLabelledElement();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.dgi.DLabel#getDLabelledElement <em>DLabelled Element</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>DLabelled Element</em>' container reference.
	 * @see #getDLabelledElement()
	 * @generated
	 */
	void setDLabelledElement(DLabelledElement value);

	/**
	 * Returns the value of the '<em><b>From Super Element</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>From Super Element</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>From Super Element</em>' attribute.
	 * @see #setFromSuperElement(boolean)
	 * @see org.isoe.diagraph.dgi.DgiPackage#getDLabel_FromSuperElement()
	 * @model
	 * @generated
	 */
	boolean isFromSuperElement();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.dgi.DLabel#isFromSuperElement <em>From Super Element</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>From Super Element</em>' attribute.
	 * @see #isFromSuperElement()
	 * @generated
	 */
	void setFromSuperElement(boolean value);

	/**
	 * Returns the value of the '<em><b>Inferred</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Inferred</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Inferred</em>' attribute.
	 * @see #setInferred(boolean)
	 * @see org.isoe.diagraph.dgi.DgiPackage#getDLabel_Inferred()
	 * @model
	 * @generated
	 */
	boolean isInferred();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.dgi.DLabel#isInferred <em>Inferred</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Inferred</em>' attribute.
	 * @see #isInferred()
	 * @generated
	 */
	void setInferred(boolean value);

	/**
	 * Returns the value of the '<em><b>EAttribute Model Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>EAttribute Model Element</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>EAttribute Model Element</em>' reference.
	 * @see #setEAttributeModelElement(EModelElement)
	 * @see org.isoe.diagraph.dgi.DgiPackage#getDLabel_EAttributeModelElement()
	 * @model
	 * @generated
	 */
	EModelElement getEAttributeModelElement();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.dgi.DLabel#getEAttributeModelElement <em>EAttribute Model Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>EAttribute Model Element</em>' reference.
	 * @see #getEAttributeModelElement()
	 * @generated
	 */
	void setEAttributeModelElement(EModelElement value);

} // DLabel
