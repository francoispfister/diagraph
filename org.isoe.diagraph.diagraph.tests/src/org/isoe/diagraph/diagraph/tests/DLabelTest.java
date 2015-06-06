/**
 */
package org.isoe.diagraph.diagraph.tests;

import junit.framework.TestCase;

import junit.textui.TestRunner;

import org.isoe.diagraph.diagraph.DLabel;
import org.isoe.diagraph.diagraph.DiagraphFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>DLabel</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class DLabelTest extends TestCase {

	/**
	 * The fixture for this DLabel test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DLabel fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(DLabelTest.class);
	}

	/**
	 * Constructs a new DLabel test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DLabelTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this DLabel test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(DLabel fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this DLabel test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DLabel getFixture() {
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
		setFixture(DiagraphFactory.eINSTANCE.createDLabel());
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

} //DLabelTest
