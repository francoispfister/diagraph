/*******************************************************************************
 * Copyright (c) 2009 Mia-Software.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Gr�goire Dup� (Mia-Software)
 *******************************************************************************/
package org.isoe.fwk.utils.borrowed.mod;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;


/**
 * @author Gr�goire Dup� (Mia-Software)
 * 
 */
public final class Utils {
	private Plugin plugin;

	public Utils(final Plugin plugin) {
		this.plugin = plugin;
	}

	public EPackage getEcorePackage() {
		URI ecoreMmUri = URI.createURI("http://www.eclipse.org/emf/2002/Ecore"); //$NON-NLS-1$
		Resource ecoreMmResource = new ResourceSetImpl().getResource(
				ecoreMmUri, true);
		return (EPackage) ecoreMmResource.getContents().get(0);
	}

}
