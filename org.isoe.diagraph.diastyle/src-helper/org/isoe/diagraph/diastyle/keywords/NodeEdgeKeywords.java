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

public interface NodeEdgeKeywords {
	
	public static final String DIASTYLE_NODE_EDGE_FONT = "font";
	public static final String DIASTYLE_NODE_EDGE_FONTSIZE = "fontsize";
	public static final String DIASTYLE_NODE_EDGE_FONTCOLOR = "fontcolor";
	public static final String DIASTYLE_NODE_EDGE_LINE = "line";
	public static final String DIASTYLE_NODE_EDGE_LINEWIDTH = "linewidth";
	public static final String DIASTYLE_NODE_EDGE_ALIGNMENT = "align";
	public static final String DIASTYLE_NODE_EDGE_FONTSTYLE = "fontstyle";
	public static final String DIASTYLE_NODE_EDGE_ICON = "icon";
	
	public static final String[] DIASTYLE_NODE_EDGE_PROPERTIES = {DIASTYLE_NODE_EDGE_FONT,DIASTYLE_NODE_EDGE_ALIGNMENT,DIASTYLE_NODE_EDGE_LINEWIDTH
			,DIASTYLE_NODE_EDGE_LINE,DIASTYLE_NODE_EDGE_FONTCOLOR,DIASTYLE_NODE_EDGE_FONTSTYLE,DIASTYLE_NODE_EDGE_FONTSIZE,DIASTYLE_NODE_EDGE_ICON};


}
