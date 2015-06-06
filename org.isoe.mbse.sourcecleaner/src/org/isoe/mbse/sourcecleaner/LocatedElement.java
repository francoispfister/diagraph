/**
 */
package org.isoe.mbse.sourcecleaner;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Located Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.isoe.mbse.sourcecleaner.LocatedElement#getAbsolutePath <em>Absolute Path</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.LocatedElement#getName <em>Name</em>}</li>
 * </ul>
 *
 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getLocatedElement()
 * @model abstract="true"
 *        annotation="diagraph label\075name='null'"
 * @generated
 */
public interface LocatedElement extends EObject {
	/**
	 * Returns the value of the '<em><b>Absolute Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Absolute Path </em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Absolute Path</em>' attribute.
	 * @see #setAbsolutePath(String)
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getLocatedElement_AbsolutePath()
	 * @model transient="true"
	 * @generated
	 */
	String getAbsolutePath();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.LocatedElement#getAbsolutePath <em>Absolute Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Absolute Path</em>' attribute.
	 * @see #getAbsolutePath()
	 * @generated
	 */
	void setAbsolutePath(String value);

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
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getLocatedElement_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.LocatedElement#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // LocatedElement
