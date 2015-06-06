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
import java.util.List;

/**
 * 
 * @author bnastov
 *
 */
public class Graph {
	private List<Vertex> vertexes;
	
	public Graph(){
		vertexes = new ArrayList<Vertex>();
	}
	
	public void addVertex(Vertex vertex){
		vertexes.add(vertex);
	}

	public List<Vertex> getVertexes() {
		return vertexes;
	}
	
	public Vertex getRoot(){
		for (Vertex v : vertexes) {
			if(v.getLabel().equals("class"))
				return v;
		}
		return null;
	}
	
	@Override
	public String toString() {
		String result ="";
		for (Vertex v1 : vertexes) {
			result += "Vertex : " + v1 + " has childrens {";
			for (Vertex v2 : v1.getChilds()) {
				result += " " + v2 + " ";
			}
			result += "}\n";
		}
		
		return result;
	}
	
}
