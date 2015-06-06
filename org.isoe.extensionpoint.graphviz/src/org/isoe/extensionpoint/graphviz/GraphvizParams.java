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
package org.isoe.extensionpoint.graphviz;

import org.isoe.fwk.core.DParams;


/**
 * 
 * @author "fpfister"
 *
 */
public interface GraphvizParams {

	public static final String TEST_INSTANCE_FOLDER = "result/";
	public static final String DOT_FOLDER = "dot/";
	public static final String DEFAULT_VIEW = org.isoe.diagraph.views.ViewConstants.DIAGRAPH_DEFAULT;
	public static String DIAGRAPH_M2_DOT = DParams.DIAGRAPH_SUFX+".dot";
	public static String DIAGRAPH_M2M0_DOT_ = DParams.DIAGRAPH_SUFX+"_m2m0.dot";
	public static String M2M0_DOT = "_m2m0.dot";
	public static String DIAGRAPH_M2M2M0_DOT = DParams.DIAGRAPH_SUFX+"_m2m2m0.dot";
	public static String M0M2_DOT = "_m0m2.dot";
	public static String M2_M0B_DOT = "_m2_m0b.dot";
	public static String M2_M0C_DOT = "_m2_m0c.dot";
}
