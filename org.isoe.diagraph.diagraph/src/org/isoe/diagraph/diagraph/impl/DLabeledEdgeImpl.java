/**
 */
package org.isoe.diagraph.diagraph.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.isoe.diagraph.diagraph.DLabel;
import org.isoe.diagraph.diagraph.DLabeledEdge;
import org.isoe.diagraph.diagraph.DLabeledElement;
import org.isoe.diagraph.diagraph.DLineEdge;
import org.isoe.diagraph.diagraph.DNode;
import org.isoe.diagraph.diagraph.DShape;
import org.isoe.diagraph.diagraph.DSimpleEdge;
import org.isoe.diagraph.diagraph.DiagraphPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>DLabeled Edge</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DLabeledEdgeImpl#getEClaz <em>EClaz</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DLabeledEdgeImpl#getDlabels <em>Dlabels</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DLabeledEdgeImpl#getLabls <em>Labls</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DLabeledEdgeImpl#getExpression <em>Expression</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DLabeledEdgeImpl#getSource <em>Source</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DLabeledEdgeImpl#getArrows <em>Arrows</em>}</li>
 *   <li>{@link org.isoe.diagraph.diagraph.impl.DLabeledEdgeImpl#getSourceReference <em>Source Reference</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DLabeledEdgeImpl extends DOwnedEdgeImpl implements DLabeledEdge {
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
	 * The cached value of the '{@link #getSource() <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected DNode source;

	/**
	 * The cached value of the '{@link #getArrows() <em>Arrows</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArrows()
	 * @generated
	 * @ordered
	 */
	protected EList<DShape> arrows;

	/**
	 * The cached value of the '{@link #getSourceReference() <em>Source Reference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceReference()
	 * @generated
	 * @ordered
	 */
	protected EReference sourceReference;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DLabeledEdgeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DiagraphPackage.Literals.DLABELED_EDGE;
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DiagraphPackage.DLABELED_EDGE__ECLAZ, oldEClaz, eClaz));
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
			eNotify(new ENotificationImpl(this, Notification.SET, DiagraphPackage.DLABELED_EDGE__ECLAZ, oldEClaz, eClaz));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DLabel> getDlabels() {
		if (dlabels == null) {
			dlabels = new EObjectContainmentEList<DLabel>(DLabel.class, this, DiagraphPackage.DLABELED_EDGE__DLABELS);
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
			labls = new EDataTypeUniqueEList<String>(String.class, this, DiagraphPackage.DLABELED_EDGE__LABLS);
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
			eNotify(new ENotificationImpl(this, Notification.SET, DiagraphPackage.DLABELED_EDGE__EXPRESSION, oldExpression, expression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DNode getSource() {
		if (source != null && source.eIsProxy()) {
			InternalEObject oldSource = (InternalEObject)source;
			source = (DNode)eResolveProxy(oldSource);
			if (source != oldSource) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DiagraphPackage.DLABELED_EDGE__SOURCE, oldSource, source));
			}
		}
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DNode basicGetSource() {
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSource(DNode newSource) {
		DNode oldSource = source;
		source = newSource;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagraphPackage.DLABELED_EDGE__SOURCE, oldSource, source));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DShape> getArrows() {
		if (arrows == null) {
			arrows = new EDataTypeUniqueEList<DShape>(DShape.class, this, DiagraphPackage.DLABELED_EDGE__ARROWS);
		}
		return arrows;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSourceReference() {
		if (sourceReference != null && sourceReference.eIsProxy()) {
			InternalEObject oldSourceReference = (InternalEObject)sourceReference;
			sourceReference = (EReference)eResolveProxy(oldSourceReference);
			if (sourceReference != oldSourceReference) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DiagraphPackage.DLABELED_EDGE__SOURCE_REFERENCE, oldSourceReference, sourceReference));
			}
		}
		return sourceReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference basicGetSourceReference() {
		return sourceReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSourceReference(EReference newSourceReference) {
		EReference oldSourceReference = sourceReference;
		sourceReference = newSourceReference;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DiagraphPackage.DLABELED_EDGE__SOURCE_REFERENCE, oldSourceReference, sourceReference));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DiagraphPackage.DLABELED_EDGE__DLABELS:
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
			case DiagraphPackage.DLABELED_EDGE__ECLAZ:
				if (resolve) return getEClaz();
				return basicGetEClaz();
			case DiagraphPackage.DLABELED_EDGE__DLABELS:
				return getDlabels();
			case DiagraphPackage.DLABELED_EDGE__LABLS:
				return getLabls();
			case DiagraphPackage.DLABELED_EDGE__EXPRESSION:
				return getExpression();
			case DiagraphPackage.DLABELED_EDGE__SOURCE:
				if (resolve) return getSource();
				return basicGetSource();
			case DiagraphPackage.DLABELED_EDGE__ARROWS:
				return getArrows();
			case DiagraphPackage.DLABELED_EDGE__SOURCE_REFERENCE:
				if (resolve) return getSourceReference();
				return basicGetSourceReference();
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
			case DiagraphPackage.DLABELED_EDGE__ECLAZ:
				setEClaz((EClass)newValue);
				return;
			case DiagraphPackage.DLABELED_EDGE__DLABELS:
				getDlabels().clear();
				getDlabels().addAll((Collection<? extends DLabel>)newValue);
				return;
			case DiagraphPackage.DLABELED_EDGE__LABLS:
				getLabls().clear();
				getLabls().addAll((Collection<? extends String>)newValue);
				return;
			case DiagraphPackage.DLABELED_EDGE__EXPRESSION:
				setExpression((String)newValue);
				return;
			case DiagraphPackage.DLABELED_EDGE__SOURCE:
				setSource((DNode)newValue);
				return;
			case DiagraphPackage.DLABELED_EDGE__ARROWS:
				getArrows().clear();
				getArrows().addAll((Collection<? extends DShape>)newValue);
				return;
			case DiagraphPackage.DLABELED_EDGE__SOURCE_REFERENCE:
				setSourceReference((EReference)newValue);
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
			case DiagraphPackage.DLABELED_EDGE__ECLAZ:
				setEClaz((EClass)null);
				return;
			case DiagraphPackage.DLABELED_EDGE__DLABELS:
				getDlabels().clear();
				return;
			case DiagraphPackage.DLABELED_EDGE__LABLS:
				getLabls().clear();
				return;
			case DiagraphPackage.DLABELED_EDGE__EXPRESSION:
				setExpression(EXPRESSION_EDEFAULT);
				return;
			case DiagraphPackage.DLABELED_EDGE__SOURCE:
				setSource((DNode)null);
				return;
			case DiagraphPackage.DLABELED_EDGE__ARROWS:
				getArrows().clear();
				return;
			case DiagraphPackage.DLABELED_EDGE__SOURCE_REFERENCE:
				setSourceReference((EReference)null);
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
			case DiagraphPackage.DLABELED_EDGE__ECLAZ:
				return eClaz != null;
			case DiagraphPackage.DLABELED_EDGE__DLABELS:
				return dlabels != null && !dlabels.isEmpty();
			case DiagraphPackage.DLABELED_EDGE__LABLS:
				return labls != null && !labls.isEmpty();
			case DiagraphPackage.DLABELED_EDGE__EXPRESSION:
				return EXPRESSION_EDEFAULT == null ? expression != null : !EXPRESSION_EDEFAULT.equals(expression);
			case DiagraphPackage.DLABELED_EDGE__SOURCE:
				return source != null;
			case DiagraphPackage.DLABELED_EDGE__ARROWS:
				return arrows != null && !arrows.isEmpty();
			case DiagraphPackage.DLABELED_EDGE__SOURCE_REFERENCE:
				return sourceReference != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == DLabeledElement.class) {
			switch (derivedFeatureID) {
				case DiagraphPackage.DLABELED_EDGE__ECLAZ: return DiagraphPackage.DLABELED_ELEMENT__ECLAZ;
				case DiagraphPackage.DLABELED_EDGE__DLABELS: return DiagraphPackage.DLABELED_ELEMENT__DLABELS;
				case DiagraphPackage.DLABELED_EDGE__LABLS: return DiagraphPackage.DLABELED_ELEMENT__LABLS;
				case DiagraphPackage.DLABELED_EDGE__EXPRESSION: return DiagraphPackage.DLABELED_ELEMENT__EXPRESSION;
				default: return -1;
			}
		}
		if (baseClass == DSimpleEdge.class) {
			switch (derivedFeatureID) {
				case DiagraphPackage.DLABELED_EDGE__SOURCE: return DiagraphPackage.DSIMPLE_EDGE__SOURCE;
				default: return -1;
			}
		}
		if (baseClass == DLineEdge.class) {
			switch (derivedFeatureID) {
				case DiagraphPackage.DLABELED_EDGE__ARROWS: return DiagraphPackage.DLINE_EDGE__ARROWS;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == DLabeledElement.class) {
			switch (baseFeatureID) {
				case DiagraphPackage.DLABELED_ELEMENT__ECLAZ: return DiagraphPackage.DLABELED_EDGE__ECLAZ;
				case DiagraphPackage.DLABELED_ELEMENT__DLABELS: return DiagraphPackage.DLABELED_EDGE__DLABELS;
				case DiagraphPackage.DLABELED_ELEMENT__LABLS: return DiagraphPackage.DLABELED_EDGE__LABLS;
				case DiagraphPackage.DLABELED_ELEMENT__EXPRESSION: return DiagraphPackage.DLABELED_EDGE__EXPRESSION;
				default: return -1;
			}
		}
		if (baseClass == DSimpleEdge.class) {
			switch (baseFeatureID) {
				case DiagraphPackage.DSIMPLE_EDGE__SOURCE: return DiagraphPackage.DLABELED_EDGE__SOURCE;
				default: return -1;
			}
		}
		if (baseClass == DLineEdge.class) {
			switch (baseFeatureID) {
				case DiagraphPackage.DLINE_EDGE__ARROWS: return DiagraphPackage.DLABELED_EDGE__ARROWS;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(", arrows: ");
		result.append(arrows);
		result.append(')');
		return result.toString();
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

} //DLabeledEdgeImpl
