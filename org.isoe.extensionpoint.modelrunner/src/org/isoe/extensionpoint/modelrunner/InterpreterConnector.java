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
package org.isoe.extensionpoint.modelrunner;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;


/**
 *
 * @author "fpfister"
 *
 */
public class InterpreterConnector {


	private static final boolean LOG = false;

	private static final String EXTENSION_POINT = "org.isoe.modelrunner";


	public ModelInterpreter getModelInterpreterService(String language) {
			for (IConfigurationElement cfElement : Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_POINT)) {
			if (cfElement.getAttribute("class") != null) {
				try {
					Object o = cfElement.createExecutableExtension("class");
					if (o instanceof ModelInterpreter){
						ModelInterpreter result=(ModelInterpreter) o;
						if (!result.isStub() && result.getLanguage().equals(language)){
						   if (LOG)
								  System.out.println(result.getClass().getName());
						   return result;
						}
					}
				} catch (org.eclipse.core.runtime.CoreException e) {
					//org.eclipse.core.runtime.CoreException: Plug-in "org.isoe.extensionpoint.modelrunner" was unable to instantiate class "org.isoe.extensionpoint.modelrunner.ModelInterpreterStub".
					if (e.toString().contains("was unable to instantiate class") && (e.toString().endsWith("Stub\".") ))
					   System.err.println("unable to instantiate stub");
					else
						e.printStackTrace();
				}
			}
		}
		 if (LOG)
			  System.out.println(" no extension for "+EXTENSION_POINT+" (ModelInterpreter) found; does your extension contribute with a plugin.xml ?");
		return null;
	}

	//example getModelLauncherService("my.kermeta.fsm.launch")
	public ModelLauncher getModelLauncherService_(String configurationType) {
		for (IConfigurationElement cfElement : Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_POINT)) {
			if (cfElement.getAttribute("class") != null) {
				try {
					Object o = cfElement.createExecutableExtension("class");
					if (o instanceof ModelLauncher){
						ModelLauncher result=(ModelLauncher) o;
						if (!result.isStub()  && result.getConfigurationType().equals(configurationType)){//
							   if (LOG)
								  System.out.println(result.getClass().getName());
						   return result;
						}
					}
				} catch (org.eclipse.core.runtime.CoreException e) {
					e.printStackTrace();
				}
			}
		}
		 if (LOG)
			  System.out.println(" no extension for "+EXTENSION_POINT+" (ModelLauncher) found; does your extension contribute with a plugin.xml ?");
		return null;
	}



}
