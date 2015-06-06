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
package org.isoe.diagraph.notationparser;

import java.util.Map;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.ETypedElement;
import org.isoe.diagraph.lang.DiagraphKeywords;

/**
 *
 * borrowed from Jacob Geisel : de.developnow.ecore2latex.transformation
 *
 */
public class LatexHelper {

	public String getLatexDocAnnotation_(EModelElement eModelElement, String key) {
		EAnnotation annotation = eModelElement.getEAnnotation(DiagraphKeywords.DIAGRAPH_LITTERAL);
		return (annotation != null) ? annotation.getDetails().get(key) : null;
	}

	public String getDiagraphProperty(EModelElement eModelElement, String key) {
		EAnnotation annotation = eModelElement.getEAnnotation(DiagraphKeywords.DIAGRAPH_LITTERAL);
		if (annotation != null) {
			for (Map.Entry<String, String> entry : annotation.getDetails()) {
				String k = entry.getKey();
				String[] ks = k.split("=");
				if (ks[0].startsWith(key))//FP150512transp
					return ks[1];
			}
		}
		return null;
	}

	public String getMultiplicity(ETypedElement eTypedElement) {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		// if only one multiplicity
		if (eTypedElement.getLowerBound() == eTypedElement.getUpperBound()) {
			sb.append(eTypedElement.getLowerBound());
		}
		// if more than one multiplicity
		else {
			sb.append(eTypedElement.getLowerBound());
			sb.append("..");
			// if upper bound is -1 replace it
			if (eTypedElement.getUpperBound() == -1) {
				sb.append("*");
			} else {
				sb.append(eTypedElement.getUpperBound());
			}
		}
		sb.append("]");
		return sb.toString();
	}

	public String getClassAssociations(EClass eClass) {
		// if associations exist
		if (eClass.getEReferences().size() > 0) {
			StringBuilder sb = new StringBuilder();

			if (getDiagraphProperty(eClass, DiagraphKeywords.KREFERENCE_) != null) {//FP150512transp
				sb.append("\\newline\n")
						.append(DiagraphKeywords.KREFERENCE_EQU_ + getDiagraphProperty(eClass, DiagraphKeywords.KREFERENCE_))
						.append("\n");
			}
			if (getDiagraphProperty(eClass, DiagraphKeywords.CREFERENCE_) != null) {
				sb.append("\\newline\n")
						.append(DiagraphKeywords.CREFERENCE_EQU + getDiagraphProperty(eClass, DiagraphKeywords.CREFERENCE_))
						.append("\n");
			}
			if (getDiagraphProperty(eClass, DiagraphKeywords.REFERENCE) != null) {
				sb.append("\\newline\n")
						.append(DiagraphKeywords.REFERENCE_EQU + getDiagraphProperty(eClass, DiagraphKeywords.REFERENCE))
						.append("\n");
			}
			if (getDiagraphProperty(eClass, DiagraphKeywords.AFFIXED_) != null) {
				sb.append("\\newline\n")
						.append(DiagraphKeywords.AFFIXED_EQU_ + getDiagraphProperty(eClass, DiagraphKeywords.AFFIXED_))
						.append("\n");
			}
			if (getDiagraphProperty(eClass, DiagraphKeywords.LINK) != null) {
				sb.append("\\newline\n")
						.append(DiagraphKeywords.LINK_EQU + getDiagraphProperty(eClass, DiagraphKeywords.LINK))
						.append("\n");
			}

			sb.append("\\subsubsection*{Associations}\n");
			sb.append("\\begin{itemize}\n");
			for (EReference eReference : eClass.getEReferences()) {
				sb.append("\\item ");
				// Name
				sb.append(eReference.getName()).append(": ");
				// Type
				// Works only for internal
				sb.append("\\nameref{")
						.append(eReference.getEReferenceType().getName())
						.append("} ");
				// Mult
				sb.append(getMultiplicity(eReference)).append(" ");
				// Containement
				if (eReference.isContainment()) {
					sb.append("(containment) ");
				}
				// Description
				if (getDiagraphProperty(eReference, DiagraphKeywords.NODE) != null) {
					sb.append("\\newline\n").append(
							getDiagraphProperty(eReference, DiagraphKeywords.NODE));
				}
				sb.append("\n");
			}
			sb.append("\\end{itemize}");
			return sb.toString();
		}
		return "\\subsubsection*{Associations} ~\\\\ No additional associations";

	}

}
