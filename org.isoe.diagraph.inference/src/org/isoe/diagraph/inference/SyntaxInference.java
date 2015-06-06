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

import org.isoe.extensionpoint.generator.ISyntaxInference;

/**
 *
 * @author "fpfister"
 *
 *
 *         future diagraph syntax inference contribution, ready to refactor allready existing methods
 *         here
 */
import org.isoe.diagraph.controler.IDiagraphControler;  //FP140707refactored
public class SyntaxInference implements ISyntaxInference {

	private IDiagraphControler controler;

	@Override
	public void resolveContainmentNode() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resolveContainmentReferences() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resolveInheritedFeatures() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inferLinkTargetReferences() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inferLinkSourceReferences() {
		// TODO Auto-generated method stub

	}

	@Override
	public void sortContainments() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inferContainments() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resolveTargetRefs() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resolveContainmentsWithName() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resolveMissingCRefsIfCanvas() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inferMissingCref() {
		// TODO Auto-generated method stub

	}

	@Override
	public void propagateInheritedLabelsIfNoLabel() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inferMissingLabels() {
		// TODO Auto-generated method stub

	}

	@Override
	public void cloneInheritedContainments() {
		// TODO Auto-generated method stub

	}

	@Override
	public void propagateAllInheritedLabels() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resolveLinkSourceAndTarget() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resolveOwnLabelAttributes() {
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
		return "void resolveContainmentNode()\n"
				+ "void resolveContainmentReferences()\n"
				+ "void resolveInheritedFeatures()\n"
				+ "void inferLinkTargetReferences()\n"
				+ "void inferLinkSourceReferences()\n"
				+ "void sortContainments()\n" + "void inferContainments()\n"
				+ "void resolveTargetRefs()\n"
				+ "void resolveContainmentsWithName()\n"
				+ "void resolveMissingCRefsIfCanvas()\n"
				+ "void inferMissingCref()\n"
				+ "void propagateInheritedLabelsIfNoLabel()\n"
				+ "void inferMissingLabels()\n"
				+ "void cloneInheritedContainments()\n"
				+ "void propagateAllInheritedLabels()\n"
				+ "void resolveLinkSourceAndTarget()\n"
				+ "void resolveOwnLabelAttributes()\n"
				+ "void setControler(IDiagraphControler controler)";
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
