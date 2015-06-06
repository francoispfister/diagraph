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

package org.isoe.diagraph.templateaction.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

/**
 * 
 * @author fpfister
 * 
 */
public class ExecuteTemplateAction implements IWorkbenchWindowActionDelegate {

	private IFile gmFile;

	public ExecuteTemplateAction() {
	}

	public void setGmFile(IFile gmFile) {
		this.gmFile = gmFile;
	}

	@Override
	public void run(IAction action) {
		if (gmFile != null) {
			ExecuteTemplatesOperation op = new ExecuteTemplatesOperation();
			op.setGenModelURI(URI.createPlatformResourceURI(gmFile
					.getFullPath().toString(), true));
			op.run();
		} else
			throw new RuntimeException("No Gmfgen to process !!!");
	}


	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// Auto-generated method stub
	}

	@Override
	public void dispose() {
		// Auto-generated method stub
	}

	@Override
	public void init(IWorkbenchWindow window) {
		// Auto-generated method stub
	}

}