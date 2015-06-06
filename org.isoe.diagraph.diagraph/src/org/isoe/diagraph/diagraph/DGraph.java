/**
 */
package org.isoe.diagraph.diagraph;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.isoe.diagraph.diagraph.helpers.IGraphHandler;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>DGraph</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.isoe.diagraph.diagraph.DGraph#getElements <em>Elements</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.DGraph#getPointOfView <em>Point Of View</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.DGraph#getViewName <em>View Name</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.DGraph#getFacade1 <em>Facade1</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.DGraph#getFacade2 <em>Facade2</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDGraph()
 * @model
 * @generated
 */
public interface DGraph extends EObject {
	/**
	 * Returns the value of the '<em><b>Elements</b></em>' containment reference list.
	 * The list contents are of type {@link org.isoe.diagraph.diagraph.DGraphElement}.
	 * It is bidirectional and its opposite is '{@link org.isoe.diagraph.diagraph.DGraphElement#getGraph <em>Graph</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Elements</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Elements</em>' containment reference list.
	 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDGraph_Elements()
	 * @see org.isoe.diagraph.diagraph.DGraphElement#getGraph
	 * @model opposite="graph" containment="true"
	 * @generated
	 */
	EList<DGraphElement> getElements();

	/**
	 * Returns the value of the '<em><b>Point Of View</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Point Of View</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Point Of View</em>' reference.
	 * @see #setPointOfView(DPointOfView)
	 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDGraph_PointOfView()
	 * @model required="true"
	 * @generated
	 */
	DPointOfView getPointOfView();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diagraph.DGraph#getPointOfView <em>Point Of View</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Point Of View</em>' reference.
	 * @see #getPointOfView()
	 * @generated
	 */
	void setPointOfView(DPointOfView value);

	/**
	 * Returns the value of the '<em><b>View Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>View Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>View Name</em>' attribute.
	 * @see #setViewName(String)
	 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDGraph_ViewName()
	 * @model
	 * @generated
	 */
	String getViewName();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diagraph.DGraph#getViewName <em>View Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>View Name</em>' attribute.
	 * @see #getViewName()
	 * @generated
	 */
	void setViewName(String value);

	/**
	 * Returns the value of the '<em><b>Facade1</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Facade1</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Facade1</em>' attribute.
	 * @see #setFacade1(IGraphHandler)
	 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDGraph_Facade1()
	 * @model dataType="org.isoe.diagraph.diagraph.DGraphHandler" transient="true"
	 * @generated
	 */
	IGraphHandler getFacade1();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diagraph.DGraph#getFacade1 <em>Facade1</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Facade1</em>' attribute.
	 * @see #getFacade1()
	 * @generated
	 */
	void setFacade1(IGraphHandler value);

	/**
	 * Returns the value of the '<em><b>Facade2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Facade2</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Facade2</em>' attribute.
	 * @see #setFacade2(IGraphHandler)
	 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDGraph_Facade2()
	 * @model dataType="org.isoe.diagraph.diagraph.DGraphHandler"
	 * @generated
	 */
	IGraphHandler getFacade2();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diagraph.DGraph#getFacade2 <em>Facade2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Facade2</em>' attribute.
	 * @see #getFacade2()
	 * @generated
	 */
	void setFacade2(IGraphHandler value);

} // DGraph
