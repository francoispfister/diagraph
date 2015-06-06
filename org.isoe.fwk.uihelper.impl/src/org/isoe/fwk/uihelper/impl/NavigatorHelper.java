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
package org.isoe.fwk.uihelper.impl;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.ecoretools.legacy.diagram.part.EcoreDiagramEditor;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.internal.ui.packageview.PackageExplorerPart;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.isoe.fwk.uihelper.INavigatorHelper;
import org.isoe.fwk.utils.ResourceUtils;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.parts.DiagramDocumentEditor;

/**
 *
 * @author fpfister
 *
 */
import org.isoe.diagraph.controler.IDiagraphControler;  //FP140707refactored
public class NavigatorHelper implements INavigatorHelper {

	IDiagraphControler controler;

	public NavigatorHelper(IDiagraphControler controler) {
		this.controler = controler;
	}

	private IViewPart findView(String vid) {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().findView(vid);
	}


	private IEditorPart bringToTop(IProject project) {
		String langName = project.getName();
		langName = langName.substring(langName.lastIndexOf(".") + 1);
		IFile diagramfile = ResourceUtils.getFile(project, "model" + "/"
				+ langName, "ecorediag");// errstatechart.ecorediag
		if (diagramfile==null)
			 return null;

		IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();

		try {
			IEditorPart openedEditor = IDE.openEditor(page, diagramfile);
			page.bringToTop(openedEditor);
			// result.setFocus();
			return openedEditor;
		} catch (PartInitException e) {
			// clog("Open editor failed: " + diagramfile.toString() + " -- " +
			// e);
			return null;
		}
	}

	@Override
	public Diagram bringToTop(String language) {
		try {
			IViewPart navigator = findView("org.eclipse.jdt.ui.PackageExplorer"); // FP130130x
			if (navigator == null)
				navigator = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage()
						.showView("org.eclipse.jdt.ui.PackageExplorer");
			TreeViewer tree = ((PackageExplorerPart) navigator).getTreeViewer();
			List<IProject> projects = controler.getDsmlProjects();
			for (IProject iProject : projects) {
				if (iProject.getName().endsWith("." + language)) {
					StructuredSelection ssel = new StructuredSelection(iProject);
					tree.getControl().setRedraw(false);
					tree.setSelection(ssel, true);
					tree.getControl().setRedraw(true);
					IEditorPart opened = bringToTop(iProject);
					if (opened==null){
						System.err.println("error while focusDiagram "+language);
						return null;
					}
					return ((EcoreDiagramEditor) opened).getDiagram();
				}
			}
		} catch (Exception e) {
			System.err.println("error while focusDiagram "+language);
		}
		System.err.println("should not happen in bringToTop for language "+language);
		throw new RuntimeException("should not happen in bringToTop for language "+language);//d = getCurrentDiagram();
	}


	/*
	public EPackage get_(String language) {
		 return (EPackage) bringToTop(language).getElement();
	}
*/

	private IViewPart focusInPackageExplorerTraverserSnippet(String language) {
		IViewPart navigator = null;

		try {
			navigator = findView("org.eclipse.jdt.ui.PackageExplorer"); // FP130130x
			if (navigator == null)
				navigator = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage()
						.showView("org.eclipse.jdt.ui.PackageExplorer");

			IWorkbenchWindow window = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow();
			ISelection selection_ = window.getSelectionService().getSelection(
					"org.eclipse.jdt.ui.PackageExplorer");

			if (selection_ instanceof Tree) {
				TreeItem[] selected = ((Tree) selection_).getSelection();
				for (TreeItem treeItem : selected) {
					System.out.println(treeItem.getText());
				}

			}

			if (selection_ instanceof TreeSelection) {
				TreeSelection ts = (TreeSelection) selection_;
				List tsl = ts.toList();
				for (Object object : tsl) {
					System.out.println(tsl.getClass().getName());
				}
			}

			Iterator ite = ((IStructuredSelection) selection_).iterator();
			while (ite.hasNext()) {
				Object obj = ite.next();
				String testString = "";
				if (obj instanceof IJavaElement) {
					IPath packageName = ((IJavaElement) obj).getPath();// /lang.m2.helloworld
					System.out.println("getFileExtension()   "
							+ packageName.getFileExtension());
					String s = packageName.toString();
					String s1[] = s.split("/");
					if ("java".equals(packageName.getFileExtension())) {
						System.out.println("package name " + s1[1]);
						System.out.println("project name"
								+ ((IJavaElement) obj).getElementName());
					}
				}
			}

			if (navigator instanceof PackageExplorerPart) {
				PackageExplorerPart pe = (PackageExplorerPart) navigator;
				TreeViewer tree = pe.getTreeViewer();

				Control[] childs = tree.getTree().getChildren();
				for (Control control : childs) {
					System.out.println(control.getClass().getName());
				}

				TreeItem[] selected = tree.getTree().getSelection();
				for (TreeItem treeItem : selected) {
					System.out.println(treeItem.getText());
				}

				TreeItem[] items = tree.getTree().getItems();
				for (TreeItem treeItem : items) {
					;// System.out.println(treeItem.getText());
				}

				Object[] els = tree.getExpandedElements();
				for (Object el : els) {
					System.out.println(el.getClass().getName());
				}
				// tree.getChildren(widget, elementChildren)

				org.eclipse.jface.viewers.TreePath[] tp = tree
						.getExpandedTreePaths();
				for (TreePath treePath : tp) {
					System.out.println(treePath.getFirstSegment().toString());
				}

				List<IProject> projects = controler.getDsmlProjects();
				for (IProject iProject : projects) {
					if (iProject.getName().endsWith("." + language)) {
						StructuredSelection ssel = new StructuredSelection(
								iProject);
						tree.getControl().setRedraw(false);
						tree.setSelection(ssel, true);
						tree.getControl().setRedraw(true);

						IEditorPart opened = bringToTop(iProject);
						// if (currentPackage==null)
						// throw new
						// RuntimeException("should not happen in getPackage()");
						((EcoreDiagramEditor) opened).getDiagram();

					}
				}

			}
			// org.eclipse.jdt.ui.PackageExplorer pe;

			IViewReference[] views = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage()
					.getViewReferences();
			for (IViewReference view : views) {
				if ("org.eclipse.jdt.ui.PackageExplorer".equals(view.getId())) {
					IViewPart pExplorer = view.getView(true);

					TreeViewer viewer;

					// pExplorer.
					// pExplorer.getViewSite().getSelectionProvider().setSelection(selection);
					break;
				}
			}

		} catch (PartInitException e1) {
			e1.printStackTrace();
			return null;
		}
		return navigator;
	}


}
