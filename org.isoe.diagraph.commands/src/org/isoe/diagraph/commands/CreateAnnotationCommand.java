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

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecoretools.legacy.diagram.edit.commands.EAnnotationCreateCommand;
import org.eclipse.emf.ecoretools.legacy.diagram.part.EcoreDiagramEditor;
import org.eclipse.emf.ecoretools.legacy.diagram.providers.EcoreElementTypes;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.isoe.diagraph.generic.GenericConstants;
import org.isoe.extensionpoint.gramgen.IDiagraphCommandHandler;
import org.isoe.extensionpoint.gramgen.IDiagraphUtils;
import org.isoe.fwk.core.DParams;

/**
 * This class enables adding annotation into a ecore model during a transaction
 *
 * @author bnastov
 *
 */

public class CreateAnnotationCommand extends AbstractTransactionalCommand {
	private IDiagraphCommandHandler gefHandler;
	private IDiagraphCommandHandler annotationEditManager;

	private static EClass contexClass;
	private static List<String> contextKeys;
	private static EAnnotation contextAnnotation_;
/*
	private EClass pContexClass;
	private List<String> pContextKeys;
	private EAnnotation pContextAnnotation;*/
	//private EditPart editPart8;
	//private boolean new_version;


/*

	public CreateAnnotationCommand(
			IDiagraphCommandHandler eAnnotationEditManager,
			TransactionalEditingDomain editingDomain, EClass claz,
			List<String> keys, boolean dummy) {
		super(editingDomain, "a message", null);
		this.annotationEditManager = eAnnotationEditManager;
		pContexClass = claz;
		pContextKeys = keys;
		pContextAnnotation = null;
		//editPart8 = eAnnotationEditManager.getEditPart();
		//new_version = true; //FP140502
	}
	*/

	/**
	 * Constructor used to assign values to the context class and the context
	 * key
	 *
	 * @param editingDomain
	 * @param caller
	 * @param keys
	 */
	private CreateAnnotationCommand(IDiagraphCommandHandler gefHandler,
			TransactionalEditingDomain editingDomain, EClass caller,
			List<String> keys) {
		super(editingDomain, "a message", null);
		this.gefHandler = gefHandler;
		this.contexClass = caller;
		this.contextKeys = keys;
		this.contextAnnotation_ = null;
		//pContexClass = caller;
		//pContextKeys = keys;
		//pContextAnnotation = null;
		//new_version = false; //FP140502
	}



	/**
	 * The method where actually the annotation is created and added into the
	 * ecore model. Note that, since the tool diagraph manipulates only
	 * annotations with source name diagraph, all the annotations that we are
	 * going to be creating will have diagraph as source name
	 *
	 */
	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
			IAdaptable info) throws ExecutionException {
		// Assigning value to the annotation that will furthermore be displayed

		/*
		if (new_version ){
			execNew(editPart8,pContexClass, pContextKeys, info);
			contextAnnotation_ = createEAnnotation(pContexClass, pContextKeys);
		}else
		*/
		{
			execOld(contexClass, contextKeys);
			contextAnnotation_ = createEAnnotation(contexClass, contextKeys);
		}
	//	contextAnnotation_ = createEAnnotation(_contexClass, _contextKeys);
		//pContextAnnotation = pCreateEAnnotation(diagramEditPart,_contexClass, _contextKeys);

		return CommandResult.newOKCommandResult();
	}

	/*
	private CommandResult execNew(
			EditPart editPart, EClass claz, List<String> details,
			 IAdaptable info)
			throws ExecutionException {
		pContextAnnotation = pCreateEAnnotationB_(editPart,pContexClass, pContextKeys,info);
		return CommandResult.newOKCommandResult();
	}
*/

	private CommandResult execOld(
			 EClass claz, List<String> details)
			throws ExecutionException {
		contextAnnotation_ = createEAnnotation(contexClass, contextKeys);
		return CommandResult.newOKCommandResult();
	}


	// FP131113
	private void deleteEAnnotation_nu() {
		EClass parent = (EClass) contextAnnotation_.getEModelElement();
		parent.getEAnnotations().remove(contextAnnotation_);
		contextAnnotation_ = null;
	}

	/**
	 * Creating annotation method, it can only be used inside of the
	 * doExecuteWithResult method
	 *
	 * @param parent
	 *            the parent {@link EClass}
	 * @param contextKeys2
	 *            the key value
	 * @return the created {@link EAnnotation}
	 */
	private static EAnnotation createEAnnotation(EClass parent,
			List<String> keys) {
		EAnnotation annot = EcoreFactory.eINSTANCE.createEAnnotation();
		annot.setSource(GenericConstants.ANNOT_DIAGRAPH_LITTERAL);
		annot.setEModelElement(parent);
		for (String key : keys) {
			annot.getDetails().put(key, DParams.NULL_ANNOTATION_VALUE);
		}
		return annot;
	}




	private EAnnotation pCreateEAnnotationB_(EditPart editPart,
			EClass parent,List<String> keys,IAdaptable info) {
		try {
			//IAdaptable info = null;
			IProgressMonitor monitor = new NullProgressMonitor();
			return pCreateAnnotationB_(editPart, parent, keys, monitor, info);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Call this method to insert an annotation into a ecore model
	 *
	 * @param paren
	 *            the parent {@link EClass}
	 * @param keys
	 *            the key value
	 */
	private static void sCreateAnnotation(IDiagraphCommandHandler gefHandler,
			EClass parent, List<String> keys) {
		ICommandProxy a = new ICommandProxy(new CreateAnnotationCommand(
				gefHandler, TransactionUtil.getEditingDomain(parent), parent,
				keys));
		a.execute();
	}



	protected EAnnotation pCreateAnnotationB_(EditPart editPart,
			EClass claz, List<String> details, IProgressMonitor monitor,
			IAdaptable info) throws ExecutionException {
		EAnnotation annotation = EcoreFactory.eINSTANCE.createEAnnotation();
		annotation.setSource("diagraph");
		for (String detail : details)
			annotation.getDetails().put(detail, DParams.NULL_ANNOTATION_VALUE);
		annotation.setEModelElement(claz);
		EditElementCommand eec = createAnnotationCommand(annotation);
		if (eec != null && eec.canExecute())
			eec.execute(monitor, info);
		return annotation;
	}

	private EAnnotation pCreateAnnotationA(IDiagraphCommandHandler gefHandler, EClass claz, List<String> keys) {
		EAnnotation annotation = EcoreFactory.eINSTANCE.createEAnnotation();
		annotation.setSource("diagraph");
		for (String detail : keys)
			annotation.getDetails().put(detail, DParams.NULL_ANNOTATION_VALUE);
		TransactionalEditingDomain editingDomain = TransactionUtil.getEditingDomain(claz);
		IElementType annotationElementType=EcoreElementTypes.EAnnotation_1003;
		CreateElementRequest req = new CreateElementRequest(
				editingDomain, annotation,
				annotationElementType);
		 req.setNewElement(annotation);
		EAnnotationCreateCommand annotationCreateCommand = new EAnnotationCreateCommand(
				req);
		ICommandProxy a = new ICommandProxy(annotationCreateCommand);
		a.execute();
		return annotation;
	}


	private void createAnnotation__1(IDiagraphCommandHandler eAnnotationEditManager, EClass parent, List<String> keys) {

		//FP140502xxx
		ICommandProxy a = new ICommandProxy(new
		CreateAnnotationCommand(eAnnotationEditManager,TransactionUtil.getEditingDomain(parent),
		 parent, keys));
		a.execute();
	}





	/*

	public void pExecute_(EcoreDiagramEditor editor) {
		pContextAnnotation = pCreateAnnotationA(annotationEditManager, pContexClass, pContextKeys);
		dropOnDiagram(pContextAnnotation, editor);
	}
*/


	/*
	public void execute(IDiagraphCommandHandler eAnnotationEditManager,
			EClass parent, List<String> keys, String view, int viewId,
			EcoreDiagramEditor editor, IDiagraphUtils utils) {
		createAnnotation(eAnnotationEditManager, parent, keys);
		dropOnDiagram(_contextAnnotation_, editor);

	}*/

	/**
	 * Special parameter that can be set on the request to avoid creating the
	 * child.
	 */
	public static final String CREATE_CHILD_PARAMETER_ = "createChild"; //$NON-NLS-1$
	/*
	 * public CreateSubProcessCommand(CreateElementRequest req) {
	 * super(req.getLabel(), null, req); }
	 */

	private EditElementCommand createAnnotationCommand(EAnnotation ean) {
		IElementType annotationElementType = EcoreElementTypes.EAnnotation_1003;
				//ElementTypeRegistry.getInstance().getType("org.eclipse.emf.ecoretools.legacy.diagram.EAnnotation_1003");
		CreateElementRequest req = new CreateElementRequest(
				TransactionUtil.getEditingDomain(ean), ean,
				annotationElementType);
		return new EAnnotationCreateCommand(req);
	}


	private CreateElementRequest createAnnotationRequest(EAnnotation ean) {
		IElementType annotationElementType = EcoreElementTypes.EAnnotation_1003;
				//ElementTypeRegistry.getInstance().getType("org.eclipse.emf.ecoretools.legacy.diagram.EAnnotation_1003");
		return new CreateElementRequest(
				TransactionUtil.getEditingDomain(ean), ean,
				annotationElementType);
	}




	public static boolean addEAnnotationReferenceToDiagram(Diagram diagram,
			EAnnotation eAnnotation, String src, EObject eobj) {
		if (diagram != null) {
			if (eAnnotation == null) {
				eAnnotation = EcoreFactory.eINSTANCE.createEAnnotation();
				eAnnotation.setSource(src);
				diagram.getEAnnotations().add(eAnnotation);
			}
			eAnnotation.getReferences().add(eobj);

			return true;
		}
		return false;
	}

	private void addOperations(EAnnotation annotation, List<String> details) {
		for (String detail : details)
			annotation.getDetails().put(detail, DParams.NULL_ANNOTATION_VALUE);
	}



	public static void sExecute(IDiagraphCommandHandler gefHandler,
			EClass parent, List<String> keys, EcoreDiagramEditor editor) {
		sCreateAnnotation(gefHandler, parent, keys);
		dropOnDiagram(contextAnnotation_, editor);
		// gefHandler.changeColor(view, viewId);
	}


	private static void dropOnDiagram(EAnnotation annot,
			EcoreDiagramEditor editor) {
		ArrayList list = new ArrayList();
		list.add(annot);
		DropObjectsRequest dropObjectsRequest = new DropObjectsRequest();
		dropObjectsRequest.setObjects(list);
		dropObjectsRequest.setLocation(new Point(15, 15));
		editor.getDiagramEditDomain()
				.getDiagramCommandStack()
				.execute(
						editor.getDiagramEditPart().getCommand(
								dropObjectsRequest));
	}

}
