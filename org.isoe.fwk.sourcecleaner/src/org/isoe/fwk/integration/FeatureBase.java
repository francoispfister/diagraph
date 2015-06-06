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
package org.isoe.fwk.integration;

import java.util.List;

/**
 * 
 * @author Pfister
 *
 */
public interface FeatureBase {

	List<String> getPluginsToDeploy();
	String getVersion();
	boolean deploySource(String plugin);
	List<String> getThirdpartyToDeploy();
	String getThirdPartyVersion_();

}
