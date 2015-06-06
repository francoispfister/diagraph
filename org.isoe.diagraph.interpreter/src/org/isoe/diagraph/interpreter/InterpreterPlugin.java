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
package org.isoe.diagraph.interpreter;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.isoe.fwk.jobs.IErrorLogger;
import org.osgi.framework.BundleContext;

/**
 *
 * @author fpfister
 *
 */
public class InterpreterPlugin extends Plugin implements IErrorLogger {
	// org.isoe.fwk.diagen.DiagenPlugin
	// The plug-in ID

	public static final String PLUGIN_ID = "org.isoe.diagraph.interpreter";

	// The shared instance
	private static InterpreterPlugin plugin;
	private String errors = "";

	/**
	 * The constructor
	 */
	public InterpreterPlugin() {
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static InterpreterPlugin getInstance() {
		return plugin;
	}

	public void logError(String error) {
		logError(error, null);
	}

	public void logError(String error, Throwable throwable) {
		if (error == null && throwable != null) {
			error = throwable.getMessage();
		}
		getLog().log(
				new Status(IStatus.ERROR, InterpreterPlugin.PLUGIN_ID, IStatus.OK,
						error, throwable));
	}

	public void logInfo(String message) {
		logInfo(message, null);
	}

	public void logInfo(String message, Throwable throwable) {
		if (message == null && throwable != null) {
			message = throwable.getMessage();
		}
		getLog().log(
				new Status(IStatus.INFO, InterpreterPlugin.PLUGIN_ID, IStatus.OK,
						message, throwable));
	}

	@Override
	public void log(IStatus status) {
		Platform.getLog(this.getBundle()).log(status);
	}



	public void addError(String mesg) {
		errors+=";"+mesg;
	}

	public String getErrors() {
		return errors;
	}

	public void setErrors(String errors) {
		this.errors = errors;
	}




}
