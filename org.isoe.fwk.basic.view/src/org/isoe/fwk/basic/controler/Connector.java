package org.isoe.fwk.basic.controler;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;


public class Connector {
//org.isoe.fwk.basic.controler.Connector.exploreServices(null);
	private static final boolean LOG = true;

	public static void exploreServices() {
		if (LOG) {
			System.out.println("exploring services ");
			IExtensionPoint[] eps = Platform.getExtensionRegistry()
					.getExtensionPoints();
			for (IExtensionPoint iExtensionPoint : eps) {

				String ns = iExtensionPoint.getNamespace();
				if (ns.startsWith("org.isoe")) {
					String epns=iExtensionPoint.getNamespaceIdentifier()+"."+iExtensionPoint.getSimpleIdentifier();
					System.out.println("ExtensionPoint:"+epns);
					String sident = iExtensionPoint.getSimpleIdentifier();

					//if (epns.equals(extensionName))
					 //  System.out.println("found");
					/*
					IExtension[] exts = iExtensionPoint.getExtensions();
					for (IExtension iExtension : exts) {
						String _nsi = iExtension.getNamespaceIdentifier();
						System.out.println("Extension:"+_nsi);
						//iExtension.
						tb = true;
					}*/

					for (IConfigurationElement cfElement : Platform.getExtensionRegistry()
							.getConfigurationElementsFor(epns)) {
						if (cfElement.getAttribute("class") != null) {
							try {
								Object found = cfElement.createExecutableExtension("class");
						        System.out.println(found.getClass().getName());
							} catch (org.eclipse.core.runtime.CoreException e) {
								e.printStackTrace();
							}
						}
					}


				}

			}
		}

	}



}
