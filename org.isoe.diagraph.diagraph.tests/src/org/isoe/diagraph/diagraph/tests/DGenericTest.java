/**
 */
package org.isoe.diagraph.diagraph.tests;

import junit.textui.TestRunner;

import org.isoe.diagraph.diagraph.DGeneric;
import org.isoe.diagraph.diagraph.DiagraphFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>DGeneric</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class DGenericTest extends DLabeledElementTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(DGenericTest.class);
	}

	/**
	 * Constructs a new DGeneric test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DGenericTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this DGeneric test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected DGeneric getFixture() {
		return (DGeneric)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(DiagraphFactory.eINSTANCE.createDGeneric());
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

} //DGenericTest
