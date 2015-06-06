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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Diagram</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.isoe.diagraph.megamodel.Diagram#getDiagramPictureURI <em>Diagram Picture URI</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.isoe.diagraph.megamodel.MegamodelPackage#getDiagram()
 * @model abstract="true"
 *        annotation="diagraph node='null'"
 * @generated
 */
public interface Diagram extends MegamodelElement {
   /**
	 * Returns the value of the '<em><b>Diagram Picture URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Diagram Picture URI</em>' attribute isn't clear,
    * there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
	 * @return the value of the '<em>Diagram Picture URI</em>' attribute.
	 * @see #setDiagramPictureURI(String)
	 * @see org.isoe.diagraph.megamodel.MegamodelPackage#getDiagram_DiagramPictureURI()
	 * @model
	 * @generated
	 */
   String getDiagramPictureURI();

   /**
	 * Sets the value of the '{@link org.isoe.diagraph.megamodel.Diagram#getDiagramPictureURI <em>Diagram Picture URI</em>}' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Diagram Picture URI</em>' attribute.
	 * @see #getDiagramPictureURI()
	 * @generated
	 */
   void setDiagramPictureURI(String value);

} // Diagram
