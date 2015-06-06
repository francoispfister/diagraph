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

package org.isoe.extensionpoint.parsers;


import java.util.List;

import org.eclipse.emf.ecore.EAnnotation;
//import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.diagraph.DGraphElement;
import org.isoe.diagraph.diagraph.DNode;
import org.isoe.diagraph.workbench.api.DiagraphNotifiable;



/**
 * 
 * @author fpfister
 *
 */
public interface IRuntimeDiagraphParser {
	void parseEnd();
	DGraph getGraph();
	void setGraph(DGraph dGraph);
	DNode getCurrent();
	int getPhase();
	void begin(String id);
	void parseStart();
	String[] parseViews(List<EAnnotation> allAnnotations);
	void end();
	boolean isFunctional(); //does what it should do
	boolean isStub(); //is a mock
	boolean isCertified(); //can use it
	void init(DiagraphNotifiable owner, IDiagraphProvider diagraphProvider);
    List<EAnnotation> parseDiagraphAnnotations(EPackage pakage);
    String[] parseViews();
    String[] getViewIds();
    void setCurrentView(String view);
    DGraphElement parse(String from, Object o);
    void checkAnnotations(String layer);
}
