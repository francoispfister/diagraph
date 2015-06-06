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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
//import org.isoe.diagraph.internal.m2.DiaNode;
import org.isoe.diagraph.internal.api.IDiaNode;


/**
 *
 * @author fpfister
 *
 */
public interface IDiaContainment {
	String getArgument();
	Object getCompartmentMapping();
	String getContainmentName_();
	String getDeferredHost();
	EClass getEModelElement();
	String getName();
	int getOrder();
	EReference getSourceContainmentReferenceAlt();
	EReference getSourceContainmentReferenceBase();
	IDiaNode getSourceNode();
	IDiaNode getTargetNode();
	EReference getTargetReference();
	//String getTargetRefName();
	//String getTargetTypeName();
	boolean isCompartment();
	boolean isContainment();
	boolean isDerived();
	boolean isDerivedCompartment();
	boolean isPort();
	boolean isPropagated();
	void logTargetNode();
	void resolveSourceNode();
	void resolveTargetNode();
	void resolveTargetRef();
	void resolveTargets();
	void setAbstract(boolean value);
	void setArgument(String arg);
	void setCompartmentMapping(Object cm);
	void setDeferredHost(String host);
	void setDerived(boolean derived);
	void setOrder(int order);
	void setSourceNode(IDiaNode nod);
	void setTargetNode(IDiaNode trgnode);
	void setTargetReference(EReference er);

	//String getTargetRefName_();
	//String getConcreteTypeName();
	//IDiaNode getTargetTypeNode();
	String toLog();
	String getNameIfShared();
	String getNameIfCompartment();
}
