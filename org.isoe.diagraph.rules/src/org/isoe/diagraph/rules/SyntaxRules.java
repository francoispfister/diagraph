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
package org.isoe.diagraph.rules;

import java.util.Iterator;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.isoe.extensionpoint.rules.ISyntaxRules;
import org.isoe.fwk.core.IDiagraphRuler;

//TODO refactor the following prototypes here, I have to write my thesis, no time left...

/**
 *
 * @author "François.Pfister francois.pfister@gmail.com"
 *
 */
import org.isoe.diagraph.controler.IDiagraphControler; //FP140707refactored
import org.isoe.extensionpoint.diagraph.action.DiagraphService; //FP140707_c_refactored

public class SyntaxRules implements ISyntaxRules {

	private String errorMessage = "";;
	private IDiagraphRuler diaParser;
	private IDiagraphControler controler;

	@Override
	public void checkBeforeParsing() {
		errorMessage = "";
		try {
			checkReferenceNames(); // FP150325z2
		} catch (Exception e) {
			errorMessage += e.toString();
			throw new RuntimeException(errorMessage);
		}

	}

	private void checkReferenceNames() { // is not qualified, but this is used
		for (Iterator<EObject> contents = diaParser.getDomainModel()
				.eAllContents(); contents.hasNext();) {
			EObject el = contents.next();
			if (el instanceof EReference) {
				EReference ref = (EReference) el;
				if (ref.getName() != null && ref.getName().isEmpty()) {
					String err = " A Reference has an empty name: "
							+ ((EClass) (ref.eContainer())).getName()
							+ " ----> " + ref.getEType().getName();
					throw new RuntimeException(err);
				}
			}
		}
	}

	@Override
	public void validate() {
		errorMessage = "";
		try {
			checkGeneric(); // FP150326
		} catch (Exception e) {
			errorMessage += e.toString();
			throw new RuntimeException(errorMessage);
		}
	}

	private void checkGeneric() {

		for (Iterator<EObject> contents = diaParser.getDomainModel()
				.eAllContents(); contents.hasNext();) {
			EObject el = contents.next();
			if (el instanceof EClass) {
				EClass c = (EClass) el;
				if (diaParser.isNode(c))
					checkGenericNode(c);
				else if (diaParser.isLink(c))
					checkGenericLink(c);
			}
		}

	}

	private void checkGenericNode(EClass c) {

		if (diaParser.isView(c) && !c.isAbstract()
				&& !diaParser.isNodeInstanciable(c)) {
			String err = "(view "+ diaParser.getViewId()+") The Node: "
					+ c.getName() + " is not instanciable";
			throw new RuntimeException(err);
		}
	}

	private void checkGenericLink(EClass c) {
		if (diaParser.isView(c) && !c.isAbstract()
				&& !diaParser.isLinkInstanciable(c)) {
			String err = "(view "+ diaParser.getViewId()+") The Link: "
					+ c.getName() + " is not instanciable";
			throw new RuntimeException(err);
		}
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
	public void setControler(Object controler) {
		this.controler = (IDiagraphControler) controler;
		this.controler.registerService(this);
	}

	@Override
	public void setParser(IDiagraphRuler diaParser) {
		this.diaParser = diaParser;
	}

	@Override
	public void checkPendingReferences() {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkInheritance() {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkContainments() {
		// TODO Auto-generated method stub

	}

	@Override
	public void validateContainment() {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkPointOfViewConsistence() {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkReferenceSyntax() {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkMultipleContainements() {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkDiagramRecursion() {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkReferenceNaming() {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkSyntax() {
		// TODO Auto-generated method stub

	}

	@Override
	public String exposeContract() {
		return "void checkBeforeParsing()\n"
				+ "void setControler(IDiagraphControler controler)\n"
				+ "void setParser(IDiagraphRuler diaParser)\n"
				+ "void checkPendingReferences()\n" + "void checkGeneric()\n"
				+ "void checkInheritance()\n" + "void checkContainments()\n"
				+ "void validateContainment()\n"
				+ "void checkPointOfViewConsistence()\n"
				+ "void checkReferenceSyntax()\n"
				+ "void checkMultipleContainements()\n"
				+ "void checkDiagramRecursion()\n"
				+ "void checkReferenceNaming()\n" + "void checkSyntax()";
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
