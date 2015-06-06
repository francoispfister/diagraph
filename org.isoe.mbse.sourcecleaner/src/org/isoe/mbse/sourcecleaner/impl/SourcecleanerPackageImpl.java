/**
 */
package org.isoe.mbse.sourcecleaner.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.isoe.mbse.sourcecleaner.Build;
import org.isoe.mbse.sourcecleaner.ClassPath;
import org.isoe.mbse.sourcecleaner.Configuration;
import org.isoe.mbse.sourcecleaner.Dependency;
import org.isoe.mbse.sourcecleaner.Export;
import org.isoe.mbse.sourcecleaner.Extension;
import org.isoe.mbse.sourcecleaner.ExtensionAttribute;
import org.isoe.mbse.sourcecleaner.ExtensionPoint;
import org.isoe.mbse.sourcecleaner.ExtensionReference;
import org.isoe.mbse.sourcecleaner.Java;
import org.isoe.mbse.sourcecleaner.LocatedElement;
import org.isoe.mbse.sourcecleaner.Manifest;
import org.isoe.mbse.sourcecleaner.Plugin;
import org.isoe.mbse.sourcecleaner.Project;
import org.isoe.mbse.sourcecleaner.Schema;
import org.isoe.mbse.sourcecleaner.Source;
import org.isoe.mbse.sourcecleaner.SourcecleanerFactory;
import org.isoe.mbse.sourcecleaner.SourcecleanerPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SourcecleanerPackageImpl extends EPackageImpl implements SourcecleanerPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass configurationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass projectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass locatedElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sourceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass manifestEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass extensionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass buildEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dependencyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass classPathEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass exportEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass extensionPointEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass extensionAttributeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass pluginEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass schemaEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass extensionReferenceEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private SourcecleanerPackageImpl() {
		super(eNS_URI, SourcecleanerFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link SourcecleanerPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static SourcecleanerPackage init() {
		if (isInited) return (SourcecleanerPackage)EPackage.Registry.INSTANCE.getEPackage(SourcecleanerPackage.eNS_URI);

		// Obtain or create and register package
		SourcecleanerPackageImpl theSourcecleanerPackage = (SourcecleanerPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof SourcecleanerPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new SourcecleanerPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theSourcecleanerPackage.createPackageContents();

		// Initialize created meta-data
		theSourcecleanerPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theSourcecleanerPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(SourcecleanerPackage.eNS_URI, theSourcecleanerPackage);
		return theSourcecleanerPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConfiguration() {
		return configurationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConfiguration_Projects() {
		return (EReference)configurationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConfiguration_Location() {
		return (EAttribute)configurationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConfiguration_Temp() {
		return (EAttribute)configurationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProject() {
		return projectEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProject_Id() {
		return (EAttribute)projectEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProject_Sources() {
		return (EReference)projectEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProject_Manifest() {
		return (EReference)projectEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProject_Build() {
		return (EReference)projectEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProject_Plugin() {
		return (EReference)projectEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProject_Schema() {
		return (EReference)projectEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProject_Workspace() {
		return (EAttribute)projectEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLocatedElement() {
		return locatedElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLocatedElement_AbsolutePath() {
		return (EAttribute)locatedElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLocatedElement_Name() {
		return (EAttribute)locatedElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSource() {
		return sourceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSource_Comment() {
		return (EAttribute)sourceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSource_Handled() {
		return (EAttribute)sourceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSource_Mark() {
		return (EAttribute)sourceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSource_Content() {
		return (EAttribute)sourceEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getJava() {
		return javaEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJava_Package() {
		return (EAttribute)javaEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getJava_Project() {
		return (EReference)javaEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getManifest() {
		return manifestEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getManifest_SymbolicName() {
		return (EAttribute)manifestEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getManifest_Singleton() {
		return (EAttribute)manifestEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getManifest_Vendor() {
		return (EAttribute)manifestEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getManifest_Version() {
		return (EAttribute)manifestEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getManifest_VersionId() {
		return (EAttribute)manifestEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getManifest_VersionQualifier() {
		return (EAttribute)manifestEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getManifest_Dependencies() {
		return (EReference)manifestEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getManifest_Lazy() {
		return (EAttribute)manifestEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getManifest_ExecutionEnvironment() {
		return (EAttribute)manifestEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getManifest_Diagraph() {
		return (EAttribute)manifestEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getManifest_Classpathes() {
		return (EReference)manifestEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getManifest_Exports() {
		return (EReference)manifestEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExtension() {
		return extensionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExtension_ExtensionPoint() {
		return (EReference)extensionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExtension_Clazz() {
		return (EAttribute)extensionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExtension_PointId() {
		return (EAttribute)extensionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExtension_Extra() {
		return (EAttribute)extensionEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExtension_Attributes() {
		return (EReference)extensionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExtension_Implements() {
		return (EReference)extensionEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExtension_Diagraph() {
		return (EAttribute)extensionEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExtension_Id() {
		return (EAttribute)extensionEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExtension_Name() {
		return (EAttribute)extensionEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBuild() {
		return buildEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDependency() {
		return dependencyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDependency_Name() {
		return (EAttribute)dependencyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDependency_Version() {
		return (EAttribute)dependencyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDependency_Dependency() {
		return (EReference)dependencyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDependency_Requerant() {
		return (EReference)dependencyEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDependency_Reexport() {
		return (EAttribute)dependencyEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDependency_Diagraph() {
		return (EAttribute)dependencyEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getClassPath() {
		return classPathEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getClassPath_Name() {
		return (EAttribute)classPathEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExport() {
		return exportEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExport_Name() {
		return (EAttribute)exportEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExtensionPoint() {
		return extensionPointEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExtensionPoint_Id() {
		return (EAttribute)extensionPointEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExtensionPoint_Name() {
		return (EAttribute)extensionPointEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExtensionPoint_Schema() {
		return (EAttribute)extensionPointEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExtensionPoint_Diagraph() {
		return (EAttribute)extensionPointEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExtensionPoint_Extensions() {
		return (EReference)extensionPointEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExtensionPoint_Plugin() {
		return (EReference)extensionPointEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExtensionAttribute() {
		return extensionAttributeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExtensionAttribute_Name() {
		return (EAttribute)extensionAttributeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExtensionAttribute_Value() {
		return (EAttribute)extensionAttributeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPlugin() {
		return pluginEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPlugin_Extensions() {
		return (EReference)pluginEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPlugin_ExtensionPoints() {
		return (EReference)pluginEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPlugin_Project() {
		return (EReference)pluginEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPlugin_Extra() {
		return (EAttribute)pluginEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSchema() {
		return schemaEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSchema_References() {
		return (EReference)schemaEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSchema_Project() {
		return (EReference)schemaEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSchema_ExtensionName() {
		return (EAttribute)schemaEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSchema_ExtensionId() {
		return (EAttribute)schemaEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSchema_PluginName() {
		return (EAttribute)schemaEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExtensionReference() {
		return extensionReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExtensionReference_Schema() {
		return (EReference)extensionReferenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExtensionReference_Javaclass() {
		return (EReference)extensionReferenceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExtensionReference_Name() {
		return (EAttribute)extensionReferenceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExtensionReference_Java() {
		return (EAttribute)extensionReferenceEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExtensionReference_Package() {
		return (EAttribute)extensionReferenceEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExtensionReference_Project() {
		return (EAttribute)extensionReferenceEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SourcecleanerFactory getSourcecleanerFactory() {
		return (SourcecleanerFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		configurationEClass = createEClass(CONFIGURATION);
		createEReference(configurationEClass, CONFIGURATION__PROJECTS);
		createEAttribute(configurationEClass, CONFIGURATION__LOCATION);
		createEAttribute(configurationEClass, CONFIGURATION__TEMP);

		projectEClass = createEClass(PROJECT);
		createEAttribute(projectEClass, PROJECT__ID);
		createEReference(projectEClass, PROJECT__SOURCES);
		createEReference(projectEClass, PROJECT__MANIFEST);
		createEReference(projectEClass, PROJECT__BUILD);
		createEReference(projectEClass, PROJECT__PLUGIN);
		createEReference(projectEClass, PROJECT__SCHEMA);
		createEAttribute(projectEClass, PROJECT__WORKSPACE);

		locatedElementEClass = createEClass(LOCATED_ELEMENT);
		createEAttribute(locatedElementEClass, LOCATED_ELEMENT__ABSOLUTE_PATH);
		createEAttribute(locatedElementEClass, LOCATED_ELEMENT__NAME);

		sourceEClass = createEClass(SOURCE);
		createEAttribute(sourceEClass, SOURCE__COMMENT);
		createEAttribute(sourceEClass, SOURCE__HANDLED);
		createEAttribute(sourceEClass, SOURCE__MARK);
		createEAttribute(sourceEClass, SOURCE__CONTENT);

		javaEClass = createEClass(JAVA);
		createEAttribute(javaEClass, JAVA__PACKAGE);
		createEReference(javaEClass, JAVA__PROJECT);

		manifestEClass = createEClass(MANIFEST);
		createEAttribute(manifestEClass, MANIFEST__SYMBOLIC_NAME);
		createEAttribute(manifestEClass, MANIFEST__SINGLETON);
		createEAttribute(manifestEClass, MANIFEST__VENDOR);
		createEAttribute(manifestEClass, MANIFEST__VERSION);
		createEAttribute(manifestEClass, MANIFEST__VERSION_ID);
		createEAttribute(manifestEClass, MANIFEST__VERSION_QUALIFIER);
		createEReference(manifestEClass, MANIFEST__DEPENDENCIES);
		createEAttribute(manifestEClass, MANIFEST__LAZY);
		createEAttribute(manifestEClass, MANIFEST__EXECUTION_ENVIRONMENT);
		createEAttribute(manifestEClass, MANIFEST__DIAGRAPH);
		createEReference(manifestEClass, MANIFEST__CLASSPATHES);
		createEReference(manifestEClass, MANIFEST__EXPORTS);

		extensionEClass = createEClass(EXTENSION);
		createEReference(extensionEClass, EXTENSION__EXTENSION_POINT);
		createEAttribute(extensionEClass, EXTENSION__POINT_ID);
		createEAttribute(extensionEClass, EXTENSION__CLAZZ);
		createEReference(extensionEClass, EXTENSION__ATTRIBUTES);
		createEReference(extensionEClass, EXTENSION__IMPLEMENTS);
		createEAttribute(extensionEClass, EXTENSION__ID);
		createEAttribute(extensionEClass, EXTENSION__NAME);
		createEAttribute(extensionEClass, EXTENSION__EXTRA);
		createEAttribute(extensionEClass, EXTENSION__DIAGRAPH);

		buildEClass = createEClass(BUILD);

		dependencyEClass = createEClass(DEPENDENCY);
		createEAttribute(dependencyEClass, DEPENDENCY__NAME);
		createEAttribute(dependencyEClass, DEPENDENCY__VERSION);
		createEReference(dependencyEClass, DEPENDENCY__DEPENDENCY);
		createEReference(dependencyEClass, DEPENDENCY__REQUERANT);
		createEAttribute(dependencyEClass, DEPENDENCY__REEXPORT);
		createEAttribute(dependencyEClass, DEPENDENCY__DIAGRAPH);

		classPathEClass = createEClass(CLASS_PATH);
		createEAttribute(classPathEClass, CLASS_PATH__NAME);

		exportEClass = createEClass(EXPORT);
		createEAttribute(exportEClass, EXPORT__NAME);

		extensionPointEClass = createEClass(EXTENSION_POINT);
		createEAttribute(extensionPointEClass, EXTENSION_POINT__ID);
		createEAttribute(extensionPointEClass, EXTENSION_POINT__NAME);
		createEAttribute(extensionPointEClass, EXTENSION_POINT__SCHEMA);
		createEAttribute(extensionPointEClass, EXTENSION_POINT__DIAGRAPH);
		createEReference(extensionPointEClass, EXTENSION_POINT__EXTENSIONS);
		createEReference(extensionPointEClass, EXTENSION_POINT__PLUGIN);

		extensionAttributeEClass = createEClass(EXTENSION_ATTRIBUTE);
		createEAttribute(extensionAttributeEClass, EXTENSION_ATTRIBUTE__NAME);
		createEAttribute(extensionAttributeEClass, EXTENSION_ATTRIBUTE__VALUE);

		pluginEClass = createEClass(PLUGIN);
		createEReference(pluginEClass, PLUGIN__EXTENSIONS);
		createEReference(pluginEClass, PLUGIN__EXTENSION_POINTS);
		createEReference(pluginEClass, PLUGIN__PROJECT);
		createEAttribute(pluginEClass, PLUGIN__EXTRA);

		schemaEClass = createEClass(SCHEMA);
		createEReference(schemaEClass, SCHEMA__REFERENCES);
		createEReference(schemaEClass, SCHEMA__PROJECT);
		createEAttribute(schemaEClass, SCHEMA__EXTENSION_NAME);
		createEAttribute(schemaEClass, SCHEMA__EXTENSION_ID);
		createEAttribute(schemaEClass, SCHEMA__PLUGIN_NAME);

		extensionReferenceEClass = createEClass(EXTENSION_REFERENCE);
		createEReference(extensionReferenceEClass, EXTENSION_REFERENCE__SCHEMA);
		createEReference(extensionReferenceEClass, EXTENSION_REFERENCE__JAVACLASS);
		createEAttribute(extensionReferenceEClass, EXTENSION_REFERENCE__NAME);
		createEAttribute(extensionReferenceEClass, EXTENSION_REFERENCE__JAVA);
		createEAttribute(extensionReferenceEClass, EXTENSION_REFERENCE__PACKAGE);
		createEAttribute(extensionReferenceEClass, EXTENSION_REFERENCE__PROJECT);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		projectEClass.getESuperTypes().add(this.getLocatedElement());
		sourceEClass.getESuperTypes().add(this.getLocatedElement());
		javaEClass.getESuperTypes().add(this.getSource());
		manifestEClass.getESuperTypes().add(this.getSource());
		buildEClass.getESuperTypes().add(this.getSource());
		pluginEClass.getESuperTypes().add(this.getSource());
		schemaEClass.getESuperTypes().add(this.getSource());

		// Initialize classes and features; add operations and parameters
		initEClass(configurationEClass, Configuration.class, "Configuration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getConfiguration_Projects(), this.getProject(), null, "projects", null, 0, -1, Configuration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConfiguration_Location(), ecorePackage.getEString(), "location", null, 0, 1, Configuration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConfiguration_Temp(), ecorePackage.getEString(), "temp", null, 0, 1, Configuration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(projectEClass, Project.class, "Project", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getProject_Id(), ecorePackage.getEInt(), "id", null, 0, 1, Project.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProject_Sources(), this.getJava(), this.getJava_Project(), "sources", null, 0, -1, Project.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProject_Manifest(), this.getManifest(), null, "manifest", null, 0, 1, Project.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProject_Build(), this.getBuild(), null, "build", null, 0, 1, Project.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProject_Plugin(), this.getPlugin(), this.getPlugin_Project(), "plugin", null, 0, 1, Project.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProject_Schema(), this.getSchema(), this.getSchema_Project(), "schema", null, 0, 1, Project.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProject_Workspace(), ecorePackage.getEString(), "workspace", null, 0, 1, Project.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(locatedElementEClass, LocatedElement.class, "LocatedElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLocatedElement_AbsolutePath(), ecorePackage.getEString(), "absolutePath", null, 0, 1, LocatedElement.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLocatedElement_Name(), ecorePackage.getEString(), "name", null, 0, 1, LocatedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(sourceEClass, Source.class, "Source", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSource_Comment(), ecorePackage.getEString(), "comment", null, 0, 1, Source.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSource_Handled(), ecorePackage.getEBoolean(), "handled", null, 0, 1, Source.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSource_Mark(), ecorePackage.getEBoolean(), "mark", null, 0, 1, Source.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSource_Content(), ecorePackage.getEString(), "content", null, 0, 1, Source.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(javaEClass, Java.class, "Java", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getJava_Package(), ecorePackage.getEString(), "package", null, 0, 1, Java.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getJava_Project(), this.getProject(), this.getProject_Sources(), "project", null, 0, 1, Java.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(manifestEClass, Manifest.class, "Manifest", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getManifest_SymbolicName(), ecorePackage.getEString(), "symbolicName", null, 0, 1, Manifest.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getManifest_Singleton(), ecorePackage.getEBoolean(), "singleton", null, 0, 1, Manifest.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getManifest_Vendor(), ecorePackage.getEString(), "vendor", null, 0, 1, Manifest.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getManifest_Version(), ecorePackage.getEString(), "version", null, 0, 1, Manifest.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getManifest_VersionId(), ecorePackage.getEString(), "versionId", null, 0, 1, Manifest.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getManifest_VersionQualifier(), ecorePackage.getEString(), "versionQualifier", null, 0, 1, Manifest.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getManifest_Dependencies(), this.getDependency(), this.getDependency_Requerant(), "dependencies", null, 0, -1, Manifest.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getManifest_Lazy(), ecorePackage.getEBoolean(), "lazy", null, 0, 1, Manifest.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getManifest_ExecutionEnvironment(), ecorePackage.getEString(), "executionEnvironment", null, 0, 1, Manifest.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getManifest_Diagraph(), ecorePackage.getEBoolean(), "diagraph", null, 0, 1, Manifest.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getManifest_Classpathes(), this.getClassPath(), null, "classpathes", null, 0, -1, Manifest.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getManifest_Exports(), this.getExport(), null, "exports", null, 0, -1, Manifest.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(extensionEClass, Extension.class, "Extension", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExtension_ExtensionPoint(), this.getExtensionPoint(), this.getExtensionPoint_Extensions(), "extensionPoint", null, 0, 1, Extension.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExtension_PointId(), ecorePackage.getEString(), "pointId", null, 0, 1, Extension.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExtension_Clazz(), ecorePackage.getEString(), "clazz", null, 0, 1, Extension.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getExtension_Attributes(), this.getExtensionAttribute(), null, "attributes", null, 0, -1, Extension.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getExtension_Implements(), this.getJava(), null, "implements", null, 0, 1, Extension.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExtension_Id(), ecorePackage.getEString(), "id", null, 0, 1, Extension.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExtension_Name(), ecorePackage.getEString(), "name", null, 0, 1, Extension.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExtension_Extra(), ecorePackage.getEString(), "extra", null, 0, 1, Extension.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExtension_Diagraph(), ecorePackage.getEBoolean(), "diagraph", null, 0, 1, Extension.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(buildEClass, Build.class, "Build", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(dependencyEClass, Dependency.class, "Dependency", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDependency_Name(), ecorePackage.getEString(), "name", null, 0, 1, Dependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDependency_Version(), ecorePackage.getEString(), "version", null, 0, 1, Dependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDependency_Dependency(), this.getManifest(), null, "dependency", null, 0, 1, Dependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDependency_Requerant(), this.getManifest(), this.getManifest_Dependencies(), "requerant", null, 0, 1, Dependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDependency_Reexport(), ecorePackage.getEBoolean(), "reexport", null, 0, 1, Dependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDependency_Diagraph(), ecorePackage.getEBoolean(), "diagraph", null, 0, 1, Dependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(classPathEClass, ClassPath.class, "ClassPath", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getClassPath_Name(), ecorePackage.getEString(), "name", null, 0, 1, ClassPath.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(exportEClass, Export.class, "Export", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getExport_Name(), ecorePackage.getEString(), "name", null, 0, 1, Export.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(extensionPointEClass, ExtensionPoint.class, "ExtensionPoint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getExtensionPoint_Id(), ecorePackage.getEString(), "id", null, 0, 1, ExtensionPoint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExtensionPoint_Name(), ecorePackage.getEString(), "name", null, 0, 1, ExtensionPoint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExtensionPoint_Schema(), ecorePackage.getEString(), "schema", null, 0, 1, ExtensionPoint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExtensionPoint_Diagraph(), ecorePackage.getEBoolean(), "diagraph", null, 0, 1, ExtensionPoint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getExtensionPoint_Extensions(), this.getExtension(), this.getExtension_ExtensionPoint(), "extensions", null, 0, -1, ExtensionPoint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getExtensionPoint_Plugin(), this.getPlugin(), this.getPlugin_ExtensionPoints(), "plugin", null, 0, 1, ExtensionPoint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(extensionAttributeEClass, ExtensionAttribute.class, "ExtensionAttribute", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getExtensionAttribute_Name(), ecorePackage.getEString(), "name", null, 0, 1, ExtensionAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExtensionAttribute_Value(), ecorePackage.getEString(), "value", null, 0, 1, ExtensionAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(pluginEClass, Plugin.class, "Plugin", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPlugin_Extensions(), this.getExtension(), null, "extensions", null, 0, -1, Plugin.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPlugin_ExtensionPoints(), this.getExtensionPoint(), this.getExtensionPoint_Plugin(), "extensionPoints", null, 0, -1, Plugin.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPlugin_Project(), this.getProject(), this.getProject_Plugin(), "project", null, 0, 1, Plugin.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPlugin_Extra(), ecorePackage.getEString(), "extra", null, 0, 1, Plugin.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(schemaEClass, Schema.class, "Schema", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSchema_References(), this.getExtensionReference(), this.getExtensionReference_Schema(), "references", null, 0, -1, Schema.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSchema_Project(), this.getProject(), this.getProject_Schema(), "project", null, 0, 1, Schema.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSchema_ExtensionName(), ecorePackage.getEString(), "extensionName", null, 0, 1, Schema.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSchema_ExtensionId(), ecorePackage.getEString(), "extensionId", null, 0, 1, Schema.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSchema_PluginName(), ecorePackage.getEString(), "pluginName", null, 0, 1, Schema.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(extensionReferenceEClass, ExtensionReference.class, "ExtensionReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExtensionReference_Schema(), this.getSchema(), this.getSchema_References(), "schema", null, 0, 1, ExtensionReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getExtensionReference_Javaclass(), this.getJava(), null, "javaclass", null, 0, 1, ExtensionReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExtensionReference_Name(), ecorePackage.getEString(), "name", null, 0, 1, ExtensionReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExtensionReference_Java(), ecorePackage.getEString(), "java", null, 0, 1, ExtensionReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExtensionReference_Package(), ecorePackage.getEString(), "package", null, 0, 1, ExtensionReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExtensionReference_Project(), ecorePackage.getEString(), "project", null, 0, 1, ExtensionReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// diagen
		createDiagenAnnotations();
		// diagraph
		createDiagraphAnnotations();
	}

	/**
	 * Initializes the annotations for <b>diagen</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createDiagenAnnotations() {
		String source = "diagen";	
		addAnnotation
		  (this, 
		   source, 
		   new String[] {
			 "knownas=simple world", null,
			 "origin=http://org.isoe.fr", null,
			 "leftparent=nil", "",
			 "rightparent=nil", "",
			 "requires=nil", "",
			 "context=startup", null
		   });
	}

	/**
	 * Initializes the annotations for <b>diagraph</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createDiagraphAnnotations() {
		String source = "diagraph";	
		addAnnotation
		  (configurationEClass, 
		   source, 
		   new String[] {
			 "node", null,
			 "pov", null
		   });	
		addAnnotation
		  (projectEClass, 
		   source, 
		   new String[] {
			 "node", null,
			 "nav:vwproject", null
		   });	
		addAnnotation
		  (locatedElementEClass, 
		   source, 
		   new String[] {
			 "label=name", null
		   });	
		addAnnotation
		  (sourceEClass, 
		   source, 
		   new String[] {
			 "node", null
		   });
	}

} //SourcecleanerPackageImpl
