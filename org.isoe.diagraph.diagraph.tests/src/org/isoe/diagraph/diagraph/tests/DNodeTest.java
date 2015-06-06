/**
 */
package org.isoe.diagraph.diagraph.tests;

import junit.textui.TestRunner;

import org.isoe.diagraph.diagraph.DNode;
import org.isoe.diagraph.diagraph.DiagraphFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>DNode</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class DNodeTest extends DLabeledElementTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(DNodeTest.class);
	}

	/**
	 * Constructs a new DNode test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DNodeTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this DNode test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected DNode getFixture() {
		return (DNode)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(DiagraphFactory.eINSTANCE.createDNode());
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

} //DNodeTest
