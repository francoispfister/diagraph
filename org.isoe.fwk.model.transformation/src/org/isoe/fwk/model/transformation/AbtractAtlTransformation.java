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
package org.isoe.fwk.model.transformation;

import java.io.IOException;

import org.eclipse.m2m.atl.common.ATLExecutionException;
import org.eclipse.m2m.atl.core.ATLCoreException;

/**
 * 
 * @author pfister
 * 
 */
public abstract class AbtractAtlTransformation extends AbtractTransformation {

	
	
	protected abstract void loadModels(String in) throws ATLCoreException;

	protected abstract void saveModels(String out) throws ATLCoreException;

	protected abstract void transformOneInOneOut() throws ATLCoreException,
			IOException;

	
	@Override
	public AbtractTransformation executeOneInOneOut(String in, String out) {
		try {
			configure();
			loadModels(in);
			transformOneInOneOut();
			saveModels(out);
			log("done");
		} catch (ATLCoreException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ATLExecutionException e) {
			e.printStackTrace();
		}
		return this;
	}
    @Override
	public AbtractTransformation executeOneInOneOut(Object sel) {
		try {
			configure();
			handleSelection(sel);
			if (inUri != null) {
				String in = inUri.toString();
				in = in.substring("platform:/resource/".length()); // org.isoe.eval.atl.book2publi.plugin/Books.xmi
				loadModels(in);
				transformOneInOneOut();
				String out = outUri.toString();
				out = out.substring("platform:/resource/".length()); // org.isoe.eval.atl.book2publi.plugin/Books.xmi
				saveModels(out);
				log("done");
			} else
				log("nothing done");
		} catch (ATLExecutionException e) {
			e.printStackTrace();
			log(e.getMessage());
		} catch (ATLCoreException e) {
			e.printStackTrace();
			log(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			log(e.getMessage());
		}
		return this;
	}


	
}
