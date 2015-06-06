/**
 */
package org.isoe.diagraph.diagraph.tests;

import junit.textui.TestRunner;

import org.isoe.diagraph.diagraph.DLabeledEdge;
import org.isoe.diagraph.diagraph.DiagraphFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>DLabeled Edge</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class DLabeledEdgeTest extends DOwnedEdgeTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(DLabeledEdgeTest.class);
	}

	/**
	 * Constructs a new DLabeled Edge test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DLabeledEdgeTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this DLabeled Edge test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected DLabeledEdge getFixture() {
		return (DLabeledEdge)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(DiagraphFactory.eINSTANCE.createDLabeledEdge());
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

} //DLabeledEdgeTest
