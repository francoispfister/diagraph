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
package org.isoe.diagraph.notationparser.visitor;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.isoe.extensionpoint.ecoreutils.IEcoreUtils;
import org.isoe.diagraph.notationparser.AbstractEdgeVisitor;
import org.isoe.diagraph.diagrammable.IDiagrammable;

//import org.isoe.fwk.language.cloner.LanguageTriple.TripleType;

/**
 *
 * @author pfister
 *
 */
public class InheritanceVisitor extends AbstractEdgeVisitor {


	public String toString() {
		String sourceName = "-";
		String targetName = objectAsString(target);
		if (source != null)
				sourceName = objectAsString(source);
		return sourceName + " | " + getSourceAnchor() + " i> " + targetName
				+ " | " + getTargetAnchor();
	}

	public InheritanceVisitor(IDiagrammable parser, EClassifier source,
			EClassifier target, String sourceAnchor, String targetAnchor,
			String label, boolean visible) {
		super(parser,source,target,sourceAnchor,targetAnchor,label, visible);
	}

	@Override
	public void accept(){
	  super.accept();
     if (parser.isCloning())
	     parser.getEcoreService().cloneInheritance((EClass) source,(EClass) target);
     else if (parser.isParsing())
        parser.parseInheritance((EClass) source,(EClass) target);
   }

	@Override
	public void visit() {
		super.visit();
	}

	@Override
	public void logit(){
		String superclass = ((EClass) target).getName();
		String thisclass = ((EClass) source).getName();
		clog("Inheritance "+thisclass+" --> "+superclass);
	}


	@Override
	public String drawEdgeM1_(boolean rawGraph, boolean linkEdges,
			boolean refEdges, boolean inheredges) {
		String edg = "";
		if (inheredges)
			//edg += "I_" + getNodeLabel(source);
		    edg += "i_";//+inherArrow(getNodeLabel(source_), getNodeLabel(target), sourceAnchor, targetAnchor) + "\n";
		return edg;
	}


	protected String inherArrow(String srclabel, String trglabel, String startoid,
			String endOid) {
		String result = (COMMENT ? "/*" + srclabel + " => " + trglabel
				+ "*/   " : "")
				+ startoid + " -> " + endOid
				// + " [dir=both arrowtail=odot];"
				+ " [color=red];" + (COMMENT ? " /*" + arrowid++ + "*/" : "");
		return result;
	}


}
