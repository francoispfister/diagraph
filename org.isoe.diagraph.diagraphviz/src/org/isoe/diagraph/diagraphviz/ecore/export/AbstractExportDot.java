 /*******************************************************************************
 * Copyright (c) 2008, Jean-Rémy Falleri.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Jean-Rémy Falleri - initial API and implementation
 *******************************************************************************/

package org.isoe.diagraph.diagraphviz.ecore.export;

import java.io.IOException;

//import old.M2tg;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

//import v2.EcoreToDot;


public abstract class AbstractExportDot implements IObjectActionDelegate {
	IStructuredSelection selection;

	public AbstractExportDot() {
		super();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		for(Object object: selection.toArray() ) {/*
			if ( object instanceof IFile ) {
				
				IFile iFile = ((IFile) object);
				String source = iFile.getRawLocation().toFile().getAbsolutePath();
				String target = source + ".dot";
				boolean[] options = ecoreToDotOptions();
			
				M2tg e2dot = new M2tg(source,target,options[0],options[1],options[2],options[3]);
				e2dot.handleModel(null,null,null,null);
				e2dot.toDotFile();
				

				IProgressMonitor monitor = new NullProgressMonitor();
				try {
					iFile.getParent().refreshLocal(1, monitor);
				}
				catch(CoreException e) {}
			}
			*/
		}
	}
	
	protected abstract boolean[] ecoreToDotOptions();
	
	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}
	
	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
			if (selection instanceof IStructuredSelection)
				this.selection = (IStructuredSelection) selection;
	}
}
