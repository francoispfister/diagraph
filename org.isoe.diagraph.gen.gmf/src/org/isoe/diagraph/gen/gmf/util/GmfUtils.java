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
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.gmfgraph.Alignment;
import org.eclipse.gmf.gmfgraph.BasicFont;
import org.eclipse.gmf.gmfgraph.ChildAccess;
import org.eclipse.gmf.gmfgraph.Color;
import org.eclipse.gmf.gmfgraph.ColorConstants;
import org.eclipse.gmf.gmfgraph.CompoundBorder;
import org.eclipse.gmf.gmfgraph.Connection;
import org.eclipse.gmf.gmfgraph.ConstantColor;
import org.eclipse.gmf.gmfgraph.DecorationFigure;
import org.eclipse.gmf.gmfgraph.DefaultSizeFacet;
import org.eclipse.gmf.gmfgraph.DiagramElement;
import org.eclipse.gmf.gmfgraph.DiagramLabel;
import org.eclipse.gmf.gmfgraph.Dimension;
import org.eclipse.gmf.gmfgraph.Figure;
import org.eclipse.gmf.gmfgraph.FigureDescriptor;
import org.eclipse.gmf.gmfgraph.FigureGallery;
import org.eclipse.gmf.gmfgraph.FlowLayout;
import org.eclipse.gmf.gmfgraph.Font;
import org.eclipse.gmf.gmfgraph.FontStyle;
import org.eclipse.gmf.gmfgraph.GMFGraphFactory;
import org.eclipse.gmf.gmfgraph.Label;
import org.eclipse.gmf.gmfgraph.LineBorder;
import org.eclipse.gmf.gmfgraph.LineKind;
import org.eclipse.gmf.gmfgraph.MarginBorder;
import org.eclipse.gmf.gmfgraph.Node;
import org.eclipse.gmf.gmfgraph.Point;
import org.eclipse.gmf.gmfgraph.Polygon;
import org.eclipse.gmf.gmfgraph.PolygonDecoration;
import org.eclipse.gmf.gmfgraph.PolylineConnection;
import org.eclipse.gmf.gmfgraph.PolylineDecoration;
import org.eclipse.gmf.gmfgraph.RGBColor;
import org.eclipse.gmf.gmfgraph.RealFigure;
import org.eclipse.gmf.gmfgraph.Rectangle;
import org.eclipse.gmf.gmfgraph.RoundedRectangle;
import org.eclipse.gmf.gmfgraph.VisualFacet;

/**
 *
 * @author pfister
 * fragments borrowed from topcased
 */
public class GmfUtils implements GmfConstants {

	private static final boolean LOG = false;
	//private static GmfUtils instance;
	//private PolylineDecoration arrowDecoration = null;
	//private PolygonDecoration triangleDecoration = null;
	private FigureGallery figureGallery = null;

	public GmfUtils(FigureGallery fg) { //FP120929
		super();
		this.figureGallery = fg;
	}





	public DiagramLabel createDiagramLabelsInFigure(Node node,
			List<EAttribute> attributes, String[] separators, String labelName) {
		DiagramLabel diagramLabel = createLabelsAndAddToFigureDescriptor(node,
				attributes, separators, labelName);
		return diagramLabel;
	}
	
	public DiagramLabel createDiagramLabelInFigure(Node node,  EAttribute labelAttribute, String labelName) {
		DiagramLabel diagramLabel = createLabelAndAddToFigureDescriptor(node,
				 labelAttribute, labelName);
		return diagramLabel;
	}
	
	
	private DiagramLabel createDiagramLabel(String name) {
		DiagramLabel label = GMFGraphFactory.eINSTANCE.createDiagramLabel();
		label.setName(name);
		//label.setElementIcon(false);
		return label;
	}


	private DiagramLabel createLabelAndAddToFigureDescriptor(
			DiagramElement diagElement,
			EAttribute attribute, String labelName) {
		DiagramLabel label = createDiagramLabel(labelName);
		RealFigure labelFigure = createLabel(label);
 		if (diagElement != null)
			addLabelFigureToContainerFigureDescriptor(diagElement, labelFigure,
					label);
		else
			addLabelFigureToNewFigureDescriptor(labelFigure, label);
		return label;
	}
	
	
	private DiagramLabel createLabelsAndAddToFigureDescriptor(DiagramElement diagElement,
			List<EAttribute> attributes, String[] separators, String labelName) { //FP140206
		DiagramLabel label = createDiagramLabel(labelName);
		RealFigure labelFigure = createLabel(label);
		if (diagElement != null)
			addLabelFigureToContainerFigureDescriptor(diagElement, labelFigure,
					label);
		else
			addLabelFigureToNewFigureDescriptor(labelFigure, label);
		return label;
	}


	/*
	private RealFigure createRectangle_nu(String name) {
		Rectangle figure = GMFGraphFactory.eINSTANCE.createRectangle();
		figure.setName(name + FIGURE_SUFFIX);
		return figure;
	}

	private FlowLayout createFlowLayout_nu() {
		FlowLayout fl = GMFGraphFactory.eINSTANCE.createFlowLayout();
		fl.setVertical(false);
		return fl;
	}
	*/

	private VisualFacet createDefaultSizeFacet(String name) {
		Dimension dimension = GMFGraphFactory.eINSTANCE.createDimension();
		dimension.setDx(0);
		dimension.setDy(0);
		DefaultSizeFacet facet = GMFGraphFactory.eINSTANCE
				.createDefaultSizeFacet();
		facet.setDefaultSize(EcoreUtil.copy(dimension));
		return facet;
	}

	private Node createNode(String name) {
		Node node = GMFGraphFactory.eINSTANCE.createNode();
		node.setName(name);
		return node;
	}







	private FigureDescriptor createRectangle_not_used_(String name,
			boolean withLabel) {

		FigureDescriptor figureDescriptor = GMFGraphFactory.eINSTANCE
				.createFigureDescriptor();
		figureDescriptor.setName(name + FIGURE_DESCRIPTOR_SUFFIX);


		Rectangle rectangle = GMFGraphFactory.eINSTANCE.createRectangle();
	//	RoundedRectangle rectangle = GMFGraphFactory.eINSTANCE.createRoundedRectangle();
		figureDescriptor.setActualFigure(rectangle);
		rectangle.setName(name + FIGURE_SUFFIX);
		rectangle.setFill(true);
		rectangle.setLineKind(LineKind.LINE_SOLID_LITERAL);
		rectangle.setLineWidth(1);
		rectangle.setOutline(true);
		rectangle.setXorFill(false);
		rectangle.setXorOutline(false);
		//rectangle.setBorder(createBorder());


		FlowLayout flowLayout = GMFGraphFactory.eINSTANCE.createFlowLayout();
		rectangle.setLayout(flowLayout);
		flowLayout.setForceSingleLine(true);
		flowLayout.setMajorAlignment(Alignment.CENTER_LITERAL);
		flowLayout.setMajorSpacing(0);
		flowLayout.setMatchMinorSize(true);
		flowLayout.setMinorAlignment(Alignment.CENTER_LITERAL);
		flowLayout.setMinorSpacing(0);
		flowLayout.setVertical(true);

		rectangle.setBackgroundColor(createConstantColor(ColorConstants.WHITE_LITERAL));

		rectangle.setForegroundColor(createConstantColor(ColorConstants.BLACK_LITERAL));
		if (withLabel)
			addLabelToFigure(rectangle, name, "<...>",figureDescriptor);
		return figureDescriptor;
	}


	private CompoundBorder createBorder() {

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





	private void addLabelToFigure(RealFigure figure, String name, String text, FigureDescriptor figureDescriptor){
			Label label = GMFGraphFactory.eINSTANCE.createLabel();
			figure.getChildren().add(label);
			label.setName(name + LABEL_SUFFIX);
			if (text!=null)
			  label.setText(text);
			ChildAccess childAccess = GMFGraphFactory.eINSTANCE
					.createChildAccess();
			figureDescriptor.getAccessors().add(childAccess);
			childAccess.setFigure(label);
			childAccess.setAccessor(ACCESSOR_PREFIX + label.getName());
	}

	private Label createLabel(DiagramLabel label) {
		Label lab = GMFGraphFactory.eINSTANCE.createLabel();
		lab.setName(label.getName() + FIGURE_SUFFIX);
		lab.setFont(createBasicFont("Tahoma", 12,
				FontStyle.ITALIC_LITERAL ));
		return lab;
	}

	private void addLabelFigureToContainerFigureDescriptor(
			DiagramElement diagramElement, RealFigure labelFigure,
			DiagramLabel label) {
		FigureDescriptor diagElementFigureDescr = diagramElement.getFigure();
		RealFigure diagElementActualFigure = (RealFigure) diagElementFigureDescr
				.getActualFigure();
		diagElementActualFigure.getChildren().add(labelFigure);
		label.setFigure(diagElementFigureDescr);
		label.setAccessor(createChildAccess(diagElementFigureDescr, labelFigure));
	}

	private void addLabelFigureToNewFigureDescriptor(RealFigure labelFigure, DiagramLabel label) {
		FigureDescriptor fd = GMFGraphFactory.eINSTANCE.createFigureDescriptor();
		fd.setName(labelFigure.getName());
		fd.setActualFigure(labelFigure);
		figureGallery.getDescriptors().add(fd);
		label.setFigure(fd);
	}

	private ChildAccess createChildAccess(FigureDescriptor fd,
			RealFigure fig) {
		ChildAccess ca = GMFGraphFactory.eINSTANCE.createChildAccess();
		ca.setFigure(fig);
		fd.getAccessors().add(ca);
		return ca;
	}




	private Font createBasicFont(String name, int height, FontStyle style) {
		BasicFont result = GMFGraphFactory.eINSTANCE.createBasicFont();
		result.setFaceName(name);
		result.setHeight(height);
		result.setStyle(style);
		return result;
	}

	private ConstantColor createConstantColor(ColorConstants constant) {
		ConstantColor result = GMFGraphFactory.eINSTANCE.createConstantColor();
		result.setValue(constant);
		return result;
	}

	private RGBColor createRGBColor(int red, int green, int blue) {
		RGBColor result = GMFGraphFactory.eINSTANCE.createRGBColor();
		result.setRed(red);
		result.setGreen(green);
		result.setBlue(blue);
		return result;
	}

	private RGBColor createColor(Color color) {
		RGBColor rgbColor = GMFGraphFactory.eINSTANCE.createRGBColor();
		rgbColor.setBlue(((RGBColor) color).getBlue());
		rgbColor.setRed(((RGBColor) color).getRed());
		rgbColor.setGreen(((RGBColor) color).getGreen());
		return rgbColor;
	}

	private RealFigure getSampleFigure() {
		RealFigure result;
		result = GMFGraphFactory.eINSTANCE.createEllipse();
		result.setName("FullOfColorsAndFonts");
		result.setFont(createBasicFont("Arial", 23, FontStyle.BOLD_LITERAL));
		result.setForegroundColor(createConstantColor(ColorConstants.ORANGE_LITERAL));
		result.setBackgroundColor(createConstantColor(ColorConstants.GREEN_LITERAL));

		Label sansLabel = GMFGraphFactory.eINSTANCE.createLabel();
		sansLabel.setName("SansLabel");
		sansLabel.setFont(createBasicFont("Sans", 8, FontStyle.ITALIC_LITERAL));
		sansLabel
				.setForegroundColor(createConstantColor(ColorConstants.BLUE_LITERAL));
		result.getChildren().add(sansLabel);

		Label tahomaLabel = GMFGraphFactory.eINSTANCE.createLabel();
		tahomaLabel.setName("TahomaLabel");
		tahomaLabel.setFont(createBasicFont("Tahoma", 12,
				FontStyle.NORMAL_LITERAL));
		tahomaLabel
				.setForegroundColor(createConstantColor(ColorConstants.YELLOW_LITERAL));
		result.getChildren().add(tahomaLabel);

		Rectangle deepLabelContainer = GMFGraphFactory.eINSTANCE
				.createRectangle();
		deepLabelContainer.setName("DeepLabelContainer");
		deepLabelContainer.setForegroundColor(createRGBColor(123, 23, 3));
		deepLabelContainer.setBackgroundColor(createRGBColor(2, 123, 23));
		result.getChildren().add(deepLabelContainer);

		Label defaultFontLabel = GMFGraphFactory.eINSTANCE.createLabel();
		defaultFontLabel.setName("DefaultFontLabel");
		defaultFontLabel.setFont(createBasicFont(null, 34,
				FontStyle.BOLD_LITERAL));
		defaultFontLabel
				.setForegroundColor(createConstantColor(ColorConstants.CYAN_LITERAL));
		deepLabelContainer.getChildren().add(defaultFontLabel);

		return result;
	}

/*-------------------Connection------------------------*/

	public Connection createSimpleConnectionWithPolyline(String name) { //FP120928zz //FP120720
		Connection connection = GMFGraphFactory.eINSTANCE.createConnection();
		connection.setName(name);
		RealFigure figure = GMFGraphFactory.eINSTANCE.createPolylineConnection();
		figure.setName(name + FIGURE_SUFFIX);
		FigureDescriptor fd = GMFGraphFactory.eINSTANCE.createFigureDescriptor();
		fd.setName(figure.getName());
		fd.setActualFigure(figure);
		figureGallery.getDescriptors().add(fd);
		connection.setFigure(fd);
		return connection;
	}

	public Connection createConnectionWithPolylineArrow(DecorationType decorationType,String name,int arrowSize,int linewidth_,String color) { //FP120928zz
		if (LOG)
			clog("createConnectionWithPolylineArrow for "+name);
		Connection connection = GMFGraphFactory.eINSTANCE.createConnection();
		connection.setName(name);
		FigureDescriptor fd = GMFGraphFactory.eINSTANCE.createFigureDescriptor();
		fd.setName(name + FIGURE_SUFFIX);
		fd.setActualFigure(getArrowFigure(decorationType,name,color,arrowSize,linewidth_,name+"Polyline"));
		figureGallery.getDescriptors().add(fd);
		connection.setFigure(fd);
		return connection;
	}


	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}


	private void decoratePolylineConnection(DecorationType decorationType, PolylineConnection polylineConnection, int arrowSize,int linewidth,String name) {
		DecorationFigure sourceDecoration = getDecoration(DecorationType.NONE_LITERAL);//FP130915//DecorationType.NONE_LITERAL);
		if (sourceDecoration!=null)
		   polylineConnection.setSourceDecoration(sourceDecoration);
		DecorationFigure targetDecoration = getArrowDecoration(decorationType, arrowSize,linewidth, name);
		//if (decorationType_ == DecorationType.ARROW_LITERAL) //FP130616
		 //  targetDecoration = getArrowDecoration(decorationType_, arrowSize);
		if (targetDecoration != null)
		   polylineConnection.setTargetDecoration(targetDecoration);
	}



	private DecorationFigure getDecoration(DecorationType decorationType) {
		switch (decorationType.getValue()) {
		case DecorationType.ARROW:
			//if (arrowDecoration == null) {
			    PolylineDecoration arrowDecoration = GMFGraphFactory.eINSTANCE
						.createPolylineDecoration();
				arrowDecoration.getTemplate().addAll(getArrowPoint());
				figureGallery.getFigures().add(arrowDecoration);
			//}
			return arrowDecoration;
		case DecorationType.TRIANGLE:
			//if (arrowDecoration == null) {
			    PolygonDecoration triangleDecoration = GMFGraphFactory.eINSTANCE
						.createPolygonDecoration();
				triangleDecoration.getTemplate().addAll(getArrowPoint());
				ConstantColor whiteColor = GMFGraphFactory.eINSTANCE
						.createConstantColor();
				whiteColor.setValue(ColorConstants.WHITE_LITERAL);
				triangleDecoration.setBackgroundColor(whiteColor);
				figureGallery.getFigures().add(triangleDecoration);
			//}
			return triangleDecoration;
		default:
			return null;
		}
	}




	private DecorationFigure getArrowDecoration(DecorationType decorationType, int arrowSize, int linewidth, String name) {
		switch (decorationType.getValue()) {
		case DecorationType.ARROW:
			PolylineDecoration arrowDecoration = GMFGraphFactory.eINSTANCE
						.createPolylineDecoration();
				arrowDecoration.getTemplate().addAll(getArrow(arrowSize));
				arrowDecoration.setLineWidth(linewidth);
				figureGallery.getFigures().add(arrowDecoration);
				arrowDecoration.setName(name);
			return arrowDecoration;
		case DecorationType.TRIANGLE:
			    PolygonDecoration triangleDecoration = GMFGraphFactory.eINSTANCE
						.createPolygonDecoration();
			    triangleDecoration.setLineWidth(linewidth);
				triangleDecoration.getTemplate().addAll(getTriangle(arrowSize)); //FP130616
				ConstantColor whiteColor = GMFGraphFactory.eINSTANCE.createConstantColor();
				whiteColor.setValue(ColorConstants.WHITE_LITERAL);
				triangleDecoration.setBackgroundColor(whiteColor);
				figureGallery.getFigures().add(triangleDecoration);
				triangleDecoration.setName(name);
			return triangleDecoration;
		default:
			return null;
		}
	}

	private Figure getArrowFigure(DecorationType decorationType, String prefix, String color, int arrowSize, int linewidth,String name) { //FP120721
		PolylineConnection result = GMFGraphFactory.eINSTANCE.createPolylineConnection();
		result.setLineKind(LineKind.LINE_SOLID_LITERAL);
		result.setForegroundColor(createConstantColor(ColorConstants.get(color)));
		if (linewidth <1)
			linewidth = 1; //FP130915
		result.setLineWidth(linewidth);
		result.setName(prefix + POLYLINE_CONNECTION_SUFFIX);
		//result.setName(name);
		//polylineConnection.setForegroundColor(createConstantColor(ColorConstants.BLACK_LITERAL));
		decoratePolylineConnection(decorationType,result,arrowSize,linewidth,name+"Decoration");//FP130915
		return result;
	}


	private Point createPoint(int x, int y) {
		Point point = GMFGraphFactory.eINSTANCE.createPoint();
		point.setX(x);
		point.setY(y);
		return point;
	}

	private List<Point> getArrowPoint() {
		List<Point> points = new ArrayList<Point>();
		points.add(createPoint(-1, -1));
		points.add(createPoint(0, 0));
		points.add(createPoint(-1, 1));
		return points;
	}


	private Collection<? extends Point> getArrow(int arrowSize) {
		List<Point> points = new ArrayList<Point>();
		points.add(createPoint(-arrowSize, -arrowSize));
		points.add(createPoint(0, 0));
		points.add(createPoint(-arrowSize, arrowSize));
		return points;
	}


	private Collection<? extends Point> getTriangle(int arrowSize) {
		List<Point> points = new ArrayList<Point>();
		points.add(createPoint(-arrowSize, -arrowSize));
		points.add(createPoint(0, 0));
		points.add(createPoint(-arrowSize, arrowSize));
		points.add(createPoint(-arrowSize, -arrowSize));
		return points;
	}





	/*-------------Connection end------------------------------*/







	/****  not used ***********/

	/*
	public Node createNodeWithRectangleFigure_not_used(String name, boolean withLabel, boolean withSizeFacet) {
		Node node = createNode(name);
		FigureDescriptor fd = createRectangle_not_used_(name,withLabel);
		if (withSizeFacet)
			node.getFacets().add(createDefaultSizeFacet(node.getName()));
		figureGallery.getDescriptors().add(fd);
		node.setFigure(fd);
		return node;
	}


	private Connection createConnectionWithPolylineArrow_nu(String name) {
		Connection connection = GMFGraphFactory.eINSTANCE.createConnection();
		connection.setName(name);
		FigureDescriptor fd = GMFGraphFactory.eINSTANCE
				.createFigureDescriptor();
		fd.setName(name + "Figure");
		fd.setActualFigure(getArrowFigure_(name));
		figureGallery.getDescriptors().add(fd);
		connection.setFigure(fd);
		return connection;
	}



	private Figure getArrowFigure_(String prefix) {
		PolylineConnection polylineConnection = GMFGraphFactory.eINSTANCE
				.createPolylineConnection();
		polylineConnection.setLineKind(LineKind.LINE_SOLID_LITERAL);
		polylineConnection.setForegroundColor(createConstantColor(ColorConstants.BLACK_LITERAL));
		polylineConnection.setLineWidth(1);
		polylineConnection.setName(prefix + POLYLINE_CONNECTION_SUFFIX);
		decoratePolylineConnection_(polylineConnection);
		return polylineConnection;
	}

	private FigureDescriptor createRectangle2(String name) {
		FigureDescriptor figureDescriptor = GMFGraphFactory.eINSTANCE
				.createFigureDescriptor();
		figureDescriptor.setName(name + FIGURE_DESCRIPTOR_SUFFIX);
		RealFigure figure = createRectangle(name);
		figure.setLayout(createFlowLayout());
		figureDescriptor.setActualFigure(figure);
		// figureGallery.getDescriptors().add(figureDescriptor);
		return figureDescriptor;
	}


	private FigureDescriptor getArrowFigureDescriptor_nu(String prefix) {
		FigureDescriptor figureDescriptor = GMFGraphFactory.eINSTANCE
				.createFigureDescriptor();
		figureDescriptor.setName(prefix + FIGURE_DESCRIPTOR_SUFFIX);
		figureDescriptor.setActualFigure(getArrowFigure_(prefix));
		return figureDescriptor;
	}

	private Connection getConnection(String prefix) {
		Connection connection = GMFGraphFactory.eINSTANCE.createConnection();
		connection.setName(prefix + CONNECTION_SUFFIX);
		return connection;
	}

	private void decoratePolylineConnection_(
			PolylineConnection polylineConnection) {
		DecorationFigure sourceDecoration = getDecoration_(DecorationType.NONE_LITERAL);
		polylineConnection.setSourceDecoration(sourceDecoration);
		DecorationFigure targetDecoration = getDecoration_(DecorationType.ARROW_LITERAL);
		polylineConnection.setTargetDecoration(targetDecoration);
	}

*/





}
