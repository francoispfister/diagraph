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
package org.isoe.diagraph.parser;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.isoe.diagraph.parser.api.IDiagraphElement;
import org.isoe.diagraph.parser.api.IDiagraphMapping;
import org.isoe.diagraph.parser.api.IDiagraphParser;
import org.isoe.fwk.core.DParams;

/**
 *
 * @author fpfister
 *
 */
public   class DiagraphElement extends DiagraphObject implements IDiagraphElement {

	private static final boolean LOG = DParams.DiagraphElement_LOG;
	private EClass eClass;
	private EReference declaredContainment;
	private List<String> labels = new ArrayList<String>();


	protected List<EReference> allContainmentReferences_;

	public void setAllContainmentReferences(List<EReference> all) {

		this.allContainmentReferences_ = new ArrayList<EReference>();
		setAllRefs(this.allContainmentReferences_, all, "allContainmentReferences");
	}

	protected void setAllRefs(List<EReference> refz, List<EReference> all, String label) {
		for (EReference ref : all) {
			if (!refz.contains(ref))
				refz.add(ref);
		}
		if (LOG) {
			clog(label +" for "+getName());
			for (EReference reference : refz)
				clog(reference.getName());
		}
	}


	//private List<IDiagraphMapping> mappings2 = new ArrayList<IDiagraphMapping>();
	//private List<IDiagraphMapping> mappings3 = new ArrayList<IDiagraphMapping>();


	public DiagraphElement(IDiagraphParser parser, String view, EClass eClass, boolean derived) {
		super(parser, view, derived, "");
		this.eClass = eClass;
	}



	@Override
	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	protected void clog(String mesg) {
		System.out.println(mesg);
	}

    @Override
	public EReference getDeclaredContainment() {
		return declaredContainment;
	}


	@Override
	public boolean setDeclaredContainment(EClass source, String containmentExpression) {
		int caze = 0;
		String className = (containmentExpression == null ? null
				: containmentExpression.substring(0,
						containmentExpression.indexOf(".")));
		EReference dc = null;
		if (className != null) {
			caze = 2;
			boolean inherited = false;
			if (!source.getName().equals(className)) {
				EClass declaredContainmentClass = (EClass) source.getEPackage()
						.getEClassifier(className);
				if (declaredContainmentClass==null)
					throw new RuntimeException(className+" supposed to contain "+getName() +" does not exist");

				if (declaredContainmentClass.isSuperTypeOf(source)) {
					inherited = true;
					caze = 3;
				}
			}

			List<EReference> srcrefs1 = inherited ? source.getEAllReferences()
					: getParser().getAllDiagraphContainers(source);
			for (EReference srcref1 : srcrefs1) {
				if (srcref1.isContainment()
						&& ((EClass) srcref1.eContainer()).getName().equals(
								className)) {
					//if (assoc!=null) assoc.setDeclaredContainment(srcref1);
					dc = srcref1;
					caze = 4;
					break;
				}
			}
			if (dc == null) {
				List<EReference> srcrefs2 = getParser().getAllDiagraphContainers(source);
				for (EReference srcref2 : srcrefs2) {
					if (srcref2.isContainment()
							&& ((EClass) srcref2.eContainer()).getName()
									.equals(className)) {
						dc = srcref2;
						caze = 5;
						break;
					}
				}
			}
			if (dc != null) {
				if (!(((EClass) (dc.eContainer())).getName()
						+ "." + dc.getName())
						.equals(containmentExpression))
					dc = null;
			}

			if (dc == null) {
				List<EClass> supes = getParser().getSuperGenericsOrNode(source, false);
				for (EClass supe : supes) {
					List<EReference> refs = getParser().getAllDiagraphContainers(supe);
					for (EReference srcref : refs) {
						if (srcref.isContainment()
								&& ((EClass) srcref.eContainer()).getName()
										.equals(className)) {
							dc = srcref;
							caze = 6;
							break;
						}
					}

				}
			}
		}

		boolean result = (className == null)
				|| (dc != null && caze > 0);
		if (result)
			 declaredContainment = dc;
		return result;
	}

	@Override
	public EClass getEClass() {
		return eClass;
	}

	void setEClass(EClass eClass) {
		this.eClass = eClass;
	}

	@Override
	public String getName() {
		return eClass.getName();
	}



	@Override
	public int getDepth() {
		return 0;
	}



	@Override
	public List<EReference> getAllContainmentReferences() {
		return null;
	}



	@Override
	public void hook_(String caze, String mesg) {
		// TODO Auto-generated method stub

	}





}
