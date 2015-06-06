/**
 */
package org.isoe.diagraph.diagraph.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.isoe.diagraph.diagraph.DCompartmentEdge;
import org.isoe.diagraph.diagraph.DiagraphPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>DCompartment Edge</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DCompartmentEdgeImpl#getPartitionName <em>Partition Name</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DCompartmentEdgeImpl#getDepth <em>Depth</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DCompartmentEdgeImpl extends DNestedEdgeImpl implements DCompartmentEdge {
	/**
	 * The default value of the '{@link #getPartitionName() <em>Partition Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPartitionName()
	 * @generated
	 * @ordered
	 */
	protected static final String PARTITION_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPartitionName() <em>Partition Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPartitionName()
	 * @generated
	 * @ordered
	 */
	protected String partitionName = PARTITION_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getDepth() <em>Depth</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDepth()
	 * @generated
	 * @ordered
	 */
	protected static final int DEPTH_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getDepth() <em>Depth</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDepth()
	 * @generated
	 * @ordered
	 */
	protected int depth = DEPTH_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DCompartmentEdgeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DiagraphPackage.Literals.DCOMPARTMENT_EDGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPartitionName() {
		return partitionName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPartitionName(String newPartitionName) {
		String oldPartitionName = partitionName;
		partitionName = newPartitionName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagraphPackage.DCOMPARTMENT_EDGE__PARTITION_NAME, oldPartitionName, partitionName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getDepth() {
		return depth;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDepth(int newDepth) {
		int oldDepth = depth;
		depth = newDepth;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagraphPackage.DCOMPARTMENT_EDGE__DEPTH, oldDepth, depth));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DiagraphPackage.DCOMPARTMENT_EDGE__PARTITION_NAME:
				return getPartitionName();
			case DiagraphPackage.DCOMPARTMENT_EDGE__DEPTH:
				return getDepth();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case DiagraphPackage.DCOMPARTMENT_EDGE__PARTITION_NAME:
				setPartitionName((String)newValue);
				return;
			case DiagraphPackage.DCOMPARTMENT_EDGE__DEPTH:
				setDepth((Integer)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case DiagraphPackage.DCOMPARTMENT_EDGE__PARTITION_NAME:
				setPartitionName(PARTITION_NAME_EDEFAULT);
				return;
			case DiagraphPackage.DCOMPARTMENT_EDGE__DEPTH:
				setDepth(DEPTH_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case DiagraphPackage.DCOMPARTMENT_EDGE__PARTITION_NAME:
				return PARTITION_NAME_EDEFAULT == null ? partitionName != null : !PARTITION_NAME_EDEFAULT.equals(partitionName);
			case DiagraphPackage.DCOMPARTMENT_EDGE__DEPTH:
				return depth != DEPTH_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (partitionName: ");
		result.append(partitionName);
		result.append(", depth: ");
		result.append(depth);
		result.append(')');
		return result.toString();
	}

} //DCompartmentEdgeImpl
