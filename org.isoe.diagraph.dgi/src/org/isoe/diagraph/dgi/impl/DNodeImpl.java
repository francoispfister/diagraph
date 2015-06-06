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
package org.isoe.diagraph.dgi.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.isoe.diagraph.dgi.DContainment;
import org.isoe.diagraph.dgi.DElement;
import org.isoe.diagraph.dgi.DLink;
import org.isoe.diagraph.dgi.DNode;
import org.isoe.diagraph.dgi.DPoorReference;
import org.isoe.diagraph.dgi.DgiPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>DNode</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.isoe.diagraph.dgi.impl.DNodeImpl#getDReferences <em>DReferences</em>}</li>
 *   <li>{@link org.isoe.diagraph.dgi.impl.DNodeImpl#getDContainments <em>DContainments</em>}</li>
 *   <li>{@link org.isoe.diagraph.dgi.impl.DNodeImpl#getDLinks <em>DLinks</em>}</li>
 *   <li>{@link org.isoe.diagraph.dgi.impl.DNodeImpl#isRoot <em>Root</em>}</li>
 *   <li>{@link org.isoe.diagraph.dgi.impl.DNodeImpl#isRecursive <em>Recursive</em>}</li>
 *   <li>{@link org.isoe.diagraph.dgi.impl.DNodeImpl#getNavigations <em>Navigations</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DNodeImpl extends DElementImpl implements DNode {
	/**
	 * The cached value of the '{@link #getDReferences() <em>DReferences</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDReferences()
	 * @generated
	 * @ordered
	 */
	protected EList<DPoorReference> dReferences;

	/**
	 * The cached value of the '{@link #getDContainments() <em>DContainments</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDContainments()
	 * @generated
	 * @ordered
	 */
	protected EList<DContainment> dContainments;

	/**
	 * The cached value of the '{@link #getDLinks() <em>DLinks</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDLinks()
	 * @generated
	 * @ordered
	 */
	protected EList<DLink> dLinks;

	/**
	 * The default value of the '{@link #isRoot() <em>Root</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRoot()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ROOT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isRoot() <em>Root</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRoot()
	 * @generated
	 * @ordered
	 */
	protected boolean root = ROOT_EDEFAULT;

	/**
	 * The default value of the '{@link #isRecursive() <em>Recursive</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRecursive()
	 * @generated
	 * @ordered
	 */
	protected static final boolean RECURSIVE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isRecursive() <em>Recursive</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRecursive()
	 * @generated
	 * @ordered
	 */
	protected boolean recursive = RECURSIVE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getNavigations() <em>Navigations</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNavigations()
	 * @generated
	 * @ordered
	 */
	protected EList<String> navigations;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DNodeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DgiPackage.Literals.DNODE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DPoorReference> getDReferences() {
		if (dReferences == null) {
			dReferences = new EObjectResolvingEList<DPoorReference>(DPoorReference.class, this, DgiPackage.DNODE__DREFERENCES);
		}
		return dReferences;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DContainment> getDContainments() {
		if (dContainments == null) {
			dContainments = new EObjectResolvingEList<DContainment>(DContainment.class, this, DgiPackage.DNODE__DCONTAINMENTS);
		}
		return dContainments;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DLink> getDLinks() {
		if (dLinks == null) {
			dLinks = new EObjectResolvingEList<DLink>(DLink.class, this, DgiPackage.DNODE__DLINKS);
		}
		return dLinks;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isRoot() {
		return root;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRoot(boolean newRoot) {
		boolean oldRoot = root;
		root = newRoot;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DgiPackage.DNODE__ROOT, oldRoot, root));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isRecursive() {
		return recursive;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRecursive(boolean newRecursive) {
		boolean oldRecursive = recursive;
		recursive = newRecursive;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DgiPackage.DNODE__RECURSIVE, oldRecursive, recursive));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getNavigations() {
		if (navigations == null) {
			navigations = new EDataTypeUniqueEList<String>(String.class, this, DgiPackage.DNODE__NAVIGATIONS);
		}
		return navigations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void resolveDiagramRecursion() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void addRootChild(DNode node) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void resolveTargetRef() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void logContainments() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String logReferences() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean checkReferences() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void resolveReferenceTargetNodes() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void resolveContainmentSourceAndTargetNodes() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DPoorReference findReference(String name) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DContainment findContainmentByTargetName(String name) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DContainment findContainmentbyName(String name) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference inferContainment(EList<DElement> diagramElements) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DgiPackage.DNODE__DREFERENCES:
				return getDReferences();
			case DgiPackage.DNODE__DCONTAINMENTS:
				return getDContainments();
			case DgiPackage.DNODE__DLINKS:
				return getDLinks();
			case DgiPackage.DNODE__ROOT:
				return isRoot();
			case DgiPackage.DNODE__RECURSIVE:
				return isRecursive();
			case DgiPackage.DNODE__NAVIGATIONS:
				return getNavigations();
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
			case DgiPackage.DNODE__DREFERENCES:
				getDReferences().clear();
				getDReferences().addAll((Collection<? extends DPoorReference>)newValue);
				return;
			case DgiPackage.DNODE__DCONTAINMENTS:
				getDContainments().clear();
				getDContainments().addAll((Collection<? extends DContainment>)newValue);
				return;
			case DgiPackage.DNODE__DLINKS:
				getDLinks().clear();
				getDLinks().addAll((Collection<? extends DLink>)newValue);
				return;
			case DgiPackage.DNODE__ROOT:
				setRoot((Boolean)newValue);
				return;
			case DgiPackage.DNODE__RECURSIVE:
				setRecursive((Boolean)newValue);
				return;
			case DgiPackage.DNODE__NAVIGATIONS:
				getNavigations().clear();
				getNavigations().addAll((Collection<? extends String>)newValue);
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
			case DgiPackage.DNODE__DREFERENCES:
				getDReferences().clear();
				return;
			case DgiPackage.DNODE__DCONTAINMENTS:
				getDContainments().clear();
				return;
			case DgiPackage.DNODE__DLINKS:
				getDLinks().clear();
				return;
			case DgiPackage.DNODE__ROOT:
				setRoot(ROOT_EDEFAULT);
				return;
			case DgiPackage.DNODE__RECURSIVE:
				setRecursive(RECURSIVE_EDEFAULT);
				return;
			case DgiPackage.DNODE__NAVIGATIONS:
				getNavigations().clear();
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
			case DgiPackage.DNODE__DREFERENCES:
				return dReferences != null && !dReferences.isEmpty();
			case DgiPackage.DNODE__DCONTAINMENTS:
				return dContainments != null && !dContainments.isEmpty();
			case DgiPackage.DNODE__DLINKS:
				return dLinks != null && !dLinks.isEmpty();
			case DgiPackage.DNODE__ROOT:
				return root != ROOT_EDEFAULT;
			case DgiPackage.DNODE__RECURSIVE:
				return recursive != RECURSIVE_EDEFAULT;
			case DgiPackage.DNODE__NAVIGATIONS:
				return navigations != null && !navigations.isEmpty();
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
		result.append(" (root: ");
		result.append(root);
		result.append(", recursive: ");
		result.append(recursive);
		result.append(", navigations: ");
		result.append(navigations);
		result.append(')');
		return result.toString();
	}

} //DNodeImpl
