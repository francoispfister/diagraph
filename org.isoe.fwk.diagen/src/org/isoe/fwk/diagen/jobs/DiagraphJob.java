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
/**
 * 
 */
package org.isoe.fwk.diagen.jobs;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.isoe.extensionpoint.diagen.ITextParser;
import org.isoe.fwk.diagen.DiagenPlugin;
import org.isoe.fwk.diagen.text.TextParser;

/**
 * @author fpfister 
 * 
 */
public abstract class DiagraphJob extends Job {
	private IProject project;
	private boolean headless;
	private String domain;
	private ITextParser textParser;

	public String getDomain() {
		return domain;
	}

	public IProject getProject() {
		return project;
	}

	public void doRun() {
		schedule();
		try {
			join();
		} catch (InterruptedException e) {
			System.err.println("AbstractDocJob interrupted ");
			e.printStackTrace();
		}
	}

	public void setProject(IProject project) {
		this.project = project;
		this.domain = project.getName();
		this.domain = domain.substring(
				domain.lastIndexOf(".")+1);
	}


	
	public DiagraphJob(String name, boolean headless) {
		super(name);
		this.headless = headless;
		this.textParser = new TextParser(null,true);
	}

	/**
	 * Construct an error IStatus with the given message.
	 */
	protected IStatus errorStatus(String message) {
		return new Status(IStatus.ERROR, DiagenPlugin.PLUGIN_ID, message);
	}

	/**
	 * Construct an error IStatus with the given throwable.
	 */
	protected IStatus errorStatus(Throwable e) {
		return new Status(IStatus.ERROR, DiagenPlugin.PLUGIN_ID,
				e.getMessage(), e);
	}

	/**
	 * Construct an error IStatus with the given message and throwable.
	 */
	protected IStatus errorStatus(String message, Throwable e) {
		return new Status(IStatus.ERROR, DiagenPlugin.PLUGIN_ID, message, e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.
	 * IProgressMonitor)
	 */
	@Override
	protected IStatus run(final IProgressMonitor monitor) {

		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				try {
					IStatus status = work(monitor);
					if (status == null || !status.isOK()) {
						if (status == null)
							status= new Status(IStatus.ERROR, DiagenPlugin.PLUGIN_ID, "An exception occured: unknown error");
						DiagenPlugin.getInstance().getLog().log(status);

						// display error
						ErrorDialog.openError(PlatformUI.getWorkbench()
								.getDisplay().getActiveShell(), getName(),
								status.getMessage(), status);
					}

				} catch (CoreException e) {
					IStatus error = new Status(Status.ERROR,
							DiagenPlugin.PLUGIN_ID, "An exception occured: "
									+ e.getMessage(), e);
					DiagenPlugin.getInstance().getLog().log(error);

					// display error
					ErrorDialog.openError(PlatformUI.getWorkbench()
							.getDisplay().getActiveShell(), getName(),
							error.getMessage(), error);
				}
			}

		});

		// can't get the real status yet
		return Status.OK_STATUS;

	}

	/**
	 * The actual implementation of the executable job.
	 * 
	 * @param monitor
	 * @return
	 */
	
	protected IStatus work(IProgressMonitor monitor)
			throws CoreException{
		/*
		String result = textParser.parseNotation(domain);
		if (result==null || result.equals("ok") || result.isEmpty())
		  return new Status(IStatus.OK, DiagenPlugin.PLUGIN_ID,"concrete syntax done");
		else*/
		return errorStatus("not implemented");
	}
}
