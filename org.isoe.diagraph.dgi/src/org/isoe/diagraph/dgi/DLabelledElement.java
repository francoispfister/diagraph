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

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>DLabelled Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.isoe.diagraph.dgi.DLabelledElement#getDLabels <em>DLabels</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.isoe.diagraph.dgi.DgiPackage#getDLabelledElement()
 * @model abstract="true"
 * @generated
 */
public interface DLabelledElement extends DNamedElement {
	/**
	 * Returns the value of the '<em><b>DLabels</b></em>' containment reference list.
	 * The list contents are of type {@link org.isoe.diagraph.dgi.DLabel}.
	 * It is bidirectional and its opposite is '{@link org.isoe.diagraph.dgi.DLabel#getDLabelledElement <em>DLabelled Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>DLabels</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>DLabels</em>' containment reference list.
	 * @see org.isoe.diagraph.dgi.DgiPackage#getDLabelledElement_DLabels()
	 * @see org.isoe.diagraph.dgi.DLabel#getDLabelledElement
	 * @model opposite="dLabelledElement" containment="true"
	 * @generated
	 */
	EList<DLabel> getDLabels();

} // DLabelledElement
