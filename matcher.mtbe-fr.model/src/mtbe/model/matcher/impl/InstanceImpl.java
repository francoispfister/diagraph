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
package mtbe.model.matcher.impl;


import java.util.ArrayList;
import java.util.List;

import mtbe.model.matcher.Instance;
import mtbe.model.matcher.ILabel;
import mtbe.model.matcher.Match;

import org.eclipse.emf.ecore.EObject;


public class InstanceImpl implements Instance {
	

	private EObject eObject;
	private ILabel label;
	//private String[] words;
	
	/*
	
	public Match match(Instance other) {
		String found = "";
		int similarity = 0;	
		for (String tgt :label.getWords()) {
			for (String src : other.getLabel().getWords()) {
					if (tgt.equals(src)) { // simulation...
						found += tgt +" ";
						similarity += tgt.length();
					}
			}
		}
		if (similarity>0)
			return new MatchImpl(true, similarity, "== " + found.trim());
		else
		    return new MatchImpl(false, 0, "NA " + other.getLabel().getValue());
	}	*/
	
/*
	private void extractWords_(){
		if (label!=null){
			String labelvalue = label.getStringValue();
			if (labelvalue != null && !labelvalue.isEmpty()){
				labelvalue = labelvalue.toLowerCase();
				String[] splitted= labelvalue.split(" ");
				List<String> sl = new ArrayList<String>();
				for (String w : splitted) {
					if (w.length()>2) //not care of determiners
						sl.add(w);
				}
				words = new String[sl.size()];
				sl.toArray(words);
				return;
			} else{
				words = new String[1];
				words[0]=""; //attribute is empty
				return;
			}
			
		} else{
			System.out.println("pb 1 extractWords()");
			words = new String[1];
			words[0]="????";
			return;
		}
	}
*/
	public InstanceImpl(EObject eObject, ILabel label) {
		super();
		this.eObject = eObject;
		this.label = label;
	//	extractWords_();
	}

	public EObject getObject() {
		return eObject;
	}

	public ILabel getLabel() {
		return label;
	}

	@Override
	public String toString() {
		return eObject.eClass().getName() + ": " + label.toString();
	}
	/*
	public String[] getWords_() {
		return words;
	}
*/

}
