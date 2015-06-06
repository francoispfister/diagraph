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

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.isoe.diagraph.diagraphviz.dotutils.DotConstants;
import org.isoe.diagraph.diagraphviz.dotutils.DotWriter;
import org.isoe.diagraph.diagraphviz.dotutils.GraphVizConverter;
import org.isoe.diagraph.diagraphviz.dotutils.GraphwizInvoker;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.diagraph.DNode;

public class M0M2bToDot extends DotWriter implements DotConstants, DotVisitable,
GraphVizConverter{

	private CompiledGraph ecoreDiagraphM2Graph;
	private CompiledGraph diagraphM0Simple;
	private static final boolean LOG = false;
	protected String outputFile;
	private String dot;
	private DGraph concreteSyntax;
	private List<EObjectEdge> edges0;
	private List<EObjectEdge> edges1;



	private static final String TEMPLATE= " %s -> %s [color=gray,arrowhead=none ,label=\"instance of %s  \"];\n";

	private static final String[][] EDGES={
		{"DNode",TEMPLATE},
		{"DNestingEdge",TEMPLATE},
		{"DPartitioningNestingEdge",TEMPLATE},
		{"DReference",TEMPLATE},
		{"DPointOfView",TEMPLATE},
	};


	private String getEClassAnchor(List<EObjectEdge> edgs, String name) {
		for (EObjectEdge eObjectEdge : edgs) {
			if (eObjectEdge.isVisible()
					&& eObjectEdge.getTarget() instanceof EClass) {
				EClass eclaz = (EClass) eObjectEdge.getTarget();
				if (eclaz.getName().equals(name))
					return eObjectEdge.getTargetAnchor();
			}
		}
		return null;
	}

	private List<String> getInstanceAnchors(List<EObjectEdge> edgs,
			String EClassName) {
		List<String> result = new ArrayList<String>();
		List<EObject> visited = new ArrayList<EObject>();
		for (EObjectEdge eObjectEdge : edgs) {
			if (eObjectEdge.isVisible()
					&& eObjectEdge.getSource() != null
					&& eObjectEdge.getSource().eClass().getName()
							.equals(EClassName)) {
				if (!visited.contains(eObjectEdge.getSource())) {
					visited.add(eObjectEdge.getSource());
					result.add(eObjectEdge.getSourceAnchor());
				}
			}
		}
		return result;
	}

	public List<String[]>  getM1ToM2Edges(List<EObjectEdge> e, String eClassName) {
		List<String> instances = getInstanceAnchors(e,eClassName);
		String eclass = getEClassAnchor(e,eClassName);
		List<String[]> result = new ArrayList<String[]>();
		for (String srcAnchor : instances){
			String[] toAd = new String[2];
			toAd[0]=srcAnchor;
			toAd[1]=eclass;
			result.add(toAd);
		}
		return result;
	}




	String addInstanceEdges(List<EObjectEdge> all, String eClassName, String template){
		String result = "";
		List<String[]>  edges = getM1ToM2Edges(all, eClassName);
	    for (String[] edg : edges)
		   result+=  String.format(template, edg[0],edg[1],eClassName);
	    return result;
	}



	public M0M2bToDot(String out, CompiledGraph ecoreDiagraphM2Graph,
			CompiledGraph diagraphM0Simple) {
		this.outputFile = out;
		this.ecoreDiagraphM2Graph=ecoreDiagraphM2Graph;
		this.diagraphM0Simple=diagraphM0Simple;
		concreteSyntax = (DGraph) diagraphM0Simple.getRootObject();
		System.out.println(ecoreDiagraphM2Graph.getRootObject());//EPackageImpl@7a9f36 (name: diagraph)
		System.out.println(diagraphM0Simple.getRootObject());//l.DGraphImpl@11e86da (graphHandler: n
	}

	@Override
	public String getOuputPath() {
		return outputFile;
	}

	protected void toDotFile(String output) throws IOException {
		FileWriter fw = new FileWriter(output);
		fw.append(dot);
		fw.close();
	}

	@Override
	public CompiledGraph createDotGraph() {
		try {
			toDotFile(outputFile);
			GraphwizInvoker.toDot(outputFile, "jpg");
		} catch (Exception e) {
			throw new RuntimeException("dotify error for " + outputFile);
		}
		return null;
	}

	public void handleModel() {
		toDot();
	}

	public String toDot() {
		int subgraphes = 1;

		dot = startGraph() + "\n";


		dot += startSubGraph(subgraphes++, true);
		dot += m2Label("A" + subgraphes) + ";" + "\n";
		dot += ecoreDiagraphM2Graph.getNodesAsString(0);
		dot += endSubGraph() + "\n";

		dot += startSubGraph(subgraphes++, true);
		dot += m2Label("B" + subgraphes) + ";" + "\n";
		dot += ecoreDiagraphM2Graph.getNodesAsString(1);
		dot += endSubGraph() + "\n";


		dot += startSubGraph(subgraphes++, true);
		dot += m2Label("B" + subgraphes) + ";" + "\n";
		dot += diagraphM0Simple.getNodesAsString(1);
		dot += endSubGraph() + "\n";

		dot += startSubGraph(subgraphes++, true);
		dot += m2Label("C" + subgraphes) + ";" + "\n";
		dot += "blabla\n";
		dot += endSubGraph() + "\n";


	    dot += "/*1a ecoreDiagraphM2Graph Edges*/\n\n";

		String eas1= ecoreDiagraphM2Graph.getEdgesAsString(0);
		dot += "/*1b ecoreDiagraphM2Graph Edges*/\n\n";

		edges0 = ecoreDiagraphM2Graph.getEdges(0);



		String eas2= ecoreDiagraphM2Graph.getEdgesAsString(1);





		dot += eas1;
		dot += eas2;

		List<EObjectEdge> oes=diagraphM0Simple.getEdges(0);
		for (EObjectEdge eObjectEdge : oes) {
			System.out.println(eObjectEdge.toString());
		}


		dot += "/*2 diagraphM0Simple Edges  */\n\n";
		dot += diagraphM0Simple.getEdgesAsString(0);

		edges1 = diagraphM0Simple.getEdges(0);


		List<EObjectEdge> all = getEdges();
		for (String[] edg : EDGES)
			dot += addInstanceEdges(all,edg[0], edg[1]);


		dot += endGraph() + "\n";
		// dot = "TODO ....";

		return dot;
	}


	@Override
	public void toLogFile() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setVisitable(DotVisitable dotGenerator) {
		// TODO Auto-generated method stub

	}

	@Override
	public EObject getRoot() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void handleModel(EObject model) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getStyleForEdgesToForeignGraph(EObject m0Object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResourceSet handleModels(ResourceSet rs, URI uri,
			String dslM1domain, String dslM1xmlns, String dslM1eNS_URI,
			String view, DotVisitable dotGenerator, boolean b) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMatchWith(EObject eObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EObject find(String cn) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void setVisitor(GraphVizConverter diagraphVisitor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRoot(EObject root) {
		// TODO Auto-generated method stub

	}

	@Override
	public void accept(EObject model) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getNodesAsString(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getEdgesAsString(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<EObject, String> getEdgeEnds(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EObjectEdge> getEdges(int i) {
		if (i==0)
		    return edges0;
		else
			return edges1;
	}

	@Override
	public List<EObjectEdge> getEdges() {
		List<EObjectEdge> all = new ArrayList<EObjectEdge>();
	    all.addAll(edges0);
	    all.addAll(edges1);
	    return all;
	}



	@Override
	public List<EObject> getNodes(int section) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String asDot() {
		return dot;
	}








}
