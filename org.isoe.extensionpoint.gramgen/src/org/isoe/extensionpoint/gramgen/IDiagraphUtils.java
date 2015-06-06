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
package org.isoe.extensionpoint.gramgen;

import org.eclipse.emf.ecore.EClass;

/**
 *
 * @author pfister
 *
 */
import org.isoe.diagraph.controler.IDiagraphControler;  //FP140707refactored
public interface IDiagraphUtils {
	//TODO  restructure
	IDiagraphControler getControler();
	String addDetailToDiagraphAnnotation(EClass parent, String view, String key);
	//void createNodeAnnotation(String al, String pov, int povid, EClass eclaz,
	//		int level);
}
