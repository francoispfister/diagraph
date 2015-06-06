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
 * A representation of the literals of the enumeration '<em><b>DShape</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.isoe.diagraph.diastyle.DiastylePackage#getDShape()
 * @model
 * @generated
 */
public enum DShape implements Enumerator {
	/**
	 * The '<em><b>Rectangle</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RECTANGLE_VALUE
	 * @generated
	 * @ordered
	 */
	RECTANGLE(0, "rectangle", "rectangle"),

	/**
	 * The '<em><b>Rounded Rectangle</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ROUNDED_RECTANGLE_VALUE
	 * @generated
	 * @ordered
	 */
	ROUNDED_RECTANGLE(1, "roundedRectangle", "roundedRectangle"),

	/**
	 * The '<em><b>Ellipse</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ELLIPSE_VALUE
	 * @generated
	 * @ordered
	 */
	ELLIPSE(2, "ellipse", "ellipse"),

	/**
	 * The '<em><b>Dot</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DOT_VALUE
	 * @generated
	 * @ordered
	 */
	DOT(6, "dot", "dot"),

	/**
	 * The '<em><b>Custom</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CUSTOM_VALUE
	 * @generated
	 * @ordered
	 */
	CUSTOM(3, "custom", "custom"),

	/**
	 * The '<em><b>Arrow</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ARROW_VALUE
	 * @generated
	 * @ordered
	 */
	ARROW(4, "arrow", "arrow"),

	/**
	 * The '<em><b>Triangle</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TRIANGLE_VALUE
	 * @generated
	 * @ordered
	 */
	TRIANGLE(5, "triangle", "triangle");

	/**
	 * The '<em><b>Rectangle</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Rectangle</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #RECTANGLE
	 * @model name="rectangle"
	 * @generated
	 * @ordered
	 */
	public static final int RECTANGLE_VALUE = 0;

	/**
	 * The '<em><b>Rounded Rectangle</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Rounded Rectangle</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ROUNDED_RECTANGLE
	 * @model name="roundedRectangle"
	 * @generated
	 * @ordered
	 */
	public static final int ROUNDED_RECTANGLE_VALUE = 1;

	/**
	 * The '<em><b>Ellipse</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Ellipse</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ELLIPSE
	 * @model name="ellipse"
	 * @generated
	 * @ordered
	 */
	public static final int ELLIPSE_VALUE = 2;

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
	public static final int DOT_VALUE = 6;

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
	public static final int CUSTOM_VALUE = 3;

	/**
	 * The '<em><b>Arrow</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Arrow</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ARROW
	 * @model name="arrow"
	 * @generated
	 * @ordered
	 */
	public static final int ARROW_VALUE = 4;

	/**
	 * The '<em><b>Triangle</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Triangle</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #TRIANGLE
	 * @model name="triangle"
	 * @generated
	 * @ordered
	 */
	public static final int TRIANGLE_VALUE = 5;

	/**
	 * An array of all the '<em><b>DShape</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final DShape[] VALUES_ARRAY =
		new DShape[] {
			RECTANGLE,
			ROUNDED_RECTANGLE,
			ELLIPSE,
			DOT,
			CUSTOM,
			ARROW,
			TRIANGLE,
		};

	/**
	 * A public read-only list of all the '<em><b>DShape</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<DShape> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>DShape</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DShape get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			DShape result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>DShape</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DShape getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			DShape result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>DShape</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DShape get(int value) {
		switch (value) {
			case RECTANGLE_VALUE: return RECTANGLE;
			case ROUNDED_RECTANGLE_VALUE: return ROUNDED_RECTANGLE;
			case ELLIPSE_VALUE: return ELLIPSE;
			case DOT_VALUE: return DOT;
			case CUSTOM_VALUE: return CUSTOM;
			case ARROW_VALUE: return ARROW;
			case TRIANGLE_VALUE: return TRIANGLE;
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
	private DShape(int value, String name, String literal) {
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
	
} //DShape
