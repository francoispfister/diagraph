/**
 */
package org.isoe.diagraph.diagraph;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.isoe.diagraph.diagraph.DiagraphPackage
 * @generated
 */
public interface DiagraphFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DiagraphFactory eINSTANCE = org.isoe.diagraph.diagraph.impl.DiagraphFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>DNode</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>DNode</em>'.
	 * @generated
	 */
	DNode createDNode();

	/**
	 * Returns a new object of class '<em>DLabeled Edge</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>DLabeled Edge</em>'.
	 * @generated
	 */
	DLabeledEdge createDLabeledEdge();

	/**
	 * Returns a new object of class '<em>DReference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>DReference</em>'.
	 * @generated
	 */
	DReference createDReference();

	/**
	 * Returns a new object of class '<em>DNested Edge</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>DNested Edge</em>'.
	 * @generated
	 */
	DNestedEdge createDNestedEdge();

	/**
	 * Returns a new object of class '<em>DGraph</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>DGraph</em>'.
	 * @generated
	 */
	DGraph createDGraph();

	/**
	 * Returns a new object of class '<em>DCompartment Edge</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>DCompartment Edge</em>'.
	 * @generated
	 */
	DCompartmentEdge createDCompartmentEdge();

	/**
	 * Returns a new object of class '<em>DPoint Of View</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>DPoint Of View</em>'.
	 * @generated
	 */
	DPointOfView createDPointOfView();

	/**
	 * Returns a new object of class '<em>DView Navigation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>DView Navigation</em>'.
	 * @generated
	 */
	DViewNavigation createDViewNavigation();

	/**
	 * Returns a new object of class '<em>DAffixed Edge</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>DAffixed Edge</em>'.
	 * @generated
	 */
	DAffixedEdge createDAffixedEdge();

	/**
	 * Returns a new object of class '<em>DLabel</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>DLabel</em>'.
	 * @generated
	 */
	DLabel createDLabel();

	/**
	 * Returns a new object of class '<em>DNavigation Edge</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>DNavigation Edge</em>'.
	 * @generated
	 */
	DNavigationEdge createDNavigationEdge();

	/**
	 * Returns a new object of class '<em>DGeneric</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>DGeneric</em>'.
	 * @generated
	 */
	DGeneric createDGeneric();

	/**
	 * Returns a new object of class '<em>DContainment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>DContainment</em>'.
	 * @generated
	 */
	DContainment createDContainment();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	DiagraphPackage getDiagraphPackage();

} //DiagraphFactory
