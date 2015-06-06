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
package org.isoe.diagraph.internal.api;

import java.util.List;

import org.isoe.diagraph.runner.IDiagraphRunner;
import org.isoe.diagraph.common.*;






/**
 *
 * @author pfister
 *
 */
public interface IDiaPlatformDelegateProvider {

	IDiaPlatformDelegate createSpecificPlatform(IDiaNamedElement dNamedElement);
	IDiagraphRunner getRunner();
	//List<IDiaPointOfView> getPointsOfView();
	IDiaPointOfView getPointOfView(); //FP140617voir ok ici
	void cerror(String mesg);

}
