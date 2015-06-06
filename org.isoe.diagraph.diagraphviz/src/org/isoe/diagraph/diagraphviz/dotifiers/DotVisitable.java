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
package org.isoe.diagraph.diagraphviz.dotifiers;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.isoe.diagraph.diagraphviz.dotutils.GraphVizConverter;
import org.isoe.diagraph.diagraph.DEdge;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.diagraph.DNode;
import org.isoe.diagraph.diagraph.DPointOfView;

/**
 * 
 * @author "François.Pfister francois.pfister@gmail.com"
 *
 */
public interface DotVisitable {

	void setVisitor(GraphVizConverter diagraphVisitor);

	CompiledGraph createDotGraph();
	
	void setRoot(EObject root);

	void accept(EObject model);

	String getNodesAsString(int i);

	String getEdgesAsString(int i);

	Map<EObject, String> getEdgeEnds(int i);

	List<EObjectEdge> getEdges(int section);

	List<EObject> getNodes(int section);

	String asDot();

}
