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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.isoe.diagraph.megamodel.MegamodelElement#getName <em>Name</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.MegamodelElement#getURI <em>URI</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.isoe.diagraph.megamodel.MegamodelPackage#getMegamodelElement()
 * @model abstract="true"
 *        annotation="diagraph label\075name='null'"
 * @generated
 */
public interface MegamodelElement extends EObject {
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
	 * @see org.isoe.diagraph.megamodel.MegamodelPackage#getMegamodelElement_Name()
	 * @model
	 * @generated
	 */
   String getName();

   /**
	 * Sets the value of the '{@link org.isoe.diagraph.megamodel.MegamodelElement#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
   void setName(String value);

   /**
	 * Returns the value of the '<em><b>URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>URI</em>' attribute isn't clear,
    * there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
	 * @return the value of the '<em>URI</em>' attribute.
	 * @see #setURI(String)
	 * @see org.isoe.diagraph.megamodel.MegamodelPackage#getMegamodelElement_URI()
	 * @model
	 * @generated
	 */
   String getURI();

   /**
	 * Sets the value of the '{@link org.isoe.diagraph.megamodel.MegamodelElement#getURI <em>URI</em>}' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @param value the new value of the '<em>URI</em>' attribute.
	 * @see #getURI()
	 * @generated
	 */
   void setURI_(String value);

} // MegamodelElement
