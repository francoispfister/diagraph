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
package org.isoe.diagraph.menu.handlers;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.isoe.diagraph.generic.GenericConstants;


/**
 *
 * @author pfister
 *
 */
import org.isoe.diagraph.controler.IDiagraphControler;  //FP140707refactored
public class DiagraphWorkbenchAction implements IDiagraphWorkbenchAction,IWorkbenchWindowActionDelegate {

	private static final boolean LOG = false;


	private IWorkbenchWindow window;
	private IDiagraphControler controler;


	public DiagraphWorkbenchAction() {

	}


	@Override
	public void setControler(Object controler) {
		this.controler = (IDiagraphControler) controler;
		this.controler.registerService(this);
	}

	private IDiagraphControler getControler() {
		if (controler == null) {
			IViewPart viewPart;
			try {
				viewPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage()
						.showView(GenericConstants.DIAGRAPH_BASIC_CONTROLER_ID_);
				controler = (IDiagraphControler) viewPart;

			} catch (PartInitException e) {
				e.printStackTrace();
				message("unable to open view "
						+ GenericConstants.DIAGRAPH_BASIC_CONTROLER_ID_
						+ " not found");
				viewPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage()
						.findView(GenericConstants.DIAGRAPH_BASIC_CONTROLER_ID_);
				controler = (IDiagraphControler) viewPart;
			}
		}
		return controler;
	}


	public void saveAllEditors_nu() {
		IWorkbenchPage workbenchPage = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		if (workbenchPage != null)
			workbenchPage.saveAllEditors(false);
	}


	@Override
	public void run(IAction action) {
		boolean[] result=new boolean[2];
		result[0]=false;
		result[1]=false;
        if (action.getId().startsWith("org.isoe.diagraph.menu"))
			result= getControler().invokeDiagraphAction(action.getId());
        if (LOG){
          if (result[0])
        	clog("handled "+action.getId()+" from menu");
          if (result[1])
        	clog("refreshed "+action.getId()+" from menu");
        }
	}



	private void message(String mesg) {
		MessageDialog.openInformation(window.getShell(),
				"Diagraph Workbench Action", mesg);
	}

	public  void selectionChanged(IAction action, ISelection selection) {
		//setDiagram(getEcoreDiag());
		if (selection instanceof TreeSelection) {
			TreeSelection treeSelection = (TreeSelection) selection;
			if (treeSelection.getFirstElement() != null) {
				Object element = treeSelection.getFirstElement();
				/*
				if (element instanceof IJavaProject)
					logProject((IJavaProject) element);
				if (element instanceof IFolder)
					logFolder((IFolder) element);
				if (element instanceof IFile) {
					logPath((IFile) element);
				}*/
			}
		}

	}

	@Override
	public void dispose() {
	}

	@Override
	public void init(IWorkbenchWindow window) {
		this.window = window;
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
		//silent = value;
	}

	private void clog(String mesg) {
		if (LOG) // && !silent
			System.out.println("WACT "+mesg);
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub

	}




}
