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
package org.isoe.fwk.utils.eclipse;


import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.views.contentoutline.ContentOutline;


import  org.eclipse.core.resources.IFile;





/**
 *
 * @author fpfister
 *
 */
public class EclipseUtil {



	public void refreshOutlinePage(){
		IViewPart view = EclipseUtil.findView("org.eclipse.ui.views.ContentOutline");
		/*
		if (fOutlinePage!=null )
			if (view==null )
				fOutlinePage.dispose();
			else
				fOutlinePage.update();*/
	}

//FP141212
	private void viewOutline(ISelection selection){
		org.eclipse.ui.views.contentoutline.ContentOutline co;
		try {
			IViewPart view = EclipseUtil.findView("org.eclipse.ui.views.ContentOutline");
			if (view == null)
				view = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("org.eclipse.ui.views.ContentOutline");
			co = (ContentOutline) view;
			co.setSelection(selection);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static IEditorPart openEditor( IEditorInput editorInput, String editorID ) throws Exception {
		IWorkbenchPage activePage = getActivePage();
		if ( activePage == null )
			return null;
		IEditorPart editor = activePage.findEditor( editorInput );
		if ( editor == null )
			editor = IDE.openEditor( activePage, editorInput, editorID );
		 else
			activePage.activate( editor );
		return editor;
	}


	public static IWorkbenchPage getActivePage() {
		IWorkbenchWindow window = getActiveWorkbench().getActiveWorkbenchWindow();
		if ( window == null ) {
			return null;
		}
		return window.getActivePage();
	}


	public static IWorkbench getActiveWorkbench() {
		return PlatformUI.getWorkbench();
	}


	public static IViewPart findView(final String viewID) {

		final IViewPart[] retContainer = new IViewPart[1];

		syncExec(new Runnable() {
			public void run() {
				IWorkbenchPage page = getActivePage();
				if (null==page) return ;
				retContainer[0] = page.findView(viewID);
			}});

		return retContainer[0];
	}

	/**
	 *
	 * @param runnable
	 * @return
	 */
	public static Object syncExec(Runnable runnable) {
		//final Object[] obj = new Object[1];
		getDisplay().syncExec(runnable);
		return "";
	}

	/**
	 *
	 * @return
	 */
	public static Display getDisplay() {

		Display dp = Display.getCurrent();

		if (dp == null)
			dp = Display.getDefault();

		return dp;
	}

}
