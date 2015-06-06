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


/**
 *
 * @author fpfister
 *
 */
import org.isoe.diagraph.controler.IDiagraphControler;  //FP140707refactored
public interface IMegamodelManager {



	void consolidateRepositoryWithProjects();

	boolean filterModelsInRepository(String modelName);

	String[] getLanguageFiles(String folder, String exclude);

	String[] getModelsInRepository(String exclude);

	void setControler(IDiagraphControler controler);

	boolean isQualified();

	boolean isStub();

	void setSilent(boolean silent);

	//void saveRepository();

}
