/**
 */
package org.isoe.diagraph.diagraph.tests;

import junit.textui.TestRunner;

import org.isoe.diagraph.diagraph.DPointOfView;
import org.isoe.diagraph.diagraph.DiagraphFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>DPoint Of View</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class DPointOfViewTest extends DNodeTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(DPointOfViewTest.class);
	}

	/**
	 * Constructs a new DPoint Of View test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DPointOfViewTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this DPoint Of View test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected DPointOfView getFixture() {
		return (DPointOfView)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(DiagraphFactory.eINSTANCE.createDPointOfView());
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

} //DPointOfViewTest
