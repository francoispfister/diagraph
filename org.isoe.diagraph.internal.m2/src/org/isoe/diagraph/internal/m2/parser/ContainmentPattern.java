package org.isoe.diagraph.internal.m2.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.isoe.diagraph.internal.m2.DiaContainedElement;
import org.isoe.diagraph.internal.m2.DiaLink;
import org.isoe.diagraph.internal.m2.DiaNode;
import org.isoe.diagraph.internal.m2.api.IDiaParser;
import org.isoe.diagraph.lang.DiagraphKeywords;
import org.isoe.diagraph.parser.api.IDiagraphNotation;
import org.isoe.diagraph.parser.api.IDiagraphView;
import org.isoe.diagraph.parser.api.IDiagraphNode;
import org.isoe.diagraph.parser.api.IDiagraphReferenceAssociation;
import org.isoe.fwk.core.DParams;

public class ContainmentPattern {

	final static boolean DIRECT_CONTAINEME = true;
	final static boolean INHERITED_CONTAINEME = false;
	private static final boolean LOG = DParams.ContainmentPattern_LOG;

	private IDiaParser parser;

	private List<EReference> directContainments;
	private List<EReference> allContainements;
	private IDiagraphNotation notation;
	// private IDiagraphParser notation.getParser();
	//private IDiagraphView grammar;

	public ContainmentPattern(IDiaParser parser, IDiagraphNotation notation) {
		super();
		this.parser = parser;
		this.notation = notation;// = grammar;
		// notation.getParser() = notation.getParser();
	}

	private String getViewName(EAnnotation annotation) {
		String view = "";
		view = "default";
		for (java.util.Map.Entry<String, String> srcdets : annotation
				.getDetails()) {
			if (srcdets.getKey().startsWith("view=")) {
				String[] p = srcdets.getKey().split("=");
				view = p[1];
			}
		}
		return view;
	}

	private boolean isDiagraphAnnotated(EClass eclass, String view) {
		if (view == null || view.isEmpty())
			view = "default";
		for (EAnnotation e : eclass.getEAnnotations())
			if (e.getSource().equals("diagraph") && getViewName(e).equals(view))
				return true;
		return false;
	}

	private List<EClass> subClasses(EClass c) {
		List<EClass> result = new ArrayList<EClass>();
		for (EClassifier eClassif : c.getEPackage().getEClassifiers()) {
			if (eClassif instanceof EClass)
				if (!eClassif.equals(c) && c.isSuperTypeOf((EClass) eClassif))
					result.add((EClass) eClassif);

		}
		return result;
	}

	public List<EClass> getTaggedConcreteSubClasses(EClass claz, String view) {
		List<EClass> result = new ArrayList<EClass>();
		List<EClass> subclasses = subClasses(claz);
		for (EClass subClass : subclasses) {
			if (!subClass.isAbstract() && isDiagraphAnnotated(subClass, view))
				result.add(subClass);
		}
		return result;
	}

	public List<EClass> getTaggedClasses(EPackage pack, String view) {
		List<EClass> result = new ArrayList<EClass>();
		List<EClassifier> classifiers = pack.getEClassifiers();
		for (EClassifier eClassifier : classifiers)
			if (eClassifier instanceof EClass)
				if (isDiagraphAnnotated((EClass) eClassifier, view))
					result.add((EClass) eClassifier);
		return result;
	}

	private List<EReference>  multipleContentionThroughInheritance(DiaContainedElement element) {
		for (EReference eReference : allContainements) {
			EClass rt = (EClass) (eReference.getEType());
			DiaNode dn = parser.findNode(rt.getName());
			if (dn != null
					&& rt.isSuperTypeOf((EClass) element.getEModelElement())
					&& rt != element.getEModelElement())
				return allContainements;
		}
		return null;
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);

	}

	// ////////////////////////////////

	private List<DiaNode> getDiaNodes() {
		List<DiaNode> result = new ArrayList<DiaNode>();

		for (DiaContainedElement diaContainedElement : parser
				.getDiagramElements()) {
			if (diaContainedElement instanceof DiaNode)
				result.add((DiaNode) diaContainedElement);
		}
		return result;
	}

	private boolean isDirectTopNode(DiaContainedElement element, String view) {
		for (EReference eReference : directContainments) {
			DiaNode diaNode = parser
					.findNode(((EClass) eReference.eContainer()).getName());
			if (diaNode != null) {

				if (diaNode.isDirectCanvas(view)) {
					if (LOG)
						clog(element.getName() + " contained (1) by canvas "
								+ diaNode.getName());
					return true;
				}
			}
		}
		/*
		 * DiaNode canvas = element.getCanvasContainment(); if (canvas != null)
		 * { if (LOG) clog("contained (2) by canvas " + canvas.getName());
		 * return true; }
		 */
		return false;
	}

	private boolean isInheritedTopNode(DiaContainedElement element, String view) {
		for (EReference eReference : allContainements) {
			DiaNode diaNode = parser
					.findNode(((EClass) eReference.eContainer()).getName());
			// if (diaNode.isCanvas(taggedSubclass, view)
			if (diaNode.isCanvas(diaNode.getSemanticRole(), view)) {
				if (LOG)
					clog(element.getName()
							+ " is contained (through inheritance) by canvas: "
							+ diaNode.getName() + " through "
							+ eReference.getName());
				return true;
			}

			// DiaNode diaNode = parser.findNode(((EClass)
			// eReference.getEType()).getName());
			if (diaNode != null) {
				List<EClass> taggedSubclasses = getTaggedConcreteSubClasses(
						diaNode.getSemanticRole(), view);
				for (EClass taggedSubclass : taggedSubclasses) {
					if (diaNode.isCanvas(taggedSubclass, view)) {
						if (LOG)
							clog(element.getName()
									+ " is contained (through inheritance) by canvas: "
									+ diaNode.getName() + " through subclass "
									+ taggedSubclass.getName());
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean isDirectOrInheritedTopNode(DiaContainedElement element,
			String view) {

		for (EReference eReference : allContainements) {
			DiaNode diaNode = parser
					.findNode(((EClass) eReference.eContainer()).getName());
			if (diaNode != null) {
				List<EClass> taggedSubclasses = getTaggedConcreteSubClasses(
						diaNode.getSemanticRole(), view);
				for (EClass taggedSubclass : taggedSubclasses) {
					if (diaNode.isCanvas(taggedSubclass, view)) {
						if (LOG)
							clog("contained (through inheritance) by canvas "
									+ diaNode.getName());
						return true;
					}
				}
			}
		}

		for (EReference eReference : directContainments) {
			DiaNode diaNode = parser
					.findNode(((EClass) eReference.eContainer()).getName());
			if (diaNode != null) {

				if (diaNode.isDirectCanvas(view)) {
					if (LOG)
						clog("contained (direct) by canvas "
								+ diaNode.getName());
					return true;
				}
			}
		}

		DiaNode canvas = element.getCanvasContainment();
		if (canvas != null) {
			if (LOG)
				clog("contained () by canvas " + canvas.getName());
			return true;
		}

		return false;
	}

	private boolean isSelfContained_(DiaContainedElement element) {
		for (EReference eReference : directContainments) {
			EClass rt = (EClass) (eReference.getEType());
			if (rt == element.getEModelElement())
				return true;
		}
		return false;
	}

	private boolean isContainedThroughInheritance(
			DiaContainedElement element) {
		if (isSelfContained_(element))
			return false;
		List<EReference> sterr100s = multipleContentionThroughInheritance(element);
				//sterr100Pattern(element);
		return sterr100s != null;
	}

	public IDiagraphNode grammarParseNode(DiaContainedElement element,
			String view_) {
		try {
			return notation.getParser().getDiagraphNode(
					element.getSemanticRole(), 0);

			//return notation.getParser().getDiagraphNode(
			//		element.getSemanticRole(),0);


		} catch (RuntimeException e) {
			parser.cerror("error in grammarParseNode: " + e.toString());
			throw e;
		}

	}

	public String parseNode(DiaContainedElement element, String view_) { // FP140218

		if (DParams.Parser_CONTAINMENT2_LOG)
			clog2("(1)propagateInheritedContainments");

		IDiagraphNode nod = grammarParseNode(element,view_);

		int directscountb, inheritedscountb;
		String result = "";

		// IDiagraphNode z =
		// notation.getParser().createDiagraphNode(element.getSemanticRole(),view_);

		// grammar.addNode(y);

		// IDiagraphNode x = new
		// DiagraphNode(notation.getParser(),view_,element.getSemanticRole());


		List<IDiagraphReferenceAssociation> allContainements = notation.getParser().
				getContainmentAssociationsAndSubHierTo(element.getSemanticRole());



		//List<IDiagraphReferenceAssociation> xxx = notation.getParser().parseAllSiblingAssociations(element.getSemanticRole(),true);


		List<IDiagraphReferenceAssociation> containements = notation.getParser()
				.patternTargetConcreteContainmentAssociationsTo(element.getSemanticRole());
		List<IDiagraphReferenceAssociation> allSimpleReferences = notation
				.getParser().getSimpleAssociationsAndSubHierTo(element.getSemanticRole());
		List<IDiagraphReferenceAssociation> simpleReferences = notation
				.getParser().parseDirectSimpleAssociationsTo(
						element.getSemanticRole());

		List<EClass> supernods = notation.getParser().getSuperGenericsOrNode(
				element.getSemanticRole(), false);
		if (DParams.Parser_CONTAINMENT2_LOG) {
			clog2("supernodes");
			for (EClass supernod : supernods) {
				clog2(supernod.getName());
			}
			clog2("*** view: " + view_ + " ***");
			clog2("*** containments for " + element.getSemanticRole().getName()
					+ " ***");
		}
		boolean isPov = notation.getParser().isPov(element.getSemanticRole());
		boolean isSubPov = notation.getParser().isSubPov(element.getSemanticRole());
		boolean isGeneric = notation.getParser().isGeneric(
				element.getSemanticRole());
		boolean isAbstractNode = notation.getParser().isNodeInstanciable(
				element.getSemanticRole());
	//	boolean isAbstractNode = notation.getParser().isAbstractNode(
	//			element.getSemanticRole());
		// boolean isAbstractLink =
		// notation.getParser().isAbstractLink(element.getSemanticRole());

		List<EReference> krefac_ = notation.getParser()
				.patternContainmentAssociationsKrefAutoCBySub(
						(EClass) element.getSemanticRole(), true);
		if (krefac_ != null)
			if (DParams.Parser_CONTAINMENT2_LOG) clog2("KrefAbstractAutoContainment:" + krefac_.size());

		List<EReference> krefa = notation.getParser()
				.patternContainmentAssociationsKrefAutoCBySub(
						(EClass) element.getSemanticRole(), false);
		if (krefa != null)
			if (DParams.Parser_CONTAINMENT2_LOG) clog2("getKrefAutoContainment:" + krefa.size());



		List<EReference> containmentsalt = notation.getParser()
				.patternKrefContainmentAlt((EClass) element.getSemanticRole());

		if (DParams.Parser_CONTAINMENT2_LOG)
		for (EReference containmentalt : containmentsalt) {
			clog2("containmentalt:" + containmentalt.getName());
		}

		List<EReference> directs = notation.getParser()
				.getDirectContainementReferences(element.getSemanticRole());
		directscountb = directs.size();

		for (EReference eReference : directs) {
			clog(eReference.getName());
		}

		if (!isPov && directs.isEmpty())
			if (DParams.Parser_CONTAINMENT2_LOG)
				clog2("nothing");

		if (DParams.Parser_CONTAINMENT2_LOG)
			clog2("AllContainements for " + element.getName());

		List<EReference> all = notation.getParser()
				.parseAllContainementReferences(element.getSemanticRole());

		inheritedscountb = all.size();

		for (EReference eReference : all) {
			IDiagraphReferenceAssociation compart = notation.getParser()
					.getRecursiveCompartment(
							// FP1404qqqq
							(EClass) element.getSemanticRole(),
							eReference.getName());

			boolean recurcompart = compart != null;
			if (DParams.Parser_CONTAINMENT2_LOG)
			  clog2(eReference.getName()
					+ (recurcompart ? " recurcompart" : "no recurcompart"));
			// boolean isrecurs = x.isRecursiveCompartment(eReference);
		}


		if (!isPov && all.isEmpty())
			if (DParams.Parser_CONTAINMENT2_LOG)
			  clog2("nothing");

		result = " directscount=" + directscountb + " inheritedscount="
				+ inheritedscountb;

		boolean isDirectTopNode = notation.getParser().isDirectTopNode(
				element.getSemanticRole());

		boolean isInhTopNode = notation.getParser().isInheritedTopNode(
				element.getSemanticRole());

		boolean isSelfContained = notation.getParser().isSelfContained(
				element.getSemanticRole());

		boolean isContainedThroughInheritance = notation.getParser()
				.patternContainedThroughInheritance(element.getSemanticRole());

		String s3b = (isPov ? " isPov " : "")
				+ (isGeneric ? " isGeneric " : "")
				+ (isAbstractNode ? " isAbstractNode " : "")
				+ (isDirectTopNode ? " isTopNode " : "")
				+ (isInhTopNode ? " isInheritedTopNode  " : "")
				+ (isContainedThroughInheritance ? " isContainedThroughInheritance "
						: "") + (isSelfContained ? " isSelfContained " : "")
				+ ("") + ("");

		result = "s3=" + s3b + result;

		return result;

	}

	private void clog2(String mesg) {
		if (DParams.Parser_CONTAINMENT2_LOG)
			System.out.println(mesg);

	}

	public String getContainments_old(DiaNode element, String view_) { // FP140218
		boolean check = true;
		String result = "";

		int directscount, inheritedscount;

		directContainments = element.getContainements(null, DIRECT_CONTAINEME);
		directscount = directContainments.size();

		allContainements = element.getContainements(directContainments,
				INHERITED_CONTAINEME);
		inheritedscount = allContainements.size();

		for (EReference eReference : directContainments) {
			clog(eReference.getName());
		}

		if (directContainments.isEmpty())
			clog("nothing");

		for (EReference eReference : allContainements) {
			clog(eReference.getName());
		}

		if (allContainements.isEmpty())
			clog("nothing");

		if (inheritedscount > 0) {

			// boolean hasContainements = containements.size() > 0;
			boolean isDirectTopNode_ = isDirectTopNode(element,view_);

			boolean isInhTopNode_ = isInheritedTopNode(element,view_);

			boolean isContainedThroughInheritance_ = isContainedThroughInheritance(element);

			boolean isSelfContained_ = isSelfContained_(element);

			String s3 = (isDirectTopNode_ ? " isTopNode " : "")
					+ (isInhTopNode_ ? " isInheritedTopNode  " : "")
					+ (isContainedThroughInheritance_ ? " isContainedThroughInheritance "
							: "")
					+ (isSelfContained_ ? " isSelfContained " : "") + ("")
					+ ("");

			result = "s3=" + s3 + " directscount=" + directscount
					+ " inheritedscount=" + inheritedscount;

			// if (LOG)
			// clog(s3);
		} else {
			result = "(2)no containment for " + element.getName() + " in view "
					+ view_;
			parser.cerror(result);
		}
		return result;

	}

	/*
	 * void compare(){ if (!s3.equals(s3b)){ check = false;
	 * System.out.println("s3="+s3+" vs "+s3b); } if (directscountb !=
	 * directscount){ check = false;
	 * System.out.println("directscount="+directscount+" vs "+directscountb); }
	 * if (inheritedscountb != inheritedscount){ check = false;
	 * System.out.println
	 * ("inheritedscount="+inheritedscount+" vs "+inheritedscountb); } }
	 */

	public void logContainment_old(DiaNode element, String view_) { // FP140218

		List<EReference> directs = notation.getParser()
				.getDirectContainementReferences(element.getSemanticRole());
		directContainments = new ArrayList<EReference>();
		directContainments.addAll(directs);

		for (EReference eReference : directContainments) {
			clog(eReference.getName());
		}

		clog("DIRECT_CONTAINEMENT for " + element.getName());
		directContainments = element.getContainements(null, DIRECT_CONTAINEME);

		for (EReference eReference : directContainments) {
			clog(eReference.getName());
		}

		if (directContainments.isEmpty())
			clog("nothing");

		clog("INHERITED_CONTAINEMEN for " + element.getName());
		List<EReference> inheriteds = notation.getParser()
				.parseAllContainementReferences(element.getSemanticRole());
		allContainements = new ArrayList<EReference>();
		allContainements.addAll(inheriteds);

		for (EReference eReference : allContainements) {
			clog(eReference.getName());
		}

		allContainements = element.getContainements(directContainments,
				INHERITED_CONTAINEME);

		for (EReference eReference : allContainements) {
			clog(eReference.getName());
		}

		if (allContainements.isEmpty())
			clog("nothing");

		// if (LOG)
		// notation.getParser().getAllAssociationsTo(element.getSemanticRole(),
		// view_, allContainements);

		if (directContainments.size() == 0 && allContainements.size() == 0) {
			parser.cerror("(3)no containment for " + element.getName()
					+ " in view " + view_);
			return;
		}

		// boolean hasContainements = containements.size() > 0;
		boolean isDirectTopNode = isDirectTopNode(element,view_);
		boolean isInhTopNode = isInheritedTopNode(element,view_);
		boolean isContainedThroughInheritance = isContainedThroughInheritance(element);
		boolean isSelfContained = isSelfContained_(element);

		clog((isDirectTopNode ? " isTopNode " : "")
				+ (isInhTopNode ? " isInheritedTopNode  " : "")
				+ (isContainedThroughInheritance ? " isContainedThroughInheritance "
						: "") + (isSelfContained ? " isSelfContained " : "")
				+ ("") + (""));

	}

	/*
	 * public void parseNode(DiaNode diagramElement, String view) { // String
	 * old = getContainments_old(diagramElement, view);
	 *
	 * String new_ = parseNode_(diagramElement, view);
	 *
	 *
	 * }
	 */

	public String parseLink__(DiaContainedElement element, String view) { // FP14P414

		IDiagraphNode nod = grammarParseNode(element, view);

		return inferContainment_(element, view);
		/*
		 * if (LOG) if (diagramElement.getContainmentReferenceBase() == null) {
		 * if (diagramElement instanceof DiaGenericElement) // may // happen
		 * clog("containement for DiaGenericElement: " +
		 * diagramElement.getName() + " is null for view " + view); else if
		 * (diagramElement.isAbstract()) clog("containement for abstract : " +
		 * diagramElement.getName() + " is null for view " + view); else
		 * clog("containement for : " + diagramElement.getName() +
		 * " is null for view " + view); }
		 */

	}

	/*----------------------------------------*/
	private String inferContainment_(DiaContainedElement element, String view) { // FP140218

		// FP140401aa
		List<EReference> containements = notation.getParser()
				.getDirectContainementReferences(
						(EClass) element.getEModelElement());
		// if (LOG)
		for (EReference containement : containements) {
			System.out.println(containement.getName());
		}

		List<EReference> allContainements = notation.getParser()
				.parseAllContainementReferences(
						(EClass) element.getEModelElement());

		for (EReference containement : allContainements) {
			System.out.println(containement.getName());
		}

		if (containements.size() == 0 && allContainements.size() == 0) {
			parser.cerror("(9)no containment for " + element.getName()
					+ " in view " + view);
			return "ok1";
		}

		boolean hasContainements = containements.size() > 0;
		boolean isTopNode = isTopNode(element, view);
		boolean isContainedThroughInheritance = isContainedThroughInheritance_(element);
		boolean isSelfContained = isSelfContained_(element);

		if (hasContainements && isTopNode && isContainedThroughInheritance
				|| isSelfContained)
			if (inferContainmentAlt(element, containements, view))
				return "ok2";
		if (containements.size() == 0)
			containements = allContainements;
		if (containements.size() == 1)
			inferContainmentSimple(element, containements, view);
		else
			inferContainmentMultiple(element, containements, view);
		return "ok3";
	}

	private void inferContainmentSimple(DiaContainedElement element,
			List<EReference> containements, String view) {

		element.setContainmentReferenceBase(containements.get(0));
		EReference declaredcontRef = getDeclaredContainment(element, view);
		EReference contRef = (EReference) element.getContainmentReferenceBase();
		if (declaredcontRef != null && declaredcontRef != contRef) { // FP131009x
			if (LOG)
				clog("containing reference is now " + declaredcontRef.getName()
						+ " vs " + contRef.getName());
			contRef = declaredcontRef;
			element.setContainmentReferenceBase(contRef);
		}
	} // FP140218b

	// in case of multiple containments
	private EReference getDeclaredContainment(DiaContainedElement element,
			String view) { // FP131009x
		EReference containment = null;
		try {
			containment = parseContainmentReferenceLate_nu(
					(EClass) element.getEModelElement(), view); // FP131008
		} catch (Exception e) {
			throw new RuntimeException("error in getDeclaredContainment");
		}
		if (containment != null) { // FP131008
			if (LOG)
				clog("declared containment found " + containment + " for view "
						+ view);

		}
		return containment; // FP131008
	}

	private EReference parseContainmentReferenceLate_nu(EClass ecl, // FP131008
			String view) {
		// EClass ecl = (EClass) contained_.getEModelElement();
		EAnnotation annot = getDiagraphAnnotation(ecl, view);
		for (Map.Entry<String, String> entry : annot.getDetails()) {
			if (entry.getKey().startsWith(DiagraphKeywords.XCONTAINMENT_EQU)) { // FP131009
																					// //XCONTAINMENT
				String[] pars = entry.getKey().split("=");
				String contname = pars[1];
				pars = contname.split("\\.");
				return (EReference) ((EClass) ecl.getEPackage().getEClassifier(
						pars[0])).getEStructuralFeature(pars[1]);
			}
		}
		return null;
	}

	private EAnnotation getDiagraphAnnotation(EClass eclass, String view) {
		EList<EAnnotation> annots = eclass.getEAnnotations();
		EAnnotation result = null;
		for (EAnnotation annot : annots) {
			if (isDiagraphView_(annot, view)) {
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

	private int isDiagraphDefaultView(EAnnotation annot) {
		if (annot.getSource().equals(DiagraphKeywords.ANNOT_DIAGRAPH_LITTERAL)) {
			for (Map.Entry<String, String> entry : annot.getDetails()) {
				if (entry.getKey().startsWith(DiagraphKeywords.VIEW_EQ)) {
					return 0; // a view but not default
				}
			}
			return 1; // default view
		}
		return -1; // not a diagraph annotation
	}

	private boolean isDiagraphView_(EAnnotation annot, String view) {
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

	private void inferContainmentMultiple(DiaContainedElement element,
			List<EReference> containements, String view) {
		EReference declaredcontRef = getDeclaredContainment(element, view);
		if (declaredcontRef != null) { // FP131008
			element.setContainmentReferenceBase(declaredcontRef);
			if (LOG)
				clog("late containment found " + declaredcontRef + " for view "
						+ view); // TODO validate this
		} else {

			// IDiagraphReferenceAssociation cp =
			// parser2.getTopLevelAssociation((EClass)
			// element.getEModelElement(), view);
			IDiagraphReferenceAssociation containment = notation.getParser()
					.getBaseContainment((EClass) element.getEModelElement());
			if (containment != null)
				element.setContainmentReferenceBase(containment.getTargetReference());
			else
				containerError(element, containements);

		}
	} // FP140218b

	private void containerError(DiaContainedElement element,
			List<EReference> containements) {
		String conts = "";
		for (EReference containr : containements)
			conts += ((EClass) containr.eContainer()).getName() + "."
					+ containr.getName() + " ; "; // FP130929

		// if (!element.getName().equals("Bar"))

		throw new RuntimeException("containment error for: "
				+ element.getName() + " : " + conts
				+ " - add a discriminant <cont> annotation to "
				+ element.getName() + " for each view");
	}

	private boolean inferContainmentAlt_old(DiaContainedElement element,
			List<EReference> containements, String view) { // FP140218
		try {
			EReference eref = null;
			List<EReference> stp = multipleContentionThroughInheritance(element);
					//sterr100Pattern(element);
			if (stp == null)
				return false;
			for (EReference eReference : stp)
				if (eReference != containements.get(0))
					eref = eReference;
			stp.remove(containements.get(0));
			EReference r0 = containements.get(0);
			EReference r1 = eref;
			EClass cl0 = r0.getEContainingClass();
			EClass cl1 = r1.getEContainingClass();
			DiaNode d0 = (DiaNode) parser.findDiagramElement(cl0);
			DiaNode d1 = (DiaNode) parser.findDiagramElement(cl1);
			if (d0.isCanvas(view))
				inferContainments(element, r0, r1);
			else if (d1.isCanvas(view))
				inferContainments(element, r1, r0);
			else {
				parser.cerror("should not happen in inferContainmentAlt");
				return false;
			}
			// inferContainments(element, containements.get(0), eref);
		} catch (Exception e) {
			parser.cerror("(2)err while inferContainmentAlt");
			return false;
		}
		return true;
	}


	private boolean inferContainmentAlt(DiaContainedElement element,
			List<EReference> containements, String view) { // FP140218
		try {
			EReference eref = null;
			List<EReference> stp = multipleContentionThroughInheritance(element);
			if (stp == null)
				return false;
			for (EReference eReference : stp)
				if (eReference != containements.get(0))
					eref = eReference;
			if(eref==null)
				 return false;
			stp.remove(containements.get(0));
			EReference r0 = containements.get(0);
			EReference r1 = eref;
			EClass cl0 = r0.getEContainingClass();
			EClass cl1 = r1.getEContainingClass();
			DiaNode d0 = (DiaNode) parser.findDiagramElement(cl0);
			DiaNode d1 = (DiaNode) parser.findDiagramElement(cl1);
			DiaNode d3 = (DiaNode) element;
			if (d0.isCanvas(view))
				inferContainments(element, r0, r1);
			else if (d1.isCanvas(view))
				inferContainments(element, r1, r0);
			else if(((EClass)d0.getEModelElement()).isSuperTypeOf(((EClass)d3.getEModelElement())))
				inferContainments(element, r1, r0); //FP150517
			else if(((EClass)d1.getEModelElement()).isSuperTypeOf(((EClass)d3.getEModelElement())))
				inferContainments(element, r0, r1);

			else {
				parser.cerror("should not happen in inferContainmentAlt");
				return false;
			}
			// inferContainments(element, containements.get(0), eref);
		} catch (Exception e) {
			parser.cerror("(2)err while inferContainmentAlt");
			return false;
		}
		return true;
	}

	private void inferContainments(DiaContainedElement element,
			EReference containement, EReference containementAlt) {
		element.setContainmentReferenceBase(containement);
		element.setContainmentReferenceAlt(containementAlt);
	}

	private boolean isContainedThroughInheritance_(
			DiaContainedElement element) {
		if (isSelfContained_(element))
			return false;
		List<EReference> sterr100s = multipleContentionThroughInheritance(element);
		return sterr100s != null;
	}

	private boolean isTopNode(DiaContainedElement element, String view) {
		// FP140401aa
		for (EReference eReference : notation.getParser()
				.getDirectContainementReferences(
						(EClass) element.getEModelElement())) {
			// FP140401 for (EReference eReference :
			// element.getDirectContainements()) {
			DiaNode diaNode = parser
					.findNode(((EClass) eReference.eContainer()).getName());
			if (diaNode != null) {

				if (diaNode.isCanvas(view)) {
					if (LOG)
						clog("contained (1) by canvas " + diaNode.getName());
					return true;
				}
			}
		}

		DiaNode canvas = element.getCanvasContainment();
		if (canvas != null) {
			if (LOG)
				clog("contained (2) by canvas " + canvas.getName());
			return true;
		}

		return false;
	}

}
