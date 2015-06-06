/**
 */
package org.isoe.mbse.sourcecleaner;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Java#getPackage <em>Package</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Java#getProject <em>Project</em>}</li>
 * </ul>
 *
 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getJava()
 * @model
 * @generated
 */
public interface Java extends Source {

	/**
	 * Returns the value of the '<em><b>Package</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Package</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Package</em>' attribute.
	 * @see #setPackage(String)
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getJava_Package()
	 * @model
	 * @generated
	 */
	String getPackage();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.Java#getPackage <em>Package</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Package</em>' attribute.
	 * @see #getPackage()
	 * @generated
	 */
	void setPackage(String value);

	/**
	 * Returns the value of the '<em><b>Project</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.isoe.mbse.sourcecleaner.Project#getSources <em>Sources</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Project</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Project</em>' container reference.
	 * @see #setProject(Project)
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getJava_Project()
	 * @see org.isoe.mbse.sourcecleaner.Project#getSources
	 * @model opposite="sources" transient="false"
	 * @generated
	 */
	Project getProject();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.Java#getProject <em>Project</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Project</em>' container reference.
	 * @see #getProject()
	 * @generated
	 */
	void setProject(Project value);
} // Java
