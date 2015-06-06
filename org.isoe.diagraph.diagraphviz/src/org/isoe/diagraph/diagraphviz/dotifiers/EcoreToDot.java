/*******************************************************************************
 * Copyright (c) 2008, Jean-RÃ©my Falleri.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Jean-Rémy Falleri - initial API and implementation
 *    François Pfister -
 *    1)refactoring - extention  to Diagraph
 *    2)extendable with other packages -
 *    3)works inside or outside Eclipse
 *    4)adaptation to graphviz html labels
 *    5)filters, replacements, color personalization, ....
 *    6)mergeability with CompiledDotGraph
 *    7)todo: implement visitor pattern
 *******************************************************************************/

package org.isoe.diagraph.diagraphviz.dotifiers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lirmm.dotutils.Dotify;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.isoe.diagraph.diagraphviz.dotifiers.EObjectEdge.AssociationType;
import org.isoe.diagraph.diagraphviz.dotifiers.styles.EcoreDotStyle;
import org.isoe.diagraph.diagraphviz.dotutils.GraphVizConverter;
import org.isoe.diagraph.diagraphviz.dotutils.DotConstants;
import org.isoe.diagraph.diagraphviz.dotutils.DotWriter;
import org.isoe.diagraph.diagraphviz.dotutils.GraphwizInvoker;
import org.isoe.extensionpoint.graphviz.IDotifier;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.platform.resolver.Activator; //must be physically present in the test directory


import org.isoe.diagraph.controler.IDiagraphControler;  //FP140707refactored
public class EcoreToDot extends DotWriter implements DotConstants,
		DotVisitable, GraphVizConverter {

	private static final String ATTR_EDGE_COLOR = "yellow";
	private static final String REF_COLOR0_ = "green";
	private static final String REF_COLOR1 = "red";
	private static final String INHERITANCE_COLOR = "black";
	private static int filterElements = 1;
	private List<EClassifier> registeredClassifiers = new ArrayList<EClassifier>();
	private IDotifier handler;
	private String asDot;
	private List<String> nodesp1;
	private List<String> nodesp2;
	private List<String> edges0;
	private List<String> edges1;
	private String snodes0;
	private String snodes1;
	private String sedges0;
	private String sedges1;
	private Map<EObject, String> edgeEnd0;
	private Map<EObject, String> edgeEnd1;
	private List<EObjectEdge> edges = new ArrayList<EObjectEdge>();
	private List<EObjectEdge> half = new ArrayList<EObjectEdge>();

	private static final boolean LOG = false;
	private static final boolean LOG_CONSOLE = false;

	protected ResourceSet resourceSet;

	protected String outputFile;
	protected String modelFile;

	private URI uri;

	private Set<String> visitedNodes;
	private EPackage rootPackage;
	private boolean drawAttributes;
	private boolean drawReferencesToEnum;
	private boolean drawOperations;
	private boolean drawDerived;

	private IEcoreToDotStyle ecoreToDotStyle;
	private Map<EObject, String> endEdgeNodes;
	private IDiagraphControler mhandler;

	public Map<EObject, String> getEdgeEnds(int i) {
		if (i == 0)
			return edgeEnd0;
		else
			return edgeEnd1;
	}

	/**
	 * Activator must be physically present in the test directory
	 */
	public static void testme() {

	}

	public static void testOutsideEclipse_(String plugin, String domain,
			EPackage extention) {
		boolean drawAttributes = true;
		boolean drawReferencesToEnum = false;
		boolean drawDerived = false;
		boolean drawOperations = false;
		String ws = Activator.getWorkspacePath(); // org.isoe.platform.resolver.Activator
													// must be physically
													// present in the test
													// directory
		String m2 =  DParams.MODEL_FOLDER_SEP
				+ domain + ".ecore";
		String dotlocation =  DParams.MODEL_FOLDER_SEP
				+ domain + "-ecore.dot";
		EcoreToDot edot = new EcoreToDot(null, ws + plugin + "/" + m2, ws
				+ plugin + "/" + dotlocation, extention, drawAttributes,
				drawReferencesToEnum, drawDerived, drawOperations,
				EcoreDotStyle.getInstance(), null);// CopyOfEcoreToDotok
		ResourceSet r = edot.handleModel();// null,null,null,null
		edot.createDotGraph();
	}

	boolean isFiltered(final EClassifier eclass) {
		if (filterElements == 1)
			return ecoreToDotStyle.getHiddenEClasses1().contains(
					eclass.getName());
		else if (filterElements == 2)
			return ecoreToDotStyle.getHiddenEClasses2().contains(
					eclass.getName());
		else
			return false;
	}

	boolean mustDrawAttributes(final EClassifier eclass) {
		if (!drawAttributes)
			return false;
		else if (ecoreToDotStyle.isFilterAttributes())
			return ecoreToDotStyle.eClassesWithVisibleAttributes().contains(
					eclass.getName());
		else
			return true;
	}

	public Resource loadModel(ResourceSet resourceSet, URI uri) { // static
		Resource result = resourceSet.getResource(uri, true);
		try {
			result.load(null);
			handler.log("loadModel " + uri.toString());
			EcoreUtil.resolveAll(resourceSet);
		} catch (IOException e) {
			throw new RuntimeException(" error while loading model "
					+ uri.toString());
		}
		return result;
	}

	private EObject getModelFromResourceSet(final URI uri) { // FP130205
		for (Resource resource : resourceSet.getResources())
			if (resource.getURI().toString().equals(uri.toString()))
				return resource.getContents().get(0);
		return null;
	}

	private Resource getModel(URI uri) {// to clean //FP130205
		EObject model = getModelFromResourceSet(uri);
		if (model == null)
			model = loadModel(resourceSet, uri).getContents().get(0);
		return model.eResource();
	}

	boolean findTargetInEdges(EObject eObject) {
		for (EObjectEdge ed : edges)
			if (ed.getTarget().equals(eObject))
				return true;
		return false;
	}

	public ResourceSet handleModel() {// URI uribase, String domain, String
		// xmlns,String nsURI
		uri = URI.createFileURI(new File(modelFile).getAbsolutePath());
		rootPackage = (EPackage) getModel(uri).getContents().get(0); // FP130205
		addEPackage(rootPackage);

		for (EObjectEdge hal : half) { // FP130107
			EObject targ = hal.getTarget();
			if (!findTargetInEdges(targ)) {
				edges.add(hal);
				if (LOG)
				  clog("added " + hal.toString());
			}
		}
		return rootPackage.eResource().getResourceSet();
	}

	@Override
	public String getOuputPath() {
		return this.outputFile;
	}


    private void clog(String msg){
       	 if (LOG_CONSOLE && mhandler!=null)
			   mhandler.log(null, msg+"\r\n");
		 System.out.println(msg);

    }

	public EcoreToDot(ResourceSet rs, String inputFile, String outputFile,
			EPackage extensionPackage, boolean drawAttributes,
			boolean drawReferencestoen, boolean drawDerived,
			boolean drawOperations, IEcoreToDotStyle ecoreToDotStyle,
			IDotifier handler) {
		this.ecoreToDotStyle = ecoreToDotStyle;// EcoreToDotStyle.getInstance();
		this.handler = handler;
		nodesp1 = new ArrayList<String>();
		nodesp2 = new ArrayList<String>();
		edges0 = new ArrayList<String>();
		edges1 = new ArrayList<String>();
		edgeEnd0 = new HashMap<EObject, String>();
		edgeEnd1 = new HashMap<EObject, String>();

		this.outputFile = outputFile;
		this.modelFile = inputFile;
		mhandler = handler.getUI();
		clog("invoke dot "+this.getClass().getSimpleName() + "   "
				+ inputFile + " -> " + outputFile);

		if (rs == null)
			resourceSet = new ResourceSetImpl();
		else
			resourceSet = rs;
		resourceSet
				.getResourceFactoryRegistry()
				.getExtensionToFactoryMap()
				.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
						new EcoreResourceFactoryImpl());
		if (extensionPackage != null)
			resourceSet.getPackageRegistry().put(extensionPackage.getNsURI(),
					extensionPackage);

		visitedNodes = new HashSet<String>();

		this.drawDerived = drawDerived;
		this.drawOperations = drawOperations;
		this.drawReferencesToEnum = drawReferencestoen;
		this.drawAttributes = drawAttributes;
	}

	public EcoreToDot(ResourceSet rs, String inputFile, String outputFile,
			EPackage extension, IEcoreToDotStyle ecoreToDotStyle,
			IDotifier handler) {
		this(rs, inputFile, outputFile, extension, true, true, false, false,
				ecoreToDotStyle, handler);
	}

	private void registerEdgeTarget(EClass trgClass, String tg) {
		if (((EClassifier) trgClass).getEPackage() == rootPackage) {
			if (edgeEnd0.get(trgClass) == null) {
				edgeEnd0.put(trgClass, tg);
				if (LOG)
				  clog(trgClass.getName() + " " + tg);
			}
		} else {
			if (edgeEnd1.get(trgClass) == null)
				edgeEnd1.put(trgClass, tg);
		}
	}

	protected void addEPackage(EPackage p) {
		for (EClassifier clsf : p.getEClassifiers()) {

			if (clsf instanceof EClass) {
				if (!visitedNodes.contains(clsf.getName())) {
					visitedNodes.add(((EClass) clsf).getName());
					if (clsf.getEPackage() == rootPackage
							&& (!p.getName().equals("diagraph"))) {
						registerEdgeTarget((EClass) clsf, getObjectId(clsf));

						// if (nodes1_.get(clsf) == null)
						// nodes1_.put(clsf, getObjectId(clsf));
					}
					addEClassAndAssociatedElements((EClass) clsf);
				}
			} else if (clsf instanceof EEnum) {
				if (!visitedNodes.contains(clsf.getName())) {
					visitedNodes.add(clsf.getName());
					addEEnum((EEnum) clsf);
				}
			}
		}
		for (EPackage sp : p.getESubpackages())
			addEPackage(sp);
	}

	protected void addEEnum(EEnum enm) {
		// TODO convert into html
		String nodeDef = enm.getName() + dotStartRecordShape(COLOR_BLUE);
		nodeDef += m2StartLabel(enm) + "|";
		for (EEnumLiteral lit : enm.getELiterals())
			nodeDef += lit.getLiteral() + "\\n";
		nodeDef += m2EndLabel() + dotEnd();
		String classid = getObjectId(enm);
		addNode_(enm, nodeDef, classid + ":" + PORT_PREFIX + classid);
	}

	void addNode_(EClassifier classifier, String label, String classid) {
		if (!isFiltered(classifier)) {
			EObjectEdge halfedge = new EObjectEdge(null, null, null,
					classifier, null, classid, null,
					AssociationType.TargetOnly, true, true, null);
			half.add(halfedge);
			registeredClassifiers.add(classifier);
			if (classifier.getEPackage() == rootPackage) {
				nodesp1.add(label);
			} else {
				nodesp2.add(label);
			}
		}
	}

	private void addFeatures(EClass eclass, StringBuffer classBuffer) {
		boolean drawAtts = mustDrawAttributes(eclass);
		String attributesAdded = addFeatures(eclass, drawAtts);
		boolean atLeastOneAdded = attributesAdded != null
				&& !attributesAdded.isEmpty();
		if (drawAtts) {
			if (atLeastOneAdded) {
				classBuffer.append("<TR><TD><TABLE border=\"0\">\n");
				classBuffer.append(attributesAdded);
			} else
				addEmptyRow(classBuffer);
			if (atLeastOneAdded)
				classBuffer.append("</TABLE></TD></TR>\n");
		}
	}

	String getFinalName(String name) {
		if (!ecoreToDotStyle.isReplace())
			return name;
		else {
			String newName = ecoreToDotStyle.replacements().get(name);
			if (newName != null)
				return newName;
			else
				return name;
		}
	}

	/* add edges * */

	protected void addAttributeEnumEdge(EAttribute eattr, EClass cls,
			EDataType dtype) {
		String st = startArrow(getObjectId(cls));
		String tg = endArrow(getObjectId(dtype));
		String edg = st + " -> " + tg;
		String relDef = edg + dotStartArrow(true, ATTR_EDGE_COLOR)
				+ m2CardinalityLabel(eattr) + dotEnd();
		edges0.add(relDef);// FP130202
	}

	protected void addInheritanceEdge(EClass eclass, EClass supCls) {
		if (!isFiltered(eclass) && !isFiltered(supCls)) {
			String st = startArrow(getObjectId(eclass));
			String tg = endArrow(getObjectId(supCls));
			String edg = st + " -> " + tg;
			edges0.add(edg + m2StartInverseLink(INHERITANCE_COLOR) + dotEnd());// FP130202
		}
	}

	protected void addReferenceEdge(EClass srcClass, EClass trgClass_,
			EReference eref) {
		if (!isFiltered(srcClass) && !isFiltered(trgClass_)) {
			String st = startArrow(getObjectId(srcClass));
			String tg = endArrow(getObjectId(trgClass_));
			registerEdgeTarget(trgClass_, tg);// FP130202x
			EObjectEdge eo = new EObjectEdge(null, null, srcClass, trgClass_,
					st, tg, eref.getName(), AssociationType.Reference, true,
					true, eref);
			edges.add(eo);

		}
	}

	/* end add edges * */

	protected void addEClassAndAssociatedElements(EClass eclass) {

		String finalColor = ecoreToDotStyle.eClassColors()
				.get(eclass.getName());

		String color = eclass.isAbstract() ? ABSTRACT_CLASS_COLOR
				: CONCRETE_CLASS_COLOR;
		if (finalColor != null)
			color = finalColor;
		String classid = getObjectId(eclass);

		StringBuffer classBuffer = new StringBuffer();

		classBuffer.append(startTopNode(eclass, classid));

		classBuffer.append("<TABLE bgcolor=\"" + color
				+ "\" cellspacing=\"-1\" border=\"1\" ><TR><TD>"
				+ getFinalName(eclass.getName()) + "</TD></TR>\n");

		addFeatures(eclass, classBuffer);

		classBuffer.append("</TABLE>\n");
		addOperationsLabels(eclass, classBuffer);
		String end = "</TD></TR></TABLE>>];\n";
		classBuffer.append(end);

		classid += ":" + PORT_PREFIX + classid;
		addNode_(eclass, classBuffer.toString(), classid);

		for (EClass supCls : eclass.getESuperTypes()) {
			if (!visitedNodes.contains(supCls.getName())) {
				visitedNodes.add(supCls.getName());
				addEClassAndAssociatedElements(supCls);
			}
			addInheritanceEdge(eclass, supCls);

		}
	}

	protected String addFeatures(EClass cls, boolean mustWriteAttribute) {
		String result = "";
		for (EStructuralFeature feature : cls.getEStructuralFeatures())
			result += addFeatureElement(feature, cls, mustWriteAttribute);
		return result;
	}

	protected void visitReferenceEdge(EClass cls, EReference eref) {
		EClass type = eref.getEReferenceType();
		if (!visitedNodes.contains(type.getName())) {
			visitedNodes.add(type.getName());
			addEClassAndAssociatedElements(type);
		}
		addReferenceEdge(cls, type, eref);
	}

	protected String addFeatureElement(EStructuralFeature feature, EClass cls,
			boolean mustWriteAttribute) {
		// Checks if the feature is displayable.
		if (!(drawDerived == false && feature.isDerived() == true)) {
			if (feature instanceof EReference)
				visitReferenceEdge(cls, (EReference) feature);
			else if (feature instanceof EAttribute) {
				return addAttributeNodeOrEdge(cls, (EAttribute) feature,
						mustWriteAttribute);
			}
		}
		return "";
	}

	private boolean isVisible(EAttribute eattr) {
		boolean showit = !ecoreToDotStyle.isHideAttributes();
		if (showit
				&& ecoreToDotStyle.getHiddenAttributes().contains(
						eattr.getName()))
			showit = false;
		return showit;
	}

	protected String addAttributeNodeOrEdge(EClass cls, EAttribute eattr,
			boolean mustWriteAttribute) {
		EDataType dtype = eattr.getEAttributeType();
		if (!(dtype instanceof EEnum)) {
			if (mustWriteAttribute) {
				if (isVisible(eattr))
					return addAttributeNodeInHtml(eattr,
							getFinalName(eattr.getName()), cls, dtype);
			}
		} else if (drawReferencesToEnum)
			addAttributeEnumEdge(eattr, cls, dtype);
		else {
			if (mustWriteAttribute) {
				if (isVisible(eattr))
					return addAttributeNodeInHtml(eattr,
							getFinalName(eattr.getName()), cls, dtype);
			}
		}
		return "";
	}

	/*
	protected void addAttributeNodeInRecordShape_nu(EAttribute eattr,
			EClass cls, EDataType dtype, StringBuffer classBuffer) {
		if (mustDrawAttributes(cls)) {
			classBuffer.append(eattr.getName() + "[" + m2Cardinality(eattr)
					+ "]");
			classBuffer.append(": " + dtype.getName());
			classBuffer.append("\\n");
		}
	}

	protected void addAttributeNodeInHtml_nu(EAttribute eattr, EClass cls,
			EDataType dtype, StringBuffer classBuffer) {
		if (mustDrawAttributes(cls)) {
			EClass contnr = (EClass) eattr.eContainer();
			String attrid = contnr.getName() + "_" + eattr.getName();
			String line = "<TR><TD port=\"" + PORT_PREFIX + attrid
					+ "\" align=\"left\">" + eattr.getName() + ": "
					+ dtype.getName() + "</TD></TR>";
			classBuffer.append(line);
		}
	}
	*/

	protected void addOperationsLabels(EClass cls, StringBuffer classBuffer) {
		if (drawOperations) {
			classBuffer.append("|");
			for (EOperation operation : cls.getEOperations())
				addOperationLabel(operation, classBuffer);
		}
	}

	protected void addOperationLabel(EOperation operation,
			StringBuffer classBuffer) {
		classBuffer.append(operation.getName() + "(");
		String params = "";
		for (EParameter param : operation.getEParameters())
			params += (param.getName() + ": " + param.getEType().getName() + ",");
		if (operation.getEParameters().size() > 0)
			classBuffer.append(params.substring(0, params.length() - 1));
		classBuffer.append(")");
		if (operation.getEType() != null)
			classBuffer.append(": " + operation.getEType().getName());
		classBuffer.append("\\n");
	}

	protected void toDotFile_(String output) throws IOException {
		FileWriter fw = new FileWriter(output);
		fw.append(asDot);
		fw.close();
	}

	public CompiledGraph createDotGraph() {
		try {
			asDot = toDot();
			for (EObject eObject : edgeEnd0.keySet()) {
				if (LOG)
					clog(edgeEnd0.get(eObject));
			}
			toDotFile_(outputFile);
			GraphwizInvoker.toDot(outputFile, "jpg");
		} catch (Exception e) {
			throw new RuntimeException("dotify error for " + outputFile + " (" + e.toString()+")");
		}
		return null;
	}

	public String toDot() {
		snodes0 = "";
		snodes1 = "";
		sedges0 = "";
		sedges1 = "";
		String dot = startGraph() + "\n";
		for (String nodeDef : nodesp2)
			snodes0 += nodeDef + "\n";
		for (String nodeDef : nodesp1)
			snodes1 += nodeDef + "\n";
		dot += startSubGraph(1, true);
		dot += m2Label("ecore") + ";" + "\n";
		dot += snodes0;
		dot += endSubGraph();

		dot += startSubGraph(rootPackage, true);
		dot += m2Label(rootPackage) + ";" + "\n";
		dot += snodes1;
		dot += endSubGraph();

		for (EObjectEdge eObjectEdge : edges) {
			if (eObjectEdge.geteReference() != null) {
				String edg = eObjectEdge.drawReferenceEdge_(rootPackage,
						REF_COLOR0_, REF_COLOR1);
				if (edg != null) {
					if (((EClass) eObjectEdge.getTarget()).getEPackage() == rootPackage)
						edges0.add(edg);// FP130202x
					else
						edges1.add(edg);// FP130202x
				}
			}
		}

		for (String ed0 : edges0)
			sedges0 += ed0 + "\n";
		for (String ed1 : edges1)
			sedges1 += ed1 + "\n";

		dot += sedges0;
		dot += sedges1;

		dot += endGraph();
		return dot;
	}

	@Override
	public String getNodesAsString(int i) {
		if (i == 0)
			return snodes0;
		else
			return snodes1;
	}

	@Override
	public String getEdgesAsString(int i) { // FP130202y
		if (i == 0)
			return sedges0;
		else
			return sedges1;
	}

	@Override
	public EObject getRoot() {
		return rootPackage;
	}

	@Override
	public void setVisitor(GraphVizConverter diagraphVisitor) {

	}

	@Override
	public void setRoot(EObject root) {

	}

	@Override
	public void accept(EObject model) {

	}

	@Override
	public void toLogFile() {

	}

	@Override
	public void setVisitable(DotVisitable dotGenerator) {

	}

	@Override
	public ResourceSet handleModels(ResourceSet rs, URI uriDslM2,
			String domain, String xmlns, String eNS_URI, String view,
			DotVisitable visitable, boolean diagraph) {
		return rs;
	}

	@Override
	public void handleModel(EObject model) {

	}

	@Override
	public String getStyleForEdgesToForeignGraph(EObject m0Object) {
		return null;
	}

	@Override
	public List<EObjectEdge> getEdges(int i) {
		return edges;
	}

	@Override
	public List<EObject> getNodes(int section) {
		return null;
	}

	@Override
	public String getMatchWith(EObject eObject) {
		for (EObjectEdge eObjectEdge : edges) {
			if (LOG)
			  clog(eObjectEdge.getTarget().toString());
			if (eObjectEdge.getTarget() == eObject)
				;
		}
		if (LOG)
		  clog("getMatchWith " + "in EcoreToDot");
		return "";
	}

	@Override
	public EObject find(String cn) {
		return null;
	}

	@Override
	public String asDot() {
		return asDot;
	}

	@Override
	public List<EObjectEdge> getEdges() {
		return edges;
	}

}
