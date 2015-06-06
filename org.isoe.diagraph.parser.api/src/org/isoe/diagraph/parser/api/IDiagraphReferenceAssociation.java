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

/**
 *
 * @author fpfister
 *
 */
public interface IDiagraphReferenceAssociation extends IDiagraphAssociation {

	boolean isRefContainment();
	boolean isCompartment();
	boolean isPort();
	boolean isContainment();
	boolean isExternal();
	boolean isSharedCompartment();
	void setDeclaredContainment(EReference reference);
	void setDeclaredCase(int caze);
	String getTargetName();
	void setTargetName(String target);
	void setCompartment(boolean compartment);
	void setPort(boolean port);
	void setDerived(boolean derived);
	String toLog();
	String toLogCompl();
	void setRecursive();
	boolean isRecursive();
	String toId();

	void addSibling(EClass eclaz);
	List<EClass> getSiblings();

	List<EClass> getTargetAbstracts();
	void addSimple(EClass eclaz);
	List<EClass> getSimples();
	void addSimple2(EClass eclaz);
	List<EClass> getSimple2s();
	void addKrefBySuper(EClass eclaz);
	List<EClass> getKrefBySupers();
	void addKrefBySub(EClass krefBySub);
	List<EClass> getKrefBySubs();
	void addKrefContainmentAlt(EClass eclaz);
	List<EClass> getKrefContainmentAlts();
	boolean isPropagated();
	void addTargetAbstract_(EClass targetAbstract);
	void resolveTargets();
	void setArgument(String arg);
	String getArgument();
	String toCsv();
}
