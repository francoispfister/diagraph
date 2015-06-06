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
public class Vertex {
	private static int id = 0;
	private int identifier;
	private String label;
	private List<Vertex> childs;
	
	private boolean isViewed;
	
	public Vertex(String label){
		id++;
		identifier += id;
		this.label = label;
		childs = new ArrayList<Vertex>();
		
		isViewed = false;
	}
	
	public void setViewed(boolean b){
		if(b){
			//The constant nodes should not be set as viewed because they should
			//always be visited
			if(!label.equals("0") && !label.equals("1") && !label.equals("n") &&
					!label.equals("true") && !label.equals("false")){
				isViewed = true;
			}
		}
		isViewed = false;
	}
	
	public boolean getIsViewed(){
		return isViewed;
	}
	
	public void addChildren(Vertex child){
		this.childs.add(child);
	}

	public String getLabel() {
		return label;
	}

	public List<Vertex> getChilds() {
		return childs;
	}
	
	
	
	public int getIdentifier() {
		return identifier;
	}

	@Override
	public String toString() {		
		return label;
	}
	
	public boolean equals(Vertex obj) {
		return obj.getIdentifier() == identifier;
	}
}
