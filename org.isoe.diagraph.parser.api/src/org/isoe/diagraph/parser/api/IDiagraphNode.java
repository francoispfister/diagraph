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
import org.isoe.diagraph.parser.api.IDiagraphReferenceAssociation;


/**
 *
 * @author fpfister
 *
 */
public interface IDiagraphNode extends IDiagraphElement{
	void addContainment(int i, IDiagraphAssociation containment, int j,
			boolean doAdd);

	void createMapping();
	void addNodeMapping(Object nodeMapping, Object childReference);
	IDiagraphReferenceAssociation findContainmentByTargetName(String label);
	List<EReference> getAbstractAutoCompartmentBySupers();
	List<EReference> getAbstractAutoCompartments();
	EReference getActualContainment();
	List<IDiagraphReferenceAssociation> getAllContainments();
	List<IDiagraphReferenceAssociation> getAllSiblingContainments();
	List<IDiagraphReferenceAssociation> getAllSimpleReferences();
	List<EReference> getAutoCompartmentBySupers();
	List<EReference> getAutoCompartments();
	String getChildInfo();
	EReference getContainmentAlt();
	EReference getContainmentBase_();
	//List<EReference> getContainments1();
	List<EReference> getDirectContainmentReferences();
	List<EReference> getKrefAltContainmentReferences();
	EClass getPovIfSuperPov();
	IDiagraphReferenceAssociation getRecursiveCompartment(EReference eReference);
	List<IDiagraphReferenceAssociation> getSimpleReferences();
	List<EClass> getSubGenericsOrNodes();
	List<IDiagraphNode> getSubNodes();
	List<EClass> getSuperGenericsOrNodes();
	boolean isCanvas();
	boolean isDirectTopNode();
	boolean isInheritedTopNode();
	boolean isOrSubNodeCanvas();
	boolean isPov();
	boolean isSelfContained();
	boolean isSelfContainedThroughInheritance();
	void setPov();


	IDiagraphNode getContainer();
	IDiagraphNode getCanvasContainment();
	IDiagraphNode getSourceNode();
	void addDerivedSubNodes(List<IDiagraphNode> subnodes);
	boolean isCompartment();
	boolean isPort();
	List<IDiagraphNode> getSuperElements();
	EReference getContainerReference();
	void setDeclaredContainment(EReference containment);
	void setContainment(EReference reference);
	void setContainments(List<IDiagraphReferenceAssociation> containments);
	void setContainmentAlt(EReference containement);
	List<IDiagraphReferenceAssociation> getContainments();
	String handleSelfCompartmentContainementPattern();
	void setCreatedContainment(IDiagraphReferenceAssociation createdContainment);
	IDiagraphReferenceAssociation getCreatedContainment();

	void setNavigation(String navigation);
	String getNavigation();

	void setXtraDeclaredContainmentReference_nu(EReference xcontref);

	void setContainmentName(String containementName);

	String toLog();

	String toLogCompl();







}