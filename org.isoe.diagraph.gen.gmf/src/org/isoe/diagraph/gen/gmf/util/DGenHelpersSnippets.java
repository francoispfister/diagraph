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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.gmf.gmfgraph.Canvas;
import org.eclipse.gmf.gmfgraph.ColorConstants;
import org.eclipse.gmf.gmfgraph.CompoundBorder;
import org.eclipse.gmf.gmfgraph.Connection;
import org.eclipse.gmf.gmfgraph.CustomAttribute;
import org.eclipse.gmf.gmfgraph.CustomLayout;
import org.eclipse.gmf.gmfgraph.DiagramElement;
import org.eclipse.gmf.gmfgraph.DiagramLabel;
import org.eclipse.gmf.gmfgraph.FigureDescriptor;
import org.eclipse.gmf.gmfgraph.FigureGallery;
import org.eclipse.gmf.gmfgraph.FlowLayout;
import org.eclipse.gmf.gmfgraph.FontStyle;
import org.eclipse.gmf.gmfgraph.GMFGraphFactory;
import org.eclipse.gmf.gmfgraph.Label;
import org.eclipse.gmf.gmfgraph.LineBorder;
import org.eclipse.gmf.gmfgraph.LineKind;
import org.eclipse.gmf.gmfgraph.MarginBorder;
import org.eclipse.gmf.gmfgraph.Node;
import org.eclipse.gmf.gmfgraph.Point;
import org.eclipse.gmf.gmfgraph.PolygonDecoration;
import org.eclipse.gmf.gmfgraph.PolylineConnection;
import org.eclipse.gmf.gmfgraph.RealFigure;
import org.eclipse.gmf.gmfgraph.RoundedRectangle;
import org.eclipse.gmf.gmfgraph.VisualFacet;
import org.eclipse.gmf.mappings.ChildReference;
import org.eclipse.gmf.mappings.CompartmentMapping;
import org.eclipse.gmf.mappings.Mapping;
import org.eclipse.gmf.mappings.NodeMapping;
import org.eclipse.gmf.mappings.TopNodeReference;
import org.eclipse.gmf.internal.bridge.genmodel.BasicGenModelAccess;
//import org.isoe.fwk.log.LogConfig;

public class DGenHelpersSnippets extends DGenHelpers {

/*** -----------------unused-------------------------------------  ***/

	private static final boolean LOG = false;

	/**
	 * if label element is contained within element mapped to node or connection
	 * then label figure should be added to container figure
	 *
	 * @param diagElement
	 * @param fGallery
	 * @param element
	 * @param name
	 * @return
	 */
	private static DiagramLabel createLabelAndAddToFigureDescriptor(
			DiagramElement diagElement, FigureGallery gallery,
			EModelElement element, String name) {
		if (LOG)
			System.out.println("createLabelAndAddToFigureDescriptor[");
		DiagramLabel label = createDiagramLabel(element, name);
		RealFigure labelFigure = createLabel(label);
		if (diagElement != null)
			addLabelFigureToContainerFigureDescriptor(diagElement, labelFigure,
					label);
		else
			addLabelFigureToNewFigureDescriptor(gallery, labelFigure, label);
		if (LOG)
			System.out.println("]createLabelAndAddToFigureDescriptor");
		return label;
	}

	private static void addLabelFigureToContainerFigureDescriptor(
			DiagramElement diagramElement, RealFigure labelFigure,
			DiagramLabel label) {
		if (LOG)
			System.out.println("addLabelFigureToContainerFigureDescriptor[");
		FigureDescriptor diagElementFigureDescr = diagramElement.getFigure();
		RealFigure diagElementActualFigure = (RealFigure) diagElementFigureDescr
				.getActualFigure();
		diagElementActualFigure.getChildren().add(labelFigure);
		label.setFigure(diagElementFigureDescr);
		label.setAccessor(createChildAccess(diagElementFigureDescr, labelFigure));
		if (LOG)
			System.out.println("]addLabelFigureToContainerFigureDescriptor");
	}

	private static void addLabelFigureToNewFigureDescriptor(
			FigureGallery gallery, RealFigure labelFigure, DiagramLabel label) {
		FigureDescriptor fd = createFigureDescriptor(labelFigure.getName());
		fd.setActualFigure(labelFigure);
		gallery.getDescriptors().add(fd);
		label.setFigure(fd);
	}

	private static DiagramLabel createDiagramLabel_(String figureGalleryName,
			Canvas canvas, EClass domainClass, EAttribute nameA,
			String labelName) {
		if (LOG)
			System.out.println("createDiagramLabel[");
		FigureGallery fGallery = findFigureGallery(canvas, figureGalleryName);
		Node node = findNode(canvas, domainClass.getName());
		if (node == null)
			System.out.println("createDiagramNodeLabel : node is null");
		DiagramLabel diagramLabel = createLabelAndAddToFigureDescriptor(node,
				fGallery, nameA, labelName);
		if (LOG)
			System.out.println("]createDiagramLabel");
		return diagramLabel;
	}

	private static Label createFooLabel(String text, String name) {
		Label label = GMFGraphFactory.eINSTANCE.createLabel();
		label.setText(text);
		label.setName(name + "Foo" + "Label");
		label.setFont(createBasicFont("Arial", 9, FontStyle.ITALIC_LITERAL));
		label.setForegroundColor(createConstantColor(ColorConstants.CYAN_LITERAL));
		if (LOG)
			System.out.println("createFooLabel: " + label.getName());
		return label;
	}

	private static Label createBarLabel(String text, String name) {
		Label label = GMFGraphFactory.eINSTANCE.createLabel();
		label.setText(text);
		label.setName(name + "Bar" + "Label");
		label.setFont(createBasicFont("Helvetica", 12, FontStyle.BOLD_LITERAL));
		if (LOG)
			System.out.println("createBarLabel: " + label.getName());
		return label;
	}




	private static CompoundBorder createBorder() {

		LineBorder outerOuter = GMFGraphFactory.eINSTANCE.createLineBorder();
		outerOuter.setColor(createConstantColor(ColorConstants.BLUE_LITERAL));
		outerOuter.setWidth(22);

		MarginBorder outerInner = GMFGraphFactory.eINSTANCE.createMarginBorder();
		outerInner.setInsets(GMFGraphFactory.eINSTANCE.createInsets());
		outerInner.getInsets().setBottom(23);
		outerInner.getInsets().setTop(34);
		//sic! outerInner.getInsets().setRight(45);
		//sic! outerInner.getInsets().setLeft(56);

		CompoundBorder outer = GMFGraphFactory.eINSTANCE.createCompoundBorder();
		outer.setOuter(outerOuter);
		outer.setInner(outerInner);

		CompoundBorder innerEmpty = GMFGraphFactory.eINSTANCE.createCompoundBorder();
		//sic!
		innerEmpty.setInner(null);
		innerEmpty.setOuter(null);

		CompoundBorder result = GMFGraphFactory.eINSTANCE.createCompoundBorder();
		result.setOuter(outer);
		result.setInner(innerEmpty);

		//rect.setBorder(result);

		return result;
}



	private static Connection createConnectionWithPolyline(
			FigureGallery fGallery, String nam) {
		if (LOG)
			System.out.println("createConnectionWithPolyline[");
		Connection connection = createConnection(nam);
		RealFigure figure = createPolylineConnection(connection.getName());
		FigureDescriptor fd = createFigureDescriptor(figure.getName());
		fd.setActualFigure(figure);
		fGallery.getDescriptors().add(fd);
		connection.setFigure(fd);
		if (LOG)
			System.out.println("]createConnectionWithPolyline");
		return connection;
	}



	private static List<Point> getArrowPoint() {
		List<Point> points = new ArrayList<Point>();
		points.add(createPoint(-2, -2));
		points.add(createPoint(0, 0));
		points.add(createPoint(-2, 2));
		return points;
	}



	/**************************---------------------------**********************************/

	private static PolylineConnection createContainmentConnection(String name) {
		PolylineConnection conectn = GMFGraphFactory.eINSTANCE
				.createPolylineConnection();
		conectn.setName(name);
		PolygonDecoration df = createBlueRhomb();
		df.setFill(true);
		conectn.setSourceDecoration(df);
		return conectn;
	}



	private static String getName(EModelElement element, String name) {
		if (name != null)
			return name;
		if (!(element instanceof ENamedElement))
			System.out.println("a name is missing for " + element);
		return ((ENamedElement) element).getName();
	}

	private static CustomLayout createItemisLayout() {
		CustomLayout ld = GMFGraphFactory.eINSTANCE.createCustomLayout();
		ld.setQualifiedClassName("de.itemis.gmf.runtime.layout.ScaleInnerFigureLayout");
		CustomAttribute normalAttr = GMFGraphFactory.eINSTANCE
				.createCustomAttribute();
		normalAttr.setDirectAccess(false);
		normalAttr.setMultiStatementValue(false);
		normalAttr.setName("scaleFactor");
		normalAttr.setValue("1.414213562373095");
		ld.getAttributes().add(normalAttr);
		return ld;
	}


	private static PolygonDecoration createBlueRhomb() {
		PolygonDecoration df = GMFGraphFactory.eINSTANCE
				.createPolygonDecoration();
		df.setName("BlueRhombDecoration");
		df.getTemplate().add(DGenHelpers.createPoint(-1, 1));
		df.getTemplate().add(DGenHelpers.createPoint(0, 0));
		df.getTemplate().add(DGenHelpers.createPoint(-1, -1));
		df.getTemplate().add(DGenHelpers.createPoint(-2, 0));
		df.setBackgroundColor(DGenHelpers
				.createConstantColor(ColorConstants.BLUE_LITERAL));
		df.setForegroundColor(DGenHelpers.createRGBColor(0, 0, 255));
		return df;
	}

	private static String getChildDomainClassName(ChildReference ref) {
		String result = "";
		NodeMapping refchild = ref.getChild();
		if (refchild != null) {
			EClass dom = ref.getChild().getDomainMetaElement();
			if (dom != null)
				result = dom.getName();
		}
		return result;
	}

	private static GenModel createGenModel_snippet(final EPackage aModel,final EPackage addModel,String pluginid_,IPath ecorePath ,String basePackage) {
		assert pluginid_ != null;
		GenModel genModel = new org.isoe.fwk.megamodel.deploy.GenModelAccess(aModel,addModel).myCreateDummy(pluginid_);//FP130823
		genModel.setModelPluginID(pluginid_);
		genModel.setModelDirectory("/" + pluginid_ + "/src/");
		genModel.setEditDirectory("/" + pluginid_ + ".edit" + "/src/");
	    IPath genModelPath = ecorePath.removeFileExtension().addFileExtension("genmodel");
        org.eclipse.emf.common.util.URI genModelURI = org.eclipse.emf.common.util.URI.createFileURI(genModelPath.toString());
        genModel.setModelDirectory("/"+genModelURI.trimFileExtension().segment(0)+"/src");
        GenPackage genPackage = genModel.getGenPackages().get(0);
        String prefix = genModelURI.trimFileExtension().lastSegment().substring(0, 1).toUpperCase()+genModelURI.trimFileExtension().lastSegment().substring(1);
        genModel.setModelName(prefix);
        genPackage.setPrefix(prefix);
        genPackage.setBasePackage(basePackage);
        genModel.setModelPluginID(genModelURI.trimFileExtension().segment(0));
        genModel.setEditPluginID(genModelURI.trimFileExtension().segment(0)+".edit");
        genModel.setEditorPluginID(genModelURI.trimFileExtension().segment(0)+".editor");
        genModel.setTestsPluginID(genModelURI.trimFileExtension().segment(0)+".test");
        genModel.setComplianceLevel(org.eclipse.emf.codegen.ecore.genmodel.GenJDKLevel.get(org.eclipse.emf.codegen.ecore.genmodel.GenJDKLevel.values().length-1)); //Set Latest Version
        genModel.setRuntimeVersion(org.eclipse.emf.codegen.ecore.genmodel.GenRuntimeVersion.get(org.eclipse.emf.codegen.ecore.genmodel.GenRuntimeVersion.values().length-1)); //Set Latest Version
		genModel.setGenerateSchema(false);
		genModel.setCanGenerate(true);
		genModel.setCodeFormatting(true);
		genModel.setNonNLSMarkers(true);
		return genModel;
	}

	// jklms
	private static TopNodeReference findNodeMapping(Mapping mapping, String name) {
		EList<TopNodeReference> nodrefs = mapping.getNodes();
		for (TopNodeReference nodref : nodrefs) {
			System.out.println(nodref.getContainmentFeature().getName());
			if (nodref.getContainmentFeature().getName().equals(name))
				return nodref;
		}
		return null;
	}


	private static RealFigure createPolylineConnection(String name) {
		if (LOG)
			System.out.println("createPolylineConnection[");
		PolylineConnection figure = GMFGraphFactory.eINSTANCE
				.createPolylineConnection();
		figure.setName(name + "Figure");
		if (LOG)
			System.out.println("PolylineConnection name: " + figure.getName());
		if (LOG)
			System.out.println("]createPolylineConnection");
		return figure;
	}



	/*** --------------remove------------------------------ ***/

	private static FlowLayout createFlowLayout_old() {
		FlowLayout fl = GMFGraphFactory.eINSTANCE.createFlowLayout();
		fl.setVertical(false);
		return fl;
	}

	private static Node createNodeWithRectangleFigure_old(
			FigureGallery fGallery, String name, boolean withSizeFacet) {
		if (LOG )
			System.out.println("createNodeWithRectangleFigure[");

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
		if (LOG )
			System.out.println("]createNodeWithRectangleFigure");
		return node;
	}

	/** -----------------private---------------------------- **/

	protected static Connection createConnection(String name) {
		if (LOG )
			System.out.println("createConnection[");
		Connection connection = GMFGraphFactory.eINSTANCE.createConnection();
		connection.setName(name);
		if (LOG )
			System.out.println("Connection name: " + connection.getName());
		if (LOG )
			System.out.println("]createConnection");
		return connection;
	}

	protected static DiagramLabel createDiagramLabel(EModelElement element,
			String name) {
		DiagramLabel label = GMFGraphFactory.eINSTANCE.createDiagramLabel();
		label.setName(name);
		if (LOG )
			System.out.println("createDiagramLabel: " + label.getName());
		return label;
	}

	protected static Label createLabel(DiagramLabel label) {
		Label lab = GMFGraphFactory.eINSTANCE.createLabel();
		lab.setName(label.getName() + FIGURE_SUFFIX);
		if (LOG )
			System.out.println("createLabel: " + lab.getName());
		return lab;
	}

	private static String logChildRef(NodeMapping nodeMapping,
			EReference containment, ChildReference childReference) {
		String result = "createChildReference: containment="
				+ containment.getName() + "    ownedChild="
				+ nodeMapping.getDiagramNode().getName() + "\n";

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
		result += " class=" + childReference.getClass().getSimpleName();
		return result;
	}






}
