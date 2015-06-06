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
package org.isoe.extensionpoint.mwb.launcher;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.isoe.extensionpoint.mwb.launcher.IModelWorkbenchLauncher;

/**
 *
 * @author "fpfister"
 *
 */
public class LaucherConnector {


	private static final boolean LOG = false;
	private static final String EXTENSION_POINT = "org.isoe.fwk.eclipse.launcher";


	public IModelWorkbenchLauncher getService() {
		for (IConfigurationElement cfElement : Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_POINT)) {
			if (cfElement.getAttribute("class") != null) {
				try {
					IModelWorkbenchLauncher mwbLauncher = (IModelWorkbenchLauncher) cfElement.createExecutableExtension("class");
					if (!mwbLauncher.isStub()){
				     if (LOG)
					  System.out.println(mwbLauncher.getClass().getName());
				     return mwbLauncher;
					}
				} catch (org.eclipse.core.runtime.CoreException e) {
					e.printStackTrace();
				}
			}
		}
		 if (LOG)
			  System.out.println(" no extension for " + EXTENSION_POINT + " found; does your extension contribute with a plugin.xml ?");
		 throw new RuntimeException("no ModelWorkbenchLauncher");
	}

}
