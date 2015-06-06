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

import java.util.HashSet;
import java.util.Set;

import mtbe.model.matcher.ILabel;
import mtbe.model.matcher.StringLabel;

public class StringLabelImpl implements ILabel, StringLabel {



	private String value;

	private int love;

	private String[] words;
	
	
    @Override
	public String[] getWords() {
		return words;
	}

	private static final String[] toReplace = { " of ", " the ", " and ",
			" by ", " for ", " in ", " by ", " with ", " a ", " le ", " de ",
			" la ", " in ", " or ", "\\(", "\\)", "\\[", "\\]", "\\.", ",",
			"\"", ":", "\\?", "'" };

	private static final String[] toRemove = { "'s " };

	private static String clean(String sentc) {
		for (String s : toRemove)
			sentc = sentc.replaceAll(s, "");

		for (String s : toReplace)
			sentc = sentc.replaceAll(s, " ");
		return sentc;
	}



	private void extractWords() {
		if (value != null && !value.isEmpty()) {
			String[] splitted = clean(value.toLowerCase()).split(" ");
			Set<String> uniques = new HashSet<String>(); // unique words
			for (String w : splitted)
				uniques.add(w);
			words = new String[uniques.size()];
			uniques.toArray(words);
			return;
		} else {
			words = new String[1];
			words[0] = ""; // attribute is empty
			return;
		}
	}

	public StringLabelImpl(String value, int love) {
		this.value = value;
		this.love = love;
		extractWords();
	}

	@Override
	public String toString() {
		return value + " (" + love + ")";
	}

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public int getLove() {
		return love;
	}

	@Override
	public String getStringValue() {
		return value;
	}

	@Override
	public String[] getValues() {
		return words;
	}

}
