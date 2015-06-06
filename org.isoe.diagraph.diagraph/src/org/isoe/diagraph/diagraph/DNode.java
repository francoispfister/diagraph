/**
 */
package org.isoe.diagraph.diagraph;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>DNode</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.isoe.diagraph.diagraph.DNode#getViewNavigation <em>View Navigation</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.DNode#getShape <em>Shape</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.DNode#isLayout <em>Layout</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.DNode#getNavigationLink <em>Navigation Link</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.DNode#getContainments <em>Containments</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDNode()
 * @model
 * @generated
 */
public interface DNode extends DLabeledElement, DOwnedElement {
	/**
	 * Returns the value of the '<em><b>View Navigation</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link org.isoe.diagraph.diagraph.DViewNavigation#getNavigationSource <em>Navigation Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>View Navigation</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>View Navigation</em>' containment reference.
	 * @see #setViewNavigation(DViewNavigation)
	 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDNode_ViewNavigation()
	 * @see org.isoe.diagraph.diagraph.DViewNavigation#getNavigationSource
	 * @model opposite="navigationSource" containment="true"
	 * @generated
	 */
	DViewNavigation getViewNavigation();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diagraph.DNode#getViewNavigation <em>View Navigation</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>View Navigation</em>' containment reference.
	 * @see #getViewNavigation()
	 * @generated
	 */
	void setViewNavigation(DViewNavigation value);

	/**
	 * Returns the value of the '<em><b>Shape</b></em>' attribute.
	 * The literals are from the enumeration {@link org.isoe.diagraph.diagraph.DShape}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Shape</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Shape</em>' attribute.
	 * @see org.isoe.diagraph.diagraph.DShape
	 * @see #setShape(DShape)
	 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDNode_Shape()
	 * @model
	 * @generated
	 */
	DShape getShape();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diagraph.DNode#getShape <em>Shape</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Shape</em>' attribute.
	 * @see org.isoe.diagraph.diagraph.DShape
	 * @see #getShape()
	 * @generated
	 */
	void setShape(DShape value);

	/**
	 * Returns the value of the '<em><b>Layout</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Layout</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Layout</em>' attribute.
	 * @see #setLayout(boolean)
	 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDNode_Layout()
	 * @model
	 * @generated
	 */
	boolean isLayout();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diagraph.DNode#isLayout <em>Layout</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Layout</em>' attribute.
	 * @see #isLayout()
	 * @generated
	 */
	void setLayout(boolean value);

	/**
	 * Returns the value of the '<em><b>Navigation Link</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Navigation Link</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Navigation Link</em>' attribute.
	 * @see #setNavigationLink(String)
	 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDNode_NavigationLink()
	 * @model
	 * @generated
	 */
	String getNavigationLink();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diagraph.DNode#getNavigationLink <em>Navigation Link</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Navigation Link</em>' attribute.
	 * @see #getNavigationLink()
	 * @generated
	 */
	void setNavigationLink(String value);

	/**
	 * Returns the value of the '<em><b>Containments</b></em>' containment reference list.
	 * The list contents are of type {@link org.isoe.diagraph.diagraph.DContainment}.
	 * It is bidirectional and its opposite is '{@link org.isoe.diagraph.diagraph.DContainment#getNode <em>Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Containments</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Containments</em>' containment reference list.
	 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDNode_Containments()
	 * @see org.isoe.diagraph.diagraph.DContainment#getNode
	 * @model opposite="node" containment="true"
	 * @generated
	 */
	EList<DContainment> getContainments();

} // DNode
