 /**
 * Copyright (c) 2014 Laboratoire de Genie Informatique et Ingenierie de Production - Ecole des Mines d'Ales
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Blazo Nastov (ISOE-LGI2P) - initial API and implementation
 */
package org.isoe.diagraph.gramgen.similarity.subGraphIsomorphism;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.isoe.diagraph.common.DiagraphException;

/**
 *
 * @author bnastov
 *
 */
public class StandardEcoreImporter extends EcoreImporter {

private static final boolean LOG = false;
//	public static void main(String [] args){
//		String uri1 = "C:\\Users\\bnastov\\runtime-EclipseApplication\\test\\model\\DefaultName.ecore";
//		String uri2 = "C:\\Users\\bnastov\\runtime-EclipseApplication\\test\\model\\iml4.ecore";
//		String uri3 = "C:\\Users\\bnastov\\runtime-EclipseApplication\\test\\model\\content.ecore";
//
//		StandardEcoreImporter imp = new StandardEcoreImporter(uri3);
//
//		Graph g = imp.getGraph();
//
//		clog(g);
//
//		clog("***Visual Nodes***");
//		for (EClass c : imp.getVisualNodes()) {
//			clog(c.getName());
//		}
//
//	}

	private Map<Vertex, EClass> classMapping;
	private EClass modelRoot;
	private List<EClass> visualElements;

	/*

	public StandardEcoreImporter(String path) {
		this((EPackage) loadMetamodel_(path).getContents().get(0));
		impossible de charger à partir d'un fichier quand c'est packagé dans un jar //FP130527
	}*/


	//FP130611 deprecated
	public StandardEcoreImporter(EPackage model,EClass fallbackPov,boolean no_more_use_it) throws DiagraphException {
		classMapping = new HashMap<Vertex, EClass>();//bnastov

		this.graph = new Graph();
		this.model = model;
//		node0 = new Vertex("0");
//		node1 = new Vertex("1");
//		nodeN = new Vertex("n");
		nodeTrue = new Vertex("true");
		nodeFalse = new Vertex("false");

//		graph.addVertex(node0);
//		graph.addVertex(node1);
//		graph.addVertex(nodeN);
		graph.addVertex(nodeTrue);
		graph.addVertex(nodeFalse);

		try {
			modelRoot = calculatePOV();
		} catch (Exception e) {
			   if (fallbackPov==null)
				    throw new DiagraphException("A Point of View cannot be found !");
	            else
	            	modelRoot = fallbackPov;
		}
		visualElements = new ArrayList<EClass>();
		visualElements.add(modelRoot);
		calculateVisualNodes(modelRoot);
	}


	//FP130611 avoid exceptions && complex calls in the constructor
	public StandardEcoreImporter(EPackage model) throws DiagraphException {
		classMapping = new HashMap<Vertex, EClass>();//bnastov
		this.graph = new Graph();
		this.model = model;
//		node0 = new Vertex("0");
//		node1 = new Vertex("1");
//		nodeN = new Vertex("n");
		nodeTrue = new Vertex("true");
		nodeFalse = new Vertex("false");
//		graph.addVertex(node0);
//		graph.addVertex(node1);
//		graph.addVertex(nodeN);
		graph.addVertex(nodeTrue);
		graph.addVertex(nodeFalse);
	}


	//FP130606 possibility to initialize a given modelRoot
	public EClass findPov(EClass fallbackPov) throws DiagraphException {
		try {
			modelRoot = calculatePOV();
		} catch (Exception e) {
			   if (fallbackPov==null)
				    throw new DiagraphException("A Point of View cannot be found !");
	            else
	            	modelRoot = fallbackPov;
		}
		return modelRoot;
	}


	public void findVisualNodes() {
		visualElements = new ArrayList<EClass>();
		visualElements.add(modelRoot);
		calculateVisualNodes(modelRoot);
	}









	public List<EClass> getVisualElements() {
		return visualElements;
	}

	public EClass getModelRoot() {
		return modelRoot;
	}

	public Graph getGraph() {
		dumpEClass(modelRoot);
		return graph;
	}

	public Vertex dumpEClass(EClass cls) {
		if ( eltNodes.get(cls) != null )
			return eltNodes.get(cls);

		Vertex v = new Vertex("class");
//		Vertex v = new Vertex(cls.getName());
		graph.addVertex(v);
		eltNodes.put(cls,v);

		asignClassMapping(v, cls);

		for( EReference ref: cls.getEReferences() ) {
			v.addChildren(dumpEReference(ref));
		}

		//Trace the super classes
		Vertex general = null;
		for (EClass sc : cls.getESuperTypes()) {
			//Initialize the vertex
			if(general == null){
				general = new Vertex("+general");
				graph.addVertex(general);
				v.addChildren(general);
			}
			general.addChildren(dumpEClass(sc));
		}
		//Trace the sub classes
		Vertex specific = null;
		for (EClass c : getSubClasses(cls)) {
			//Initialize the vertex
			if(specific == null){
				specific = new Vertex("+specific");
				graph.addVertex(specific);
				v.addChildren(specific);
			}
			specific.addChildren(dumpEClass(c));
		}
		return v;
	}

	public Vertex dumpEReference(EReference ref) {
		if ( eltNodes.get(ref) != null)
			return eltNodes.get(ref);

		Vertex n = new Vertex("reference");
		graph.addVertex(n);
		eltNodes.put(ref,n);

		if ( ref.isContainment() )
			n.addChildren(nodeTrue);
		else
			n.addChildren(nodeFalse);

//		int upper = ref.getUpperBound();
//		int lower = ref.getLowerBound();

//		if ( upper == 0 )
//			n.addChildren(node0);
//		else if ( upper == 1 )
//			n.addChildren(node1);
//		else
//			n.addChildren(nodeN);
//
//		if ( lower == 0 )
//			n.addChildren(node0);
//		else if ( lower == 1 )
//			n.addChildren(node1);
//		else
//			n.addChildren(nodeN);

		n.addChildren(dumpEClass(ref.getEReferenceType()));

		return n;
	}

	private EClass calculatePOV() throws Exception {

		ArrayList<EClass> markedClasses = new ArrayList<EClass>();

		Iterator<EObject> it = model.eAllContents();
		while(it.hasNext()) {
			EObject o = it.next();
			if ( o instanceof EClass ){
				EClass c = (EClass)o;
				//All referenced classes
				for (EReference ref : c.getEReferences()) {
					if(ref.getEType() instanceof EClass){
						//Excluding the auto referenced classes
						if(!ref.getEType().equals(c)){
							markedClasses.add((EClass) ref.getEType());
							//Include all sub classes
							for (EClass clas : getSubClasses((EClass) ref.getEType())) {
								markedClasses.add(clas);
							}
						}
					}
				}
				//All super classes
				for (EClass st : c.getESuperTypes()) {
					markedClasses.add(st);
				}
			}
		}

		ArrayList<EClass> possiblePOVs = new ArrayList<EClass>();
		Iterator<EObject> it2 = model.eAllContents();
		while(it2.hasNext()) {
			EObject o = it2.next();
			if ( o instanceof EClass ){
				if(!markedClasses.contains(o)){
					possiblePOVs.add((EClass)o);
				}
			}
		}
		if (LOG)
		  clog(""+possiblePOVs.size());
		if(possiblePOVs.size()==0)
			throw new Exception("The POV cannot be found");
		else if(possiblePOVs.size() > 1)
			throw new Exception("Incorrect diagram : Multiple POVs found");

		return possiblePOVs.get(0);

	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);

	}

	private void calculateVisualNodes(EClass c){
		for (EReference ref : c.getEReferences()) {
			if(ref.isContainment() && ref.getEType() instanceof EClass){
				if(!visualElements.contains(ref.getEType())){
					visualElements.add((EClass) ref.getEType());
					calculateVisualNodes((EClass) ref.getEType());
				}
			}
		}
		for (EClass sc : getSubClasses(c)) {
			if(!visualElements.contains(sc)){
				visualElements.add(sc);
				calculateVisualNodes(sc);
			}
		}

	}

	private ArrayList<EClass> getSubClasses(EClass c){
		Iterator<EObject> it = model.eAllContents();
		ArrayList<EClass> result = new ArrayList<EClass>();
		while(it.hasNext()) {
			EObject o = it.next();
			if ( o instanceof EClass ){
				if(!o.equals(c) && c.isSuperTypeOf((EClass) o)){
					result.add((EClass) o);
				}
			}
		}
		return result;
	}

	private void asignClassMapping(Vertex n, EClass eClass) {
		//clog("Node : " + n.getLabel() + " classifiers id : " + classifierID);
		classMapping.put(n, eClass);
	}

	public Map<Vertex, EClass> getGraphClassMapping(){
		return classMapping;
	}

	@Override
	public String toString(){
		String result ="*****Class Mapping*****\n";
		for (Vertex node : classMapping.keySet()) {
			result += "Node name: " + node.getLabel() /*+ " Node id : " + node.getID() */
					+ " EClass : " + classMapping.get(node) + "\n";
		}
		return result;

	}
}
