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
package org.isoe.diagraph.parser.api;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.isoe.diagraph.lang.DiagraphKeywords;
import org.isoe.diagraph.parser.api.IDiagraphAssociation.AssociationCandidateType;
import org.isoe.diastyle.lang.StyleConstants;
import org.isoe.diagraph.generic.GenericConstants;
import org.isoe.diagraph.views.ViewConstants;


/**
 *
 * @author fpfister
 *
 */
public interface IDiagraphAssociation extends IDiagraphObject {
	// org.isoe.diagraph.parser.api.IDiagraphAssociation



	enum AssociationType { // FP150512
		SIMPLE,
		CLASSOC_,
		TYPED_COMPARTMENT,
		SHARED_COMPARTMENT_,
		AT_CONTAINMENT,
		AT_PORT,
		EXTERNAL,
		VOID_
	}

	static final String[] TYPE_NAMES = {
		    "simple",
		    DiagraphKeywords.LNK,
			DiagraphKeywords.KREFERENCE_,
			DiagraphKeywords.CREFERENCE_,
			DiagraphKeywords.CONTAINMENT,
			DiagraphKeywords.AFFIXED_,
			DiagraphKeywords.OPEN_DIAGRAM,
			"void" };

	enum AssociationCandidateType {
		CAND_SIMPLE_,
		CAND_CONTAINMENT,
		CAND_CONT_ABSTR__,
		CAND_CONT_ABSTR_TARGET__,
		CAND_CONT_CONCRETE,
		CAND_CONT_ABSTR_2,
		CAND_CONT_3,
		CAND_VOID_
	}

	static final String[] CASE_NAMES = { "case_simple", "case_cont",
			"case_cont_abstr", "case_cont_abstr_target", "case_cont_concrete",
			"case_cont_abstr_2", "case_cont_abstr_3", "case_void" };

	static final AssociationType[] ALL_CONTAINMENT_TYPES = {
			AssociationType.SIMPLE,
			AssociationType.CLASSOC_,
			AssociationType.TYPED_COMPARTMENT,
			AssociationType.SHARED_COMPARTMENT_,
			AssociationType.AT_CONTAINMENT,
			AssociationType.AT_PORT,
			AssociationType.EXTERNAL,
			AssociationType.VOID_ };

	EClass getSource();

	EClass getTarget();

	boolean isTargetAbstract();

	boolean isSourceAbstract();

	boolean isSourcePov();

	List<EClass> getTargetSubHierarchy();

	EClass getType();

	void setSourceNode(IDiagraphNode diagraphNode);

	void setTargetNode(IDiagraphNode diagraphNode);

	IDiagraphNode getSourceNode();

	IDiagraphNode getTargetNode();

	String getInfo();

	EClass getInstanceSource();

	void setPropagated(boolean propagated);

	void setAbstract(boolean abstragt);

	void setContainment(EReference eReference);

	EReference getTargetReference();

	void resolveTargetReference();

	void setTargetReference(EReference reference);

	AssociationType getAssociationType();

	String toLog();

	// void setId_(String id);
	void addCase(AssociationCandidateType associationCandidateType);

	List<AssociationCandidateType> getCases();

	void setSourcePov();

	String getSourceName();

	boolean contains(AssociationCandidateType caze);

    String toCsv();
    String toCsvHeader();


}
