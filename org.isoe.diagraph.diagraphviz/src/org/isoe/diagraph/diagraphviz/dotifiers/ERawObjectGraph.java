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
package org.isoe.diagraph.diagraphviz.dotifiers;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.isoe.extensionpoint.graphviz.IDotifier;

public class ERawObjectGraph extends EObjectGraph {

	public ERawObjectGraph(ResourceSet rs, String inputFile, String outputFile,
			String dotOutputPath, IDotifier handler) {
		super(rs, inputFile, outputFile, dotOutputPath, handler);
		// TODO Auto-generated constructor stub
	}

}
