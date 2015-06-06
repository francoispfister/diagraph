/**
 */
package org.isoe.diagraph.diagraph;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>DSimple Edge</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.isoe.diagraph.diagraph.DSimpleEdge#getSource <em>Source</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDSimpleEdge()
 * @model abstract="true"
 * @generated
 */
public interface DSimpleEdge extends DEdge {
	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(DNode)
	 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDSimpleEdge_Source()
	 * @model required="true"
	 * @generated
	 */
	DNode getSource();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diagraph.DSimpleEdge#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(DNode value);

} // DSimpleEdge
