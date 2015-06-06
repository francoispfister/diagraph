/**
 */
package org.isoe.diagraph.diagraph;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>DCompartment Edge</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.isoe.diagraph.diagraph.DCompartmentEdge#getPartitionName <em>Partition Name</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.DCompartmentEdge#getDepth <em>Depth</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDCompartmentEdge()
 * @model
 * @generated
 */
public interface DCompartmentEdge extends DNestedEdge {
	/**
	 * Returns the value of the '<em><b>Partition Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Partition Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Partition Name</em>' attribute.
	 * @see #setPartitionName(String)
	 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDCompartmentEdge_PartitionName()
	 * @model
	 * @generated
	 */
	String getPartitionName();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diagraph.DCompartmentEdge#getPartitionName <em>Partition Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Partition Name</em>' attribute.
	 * @see #getPartitionName()
	 * @generated
	 */
	void setPartitionName(String value);

	/**
	 * Returns the value of the '<em><b>Depth</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Depth</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Depth</em>' attribute.
	 * @see #setDepth(int)
	 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDCompartmentEdge_Depth()
	 * @model
	 * @generated
	 */
	int getDepth();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diagraph.DCompartmentEdge#getDepth <em>Depth</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Depth</em>' attribute.
	 * @see #getDepth()
	 * @generated
	 */
	void setDepth(int value);

} // DCompartmentEdge
