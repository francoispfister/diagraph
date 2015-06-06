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
package org.isoe.extensionpoint.xmi;



import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;

/**
 *
 * @author fpfister
 *
 */
import org.isoe.diagraph.controler.IDiagraphControler;  //FP140707refactored
public interface IMetamodelRetriever {
//org.isoe.extensionpoint.xmi.IMetamodelRetriever
	IFile getCurrentFile();
	String getMetamodelBase();
	String getDomain();
	String getNsPrefix();
	String getNsURI();
	EPackage getCurrentPackage();
	void setCurrentFile(IFile cfile);
	void markUnregisteredModels(IProgressMonitor progressMonitor);
	boolean isInterrupted();
	EPackage findRegisteredMetamodel(URI uri, IProgressMonitor progressMonitor);
	EPackage findRegisteredMetamodel(IFile file);
	boolean isQualified();
	boolean isStub();
	void setSilent(boolean silent);

	void setControler(IDiagraphControler controler);

}
