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

import org.eclipse.gmf.tooldef.CreationTool;
import org.isoe.diagraph.diagraph.DGraphElement;

/**
 * 
 * @author pfister
 * 
 */
public class TooledGraphElement {
	private CreationTool creationTool;
	private DGraphElement graphElement;

	public TooledGraphElement(CreationTool creationTool,
			DGraphElement graphElement) {
		super();
		this.creationTool = creationTool;
		this.graphElement = graphElement;
	}

	public CreationTool getCreationTool() {
		return creationTool;
	}

	public DGraphElement getGraphElement() {
		return graphElement;
	}




}
