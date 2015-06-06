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

/**
 * 
 * @author pfister
 * cloned from GmfGraph
 *
 */
public enum DiaColor implements Enumerator
{

	WHITE_LITERAL(0, "white", "white"),

	BLACK_LITERAL(1, "black", "black"),

	LIGHT_GRAY_LITERAL(2, "lightGray", "lightGray"),

	GRAY_LITERAL(3, "gray", "gray"),

	DARK_GRAY_LITERAL(4, "darkGray", "darkGray"),

	RED_LITERAL(5, "red", "red"),

	ORANGE_LITERAL(6, "orange", "orange"),

	YELLOW_LITERAL(7, "yellow", "yellow"),

	GREEN_LITERAL(8, "green", "green"),

	LIGHT_GREEN_LITERAL(9, "lightGreen", "lightGreen"),

	DARK_GREEN_LITERAL(10, "darkGreen", "darkGreen"),

	CYAN_LITERAL(11, "cyan", "cyan"),

	LIGHT_BLUE_LITERAL(12, "lightBlue", "lightBlue"),

	BLUE_LITERAL(13, "blue", "blue"),

	DARK_BLUE_LITERAL(14, "darkBlue", "darkBlue");

	public static final int WHITE = 0;

	public static final int BLACK = 1;

	public static final int LIGHT_GRAY = 2;

	public static final int GRAY = 3;

	public static final int DARK_GRAY = 4;


	public static final int RED = 5;


	public static final int ORANGE = 6;

	public static final int YELLOW = 7;


	public static final int GREEN = 8;

	public static final int LIGHT_GREEN = 9;


	public static final int DARK_GREEN = 10;

	public static final int CYAN = 11;

	public static final int LIGHT_BLUE = 12;


	public static final int BLUE = 13;


	public static final int DARK_BLUE = 14;

	private static final DiaColor[] VALUES_ARRAY =
		new DiaColor[] {
			WHITE_LITERAL,
			BLACK_LITERAL,
			LIGHT_GRAY_LITERAL,
			GRAY_LITERAL,
			DARK_GRAY_LITERAL,
			RED_LITERAL,
			ORANGE_LITERAL,
			YELLOW_LITERAL,
			GREEN_LITERAL,
			LIGHT_GREEN_LITERAL,
			DARK_GREEN_LITERAL,
			CYAN_LITERAL,
			LIGHT_BLUE_LITERAL,
			BLUE_LITERAL,
			DARK_BLUE_LITERAL,
		};


	public static final List<DiaColor> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));


	public static DiaColor get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			DiaColor result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}


	public static DiaColor getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			DiaColor result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}


	
	
	public static DiaColor get(int value) {
		switch (value) {
			case WHITE: return WHITE_LITERAL;
			case BLACK: return BLACK_LITERAL;
			case LIGHT_GRAY: return LIGHT_GRAY_LITERAL;
			case GRAY: return GRAY_LITERAL;
			case DARK_GRAY: return DARK_GRAY_LITERAL;
			case RED: return RED_LITERAL;
			case ORANGE: return ORANGE_LITERAL;
			case YELLOW: return YELLOW_LITERAL;
			case GREEN: return GREEN_LITERAL;
			case LIGHT_GREEN: return LIGHT_GREEN_LITERAL;
			case DARK_GREEN: return DARK_GREEN_LITERAL;
			case CYAN: return CYAN_LITERAL;
			case LIGHT_BLUE: return LIGHT_BLUE_LITERAL;
			case BLUE: return BLUE_LITERAL;
			case DARK_BLUE: return DARK_BLUE_LITERAL;
		}
		return null;
	}

	private final int value;
	private final String name;
	private final String literal;
	private DiaColor(int value, String name, String literal) {
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
