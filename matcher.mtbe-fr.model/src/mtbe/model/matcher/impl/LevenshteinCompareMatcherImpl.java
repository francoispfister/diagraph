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

import uk.ac.shef.wit.simmetrics.similaritymetrics.Levenshtein;
import mtbe.model.matcher.LevenshteinCompareMatcher;
import mtbe.model.matcher.Match;
import mtbe.model.matcher.Matcher;
import mtbe.model.matcher.Instance;
import mtbe.model.matcher.WordCompareMatcher;
import mtbe.model.matcher.helpers.MatchUtils;

/**
 * 
 * @author pfister
 *
 */
public class LevenshteinCompareMatcherImpl implements Matcher,
		LevenshteinCompareMatcher {

	private int requiredSimilarity;

	private static Levenshtein levenshtein = new Levenshtein();
	
	public void setRequiredSimilarity(int requiredSimilarity) {
		this.requiredSimilarity = requiredSimilarity;
	}	

	public LevenshteinCompareMatcherImpl(int requiredSimilarity) {
		this.requiredSimilarity = requiredSimilarity;
	}

	public Match match(Instance source, Instance target) {
		String found = "";
		String tgt = target.getLabel().getValues()[0];
		String src = source.getLabel().getValues()[0];
		double sigma = levenshtein.getSimilarity(src,tgt)*100;
		if (sigma >= requiredSimilarity) {
			found = tgt;		
		}
		if (sigma >= requiredSimilarity) 
			return new MatchImpl(true, (int) sigma, " sim " + found);
		 else
			return new MatchImpl(false, (int) sigma, "NA " + source.getLabel().getValue());
	}
}
