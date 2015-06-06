/**
 */
package org.isoe.diagraph.diagraph.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.isoe.diagraph.diagraph.DLabel;
import org.isoe.diagraph.diagraph.DLabeledElement;
import org.isoe.diagraph.diagraph.DiagraphPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>DLabeled Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DLabeledElementImpl#getEClaz <em>EClaz</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DLabeledElementImpl#getDlabels <em>Dlabels</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DLabeledElementImpl#getLabls <em>Labls</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DLabeledElementImpl#getExpression <em>Expression</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class DLabeledElementImpl extends DGraphElementImpl implements DLabeledElement {
	/**
	 * The cached value of the '{@link #getEClaz() <em>EClaz</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEClaz()
	 * @generated
	 * @ordered
	 */
	protected EClass eClaz;

	/**
	 * The cached value of the '{@link #getDlabels() <em>Dlabels</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDlabels()
	 * @generated
	 * @ordered
	 */
	protected EList<DLabel> dlabels;

	/**
	 * The cached value of the '{@link #getLabls() <em>Labls</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabls()
	 * @generated
	 * @ordered
	 */
	protected EList<String> labls;

	/**
	 * The default value of the '{@link #getExpression() <em>Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExpression()
	 * @generated
	 * @ordered
	 */
	protected static final String EXPRESSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getExpression() <em>Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExpression()
	 * @generated
	 * @ordered
	 */
	protected String expression = EXPRESSION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DLabeledElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DiagraphPackage.Literals.DLABELED_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEClaz() {
		if (eClaz != null && eClaz.eIsProxy()) {
			InternalEObject oldEClaz = (InternalEObject)eClaz;
			eClaz = (EClass)eResolveProxy(oldEClaz);
			if (eClaz != oldEClaz) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DiagraphPackage.DLABELED_ELEMENT__ECLAZ, oldEClaz, eClaz));
			}
		}
		return eClaz;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass basicGetEClaz() {
		return eClaz;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEClaz(EClass newEClaz) {
		EClass oldEClaz = eClaz;
		eClaz = newEClaz;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagraphPackage.DLABELED_ELEMENT__ECLAZ, oldEClaz, eClaz));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DLabel> getDlabels() {
		if (dlabels == null) {
			dlabels = new EObjectContainmentEList<DLabel>(DLabel.class, this, DiagraphPackage.DLABELED_ELEMENT__DLABELS);
		}
		return dlabels;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getLabls() {
		if (labls == null) {
			labls = new EDataTypeUniqueEList<String>(String.class, this, DiagraphPackage.DLABELED_ELEMENT__LABLS);
		}
		return labls;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getExpression() {
		return expression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExpression(String newExpression) {
		String oldExpression = expression;
		expression = newExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagraphPackage.DLABELED_ELEMENT__EXPRESSION, oldExpression, expression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DiagraphPackage.DLABELED_ELEMENT__DLABELS:
				return ((InternalEList<?>)getDlabels()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DiagraphPackage.DLABELED_ELEMENT__ECLAZ:
				if (resolve) return getEClaz();
				return basicGetEClaz();
			case DiagraphPackage.DLABELED_ELEMENT__DLABELS:
				return getDlabels();
			case DiagraphPackage.DLABELED_ELEMENT__LABLS:
				return getLabls();
			case DiagraphPackage.DLABELED_ELEMENT__EXPRESSION:
				return getExpression();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case DiagraphPackage.DLABELED_ELEMENT__ECLAZ:
				setEClaz((EClass)newValue);
				return;
			case DiagraphPackage.DLABELED_ELEMENT__DLABELS:
				getDlabels().clear();
				getDlabels().addAll((Collection<? extends DLabel>)newValue);
				return;
			case DiagraphPackage.DLABELED_ELEMENT__LABLS:
				getLabls().clear();
				getLabls().addAll((Collection<? extends String>)newValue);
				return;
			case DiagraphPackage.DLABELED_ELEMENT__EXPRESSION:
				setExpression((String)newValue);
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
			case DiagraphPackage.DLABELED_ELEMENT__ECLAZ:
				setEClaz((EClass)null);
				return;
			case DiagraphPackage.DLABELED_ELEMENT__DLABELS:
				getDlabels().clear();
				return;
			case DiagraphPackage.DLABELED_ELEMENT__LABLS:
				getLabls().clear();
				return;
			case DiagraphPackage.DLABELED_ELEMENT__EXPRESSION:
				setExpression(EXPRESSION_EDEFAULT);
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
			case DiagraphPackage.DLABELED_ELEMENT__ECLAZ:
				return eClaz != null;
			case DiagraphPackage.DLABELED_ELEMENT__DLABELS:
				return dlabels != null && !dlabels.isEmpty();
			case DiagraphPackage.DLABELED_ELEMENT__LABLS:
				return labls != null && !labls.isEmpty();
			case DiagraphPackage.DLABELED_ELEMENT__EXPRESSION:
				return EXPRESSION_EDEFAULT == null ? expression != null : !EXPRESSION_EDEFAULT.equals(expression);
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
		result.append(" (labls: ");
		result.append(labls);
		result.append(", expression: ");
		result.append(expression);
		result.append(')');
		return result.toString();
	}

} //DLabeledElementImpl
