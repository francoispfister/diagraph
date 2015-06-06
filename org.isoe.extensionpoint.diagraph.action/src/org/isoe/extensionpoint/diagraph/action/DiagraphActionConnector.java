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
package org.isoe.extensionpoint.diagraph.action;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;


/**
 *
 * @author "fpfister"
 *
 */
public class DiagraphActionConnector { //FP130417

	private static final boolean LOG = false;
	private static final String EXTENSION_POINT_ ="org.isoe.extensionpoint.diagraph.action"; //FP130926x

	public IGenDiagraphServ GenDiagraphAction() {
		for (IConfigurationElement cfElement : Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_POINT_)) {
			if (cfElement.getAttribute("class") != null) {
				try {
					Object found = cfElement.createExecutableExtension("class");
					if (found instanceof IGenDiagraphServ){
						IGenDiagraphServ diagraphGenAction = (IGenDiagraphServ) found;
						if (!diagraphGenAction.isStub()){
				         if (LOG)
					      System.out.println(diagraphGenAction.getClass().getName());
				          return diagraphGenAction;
						}
					}
				} catch (org.eclipse.core.runtime.CoreException e) {
					e.printStackTrace();
				}
			}
		}
		throw new RuntimeException("No GenDiagraph extension found");

	}


}
