/**
 */
package org.isoe.mbse.sourcecleaner.tests;

import junit.textui.TestRunner;

import org.isoe.mbse.sourcecleaner.Manifest;
import org.isoe.mbse.sourcecleaner.SourcecleanerFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Manifest</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class ManifestTest extends SourceTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(ManifestTest.class);
	}

	/**
	 * Constructs a new Manifest test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ManifestTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Manifest test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Manifest getFixture() {
		return (Manifest)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(SourcecleanerFactory.eINSTANCE.createManifest());
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

} //ManifestTest
