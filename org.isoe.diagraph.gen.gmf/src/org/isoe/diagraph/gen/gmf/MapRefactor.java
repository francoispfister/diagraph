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
public class MapRefactor {

	private static final boolean REFACTOR = true;
	private static final boolean LOG = DParams.MapRefactor_LOG;

	private static final String SPACES = "                                                                                ";
	private static final boolean LOG_COMPARTMENT = true;

	public static void doRefactorMapping_(NodeMapping nodeMapping, int depth) {
		String sp = "";
		if (depth > 0)
			sp = SPACES.substring(0, 3 * depth);
		if (nodeMapping == null) {
			clog(sp + "--logNodeMapping for Null");
			return;
		}
		if (REFACTOR) // FP130124
			clog(sp + "--logNodeMapping for "
					+ nodeMapping.getDiagramNode().getName() + "--");
		String nodeName = null;
		if (nodeMapping.getDiagramNode() != null)
			nodeName = nodeMapping.getDiagramNode().getName();
		else
			nodeName = Integer.toString(nodeMapping.hashCode());
		if (nodeMapping.eContainer() instanceof TopNodeReference) {
			if (REFACTOR) {
				clog(sp + nodeName + " TopNodeReference:"
						+ nodeMapping.eContainer().getClass().getSimpleName()
						+ "-" + nodeMapping.eContainer().hashCode());
				clog(sp + nodeName + " NodeMapping Node");
			}
			TopNodeReference topNodeReference = (TopNodeReference) nodeMapping
					.eContainer();
			EReference containmentFeature = topNodeReference
					.getContainmentFeature();
			if (REFACTOR)
				clog(sp + nodeName + " EReference ContainmentFeature:"
						+ ((EClass) containmentFeature.eContainer()).getName()
						+ "." + containmentFeature.getName());
		} else {
			if (REFACTOR) {
				clog(sp + nodeName + " ChildReference:"
						+ nodeMapping.eContainer().getClass().getSimpleName()
						+ "-" + nodeMapping.eContainer().hashCode());
				clog(sp + nodeName + " NodeMapping Node");
			}
		}
		EClass eClass = nodeMapping.getDomainMetaElement();
		if (REFACTOR)
			clog(sp + nodeName + " DomainMetaElement:" + eClass.getName());
		for (LabelMapping labelMapping : nodeMapping.getLabelMappings())
			logLabelMapping(sp + nodeName + " labelMapping", labelMapping,
					depth);
		for (CompartmentMapping compartmentMapping : nodeMapping
				.getCompartments())
			logCompartmentMapping_(sp + nodeName + " compartmentMapping",
					compartmentMapping, depth);
		for (CanvasMapping canvasMapping : nodeMapping.getRelatedDiagrams())
			clog(sp + nodeName + " relatedDiagram for:"
					+ canvasMapping.getDomainMetaElement().getName());
	}



	public static void doRefactorTopNodeMapping_(NodeMapping nodeMapping,
			int depth) {
		doRefactorMapping_(nodeMapping, depth);
		for (ChildReference child : nodeMapping.getChildren())
			doRefactorMapping_(child.getOwnedChild(), ++depth);
	}

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
			clog(sp + descr + " figure:" + labelFigure.getClass().getName()
					+ "-" + labelFigure.hashCode());
	}





	public static void logCompartmentMapping_(String descr,
			CompartmentMapping compartmentMapping, int depth) {
		String sp = "";
		if (depth > 0)
			sp = SPACES.substring(0, 3 * depth);

		Compartment compartment_ = null;
		if (compartmentMapping.getCompartment() != null) {
			compartment_ = compartmentMapping.getCompartment();
			clogc(sp + "--logCompartmentMapping for "
					+ compartmentMapping.getCompartment().getName() + "--");
		} else
			clogc(sp + "--logCompartmentMapping for "
					+ "mapping without compartment" + "--");
		String compartmentName_ = compartment_ == null ? "no compartment"
				: compartment_.getName();
		clogc(sp + descr + " compartmentName:" + compartmentName_);
		if (compartmentMapping.eContainingFeature() != null) {
			EReference contn = (EReference) compartmentMapping
					.eContainingFeature();
			if (contn != null)
				clogc(sp + descr + " containingFeature:"
						+ contn.getEType().getName() + " " + contn.getName());
			else
				clogc(sp + descr + " containingFeature: null (1)");
		} else
			clogc(sp + descr + " containingFeature: null (2)");

		if (compartment_ != null) {
			ChildAccess compartmentAccessor = compartment_.getAccessor();
			if (compartmentAccessor != null) {
				Figure compartmentFigure = compartmentAccessor.getFigure();
				clogc(sp
						+ descr
						+ " childAccess.owner-FigureDescriptor:"
						+ (compartmentAccessor.getOwner() != null ? compartmentAccessor
								.getOwner().getName() : "none "));// .getName());
				if (compartmentFigure instanceof Shape) {
					Shape compartmentShape = (Shape) compartmentFigure;
					String compartmentShapeName = compartmentShape.getName();
					clogc(sp + descr + " compartmentShapeName:"
							+ compartmentShapeName);
				}
				EObject compartmentFigureContainer = compartmentFigure
						.eContainer();
				if (compartmentFigureContainer != null)
					clogc(sp
							+ descr
							+ " compartmentFigureContainer:"
							+ compartmentFigureContainer.getClass()
									.getSimpleName());
			}
		}
	}

	private static void clog(String mesg) {
		if (LOG)
			;// clog(mesg);

	}

	private static void clogc(String mesg) {
		if (LOG && LOG_COMPARTMENT)
			System.out.println(mesg);

	}

	public static void logLabelMapping(String descr, LabelMapping labelMapping,
			int depth) {
		String sp = "";
		if (depth > 0)
			sp = SPACES.substring(0, 3 * depth);
		clog(sp + "--logLabelMapping for "
				+ labelMapping.getDiagramLabel().getName() + "--");
		DiagramLabel diagramLabel = labelMapping.getDiagramLabel();
		String diagramLabelName = diagramLabel.getName();
		if (REFACTOR)
			clog(sp + descr + " labelMapping.diagramLabel:" + diagramLabelName);
		if (labelMapping instanceof FeatureLabelMapping) {
			if (REFACTOR)
				clog(sp + descr + " FeatureLabelMapping");
			FeatureLabelMapping labelFeatureMapping = (FeatureLabelMapping) labelMapping;
			EList<EAttribute> labelEAttributes = labelFeatureMapping
					.getFeatures();
			if (REFACTOR)
				for (EAttribute labelEAttribute : labelEAttributes)
					clog(sp
							+ descr
							+ " EAttribute: "
							+ ((EClass) (labelEAttribute.eContainer()))
									.getName() + "."
							+ labelEAttribute.getName());
		}
		ChildAccess diagramChildAccess = diagramLabel.getAccessor();
		if (diagramChildAccess != null) {
			Figure diagramChildFigure = diagramChildAccess.getFigure();
			if (REFACTOR)
				logFigure_("         " + descr + " diagramChild",
						diagramChildFigure, depth);
		}

		FigureDescriptor figureDescriptor = diagramLabel.getFigure();
		Figure labelFigure = figureDescriptor.getActualFigure();
		if (REFACTOR)
			logFigure_(sp + descr + " labelFigure", labelFigure, depth);

		ChildAccess labelContentPane = diagramLabel.getContentPane();
		if (labelContentPane != null) {
			Figure smartyContentPaneFigure = labelContentPane.getFigure();
			if (REFACTOR)
				logFigure_("         " + descr + " labelContentPane",
						smartyContentPaneFigure, depth);
		}
	}

	public static void logLinkMapping(LinkMapping linkMapping) {
		if (REFACTOR) {
			clog("--logLinkMapping");
			clog("          name:" + linkMapping.getDiagramLink().getName());
			clog("   containment:"
					+ linkMapping.eContainmentFeature().getName());
			clog("        source:"
					+ (linkMapping.getSourceMetaFeature() != null ? linkMapping
							.getSourceMetaFeature().getName() : " - "));
			clog("          link:" + linkMapping.getLinkMetaFeature().getName());
			clog("          tool:" + linkMapping.getTool().getTitle());
		}
		clog(linkMapping.getLabelMappings().size() > 0 ? "label mappings:" : "");
		for (LabelMapping labelMapping : linkMapping.getLabelMappings())
			logLabelMapping("    ", labelMapping, 0);
	}

	public static void logCompartments(DiaNode node) {
		clog("--logContainments for " + node.getName() + "--");
		for (IDiaContainment c : node.getContainments()) {
			clog("Containments:" + c.getName());
		}
	}

	public static void logNode_(DiaNode node) {
		clog("--logNode for " + node.getName() + "--");
		for (IDiaContainedElement supel : node.getSuperElements())
			clog("SuperElement:" + supel.getName());
		if (node.getInheritedContainmentsBase().size() == 0) // FP2611
			clog("no InheritedContainment base: for " + node.getName());
		else
			for (EModelElement inhercont : node.getInheritedContainmentsBase()) {
				assert inhercont instanceof EReference;
				clog("InheritedContainment:"
						+ ((EReference) inhercont).getName());
			}


		EModelElement contn_ = node.getContainmentReferenceBase();
		// assert contn instanceof EReference;
		if (contn_ == null) {
			clog("node.getContainmentReferenceBase == null for " + node.getName());
			if (node.getInheritedContainmentsBase().size() == 0)
				clog("node " + node.getName()
						+ " has no containment references !!!!");
		} else
			clog("ContainmentBase:" + ((EClass) contn_.eContainer()).getName() + "."
					+ ((EReference) contn_).getName());


		EModelElement contn = node.getContainmentReferenceAlt();
		// assert contn instanceof EReference;
		if (contn == null) {
			clog("node.getContainmentReferenceAlt == null for " + node.getName());
		} else
			clog("ContainmentAlt:" + ((EClass) contn.eContainer()).getName() + "."
					+ ((EReference) contn).getName());



		if (node.getContainments().size() > 0)
			clog(node.getContainments().size() + " containments");
	}

}
