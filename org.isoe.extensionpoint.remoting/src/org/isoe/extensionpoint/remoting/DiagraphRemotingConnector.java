 /**
 * Copyright (c) 2014 Laboratoire de Genie Informatique et Ingenierie de Production - Ecole des Mines d'Ales
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Blazo Nastov (ISOE-LGI2P) - initial API and implementation
 */
package org.isoe.extensionpoint.remoting;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

/**
 *
 * @author bnastov
 *
 */
public class DiagraphRemotingConnector {
	//org.isoe.extensionpoint.remoting.DiagraphRemotingConnector

	private static final boolean LOG = false;
	private static final String EXTENSION_POINT = "org.isoe.diagraph.remoting";

	public IDiagraphRemotingService getService() {
		for (IConfigurationElement cfElement : Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_POINT)) {
			if (cfElement.getAttribute("class") != null) {
				try {
					Object found = cfElement.createExecutableExtension("class");
					if (found instanceof IDiagraphRemotingService){
						IDiagraphRemotingService service = (IDiagraphRemotingService) found;
						if (!service.isStub()){
				          if (LOG)
					        System.out.println("found  service: "+service.getClass().getName());
				          return service;
						}
					}
				} catch (org.eclipse.core.runtime.CoreException e) {
					e.printStackTrace();
				}
			}
		}
		 if (LOG){
			  System.out.println("no service for "+EXTENSION_POINT+" found; does your extension contribute with a plugin.xml ?");
			  System.out.println("a non functional stub is used");
		 }
		 throw new RuntimeException(" no DiagraphRemoting");
	}


}
