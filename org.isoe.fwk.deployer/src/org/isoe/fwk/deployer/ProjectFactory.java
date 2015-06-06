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
package org.isoe.fwk.deployer;

import org.eclipse.core.resources.IProject;
import org.eclipse.gmt.mod.infra.common.core.internal.utils.ProjectUtils;
import org.osgi.framework.Bundle;

/**
 * 
 * @author pfister
 *
 */
public class ProjectFactory {
	
	public static IProject createPluginProject(String name){
		Bundle bundleContainingResources = null;
		try {
			IProject result = ProjectUtils.getPluginProject(name,bundleContainingResources,"dir");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public IProject getPluginProject(String base,String name) {
		Bundle bundleContainingResources = null;
		try {
			IProject result = ProjectUtils.getProject(base+ "." + name);
			if (result == null)
			   result = ProjectUtils.getPluginProject(base+ "." + name, bundleContainingResources, "dir");  
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
