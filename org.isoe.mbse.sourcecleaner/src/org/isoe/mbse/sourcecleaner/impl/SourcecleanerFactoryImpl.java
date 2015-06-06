/**
 */
package org.isoe.mbse.sourcecleaner.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.isoe.mbse.sourcecleaner.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SourcecleanerFactoryImpl extends EFactoryImpl implements SourcecleanerFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SourcecleanerFactory init() {
		try {
			SourcecleanerFactory theSourcecleanerFactory = (SourcecleanerFactory)EPackage.Registry.INSTANCE.getEFactory(SourcecleanerPackage.eNS_URI);
			if (theSourcecleanerFactory != null) {
				return theSourcecleanerFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new SourcecleanerFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SourcecleanerFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case SourcecleanerPackage.CONFIGURATION: return createConfiguration();
			case SourcecleanerPackage.PROJECT: return createProject();
			case SourcecleanerPackage.JAVA: return createJava();
			case SourcecleanerPackage.MANIFEST: return createManifest();
			case SourcecleanerPackage.EXTENSION: return createExtension();
			case SourcecleanerPackage.BUILD: return createBuild();
			case SourcecleanerPackage.DEPENDENCY: return createDependency();
			case SourcecleanerPackage.CLASS_PATH: return createClassPath();
			case SourcecleanerPackage.EXPORT: return createExport();
			case SourcecleanerPackage.EXTENSION_POINT: return createExtensionPoint();
			case SourcecleanerPackage.EXTENSION_ATTRIBUTE: return createExtensionAttribute();
			case SourcecleanerPackage.PLUGIN: return createPlugin();
			case SourcecleanerPackage.SCHEMA: return createSchema();
			case SourcecleanerPackage.EXTENSION_REFERENCE: return createExtensionReference();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Configuration createConfiguration() {
		ConfigurationImpl configuration = new ConfigurationImpl();
		return configuration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Project createProject() {
		ProjectImpl project = new ProjectImpl();
		return project;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Java createJava() {
		JavaImpl java = new JavaImpl();
		return java;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Manifest createManifest() {
		ManifestImpl manifest = new ManifestImpl();
		return manifest;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Extension createExtension() {
		ExtensionImpl extension = new ExtensionImpl();
		return extension;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Build createBuild() {
		BuildImpl build = new BuildImpl();
		return build;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Dependency createDependency() {
		DependencyImpl dependency = new DependencyImpl();
		return dependency;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ClassPath createClassPath() {
		ClassPathImpl classPath = new ClassPathImpl();
		return classPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Export createExport() {
		ExportImpl export = new ExportImpl();
		return export;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExtensionPoint createExtensionPoint() {
		ExtensionPointImpl extensionPoint = new ExtensionPointImpl();
		return extensionPoint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExtensionAttribute createExtensionAttribute() {
		ExtensionAttributeImpl extensionAttribute = new ExtensionAttributeImpl();
		return extensionAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Plugin createPlugin() {
		PluginImpl plugin = new PluginImpl();
		return plugin;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Schema createSchema() {
		SchemaImpl schema = new SchemaImpl();
		return schema;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExtensionReference createExtensionReference() {
		ExtensionReferenceImpl extensionReference = new ExtensionReferenceImpl();
		return extensionReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SourcecleanerPackage getSourcecleanerPackage() {
		return (SourcecleanerPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static SourcecleanerPackage getPackage() {
		return SourcecleanerPackage.eINSTANCE;
	}

} //SourcecleanerFactoryImpl
