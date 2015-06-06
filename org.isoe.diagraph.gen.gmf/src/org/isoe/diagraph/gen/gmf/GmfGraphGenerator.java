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
package org.isoe.diagraph.gen.gmf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.gmf.gmfgraph.Canvas;
import org.eclipse.gmf.gmfgraph.ChildAccess;
import org.eclipse.gmf.gmfgraph.ColorConstants;
import org.eclipse.gmf.gmfgraph.Compartment;
import org.eclipse.gmf.gmfgraph.Connection;
import org.eclipse.gmf.gmfgraph.DiagramElement;
import org.eclipse.gmf.gmfgraph.DiagramLabel;
import org.eclipse.gmf.gmfgraph.Direction;
import org.eclipse.gmf.gmfgraph.Figure;
import org.eclipse.gmf.gmfgraph.FigureDescriptor;
import org.eclipse.gmf.gmfgraph.FigureGallery;
import org.eclipse.gmf.gmfgraph.Node;
import org.eclipse.gmf.gmfgraph.RealFigure;
import org.eclipse.gmf.gmfgraph.Rectangle;
import org.eclipse.gmf.gmfgraph.VisualFacet;
import org.isoe.diagraph.diagraph.DAffixedEdge;
import org.isoe.diagraph.diagraph.DCompartmentEdge;
import org.isoe.diagraph.diagraph.DContainment;
import org.isoe.diagraph.diagraph.DEdge;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.diagraph.DGraphElement;
import org.isoe.diagraph.diagraph.DLabeledEdge;
import org.isoe.diagraph.diagraph.DNestedEdge;
import org.isoe.diagraph.diagraph.DNode;
import org.isoe.diagraph.diagraph.DReference;
import org.isoe.diagraph.diagraph.DSimpleEdge;
import org.isoe.diagraph.diastyle.DAlignment;
import org.isoe.diagraph.diastyle.DBaseStyle;
import org.isoe.diagraph.diastyle.DColor;
import org.isoe.diagraph.diastyle.DEdgeStyle;
import org.isoe.diagraph.diastyle.DShape;
import org.isoe.diagraph.diastyle.helpers.IErrorReporter;
import org.isoe.diagraph.gen.gmf.util.CanvasAccess;
import org.isoe.diagraph.gen.gmf.util.DGenHelpers;
import org.isoe.diagraph.gen.gmf.util.DecorationType;
import org.isoe.diagraph.gen.gmf.util.GmfConstants;
import org.isoe.diagraph.gen.gmf.util.GmfUtils;
import org.isoe.diagraph.internal.api.DPhase;
import org.isoe.diagraph.internal.api.IDiaNamedElement;
import org.isoe.diagraph.internal.api.IDiaNode;
import org.isoe.diagraph.internal.api.IDiaSyntaxElement;
import org.isoe.diagraph.internal.m2.DiaContainedElement;
import org.isoe.diagraph.internal.m2.DiaLabel;
import org.isoe.diagraph.internal.m2.DiaLink;
import org.isoe.diagraph.internal.m2.DiaNode;
import org.isoe.diagraph.internal.m2.DiaReference;
import org.isoe.diagraph.internal.m2.api.IDiaContainment;
import org.isoe.diagraph.internal.m2.parser.DAnnotationParser;
import org.isoe.diagraph.internal.m2.parser.DStatement;
import org.isoe.diagraph.internal.m2.parser.DStatement.DCommand_;
import org.isoe.diagraph.parser.api.IDiagraphParser;
import org.isoe.fwk.core.DParams;

/**
 *
 * @author fpfister
 *
 */
public class GmfGraphGenerator extends GmfBaseGenerator {

	private static final boolean LOG = DParams.GmfGraphGenerator_LOG;
	private Canvas canvas;
	protected static final String DEFAULT_GALLERY_NAME = "default";
	private static final boolean ORIENTED = true;

	protected Map<IDiaSyntaxElement, DiagramElement> diagramElements;

	int count = 0;

	public GmfGraphGenerator(IErrorReporter logger, String layer,
			String pluginId, String filePath, EPackage ePackage,
			DAnnotationParser annotatedModel,
			IDiagraphParser diagraphAnnotationUtils, boolean canvasComposite) {
		super(logger, layer, pluginId, filePath, ePackage, annotatedModel,
				diagraphAnnotationUtils, canvasComposite);
		// uniques_ = new ArrayList<String>();// FP150331a307
		// dStyle = annotationParser.getStyleSheet();
		// dgraph = annotationParser.getEcoreModel();
	}

	/***********************/

	@SuppressWarnings("restriction")
	private void addCompartment(DStatement statement,
			IDiaContainment containment) {


		if (LOG) {
			Object o = statement.getElement();
			DiaNode d = (DiaNode) o;


			// String targ = statement.getTargetElementName_();
			// if ("Baz".equals(targ))
			// tb = true;

			clog_("create compartment for " + statement.toString());
		}

		Compartment toAdd = getCompartment(statement); // FP150420


		if (toAdd == null)
			toAdd = createCompartment(statement); // FP150407
		else if (LOG && toAdd != null)
			clog_("compartment allready exists for " + statement.toString());

		if (toAdd == null) {
			if (!containment.getSourceNode().isCanvas())
				cerror("compartment not created for " + statement.toString());
			else
				clog("compartment not created on canvas for "
						+ statement.toString());
		}
	}

	@SuppressWarnings({ "restriction", "unused" })
	@Override
	public boolean processContainment(DStatement statement) {

		String id = statement.getIdent();// d.getDepth() + "." + d.getName() +
											// "." + targ;// FP150331a309

		if (// !uniques_.contains(id) &&
		statement.getCommand() != DCommand_.GRAPH_CONTAIN_TRG_ABSTRACT
				&& statement.getCommand() != DCommand_.GRAPH_CONTAIN_SRC_ABSTRACT

		) { // FP150409voir
			// uniques_.add(id);

			DiaNode d = (DiaNode) statement.getElement();

			// DiaContainment cont_ = (DiaContainment)
			// statement.getContainment();
			if (LOG) {


				clog_("\n\n--------------------------------\nGMGGPC (" + id
						+ ") " + this.getClass().getSimpleName()
						+ " processContainment - " + statement);
			}

			if (LOG) {
				logStatement(statement);
			}


			DNode nod = findDNode(d.getName());
			List<String> compartments = getCompartments(nod);

			for (String compartment : compartments) {
				if (LOG)
					clog_(compartment);
			}

			// FP140410

			List<DCompartmentEdge> edges = finDCompartmentEdges(d.getName(),
					statement.getName());// FP150410
			for (DCompartmentEdge edg : edges) {
				if (LOG)
					clog_(edg.getName() + "->" + edg.getTarget().getName());
			}

			createContainment(statement);
			if (LOG) {
				clog_(id + " yes created");
				clog_("--------------------------------\n\n");
			}
			return true;
		} else {
			if (LOG)
				System.out.println(id + " bad cmd");
			return false;// FP150331a307
		}
	}

	public void logContainment(DStatement statement) {

		DiaNode d = (DiaNode) statement.getElement();

		String id = d.getDepth() + "." + d.getName() + "."
				+ statement.getName();// FP150331a309

		clog(id);

	}

	@SuppressWarnings("restriction")
	private void createContainment(DStatement statement) { // FP140120xx
		DiaNode nod = (DiaNode) statement.getElement();
		IDiaContainment containment = nod.findContainmentbyName(statement
				.getName());
		boolean hasderiv = nod.hasDerivedSubNode();
		IDiaNode derivd = nod.getDerivedSubNode(nod.getName());
		DiaNode src = (DiaNode) containment.getSourceNode();

		boolean initial = !hasderiv && !containment.isDerived();

		boolean derived = hasderiv && containment.isDerived() && derivd == src;

		if (initial || derived) {
			if (containment == null) // check already created
				throw new RuntimeException(
						"createContainment - Containment is null");
			DiaNode srcNode = (DiaNode) containment.getSourceNode();

			if (LOG) {
				if (srcNode.isCanvas() && containment.isCompartment()) // FP150503
					clog_(srcNode.getName()
							+ "->"
							+ containment.getName()
							+ " :a canvas cannot have compartments; remains a simple containment...");

				if (srcNode.isCanvas() && containment.isPort())
					clog_(srcNode.getName()
							+ "->"
							+ containment.getName()
							+ " :a canvas cannot have ports; remains a simple containment...");

				if (containment.isPropagated()) {
					clog_(srcNode.getName() + "->" + containment.getName()
							+ " :propagated!");
				}
			}

			if (containment.isCompartment()) {// FP150503 // FP140120 //
				if (LOG)
					logContainment(statement); // FP150518voir
				// FP120703
				if (srcNode.isCanvas()) // State->children
					clog_(srcNode.getName()
							+ "->"
							+ containment.getName()
							+ " :a canvas cannot have compartments; but is created");

				addCompartment(statement, containment);
			}
		}
	}

	private void clog2(String mesg) {
		if (DParams.Parser_CONTAINMENT2_LOG)
			System.out.println(mesg);
	}

	/*
	 * private Compartment createCompartment_new_nu(DNode dnod, String
	 * compartmentName) {// FP150405 return doCreateCompartment(dnod,
	 * compartmentName); // FP150405 }
	 */
	private DContainment getDContainment(DNode dnod_, String name) {
		List<DContainment> conts = dnod_.getContainments();
		for (DContainment cont : conts)
			if (cont.getName().equals(name))
				return cont;
		return null;
	}

	private Compartment getCompartment(DStatement statement) {


		DNode dnod_ = findDNode(statement.getDiagramElementName());
		if (LOG) {
			EClass eclaz = (EClass) dnod_.getSemanticRole();
			EReference ref = (EReference) eclaz.getEStructuralFeature(statement
					.getName());
			EClass reftyp = (EClass) ref.getEType();

			clog_("getCompartment " + statement.toCsv()
					+ (reftyp.isAbstract() ? " ref abstract" : ""));
		}
		DContainment cont_nu = getDContainment(dnod_, statement.getName());
		EObject comp = ((DiaNode) statement.getElement())
				.getCompartment_(statement.getName()); // FP150421
		return (Compartment) comp;
	}

	@SuppressWarnings({ "restriction", "unused" })
	private Compartment createCompartment(DStatement statement) { // FP150405

		DNode dnod_ = findDNode(statement.getDiagramElementName());
		if (LOG) {

			EClass eclaz = (EClass) dnod_.getSemanticRole();
			EReference ref = (EReference) eclaz.getEStructuralFeature(statement
					.getName());
			EClass reftyp = (EClass) ref.getEType();
			clog_("createCompartment " + statement.toCsv()
					+ (reftyp.isAbstract() ? " ref abstract" : ""));
		}

		if (LOG) {
			EClass eclaz = (EClass) dnod_.getSemanticRole();
			EReference ref = (EReference) eclaz.getEStructuralFeature(statement
					.getName());
			EClass reftyp = (EClass) ref.getEType();
		}
		// Compartment result = doCreateCompartment_nu(dnod_,
		// statement.getName());

		Compartment result = createCompartmentContainmentFigure(statement); // FP150523b
		// Compartment result = createCompartmentOwnFigure(statement);
		// //FP150523b

		if (result != null) {
			DContainment cont = getDContainment(dnod_, statement.getName());
			EObject compart = result;
			((DiaNode) statement.getElement()).setCompartment_(
					statement.getName(), result);
		}

		return result;
	}

	private FigureDescriptor createCompartmentContainingFigureDescriptor_(
			String containingElementName, String compartmentName, boolean own) {

		if (!own)
			return DGenHelpers.findFigureDescriptorForElement(
					DEFAULT_GALLERY_NAME, canvas, containingElementName);
		else {
			RealFigure figure = DGenHelpers
					.createSimpleRectangle(compartmentName);
			figure.setLayout(DGenHelpers.createFlowLayout()); // FP2810
			FigureDescriptor containingFigureDescriptor = DGenHelpers
					.createFigureDescriptor(figure.getName());
			containingFigureDescriptor.setActualFigure(figure);
			DGenHelpers.findFigureGallery(canvas, DEFAULT_GALLERY_NAME)
					.getDescriptors().add(containingFigureDescriptor);
			return containingFigureDescriptor;
		}

	}

	private Compartment doCreateCompartment_nu(DNode parentNode,
			String compartmentName) { // FP120715c
		DBaseStyle s = styleHandler.getEdgeStyle(compartmentName); // FP121001a
		if (LOG)
			clog_("CCEF1- " + parentNode.getName() + " |k--" + compartmentName // CCEF1-
																				// Foo
																				// |k--bars-->Baz
			);// + "-->" + comp_.getTarget().getName());
		EModelElement parentael_ = parentNode.getSemanticRole();// diaNode.getEModelElement();
		String containingElementName = ((EClass) parentael_).getName();
		FigureDescriptor containingFigureDescriptor = createCompartmentContainingFigureDescriptor_(
				containingElementName, compartmentName, false);// FP150508

		if (containingFigureDescriptor == null) { // FP120621c
			if (LOG)
				clog_("no containingFigureDescriptor, compartment not created");
			return null;
		} else {

			Compartment compartment = DGenHelpers.createCompartment(
			// ael,
					compartmentName);// FP150523b
			Figure containingActualfig = containingFigureDescriptor
					.getActualFigure();
			if (!(containingActualfig instanceof RealFigure))
				throw new RuntimeException(
						"createCompartmentEmbeddedFigure - containingFigureDescriptor.getActualFigure must be a RealFigure");
			Rectangle compartmentRectangle = createCompartmentRectangleWBackground(
					parentNode.getName(), compartment.getName());

			((RealFigure) containingActualfig).getChildren().add(
					compartmentRectangle);
			ChildAccess ca = DGenHelpers.createChildAccess(
					containingFigureDescriptor, compartmentRectangle);
			clog2("_____ChildAccess:" + containingFigureDescriptor.getName()
					+ "->" + compartmentRectangle.getName());
			compartment.setFigure(containingFigureDescriptor);
			compartment.setAccessor(ca);
			canvas.getCompartments().add(compartment);
			return compartment;
		}
	}

	@SuppressWarnings({ "unused", "restriction" })
	private Compartment createCompartmentContainmentFigure(DStatement statement) {

		DiaNode parentNode = (DiaNode) statement.getElement();
		EModelElement parentael_ = parentNode.getSemanticRole();

		// EModelElement parentael_ = annotationParser.getInternalModel()
		// .findDiaNode(statement.getDiagramElementName())
		// .getEModelElement();
		String containingElementName = ((EClass) parentael_).getName();
		String compartmentName = statement.getName();
		FigureDescriptor containingFigureDescriptor = createCompartmentContainingFigureDescriptor_(
				containingElementName, compartmentName, false);// FP150508
		if (containingFigureDescriptor == null) { // FP120621c
			if (LOG)
				clog_("no containingFigureDescriptor, compartment not created");
			return null;
		} else {
			Compartment compartment = DGenHelpers.createCompartment(
			// ael,
					compartmentName);// FP150513b
			Figure containingActualfig = containingFigureDescriptor
					.getActualFigure();
			if (!(containingActualfig instanceof RealFigure))
				throw new RuntimeException(
						"createCompartmentEmbeddedFigure - containingFigureDescriptor.getActualFigure must be a RealFigure");
			Rectangle compartmentRectangle = createCompartmentRectangleWBackground(
					containingElementName, compartmentName);

			((RealFigure) containingActualfig).getChildren().add(
					compartmentRectangle);

			ChildAccess ca = DGenHelpers.createChildAccess(
					containingFigureDescriptor, compartmentRectangle);
			clog2("_____ChildAccess:" + containingFigureDescriptor.getName()
					+ "->" + compartmentRectangle.getName());
			compartment.setFigure(containingFigureDescriptor);
			compartment.setAccessor(ca);
			canvas.getCompartments().add(compartment);
			return compartment;

		}
	}

	private Compartment createCompartmentOwnFigure(DStatement statement) {
		if (LOG)
			clog_("createCompartmentOwnFigure for " + pluginId);
		DiaNode parentNode = (DiaNode) statement.getElement();
		EModelElement parentael_ = parentNode.getSemanticRole();
		String containingElementName = ((EClass) parentael_).getName();
		String compartmentName = statement.getName();
		Compartment compartment = DGenHelpers.createCompartment(
		// ael,
				compartmentName);
		FigureGallery fGallery_ = DGenHelpers.findFigureGallery(canvas,
				DEFAULT_GALLERY_NAME);
		// RealFigure figure = DGenHelpers.createSimpleRectangle(compartment
		// .getName());
		// figure.setLayout(DGenHelpers.createFlowLayout()); // FP2810
		RealFigure compartmentRectangle = createCompartmentRectangleWBackground(
				containingElementName, compartmentName);
		FigureDescriptor containingFigureDescriptor = DGenHelpers
				.createFigureDescriptor(compartmentRectangle.getName());
		containingFigureDescriptor.setActualFigure(compartmentRectangle);
		fGallery_.getDescriptors().add(containingFigureDescriptor);
		compartment.setFigure(containingFigureDescriptor);

		/*
		 * ChildAccess ca = DGenHelpers.createChildAccess(
		 * containingFigureDescriptor, compartmentRectangle);
		 * clog2("_____ChildAccess:" + containingFigureDescriptor.getName() +
		 * "->" + compartmentRectangle.getName()); compartment.setAccessor(ca);
		 */

		canvas.getCompartments().add(compartment);
		return compartment;
	}

	private Compartment doCreateCompartment_old_nu(DNode parentNode,
			String compartmentName) { // FP120715c
		DBaseStyle s = styleHandler.getEdgeStyle(compartmentName); // FP121001a
		if (LOG)
			clog_("CCEF1- " + parentNode.getName() + " |k--" + compartmentName // CCEF1-
																				// Foo
																				// |k--bars-->Baz
			);// + "-->" + comp_.getTarget().getName());
		EModelElement parentael = parentNode.getSemanticRole();// diaNode.getEModelElement();
		Compartment compartment = DGenHelpers.createCompartment(
		// ael,
				compartmentName);// FP150513b

		String containingElementName = ((EClass) parentael).getName();

		FigureDescriptor containingFigureDescriptor = createCompartmentContainingFigureDescriptor_(
				containingElementName, compartmentName, false);// FP150508

		if (containingFigureDescriptor == null) { // FP120621c
			if (LOG)
				clog_("no containingFigureDescriptor, compartment not created");
			return null;
		} else {
			Figure containingActualfig = containingFigureDescriptor
					.getActualFigure();
			if (!(containingActualfig instanceof RealFigure))
				throw new RuntimeException(
						"createCompartmentEmbeddedFigure - containingFigureDescriptor.getActualFigure must be a RealFigure");
			Rectangle compartmentRectangle = createCompartmentRectangleWBackground(
					parentNode.getName(), compartment.getName());

			((RealFigure) containingActualfig).getChildren().add(
					compartmentRectangle);
			ChildAccess ca = DGenHelpers.createChildAccess(
					containingFigureDescriptor, compartmentRectangle);
			clog2("_____ChildAccess:" + containingFigureDescriptor.getName()
					+ "->" + compartmentRectangle.getName());
			compartment.setFigure(containingFigureDescriptor);
			compartment.setAccessor(ca);
			canvas.getCompartments().add(compartment);
			return compartment;
		}
	}

	/***********************/

	private void createCanvas(DStatement statement) {
		String canvasName = statement.getDiagramElementName() + "Canvas";
		canvas = DGenHelpers.createCanvasAndFigureGallery(canvasName,
				DEFAULT_GALLERY_NAME);
		defaultFigureGallery = DGenHelpers.findFigureGallery(canvas,
				DEFAULT_GALLERY_NAME);
		gmfutils = new GmfUtils(defaultFigureGallery);// FP120929
														// GmfUtils.getInstance_(defaultFigureGallery);
														// //

	}

	@SuppressWarnings("restriction")
	public Canvas getCanvas_() {
		if (canvas == null) {
			// this.devcomposite_ = detectComposite();
			diagramElements = new HashMap<IDiaSyntaxElement, DiagramElement>();
			processDomainModel(DPhase.CANVAS);
			if (canvas == null)
				throw new RuntimeException("GraphGenerator: Canvas is null !!");
		}
		return canvas;
	}

	@Override
	public void processCanvas(DStatement statement) {
		createCanvas(statement);
	}

	// @Override
	public Object processNode_old_nu(DStatement statement, DCommand_ force) {

		if (LOG)
			clog_("GMGGPN (" + count++ + ")" + this.getClass().getSimpleName()
					+ " processNode - " + statement);
		if (canvas == null)
			throw new RuntimeException("there is no canvas");

		DNode dnod = findDNode(statement.getDiagramElementName()); // FP130318

		Node gmfnode = DGenHelpers.findNode(canvas, dnod.getName());


		Node node = createNode(statement, force);

		if (node != null) { // FP140221z
			Direction dir = node.getAffixedParentSide();
			if (dir != null && dir != Direction.NONE_LITERAL)
				clog_(node.getName() + " is affixed parent");
			canvas.getNodes().add(node); // FP120924
			diagramElements.put(
					findDiaSyntaxElement(statement.getDiagramElementName()),
					node);
		}
		return node;
	}

	@Override
	public Object processNodeTop(DStatement statement, DCommand_ force) {
		if (LOG)
			clog_("GMGGPN "+(force!=null?" FORCE ":"")+"(" + count++ + ")" + this.getClass().getSimpleName()
					+ " processNode - " + statement);
		if (canvas == null)
			throw new RuntimeException("there is no canvas");




		DNode dnod = findDNode(statement.getDiagramElementName()); // FP150526kkkk

		Node gmfnode = DGenHelpers.findNode(canvas, dnod.getName());
		if (gmfnode == null) { //FP150526kkkk
			gmfnode = createNode(statement, force);
			if (gmfnode != null) { // FP140221z
				Direction dir = gmfnode.getAffixedParentSide();
				if (dir != null && dir != Direction.NONE_LITERAL)
					if (LOG) clog_(gmfnode.getName() + " is affixed parent");
				canvas.getNodes().add(gmfnode); // FP120924
				diagramElements
						.put(findDiaSyntaxElement(statement
								.getDiagramElementName()), gmfnode);
			}else
				throw new RuntimeException("should not happen in processNode for "+statement.toString());
		} else
			if (LOG) clog_("node allready exists ");
		return gmfnode;
	}

	@Override
	public void processLabels(IDiaSyntaxElement diagramElement) {
		if (LOG)
			clog_("GMGGPB " + this.getClass().getSimpleName()
					+ " processLabels - " + diagramElement.getName());
		createLabels((DiaContainedElement) diagramElement);
	}

	@Override
	public Object processReference_(IDiaSyntaxElement reference,
			boolean oriented, boolean unique) { // FP120621b
		if (LOG)
			clog_("GMGGPR " + this.getClass().getSimpleName()
					+ " processReference - " + reference.getName());
		Object result = createReferenceConnection((DiaReference) reference,
				oriented, unique);
		if (result == null)
			throw new RuntimeException("~~~~~ " + reference.getName()
					+ " already exists in the model !!!!");
		return result;
	}

	@Override
	public Object processLink(DStatement statement, boolean oriented) { // FP120621b

		Connection result = null;
		if (LOG) {
			clog_("GMGGPL " + this.getClass().getSimpleName()
					+ " processLink -  " + statement);


		}
		// GmfGraphGenerator processLink - [2004]GRAPH_LINK - Binding -a Binding

		DLabeledEdge DLabeledEdge = findDLabeledEdge(statement
				.getDiagramElementName());
		if (!INTERNAL_METAMODEL_BASED) // FP120719a
			result = createLink(DLabeledEdge);
		else
			throw new RuntimeException("must not be INTERNAL_METAMODEL_BASED");
		return result;
	}

	@Override
	public void initTool() {
		// nothing
	}

	private DiagramLabel createUnique(DiaContainedElement diagramElement,
			DiaLabel lab) {
		String labelName = lab.getName();
		EAttribute eAttribute = lab.getEAttribute();
		if (eAttribute == null)
			throw new RuntimeException("error while create unique label !");
		Node node = DGenHelpers.findNode(canvas,
				((EClass) diagramElement.getEModelElement()).getName());
		return gmfutils.createDiagramLabelInFigure(node, eAttribute, labelName);
	}

	private void createLabels(DiaContainedElement diagramElement) {// FP140206xxxaaa+
																	// //
																	// FP120123
		for (IDiaNamedElement nmel : diagramElement.getNamedChildren()) {
			DiaLabel lab = (DiaLabel) nmel;
			DiagramLabel diagramLabel = null;
			if (!lab.isMultiple())
				diagramLabel = createUnique(diagramElement, lab);
			else
				diagramLabel = createMultiple(diagramElement, lab);
			if (diagramLabel != null)
				getCanvas_().getLabels().add(diagramLabel);
		}
	}

	private DiagramLabel createMultiple(DiaContainedElement diagramElement,
			DiaLabel lab) {
		String labelName = lab.getName();
		List<EAttribute> attributes = lab.getEAttributes_();
		if (attributes.isEmpty())
			throw new RuntimeException("error while create composite label !");
		if (LOG) {
			String log = "";
			for (EAttribute eAttribute : attributes)
				log += eAttribute.getName() + ";";
			clog_("createMultiple DiagramLabel :" + log);
		}
		Node node = DGenHelpers.findNode(canvas,
				((EClass) diagramElement.getEModelElement()).getName());
		return gmfutils.createDiagramLabelsInFigure(node, attributes,
				lab.getAttributeSeparators_(), labelName);
	}

	private boolean isAffixed(DNode dnod) {
		for (DNode dNode : getNodes(dnod.getGraph()))
			for (DEdge dEdge : getEdges(dNode))
				if (dEdge.getTarget() == dnod)
					if (dEdge instanceof DAffixedEdge)
						return true;
		return false;
	}

	private List<DNode> getNodes(DGraph graph) {
		List<DNode> nodes = new ArrayList<DNode>();
		List<DGraphElement> elements = graph.getElements();
		for (DGraphElement element : elements) {
			if (element instanceof DNode)
				nodes.add((DNode) element);
		}
		return nodes;
	}

	private List<DEdge> getEdges(DGraph graph) { // FP140518
		List<DEdge> result = new ArrayList<DEdge>();
		for (DGraphElement element : graph.getElements())
			if (element instanceof DEdge)
				result.add((DEdge) element);
		return result;
	}

	public List<DEdge> getEdges(DNode node) {
		List<DEdge> result = new ArrayList<DEdge>();
		for (DGraphElement element : node.getGraph().getElements()) {
			if (element instanceof DNestedEdge) {
				if (((DNestedEdge) element).getSource().getNode() == node)
					result.add((DEdge) element);
			} else if (element instanceof DSimpleEdge) {
				if (((DSimpleEdge) element).getSource() == node)
					result.add((DEdge) element); // FP150423b
			}
		}
		return result;
	}

	private Node createNode(DStatement statement_, DCommand_ force) { // FP121112
		FigureGallery fGallery = DGenHelpers.findFigureGallery(canvas,
				DEFAULT_GALLERY_NAME);
		DNode dnod = findDNode(statement_.getDiagramElementName()); // FP130318
		if (dnod != null) {
			if (LOG)
				clog_("CCEF2- " + dnod.getName());
			RealFigure figure = null;
			DShape shape = styleHandler.getNodeType(dnod, this);
			if (shape == DShape.CUSTOM)
				figure = createNodeScalablePolygonWBackgroundWLayout(dnod);
			else if (shape == DShape.ELLIPSE)
				figure = createNodeEllipseWBackgroundWLayout(dnod);
			else
				// if (shape == DShape.ROUNDED_RECTANGLE)
				figure = createNodeRoundedRectangleWBackgroundWLayout(dnod);
			Node node = DGenHelpers.createNode(dnod.getName());

			if (isAffixed(dnod))
				node.setAffixedParentSide(Direction.NORTH_LITERAL);

			if (!figure.getName().endsWith(GmfConstants.FIGURE_SUFFIX))
				throw new RuntimeException(
						" Figure Name must end with GmfConstants.FIGURE_SUFFIX"); // FP130319
			FigureDescriptor fd = DGenHelpers.createFigureDescriptor(figure
					.getName());
			fd.setActualFigure(figure);
			int[] size = styleHandler.getSize(dnod, this);
			VisualFacet facet = null;

			if (size != null)
				facet = DGenHelpers.createSizeFacet(dnod.getName(), size);
			else
				facet = DGenHelpers.createDefaultSizeFacet(dnod.getName());
			node.getFacets().add(facet);

			fGallery.getDescriptors().add(fd);
			node.setFigure(fd);
			if (LOG)
				clog_("node " + node.getName() + " has figure descriptor "
						+ fd.getName());
			return node;
		}
		if (LOG)
			clog_("CCEF3- not a part of the diagram:"
					+ statement_.getDiagramElementName());
		return null;

	}

	private RealFigure createNodeRoundedRectangleWBackgroundWLayout(DNode dnode) { // FP130318
		String name = dnode.getName();
		RealFigure nodeFigure = DGenHelpers
				.createNodeRoundedRectangleWLayout(name);
		String nodeBackgroundcolor = null;
		if (LOG)
			clog_("createNodeRoundedRectangleWBackgroundWLayout for " + name
					+ " for " + nodeFigure.getName());
		nodeBackgroundcolor = styleHandler.getBackgroundColor(dnode.getName(),
				this);
		if (LOG)
			clog_("nodeBackgroundcolor="
					+ ColorConstants.get(nodeBackgroundcolor).getLiteral());
		nodeFigure.setBackgroundColor(DGenHelpers
				.createConstantColor(ColorConstants.get(nodeBackgroundcolor))); // FP120924//FP120619
		return nodeFigure;
	}

	private RealFigure createNodeScalablePolygonWBackgroundWLayout(DNode dnode) {
		String name = dnode.getName();
		String polygon = styleHandler.getPolygon(dnode, this);
		RealFigure nodeFigure = DGenHelpers.createScalablePolygon_(name,
				polygon);
		String nodeBackgroundcolor = null;
		if (LOG)
			clog_("createNodeScalablePolygonWBackgroundWLayout for " + name
					+ " for " + nodeFigure.getName());
		nodeBackgroundcolor = styleHandler.getBackgroundColor(dnode.getName(),
				this);
		if (LOG)
			clog_("nodeBackgroundcolor="
					+ ColorConstants.get(nodeBackgroundcolor).getLiteral());
		nodeFigure.setBackgroundColor(DGenHelpers
				.createConstantColor(ColorConstants.get(nodeBackgroundcolor))); // FP120924//FP120619
		return nodeFigure;
	}

	private RealFigure createNodeEllipseWBackgroundWLayout(DNode dnode) {
		String name = dnode.getName();
		RealFigure nodeFigure = DGenHelpers.createEllipse(name);
		String nodeBackgroundcolor = null;
		if (LOG)
			clog_("createNodeScalablePloygonWBackgroundWLayout for " + name
					+ " for " + nodeFigure.getName());
		nodeBackgroundcolor = styleHandler.getBackgroundColor(dnode.getName(),
				this);
		if (LOG)
			clog_("nodeBackgroundcolor="
					+ ColorConstants.get(nodeBackgroundcolor).getLiteral());
		nodeFigure.setBackgroundColor(DGenHelpers
				.createConstantColor(ColorConstants.get(nodeBackgroundcolor))); // FP120924//FP120619
		return nodeFigure;
	}

	// ScalablePolygon createScalablePolygon

	private List<String> getCompartments(DNode nod) {
		List<String> compartments = new ArrayList<String>();
		List<DCompartmentEdge> allEdges = finDCompartmentEdges(nod);
		for (DCompartmentEdge edg : allEdges) {
			if (!compartments.contains(edg.getName()))
				compartments.add(edg.getName());
		}
		return compartments;
	}

	private List<DCompartmentEdge> finDCompartmentEdges(DNode nod) {
		List<DCompartmentEdge> result = new ArrayList<DCompartmentEdge>();
		List<DEdge> edgs = getEdges(nod);
		for (DEdge edg : edgs)
			if (edg instanceof DCompartmentEdge)
				result.add((DCompartmentEdge) edg);
		return result;
	}

	private List<DCompartmentEdge> finDCompartmentEdges(String nodeName,
			String compartmentName) { // FP150410
		DNode nod = findDNode(nodeName);
		if (nod == null)
			throw new RuntimeException(
					"should not happen in finDCompartmentEdges");
		return findCompartmentedEdges(nod, compartmentName);
		// FP150410
	}

	protected List<DCompartmentEdge> findCompartmentedEdges(DNode dnod,
			String name) { // FP150510
		List<DCompartmentEdge> result = new ArrayList<DCompartmentEdge>();
		List<DEdge> edgs = getEdges(dnod);
		for (DEdge dEdge : edgs)
			if (dEdge.getName().equals(name)) {
				if (dEdge instanceof DCompartmentEdge)
					result.add((DCompartmentEdge) dEdge);
			}
		return result;
	}

	private Rectangle createCompartmentRectangleWBackground(String nodeName,
			String compartmentName) {
		RealFigure compartmentFigure_ = DGenHelpers
				.createSimpleRectangle(compartmentName);

		RealFigure compartmentFigure = DGenHelpers
				.createSimpleRectangleWLayout(compartmentName); // FP130318xx

		String compartmentBackgroundcolor = null;
		if (LOG)
			clog_("createCompartmentRectangleWBackground for " + nodeName + "."
					+ compartmentName + " ok--> " + compartmentFigure.getName());

		DNode nod = findDNode(nodeName);
		if (nod == null)
			throw new RuntimeException(
					"should not happen in createCompartmentRectangleWBackground");
		// List<DCompartmentEdge> comps = findCompartmentedEdges(nod,
		// compartmentName);
		// FP150410
		// DCompartmentEdge comp_ = findFirstCompartmentedEdge_nu(nod,
		// compartmentName);

		compartmentBackgroundcolor = styleHandler.getBackgroundColor(
				compartmentName, this);
		if (LOG) {
			if (compartmentBackgroundcolor.startsWith("error"))// FP150413
				clog_("error on get compartmentBackgroundcolor");
			else
				clog_("compartmentBackgroundcolor="
						+ ColorConstants.get(compartmentBackgroundcolor)
								.getLiteral());
		}
		if (!compartmentBackgroundcolor.startsWith("error"))
			compartmentFigure.setBackgroundColor(DGenHelpers
					.createConstantColor(ColorConstants
							.get(compartmentBackgroundcolor))); // FP120621//FP120619
		return (Rectangle) compartmentFigure;
	}

	/** -------------Connections----------------------------------------- **/

	private DecorationType Shape2DecoType(DShape shap) {
		DecorationType result = DecorationType.NONE_LITERAL;
		switch (shap) {
		case RECTANGLE:
			result = DecorationType.ARROW_LITERAL;
			break;
		case ARROW:
			result = DecorationType.ARROW_LITERAL;
			break;
		case TRIANGLE:
			result = DecorationType.TRIANGLE_LITERAL;
			break;
		case DOT:
			result = DecorationType.NONE_LITERAL;
			break;
		case ELLIPSE:
			result = DecorationType.NONE_LITERAL;
			break;
		default:
			result = DecorationType.NONE_LITERAL;
			break;
		}
		return result;

	}

	private Connection createReferenceConnection(DiaReference reference,
			boolean oriented, boolean unique) { // FP120621
		if (!unique || diagramElements.get(reference) == null) {
			DReference dref = findDReference(reference.getName()); // FP120929
			if (dref == null)
				throw new RuntimeException(
						"createReferenceConnection - should not happen !!!!");
			DEdgeStyle edgeStyle = (DEdgeStyle) styleHandler.getEdgeStyle(dref
					.getName()); // FP121001
			Connection connection = null;
			if (oriented) {
				String bgcolor = styleHandler.getBackgroundColor(
						dref.getName(), this);
				String colorLitteral = edgeStyle.getColor().getLiteral();
				String orientationLitteral = edgeStyle.getArrowDirection()
						.getLiteral();
				int arrowSize = edgeStyle.getArrowSize();
				int lineWidth = edgeStyle.getLineWidth();
				String shape = edgeStyle.getShape().getLiteral();
				String direction = edgeStyle.getArrowDirection().getLiteral();
				String line = edgeStyle.getLine().getLiteral();
				String fontName = edgeStyle.getFontName().getLiteral();
				DColor fontColor = edgeStyle.getFontColor();
				DAlignment alignment = edgeStyle.getTextAlignment();
				String icon = edgeStyle.getIcon();
				String elementName = dref.getSemanticRole().getName();
				connection = gmfutils.createConnectionWithPolylineArrow(
						Shape2DecoType(edgeStyle.getShape()), elementName,
						arrowSize, lineWidth, colorLitteral); // FP130915
				// connection =
				// gmfutils.createConnectionWithPolylineArrow_(DecorationType.NONE_LITERAL,
				// //FP130616
				// reference.getName(), 2, bgcolor); // FP120928zz
			} else
				connection = gmfutils
						.createSimpleConnectionWithPolyline(reference.getName());
			canvas.getConnections().add(connection);
			diagramElements.put(reference, connection);// getTargetReference().getE
			return connection;
		}
		return null;

	}

	private Connection createLink(DLabeledEdge labeledEdge_) {// FP130616z

		DEdgeStyle edgeStyle = (DEdgeStyle) styleHandler
				.getEdgeStyle(labeledEdge_.getName()); // FP121001
		String colorLitteral = edgeStyle.getColor().getLiteral();
		String orientationLitteral = edgeStyle.getArrowDirection().getLiteral();
		int arrowSize = edgeStyle.getArrowSize();
		int lineWidth = edgeStyle.getLineWidth();
		String shape = edgeStyle.getShape().getLiteral();
		String direction = edgeStyle.getArrowDirection().getLiteral();
		String line = edgeStyle.getLine().getLiteral();
		String fontName = edgeStyle.getFontName().getLiteral();
		DColor fontColor = edgeStyle.getFontColor();
		DAlignment alignment = edgeStyle.getTextAlignment();
		String icon = edgeStyle.getIcon();
		String elementName = labeledEdge_.getSemanticRole().getName();
		int linewidth = edgeStyle.getLineWidth();
		Connection connection = gmfutils.createConnectionWithPolylineArrow(
				Shape2DecoType(edgeStyle.getShape()), elementName, arrowSize,
				linewidth, colorLitteral);
		canvas.getConnections().add(connection);
		diagramElements.put(findDiaSyntaxElement(elementName), connection);
		return connection;
	}

	/** -------------Connections end----------------------------------------- **/

	/*
	 * private void createLink_old(DStatement statement, boolean oriented) { //
	 * FP120621 Connection connection = null; if (oriented) connection =
	 * gmfutils
	 * .createConnectionWithPolylineArrow(statement.getDiagramElementName());
	 * else connection = gmfutils.createSimpleConnectionWithPolyline(statement
	 * .getDiagramElementName()); canvas.getConnections().add(connection);
	 *
	 * DiaLink diaLink = annotationParser.getInternalModel().findDiaLink(
	 * statement.getDiagramElementName());
	 *
	 * EModelElement ael = diaLink.getEModelElement(); diagramElements.put(
	 * findDiaSyntaxElement(statement.getDiagramElementName()), connection); }
	 */

	@SuppressWarnings("unused")
	private void validate() {
		CanvasAccess canvasAccess = new CanvasAccess(canvas.eResource());
		// canvas.eResource().getEObject("//@figures.0/@descriptors.5");
		canvasAccess.findFigureDescriptor("//@figures.0/@descriptors.5");
		canvasAccess
				.findPolylineConnection("//@figures.0/@descriptors.5/@actualFigure");
		canvasAccess
				.findLabel("//@figures.0/@descriptors.5/@actualFigure/@children.0");
		canvasAccess
				.findLabel("//@figures.0/@descriptors.5/@actualFigure/@children.1");
		canvasAccess
				.findChildAccess("//@figures.0/@descriptors.5/@accessors.0");
		canvasAccess
				.findChildAccess("//@figures.0/@descriptors.5/@accessors.1");
	}

	// @Override
	public EObject getRoot() {
		return getCanvas_();
	}

	@Override
	public void executeCommands(DPhase phase) {

		/*
static final boolean COMPOSITE_LOG = true;
static final boolean GmfMapGenerator_LOG = true;
static final boolean Parser_15_LOG = true;
static final boolean DiaNode_LOG = true;
static final boolean DiagramGenerator_LOG = true;
static final boolean DTokenizer_LOG = true;		 *
		 */


		if (DParams.Parser_15_LOG) {

			clog_("LOGSTA all ----------------------");
			logStatements(null);
			clog_("end LOGSTA all -------------------\n");


			//GRAPH_CREATE, GRAPH_NODE_, GRAPH_LINK, GRAPH_CONTAIN,

			clog_("LOGSTA canvas GRAPH_NODE");
			logStatements(DCommand_.GRAPH_CREATE);

			clog_("LOGSTA nodes GRAPH_NODE");
			logStatements(DCommand_.GRAPH_NODE_);

			clog_("LOGSTA links GRAPH_LINK");
			logStatements(DCommand_.GRAPH_LINK);

			clog_("LOGSTA links GRAPH_CONTAIN");
			logStatements(DCommand_.GRAPH_CONTAIN);
		}

		for (DStatement statement : annotationParser.getDStatements()) {
			clog_(statement.toCsv());
			if (statement.getCommand() == DCommand_.GRAPH_CREATE) {
				processCanvas(statement);// before z8
				// FP150514composite
				if (canvasComposite_nu) {
					Object processed = processNodeTop(statement,
							DCommand_.GRAPH_NODE_);// FP150524 //FP150531
				}
			}
		}

		for (DStatement statement : annotationParser.getDStatements()) { // FP130619yaa
			if (statement.getCommand() == DCommand_.GRAPH_NODE_) {
				Object processed = processNodeTop(statement, null); // create,+
																	// shape +
				// figure+
				// affixedside
				if (processed == null)
					if (LOG)
						clog_("not processed");
				// ...
			}
		}

		for (DStatement statement : annotationParser.getDStatements()) {
			if (statement.getCommand() == DCommand_.GRAPH_LINK) {
				Object processed = processLink(statement, ORIENTED); // FP120621a
				if (processed == null)
					if (LOG)
						clog_("not processed");
			}
		}

	}

	@Override
	public void pass2(DPhase phase) {
		if (DParams.Parser_15_LOG) {

			clog_("LOGSTA all ----------------------");
			logStatements(null);
			clog_("end LOGSTA all -------------------\n");

			clog_("LOGSTA containments");
			logStatements(DCommand_.GRAPH_CONTAIN);
			logStatements(DCommand_.GRAPH_CONTAIN_TRG_ABSTRACT); // FP150409voir
			logStatements(DCommand_.GRAPH_CONTAIN_SRC_ABSTRACT); // FP150409voir
		}
		super.pass2(phase);
		// uniques_ = new ArrayList<String>();// FP150331a307
		for (DStatement statement : annotationParser.getDStatements()) {
			if (statement.getCommand() == DCommand_.GRAPH_CONTAIN)
				processContainment(statement);// FP150331a307
		}/*
		 * //FP150412 for (DStatement statement :
		 * annotationParser.getDStatements()) { if (statement.getCommand() ==
		 * DCommand_.GRAPH_CONTAIN_TRG_ABSTRACT) //FP150409voir
		 * processContainment(statement);// FP150409 } for (DStatement statement
		 * : annotationParser.getDStatements()) { if (statement.getCommand() ==
		 * DCommand_.GRAPH_CONTAIN_SRC_ABSTRACT_) //FP150409voir
		 * processContainment(statement);// FP150409 }
		 */
	}

	public void logDiagramElements() {
		Set<IDiaSyntaxElement> keys = diagramElements.keySet();
		try {
			for (IDiaSyntaxElement key : keys) {
				DiagramElement de = diagramElements.get(key);
				String tp = "";
				tp += key instanceof DiaNode ? "DNode " : "";
				tp += key instanceof DiaLink ? "DLabeledEdge " : "";
				tp += key instanceof DiaReference ? "DReference " : "";
				tp += key.getName() + " - ";
				tp += key.getEModelElement() != null ? ((ENamedElement) key
						.getEModelElement()).getName() : "NA";
				tp += " : (gmf) " + de.getName();
				clog_(tp);
			}
		} catch (Exception e) {
			cerror("error while logging diagramElements " + e.getMessage()); // FP120621c
		}

	}

	@Override
	public void pass4(DPhase phase) {
		// nothing
	}

	/*--------------remove thid--------------------*/

	private void check2_toremove(String diagramElementName, EModelElement ael) {
		EModelElement ael_ = annotationParser.getInternalModel()
				.findDiaNode(diagramElementName).getEModelElement();
		if (ael_ != ael)
			throw new RuntimeException(
					"error 2 in createCompartmentEmbeddedFigure !!!!");
	}

	private void clog_(String mesg) {
		if (LOG || DParams.Parser_15_LOG)
			System.out.println(mesg);

	}

}
