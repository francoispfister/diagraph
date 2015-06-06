/**
 */
package org.isoe.diagraph.diagraph;

import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>DGraph Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.isoe.diagraph.diagraph.DGraphElement#getName <em>Name</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.DGraphElement#getSemanticRole <em>Semantic Role</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.DGraphElement#getIcon <em>Icon</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.DGraphElement#getGraph <em>Graph</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.DGraphElement#isAbztract <em>Abztract</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDGraphElement()
 * @model abstract="true"
 * @generated
 */
public interface DGraphElement extends EObject {
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
	 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDGraphElement_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diagraph.DGraphElement#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Semantic Role</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Semantic Role</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Semantic Role</em>' reference.
	 * @see #setSemanticRole(ENamedElement)
	 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDGraphElement_SemanticRole()
	 * @model
	 * @generated
	 */
	ENamedElement getSemanticRole();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diagraph.DGraphElement#getSemanticRole <em>Semantic Role</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Semantic Role</em>' reference.
	 * @see #getSemanticRole()
	 * @generated
	 */
	void setSemanticRole(ENamedElement value);

	/**
	 * Returns the value of the '<em><b>Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Icon</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Icon</em>' attribute.
	 * @see #setIcon(String)
	 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDGraphElement_Icon()
	 * @model
	 * @generated
	 */
	String getIcon();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diagraph.DGraphElement#getIcon <em>Icon</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Icon</em>' attribute.
	 * @see #getIcon()
	 * @generated
	 */
	void setIcon(String value);

	/**
	 * Returns the value of the '<em><b>Graph</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.isoe.diagraph.diagraph.DGraph#getElements <em>Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Graph</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Graph</em>' container reference.
	 * @see #setGraph(DGraph)
	 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDGraphElement_Graph()
	 * @see org.isoe.diagraph.diagraph.DGraph#getElements
	 * @model opposite="elements" transient="false"
	 * @generated
	 */
	DGraph getGraph();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diagraph.DGraphElement#getGraph <em>Graph</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Graph</em>' container reference.
	 * @see #getGraph()
	 * @generated
	 */
	void setGraph(DGraph value);

	/**
	 * Returns the value of the '<em><b>Abztract</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Abztract</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Abztract</em>' attribute.
	 * @see #setAbztract(boolean)
	 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDGraphElement_Abztract()
	 * @model
	 * @generated
	 */
	boolean isAbztract();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diagraph.DGraphElement#isAbztract <em>Abztract</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Abztract</em>' attribute.
	 * @see #isAbztract()
	 * @generated
	 */
	void setAbztract(boolean value);

} // DGraphElement
