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
package org.isoe.extensionpoint.languagehandler;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;


/**
 *
 * @author "fpfister"
 *
 */
public class LanguageHandlerConnector {
	private static final boolean LOG = false;
	private static final String EXTENSION_POINT = "org.isoe.extensionpoint.languagehandler";



	public ILanguageHandler getParseService() {
		String signature="languageutils"; //bad tip: if you refactor the class name ...
		for (IConfigurationElement cfElement : Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_POINT)) {
			if (cfElement.getAttribute("class") != null) {
				try {
					ILanguageHandler handler = (ILanguageHandler) cfElement.createExecutableExtension("class");
					 //if (LOG)
				//		    System.out.println(" ?? "+handler.getClass().getName());

					if (!handler.isStub() && handler.getClass().getSimpleName().toLowerCase().contains(signature)){ //TODO be serious
				      if (LOG)
					    System.out.println(" ok: "+handler.getClass().getName());
				       return handler;
					}
				} catch (org.eclipse.core.runtime.CoreException e) {
					e.printStackTrace();
				}
			}
		}
		 if (LOG)
			  System.out.println(" no extension for " + EXTENSION_POINT + "-"+signature+" found; does your extension contribute with a plugin.xml ?");
		 throw new RuntimeException("no LanguageHandler");
	}


	public ILanguageHandler getCloneService() {
		for (IConfigurationElement cfElement : Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_POINT)) {

			String[] names=cfElement.getAttributeNames();
			for (String at : names) {
				System.out.println(at);
			}
			if (cfElement.getAttribute("class") != null) { //FP130624
				try {
					ILanguageHandler handler = (ILanguageHandler) cfElement.createExecutableExtension("class");
					if (!handler.isStub() && handler.getClass().getSimpleName().toLowerCase().contains("cloner")){ //TODO be serious
				      if (LOG)
					    System.out.println(handler.getClass().getName());
				       return handler;
					}
				} catch (org.eclipse.core.runtime.CoreException e) {
					e.printStackTrace();
				}
			}
		}
		 if (LOG)
			  System.out.println(" no extension for " + EXTENSION_POINT + " found; does your extension contribute with a plugin.xml ?");
		 throw new RuntimeException("no LanguageHandler");
	}

}
