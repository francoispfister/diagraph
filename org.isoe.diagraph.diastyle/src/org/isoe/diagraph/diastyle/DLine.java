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
 * A representation of the literals of the enumeration '<em><b>DLine</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.isoe.diagraph.diastyle.DiastylePackage#getDLine()
 * @model
 * @generated
 */
public enum DLine implements Enumerator {
	/**
	 * The '<em><b>Solid</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SOLID_VALUE
	 * @generated
	 * @ordered
	 */
	SOLID(1, "solid", "solid"),

	/**
	 * The '<em><b>Dash</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DASH_VALUE
	 * @generated
	 * @ordered
	 */
	DASH(2, "dash", "dash"),

	/**
	 * The '<em><b>Dot</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DOT_VALUE
	 * @generated
	 * @ordered
	 */
	DOT(3, "dot", "dot"),

	/**
	 * The '<em><b>Dashdot</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DASHDOT_VALUE
	 * @generated
	 * @ordered
	 */
	DASHDOT(4, "dashdot", "dashdot"),

	/**
	 * The '<em><b>Dashdotdot</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DASHDOTDOT_VALUE
	 * @generated
	 * @ordered
	 */
	DASHDOTDOT(5, "dashdotdot", "dashdotdot"),

	/**
	 * The '<em><b>Custom</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CUSTOM_VALUE
	 * @generated
	 * @ordered
	 */
	CUSTOM(6, "custom", "custom");

	/**
	 * The '<em><b>Solid</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Solid</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SOLID
	 * @model name="solid"
	 * @generated
	 * @ordered
	 */
	public static final int SOLID_VALUE = 1;

	/**
	 * The '<em><b>Dash</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Dash</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DASH
	 * @model name="dash"
	 * @generated
	 * @ordered
	 */
	public static final int DASH_VALUE = 2;

	/**
	 * The '<em><b>Dot</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Dot</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DOT
	 * @model name="dot"
	 * @generated
	 * @ordered
	 */
	public static final int DOT_VALUE = 3;

	/**
	 * The '<em><b>Dashdot</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Dashdot</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DASHDOT
	 * @model name="dashdot"
	 * @generated
	 * @ordered
	 */
	public static final int DASHDOT_VALUE = 4;

	/**
	 * The '<em><b>Dashdotdot</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Dashdotdot</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DASHDOTDOT
	 * @model name="dashdotdot"
	 * @generated
	 * @ordered
	 */
	public static final int DASHDOTDOT_VALUE = 5;

	/**
	 * The '<em><b>Custom</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Custom</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CUSTOM
	 * @model name="custom"
	 * @generated
	 * @ordered
	 */
	public static final int CUSTOM_VALUE = 6;

	/**
	 * An array of all the '<em><b>DLine</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final DLine[] VALUES_ARRAY =
		new DLine[] {
			SOLID,
			DASH,
			DOT,
			DASHDOT,
			DASHDOTDOT,
			CUSTOM,
		};

	/**
	 * A public read-only list of all the '<em><b>DLine</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<DLine> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>DLine</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DLine get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			DLine result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>DLine</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DLine getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			DLine result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>DLine</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DLine get(int value) {
		switch (value) {
			case SOLID_VALUE: return SOLID;
			case DASH_VALUE: return DASH;
			case DOT_VALUE: return DOT;
			case DASHDOT_VALUE: return DASHDOT;
			case DASHDOTDOT_VALUE: return DASHDOTDOT;
			case CUSTOM_VALUE: return CUSTOM;
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
	private DLine(int value, String name, String literal) {
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
	
} //DLine
