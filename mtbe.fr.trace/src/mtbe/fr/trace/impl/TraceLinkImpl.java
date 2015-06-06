/**
 * Copyright (c) 2014 Laboratoire de Genie Informatique et Ingenierie de Production - Ecole des Mines d'Ales
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Francois Pfister (ISOE-LGI2P) - initial API and implementation
 */
package mtbe.fr.trace.impl;

import mtbe.fr.trace.TraceLink;
import mtbe.fr.trace.TracePackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.URI;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Link</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link mtbe.fr.trace.impl.TraceLinkImpl#getName <em>Name</em>}</li>
 *   <li>{@link mtbe.fr.trace.impl.TraceLinkImpl#getSimilarity <em>Similarity</em>}</li>
 *   <li>{@link mtbe.fr.trace.impl.TraceLinkImpl#getRequiredSimilarity <em>Required Similarity</em>}</li>
 *   <li>{@link mtbe.fr.trace.impl.TraceLinkImpl#getRationale <em>Rationale</em>}</li>
 *   <li>{@link mtbe.fr.trace.impl.TraceLinkImpl#getSimilarityMethod <em>Similarity Method</em>}</li>
 *   <li>{@link mtbe.fr.trace.impl.TraceLinkImpl#getSource <em>Source</em>}</li>
 *   <li>{@link mtbe.fr.trace.impl.TraceLinkImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link mtbe.fr.trace.impl.TraceLinkImpl#getTargetValue <em>Target Value</em>}</li>
 *   <li>{@link mtbe.fr.trace.impl.TraceLinkImpl#getSourceValue <em>Source Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TraceLinkImpl extends EObjectImpl implements TraceLink {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getSimilarity() <em>Similarity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSimilarity()
	 * @generated
	 * @ordered
	 */
	protected static final int SIMILARITY_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getSimilarity() <em>Similarity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSimilarity()
	 * @generated
	 * @ordered
	 */
	protected int similarity = SIMILARITY_EDEFAULT;

	/**
	 * The default value of the '{@link #getRequiredSimilarity() <em>Required Similarity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRequiredSimilarity()
	 * @generated
	 * @ordered
	 */
	protected static final int REQUIRED_SIMILARITY_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getRequiredSimilarity() <em>Required Similarity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRequiredSimilarity()
	 * @generated
	 * @ordered
	 */
	protected int requiredSimilarity = REQUIRED_SIMILARITY_EDEFAULT;

	/**
	 * The default value of the '{@link #getRationale() <em>Rationale</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRationale()
	 * @generated
	 * @ordered
	 */
	protected static final String RATIONALE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRationale() <em>Rationale</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRationale()
	 * @generated
	 * @ordered
	 */
	protected String rationale = RATIONALE_EDEFAULT;

	/**
	 * The default value of the '{@link #getSimilarityMethod() <em>Similarity Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSimilarityMethod()
	 * @generated
	 * @ordered
	 */
	protected static final int SIMILARITY_METHOD_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getSimilarityMethod() <em>Similarity Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSimilarityMethod()
	 * @generated
	 * @ordered
	 */
	protected int similarityMethod = SIMILARITY_METHOD_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSource() <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected EObject source;

	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected EObject target;

	/**
	 * The default value of the '{@link #getTargetValue() <em>Target Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetValue()
	 * @generated
	 * @ordered
	 */
	protected static final String TARGET_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTargetValue() <em>Target Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetValue()
	 * @generated
	 * @ordered
	 */
	protected String targetValue = TARGET_VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getSourceValue() <em>Source Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceValue()
	 * @generated
	 * @ordered
	 */
	protected static final String SOURCE_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSourceValue() <em>Source Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceValue()
	 * @generated
	 * @ordered
	 */
	protected String sourceValue = SOURCE_VALUE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TraceLinkImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return TracePackage.Literals.TRACE_LINK;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TracePackage.TRACE_LINK__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getSimilarity() {
		return similarity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSimilarity(int newSimilarity) {
		int oldSimilarity = similarity;
		similarity = newSimilarity;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TracePackage.TRACE_LINK__SIMILARITY, oldSimilarity, similarity));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getRequiredSimilarity() {
		return requiredSimilarity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRequiredSimilarity(int newRequiredSimilarity) {
		int oldRequiredSimilarity = requiredSimilarity;
		requiredSimilarity = newRequiredSimilarity;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TracePackage.TRACE_LINK__REQUIRED_SIMILARITY, oldRequiredSimilarity, requiredSimilarity));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRationale() {
		return rationale;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRationale(String newRationale) {
		String oldRationale = rationale;
		rationale = newRationale;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TracePackage.TRACE_LINK__RATIONALE, oldRationale, rationale));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getSimilarityMethod() {
		return similarityMethod;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSimilarityMethod(int newSimilarityMethod) {
		int oldSimilarityMethod = similarityMethod;
		similarityMethod = newSimilarityMethod;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TracePackage.TRACE_LINK__SIMILARITY_METHOD, oldSimilarityMethod, similarityMethod));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getSource() {
		if (source != null && source.eIsProxy()) {
			InternalEObject oldSource = (InternalEObject)source;
			source = eResolveProxy(oldSource);
			if (source != oldSource) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TracePackage.TRACE_LINK__SOURCE, oldSource, source));
			}
		}
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetSource() {
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSource(EObject newSource) {
		EObject oldSource = source;
		source = newSource;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TracePackage.TRACE_LINK__SOURCE, oldSource, source));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getTarget() {
		if (target != null && target.eIsProxy()) {
			InternalEObject oldTarget = (InternalEObject)target;
			target = eResolveProxy(oldTarget);
			if (target != oldTarget) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TracePackage.TRACE_LINK__TARGET, oldTarget, target));
			}
		}
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetTarget() {
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTarget(EObject newTarget) {
		EObject oldTarget = target;
		target = newTarget;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TracePackage.TRACE_LINK__TARGET, oldTarget, target));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTargetValue() {
		return targetValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTargetValue(String newTargetValue) {
		String oldTargetValue = targetValue;
		targetValue = newTargetValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TracePackage.TRACE_LINK__TARGET_VALUE, oldTargetValue, targetValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSourceValue() {
		return sourceValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSourceValue(String newSourceValue) {
		String oldSourceValue = sourceValue;
		sourceValue = newSourceValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TracePackage.TRACE_LINK__SOURCE_VALUE, oldSourceValue, sourceValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public boolean sameAs(TraceLink tracelink) {
		URI thisSource=EcoreUtil.getURI((EObject) getSource());
		URI thisTarget=EcoreUtil.getURI((EObject) getTarget());
		URI tlSource=EcoreUtil.getURI((EObject) tracelink.getSource());
		URI tlTarget=EcoreUtil.getURI((EObject) tracelink.getTarget());
		return (thisSource.equals(tlSource)&&thisTarget.equals(tlTarget))||(thisSource.equals(tlTarget)&&thisTarget.equals(tlSource));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TracePackage.TRACE_LINK__NAME:
				return getName();
			case TracePackage.TRACE_LINK__SIMILARITY:
				return new Integer(getSimilarity());
			case TracePackage.TRACE_LINK__REQUIRED_SIMILARITY:
				return new Integer(getRequiredSimilarity());
			case TracePackage.TRACE_LINK__RATIONALE:
				return getRationale();
			case TracePackage.TRACE_LINK__SIMILARITY_METHOD:
				return new Integer(getSimilarityMethod());
			case TracePackage.TRACE_LINK__SOURCE:
				if (resolve) return getSource();
				return basicGetSource();
			case TracePackage.TRACE_LINK__TARGET:
				if (resolve) return getTarget();
				return basicGetTarget();
			case TracePackage.TRACE_LINK__TARGET_VALUE:
				return getTargetValue();
			case TracePackage.TRACE_LINK__SOURCE_VALUE:
				return getSourceValue();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case TracePackage.TRACE_LINK__NAME:
				setName((String)newValue);
				return;
			case TracePackage.TRACE_LINK__SIMILARITY:
				setSimilarity(((Integer)newValue).intValue());
				return;
			case TracePackage.TRACE_LINK__REQUIRED_SIMILARITY:
				setRequiredSimilarity(((Integer)newValue).intValue());
				return;
			case TracePackage.TRACE_LINK__RATIONALE:
				setRationale((String)newValue);
				return;
			case TracePackage.TRACE_LINK__SIMILARITY_METHOD:
				setSimilarityMethod(((Integer)newValue).intValue());
				return;
			case TracePackage.TRACE_LINK__SOURCE:
				setSource((EObject)newValue);
				return;
			case TracePackage.TRACE_LINK__TARGET:
				setTarget((EObject)newValue);
				return;
			case TracePackage.TRACE_LINK__TARGET_VALUE:
				setTargetValue((String)newValue);
				return;
			case TracePackage.TRACE_LINK__SOURCE_VALUE:
				setSourceValue((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(int featureID) {
		switch (featureID) {
			case TracePackage.TRACE_LINK__NAME:
				setName(NAME_EDEFAULT);
				return;
			case TracePackage.TRACE_LINK__SIMILARITY:
				setSimilarity(SIMILARITY_EDEFAULT);
				return;
			case TracePackage.TRACE_LINK__REQUIRED_SIMILARITY:
				setRequiredSimilarity(REQUIRED_SIMILARITY_EDEFAULT);
				return;
			case TracePackage.TRACE_LINK__RATIONALE:
				setRationale(RATIONALE_EDEFAULT);
				return;
			case TracePackage.TRACE_LINK__SIMILARITY_METHOD:
				setSimilarityMethod(SIMILARITY_METHOD_EDEFAULT);
				return;
			case TracePackage.TRACE_LINK__SOURCE:
				setSource((EObject)null);
				return;
			case TracePackage.TRACE_LINK__TARGET:
				setTarget((EObject)null);
				return;
			case TracePackage.TRACE_LINK__TARGET_VALUE:
				setTargetValue(TARGET_VALUE_EDEFAULT);
				return;
			case TracePackage.TRACE_LINK__SOURCE_VALUE:
				setSourceValue(SOURCE_VALUE_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case TracePackage.TRACE_LINK__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case TracePackage.TRACE_LINK__SIMILARITY:
				return similarity != SIMILARITY_EDEFAULT;
			case TracePackage.TRACE_LINK__REQUIRED_SIMILARITY:
				return requiredSimilarity != REQUIRED_SIMILARITY_EDEFAULT;
			case TracePackage.TRACE_LINK__RATIONALE:
				return RATIONALE_EDEFAULT == null ? rationale != null : !RATIONALE_EDEFAULT.equals(rationale);
			case TracePackage.TRACE_LINK__SIMILARITY_METHOD:
				return similarityMethod != SIMILARITY_METHOD_EDEFAULT;
			case TracePackage.TRACE_LINK__SOURCE:
				return source != null;
			case TracePackage.TRACE_LINK__TARGET:
				return target != null;
			case TracePackage.TRACE_LINK__TARGET_VALUE:
				return TARGET_VALUE_EDEFAULT == null ? targetValue != null : !TARGET_VALUE_EDEFAULT.equals(targetValue);
			case TracePackage.TRACE_LINK__SOURCE_VALUE:
				return SOURCE_VALUE_EDEFAULT == null ? sourceValue != null : !SOURCE_VALUE_EDEFAULT.equals(sourceValue);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", similarity: ");
		result.append(similarity);
		result.append(", requiredSimilarity: ");
		result.append(requiredSimilarity);
		result.append(", rationale: ");
		result.append(rationale);
		result.append(", similarityMethod: ");
		result.append(similarityMethod);
		result.append(", targetValue: ");
		result.append(targetValue);
		result.append(", sourceValue: ");
		result.append(sourceValue);
		result.append(')');
		return result.toString();
	}

} //TraceLinkImpl
