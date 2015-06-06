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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.isoe.diagraph.diagraphviz.dotutils.GraphVizConverter;
import org.isoe.diagraph.diagraphviz.dotutils.DotWriter;
import org.isoe.fwk.core.DParams;

/**
 * 
 * @author pfister 
 * An Adapter that allows  many dot graphs to be interconnected (must be populated before)
 * to be cleaned
 */
public class CompiledGraph {

	// private EObject rootObject;
	private GraphVizConverter graphvizConverter;
	private ResourceSet resourceSet;
	
	private static boolean LOG= DParams.CompiledGraph_LOG;
	

	public String getOutputPath() {
		return graphvizConverter.getOuputPath();
	}

	public List<EObjectEdge> getEdges() {
		return graphvizConverter.getEdges();
	}
	

	public String getEClassAnchor(List<EObjectEdge> edgs, String name) {
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

	public List<String> getInstanceAnchors(List<EObjectEdge> edgs,
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


	public String  connectM1ToM2(List<EObjectEdge> e, String eClassName) {
		String trgAnchor = getEClassAnchor(e,eClassName);
		String toAdd="";
		List<String> srcanchrs = getInstanceAnchors(e,eClassName);
		for (String srcAnchor : srcanchrs) 
			toAdd+=  srcAnchor + " -> "+ trgAnchor+ ";\n" ;
		return toAdd;
	}
	

	private String getAnchorId2(String name) {
		
		String result = null;
		List<EObjectEdge> edges1 = graphvizConverter.getEdges(1);
		for (EObjectEdge eObjectEdge : edges1) {
			if (LOG)
			   clog(((EClass) eObjectEdge.getTarget()).getName());

			if (((EClass) eObjectEdge.getTarget()).getName().equals(name))
				result = eObjectEdge.getTargetAnchor(); // id31125695:pid31125695
			break;

		}
		return result;
	}

	private String getAnchorId1(String name) {
		String result = null;
		// String result2=null;
		for (EObject eObject : graphvizConverter.getEdgeEnds(0).keySet()) {
			if (eObject instanceof EClass) {
				if (LOG)
				   clog(((EClass) eObject).getName() + " ?? " + name);
				if (((EClass) eObject).getName().equals(name)) {
					result = graphvizConverter.getEdgeEnds(0).get(eObject);
					break; // id31125695:pid31125695
				}
			}
		}
		return result;
	}

	/**
	 * @param i
	 *            @ see proxy mechanism in emf
	 * 
	 * @param m2
	 * @param style
	 * @return
	 */
	public String createEdgesToForeignGraph1(int section, CompiledGraph target) {
		String edges = "";
		List<EObject> eobjs = getEObjects(section);
		if (eobjs != null)
			for (EObject m0Object : eobjs) {
				String oid = target.getAnchorId1(m0Object.eClass().getName());
				if (oid == null)
					oid = target.getMatchWith(m0Object);

				if (oid != null)
					edges += DotWriter.getObjectId(m0Object)
							+ " -> "
							+ oid
							+ " "
							+ graphvizConverter
									.getStyleForEdgesToForeignGraph(m0Object)
							+ "\n";
			}
		return edges;
	}

	private String getMatchWith(EObject eObject) {
		return graphvizConverter.getMatchWith(eObject);
	}

	public String createEdgesToForeignGraph2(int section, CompiledGraph target) {
		String edges = "";
		List<EObject> eobjs = getEObjects(section);
		if (eobjs != null)
			for (EObject m0Object : eobjs) {
				String oid1 = target.getAnchorId1(m0Object.eClass().getName());
				String oid = target.getAnchorId2(m0Object.eClass().getName());
				if (oid != null)
					edges += DotWriter.getObjectId(m0Object)
							+ " -> "
							+ oid
							+ " "
							+ graphvizConverter
									.getStyleForEdgesToForeignGraph(m0Object)
							+ "\n";
			}
		return edges;
	}

	public String getNodesAsString(int section) {
		return graphvizConverter.getNodesAsString(section);
	}

	public EObject getRootObject() {
		return graphvizConverter.getRoot();
	}

	public String getEdgesAsString(int section) {
		return graphvizConverter.getEdgesAsString(section);
	}

	public CompiledGraph(GraphVizConverter diagraphVisitor, ResourceSet rs) {
		this.graphvizConverter = diagraphVisitor;
		this.resourceSet = rs;
	}

	public List<EObject> getEObjects(int section) {
		List<EObject> result = new ArrayList<EObject>();
		Map<EObject, String> edgend = graphvizConverter.getEdgeEnds(section);
		if (edgend != null && !edgend.isEmpty()) {
			Set ks = graphvizConverter.getEdgeEnds(section).keySet();
			for (Object object : ks)
				result.add((EObject) object);
		} else
			System.out.println("no foreign links for section " + section + " "
					+ graphvizConverter.getClass().getSimpleName());
		return result;
	}

	public List<EObject> getEObjects_n(int section) {
		return graphvizConverter.getNodes(section);
	}

	public List<EObjectEdge> getEdges(int section) {
		return graphvizConverter.getEdges(section);
	}

	public ResourceSet getResourceSet() {
		return resourceSet;
	}

	public Map<EObject, String> getEdgeEnds(int i) {
		return graphvizConverter.getEdgeEnds(i);
	}

	public EObject find(String cn) {
		return graphvizConverter.find(cn);
	}

	public String asDot() {
		return graphvizConverter.asDot();
	}

	
	
	
	public static void drawGraph(String filePath,String format) throws IOException  {
		File file = new File(filePath);
		String dotPath = file.getAbsolutePath();
		String imgPath = dotPath + "." + format;
		//String charset= "charset=iso-8859-1";
		String charset1= "charset=UTF-8";
		String charset2= "charset=latin1";
		String dotCommand = "dot -T" + format + " " + dotPath + " -o " + imgPath+ " -G"+charset2;
		if (LOG)
		   sclog("executing " + dotCommand);
		Process p = Runtime.getRuntime().exec(dotCommand);
		try {
		p.waitFor();
		}
		catch(InterruptedException e) {
			System.out.println("error dotifying");	
		}
	}
	
	
	


	public void addContent(String added) { //hack
		if (added != null && !added.isEmpty()) {
			String asdot = asDot();
			String end = DotWriter.GRAPH_END;
			String newContent = asdot.substring(0, asdot.indexOf(end)) + "\n";
			newContent += added;
			newContent += end;
			String outp = getOutputPath();
			if (LOG)
			  clog(newContent);
			FileWriter fw;
			try {
				fw = new FileWriter(outp);
				fw.append(newContent);
				fw.close();
				String ot_ = outp.substring(0, outp.lastIndexOf("."));
					ot_=ot_+".jpg";
				drawGraph(outp,"jpg");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}


	
	
	private static void sclog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}




}
