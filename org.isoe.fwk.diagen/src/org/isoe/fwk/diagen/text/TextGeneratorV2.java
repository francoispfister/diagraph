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
package org.isoe.fwk.diagen.text;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.isoe.diagraph.diagraph.DAffixedEdge;
import org.isoe.diagraph.diagraph.DCompartmentEdge;
import org.isoe.diagraph.diagraph.DLabel;
import org.isoe.diagraph.diagraph.DOwnedEdge;
import org.isoe.diagraph.diagraph.DEdge;
import org.isoe.diagraph.diagraph.DGeneric;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.diagraph.DGraphElement;
import org.isoe.diagraph.diagraph.DNavigationEdge;
import org.isoe.diagraph.diagraph.DNestedEdge;
import org.isoe.diagraph.diagraph.DLabeledEdge;
import org.isoe.diagraph.diagraph.DLabeledElement;
import org.isoe.diagraph.diagraph.DNode;
import org.isoe.diagraph.diagraph.DPointOfView;
import org.isoe.diagraph.diagraph.DReference;
import org.isoe.diagraph.diagraph.DSimpleEdge;
import org.isoe.diagraph.interpreter.InterpreterPlugin;
import org.isoe.diagraph.megamodel.Dsml;
import org.isoe.diagraph.megamodel.EcoreDiagram;
import org.isoe.diagraph.megamodel.Megamodel;
import org.isoe.diagraph.megamodel.Notation;
import org.isoe.extensionpoint.diagen.IDiagraphAlphabet;
import org.isoe.extensionpoint.diagen.IHandleMegamodelJob;
import org.isoe.extensionpoint.diagen.ITextGenerator;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.diagen.DiagenPlugin;

/**
 *
 * @author fpfister Handmade... TODO use xpand
 */
public class TextGeneratorV2 implements IDiagraphAlphabet, ITextGenerator {

	// static final int LEFT_PARENT_ = 0;
	// static final int RIGHT_PARENT_ = 1;
	static final DecimalFormat LINE_FORMATER = new DecimalFormat("###");
	private static final String LINE_PREFIX = "###";
	private static final String CR_ = "\n";
	private static final String I1 = "    ";
	private static final String COLON = " : ";

	private static final String I2 = I1 + I1;
	// private static final String SEP_EQU_SPS = SEP_SPACE + SEP_EQU +
	// SEP_SPACE;
	private static final String SEP_COMMA_SPS = SEP_SPACE + SEP_COMMA
			+ SEP_SPACE;
	private static final String UNDERSCORE_DIAGRAPH_VOCAB_ROOT = SEP_UNDERSCORE
			+ DIAGRAPH_VOCAB_ROOT;

	// private static final String KW_PROPAGATED = "_propagated";

	private static final boolean LOG_ = DParams.TextGenerator2_LOG;
	private static final boolean LOG2 = DParams.TextGenerator22_LOG;
	private static final boolean LINE_NUMBERED = true;

	private int linecount;
	private int startConcreteLine;

	private List<IProject> dones;

	private List<String> keywwords = new ArrayList<String>();
	private List<String> separators = new ArrayList<String>();
	private List<String> glossary = new ArrayList<String>();

	private IHandleMegamodelJob megamodelHandler;
	private boolean DISPLAY_PROPAGATED;
	private int linecounter;

	private void clog(String mesg) {
		if (LOG_)
			System.out.println(mesg);
	}

	public TextGeneratorV2(IHandleMegamodelJob megamodelHandler) {
		this.megamodelHandler = megamodelHandler;
	}

	private void clog6(String mesg) {
		if (DParams.TextGen6_LOG)
			System.out.println(mesg);
	}

	private Dsml findDsml(List<Dsml> dsmls, String name) {
		for (Dsml dsml : dsmls)
			if (dsml.getName().equals(name))
				return dsml;
		return null;
	}

	private String end(String indent) {
		String result = LINE_PREFIX + indent + SEP_CLOSE_BRACKET + CR_;
		linecount++;
		if (DParams.TextGen6_LOG)
			clog6("TGV2 end          : \n" + result);
		return result;
	}

	private String getLine() {
		if (LINE_NUMBERED) {
			if (linecounter == startConcreteLine)
				linecounter = 0;
			return String.format("%4s", LINE_FORMATER.format(linecounter++))
					+ SEP_TAB;
			// return Integer.toString(linecounter++) + SEP_TAB;
		} else
			return "";
	}

	private String numberLines(String content) {
		StringBuffer buf = new StringBuffer();
		for (String line : content.split(LINE_PREFIX))
			if (!line.isEmpty())
				if (LINE_NUMBERED)
					buf.append(getLine() + line);
				else
					buf.append(line);
		return buf.toString();
	}

	private void refreshProject(IProject p) {
		try {
			p.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		} catch (CoreException e) {
			System.err
					.println("**************  err refresh in compile megamodel");
			e.printStackTrace();
		}
	}

	private String edgeToLine_(DEdge dEdge) {



		if (!DISPLAY_PROPAGATED && dEdge.isPropagated())
			return "";
		else
			return edgeToKeyword(dEdge) + SEP_EQU_SPS + edgeToCode(dEdge);
	}

	private String labelLine(DLabel label) {
		if (!DISPLAY_PROPAGATED && label.isPropagated())
			return "";
		else
			return labelToCode(label) + ",";
	}

	private void edges2code(StringBuffer buf, DNode dNode) {

		String ll = dNode.getName() + SEP_SPACE + SEP_OPEN_BRACKET;

		List<DEdge> edges = getEdges(dNode);
		if (dNode instanceof DPointOfView)
			ll += SEP_SPACE + DIAGRAPH_VOCAB_POV_;
		buf.append(ll);
		if (!edges.isEmpty()) {
			int x = 0;
			for (int k = 0; k < edges.size(); k++) {
				DEdge dEdge = edges.get(k);
				String l = edgeToLine_(dEdge).trim();
				if (!l.isEmpty()) {
					if (x == 0) {
						if (dNode instanceof DPointOfView)
							buf.append(SEP_COMMA_SPS);
						else
							buf.append(SEP_SPACE);
					}
					x++;
					buf.append(l);
					if ((k < edges.size() - 1 || dNode.getNavigationLink() != null))
						buf.append(SEP_COMMA_SPS);
				}
			}
			buf.append(SEP_SPACE);
		}
	}

	private EReference getContainingReference(DLabeledEdge dEdge) {
		EClass sr = (EClass) dEdge.getSemanticRole();
		DNode owner = dEdge.getOwner();
		EPackage p = owner.getEClaz().getEPackage();
		List<EClassifier> clasz = p.getEClassifiers();
		for (EClassifier eClassifier : clasz) {
			if (eClassifier instanceof EClass) {
				for (EStructuralFeature eStructuralFeature : (((EClass) eClassifier)
						.getEAllStructuralFeatures())) {
					if (eStructuralFeature instanceof EReference) {
						EReference er = (EReference) eStructuralFeature;
						if (er.getEType() == sr)
							if (er.isContainment())
								return er;
					}
				}
			}
		}
		return null;
	}

	private String edgeToCode(DEdge dEdge) {
		String result = dEdge.getName()
				+ (dEdge.isPropagated() ? DIAGRAPH_VOCAB_PROPOGATED : "");
		String edg = getEdgeWord(dEdge);
		if (edg.equals(DLabeledEdge.class.getSimpleName())) {
			EReference trg = ((DLabeledEdge) dEdge).getTargetReference();
			EReference src = ((DLabeledEdge) dEdge).getSourceReference();
			EReference cont = getContainingReference((DLabeledEdge) dEdge);
			String args1 = src != null ? src.getName() : "";
			String args2 = trg != null ? trg.getName() : "";
			String args3 = cont != null ? ((EClass) cont.eContainer())
					.getName() + "." + cont.getName() : "";
			String arg = args1 + (args2.isEmpty() ? "" : ("," + args2));
			arg += (args3.isEmpty() ? "" : ("," + args3));
			result += "(" + arg + ")";
		}
		return result;

	}

	private String labelToCode(DLabel label) {
		return label.getAttribute().getName()
				+ (label.isPropagated() ? DIAGRAPH_VOCAB_PROPOGATED : "");
	}

	private void labels2code(StringBuffer buf_, DNode dNode) {

		String lbs = "";
		if (!dNode.getLabls().isEmpty()) {
			lbs = buf_.toString().trim().endsWith(SEP_OPEN_BRACKET) ? "" : (!buf_.toString().trim().endsWith(SEP_COMMA)?SEP_COMMA:"")
					+ SEP_SPACE;
			lbs += DIAGRAPH_VOCAB_LABELS_EQUAL;
			List<DLabel> labls = dNode.getDlabels();
			for (DLabel label : labls) {

				lbs += labelLine(label);

				// lbs += labelToCode(label) + ","; // FP150514
			}
			lbs = " " + lbs.substring(0, lbs.length() - 1);
		}

		if (!lbs.isEmpty()) {
			buf_.append(lbs);
			if (LOG2)
				clog2(format("(2) export labels") + lbs.trim());
		}

	}

	private String genericElementsToCode_(DGraph dgraph, String indent) {
		StringBuffer buf = new StringBuffer();
		if (LOG2)
			clog2(format("export gener2Code"));
		for (DGeneric el : getGenerics((dgraph))) {
			String l = LINE_PREFIX + indent + genericToCode(I1 + indent, el)
					+ CR_;
			linecount++;
			if (LOG2)
				clog2(l);
			buf.append(l);
		}
		return buf.toString();
	}

	private String genericToCode(String prefix, DLabeledElement labeledElement) { // FP140518xx

		StringBuffer buf = new StringBuffer();
		String ll = labeledElement.getName() + SEP_OPEN_PARENTH
				+ DIAGRAPH_VOCAB_GENERIC + SEP_CLOSE_PARENTH + SEP_SPACE
				+ SEP_OPEN_BRACKET;
		buf.append(ll);
		if (LOG2)
			clog2(format("export gener2Code") + labeledElement.getName());
		String lbs = "";
		if (!labeledElement.getLabls().isEmpty()) {
			// lbs += SEP_COMMA + SEP_SPACE;
			// if (!buf.toString().endsWith("{"))
			lbs += DIAGRAPH_VOCAB_LABELS_EQUAL;//
			for (String label : labeledElement.getLabls())
				lbs += label + ",";
		}

		lbs = lbs.isEmpty() ? ""
				: (" " + lbs.substring(0, lbs.length() - 1) + " ");
		if (!lbs.isEmpty()) {
			buf.append(lbs);
			if (LOG2)
				clog2(format("(1) export labels") + lbs.trim());
		}
		buf.append(SEP_CLOSE_BRACKET);
		return buf.toString();
	}

	private String concreteElements2code(String prefix, DNode dNode) {

		if (LOG2) {
			String pref = format("export concreteElements2code");
			clog2(pref + dNode.getName()
					+ ((dNode instanceof DPointOfView) ? " pov" : ""));


		}
		StringBuffer buf = new StringBuffer();
		edges2code(buf, dNode);

		labels2code(buf, dNode);

		if (dNode.getNavigationLink() != null) {
			String navlin = dNode.getNavigationLink();
			navlin = navlin.substring(0, navlin.length()
					- UNDERSCORE_DIAGRAPH_VOCAB_ROOT.length());
			// if (!lbs.isEmpty() || !edges.isEmpty() || dNode instanceof
			// DPointOfView)
			// buf.append(" ,"); //FP140523az
            if (!buf.toString().trim().endsWith(","))
            	buf.append(", ");
			buf.append(DIAGRAPH_VOCAB_NAV_EQUAL + navlin + SEP_SPACE);
			if (LOG2)
				clog2(format("export navlink") + navlin.trim());
		}
		buf.append(SEP_CLOSE_BRACKET);
		return buf.toString();
	}

	private String format(String mesg) {
		return (mesg.trim() + "                                          ")
				.substring(0, 32);
	}

	public List<DEdge> getEdges(DNode node) {

		if (LOG2) {
			clog2(" edges for node " + node.getName());
		}
		List<DEdge> result = new ArrayList<DEdge>();
		for (DGraphElement element : node.getGraph().getElements()) {
			if (element != node) {
				if (LOG2 && false) {
					clog2(" edge " + node.getName() + "->" + element.getName());
				}
				if (element instanceof DNestedEdge) {

					DNestedEdge ne = (DNestedEdge) element;
					EReference sr = (EReference) ne.getSemanticRole();
					DNode nod = ne.getSource().getNode();
					if (nod == node) {
						result.add((DEdge) element);
						if (LOG2) {
							clog2(" nested edge "
									+ node.getName()
									+ "-"
									+ element.getName()
									+ "->"
									+ ((DNestedEdge) element).getTarget()
											.getName());
						}
					}
				} else if (element instanceof DSimpleEdge) {
					if (((DSimpleEdge) element).getSource() == node) {
						result.add((DEdge) element); // FP150423b
						if (LOG2) {
							clog2(" nested edge "
									+ node.getName()
									+ "-"
									+ element.getName()
									+ "->"
									+ ((DSimpleEdge) element).getTarget()
											.getName());
						}
					}
				}
			}
		}
		return result;
	}

	private List<DEdge> getEdges(DGraph graph) { // FP140518
		List<DEdge> result = new ArrayList<DEdge>();
		for (DGraphElement element : graph.getElements())
			if (element instanceof DEdge)
				result.add((DEdge) element);
		return result;
	}

	private String concreteElementsToCode(DGraph dgraph, String indent) {
		;
		StringBuffer buf = new StringBuffer();

		for (DNode dNode : getNodes(dgraph)) { // FP150513

			if (LOG2)
				clog2(dNode.getName());
		}



		for (DNode dNode : getNodes(dgraph)) { // FP150513

			String l = LINE_PREFIX + indent
					+ concreteElements2code(I1 + indent, dNode) + CR_;
			linecount++;
			if (LOG2)
				clog2(l);
			buf.append(l);
		}
		return buf.toString();
	}

	private String packageToCodev2(EPackage ePackage, String indent) {
		StringBuffer buf = new StringBuffer();
		buf.append(LINE_PREFIX + indent + "metamodel" + SEP_OPEN_PARENTH
				+ ePackage.getName() + SEP_CLOSE_PARENTH + SEP_OPEN_BRACKET
				+ CR_);
		linecount++;
		List<EClassifier> clazs = ePackage.getEClassifiers();
		for (EClassifier eClassifier : clazs) {
			if (eClassifier instanceof EClass) {
				EClass claz = (EClass) eClassifier;
				buf.append(LINE_PREFIX + indent
						+ I1 // + SEP_SPACE
						+ classToCode(claz) + SEP_SPACE + SEP_OPEN_BRACKET
						+ CR_);
				linecount++;
				buf.append(attributesToCode(claz, indent + I2));
				buf.append(referencesToCode(claz, indent + I2));
				buf.append(LINE_PREFIX + indent + I1 + SEP_CLOSE_BRACKET + CR_);// notation
				linecount++;
			}
		}
		buf.append(LINE_PREFIX + indent + SEP_CLOSE_BRACKET + CR_);// notation
		linecount++;
		return buf.toString();
	}

	private String packageToCode(EPackage ePackage, String indent) {
		StringBuffer buf = new StringBuffer();
		buf.append(LINE_PREFIX + indent + "metamodel" + SEP_OPEN_BRACKET + CR_);
		linecount++;
		List<EClassifier> clazs = ePackage.getEClassifiers();
		for (EClassifier eClassifier : clazs) {
			if (eClassifier instanceof EClass) {
				EClass claz = (EClass) eClassifier;
				buf.append(LINE_PREFIX + indent
						+ I1 // + SEP_SPACE
						+ classToCode(claz) + SEP_SPACE + SEP_OPEN_BRACKET
						+ CR_);
				linecount++;
				buf.append(attributesToCode(claz, indent + I2));
				buf.append(referencesToCode(claz, indent + I2));
				buf.append(LINE_PREFIX + indent + I1 + SEP_CLOSE_BRACKET + CR_);// notation
				linecount++;
			}
		}
		buf.append(LINE_PREFIX + indent + SEP_CLOSE_BRACKET + CR_);// notation
		linecount++;
		return buf.toString();
	}

	private List<DNode> getNodes(DGraph graph) { // FP140518
		List<DNode> nodes = new ArrayList<DNode>();
		for (DGraphElement element : graph.getElements())
			if (element instanceof DNode)
				nodes.add((DNode) element);
		return nodes;
	}

	private List<DGeneric> getGenerics(DGraph graph) {// FP140518xx
		List<DGeneric> els = new ArrayList<DGeneric>();
		for (DGraphElement element : graph.getElements())
			if (element instanceof DGeneric)
				// if (!(element instanceof DNode) && !(element instanceof
				// DEdge))
				els.add((DGeneric) element);
		return els;
	}

	private String classToCode(EClass claz) {
		String supclasses = " -> ";
		List<EClass> superclasses = claz.getESuperTypes();
		if (!superclasses.isEmpty())
			for (EClass eClass : superclasses)
				supclasses += eClass.getName() + ",";
		supclasses = supclasses.substring(0, supclasses.length() - 1);
		String abztract = claz.isAbstract() ? " " + DIAGRAPH_VOCAB_ABSTRACT
				: "";
		return claz.getName() + (superclasses.isEmpty() ? "" : supclasses)
				+ abztract;
	}

	private Object referencesToCode(EClass claz, String indent) {
		StringBuffer buf = new StringBuffer();
		for (EReference eref : getReferences(claz)) {
			buf.append(LINE_PREFIX + indent + eref2code("", eref) + CR_);
			linecount++;
		}
		return buf.toString();
	}

	/*
	 * DIAGRAPH_VOCAB_BYVAL, DIAGRAPH_VOCAB_DATATYPE, DIAGRAPH_VOCAB_ESTRING,
	 * DIAGRAPH_VOCAB_EINT, DIAGRAPH_VOCAB_EBOOL,
	 */
	private String eref2code(String indent, EReference eref) {
		return indent + eref.getName() + COLON + eref.getEType().getName()
				+ (eref.isContainment() ? " " + DIAGRAPH_VOCAB_BYVAL : "");
	}

	private List<EReference> getReferences(EClass claz) {
		return claz.getEReferences();
	}

	private Object attributesToCode(EClass claz, String indent) {
		StringBuffer buf = new StringBuffer();
		for (EAttribute att : getAttributes(claz)) {
			buf.append(LINE_PREFIX + indent + att2code("", att) + CR_);
			linecount++;
		}
		return buf.toString();

	}

	private String att2code(String indent, EAttribute att) {
		return indent + att.getName() + COLON
				+ att.getEAttributeType().getName();
	}

	private List<EAttribute> getAttributes(EClass claz) {
		return claz.getEAttributes();
	}

	private String viewsToCode(List<DGraph> dsmls, String indent) { // FP140625aa
		StringBuffer buf = new StringBuffer();
		for (DGraph dgraph : dsmls) {
			// DGraph dgraph = (DGraph) notation_.getNotationBridge();
			String languageName = dgraph.getFacade1().getLanguageName();
			if (LOG2)
				clog2(format("export viewToCode") + languageName + "."
						+ dgraph.getViewName());
			String l1 = LINE_PREFIX + indent + DIAGEN_VOCAB_VIEW
					+ SEP_OPEN_PARENTH + dgraph.getViewName()
					+ SEP_CLOSE_PARENTH + SEP_OPEN_BRACKET + CR_;
			linecount++;
			if (LOG2)
				clog2(l1);
			buf.append(l1);
			buf.append(concreteElementsToCode(dgraph, indent + I1));
			buf.append(genericElementsToCode_(dgraph, indent + I1));
			String l2 = LINE_PREFIX + indent + SEP_CLOSE_BRACKET + CR_;
			linecount++;
			if (LOG2)
				clog2(l2);
			buf.append(l2);// notation
		}
		String result = buf.toString();
		if (LOG2)
			clog2("TGV2 viewsToCode: \n" + buf);
		return result;
	}

	private String viewsToCodev2(EPackage ePackage, List<DGraph> dsmls,
			String indent) { // FP140625aa
		StringBuffer buf = new StringBuffer();
		for (DGraph dgraph : dsmls) {
			String languageName = dgraph.getFacade1().getLanguageName();
			if (LOG2)
				clog2(format("export viewToCode") + languageName + "."
						+ dgraph.getViewName());
			String l1 = LINE_PREFIX + indent + DIAGEN_VOCAB_VIEW
					+ SEP_OPEN_PARENTH + ePackage.getName() + "."
					+ dgraph.getViewName() + SEP_CLOSE_PARENTH
					+ SEP_OPEN_BRACKET + CR_;
			linecount++;
			if (LOG2)
				clog2(l1);
			buf.append(l1);
			buf.append(concreteElementsToCode(dgraph, indent + I1));
			buf.append(genericElementsToCode_(dgraph, indent + I1));
			String l2 = LINE_PREFIX + indent + SEP_CLOSE_BRACKET + CR_;
			linecount++;
			if (LOG2)
				clog2(l2);
			buf.append(l2);// notation
		}
		String result = buf.toString();
		if (LOG2)
			clog2("TGV2 viewsToCode: \n" + buf);
		return result;
	}

	private void clog2(String mesg) {
		if (LOG2)
			System.out.println(mesg);
	}

	private String begin(String dsmlName, String indent) {
		String buf = LINE_PREFIX + indent + DIAGEN_VOCAB_DSML
				+ SEP_OPEN_PARENTH + dsmlName + SEP_CLOSE_PARENTH
				+ SEP_OPEN_BRACKET + CR_; // FP140601pars
		linecount++;
		if (DParams.TextGen6_LOG)
			clog6("TGV2 begin      : \n" + buf);
		return buf;
	}

	private String getEdgeWord(DEdge dEdge) {
		return dEdge
				.getClass()
				.getSimpleName()
				.substring(
						0,
						dEdge.getClass().getSimpleName().length()
								- "Impl".length());
	}

	private String edgeToKeyword(DEdge dEdge) {
		;
		String edg = getEdgeWord(dEdge);
		if (LOG2) {
			clog2(format("export edge2Keyword") + edg.trim());
		}
		if (edg.equals(DNestedEdge.class.getSimpleName()))
			edg = DIAGRAPH_VOCAB_CREF;
		else if (edg.equals(DAffixedEdge.class.getSimpleName()))
			edg = DIAGRAPH_VOCAB_AFX_;
		else if (edg.equals(DCompartmentEdge.class.getSimpleName()))
			edg = DIAGRAPH_VOCAB_KREF;
		else if (edg.equals(DReference.class.getSimpleName()))
			edg = DIAGRAPH_VOCAB_REF;
		else if (edg.equals(DLabeledEdge.class.getSimpleName())) {
			edg = DIAGRAPH_VOCAB_LINK;
		}
		return edg;
	}

	private IProject getProject(String name) {
		return megamodelHandler.getProject(name);
	}

	private List<Dsml> getMegamodelDsmls() {// FP140622voiraaa

		List<Dsml> result = megamodelHandler.getMegamodelDsmls();
		if (LOG_) {
			String log = "";

			for (Dsml dsml : result) {

				log += dsml.getName()
						+ (dsml.getNotations_().isEmpty() ? " no view " : "")
						+ "; ";
			}
			clog("getMegamodelDsmls: " + log);
		}
		return result;
	}

	private IStatus errorStatus(Exception e) {
		return megamodelHandler.errorStatus(e);
	}

	private IStatus errorStatus(String mesg) {
		return megamodelHandler.errorStatus(mesg);
	}

	@Override
	public void connectAll() {
		// TODO Auto-generated method stub
	}

	@Override
	public void getAllModels() {
		// TODO Auto-generated method stub

	}

	private void keywwordsAdd(String k) {
		if (!keywwords.contains(k))
			keywwords.add(k);
		else
			clog("********************** allready contains " + k);

	}

	@Override
	public void getKeywords() { // to highlight in the future
		keywwordsAdd(DIAGEN_VOCAB_DSML);
		keywwordsAdd(DIAGRAPH_VOCAB_REF);
		keywwordsAdd(DIAGRAPH_VOCAB_KREF);
		keywwordsAdd(DIAGRAPH_VOCAB_CREF);
		keywwordsAdd(DIAGRAPH_VOCAB_AFX_);
		keywwordsAdd(DIAGRAPH_VOCAB_POV_);
		keywwordsAdd(DIAGRAPH_VOCAB_NAV_);
		keywwordsAdd(DIAGRAPH_VOCAB_LINK);
		keywwordsAdd("metamodel");
	}

	@Override
	public List<String> getSeparators() { // to highlight in the future
		separators.add(SEP_OPEN_PARENTH);
		separators.add(SEP_CLOSE_PARENTH);
		separators.add(SEP_OPEN_BRACKET);
		separators.add(SEP_CLOSE_BRACKET);
		return separators;
	}

	@Override
	public List<String> getGlossary() { // FP140621
		return glossary;
	}

	/*************************************/

	@Override
	public IStatus exportLanguage(SubProgressMonitor monitor,
			List<DGraph> dgraphs, String langname, int n, File destFile) { // FP140601
		monitor.beginTask("Exporting " + n + " " + DIAGEN_VOCAB_DSMLS, n * 3);
		if (!dgraphs.isEmpty()) {
			// String langname = dgraphs.get(0).getFacade1().getLanguageName();
			if (LOG_)
				clog("exporting dsml " + langname);
			if (monitor.isCanceled())
				return Status.CANCEL_STATUS;
			dones = new ArrayList<IProject>();
			try {
				if (langname != null && !langname.isEmpty()) {
					exportDGraph_twopartsv2(monitor, dgraphs, langname);
				}
			} catch (Exception e) {
				// e.printStackTrace();
				InterpreterPlugin.getInstance().addError(
						e.toString() + " in exportLanguage");
				return errorStatus(e.toString() + " in exportLanguage");
			}
		}
		return Status.OK_STATUS;
	}

	private void exportDGraph(SubProgressMonitor monitor, List<DGraph> dgraphs,
			String languageName) throws IOException { // FP140620 //FP150513
		if (dgraphs.isEmpty())
			throw new RuntimeException(
					"should not happen in exportDGraph: no views");
		if (LOG_)
			clog("exporting language " + languageName);
		if (DParams.TextGen6_LOG) {
			String log = "";
			for (DGraph dGraph : dgraphs)
				log += languageName + "." + dGraph.getViewName() + "; ";
			clog6("exportLanguage views=" + log);
		}
		IProject p = getProject(languageName);
		if (!dones.contains(p))
			dones.add(p);
		if (LOG_)
			clog("export textual Dsml " + languageName);
		monitor.subTask("export Dsml " + languageName);// .substring(model.lastIndexOf("/")));
		String buf = "";
		String indent = "";
		buf += begin(languageName, indent);
		buf += packageToCode(dgraphs.get(0).getPointOfView().getEClaz()
				.getEPackage(), indent + I1);
		buf += viewsToCode(dgraphs, indent + I1);
		buf += end(indent);
		String syntaxFilename = p.getLocation().toFile().getAbsolutePath()
				+ File.separator + DIAGEN_VOCAB_MODEL + File.separator
				+ languageName + "." + DIAGEN_CST_EXT;
		FileWriter fw = new FileWriter(new File(syntaxFilename));
		fw.write(numberLines(buf));
		fw.close();
		if (LOG_)
			clog("------------------------------\nconcrete syntax file saved: "
					+ syntaxFilename);

	}

	private void exportDGraph_twopartsv1(SubProgressMonitor monitor,
			List<DGraph> dgraphs, String languageName) throws IOException { // FP140620
																			// //FP150513
		if (dgraphs.isEmpty())
			throw new RuntimeException(
					"should not happen in exportDGraph: no views");
		if (LOG_)
			clog("exporting language " + languageName);
		if (DParams.TextGen6_LOG) {
			String log = "";
			for (DGraph dGraph : dgraphs)
				log += languageName + "." + dGraph.getViewName() + "; ";
			clog6("exportLanguage views=" + log);
		}
		IProject p = getProject(languageName);
		if (!dones.contains(p))
			dones.add(p);
		if (LOG_)
			clog("export textual Dsml " + languageName);
		monitor.subTask("export Dsml " + languageName);// .substring(model.lastIndexOf("/")));
		String buf = "";
		String indent = "";
		buf += begin(languageName, indent);
		buf += packageToCode(dgraphs.get(0).getPointOfView().getEClaz()
				.getEPackage(), indent + I1);
		buf += end(indent);
		startConcreteLine = linecount;
		linecount = 0;

		buf += begin(languageName, indent);
		buf += viewsToCode(dgraphs, indent + I1);
		buf += end(indent);
		String syntaxFilename = p.getLocation().toFile().getAbsolutePath()
				+ File.separator + DIAGEN_VOCAB_MODEL + File.separator
				+ languageName + "." + DIAGEN_CST_EXT;
		FileWriter fw = new FileWriter(new File(syntaxFilename));
		fw.write(numberLines(buf));
		fw.close();
		if (LOG_)
			clog("------------------------------\nconcrete syntax file saved: "
					+ syntaxFilename);
	}

	private void exportDGraph_twopartsv2(SubProgressMonitor monitor,
			List<DGraph> dgraphs, String languageName) throws IOException { // FP140620
																			// //FP150513
		if (dgraphs.isEmpty())
			throw new RuntimeException(
					"should not happen in exportDGraph: no views");
		if (LOG_)
			clog("exporting language " + languageName);
		if (DParams.TextGen6_LOG) {
			String log = "";
			for (DGraph dGraph : dgraphs)
				log += languageName + "." + dGraph.getViewName() + "; ";
			clog6("exportLanguage views=" + log);
		}
		IProject p = getProject(languageName);
		if (!dones.contains(p))
			dones.add(p);
		if (LOG_)
			clog("export textual Dsml " + languageName);
		monitor.subTask("export Dsml " + languageName);// .substring(model.lastIndexOf("/")));
		String buf = "";
		String indent_ = "";
		// buf += beginv2(languageName, indent);
		buf += packageToCodev2(dgraphs.get(0).getPointOfView().getEClaz()
				.getEPackage(), indent_);
		// buf += end(indent);
		startConcreteLine = linecount;
		linecount = 0;

		// buf += begin(languageName, indent);
		buf += viewsToCodev2(dgraphs.get(0).getPointOfView().getEClaz()
				.getEPackage(), dgraphs, indent_);
		// buf += end(indent);
		String syntaxFilename = p.getLocation().toFile().getAbsolutePath()
				+ File.separator + DIAGEN_VOCAB_MODEL + File.separator
				+ languageName + "." + DIAGEN_CST_EXT;
		FileWriter fw = new FileWriter(new File(syntaxFilename));
		fw.write(numberLines(buf));
		fw.close();
		if (LOG_)
			clog("------------------------------\nconcrete syntax file saved: "
					+ syntaxFilename);
	}

	private void exportDGraphAs_nu(SubProgressMonitor monitor,
			List<DGraph> dgraphs, String languageName) throws IOException { // FP140620
																			// //FP150513
		if (dgraphs.isEmpty())
			throw new RuntimeException(
					"should not happen in exportDGraph: no views");
		// String languageName = dgraphs.get(0).getFacade1().getLanguageName();
		if (LOG_)
			clog("exporting language " + languageName);
		if (DParams.TextGen6_LOG) {
			String log = "";
			for (DGraph dGraph : dgraphs)
				log += languageName + "." + dGraph.getViewName() + "; ";
			clog6("exportLanguage views=" + log);
		}
		IProject p = getProject(languageName);
		if (!dones.contains(p))
			dones.add(p);
		if (LOG_)
			clog("export textual Dsml " + languageName);
		monitor.subTask("export Dsml " + languageName);// .substring(model.lastIndexOf("/")));
		String buf = "";
		String indent = "";
		buf += begin(languageName, indent);
		buf += packageToCode(dgraphs.get(0).getPointOfView().getEClaz()
				.getEPackage(), indent + I1);
		buf += end(indent);
		String syntaxFilename = p.getLocation().toFile().getAbsolutePath()
				+ File.separator + DIAGEN_VOCAB_MODEL + File.separator
				+ languageName + "." + DIAGEN_AST_EXT;
		FileWriter fw = new FileWriter(new File(syntaxFilename));
		fw.write(numberLines(buf));
		fw.close();
		if (LOG_)
			clog("------------------------------\nconcrete syntax file saved: "
					+ syntaxFilename);

	}

	private void exportDGraphCs_nu(SubProgressMonitor monitor,
			List<DGraph> dgraphs, String languageName) throws IOException { // FP140620
																			// //FP150513
		if (dgraphs.isEmpty())
			throw new RuntimeException(
					"should not happen in exportDGraph: no views");
		// String languageName = dgraphs.get(0).getFacade1().getLanguageName();
		if (LOG_)
			clog("exporting language " + languageName);
		if (DParams.TextGen6_LOG) {
			String log = "";
			for (DGraph dGraph : dgraphs)
				log += languageName + "." + dGraph.getViewName() + "; ";
			clog6("exportLanguage views=" + log);
		}
		IProject p = getProject(languageName);
		if (!dones.contains(p))
			dones.add(p);
		if (LOG_)
			clog("export textual Dsml " + languageName);
		monitor.subTask("export Dsml " + languageName);// .substring(model.lastIndexOf("/")));
		String buf = "";
		String indent = "";
		buf += begin(languageName, indent);
		buf += viewsToCode(dgraphs, indent + I1);
		buf += end(indent);
		String syntaxFilename = p.getLocation().toFile().getAbsolutePath()
				+ File.separator + DIAGEN_VOCAB_MODEL + File.separator
				+ languageName + "." + DIAGEN_CST_EXT;
		FileWriter fw = new FileWriter(new File(syntaxFilename));
		fw.write(numberLines(buf));
		fw.close();
		if (LOG_)
			clog("------------------------------\nconcrete syntax file saved: "
					+ syntaxFilename);

	}

	// @Override
	private IStatus genGrammarDsml_old_nu(IProgressMonitor monitor,
			String langName) throws CoreException { // FP140622voiraaaccc

		return null;
	}

	public void logDsmls() // FP140624
			throws CoreException { // FP140622voiraaaccc
		clog("checkDsmls ");
		List<Dsml> dsmls_ = getMegamodelDsmls();
		if (dsmls_.isEmpty()) {// FP140522
			clog("empty dsmls");
			return;
		} else
			for (Dsml dsml : dsmls_) {
				List<Notation> dsmlnotations = dsml.getNotations_();
				if (dsmlnotations.isEmpty())
					clog("empty notations for " + dsml.getName());
				else
					for (Notation notation : dsmlnotations) {
						clog("notation: " + notation.getName() + " for "
								+ dsml.getName());
					}
				List<EcoreDiagram> ecoreDiagrams = dsml.getEcoreDiagrams();
				if (ecoreDiagrams.isEmpty())
					clog("empty diagrams for " + dsml.getName());
				else
					for (EcoreDiagram ecoreDiagram : ecoreDiagrams) {
						clog("ecorediag: " + ecoreDiagram.getName() + " ("
								+ ecoreDiagram.getURI() + ") for "
								+ dsml.getName());
					}
			}
	}

	@Override
	public IStatus genGrammarDGraph(IProgressMonitor monitor, String langName)
			throws CoreException { // FP140622voiraaa //FP140622voiraaaccc

		if (LOG_)
			clog("genGrammar ");

		if (LOG_)
			logDsmls();

		List<DGraph> graphs = megamodelHandler.getNotifiable()
				.getCurrentDiagraphs(); // FP140621
		if (graphs.isEmpty()) {// FP140522
			if (LOG_)
				clog("empty diagraphs");
			return new Status(IStatus.INFO, DiagenPlugin.PLUGIN_ID,
					"empty diagraphs");
		}
		File nulldestFile = null;
		IStatus status = exportLanguage(new SubProgressMonitor(monitor, 90),
				graphs, langName, graphs.size(), nulldestFile);
		for (IProject project : dones)
			refreshProject(project);
		refreshProject(megamodelHandler.getProject(IHandleMegamodelJob.MGM_KEY));
		return status;
	}

	private IStatus exportDsmls_old_nu_(SubProgressMonitor monitor,
			List<Dsml> dsmls_, String langname, int n, File destFile) { // FP140601
		return Status.CANCEL_STATUS;
	}

}
