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
package org.isoe.diagraph.diastyle.helpers;

import org.isoe.diagraph.diastyle.DAlignment;
import org.isoe.diagraph.diastyle.DBaseStyle;
import org.isoe.diagraph.diastyle.DColor;
import org.isoe.diagraph.diastyle.DDirection;
import org.isoe.diagraph.diastyle.DEdgeStyle;
import org.isoe.diagraph.diastyle.DFontName;
import org.isoe.diagraph.diastyle.DFontStyle;
import org.isoe.diagraph.diastyle.DLayout;
import org.isoe.diagraph.diastyle.DLine;
import org.isoe.diagraph.diastyle.DNestingEdgeStyle;
import org.isoe.diagraph.diastyle.DNodeEdgeStyle;
import org.isoe.diagraph.diastyle.DNodeStyle;
import org.isoe.diagraph.diastyle.DShape;
import org.isoe.diagraph.diastyle.DStyle;
import org.isoe.diagraph.diastyle.DiastyleFactory;
import org.isoe.diagraph.diastyle.keywords.BaseKeywords;
import org.isoe.diagraph.diastyle.keywords.EdgeKeywords;
import org.isoe.diagraph.diastyle.keywords.NodeEdgeKeywords;
import org.isoe.diagraph.diastyle.keywords.NodeKeywords;
import org.isoe.diagraph.diastyle.keywords.PartitionKeywords;


/**
 *
 * @author pfister
 *
 */
public class StyleFactory implements StyleKeywords, BaseKeywords, EdgeKeywords,
		NodeEdgeKeywords, NodeKeywords, PartitionKeywords {

	private static final boolean LOG = false;

	private boolean done;
	private IStyleHandler handler;
	private DStyle dStyle;

	public void createModel() {
		dStyle = DiastyleFactory.eINSTANCE.createDStyle();
	}

	public void setStyle(DStyle dStyle) {
		this.dStyle = dStyle;
	}

	public StyleFactory(IStyleHandler handler) {
		this.handler = handler;
	}

	private void parseNodeSize(DNodeStyle styl, String values) {
		String[] k2 = values.split(",");
		if (k2.length != 2)
			throw new RuntimeException(
					"parseNodeSize - should have 2 arguments !!!");
		int width = Integer.parseInt(k2[0]);
		int height = Integer.parseInt(k2[1]);
		styl.setSizeX(width);
		styl.setSizeY(height);
	}

	private void parseArrowSize(DEdgeStyle styl, String value) {
		styl.setArrowSize(Integer.parseInt(value));
	}

	private void parseEdgeLineWidth(DEdgeStyle styl, String value) {
		styl.setLineWidth(Integer.parseInt(value));
		// styl.setEdgeLineWidth(Integer.parseInt(value));
	}

	private void parseEdgeLine(DNodeEdgeStyle styl, String value) { // FP130616
		styl.setLine(parseLine(value));
		// styl.setLine(li);
		// styl.setLine(parseLine(value));
		// styl.setEdgeLine(parseLine(value));
	}

	private void parseEdgeShape(DEdgeStyle styl, String value) {
		styl.setShape(parseShape(value));
		if (LOG)
			clog("parseEdgeShape for " + styl.getName()+" value="+value );
		// styl.setEdgeShape(parseShape(value));
	}

	int parseShape___old(String shape) {
		if (shape.equals("rounded") || shape.equals("roundedrectangle"))
			return DShape.ROUNDED_RECTANGLE_VALUE;
		else if (shape.equals("rectangle"))
			return DShape.RECTANGLE_VALUE;
		else if (shape.equals("ellipse"))
			return DShape.ELLIPSE_VALUE;
		else if (shape.equals("custom"))
			return DShape.CUSTOM_VALUE;
		else
			throw new RuntimeException("unknown shape: " + shape);
	}

	DShape parseShape(String shape) {
		if (shape.equals("rounded") || shape.equals("roundedrectangle"))
			return DShape.ROUNDED_RECTANGLE;
		else if (shape.equals("rectangle"))
			return DShape.RECTANGLE;
		else if (shape.equals("ellipse"))
			return DShape.ELLIPSE;
		else if (shape.equals("triangle"))
			return DShape.TRIANGLE;
		else if (shape.equals("arrow"))
			return DShape.ARROW;
		else if (shape.equals("dot"))
			return DShape.DOT;
		else if (shape.equals("custom"))
			return DShape.CUSTOM;
		else
			throw new RuntimeException("unknown shape: " + shape);
	}

	int parseLayout(String layout) {
		if (layout.equals("none"))
			return DLayout.NONE_VALUE;
		else if (layout.equals("free"))
			return DLayout.FREE_VALUE;
		else if (layout.equals("horizontal"))
			return DLayout.HORIZONTAL_VALUE;
		else if (layout.equals("vertical"))
			return DLayout.VERTICAL_VALUE;
		else
			throw new RuntimeException("unknown layout: " + layout);
	}

	int parseDirection(String direction) {
		if (direction.equals("right"))
			return DDirection.RIGHT_VALUE;
		else if (direction.equals("none"))
			return DDirection.NONE_VALUE;
		else if (direction.equals("left"))
			return DDirection.LEFT_VALUE;
		else if (direction.equals("bidirectional"))
			return DDirection.BIDIRECTIONAL_VALUE;
		else
			throw new RuntimeException("unknown direction: " + direction);
	}

	int parseAlignment(String align) {
		if (align.equals("beginning"))
			return DAlignment.BEGINNING_VALUE;
		else if (align.equals("center"))
			return DAlignment.CENTER_VALUE;
		else if (align.equals("end"))
			return DAlignment.END_VALUE;
		else if (align.equals("fill"))
			return DAlignment.FILL_VALUE;
		else
			throw new RuntimeException("unknown alignment: " + align);
	}

	int parseLine___old(String line) {
		if (line.equals("solid"))
			return DLine.SOLID_VALUE;
		else if (line.equals("dashed") || line.equals("dash"))
			return DLine.DASH_VALUE;
		else if (line.equals("dot"))
			return DLine.DOT_VALUE;
		else if (line.equals("dashdot"))
			return DLine.DASHDOT_VALUE;
		else if (line.equals("dashdotdot"))
			return DLine.DASHDOTDOT_VALUE;
		else if (line.equals("custom"))
			return DLine.CUSTOM_VALUE;
		else
			throw new RuntimeException("unknown line style: " + line);
	}

	DLine parseLine(String line) {
		if (line.equals("solid"))
			return DLine.SOLID;
		else if (line.equals("dashed") || line.equals("dash"))
			return DLine.DASH;
		else if (line.equals("dot"))
			return DLine.DOT;
		else if (line.equals("dashdot"))
			return DLine.DASHDOT;
		else if (line.equals("dashdotdot"))
			return DLine.DASHDOTDOT;
		else if (line.equals("custom"))
			return DLine.CUSTOM;
		else
			throw new RuntimeException("unknown line style: " + line);
	}

	int parseFontName(String fontname) {
		if (fontname.equals("arial"))
			return DFontName.ARIAL_VALUE;
		else if (fontname.equals("courier"))
			return DFontName.COURIER_VALUE;
		else if (fontname.equals("times "))
			return DFontName.TIMES_VALUE;
		else
			throw new RuntimeException("unknown font name: " + fontname);
	}

	int parseFontStyle(String fontStyle) {
		if (fontStyle.equals("normal"))
			return DFontStyle.NORMAL_VALUE;
		else if (fontStyle.equals("bold"))
			return DFontStyle.BOLD_VALUE;
		else if (fontStyle.equals("italic"))
			return DFontStyle.ITALIC_VALUE;
		else
			throw new RuntimeException("unknown font style: " + fontStyle);
	}

	int parseColor(String color) {
		if (color.equals(DIASTYLE_COLOR_WHITE))
			return DColor.WHITE_VALUE;
		else if (color.equals(DIASTYLE_COLOR_BLACK))
			return DColor.BLACK_VALUE;
		else if (color.equals(DIASTYLE_COLOR_LIGHT_GRAY))
			return DColor.LIGHT_GRAY_VALUE;
		else if (color.equals(DIASTYLE_COLOR_GRAY))
			return DColor.GRAY_VALUE;
		else if (color.equals(DIASTYLE_COLOR_DARK_GRAY))
			return DColor.DARK_GRAY_VALUE;
		else if (color.equals(DIASTYLE_COLOR_RED))
			return DColor.RED_VALUE;
		else if (color.equals(DIASTYLE_COLOR_ORANGE))
			return DColor.ORANGE_VALUE;
		else if (color.equals(DIASTYLE_COLOR_YELLOW))
			return DColor.YELLOW_VALUE;
		else if (color.equals(DIASTYLE_COLOR_GREEN))
			return DColor.GREEN_VALUE;
		else if (color.equals(DIASTYLE_COLOR_LIGHT_GREEN))
			return DColor.LIGHT_GREEN_VALUE;
		else if (color.equals(DIASTYLE_COLOR_DARK_GREEN))
			return DColor.DARK_GREEN_VALUE;
		else if (color.equals(DIASTYLE_COLOR_CYAN))
			return DColor.CYAN_VALUE;
		else if (color.equals(DIASTYLE_COLOR_LIGHT_BLUE))
			return DColor.LIGHT_BLUE_VALUE;
		else if (color.equals(DIASTYLE_COLOR_BLUE))
			return DColor.BLUE_VALUE;
		else if (color.equals(DIASTYLE_COLOR_DARK_BLUE))
			return DColor.DARK_BLUE_VALUE;
		else
			throw new RuntimeException("unknown color: " + color);
	}

	void setShape(DNodeStyle styl, String value) {
		styl.setShape(parseShape(value)); // FP130616
		// styl.setShape(DShape.get(parseShape(value)));
		if (LOG)
			clog("shape=" + value);
		done = true;
	}

	void setLayout(DNodeStyle styl, String value) {
		styl.setLayout(DLayout.get(parseLayout(value)));
		if (LOG)
			clog("layout=" + value);
		done = true;
	}

	void setShapeData(DNodeStyle styl, String value) {
		styl.setShapeData(value);
		if (LOG)
			clog("shapedata=" + value);
		done = true;
	}

	public void setCustomFigure(DNodeStyle styl, String value) {
		styl.setFigure(value);
		if (LOG)
			clog("custom-figure=" + value);
		done = true;
	}

	private void setIcon(DNodeEdgeStyle styl, String value) {// FP120930
		styl.setIcon(value);
		if (LOG)
			clog("icon=" + value);
		done = true;
	}

	void setNodeSize(DNodeStyle styl, String value) {
		parseNodeSize(styl, value);
		if (LOG)
			clog("nodesize=" + value);
		done = true;
	}

	void setArrowSize(DEdgeStyle styl, String value) {
		parseArrowSize(styl, value);
		if (LOG)
			clog("arrowsize=" + value);
		done = true;
	}

	public void setEdgeShape(DEdgeStyle styl, String value) {
		parseEdgeShape(styl, value);
		if (LOG)
			clog("edgeshape=" + value);
		done = true;
	}

	public void setEdgeLineWidth(DEdgeStyle styl, String value) {
		parseEdgeLineWidth(styl, value);
		if (LOG)
			clog("edgelinewidth=" + value);
		done = true;
	}

	public void setEdgeLine(DEdgeStyle styl, String value) {
		parseEdgeLine(styl, value);
		if (LOG)
			clog("edgeline=" + value);
		done = true;
	}

	void setDirection(DEdgeStyle styl, String value) {
		styl.setArrowDirection(DDirection.get(parseDirection(value)));
		if (LOG)
			clog("arrowdirection=" + value);
		done = true;
	}

	void setRadius(DNodeStyle styl, String value) {
		styl.setRadius(Integer.parseInt("5"));
		if (LOG)
			clog("radius=" + value);
		done = true;
	}

	private void setFontName(DNodeEdgeStyle styl, String value) {
		// FP120929
		styl.setFontName(DFontName.get(parseFontName(value)));
		if (LOG)
			clog("fontname=" + value);
		done = true;
	}

	private void setColor(DBaseStyle styl, String value) {
		styl.setColor(DColor.get(parseColor(value)));
		if (LOG)
			clog("color=" + value);
		done = true;
	}

	private void setParent(DBaseStyle styl, String value) {
		// styl.setParent(styl);
		styl.setParentName(value);
		if (LOG)
			clog("parent=" + value);
		done = true;
	}

	private void setFontColor(DNodeEdgeStyle styl, String value) {
		// FP120929
		styl.setFontColor(DColor.get(parseColor(value)));
		if (LOG)
			clog("fontcolor=" + value);
		done = true;
	}

	private void setFontStyle(DNodeEdgeStyle styl, String value) {
		// FP120929
		styl.setFontStyle(DFontStyle.get(parseFontStyle(value)));
		if (LOG)
			clog("fontstyle=" + value);
		done = true;
	}

	private void setFontSize(DNodeEdgeStyle styl, String value) {
		// FP120929
		styl.setFontSize(Integer.parseInt(value));
		if (LOG)
			clog("fontsize=" + value);
		done = true;
	}

	private void setLine(DNodeEdgeStyle styl, String value) {
		// FP120929
		styl.setLine(parseLine(value)); // FP130616
		// styl.setLine(DLine.get(parseLine(value)));
		if (LOG)
			clog("line=" + value);
		done = true;
	}

	private void setLineWidth(DNodeEdgeStyle styl, String value) {
		// FP120929
		styl.setLineWidth(Integer.parseInt(value));
		if (LOG)
			clog("linewidth=" + value);
		done = true;
	}

	private void setTextAlignment(DNodeEdgeStyle styl, String value) {
		// FP120929
		styl.setTextAlignment(DAlignment.get(parseAlignment(value)));
		if (LOG)
			clog("alignment=" + value);
		done = true;
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	private boolean isBaseProperty(String property) {
		for (String prop : DIASTYLE_BASE_PROPERTIES)
			if (prop.equals(property))
				return true;
		return false;
	}

	private boolean isNodeProperty(String property) {
		for (String prop : DIASTYLE_NODE_PROPERTIES)
			if (prop.equals(property))
				return true;
		return false;
	}

	private boolean isPartitionProperty(String property) {
		for (String prop : DIASTYLE_PARTITION_PROPERTIES)
			if (prop.equals(property))
				return true;
		return false;
	}

	private boolean isNodeEdgeProperty(String property) {
		for (String prop : DIASTYLE_NODE_EDGE_PROPERTIES)
			if (prop.equals(property))
				return true;
		return false;
	}

	private boolean isEdgeProperty(String property) {
		for (String prop : DIASTYLE_EDGE_PROPERTIES)
			if (prop.equals(property))
				return true;
		return false;
	}

	boolean isValidProperty(String property) {
		return isBaseProperty(property) || isEdgeProperty(property)
				|| isNodeProperty(property) || isNodeEdgeProperty(property)
				|| isPartitionProperty(property);
	}

	void setGenericBaseStyleProperties(String property, DBaseStyle styl,
			String value) {
		if (property.equals(DIASTYLE_BASE_PARENT)) {
			setParent(styl, value);
			return;
		} else if (property.equals(DIASTYLE_BASE_COLOR_)) {
			setColor(styl, value);
			return;
		}
	}

	boolean setNodeEdgeStyleProperties(String property, DNodeEdgeStyle styl,
			String value) {
		if (property.equals(DIASTYLE_NODE_EDGE_FONT)) {
			setFontName(styl, value);
			return true;
		} else if (property.equals(DIASTYLE_NODE_EDGE_FONTCOLOR)) {
			setFontColor(styl, value);
			return true;
		} else if (property.equals(DIASTYLE_NODE_EDGE_FONTSTYLE)) {
			setFontStyle(styl, value);
			return true;
		} else if (property.equals(DIASTYLE_NODE_EDGE_ALIGNMENT)) {
			setTextAlignment(styl, value);
			return true;
		} else if (property.equals(DIASTYLE_NODE_EDGE_LINEWIDTH)) {
			setLineWidth(styl, value);
			return true;
		} else if (property.equals(DIASTYLE_NODE_EDGE_LINE)) {
			setLine(styl, value);
			return true;
		} else if (property.equals(DIASTYLE_NODE_EDGE_FONTSIZE)) {
			setFontSize(styl, value);
			return true;
		} else if (property.equals(DIASTYLE_NODE_EDGE_ICON)) {
			setIcon(styl, value);
			return true;
		}
		return false;

	}

	public void setNestingEdgeStyleProperties(String property,
			DNestingEdgeStyle styl, String value) {
		if (property.equals(DIASTYLE_PARTITION_XX)) {
			;// setXX(styl, value);
			return;
		}
	}

	public void prepare() {
		done = false;
	}

	public boolean isDone() {
		return done;
	}

	public DStyle getdStyle() {
		return dStyle;
	}

	public boolean setEdgeStyleProperties(String property, DEdgeStyle styl,
			String value) { // FP130616
		if (property.equals(DIASTYLE_EDGE_ARROWSIZE)) {
			setArrowSize(styl, value);
			return true;
		} else if (property.equals(DIASTYLE_EDGE_DIRECTION)) {
			setDirection(styl, value);
			return true;
		} else if (property.equals(DIASTYLE_EDGE_SHAPE)) {
			setEdgeShape(styl, value);
			return true;
		} /*else if (property.equals(DIASTYLE_EDGE_LINEWIDTH)) {
			setEdgeLineWidth(styl, value);
			return true;
		} else if (property.equals(DIASTYLE_EDGE_LINE)) {
			setEdgeLine(styl, value);
			return true;
		}*/
		return false;

	}

}
