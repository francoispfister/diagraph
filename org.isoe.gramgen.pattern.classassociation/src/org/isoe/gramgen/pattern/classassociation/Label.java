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
package org.isoe.gramgen.pattern.classassociation;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Label</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.isoe.gramgen.pattern.classassociation.Label#getLt <em>Lt</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.isoe.gramgen.pattern.classassociation.ClassassociationPackage#getLabel()
 * @model
 * @generated
 */
public interface Label extends EObject {
	/**
	 * Returns the value of the '<em><b>Lt</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lt</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lt</em>' reference.
	 * @see #setLt(Target)
	 * @see org.isoe.gramgen.pattern.classassociation.ClassassociationPackage#getLabel_Lt()
	 * @model
	 * @generated
	 */
	Target getLt();

	/**
	 * Sets the value of the '{@link org.isoe.gramgen.pattern.classassociation.Label#getLt <em>Lt</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lt</em>' reference.
	 * @see #getLt()
	 * @generated
	 */
	void setLt(Target value);

} // Label
