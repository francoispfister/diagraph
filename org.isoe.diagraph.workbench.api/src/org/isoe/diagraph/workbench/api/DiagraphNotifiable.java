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
package org.isoe.diagraph.workbench.api;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.megamodel.Megamodel;
//import org.isoe.extensionpoint.diagraph.IDiagraphConsole;

/**
 *
 * @author fpfister
 *
 */
public interface DiagraphNotifiable {
	void alert(boolean headless, String message);
	void clearLog(int target);
	void csvTrace(String csv);
	void diagraphMegamodel_(boolean deployBuildInPlugin,
			boolean deployLocalPlugin, boolean deployLocalWorkspace,
			String languagesToDiagraph);
	void done(IProject project);
	void endParsed(Diagram currentDiagram);
	void generateLanguage(boolean headless, boolean refreshOnly, Object sender, String language);
	Diagram getChangedDiagram();
	EPackage getEPackage();
	Megamodel getMegamodel_(boolean headless);
	String getMetadata(String id);
	String getParseErrorLog();
	String getRegisterdView(String language);
	void log(int target, String message);
	void logStatements(String view, String logstatements);
	List<DGraph> newParse(Diagram diagram);
	void newParseLanguage_nu();
	void registerCurrentView(String langName, String view, int sender);
	boolean setCurrentView(Diagram diagram, String view);
	void setParseErrorLog(String error);
	void setViews(Object sender, int id, String langname,String[] viewids, String current);
	String getCurrentView(int sender, String language, String message);
	List<String> getViewsForLanguage(String lang);
	DGraph getDGraph(String language, String view);
	String[] getViews();
	List<DGraph> getCurrentDiagraphs();
	List<String> getDiagraphProjectNames();
	boolean mustThrowExceptions();
	void cerror(String mesg);
	void change(Diagram diagram);
	void cinfo(String mesg);
	void cwarn(String mesg);
	boolean isLayouting();
	Resource getCurrentResource();
	DGraph get(String langname, String view);
	void creport(List<String> log);
}
