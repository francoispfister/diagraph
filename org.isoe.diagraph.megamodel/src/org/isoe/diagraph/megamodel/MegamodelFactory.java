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

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.isoe.diagraph.megamodel.MegamodelPackage
 * @generated
 */
public interface MegamodelFactory extends EFactory {
   /**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   MegamodelFactory eINSTANCE = org.isoe.diagraph.megamodel.impl.MegamodelFactoryImpl.init();

   /**
	 * Returns a new object of class '<em>Megamodel</em>'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return a new object of class '<em>Megamodel</em>'.
	 * @generated
	 */
   Megamodel createMegamodel();

   /**
	 * Returns a new object of class '<em>Dsml</em>'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return a new object of class '<em>Dsml</em>'.
	 * @generated
	 */
   Dsml createDsml();

   /**
	 * Returns a new object of class '<em>Related To</em>'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return a new object of class '<em>Related To</em>'.
	 * @generated
	 */
   RelatedTo createRelatedTo();

   /**
	 * Returns a new object of class '<em>Model</em>'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return a new object of class '<em>Model</em>'.
	 * @generated
	 */
   Model createModel();

   /**
	 * Returns a new object of class '<em>Notation</em>'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return a new object of class '<em>Notation</em>'.
	 * @generated
	 */
   Notation createNotation();

   /**
	 * Returns a new object of class '<em>Notation Diagram</em>'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return a new object of class '<em>Notation Diagram</em>'.
	 * @generated
	 */
   NotationDiagram createNotationDiagram();

   /**
	 * Returns a new object of class '<em>Navigation</em>'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return a new object of class '<em>Navigation</em>'.
	 * @generated
	 */
   Navigation createNavigation();

   /**
	 * Returns a new object of class '<em>Ecore Diagram</em>'.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return a new object of class '<em>Ecore Diagram</em>'.
	 * @generated
	 */
   EcoreDiagram createEcoreDiagram();

   /**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
   MegamodelPackage getMegamodelPackage();

} //MegamodelFactory
