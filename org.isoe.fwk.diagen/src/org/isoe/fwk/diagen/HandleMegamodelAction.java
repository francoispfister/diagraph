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
package org.isoe.fwk.diagen;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.Action;
import org.isoe.diagraph.workbench.api.DiagraphNotifiable;
import org.isoe.extensionpoint.diagen.IDiagraphAlphabet;
import org.isoe.extensionpoint.diagen.IMegamodelService;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.diagen.jobs.ExportImagesJob;
import org.isoe.fwk.diagen.jobs.GenEditorJob;
import org.isoe.fwk.diagen.jobs.GrammarJob;
import org.isoe.fwk.diagen.jobs.HandleLanguagesJob;
import org.isoe.fwk.diagen.jobs.ModelWeaverJob;
import org.isoe.fwk.diagen.jobs.WeaverJob;

/**
 * @author fpfister
 *
 */
public class HandleMegamodelAction extends Action implements IMegamodelService {
	// org.isoe.fwk.diagen.HandleMegamodelAction
	private static final boolean LOG = DParams.HandleMegamodelAction_LOG;
	private List<IProject> selection;
	private IProject megamodel;
	// private IProject project;
	private DiagraphNotifiable owner;
	private String cmd;
	private boolean lineNumbered;
	private boolean legended;
	private boolean showStatus;
	private boolean silent;
	private boolean headless;

	// private String language="";//FP140604dodo

	@Override
	public void setControler(DiagraphNotifiable owner) {
		this.owner = owner;
	}

	@Override
	public void setLineNumbered(boolean lineNumbered) {
		this.lineNumbered = lineNumbered;
	}

	@Override
	public void setLegended(boolean legended) {
		this.legended = legended;
	}

	@Override
	public void setShowStatus(boolean showStatus) {
		this.showStatus = showStatus;
	}

	public HandleMegamodelAction() {

	}

	@Override
	public void setSelection(List<IProject> selectedProjects) {
		selection = selectedProjects;
	}

	/*
	 * @Override public void setProject(IProject project) { this.project =
	 * project; }
	 */

	@Override
	public void setHeadless(boolean headless) {
		this.headless = headless;
	}

	@Override
	public void run() { // FP140515

		if (cmd.equals(IDiagraphAlphabet.MEGAMODEL_CMD_MODEL_WEAVER)) {
			// IProject project= selection.get(0);
			// String language = project.getName();
			// language = language.substring(language.lastIndexOf(".")+1);
			clog("scheduling model weaver");// + project.getName());
			new ModelWeaverJob(megamodel, headless, "", owner).doRun();
		} else {
			if (selection != null) {

				if (cmd.equals(IDiagraphAlphabet.MEGAMODEL_CMD_EXPORT_IMAGE)) {
					for (IProject project : selection) {
						String language = project.getName();
						language = language
								.substring(language.lastIndexOf(".") + 1);
						clog("scheduling image export for " + language);
						new ExportImagesJob(project, headless, language, owner)
								.doRun();
					}

				} else if (cmd
						.equals(IDiagraphAlphabet.MEGAMODEL_CMD_GRAMMAR)) {
					IProject project = selection.get(0);

					String options = "exportTxt;generatorv2";
					String language = project.getName();
					language = language
							.substring(language.lastIndexOf(".") + 1);
					clog("scheduling textual grammar generation for " + language +" options="+options+" headless="+(headless?"headless":" gui"));
					new GrammarJob(project, headless, language, owner,
							options).doRun(); // FP140524 textual
				} else if (cmd.equals(IDiagraphAlphabet.MEGAMODEL_CMD_GEN_EDITOR)) {
					IProject project = selection.get(0);
					String language = project.getName();
					language = language
							.substring(language.lastIndexOf(".") + 1);
					clog("scheduling editor generation for "
							+ language);
					new GenEditorJob(project, headless, language, owner)
							.doRun();
				} else if (cmd
						.equals(IDiagraphAlphabet.MEGAMODEL_CMD_LANGUAGES)) {
					for (IProject project : selection) {
						String language = project.getName();
						language = language
								.substring(language.lastIndexOf(".") + 1);
						clog("scheduling language for " + language);
						new HandleLanguagesJob(project, headless, language,
								owner).doRun();
					}
				} else if (cmd
						.equals(IDiagraphAlphabet.MEGAMODEL_CMD_WEAVER_)) {
					for (IProject project : selection) {
						String language = project.getName();
						language = language
								.substring(language.lastIndexOf(".") + 1);
						clog("scheduling diagraph to megamodel for " + language);
						new WeaverJob(project, headless, megamodel,
								language, owner).doRun();
					}
				}
			} else
				System.err.println("no selected project");
		}
	}

	private void clog(String mesg) {
		if (LOG && !silent)
			System.out.println(mesg);
	}

	@Override
	public void run(String cmd) {
		this.cmd = cmd;
		run();
	}

	@Override
	public void setSilent(boolean value) {
		this.silent = value;
	}

	@Override
	public boolean isQualified() {
		return true;
	}

	@Override
	public boolean isStub() {
		return false;
	}

	@Override
	public void setMegaModelProject(IProject mproject) {
		this.megamodel = mproject;
	}

	@Override
	public DiagraphNotifiable getControler() {
		return owner;
	}

}
