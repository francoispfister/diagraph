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
package org.isoe.diagraph.diagraphviz.dotifiers;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;


/**
 * 
 * @author pfister
 *
 */
public class Utils {
	

	private final static String VISIBLE_ECORE_CLASSES[] = { "EClass", "EReference", "EAttribute" };

	public static boolean isEcoreClass(EObject eobj){
		return (eobj instanceof EClass) && (((EClass) eobj).getEPackage()== EcorePackage.eINSTANCE);
    }
	

	
	public static boolean isEcoreObject(EObject eobj){
		return (eobj instanceof EClassifier) && ((EClassifier) eobj).getEPackage().getName().equals("ecore");
    }
	
	

	
	public static boolean isVisibleEcoreObject(EObject eobj){
		boolean result =isEcoreObject(eobj) && isVisibleEcore(eobj);
		//if (eobj.eClass().getName().equals("EReference"))
			//result = false;
		return result;
    }
		


	
	
	public static boolean isVisibleEcore(EObject eObject) {
		if (isEcoreObject(eObject)){
			EClassifier eclaz = (EClassifier) eObject;
			for (String ecoreclass : VISIBLE_ECORE_CLASSES) {
				if (ecoreclass.equals(eclaz.getName()))
					return true;
			}	
		}
		return false;
	}
	
	
}
