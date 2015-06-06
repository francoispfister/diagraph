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

import mtbe.model.matcher.DefaultCompareMatcher;
import mtbe.model.matcher.Match;
import mtbe.model.matcher.Matcher;
import mtbe.model.matcher.Instance;


/**
 * 
 * @author pfister
 */





public class DefaultCompareMatcherImpl implements Matcher, DefaultCompareMatcher {
	
	public DefaultCompareMatcherImpl() {
		
	}

	
	public Match match(Instance source, Instance target) {
		
		String found = "";
		int similarity = 0;	
		for (String tgt : target.getLabel().getValues()) {
			for (String src : source.getLabel().getValues()) {
					if (tgt.equals(src)) { // simulation...
						found += tgt +" ";
						similarity += tgt.length();
					}
			}
		}
		if (similarity>0)
			return new MatchImpl(true, similarity, "== " + found.trim());
		else
		    return new MatchImpl(false, 0, "NA " + source.getLabel().getValue());
	}	

	@Override
	public void setRequiredSimilarity(int requiredSimilarity) {
		//nothing for default matcher	
	}

}
