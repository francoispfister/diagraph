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
package org.isoe.diagraph.annotationhelper;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.notation.FillStyle;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.Style;
import org.eclipse.gmf.runtime.notation.View;
import org.isoe.diagraph.generic.GenericConstants;
import org.isoe.fwk.core.DParams;
import org.isoe.diagraph.controler.IDiagraphControler;  //FP140707refactored

/**
 *
 * @author fpfister
 *
 */

public class AnnotationHelper implements IAnnotationHelper {

	protected static final boolean LOG = DParams.AnnotationHelper_LOG;
	private IDiagraphControler controler;

	public AnnotationHelper(IDiagraphControler controler) {
		this.controler = controler;
	}

	private static int[] COLORS = { 16776960, 11991228, 15715474, 7781080,
			14517900, 12220865, 1677696, 1199122, 1571547, 778108, 1451790,
			1222086, 167769, 119912, 157154, 77810, 145179, 122208 }; // TODO

	private void changeColor(final FillStyle styl, final int color) {
		TransactionalEditingDomain ted = TransactionUtil.getEditingDomain(styl);
		ted.getCommandStack().execute(new RecordingCommand(ted) {
			@Override
			protected void doExecute() {
				styl.setFillColor(color);
				if (LOG)
					clog("DAH2 changeColor" + color);
			}
		});
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	private void changeColor(Node node, int color) {
		if (LOG) {
			String nam = "";
			if (node.getElement() instanceof EAnnotation) {
				EAnnotation ne = (EAnnotation) node.getElement();
				nam = ((EClass) ne.eContainer()).getName();
			} else
				nam = node.getElement().toString();
			clog("*** DAH1 changeColor " + nam + "=" + color);
		}
		EList<Style> styl = node.getStyles();
		for (Style style : styl) {
			if (style instanceof FillStyle)
				changeColor((FillStyle) style, color);
		}
	}


	private  void updateColor_old_nu(String language, String view) {
		String key = "view=" + view;
		int color = controler.getViewId(view);
		for (View vw : (List<View>) controler.getEcoreDiagram().getChildren()) {
			if (vw instanceof Node) {
				Node node = (Node) vw;
				if (node.getElement() instanceof EAnnotation) {
					EAnnotation eAnnotation = (EAnnotation) node.getElement();
					EModelElement emel = eAnnotation.getEModelElement();
					if (emel instanceof EClass
							&& (eAnnotation.getSource().equals(
									GenericConstants.ANNOT_DIAGRAPH_LITTERAL) && eAnnotation
									.getDetails().containsKey(key)))
						changeColor(node, COLORS[color + 1]);
				}
			}
		}

	}

	public void updateColor(String language, String view) {
		int color = controler.getViewId(view);
		for (Node node : getAnnotationRuntimeNodes(language,view))
			changeColor(node, COLORS[color + 1]);
	}

	public List<Node> getAnnotationRuntimeNodes(String language, String view) {
		List<Node> result = new ArrayList<Node>();
		String key = "view=" + view;
		for (View vw : (List<View>) controler.getEcoreDiagram(language).getChildren()) {//FP140616voir2
			if (vw instanceof Node) {
				Node node = (Node) vw;
				if (node.getElement() instanceof EAnnotation) {
					EAnnotation eAnnotation = (EAnnotation) node.getElement();
					if ((eAnnotation.getEModelElement()) instanceof EClass
							&& (eAnnotation.getSource().equals(
									GenericConstants.ANNOT_DIAGRAPH_LITTERAL) && eAnnotation
									.getDetails().containsKey(key))) // DParams.VIEW_PREFIX+
						result.add(node);
				}
			}
		}
		return result;

	}

}
