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
package org.isoe.diagraph.notationparser;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.isoe.diagraph.diagrammable.IDiagrammable;
import org.isoe.extensionpoint.ecoreutils.IEcoreUtils;

/**
 *
 * @author fpfister
 *
 */
public abstract class AbstractEdgeVisitor extends AbstractVisitor {

   protected EObject source;
   protected String sourceAnchor;

	public AbstractEdgeVisitor(IDiagrammable parser, EClassifier source,
			EClassifier target, String sourceAnchor, String targetAnchor,
			String label, boolean visible) {
		super(parser,  target, targetAnchor, label, visible);
		this.source = source;
		this.sourceAnchor = sourceAnchor;
	}

	public EObject getSource() {
		return source;
	}

	public String getSourceAnchor() {
		return sourceAnchor;
	}

	public String getTargetAnchor() {
		return targetAnchor;
	}

	public String toString() {
		String sourceName = "-";
		String targetName = objectAsString(target);
		if (source!=null)
			sourceName = objectAsString(source);
		return sourceName+" | " + getSourceAnchor()+" > " + targetName + " | " + getTargetAnchor();
	}

	@Override
	protected void logit() {
	}

	@Override
	public String drawEdgeM1_(boolean rawGraph, boolean linkEdges,
			boolean refEdges, boolean inheredges) {
		return null;
	}

	public void accept(){
	    super.accept();
	    if (parser.isCloning())
	         parser.getEcoreService().cloneClassWithAttributesAndAnnotations((EClass) source);
	    else if (parser.isParsing())
          parser.parseClassWithAttributesAndAnnotations((EClass) source,this);
	}

}
