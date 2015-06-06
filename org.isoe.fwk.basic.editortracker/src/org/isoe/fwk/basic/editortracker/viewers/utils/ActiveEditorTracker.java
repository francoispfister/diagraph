 /**
 * Copyright (c) 2014 Laboratoire de Genie Informatique et Ingenierie de Production - Ecole des Mines d'Ales
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    patrick.koenemann@itemis.de - initial API and implementation
 *    alexander.nyssen@itemis.de - initial API and implementation
 *    Francois Pfister (ISOE-LGI2P) - adaptation to Diagraph
 *    borrowed from package de.itemis.xtext.utils.jface.viewers.util;
 */
package org.isoe.fwk.basic.editortracker.viewers.utils;



import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecoretools.legacy.diagram.part.EcoreDiagramEditor;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.parts.DiagramDocumentEditor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPageListener;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.isoe.diagraph.controler.IDiagraphControler;
import org.isoe.fwk.core.DParams;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.parts.DiagramDocumentEditor;
import org.eclipse.gmf.runtime.notation.Diagram;




/**
 * Get the last active editor in general or of a specific type.
 *
 * Please note that the ActiveEditorTracker currently only supports one single
 * workbench window properly.
 *
 * @author patrick.koenemann@itemis.de
 * @author alexander.nyssen@itemis.de
 * @author francois.pfister@mines-ales.fr adaptation to diagraph
 *
 */
public class ActiveEditorTracker implements IPageListener, IPartListener,
		IStartup, IWindowListener {

	private static final String SINGLETON_MSG = "This class is a singleton and may only be instantiated once!";

	private static final boolean LOG = DParams.ActiveEditorTracker_LOG;

	private IWorkbenchWindow workbenchWindow;

	private Map<String, IEditorPart> activeEditors = new HashMap<String, IEditorPart>();

	private String lastActiveEditorId;

	private IWorkbenchPage activePage;

	private IEditorPart activeEditor;

	private IDiagraphControler controler;

	private static ActiveEditorTracker INSTANCE;




	public IEditorPart getActiveEditor() {
		return activeEditor;
	}

	public ActiveEditorTracker(IDiagraphControler controler) {
		if (INSTANCE != null)
			throw new IllegalStateException(SINGLETON_MSG);
		INSTANCE = this;
		this.controler = controler;
	}

	@Override
	public void earlyStartup() {
		PlatformUI.getWorkbench().addWindowListener(this);
	}

	/**
	 * @return The last active editor in the current active workbench page.
	 */
	public static IEditorPart getLastActiveEditor() {
		if (INSTANCE == null) {
			// not yet initialized, e.g. when another early startups blocks us!
			// Let's try to get the current active editor instead.
			if (PlatformUI.getWorkbench() != null) {
				if (PlatformUI.getWorkbench().getActiveWorkbenchWindow() != null) {
					return PlatformUI.getWorkbench().getActiveWorkbenchWindow()
							.getActivePage().getActiveEditor();
				}
			}
			return null;
		}
		return INSTANCE.getLastActiveEditorInternal();
	}

	private IEditorPart getLastActiveEditorInternal() {
		if (activePage == null) {
			initialize(PlatformUI.getWorkbench().getActiveWorkbenchWindow());
			if (activePage == null)
				return null;
		}
		boolean updated = false;
		if (lastActiveEditorId == null) {
			final IEditorPart editor = activePage.getActiveEditor();
			if (editor != null) {
				setActiveEditor(editor);
			}
			updated = true;
		}
		IEditorPart editor = getEditorById(lastActiveEditorId);
		if (editor == null && !updated) {
			editor = activePage.getActiveEditor();
			if (editor != null) {
				setActiveEditor(editor);
			}
		}
		return editor;
	}


	/**
	 * @return The last active editor with the given editor ID in the current
	 *         active workbench page.
	 */
	public static IEditorPart getLastEditor(String editorId) {
		if (INSTANCE == null) {
			// not yet initialized, e.g. when another early startups blocks us!
			// Let's try to get any editor with the specified id instead.
			if (PlatformUI.getWorkbench() != null) {
				final IWorkbenchWindow window = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow();
				if (window != null) {
					final IWorkbenchPage page = window.getActivePage();
					if (page != null) {
						for (IEditorReference ref : page.getEditorReferences()) {
							if (ref.getId().equals(editorId)) {
								return ref.getEditor(false);
							}
						}
					}
				}
			}
			return null;
		}
		return INSTANCE.getEditorById(editorId);
	}


	private IEditorPart getEditorById(String editorId) {
		if (activePage == null || editorId == null)
			return null;
		final IEditorPart editor = activeEditors.get(editorId);
		final String id = checkEditorAndGetId(editor);
		if (id != null && id.equals(editorId)) {
			return editor;
		}
		return null;
	}

	/**
	 *
	 * @return The EMF resource set of the last active editor (if it is still
	 *         open).
	 */
	public static ResourceSet getLastActiveEditorResourceSet() {
		final IEditorPart editor = getLastActiveEditor();
		if (editor == null)
			return null;
		EditingDomain domain = null;
		if (editor instanceof IEditingDomainProvider) {
			domain = ((IEditingDomainProvider) editor).getEditingDomain();
		} else if (editor.getAdapter(IEditingDomainProvider.class) != null) {
			domain = ((IEditingDomainProvider) editor
					.getAdapter(IEditingDomainProvider.class))
					.getEditingDomain();
		} else if (editor.getAdapter(EditingDomain.class) != null) {
			domain = (EditingDomain) editor.getAdapter(EditingDomain.class);
		}
		if (domain == null) {
			return null;
		}

		return domain.getResourceSet();
	}

	/**
	 * @return The project which contains the file that is open in the last
	 *         active editor in the current workbench page.
	 */
	public static IProject getLastActiveEditorProject() {
		final IEditorPart editor = getLastActiveEditor();
		if (editor == null)
			return null;
		final IEditorInput editorInput = editor.getEditorInput();
		if (editorInput instanceof IFileEditorInput) {
			final IFileEditorInput input = (IFileEditorInput) editorInput;
			return input.getFile().getProject();
		}
		return null;
	}



	@Override
	public void pageClosed(IWorkbenchPage page) {
		if (page == activePage) {
			activePage = null;
			if (DParams.LanguageTransformer_4_LOG) clog4("AKW TRCK pageClosed");
		}
		lastActiveEditorId = null;
	}

	@Override
	public void pageOpened(IWorkbenchPage page) {
		if (LOG) _clog("TRCK pageOpened: "+page.getLabel());
	}

	public EPackage getActiveEPackage() { // FP131225
		EPackage result  = null;
		if (LOG)
			_clog(" getLastEPackage()");
		IEditorPart editorPart = getLastActiveEditor();
		if (editorPart instanceof EcoreDiagramEditor)
			result=(EPackage) (((EcoreDiagramEditor) editorPart).getDiagram()).getElement();
		if (LOG)
			_clog(" getLastEPackage()="+result==null?"null":result.getName());
		return result;
	}

	private EcoreDiagramEditor getActiveEcoreEditor_pb() { // FP140528
		EcoreDiagramEditor result = null;
		IEditorPart editorPart = getLastActiveEditor();
		if (editorPart instanceof EcoreDiagramEditor)
			result=(EcoreDiagramEditor) editorPart;
		else
			System.err.println(" opened Editor is not a n EcoreDiagramEditor: "
						+ editorPart.getTitle());
		return result;
	}

	private EcoreDiagramEditor getActiveEcoreEditor() { // FP120621
		IWorkbenchPage workbenchPage = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		if (workbenchPage.getActiveEditor() == null) {
			System.err.println(" no editor opened");
			return null;
		}
		IWorkbenchPart workbenchPart = workbenchPage.getActiveEditor()
				.getEditorSite().getPart();
		if (workbenchPart instanceof EcoreDiagramEditor) {
			return (EcoreDiagramEditor) workbenchPart;
		} else {
			if (LOG)
				_clog(" (2)opened Editor is not a n EcoreDiagramEditor: "
						+ workbenchPart.toString());
			return null;
		}
	}


	private IWorkbenchPart  getActiveEditor1() { // FP120621
		IWorkbenchPage workbenchPage = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		if (workbenchPage.getActiveEditor() == null) {
			System.err.println(" no editor opened");
			return null;
		}
		IWorkbenchPart workbenchPart = workbenchPage.getActiveEditor()
				.getEditorSite().getPart();

		return workbenchPart;

	}

	@Override
	public void partActivated(IWorkbenchPart part) {
		if (part instanceof IEditorPart)
			setActiveEditor((IEditorPart) part);
	}

	@Override
	public void partBroughtToTop(IWorkbenchPart part) {
		if (part instanceof IEditorPart) {
			if (LOG) _clog("TRCK partBroughtToTop: "+part.getTitle());
			setActiveEditor((IEditorPart) part);
		}
	}

	@Override
	public void partClosed(IWorkbenchPart part) {
		if (part instanceof IEditorPart) {
			String id = null;
			for (Entry<String, IEditorPart> entry : activeEditors.entrySet()) {
				if (entry.getValue().equals(part)) {
					id = entry.getKey();
					break;
				}
			}
			if (id != null) {
				activeEditors.remove(id);
				if (DParams.LanguageTransformer_4_LOG) clog4("AKW TRCK partClosed: "+id +"("+part.getTitle()+")");
				if (id.equals(lastActiveEditorId)) {
					lastActiveEditorId = null;
					if (DParams.LanguageTransformer_4_LOG) clog4("AKW TRCK id null ");
				}
			}
		}
	}

	@Override
	public void partDeactivated(IWorkbenchPart part_) {
		if (part_!= null && part_ instanceof IEditorPart) {
			IWorkbenchPartReference reference_ = activePage.getReference(part_);
			String rid= reference_.getId();
			//if (DParams.LanguageTransformer_4_LOG) clog4("AKW TRCK partDeactivated="+part_.getTitle()+" referenceid="+rid);
		}
	}

	private void clog4____(String mesg) {
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






	private String checkEditorAndGetId(IEditorPart editor) {
		if (editor == null)
			return null;
		for (IEditorReference ref : activePage.getEditorReferences()) {
			if (editor.equals(ref.getEditor(false))) {
				return ref.getId();
			}
		}
		return null;
	}


	public IWorkbenchPage getActivePage() {
		return activePage;
	}

	/**
	 * Set the active editor
	 */
	private void setActiveEditor(IEditorPart part) {
		activeEditor = part;
		controler.setActiveEditor(part);
		EcoreDiagramEditor ecoreDiagramEditor = getActiveEcoreEditor();
		if (ecoreDiagramEditor!=null){ //FP140527
		  EPackage epack=(EPackage) ecoreDiagramEditor.getDiagram().getElement();
		  if (DParams.LanguageTransformer_4_LOG) clog4("AKW TRCK partActivated: "+epack.getName()+" (title="+part.getTitle()+")");
		  controler.setActiveEcoreDiagramEditor(ecoreDiagramEditor);
		}
		if (part == null) {
			lastActiveEditorId = null;
			return;
		}
		final IWorkbenchPartReference reference = activePage.getReference(part);
		if (reference == null)
			throw new IllegalStateException("Impossible?!");
		lastActiveEditorId = reference.getId();
		activeEditors.put(lastActiveEditorId, part);

		if (!(activeEditor instanceof EcoreDiagramEditor))
		  controler.setOtherEditor(activeEditor);
		if (LOG) _clog("AKW TRCK setActiveEditor:"+lastActiveEditorId);
	}

	@Override
	public void pageActivated(IWorkbenchPage page) {
		this.activePage = page;
		if (LOG) _clog("TRCK pageActivated:"+page.getLabel());
	}

	private void _clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	@Override
	public void partOpened(IWorkbenchPart part) {
		if (part instanceof IEditorPart) {
			setActiveEditor((IEditorPart) part);
			if (LOG) _clog("TRCK partOpened:"+part.getTitle());
		}
	}

	public void dispose() {
		if (workbenchWindow == null)
			return;
		workbenchWindow.removePageListener(this);
		workbenchWindow.getPartService().removePartListener(this);
		workbenchWindow = null;
	}

	@Override
	public void windowActivated(IWorkbenchWindow window) {
		initialize(window);
		if (LOG) _clog("TRCK windowActivated");
	}


	protected void initialize(IWorkbenchWindow window) {
		if (workbenchWindow != null && !workbenchWindow.equals(window)) {
			/*
			 * TODO: implement logic for keeping track of editors in multiple
			 * workbench windows!
			 */
		}
		this.workbenchWindow = window;
		if (window == null)
			return;
		this.activePage = window.getActivePage();
		final IEditorPart editor = this.activePage.getActiveEditor();
		if (editor != null) {
			lastActiveEditorId = checkEditorAndGetId(editor);
			activeEditors.put(lastActiveEditorId, editor);
			if (DParams.LanguageTransformer_4_LOG) clog4("AKW TRCK initialized");
		}
		window.addPageListener(this);
		window.getPartService().addPartListener(this);
	}

	@Override
	public void windowDeactivated(IWorkbenchWindow window) {
		// not of interest
	}

	@Override
	public void windowClosed(IWorkbenchWindow window) {
		dispose();
	}

	@Override
	public void windowOpened(IWorkbenchWindow window) {
		// not of interest
	}


}
