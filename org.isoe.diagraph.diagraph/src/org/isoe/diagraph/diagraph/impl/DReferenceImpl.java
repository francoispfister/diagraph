/**
 */
package org.isoe.diagraph.diagraph.impl;

import org.eclipse.emf.ecore.EClass;

import org.isoe.diagraph.diagraph.DReference;
import org.isoe.diagraph.diagraph.DiagraphPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>DReference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class DReferenceImpl extends DLineEdgeImpl implements DReference {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DReferenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DiagraphPackage.Literals.DREFERENCE;
	}

	@Override
	public boolean isDerived() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isInferred() {
		// TODO Auto-generated method stub
		return false;
	}

} //DReferenceImpl
