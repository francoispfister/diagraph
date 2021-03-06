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
//FP150512
/**
 *
 * @author fpfister
 *
 */
public interface IDiagraphObject {
	IDiagraphParser getParser();
	String getName();
	String getView();
	boolean isAbstract();
	boolean isGeneric();
	String getInfo();
	int getRecursiveDepth();
	void addMapping(IDiagraphMapping mapping);
	List<IDiagraphMapping> getMappings();
	int getDepth();
	boolean isDerived();
	void setDerived(boolean derived);
	EReference getContainment();
	EReference getDeclaredContainment();
	boolean setDeclaredContainment(EClass source, String declaredContention);
}
