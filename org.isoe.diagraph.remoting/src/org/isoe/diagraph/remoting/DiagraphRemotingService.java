 /**
 * Copyright (c) 2014 Laboratoire de Genie Informatique et Ingenierie de Production - Ecole des Mines d'Ales
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Blazo Nastov (ISOE-LGI2P) - initial API and implementation
 */
package org.isoe.diagraph.remoting;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecoretools.legacy.diagram.part.EcoreDiagramEditor;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.jface.action.Action;
import org.isoe.extensionpoint.remoting.IDiagraphRemotingService;
import org.isoe.extensionpoint.xmi.IMetamodelRetriever;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.parts.DiagramDocumentEditor;
/**
 *
 * @author bnastov
 *
 */
import org.isoe.diagraph.controler.IDiagraphControler;  //FP140707refactored
import org.isoe.extensionpoint.diagraph.action.DiagraphService;  //FP140707_c_refactored
public class DiagraphRemotingService extends Action implements
		IDiagraphRemotingService {

	private IDiagraphControler controler;
	private boolean cmdSave;
	private DiagramEditor diagramEditor;
	private String modelDescription;
	private String modelUri;
	private EPackage packag;

	private IMetamodelRetriever metamodelRetriever;



    @Override
	public void setMetamodelRetriever(IMetamodelRetriever metamodelRetriever) {
		this.metamodelRetriever = metamodelRetriever;
	}

	@Override
	public void setPackage(EPackage packag) {
		this.packag = packag;
	}

	@Override
	public void setSave() {
		this.cmdSave = true;
	}

	@Override
	public void setLoad() {
		this.cmdSave = false;
	}

	@Override
	public void setControler(Object controler) {
		this.controler = (IDiagraphControler) controler;
		this.controler.registerService(this);
	}

	@Override
	public void setModelDescription(String modelDescription) {
		this.modelDescription = modelDescription;
	}

	@Override
	public void setModelUri(String modelUri) {
		this.modelUri = modelUri;
	}

	@Override
	public void setModelName(String name) {
		this.modelName = name;

	}

	@Override
	public void setModelNsPrefix(String nsPrefix) {
		this.modelNsprefix = nsPrefix;
	}


	@Override
	public void run() {
		diagramEditor = controler.isModelConfiguration() ? controler
				.getGenericDiagramEditor() : controler.getEcoreDiagramEditor();
		if (cmdSave) {
			// if (diagramEditor != null) //no longer used
			DiagraphRemoting.save(packag, diagramEditor, modelDescription,
					controler.isModelConfiguration(), controler,metamodelRetriever);
			// else
			// controler.showMessage("no opened editor");
		} else
			DiagraphRemoting.load(modelUri, controler.isModelConfiguration(),
					controler);
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
	private String modelName;
	private String modelNsprefix;

	@Override
	public void setSilent(boolean value) {
		silent = value;
	}

	private void clog(String mesg) {
		if (LOG && !silent)
			System.out.println(mesg);
	}



}
