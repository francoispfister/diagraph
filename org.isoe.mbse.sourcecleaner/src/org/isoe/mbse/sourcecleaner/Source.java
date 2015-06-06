/**
 */
package org.isoe.mbse.sourcecleaner;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Source</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Source#getComment <em>Comment</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Source#isHandled <em>Handled</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Source#isMark <em>Mark</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.Source#getContent <em>Content</em>}</li>
 * </ul>
 *
 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getSource()
 * @model abstract="true"
 *        annotation="diagraph node='null'"
 * @generated
 */
public interface Source extends LocatedElement {
	/**
	 * Returns the value of the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Comment</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Comment</em>' attribute.
	 * @see #setComment(String)
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getSource_Comment()
	 * @model
	 * @generated
	 */
	String getComment();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.Source#getComment <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Comment</em>' attribute.
	 * @see #getComment()
	 * @generated
	 */
	void setComment(String value);

	/**
	 * Returns the value of the '<em><b>Handled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Handled</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Handled</em>' attribute.
	 * @see #setHandled(boolean)
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getSource_Handled()
	 * @model
	 * @generated
	 */
	boolean isHandled();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.Source#isHandled <em>Handled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Handled</em>' attribute.
	 * @see #isHandled()
	 * @generated
	 */
	void setHandled(boolean value);

	/**
	 * Returns the value of the '<em><b>Mark</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mark</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mark</em>' attribute.
	 * @see #setMark(boolean)
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getSource_Mark()
	 * @model
	 * @generated
	 */
	boolean isMark();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.Source#isMark <em>Mark</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mark</em>' attribute.
	 * @see #isMark()
	 * @generated
	 */
	void setMark(boolean value);

	/**
	 * Returns the value of the '<em><b>Content</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Content</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Content</em>' attribute.
	 * @see #setContent(String)
	 * @see org.isoe.mbse.sourcecleaner.SourcecleanerPackage#getSource_Content()
	 * @model
	 * @generated
	 */
	String getContent();

	/**
	 * Sets the value of the '{@link org.isoe.mbse.sourcecleaner.Source#getContent <em>Content</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Content</em>' attribute.
	 * @see #getContent()
	 * @generated
	 */
	void setContent(String value);

} // Source
