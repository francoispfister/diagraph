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

import java.util.List;

import org.isoe.diagraph.diagraph.DCompartmentEdge;
import org.isoe.diagraph.diagraph.DEdge;
import org.isoe.diagraph.diagraph.DGraphElement;
import org.isoe.diagraph.diagraph.DNestedEdge;
import org.isoe.diagraph.diagraph.DNode;
import org.isoe.diagraph.diastyle.DBaseStyle;
import org.isoe.diagraph.diastyle.DEdgeStyle;
import org.isoe.diagraph.diastyle.DNestingEdgeStyle;
import org.isoe.diagraph.diastyle.DNodeStyle;
import org.isoe.diagraph.diastyle.DShape;
import org.isoe.diagraph.diastyle.DStyle;
import org.isoe.diagraph.diastyle.DStyleBridge;


/**
 *
 * @author pfister
 *
 */
public interface IStyleHandler {
	String getOrientation(DEdge dEdge);
	int getArrowSize(DEdge dEdge);

	DStyleBridge getStyleBridge2(DGraphElement el);
	DBaseStyle getGraphStyle(DGraphElement dnod);
	DBaseStyle getGraphStyle(String graphElementName);

	DNodeStyle getNodeStyle(DNode dnod);
	boolean isListLayout(String eModelElementName);
	List<DNode> getAllNodes();
	List<DGraphElement> getAllElements();
	DCompartmentEdge getCompartment(String eModelElementName);
	DStyleBridge getStyleBridge(String graphElementName);
	DStyleBridge getStylBridge(DNode dnod);
	List<DNestedEdge> getContainments();
	List<DCompartmentEdge> getCompartments();
	DNode findNode(String nodeName);
	DCompartmentEdge getNodeCompartment(String nodeName);
	void setGenericDefaultValues(DBaseStyle result);
	void setSpecificDefaultValues(DNodeStyle result);
	void setSpecificDefaultValues(DEdgeStyle result);
	void setSpecificDefaultValues(DNestingEdgeStyle result);
	void setStyle(DStyle dStyle);


	String getPolygon(DGraphElement graphElement, Object caller);
	DShape getNodeType(DGraphElement graphElement, Object caller);
	int[] getSize(DGraphElement graphElement, Object caller);
	String getFigure(DGraphElement element, Object caller);
	String getArrowType(DEdge dEdge);

	String[] getIcons(DGraphElement graphElement, int imageId,Object caller);
	String getIcon(DGraphElement element, Object caller);

	//DBaseStyle getEdgeStyle(DEdge dedge);
	//String getBackgroundColor(DGraphElement dGraphElement, Object caller);

	DBaseStyle getEdgeStyle(String edgeName);
	String getBackgroundColor(String compartmentName, Object caller);


}
