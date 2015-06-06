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
package org.isoe.diagraph.similarity.action;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.jface.action.Action;

import org.isoe.fwk.core.Separator;
import org.isoe.extensionpoint.similarity.IAlignService;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.pathes.PathPreferences;
import org.isoe.fwk.platform.resolver.Activator;


import org.isoe.extensionpoint.xmi.IMetamodelRetriever;
import org.isoe.extensionpoint.diagraph.action.DiagraphService;







/**
 *
 * thanks to com.googlecode.alignment.ui.AlignAction
 *
 */
import org.isoe.diagraph.controler.IDiagraphControler;  //FP140707refactored
public class AlignAction extends Action implements DParams, IAlignService {


	//contributes to: org.isoe.extensionpoint.similarity.diagraph_similarity



	private static final String LOG_TARGET="m2PackageLabel";
	private static final boolean LOG = false;
	private boolean silent;
	private IDiagraphControler controler;
	private IMetamodelRetriever metamodelRetriever;
	private String leftPath;
	private String rightPath;
	private String leftPlugin;
	private String rightPlugin;
	private String leftFolder;
	private String rightFolder;
	boolean languageSide=false;
	private IFile leftFile;
	private IFile rightFile;
	private URI leftMUri;
	private EPackage leftMM;
	private URI[] uris;

	/*
	@Override
	public void setMetamodelRetriever(IMetamodelRetriever metamodelRetriever) {
		this.metamodelRetriever = metamodelRetriever;
	}*/

	@Override
	public void init(String leftPath, String rightPath,
			boolean languageSide) {
		this.languageSide = languageSide;
		this.rightPath = rightPath;
		this.leftPath = leftPath;
	}

    @Override
	public void setControler(Object controler) {
		this.controler = (IDiagraphControler) controler;
		this.controler.registerService(this);
	}

	public AlignAction() {
		super();
		//this.view = view;

		setText("Instance Alignment");
		setToolTipText("Instance Alignment");
	}


	private String getRepositoryLocation(){
		String result = null;
		if (languageSide)
			return leftPlugin;
		else
		   return PathPreferences.getPreferences().getInstanceRepositoryLocation();
	}


	private  URI createplatformResourceFolderURI(String plugin, String folder) {
		URI result = null;
		if (!EMFPlugin.IS_ECLIPSE_RUNNING) {
			String fp = Activator.getWorkspacePath() + plugin + File.separator
					+ folder ;
			result = URI.createFileURI(fp);
			return result;
		} else {
			folder = folder.replaceAll(Separator.SEPARATOR, "/");
			result = URI.createURI("platform:/resource/" + plugin + "/"
					+ folder);
			return result;
		}
	}

	@Override
	public void createFolderIfNotExists(String project, String folder){
		URI uriAlignFolder = createplatformResourceFolderURI(
				project, folder);
		File foldr = new File(CommonPlugin.resolve(uriAlignFolder).toFileString());
		if (!foldr.exists()){
			if (!foldr.mkdir()) // FP130401
				throw new RuntimeException("unable to create folder "
						+ foldr);
			//IProgressMonitor monitor = new NullProgressMonitor();

		}
	}


	private void createFoldersIfNotExist() {
		createFolderIfNotExists(getRepositoryLocation(), leftFolder);
		createFolderIfNotExists(getRepositoryLocation(), leftFolder+File.separator+TRACE_DATA);
		try {
			leftFile.getParent().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		//	metamodelRetriever.getCurrentFile().getParent().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor()); //FP130401xx
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private URI createplatformResourceModelURI(String plugin, String folder,
			String traceModelName, String traceExtension) {
		URI result = null;
		if (!EMFPlugin.IS_ECLIPSE_RUNNING) {
			String fp = Activator.getWorkspacePath() + plugin + File.separator
					+ folder + File.separator + traceModelName + "."
					+ traceExtension;
			result = URI.createFileURI(fp);
			return result;
		} else {
			folder = folder.replaceAll(Separator.SEPARATOR, "/");
			result = URI.createURI("platform:/resource/" + plugin + "/"
					+ folder + "/" + traceModelName + "." + traceExtension);
			return result;
		}
	}


	@Override
	public void setMetamodelRetriever(IMetamodelRetriever metamodelRetriever) {
		this.metamodelRetriever = metamodelRetriever;
	}


	private boolean getUris() {
		String  left = controler.getChoice(1);//default2.visualinher //visualinher.ecore
		String right = controler.getChoice(2);
		controler.setLastChoice(right);

		String[] pl = left.split("\\.");
		left = pl[0];
		String mlExtension = pl[1];

		String[] pr =  right.split("\\.");
		right = pr[0];
		String mrExtension = pr[1];
		if (!languageSide){

			leftFolder = leftPath.substring(0,leftPath.length()-1);
			leftFolder = leftFolder.substring(leftFolder.lastIndexOf(File.separator)+1);

			leftPlugin = leftPath.substring(0,leftPath.lastIndexOf(leftFolder)-1);
			leftPlugin = leftPlugin.substring(leftPlugin.lastIndexOf(File.separator)+1);


		  rightPlugin=leftPlugin;
		  rightFolder=leftFolder;
		} else {

			leftFolder = leftPath.substring(0,leftPath.length()-1);
			leftFolder = leftFolder.substring(leftFolder.lastIndexOf(File.separator)+1);

			leftPlugin = leftPath.substring(0,leftPath.lastIndexOf(leftFolder)-1);
			leftPlugin = leftPlugin.substring(leftPlugin.lastIndexOf(File.separator)+1);

			rightFolder = rightPath.substring(0,rightPath.length()-1);
			rightFolder = rightFolder.substring(rightFolder.lastIndexOf(File.separator)+1);

			rightPlugin = rightPath.substring(0,rightPath.lastIndexOf(rightFolder)-1);
			rightPlugin = rightPlugin.substring(rightPlugin.lastIndexOf(File.separator)+1);
		}

		leftMUri = createplatformResourceModelURI(leftPlugin, leftFolder, left, mlExtension);
		URI lr=CommonPlugin.resolve(leftMUri);
		File f=new File(lr.toFileString());
		URI rightMUri = createplatformResourceModelURI(rightPlugin, rightFolder, right, mrExtension);
		URI rr=CommonPlugin.resolve(rightMUri);
		controler.log(LOG_TARGET,"");
		leftMM = metamodelRetriever.findRegisteredMetamodel(leftMUri,new NullProgressMonitor());
		leftFile = metamodelRetriever.getCurrentFile();
		URI leftMMUri = null;
		if (leftMM != null){
			leftMMUri = leftMM.eResource().getURI();
			controler.log(LOG_TARGET,leftMM.eResource().getURI().toString());
		}else{
			controler.log(LOG_TARGET,"not a model");
			controler.restoreListeners(false);
			return false;
		}
		EPackage rightMM = metamodelRetriever.findRegisteredMetamodel(rightMUri,new NullProgressMonitor());
		rightFile = metamodelRetriever.getCurrentFile();
		URI rightMMUri = rightMM.eResource().getURI();
		if (rightMM != null)
			controler.log(LOG_TARGET,rightMM.eResource().getURI().toString()
					+ " - " + rightMM.eResource().getURI().toString());
		else
			controler.log(LOG_TARGET,"not a model");
		createFoldersIfNotExist();
		URI uriAlignresult = createplatformResourceModelURI(getRepositoryLocation(), leftFolder+File.separator+TRACE_DATA, left + "_" + mlExtension
						+ "-vs-" + right + "_" + mrExtension, "trace");

		uris=new URI[5];

		uris[0]=uriAlignresult; //platform:/resource/isoe.bibtex/model/trace/bibtex_ecore-vs-bibtex_ecore.trace
		uris[1]=leftMMUri; //http://www.eclipse.org/emf/2002/Ecore
		uris[2]=leftMUri;//platform:/resource/isoe.bibtex/model/bibtex.ecore
		uris[3]=rightMMUri; //http://www.eclipse.org/emf/2002/Ecore
		uris[4]=rightMUri; //platform:/resource/isoe.bibtex/model/bibtex.ecore

		metamodelRetriever.markUnregisteredModels(new NullProgressMonitor());
        return true;
	}

	@Override
	public void run() {

		controler.removeListeners_();
		controler.clearErrorLog();
		if (getUris()){
		   controler.execute("setTraceUri",2,uris);
		   controler.execute("traceAlignAndSave",2,uris);
		} else
			clog ("getUris - Not a model !!");
		//view.setTraceUri(2,uriAlignresult);
		//view.execute("traceAlignAndSave",2,uriAlignresult, leftMMUri, leftMUri,rightMMUri, rightMUri)
		controler.restoreListeners(false);
	}

	@Override
	public boolean isStub() {
		return false;
	}

	@Override
	public boolean isQualified() {
		return true;
	}

	@Override
	public void setSilent(boolean value) {
		silent=value;
	}

	private void clog(String mesg){
		if (LOG && !silent)
			System.out.println(mesg);
	}



}
