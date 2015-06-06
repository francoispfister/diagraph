/**
 */
package org.isoe.mbse.sourcecleaner;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Configuration#getProjects <em>Projects</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Configuration#getLocation <em>Location</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Configuration#getTemp <em>Temp</em>}</li>
 * </ul>
 *
 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getConfiguration()
 * @model annotation="diagraph node='null' pov='null'"
 * @generated
 */
public interface Configuration extends EObject {
	/**
	 * Returns the value of the '<em><b>Projects</b></em>' containment reference list.
	 * The list contents are of type {@link org.isoe.mbse.sourcecleaner.Project}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Projects</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Projects</em>' containment reference list.
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getConfiguration_Projects()
	 * @model containment="true"
	 * @generated
	 */
	EList<Project> getProjects();

	/**
	 * Returns the value of the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Location</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Location</em>' attribute.
	 * @see #setLocation(String)
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getConfiguration_Location()
	 * @model
	 * @generated
	 */
	String getLocation();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.Configuration#getLocation <em>Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Location</em>' attribute.
	 * @see #getLocation()
	 * @generated
	 */
	void setLocation(String value);

	/**
	 * Returns the value of the '<em><b>Temp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Temp</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Temp</em>' attribute.
	 * @see #setTemp(String)
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getConfiguration_Temp()
	 * @model
	 * @generated
	 */
	String getTemp();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.Configuration#getTemp <em>Temp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Temp</em>' attribute.
	 * @see #getTemp()
	 * @generated
	 */
	void setTemp(String value);



} // Configuration
