/**
 */
package org.isoe.mbse.sourcecleaner;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Extension</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Extension#getExtensionPoint <em>Extension Point</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Extension#getPointId <em>Point Id</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Extension#getClazz <em>Clazz</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Extension#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Extension#getImplements <em>Implements</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Extension#getId <em>Id</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Extension#getName <em>Name</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Extension#getExtra <em>Extra</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Extension#isDiagraph <em>Diagraph</em>}</li>
 * </ul>
 *
 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getExtension()
 * @model
 * @generated
 */
public interface Extension extends EObject {
	/**
	 * Returns the value of the '<em><b>Extension Point</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.isoe.mbse.sourcecleaner.ExtensionPoint#getExtensions <em>Extensions</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extension Point</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Extension Point</em>' reference.
	 * @see #setExtensionPoint(ExtensionPoint)
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getExtension_ExtensionPoint()
	 * @see org.isoe.mbse.sourcecleaner.ExtensionPoint#getExtensions
	 * @model opposite="extensions"
	 * @generated
	 */
	ExtensionPoint getExtensionPoint();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.Extension#getExtensionPoint <em>Extension Point</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Extension Point</em>' reference.
	 * @see #getExtensionPoint()
	 * @generated
	 */
	void setExtensionPoint(ExtensionPoint value);

	/**
	 * Returns the value of the '<em><b>Clazz</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Clazz</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Clazz</em>' attribute.
	 * @see #setClazz(String)
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getExtension_Clazz()
	 * @model
	 * @generated
	 */
	String getClazz();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.Extension#getClazz <em>Clazz</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Clazz</em>' attribute.
	 * @see #getClazz()
	 * @generated
	 */
	void setClazz(String value);

	/**
	 * Returns the value of the '<em><b>Point Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Point Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Point Id</em>' attribute.
	 * @see #setPointId(String)
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getExtension_PointId()
	 * @model
	 * @generated
	 */
	String getPointId();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.Extension#getPointId <em>Point Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Point Id</em>' attribute.
	 * @see #getPointId()
	 * @generated
	 */
	void setPointId(String value);

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
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getExtension_Extra()
	 * @model
	 * @generated
	 */
	String getExtra();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.Extension#getExtra <em>Extra</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Extra</em>' attribute.
	 * @see #getExtra()
	 * @generated
	 */
	void setExtra(String value);

	/**
	 * Returns the value of the '<em><b>Attributes</b></em>' containment reference list.
	 * The list contents are of type {@link org.isoe.mbse.sourcecleaner.ExtensionAttribute}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attributes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attributes</em>' containment reference list.
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getExtension_Attributes()
	 * @model containment="true"
	 * @generated
	 */
	EList<ExtensionAttribute> getAttributes();

	/**
	 * Returns the value of the '<em><b>Implements</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Implements</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Implements</em>' reference.
	 * @see #setImplements(Java)
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getExtension_Implements()
	 * @model
	 * @generated
	 */
	Java getImplements();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.Extension#getImplements <em>Implements</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Implements</em>' reference.
	 * @see #getImplements()
	 * @generated
	 */
	void setImplements(Java value);

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
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getExtension_Diagraph()
	 * @model
	 * @generated
	 */
	boolean isDiagraph();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.Extension#isDiagraph <em>Diagraph</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Diagraph</em>' attribute.
	 * @see #isDiagraph()
	 * @generated
	 */
	void setDiagraph(boolean value);

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
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getExtension_Id()
	 * @model
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.Extension#getId <em>Id</em>}' attribute.
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
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getExtension_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.Extension#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // Extension
