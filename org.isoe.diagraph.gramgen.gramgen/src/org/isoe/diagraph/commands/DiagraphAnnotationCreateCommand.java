 /**
 * Copyright (c) 2014 Laboratoire de Genie Informatique et Ingenierie de Production - Ecole des Mines d'Ales
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Fran�ois Pfister (ISOE-LGI2P) - initial API and implementation
 */
package org.isoe.diagraph.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecoretools.legacy.diagram.part.EcoreDiagramEditor;
import org.eclipse.emf.ecoretools.legacy.diagram.providers.EcoreElementTypes;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
//import org.isoe.extensionpoint.gramgen.IDiagraphCommandHandler;
import org.isoe.diagraph.generic.GenericConstants;
import org.isoe.extensionpoint.gramgen.IDiagraphCommandHandler;
import org.isoe.fwk.core.DParams;

//import org.eclipse.gmf.runtime.emf.type.core.internal.requests.RequestCacheEntries;

/**
 *
 * @author fpfister
 * fragments borrowed from ecoretools
 *
 */
public class DiagraphAnnotationCreateCommand extends AbstractTransactionalCommand {


	/**
	 * The edit request that carries the required command parameters.
	 */
	private final IEditCommandRequest request;


	private EClass eClass;
	private List<String> keys;
	private EAnnotation eAnnotation;
	private TransactionalEditingDomain editingDomain;
	private IDiagraphCommandHandler diagraphCommandHandler;

	protected static List getAffectedFiles(IEditCommandRequest request) {
		List result = new ArrayList();
		List elements = request.getElementsToEdit();
		int size = elements.size();
		if (elements != null && (size > 0)) {
			if (size == 1) {
				Resource resource =  ((EObject) elements.get(0)).eResource();
				if (resource != null) {
					IFile file = WorkspaceSynchronizer.getFile(resource);
					if (file != null) {
						result.add(file);
					}
				}
			} else {
				Map resourcesToFileMap = new HashMap();
				for (int i = 0; i < size; ++i) {
					Resource resource = ((EObject) elements.get(i)).eResource();
					if (resource != null) {
						Object file = resourcesToFileMap.get(resource);
						// if it is in the Map, then it is in the List already
						// as well
						if (file == null) {
							file = WorkspaceSynchronizer.getFile(resource);
							if (file != null) {
								resourcesToFileMap.put(resource, file);
								result.add(file);
							}
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * Gets the element to be modified by this command.
	 *
	 * @return the element to be modified
	 */


	protected EObject getElementToEdit() {
		/*
		 * EObject container = ((CreateElementRequest)
		 * getRequest()).getContainer(); if (container instanceof View) {
		 * container = ((View) container).getElement(); } return container;
		 */
		return eClass;
	}


	protected EClass getEClassToEdit() {
		return eClass.eClass();
	}


	protected IEditCommandRequest getRequest() {
		return request;
	}

	protected boolean isOK(CommandResult commandResult) {
		return commandResult.getStatus().getCode() == IStatus.OK;
	}

	static void sExecute(IDiagraphCommandHandler handler,String label, EClass parent, List<String> keys,EcoreDiagramEditor editor) {

		TransactionalEditingDomain editingDomain_ = TransactionUtil.getEditingDomain(parent);
		DiagraphAnnotationCreateCommand cmd = new DiagraphAnnotationCreateCommand(
				editingDomain_, label,
				parent, keys);
		cmd.diagraphCommandHandler = handler;
		ICommandProxy cmp = new ICommandProxy(cmd);
		cmp.execute();
		cmd.dropOnDiagram(editor);
	}


	private void dropOnDiagram(
			EcoreDiagramEditor editor) {
		ArrayList list = new ArrayList();
		list.add(eAnnotation);
		DropObjectsRequest dropObjectsRequest = new DropObjectsRequest();
		dropObjectsRequest.setObjects(list);
		dropObjectsRequest.setLocation(new Point(15, 15));
		editor.getDiagramEditDomain()
				.getDiagramCommandStack()
				.execute(
						editor.getDiagramEditPart().getCommand(
								dropObjectsRequest));
	}



	protected DiagraphAnnotationCreateCommand(
			TransactionalEditingDomain editingDomain, String label,
			// EObject elementToEdit,
			EClass caller, List<String> keys) { // IEditCommandRequest
		super(editingDomain, label, null);
		this.editingDomain = editingDomain;
		eClass = caller;
		this.keys = keys;
		eAnnotation = null;

		IElementType annotationElementType = EcoreElementTypes.EAnnotation_1003;
		//ElementTypeRegistry.getInstance().getType("org.eclipse.emf.ecoretools.legacy.diagram.EAnnotation_1003");
		this.request = new CreateElementRequest(
				this.editingDomain, eClass,
		annotationElementType);

	}

	@Override
	public boolean canExecute() {
		// if (getEClass() != null) {
		// return getEClass().isSuperTypeOf(getEClassToEdit());
		// }
		return true;
	}


	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
			IAdaptable info) throws ExecutionException {
		eAnnotation = EcoreFactory.eINSTANCE.createEAnnotation();
		eClass.getEAnnotations().add(eAnnotation);
		doConfigure(eAnnotation, monitor, info);
		for (String key : keys)
			eAnnotation.getDetails().put(key, DParams.NULL_ANNOTATION_VALUE);
		eAnnotation.setSource(GenericConstants.ANNOT_DIAGRAPH_LITTERAL);
		((CreateElementRequest) getRequest())
				.setNewElement(eAnnotation);
		return CommandResult.newOKCommandResult(eAnnotation);
	}



	protected void doConfigure(EAnnotation newElement,
			IProgressMonitor monitor, IAdaptable info)
			throws ExecutionException {
		IElementType elementType = ((CreateElementRequest) getRequest())
				.getElementType();
		ConfigureRequest configureRequest = new ConfigureRequest(
				getEditingDomain(), newElement, elementType);
		configureRequest.setClientContext(((CreateElementRequest) getRequest())
				.getClientContext());
		configureRequest.addParameters(getRequest().getParameters());
		org.eclipse.gmf.runtime.common.core.command.ICommand configureCommand = elementType
				.getEditCommand(configureRequest);
		if (configureCommand != null && configureCommand.canExecute()) {
			configureCommand.execute(monitor, info);
		}
	}

}
