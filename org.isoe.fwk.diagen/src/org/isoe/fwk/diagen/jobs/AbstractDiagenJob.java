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
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.diagen.DiagenPlugin;

/**
 * Some helper methods for jobs.
 *
 * @author jmwright (iaml)
 * @author fpfister (adaptation to diagraph)
 *
 */
public abstract class AbstractDiagenJob extends Job {
	static final boolean LOG = DParams.AbstractDiagenJob_LOG;
	private IProject project;
	protected boolean headless;
	private String domain;

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

	public AbstractDiagenJob(String name, boolean headless) {
		super(name);
		this.headless = headless;
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


	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
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
					IStatus status = work(monitor , getName());
					if (status == null || !status.isOK()) {
						if (status == null)
							status = errorStatus("An exception occured: unknown error");
						System.err.println("error "+status.getMessage()+" in "+getName());
						DiagenPlugin.getInstance().getLog().log(status);


						// display error
						if (!headless)
						  ErrorDialog.openError(PlatformUI.getWorkbench()
								.getDisplay().getActiveShell(), getName(),
								status.getMessage(), status);
						else
							System.err.println(status.getMessage());
					} else
						if (LOG)
							clog("run ok");

				} catch (CoreException e) {
			//	} catch (Exception e) {
					IStatus error = new Status(Status.ERROR,
							DiagenPlugin.PLUGIN_ID, "An exception occured: "
									+ e.getMessage(), e);
					DiagenPlugin.getInstance().getLog().log(error);

					// display error
					if (!headless)
					 ErrorDialog.openError(PlatformUI.getWorkbench()
							.getDisplay().getActiveShell(), getName(),
							error.getMessage(), error);
					else
						System.err.println(error.getMessage());
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
	protected abstract IStatus work(IProgressMonitor monitor, String id)
			throws CoreException;


}
