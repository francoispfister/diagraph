/**
 */
package org.isoe.diagraph.diagraph;

import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>DLabeled Edge</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.isoe.diagraph.diagraph.DLabeledEdge#getSourceReference <em>Source Reference</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDLabeledEdge()
 * @model
 * @generated
 */
public interface DLabeledEdge extends DOwnedEdge, DLabeledElement, DLineEdge {
	/**
	 * Returns the value of the '<em><b>Source Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Reference</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Reference</em>' reference.
	 * @see #setSourceReference(EReference)
	 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDLabeledEdge_SourceReference()
	 * @model
	 * @generated
	 */
	EReference getSourceReference();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diagraph.DLabeledEdge#getSourceReference <em>Source Reference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Reference</em>' reference.
	 * @see #getSourceReference()
	 * @generated
	 */
	void setSourceReference(EReference value);

} // DLabeledEdge
