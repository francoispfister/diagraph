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
package org.isoe.diagraph.common;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;
import org.isoe.diagraph.diastyle.helpers.StyleKeywords;

/**
 * 
 * @author pfister
 *
 */
public enum DiaLayout implements Enumerator
{

	NONE_LITERAL(0, "none", "none"),
	
	FREE_LITERAL(1, StyleKeywords.DIASTYLE_LAYOUT_FREE, StyleKeywords.DIASTYLE_LAYOUT_FREE),

	LIST_LITERAL(2, "list", "list");

	public static final int NONE = 0;
	
	public static final int FREE = 1;

	public static final int LIST = 2;

	
	private static final DiaLayout[] VALUES_ARRAY =
		new DiaLayout[] {
		NONE_LITERAL,
		FREE_LITERAL,
		LIST_LITERAL,
		};


	public static final List<DiaLayout> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));


	public static DiaLayout get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			DiaLayout result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}


	public static DiaLayout getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			DiaLayout result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}


	public static DiaLayout get(int value) {
		switch (value) {
		    case NONE: return NONE_LITERAL;
		    case FREE: return FREE_LITERAL;
			case LIST: return LIST_LITERAL;
		}
		return null;
	}

	private final int value;
	private final String name;
	private final String literal;
	private DiaLayout(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}


	public int getValue() {
	  return value;
	}
	public String getName() {
	  return name;
	}
	public String getLiteral() {
	  return literal;
	}

	@Override
	public String toString() {
		return literal;
	}
}
