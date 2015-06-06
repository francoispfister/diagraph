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
package org.isoe.diagraph.runtimefactory;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecoretools.legacy.diagram.part.EcoreDiagramEditor;

/**
 *
 * @author fpfister
 *
 */
public interface IDiagraphRuntimeFactory {

	void selectDsml(String name);

	void redropDiagraphAnnotations(EPackage currentPackage);

	boolean checkConcreteSyntax();

	boolean addNewPovRoot(String curntView, String newView, int newId,
			EClass curntClass, EClass povclass);

	boolean[] invokeDiagraphAction(String actionid);

	EcoreDiagramEditor isDiagramOpen(String string);

	void openDiagram(EcoreDiagramEditor ecoreDiagramEditor, String apath);

	boolean[] executeDiagraphActions(String diagraphactions);

	String getLastView(String diagraphactions);

}
