 /**
 * Copyright (c) 2014 Laboratoire de Genie Informatique et Ingenierie de Production - Ecole des Mines d'Ales
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Blazo Nastov (ISOE-LGI2P) - initial API and implementation
 */
package org.isoe.extensionpoint.remoting;

import org.eclipse.emf.ecore.EPackage;
import org.isoe.extensionpoint.xmi.IMetamodelRetriever;


/**
 *
 * @author bnastov
 *
 */
import org.isoe.extensionpoint.diagraph.action.DiagraphService;  //FP140707_c_refactored
public interface IDiagraphRemotingService extends DiagraphService {
	void setModelDescription(String description);
	void setSave();
	void setLoad();
	void setPackage(EPackage packag);
	void setModelName(String name);
	void setModelNsPrefix(String nsPrefix);
	void setModelUri(String nsUri);
	void setMetamodelRetriever(IMetamodelRetriever metamodelRetriever);
}
