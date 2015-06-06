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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.diagraphviz.dotutils.GraphVizConverter;
import org.isoe.diagraph.diagraphviz.utils.DiaGraphvizResourceUtils;
import org.isoe.extensionpoint.graphviz.IDotifier;

/**
 * 
 * @author pfister
 * 
 */
public class M1ToGraph implements GraphVizConverter {

	private static final boolean LOG = false;
	private static final boolean LOG_CONSOLE = false;
	private DiaGraphvizResourceUtils utils;
	private DGraph dsmlConcreteSyntax;
	private EObject dsmlAbstractSyntax;
	private DotVisitable visitable;
	private ResourceSet resourceSet;
	private String outputPath;
	private IDotifier handler;
	

	@Override
	public List<EObjectEdge> getEdges() {
		return null;
	}

	@Override
	public ResourceSet handleModels(ResourceSet rs, URI uriDslM2,
			String domain, String xmlns, String eNS_URI, String view,
			DotVisitable visitable, boolean diagraph) {
		this.visitable = visitable;
		if (this.visitable != null)
			this.visitable.setVisitor(this);
		EObject aModel = loadModels(uriDslM2, domain, xmlns, eNS_URI, view);
	
		if (aModel != null && dsmlConcreteSyntax!=null) //FP130402zz
			findRootNodeAndCreateVisualNotation1_();
		else
			clog("model == null || concreteSyntax == null");
		return aModel.eResource().getResourceSet();
	}

	
	private void clog(String mesg){
		if (LOG_CONSOLE   && handler.getUI()!=null){
			handler.getUI().log(null, mesg);
		}
		System.out.println(mesg);
	}
	
	public M1ToGraph(ResourceSet re, String inputFile, String outputFile,
			IDotifier handler) {
		this.handler=handler;
		this.resourceSet = re;
		this.outputPath = outputFile;
		utils = new DiaGraphvizResourceUtils(this.resourceSet, inputFile,
				outputFile, handler);
		if (LOG)
			clog("M1ToGraph (" + inputFile + " -log "
					+ outputFile + ")");
	}

	@Override
	public String getOuputPath() {
		return outputPath;
	}

	@Override
	public EObject getRoot() {
		return dsmlConcreteSyntax;
	}

	@Override
	public void handleModel(EObject model) {
		if (visitable != null)
			visitable.accept(model);
	}

	private String findRootNodeAndCreateVisualNotation1_() {
		visitable.setRoot(dsmlConcreteSyntax);
		handleModel(dsmlAbstractSyntax);
		return "";
	}

	private EObject loadModels(URI dslM2Uri, String domain, String xmlns,
			String nsURI, String viewName) {
		URI diagraphUri = utils.loadDiagraphModels(dslM2Uri, domain, xmlns,
				nsURI, viewName, false);
		if (diagraphUri != null) {
			dsmlAbstractSyntax = utils.getDsmlAbstractSyntax();// FP130402zz
			if (LOG)
				clog("dsmlAbstractSyntax="
						+ dsmlAbstractSyntax.toString());
			dsmlConcreteSyntax = utils.getDsmlConcreteSyntax();
			if (dsmlConcreteSyntax != null) {
				if (LOG)
					clog("dsmlConcreteSyntax="
							+ dsmlConcreteSyntax.toString());
			} else
				clog("failed loading diagraph resources !!!");

		} else
			clog("failed loading diagraph resources !!!");

		return dsmlAbstractSyntax;
	}

	@Override
	public void toLogFile() {

	}

	@Override
	public void setVisitable(DotVisitable dotGenerator) {
		this.visitable = dotGenerator; // TODO move some parts of dotGenerator
										// here (separate visitor && visitable)
	}

	@Override
	public String getNodesAsString(int i) {
		return visitable.getNodesAsString(i);
	}

	@Override
	public String getEdgesAsString(int i) {
		return visitable.getEdgesAsString(i);
	}

	@Override
	public Map<EObject, String> getEdgeEnds(int i) {
		return visitable.getEdgeEnds(i);
	}

	@Override
	public String getStyleForEdgesToForeignGraph(EObject m0Object) {
		return null;
	}

	@Override
	public List<EObjectEdge> getEdges(int section) {
		return visitable.getEdges(section);
	}

	@Override
	public CompiledGraph createDotGraph() {
		return null;
	}

	@Override
	public List<EObject> getNodes(int section) {
		return visitable.getNodes(section);
	}

	@Override
	public String getMatchWith(EObject eObject) {
		clog("getMatchWith " + "in M1ToGraph");
		for (EObjectEdge eObjectEdge : visitable.getEdges(0)) {
			clog(eObjectEdge.getSource().toString());
			clog(eObjectEdge.getTarget().toString());
		}

		for (EObjectEdge eObjectEdge : visitable.getEdges(1)) {
			clog(eObjectEdge.getSource().toString());
			clog(eObjectEdge.getTarget().toString());
		}

		return "";
	}

	@Override
	public EObject find(String cn) {
		return null;
	}

	@Override
	public String asDot() {
		return visitable.asDot();
	}

}
