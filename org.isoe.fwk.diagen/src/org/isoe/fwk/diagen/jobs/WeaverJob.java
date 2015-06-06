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
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.parts.DiagramDocumentEditor;
import org.isoe.diagraph.diagraph.DNestedEdge;
import org.isoe.diagraph.diagraph.DOwnedEdge;
import org.isoe.diagraph.diagraph.DEdge;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.diagraph.DGraphElement;
import org.isoe.diagraph.diagraph.DNode;
import org.isoe.diagraph.diagraph.DSimpleEdge;
import org.isoe.diagraph.diagraph.DiagraphPackage;
import org.isoe.diagraph.factory.DGraphFactory;
import org.isoe.diagraph.megamodel.Notation;
import org.isoe.diagraph.workbench.api.DiagraphNotifiable;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.diagen.ExportMegamodelException;
import org.isoe.fwk.diagen.model.DiagenParser;

/**
 * @author fpfister
 *
 */
public class WeaverJob extends HandleMegamodelJob {
	static final boolean LOG = DParams.DiagraphWeaverJob_LOG;
	static final boolean WRITE_CSV = DParams.WEAVER_WRITE_CSV;
	private IProject megamodel;
	private List<String> exportedDomain;
	private List<String> exportedView_;
	private List<String> views;
	// private EPackage metamodel;
	private ResourceSet resourceset;

	public WeaverJob(IProject project, boolean headless,
			IProject megamodel, String language, DiagraphNotifiable owner) {
		super(headless, "Weaving megamodel", project, language, owner);
		this.megamodel = megamodel;
		// modelingUniverse.getMegamodels().get(0).getModelingLanguages().clear();
		// notifiable.saveMegamodel();
		exportedDomain = new ArrayList<String>();
		exportedView_ = new ArrayList<String>();
		String lang = project.getName();
		lang = lang.substring(lang.lastIndexOf(".") + 1);
		views = owner.getViewsForLanguage(lang); // FP140603
	}

	protected IStatus export(IProgressMonitor monitor) throws CoreException {

		if (!megamodel.exists())
			return errorStatus("megamodel does not exist in the workspace");
		IFolder folder = megamodel.getFolder("repository");
		String pp = folder.getFullPath().toPortableString();
		if (LOG)
			clog("weaverjob megamodel.folder=" + pp);
		// URI ruri = URI.createPlatformResourceURI(pp, false);
		// File modelfoldr = new
		// File(CommonPlugin.resolve(ruri).toFileString());
		IStatus status = weaveDomain(new SubProgressMonitor(monitor, 90),
				domain); // 90%
		return status;
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	protected IPath generateDestination(String filename, String fileExtension) {
		if (!fileExtension.startsWith("."))
			throw new IllegalArgumentException(
					"File extension needs to start with '.'");
		return getProject().getFile("model/" + filename + fileExtension)
				.getLocation();
	}

	@Override
	protected void doit(String domain, DiagramDocumentEditor editor,
			IProgressMonitor monitor) throws CoreException {
		IProgressMonitor smonitor = new SubProgressMonitor(monitor, 2);
		smonitor.beginTask(message + domain, 2);
		if (LOG)
			clog("GrammaJob doit "+message + domain);
		if (diagram != null)
			diagen.parseProto((EPackage) diagram.getElement());
		export(monitor);
		smonitor.worked(1);
		smonitor.done();
	}

	/*
	private List<String> getViews_new() {
		String[] = notifiable.getViews_();
		return notifiable.getCurrentViews();
		//return views;
	}*/

	private List<String> getViews_old_nu() { // FP140603zz
		IPath modelc = getProject().getLocation().append("model");
		String ms = modelc.toOSString();
		File modelfolder = new File(modelc.toOSString());
		File[] children = modelfolder.listFiles();
		List<String> result = new ArrayList<String>();
		for (File file : children) {
			if (file.getName().endsWith("_root.diagraph")) {
				String fn = file.getName();
				int i = fn.length() - ".diagraph".length();
				String res = fn.substring(0, i);
				int p = res.indexOf("_") + 1;
				res = res.substring(p);
				int q = res.indexOf("_root");
				res = res.substring(0, q);
				result.add(res);
				if (DParams.TextGen6_LOG)
					clog6("view=" + res);
			}
		}
		return result;
	}

	private void clog6(String mesg) {
		if (DParams.TextGen6_LOG)
			System.out.println(mesg);
	}

	/*
	 * private String listAsString_nu(List<String> l) { String result = ""; if
	 * (l == null) return "null"; for (int i = 1; i < l.size(); i++) { result +=
	 * l.get(i); if (i < l.size() - 1) result += "\r\n"; } return result; }
	 */

	private void logWeavings() {
		diagen.logWeavings();
	}



	protected IStatus weaveDomain(SubProgressMonitor monitor, String domain) {
		monitor.beginTask("Weaving " + " domain " + domain, 3);
		if (diagram != null && LOG){
			clog("weaving "+domain);
			logWeavings();
		}
		IPath csvDestination_ = null;
		File destFile_ = null;
		if (WRITE_CSV) {
			csvDestination_ = megamodel.getLocation().append("src")
					.append("mgm2_" + domain).addFileExtension("csv");
			destFile_ = new File(csvDestination_.toOSString());
		}
		FileWriter fw_ = null;
		if (monitor.isCanceled())
			return Status.CANCEL_STATUS;
		try {
			if (WRITE_CSV)
				fw_ = new FileWriter(destFile_);
			if (!exportedDomain.contains(domain)) {
				exportedDomain.add(domain);
				monitor.subTask("Exporting " + domain);// .substring(model.lastIndexOf("/")));
				if (WRITE_CSV)
					fw_.write("domain;" + domain + ";" + "\r\n");
				if (diagram != null) {
					if (WRITE_CSV)
						writeWeavings(fw_);
					addProtoReferences_(diagen);
				}
				for (String view : notifiable.getViews()) { //FP140621
					IPath diagraphmodel_nu = null;/*
							getProject().getLocation()
							.append("model")
							.append(domain + "_" + view + "_root")
							.addFileExtension("diagraph");*/
					if (!exportedView_.contains(domain+"."+view)) {
						exportedView_.add(domain+"."+view);
						weaveView(fw_, domain,view, diagraphmodel_nu);
					}
				}
			}
			if (WRITE_CSV)
				fw_.close();
		} catch (IOException e) {
			e.printStackTrace();
			return errorStatus(e);
		} catch (ExportMegamodelException e) {
			e.printStackTrace();
			return errorStatus(e);
		}
		return Status.OK_STATUS;
	}




	protected IStatus weaveDomain_old_nu(SubProgressMonitor monitor, String domain) {
		monitor.beginTask("Weaving " + " domain " + domain, 3);
		if (diagram != null && LOG){
			clog("weaving "+domain);
			logWeavings();
		}
		IPath csvDestination_ = null;
		File destFile_ = null;
		if (WRITE_CSV) {
			csvDestination_ = megamodel.getLocation().append("src")
					.append("mgm2_" + domain).addFileExtension("csv");
			destFile_ = new File(csvDestination_.toOSString());
		}
		FileWriter fw_ = null;
		if (monitor.isCanceled())
			return Status.CANCEL_STATUS;
		try {
			if (WRITE_CSV)
				fw_ = new FileWriter(destFile_);
			if (!exportedDomain.contains(domain)) {
				exportedDomain.add(domain);
				monitor.subTask("Exporting " + domain);// .substring(model.lastIndexOf("/")));
				if (WRITE_CSV)
					fw_.write("domain;" + domain + ";" + "\r\n");
				if (diagram != null) {
					if (WRITE_CSV)
						writeWeavings(fw_);
					addProtoReferences_(diagen);
				}
				for (String view : getViews_old_nu()) {
					IPath diagraphmodel_ = getProject().getLocation()
							.append("model")
							.append(domain + "_" + view + "_root")
							.addFileExtension("diagraph");
					if (!exportedView_.contains(diagraphmodel_.toOSString())) {
						exportedView_.add(diagraphmodel_.toOSString());
						weaveView(fw_, domain,view, diagraphmodel_);
					}
				}
			}
			if (WRITE_CSV)
				fw_.close();
		} catch (IOException e) {
			e.printStackTrace();
			return errorStatus(e);
		} catch (ExportMegamodelException e) {
			e.printStackTrace();
			return errorStatus(e);
		}
		return Status.OK_STATUS;
	}

	private void writeWeavings(FileWriter fw) throws IOException {
		StringBuffer buf = new StringBuffer();
		buf.append("left-parent;"
				+ (diagen.getPropParentAndDetails(true) == null ? "-" : diagen
						.getPropParentAndDetails(true)) + "\r\n");
		buf.append("right-parent;"
				+ (diagen.getPropParentAndDetails(false) == null ? "-" : diagen
						.getPropParentAndDetails(false)) + "\r\n");
		buf.append("context;"
				+ (diagen.getPropContextAndDetailz() == null ? "-" : diagen
						.getPropContextAndDetailz()) + "\r\n");

		if (diagen.getDiagenProperties(DiagenParser.KW_REQUIRES_) != null) {
			for (String d : diagen
					.getDiagenProperties(DiagenParser.KW_REQUIRES_))
				buf.append("required;" + d + "\r\n");
		} else
			buf.append("required;" + "-" + "\r\n");

		if (diagen.getDiagenProperties(DiagenParser.KW_RELATED) != null) {
			for (String d : diagen.getDiagenProperties(DiagenParser.KW_RELATED))
				buf.append("related;" + d + "\r\n");
		} else
			buf.append("related;" + "-" + "\r\n");

		buf.append("origin;"
				+ (diagen.getPropOrigin() == null ? "-" : diagen
						.getPropOrigin()) + "\r\n");
		buf.append("knownas;"
				+ (diagen.getPropKnownAs() == null ? "-" : diagen
						.getPropKnownAs()) + "\r\n");
		// if (WRITE_CSV)
		fw.write(buf.toString());
		// if (LOG)
		// clog("buf.toString()");
	}

	public List<DEdge> getEdges(DNode node) {
		List<DEdge> result = new ArrayList<DEdge>();
		for (DGraphElement element : node.getGraph().getElements()){
			if (element instanceof DNestedEdge){
				if (((DNestedEdge) element).getSource().getNode() == node)
					result.add((DEdge) element);
			} else if(element instanceof DSimpleEdge){
				 if (((DSimpleEdge) element).getSource() == node)
					result.add((DEdge) element); //FP150423b
			}
		}
		return result;
	}

	public void weaveView(FileWriter fw, String domain,String view, IPath model_nu )
			throws ExportMegamodelException, IOException {
		StringBuffer buffer = new StringBuffer();
		clog("weaveView "+domain + " - " + view);
		//URI fileURI_ = URI.createFileURI(model_nu.toOSString());
		//clog(fileURI_.toString()); //FP140621
		DGraph diagraph_ = this.notifiable.getDGraph(domain,view);

		//DGraph diagraph_ = load(fileURI);
		//diagraph_.setFacade1(new DGraphFactory(diagraph_));
		if (diagraph_ != null) {
			List<DNode> nods = getNodes(diagraph_);//FP140603zz
			for (DNode dNode : nods) {
				clog(dNode.getName());
				buffer.append("node" + ";" + dNode.getName() + "\r\n");
				List<DEdge> edges = getEdges(dNode);
				for (DEdge dEdge : edges)
					buffer.append("edge" + ";" + dEdge.getName() + "\r\n");
			}
			Notation notation = addNotation(diagraph_, view);
			if (WRITE_CSV)
				fw.write(buffer.toString());
		} else
			System.err.println("no view " + view);
	}

	private DGraph load_nu(URI uri) {
		Resource resource = null;
		try {
			resource = getResourceSet_nu().getResource(uri, true);
		} catch (Exception e) {
			clog("error while loading " + uri.toString() + " " + e.toString());
		}

		if (resource != null && !resource.getContents().isEmpty()) {
			return (DGraph) resource.getContents().get(0);
		} else {
			try {
				if (resource != null) {
					URI ruri = CommonPlugin.resolve(uri);
					if (LOG)
						clog(ruri.toFileString() + " is not a model");
					return null;
				}
			} catch (Exception e) {
				clog(uri.toString() + " :nothing found");
				return null;
			}
			if (LOG)
				clog(uri.toString() + " :nothing found");
			return null;
		}
	}

	ResourceSet getResourceSet_nu() {
		if (resourceset == null) {
			resourceset = new ResourceSetImpl();
			resourceset
					.getResourceFactoryRegistry()
					.getExtensionToFactoryMap()
					.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
							new XMIResourceFactoryImpl());
			resourceset.getResourceFactoryRegistry().getExtensionToFactoryMap()
					.put("ecore", new EcoreResourceFactoryImpl());
			resourceset.getPackageRegistry().put(DiagraphPackage.eNS_URI,
					DiagraphPackage.eINSTANCE);
		}
		return resourceset;
	}

	/*---------------  from graphviz feature -------------------------------------*/

}
