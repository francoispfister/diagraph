/**
 */
package org.isoe.diagraph.diagraph;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>DView Navigation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.isoe.diagraph.diagraph.DViewNavigation#getId <em>Id</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.DViewNavigation#getNavigationTarget <em>Navigation Target</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.DViewNavigation#getNavigationSource <em>Navigation Source</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDViewNavigation()
 * @model
 * @generated
 */
public interface DViewNavigation extends EObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDViewNavigation_Id()
	 * @model transient="true"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diagraph.DViewNavigation#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Navigation Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Navigation Target</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Navigation Target</em>' reference.
	 * @see #setNavigationTarget(DGraph)
	 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDViewNavigation_NavigationTarget()
	 * @model
	 * @generated
	 */
	DGraph getNavigationTarget();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diagraph.DViewNavigation#getNavigationTarget <em>Navigation Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Navigation Target</em>' reference.
	 * @see #getNavigationTarget()
	 * @generated
	 */
	void setNavigationTarget(DGraph value);

	/**
	 * Returns the value of the '<em><b>Navigation Source</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.isoe.diagraph.diagraph.DNode#getViewNavigation <em>View Navigation</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Navigation Source</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Navigation Source</em>' container reference.
	 * @see #setNavigationSource(DNode)
	 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDViewNavigation_NavigationSource()
	 * @see org.isoe.diagraph.diagraph.DNode#getViewNavigation
	 * @model opposite="viewNavigation" transient="false"
	 * @generated
	 */
	DNode getNavigationSource();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diagraph.DViewNavigation#getNavigationSource <em>Navigation Source</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Navigation Source</em>' container reference.
	 * @see #getNavigationSource()
	 * @generated
	 */
	void setNavigationSource(DNode value);

} // DViewNavigation
