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
package org.isoe.extensionpoint.diagen;

/**
 * 
 * @author fpfister
 *
 */
public interface IDiagenAlphabet {
	
	public static final String KW_CONTEXT = "context";
	public static final String KW_LEFT_PARENT = "leftparent";
	public static final String KW_RIGHT_PARENT = "rightparent";
	public static final String KW_REQUIRES_ = "requires";
	public static final String KW_REQUIRED = "required";
	public static final String KW_RELATED = "related"; // add doc,keywords,
														// anti,
														// problem

	public static final String KW_ORIGIN = "origin";
	public static final String KW_AKA = "knownas";

	public static final String DIAGEN_ANNOT = "diagen";
	public static final String[] DIAGEN_KW = { KW_CONTEXT, KW_LEFT_PARENT,
			KW_RIGHT_PARENT, KW_REQUIRES_, KW_RELATED, KW_ORIGIN, KW_AKA };
	
	

}
