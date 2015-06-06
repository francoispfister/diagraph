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
package org.isoe.diagraph.parser;

import org.isoe.diagraph.parser.api.IDiagraphElement;
import org.isoe.diagraph.parser.api.IDiagraphMapping;
import org.isoe.fwk.core.DParams;

/**
 *
 * @author fpfister
 *
 */
public class DiagraphMapping implements IDiagraphMapping{

	private static final boolean LOG = DParams.DiagraphMapping_LOG;
	private Object mapping;
	private Object child;
	private int depth;
	private String info;
	IDiagraphElement element;
	private int id ;

   private void clog(String mesg){
	   if (LOG)
		   System.out.println(mesg);
   }


	@Override
	public int getDepth() {
		return depth;
	}

	void setDepth(int depth) {
		this.depth = depth;
	}

	@Override
	public String getInfo() {
		return info;
	}

	void setInfo(String info) {
		this.info = info;
	}


	public DiagraphMapping(IDiagraphElement element, Object mapping, Object child, int id,int depth, String info) {
		this.element=element;
		this.mapping = mapping;
		this.child = child;
		this.depth = depth;
		this.id = id;
		this.info = info;
		this.element.addMapping(this);
	}

	@Override
	public String toString(){
		return info +  " [id="+id+" element.depth="+element.getDepth()+ " depth="+depth+"]";
	}


	@Override
	public Object getMapping() {
		return mapping;
	}

	void setMapping(Object mapping) {
		this.mapping = mapping;
	}

	@Override
	public Object getChild() {
		return child;
	}

	void setChild(Object child) {
		this.child = child;
	}


}
