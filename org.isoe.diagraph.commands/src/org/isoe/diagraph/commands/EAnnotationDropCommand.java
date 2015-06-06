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

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GroupEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ListCompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;

/**
 *
 * @author fpfister
 *
 */
public class EAnnotationDropCommand extends DropCommand {


	/**
	 * Handles dropping attributes from a class to the diagram to show as an
	 * association.
	 * @see org.eclipse.gmf.runtime.diagram.ui.editpolicies.DragDropEditPolicy#getDropCommand(org.eclipse.gef.requests.ChangeBoundsRequest)
	 */
	protected Command getDropCommand(ChangeBoundsRequest request) {
		if (request.getEditParts().size() > 0) {
			EditPart parentEP =
				((IGraphicalEditPart) request.getEditParts().get(0)).getParent();
			if (parentEP instanceof ListCompartmentEditPart) {
				Object originalType = request.getType();
				request.setType(RequestConstants.REQ_SHOW_AS_ALTERNATE_VIEW);
				Command cmd = parentEP.getCommand(request);
				request.setType(originalType);
				if (cmd != null) {
					return cmd;
				}
			} else if (parentEP instanceof GroupEditPart) {
                // Dragging shapes outside the group will cause the group to
                // grow and thus should not reparent.
                return null;
            }
		}
		return super.getDropCommand(request);
	}

}
