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
package org.isoe.diagraph.inference;

import org.isoe.extensionpoint.generator.ISyntaxRefactoring;

/**
 *
 * @author "François.Pfister francois.pfister@gmail.com"
 *
 */
import org.isoe.diagraph.controler.IDiagraphControler;  //FP140707refactored
import org.isoe.extensionpoint.diagraph.action.DiagraphService;  //FP140707_c_refactored
public class SyntaxRefactoring implements ISyntaxRefactoring{

	private IDiagraphControler controler;

	@Override
	public void refactorDuplicateReferences() {
		// TODO Auto-generated method stub

	}

	@Override
	public void applyGenericNamedPattern() {
		// TODO Auto-generated method stub

	}

	@Override
	public void applySemanticLinkPatterns() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setControler(Object controler) {
		this.controler = (IDiagraphControler) controler;
		this.controler.registerService(this);
	}

	@Override
	public boolean isStub() {
		return false;
	}

	@Override
	public boolean isQualified() {
		return false;
	}

	@Override
	public String exposeContract() {
		return "void refactorDuplicateReferences()\n"
		+"void applyGenericNamedPattern()\n"
		+"void applySemanticLinkPatterns()\n"
		+"void setControler(IDiagraphControler controler)";
	}



	private static final boolean LOG = false;

	private boolean silent;

	public void setSilent(boolean value) {
		silent = value;
	}

	private void clog(String mesg) {
		if (LOG && !silent)
			System.out.println(mesg);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}


}
