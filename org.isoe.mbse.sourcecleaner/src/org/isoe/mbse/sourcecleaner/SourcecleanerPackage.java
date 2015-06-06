/**
 */
package org.isoe.mbse.sourcecleaner;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.isoe.mbse.sourcecleaner.SourcecleanerFactory
 * @model kind="package"
 *        annotation="diagen knownas\075simple\040world='null' origin\075http://org.isoe.fr='null' leftparent\075nil='' rightparent\075nil='' requires\075nil='' context\075startup='null'"
 * @generated
 */
public interface SourcecleanerPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "sourcecleaner";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://sourcecleaner";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "_sourcecleaner";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SourcecleanerPackage eINSTANCE = org.isoe.mbse.sourcecleaner.impl.SourcecleanerPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.isoe.mbse.sourcecleaner.impl.ConfigurationImpl <em>Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.mbse.sourcecleaner.impl.ConfigurationImpl
	 * @see org.isoe.mbse.sourcecleaner.impl.SourcecleanerPackageImpl#getConfiguration()
	 * @generated
	 */
	int CONFIGURATION = 0;

	/**
	 * The feature id for the '<em><b>Projects</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION__PROJECTS = 0;

	/**
	 * The feature id for the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION__LOCATION = 1;

	/**
	 * The feature id for the '<em><b>Temp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION__TEMP = 2;

	/**
	 * The number of structural features of the '<em>Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.isoe.mbse.sourcecleaner.impl.LocatedElementImpl <em>Located Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.mbse.sourcecleaner.impl.LocatedElementImpl
	 * @see org.isoe.mbse.sourcecleaner.impl.SourcecleanerPackageImpl#getLocatedElement()
	 * @generated
	 */
	int LOCATED_ELEMENT = 2;

	/**
	 * The feature id for the '<em><b>Absolute Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATED_ELEMENT__ABSOLUTE_PATH = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATED_ELEMENT__NAME = 1;

	/**
	 * The number of structural features of the '<em>Located Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATED_ELEMENT_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.isoe.mbse.sourcecleaner.impl.ProjectImpl <em>Project</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.mbse.sourcecleaner.impl.ProjectImpl
	 * @see org.isoe.mbse.sourcecleaner.impl.SourcecleanerPackageImpl#getProject()
	 * @generated
	 */
	int PROJECT = 1;

	/**
	 * The feature id for the '<em><b>Absolute Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT__ABSOLUTE_PATH = LOCATED_ELEMENT__ABSOLUTE_PATH;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT__NAME = LOCATED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT__ID = LOCATED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Sources</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT__SOURCES = LOCATED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Manifest</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT__MANIFEST = LOCATED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Build</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT__BUILD = LOCATED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Plugin</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT__PLUGIN = LOCATED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT__SCHEMA = LOCATED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Workspace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT__WORKSPACE = LOCATED_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Project</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_FEATURE_COUNT = LOCATED_ELEMENT_FEATURE_COUNT + 7;

	/**
	 * The meta object id for the '{@link org.isoe.mbse.sourcecleaner.impl.SourceImpl <em>Source</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.mbse.sourcecleaner.impl.SourceImpl
	 * @see org.isoe.mbse.sourcecleaner.impl.SourcecleanerPackageImpl#getSource()
	 * @generated
	 */
	int SOURCE = 3;

	/**
	 * The feature id for the '<em><b>Absolute Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE__ABSOLUTE_PATH = LOCATED_ELEMENT__ABSOLUTE_PATH;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE__NAME = LOCATED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE__COMMENT = LOCATED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Handled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE__HANDLED = LOCATED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Mark</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE__MARK = LOCATED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Content</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE__CONTENT = LOCATED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Source</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_FEATURE_COUNT = LOCATED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.isoe.mbse.sourcecleaner.impl.JavaImpl <em>Java</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.mbse.sourcecleaner.impl.JavaImpl
	 * @see org.isoe.mbse.sourcecleaner.impl.SourcecleanerPackageImpl#getJava()
	 * @generated
	 */
	int JAVA = 4;

	/**
	 * The feature id for the '<em><b>Absolute Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA__ABSOLUTE_PATH = SOURCE__ABSOLUTE_PATH;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA__NAME = SOURCE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA__COMMENT = SOURCE__COMMENT;

	/**
	 * The feature id for the '<em><b>Handled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA__HANDLED = SOURCE__HANDLED;

	/**
	 * The feature id for the '<em><b>Mark</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA__MARK = SOURCE__MARK;

	/**
	 * The feature id for the '<em><b>Content</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA__CONTENT = SOURCE__CONTENT;

	/**
	 * The feature id for the '<em><b>Package</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA__PACKAGE = SOURCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Project</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA__PROJECT = SOURCE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Java</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_FEATURE_COUNT = SOURCE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.isoe.mbse.sourcecleaner.impl.ManifestImpl <em>Manifest</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.mbse.sourcecleaner.impl.ManifestImpl
	 * @see org.isoe.mbse.sourcecleaner.impl.SourcecleanerPackageImpl#getManifest()
	 * @generated
	 */
	int MANIFEST = 5;

	/**
	 * The feature id for the '<em><b>Absolute Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANIFEST__ABSOLUTE_PATH = SOURCE__ABSOLUTE_PATH;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANIFEST__NAME = SOURCE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANIFEST__COMMENT = SOURCE__COMMENT;

	/**
	 * The feature id for the '<em><b>Handled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANIFEST__HANDLED = SOURCE__HANDLED;

	/**
	 * The feature id for the '<em><b>Mark</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANIFEST__MARK = SOURCE__MARK;

	/**
	 * The feature id for the '<em><b>Content</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANIFEST__CONTENT = SOURCE__CONTENT;

	/**
	 * The feature id for the '<em><b>Symbolic Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANIFEST__SYMBOLIC_NAME = SOURCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Singleton</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANIFEST__SINGLETON = SOURCE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Vendor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANIFEST__VENDOR = SOURCE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANIFEST__VERSION = SOURCE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Version Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANIFEST__VERSION_ID = SOURCE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Version Qualifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANIFEST__VERSION_QUALIFIER = SOURCE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Dependencies</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANIFEST__DEPENDENCIES = SOURCE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Lazy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANIFEST__LAZY = SOURCE_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Execution Environment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANIFEST__EXECUTION_ENVIRONMENT = SOURCE_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Diagraph</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANIFEST__DIAGRAPH = SOURCE_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Classpathes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANIFEST__CLASSPATHES = SOURCE_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Exports</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANIFEST__EXPORTS = SOURCE_FEATURE_COUNT + 11;

	/**
	 * The number of structural features of the '<em>Manifest</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANIFEST_FEATURE_COUNT = SOURCE_FEATURE_COUNT + 12;

	/**
	 * The meta object id for the '{@link org.isoe.mbse.sourcecleaner.impl.ExtensionImpl <em>Extension</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.mbse.sourcecleaner.impl.ExtensionImpl
	 * @see org.isoe.mbse.sourcecleaner.impl.SourcecleanerPackageImpl#getExtension()
	 * @generated
	 */
	int EXTENSION = 6;

	/**
	 * The feature id for the '<em><b>Extension Point</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENSION__EXTENSION_POINT = 0;

	/**
	 * The feature id for the '<em><b>Point Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENSION__POINT_ID = 1;

	/**
	 * The feature id for the '<em><b>Clazz</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENSION__CLAZZ = 2;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENSION__ATTRIBUTES = 3;

	/**
	 * The feature id for the '<em><b>Implements</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENSION__IMPLEMENTS = 4;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENSION__ID = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENSION__NAME = 6;

	/**
	 * The feature id for the '<em><b>Extra</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENSION__EXTRA = 7;

	/**
	 * The feature id for the '<em><b>Diagraph</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENSION__DIAGRAPH = 8;

	/**
	 * The number of structural features of the '<em>Extension</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENSION_FEATURE_COUNT = 9;

	/**
	 * The meta object id for the '{@link org.isoe.mbse.sourcecleaner.impl.BuildImpl <em>Build</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.mbse.sourcecleaner.impl.BuildImpl
	 * @see org.isoe.mbse.sourcecleaner.impl.SourcecleanerPackageImpl#getBuild()
	 * @generated
	 */
	int BUILD = 7;

	/**
	 * The feature id for the '<em><b>Absolute Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUILD__ABSOLUTE_PATH = SOURCE__ABSOLUTE_PATH;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUILD__NAME = SOURCE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUILD__COMMENT = SOURCE__COMMENT;

	/**
	 * The feature id for the '<em><b>Handled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUILD__HANDLED = SOURCE__HANDLED;

	/**
	 * The feature id for the '<em><b>Mark</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUILD__MARK = SOURCE__MARK;

	/**
	 * The feature id for the '<em><b>Content</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUILD__CONTENT = SOURCE__CONTENT;

	/**
	 * The number of structural features of the '<em>Build</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUILD_FEATURE_COUNT = SOURCE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.isoe.mbse.sourcecleaner.impl.DependencyImpl <em>Dependency</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.mbse.sourcecleaner.impl.DependencyImpl
	 * @see org.isoe.mbse.sourcecleaner.impl.SourcecleanerPackageImpl#getDependency()
	 * @generated
	 */
	int DEPENDENCY = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPENDENCY__NAME = 0;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPENDENCY__VERSION = 1;

	/**
	 * The feature id for the '<em><b>Dependency</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPENDENCY__DEPENDENCY = 2;

	/**
	 * The feature id for the '<em><b>Requerant</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPENDENCY__REQUERANT = 3;

	/**
	 * The feature id for the '<em><b>Reexport</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPENDENCY__REEXPORT = 4;

	/**
	 * The feature id for the '<em><b>Diagraph</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPENDENCY__DIAGRAPH = 5;

	/**
	 * The number of structural features of the '<em>Dependency</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPENDENCY_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link org.isoe.mbse.sourcecleaner.impl.ClassPathImpl <em>Class Path</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.mbse.sourcecleaner.impl.ClassPathImpl
	 * @see org.isoe.mbse.sourcecleaner.impl.SourcecleanerPackageImpl#getClassPath()
	 * @generated
	 */
	int CLASS_PATH = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_PATH__NAME = 0;

	/**
	 * The number of structural features of the '<em>Class Path</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_PATH_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.isoe.mbse.sourcecleaner.impl.ExportImpl <em>Export</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.mbse.sourcecleaner.impl.ExportImpl
	 * @see org.isoe.mbse.sourcecleaner.impl.SourcecleanerPackageImpl#getExport()
	 * @generated
	 */
	int EXPORT = 10;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORT__NAME = 0;

	/**
	 * The number of structural features of the '<em>Export</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORT_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.isoe.mbse.sourcecleaner.impl.ExtensionPointImpl <em>Extension Point</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.mbse.sourcecleaner.impl.ExtensionPointImpl
	 * @see org.isoe.mbse.sourcecleaner.impl.SourcecleanerPackageImpl#getExtensionPoint()
	 * @generated
	 */
	int EXTENSION_POINT = 11;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENSION_POINT__ID = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENSION_POINT__NAME = 1;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENSION_POINT__SCHEMA = 2;

	/**
	 * The feature id for the '<em><b>Diagraph</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENSION_POINT__DIAGRAPH = 3;

	/**
	 * The feature id for the '<em><b>Extensions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENSION_POINT__EXTENSIONS = 4;

	/**
	 * The feature id for the '<em><b>Plugin</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENSION_POINT__PLUGIN = 5;

	/**
	 * The number of structural features of the '<em>Extension Point</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENSION_POINT_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link org.isoe.mbse.sourcecleaner.impl.ExtensionAttributeImpl <em>Extension Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.mbse.sourcecleaner.impl.ExtensionAttributeImpl
	 * @see org.isoe.mbse.sourcecleaner.impl.SourcecleanerPackageImpl#getExtensionAttribute()
	 * @generated
	 */
	int EXTENSION_ATTRIBUTE = 12;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENSION_ATTRIBUTE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENSION_ATTRIBUTE__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Extension Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENSION_ATTRIBUTE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.isoe.mbse.sourcecleaner.impl.PluginImpl <em>Plugin</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.mbse.sourcecleaner.impl.PluginImpl
	 * @see org.isoe.mbse.sourcecleaner.impl.SourcecleanerPackageImpl#getPlugin()
	 * @generated
	 */
	int PLUGIN = 13;

	/**
	 * The feature id for the '<em><b>Absolute Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLUGIN__ABSOLUTE_PATH = SOURCE__ABSOLUTE_PATH;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLUGIN__NAME = SOURCE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLUGIN__COMMENT = SOURCE__COMMENT;

	/**
	 * The feature id for the '<em><b>Handled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLUGIN__HANDLED = SOURCE__HANDLED;

	/**
	 * The feature id for the '<em><b>Mark</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLUGIN__MARK = SOURCE__MARK;

	/**
	 * The feature id for the '<em><b>Content</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLUGIN__CONTENT = SOURCE__CONTENT;

	/**
	 * The feature id for the '<em><b>Extensions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLUGIN__EXTENSIONS = SOURCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Extension Points</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLUGIN__EXTENSION_POINTS = SOURCE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Project</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLUGIN__PROJECT = SOURCE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Extra</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLUGIN__EXTRA = SOURCE_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Plugin</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLUGIN_FEATURE_COUNT = SOURCE_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.isoe.mbse.sourcecleaner.impl.SchemaImpl <em>Schema</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.mbse.sourcecleaner.impl.SchemaImpl
	 * @see org.isoe.mbse.sourcecleaner.impl.SourcecleanerPackageImpl#getSchema()
	 * @generated
	 */
	int SCHEMA = 14;

	/**
	 * The feature id for the '<em><b>Absolute Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEMA__ABSOLUTE_PATH = SOURCE__ABSOLUTE_PATH;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEMA__NAME = SOURCE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEMA__COMMENT = SOURCE__COMMENT;

	/**
	 * The feature id for the '<em><b>Handled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEMA__HANDLED = SOURCE__HANDLED;

	/**
	 * The feature id for the '<em><b>Mark</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEMA__MARK = SOURCE__MARK;

	/**
	 * The feature id for the '<em><b>Content</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEMA__CONTENT = SOURCE__CONTENT;

	/**
	 * The feature id for the '<em><b>References</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEMA__REFERENCES = SOURCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Project</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEMA__PROJECT = SOURCE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Extension Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEMA__EXTENSION_NAME = SOURCE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Extension Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEMA__EXTENSION_ID = SOURCE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Plugin Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEMA__PLUGIN_NAME = SOURCE_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Schema</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEMA_FEATURE_COUNT = SOURCE_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.isoe.mbse.sourcecleaner.impl.ExtensionReferenceImpl <em>Extension Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.isoe.mbse.sourcecleaner.impl.ExtensionReferenceImpl
	 * @see org.isoe.mbse.sourcecleaner.impl.SourcecleanerPackageImpl#getExtensionReference()
	 * @generated
	 */
	int EXTENSION_REFERENCE = 15;

	/**
	 * The feature id for the '<em><b>Schema</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENSION_REFERENCE__SCHEMA = 0;

	/**
	 * The feature id for the '<em><b>Javaclass</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENSION_REFERENCE__JAVACLASS = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENSION_REFERENCE__NAME = 2;

	/**
	 * The feature id for the '<em><b>Java</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENSION_REFERENCE__JAVA = 3;

	/**
	 * The feature id for the '<em><b>Package</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENSION_REFERENCE__PACKAGE = 4;

	/**
	 * The feature id for the '<em><b>Project</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENSION_REFERENCE__PROJECT = 5;

	/**
	 * The number of structural features of the '<em>Extension Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENSION_REFERENCE_FEATURE_COUNT = 6;


	/**
	 * Returns the meta object for class '{@link org.isoe.mbse.sourcecleaner.Configuration <em>Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Configuration</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Configuration
	 * @generated
	 */
	EClass getConfiguration();

	/**
	 * Returns the meta object for the containment reference list '{@link org.isoe.mbse.sourcecleaner.Configuration#getProjects <em>Projects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Projects</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Configuration#getProjects()
	 * @see #getConfiguration()
	 * @generated
	 */
	EReference getConfiguration_Projects();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.mbse.sourcecleaner.Configuration#getLocation <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Location</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Configuration#getLocation()
	 * @see #getConfiguration()
	 * @generated
	 */
	EAttribute getConfiguration_Location();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.mbse.sourcecleaner.Configuration#getTemp <em>Temp</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Temp</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Configuration#getTemp()
	 * @see #getConfiguration()
	 * @generated
	 */
	EAttribute getConfiguration_Temp();

	/**
	 * Returns the meta object for class '{@link org.isoe.mbse.sourcecleaner.Project <em>Project</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Project</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Project
	 * @generated
	 */
	EClass getProject();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.mbse.sourcecleaner.Project#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Project#getId()
	 * @see #getProject()
	 * @generated
	 */
	EAttribute getProject_Id();

	/**
	 * Returns the meta object for the containment reference list '{@link org.isoe.mbse.sourcecleaner.Project#getSources <em>Sources</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sources</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Project#getSources()
	 * @see #getProject()
	 * @generated
	 */
	EReference getProject_Sources();

	/**
	 * Returns the meta object for the containment reference '{@link org.isoe.mbse.sourcecleaner.Project#getManifest <em>Manifest</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Manifest</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Project#getManifest()
	 * @see #getProject()
	 * @generated
	 */
	EReference getProject_Manifest();

	/**
	 * Returns the meta object for the containment reference '{@link org.isoe.mbse.sourcecleaner.Project#getBuild <em>Build</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Build</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Project#getBuild()
	 * @see #getProject()
	 * @generated
	 */
	EReference getProject_Build();

	/**
	 * Returns the meta object for the containment reference '{@link org.isoe.mbse.sourcecleaner.Project#getPlugin <em>Plugin</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Plugin</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Project#getPlugin()
	 * @see #getProject()
	 * @generated
	 */
	EReference getProject_Plugin();

	/**
	 * Returns the meta object for the containment reference '{@link org.isoe.mbse.sourcecleaner.Project#getSchema <em>Schema</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Schema</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Project#getSchema()
	 * @see #getProject()
	 * @generated
	 */
	EReference getProject_Schema();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.mbse.sourcecleaner.Project#getWorkspace <em>Workspace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Workspace</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Project#getWorkspace()
	 * @see #getProject()
	 * @generated
	 */
	EAttribute getProject_Workspace();

	/**
	 * Returns the meta object for class '{@link org.isoe.mbse.sourcecleaner.LocatedElement <em>Located Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Located Element</em>'.
	 * @see org.isoe.mbse.sourcecleaner.LocatedElement
	 * @generated
	 */
	EClass getLocatedElement();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.mbse.sourcecleaner.LocatedElement#getAbsolutePath <em>Absolute Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Absolute Path</em>'.
	 * @see org.isoe.mbse.sourcecleaner.LocatedElement#getAbsolutePath()
	 * @see #getLocatedElement()
	 * @generated
	 */
	EAttribute getLocatedElement_AbsolutePath();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.mbse.sourcecleaner.LocatedElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.isoe.mbse.sourcecleaner.LocatedElement#getName()
	 * @see #getLocatedElement()
	 * @generated
	 */
	EAttribute getLocatedElement_Name();

	/**
	 * Returns the meta object for class '{@link org.isoe.mbse.sourcecleaner.Source <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Source</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Source
	 * @generated
	 */
	EClass getSource();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.mbse.sourcecleaner.Source#getComment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comment</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Source#getComment()
	 * @see #getSource()
	 * @generated
	 */
	EAttribute getSource_Comment();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.mbse.sourcecleaner.Source#isHandled <em>Handled</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Handled</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Source#isHandled()
	 * @see #getSource()
	 * @generated
	 */
	EAttribute getSource_Handled();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.mbse.sourcecleaner.Source#isMark <em>Mark</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mark</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Source#isMark()
	 * @see #getSource()
	 * @generated
	 */
	EAttribute getSource_Mark();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.mbse.sourcecleaner.Source#getContent <em>Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Content</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Source#getContent()
	 * @see #getSource()
	 * @generated
	 */
	EAttribute getSource_Content();

	/**
	 * Returns the meta object for class '{@link org.isoe.mbse.sourcecleaner.Java <em>Java</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Java
	 * @generated
	 */
	EClass getJava();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.mbse.sourcecleaner.Java#getPackage <em>Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Package</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Java#getPackage()
	 * @see #getJava()
	 * @generated
	 */
	EAttribute getJava_Package();

	/**
	 * Returns the meta object for the container reference '{@link org.isoe.mbse.sourcecleaner.Java#getProject <em>Project</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Project</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Java#getProject()
	 * @see #getJava()
	 * @generated
	 */
	EReference getJava_Project();

	/**
	 * Returns the meta object for class '{@link org.isoe.mbse.sourcecleaner.Manifest <em>Manifest</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Manifest</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Manifest
	 * @generated
	 */
	EClass getManifest();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.mbse.sourcecleaner.Manifest#getSymbolicName <em>Symbolic Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Symbolic Name</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Manifest#getSymbolicName()
	 * @see #getManifest()
	 * @generated
	 */
	EAttribute getManifest_SymbolicName();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.mbse.sourcecleaner.Manifest#isSingleton <em>Singleton</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Singleton</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Manifest#isSingleton()
	 * @see #getManifest()
	 * @generated
	 */
	EAttribute getManifest_Singleton();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.mbse.sourcecleaner.Manifest#getVendor <em>Vendor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Vendor</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Manifest#getVendor()
	 * @see #getManifest()
	 * @generated
	 */
	EAttribute getManifest_Vendor();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.mbse.sourcecleaner.Manifest#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Manifest#getVersion()
	 * @see #getManifest()
	 * @generated
	 */
	EAttribute getManifest_Version();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.mbse.sourcecleaner.Manifest#getVersionId <em>Version Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version Id</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Manifest#getVersionId()
	 * @see #getManifest()
	 * @generated
	 */
	EAttribute getManifest_VersionId();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.mbse.sourcecleaner.Manifest#getVersionQualifier <em>Version Qualifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version Qualifier</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Manifest#getVersionQualifier()
	 * @see #getManifest()
	 * @generated
	 */
	EAttribute getManifest_VersionQualifier();

	/**
	 * Returns the meta object for the containment reference list '{@link org.isoe.mbse.sourcecleaner.Manifest#getDependencies <em>Dependencies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Dependencies</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Manifest#getDependencies()
	 * @see #getManifest()
	 * @generated
	 */
	EReference getManifest_Dependencies();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.mbse.sourcecleaner.Manifest#isLazy <em>Lazy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Lazy</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Manifest#isLazy()
	 * @see #getManifest()
	 * @generated
	 */
	EAttribute getManifest_Lazy();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.mbse.sourcecleaner.Manifest#getExecutionEnvironment <em>Execution Environment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Execution Environment</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Manifest#getExecutionEnvironment()
	 * @see #getManifest()
	 * @generated
	 */
	EAttribute getManifest_ExecutionEnvironment();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.mbse.sourcecleaner.Manifest#isDiagraph <em>Diagraph</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Diagraph</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Manifest#isDiagraph()
	 * @see #getManifest()
	 * @generated
	 */
	EAttribute getManifest_Diagraph();

	/**
	 * Returns the meta object for the containment reference list '{@link org.isoe.mbse.sourcecleaner.Manifest#getClasspathes <em>Classpathes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Classpathes</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Manifest#getClasspathes()
	 * @see #getManifest()
	 * @generated
	 */
	EReference getManifest_Classpathes();

	/**
	 * Returns the meta object for the containment reference list '{@link org.isoe.mbse.sourcecleaner.Manifest#getExports <em>Exports</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Exports</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Manifest#getExports()
	 * @see #getManifest()
	 * @generated
	 */
	EReference getManifest_Exports();

	/**
	 * Returns the meta object for class '{@link org.isoe.mbse.sourcecleaner.Extension <em>Extension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Extension</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Extension
	 * @generated
	 */
	EClass getExtension();

	/**
	 * Returns the meta object for the reference '{@link org.isoe.mbse.sourcecleaner.Extension#getExtensionPoint <em>Extension Point</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Extension Point</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Extension#getExtensionPoint()
	 * @see #getExtension()
	 * @generated
	 */
	EReference getExtension_ExtensionPoint();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.mbse.sourcecleaner.Extension#getClazz <em>Clazz</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Clazz</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Extension#getClazz()
	 * @see #getExtension()
	 * @generated
	 */
	EAttribute getExtension_Clazz();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.mbse.sourcecleaner.Extension#getPointId <em>Point Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Point Id</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Extension#getPointId()
	 * @see #getExtension()
	 * @generated
	 */
	EAttribute getExtension_PointId();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.mbse.sourcecleaner.Extension#getExtra <em>Extra</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Extra</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Extension#getExtra()
	 * @see #getExtension()
	 * @generated
	 */
	EAttribute getExtension_Extra();

	/**
	 * Returns the meta object for the containment reference list '{@link org.isoe.mbse.sourcecleaner.Extension#getAttributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attributes</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Extension#getAttributes()
	 * @see #getExtension()
	 * @generated
	 */
	EReference getExtension_Attributes();

	/**
	 * Returns the meta object for the reference '{@link org.isoe.mbse.sourcecleaner.Extension#getImplements <em>Implements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Implements</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Extension#getImplements()
	 * @see #getExtension()
	 * @generated
	 */
	EReference getExtension_Implements();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.mbse.sourcecleaner.Extension#isDiagraph <em>Diagraph</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Diagraph</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Extension#isDiagraph()
	 * @see #getExtension()
	 * @generated
	 */
	EAttribute getExtension_Diagraph();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.mbse.sourcecleaner.Extension#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Extension#getId()
	 * @see #getExtension()
	 * @generated
	 */
	EAttribute getExtension_Id();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.mbse.sourcecleaner.Extension#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Extension#getName()
	 * @see #getExtension()
	 * @generated
	 */
	EAttribute getExtension_Name();

	/**
	 * Returns the meta object for class '{@link org.isoe.mbse.sourcecleaner.Build <em>Build</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Build</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Build
	 * @generated
	 */
	EClass getBuild();

	/**
	 * Returns the meta object for class '{@link org.isoe.mbse.sourcecleaner.Dependency <em>Dependency</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dependency</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Dependency
	 * @generated
	 */
	EClass getDependency();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.mbse.sourcecleaner.Dependency#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Dependency#getName()
	 * @see #getDependency()
	 * @generated
	 */
	EAttribute getDependency_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.mbse.sourcecleaner.Dependency#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Dependency#getVersion()
	 * @see #getDependency()
	 * @generated
	 */
	EAttribute getDependency_Version();

	/**
	 * Returns the meta object for the reference '{@link org.isoe.mbse.sourcecleaner.Dependency#getDependency <em>Dependency</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Dependency</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Dependency#getDependency()
	 * @see #getDependency()
	 * @generated
	 */
	EReference getDependency_Dependency();

	/**
	 * Returns the meta object for the container reference '{@link org.isoe.mbse.sourcecleaner.Dependency#getRequerant <em>Requerant</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Requerant</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Dependency#getRequerant()
	 * @see #getDependency()
	 * @generated
	 */
	EReference getDependency_Requerant();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.mbse.sourcecleaner.Dependency#isReexport <em>Reexport</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Reexport</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Dependency#isReexport()
	 * @see #getDependency()
	 * @generated
	 */
	EAttribute getDependency_Reexport();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.mbse.sourcecleaner.Dependency#isDiagraph <em>Diagraph</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Diagraph</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Dependency#isDiagraph()
	 * @see #getDependency()
	 * @generated
	 */
	EAttribute getDependency_Diagraph();

	/**
	 * Returns the meta object for class '{@link org.isoe.mbse.sourcecleaner.ClassPath <em>Class Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Class Path</em>'.
	 * @see org.isoe.mbse.sourcecleaner.ClassPath
	 * @generated
	 */
	EClass getClassPath();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.mbse.sourcecleaner.ClassPath#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.isoe.mbse.sourcecleaner.ClassPath#getName()
	 * @see #getClassPath()
	 * @generated
	 */
	EAttribute getClassPath_Name();

	/**
	 * Returns the meta object for class '{@link org.isoe.mbse.sourcecleaner.Export <em>Export</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Export</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Export
	 * @generated
	 */
	EClass getExport();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.mbse.sourcecleaner.Export#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Export#getName()
	 * @see #getExport()
	 * @generated
	 */
	EAttribute getExport_Name();

	/**
	 * Returns the meta object for class '{@link org.isoe.mbse.sourcecleaner.ExtensionPoint <em>Extension Point</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Extension Point</em>'.
	 * @see org.isoe.mbse.sourcecleaner.ExtensionPoint
	 * @generated
	 */
	EClass getExtensionPoint();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.mbse.sourcecleaner.ExtensionPoint#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.isoe.mbse.sourcecleaner.ExtensionPoint#getId()
	 * @see #getExtensionPoint()
	 * @generated
	 */
	EAttribute getExtensionPoint_Id();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.mbse.sourcecleaner.ExtensionPoint#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.isoe.mbse.sourcecleaner.ExtensionPoint#getName()
	 * @see #getExtensionPoint()
	 * @generated
	 */
	EAttribute getExtensionPoint_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.mbse.sourcecleaner.ExtensionPoint#getSchema <em>Schema</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Schema</em>'.
	 * @see org.isoe.mbse.sourcecleaner.ExtensionPoint#getSchema()
	 * @see #getExtensionPoint()
	 * @generated
	 */
	EAttribute getExtensionPoint_Schema();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.mbse.sourcecleaner.ExtensionPoint#isDiagraph <em>Diagraph</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Diagraph</em>'.
	 * @see org.isoe.mbse.sourcecleaner.ExtensionPoint#isDiagraph()
	 * @see #getExtensionPoint()
	 * @generated
	 */
	EAttribute getExtensionPoint_Diagraph();

	/**
	 * Returns the meta object for the reference list '{@link org.isoe.mbse.sourcecleaner.ExtensionPoint#getExtensions <em>Extensions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Extensions</em>'.
	 * @see org.isoe.mbse.sourcecleaner.ExtensionPoint#getExtensions()
	 * @see #getExtensionPoint()
	 * @generated
	 */
	EReference getExtensionPoint_Extensions();

	/**
	 * Returns the meta object for the container reference '{@link org.isoe.mbse.sourcecleaner.ExtensionPoint#getPlugin <em>Plugin</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Plugin</em>'.
	 * @see org.isoe.mbse.sourcecleaner.ExtensionPoint#getPlugin()
	 * @see #getExtensionPoint()
	 * @generated
	 */
	EReference getExtensionPoint_Plugin();

	/**
	 * Returns the meta object for class '{@link org.isoe.mbse.sourcecleaner.ExtensionAttribute <em>Extension Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Extension Attribute</em>'.
	 * @see org.isoe.mbse.sourcecleaner.ExtensionAttribute
	 * @generated
	 */
	EClass getExtensionAttribute();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.mbse.sourcecleaner.ExtensionAttribute#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.isoe.mbse.sourcecleaner.ExtensionAttribute#getName()
	 * @see #getExtensionAttribute()
	 * @generated
	 */
	EAttribute getExtensionAttribute_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.mbse.sourcecleaner.ExtensionAttribute#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.isoe.mbse.sourcecleaner.ExtensionAttribute#getValue()
	 * @see #getExtensionAttribute()
	 * @generated
	 */
	EAttribute getExtensionAttribute_Value();

	/**
	 * Returns the meta object for class '{@link org.isoe.mbse.sourcecleaner.Plugin <em>Plugin</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Plugin</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Plugin
	 * @generated
	 */
	EClass getPlugin();

	/**
	 * Returns the meta object for the containment reference list '{@link org.isoe.mbse.sourcecleaner.Plugin#getExtensions <em>Extensions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Extensions</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Plugin#getExtensions()
	 * @see #getPlugin()
	 * @generated
	 */
	EReference getPlugin_Extensions();

	/**
	 * Returns the meta object for the containment reference list '{@link org.isoe.mbse.sourcecleaner.Plugin#getExtensionPoints <em>Extension Points</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Extension Points</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Plugin#getExtensionPoints()
	 * @see #getPlugin()
	 * @generated
	 */
	EReference getPlugin_ExtensionPoints();

	/**
	 * Returns the meta object for the container reference '{@link org.isoe.mbse.sourcecleaner.Plugin#getProject <em>Project</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Project</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Plugin#getProject()
	 * @see #getPlugin()
	 * @generated
	 */
	EReference getPlugin_Project();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.mbse.sourcecleaner.Plugin#getExtra <em>Extra</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Extra</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Plugin#getExtra()
	 * @see #getPlugin()
	 * @generated
	 */
	EAttribute getPlugin_Extra();

	/**
	 * Returns the meta object for class '{@link org.isoe.mbse.sourcecleaner.Schema <em>Schema</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Schema</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Schema
	 * @generated
	 */
	EClass getSchema();

	/**
	 * Returns the meta object for the containment reference list '{@link org.isoe.mbse.sourcecleaner.Schema#getReferences <em>References</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>References</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Schema#getReferences()
	 * @see #getSchema()
	 * @generated
	 */
	EReference getSchema_References();

	/**
	 * Returns the meta object for the container reference '{@link org.isoe.mbse.sourcecleaner.Schema#getProject <em>Project</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Project</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Schema#getProject()
	 * @see #getSchema()
	 * @generated
	 */
	EReference getSchema_Project();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.mbse.sourcecleaner.Schema#getExtensionName <em>Extension Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Extension Name</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Schema#getExtensionName()
	 * @see #getSchema()
	 * @generated
	 */
	EAttribute getSchema_ExtensionName();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.mbse.sourcecleaner.Schema#getExtensionId <em>Extension Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Extension Id</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Schema#getExtensionId()
	 * @see #getSchema()
	 * @generated
	 */
	EAttribute getSchema_ExtensionId();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.mbse.sourcecleaner.Schema#getPluginName <em>Plugin Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Plugin Name</em>'.
	 * @see org.isoe.mbse.sourcecleaner.Schema#getPluginName()
	 * @see #getSchema()
	 * @generated
	 */
	EAttribute getSchema_PluginName();

	/**
	 * Returns the meta object for class '{@link org.isoe.mbse.sourcecleaner.ExtensionReference <em>Extension Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Extension Reference</em>'.
	 * @see org.isoe.mbse.sourcecleaner.ExtensionReference
	 * @generated
	 */
	EClass getExtensionReference();

	/**
	 * Returns the meta object for the container reference '{@link org.isoe.mbse.sourcecleaner.ExtensionReference#getSchema <em>Schema</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Schema</em>'.
	 * @see org.isoe.mbse.sourcecleaner.ExtensionReference#getSchema()
	 * @see #getExtensionReference()
	 * @generated
	 */
	EReference getExtensionReference_Schema();

	/**
	 * Returns the meta object for the reference '{@link org.isoe.mbse.sourcecleaner.ExtensionReference#getJavaclass <em>Javaclass</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Javaclass</em>'.
	 * @see org.isoe.mbse.sourcecleaner.ExtensionReference#getJavaclass()
	 * @see #getExtensionReference()
	 * @generated
	 */
	EReference getExtensionReference_Javaclass();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.mbse.sourcecleaner.ExtensionReference#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.isoe.mbse.sourcecleaner.ExtensionReference#getName()
	 * @see #getExtensionReference()
	 * @generated
	 */
	EAttribute getExtensionReference_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.mbse.sourcecleaner.ExtensionReference#getJava <em>Java</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Java</em>'.
	 * @see org.isoe.mbse.sourcecleaner.ExtensionReference#getJava()
	 * @see #getExtensionReference()
	 * @generated
	 */
	EAttribute getExtensionReference_Java();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.mbse.sourcecleaner.ExtensionReference#getPackage <em>Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Package</em>'.
	 * @see org.isoe.mbse.sourcecleaner.ExtensionReference#getPackage()
	 * @see #getExtensionReference()
	 * @generated
	 */
	EAttribute getExtensionReference_Package();

	/**
	 * Returns the meta object for the attribute '{@link org.isoe.mbse.sourcecleaner.ExtensionReference#getProject <em>Project</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Project</em>'.
	 * @see org.isoe.mbse.sourcecleaner.ExtensionReference#getProject()
	 * @see #getExtensionReference()
	 * @generated
	 */
	EAttribute getExtensionReference_Project();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SourcecleanerFactory getSourcecleanerFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.isoe.mbse.sourcecleaner.impl.ConfigurationImpl <em>Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.mbse.sourcecleaner.impl.ConfigurationImpl
		 * @see org.isoe.mbse.sourcecleaner.impl.SourcecleanerPackageImpl#getConfiguration()
		 * @generated
		 */
		EClass CONFIGURATION = eINSTANCE.getConfiguration();

		/**
		 * The meta object literal for the '<em><b>Projects</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONFIGURATION__PROJECTS = eINSTANCE.getConfiguration_Projects();

		/**
		 * The meta object literal for the '<em><b>Location</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFIGURATION__LOCATION = eINSTANCE.getConfiguration_Location();

		/**
		 * The meta object literal for the '<em><b>Temp</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFIGURATION__TEMP = eINSTANCE.getConfiguration_Temp();

		/**
		 * The meta object literal for the '{@link org.isoe.mbse.sourcecleaner.impl.ProjectImpl <em>Project</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.mbse.sourcecleaner.impl.ProjectImpl
		 * @see org.isoe.mbse.sourcecleaner.impl.SourcecleanerPackageImpl#getProject()
		 * @generated
		 */
		EClass PROJECT = eINSTANCE.getProject();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROJECT__ID = eINSTANCE.getProject_Id();

		/**
		 * The meta object literal for the '<em><b>Sources</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROJECT__SOURCES = eINSTANCE.getProject_Sources();

		/**
		 * The meta object literal for the '<em><b>Manifest</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROJECT__MANIFEST = eINSTANCE.getProject_Manifest();

		/**
		 * The meta object literal for the '<em><b>Build</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROJECT__BUILD = eINSTANCE.getProject_Build();

		/**
		 * The meta object literal for the '<em><b>Plugin</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROJECT__PLUGIN = eINSTANCE.getProject_Plugin();

		/**
		 * The meta object literal for the '<em><b>Schema</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROJECT__SCHEMA = eINSTANCE.getProject_Schema();

		/**
		 * The meta object literal for the '<em><b>Workspace</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROJECT__WORKSPACE = eINSTANCE.getProject_Workspace();

		/**
		 * The meta object literal for the '{@link org.isoe.mbse.sourcecleaner.impl.LocatedElementImpl <em>Located Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.mbse.sourcecleaner.impl.LocatedElementImpl
		 * @see org.isoe.mbse.sourcecleaner.impl.SourcecleanerPackageImpl#getLocatedElement()
		 * @generated
		 */
		EClass LOCATED_ELEMENT = eINSTANCE.getLocatedElement();

		/**
		 * The meta object literal for the '<em><b>Absolute Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOCATED_ELEMENT__ABSOLUTE_PATH = eINSTANCE.getLocatedElement_AbsolutePath();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOCATED_ELEMENT__NAME = eINSTANCE.getLocatedElement_Name();

		/**
		 * The meta object literal for the '{@link org.isoe.mbse.sourcecleaner.impl.SourceImpl <em>Source</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.mbse.sourcecleaner.impl.SourceImpl
		 * @see org.isoe.mbse.sourcecleaner.impl.SourcecleanerPackageImpl#getSource()
		 * @generated
		 */
		EClass SOURCE = eINSTANCE.getSource();

		/**
		 * The meta object literal for the '<em><b>Comment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SOURCE__COMMENT = eINSTANCE.getSource_Comment();

		/**
		 * The meta object literal for the '<em><b>Handled</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SOURCE__HANDLED = eINSTANCE.getSource_Handled();

		/**
		 * The meta object literal for the '<em><b>Mark</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SOURCE__MARK = eINSTANCE.getSource_Mark();

		/**
		 * The meta object literal for the '<em><b>Content</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SOURCE__CONTENT = eINSTANCE.getSource_Content();

		/**
		 * The meta object literal for the '{@link org.isoe.mbse.sourcecleaner.impl.JavaImpl <em>Java</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.mbse.sourcecleaner.impl.JavaImpl
		 * @see org.isoe.mbse.sourcecleaner.impl.SourcecleanerPackageImpl#getJava()
		 * @generated
		 */
		EClass JAVA = eINSTANCE.getJava();

		/**
		 * The meta object literal for the '<em><b>Package</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute JAVA__PACKAGE = eINSTANCE.getJava_Package();

		/**
		 * The meta object literal for the '<em><b>Project</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference JAVA__PROJECT = eINSTANCE.getJava_Project();

		/**
		 * The meta object literal for the '{@link org.isoe.mbse.sourcecleaner.impl.ManifestImpl <em>Manifest</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.mbse.sourcecleaner.impl.ManifestImpl
		 * @see org.isoe.mbse.sourcecleaner.impl.SourcecleanerPackageImpl#getManifest()
		 * @generated
		 */
		EClass MANIFEST = eINSTANCE.getManifest();

		/**
		 * The meta object literal for the '<em><b>Symbolic Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MANIFEST__SYMBOLIC_NAME = eINSTANCE.getManifest_SymbolicName();

		/**
		 * The meta object literal for the '<em><b>Singleton</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MANIFEST__SINGLETON = eINSTANCE.getManifest_Singleton();

		/**
		 * The meta object literal for the '<em><b>Vendor</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MANIFEST__VENDOR = eINSTANCE.getManifest_Vendor();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MANIFEST__VERSION = eINSTANCE.getManifest_Version();

		/**
		 * The meta object literal for the '<em><b>Version Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MANIFEST__VERSION_ID = eINSTANCE.getManifest_VersionId();

		/**
		 * The meta object literal for the '<em><b>Version Qualifier</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MANIFEST__VERSION_QUALIFIER = eINSTANCE.getManifest_VersionQualifier();

		/**
		 * The meta object literal for the '<em><b>Dependencies</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MANIFEST__DEPENDENCIES = eINSTANCE.getManifest_Dependencies();

		/**
		 * The meta object literal for the '<em><b>Lazy</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MANIFEST__LAZY = eINSTANCE.getManifest_Lazy();

		/**
		 * The meta object literal for the '<em><b>Execution Environment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MANIFEST__EXECUTION_ENVIRONMENT = eINSTANCE.getManifest_ExecutionEnvironment();

		/**
		 * The meta object literal for the '<em><b>Diagraph</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MANIFEST__DIAGRAPH = eINSTANCE.getManifest_Diagraph();

		/**
		 * The meta object literal for the '<em><b>Classpathes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MANIFEST__CLASSPATHES = eINSTANCE.getManifest_Classpathes();

		/**
		 * The meta object literal for the '<em><b>Exports</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MANIFEST__EXPORTS = eINSTANCE.getManifest_Exports();

		/**
		 * The meta object literal for the '{@link org.isoe.mbse.sourcecleaner.impl.ExtensionImpl <em>Extension</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.mbse.sourcecleaner.impl.ExtensionImpl
		 * @see org.isoe.mbse.sourcecleaner.impl.SourcecleanerPackageImpl#getExtension()
		 * @generated
		 */
		EClass EXTENSION = eINSTANCE.getExtension();

		/**
		 * The meta object literal for the '<em><b>Extension Point</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXTENSION__EXTENSION_POINT = eINSTANCE.getExtension_ExtensionPoint();

		/**
		 * The meta object literal for the '<em><b>Clazz</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXTENSION__CLAZZ = eINSTANCE.getExtension_Clazz();

		/**
		 * The meta object literal for the '<em><b>Point Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXTENSION__POINT_ID = eINSTANCE.getExtension_PointId();

		/**
		 * The meta object literal for the '<em><b>Extra</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXTENSION__EXTRA = eINSTANCE.getExtension_Extra();

		/**
		 * The meta object literal for the '<em><b>Attributes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXTENSION__ATTRIBUTES = eINSTANCE.getExtension_Attributes();

		/**
		 * The meta object literal for the '<em><b>Implements</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXTENSION__IMPLEMENTS = eINSTANCE.getExtension_Implements();

		/**
		 * The meta object literal for the '<em><b>Diagraph</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXTENSION__DIAGRAPH = eINSTANCE.getExtension_Diagraph();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXTENSION__ID = eINSTANCE.getExtension_Id();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXTENSION__NAME = eINSTANCE.getExtension_Name();

		/**
		 * The meta object literal for the '{@link org.isoe.mbse.sourcecleaner.impl.BuildImpl <em>Build</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.mbse.sourcecleaner.impl.BuildImpl
		 * @see org.isoe.mbse.sourcecleaner.impl.SourcecleanerPackageImpl#getBuild()
		 * @generated
		 */
		EClass BUILD = eINSTANCE.getBuild();

		/**
		 * The meta object literal for the '{@link org.isoe.mbse.sourcecleaner.impl.DependencyImpl <em>Dependency</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.mbse.sourcecleaner.impl.DependencyImpl
		 * @see org.isoe.mbse.sourcecleaner.impl.SourcecleanerPackageImpl#getDependency()
		 * @generated
		 */
		EClass DEPENDENCY = eINSTANCE.getDependency();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEPENDENCY__NAME = eINSTANCE.getDependency_Name();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEPENDENCY__VERSION = eINSTANCE.getDependency_Version();

		/**
		 * The meta object literal for the '<em><b>Dependency</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEPENDENCY__DEPENDENCY = eINSTANCE.getDependency_Dependency();

		/**
		 * The meta object literal for the '<em><b>Requerant</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEPENDENCY__REQUERANT = eINSTANCE.getDependency_Requerant();

		/**
		 * The meta object literal for the '<em><b>Reexport</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEPENDENCY__REEXPORT = eINSTANCE.getDependency_Reexport();

		/**
		 * The meta object literal for the '<em><b>Diagraph</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEPENDENCY__DIAGRAPH = eINSTANCE.getDependency_Diagraph();

		/**
		 * The meta object literal for the '{@link org.isoe.mbse.sourcecleaner.impl.ClassPathImpl <em>Class Path</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.mbse.sourcecleaner.impl.ClassPathImpl
		 * @see org.isoe.mbse.sourcecleaner.impl.SourcecleanerPackageImpl#getClassPath()
		 * @generated
		 */
		EClass CLASS_PATH = eINSTANCE.getClassPath();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CLASS_PATH__NAME = eINSTANCE.getClassPath_Name();

		/**
		 * The meta object literal for the '{@link org.isoe.mbse.sourcecleaner.impl.ExportImpl <em>Export</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.mbse.sourcecleaner.impl.ExportImpl
		 * @see org.isoe.mbse.sourcecleaner.impl.SourcecleanerPackageImpl#getExport()
		 * @generated
		 */
		EClass EXPORT = eINSTANCE.getExport();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXPORT__NAME = eINSTANCE.getExport_Name();

		/**
		 * The meta object literal for the '{@link org.isoe.mbse.sourcecleaner.impl.ExtensionPointImpl <em>Extension Point</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.mbse.sourcecleaner.impl.ExtensionPointImpl
		 * @see org.isoe.mbse.sourcecleaner.impl.SourcecleanerPackageImpl#getExtensionPoint()
		 * @generated
		 */
		EClass EXTENSION_POINT = eINSTANCE.getExtensionPoint();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXTENSION_POINT__ID = eINSTANCE.getExtensionPoint_Id();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXTENSION_POINT__NAME = eINSTANCE.getExtensionPoint_Name();

		/**
		 * The meta object literal for the '<em><b>Schema</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXTENSION_POINT__SCHEMA = eINSTANCE.getExtensionPoint_Schema();

		/**
		 * The meta object literal for the '<em><b>Diagraph</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXTENSION_POINT__DIAGRAPH = eINSTANCE.getExtensionPoint_Diagraph();

		/**
		 * The meta object literal for the '<em><b>Extensions</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXTENSION_POINT__EXTENSIONS = eINSTANCE.getExtensionPoint_Extensions();

		/**
		 * The meta object literal for the '<em><b>Plugin</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXTENSION_POINT__PLUGIN = eINSTANCE.getExtensionPoint_Plugin();

		/**
		 * The meta object literal for the '{@link org.isoe.mbse.sourcecleaner.impl.ExtensionAttributeImpl <em>Extension Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.mbse.sourcecleaner.impl.ExtensionAttributeImpl
		 * @see org.isoe.mbse.sourcecleaner.impl.SourcecleanerPackageImpl#getExtensionAttribute()
		 * @generated
		 */
		EClass EXTENSION_ATTRIBUTE = eINSTANCE.getExtensionAttribute();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXTENSION_ATTRIBUTE__NAME = eINSTANCE.getExtensionAttribute_Name();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXTENSION_ATTRIBUTE__VALUE = eINSTANCE.getExtensionAttribute_Value();

		/**
		 * The meta object literal for the '{@link org.isoe.mbse.sourcecleaner.impl.PluginImpl <em>Plugin</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.mbse.sourcecleaner.impl.PluginImpl
		 * @see org.isoe.mbse.sourcecleaner.impl.SourcecleanerPackageImpl#getPlugin()
		 * @generated
		 */
		EClass PLUGIN = eINSTANCE.getPlugin();

		/**
		 * The meta object literal for the '<em><b>Extensions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLUGIN__EXTENSIONS = eINSTANCE.getPlugin_Extensions();

		/**
		 * The meta object literal for the '<em><b>Extension Points</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLUGIN__EXTENSION_POINTS = eINSTANCE.getPlugin_ExtensionPoints();

		/**
		 * The meta object literal for the '<em><b>Project</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLUGIN__PROJECT = eINSTANCE.getPlugin_Project();

		/**
		 * The meta object literal for the '<em><b>Extra</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PLUGIN__EXTRA = eINSTANCE.getPlugin_Extra();

		/**
		 * The meta object literal for the '{@link org.isoe.mbse.sourcecleaner.impl.SchemaImpl <em>Schema</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.mbse.sourcecleaner.impl.SchemaImpl
		 * @see org.isoe.mbse.sourcecleaner.impl.SourcecleanerPackageImpl#getSchema()
		 * @generated
		 */
		EClass SCHEMA = eINSTANCE.getSchema();

		/**
		 * The meta object literal for the '<em><b>References</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCHEMA__REFERENCES = eINSTANCE.getSchema_References();

		/**
		 * The meta object literal for the '<em><b>Project</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCHEMA__PROJECT = eINSTANCE.getSchema_Project();

		/**
		 * The meta object literal for the '<em><b>Extension Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCHEMA__EXTENSION_NAME = eINSTANCE.getSchema_ExtensionName();

		/**
		 * The meta object literal for the '<em><b>Extension Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCHEMA__EXTENSION_ID = eINSTANCE.getSchema_ExtensionId();

		/**
		 * The meta object literal for the '<em><b>Plugin Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCHEMA__PLUGIN_NAME = eINSTANCE.getSchema_PluginName();

		/**
		 * The meta object literal for the '{@link org.isoe.mbse.sourcecleaner.impl.ExtensionReferenceImpl <em>Extension Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.isoe.mbse.sourcecleaner.impl.ExtensionReferenceImpl
		 * @see org.isoe.mbse.sourcecleaner.impl.SourcecleanerPackageImpl#getExtensionReference()
		 * @generated
		 */
		EClass EXTENSION_REFERENCE = eINSTANCE.getExtensionReference();

		/**
		 * The meta object literal for the '<em><b>Schema</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXTENSION_REFERENCE__SCHEMA = eINSTANCE.getExtensionReference_Schema();

		/**
		 * The meta object literal for the '<em><b>Javaclass</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXTENSION_REFERENCE__JAVACLASS = eINSTANCE.getExtensionReference_Javaclass();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXTENSION_REFERENCE__NAME = eINSTANCE.getExtensionReference_Name();

		/**
		 * The meta object literal for the '<em><b>Java</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXTENSION_REFERENCE__JAVA = eINSTANCE.getExtensionReference_Java();

		/**
		 * The meta object literal for the '<em><b>Package</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXTENSION_REFERENCE__PACKAGE = eINSTANCE.getExtensionReference_Package();

		/**
		 * The meta object literal for the '<em><b>Project</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXTENSION_REFERENCE__PROJECT = eINSTANCE.getExtensionReference_Project();

	}

} //SourcecleanerPackage
