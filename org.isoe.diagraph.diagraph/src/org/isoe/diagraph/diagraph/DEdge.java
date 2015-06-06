/**
 */
package org.isoe.diagraph.diagraph;

import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>DEdge</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.isoe.diagraph.diagraph.DEdge#getTarget <em>Target</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.DEdge#getTargetReference <em>Target Reference</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.DEdge#isPropagated <em>Propagated</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDEdge()
 * @model abstract="true"
 * @generated
 */
public interface DEdge extends DGraphElement {
	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(DNode)
	 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDEdge_Target()
	 * @model required="true"
	 * @generated
	 */
	DNode getTarget();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diagraph.DEdge#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(DNode value);

	/**
	 * Returns the value of the '<em><b>Target Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target Reference</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Reference</em>' reference.
	 * @see #setTargetReference(EReference)
	 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDEdge_TargetReference()
	 * @model
	 * @generated
	 */
	EReference getTargetReference();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diagraph.DEdge#getTargetReference <em>Target Reference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target Reference</em>' reference.
	 * @see #getTargetReference()
	 * @generated
	 */
	void setTargetReference(EReference value);

	/**
	 * Returns the value of the '<em><b>Propagated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Propagated</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Propagated</em>' attribute.
	 * @see #setPropagated(boolean)
	 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDEdge_Propagated()
	 * @model
	 * @generated
	 */
	boolean isPropagated();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diagraph.DEdge#isPropagated <em>Propagated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Propagated</em>' attribute.
	 * @see #isPropagated()
	 * @generated
	 */
	void setPropagated(boolean value);

	boolean isDerived();

	boolean isInferred();

} // DEdge
