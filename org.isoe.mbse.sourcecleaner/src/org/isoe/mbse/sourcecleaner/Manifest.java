/**
 */
package org.isoe.mbse.sourcecleaner;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Manifest</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Manifest#getSymbolicName <em>Symbolic Name</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Manifest#isSingleton <em>Singleton</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Manifest#getVendor <em>Vendor</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Manifest#getVersion <em>Version</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Manifest#getVersionId <em>Version Id</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Manifest#getVersionQualifier <em>Version Qualifier</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Manifest#getDependencies <em>Dependencies</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Manifest#isLazy <em>Lazy</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Manifest#getExecutionEnvironment <em>Execution Environment</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Manifest#isDiagraph <em>Diagraph</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Manifest#getClasspathes <em>Classpathes</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Manifest#getExports <em>Exports</em>}</li>
 * </ul>
 *
 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getManifest()
 * @model
 * @generated
 */
public interface Manifest extends Source {
	/**
	 * Returns the value of the '<em><b>Symbolic Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Symbolic Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Symbolic Name</em>' attribute.
	 * @see #setSymbolicName(String)
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getManifest_SymbolicName()
	 * @model
	 * @generated
	 */
	String getSymbolicName();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.Manifest#getSymbolicName <em>Symbolic Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Symbolic Name</em>' attribute.
	 * @see #getSymbolicName()
	 * @generated
	 */
	void setSymbolicName(String value);

	/**
	 * Returns the value of the '<em><b>Singleton</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Singleton</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Singleton</em>' attribute.
	 * @see #setSingleton(boolean)
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getManifest_Singleton()
	 * @model
	 * @generated
	 */
	boolean isSingleton();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.Manifest#isSingleton <em>Singleton</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Singleton</em>' attribute.
	 * @see #isSingleton()
	 * @generated
	 */
	void setSingleton(boolean value);

	/**
	 * Returns the value of the '<em><b>Vendor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Vendor</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Vendor</em>' attribute.
	 * @see #setVendor(String)
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getManifest_Vendor()
	 * @model
	 * @generated
	 */
	String getVendor();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.Manifest#getVendor <em>Vendor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Vendor</em>' attribute.
	 * @see #getVendor()
	 * @generated
	 */
	void setVendor(String value);

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
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getManifest_Version()
	 * @model
	 * @generated
	 */
	String getVersion();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.Manifest#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(String value);

	/**
	 * Returns the value of the '<em><b>Version Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Version Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version Id</em>' attribute.
	 * @see #setVersionId(String)
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getManifest_VersionId()
	 * @model
	 * @generated
	 */
	String getVersionId();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.Manifest#getVersionId <em>Version Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version Id</em>' attribute.
	 * @see #getVersionId()
	 * @generated
	 */
	void setVersionId(String value);

	/**
	 * Returns the value of the '<em><b>Version Qualifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Version Qualifier</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version Qualifier</em>' attribute.
	 * @see #setVersionQualifier(String)
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getManifest_VersionQualifier()
	 * @model
	 * @generated
	 */
	String getVersionQualifier();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.Manifest#getVersionQualifier <em>Version Qualifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version Qualifier</em>' attribute.
	 * @see #getVersionQualifier()
	 * @generated
	 */
	void setVersionQualifier(String value);

	/**
	 * Returns the value of the '<em><b>Dependencies</b></em>' containment reference list.
	 * The list contents are of type {@link org.isoe.mbse.sourcecleaner.Dependency}.
	 * It is bidirectional and its opposite is '{@link org.isoe.mbse.sourcecleaner.Dependency#getRequerant <em>Requerant</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dependencies</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dependencies</em>' containment reference list.
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getManifest_Dependencies()
	 * @see org.isoe.mbse.sourcecleaner.Dependency#getRequerant
	 * @model opposite="requerant" containment="true"
	 * @generated
	 */
	EList<Dependency> getDependencies();

	/**
	 * Returns the value of the '<em><b>Lazy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lazy</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lazy</em>' attribute.
	 * @see #setLazy(boolean)
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getManifest_Lazy()
	 * @model
	 * @generated
	 */
	boolean isLazy();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.Manifest#isLazy <em>Lazy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lazy</em>' attribute.
	 * @see #isLazy()
	 * @generated
	 */
	void setLazy(boolean value);

	/**
	 * Returns the value of the '<em><b>Execution Environment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Execution Environment</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Execution Environment</em>' attribute.
	 * @see #setExecutionEnvironment(String)
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getManifest_ExecutionEnvironment()
	 * @model
	 * @generated
	 */
	String getExecutionEnvironment();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.Manifest#getExecutionEnvironment <em>Execution Environment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Execution Environment</em>' attribute.
	 * @see #getExecutionEnvironment()
	 * @generated
	 */
	void setExecutionEnvironment(String value);

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
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getManifest_Diagraph()
	 * @model
	 * @generated
	 */
	boolean isDiagraph();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.Manifest#isDiagraph <em>Diagraph</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Diagraph</em>' attribute.
	 * @see #isDiagraph()
	 * @generated
	 */
	void setDiagraph(boolean value);

	/**
	 * Returns the value of the '<em><b>Classpathes</b></em>' containment reference list.
	 * The list contents are of type {@link org.isoe.mbse.sourcecleaner.ClassPath}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Classpathes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Classpathes</em>' containment reference list.
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getManifest_Classpathes()
	 * @model containment="true"
	 * @generated
	 */
	EList<ClassPath> getClasspathes();

	/**
	 * Returns the value of the '<em><b>Exports</b></em>' containment reference list.
	 * The list contents are of type {@link org.isoe.mbse.sourcecleaner.Export}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exports</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exports</em>' containment reference list.
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getManifest_Exports()
	 * @model containment="true"
	 * @generated
	 */
	EList<Export> getExports();

} // Manifest
