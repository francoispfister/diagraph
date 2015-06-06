/**
 */
package org.isoe.mbse.sourcecleaner;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Dependency</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Dependency#getName <em>Name</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Dependency#getVersion <em>Version</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Dependency#getDependency <em>Dependency</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Dependency#getRequerant <em>Requerant</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Dependency#isReexport <em>Reexport</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Dependency#isDiagraph <em>Diagraph</em>}</li>
 * </ul>
 *
 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getDependency()
 * @model
 * @generated
 */
public interface Dependency extends EObject {
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
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getDependency_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.Dependency#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version</em>' attribute.
	 * @see #setVersion(String)
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getDependency_Version()
	 * @model
	 * @generated
	 */
	String getVersion();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.Dependency#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(String value);

	/**
	 * Returns the value of the '<em><b>Dependency</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dependency</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dependency</em>' reference.
	 * @see #setDependency(Manifest)
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getDependency_Dependency()
	 * @model
	 * @generated
	 */
	Manifest getDependency();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.Dependency#getDependency <em>Dependency</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dependency</em>' reference.
	 * @see #getDependency()
	 * @generated
	 */
	void setDependency(Manifest value);

	/**
	 * Returns the value of the '<em><b>Requerant</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.isoe.mbse.sourcecleaner.Manifest#getDependencies <em>Dependencies</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Requerant</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Requerant</em>' container reference.
	 * @see #setRequerant(Manifest)
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getDependency_Requerant()
	 * @see org.isoe.mbse.sourcecleaner.Manifest#getDependencies
	 * @model opposite="dependencies" transient="false"
	 * @generated
	 */
	Manifest getRequerant();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.Dependency#getRequerant <em>Requerant</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Requerant</em>' container reference.
	 * @see #getRequerant()
	 * @generated
	 */
	void setRequerant(Manifest value);

	/**
	 * Returns the value of the '<em><b>Reexport</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Reexport</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reexport</em>' attribute.
	 * @see #setReexport(boolean)
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getDependency_Reexport()
	 * @model
	 * @generated
	 */
	boolean isReexport();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.Dependency#isReexport <em>Reexport</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reexport</em>' attribute.
	 * @see #isReexport()
	 * @generated
	 */
	void setReexport(boolean value);

	/**
	 * Returns the value of the '<em><b>Diagraph</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Diagraph</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Diagraph</em>' attribute.
	 * @see #setDiagraph(boolean)
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getDependency_Diagraph()
	 * @model
	 * @generated
	 */
	boolean isDiagraph();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.Dependency#isDiagraph <em>Diagraph</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Diagraph</em>' attribute.
	 * @see #isDiagraph()
	 * @generated
	 */
	void setDiagraph(boolean value);

} // Dependency
