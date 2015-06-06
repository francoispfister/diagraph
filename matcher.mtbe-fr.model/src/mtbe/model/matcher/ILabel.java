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
package mtbe.model.matcher;


/**
 * @model interface="true" abstract="true"
 */
public interface ILabel {
	/**
	 * @model kind="operation"
	 */
	String getStringValue();
	/**
	 * @model kind="operation"
	 */
	Object getValue();
	/**
	 * @model kind="operation"
	 */
	int getLove();
	
	/**
	 * @model kind="operation"
	 */	
	String[] getValues();
}
