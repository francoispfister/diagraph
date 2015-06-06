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
package org.isoe.diagraph.megamodel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.isoe.diagraph.megamodel.Dsml;
import org.isoe.diagraph.megamodel.EcoreDiagram;
import org.isoe.diagraph.megamodel.MegamodelPackage;
import org.isoe.diagraph.megamodel.Model;
import org.isoe.diagraph.megamodel.Notation;
import org.isoe.diagraph.megamodel.RelatedTo;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Dsml</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.isoe.diagraph.megamodel.impl.DsmlImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.impl.DsmlImpl#getNotationBridge <em>Notation Bridge</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.impl.DsmlImpl#getRelations <em>Relations</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.impl.DsmlImpl#getModels <em>Models</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.impl.DsmlImpl#getNotations <em>Notations</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.impl.DsmlImpl#getKnownAs <em>Known As</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.impl.DsmlImpl#getDocumentation <em>Documentation</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.impl.DsmlImpl#getOrigin <em>Origin</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.impl.DsmlImpl#getProblem <em>Problem</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.impl.DsmlImpl#getContext <em>Context</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.impl.DsmlImpl#getKeywords <em>Keywords</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.impl.DsmlImpl#isBuildIn <em>Build In</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.impl.DsmlImpl#getLeftParentDetails <em>Left Parent Details</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.impl.DsmlImpl#getRightParentDetails <em>Right Parent Details</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.impl.DsmlImpl#getRequireDetails <em>Require Details</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.impl.DsmlImpl#getRelatedDetails <em>Related Details</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.impl.DsmlImpl#getVariantDetails <em>Variant Details</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.impl.DsmlImpl#getRootNotation <em>Root Notation</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.impl.DsmlImpl#getEcoreDiagrams <em>Ecore Diagrams</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DsmlImpl extends MegamodelElementImpl implements Dsml {
   /**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
   protected static final int ID_EDEFAULT = 0;

   /**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
   protected int id = ID_EDEFAULT;

   /**
	 * The cached value of the '{@link #getNotationBridge() <em>Notation Bridge</em>}' reference.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see #getNotationBridge()
	 * @generated
	 * @ordered
	 */
   protected EObject notationBridge;

   /**
	 * The cached value of the '{@link #getRelations() <em>Relations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see #getRelations()
	 * @generated
	 * @ordered
	 */
   protected EList<RelatedTo> relations;

   /**
	 * The cached value of the '{@link #getModels() <em>Models</em>}' containment reference list.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see #getModels()
	 * @generated
	 * @ordered
	 */
   protected EList<Model> models;

   /**
	 * The cached value of the '{@link #getNotations() <em>Notations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see #getNotations()
	 * @generated
	 * @ordered
	 */
   protected EList<Notation> notations;

   /**
	 * The cached value of the '{@link #getKnownAs() <em>Known As</em>}' attribute list.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see #getKnownAs()
	 * @generated
	 * @ordered
	 */
   protected EList<String> knownAs;

   /**
	 * The default value of the '{@link #getDocumentation() <em>Documentation</em>}' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see #getDocumentation()
	 * @generated
	 * @ordered
	 */
   protected static final String DOCUMENTATION_EDEFAULT = null;

   /**
	 * The cached value of the '{@link #getDocumentation() <em>Documentation</em>}' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see #getDocumentation()
	 * @generated
	 * @ordered
	 */
   protected String documentation = DOCUMENTATION_EDEFAULT;

   /**
	 * The default value of the '{@link #getOrigin() <em>Origin</em>}' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see #getOrigin()
	 * @generated
	 * @ordered
	 */
   protected static final String ORIGIN_EDEFAULT = null;

   /**
	 * The cached value of the '{@link #getOrigin() <em>Origin</em>}' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see #getOrigin()
	 * @generated
	 * @ordered
	 */
   protected String origin = ORIGIN_EDEFAULT;

   /**
	 * The default value of the '{@link #getProblem() <em>Problem</em>}' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see #getProblem()
	 * @generated
	 * @ordered
	 */
   protected static final String PROBLEM_EDEFAULT = null;

   /**
	 * The cached value of the '{@link #getProblem() <em>Problem</em>}' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see #getProblem()
	 * @generated
	 * @ordered
	 */
   protected String problem = PROBLEM_EDEFAULT;

   /**
	 * The cached value of the '{@link #getContext() <em>Context</em>}' attribute list.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see #getContext()
	 * @generated
	 * @ordered
	 */
   protected EList<String> context;

   /**
	 * The cached value of the '{@link #getKeywords() <em>Keywords</em>}' attribute list.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see #getKeywords()
	 * @generated
	 * @ordered
	 */
   protected EList<String> keywords;

   /**
	 * The default value of the '{@link #isBuildIn() <em>Build In</em>}' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see #isBuildIn()
	 * @generated
	 * @ordered
	 */
   protected static final boolean BUILD_IN_EDEFAULT = false;

   /**
	 * The cached value of the '{@link #isBuildIn() <em>Build In</em>}' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see #isBuildIn()
	 * @generated
	 * @ordered
	 */
   protected boolean buildIn = BUILD_IN_EDEFAULT;

   /**
	 * The cached value of the '{@link #getLeftParentDetails() <em>Left Parent Details</em>}' attribute list.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see #getLeftParentDetails()
	 * @generated
	 * @ordered
	 */
   protected EList<String> leftParentDetails;

   /**
	 * The cached value of the '{@link #getRightParentDetails() <em>Right Parent Details</em>}' attribute list.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see #getRightParentDetails()
	 * @generated
	 * @ordered
	 */
   protected EList<String> rightParentDetails;

   /**
	 * The cached value of the '{@link #getRequireDetails() <em>Require Details</em>}' attribute list.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see #getRequireDetails()
	 * @generated
	 * @ordered
	 */
   protected EList<String> requireDetails;

   /**
	 * The cached value of the '{@link #getRelatedDetails() <em>Related Details</em>}' attribute list.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see #getRelatedDetails()
	 * @generated
	 * @ordered
	 */
   protected EList<String> relatedDetails;

   /**
	 * The cached value of the '{@link #getVariantDetails() <em>Variant Details</em>}' attribute list.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see #getVariantDetails()
	 * @generated
	 * @ordered
	 */
   protected EList<String> variantDetails;

   /**
	 * The cached value of the '{@link #getRootNotation() <em>Root Notation</em>}' reference.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see #getRootNotation()
	 * @generated
	 * @ordered
	 */
   protected Notation rootNotation;

   /**
	 * The cached value of the '{@link #getEcoreDiagrams() <em>Ecore Diagrams</em>}' containment reference list.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see #getEcoreDiagrams()
	 * @generated
	 * @ordered
	 */
   protected EList<EcoreDiagram> ecoreDiagrams;

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   protected DsmlImpl() {
		super();
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   @Override
   protected EClass eStaticClass() {
		return MegamodelPackage.Literals.DSML;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public int getId() {
		return id;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public void setId(int newId) {
		int oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MegamodelPackage.DSML__ID, oldId, id));
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EObject getNotationBridge() {
		if (notationBridge != null && notationBridge.eIsProxy()) {
			InternalEObject oldNotationBridge = (InternalEObject)notationBridge;
			notationBridge = eResolveProxy(oldNotationBridge);
			if (notationBridge != oldNotationBridge) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MegamodelPackage.DSML__NOTATION_BRIDGE, oldNotationBridge, notationBridge));
			}
		}
		return notationBridge;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EObject basicGetNotationBridge() {
		return notationBridge;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public void setNotationBridge(EObject newNotationBridge) {
		EObject oldNotationBridge = notationBridge;
		notationBridge = newNotationBridge;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MegamodelPackage.DSML__NOTATION_BRIDGE, oldNotationBridge, notationBridge));
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EList<RelatedTo> getRelations() {
		if (relations == null) {
			relations = new EObjectContainmentWithInverseEList<RelatedTo>(RelatedTo.class, this, MegamodelPackage.DSML__RELATIONS, MegamodelPackage.RELATED_TO__FROM);
		}
		return relations;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EList<Model> getModels() {
		if (models == null) {
			models = new EObjectContainmentWithInverseEList<Model>(Model.class, this, MegamodelPackage.DSML__MODELS, MegamodelPackage.MODEL__DSML);
		}
		return models;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EList<Notation> getNotations_() {
		if (notations == null) {
			notations = new EObjectContainmentWithInverseEList<Notation>(Notation.class, this, MegamodelPackage.DSML__NOTATIONS, MegamodelPackage.NOTATION__DSML);
		}
		return notations;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EList<String> getKnownAs() {
		if (knownAs == null) {
			knownAs = new EDataTypeUniqueEList<String>(String.class, this, MegamodelPackage.DSML__KNOWN_AS);
		}
		return knownAs;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public String getDocumentation() {
		return documentation;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public void setDocumentation(String newDocumentation) {
		String oldDocumentation = documentation;
		documentation = newDocumentation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MegamodelPackage.DSML__DOCUMENTATION, oldDocumentation, documentation));
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public String getOrigin() {
		return origin;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public void setOrigin(String newOrigin) {
		String oldOrigin = origin;
		origin = newOrigin;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MegamodelPackage.DSML__ORIGIN, oldOrigin, origin));
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public String getProblem() {
		return problem;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public void setProblem(String newProblem) {
		String oldProblem = problem;
		problem = newProblem;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MegamodelPackage.DSML__PROBLEM, oldProblem, problem));
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EList<String> getContext() {
		if (context == null) {
			context = new EDataTypeUniqueEList<String>(String.class, this, MegamodelPackage.DSML__CONTEXT);
		}
		return context;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EList<String> getKeywords() {
		if (keywords == null) {
			keywords = new EDataTypeUniqueEList<String>(String.class, this, MegamodelPackage.DSML__KEYWORDS);
		}
		return keywords;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public boolean isBuildIn() {
		return buildIn;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public void setBuildIn(boolean newBuildIn) {
		boolean oldBuildIn = buildIn;
		buildIn = newBuildIn;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MegamodelPackage.DSML__BUILD_IN, oldBuildIn, buildIn));
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EList<String> getLeftParentDetails() {
		if (leftParentDetails == null) {
			leftParentDetails = new EDataTypeUniqueEList<String>(String.class, this, MegamodelPackage.DSML__LEFT_PARENT_DETAILS);
		}
		return leftParentDetails;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EList<String> getRightParentDetails() {
		if (rightParentDetails == null) {
			rightParentDetails = new EDataTypeUniqueEList<String>(String.class, this, MegamodelPackage.DSML__RIGHT_PARENT_DETAILS);
		}
		return rightParentDetails;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EList<String> getRequireDetails() {
		if (requireDetails == null) {
			requireDetails = new EDataTypeUniqueEList<String>(String.class, this, MegamodelPackage.DSML__REQUIRE_DETAILS);
		}
		return requireDetails;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EList<String> getRelatedDetails() {
		if (relatedDetails == null) {
			relatedDetails = new EDataTypeUniqueEList<String>(String.class, this, MegamodelPackage.DSML__RELATED_DETAILS);
		}
		return relatedDetails;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EList<String> getVariantDetails() {
		if (variantDetails == null) {
			variantDetails = new EDataTypeUniqueEList<String>(String.class, this, MegamodelPackage.DSML__VARIANT_DETAILS);
		}
		return variantDetails;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public Notation getRootNotation() {
		if (rootNotation != null && rootNotation.eIsProxy()) {
			InternalEObject oldRootNotation = (InternalEObject)rootNotation;
			rootNotation = (Notation)eResolveProxy(oldRootNotation);
			if (rootNotation != oldRootNotation) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MegamodelPackage.DSML__ROOT_NOTATION, oldRootNotation, rootNotation));
			}
		}
		return rootNotation;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public Notation basicGetRootNotation() {
		return rootNotation;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public void setRootNotation(Notation newRootNotation) {
		Notation oldRootNotation = rootNotation;
		rootNotation = newRootNotation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MegamodelPackage.DSML__ROOT_NOTATION, oldRootNotation, rootNotation));
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EList<EcoreDiagram> getEcoreDiagrams() {
		if (ecoreDiagrams == null) {
			ecoreDiagrams = new EObjectContainmentEList<EcoreDiagram>(EcoreDiagram.class, this, MegamodelPackage.DSML__ECORE_DIAGRAMS);
		}
		return ecoreDiagrams;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   @SuppressWarnings("unchecked")
   @Override
   public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MegamodelPackage.DSML__RELATIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getRelations()).basicAdd(otherEnd, msgs);
			case MegamodelPackage.DSML__MODELS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getModels()).basicAdd(otherEnd, msgs);
			case MegamodelPackage.DSML__NOTATIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getNotations_()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   @Override
   public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MegamodelPackage.DSML__RELATIONS:
				return ((InternalEList<?>)getRelations()).basicRemove(otherEnd, msgs);
			case MegamodelPackage.DSML__MODELS:
				return ((InternalEList<?>)getModels()).basicRemove(otherEnd, msgs);
			case MegamodelPackage.DSML__NOTATIONS:
				return ((InternalEList<?>)getNotations_()).basicRemove(otherEnd, msgs);
			case MegamodelPackage.DSML__ECORE_DIAGRAMS:
				return ((InternalEList<?>)getEcoreDiagrams()).basicRemove(otherEnd, msgs);
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
			case MegamodelPackage.DSML__ID:
				return getId();
			case MegamodelPackage.DSML__NOTATION_BRIDGE:
				if (resolve) return getNotationBridge();
				return basicGetNotationBridge();
			case MegamodelPackage.DSML__RELATIONS:
				return getRelations();
			case MegamodelPackage.DSML__MODELS:
				return getModels();
			case MegamodelPackage.DSML__NOTATIONS:
				return getNotations_();
			case MegamodelPackage.DSML__KNOWN_AS:
				return getKnownAs();
			case MegamodelPackage.DSML__DOCUMENTATION:
				return getDocumentation();
			case MegamodelPackage.DSML__ORIGIN:
				return getOrigin();
			case MegamodelPackage.DSML__PROBLEM:
				return getProblem();
			case MegamodelPackage.DSML__CONTEXT:
				return getContext();
			case MegamodelPackage.DSML__KEYWORDS:
				return getKeywords();
			case MegamodelPackage.DSML__BUILD_IN:
				return isBuildIn();
			case MegamodelPackage.DSML__LEFT_PARENT_DETAILS:
				return getLeftParentDetails();
			case MegamodelPackage.DSML__RIGHT_PARENT_DETAILS:
				return getRightParentDetails();
			case MegamodelPackage.DSML__REQUIRE_DETAILS:
				return getRequireDetails();
			case MegamodelPackage.DSML__RELATED_DETAILS:
				return getRelatedDetails();
			case MegamodelPackage.DSML__VARIANT_DETAILS:
				return getVariantDetails();
			case MegamodelPackage.DSML__ROOT_NOTATION:
				if (resolve) return getRootNotation();
				return basicGetRootNotation();
			case MegamodelPackage.DSML__ECORE_DIAGRAMS:
				return getEcoreDiagrams();
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
			case MegamodelPackage.DSML__ID:
				setId((Integer)newValue);
				return;
			case MegamodelPackage.DSML__NOTATION_BRIDGE:
				setNotationBridge((EObject)newValue);
				return;
			case MegamodelPackage.DSML__RELATIONS:
				getRelations().clear();
				getRelations().addAll((Collection<? extends RelatedTo>)newValue);
				return;
			case MegamodelPackage.DSML__MODELS:
				getModels().clear();
				getModels().addAll((Collection<? extends Model>)newValue);
				return;
			case MegamodelPackage.DSML__NOTATIONS:
				getNotations_().clear();
				getNotations_().addAll((Collection<? extends Notation>)newValue);
				return;
			case MegamodelPackage.DSML__KNOWN_AS:
				getKnownAs().clear();
				getKnownAs().addAll((Collection<? extends String>)newValue);
				return;
			case MegamodelPackage.DSML__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case MegamodelPackage.DSML__ORIGIN:
				setOrigin((String)newValue);
				return;
			case MegamodelPackage.DSML__PROBLEM:
				setProblem((String)newValue);
				return;
			case MegamodelPackage.DSML__CONTEXT:
				getContext().clear();
				getContext().addAll((Collection<? extends String>)newValue);
				return;
			case MegamodelPackage.DSML__KEYWORDS:
				getKeywords().clear();
				getKeywords().addAll((Collection<? extends String>)newValue);
				return;
			case MegamodelPackage.DSML__BUILD_IN:
				setBuildIn((Boolean)newValue);
				return;
			case MegamodelPackage.DSML__LEFT_PARENT_DETAILS:
				getLeftParentDetails().clear();
				getLeftParentDetails().addAll((Collection<? extends String>)newValue);
				return;
			case MegamodelPackage.DSML__RIGHT_PARENT_DETAILS:
				getRightParentDetails().clear();
				getRightParentDetails().addAll((Collection<? extends String>)newValue);
				return;
			case MegamodelPackage.DSML__REQUIRE_DETAILS:
				getRequireDetails().clear();
				getRequireDetails().addAll((Collection<? extends String>)newValue);
				return;
			case MegamodelPackage.DSML__RELATED_DETAILS:
				getRelatedDetails().clear();
				getRelatedDetails().addAll((Collection<? extends String>)newValue);
				return;
			case MegamodelPackage.DSML__VARIANT_DETAILS:
				getVariantDetails().clear();
				getVariantDetails().addAll((Collection<? extends String>)newValue);
				return;
			case MegamodelPackage.DSML__ROOT_NOTATION:
				setRootNotation((Notation)newValue);
				return;
			case MegamodelPackage.DSML__ECORE_DIAGRAMS:
				getEcoreDiagrams().clear();
				getEcoreDiagrams().addAll((Collection<? extends EcoreDiagram>)newValue);
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
			case MegamodelPackage.DSML__ID:
				setId(ID_EDEFAULT);
				return;
			case MegamodelPackage.DSML__NOTATION_BRIDGE:
				setNotationBridge((EObject)null);
				return;
			case MegamodelPackage.DSML__RELATIONS:
				getRelations().clear();
				return;
			case MegamodelPackage.DSML__MODELS:
				getModels().clear();
				return;
			case MegamodelPackage.DSML__NOTATIONS:
				getNotations_().clear();
				return;
			case MegamodelPackage.DSML__KNOWN_AS:
				getKnownAs().clear();
				return;
			case MegamodelPackage.DSML__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case MegamodelPackage.DSML__ORIGIN:
				setOrigin(ORIGIN_EDEFAULT);
				return;
			case MegamodelPackage.DSML__PROBLEM:
				setProblem(PROBLEM_EDEFAULT);
				return;
			case MegamodelPackage.DSML__CONTEXT:
				getContext().clear();
				return;
			case MegamodelPackage.DSML__KEYWORDS:
				getKeywords().clear();
				return;
			case MegamodelPackage.DSML__BUILD_IN:
				setBuildIn(BUILD_IN_EDEFAULT);
				return;
			case MegamodelPackage.DSML__LEFT_PARENT_DETAILS:
				getLeftParentDetails().clear();
				return;
			case MegamodelPackage.DSML__RIGHT_PARENT_DETAILS:
				getRightParentDetails().clear();
				return;
			case MegamodelPackage.DSML__REQUIRE_DETAILS:
				getRequireDetails().clear();
				return;
			case MegamodelPackage.DSML__RELATED_DETAILS:
				getRelatedDetails().clear();
				return;
			case MegamodelPackage.DSML__VARIANT_DETAILS:
				getVariantDetails().clear();
				return;
			case MegamodelPackage.DSML__ROOT_NOTATION:
				setRootNotation((Notation)null);
				return;
			case MegamodelPackage.DSML__ECORE_DIAGRAMS:
				getEcoreDiagrams().clear();
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
			case MegamodelPackage.DSML__ID:
				return id != ID_EDEFAULT;
			case MegamodelPackage.DSML__NOTATION_BRIDGE:
				return notationBridge != null;
			case MegamodelPackage.DSML__RELATIONS:
				return relations != null && !relations.isEmpty();
			case MegamodelPackage.DSML__MODELS:
				return models != null && !models.isEmpty();
			case MegamodelPackage.DSML__NOTATIONS:
				return notations != null && !notations.isEmpty();
			case MegamodelPackage.DSML__KNOWN_AS:
				return knownAs != null && !knownAs.isEmpty();
			case MegamodelPackage.DSML__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case MegamodelPackage.DSML__ORIGIN:
				return ORIGIN_EDEFAULT == null ? origin != null : !ORIGIN_EDEFAULT.equals(origin);
			case MegamodelPackage.DSML__PROBLEM:
				return PROBLEM_EDEFAULT == null ? problem != null : !PROBLEM_EDEFAULT.equals(problem);
			case MegamodelPackage.DSML__CONTEXT:
				return context != null && !context.isEmpty();
			case MegamodelPackage.DSML__KEYWORDS:
				return keywords != null && !keywords.isEmpty();
			case MegamodelPackage.DSML__BUILD_IN:
				return buildIn != BUILD_IN_EDEFAULT;
			case MegamodelPackage.DSML__LEFT_PARENT_DETAILS:
				return leftParentDetails != null && !leftParentDetails.isEmpty();
			case MegamodelPackage.DSML__RIGHT_PARENT_DETAILS:
				return rightParentDetails != null && !rightParentDetails.isEmpty();
			case MegamodelPackage.DSML__REQUIRE_DETAILS:
				return requireDetails != null && !requireDetails.isEmpty();
			case MegamodelPackage.DSML__RELATED_DETAILS:
				return relatedDetails != null && !relatedDetails.isEmpty();
			case MegamodelPackage.DSML__VARIANT_DETAILS:
				return variantDetails != null && !variantDetails.isEmpty();
			case MegamodelPackage.DSML__ROOT_NOTATION:
				return rootNotation != null;
			case MegamodelPackage.DSML__ECORE_DIAGRAMS:
				return ecoreDiagrams != null && !ecoreDiagrams.isEmpty();
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
		result.append(" (id: ");
		result.append(id);
		result.append(", knownAs: ");
		result.append(knownAs);
		result.append(", documentation: ");
		result.append(documentation);
		result.append(", origin: ");
		result.append(origin);
		result.append(", problem: ");
		result.append(problem);
		result.append(", context: ");
		result.append(context);
		result.append(", keywords: ");
		result.append(keywords);
		result.append(", buildIn: ");
		result.append(buildIn);
		result.append(", leftParentDetails: ");
		result.append(leftParentDetails);
		result.append(", rightParentDetails: ");
		result.append(rightParentDetails);
		result.append(", requireDetails: ");
		result.append(requireDetails);
		result.append(", relatedDetails: ");
		result.append(relatedDetails);
		result.append(", variantDetails: ");
		result.append(variantDetails);
		result.append(')');
		return result.toString();
	}

} //DsmlImpl
