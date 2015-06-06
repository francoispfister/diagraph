/**
 */
package org.isoe.diagraph.diagraph.tests;

import junit.framework.TestCase;

import junit.textui.TestRunner;

import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.diagraph.DiagraphFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>DGraph</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class DGraphTest extends TestCase {

	/**
	 * The fixture for this DGraph test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DGraph fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(DGraphTest.class);
	}

	/**
	 * Constructs a new DGraph test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DGraphTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this DGraph test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(DGraph fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this DGraph test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DGraph getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(DiagraphFactory.eINSTANCE.createDGraph());
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

} //DGraphTest
