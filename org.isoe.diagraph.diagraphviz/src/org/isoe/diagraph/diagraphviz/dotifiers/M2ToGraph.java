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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.isoe.diagraph.diagraphviz.dotutils.GraphVizConverter;
import org.isoe.diagraph.diagraphviz.utils.DiaGraphvizResourceUtils;
import org.isoe.extensionpoint.graphviz.IDotifier;

/**
 * 
 * @author pfister
 * 
 */
public class M2ToGraph implements  GraphVizConverter {

	DiaGraphvizResourceUtils utils;

	private static final boolean LOG_ = false;
	private static int counter;
	private final static String SPACES = "                                                                                                     ";

	private static final boolean LOG_OBJ = false;
	private static final boolean LOG_TRACE = true;


	private DotVisitable visitable;
	private String outputFileName;
	
	private String asDot;
	


	@Override
	public ResourceSet handleModels(ResourceSet rs,URI uriDslM2, String domain, String xmlns,
			String eNS_URI, String view, DotVisitable visitable,boolean diagraph) {
		this.visitable=visitable;
		if (this.visitable!=null)
		   this.visitable.setVisitor(this);
	//	URI diagraphUri=loadModels(uriDslM2, domain, xmlns, eNS_URI, view);
		EObject aModel=loadModels(uriDslM2, domain, xmlns, eNS_URI, view);
		if (aModel!=null){
			asDot = createM2();
		  if (LOG_TRACE)
		   System.out.println(asDot);
		   return aModel.eResource().getResourceSet();
		} else{
			System.out.println("failed loading Diagraph resources");
			return null;
		}
	}

	private String createM2() {
		// TODO Auto-generated method stub
		return null;
	}

	public M2ToGraph(ResourceSet rs,String inputFile, String outputFile, IDotifier handler) {
		this.outputFileName = outputFile;
		utils = new DiaGraphvizResourceUtils(rs,inputFile, outputFile,handler);
	}
	
	@Override
	public String getOuputPath() {
		return outputFileName;
	}
	


	private void visitableAccept_(EObject model) {
		if (visitable!=null)
		    visitable.accept(model);		
	}
	
	private String findRootNodeAndCreateVisualNotation1() {
		
		//visitable.setDsmlConcreteSyntax(dsmlConcreteSyntax);
		//visitableAccept_(dsmlAbstractSyntax);
		return  "";
	}




	private EObject loadModels(URI dslM2Uri, String domain, String xmlns,
			String nsURI, String viewName) {
		URI  uri=utils.loadDiagraphModels(dslM2Uri, domain, xmlns, nsURI, viewName,false);
		if (uri!=null){
		  EObject dsmlAbstractSyntax = utils.getDsmlAbstractSyntax();
		  EObject dsmlConcreteSyntax = utils.getDsmlConcreteSyntax();
		  return dsmlConcreteSyntax;
		}
		return null;
	}



	@Override
	public void toLogFile() {
		
	}

	@Override
	public void setVisitable(DotVisitable dotGenerator) {
		this.visitable = dotGenerator; //TODO move some parts of dotGenerator here (separate visitor && visitable)

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
	public String getNodesAsString(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getEdgesAsString(int i) {
		return null;
	}

	@Override
	public Map<EObject, String> getEdgeEnds(int i) {
		return null;
	}



	@Override
	public String getStyleForEdgesToForeignGraph(EObject m0Object) {
		// TODO Auto-generated method stub
		return null;
	}

	private List<EObjectEdge> edges = new ArrayList<EObjectEdge>();

	
	@Override
	public List<EObjectEdge> getEdges(int i) {
		// TODO FP130201
		return edges;
	}

	@Override
	public CompiledGraph createDotGraph() {
		return null;	
	}

	@Override
	public List<EObject> getNodes(int section) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public String getMatchWith(EObject eObject) {
		System.out.println("getMatchWith " +"in M2ToGraph");
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
		return asDot;
	}

	@Override
	public List<EObjectEdge> getEdges() {
		return edges;
	}





}
