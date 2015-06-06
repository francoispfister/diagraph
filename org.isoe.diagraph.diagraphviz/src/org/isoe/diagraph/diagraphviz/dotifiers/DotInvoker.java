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
package org.isoe.diagraph.diagraphviz.dotifiers;

import org.eclipse.core.internal.resources.File;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.isoe.diagraph.diagraph.DiagraphPackage;
import org.isoe.diagraph.diagraphviz.dotifiers.styles.EcoreDotStyle;
import org.isoe.diagraph.diagraphviz.dotifiers.styles.M2DotDefaultStyle;
import org.isoe.diagraph.diagraphviz.dotutils.GraphVizConverter;
import org.isoe.extensionpoint.graphviz.IDotifier;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.platform.resolver.Activator;
import org.isoe.extensionpoint.xmi.IMetamodelRetriever;
//import org.isoe.fwk.xmi.domparser.MetamodelRetriever;

/**
 *
 * @author pfister
 *
 */
import org.isoe.diagraph.controler.IDiagraphControler;  //FP140707refactored
public class DotInvoker implements IDotifier {
	// org.isoe.diagraph.diagraphviz.dotifiers.DotInvoker


	private static final boolean LOG_CONSOLE = false;
	private static final boolean LOG = false;

	private EPackage diagraphPackage;
	private EPackage currentPackage;

	private IMetamodelRetriever metamodelRetriever;

	private IDiagraphControler controler;
	private URI modelURI;

	public EPackage getCurrentPackage() {
		return currentPackage;
	}

	private EPackage retrieveMetamodel(URI uri) {
		return metamodelRetriever.findRegisteredMetamodel(uri, new NullProgressMonitor());
	}

	@Override
	public void log(String mesg) {
		if (LOG)
			clog("***********     " + mesg);
	}

	private DotInvoker() {
		super();
		//metamodelRetriever = handler.getMetamodelRetriever();
	}



	@Override
	public void setMetamodelRetriever(IMetamodelRetriever metamodelRetriever) {
		this.metamodelRetriever = metamodelRetriever;
	}


	private static DotInvoker instance;

	public static DotInvoker getInstance() {
		if (instance == null)
			instance = new DotInvoker();
		return instance;
	}

	void checkUri(ResourceSet resourceSet, URI uri) {
	}

	/*
	void checkUri_old(ResourceSet resourceSet, URI uri) {
		Resource result = null;
		try {
			result = resourceSet.getResource(uri, true);
			result.load(null);
			EcoreUtil.resolveAll(resourceSet);
		} catch (Exception e) {
			throw new RuntimeException("error in checkuri");
			// System.out.println("error in checkuri");
		}
		if (result != null)
			System.out.println(result.getContents().get(0));
		else
			System.out.println("resource error for " + uri);
	}*/

	String getDiagraphM2PhysicalPath() {
		URI diagraphuri = URI.createPlatformPluginURI(
				"org.isoe.diagraph.diagraph", false);
		String duri = CommonPlugin.resolve(diagraphuri).toString();
		duri += "model" + "/" + "diagraph.ecore";
		if (duri.startsWith("file:/")) {
			duri = duri.substring("file:/".length());
			String fs = java.io.File.separator;
			if (fs.equals("\\"))
				fs = "\\\\";
			duri = duri.replaceAll("/", fs);
		}
		return duri;
	}

	private void clog(String msg) {
		if (LOG_CONSOLE && controler != null)
			controler.log(null, msg + "\r\n");

	}

	public void runDiagraphM2(ResourceSet rs, String typ, String view) {
		String src = metamodelRetriever.getCurrentFile().getRawLocation()
				.toFile().getAbsolutePath();
		if (LOG)
			clog("runDiagraphM2 - source :" + src);
		String target = createTargetPath(src, typ);
		boolean drawAttributes = false;
		boolean drawReferencesToEnum = false;
		boolean drawDerived = false;
		boolean drawOperations = false;
		String ws = Activator.getWorkspacePath();
		EcoreToDot edot = new EcoreToDot(rs, getDiagraphM2PhysicalPath(),
				target, DiagraphPackage.eINSTANCE, drawAttributes,
				drawReferencesToEnum, drawDerived, drawOperations,
				EcoreDotStyle.getInstance(), this);
		try {
			ResourceSet r = edot.handleModel();// null, null, null, null
			edot.createDotGraph();
			IProgressMonitor monitor = new NullProgressMonitor();
			metamodelRetriever.getCurrentFile().getParent()
					.refreshLocal(IResource.DEPTH_INFINITE, monitor); // FP130401xx
		} catch (CoreException e) {
		   e.printStackTrace();
			String err=e.getMessage();
			if (err==null ||err.isEmpty())
				err=e.toString();
			controler.log("dotify error ", err+" is Graphviz installed and accessible ?");
			if (LOG)
				clog(" ModelToDot error (2): " + err);
		}
	}

	public IDiagraphControler getUI() {
		return controler;
	}

	private String createTargetPath(String src, String typ) {
		java.io.File par = metamodelRetriever.getCurrentFile().getRawLocation()
				.toFile().getParentFile();
		String graphvizFolder = par.getAbsolutePath() + java.io.File.separator
				+ GRAPHVIZ_FOLDER;
		java.io.File graphvizfoldr = new java.io.File(graphvizFolder);
		if (!graphvizfoldr.exists())
			if (!graphvizfoldr.mkdir()) // FP130401
				throw new RuntimeException("unable to create folder "
						+ graphvizFolder);
		if (LOG)
			clog("runM2 - source :" + src);
		String target = graphvizFolder
				+ src.substring(src.lastIndexOf(java.io.File.separator)) + "."
				+ typ + ".dot";
		if (LOG)
			clog("runM2 - target :" + target);
		return target;
	}

	public void runDomainM2(ResourceSet rs, String typ, String view) {
		boolean drawAttributes = false;
		boolean drawReferencesToEnum = false;
		boolean drawDerived = false;
		boolean drawOperations = false;
		String src = metamodelRetriever.getCurrentFile().getRawLocation()
				.toFile().getAbsolutePath();
		String target = createTargetPath(src, typ);// FP130401xx
		URI muri = URI.createPlatformPluginURI(
				metamodelRetriever.getMetamodelBase(), true);
		if (LOG)
			clog("runM2 - model uri :" + muri);
		String furi = CommonPlugin.resolve(muri).toFileString();

		furi += "model" + java.io.File.separator
				+ metamodelRetriever.getCurrentPackage().getName() + ".ecore"; // FP130401
																				// currentPackage.getName()
																				// vs
																				// nsprefix
		EcoreToDot edot = new EcoreToDot(rs, furi, target,
				DiagraphPackage.eINSTANCE, drawAttributes,
				drawReferencesToEnum, drawDerived, drawOperations,
				M2DotDefaultStyle.getInstance(), this);
		try {

			ResourceSet r = edot.handleModel();// null, null, null, null
			edot.createDotGraph();
			IProgressMonitor monitor = new NullProgressMonitor();
			metamodelRetriever.getCurrentFile().getParent()
					.refreshLocal(IResource.DEPTH_INFINITE, monitor); // FP130401xx
		} catch (CoreException e) {
		   e.printStackTrace();
			clog(" ModelToDot error (2): " + e.getMessage() + " ????");
			// showMessage_(" ModelToDot error");
		}
	}

	public void runM0(ResourceSet rs, String typ) {
		boolean hideProxiesToEcore = false;
		String source = metamodelRetriever.getCurrentFile().getRawLocation()
				.toFile().getAbsolutePath();
		clog("runM1 - source :" + source);
		// String target = source + "." + typ + ".dot";
		String target = createTargetPath(source, typ);// FP130401xx
		final URI uribase = URI.createPlatformPluginURI(
				metamodelRetriever.getMetamodelBase(), true);
		try {
			EObjectGraph m0ToGraph = new ERawObjectGraph(rs, source, target,
					target, this);
			ResourceSet r = m0ToGraph.handleModels(rs, uribase,
					metamodelRetriever.getDomain(),
					metamodelRetriever.getNsPrefix(),
					metamodelRetriever.getNsURI(), null, null, false);
			m0ToGraph.createDotGraph();
			IProgressMonitor monitor = new NullProgressMonitor();
			metamodelRetriever.getCurrentFile().getParent()
					.refreshLocal(IResource.DEPTH_INFINITE, monitor); // FP130401xx
		} catch (CoreException e) {
		   e.printStackTrace();
			clog(" ModelToDot error (4): " + e.getMessage() + " ????");
		}
	}

	/*
	 * //FP130402yy intéger encore blazodiagraph interfacer avec gumm peupler
	 * megamodel.megamodel changer folder repo vers prj java individuels faire
	 * une version de base + une version diagraph ?? contribution ?? factoriser
	 * les actions à part des vues
	 */

	public void runM1(ResourceSet rs, String typ, String view, boolean rawgraph) {
		String source = metamodelRetriever.getCurrentFile().getRawLocation()
				.toFile().getAbsolutePath();
		clog("runM1 - source :" + source);
		// String target = source + "." + typ + ".dot";
		String target = createTargetPath(source, typ);// FP130401xx
		boolean[] options = new boolean[] { true, true, false, false };
		final GraphVizConverter modelToGraph = new M1ToGraph(rs, source,
				target, this);
		final DotVisitable dotGenerator = new DotM1Generator(target, rawgraph);
		final URI uribase = URI.createPlatformPluginURI(
				metamodelRetriever.getMetamodelBase(), true);
		try {
			ResourceSet r = modelToGraph.handleModels(rs, uribase,
					metamodelRetriever.getDomain(),
					metamodelRetriever.getNsPrefix(),
					metamodelRetriever.getNsURI(), view, dotGenerator, false);
			dotGenerator.createDotGraph();
			IProgressMonitor monitor = new NullProgressMonitor();
			metamodelRetriever.getCurrentFile().getParent()
					.refreshLocal(IResource.DEPTH_INFINITE, monitor); // FP130401xx
		} catch (CoreException e) {
		   e.printStackTrace();
			clog(" ModelToDot error (2): " + e.getMessage() + " ????");
		}
	}





	public void dorun(String id) {
		boolean rawgraph = false;
		String typ = "";
		if ((DParams.DOTIFY_ACTION_PREFIX+"raw").equals(id)) {
			typ = DOT_OPT_RAW;
			rawgraph = true;
		} else if ((DParams.DOTIFY_ACTION_PREFIX+DOT_OPT_M1_COMPOUND).equals(id)) {
			typ = DOT_OPT_COMPOUND;
			rawgraph = false;
		} else if ((DParams.DOTIFY_ACTION_PREFIX+DOT_OPT_M2).equals(id)) {
			typ = DOT_OPT_M2;
		} else if ((DParams.DOTIFY_ACTION_PREFIX+DOT_OPT_DM2).equals(id)) {
			typ = DOT_OPT_DM2;
		} else if ((DParams.DOTIFY_ACTION_PREFIX+DOT_OPT_M0).equals(id)) {
			typ = DOT_OPT_M0;
		} else if ((DParams.DOTIFY_ACTION_PREFIX+DOT_OPT_ALL).equals(id)) {
			typ = DOT_OPT_ALL;
		} else if ("org.isoe.other...".equals(id)) {
			return;
		} else
			return;

		String s = null;
		String view = "default";
		if (metamodelRetriever.getCurrentFile() != null)
		{
			// metamodelRetriever.getCurrentFile().getFullPath().toOSString();
			ResourceSet rs = new ResourceSetImpl();
			if (typ.equals(DOT_OPT_RAW) || typ.equals(DOT_OPT_COMPOUND))
				runM1(rs, typ, view, rawgraph);
			else if (typ.equals(DOT_OPT_M0))
				runM0(rs, typ);
			else if (typ.equals(DOT_OPT_M2))
				runDomainM2(rs, typ, view);
			else if (typ.equals(DOT_OPT_DM2))
				runDiagraphM2(rs, typ, view);
			else if (typ.equals(DOT_OPT_ALL)) {
				runAll(view);
			}
			clog(" ModelToDot executed");
		}

		// MessageDialog.openInformation(shell,
		// "View2","New Action was executed with " + s);
	}






	private void runAll(String view) {
		ResourceSet rs = new ResourceSetImpl();
		try {runDiagraphM2(rs, DOT_OPT_DM2, view);} catch (Exception e) {System.out.println("failed runDiagraphM2");}
		try {runDomainM2(rs, DOT_OPT_M2, view);} catch (Exception e) {System.out.println("failed runDomainM2");}
		try {runM0(rs, DOT_OPT_M0);} catch (Exception e) {System.out.println("failed runM0");}
		try {runM1(rs, DOT_OPT_COMPOUND, view, false);} catch (Exception e) {System.out.println("failed runM1 a");}
		try {runM1(rs, DOT_OPT_RAW, view, true);} catch (Exception e) {System.out.println("failed runM1 b");}
		/*
		runDiagraphM2(rs, DOT_OPT_DM2, view);
		runDomainM2(rs, DOT_OPT_M2, view);
		runM0(rs, DOT_OPT_M0);
		runM1(rs, DOT_OPT_COMPOUND, view, false);
		runM1(rs, DOT_OPT_RAW, view, true);*/
	}

	private EObject getRoot(EObject eObject) {
		EObject result = eObject.eContainer();
		if (result == null)
			return eObject;
		else
			return getRoot(result);
	}

	public void findRegisteredMetamodel(URI uri) {
		currentPackage = metamodelRetriever.findRegisteredMetamodel(uri, new NullProgressMonitor());
	}


	public void setPackage(EPackage packag) {
		currentPackage = packag;
	}


	public void findRegisteredMetamodel(IFile fi) {
		currentPackage = metamodelRetriever.findRegisteredMetamodel(fi);
	}


	public void setControler(IDiagraphControler handler) {
		this.controler = handler;
	}
/*
	public void setRegisteredMetamodel(URI mmUri) {
		currentPackage = metamodelRetriever.findRegisteredMetamodel(mmUri);
	}*/

	public void setModel(URI mUri) {
		this.modelURI=mUri;

	}

	public void setCurrentFile(IFile cfile) {
	   metamodelRetriever.setCurrentFile(cfile);
	}


}
