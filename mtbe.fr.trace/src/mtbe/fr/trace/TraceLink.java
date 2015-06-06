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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Link</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link mtbe.fr.trace.TraceLink#getName <em>Name</em>}</li>
 *   <li>{@link mtbe.fr.trace.TraceLink#getSimilarity <em>Similarity</em>}</li>
 *   <li>{@link mtbe.fr.trace.TraceLink#getRequiredSimilarity <em>Required Similarity</em>}</li>
 *   <li>{@link mtbe.fr.trace.TraceLink#getRationale <em>Rationale</em>}</li>
 *   <li>{@link mtbe.fr.trace.TraceLink#getSimilarityMethod <em>Similarity Method</em>}</li>
 *   <li>{@link mtbe.fr.trace.TraceLink#getSource <em>Source</em>}</li>
 *   <li>{@link mtbe.fr.trace.TraceLink#getTarget <em>Target</em>}</li>
 *   <li>{@link mtbe.fr.trace.TraceLink#getTargetValue <em>Target Value</em>}</li>
 *   <li>{@link mtbe.fr.trace.TraceLink#getSourceValue <em>Source Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see mtbe.fr.trace.TracePackage#getTraceLink()
 * @model
 * @generated
 */
public interface TraceLink extends EObject {
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
	 * @see mtbe.fr.trace.TracePackage#getTraceLink_Name()
	 * @model id="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link mtbe.fr.trace.TraceLink#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Similarity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Similarity</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Similarity</em>' attribute.
	 * @see #setSimilarity(int)
	 * @see mtbe.fr.trace.TracePackage#getTraceLink_Similarity()
	 * @model
	 * @generated
	 */
	int getSimilarity();

	/**
	 * Sets the value of the '{@link mtbe.fr.trace.TraceLink#getSimilarity <em>Similarity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Similarity</em>' attribute.
	 * @see #getSimilarity()
	 * @generated
	 */
	void setSimilarity(int value);

	/**
	 * Returns the value of the '<em><b>Required Similarity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Required Similarity</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Required Similarity</em>' attribute.
	 * @see #setRequiredSimilarity(int)
	 * @see mtbe.fr.trace.TracePackage#getTraceLink_RequiredSimilarity()
	 * @model
	 * @generated
	 */
	int getRequiredSimilarity();

	/**
	 * Sets the value of the '{@link mtbe.fr.trace.TraceLink#getRequiredSimilarity <em>Required Similarity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Required Similarity</em>' attribute.
	 * @see #getRequiredSimilarity()
	 * @generated
	 */
	void setRequiredSimilarity(int value);

	/**
	 * Returns the value of the '<em><b>Rationale</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rationale</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rationale</em>' attribute.
	 * @see #setRationale(String)
	 * @see mtbe.fr.trace.TracePackage#getTraceLink_Rationale()
	 * @model
	 * @generated
	 */
	String getRationale();

	/**
	 * Sets the value of the '{@link mtbe.fr.trace.TraceLink#getRationale <em>Rationale</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rationale</em>' attribute.
	 * @see #getRationale()
	 * @generated
	 */
	void setRationale(String value);

	/**
	 * Returns the value of the '<em><b>Similarity Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Similarity Method</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Similarity Method</em>' attribute.
	 * @see #setSimilarityMethod(int)
	 * @see mtbe.fr.trace.TracePackage#getTraceLink_SimilarityMethod()
	 * @model
	 * @generated
	 */
	int getSimilarityMethod();

	/**
	 * Sets the value of the '{@link mtbe.fr.trace.TraceLink#getSimilarityMethod <em>Similarity Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Similarity Method</em>' attribute.
	 * @see #getSimilarityMethod()
	 * @generated
	 */
	void setSimilarityMethod(int value);

	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(EObject)
	 * @see mtbe.fr.trace.TracePackage#getTraceLink_Source()
	 * @model
	 * @generated
	 */
	EObject getSource();

	/**
	 * Sets the value of the '{@link mtbe.fr.trace.TraceLink#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(EObject value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(EObject)
	 * @see mtbe.fr.trace.TracePackage#getTraceLink_Target()
	 * @model
	 * @generated
	 */
	EObject getTarget();

	/**
	 * Sets the value of the '{@link mtbe.fr.trace.TraceLink#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(EObject value);

	/**
	 * Returns the value of the '<em><b>Target Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Value</em>' attribute.
	 * @see #setTargetValue(String)
	 * @see mtbe.fr.trace.TracePackage#getTraceLink_TargetValue()
	 * @model
	 * @generated
	 */
	String getTargetValue();

	/**
	 * Sets the value of the '{@link mtbe.fr.trace.TraceLink#getTargetValue <em>Target Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target Value</em>' attribute.
	 * @see #getTargetValue()
	 * @generated
	 */
	void setTargetValue(String value);

	/**
	 * Returns the value of the '<em><b>Source Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Value</em>' attribute.
	 * @see #setSourceValue(String)
	 * @see mtbe.fr.trace.TracePackage#getTraceLink_SourceValue()
	 * @model
	 * @generated
	 */
	String getSourceValue();

	/**
	 * Sets the value of the '{@link mtbe.fr.trace.TraceLink#getSourceValue <em>Source Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Value</em>' attribute.
	 * @see #getSourceValue()
	 * @generated
	 */
	void setSourceValue(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	boolean sameAs(TraceLink tracelink);

} // TraceLink
