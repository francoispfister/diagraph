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
package org.isoe.diagraph.megamodel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Megamodel</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.isoe.diagraph.megamodel.Megamodel#getDsmls <em>Dsmls</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.Megamodel#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.isoe.diagraph.megamodel.MegamodelPackage#getMegamodel()
 * @model annotation="diagraph node='null' pov='null'"
 * @generated
 */
public interface Megamodel extends EObject {
   /**
	 * Returns the value of the '<em><b>Dsmls</b></em>' containment reference list.
	 * The list contents are of type {@link org.isoe.diagraph.megamodel.Dsml}.
	 * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Dsmls</em>' containment reference list isn't clear,
    * there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
	 * @return the value of the '<em>Dsmls</em>' containment reference list.
	 * @see org.isoe.diagraph.megamodel.MegamodelPackage#getMegamodel_Dsmls()
	 * @model containment="true"
	 * @generated
	 */
   EList<Dsml> getDsmls();

   /**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Name</em>' attribute isn't clear,
    * there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.isoe.diagraph.megamodel.MegamodelPackage#getMegamodel_Name()
	 * @model
	 * @generated
	 */
   String getName();

   /**
	 * Sets the value of the '{@link org.isoe.diagraph.megamodel.Megamodel#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
   void setName(String value);

} // Megamodel
