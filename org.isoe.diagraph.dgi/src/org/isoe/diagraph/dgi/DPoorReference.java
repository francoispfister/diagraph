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
 * A representation of the model object '<em><b>DPoor Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.isoe.diagraph.dgi.DPoorReference#getETargetModelElement <em>ETarget Model Element</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.isoe.diagraph.dgi.DgiPackage#getDPoorReference()
 * @model
 * @generated
 */
public interface DPoorReference extends DEdge, DNamedElement {
	/**
	 * Returns the value of the '<em><b>ETarget Model Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>ETarget Model Element</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>ETarget Model Element</em>' reference.
	 * @see #setETargetModelElement(EModelElement)
	 * @see org.isoe.diagraph.dgi.DgiPackage#getDPoorReference_ETargetModelElement()
	 * @model
	 * @generated
	 */
	EModelElement getETargetModelElement();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.dgi.DPoorReference#getETargetModelElement <em>ETarget Model Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>ETarget Model Element</em>' reference.
	 * @see #getETargetModelElement()
	 * @generated
	 */
	void setETargetModelElement(EModelElement value);

} // DPoorReference
