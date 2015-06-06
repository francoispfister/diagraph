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
package org.isoe.diagraph.internal.m2.api;

import org.isoe.diagraph.internal.api.IDiaNamedElement;
import org.isoe.diagraph.internal.api.IDiaNode;
import org.isoe.diagraph.internal.api.IDiaSyntaxElement;


/**
 *
 * @author pfister
 *
 */

public interface IDiaEdge extends IDiaNamedElement, IDiaSyntaxElement{
   IDiaNode getSourceNode(); //FP120620
   IDiaNode getTargetNode();
   String getType();
   String asString();
   void setOriented_(boolean value);
   boolean getOriented_();
}
