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
/**
 *
 */
package org.isoe.fwk.diagen.jobs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.parts.DiagramDocumentEditor;
import org.isoe.diagraph.megamodel.Dsml;
import org.isoe.diagraph.megamodel.Model;
import org.isoe.diagraph.megamodel.NotationDiagram;
import org.isoe.diagraph.workbench.api.DiagraphNotifiable;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.diagen.ExportMegamodelException;

/**
 * @author fpfister
 *
 */
public class ModelWeaverJob extends HandleMegamodelJob {
	private static final boolean LOG = DParams.Megamodel_LOG;
	private static final boolean WRITE_CSV = false;
	// private IProject project;
	private IProject megamodel;
	private Map<String, String> domainFiles_ = new HashMap<String, String>();
	private Map<String, String> diagramFiles = new HashMap<String, String>();
	private List<String> exported;
	//private List<String> domains_;
	private List<String> diagrams;


	public ModelWeaverJob(IProject project, boolean headless,String language,DiagraphNotifiable owner) {
		super(headless, "Export megamodel", project, language,owner);
		// this.project = project;
		// setUser(true);
	}

	private List<String> getDomainFiles(File modelfoldr, List<String> domains) {
		List<String> models = new ArrayList<String>();
		domainFiles_ = new HashMap<String, String>();
		for (String domain : domains) {
			for (File file : modelfoldr.listFiles()) {
				String domf = file.getName().substring(
						file.getName().indexOf(".") + 1);
				if (domain.equals(domf)) {
					models.add(file.getName());
					if (domainFiles_.get(file.getName()) == null)
						domainFiles_.put(file.getName(), domain);
				}
			}
		}
		return models;
	}

	private List<String> getDomains__(File modelfoldr) {
		diagramFiles = new HashMap<String, String>();
		List<String> domains = new ArrayList<String>();
		File[] listf = modelfoldr.listFiles();
		for (File file : listf) {
			// System.out.println(file.getName());
			if (file.getName().endsWith("_default_root_diagram")) {
				String f = file.getName().substring(
						0,
						file.getName().length()
								- "_default_root_diagram".length());
				String[] mf = f.split("\\.");
				if (!domains.contains(mf[1]))
					domains.add(mf[1]);
			}
		}
		return domains;
	}

	private List<String> getDiagrams(File modelfoldr) {
		diagramFiles = new HashMap<String, String>();
		List<String> diagrams = new ArrayList<String>();
		File[] listf = modelfoldr.listFiles();
		for (File file : listf) {
			if (file.getName().endsWith("_default_root_diagram")) {
				String filepath_ = file.getName();
				String f = filepath_.substring(0, filepath_.length()
						- "_default_root_diagram".length());
				if (diagramFiles.get(filepath_) == null)
					diagramFiles.put(filepath_, f);
				if (!diagrams.contains(filepath_))
					diagrams.add(filepath_);
			}
		}
		return diagrams;
	}

	@Override
	protected IStatus work(IProgressMonitor monitor, String id) throws CoreException {
		if (LOG)
			clog("work "+id);
		monitor.beginTask(this.getName(), 100);
		exported = new ArrayList<String>();
		megamodel = getProject();
		if (!megamodel.exists())
			return errorStatus("megamodel does not exist in the workspace");
		IFolder folder = megamodel.getFolder("repository");
		String pp = folder.getFullPath().toPortableString();
		//if (LOG) clog(pp);
		URI ruri = URI.createPlatformResourceURI(pp, false);
		File modelfoldr_ = new File(CommonPlugin.resolve(ruri).toFileString());
		//domains_ = getDomains__(modelfoldr_);
		List<String> domains_ = notifiable.getDiagraphProjectNames();

		diagrams = getDiagrams(modelfoldr_); //default.avkclp_default_root_diagram, default.dvynhs_default_root_diagram

		List<String> models = getDomainFiles(modelfoldr_, domains_);


		if (LOG){
			String sm="";
			for (String model : models) {
				sm+=model+"; ";
			}
			clog("models to export "+sm);

		}

		monitor.subTask("Loading list of models to export");
		Set<String> listModels = new HashSet<String>();
		for (String model : models)
			listModels.add(model);
		if (listModels.isEmpty() && displayWarning)
			return errorStatus("models list was empty.");
		IStatus status = exportDomains(new SubProgressMonitor(monitor, 90),domains_); // 90%
		monitor.done();
		return status;
	}

	@Override
	protected void doit(String name, DiagramDocumentEditor editor,
			IProgressMonitor monitor) throws CoreException {
		// nothing
	}

	protected IFile getModel(String extension) throws CoreException {
		IFolder fo = getProject().getFolder(
				org.isoe.fwk.core.DParams.MODEL_FOLDER);
		URI ri = URI.createURI("platform:/resource/"
				+ fo.getFullPath().toPortableString());
		File modelfolr = new File(CommonPlugin.resolve(ri).toFileString());
		if (modelfolr.exists()) {
			if (modelfolr.isDirectory()) {
				for (File f : modelfolr.listFiles()) {
					if (f.getName().endsWith(extension)) {
						IFile dfile = fo.getFile(f.getName());
						return dfile;
					}
				}
			}
		}
		return null;
	}



	protected IStatus exportDomains(SubProgressMonitor monitor, List<String> domains) { //FP140621zzz
		monitor.beginTask("Exporting " + domains.size() + " domains",
				domains.size() * 3);
		if (LOG)
			clog("Exporting " + domains.size());


		IPath csvDestination = megamodel.getLocation().append("src")
				.append("mgm").addFileExtension("csv");
		File destFile_ = null;
		if (WRITE_CSV)
			destFile_ = new File(csvDestination.toOSString());
		FileWriter fw = null;
		try {
			if (WRITE_CSV)
				fw = new FileWriter(destFile_);
			for (String domain : domains) {
				if (!exported.contains(domain) && isOpened(domain)) {
					if (monitor.isCanceled())
						return Status.CANCEL_STATUS;
					exported.add(domain);
					monitor.subTask("Exporting " + domain);// .substring(model.lastIndexOf("/")));
					if (WRITE_CSV)
						fw.write("domain;" + domain + ";" + "\r\n");
					exportModel(fw, domain);
					exportDiagrams(domain);
				}
			}
			if (WRITE_CSV)
				fw.close();
		} catch (IOException e) {
			e.printStackTrace();
			return errorStatus(e);
		} catch (ExportMegamodelException e) {
			e.printStackTrace();
			return errorStatus(e);
		}
		return Status.OK_STATUS;
	}

	public void exportModel(FileWriter fw, String domain)
			throws ExportMegamodelException {
		Dsml dsml = addDsml(domain,"exportmodel");   //FP140622aa
		try {
			Set<String> files = domainFiles_.keySet();
			for (String fil : files) {
				String dom = domainFiles_.get(fil);
				if (dom != null && dom.equals(domain)) {
					clog(fil);
					if (WRITE_CSV)
						fw.write(dom + ";" + fil + "\r\n");
					Model added = addModel(domain,
							fil.substring(0, fil.indexOf(".")), fil);

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);

	}

	public void exportDiagrams(String domain) throws ExportMegamodelException { // FP130903
		Dsml dsml = addDsml(domain,"exportdiagram");   //FP140622aa
		Set<String> files = diagramFiles.keySet();
		for (String fil : files) {
			String[] mf = diagramFiles.get(fil).split("\\.");
			String dom = mf[1];
			String mod = mf[0];
			if (dom != null && dom.equals(domain)) {
				clog(fil);
				NotationDiagram added = addNotationDiagram(domain, mod,
						fil.substring(0, fil.indexOf(".")), fil);
			}
		}
	}

}
