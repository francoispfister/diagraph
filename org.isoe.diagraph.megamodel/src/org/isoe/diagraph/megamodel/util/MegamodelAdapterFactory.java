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
package org.isoe.diagraph.megamodel.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.isoe.diagraph.megamodel.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.isoe.diagraph.megamodel.MegamodelPackage
 * @generated
 */
public class MegamodelAdapterFactory extends AdapterFactoryImpl {
   /**
	 * The cached model package.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   protected static MegamodelPackage modelPackage;

   /**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public MegamodelAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = MegamodelPackage.eINSTANCE;
		}
	}

   /**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
    * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
    * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
   @Override
   public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

   /**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   protected MegamodelSwitch<Adapter> modelSwitch =
      new MegamodelSwitch<Adapter>() {
			@Override
			public Adapter caseMegamodel(Megamodel object) {
				return createMegamodelAdapter();
			}
			@Override
			public Adapter caseDsml(Dsml object) {
				return createDsmlAdapter();
			}
			@Override
			public Adapter caseMegamodelElement(MegamodelElement object) {
				return createMegamodelElementAdapter();
			}
			@Override
			public Adapter caseRelatedTo(RelatedTo object) {
				return createRelatedToAdapter();
			}
			@Override
			public Adapter caseModel(Model object) {
				return createModelAdapter();
			}
			@Override
			public Adapter caseNotation(Notation object) {
				return createNotationAdapter();
			}
			@Override
			public Adapter caseNotationDiagram(NotationDiagram object) {
				return createNotationDiagramAdapter();
			}
			@Override
			public Adapter caseNavigation(Navigation object) {
				return createNavigationAdapter();
			}
			@Override
			public Adapter caseDiagram(Diagram object) {
				return createDiagramAdapter();
			}
			@Override
			public Adapter caseEcoreDiagram(EcoreDiagram object) {
				return createEcoreDiagramAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

   /**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
   @Override
   public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


   /**
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.megamodel.Megamodel <em>Megamodel</em>}'.
	 * <!-- begin-user-doc -->
    * This default implementation returns null so that we can easily ignore cases;
    * it's useful to ignore a case when inheritance will catch all the cases anyway.
    * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.megamodel.Megamodel
	 * @generated
	 */
   public Adapter createMegamodelAdapter() {
		return null;
	}

   /**
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.megamodel.Dsml <em>Dsml</em>}'.
	 * <!-- begin-user-doc -->
    * This default implementation returns null so that we can easily ignore cases;
    * it's useful to ignore a case when inheritance will catch all the cases anyway.
    * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.megamodel.Dsml
	 * @generated
	 */
   public Adapter createDsmlAdapter() {
		return null;
	}

   /**
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.megamodel.MegamodelElement <em>Element</em>}'.
	 * <!-- begin-user-doc -->
    * This default implementation returns null so that we can easily ignore cases;
    * it's useful to ignore a case when inheritance will catch all the cases anyway.
    * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.megamodel.MegamodelElement
	 * @generated
	 */
   public Adapter createMegamodelElementAdapter() {
		return null;
	}

   /**
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.megamodel.RelatedTo <em>Related To</em>}'.
	 * <!-- begin-user-doc -->
    * This default implementation returns null so that we can easily ignore cases;
    * it's useful to ignore a case when inheritance will catch all the cases anyway.
    * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.megamodel.RelatedTo
	 * @generated
	 */
   public Adapter createRelatedToAdapter() {
		return null;
	}

   /**
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.megamodel.Model <em>Model</em>}'.
	 * <!-- begin-user-doc -->
    * This default implementation returns null so that we can easily ignore cases;
    * it's useful to ignore a case when inheritance will catch all the cases anyway.
    * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.megamodel.Model
	 * @generated
	 */
   public Adapter createModelAdapter() {
		return null;
	}

   /**
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.megamodel.Notation <em>Notation</em>}'.
	 * <!-- begin-user-doc -->
    * This default implementation returns null so that we can easily ignore cases;
    * it's useful to ignore a case when inheritance will catch all the cases anyway.
    * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.megamodel.Notation
	 * @generated
	 */
   public Adapter createNotationAdapter() {
		return null;
	}

   /**
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.megamodel.NotationDiagram <em>Notation Diagram</em>}'.
	 * <!-- begin-user-doc -->
    * This default implementation returns null so that we can easily ignore cases;
    * it's useful to ignore a case when inheritance will catch all the cases anyway.
    * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.megamodel.NotationDiagram
	 * @generated
	 */
   public Adapter createNotationDiagramAdapter() {
		return null;
	}

   /**
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.megamodel.Navigation <em>Navigation</em>}'.
	 * <!-- begin-user-doc -->
    * This default implementation returns null so that we can easily ignore cases;
    * it's useful to ignore a case when inheritance will catch all the cases anyway.
    * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.megamodel.Navigation
	 * @generated
	 */
   public Adapter createNavigationAdapter() {
		return null;
	}

   /**
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.megamodel.Diagram <em>Diagram</em>}'.
	 * <!-- begin-user-doc -->
    * This default implementation returns null so that we can easily ignore cases;
    * it's useful to ignore a case when inheritance will catch all the cases anyway.
    * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.megamodel.Diagram
	 * @generated
	 */
   public Adapter createDiagramAdapter() {
		return null;
	}

   /**
	 * Creates a new adapter for an object of class '{@link org.isoe.diagraph.megamodel.EcoreDiagram <em>Ecore Diagram</em>}'.
	 * <!-- begin-user-doc -->
    * This default implementation returns null so that we can easily ignore cases;
    * it's useful to ignore a case when inheritance will catch all the cases anyway.
    * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.isoe.diagraph.megamodel.EcoreDiagram
	 * @generated
	 */
   public Adapter createEcoreDiagramAdapter() {
		return null;
	}

   /**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
    * This default implementation returns null.
    * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
   public Adapter createEObjectAdapter() {
		return null;
	}

} //MegamodelAdapterFactory
