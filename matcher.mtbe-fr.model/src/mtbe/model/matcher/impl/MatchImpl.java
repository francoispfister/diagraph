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

import mtbe.model.matcher.Match;


/**
 * 
 * @author pfister
 * @TODO baser sur un ecore
 * 
 * 
 */


public class MatchImpl implements Match {
	

	private boolean value;
	private int similarity;
	private String rationale; // argumentation, � formaliser

	public MatchImpl(boolean value, int similarity, String rationale) {
		super();
		this.value = value;
		this.similarity = similarity;
		this.rationale = rationale;
	}


	@Override
	public String toString() {
		return "rationale=" + rationale + ", similarity=" + similarity
				+ ", value=" + value;
	}


	public boolean isTrue() {
		return value;
	}


	public int getSimilarity() {
		return similarity;
	}


	public String getRationale() {
		return rationale;
	}
	
}
