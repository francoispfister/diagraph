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
package org.isoe.extensionpoint.automation;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.isoe.fwk.core.DParams;

/**
 *
 * @author "François.Pfister francois.pfister@gmail.com"
 *
 */
public class AutomationConnector {


	private static final boolean LOG = DParams.Automation_LOG;
	private static final String EXTENSION_POINT ="org.isoe.fwk.automation";

	public IAutomationServer getAutomationService() {
		for (IConfigurationElement cfElement : Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_POINT)) {
			if (cfElement.getAttribute("class") != null) {
				try {
					Object found = cfElement.createExecutableExtension("class");
					if (found instanceof IAutomationServer){
						IAutomationServer automationserver = (IAutomationServer) found;
						if (!automationserver.isStub()){
				          if (LOG)
					        System.out.println("found  automationserver: "+automationserver.getClass().getName());
				            return automationserver;
						} else{
					       // System.out.println("not an operational automationserver: "+automationserver.getClass().getName());
							//return null;
						}
					}
				} catch (org.eclipse.core.runtime.CoreException e) {
					e.printStackTrace();
				}
			}
		}
		 if (LOG)
			  System.out.println(" no automationserver for "+EXTENSION_POINT+" found; does your extension contribute with a plugin.xml ?");
		return null;
	}



	public IAutomationService getAutomationHandler() {
		for (IConfigurationElement cfElement : Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_POINT)) {
			if (cfElement.getAttribute("class") != null) {
				try {
					Object found = cfElement.createExecutableExtension("class");
					if (found instanceof IAutomationService){
						IAutomationService automationHandler = (IAutomationService) found;
						if (!automationHandler.isStub()){
				          if (LOG)
					         System.out.println("found  automationHandler: "+automationHandler.getClass().getName());
				          return automationHandler;
						} else{
						    //System.out.println("found  stub for automationHandler: "+automationHandler.getClass().getName());
					        //return null;
						}
					}
				} catch (org.eclipse.core.runtime.CoreException e) {
					e.printStackTrace();
				}
			}
		}
		 if (LOG)
			  System.out.println(" no automationHandler for "+EXTENSION_POINT+" found; does your extension contribute with a plugin.xml ?");
		throw new RuntimeException("no automationHandler");
	}

}
