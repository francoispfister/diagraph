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
package org.isoe.fwk.extensions.providers;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.isoe.fwk.extensions.factories.DiagramOutlineFactory;

/**
 * 
 * @author pfister
 *
    insert call here:
    XXXDiagramEditor extends DiagramDocumentEditor
	public Object getAdapter ....
 */
public class DiagramOutlineProvider {
	
	
	public static DiagramOutlineFactory getOutlineFactory(String extension, String outlin) {
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IConfigurationElement[] extensions = reg.getConfigurationElementsFor(extension);
		if (extensions.length==0)
			System.out.println("extension point "+extension+" not found");
		
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement extelement = extensions[i];
			String ext_outl = extelement.getAttribute("implementation");
			//if (outlin.equals(ext_outl) ) 
			//return the first found
			{
				try {
					Object oo = extelement.createExecutableExtension("class");
					return (DiagramOutlineFactory) oo;
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	

	
}
