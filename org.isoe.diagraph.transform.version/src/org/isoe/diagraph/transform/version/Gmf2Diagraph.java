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
package org.isoe.diagraph.transform.version;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.isoe.extensionpoint.transformation.IAbtractTransformation;
import org.isoe.fwk.model.transformation.AbtractTransformation;

/**
 *
 * @author fpfister
 *
 */
public class Gmf2Diagraph extends AbstractDiagraphTransformation implements IAbtractTransformation {



	@Override
	public EObject transformOneInOneOut(EObject input) {
		EPackage rootp = (EPackage) input;
		return input;
	}


	public Gmf2Diagraph(String outfolder) {
		this.outFolder=outfolder;
	}

	public static AbtractTransformation execute(Object sel, String outfolder) {
		AbtractTransformation transfo = new Gmf2Diagraph(outfolder);
		transfo.executeOneInOneOut(sel);
		return transfo;
	}


}
