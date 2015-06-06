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
package org.isoe.extensionpoint.diagen;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

/**
 *
 * @author fpfister
 *
 */
public class MegamodelConnector {

	private static final boolean LOG = false;
	private static final String EXTENSION_POINT ="org.isoe.extensionpoint.diagen";

	public IMegamodelService getMegamodelService() {
		for (IConfigurationElement cfElement : Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_POINT)) {
			if (cfElement.getAttribute("class") != null) {
				try {
					Object found = cfElement.createExecutableExtension("class");
					if (found instanceof IMegamodelService){
						IMegamodelService megamodelService = (IMegamodelService) found;
						if (!megamodelService.isStub()){
				          if (LOG)
					        System.out.println("found  MegamodelService: "+megamodelService.getClass().getName());
				            return megamodelService;
						} else{
					       // System.out.println("not an operational MegamodelService: "+MegamodelService.getClass().getName());
							//return null;
						}
					}
				} catch (org.eclipse.core.runtime.CoreException e) {
					e.printStackTrace();
				}
			}
		}
		 if (LOG)
			  System.out.println(" no MegamodelService for "+EXTENSION_POINT+" found; does your extension contribute with a plugin.xml ?");
		return null;
	}



}
