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
package org.isoe.diagraph.common;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.isoe.diagraph.workbench.api.DiagraphNotifiable; //FP140701



/**
 *
 * @author pfister
 *
 */
public interface IDiagraphInvoker {
	void log(String mesg);
	String getCurrentView(int sender, String language, String mesg);
	void registerCurrentView(String language, String view, int sender);
	int getViewId();
	int getNextNodeId(String name);
	int getNextEdgeId(String name);
	void clearIdGenerator();
	void execDiagraphTransformation(String layer,
			String resourcepath,boolean validate,boolean refreshArtifactsOnly,IProgressMonitor progressMonitor);
	String getMetadata(String id);
	void logStatements(String view, String logstatements);
	boolean mustThrowExceptions();
	DiagraphNotifiable getOwner();
	void cerror(String mesg);
	void cinfo(String mesg);
	void cwarn(String mesg);
	boolean isLayouting();
	void creport(List<String> log);
//	void logGeneration(String mesg);

}
