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
package org.isoe.diagraph.gen.gmf.util;

import org.eclipse.gmf.codegen.gmfgen.*;

import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.codegen.ecore.genmodel.GenClassifier;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.gmf.codegen.gmfgen.GMFGenFactory;
import org.eclipse.gmf.gmfgraph.Alignment;
import org.eclipse.gmf.gmfgraph.BasicFont;
import org.eclipse.gmf.gmfgraph.Canvas;
import org.eclipse.gmf.gmfgraph.ChildAccess;
import org.eclipse.gmf.gmfgraph.ColorConstants;
import org.eclipse.gmf.gmfgraph.Compartment;
import org.eclipse.gmf.gmfgraph.Connection;
import org.eclipse.gmf.gmfgraph.ConstantColor;
import org.eclipse.gmf.gmfgraph.DefaultSizeFacet;
import org.eclipse.gmf.gmfgraph.DiagramLabel;
import org.eclipse.gmf.gmfgraph.Dimension;
import org.eclipse.gmf.gmfgraph.Ellipse;
import org.eclipse.gmf.gmfgraph.FigureDescriptor;
import org.eclipse.gmf.gmfgraph.FigureGallery;
import org.eclipse.gmf.gmfgraph.FlowLayout;
import org.eclipse.gmf.gmfgraph.Font;
import org.eclipse.gmf.gmfgraph.FontStyle;
import org.eclipse.gmf.gmfgraph.GMFGraphFactory;
import org.eclipse.gmf.gmfgraph.Insets;
import org.eclipse.gmf.gmfgraph.Label;
import org.eclipse.gmf.gmfgraph.LineKind;
import org.eclipse.gmf.gmfgraph.MarginBorder;
import org.eclipse.gmf.gmfgraph.Node;
import org.eclipse.gmf.gmfgraph.Point;
import org.eclipse.gmf.gmfgraph.PolygonDecoration;
import org.eclipse.gmf.gmfgraph.RGBColor;
import org.eclipse.gmf.gmfgraph.RealFigure;
import org.eclipse.gmf.gmfgraph.Rectangle;
import org.eclipse.gmf.gmfgraph.RoundedRectangle;
import org.eclipse.gmf.gmfgraph.ScalablePolygon;
import org.eclipse.gmf.gmfgraph.VisualFacet;
import org.eclipse.gmf.mappings.AuditContainer;
import org.eclipse.gmf.mappings.AuditRule;
import org.eclipse.gmf.mappings.CanvasMapping;
import org.eclipse.gmf.mappings.ChildReference;
import org.eclipse.gmf.mappings.CompartmentMapping;
import org.eclipse.gmf.mappings.Constraint;
import org.eclipse.gmf.mappings.DomainElementTarget;
import org.eclipse.gmf.mappings.ElementInitializer;
import org.eclipse.gmf.mappings.FeatureInitializer;
import org.eclipse.gmf.mappings.FeatureLabelMapping;
import org.eclipse.gmf.mappings.FeatureSeqInitializer;
import org.eclipse.gmf.mappings.FeatureValueSpec;
import org.eclipse.gmf.mappings.GMFMapFactory;
import org.eclipse.gmf.mappings.Language;
import org.eclipse.gmf.mappings.LinkMapping;
import org.eclipse.gmf.mappings.Mapping;
import org.eclipse.gmf.mappings.NodeMapping;
import org.eclipse.gmf.mappings.TopNodeReference;
import org.eclipse.gmf.mappings.ValueExpression;
import org.eclipse.gmf.tooldef.AbstractTool;
import org.eclipse.gmf.tooldef.BundleImage;
import org.eclipse.gmf.tooldef.CreationTool;
import org.eclipse.gmf.tooldef.GMFToolFactory;
import org.eclipse.gmf.tooldef.Palette;
import org.eclipse.gmf.tooldef.ToolGroup;
import org.eclipse.gmf.tooldef.ToolRegistry;
import org.isoe.fwk.core.DParams;

public class DGenHelpers implements GmfConstants {

	private static final boolean LOG = DParams.DGenHelpers_LOG; // FP130731
																// error10 case

	// private static final ColorConstants RECTANGLE_FOREGROUND_COLOR__ =
	// ColorConstants.LIGHT_GRAY_LITERAL; //FP130318
	// private static final ColorConstants RECTANGLE_FOREGROUND_COLOR =
	// ColorConstants.BLACK_LITERAL;

	public static ToolGroup createToolGroup(String toolGroupName) {
		ToolGroup group = GMFToolFactory.eINSTANCE.createToolGroup();
		group.setTitle(toolGroupName);
		if (LOG)
			clog("createToolGroup: " + toolGroupName);
		return group;
	}

	public static ToolRegistry createToolRegistryAndPalette(String paletteTitle) {
		ToolRegistry toolRegistry = GMFToolFactory.eINSTANCE
				.createToolRegistry();
		Palette palette = GMFToolFactory.eINSTANCE.createPalette();
		palette.setTitle(paletteTitle);
		toolRegistry.setPalette(palette);
		if (LOG)
			clog("createToolRegistryAndPalette: " + paletteTitle);
		return toolRegistry;
	}

	public static LinkMapping createLinkMapping(EModelElement modelElement,
			EReference containement, EReference source, EReference target,
			CreationTool creationTool, Connection connection) {
		if (LOG)
			clog("createLinkMapping with: " + ((EClass) modelElement).getName());
		LinkMapping linkmapping = GMFMapFactory.eINSTANCE.createLinkMapping();
		linkmapping.setDomainMetaElement((EClass) modelElement);
		linkmapping.setContainmentFeature(containement);
		linkmapping.setSourceMetaFeature(source);
		linkmapping.setLinkMetaFeature(target);
		linkmapping.setTool(creationTool);
		linkmapping.setDiagramLink(connection);
		return linkmapping;
	}

	public static LinkMapping createReferenceMapping(
			EReference layoutReference, CreationTool creationTool,
			Connection connection) {
		if (LOG)
			clog("&&&&&&& createReferenceMapping with: "
					+ layoutReference.getName());
		LinkMapping linkmapping = GMFMapFactory.eINSTANCE.createLinkMapping();
		linkmapping.setLinkMetaFeature(layoutReference);
		linkmapping.setTool(creationTool);
		linkmapping.setDiagramLink(connection);
		return linkmapping;
	}

	public static FeatureLabelMapping createFeatureLabelMapping_old(
			EAttribute attrib, DiagramLabel diagramLabel) {
		FeatureLabelMapping featureLabelMapping = GMFMapFactory.eINSTANCE
				.createFeatureLabelMapping();
		featureLabelMapping.getFeatures().add(attrib);
		featureLabelMapping.setDiagramLabel(diagramLabel);
		if (LOG)
			clog("createFeatureLabelMapping: " + diagramLabel.getName());
		return featureLabelMapping;
	}

	public static FeatureLabelMapping createFeatureLabelMapping(
			List<EAttribute> attribs, String[] separators,
			DiagramLabel diagramLabel) { // FP140206

		FeatureLabelMapping featureLabelMapping = GMFMapFactory.eINSTANCE
				.createFeatureLabelMapping();
		featureLabelMapping.getFeatures().addAll(attribs);
		featureLabelMapping.setDiagramLabel(diagramLabel);
		String pattern = "";
		if (attribs.size() > 1) {
			// "{0}:{1}"
			pattern = "{";
			for (int i = 0; i < attribs.size(); i++) {
				pattern += Integer.toString(i) + "}";
				if (i < attribs.size() - 1)
					pattern += (separators == null ? ":" : separators[i]) + "{";
			}
			// featureLabelMapping.setEditorPattern(pattern);
			// featureLabelMapping.setEditPattern(pattern);
			featureLabelMapping.setViewPattern(pattern);
			featureLabelMapping.setReadOnly(true);
		}

		if (LOG) {
			String atts = "";
			for (EAttribute attr : attribs) {
				atts += attr.getName() + ";";
			}
			clog("createFeatureLabelMapping: " + diagramLabel.getName() + "["
					+ atts + "] - pattern=" + pattern);
		}
		return featureLabelMapping;
	}

	/*
	 *
	 * public String getArticleAid() { return trg==null?"":trg.getAid(); }
	 */

	public static CompartmentMapping createCompartmentMapping(
			Compartment compartment) {
		;
		CompartmentMapping compartmentMapping = GMFMapFactory.eINSTANCE
				.createCompartmentMapping();
		compartmentMapping.setCompartment(compartment);
		if (LOG) {
			clog("createCompartmentMapping: " + compartment.getName());

		}
		return compartmentMapping;
	}

	public static Compartment createCompartment(// EModelElement element_,
			String name) {
		;
		if (LOG) {

			clog("createCompartment: " + name);
		}

		Compartment compartment = GMFGraphFactory.eINSTANCE.createCompartment();

		// if (name.endsWith("EClass3"))
		// name=name.substring(0,name.length()-7);
		// compartment.setCollapsible(true); //FP130318zz
		// compartment.setNeedsTitle(false);

		compartment.setName(name);

		return compartment;
	}

	public static ChildReference createChildReference_(
			NodeMapping childMapping, EReference containment) {
		boolean check = true;
		ChildReference childReference = GMFMapFactory.eINSTANCE
				.createChildReference();
		childReference.setContainmentFeature(containment);
		if (childMapping != null) {
			childReference.setOwnedChild(childMapping);
			int comps = childMapping.getCompartments().size();
			String ddd = childMapping.getDiagramNode().getName();
			EClass dme = childMapping.getDomainMetaElement();
			if (LOG) {
				// String txtcref=logChildRef(nodeMapping, containment,
				// childReference, topNode);
				// clog(txtcref);
				String t = ((EClass) containment.eContainer()).getName() + "."
						+ containment.getName() + ":"
						+ childMapping.getDomainMetaElement().getName();
				clog("createChildReference: " + t);
				if (t.equals("Composite.EReference0:EClass0"))
					check = true; // FP140415//FP150429
				if (t.equals("Composite.astates:Component"))
					check = true; // FP140415//FP150429
			}
		}

		return childReference; // FP140121
	}

	public static Constraint createConstraint(NodeMapping nodeMapping,
			String rule) { // FP130914
		Constraint c = GMFMapFactory.eINSTANCE.createConstraint();
		c.setBody(rule);
		nodeMapping.setDomainSpecialization(c);
		// c.setLanguage(Language.OCL_LITERAL);
		return c;
	}

	private static boolean isStringType(EAttribute eAttribute) {
		final Class<?> clz = eAttribute.getEAttributeType().getInstanceClass();
		if (clz != null && String.class.isAssignableFrom(clz)) {
			return true;
		}
		if (eAttribute.getEAttributeType() == XMLTypePackage.eINSTANCE
				.getString()) {
			return true;
		}
		if (eAttribute.getEAttributeType() == XMLTypePackage.eINSTANCE
				.getName_()) {
			return true;
		}
		if (eAttribute.getEAttributeType() == XMLTypePackage.eINSTANCE
				.getNCName()) {
			return true;
		}
		if (eAttribute.getEAttributeType() == XMLTypePackage.eINSTANCE
				.getToken()) {
			return true;
		}
		if (eAttribute.getEAttributeType() == XMLTypePackage.eINSTANCE
				.getQName()) {
			return true;
		}
		return false;
	}

	public static FeatureSeqInitializer createAttributeInitializer(
			NodeMapping nodeMapping, String feature, String value) {// FP
		FeatureSeqInitializer initer = GMFMapFactory.eINSTANCE
				.createFeatureSeqInitializer();
		EClass domain = nodeMapping.getDomainMetaElement();
		EStructuralFeature sf = domain.getEStructuralFeature(feature);
		if (sf instanceof EAttribute) {
			EAttribute sfa = (EAttribute) sf;
			EDataType adt = sfa.getEAttributeType();
			clog(adt.getInstanceTypeName());
			if (adt.getInstanceTypeName().contains("Int")) {
				;// initer.ge
			}
			List<FeatureInitializer> inits = initer.getInitializers();
			FeatureValueSpec valuespec_ = GMFMapFactory.eINSTANCE
					.createFeatureValueSpec();
			ValueExpression ve = GMFMapFactory.eINSTANCE
					.createValueExpression();
			ve.setBody(value);
			valuespec_.setValue(ve);
			valuespec_.setFeature(sf);
			initer.getInitializers().add(valuespec_);
		}
		return initer;
	}

	public static Object[] createNodeMappingAndChildReference_(
			// FP140121 //FP140404 //FP140417
			CreationTool creationTool, Node node, EClass eclaz,
			EReference cont) {
		Object[] result = new Object[2];
		NodeMapping nodeMapping = GMFMapFactory.eINSTANCE.createNodeMapping();
		nodeMapping.setDomainMetaElement(eclaz);
		nodeMapping.setTool(creationTool);
		nodeMapping.setDiagramNode(node);
		if (LOG) {
			clog("1_createNodeMapping: " + node.getName() + " cont: "
					+ ((EClass) (cont.eContainer())).getName() + "."
					+ cont.getName() + ":" + eclaz.getName());
		}
		ChildReference chr = createChildReference_(nodeMapping, cont);
		result[0] = nodeMapping;
		result[1] = chr;
		return result;
	}

	public static Mapping createMapping() {
		Mapping result = GMFMapFactory.eINSTANCE.createMapping();
		if (LOG)
			clog("createMapping: " + result.toString());
		return result;
	}

	public static TopNodeReference createTopNodeReference(
			NodeMapping nodeMapping) {

		TopNodeReference tref = GMFMapFactory.eINSTANCE
				.createTopNodeReference();
		tref.setContainmentFeature(((ChildReference) nodeMapping.eContainer())
				.getContainmentFeature());
		tref.setOwnedChild(nodeMapping);
		if (LOG)
			clog("DGNH createTopNodeReference: "
					+ nodeMapping.getDiagramNode().getName());

		return tref;
	}

	/*
	 * public static CreationTool createCreationTool_old(String name, String
	 * description) { CreationTool tool =
	 * GMFToolFactory.eINSTANCE.createCreationTool(); tool.setTitle(name); if
	 * (description != null) tool.setDescription(description);
	 * tool.setLargeIcon(GMFToolFactory.eINSTANCE.createDefaultImage());
	 * tool.setSmallIcon(GMFToolFactory.eINSTANCE.createDefaultImage()); if
	 * (LOG_ && LogConfig.LOG_GLOBAL) clog("createCreationTool: " + name);
	 * return tool; }
	 */

	public static CreationTool createCreationToolWithBundleImage(String name,
			String description, String imageBundle, String smallIcon,
			String largeIcon, String extension) {
		CreationTool tool = GMFToolFactory.eINSTANCE.createCreationTool();
		tool.setTitle(name);
		if (description != null)
			tool.setDescription(description);

		try {
			BundleImage small = GMFToolFactory.eINSTANCE.createBundleImage();
			small.setBundle(imageBundle); // org.isoe.sample.multv
			small.setPath("/icons/" + smallIcon + "." + extension);// icons/node1.gif
			tool.setSmallIcon(small);
			tool.setLargeIcon(GMFToolFactory.eINSTANCE.createDefaultImage());
			if (LOG)
				clog("(1) createCreationTool " + name + "- with image bundle: "
						+ smallIcon);// + " - " +
										// largeIcon);
										// //FP120930
		} catch (Exception e) {
			tool.setLargeIcon(GMFToolFactory.eINSTANCE.createDefaultImage());
			tool.setSmallIcon(GMFToolFactory.eINSTANCE.createDefaultImage());
			if (LOG)
				clog("image error while createCreationTool: " + name);
		}

		return tool;
	}

	public static CreationTool createCreationToolSimple(String name,
			String description) {
		CreationTool tool = GMFToolFactory.eINSTANCE.createCreationTool();
		tool.setTitle(name);
		if (description != null)
			tool.setDescription(description);
		tool.setLargeIcon(GMFToolFactory.eINSTANCE.createDefaultImage());
		tool.setSmallIcon(GMFToolFactory.eINSTANCE.createDefaultImage());
		if (LOG)
			clog("(2) createCreationTool " + name + "- with default image: "
					+ tool.getSmallIcon());
		return tool;
	}

	public static CanvasMapping createCanvasMapping(EPackage domainModel,
			Palette palette, Canvas canvas, EModelElement element) {
		CanvasMapping canvasMapping = GMFMapFactory.eINSTANCE
				.createCanvasMapping();
		canvasMapping.setDomainModel(domainModel);
		canvasMapping.setDomainMetaElement((EClass) element);
		canvasMapping.setPalette(palette);
		canvasMapping.setDiagramCanvas(canvas);
		if (canvas.getName() == null || canvas.getName().isEmpty()) {
			// canvas.setName(((ENamedElement) element).getName());
		}
		if (LOG)
			clog("createCanvasMapping: ");
		return canvasMapping;
	}

	public static ChildAccess createChildAccess(FigureDescriptor fd,
			RealFigure fig) {
		ChildAccess ca = GMFGraphFactory.eINSTANCE.createChildAccess();
		ca.setFigure(fig);
		fd.getAccessors().add(ca);
		if (LOG)
			clog("createChildAccess for figure descriptor: " + fd.getName());
		return ca;
	}

	public static Node createNode(String name) {


		Node node = GMFGraphFactory.eINSTANCE.createNode();
		node.setName(name);
		if (LOG)
			clog("createNode: " + node.getName());
		return node;
	}

	public static VisualFacet createDefaultSizeFacet(String name) {
		Dimension dimension = GMFGraphFactory.eINSTANCE.createDimension();
		dimension.setDx(DParams.DEFAULT_NODE_SIZE_WIDTH);
		dimension.setDy(DParams.DEFAULT_NODE_SIZE_HEIGHT);
		DefaultSizeFacet facet = GMFGraphFactory.eINSTANCE
				.createDefaultSizeFacet();
		facet.setDefaultSize(EcoreUtil.copy(dimension));
		if (LOG)
			clog("createDefaultSizeFacet for: " + name);
		return facet;
	}

	public static VisualFacet createSizeFacet(String name, int[] size) {
		Dimension dimension = GMFGraphFactory.eINSTANCE.createDimension();
		dimension.setDx(size[0]);
		dimension.setDy(size[1]);
		DefaultSizeFacet facet = GMFGraphFactory.eINSTANCE
				.createDefaultSizeFacet();
		facet.setDefaultSize(EcoreUtil.copy(dimension));
		if (LOG)
			clog("createDefaultSizeFacet for: " + name);
		return facet;
	}

	public static PolygonDecoration createPolygonDecoration() {// FP121111
		PolygonDecoration df = GMFGraphFactory.eINSTANCE
				.createPolygonDecoration();
		df.setName("createPolygonDecoration");
		df.getTemplate().add(DGenHelpers.createPoint(-1, 1));
		df.getTemplate().add(DGenHelpers.createPoint(0, 0));
		df.getTemplate().add(DGenHelpers.createPoint(-1, -1));
		df.getTemplate().add(DGenHelpers.createPoint(-2, 0));
		df.setBackgroundColor(DGenHelpers
				.createConstantColor(ColorConstants.BLUE_LITERAL));
		df.setForegroundColor(DGenHelpers.createRGBColor(0, 0, 255));
		return df;
	}

	public static FigureDescriptor createFigureDescriptor(String name) {
		if (LOG)
			clog("createFigureDescriptor[");
		if (name == null)
			throw new RuntimeException("createFigureDescriptor: name is null");
		FigureDescriptor fd = GMFGraphFactory.eINSTANCE
				.createFigureDescriptor();
		fd.setName(name);


		if (LOG)
			clog("FigureDescriptor name: " + fd.getName());
		if (LOG)
			clog("]createFigureDescriptor");
		return fd;
	}

	public static RealFigure createSimpleRectangle(String name) {
		;
		if (LOG)
			clog("createSimpleRectangle[");
		Rectangle figure = GMFGraphFactory.eINSTANCE.createRectangle();
		figure.setName(name + FIGURE_SUFFIX);

		if (LOG) {

			clog("]createSimpleRectangle: " + figure.getName());
		}
		return figure;
	}

	public static RealFigure createSimpleRectangleWLayout(String name) { // FP130318
		if (LOG)
			clog("createSimpleRectangleWLayout[");

		Rectangle rect = GMFGraphFactory.eINSTANCE.createRectangle();
		rect.setName(name + FIGURE_SUFFIX);
		rect.setFill(true);
		rect.setLineKind(LineKind.LINE_SOLID_LITERAL);
		rect.setLineWidth(DParams.COMPARTMENT_BORDER_WIDTH); // FP130318x
		// rect.setLineWidth(5);
		rect.setOutline(true);
		rect.setXorFill(false);
		rect.setXorOutline(false);
		rect.setLayout(createFlowLayout());
		rect.setBackgroundColor(createConstantColor(ColorConstants.WHITE_LITERAL));
		// rect.setForegroundColor(createConstantColor(RECTANGLE_FOREGROUND_COLOR__));
		rect.setForegroundColor(createConstantColor(ColorConstants
				.get(DParams.RECTANGLE_FOREGROUND)));// black

		MarginBorder mb = GMFGraphFactory.eINSTANCE.createMarginBorder(); // FP130318
		Insets ins = GMFGraphFactory.eINSTANCE.createInsets();
		ins.setLeft(DParams.COMPARTMENT_INSETS);
		ins.setRight(DParams.COMPARTMENT_INSETS);
		ins.setTop(DParams.COMPARTMENT_INSETS);
		ins.setBottom(DParams.COMPARTMENT_INSETS);
		mb.setInsets(ins);
		rect.setBorder(mb);

		if (LOG)
			clog("]createSimpleRectangleWLayout");
		return rect;
	}

	public static Font createBasicFont(String name, int height, FontStyle style) {
		BasicFont result = GMFGraphFactory.eINSTANCE.createBasicFont();
		result.setFaceName(name);
		result.setHeight(height);
		result.setStyle(style);
		if (LOG)
			clog("createBasicFont: " + name);
		return result;
	}

	public static Dimension createDimension(int dx, int dy) {
		Dimension result = GMFGraphFactory.eINSTANCE.createDimension();
		result.setDx(dx);
		result.setDy(dy);
		if (LOG)
			clog("createDimension: " + result.toString());
		return result;
	}

	public static ConstantColor createConstantColor(ColorConstants constant) {
		ConstantColor result = GMFGraphFactory.eINSTANCE.createConstantColor();
		result.setValue(constant);
		if (LOG)
			clog("createConstantColor: " + result.toString());
		return result;
	}

	public static RGBColor createRGBColor(int red, int green, int blue) {
		RGBColor result = GMFGraphFactory.eINSTANCE.createRGBColor();
		result.setRed(red);
		result.setGreen(green);
		result.setBlue(blue);
		if (LOG)
			clog("createRGBColor: " + result.toString());
		return result;
	}

	public static FlowLayout createFlowLayout() { // FP130318
		FlowLayout flowLayout = GMFGraphFactory.eINSTANCE.createFlowLayout();
		flowLayout.setForceSingleLine(true);
		flowLayout.setMajorAlignment(Alignment.CENTER_LITERAL);
		flowLayout.setMajorSpacing(0);
		flowLayout.setMatchMinorSize(true);
		flowLayout.setMinorAlignment(Alignment.CENTER_LITERAL);
		flowLayout.setMinorSpacing(0);
		flowLayout.setVertical(true);
		return flowLayout;
	}

	public static RealFigure createNodeRoundedRectangleWLayout(String name) { // FP120924
		;
		if (LOG)
			clog("createNodeRoundedRectangleWLayout[");

		RoundedRectangle rect = GMFGraphFactory.eINSTANCE
				.createRoundedRectangle();


		rect.setName(name + FIGURE_SUFFIX);
		rect.setFill(true);
		rect.setLineKind(LineKind.LINE_SOLID_LITERAL);
		rect.setLineWidth(DParams.RECTANGLE_BORDER_WIDTH); // FP130318x
		// rect.setLineWidth(5);
		rect.setOutline(true);
		rect.setXorFill(false);
		rect.setXorOutline(false);
		rect.setLayout(createFlowLayout());
		rect.setBackgroundColor(createConstantColor(ColorConstants.WHITE_LITERAL));
		// rect.setForegroundColor(createConstantColor(RECTANGLE_FOREGROUND_COLOR));
		// rect.setForegroundColor(createConstantColor(ColorConstants.get("lightGray")));//black
		rect.setForegroundColor(createConstantColor(ColorConstants
				.get(DParams.RECTANGLE_FOREGROUND)));// black

		// rect.setForegroundColor(createConstantColor(ColorConstants.BLACK_LITERAL));

		Insets insets = GMFGraphFactory.eINSTANCE.createInsets(); // FP130318xy
		insets.setBottom(DParams.NODE_INSETS);
		insets.setLeft(DParams.NODE_INSETS);// FP130319x
		insets.setRight(DParams.NODE_INSETS);
		// insets.setTop(20);
		rect.setInsets(insets);

		if (LOG)
			clog("]createNodeRoundedRectangleWLayout");
		return rect;
	}

	public static RealFigure createEllipse(String name) { // FP121111
		if (LOG)
			clog("createEllipse[");
		Ellipse el = GMFGraphFactory.eINSTANCE.createEllipse();
		el.setName(name + FIGURE_SUFFIX);
		el.setFill(true);
		el.setLineKind(LineKind.LINE_SOLID_LITERAL);
		el.setLineWidth(1);
		el.setOutline(true);
		el.setXorFill(false);
		el.setXorOutline(false);
		el.setLayout(createFlowLayout());
		el.setBackgroundColor(createConstantColor(ColorConstants.WHITE_LITERAL));
		el.setForegroundColor(createConstantColor(ColorConstants.BLACK_LITERAL));
		if (LOG)
			clog("]createEllipse");
		return el;
	}

	public static ScalablePolygon createScalablePolygon_(String name,
			String polygon) {// FP121111
		String sample = "-1,1;0,0;-1,-1;-2,0";
		if (LOG)
			clog("createScalablePolygon[");
		ScalablePolygon df = GMFGraphFactory.eINSTANCE.createScalablePolygon();
		df.setName(name + FIGURE_SUFFIX); // FP130319
		try {
			String[] couples = polygon.split(";");
			for (String couple : couples) {
				String[] xy = couple.split(",");
				df.getTemplate().add(
						DGenHelpers.createPoint(Integer.parseInt(xy[0]),
								Integer.parseInt(xy[1])));
			}
		} catch (Exception e) {
			throw new RuntimeException("error while parsing polygon !!!");
		}
		df.setFill(true);
		df.setLineKind(LineKind.LINE_SOLID_LITERAL);
		df.setLineWidth(1);
		df.setOutline(true);
		df.setXorFill(false);
		df.setXorOutline(false);
		df.setLayout(createFlowLayout());
		df.setBackgroundColor(createConstantColor(ColorConstants.WHITE_LITERAL));
		df.setForegroundColor(createConstantColor(ColorConstants.BLACK_LITERAL));
		if (LOG)
			clog("]createScalablePolygon");
		return df;
	}

	public static Canvas createCanvasAndFigureGallery(String canvasName,
			String figureGalleryName) {
		if (LOG)
			clog("createCanvasAndFigureGallery[");
		Canvas canvas = GMFGraphFactory.eINSTANCE.createCanvas();
		canvas.setName(canvasName);
		if (LOG)
			clog("createCanvas: " + canvas.getName());
		FigureGallery fg = GMFGraphFactory.eINSTANCE.createFigureGallery();
		fg.setName(figureGalleryName);
		if (LOG)
			clog("createFigureGallery: " + fg.getName());
		canvas.getFigures().add(fg);
		if (LOG)
			clog("]createCanvasAndFigureGallery");
		return canvas;
	}

	public static Point createPoint(int x, int y) {
		Point point = GMFGraphFactory.eINSTANCE.createPoint();
		point.setX(x);
		point.setY(y);
		return point;
	}

	// TODO unify with megamodeldeployer
	public static GenModel createGenModel(final EPackage aModel,
			final EPackage additionnalModel, String pluginid) {
		if (pluginid == null)
			throw new RuntimeException("pluginid should no be null !!!!");
		GenModel genModel = new org.isoe.fwk.megamodel.deploy.GenModelAccess(
				aModel, additionnalModel).myCreateDummy(pluginid);
		genModel.setModelPluginID(pluginid);
		genModel.setModelDirectory("/" + pluginid + "/src/");
		genModel.setEditDirectory("/" + pluginid + ".edit" + "/src/");
		genModel.setComplianceLevel(DParams.GEN_JDK_LEVEL); // FP120518
		genModel.setRuntimeVersion(DParams.EMF_LEVEL);// FP120521
		if (LOG)
			clog("dummy GenModel created for " + pluginid);
		return genModel;
	}

	public static DiagramLabel findLabel(Canvas canvas, String name) {
		for (DiagramLabel label : canvas.getLabels())
			if (name.equals(label.getName()))
				return label;
		return null;
	}

	public static FigureGallery findFigureGallery(Canvas canvas, String name) {
		EList<FigureGallery> figures = canvas.getFigures();
		for (FigureGallery figuregal : figures)
			if (figuregal.getName().equals(name))
				return figuregal;
		return null;
	}

	public static FigureDescriptor findFigureDescriptorForElement(
			String galleryName, Canvas canvas, String elementName) {
		FigureDescriptor result = null;
		FigureGallery fGallery = findFigureGallery(canvas, galleryName);
		EList<FigureDescriptor> descrs = fGallery.getDescriptors();
		for (FigureDescriptor figureDescriptor : descrs)
			if (figureDescriptor.getName().equals(elementName + FIGURE_SUFFIX))
				result = figureDescriptor;
		/*
		 *  = false; if (result.getName().equals("BilleFigure")) tb =
		 * true; //FP150405 if (result.getName().equals("ZigFigure")) tb = true;
		 * if (result.getName().equals("boumsFigure")) tb = true;
		 */

		return result;
	}

	public static ToolGroup findToolGroup(ToolRegistry registry, String title) {
		for (AbstractTool tool : registry.getPalette().getTools())
			if (tool instanceof ToolGroup && tool.getTitle().equals(title))
				return (ToolGroup) tool;
		return null;
	}

	public static Node findNode(Canvas canvas, String name) {
		for (Node node : canvas.getNodes())
			if (name.equals(node.getName()))
				return node;
		return null;
	}

	public static Compartment findCompartment(Canvas canvas, String name) {
		for (Compartment compartment : canvas.getCompartments())
			if (name.equals(compartment.getName()))
				return compartment;
		return null;
	}

	public static LinkMapping findLinkMapping_obsolete(Mapping mapping,
			String name) {
		EList<LinkMapping> lnks = mapping.getLinks();
		for (LinkMapping linkMapping : lnks)
			if (linkMapping.getLinkMetaFeature().getName().equals(name))
				return linkMapping;
		return null;
	}

	public static NodeMapping findNodeMapping(List<NodeMapping> nodes,
			String name) {
		for (NodeMapping nodeMapping : nodes)
			if (nodeMapping.getDomainMetaElement().getName().equals(name))
				return nodeMapping;
		return null;
	}

	public static LinkMapping findLinkMapping(List<LinkMapping> lnks,
			String name) {
		// EList<LinkMapping> lnks = mapping.getLinks();
		for (LinkMapping linkMapping : lnks)
			if (linkMapping.getLinkMetaFeature().getName().equals(name))
				return linkMapping;
		return null;
	}

	public static CreationTool findCreationToolByTitle(ToolRegistry treg,
			String name) {
		for (Iterator<EObject> it = treg.eAllContents(); it.hasNext();) {
			EObject next = it.next();
			if (next instanceof CreationTool) {
				CreationTool tool = (CreationTool) next;
				if (name.equals(tool.getTitle()))
					return tool;
			}
		}
		return null;
	}

	public static Connection findConnection(Canvas canvas, String name) {
		for (Connection connection : canvas.getConnections()) {
			if (name.equals(connection.getName()))
				return connection;
		}
		return null;
	}

	/*** --------------remove------------------------------ ***/

	private static FlowLayout createFlowLayout_old() {
		FlowLayout fl = GMFGraphFactory.eINSTANCE.createFlowLayout();
		fl.setVertical(false);
		return fl;
	}

	private static Node createNodeWithRectangleFigure_old(
			FigureGallery fGallery, String name, boolean withSizeFacet) {
		if (LOG)
			clog("createNodeWithRectangleFigure[");

		RoundedRectangle rect = GMFGraphFactory.eINSTANCE
				.createRoundedRectangle();
		rect.setName(name + FIGURE_SUFFIX);
		rect.setFill(true);
		rect.setLineKind(LineKind.LINE_SOLID_LITERAL);
		rect.setLineWidth(1);
		rect.setOutline(true);
		rect.setXorFill(false);
		rect.setXorOutline(false);
		rect.setLayout(createFlowLayout());
		rect.setBackgroundColor(createConstantColor(ColorConstants.WHITE_LITERAL));
		rect.setForegroundColor(createConstantColor(ColorConstants.BLACK_LITERAL));
		// rect.setBorder(createBorder());
		FigureDescriptor fd = createFigureDescriptor(rect.getName());
		fd.setActualFigure(rect);
		Node node = createNode(name);
		VisualFacet facet = createDefaultSizeFacet(node.getName());
		node.getFacets().add(facet);
		fGallery.getDescriptors().add(fd);
		node.setFigure(fd);
		if (LOG)
			clog("]createNodeWithRectangleFigure");
		return node;
	}

	/** -----------------private---------------------------- **/

	protected static Connection createConnection(String name) {
		;
		if (LOG)
			clog("createConnection[");
		Connection connection = GMFGraphFactory.eINSTANCE.createConnection();
		connection.setName(name);


		if (LOG)
			clog("Connection name: " + connection.getName());
		if (LOG)
			clog("]createConnection");
		return connection;
	}

	public GenAuditRule createAudit(String id, String ruleBody,
			GenClass target, GenSeverity severity, boolean isLiveMode) {
		GenAuditRule audit = GMFGenFactory.eINSTANCE.createGenAuditRule();
		audit.setId(id);
		audit.setName("Name of" + id); //$NON-NLS-1$
		org.eclipse.gmf.codegen.gmfgen.GenDomainElementTarget ruleTarget = GMFGenFactory.eINSTANCE
				.createGenDomainElementTarget();
		ruleTarget.setElement(target);
		audit.setTarget(ruleTarget);
		GenConstraint rule = GMFGenFactory.eINSTANCE.createGenConstraint();
		rule.setBody(ruleBody);
		audit.setRule(rule);
		audit.setSeverity(severity);
		audit.setUseInLiveMode(isLiveMode);
		return audit;
	}

	public static AuditRule createAuditRule_(Mapping mapping, EClass eclaz,
			String containerid, String ruleName, String ruleDescription,
			String oclRule) { // FP130914
		AuditRule auditRule = GMFMapFactory.eINSTANCE.createAuditRule();
		AuditContainer auditContainer = mapping.getAudits();
		if (auditContainer == null) {
			auditContainer = GMFMapFactory.eINSTANCE.createAuditContainer();
			mapping.setAudits(auditContainer);
			auditContainer.setId(containerid + ".audits");
			auditContainer.setName(containerid + " audits");
			auditContainer.setDescription("Set of audits");
		}

		mapping.getAudits().getAudits().add(auditRule);
		Constraint c = GMFMapFactory.eINSTANCE.createConstraint();
		auditRule.setRule(c);
		c.setBody(oclRule);
		c.setLanguage(Language.OCL_LITERAL);

		auditRule.setUseInLiveMode(true);
		auditRule.setName(ruleName);
		auditRule.setDescription(ruleDescription);
		auditRule.setMessage(ruleDescription);
		auditRule.setId(ruleName + "_check");
		DomainElementTarget domainElementTarget = GMFMapFactory.eINSTANCE
				.createDomainElementTarget();
		domainElementTarget.setElement(eclaz);
		auditRule.setTarget(domainElementTarget);

		return auditRule;
	}

	protected static DiagramLabel createDiagramLabel(EModelElement element,
			String name) {
		DiagramLabel label = GMFGraphFactory.eINSTANCE.createDiagramLabel();
		label.setName(name);
		if (LOG)
			clog("createDiagramLabel: " + label.getName());
		return label;
	}

	protected static Label createLabel(DiagramLabel label) {
		Label lab = GMFGraphFactory.eINSTANCE.createLabel();
		lab.setName(label.getName() + FIGURE_SUFFIX);
		if (LOG)
			clog("createLabel: " + lab.getName());
		return lab;
	}

	private static void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	private static String logChildRef(NodeMapping nodeMapping,
			EReference containment, ChildReference childReference, boolean top) {

		String result = top ? "TOP " : "CHILD ";

		result += "createChildReference: containment=" + containment.getName()
				+ "    ownedChild=" + nodeMapping.getDiagramNode().getName()
				+ "\n";

		if (nodeMapping.getCompartments() != null) {
			for (CompartmentMapping compartmentMapping : nodeMapping
					.getCompartments())
				result += "compartment: "
						+ compartmentMapping.getCompartment().getName() + "\n";
		}
		result += "childReference:  ";
		result += childReference.getOwnedChild() != null ? " ownedChild="
				+ childReference.getOwnedChild().getDiagramNode().getName()
				: " "; // org.eclipse.gmf.mappings.impl.NodeMappingImpl
		// result+=childReference.getChild();
		result += childReference.getChildrenFeature() != null ? " childrenFeature (reference)="
				+ childReference.getChildrenFeature().getName()
				: " ";
		result += childReference.getCompartment() != null ? " compartment (compartment mapping)="
				+ childReference.getCompartment().getCompartment().getName()
				: " ";
		result += childReference.getContainmentFeature() != null ? " containmentFeature (reference)="
				+ childReference.getContainmentFeature().getName()
				: " ";
		result += childReference.getParentNode() != null ? " parentNode (node mapping)="
				+ childReference.getParentNode().getDiagramNode().getName()
				: " ";
		result += childReference.getReferencedChild() != null ? " referencedChild (node mapping)="
				+ childReference.getReferencedChild().getDiagramNode()
						.getName()
				: " ";
		result += " class="
				+ childReference.getOwnedChild().getDomainMetaElement()
						.getName();

		result = (top ? "TOP " : "CHILD ")
				+ ((EClass) containment.eContainer()).getName()
				+ "."
				+ containment.getName()
				+ ":"
				+ childReference.getOwnedChild().getDomainMetaElement()
						.getName();

		return result;
	}

}
