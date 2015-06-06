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
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.tools.ToolUtilities;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.commands.CommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.swt.dnd.DND;

/**
 *
 * @author fpfister
 * adapted from gmf
 *
 */
public abstract class DropCommand implements RequestConstants{

	private EditPart host;

	protected Command getDropCommand(ChangeBoundsRequest request) {
		ChangeBoundsRequest req = new ChangeBoundsRequest(REQ_ADD);
		req.setEditParts(request.getEditParts());
		req.setMoveDelta(request.getMoveDelta());
		req.setSizeDelta(request.getSizeDelta());
		req.setLocation(request.getLocation());
		req.setResizeDirection(request.getResizeDirection());
		Command cmd = getHost().getCommand(req);
		if (cmd == null || !cmd.canExecute()) {
			return getDropObjectsCommand(castToDropObjectsRequest(request));
		}

		return cmd;
	}



	/**
	 * getDropObjectsCommand
	 * This provides a generic implementation for handling the
	 * DropObjectsRequest which checks for EObject and gets a command
	 * from the getDropEementCommand method.
	 * @param request DropObjectsRequest that is being handled
	 * @return Command that handles the request.
	 */
	protected Command getDropObjectsCommand(DropObjectsRequest request) {
		CompoundCommand cm = new CompoundCommand();
		Iterator elements = request.getObjects().iterator();
		while (elements.hasNext()) {
			Object obj = elements.next();
			if (obj instanceof EObject) {
				Command cmd = getDropElementCommand((EObject)obj, request);
			if (cmd != null)
				cm.add(cmd);
		}
		}

		if (cm.isEmpty())
			return null;

		return new ICommandProxy(new CommandProxy(cm.unwrap()));
	}



	public EditPart getHost() {
		return host;
	}

	public void setHost(EditPart host) {
		this.host = host;
	}


	/**
	 * getDropElementCommand
	 * Returns a command to handle a DropObjectsRequest of an EObject
	 *
	 * @param element EObject that is being dropped.
	 * @param request DropObjectsRequest that the element has retrieved from.
	 * @return Command that handles the dropping of the EObject element.
	 */
	protected Command getDropElementCommand(EObject element, DropObjectsRequest request) {
		return null;
	}

	/**
	 *
	 * @param request
	 * @return int
	 */
	protected int getRequiredDragDetail(Request request) {
		return DND.DROP_COPY;
	}

	/**
	 * Retrieves the list of elements being dropped
	 * @param request the request
	 * @return List of elements
	 */
	protected DropObjectsRequest castToDropObjectsRequest(ChangeBoundsRequest request) {
		Iterator editParts =
			ToolUtilities
				.getSelectionWithoutDependants(request.getEditParts())
				.iterator();

		List elements = new ArrayList();
		while (editParts.hasNext()) {
			EditPart editPart = (EditPart) editParts.next();
			if (editPart instanceof IGraphicalEditPart) {
				EObject element = ViewUtil
					.resolveSemanticElement((View) ((IGraphicalEditPart) editPart)
						.getModel());
				if (element != null)
					elements.add(element);
			}
		}

		DropObjectsRequest req = new DropObjectsRequest();
		req.setObjects(elements);
		req.setAllowedDetail(DND.DROP_COPY | DND.DROP_MOVE | DND.DROP_LINK);
		req.setLocation(request.getLocation());
		req.setRequiredDetail(getRequiredDragDetail(request));
		return req;
	}


}
