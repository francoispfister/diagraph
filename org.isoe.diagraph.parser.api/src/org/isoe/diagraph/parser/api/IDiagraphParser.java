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
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.isoe.diagraph.parser.api.IDiagraphAssociation.AssociationType;



/**
 *
 * @author fpfister
 *
 */
public interface IDiagraphParser {


	enum InheritanceType {
		DIRECT, INHERITANCE
	};

	enum NodeType {
		NODE, LINK, GENERIC, GENERIC_OR_NODE, GENERIC_OR_LINK
	};


	static final boolean CONTAINMENT_TRUE = true;

	void cerror(String string);
	void checkBeforeParsing();
	IDiagraphReferenceAssociation createContainment(
			AssociationType containmentType, EClass element, String target,
			String containmentName, boolean propagated, boolean doit);
	IDiagraphReferenceAssociation createReferenceAssociation(EReference ref,
			boolean simple);
	String ctToString(AssociationType containmentType);
	void endParse_2(List<EModelElement> modelElements, IProgressMonitor progressMonitor);
	IDiagraphAssociation findAssociation_(EClass source, EClass target);
	IDiagraphAssociation findClassAssociation(EClass c);
	IDiagraphNode findNode_(EClass claz);
	List<EReference> getAllDiagraphContainers(EClass eclass);
	List<EReference> getAllDiagraphContainers(EClass eClass,
			EClass target);
	List<EReference> getAllReferences(EClass eClass, boolean containment);
	List<EReference> getAllReferences(EPackage pakage, boolean containment);
	List<EReference> getAllReferencesTo(EClass eclaz);
	List<EReference> getAllSimpleReferences(EClass eClass);
	IDiagraphReferenceAssociation getBaseContainment(EClass eModelElement);
	IDiagraphNode getCanvas(EClass eclaz);
	List<EClass> getConcreteSubLinks(EClass eClass);
	List<EClass> getConcreteSubNodes_(EClass eClass);
	EReference getContainment(EClass source);
	List<IDiagraphReferenceAssociation> getContainmentAssociationsAndSubHierTo(
			EClass eclaz);
	IDiagraphView getCurrentPointOfView();
	String getCurrentView();
	EReference getDeclaredContainment(EClass eClass, int depth);
	EAnnotation getDiagraphAnnotation(EClass eClass);
	List<List<IDiagraphAnnotation>> getDiagraphAnnotations();
	IDiagraphAssociation getDiagraphClassAssociation(EClass c);
	List<EClass> getDiagraphedClasses(NodeType nodeType, boolean concrete,
			EPackage pack);
	IDiagraphElement getDiagraphGeneric(EClass eclaz, int depth);
	IDiagraphNode getDiagraphNode(EClass eclaz, int depth);
	List<EReference> getDirectContainementReferences(EClass eClass);
	List<EReference> getDirectReferencesTo(EClass eclaz);
	List<EReference> getDirectSimpleReferences(EClass eClass);
	Set<EClass> getElements();
	EAnnotation getInViewDiagraphAnnotation(EClass eclaz);
	String getLog();
	IDiagraphNode getNamedNode(EClass eclaz, String nodeLabels);
	List<EReference> getNodeRefsToNodeOrGeneric(EClass eclaz,
			boolean containment);
	IDiagraphNode getPointOfView(EPackage pack);
	EClass getPointOfView(List<EModelElement> modelElements);
	EClass getPointOfView(String eclassName);
	String getPointOfViewCandidate(EClass eclaz);
	List<String> getPointOfViewCandidates();
	List<String> getPointOfViewCandidates(List<EClass> classes);
	EClass getPointOfViewClass(EPackage ePackage);
	EReference getPovDiagraphContainer(EClass eClass);
	EClass getPovIfSuperPov(EClass claz);
	IDiagraphReferenceAssociation getRecursiveCompartment(EClass eclaz,
			String refName);
	String getReport();
	String getReports();
	List<IDiagraphReferenceAssociation> getSimpleAssociationsAndSubHierTo(
			EClass eclaz);
	List<EReference> getSimpleReferencesTo_(EClass eclaz);
	List<EClass> getSubDiagraphedClasses_(NodeType nodeType, EClass c,
			boolean concrete);
	List<EClass> getSubGenerics(EClass claz);
	List<EClass> getSubGenericsOrLinks(EClass claz);
	List<EClass> getSubGenericsOrNodes_(EClass claz);
	List<EClass> getSubLinks(EClass claz, boolean concrete);
	List<EClass> getSubNodes(EClass claz, boolean concrete);
	List<EClass> getSuperDiagraphedClasses(NodeType nodeType, EClass c,
			boolean concrete);
	List<EClass> getSuperGenerics(EClass eClass);
	List<EClass> getSuperGenericsOrLinks(EClass eClass);
	List<EClass> getSuperGenericsOrNode(EClass claz,
			boolean concrete);
	List<EClass> getSuperLinks(EClass eClass);
	List<EClass> getSuperNodes(EClass claz, boolean concrete);
	List<EReference> getSuperReferencesTo(EClass eclaz);
	List<EClass> getTaggedClasses(EPackage pakage);
	IDiagraphReferenceAssociation getTopLevelAssociation(EClass eclaz);
	String getViewName(EAnnotation annotation);
	List<IDiagraphReferenceAssociation> guessContainments(EClass eclaz,
			boolean abztract);
	List<IDiagraphReferenceAssociation> guessContainments(String view,
			EClass eclass);
	void initView(EPackage packag, String view);
	boolean isAnnotated(EClass eclaz, String name, AssociationType c,
			InheritanceType inhtype);
	boolean isClassAssociation(EClass eclaz);
	boolean isCurrentView(EClass c);
	boolean isDefaultView(EAnnotation anot);
	boolean isDiagraphAnnotated(EClass eclass);
	boolean isDirectTopNode(EClass eClass);
	boolean isGeneric(EAnnotation eAnnotation);
	boolean isGeneric(EClass eclaz);
	boolean isGenericOrLink(EAnnotation eAnnotation);
	boolean isGenericOrNode(EAnnotation eAnnotation);
	boolean isInheritedTopNode(EClass claz);
	boolean isLink(EAnnotation eAnnotation);
	boolean isLink(EClass eClass);
	boolean isLinkInstanciable(EClass c);
	boolean isNode(EAnnotation eAnnotation);
	boolean isNode(EClass eclaz);
	boolean isNodeInstanciable(EClass c);
	boolean isPov(EClass eclaz);
	boolean isSelfContained(EClass eClass);
	boolean isSubPov(EClass eclaz);
	boolean isView(EAnnotation anot);
	EAnnotation isView(EClass eclaz);
	void logAnnotations();
	void logNodesAndSubnodes();
	void parse21NodesAndRelations(List<EModelElement> modelElements,
			IProgressMonitor progressMonitor);
	void parse22LinkAnnotations(List<EModelElement> modelElements,
			IProgressMonitor progressMonitor);
	void parse22NodeAnnotations(List<EModelElement> modelElements,
			IProgressMonitor progressMonitor);
	List<IDiagraphReferenceAssociation> parseAllAssociationsTo(EClass eclaz,
			boolean containment);
	List<EReference> parseAllContainementReferences(EClass eclaz);
	List<IDiagraphReferenceAssociation> parseContainmentAssociationsAbstractTo(
			EClass eclaz_, String targetValue);
	List<IDiagraphReferenceAssociation> parseContainmentAssociationsConcreteTo_(
			EClass eclaz, String targetValue);
	List<IDiagraphReferenceAssociation> parseDirectSimpleAssociationsTo(
			EClass eclaz);
	List<IDiagraphAnnotation> parseElementAnnotations(EClass element,
			IProgressMonitor progressMonitor);
	EReference parseExtraContainmentReference(EClass eclazid);
	String parseNavigation(List<IDiagraphAnnotation> annotations);
	void parsePov();
	IDiagraphReferenceAssociation parseReferenceAssociation_(EClass refsource,
			EClass refinstancesource, EClass target, EReference ref,
			boolean derived, boolean container);
	boolean patternContainedThroughInheritance(EClass claz);
	List<EReference> patternContainmentAssociationsKrefAutoCBySub(
			EClass eclaz, boolean abstragt);
	List<EReference> patternContainmentAssociationsKrefAutoCBySubAbstract(
			EClass eclass, boolean b);
	List<EReference> patternContainmentAssociationsKrefAutoCBySuper(
			EClass eclaz, boolean abstragt);
	List<EReference> patternContainmentAssociationsKrefAutoCont(
			EClass eclass, boolean b);
	List<EReference> patternKrefContainmentAlt(EClass eclaz);
	List<IDiagraphReferenceAssociation> patternSiblingAllContainmentAssociations(
			EClass eclaz);
	//List<IDiagraphReferenceAssociation> patternTargetAbstractContainmentAssociationsTo(
	//		EClass eclaz);
	void postParse();
	void preParse(List<EModelElement> modelElements);
	IDiagraphView setPointOfView_(EPackage packag,
			List<EModelElement> modelElements, String view);
	void startParse_2();
	void startPointOfView();
	List<EClass> subClasses(EClass c);
	void validate();
	void parse22LnkAnnotations(List<EModelElement> modelElements,
			IProgressMonitor progressMonitor);
	List<IDiagraphReferenceAssociation> patternTargetConcreteContainmentAssociationsTo(
			EClass eclaz);
	boolean isCref(EReference ref);

}
