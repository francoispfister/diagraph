/*
 * Copyright (c) 2005, 2010 Borland Software Corporation and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Artem Tikhomirov (Borland) - initial API and implementation
 */
package org.isoe.fwk.utils.workspace;

import org.eclipse.swt.widgets.Display;

/**
 * @author artem
 * 
 */
public class MessageUtils {
	

	/**
	 * @return false if timeout broke the loop
	 */
	public static boolean dispatchDisplayMessages(boolean[] condition,
			int timeoutSeconds) {
		if (Display.getCurrent() == null)
			return true; //FP120522a
		final long start = System.currentTimeMillis();
		final long deltaMillis = timeoutSeconds * 1000;
		do {
			while (Display.getCurrent().readAndDispatch()) {
				;
			}
		} while (condition[0]
				&& (System.currentTimeMillis() - start) < deltaMillis);
		return !condition[0];
	}
	

	/**
	 * @return false if message re-dispatch was broken by timeout
	 */
	public static boolean dispatchDisplayMessages(int timeoutSeconds) {
		if (Display.getCurrent() == null)
			return true; //FP120522a
		final long start = System.currentTimeMillis();
		final long deltaMillis = timeoutSeconds * 1000;
		while (Display.getCurrent().readAndDispatch()) {
			if ((System.currentTimeMillis() - start) > deltaMillis) {
				return false;
			}
		}
		return true;
	}

	public static void assertDispatchDisplayMessages(int timeoutSeconts) {
		boolean queueCleared = dispatchDisplayMessages(3);
	}

	public static void assertDispatchDisplayMessages(boolean[] condition,
			int timeoutSeconds) {
		boolean conditionSatisfied = dispatchDisplayMessages(condition,
				10);
	}

}
