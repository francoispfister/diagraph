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
package org.isoe.diagraph.gen.gmf;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.gmf.gmfgraph.FigureGallery;
import org.isoe.diagraph.diagraph.DCompartmentEdge;
import org.isoe.diagraph.diagraph.DOwnedEdge;
import org.isoe.diagraph.diagraph.DEdge;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.diagraph.DGraphElement;
import org.isoe.diagraph.diagraph.DNavigationEdge;
import org.isoe.diagraph.diagraph.DNestedEdge;
import org.isoe.diagraph.diagraph.DLabeledEdge;
import org.isoe.diagraph.diagraph.DNode;
import org.isoe.diagraph.diagraph.DReference;
import org.isoe.diagraph.diagraph.DSimpleEdge;
import org.isoe.diagraph.diastyle.helpers.IErrorReporter;
import org.isoe.diagraph.gen.gmf.util.GmfUtils;
import org.isoe.diagraph.generator.generic.DBaseGenerator;
import org.isoe.diagraph.internal.api.DPhase;
import org.isoe.diagraph.internal.m2.DiaContainment;
import org.isoe.diagraph.internal.m2.DiaNode;
import org.isoe.diagraph.internal.m2.api.IDiaContainment;
import org.isoe.diagraph.internal.m2.parser.DAnnotationParser;
import org.isoe.diagraph.internal.m2.parser.DStatement;
import org.isoe.diagraph.parser.api.IDiagraphParser;
import org.isoe.fwk.core.DParams;

/**
 *
 * @author fpfister
 *
 */
public class GmfBaseGenerator extends DBaseGenerator {

	private static final boolean LOG = DParams.GmfBaseGenerator_LOG;
	protected static FigureGallery defaultFigureGallery;
	protected static GmfUtils gmfutils;
	protected IDiagraphParser parser2;
	//protected boolean devcomposite_;
//	protected boolean newversion_;
	protected boolean canvasComposite_nu;

	protected void clogk(String mesg) {
		if (DParams.containment_LOG)
			System.out.println("=========[ " + mesg);
	}

	public GmfBaseGenerator(IErrorReporter logger, String layer,
			String pluginId, String filePath, EPackage ePackage,
			DAnnotationParser parser, IDiagraphParser parser2,boolean canvasComposite) {
		super(logger, layer, pluginId, filePath, ePackage, parser);
		this.parser2 = parser2;
		//this.devcomposite_ = false;
		//this.newversion_ = true;
		this.canvasComposite_nu=canvasComposite;
		if (canvasComposite_nu)
			throw new RuntimeException("should not hammepn in  GmfBaseGenerator - refactor");
		//if (canvasComposite)
		//	newversion_  = true;

	}

	public void logStatements(DStatement.DCommand_ cmd) {

		int count = 0;
		for (DStatement statement : annotationParser.getDStatements()) {
			if (statement.getCommand() == cmd || cmd==null) {
				count++;
				logStatement(statement);
			}
		}
		if (count == 0)
			clog("LOGSTA empty");
	}



	protected void logStatement(DStatement statement) {//FP150420 // FP150407
		// reçoist
		// 2
		// fois
		// GRAPH_CONTAIN
		// - Foo
		// -
		// bars
		;
		// DiaNode dian = (DiaNode) statement.getElement();
		DiaContainment cont_ = (DiaContainment) statement.getContainment();

		// DiaNode dias_ = cont_.getSourceNode();
		// DiaNode diat_ = cont_.getTargetNode();

		DNode dnod_ = findDNode(statement.getDiagramElementName());
		String log = " ";
		if (dnod_ != null) {
			EClass eclaz = (EClass) dnod_.getSemanticRole();
			EReference ref_ = (EReference) eclaz
					.getEStructuralFeature(statement.getName());
			if (ref_ != null) {
				EClass reftyp = (EClass) ref_.getEType();
				if (cont_ != null) {
					if (reftyp.isAbstract()) {
						List<EClass> subtargs = cont_.getSubTargets();
						if (!subtargs.isEmpty()) {
							log += " abstract, subtargets are: ";
							for (EClass subtarget : subtargs)
								log += subtarget.getName() + ";";
						}
						EClass supertarg = cont_.getSuperTarget();
						if (supertarg != null)
							log += " abstract, is a subtarget of: "
									+ supertarg.getName();
					}
				} else
					log += " no containement";
			} else
				log += " no ref in statement";
		}
		if(DParams.Parser_15_LOG){

		 clog("LOGSTA " + statement.toString() + log);
		}
	}


	@Override
	public void pass4(DPhase phase) {

	}

	@Override
	public boolean processContainment(DStatement statement) {
		return false;// FP150331a307
	}

	protected DLabeledEdge findDLabeledEdge(String name) { // FP120719
		for (DGraphElement dGraphElement : annotationParser
				.getAllConcreteElements())
			if (dGraphElement.getName().equals(name))
				return (DLabeledEdge) dGraphElement;
		return null;
	}

	protected DNode findDNode(String name) { // FP120715
		for (DGraphElement dGraphElement : getNodes(annotationParser
				.getDGraph()))
			if (dGraphElement.getName().equals(name))
				return (DNode) dGraphElement;
		return null;
	}

	private List<DNode> getNodes(DGraph graph) { // FP140518
		// if (nodes==null){
		List<DNode> nodes = new ArrayList<DNode>();
		List<DGraphElement> elements = graph.getElements();
		for (DGraphElement element : elements) {
			if (element instanceof DNode)
				nodes.add((DNode) element);
		}
		// }
		return nodes;
	}

	protected DReference findDReference(String name) {
		for (DNode dnod : getNodes(annotationParser.getDGraph())) {
			// DNode dnod = (DNode) dGraphElement;
			List<DEdge> edgs = getEdges(dnod);
			for (DEdge edge : edgs) {
				if (edge.getName().equals(name) && (edge instanceof DReference))
					return (DReference) edge;
			}
		}
		return null;
	}

	protected DCompartmentEdge findCompartment_(DNode dnod, String name) { // FP140201
		DNestedEdge result = null;
		List<DEdge> edgs = getEdges(dnod);
		for (DEdge dEdge : edgs)
			if (dEdge.getName().equals(name)) {
				result = (DNestedEdge) dEdge;
				if (result instanceof DCompartmentEdge)
					return (DCompartmentEdge) result;
			}
		return null;
	}

	private List<DEdge> getEdges(DGraph graph) { // FP140518
		List<DEdge> result = new ArrayList<DEdge>();
		for (DGraphElement element : graph.getElements())
			if (element instanceof DEdge)
				result.add((DEdge) element);
		return result;
	}




	public List<DEdge> getEdges(DNode node) {
		List<DEdge> result = new ArrayList<DEdge>();
		for (DGraphElement element : node.getGraph().getElements()){
			if (element instanceof DNestedEdge){
				if (((DNestedEdge) element).getSource().getNode() == node)
					result.add((DEdge) element);
			} else if(element instanceof DSimpleEdge){
				 if (((DSimpleEdge) element).getSource() == node)
					result.add((DEdge) element); //FP150423b
			}
		}
		return result;
	}


	@Override
	public boolean createValidators(DPhase phase) {
		return false;

	}

	@Override
	public void initIdGenerator() {
		if (LOG)
			clog("(1) 131205 not iplemented"); // FP131205
	}

	protected void clog(String mesg) {
		if (LOG || DParams.Parser_15_LOG)
			System.out.println(mesg);

	}

}
