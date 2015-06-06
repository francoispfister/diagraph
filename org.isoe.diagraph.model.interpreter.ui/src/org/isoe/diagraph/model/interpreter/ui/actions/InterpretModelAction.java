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
package org.isoe.diagraph.model.interpreter.ui.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.isoe.extensionpoint.modelrunner.InterpreterConnector;
import org.isoe.extensionpoint.modelrunner.ModelInterpreter;
import org.isoe.extensionpoint.modelrunner.ModelLauncher;
import org.isoe.fwk.core.DParams;

/**
 *
 * @author fpfister
 *
 */
import org.isoe.extensionpoint.diagraph.action.DiagraphService;  //FP140707_c_refactored
public class InterpretModelAction implements IObjectActionDelegate, org.isoe.diagraph.osem.constants.OsemConfiguration {
	private static final boolean LOG = DParams.InterpretModelAction_LOG;
	// FP140317
	private Shell shell;
	private URI uri;




	private String language;

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

	private ModelInterpreter getModelInterpreter_(String language) {
		boolean isStub = true;
		boolean isQualified = false;
		ModelInterpreter modelInterpreter = null;
		// if (modelInterpreter == null) {
		modelInterpreter = new InterpreterConnector()
				.getModelInterpreterService(language);
		if (modelInterpreter == null)
			throw new RuntimeException("no ModelInterpreter");
		// modelInterpreter.setControler(this);
		if (modelInterpreter.isStub()) { // is a mock object, presently
			// unusable, for further
			// implementation
			if (LOG) {
				clog("ModelInterpreter is Stub");
				// String willDo = modelInterpreter.exposeContract();
				// if (LOG)
				// clog("should do: " + willDo);
			}
		} else
			isStub = false;
		if (!modelInterpreter.isQualified()) { // is not a mock object but not
												// usable
			if (LOG)
				clog("ModelInterpreter is Not Qualified");
		} else
			isQualified = true;
		// }
		return (isQualified && !isStub) ? modelInterpreter : null;
	}

	private ModelLauncher getModelLauncher(String configuration) {
		boolean isStub = true;
		boolean isQualified = false;
		ModelLauncher modelLancher = null;
		// if (modelLancher == null) {
		modelLancher = new InterpreterConnector()
				.getModelLauncherService_(configuration);
		if (modelLancher == null)
			throw new RuntimeException("no ModelLancher");
		// modelLancher.setControler(this);
		if (modelLancher.isStub()) { // is a mock object, presently
			// unusable, for further
			// implementation
			if (LOG) {
				clog("ModelLauncher is Stub");
				// String willDo = modelLancher.exposeContract();
				// if (LOG)
				// clog("should do: " + willDo);
			}
		} else
			isStub = false;
		if (!modelLancher.isQualified()) { // is not a mock object but not
											// usable
			if (LOG)
				clog("ModelLauncher is Not Qualified");
		} else
			isQualified = true;
		// }
		return (isQualified && !isStub) ? modelLancher : null;
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);

	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		if (uri != null) {
			language = uri.fileExtension();// uriAsString.substring(uriAsString.lastIndexOf(".")+1);
			for (String[] mapLineLine : EXEC_MAP)
				if (mapLineLine[0].equals(language)) {
					clog("ITPMA interpret "+language);
					interpret();
					clog("ITPMA launch "+language+"("+mapLineLine[1]+","+ mapLineLine[2]+")");
					launch(mapLineLine[1], mapLineLine[2]);
					break;
				}
		}
	}

	private ModelInterpreter interpret() {
		ModelInterpreter interpreter = getModelInterpreter_(language);
		if (interpreter != null) {
			interpreter.setPlugin(uri.segment(1));
			interpreter.setFolder(uri.segment(2));
			String res = uri.lastSegment();
			res = res.substring(0, res.lastIndexOf("."));
			interpreter.setResourceName(res);
			interpreter.load();
			interpreter.run();
		}
		return interpreter;
	}


	private void launch(String launcherId, String projectId) {
		ModelInterpreter interpreter = getModelInterpreter_(language);
		if (interpreter != null) {
			interpreter.setPlugin(uri.segment(1));
			interpreter.setFolder(uri.segment(2));
			String res = uri.lastSegment();
			res = res.substring(0, res.lastIndexOf("."));
			interpreter.setResourceName(res);
			interpreter.load();
			ModelLauncher launcher = getModelLauncher(launcherId);
			if (launcher != null) {
				launcher.setLaunchProject(projectId);
				launcher.setTarget(interpreter.getLocation());
				launcher.launch();
			}
		}
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		uri = null;
		try {
			if (selection instanceof TreeSelection) {
				TreeSelection treeSelection = (TreeSelection) selection;
				if (treeSelection.getFirstElement() != null) {
					Object element = treeSelection.getFirstElement();
					if (element instanceof IFile) {
						IFile ifil = (IFile) element;
						if (ifil.getType() == IResource.FILE) {
							String ipath = ifil.getFullPath().toString();
							uri = URI.createPlatformResourceURI(ipath, true);
						}
					}
				}
			}
		} catch (Exception e) {
			language = null;
			e.printStackTrace();
		}
	}

}
