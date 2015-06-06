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
package org.isoe.diagraph.diagraphviz.dotutils;

import java.util.regex.Pattern;

import org.isoe.diagraph.diagraph.DiagraphPackage;

/**
 * 
 * @author "François.Pfister francois.pfister@gmail.com"
 *
 */
public interface DotConstants {
	
	static final boolean SEMANTIC_IDS = false; //true only for tests
	static final boolean COMMENT = false; //true only for tests
	static final boolean LOG = false; //true only for tests
	
	static final String COLOR_BLUE = ".6 .8 1.0";
	static final String COLOR_ORANGE = ".1 1.0 1.0";
	static final String COLOR_WHITE = ".0 .0 1.0";
	static final String COLOR_BLACK = ".0 .0 .0";
	static final String COLOR_RED = ".0 1.0 1.0";
	static final String COLOR_TURQUOISE = ".482 .714 .878";
	static final String COLOR_SIENNA = ".051 .718 .627";
	
	static final String FONT = "Verdana";
	static final String FONT_SIZE="10";

	static final String DIAGRAPH_PLUGIN_ = "org.isoe.diagraph.diagraph";


	static final String MODEL_FOLDER = "model/";
	static final String SEP_MODEL_FOLDER = "/"+MODEL_FOLDER;
	static final String RUNTIME_INSTANCE_FOLDER = "instances/";
	static final String DIAGRAPH_NAME = DiagraphPackage.eNAME;
	
	static final String[] COLORS = {  "aquamarine",
			"blanchedalmond", "cadetblue1", "darkorange", "brown1", "darkturquoise",
			"cadetblue", "chartreuse", "chocolate", "coral", "cornflowerblue",
			"cornsilk", "crimson", "cyan", "darkgoldenrod", "darkgreen",
			"darkkhaki", "darkolivegreen" ,COLOR_WHITE, COLOR_BLUE,
			COLOR_ORANGE, COLOR_RED, COLOR_TURQUOISE, COLOR_SIENNA}; 
	
	static final String CONCRETE_CLASS_COLOR = COLOR_WHITE;
	static final String ABSTRACT_CLASS_COLOR__ = COLOR_ORANGE;
	static final String ABSTRACT_CLASS_COLOR = COLOR_WHITE;

												
	static final String SPACE = "                                                                                                                                                    ";
	static final String SPACES = SPACE + SPACE;
	static final boolean SECOND_OBJ_ = true;

	static final String PORT_PREFIX = "p";
	static final String SOURCE_OID_SUFFIX = PORT_PREFIX + "0";
	static final String TARGET_OID_SUFFIX = PORT_PREFIX + "1"; // graphviz
																		// ports
	static final int TARGET_PREFIX = 1;
	static final Pattern PATTERN = Pattern
			.compile("\\p{InCombiningDiacriticalMarks}+");
	



}
