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
package org.isoe.diagraph.commands;

import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecoretools.legacy.diagram.edit.parts.EAnnotationEditPart;
import org.eclipse.emf.ecoretools.legacy.diagram.part.EcoreDiagramEditor;
import org.eclipse.emf.ecoretools.legacy.diagram.providers.EcoreElementTypes;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.FillStyle;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.Style;
import org.eclipse.gmf.runtime.notation.View;
import org.isoe.diagraph.generic.GenericConstants;
import org.isoe.extensionpoint.gramgen.IAnnotationHelper;
import org.isoe.extensionpoint.gramgen.IDiagraphCommandHandler;
import org.isoe.extensionpoint.gramgen.IPackageCommandHandler;
import org.isoe.fwk.core.DParams;

import org.isoe.diagraph.controler.IDiagraphControler;  //FP140707refactored
public class DiagraphAnnotationFactory implements IDiagraphCommandHandler, IPackageCommandHandler{

	//vf1407
	private static final boolean LOG = DParams.DiagraphCommand_LOG;

	private static int COLOR_HIGHLIGHT = 12220865;

	private static int[] COLORS = { 16776960, 11991228, 15715474, 7781080,
			14517900, 12220865, 1677696, 1199122, 1571547, 778108, 1451790,
			1222086, 167769, 119912, 157154, 77810, 145179, 122208 }; // TODO
																		// only

	private boolean initialized = false;

	private IAnnotationHelper helper;


	private Map<EClass, List<String>>[] arguments;


	public DiagraphAnnotationFactory(IAnnotationHelper helper) {
		this.helper = helper;
		arguments = (Map<EClass, List<String>>[]) helper.getArguments();
	}

	private String normalize(String viewarg){
		if (!viewarg.startsWith("view="+DParams.VIEW_PREFIX))
			viewarg = "view="+DParams.VIEW_PREFIX+viewarg; //FP1126xx

		if (viewarg.contains("view="+DParams.VIEW_PREFIX+DParams.VIEW_PREFIX))
			viewarg="view="+DParams.VIEW_PREFIX+viewarg.substring(("view="+DParams.VIEW_PREFIX+DParams.VIEW_PREFIX).length());
		return viewarg;
	}


	public void generateAll(String srcpov, String trgpov, int povid) {


		if (!initialized)
			initializeElement(helper.getEditor());

		for (EClass cl : arguments[0].keySet()){
			//CreateAnnotationCommand_.sExecute(this, cl,arguments[0].get(cl),  helper.getEditor());
			DiagraphAnnotationCreateCommand.sExecute(this, "creatediagraphannot",cl,arguments[0].get(cl),  helper.getEditor());
		}

		if (srcpov != null)
		for (EClass cl : arguments[1].keySet()){
			AddAnnotationDetailCommand.execute(helper.getDiagraphUtils(), cl, srcpov,
					arguments[1].get(cl).get(0));
		}

		trgpov = normalize(trgpov);
		try {
			changeColor(trgpov, povid); // v is the view prefix
		} catch (Exception e) {
			helper.getControler().cerror("error while generateAll "+e.toString());
		}

	}

	@Override
	public void initializeElement(EcoreDiagramEditor editor) {
		// wrap around gef mysteries
		// see http://www.eclipse.org/forums/index.php/t/369126/
		// ADDING an element
		initialized = true;
		DiagramEditPart diagramEditPart = editor.getDiagramEditPart();
		EClass temp = EcoreFactory.eINSTANCE.createEClass();

		CreateElementRequest createElementRequest = new CreateElementRequest(
				EcoreElementTypes.EClass_1001);
		createElementRequest.setNewElement(temp);
		CreateElementRequestAdapter createElementRequestAdapter = new CreateElementRequestAdapter(
				createElementRequest);
		CreateViewAndElementRequest.ViewAndElementDescriptor veDescriptor = new CreateViewAndElementRequest.ViewAndElementDescriptor(
				createElementRequestAdapter,
				Node.class,
				((IHintedType) EcoreElementTypes.EClass_1001).getSemanticHint(),
				diagramEditPart.getDiagramPreferencesHint());
		CreateViewAndElementRequest veRequest = new CreateViewAndElementRequest(
				veDescriptor);
		Point p = new Point(150, 150);
		veRequest.setLocation(p);
		org.eclipse.gef.commands.Command command = diagramEditPart
				.getCommand(veRequest);
		command.execute();

		// DELETING that element

		EClass cls = (EClass) createElementRequestAdapter.resolve();
		EditingDomain editingDomain = editor.getEditingDomain();
		editingDomain.getCommandStack().execute(
				RemoveCommand.create(editingDomain, cls));

	}


	private void changeColor(final FillStyle styl, final int color) {
		TransactionalEditingDomain ted = TransactionUtil.getEditingDomain(styl);
		ted.getCommandStack().execute(new RecordingCommand(ted) {
			@Override
			protected void doExecute() {
				styl.setFillColor(color);
				if (LOG)
					clog("DAF2 changeColor" +color);
			}
		});
	}

	private void changeColor(Node node, int color) {

		if (LOG){
			  String nam="";
			  if (node.getElement() instanceof EAnnotation){
				  EAnnotation ne = (EAnnotation) node.getElement();
				 nam=((EClass)ne.eContainer()).getName();
			  } else
				  nam=node.getElement().toString();
			   clog("*** DAF1 changeColor " +nam+"="+color);
			   //FP140606 ici pour changer la couleur au redrop
			   //FP140611c
			}

		EList<Style> styl = node.getStyles();
		for (Style style : styl) {
			if (style instanceof FillStyle)
				changeColor((FillStyle) style, color);
		}
	}


	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	@Override
	public void changeColor(String key, int color_) {
		for (View view : (List<View>) helper.getEditor().getDiagram().getChildren()) {
			if (view instanceof Node) {
				Node node = (Node) view;
				if (node.getElement() instanceof EAnnotation) {
					EAnnotation eAnnotation = (EAnnotation) node.getElement();

					EModelElement emel = eAnnotation.getEModelElement();
				    if (emel instanceof EClass && (eAnnotation.getSource().equals(
							GenericConstants.ANNOT_DIAGRAPH_LITTERAL)
							&& eAnnotation.getDetails().containsKey(key))) //DParams.VIEW_PREFIX+
						changeColor(node, COLORS[color_+1]);
				}
			}
		}
	}


	@Override
	public void highlightAnnotation(String key) {// DiagraphKeywords.UNKNOWN
		EList<View> views = helper.getEditor().getDiagram().getChildren(); // FP130511,

		for (View view : views) {
			if (view instanceof Node) {
				Node node = (Node) view;
				if (node.getElement() instanceof EAnnotation) {
					EAnnotation eAnnotation = (EAnnotation) node.getElement();
					if (eAnnotation.getSource().equals(
							GenericConstants.ANNOT_DIAGRAPH_LITTERAL)
							&& eAnnotation.getDetails().containsKey(key)) {
						EList<Style> styl = node.getStyles();
						for (Style style : styl) {
							if (style instanceof FillStyle)
								changeColor((FillStyle) style, COLOR_HIGHLIGHT);
						}
					}
				}

			}
		}
	}

	@Override
	public void dropAll() {
		throw new RuntimeException("TODO implement dropAll");
	}

	@Override
	public void checkAllDiagraphAnnots() {
		throw new RuntimeException("TODO implement checkAllDiagraphAnnots");
	}

	@Override
	public void removeDiagraphAnnotationsFromDiagramAndFromModel(
			EcoreDiagramEditor ecoreDiagramEditor, EAnnotation modelElement,
			EAnnotationEditPart graphicalEditPart) {
		throw new RuntimeException("TODO implement removeDiagraphAnnotationsFromDiagramAndFromModel");

	}

	@Override
	public void removeDiagraphAnnotationsFromDiagram(
			EcoreDiagramEditor ecoreDiagramEditor, List<EAnnotation> toremove) {
		throw new RuntimeException("TODO implement removeDiagraphAnnotationsFromDiagram");


	}

	@Override
	public IDiagraphControler getControler() {
		return helper.getControler();
	}


}
