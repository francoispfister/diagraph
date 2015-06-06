/**
 */
package org.isoe.mbse.sourcecleaner;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Plugin</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Plugin#getExtensions <em>Extensions</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Plugin#getExtensionPoints <em>Extension Points</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Plugin#getProject <em>Project</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Plugin#getExtra <em>Extra</em>}</li>
 * </ul>
 *
 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getPlugin()
 * @model
 * @generated
 */
public interface Plugin extends Source {
	/**
	 * Returns the value of the '<em><b>Extensions</b></em>' containment reference list.
	 * The list contents are of type {@link org.isoe.mbse.sourcecleaner.Extension}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extensions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Extensions</em>' containment reference list.
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getPlugin_Extensions()
	 * @model containment="true"
	 * @generated
	 */
	EList<Extension> getExtensions();

	/**
	 * Returns the value of the '<em><b>Extension Points</b></em>' containment reference list.
	 * The list contents are of type {@link org.isoe.mbse.sourcecleaner.ExtensionPoint}.
	 * It is bidirectional and its opposite is '{@link org.isoe.mbse.sourcecleaner.ExtensionPoint#getPlugin <em>Plugin</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extension Points</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Extension Points</em>' containment reference list.
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getPlugin_ExtensionPoints()
	 * @see org.isoe.mbse.sourcecleaner.ExtensionPoint#getPlugin
	 * @model opposite="plugin" containment="true"
	 * @generated
	 */
	EList<ExtensionPoint> getExtensionPoints();

	/**
	 * Returns the value of the '<em><b>Project</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.isoe.mbse.sourcecleaner.Project#getPlugin <em>Plugin</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Project</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Project</em>' container reference.
	 * @see #setProject(Project)
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getPlugin_Project()
	 * @see org.isoe.mbse.sourcecleaner.Project#getPlugin
	 * @model opposite="plugin" transient="false"
	 * @generated
	 */
	Project getProject();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.Plugin#getProject <em>Project</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Project</em>' container reference.
	 * @see #getProject()
	 * @generated
	 */
	void setProject(Project value);

	/**
	 * Returns the value of the '<em><b>Extra</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extra</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Extra</em>' attribute.
	 * @see #setExtra(String)
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getPlugin_Extra()
	 * @model
	 * @generated
	 */
	String getExtra();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.Plugin#getExtra <em>Extra</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Extra</em>' attribute.
	 * @see #getExtra()
	 * @generated
	 */
	void setExtra(String value);

} // Plugin
