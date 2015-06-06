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

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMIResource;

/**
 * 
 * @author pfister
 * 
 */
public abstract class AbstractEmfTransformation extends  AbtractTransformation{
	
	//org.isoe.fwk.model.transformation.AbstractEmfTransformation
	/*
	public abstract EObject transformOneInOneOut(EObject input);
	public abstract EObject transformMultiInOneOut(List<EObject> input);
	public abstract List<EObject> transformOneInMultiOut(EObject input);
	public abstract List<EObject> transformMultiInMultiOut(List<EObject> input);
*/
	
	protected abstract String getPluginId();

	public EObject transformMultiInOneOut(List<EObject> input) {
		return null;
	}

	
	public List<EObject> transformOneInMultiOut(EObject input) {
		return null;
	}


	public List<EObject> transformMultiInMultiOut(List<EObject> input) {
		return null;
	}
	

	public EObject transformOneInOneOut(EObject input) {
		return null;
	}
	
	private static URI createPlatformResourceUri2(String plugin, String folder,
			String file) {
		URI uri = URI.createPlatformResourceURI(plugin, true);
		if (folder != null && !folder.isEmpty())
			uri = uri.appendSegment(folder);
		uri = uri.appendSegment(file);
		return uri;
	}

	
	
	private static URI createPlatformResourceUri(String plugin, String folder,
			String file) {
		URI uri = URI.createPlatformResourceURI(plugin, true);
		if (folder != null && !folder.isEmpty())
			uri = uri.appendSegment(folder);
		uri = uri.appendSegment(file);
		return uri;
	}

	
	private static URI createFileUri2(String plugin, String folder, String file) {
		//String path = "../" + plugin;
		//if (folder != null && !folder.isEmpty())
		//	path += "/" + folder;
	//	path += "/" + file;
		URI uri = URI.createFileURI(new File(file).getAbsolutePath());
		return uri;
	}
	
	private static URI createFileUri(String plugin, String folder, String file) {
		String path = "../" + plugin;
		if (folder != null && !folder.isEmpty())
			path += "/" + folder;
		path += "/" + file;
		URI uri = URI.createFileURI(new File(path).getAbsolutePath());
		return uri;
	}

	
	private static URI createUri(String plugin, String folder, String file) {
		if (EMFPlugin.IS_ECLIPSE_RUNNING)
			//return createPlatformResourceUri(plugin, folder, file);
		    return createPlatformResourceUri2(plugin, folder, file);
		else
			//return createFileUri(plugin, folder, file);
		    return createFileUri2(plugin, folder, file);
	}
	




	protected AbtractTransformation executeOneInOneOut() {
		try {
			Resource inResource = resourceSet.getResource(inUri, true);
			EcoreUtil.resolveAll(inResource);
			EObject input = inResource.getContents().get(0);
			EObject result = transformOneInOneOut(input);
			Resource outResource = resourceSet.createResource(outUri);
			outResource.getContents().add(result);
			Map options = new HashMap();
			options.put(XMIResource.OPTION_ENCODING, "ISO-8859-1");
			outResource.save(options);
			log("execEmf " + outResource.getURI().toString() + " saved ok");
		} catch (Exception e) {
			log(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return this;
	}


	
	
	public AbtractTransformation executeOneInOneOut(URI in, URI out) {
		inUri = in;
		outUri =  out;
		configure();
		executeOneInOneOut();
		log("done");
		return this;	
	}
	
	@Override
	public AbtractTransformation executeOneInOneOut(String in, String out) {
		inUri = createUri(getPluginId(), null, in);
		outUri = createUri(getPluginId(), null, out);
		configure();
		executeOneInOneOut();
		log("done");
		return this;
	}
	/*
	@Override
	public AbtractTransformation executeOneInOneOut(URI in, URI out) {
		inUri = in;//createUri(getPluginId(), null, in);
		outUri = out;//createUri(getPluginId(), null, out);
		configure();
		executeOneInOneOut();
		log("done");
		return this;
	}*/

	@Override
	public AbtractTransformation executeOneInOneOut( Object sel) {
		configure();
		handleSelection(sel);
		if (inUri != null) {
			executeOneInOneOut();
			log("done");
		} else
			log("nothing selected");
		return this;
	}




	

	

}
