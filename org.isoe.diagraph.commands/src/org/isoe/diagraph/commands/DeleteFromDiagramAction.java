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

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gmf.runtime.diagram.ui.actions.ActionIds;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

/**
 *
 * @author pfister
 * borrowed from gmf
 *
 */
public class DeleteFromDiagramAction extends Action {

	private List<IGraphicalEditPart> editParts;

	public void setEditParts(List<IGraphicalEditPart> editParts) {
		this.editParts = editParts;
	}

	@Override
	public void run() {
		super.run();
		Command cmd = getCommand();
		if (cmd!=null && cmd != UnexecutableCommand.INSTANCE)
		  cmd.execute();
	}

	public void init() {
		setId(ActionIds.ACTION_DELETE_FROM_DIAGRAM);
		setText("delete from diagram");
		setToolTipText("delete from diagram");
		ISharedImages workbenchImages = PlatformUI.getWorkbench().getSharedImages();
		setHoverImageDescriptor(
			workbenchImages.getImageDescriptor(
				ISharedImages.IMG_TOOL_DELETE));
		setImageDescriptor(
			workbenchImages.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
		setDisabledImageDescriptor(
			workbenchImages.getImageDescriptor(
				ISharedImages.IMG_TOOL_DELETE_DISABLED));

	}

	public DeleteFromDiagramAction(boolean dummy) {
		super();
		init();
	}

	protected boolean isSelectionListener() {
		return true;
	}


	protected Request createTargetRequest(){
		/* Create the delete request */
		GroupRequest deleteReq = new GroupRequest(
			RequestConstants.REQ_DELETE);
		return deleteReq;

	}


	/**
	 * Gets a command to execute on the operation set based on the target request
	 * @return a command to execute
	 */
	protected Command getCommand() {

		List objects = createOperationSet();
		if (objects==Collections.EMPTY_LIST)
			return UnexecutableCommand.INSTANCE;
		if (!supportViews(objects) || isCanonical(objects)){
			return null;
		}

		CompoundCommand deleteCC = new CompoundCommand("Delete From Diagram");
		for (Iterator iter = objects.iterator(); iter.hasNext();) {
			/* Get the next part */
			EditPart editPart = (EditPart) iter.next();
			/* Send the request to the edit part */
			deleteCC.add(editPart.getCommand(getTargetRequest()));
		}
		return deleteCC;
	}

	private Request getTargetRequest() {
		return new GroupRequest(RequestConstants.REQ_DELETE);
	}

	private boolean supportViews(List objects) {
		for (Iterator iter = objects.iterator(); iter.hasNext();) {
			Object object = iter.next();
			if (object instanceof GraphicalEditPart &&
				!((GraphicalEditPart)object).hasNotationView()){
				return false;
			}

		}
		return true;
	}

	/**
	 * Filters the selected objects and returns only editparts.
	 * @return a list of editparts selected.
	 *
	 */
	protected List createOperationSet() {
		List selection = editParts;//getSelectedObjects();
		if (selection.isEmpty() || !(selection.get(0) instanceof IGraphicalEditPart))
			return Collections.EMPTY_LIST;
		return selection;
	}

	/**
	 * Return true if any of cntxt's selectedObjects reside in
	 * containers that are canonical.
	 * Returns false if the selectedObjects reside in non-canonical containers,
	 * or if the selectedObjects do not have semantic elements.
	 * @param cntxt
	 * @return
	 */
	private boolean isCanonical(List selectedItems){

	    boolean isCanonical = false;
        if ( !selectedItems.isEmpty()) {

            for  (Iterator si = selectedItems.iterator(); si.hasNext() && !isCanonical;) {
                Object selected = si.next();
                if ( selected instanceof EditPart ) {
                    EditPart child = (EditPart)selected;
                    View view = (View)child.getAdapter(View.class);

                    if (  view == null
                            || view.getElement() == null
                            || view.getElement() instanceof View ) {
                        // If there is no element or the element is a view (e.g. diagram
                        // link) than we want to support delete from diagram. See
                        // bugzilla#148453.
                        isCanonical = false;
                        continue;
                    }
                    if (child instanceof ConnectionEditPart) {
                        ConnectionEditPart connection = (ConnectionEditPart)child;
                        isCanonical = ( !connection.isSemanticConnection()
                                || (isCanonical(connection.getSource())
                                        && isCanonical(connection.getTarget())) );
                    }
                    else {
                        isCanonical = isCanonical(child);
                    }
                }
            }
        }
        return isCanonical;
	}

	/**
	 * @param gep
	 * @return
	 */
	private boolean isCanonical(EditPart ep) {
	    EObject eObject = (EObject)ep.getAdapter(EObject.class);
        EditPart parent = ep.getParent();
        if ( eObject != null && parent != null ) { //sanity checks
            CanonicalEditPolicy cep = (CanonicalEditPolicy)parent.getEditPolicy(EditPolicyRoles.CANONICAL_ROLE);
            return cep != null
                && cep.isEnabled()
                && cep.canCreate(eObject);
        }
        return false;
	}


}
