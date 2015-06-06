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
public class ReferenceResponse implements MatchingResponse{
	private EClass source;
	private EClass target;
	private EReference ref;
	
	public ReferenceResponse(EClass source, EClass target, EReference ref){
		this.source = source;
		this.target = target;
		this.ref = ref;
	}

	public EClass getSource() {
		return source;
	}

	public EClass getTarget() {
		return target;
	}

	public EReference getRef() {
		return ref;
	}

	@Override
	public int getResponseType() {
		// TODO Auto-generated method stub
		return 3;
	}
	
	
}
