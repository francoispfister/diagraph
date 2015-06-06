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
public class LabeledAssociationResponse implements MatchingResponse{

	private EClass source;
	private EClass label;
	private List<EClass> target;
	
	private EReference ls;
	private List<EReference> lt;
	private EReference sl;
	
	public LabeledAssociationResponse(EClass source, EClass label, ArrayList<EClass> target,
			EReference ls, ArrayList<EReference> lt, EReference sl){
		this.source = source;
		this.label = label;
		this.target = target;
		
		this.ls = ls;
		this.lt = lt;
		this.sl = sl;
	}

	public EClass getSource() {
		return source;
	}

	public EClass getLabel() {
		return label;
	}

	public List<EClass> getTarget() {
		return target;
	}
	
	public EReference getLs() {
		return ls;
	}

	public List<EReference> getLt() {
		return lt;
	}

	public EReference getSl() {
		return sl;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof EClass){
			return equals((EClass) o);
		}
		if(o instanceof EReference){
			return equals((EReference) o);
		}
		return false;
	}
	
//	public boolean equals(EClass o) {
//		return source.equals(o) || target.equals(o) || label.equals(o);
//	}
//	
//	public boolean equals(EReference r){
//		return ls.equals(r) || lt.equals(r) || sl.equals(r);
//	}
//
	@Override
	public String toString() {		
		return "Source : " + source.getName() + "\tLabel : " + 
				label.getName() + "\tNb of Targets : " + target.size();
	}

	@Override
	public int getResponseType() {
		// TODO Auto-generated method stub
		return 1;
	}
}
