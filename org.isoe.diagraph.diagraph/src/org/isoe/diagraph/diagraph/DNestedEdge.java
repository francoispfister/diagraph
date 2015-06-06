/**
 */
package org.isoe.diagraph.diagraph;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>DNested Edge</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.isoe.diagraph.diagraph.DNestedEdge#getSource <em>Source</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDNestedEdge()
 * @model
 * @generated
 */
public interface DNestedEdge extends DOwnedEdge {
	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(DContainment)
	 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDNestedEdge_Source()
	 * @model required="true"
	 * @generated
	 */
	DContainment getSource();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diagraph.DNestedEdge#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(DContainment value);



} // DNestedEdge
