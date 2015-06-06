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
 * A representation of the model object '<em><b>Related To</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.isoe.diagraph.megamodel.RelatedTo#getFrom <em>From</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.RelatedTo#getTo <em>To</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.RelatedTo#getKind <em>Kind</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.isoe.diagraph.megamodel.MegamodelPackage#getRelatedTo()
 * @model annotation="diagraph link='null' cont\075Dsml.relations='null' lsrc\075from='null' ltrg\075to='null'"
 * @generated
 */
public interface RelatedTo extends EObject {
   /**
	 * Returns the value of the '<em><b>From</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.isoe.diagraph.megamodel.Dsml#getRelations <em>Relations</em>}'.
	 * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>From</em>' container reference isn't clear,
    * there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
	 * @return the value of the '<em>From</em>' container reference.
	 * @see #setFrom(Dsml)
	 * @see org.isoe.diagraph.megamodel.MegamodelPackage#getRelatedTo_From()
	 * @see org.isoe.diagraph.megamodel.Dsml#getRelations
	 * @model opposite="relations" transient="false"
	 * @generated
	 */
   Dsml getFrom();

   /**
	 * Sets the value of the '{@link org.isoe.diagraph.megamodel.RelatedTo#getFrom <em>From</em>}' container reference.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @param value the new value of the '<em>From</em>' container reference.
	 * @see #getFrom()
	 * @generated
	 */
   void setFrom(Dsml value);

   /**
	 * Returns the value of the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>To</em>' reference isn't clear,
    * there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
	 * @return the value of the '<em>To</em>' reference.
	 * @see #setTo(Dsml)
	 * @see org.isoe.diagraph.megamodel.MegamodelPackage#getRelatedTo_To()
	 * @model
	 * @generated
	 */
   Dsml getTo();

   /**
	 * Sets the value of the '{@link org.isoe.diagraph.megamodel.RelatedTo#getTo <em>To</em>}' reference.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @param value the new value of the '<em>To</em>' reference.
	 * @see #getTo()
	 * @generated
	 */
   void setTo(Dsml value);

   /**
	 * Returns the value of the '<em><b>Kind</b></em>' attribute.
	 * The literals are from the enumeration {@link org.isoe.diagraph.megamodel.Relation}.
	 * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Kind</em>' attribute isn't clear,
    * there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
	 * @return the value of the '<em>Kind</em>' attribute.
	 * @see org.isoe.diagraph.megamodel.Relation
	 * @see #setKind(Relation)
	 * @see org.isoe.diagraph.megamodel.MegamodelPackage#getRelatedTo_Kind()
	 * @model
	 * @generated
	 */
   Relation getKind();

   /**
	 * Sets the value of the '{@link org.isoe.diagraph.megamodel.RelatedTo#getKind <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Kind</em>' attribute.
	 * @see org.isoe.diagraph.megamodel.Relation
	 * @see #getKind()
	 * @generated
	 */
   void setKind(Relation value);

} // RelatedTo
