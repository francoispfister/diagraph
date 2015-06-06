/**
 */
package org.isoe.mbse.sourcecleaner;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage
 * @generated
 */
public interface SourcecleanerFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SourcecleanerFactory eINSTANCE = org.isoe.mbse.sourcecleaner.impl.SourcecleanerFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Configuration</em>'.
	 * @generated
	 */
	Configuration createConfiguration();

	/**
	 * Returns a new object of class '<em>Project</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Project</em>'.
	 * @generated
	 */
	Project createProject();

	/**
	 * Returns a new object of class '<em>Java</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Java</em>'.
	 * @generated
	 */
	Java createJava();

	/**
	 * Returns a new object of class '<em>Manifest</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Manifest</em>'.
	 * @generated
	 */
	Manifest createManifest();

	/**
	 * Returns a new object of class '<em>Extension</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Extension</em>'.
	 * @generated
	 */
	Extension createExtension();

	/**
	 * Returns a new object of class '<em>Build</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Build</em>'.
	 * @generated
	 */
	Build createBuild();

	/**
	 * Returns a new object of class '<em>Dependency</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Dependency</em>'.
	 * @generated
	 */
	Dependency createDependency();

	/**
	 * Returns a new object of class '<em>Class Path</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Class Path</em>'.
	 * @generated
	 */
	ClassPath createClassPath();

	/**
	 * Returns a new object of class '<em>Export</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Export</em>'.
	 * @generated
	 */
	Export createExport();

	/**
	 * Returns a new object of class '<em>Extension Point</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Extension Point</em>'.
	 * @generated
	 */
	ExtensionPoint createExtensionPoint();

	/**
	 * Returns a new object of class '<em>Extension Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Extension Attribute</em>'.
	 * @generated
	 */
	ExtensionAttribute createExtensionAttribute();

	/**
	 * Returns a new object of class '<em>Plugin</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Plugin</em>'.
	 * @generated
	 */
	Plugin createPlugin();

	/**
	 * Returns a new object of class '<em>Schema</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Schema</em>'.
	 * @generated
	 */
	Schema createSchema();

	/**
	 * Returns a new object of class '<em>Extension Reference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Extension Reference</em>'.
	 * @generated
	 */
	ExtensionReference createExtensionReference();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	SourcecleanerPackage getSourcecleanerPackage();

} //SourcecleanerFactory
