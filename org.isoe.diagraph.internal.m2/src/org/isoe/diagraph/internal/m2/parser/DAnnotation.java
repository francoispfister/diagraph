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
package org.isoe.diagraph.internal.m2.parser;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.isoe.diagraph.diastyle.DBaseStyle;
import org.isoe.diagraph.diastyle.DStyle;
import org.isoe.diagraph.internal.api.IAnnotation;
import org.isoe.diagraph.internal.m2.DiaContainedElement;
import org.isoe.diagraph.internal.m2.DiaGenericElement;
import org.isoe.diagraph.internal.m2.DiaLink;
import org.isoe.diagraph.internal.m2.DiaNode;
import org.isoe.diagraph.internal.m2.DiaReference;
import org.isoe.diagraph.internal.m2.api.IDiaContainment;
import org.isoe.diagraph.internal.m2.api.IDiaEdge;
import org.isoe.diagraph.internal.m2.api.IDiaParser;
import org.isoe.diagraph.lang.DiagraphKeywords;
import org.isoe.diagraph.parser.DiagraphAnnotationParser;
import org.isoe.diagraph.parser.api.IDiagraphNode;
import org.isoe.diagraph.parser.api.IDiagraphNotation;
import org.isoe.diagraph.parser.api.IDiagraphParser;
import org.isoe.diagraph.parser.api.IDiagraphReferenceAssociation;
import org.isoe.diagraph.views.ViewConstants;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.core.EcoreUtilsv0;
import org.isoe.diagraph.runner.IDiagraphRunner;
import org.isoe.fwk.utils.DLog;
import org.isoe.diastyle.lang.StyleConstants;

/**
 * @author pfister TODO heavy refactoring
 *
 */
public class DAnnotation implements IAnnotation, DiagraphKeywords {
	public static String logparse = "";
	private static final boolean LOG = DParams.DAnnotation_LOG;// false;
	private static final boolean LOG_COMPOSITE_LABELS = DParams.COMPOSITE_LABELS_LOG_;
	private static final boolean CONTAINMENT_TRUE = true;
	// //FP130731
	// error10 case
	private static String currentView; // FP121015
	private static boolean promoted = true;
	private static String spaces = "                                                          ";
	private static List<DAnnotation> allannots;
	private String key;
	private String value;
	private String argument_;
	private String[] leftHand = new String[2];
	private String[] rightHand = new String[2];
	private String layer;
	private EAnnotation owner;
	private String argument2;
	// private static List<EReference> allReferences = new
	// ArrayList<EReference>();
	private static String report = "";

	public static String getReport() {
		return report;
	}

	public static void setReport(String report) {
		DAnnotation.report = report;
	}

	public String getArgument_() {
		return argument_;
	}

	public String getLayer() {
		return layer;
	}

	public static boolean isDiagraphView(EAnnotation annot, String view) {
		String viewname = DIAGRAPH_DEFAULT;// FP121010
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

	@Override
	public String getExpression_() {
		return expression;
	}

	public static EAnnotation getDiagraphAnnotation(EClass eclass, String view) {
		EList<EAnnotation> annots = eclass.getEAnnotations();
		EAnnotation result = null;
		int count = 0;
		for (EAnnotation annot : annots) {
			if (isDiagraphView(annot, view)) {
				result = annot;
				count++;
			}
		}
		if (count > 1)
			throw new RuntimeException(
					"more than one Diagraph Annotation for EClass: "
							+ eclass.getName() + " and view " + view);
		return result;
	}

	public String toCsv() {
		String result = key + ";" + (value != null ? value : "") + ";"
				+ (argument_ != null ? argument_ : "");
		if (expression.length() > 0)
			result += expression;
		return result;
	}

	/*********************************** -------------- */

	private static void guessContainment_(IDiagraphParser parser2_,
			DAnnotation annotation, List<DAnnotation> otherAnnotations) {
		EAnnotation owner = annotation.getOwner();
		EClass ownerclass = (EClass) owner.getEModelElement();
		EPackage pack = ownerclass.getEPackage();
		String view = annotation.getLayer();
		String key = annotation.getKey();
		String value = annotation.getValue();

		parser2_.guessContainments(view,(EClass) owner.getEModelElement());

	}

	/*********************************** -------------- */

	private static DGraphElementType guessNodeOrLink(
			List<DAnnotation> otherAnnotations) {
		for (DAnnotation dAnnotation : otherAnnotations) {
			if (dAnnotation.keyFirstSegmentEquals(NODE))
				return DGraphElementType.NODE;
			if (dAnnotation.keyFirstSegmentEquals(POINT_OF_VIEW)) // FP121017
				return DGraphElementType.NODE; // FP130105x
			if (dAnnotation.keyFirstSegmentEquals(OPEN_DIAGRAM)) // FP121017
				return DGraphElementType.NODE;
			if (dAnnotation.keyFirstSegmentEquals(LINK))
				return DGraphElementType.LINK;
		}
		return DGraphElementType.GENERIC;
	}

	private static DGraphElementType dynamicGuessNodeOrLink(DAnnotation annotation) { //FP150508
		String view = annotation.getLayer();
		DGraphElementType result = DGraphElementType.GENERIC;
		if (allSubNodes_(annotation))
			result = DGraphElementType.NODE;
		else if (allSubLinks_(annotation))
			result = DGraphElementType.LINK;
		return result;
	}



	private static List<EClass> getSubNodes(DAnnotation annotation) {
		String view = annotation.getLayer();
		EAnnotation owner = annotation.getOwner();
		//EClass thisclass = (EClass) owner.getEModelElement();
		EClass claz = (EClass) owner.getEModelElement();
		//System.out.println(claz.getName());
		EPackage p = claz.getEPackage();
		List<EClass> classez = new ArrayList<EClass>();
		List<EClassifier> classifs = p.getEClassifiers();
		for (EClassifier eClassifier : classifs) {
			if (eClassifier instanceof EClass) {
				EClass cla = (EClass) eClassifier;
				//System.out.println(claz.getName()+" == "+cla.getName()+" ?");
				if (claz!=cla){
					//System.out.println(cla.getName());
					List<EAnnotation> anotz = cla.getEAnnotations();
					for (EAnnotation anot : anotz) {
						if (isNode(anot, view))
							   classez.add(cla);
					}
				}
			}
		}
		return classez;
	}


	private static List<EClass> getSubLinks(DAnnotation annotation) {
		String view = annotation.getLayer();
		EAnnotation owner = annotation.getOwner();
		//EClass thisclass = (EClass) owner.getEModelElement();
		EClass claz = (EClass) owner.getEModelElement();
		//System.out.println(claz.getName());
		EPackage p = claz.getEPackage();
		List<EClass> classez = new ArrayList<EClass>();
		List<EClassifier> classifs = p.getEClassifiers();
		for (EClassifier eClassifier : classifs) {
			if (eClassifier instanceof EClass) {
				EClass cla = (EClass) eClassifier;
				//System.out.println(claz.getName()+" == "+cla.getName()+" ?");
				if (claz!=cla){
					System.out.println(cla.getName());
					List<EAnnotation> anotz = cla.getEAnnotations();
					for (EAnnotation anot : anotz) {
						if (isLink(anot, view))
							   classez.add(cla);
					}
				}
			}
		}
		return classez;
	}




	private static boolean allSubNodes_(DAnnotation annotation) {
		EAnnotation owner = annotation.getOwner();
		EClass claz = (EClass) owner.getEModelElement();
		List<EClass> nodclasses = getSubNodes(annotation);
		List<EClass> linkclasses = getSubLinks(annotation);
		List<EClass> inhNodclasses = new ArrayList<EClass>();
		for (EClass eClass : nodclasses)
			if (claz.isSuperTypeOf(eClass))
				inhNodclasses.add(eClass);
		return linkclasses.isEmpty() && !inhNodclasses.isEmpty();
	}

	private static boolean allSubLinks_(DAnnotation annotation) {
		EAnnotation owner = annotation.getOwner();
		EClass claz = (EClass) owner.getEModelElement();
		List<EClass> nodclasses = getSubNodes(annotation);
		List<EClass> linkclasses = getSubLinks(annotation);
		List<EClass> inhLinkclasses = new ArrayList<EClass>();
		for (EClass eClass : linkclasses)
			if (claz.isSuperTypeOf(eClass))
				inhLinkclasses.add(eClass);
		return nodclasses.isEmpty() && !inhLinkclasses.isEmpty();
	}

	private static boolean isLink(EAnnotation eAnnotation, String viewid) {
		if (isView(eAnnotation, viewid))
			for (Map.Entry<String, String> entry : eAnnotation.getDetails())
				if (entry.getKey().equals(DiagraphKeywords.LINK))
					return true;
		return false;
	}

	private static boolean isNode(EAnnotation eAnnotation, String viewid) {
		EClass ecl=(EClass) eAnnotation.getEModelElement();
		if (isView(eAnnotation, viewid))
			for (Map.Entry<String, String> entry : eAnnotation.getDetails())
				if (entry.getKey().equals(DiagraphKeywords.NODE))
					return true;
		return false;
	}

	private static boolean isDefaultView(EAnnotation anot) {
		for (Map.Entry<String, String> entry : anot.getDetails())
			if (entry.getKey().startsWith(DiagraphKeywords.VIEW_EQU))
				return false;
		return true;
	}

	private static boolean isView(EAnnotation anot, String viewid) { // FP131208

		if (!anot.getSource().equals(DiagraphKeywords.DIAGRAPH_LITTERAL))
			return false;
		if (viewid == null || viewid.equals(DiagraphKeywords.DIAGRAPH_DEFAULT)
				|| viewid.isEmpty())
			return isDefaultView(anot);
		for (Map.Entry<String, String> entry : anot.getDetails())
			if (entry.getKey().equals(DiagraphKeywords.VIEW_EQU + viewid))
				return true;
		return false;
	}

	private static IDiaContainment parseHostContainment(
			List<DAnnotation> annotations, DAnnotation annotation,
			EClass element, IDiaParser parser) {
		IDiaContainment result = diagramGetNamedContainment(parser, element,
				annotation);
		return result;
	}

	private static IDiaContainment parsePReference_(
			// FP130319ppp //FP151318t
			List<DAnnotation> otherAnnotations, DAnnotation annotation,
			EClass element, IDiaParser parser) {
		IDiaContainment justadded = null;
		String[] args = annotation.parse();
		DGraphElementType nodtype = guessNodeOrLink(otherAnnotations);
		if (nodtype != DGraphElementType.NODE)
			throw new RuntimeException(
					"parsePReference: node is not a DNode or an Unknown : "
							+ annotation.toString());
		if (LOG)
			if (args[0] != null) {
				clog("parsing 1, name=" + args[0] + " parsePReference");
				logparse += "parsing 1, name=" + args[0] + " parsePReference\n"; // FP120123
			}
		boolean not_propagated = false;

		EReference erf = (EReference) element.getEStructuralFeature(annotation
				.getValue());// FP150505
		DiaNode nod = parser.getNodeWithPReference(element,
				(EClass) erf.getEType(), annotation.getValue(), args[0],
				not_propagated); // , FIGURE_EMBEDDED_ARGUMENT.equals(args[1]),
		// false
		// FP150424b
//FP150516
		if ((!nod.isCanvas()) && nod.getContainments().size() == 0) // FP140701cn
																	// //
																	// FP130107b
			throw new RuntimeException("(1)syntax error on " + nod
					+ " check if there is  a [node] annotation !!!!");
		justadded = nod.getContainments().get(nod.getContainments().size() - 1);
		return justadded;
	}

	private static DiaLink parseLnkAssocs(IDiagraphRunner runner_,
			List<DAnnotation> annotations, DAnnotation annotation,
			EClass element, IDiaParser parser) {
		return parser.getLinkWithtarget(element, annotation.getValue(), null);
	}

	private static DiaLink parseLnk_(List<DAnnotation> otherAnnotations,
			DAnnotation annotation, EClass element, IDiaParser parser) {
		if (DParams.LNK_OBSOLETE)
			throw new RuntimeException("lnk annotation obsolete for " + element.getName()+" in view "+getCurrent_View()); // FP140503

		String[] args = annotation.parse();
		DGraphElementType nodtype = guessNodeOrLink(otherAnnotations);
		DiaLink justAdded = null;
		if (!(nodtype == DGraphElementType.NODE))
			throw new RuntimeException("parseLink: node is not a DNode: "
					+ annotation.toString());
		if (nodtype == DGraphElementType.NODE) {
			if (args[0] != null) {
				// label_ = args[0];

				if (LOG) {
					clog("parsing 2, " + " parseLnk\n"); // FP120123
					logparse += "parsing 2, " + " parseLnk\n"; // FP120123
				}
			}

			DiaNode nod = parser.getNodeWithLink(element,
					annotation.getValue(), null);

			if (nod.getDiaLinks().size() > 0)
				justAdded = nod.getDiaLinks().get(nod.getDiaLinks().size() - 1);

			// getContainments().get(nod.getContainments().size()
			// -
			// 1);
		} else if (nodtype == DGraphElementType.GENERIC) {
			throw new RuntimeException(
					"parseLink: nodtype == DGraphElement.UNKNOWN - should not happen!");
		}
		return justAdded;
	}

	private static IDiaContainment diagramGetNamedContainment(
			IDiaParser parser, EClass element, DAnnotation annotation) {
		// boolean figEmbedded = true;
		boolean propagated = false;
		/*
		 * if (annotation.argument != null &&
		 * FIGURE_OWN_ARGUMENT.equals(annotation.argument)) figEmbedded = false;
		 */
		IDiaContainment comp = parser.getNamedContainment(element,
				annotation.getValue(), propagated);
		return comp;
	}

	private static DiaContainedElement parseLabel(IDiagraphRunner runner_,
			List<DAnnotation> annotations, DAnnotation annotation,
			EModelElement element, IDiaParser parser) {
		// EClass claz = (EClass) element;
		if (annotation.argument_ != null)
			if (LOG) {
				System.out.print("parsing 3, name=" + annotation.argument_
						+ " parseLabel\n"); // FP120123
				logparse += "parsing 3, name=" + annotation.argument_
						+ " parseLabel\n"; // FP120123
			}

		DGraphElementType nodtype = guessNodeOrLink(annotations);
		if (nodtype == DGraphElementType.GENERIC)
			nodtype = guessNodeOrLink(runner_, element, parser);
		boolean old = true;
		if (old)
			return parser.getLabelledElement((EClass) element,
					annotation.getValue(), annotation.argument_);
		else
			return parser.getLabelledElement((EClass) element, nodtype,
					promoted, annotation.getValue(), annotation.argument_);
	}

	private static DiaContainedElement parseLabels(IDiagraphRunner runner_,
			List<DAnnotation> annotations, DAnnotation annotation,
			EModelElement element, IDiaParser parser) {// FP140204x
		if (annotation.value != null)
			if (LOG || LOG_COMPOSITE_LABELS) {
				cloglabs("parsing 3, value=" + annotation.value);

				logparse += "parsing 3, value=" + annotation.value
						+ " parseLabels\n"; // FP120123
			}

		DGraphElementType nodtype = guessNodeOrLink(annotations);
		if (nodtype == DGraphElementType.GENERIC)
			nodtype = guessNodeOrLink(runner_, element, parser);
		// boolean old = true;

		// labels=quant=articleAid
		String val = annotation.getValue();
		if (LOG || LOG_COMPOSITE_LABELS) {

			cloglabs("parseLabels " + val);
		}
		// throw new RuntimeException("todo parseLabels");//quant:articleAid

		// if (old)
		return parser.getLabelledElements((EClass) element,
				annotation.getValue(), annotation.argument_);
		// else
		// return parser.getLabelledElement((EClass) element, nodtype,
		// promoted, annotation.getValue(), annotation.argument);*/
	}

	private String[] parse() {
		String[] args = new String[2];
		if (argument_ != null && argument_.contains("/"))
			args = argument_.split("/");
		else {
			args[0] = argument_;
			args[1] = "figureEmbedded"; // FP120619
		}
		return args;
	}

	/*
	 * private static IDiaContainment parseCReference_old( List<DAnnotation>
	 * otherAnnotations, DAnnotation annotation, EClass element, IDiaParser
	 * parser) {  = false; if (((EClass)
	 * element).getName().equals("Foo")) tb = true;
	 * parser.getParser2().guessContainments((EClass)
	 * (annotation.getOwner()).getEModelElement()); IDiaContainment justadded =
	 * null; String[] args = annotation.parse(); DGraphElementType nodtype =
	 * guessNodeOrLink(otherAnnotations); if (nodtype != DGraphElementType.NODE)
	 * throw new RuntimeException(
	 * "parseCReference: node is not a DNode or an Unknown : " +
	 * annotation.toString()); if (LOG) if (args[0] != null) {
	 * clog("parsing 1, name=" + args[0] + " parseCReference"); logparse +=
	 * "parsing 1, name=" + args[0] + " parseCReference\n"; // FP120123 }
	 * boolean propagated_false=false; DiaNode nod =
	 * parser.getNodeWithCReference(element, annotation.getValue(), args[0],
	 * propagated_false); // , FIGURE_EMBEDDED_ARGUMENT.equals(args[1]), //
	 * false
	 *
	 * if ((!nod.isCanvas()) && nod.getContainments().size() == 0) //FP140701cn
	 * // FP130107b throw new RuntimeException("(2)syntax error on " + nod +
	 * " check if there is  a [node] annotation !!!!"); justadded =
	 * nod.getContainments().get( nod.getContainments().size() - 1);
	 *
	 * return justadded; }
	 */

	private static List<IDiaContainment> parseCReference_(
			// FP150331a305 //FP151318t
			List<DAnnotation> otherAnnotations, DAnnotation annotation,
			EClass element, IDiaParser parser) {
		List<IDiaContainment> result = new ArrayList<IDiaContainment>();
		IDiaContainment justadded = null;
		String[] args = annotation.parse();
		DGraphElementType nodtype = guessNodeOrLink(otherAnnotations);
		String view = annotation.getLayer();
		List<IDiagraphReferenceAssociation> cts = parser.getParser2()
				.guessContainments(view,
						(EClass) (annotation.getOwner()).getEModelElement());
		if (LOG)
		    for (IDiagraphReferenceAssociation ct : cts)
				clog(ct.toLogCompl()); // FP150329a109


		if (!(nodtype == DGraphElementType.NODE || nodtype == DGraphElementType.GENERIC))
			throw new RuntimeException(
					"parseCReference: node is not a DNode or an Unknown : "
							+ annotation.toString());

		if (nodtype == DGraphElementType.NODE) {
			String label = args[0];
			if (label != null)
				if (LOG) {
					clog("parsing 4, label=" + label + " parseCReference\n"); // FP120123
					logparse += "parsing 4, label=" + label
							+ " parseCReference\n"; // FP120123
				}
			boolean propagated_false = false;
			EReference tref = (EReference) element
					.getEStructuralFeature(annotation.getValue());
			//List<EClass> cclasses = parser.getConcreteEClasses(tref, true);// FP150330a301
			//List<EClass> cclasses = parser.getSubClasses(tref, true); //FP150517// FP150330a301


			List<EClass> children = new ArrayList<EClass>();
			if (!((EClass)tref.getEType()).isAbstract())
				children.add((EClass) tref.getEType());
            for (EClass eClass : parser.getSubClasses(tref, true))
				children.add(eClass); //FP150517


			if (children.isEmpty())
				throw new RuntimeException("should not happen while kref");
			if (DParams.Parser_15_LOG)
				clog("concrete classes for " + tref.getName());
			for (EClass eClass : children) {
				if (DParams.Parser_15_LOG)
					clog(eClass.getName());

				DiaNode nod = parser.getNodeWithCReference(element,
						annotation.getValue(), eClass,// label,
						false, propagated_false);
				justadded = nod.getContainments().get(
						nod.getContainments().size() - 1);
				result.add(justadded);
			}
		} else if (nodtype == DGraphElementType.GENERIC) {
			throw new RuntimeException(
					"("
							+ element.getName()
							+ ") parseCReference: nodtype == DGraphElement.UNKNOWN - should not happen!");
		}
		return result;
	}


	private static List<IDiaContainment> parseKReference_(
			boolean mustThrowException, // FP150331a305 //FP151318t
			List<DAnnotation> otherAnnotations, DAnnotation annotation,
			EClass element, IDiaParser parser) {
		List<IDiaContainment> result = new ArrayList<IDiaContainment>();
		IDiaContainment justadded = null;
		String[] args = annotation.parse();
		DGraphElementType nodtype = guessNodeOrLink(otherAnnotations);
		String view = annotation.getLayer();
		List<IDiagraphReferenceAssociation> cts = parser.getParser2()//FP150512transp
				.guessContainments(view , (EClass) (annotation.getOwner()).getEModelElement());
		if (LOG)
		   for (IDiagraphReferenceAssociation ct : cts)
				clog(ct.toLogCompl()); // FP150329a109

		if (!(nodtype == DGraphElementType.NODE || nodtype == DGraphElementType.GENERIC))
			throw new RuntimeException(
					"parseKReference: node is not a DNode or an Unknown : "
							+ annotation.toString());

		if (nodtype == DGraphElementType.NODE) {
			String label = args[0];
			if (label != null)
				if (LOG) {
					clog("parsing 4, label=" + label + " parseKReference\n"); // FP120123
					logparse += "parsing 4, label=" + label
							+ " parseKReference\n"; // FP120123
				}
			EReference tref = (EReference) element
					.getEStructuralFeature(annotation.getValue());
			List<EClass> children = new ArrayList<EClass>();

			if (!((EClass)tref.getEType()).isAbstract())
				children.add((EClass) tref.getEType());
            for (EClass eClass : parser.getSubClasses(tref, true))
				children.add(eClass); //FP150517


			if (children.isEmpty())
				throw new RuntimeException("should not happen while kref");
			if (LOG)
			for (EClass eClass : children) {
				clog(""+eClass.getName());
			}
			if (DParams.Parser_15_LOG){
				String log ="concrete classes for " + tref.getName()+" ";
			for (EClass eClass : children)
				log+=eClass.getName()+" ,";
			 clog(log);
			}


			EClass reftype = (EClass) tref.getEType();

			for (EClass eClass : children) {
				if (DParams.Parser_15_LOG)
					clog(eClass.getName());
				boolean propagated = false; //FP150516z
				if (eClass != reftype ){
					if (!reftype.isSuperTypeOf(eClass))
							throw new RuntimeException("should not happen in parseKReference");
					propagated = true;
				}

				DiaNode nod = parser.getNodeWithKReference(element,
						annotation.getValue(),
						annotation.getArgument2(),
						//annotation.getArgument_(),
						label, eClass,
						FIGURE_EMBEDDED_ARGUMENT.equals(args[1]),
						propagated); //FP150516

				justadded = nod.getContainments().get(
						nod.getContainments().size() - 1);
				if (DParams.Parser_15_LOG)
					clog("added "+nod.getName()+" "+justadded.getContainmentName_()+" "+(justadded.getTargetNode()==null?" target=null":("target=")+justadded.getTargetNode().getName()));


				result.add(justadded);
			}
		} else if (nodtype == DGraphElementType.GENERIC) {
			if (mustThrowException) // FP150508
				throw new RuntimeException(
						"("
								+ element.getName()
								+ ") parseKReference: nodtype == DGraphElement.UNKNOWN - should not happen!");
		}
		return result;
	}

	private String getArgument2() {
		return argument2;
	}

	private static DiaContainedElement parseContainment(
			List<DAnnotation> otherAnnotations, DAnnotation annotation,
			EClass element, IDiaParser parser) {
		DGraphElementType nodtype = guessNodeOrLink(otherAnnotations);
		DiaContainedElement result = parser.getContainedElement(element,
				annotation.getValue()); // B, A.bs
		return result;
	}




	private static DiaLink parseLink(DAnnotation annotation,
			EClass element, IDiaParser parser) { //FP150523
		// int ph = runner.getPhase();
		String[] labelledLink_nu = new String[2];
		String linkname = null;
		String linkTarget = null;
		if (annotation.getValue() != null && annotation.getValue().length() > 0)
			linkTarget  = annotation.getValue();
		else if (annotation.getKey().startsWith(LINK + ":")) { // obsolete
			labelledLink_nu = annotation.getKey().split(":");
			linkname = labelledLink_nu[1];
		} else
			labelledLink_nu[0] = (annotation.getKey());
		return parser.getNamedLink(element, linkTarget, linkname);
	}

	private static String parseNavigation(IDiagraphRunner runner_,
			List<DAnnotation> annotations, EClass element) {
		try {
			for (DAnnotation dAnnotation : annotations) {

				if (dAnnotation.key.startsWith(POINT_OF_VIEW_PREFIX)) // FP130105
					throw new RuntimeException(
							"obsolete key: pov:[PointOfViewName], change to nav:[PointOfViewName] in multiple views !!!"); // FP130105x

				if (dAnnotation.keyFirstSegmentEquals(OPEN_DIAGRAM)) { // FP121017
					String k = dAnnotation.getKey();
					if (!k.startsWith("nav:vw"))
						runner_.cwarn("verify view name " + k + " on element "
								+ element.getName());
					if (LOG)
						clog("parse Diagram Navigation: " + k);
					String[] odbs = k.split(":");
					String di = odbs[1];
					if (di.contains("."))
						throw new RuntimeException(
								"should not happen (in parseNavigation)");
					String result = di + VIEW_SEPARATOR_1 + ROOT_NAME;

					return result;
				}
			}
		} catch (Exception e) {
			String err = e.getMessage(); // FP130105x
			if (err == null || err.isEmpty())
				err = " use a dot and not an equal ";
			throw new RuntimeException("error while parsing navigation for "
					+ element.getName() + " " + err);// " use a dot and not an equal ???");
		}
		return null;
	}

	private static DiaNode parseNode(IDiagraphRunner runner_,
			List<DAnnotation> otherAnnotations, DAnnotation annotation,
			EClass element, IDiaParser parser) {
		int ph = runner_.getPhase();
		boolean isCurrentCanvas = false; // FP130610n
		if (parser.getPointOfView() != null)
			isCurrentCanvas = element.getName().equals(
					parser.getPointOfView().getElementName());

		if ((LOG)) {// LOG_GEN ||
			if (isCurrentCanvas)
				clog("DBNT-DAPND-" + ph + " - currentcanvas- "); // FP120926
			else
				clog("DBNT-DAPND-" + ph + " - NOT currentcanvas- "); // FP120926
		}
		boolean pointOfView = annotation.key.equals(POINT_OF_VIEW);
		// boolean notvalid = annotation.key_.startsWith(POINT_OF_VIEW_PREFIX);
		if (pointOfView) {
			annotation.setValue(null);
			if (!isCurrentCanvas)
				if (LOG)
					clog("canvas but not current: " + element.getName());
		}
		String[] labelledNode = new String[2];
		String obsoletenodename = null;
		if (annotation.getValue() != null && annotation.getValue().length() > 0)
			obsoletenodename = annotation.getValue();
		else if (annotation.getKey().startsWith(NODE + ":")) { // FP121007
			labelledNode = annotation.getKey().split(":"); // FP140429 tosee
			obsoletenodename = labelledNode[1];
		} else
			labelledNode[0] = (annotation.getKey());

		DiaNode result = null;
		if (isCurrentCanvas)
			result = parser.getCanvas(element); // FP140521 obsolete
		else
			result = parser.getNamedNode(element, null);// annotation_.value);//,canvasAnnotation);
															// //FP120926voir
		String navigation = parseNavigation(runner_, otherAnnotations, element); // FP121019xxx

		if (navigation != null)
			result.setNavigation(navigation); // FP121124k

		EReference xcontref = parseExtraContainmentReference(element,
				parser.getView());// FP131009 TODO validate this case
		if (xcontref != null) {
			result.setDeclaredContainmentReference(xcontref); // FP131020 not
																// used at the
																// moment
																// //FP131009x//in
																// case of
																// multiple
																// containments
		}

		return result;
	}

	private void setValue(String value) {
		this.value = value;
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

	private static EReference parseExtraContainmentReference(EClass ecl, // FP131008
			String view) {
		EAnnotation annot = getDiagraphAnnotation(ecl, view);
		for (Map.Entry<String, String> entry : annot.getDetails()) {
			if (entry.getKey().startsWith(
					org.isoe.diagraph.lang.DiagraphKeywords.XCONTAINMENT_EQU)) { // FP131009//XCONTAINMENT
				String[] pars = entry.getKey().split("=");
				String contname = pars[1];
				pars = contname.split("\\.");
				return (EReference) ((EClass) ecl.getEPackage().getEClassifier(
						pars[0])).getEStructuralFeature(pars[1]);
			}
		}
		return null;
	}

	private static DBaseStyle findStyle(IDiaParser parser, String stylname) {
		DStyle styleSheet = parser.getStyleSheet(); // FP120715
		EList<DBaseStyle> dGraphStyles = styleSheet.getStyles();
		for (DBaseStyle dGraphStyle : dGraphStyles) {
			if (dGraphStyle.getName().equals(stylname)) {
				return dGraphStyle;
			}
		}
		return null;
	}

	private static DiaReference parseReference(
			List<DAnnotation> otherAnnotations, DAnnotation annotation,
			EClass element, IDiaParser parser) {
		DGraphElementType nodtype = guessNodeOrLink(otherAnnotations);
		if (nodtype == DGraphElementType.NODE) {
			DiaNode nod = parser.getNodeWithReference_(element,
					annotation.getValue(), annotation.argument_);
			return nod.getReferences().get(nod.getReferences().size() - 1);
		} else if (nodtype == DGraphElementType.GENERIC) {
			DiaReference justadded = null;
			DiaGenericElement gel = parser.getGenericElementWithReference(
					element, annotation.getValue(), annotation.argument_);
			justadded = gel.getReferences().get(gel.getReferences().size() - 1);
			return justadded;
		} else {
			clog("TODO !!! parseReference ");
			return null;
		}
	}

	private static IDiaContainment parseCompartment(
			List<DAnnotation> otherAnnotations, DAnnotation annotation,
			EClass element, IDiaParser parser) {
		// boolean figureEmbedded = DEFAULT_COMPARTM_OPTION_FIGURE_EMBEDDED;
		boolean propagated = false;
		/*
		 * if (annotation.argument != null &&
		 * !FIGURE_EMBEDDED_ARGUMENT.equals(annotation.argument)) figureEmbedded
		 * = !DEFAULT_COMPARTM_OPTION_FIGURE_EMBEDDED;
		 */
		IDiaContainment result = parser.getContainerContainment(element,
				annotation.getValue(), propagated);
		return result;
	}

	private static void logAllInheritance_not_used(EClass eclass, EClass upper) {
		if (eclass.getEAllSuperTypes() != null
				&& eclass.getEAllSuperTypes().contains(upper)) {
			clog(((EClass) upper).getName() + "<-o-" + eclass.getName());
		}
	}

	/*
	 * annotation yet exists
	 */
	public static EAnnotation createEntry(EClass eClass, String key,
			String value, String view) {
		if (!isCurrentValidToken(key))
			throw new RuntimeException("createAnnotation:  not valid token");
		return EcoreUtilsv0.createEntry(eClass,
				DiagraphKeywords.ANNOT_DIAGRAPH_LITTERAL, key + "=" + value,
				"", view); // FP130723
	}

	/*
	 * annotation not yet exists
	 */
	public static EAnnotation createAnnotationAndDetail(EClass eClass,
			String key, String value, String view) {
		if (!isCurrentValidToken(key))
			throw new RuntimeException("createAnnotation:  not valid token");
		return EcoreUtilsv0.createAnnotationAndDetail(eClass,
				ANNOT_DIAGRAPH_LITTERAL, key + "=" + value, "", VIEW, view);// FP130723
	}

	public static void removeDetail(IDiagraphRunner runner_, EClass eclass,
			String key) {
		if (!isCurrentValidToken(key))
			throw new RuntimeException("removeEntry: not valid token");
		EAnnotation ean = getInViewDiagraphAnnotation(eclass);
		Map.Entry<String, String> toremove = null;
		for (Map.Entry<String, String> entry : ean.getDetails()) {
			DAnnotation dummy = createAnnotation(runner_, entry, ean);
			if (dummy != null && dummy.getKey().equals(key)) { // FP120111
				toremove = entry;
				break;
			}
		}
		if (toremove == null)
			throw new RuntimeException("removeDetail:  entry " + key
					+ " should exist in " + eclass.getName());
		ean.getDetails().remove(toremove); // FP120111
	}

	public static void removeDetail(IDiagraphRunner runner_, EClass eclass,
			String key, String value) {// REFERENCE
		if (!isCurrentValidToken(key))
			throw new RuntimeException("removeEntry: not valid token");
		EAnnotation ean = getInViewDiagraphAnnotation(eclass);
		Map.Entry<String, String> toremove = null;
		for (Map.Entry<String, String> entry : ean.getDetails()) {
			DAnnotation dummy = createAnnotation(runner_, entry, ean);
			if (dummy != null)
				if (dummy.getKey().equals(key)
						&& dummy.getValue().equals(value)) { // FP120111
					toremove = entry;
					break;
				}
		}
		if (toremove == null)
			throw new RuntimeException("removeDetail:  entry " + key + "="
					+ value + " should exist in " + eclass.getName());
		ean.getDetails().remove(toremove); // FP120111
	}

	public static boolean isDiaNode(IDiagraphRunner runner_, EClass eclass) {

		EAnnotation ean = getInViewDiagraphAnnotation(eclass);
		List<DAnnotation> anns = createAnnotations(runner_, ean);
		for (DAnnotation anot : anns)
			if (anot.getKey().equals(NODE))
				return true;
		for (DAnnotation anot : anns)
			if (anot.getKey().startsWith(NODE + ":")) // FP121008
				return true;
		return false;
	}

	public static void checkReferenceInDiaNode(IDiagraphRunner runner_,
			EClass eclass) {
		EAnnotation ean = getInViewDiagraphAnnotation(eclass);
		List<DAnnotation> anns = createAnnotations(runner_, ean);
		for (DAnnotation anot : anns) {
			if (anot.getKey().equals(REFERENCE)) {
				if (!isDiaNode(runner_, eclass))
					throw new RuntimeException(eclass.getName()
							+ " should be a DiaNode (has a reference:"
							+ anot.getValue() + ")");
				return;
			}
		}
	}

	public static DAnnotation findOrCreateUniqueAnnotation(
			IDiagraphRunner runner, EClass eclass, String key) {
		List<DAnnotation> r = findOrCreateAnnotations(runner, eclass, key);
		if (r.size() > 1)
			throw new RuntimeException("should have one DAnnotation for "
					+ eclass.getName() + " " + key);
		if (r.size() == 1)
			return r.get(0);
		else
			return null;
	}

	private static DAnnotation create(IDiagraphRunner runner, String key,
			EAnnotation ean, Map.Entry<String, String> entry) {
		DAnnotation result = createExpression(runner, key, ean, entry);
		if (LOG){
			if (result != null){
				EClass owner=(EClass)ean.eContainer();


				clog("DANPRS (" + result.getLayer() + ")(" + owner.getName()+"."+key + ") "
						+ result.expression);
			}
		}
		if (result != null && result.getLeftHands().equals(key))
			return result;
		else
			return null;
	}

	private static DAnnotation createExpression(IDiagraphRunner runner,
			String key, EAnnotation ean, Map.Entry<String, String> entry) {
		DAnnotation an = createAnnotation(runner, entry, ean);
		if (an != null) {
			if (an.getKey().equals(key))
				an.setLeftHand(0, key);
			else if (an.getKey().contains(key + ".")) {
				String[] args = an.getKey().split("\\.");
				if (args.length != 2)
					throw new RuntimeException("should have 2 arguments !!!");
				an.setLeftHand(0, args[0]);
				an.setLeftHand(1, args[1]);
			} else
				return null;
			if (an.getValue() != null) {
				if (an.getValue().contains(".")) {
					String[] args = an.getValue().split("\\.");
					if (args.length != 2)
						throw new RuntimeException(
								"should have 2 arguments !!!");
					an.setRightHand(0, args[0]);
					an.setRightHand(1, args[1]);
				} else
					an.setRightHand(0, an.getValue());
			}
		}
		an.toExpression();
		return an;
	}

	public static EAnnotation getInViewDiagraphAnnotation(EClass eclass) {
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

	public static List<DAnnotation> findOrCreateAnnotations(
			IDiagraphRunner runner, EClass eclass, String key) {// , boolean
																// old) {
		if (!isValidToken(key))
			throw new RuntimeException("checkDetail: not valid token");
		List<DAnnotation> result = new ArrayList<DAnnotation>();
		if (isDiaStyleToken(key))
			return result;
		EAnnotation ean = getInViewDiagraphAnnotation(eclass);
		if (ean == null) {
			if (LOG)
				clog(eclass.getName() + " - " + key
						+ " belongs to antoher layer");
		} else
			for (Map.Entry<String, String> entry : ean.getDetails()) {
				if (!isDiaStyleToken(entry.getKey())) {// FP120717
					DAnnotation created = create(runner, key, ean, entry);
					if (created != null)
						result.add(created); // FP121015-200
				}
			}
		return result;
	}

	public String getRightHand() {
		return rightHand[0];
	}

	public String getLeftHand() {
		return leftHand[0];
	}

	@Override
	public String getLeftHands() {
		String result = leftHand[0];
		if (leftHand[1] != null)
			result += "." + leftHand[1];
		return result;
	}

	@Override
	public String getRightHands() {
		String result = rightHand[0];
		if (rightHand[1] != null)
			result += "." + rightHand[1];
		return result;
	}

	public String getLeftHand(int i) {
		return leftHand[i];
	}

	public String getRightHand(int i) {
		return rightHand[i];
	}

	private void setLeftHand(int i, String value) {
		leftHand[i] = value;
	}

	private void setRightHand(int i, String value) {
		rightHand[i] = value;
	}

	public static DAnnotation findOrCreateAnnotation(IDiagraphRunner runner,
			EClass eclass, String key, String value) {
		if (!isCurrentValidToken(key))
			throw new RuntimeException("checkDetail: not valid token");
		EAnnotation ean = getInViewDiagraphAnnotation(eclass);
		for (Map.Entry<String, String> entry : ean.getDetails()) {
			DAnnotation an = createAnnotation(runner, entry, ean);
			if (an != null)
				if (an.getKey().equals(key)
						&& ((an.getValue() != null && an.getValue().equals(
								value)) || (an.getValue() != null && (value == null || value
								.isEmpty())))) {
					if (an.getValue() == null)
						throw new RuntimeException("test this case !!!!");
					return an;
				}
		}
		return null;
	}

	private static List<IAnnotation> getAnnotations(IDiagraphRunner runner,
			EClass eclass, String[] annots) {
		List<IAnnotation> result = new ArrayList<IAnnotation>();
		EAnnotation ean = getInViewDiagraphAnnotation(eclass);
		if (ean == null) {
			if (LOG)
				clog(eclass.getName() + " is not a part of the view: "
						+ currentView);
		} else
			for (String key : annots) {
				List<DAnnotation> dAnnotations = findOrCreateAnnotations(
						runner, eclass, key);// , annots == OLD_TOKENS);
				if (dAnnotations != null && !dAnnotations.isEmpty())
					result.addAll(dAnnotations);
			}
		return result;
	}

	public static List<IAnnotation> findAnnotations(IDiagraphRunner runner,
			EClass eclass, String[] keys) {
		String view = DIAGRAPH_DEFAULT;
		List<IAnnotation> result = new ArrayList<IAnnotation>();
		EAnnotation ean = getDiagraphAnnotation(eclass, view);
		if (ean != null) {
			result = getAnnotations(runner, eclass, keys);
			if (false) {
				if (result.size() > 0) {
					String anotlog = "[annotations for ";
					anotlog += eclass.getName() + "\n";
					for (IAnnotation an : result)
						if (an != null)
							anotlog += an.getExpression_() + "\n";
					anotlog += "]";
					clog(anotlog);
				}
			}
		}
		return result;
	}

	public static void checkDetail(IDiagraphRunner runner, EClass eclass,
			String key, String value) {
		if (findOrCreateAnnotation(runner, eclass, key, value) == null)
			throw new RuntimeException("checkDetail:  entry " + key + "="
					+ value + " should exist in " + eclass.getName());
	}

	public static boolean isNodeOrLink(IDiagraphRunner runner, EClass eclass) {
		// EAnnotation an = eclass.getEAnnotation(getAnnotationLitteral());
		EAnnotation an = getInViewDiagraphAnnotation(eclass);
		if (an != null) {
			List<DAnnotation> anns = createAnnotations(runner, an);
			for (DAnnotation dAnnotation : anns)
				if (NODE.equals(dAnnotation.getKey())
						|| LINK.equals(dAnnotation.getKey()))
					return true;
		}
		return false;
	}

	public static boolean isNode1(IDiagraphRunner runner, EClass eclass) {
		if (eclass == null)
			return false;
		// EAnnotation an = eclass.getEAnnotation(getAnnotationLitteral());
		EAnnotation an = getInViewDiagraphAnnotation(eclass);
		if (an != null) {
			List<DAnnotation> temp = createAnnotations(runner, an);
			for (DAnnotation dAnnotation : temp)
				if (NODE.equals(dAnnotation.getKey()))
					return true;
		}
		return false;
	}

	public static boolean isReference(IDiagraphRunner runner,
			EReference reference) {
		EClass eclass = (EClass) reference.eContainer();
		return hasEntry(runner, eclass, REFERENCE, reference.getName());
	}

	public static boolean isContainer(IDiagraphRunner runner, EClass eclass) {
		EAnnotation an = getInViewDiagraphAnnotation(eclass);
		if (an != null) {
			List<DAnnotation> anns = createAnnotations(runner, an);
			for (DAnnotation dAnnotation : anns){
				if (PREFERENCE_OLD_NU.equals(dAnnotation.getKey()))
				  throw new RuntimeException("keyword "+PREFERENCE_OLD_NU+" is obsolete");
				if (KREFERENCE_.equals(dAnnotation.getKey())
						|| CREFERENCE_.equals(dAnnotation.getKey())
						|| AFFIXED_.equals(dAnnotation.getKey()) // FP130319ppp
				) return true;
			}
		}
		return false;
	}

	public static boolean isLinkDefinedInSourceNode_(IDiagraphRunner runner,
			EClass targetClass) {
		EPackage pak = targetClass.getEPackage();
		for (TreeIterator<EObject> it2 = pak.eAllContents(); it2.hasNext();) {
			EObject srco = it2.next();
			if (srco instanceof EClass && isNode1(runner, (EClass) srco)) {
				for (EReference srcref : ((EClass) srco).getEAllReferences()) {
					if (srcref.isContainment()
							&& srcref.getEReferenceType() == targetClass) {
						EAnnotation an = getInViewDiagraphAnnotation(((EClass) srco));
						if (an != null)
							for (DAnnotation dAnnotation : DAnnotation
									.createAnnotations(runner, an))
								if (LNK.equals(dAnnotation.getKey())
										&& dAnnotation.getValue().equals(
												srcref.getName())) {
									if (DParams.LNK_OBSOLETE)
										runner.cerror("lnk annotation deprecated for "
												+ ((EClass) srco).getName()); // FP140503
									return true;
								}
					}
				}
			}
		}
		return false;
	}

	public static EAnnotation addSourceAnnotation(EClass eclass) {
		EAnnotation tn = getInViewDiagraphAnnotation(eclass);
		if (tn == null) {
			EAnnotation an = EcoreFactory.eINSTANCE.createEAnnotation();
			an.setSource(DiagraphKeywords.ANNOT_DIAGRAPH_LITTERAL);
			an.setEModelElement(eclass);
			an.getDetails().put(VIEW_EQ + currentView,
					DParams.NULL_ANNOTATION_VALUE);
		}
		return tn;
	}

	public static boolean hasReference(IDiagraphRunner runner,
			EClass sourceClass, EReference ref) {
		return hasEntry(runner, sourceClass, REFERENCE, ref.getName());
	}

	public static void log(IDiagraphRunner runner, EClass claz) {
		for (EAnnotation lanot : claz.getEAnnotations()) {
			if (LOG)
				clog(lanot.getSource());
			if (lanot.getSource().equals(
					DiagraphKeywords.ANNOT_DIAGRAPH_LITTERAL)) {
				if (parseView(lanot).equals(currentView)) {
					for (Map.Entry<String, String> entry : lanot.getDetails()) {
						DAnnotation dummy = createAnnotation(runner, entry,
								lanot);
						if (dummy != null)
							if (LOG)
								System.out.print(dummy.toString());
					}
				}
			}

		}
	}

	private static void checkDirectInheritance(EClass eclass, EClass upper,
			List<EModelElement> modelElements, String view, int depth) {
		if (eclass.getESuperTypes() != null
				&& eclass.getESuperTypes().contains(upper)) {
			if (LOG) {
				String sp = "";
				if (depth > 0)
					sp = spaces.substring(0, depth * 3);
				clog(sp + ((EClass) upper).getName() + "<-*-"
						+ eclass.getName());
			}
			EAnnotation eclassgmfannot = getInViewDiagraphAnnotation(eclass);
			EAnnotation uclassgmfannot = getInViewDiagraphAnnotation((EClass) upper);
			if (eclassgmfannot != null && uclassgmfannot == null)
				throw new RuntimeException(
						"checkDirectInheritance: Super Node is not tagged: "
								+ ((EClass) upper).getName() + "<-*-"
								+ eclass.getName() + " for view " + view); // FP131019
			checkInheritanceTree(eclass, modelElements, view, ++depth);
		}
	}

	private static void checkInheritanceTree(EClass supertype,
			List<EModelElement> modelElements, String view, int depth) {
		for (EModelElement eModelElement : modelElements)
			if (eModelElement instanceof EClass)
				checkDirectInheritance((EClass) eModelElement, supertype,
						modelElements, view, depth);
	}

	static void checkInheritance(List<EModelElement> elems, String view) {
		for (EModelElement me : elems)
			if ((me instanceof EClass)
					&& (((EClass) me).getEAllSuperTypes().size() == 0))
				checkInheritanceTree((EClass) me, elems, view, 0);
	}

	private static DGraphElementType guess() {
		if (allannots.size() == 0)
			return DGraphElementType.GENERIC;
		boolean containsNode = false;
		boolean containsLink = false;
		for (DAnnotation dAnnotation : allannots) {
			if (LINK.equals(dAnnotation.key))
				containsLink = true;
			if (NODE.equals(dAnnotation.key))
				containsNode = true;
		}
		if (containsLink && containsNode)
			return DGraphElementType.GENERIC;
		else if (containsLink)
			return DGraphElementType.LINK;
		else if (containsNode)
			return DGraphElementType.NODE;
		else
			return DGraphElementType.GENERIC;
	}

	private static void nodeOrLink(IDiagraphRunner runner, EClass eclass) {

		// EAnnotation ano = eclass.getEAnnotation(getAnnotationLitteral());
		EAnnotation ano = getInViewDiagraphAnnotation(eclass);
		if (ano != null) {
			List<DAnnotation> anns = createAnnotations(runner, ano);
			for (DAnnotation dAnnotation : anns)
				if (NODE.equals(dAnnotation.key)
						|| LINK.equals(dAnnotation.key))
					allannots.add(dAnnotation);
		}
	}

	private static DGraphElementType guessNodeOrLink(IDiagraphRunner runner,
			EModelElement element, IDiaParser parser) {
		allannots = new ArrayList<DAnnotation>();
		for (EModelElement eModelElement : parser.getModelElements()) {
			if (eModelElement instanceof EClass) {
				EClass eclass = (EClass) eModelElement;
				if (eclass.getEAllSuperTypes() != null
						&& eclass.getEAllSuperTypes().contains(element))
					nodeOrLink(runner, eclass);
			}
		}
		return guess();
	}

	private IDiagraphRunner runner;

	private DAnnotation(IDiagraphRunner runner_, String key, String value,
			String layer) {
		;
		this.layer = layer;
		this.value = value;
		if (value!=null && value.contains(","))
			throw new RuntimeException("should not happen in DAnnotation");
		this.key = key;
		this.runner = runner_;
	}

	@Override
	public IDiagraphRunner getRunner() {
		return runner;
	}

	public DAnnotation(IDiagraphRunner runner, EAnnotation owner, String key,
			String value, String argument2,  String argument, String layer) {
		this(runner, key, value, layer);
		this.owner = owner;
		this.argument_ = argument;
		this.argument2 = argument2;
	}

	private String expression;

	public void toExpression() {
		String expr = "";
		if (leftHand[0] != null)
			expr += leftHand[0];
		if (leftHand[1] != null)
			expr += "." + leftHand[1];
		if (rightHand[0] != null)
			expr += "=" + rightHand[0];
		if (rightHand[1] != null)
			expr += "." + rightHand[1];
		if (expr.length() > 0)
			expression = expr;
	}

	public String toString() {
		String result = "(" + layer + ")" + key + "="
				+ (value == null ? "" : value) + "="
				+ (argument_ == null ? "" : argument_)
				+ (argument2 == null ? "" : argument2);
		if (expression != null && expression.length() > 0)
			result += " expression: " + expression;
		return result;
	}

	public boolean keyEquals(String k) {
		return k.equals(key);
	}

	public boolean keyFirstSegmentEquals(String k) {
		String[] ks = new String[2];
		if (key.contains(":"))
			ks = key.split(":");
		else
			ks[0] = key;
		return k.equals(ks[0]);
	}

	private static DAnnotation createSimple(IDiagraphRunner runner,
			Map.Entry<String, String> entry, EAnnotation annot) {
		String viewname = DIAGRAPH_DEFAULT;
		for (Map.Entry<String, String> entr : annot.getDetails())
			if (entr.getKey().startsWith(VIEW_EQ))
				viewname = entr.getKey().split("=")[1];
		DAnnotation a = new DAnnotation(runner, entry.getKey(),
				entry.getValue(), viewname);
		if (false)
			clog("*a{" + a.toString() + "}a* -"
					+ ((EClass) annot.getEModelElement()).getName());
		return a;
	}

	private static DAnnotation createEgal(IDiagraphRunner runner,
			Map.Entry<String, String> entry, EAnnotation annot) {
		String arg1_ = null;
		String arg2_ = null;
		String[] args_ = entry.getKey().split("=");
		if (args_.length > 2)
			throw new RuntimeException("parseAnnotation: only 2 arguments for "
					+ annot.toString());
		for (int i = 0; i < args_.length; i++)
			args_[i] = args_[i].trim();

		if (!args_[0].equals(DiagraphKeywords.LABELS)) {
			if (args_[1].contains(":")) {
				String[] sargs = args_[1].split(":");
				if (sargs.length > 2)
					throw new RuntimeException(
							"parseAnnotation: only 2 sub arguments for "
									+ args_[1]);
				for (int i = 0; i < sargs.length; i++)
					sargs[i] = sargs[i].trim();
				args_[1] = sargs[0];
				arg1_ = sargs[1];
			}
		}
		if (args_[0].equals(DiagraphKeywords.KREFERENCE_)) { //FP150513transp
			if (args_[1].contains(",")) {
				String[] sargs = args_[1].split(",");
				if (sargs.length > 2)
					throw new RuntimeException(
							"parseAnnotation: only 2 sub arguments for "
									+ args_[1]);
				for (int i = 0; i < sargs.length; i++)
					sargs[i] = sargs[i].trim();
				args_[1] = sargs[0];
				arg2_ = sargs[1];

			}
		}
		String viewname = DIAGRAPH_DEFAULT;
		for (Map.Entry<String, String> entr : annot.getDetails())
			if (entr.getKey().startsWith(VIEW_EQ))
				viewname = entr.getKey().split("=")[1];
		if (args_[1]!=null && args_[1].contains(","))
			throw new RuntimeException("should not happen in createEgal");
		DAnnotation a = new DAnnotation(runner, annot, args_[0], args_[1], arg2_,arg1_,
				viewname);
		if (LOG && false)
			clog("-{" + a.toString() + "}-");
		return a;
	}

	public static DAnnotation createAnnotation(IDiagraphRunner runner,
			Map.Entry<String, String> entry, EAnnotation toAdd) {
		//if (entry.getKey().contains("link=toThing")).... //FP150522a
		if (entry.getKey().contains("="))
			return createEgal(runner, entry, toAdd);
		else
			return createSimple(runner, entry, toAdd);
	}

	public static List<DAnnotation> createAnnotations(IDiagraphRunner runner,
			EAnnotation annotation) {
		List<DAnnotation> annotations = new ArrayList<DAnnotation>();
		if (annotation == null)
			return annotations; // empty //FP120111
		if (!(annotation.eContainer() instanceof EClass))
			throw new RuntimeException(
					"annotation container should be an EClass !!!!");

		if (DiagraphKeywords.ANNOT_DIAGRAPH_LITTERAL.equals(annotation
				.getSource()) && parseView(annotation).equals(currentView)) {
			if (LOG)
				clog("[" + ((EClass) annotation.eContainer()).getName() + "]");
			for (Map.Entry<String, String> entry : annotation.getDetails()) {
				DAnnotation an = createAnnotation(runner, entry, annotation);
				if (an != null)
					annotations.add(an);
			}
		}
		return annotations;
	}

	protected Map<EClass, List<DAnnotation>> annotations;

	static class Parser {

		private static void log(DBaseStyle style) {
			if (!(LOG))
				return;
			if (style == null)
				clog("style == null");
			else
				clog(style.getName());
		}

		private static void log(IDiaEdge ref) {
			if (!(LOG))
				return;
			if (ref != null) {
				if (ref.getSourceNode() != null) {
					clog(ref.getType() + " created (1): "
							+ ref.getSourceNode().getName() + "."
							+ ref.getName());
					// TODO supress trans-diagram graphical links
					String gmfname = ref.getSourceNode().getName()
							.toUpperCase()
							+ "_"
							+ ref.getName().substring(0, 1).toUpperCase()
							+ ref.getName().substring(1);
					clog("gmfname=" + gmfname);
				} else
					clog(ref.getType()
							+ " -abstract to be propagated- created (2): "
							+ ref.getName());
			} else
				clog(" created (3): NULL!!"); // FP120111
		}

		public static void parse(IDiagraphRunner runner,
				List<DAnnotation> annotations, DAnnotation annotation,
				EModelElement element, IDiaParser parser) {
			if (!(element instanceof EClass))
				throw new RuntimeException(element.toString()
						+ " should be an EClass");
			if (LOG_COMPOSITE_LABELS) {
				if (annotation.getKey().equals("label")
						|| annotation.getKey().equals("labels")) {
					String log = annotation.getKey() + "="
							+ annotation.getValue() + "=" + annotation.argument_;
					cloglabs("   (1)parse-----" + log);
				}
			}

			if (LOG) {
				String log = annotation.getKey() + "=" + annotation.getValue()
						+ "=" + annotation.argument_;
				//if (log.equals("link=toThing=null"))
				//	tb = true;
				clog("   parse-----" + log);//parse-----
			}
			if (annotation.getKey().equals(NODE)
					|| annotation.getKey().startsWith(NODE + ":")) { // FP121007
				if (annotation.getKey().startsWith(NODE + ":"))
					runner.cerror("annotation " + NODE + ":" + "is obsolete");

				DiaNode nod = parseNode(runner, annotations, annotation,
						(EClass) element, parser);

			} else if (annotation.getKey().equals(CONTAINMENT_HOST_NU)) {
				IDiaContainment comp = parseHostContainment(annotations,
						annotation, (EClass) element, parser);
			} else if (annotation.getKey().equals(CONTAINMENT)) {
				parseContainment(annotations, annotation, (EClass) element,
						parser);
			} else if (annotation.getKey().equals(LABEL)) {
				DiaContainedElement lbl = parseLabel(runner, annotations,
						annotation, (EClass) element, parser);
			} else if (annotation.getKey().equals(LABELS)) {
				DiaContainedElement lbl = parseLabels(runner, annotations,
						annotation, (EClass) element, parser);
			} else if (annotation.getKey().equals(POINT_OF_VIEW)) { // FP120102
				DiaNode nod = parseNode(runner, annotations, annotation,
						(EClass) element, parser);
			} else if (annotation.getKey().equals(OPEN_DIAGRAM)) { // FP121022kkk
				// tb = true;
				DiaNode nod = parseNode(runner, annotations, annotation,
						(EClass) element, parser);
			}

			else if (annotation.getKey().equals(LINK)
					//|| annotation.getKey().startsWith(LINK + "=")
					|| annotation.getKey().startsWith(LINK + ":")) {// FP121007
				if (annotation.getKey().startsWith(LINK + ":"))
					runner.cerror("annotation " + LINK + ":" + " is obsolete ?");
				//if (LOG && annotation.getKey().equals(LINK) && "toThing".equals(annotation.getValue()))
				//	tb = true;
//FP150523
				DiaLink link = parseLink( annotation,
						(EClass) element, parser); // FP150522b
			} else if (annotation.getKey().equals(REFERENCE)) {
				DiaReference ref = parseReference(annotations, annotation,
						(EClass) element, parser);
				log(ref);
			} else if (annotation.getKey().equals(KREFERENCE_)) {
				boolean mustThrowException = !runner.isLayouting();
				List<IDiaContainment> comps = parseKReference_(
						mustThrowException, annotations, annotation,
						(EClass) element, parser);
				// log(comp);
			} else if (annotation.getKey().equals(LNK)) {
				DiaLink comp = parseLnk_(annotations, annotation,
						(EClass) element, parser);
				if (comp == null) // FP120111
					DLog.addWarning("parse link null: "
							+ ((EClass) element).getName() + " - "
							+ annotation.toString());
				log(comp);
			} else if (annotation.getKey().equals(CREFERENCE_)) {
				List<IDiaContainment> comps = parseCReference_(annotations,
						annotation, (EClass) element, parser);
				// log(comp);
			} else if (PREFERENCE_OLD_NU.equals(annotation.getKey()))
				  throw new RuntimeException("keyword "+PREFERENCE_OLD_NU+" is obsolete");
			else if (annotation.getKey().equals(AFFIXED_)) {
				IDiaContainment comp = parsePReference_(annotations,
						annotation, // FP130319ppp
						(EClass) element, parser);
				// log(comp);
			} else if (annotation.getKey().equals(LINK_SOURCE))
				parser.getLinkWithSource(element, annotation.getValue());
			else if (annotation.getKey().equals(LINK_TARGET)) {
				// parser.getLinkWithtarget(element, annotation.getValue(),"");
				DiaLink link = parseLnkAssocs(runner, annotations, annotation,
						(EClass) element, parser); // FP121008
			} else if (annotation.getKey().equals(IN_COMPARTMENT_NU)) {
				IDiaContainment dcomp = parseCompartment(annotations,
						annotation, (EClass) element, parser);
				// log(dcomp);
			}
		}



	}
/*
	private static DiaLink parseLink_nu(IDiagraphRunner runner,
			List<DAnnotation> annotations, DAnnotation annotation,
			EClass element, IDiaParser parser) {
		String[] labelledLink = new String[2];
		String linkname = null;
		if (annotation.getValue() != null && annotation.getValue().length() > 0) {
			linkname = annotation.getValue();
		} else if (annotation.getKey().startsWith(LINK + ":")) { // FP121007
			labelledLink = annotation.getKey().split(":");
			linkname = labelledLink[1];
		} else
			labelledLink[0] = (annotation.getKey());

		DiaLink result = parser.getNamedLink(element, linkname);

		return result;

	}
*/
	private static boolean isDiaStyleToken(String tok) {
		for (String xkey : DIASTYLE_TOKENS) {
			if (tok.startsWith(xkey))
				return true;
		}
		return false;
	}

	public static boolean isValidToken(String tok) {
		for (String key : VALID_TOKENS)
			if (key.equals(tok))
				return true;
		return false;
	}

	public static boolean isCurrentValidToken(String tok) {
		String t = tok;
		String[] composedToken = tok.split(":");
		if (composedToken.length == 2)
			t = composedToken[0];
		for (String key : VALID_TOKENS) {
			if (key.equals(t))
				return true;
			else if (key.endsWith("*")
					&& t.startsWith(key.substring(0, key.length() - 1))) // FP120716
				return true;

		}
		return false;
	}

	private static void checkValidToken(List<DAnnotation> anns,
			String elementName) {
		for (DAnnotation anot : anns) {
			if (!isCurrentValidToken(anot.key)) {
				throw new RuntimeException(
						"parseElementAnnotations - token is not valid: "
								+ anot.key + "-" + " for element :"
								+ elementName + " !!!!");
			}
		}
	}

	private static String parseView(EAnnotation eAnnotation) {
		for (Map.Entry<String, String> entry : eAnnotation.getDetails())
			if (entry.getKey().startsWith(VIEW_EQ))
				return entry.getKey().split("=")[1];
		return DIAGRAPH_DEFAULT;
	}

	public static List<DAnnotation> parseElementAnnotations(
			IDiagraphRunner runner, String viewId, EModelElement element_,
			IDiaParser parser, IProgressMonitor progressMonitor) {
		;
		String elementName = ((ENamedElement) element_).getName();
		if (progressMonitor != null) {
			progressMonitor.subTask("parseElementAnnotations " + elementName);
			progressMonitor.worked(1);
		}
		if (LOG){
			clog("(2)parseElementAnnotations for view " + viewId + " and element "
					+ elementName);
		}

		List<DAnnotation> annotations = new ArrayList<DAnnotation>();
		for (EAnnotation annotation : (List<? extends EAnnotation>) element_
				.getEAnnotations()) {
			if (annotation.getSource().equals(
					DiagraphKeywords.ANNOT_DIAGRAPH_LITTERAL)) {
				String id = parseView(annotation);
				if (id.equals(viewId)) {
					List<DAnnotation> anns = new ArrayList<DAnnotation>();
					try {
						anns = createAnnotations(runner, annotation);
					} catch (Exception e) {
						throw new RuntimeException("bad annotation syntax "
								+ annotation.toString() + " for element :"
								+ elementName + " !!!!");
					}
					checkValidToken(anns, elementName);
					if (isGenericElement(anns))
						checkGenericElement(parser, elementName, element_);
					for (DAnnotation anot : anns)
						Parser.parse(runner, anns, anot, element_, parser);
					annotations.addAll(anns);
				}

			}

		}

		/*
		 * for (EAnnotation annotation : (List<? extends EAnnotation>) element
		 * .getEAnnotations()) { if (annotation.getSource().equals(
		 * DiagraphKeywords.ANNOT_DIAGRAPH_LITTERAL)) { String id =
		 * parseView(annotation); if (id.equals(viewId)) { parser2. } } }
		 */

		return annotations;
	}

	private static void checkGenericElement(IDiaParser parser,
			String elementName, EModelElement element) {
		if (LOG)
			clog("generic element: " + elementName);
		DiaContainedElement crtd = parser.getContainedElement(element, null);
		if (crtd instanceof DiaLink)
			clog("A generic Element was already promoted to DialLink: "
					+ crtd.getName());
		else if (!(crtd instanceof DiaGenericElement)) // FP121122y
			clog("should be a generic element: " + crtd.getName());
	}

	private static boolean isGenericElement(List<DAnnotation> anns) {
		for (DAnnotation anot : anns)
			if (anot.keyFirstSegmentEquals(NODE)
					|| anot.keyFirstSegmentEquals(LINK)
					|| anot.keyFirstSegmentEquals(OPEN_DIAGRAM))
				return false;
		return true;
	}

	public static void parse(IDiagraphRunner runner, IDiaParser parser,
			IProgressMonitor progressMonitor) {
			for (EModelElement element : parser.getModelElements()) {
				EClass eclaz1 = null;
				EClass eclaz2 = null;
				if (element instanceof EClass) {
					eclaz1 = (EClass) element;
					if (LOG) {
						clog("DAP parsing EClass:" + eclaz1.getName());
						// DAP parsing EClass:Sequence
					}

				} else if (element instanceof EReference) {
					EReference ref = (EReference) element;

					eclaz1 = (EClass) ref.eContainer();
					eclaz2 = (EClass) ref.getEType();
					if (LOG)
						clog("DAP parsing EReference:" + eclaz1.getName() + "."
								+ ref.getName());
				}
				if (eclaz1 != null
						&& getInViewDiagraphAnnotation(eclaz1) == null)
					continue;
				if (eclaz2 != null
						&& getInViewDiagraphAnnotation(eclaz2) == null)
					continue;
				if (eclaz1 == null)
					continue;
				if (element instanceof EClass)
					parseElementAnnotations(runner, parser.getView(), element,
							parser, progressMonitor);
			}
			parser.postParse(); // FP140505 depl
	}

	public static Entry<String, String> matches(boolean exact, EAnnotation an,
			String source, String key) {
		if (source.equals(an.getSource()) && parseView(an).equals(currentView)) {
			for (Map.Entry<String, String> entry : an.getDetails()) {
				if ((entry.getKey().contains(key) && !exact)
						|| (entry.getKey().equals(key) && exact))
					return entry;
			}
		}
		return null;
	}

	public static String getPointOfViewName2(DiaNode node, String layer) { // FP140216
		EClass eclaz = (EClass) node.getEModelElement();
		String name = null;
		Entry<String, String> canvasproperty = getPointOfViewProperty(eclaz);
		if (canvasproperty == null)
			canvasproperty = getNavigationProperty(eclaz);
		if (canvasproperty != null) {
			String subval = subValue(canvasproperty);
			if (subval.contains(".")) {
				subval = subval.substring(subval.indexOf(".") + 1);
				if (subval != null && !subval.isEmpty())
					name = layer + VIEW_SEPARATOR_1 + subval;
			} else {
				subval = ROOT_NAME; // FP140216
				name = layer;
			}

		}

		return name;
	}

	public static String getPointOfViewName1(DiaNode node, String layer) {
		EClass eclaz = (EClass) node.getEModelElement();
		String name = null;
		Entry<String, String> canvasproperty = getPointOfViewProperty(eclaz);
		if (canvasproperty == null)
			canvasproperty = getNavigationProperty(eclaz);
		if (canvasproperty != null) {
			String subval = subValue(canvasproperty);
			if (subval.contains("."))
				subval = subval.substring(subval.indexOf(".") + 1);
			else
				subval = ROOT_NAME; // FP140216
			if (subval != null && !subval.isEmpty())
				name = layer + VIEW_SEPARATOR_1 + subval;
		}
		return name;
	}

	/*
	 * public static String getPointOfViewName2(DiaNode node, String layer) {
	 * String name = getPointOfViewName1(node, layer); if
	 * (name.endsWith(ViewConstants.VIEW_SEPARATOR_1 + ViewConstants.ROOT_NAME))
	 * // FP140603 TODO revoir name = name .substring( 0, name.length() -
	 * (ViewConstants.VIEW_SEPARATOR_1 + DiagraphKeywords.ROOT_NAME) .length());
	 * return name; }
	 */

	public static Entry<String, String> getPointOfViewProperty(EClass claz) {
		Entry<String, String> canvasEntry = null;
		for (EAnnotation annot : claz.getEAnnotations()) {
			if (parseView(annot).equals(currentView)) {
				canvasEntry = matches(true, annot, // FP130105x true
						DiagraphKeywords.ANNOT_DIAGRAPH_LITTERAL, POINT_OF_VIEW);
				if (canvasEntry != null) {
					for (Map.Entry<String, String> entry : annot.getDetails()) {
						if (LOG)
							clog(entry.getKey());
						return canvasEntry;
					}
				}
			}
		}
		return null;
	}

	public static Entry<String, String> getNavigationProperty(EClass claz) {
		Entry<String, String> canvasEntry = null;
		for (EAnnotation annot : claz.getEAnnotations()) {
			if (parseView(annot).equals(currentView)) {
				canvasEntry = matches(false, annot,
						DiagraphKeywords.ANNOT_DIAGRAPH_LITTERAL, OPEN_DIAGRAM);
				if (canvasEntry != null) {
					for (Map.Entry<String, String> entry : annot.getDetails()) {
						if (LOG)
							clog(entry.getKey());
						return canvasEntry;
					}
				}
			}
		}
		return null;
	}

	public static String subKey(Entry<String, String> entry) {
		String k = entry.getKey();
		String[] args = k.split(":");
		if (args.length > 1)
			return args[0];
		else
			return "";
	}

	public static String subValue(Entry<String, String> entry) {
		String k = entry.getKey();
		String[] args = k.split(":");
		if (args.length > 1)
			return args[1];
		else
			return "";
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return DParams.NULL_ANNOTATION_VALUE.equals(value) ? null : value;// FP140429xxxx
	}

	public static boolean isDiaObject(EObject o) {
		if (o instanceof EClass) {
			EAnnotation ano = getInViewDiagraphAnnotation((EClass) o);
			if (ano != null)
				return true;
		}
		return false;
	}

	public static void checkAllReferences(IDiagraphRunner runner, EClass eclass) {
		EAnnotation ean = getInViewDiagraphAnnotation(eclass);
		List<DAnnotation> anns = createAnnotations(runner, ean);
		for (DAnnotation anot : anns) {
			if (anot.getKey().equals(REFERENCE)) {
				if (eclass.getEStructuralFeature(anot.getValue()) == null)
					throw new RuntimeException(" reference " + eclass.getName()
							+ "." + anot.getValue() + " should exist ");
			}
		}
	}

	public static List<EReference> checkDuplicatReferenceNames(
			IDiagraphRunner runner, EClass targetClass) {
		List<EReference> duplicates = new ArrayList<EReference>();
		Hashtable<String, EReference> names = new Hashtable<String, EReference>();
		EPackage parent = targetClass.getEPackage();
		for (TreeIterator<EObject> it1 = parent.eAllContents(); it1.hasNext();) {
			EObject o = it1.next();
			if (isDiaObject(o)) {
				EClass sourceClass = (EClass) o;
				EList<EReference> srefs = sourceClass.getEReferences();
				for (EReference sref : srefs) {
					if (sref.getEType() == targetClass) {
						if (hasReference(runner, sourceClass, sref)) {
							if (!names.containsKey(sref.getName()))
								names.put(sref.getName(), sref);
							else
								duplicates.add(sref);
						}
					}
				}
			}
		}
		return duplicates;
	}

	public static boolean hasEntry(IDiagraphRunner runner, EClass eclass,
			String key, String value) {//
		if (!isCurrentValidToken(key))
			throw new RuntimeException("hasEntry:  not valid token");
		EAnnotation ean = getInViewDiagraphAnnotation(eclass);
		for (Map.Entry<String, String> entry : ean.getDetails()) {
			DAnnotation an = createAnnotation(runner, entry, ean);
			if (an != null)
				if (an.getKey().equals(key) && an.getValue().equals(value)) {
					return true;
				}
		}
		return false;
	}

	public static void changeLitteral(EClass eclass, String oldLitteral,
			String newLitteral, List<String> logs) {
		EAnnotation ean = eclass.getEAnnotation(oldLitteral);
		if (ean != null) {
			ean.setSource(newLitteral);
			logs.add("    annotation source changed :" + eclass.getName() + " "
					+ oldLitteral + " -> " + newLitteral);
		}
	}

	public static void completeNullValuesToNullValue(EClass eClass) {
		EAnnotation ean = getInViewDiagraphAnnotation(eClass);
		if (ean != null) {
			EMap<String, String> details = ean.getDetails();
			Iterator<Entry<String, String>> iterator = details.iterator();
			while (iterator.hasNext()) {
				Entry entry = iterator.next();
				if (entry.getValue() == null) {
					// clog("annotation value must be set to empty string :"+eClass.getName()+" "+entry.getKey()+" -> "
					// );
					entry.setValue(DParams.NULL_ANNOTATION_VALUE);
				}
			}
		}
	}

	public static void changeKey(EClass eClass, String oldKey, String newKey,
			List<String> logs) {
		EAnnotation ean = getInViewDiagraphAnnotation(eClass);
		if (ean != null) {
			EMap<String, String> details = ean.getDetails();
			List<String> newks = new ArrayList<String>();
			Iterator<Entry<String, String>> iterator = details.iterator();
			while (iterator.hasNext()) {
				Entry<String, String> entry = iterator.next();
				if ((entry.getKey()).contains(oldKey)) {
					String old = entry.getKey();
					String newk = old.replace(oldKey, newKey);
					logs.add("    annotation key change :" + eClass.getName()
							+ " " + oldKey + "->" + newKey);
					newks.add(newk);
					iterator.remove();
				}
			}
			for (String newk : newks) {
				ean.getDetails().put(newk, "");
			}
		}
	}

	public static void renameAnnotation_obsolete(IDiagraphRunner runner,
			EReference ref, String oldName) {
		EClass eclass = (EClass) (ref.eContainer());
		EAnnotation ean = getInViewDiagraphAnnotation(eclass);
		boolean removed = false;
		for (Map.Entry<String, String> entry : ean.getDetails()) {
			DAnnotation an = createAnnotation(runner, entry, ean);
			if (an != null)
				if (an.getKey().equals(REFERENCE)
						&& an.getValue().equals(oldName)) {
					removeDetail(runner, eclass, REFERENCE, oldName);
					removed = true;
					break;
				}
		}
		if (!removed)
			throw new RuntimeException(" should remove old name: " + oldName);
		ean.getDetails().put(REFERENCE, ref.getName());
		if (LOG)
			clog("annotation renamed :" + REFERENCE + " " + oldName + " -> "
					+ ref.getName());
	}

	public static void logAnnotation(EClass eClass, List<String> logs) {
		EAnnotation ean = getInViewDiagraphAnnotation(eClass);
		logs.add("  " + eClass.getName() + "{");
		if (LOG)
			clog(eClass.getName());
		EMap<String, String> details = ean.getDetails();
		Iterator<Entry<String, String>> iterator = details.iterator();
		while (iterator.hasNext()) {
			Entry<String, String> entry = iterator.next();
			if (LOG)
				clog("    " + entry.getKey() + "=" + entry.getValue());
			logs.add("      " + entry.getKey());
			Stats.statAnnotation(eClass, entry.getKey());
		}
		logs.add("  }");
		logs.add("");
	}

	private static boolean findLabelAnnotationInSuperTypes(// FP140204 ok ?
			IDiagraphRunner runner, EClass claz) {
		String[] keys = { LABEL, LABELS };
		for (EClass superClass : claz.getEAllSuperTypes()) {
			List<IAnnotation> anss = findAnnotations(runner, superClass, keys);
			if (anss.size() > 0)
				return true;
		}
		return false;
	}

	private static boolean inferLabelAnnotationIfMissing(EClass claz, // FP140204ok
			EAnnotation ean) {
		EList<EAttribute> attributes = claz.getEAttributes();
		for (EAttribute attr : attributes) {
			for (String nameattr : DParams.NAME_ATTRIBUTES)
				if (attr.getName() != null
						&& attr.getName().toLowerCase().contains(nameattr)) {
					ean.getDetails().put(LABEL + "=" + attr.getName(),
							DParams.NULL_ANNOTATION_VALUE);
					if (LOG)
						clog("creating inferred label annotation for "
								+ claz.getName() + " : " + attr.getName());
					return true;
				}
		}
		if (attributes.size() > 0) {
			String an = attributes.get(attributes.size() - 1).getName();
			ean.getDetails().put(LABEL + "=" + an,
					DParams.NULL_ANNOTATION_VALUE); // the
			if (LOG)
				clog("creating inferred label annotation for " + claz.getName()
						+ " : " + an); // lowest
			return true;
		}
		return false;

	}

	private static void clog(String mesg) {
		if (LOG || DParams.Parser_15_LOG)
			System.out.println(mesg);

	}

	private static void cloglabs(String mesg) {
		if (LOG_COMPOSITE_LABELS)
			System.out.println(mesg);

	}

	public static boolean refactorAnnotations(IDiagraphRunner runner,
			EPackage mmodel) {
		for (TreeIterator<EObject> it1 = mmodel.eAllContents(); it1.hasNext();) {
			EObject sourceClass = it1.next();
			if (sourceClass instanceof EClass) {
				EAnnotation ean = DAnnotation
						.getInViewDiagraphAnnotation((EClass) sourceClass);
				List<IAnnotation> ans = findAnnotations(runner,
						(EClass) sourceClass, DAnnotation.VALID_TOKENS);
				if (ans.size() == 0
						&& !findLabelAnnotationInSuperTypes(runner,
								(EClass) sourceClass)
						&& DParams.INFER_LABELS_IF_MISSING)
					if (inferLabelAnnotationIfMissing((EClass) sourceClass, ean))
						return true;
			}
		}
		return false;

	}

	public static void setCurrentView(String curView) { // FP121015
		if (curView == null)
			currentView = DIAGRAPH_DEFAULT;
		else
			currentView = curView;
	}

	public static String getCurrent_View() {
		return currentView;
	}

	public EAnnotation getOwner() {
		return owner;
	}

}
