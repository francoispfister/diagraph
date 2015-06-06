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

public interface NodeKeywords {
	
	public static final String DIASTYLE_NODE_SIZE = "size";
	public static final String DIASTYLE_NODE_LAYOUT_ = "layout";
	public static final String DIASTYLE_NODE_SHAPE = "shape";
	public static final String DIASTYLE_NODE_SHAPEDATA = "data";
	public static final String DIASTYLE_NODE_RADIUS = "radius";
	public static final String DIASTYLE_NODE_FIGURE = "figure";
	
	public static final String[] DIASTYLE_NODE_PROPERTIES = {DIASTYLE_NODE_SIZE,DIASTYLE_NODE_LAYOUT_,DIASTYLE_NODE_SHAPE,DIASTYLE_NODE_SHAPEDATA,DIASTYLE_NODE_RADIUS,DIASTYLE_NODE_FIGURE};


}
