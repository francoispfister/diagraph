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






/**
 * 
 * @author pfister
 *
 */
public interface IDiaPlatformDelegate {
	
	void addPreparedNode(Object node);
	List removePreparedNodes();
	List getPreparedNodes();
	
	void addPreparedCompartment(Object compartment);
	List removePreparedCompartments() ;
	List getPreparedCompartments();
	
	void addPreparedLabel(Object label);
	List removePreparedLabels();
	List getPreparedLabels();
	
	boolean checkHandled();
	
	
}
