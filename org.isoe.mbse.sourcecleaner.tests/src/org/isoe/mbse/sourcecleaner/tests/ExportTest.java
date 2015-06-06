/**
 */
package org.isoe.mbse.sourcecleaner.tests;

import junit.framework.TestCase;

import junit.textui.TestRunner;

import org.isoe.mbse.sourcecleaner.Export;
import org.isoe.mbse.sourcecleaner.SourcecleanerFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Export</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class ExportTest extends TestCase {

	/**
	 * The fixture for this Export test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Export fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(ExportTest.class);
	}

	/**
	 * Constructs a new Export test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExportTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Export test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(Export fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Export test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Export getFixture() {
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
		setFixture(SourcecleanerFactory.eINSTANCE.createExport());
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

} //ExportTest
