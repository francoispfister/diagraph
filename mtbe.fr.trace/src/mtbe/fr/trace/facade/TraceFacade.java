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
package mtbe.fr.trace.facade;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import mtbe.fr.trace.Trace;
import mtbe.fr.trace.TraceFactory;
import mtbe.fr.trace.TracePackage;


/**
 * 
 * @author "François.Pfister francois.pfister@gmail.com"
 *
 */
public class TraceFacade {

	private Trace alignment;
	private URI modelUri;
	

	private void createAlignment(String leftmm, String leftm, String rightmm,
			String rightm) {
		alignment = TraceFactory.eINSTANCE.createTrace();
		EObject srcmodel = loadModel(leftmm, leftm);
		srcmodel.eContents();
		EObject tgtmodel = loadModel(rightmm, rightm);
		tgtmodel.eContents();
		alignment.setSourceModel(srcmodel);
		alignment.setTargetModel(tgtmodel);
	}

	private void createAlignment(String leftMM, URI leftMUri, String rightMM,
			URI rightMUri) {
		alignment = TraceFactory.eINSTANCE.createTrace();
		EObject srcmodel = loadModel(leftMM, leftMUri);
		srcmodel.eContents();
		EObject tgtmodel = loadModel(rightMM, rightMUri);
		tgtmodel.eContents();
		alignment.setSourceModel(srcmodel);
		alignment.setTargetModel(tgtmodel);
	}
	
	
	private void createAlignment(URI leftMMUri, URI leftMUri, URI rightMMUri,
			URI rightMUri) {
		alignment = TraceFactory.eINSTANCE.createTrace();
		EObject srcmodel = loadModel(leftMMUri, leftMUri);
		srcmodel.eContents();
		EObject tgtmodel = loadModel(rightMMUri, rightMUri);
		tgtmodel.eContents();
		alignment.setSourceModel(srcmodel);
		alignment.setTargetModel(tgtmodel);	
	}
	


	
	public Trace readXmi(){
		if (modelUri!=null)
		  readXmiAlignment(modelUri); //, metamodelPath
		//readXmiAlignment(modelPath); //, metamodelPath
		return alignment;
	}
	


	public void saveXmi(String path){
		saveXmiTrace(path);
	}
	
	public void saveXmi(URI uri){
		saveXmiTrace(uri);
	}
	
	
	public Trace create( String leftmm, String leftm, String rightmm, String rightm) {
		createAlignment(leftmm, leftm, rightmm, rightm);
		return alignment;
	}
	
	public Trace create(String leftMM, URI leftMUri, String rightMM,
			URI rightMUri) {
		createAlignment(leftMM, leftMUri, rightMM, rightMUri);
		return alignment;
	}
	
	
	public Trace create(URI leftMMUri, URI leftMUri, URI rightMMUri, URI rightMUri) {
		createAlignment(leftMMUri, leftMUri, rightMMUri, rightMUri);
		return alignment;
	}


/*
	public TraceFacade(String mpath) {//String mmpath, String mpath
		//TracePackageImpl.setMetamodelLocation(mmpath);
		//this.metamodelPath = mmpath;
		this.modelPath_ = mpath;
	}
*/
	
	public TraceFacade(URI mUri) {
		this.modelUri = mUri;
	}

	public TraceFacade() {
		this.modelUri = null;
	}

	/******************** persistance *************************/
	private EObject loadModel(String mmpath, String mpath) {
		Resource r = ModelLoadingV2.loadResource(mpath, mmpath);
		EObject m = r.getContents().get(0);
		return m;
	}
	
	
	private EObject loadModel(String mmPath, URI mUri) {
		Resource r = ModelLoadingV2.loadResource(mUri, mmPath);
		EObject m = r.getContents().get(0);
		return m;
	}

	
	private EObject loadModel(URI mmUri, URI mUri) {
		Resource r = ModelLoadingV2.loadResource(mUri, mmUri);
		EObject m = r.getContents().get(0);
		return m;
	}



	private void saveXmiTrace(String mpath) {
		ModelLoadingV2.saveXmiTraceFile(mpath, alignment, TracePackage.eINSTANCE
				.getNsURI(), TracePackage.eINSTANCE);
	}
	
	private void saveXmiTrace(URI uri) {
		ModelLoadingV2.saveXmiTraceFile(uri, alignment, TracePackage.eINSTANCE
				.getNsURI(), TracePackage.eINSTANCE);
	}

	//static final String DEFAULT_ALIGN_RESULT = MODEL_FOLDER + "bibtex2docbook.xmi";
	private void readXmiAlignment(String mpath) { //, String mmloc_
		alignment = (Trace) ModelLoadingV2.loadXmiTraceFile(mpath,
				TracePackage.eINSTANCE.getNsURI(), TracePackage.eINSTANCE);
	}
	
	private void readXmiAlignment(URI modelUri) {
		alignment = (Trace) ModelLoadingV2.loadXmiTraceFile(modelUri,
				TracePackage.eINSTANCE.getNsURI(), TracePackage.eINSTANCE);
	}



}
