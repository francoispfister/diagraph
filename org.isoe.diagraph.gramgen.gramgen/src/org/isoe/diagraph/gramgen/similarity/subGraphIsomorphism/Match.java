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
public class Match {
	
	public static ArrayList<Vertex> startMatching(Graph target, Graph pattern){
		
		ArrayList<Vertex> result = new ArrayList<Vertex>();
		
		for (Vertex targetVertex : target.getVertexes()) {
			if(targetVertex.getLabel().equals(pattern.getRoot().getLabel())
					&& pattern.getRoot().getChilds().size() <= targetVertex.getChilds().size()){
				if(matchNodes(targetVertex, pattern.getRoot())){					
					result.add(targetVertex);
				}
				reinitalizeGraph(pattern);
			}
		}
		
		return result;
		
	}

	public static boolean matchNodes(Vertex T, Vertex P){
		
		if(P == null)
			return true;
		else if(T == null)
			return false;	
		else if(T.getChilds().size() == 0)
			return P.getChilds().size() == 0 && P.getLabel().equals(T.getLabel());
		else if(P.getChilds().size() == 0)
			return P.getLabel().equals(T.getLabel());
		else if(P.getChilds().size() > T.getChilds().size() ||
				!T.getLabel().equals(P.getLabel()))
			return false;
		else
			return matchChilds(T.getChilds(), P.getChilds());
	}
	
	private static boolean matchChilds(List<Vertex> targetChilds, List<Vertex> patternChilds){
		boolean childMatched; 
		for (Vertex cp : patternChilds) {
			childMatched = false;
			int i = 0;
			while(!childMatched && i < targetChilds.size()){
				if(targetChilds.get(i).getLabel().equals(cp.getLabel())){
					
					if(cp.getIsViewed()){
						//This detects a cycle
						childMatched = true;
					}else{
						cp.setViewed(true);
						childMatched = (matchNodes(targetChilds.get(i), cp));
						//return matchNodes(targetChilds.get(i), cp);
					}
				}
				i++;
			}
			if(!childMatched){
				return false;
			}
		}
		return true;
	}
	
	private static void reinitalizeGraph(Graph g){
		for (Vertex v : g.getVertexes()) {
			v.setViewed(false);
		}
	}

	private static void main(String[] args) {
		simpleTrueTest();
		simpleCycleTrueTest();
	}
	
	private static void simpleCycleTrueTest(){
		//The Target
		Vertex a = new Vertex("A");
		Vertex b = new Vertex("B");
		Vertex c = new Vertex("C");
		Vertex d = new Vertex("D");
		Vertex e = new Vertex("E");
		
		a.addChildren(b);
		b.addChildren(c);
		c.addChildren(d);
		d.addChildren(e);	
		e.addChildren(a);
		
		//The pattern
		Vertex a1 = new Vertex("A");
		Vertex b1 = new Vertex("B");
		Vertex c1 = new Vertex("C");
		Vertex d1 = new Vertex("D");
		Vertex e1 = new Vertex("E");
		
		e1.addChildren(a1);
		d1.addChildren(e1);
		c1.addChildren(d1);
		b1.addChildren(c1);
		a1.addChildren(b1);
		
		//False test
		//a1.addChildren(c1);
		
		
		System.out.println(matchNodes(a, a1));	
	}

	private static void simpleTrueTest(){
		Vertex a = new Vertex("A");
		Vertex b = new Vertex("B");
		Vertex c = new Vertex("C");
		Vertex d = new Vertex("D");
		Vertex e = new Vertex("E");
		
		a.addChildren(b);
		b.addChildren(c);
		c.addChildren(d);
		d.addChildren(e);	
		
		Vertex a1 = new Vertex("A");
		Vertex b1 = new Vertex("B");
		Vertex c1 = new Vertex("C");
		Vertex d1 = new Vertex("D");
		Vertex e1 = new Vertex("E");
		
		d1.addChildren(e1);
		c1.addChildren(d1);
		b1.addChildren(c1);
		a1.addChildren(b1);
		
		System.out.println(matchNodes(a, a1));	
	}
}
