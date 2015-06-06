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
package org.isoe.extensionpoint.diagraph.generator;

import java.util.TreeMap;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

/**
 *
 * @author "fpfister"
 *
 */
public class DiagraphGenConnector { //FP130417

	private static final boolean LOG = false;
	private static final String EXTENSION_POINT_ ="org.isoe.extensionpoint.diagraph.generator"; //FP130926x

	public IDiagraphGen getService() {
		for (IConfigurationElement cfElement : Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_POINT_)) {
			if (cfElement.getAttribute("class") != null) {
				try {
					Object found = cfElement.createExecutableExtension("class");
					if (found instanceof IDiagraphGen){
					   IDiagraphGen diagraphGenerator = (IDiagraphGen) found;
					  // if (diagraphGenerator.isFunctional()){
					   if (!diagraphGenerator.isStub()){ //ne pas prendre le stup
				         if (LOG)
					      System.out.println(diagraphGenerator.getClass().getName());
				         return diagraphGenerator;
					   }
					}
				} catch (org.eclipse.core.runtime.CoreException e) {
					e.printStackTrace();
				}
			}
		}
		throw new RuntimeException("No DiagraphGenerator extension found");
	}





}

/*

class PluginUtility {


    public static TreeMap<Integer, AbstractModule> getModuleConfigurations() throws CoreException {
        final TreeMap<Integer, AbstractModule> moduleConfigurations = new TreeMap<Integer, AbstractModule>();
        IExtension[] moduleConfigurationExtensions = Platform.getExtensionRegistry().getExtensionPoint("...id...").getExtensions();
        for (IExtension moduleConfiguration : moduleConfigurationExtensions) {
            for (IConfigurationElement configElement : moduleConfiguration.getConfigurationElements()) {

                AbstractModule module = (AbstractModule) configElement.createExecutableExtension("moduleClassname");
                String priorityAsString = configElement.getAttribute("priority");
                int priority = 0;
                try {
                    priority = Integer.parseInt(priorityAsString);
                } catch (NumberFormatException e) {
                    throw new CoreException(...);
                }

                moduleConfigurations.put(Integer.valueOf(priority), module);
            }
        }
        return moduleConfigurations;
    }*/

