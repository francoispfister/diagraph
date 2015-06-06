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
package org.isoe.fwk.platform.resolver;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * 
 * @author pfister
 * Usefull for tests outside Eclipse
 * Attention: uses class.getResource and EMFPlugin.IS_ECLIPSE_RUNNING, 
 * thus this plugin must be physically copied into the workspace, 
 * not just linked from another workspace
 * 
 */
public class Activator extends AbstractUIPlugin {

	// org.isoe.fwk.platform.resolver.Activator

	// The plug-in ID
	public static final String PLUGIN_ID = "org.isoe.fwk.platform.resolver"; //$NON-NLS-1$
	private static String workspacePath;

	// The shared instance
	private static Activator plugin;

	/**
	 * The constructor
	 */ 
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	

	public static String getWorkspacePath() {
		if (workspacePath == null) {
			String plugfp = getPluginFilePath();
			workspacePath = plugfp.substring(0,
					plugfp.lastIndexOf(File.separator) + 1);
		}
		return workspacePath;
	}

	public static String getPluginFilePath() {
		String furl = null;
		try {
			URL url_ = Activator.class.getResource(Activator.class.getSimpleName()+".class");
			//URL url = Activator.class.getResource("Activator.class");
			if (EMFPlugin.IS_ECLIPSE_RUNNING)
				furl = FileLocator.toFileURL(FileLocator.toFileURL(url_))
						.getFile();
			else{
				furl = url_.toString();
				if (furl.startsWith("file:/"))
					furl = furl.substring("file:/".length());
			}
			furl = furl.substring(0, furl.indexOf(Activator.PLUGIN_ID)
					+ Activator.PLUGIN_ID.length())
					+ "/";
			File f = new File(furl);
			return f.toString();
		} catch (IOException e) {
			throw new RuntimeException("error in getPluginFilePath");
		}
	}
}
