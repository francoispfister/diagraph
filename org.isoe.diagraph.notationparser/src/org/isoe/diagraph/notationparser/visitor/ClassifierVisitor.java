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
import org.isoe.diagraph.notationparser.AbstractVisitor;
import org.isoe.diagraph.diagrammable.IDiagrammable;


/**
 *
 * @author pfister
 *
 */
public class ClassifierVisitor extends AbstractVisitor {

	public String toString() {
		String targetName  = objectAsString(target);
		return targetName
				+ " | " + getTargetAnchor();
	}

	public ClassifierVisitor(IDiagrammable parser,
			EClassifier target,String targetAnchor,
			String label, boolean visible) { //,EPackage srcPackage, EPackage toMergePackage
		super(parser,target,targetAnchor,label, visible);//, srcPackage, toMergePackage
	}

	@Override
	public void accept(){
	  super.accept();
    }

	@Override
	public void visit() {
		super.visit();
	}

	@Override
	protected void logit() {
		String tclass = target==null?"":((EClass) target).getName();
		clog("Classifier "+ " -- "+tclass);
	}

	@Override
	public String drawEdgeM1_(boolean rawGraph, boolean linkEdges,
			boolean refEdges, boolean inheredges) {
		String edg = "";
		return edg;
	}

}
