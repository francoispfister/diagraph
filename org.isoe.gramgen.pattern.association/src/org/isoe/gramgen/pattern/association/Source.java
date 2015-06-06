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
package org.isoe.gramgen.pattern.association;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Source</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.isoe.gramgen.pattern.association.Source#getRef <em>Ref</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.isoe.gramgen.pattern.association.AssociationPackage#getSource()
 * @model
 * @generated
 */
public interface Source extends EObject {
	/**
	 * Returns the value of the '<em><b>Ref</b></em>' reference list.
	 * The list contents are of type {@link org.isoe.gramgen.pattern.association.Target}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ref</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ref</em>' reference list.
	 * @see org.isoe.gramgen.pattern.association.AssociationPackage#getSource_Ref()
	 * @model type="org.isoe.gramgen.pattern.association.Target"
	 * @generated
	 */
	EList getRef();

} // Source
