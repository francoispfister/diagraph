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
package org.isoe.diagraph.internal.api;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;




/**
 * 
 * @author pfister
 *
 */
public interface IMetamodelChecker extends IDiagraphParameters {

	EClass findNamed(EPackage pak, String name, String nameLitteral);

	List<EReference> checkDuplicatReferenceNames();

	List<IReferencePattern> getBadNamePatterns(EPackage metaModel,String[] resourceData);

	List<IReferencePattern> getPoorReferencePatterns(EPackage metaModel,String[] resourceData);


	//List<IAnnotationPattern> getAnnotationLinkPatterns(EPackage mmodel,
	//		String[] resourceData);

	//List<EClass> getAnnotatedClasses(EPackage mmodel);

	//List<IAnnotationPattern> getAnnotationLinkPatterns(EPackage mmodel,String key);
	//List<IAnnotationPattern> getAnnotationPatterns(EPackage mmodel);

	//List<IAnnotationPattern> getAllAnnotationPatterns(EPackage mmodel);

	//List<IAnnotatedEClass> getClassesAnnotated(EPackage mmodel);

	List<IAnnotationPattern>  getAnnotationLinkPatterns(List<IAnnotatedEClass> annotatedClasses);

	List getBadLinkPatterns();


}
