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
package org.isoe.fwk.jobs;

import org.eclipse.core.runtime.IStatus;

/**
 * Allows the logging of errors.
 * 
 * @author jmwright
 *
 */
public interface IErrorLogger {

	/**
	 * Log the given error.
	 * 
	 * @param message A message to log
	 * @param e The root cause of the error
	 */
	public void logError(String message, Throwable e);

	/**
	 * Log the given error.
	 * 
	 * @param message A message to log
	 */
	public void logError(String message);

	/**
	 * Log the given multi status.
	 * 
	 * @param multi
	 */
	public void log(IStatus multi);

}
