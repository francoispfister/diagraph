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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.isoe.diagraph.megamodel.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class MegamodelFactoryImpl extends EFactoryImpl implements MegamodelFactory {
   /**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public static MegamodelFactory init() {
		try {
			MegamodelFactory theMegamodelFactory = (MegamodelFactory)EPackage.Registry.INSTANCE.getEFactory("http://megamodel"); 
			if (theMegamodelFactory != null) {
				return theMegamodelFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new MegamodelFactoryImpl();
	}

   /**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public MegamodelFactoryImpl() {
		super();
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   @Override
   public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case MegamodelPackage.MEGAMODEL: return createMegamodel();
			case MegamodelPackage.DSML: return createDsml();
			case MegamodelPackage.RELATED_TO: return createRelatedTo();
			case MegamodelPackage.MODEL: return createModel();
			case MegamodelPackage.NOTATION: return createNotation();
			case MegamodelPackage.NOTATION_DIAGRAM: return createNotationDiagram();
			case MegamodelPackage.NAVIGATION: return createNavigation();
			case MegamodelPackage.ECORE_DIAGRAM: return createEcoreDiagram();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   @Override
   public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case MegamodelPackage.RELATION:
				return createRelationFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   @Override
   public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case MegamodelPackage.RELATION:
				return convertRelationToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public Megamodel createMegamodel() {
		MegamodelImpl megamodel = new MegamodelImpl();
		return megamodel;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public Dsml createDsml() {
		DsmlImpl dsml = new DsmlImpl();
		return dsml;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public RelatedTo createRelatedTo() {
		RelatedToImpl relatedTo = new RelatedToImpl();
		return relatedTo;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public Model createModel() {
		ModelImpl model = new ModelImpl();
		return model;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public Notation createNotation() {
		NotationImpl notation = new NotationImpl();
		return notation;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public NotationDiagram createNotationDiagram() {
		NotationDiagramImpl notationDiagram = new NotationDiagramImpl();
		return notationDiagram;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public Navigation createNavigation() {
		NavigationImpl navigation = new NavigationImpl();
		return navigation;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public EcoreDiagram createEcoreDiagram() {
		EcoreDiagramImpl ecoreDiagram = new EcoreDiagramImpl();
		return ecoreDiagram;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public Relation createRelationFromString(EDataType eDataType, String initialValue) {
		Relation result = Relation.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public String convertRelationToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public MegamodelPackage getMegamodelPackage() {
		return (MegamodelPackage)getEPackage();
	}

   /**
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
   @Deprecated
   public static MegamodelPackage getPackage() {
		return MegamodelPackage.eINSTANCE;
	}

} //MegamodelFactoryImpl
