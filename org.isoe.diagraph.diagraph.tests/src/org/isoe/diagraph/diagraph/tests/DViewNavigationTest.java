/**
 */
package org.isoe.diagraph.diagraph.tests;

import junit.framework.TestCase;

import junit.textui.TestRunner;

import org.isoe.diagraph.diagraph.DViewNavigation;
import org.isoe.diagraph.diagraph.DiagraphFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>DView Navigation</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class DViewNavigationTest extends TestCase {

	/**
	 * The fixture for this DView Navigation test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DViewNavigation fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(DViewNavigationTest.class);
	}

	/**
	 * Constructs a new DView Navigation test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DViewNavigationTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this DView Navigation test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(DViewNavigation fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this DView Navigation test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DViewNavigation getFixture() {
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
		setFixture(DiagraphFactory.eINSTANCE.createDViewNavigation());
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

} //DViewNavigationTest
