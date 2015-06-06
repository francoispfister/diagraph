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
package org.isoe.diagraph.megamodel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Relation</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.isoe.diagraph.megamodel.MegamodelPackage#getRelation()
 * @model
 * @generated
 */
public enum Relation implements Enumerator {
   /**
	 * The '<em><b>Required</b></em>' literal object.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see #REQUIRED_VALUE
	 * @generated
	 * @ordered
	 */
   REQUIRED(0, "required", "required"),

   /**
	 * The '<em><b>Related</b></em>' literal object.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see #RELATED_VALUE
	 * @generated
	 * @ordered
	 */
   RELATED(1, "related", "related"),

   /**
	 * The '<em><b>Variant</b></em>' literal object.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see #VARIANT_VALUE
	 * @generated
	 * @ordered
	 */
   VARIANT(2, "variant", "variant"),

   /**
	 * The '<em><b>Left Parent</b></em>' literal object.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see #LEFT_PARENT_VALUE
	 * @generated
	 * @ordered
	 */
   LEFT_PARENT(3, "leftParent", "leftParent"),

   /**
	 * The '<em><b>Right Parent</b></em>' literal object.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @see #RIGHT_PARENT_VALUE
	 * @generated
	 * @ordered
	 */
   RIGHT_PARENT(4, "rightParent", "rightParent");

   /**
	 * The '<em><b>Required</b></em>' literal value.
	 * <!-- begin-user-doc -->
    * <p>
    * If the meaning of '<em><b>Required</b></em>' literal object isn't clear,
    * there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
	 * @see #REQUIRED
	 * @model name="required"
	 * @generated
	 * @ordered
	 */
   public static final int REQUIRED_VALUE = 0;

   /**
	 * The '<em><b>Related</b></em>' literal value.
	 * <!-- begin-user-doc -->
    * <p>
    * If the meaning of '<em><b>Related</b></em>' literal object isn't clear,
    * there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
	 * @see #RELATED
	 * @model name="related"
	 * @generated
	 * @ordered
	 */
   public static final int RELATED_VALUE = 1;

   /**
	 * The '<em><b>Variant</b></em>' literal value.
	 * <!-- begin-user-doc -->
    * <p>
    * If the meaning of '<em><b>Variant</b></em>' literal object isn't clear,
    * there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
	 * @see #VARIANT
	 * @model name="variant"
	 * @generated
	 * @ordered
	 */
   public static final int VARIANT_VALUE = 2;

   /**
	 * The '<em><b>Left Parent</b></em>' literal value.
	 * <!-- begin-user-doc -->
    * <p>
    * If the meaning of '<em><b>Left Parent</b></em>' literal object isn't clear,
    * there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
	 * @see #LEFT_PARENT
	 * @model name="leftParent"
	 * @generated
	 * @ordered
	 */
   public static final int LEFT_PARENT_VALUE = 3;

   /**
	 * The '<em><b>Right Parent</b></em>' literal value.
	 * <!-- begin-user-doc -->
    * <p>
    * If the meaning of '<em><b>Right Parent</b></em>' literal object isn't clear,
    * there really should be more of a description here...
    * </p>
    * <!-- end-user-doc -->
	 * @see #RIGHT_PARENT
	 * @model name="rightParent"
	 * @generated
	 * @ordered
	 */
   public static final int RIGHT_PARENT_VALUE = 4;

   /**
	 * An array of all the '<em><b>Relation</b></em>' enumerators.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   private static final Relation[] VALUES_ARRAY =
      new Relation[] {
			REQUIRED,
			RELATED,
			VARIANT,
			LEFT_PARENT,
			RIGHT_PARENT,
		};

   /**
	 * A public read-only list of all the '<em><b>Relation</b></em>' enumerators.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public static final List<Relation> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

   /**
	 * Returns the '<em><b>Relation</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public static Relation get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			Relation result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

   /**
	 * Returns the '<em><b>Relation</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public static Relation getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			Relation result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

   /**
	 * Returns the '<em><b>Relation</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
	 * @generated
	 */
   public static Relation get(int value) {
		switch (value) {
			case REQUIRED_VALUE: return REQUIRED;
			case RELATED_VALUE: return RELATED;
			case VARIANT_VALUE: return VARIANT;
			case LEFT_PARENT_VALUE: return LEFT_PARENT;
			case RIGHT_PARENT_VALUE: return RIGHT_PARENT;
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
   private Relation(int value, String name, String literal) {
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
   
} //Relation
