/**
 */
package org.isoe.mbse.sourcecleaner;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Extension Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.isoe.mbse.sourcecleaner.ExtensionReference#getSchema <em>Schema</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.ExtensionReference#getJavaclass <em>Javaclass</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.ExtensionReference#getName <em>Name</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.ExtensionReference#getJava <em>Java</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.ExtensionReference#getPackage <em>Package</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.ExtensionReference#getProject <em>Project</em>}</li>
 * </ul>
 *
 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getExtensionReference()
 * @model
 * @generated
 */
public interface ExtensionReference extends EObject {
	/**
	 * Returns the value of the '<em><b>Schema</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.isoe.mbse.sourcecleaner.Schema#getReferences <em>References</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Schema</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Schema</em>' container reference.
	 * @see #setSchema(Schema)
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getExtensionReference_Schema()
	 * @see org.isoe.mbse.sourcecleaner.Schema#getReferences
	 * @model opposite="references" transient="false"
	 * @generated
	 */
	Schema getSchema();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.ExtensionReference#getSchema <em>Schema</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Schema</em>' container reference.
	 * @see #getSchema()
	 * @generated
	 */
	void setSchema(Schema value);

	/**
	 * Returns the value of the '<em><b>Javaclass</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Javaclass</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Javaclass</em>' reference.
	 * @see #setJavaclass(Java)
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getExtensionReference_Javaclass()
	 * @model
	 * @generated
	 */
	Java getJavaclass();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.ExtensionReference#getJavaclass <em>Javaclass</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Javaclass</em>' reference.
	 * @see #getJavaclass()
	 * @generated
	 */
	void setJavaclass(Java value);

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
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getExtensionReference_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.ExtensionReference#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Java</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Java</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Java</em>' attribute.
	 * @see #setJava(String)
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getExtensionReference_Java()
	 * @model
	 * @generated
	 */
	String getJava();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.ExtensionReference#getJava <em>Java</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Java</em>' attribute.
	 * @see #getJava()
	 * @generated
	 */
	void setJava(String value);

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
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getExtensionReference_Package()
	 * @model
	 * @generated
	 */
	String getPackage();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.ExtensionReference#getPackage <em>Package</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Package</em>' attribute.
	 * @see #getPackage()
	 * @generated
	 */
	void setPackage(String value);

	/**
	 * Returns the value of the '<em><b>Project</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Project</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Project</em>' attribute.
	 * @see #setProject(String)
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getExtensionReference_Project()
	 * @model
	 * @generated
	 */
	String getProject();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.ExtensionReference#getProject <em>Project</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Project</em>' attribute.
	 * @see #getProject()
	 * @generated
	 */
	void setProject(String value);

} // ExtensionReference
