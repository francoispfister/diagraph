/**
 */
package org.isoe.mbse.sourcecleaner.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.isoe.mbse.sourcecleaner.Source;
import org.isoe.mbse.sourcecleaner.SourcecleanerPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Source</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.SourceImpl#getComment <em>Comment</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.SourceImpl#isHandled <em>Handled</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.SourceImpl#isMark <em>Mark</em>}</li>
 *   <li>{@link org.isoe.mbse.sourcecleaner.impl.SourceImpl#getContent <em>Content</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class SourceImpl extends LocatedElementImpl implements Source {
	/**
	 * The default value of the '{@link #getComment() <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComment()
	 * @generated
	 * @ordered
	 */
	protected static final String COMMENT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getComment() <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComment()
	 * @generated
	 * @ordered
	 */
	protected String comment = COMMENT_EDEFAULT;

	/**
	 * The default value of the '{@link #isHandled() <em>Handled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHandled()
	 * @generated
	 * @ordered
	 */
	protected static final boolean HANDLED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isHandled() <em>Handled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHandled()
	 * @generated
	 * @ordered
	 */
	protected boolean handled = HANDLED_EDEFAULT;

	/**
	 * The default value of the '{@link #isMark() <em>Mark</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMark()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MARK_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMark() <em>Mark</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMark()
	 * @generated
	 * @ordered
	 */
	protected boolean mark = MARK_EDEFAULT;

	/**
	 * The default value of the '{@link #getContent() <em>Content</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContent()
	 * @generated
	 * @ordered
	 */
	protected static final String CONTENT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getContent() <em>Content</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContent()
	 * @generated
	 * @ordered
	 */
	protected String content = CONTENT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SourceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SourcecleanerPackage.Literals.SOURCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComment(String newComment) {
		String oldComment = comment;
		comment = newComment;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.SOURCE__COMMENT, oldComment, comment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isHandled() {
		return handled;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHandled(boolean newHandled) {
		boolean oldHandled = handled;
		handled = newHandled;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.SOURCE__HANDLED, oldHandled, handled));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isMark() {
		return mark;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMark(boolean newMark) {
		boolean oldMark = mark;
		mark = newMark;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.SOURCE__MARK, oldMark, mark));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getContent() {
		return content;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setContent(String newContent) {
		String oldContent = content;
		content = newContent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SourcecleanerPackage.SOURCE__CONTENT, oldContent, content));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SourcecleanerPackage.SOURCE__COMMENT:
				return getComment();
			case SourcecleanerPackage.SOURCE__HANDLED:
				return isHandled();
			case SourcecleanerPackage.SOURCE__MARK:
				return isMark();
			case SourcecleanerPackage.SOURCE__CONTENT:
				return getContent();
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
			case SourcecleanerPackage.SOURCE__COMMENT:
				setComment((String)newValue);
				return;
			case SourcecleanerPackage.SOURCE__HANDLED:
				setHandled((Boolean)newValue);
				return;
			case SourcecleanerPackage.SOURCE__MARK:
				setMark((Boolean)newValue);
				return;
			case SourcecleanerPackage.SOURCE__CONTENT:
				setContent((String)newValue);
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
			case SourcecleanerPackage.SOURCE__COMMENT:
				setComment(COMMENT_EDEFAULT);
				return;
			case SourcecleanerPackage.SOURCE__HANDLED:
				setHandled(HANDLED_EDEFAULT);
				return;
			case SourcecleanerPackage.SOURCE__MARK:
				setMark(MARK_EDEFAULT);
				return;
			case SourcecleanerPackage.SOURCE__CONTENT:
				setContent(CONTENT_EDEFAULT);
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
			case SourcecleanerPackage.SOURCE__COMMENT:
				return COMMENT_EDEFAULT == null ? comment != null : !COMMENT_EDEFAULT.equals(comment);
			case SourcecleanerPackage.SOURCE__HANDLED:
				return handled != HANDLED_EDEFAULT;
			case SourcecleanerPackage.SOURCE__MARK:
				return mark != MARK_EDEFAULT;
			case SourcecleanerPackage.SOURCE__CONTENT:
				return CONTENT_EDEFAULT == null ? content != null : !CONTENT_EDEFAULT.equals(content);
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
		result.append(" (comment: ");
		result.append(comment);
		result.append(", handled: ");
		result.append(handled);
		result.append(", mark: ");
		result.append(mark);
		result.append(", content: ");
		result.append(content);
		result.append(')');
		return result.toString();
	}

} //SourceImpl
