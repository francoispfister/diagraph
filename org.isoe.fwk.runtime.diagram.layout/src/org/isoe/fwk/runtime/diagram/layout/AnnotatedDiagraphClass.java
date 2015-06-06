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
package org.isoe.fwk.runtime.diagram.layout;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Node;

/**
 * 
 * @author pfister
 * 
 */
public class AnnotatedDiagraphClass {

	private static final boolean LOG_LAYOUT = false; //FP121124j
	private static final boolean LOG = false;
	private EClass eClass;
	private final Bounds eClassBounds;
	private EAnnotation eAnnotation;
	private Bounds eAnnotationBounds;
	private Node annotationNode; // FP120528
	private Node semanticNode;
	private int classHeight;
	private String view;
	private boolean currentView;

	public void setCurrentView(boolean currentView) {
		this.currentView = currentView;
	}

	public boolean isCurrentView() {
		return currentView;
	}

	public String getView() {
		return view;
	}

	public AnnotatedDiagraphClass(EClass eclass, Bounds eclassBounds,
			int classHeight, Node semanticNode, String view,
			boolean isCurrentView) {// Dimension eClassDimemsion
		this.eClass = eclass;
		this.eClassBounds = eclassBounds;
		this.classHeight = classHeight;
		// this.classHeight = eclassBounds.getHeight();
		this.semanticNode = semanticNode;
		this.view = view;
		this.currentView = isCurrentView;
	}

	public String toString() {
		String result = " view=" + view + " ";
		String eclassbounds = "x: " + eClassBounds.getX() + " y: "
				+ eClassBounds.getY() + " w: " + eClassBounds.getWidth()
				+ " h: " + eClassBounds.getHeight();
		String eclassDim = "";// "w: " + eClassDimension.width + " h:" +
								// eClassDimension.height;

		String eanotbounds = null;
		if (eAnnotationBounds != null)
			eanotbounds = "x: " + eAnnotationBounds.getX() + " y: "
					+ eAnnotationBounds.getY() + " w: "
					+ eAnnotationBounds.getWidth() + " h: "
					+ eAnnotationBounds.getHeight();

		String eanotDim = "";

		result += eClass.getName()
				+ (currentView ? " current" : " ** not current")
				+ "->"
				+ (eclassbounds == null ? " no bound " : eclassbounds)
				+ " - "
				+ eclassDim
				+ " -- "
				+ (eAnnotation == null ? " not annot " : eAnnotation
						.getSource()) + "->"
				+ (eanotbounds == null ? " no bound " : eanotbounds) + " - "
				+ eanotDim;

		return result;
	}

	public EAnnotation getEAnnotation() {
		return eAnnotation;
	}

	public void redimClass(Bounds bounds) {
		bounds.setX(eClassBounds.getX());
		bounds.setY(eClassBounds.getY());
		bounds.setWidth(Math.max(eClassBounds.getWidth(),
				eAnnotationBounds.getWidth()));
	}

	public void hideBehindClass(Bounds bounds) {
		bounds.setX(eClassBounds.getX());
		bounds.setY(eClassBounds.getY());
		bounds.setWidth(eClassBounds.getWidth());
		bounds.setHeight(classHeight);
	}

	public void redimAnnotation(Node node, Bounds anotbounds, int layer, boolean all) {
		if (node.getElement() instanceof EAnnotation) {
			//EAnnotation eAnnotation_ = (org.eclipse.emf.ecore.EAnnotation) node.getElement();
			int offset=0;
			if (all)
				offset = layer * 8;
			anotbounds.setX(offset + eClassBounds.getX() + 4);
			anotbounds.setY(offset + eClassBounds.getY() + classHeight - 37);
			anotbounds.setWidth(Math.max(eClassBounds.getWidth(),
					eAnnotationBounds.getWidth()));
		}

	}

	public void setAnnotationNode(Node node) {
		this.annotationNode = node;
	}

	public Node getAnnotationNode() {
		return annotationNode;
	}

	public Node getSemanticNode() {
		return semanticNode;
	}

	public void setEClass(EClass eClass) {
		this.eClass = eClass;
	}

	public EClass getEClass() {
		return eClass;
	}

	public void setEAnnotation(EAnnotation eAnnotation) {
		this.eAnnotation = eAnnotation;
	}

	public void setEAnnotationBounds(Bounds eAnnotationBounds) {
		this.eAnnotationBounds = eAnnotationBounds;
	}

}
