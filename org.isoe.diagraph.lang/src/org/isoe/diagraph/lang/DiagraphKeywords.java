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
package org.isoe.diagraph.lang;

import org.isoe.diagraph.generic.GenericConstants;
import org.isoe.diagraph.views.ViewConstants;
import org.isoe.diastyle.lang.StyleConstants;

/**
 *
 * @author fpfister
 *
 */
public interface DiagraphKeywords extends ViewConstants, GenericConstants,
		StyleConstants {
//org.isoe.diagraph.lang.DiagraphKeywords.KREFERENCE_
//FP141227int
	public static final String DIAGRAPH_LITTERAL = "diagraph";
	public static final String POINT_OF_VIEW = "pov";
	public static final String POINT_OF_VIEW_PREFIX = POINT_OF_VIEW + ":";
	public static final String OPEN_DIAGRAM = "nav";
	public static final String NODE = "node";
	public static final String LINK = "link";
	public static final String UNKNOWN = "unknown";

	public static final String REFERENCE = "ref";
	public static final String KREFERENCE_ = "kref";
	public static final String LNK = "lnk";
	public static final String CREFERENCE_ = "cref";
	public static final String PREFERENCE_OLD_NU = "pref";
	public static final String AFFIXED_ = "afx";



	public static final String IN_COMPARTMENT_NU = "komp";
	public static final String CONTAINMENT_HOST_NU = "host";

	public static final String LABEL = "label";
	public static final String LABELS = "labels";

	public static final String CONTAINMENT = "cont";
	public static final String XCONTAINMENT_ = "xcont"; //FP13108ww

	public static final String LINK_SOURCE = "lsrc";
	public static final String LINK_TARGET = "ltrg";





   public static final int CONTAINMENT_ID = 0;
   public static final int LINK_SOURCE_ID = 1;
   public static final int LINK_TARGET_ID = 2;



    public static final String CONTAINMENT_CMD_ = "cont_cmd";



	public static final String FIGURE_EMBEDDED_ARGUMENT = "figureEmbedded";
	//public static final String FIGURE_OWN_ARGUMENT = "figureOwn";

	//public static final String FIGURE_DEFAULT_ARGUMENT = FIGURE_EMBEDDED_ARGUMENT;
	//public static final boolean DEFAULT_COMPARTM_OPTION_FIGURE_EMBEDDED = true;



	public static final String TARGET_ARG_OPT = "t";

	public static final String NAME_ARG_OPT = "n";
	public static final String SRC_ARG_OPT = "s";

	public static final String NODEEQ = NODE + "=";
	public static final String LINKEQ = LINK + "=";
	public static final String LABELEQ = LABEL + "=";
	public static final String LABELSEQ = LABELS + "=";
	public static final String CONTAINMENT_EQU = CONTAINMENT + "=";
	public static final String XCONTAINMENT_EQU = XCONTAINMENT_+ "=";
	public static final String LINK_SOURCE_EQU = LINK_SOURCE + "=";
	public static final String LINK_TARGET_EQU = LINK_TARGET + "=";
	public static final String KREFERENCE_EQU_ = KREFERENCE_ + "=";
	public static final String CREFERENCE_EQU = CREFERENCE_ + "=";
	public static final String REFERENCE_EQU = REFERENCE + "=";
	public static final String AFFIXED_EQU_ = AFFIXED_ + "=";
	public static final String LINK_EQU = LINK + "=";
	public static final String VIEW_EQU = VIEW + "=";
	public static final String LABEL_EQU = LABEL + "=";




	public static final String STYLE_LNK = STYLE + "." + LNK;
	public static final String STYLE_KREF = STYLE + "." + KREFERENCE_;
	public static final String STYLE_CREF = STYLE + "." + CREFERENCE_;
	public static final String STYLE_REF = STYLE + "." + REFERENCE;

	public static final String DOTTED_STYLE_LNK = STYLE_LNK + ".";
	public static final String DOTTED_STYLE_KREF = STYLE_KREF + ".";
	public static final String DOTTED_STYLE_CREF = STYLE_CREF + ".";
	public static final String DOTTED_STYLE_REF = STYLE_REF + ".";

	public static final String[] DIASTYLE_TOKENS = { STYLE, STYLE_LNK,
			STYLE_KREF, STYLE_CREF };

	public static final String[] VALID_TOKENS = { POINT_OF_VIEW, OPEN_DIAGRAM,
			NODE, UNKNOWN, LABEL,LABELS, VIEW, CONTAINMENT_HOST_NU, IN_COMPARTMENT_NU,
			CONTAINMENT, REFERENCE, KREFERENCE_, LNK, CREFERENCE_, AFFIXED_,XCONTAINMENT_,
			LINK, LINK_SOURCE, LINK_TARGET, STYLE + "*", STYLE_LNK + "*",
			STYLE_KREF + "*", STYLE_CREF + "*", STYLE_REF + "*" };//PREFERENCE_OLD,


	public static final String GENERIC = "generic"; //supersedes node && link


	public static final String POINT_OF_VIEW_PREFIX_OBSOLETE = POINT_OF_VIEW + ":";











}
