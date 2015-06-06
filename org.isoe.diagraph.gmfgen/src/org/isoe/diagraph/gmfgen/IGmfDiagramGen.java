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
package org.isoe.diagraph.gmfgen;


import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;

/**
 *
 * @author pfister
 *
 */
public interface IGmfDiagramGen {

	void exportIconsToEdit(String string, String layer);

	void generateDiagram(IProject project, IFile gmfgenfileRoot,
			IProgressMonitor progressMonitor);

	void progress(IProgressMonitor progressMonitor, String string);

	String handleException(Exception e, String mesg, String layer);

	void setPhase(int i);

	boolean  isGenError();

	List<String> getLog();

	void refresh(String projectName);

	String generateAllPointsOfView(String layer, boolean atRuntime,
			GenModel genmodel, boolean genFromDiagraph, String[] resourceData,
			boolean deleteOldJavaSrc, boolean changeTargetFolder, boolean rcp,
			boolean validate, boolean refreshArtifactsOnly,IProgressMonitor progressMonitor);

	void cerror(String mesg);

}
