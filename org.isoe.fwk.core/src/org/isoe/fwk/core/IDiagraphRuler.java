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
package org.isoe.fwk.core;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;



/**
 *
 * @author fpfister
 *
 */
public interface IDiagraphRuler { //TODO remove

	EPackage getDomainModel();
	boolean isNodeInstanciable(EClass c);
	boolean isLinkInstanciable(EClass c);
	boolean isView(EClass c);
	boolean isNode(EClass c);
	boolean isLink(EClass c);
	String getViewId();

}
