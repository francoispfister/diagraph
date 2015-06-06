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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.isoe.extensionpoint.diagraph.action.DiagraphService;

/**
 *
 * @author fpfister
 *
 */
public interface ModelInterpreter extends DiagraphService{
//org.isoe.extensionpoint.modelrunner.ModelInterpreter
//FP140317
	String getResourcename();
	String getLocation();
	String getLanguage();
	Resource getResource();
	URI getUri();
	void load();
	void run();
	boolean isStub();
	boolean isQualified();
	void setPlugin(String plugin);
	String getPlugin();
	void setResourceName(String resourceName);
	void setFolder(String folder);
	String getFolder();
}
