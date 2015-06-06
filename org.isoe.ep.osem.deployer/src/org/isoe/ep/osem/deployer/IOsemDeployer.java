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
package org.isoe.ep.osem.deployer;

import java.util.List;

import org.eclipse.emf.common.util.URI;

/**
 *
 * @author fpfister
 *
 */
public interface IOsemDeployer {
	//FP140321z
	//void deployingModels(String language, List<String> instancesdeployed);

	void doOsemJob_(String mode, String language);

//	void deployingModels(String language, URI megamodelSource,List<String> instancesdeployed);

	void deployModels(String language, URI megamodelSource, List<String> instancesdeployed);

	void cerror(String mesg);

}

//org.isoe.ep.osem.deployer.IOsemDeployer