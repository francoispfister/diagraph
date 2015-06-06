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
import org.eclipse.emf.ecore.EReference;
import org.isoe.diagraph.notationparser.AbstractEdgeVisitor;

import org.isoe.diagraph.diagrammable.IDiagrammable;



/**
 *
 * @author pfister
 *
 */
public class ReferenceVisitor extends AbstractEdgeVisitor {


	private EReference eReference;

	public String toString() {
		String sourceName = "-";
		String targetName = objectAsString(target);
		if (source != null)
				sourceName = objectAsString(source);
		return sourceName + " | " + getSourceAnchor() + " r> " + targetName
				+ " | " + getTargetAnchor();
	}

	public ReferenceVisitor(IDiagrammable parser, EClassifier source,
			EClassifier target, String sourceAnchor, String targetAnchor,
			String label, boolean visible, EReference eref) {//,EPackage srcPackage, EPackage toMergePackage
		super(parser, source, target, sourceAnchor, targetAnchor, label,visible);//,srcPackage,toMergePackage
		this.eReference = eref;
	}

	@Override
	public void accept() {
		super.accept();
	   if (parser.isCloning())
	      parser.getEcoreService().cloneEReference(this.eReference);
	     else if (parser.isParsing())
	        parser.parseEReference(this.eReference);
	}

	private String getReferenceUpperBound() {
		int upperbound = eReference.getUpperBound();
		if (upperbound == -1)
			return "*";
		else
			return Integer.toString(upperbound);
	}

	@Override
	public void visit() {
		super.visit();
	}

	@Override
	public void logit() {
		boolean containment = eReference.isContainment();
		String name = eReference.getName();
		String eType = ((EClass) target).getName();
		String eContainer = ((EClass) source).getName();
		int lowerbound = eReference.getLowerBound();
		clog("Reference [" + eContainer + (containment ? "] c" : "] r") + "--"
				+ name + "--" + getReferenceUpperBound() + "> [" + eType + "]");
	}

	public EReference geteReference() {
		return eReference;
	}

	@Override
	public String drawEdgeM1_(boolean rawGraph, boolean linkEdges,
			boolean refEdges, boolean inheredges) {
		String edg = "";
		if (refEdges)
			edg += "r_";//+ refArrow(getNodeLabel(source_), getNodeLabel(target),sourceAnchor, targetAnchor) + "\n";
		return edg;
	}

	protected String refArrow(String srclabel, String trglabel,
			String startoid, String endOid) {
		String result = (COMMENT ? "/*" + srclabel + " => " + trglabel
				+ "*/   " : "")
				+ startoid + " -> " + endOid
				// + " [dir=both arrowtail=odot];"
				+ " [color=blue];" + (COMMENT ? " /*" + arrowid++ + "*/" : "");
		return result;
	}


}
