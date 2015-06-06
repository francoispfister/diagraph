package org.isoe.diagraph.templateaction.actions;

/*
 * Copyright (c) 2006, 2010 Borland Software Corporation and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Dmitry Stadnik (Borland) - initial API and implementation
 *    Artem Tikhomirov (independent) - [304421] allow code generation to run in background
 *    Francois Pfister (LGi2P) - adaptation to Diagraph (never show confirmation dialog)
 */
//package org.eclipse.gmf.internal.codegen.popup.actions;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gmf.codegen.gmfgen.GenDiagram;
import org.eclipse.gmf.codegen.gmfgen.GenEditorGenerator;
import org.eclipse.gmf.codegen.util.Generator;
import org.eclipse.gmf.internal.bridge.transform.ValidationHelper;
import org.eclipse.gmf.internal.codegen.CodeGenUIPlugin;
import org.eclipse.gmf.internal.codegen.popup.actions.DiagnosticsDialog;
import org.eclipse.gmf.internal.common.codegen.GeneratorBase;
import org.eclipse.gmf.internal.common.migrate.ModelLoadHelper;
import org.eclipse.gmf.internal.xpand.Activator;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.IProgressConstants;
import org.isoe.fwk.core.DParams;

/**
 * Operation that processes templates to generate diagram editor.
 * Clients should call run() method to invoke it.
 * Configured operation should normally run without interacting
 * with the user so it could be used as part of diagram editor
 * build process.
 *
 * @author dstadnik
 * @author fpfister - adaptation to Diagraph (never show confirmation dialog)
 */
public class ExecuteTemplatesOperation {

	private static final String ASK_OK = "ask_ok"; //$NON-NLS-1$

	protected static final boolean LOG = DParams.ExecuteTemplatesOperation_LOG;

	private String name;

	private Shell shell_;

	private URI genModelURI;

	private GenEditorGenerator myGenModel;



	private void clog(String mesg) {
		if (LOG)
			 System.out.println(mesg);
	}

	public String getName() {
		if (name == null) {
			return "Execute Templates"; //$NON-NLS-1$
		}
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Shell getShell() {
		if (shell_ == null) {
			return Display.getDefault().getActiveShell();
		}
		return shell_;
	}

	public void setShell(Shell shell) {
		this.shell_ = shell;
	}

	public URI getGenModelURI() {
		return genModelURI;
	}

	public void setGenModelURI(URI uri) {
		genModelURI = uri;
	}

	// TODO Jobs
	public void run() {
		if (getGenModelURI() == null) {
			return;
		}
		Diagnostic loadStatus = loadGenModel();
		if (!canProcessGMFGenModel(loadStatus)) {
			return;
		}

		assert getGenModel() != null;
		Diagnostic isGenModelValid = validateGenModel();
		if (!ValidationHelper.isOK(isGenModelValid)) {
			final String msg = CodeGenUIPlugin.getBundleString("generatecode.badsrc"); //$NON-NLS-1$
			if (DiagnosticsDialog.openProceedCancel(getShell(), getName(), msg, isGenModelValid) == IDialogConstants.CANCEL_ID) {
				return;
			}
		}

		if (!PlatformUI.getWorkbench().saveAllEditors(true)) {
			return;
		}
		//to serialize

		final Job job = new Job(getName()) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					IStatus s = ExecuteTemplatesOperation.this.run(monitor);
					if (!s.isOK()) {
						return s;
					}
					// usually, ok status has just "OK" text. Since we put this into a job, visible in the progress view, better label needed
					return new Status(IStatus.OK, s.getPlugin(), CodeGenUIPlugin.getBundleString("generatecode.ok")); //$NON-NLS-1$
				} catch (InterruptedException ex) {
					return Status.CANCEL_STATUS;
				}catch (Exception ex) {
					return Status.CANCEL_STATUS; //FP140616voir2
				}
			}

			@Override
			public boolean belongsTo(Object family) {
				if (family instanceof Job) {
					return family.getClass().equals(this.getClass());
				}
				return false;
			}
		};
		job.setUser(true);
		job.setProperty(IProgressConstants.KEEPONE_PROPERTY, true);
		job.setProperty(IProgressConstants.ACTION_PROPERTY, new Action() {
			@Override
			public void run() {
				IStatus runStatus = job.getResult();
				if (runStatus == null) {
					return;
				}
				if (runStatus.isOK()) {
					//showOk(true);
					if (LOG)
						clog("(1) generation ok"); //FP140304
				} else if (runStatus.matches(IStatus.ERROR)) {
					//System.err.println();
					ErrorDialog.openError(getShell(), getName(), CodeGenUIPlugin.getBundleString("generatecode.err"), runStatus); //$NON-NLS-1$
				} else if (runStatus.matches(IStatus.WARNING)) {
					ErrorDialog.openError(getShell(), getName(), CodeGenUIPlugin.getBundleString("generatecode.warn"), runStatus); //$NON-NLS-1$
				} else if (runStatus.matches(IStatus.INFO)) {
					ErrorDialog.openError(getShell(), getName(), CodeGenUIPlugin.getBundleString("generatecode.info"), runStatus); //$NON-NLS-1$
				}
			}


		});
		job.addJobChangeListener(new JobChangeAdapter() {

			@Override
			public void done(IJobChangeEvent event) {
				unloadGenModel();
				//
				IStatus runStatus = event.getResult();
				if (runStatus.isOK() && Boolean.TRUE.equals(event.getJob().getProperty(IProgressConstants.PROPERTY_IN_DIALOG))) {
					if (LOG)
						clog("(2) generation ok"); //FP140304
				} else if (runStatus.matches(IStatus.ERROR)) {
					CodeGenUIPlugin.getDefault().getLog().log(runStatus);
				}
			}
		});
		job.schedule();
	}

	private void showOk(boolean force) {
		final boolean neverShowAgain = MessageDialogWithToggle.ALWAYS.equals(getPreferences().getString(ASK_OK));
		if (force || !neverShowAgain) {
			final String okMsg = CodeGenUIPlugin.getBundleString("generatecode.ok"); //$NON-NLS-1$
			final String neverMsg = CodeGenUIPlugin.getBundleString("generatecode.neveragain"); //$NON-NLS-1$
			Runnable r = new Runnable() {
				public void run() {
					MessageDialogWithToggle dlg = MessageDialogWithToggle.openInformation(getShell(), getName(), okMsg, neverMsg, neverShowAgain, getPreferences(), ASK_OK);
					if (!dlg.getToggleState()) {
						// Unfortunately, MessageDialogWithToggle doesn't support clearing 'ask me again' option once set
						// @see #buttonPressed - preferences are modified only when toggleState == true
						// Hence, need to clear it manually
						getPreferences().setValue(ASK_OK, MessageDialogWithToggle.PROMPT);
					}
				}
			};
			if (Display.getCurrent() != null) {
				r.run();
			} else {
				Display.getDefault().asyncExec(r);
			}
		}
	}

	/**
	 * Checks if loaded gmfgen model can be processed further.
	 * <p>
	 * Note: Must be called after {@link #loadGenModel()}.
	 *
	 * @param loadStatus
	 *            the result status of loading the gmfgen model
	 * @param action
	 *            the action in execution
	 * @return <code>true</code> if gmfgen model is available with OK status or in case of load problems, user decided to proceed, <code>false</code> otherwise.
	 */
	private boolean canProcessGMFGenModel(Diagnostic loadStatus) {
		if (!ValidationHelper.isOK(loadStatus)) {
			boolean disableProceed = myGenModel == null; // we cannot proceed further as there is no gmfgen, disable proceed button
			if (IDialogConstants.CANCEL_ID ==
				DiagnosticsDialog.openProceedCancel(getShell(), getName(), null, loadStatus, disableProceed)) {
				return false;
			}
		}
		return true;
	}

	protected IStatus run(IProgressMonitor monitor) throws InterruptedException {
		GeneratorBase g = createGenerator();
		g.run(monitor);
		return g.getRunStatus();
	}

	protected GeneratorBase createGenerator() {
		return new Generator(getGenModel(), CodeGenUIPlugin.getDefault().getEmitters(getGenModel()));
	}

	protected final GenEditorGenerator getGenModel() {
		return myGenModel;
	}

	private Diagnostic loadGenModel() {
		ResourceSet srcResSet = new ResourceSetImpl();
		srcResSet.getURIConverter().getURIMap().putAll(EcorePlugin.computePlatformURIMap());
		Activator.fillWorkspaceMetaModelsMap(srcResSet.getPackageRegistry());
		ModelLoadHelper loadHelper = new ModelLoadHelper(srcResSet, getGenModelURI());
		Object root = loadHelper.getContentsRoot();
		if (root instanceof GenDiagram) {
			myGenModel = ((GenDiagram) root).getEditorGen();
		} else if (root instanceof GenEditorGenerator) {
			myGenModel = (GenEditorGenerator) root;
		}
		if (myGenModel != null && myGenModel.getDomainGenModel() != null) {
			myGenModel.getDomainGenModel().reconcile();
		}
		return ValidationHelper.createResourceProblemMarkers(loadHelper.getDiagnostics());
	}

	private void unloadGenModel() {
		if (myGenModel != null && myGenModel.eResource() != null) {
			myGenModel.eResource().unload();
		}
		myGenModel = null;
	}

	private Diagnostic validateGenModel() {
		return ValidationHelper.validate(getGenModel(), true);
	}

	private static IPreferenceStore getPreferences() {
		return CodeGenUIPlugin.getDefault().getPreferenceStore();
	}
}
