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
package org.isoe.extensionpoint.diagen;

import java.io.File;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.megamodel.Dsml;

/**
 *
 * @author fpfister
 *
 */
public interface ITextGenerator {
    void connectAll();
    void getAllModels();
    void getKeywords();
    IStatus genGrammarDGraph(IProgressMonitor monitor, String langName) throws CoreException;
    List<String> getGlossary(); //FP140621
    List<String> getSeparators();//FP140621

	//IStatus genGrammarDsml_old(IProgressMonitor monitor, String langName)
	//		throws CoreException;
	IStatus exportLanguage(SubProgressMonitor monitor, List<DGraph> dgraphs,
			String langname, int n, File destFile);
	//IStatus exportDsmls_old(SubProgressMonitor monitor, List<Dsml> dsmls_,
	//		String langname, int n, File destFile);



}
