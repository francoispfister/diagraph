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
package org.isoe.fwk.deployer;

import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.resource.ImageDescriptor;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	private static BundleContext context;

	public static final String PLUGIN_ID = "org.isoe.fwk.deployer"; //$NON-NLS-1$

	private static Activator plugin;


	static BundleContext getContext() {
		return context;
	}


	public static String getPluginID() {
		return PLUGIN_ID;
	}

	public void start(BundleContext context) throws Exception {
	//	super.start(context);
		plugin = this;
		Activator.context = context;
	}

	public void stop(BundleContext context) throws Exception {
		plugin = null;
		Activator.context = null;
		//super.stop(context);
	}

	public static Activator getDefault() {
		return plugin;
	}
	/*
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}*/

	public void logerror(String mesg) {
		System.err.println(mesg);
	}

	public static URI createURI(String bundledFileName) {
		if (!bundledFileName.startsWith("/"))
			throw new RuntimeException("must start with /");
		 return URI.createPlatformPluginURI(PLUGIN_ID + bundledFileName, true);
	}



}
