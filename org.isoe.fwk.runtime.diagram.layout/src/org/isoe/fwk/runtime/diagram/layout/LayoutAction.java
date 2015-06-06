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
package org.isoe.fwk.runtime.diagram.layout;

import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecoretools.legacy.diagram.part.EcoreDiagramEditor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.emf.workspace.AbstractEMFOperation;
//import org.eclipse.gmf.examples.runtime.diagram.layout.provider.SquareLayoutProvider;
import org.eclipse.gmf.runtime.diagram.ui.services.layout.LayoutService;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.action.Action;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.views.ViewConstants;
import org.isoe.extensionpoint.layout.ILayoutService;
import org.isoe.fwk.core.DParams;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.parts.DiagramDocumentEditor;

import org.isoe.extensionpoint.diagraph.action.DiagraphService;


/**
 *
 * @author "fpfister"
 *
 */
import org.isoe.diagraph.controler.IDiagraphControler;  //FP140707refactored
import org.isoe.extensionpoint.diagraph.generator.IDiagraphGen;  //FP140707_b_refactored
public class LayoutAction extends Action implements ILayoutService { // FP130927ll

	private static final boolean SAVE = true;
	private static final boolean AT_RUNTIME = true; // FP120923
	private static final boolean LOG = DParams.LayoutAction_LOG;

	private IDiagraphControler controler;
	private boolean silent;

	// private boolean initial;

	public LayoutAction() {
		setText("Diagraph_ Layout");
		setToolTipText("Diagraph_ Layout");
	}

	private void layout(int caze) {
		if (LOG)
			clog("AKW layout caz= " + caze);
		final Diagram diag = controler.getDiagramToLayout();// getDiagraphGenerator().getDiagram();
		if (diag != null) {
			if (LOG)
				clog("(1) starting layout");
			TransactionalEditingDomain tedom_ = TransactionUtil
					.getEditingDomain(diag);
			AbstractEMFOperation operation_ = new AbstractEMFOperation(tedom_,
					DParams.KEY_DIAGRAPH_LAYOUT_, null) {
				protected IStatus doExecute(IProgressMonitor monitor,
						IAdaptable info) throws ExecutionException {
					String view = controler.getCurrentView(22,((EPackage) diag.getElement()).getName(), this.getClass().getName());
					//FP140611

					if (LOG)
						clog("AKW executing layout for diagram "
								+ ((EPackage) diag.getElement()).getName()
								+ "-" + view);
					String infos = DParams.DIAGRAPH_LAYOUT_ + "=" + view + ";";
					String views[] = controler.getViews();
					for (int i = 0; i < views.length; i++) //FP140619
						infos += views[i] + ";";
					infos = infos.substring(0, infos.length() - 1);
					if (LOG)
						clog("AKW infos= [" + infos + "]");
					LayoutService.getInstance().layout(diag, infos); // FP121029
					return Status.OK_STATUS;
				}

			};

			try {
				operation_.execute(new NullProgressMonitor(), null);
			} catch (Exception e) {
				System.err.println("(3) error during layout: " + e.getMessage()
						+ " !!!!");
				controler.retryLayout();
				//throw new RuntimeException("(3) error during layout: "
				//		+ e.getMessage() + " !!!!");
			}
		} else
			System.err.println("no diagram");
	}






	@Override
	public void run() {
		boolean check = true;
		int i=0;
		//controler.logLayout(this);
		//boolean old = false; // FP140211
		try {


			IProgressMonitor nullProgressMonitor = new NullProgressMonitor(); // FP121017c
			EcoreDiagramEditor ecoreDiagramEditor_ = ((IDiagraphGen)controler
					.getDiagraphGenerator()).findActiveEcoreEditor(null);// "open a Diagraphed Ecore Model !(3)");
			boolean rcp = false; // FP130507zz
			if (ecoreDiagramEditor_ == null) {
				if (DParams.UI_LOG)
					clogui("not yet an ecore diagram to layout");
			} else {
				controler.logLayout(this); //FP140626
				EPackage pak_ = (EPackage) ecoreDiagramEditor_.getDiagram()
						.getElement();

				i=1;

                //FP140611
				String layer = controler.getCurrentView(23, pak_.getName(),this.getClass().getName());
				// FP140523qq
				if (layer == null)
					layer = ViewConstants.DIAGRAPH_DEFAULT; // FP140211

				i=2;

				if (LOG)
					clog("AKW runtime.diagram.layout for " + pak_.getName()
							+ "." + layer);

				i=3;

				List<DGraph> diagraphs = null;
				if (false)
					diagraphs = ((IDiagraphGen)controler.getDiagraphGenerator())
						.selectDiagraphNewParserTodo(null, ecoreDiagramEditor_.getDiagram(),
								!SAVE, AT_RUNTIME, rcp, false, //old,
								nullProgressMonitor);

				if (diagraphs == null || diagraphs.isEmpty()){
					if (LOG)
						clog("fallback to old parser");

					IDiagraphGen  gen = (IDiagraphGen) controler.getDiagraphGenerator();
					if (gen==null)
						controler.cerror("controler.getDiagraphGenerator() == null" );

					diagraphs = gen.selectDiagraph(null, ecoreDiagramEditor_.getDiagram(),
							!SAVE, AT_RUNTIME, rcp, false, //old,
							nullProgressMonitor);
				}


				i=4;



				DGraph diagraph = null;
				if (diagraphs!=null && !diagraphs.isEmpty()){
					diagraph =diagraphs.get(0);
				} else{
					String msg = "(1) diagraph is null while layout i="+ i ;
					controler.cerror(msg);
				}
				//DGraph diagraph = diagraphs == null ? null : diagraphs.get(0); // TODO
																				// handle
																				// with
																				// the
																				// view


				if (diagraph != null) { // FP140523qq
					if (LOG)
						clog("AKW layout for dgraph "
								+ diagraph.getPointOfView().getEClaz()
										.getEPackage().getName());
					layout(1);
				} else{
					controler.layoutError("error dgraph==null");
				}
				/*else if (layer
						.equals(ViewConstants.DIAGRAPH_ALL_VIEW_LITTERAL_))
					check = true;*/

				/*
				else if (layer
						.equals(org.isoe.diagraph.views.ViewConstants.DIAGRAPH_ALL_VIEW_LITTERAL_)){
					if (DParams.LanguageTransformer_4_LOG)
						clog4("AKW layout for caze 2 layer=" + layer);
					layout(2);
				} else{
					if (DParams.LanguageTransformer_4_LOG)
						clog4("AKW layout for caze 3");
					layout(3); // FP140522xxx
				}*/

			}

		} catch (Exception e_) {

			int errlen = e_.toString().length();
			errlen = Math.min(DParams.MAX_ERRLEN, errlen);
			String error = e_.toString().substring(0,errlen);

			controler.layoutError("exception "+error);
			if (e_ instanceof NullPointerException) {
				String msg = "(1) diagraph layout error i="+ i +" "+ error;
				controler.cerror(msg);
				//controler.showMessage(msg); //FP150530
				throw new RuntimeException(msg);
			} else if (e_ instanceof IndexOutOfBoundsException) {
				String msg = "(3) diagraph layout error i="+i+ " " + error;
				controler.cerror(msg);
			} else {
				//String msg = e.toString();
				String msg = "(4) diagraph layout error i="+i+ " " + error;
				//if (msg == null)
				//	msg = e.toString();
				controler.cerror(msg);
				//controler.showMessage(msg);
			}

		}
	}

	private void clogui(String mesg) {
		if (DParams.UI_LOG)
		 System.out.println(mesg);
	}

	public boolean isStub() {
		return false;
	}

	public boolean isQualified() {
		return true;
	}

	public void setSilent(boolean value) {
		silent = value;
	}




	private void clog(String mesg) {
		if (LOG){ // && !silent
			if (mesg == null)
				mesg="null";
			if (DParams.LOG_ON_CONSOLE)
				controler.logConsole(mesg);
			else
			   System.out.println( mesg);
		}
	}

	private void clog4____(String mesg) {
		if (DParams.LanguageTransformer_4_LOG)
			System.out.println(mesg);
	}




	private void clog4(String mesg) {
		if (DParams.LanguageTransformer_4_LOG){ // && !silent
			if (mesg == null)
				mesg="null";
			if (DParams.LOG_ON_CONSOLE)
				controler.logConsole(mesg);//FP140630aaa
			else
			   System.out.println( mesg);
		}
	}

	public void setControler(Object controler) {
		this.controler = (IDiagraphControler) controler;
		this.controler.registerService(this);
	}

}
