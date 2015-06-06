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
import mtbe.model.matcher.Matcher;
import mtbe.model.matcher.Instance;
import mtbe.model.matcher.WordCompareMatcher;



public class ValueCompareMatcherImpl implements Matcher, WordCompareMatcher {


	public void setRequiredSimilarity(int requiredSimilarity) {
		this.requiredSimilarity = requiredSimilarity;
	}

	private int requiredSimilarity;

	public ValueCompareMatcherImpl(int requiredSimilarity) {
		this.requiredSimilarity = requiredSimilarity;
	}

	public Match match(Instance source, Instance target) {
		int similarity = 10; //arbitraire pour l'instant
		String sourceValue = source.getLabel().getStringValue();
		if (sourceValue!=null && sourceValue.equals(target.getLabel().getStringValue()))
			 return new MatchImpl(true, similarity, "== " + sourceValue);
		else
			 return new MatchImpl(false, 0, "NA " + source.getLabel().getValue());
	}

}
