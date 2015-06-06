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

import java.io.File;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.isoe.fwk.model.transformation.AbstractEmfTransformation;
import org.isoe.fwk.model.transformation.AbtractTransformation;

/**
 * 
 * @author pfister
 * 
 */
public class AbstractDiagraphTransformation extends AbstractEmfTransformation {
//org.isoe.eval.transfo.emf.boo2publi.plugin.files.Book2publiEmf
	
	
	public final static String PLUGIN_ID = "org.isoe.diagraph.transform.version";
	
	//private final static String OUTPUT_FOLDER = "model";
	
	protected String outFolder;



	@Override
	protected void configure() {
		super.configure();
		inNsURI = "http://www.eclipse.org/emf/2002/Ecore";
	}

	
	public static AbtractTransformation execute(String in, String out) {
		AbtractTransformation transfo = new AbstractDiagraphTransformation();
		transfo.executeOneInOneOut(in,out);
		return transfo;
	}
	
	
	
	public static void main(String[] args) {
		//E:\Apps\workspaces\ws-integr-c6\_megamodel\repository\case4.ecore
		File f = new File(".");
		System.out.println(f.getAbsolutePath());//E:\Apps\workspaces\ws-integr-c6\org.isoe.diagraph.transform.version\.
		String path = f.getAbsolutePath();
		 path = path.substring(0,path.length()-2);
		 path = path.substring(0,path.lastIndexOf(File.separator));
		 System.out.println(path);
		
		//String pa = path+"\\_megamodel\\repository\\model\\case4.ecore";
		//String pb = path+"\\_megamodel\\repository\\model\\case4b.ecore";
		
		String pa = path+"\\_megamodel\\repository\\case4.ecore";
		String pb = path+"\\_megamodel\\repository\\case4b.ecore";

		//String p1 = "..\\org.isoe.diagraph.test.dp4se\\saved-model\\dp4se.ecore";
		//String p2 = "..\\org.isoe.diagraph.test.dp4se\\model\\dp4se.ecore";
		execute(pa, pb);
	}
	
	
	@Override
	public EObject transformOneInOneOut(EObject input) {
		log("transformDiagraph " + input);
		EPackage rootp = (EPackage) input;
		return input;
	}

	@Override
	protected String getPluginId() {
		return PLUGIN_ID;
	}


	@Override
	protected URI getOutputUri() {
		String inpath = inUri.toPlatformString(false);
		int slash = inpath.lastIndexOf("/") + 1;
		String file = inpath.substring(slash);
		String result = inpath.substring(0,slash-1);
		slash = result.lastIndexOf("/") + 1;
		result = result.substring(0,slash-1);
		result = result+"/"+outFolder+"/"+file;
		return URI.createPlatformResourceURI(result, true);
	}
	
	

	
	public static AbtractTransformation execute(Object sel) {
		AbtractTransformation transfo = new AbstractDiagraphTransformation();
		transfo.executeOneInOneOut(sel);
		return transfo;
	}
	
	@Override
	public Object getLog() {
		return super.getLog();
	}

}
