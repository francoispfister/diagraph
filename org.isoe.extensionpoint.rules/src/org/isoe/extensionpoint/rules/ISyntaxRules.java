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
package org.isoe.extensionpoint.rules;



import org.isoe.fwk.core.IDiagraphRuler;



/**
 *
 * @author fpfister
 *
 * future diagraph syntax rules extension-point,
 * this is a basis  for  refactoring
 * allready existing methods
 *
 */

import org.isoe.extensionpoint.diagraph.action.DiagraphService;  //FP140707_c_refactored
public interface ISyntaxRules extends DiagraphService {

	void setParser(IDiagraphRuler parser);

	void checkPendingReferences();

	void checkInheritance();

	void checkContainments();

	void validateContainment();

	void checkPointOfViewConsistence();

	void checkReferenceSyntax();

	void checkMultipleContainements();

	void checkDiagramRecursion();

	void checkReferenceNaming();

	void checkSyntax();

	boolean isStub();

	boolean isQualified();

	String exposeContract();

	void validate();

	void checkBeforeParsing();

}
