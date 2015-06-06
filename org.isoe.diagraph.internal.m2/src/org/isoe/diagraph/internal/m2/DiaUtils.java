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
package org.isoe.diagraph.internal.m2;

import org.isoe.diagraph.internal.m2.api.IDiaContainment;
import org.isoe.fwk.core.DParams;



/**
 *
 * @author pfister
 *
 */

public class DiaUtils {

	private static final boolean LOG = DParams.DiaUtils_LOG;


	public static void log(String caze, String kname, boolean kcreated, boolean knameinferred, IDiaContainment containment, DiaNode toembed) {
		if (!(LOG ))
			return;

		String log = "DUTLS   " + caze + " " + kname + " -> " + (containment.getTargetNode() != null ? containment.getTargetNode().getName() : "no target node");
		log += kcreated ? "\ncreated" : "";
		log += knameinferred ? "\nname inferred=" + containment.getName() : "\nname=" + containment.getName();

		log += "\nDeferredHost=";
		log += containment.getDeferredHost() != null ? containment.getDeferredHost() : "~";
		log += "\nTargetRefName=";
		log += containment.getNameIfCompartment() != null ? containment.getNameIfCompartment() : "~";
		log += "\nTargetTypeName=";
		log += containment.getNameIfShared() != null ? containment.getNameIfShared() : "~";
		log += "\nSourceNode=";
		log += containment.getSourceNode() != null ? containment.getSourceNode().getName() : "~";
		log += "\nTargetNode=";
		log += containment.getTargetNode() != null ? containment.getTargetNode().getName() : "~";
		log += "\nTargetReference=";
		log += containment.getTargetReference() != null ? containment.getTargetReference().getName() : "~";
		log += "\nnodeToEmbedIn=" + (toembed != null ? toembed.getName() : "~");
		log += containment.isPropagated() ? "\npropagated=true" :"\npropagated=false";
		System.out.println(log);
	}


}
