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

import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.isoe.diagraph.diagraphviz.dotifiers.CompiledGraph;
import org.isoe.diagraph.diagraphviz.dotifiers.DotVisitable;
import org.isoe.diagraph.diagraphviz.dotifiers.EObjectEdge;
import org.isoe.extensionpoint.graphviz.GraphvizParams;

/**
 * 
 * @author "François.Pfister francois.pfister@gmail.com"
 *
 */
public interface GraphVizConverter extends GraphvizParams{
	

	void toLogFile();

	void setVisitable(DotVisitable dotGenerator);

	//void handleModels(URI uriDslM2, String domain, String xmlns,
		//	String eNS_URI, String view, DotVisitable visitable);

	EObject getRoot();
	
	void handleModel(EObject model);

	String getNodesAsString(int i);

	String getEdgesAsString(int i);


	Map<EObject, String> getEdgeEnds(int i);

	//void setEdgeEnds(int section, Map<EObject, String> edgeEnd);

	String getStyleForEdgesToForeignGraph(EObject m0Object);

	List<EObjectEdge> getEdges(int i);

	CompiledGraph createDotGraph();

	List<EObject> getNodes(int section);

	ResourceSet handleModels(ResourceSet rs,URI uri, String dslM1domain, String dslM1xmlns,
			String dslM1eNS_URI, String view, DotVisitable dotGenerator,
			boolean b);

	String getMatchWith(EObject eObject);

	EObject find(String cn);

	String asDot();

	String getOuputPath();

	List<EObjectEdge> getEdges();

	//ResourceSet handleModels(ResourceSet rsURI);


}
