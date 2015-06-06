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
package org.isoe.extensionpoint.gramgen;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecoretools.legacy.diagram.part.EcoreDiagramEditor;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;

/**
 *
 * @author bnastov
 * @author fpfister
 *
 */
import org.isoe.diagraph.controler.IDiagraphControler;  //FP140707refactored
import org.isoe.extensionpoint.diagraph.action.DiagraphService;  //FP140707_c_refactored
public interface IGrammarGeneratorService extends DiagraphService {
	void addDetail(EModelElement element, String view, String argument);
	String addDetailToDiagraphAnnotation(EClass parent, String view, String key);
	void addManualView(EClass rootClass);
	EClass addView();
	boolean checkConcreteSyntax(String view);
	void clearConcreteSyntax(String view);
	void computePov();
	Map<EClass, List<String>> createGenericAnnotation(Map<EClass, List<String>> annotations, String view, //int povid,
			EClass target);
	Map<EClass, List<String>> createLinkAnnotation(Map<EClass, List<String>> annotations, String view, //int povid,
			EClass target);
	Map<EClass, List<String>> createNodeAnnotation1(
			Map<EClass, List<String>> annotations, String pov, EClass target);
	Map<EClass, List<String>> createPovAnnotation(Map<EClass, List<String>>[] maps,
			String frompov, String topov,  EClass fromClass, //int povid,
			EClass toClass, boolean addNavAnnotation); // int level,
	void dropConcreteSyntax(String view, EClass selectedClass, boolean register);
	void generate();
	void generateDefaultAnnotations();
	void generatePointOfViewCandidates(IDiagraphControler iDiagraphControler);
	EClass getComputedPov();
	IDiagraphControler getControler();
	String getDiagraphActions();
	public EcoreDiagramEditor getEcoreDiagramEditor();
	String getPovCandidate(EClass eclaz);
	public String getView();
	EClass getViewRoot(String eclassName);
	void init();
	boolean isElementType(String view, String key, EClass eclaz);
	List<EClass> propagateNodes(String view, EClass c);
	void registerCommand(String cmd);
	void removeAnnotation(IGraphicalEditPart graphicalEditPart);
	void setEModelElement(EModelElement eModelElement);
	void setView(String view); // FP131008
	void verifyAfterPov(EClass rootClass); //workaround: super classes are not always tagged
}
