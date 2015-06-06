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
import org.eclipse.gmf.runtime.notation.Node;
import org.isoe.diagraph.diagraph.DGraph;

public interface IDiagraphProvider {
	int getPhase();
	List<EAnnotation> getAllAnnotations();
	Node getCurrentDiagraphedNode();
	void endParsed();
	String getCurrentView(int sender, String language, String message);
	String getRegisterdView(String language);
	String[] getViews();
	void setDiagraph(DGraph diagraph);
	DGraph getDiagraph();
	DGraph getDiagraph(boolean mock,String view);
	void setDiagraph(boolean mock,String view,DGraph diagraph);
	DGraph getMockDiagraph();
	void setMockDiagraph(DGraph diagraph);

}
