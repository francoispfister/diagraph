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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.gmf.gmfgraph.ChildAccess;
import org.eclipse.gmf.gmfgraph.Compartment;
import org.eclipse.gmf.gmfgraph.DiagramLabel;
import org.eclipse.gmf.gmfgraph.Figure;
import org.eclipse.gmf.gmfgraph.FigureDescriptor;
import org.eclipse.gmf.gmfgraph.Shape;
import org.eclipse.gmf.mappings.CanvasMapping;
import org.eclipse.gmf.mappings.ChildReference;
import org.eclipse.gmf.mappings.CompartmentMapping;
import org.eclipse.gmf.mappings.FeatureLabelMapping;
import org.eclipse.gmf.mappings.LabelMapping;
import org.eclipse.gmf.mappings.LinkMapping;
import org.eclipse.gmf.mappings.NodeMapping;
import org.eclipse.gmf.mappings.TopNodeReference;
import org.isoe.diagraph.internal.m2.DiaNode;
import org.isoe.diagraph.internal.m2.api.IDiaContainedElement;
import org.isoe.diagraph.internal.m2.api.IDiaContainment;
import org.isoe.fwk.core.DParams;


/**
 *
 * @author pfister
 *
 */
public class GmfLogger {

	private static final boolean LOG = DParams.GmfLogger_LOG;

	private static final String SPACES="                                                                                                                                                                                                                                                                                                                                                                     ";

	public static void logFigure_(String descr, Figure labelFigure, int depth) {
		String sp = "";
		if (depth > 0)
			sp = SPACES.substring(0, 3 * depth);
		clog(sp + "--logFigure--");
		if (labelFigure instanceof Shape) {
			Shape labelShape = (Shape) labelFigure;
			String labelShapeName = labelShape.getName();
			clog(sp + descr + " figure:" + labelShapeName);
		} else
			clog(sp + descr + " figure:" + labelFigure.getClass().getName() + "-" + labelFigure.hashCode());
	}

	public static void logCompartmentMapping(String descr, CompartmentMapping compartmentMapping, int depth) {
		String sp = "";
		if (depth > 0)
			sp = SPACES.substring(0, 3 * depth);
		clog(sp + "--logCompartmentMapping for " + compartmentMapping.getCompartment().getName() + "--");
		Compartment compartment = compartmentMapping.getCompartment();
		String compartmentName = compartment.getName();
		clog(sp + descr + " compartmentName:" + compartmentName);
		EReference contn = (EReference) compartmentMapping.eContainingFeature();
		if (contn != null)
			clog(sp + descr + " containingFeature:" + contn.getEType().getName() + " " + contn.getName());
		else
			clog(sp + descr + " containingFeature: null");
		ChildAccess compartmentAccessor = compartment.getAccessor();
		if (compartmentAccessor != null) {
			Figure compartmentFigure = compartmentAccessor.getFigure();
			clog(sp + descr + " childAccess.owner-FigureDescriptor:"
					+ (compartmentAccessor.getOwner() != null ? compartmentAccessor.getOwner().getName() : "none "));// .getName());
			if (compartmentFigure instanceof Shape) {
				Shape compartmentShape = (Shape) compartmentFigure;
				String compartmentShapeName = compartmentShape.getName();
				clog(sp + descr + " compartmentShapeName:" + compartmentShapeName);
			}
			EObject compartmentFigureContainer = compartmentFigure.eContainer();
			if (compartmentFigureContainer != null)
				clog(sp + descr + " compartmentFigureContainer:" + compartmentFigureContainer.getClass().getSimpleName());
		}
	}

	public static void logLabelMapping(String descr, LabelMapping labelMapping, int depth) {
		String sp = "";
		if (depth > 0)
			sp = SPACES.substring(0, 3 * depth);
		clog(sp+"--logLabelMapping for " + labelMapping.getDiagramLabel().getName() + "--");
		DiagramLabel diagramLabel = labelMapping.getDiagramLabel();
		String diagramLabelName = diagramLabel.getName();
		if ( LOG)
		  clog(sp + descr + " labelMapping.diagramLabel:" + diagramLabelName);
		if (labelMapping instanceof FeatureLabelMapping) {
			if ( LOG)
			  clog(sp + descr + " FeatureLabelMapping");
			FeatureLabelMapping labelFeatureMapping = (FeatureLabelMapping) labelMapping;
			EList<EAttribute> labelEAttributes = labelFeatureMapping.getFeatures();
			if ( LOG)
			  for (EAttribute labelEAttribute : labelEAttributes)
				clog(sp + descr + " EAttribute: " + ((EClass) (labelEAttribute.eContainer())).getName() + "." + labelEAttribute.getName());
		}
		ChildAccess diagramChildAccess = diagramLabel.getAccessor();
		if (diagramChildAccess != null) {
			Figure diagramChildFigure = diagramChildAccess.getFigure();
			if ( LOG)
			  logFigure_("         " + descr + " diagramChild", diagramChildFigure, depth);
		}

		FigureDescriptor figureDescriptor = diagramLabel.getFigure();
		Figure labelFigure = figureDescriptor.getActualFigure();
		if ( LOG)
		  logFigure_(sp + descr + " labelFigure", labelFigure, depth);

		ChildAccess labelContentPane = diagramLabel.getContentPane();
		if (labelContentPane != null) {
			Figure smartyContentPaneFigure = labelContentPane.getFigure();
			if ( LOG)
			  logFigure_("         " + descr + " labelContentPane", smartyContentPaneFigure, depth);
		}
	}

	public static void logLinkMapping(LinkMapping linkMapping) {
		if ( LOG){
		  clog("--logLinkMapping");
		  clog("          name:" + linkMapping.getDiagramLink().getName());
		  clog("   containment:" + linkMapping.eContainmentFeature().getName());
		  clog("        source:" + (linkMapping.getSourceMetaFeature() != null ? linkMapping.getSourceMetaFeature().getName() : " - "));
		  clog("          link:" + linkMapping.getLinkMetaFeature().getName());
		  clog("          tool:" + linkMapping.getTool().getTitle());
		}
		clog(linkMapping.getLabelMappings().size() > 0 ? "label mappings:" : "");
		for (LabelMapping labelMapping : linkMapping.getLabelMappings())
			logLabelMapping("    ", labelMapping, 0);
	}

	public static void logNodeMapping(NodeMapping nodeMapping, int depth) {
		String sp="";
		if (depth > 0)
			sp = SPACES.substring(0, 3 * depth);
		if (nodeMapping == null) {
			clog(sp + "--logNodeMapping for Null");
			return;
		}
		if ( LOG) //FP130124
		  clog(sp + "--logNodeMapping for " + nodeMapping.getDiagramNode().getName() + "--");
		String nodeName = null;
		if (nodeMapping.getDiagramNode() != null)
			nodeName = nodeMapping.getDiagramNode().getName();
		else
			nodeName = Integer.toString(nodeMapping.hashCode());
		if (nodeMapping.eContainer() instanceof TopNodeReference) {
			if ( LOG){
			  clog(sp + nodeName + " TopNodeReference:" + nodeMapping.eContainer().getClass().getSimpleName() + "-"
					+ nodeMapping.eContainer().hashCode());
			  clog(sp + nodeName + " NodeMapping Node");
			}
			TopNodeReference topNodeReference = (TopNodeReference) nodeMapping.eContainer();
			EReference containmentFeature = topNodeReference.getContainmentFeature();
			if ( LOG)
			  clog(sp + nodeName + " EReference ContainmentFeature:" + ((EClass) containmentFeature.eContainer()).getName() + "."
					+ containmentFeature.getName());
		} else {
			if ( LOG){
			  clog(sp + nodeName + " ChildReference:" + nodeMapping.eContainer().getClass().getSimpleName() + "-"
					+ nodeMapping.eContainer().hashCode());
			  clog(sp + nodeName + " NodeMapping Node");
			}
		}
		EClass eClass = nodeMapping.getDomainMetaElement();
		if ( LOG)
		  clog(sp + nodeName + " DomainMetaElement:" + eClass.getName());
		for (LabelMapping labelMapping : nodeMapping.getLabelMappings())
			logLabelMapping(sp + nodeName + " labelMapping", labelMapping, depth);
		for (CompartmentMapping compartmentMapping : nodeMapping.getCompartments())
			logCompartmentMapping(sp + nodeName + " compartmentMapping", compartmentMapping, depth);
		for (CanvasMapping canvasMapping : nodeMapping.getRelatedDiagrams())
			clog(sp + nodeName + " relatedDiagram for:" + canvasMapping.getDomainMetaElement().getName());
		for (ChildReference child : nodeMapping.getChildren())
			logNodeMapping(child.getOwnedChild(), ++depth);
	}


	public static void logCompartments(DiaNode node) {
		clog("--logContainments for " + node.getName() + "--");
		for (IDiaContainment c : node.getContainments()) {
			clog("Containments:" + c.getName());
		}
	}

	public static void logNode(DiaNode node) {
		clog("--logNode for " + node.getName() + "--");
		for (IDiaContainedElement supel : node.getSuperElements())
			clog("SuperElement:" + supel.getName());
		if (node.getInheritedContainmentsBase().size()==0)  //FP2611
			clog("no InheritedContainment base: for " + node.getName());
		else
		for (EModelElement inhercont : node.getInheritedContainmentsBase()) {
			assert inhercont instanceof EReference;
			clog("InheritedContainment:" + ((EReference) inhercont).getName());
		}


		EReference contn_ = node.getContainmentReferenceBase();
		//assert contn instanceof EReference;
		if (contn_ == null){
			clog("node.getContainmentReferenceBase == null for " + node.getName());
			if (node.getInheritedContainmentsBase().size()==0)
				clog("node "+ node.getName()+" has no containment references !!!!");
		}else
		    clog("Containment base:" +((EClass)contn_.eContainer()).getName()+"."+ ((EReference) contn_).getName());


		EReference contalt = node.getContainmentReferenceAlt();
		//assert contn instanceof EReference;
		if (contalt == null){
			clog("node.getContainmentReferenceAlt == null for " + node.getName());

		}else
		    clog("Containment alt:" +((EClass)contalt.eContainer()).getName()+"."+ ((EReference) contalt).getName());



		if (node.getContainments().size() > 0)
			clog(node.getContainments().size() + " containments");
	}

	private static void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}


}
