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
package org.isoe.extensionpoint.diagraph.action;


import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDocumentEditor;
//import org.isoe.extensionpoint.diagraph.generator.IDiagraphGen;


/**
 *
 * @author "fpfister"
 *
 */
public interface IGenDiagraphServ extends DiagraphService {
	void setCurrentView();
	void setRcp(boolean rcp);
	void setAllViews();
	void setDiagraphGenerator(Object diagraphGenerator);
	void genCurrentView(IDocumentEditor diagramEditor);
	void genAllViews(IDocumentEditor diagramEditor);
	void setRefactor(boolean refactor);
	void setHeadless(boolean headless);
	void setRefreshOnly(boolean refreshOnly);
}
