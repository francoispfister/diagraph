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
package org.isoe.extensionpoint.similarity;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

/**
 *
 * @author fpfister
 *
 */
public class SimilarityConnector {
	private static final String SIMILARITY_EXTENSION_NAME="diagraph_similarity";
	//private static final String SIMILARITY_EXTENSION_NAME="org.isoe.extensionpoint.similarity.diagraph_similarity";
	//private static final String SIMILARITY_ACTION_NAME="org.isoe.extensionpoint.similarity.diagraph_similarity_action";
	private static final boolean LOG = false;

	public ISimilarityService getSimilarityService() {
		for (IConfigurationElement cfElement : Platform.getExtensionRegistry().getConfigurationElementsFor(SIMILARITY_EXTENSION_NAME)) {
			if (cfElement.getAttribute("class") != null) {
				try {
					  Object found =  cfElement.createExecutableExtension("class");
					  if (LOG)
					      System.out.println("getSimilarityService> "+found.getClass().getName());
					  if (found instanceof ISimilarityService){
					        ISimilarityService service = (ISimilarityService) found;
					          if (!service.isStub()){
				               if (LOG)
					            System.out.println(service.getClass().getName());
				               return service;
						  }
					  }
				} catch (org.eclipse.core.runtime.CoreException e) {
					e.printStackTrace();
				}
			}
		}
		 if (LOG)
		  System.out.println(" no SimilarityService for "+SIMILARITY_EXTENSION_NAME+" found; does your service contribute to the extension with a plugin.xml ?");
		return null;
	}

	public IAlignService getSimilarityAction() {
		for (IConfigurationElement cfElement : Platform.getExtensionRegistry().getConfigurationElementsFor(SIMILARITY_EXTENSION_NAME)) {
			if (cfElement.getAttribute("class") != null) {
				try {
					  Object found =  cfElement.createExecutableExtension("class");
					  if (LOG)
					      System.out.println("getSimilarityAction> "+found.getClass().getName());
					  if (found instanceof IAlignService){
						 IAlignService action = (IAlignService) found;
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
		  System.out.println(" no SimilarityAction for "+SIMILARITY_EXTENSION_NAME+" found; does your action contribute to the extension with a plugin.xml ?");
		return null;
	}


}
//org.isoe.similarity
//matcher.mtbe-fr.model mtbe.model.matcher.Alignment
