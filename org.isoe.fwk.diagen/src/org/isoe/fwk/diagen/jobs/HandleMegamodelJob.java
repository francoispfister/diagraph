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
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.parts.DiagramDocumentEditor;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.isoe.diagraph.diagraph.DOwnedEdge;
import org.isoe.diagraph.diagraph.DEdge;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.diagraph.DGraphElement;
import org.isoe.diagraph.diagraph.DNavigationEdge;
import org.isoe.diagraph.diagraph.DNode;
import org.isoe.diagraph.diagraph.DReference;
import org.isoe.diagraph.diagraph.DSimpleEdge;
import org.isoe.diagraph.megamodel.Dsml;
import org.isoe.diagraph.megamodel.EcoreDiagram;
import org.isoe.diagraph.megamodel.Megamodel;
import org.isoe.diagraph.megamodel.MegamodelFactory;
import org.isoe.diagraph.megamodel.Model;
import org.isoe.diagraph.megamodel.Notation;
import org.isoe.diagraph.megamodel.NotationDiagram;
import org.isoe.diagraph.views.ViewConstants;
import org.isoe.diagraph.workbench.api.DiagraphNotifiable;
import org.isoe.extensionpoint.diagen.IHandleMegamodelJob;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.diagen.DiagenPlugin;
import org.isoe.fwk.diagen.model.DiagenParser;
import org.isoe.fwk.jobs.InitialiseDiagram;

/**
 * @author fpfister //adaptation from iaml to diagraph
 *
 */
public abstract class HandleMegamodelJob extends AbstractDiagenJob implements
		IHandleMegamodelJob {

	protected static final boolean LOG = DParams.HandleMegamodelJob_LOG;

	// protected EPackage rootPackage_ = EcorePackage.eINSTANCE;
	protected String message;
	protected String language;
	protected DiagraphNotifiable notifiable;
	//protected Megamodel megamodel_;
	protected String domain;
	protected boolean displayWarning = false;
	protected Diagram diagram;
	protected DiagenParser diagen = new DiagenParser();

	protected abstract void doit(String name, DiagramDocumentEditor editor,
			IProgressMonitor monitor) throws CoreException;



	@Override
	public IStatus errorStatus(String message) {
		return super.errorStatus(message);
	}

	@Override
	public IStatus errorStatus(Throwable e) {
		return new Status(IStatus.ERROR, DiagenPlugin.PLUGIN_ID,
				e.getMessage(), e);
	}

	@Override
	public IStatus errorStatus(String message, Throwable e) {
		return new Status(IStatus.ERROR, DiagenPlugin.PLUGIN_ID, message, e);
	}

	@Override
	protected IStatus work(IProgressMonitor monitor, String id)
			throws CoreException {
		monitor.beginTask(this.getName(), 100);
		monitor.worked(10); // 10%
		if (LOG)
			clog("start " + id + " " + getProject().getName());
		IStatus status = doHandle(new SubProgressMonitor(monitor, 80));
		notifiable.done(getProject());
		if (LOG)
			clog("end " + id + " " + getProject().getName());
		monitor.done();
		return status;
	}

	boolean doit(IProgressMonitor monitor, MultiStatus errorResult_)
			throws CoreException {

		String language = getDomain();
		IFile diagramfile = getModel(language + ".ecorediag");
		IFile modelfile = getModel(language + ".ecore");
		if (LOG)
			clog("HandleMegamodel " + language);
		if (diagramfile != null) {
			monitor.worked(10);
			InitialiseDiagram init = new InitialiseDiagram();
			boolean create = diagramfile == null;
			boolean open = true;
			String diagramExtension = "ecorediag";
			Diagram changed = notifiable.getChangedDiagram();
			try {
				init.initialize(getProject(), changed, modelfile, diagramfile,
						diagramExtension, create, open, new SubProgressMonitor(
								monitor, 50));
				monitor.subTask("Exporting root image");
				IWorkbenchPage page = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage();
				DiagramDocumentEditor editor = (DiagramDocumentEditor) page
						.getActiveEditor();
				diagram = editor.getDiagram();
				domain = modelfile.getName().substring(0,
						modelfile.getName().length() - ".ecore".length());
				doit(domain, editor, new SubProgressMonitor(monitor, 40)); // FP130815x
				boolean result = false;
				if (notifiable.setCurrentView(editor.getDiagram(),
						notifiable.getRegisterdView(language))) // FP140606ab
					result = true; // FP140227
				else
					System.err.println("HMJ: " + "language ko");
				monitor.done();
				return result;
			} catch (Exception e) {
				errorResult_.add(errorStatus("Error '" + "'", e));
				return false;
			}
		}
		return false;
	}

	protected IStatus doHandle(IProgressMonitor monitor) throws CoreException {
		int handled = 0;
		MultiStatus errorResult = new MultiStatus(DiagenPlugin.PLUGIN_ID,
				Status.ERROR, "Could not run job " + message
						+ ": multiple errors occured", null);
		monitor.beginTask("Exporting", 105);
		monitor.subTask("Finding");
		if (LOG)
			clog("doHandle Exporting - Finding " + getDomain() + " (" + handled
					+ ")");
		if (doit(monitor, errorResult))
			handled++;
		if (errorResult.getChildren().length != 0){
			clog(errorResult.getMessage()); //FP140705
			return errorResult;
		}
		if (handled == 0)
			if (LOG)
				clog("nothing done");
		if (handled == 0 && displayWarning) {
			return new Status(Status.WARNING, DiagenPlugin.PLUGIN_ID,
					"Did not " + message);
		}
		return Status.OK_STATUS;
	}

	protected boolean isOpened(String name) {
		return getProject(name) != null;
	}

	@Override
	public IProject getProject(String name) {
		List<IProject> projects = getOpenedProjects();
		try {
			for (IProject iProject : projects) {
				IFile modelfile = getModel(iProject, "ecore");
				if (modelfile != null) {
					if (LOG)
						clog(modelfile.getName());
					if (modelfile.getName().startsWith(name + "."))
						return iProject;
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected List<IProject> getDsmlProjects() {
		List<IProject> dsmlProjects = new ArrayList<IProject>();
		List<IProject> prjs = getOpenedProjects();
		try {
			for (IProject iProject : prjs)
				if (getModel(iProject, "ecore") != null)
					dsmlProjects.add(iProject);

		} catch (CoreException e) {
			e.printStackTrace();
		}
		return dsmlProjects;
	}

	protected List<IProject> getOpenedProjects() {
		List<IProject> prjs = new ArrayList<IProject>();
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot wsroot = workspace.getRoot();
		IProject[] projects = wsroot.getProjects();
		for (IProject project : projects)
			if (project.isOpen())
				prjs.add(project);
		return prjs;
	}

	protected IFile getModel(IProject project, String extension)
			throws CoreException {
		IFolder fo = project.getFolder(org.isoe.fwk.core.DParams.MODEL_FOLDER);
		if (fo != null) {
			URI ri = URI.createURI("platform:/resource"
					+ fo.getFullPath().toPortableString());
			File modelfoldr = new File(CommonPlugin.resolve(ri).toFileString());
			if (modelfoldr.exists())
				if (modelfoldr.isDirectory())
					for (File f : modelfoldr.listFiles())
						if (f.getName().endsWith(extension))
							return fo.getFile(f.getName());
		}
		return null;
	}

	protected File getFile(String name) { // FP140517a
		File csfile = new File(((Path) getProject().getWorkspace().getRoot()
				.getLocation()).toPortableString()
				+ File.separator
				+ getProject().getName()
				+ File.separator
				+ DParams.MODEL_FOLDER + File.separator + name);
		return csfile.exists() ? csfile : null;
	}

	protected List<DNode> getNodes(DGraph graph) { // FP140518
		List<DNode> nodes = new ArrayList<DNode>();
		List<DGraphElement> elements = graph.getElements();
		for (DGraphElement element : elements) {
			if (element instanceof DNode)
				nodes.add((DNode) element);
		}
		return nodes;
	}







	protected List<DEdge> getEdges(DGraph graph) { // FP140518
		List<DEdge> result = new ArrayList<DEdge>();
		for (DGraphElement element : graph.getElements())
			if (element instanceof DEdge)
				result.add((DEdge) element);
		return result;
	}

	protected IFile getModel(String name) throws CoreException {
		IFolder fo = getProject().getFolder(
				org.isoe.fwk.core.DParams.MODEL_FOLDER);
		URI ri = URI.createURI("platform:/resource"
				+ fo.getFullPath().toPortableString());
		File modelfoldr = new File(CommonPlugin.resolve(ri).toFileString());
		if (modelfoldr.exists())
			if (modelfoldr.isDirectory())
				for (File f : modelfoldr.listFiles())
					if (f.getName().equals(name))
						return fo.getFile(f.getName());
		return null;
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	protected EcoreDiagram findEcoreDiagram(Dsml dsml, String path) {
		if (dsml != null) {
			List<org.isoe.diagraph.megamodel.EcoreDiagram> diagrams = dsml
					.getEcoreDiagrams();
			for (org.isoe.diagraph.megamodel.EcoreDiagram diagram : diagrams)
				if (diagram.getDiagramPictureURI().equals(path))
					return diagram;
		}
		return null;
	}

	protected EcoreDiagram addEcoreToolsDiagram_(Dsml dsml, String path) {
		org.isoe.diagraph.megamodel.EcoreDiagram diagram = findEcoreDiagram(
				dsml, path);
		if (diagram == null) {
			diagram = MegamodelFactory.eINSTANCE.createEcoreDiagram();
			dsml.getEcoreDiagrams().add(diagram);
			diagram.setDiagramPictureURI(path);
			if (LOG)
				clog("megamodel - added diagram " + path);

			if (DParams.MegaModelBuilderJOB_LOG)
				   jobclog("added diagram for "+dsml.getName());

		} else if (LOG)
			clog("megamodel - existing diagram " + path);

		return (EcoreDiagram) diagram;
	}

	private Notation findNotation(DGraph diagraph, String view) {
		Dsml dsml = findDsml_(domain);
		if (dsml != null) {
			List<Notation> nots = dsml.getNotations_(); //FP140624
			for (Notation notation : nots)
				if (view.equals(notation.getName()))
					return notation;
		}
		return null;
	}



	public Notation addNotation(DGraph diagraph, String view) {
		Dsml dsml = addDsml(domain,"notation"); // FP140622aa
		Notation n = findNotation(diagraph, view);
		if (n == null) {
			n = MegamodelFactory.eINSTANCE.createNotation();
			dsml.getNotations_().add(n); //FP140624
		}
		n.setNotationBridge(diagraph);
		n.setName(view);
		if (LOG)
			clog("DGraph weaved to megamodel:"
					+ diagraph.getFacade1().getLanguageName() + "." + view
					+ (view.equals("default") ? " root" : ""));
		if (view.equals(ViewConstants.DIAGRAPH_DEFAULT))
			dsml.setRootNotation(n);
		return n;
	}


	@Override
	public List<Dsml> getMegamodelDsmls() {
		//FP140622voiraaa
		List<Dsml> result = notifiable.getMegamodel_(headless).getDsmls();
		if (LOG ||DParams.MegaModelBuilderJOB_LOG) {
			String log = "";
			for (Dsml dsml : result) {
				log += dsml.getName() + "; ";
			}
			if (LOG)
			   clog("ActiveDsmls: " + log);
			if (DParams.MegaModelBuilderJOB_LOG)
			   jobclog("ActiveDsmls: " + log);
		}
		return result;
	}



	private Dsml findDsml_(String domain) {
		for (Dsml dsml : notifiable.getMegamodel_(headless).getDsmls()) {
			String name = dsml.getName(); // packageName
			if (name != null && name.equals(domain))
				return dsml;
		}
		return null;
	}

	public Dsml addDsml(String domain, String sender) { // FP140622aa
		Dsml fdsml = findDsml_(domain);
		if (fdsml == null) {
			if (LOG)
				clog(" added dsml " + domain);
			fdsml = MegamodelFactory.eINSTANCE.createDsml();
			fdsml.setName(domain);
			Megamodel mmodel = notifiable.getMegamodel_(headless);
			mmodel.getDsmls().add(fdsml); // FP140622aazzz
			if (DParams.MegaModelBuilderJOB_LOG)
				   jobclog("(2) added dsml "+domain+" to megamodel "+mmodel.hashCode()
						   +" sender="+sender);
		} else {
			if (LOG)
				clog(" existing dsml " + domain);
			if (!fdsml.getName().equals(domain)) {
				fdsml.setName(domain);
				if (LOG)
					clog(" redefining name " + domain);
			}
		}
		return fdsml;
	}

	private Model findModel_(String domainName, String modelName) {
		Dsml dsml = findDsml_(domainName);
		if (dsml == null)
			if (LOG)
				clog("no dsml for " + domainName);
		if (dsml != null)
			for (Model model : dsml.getModels())
				if (model.getName().equals(modelName))
					return model;
		return null;
	}

	private NotationDiagram findNotationDiagram_(String domainName,
			String modelName, String diagramName) {
		Dsml dsml = findDsml_(domainName);
		if (dsml == null)
			if (LOG)
				clog("no dsml for " + domainName);
		if (dsml != null)
			for (Model model : dsml.getModels())
				if (model.getName().equals(modelName))
					for (NotationDiagram notationDiagram : model
							.getNotationDiagrams())
						if (notationDiagram.getName().equals(diagramName))
							return notationDiagram;
		return null;
	}

	protected NotationDiagram addNotationDiagram(String domainName,
			String modelName, String diagramName, String diagramUri) {
		Dsml dsml = findDsml_(domainName);
		Model model = findModel_(domainName, modelName);
		NotationDiagram diag = findNotationDiagram_(domainName, modelName,
				diagramName);
		if (dsml != null && model != null && diag == null) {
			diag = MegamodelFactory.eINSTANCE.createNotationDiagram();
			model.getNotationDiagrams().add(diag);
			diag.setName(diagramName);
			if (DParams.MegaModelBuilderJOB_LOG)
				   jobclog("add notationdiagram: " + diagramName);

			// model.setBuildIn(true);
			diag.setURI_(diagramUri);
			if (LOG)
				clog("diagram added " + domainName + "." + modelName + "("
						+ diagramName + ")");
			return diag;
		} else if (dsml != null && model != null && diag != null) {
			if (LOG)
				clog("diagram found " + domainName + "." + modelName + "("
						+ diagramName + ")");
			return diag;
		} else {
			if (LOG)
				clog("error adding diagram for " + domainName + "." + modelName
						+ "(" + diagramName + ")");
			return null;
		}
	}

	public Model addModel(String domainName, String modelName, String modelUri) {
		Dsml dsml = findDsml_(domainName);
		Model model = findModel_(domainName, modelName);
		if (dsml != null && model == null) {
			model = MegamodelFactory.eINSTANCE.createModel();
			dsml.getModels().add(model);// FP140622
			model.setName(modelName);
			if (DParams.MegaModelBuilderJOB_LOG)
				jobclog("(2) add model " + modelName + " uri=" + modelUri
						+ " to dsml " + dsml.getName());
			// model.setBuildIn(true);
			model.setURI_(modelUri);
			if (LOG)
				clog("model added " + domainName + "." + modelName);
			return model;
		} else {
			if (LOG)
				clog("model found " + domainName + "." + modelName);
			return model;
		}
	}

	private void jobclog(String mesg) {
		if (DParams.MegaModelBuilderJOB_LOG)
			System.out.println(mesg);
	}

	public void addProtoReferences_(DiagenParser diagen) {
		Dsml dsml = addDsml(domain,"protoreferences"); // FP140622aa
		String leftparent = diagen.getPropParentAndDetails(true);
		if (leftparent != null) {
			if (dsml.getLeftParentDetails().isEmpty()) {

				if (diagen.getPropParent(true) != null)
					dsml.getLeftParentDetails().add(diagen.getPropParent(true));
				dsml.getLeftParentDetails().addAll(
						diagen.getPropParentDetails(true));

			} else {
				if (diagen.getPropParent(true) != null)
					dsml.getLeftParentDetails().set(0,
							diagen.getPropParent(true));
				for (int i = 1; i < dsml.getLeftParentDetails().size(); i++)
					dsml.getLeftParentDetails().add(
							diagen.getPropParentDetails(true).get(i - 1));
			}
		}
		String rightparent = diagen.getPropParentAndDetails(false);
		if (rightparent != null) {
			if (dsml.getRightParentDetails().isEmpty()) {
				if (diagen.getPropParent(false) != null)
					dsml.getRightParentDetails().add(
							diagen.getPropParent(false));
				dsml.getRightParentDetails().addAll(
						diagen.getPropParentDetails(false));
			} else {
				if (diagen.getPropParent(false) != null)
					dsml.getRightParentDetails().set(0,
							diagen.getPropParent(false));
				for (int i = 1; i < dsml.getRightParentDetails().size(); i++)
					dsml.getRightParentDetails().add(
							diagen.getPropParentDetails(false).get(i - 1));
			}
		}

		String contxt = diagen.getPropContextAndDetailz();
		if (contxt != null)
			dsml.getContext().addAll(
					diagen.getDiagenProperties(DiagenParser.KW_CONTEXT));

		String from = diagen.getPropOrigin();
		if (from != null) {
			dsml.setOrigin(from);
		}

		String kn = diagen.getPropKnownAs();
		if (kn != null) {
			if (dsml.getKnownAs().isEmpty())
				dsml.getKnownAs().add(kn);
			else {
				dsml.getKnownAs().clear(); // TODO cardinality
				dsml.getKnownAs().add(kn);
			}
		}

		// String rq = diagen.getProperties(Diagen.KW_CONTEXT);
		if (diagen.getProperties(DiagenParser.KW_CONTEXT) != null
				&& diagen.getDiagenProperties(DiagenParser.KW_CONTEXT) != null)
			dsml.getRequireDetails().addAll(
					diagen.getDiagenProperties(DiagenParser.KW_CONTEXT)); // FP130830req

	}

	@Override
	public DiagraphNotifiable getNotifiable() {
		return notifiable;
	}

	public HandleMegamodelJob(boolean headless, String mesg, IProject project,
			String language, DiagraphNotifiable owner) {
		super(mesg, headless);

		setProject(project);// FP18a
		this.message = mesg;
		this.notifiable = owner;
		this.language = language; // FP140604
		setUser(true);
	}
}
