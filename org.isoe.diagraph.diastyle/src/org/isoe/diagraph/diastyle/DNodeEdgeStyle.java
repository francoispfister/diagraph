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

import org.eclipse.emf.ecore.EModelElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>DNode Edge Style</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.isoe.diagraph.diastyle.DNodeEdgeStyle#getLine <em>Line</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.DNodeEdgeStyle#getLineWidth <em>Line Width</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.DNodeEdgeStyle#getFontName <em>Font Name</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.DNodeEdgeStyle#getFontStyle <em>Font Style</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.DNodeEdgeStyle#getFontSize <em>Font Size</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.DNodeEdgeStyle#getFontColor <em>Font Color</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.DNodeEdgeStyle#getTextAlignment <em>Text Alignment</em>}</li>
 *   <li>{@link org.isoe.diagraph.diastyle.DNodeEdgeStyle#getIcon <em>Icon</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.isoe.diagraph.diastyle.DiastylePackage#getDNodeEdgeStyle()
 * @model abstract="true"
 * @generated
 */
public interface DNodeEdgeStyle extends EModelElement, DBaseStyle {
	/**
	 * Returns the value of the '<em><b>Line</b></em>' attribute.
	 * The literals are from the enumeration {@link org.isoe.diagraph.diastyle.DLine}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Line</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Line</em>' attribute.
	 * @see org.isoe.diagraph.diastyle.DLine
	 * @see #setLine(DLine)
	 * @see org.isoe.diagraph.diastyle.DiastylePackage#getDNodeEdgeStyle_Line()
	 * @model
	 * @generated
	 */
	DLine getLine();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diastyle.DNodeEdgeStyle#getLine <em>Line</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Line</em>' attribute.
	 * @see org.isoe.diagraph.diastyle.DLine
	 * @see #getLine()
	 * @generated
	 */
	void setLine(DLine value);

	/**
	 * Returns the value of the '<em><b>Line Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Line Width</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Line Width</em>' attribute.
	 * @see #setLineWidth(int)
	 * @see org.isoe.diagraph.diastyle.DiastylePackage#getDNodeEdgeStyle_LineWidth()
	 * @model
	 * @generated
	 */
	int getLineWidth();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diastyle.DNodeEdgeStyle#getLineWidth <em>Line Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Line Width</em>' attribute.
	 * @see #getLineWidth()
	 * @generated
	 */
	void setLineWidth(int value);

	/**
	 * Returns the value of the '<em><b>Font Name</b></em>' attribute.
	 * The literals are from the enumeration {@link org.isoe.diagraph.diastyle.DFontName}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Font Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Font Name</em>' attribute.
	 * @see org.isoe.diagraph.diastyle.DFontName
	 * @see #setFontName(DFontName)
	 * @see org.isoe.diagraph.diastyle.DiastylePackage#getDNodeEdgeStyle_FontName()
	 * @model
	 * @generated
	 */
	DFontName getFontName();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diastyle.DNodeEdgeStyle#getFontName <em>Font Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Font Name</em>' attribute.
	 * @see org.isoe.diagraph.diastyle.DFontName
	 * @see #getFontName()
	 * @generated
	 */
	void setFontName(DFontName value);

	/**
	 * Returns the value of the '<em><b>Font Style</b></em>' attribute.
	 * The literals are from the enumeration {@link org.isoe.diagraph.diastyle.DFontStyle}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Font Style</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Font Style</em>' attribute.
	 * @see org.isoe.diagraph.diastyle.DFontStyle
	 * @see #setFontStyle(DFontStyle)
	 * @see org.isoe.diagraph.diastyle.DiastylePackage#getDNodeEdgeStyle_FontStyle()
	 * @model
	 * @generated
	 */
	DFontStyle getFontStyle();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diastyle.DNodeEdgeStyle#getFontStyle <em>Font Style</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Font Style</em>' attribute.
	 * @see org.isoe.diagraph.diastyle.DFontStyle
	 * @see #getFontStyle()
	 * @generated
	 */
	void setFontStyle(DFontStyle value);

	/**
	 * Returns the value of the '<em><b>Font Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Font Size</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Font Size</em>' attribute.
	 * @see #setFontSize(int)
	 * @see org.isoe.diagraph.diastyle.DiastylePackage#getDNodeEdgeStyle_FontSize()
	 * @model
	 * @generated
	 */
	int getFontSize();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diastyle.DNodeEdgeStyle#getFontSize <em>Font Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Font Size</em>' attribute.
	 * @see #getFontSize()
	 * @generated
	 */
	void setFontSize(int value);

	/**
	 * Returns the value of the '<em><b>Font Color</b></em>' attribute.
	 * The literals are from the enumeration {@link org.isoe.diagraph.diastyle.DColor}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Font Color</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Font Color</em>' attribute.
	 * @see org.isoe.diagraph.diastyle.DColor
	 * @see #setFontColor(DColor)
	 * @see org.isoe.diagraph.diastyle.DiastylePackage#getDNodeEdgeStyle_FontColor()
	 * @model
	 * @generated
	 */
	DColor getFontColor();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diastyle.DNodeEdgeStyle#getFontColor <em>Font Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Font Color</em>' attribute.
	 * @see org.isoe.diagraph.diastyle.DColor
	 * @see #getFontColor()
	 * @generated
	 */
	void setFontColor(DColor value);

	/**
	 * Returns the value of the '<em><b>Text Alignment</b></em>' attribute.
	 * The literals are from the enumeration {@link org.isoe.diagraph.diastyle.DAlignment}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Text Alignment</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Text Alignment</em>' attribute.
	 * @see org.isoe.diagraph.diastyle.DAlignment
	 * @see #setTextAlignment(DAlignment)
	 * @see org.isoe.diagraph.diastyle.DiastylePackage#getDNodeEdgeStyle_TextAlignment()
	 * @model
	 * @generated
	 */
	DAlignment getTextAlignment();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diastyle.DNodeEdgeStyle#getTextAlignment <em>Text Alignment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Text Alignment</em>' attribute.
	 * @see org.isoe.diagraph.diastyle.DAlignment
	 * @see #getTextAlignment()
	 * @generated
	 */
	void setTextAlignment(DAlignment value);

	/**
	 * Returns the value of the '<em><b>Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Icon</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Icon</em>' attribute.
	 * @see #setIcon(String)
	 * @see org.isoe.diagraph.diastyle.DiastylePackage#getDNodeEdgeStyle_Icon()
	 * @model
	 * @generated
	 */
	String getIcon();

	/**
	 * Sets the value of the '{@link org.isoe.diagraph.diastyle.DNodeEdgeStyle#getIcon <em>Icon</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Icon</em>' attribute.
	 * @see #getIcon()
	 * @generated
	 */
	void setIcon(String value);

} // DNodeEdgeStyle
