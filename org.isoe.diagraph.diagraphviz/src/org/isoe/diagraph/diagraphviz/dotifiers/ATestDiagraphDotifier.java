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
import org.isoe.diagraph.controler.IDiagraphControler;
import org.isoe.diagraph.diagraph.DiagraphPackage;
import org.isoe.diagraph.diagraphviz.dotifiers.styles.EcoreDotStyle;
import org.isoe.diagraph.diagraphviz.dotifiers.styles.M2DotDefaultStyle;
import org.isoe.diagraph.diagraphviz.dotutils.DotWriter;
import org.isoe.diagraph.views.ViewConstants;
import org.isoe.extensionpoint.graphviz.GraphvizParams;
import org.isoe.extensionpoint.graphviz.IDotifier;
import org.isoe.extensionpoint.xmi.IMetamodelRetriever;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.platform.resolver.Activator;

//import com.sun.org.apache.xerces.internal.util.XML11Char;

/**
 *
 * @author pfister
 *
 */
public class ATestDiagraphDotifier extends DotWriter implements
		GraphvizParams, IDotifier {

	private static final boolean LOG = false;
	private static final String diagraphPlugin = DParams.DIAGRAPH_M2_PLUGIN;
	private static final String workspace = Activator.getWorkspacePath();

	private StringBuffer logs_ = new StringBuffer();
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



	private int INPUT_PATH = 0;
	private int LOG_PATH = 1;
	private int DOT_PATH = 2;
	private int PATH_PARAMS_LENGTH = 3;



	private void transform() {

		CompiledGraph diagraphM2 = diagraphM2(null); // diagraph.dot.jpg
		ResourceSet re = diagraphM2.getResourceSet();
		CompiledGraph diagraphM1 = diagraphM0Simple(re);// helloworld-m0-diagraph.dot
		CompiledGraph domainM2 = domainM2(re); // helloworld-ecore.dot.jpg
		CompiledGraph diagraphM2_M0 = domainM2_M0c(re, diagraphM2, diagraphM1,
				domainM2);
		boolean exclude = true;
		if (!exclude) {
			String toAdd1_ = "";
			List<EObjectEdge> all = diagraphM2_M0.getEdges();
			for (String[] edg : edges)
				toAdd1_ += addInstanceEdges(all, edg[0], edg[1]);

			String newedges0a_ = "";
			List<EObjectEdge> edgs = diagraphM2.getEdges(0);
			for (EObjectEdge eObjectEdge : edgs) {
				if (eObjectEdge.getTarget() instanceof EClass) {
					EClass eclaz_ = (EClass) eObjectEdge.getTarget();
					String toAdd = " mytest  -> "
							+ eObjectEdge.getTargetAnchor() + ";\n";
					if (!newedges0a_.contains(toAdd))
						newedges0a_ += toAdd;
				}
			}
		}

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
		String newedges3 = "";
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
							if (!newedges3.contains(toAdd))
								newedges3 += toAdd;
						}
					}
				}

			}
		}

		if (LOG) {
			String dNodeTargetAnchor = getEClassAnchor(diagraphM2.getEdges(0),
					"DNode");
			String toAdda_ = " mytest  -> " + dNodeTargetAnchor + ";\n";
			String toAdd = "";
			List<String> dnoanchrs = getInstanceAnchors(
					diagraphM2_M0.getEdges(1), "DNode");
			for (String anch : dnoanchrs) {
				toAdd += anch + " -> " + dNodeTargetAnchor + ";\n";
			}
			String conc = connectM1ToM2_ori(diagraphM2_M0.getEdges(1),
					diagraphM2.getEdges(0), "DNode");
			System.out.println("################################");
			System.out.print(logs_.toString());
			System.out.println("################################");
		}

	}

	private CompiledGraph domainM2_M0c(ResourceSet rs,
			CompiledGraph diagraphM2, CompiledGraph diagraphM1,
			CompiledGraph domainM2) {
		String ws = Activator.getWorkspacePath();
		String out = workspace + dslM1Plugin + "/"
				+ GraphvizParams.TEST_INSTANCE_FOLDER
				+ GraphvizParams.DOT_FOLDER + dslM1xmlns + M2_M0C_DOT;
		M0M2cToDot m2m0dot = new M0M2cToDot(out, diagraphM2, diagraphM1,
				domainM2);
		m2m0dot.handleModel();
		m2m0dot.createDotGraph();
		CompiledGraph cg = new CompiledGraph(m2m0dot, rs);
		return cg;
	}

	private CompiledGraph diagraphM0Simple(ResourceSet rs) {
		String[] locs = getDiagraphLocations("-m0-diagraph");
		EObjectGraph objectGraph = new EDiagraphObjectGraph(rs,
				locs[INPUT_PATH], locs[LOG_PATH], locs[DOT_PATH], this);
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

	private String[] getDiagraphLocations(String outputSuffix) {
		String[] result = new String[PATH_PARAMS_LENGTH];
		String instanceOutputPath = GraphvizParams.TEST_INSTANCE_FOLDER
				+ GraphvizParams.DOT_FOLDER + dslM1xmlns + outputSuffix;
		String instanceDotOutputPath = instanceOutputPath + ".dot";
		String instanceLogOutputPath = instanceOutputPath + ".log";

		result[INPUT_PATH] = workspace + dslM2Plugin + "/" + diagraphInputPath;
		result[LOG_PATH] = workspace + dslM1Plugin + "/"
				+ instanceLogOutputPath;
		result[DOT_PATH] = workspace + dslM1Plugin + "/"
				+ instanceDotOutputPath;
		return result;
	}

	private CompiledGraph diagraphM2(ResourceSet rs) {
		boolean drawAttributes = true;
		boolean drawReferencesToEnum = false;
		boolean drawDerived = false;
		boolean drawOperations = false;
		String ecorePath_ = workspace + DParams.DIAGRAPH_M2_PLUGIN + "/"
				+ DParams.DIAGRAPH_M2_LOCATION_;
		String out = workspace + dslM1Plugin + "/"
				+ GraphvizParams.TEST_INSTANCE_FOLDER
				+ GraphvizParams.DOT_FOLDER + DIAGRAPH_M2_DOT;
		EcoreToDot ecoreToDot = new EcoreToDot(rs, ecorePath_, out,
				DiagraphPackage.eINSTANCE, drawAttributes,
				drawReferencesToEnum, drawDerived, drawOperations,
				EcoreDotStyle.getInstance(), this);
		ResourceSet r = ecoreToDot.handleModel();
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

	public CompiledGraph m0Simple(ResourceSet rs) {
		String[] locs = getLocations("-m0-model");
		EObjectGraph objectGraph = new ERawObjectGraph(rs, locs[INPUT_PATH],
				locs[LOG_PATH], locs[DOT_PATH], this);
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
		ATestDiagraphDotifier diagraphDotifier = new ATestDiagraphDotifier();
		diagraphDotifier.transform();
		/*
		 * raz(); init_link(); init(); diagraphDotifier = new
		 * DiagraphDotifier(); diagraphDotifier.transform();
		 *
		 * raz(); init_simple(); init(); diagraphDotifier = new
		 * DiagraphDotifier(); diagraphDotifier.transform();
		 *
		 * raz(); init_iml5(); init(); diagraphDotifier = new
		 * DiagraphDotifier(); diagraphDotifier.transform();
		 *
		 * raz(); init_hello(); init(); diagraphDotifier = new
		 * DiagraphDotifier(); diagraphDotifier.transform();
		 */

	}

	private static void init_helloworld() {
		dslM1Plugin = "org.isoe.diagraph.diagraphviz";
		dslM1xmlns = "helloworld";
		dslM1domain = "helloworld";
		dslM1eNS_URI = "http://helloworld.example.v1";
		dslM2Plugin = "diagraph.sample.helloworld";
		modelname = "default";
		view = DEFAULT_VIEW;
	}

	private static void init_simple() {
		dslM1Plugin = "org.isoe.diagraph.diagraphviz";
		dslM1xmlns = "simple";
		dslM1domain = "simple";
		dslM1eNS_URI = "http://simple.helloworld.example.v0";
		dslM2Plugin = "diagraph.sample.simple";
		modelname = "default";
		view = DEFAULT_VIEW;
	}

	private static void init_iml5() {
		dslM1Plugin = "org.isoe.diagraph.diagraphviz";
		dslM1xmlns = "iml5";
		dslM1domain = "iml5";
		dslM1eNS_URI = "http://www.isoe.lgi2p.ema.fr/diagraph/example/iml5";
		dslM2Plugin = "diagraph.iml5";
		modelname = "default2";
		view = DEFAULT_VIEW;
	}

	private static void init_hello() {
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

	private static void init_link() {
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
		m2Path = DParams.MODEL_FOLDER_SEP  + dslM1xmlns//FP130504
				+ ".ecore";
		m2DotOutputPath = GraphvizParams.TEST_INSTANCE_FOLDER
				+ GraphvizParams.DOT_FOLDER + dslM1xmlns + "-ecore.dot";
		java.io.File dotDirectory = new java.io.File(workspace + dslM1Plugin
				+ java.io.File.separator
				+ GraphvizParams.TEST_INSTANCE_FOLDER
				+ GraphvizParams.DOT_FOLDER);
		if (!dotDirectory.exists())
			dotDirectory.mkdir();
		if (LOG)
		  System.out.println(workspace + dslM1Plugin + "/" + m2DotOutputPath);
	}

	@Override
	public void log(String mesg) {
		logs_.append(mesg).append("\n");
		System.out.println("***********     " + mesg);
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

	private String connectM1ToM2_ori(List<EObjectEdge> s, List<EObjectEdge> t,
			String eClassName) {
		String trgAnchor = getEClassAnchor(t, eClassName);
		String toAdd = "";
		List<String> srcanchrs = getInstanceAnchors(s, eClassName);
		for (String srcAnchor : srcanchrs)
			toAdd += srcAnchor + " -> " + trgAnchor + ";\n";
		return toAdd;
	}

	private List<String[]> getM1ToM2Edges(List<EObjectEdge> e, String eClassName) {
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

	private String addInstanceEdges(List<EObjectEdge> all, String eClassName,
			String template) {
		String result = "";
		List<String[]> edges = getM1ToM2Edges(all, eClassName);
		for (String[] edg : edges)
			result += String.format(template, edg[0], edg[1], eClassName);
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

	static String template = " %s -> %s [color=gray,arrowhead=none ,label=\"instance of %s  \"];\n";

	static String[][] edges = { { "DNode", template },
			{ "DNestingEdge", template },
			{ "DPartitioningNestingEdge", template },
			{ "DReference", template }, { "DPointOfView", template }, };

	@Override
	public IDiagraphControler getUI() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMetamodelRetriever(IMetamodelRetriever metamodelRetriever) {
		// TODO Auto-generated method stub

	}

}
