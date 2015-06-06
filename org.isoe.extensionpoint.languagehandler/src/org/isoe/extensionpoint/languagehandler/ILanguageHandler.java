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
package org.isoe.extensionpoint.languagehandler;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.extensionpoint.ecoreutils.IEcoreUtils;



/**
 *
 * @author "fpfister"
 *
 */
import org.isoe.diagraph.controler.IDiagraphControler;  //FP140707refactored
public interface ILanguageHandler {
	void setControler(IDiagraphControler controler);
	IDiagraphControler getControler();
	boolean isStub();
    List<DGraph> getConcreteSyntax();
    EReference parseExtraContainmentReference(EClass eclass, String view); //FP131008
	EClass getPovForView(EPackage pakag, String view);
	String newParse_nu(EClass toTransform,boolean headless);
	//EPackage merge(DGraph diagraph, DGraph withDiagraph, boolean headless);
	EPackage clone(DGraph diagraph, boolean headless);
	void setEcoreUtils(IEcoreUtils ecoreUtilService);
	EPackage merge(DGraph currentDiagraph, DGraph withDiagraph,
			boolean headless, boolean byscript);

}
