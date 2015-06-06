/**
 */
package org.isoe.mbse.sourcecleaner.tests;

import junit.textui.TestRunner;

import org.isoe.mbse.sourcecleaner.Plugin;
import org.isoe.mbse.sourcecleaner.SourcecleanerFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Plugin</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class PluginTest extends SourceTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(PluginTest.class);
	}

	/**
	 * Constructs a new Plugin test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PluginTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Plugin test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Plugin getFixture() {
		return (Plugin)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(SourcecleanerFactory.eINSTANCE.createPlugin());
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

} //PluginTest
