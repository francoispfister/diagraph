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

import lirmm.dotutils.Dotify;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.isoe.diagraph.diagraphviz.dotutils.GraphVizConverter;
import org.isoe.diagraph.diagraphviz.dotutils.DotConstants;
import org.isoe.diagraph.diagraphviz.dotutils.DotWriter;
import org.isoe.diagraph.diagraphviz.dotutils.GraphwizInvoker;



/**
 * 
 * @author pfister
 * 
 */
public class M2M1ToDot extends DotWriter implements DotConstants, DotVisitable,
		GraphVizConverter {

	private static final boolean LOG = false;
	protected String outputFile;
	private String dot;
	private CompiledGraph domainM0Graph;
	private CompiledGraph domainM2Graph;

	private List<EObjectEdge> edges = new ArrayList<EObjectEdge>();

	
	@Override
	public List<EObjectEdge> getEdges() {
		return edges;
	}

	
	public M2M1ToDot(String outputFile, CompiledGraph domainM2Fraph,CompiledGraph domainM0Graph) {
		this.outputFile = outputFile;
		this.domainM2Graph = domainM2Fraph;
		this.domainM0Graph = domainM0Graph;
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

		dot += startSubGraph(subgraphes++, "box", true);
		dot += m2Label("M0") + ";" + "\n";
		dot += domainM0Graph.getNodesAsString(0);
		dot += endSubGraph() + "\n";

		dot += startSubGraph(subgraphes++, true);
		dot += m2Label("ecore") + ";" + "\n";
		dot += domainM2Graph.getNodesAsString(0);
		dot += endSubGraph() + "\n";

		dot += startSubGraph((EPackage)domainM2Graph.getRootObject(), true) + "\n";
		dot += m2Label((EPackage) domainM2Graph.getRootObject()) + ";" + "\n";
		dot += domainM2Graph.getNodesAsString(1);
		dot += endSubGraph() + "\n";
		
		
		dot += "/*ecoreEdges*/\n\n";
		dot += domainM2Graph.getEdgesAsString(0);
		dot += domainM2Graph.getEdgesAsString(1);
		
		dot += "/*m0edges*/\n\n";
		dot += domainM0Graph.getEdgesAsString(0);
		
		dot += "/*edges*/\n\n";
		
		//dot += domainM0Graph.createEdges("[color=gray,arrowhead=none ,label=\"instance of\"];");
		
		//dot += domainM0Graph.createEdgesToForeignGraph(1,domainM2Graph);
		
		
		
		
		//dot += domainM0Graph.createEdgesToForeignGraph1(0,domainM2Graph);

		//dot += domainM2Graph.createEdgesToForeignGraph1(0,domainM0Graph);

		//dot += this.createEdgesToForeignGraph1(0,domainM2Graph);

		//dot += domainM2Graph.createEdgesToForeignGraph1(0,this);





		
		dot += endGraph() + "\n";
		return dot;
	}



	@Override
	public String getStyleForEdgesToForeignGraph(EObject eObject) {
		return "label=\"???\"";//" [color=gray,arrowhead=none ,label=\"instance of "+eObject.eClass().getName()+"   \"];";
	}



	@Override
	public void setVisitor(GraphVizConverter diagraphVisitor) {

	}

	@Override
	public void setRoot(EObject root) {

	}

	@Override
	public void accept(EObject model) {

	}

	@Override
	public void toLogFile() {

	}

	@Override
	public void setVisitable(DotVisitable dotGenerator) {

	}

	@Override
	public ResourceSet handleModels(ResourceSet rs,URI uriDslM2, String domain, String xmlns,
			String eNS_URI, String view, DotVisitable visitable,boolean diagraph) {
		return rs;
	}

	@Override
	public EObject getRoot() {
		return null;
	}

	@Override
	public void handleModel(EObject model) {

	}

	
	@Override
	public String getNodesAsString(int i) {
		return null;
	}

	@Override
	public String getEdgesAsString(int i) {
		return null;
	}

	@Override
	public Map<EObject, String> getEdgeEnds(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	@Override
	public void setEdgeEnds(int section, Map<EObject, String> edgeEnd) {
		// TODO Auto-generated method stub
		
	}
	*/

	
	
	@Override
	public List<EObjectEdge> getEdges(int i) {
		// TODO FP130201
		return edges;
	}

	@Override
	public List<EObject> getNodes(int section) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMatchWith(EObject eObject) {
		System.out.println("getMatchWith " +"in M2M1ToDot");
		
		for (EObjectEdge eObjectEdge : getEdges(0)) {
			System.out.println(eObjectEdge.getSource().toString());
			System.out.println(eObjectEdge.getTarget().toString());
	
		}
		for (EObjectEdge eObjectEdge : getEdges(1)) {
			System.out.println(eObjectEdge.getSource().toString());
			System.out.println(eObjectEdge.getTarget().toString());

		}
		return "";
	}

	@Override
	public EObject find(String cn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String asDot() {
		return dot;
	}




}
