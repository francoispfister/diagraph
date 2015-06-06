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
package org.isoe.extensionpoint.layout;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;


/**
 * 
 * @author "François.Pfister francois.pfister@gmail.com"
 *
 */
public class LayoutConnector {
	
	private static final boolean LOG = false;
	private static final String EXTENSION_POINT ="org.isoe.diagraph.layout";

	public ILayoutService getLayoutService() {
		for (IConfigurationElement cfElement : Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_POINT)) {
			if (cfElement.getAttribute("class") != null) {
				try {
					ILayoutService layoutService = (ILayoutService) cfElement.createExecutableExtension("class");
					if (!layoutService.isStub()){
				      if (LOG)
					     System.out.println(layoutService.getClass().getName());
				      return layoutService;
					}
		
				} catch (org.eclipse.core.runtime.CoreException e) {
					e.printStackTrace();
				}
			}
		}
		 if (LOG)
			  System.out.println(" no extension for "+EXTENSION_POINT+" found; does your extension contribute with a plugin.xml ?");
		return null;
	}
	


}
