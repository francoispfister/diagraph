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
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.parts.DiagramDocumentEditor;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.megamodel.Dsml;
import org.isoe.diagraph.workbench.api.DiagraphNotifiable;
import org.isoe.extensionpoint.diagen.IDiagraphAlphabet;
import org.isoe.extensionpoint.diagen.ITextGenerator;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.diagen.text.TextGeneratorV1;
import org.isoe.fwk.diagen.text.TextGeneratorV2;

/**
 * @author fpfister
 *
 */
public class GrammarJob extends HandleMegamodelJob implements IDiagraphAlphabet{

	private static final boolean LOG = DParams.DiagraphToTextGrammarJob_LOG;
	//private static final boolean SHOW_INHERITED_EDGES_ = true;

	private ITextGenerator textGenerator;
	private IProject megamodel_prj;
	private int version;

	@Override
	public IProject getProject(String name) {
		if (name.equals(MGM_KEY))
			return  megamodel_prj;
		else
		    return super.getProject(name);
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	public GrammarJob(IProject project, boolean headless, String language, DiagraphNotifiable owner, String options) {
		super(headless,"Diagraph To Text", project, language, owner);
		boolean lineNumbered = options.indexOf("lineNumbered")>-1;
		boolean legended =  options.indexOf("legended")>-1;
		boolean showStatus =  options.indexOf("showStatus")>-1;
		boolean exportTxt =  options.indexOf("exportTxt")>-1;



		if (options.indexOf("generatorv1")>-1)
			version = 1;
		else
		  if ( options.indexOf("generatorv2")>-1)
			version = 2;

		if(!exportTxt)
		   //this.textGenerator = new TextGeneratorV1(this,lineNumbered, legended, showStatus);
			clog("notImplemented");
	    else {
		       if (version==1){
			         this.textGenerator = new TextGeneratorV1(this,lineNumbered,legended, showStatus);
			         clog("textGenerator = TextGeneratorV1");
		       }
		       else if (version ==2){
			         this.textGenerator = new TextGeneratorV2(this);
	                 clog("textGenerator = TextGeneratorV2");
		       }
	    }
	}


	@Override
	protected IStatus work(IProgressMonitor monitor, String id) throws CoreException {
		if (LOG)
			clog("work "+id);
		monitor.beginTask(this.getName(), 100);
		megamodel_prj = getProject();
		if (!megamodel_prj.exists())
			return errorStatus("megamodel does not exist in the workspace");
		IStatus status = null;
		monitor.subTask("genLanguage");
		status = genGrammar_(monitor,language); //FP140604modif
		monitor.done();
		return status;
	}

	@Override
	protected void doit(String name, DiagramDocumentEditor editor,
			IProgressMonitor monitor) throws CoreException {
		if (LOG)
			clog("nothing to do");
		//if (LOG)
		//	clog("DiagraphToTextJob doit "+message + domain);
	}

	private IStatus genGrammar_(IProgressMonitor monitor,String langName) throws CoreException {

		//textGenerator.connectAll();
		//textGenerator.getAllModels();
		if (LOG)
			clog("DiagraphToTextJob genGrammar "+langName);
		textGenerator.getKeywords();
		textGenerator.getSeparators();
		textGenerator.getGlossary();
		if (version==1)
		  throw new RuntimeException("should not happen in genGrammar_");// textGenerator.genGrammarDsml_old(monitor,langName); //FP140621
		else
		  return textGenerator.genGrammarDGraph(monitor,langName); //FP140622voiraaaccc  //FP140621
	}
/*
	private IStatus exportLanguageDsmls_old_nu(SubProgressMonitor monitor,
			List<Dsml> dsmls, String langName,int n, File destFile) {
		return textGenerator.exportDsmls_old(monitor, dsmls,langName, n, destFile); //FP140621
	}

	private IStatus exportLanguageDGraphs_nu(SubProgressMonitor monitor,
			List<DGraph> dsmls, String langName,int n, File destFile) {
		return textGenerator.exportLanguage(monitor, dsmls,langName, n, destFile); //FP140621
	}
*/

}
