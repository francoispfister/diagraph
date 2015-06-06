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
import org.isoe.diagraph.parser.api.IDiagraphMapping;
import org.isoe.diagraph.parser.api.IDiagraphObject;
import org.isoe.diagraph.parser.api.IDiagraphParser;
import org.isoe.fwk.core.DParams;

/**
 *
 * @author Pfister
 *
 */
public abstract class DiagraphObject implements IDiagraphObject {

	protected static final boolean LOG = DParams.DiagraphObject_LOG;
	private List<IDiagraphMapping> mappings1 = new ArrayList<IDiagraphMapping>();

	protected IDiagraphParser parser;
	protected String view;
	private boolean propagated;
	private boolean derived;
	private boolean abstragt;
	private boolean generic;
	private String info;

	private EReference containment;
	private EReference declaredContainment;


	public void setContainment(EReference containment) {// FP131009x
		this.containment = containment;
		if (declaredContainment != null
				&& declaredContainment != this.containment) {
			this.containment = declaredContainment;
			clog("DCESCR changed " + declaredContainment.getName()); // TODO
																		// validate
		}
	}

	public DiagraphObject(IDiagraphParser parser, String view, boolean derived,
			String info) {
		super();
		this.parser = parser;
		this.view = view;
		this.info = info;
		this.derived = derived;
	}

	public void setDeclaredContainment(EReference declaredContainment) {
		this.declaredContainment = declaredContainment;
	}


	@Override
	public boolean setDeclaredContainment(EClass source,
			String declaredContention) {
		throw new RuntimeException("TODO implement setDeclaredContainment");
		//return false;
	}


    @Override
	public EReference getContainment() {
		return containment;
	}

    @Override
	public EReference getDeclaredContainment() {
		return declaredContainment;
	}

	@Override
	public void setDerived(boolean derived) {
		this.derived = derived;
	}

	@Override
	public int getRecursiveDepth() {
		return mappings1.size();
	}

	@Override
	public void addMapping(IDiagraphMapping mapping) {
		this.mappings1.add(mapping); // CHILD Cccc.fs:Ffff
		if (LOG)
			clog(mapping.toString());
	}

	protected void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);

	}

	@Override
	public List<IDiagraphMapping> getMappings() {
		return mappings1;
	}

	@Override
	public String getInfo() {
		return info;
	}

	@Override
	public String getView() {
		return view;
	}

	public void setAbstract(boolean abstragt) {
		this.abstragt = abstragt;
	}

	public void setGeneric(boolean generic) {
		this.generic = generic;
	}

	public void setPropagated(boolean propagated) {
		this.propagated = propagated;
	}

	@Override
	public boolean isDerived() {
		return derived;
	}

	@Override
	public IDiagraphParser getParser() {
		return parser;
	}

	@Override
	public boolean isAbstract() {
		return abstragt;
	}

	@Override
	public boolean isGeneric() {
		return generic;
	}

}
