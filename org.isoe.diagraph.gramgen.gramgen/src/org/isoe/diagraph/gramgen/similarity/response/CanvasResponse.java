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
package org.isoe.diagraph.gramgen.similarity.response;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;

/**
 * 
 * @author bnastov
 *
 */
public class CanvasResponse implements MatchingResponse{
	private EClass canvas;
	private List<EClass> topLevelNodes;
	
	public CanvasResponse(EClass canvas){
		this.canvas = canvas;
		topLevelNodes = findCanvasNodes(canvas);
	}

	private List<EClass> findCanvasNodes(EClass canvas) {
		ArrayList<EClass> result = new ArrayList<EClass>();
		for (EReference ref : canvas.getEReferences()) {			
			//Verifying if the reference is an containment
			if(ref.isContainment() && !ref.getEType().equals(canvas)){
				//add it into result
				result.add(ref.getEReferenceType());
			}
		}	
		return result;
	}

	public EClass getCanvas() {
		return canvas;
	}

	public List<EClass> getTopLevelNodes() {
		return topLevelNodes;
	}

	@Override
	public int getResponseType() {
		// TODO Auto-generated method stub
		return 4;
	}

}
