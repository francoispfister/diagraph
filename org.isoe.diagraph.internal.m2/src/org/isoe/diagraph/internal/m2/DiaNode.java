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

//import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EStructuralFeature;
//import org.eclipse.gmf.mappings.CompartmentMapping;
import org.isoe.diagraph.common.DiaColor;
import org.isoe.diagraph.common.DiaLayout;
import org.isoe.diagraph.generic.GenericConstants;
import org.isoe.diagraph.internal.api.IDiaLabel;
import org.isoe.diagraph.internal.api.IDiaNode;
import org.isoe.diagraph.internal.api.IDiaPointOfView;
import org.isoe.diagraph.internal.api.IDiaSyntaxElement;
import org.isoe.diagraph.diagraph.DGraphElement;
import org.isoe.diagraph.diastyle.DBaseStyle;
import org.isoe.diagraph.internal.m2.api.IDiaContainedElement;
import org.isoe.diagraph.internal.m2.api.IDiaContainment;
import org.isoe.diagraph.internal.m2.api.IDiaEdge;
import org.isoe.diagraph.internal.m2.api.IDiaParser;
import org.isoe.diagraph.internal.m2.parser.DStatement;
import org.isoe.diagraph.internal.m2.parser.DStatement.DCommand_;
import org.isoe.diagraph.lang.DiagraphKeywords;
import org.isoe.diagraph.parser.DiagraphAnnotationParser;
import org.isoe.diagraph.parser.api.IDiagraphAssociation;
import org.isoe.diagraph.parser.api.IDiagraphNode;
import org.isoe.diagraph.parser.api.IDiagraphReferenceAssociation;
import org.isoe.diagraph.parser.api.IDiagraphParser;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.log.LogConfig;

//import org.isoe.diagraph.internal.m2.api.IDiaContainment;

/**
 *
 * @author pfister
 *
 */
public class DiaNode extends DiaContainedElement implements IDiaNode {

	private static final boolean LOG = DParams.DiaNode_LOG;
	// private static final boolean CONTAINMENT_LOG =
	// DParams.Parser_CONTAINMENT_LOG;
	private static final String spaces = "                                                               ";
	private static final boolean CONTAINMENT = true;

	private boolean canvas;
	private boolean diagramRecursive_;

	private EReference declaredContainment_;
	private IDiaContainment container;

	private List<IDiaContainment> sources_ = new ArrayList<IDiaContainment>();

	private List<DiaLink> links = new ArrayList<DiaLink>();
	private List<DiaReference> references = new ArrayList<DiaReference>();
	private String navigation;
	private List<IDiaEdge> edges;
	private List<IDiaNode> embeddedNodes_;
	private List<IDiaNode> embeddingNodes;
	private List<IDiaNode> rootChildren = new ArrayList<IDiaNode>();
	private IDiaPointOfView pointOfView;
	private int depth;
	private boolean mapped;
	private boolean mayBeAContainment;
	private boolean mustRemoveFromTool;

	private IDiagraphParser parser2;
	// private boolean activePointOfView_ = false;

	private DiaColor backgroundcolor = DiaColor.get(DiaColor.WHITE);
	private DiaLayout layout_ = DiaLayout.get(DiaLayout.LIST);
	private DBaseStyle style;
	private int containmentCount; // FP140408
	private List<IDiaContainment> containments_ = new ArrayList<IDiaContainment>();

	private List childReferences0_ = new ArrayList(); // FP140403
	private List nodeMappings0_ = new ArrayList();

	// private List childReferences1_ = new ArrayList(); // FP140403
	private List nodeMappings1_ = new ArrayList();

	// private List compartmentMappings = new ArrayList();
	private Map<String, Object> compartmentMappings = new HashMap<String, Object>();
	private List<IDiaNode> derivedSubNodes = new ArrayList<IDiaNode>();

	private Map<String, EObject> compartments = new HashMap<String, EObject>(); // FP150421
	private int caze;
	private boolean component;

	/*
	 * public List getCompartmentMappings() { return compartmentMappings; }
	 */

	public EClass getSemanticRole() { // FP140114x
		return (EClass) getEModelElement();
	}

	public boolean containmentExists_(EReference ref) {
		if (ref.isContainment()) {
			EClass contner = (EClass) ref.eContainer();
			List<EReference> contrefs = AnnotationUtils.getInstance()
					.getAllDiagraphContainers(contner, view); // FP140402
			// .getDiagraphContainers_(contner, view);
			for (EReference contref : contrefs) {
				if (ref.equals(contref))
					return true;
			}
		}
		return false;
	}

	/*
	 * public List<EReference> getDiagraphedContainments() { EClass
	 * sr=getSemanticRole(); List<EClassifier>
	 * classfs=sr.getEPackage().getEClassifiers(); for (EClassifier eClassifier
	 * : classfs) { if (eClassifier instanceof EClass){ wxc } } return null; }
	 */

	public EReference getDeclaredContainment() {
		return declaredContainment_;
	}

	private List<EClass> getSubTypes(EClass eclass) {
		List<EClass> result = new ArrayList<EClass>();
		EPackage pack = eclass.getEPackage();
		for (Iterator<EObject> contents = pack.eAllContents(); contents
				.hasNext();) {
			EObject el = contents.next();
			if (el instanceof EClass) {
				EClass other = (EClass) el;
				if (eclass.isSuperTypeOf(other) && !result.contains(other)
						&& other != eclass) {
					result.add(other);
				}
			}
		}
		return result;
	}

	@Override
	public List<IDiaNode> getSubNodes() {// FP150430
		List<IDiaNode> result = new ArrayList<IDiaNode>();
		EClass c = (EClass) getEClass();
		EPackage p = c.getEPackage();
		List<EClassifier> cs = p.getEClassifiers();
		for (EClassifier eClassifier : cs) {
			if (eClassifier instanceof EClass) {
				EClass ecl = (EClass) eClassifier;
				if (c != ecl && c.isSuperTypeOf(ecl)) {
					DiaNode dn = parser.findNode(ecl.getName());
					if (dn != null)
						result.add(dn);
				}
			}
		}
		return result;
	}

	/*
	 * public List<DiaNode> getSubNodes2() { // FP140201 List<DiaNode> result =
	 * new ArrayList<DiaNode>(); EClass c = (EClass) getEModelElement(); for
	 * (EClass sub : getSubTypes(c)) { DiaContainedElement el =
	 * parser.findDiagramElement(sub); if (el instanceof DiaNode)
	 * result.add((DiaNode) el); } return result; }
	 */

	// FP140125
	public List<DiaNode> getSubNodes1() {
		List<DiaNode> result = new ArrayList<DiaNode>();
		EClass eclaz = (EClass) getEModelElement();
		List<EClass> subs = subClasses(eclaz);
		if (subs.size() > 0)
			for (EClass subclaz : subs)
				result.add(parser.getNode(subclaz));
		return result;
	}

	public boolean isTopNode() { // FP140131b
		DiaNode container = (DiaNode) getContainmentElement();
		DiaNode canv = getCanvasIfTopNode();
		if (LOG) {
			if (canv != null)
				clog("canvas: " + canv.getName());
		}
		List<EReference> containments = getDiagraphContainments();
		if (LOG)
			for (EReference containment : containments) {
				clog("containment: " + containment.getName());
			}
		if (container == null)
			return false;
		if (container.isOrSubNodeCanvas())
			return true;
		if (getContainmentOnCanvas() != null) // FP140222
			return true;
		return false;
	}

	public EReference getContainmentOnCanvas() {
		for (EClass supertype : ((EClass) getEModelElement())
				.getEAllSuperTypes()) {
			DiaContainedElement superelement = parser
					.findDiagramElement(supertype);
			if (superelement != null && superelement instanceof DiaNode) {
				DiaNode superDiaNode = (DiaNode) superelement;
				if (superDiaNode != null)
					for (EReference eContReference : superDiaNode
							.getAllContainements2())
						if (isCanvasContainment(eContReference))
							return eContReference;
			}
		}

		return null;
	}

	private List<EReference> getDiagraphContainments(EClass eclaz) {
		List<EReference> result = new ArrayList<EReference>();
		DiaContainedElement element = parser.findDiagramElement(eclaz);
		if (element != null && element instanceof DiaNode) {
			DiaNode diaNode = (DiaNode) element;
			if (diaNode != null)
				for (EReference eContReference : diaNode.getAllContainements2()) {

					if (getDiagraphContainment(eContReference) != null
							|| getCanvasContainment(eContReference) != null
							&& !result.contains(eContReference))
						result.add(eContReference);
				}
		}
		return result;
	}

	private List<EReference> getAllContainements2() {
		return parser2.parseAllContainementReferences(getSemanticRole());
	}

	public List<EReference> getDiagraphContainments() {
		List<EReference> result = new ArrayList<EReference>();
		for (EReference r : getDiagraphContainments((EClass) getEModelElement()))
			result.add(r);
		for (EClass supertype : ((EClass) getEModelElement())
				.getEAllSuperTypes()) {
			for (EReference erf : getDiagraphContainments(supertype))
				if (!result.contains(erf))
					result.add(erf);
		}
		return result;
	}

	public DiaNode getCanvasIfTopNode() {
		for (EClass supertype : ((EClass) getEModelElement())
				.getEAllSuperTypes()) {
			DiaContainedElement superelement = parser
					.findDiagramElement(supertype);
			if (superelement != null && superelement instanceof DiaNode) {
				DiaNode superDiaNode = (DiaNode) superelement;
				if (superDiaNode != null)
					for (EReference eContReference : superDiaNode
							.getAllContainements2()) {
						DiaNode dn = getCanvasContainment(eContReference);
						if (dn != null)
							return dn;
					}
			}
		}
		return null;
	}

	public boolean isOrSubNodeCanvas() { // FP140201xxxzzz
		if (isCanvas())
			return true;
		checkCanvasInheritance();
		for (DiaNode sub : getSubNodes1())
			if (sub.isCanvas())
				return true;
		return false;
	}

	public void checkCanvasInheritance() { // FP140201xxxaaa
		if (!isAbstract() && !isCanvas())
			for (DiaNode sub : getSubNodes1())
				if (sub.isCanvas())
					;// throw new RuntimeException(
						// "the canvas should not inheritate from a concrete node !!!");

	}

	private boolean isCanvasContainment(EReference eContReference) {
		DiaContainedElement cel = parser
				.findDiagramElement((EClass) eContReference.eContainer());
		if (cel != null && cel instanceof DiaNode)
			if (((DiaNode) cel).isCanvas())
				return true;
		return false;
	}

	private boolean isCanvasOrSubNodeCanvas1() {// FP140131b
		if (isCanvas())
			return true;
		for (DiaNode sub : getSubNodes1())
			if (sub.isCanvas())
				return true;
		return false;
	}

	// FP140125
	private List<EClass> subClasses(EClass c) {
		List<EClass> result = new ArrayList<EClass>();
		for (EClassifier eClassif : c.getEPackage().getEClassifiers()) {
			if (eClassif instanceof EClass)
				if (!eClassif.equals(c) && c.isSuperTypeOf((EClass) eClassif))
					result.add((EClass) eClassif);

		}
		return result;
	}

	// FP140125 //FP140402
	public EReference getContainerReference() {
		IDiaContainment cont = getContainer();
		if (cont != null) {
			EClass nodeme = (EClass) getEModelElement();
			IDiaNode contsn = cont.getSourceNode();
			EClass contme = (EClass) contsn.getEModelElement();
			List<EReference> refs = contme.getEAllReferences();
			for (EReference contmeref : refs)
				if (contmeref.getEType() == nodeme)
					return contmeref;
		}
		return null;
	}

	public static boolean isDiagraphView(EAnnotation annot, String view) {
		String viewname = org.isoe.diagraph.views.ViewConstants.DIAGRAPH_DEFAULT;
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

	private static EReference parseDeclaredContainmentReference_(EClass ecl,
			String view) {
		EAnnotation annot = getDiagraphAnnotation(ecl, view);
		for (Map.Entry<String, String> entry : annot.getDetails()) {
			if (entry.getKey().startsWith(
					org.isoe.diagraph.lang.DiagraphKeywords.CONTAINMENT_EQU)) {
				String[] pars = entry.getKey().split("=");
				String contname = pars[1];
				pars = contname.split("\\.");
				return (EReference) ((EClass) ecl.getEPackage().getEClassifier(
						pars[0])).getEStructuralFeature(pars[1]);
			}
		}
		return null;
	}

	/*
	 * public IDiaContainedElement findExplicitContainer(){ EReference
	 * ref=parseDeclaredContainmentReference(getSemanticRole(),view); EClass
	 * contnr=(EClass) ref.eContainer(); DiaContainedElement elem =
	 * parser.findDiagramElement(contnr); return elem; }
	 */

	private List<EClass> superClasses(EClass c, List<EClass> viu) {
		List<EClass> result = new ArrayList<EClass>();
		for (EClass eClass : viu)
			if (!eClass.equals(c) && eClass.isSuperTypeOf(c))
				if (!result.contains(c))
					result.add(eClass);
		Collections.reverse(result);
		return result;
	}

	private List<EClass> lowerClasses(EClass c, List<EClass> viu) {
		List<EClass> result = new ArrayList<EClass>();
		for (EClass eClass : viu)
			if (!eClass.equals(c) && c.isSuperTypeOf(eClass))
				if (!result.contains(eClass))
					result.add(eClass);
		return result;
	}

	private List<EClass> getAllClassesOfPackage(EClass root) {
		List<EClass> all = new ArrayList<EClass>();
		for (EClassifier eClassifier : root.getEPackage().getEClassifiers())
			if (eClassifier instanceof EClass)
				all.add((EClass) eClassifier);
		return all;
	}

	private List<EClass> getAllSuperClassesOf(EClass root) {
		return superClasses(root, getAllClassesOfPackage(root));
	}

	private List<EClass> getAllLowerClassesOf(EClass root) {
		return lowerClasses(root, getAllClassesOfPackage(root));
	}

	private List<DiaContainment> getContaimentsInLowerClasses_(EClass claz) {
		List<DiaContainment> result = new ArrayList<DiaContainment>();
		List<EClass> lowers = getAllLowerClassesOf(claz);
		for (EClass lower : lowers) {
			EReference ref = parseDeclaredContainmentReference_(lower, view);
			if (ref != null)
				for (DiaContainment diaContainment : result)
					if (diaContainment.getTargetReference() == ref)
						result.add(diaContainment);
		}
		return result;
	}

	private IDiaContainment superContainment(List<IDiaContainment> containments) {
		IDiaContainment result = null;
		DiaNode pov = (DiaNode) getParser().getPointOfView().getNode();
		List<IDiaContainment> conts = new ArrayList<IDiaContainment>();
		List<EReference> refs = new ArrayList<EReference>();
		EReference erf = null;
		for (IDiaContainment diaContainment : getParser().getContainments()) {

			boolean match1 = diaContainment.getTargetNode().getSemanticRole()
					.isSuperTypeOf(this.getSemanticRole());
			boolean match2 = diaContainment.getTargetNode().getSemanticRole() == this
					.getSemanticRole();

			if (diaContainment.getSourceNode() != pov)
				if (match1 || match2) {
					conts.add(diaContainment);
					erf = diaContainment.getTargetReference();
					if (!refs.contains(erf))
						refs.add(diaContainment.getTargetReference());
				}
		}
		if (refs.size() == 1) {
			for (IDiaContainment diaContainment : conts) {
				if (result == null)
					result = diaContainment;
				if (((EClass) diaContainment.getEModelElement())
						.isSuperTypeOf((EClass) result.getEModelElement()))
					result = diaContainment;
			}
		}
		return result;
	}

	private boolean hasKrefDirect(EClass eModelElement, String view, String name) {
		return parser2.isAnnotated(eModelElement,

		name, IDiagraphAssociation.AssociationType.TYPED_COMPARTMENT,
				IDiagraphParser.InheritanceType.DIRECT);
	}

	private boolean hasKrefIheritance(EClass eModelElement, String view,
			String name) {
		return parser2.isAnnotated(eModelElement,

		name, IDiagraphAssociation.AssociationType.TYPED_COMPARTMENT,
				IDiagraphParser.InheritanceType.INHERITANCE);
	}

	/*
	 * private EReference getKrefAbstractAutoContainment( DiaContainment
	 * diaContainment) { if (this.isAbstract()) {
	 * List<IDiagraphReferenceAssociation> asscs = parser2
	 * .getAllAssociations(diaContainment.getSourceNode() .getSemanticRole(),
	 * CONTAINMENT, view); for (IDiagraphReferenceAssociation association :
	 * asscs) { if (LOG) clog(association.toString()); if
	 * (hasKrefDirect(diaContainment.getSourceNode() .getSemanticRole(), view,
	 * association.getTargetReference() .getName())) if
	 * (getSubTypes(this.getSemanticRole()).contains( association.getSource()))
	 * if (association.getTargetReference().getEType() == this
	 * .getSemanticRole()) return association.getTargetReference(); } } return
	 * null; }
	 */

	private EReference getKrefAutoContainment(DiaContainment diaContainment,
			boolean abstragt) {
		if (abstragt && !this.isAbstract())
			return null;
		List<IDiagraphReferenceAssociation> asscs = parser2
				.getContainmentAssociationsAndSubHierTo(diaContainment
						.getSourceNode().getSemanticRole());
		for (IDiagraphReferenceAssociation association : asscs) {
			// if (LOG)
			// clog(association.toString());
			boolean kdir = hasKrefDirect(diaContainment.getSourceNode()
					.getSemanticRole(), view, association.getTargetReference()
					.getName());
			boolean kinh = hasKrefIheritance(diaContainment.getSourceNode()
					.getSemanticRole(), view, association.getTargetReference()
					.getName());

			if (kdir || kinh) {
				// List<EClass>
				// subs=getSubTypes(diaContainment.getTargetNode().getSemanticRole());
				// for (EClass eClass : subs) {
				// System.out.println(eClass.getName());
				// }

				if (getSubTypes(
						diaContainment.getTargetNode().getSemanticRole())
						.contains(association.getSource()))
					if (association.getTargetReference().getEType() == diaContainment
							.getTargetNode().getSemanticRole())
						return association.getTargetReference();
			}
		}

		return null;
	}

	private List<IDiaContainment> getContainers() {// FP140408b
		// DiaPointOfView pov = (DiaPointOfView)
		// getParser().getPointOfView().getNode();//FP150523
		IDiaNode pov = getParser().getPointOfView().getNode();// FP150523
		;
		if (pov instanceof DiaPointOfView)
			getParser().inferMissingCrefs((DiaPointOfView) pov);
		else if (LOG)
			clog(pov.getName() + " should be a pov !!!!");
		List<IDiaContainment> result = new ArrayList<IDiaContainment>();
		for (IDiaContainment diaContainment : getParser().getContainments()) { // FP140408b{
			if (diaContainment.getSourceNode() != pov)
				if (!diaContainment.getSourceNode().getSemanticRole()
						.isAbstract())
					if (diaContainment.getTargetNode().getSemanticRole()
							.isSuperTypeOf(this.getSemanticRole())) {
						List<EReference> krefacs = getKrefAbstractAutoContainments(diaContainment);
						if (krefacs.isEmpty()
								&& !result.contains(diaContainment))
							result.add(diaContainment);
					}
		}
		return result;
	}

	public IDiaContainment getContainer() {// FP140408b
		boolean check = true;
		if (container == null) {
			List<IDiaContainment> result_ = getContainers();
			if (result_.isEmpty())
				return null;
			if (result_.size() > 1) {
				IDiaContainment superc = superContainment(result_);
				if (superc != null)
					return superc;
			}
			if (result_.size() > 1) { // FP140324xx
				check = true;
			}
			container = result_.get(0);
		}
		return container;
	}

	private List<EReference> getKrefAbstractAutoContainments(
			IDiaContainment diaContainment) {
		List<EReference> result = new ArrayList<EReference>();
		if (this.isAbstract()) {
			List<IDiagraphReferenceAssociation> asscs = parser2
					.getContainmentAssociationsAndSubHierTo(diaContainment
							.getSourceNode().getSemanticRole());
			for (IDiagraphReferenceAssociation association : asscs) {
				if (LOG)
					clog(association.toString());
				if (hasKrefDirect(diaContainment.getSourceNode()
						.getSemanticRole(), view, association
						.getTargetReference().getName()))
					if (getSubTypes(this.getSemanticRole()).contains(
							association.getSource()))
						if (association.getTargetReference().getEType() == this
								.getSemanticRole())
							result.add(association.getTargetReference());
			}
		}
		return result;
	}

	public List<IDiaContainment> getPropagatedContainments_() {
		List<IDiaContainment> result = new ArrayList<IDiaContainment>(); // FP150318t
		for (IDiaContainment diaContainment : containments_)
			if (diaContainment.isPropagated())
				result.add(diaContainment);
		return result;
	}

	// private IDiagraphRunner runner;

	@Override
	public void setBackGroundColor(DiaColor backgroundcolor) {
		this.backgroundcolor = backgroundcolor;
	}

	@Override
	public void setLayout(DiaLayout layout) {
		this.layout_ = layout;
		// clog("setLayout "+this.getName()+"="+layout.getLiteral());
	}

	@Override
	public DiaColor getBackGroundColor() {
		return this.backgroundcolor;
	}

	@Override
	public DiaLayout getLayout() {
		// clog("getLayout "+this.getName()+"="+layout.getLiteral());
		return this.layout_;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public List<IDiaNode> getRootChildren() {
		if (!isOrSubNodeCanvas()) // FP140201
			// if (!isCanvas())
			throw new RuntimeException("only canvas have root children !!!");
		return rootChildren;
	}

	public List<IDiaNode> getEmbeddedNodes() {
		boolean check;
		if (embeddedNodes_ == null) {
			embeddedNodes_ = new ArrayList<IDiaNode>();
			for (IDiaContainment containment : containments_) {
				if (containment.isPropagated())
					check = true;
				if (containment.getTargetNode() == null)
					if (LOG)
						clog("containment.getTargetNode() == null  for containment "
								+ containment.getName()); // FP140201zzz
				if (containment.getTargetNode() != null)
					embeddedNodes_.add(containment.getTargetNode());
			}
		}
		return embeddedNodes_;
	}

	public List<IDiaNode> getContainmentSources() {
		if (embeddingNodes == null) {
			embeddingNodes = new ArrayList<IDiaNode>();
			for (IDiaContainment containment2 : sources_) {
				embeddingNodes.add(containment2.getSourceNode());
				if (LOG)
					clog(containment2.toString()); // FP140329a
			}
			// chercher les autres conteneurs. modèles de test=casebf32
			/*
			 * [Containment name:subExpressions_Cccc
			 *
			 * SourceNode= Cccc where containmentBase=cs SourceNode= Cccc where
			 * containmentAlt= null TargetNode= Cccc - containment=cs
			 * figureEmbedded= fig embedded propagated= not propagated
			 * TargetName= subExpressions_Cccc TargetReference=
			 * subExpressions_Cccc Containment]
			 *
			 * [Containment name:bars
			 *
			 * SourceNode= Foo where containmentBase=foos SourceNode= Foo where
			 * containmentAlt= null TargetNode= Bar - containment=bars
			 * figureEmbedded= fig embedded propagated= not propagated
			 * TargetName= bars TargetReference= bars Containment]
			 */

		}
		return embeddingNodes;
	}

	public List<DiaLink> getDiaLinks() {
		return links;
	}

	private DiaNode(IDiaParser owner, String view, List<String> slabels,
			String elementName, EModelElement element, String containmentName,
			EReference containmentBaseref, EReference containmentAltref,
			List<DiaLabel> labels) {
		super(owner, element, view, slabels);
		parser2 = owner.getParser2();// DiagraphAnnotationParser.getInstance_(null,this);
		this.setContention_(containmentName);
		this.setContainmentReferenceBase(containmentBaseref);
		this.setContainmentReferenceAlt(containmentAltref);
		this.setElementName(elementName);

		// in super this.setLabel(label);// FP121007
		// setDefaultName(defaultName);
		this.setOwnLabels(labels);
	}

	public DiaNode(IDiaParser owner, String view, EModelElement element) {
		super(owner, element, view, null); // FP130121
		parser2 = owner.getParser2();
	}

	public static DiaNode cloneNode(IDiaParser owner, String view,
			DiaContainedElement delement) {
		return new DiaNode(owner,
				view,
				delement.getLabels(),// delement.getDefaultName(),
				delement.getElementName(), delement.getEModelElement(),
				delement.getContention_(),
				delement.getContainmentReferenceBase(),
				delement.getContainmentReferenceAlt(), delement.getOwnLabels());
	}

	public String toString() {
		String cont1 = "";
		for (IDiaContainment c1 : containments_) {
			cont1 += " containment: " + c1.toString() + " ";
		}

		String cont2 = "";
		for (IDiaContainment c2 : sources_)
			cont2 += " source: " + c2.toString() + " ";

		String result = "[" + this.getClass().getSimpleName() + "] ";
		result += super.toString(); // FP150329
		result += (isCanvas() ? " canvas" : " ") // FP140201
				+ (diagramRecursive_ ? " recursive" : " ") + "\n"
				+ cont1
				+ "\n" + cont2;
		result += "\n";
		for (DiaReference ref : references) {
			result += ref.toString() + "\n";
		}
		return result;
	}

	public List<DiaReference> getReferences() {
		return references;
	}

	public void addReference(DiaReference r) {
		references.add(r);
	}

	public List<IDiaContainment> getSources() {
		if (LOG)
			for (IDiaContainment c : sources_)
				clog(c.getName());
		return sources_;
	}

	public List<IDiaContainment> getContainments() {
		// if (LOG)
		// for (IDiaContainment c : containments)
		// clog(c.getName());
		return containments_;
	}

	public void setCanvas_(boolean canvas) {
		this.canvas = canvas;
	}

	@Override
	public boolean isCanvas() {
		return canvas;
	}

	public void parserAddContainment1(DiaContainment containment) {
		/*
		 * EReference
		 * dcont=parser2.getContainment(containment.getSourceNode().getSemanticRole
		 * (),view); if (LOG && dcont!=null) clog(dcont.getName());
		 */
	}

	public void logContainment(int sender) {
		containmentCount++;
		// if (DParams.Parser_CONTAINMENT2_LOG__)
		// System.out.println("<"+sender+"> "+getName()+" addContainment ["+containments+"]");
	}

	/*
	 * private void addContainment2_nu(int sender, IDiaContainment containment,
	 * int order, EReference erf, boolean doAdd) { boolean result = true;
	 * boolean check; if (sender == 6) check = true; declaredContainment_ =
	 * null; EClass src = containment.getSourceNode().getSemanticRole();
	 *
	 * if (containment.getTargetReference() == null)
	 * containment.setTargetReference(erf); // FP150329a114
	 *
	 * if (DParams.Parser_CONTAINMENT2_LOG && doAdd) clog2("<" + sender +
	 * "> addContainment src=" + src.getName() + " [" + containmentCount + "]");
	 *
	 * IDiaNode srcNode = containment.getSourceNode(); if (doAdd)
	 * srcNode.logContainment(sender); // FP140408 IDiagraphNode diagraphNode =
	 * parser2.getDiagraphNode(src, 0); if (DParams.Parser_CONTAINMENT2_LOG)
	 * clog2("setDeclaredContainment(1) for " + diagraphNode.getName() + " ->" +
	 * containment.getTargetReference().getName()); // FP140417
	 *
	 * EReference targ = containment.getTargetReference();
	 *
	 * result = diagraphNode.setDeclaredContainment(src, containment
	 * .getSourceNode().getContention_()); // FP140408
	 *
	 * result = setDeclaredContainment_(containment);
	 *
	 * containment.setOrder(order); // FP2512 //FP121224x
	 *
	 * if (doAdd) containments_.add(containment); // FP140407zzz }
	 */
	@SuppressWarnings("unused")
	public void addContainment(int sender, IDiaContainment containment,
			int order, boolean doAdd) {

		// ;
		boolean result = true;
		boolean check;
		if (sender == 5)// createRecursiveCompartment
			check = true;

		if (containment.getTargetNode() == null)
			check = true;

		declaredContainment_ = null;
		EClass src = containment.getSourceNode().getSemanticRole();
		if (DParams.Parser_CONTAINMENT2_LOG && doAdd)
			clog2("<" + sender + "> addContainment src=" + src.getName() + " ["
					+ containmentCount + "]");

		IDiaNode srcNode = containment.getSourceNode();
		if (doAdd)
			srcNode.logContainment(sender); // FP140408
		IDiagraphNode diagraphNode = parser2.getDiagraphNode(src, 0);
		if (DParams.Parser_CONTAINMENT2_LOG)
			clog2("setDeclaredContainment(2) for " + diagraphNode.getName()
					+ " ->" + containment.getTargetReference().getName()); // FP140417

		EReference targ = containment.getTargetReference();

		result = diagraphNode.setDeclaredContainment(src, containment
				.getSourceNode().getContention_()); // FP140408

		result = setDeclaredContainment_(containment);

		containment.setOrder(order); // FP2512 //FP121224x
		if (LOG) {
			String log = containment.getSourceNode().getName()
					+ "--"
					+ containment.getName()
					+ "("
					+ targ.getName()
					+ ")"
					+ (containment.getTargetNode() == null ? " no target"
							: ("->" + containment.getTargetNode().getName()));
			clog(" containment " + log);
		}

		if (doAdd)
			containments_.add(containment); // FP140407zzz
	}

	private void clog2(String mesg) {
		if (DParams.Parser_CONTAINMENT2_LOG)
			System.out.println(mesg);
	}

	public int getContainmentCount() { // FP140408
		return containmentCount;
	}

	private boolean setDeclaredContainment_(IDiaContainment containment) {
		int caze_ = 0;
		EClass src = containment.getSourceNode().getSemanticRole();
		String declaredContention_ = containment.getSourceNode()
				.getContention_();
		// EReference targetref=containment.getTargetReference();
		String declaredContentionClass = (declaredContention_ == null ? null
				: declaredContention_.substring(0,
						declaredContention_.indexOf(".")));

		if (declaredContentionClass != null) {
			caze_ = 2;
			boolean inherited = false;
			if (!containment.getSourceNode().getName()
					.equals(declaredContentionClass)) {
				EClass declaredContainmentClass = (EClass) src.getEPackage()
						.getEClassifier(declaredContentionClass);

				if (declaredContainmentClass == null)
					throw new RuntimeException(declaredContentionClass
							+ " supposed to contain " + getName()
							+ " does not exist");
				// FP140421

				if (declaredContainmentClass.isSuperTypeOf(src)) {
					inherited = true;
					caze_ = 3;
				}
			}
			List<EReference> srcrefs1 = inherited ? src.getEAllReferences()
					: AnnotationUtils.getInstance().getAllDiagraphContainers(
							src, view);
			for (EReference srcref1 : srcrefs1) {
				if (srcref1.isContainment()
						&& ((EClass) srcref1.eContainer()).getName().equals(
								declaredContentionClass)) {
					declaredContainment_ = srcref1;
					caze_ = 4;
					break;
				}
			}
			if (declaredContainment_ == null) {
				List<EReference> srcrefs2 = AnnotationUtils.getInstance()
						.getAllDiagraphContainers(src, view);
				for (EReference srcref2 : srcrefs2) {
					if (srcref2.isContainment()
							&& ((EClass) srcref2.eContainer()).getName()
									.equals(declaredContentionClass)) {
						declaredContainment_ = srcref2;
						caze_ = 5;
						break;
					}
				}
			}
			if (declaredContainment_ != null) {
				if (!(((EClass) (declaredContainment_.eContainer())).getName()
						+ "." + declaredContainment_.getName())
						.equals(declaredContention_))
					declaredContainment_ = null;
			}

			if (declaredContainment_ == null) {
				List<EClass> supes = parser2.getSuperGenericsOrNode(src, false);
				for (EClass supe : supes) {
					List<EReference> refs = AnnotationUtils.getInstance()
							.getAllDiagraphContainers(supe, view);
					for (EReference srcref : refs) {
						if (srcref.isContainment()
								&& ((EClass) srcref.eContainer()).getName()
										.equals(declaredContentionClass)) {
							declaredContainment_ = srcref;
							caze_ = 6;
							break;
						}
					}

				}
			}
		}
		boolean result = (declaredContentionClass == null)
				|| (declaredContainment_ != null && caze_ > 0);

		if (LOG)
			clog("declaredContainment for "
					+ this.getName()
					+ "="
					+ (declaredContainment_ != null ? declaredContainment_
							.getName() : "null") + " caze=" + caze); // FP150526kkkk
		return result;

	}

	public void addSource(DiaContainment src) {
		if (DParams.Parser_29_LOG)
			clog29("addSource " + this.getName() + "[" + src.getName() + "]");
		sources_.add(src);
	}

	private void clog29(String mesg) {
		if (DParams.Parser_29_LOG)
			System.out.println(mesg);
	}

	public void addLink(DiaLink l) {
		links.add(l);
	}

	@Override
	public void setMapped(boolean mapped) {
		this.mapped = mapped;
		isMapped();
	}

	public boolean isMapped() {
		if (LOG)
			if (mapped) {
				clog(this.hashCode() + "-" + this.getName() + " is mapped");

			} else
				clog(this.hashCode() + "-" + this.getName() + " is not mapped");
		return mapped;
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);

	}

	public void markAsContainment() {
		mayBeAContainment = true;
	}

	public boolean containmentMark() {
		return mayBeAContainment;
	}

	public void removeFromTool_() {
		mustRemoveFromTool = true;
	}

	public boolean mustRemoveFromTool() {
		return mustRemoveFromTool;
	}

	/*
	 * public void setActivePointOfView(boolean value) { activePointOfView_ =
	 * value; }
	 *
	 * public boolean isActivePointOfView() { return activePointOfView_; }
	 */

	public void setPointOfView(IDiaPointOfView pointOfView) {
		int ph = parser.getPhase();
		/*
		 * if (getName().equals("PublicationStructure")) clog("DBNT-DNSPOV-" +
		 * ph + " - " + getName()); // FP120926 if
		 * (getName().equals("PublicationSystem")) clog("DBNT-DNSPOV-" + ph +
		 * " - " + getName()); // FP120926
		 */
		this.pointOfView = pointOfView;
	}

	public IDiaPointOfView getPointOfView() {
		return pointOfView;
	}

	@Override
	public String getView() {
		return view;
	}

	public void resolveDiagramRecursion_() {
		if (isCanvas() && getContainmentReferenceBase() != null) // FP140201
			diagramRecursive_ = true;
	}


	public void resolveCompositePattern() {
		detectComposite();
	}

	private void detectComposite() {
		if (isCanvas()) {
			int count = 0;
			EClass cn = null;
			EClass nc = null;
			EClass r = null;
			List<DiaNode> allnods = parser.getAllNodes();
			for (DiaNode diaNode : allnods) {
				nc = diaNode.getSemanticRole();
				if (nc != null) {
					EClass c = getEClass();
					if (nc.isAbstract() && nc.isSuperTypeOf(c)
							&& contains(c, nc)) {
						if (cn == null || cn != c) {
							r = nc;
							count++;
						}
						cn = c;
					}
				}
			}
			if (count > 1)
				throw new RuntimeException(
						"should not happen in detectComposite");
			else if (LOG && count == 1)
				clog("pattern composite found " + r.getName() + " - "
						+ getName());//FP150531voiraz
			if (count == 1) {
				for (DiaNode diaNode : allnods) {
					EClass claz = diaNode.getSemanticRole();
					if (claz == r)
						diaNode.setComponent();

				}
			}
		}

	}

	private boolean contains(EClass k, EClass c) {
		List<EStructuralFeature> sfs = k.getEStructuralFeatures();
		for (EStructuralFeature eStructuralFeature : sfs) {
			if (eStructuralFeature instanceof EReference) {
				EReference erf = (EReference) eStructuralFeature;
				if (erf.getEType() == c && erf.isContainment())
					if (parser.isCref(erf))
						return true; // FP150531voir
			}
		}
		return false;
	}

	public void addRootChild(DiaNode node) {
		if (!isOrSubNodeCanvas()) // FP140201
			throw new RuntimeException("only canvas have root children !!!");
		// assert isCanvas():"should be canvas to have root children !!!";
		rootChildren.add(node);
	}

	// TODO FP2910
	@Override
	public void resolveTargetRef() {
		super.resolveTargetRef();
		for (DiaReference r : references)
			r.resolveTargetRef();
		for (IDiaContainment c : containments_)
			c.resolveTargetRef();
	}

	public void logContainments() {
		if (LOG)
			for (IDiaContainment c : containments_)
				clog(">>containment: " + c.toString());
	}

	public String logReferences() {
		String result = "";
		for (DiaReference reference : references) {
			// if (reference.getName().equals("bars")&&
			// reference.getSourceNode().getName().equals("Foo"))tb = true;
			if (reference.targetReference == null)
				result += "\n!!! targetReference should have a value for "
						+ reference.getSourceNode().getName() + "."
						+ reference.getName();
		}
		return result;
	}

	public boolean checkReferences() {
		for (DiaReference reference : references)
			if (reference.targetReference == null)
				return false;
		return true;
	}

	public void resolveReferenceTargetNodes() {
		for (DiaReference reference : references) {
			if (reference.targetReference == null)
				if (LOG)
					clog(" targetReference should have a value for "
							+ reference.getSourceNode().getName() + "."
							+ reference.getName());
			try {
				reference.resolveTargetNode_();
			} catch (Exception e) {
				throw new RuntimeException(e); // FP120513
			}

		}
	}

	public void resolveLinkSourceNodes() {
		for (DiaLink link : links) {
			if (link.getSourceReference() == null)
				if (LOG)
					clog(" sourceReference should have a value for "
							+ link.getName());
			try {
				link.resolveSourceReference_();
			} catch (Exception e) {
				throw new RuntimeException(e); // FP120513
			}
		}
	}

	public List<EReference> getContainmentReferences() {
		List<EReference> result = new ArrayList<EReference>();
		EClass eclaz = (EClass) getEModelElement();
		EList<EReference> crefs = eclaz.getEAllReferences();
		for (EReference cref : crefs) {
			if (cref.isContainment())
				result.add(cref);
		}
		return result;
	}

	private boolean crefExists(EReference cref) {
		for (IDiaContainment c : containments_)
			if (c.getTargetReference() == cref)
				return true;
		return false;
	}

	public void resolveContainmentSourceAndTargetNodes_() {
		;
		for (IDiaContainment c : containments_)
			if (c.getTargetNode() == null)
				c.resolveTargetNode();
			else
				c.logTargetNode();
		for (IDiaContainment c : containments_)
			c.resolveSourceNode();

		for (IDiaContainment c : containments_) {
			c.resolveTargets();
		}
	}

	private void replaceTargetAbstractByConcrete_nu() {
		;
		boolean existAbstract = false;
		for (IDiaContainment c : containments_) {
			if (c.getTargetNode().isAbstract())
				existAbstract = true;

		}

	}

	void f() {
		if (DParams.Parser_CONTAINMENT2_LOG)
			clog2("derived for " + getName() + " ?");
		List<DiaNode> subnodes = getSubNodes1();

		if (DParams.Parser_CONTAINMENT2_LOG) {
			if (!subnodes.isEmpty()) {
				String subs = "";
				for (DiaNode subnode : subnodes)
					subs += subnode.getName() + " ";
				clog2("has " + subnodes.size() + " subnodes: " + subs);
			} else
				clog2("has no  subnodes ");
		}
		if (isAbstract()) {
			if (DParams.Parser_CONTAINMENT2_LOG)
				clog2("node.isAbstract " + getName());
			for (DiaNode subnode : subnodes) {
				if (subnode.isAbstract()) {
					if (DParams.Parser_CONTAINMENT2_LOG)
						clog2("\ncreateDerivedContainments(1) for abstract "
								+ subnode.getName() + " ?");
					if (LOG)
						clog("DACTN: AC " + subnode.logNode());

					createDerivedContainments1_(subnodes, subnode);
				} else {
					if (DParams.Parser_CONTAINMENT2_LOG)
						clog2("subnode " + subnode.getName()
								+ " is not abstract");
				}

			}
		} else {
			if (DParams.Parser_CONTAINMENT2_LOG)
				clog2("no derived for " + getName() + " !");
			if (LOG)
				clog("CDCTN: NAC " + logNode());
		}
	}

	private void createDerivedContainments1_(List<DiaNode> subnodes,
			DiaNode subnode) {
		if (DParams.Parser_CONTAINMENT2_LOG)
			for (DiaNode diaNode : subnodes)
				clog2("TODO createDerivedContainments1 " + diaNode.getName()
						+ " !");
	}

	public String logNode() {
		String result = "--Node " + getName() + "--";
		for (IDiaContainedElement supel : getSuperElements())
			result += "SuperElement:" + supel.getName() + "\n";
		if (getInheritedContainmentsBase().size() == 0) // FP2611
			result += "no InheritedContainment base: for " + getName() + "\n";
		else
			for (EModelElement inhercont : getInheritedContainmentsBase()) {
				assert inhercont instanceof EReference;
				result += "InheritedContainment:"
						+ ((EReference) inhercont).getName() + "\n";
			}
		if (getContainments().size() > 0)
			result += getContainments().size() + " containments" + "\n";
		return result;
	}

	public void logNode___(DiaNode node) {
		clog("--logNode for " + node.getName() + "--");
		for (IDiaContainedElement supel : node.getSuperElements())
			clog("SuperElement:" + supel.getName());
		if (node.getInheritedContainmentsBase().size() == 0) // FP2611
			clog("no InheritedContainment base: for " + node.getName());
		else
			for (EModelElement inhercont : node.getInheritedContainmentsBase()) {
				assert inhercont instanceof EReference;
				clog("InheritedContainment:"
						+ ((EReference) inhercont).getName());
			}

		EReference contn_ = node.getContainmentReferenceBase();
		// assert contn instanceof EReference;
		if (contn_ == null) {
			clog("node.getContainmentReferenceBase == null for "
					+ node.getName());
			if (node.getInheritedContainmentsBase().size() == 0)
				clog("node " + node.getName()
						+ " has no containment references !!!!");
		} else
			clog("Containment base:" + ((EClass) contn_.eContainer()).getName()
					+ "." + ((EReference) contn_).getName());

		EReference contalt = node.getContainmentReferenceAlt();
		// assert contn instanceof EReference;
		if (contalt == null) {
			clog("node.getContainmentReferenceAlt == null for "
					+ node.getName());

		} else
			clog("Containment alt:" + ((EClass) contalt.eContainer()).getName()
					+ "." + ((EReference) contalt).getName());

		if (node.getContainments().size() > 0)
			clog(node.getContainments().size() + " containments");
	}

	public void resolveMissingCRefsIfCanvas() { // FP121224y
		if (isCanvas()) {// FP140201
			for (EReference cref : getContainmentReferences()) {
				if (!crefExists(cref)) {
					boolean propagated = false;// isInherited(cref);//
												// FP130619ya
					IDiaContainment dn = createMissingCRef(cref, propagated); //FP150601
					//addContainment
					if (LOG)
						clog("missing cref created " + dn.getName());
				}
			}
		}
	}

	/*
	 * private boolean isInherited(EReference cref) { // FP130619ya return
	 * false; }
	 */

	private IDiaContainment createMissingCRef(EReference cref,
			boolean propagated) { // FP121224z
		if (LOG)
			clog("createMissingCRef: " + cref.getName());
		// EModelElement element_= (EClass)cref.eContainer();
		// String target_= cref.getName();
		// String name_= cref.getName();
		IDiaContainment result = parser.createMissingCReference(cref,
				propagated);
		// result.setTargetReference(cref);// FP121224zz
		// result.setTargetNode(null);//xxx
		return result;
	}

	public boolean isDiagramRecursive() {
		return diagramRecursive_;
	}

	public DiaReference findReference(String name) {
		for (DiaReference ref : references) {
			if (ref.getTargetName().equals(name))
				return ref;
		}
		return null;
	}

	public IDiaContainment findContainmentByTargetRefName(String name) {
		for (IDiaContainment c : containments_) {
			if (c.getNameIfCompartment().equals(name))
				return c;
		}
		return null;
	}

	public IDiaContainment findContainmentbyName(String name) {
		for (IDiaContainment c : containments_)
			if (c.getName().equals(name))
				return c;
		return null;
	}

	public IDiaContainment findContainmentByTargetName(String nodename,
			String name) { // FP150516z
		for (IDiaContainment c : containments_)
			if (c.getName().equals(name)
					&& c.getTargetNode().getName().equals(nodename))
				return c;
		return null;
	}

	public IDiaContainment findContainmentByName(String sourceName,
			String name, String targetName) {
		for (IDiaContainment c : containments_)
			if (c.getTargetNode().getName().equals(targetName)
					&& c.getSourceNode().getName().equals(sourceName)
					&& c.getTargetReference().getName().equals(name))
				return c;
		return null;
	}

	public DiaLink findLinkByTargetName(String name) {
		for (DiaLink l : links)
			if (l.getName().equals(name))
				return l;
		return null;
	}

	public EReference inferContainment(List<DiaContainedElement> diagramElements) {
		int containmentcount = 0;
		for (DiaContainedElement other : diagramElements)
			for (EReference otherclazref : ((EClass) other.getEModelElement())
					.getEAllReferences()) {
				EClass claz = (EClass) this.getEModelElement();
				EClass othclaz = (EClass) otherclazref.getEType();
				if (othclaz.isSuperTypeOf(claz)) {
					EClass containing = otherclazref.getEContainingClass();
					if (otherclazref.isContainment()) {
						containmentcount++;
						if (containmentcount == 1) {
							this.setContainmentReferenceBase(otherclazref);
							if (LOG)
								clog(containing.getName() + "-("
										+ otherclazref.getName() + ")->"
										+ claz.getName());
						} else if (LOG)
							clog("????  containmentcount" + containmentcount
									+ "  -   " + containing.getName() + "-("
									+ otherclazref.getName() + ")->"
									+ claz.getName());
					}
				}
			}
		if (containmentcount > 1)
			throw new RuntimeException(this.getName()
					+ ": multiple containments should not happen !");
		return (EReference) getContainmentReferenceBase();
	}

	public void setStyle(DBaseStyle dGraphStyle) {
		this.style = dGraphStyle;
	}

	public DBaseStyle getStyle() {
		return style;
	}

	@Override
	public String getNavigation() {
		return navigation;
	}

	public void setNavigation(String navigation) {
		this.navigation = navigation;
	}

	@Override
	public void addInferredLabel(IDiaLabel lb) {
		// TODO
	}

	@Override
	public boolean isCanvas(String view) {
		String tn = getName();
		for (EAnnotation eAnnotation : ((EClass) getEModelElement())
				.getEAnnotations()) {
			if (eAnnotation.getSource().equals(
					GenericConstants.ANNOT_DIAGRAPH_LITTERAL)) {
				parseAnnotationDetails(eAnnotation);
				if (isView(eAnnotation, view)) {
					if (isPov(eAnnotation))
						return true;
				}
			}
		}
		return false;
	}

	private boolean isPov(EAnnotation eAnnotation) {
		for (Map.Entry<String, String> entry : eAnnotation.getDetails()) {
			if (entry.getKey().equals("pov"))
				return true;
			/*
			 * clog( entry.getKey() + (entry.getValue() != null &&
			 * !entry.getValue().isEmpty() ? "->" + entry.getValue() : ""));
			 */
		}
		return false;
	}

	private boolean isDefaultView(EAnnotation anot) {
		for (Map.Entry<String, String> entry : anot.getDetails())
			if (entry.getKey().startsWith("view="))
				return false;
		return true;
	}

	private boolean isView(EAnnotation anot, String view) {
		if (view == null || view.equals("default") || view.isEmpty())
			return isDefaultView(anot);
		for (Map.Entry<String, String> entry : anot.getDetails())
			if (entry.getKey().equals("view=" + view))
				return true;
		return false;
	}

	private void parseAnnotationDetails(EAnnotation anot) {
		for (Map.Entry<String, String> entry : anot.getDetails()) {
			// parse(entry.getKey());
			clog(entry.getKey()
					+ (entry.getValue() != null && !entry.getValue().isEmpty() ? "->"
							+ entry.getValue()
							: ""));
		}
	}

	@Override
	public boolean isDirectCanvas(String view) {
		// String tn = getName();
		for (EAnnotation eAnnotation : ((EClass) getEModelElement())
				.getEAnnotations()) {
			if (eAnnotation.getSource().equals(
					GenericConstants.ANNOT_DIAGRAPH_LITTERAL)) {
				parseAnnotationDetails(eAnnotation);
				if (isView(eAnnotation, view)) {
					if (isPov(eAnnotation))
						return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean isCanvas(EClass eclass, String view) {
		for (EAnnotation eAnnotation : eclass.getEAnnotations()) {
			if (eAnnotation.getSource().equals(
					GenericConstants.ANNOT_DIAGRAPH_LITTERAL)) {
				parseAnnotationDetails(eAnnotation);
				if (isView(eAnnotation, view)) {
					if (isPov(eAnnotation))
						return true;
				}
			}
		}
		return false;
	}

	public void addNodeMapping(Object nodeMapping, String nodeMappingName) { // FP140417
		if (DParams.Parser_CONTAINMENT2_LOG) {
			if (nodeMappings1_.contains(nodeMapping))
				clog2("allready added " + nodeMappingName);
		}
		if (!nodeMappings1_.contains(nodeMapping)) {
			nodeMappings1_.add(nodeMapping);
			if (DParams.Parser_CONTAINMENT2_LOG)
				clog2("addNodeMapping " + nodeMappingName + " to " + getName()
						+ " count=" + nodeMappings1_.size());
		}
	}

	public void addMapping(Object nodeMapping, Object childReference, int sender) { // FP140417
		childReferences0_.add(childReference);
		nodeMappings0_.add(nodeMapping);// <" + sender + ">
		if (DParams.Parser_CONTAINMENT2_LOG)
			clog2("<" + sender + ">" + "addNodeMapping to " + getName()
					+ " count=" + nodeMappings0_.size());
	}

	@Override
	public IDiaNode getDerivedSubNode(String name) {
		for (IDiaNode sub : derivedSubNodes)
			if (sub.getName().equals(name))
				return sub;
		return null;
	}

	@Override
	public List<IDiaNode> getDerivedSubNodes() {
		return derivedSubNodes;
	}

	@Override
	public boolean hasDerivedSubNode() {
		return !derivedSubNodes.isEmpty();
	}

	// @Override
	public void addDerivedSubNodes(List<? extends IDiaNode> subnodes) {
		for (IDiaNode derived : subnodes) {
			if (DParams.Parser_CONTAINMENT2_LOG)
				clog2("addDerivedSubNode " + derived.getName());
			if (!derivedSubNodes.contains(derived)) {
				derivedSubNodes.add(derived);
			}
		}
	}

	@Override
	public List getNodeMappings_() { // FP140408
		return nodeMappings0_;
	}

	@Override
	public List getChildReferences_() {
		return childReferences0_;
	}

	public String toLog() {
		return toString();
	}

	public EObject getCompartment_(String compartmentName) {
		return compartments.get(compartmentName);
	}

	public void setCompartment_(String compartmentName, EObject compartment) {
		compartments.put(compartmentName, compartment); // aliases
	}

	public Object getCompartmentMapping(String name, int level) { // FP150428
		return compartmentMappings.get(name + "_" + level);
	}

	public void addCompartmentMapping(Object compartmentMapping, String name,
			int level) {// FP150428
		compartmentMappings.put(name + "_" + level, compartmentMapping);
		/* compartmentMappings.add(compartmentMapping); */
		if (DParams.Parser_CONTAINMENT2_LOG)
			clog2("addCompartmentMapping depth=" + level + " to " + getName()
					+ " count=" + compartmentMappings.size());
	}

	public int getCompartmentMappingSize() {
		return compartmentMappings.size();
	}

	@Override
	public EClass getEClass() {
		return this.getSemanticRole();
	}

	public void setCase(int caze) {
		this.caze = caze;
	}

	@Override
	public int getCase() {
		return caze;
	}

	@Override
	public EModelElement getEModelElement() {
		return eModelElement;
	}

	public IDiaContainment getContainment_(
			IDiagraphAssociation.AssociationType containmentType_,
			DiaNode trgnode, EReference er, String name, boolean isPropagated,
			boolean derived) {
		IDiaContainment nodecont = findContainmentByTargetRefName(trgnode
				.getName());
		if (true)
			throw new RuntimeException("TODO see getContainment"); // FP150517
		EClass srcclass = (EClass) getEModelElement();
		if (nodecont == null) {
			nodecont = parser.createContainment_(containmentType_, srcclass,
					trgnode.getEClass(), trgnode.getName(), name, isPropagated,
					derived);// isPort,
			nodecont.setTargetReference(er);
			nodecont.setTargetNode(trgnode);
		}
		return nodecont;
	}

	public boolean isComponent() {
		if (LOG)
			 clog(getName()+" is component ? "+(this.hashCode())+" "+(component?"compo":"not compo"));
		return component;
	}

	private void setComponent() {
		if (LOG)
			 clog(getName()+" is component "+(this.hashCode()));
		component = true;
	}



}
