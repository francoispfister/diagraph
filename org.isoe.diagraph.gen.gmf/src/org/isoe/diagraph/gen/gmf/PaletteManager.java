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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.gmf.tooldef.CreationTool;
import org.isoe.diagraph.diagraph.DOwnedEdge;
import org.isoe.diagraph.diagraph.DEdge;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.diagraph.DGraphElement;
import org.isoe.diagraph.diagraph.DLabeledEdge;
import org.isoe.diagraph.diagraph.DNavigationEdge;
import org.isoe.diagraph.diagraph.DNestedEdge;
import org.isoe.diagraph.diagraph.DNode;
import org.isoe.diagraph.diagraph.DReference;
import org.isoe.diagraph.diagraph.DSimpleEdge;
import org.isoe.diagraph.diastyle.DBaseStyle;
import org.isoe.diagraph.diastyle.DNodeEdgeStyle;
import org.isoe.diagraph.diastyle.helpers.IStyleHandler;
import org.isoe.diagraph.diastyle.helpers.StyleHandler;
import org.isoe.diagraph.gen.gmf.util.DGenHelpers;
import org.isoe.fwk.core.DParams;
import org.isoe.diagraph.runner.IDiagraphRunner;

/**
 *
 * @author pfister
 *
 */
public class PaletteManager implements IPaletteManager {

	private static final int TOOL_NAME = 0;
	private static final int TOOL_DESCRIPTION = 1;
	private static final int TOOL_IMAGE_BUNDLE = 2;
	private static final boolean LOG = DParams.PaletteManager_LOG;
	private IStyleHandler styleHandler;
	private IDiagraphRunner runner;
	private String iconExtension;
	private String pluginId;
	private List<TooledGraphElement> tgels;
	private List<String[]> params = new ArrayList<String[]>();

	public int getNextNodeId_(String name) {
		return runner.getNextNodeId_(name);
	}

	public int getNextEdgeId_(String name) {
		return runner.getNextEdgeId_(name);
	}

	/*
	 * public void clearIdGenerator(){ runner.clearIdGenerator(); }
	 */

	@Override
	public void initTool() {
		runner.clearIdGenerator();
	}

	public PaletteManager(IDiagraphRunner runner, IStyleHandler styleHandler,
			String pluginId, String iconExtension) {
		super();
		this.styleHandler = styleHandler;
		this.iconExtension = iconExtension;
		this.pluginId = pluginId;
		this.runner = runner;
		readMetadata();
	}

	private int getIconId_(DGraphElement graphElement) {
		DBaseStyle dGraphStyle = styleHandler.getGraphStyle(graphElement);
		if (!(dGraphStyle instanceof DNodeEdgeStyle))
			throw new RuntimeException("should not happen in getIconId");
		DNodeEdgeStyle ns = (DNodeEdgeStyle) dGraphStyle;
		if (ns.getIcon().equals(StyleHandler.DEFAULT_ICON)) { // FP121002
			if (graphElement instanceof DNode)
				return getNextNodeId_(graphElement.getName());
			if (graphElement instanceof DEdge)
				return getNextEdgeId_(graphElement.getName());
		}
		return -1;
	}

	public boolean iconExists(String srcplugin, String sourcefile) {
		if (sourcefile.startsWith("/"))
			sourcefile = sourcefile.substring(1);
		File sourceAbsoluteFile = new File(CommonPlugin.resolve(
				URI.createPlatformResourceURI(srcplugin + "/" + sourcefile,
						true)).toFileString());
		return sourceAbsoluteFile.exists();
	}

	@Override
	public CreationTool registerTool(DGraphElement el, String description) {
		CreationTool result = null;
		TooledGraphElement tge = findTooledGraphElement(el);
		if (tge == null)
			result = addTool(el, description);
		return result;
	}

	@Override
	public List<CreationTool> getExtraTools(String layer) {
		return null;
	}

	@Override
	public String getExtraToolsName() {
		return null;
	}

	private void readMetadata() {
		String paletteData = runner.getMetadata("palette");
		try {
			String[] rows = paletteData.split(";");
			for (String row : rows) {
				String[] line = row.split("=");
				params.add(line);
				clog(line[0] + "=" + line[1]);
			}
		} catch (Exception e) {
			if (LOG)
				clog("error in readMetadata"); //FP150523
		}
	}

	private String getFirstLabel_nu(DGraphElement el) { // FP140121
		String label = null;
		if (el instanceof DLabeledEdge) { // FP130121
			DLabeledEdge DLabeledEdge = (DLabeledEdge) el;
			if (!DLabeledEdge.getLabls().isEmpty()) {
				label = DLabeledEdge.getLabls().get(0);
				if (label != null)
					return label;
			}
		} else if (el instanceof DNode) {
			DNode dNode = (DNode) el;
			if (!dNode.getLabls().isEmpty()) {
				label = dNode.getLabls().get(0);
				if (label != null)
					return label;
			}
		}
		return null;
	}

	private CreationTool addTool(DGraphElement el, String description) {
		if (LOG)
			clog("addTool " + el.getName() + " (" + description + ")");
		CreationTool ct = null;
		String[] icons = null;
		if (iconExists(pluginId, "icons/" + el.getName() + "."
				+ org.isoe.fwk.core.DParams.ICON_TYPE)) {
			icons = new String[3];
			icons[0] = pluginId;
			icons[1] = el.getName();
		} // FP121002
		else {
			int idel = getIconId_(el);
			String id = (el instanceof DNode) ? "node" : "edge";
			id = id + idel;

			icons = styleHandler.getIcons(el, idel, this); // FP121110 //
															// FP121002
			copyIcon(pluginId, id, "icons/" + el.getName() + "."
					+ org.isoe.fwk.core.DParams.ICON_TYPE);// org.isoe.sample.research
															// 1
															// icons/PublicationProcess.gif

		}
		String toolName = el.getName();
		ct = makeCreationTool(icons, toolName, description); // FP121031www1
		tgels.add(new TooledGraphElement(ct, el));
		return ct;
	}

	private void copyIcon(String pluginId, String iconId_, String iconPath) {
		if (LOG)
			clog("to do copyIcon:" + iconId_ + "."
					+ org.isoe.fwk.core.DParams.ICON_TYPE + "  - " + pluginId
					+ "/" + iconPath); // FP131204 xx

	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	private TooledGraphElement findTooledGraphElement(DGraphElement el) {
		for (TooledGraphElement tge : tgels)
			if (tge.getGraphElement() == el)
				return tge;
		return null;
	}

	private CreationTool makeCreationTool(String[] icons, String toolName,
			String description) {
		if (StyleHandler.HANDLE_GMF_PALETTE && icons == null)
			throw new RuntimeException(
					"should not happen in makeCreationTool !!!");
		if (icons != null)
			return DGenHelpers.createCreationToolWithBundleImage(toolName,
					description, icons[TOOL_NAME], icons[TOOL_DESCRIPTION],
					icons[TOOL_IMAGE_BUNDLE], iconExtension);
		else
			return DGenHelpers.createCreationToolSimple(toolName, description);

	}

	private boolean isInCompartment(DNode node) {// FP120330
		return getContainer(node) != null;
	}

	private DNode getContainer(DNode node) {
		for (TooledGraphElement tge : tgels)
			if (tge.getGraphElement() instanceof DNode)
				for (DEdge dEdge : getEdges(((DNode) tge.getGraphElement())))
					if ((dEdge instanceof DNestedEdge)
							&& node == dEdge.getTarget())
						return ((DNestedEdge) dEdge).getSource().getNode(); // FP150423d
		return null;
	}

	/*
	 * private List<DNode> getNodes(DGraph graph){ //FP140518aa List<DNode>
	 * nodes = new ArrayList<DNode>(); for (DGraphElement element :
	 * graph.getElements()) if(element instanceof DNode) nodes.add((DNode)
	 * element); return nodes; }
	 */

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

	@Override
	public CreationTool findTool(DGraphElement el) {
		for (TooledGraphElement tge : tgels)
			if (tge.getGraphElement() == el)
				return tge.getCreationTool();
		return null;
	}

	@Override
	public List<CreationTool> getTopNodeTools(String layer) {
		List<CreationTool> result = new ArrayList<CreationTool>();
		for (TooledGraphElement tge : tgels)
			if (tge.getGraphElement() instanceof DNode
					&& !isInCompartment((DNode) tge.getGraphElement()))
				result.add(tge.getCreationTool());
		return result;
	}

	@Override
	public List<CreationTool> getCompartmentedNodeTools(String layer) {
		List<CreationTool> result = new ArrayList<CreationTool>();
		for (TooledGraphElement tge : tgels)
			if (tge.getGraphElement() instanceof DNode
					&& isInCompartment((DNode) tge.getGraphElement()))
				result.add(tge.getCreationTool());
		return result;
	}

	@Override
	public List<CreationTool> getLinkTools(String layer) {
		List<CreationTool> result = new ArrayList<CreationTool>();
		for (TooledGraphElement tge : tgels)
			if (tge.getGraphElement() instanceof DLabeledEdge)
				result.add(tge.getCreationTool());
		return result;
	}

	@Override
	public List<CreationTool> getReferenceTools(String layer) {
		List<CreationTool> result = new ArrayList<CreationTool>();
		for (TooledGraphElement tge : tgels)
			if (tge.getGraphElement() instanceof DReference)
				result.add(tge.getCreationTool());
		return result;
	}

	@Override
	public List<CreationTool> getUnsortedTools(String layer) {
		List<CreationTool> result = new ArrayList<CreationTool>();
		for (TooledGraphElement tge : tgels)
			result.add(tge.getCreationTool());
		return result;

	}

	@Override
	public void setElements(List<TooledGraphElement> els) {
		this.tgels = els;
	}

}
