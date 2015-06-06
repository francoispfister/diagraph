/**
 */
package org.isoe.mbse.sourcecleaner;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Schema</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Schema#getReferences <em>References</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Schema#getProject <em>Project</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Schema#getExtensionName <em>Extension Name</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Schema#getExtensionId <em>Extension Id</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Schema#getPluginName <em>Plugin Name</em>}</li>
 * </ul>
 *
 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getSchema()
 * @model
 * @generated
 */
public interface Schema extends Source {
	/**
	 * Returns the value of the '<em><b>References</b></em>' containment reference list.
	 * The list contents are of type {@link org.isoe.mbse.sourcecleaner.ExtensionReference}.
	 * It is bidirectional and its opposite is '{@link org.isoe.mbse.sourcecleaner.ExtensionReference#getSchema <em>Schema</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>References</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>References</em>' containment reference list.
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getSchema_References()
	 * @see org.isoe.mbse.sourcecleaner.ExtensionReference#getSchema
	 * @model opposite="schema" containment="true"
	 * @generated
	 */
	EList<ExtensionReference> getReferences();

	/**
	 * Returns the value of the '<em><b>Project</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.isoe.mbse.sourcecleaner.Project#getSchema <em>Schema</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Project</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Project</em>' container reference.
	 * @see #setProject(Project)
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getSchema_Project()
	 * @see org.isoe.mbse.sourcecleaner.Project#getSchema
	 * @model opposite="schema" transient="false"
	 * @generated
	 */
	Project getProject();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.Schema#getProject <em>Project</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Project</em>' container reference.
	 * @see #getProject()
	 * @generated
	 */
	void setProject(Project value);

	/**
	 * Returns the value of the '<em><b>Extension Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extension Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Extension Name</em>' attribute.
	 * @see #setExtensionName(String)
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getSchema_ExtensionName()
	 * @model
	 * @generated
	 */
	String getExtensionName();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.Schema#getExtensionName <em>Extension Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Extension Name</em>' attribute.
	 * @see #getExtensionName()
	 * @generated
	 */
	void setExtensionName(String value);

	/**
	 * Returns the value of the '<em><b>Extension Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extension Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Extension Id</em>' attribute.
	 * @see #setExtensionId(String)
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getSchema_ExtensionId()
	 * @model
	 * @generated
	 */
	String getExtensionId();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.Schema#getExtensionId <em>Extension Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Extension Id</em>' attribute.
	 * @see #getExtensionId()
	 * @generated
	 */
	void setExtensionId(String value);

	/**
	 * Returns the value of the '<em><b>Plugin Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Plugin Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Plugin Name</em>' attribute.
	 * @see #setPluginName(String)
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getSchema_PluginName()
	 * @model
	 * @generated
	 */
	String getPluginName();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.Schema#getPluginName <em>Plugin Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Plugin Name</em>' attribute.
	 * @see #getPluginName()
	 * @generated
	 */
	void setPluginName(String value);

} // Schema
