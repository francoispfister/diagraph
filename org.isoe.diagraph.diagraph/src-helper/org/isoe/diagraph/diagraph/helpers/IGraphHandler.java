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
package org.isoe.diagraph.diagraph.helpers;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.isoe.diagraph.diagraph.DAffixedEdge;
import org.isoe.diagraph.diagraph.DEdge;
import org.isoe.diagraph.diagraph.DGeneric;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.diagraph.DGraphElement;
import org.isoe.diagraph.diagraph.DLabeledEdge;
import org.isoe.diagraph.diagraph.DLabeledElement;
import org.isoe.diagraph.diagraph.DNestedEdge;
import org.isoe.diagraph.diagraph.DNode;
import org.isoe.diagraph.diagraph.DOwnedEdge;
import org.isoe.diagraph.diagraph.DPointOfView;
import org.isoe.diagraph.diagraph.DReference;

/**
 *
 * @author fpfister
 *
 */
public interface IGraphHandler  {

	DGeneric addGeneric(String name, List<String> labels, EClass mel); //FP140518
	DLabeledEdge addLink(String name, List<String> labels, EClass eClass, DNode source,
			DNode target, EReference containementReference,
			EReference sourceReference, EReference targetReference);
	void addNodeLabels(EAnnotation eAnnotation);
	DPointOfView addPointOfView(EAnnotation eAnnotation);
	//DReference addReference(String name, DNode source, DNode target, EReference targetReference);
	DNode addSimpleNode(EAnnotation eAnnotation);
	DLabeledEdge createLabeledEdge(EClass owner, EReference ref);
	DLabeledEdge createLabeledEdge(EClass eclaz, String[] args);
	DLabeledEdge createLink(String name, List<String> labels, EClass eClass, DNode source,
			DNode target, EReference containementReference,
			EReference sourceReference, EReference targetReference);
	DNode findDNode(String nodeName);
	DGraphElement findElement(String name);
	List<DGraphElement> getAllConcreteElements();
	List<DNode> getAllNodes();
	DLabeledEdge getDLabeledEdge(EObject eObject);
	DEdge getEdge(String edgeName);
	List<DEdge> getEdges();
	List<DEdge> getEdges(DNode node);
	List<DEdge> getEdgesTo(DNode node);
	List<DGeneric> getGenericElements();
	DEdge getLabeledEdge(String edgeName);
	List<DLabeledEdge> getLabeledEdges(DNode node);
	String getLanguageName();
	DNode getNode(EObject eObject);
	List<DNode> getNodes();
	List<DReference> getReferenceEdges(DNode node);
	void save(URI uri);
	void setStyle(Object style);


	DNode addNode(String name, List<String> labels,
			EModelElement modelElement, String pointOfViewName, String navLink, boolean abztr);

	String logLabel(DGraphElement owner, String name,
			EAttribute eAttribute, boolean inSuperType, boolean inferred);

	DLabeledElement addLabel(DGraphElement owner_, String name,
			EAttribute eAttribute, boolean inSuperType, boolean inferred,
			boolean abztract);

	DNestedEdge addNestedEdge(String name, DNode source, DNode target,
			EReference targetReference, boolean isPropagated, boolean isDerived,
			boolean isCompartment, boolean isPort, int depth,
			String containmentName,
			boolean abztract);

	DAffixedEdge addPortContainment(String name, DNode source, DNode target,
			EReference targetReference,  boolean isPropagated, boolean abztr);

	DOwnedEdge finContainment(String srcName, String containmentName, String targetName);

	DNode createNode(String name, List<String> labels,
			EModelElement modelElement, String pointOfViewName, String navLink, boolean abztr);


	DReference addReference(String name, DNode source, DNode target,
			EReference targetReference, boolean propagated);

	List<DNode> resolveMissingTopNodes_(String nodeName,
			List<EReference> shouldExists);

	List<DGraphElement> getAllElements();


	EClass getEClassMapping(DGraphElement element);


	DLabeledElement findGeneric(String name);

	DLabeledEdge findLink(String name);

	DReference findReference(EReference reference);
	void initGraph(DGraph graph);
	void endGraph(DGraph graph);
	String getQualifiedLanguageName();



}
