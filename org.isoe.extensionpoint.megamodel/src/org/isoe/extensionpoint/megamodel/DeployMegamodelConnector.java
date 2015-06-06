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
package org.isoe.extensionpoint.megamodel;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

/**
 *
 * @author "François.Pfister francois.pfister@gmail.com"
 *
 */
public class DeployMegamodelConnector {
	private static final boolean LOG = false;
	private static final String EXTENSION_POINT = "org.isoe.diagraph.deploymegamodel";

	public IModelDeployService getModelDeployService() {
		for (IConfigurationElement cfElement : Platform.getExtensionRegistry()
				.getConfigurationElementsFor(EXTENSION_POINT)) {
			if (cfElement.getAttribute("class") != null) {
				try {
					IModelDeployService modelDeployService = null;
					Object mds = cfElement.createExecutableExtension("class");
					if (mds instanceof IModelDeployService)
						modelDeployService = (IModelDeployService) mds;
					if (modelDeployService != null) {
						if (!modelDeployService.isStub()){
						  if (LOG)
							System.out.println(modelDeployService.getClass()
									.getName());
						  return modelDeployService;
						}
					}
				} catch (org.eclipse.core.runtime.CoreException e) {
					e.printStackTrace();
				}
			}
		}
		if (LOG)
			System.out
					.println(" no extension modelDeployService for "
							+ EXTENSION_POINT
							+ " found; does your extension contribute with a plugin.xml ?");
		return null;
	}

	public IMegamodelDeployService getMegamodelDeployService() {
		for (IConfigurationElement cfElement : Platform.getExtensionRegistry()
				.getConfigurationElementsFor(EXTENSION_POINT)) {
			if (cfElement.getAttribute("class") != null) {
				IMegamodelDeployService megamodelDeployService = null;
				try {
					Object mds = cfElement.createExecutableExtension("class");
					if (mds instanceof IMegamodelDeployService)
						megamodelDeployService = (IMegamodelDeployService) mds;
					if (megamodelDeployService != null) {
						if (!megamodelDeployService.isStub()){
						if (LOG)
							System.out.println(megamodelDeployService
									.getClass().getName());
						  return megamodelDeployService;
						}
					}
				} catch (org.eclipse.core.runtime.CoreException e) {
					e.printStackTrace();
				}
			}
		}

		if (LOG)
			System.out
					.println(" no megamodelDeployService for "
							+ EXTENSION_POINT
							+ " found; does your extension contribute with a plugin.xml ?");
		return null;
	}


	public IMegamodelManager getMegamodelManager() {
		for (IConfigurationElement cfElement : Platform.getExtensionRegistry()
				.getConfigurationElementsFor(EXTENSION_POINT)) {
			if (cfElement.getAttribute("class") != null) {
				IMegamodelManager megamodelManager = null;
				try {
					Object mds = cfElement.createExecutableExtension("class");
					if (mds instanceof IMegamodelManager)
						megamodelManager = (IMegamodelManager) mds;
					if (megamodelManager != null) {
						if (!megamodelManager.isStub()){
						  if (LOG)
							System.out.println(megamodelManager
									.getClass().getName());
						  return megamodelManager;
						}
					}
				} catch (org.eclipse.core.runtime.CoreException e) {
					e.printStackTrace();
				}
			}
		}

		if (LOG)
			System.out
					.println(" no MegamodelManager for "
							+ EXTENSION_POINT
							+ " found; does your extension contribute with a plugin.xml ?");
		throw new RuntimeException("no MegamodelManager");
	}

}
