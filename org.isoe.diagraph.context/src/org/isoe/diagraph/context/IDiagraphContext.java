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
package org.isoe.diagraph.context;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.isoe.diagraph.diagraph.DGraph;


/**
 *
 * @author fpfister
 *
 */
public interface IDiagraphContext {
	List<DGraph> getCurrentDiagraphs();
	Diagram getCurrentDiagram();
	DGraph getMergeDiagraph();
	DGraph getCurrentDiagraph();
	//DGraph getCurrentDiagraph(Diagram currentDiagram, Diagram previousDiagram,String view);
	EClass getCurrentRoot();
	EClass getMergeRoot();
	List<String> getViewsForLanguage(String language);
	boolean isMerging();
	void setCurrentDiagram(Diagram diagram);
	void setCurrentDiagraph(DGraph dGraph);
	void setMergeDiagram(Diagram diagram);
	void setMergeDiagraph(DGraph dGraph);
	void setMergeRoot(EClass eClass);
	//void startMerge();
	void endMerge();
	//String updateCurrentDiagraph(Diagram currentDiagram, Diagram previousDiagram, String view);
	//void cloneLanguage(Diagram currentDiagram, Diagram previousDiagram, String view);
	//void cloneLanguage_(Diagram currentDiagram, Diagram exDiagram, String view);
	Diagram getMergeDiagram();
	DGraph get(String language, String view);
	DGraph get(String key);
	void startMerge(String languageView);
	void getCurrentDiagraphs(Diagram current, Diagram ex);
	//DGraph getCurrentDiagraph__(Diagram current, Diagram ex, String viu);
	DGraph getCurrentDiagraph(Diagram current, Diagram ex, String viu);
	//String updateCurrentDiagraph_(Diagram diag, Diagram exdiag, String view);
	String getCurrentAsString(String langview);
	String updateCurrentDiagraph(Diagram diag, Diagram exdiag, String view);
	void setSourceDiagraph(DGraph sourceDGraph);
	DGraph getSourceDiagraph();
}
