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
package org.isoe.diagraph.lang.internal.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecoretools.legacy.diagram.edit.parts.EAnnotationEditPart;
import org.eclipse.emf.ecoretools.legacy.diagram.part.EcoreDiagramEditor;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramGraphicalViewer;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.isoe.diagraph.commands.DiagraphAnnotationFactory;
import org.isoe.diagraph.generic.GenericConstants;
import org.isoe.diagraph.lang.DiagraphKeywords;
import org.isoe.diagraph.views.ViewConstants;
import org.isoe.extensionpoint.gramgen.IAnnotationHelper;
import org.isoe.extensionpoint.gramgen.IDiagraphService;
import org.isoe.extensionpoint.gramgen.IDiagraphUtils;
import org.isoe.fwk.core.DParams;
import org.isoe.diagraph.commands.AddAnnotationDetailCommand;
import org.isoe.diagraph.commands.DiagraphCommandHandler;
import org.isoe.diagraph.commands.PackageCommandHandler;
import org.isoe.diagraph.controler.IDiagraphControler;

import org.isoe.diastyle.lang.StyleConstants;








/**
 *
 * @author pfister
 *
 */
public class DiagraphAnnotationUtils implements IAnnotationHelper, IDiagraphUtils {
//FP141227int

	private static final boolean LOG = DParams.AnnotationUtils_LOG;
	private static DiagraphAnnotationUtils instance;
	private IDiagraphService diagraphService;
	private List<String> cmds = new ArrayList<String>();

	String[] NAME_CANDIDATES = { "name", "id", "description", "designation",
			"type" };
	private IDiagraphControler controler;

	public static DiagraphAnnotationUtils getInstance(IDiagraphService diagraphService) {
		if (instance == null) {
			instance = new DiagraphAnnotationUtils();
			instance.diagraphService = diagraphService;
			instance.controler = diagraphService.getControler();
		}
		return instance;
	}


	private DiagraphAnnotationUtils() {
		super();
	}

	private Map<EClass, List<String>> arguments[];

	@Override
	public Object getArguments() {
		return arguments;
	}




	/**------------------------------------**/


	public Map<EClass, List<String>> createAnnotation(
			Map<EClass, List<String>> maps[], String srcpov, String pov,
			//int povid,
			EClass target, List<String> details) {
		maps[0].put(target, details);
		arguments = maps;
		//new DiagraphCommandHandler_(this).generateAll(srcpov, pov, povid);

		int povid = getControler().getViewId(pov); //FP140605xxx
		new DiagraphAnnotationFactory(this).generateAll(srcpov, pov, povid);

		return maps[0];
	}




	private void dropToDiagram(List<EAnnotation> toDrop, EClass selectedClass) {
		Map<EClass, List<String>>[] maps = new HashMap[2];
		Map<EClass, List<String>> config = new HashMap<EClass, List<String>>();
		maps[0] = config;
		List<String> dummy = new ArrayList<String>();
		for (EAnnotation drop : toDrop) {
			EClass dropclass = (EClass) drop.getEModelElement();
			if (selectedClass == null || selectedClass == dropclass) // FP131203
				config.put(dropclass, dummy);
		}
		arguments = maps;
		new DiagraphCommandHandler(this).dropAll();
		//new EAnnotation_EditManager(this).dropAll();

	}

	private void checkDiagraphAnnots(List<EAnnotation> annots) {
		Map<EClass, List<String>>[] maps = new HashMap[2];
		Map<EClass, List<String>> config = new HashMap<EClass, List<String>>();
		maps[0] = config;
		List<String> dummy = new ArrayList<String>();
		for (EAnnotation annot : annots) {
			EClass cclass = (EClass) annot.getEModelElement();
			List<String> exists = config.get(cclass);
			if (exists == null)
				config.put(cclass, dummy); // FP140501 inutile, à revoir
		}
		arguments = maps;
		new DiagraphCommandHandler(this).checkAllDiagraphAnnots();
		//new EAnnotation_EditManager(this).checkAllDiagraphAnnots();


	}


	public void deleteAnnotation_(EAnnotation modelElement,
			EAnnotationEditPart graphicalEditPart) { // FP131116

		//new EAnnotation_EditManager(this).removeDiagraphAnnotationsFromDiagramAndFromModel_(
		//		getControler().getEcoreDiagramEditor(), modelElement,
		//		graphicalEditPart);

		new DiagraphCommandHandler(this)
				.removeDiagraphAnnotationsFromDiagramAndFromModel(
						getControler().getEcoreDiagramEditor(), modelElement,
						graphicalEditPart);

	}

	public void deleteAnnotationsFromEditor(EcoreDiagramEditor editor,
			List<EAnnotation> toremove) { // FP140421
		//new EAnnotation_EditManager(this).removeDiagraphAnnotationsFromDiagram_(
		//		getControler().getEcoreDiagramEditor(), toremove);

		new DiagraphCommandHandler(this).removeDiagraphAnnotationsFromDiagram(
				getControler().getEcoreDiagramEditor(), toremove);
	}

	/**------------------------------------**/	/**------------------------------------**/

	public Map<EClass, List<String>> createNodeAnnotation1(
			Map<EClass, List<String>> annotations, String view_,// int povid_,
			EClass target) {
		view_ = normalizeViewWithPrefix(view_);
		//int povid = getControler().getViewId(view_); //FP140605xxx
		//int povid_ = 0;
		List<String> details = new ArrayList<String>();
		details.add("node");
		//if (povid > 0) {
			// if (pov.startsWith(DParams.VIEW_PREFIX));
			if (!view_.isEmpty() && !view_.equals("default"))
				details.add("view=" + view_);// (pov.startsWith(DParams.VIEW_PREFIX))?"":"");//DParams.VIEW_PREFIX//
			// + pov);
		//}
		// if (level == 1)
		for (String arg : getNodeArguments(target, view_))
			details.add(arg);
		Map maps[] = new HashMap[2];
		maps[0] = annotations;
		return createAnnotation(maps, null, view_,  target, details);//FP140502
	}



	public Map<EClass, List<String>> createPovAnnotation_(
			Map<EClass, List<String>>[] maps, String srcview_,
			String targview_, EClass source, EClass target,
			boolean addNavAnnotation) {
		targview_ = normalizeViewWithPrefix(targview_);
		srcview_ = normalizeViewWithPrefix(srcview_);
		List<String> details = new ArrayList<String>();
		details.add("node");
		details.add("pov");
		//int targetpovid = getControler().getViewId__(targview_); //FP140605xxx
		if (!targview_.isEmpty() && !targview_.equals("default"))
			details.add("view=" + targview_);
		// }
		if (!targview_.isEmpty() && source != null && addNavAnnotation) {
			List<String> newannotdetail = new ArrayList<String>();
			newannotdetail.add("nav:" + targview_);
			maps[1].put(source, newannotdetail);
		}
		return createAnnotation(maps, srcview_, targview_,  target,
				details);
	}

	/**------------------------------------**/

	public void clearConcreteSyntax(String view) {
		diagraphService.setView(view);
		removeVisibleDiagraphAnnotationsFromDiagram();
		removeAllDiagraphAnnotationsFromModel();// FP131127
		getControler().removeView(view);// FP131127
	}

	private List<EClass> getClassesInView(EPackage pack, String pov_) {

		if (pov_.startsWith(DParams.VIEW_PREFIX))
			pov_ = pov_.substring(DParams.VIEW_PREFIX_LEN);
		List<EClass> result = new ArrayList<EClass>();
		for (EClassifier eClassifier : pack.getEClassifiers()) {
			if (eClassifier instanceof EClass) {
				for (EAnnotation eAnnotation : ((EClass) eClassifier)
						.getEAnnotations())
					if (eAnnotation.getSource().equals(
							GenericConstants.ANNOT_DIAGRAPH_LITTERAL)
							&& isView(eAnnotation, pov_)) {
						if (!result.contains(eClassifier))
							result.add((EClass) eClassifier);
					}
			}
		}
		return result;
	}

	public void removeVisibleDiagraphAnnotationsFromDiagram() {
		// by view
		EditingDomain editingDomain = getEditor().getEditingDomain();
		IDiagramGraphicalViewer viewer = getEditor()
				.getDiagramGraphicalViewer();
		List parts = new ArrayList(viewer.getEditPartRegistry().values());
		if (LOG)
			clog("removeVisibleDiagraphAnnotationsFromDiagramAndFromModel, view="
					+ diagraphService.getView());
		for (Object o : parts) {
			if (o instanceof EAnnotationEditPart) {
				EAnnotationEditPart editpart = (EAnnotationEditPart) o;
				Node anode = (Node) editpart.getModel();
				EModelElement el = (EModelElement) anode.getElement();
				EAnnotation eAnnotation = (EAnnotation) el;
				if (eAnnotation.getSource().equals(
						GenericConstants.ANNOT_DIAGRAPH_LITTERAL)) {
					if (isView(eAnnotation, diagraphService.getView())) {
						try {

							if (LOG)
								clog("removing annotation from "
										+ ((EClass) eAnnotation
												.getEModelElement()).getName()
										+ " (view=" + diagraphService.getView() + ")");
							editingDomain.getCommandStack().execute(
									RemoveCommand.create(editingDomain,
											editpart.getModel()));
						} catch (Exception e) {
							controler.cerror(e.toString());
						}
					}
				}
			}
		}
	}

	// FP131115
	public void removeAllDiagraphAnnotationsFromModel() {
		// by view
		if (LOG)
			clog("removeAllDiagraphAnnotationsFromModel, view="
					+ diagraphService.getView());
		EditingDomain editingDomain = getEditor().getEditingDomain();
		EPackage editedPackage = (EPackage) getEditor().getDiagram()
				.getElement();
		for (EClassifier eClassifier : editedPackage.getEClassifiers()) {
			if (eClassifier instanceof EClass) {
				List<EAnnotation> toremove = new ArrayList<EAnnotation>();
				for (EAnnotation eAnnotation : ((EClass) eClassifier)
						.getEAnnotations())
					if (eAnnotation.getSource().equals(
							GenericConstants.ANNOT_DIAGRAPH_LITTERAL)
							&& isView(eAnnotation, diagraphService.getView())) {
						toremove.add(eAnnotation);
						if (LOG)
							clog("removing annotation from "
									+ ((EClass) eAnnotation.getEModelElement())
											.getName() + " (view="
									+ diagraphService.getView() + ")");
					}
				for (Iterator<EAnnotation> iterator = toremove.iterator(); iterator
						.hasNext();)
					editingDomain.getCommandStack()
							.execute(
									RemoveCommand.create(editingDomain,
											iterator.next()));
			}
		}
	}

	public List<EAnnotation> getAllDiagraphAnnotationFromView() {
		List<EAnnotation> result = new ArrayList<EAnnotation>();
		String view = diagraphService.getView();
		EPackage editedPackage = (EPackage) getEditor().getDiagram()
				.getElement();
		if (LOG)
			clog("getAllAnnotationFromView (2), view=" + view +" - "+editedPackage.getName());
		for (EClassifier eClassifier : editedPackage.getEClassifiers()) {
			if (eClassifier instanceof EClass) {
				for (EAnnotation eAnnotation : ((EClass) eClassifier)
						.getEAnnotations())
					if (eAnnotation.getSource().equals(
							GenericConstants.ANNOT_DIAGRAPH_LITTERAL)
							&& isView(eAnnotation, view)) {
						result.add(eAnnotation);
					}
			}
		}
		return result;
	}

	private List<EAnnotation> getAllAnnotations(String src, int from) {
		List<EAnnotation> result = new ArrayList<EAnnotation>();
		String view = diagraphService.getView();

		EPackage editedPackage = (EPackage) getEditor().getDiagram()
				.getElement();
		if (LOG)
			clog("getAllAnnotationFromView (1) "+from+" , view=" + view +" - "+editedPackage.getName());
		for (EAnnotation eAnnotation : editedPackage.getEAnnotations())
			if (eAnnotation.getSource().equals(src))
				result.add(eAnnotation);
		return result;
	}



	private boolean isView(EAnnotation anot, String view) {
	//	if (view.equals(org.isoe.diagraph.views.ViewConstants.DIAGRAPH_ALL_VIEW_LITTERAL))
	//		return true;
		if (view == null || view.equals(ViewConstants.DIAGRAPH_DEFAULT)
				|| view.isEmpty())
			return isDefaultView(anot);
		for (Map.Entry<String, String> entry : anot.getDetails())
			if (entry.getKey().equals("view=" + view))
				return true;
		return false;
	}

	private boolean isDefaultView(EAnnotation anot) {
		for (Map.Entry<String, String> entry : anot.getDetails())
			if (entry.getKey().startsWith("view="))
				return false;
		return true;
	}

	private boolean isNode(EClass eClaz, String pov) {
		for (EAnnotation eAnnotation : eClaz.getEAnnotations())
			if (eAnnotation.getSource().equals(
					GenericConstants.ANNOT_DIAGRAPH_LITTERAL))
				if (isView(eAnnotation, pov))
					if (hasKey(eAnnotation, "node")) {
						return true;
					}
		return false;
	}

	private boolean isLink(EClass eClaz, String pov) {
		for (EAnnotation eAnnotation : eClaz.getEAnnotations())
			if (eAnnotation.getSource().equals(
					GenericConstants.ANNOT_DIAGRAPH_LITTERAL))
				if (isView(eAnnotation, pov))
					if (hasKey(eAnnotation, "link")) {
						return true;
					}
		return false;
	}

	private boolean isGeneric(EClass eClaz, String pov) {
		for (EAnnotation eAnnotation : eClaz.getEAnnotations())
			if (eAnnotation.getSource().equals(
					GenericConstants.ANNOT_DIAGRAPH_LITTERAL))
				if (isView(eAnnotation, pov))
					if (!hasKey(eAnnotation, "node")
							&& !hasKey(eAnnotation, "link")) {
						return true;
					}
		return false;
	}

	private boolean hasKey(EAnnotation anot, String key) {
		for (Map.Entry<String, String> entry : anot.getDetails())
			if (entry.getKey().equals(key))
				return true;
		return false;
	}

	private List<String> getContainers(List<EClass> viu, String pov, EClass link) {
		List<String> result = new ArrayList<String>();
		for (EClass eclaz : viu) {
			for (EReference eReference : eclaz.getEReferences())
				if (eReference.getEType() == link)
					if (isNode((EClass) eReference.eContainer(), pov))
						if (eReference.isContainment())
							result.add( // "cont="+
							((EClass) eReference.eContainer()).getName() + "."
									+ eReference.getName());
		}
		return result;
	}

	private List<String> getNoContRelations(List<EClass> viu, String pov,
			EClass claz) {
		List<String> result = new ArrayList<String>();
		List<EReference> refs = claz.getEReferences();
		for (EReference eReference : refs) {
			if (!eReference.isContainment()
					&& viu.contains(eReference.getEType()))
				result.add(eReference.getName());// "lsrc="+
		}
		return result;
	}

	private List<String> getContRelations(List<EClass> viu, String pov,
			EClass claz) {
		List<String> result = new ArrayList<String>();
		List<EReference> refs = claz.getEReferences();
		for (EReference eReference : refs) {
			if (eReference.isContainment()
					&& viu.contains(eReference.getEType()))
				result.add(eReference.getName());// "lsrc="+
		}
		return result;
	}

	public Map<EClass, List<String>> createLinkAnnotation(
			Map<EClass, List<String>> annotations, String view, // int povid,
			EClass target) {// , int level) {
		view = normalizeViewWithPrefix(view);
		List<String> details = new ArrayList<String>();
		details.add("link");
		//int povid = getControler().getViewId__(view_); //FP140605xxx

		if (!view.isEmpty() && !view.equals("default"))
			details.add("view=" + view);
		// if (level == 1) {
		String[] args = getLinkArguments(target, view);// findLinkArguments(target,pov);
		details.add(args[0]);
		details.add(args[1]);
		details.add(args[2]);
		// }
		Map maps[] = new HashMap[2];
		maps[0] = annotations;
		int povid = getControler().getViewId(view); //FP140605xxx
		return createAnnotation(maps, null, view,  target, details);
	}



	private List<EClass> superClasses(EClass c, List<EClass> viu) {
		List<EClass> result = new ArrayList<EClass>();
		for (EClass eClass : viu) {
			if (!eClass.equals(c) && eClass.isSuperTypeOf(c)) {
				// System.out.println("super " + eClass.getName());
				result.add(eClass);
			}
		}
		Collections.reverse(result);
		return result;
	}

	private List<EClass> getAllSuperClassesOfCurrent(EClass root) {
		EClass curnt = root;// (EClass) controler.getCurrentElement();
		List<EClassifier> clasfis_ = null;
		clasfis_ = curnt.getEPackage().getEClassifiers();
		List<EClass> all = new ArrayList<EClass>();
		for (EClassifier eClassifier : clasfis_)
			if (eClassifier instanceof EClass) // FP140116
				all.add((EClass) eClassifier);
		return superClasses(curnt, all);
	}

	private List<EClass> allSuperClassesOfCurrentNotInNewView(EClass root,
			String viu) {
		List<EClass> result = new ArrayList<EClass>();
		List<EClass> supers = getAllSuperClassesOfCurrent(root);
		for (EClass supair : supers) {
			if (getKeyInSuperClass(supair, "view=" + DParams.VIEW_PREFIX + viu) == null) {
				clog(supair.getName() + " " + "no tagged");
				result.add(supair);
			}
		}
		return result;
	}

	public void verifyAfterPov(EClass rootclass) {
		List<EClass> toCompletge = allSuperClassesOfCurrentNotInNewView(
				rootclass, getControler().getNewPov());
		for (EClass tocompl : toCompletge) {
			Map<EClass, List<String>> annotations = new HashMap<EClass, List<String>>();
			createGenericAnnotation(annotations, getControler().getNewPov(),
					tocompl);//FP140605xxx//, getControler().getNewPovId());// getControler().getNewPovId();
		}
	}

	private String getKeyInSuperClass(EClass claz, String k) {// DiagraphKeywords.UNKNOWN
		List<EAnnotation> annots = claz.getEAnnotations();
		for (EAnnotation eAnnotation : annots)
			if (eAnnotation.getSource().equals(GenericConstants.ANNOT_DIAGRAPH_LITTERAL)) {
				for (String key : eAnnotation.getDetails().keySet())
					if (key.startsWith(k))
						return key;
			}
		return null;
	}

	private String getFirstLabelInSuperClasses(List<EClass> viu, EClass node) {
		List<EClass> sups = superClasses(node, viu);
		for (EClass eClass : sups) {
			if (viu.contains(eClass)) {
				String label = getKeyInSuperClass(eClass, "label=");
				if (label != null)
					return label;
			}
		}
		return null;
	}

	private String getOwnStringAttribute(EClass node) {
		List<EAttribute> atts = node.getEAttributes();
		for (EAttribute eAttribute : atts) {
			if (eAttribute.getEAttributeType().getName().equals("EString"))
				return eAttribute.getName();
		}
		return null;
	}

	private String getFirstAttribute(EClass node) {
		if (node.getEAttributes().size() > 0)
			return node.getEAttributes().get(0).getName();
		else
			return null;
	}

	private String getOwnNameAttribute(EClass node) {
		List<EAttribute> atts = node.getEAttributes();
		for (EAttribute eAttribute : atts) {
			for (String name : NAME_CANDIDATES) {
				if (eAttribute.getName().contains(name)) {
					if (eAttribute.getEAttributeType().getName()
							.equals("EString"))
						return eAttribute.getName();
				}
			}
		}
		return null;
	}

	private String resolveLabel(List<EClass> viu, EClass node) {
		String first = getFirstLabelInSuperClasses(viu, node);
		if (first == null) {
			first = getOwnNameAttribute(node);
			if (first == null)
				first = getOwnStringAttribute(node);
			if (first == null)
				first = getFirstAttribute(node);
			if (first != null)
				return first;
		}
		return null;
	}

	private String[] getNodeArguments(EClass node, String pov) {
		List<String> result = new ArrayList<String>();
		EPackage pack = node.getEPackage();
		List<EClass> viu = getClassesInView(node.getEPackage(), pov);
		List<String> ncrelations = getNoContRelations(viu, pov, node);
		for (String r : ncrelations)
			result.add(DiagraphKeywords.REFERENCE_EQU + r);
		List<String> contrelations = getContRelations(viu, pov, node);
		for (String kr : contrelations)
			result.add(DiagraphKeywords.KREFERENCE_EQU_ + kr);//FP150512transp
		String label = resolveLabel(viu, node);
		if (label != null)
			result.add(DiagraphKeywords.LABEL_EQU + label);
		return result.toArray(new String[result.size()]);
	}

	public Map<EClass, List<String>> createGenericAnnotation(
			Map<EClass, List<String>> annotations, String view, // int povid,
			EClass target) {
		view = normalizeViewWithPrefix(view);
		List<String> details = new ArrayList<String>();

		//if (povid == -1)
		//	povid = getControler().getViewId__(view_); //FP140605xxx

		if (!view.isEmpty() && !view.equals("default"))
				details.add("view=" + view);
		// if (level == 1)
		for (String arg : getNodeArguments(target, view))
			if (arg.startsWith("label="))
				details.add(arg);
		Map maps[] = new HashMap[2];
		maps[0] = annotations;
		if (LOG)
			clog("createGenericAnnotation on view " + view + " "
					+ target.getName()); // FP140116
		return createAnnotation(maps, null, view,  target, details);
	}

	private String[] getLinkArguments(EClass link, String view) {
		String[] result = new String[3];
		EPackage pack = link.getEPackage();
		List<EClass> viu = getClassesInView(link.getEPackage(), view);
		List<String> containers = getContainers(viu, view, link);
		List<String> relations = getNoContRelations(viu, view, link);
		result[0] = (containers.isEmpty() ? "??" : containers.get(0));
		result[1] = (relations.isEmpty() ? "??" : relations.get(0));
		result[2] = (relations.size() > 1 ? relations.get(1) : "??");
		result = finalizeLink(result);
		return result;
	}

	private String[] finalizeLink(String[] result) {
		boolean swap = false;
		if (result[1].contains("trg") || result[1].contains("targ")
				|| result[1].contains("to") || result[2].contains("src")
				|| result[2].contains("source") || result[2].contains("from"))
			swap = true;
		if (swap) {
			String t = result[2];
			result[2] = result[1];
			result[1] = t;
		}
		result[0] = "cont=" + result[0];
		result[1] = "lsrc=" + result[1];
		result[2] = "ltrg=" + result[2];
		return result;
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	@Override
	public IDiagraphControler getControler() {
		return diagraphService.getControler();
	}

	@Override
	public EcoreDiagramEditor getEditor() {
		EcoreDiagramEditor result = getControler().getEcoreDiagramEditor();
		return result; // FP140421

		// return gramgen.getEcoreDiagramEditor();
	}

	@Override
	public IDiagraphUtils getDiagraphUtils() {
		return (IDiagraphUtils) diagraphService;
	}

	private boolean isTagInView(EAnnotation eAnnotation, String view,
			String k1, String k2) {
		return k1.equals(k2) && isView(eAnnotation, view);
	}

	public boolean isElementType(String view, String key_, EClass eclaz) {
		if (key_.equals("pov"))
			throw new RuntimeException("TODO in existsAnnotation");
		if (key_ == null || key_.isEmpty())
			throw new RuntimeException("TODO in existsAnnotation");
		Diagram diagram = getControler().getEcoreDiagram();
		if (diagram != null) {
			List<Node> ch = diagram.getPersistedChildren();
			for (Node chNode : ch) {
				EObject el = chNode.getElement();
				if (el instanceof EClass) {
					EClass eClass = (EClass) el;
					if (eclaz == eClass)
						for (EAnnotation eAnnotation : eClass.getEAnnotations()) {
							if ((eAnnotation.getSource()
									.equals(GenericConstants.ANNOT_DIAGRAPH_LITTERAL))) {
								if (eAnnotation.getDetails().size() > 0
										&& isTagInView(eAnnotation, view,
												eAnnotation.getDetails().get(0)
														.getKey(), key_))
									return true;
							}
						}
				}
			}
		}

		return false;
	}

	public void dropConcreteSyntax(String view, EClass selectedClass,
			boolean register) {
		diagraphService.setView(view);
		List<EAnnotation> toDrop = getAllDiagraphAnnotationFromView();// getAllAnnotationFromView();
		dropToDiagram(toDrop, selectedClass);
		// String viewArg = normalize(view);
		if (register)
			registerCommand("drop" + ";" + normalize(view) + ";"
					+ (selectedClass == null ? "nil" : selectedClass.getName()));
	}

	public boolean nullValueIn(List<EAnnotation> allannot) {// FP140429
		for (EAnnotation eAnnotation : allannot)
			for (Map.Entry<String, String> entry : eAnnotation.getDetails())
				if (entry.getValue() == null)
					return true;
		return false;
	}

	public boolean checkConcreteSyntax_(String view) {// FP140429
		boolean done = false;
		if (LOG) {
			clog("__ checkConcreteSyntax for view " + view);
		}
		diagraphService.setView(view);

		List<EAnnotation> diagraphAnnots = getAllDiagraphAnnotationFromView();// getAllAnnotationFromView();
		if (nullValueIn(diagraphAnnots)) {
			checkDiagraphAnnots(diagraphAnnots);
			done = true;
		}
/*
		List<EAnnotation> styleannot = getAllAnnotations("diastyle");// getAllAnnotationFromView();
		if (nullValueIn(styleannot)) {
			checkPackageAnnots(styleannot);
			done = true;
		}

		List<EAnnotation> genannot = getAllAnnotations("diagen");// getAllAnnotationFromView();
		if (nullValueIn(genannot)) {
			checkPackageAnnots(genannot);
			done = true;
		}*/

		return done;
	}

	private void checkPackageAnnots(List<EAnnotation> annots) {
		Map<EPackage, List<String>>[] maps = new HashMap[2];
		Map<EPackage, List<String>> config = new HashMap<EPackage, List<String>>();
		maps[0] = config;
		List<String> dummy = new ArrayList<String>();
		for (EAnnotation annot : annots) {
			EPackage pak = (EPackage) annot.getEModelElement();
			List<String> exists = config.get(pak);
			if (exists == null)
				config.put(pak, dummy); // FP140501 inutile, à revoir
		}
		new PackageCommandHandler(this, maps).checkAllPackageAnnots();
	}

	private String normalizeViewWithPrefix(String view) {
		if (view == null || view.isEmpty() || view.equals("default"))
			return "";
		String v = view;
		if (!v.startsWith(DParams.VIEW_PREFIX))
			v = DParams.VIEW_PREFIX + v;
		return v;
	}

	private String normalize(String viewarg) {
		if (!viewarg.startsWith("view=" + DParams.VIEW_PREFIX))
			viewarg = "view=" + DParams.VIEW_PREFIX + viewarg; // FP1126xx

		if (viewarg.contains("view=" + DParams.VIEW_PREFIX
				+ DParams.VIEW_PREFIX))
			viewarg = "view="
					+ DParams.VIEW_PREFIX
					+ viewarg
							.substring(("view=" + DParams.VIEW_PREFIX + DParams.VIEW_PREFIX)
									.length());
		return viewarg;
	}



	public void registerCommand(String cmd) { // FP140117
		if (LOG)
			clog("CMD----[" + cmd + "]");
		cmds.add(cmd);
	}

	public String getDiagraphActions() {
		String result = "";
		for (String cmd : cmds)
			// FP140117
			result += cmd + "\n";
		return result;
	}

	public boolean[] invokeDiagraphAction(String cmd, String view,
			String eClassName) { // FP140117
		EClass claz = getControler().getEClass(eClassName);
		boolean[] result = new boolean[2];
		result[0] = false;
		result[1] = true;
		if (view.startsWith("view="))
			view = view.substring(5);
		return result;
	}

	public void addDetail(EModelElement element_, EModelElement modelElement_,
			String view_, String argument_) {
		if (argument_.contains("ref=") || argument_.contains("afx=")
				|| argument_.contains("lsrc=") || argument_.contains("ltrg=")) {
			EClass eclaz = (EClass) ((EReference) modelElement_).eContainer();

			AddAnnotationDetailCommand.execute(this, eclaz, view_, argument_);

		} else if (argument_.equals("pov") || argument_.equals("node")
				|| argument_.equals("link") || argument_.contains("label=")
				|| argument_.contains("view=") || argument_.contains("nav:")
				|| argument_.contains("cont=")) {

			if (argument_.contains("label=")) {
				EAttribute atr = (EAttribute) modelElement_;
				EClass eclaz = (EClass) atr.eContainer();
				AddAnnotationDetailCommand.execute(this, eclaz, view_, // FP131118zz
						"label=" + atr.getName());
			} else {
				EClass eclaz = (EClass) modelElement_;
				AddAnnotationDetailCommand.execute(this, eclaz, view_, // FP131118zz
						argument_);// "pov"
			}
		}
	}

	private String parseView(EAnnotation eAnnotation) {
		for (Map.Entry<String, String> entry : eAnnotation.getDetails())
			if (entry.getKey().startsWith(ViewConstants.VIEW_EQ))
				return entry.getKey().split("=")[1];
		return ViewConstants.DIAGRAPH_DEFAULT;
	}

	private String getEAnnotationAsString(EAnnotation eAnnotation) {
		String result = "";
		for (Map.Entry<String, String> entry : eAnnotation.getDetails())
			result += entry.getKey() + " - ";
		return result;
	}

	private EAnnotation getDiagraphAnnotation(EClass eclass, String viewId) {
		EList<EAnnotation> annots = eclass.getEAnnotations();
		EAnnotation result = null;
		int count = 0;
		for (EAnnotation annot : annots) {
			if (annot.getSource().equals(
					GenericConstants.ANNOT_DIAGRAPH_LITTERAL)) {
				String viewid = parseView(annot);
				if (viewId == null || viewid.equals(viewId)) {
					result = annot;
					if (LOG)
						clog("found existing Diagraph EAnnotation, view="
								+ viewId + " on EClass " + eclass.getName()
								+ " [" + getEAnnotationAsString(annot) + "]");
					count++;
				}
			}
		}
		if (count > 1) {
			String mesg = "more than one Diagraph Annotation for EClass: "
					+ eclass.getName() + " and view " + viewId;
			if (LOG)
				clog(mesg);
			throw new RuntimeException(mesg);
		}
		return result;
	}

	private boolean entryExists(EAnnotation anot, String key) {
		for (Map.Entry<String, String> entry : anot.getDetails())
			if (entry.getKey().equals(key))
				return true;
		return false;
	}

	@Override
	public String addDetailToDiagraphAnnotation(EClass parent, String view,
			String key) {
		EAnnotation annot = null;
		try {
			annot = getDiagraphAnnotation(parent, view);
		} catch (Exception e) {
			return e.toString();
		}
		if (annot == null) {
			String msg = " no diagraph annotation on EClass "
					+ parent.getName() + " for view " + view;
			if (LOG)
				clog(msg);
			return msg;
		} else if (LOG)
			clog("diagraph annotation found on EClass " + parent.getName()
					+ " for view " + view); // FP31118
		if (!entryExists(annot, key)) {
			annot.getDetails().put(key, DParams.NULL_ANNOTATION_VALUE);
			if (LOG)
				clog("entry added: " + key); // FP31118
			return "ok";
		} else {
			if (LOG)
				clog("entry " + key + " already exists"); // FP31118
			return "entry " + key + " already exists";
		}
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

	private boolean isDiagraphAnnotated(EClass eclass, String view) {
		// if (eclass.getEAnnotations().size() == 0) return false;
		if (view == null || view.isEmpty())
			view = "default";
		for (EAnnotation e : eclass.getEAnnotations())
			if (e.getSource().equals("diagraph") && getViewName(e).equals(view))
				return true;
		return false;
	}

	// old name = calculateLabel(EClass c)
	private List<String> labelsFromAttributes(EClass c) { // FP140204ok
		ArrayList<String> result = new ArrayList<String>();
		EList<EAttribute> latt = c.getEAttributes();
		for (EAttribute att : latt) {
			// The attribute must have a type
			if (att.getEAttributeType() != null)
				result.add(DiagraphKeywords.LABEL + "=" + att.getName());
		}
		return result;
	}

	private List<String> detail(String element) {
		ArrayList<String> ants = new ArrayList<String>();
		ants.add(element);
		return ants;
	}

	public List<EClass> propagateNodes(String view,  EClass c) { //FP140605xxx

		List<EClass> p = null;
		try {
			/*
			 * gramgen.generate(); gramgen.computePov(); propagateNonVisual();
			 * propagateByInheritance();
			 */
			p = propagateNodes(c, view);
			for (EClass e : p) {
				clog(e.getName());
			}
		} catch (Exception e) {
			controler.cerror("error on propagate");
		}
		return p;

	}

	// old name =changeAnnotColorAndAddMoreAnnots()

	/*
	private Map<EClass, List<String>> propagateNonVisual_() {
		Map<EClass, List<String>> nvresult = new HashMap<EClass, List<String>>();
		for (View gview : (List<View>) getControler().getEcoreDiagram()
				.getChildren()) {
			if (gview instanceof Node) {
				Node node = (Node) gview;
				if (node.getElement() instanceof EClass) {
					EClass notAnnotated = (EClass) node.getElement();
					if (!isDiagraphAnnotated(notAnnotated, getControler()
							.getCurrentView())) {
						// Non visual element
						if (!EcoreSimilarityChecker.getVisualElements()
								.contains(notAnnotated)) {
							nvresult.put(notAnnotated,
									labelsFromAttributes(notAnnotated));
						}
					}
				}
			}
		}
		return nvresult;
	}

	*/

	private List<EClass> propagateNodes(EClass c, String viu) {
		List<EClass> clzez = new ArrayList<EClass>();
		if (!isNode(c, viu))
			throw new RuntimeException("should not happen in propagate");
		Diagram d = getControler().getEcoreDiagram();
		EPackage p = (EPackage) d.getElement();
		EList<EClass> superclasses = c.getEAllSuperTypes();
		for (EClass superclass : superclasses) {
			if (!isNode(superclass, viu) && !isGeneric(superclass, viu)
					&& !clzez.contains(superclass))
				clzez.add(superclass);
		}
		return clzez;
	}

	private List<EClass> propagatex_0(EClass c, String viu) {
		List<EClass> clzez = new ArrayList<EClass>();
		for (View view : (List<View>) getControler().getEcoreDiagram()
				.getChildren()) {
			if (view instanceof Node) {
				Node node = (Node) view;
				if (node.getElement() instanceof EClass) {
					EClass cl = (EClass) node.getElement();

					if (cl != c && isNode(cl, viu)) { // un supertype taggé
														// bnode appartenant à
														// la vue
						if (c.getEAllSuperTypes().contains(cl))

							if (!clzez.contains(cl))
								clzez.add(cl);
					}

				}
			}
		}
		return clzez;
	}

	private Map<EClass, List<String>> propagateByInheritance_() {
		Map<EClass, List<String>> inhresult = new HashMap<EClass, List<String>>();
		Diagram d = getControler().getEcoreDiagram();
		String lang = ((EPackage) d.getDiagram().getElement()).getName();
		String viu = getControler().getCurrentView(7, lang,
				this.getClass().getName()); // FP140611
		for (View view : (List<View>)d.getChildren()) {
			if (view instanceof Node) {
				Node node = (Node) view;
				if (node.getElement() instanceof EClass) {
					EClass annotated = (EClass) node.getElement();
						if (isDiagraphAnnotated(annotated, viu)) {
						for (EClass superAnnotated : annotated
								.getEAllSuperTypes()) {
							if (isDiagraphAnnotated(superAnnotated, viu)) {
								for (EAnnotation an : superAnnotated
										.getEAnnotations()) {
									if (an.getDetails().keySet()
											.contains(DiagraphKeywords.NODE)) {
										inhresult.put(annotated,
												detail(DiagraphKeywords.NODE));
									}
									if (an.getDetails().keySet()
											.contains(DiagraphKeywords.LINK)) {
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


}
