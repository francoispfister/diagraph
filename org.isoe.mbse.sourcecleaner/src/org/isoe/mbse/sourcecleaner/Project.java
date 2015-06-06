/**
 */
package org.isoe.mbse.sourcecleaner;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Project</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Project#getId <em>Id</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Project#getSources <em>Sources</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Project#getManifest <em>Manifest</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Project#getBuild <em>Build</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Project#getPlugin <em>Plugin</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Project#getSchema <em>Schema</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Project#getWorkspace <em>Workspace</em>}</li>
 * </ul>
 *
 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getProject()
 * @model annotation="diagraph node='null' nav:vwproject='null'"
 * @generated
 */
public interface Project extends LocatedElement {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(int)
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getProject_Id()
	 * @model
	 * @generated
	 */
	int getId();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.Project#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(int value);

	/**
	 * Returns the value of the '<em><b>Sources</b></em>' containment reference list.
	 * The list contents are of type {@link org.isoe.mbse.sourcecleaner.Java}.
	 * It is bidirectional and its opposite is '{@link org.isoe.mbse.sourcecleaner.Java#getProject <em>Project</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sources</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sources</em>' containment reference list.
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getProject_Sources()
	 * @see org.isoe.mbse.sourcecleaner.Java#getProject
	 * @model opposite="project" containment="true"
	 * @generated
	 */
	EList<Java> getSources();

	/**
	 * Returns the value of the '<em><b>Manifest</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Manifest</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Manifest</em>' containment reference.
	 * @see #setManifest(Manifest)
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getProject_Manifest()
	 * @model containment="true"
	 * @generated
	 */
	Manifest getManifest();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.Project#getManifest <em>Manifest</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Manifest</em>' containment reference.
	 * @see #getManifest()
	 * @generated
	 */
	void setManifest(Manifest value);

	/**
	 * Returns the value of the '<em><b>Build</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Build</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Build</em>' containment reference.
	 * @see #setBuild(Build)
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getProject_Build()
	 * @model containment="true"
	 * @generated
	 */
	Build getBuild();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.Project#getBuild <em>Build</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Build</em>' containment reference.
	 * @see #getBuild()
	 * @generated
	 */
	void setBuild(Build value);

	/**
	 * Returns the value of the '<em><b>Plugin</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link org.isoe.mbse.sourcecleaner.Plugin#getProject <em>Project</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Plugin</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Plugin</em>' containment reference.
	 * @see #setPlugin(Plugin)
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getProject_Plugin()
	 * @see org.isoe.mbse.sourcecleaner.Plugin#getProject
	 * @model opposite="project" containment="true"
	 * @generated
	 */
	Plugin getPlugin();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.Project#getPlugin <em>Plugin</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Plugin</em>' containment reference.
	 * @see #getPlugin()
	 * @generated
	 */
	void setPlugin(Plugin value);

	/**
	 * Returns the value of the '<em><b>Schema</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link org.isoe.mbse.sourcecleaner.Schema#getProject <em>Project</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Schema</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Schema</em>' containment reference.
	 * @see #setSchema(Schema)
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getProject_Schema()
	 * @see org.isoe.mbse.sourcecleaner.Schema#getProject
	 * @model opposite="project" containment="true"
	 * @generated
	 */
	Schema getSchema();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.Project#getSchema <em>Schema</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Schema</em>' containment reference.
	 * @see #getSchema()
	 * @generated
	 */
	void setSchema(Schema value);

	/**
	 * Returns the value of the '<em><b>Workspace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Workspace</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Workspace</em>' attribute.
	 * @see #setWorkspace(String)
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getProject_Workspace()
	 * @model
	 * @generated
	 */
	String getWorkspace();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.Project#getWorkspace <em>Workspace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Workspace</em>' attribute.
	 * @see #getWorkspace()
	 * @generated
	 */
	void setWorkspace(String value);

} // Project
