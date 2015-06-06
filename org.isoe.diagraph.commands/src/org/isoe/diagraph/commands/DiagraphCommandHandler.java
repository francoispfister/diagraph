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
package org.isoe.diagraph.commands;

import java.util.ArrayList;
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
import org.eclipse.gmf.runtime.diagram.ui.editparts.INodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.FillStyle;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.Style;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.swt.graphics.Color;
import org.isoe.diagraph.generic.GenericConstants;
import org.isoe.extensionpoint.gramgen.IAnnotationHelper;
import org.isoe.extensionpoint.gramgen.IDiagraphCommandHandler;
import org.isoe.extensionpoint.gramgen.IViewGenerator;
import org.isoe.fwk.core.DParams;
import org.isoe.diagraph.controler.IDiagraphControler; 

/**
 *
 * @author bnastov
 *
 */

public class DiagraphCommandHandler implements IDiagraphCommandHandler {
    private static final boolean LOG = DParams.DiagraphCommand_LOG;


	private Map<EClass, List<String>>[] arguments;


	private IAnnotationHelper helper;

	private static int COLOR_HIGHLIGHT = 12220865;

	private static int[] COLORS = { 16776960, 11991228, 15715474, 7781080,
			14517900, 12220865, 1677696, 1199122, 1571547, 778108, 1451790,
			1222086, 167769, 119912, 157154, 77810, 145179, 122208 }; // TODO
																		// only

	private boolean initialized = false;



	public void sGenerateAll() {
		arguments = ((IViewGenerator) helper).getConcreteSyntax();
		String pov = ((IViewGenerator) helper).getViewName();
		int povid = ((IViewGenerator) helper).getViewId();
		Map<EClass,List<String>> a =arguments[0];
		generateAll(null,pov, povid);
	}

	public void sGenerateAll(String spov,String tpov, int povid, String toHighlight) {
		generateAll(spov,tpov, povid);
		if (toHighlight != null)
			highlightAnnotation(toHighlight);
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
			helper.getControler().cerror("generateAll "+e.toString());
		}

	}

	public DiagraphCommandHandler(IAnnotationHelper helper) {
		this.helper = helper; // FP131114
		arguments = (Map<EClass, List<String>>[]) helper.getArguments();
	}

	@Override
	public void checkAllDiagraphAnnots() {
		if (!initialized)
			initializeElement(helper.getEditor());
		for (EClass cl : arguments[0].keySet())
			CheckAnnotationCommand.exec(cl);
	}

    @Override
	public void dropAll() {
		if (!initialized)
			initializeElement(helper.getEditor());
		for (EClass cl : arguments[0].keySet())
			DropAnnotationCommand.execute(this, cl,
				 helper.getEditor());
	}

	//TODO correct these 2 errors at the source
	private String normalize(String viewarg){
		if (!viewarg.startsWith("view="+DParams.VIEW_PREFIX))
			viewarg = "view="+DParams.VIEW_PREFIX+viewarg; //FP1126xx

		if (viewarg.contains("view="+DParams.VIEW_PREFIX+DParams.VIEW_PREFIX))
			viewarg="view="+DParams.VIEW_PREFIX+viewarg.substring(("view="+DParams.VIEW_PREFIX+DParams.VIEW_PREFIX).length());
		return viewarg;
	}


	// FP131125
	private INodeEditPart getNodeEditpart(EcoreDiagramEditor diagramEditor,
			EModelElement toremove) {
		for (Object o : new ArrayList(diagramEditor.getDiagramGraphicalViewer()
				.getEditPartRegistry().values()))
			if (o instanceof INodeEditPart
					&& ((INodeEditPart) o).getModel() instanceof Node
					&& toremove == ((Node) ((INodeEditPart) o).getModel())
							.getElement())
				return (INodeEditPart) o;
		return null;
	}

	private List<INodeEditPart> getNodeEditparts(
			EcoreDiagramEditor editor, List<EAnnotation> toremove) {
		List<INodeEditPart> result = new ArrayList<INodeEditPart>();
		for (EAnnotation toremov : toremove) {
			for (Object o : new ArrayList(editor.getDiagramGraphicalViewer()
					.getEditPartRegistry().values()))
				if (o instanceof INodeEditPart
						&& ((INodeEditPart) o).getModel() instanceof Node
						&& toremov == ((Node) ((INodeEditPart) o).getModel())
								.getElement())
					result.add((INodeEditPart) o);
		}
		return result;
	}

	public void delete(EcoreDiagramEditor editor, EAnnotation eAnnotation) {
		if (!initialized)
			initializeElement(editor);
		INodeEditPart toremove = getNodeEditpart(editor, eAnnotation);
		if (toremove != null)
			//DeleteAnnotationCommand.execute_old(helper, eAnnotation,
			//		(EAnnotationEditPart) toremove, editor);
		    DeleteAnnotationCommand.executeRemoveFromDiagramAndFromModel((EAnnotationEditPart) toremove, editor);
	}


	@Override
	public void removeDiagraphAnnotationsFromDiagram(EcoreDiagramEditor editor,
			List<EAnnotation> toremove) {
		if (!initialized)
			initializeElement(editor);
		 DeleteAnnotationCommand.executeRemoveFromDiagram(getNodeEditparts(editor, toremove), editor);
	}


	@Override
	public void removeDiagraphAnnotationsFromDiagramAndFromModel(EcoreDiagramEditor editor, EAnnotation eAnnotation,
			EAnnotationEditPart toremove) {
		if (!initialized)
			initializeElement(editor);
		if (toremove != null)
			//DeleteAnnotationCommand.execute_old(helper, eAnnotation,
			//		(EAnnotationEditPart) toremove, editor_);
		    DeleteAnnotationCommand.executeRemoveFromDiagramAndFromModel(
							(EAnnotationEditPart) toremove, editor);
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

	private int translate(final Color color) {
		return color.getRed() << 16 | color.getGreen() << 8 | color.getBlue();
	}

	private void changeColor(final FillStyle styl, final int color) {
		TransactionalEditingDomain ted = TransactionUtil.getEditingDomain(styl);
		ted.getCommandStack().execute(new RecordingCommand(ted) {
			@Override
			protected void doExecute() {
				styl.setFillColor(color);
			}
		});
	}

	private void changeColor(Node node, int color) {
		if (LOG){
			  String nam="";
			  if (node.getElement() instanceof ENamedElement){
				ENamedElement ne = (ENamedElement) node.getElement();
				nam=ne.getName();
			  } else
				  nam=node.getElement().toString();
			   clog("DCH 2 changeColor" +nam+"="+color);
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
	public IDiagraphControler getControler() {
		return helper.getControler();
	}



/*
 *
 *
 *


Adding existing domain element to the diagram at specific location [message #1185958]	Thu, 14 November 2013 06:27 Go to next message
Vitaly Savickas is currently offline Vitaly Savickas
Messages: 61
Registered: March 2010
Member
Does anyone know how can I add an existing domain element to the diagram at particular location?

I have spent three days figuring it out and debugging the existing implementation of creation tools from the palette, but nothing works. The CreateViewAndElement request is the closest thing to suit me, BUT I don't want a new element to be created. I already have an element with some attributes set, not belonging to the diagram yet. There's a method setResultElement() on the request, but it does not have any effect - still creates a new empty element.



void snippet(){
	// requests and adapters
	IElementType type = ComponentsElementTypes.Component_2002;
	CreateElementRequest createElementRequest = new CreateElementRequest(
			getEditingDomain(), ((View) getModel()).getElement(),
			ComponentsElementTypes.Component_2002);
	CreateElementRequestAdapter adapter = new CreateElementRequestAdapter(createElementRequest);
	ViewAndElementDescriptor descriptor = new ViewAndElementDescriptor(
			adapter,
			Node.class,
			((IHintedType) type).getSemanticHint(),
			getDiagramPreferencesHint());
	CreateViewAndElementRequest request = new CreateViewAndElementRequest(descriptor);

	// element command (customised to return an existing element)
	Command createElementCommand = new ICommandProxy(
			new EventBComponentCreateCommand((CreateElementRequest) descriptor.getCreateElementRequestAdapter().getAdapter(CreateElementRequest.class)) {

				@Override
				protected CommandResult doExecuteWithResult(
						IProgressMonitor monitor, IAdaptable info)
						throws ExecutionException {
					EventBComponent newElement = (EventBComponent) Platform
							.getAdapterManager().getAdapter(element,
									Component.class);

					ComponentDiagram owner = (ComponentDiagram) getElementToEdit();
					owner.getComponents().add(newElement);

					doConfigure(newElement, monitor, info);

					((CreateElementRequest) getRequest())
							.setNewElement(newElement);
					return CommandResult.newOKCommandResult(newElement);
				}
			});

	// semantic command
	SemanticCreateCommand semanticCommand = new SemanticCreateCommand(adapter, createElementCommand);

	// view command
	TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) getHost()).getEditingDomain();
	CreateCommand viewCommand = new CreateCommand(editingDomain, descriptor, (View) (getHost().getModel()));

	// relocate command
	Point dropLocation = dropRequest.getLocation().getCopy();
	getFigure().translateToRelative(dropLocation);
	SetBoundsCommand relocateCommand = new SetBoundsCommand(editingDomain, "Set location", request.getViewAndElementDescriptor(), dropLocation);

	// compound command
	CompositeCommand cc = new CompositeCommand(semanticCommand.getLabel());
	cc.compose(semanticCommand);
	cc.compose(viewCommand);
	cc.compose(relocateCommand);
	return new ICommandProxy(cc);
}*/

}
