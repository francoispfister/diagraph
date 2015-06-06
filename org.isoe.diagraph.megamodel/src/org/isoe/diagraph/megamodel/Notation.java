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
 * A representation of the model object '<em><b>Notation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.isoe.diagraph.megamodel.Notation#getNavigations <em>Navigations</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.Notation#getNotationBridge <em>Notation Bridge</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.Notation#getDsml <em>Dsml</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.isoe.diagraph.megamodel.MegamodelPackage#getNotation()
 * @model annotation="diagraph node='null'"
 * @generated
 */
public interface Notation extends MegamodelElement {
   /**
	 * Returns the value of the '<em><b>Navigations</b></em>' containment reference list.
	 * The list contents are of type {@link org.isoe.diagraph.megamodel.Navigation}.
	 * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Navigations</em>' containment reference isn't clear,
    * there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
	 * @return the value of the '<em>Navigations</em>' containment reference list.
	 * @see org.isoe.diagraph.megamodel.MegamodelPackage#getNotation_Navigations()
	 * @model containment="true"
	 * @generated
	 */
   EList<Navigation> getNavigations();

   /**
	 * Returns the value of the '<em><b>Notation Bridge</b></em>' reference.
	 * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Notation Bridge</em>' reference isn't clear,
    * there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
	 * @return the value of the '<em>Notation Bridge</em>' reference.
	 * @see #setNotationBridge(EObject)
	 * @see org.isoe.diagraph.megamodel.MegamodelPackage#getNotation_NotationBridge()
	 * @model
	 * @generated
	 */
   EObject getNotationBridge();

   /**
	 * Sets the value of the '{@link org.isoe.diagraph.megamodel.Notation#getNotationBridge <em>Notation Bridge</em>}' reference.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Notation Bridge</em>' reference.
	 * @see #getNotationBridge()
	 * @generated
	 */
   void setNotationBridge(EObject value);

			/**
	 * Returns the value of the '<em><b>Dsml</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.isoe.diagraph.megamodel.Dsml#getNotations <em>Notations</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dsml</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dsml</em>' container reference.
	 * @see #setDsml(Dsml)
	 * @see org.isoe.diagraph.megamodel.MegamodelPackage#getNotation_Dsml()
	 * @see org.isoe.diagraph.megamodel.Dsml#getNotations
	 * @model opposite="notations" required="true" transient="false"
	 * @generated
	 */
	Dsml getDsml();

			/**
	 * Sets the value of the '{@link org.isoe.diagraph.megamodel.Notation#getDsml <em>Dsml</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dsml</em>' container reference.
	 * @see #getDsml()
	 * @generated
	 */
	void setDsml(Dsml value);

} // Notation
