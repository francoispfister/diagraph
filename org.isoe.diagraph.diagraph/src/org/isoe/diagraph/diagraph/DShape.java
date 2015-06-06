/**
 */
package org.isoe.diagraph.diagraph;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>DShape</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.isoe.diagraph.diagraph.DiagraphPackage#getDShape()
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
	RECTANGLE(4, "rectangle", "rectangle"),

	/**
	 * The '<em><b>Vee</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #VEE_VALUE
	 * @generated
	 * @ordered
	 */
	VEE(0, "vee", "vee"),

	/**
	 * The '<em><b>Triangle</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TRIANGLE_VALUE
	 * @generated
	 * @ordered
	 */
	TRIANGLE(1, "triangle", "triangle"),

	/**
	 * The '<em><b>Dot</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DOT_VALUE
	 * @generated
	 * @ordered
	 */
	DOT(2, "dot", "dot"),

	/**
	 * The '<em><b>Circle</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CIRCLE_VALUE
	 * @generated
	 * @ordered
	 */
	CIRCLE(3, "circle", "circle"),

	/**
	 * The '<em><b>Rounded Rect</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ROUNDED_RECT_VALUE
	 * @generated
	 * @ordered
	 */
	ROUNDED_RECT(5, "roundedRect", "roundedRect");

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
	public static final int RECTANGLE_VALUE = 4;

	/**
	 * The '<em><b>Vee</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Vee</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #VEE
	 * @model name="vee"
	 * @generated
	 * @ordered
	 */
	public static final int VEE_VALUE = 0;

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
	public static final int TRIANGLE_VALUE = 1;

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
	public static final int DOT_VALUE = 2;

	/**
	 * The '<em><b>Circle</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Circle</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CIRCLE
	 * @model name="circle"
	 * @generated
	 * @ordered
	 */
	public static final int CIRCLE_VALUE = 3;

	/**
	 * The '<em><b>Rounded Rect</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Rounded Rect</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ROUNDED_RECT
	 * @model name="roundedRect"
	 * @generated
	 * @ordered
	 */
	public static final int ROUNDED_RECT_VALUE = 5;

	/**
	 * An array of all the '<em><b>DShape</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final DShape[] VALUES_ARRAY =
		new DShape[] {
			RECTANGLE,
			VEE,
			TRIANGLE,
			DOT,
			CIRCLE,
			ROUNDED_RECT,
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
			case VEE_VALUE: return VEE;
			case TRIANGLE_VALUE: return TRIANGLE;
			case DOT_VALUE: return DOT;
			case CIRCLE_VALUE: return CIRCLE;
			case ROUNDED_RECT_VALUE: return ROUNDED_RECT;
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
