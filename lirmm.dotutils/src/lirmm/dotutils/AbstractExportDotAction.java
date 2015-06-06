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

package lirmm.dotutils;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;


public abstract class AbstractExportDotAction implements IObjectActionDelegate {
	private static final boolean LOG = false;
	IStructuredSelection selection; 

	public AbstractExportDotAction() {
		super();
	}
	
	public abstract String getFormat();
	
	
	
	public static void drawGraph(String filePath,String format) throws IOException  {
		File file_ = new File(filePath);
		String dotPath = file_.getAbsolutePath();
		String imgPath = dotPath + "." + format;
		
		//String charset1= "charset=UTF-8";
		String charset2= "charset=latin1";
		String dotCommand = "dot -T" + format + " " + dotPath + " -o " + imgPath+ " -G"+charset2;
		clog("executing " + dotCommand);
		Process p = Runtime.getRuntime().exec(dotCommand);
		try {
		p.waitFor();
		}
		catch(InterruptedException e) {
			clog("error dotifying");	
		}
	}

	private static void clog(String mesg) {
		if (LOG)
	  	  System.out.println(mesg);
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		for(Object object: selection.toArray() ) {
			if ( object instanceof IFile ) {
				IFile iFile = ((IFile) object);
				String source = iFile.getRawLocation().toFile().getAbsolutePath();
				try {
					drawGraph(source, getFormat());
				}
				catch(IOException e) {}
				IProgressMonitor monitor = new NullProgressMonitor();
				try {
					iFile.getParent().refreshLocal(1, monitor);
				}
				catch(CoreException e) {
				   throw new RuntimeException(e);
				}
			}
		}
	}
	
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
