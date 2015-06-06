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
package org.isoe.fwk.basic.startup;

import org.eclipse.core.commands.Command;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveListener;
import org.eclipse.ui.IPerspectiveRegistry;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.progress.UIJob;
/**
 *
 * @author fpfister
 *
 */
import org.isoe.diagraph.controler.IDiagraphControler; //FP140707refactored
import org.isoe.diagraph.generic.GenericConstants;
import org.isoe.diagraph.views.ViewConstants;
import org.isoe.diagraph.workbench.api.IDiagraphActionWiper;
import org.isoe.fwk.core.DParams;

public class Startup implements IStartup, IPerspectiveListener,
		IDiagraphActionWiper {

	private static final boolean LOG = DParams.Startup_LOG;

	private static final String[] ACTIONS_2_WIPE = new String[] {
			"org.isoe.diagraph.menu.actions.2", //$NON-NLS-1$
			"org.isoe.diagraph.menu.actions.3", //$NON-NLS-1$
			"org.isoe.diagraph.menu.actionSet", //$NON-NLS-1$
			"org.isoe.fwk.runtime.diagraph.actions" //$NON-NLS-1$
	};

	private void openPerspective(String perspId, IAdaptable input) {

		AbstractUIPlugin plugin = (AbstractUIPlugin) Platform
				.getPlugin(PlatformUI.PLUGIN_ID);
		IPreferenceStore store = plugin.getPreferenceStore();
		String pref = store
				.getString(IWorkbenchPreferenceConstants.OPEN_NEW_PERSPECTIVE);
		IWorkbench workbench = PlatformUI.getWorkbench();

		// Implement open behavior.
		try {
			if (pref.equals(IWorkbenchPreferenceConstants.OPEN_PERSPECTIVE_WINDOW))
				workbench.openWorkbenchWindow(perspId, input);
			else if (pref
					.equals(IWorkbenchPreferenceConstants.OPEN_PERSPECTIVE_PAGE))
				PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.openPage(perspId, input);
			else if (pref
					.equals(IWorkbenchPreferenceConstants.OPEN_PERSPECTIVE_REPLACE)) {
				IPerspectiveRegistry reg = workbench.getPerspectiveRegistry();

				IPerspectiveDescriptor pid = reg.findPerspectiveWithId(perspId);

				IPerspectiveDescriptor current = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage()
						.getPerspective(); // FP131206

				if (current == null || current != null && current != pid) {

					if (current != null)
						clog("current perspective is " + current.getLabel());
					clog("setting perspective " + pid.getLabel());

					PlatformUI.getWorkbench().getActiveWorkbenchWindow()
							.getActivePage().setPerspective(pid);
				} else {
					if (current != null)
						clog("allready opened perspective: "
								+ current.getLabel());
				}
			}
		} catch (WorkbenchException e) {
			System.err
					.println("unable to open Diagraph perspective at startup ("
							+ perspId + ")");
			// e.printStackTrace();
		}
	}

	private void showView(String vid) {
		IViewPart result = null;
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().showView(vid);
		} catch (Exception e) {

		}
	}

	protected IViewPart findAndShowView(String vid) {
		IViewPart result = null;
		try { // FP130627
			result = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().findView(vid);
			showView(vid);
			if (result == null) // FP131206
				result = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage().findView(vid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private IViewPart findView(String vid) {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().findView(vid);
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	private URI getDiagraphURI() {
		URI result = URI
				.createURI("platform:/plugin/org.isoe.diagraph.diagraph");
		URI r = CommonPlugin.resolve(result);
		return r;
	}

	private String getBasePath() {
		return org.isoe.fwk.platform.resolver.Activator.getWorkspacePath();
	}

	private boolean isDevMode() { // FP141223
		String startMode = System.getProperty("start.mode");
		if ("dev".equals(startMode)) { // FP141224
			clog("starting in dev mode");
			return true;
		} else
			clog("starting in prod mode");
		return false;
	}



	void snippet_toremove(){
		final UIJob switchDiagraphPersectiveUIJob = new UIJob("Switching Diagraph Perspective") {
			@SuppressWarnings({ "restriction", "deprecation" })
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				IWorkbench workbench = PlatformUI.getWorkbench();
				IPerspectiveRegistry reg = workbench.getPerspectiveRegistry();
				IPerspectiveDescriptor pid = reg.findPerspectiveWithId(GenericConstants.DIAGRAPH_PERSPECTIVE_ID);
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				page.setPerspective(pid);
				return Status.OK_STATUS;
			}
		};
		switchDiagraphPersectiveUIJob.schedule(10);
	}


	private void runStartupUIJob(){
		final Startup thisObject = this;
		final UIJob startupUIJob = new UIJob("Diagraph startup") {
			@SuppressWarnings({ "restriction", "deprecation" })
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
						//schedule job //FP150322 delay
					IDiagraphControler mainDiagraphControler = null;
					try {

						// Connector.exploreServices();
						clog("findPerspectiveWithId() "
								+ GenericConstants.DIAGRAPH_PERSPECTIVE_ID);

						openPerspective(
								GenericConstants.DIAGRAPH_PERSPECTIVE_ID,
								ResourcesPlugin.getWorkspace()); // FP130627

						IPerspectiveDescriptor diagraphpd = PlatformUI
								.getWorkbench()
								.getPerspectiveRegistry()
								.findPerspectiveWithId(
										GenericConstants.DIAGRAPH_PERSPECTIVE_ID);

						PlatformUI.getWorkbench().getActiveWorkbenchWindow()
								.getActivePage()
								.setPerspective(diagraphpd);
						clog("findAndShowView() "
								+ GenericConstants.DIAGRAPH_BASIC_CONTROLER_ID_);

						IViewPart vp = findAndShowView(GenericConstants.DIAGRAPH_BASIC_CONTROLER_ID_);
						if (vp != null) {
							vp.setFocus();
							mainDiagraphControler = (IDiagraphControler) vp;
							clog("mainDiagraphControler.startup() ");
							mainDiagraphControler.startup(getBasePath());
						} else
							clog("Diagraph Perspective not found !!!"); // FP130626

					} catch (Exception e) {

						System.err
								.println("error while showing Diagraph Perspective !!!");
					}

					if (mainDiagraphControler != null) {

						for (IWorkbenchWindow wind : PlatformUI.getWorkbench()
								.getWorkbenchWindows()) {
							IWorkbenchPage page = wind.getActivePage();
							if (page != null)
								mainDiagraphControler.wipeActions(thisObject,
										page);
							wind.addPerspectiveListener(thisObject);
						}

						mainDiagraphControler.earlyStartup();
						/*
						 * IWorkbenchWindow[] windows =
						 * PlatformUI.getWorkbench() .getWorkbenchWindows(); for
						 * (int i = 0; i < windows.length; i++) { IWorkbenchPage
						 * page = windows[i].getActivePage(); if (page != null)
						 * mainDiagraphControler.wipeActions(st,page);
						 * windows[i].addPerspectiveListener(st); }
						 */

					}



				return Status.OK_STATUS;
		}
		};

		startupUIJob.setUser(false);
		startupUIJob.schedule(2000);//FP150324init

	}


	@Override
	public void earlyStartup() {
		if (LOG)
			clog("************* org.isoe.fwk.actions Startup **************");
		final IWorkbench workbench = PlatformUI.getWorkbench();
		final Startup thisObject = this;
		if (!isDevMode()) { // FP141225

			runStartupUIJob();

			if (false) workbench.getDisplay().asyncExec(new Runnable() {
				public void run(){
					sleep(500);//TODO schedule job //FP150322 delay
					IDiagraphControler mainDiagraphControler = null;
					try {

						// Connector.exploreServices();
						clog("findPerspectiveWithId() "
								+ GenericConstants.DIAGRAPH_PERSPECTIVE_ID);

						openPerspective(
								GenericConstants.DIAGRAPH_PERSPECTIVE_ID,
								ResourcesPlugin.getWorkspace()); // FP130627

						IPerspectiveDescriptor persDescription1_ = PlatformUI
								.getWorkbench()
								.getPerspectiveRegistry()
								.findPerspectiveWithId(
										GenericConstants.DIAGRAPH_PERSPECTIVE_ID);

						PlatformUI.getWorkbench().getActiveWorkbenchWindow()
								.getActivePage()
								.setPerspective(persDescription1_);
						clog("findAndShowView() "
								+ GenericConstants.DIAGRAPH_BASIC_CONTROLER_ID_);

						IViewPart vp = findAndShowView(GenericConstants.DIAGRAPH_BASIC_CONTROLER_ID_);
						if (vp != null) {
							vp.setFocus();
							mainDiagraphControler = (IDiagraphControler) vp;
							clog("mainDiagraphControler.startup() ");
							mainDiagraphControler.startup(getBasePath());
						} else
							clog("Diagraph Perspective not found !!!"); // FP130626

					} catch (Exception e) {

						System.err
								.println("error while showing Diagraph Perspective !!!");
					}

					if (mainDiagraphControler != null) {

						for (IWorkbenchWindow wind : PlatformUI.getWorkbench()
								.getWorkbenchWindows()) {
							IWorkbenchPage page = wind.getActivePage();
							if (page != null)
								mainDiagraphControler.wipeActions(thisObject,
										page);
							wind.addPerspectiveListener(thisObject);
						}

						mainDiagraphControler.earlyStartup();
						/*
						 * IWorkbenchWindow[] windows =
						 * PlatformUI.getWorkbench() .getWorkbenchWindows(); for
						 * (int i = 0; i < windows.length; i++) { IWorkbenchPage
						 * page = windows[i].getActivePage(); if (page != null)
						 * mainDiagraphControler.wipeActions(st,page);
						 * windows[i].addPerspectiveListener(st); }
						 */

					}

				}

			});
			//sleep(1000); //FP150222
		} else
			wipeAll();
	}

	private void sleep(int timeout) {
		if (Display.getCurrent() == null)
			return;
		final long start = System.currentTimeMillis();
		final long deltaMillis = timeout;
		do {
			while (Display.getCurrent().readAndDispatch())
				;
		} while ((System.currentTimeMillis() - start) < deltaMillis);
	}



	public void wipeAll() {
		IWorkbenchWindow[] windows = PlatformUI.getWorkbench()
				.getWorkbenchWindows();
		for (int i = 0; i < windows.length; i++) {
			IWorkbenchPage page = windows[i].getActivePage();
			if (page != null) {
				wipeActions(page);
			}
			windows[i].addPerspectiveListener(this);
		}
	}

	@Override
	public void wipeActions(final IWorkbenchPage page) {
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				// remove the run menu
				Menu menu = page.getWorkbenchWindow().getShell().getMenuBar();
				for (MenuItem item : menu.getItems()) {
					if (item.getText().equals("Diagraph")
							|| item.getText().equals("Sample &Menu")
							|| item.getText().equals("Sample Menu")

					) {
						item.dispose();

					}
				}

				for (int i = 0; i < ACTIONS_2_WIPE.length; i++) {
					page.hideActionSet(ACTIONS_2_WIPE[i]);
				}
			}
		});
	}

	public Shell getShell() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
	}

	public void showMessage(String message) {
		MessageDialog.openInformation(getShell(),
				"Deploy Megamodel on Startup", message);
	}

	public String getCurrentLayer() {
		return ViewConstants.DIAGRAPH_DEFAULT;
	}

	public int getViewId() {
		return 0;
	}

	public void log(String mesg) {
		if (LOG)
			System.out.println("STARTUP " + mesg);
	}

	@Override
	public void perspectiveActivated(IWorkbenchPage page,
			IPerspectiveDescriptor perspective) {
		IViewPart probs = findAndShowView("org.eclipse.ui.view.ProblemView"); // FP140603

		IViewPart vp = findAndShowView(GenericConstants.DIAGRAPH_BASIC_CONTROLER_ID_);
		if (vp != null) {
			IDiagraphControler mainDiagraphControler = (IDiagraphControler) vp;
			mainDiagraphControler.wipeActions(this, page);
		}

		// wipeActions(page);
	}

	@Override
	public void perspectiveChanged(IWorkbenchPage page,
			IPerspectiveDescriptor perspective, String changeId) {
		IViewPart vp = findView(GenericConstants.DIAGRAPH_BASIC_CONTROLER_ID_);
		if (vp != null) {

			IDiagraphControler mainDiagraphControler = (IDiagraphControler) vp;
			mainDiagraphControler.perspectiveChanged(perspective,
					perspective.getId(), changeId);
			;
		}
	}

}
