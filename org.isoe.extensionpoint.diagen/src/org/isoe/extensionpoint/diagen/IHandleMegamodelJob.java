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
package org.isoe.extensionpoint.diagen;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.isoe.diagraph.megamodel.Dsml;
import org.isoe.diagraph.workbench.api.DiagraphNotifiable;


/**
 *
 * @author fpfister
 *
 */
public interface IHandleMegamodelJob {
	static final String MGM_KEY = "*MEGAMODEL_PROJECT";
	IProject getProject(String name);
   // List<Dsml> getActiveDsmls__();
    List<Dsml> getMegamodelDsmls();
	IStatus errorStatus(String message);
    IStatus errorStatus(Throwable e);
    IStatus errorStatus(String message, Throwable e);
	DiagraphNotifiable getNotifiable();
	//List<Dsml> getActiveDsmls_();

}
