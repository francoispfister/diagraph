/*******************************************************************************
 * Copyright (c) 2011 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

//orig package org.eclipse.stem.jobs;
package org.isoe.fwk.jobs;


import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;

/**
 * Provides methods to safely get the SWT Display 
 * instance and invoke UI synchronous/asynchronous tasks
 * against the display.  If display is null - for example, when 
 * running in a system where there is no supported windowing system,
 * such as headless - then uses an alternative method for synchronous 
 * and asynchronous execution.
 * 
 */
public class DisplaySafeExecutor 
{
	private static Display display = null;
	private static boolean displayInit = false;
	
	/**
	 * Safely gets the default Display for this SWT instance, 
	 * catching all exceptions/errors thrown and checking for 
	 * disposal.  For a currently valid default display that has
	 * not been disposed, returns the instance of the default display.
	 * Otherwise, returns null;
	 * 
	 * @return The default SWT Display or null if no valid display exists
	 */
	public static Display safeGetDefaultDisplay()
	{
		return safeGetDefaultDisplay(false);
	}
	
	/**
	 * Same as {@link DisplaySafeExecutor#safeGetDefaultDisplay()} excepts 
	 * reinitializes the stored instance.  Can be used if the default display
	 * changes (is that possible?) or a display is disposed & recreated.
	 * 
	 * @param reinit Whether to update the stored static instance
	 * @return The default SWT Display or null if no valid display exists
	 */
	public synchronized static Display safeGetDefaultDisplay(boolean reinit)
	{
		if (reinit || (display == null && !displayInit)) {
			try {
				display = Display.getDefault();
			} catch (Throwable t) {
				// catch exception, ignore
			}
			
			displayInit = true;
		}
	
		if (display != null && display.isDisposed()) {
			display = null;
		}
		
		return display;
	}
	
	/**
	 * Runs a UI synchronous task (runnable) in the default Display UI thread or,
	 * if no valid display exists, runs as synchronous task against the
	 * current thread.
	 * 
	 * @see {@link Display#syncExec(Runnable)}
	 * @param runnable Runnable instance to invoke synchronously
	 */
	public static void executeSync(final Runnable runnable)
	{
		if (runnable == null) {
			return;
		}
		final Display d = safeGetDefaultDisplay();
		if (d != null) {
			d.syncExec(runnable);
		} else {
			runnable.run();
		}
	}
	
	/**
	 * Runs a UI asynchronous task (runnable) in the default Display UI thread or,
	 * if no valid display exists, runs as an asynchronous Eclipse job.
	 * 
	 * @see {@link Display#asyncExec(Runnable)}
	 * @see {@link Job}
	 * @param runnable Runnable instance to invoke synchronously
	 */
	public static void executeAsync(final Runnable runnable)
	{
		if (runnable == null) {
			return;
		}
		final Display d = safeGetDefaultDisplay();
		if (d != null) {
			d.asyncExec(runnable);
		} else {
			new Job(runnable.toString()) {		
				@SuppressWarnings("unused")
				protected IStatus run(IProgressMonitor monitor) {
					runnable.run();
					return Status.OK_STATUS;
				}
			}.schedule();
		}
	}
	
}