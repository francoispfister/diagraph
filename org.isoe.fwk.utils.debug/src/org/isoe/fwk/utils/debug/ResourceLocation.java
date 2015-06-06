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
package org.isoe.fwk.utils.debug;

public interface ResourceLocation {
	
	public final static int RSRC_MODEL_ABSOLUTE = 0; 
	public final static int RSRC_MODEL_FOLDER = 1; 
	public final static int RSRC_MODEL = 2; 
	public final static int RSRC_WORKSPACE = 3; 
	public final static int RSRC_PROJECT = 4;
	public final static int RSRC_PLUGIN = 5; 	
	public final static int RSRC_MODEL_PATH = 6; 
	public final static int RSRC_CHECKSUM_0 = 7;
	public final static int RSRC_CHECKSUM_1 = 8;
	public final static int RSRC_CHECKSUM_2 = 9;

}
