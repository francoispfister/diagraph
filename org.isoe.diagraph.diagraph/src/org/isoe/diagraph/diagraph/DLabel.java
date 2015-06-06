/**
 */
package org.isoe.diagraph.diagraph;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>DLabel</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.isoe.diagraph.diagraph.DLabel#getAttribute <em>Attribute</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.DLabel#isPropagated <em>Propagated</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.DLabel#isInferred <em>Inferred</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.DLabel#isAbztract <em>Abztract</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDLabel()
 * @model
 * @generated
 */
public interface DLabel extends EObject {
	/**
	 * Returns the value of the '<em><b>Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute</em>' reference.
	 * @see #setAttribute(EAttribute)
	 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDLabel_Attribute()
	 * @model
	 * @generated
	 */
	EAttribute getAttribute();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diagraph.DLabel#getAttribute <em>Attribute</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute</em>' reference.
	 * @see #getAttribute()
	 * @generated
	 */
	void setAttribute(EAttribute value);

	/**
	 * Returns the value of the '<em><b>Propagated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Propagated</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Propagated</em>' attribute.
	 * @see #setPropagated(boolean)
	 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDLabel_Propagated()
	 * @model
	 * @generated
	 */
	boolean isPropagated();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diagraph.DLabel#isPropagated <em>Propagated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Propagated</em>' attribute.
	 * @see #isPropagated()
	 * @generated
	 */
	void setPropagated(boolean value);

	/**
	 * Returns the value of the '<em><b>Inferred</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Inferred</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Inferred</em>' attribute.
	 * @see #setInferred(boolean)
	 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDLabel_Inferred()
	 * @model
	 * @generated
	 */
	boolean isInferred();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diagraph.DLabel#isInferred <em>Inferred</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Inferred</em>' attribute.
	 * @see #isInferred()
	 * @generated
	 */
	void setInferred(boolean value);

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
	 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDLabel_Abztract()
	 * @model
	 * @generated
	 */
	boolean isAbztract();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diagraph.DLabel#isAbztract <em>Abztract</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Abztract</em>' attribute.
	 * @see #isAbztract()
	 * @generated
	 */
	void setAbztract(boolean value);

} // DLabel
