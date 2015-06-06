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
package org.isoe.diagraph.notationparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.diagraph.DPointOfView;
import org.isoe.diagraph.lang.DiagraphKeywords;
import org.isoe.diagraph.views.ViewConstants;
import org.isoe.extensionpoint.ecoreutils.IEcoreUtils;
//import org.isoe.extensionpoint.languagehandler.ILanguageControler;
import org.isoe.extensionpoint.languagehandler.ILanguageHandler;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.core.Separator;
import org.isoe.fwk.pathes.PathPreferences;
import org.isoe.fwk.utils.FilesUtils;
import org.isoe.fwk.utils.debug.WorkspaceUtils;
import org.isoe.diastyle.lang.StyleConstants;

/**
 *
 * @author fpfister
 *
 */

import org.isoe.diagraph.controler.IDiagraphControler;  //FP140707refactored
public class LanguageUtils implements ILanguageHandler {

	private static final boolean LOG = DParams.LanguageUtils_LOG;
	private IDiagraphControler owner;
	private LanguageTransformer transformer;
	private String currentView;
	private List<DGraph> concreteSyntax;
	private IEcoreUtils ecoreUtils;




	public void setEcoreUtils(IEcoreUtils ecoreUtils) {
		this.ecoreUtils = ecoreUtils;
	}

	public void setCurrentView(String curView) { // FP121015
		if (curView == null)
			currentView = ViewConstants.DIAGRAPH_DEFAULT;
		else
			currentView = curView;
	}

	public String getCurrent_View() {
		return currentView;
	}

	private void setNameable(EClass namedClass, EClass eClass) {
		if (namedClass == null) {
			transformer.getEcoreService().createAttribute(eClass,
					EcorePackage.eINSTANCE.getEString(), DParams.DEFAULT_NAME);
			// TODO ajouter l'annotation label=
			return;
		}
		if (!namedClass.isSuperTypeOf(eClass))
			eClass.getESuperTypes().add(namedClass);
	}

	private String parseView(EAnnotation eAnnotation) {
		for (Map.Entry<String, String> entry : eAnnotation.getDetails())
			if (entry.getKey().startsWith(ViewConstants.VIEW_EQ))
				return entry.getKey().split("=")[1];
		return ViewConstants.DIAGRAPH_DEFAULT;
	}

	public EAnnotation getInViewDiagraphAnnotation(EClass eclass) {
		try {
			for (EAnnotation eAnnotation : eclass.getEAnnotations()) { // FP121015
				if (eAnnotation.getSource().equals(
						DiagraphKeywords.ANNOT_DIAGRAPH_LITTERAL)) {
					if (parseView(eAnnotation).equals(currentView))
						return eAnnotation;
				}
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	private EAnnotation addSourceAnnotation_(EClass eclass) {
		EAnnotation tn = getInViewDiagraphAnnotation(eclass);
		if (tn == null) {
			EAnnotation an = EcoreFactory.eINSTANCE.createEAnnotation();
			an.setSource(DiagraphKeywords.ANNOT_DIAGRAPH_LITTERAL);
			an.setEModelElement(eclass);
			an.getDetails().put(ViewConstants.VIEW_EQ + currentView,
					DParams.NULL_ANNOTATION_VALUE);
		}
		return tn;
	}

	public boolean isCurrentValidToken(String tok) {
		String t = tok;
		String[] composedToken = tok.split(":");
		if (composedToken.length == 2)
			t = composedToken[0];
		for (String key : DiagraphKeywords.VALID_TOKENS) {
			if (key.equals(t))
				return true;
			else if (key.endsWith("*")
					&& t.startsWith(key.substring(0, key.length() - 1))) // FP120716
				return true;

		}
		return false;
	}

	public EAnnotation createEntry(EClass eClass, String key, String value,
			String view) {
		if (!isCurrentValidToken(key))
			throw new RuntimeException("createAnnotation:  not valid token");
		return transformer.getEcoreService().createEntry(eClass,
				DiagraphKeywords.ANNOT_DIAGRAPH_LITTERAL, key + "=" + value,
				"", view);
	}

	private void applySemanticLinkPatternSnippet(EClass eClass) {
		EClass sourceClass = null;
		EClass targetClass = null;
		EReference reference = null;
		if (LOG)
			clog("refactoring " + sourceClass.getName() + "-"
					+ reference.getName() + "->" + targetClass.getName());
		String oldrefname = reference.getName();
		int oldrefcard = reference.getUpperBound();
		transformer.getEcoreService().removeReference(reference);
		// org.isoe.diagraph.internal.m2.parser.DAnnotation.removeDetail(runner,sourceClass,
		// DAnnotation.REFERENCE, oldrefname);
		EClass linkClass = transformer.getEcoreService().createClass(
				sourceClass.getEPackage(),
				sourceClass.getName() + "_" + targetClass.getName());
		transformer.getEcoreService().createReference(sourceClass, linkClass,
				oldrefname + "_", 0, oldrefcard, true);
		setNameable(eClass, linkClass);
		transformer.getEcoreService().createReference(linkClass, targetClass,
				oldrefname + "_", 0, 1, false);
		addSourceAnnotation_(linkClass);
		createEntry(sourceClass, DiagraphKeywords.LNK, oldrefname + "_",
				getCurrent_View());
		createEntry(sourceClass, ViewConstants.VIEW, getCurrent_View(),
				getCurrent_View());
	}

	public void setEObjectFeature(EObject eObject, String value,
			EAttribute eAttribute) {
		if (eAttribute.getEType() == EcorePackage.eINSTANCE.getEString()) {
			eObject.eSet(eAttribute, value);
		} else if (eAttribute.getEType() == EcorePackage.eINSTANCE.getEInt()
				|| eAttribute.getEType() == EcorePackage.eINSTANCE
						.getEIntegerObject()) {
			eObject.eSet(eAttribute, new Integer(value));
		}
	}

	public IDiagraphControler getCloneControler() {
		return owner;
	}

	private EPackage getPackageFromPath_(String path) {
		String domain = path.substring(path.lastIndexOf(File.separator) + 1);
		domain = domain.substring(0, domain.indexOf("."));
		return getPackageFromName(domain, domain);
	}

	private EPackage getPackageFromName(String pluginid, String lang) {
		String path = PathPreferences.getPreferences().getTeamNamespace() + "."
				+ pluginid + DParams.SEP_MODEL_FOLDER_SEP + lang + ".ecore";
		return (EPackage) (new ResourceSetImpl().getResource(
				URI.createPlatformResourceURI(path, false), true).getContents()
				.get(0));
	}

	private Resource getEObjectFromResource(ResourceSet rs, String pluginid,
			String qualifiedResource) {
		String path = pluginid + DParams.SEP_MODEL_FOLDER_SEP
				+ qualifiedResource;
		return (rs
				.getResource(URI.createPlatformResourceURI(path, false), true));
	}

	// TODO check preexistence of the language, with an option to overwrite it

	private EPackage transform(EPackage tpack,
			String origin, String newlang, EClass sourceClass, EClass mergeClass,
			boolean clone, boolean parse) {
		if (mergeClass != null) //FP140618vvv
			concreteSyntax = transform(sourceClass, mergeClass, clone, parse); // trg,
		if (clone)
			return save(tpack, origin, newlang);
		else
			return tpack;
	}

	private EPackage save(EPackage trg, String origin, String lang) {

		trg.setName(lang);
		trg.setNsURI("http://" + lang);
		trg.setNsPrefix("_" + lang);
		URI newuri = URI.createPlatformResourceURI(PathPreferences
				.getPreferences().getTeamNamespace()
				+ "."
				+ origin
				+ DParams.SEP_MODEL_FOLDER_SEP + lang + ".ecore", false);
		Resource resource = new XMIResourceFactoryImpl().createResource(newuri);
		resource.getContents().add(trg);
		try {
			resource.save(null);
		} catch (IOException e) {
			throw new RuntimeException("LanguageParser, error while saving "
					+ e.toString());
		}
		if (LOG)
			clog("saved:" + resource.getURI().toString());

		if (DParams.merge_LOG)
			clogmerge("saved:" + resource.getURI().toString());
		return trg;
	}

	/*
	 *
	 * private void parsePackage(EPackage pack, String origin, String
	 * newlang,DGraph diagraph, EClass toMerge) { if (toMerge != null)
	 * //parse(pack, diagraph,toMerge,true); parse(pack, diagraph,toMerge,true);
	 *
	 * }
	 */

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	private void log(List<AbstractVisitor> edges) {
		clog("-----------------");
		for (int i = edges.size() - 1; i > -1; i--) {
			AbstractVisitor e = edges.get(i);
			EClass src = null;
			if (e instanceof AbstractEdgeVisitor) {
				AbstractEdgeVisitor aev = (AbstractEdgeVisitor) e;
				if (aev.getSource() != null) {
					src = (EClass) aev.getSource();
				}
			}
			EClassifier trg = null; // can be an EEnum
			if (e.getTarget() != null) {
				trg = (EClassifier) e.getTarget();
			}
			clog((src == null ? "    " : src.getName()) + "=>"
					+ (trg.getName() + ((trg instanceof EEnum) ? " (e)" : "")));
		}
	}

	private void saveAllEditors() {
		IWorkbenchPage workbenchPage = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		if (workbenchPage != null)
			workbenchPage.saveAllEditors(false);
	}

	private String newParseItTodo_nu(EClass rootToMerge_,
			boolean headless) {
		String result = "";
		if (!owner.isLanguageConfiguration())
			throw new RuntimeException("deactivated feature in model config");
		try {
			saveAllEditors(); // FP131116
			String languageName = owner.getLanguageName();
			if (languageName == null || languageName.isEmpty()) {
				String msg = "No active language: you should open an Ecore Diagram on a metamodel located within a project !";
				clog(msg);
				return null;
			}
			try {
				EPackage[] packages = transformMetaModelTodo_nu(ecoreUtils,
						rootToMerge_);// clonName,//
										// rootToMerge.getEPackage().getName());
				owner.log("Language parsed: " + packages[0].getName());// +
																		// " => "
				result = packages[0].getName();
			} catch (Exception e) {

				String msg = "operation failed: " + e.toString();
				clog(msg);
				if (e.toString().contains("NullPointerException")) {
					String err = "";
					StackTraceElement[] stack = e.getStackTrace();
					for (int i = 0; i < Math.min(stack.length, 5); i++)
						err += stack[i].toString() + "";
					owner.log(1, err.substring(0, 100)); // FP130823
				} else if (e.toString().contains(
						"Concrete Syntax Parsing - error")) {
					owner.log(1, msg); // TODO
				} else {
					if (!headless)
						owner.showMessage(msg);
				}
			}
		} catch (Exception e) {
			String msg = "(1)cloning failed: " + e.toString();
			owner.log(msg);
			owner.showMessage(msg);
			clog(msg);
		}

		return result;
	}


	private void cerror(String mesg){
		getControler().cerror(mesg);
	}

	@Override
	public EPackage clone(DGraph currentDiagraph,
			boolean headless) {// FP131207

		if (DParams.MegaModelBuilderJOB_LOG)
			jobclog("LanguageUtils.clone "+currentDiagraph.getFacade1().getLanguageName());


		EClass trg =null;
		DPointOfView root = currentDiagraph.getPointOfView();
		EClass toTransform = root.getEClaz();
		String result = "";
		if (!owner.isLanguageConfiguration())
			throw new RuntimeException("deactivated feature in model config");
		try {
			saveAllEditors(); // FP131116

			String clonName_ = owner.getCloneName(true);
			if (clonName_ == null){
				cerror("No clone name !");
				return null;
			}

			String languageName = currentDiagraph.getFacade1().getLanguageName();
			/*
			String languageName = owner.getLanguageName();
			if (languageName == null || languageName.isEmpty()) {
				String msg = "No active language !";
				cerror(msg);
				// cloneControler.showMessage(msg);
				return null;
			}*/

			if (DParams.merge_LOG)
				clogmerge("clone " + languageName + " -> " + clonName_);

			/*
			EPackage[] packages = cloneModels(transform_clone(
					clonName_,
					toTransform, trg,
					headless));*/

			EPackage[] packages = cloneModels(transform_v2(
					clonName_,
					toTransform, trg,
					headless));


			if (LOG)
				clog("Language cloned: " + packages[0].getName() + " => "
						+ packages[1].getName());

			if (DParams.MegaModelBuilderJOB_LOG)
				jobclog("Language cloned: " + packages[0].getName() + " => "
						+ packages[1].getName());


			owner.setClonedPackage(packages[1]);


			result = packages[1].getName();
			if (DParams.merge_LOG)
				clogmerge("clone result " + result);
			if (DParams.MegaModelBuilderJOB_LOG)
				jobclog("clone result " + result);
			return packages[1];
		} catch (Exception e) {
			String msg = "(3)cloning failed: " + e.toString();
			owner.log(msg);
			if (!headless)
				owner.showMessage(msg);
			clog(msg);
			return null;
		}



	//	return cloneint(ecoreUtils, toTransform, null,allowRandom,currentDiagraph, headless,sender);// ,genEmfGen
	}





	private EPackage merge_v2(IEcoreUtils ecoreUtils, EClass src, EClass trg,
			boolean headless, boolean byscript) {// ,boolean genEmfGen
		String result = "";
		if (!owner.isLanguageConfiguration())
			throw new RuntimeException("deactivated feature in model config");
		try {
			saveAllEditors(); // FP131116
			String clonName_ = owner.getCloneName(true);
			if (clonName_ == null){
				String msg = "No clone name !";
				cerror(msg);
				return null;
			}

			if (src==null){
				String msg = "No active language !";
				cerror(msg);
				return null;
			}
			if (trg==null){
				String msg = "No merge language !";
				cerror(msg);
				return null;
			}

			String srcName =src.getEPackage().getName();
			String mergeName = trg.getEPackage().getName();

			if (DParams.merge_LOG)
				clogmerge("merge " + srcName + " U " + mergeName + " -> "
						+ clonName_);
			try {
				EPackage[] packages = transform_v2( clonName_, src,
						trg, headless);// rootToMerge.getEPackage().getName());
				//if (!headless)
				//	owner.showMessage("Languages merged: "
					//		+ languageName + " U " + mergeName
						//	 + " => "
							//+ clonName_);
				if (DParams.merge_LOG)
					clogmerge("Languages merged: "
							+ srcName + " U " + mergeName
							 + " => "
							+ clonName_);
				result = packages[1].getName();
				if (DParams.merge_LOG)
					clogmerge("merge result " + result);
				return packages[1];
			} catch (Exception e) {
				String msg = "operations failed: " + e.toString();
				cerror(msg);
				owner.log(msg);
				if (!headless)
					owner.showMessage(msg);
				return null;
			}
			// result = "";
		} catch (Exception e) {
			String msg = "(2)merging failed: " + e.toString();
			owner.log(msg);
			if (!headless)
				owner.showMessage(msg);
			cerror(msg);
			return null;
		}
	}







	private EPackage merge_new_nu(IEcoreUtils ecoreUtils, EClass src, EClass trg,
			boolean headless) {//FP140626pbici  // ,boolean genEmfGen
		String result = "";
		if (!owner.isLanguageConfiguration())
			throw new RuntimeException("deactivated feature in model config");
		try {
			if (DParams.merge_LOG)
				clogmerge("merge src="+src.getName()+" trg="+trg.getName());
//merge language src: currentDGraph=statechart.default mrg: mergeDGraph=helloworld.default trg= not headless root=World
			saveAllEditors(); // FP131116

			String clonName_ = owner.getCloneName(true);//cscjfm
			if (clonName_ == null){
				String msg = "No clone name !";
				cerror(msg);
				return null;
			}


			if (src==null) {
				String msg = "No active language !";
				cerror(msg);
				return null;
			}

			String languageName = src.getEPackage().getName();
			//String languageName__ = owner.getLanguageName_(); //statechart


			if (trg == null) {
				String msg = "No language to merge with !";
				cerror(msg);
				return null;
			}

			String mergeName = trg.getEPackage().getName();
			//String mergeName__ = owner.getLanguageToMergeName_();//helloworld

			if (DParams.merge_LOG) //merge statechart U helloworld -> cscjfm
				clogmerge("merge " + languageName + " U " + mergeName + " -> "
						+ clonName_);
			try {
				EPackage[] packages = transform_clone(clonName_, src, trg, headless);// rootToMerge.getEPackage().getName());
				//EPackage[] packages = transform_(clonName_, trg, src, headless);// rootToMerge.getEPackage().getName());
				if (!headless)
					owner.showMessage("Languages merged: "
							+ src.getEPackage().getName() + " U "
							+ packages[0].getName() + " => "
							+ packages[1].getName());
				result = packages[1].getName();
				if (DParams.merge_LOG)
					clogmerge("merge result " + result);
				return packages[1];
			} catch (Exception e) {
				String msg = "operations failed: " + e.toString();
				cerror(msg);
				owner.log(msg);
				if (!headless)
					owner.showMessage(msg);
				return null;
			}
			// result = "";
		} catch (Exception e) {
			String msg = "(2)merging failed: " + e.toString();
			owner.log(msg);
			if (!headless)
				owner.showMessage(msg);
			cerror(msg);
			return null;
		}
	}


	private void clogmerge(String mesg) {
		if (DParams.merge_LOG)
			System.out.println(mesg);

	}

	/*
	 * @Override public String request_(IEcoreUtils ecoreUtils, String id,
	 * Object[] arguments) { if (id.equals("parse")) return parseIt(ecoreUtils,
	 * (EClass) arguments[0]); // (DGraph) // arguments[1] if
	 * (id.equals("clone")) return cloneIt(ecoreUtils, (DGraph)
	 * arguments[0],(Boolean) arguments[1]); else if (id.equals("merge")) return
	 * mergeIt(ecoreUtils, (EClass) arguments[0], (Boolean) arguments[1]); // ,
	 * (DGraph) return null; }
	 */



	@Override
	public String newParse_nu(EClass toTransform,
			boolean headless) { // FP131207
		throwNotToUse();
		return newParseItTodo_nu(toTransform, headless);
	}

	private void throwNotToUse() {
		throw new RuntimeException("TODO implement newParse_nu");
	}


	@Override
	// public String merge(IEcoreUtils ecoreUtils, DGraph currentDiagraph)
	// {//FP131207 //, boolean genEmfGen
	public EPackage merge( DGraph srcDiagraph,
			DGraph withDiagraph,  boolean headless, boolean byscript) {// FP131207
		if (DParams.MegaModelBuilderJOB_LOG)
			jobclog("LanguageUtils.merge "+srcDiagraph.getFacade1().getLanguageName());
		DPointOfView root = srcDiagraph.getPointOfView();
		EClass toTransform = root.getEClaz();
		DPointOfView with = withDiagraph.getPointOfView();
		EClass toMerge = with.getEClaz();
		return merge_v2(ecoreUtils, toTransform, toMerge, headless,byscript); // ,
																	// (DGraph)
	}


	private void jobclog(String mesg) {
		if (DParams.MegaModelBuilderJOB_LOG)
			System.out.println(mesg);
	}







	/*
	 * private void parse(EPackage given,DGraph diagraph, EClass rootToMerge,
	 * boolean clone) { packageParser = new
	 * PackageParser(this,given,diagraph,rootToMerge); List<AbstractVisitor>
	 * edges = packageParser.parse(clone); log(edges); }
	 */



	private List<DGraph> transform(
			EClass sourceClass, EClass mergeClass, boolean clone, boolean parse) {// FP130612

		//if (mergeClass !=null)
		     transformer = new LanguageTransformer(this, sourceClass, mergeClass,false); // trg,
		//else
		//	 transformer = new LanguageTransformer(this, null, null,dummy); // trg,

		     transformer.setEcoreUtils(this.ecoreUtils);
		     List<AbstractVisitor> edges = transformer
				.doit( clone, parse); // FP130723
		log(edges);
		return transformer.getConcreteSyntax();
	}

	private int isDiagraphDefaultView(EAnnotation annot) {
		if (annot.getSource().equals(DiagraphKeywords.ANNOT_DIAGRAPH_LITTERAL)) {
			for (Map.Entry<String, String> entry : annot.getDetails()) {
				if (entry.getKey().startsWith(DiagraphKeywords.VIEW_EQ)) {
					return 0; // a view but not default
				}
			}
			return 1; // no view entry, so it's the default view
		}
		return -1; // not a diagraph annotation
	}

	private boolean isDiagraphView(EAnnotation annot, String view) {
		String viewname = "default";// FP121010
		if (annot.getSource().equals(DiagraphKeywords.ANNOT_DIAGRAPH_LITTERAL)) {
			for (Map.Entry<String, String> entry : annot.getDetails()) {
				if (entry.getKey().startsWith(DiagraphKeywords.VIEW_EQ)) {
					String[] pars = entry.getKey().split("=");
					viewname = pars[1];
				}
			}
			return view.equals(viewname);
		} else
			return false;
	}

	private EAnnotation getDiagraphAnnotation(EClass eclass, String view) {
		EList<EAnnotation> annots = eclass.getEAnnotations();
		EAnnotation result = null;
		for (EAnnotation annot : annots) {
			if (isDiagraphView(annot, view)) {
				result = annot;
				break;
			}
		}
		if (result == null && view.equals("default"))
			for (EAnnotation annot : annots) {
				if (isDiagraphDefaultView(annot) == 1) {
					result = annot;
					break;
				}
			}
		return result;
	}

	@Override
	public EReference parseExtraContainmentReference(EClass ecl_, // FP131008
			String view) {
		EAnnotation annot = getDiagraphAnnotation(ecl_, view);
		for (Map.Entry<String, String> entry : annot.getDetails()) {
			if (entry.getKey().startsWith(
					org.isoe.diagraph.lang.DiagraphKeywords.XCONTAINMENT_EQU)) { // FP131009//XCONTAINMENT
				String[] pars = entry.getKey().split("=");
				String contname = pars[1];
				pars = contname.split("\\.");
				return (EReference) ((EClass) ecl_.getEPackage()
						.getEClassifier(pars[0]))
						.getEStructuralFeature(pars[1]);
			}
		}
		return null;
	}

	@Override
	public EClass getPovForView(EPackage pakag, String view) { // FP131008xx
		for (EClassifier eClassifier : pakag.getEClassifiers()) {
			if (eClassifier instanceof EClass) {
				EAnnotation annot = getDiagraphAnnotation((EClass) eClassifier,
						view);
				if (annot != null)
					for (Map.Entry<String, String> entry : annot.getDetails()) {
						if (entry.getKey().startsWith("pov")) {
							return (EClass) eClassifier;
						}
					}
			}
		}
		return null;
	}

	private EPackage[] transformMetaModelTodo_nu(IEcoreUtils ecoreUtils,
			EClass rootToMerge) {
		EPackage[] cl = transformPackageTodo_nu(ecoreUtils, rootToMerge);
		return cl;
	}
/*
	private EPackage[] cloneMetaModel(IEcoreUtils ecoreUtils, String cloneName,EClass src, EClass trg, boolean headless) throws IOException {
		EPackage[] cl = clonePackage_old_(ecoreUtils, cloneName, src, trg, headless);
		return deployLocalLanguage(moveLanguageToLocalRepository(cloneDiagramM2_(cl)));
	}
*/
	private EPackage[] transform_clone(String cloneName, EClass sourceClass, EClass mergeClass, boolean headless) throws IOException {
		EPackage[] packs = doTransform_clone(ecoreUtils, cloneName, sourceClass, mergeClass, headless);
		packs = cloneDiagramM2(packs);
		packs = moveLanguageToLocalRepository(packs);
		packs = deployLocalLanguage_(packs);
		return packs;
		//return deployLocalLanguage(moveLanguageToLocalRepository(cloneDiagramM2_(doTransform(ecoreUtils, cloneName, sourceClass, mergeClass, headless))));
	}




	private EPackage[] transform_v2(String cloneName, EClass sourceClass, EClass mergeClass, boolean headless) throws IOException {
		EPackage[] packs = doTransform_v2(ecoreUtils, cloneName, sourceClass, mergeClass, headless);
		packs = cloneDiagramM2(packs);
		packs = moveLanguageToLocalRepository(packs);
		packs = deployLocalLanguage_(packs);
		return packs;
	}



	private EPackage[] deployLocalLanguage_(EPackage[] packages) {
		boolean deployBuildInPlugin = false;
		boolean deployLocalPlugin = false;
		boolean deployLocalWorkspace = true;
		owner.deployMegamodelClone_(deployBuildInPlugin,// genEmfGen,
				deployLocalPlugin, deployLocalWorkspace, packages[1].getName());
		// packages[0] is the original language
		return packages;
	}

	private EPackage[] moveLanguageToLocalRepository(EPackage[] packz)
			throws IOException {
		EPackage origin = packz[0];
		EPackage cloned = packz[1];
		String targetProject = PathPreferences.getPreferences()
				.getUniverseProjectName();
		String originProject = PathPreferences.getPreferences()
				.getTeamNamespace() + "." + origin.getName();
		String target = targetProject + "/"
				+ PathPreferences.getPreferences().getUniverseFolder();
		URI targuri = URI.createPlatformResourceURI(target, false);
		URI srcuri = URI.createPlatformResourceURI(originProject, false);
		File targfolder = new File(CommonPlugin.resolve(targuri).toFileString());
		File srcfolder = new File(CommonPlugin.resolve(srcuri).toFileString());
		String srcdiag = srcfolder + DParams.SEP_MODEL_FOLDER_SEP
				+ cloned.getName() + ".ecorediag";
		srcdiag = srcdiag.replaceAll("/", Separator.SEPARATOR);
		String srcpack = srcfolder + DParams.SEP_MODEL_FOLDER_SEP
				+ cloned.getName() + ".ecore";
		srcpack = srcpack.replaceAll("/", Separator.SEPARATOR);
		String destdiag = targfolder + File.separator + cloned.getName()
				+ ".ecorediag";
		destdiag = destdiag.replaceAll("/", Separator.SEPARATOR);
		String destpack = targfolder + File.separator + cloned.getName()
				+ ".ecore";
		destpack = destpack.replaceAll("/", Separator.SEPARATOR);

		File sd = new File(srcdiag);
		File sp = new File(srcpack);

		FilesUtils.copyFile(sd, new File(destdiag));
		if (LOG)
			clog("copy " + sd.getName() + " -> " + destdiag + " ok");
		FilesUtils.copyFile(sp, new File(destpack));
		if (LOG)
			clog("copy " + sp.getName() + " -> " + destpack + " ok");
		sd.delete();
		sp.delete();

		WorkspaceUtils.getInstance().refreshProject(
				targetProject, false);
		WorkspaceUtils.getInstance().refreshProject(
				originProject, false);
		return packz;
	}

	private EPackage[] parseDiagramM2(EPackage[] packz) throws IOException {
		EPackage origin = packz[0];
		EPackage cloned = packz[1];
		ResourceSet rs = new ResourceSetImpl();
		String project = PathPreferences.getPreferences().getTeamNamespace()
				+ "." + origin.getName();// origin;//+DParams.SEP_MODEL_FOLDER_SEP;
		Resource rdiag = getEObjectFromResource(rs, project, origin.getName()
				+ ".ecorediag");
		Diagram diagr = (Diagram) rdiag.getContents().get(0);
		String newdiaguripath = PathPreferences.getPreferences()
				.getTeamNamespace()
				+ "."
				+ origin.getName()
				+ DParams.SEP_MODEL_FOLDER_SEP
				+ cloned.getName()
				+ ".ecorediag";
		URI newdiaguri = URI.createPlatformResourceURI(newdiaguripath, false);
		Resource newdiagrs = new XMIResourceFactoryImpl()
				.createResource(newdiaguri);
		newdiagrs.getContents().add(diagr);
		rs.getResources().add(newdiagrs);
		newdiagrs.save(null);
		FilesUtils.replaceInFile(CommonPlugin.resolve(newdiaguri)
				.toFileString(), "href=\"" + origin.getName() + ".ecore",
				"href=\"" + cloned.getName() + ".ecore");
		org.isoe.fwk.utils.debug.WorkspaceUtils.getInstance().refreshProject(
				PathPreferences.getPreferences().getTeamNamespace() + "."
						+ origin.getName(), false);
		return packz;
	}

	private void parseDiagramM2_old(EPackage[] packz) {
		EPackage origin = packz[0];
		EPackage cloned_ = packz[1];
		ResourceSet rs = new ResourceSetImpl();
		String project = PathPreferences.getPreferences().getTeamNamespace()
				+ "." + origin.getName();// origin;//+DParams.SEP_MODEL_FOLDER_SEP;
		Resource rdiag = getEObjectFromResource(rs, project, origin.getName()
				+ ".ecorediag");
		Diagram diagr = (Diagram) rdiag.getContents().get(0);
		String newdiaguripath = PathPreferences.getPreferences()
				.getTeamNamespace()
				+ "."
				+ origin.getName()
				+ DParams.SEP_MODEL_FOLDER_SEP
				+ cloned_.getName()
				+ ".ecorediag";
		clog("parseDiagramM2 " + newdiaguripath);

		/*
		 * URI newdiaguri = URI.createPlatformResourceURI(newdiaguripath,
		 * false); Resource newdiagrs = new XMIResourceFactoryImpl()
		 * .createResource(newdiaguri); newdiagrs.getContents().add(diagr);
		 * rs.getResources().add(newdiagrs); newdiagrs.save(null);
		 * FilesUtils.replaceInFile(CommonPlugin.resolve(newdiaguri)
		 * .toFileString(), "href=\"" + origin.getName() + ".ecore", "href=\"" +
		 * cloned_.getName() + ".ecore");
		 * ResourceManager.refreshProject(PathPreferences.getPreferences()
		 * .getTeamNamespace() + "." + origin.getName());
		 */
		// return packz;

	}

	private EPackage[] cloneDiagramM2(EPackage[] packz) throws IOException {
		EPackage origin = packz[0];
		EPackage cloned = packz[1];
		ResourceSet rs = new ResourceSetImpl();
		String project = PathPreferences.getPreferences().getTeamNamespace()
				+ "." + origin.getName();// origin;//+DParams.SEP_MODEL_FOLDER_SEP;
		Resource rdiag = getEObjectFromResource(rs, project, origin.getName()
				+ ".ecorediag");
		Diagram diagr = (Diagram) rdiag.getContents().get(0);
		String newdiaguripath = PathPreferences.getPreferences()
				.getTeamNamespace()
				+ "."
				+ origin.getName()
				+ DParams.SEP_MODEL_FOLDER_SEP
				+ cloned.getName()
				+ ".ecorediag";
		URI newdiaguri = URI.createPlatformResourceURI(newdiaguripath, false);
		Resource newdiagrs = new XMIResourceFactoryImpl()
				.createResource(newdiaguri);
		newdiagrs.getContents().add(diagr);
		rs.getResources().add(newdiagrs);
		newdiagrs.save(null);
		FilesUtils.replaceInFile(CommonPlugin.resolve(newdiaguri)
				.toFileString(), "href=\"" + origin.getName() + ".ecore",
				"href=\"" + cloned.getName() + ".ecore");
		WorkspaceUtils.getInstance().refreshProject(
				PathPreferences.getPreferences().getTeamNamespace() + "."
						+ origin.getName(), false);
		return packz;
	}

	private List<String> filesInUri(URI uri) {
		List<String> result = new ArrayList<String>();
		try {
			result = org.isoe.fwk.utils.FilesUtils.filesInUri(uri);
		} catch (Exception e) {
			System.out.println("error filesInUri");
		}
		return result;
	}

	private List<String> getInstancesOf(EPackage ePackage) {
		String repo = PathPreferences.getPreferences().getUniverseProjectName()
				+ "/" + PathPreferences.getPreferences().getUniverseFolder();
		URI modelFolderUri = URI.createPlatformResourceURI(repo, false);
		List<String> instances = new ArrayList<String>();
		List<String> files = filesInUri(modelFolderUri);
		for (String fil : files) {
			// clog(fil);
			String fname = fil.substring(fil.lastIndexOf(File.separator) + 1);
			String ext = fname.substring(fname.lastIndexOf(".") + 1);
			if (ext.equals(ePackage.getName()))
				instances.add(fil);
		}
		return instances;
	}

	private EPackage[] cloneModels(EPackage[] ePackages) { // FP130518azy
		List<String> models = getInstancesOf(ePackages[0]);
		try {
			for (String instance : models)
				cloneModel(instance, ePackages, false);

			for (String instance : models) {
				File diagfile = new File(instance + "_default_root_diagram");
				if (diagfile.exists()) {
					cloneModel(diagfile.getAbsolutePath(), ePackages, true);
				}
			}
		} catch (IOException e) {
			cerror(e.toString());
		}
		return ePackages;
	}

	private String replace(String line, String[][] replace) {
		String b = null;
		for (int i = 0; i < replace.length; i++) {
			if (line.contains(replace[i][0])) {
				b = line.replaceAll(replace[i][0], replace[i][1]);
				break;
			}
		}
		return b;
	}

	private void createFromTemplate(String templateSheet, String targetSheet,
			String[][] replace) throws IOException {
		File inclfile = new File(templateSheet);
		InputStream in = new FileInputStream(inclfile);
		StringBuffer buffer = new StringBuffer();
		try {
			InputStreamReader inR = new InputStreamReader(in);
			BufferedReader buf = new BufferedReader(inR);
			String line;
			while ((line = buf.readLine()) != null) {
				String result = line;
				do {
					String sv = result;
					result = replace(result, replace);
					if (result == null) {
						result = sv;
						break;
					}
				} while (true);
				buffer.append(result).append("\n");
			}
		} finally {
			in.close();
		}
		OutputStream os = new FileOutputStream(targetSheet);
		os.write(buffer.toString().getBytes());
		os.close();
	}

	private String cap(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
	}

	private String[][] getinstanceClonerTransformation(String modelname,
			EPackage[] ePackages) {
		String p0name = ePackages[0].getName();
		String p1name = ePackages[1].getName();
		String p0nsp = ePackages[0].getNsPrefix();
		String p1nsp = ePackages[1].getNsPrefix();
		String p0nsu = ePackages[0].getNsURI();
		String p1nsu = ePackages[1].getNsURI();
		// transformation mapping, at the xmi level, this is sufficient here
		String[] r0 = { "type=\"" + cap(p0name) + "_default_root\"",
				"type=\"" + cap(p1name) + "_default_root\"" };
		String[] r1 = { "xmlns:" + p0nsp + "=" + "\"" + p0nsu + "\"",
				"xmlns:" + p1nsp + "=" + "\"" + p1nsu + "\"" };
		String[] r2 = { "xmi:type=\"" + p0nsp + ":",
				"xmi:type=\"" + p1nsp + ":" };
		String[] r3 = {
				"name=\"" + modelname + "." + p0name
						+ "_default_root_diagram\"",
				"name=\"" + modelname + "." + p1name
						+ "_default_root_diagram\"" };
		String[] r4 = { "href=\"" + modelname + "." + p0name + "#/",
				"href=\"" + modelname + "." + p1name + "#/" };
		String[] r5 = { "<" + p0nsp + ":", "<" + p1nsp + ":" };
		String[] r6 = { "</" + p0nsp + ":", "</" + p1nsp + ":" };
		String[][] rs = new String[7][2];
		rs[0] = r0;
		rs[1] = r1;
		rs[2] = r2;
		rs[3] = r3;
		rs[4] = r4;
		rs[5] = r5;
		rs[6] = r6;
		if (LOG) {
			int i = 0;
			for (String[] r : rs) {
				clog("replacing " + rs[i][0] + " with " + rs[i][1]);
				i++;
			}
		}
		return rs;
	}

	private void cloneModel(String srcfileName, EPackage[] ePackages,
			boolean isdiagram) throws IOException {
		String language = ePackages[0].getName();
		String filenam = srcfileName.substring(srcfileName
				.lastIndexOf(File.separator) + 1);
		filenam = filenam.substring(0, filenam.indexOf("."));
		String[][] rs = getinstanceClonerTransformation(filenam, ePackages);
		clog("---model---  " + srcfileName);
		String trgfileName = srcfileName;
		trgfileName = trgfileName
				.substring(0, trgfileName.lastIndexOf(".") + 1);
		trgfileName = trgfileName + ePackages[1].getName()
				+ (isdiagram ? "_default_root_diagram" : "");
		try {
			createFromTemplate(srcfileName, trgfileName, rs);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}


	private EPackage[] clonePackage_old(IEcoreUtils ecoreUtils, String cloneName,
			EClass sclazz_,EClass mergeClass_, boolean headless) throws IOException {
		//if (LOG)
		//	clog("clonePackage "+(sclazz_==null?"":sclazz_.getName()));

		if (LOG)
			clog((mergeClass_ == null ? "clonePackage " : "mergePackage ")
					+ (sclazz_ == null ? "" : (sclazz_.getEPackage().getName()
							+ ";" + sclazz_.getName()))
					+ (mergeClass_ == null ? "" : (" U "+(mergeClass_.getEPackage().getName()
							+ ";" + mergeClass_.getName()))));

		EPackage[] result = new EPackage[2];
		String langname = owner.getLanguageName();
		String nn = PathPreferences.getPreferences().getTeamNamespace() + "."
				+ langname;
		URI curnturi = URI.createPlatformResourceURI(nn, false);
		String trgpath = CommonPlugin.resolve(curnturi).toFileString()
				+ DParams.SEP_MODEL_FOLDER_SEP + langname + ".ecore";
		trgpath = trgpath.replaceAll("/", Separator.SEPARATOR);
		String newlang = cloneName;
		boolean clone = true;
		boolean parse = false;

		if (!newlang.isEmpty()) {
			// newlang = normalize(newlang);


			//transform(ecoreUtils, getPackageFromPath_(trgpath), langname,
		//			newlang, sclazz_, mergeClass_,clone, parse);

			transform(getPackageFromPath_(trgpath), langname,
					newlang, sclazz_, null,clone, parse); //FP140618zzz



			WorkspaceUtils.getInstance()
					.refreshProject(
							PathPreferences.getPreferences().getTeamNamespace()
									+ "." + langname, false);
			result[1] = getPackageFromName(langname, newlang);
			result[0] = getPackageFromName(langname, langname);
			// TODO work within an emf transaction
			// reload the
			// original, (it
			// has been
			// modified in
			// the resource)
		} else {
			String msg = "(4)cloning failed: no name";

			owner.log(msg);
			owner.showMessage(msg);
			clog(msg);
		}

		return result;
	}

	private EPackage[] doTransform_v2(IEcoreUtils ecoreUtils, String cloneName,
			EClass sclazz_, EClass mergeClass, boolean headless) throws IOException {
		if (DParams.merge_LOG)
			clogmerge((mergeClass == null ? "clonePackage " : "mergePackage ")
					+ (sclazz_ == null ? "" : (sclazz_.getEPackage().getName()
							+ ";" + sclazz_.getName()))
					+ " U "
					+ (mergeClass == null ? "" : (mergeClass.getEPackage().getName()
							+ ";" + mergeClass.getName())));

		boolean merging = mergeClass !=null;
		EPackage[] result = new EPackage[2];
		String langname = sclazz_.getEPackage().getName();
		String newlang = cloneName;
		boolean clone = true;
		boolean parse = false;
		if (!newlang.isEmpty()) {
				EPackage pak =  merging?mergeClass.getEPackage():sclazz_.getEPackage();
		       transform_v2(ecoreUtils, pak,// getPackageFromPath(trgpath),
					langname, newlang, sclazz_, mergeClass, clone, parse);
			WorkspaceUtils.getInstance().refreshProject(
					PathPreferences.getPreferences().getTeamNamespace() + "."
							+ langname, false);
			result[1] = getPackageFromName(langname, newlang);
			result[0] = getPackageFromName(langname, langname);
			// TODO work within an emf transaction
			// reload the
			// original, (it
			// has been
			// modified in
			// the resource)
		} else {
			String msg = "(4)cloning failed: no name";
			owner.log(msg);
			if (!headless)
				owner.showMessage(msg);
			clog(msg);
		}

		return result;
	}


	private EPackage transform_v2(IEcoreUtils ecoreUtils, EPackage tpack,
			String origin, String newlang, EClass sourceClass, EClass mergeClass,
			boolean clone, boolean parse) {
		if (mergeClass != null) //FP140618vvv
			concreteSyntax = transform(sourceClass, mergeClass, clone, parse); // trg,
		if (clone)
			return save(tpack, origin, newlang);
		else
			return tpack;
	}

	private EPackage[] doTransform_new(IEcoreUtils ecoreUtils, String cloneName,
			EClass sclazz_, EClass mergeClass, boolean headless) throws IOException {
		boolean merging = sclazz_ == null ? true:false;//FP140626pbici
		if (DParams.merge_LOG)
			clogmerge((merging ? "merging " : "cloning ")
					+ (sclazz_ == null ? "" : (sclazz_.getEPackage().getName()
							+ ";" + sclazz_.getName()))
					+ " U "
					+ (mergeClass == null ? "" : (mergeClass.getEPackage().getName()
							+ ";" + mergeClass.getName())));
		EPackage[] result = new EPackage[2];
		String langname = owner.getLanguageName();
		String nn = PathPreferences.getPreferences().getTeamNamespace() + "."
				+ langname;
		URI curnturi_ = URI.createPlatformResourceURI(nn, false);
		String trgpath_ = CommonPlugin.resolve(curnturi_).toFileString()
				+ DParams.SEP_MODEL_FOLDER_SEP + langname + ".ecore";
		trgpath_ = trgpath_.replaceAll("/", Separator.SEPARATOR);
		String newlang = cloneName;
		boolean clone = true;
		boolean parse = false;

		if (!newlang.isEmpty()) {
			// newlang = normalize(newlang);

			//EPackage tp_orig_s = getPackageFromPath(trgpath); //FP140613 pb was here
			//EPackage tpak_ =  !merging?sclazz_.getEPackage():null;//statechart
			EPackage tpak_ =  merging?mergeClass.getEPackage():sclazz_.getEPackage(); //helloworld
			EPackage trgpak__ = getPackageFromPath_(trgpath_);//statechart

//FP140627
			//scpak = getPackageFromPath_(trgpath);

			transform(tpak_,// getPackageFromPath(trgpath),
					langname, newlang, sclazz_, mergeClass, clone, parse);
			WorkspaceUtils.getInstance().refreshProject(
					PathPreferences.getPreferences().getTeamNamespace() + "."
							+ langname, false);
			result[1] = getPackageFromName(langname, newlang);
			result[0] = getPackageFromName(langname, langname);
			// TODO work within an emf transaction
			// reload the
			// original, (it
			// has been
			// modified in
			// the resource)
		} else {
			String op = merging ? "merging":"cloning";
			String msg = "(4)" + op + " failed: no name";
			cerror(msg);
			owner.log(msg);
			if (!headless)
				owner.showMessage(msg);
			clog(msg);
		}
		return result;
	}


	private EPackage[] doTransform_clone(IEcoreUtils ecoreUtils, String cloneName,
			EClass sclazz_, EClass mergeClass, boolean headless) throws IOException {
		boolean merging = sclazz_ == null ? true:false;//FP140626pbici
		if (DParams.merge_LOG)
			clogmerge((merging ? "merging " : "cloning ")
					+ (sclazz_ == null ? "" : (sclazz_.getEPackage().getName()
							+ ";" + sclazz_.getName()))
					+ " U "
					+ (mergeClass == null ? "" : (mergeClass.getEPackage().getName()
							+ ";" + mergeClass.getName())));
		EPackage[] result = new EPackage[2];
		String langname = owner.getLanguageName();
		String nn = PathPreferences.getPreferences().getTeamNamespace() + "."
				+ langname;
		URI curnturi_ = URI.createPlatformResourceURI(nn, false);
		String trgpath_ = CommonPlugin.resolve(curnturi_).toFileString()
				+ DParams.SEP_MODEL_FOLDER_SEP + langname + ".ecore";
		trgpath_ = trgpath_.replaceAll("/", Separator.SEPARATOR);
		String newlang = cloneName;
		boolean clone = true;
		boolean parse = false;

		if (!newlang.isEmpty()) {
			// newlang = normalize(newlang);

			//EPackage tp_orig_s = getPackageFromPath(trgpath); //FP140613 pb was here
			//EPackage tpak_ =  !merging?sclazz_.getEPackage():null;//statechart
			EPackage tpak_ =  merging?mergeClass.getEPackage():sclazz_.getEPackage(); //helloworld
			EPackage trgpak__ = getPackageFromPath_(trgpath_);//statechart

//FP140627
			//scpak = getPackageFromPath_(trgpath);

			transform(tpak_,// getPackageFromPath(trgpath),
					langname, newlang, sclazz_, mergeClass, clone, parse);
			WorkspaceUtils.getInstance().refreshProject(
					PathPreferences.getPreferences().getTeamNamespace() + "."
							+ langname, false);
			result[1] = getPackageFromName(langname, newlang);
			result[0] = getPackageFromName(langname, langname);
			// TODO work within an emf transaction
			// reload the
			// original, (it
			// has been
			// modified in
			// the resource)
		} else {
			String op = merging ? "merging":"cloning";
			String msg = "(4)" + op + " failed: no name";
			cerror(msg);
			owner.log(msg);
			if (!headless)
				owner.showMessage(msg);
			clog(msg);
		}
		return result;
	}


	private EPackage[] doTransform_new_nu(IEcoreUtils ecoreUtils, String cloneName,
			EClass sclazz, EClass mergeClass, boolean headless) throws IOException {
		boolean merging = sclazz == null ? false:true;//FP140626pbici
		if (DParams.merge_LOG)
			clogmerge((merging ? "merging " : "cloning ")
					+ (sclazz == null ? "" : (sclazz.getEPackage().getName()
							+ ";" + sclazz.getName()))
					+ " U "
					+ (mergeClass == null ? "" : (mergeClass.getEPackage().getName()
							+ ";" + mergeClass.getName())));
		EPackage[] result = new EPackage[2];
		String langname = owner.getLanguageName();
		String nn = PathPreferences.getPreferences().getTeamNamespace() + "."
				+ langname;
		URI curnturi = URI.createPlatformResourceURI(nn, false);
		String trgpath = CommonPlugin.resolve(curnturi).toFileString()
				+ DParams.SEP_MODEL_FOLDER_SEP + langname + ".ecore";
		trgpath = trgpath.replaceAll("/", Separator.SEPARATOR);
		String newlang = cloneName;
		boolean clone = true;
		boolean parse = false;

		if (!newlang.isEmpty()) {
			// newlang = normalize(newlang);

			//EPackage tp_orig_s = getPackageFromPath(trgpath); //FP140613 pb was here
			EPackage scpak =  merging?sclazz.getEPackage():null;//statechart
			EPackage mpak =  merging?mergeClass.getEPackage():null; //helloworld
			EPackage trgpak = getPackageFromPath_(trgpath);//statechart

//FP140627
			//scpak = getPackageFromPath_(trgpath);

			transform(mpak,// getPackageFromPath(trgpath),
					langname, newlang, sclazz, mergeClass, clone, parse);
			WorkspaceUtils.getInstance().refreshProject(
					PathPreferences.getPreferences().getTeamNamespace() + "."
							+ langname, false);
			result[1] = getPackageFromName(langname, newlang);
			result[0] = getPackageFromName(langname, langname);
			// TODO work within an emf transaction
			// reload the
			// original, (it
			// has been
			// modified in
			// the resource)
		} else {
			String op = sclazz == null ? "cloning":"merging";
			String msg = "(4)" + op + " failed: no name";
			cerror(msg);
			owner.log(msg);
			if (!headless)
				owner.showMessage(msg);
			clog(msg);
		}
		return result;
	}

	private EPackage[] transformPackageTodo_nu(IEcoreUtils ecoreUtils, EClass sClaz) {
		if (LOG)
			clog("parsePackage EClass=" + sClaz.getName());
		EPackage[] result = new EPackage[2];
		String langname = owner.getLanguageName();
		String nn = PathPreferences.getPreferences().getTeamNamespace() + "."
				+ langname;
		URI curnturi = URI.createPlatformResourceURI(nn, false);
		String currentpath = CommonPlugin.resolve(curnturi).toFileString()
				+ DParams.SEP_MODEL_FOLDER_SEP + langname + ".ecore";
		currentpath = currentpath.replaceAll("/", Separator.SEPARATOR);
		EPackage thePack = getPackageFromPath_(currentpath);

		boolean clone = false;
		boolean parse = true;

		EClass tclazNull = null;
		String newLangNull = null;

		transform( thePack, langname, newLangNull, sClaz, tclazNull,
				clone, parse);
		try {
			WorkspaceUtils.getInstance().refreshProject(
					PathPreferences.getPreferences().getTeamNamespace() + "."
							+ langname, false);
		} catch (Exception e) {
			cerror("error while refreshing");
			// FP140410
		}
		// result[1] = getPackageFromName(langname, newlang);
		result[0] = getPackageFromName(langname, langname);
		owner.log("package parsed");
		return result;
	}

	@Override
	public void setControler(IDiagraphControler cloneControler) {
		this.owner = cloneControler;
	}

	@Override
	public boolean isStub() {
		return false;
	}

	@Override
	public IDiagraphControler getControler() {
		return this.owner;
	}

	@Override
	public List<DGraph> getConcreteSyntax() {
		return concreteSyntax;
	}

}
