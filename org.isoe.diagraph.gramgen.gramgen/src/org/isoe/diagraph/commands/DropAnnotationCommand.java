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
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecoretools.legacy.diagram.edit.parts.EAnnotationEditPart;
import org.eclipse.emf.ecoretools.legacy.diagram.edit.parts.EClassEditPart;
import org.eclipse.emf.ecoretools.legacy.diagram.part.EcoreDiagramEditor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.Node;
import org.isoe.extensionpoint.gramgen.IDiagraphCommandHandler;
import org.isoe.fwk.core.DParams;

/**
 *
 * @author fpfister
 *
 */
public class DropAnnotationCommand extends AbstractTransactionalCommand {

	private static final boolean LOG = DParams.DropAnnotationCommand_LOG;

	private IDiagraphCommandHandler gefHandler;

	private static EClass eClass;

	private DropAnnotationCommand(IDiagraphCommandHandler gefHandler,
			TransactionalEditingDomain editingDomain, EClass eclass) {
		super(editingDomain, "drop annotation for " + eclass.getName(), null);
		this.gefHandler = gefHandler;
		this.eClass = eclass;
		// this.eAnnotation = eannotation;
	}

	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
			IAdaptable info) throws ExecutionException {
		return CommandResult.newOKCommandResult();
	}

	private static void execute(IDiagraphCommandHandler gefHandler,
			EClass eclass) {
		ICommandProxy a = new ICommandProxy(new DropAnnotationCommand(
				gefHandler, TransactionUtil.getEditingDomain(eclass), eclass));
		a.execute();
	}

	private static boolean isOnDiagram(EcoreDiagramEditor editor,
			EAnnotation eAnnotation) {
		for (Object object : editor.getDiagramEditPart().getChildren())
			if (object instanceof EAnnotationEditPart
					&& ((Node) ((EAnnotationEditPart) object).getModel())
							.getElement() == eAnnotation)
				return true;
		return false;
	}

	private static boolean isOnDiagram(EcoreDiagramEditor editor, EClass eClaz) {
		for (Object object : editor.getDiagramEditPart().getChildren())
			if (object instanceof EClassEditPart
					&& ((Node) ((EClassEditPart) object).getModel())
							.getElement() == eClaz)
				return true;
		return false;
	}

	private static void dropOnDiagram(IDiagraphCommandHandler handler,EcoreDiagramEditor editor) { //FP140521bbb
		try {
			if (LOG)
				clog("dropOnDiagram " + eClass.getName());
			String todrop = null;
			if (LOG)
				todrop = "";
			List<EAnnotation> diagraphAnnotations = new ArrayList<EAnnotation>();
			for (EAnnotation eAnnotation : eClass.getEAnnotations()) {
				if (eAnnotation.getSource().equals("diagraph")) {
					if (!isOnDiagram(editor, eAnnotation)
							&& isOnDiagram(editor, eClass)) {
						diagraphAnnotations.add(eAnnotation);
						if (LOG)
							todrop += eAnnotation.hashCode() + ";";
					}
				}
			}

			if (LOG)
				clog("todrop="+todrop);
			DropObjectsRequest dropObjectsRequest = new DropObjectsRequest();
			dropObjectsRequest.setObjects(diagraphAnnotations);
			dropObjectsRequest.setLocation(new Point(15, 15));
			editor.getDiagramEditDomain()
					.getDiagramCommandStack()
					.execute(
							editor.getDiagramEditPart().getCommand(
									dropObjectsRequest));
		} catch (Exception e) {
			handler.getControler().cerror("error while dropOnDiagram");
			clog("error while dropOnDiagram");
		}
	}

	private static void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	public static void execute(IDiagraphCommandHandler handler,
			EClass eclass, EcoreDiagramEditor editor) {
		execute(handler, eclass);
		dropOnDiagram(handler,editor);
	}

}
