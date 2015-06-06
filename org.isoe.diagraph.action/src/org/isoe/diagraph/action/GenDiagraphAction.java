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
package org.isoe.diagraph.action;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecoretools.legacy.diagram.part.EcoreDiagramEditor;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDocumentEditor;
import org.eclipse.jface.action.Action;
import org.isoe.extensionpoint.diagraph.action.IGenDiagraphServ;
import org.isoe.fwk.core.DParams;

/**
 *
 * @author "François.Pfister francois.pfister@gmail.com"
 *
 */
import org.isoe.diagraph.controler.IDiagraphControler;  //FP140707refactored
import org.isoe.extensionpoint.diagraph.generator.IDiagraphGen;  //FP140707_b_refactored
public class GenDiagraphAction extends Action implements IGenDiagraphServ {

	private IDiagraphControler controler;
	private IDiagraphGen diagraphGenerator;
	private boolean justTheCurrentView = false;
	private boolean rcp_;
	private boolean refactor;
	private boolean headless;
	private boolean refreshOnly; // FP140602see

	@Override
	public void setRefactor(boolean refactor) {
		this.refactor = refactor;
	}

	@Override
	public void setControler(Object controler) {
		this.controler = (IDiagraphControler) controler;
		this.controler.registerService(this);
	}

	@Override
	public void setCurrentView() {
		this.justTheCurrentView = true;
	}

	@Override
	public void setAllViews() {
		this.justTheCurrentView = false;
	}

	@Override
	public void setDiagraphGenerator(Object diagraphGenerator) {
		this.diagraphGenerator = (IDiagraphGen) diagraphGenerator;
	//	diagraphGenerator.setControler(controler);
	}

	public GenDiagraphAction() {
		setText("GenDiagraphAction Action");
		setToolTipText("GenDiagraphAction Action");
	}

	@Override
	public void genCurrentView(IDocumentEditor ecoreDiagramEditor) {
		if (LOG)
			clog("getCurrentView");
		boolean forceGeneration = false;
		boolean save = true;
		boolean atRuntime = true;
		// boolean old = true; //FP140211
		// boolean rcp=false; //FP130507zz
		GenModel gm = null;
		boolean refreshOnly = true;
		IProgressMonitor nullProgressMonitor = new NullProgressMonitor();
		boolean validate = false;
		String lang = ((EPackage) ((EcoreDiagramEditor) ecoreDiagramEditor)
				.getDiagram().getElement()).getName(); // FP140611
		String log = diagraphGenerator.generateDiagraphwold("gda_",
				forceGeneration, gm,
				controler.getCurrentView(19, lang, this.getClass().getName()),
				!save, ((EcoreDiagramEditor) ecoreDiagramEditor).getDiagram(),
				atRuntime, nullProgressMonitor, rcp_, validate, refactor,
				refreshOnly); // FP130417
		if (!log.isEmpty())
			controler.showMessage(log);
		controler.showDiagraphLog();
	}

	@Override
	public void genAllViews(IDocumentEditor ecoreDiagramEditor) {
		if (LOG) {
			String lang = controler.getCurrentPackage().getName();
			String vv = lang + " ";
			for (String layer : controler.getViews())
				vv += layer + "; ";
			clog("genAllViews " + vv);
		}
		String[] layers = controler.getViews();
		String viewname = controler.getViewName();
		if (layers != null) {
			for (int i = 0; i < layers.length; i++) { // FP140619
				controler.setViewText(layers[i]);

				if (!diagraphGenerator.runGenNotation(layers[i], refactor,
						headless, refreshOnly)) {
					controler.cerror("generation failed with view "
							+ layers[i]);
					break;
				}
			}
			controler.setViewText(viewname);
		}
	}

	@Override
	public void setHeadless(boolean headless) {
		this.headless = headless;
	}

	@Override
	public void run() {
		EcoreDiagramEditor ecoreDiagramEditor = controler
				.getEcoreDiagramEditor();
		if (ecoreDiagramEditor != null) {
			if (justTheCurrentView)
				genCurrentView(ecoreDiagramEditor);
			else
				genAllViews(ecoreDiagramEditor);
		} else
			clog("no opened editor");
	}

	@Override
	public void setRcp(boolean value) {
		this.rcp_ = value;
	}

	@Override
	public boolean isStub() {
		return false;
	}

	@Override
	public boolean isQualified() {
		return true;
	}

	private static final boolean LOG = DParams.GenDiagraphAction_LOG;

	private boolean silent;

	@Override
	public void setSilent(boolean value) {
		silent = value;
	}

	private void clog(String mesg) {
		if (LOG && !silent)
			System.out.println(mesg);
	}

	@Override
	public void setRefreshOnly(boolean refreshOnly) {
		this.refreshOnly = refreshOnly;
	}

}
