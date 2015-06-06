/**
 */
package org.isoe.mbse.sourcecleaner;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Extension Point</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.isoe.mbse.sourcecleaner.ExtensionPoint#getId <em>Id</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.ExtensionPoint#getName <em>Name</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.ExtensionPoint#getSchema <em>Schema</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.ExtensionPoint#isDiagraph <em>Diagraph</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.ExtensionPoint#getExtensions <em>Extensions</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.ExtensionPoint#getPlugin <em>Plugin</em>}</li>
 * </ul>
 *
 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getExtensionPoint()
 * @model
 * @generated
 */
public interface ExtensionPoint extends EObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getExtensionPoint_Id()
	 * @model
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.ExtensionPoint#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

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
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getExtensionPoint_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.ExtensionPoint#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Schema</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Schema</em>' attribute.
	 * @see #setSchema(String)
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getExtensionPoint_Schema()
	 * @model
	 * @generated
	 */
	String getSchema();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.ExtensionPoint#getSchema <em>Schema</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Schema</em>' attribute.
	 * @see #getSchema()
	 * @generated
	 */
	void setSchema(String value);

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
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getExtensionPoint_Diagraph()
	 * @model
	 * @generated
	 */
	boolean isDiagraph();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.ExtensionPoint#isDiagraph <em>Diagraph</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Diagraph</em>' attribute.
	 * @see #isDiagraph()
	 * @generated
	 */
	void setDiagraph(boolean value);

	/**
	 * Returns the value of the '<em><b>Extensions</b></em>' reference list.
	 * The list contents are of type {@link org.isoe.mbse.sourcecleaner.Extension}.
	 * It is bidirectional and its opposite is '{@link org.isoe.mbse.sourcecleaner.Extension#getExtensionPoint <em>Extension Point</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extensions</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Extensions</em>' reference list.
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getExtensionPoint_Extensions()
	 * @see org.isoe.mbse.sourcecleaner.Extension#getExtensionPoint
	 * @model opposite="extensionPoint"
	 * @generated
	 */
	EList<Extension> getExtensions();

	/**
	 * Returns the value of the '<em><b>Plugin</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.isoe.mbse.sourcecleaner.Plugin#getExtensionPoints <em>Extension Points</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Plugin</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Plugin</em>' container reference.
	 * @see #setPlugin(Plugin)
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getExtensionPoint_Plugin()
	 * @see org.isoe.mbse.sourcecleaner.Plugin#getExtensionPoints
	 * @model opposite="extensionPoints" transient="false"
	 * @generated
	 */
	Plugin getPlugin();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.ExtensionPoint#getPlugin <em>Plugin</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Plugin</em>' container reference.
	 * @see #getPlugin()
	 * @generated
	 */
	void setPlugin(Plugin value);

} // ExtensionPoint
