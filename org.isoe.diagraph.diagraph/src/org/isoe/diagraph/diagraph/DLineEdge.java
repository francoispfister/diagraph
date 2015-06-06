/**
 */
package org.isoe.diagraph.diagraph;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>DLine Edge</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.isoe.diagraph.diagraph.DLineEdge#getArrows <em>Arrows</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDLineEdge()
 * @model abstract="true"
 * @generated
 */
public interface DLineEdge extends DSimpleEdge {
	/**
	 * Returns the value of the '<em><b>Arrows</b></em>' attribute list.
	 * The list contents are of type {@link org.isoe.diagraph.diagraph.DShape}.
	 * The literals are from the enumeration {@link org.isoe.diagraph.diagraph.DShape}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Arrows</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Arrows</em>' attribute list.
	 * @see org.isoe.diagraph.diagraph.DShape
	 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDLineEdge_Arrows()
	 * @model upper="2"
	 * @generated
	 */
	EList<DShape> getArrows();

} // DLineEdge
