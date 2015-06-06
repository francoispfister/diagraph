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

import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecoretools.legacy.diagram.part.EcoreDiagramEditor;


/**
 *
 * @author pfister
 *
 */
import org.isoe.diagraph.controler.IDiagraphControler;  //FP140707refactored
public interface IAnnotationHelper {
	//TODO  restructure
	IDiagraphUtils getDiagraphUtils();
	IDiagraphControler getControler();
	EcoreDiagramEditor getEditor();
	Object getArguments();
}
