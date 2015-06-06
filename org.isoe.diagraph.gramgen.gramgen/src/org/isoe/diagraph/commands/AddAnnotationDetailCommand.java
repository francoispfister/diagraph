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

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.isoe.extensionpoint.gramgen.IDiagraphUtils;
import org.isoe.fwk.core.DParams;

/**
 *
 * @author bnastov
 *
 */
public class AddAnnotationDetailCommand extends AbstractTransactionalCommand {
	private EClass parent;
	private String key;
	private String view;
	private IDiagraphUtils diagraphUtils;
	private boolean LOG = DParams.AddAnnotationAttributeCommand_LOG;

	// 130630
	public AddAnnotationDetailCommand(IDiagraphUtils utils, TransactionalEditingDomain domain,
			EClass parent, String view, String key) {
		super(domain, "adding " + key + " to view " + view +" for EClass "+parent, null);
		this.diagraphUtils = utils;
		this.key = key;
		this.parent = parent;
		this.view = view;
		if (LOG) clog("adding " + key + " to view " + view +" for EClass "+parent.getName());
	}

	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
			IAdaptable info) throws ExecutionException {
		if (LOG) clog("executing ") ;
		String result = diagraphUtils.addDetailToDiagraphAnnotation(parent,view,key);
		if (result.equals("ok")){
			clog(key+" added to "+view+" "+result);
			return CommandResult.newOKCommandResult();
		}else{
			if (LOG) clog("warning while adding "+key+" to "+view+" ("+result+")");
			return CommandResult.newWarningCommandResult(result, "");
		}
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	public static void execute(IDiagraphUtils diagraphUtils, EClass parent, String view, String key) {
		ICommandProxy a = new ICommandProxy(new AddAnnotationDetailCommand(diagraphUtils,
				TransactionUtil.getEditingDomain(parent), parent, view, key));
		  a.execute();
	}

}
