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
package org.isoe.diagraph.diastyle;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;

import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.diagraph.DGraphElement;

import org.isoe.diagraph.diastyle.helpers.IStyleHandler;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>DStyle</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.isoe.diagraph.diastyle.DStyle#getStyles <em>Styles</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.DStyle#getStyleHandler <em>Style Handler</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.DStyle#getDGraph <em>DGraph</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.isoe.diagraph.diastyle.DiastylePackage#getDStyle()
 * @model
 * @generated
 */
public interface DStyle extends EObject, EModelElement {
	/**
	 * Returns the value of the '<em><b>Styles</b></em>' containment reference list.
	 * The list contents are of type {@link org.isoe.diagraph.diastyle.DBaseStyle}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Styles</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Styles</em>' containment reference list.
	 * @see org.isoe.diagraph.diastyle.DiastylePackage#getDStyle_Styles()
	 * @model containment="true"
	 * @generated
	 */
	EList<DBaseStyle> getStyles();

	/**
	 * Returns the value of the '<em><b>Style Handler</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Style Handler</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Style Handler</em>' attribute.
	 * @see #setStyleHandler(IStyleHandler)
	 * @see org.isoe.diagraph.diastyle.DiastylePackage#getDStyle_StyleHandler()
	 * @model dataType="org.isoe.diagraph.diastyle.DStyleHandler" transient="true"
	 * @generated
	 */
	IStyleHandler getStyleHandler();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diastyle.DStyle#getStyleHandler <em>Style Handler</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Style Handler</em>' attribute.
	 * @see #getStyleHandler()
	 * @generated
	 */
	void setStyleHandler(IStyleHandler value);

	/**
	 * Returns the value of the '<em><b>DGraph</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>DGraph</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>DGraph</em>' reference.
	 * @see #setDGraph(DGraph)
	 * @see org.isoe.diagraph.diastyle.DiastylePackage#getDStyle_DGraph()
	 * @model
	 * @generated
	 */
	DGraph getDGraph();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diastyle.DStyle#getDGraph <em>DGraph</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>DGraph</em>' reference.
	 * @see #getDGraph()
	 * @generated
	 */
	void setDGraph(DGraph value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	String getBackgroundColor(DGraphElement element);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	String getFigure(DGraphElement element);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	String getIcon(DGraphElement element);

} // DStyle
