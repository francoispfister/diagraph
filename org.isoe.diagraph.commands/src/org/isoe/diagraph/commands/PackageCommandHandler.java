 /**
 * Copyright (c) 2014 Laboratoire de Genie Informatique et Ingenierie de Production - Ecole des Mines d'Ales
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    François Pfister (ISOE-LGI2P) - initial API and implementation
 */
package org.isoe.diagraph.commands;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecoretools.legacy.diagram.part.EcoreDiagramEditor;
import org.isoe.diagraph.lang.internal.utils.DiagraphAnnotationUtils;
import org.isoe.extensionpoint.gramgen.IAnnotationHelper;
import org.isoe.extensionpoint.gramgen.IPackageCommandHandler;


/**
 *
 * @author fpfister
 *
 */
public class PackageCommandHandler implements IPackageCommandHandler {

	
	private Map<EPackage, List<String>>[] arguments;
	private IAnnotationHelper helper;
	private boolean initialized = false;

	public PackageCommandHandler(IAnnotationHelper helper) {
		this.helper = helper; // FP131114
	}

	public PackageCommandHandler(DiagraphAnnotationUtils helper,
			Map<EPackage, List<String>>[] annotations) {
		this.arguments = annotations;
	}

	public void checkAllPackageAnnots() {

		if (!initialized)
			initializeElement(helper.getEditor());
		for (EPackage p : arguments[0].keySet())
			CheckAnnotationCommand.exec(p);	
	}
	
	@Override
	public void initializeElement(EcoreDiagramEditor editor) {
		// wrap around gef mysteries
		// see http://www.eclipse.org/forums/index.php/t/369126/
		// ADDING an element
		initialized = true;
		
		/*
		DiagramEditPart diagramEditPart = editor.getDiagramEditPart();
		EClass temp = EcoreFactory.eINSTANCE.createEClass();

		CreateElementRequest createElementRequest = new CreateElementRequest(
				EcoreElementTypes.EClass_1001);
		createElementRequest.setNewElement(temp);
		CreateElementRequestAdapter createElementRequestAdapter = new CreateElementRequestAdapter(
				createElementRequest);
		CreateViewAndElementRequest.ViewAndElementDescriptor veDescriptor = new CreateViewAndElementRequest.ViewAndElementDescriptor(
				createElementRequestAdapter,
				Node.class,
				((IHintedType) EcoreElementTypes.EClass_1001).getSemanticHint(),
				diagramEditPart.getDiagramPreferencesHint());
		CreateViewAndElementRequest veRequest = new CreateViewAndElementRequest(
				veDescriptor);
		Point p = new Point(150, 150);
		veRequest.setLocation(p);
		org.eclipse.gef.commands.Command command = diagramEditPart
				.getCommand(veRequest);
		command.execute();

		// DELETING that element

		EClass cls = (EClass) createElementRequestAdapter.resolve();
		EditingDomain editingDomain = editor.getEditingDomain();
		editingDomain.getCommandStack().execute(
				RemoveCommand.create(editingDomain, cls));
*/
	}

	@Override
	public void changeColor(String view, int color) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void highlightAnnotation(String key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dropAll() {
		// TODO Auto-generated method stub
		
	}


	







}
