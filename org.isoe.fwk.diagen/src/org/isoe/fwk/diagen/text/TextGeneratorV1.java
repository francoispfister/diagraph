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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.isoe.diagraph.diagraph.DAffixedEdge;
import org.isoe.diagraph.diagraph.DCompartmentEdge;
import org.isoe.diagraph.diagraph.DOwnedEdge;
import org.isoe.diagraph.diagraph.DEdge;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.diagraph.DGraphElement;
import org.isoe.diagraph.diagraph.DNavigationEdge;
import org.isoe.diagraph.diagraph.DNestedEdge;
import org.isoe.diagraph.diagraph.DLabeledEdge;
import org.isoe.diagraph.diagraph.DNode;
import org.isoe.diagraph.diagraph.DPointOfView;
import org.isoe.diagraph.diagraph.DReference;
import org.isoe.diagraph.diagraph.DSimpleEdge;
import org.isoe.diagraph.interpreter.InterpreterPlugin;
import org.isoe.diagraph.megamodel.Dsml;
import org.isoe.diagraph.megamodel.MegamodelFactory;
import org.isoe.diagraph.megamodel.Model;
import org.isoe.diagraph.megamodel.Notation;
import org.isoe.diagraph.megamodel.RelatedTo;
import org.isoe.diagraph.megamodel.Relation;
import org.isoe.diagraph.workbench.api.DiagraphNotifiable;
import org.isoe.extensionpoint.diagen.IDiagraphAlphabet;
import org.isoe.extensionpoint.diagen.IHandleMegamodelJob;
import org.isoe.extensionpoint.diagen.ITextGenerator;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.diagen.DiagenPlugin;

/*
 *
 * @author fpfister
 * Handmade... TODO use xpand
 */
public class TextGeneratorV1 implements IDiagraphAlphabet, ITextGenerator {

	private static final boolean SHOW_INHERITED_EDGES_ = true;

	static final int LEFT_PARENT = 0;
	static final int RIGHT_PARENT = 1;

	/*--------------------------*/
	private static final String LINE_PREFIX = "###";
	private static final String CR = "\n";
	private static final String I1 = "  ";
	private static final String I2 = I1 + I1;
	private static final String I3 = I2 + I1;
	private static final String STR_COLON_SPS_ = SEP_SPACE + STR_COLON
			+ SEP_SPACE;
	private static final String SEP_PLUS_SPS = SEP_SPACE + SEP_PLUS + SEP_SPACE;
	private static final String SEP_EQU_SPS = SEP_SPACE + SEP_EQU + SEP_SPACE;
	private static final String SEP_COMMA_SPS = SEP_SPACE + SEP_COMMA
			+ SEP_SPACE;
	private static final String SEP_UNDERSCORE_SPS = SEP_SPACE + SEP_UNDERSCORE
			+ SEP_SPACE;

	private static final String REPOSITORY_FOLDER = "repository";

	/* compositions */
	private static final String EXPR_OPEN_SET = SEP_EQU + SEP_OPEN_BRACKET
			+ SEP_SPACE;
	private static final String EXPR_CLOSE_SET = SEP_SPACE + SEP_CLOSE_BRACKET;
	private static final String EXPR_NIL = SEP_OPEN_BRACKET + SEP_SPACE
			+ DIAGEN_VOCAB_NIL + SEP_SPACE + SEP_CLOSE_BRACKET;

	private static final String UNDERSCORE_DIAGRAPH_VOCAB_ROOT = SEP_UNDERSCORE
			+ DIAGRAPH_VOCAB_ROOT;
	private static final String UNDERSCORE_DIAGRAPH_STATUS_ABSTRACT = SEP_UNDERSCORE
			+ DIAGRAPH_STATUS_ABSTRACT;
	private static final String DIAGEN_VOCAB_EDGE_UNDER = DIAGEN_VOCAB_EDGE
			+ SEP_UNDERSCORE;
	private static final String DIAGRAPH_VOCAB_NAV_COLON = DIAGRAPH_VOCAB_NAV_
			+ STR_COLON;



	private static final boolean LOG = DParams.TextGenerator_LOG;

	//static final String MGM_KEY = "MEGAMODEL_PROJECT";

	/*--------------------------*/

	private Map<String, String> modelmap_ = new HashMap<String, String>();
	private Map<EClass, List<EClass>> lowEClasses;
	private List<DEdgeStatus> edgestatus;
	private int linecounter = 1;
	private boolean lineNumbered_;
	private boolean legended;
	private boolean showStatus = false;
	private List<String> theLegend = null;
	private String bufer = "";

	private List<String> keywwords = new ArrayList<String>();
	private List<String> separators = new ArrayList<String>();
	private List<String> glossary = new ArrayList<String>();
	// private IProject megamodel_prj;
	private List<IProject> dones;

	private IHandleMegamodelJob megamodelHandler;



	@Override
	public List<String> getSeparators() { // to highlight in the future
		separators.add(SEP_OPEN_PARENTH);
		separators.add(SEP_CLOSE_PARENTH);
		separators.add(SEP_OPEN_BRACKET);
		separators.add(SEP_CLOSE_BRACKET);
		separators.add(SEP_OPEN_ARRAY);
		separators.add(SEP_CLOSE_ARRAY);
		separators.add(SEP_UNDERSCORE);
		separators.add(SEP_ARROW);
		separators.add(SEP_MINUS);
		separators.add(SEP_EQU);
		separators.add(SEP_COMMA);
		separators.add(SEP_SPACE);
		separators.add(SEP_TAB);
		return separators;
	}

	@Override
	public List<String> getGlossary() {
		glossary.add(DIAGRAPH_STATUS_NRA + ": not representable");
		glossary.add(DIAGRAPH_STATUS_RA + ": representable");
		glossary.add(DIAGEN_STATUS_INHER + ": inherited");
		glossary.add(DIAGRAPH_STATUS_ABSTRACT + ": abstract");
		glossary.add(DIAGRAPH_STATUS_NOT_ECORE_CONTAINED
				+ ": not ecore contained");
		glossary.add(DIAGRAPH_STATUS_NOT_DIAGRAPH_CONTAINED
				+ ": not diagraph contained");
		return glossary;
	}

	@Override
	public void getKeywords() { // to highlight in the future

		keywwordsAdd(DIAGEN_VOCAB_NAME);
		keywwordsAdd(DIAGEN_VOCAB_DIAGRAM);

		keywwordsAdd(DIAGEN_VOCAB_EDGE);
		keywwordsAdd(DIAGEN_VOCAB_MODELS);
		keywwordsAdd(DIAGEN_VOCAB_EXCERPT);
		keywwordsAdd(DIAGEN_VOCAB_MODEL);
		keywwordsAdd(DIAGEN_VOCAB_EDGES);
		keywwordsAdd(DIAGEN_VOCAB_NODES);
		keywwordsAdd(DIAGEN_VOCAB_NOTATION_);

		keywwordsAdd(DIAGRAPH_VOCAB_REF);
		keywwordsAdd(DIAGRAPH_VOCAB_KREF);
		keywwordsAdd(DIAGRAPH_VOCAB_CREF);
		keywwordsAdd(DIAGRAPH_VOCAB_AFX_);

		keywwordsAdd(DIAGEN_VOCAB_SRC);
		keywwordsAdd(DIAGEN_VOCAB_TRG);
		keywwordsAdd(DIAGRAPH_VOCAB_CONT);

		keywwordsAdd(DIAGRAPH_VOCAB_POV_SHORT); // keywwordsAdd(DiagraphKeywords.POINT_OF_VIEW);
		keywwordsAdd(DIAGRAPH_VOCAB_NAV_); // DiagraphKeywords.OPEN_DIAGRAM

		keywwordsAdd(DIAGRAPH_VOCAB_LINK);
		keywwordsAdd(DIAGEN_VOCAB_NIL);
		keywwordsAdd(DIAGRAPH_VOCAB_ROOT);
		keywwordsAdd(DIAGRAPH_VOCAB_NOT_ROOT);
		keywwordsAdd(DIAGRAPH_VOCAB_RECURSIVE);

		if (showStatus) {
			keywwordsAdd(DIAGRAPH_STATUS_ABSTRACT);
			keywwordsAdd(DIAGRAPH_STATUS_NRA);
			keywwordsAdd(DIAGRAPH_STATUS_RA);
			keywwordsAdd(DIAGEN_STATUS_INHER);
			keywwordsAdd(DIAGRAPH_STATUS_NOT_ECORE_CONTAINED);
			keywwordsAdd(DIAGRAPH_STATUS_NOT_DIAGRAPH_CONTAINED);
		}

		keywwordsAdd(DIAGEN_VOCAB_DSML);
		keywwordsAdd(DIAGEN_VOCAB_MEGAMODEL);
		keywwordsAdd(DIAGEN_VOCAB_EDGE);
		keywwordsAdd(DIAGEN_VOCAB_NODE);
		keywwordsAdd(DIAGEN_VOCAB_MODELS);
		keywwordsAdd(DIAGEN_VOCAB_EXCERPT);
		keywwordsAdd(DIAGEN_VOCAB_MODEL);
		keywwordsAdd(DIAGEN_VOCAB_EDGES);
		keywwordsAdd(DIAGEN_VOCAB_NODES);
		keywwordsAdd(DIAGEN_VOCAB_NOTATION_);

		keywwordsAdd(DIAGEN_VOCAB_NAME);
		keywwordsAdd(DIAGEN_VOCAB_NOTICES);
		keywwordsAdd(DIAGEN_VOCAB_KNOWN_AS);
		keywwordsAdd(DIAGEN_VOCAB_RIGHT_PARENT);
		keywwordsAdd(DIAGEN_VOCAB_LEFT_PARENT);
		keywwordsAdd(DIAGEN_VOCAB_REQUIRED_);// Diagen.KW_REQUIRES);
		keywwordsAdd(DIAGEN_VOCAB_RELATED_);// Diagen.KW_REQUIRES);
		keywwordsAdd(DIAGEN_VOCAB_CONTEXT);
		keywwordsAdd(DIAGEN_VOCAB_ORIGIN);

		keywwordsAdd(DIAGEN_VOCAB_DSMLS);

		// keywwordsAdd("_%d");
		// keywwordsAdd("(%d)");
	}

	private void keywwordsAdd(String k) {
		if (!keywwords.contains(k))
			keywwords.add(k);
		else
			clog("********************** allready contains " + k);

	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	public TextGeneratorV1(IHandleMegamodelJob megamodelHandler,
			boolean lineNumbered, boolean legended, boolean showStatus) {
		// super(false,"Export megamodel", project, owner);
		this.megamodelHandler = megamodelHandler;
		this.lineNumbered_ = lineNumbered;
		this.legended = legended;
		this.showStatus = showStatus;
		//this.exportSimple = false;
	}



	private String getModel(String model, String domain) {
		try {
			for (String key : modelmap_.keySet())
				if (modelmap_.get(key).equals(model + "." + domain))
					return key;
		} catch (Exception e) {
			return "";
		}
		return "";
	}

	@Override
	public void connectAll() {
		//FP140622voiraaa
		for (Dsml dsml : getMegamodelDsmls_())
			if (getProject(dsml.getName()) != null
					&& !dsml.getNotations_().isEmpty()) {
				connectAssociated(dsml, dsml.getRequireDetails(),
						dsml.getRelations(), Relation.REQUIRED);
				connectAssociated(dsml, dsml.getRelatedDetails(),
						dsml.getRelations(), Relation.RELATED);
				connectParent(dsml, true); // left
				connectParent(dsml, false); // right
			}
	}

	private void connectAssociated(Dsml dsml, EList<String> details,
			EList<RelatedTo> relations, Relation relation) {
		//FP140622voiraaa
		for (String detail : details)
			for (Dsml otherDsml : getMegamodelDsmls_())
				// megamodel.getDsmls())
				if (otherDsml != dsml && otherDsml.getName().equals(detail)) {
					RelatedTo relatedTo = MegamodelFactory.eINSTANCE
							.createRelatedTo();
					relatedTo.setFrom(dsml);
					relatedTo.setTo(otherDsml);
					dsml.getRelations().add(relatedTo);
					relatedTo.setKind(relation);
					// associations.add(otherDsml);
				}

	}

	private void connectParent(Dsml dsml, boolean left) {
		if (left && dsml.getLeftParentDetails().isEmpty())
			return;
		if (!left && dsml.getRightParentDetails().isEmpty())
			return;
		String parentname = left ? dsml.getLeftParentDetails().get(0) : dsml
				.getRightParentDetails().get(0);
		if (parentname != null && !parentname.isEmpty()) {
			for (Dsml otherDsml : getMegamodelDsmls_()) //FP140622voiraaa
				// megamodel.getDsmls())
				if (otherDsml != dsml && otherDsml.getName().equals(parentname)) {
					RelatedTo relatedTo = MegamodelFactory.eINSTANCE
							.createRelatedTo();
					relatedTo.setFrom(dsml);
					relatedTo.setTo(otherDsml);
					dsml.getRelations().add(relatedTo);
					relatedTo.setKind(left ? Relation.LEFT_PARENT
							: Relation.RIGHT_PARENT);
					break;
				}
		}
	}

	private String readExcerpt(File file) {
		InputStream in = null;
		try {
			String line3 = "";
			in = new FileInputStream(file);
			StringBuffer buffer_ = new StringBuffer();
			InputStreamReader inR = new InputStreamReader(in);
			BufferedReader buf = new BufferedReader(inR);
			String line;
			int lines = 0;
			while ((line = buf.readLine()) != null) {
				if (lines == 3)
					line3 = line;
				if (line == null || lines == 3)
					break;
				lines++;
				buffer_.append(line).append(CR);
			}
			in.close();
			return line3;// buffer_.toString();
		} catch (Exception e) {
			try {
				in.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return "";
		}
	}

	@Override
	public void getAllModels() {
		IFolder folder = megamodelHandler.getProject(IHandleMegamodelJob.MGM_KEY).getFolder(
				REPOSITORY_FOLDER);
		String pp = folder.getFullPath().toPortableString();
		File modelfoldr = new File(CommonPlugin.resolve(
				URI.createPlatformResourceURI(pp, false)).toFileString());
		clog(modelfoldr.getAbsolutePath());
		File[] fils = modelfoldr.listFiles();
		List<File> files = new ArrayList<File>();
		for (File file : fils) {
			files.add(file);
			String line = readExcerpt(file);
			modelmap_.put(line, file.getName());
		}
	}




	private String getBuffer() {
		return bufer;
	}

	private void addToBuffer(String buf) {
		this.bufer += buf;
	}

	private String getLine() {
		if (lineNumbered_)
			return Integer.toString(linecounter++) + SEP_TAB;
		else
			return "";
	}

	private List<String> getTheLegend() {
		if (theLegend == null) {
			theLegend = new ArrayList<String>();
			if (legended) {
				Collections.sort(keywwords, new Comparator<String>() {
					public int compare(String p1, String p2) {
						long result = p1.compareTo(p2);
						if (result == 0)
							return 0;
						else if (result > 0)
							return 1;
						else
							return -1;
					}
				});
				Collections.sort(glossary, new Comparator<String>() {
					public int compare(String p1, String p2) {
						long result = p1.compareTo(p2);
						if (result == 0)
							return 0;
						else if (result > 0)
							return 1;
						else
							return -1;
					}
				});
				theLegend.add("");
				theLegend.addAll(getLegend("keywords", null, keywwords));
				if (showStatus) {
					theLegend.add("");
					theLegend.addAll(getLegend2("status", null, glossary));
				}
				theLegend.add("");
			}
		}
		return theLegend;
	}

	private List<String> getLegend2(String label, String metasep,
			List<String> keyws) {
		List<String> result_ = new ArrayList<String>();
		int len = keyws.size();
		int count = 0;
		int line = 0;
		int lastline = 0;
		result_.add(label + SEP_OPEN_ARRAY);
		String aline = "  ";
		for (String keyword : keyws) {
			if (lastline != line) {
				lastline = line;
				keyword = I1 + keyword;
			}
			aline += (metasep == null ? "" : metasep) + keyword
					+ (metasep == null ? "" : metasep);
			count++;
			if (count % (len / 4) == 0) {
				aline += SEP_COMMA;
				line++;
				result_.add(aline);
				aline = "";
			} else
				aline += SEP_COMMA;
		}
		if (!aline.trim().isEmpty()) {
			aline = aline.substring(0, aline.length() - 1);
			result_.add(aline);
		}
		result_.add(SEP_CLOSE_ARRAY);
		return result_;
	}

	private List<String> getLegend(String label, String metasep,
			List<String> keyws) {
		List<String> result = new ArrayList<String>();
		int len = keyws.size();
		int count = 0;
		int line = 0;
		int lastline = 0;
		String aline = label + SEP_OPEN_BRACKET;
		for (String keyword : keyws) {
			if (lastline != line) {
				lastline = line;
				keyword = I1 + keyword;
			}
			aline += (metasep == null ? "" : metasep) + keyword
					+ (metasep == null ? "" : metasep);
			count++;
			if (count % (len / 4) == 0) {
				aline += SEP_COMMA;
				line++;
				result.add(aline);
				aline = "";
			} else
				aline += SEP_COMMA;
		}
		if (!aline.trim().isEmpty()) {
			aline = aline.substring(0, aline.length() - 1);
			result.add(aline);
		}
		result.add(SEP_CLOSE_BRACKET);
		return result;
	}

	private Dsml findDsml(List<Dsml> dsmls, String name) {
		for (Dsml dsml : dsmls)
			if (dsml.getName().equals(name))
				return dsml;
		return null;
	}




	private String numberLines(String content) {
		StringBuffer buf = new StringBuffer();
		for (String line : content.split(LINE_PREFIX))
			if (!line.isEmpty())
				if (lineNumbered_)
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

	private DNode getDiagraphDirectContainer(DNode node) {
		for (DNode contnr : getNodes(node.getGraph()))//node.getGraph().getNodes())
			for (DEdge dEdge : getEdges(contnr))
				if (dEdge instanceof DNestedEdge) {
					if (dEdge.getTarget().equals(node))
						return contnr;
				}
		return null;
	}


	private DNode getSource(DEdge edge){
		if (edge instanceof DNestedEdge) //FP150423b
			return ((DNestedEdge)edge).getSource().getNode();
		 else if (edge instanceof DSimpleEdge)
			return ((DSimpleEdge) edge).getSource();
		 else throw new RuntimeException("should not happen in getSource");
	}

	private DNode getDiagraphDirectContainer(DLabeledEdge edg) {
		EClass sr = (EClass) edg.getSemanticRole();
		EClass cont = getEcoreContainer(sr);
		for (DNode contnr : getNodes(edg.getSource().getGraph())){//FP150423c //FP140518//edg.getSource().getGraph().getNodes()) {
			EClass csr = (EClass) contnr.getSemanticRole();
			if (csr == cont)
				return contnr;
		}
		return null;
	}

	private DNode getDiagraphSuperContainer(DNode node) {
		for (DNode sup : getSuperNodes(node)) {
			DNode supcontner = getDiagraphDirectContainer(sup);
			if (supcontner != null)
				return supcontner;
		}
		return null;
	}

	private DNode getDiagraphUnderContainer(DNode node) {
		for (DNode lo : getLowerNodes(node)) {
			DNode locontner = getDiagraphDirectContainer(lo);
			if (locontner != null)
				return locontner;
		}
		return null;
	}



	private List<DNode> getNodes(DGraph graph){ //FP140518aa
		   List<DNode> nodes = new ArrayList<DNode>();
		   for (DGraphElement element : graph.getElements())
			 if(element instanceof DNode)
				 nodes.add((DNode) element);
		   return nodes;
	}


	private List<DEdge> getEdges(DGraph graph) { // FP140518
		List<DEdge> result = new ArrayList<DEdge>();
		for (DGraphElement element : graph.getElements())
			if (element instanceof DEdge)
				result.add((DEdge) element);
		return result;
	}
/*
	protected List<DEdge> getEdges(DNode nod) {
		List<DEdge> result = new ArrayList<DEdge>();
		List<DEdge> all = getEdges(nod.getGraph());
		for (DEdge dEdge : all)
			if (dEdge.getSource().getNode() == nod)
				result.add(dEdge);
		return result;
	}
*/

	public List<DEdge> getEdges(DNode node) {
		List<DEdge> result = new ArrayList<DEdge>();
		for (DGraphElement element : node.getGraph().getElements()){
			if (element instanceof DNestedEdge){
				if (((DNestedEdge) element).getSource().getNode() == node)
					result.add((DEdge) element);
			} else if(element instanceof DSimpleEdge){
				 if (((DSimpleEdge) element).getSource() == node)
					result.add((DEdge) element); //FP150423b
			}
		}
		return result;
	}


	private List<DNode> getLowerNodes(DNode node) {
		List<DNode> result = new ArrayList<DNode>();
		EClass claz = (EClass) node.getSemanticRole();
		for (EClass eClass : lowerClasses(claz)) {
			for (DNode othernode : getNodes(node.getGraph())) {//node.getGraph().getNodes()) {
				if (othernode != node) {
					EClass otherclaz = (EClass) othernode.getSemanticRole();
					if (otherclaz.isSuperTypeOf(eClass)) {
						if (!result.contains(othernode))
							result.add(othernode);
					}
				}
			}
		}
		return result;
	}

	private List<DNode> getSuperNodes(DNode node) {
		List<DNode> result = new ArrayList<DNode>();
		EClass claz = (EClass) node.getSemanticRole();
		for (EClass eClass : claz.getEAllSuperTypes()) {
			List<DNode> allnodes = getNodes(node.getGraph());//node.getGraph().getNodes();
			for (DNode othernode : allnodes) {
				if (othernode != node) {
					EClass otherclaz = (EClass) othernode.getSemanticRole();
					if (otherclaz.isSuperTypeOf(eClass))
						result.add(othernode);
				}
			}
		}
		return result;
	}

	private EClass getEcoreContainer(EClass eclaz) {
		for (EClassifier eClassifier : eclaz.getEPackage().getEClassifiers()) {
			if (eClassifier instanceof EClass)
				for (EReference otherclazref : ((EClass) eClassifier)
						.getEReferences()) {
					EClass otherClass = (EClass) otherclazref.getEType();
					if ((otherClass).isSuperTypeOf(eclaz)
							&& otherclazref.isContainment()) {
						return (EClass) eClassifier;
					}
				}
		}
		return null;
	}

	private boolean isNodeUnderContained(DNode dNode) {
		boolean result = getDiagraphDirectContainer(dNode) != null;
		if (!result)
			result = getDiagraphUnderContainer(dNode) != null;
		return result;
	}

	private boolean isNodeSuperContained(DNode dNode) {
		boolean result = getDiagraphDirectContainer(dNode) != null;
		if (!result)
			result = getDiagraphSuperContainer(dNode) != null;
		return result;
	}

	private String getNodeAllStatus(DNode dNode) {
		if (dNode instanceof DPointOfView)
			return "";
		StringBuffer buf = new StringBuffer();
		EClass ec = (EClass) dNode.getSemanticRole();
		boolean isabstract = ec.isAbstract();
		// boolean isecorecontained = getEcoreContainer(ec) != null;
		boolean isdiagraphcontained = getDiagraphDirectContainer(dNode) != null;
		if (!isdiagraphcontained)
			isdiagraphcontained = getDiagraphSuperContainer(dNode) != null;
		boolean isnotrepresentable = isabstract // || !isecorecontained
				|| !isdiagraphcontained;
		if (isnotrepresentable)
			buf.append(" " + DIAGRAPH_STATUS_NRA);
		if (isabstract)
			buf.append("," + DIAGRAPH_STATUS_ABSTRACT);
		// if (!isecorecontained)
		// buf.append("," + DIAGRAPH_STATUS_NOT_ECORE_CONTAINED);
		// else
		if (!isdiagraphcontained)
			buf.append("," + DIAGRAPH_STATUS_NOT_DIAGRAPH_CONTAINED);
		return buf.toString();
	}

	private boolean isNodeAbstract(DNode dNode) {
		return ((EClass) dNode.getSemanticRole()).isAbstract();
	}




	private List<DNode[]> getInheritedAndContainedForEdgeTrg(DEdge dEdge) {
		List<DNode[]> result = new ArrayList<DNode[]>();
		DNode targetNode = dEdge.getTarget();
		List<DNode> srcConcreteNodes = new ArrayList<DNode>();
		boolean stat_ = false;
		DNode esrc = getSource(dEdge);
		if (!(esrc instanceof DPointOfView))
			stat_ = !getNodeAllStatus(esrc).isEmpty();

		if (!stat_ || (esrc instanceof DPointOfView))
			srcConcreteNodes.add(esrc);
		else
			srcConcreteNodes.addAll(getInheritedRepresentableNodes(esrc));
		List<DNode> inh = new ArrayList<DNode>();
		inh.addAll(getInheritedAndContainedIfNodeAbstract(dEdge, targetNode));
		for (DNode edgeSources : srcConcreteNodes)
			for (DNode dNode : inh) {
				DNode[] couple = new DNode[2];
				couple[0] = edgeSources;
				couple[1] = dNode;
				result.add(couple);
				// result.add(edgeSources.getName() + STR_DOT +
				// dNode.getName());
			}
		return result;
	}

	private List<DNode[]> getInheritedAndContainedForEdgeSrc(DEdge dEdge) {
		List<DNode[]> result = new ArrayList<DNode[]>();
		List<DNode> srcConcreteNodes = new ArrayList<DNode>();
		String sstat = "";
		DNode esrc = getSource(dEdge);
		if (!(esrc instanceof DPointOfView))
			sstat = getNodeAllStatus(esrc);
		boolean stat = !sstat.isEmpty();
		if (!stat)
			srcConcreteNodes.add(esrc);
		else
			srcConcreteNodes.addAll(getInheritedRepresentableNodes(esrc));
		List<DNode> inht = new ArrayList<DNode>();
		String targstat = getNodeAllStatus(dEdge.getTarget());
		if (targstat.isEmpty())
			inht.add(dEdge.getTarget());
		inht.addAll(getInheritedRepresentableNodes(dEdge.getTarget()));
		for (DNode edgeSrc : srcConcreteNodes)
			for (DNode dNodet : inht) {
				DNode[] couple = new DNode[2];
				couple[0] = edgeSrc;
				couple[1] = dNodet;
				result.add(couple);
			}
		return result;
	}

	private boolean isEdgeSourceAbstract(DEdge dEdge) {
		return ((EClass) getSource(dEdge).getSemanticRole()).isAbstract();
	}

	private boolean isEdgeTargetAbstract(DEdge dEdge) {
		return ((EClass) dEdge.getTarget().getSemanticRole()).isAbstract();
	}

	private DEdgeStatus findEdgeStatus(DEdge edg) {
		for (DEdgeStatus status : edgestatus) {
			if (status.edge == edg)
				return status;
		}
		return null;
	}

	private String statusNode(String prefix, DNode dNode, int nodeId) {
		StringBuffer buf = new StringBuffer();
		// n8 = P={ link_L4 , ref_ks }
		// link_L1 -> C nra gen={ P.A , P.B , P.J , P.K , Q.A , Q.B , Q.J , Q.K
		// }
		// L1 Q -> K,J,A,B
		// L1 P -> K,J,A,B

		String prf = prefix + nodeId;
		if (prf.length() == 6) // p1
			prf += " ";
		buf.append(prf + SEP_EQU_SPS// SEP_UNDERSCORE +
				+ dNode.getName());
		EClass ec = (EClass) dNode.getSemanticRole();

		List<DEdge> edges = getEdges(dNode);
		if (!edges.isEmpty()) {
			buf.append(SEP_EQU + SEP_OPEN_BRACKET + SEP_SPACE);
			for (int k = 0; k < edges.size(); k++) {
				DEdge dEdge = edges.get(k);

				buf.append(edgeToKeyword(dEdge));
				DEdgeStatus status = findEdgeStatus(dEdge);
				if (status != null) {
					buf.append(SEP_UNDERSCORE + status.name);
				}
				if (k < edges.size() - 1)
					buf.append(SEP_COMMA_SPS);
			}
			buf.append(SEP_SPACE + SEP_CLOSE_BRACKET);
		} else
			buf.append(SEP_EQU + EXPR_NIL);
		if (!(dNode instanceof DPointOfView) && showStatus)
			buf.append(getNodeAllStatus(dNode));
		if (dNode.getNavigationLink() != null) {
			String navlin = dNode.getNavigationLink();
			navlin = navlin.substring(0, navlin.length()
					- UNDERSCORE_DIAGRAPH_VOCAB_ROOT.length());
			buf.append(SEP_PLUS_SPS + DIAGRAPH_VOCAB_NAV_COLON + navlin);
		}

		// String xxx = getIfAbstractInheritedRepresentableStatus(dNode);

		if (!(dNode instanceof DPointOfView)) { // FP130825
			String inherIfAbstract = getIfAbstractInheritedRepresentableStatus(dNode);
			if (showStatus && !inherIfAbstract.isEmpty())
				buf.append(SEP_SPACE + inherIfAbstract);
		}
		return buf.toString();
	}

	private String getIfAbstractInheritedRepresentableStatus(DNode dNode) {
		String inhers = getInheritedForNodesIfAbstract(dNode);
		if (!inhers.isEmpty())
			return (DIAGRAPH_STATUS_RA + EXPR_OPEN_SET + inhers + EXPR_CLOSE_SET);
		return "";
	}

	private String unName(DEdge dEdge) { // undo diagraph naming (was done to
											// prevent name collision)
		String edgname = dEdge.getName();
		DNode esrc = getSource(dEdge);
		if (edgname.endsWith(esrc.getName()))
			edgname = edgname.substring(0,
					edgname.indexOf(esrc.getName()));
		return edgname;
	}

	class DEdgeStatus {
		public int id;
		DEdge edge;
		public boolean representable;
		public boolean sourceContained;
		public boolean targetContained;
		public boolean targetAbstract;
		public String container;
		public boolean recursive;
		List<DNode[]> infered = new ArrayList<DNode[]>();
		public List<String> associations = new ArrayList<String>();
		public String name;

		public DEdgeStatus() {
			super();
			representable = true;
			sourceContained = true;
			targetContained = true;
			targetAbstract = false;
			id = 0;
		}

	}

	private String statusEdge(String prefix, DEdge dEdge, int i) {
		DEdgeStatus edgeSt = new DEdgeStatus();
		edgeSt.id = i;
		edgeSt.edge = dEdge;
		edgestatus.add(edgeSt);
		StringBuffer buf = new StringBuffer();
		boolean representable = true;
		String prf = prefix + DIAGEN_VOCAB_EDGE + i;
		if (prf.length() == 6)
			prf += " ";
		buf.append(prf + SEP_EQU_SPS); // _UNDER
		String srcl = "";
		DNode srcNode = getSource(dEdge);
		//DNode srcNode = dEdge.getSource().getNode();
		if (isEdgeSourceAbstract(dEdge)) {
			if (showStatus)
				srcl = UNDERSCORE_DIAGRAPH_STATUS_ABSTRACT;
			representable = false;
			edgeSt.representable = false;
		}
		if (!(srcNode instanceof DPointOfView)) {
			if (!isNodeSuperContained(srcNode)) // @def i
				representable = false;
			edgeSt.sourceContained = false;
		}

		if (!isNodeSuperContained(dEdge.getTarget())) { // @def i
			representable = false;
			edgeSt.targetContained = false;
		}

		if (isNodeAbstract(dEdge.getTarget())) // @def i
			representable = false;

		String ekw = edgeToKeyword(dEdge);

		buf.append(srcNode.getName() + STR_DOT + ekw + SEP_UNDERSCORE); // SEP_OPEN_ARRAY
																					// +
																					// SEP_SPACE);

		String edgname = unName(dEdge);
		edgeSt.name = edgname;
		buf.append(edgname + SEP_SPACE + SEP_MINUS + SEP_ARROW + SEP_SPACE); // SEP_COMMA_SPS);
		String trstatus = "";
		DNode trg = dEdge.getTarget();
		if (isEdgeTargetAbstract(dEdge)) {// @def isEdgeTargetAbstract(dEdge)
											// then
											// nra
			if (showStatus)
				trstatus = "";// UNDERSCORE_DIAGRAPH_STATUS_ABSTRACT;
			representable = false;
			edgeSt.targetAbstract = true;
			// trg_representable_ = false;
		}
		// buf.append(DIAGEN_VOCAB_TRG + SEP_EQU + trg.getName() + trstatus);
		buf.append(trg.getName() + trstatus);
		if (dEdge instanceof DLabeledEdge) { // @def for edge if edge is
												// LabeledEdge
												// getContainer(edge) ;
												// if edge.source !=
												// edge.container
												// specify edge
			String srclo = "";
			DLabeledEdge lEdge_ = (DLabeledEdge) dEdge;
			DNode edgeContainer = getDiagraphDirectContainer(lEdge_);
			if (srcNode != edgeContainer) {
				String ad = SEP_COMMA_SPS + DIAGRAPH_VOCAB_CONT + SEP_EQU
						+ edgeContainer.getName() + srclo;
				if (LOG)
					clog(ad);
				buf.append(ad);
				edgeSt.container = edgeContainer.getName();
			}
		}
		String sufx = "";
		boolean recursive = false;
		ENamedElement sr = srcNode.getSemanticRole();
		if (LOG)
			clog("DDTG " + sr.getName());
		if (dEdge instanceof DNestedEdge) {
			EReference r = (EReference) dEdge.getSemanticRole();
			if (r != null) {
				EClass rcl = (EClass) r.getEType();
				if (rcl == srcNode.getSemanticRole()) {
					sufx = " -" + DIAGRAPH_VOCAB_RECURSIVE;
					recursive = true;
					edgeSt.recursive = true;
				}
			} else {
				clog("???? semanticrole is null for " + dEdge.getName());
			}
		}
		String inher = "";
		if (!recursive) {// && dEdge instanceof DOwnedEdge

			for (DNode dNode : getNodes(srcNode.getGraph())){//dEdge.getSource().getGraph().getNodes()) {
				EClass nsc = (EClass) dNode.getSemanticRole();
				if (showStatus && nsc != sr && nsc.isSuperTypeOf((EClass) sr))
					;// inher += SEP_ARROW_ + dNode.getName();
			}
			if (!inher.isEmpty())
				sufx += SEP_SPACE + inher;
			if (showStatus && !representable) {
				sufx += SEP_SPACE;
				sufx += DIAGRAPH_STATUS_NRA;
			}
			buf.append(SEP_SPACE);// + SEP_CLOSE_ARRAY
			buf.append(sufx);
			List<DNode> associatedSourceNodes = new ArrayList<DNode>();
			if (isNodeSuperContained(dEdge.getTarget()))
				;
			if (isEdgeBetweenContainedNodes(dEdge)) {// @def
				String hh_ = "";

				List<DNode[]> inhcpls = inferInheritedAssociations(dEdge);

				if (!inhcpls.isEmpty()) {
					for (int j = 0; j < inhcpls.size(); j++) {
						DNode[] inhcpl = inhcpls.get(j);
						edgeSt.infered.add(inhcpl);
						if (!associatedSourceNodes.contains(inhcpl[0]))
							associatedSourceNodes.add(inhcpl[0]);
						hh_ += inhcpl[0].getName() + "." + inhcpl[1].getName();
						if (j < inhcpls.size() - 1)
							hh_ += SEP_COMMA_SPS;
					}
					hh_ = SEP_SPACE + "gen" + EXPR_OPEN_SET // DIAGRAPH_STATUS_RA
							+ hh_ + EXPR_CLOSE_SET;
					if (showStatus)
						buf.append(hh_);

					String assocs = "";
					for (DNode dNode : associatedSourceNodes) {

						String assoc = " " + dNode.getName() + " -> ";// dEdge.getName()+
						for (DNode[] in : inhcpls) {
							if (in[0] == dNode)
								assoc += in[1].getName() + ",";
						}
						assoc = assoc.substring(0, assoc.length() - 1);
						edgeSt.associations.add(assoc);
						assocs += dEdge.getName() + " " + assoc + CR;

					}
					clog(assocs);

					// n8 = P={ link_L4 , ref_ks }
					// link_L1 -> C nra gen={ P.A , P.B , P.J , P.K , Q.A , Q.B
					// , Q.J
					// , Q.K }
					// L1 Q -> K,J,A,B
					// L1 P -> K,J,A,B

				}
			} else {
				buf.append("_gen");
			}
		}
		buf.append(CR);
		return buf.toString();
	}

	private List<DNode[]> inferInheritedAssociations__old(DEdge edge) {
		DNode srcNode = getSource(edge);
		List<DNode[]> inhLinks = new ArrayList<DNode[]>();
		List<DNode[]> s = getInheritedAndContainedForEdgeSrc(edge);
		for (DNode[] dNodes : s) {
			if (!(srcNode == dNodes[0] && edge.getTarget() == dNodes[1]))
				inhLinks.add(dNodes);
			for (DNode[] dNodet : getInheritedAndContainedForEdgeTrg(edge)) {
				if (!contains(inhLinks, dNodet))
					if (!(srcNode == dNodet[0] && edge.getTarget() == dNodet[1]))
						inhLinks.add(dNodet);
			}
		}
		return inhLinks;
	}

	private List<DNode[]> inferInheritedAssociations(DEdge edge) {
		String buf = "";
		List<DNode[]> inhLinks = new ArrayList<DNode[]>();
		try {

			List<DNode[]> s = getInheritedAndContainedForEdgeSrc(edge);
			for (DNode[] ds : s) {
				String sign = ds[0] + "." + ds[1];
				if (!buf.contains(sign)) {
					inhLinks.add(ds);
					buf += sign;
				}
			}
			List<DNode[]> t = getInheritedAndContainedForEdgeTrg(edge);
			for (DNode[] dt : t) {
				String sign = dt[0] + "." + dt[1];
				if (!buf.contains(sign)) {
					inhLinks.add(dt);
					buf += sign;
				}
			}
		} catch (Exception e) {
			System.err.println("error while inferInheritedAssociations");
		}
		return inhLinks;
	}

	private boolean contains(List<DNode[]> lst, DNode[] other) {
		for (DNode[] cpl : lst)
			if (cpl[0].getName().equals(other[0].getName())
					&& cpl[1].getName().equals(other[1].getName()))
				return true;
		return false;
	}

	private boolean isEdgeBetweenContainedNodes(DEdge edg) {
		DNode srcNode = getSource(edg);
		boolean result = true;
		clog("isEdgeBetweenContainedNodes?" + srcNode.getName()
				+ " -> " + edg.getTarget().getName());
		if (srcNode.getName().equals("A")
				&& edg.getTarget().getName().equals("Tata")) {
			boolean sc = isNodeSuperContained(edg.getTarget());
			boolean uc = isNodeUnderContained(edg.getTarget());
			if (uc)
				clog(edg.getTarget().getName() + " uc by"
						+ getDiagraphUnderContainer(edg.getTarget()).getName());// edg.getTarget().getName()
		}
		if (!(srcNode instanceof DPointOfView)
				&& !isNodeSuperContained(srcNode)
				&& !isNodeUnderContained(srcNode)) {
			clog("(source) " + srcNode.getName() + " is not contained");
			result = false;
		}
		if (!isNodeSuperContained(edg.getTarget())
				&& !isNodeUnderContained(edg.getTarget())) {
			clog("(target) " + edg.getTarget().getName() + " is not contained");
			result = false;
		}
		return result;
	}

	private String associationsToCode(Dsml dsml, String indent) {
		StringBuffer buf_ = new StringBuffer();
		int i = 1;
		buf_.append(LINE_PREFIX + indent + I1 + "Associations{" + CR); // TODO
																		// finalise
																		// keywords
		for (DEdgeStatus status : edgestatus) {
			for (String sassoc : status.associations) {
				buf_.append(LINE_PREFIX + indent + I2 + i++ + " "
						+ status.edge.getName() + " " + sassoc + CR);
			}
		}
		buf_.append(LINE_PREFIX + indent + I1 + "}" + CR);
		return buf_.toString();
	}

	private String modelsToCode(Dsml dsml, String ind1_) { // FP130823
		StringBuffer buf_ = new StringBuffer();
		if (!dsml.getModels().isEmpty()) {
			List<Model> modls = dsml.getModels();
			int k = 0;
			int i = 1;
			for (Model model : modls)
				if (!getModel(model.getName(), dsml.getName()).trim().isEmpty())
					k++;
			if (k > 0) {
				buf_.append(LINE_PREFIX + ind1_ + I1 + DIAGEN_VOCAB_MODELS
						+ " (" + k + "){" + CR);// +
				for (Model model : modls) {
					String modelExcerpt = getModel(model.getName(),
							dsml.getName());
					if (!modelExcerpt.trim().isEmpty()) {
						buf_.append(LINE_PREFIX + ind1_ + I2
								+ DIAGEN_VOCAB_MODEL + "_" + i + " "
								+ DIAGEN_VOCAB_NAME + SEP_EQU_SPS
								+ model.getName() + " " + DIAGEN_VOCAB_EXCERPT
								+ "=[ " + modelExcerpt.trim() + " ]\n");
						String shor = modelExcerpt.trim();
						if (shor.length() > 35) {
							shor = shor.substring(0, 35);
							shor += "...";
						}
						model.setName(model.getName());
						model.setExcerpt(shor); // FP130902
						i++;
					}
				}
				buf_.append(LINE_PREFIX + ind1_ + I1 + "}" + CR);
			} else
				buf_.append(LINE_PREFIX + ind1_ + I1 + DIAGEN_VOCAB_MODELS
						+ " (" + "0" + "){nil}" + CR);
		} else
			buf_.append(LINE_PREFIX + ind1_ + I1 + DIAGEN_VOCAB_MODELS + " ("
					+ "0" + "){nil}" + CR);
		return buf_.toString();
	}

	private String edgesToCode_(List<DEdge> alledges, Notation notation,
			String indent) {
		// edge_7 = M.link[ L3 , ltrg=Z , lsrc=MM ]
		StringBuffer buf = new StringBuffer();
		buf.append(LINE_PREFIX + I1 + I1 + indent + DIAGEN_VOCAB_EDGES + " ("
				+ alledges.size() + "){" + CR);
		edgestatus = new ArrayList<DEdgeStatus>();

		for (int m = 0; m < alledges.size(); m++) {
			DEdge edg = alledges.get(m);
			buf.append(LINE_PREFIX + I1
					+ statusEdge(I1 + I1 + indent, edg, m + 1));
		}
		buf.append(LINE_PREFIX + I1 + I1 + indent + SEP_CLOSE_BRACKET + CR); // edges
		return buf.toString();
	}

	private String nodesToCode(List<DNode> nods, Notation notation,
			String indent) {
		StringBuffer buf = new StringBuffer();
		DGraph dgraph = (DGraph) notation.getNotationBridge();
		int siz=getNodes(dgraph).size();
		buf.append(LINE_PREFIX + I2 + indent + DIAGEN_VOCAB_NODES + " ("
				+ siz+ "){" + CR);
		int cnt = 1;
		for (DNode dNode : nods)
			if (dNode instanceof DPointOfView)
				buf.append(LINE_PREFIX + I1 + statusNode(I2 + indent // +//
																		// DIAGEN_VOCAB_NODE
																		// + "-"
						+ DIAGRAPH_VOCAB_POV_SHORT, dNode, cnt++) + CR);
		for (DNode dNode : nods)
			if (!(dNode instanceof DPointOfView))
				buf.append(LINE_PREFIX
						+ I1
						+ statusNode(I2 + indent + DIAGEN_VOCAB_NODE, dNode,
								cnt++) + CR);
		buf.append(LINE_PREFIX + I2 + indent + SEP_CLOSE_BRACKET + CR); // nodes
		return buf.toString();
	}

	private Map<EClass, List<EClass>> mapLowerClasses(Notation notation) {
		Map<EClass, List<EClass>> m = new HashMap<EClass, List<EClass>>();
		DGraph dgraph = (DGraph) notation.getNotationBridge();
		EClass rootclass = (EClass) dgraph.getPointOfView().getSemanticRole();
		EPackage thepack = rootclass.getEPackage();
		List<EClass> clasups = new ArrayList<EClass>();
		for (EClassifier eClassifierup : thepack.getEClassifiers()) {
			if (eClassifierup instanceof EClass) {
				clasups.add((EClass) eClassifierup);
				m.put((EClass) eClassifierup, new ArrayList<EClass>());
			}
		}
		for (EClass eClassupper : clasups) {
			for (EClassifier eClassifierlo : thepack.getEClassifiers()) {
				if (eClassifierlo instanceof EClass) {
					EClass eClasslower = (EClass) eClassifierlo;
					if (eClassupper != eClasslower
							&& eClassupper.isSuperTypeOf(eClasslower)) {
						List<EClass> lowers = m.get(eClassupper);
						lowers.add(eClasslower);
					}
				}
			}
		}
		return m;
	}

	private List<EClass> lowerClasses(EClass upper) {
		for (EClass ek : lowEClasses.keySet())
			if (ek == upper)
				return lowEClasses.get(ek);
		return null;
	}

	private String notationToCode_(Dsml dsml, String indent) {
		StringBuffer buf = new StringBuffer();
		List<Notation> nots = dsml.getNotations_();
		if (nots.isEmpty()) {
			buf.append(LINE_PREFIX + indent + I1 + DIAGEN_VOCAB_NOTATION_ + "0"
					+ EXPR_NIL + CR);
			return buf.toString();
		}
		int i = 1;
		for (Notation notation : nots) {
			lowEClasses = mapLowerClasses(notation);
			buf.append(LINE_PREFIX + indent + I1 + DIAGEN_VOCAB_NOTATION_ + "_"
					+ (i++) + SEP_OPEN_BRACKET + CR);
			buf.append(LINE_PREFIX
					+ indent
					+ I2
					+ DIAGEN_VOCAB_NAME
					+ SEP_EQU_SPS
					+ (notation.getName() == null ? DIAGEN_VOCAB_NIL : notation
							.getName())
					+ ((dsml.getRootNotation() != null && dsml
							.getRootNotation() == notation) ? SEP_UNDERSCORE
							+ DIAGRAPH_VOCAB_ROOT : SEP_UNDERSCORE
							+ DIAGRAPH_VOCAB_NOT_ROOT) + CR);
			DGraph dgraph = (DGraph) notation.getNotationBridge();
			List<DNode> nods = getNodes(dgraph);//dgraph.getNodes();
			List<DEdge> alledges = new ArrayList<DEdge>();
			for (DNode dNode : nods)
				for (DEdge dEdge : getEdges(dNode))
					alledges.add(dEdge);

			String edgbuf = edgesToCode_(alledges, notation, indent);
			buf.append(nodesToCode(nods, notation, indent));
			// String edgbuf = edgesToCode_(alledges, notation, indent);
			buf.append(edgbuf);

			buf.append(LINE_PREFIX + indent + I1 + SEP_CLOSE_BRACKET + CR);// notation
		}
		return buf.toString();
	}

	private void generateAssociated(Dsml dsml, List<String> relations,
			String keyword, StringBuffer buf, String indent) {
		if (!relations.isEmpty()) {
			int i = 0;
			for (String relation : relations) {
				if (i == 0) {
					buf.append(LINE_PREFIX + indent + I1 + keyword
							+ SEP_OPEN_BRACKET + CR);
					buf.append(LINE_PREFIX + indent + I2 + relation + CR);
				} else
					buf.append(LINE_PREFIX + indent + I2 + relation + CR);
				i++;
			}
			buf.append(LINE_PREFIX + indent + I1 + SEP_CLOSE_BRACKET + CR);
		} else
			buf.append(LINE_PREFIX + indent + I1 + keyword + EXPR_NIL + CR);
	}

	private void generateOrigin(Dsml dsml, StringBuffer buf, String indent) {
		if (dsml.getContext().size() > 0) {
			int i = 0;
			for (String d : dsml.getContext()) {
				if (i == 0) {
					d = d.replaceAll(SEP_EQU, STR_COLON); // = is a separator
					if (d.startsWith(DIAGEN_VOCAB_CONTEXT))
						d = d.substring(DIAGEN_VOCAB_CONTEXT.length());
					buf.append(LINE_PREFIX + indent + I1 + DIAGEN_VOCAB_CONTEXT
							+ SEP_OPEN_BRACKET + CR);
					buf.append(LINE_PREFIX + indent + I2 + d + CR);
				} else {
					buf.append(LINE_PREFIX + indent + I2 + d + CR);
				}
				i++;
			}
			buf.append(LINE_PREFIX + indent + I1 + SEP_CLOSE_BRACKET + CR);
		} else
			buf.append(LINE_PREFIX + indent + I1 + DIAGEN_VOCAB_CONTEXT
					+ "{nil}" + CR);
	}

	private void generateContext(Dsml dsml, StringBuffer buf, String indent) {
		buf.append(LINE_PREFIX
				+ indent
				+ I1
				+ DIAGEN_VOCAB_ORIGIN
				+ SEP_EQU_SPS
				+ (dsml.getOrigin() == null ? DIAGEN_VOCAB_NIL : dsml
						.getOrigin()) + CR);
		buf.append(LINE_PREFIX
				+ indent
				+ I1
				+ DIAGEN_VOCAB_KNOWN_AS
				+ SEP_EQU_SPS
				+ (dsml.getKnownAs() == null ? DIAGEN_VOCAB_NIL : dsml
						.getKnownAs()) + CR);
	}

	private String dsmlToCode(Dsml dsml, int dsmlcount, String indent) {
		StringBuffer buf = new StringBuffer();
		buf.append(LINE_PREFIX + indent + I1 + DIAGEN_VOCAB_NAME + SEP_EQU_SPS
				+ dsml.getName() + CR);
		if (showStatus) {
			generateLeftParent(dsml, buf, indent);
			generateRightParent(dsml, buf, indent);
			generateAssociated(dsml, dsml.getRequireDetails(),
					DIAGEN_VOCAB_REQUIRED_, buf, indent);
			generateAssociated(dsml, dsml.getRelatedDetails(),
					DIAGEN_VOCAB_RELATED_, buf, indent);
			generateContext(dsml, buf, indent);
			generateOrigin(dsml, buf, indent);
		}
		return buf.toString();
	}

	private void generateLeftParent(Dsml dsml, StringBuffer buf, String indent) {
		if (dsml.getLeftParentDetails().size() > 0) {
			int i = 0;
			for (String d : dsml.getLeftParentDetails()) {
				if (i == 0)
					buf.append(LINE_PREFIX + indent + I1
							+ DIAGEN_VOCAB_LEFT_PARENT + SEP_EQU_SPS + d + CR);
				else {
					d = d.replaceAll(SEP_EQU, STR_COLON); // SEP_EQU is a
															// separator
					if (i < 2) {
						buf.append(LINE_PREFIX + indent + I1
								+ DIAGEN_VOCAB_NOTICES + SEP_OPEN_BRACKET + CR);
						buf.append(LINE_PREFIX + indent + I2 + d + CR);
					} else
						buf.append(LINE_PREFIX + indent + I2 + d + CR);
				}
				i++;
			}
		} else
			buf.append(LINE_PREFIX + indent + I1 + DIAGEN_VOCAB_LEFT_PARENT
					+ SEP_EQU_SPS + DIAGEN_VOCAB_NIL + CR);
	}

	private void generateRightParent(Dsml dsml, StringBuffer buf, String indent) {
		if (dsml.getRightParentDetails().size() > 0) {
			int i = 0;
			for (String d : dsml.getRightParentDetails()) {
				if (i == 0)
					buf.append(LINE_PREFIX + indent + I1
							+ DIAGEN_VOCAB_RIGHT_PARENT + SEP_EQU_SPS + d + CR);
				else {
					d = d.replaceAll(SEP_EQU, ":"); // = is a separator
					if (i < 2) {
						buf.append(LINE_PREFIX + indent + I1
								+ DIAGEN_VOCAB_NOTICES + SEP_OPEN_BRACKET + CR);
						buf.append(LINE_PREFIX + indent + I2 + d + CR);
					} else
						buf.append(LINE_PREFIX + indent + I2 + d + CR);
				}
				i++;
			}
		} else
			buf.append(LINE_PREFIX + indent + I1 + DIAGEN_VOCAB_RIGHT_PARENT
					+ SEP_EQU_SPS + DIAGEN_VOCAB_NIL + CR);
	}

	private String diagramsToCode(Dsml dsml, String ind) {
		StringBuffer buf = new StringBuffer();
		int i = 1;
		int k = 0;
		for (org.isoe.diagraph.megamodel.EcoreDiagram diagram : dsml
				.getEcoreDiagrams())
			if (diagram.getDiagramPictureURI() != null)
				k++;
		if (k == 0)
			buf.append(LINE_PREFIX + ind + I1 + DIAGEN_VOCAB_DIAGRAM
					+ SEP_UNDERSCORE + "0" + SEP_EQU_SPS + DIAGEN_VOCAB_NIL
					+ CR);
		else
			for (org.isoe.diagraph.megamodel.EcoreDiagram diagram : dsml
					.getEcoreDiagrams()) {
				if (diagram.getDiagramPictureURI() != null) {
					String diag = diagram.getDiagramPictureURI();
					if (diag.contains("/model/"))
						diag = diag.substring(diag.indexOf("/model/"));
					diag = diag.replace("jpeg", "ecorediag");
					diagram.setName(diag);
					buf.append(LINE_PREFIX + ind + I1 + DIAGEN_VOCAB_DIAGRAM
							+ "_" + (i++) + SEP_EQU_SPS + diag + CR);
				}
			}
		return buf.toString();
	}

	private String edgeToKeyword(DEdge dEdge) {
		String edg = dEdge
				.getClass()
				.getSimpleName()
				.substring(
						0,
						dEdge.getClass().getSimpleName().length()
								- "Impl".length());
		if (edg.equals(DNestedEdge.class.getSimpleName()))
			edg = DIAGRAPH_VOCAB_CREF;
		else if (edg.equals(DAffixedEdge.class.getSimpleName()))
			edg = DIAGRAPH_VOCAB_AFX_;
		else if (edg.equals(DCompartmentEdge.class.getSimpleName()))
			edg = DIAGRAPH_VOCAB_KREF;
		else if (edg.equals(DReference.class.getSimpleName()))
			edg = DIAGRAPH_VOCAB_REF;
		else if (edg.equals(DLabeledEdge.class.getSimpleName()))
			edg = DIAGRAPH_VOCAB_LINK;
		return edg;
	}


	private Collection<? extends DNode> getInheritedAndContainedIfNodeAbstract(
			DEdge dEdge, DNode nod) {
		DNode srcNode = getSource(dEdge);
		EClass claz = (EClass) nod.getSemanticRole();
		List<DNode> inh = new ArrayList<DNode>();
		if (claz.isAbstract()) {
			for (DNode dNode : getNodes(srcNode.getGraph())) {//dEdge.getSource().getGraph().getNodes()) {
				EClass sr = (EClass) dNode.getSemanticRole();
				if (sr != claz
						// zz && !sr.isAbstract()
						&& getEcoreContainer(sr) != null
						&& claz.isSuperTypeOf(sr)) {
					inh.add(dNode);
				}
			}
		}
		return inh;
	}

	List<DNode> getInheritedIfNodeAbstract(DEdge dEdge, DNode nod) {
		EClass claz = (EClass) nod.getSemanticRole();
		List<DNode> inh = new ArrayList<DNode>();
		if (claz.isAbstract()) {
			for (DNode dNode : getNodes(getSource(dEdge).getGraph())) {//dEdge.getSource().getGraph().getNodes()) {
				EClass sr = (EClass) dNode.getSemanticRole();
				if (sr != claz
				// zz && !sr.isAbstract()
						&& claz.isSuperTypeOf(sr)) {
					inh.add(dNode);
				}
			}
		}
		return inh;
	}

	private String getInheritedForNodesIfAbstract(DNode dNode) {
		String inhers = "";
		EClass nclaz = (EClass) dNode.getSemanticRole();
		if (nclaz.isAbstract()) {
			for (DNode no : getNodes(dNode.getGraph())) {//dNode.getGraph().getNodes()) {
				EClass nsc = (EClass) no.getSemanticRole();
				if (nsc != nclaz
				// zz && !nsc.isAbstract()
						&& ((EClass) nclaz).isSuperTypeOf(nsc)) {
					clog(nsc.getName());
					inhers += nsc.getName() + SEP_COMMA_SPS;
				}
			}
		}
		if (!inhers.isEmpty())
			inhers = inhers.substring(0,
					inhers.length() - SEP_COMMA_SPS.length());
		return inhers;
	}

	private List<DNode> getInheritedRepresentableNodes(DNode dNode) {
		List<DNode> result = new ArrayList<DNode>();
		EClass nclaz = (EClass) dNode.getSemanticRole();

		for (DNode no : getNodes(dNode.getGraph())) {//dNode.getGraph().getNodes()) {
			EClass nsc = (EClass) no.getSemanticRole();
			if (nsc == null) {
				clog("getInheritedRepresentableNodes nosemantic role for "
						+ no.getName());
			}

			if (nsc != null && nsc != nclaz
					// zz && !nsc.isAbstract()
					&& getEcoreContainer(nsc) != null
					&& ((EClass) nclaz).isSuperTypeOf(nsc)) {
				clog(nsc.getName());
				result.add(no);
			}
		}
		return result;
	}

	private IProject getProject(String name) {
		return megamodelHandler.getProject(name);
	}
/*
	private List<Dsml> getActiveDsmls() {
		return megamodelHandler.getActiveDsmls();
	}
*/
	private List<Dsml> getMegamodelDsmls_() {
		return megamodelHandler.getMegamodelDsmls();
	}

	private IStatus errorStatus(Exception e) {
		return megamodelHandler.errorStatus(e);
	}

	private IStatus errorStatus(String mesg) {
		return megamodelHandler.errorStatus(mesg);
	}




	//@Override
	private IStatus exportDsmls_old_nu(SubProgressMonitor monitor, List<Dsml> dsmls_,String langname,
			int n, File destFile) {
		monitor.beginTask("Exporting " + n + " " + DIAGEN_VOCAB_DSMLS, n * 3);

		boolean whole = destFile != null;

		dones = new ArrayList<IProject>();

		if (dsmls_==null || dsmls_.isEmpty())
			throw new RuntimeException("no available languages");

		if (langname!=null && !langname.isEmpty()){
			Dsml dsml_nu = findDsml(dsmls_, langname); //FP140604modif
		}

		Dsml firstdsml_nu = dsmls_.get(0);


		try {
			linecounter = 1;
			FileWriter fw1 = null;
			if (whole) {
				fw1 = new FileWriter(destFile);
				addToBuffer(LINE_PREFIX + DIAGEN_VOCAB_MEGAMODEL
						+ SEP_OPEN_PARENTH + dsmls_.size() + SEP_CLOSE_PARENTH
						+ SEP_OPEN_BRACKET + CR);
			}
			int dsmlcount = 0;
			for (Dsml dsml : dsmls_) {
				if (monitor.isCanceled())
					return Status.CANCEL_STATUS;
				IProject p = getProject(dsml.getName());
				if (p != null) {
					if (!dsml.getNotations_().isEmpty()) {
						dones.add(p);
						monitor.subTask("export Dsml " + dsml.getName());// .substring(model.lastIndexOf("/")));
						String buf = "";
						dsmlcount++;
						String indent = "";
						if (whole)
							indent = I1;
						String dsmlabel = null;
						if (whole)
							dsmlabel = DIAGEN_VOCAB_DSML + SEP_UNDERSCORE
									+ dsmlcount + SEP_OPEN_BRACKET;
						else {
							linecounter = 1;
							dsmlabel = DIAGEN_VOCAB_DSML + SEP_OPEN_BRACKET;
						}
						buf += LINE_PREFIX + indent + dsmlabel + CR;
						buf += dsmlToCode(dsml, dsmlcount, indent);
						buf += diagramsToCode(dsml, indent);
						buf += notationToCode_(dsml, indent);
						if (showStatus) {
							buf += associationsToCode(dsml, indent);
							buf += modelsToCode(dsml, indent);
						}

						buf += LINE_PREFIX + indent + SEP_CLOSE_BRACKET + CR;
						FileWriter fw2 = null;
						if (whole)
							addToBuffer(buf);// fw_.write(dtc_);
						else {
							fw2 = new FileWriter(new File(p.getLocation()
									.toFile().getAbsolutePath()
									+ File.separator
									+ DIAGEN_VOCAB_MODEL
									+ File.separator
									+ dsml.getName()
									+ "_"
									+ DIAGEN_NAME + "." + DIAGEN_EXT));

							String b2 = buf;

							for (String lg : getTheLegend())
								b2 += LINE_PREFIX + lg + CR;

							fw2.write(numberLines(b2));
							fw2.close();
						}
					}
				}
			}
			if (whole) {
				addToBuffer(LINE_PREFIX + SEP_CLOSE_BRACKET + CR);
				String b2 = getBuffer();
				for (String lg : getTheLegend())
					b2 += LINE_PREFIX + lg + CR;
				fw1.write(numberLines(b2));
				fw1.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return errorStatus(e);
		} catch (Exception e) {
		   InterpreterPlugin.getInstance().addError(e.toString()+" in exportLanguage");
		   return errorStatus(e.toString()+" in exportLanguage");
		}
		return Status.OK_STATUS;
	}


	//FP140622voiraaa
	/*
	@Override
	public IStatus genGrammarDsml_old(IProgressMonitor monitor, String langName) throws CoreException {
		List<Dsml> dsmls_ = getMegamodelDsmls_();
		File destFile = new File(megamodelHandler.getProject(IHandleMegamodelJob.MGM_KEY)
				.getLocation().append(DIAGEN_NAME).addFileExtension(DIAGEN_EXT)
				.toOSString());
		if (destFile.exists()) {
			destFile.delete();
			refreshProject(megamodelHandler.getProject(IHandleMegamodelJob.MGM_KEY));
		}
		if (dsmls_.isEmpty())//FP140522
			return new Status(IStatus.INFO, DiagenPlugin.PLUGIN_ID, "empty dsmls");

		IStatus status = exportDsmls_old(new SubProgressMonitor(monitor, 90),
				dsmls_, langName,dsmls_.size(), null);
		status = exportDsmls_old(new SubProgressMonitor(monitor, 90), dsmls_, langName,
				dsmls_.size(), destFile);
		for (IProject project : dones)
			refreshProject(project);
		refreshProject(megamodelHandler.getProject(IHandleMegamodelJob.MGM_KEY));
		return status;
	}

*/

	@Override
	public IStatus exportLanguage(SubProgressMonitor monitor,
			List<DGraph> dgraphs, String langname, int n, File destFile) { //FP140621
		throw new RuntimeException("TODO implement exportDGraphs");
		//return null;
	}

	@Override
	public IStatus genGrammarDGraph(IProgressMonitor monitor, String langName)
			throws CoreException {
		throw new RuntimeException("TODO implement genGrammarDGraph");
		//return null;
	}



	/*
	@Override
	protected IStatus work(IProgressMonitor monitor) throws CoreException {
		monitor.beginTask(this.getName(), 100);
		megamodel_prj = getProject();
		if (!megamodel_prj.exists())
			return errorStatus("megamodel does not exist in the workspace");
		connectAll();
		getAllModels();
		getKeywords();
		getSeparators();
		getGlossary();
		IStatus status = null;
		monitor.subTask("genLanguage");
		status = genGrammar(monitor);
		if (status != Status.OK_STATUS)
			return status;
		monitor.done();
		return status;
	}


		@Override
	protected void doit(String name, DiagramDocumentEditor editor,
			IProgressMonitor monitor) throws CoreException {
	}
	*/

}
