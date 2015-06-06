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
package org.isoe.diagraph.styleparser;

import java.util.Map.Entry;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.isoe.diagraph.generic.GenericConstants;
import org.isoe.diagraph.common.IDiagraph;
import org.isoe.extensionpoint.parsers.IStyleParser;



/**
 * 
 * @author pfister
 * 
 */
public class StyleParser implements IStyleParser {

	private static final boolean LOG = false;
	private EPackage metaModel;
	private IDiagraph diagraph;
	private StyleParserUtils utils;
	private static final String DOTTED_ANNOT_STYLE_LITERAL = GenericConstants.ANNOT_DIAGRAPH_LITTERAL
			+ ".";
	private static final int DOTTED_ANNOT_STYLE_LITERAL_LENGTH = DOTTED_ANNOT_STYLE_LITERAL
			.length();

	public StyleParser() {
	
	}
	
	@Override
	public void init(EPackage mmodel, IDiagraph diagraph) {
		this.metaModel = mmodel;
		this.diagraph = diagraph;
		utils = new StyleParserUtils(mmodel, diagraph);
	}

	@Override
	public void parse() { //FP120705
		for (EAnnotation packageAnnotation : metaModel.getEAnnotations()) {
			if (packageAnnotation.getSource().startsWith(
					DOTTED_ANNOT_STYLE_LITERAL)) {
				String target = packageAnnotation.getSource();
				target = target.substring(DOTTED_ANNOT_STYLE_LITERAL_LENGTH);
				EMap<String, String> details = packageAnnotation.getDetails();
				for (Entry<String, String> entry : details) 
					parseEntry(target, entry.getKey());
			}
		}
	}

	private void parseEntry(String obj1, String cmd) {
		int p1 = cmd.indexOf(".");
		int p2 = cmd.indexOf("=");
		String obj2 = null;
		if (p2 > 0 && p2 > p1) {
				String[] k1 = cmd.split("\\.");
			String[] k2 = null;
			if (!(k1.length == 1 || k1.length == 2))
				throw new RuntimeException(
						"parseEntry 1 - should have 1 || 2 arguments !!!");
			if (k1.length == 1) {
				k2 = cmd.split("=");
				if (k2.length != 2)
					throw new RuntimeException(
							"parseEntry 2 - should have 2 arguments !!!");
			} else if (k1.length == 2) {
				k2 = k1[1].split("=");
				if (k2.length != 2)
					throw new RuntimeException(
							"parseEntry 2 - should have 2 arguments !!!");
				obj2 = k1[0].trim();
			}
			String command = k2[0].trim();
			String commandArgument =  k2[1].trim();
			EClass eClass = null;
			EReference eReference = null;
			if (obj2 == null)
				eClass = utils.findDiagraphedClass(metaModel, obj1);
			else
				eReference = utils.findDiagraphedReference(metaModel, obj1,
						obj2);
			if (eClass != null) {
				if (utils.setNodeStyle(obj1, command, commandArgument))
					return;
			} else if (eReference != null) {
				if (utils.setLinkStyle(obj1, obj2, command, commandArgument))
					return;
			}
			utils.setBadCommand(obj1, command, commandArgument);
		}
	}

	@Override
	public boolean isStub() {
		return false;
	}
}

