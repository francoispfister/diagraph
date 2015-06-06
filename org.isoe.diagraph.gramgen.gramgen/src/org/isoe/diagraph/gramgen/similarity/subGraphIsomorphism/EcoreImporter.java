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
package org.isoe.diagraph.gramgen.similarity.subGraphIsomorphism;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;




/**
 * 
 * @author bnastov
 *
 */
public abstract class EcoreImporter {

	
	protected EPackage model;
	
	protected Graph graph;
	
	protected Vertex nodeTrue;
	
	protected Vertex nodeFalse;
	
//	protected Vertex node0;
	
//	protected Vertex node1;
	
//	protected Vertex nodeN;
	
	protected Map<EObject,Vertex> eltNodes = new HashMap<EObject, Vertex>();
	
	public String getInputFormat() {
		return "Ecore";
	}
	
	public static Resource loadMetamodel_(String metamodelPath) {
		ResourceSet metamodelSet = new ResourceSetImpl();
		metamodelSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION,new EcoreResourceFactoryImpl());
		URI metamodelURI = URI.createFileURI(new File(metamodelPath).getAbsolutePath());
		return metamodelSet.getResource(metamodelURI, true);
	}
}
