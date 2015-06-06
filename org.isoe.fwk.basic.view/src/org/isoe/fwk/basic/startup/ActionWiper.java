/*******************************************************************************
 * Copyright (c) 2010 - 2012 by Timotei Dolean <timotei21@gmail.com>
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
//origin package org.wesnoth.product;
package org.isoe.fwk.basic.startup;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveListener;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

//import org.wesnoth.Messages;

/**
 * A class that wipes irrelevant action
 */
public class ActionWiper implements IStartup, IPerspectiveListener {

	// org.isoe.fwk.basic.startup.ActionWiper
	private static final String[] ACTIONS_2_WIPE = new String[] {
			"org.isoe.diagraph.menu.actions.2", //$NON-NLS-1$
			"org.isoe.diagraph.menu.actions.3", //$NON-NLS-1$
			"org.isoe.diagraph.menu.actionSet", //$NON-NLS-1$
			"org.isoe.fwk.runtime.diagraph.actions" //$NON-NLS-1$
	};

	// not used
	/**
	 * <extension point="org.eclipse.ui.startup"> <startup
	 * class="org.isoe.fwk.basic.startup.ActionWiper"> </startup> </extension>
	 */

	@Override
	public void earlyStartup() {
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

	private void wipeActions(final IWorkbenchPage page) {
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
				for (int i = 0; i < ACTIONS_2_WIPE.length; i++)
					page.hideActionSet(ACTIONS_2_WIPE[i]);
			}
		});
	}

	@Override
	public void perspectiveActivated(IWorkbenchPage page,
			IPerspectiveDescriptor perspective) {
		wipeActions(page);
	}

	@Override
	public void perspectiveChanged(IWorkbenchPage page,
			IPerspectiveDescriptor perspective, String changeId) {
	}
}