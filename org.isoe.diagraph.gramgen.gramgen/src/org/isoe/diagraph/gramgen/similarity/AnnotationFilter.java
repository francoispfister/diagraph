 /**
 * Copyright (c) 2014 Laboratoire de Genie Informatique et Ingenierie de Production - Ecole des Mines d'Ales
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Blazo Nastov (ISOE-LGI2P) - initial API and implementation
 */
package org.isoe.diagraph.gramgen.similarity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.isoe.diagraph.gramgen.similarity.response.CanvasResponse;
import org.isoe.diagraph.gramgen.similarity.response.CompartmentFromClassAssocResponse;
import org.isoe.diagraph.gramgen.similarity.response.CompartmentResponse;
import org.isoe.diagraph.gramgen.similarity.response.LabeledAssociationResponse;
import org.isoe.diagraph.gramgen.similarity.response.MatchingResponse;
import org.isoe.diagraph.gramgen.similarity.response.ReferenceResponse;
import org.isoe.diagraph.lang.DiagraphKeywords;
import org.isoe.diastyle.lang.StyleConstants;
import org.isoe.diagraph.views.ViewConstants;




/**
 *
 * @author bnastov
 *
 */
public class AnnotationFilter {
	public static final boolean NEST_W_KREF = true; //FP130517 //TODO take in preferences

	private Map<EClass, List<String>> associatedAnnotations;
	private List<EClass> visualElements;
	private EClass canvas;


	public AnnotationFilter(List<MatchingResponse> ensResponses, List<EClass> visualElements){
		associatedAnnotations = new HashMap<EClass, List<String>>();
		this.visualElements = visualElements;
		for (MatchingResponse response : ensResponses) {
			if(response instanceof CanvasResponse)
				addCanvasNodes((CanvasResponse) response);
			else if(response instanceof LabeledAssociationResponse)
				addClassAssociationNodes((LabeledAssociationResponse) response);
			else if(response instanceof CompartmentFromClassAssocResponse)
				addCompartmentNodesFromClassAssoc((CompartmentFromClassAssocResponse) response);
			else if(response instanceof CompartmentResponse)
				addCompartmentNodes((CompartmentResponse) response);
			else if(response instanceof ReferenceResponse)
				addReferenceNodes((ReferenceResponse) response);
		}
	}


	public Map<EClass, List<String>> getAssociatedAnnotations() {
		return associatedAnnotations;
	}



	private String getNestingAnnotation(){
		return NEST_W_KREF?DiagraphKeywords.KREFERENCE_:DiagraphKeywords.CREFERENCE_;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////REFERENCE NODE///////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////


	private void addReferenceNodes(ReferenceResponse response) {
		//String ref = DiagraphKeywords.REFERENCE + response.getRef().getName(); prior version
		String ref = DiagraphKeywords.REFERENCE + "=" +  response.getRef().getName(); //FP130516??  "=" was missing
		addSuplementaryAnnots(response.getSource(), response.getTarget(), ref);
	}


////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////COMPARTMENT NODE////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void addCompartmentNodes(CompartmentResponse response) {
		String cref = getNestingAnnotation() + "=" + response.getInclude().getName();
		addSuplementaryAnnots(response.getContainer(), response.getContenu(), cref);
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////COMPARTMENT FROM CLASS ASSOC NODE//////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void addCompartmentNodesFromClassAssoc(CompartmentFromClassAssocResponse response) {
		String cref = getNestingAnnotation() +"=" + response.getInclude().getName();
		addNodeAnnotation(response.getContainer());
		addAnnotation(response.getContainer(), cref);
		addNodeAnnotation(response.getContenu());
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////ASSOCIATION CLASS//////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void addClassAssociationNodes(LabeledAssociationResponse response) {
		//Check if the matched source is the canvas
		if(response.getSource().equals(this.canvas))
			return;

		addNodeAnnotation(response.getSource());
		addNodeAnnotation(response.getTarget().get(0));

		if(response.getLs() != null){
			addLinkNode(response);
		}else{
			addUnknownNode(response.getLabel());
		}
	}

	private void addUnknownNode(EClass label) {
		List<String> annots = associatedAnnotations.get(label);
		if(annots != null){
			for (int i=0; i<annots.size(); i++) {
				annots.remove(i);
			}
			annots.add(DiagraphKeywords.UNKNOWN);
		}else{
			ArrayList<String> unknownAnnot = new ArrayList<String>();
			unknownAnnot.add("unknown");
			associatedAnnotations.put(label, unknownAnnot);
		}

	}

	private void addLinkNode(LabeledAssociationResponse response) {
		//The annotations for the link node
		String cont = DiagraphKeywords.CONTAINMENT + "=" + response.getSource().getName() + "." + response.getSl().getName();
		String src = DiagraphKeywords.LINK_SOURCE + "=" + response.getLs().getName();
		String trg = DiagraphKeywords.LINK_TARGET + "=" + response.getLt().get(0).getName();

		ArrayList<String> annotsForLinkNode = new ArrayList<String>();
		annotsForLinkNode.add(DiagraphKeywords.LINK);
		annotsForLinkNode.add(cont);
		annotsForLinkNode.add(src);
		annotsForLinkNode.add(trg);
		associatedAnnotations.put(response.getLabel(), annotsForLinkNode);
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////CANVAS//////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void addCanvasNodes(CanvasResponse response) {
		addCanvas(response.getCanvas());
		addtopLevelNodes(response.getTopLevelNodes());
	}

	private void addtopLevelNodes(List<EClass> topLevelNodes) {
		for (EClass topLevelNode : topLevelNodes) {
			addNodeAnnotationAndDeleteTheOthersAnnots(topLevelNode);
		}

	}

	private void addCanvas(EClass canvas) {
		ArrayList<String> annotsForCanvas = new ArrayList<String>();
		annotsForCanvas.add(DiagraphKeywords.NODE);
		annotsForCanvas.add(DiagraphKeywords.POINT_OF_VIEW);
		associatedAnnotations.put(canvas, annotsForCanvas);
		this.canvas = canvas;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////UTILITYS////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void addAnnotation(EClass node, String annotation){
		List<String> annots = associatedAnnotations.get(node);
		if(annots != null){
			//Check if the node is already generated
			boolean annotFound = false;

			for (int i=0; i<annots.size(); i++) {
				//It the node is a pov, we should not add supplementary annotations
				if(annots.get(i).equals(annotation) || annots.get(i).equals(DiagraphKeywords.POINT_OF_VIEW))
					annotFound = true;
			}

			if(!annotFound)
				annots.add(annotation);
		}else{
			ArrayList<String> annotsForSimplelNode = new ArrayList<String>();
			annotsForSimplelNode.add(annotation);
			associatedAnnotations.put(node, annotsForSimplelNode);
		}
	}

	private void addNodeAnnotation(EClass node) {
		if(!this.visualElements.contains(node)){
			return;
		}
		List<String> annots = associatedAnnotations.get(node);
		if(annots != null){
			//Check if the node is already generated
			boolean nodeAnnotationFound = false;
			for (int i=0; i<annots.size(); i++) {
				if(annots.get(i).equals(DiagraphKeywords.NODE))
					nodeAnnotationFound = true;
				else if(annots.get(i).equals(DiagraphKeywords.LINK))
					annots.remove(DiagraphKeywords.LINK);
			}
			if(!nodeAnnotationFound)
				annots.add(DiagraphKeywords.NODE);
		}else{
			ArrayList<String> annotsForSimplelNode = new ArrayList<String>();
			annotsForSimplelNode.add(DiagraphKeywords.NODE);
			associatedAnnotations.put(node, annotsForSimplelNode);
		}
	}

	private void addNodeAnnotationAndDeleteTheOthersAnnots(EClass node) {
		List<String> annots = associatedAnnotations.get(node);
		if(annots != null){
			for (int i=0; i<annots.size(); i++) {
				annots.remove(i);
			}
			annots.add(DiagraphKeywords.NODE);
		}else{
			ArrayList<String> annotsForSimplelNode = new ArrayList<String>();
			annotsForSimplelNode.add(DiagraphKeywords.NODE);
			associatedAnnotations.put(node, annotsForSimplelNode);
		}
	}

	private void addSuplementaryAnnots(EClass source, EClass target, String annotation){
		List<String> annotTarget = associatedAnnotations.get(target);
		List<String> annotSource = associatedAnnotations.get(source);

		if(annotTarget == null){
			//Generate the annotation node for the target
			addNodeAnnotation(target);
			if(annotSource == null){
				addNodeAnnotation(source);
				addAnnotation(source, annotation);
			}else{
				if(!containsLinkOrUnknown(annotSource)){
					addAnnotation(source, annotation);
				}
			}
		}else{
			if(!containsLinkOrUnknown(annotTarget)){
				if(annotSource == null){
					addNodeAnnotation(source);
					addAnnotation(source, annotation);
				}else{
					if(!containsLinkOrUnknown(annotSource)){
						addAnnotation(source, annotation);
					}
				}
			}
		}
	}

	private boolean containsLinkOrUnknown(List<String> list){
		for (String s : list) {
			if(s.equals(DiagraphKeywords.LINK) || s.equals(DiagraphKeywords.UNKNOWN) || s.equals(DiagraphKeywords.POINT_OF_VIEW)){
				return true;
			}
		}
		return false;
	}
}
