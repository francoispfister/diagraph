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
package org.isoe.extensionpoint.rules;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

/**
 *
 * @author fpfister
 *
 */
public class SyntaxRulesConnector {
	//org.isoe.extensionpoint.rules.SyntaxRulesFinder
	private static final String EXTENSION_NAME = "org.isoe.diagraph.syntaxrules";
	private static final boolean LOG = false;

	public ISyntaxRules getService() {
		for (IConfigurationElement cfElement : Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_NAME)) {
			if (cfElement.getAttribute("class") != null) {
				try {
					  Object found =  cfElement.createExecutableExtension("class");
					  if (found instanceof ISyntaxRules){
						  ISyntaxRules service = (ISyntaxRules) found;
						  if (!service.isStub()){
				           if (LOG)
					         System.out.println("SyntaxRules "+service.getClass().getName());
				           return service;
						  }
					  }
				} catch (org.eclipse.core.runtime.CoreException e) {
					e.printStackTrace();
				}
			}
		}
		 if (LOG){
		  System.out.println(" no SyntaxRules for "+EXTENSION_NAME+" found; does your service contribute to the extension with a plugin.xml ?");
		  System.out.println(" using a dummy stub for SyntaxRules");
		 }
		 throw new RuntimeException("no SyntaxRules");
	}





}
