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
package org.isoe.diagraph.diagraphviz.actions;

import java.io.File;

import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.action.Action;
import org.isoe.diagraph.diagraphviz.dotifiers.DotInvoker;
import org.isoe.extensionpoint.graphviz.IModelDotifyService;
import org.isoe.extensionpoint.xmi.IMetamodelRetriever;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.pathes.PathPreferences;
import org.isoe.diagraph.controler.IDiagraphControler;  //FP140707refactored
import org.isoe.extensionpoint.diagraph.action.DiagraphService;  //FP140707_c_refactored



/**
 *
 * @author pfister
 *
 */
public class ModelDotifyAction extends Action implements DParams, IModelDotifyService {

	private IDiagraphControler controler;
	private IMetamodelRetriever metamodelRetriever;

	private static String REPLACE_SEPARATOR;
	static {
		if (REPLACE_SEPARATOR == null) {
			REPLACE_SEPARATOR = File.separator;
			if (REPLACE_SEPARATOR.equals("\\"))
				REPLACE_SEPARATOR = "\\\\";
		}
	}


	public ModelDotifyAction() {
		//this.controler = controler;
		setText("Graphviz Action");
		setToolTipText("Graphviz Action");
		//metamodelRetriever = controler.getMetamodelRetriever();//new MetamodelRetriever();
	}



    @Override
	public void setControler(Object controler) {
		this.controler = (IDiagraphControler) controler;
		this.controler.registerService(this);
	}


    @Override
	public URI createplatformResourceModelURI(String plugin, String folder,
			String traceModelName, String traceExtension) {
		return  URI.createURI("platform:/resource/" + plugin + "/"
					+ folder.replaceAll(REPLACE_SEPARATOR, "/") + "/" + traceModelName + "." + traceExtension);
	}

    @Override
	public void run() {
		controler.removeListeners_();
		controler.clearErrorLog();
		controler.setLastChoice(controler.getChoice(2));

		String model = controler.getChoice(1);
		String[] pl = model.split("\\.");
		if (pl.length==2){
		model = pl[0];
		  String extension = pl[1];
		  URI mUri = createplatformResourceModelURI(PathPreferences.getPreferences().getInstanceRepositoryLocation(),
				  INSTANCE_FOLDER, model, extension);
		  String actionId = DOTIFY_ACTION_PREFIX + controler.getCommandId();//"org.isoe.diagraph.dotify."//dotifyTypeCombo.getText();
		  try {
			  findRegisteredMetamodelAndinvokeDot(mUri,actionId);
		 } catch (Exception e) {
			String m = e.getMessage();
			if (m==null)
				m=e.toString();
			controler.log("graphviz-error",m);
		 }

		}
		controler.restoreListeners(false);
	}

	private void findRegisteredMetamodelAndinvokeDot(URI uri, String actionId) {// "org.isoe.diagraph.dotify.all"
		org.isoe.diagraph.diagraphviz.dotifiers.DotInvoker helper = DotInvoker.getInstance();
		helper.setControler(controler);
		helper.setMetamodelRetriever(metamodelRetriever);
		helper.findRegisteredMetamodel(uri);
		helper.dorun(actionId);

	}


	@Override
	public boolean isStub() {
		return false;
	}


	@Override
	public boolean isQualified() {
		return true;
	}

	private static final boolean LOG = false;

	private boolean silent;


	public void setSilent(boolean value) {
		silent = value;
	}

	private void clog(String mesg) {
		if (LOG && !silent)
			System.out.println(mesg);
	}

	@Override
	public void setMetamodelRetriever(IMetamodelRetriever metamodelRetriever) {
		this.metamodelRetriever=metamodelRetriever;
	}





}
