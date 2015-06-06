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
package org.isoe.gramgen.pattern.compartiment;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.isoe.gramgen.pattern.compartiment.Container#getContient <em>Contient</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.isoe.gramgen.pattern.compartiment.CompartimentPackage#getContainer()
 * @model
 * @generated
 */
public interface Container extends EObject {
	/**
	 * Returns the value of the '<em><b>Contient</b></em>' containment reference list.
	 * The list contents are of type {@link org.isoe.gramgen.pattern.compartiment.Contenu}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Contient</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contient</em>' containment reference list.
	 * @see org.isoe.gramgen.pattern.compartiment.CompartimentPackage#getContainer_Contient()
	 * @model type="org.isoe.gramgen.pattern.compartiment.Contenu" containment="true"
	 * @generated
	 */
	EList getContient();

} // Container
