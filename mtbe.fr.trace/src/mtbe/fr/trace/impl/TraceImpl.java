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

import java.util.Collection;

import mtbe.fr.trace.Trace;
import mtbe.fr.trace.TraceLink;
import mtbe.fr.trace.TracePackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Trace</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link mtbe.fr.trace.impl.TraceImpl#getTraces <em>Traces</em>}</li>
 *   <li>{@link mtbe.fr.trace.impl.TraceImpl#getSourceModel <em>Source Model</em>}</li>
 *   <li>{@link mtbe.fr.trace.impl.TraceImpl#getTargetModel <em>Target Model</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TraceImpl extends EObjectImpl implements Trace {
	/**
	 * The cached value of the '{@link #getTraces() <em>Traces</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTraces()
	 * @generated
	 * @ordered
	 */
	protected EList traces;

	/**
	 * The cached value of the '{@link #getSourceModel() <em>Source Model</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceModel()
	 * @generated
	 * @ordered
	 */
	protected EObject sourceModel;

	/**
	 * The cached value of the '{@link #getTargetModel() <em>Target Model</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetModel()
	 * @generated
	 * @ordered
	 */
	protected EObject targetModel;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TraceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return TracePackage.Literals.TRACE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<TraceLink> getTraces() {
		if (traces == null) {
			traces = new EObjectContainmentEList(TraceLink.class, this, TracePackage.TRACE__TRACES);
		}
		return traces;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getSourceModel() {
		if (sourceModel != null && sourceModel.eIsProxy()) {
			InternalEObject oldSourceModel = (InternalEObject)sourceModel;
			sourceModel = eResolveProxy(oldSourceModel);
			if (sourceModel != oldSourceModel) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TracePackage.TRACE__SOURCE_MODEL, oldSourceModel, sourceModel));
			}
		}
		return sourceModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetSourceModel() {
		return sourceModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSourceModel(EObject newSourceModel) {
		EObject oldSourceModel = sourceModel;
		sourceModel = newSourceModel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TracePackage.TRACE__SOURCE_MODEL, oldSourceModel, sourceModel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getTargetModel() {
		if (targetModel != null && targetModel.eIsProxy()) {
			InternalEObject oldTargetModel = (InternalEObject)targetModel;
			targetModel = eResolveProxy(oldTargetModel);
			if (targetModel != oldTargetModel) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TracePackage.TRACE__TARGET_MODEL, oldTargetModel, targetModel));
			}
		}
		return targetModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetTargetModel() {
		return targetModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTargetModel(EObject newTargetModel) {
		EObject oldTargetModel = targetModel;
		targetModel = newTargetModel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TracePackage.TRACE__TARGET_MODEL, oldTargetModel, targetModel));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	public EList<EObject> getAllMappedObjects() {
		EList<EObject> allMappedObjects = new BasicEList<EObject>();
		for (TraceLink link : getTraces()) {
			allMappedObjects.add((EObject) link.getSource());
			allMappedObjects.add((EObject) link.getTarget());
		}
		return allMappedObjects;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TracePackage.TRACE__TRACES:
				return ((InternalEList)getTraces()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TracePackage.TRACE__TRACES:
				return getTraces();
			case TracePackage.TRACE__SOURCE_MODEL:
				if (resolve) return getSourceModel();
				return basicGetSourceModel();
			case TracePackage.TRACE__TARGET_MODEL:
				if (resolve) return getTargetModel();
				return basicGetTargetModel();
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
			case TracePackage.TRACE__TRACES:
				getTraces().clear();
				getTraces().addAll((Collection)newValue);
				return;
			case TracePackage.TRACE__SOURCE_MODEL:
				setSourceModel((EObject)newValue);
				return;
			case TracePackage.TRACE__TARGET_MODEL:
				setTargetModel((EObject)newValue);
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
			case TracePackage.TRACE__TRACES:
				getTraces().clear();
				return;
			case TracePackage.TRACE__SOURCE_MODEL:
				setSourceModel((EObject)null);
				return;
			case TracePackage.TRACE__TARGET_MODEL:
				setTargetModel((EObject)null);
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
			case TracePackage.TRACE__TRACES:
				return traces != null && !traces.isEmpty();
			case TracePackage.TRACE__SOURCE_MODEL:
				return sourceModel != null;
			case TracePackage.TRACE__TARGET_MODEL:
				return targetModel != null;
		}
		return super.eIsSet(featureID);
	}

} //TraceImpl
