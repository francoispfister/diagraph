
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
package org.isoe.diagraph.commands.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecoretools.legacy.diagram.edit.parts.EAnnotationEditPart;
import org.eclipse.emf.ecoretools.legacy.diagram.part.EcoreDiagramEditor;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramGraphicalViewer;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.isoe.diagraph.generic.GenericConstants;
import org.isoe.diagraph.lang.DiagraphKeywords;
import org.isoe.diagraph.parser.api.IDiagraphNotation;
import org.isoe.diagraph.parser.api.IDiagraphView;
import org.isoe.extensionpoint.gramgen.IAnnotationHelper;
import org.isoe.extensionpoint.gramgen.IDiagraphUtils;
import org.isoe.extensionpoint.gramgen.IGrammarGeneratorService;
import org.isoe.fwk.core.DParams;


import org.isoe.diagraph.controler.IDiagraphControler;  //FP140707refactored


/**
 * 
 * @author fpfister
 *
 */
public class DiagraphRuntimeHelper implements IAnnotationHelper {

//FP141227int
	
	@Override
	public Object getArguments() {
		return null;
	}


	private static DiagraphRuntimeHelper instance;
	private static IGrammarGeneratorService grammGenService;
	private IDiagraphNotation notation;
	private static final boolean LOG = DParams.SimilarityChecker_LOG;

	private EcoreDiagramEditor editor;
	private IDiagraphControler controler;

	private DiagraphRuntimeHelper() {
		super();
	}

	public static DiagraphRuntimeHelper getInstance(IGrammarGeneratorService service,IDiagraphNotation notation) {
		grammGenService = service;
		if (instance == null) {
			instance = new DiagraphRuntimeHelper();
			instance.controler = service.getControler();
			instance.notation = notation;
		}
		return instance;
	}

	public String getPointOfViewCandidate(EClass eclaz) {
		return notation.getParser().getPointOfViewCandidate(eclaz);
	}


	public List<String> getPointOfViewCandidates() {
		return notation.getParser().getPointOfViewCandidates();
	}

	public EClass getRootOfView(String eclassName) {
		return  notation.getParser().getPointOfView(eclassName);
	}

	public void logElements(Map<EClass, List<String>> m) {
		StringBuffer log = new StringBuffer();
		for (EClass cls : m.keySet()) {
			log.append(cls.getName()).append("\n");
			for (String annot : m.get(cls))
			  log.append("    ").append(annot).append("\n");
		}
		clog(log.toString());
	}

	public void setEditor(EcoreDiagramEditor editor) {
		this.editor = editor;
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}


	private void cleanAllDiagraphAnnotations(String view) { // TODO remove only a given view
		cleanFromDiagram(view);
		cleanFromModel(view);
	}

	private void cleanFromModel(String viu) { // TODO remove only a given view
		List<View> views = editor.getDiagram().getChildren(); // FP130511,
																// editor.getDiagram()
																// vs
																// editor.getDiagram().getDiagram()
																// ???
		// EList<View> views = editor.getDiagram().getDiagram().getChildren();
		EditingDomain editingDomain = editor.getEditingDomain();
		for (View view : views) {
			if (view instanceof Node) {
				Node node = (Node) view;
				if (node.getElement() instanceof EAnnotation) {
					EAnnotation eAnnotation = (EAnnotation) node.getElement();
					if (eAnnotation.getSource().equals(
							GenericConstants.ANNOT_DIAGRAPH_LITTERAL)) {
						editingDomain.getCommandStack().execute(
								RemoveCommand.create(editingDomain,
										node.getElement()));
					}
				}
			}
		}

	}

	private void cleanFromDiagram(String view) {
		EditingDomain editingDomain = editor.getEditingDomain();
		IDiagramGraphicalViewer viewer = editor.getDiagramGraphicalViewer();
		List parts = new ArrayList(viewer.getEditPartRegistry().values());
		for (Object o : parts) {
			if (o instanceof EAnnotationEditPart) {
				EAnnotationEditPart editpart = (EAnnotationEditPart) o;
				editingDomain.getCommandStack()
						.execute(
								RemoveCommand.create(editingDomain,
										editpart.getModel()));
			}
		}
	}



	private Map<EClass, List<String>> propagateByInheritance() {
		Map<EClass, List<String>> inhresult = new HashMap<EClass, List<String>>();
		String lang=((EPackage) editor.getDiagram().getElement()).getName(); //FP140611
		String viu = getControler().getCurrentView(18,lang,this.getClass().getName());
		for (View view : (List<View>) editor.getDiagram().getChildren()) {
			if (view instanceof Node) {
				Node node = (Node) view;
				if (node.getElement() instanceof EClass) {
					EClass annotated = (EClass) node.getElement();
				//	String viu = getControler().getCurrentView(18,this.getClass().getName());
					if (isDiagraphAnnotated(annotated,viu)) {
						for (EClass superAnnotated : annotated
								.getEAllSuperTypes()) {
							if (isDiagraphAnnotated(superAnnotated,viu)) {
								for (EAnnotation an : superAnnotated
										.getEAnnotations()) {
									if (an.getDetails().keySet()
											.contains(DiagraphKeywords.NODE)) {
										if (LOG) clog("propagateByInheritance NODE "+annotated.getName());
										inhresult.put(annotated,
												detail(DiagraphKeywords.NODE));
									}
									if (an.getDetails().keySet()
											.contains(DiagraphKeywords.LINK)) {
										if (LOG) clog("propagateByInheritance LINK "+annotated.getName());
										inhresult.put(annotated,
												detail(DiagraphKeywords.LINK));
									}
								}
							}
						}
					}
				}
			}
		}
		return inhresult;
	}




	private boolean isDiagraphAnnotated(EClass clas, String view) {
	  return notation.getParser().isDiagraphAnnotated(clas);
   }


	public Set<EClass> getElements() {
		return notation.getParser().getElements();
	}



	private List<String> detail(String element) {
		ArrayList<String> ants = new ArrayList<String>();
		ants.add(element);
		return ants;
	}

	private List<String> labelsFromAttributes(EClass c) {//FP140204ok
		ArrayList<String> result = new ArrayList<String>();
		EList<EAttribute> latt = c.getEAttributes();
		for (EAttribute att : latt) {
			// The attribute must have a type
			if (att.getEAttributeType() != null)
				result.add(DiagraphKeywords.LABEL + "=" + att.getName());
		}
		return result;
	}

	private void save() {
		editor.doSave(new NullProgressMonitor());
	}


	public void setControler_nu(IDiagraphControler controler) {
		this.controler = controler;
	}

	@Override
	public IDiagraphUtils getDiagraphUtils() {
		return (IDiagraphUtils) grammGenService;
	}

	@Override
	public IDiagraphControler getControler() {
		return controler;
	}

	@Override
	public EcoreDiagramEditor getEditor() {
		return editor;
	}




}
