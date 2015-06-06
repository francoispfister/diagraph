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

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.isoe.extensionpoint.xmi.IMetamodelRetriever;

/**
 *
 * @author "fpfister"
 *
 */
import org.isoe.extensionpoint.diagraph.action.DiagraphService;  //FP140707_c_refactored
public interface ILanguageDotifyService extends DiagraphService {
	//void init(String leftPath, String rightPath, boolean languageOrModelSide);
	void runGraphviz(IFile file, EPackage pakage, URI uri);
	void createFolderIfNotExists(String project, String folder);
	void setMetamodelRetriever(IMetamodelRetriever metamodelRetriever);
	void init(String leftPath, String rightPath, boolean languageSide);
}
