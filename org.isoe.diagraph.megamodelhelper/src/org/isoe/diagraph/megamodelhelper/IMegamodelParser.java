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


import org.eclipse.emf.ecore.EPackage;
//import org.eclipse.gmf.runtime.notation.Diagram;
import org.isoe.extensionpoint.diagen.IMegamodelService;

/**
 *
 * @author fpfister
 *
 */
public interface IMegamodelParser {
   void clearForm(String language,String view);
   void diagraphLanguages(String[] langs);
   void generateLanguage(boolean headless, boolean refreshOnly, Object sender, String language);
   void newParseLanguage_nu();
   IMegamodelService getMegamodelService();
   IMegaModelMan getMegamodelMan();
   String getArtifact();
   EPackage get(String language);
   String getCurrentLanguageName();

}
