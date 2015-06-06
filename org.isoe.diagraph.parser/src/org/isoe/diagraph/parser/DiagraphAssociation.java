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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.isoe.diagraph.parser.api.IDiagraphAssociation;
import org.isoe.diagraph.parser.api.IDiagraphElement;
import org.isoe.diagraph.parser.api.IDiagraphNode;
import org.isoe.diagraph.parser.api.IDiagraphParser;

/**
 *
 * @author fpfister
 *
 */
public abstract class DiagraphAssociation extends DiagraphObject implements IDiagraphAssociation {

	static final String SPACES = "                                                                                                   ";
	static int WBASE = 120;
	static DecimalFormat F12 = new DecimalFormat("############");
	protected boolean sourcePov;
	protected IDiagraphNode sourceNode;
	protected IDiagraphNode targetNode;
	protected EClass source; // may be abstract
	protected EClass instanceSource; // if source is abstract
	protected EClass target;
	protected List<EClass> targetSubHierarchy;
	protected EReference targetReference;
	protected AssociationType associationType = AssociationType.VOID_;
	protected List<AssociationCandidateType> cases = new ArrayList<IDiagraphAssociation.AssociationCandidateType>();

    @Override
	public AssociationType getAssociationType() {
		return associationType;
	}


    public abstract String toCsv();
    public abstract String toCsvHeader();


	@Override
	public String getSourceName() {
		return source.getName();
	}

	@Override
	public EReference getTargetReference() {
		return targetReference;
	}


	@Override
	public void setTargetReference(EReference reference) {
		targetReference = reference;
	}

	protected DiagraphAssociation(IDiagraphParser parser,String view, EClass refsource,
			EClass refinstancesource, EClass target, EReference targetref,
			List<EClass> targetSubHierarchy, AssociationType associationType, boolean derived, String info) {
		super(parser, view, derived,info);
		this.associationType = associationType;
		this.targetReference = targetref;
		this.target = target;
		this.source = refsource;
		this.instanceSource = refinstancesource; // if source is abstract
		this.targetSubHierarchy = targetSubHierarchy;

		//this.info = info;
		//this.view = view;
		//this.parser = parser;
	}


	public DiagraphAssociation(IDiagraphParser parser, String view,
			IDiagraphNode contNode, IDiagraphElement src, //FP150520
			IDiagraphElement trg, EClass refinstancesource,EReference targetref,boolean derived, String info) {
		super(parser, view, derived,info);
		this.associationType = IDiagraphAssociation.AssociationType.CLASSOC_;
		this.targetReference = targetref;
		this.target = trg.getEClass();
		this.source = src.getEClass();
		this.instanceSource = refinstancesource; // if source is abstract
		this.targetSubHierarchy = new ArrayList<EClass>();

		/*
		List<EClass> emptySubHierarchy_ = new ArrayList<EClass>();
		IDiagraphAssociation.AssociationType classoc = IDiagraphAssociation.AssociationType.CLASSOC;
		*/
		// TODO Auto-generated constructor stub
	}


	@Override
	public boolean isTargetAbstract() {
		return (((EClass) targetReference.getEType()).isAbstract());
	}

	@Override
	public boolean isSourceAbstract() {
		return( (EClass)targetReference.eContainer()).isAbstract();
	}


	@Override
	public boolean isSourcePov() {
		return sourcePov;
	}


	@Override
	public void setSourcePov() {
		sourcePov = true;
	}


	@Override
	public IDiagraphNode getSourceNode() {
		return sourceNode;
	}

	@Override
	public void setSourceNode(IDiagraphNode sourceNode) {
		this.sourceNode = sourceNode;
	}

	@Override
	public IDiagraphNode getTargetNode() {
		return targetNode;
	}

	@Override
	public void setTargetNode(IDiagraphNode targetNode) {
		this.targetNode = targetNode;
	}



	@Override
	public EClass getSource() {
		return source;
	}

	@Override
	public EClass getTarget() {
		return target;
	}

	@Override
	public List<EClass> getTargetSubHierarchy() {
		return targetSubHierarchy;
	}

	@Override
	public EClass getInstanceSource() {
		return instanceSource;
	}
/*

	@Override
	public void setId_(String id) {
		this.id = id;
	}
*/
	@Override
	public void addCase(
			AssociationCandidateType caze) {
		cases.add(caze);
	}

	@Override
	public List<AssociationCandidateType> getCases() {
		return cases;
	}


	@Override
	public boolean contains(AssociationCandidateType caze) {
        for (AssociationCandidateType caz : cases)
			if (caz==caze)
				 return true;
		return false;
	}

	//http://www.java2s.com/Code/Java/Data-Type/Rightjustifystringensuringthatthestringendsatthelastcharacter.htm
    static String justifyRight(String str, final int width,
			char padWithChar) {
		assert width > 0;
		// Trim the leading and trailing whitespace ...
		str = str != null ? str.trim() : "";
		final int length = str.length();
		int addChars = width - length;
		if (addChars < 0) {
			// truncate the first characters, keep the last
			return str.subSequence(length - width, length).toString();
		}
		// Prepend the whitespace ...
		final StringBuilder sb = new StringBuilder();
		while (addChars > 0) {
			sb.append(padWithChar);
			--addChars;
		}
		// Write the content ...
		sb.append(str);
		return sb.toString();
	}




}
