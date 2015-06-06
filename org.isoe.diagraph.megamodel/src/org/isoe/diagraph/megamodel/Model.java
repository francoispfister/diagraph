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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.isoe.diagraph.megamodel.Model#getExcerpt <em>Excerpt</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.Model#getNotationDiagrams <em>Notation Diagrams</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.Model#getDsml <em>Dsml</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.isoe.diagraph.megamodel.MegamodelPackage#getModel()
 * @model annotation="diagraph node='null' label\075name='null' label\075excerpt='null' kref\075notationDiagrams='null'"
 * @generated
 */
public interface Model extends MegamodelElement {
   /**
	 * Returns the value of the '<em><b>Excerpt</b></em>' attribute.
	 * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Excerpt</em>' attribute isn't clear,
    * there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
	 * @return the value of the '<em>Excerpt</em>' attribute.
	 * @see #setExcerpt(String)
	 * @see org.isoe.diagraph.megamodel.MegamodelPackage#getModel_Excerpt()
	 * @model
	 * @generated
	 */
   String getExcerpt();

   /**
	 * Sets the value of the '{@link org.isoe.diagraph.megamodel.Model#getExcerpt <em>Excerpt</em>}' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Excerpt</em>' attribute.
	 * @see #getExcerpt()
	 * @generated
	 */
   void setExcerpt(String value);

			/**
	 * Returns the value of the '<em><b>Notation Diagrams</b></em>' containment reference list.
	 * The list contents are of type {@link org.isoe.diagraph.megamodel.NotationDiagram}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Notation Diagrams</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Notation Diagrams</em>' containment reference list.
	 * @see org.isoe.diagraph.megamodel.MegamodelPackage#getModel_NotationDiagrams()
	 * @model containment="true"
	 * @generated
	 */
	EList<NotationDiagram> getNotationDiagrams();

			/**
	 * Returns the value of the '<em><b>Dsml</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.isoe.diagraph.megamodel.Dsml#getModels <em>Models</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dsml</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dsml</em>' container reference.
	 * @see #setDsml(Dsml)
	 * @see org.isoe.diagraph.megamodel.MegamodelPackage#getModel_Dsml()
	 * @see org.isoe.diagraph.megamodel.Dsml#getModels
	 * @model opposite="models" required="true" transient="false"
	 * @generated
	 */
	Dsml getDsml();

			/**
	 * Sets the value of the '{@link org.isoe.diagraph.megamodel.Model#getDsml <em>Dsml</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dsml</em>' container reference.
	 * @see #getDsml()
	 * @generated
	 */
	void setDsml(Dsml value);

} // Model
