/**
 */
package org.isoe.mbse.sourcecleaner.tests;

import junit.textui.TestRunner;

import org.isoe.mbse.sourcecleaner.Project;
import org.isoe.mbse.sourcecleaner.SourcecleanerFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Project</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class ProjectTest extends LocatedElementTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(ProjectTest.class);
	}

	/**
	 * Constructs a new Project test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProjectTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Project test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Project getFixture() {
		return (Project)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(SourcecleanerFactory.eINSTANCE.createProject());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}

} //ProjectTest