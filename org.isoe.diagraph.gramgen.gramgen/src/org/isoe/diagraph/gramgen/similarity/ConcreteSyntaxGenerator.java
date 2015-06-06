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
package org.isoe.diagraph.gramgen.similarity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecoretools.legacy.diagram.edit.parts.EAnnotationEditPart;
import org.eclipse.emf.ecoretools.legacy.diagram.part.EcoreDiagramEditor;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramGraphicalViewer;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.isoe.diagraph.common.DiagraphException;
import org.isoe.diagraph.diastyle.helpers.IErrorReporter;
import org.isoe.diagraph.generic.GenericConstants;
import org.isoe.diagraph.gramgen.similarity.response.CanvasResponse;
import org.isoe.diagraph.gramgen.similarity.response.CompartmentFromClassAssocResponse;
import org.isoe.diagraph.gramgen.similarity.response.CompartmentResponse;
import org.isoe.diagraph.gramgen.similarity.response.LabeledAssociationResponse;
import org.isoe.diagraph.gramgen.similarity.response.MatchingResponse;
import org.isoe.diagraph.gramgen.similarity.response.ReferenceResponse;
import org.isoe.diagraph.gramgen.similarity.subGraphIsomorphism.EcoreSimilarityChecker;
import org.isoe.diagraph.lang.DiagraphKeywords;
import org.isoe.extensionpoint.gramgen.IAnnotationHelper;
import org.isoe.extensionpoint.gramgen.IDiagraphUtils;
import org.isoe.extensionpoint.gramgen.IGrammarGeneratorService;
import org.isoe.fwk.core.DParams;
import org.isoe.gramgen.pattern.association.AssociationPackage;
import org.isoe.gramgen.pattern.classassociation.ClassassociationPackage;
import org.isoe.gramgen.pattern.compartiment.CompartimentPackage;

/**
 *
 * @author bnastov old name class name was SimilarityChecker
 */
import org.isoe.diagraph.controler.IDiagraphControler;  //FP140707refactored
public class ConcreteSyntaxGenerator implements IAnnotationHelper {

	private Map<EClass, List<String>>[] arguments = new Map[1];

	@Override
	public Object getArguments() {
		return arguments;
	}

	private static ConcreteSyntaxGenerator instance;
	private static IGrammarGeneratorService grammGenService;
	// private static final String THIS_PLUGIN_ID =
	// "org.isoe.diagraph.gramgen.gramgen";
	// private static final String PATTERN_MODELS = "patterns";
	private static final boolean LOG = DParams.SimilarityChecker_LOG;
	private static final boolean LOG2 = false;
	/*
	 * private String labelSimilarityModelURI_; private String
	 * associationModelURI_; private String compartimentModelURI_;
	 */

	private EPackage labelSimilarityModel;
	private EPackage associationModel;
	private EPackage compartimentModel;

	private Map<EClass, List<String>> elements;
	private Map<String, EClass> povCandidates;

	// private static Diagram diagram = null; //removed
	private EcoreDiagramEditor editor;
	private IDiagraphControler controler;
	private EClass fallBackPov;

	private ConcreteSyntaxGenerator() {
		super();
	}

	/*
	 *
	 * private String getPathFrmProjectElement_old(String element) { URI uri =
	 * URI.createPlatformPluginURI(THIS_PLUGIN_ID + element, false); URI curi =
	 * CommonPlugin.resolve(uri); File monfichier = new
	 * File(curi.toFileString()); return monfichier.getAbsolutePath(); }
	 */
	/*
	 * public static ConcreteSyntaxGenerator
	 * initInstance(IGrammarGeneratorService service) { //FP140117 instance =
	 * null; return getInstance(service); }
	 */

	public static ConcreteSyntaxGenerator getInstance(
			IGrammarGeneratorService service) {
		grammGenService = service;
		if (instance == null) {
			instance = new ConcreteSyntaxGenerator();
			instance.associationModel = AssociationPackage.eINSTANCE;
			instance.compartimentModel = CompartimentPackage.eINSTANCE;
			instance.labelSimilarityModel = ClassassociationPackage.eINSTANCE;
			instance.controler = service.getControler();
			// FP130527 impossible de lire des fichiers dans un jar de plugin
			// URI uri =
			// URI.createPlatformPluginURI("org.isoe.gramgen.pattern.association",
			// false);
			// instance.labelSimilarityModelURI_ =
			// instance.getPathFrmProjectElement("/"+PATTERN_MODELS+"/labeled-association-model.ecore");
			// instance.associationModelURI_ =
			// instance.getPathFrmProjectElement("/"+PATTERN_MODELS+"/association.ecore");
			// instance.compartimentModelURI_ =
			// instance.getPathFrmProjectElement("/"+PATTERN_MODELS+"/compartiment.ecore");
		}
		return instance;
	}

	public EClass getRootOfView_(String eclassName) {
		return povCandidates.get(eclassName);
	}

	public String getPointOfViewCandidate(EClass eclaz) {
		for (EClass cls : elements.keySet()) {
			// All classes that have node associated annotation
			List<String> annots = elements.get(cls);
			if (annots.contains(DiagraphKeywords.NODE)) {
				// Excluding the non containing classes
				List<EReference> containmentRefs = cls.getEAllContainments();
				if (containmentRefs.size() > 0) {
					// Excluding the pov class
					if (!annots.contains(DiagraphKeywords.POINT_OF_VIEW)) {
						if (eclaz == cls) {
							if (LOG)
								clog("GPOVC " + DParams.VIEW_PREFIX
										+ cls.getName().toLowerCase());
							return DParams.VIEW_PREFIX
									+ cls.getName().toLowerCase();
						}
					}
				}
			}
		}
		return "unknown";
	}

	public List<String> getPointOfViewCandidates() {

		povCandidates = new HashMap<String, EClass>();// FP130511
		List<String> povablez = new ArrayList<String>();
		try {
		for (EClass cls : elements.keySet()) {
			// All classes that have node associated annotation
			List<String> annots = elements.get(cls);
			if (annots.contains(DiagraphKeywords.NODE)) {
				// Excluding the non containing classes
				List<EReference> containmentRefs = cls.getEAllContainments();
				if (containmentRefs.size() > 0) {
					// Excluding the pov class
					if (!annots.contains(DiagraphKeywords.POINT_OF_VIEW)) {
						if (LOG)
							clog("GPOVCs put " + cls.getName());
						povCandidates.put(cls.getName(), cls);
					}
				}
			}
		}
		for (Object povable : povCandidates.keySet().toArray())
			povablez.add((String) povable);
		} catch (Exception e) {
			cerror("error on getPointOfViewCandidates"); //FP150518
		}
		return povablez;
	}

	private void cerror(String mesg) {
		instance.controler.cerror(mesg);
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

	public Map<EClass, List<String>> getElements() {
		if (LOG) {
			clog("concrete syntax elements:");
			logElements(elements);
		}
		arguments[0] = elements;
		return elements;
	}

	// one shot operation, no cache is used
	public EClass computePov(EcoreDiagramEditor editor, EClass fallBackPov) { // FP130611
																				// separated
		this.editor = editor;
		this.fallBackPov = fallBackPov; // if a pov cannot be calculated, take
										// fallBackPov as the default value
		return ((CanvasResponse) checkForPov()).getCanvas();
		// EcoreSimilarityCheckerV3.cleanCache();
	}

	public void generate(IErrorReporter logger,EcoreDiagramEditor editor, EClass fallBackPov,
			boolean cleanCacheOnFinish) {

		this.editor = editor;
		this.fallBackPov = fallBackPov; // if a pov cannot be calculated, take
										// this as a default value
		// String view_ = "default";
		// grammGenService.cleanAllDiagraphAnnotation();
		// cleanAllDiagraphAnnotations_(view); //TODO work with a view
		MatchingResponse canvas = checkForCanvasModel();
		if (canvas == null){
			System.err.println("cs generation error");
			return;
		}
		EClass computedCanvas = ((CanvasResponse) canvas).getCanvas();
		if (LOG)
			clog("computedCanvas="
					+ (computedCanvas == null ? "null" : computedCanvas
							.getName()));
		List<MatchingResponse> classAssocs = checkForLabeledAssociationModel();
		List<MatchingResponse> conts = checkForContainmentModel();
		List<MatchingResponse> refs = checkForAssociationModel();
		List<MatchingResponse> resultMatching = new ArrayList<MatchingResponse>();
		resultMatching.add(canvas);
		resultMatching.addAll(classAssocs);
		resultMatching.addAll(conts);
		resultMatching.addAll(refs);
		AnnotationFilter annotationFilter = null;
		try {
			annotationFilter = new AnnotationFilter(resultMatching,
					EcoreSimilarityChecker.getVisualElements());
		} catch (Exception e) {
			controler.log("info",
					"Diagraph error: A Point of View cannot be found !!");
			clog("A Point of View cannot be found");
		}
		if (annotationFilter != null) {
			elements = annotationFilter.getAssociatedAnnotations(); // FP140117
			if (LOG)
				clog("__generate[");
			// if (LOG) logNodes_(all);
			Map<EClass, List<String>> nonVisual_ = propagateNonVisual();
			elements.putAll(nonVisual_);
			// if (LOG) logNodes_(nonVisual);
			Map<EClass, List<String>> inherited_ = propagateByInheritance();
			// all.putAll(inherited_); //FP140117
			// if (LOG) logNodes_(inherited);
			if (LOG)
				clog("]generate__");
			// printDiagram();
			save();
		} else {
			elements = new HashMap<EClass, List<String>>();
			// nonVisual_ = new HashMap<EClass, List<String>>();
			// inherited_ = new HashMap<EClass, List<String>>();
		}

		arguments[0] = elements;

		// finally
		// we delete the cache after the process of similarity is finished
		if (cleanCacheOnFinish)
			EcoreSimilarityChecker.cleanCache();

	}

	/*
	 * public void setAssociatedAnnotations( Map<EClass, List<String>>
	 * associatedAnnotations) { this.result = associatedAnnotations;
	 *
	 * }
	 */

	/*
	 * public void setAssociatedAnnotations( Map<EClass, List<String>>
	 * addDiagraphAnnotation) { associatedAnnotations = addDiagraphAnnotation; }
	 */

	public void setEditor(EcoreDiagramEditor editor) {
		this.editor = editor;
	}

	public void setFallBackPov(EClass fallBackPov) {
		this.fallBackPov = fallBackPov;
	}

	/*
	 * public void addEmptyAnnotation_old_nu(EcoreDiagramEditor editor, EClass
	 * fallBackPov, String kind) { this.editor = editor; this.fallBackPov =
	 * fallBackPov; // if a pov cannot be calculated, take // this as a default
	 * value result = new HashMap<EClass, List<String>>(); // FP130606 // not //
	 * to // let // it be // null List<String> ll = new ArrayList<String>(); if
	 * (kind != null && !kind.isEmpty()) ll.add(kind); result.put(fallBackPov,
	 * ll); new GefHandler(this, result).generateAll(true);// controler,editor,
	 * }
	 */

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////CANVAS//////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Pattern 1 Checking for canvas class, a pattern model isn't needed since
	 * we consider that we are manipulating only one package which implies that
	 * the canvas class is always the first package node
	 *
	 * @param editor
	 * @param uRI
	 */
	private MatchingResponse checkForCanvasModel() {
		// get the package
		EPackage ePackage = (EPackage) (editor.getDiagram().getElement());
		MatchingResponse result = null;
		try {
			result = new CanvasResponse(
					EcoreSimilarityChecker.computeVisualNodes(ePackage,
							fallBackPov));
		} catch (DiagraphException e) {
			controler.log("info", "Diagraph error: " + e.getMessage());
		}
		return result;
	}

	/**
	 * Short operation not followed by other searches
	 *
	 * @return the response for the canvas
	 */
	private MatchingResponse checkForPov() { // FP130611
		MatchingResponse result = null;
		try {
			result = new CanvasResponse(EcoreSimilarityChecker.computePOV(
					(EPackage) (editor.getDiagram().getElement()), fallBackPov));
		} catch (DiagraphException e) {
			controler.log("info", "Diagraph error: " + e.getMessage());
		}
		return result;
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////ASSOCIATION
	// CLASS//////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Labeled association similarity checking method
	 *
	 * @param ePackage
	 *            the package of the checking model
	 * @param pattern1uri2
	 *            the uri of the pattern model
	 * @return
	 */
	private List<MatchingResponse> checkForLabeledAssociationModel() {
		if (LOG)
			clog("Checking for labeled associations");
		// Get the package
		EPackage ePackage = (EPackage) (editor.getDiagram().getElement());
		// Call the similarity method
		ArrayList<EClass> mappingResult = null;
		;
		try {
			mappingResult = EcoreSimilarityChecker.checkSimilarity(
					labelSimilarityModel, ePackage, fallBackPov);
		} catch (DiagraphException e) {
			controler.log("info", "Diagraph error: " + e.getMessage());
		}
		// Filtering
		if (mappingResult != null)
			return filterLabelledAssociationMapping(mappingResult);
		else
			return new ArrayList<MatchingResponse>(); // return an empty list

	}

	private void clog(String mesg) {
		if (LOG || LOG2)
			System.out.println(mesg);

	}

	/**
	 * Pattern 3 Filter 1
	 *
	 * @param mappingResult
	 * @return
	 */
	private List<MatchingResponse> filterLabelledAssociationMapping(
			ArrayList<EClass> mappingResult) {

		ArrayList<MatchingResponse> result = new ArrayList<MatchingResponse>();

		for (EClass cl : mappingResult) {
			List<MatchingResponse> response = checkLabeledAssociationBySourceClass(cl);
			if (response.size() > 0) {
				for (MatchingResponse lr : response) {
					result.add(lr);
				}
			}
		}

		return result;
	}

	/**
	 * This method verify is the source class belongs to a
	 * labelledAssociationPattern and if so, it returns the classes that match
	 * this pattern.
	 *
	 * @param sourceClass
	 * @return more then 1 patterns can be assigned to 1class
	 */
	private List<MatchingResponse> checkLabeledAssociationBySourceClass(
			EClass sourceClass) {

		ArrayList<MatchingResponse> result = new ArrayList<MatchingResponse>();

		// Find the containment reference
		for (EReference sl : sourceClass.getEReferences()) {
			if (sl.isContainment()) {
				EClass probableLabeledClass = getEdgeTargetClass(sl);
				if (probableLabeledClass != null) {
					MatchingResponse candidate = checkLabeledClassFromSourceClass(
							probableLabeledClass, sourceClass, sl);
					if (candidate != null) {
						result.add(candidate);
					}
				}
			}
		}
		return result;
	}

	private MatchingResponse checkLabeledClassFromSourceClass(
			EClass probableLabeledClass, EClass sourceClass, EReference sl) {

		EReference ls = null;
		ArrayList<EReference> setLT = new ArrayList<EReference>();
		ArrayList<EClass> targetClasses = new ArrayList<EClass>();

		for (EReference ref : probableLabeledClass.getEReferences()) {

			if (!isLabeledClassReference(ref)) {
				// Association that have not 0-1 cardinality in the labeled
				// class
				return null;
			}

			EClass cl = getEdgeTargetClass(ref);
			if (cl != null && cl.equals(sourceClass)) {
				if (ls != null) {
					// More then 1 LS found
					return null;
				}
				ls = ref;
			} else {
				if (cl != null) {
					setLT.add(ref);
					targetClasses.add(cl);
				}
			}

		}

		if (targetClasses.size() > 0)
			return new LabeledAssociationResponse(sourceClass,
					probableLabeledClass, targetClasses, ls, setLT, sl);
		else
			return new CompartmentFromClassAssocResponse(sourceClass,
					probableLabeledClass, sl);
	}

	/**
	 * Test if the reference is a class association reference
	 *
	 * @param ref
	 * @return
	 */
	private boolean isLabeledClassReference(EReference ref) {
		return ref.getLowerBound() == 0 && ref.getUpperBound() == 1
				&& !ref.isContainment();
	}

	/**
	 * Returns the class that is references by the reference
	 *
	 * @param ref
	 * @return
	 */
	private EClass getEdgeTargetClass(EReference ref) {
		if (ref.getEType() instanceof EClass)
			return (EClass) ref.getEType();
		else
			return null;
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////ASSOCIATION/////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////

	private List<MatchingResponse> checkForAssociationModel() {

		EPackage ePackage = (EPackage) (editor.getDiagram().getElement());
		// Call the similarity method
		ArrayList<EClass> mappingResult = null;
		try {
			mappingResult = EcoreSimilarityChecker.checkSimilarity(
					associationModel, ePackage, fallBackPov);
		} catch (DiagraphException e) {
			controler.log("info", "Diagraph error: " + e.getMessage());
		}
		if (mappingResult != null)
			return arangeMatchedAssociations(mappingResult);
		else
			return new ArrayList<MatchingResponse>();
	}

	private List<MatchingResponse> arangeMatchedAssociations(
			ArrayList<EClass> mappingResult) {

		ArrayList<MatchingResponse> result = new ArrayList<MatchingResponse>();

		for (EClass c : mappingResult) {
			for (EReference ref : c.getEReferences()) {
				if (ref.getEType() instanceof EClass && !ref.isContainment()) {
					result.add(new ReferenceResponse(c,
							(EClass) ref.getEType(), ref));
				}
			}
		}

		return result;
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////CONTAINMENT/////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////
	private List<MatchingResponse> checkForContainmentModel() {
		EPackage ePackage = (EPackage) (editor.getDiagram().getElement());
		// Call the similarity method
		ArrayList<EClass> mappingResult = null;
		;
		try {
			mappingResult = EcoreSimilarityChecker.checkSimilarity(
					compartimentModel, ePackage, fallBackPov);
		} catch (DiagraphException e) {
			controler.log("info", "Diagraph error: " + e.getMessage());
		}
		if (mappingResult != null)
			return arangeMatchedContainments(mappingResult);
		else
			return new ArrayList<MatchingResponse>();
	}

	private List<MatchingResponse> arangeMatchedContainments(
			ArrayList<EClass> mappingResult) {
		ArrayList<MatchingResponse> result = new ArrayList<MatchingResponse>();
		for (EClass c : mappingResult) {
			for (EReference ref : c.getEReferences()) {
				if (ref.getEType() instanceof EClass && ref.isContainment()) {
					EClass cls = (EClass) ref.getEType();
					// Check if it is a spatial case of the labeled association
					if (cls.getEReferences().size() == 2) {
						boolean isAssocClass = true;
						for (EReference checkRef : cls.getEReferences()) {
							if (!isLabeledClassReference(checkRef))
								isAssocClass = false;
						}
						if (isAssocClass) {
							// Classes
							EClass sourceCls = c;
							EClass labelCls = cls;
							ArrayList<EClass> targetCls = new ArrayList<EClass>();
							targetCls.add(c);
							// References
							EReference sl = ref;
							EReference ls;
							ArrayList<EReference> lt = new ArrayList<EReference>();

							if (cls.getEReferences().get(0).getEType()
									.equals(sourceCls)) {
								ls = cls.getEReferences().get(0);
								lt.add(cls.getEReferences().get(1));
							} else {
								ls = cls.getEReferences().get(1);
								lt.add(cls.getEReferences().get(0));
							}

							result.add(new LabeledAssociationResponse(
									sourceCls, labelCls, targetCls, ls, lt, sl));
						}
					} else {
						if (cls.getEAllReferences().size() == 2) {
							boolean isAssocClass = true;
							for (EReference checkRef : cls.getEAllReferences()) {
								// It should be labeled reference contained in
								// the reference class or in one of the
								// generic classes
								if (!isLabeledClassReference(checkRef))
									isAssocClass = false;
								else if (EcoreSimilarityChecker
										.getVisualElements().contains(
												checkRef.getEContainingClass())
										&& !checkRef.getEContainingClass()
												.equals(cls))
									isAssocClass = false;

							}
							if (isAssocClass) {
								// Classes
								EClass sourceCls = c;
								EClass labelCls = cls;
								ArrayList<EClass> targetCls = new ArrayList<EClass>();
								targetCls.add(c);
								// References
								EReference sl = ref;
								EReference ls;
								ArrayList<EReference> lt = new ArrayList<EReference>();

								if (cls.getEAllReferences().get(0).getEType()
										.equals(c)) {
									ls = cls.getEAllReferences().get(0);
									lt.add(cls.getEAllReferences().get(1));
								} else {
									ls = cls.getEAllReferences().get(1);
									lt.add(cls.getEAllReferences().get(0));
								}

								result.add(new LabeledAssociationResponse(
										sourceCls, labelCls, targetCls, ls, lt,
										sl));
							}
						} else {
							result.add(new CompartmentResponse(c, (EClass) ref
									.getEType(), ref));
						}
					}
				}
			}
		}
		return result;
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////UTILITY/////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void cleanAllDiagraphAnnotations(String view) { // TODO remove only
															// a given view
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

	private void cleanFromDiagram(String view) { // TODO remove only a given
													// view
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

	/*
	 * private void changeColor(final FillStyle styl, final int color) {
	 * TransactionalEditingDomain ted = TransactionUtil.getEditingDomain(styl);
	 * ted.getCommandStack().execute(new RecordingCommand(ted) {
	 *
	 * @Override protected void doExecute() { styl.setFillColor(color); } }); }
	 */

	private boolean isDiagraphAnnotated(EClass eclass, String view) {
		// if (eclass.getEAnnotations().size() == 0) return false;
		if (view == null || view.isEmpty())
			view = "default";
		for (EAnnotation e : eclass.getEAnnotations())
			if (e.getSource().equals("diagraph") && getViewName(e).equals(view))
				return true;
		return false;
	}

	private String getViewName(EAnnotation anot) {
		EMap<String, String> entries = anot.getDetails();
		for (Entry<String, String> entry : entries) {
			String k = entry.getKey();
			if (k.startsWith("view")) {
				String[] kv = k.split("=");
				return kv[1];
			}
		}
		return "default";
	}

	private Map<EClass, List<String>> propagateNonVisual() {
		Map<EClass, List<String>> nvresult = new HashMap<EClass, List<String>>();
		String lang = ((EPackage) editor.getDiagram().getElement()).getName();
		String viu = getControler().getCurrentView(7, lang,
				this.getClass().getName()); // FP140611
		for (View gview : (List<View>) editor.getDiagram().getChildren()) {
			if (gview instanceof Node) {
				Node node = (Node) gview;
				if (node.getElement() instanceof EClass) {
					EClass notAnnotated = (EClass) node.getElement();
					if (!isDiagraphAnnotated(notAnnotated, viu)) { // FP140611
						// Non visual element
						if (!EcoreSimilarityChecker.getVisualElements()
								.contains(notAnnotated)) {
							if (LOG2)
								clog("propagateNonVisual "
										+ notAnnotated.getName());
							nvresult.put(notAnnotated,
									labelsFromAttributes(notAnnotated));
						}
					}
				}
			}
		}
		// all_.putAll(nvresult);
		/*
		 * if (LOG) clog("PNONVIS all:"); logNodes_(all);
		 */
		// if (LOG)
		// clog("PNONVIS nvresult:");
		// logNodes(nvresult);
		return nvresult;
	}

	private Map<EClass, List<String>> propagateByInheritance() {
		Map<EClass, List<String>> inhresult = new HashMap<EClass, List<String>>();
		String lang = ((EPackage) editor.getDiagram().getElement()).getName();
		String viu = getControler().getCurrentView(7, lang,
				this.getClass().getName()); // FP140611
		for (View view : (List<View>) editor.getDiagram().getChildren()) {
			if (view instanceof Node) {
				Node node = (Node) view;
				if (node.getElement() instanceof EClass) {
					EClass annotated = (EClass) node.getElement();
					// String viu = getControler().getCurrentView(8,
					// this.getClass().getName());
					if (isDiagraphAnnotated(annotated, viu)) {
						for (EClass superAnnotated : annotated
								.getEAllSuperTypes()) {
							if (isDiagraphAnnotated(superAnnotated, viu)) {
								for (EAnnotation an : superAnnotated
										.getEAnnotations()) {
									if (an.getDetails().keySet()
											.contains(DiagraphKeywords.NODE)) {
										if (LOG2)
											clog("propagateByInheritance NODE "
													+ annotated.getName());
										inhresult.put(annotated,
												detail(DiagraphKeywords.NODE));
									}
									if (an.getDetails().keySet()
											.contains(DiagraphKeywords.LINK)) {
										if (LOG2)
											clog("propagateByInheritance LINK "
													+ annotated.getName());
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
		// add to the associated annotation map
		// if (LOG)
		// clog("PNONVIS inhresult:");
		// logNodes(inhresult);
		return inhresult;
	}

	private List<String> detail(String element) {
		ArrayList<String> ants = new ArrayList<String>();
		ants.add(element);
		return ants;
	}



	private List<String> labelsFromAttributes(EClass c) {// FP140204ok
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
	/*
	 * public Map<EClass, List<String>> getNonVisual() { return nonVisual; }
	 *
	 * public Map<EClass, List<String>> getInherited() { return inherited; }
	 */

}
