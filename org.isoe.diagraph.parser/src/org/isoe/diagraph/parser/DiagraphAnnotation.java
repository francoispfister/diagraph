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

import java.util.List;

import org.eclipse.emf.ecore.EAnnotation;
import org.isoe.diagraph.parser.api.IDiagraphAnnotation;
import org.isoe.diagraph.parser.api.IDiagraphNotation;
import org.isoe.fwk.core.DParams;


/**
 *
 * @author fpfister
 *
 */
public class DiagraphAnnotation implements IDiagraphAnnotation {

	private static final boolean LOG = DParams.DiagraphAnnotation_LOG;
	private boolean promoted = true;
	private String spaces = "                                                          ";
	//private List<IDiagraphAnnotation> allannots_;
	private String key;
	private String value;
	private String argument1;
	private String argument2;
	//private String[] leftHand_ = new String[2];
	//private String[] rightHand_ = new String[2];
	private String view;
	private EAnnotation owner;
	private IDiagraphNotation notation;
	private List<IDiagraphAnnotation> other; //FP150324
	private boolean problem;


	@Override
	public String toLog() {
		return key+((value!=null && !value.equals("_"))?("="+value):"");
	}

	private DiagraphAnnotation(IDiagraphNotation notation, String key,
			String value, String view) {
		;
		this.view = view;
		if (value != null && value.length() > 0)
			this.value = value;

		this.key = key;
		this.notation = notation;
	}

	public DiagraphAnnotation(IDiagraphNotation notation, EAnnotation owner,
			String key, String value, String argument2,String argument1, String view) {
		this(notation, key, value, view);
		this.owner = owner;
		this.argument1 = argument1;
		this.argument2 = argument2;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public String getArgument1() {//FP150513
		return argument1;
	}


	@Override
	public boolean keyFirstSegmentEquals(String keyword) {
		String[] ks = new String[2];
		if (key.contains(":"))
			ks = key.split(":");
		else
			ks[0] = key;
		return keyword.equals(ks[0]);
	}


	private void clog(String msg) {
		if (LOG)
			System.out.println(msg);
	}

	@Override
	public void setValue(String value) {
		this.value= value;
	}

	@Override
	public String[] parse() {
		String[] args = new String[2];
		if (argument1 != null && argument1.contains("/"))
			args = argument1.split("/");
		else {
			args[0] = argument1;
			args[1] = "figureEmbedded"; // FP120619
		}
		return args;
	}

	@Override
	public void setOther(List<IDiagraphAnnotation> other) {
		this.other = other;

	}

	@Override
	public List<IDiagraphAnnotation> getOther() {
		return other;
	}

	@Override
	public String getArgument2() {
		return argument2;
	}

	@Override
	public void setProblem() {
	  this.problem = true;
	}

	public boolean isProblem() {
		return problem;
	}





}
