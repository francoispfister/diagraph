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

import mtbe.model.matcher.Instance;
import mtbe.model.matcher.Match;
import mtbe.model.matcher.Matcher;
import mtbe.model.matcher.WordCompareMatcher;

/**
 * 
 * @author pfister
 *
 */
public class WordCompareMatcherImpl implements Matcher, WordCompareMatcher {

	public void setRequiredSimilarity(int requiredSimilarity) {
		this.requiredSimilarity = requiredSimilarity;
	}

	private int requiredSimilarity;
	

	public WordCompareMatcherImpl(int requiredSimilarity) {
		this.requiredSimilarity = requiredSimilarity;
	}

	String[] srv, trv;

	/**
	 * @author A.Dogui
	 * @param source
	 * @param target
	 * @return Comparaison avec la distance de levenstein (Aymen DOGUI)
	 */
	public Match matchLevenshtein(Instance source, Instance target) {
		int maxDistance = requiredSimilarity;
		String txtSource = source.getLabel().getStringValue();
		String txtTarget = target.getLabel().getStringValue();
		int distance = 10000;
		if (txtSource == null || txtTarget == null) {
			throw new IllegalArgumentException("Strings must not be null");
		}
		int n = txtSource.length();
		int m = txtTarget.length();
		if (n == 0) {
			distance = m;
		} else if (m == 0) {
			distance = n;
		}
		int p[] = new int[n + 1];
		int d[] = new int[n + 1];
		int _d[];
		int i;
		int j;
		char t_j;
		int cost;
		for (i = 0; i <= n; i++) {
			p[i] = i;
		}
		for (j = 1; j <= m; j++) {
			t_j = txtTarget.charAt(j - 1);
			d[0] = j;
			for (i = 1; i <= n; i++) {
				cost = txtSource.charAt(i - 1) == t_j ? 0 : 1;
				d[i] = Math.min(Math.min(d[i - 1] + 1, p[i] + 1), p[i - 1]
						+ cost);
			}
			_d = p;
			p = d;
			d = _d;
		}
		distance = p[n];
		if (distance <= maxDistance) {
			return new MatchImpl(true, distance, "maxDistance= " + maxDistance);
		} else
			return new MatchImpl(false, 0, "NA "
					+ source.getLabel().getStringValue());
	}

	
	public Match match(Instance source, Instance target) {
		//return matchLevenshtein(source, target);
		return matchWords(source, target);
	}

	public Match matchWords(Instance source, Instance target) {
		String found = "";
		int maxmatch = Math.max(target.getLabel().getValues().length, source
				.getLabel().getValues().length); // 4
		int requirableSimilarity = Math.max(1, Math.min(maxmatch,
				requiredSimilarity)); // 2
		if (requirableSimilarity > source.getLabel().getValues().length)
			requirableSimilarity = source.getLabel().getValues().length;
		if (requirableSimilarity > target.getLabel().getValues().length)
			requirableSimilarity = target.getLabel().getValues().length;

		int wordsFound = 0;
		for (String tgt : target.getLabel().getValues()) {
			for (String src : source.getLabel().getValues()) {
				// if (tgt.length() > 2) // simulation...
				if (tgt.equals(src)) { // simulation...
					found += tgt + " ";
					wordsFound++;
				}
			}
		}
		if (wordsFound >= requirableSimilarity) {
			double ratio = (wordsFound / requirableSimilarity) * 100;
			return new MatchImpl(true, (int) ratio, "wf=" + wordsFound + " == "
					+ found.trim());
		} else
			return new MatchImpl(false, 0, "NA " + source.getLabel().getValue());
	}

}
