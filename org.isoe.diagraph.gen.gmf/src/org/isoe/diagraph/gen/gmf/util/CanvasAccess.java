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

package org.isoe.diagraph.gen.gmf.util;


import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gmf.gmfgraph.ChildAccess;
import org.eclipse.gmf.gmfgraph.FigureDescriptor;
import org.eclipse.gmf.gmfgraph.Label;
import org.eclipse.gmf.gmfgraph.PolylineConnection;

/**
 *
 * @author pfister
 *
 */
public final class CanvasAccess {
	private final Resource resource;
	public static final boolean LOG = false;

	public CanvasAccess(Resource resource) {
		assert resource != null;
		this.resource = resource;
	}

	public static final CanvasAccess createEcorePathFromModel(URI uri) {
		return new CanvasAccess(new ResourceSetImpl().getResource(uri, true));
	}

	public <T> T lookup(String uriFragment, Class<T> type) {
		EObject target = resource.getEObject(uriFragment);
		assert type.isInstance(target);
		return type.cast(target);
	}

	public EObject findObject(String uriFragment){
		EObject result = resource.getEObject(uriFragment);
		if (LOG)
			System.out.println("=== "+result.getClass().getName()+":"+result.toString());
		return result;
	}

	public PolylineConnection findPolylineConnection(String uriFragment) {
		return lookup(uriFragment, PolylineConnection.class);
	}

	public Label findLabel(String uriFragment) {
		return lookup(uriFragment, Label.class);
	}

	public ChildAccess findChildAccess(String uriFragment) {
		return lookup(uriFragment, ChildAccess.class);
	}

	public FigureDescriptor findFigureDescriptor(String uriFragment) {
		return lookup(uriFragment, FigureDescriptor.class);
	}




}
