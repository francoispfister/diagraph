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
package org.isoe.diagraph.views;

/**
 *
 * @author "François.Pfister francois.pfister@gmail.com"
 *
 */
public interface ViewConstants {
	//org.isoe.diagraph.views.ViewConstants.DIAGRAPH_DEFAULT

	public static final String DIAGRAM_SUFFIX_LITTERAL = "diagram";
	public static final String DIAGRAPH_DEFAULT = "default";
	public static final String DIAGRAPH_DEFAULT_SEP = "default;";
	public static final String VIEW = "view";
	public static final String VIEW_EQ = "view=";
	public static final String DIAGRAPH_ALL_VIEW_LITTERAL_NU = "all layers";


	public static final String DIAGRAPH_DEFAULT_VIEW_LITTERAL = "default";
	public static final String VIEW_SEPARATOR_0 = "_";

	public static final String VIEW_SEPARATOR_1 = "_";
	public static final String ROOT_NAME	 = "root";

	public final String DIAGRAM_DEFAULT = VIEW_SEPARATOR_0+DIAGRAPH_DEFAULT+VIEW_SEPARATOR_1+ROOT_NAME;
	public final String SUFFIX_DIAGRAM = DIAGRAM_DEFAULT + "_"+DIAGRAM_SUFFIX_LITTERAL;
	public final String DIAGRAM_SUFFIX_ = DIAGRAM_SUFFIX_LITTERAL + DIAGRAM_DEFAULT;//"_"+DIAGRAM_SUFFIX_LITTERAL + DIAGRAM_DEFAULT;


	public static final String VIEW_SPLITTER_ = "\\.";
	public static final String PREFIXED_ROOT_SUFFIX = VIEW_SEPARATOR_1+ROOT_NAME;
	public static final String DEFAULT_VIEW_DEFAULT_ROOT_SUFFIX = VIEW_SEPARATOR_0+DIAGRAPH_DEFAULT_VIEW_LITTERAL+PREFIXED_ROOT_SUFFIX;
	//_default_root


}
