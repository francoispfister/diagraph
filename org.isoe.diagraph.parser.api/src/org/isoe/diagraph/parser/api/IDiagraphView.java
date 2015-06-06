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
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EPackage;
import org.isoe.diagraph.parser.api.IDiagraphAssociation.AssociationType;

/**
 *
 * @author fpfister
 *
 */
public interface IDiagraphView {
	void addAssociation(IDiagraphReferenceAssociation assoc);
	void addClassAssociation(IDiagraphClassAssociation classoc);
	void addGeneric(IDiagraphElement el);
	void addNode(IDiagraphNode node);
	IDiagraphNode addPointOfView();
	void addReferenceAssociation(IDiagraphReferenceAssociation assoc);
	void addReferenceAssociations(List<IDiagraphReferenceAssociation> list);
	IDiagraphElement findGeneric(EClass eclaz);
	IDiagraphNode findNode(EClass eclaz);
	List<IDiagraphReferenceAssociation> findReferenceAssociationsByAssociationName(
			String targetName, String associationName,
			AssociationType[] associationType);
	List<IDiagraphReferenceAssociation> findReferenceAssociationsByTargetName(
			String name, AssociationType[] associationType);
	List<IDiagraphAssociation> getAssocs();
	List<IDiagraphClassAssociation> getClassAssociations();
	EPackage getDomainModel();
	List<IDiagraphElement> getGenerics();
	List<EModelElement> getModelElements();
	List<IDiagraphNode> getNodes();
	IDiagraphNotation getNotation();
	EClass getPointOfView();
	IDiagraphNode getPov();
	String getPovName();
	List<IDiagraphReferenceAssociation> getReferenceAssociations();
	List<IDiagraphAnnotation> getRootAnnotations();
	String getViewId();
	void init(EPackage packag);
	IDiagraphView parsePov();
	void setElements(List<EModelElement> modelElements, EClass pov);
	void setParsedAnnotations(List<List<IDiagraphAnnotation>> parsedAnnotations);
	void setPov(IDiagraphNode nod);
	void setViewId(String view);



}
