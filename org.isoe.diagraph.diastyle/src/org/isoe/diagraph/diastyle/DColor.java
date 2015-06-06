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
package org.isoe.diagraph.diastyle;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>DColor</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.isoe.diagraph.diastyle.DiastylePackage#getDColor()
 * @model
 * @generated
 */
public enum DColor implements Enumerator {
	/**
	 * The '<em><b>White</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #WHITE_VALUE
	 * @generated
	 * @ordered
	 */
	WHITE(0, "white", "white"),

	/**
	 * The '<em><b>Black</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BLACK_VALUE
	 * @generated
	 * @ordered
	 */
	BLACK(1, "black", "black"),

	/**
	 * The '<em><b>Light Gray</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LIGHT_GRAY_VALUE
	 * @generated
	 * @ordered
	 */
	LIGHT_GRAY(2, "lightGray", "lightGray"),

	/**
	 * The '<em><b>Gray</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #GRAY_VALUE
	 * @generated
	 * @ordered
	 */
	GRAY(3, "gray", "gray"),

	/**
	 * The '<em><b>Dark Gray</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DARK_GRAY_VALUE
	 * @generated
	 * @ordered
	 */
	DARK_GRAY(4, "darkGray", "darkGray"),

	/**
	 * The '<em><b>Red</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RED_VALUE
	 * @generated
	 * @ordered
	 */
	RED(5, "red", "red"),

	/**
	 * The '<em><b>Orange</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ORANGE_VALUE
	 * @generated
	 * @ordered
	 */
	ORANGE(6, "orange", "orange"),

	/**
	 * The '<em><b>Yellow</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #YELLOW_VALUE
	 * @generated
	 * @ordered
	 */
	YELLOW(7, "yellow", "yellow"),

	/**
	 * The '<em><b>Green</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #GREEN_VALUE
	 * @generated
	 * @ordered
	 */
	GREEN(8, "green", "green"),

	/**
	 * The '<em><b>Light Green</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LIGHT_GREEN_VALUE
	 * @generated
	 * @ordered
	 */
	LIGHT_GREEN(9, "lightGreen", "lightGreen"),

	/**
	 * The '<em><b>Dark Green</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DARK_GREEN_VALUE
	 * @generated
	 * @ordered
	 */
	DARK_GREEN(10, "darkGreen", "darkGreen"),

	/**
	 * The '<em><b>Cyan</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CYAN_VALUE
	 * @generated
	 * @ordered
	 */
	CYAN(11, "cyan", "cyan"),

	/**
	 * The '<em><b>Light Blue</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LIGHT_BLUE_VALUE
	 * @generated
	 * @ordered
	 */
	LIGHT_BLUE(12, "lightBlue", "lightBlue"),

	/**
	 * The '<em><b>Blue</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BLUE_VALUE
	 * @generated
	 * @ordered
	 */
	BLUE(13, "blue", "blue"),

	/**
	 * The '<em><b>Dark Blue</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DARK_BLUE_VALUE
	 * @generated
	 * @ordered
	 */
	DARK_BLUE(14, "darkBlue", "darkBlue");

	/**
	 * The '<em><b>White</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>White</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #WHITE
	 * @model name="white"
	 * @generated
	 * @ordered
	 */
	public static final int WHITE_VALUE = 0;

	/**
	 * The '<em><b>Black</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Black</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #BLACK
	 * @model name="black"
	 * @generated
	 * @ordered
	 */
	public static final int BLACK_VALUE = 1;

	/**
	 * The '<em><b>Light Gray</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Light Gray</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #LIGHT_GRAY
	 * @model name="lightGray"
	 * @generated
	 * @ordered
	 */
	public static final int LIGHT_GRAY_VALUE = 2;

	/**
	 * The '<em><b>Gray</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Gray</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #GRAY
	 * @model name="gray"
	 * @generated
	 * @ordered
	 */
	public static final int GRAY_VALUE = 3;

	/**
	 * The '<em><b>Dark Gray</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Dark Gray</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DARK_GRAY
	 * @model name="darkGray"
	 * @generated
	 * @ordered
	 */
	public static final int DARK_GRAY_VALUE = 4;

	/**
	 * The '<em><b>Red</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Red</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #RED
	 * @model name="red"
	 * @generated
	 * @ordered
	 */
	public static final int RED_VALUE = 5;

	/**
	 * The '<em><b>Orange</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Orange</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ORANGE
	 * @model name="orange"
	 * @generated
	 * @ordered
	 */
	public static final int ORANGE_VALUE = 6;

	/**
	 * The '<em><b>Yellow</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Yellow</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #YELLOW
	 * @model name="yellow"
	 * @generated
	 * @ordered
	 */
	public static final int YELLOW_VALUE = 7;

	/**
	 * The '<em><b>Green</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Green</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #GREEN
	 * @model name="green"
	 * @generated
	 * @ordered
	 */
	public static final int GREEN_VALUE = 8;

	/**
	 * The '<em><b>Light Green</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Light Green</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #LIGHT_GREEN
	 * @model name="lightGreen"
	 * @generated
	 * @ordered
	 */
	public static final int LIGHT_GREEN_VALUE = 9;

	/**
	 * The '<em><b>Dark Green</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Dark Green</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DARK_GREEN
	 * @model name="darkGreen"
	 * @generated
	 * @ordered
	 */
	public static final int DARK_GREEN_VALUE = 10;

	/**
	 * The '<em><b>Cyan</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Cyan</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CYAN
	 * @model name="cyan"
	 * @generated
	 * @ordered
	 */
	public static final int CYAN_VALUE = 11;

	/**
	 * The '<em><b>Light Blue</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Light Blue</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #LIGHT_BLUE
	 * @model name="lightBlue"
	 * @generated
	 * @ordered
	 */
	public static final int LIGHT_BLUE_VALUE = 12;

	/**
	 * The '<em><b>Blue</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Blue</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #BLUE
	 * @model name="blue"
	 * @generated
	 * @ordered
	 */
	public static final int BLUE_VALUE = 13;

	/**
	 * The '<em><b>Dark Blue</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Dark Blue</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DARK_BLUE
	 * @model name="darkBlue"
	 * @generated
	 * @ordered
	 */
	public static final int DARK_BLUE_VALUE = 14;

	/**
	 * An array of all the '<em><b>DColor</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final DColor[] VALUES_ARRAY =
		new DColor[] {
			WHITE,
			BLACK,
			LIGHT_GRAY,
			GRAY,
			DARK_GRAY,
			RED,
			ORANGE,
			YELLOW,
			GREEN,
			LIGHT_GREEN,
			DARK_GREEN,
			CYAN,
			LIGHT_BLUE,
			BLUE,
			DARK_BLUE,
		};

	/**
	 * A public read-only list of all the '<em><b>DColor</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<DColor> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>DColor</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DColor get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			DColor result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>DColor</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DColor getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			DColor result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>DColor</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DColor get(int value) {
		switch (value) {
			case WHITE_VALUE: return WHITE;
			case BLACK_VALUE: return BLACK;
			case LIGHT_GRAY_VALUE: return LIGHT_GRAY;
			case GRAY_VALUE: return GRAY;
			case DARK_GRAY_VALUE: return DARK_GRAY;
			case RED_VALUE: return RED;
			case ORANGE_VALUE: return ORANGE;
			case YELLOW_VALUE: return YELLOW;
			case GREEN_VALUE: return GREEN;
			case LIGHT_GREEN_VALUE: return LIGHT_GREEN;
			case DARK_GREEN_VALUE: return DARK_GREEN;
			case CYAN_VALUE: return CYAN;
			case LIGHT_BLUE_VALUE: return LIGHT_BLUE;
			case BLUE_VALUE: return BLUE;
			case DARK_BLUE_VALUE: return DARK_BLUE;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private DColor(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue() {
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //DColor
