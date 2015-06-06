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
package org.isoe.extensionpoint.graphviz;

import org.isoe.diagraph.controler.IDiagraphControler;
import org.isoe.extensionpoint.xmi.IMetamodelRetriever;


/**
 *
 * @author fpfister
 *
 */
public interface IDotifier {
	// org.isoe.extensionpoint.graphviz.IDotifier
	static final String GRAPHVIZ_FOLDER = "graphviz"; // FP130401
	static final String DOT_OPT_M1 = "m1";
	static final String DOT_OPT_M2 = "m2";
	static final String DOT_OPT_DM2 = "dm2";
	static final String DOT_OPT_M0 = "m0";
	static final String DOT_OPT_ALL = "all";
	static final String DOT_OPT_RAW = "raw";
	static final String DOT_OPT_COMPOUND = "compound";
	static final String DOT_OPT_M1_RAW = DOT_OPT_M1 + "." + DOT_OPT_RAW;
	static final String DOT_OPT_M1_COMPOUND = DOT_OPT_M1 + "."
			+ DOT_OPT_COMPOUND;

	void log(String mesg);

	IDiagraphControler getUI();

	void setMetamodelRetriever(IMetamodelRetriever metamodelRetriever);
}
