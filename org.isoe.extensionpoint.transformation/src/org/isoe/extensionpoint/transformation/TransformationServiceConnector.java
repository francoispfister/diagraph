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
package org.isoe.extensionpoint.transformation;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

/**
 * 
 * @author fpfister
 * 
 */
public class TransformationServiceConnector {
//org.isoe.extensionpoint.transformation.TransformationServiceConnector
	private static final boolean LOG = false;
	private static final String EXTENSION_NAME = "org.isoe.transformations";

	public ITransformationService getTransformationService() {
		for (IConfigurationElement cfElement : Platform.getExtensionRegistry()
				.getConfigurationElementsFor(EXTENSION_NAME)) {
			if (cfElement.getAttribute("class") != null) {
				try {
					Object found = cfElement.createExecutableExtension("class");
					if (LOG)
						System.out.println("TransformationService "
								+ found.getClass().getName());
					if (found instanceof ITransformationService) {
						ITransformationService action = (ITransformationService) found;
						if (!action.isStub()){
						  if (LOG)
							System.out.println(action.getClass().getName());
						  return action;
						}
					}
				} catch (org.eclipse.core.runtime.CoreException e) {
					e.printStackTrace();
				}
			}
		}
		if (LOG)
			System.out
					.println(" no TransformationService for "
							+ EXTENSION_NAME
							+ " found; does your action contribute to the extension with a plugin.xml ?");
		return null;
	}

}
