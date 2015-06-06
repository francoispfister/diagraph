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

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecoretools.legacy.diagram.part.EcoreDiagramEditor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.SetValueCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.isoe.diagraph.generic.GenericConstants;
import org.isoe.extensionpoint.gramgen.IDiagraphCommandHandler;
import org.isoe.extensionpoint.gramgen.IDiagraphUtils;
import org.isoe.fwk.core.DParams;


/**
 *
 * @author pfister
 * snippet
 */
public class XXCommand extends AbstractTransactionalCommand{
	private IDiagraphCommandHandler gefHandler;

	private static EClass contexClass;


	private XXCommand(IDiagraphCommandHandler gefHandler,TransactionalEditingDomain editingDomain, EClass caller, List<String> keys)
	{
	    super(editingDomain, "a message", null);
	    this.gefHandler=gefHandler;
	    contexClass = caller;

	}

	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
			IAdaptable info) throws ExecutionException {

		//contextAnnotation = createEAnnotation(contexClass, contextKeys);
		return CommandResult.newOKCommandResult();
	}



	private static EAnnotation createZZ(EClass parent, List<String> keys){
		EAnnotation annot = EcoreFactory.eINSTANCE.createEAnnotation();
		annot.setSource(GenericConstants.ANNOT_DIAGRAPH_LITTERAL);
		annot.setEModelElement( parent );
		for (String key : keys) {
			annot.getDetails().put(key, DParams.NULL_ANNOTATION_VALUE);
		}
		return annot;
	}


	private static void createAnnotation(IDiagraphCommandHandler gefHandler,EClass parent, List<String> keys){
		ICommandProxy a = new ICommandProxy(new XXCommand(gefHandler,TransactionUtil.getEditingDomain(parent), parent, keys));
		a.execute();
	}

	public static void execute(IDiagraphCommandHandler gefHandler,EClass parent, List<String> keys, String view, int viewId, EcoreDiagramEditor editor, IDiagraphUtils utils){
		createAnnotation(gefHandler,parent, keys);
		EObject xx=null;
		doit(xx, editor);
		//gefHandler.changeColor(view, viewId);
	}

	private static void doit(EObject annot, EcoreDiagramEditor editor){

		TransactionalEditingDomain editingDomain = null;//((IGraphicalEditPart) selectedPart).getEditingDomain();
		final EObject elementToEdit=null;
		final EStructuralFeature feature=null;

		SetRequest setRequest = new SetRequest(editingDomain, elementToEdit,feature, "name");
		setRequest.setParameter("oldValue", ((ENamedElement) elementToEdit).getName());

		SetValueCommand operation = new SetValueCommand(setRequest){
			  @Override
			  protected IStatus doUndo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				  elementToEdit.eSet(feature,
			    this.getRequest().getParameter("oldValue"));
			    return Status.OK_STATUS;
			  }

			  @Override
			  public boolean canUndo() {
			    return true;
			  }

			  @Override
			  protected IStatus doRedo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				  elementToEdit.eSet(feature, ((SetRequest)this.getRequest()).getValue());
			    return Status.OK_STATUS;
			  }

			  @Override
			  public boolean canRedo() {
			    return true;
			  }
			};



	}






}
