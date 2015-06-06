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
package org.isoe.diagraph.internal.m2.api;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EReference;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.diastyle.DStyle;
import org.isoe.diagraph.internal.api.IDiaNode;
import org.isoe.diagraph.internal.api.IDiaPlatformDelegateProvider;
import org.isoe.diagraph.internal.api.IDiaPointOfView;
import org.isoe.diagraph.internal.m2.DiaContainedElement;
import org.isoe.diagraph.internal.m2.DiaContainment;
import org.isoe.diagraph.internal.m2.DiaGenericElement;
import org.isoe.diagraph.internal.m2.DiaLabel;
import org.isoe.diagraph.internal.m2.DiaLink;
import org.isoe.diagraph.internal.m2.DiaNode;
import org.isoe.diagraph.internal.m2.DiaPointOfView;
import org.isoe.diagraph.internal.m2.DiaReference;
import org.isoe.diagraph.internal.m2.parser.DGraphElementType;
import org.isoe.diagraph.parser.api.IDiagraphAssociation.AssociationType;
import org.isoe.diagraph.parser.api.IDiagraphParser;

/**
 *
 * @author fpfister
 *
 */
public interface IDiaParser {

	void addErrorMessage(String message);
	void cerror(String mesg);
	void checkBeforeParsing();
	IDiaContainment createContainment_(AssociationType containmentType,
			EClass element, EClass targetClass, String target,
			String containmentName, boolean propagated, boolean doit);
	DiaGenericElement createGenericElement(EModelElement element);
	DiaLabel createInferredLabel(DiaContainedElement del, String name, String attributeName);
	IDiaContainment createMissingCReference(EReference cref, boolean isPropagated);
	IDiaContainment createNodeCompartment_(EModelElement element, String target,
			String label, boolean propagated, boolean doAdd, boolean doit);
	IDiaContainment createNodeContainment(EClass element, EClass targClass,
			String target, String containmentName, boolean isPort,
			boolean propagated, boolean doAdd, boolean derived);
	DiaContainedElement findDiagramElement(EModelElement element);
	DiaNode findNode(String nodeName);
	List<DiaGenericElement> getAllGenericElements();
	List<DiaNode> getAllNodes();
	List<EReference> getAllReferences();
	DiaNode getCanvas(EModelElement element);
	DiaContainedElement getContainedElement(EModelElement element, String value);
	IDiaContainment getContainerContainment(EModelElement element,
			String containmentName, boolean isPropagated);
	List<IDiaContainment> getContainments();
	List<IDiaContainment> getCreatedContainments();
	List<DiaReference> getCreatedReferences();
	List<DiaContainedElement> getDiagramElements();
	String getErrorMessage();
	DiaContainedElement getGenericElement(EModelElement element);
	DiaGenericElement getGenericElementWithReference(EModelElement element, String value, String argument);
	DiaContainedElement getLabelledElement(EModelElement container, DGraphElementType elementType, boolean elementPromoted,
			String attributeName, String name);
	DiaContainedElement getLabelledElement(EModelElement element, String value, String argument);
	DiaContainedElement getLabelledElements(EModelElement container,
			String attributeName, String name);
	DiaLink getLinkWithSource(EModelElement element, String value);
	DiaLink getLinkWithtarget(EModelElement element, String target, String label);
	String getLog();
	List<EModelElement> getModelElements();
	IDiaContainment getNamedContainment(EModelElement element,
			String containmentName, boolean isPropagated);
	DiaLink getNamedLink(EClass element, String linkTarget, String linkName);
	DiaNode getNamedNode(EModelElement element, String value);
	DiaNode getNode(EModelElement element);
	DiaNode getNodeWithCReference(EClass element, String target, EClass targ,
			boolean figureEmbedded, boolean propagated);
	DiaNode getNodeWithKReference(EModelElement element, String target,
			String argument2, String label, EClass targ,
			boolean figureEmbedded, boolean propagated);
	DiaNode getNodeWithLink(EModelElement element, String target, String name);
	DiaNode getNodeWithPReference(EClass element, EClass targetClass,
			String target, String label, boolean propagated);
	DiaNode getNodeWithReference_(EModelElement element, String value, String argument);
	DiaNode getNodeWithStyle(EModelElement element, String value, String argument);
	IDiagraphParser getParser2();
	int getPhase();
	IDiaPlatformDelegateProvider getPlatformDelegateProvider();
	IDiaPointOfView getPointOfView();
	DStyle getStyleSheet();
	List<EClass> getSubClasses(EReference ref, boolean containment);
	String getView();
	boolean isDianodeOrRecursiveCanvasOrDialinkOrGeneric(DiaContainedElement diagramElement);
	boolean isDisabled();
	boolean isLinkInstanciable(EClass c);
	boolean isNodeInstanciable(EClass c);
	void postParse();
	void postTransform(DGraph dgraph);
	void traverseInheritance(IDiaElementVisitor v);
	void inferMissingCrefs(DiaPointOfView pov);
	boolean isPov(EClass eClass);



	DiaContainment createContainment(boolean propagated, boolean compartment,
			boolean port, String refname, String typName, String targetName,
			IDiaNode sourceNode, boolean dummy);
	boolean isCref(EReference ref);


}
