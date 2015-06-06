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
package org.isoe.diagraph.megamodelhelper;

import java.util.List;

import org.eclipse.emf.ecore.EPackage;
import org.isoe.diagraph.megamodel.Dsml;
import org.isoe.diagraph.megamodel.Megamodel;


/**
 *
 * @author fpfister
 *
 */
public interface IMegaModelMan {
	void saveUniverse();
	Megamodel getMegamodel();
	Megamodel getMegamodel(boolean headless);
	Megamodel getMegamodel(String name, boolean recreate);
	void clearMegamodel();
	void setHandler(IMegamodelHandler megamodelHandler);
	Dsml addDsml(String projectName, EPackage model);
	//void close(String language, String operation);
	String[] prepare(String langs, String operation);
	List<Dsml> getDsmls();
	void close(String language, String operation, boolean save);
}
