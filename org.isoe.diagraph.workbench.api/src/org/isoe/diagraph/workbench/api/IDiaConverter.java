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
package org.isoe.diagraph.workbench.api;

import org.eclipse.emf.ecore.resource.Resource;
import org.isoe.diagraph.diastyle.DStyle;

public interface IDiaConverter {

	int getPhase();

	String getView();

	Resource saveDgrf(String plugin, String povsuffix, String path);

	void convert(DStyle style);

	void setStyle(DStyle style);

}
