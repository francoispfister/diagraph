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

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.isoe.extensionpoint.xmi.IMetamodelRetriever;

/**
 *
 * @author "fpfister"
 *
 */
import org.isoe.extensionpoint.diagraph.action.DiagraphService;  //FP140707_c_refactored
public interface IMegamodelDeployService extends DiagraphService {
	void setDeployBuildInPlugin(boolean deploy);
	//void setDeployLocalPlugin(boolean deploy);
	void setDeployLocalWorkspace(boolean deploy);
	void setMetamodelRetriever(IMetamodelRetriever metamodelRetriever);
	void setLanguageToAll(boolean all);
	void setAllprototypes(boolean allprototypes);
	void setLanguageFilter(String filter);
    void setLanguageToDeploy(String languageToDeploy);
    void setLanguagesToDiagraph(String languages);
   // void setGenerateEmfGen(boolean value);
	void setDeployLocalPlugin(boolean deployLocalPlugin);
	List<IProject> getDeployedProjects();
	List<String> getDeployedLanguages();
}
