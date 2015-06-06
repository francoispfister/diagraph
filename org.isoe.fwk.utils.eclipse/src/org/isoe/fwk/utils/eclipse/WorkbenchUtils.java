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
package org.isoe.fwk.utils.eclipse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.isoe.fwk.core.DParams;

/**
 *
 * @author pfister
 *
 */
public class WorkbenchUtils {

	private static final boolean LOG = DParams.WorkbenchUtils_LOG;

	// org.isoe.fwk.eclipse.utils.WorkbenchUtils

	/**
	 *
	 * @param extensionPoint
	 *            example: org.eclipse.ui.popupMenus
	 * @param action
	 *            example: gmf.codegen.ui.executeTemplatesAction
	 * @return action found or null
	 * @throws CoreException
	 */
	public static IObjectActionDelegate getAction(String extensionPoint,
			String action) {
		IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		IConfigurationElement[] configurationElements = extensionRegistry
				.getConfigurationElementsFor(extensionPoint);
		for (IConfigurationElement configurationElement : configurationElements) {
			String cfgid = configurationElement.getAttribute("id");
			if (cfgid!=null && LOG) //FP131124
			   clog("WBUe "+cfgid);
			IConfigurationElement[] children = configurationElement
					.getChildren();
			for (IConfigurationElement child : children) {
				String id = child.getAttribute("id");
				if (id!=null && LOG)
				   clog("WBUef    "+id);
				if (action.equals(id)) {
					try {
						return (IObjectActionDelegate) child
								.createExecutableExtension("class");
					} catch (CoreException e) {
						//e.printStackTrace();
						return null;
					}
				}
			}
		}
		return null;
	}

	private static void clog(String mesg) {
		if (LOG)
		 System.out.println(mesg);

	}

	public static Display getDisplay() {
		Display dp = Display.getCurrent();
		if (dp == null)
			dp = Display.getDefault();
		return dp;
	}

	public static Object syncExec(Runnable runnable) {
		final Object[] obj = new Object[1];
		getDisplay().syncExec(runnable);
		return obj;
	}

	public static IWorkbenchPage getActivePage() { // FP130130
		IWorkbenchWindow window = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		if (window == null) {
			return null;
		}
		return window.getActivePage();
	}

	public static IViewPart findView(final String viewID) {
		final IViewPart[] retContainer = new IViewPart[1];
		syncExec(new Runnable() {
			public void run() {
				IWorkbenchPage page = getActivePage();
				if (null == page)
					return;
				retContainer[0] = page.findView(viewID);
			}
		});

		return retContainer[0];
	}

	public static IViewPart showNavigator() { // FP130130 err here
		IViewPart navigator = null;
		try {
			navigator = findView("org.eclipse.jdt.ui.PackageExplorer"); // FP130130x
			if (navigator == null)
				navigator = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage()
						.showView("org.eclipse.jdt.ui.PackageExplorer");

		} catch (PartInitException e1) {
			e1.printStackTrace();
			return null;
		}
		return navigator;
	}

	////org.isoe.fwk.eclipse.utils.WorkbenchUtils.getPlatformVersion()
	public static String getPlatformVersion() {
		String version = null;

		try {
			java.util.Dictionary dictionary = org.eclipse.ui.internal.WorkbenchPlugin
					.getDefault().getBundle().getHeaders();
			version = (String) dictionary.get("Bundle-Version"); //$NON-NLS-1$
		} catch (NoClassDefFoundError e) {
			version = getProductVersion();
		}

		return version;
	}

	public static String getProductVersion() {
		String version = null;

		try {
			// this approach fails in "Rational Application Developer 6.0.1"
			IProduct product = Platform.getProduct();
			String aboutText = product.getProperty("aboutText"); //$NON-NLS-1$

			String pattern = "Version: (.*)\n"; //$NON-NLS-1$
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(aboutText);
			boolean found = m.find();

			if (found) {
				version = m.group(1);
			}
		} catch (Exception e) {

		}

		return version;
	}

	public static IActionDelegate getAction2(String extensionPoint,
			String action) {
		IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		IConfigurationElement[] configurationElements = extensionRegistry
				.getConfigurationElementsFor(extensionPoint);
		for (IConfigurationElement configurationElement : configurationElements) {
			String cfgid = configurationElement.getAttribute("id");
			if (cfgid!=null && LOG) //FP131124
			   clog("WBUeg "+cfgid);
			IConfigurationElement[] children = configurationElement
					.getChildren();
			for (IConfigurationElement child : children) {
				String id = child.getAttribute("id");
				if (id!=null && LOG)
				   clog("WBUeh    "+id);
				if (action.equals(id)) {
					try {
						return (IActionDelegate) child
								.createExecutableExtension("class");
					} catch (CoreException e) {
						//e.printStackTrace();
						return null;
					}
				}
			}
		}
		return null;
	}

}
