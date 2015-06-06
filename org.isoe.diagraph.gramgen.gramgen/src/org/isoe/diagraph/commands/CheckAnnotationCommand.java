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
import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecoretools.legacy.diagram.edit.parts.EAnnotationEditPart;
import org.eclipse.emf.ecoretools.legacy.diagram.part.EcoreDiagramEditor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.emf.workspace.AbstractEMFOperation;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.services.layout.LayoutService;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.Node;
import org.isoe.diagraph.views.ViewConstants;
import org.isoe.extensionpoint.gramgen.IDiagraphCommandHandler;
import org.isoe.fwk.core.DParams;

/**
 * 
 * @author fpfister
 * 
 */
public class CheckAnnotationCommand extends AbstractTransactionalCommand {

	private static final boolean LOG = DParams.CheckAnnotationCommand_LOG;

	private static EClass eClass;
	private static EPackage ePackage;

	private static TransactionalEditingDomain editingDomain;

	

	private CheckAnnotationCommand(TransactionalEditingDomain edDomain, EClass eclass) {
		super(edDomain, "check annotation for " + eclass.getName(), null);
		//this.gefHandler = gefHandler;
		editingDomain = edDomain;
		this.eClass = eclass;
	}

	public CheckAnnotationCommand(TransactionalEditingDomain edDomain,
			EPackage pak) {
		super(edDomain, "check annotation for " + pak.getName(), null);
		//this.gefHandler = gefHandler;
		editingDomain = edDomain;
		this.ePackage = pak;
	}

	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
			IAdaptable info) throws ExecutionException {
		return CommandResult.newOKCommandResult();
	}

	private static void doCheck(final List<EAnnotation> diagraphAnnotations) {// FP140429
		//TransactionalEditingDomain transaction = TransactionUtil.getEditingDomain(eClass);
		AbstractEMFOperation operation = new AbstractEMFOperation(editingDomain,
				"check_diagraph_annotations", null) {
			protected IStatus doExecute(IProgressMonitor monitor,
					IAdaptable info) throws ExecutionException {
				for (EAnnotation eAnnotation : diagraphAnnotations) {
					for (Map.Entry<String, String> entry : eAnnotation
							.getDetails()) {
						String k = entry.getKey();
						if (entry.getValue() == null) {
							if (LOG)
								clog("annotation value is null for entry " + k);
							entry.setValue(DParams.NULL_ANNOTATION_VALUE);
						}
					}

				}
				return Status.OK_STATUS;
			}
		};
		try {
			operation.execute(new NullProgressMonitor(), null);
		} catch (Exception e) {
			throw new RuntimeException(
					"error during checking diagraph annotation: "
							+ e.getMessage() + " !!!!");
		}
	}

	private static void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	private static void check() {
		if ( eClass!=null)
			checkEClass();
		else
			checkEPackage();
	}
	
	

	private static void checkEClass() {
		List<EAnnotation> diagraphAnnotations = new ArrayList<EAnnotation>();
		for (EAnnotation eAnnotation : eClass.getEAnnotations()) {
			if (eAnnotation.getSource().equals("diagraph")) {
				diagraphAnnotations.add(eAnnotation);
			}
		}
		doCheck(diagraphAnnotations);
	}
	
	private static void checkEPackage() {
		List<EAnnotation> diagraphAnnotations = new ArrayList<EAnnotation>();
		for (EAnnotation eAnnotation : ePackage.getEAnnotations()) {
			//if (eAnnotation.getSource().equals("diagraph")) {
				diagraphAnnotations.add(eAnnotation);
			//}
		}
		doCheck(diagraphAnnotations);
	}

	public static void exec(EClass eclass) {//TODO simplify
		//execute(eclass);
		ICommandProxy a = new ICommandProxy(new CheckAnnotationCommand(TransactionUtil.getEditingDomain(eclass), eclass));
		a.execute();
		check();
	}
	
	public static void exec(EPackage pak) {//TODO simplify
		//execute(eclass);
		ICommandProxy a = new ICommandProxy(new CheckAnnotationCommand(TransactionUtil.getEditingDomain(pak), pak));
		a.execute();
		check();
	}

}
