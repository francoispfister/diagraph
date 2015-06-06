/*
 * Copyright (c) 2005, 2007 Borland Software Corporation
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Radek Dvorak (Borland) - initial API and implementation
 *           Artem Tikhomirov (Borland) - complete reimplementation with uriFragments
 */
package org.isoe.diagraph.gen.gmf.util;



import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

/**
 * Utility class providing easy access and navigation to 
 * ecore metamodel elements by using paths.
 * @author artem
 */
public final class EPath {
	private final Resource myLookupResource;

	public EPath(Resource lookupResource) {
		assert lookupResource != null;
		myLookupResource = lookupResource;
	}
	
	public static final EPath createEcorePathFromModel(URI uri) {
		return new EPath(new ResourceSetImpl().getResource(uri, true));
	}
	
	
	public <T> T lookup(String uriFragment, Class<T> type) {
		EObject target = myLookupResource.getEObject(uriFragment);
		//Assert.assertNotNull("Check uriFragment to point to existing element " + uriFragment, target);
		//Assert.assertTrue(type.isInstance(target));			
		return type.cast(target);
	}

	// utility

	public EClass findClass(String uriFragment) {
		return lookup(uriFragment, EClass.class);
	}

	public EStructuralFeature findFeature(String uriFragment) {
		return lookup(uriFragment, EStructuralFeature.class);
	}

	public EReference findReference(String uriFragment) {
		return lookup(uriFragment, EReference.class);
	}

	public EAttribute findAttribute(String uriFragment) {
		return lookup(uriFragment, EAttribute.class);
	}
}
