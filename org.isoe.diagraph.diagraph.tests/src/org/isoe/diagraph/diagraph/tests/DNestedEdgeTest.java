/**
 */
package org.isoe.diagraph.diagraph.tests;

import junit.textui.TestRunner;

import org.isoe.diagraph.diagraph.DNestedEdge;
import org.isoe.diagraph.diagraph.DiagraphFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>DNested Edge</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class DNestedEdgeTest extends DOwnedEdgeTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(DNestedEdgeTest.class);
	}

	/**
	 * Constructs a new DNested Edge test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DNestedEdgeTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this DNested Edge test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected DNestedEdge getFixture() {
		return (DNestedEdge)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(DiagraphFactory.eINSTANCE.createDNestedEdge());
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

} //DNestedEdgeTest
