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
package org.isoe.extensionpoint.generator;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.isoe.extensionpoint.generator.ISyntaxInference;


public class SyntaxGeneratorConnector {


	private static final String EXTENSION_NAME = "org.isoe.diagraph.syntaxgenerator";
	private static final boolean LOG = false;

	public ISyntaxInference getSyntaxInferenceService() {
		for (IConfigurationElement cfElement : Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_NAME)) {
			if (cfElement.getAttribute("class") != null) {
				try {
					  Object found =  cfElement.createExecutableExtension("class");
					  if (found instanceof ISyntaxInference){
						  ISyntaxInference service = (ISyntaxInference) found;
						  if (!service.isStub()){
				            if (LOG)
					          System.out.println("SyntaxInference "+service.getClass().getName());
				            return service;
						  }
					  }
				} catch (org.eclipse.core.runtime.CoreException e) {
					e.printStackTrace();
				}
			}
		}
		 if (LOG){
		  System.out.println(" no SyntaxInference for "+EXTENSION_NAME+" found; does your service contribute to the extension with a plugin.xml ?");
		  System.out.println(" using a dummy stub for SyntaxInference");
		 }
		 throw new RuntimeException("no syntaxInference");
	}


	public ISyntaxRefactoring getSyntaxRefactoringService() {
		for (IConfigurationElement cfElement : Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_NAME)) {
			if (cfElement.getAttribute("class") != null) {
				try {
					  Object found =  cfElement.createExecutableExtension("class");
					  if (found instanceof ISyntaxRefactoring){
						  ISyntaxRefactoring service = (ISyntaxRefactoring) found;
						  if (!service.isStub()){
				            if (LOG)
					          System.out.println("SyntaxRefactoring "+service.getClass().getName());
				            return service;
						  }
					  }
				} catch (org.eclipse.core.runtime.CoreException e) {
					e.printStackTrace();
				}
			}
		}
		 if (LOG){
		  System.out.println(" no SyntaxRefactoring for "+EXTENSION_NAME+" found; does your service contribute to the extension with a plugin.xml ?");
		  System.out.println(" using a dummy stub for SyntaxRefactoring");
		 }
		 throw new RuntimeException("no SyntaxRefactoring");
	}


}
