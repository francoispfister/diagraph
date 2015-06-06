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
package org.isoe.extensionpoint.diagen;

import org.isoe.diagraph.lang.DiagraphKeywords;

/**
 *
 * @author fpfister
 *
 */
public interface IDiagraphAlphabet {
	static final String SEP_COMMA = ",";
	static final String SEP_EQU = "=";
	static final String SEP_UNDERSCORE = "_";
	static final String SEP_MINUS = "-";
	static final String SEP_SPACE = " ";
	static final String SEP_PLUS = "+";
	static final String SEP_ARROW = ">";
	static final String SEP_OPEN_BRACKET = "{";
	static final String SEP_CLOSE_BRACKET = "}";
	static final String SEP_OPEN_ARRAY = "[";
	static final String SEP_CLOSE_ARRAY = "]";
	static final String SEP_TAB = "\t";

	static final String STR_COLON = ":";
	static final String STR_DOT = ".";

	static final String SEP_OPEN_PARENTH = "(";
	static final String SEP_CLOSE_PARENTH = ")";

	static final String SEP_EQU_SPS = SEP_SPACE + SEP_EQU + SEP_SPACE;

	static final String DIAGRAPH_VOCAB_POV_SHORT = "p";

	static final String DIAGRAPH_VOCAB_POV_ = "pov";
	static final String DIAGRAPH_VOCAB_GENERIC = "gen";

	static final String DIAGRAPH_VOCAB_NAV_ = DiagraphKeywords.OPEN_DIAGRAM;// "nav";

	static final String DIAGRAPH_VOCAB_LINK = DiagraphKeywords.LINK;// "link";

	static final String DIAGRAPH_VOCAB_CONT = DiagraphKeywords.CONTAINMENT;
	static final String DIAGRAPH_VOCAB_CREF = DiagraphKeywords.CREFERENCE_;// "cref";
	static final String DIAGRAPH_VOCAB_AFX_ = DiagraphKeywords.AFFIXED_;// "afx";
	static final String DIAGRAPH_VOCAB_REF = DiagraphKeywords.REFERENCE;// "ref";
	static final String DIAGRAPH_VOCAB_KREF = DiagraphKeywords.KREFERENCE_;

	static final String DIAGRAPH_VOCAB_DEFAULT_VIEW_ = "default";

	static final String DIAGRAPH_VOCAB_BYVAL = "byval";
	static final String DIAGRAPH_VOCAB_DATATYPE = "datatype";
	static final String DIAGRAPH_VOCAB_ESTRING = "EString";
	static final String DIAGRAPH_VOCAB_EINT = "EInt";
	static final String DIAGRAPH_VOCAB_EBOOL = "EBool";
	static final String DIAGRAPH_VOCAB_PROPOGATED = "_propagated";
	static final String DIAGRAPH_VOCAB_ABSTRACT = "abstract";


	static final String DIAGRAPH_VOCAB_LABELS_ = DiagraphKeywords.LABELS;
	static final String DIAGRAPH_VOCAB_NAV_EQUAL = DIAGRAPH_VOCAB_NAV_
			+ SEP_EQU;
	static final String DIAGRAPH_VOCAB_LABELS_EQUAL = DIAGRAPH_VOCAB_LABELS_
			+ SEP_EQU;

	// static final String DIAGRAPH_VOCAB_GENERIC_NU = "gen";

	static final String DIAGEN_VOCAB_NIL = "nil";
	static final String DIAGRAPH_VOCAB_RECURSIVE = "recursive";
	static final String DIAGRAPH_VOCAB_ROOT = "root";
	static final String DIAGRAPH_VOCAB_NOT_ROOT = "not-root";
	static final String DIAGEN_VOCAB_SRC = "src";
	static final String DIAGEN_VOCAB_TRG = "trg";

	static final String DIAGRAPH_STATUS_ABSTRACT = "abstr";
	static final String DIAGRAPH_STATUS_NRA = "nra";
	static final String DIAGRAPH_STATUS_RA = "ra";
	static final String DIAGEN_STATUS_INHER = "her";
	static final String DIAGRAPH_STATUS_NOT_ECORE_CONTAINED = "nec";
	static final String DIAGRAPH_STATUS_NOT_DIAGRAPH_CONTAINED = "ndc";

	static final String MEGAMODEL_NEW_PARSE_NU = "parse_nu";

	static final String MEGAMODEL_DIAGRAPH = DiagraphKeywords.DIAGRAPH_LITTERAL;
	static final String MEGAMODEL_ARTIFACTS = "ex";
	static final String MEGAMODEL_ALL = "all";
	static final String MEGAMODEL_CHECK_STATUS = "status";

	static final String MEGAMODEL_FIND = "find";
	static final String MEGAMODEL_ACTION_BUILD = "testall";
	static final String MEGAMODEL_GEN_DSML = "dsml";
	static final String MEGAMODEL_GEN_ROLES = "roles";
	static final String MEGAMODEL_ACTION_CLONE = "clone";
	static final String MEGAMODEL_ACTION_MERGE = "merge";
	static final String MEGAMODEL_GEN_GMF_EDITOR = "gmf";

	static final String MEGAMODEL_CMD_EXPORT_IMAGE = "megamodel_export_image";
	static final String MEGAMODEL_CMD_MODEL_WEAVER = "megamodel_model_weaver";
	static final String MEGAMODEL_CMD_LANGUAGES = "megamodel_languages";
	static final String MEGAMODEL_CMD_WEAVER_ = "megamodel_diagraph_weaver";
	static final String MEGAMODEL_CMD_GRAMMAR = "megamodel_grammar";
	static final String MEGAMODEL_CMD_GEN_EDITOR = "megamodel_geneditor";

	static final String DIAGEN_VOCAB_NAME = "name";
	static final String DIAGEN_VOCAB_DIAGRAM = "diagram";

	static final String DIAGEN_VOCAB_NODE = "n";
	static final String DIAGEN_VOCAB_EDGE = "e";
	static final String DIAGEN_VOCAB_MODELS = "models";
	static final String DIAGEN_VOCAB_EXCERPT = "excerpt";
	static final String DIAGEN_VOCAB_MODEL = "model";
	static final String DIAGEN_VOCAB_EDGES = "edges";
	static final String DIAGEN_VOCAB_NODES = "nodes";
	static final String DIAGEN_VOCAB_NOTATION_ = "notation";
	static final String DIAGEN_VOCAB_VIEW = "view";

	static final String DIAGEN_VOCAB_DSML = "dsml";
	static final String DIAGRAPH_VOCAB_METAMODEL = "metamodel";
	static final String DIAGEN_VOCAB_MEGAMODEL = "megamodel";
	static final String DIAGEN_VOCAB_DSMLS = "dsmls";

	static final String DIAGEN_VOCAB_RIGHT_PARENT = "right-parent";
	static final String DIAGEN_VOCAB_LEFT_PARENT = "left-parent";
	static final String DIAGEN_VOCAB_KNOWN_AS = "known-as";
	static final String DIAGEN_VOCAB_NOTICES = "notices";
	static final String DIAGEN_VOCAB_CONTEXT = IDiagenAlphabet.KW_CONTEXT;
	static final String DIAGEN_VOCAB_ORIGIN = IDiagenAlphabet.KW_ORIGIN;

	static final String DIAGEN_VOCAB_REQUIRED_ORIG_NU = IDiagenAlphabet.KW_REQUIRES_;
	static final String DIAGEN_VOCAB_REQUIRED_ = IDiagenAlphabet.KW_REQUIRED;
	static final String DIAGEN_VOCAB_RELATED_ = IDiagenAlphabet.KW_RELATED;

	static final String NOTATION_OPEN_ = DIAGEN_VOCAB_NOTATION_
			+ SEP_OPEN_PARENTH;
	static final String VIEW_OPEN = DIAGEN_VOCAB_VIEW + SEP_OPEN_PARENTH;

	static final String DIAGEN_NAME = "diagen";
	static final String DIAGEN_EXT = "txt";
	static final String DIAGEN_CST_EXT = "cst";
	static final String DIAGEN_AST_EXT = "ast";
	static final String DIAGEN_GRAM_EXT_ = "dsml";
	static final String DIAGEN_CMD_EXT = "diascript";
	static final String DIAGEN_RTF_EXT = "rtf";

	static final String DIAGEN_CMD_RTF_EXT = "_" + DIAGEN_CMD_EXT + "."
			+ DIAGEN_RTF_EXT;
	static final String DIAGEN_CST_RTF_EXT__ = "_" + DIAGEN_CST_EXT + "."
			+ DIAGEN_RTF_EXT;
	static final String DIAGEN_AST_RTF_EXT__ = "_" + DIAGEN_AST_EXT + "."
			+ DIAGEN_RTF_EXT;
	static final String DIAGEN_GRAM_RTF_EXT__  = "_" + DIAGEN_GRAM_EXT_ + "."
			+ DIAGEN_RTF_EXT;

	static String[] DIAGRAPH_KEYWORDS_ = { DIAGEN_VOCAB_DSML,
			DIAGEN_VOCAB_VIEW, DIAGRAPH_VOCAB_CREF, DIAGRAPH_VOCAB_KREF,
			DIAGRAPH_VOCAB_REF, DIAGRAPH_VOCAB_AFX_, DIAGRAPH_VOCAB_LINK,
			DIAGRAPH_VOCAB_NAV_, DIAGRAPH_VOCAB_LABELS_, DIAGRAPH_VOCAB_POV_,
			DIAGRAPH_VOCAB_DEFAULT_VIEW_, DIAGRAPH_VOCAB_GENERIC,
			DIAGRAPH_VOCAB_METAMODEL,

			DIAGRAPH_VOCAB_BYVAL, DIAGRAPH_VOCAB_DATATYPE,
			DIAGRAPH_VOCAB_ESTRING, DIAGRAPH_VOCAB_EINT, DIAGRAPH_VOCAB_EBOOL,
			DIAGRAPH_VOCAB_PROPOGATED,DIAGRAPH_VOCAB_ABSTRACT

	};

	static final String DIAGRAPH_CMD_POV_ = DIAGRAPH_VOCAB_POV_;
	static final String DIAGRAPH_CMD_GEN = DIAGRAPH_VOCAB_GENERIC;

	static final String DIAGRAPH_CMD_NOD = "node";
	static final String DIAGRAPH_CMD_NAV = "nav";
	static final String DIAGRAPH_CMD_CREF = "cref";
	static final String DIAGRAPH_CMD_REF = "ref";
	static final String DIAGRAPH_CMD_AFX = "afx";
	static final String DIAGRAPH_CMD_KREF = DiagraphKeywords.KREFERENCE_;
	static final String DIAGRAPH_CMD_LINK = DIAGRAPH_VOCAB_LINK;
	static final String DIAGRAPH_CMD_LNK = "lnk";
	static final String DIAGRAPH_CMD_LABELS_ = "labels";

	static String[] DIAGRAPH_ROLE_KW_ = { DIAGRAPH_CMD_AFX, DIAGRAPH_CMD_REF,
			DIAGRAPH_CMD_CREF, DIAGRAPH_CMD_KREF, DIAGRAPH_CMD_LABELS_,
			DIAGRAPH_CMD_LINK, DIAGRAPH_CMD_NAV, DIAGRAPH_CMD_NOD,
			DIAGRAPH_CMD_POV_, DIAGRAPH_CMD_GEN,
	// DIAGRAPH_VOCAB_DEFAULT_VIEW_
	};

	enum DiagraphRoles {
		afx, ref, cref, kref, labels, link, nav, nod, pov, gen
	}

	static final String[] EXISTING_COMMANDS = { MEGAMODEL_FIND,
			MEGAMODEL_GEN_DSML, MEGAMODEL_GEN_ROLES, MEGAMODEL_ACTION_CLONE,
			MEGAMODEL_ACTION_MERGE, MEGAMODEL_GEN_GMF_EDITOR,

			MEGAMODEL_ACTION_BUILD, MEGAMODEL_ARTIFACTS, MEGAMODEL_DIAGRAPH,
			MEGAMODEL_ALL, MEGAMODEL_NEW_PARSE_NU };

}
