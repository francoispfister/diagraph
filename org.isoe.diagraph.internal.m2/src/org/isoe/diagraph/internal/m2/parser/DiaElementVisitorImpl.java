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
package org.isoe.diagraph.internal.m2.parser;

import org.isoe.diagraph.internal.m2.api.IDiaContainedElement;
import org.isoe.diagraph.internal.m2.api.IDiaElementVisitor;


/**
 * 
 * @author pfister
 *
 */
public abstract class DiaElementVisitorImpl implements IDiaElementVisitor {

	protected IDiaContainedElement element;
	protected Object object;
	
	public DiaElementVisitorImpl(IDiaContainedElement element) {
		this.element = element;
	}
	
	public IDiaContainedElement getElement() {
		return element;
	}

	public Object getObject(){
		return object;
	}
	
	public void visit(IDiaContainedElement superElement, IDiaContainedElement lowerElement, int depth){}


}
