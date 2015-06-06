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

import mtbe.model.matcher.ILabel;
import mtbe.model.matcher.TextLabel;

/**
 * 
 * @author pfister
 * considère le texte de l'attribut en entier (pas de découpage en mots)
 * vocation à être utilisé avec la distance de levenshtein
 * mais on fait quand même un petit nettoyage (ne serait pas obligatoire)
 *
 */
public class TextLabelImpl implements ILabel, TextLabel {



	private String value;

	private int love;

	private String text;
	
	
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

	private void extractText() {
		if (value != null && !value.isEmpty()) {
			text = clean(value.toLowerCase());
			return;
		} else {
			text = ""; // attribute is empty
			return;
		}
	}

	public TextLabelImpl(String value, int love) {
		this.value = value;
		this.love = love;
		extractText();
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
	public String getText() {
		return text;
	}

	@Override
	public String[] getValues() {
		String[] result = new String[1];
		result[0]=text;
		return result;
	}



}
