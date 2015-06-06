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
package org.isoe.fwk.diagen.jobs;

import java.io.File;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.parts.DiagramDocumentEditor;
import org.isoe.diagraph.interpreter.InterpreterPlugin;
import org.isoe.diagraph.workbench.api.DiagraphNotifiable;
import org.isoe.extensionpoint.diagen.IDiagraphAlphabet;
import org.isoe.extensionpoint.diagen.ITextParser;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.diagen.DiagenPlugin;
import org.isoe.fwk.diagen.text.TextParser;

/**
 * @author fpfister
 *
 */
public class GenEditorJob extends HandleMegamodelJob implements
		IDiagraphAlphabet {

	private static final boolean LOG = DParams.DiagraphToTextGrammarJob_LOG;
	// private static final boolean SHOW_INHERITED_EDGES_ = true;

	private ITextParser textParser;

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	public GenEditorJob(IProject project, boolean headless, String language, DiagraphNotifiable owner) {
		super(headless, "Apply Diagraph", project, language, owner);

		this.textParser = new TextParser(this, false);
		System.err.println("not yet implemented (TextToDiagraphJob)");

		// this.textGenerator = new TextGenerator(this,lineNumbered, legended,
		// showStatus);
	}

	@Override
	protected IStatus work(IProgressMonitor monitor, String id) throws CoreException {
		if (LOG)
			clog("work "+id);
		monitor.beginTask(this.getName(), 100);
		IStatus status = null;
		monitor.subTask("genConcreteSyntax");
		status = genConcreteSyntax(monitor);
		monitor.done();
		return status;
	}

	public IStatus errorStatus(String message) {
		return new Status(IStatus.ERROR, DiagenPlugin.PLUGIN_ID, message);
	}

	public IStatus okStatus(String message) {
		return new Status(IStatus.OK, DiagenPlugin.PLUGIN_ID, message);
	}

	protected IStatus genConcreteSyntax(IProgressMonitor monitor)
			throws CoreException { //FP140517 //FP150513
		if (LOG)
			clog("genCs");
		File syntaxfile = getFile(domain + "." + DIAGEN_CST_EXT );
		if (syntaxfile != null) {
			if (LOG)
				clog("cst file exists: "+domain + "." + DIAGEN_CST_EXT);
			String result = textParser.genConcreteSyntax(syntaxfile, monitor);
			if (result == null || result.isEmpty() || result.equals("ok")){
				List<String> roles = textParser.getRoles();
				if (LOG)
					clog("script file generated ");
				return new Status(IStatus.OK, DiagenPlugin.PLUGIN_ID,
						"concrete syntaxe done");
			}
			else{
				System.err.println(result);
				return errorStatus(result);
			}
		} else{
			System.err.println("no syntax file for " + domain);
			InterpreterPlugin.getInstance().addError("no syntax file for " + domain + " in Concrete Syntax Generation");
			return errorStatus("no syntax file for " + domain);
		}
	}

	@Override
	public void setProject(IProject project) {
		super.setProject(project);
		// this.project = project;
		this.domain = project.getName();
		String[] segs = this.domain.split("\\.");
		if (segs.length > 1)
			this.domain = segs[segs.length - 1];
	}

	@Override
	protected void doit(String name, DiagramDocumentEditor editor,
			IProgressMonitor monitor) throws CoreException {
		if (LOG)
			clog("nothing to do");
	}


}
