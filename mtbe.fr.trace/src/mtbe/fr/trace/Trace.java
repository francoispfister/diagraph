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
package mtbe.fr.trace;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Trace</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link mtbe.fr.trace.Trace#getTraces <em>Traces</em>}</li>
 *   <li>{@link mtbe.fr.trace.Trace#getSourceModel <em>Source Model</em>}</li>
 *   <li>{@link mtbe.fr.trace.Trace#getTargetModel <em>Target Model</em>}</li>
 * </ul>
 * </p>
 *
 * @see mtbe.fr.trace.TracePackage#getTrace()
 * @model
 * @generated
 */
public interface Trace extends EObject {
	/**
	 * Returns the value of the '<em><b>Traces</b></em>' containment reference list.
	 * The list contents are of type {@link mtbe.fr.trace.TraceLink}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Traces</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Traces</em>' containment reference list.
	 * @see mtbe.fr.trace.TracePackage#getTrace_Traces()
	 * @model type="mtbe.fr.trace.TraceLink" containment="true"
	 * @generated
	 */
	EList getTraces();

	/**
	 * Returns the value of the '<em><b>Source Model</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Model</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Model</em>' reference.
	 * @see #setSourceModel(EObject)
	 * @see mtbe.fr.trace.TracePackage#getTrace_SourceModel()
	 * @model
	 * @generated
	 */
	EObject getSourceModel();

	/**
	 * Sets the value of the '{@link mtbe.fr.trace.Trace#getSourceModel <em>Source Model</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Model</em>' reference.
	 * @see #getSourceModel()
	 * @generated
	 */
	void setSourceModel(EObject value);

	/**
	 * Returns the value of the '<em><b>Target Model</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target Model</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Model</em>' reference.
	 * @see #setTargetModel(EObject)
	 * @see mtbe.fr.trace.TracePackage#getTrace_TargetModel()
	 * @model
	 * @generated
	 */
	EObject getTargetModel();

	/**
	 * Sets the value of the '{@link mtbe.fr.trace.Trace#getTargetModel <em>Target Model</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target Model</em>' reference.
	 * @see #getTargetModel()
	 * @generated
	 */
	void setTargetModel(EObject value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" type="org.eclipse.emf.ecore.EObject"
	 * @generated
	 */
	EList getAllMappedObjects();

} // Trace
