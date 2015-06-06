/**
 */
package org.isoe.diagraph.diagraph;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>DContainment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.isoe.diagraph.diagraph.DContainment#getNode <em>Node</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.DContainment#getName <em>Name</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.DContainment#getEdges <em>Edges</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDContainment()
 * @model
 * @generated
 */
public interface DContainment extends EObject {
	/**
	 * Returns the value of the '<em><b>Node</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.isoe.diagraph.diagraph.DNode#getContainments <em>Containments</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Node</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Node</em>' container reference.
	 * @see #setNode(DNode)
	 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDContainment_Node()
	 * @see org.isoe.diagraph.diagraph.DNode#getContainments
	 * @model opposite="containments" required="true" transient="false"
	 * @generated
	 */
	DNode getNode();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diagraph.DContainment#getNode <em>Node</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Node</em>' container reference.
	 * @see #getNode()
	 * @generated
	 */
	void setNode(DNode value);

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
	 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDContainment_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diagraph.DContainment#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Edges</b></em>' reference list.
	 * The list contents are of type {@link org.isoe.diagraph.diagraph.DNestedEdge}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Edges</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Edges</em>' reference list.
	 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDContainment_Edges()
	 * @model
	 * @generated
	 */
	EList<DNestedEdge> getEdges();

} // DContainment
