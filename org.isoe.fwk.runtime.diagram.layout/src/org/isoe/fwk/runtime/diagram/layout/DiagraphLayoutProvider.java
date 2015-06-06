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

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.services.layout.AbstractLayoutNodeProvider;
import org.eclipse.gmf.runtime.diagram.ui.services.layout.ILayoutNode;
import org.eclipse.gmf.runtime.diagram.ui.services.layout.ILayoutNodeOperation;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.isoe.diagraph.generic.GenericConstants;
import org.isoe.diagraph.views.ViewConstants;
//import org.isoe.extensionpoint.diagraph.IDiagraphConsole;
import org.isoe.fwk.core.DParams;
//import org.isoe.fwk.log.LogConfig;

/**
 *
 * @author pfister
 *
 */
public class DiagraphLayoutProvider extends AbstractLayoutNodeProvider  {
//org.isoe.fwk.runtime.diagram.layout.DiagraphLayoutProvider
	private static final boolean LOG = false;
	private String currentView = ViewConstants.DIAGRAPH_DEFAULT; // FP121013
	private String[] allViews;
	private DiagraphLayout diagraphLayout = new DiagraphLayout();
	//private IDiagraphConsole console;

	protected Command getDeleteCommand(List<IGraphicalEditPart> editParts)
			throws ExecutionException {
		if (editParts.isEmpty()) {
			return UnexecutableCommand.INSTANCE;
		}
		CompoundCommand command = new CompoundCommand("Delete From Diagram");
		for (Iterator<IGraphicalEditPart> iter = editParts.iterator(); iter
				.hasNext();) {
			IGraphicalEditPart editPart = iter.next();
			/* Send the request to the edit part */
			command.add(editPart.getCommand(new GroupRequest(
					RequestConstants.REQ_DELETE)));
			// command.add(editPart.getCommand(new
			// GroupRequest(RequestConstants.REQ_ADD)));
		}
		return command;
	}


	// FP121009b

	void logObject(EObject element) {
		if (!LOG)
			return;

		if (element==null)
			return;
		if (element instanceof EEnum) {
			EEnum en = (EEnum) element;
			String cn = en.getName(); // etc...
			clog("DLP123 EEnum " + cn);
		} else if (element instanceof EAnnotation) {
			EAnnotation eAnnotation = (EAnnotation) element;
			String src = eAnnotation.getSource(); // etc...
			if (GenericConstants.ANNOT_DIAGRAPH_LITTERAL.equals(src))
				clog("DLP123 EAnnotation diagraph");
		} else if (element instanceof EClass) {
			EClass eClass = (EClass) element;
			String cn = eClass.getName(); // etc...
			clog("DLP123 EClass " + cn);
		} else
			clog("DLP123 ???  " + element.getClass().getName());
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);

	}




	boolean isContainerDiagram(IOperation operation) {
		Iterator nodes = ((ILayoutNodeOperation) operation).getLayoutNodes()
				.listIterator();
		if (nodes.hasNext()) {
			Node node = ((ILayoutNode) nodes.next()).getNode();
			logObject(node.getElement());
			View container = (View) node.eContainer();
			return (container instanceof Diagram);
		}
		return false;
	}

	//@Override
	public boolean provides(IOperation operation) {

		// check to make sure all node are contained in a diagram

		/*
		 * if (operation instanceof ILayoutNodeOperation) { Iterator nodes =
		 * ((ILayoutNodeOperation) operation) .getLayoutNodes().listIterator();
		 * if (nodes.hasNext()) { Node node = ((ILayoutNode)
		 * nodes.next()).getNode();
		 *
		 * logObject(node.getElement());
		 *
		 * View container = (View) node.eContainer(); if (!(container instanceof
		 * Diagram)) return false; } } else return false;
		 */
		if (isContainerDiagram(operation)) {
			IAdaptable layoutHint = ((ILayoutNodeOperation) operation)
					.getLayoutHint();
			String layoutType = (String) layoutHint.getAdapter(String.class);
			boolean result = layoutType.startsWith(DParams.DIAGRAPH_LAYOUT_); // FP121013
			if (result && layoutType.contains("=")) {
				String[] lay = layoutType.split("=");
				String info = lay[1];
				String[] infos = info.split(";");
				currentView = infos[0];
				allViews = new String[infos.length - 1];
				for (int i = 1; i < infos.length; i++)
					allViews[i - 1] = infos[i];
			}
			//if (DParams.LanguageTransformer_4_LOG)
			//	clog4("AKW LAYOUT type=["+layoutType+"]");
			return result;
		}
		return false;
		// FP121013
		// return DParams.DIAGRAPH_LAYOUT_.equals(layoutType);
	}



	//@Override
	public Runnable layoutLayoutNodes(final List layouts,
			boolean offsetFromBoundingBox, IAdaptable layoutHint) {
		return new Runnable() {
			public void run() {
				List<AnnotatedDiagraphClass> result = diagraphLayout.layout_( //FP140618voir
						layouts, currentView, allViews); // FP121012
				IWorkbenchPage page = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage();
				IViewPart view = page.findView(GenericConstants.DIAGRAPH_BASIC_CONTROLER_ID_);//FP130421 DIAGRAPH_VIEW_ID);
				// ((DiagraphView) view).setLayers(result);
			}
		};
	}

}
