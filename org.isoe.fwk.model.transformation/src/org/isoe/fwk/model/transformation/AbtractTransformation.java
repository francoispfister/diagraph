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


import java.util.ArrayList;
import java.util.List;

//import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;



/**
 * 
 * @author pfister
 * 
 */
public abstract class AbtractTransformation {
	//org.isoe.fwk.transformation.AbtractTransformation
	
	protected ResourceSet resourceSet;
	protected URI inUri;
	protected URI outUri;
	protected String outPrefix;
	protected String inNsURI;
	protected String selectedNsURI;

	private List<String> logs = new ArrayList<String>();


	public abstract AbtractTransformation executeOneInOneOut(Object sel);
	public abstract AbtractTransformation executeOneInOneOut(String in, String out);
	

	
	protected void configure() {
		log("configure");
		resourceSet = new ResourceSetImpl();
		resourceSet
				.getResourceFactoryRegistry()
				.getExtensionToFactoryMap()
				.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
						new XMIResourceFactoryImpl());
		setOutPrefix();
	}

	protected void setOutPrefix() {
		outPrefix = "Transformed";	
	}

	//static Logger log = Logger.getLogger(AbtractTransformation.class);


	public Object getLog() {
		return logs;
	}
	

	protected void log(Object o) {
		logs.add(o.toString());
		System.out.println(o.toString());
		//log_info(o);
	}

	private void log_info(Object o) {
		System.out.println(o.toString());
	}
	
	
	protected static String getPlatformPluginUri(String uri) {
		if (isEclipseRunning())
			return URI.createPlatformPluginURI(uri, true).toString();
		else
			return "../" + uri;
	}

	protected static String getPlatformResourceUri(String uri) {
		if (isEclipseRunning()) {
			return URI.createPlatformResourceURI(uri, true).toString();
		} else
			return uri;
	}

	public static boolean isEclipseRunning() {
		try {
			return Platform.isRunning();
		} catch (Throwable exception) {

		}
		return false;
	}

	protected boolean handleSelection(Object selection) {
		if (inNsURI == null || inNsURI.isEmpty())
			throw new RuntimeException("inNsURI should have a value !!!");
		URI matched = null;
		if (selection instanceof XMIResource)
			matched = matchPackage((Resource) selection);
		else if (selection instanceof IFile)
			matched = matchPackageFile((IFile) selection);
		else if (selection != null)
			log(selection.getClass().getName());
		else
			log("no selection");
		if (matched != null) {
			inUri = matched; // platform:/resource/org.isoe.test.atl.book/model/Books.xmi
			outUri = getOutputUri();
			log("selection ok: "+ inNsURI);
			return true;
		}else
			log("bad selection :"+selectedNsURI+" , seeking for " + inNsURI);
		return false;
	}

	protected URI matchPackageFile(IFile file) {
		Resource resource = resourceSet.getResource(URI
				.createPlatformResourceURI(file.getFullPath()
						.toPortableString(), true), true);
		return matchPackage(resource);
	}

	protected URI matchPackage(Resource resource) {
		selectedNsURI = resource.getContents().get(0).eClass().getEPackage().getNsURI();
		if (selectedNsURI.equals(inNsURI))
			return resource.getURI();
		else
			return null;
	}

	protected URI getOutputUri() {
		String inpath = inUri.toPlatformString(false);
		int slash = inpath.lastIndexOf("/") + 1;
		String post = inpath.substring(slash);
		return URI.createPlatformResourceURI(
				inpath.substring(0, slash)
						+ post.substring(0, post.lastIndexOf(".")) + "2"
						+ outPrefix + ".xmi", true);
	}
	/*
	protected URI getOutputUri__nu() {
		String OUTPUT_FOLDER = DParams.MODEL_FOLDER;
		String inpath = inUri.toPlatformString(false);
		int slash = inpath.lastIndexOf("/") + 1;
		String file = inpath.substring(slash);
		String result = inpath.substring(0,slash-1);
		slash = result.lastIndexOf("/") + 1;
		result = result.substring(0,slash-1);
		result = result+"/"+OUTPUT_FOLDER+"/"+file;
		return URI.createPlatformResourceURI(result, true);
	}
*/



}
