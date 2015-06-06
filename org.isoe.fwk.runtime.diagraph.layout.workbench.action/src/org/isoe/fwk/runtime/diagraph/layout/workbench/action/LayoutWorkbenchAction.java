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
package org.isoe.fwk.runtime.diagraph.layout.workbench.action;

import java.io.File;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecoretools.legacy.diagram.edit.parts.EPackageEditPart;
import org.eclipse.emf.ecoretools.legacy.diagram.part.EcoreDiagramEditor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.emf.workspace.AbstractEMFOperation;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.gmf.runtime.diagram.ui.services.layout.LayoutService;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.generic.GenericConstants;
import org.isoe.diagraph.views.ViewConstants;
import org.isoe.extensionpoint.layout.ILayoutWorkbenchAction;
import org.isoe.fwk.core.DParams;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.parts.DiagramDocumentEditor;
import org.isoe.extensionpoint.diagraph.action.DiagraphService;

/**
 *
 * @author "fpfister"
 *
 */

import org.isoe.diagraph.controler.IDiagraphControler;  //FP140707refactored
import org.isoe.extensionpoint.diagraph.generator.IDiagraphGen;  //FP140707_b_refactored
public class LayoutWorkbenchAction implements ILayoutWorkbenchAction,
		IWorkbenchWindowActionDelegate {

	private static final boolean SAVE = true;
	private static final boolean AT_RUNTIME = true; // FP120923

	private static final boolean LOG = DParams.LayoutWorkbenchAction_LOG;

	private boolean silent;

	private IWorkbenchWindow window;
	private String parentPath;
	private String path;

	public LayoutWorkbenchAction() {

	}

	private IDiagraphControler controler;
	private Diagram diagram;

	@Override
	public void setControler(Object controler) {
		this.controler = (IDiagraphControler) controler;
		this.controler.registerService(this);
	}

	private void setDiagram(Diagram diagram) {
		if (diagram != null)
			this.diagram = diagram;
	}

	private void setDiagram_debug_nu(Diagram diagram) { // FP140416
		if (diagram != null) {
			if (diagram != this.diagram) {
				changeDiagram(diagram);
			}
			if (LOG)
				clog("***  Diagram= " + diagram.getName());
			this.diagram = diagram;
		} else if (LOG)
			clog("Diagram=null");
	}

	private void changeDiagram(Diagram diagram) {
		if (LOG)
			clog("diagram changes:"
					+ (this.diagram == null ? "null" : this.diagram.getName())
					+ " => " + diagram.getName());
	}

	private IDiagraphControler getControler() {
		if (controler == null) {
			IViewPart viewPart;
			try {
				viewPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage()
						.showView(GenericConstants.DIAGRAPH_BASIC_CONTROLER_ID_);
				controler = (IDiagraphControler) viewPart;
				controler.setLayoutActionDelegate(this);
			} catch (PartInitException e) {
				e.printStackTrace();
				message("unable to open view "
						+ GenericConstants.DIAGRAPH_BASIC_CONTROLER_ID_
						+ " not found");
				viewPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage()
						.findView(GenericConstants.DIAGRAPH_BASIC_CONTROLER_ID_);
				controler = (IDiagraphControler) viewPart;
				controler.setLayoutActionDelegate(this); // FP130506b
			}
		}
		return controler;
	}

	private void layout() {
		// if (VALIDATE_BEFORE_LAYOUT)
		// validateAction.run(); // FP121122
		final Diagram diag = ((IDiagraphGen)getControler().getDiagraphGenerator())
				.getWorkdiagram();
		// final Diagram cdiag =
		// getControler().getDiagraphGenerator().getCurrentDiagram();
		// final Diagram sdiag =
		// getControler().getDiagraphGenerator().getSelectedDiagram();

		if (DParams.LanguageTransformer_4_LOG)
			clog4("AKW diagraph.layout.workbench.action");

		if (diag != null) {
			if (DParams.LanguageTransformer_4_LOG)
				clog4("(2) starting layout");
			TransactionalEditingDomain tedom = TransactionUtil
					.getEditingDomain(diag);
			AbstractEMFOperation operation = new AbstractEMFOperation(tedom,
					DParams.KEY_DIAGRAPH_LAYOUT_, null) {
				protected IStatus doExecute(IProgressMonitor monitor,
						IAdaptable info) throws ExecutionException { // FP140611
					String layer = controler.getCurrentView(24, //FP140618voir
							((EPackage) diag.getElement()).getName(), this
									.getClass().getName());
					if (DParams.LanguageTransformer_4_LOG)
						clog4(" executing layout for diagram "
								+ ((EPackage) diag.getElement()).getName()
								+  " -view:" + layer);

					String infos = DParams.DIAGRAPH_LAYOUT_ + "=" + layer + ";";
					String layers[] = controler.getViews();
					for (int i = 0; i < layers.length; i++)
				//	for (int i = 0; i < layers.length - 1; i++) //FP140619
						infos += layers[i] + ";";

					infos = infos.substring(0, infos.length() - 1);
					LayoutService.getInstance().layout(diag, infos); // FP121029
					return Status.OK_STATUS;
				}
			};
			try {
				operation.execute(new NullProgressMonitor(), null);
			} catch (Exception e) {
				throw new RuntimeException("(4) error during layout: "
						+ e.getMessage() + " !!!!");
			}
		} else
			throw new RuntimeException("no diagram selected !!!!");
	}

	public void doLayout() {
		// boolean old = false; //FP140211
		try {
			IProgressMonitor nullProgressMonitor = new NullProgressMonitor(); // FP121017c
			EcoreDiagramEditor ecoreDiagramEditor_ = ((IDiagraphGen)getControler()
					.getDiagraphGenerator()).findActiveEcoreEditor(null);// "open a Diagraphed Ecore Model !(3)");
			if (ecoreDiagramEditor_ != null) {
				boolean rcp = false;

				String lang = ((EPackage) ecoreDiagramEditor_.getDiagram()
						.getElement()).getName();
				// FP140611
				String layer = controler.getCurrentView(25, lang, this
						.getClass().getName());//FP140618voir

				List<DGraph> diagraphs = null;

				if (false)
					diagraphs = ((IDiagraphGen)controler.getDiagraphGenerator())
						.selectDiagraphNewParserTodo(null,
								ecoreDiagramEditor_.getDiagram(), !SAVE,
								AT_RUNTIME, rcp, false,// old,
								nullProgressMonitor);

				if (diagraphs == null || diagraphs.isEmpty()) {
					if (DParams.LanguageTransformer_4_LOG)
						clog4("fallback to old parser");
					diagraphs = ((IDiagraphGen)controler.getDiagraphGenerator())
							.selectDiagraph(null,
									ecoreDiagramEditor_.getDiagram(), !SAVE,
									AT_RUNTIME, rcp, false,// old,
									nullProgressMonitor);
				}

				DGraph diagraph = (diagraphs == null ? null
						: (diagraphs.size() > 0) ? diagraphs.get(0) : null); // TODO
																				// handle
																				// with
				// view/layer
				try {

				    layout();

				} catch (Exception e) {
					String msg = " layout error for layer " + layer + " -- "
							+ controler.getError() + "[2]";// FP130418
					if (msg.contains("not diagraphed")) // cleanup this hack
														// please => needs an
														// exception hierarchy
						msg = "not diagraphed (6)";
					if (LOG)
						clog(msg);
					if (AT_RUNTIME)
						controler.showMessage(msg);
				} // FP131121

			}
		} catch (Exception e) {
			if (e instanceof NullPointerException) {
				String msg = "(2) diagraph layout error";
				controler.showMessage(msg);
				throw new RuntimeException(msg);
			}
			String msg = e.getMessage();
			if (msg == null)
				msg = e.toString();
			controler.showMessage(msg);

		}
	}

	public void saveAllEditors() {
		IWorkbenchPage workbenchPage = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		if (workbenchPage != null)
			workbenchPage.saveAllEditors(false);
	}

	@Override
	public void run(IAction action) {
		// if ("org.isoe.fwk.runtime.diagraph.add.ref".equals(action.getId()))
		// getControler().addEReference();

		boolean isLayoutAction = "org.isoe.fwk.runtime.diagraph.layout.action.LayoutAction"
				.equals(action.getId())
				|| "org.isoe.fwk.runtime.diagraph.layout.action.LayoutAction"
						.equals(action.getText());

		if (isLayoutAction) {
			// getControler().dropConcreteSyntax(); //FP131130
			getControler().setLayouting(true);
			doLayout();
			saveAllEditors();
			getControler().refresh(100);
			getControler().setLayouting(false);
		}
		/*
		 * else if (action.getId().startsWith("org.isoe.fwk.runtime.diagraph"))
		 * { String actionid = action.getId();
		 * getControler().invokeDiagraphAction(actionid);
		 *
		 * if (actionid.endsWith(".node") || actionid.endsWith(".pov") ||
		 * actionid.endsWith(".link")) { doLayout(); // FP131119
		 * saveAllEditors(); } else
		 *
		 * if (!action.getId().endsWith("cmd.btndelannot")) { doLayout(); //
		 * FP131119 saveAllEditors(); }
		 *
		 * }
		 */
	}

	private void message(String mesg) {
		MessageDialog.openInformation(window.getShell(),
				"Diagraph Layout Action", mesg);
	}

	public void selectionChanged(IAction action, ISelection selection) {
		setDiagram(getEcoreDiag());
	}

	public void selectionChanged_debug(IAction action, ISelection selection) {
		setDiagram(getEcoreDiag());
		if (selection instanceof TreeSelection) {
			TreeSelection treeSelection = (TreeSelection) selection;
			if (treeSelection.getFirstElement() != null) {
				Object element = treeSelection.getFirstElement();
				if (element instanceof IJavaProject)
					logProject((IJavaProject) element);
				if (element instanceof IFolder)
					logFolder((IFolder) element);
				if (element instanceof IFile) {
					logPath((IFile) element);
				}
			}
		}
	}

	private void logOther(ISelection selection) {
		if (LOG)
			clog(selection.toString());
	}

	private void logPackageEditPart(EPackageEditPart p) {
		setDiagram(p.getDiagramView());
		if (LOG)
			clog("EPackageEditPart");
	}

	private Diagram getEcoreDiag() {
		Diagram diagram = getCurrentDiagram();
		boolean is = diagram != null
				&& EPackageEditPart.MODEL_ID.equals(diagram.getType());
		return is ? diagram : null;
	}

	private Diagram getCurrentDiagram() {
		IEditorPart editorPart = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if (editorPart instanceof DiagramEditor) {
			DiagramEditPart host = ((DiagramEditor) editorPart)
					.getDiagramEditPart();
			return ((org.eclipse.gmf.runtime.notation.View) host.getModel())
					.getDiagram();
		}
		return null;
	}

	private void logPath(IFile element) {
		path = element.getRawLocation().toFile().getAbsolutePath();
		parentPath = new File(path).getParentFile().getAbsolutePath();
		if (LOG)
			clog(parentPath + " > " + path);
	}

	private void logFolder(IFolder element) {
		path = element.getRawLocation().toFile().getAbsolutePath();
		parentPath = new File(path).getParentFile().getAbsolutePath();
		if (LOG)
			clog(parentPath + " > " + path);
	}

	private void logProject(IJavaProject javaProject) {
		path = javaProject.getPath().toFile().getAbsolutePath();
		parentPath = new File(path).getParentFile().getAbsolutePath();
		if (LOG)
			clog(parentPath + " > " + path);
	}

	public void dispose() {
	}

	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	@Override
	public void run() {
	}

	@Override
	public boolean isStub() {
		return false;
	}

	@Override
	public boolean isQualified() {
		return true;
	}

	public void setSilent(boolean value) {
		silent = value;
	}

	private void clog(String mesg) {
		if (LOG) // && !silent
			System.out.println("LAYACT " + mesg);
	}

	private void clog4__(String mesg) {
		if (DParams.LanguageTransformer_4_LOG)
			System.out.println(mesg);

	}




	private void clog4(String mesg) {
		if (DParams.LanguageTransformer_4_LOG){ // && !silent
			if (mesg == null)
				mesg="null";
			if (DParams.LOG_ON_CONSOLE)
				controler.logConsole(mesg);//FP140630aaa
			else
			   System.out.println( mesg);
		}
	}

}
