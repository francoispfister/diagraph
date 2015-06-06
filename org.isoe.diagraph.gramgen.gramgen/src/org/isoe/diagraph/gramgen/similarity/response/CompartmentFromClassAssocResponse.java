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
package org.isoe.diagraph.gramgen.similarity.response;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;

/**
 * 
 * @author bnastov
 *
 */
public class CompartmentFromClassAssocResponse extends CompartmentResponse {

	public CompartmentFromClassAssocResponse(EClass container, EClass contenu,
			EReference include) {
		super(container, contenu, include);
		// TODO Auto-generated constructor stub
	}

}
