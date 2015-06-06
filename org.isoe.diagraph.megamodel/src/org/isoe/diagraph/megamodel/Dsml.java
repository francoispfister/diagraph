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
package org.isoe.diagraph.megamodel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Dsml</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.isoe.diagraph.megamodel.Dsml#getId <em>Id</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.Dsml#getNotationBridge <em>Notation Bridge</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.Dsml#getRelations <em>Relations</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.Dsml#getModels <em>Models</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.Dsml#getNotations <em>Notations</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.Dsml#getKnownAs <em>Known As</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.Dsml#getDocumentation <em>Documentation</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.Dsml#getOrigin <em>Origin</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.Dsml#getProblem <em>Problem</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.Dsml#getContext <em>Context</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.Dsml#getKeywords <em>Keywords</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.Dsml#isBuildIn <em>Build In</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.Dsml#getLeftParentDetails <em>Left Parent Details</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.Dsml#getRightParentDetails <em>Right Parent Details</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.Dsml#getRequireDetails <em>Require Details</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.Dsml#getRelatedDetails <em>Related Details</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.Dsml#getVariantDetails <em>Variant Details</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.Dsml#getRootNotation <em>Root Notation</em>}</li>
 *   <li>{@link org.isoe.diagraph.megamodel.Dsml#getEcoreDiagrams <em>Ecore Diagrams</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.isoe.diagraph.megamodel.MegamodelPackage#getDsml()
 * @model annotation="diagraph node='null' kref\075models='null' kref\075notations='null' kref\075ecoreDiagrams='null'"
 * @generated
 */
public interface Dsml extends MegamodelElement {
   /**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Id</em>' attribute isn't clear,
    * there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(int)
	 * @see org.isoe.diagraph.megamodel.MegamodelPackage#getDsml_Id()
	 * @model
	 * @generated
	 */
   int getId();

   /**
	 * Sets the value of the '{@link org.isoe.diagraph.megamodel.Dsml#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
   void setId(int value);

   /**
	 * Returns the value of the '<em><b>Notation Bridge</b></em>' reference.
	 * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Notation Bridge</em>' reference isn't clear,
    * there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
	 * @return the value of the '<em>Notation Bridge</em>' reference.
	 * @see #setNotationBridge(EObject)
	 * @see org.isoe.diagraph.megamodel.MegamodelPackage#getDsml_NotationBridge()
	 * @model
	 * @generated
	 */
   EObject getNotationBridge();

   /**
	 * Sets the value of the '{@link org.isoe.diagraph.megamodel.Dsml#getNotationBridge <em>Notation Bridge</em>}' reference.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Notation Bridge</em>' reference.
	 * @see #getNotationBridge()
	 * @generated
	 */
   void setNotationBridge(EObject value);

   /**
	 * Returns the value of the '<em><b>Relations</b></em>' containment reference list.
	 * The list contents are of type {@link org.isoe.diagraph.megamodel.RelatedTo}.
	 * It is bidirectional and its opposite is '{@link org.isoe.diagraph.megamodel.RelatedTo#getFrom <em>From</em>}'.
	 * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Relations</em>' containment reference list isn't clear,
    * there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
	 * @return the value of the '<em>Relations</em>' containment reference list.
	 * @see org.isoe.diagraph.megamodel.MegamodelPackage#getDsml_Relations()
	 * @see org.isoe.diagraph.megamodel.RelatedTo#getFrom
	 * @model opposite="from" containment="true"
	 * @generated
	 */
   EList<RelatedTo> getRelations();

   /**
	 * Returns the value of the '<em><b>Models</b></em>' containment reference list.
	 * The list contents are of type {@link org.isoe.diagraph.megamodel.Model}.
	 * It is bidirectional and its opposite is '{@link org.isoe.diagraph.megamodel.Model#getDsml <em>Dsml</em>}'.
	 * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Models</em>' containment reference list isn't clear,
    * there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
	 * @return the value of the '<em>Models</em>' containment reference list.
	 * @see org.isoe.diagraph.megamodel.MegamodelPackage#getDsml_Models()
	 * @see org.isoe.diagraph.megamodel.Model#getDsml
	 * @model opposite="dsml" containment="true"
	 * @generated
	 */
   EList<Model> getModels();

   /**
	 * Returns the value of the '<em><b>Notations</b></em>' containment reference list.
	 * The list contents are of type {@link org.isoe.diagraph.megamodel.Notation}.
	 * It is bidirectional and its opposite is '{@link org.isoe.diagraph.megamodel.Notation#getDsml <em>Dsml</em>}'.
	 * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Notations</em>' containment reference list isn't clear,
    * there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
	 * @return the value of the '<em>Notations</em>' containment reference list.
	 * @see org.isoe.diagraph.megamodel.MegamodelPackage#getDsml_Notations()
	 * @see org.isoe.diagraph.megamodel.Notation#getDsml
	 * @model opposite="dsml" containment="true"
	 * @generated
	 */
   EList<Notation> getNotations_();

   /**
	 * Returns the value of the '<em><b>Known As</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Known As</em>' attribute list isn't clear,
    * there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
	 * @return the value of the '<em>Known As</em>' attribute list.
	 * @see org.isoe.diagraph.megamodel.MegamodelPackage#getDsml_KnownAs()
	 * @model
	 * @generated
	 */
   EList<String> getKnownAs();

   /**
	 * Returns the value of the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Documentation</em>' attribute isn't clear,
    * there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
	 * @return the value of the '<em>Documentation</em>' attribute.
	 * @see #setDocumentation(String)
	 * @see org.isoe.diagraph.megamodel.MegamodelPackage#getDsml_Documentation()
	 * @model
	 * @generated
	 */
   String getDocumentation();

   /**
	 * Sets the value of the '{@link org.isoe.diagraph.megamodel.Dsml#getDocumentation <em>Documentation</em>}' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Documentation</em>' attribute.
	 * @see #getDocumentation()
	 * @generated
	 */
   void setDocumentation(String value);

   /**
	 * Returns the value of the '<em><b>Origin</b></em>' attribute.
	 * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Origin</em>' attribute isn't clear,
    * there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
	 * @return the value of the '<em>Origin</em>' attribute.
	 * @see #setOrigin(String)
	 * @see org.isoe.diagraph.megamodel.MegamodelPackage#getDsml_Origin()
	 * @model
	 * @generated
	 */
   String getOrigin();

   /**
	 * Sets the value of the '{@link org.isoe.diagraph.megamodel.Dsml#getOrigin <em>Origin</em>}' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Origin</em>' attribute.
	 * @see #getOrigin()
	 * @generated
	 */
   void setOrigin(String value);

   /**
	 * Returns the value of the '<em><b>Problem</b></em>' attribute.
	 * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Problem</em>' attribute isn't clear,
    * there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
	 * @return the value of the '<em>Problem</em>' attribute.
	 * @see #setProblem(String)
	 * @see org.isoe.diagraph.megamodel.MegamodelPackage#getDsml_Problem()
	 * @model
	 * @generated
	 */
   String getProblem();

   /**
	 * Sets the value of the '{@link org.isoe.diagraph.megamodel.Dsml#getProblem <em>Problem</em>}' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Problem</em>' attribute.
	 * @see #getProblem()
	 * @generated
	 */
   void setProblem(String value);

   /**
	 * Returns the value of the '<em><b>Context</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Context</em>' attribute list isn't clear,
    * there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
	 * @return the value of the '<em>Context</em>' attribute list.
	 * @see org.isoe.diagraph.megamodel.MegamodelPackage#getDsml_Context()
	 * @model
	 * @generated
	 */
   EList<String> getContext();

   /**
	 * Returns the value of the '<em><b>Keywords</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Keywords</em>' attribute list isn't clear,
    * there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
	 * @return the value of the '<em>Keywords</em>' attribute list.
	 * @see org.isoe.diagraph.megamodel.MegamodelPackage#getDsml_Keywords()
	 * @model
	 * @generated
	 */
   EList<String> getKeywords();

   /**
	 * Returns the value of the '<em><b>Build In</b></em>' attribute.
	 * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Build In</em>' attribute isn't clear,
    * there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
	 * @return the value of the '<em>Build In</em>' attribute.
	 * @see #setBuildIn(boolean)
	 * @see org.isoe.diagraph.megamodel.MegamodelPackage#getDsml_BuildIn()
	 * @model
	 * @generated
	 */
   boolean isBuildIn();

   /**
	 * Sets the value of the '{@link org.isoe.diagraph.megamodel.Dsml#isBuildIn <em>Build In</em>}' attribute.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Build In</em>' attribute.
	 * @see #isBuildIn()
	 * @generated
	 */
   void setBuildIn(boolean value);

   /**
	 * Returns the value of the '<em><b>Left Parent Details</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Left Parent Details</em>' attribute list isn't clear,
    * there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
	 * @return the value of the '<em>Left Parent Details</em>' attribute list.
	 * @see org.isoe.diagraph.megamodel.MegamodelPackage#getDsml_LeftParentDetails()
	 * @model transient="true"
	 * @generated
	 */
   EList<String> getLeftParentDetails();

   /**
	 * Returns the value of the '<em><b>Right Parent Details</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Right Parent Details</em>' attribute list isn't clear,
    * there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
	 * @return the value of the '<em>Right Parent Details</em>' attribute list.
	 * @see org.isoe.diagraph.megamodel.MegamodelPackage#getDsml_RightParentDetails()
	 * @model transient="true"
	 * @generated
	 */
   EList<String> getRightParentDetails();

   /**
	 * Returns the value of the '<em><b>Require Details</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Require Details</em>' attribute list isn't clear,
    * there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
	 * @return the value of the '<em>Require Details</em>' attribute list.
	 * @see org.isoe.diagraph.megamodel.MegamodelPackage#getDsml_RequireDetails()
	 * @model transient="true"
	 * @generated
	 */
   EList<String> getRequireDetails();

   /**
	 * Returns the value of the '<em><b>Related Details</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Related Details</em>' attribute list isn't clear,
    * there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
	 * @return the value of the '<em>Related Details</em>' attribute list.
	 * @see org.isoe.diagraph.megamodel.MegamodelPackage#getDsml_RelatedDetails()
	 * @model transient="true"
	 * @generated
	 */
   EList<String> getRelatedDetails();

   /**
	 * Returns the value of the '<em><b>Variant Details</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Variant Details</em>' attribute list isn't clear,
    * there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
	 * @return the value of the '<em>Variant Details</em>' attribute list.
	 * @see org.isoe.diagraph.megamodel.MegamodelPackage#getDsml_VariantDetails()
	 * @model transient="true"
	 * @generated
	 */
   EList<String> getVariantDetails();

   /**
	 * Returns the value of the '<em><b>Root Notation</b></em>' reference.
	 * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Root Notation</em>' reference isn't clear,
    * there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
	 * @return the value of the '<em>Root Notation</em>' reference.
	 * @see #setRootNotation(Notation)
	 * @see org.isoe.diagraph.megamodel.MegamodelPackage#getDsml_RootNotation()
	 * @model
	 * @generated
	 */
   Notation getRootNotation();

   /**
	 * Sets the value of the '{@link org.isoe.diagraph.megamodel.Dsml#getRootNotation <em>Root Notation</em>}' reference.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Root Notation</em>' reference.
	 * @see #getRootNotation()
	 * @generated
	 */
   void setRootNotation(Notation value);

   /**
	 * Returns the value of the '<em><b>Ecore Diagrams</b></em>' containment reference list.
	 * The list contents are of type {@link org.isoe.diagraph.megamodel.EcoreDiagram}.
	 * <!-- begin-user-doc -->
    * <p>
    * If the meaning of the '<em>Ecore Diagrams</em>' containment reference list isn't clear,
    * there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
	 * @return the value of the '<em>Ecore Diagrams</em>' containment reference list.
	 * @see org.isoe.diagraph.megamodel.MegamodelPackage#getDsml_EcoreDiagrams()
	 * @model containment="true"
	 * @generated
	 */
   EList<EcoreDiagram> getEcoreDiagrams();

} // Dsml
