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
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.diagraph.DGraphElement;
import org.isoe.diagraph.diagraph.DNode;


/**
 *
 * @author pfister
 *
 */
public class M0M2ToDot extends DotWriter implements DotConstants, DotVisitable,
		GraphVizConverter {

	private static final boolean LOG = false;
	protected String outputFile;
	private String dot;
	private DGraph concreteSyntax;
	private CompiledGraph diagraphMetamodel_;
	private CompiledGraph domainMetaModel;
	private CompiledGraph domainModelGraph_;
	private CompiledGraph concreteSyntaxGraph_;



	@Override
	public List<EObjectEdge> getEdges() {
		return null;
	}


	@Override
	public String getOuputPath() {
		return this.outputFile;
	}


	public M0M2ToDot(String outputFile, CompiledGraph diagraphMetamodel,
			CompiledGraph domainMetaModel, CompiledGraph domainModel_,
			CompiledGraph concreteSyntaxgraph_) {
		this.outputFile = outputFile;
		this.diagraphMetamodel_ = diagraphMetamodel;
		this.domainMetaModel = domainMetaModel;
		this.domainModelGraph_ = domainModel_;
		this.concreteSyntaxGraph_ = concreteSyntaxgraph_;

		System.out.println(concreteSyntaxGraph_.getRootObject());
		concreteSyntax = (DGraph) concreteSyntaxGraph_.getRootObject(); // FP130204
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

	private EObject getSource(List<EObjectEdge> ededges, String eclassName) {
		for (EObjectEdge edg : ededges) {
			if (edg.getSource() instanceof EClass) {
				EClass eclaz = (EClass) edg.getSource();
				if (eclaz.getName().equals(eclassName))
					return edg.getSource();
			}
		}
		return null;
	}


	private EObject getTarget(List<EObjectEdge> ededges, String eclassName) {
		for (EObjectEdge edg : ededges) {
			if (edg.getTarget() instanceof EClass) {
				EClass eclaz = (EClass) edg.getTarget();
				if (eclaz.getName().equals(eclassName))
					return edg.getSource();
			}
		}
		return null;
	}



	public String toDot() {
		int subgraphes = 1;

		dot = startGraph() + "\n";

		dot += startSubGraph(subgraphes++, true);
		dot += m2Label("A" + subgraphes) + ";" + "\n";
		dot += diagraphMetamodel_.getNodesAsString(0);
		dot += endSubGraph() + "\n";

		dot += startSubGraph(subgraphes++, true);
		dot += m2Label("B" + subgraphes) + ";" + "\n";
		dot += diagraphMetamodel_.getNodesAsString(1);
		dot += endSubGraph() + "\n";

		dot += startSubGraph(subgraphes++, true);
		dot += m2Label("C" + subgraphes) + ";" + "\n";
		dot += domainMetaModel.getNodesAsString(0);
		dot += endSubGraph() + "\n";

		dot += startSubGraph(subgraphes++, true);
		dot += m2Label("D" + subgraphes) + ";" + "\n";
		dot += domainMetaModel.getNodesAsString(1);
		dot += endSubGraph() + "\n";

		dot += startSubGraph(subgraphes++, "trapezium", true);
		dot += m2Label("domainModelGraph E" + subgraphes) + ";" + "\n";
		dot += domainModelGraph_.getNodesAsString(1);
		dot += endSubGraph() + "\n";

		dot += startSubGraph(subgraphes++, "oval", true);
		dot += m2Label("concreteSyntaxGraph F" + subgraphes) + ";" + "\n";
		dot += concreteSyntaxGraph_.getNodesAsString(1);
		dot += endSubGraph() + "\n";

		dot += "/*1 diagraphMetamodel Edges*/\n\n";

		String eas1= diagraphMetamodel_.getEdgesAsString(0);
		String eas2= diagraphMetamodel_.getEdgesAsString(1);



		dot += eas1;
		dot += eas2;

		List<EObjectEdge> oes=diagraphMetamodel_.getEdges(0);
		for (EObjectEdge eObjectEdge : oes) {
			System.out.println(eObjectEdge.toString());
		}


		dot += "/*2 domainMetaModel Edges 0 */\n\n";
		dot += domainMetaModel.getEdgesAsString(0);

		dot += "/*2 domainMetaModel Edges 1 */\n\n";
		dot += domainMetaModel.getEdgesAsString(1);

		dot += "/*1  domainModelGraph Edges*/\n\n";
		dot += domainModelGraph_.getEdgesAsString(0);

		dot += "/*3 concreteSyntaxGraph Edges*/\n\n";
		dot += concreteSyntaxGraph_.getEdgesAsString(0);

		dot += "/*4 domainModelGraph edgesToForeignGraph domainMetaModel Edges*/\n\n";
		dot += domainModelGraph_.createEdgesToForeignGraph1(0, domainMetaModel);
		// dot += domainModel.createEdgesToForeignGraph1(0,concreteSyntaxGraph);

		// dot +=
		// "/*5 concreteSyntaxGraph edgesToForeignGraph domainMetaModel Edges*/\n\n";
		// dot +=
		// concreteSyntaxGraph_.createEdgesToForeignGraph1(0,domainMetaModel);

		// dot +=
		// domainMetaModel.createEdgesToForeignGraph2(0,diagraphMetamodel);

		String res = "";
		System.out.println("+++  concreteSyntax.getNodes");
		for (DNode dNode : getNodes(concreteSyntax))
			res += proxiedEClassName(dNode) + " + ";
		res = res.substring(0, res.length() - 3);
		System.out.println(" ********* " + res + " ********* ");

		List<EObjectEdge> edgs3 = domainMetaModel.getEdges(0);
		System.out.println("***  domainMetaModel");
		for (EObjectEdge edg3 : edgs3) {
			System.out
					.println(((edg3.isEcoreObject(edg3.getSource())
							|| (edg3.isEcoreObject(edg3.getTarget())) ? " ++ "
							: "    ") + edg3.toString()));
		}
		System.out.println("1-------------");

		// languages;toAnother;researcher;saysHello;motherTongue;speaks;annotations;paragraphs;sections;entries;bibliography;publication;researcher;authors;annotates;researchers;publications;-------------




		List<EObjectEdge> edgs4_ = diagraphMetamodel_.getEdges(0);
		System.out.println("***  diagraphMetamodel");
		for (EObjectEdge edg4_ : edgs4_) {
			boolean isec = edg4_.isEcoreObject(edg4_.getSource())
					|| (edg4_.isEcoreObject(edg4_.getTarget()));
	
			System.out.println((isec ? " ++ " : "    ") + edg4_.toString());

			String sanch = "";
			String tanch = "";
			if (edg4_.getSource() instanceof EClass) {
				EClass eclaz = (EClass) edg4_.getSource();
				if (eclaz.getName().equals("DNode"))
					sanch = edg4_.getSourceAnchor();
			}
			if (edg4_.getTarget() instanceof EClass) {
				EClass eclaz = (EClass) edg4_.getTarget();
				if (eclaz.getName().equals("DNode"))
					tanch = edg4_.getTargetAnchor();
			}


		}

		System.out.println("2-------------");
		// edges;nodes;pointOfView;graph;roleInCsGraph;eSuperTypes;eType;eContainingClass;eStructuralFeatures;eClass;labelAttributes;owner;target;source;eOpposite;eKeys;targetReference;sourceReference;-------------

		List<EObjectEdge> edgs6_ = domainModelGraph_.getEdges(0);
		System.out.println("***  domainModel");
		for (EObjectEdge edg6 : edgs6_) {
			boolean isec = edg6.isEcoreObject(edg6.getSource())
					|| (edg6.isEcoreObject(edg6.getTarget()));

			System.out.println((isec ? " ++ " : "    ") + edg6.toString());
		}

		System.out.println("4-------------");

		List<EObjectEdge> edgs7_ = concreteSyntaxGraph_.getEdges(0);
		System.out.println("***  concreteSyntaxGraph");
		for (EObjectEdge edg7 : edgs7_) {

			if (edg7.isVisible()) {
				boolean isec = edg7.isEcoreObject(edg7.getSource())
						|| (edg7.isEcoreObject(edg7.getTarget()));

				System.out.println((isec ? " ++ " : "    ") + edg7.toString());
			}
		}

		System.out.println("5-------------");

		List<EObjectEdge> concreteSyntaxedgs = concreteSyntaxGraph_.getEdges(0);
		System.out.println("***  concreteSyntaxGraph");

		dot += "/*5 xxxxxs*/\n\n";



		for (EObjectEdge concreteSyntaxedg : concreteSyntaxedgs) {

			if (concreteSyntaxedg.isVisible()) {

				EObject st = concreteSyntaxedg.getTarget();
		
				EObject r_=st.eResource().getContents().get(0);
				if (r_ instanceof DGraph){
					String cn=st.eClass().getName();

					System.out.println(cn);

					EObject f=diagraphMetamodel_.find(cn);

					Set<EObject> ks=diagraphMetamodel_.getEdgeEnds(0).keySet();
					for (EObject eObject : ks) {
						if(!eObject.eClass().getName().equals(cn))		
							 System.out.println(eObject.eClass().getName()+"..."+diagraphMetamodel_.getEdgeEnds(0).get(eObject));
					}


					Set<EObject> ks2=diagraphMetamodel_.getEdgeEnds(1).keySet();
					for (EObject eObject : ks2) {
						if(!eObject.eClass().getName().equals(cn))
							 System.out.println(eObject.eClass().getName()+"..."+diagraphMetamodel_.getEdgeEnds(0).get(eObject));
					}



					EObject sro=getSource(diagraphMetamodel_.getEdges(0),cn);
					if (sro!=null){
					  String srnn=sro.eClass().getName();
					  System.out.println(srnn);
					} else
						System.out.println("no "+cn);

					EObject tro=getTarget(diagraphMetamodel_.getEdges(0),cn);
					if (tro!=null){
						  String trnn=tro.eClass().getName();
						  System.out.println(trnn);
						} else
							System.out.println("no "+cn);

				}

			}
		}

		// dot += m0Graph.createEdgesToForeignGraph(0,m2Graph);

		dot += endGraph() + "\n";
		// dot = "TODO ....";

		return dot;
	}
	
	   private List<DNode> getNodes(DGraph graph){ //FP140518
		//if (nodes==null){
		   List<DNode> nodes = new ArrayList<DNode>();
		   List<DGraphElement> elements = graph.getElements();
		   for (DGraphElement element : elements) {
			 if(element instanceof DNode)
				 nodes.add((DNode) element);
		   }
		//}
		   return nodes; 
	   }

	/**
	 * clean this hack
	 *
	 * @param dNode
	 * @return
	 */
	private String proxiedEClassName(DNode dNode) {
		String e = null;
		String eclassAsString = dNode.getEClaz().toString();
		if (eclassAsString.contains("(eProxyURI: file:")) {
			e = eclassAsString.substring(
					eclassAsString.indexOf(".ecore#//") + 9,
					eclassAsString.length() - 1);
		}
		return e;
	}


	@Override
	public String getStyleForEdgesToForeignGraph(EObject eObject) {
		return "label=\"???\"";// " [color=gray,arrowhead=none ,label=\"instance of "+eObject.eClass().getName()+"   \"];";
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
	public ResourceSet handleModels(ResourceSet rs, URI uriDslM2,
			String domain, String xmlns, String eNS_URI, String view,
			DotVisitable visitable, boolean diagraph) {
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
	 * @Override public void setEdgeEnds(int section, Map<EObject, String>
	 * edgeEnd) { // TODO Auto-generated method stub
	 *
	 * }
	 */

	private List<EObjectEdge>edges_ = new ArrayList<EObjectEdge>();

	@Override
	public List<EObjectEdge> getEdges(int i) {
		// TODO FP130201
		return edges_;
	}

	@Override
	public List<EObject> getNodes(int section) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMatchWith(EObject eObject) {
		System.out.println("getMatchWith " + "in M0M2ToDot");

		/*
		 *
		 * List<EObjectEdge> diagraphMetamodelEdges=
		 * diagraphMetamodel.getEdges(0); for (EObjectEdge eObjectEdge :
		 * diagraphMetamodelEdges) {
		 * System.out.println(eObjectEdge.getSource().toString());
		 * System.out.println(eObjectEdge.getTarget().toString());
		 *
		 * if (eObjectEdge.getTarget()==eObject) tb = true;
		 *
		 * if (eObjectEdge.getSource()==eObject) tb = true; }
		 *
		 * diagraphMetamodelEdges= diagraphMetamodel.getEdges(1); for
		 * (EObjectEdge eObjectEdge : diagraphMetamodelEdges) {
		 * System.out.println(eObjectEdge.getSource().toString());
		 * System.out.println(eObjectEdge.getTarget().toString());
		 *
		 * if (eObjectEdge.getTarget()==eObject) tb = true;
		 *
		 * if (eObjectEdge.getSource()==eObject) tb = true; }
		 *
		 * diagraphMetamodelEdges= domainMetaModel.getEdges(0); for (EObjectEdge
		 * eObjectEdge : diagraphMetamodelEdges) { if
		 * (eObjectEdge.getTarget()==eObject) tb = true;
		 *
		 * if (eObjectEdge.getSource()==eObject) tb = true;
		 *
		 * } diagraphMetamodelEdges= domainMetaModel.getEdges(1); for
		 * (EObjectEdge eObjectEdge : diagraphMetamodelEdges) { if
		 * (eObjectEdge.getTarget()==eObject) tb = true;
		 *
		 * if (eObjectEdge.getSource()==eObject) tb = true;
		 *
		 * }
		 *
		 * diagraphMetamodelEdges= domainModelGraph_.getEdges(0); for
		 * (EObjectEdge eObjectEdge : diagraphMetamodelEdges) { if
		 * (eObjectEdge.getTarget()==eObject) tb = true;
		 *
		 * if (eObjectEdge.getSource()==eObject) tb = true;
		 *
		 * } diagraphMetamodelEdges= domainModelGraph_.getEdges(1); for
		 * (EObjectEdge eObjectEdge : diagraphMetamodelEdges) { if
		 * (eObjectEdge.getTarget()==eObject) tb = true;
		 *
		 * if (eObjectEdge.getSource()==eObject) tb = true;
		 *
		 * }
		 *
		 * diagraphMetamodelEdges= concreteSyntaxGraph_.getEdges(0); for
		 * (EObjectEdge eObjectEdge : diagraphMetamodelEdges) { if
		 * (eObjectEdge.getTarget()==eObject) tb = true;
		 *
		 * if (eObjectEdge.getSource()==eObject) tb = true;
		 *
		 * } diagraphMetamodelEdges= concreteSyntaxGraph_.getEdges(1); for
		 * (EObjectEdge eObjectEdge : diagraphMetamodelEdges) { if
		 * (eObjectEdge.getTarget()==eObject) tb = true;
		 *
		 * if (eObjectEdge.getSource()==eObject) tb = true;
		 *
		 * }
		 *
		 *
		 *
		 *
		 *
		 *
		 * for (EObjectEdge eObjectEdge : edges) {
		 * System.out.println(eObjectEdge.getSource().toString());
		 * System.out.println(eObjectEdge.getTarget().toString());
		 *
		 * if (eObjectEdge.getTarget()==eObject) tb = true;
		 *
		 * if (eObjectEdge.getSource()==eObject) tb = true;
		 *
		 *
		 *
		 * }
		 */

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
