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
package org.isoe.diagraph.internal.m2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.isoe.diagraph.generic.GenericConstants;
import org.isoe.diagraph.lang.DiagraphKeywords;
import org.isoe.diagraph.views.ViewConstants;
import org.isoe.fwk.core.DParams;
/*
import bsh.EvalError;
import bsh.Interpreter;
*/
/**
 *
 * @author pfister
 *
 */
public class AnnotationUtils {
	// org.isoe.diagraph.internal.m2.AnnotationUtils

	private static final boolean LOG = DParams.AnnotationUtils2_LOG;
	private static AnnotationUtils instance;

	public static AnnotationUtils getInstance() {
		if (instance == null)
			instance = new AnnotationUtils();
		return instance;
	}



	private AnnotationUtils() {

	}

	public List<EAnnotation> getAllAnnotationFromView(EPackage editedPackage,
			String view) {
		List<EAnnotation> result = new ArrayList<EAnnotation>();
		// String view = gramgen.getView();
		// by view
		if (LOG)
			clog("getAllAnnotationFromView (3), view=" + view +" - "+editedPackage.getName());
		// EditingDomain editingDomain = ecoreDiagramEditor.getEditingDomain();
		// EPackage editedPackage = (EPackage) getEditor().getDiagram()
		// .getElement();
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

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	private boolean isView(EAnnotation anot, String view) {
		//if (view.equals(org.isoe.diagraph.views.ViewConstants.DIAGRAPH_ALL_VIEW_LITTERAL))
		//	return true;
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

	private EAnnotation getNode(EClass eClaz, String view) {
		for (EAnnotation eAnnotation : eClaz.getEAnnotations())
			if (eAnnotation.getSource().equals(
					GenericConstants.ANNOT_DIAGRAPH_LITTERAL))
				if (isView(eAnnotation, view))
					if (hasKey(eAnnotation, "node")) {
						return eAnnotation;
					}
		return null;
	}

	private boolean isLink(EClass eClaz, String view) {
		for (EAnnotation eAnnotation : eClaz.getEAnnotations())
			if (eAnnotation.getSource().equals(
					GenericConstants.ANNOT_DIAGRAPH_LITTERAL))
				if (isView(eAnnotation, view))
					if (hasKey(eAnnotation, "link")) {
						return true;
					}
		return false;
	}

	private boolean isNodeOrGeneric(EClass eClaz, String view) {
		return getNode(eClaz, view)!=null  || isGeneric(eClaz, view);
	}


	private boolean isGeneric(EClass eClaz, String view) {
		for (EAnnotation eAnnotation : eClaz.getEAnnotations())
			if (eAnnotation.getSource().equals(
					GenericConstants.ANNOT_DIAGRAPH_LITTERAL))
				if (isView(eAnnotation, view))
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

	private String startsKey_nu(EAnnotation anot, String key) {
		for (Map.Entry<String, String> entry : anot.getDetails())
			if (entry.getKey().startsWith(key))
				return entry.getKey();
		return null;
	}

	private String matches(String kw, EAnnotation annot, EReference ref) {
		for (Map.Entry<String, String> entry : annot.getDetails())
			if (entry.getKey().startsWith(kw)) {//FP150512transp
				String k = entry.getKey();// startsKey(annot, kw);
				if (k != null) {
					String equ = k.substring(kw.length());
					//if (LOG && equ.equals("his"))
					//	tb = true;
					if (equ.equals(ref.getName()))
						return equ;
				}
			}
		return "";
	}


	private EReference getPovDiagraphContainer(List<EClass> viu,
			String view, EClass target) {
		for (EClass eclaz : viu) {
			for (EReference eReference : eclaz.getEReferences())
				if (eReference.isContainment()
						&& eReference.getEType() == target) {
					EAnnotation annot = getNode(
							(EClass) eReference.eContainer(), view);
					if (annot != null && isPov(annot))
							return eReference;
				}
		}
		return null;
	}


	private List<EReference> getAllDiagraphContainers(List<EClass> viu,
			String view, EClass target_) { //FP140402
		List<EReference> result = new ArrayList<EReference>();
		for (EClass eclaz : viu) {
			for (EReference eReference : eclaz.getEReferences())
				if (eReference.isContainment()
						&& eReference.getEType() == target_) {
					EAnnotation annot = getNode(
							(EClass) eReference.eContainer(), view);
					if (annot != null) {
						String mat = matches(DiagraphKeywords.KREFERENCE_EQU_, annot, eReference)
								+ matches(DiagraphKeywords.CREFERENCE_EQU, annot, eReference)
								+ matches(DiagraphKeywords.AFFIXED_EQU_, annot, eReference);
						if (!mat.isEmpty() || isPov(annot)) {
							result.add(eReference);
						}
					}
				}
		}
		return result;
	}

	private boolean isPov(EAnnotation annot) {
		for (Map.Entry<String, String> entry : annot.getDetails())
			if (entry.getKey().equals("pov"))
			  return true;
		return false;
	}




	private List<String> getContainers(List<EClass> viu, String pov, EClass link) {
		List<String> result = new ArrayList<String>();
		for (EClass eclaz : viu) {
			for (EReference eReference : eclaz.getEReferences())
				if (eReference.getEType() == link)
					if (getNode((EClass) eReference.eContainer(), pov) != null)
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

	private String[] getLinkArguments(EClass link, String view) {
		String[] result = new String[3];
		EPackage pack = link.getEPackage();
		List<EClass> viu = getClassesInView(pack, view);
		List<String> containers = getContainers(viu, view, link);
		List<String> relations = getNoContRelations(viu, view, link);
		result[0] = (containers.isEmpty() ? "??" : containers.get(0));
		result[1] = (relations.isEmpty() ? "??" : relations.get(0));
		result[2] = (relations.size() > 1 ? relations.get(1) : "??");
		result = finalizeLink(result);
		return result;
	}




	public List<EReference> getAllDiagraphContainers(EClass eclass, String view) {
		List<EReference> containers = getAllDiagraphContainers(
				getClassesInView(eclass.getEPackage(), view), view, eclass);
		return containers;
	}



	private List<EClass> getClassesInView(EPackage pack, String view) {
		List<EClass> result = new ArrayList<EClass>();
		for (EClassifier eClassifier : pack.getEClassifiers()) {
			if (eClassifier instanceof EClass) {
				for (EAnnotation eAnnotation : ((EClass) eClassifier)
						.getEAnnotations())
					if (eAnnotation.getSource().equals(
							GenericConstants.ANNOT_DIAGRAPH_LITTERAL)
							&& isView(eAnnotation, view)) {
						if (!result.contains(eClassifier))
							result.add((EClass) eClassifier);
					}
			}
		}
		return result;
	}


	public EReference getPovDiagraphContainer(EClass eclass, String view) {
		return getPovDiagraphContainer(
				getClassesInView(eclass.getEPackage(), view), view, eclass);
	}





/*

	private void bshSnippet() throws EvalError, FileNotFoundException, IOException{
		Interpreter bsh = new Interpreter();

        // Evaluate statements and expressions
        bsh.eval("foo=Math.sin(0.5)");
        bsh.eval("bar=foo*5; bar=Math.cos(bar);");
        bsh.eval("for(i=0; i<10; i++) { print(\"hello\"); }");
        // same as above using java syntax and apis only
        bsh.eval("for(int i=0; i<10; i++) { System.out.println(\"hello\"); }");

        // Source from files or streams
        bsh.source("myscript.bsh");  // or bsh.eval("source(\"myscript.bsh\")");

        // Use set() and get() to pass objects in and out of variables
        bsh.set( "date", new Date() );
        Date date = (Date)bsh.get( "date" );
        // This would also work:
        date = (Date)bsh.eval( "date" );

        bsh.eval("year = date.getYear()");
        Integer year = (Integer)bsh.get("year");  // primitives use wrappers

        // With Java1.3+ scripts can implement arbitrary interfaces...
        // Script an awt event handler (or source it from a file, more likely)
        bsh.eval( "actionPerformed( e ) { print( e ); }");
        // Get a reference to the script object (implementing the interface)
       // ActionListener scriptedHandler =
        //        (ActionListener)bsh.eval("return (ActionListener)this");
        // Use the scripted event handler normally...
        //new JButton.addActionListener( script );

	}
	*/

	private Map<EClass, List<String>> createLinkAnnotation_snippet(
			Map<EClass, List<String>> annotations, String view, EClass target) {
		List<String> details_ = new ArrayList<String>();
		details_.add("link");
		if (!view.isEmpty() && !view.equals("default"))
			details_.add("view=" + view);
		String[] args = getLinkArguments(target, view);
		details_.add(args[0]);
		details_.add(args[1]);
		details_.add(args[2]);
		Map maps[] = new HashMap[2];
		maps[0] = annotations;
		return createAnnotation_snippet(maps, null, view, 0, target, details_);
	}

	/*
	 * private void f(String view, EClass target) { if (!view.isEmpty()
	 * &&!view.equals("default"));// details_.add("view=" + view); String[] args
	 * = getContainers(target, view); }
	 */

	private Map<EClass, List<String>> createAnnotation_snippet(
			Map<EClass, List<String>> maps[], String srcpov, String pov,
			int povid, EClass target, List<String> details) {
		maps[0].put(target, details);
		// new DiagraphCommandHandler(this, maps).generateAll(srcpov, pov,
		// povid);// controler,editor,
		return maps[0];
	}


	/////////////// _nu //////////////

	private  List<EReference> getDeclaredDiagraphContainers_nu(EClass eclass, String view) {
		// String[] result = new String[3];
		// EPackage pack = target.getEPackage();
		// List<EClass> viu = getView(pack, view);
		List<EReference> containers = getDeclaredDiagraphContainers_nu(
				getClassesInView(eclass.getEPackage(), view), view, eclass);
		// List<String> relations_ = getNoContRelations(viu, view, target);
		// result[0] = (containers.isEmpty() ? "??" : containers.get(0));
		// result[1] = (relations.isEmpty() ? "??" : relations.get(0));
		// result[2] = (relations.size() > 1 ? relations.get(1) : "??");
		return containers;
	}
	private List<EReference> getDeclaredDiagraphContainers_nu(List<EClass> viu,
			String view, EClass target) {
	     return null;
	}







}
