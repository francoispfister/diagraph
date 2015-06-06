/**
 */
package org.isoe.diagraph.diagraph;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>DOwned Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.isoe.diagraph.diagraph.DOwnedElement#getOwner <em>Owner</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDOwnedElement()
 * @model abstract="true"
 * @generated
 */
public interface DOwnedElement extends EObject {
	/**
	 * Returns the value of the '<em><b>Owner</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owner</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owner</em>' reference.
	 * @see #setOwner(DNode)
	 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDOwnedElement_Owner()
	 * @model
	 * @generated
	 */
	DNode getOwner();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diagraph.DOwnedElement#getOwner <em>Owner</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owner</em>' reference.
	 * @see #getOwner()
	 * @generated
	 */
	void setOwner(DNode value);

} // DOwnedElement
