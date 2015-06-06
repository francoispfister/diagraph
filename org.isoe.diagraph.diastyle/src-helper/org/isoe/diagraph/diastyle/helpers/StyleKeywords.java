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
package org.isoe.diagraph.diastyle.helpers;

import org.isoe.diagraph.lang.DiagraphKeywords;




/**
 *
 * @author pfister
 *
 */
public interface StyleKeywords {



	//types

	public static final String ANNOT_DIASTYLE_NODE_LITTERAL_ = DiagraphKeywords.ANNOT_DIASTYLE_LITTERAL+".node";
	public static final String ANNOT_DIASTYLE_EDGE_LITERAL = DiagraphKeywords.ANNOT_DIASTYLE_LITTERAL+".edge"; //FP121006

	public static final String ANNOT_DIASTYLE_PARTITION_LITERAL_ = DiagraphKeywords.ANNOT_DIASTYLE_LITTERAL+".partition";

	public static final String DOTTED_DIASTYLE_NODE_LITERAL = ANNOT_DIASTYLE_NODE_LITTERAL_
			+ ".";
	public static final int DOTTED_DIASTYL_NODE_LITERAL_LENGTH = DOTTED_DIASTYLE_NODE_LITERAL
			.length();
	public static final String DOTTED_DIASTYLE_EDGE_LITERAL = ANNOT_DIASTYLE_EDGE_LITERAL
			+ ".";
	public static final String DOTTED_DIASTYLE_PARTITION_LITERAL = ANNOT_DIASTYLE_PARTITION_LITERAL_
			+ ".";
	public static final int DOTTED_DIASTYL_EDGE_LITERAL_LENGTH = DOTTED_DIASTYLE_EDGE_LITERAL
			.length();
	public static final int DOTTED_DIASTYL_PARTITION_LITERAL_LENGTH = DOTTED_DIASTYLE_PARTITION_LITERAL
			.length();

	//enums

	public static final String DIASTYLE_COLOR_WHITE = "white";
	public static final String DIASTYLE_COLOR_BLACK = "black";
	public static final String DIASTYLE_COLOR_LIGHT_GRAY = "lightgray";
	public static final String DIASTYLE_COLOR_GRAY = "gray";
	public static final String DIASTYLE_COLOR_DARK_GRAY = "darkgray";
	public static final String DIASTYLE_COLOR_RED = "red";
	public static final String DIASTYLE_COLOR_ORANGE = "orange";
	public static final String DIASTYLE_COLOR_YELLOW = "yellow";
	public static final String DIASTYLE_COLOR_GREEN = "green";
	public static final String DIASTYLE_COLOR_LIGHT_GREEN = "lightgreen";
	public static final String DIASTYLE_COLOR_DARK_GREEN = "darkgreen";
	public static final String DIASTYLE_COLOR_CYAN = "cyan";
	public static final String DIASTYLE_COLOR_LIGHT_BLUE = "lightblue";
	public static final String DIASTYLE_COLOR_BLUE = "blue";
	public static final String DIASTYLE_COLOR_DARK_BLUE = "darkblue";

	public static final String DIASTYLE_LAYOUT_NONE = "none";
	public static final String DIASTYLE_LAYOUT_FREE = "free";
	public static final String DIASTYLE_LAYOUT_HORIZONTAL = "horizontal";
	public static final String DIASTYLE_LAYOUT_VERTICAL = "vertical";

	public static final String DIASTYLE_ALIGNMENT_BEGINNING = "beginning";
	public static final String DIASTYLE_ALIGNMENT_CENTER = "center";
	public static final String DIASTYLE_ALIGNMENT_END = "end";
	public static final String DIASTYLE_ALIGNMENT_FILL = "fill";

	public static final String DIASTYLE_FONTSTYLE_NORMAL = "normal";
	public static final String DIASTYLE_FONTSTYLE_BOLD = "bold";
	public static final String DIASTYLE_FONTSTYLE_ITALIC = "italic";

	public static final String DIASTYLE_FONTNAME_ARIAL = "arial";
	public static final String DIASTYLE_FONTNAME_COURIER = "courier";
	public static final String DIASTYLE_FONTNAME_TIMES = "times";

	public static final String DIASTYLE_SHAPE_RECTANGLE = "rectangle";
	public static final String DIASTYLE_SHAPE_ROUNDEDRECTANGLE = "rounded";
	public static final String DIASTYLE_SHAPE_ELLIPSE = "ellipse";
	public static final String DIASTYLE_SHAPE_CUSTOM = "custom";

	public static final String DIASTYLE_DIRECTION_NONE = "none";
	public static final String DIASTYLE_DIRECTION_LEFT = "left";
	public static final String DIASTYLE_DIRECTION_RIGHT = "right";
	public static final String DIASTYLE_DIRECTION_BIDIRECTIONAL = "bidirectional";

	public static final String DIASTYLE_LINE_SOLID = "solid";
	public static final String DIASTYLE_LINE_DASH = "dash";
	public static final String DIASTYLE_LINE_DOT = "dot";
	public static final String DIASTYLE_LINE_DASHDOT = "dashdot";
	public static final String DIASTYLE_LINE_DASHDOTDOT = "dashdotdot";
	public static final String DIASTYLE_LINE_CUSTOM = "custom";


	public static final String DEFAULT_STYLE = "default";

	public static final String INIT_ARROW = "left";
	public static final String INIT_ARROW_COLOR = "black";
	public static final String INIT_ARROW_SIZE = "1";
	public static final String INIT_LAYOUT_ = "none"; //FP130317
	public static final String INIT_SHAPE = "arrow";
	public static final String INIT_SIZE_X = "20";
	public static final String INIT_SIZE_Y = "20";
	public static final String INIT_RADIUS = "5";
	public static final String INIT_SHAPE_DATA = "";
	public static final String INIT_ICON = "default";
	public static final String INIT_COLOR = "white";

	public static final String DEFAULT_ICON_EDGE = "edgeDefault1";
	public static final String DEFAULT_ICON_NODE = "nodeDefault1";
	public static final String LARGE_ICON_SUFFIX = "Large";

	public static final String DEFAULT_ICON = "default";








}
