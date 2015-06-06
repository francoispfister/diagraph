/**
 */
package org.isoe.diagraph.diagraph.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.isoe.diagraph.diagraph.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.isoe.diagraph.diagraph.DiagraphPackage
 * @generated
 */
public class DiagraphSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static DiagraphPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DiagraphSwitch() {
		if (modelPackage == null) {
			modelPackage = DiagraphPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @parameter ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case DiagraphPackage.DEDGE: {
				DEdge dEdge = (DEdge)theEObject;
				T result = caseDEdge(dEdge);
				if (result == null) result = caseDGraphElement(dEdge);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DiagraphPackage.DGRAPH_ELEMENT: {
				DGraphElement dGraphElement = (DGraphElement)theEObject;
				T result = caseDGraphElement(dGraphElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DiagraphPackage.DNODE: {
				DNode dNode = (DNode)theEObject;
				T result = caseDNode(dNode);
				if (result == null) result = caseDLabeledElement(dNode);
				if (result == null) result = caseDOwnedElement(dNode);
				if (result == null) result = caseDGraphElement(dNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DiagraphPackage.DLABELED_EDGE: {
				DLabeledEdge dLabeledEdge = (DLabeledEdge)theEObject;
				T result = caseDLabeledEdge(dLabeledEdge);
				if (result == null) result = caseDOwnedEdge(dLabeledEdge);
				if (result == null) result = caseDLabeledElement(dLabeledEdge);
				if (result == null) result = caseDLineEdge(dLabeledEdge);
				if (result == null) result = caseDOwnedElement(dLabeledEdge);
				if (result == null) result = caseDSimpleEdge(dLabeledEdge);
				if (result == null) result = caseDEdge(dLabeledEdge);
				if (result == null) result = caseDGraphElement(dLabeledEdge);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DiagraphPackage.DREFERENCE: {
				DReference dReference = (DReference)theEObject;
				T result = caseDReference(dReference);
				if (result == null) result = caseDLineEdge(dReference);
				if (result == null) result = caseDSimpleEdge(dReference);
				if (result == null) result = caseDEdge(dReference);
				if (result == null) result = caseDGraphElement(dReference);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DiagraphPackage.DNESTED_EDGE: {
				DNestedEdge dNestedEdge = (DNestedEdge)theEObject;
				T result = caseDNestedEdge(dNestedEdge);
				if (result == null) result = caseDOwnedEdge(dNestedEdge);
				if (result == null) result = caseDOwnedElement(dNestedEdge);
				if (result == null) result = caseDEdge(dNestedEdge);
				if (result == null) result = caseDGraphElement(dNestedEdge);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DiagraphPackage.DGRAPH: {
				DGraph dGraph = (DGraph)theEObject;
				T result = caseDGraph(dGraph);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DiagraphPackage.DLABELED_ELEMENT: {
				DLabeledElement dLabeledElement = (DLabeledElement)theEObject;
				T result = caseDLabeledElement(dLabeledElement);
				if (result == null) result = caseDGraphElement(dLabeledElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DiagraphPackage.DOWNED_ELEMENT: {
				DOwnedElement dOwnedElement = (DOwnedElement)theEObject;
				T result = caseDOwnedElement(dOwnedElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DiagraphPackage.DOWNED_EDGE: {
				DOwnedEdge dOwnedEdge = (DOwnedEdge)theEObject;
				T result = caseDOwnedEdge(dOwnedEdge);
				if (result == null) result = caseDOwnedElement(dOwnedEdge);
				if (result == null) result = caseDEdge(dOwnedEdge);
				if (result == null) result = caseDGraphElement(dOwnedEdge);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DiagraphPackage.DCOMPARTMENT_EDGE: {
				DCompartmentEdge dCompartmentEdge = (DCompartmentEdge)theEObject;
				T result = caseDCompartmentEdge(dCompartmentEdge);
				if (result == null) result = caseDNestedEdge(dCompartmentEdge);
				if (result == null) result = caseDOwnedEdge(dCompartmentEdge);
				if (result == null) result = caseDOwnedElement(dCompartmentEdge);
				if (result == null) result = caseDEdge(dCompartmentEdge);
				if (result == null) result = caseDGraphElement(dCompartmentEdge);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DiagraphPackage.DPOINT_OF_VIEW: {
				DPointOfView dPointOfView = (DPointOfView)theEObject;
				T result = caseDPointOfView(dPointOfView);
				if (result == null) result = caseDNode(dPointOfView);
				if (result == null) result = caseDLabeledElement(dPointOfView);
				if (result == null) result = caseDOwnedElement(dPointOfView);
				if (result == null) result = caseDGraphElement(dPointOfView);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DiagraphPackage.DVIEW_NAVIGATION: {
				DViewNavigation dViewNavigation = (DViewNavigation)theEObject;
				T result = caseDViewNavigation(dViewNavigation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DiagraphPackage.DAFFIXED_EDGE: {
				DAffixedEdge dAffixedEdge = (DAffixedEdge)theEObject;
				T result = caseDAffixedEdge(dAffixedEdge);
				if (result == null) result = caseDNestedEdge(dAffixedEdge);
				if (result == null) result = caseDOwnedEdge(dAffixedEdge);
				if (result == null) result = caseDOwnedElement(dAffixedEdge);
				if (result == null) result = caseDEdge(dAffixedEdge);
				if (result == null) result = caseDGraphElement(dAffixedEdge);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DiagraphPackage.DLABEL: {
				DLabel dLabel = (DLabel)theEObject;
				T result = caseDLabel(dLabel);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DiagraphPackage.DLINE_EDGE: {
				DLineEdge dLineEdge = (DLineEdge)theEObject;
				T result = caseDLineEdge(dLineEdge);
				if (result == null) result = caseDSimpleEdge(dLineEdge);
				if (result == null) result = caseDEdge(dLineEdge);
				if (result == null) result = caseDGraphElement(dLineEdge);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DiagraphPackage.DNAVIGATION_EDGE: {
				DNavigationEdge dNavigationEdge = (DNavigationEdge)theEObject;
				T result = caseDNavigationEdge(dNavigationEdge);
				if (result == null) result = caseDSimpleEdge(dNavigationEdge);
				if (result == null) result = caseDEdge(dNavigationEdge);
				if (result == null) result = caseDGraphElement(dNavigationEdge);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DiagraphPackage.DGENERIC: {
				DGeneric dGeneric = (DGeneric)theEObject;
				T result = caseDGeneric(dGeneric);
				if (result == null) result = caseDLabeledElement(dGeneric);
				if (result == null) result = caseDGraphElement(dGeneric);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DiagraphPackage.DCONTAINMENT: {
				DContainment dContainment = (DContainment)theEObject;
				T result = caseDContainment(dContainment);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DiagraphPackage.DSIMPLE_EDGE: {
				DSimpleEdge dSimpleEdge = (DSimpleEdge)theEObject;
				T result = caseDSimpleEdge(dSimpleEdge);
				if (result == null) result = caseDEdge(dSimpleEdge);
				if (result == null) result = caseDGraphElement(dSimpleEdge);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>DEdge</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>DEdge</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDEdge(DEdge object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>DGraph Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>DGraph Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDGraphElement(DGraphElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>DNode</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>DNode</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDNode(DNode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>DLabeled Edge</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>DLabeled Edge</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDLabeledEdge(DLabeledEdge object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>DReference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>DReference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDReference(DReference object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>DNested Edge</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>DNested Edge</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDNestedEdge(DNestedEdge object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>DGraph</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>DGraph</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDGraph(DGraph object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>DLabeled Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>DLabeled Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDLabeledElement(DLabeledElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>DOwned Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>DOwned Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDOwnedElement(DOwnedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>DOwned Edge</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>DOwned Edge</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDOwnedEdge(DOwnedEdge object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>DCompartment Edge</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>DCompartment Edge</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDCompartmentEdge(DCompartmentEdge object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>DPoint Of View</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>DPoint Of View</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDPointOfView(DPointOfView object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>DView Navigation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>DView Navigation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDViewNavigation(DViewNavigation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>DAffixed Edge</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>DAffixed Edge</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDAffixedEdge(DAffixedEdge object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>DLabel</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>DLabel</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDLabel(DLabel object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>DLine Edge</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>DLine Edge</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDLineEdge(DLineEdge object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>DNavigation Edge</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>DNavigation Edge</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDNavigationEdge(DNavigationEdge object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>DGeneric</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>DGeneric</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDGeneric(DGeneric object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>DContainment</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>DContainment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDContainment(DContainment object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>DSimple Edge</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>DSimple Edge</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDSimpleEdge(DSimpleEdge object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //DiagraphSwitch
