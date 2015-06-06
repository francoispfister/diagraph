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

//import old.M2tg;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.diagraph.DiagraphPackage;
import org.isoe.diagraph.diagraphviz.dotifiers.styles.EcoreDotStyle;
import org.isoe.diagraph.diagraphviz.dotifiers.styles.M2DotDefaultStyle;
import org.isoe.diagraph.diagraphviz.dotutils.DotWriter;
import org.isoe.diagraph.diagraphviz.dotutils.GraphVizConverter;
import org.isoe.diagraph.views.ViewConstants;
import org.isoe.extensionpoint.graphviz.GraphvizParams;
import org.isoe.extensionpoint.graphviz.IDotifier;
import org.isoe.extensionpoint.xmi.IMetamodelRetriever;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.platform.resolver.Activator;
import org.isoe.diagraph.controler.IDiagraphControler; 



/**
 *
 * @author pfister
 *
 */
public class DiagraphDotifier extends DotWriter implements
		GraphvizParams, IDotifier {

	private static final boolean LOG = false;
	private static final String diagraphPlugin = DParams.DIAGRAPH_M2_PLUGIN;
	private static final String workspace = Activator.getWorkspacePath();

	private StringBuffer logs = new StringBuffer();
	private static String dslM1Plugin;
	private static String dslM1xmlns;
	private static String dslM1domain;
	private static String dslM1eNS_URI;
	private static String dslM2Plugin;
	private static String modelname;
	private static String instanceInputPath;
	private static String diagraphInputPath;
	private static String m2Path;
	private static String m2DotOutputPath;
	private static String view;// = DEFAULT_VIEW;

	// DomainRepresentation domainrepre_;

	private int INPUT_PATH = 0;
	private int LOG_PATH = 1;
	private int DOT_PATH = 2;
	private int PATH_PARAMS_LENGTH = 3;


	private IMetamodelRetriever metamodelRetriever;


	public void transform() {
		// boolean hideProxiesToEcore_=true;

		CompiledGraph domainM1WDiagraphGraph = domainM1WDiagraph(null, true); // helloworld-raw-model.dot.jpg

		ResourceSet re = domainM1WDiagraphGraph.getResourceSet();

		String as0 = domainM1WDiagraphGraph.getEdgesAsString(0);

		CompiledGraph domainM1WoDiagraphGraph = domainM1WDiagraph(re, false); // helloworld-compound-model.dot.jpg

		if (LOG) {
			for (EObject eObject : domainM1WoDiagraphGraph.getEObjects(1))
				System.out.println(eObject.toString());

			String ss_ = domainM1WoDiagraphGraph.getEdgesAsString(1);

			List<EObject> aa_ = domainM1WoDiagraphGraph.getEObjects_n(0);
			List<EObject> bb_ = domainM1WoDiagraphGraph.getEObjects_n(1);

			String cc_ = domainM1WoDiagraphGraph.getNodesAsString(0);
			String dd_ = domainM1WoDiagraphGraph.getNodesAsString(1);
		}

		CompiledGraph domainM2Graph = domainM2(re); // helloworld-ecore.dot.jpg

		CompiledGraph domainM0Graph = m0Simple(re); // helloworld-m0-model.dot.jpg

		CompiledGraph diagraphM0Simple = diagraphM0Simple(re);// helloworld-m0-diagraph.dot

		CompiledGraph diagraphM1GraphRaw = domainDiagraphToGraphRaw(re, true);// helloworld-diagraph-model_default.dot.jpg

		CompiledGraph ecoreDiagraphM2Graph_ = diagraphM2(re); // diagraph.dot.jpg

		CompiledGraph diagraphM1 = diagraphM0Simple(re);// helloworld-m0-diagraph.dot
		CompiledGraph diagraphM2_M0 = domainM2_M0b(re, ecoreDiagraphM2Graph_,
				diagraphM1);// helloworld_m2_m0.dot.jpg

		diagraphM2M2M0(ecoreDiagraphM2Graph_, diagraphM1GraphRaw);// diagraph_m2m2m0.dot.jpg
		// diagraphM2M2M0(ecoreDiagraphM2Graph,domainM0Graph);//diagraph_m2m2m0.dot.jpg

		domainM2M0(domainM2Graph, domainM0Graph); // helloworld_m2m0.dot.jpg

		if (LOG) {

			for (EObjectEdge eObjectEdge : domainM0Graph.getEdges(0)) {
				System.out.println(eObjectEdge.getSourceAnchor() + " -> "
						+ eObjectEdge.getTargetAnchor());
			}

			for (EObjectEdge eObjectEdge : domainM2Graph.getEdges(0)) {
				System.out.println(eObjectEdge.getSourceAnchor() + " -> "
						+ eObjectEdge.getTargetAnchor());
			}

			for (EObjectEdge eObjectEdge : ecoreDiagraphM2Graph_.getEdges(0)) {
				System.out.println(eObjectEdge.getSourceAnchor() + " -> "
						+ eObjectEdge.getTargetAnchor());
			}
		}

		// domainM0M2_(ecoreDiagraphM2Graph,domainM2Graph,domainM0Graph_,diagraphM1GraphRaw_);
		// //helloworld_m0m2.dot.jpg
		domainM0M2__(ecoreDiagraphM2Graph_, domainM2Graph, domainM0Graph,
				diagraphM0Simple);
        if (LOG){
		System.out.println("################################");
		System.out.print(logs.toString());
		System.out.println("################################");
        }

	}

	public void transform__() {

		CompiledGraph diagraphM0Simple = diagraphM0Simple(null);// helloworld-m0-diagraph.dot

		List<EObjectEdge> edgs3 = diagraphM0Simple.getEdges(0);
		for (EObjectEdge eObjectEdge : edgs3) {
			System.out.println(eObjectEdge.getSourceAnchor() + " -> "
					+ eObjectEdge.getTargetAnchor());
		}

		System.out.println("################################");
		System.out.print(logs.toString());
		System.out.println("################################");

	}

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

	public String connectM1ToM2_ori(List<EObjectEdge> s, List<EObjectEdge> t,
			String eClassName) {
		String trgAnchor = getEClassAnchor(t, eClassName);
		String toAdd = "";
		List<String> srcanchrs = getInstanceAnchors(s, eClassName);
		for (String srcAnchor : srcanchrs)
			toAdd += srcAnchor + " -> " + trgAnchor + ";\n";
		return toAdd;
	}

	public String connectM1ToM2_(List<EObjectEdge> e, String eClassName) {
		String trgAnchor = getEClassAnchor(e, eClassName);
		String toAdd = "";
		List<String> srcanchrs = getInstanceAnchors(e, eClassName);
		for (String srcAnchor : srcanchrs)
			toAdd += srcAnchor + " -> " + trgAnchor + ";\n";
		return toAdd;
	}

	public List<String[]> getM1ToM2Edges(List<EObjectEdge> e, String eClassName) {
		List<String> instances = getInstanceAnchors(e, eClassName);
		String eclass = getEClassAnchor(e, eClassName);
		List<String[]> result = new ArrayList<String[]>();
		for (String srcAnchor : instances) {
			String[] toAd = new String[2];
			toAd[0] = srcAnchor;
			toAd[1] = eclass;
			result.add(toAd);
		}
		return result;
	}

	String addInstanceEdges(List<EObjectEdge> all, String eClassName,
			String template) {
		String result = "";
		List<String[]> edges = getM1ToM2Edges(all, eClassName);
		for (String[] edg : edges)
			result += String.format(template, edg[0], edg[1], eClassName);
		return result;
	}

	static String template = " %s -> %s [color=gray,arrowhead=none ,label=\"instance of %s  \"];\n";

	static String[][] edges = { { "DNode", template },
			{ "DNestingEdge", template },
			{ "DPartitioningNestingEdge", template },
			{ "DReference", template }, { "DPointOfView", template }, };

	public void transform_exp() {

		CompiledGraph diagraphM2 = diagraphM2(null); // diagraph.dot.jpg
		ResourceSet re = diagraphM2.getResourceSet();

		CompiledGraph diagraphM1 = diagraphM0Simple(re);// helloworld-m0-diagraph.dot

		String newedges0a = "";

		List<EObjectEdge> edgs = diagraphM2.getEdges(0);
		for (EObjectEdge eObjectEdge : edgs) {
			if (eObjectEdge.getTarget() instanceof EClass) {
				EClass eclaz = (EClass) eObjectEdge.getTarget();

				String toAdd = " mytest  -> " + eObjectEdge.getTargetAnchor()
						+ ";\n";

				if (!newedges0a.contains(toAdd))
					newedges0a += toAdd;

			}
		}

		/******************************************************/
		CompiledGraph diagraphM2_M0 = domainM2_M0b(re, diagraphM2, diagraphM1);

		String newedges1 = "";

		int id = 0;

		List<EObjectEdge> edegsM2M0a = diagraphM2_M0.getEdges(0);
		for (EObjectEdge eObjectEdge : edegsM2M0a) {
			if (eObjectEdge.isVisible()) {

				String toAdd = " mytest1  -> " + eObjectEdge.getTargetAnchor()
						+ ";/*" + id++ + "*/\n";
				if (!newedges1.contains(toAdd))
					newedges1 += toAdd;
			}
		}

		String newedges2 = "";

		List<EObjectEdge> edegsM2M0b = diagraphM2_M0.getEdges(1);
		for (EObjectEdge eObjectEdge : edegsM2M0b) {
			if (eObjectEdge.isVisible()) {
				String toAdd = " mytest2  -> " + eObjectEdge.getTargetAnchor()
						+ ";/*" + id++ + "*/\n";
				if (!newedges2.contains(toAdd))
					newedges2 += toAdd;
			}
		}

		String newedges3_ = "";

		List<EObject> visited = new ArrayList<EObject>();

		List<EObjectEdge> edegsM2M0c = diagraphM2_M0.getEdges(1);
		for (EObjectEdge eObjectEdge : edegsM2M0c) {
			if (eObjectEdge.isVisible()) {

				if (eObjectEdge.getSource() != null) {

					if (eObjectEdge.getSource().eClass().getName()
							.equals("DNode")) {
						if (!visited.contains(eObjectEdge.getSource())) {
							visited.add(eObjectEdge.getSource());
							String toAdd = eObjectEdge.getSourceAnchor()
									+ "  -> mytest3 " + ";/*" + id++ + "*/\n";
							if (!newedges3_.contains(toAdd))
								newedges3_ += toAdd;
						}
					}

				}

			}
		}

		String dNodeTargetAnchor = getEClassAnchor(diagraphM2.getEdges(0),
				"DNode");

		// String toAdda = " mytest  -> " + dNodeTargetAnchor + ";\n";

		String toAdd_ = "";
		List<String> dnoanchrs = getInstanceAnchors(diagraphM2_M0.getEdges(1),
				"DNode");
		for (String anch : dnoanchrs)
			toAdd_ += anch + " -> " + dNodeTargetAnchor + ";\n";
		String conc = connectM1ToM2_ori(diagraphM2_M0.getEdges(1),
				diagraphM2.getEdges(0), "DNode");
		System.out.println("################################");
		System.out.print(logs.toString());
		System.out.println("################################");

	}

	public DiagraphDotifier() {
		super();
	}

	private CompiledGraph domainM2_M0b(ResourceSet rs,
			CompiledGraph ecoreDiagraphM2Graph, CompiledGraph diagraphM0Simple) {
		String ws = Activator.getWorkspacePath();
		String out = workspace + dslM1Plugin + "/"
				+ GraphvizParams.TEST_INSTANCE_FOLDER
				+ GraphvizParams.DOT_FOLDER + dslM1xmlns + M2_M0B_DOT;

		M0M2bToDot m2m0dot = new M0M2bToDot(out, ecoreDiagraphM2Graph,
				diagraphM0Simple);
		m2m0dot.handleModel();
		m2m0dot.createDotGraph();
		CompiledGraph cg = new CompiledGraph(m2m0dot, rs);
		return cg;
	}

	private CompiledGraph domainM2_M0c(ResourceSet rs,
			CompiledGraph ecoreDiagraphM2Graph, CompiledGraph diagraphM0Simple) {
		String ws = Activator.getWorkspacePath();
		String out = workspace + dslM1Plugin + "/"
				+ GraphvizParams.TEST_INSTANCE_FOLDER
				+ GraphvizParams.DOT_FOLDER + dslM1xmlns + M2_M0C_DOT;
		M0M2cToDot m2m0dot = new M0M2cToDot(out, ecoreDiagraphM2Graph,
				diagraphM0Simple, null);
		m2m0dot.handleModel();
		m2m0dot.createDotGraph();
		CompiledGraph cg = new CompiledGraph(m2m0dot, rs);
		return cg;
	}

	private CompiledGraph diagraphM0Simple(ResourceSet rs) {
		String[] locs = getDiagraphLocations("-m0-diagraph");
		EObjectGraph objectGraph = new EDiagraphObjectGraph(rs,
				locs[INPUT_PATH], locs[LOG_PATH], locs[DOT_PATH], this);

		/*
		 * objectGraph.setEdgeLabelReplacements(EDGE_LABEL_REPLACE);
		 * objectGraph.setNodeLabelReplacements(NODE_LABEL_REPLACE);
		 * objectGraph.setNodeStyles(NODE_STYLE);
		 * objectGraph.setEdgefilters(EDGE_FILTER);
		 * objectGraph.setEcoreClassfilter(ECLASS_FILTER);
		 * objectGraph.setReferencefilter(REFER_FILTER);
		 */
		objectGraph.handleModels(
				rs,
				EMFPlugin.IS_ECLIPSE_RUNNING ? URI.createPlatformPluginURI(
						dslM2Plugin, true) : URI.createFileURI(workspace
						+ dslM2Plugin), dslM1domain, dslM1xmlns, dslM1eNS_URI,
				view, null, false);
		objectGraph.createDotGraph();

		CompiledGraph r = new CompiledGraph(objectGraph, rs);
		return r;
	}

	public CompiledGraph domainDiagraphToGraphRaw(ResourceSet rs,
			boolean hideProxiesToEcore) {
		if (diagraphInputPath == null) {
			System.out.println("**no diagraphInputPath for " + dslM1xmlns);
			return null;
		}

		String instanceOutputPath_ = GraphvizParams.TEST_INSTANCE_FOLDER
				+ GraphvizParams.DOT_FOLDER + dslM1xmlns
				+ "-diagraph-model";
		String instanceDotOutputPath = instanceOutputPath_ + "_" + view
				+ ".dot";
		String instanceLogOutputPath = instanceOutputPath_ + "_" + view
				+ ".log";

		String inputFile = workspace + dslM2Plugin + "/" + diagraphInputPath;
		String logOutputFile = workspace + dslM1Plugin + "/"
				+ instanceLogOutputPath;
		String dotOutputFile = workspace + dslM1Plugin + "/"
				+ instanceDotOutputPath;

		EObjectGraph eObjectGraph = new ERawObjectGraph(rs, inputFile,
				logOutputFile, dotOutputFile, this);// ,m2Location
		// todo use a specific iterator to map leafs to Diagraph metamodel

		ResourceSet r = eObjectGraph.handleModels(
				rs,
				EMFPlugin.IS_ECLIPSE_RUNNING ? URI.createPlatformPluginURI(
						diagraphPlugin, true) : URI.createFileURI(workspace
						+ diagraphPlugin), DParams.DIAGRAPH_SUFX,
				DParams.DIAGRAPH_SUFX,
				DParams.DIAGRAPH_NSURI, view, null, true);
		return eObjectGraph.createDotGraph();
		// CompiledGraph r= new CompiledGraph(eObjectGraph);
		// return r;

	}

	public CompiledGraph m0Simple(ResourceSet rs) {
		// boolean hideProxiesToEcore=false;
		String[] locs = getLocations("-m0-model");
		EObjectGraph objectGraph = new ERawObjectGraph(rs, locs[INPUT_PATH],
				locs[LOG_PATH], locs[DOT_PATH], this);
		// ResourceSet rs=
		objectGraph.handleModels(
				rs,
				EMFPlugin.IS_ECLIPSE_RUNNING ? URI.createPlatformPluginURI(
						dslM2Plugin, true) : URI.createFileURI(workspace
						+ dslM2Plugin), dslM1domain, dslM1xmlns, dslM1eNS_URI,
				view, null, false);
		objectGraph.createDotGraph();
		CompiledGraph r = new CompiledGraph(objectGraph, rs);
		return r;

	}

	public CompiledGraph domainM1WDiagraph(ResourceSet re, boolean raw) {
		String[] locs = getLocations(raw ? "-raw-model" : "-compound-model");
		GraphVizConverter modelToGraph = new M1ToGraph(re, locs[INPUT_PATH],
				locs[LOG_PATH], this);
		DotVisitable dotGenerator = new DotM1Generator(locs[DOT_PATH], raw);
		// dotGenerator.setRoot((DGraph) modelToGraph.getRoot());
		ResourceSet r = modelToGraph.handleModels(
				re,
				EMFPlugin.IS_ECLIPSE_RUNNING ? URI.createPlatformPluginURI(
						dslM2Plugin, true) : URI.createFileURI(workspace
						+ dslM2Plugin), dslM1domain, dslM1xmlns, dslM1eNS_URI,
				view, dotGenerator, false);
		dotGenerator.createDotGraph();

		CompiledGraph cg = new CompiledGraph(modelToGraph, r);
		return cg;
	}

	private String[] getDiagraphLocations(String outputSuffix) {
		String[] result = new String[PATH_PARAMS_LENGTH];
		String instanceOutputPath = GraphvizParams.TEST_INSTANCE_FOLDER
				+ GraphvizParams.DOT_FOLDER + dslM1xmlns + outputSuffix;
		String instanceDotOutputPath = instanceOutputPath + ".dot";
		String instanceLogOutputPath = instanceOutputPath + ".log";

		result[INPUT_PATH] = workspace + dslM2Plugin + "/" + diagraphInputPath;
		// E:\Apps\eclipse-diagraph-13\workspace\diagraph.sample.helloworld\model\helloworld_default_root.diagraph
		result[LOG_PATH] = workspace + dslM1Plugin + "/"
				+ instanceLogOutputPath;
		result[DOT_PATH] = workspace + dslM1Plugin + "/"
				+ instanceDotOutputPath;
		return result;
	}

	private String[] getLocations(String outpuSuffix) {
		String[] result = new String[PATH_PARAMS_LENGTH];
		String instanceOutputPath_ = GraphvizParams.TEST_INSTANCE_FOLDER
				+ GraphvizParams.DOT_FOLDER + dslM1xmlns + outpuSuffix;// "-m0-model";
		String instanceDotOutputPath = instanceOutputPath_ + ".dot";
		String instanceLogOutputPath = instanceOutputPath_ + ".log";
		result[INPUT_PATH] = workspace + dslM1Plugin + "/" + instanceInputPath;
		result[LOG_PATH] = workspace + dslM1Plugin + "/"
				+ instanceLogOutputPath;
		result[DOT_PATH] = workspace + dslM1Plugin + "/"
				+ instanceDotOutputPath;
		return result;
	}

	public CompiledGraph diagraphM2(ResourceSet rs) {
		boolean drawAttributes = true;
		boolean drawReferencesToEnum = false;
		boolean drawDerived = false;
		boolean drawOperations = false;
		String ecorePath = workspace + DParams.DIAGRAPH_M2_PLUGIN + "/"
				+ DParams.DIAGRAPH_M2_LOCATION_;
		String out = workspace + dslM1Plugin + "/"
				+ GraphvizParams.TEST_INSTANCE_FOLDER
				+ GraphvizParams.DOT_FOLDER + DIAGRAPH_M2_DOT;
		EcoreToDot ecoreToDot = new EcoreToDot(rs, ecorePath, out,
				DiagraphPackage.eINSTANCE, drawAttributes,
				drawReferencesToEnum, drawDerived, drawOperations,
				EcoreDotStyle.getInstance(), this);
		ResourceSet r = ecoreToDot.handleModel(); // null,null,null,null
		ecoreToDot.createDotGraph();
		CompiledGraph domrepr = new CompiledGraph(ecoreToDot, r);
		return domrepr;
	}

	public CompiledGraph domainM2(ResourceSet rs) {
		String input = workspace + dslM2Plugin + "/" + m2Path;
		String output = workspace + dslM1Plugin + "/" + m2DotOutputPath;
		boolean drawAttributes = true;
		boolean drawReferencesToEnum = false;
		boolean drawDerived = false;
		boolean drawOperations = false;
		EcoreToDot domaindot = new EcoreToDot(rs, input, output,
				DiagraphPackage.eINSTANCE, drawAttributes,
				drawReferencesToEnum, drawDerived, drawOperations,
				M2DotDefaultStyle.getInstance(), this);
		ResourceSet r = domaindot.handleModel();
		domaindot.createDotGraph();
		CompiledGraph domrepr = new CompiledGraph(domaindot, r);
		return domrepr;
	}

	private void domainM0M2__(CompiledGraph diagraphMetamodel,
			CompiledGraph domainMetaModel, CompiledGraph domainModelGraph,
			CompiledGraph concreteSyntaxGraph) {
		String ws = Activator.getWorkspacePath();
		String out = workspace + dslM1Plugin + "/"
				+ GraphvizParams.TEST_INSTANCE_FOLDER
				+ GraphvizParams.DOT_FOLDER + dslM1xmlns + M0M2_DOT;
		// EPackage package0 = (EPackage) diagraphMetamodel.getRootObject();
		// //name: diagraph
		// EPackage pakag = (EPackage) domainMetaModel.getRootObject(); //:
		// helloworld) (
		// System.out.println(concreteSyntaxGraph.getRootObject());
		DGraph cs = (DGraph) concreteSyntaxGraph.getRootObject();
		M0M2ToDot ecoreToDot = new M0M2ToDot(out, diagraphMetamodel,
				domainMetaModel, domainModelGraph, concreteSyntaxGraph);
		ecoreToDot.handleModel();
		ecoreToDot.createDotGraph();
	}

	public void diagraphM2M2M0(CompiledGraph domainM2Graph,
			CompiledGraph domainM0Graph) {
		String ws = Activator.getWorkspacePath();
		String out = workspace + dslM1Plugin + "/"
				+ GraphvizParams.TEST_INSTANCE_FOLDER
				+ GraphvizParams.DOT_FOLDER + DIAGRAPH_M2M2M0_DOT;
		M2M1ToDot ecoreToDot = new M2M1ToDot(out, domainM2Graph, domainM0Graph);
		ecoreToDot.handleModel();
		ecoreToDot.createDotGraph();
	}

	public void domainM2M0(CompiledGraph domainM2Graph,
			CompiledGraph domainM0Graph) {
		String ws = Activator.getWorkspacePath();
		String out = workspace + dslM1Plugin + "/"
				+ GraphvizParams.TEST_INSTANCE_FOLDER
				+ GraphvizParams.DOT_FOLDER + dslM1xmlns + M2M0_DOT; // DIAGRAPH_M2M2M0_DOT;
		M2M1ToDot ecoreToDot = new M2M1ToDot(out, domainM2Graph, domainM0Graph);
		ecoreToDot.handleModel();
		ecoreToDot.createDotGraph();
	}

	/*------------------------------------------------------------------------------*/

	private static void raz() {
		dslM1Plugin = null;
		dslM1xmlns = null;
		dslM1domain = null;
		dslM1eNS_URI = null;
		dslM2Plugin = null;
		modelname = null;
		instanceInputPath = null;
		diagraphInputPath = null;
		m2Path = null;
		m2DotOutputPath = null;
		view = null;
	}

	public static void main(String[] args) {

		raz();
		init_helloworld();
		init();
		DiagraphDotifier diagraphDotifier = new DiagraphDotifier();
		diagraphDotifier.transform();
	}

	static void init_helloworld() {
		dslM1Plugin = "org.isoe.diagraph.diagraphviz";
		dslM1xmlns = "helloworld";
		dslM1domain = "helloworld";
		dslM1eNS_URI = "http://helloworld.example.v1";
		dslM2Plugin = "diagraph.sample.helloworld";
		modelname = "default";
		view = DEFAULT_VIEW;
	}

	static void init_simple() {
		dslM1Plugin = "org.isoe.diagraph.diagraphviz";
		dslM1xmlns = "simple";
		dslM1domain = "simple";
		dslM1eNS_URI = "http://simple.helloworld.example.v0";
		dslM2Plugin = "diagraph.sample.simple";
		modelname = "default";
		view = DEFAULT_VIEW;
	}

	static void init_iml5() {
		dslM1Plugin = "org.isoe.diagraph.diagraphviz";
		dslM1xmlns = "iml5";
		dslM1domain = "iml5";
		dslM1eNS_URI = "http://www.isoe.lgi2p.ema.fr/diagraph/example/iml5";
		dslM2Plugin = "diagraph.iml5";
		modelname = "default2";
		view = DEFAULT_VIEW;
	}

	static void init_hello() {
		dslM1Plugin = "org.isoe.diagraph.diagraphviz";
		dslM1xmlns = "hello";
		dslM1domain = "hello";
		dslM1eNS_URI = "http://hello.example.v0";
		// dslM2Plugin = "diagraph.zoo.instances.hello";
		dslM2Plugin = "diagraph.zoo.hello";
		modelname = "default";
		view = DEFAULT_VIEW;
		// view_ = null;// DEFAULT_VIEW;
	}

	static void init_link() {
		dslM1Plugin = "org.isoe.diagraph.diagraphviz";
		dslM1xmlns = "link";
		dslM1domain = "link";
		dslM1eNS_URI = "http://link.v0";
		// dslM2Plugin = "diagraph.zoo.instances.hello";
		dslM2Plugin = "diagraph.sample.link";
		modelname = "default";
		view = DEFAULT_VIEW;
		// view_ = null;// DEFAULT_VIEW;
	}

	private static void init() {
		instanceInputPath = GraphvizParams.TEST_INSTANCE_FOLDER
				+ modelname + "." + dslM1xmlns;

		diagraphInputPath = DParams.MODEL_FOLDER_SEP + dslM1domain
				+ ViewConstants.VIEW_SEPARATOR_0 + view + ViewConstants.VIEW_SEPARATOR_1 + ViewConstants.ROOT_NAME + "."
				+ DParams.DIAGRAPH_SUFX;

		m2Path = DParams.MODEL_FOLDER_SEP  + dslM1xmlns + ".ecore"; //FP130504

		m2DotOutputPath = GraphvizParams.TEST_INSTANCE_FOLDER
				+ GraphvizParams.DOT_FOLDER + dslM1xmlns + "-ecore.dot";
		java.io.File dotDirectory = new java.io.File(workspace + dslM1Plugin
				+ java.io.File.separator
				+ GraphvizParams.TEST_INSTANCE_FOLDER
				+ GraphvizParams.DOT_FOLDER);
		if (!dotDirectory.exists())
			dotDirectory.mkdir();

		System.out.println(workspace + dslM1Plugin + "/" + m2DotOutputPath);

	}

	@Override
	public void log(String mesg) {
		logs.append(mesg).append("\n");
		System.out.println("***********     " + mesg);
	}

	@Override
	public IDiagraphControler getUI() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMetamodelRetriever(IMetamodelRetriever metamodelRetriever) {
		this.metamodelRetriever = metamodelRetriever;

	}

	// private String domainDiagraphNodes;
	// private String domainDiagraphEdges;
	// private List<EObject> domainDiagraphObjects;
	// private String diagraphM2Nodes0;
	// private String diagraphM2Nodes1;
	// private String diagraphM2Edges;
	// private EPackage diagraphM2RootPackage;
	// private Map<EObject, String> diagraphM20Objects;
	// private Map<EObject, String> diagraphM2Objects;

}
