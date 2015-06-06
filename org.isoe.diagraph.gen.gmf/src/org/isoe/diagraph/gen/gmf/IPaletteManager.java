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
package org.isoe.diagraph.gen.gmf;

import java.util.List;

import org.eclipse.gmf.tooldef.CreationTool;
import org.isoe.diagraph.diagraph.DGraphElement;


/**
 *
 * @author pfister
 *
 */
public interface IPaletteManager {

	List<CreationTool> getTopNodeTools(String layer);
	List<CreationTool> getCompartmentedNodeTools(String layer);
	List<CreationTool> getReferenceTools(String layer);
	List<CreationTool> getLinkTools(String layer);
	List<CreationTool> getUnsortedTools(String layer);
	List<CreationTool> getExtraTools(String layer);
	CreationTool findTool(DGraphElement el);
	void setElements(List<TooledGraphElement> els);
	//void registerTool_(DGraphElement el, String description);
	void initTool();
	String getExtraToolsName();
	CreationTool registerTool(DGraphElement el, String description);



}
