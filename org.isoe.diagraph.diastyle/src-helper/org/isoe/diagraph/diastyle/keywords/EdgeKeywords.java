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
package org.isoe.diagraph.diastyle.keywords;

public interface EdgeKeywords {

	public static final String DIASTYLE_EDGE_DIRECTION = "direction";
	public static final String DIASTYLE_EDGE_ARROWSIZE = "arrowsize";
	//FP130615
	public static final String DIASTYLE_EDGE_SHAPE = "shape";
	public static final String DIASTYLE_EDGE_LINEWIDTH = "linewidth";
	public static final String DIASTYLE_EDGE_LINE = "line";
	
	public static final String[] DIASTYLE_EDGE_PROPERTIES = {DIASTYLE_EDGE_DIRECTION,DIASTYLE_EDGE_ARROWSIZE,DIASTYLE_EDGE_SHAPE,DIASTYLE_EDGE_LINEWIDTH,DIASTYLE_EDGE_LINE};


}
