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
package org.isoe.diagraph.internal.m2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EPackage;
import org.isoe.diagraph.internal.api.IDiaConcreteSyntax;
import org.isoe.diagraph.internal.api.IDiaPointOfView;
import org.isoe.diagraph.views.ViewConstants;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.log.LogConfig;

/**
 *
 * @author pfister
 *
 */
public class DiaConcreteSyntax implements IDiaConcreteSyntax {

	private static final boolean LOG = DParams.DiaConcreteSyntax_LOG;
//	private List<EClass> classes = new ArrayList<EClass>();
	private String view;

	private IDiaPointOfView root; // FP140615

	@Override
	public IDiaPointOfView getPointOfView() {
		return root;
	}

	@Override
	public boolean parse(String layer, EPackage domainModel) { // FP140615
		if (layer == null)
			throw new RuntimeException("view should not be null for "
					+ domainModel.getName() + " !!!"); // FP130107a
		if (LOG)
			clog(" layer="
					+ layer
					+ " --------- Points Of View - parse domainModel  -------------");
		view = layer;
		List<EClass> classes = new ArrayList<EClass>();
		for (EClassifier eClassif : domainModel.getEClassifiers()){
			if (eClassif instanceof EClass)
			  for (EAnnotation anot : ((EClass) eClassif).getEAnnotations())
				if (isView(anot, layer))
					classes.add((EClass) eClassif);
		}
		root = DiaPointOfView.getPointOfView(getPointOfViewClass(classes, view), view);
		return true;
	}

	private EClass getPointOfViewClass(List<EClass> eclasses, String view) {
		this.view = view;
		for (EClass el : eclasses)
			if (isPov((EClass) el, view))
				return (EClass) el;
		return null;
	}

	private boolean isPov(EClass eclaz, String view) { // FP131209
		EList<EAnnotation> annots = eclaz.getEAnnotations();
		for (EAnnotation eAnnotation : annots) {
			if (isView(eAnnotation, view))
				for (Map.Entry<String, String> entry : eAnnotation.getDetails())
					if (entry.getKey().equals("pov"))
						return true;
		}
		return false;
	}

	private boolean isView(EAnnotation anot, String view) { // FP131208
		if (!anot.getSource().equals("diagraph"))
			return false;
		if (view == null || view.equals(ViewConstants.DIAGRAPH_DEFAULT)
				|| view.isEmpty())
			return isDefaultView(anot);
		for (Map.Entry<String, String> entry : anot.getDetails())
			if (entry.getKey().equals("view=" + view))
				return true;
		return false;
	}

	private boolean isDefaultView(EAnnotation anot) {
		for (Map.Entry<String, String> entry : anot.getDetails())
			if (entry.getKey().startsWith("view="))
				return false;
		return true;
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

}
