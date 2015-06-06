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
package org.isoe.diagraph.gen;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecoretools.legacy.diagram.part.EcoreDiagramEditor;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.isoe.diagraph.common.IDiagraphInvoker;
import org.isoe.diagraph.views.ViewConstants;
import org.isoe.fwk.core.DParams;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.parts.DiagramDocumentEditor;



/**
 *
 * @author pfister
 */
import org.isoe.extensionpoint.diagraph.generator.IDiagraphGen;  //FP140707_b_refactored
public class GenNotationAction extends Action implements ISelectionListener {

	public final static String ID = "org.isoe.fwk.views.gmfview.edit.actions.GenNotationAction";
	private static final boolean LOG_GEN_NOTATION = false;
	private static final boolean LOG = DParams.GenNotationAction_LOG;

	private IDiagraphGen diagraphGenerator;
	private GraphicalEditPart selectedElement;
	private String log;
	private IDiagraphInvoker diagraphview;
	private String layer_ = "default";
	private IWorkbenchPartSite site;
	private boolean refactor;
   	boolean refreshOnly;


	private void clog(String mesg) {
	   if (LOG)
          System.out.println(mesg);
	}

	public String getLog() {
		return log;
	}


	public void init_ (IDiagraphGen gen,  String layer,boolean refactor,boolean refreshOnly) { //IDiagraphRunner diagraphrunner,
		this.diagraphGenerator = gen;
		this.layer_ = layer;
		this.refactor = refactor;
		this.refreshOnly = refreshOnly;
		log="";
		//site.getPage().addSelectionListener(this);
	}

	public GenNotationAction(IDiagraphInvoker diagraphview,IWorkbenchPartSite site) {
		super();
		this.diagraphview = diagraphview;
		this.site = site;
		//site.getPage().addSelectionListener(this);
	}


	private String getCurrentLayer() {
		//if (diagraphview!=null)
		//  return diagraphview.getCurrentLayer();
		//else
		 return layer_;//"default";
	}


	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		selectedElement = null;
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			//selectedElement = (GraphicalEditPart) structuredSelection.getFirstElement();
			//System.out.println("#### selectionChanged  "+selectedElement.toString());

		}
	}

	@Override
	public void run() {
		//final boolean old=true; //FP140211
		ProgressMonitorDialog dialog = new ProgressMonitorDialog(site.getShell());
		try {
			dialog.run(false,false, new IRunnableWithProgress(){
			    public void run(IProgressMonitor monitor) {
			    	String layer = getCurrentLayer();
			        monitor.beginTask("Diagraph Generation -view: "+layer , -1);
			        if (LOG)
			        	clog("generating diagram editor for view "+layer);
					//layoutaction.run();// sequential execution
					//validateAction.run();
					boolean save = true;
					boolean atRuntime=false;
					boolean forceGenerationYes = true;
					boolean rcp = false; //FP130507zz
					//boolean validate_ = true; //FP130915
					EcoreDiagramEditor ecoreDiagramEditor = diagraphGenerator
							.findActiveEcoreEditor(
									"open a Diagraphed Ecore Model !(1)"); // FP120617
					if (ecoreDiagramEditor != null) { // FP120621 sauvegarder tout
														// avant de continuer

						//if (!layer.equals(ViewConstants.DIAGRAPH_ALL_VIEW_LITTERAL_)){
							//String layer=getCurrentLayer();
							Diagram diag=ecoreDiagramEditor.getDiagram();

							EPackage pack=(EPackage) diag.getElement();
							GenModel gm=null;
							log +=diagraphGenerator.generateDiagraphwold("gnact",forceGenerationYes, gm, layer, save, diag, atRuntime, monitor, rcp,true,refactor,refreshOnly);//old, //FP130417
						//}
					}
					//showDiagraphLog();
			        monitor.done();
			    }





			});
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

/*

	private void shortcuts() {
		final Diagram diag = workbenchEventAdapter.getDiagram();
		if (diag != null) {
			if (LOG_GEN_NOTATION || LOG && DParams.LOG_GLOBAL)
				System.out.println("starting notation generation");

		}

	}*/



}
