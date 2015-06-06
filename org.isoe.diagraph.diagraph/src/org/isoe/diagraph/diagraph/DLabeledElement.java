/**
 */
package org.isoe.diagraph.diagraph;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>DLabeled Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.isoe.diagraph.diagraph.DLabeledElement#getEClaz <em>EClaz</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.DLabeledElement#getDlabels <em>Dlabels</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.DLabeledElement#getLabls <em>Labls</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.DLabeledElement#getExpression <em>Expression</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDLabeledElement()
 * @model abstract="true"
 * @generated
 */
public interface DLabeledElement extends DGraphElement {
	/**
	 * Returns the value of the '<em><b>EClaz</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>EClaz</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>EClaz</em>' reference.
	 * @see #setEClaz(EClass)
	 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDLabeledElement_EClaz()
	 * @model
	 * @generated
	 */
	EClass getEClaz();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diagraph.DLabeledElement#getEClaz <em>EClaz</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>EClaz</em>' reference.
	 * @see #getEClaz()
	 * @generated
	 */
	void setEClaz(EClass value);

	/**
	 * Returns the value of the '<em><b>Dlabels</b></em>' containment reference list.
	 * The list contents are of type {@link org.isoe.diagraph.diagraph.DLabel}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dlabels</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dlabels</em>' containment reference list.
	 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDLabeledElement_Dlabels()
	 * @model containment="true"
	 * @generated
	 */
	EList<DLabel> getDlabels();

	/**
	 * Returns the value of the '<em><b>Labls</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Labls</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Labls</em>' attribute list.
	 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDLabeledElement_Labls()
	 * @model
	 * @generated
	 */
	EList<String> getLabls();

	/**
	 * Returns the value of the '<em><b>Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Expression</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expression</em>' attribute.
	 * @see #setExpression(String)
	 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDLabeledElement_Expression()
	 * @model
	 * @generated
	 */
	String getExpression();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diagraph.DLabeledElement#getExpression <em>Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expression</em>' attribute.
	 * @see #getExpression()
	 * @generated
	 */
	void setExpression(String value);

} // DLabeledElement
