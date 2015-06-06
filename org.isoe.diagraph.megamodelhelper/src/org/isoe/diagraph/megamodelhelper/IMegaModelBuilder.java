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
package org.isoe.diagraph.megamodelhelper;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;

/**
 *
 * @author fpfister
 *
 */
public interface IMegaModelBuilder {

   void setup(EPackage pak);
   void done(String projectName);
   void csvTrace(String csv);
   void consolidate(URI repositorypathURI, String sourcePlugin,
		String sourceFolder, String language);//FP140218
 //  void doJob(String[] arguments, String currentLanguage, String currentView,
//		String option, int sender);
   String build(String[] arguments, String currentLanguage, String currentView,
		String option, int sender);
   void saveRepository();

}
