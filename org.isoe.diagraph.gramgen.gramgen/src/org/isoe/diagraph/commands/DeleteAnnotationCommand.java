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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecoretools.legacy.diagram.edit.parts.EAnnotationEditPart;
import org.eclipse.emf.ecoretools.legacy.diagram.part.EcoreDiagramEditor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.INodeEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Node;
import org.isoe.extensionpoint.gramgen.IAnnotationHelper;

/**
 * @author pfister
 *
 */

public class DeleteAnnotationCommand extends AbstractTransactionalCommand{
//FP131113a

	private static EClass contexClass;
	private static EAnnotation contextAnnotation;


	private DeleteAnnotationCommand(TransactionalEditingDomain editingDomain, EClass caller, EAnnotation ean)
	{
	    super(editingDomain, "delete diagraph annotation on "+caller.getName(), null);
	    contexClass = caller;
		contextAnnotation = ean;
	}


	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
			IAdaptable info) throws ExecutionException {
	    deleteEAnnotation();
		return CommandResult.newOKCommandResult();
	}


	// FP131113
	private void deleteEAnnotation() {
		EClass parent = (EClass) contextAnnotation.getEModelElement();
		parent.getEAnnotations().remove(contextAnnotation);
		contextAnnotation = null;
	}


	public static void removeFromModel(EAnnotation toDelete) {
		EClass parent = (EClass) toDelete.getEModelElement();
		ICommandProxy a = new ICommandProxy(new DeleteAnnotationCommand(TransactionUtil.getEditingDomain(parent), parent,toDelete));
		a.execute();
	}



	private static void removeFromDiagram(EcoreDiagramEditor editor,EAnnotationEditPart annotationEditPart) {
		DeleteFromDiagramAction delAction = new DeleteFromDiagramAction(true);
		List editParts = new ArrayList();
		editParts.add(annotationEditPart);
		delAction.setEditParts(editParts);
		delAction.run();
	}

	
	private static void removeFromDiagram(EcoreDiagramEditor editor,List editParts) {
		if (editParts.isEmpty())
			return;
		DeleteFromDiagramAction delAction = new DeleteFromDiagramAction(true);
		delAction.setEditParts(editParts);
		delAction.run();
	}

	public static void executeRemoveFromDiagram(List<INodeEditPart> annotationEditParts,EcoreDiagramEditor editor){ //FP140421
		removeFromDiagram(editor,annotationEditParts);
	}
	
	public static void executeRemoveFromDiagramAndFromModel(EAnnotationEditPart annotationEditPart,EcoreDiagramEditor editor){
		EAnnotation eAnnotation = (EAnnotation) ((Node) annotationEditPart.getModel()).getElement();
		removeFromDiagram(editor,annotationEditPart);
		removeFromModel(eAnnotation);
	}
	/*
	public static void execute_old(IAnnotationHelper helper_,  EAnnotation eAnnotation, EAnnotationEditPart annotationEditPart,EcoreDiagramEditor editor){
		//Object modl=annotationEditPart.getModel();
		//Node runtimeNode = (Node) modl;
		//EAnnotation eAnnotation_ = (EAnnotation) ((Node) annotationEditPart.getModel()).getElement();
		removeFromDiagram(editor,annotationEditPart);
		removeFromModel(eAnnotation);
	}*/



}
