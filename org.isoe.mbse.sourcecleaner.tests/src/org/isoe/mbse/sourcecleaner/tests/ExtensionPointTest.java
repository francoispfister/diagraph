/**
 */
package org.isoe.mbse.sourcecleaner.tests;

import junit.framework.TestCase;

import junit.textui.TestRunner;

import org.isoe.mbse.sourcecleaner.ExtensionPoint;
import org.isoe.mbse.sourcecleaner.SourcecleanerFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Extension Point</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class ExtensionPointTest extends TestCase {

	/**
	 * The fixture for this Extension Point test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExtensionPoint fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(ExtensionPointTest.class);
	}

	/**
	 * Constructs a new Extension Point test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExtensionPointTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Extension Point test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(ExtensionPoint fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Extension Point test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExtensionPoint getFixture() {
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
		setFixture(SourcecleanerFactory.eINSTANCE.createExtensionPoint());
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

} //ExtensionPointTest
