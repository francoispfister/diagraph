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
 * A representation of the model object '<em><b>Navigation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.isoe.diagraph.megamodel.Navigation#getFrom <em>From</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.Navigation#getTo <em>To</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.Navigation#getId <em>Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.isoe.diagraph.megamodel.MegamodelPackage#getNavigation()
 * @model annotation="diagraph link='null' lsrc\075from='null' ltrg\075to='null'"
 * @generated
 */
public interface Navigation extends EObject {
   /**
	 * Returns the value of the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>From</em>' reference isn't clear,
    * there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
	 * @return the value of the '<em>From</em>' reference.
	 * @see #setFrom(Notation)
	 * @see org.isoe.diagraph.megamodel.MegamodelPackage#getNavigation_From()
	 * @model
	 * @generated
	 */
   Notation getFrom();

   /**
	 * Sets the value of the '{@link org.isoe.diagraph.megamodel.Navigation#getFrom <em>From</em>}' reference.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @param value the new value of the '<em>From</em>' reference.
	 * @see #getFrom()
	 * @generated
	 */
   void setFrom(Notation value);

   /**
	 * Returns the value of the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>To</em>' reference isn't clear,
    * there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
	 * @return the value of the '<em>To</em>' reference.
	 * @see #setTo(Notation)
	 * @see org.isoe.diagraph.megamodel.MegamodelPackage#getNavigation_To()
	 * @model
	 * @generated
	 */
   Notation getTo();

   /**
	 * Sets the value of the '{@link org.isoe.diagraph.megamodel.Navigation#getTo <em>To</em>}' reference.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @param value the new value of the '<em>To</em>' reference.
	 * @see #getTo()
	 * @generated
	 */
   void setTo(Notation value);

   /**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Id</em>' attribute isn't clear,
    * there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see org.isoe.diagraph.megamodel.MegamodelPackage#getNavigation_Id()
	 * @model
	 * @generated
	 */
   String getId();

   /**
	 * Sets the value of the '{@link org.isoe.diagraph.megamodel.Navigation#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
   void setId(String value);

} // Navigation
