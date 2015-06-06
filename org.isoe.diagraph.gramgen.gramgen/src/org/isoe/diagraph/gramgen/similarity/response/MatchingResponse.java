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

/**
 * 
 * @author bnastov
 *
 */
public interface MatchingResponse {
	/**
	 * 1 for labeled association
	 * 2 for compartment
	 * 3 for reference
	 * 4 for canvas
	 *
	 * 
	 * @return
	 */
	public int getResponseType();
}
