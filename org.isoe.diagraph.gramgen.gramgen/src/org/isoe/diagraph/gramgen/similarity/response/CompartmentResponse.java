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
public class CompartmentResponse implements MatchingResponse{
	private EClass container;
	private EClass contenu;
	private EReference include;
	
	public CompartmentResponse(EClass container, EClass contenu, EReference include){
		this.container = container;
		this.contenu = contenu;
		this.include = include;
	}
	
	@Override
	public int getResponseType() {
		// TODO Auto-generated method stub
		return 2;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Container : " + container.getName() + " Contenu : " + contenu.getName();
	}

	public EClass getContainer() {
		return container;
	}

	public EClass getContenu() {
		return contenu;
	}

	public EReference getInclude() {
		return include;
	}
	
	
}
