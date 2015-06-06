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
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.gmf.codegen.gmfgen.ToolEntry;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.gmf.runtime.gef.ui.internal.palette.PaletteToolbar;
import org.eclipse.gmf.tooldef.AbstractTool;
import org.eclipse.gmf.tooldef.CreationTool;
import org.eclipse.gmf.tooldef.GMFToolFactory;
import org.eclipse.gmf.tooldef.Palette;
import org.eclipse.gmf.tooldef.PaletteSeparator;
import org.eclipse.gmf.tooldef.ToolGroup;
import org.eclipse.gmf.tooldef.ToolRegistry;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.isoe.diagraph.diagraph.DNestedEdge;
import org.isoe.diagraph.diagraph.DOwnedEdge;
import org.isoe.diagraph.diagraph.DEdge;
import org.isoe.diagraph.diagraph.DLabeledEdge;
import org.isoe.diagraph.diagraph.DNavigationEdge;
import org.isoe.diagraph.diagraph.DNode;
import org.isoe.diagraph.diagraph.DReference;
import org.isoe.diagraph.diagraph.DSimpleEdge;
import org.isoe.diagraph.diastyle.helpers.IErrorReporter;
import org.isoe.diagraph.diastyle.helpers.StyleHandler;
import org.isoe.diagraph.gen.gmf.util.DGenHelpers;
import org.isoe.diagraph.internal.api.DPhase;
import org.isoe.diagraph.internal.api.IDiaSyntaxElement;
import org.isoe.diagraph.internal.m2.DiaNode;
import org.isoe.diagraph.internal.m2.parser.DAnnotationParser;
import org.isoe.diagraph.internal.m2.parser.DStatement;
import org.isoe.diagraph.internal.m2.parser.DStatement.DCommand_;
import org.isoe.diagraph.parser.api.IDiagraphParser;
import org.isoe.fwk.core.DParams;
import org.isoe.diagraph.runner.IDiagraphRunner;

/**
 * Creates tooling definition model from syntax model.
 *
 * @author fpfister
 */
public class GmfToolGenerator extends GmfBaseGenerator {// implements
														// BaseGenerator

	private static final boolean LOG = DParams.GmfToolGenerator_LOG;
	protected ToolRegistry toolRegistry;
	protected static final String DEFAULT_TOOL_GROUP_NAME = "default";
	protected static final String DEFAULT_PALETTE_TITLE = "default";
	private List<TooledGraphElement> toAdd_;// = new
											// ArrayList<TooledGraphElement>();
	private IPaletteManager paletteManager;
	private IDiagraphRunner runner;
	private ToolGroup tgroup;

	protected ToolRegistry createToolRegistryAndGroup(String toolgroupName,
			String paletteTitle) {
		toolRegistry = DGenHelpers.createToolRegistryAndPalette(paletteTitle);
		ToolGroup defgroup = DGenHelpers.createToolGroup(toolgroupName);
		toolRegistry.getPalette().getTools().add(defgroup);
		return toolRegistry;
	}

	protected void clog(String mesg) {
		if (LOG || DParams.Parser_15_LOG)
			System.out.println(mesg);
	}

	@Override
	public void processCanvas(DStatement statement) {
		if (LOG)
			clog("TPC " + this.getClass().getSimpleName() + " processCanvas - "
					+ statement);
		// createToolRegistryAndGroup(tempor1,tempor2);
		createToolRegistryAndGroup(DEFAULT_TOOL_GROUP_NAME,
				DEFAULT_PALETTE_TITLE);
	}

	/***********/

	@Override
	public Object processNodeTop(DStatement statement,DCommand_ force) {
		//if (LOG)
		//	clog("TPN " + this.getClass().getSimpleName() + " processNode - "
		//			+ statement);

		if (LOG)
			clog("GMTGPN "+(force!=null?" FORCE ":"") + this.getClass().getSimpleName()
					+ " processNode - " + statement);

		return createNodeTool(findDNode(statement.getDiagramElementName())); // FP121002//FP120111
	}

	@Override
	public Object processLink(DStatement statement, boolean oriented) { // FP120621a
		if (LOG)
			clog("TPL " + this.getClass().getSimpleName() + " processLink - "
					+ statement);
		return createLinkTool_(findDLabeledEdge(statement
				.getDiagramElementName()));
	}

	@Override
	public Object processReference_(IDiaSyntaxElement elem, boolean oriented,
			boolean unique) {// FP120621a
		if (LOG)
			clog("TPR " + this.getClass().getSimpleName()
					+ " processReference - " + elem.getName());
		return createReferenceTool(findDReference(elem.getName()), unique);
	}

	@Override
	public void initIdGenerator() {
		paletteManager.initTool();
	}

	/***********/

	public CreationTool createLinkTool_(DLabeledEdge lnk) {
		ToolGroup group = DGenHelpers.findToolGroup(toolRegistry,
				DEFAULT_TOOL_GROUP_NAME); // FP121101
		// String description = lnk.getSource().getNode().getName() + "->"
		String description = lnk.getSource().getName() + "->"
				+ lnk.getTarget().getName(); // FP150423c
		return paletteManager.registerTool(lnk, description);
	}

	public CreationTool createNodeTool(DNode node) { // FP120330
		ToolGroup group = DGenHelpers.findToolGroup(toolRegistry,
				DEFAULT_TOOL_GROUP_NAME);// FP121101
		String description = "Model Element: " + node.getName();
		return paletteManager.registerTool(node, description);
	}

	private DNode getSource(DEdge edg) {
		if (edg instanceof DNestedEdge) // FP150423b
			return ((DNestedEdge) edg).getSource().getNode();
		else if (edg instanceof DSimpleEdge)
			return ((DSimpleEdge) edg).getSource();
		else
			throw new RuntimeException("should not happen in getSource");
	}

	public CreationTool createReferenceTool(DReference dref, boolean unique) {
		// boolean result = false;
		CreationTool rct = null;
		ToolGroup group_ = DGenHelpers.findToolGroup(toolRegistry,
				DEFAULT_TOOL_GROUP_NAME);// //FP121101
		String reftitle = dref.getName();
		AbstractTool found = findTool(group_, reftitle);
		if (found != null)
			throw new RuntimeException(
					"in createReferenceTool: should not happen !!!");
		if (!unique || found == null) {
			String description = getSource(dref).getName() + "->"
					+ dref.getTarget().getName();
			rct = paletteManager.registerTool(dref, description);
			// result = true;
		}
		return rct;
	}

	@Override
	public boolean processContainment(DStatement statement) {
		// nothing
		return false;// FP150331a307
	}

	@Override
	public void processLabels(IDiaSyntaxElement element) {
		// nothing
	}

	public ToolRegistry getToolRegistry() {
		if (toolRegistry == null) {
			if (LOG)
				clog("---- Tool idgen reinitializing ----");
			initTool();// FP131205xxxzzz
			processDomainModel(DPhase.TOOL);
			validateDomainModel(DPhase.TOOL);
			tgroup = DGenHelpers.findToolGroup(toolRegistry,
					DEFAULT_TOOL_GROUP_NAME);
			if (StyleHandler.HANDLE_GMF_PALETTE)
				addSortedToolsToPalette(view);
			else
				addUnsortedSortedToolsToPalette(view);
			if (LOG)
				clog("---- Tool idgen reinitialized ----");
		}
		return toolRegistry;
	}

	@Override
	public void initTool() {
		paletteManager.initTool();
	}

	@Override
	public void executeCommands(DPhase phase) {

		if (DParams.Parser_15_LOG) {
			clog("LOGSTA tools-canvas");
			logStatements(DCommand_.TOOL_CREATE);

			clog("LOGSTA tools-nodes");
			logStatements(DCommand_.TOOL_NODE);

			clog("LOGSTA tools-links");
			logStatements(DCommand_.TOOL_LINK);
		}

		for (DStatement statement : annotationParser.getDStatements()) {
			if (statement.getCommand() == DCommand_.TOOL_CREATE){
				processCanvas(statement);
				if (canvasComposite_nu)
				   processNodeTop(statement,DCommand_.TOOL_NODE);//FP150524composite
			}
		}
		for (DStatement statement : annotationParser.getDStatements()) {
			if (statement.getCommand() == DCommand_.TOOL_NODE)
				processNodeTop(statement,null);
		}

		for (DStatement statement : annotationParser.getDStatements()) {
			if (statement.getCommand() == DCommand_.TOOL_LINK)
				processLink(statement, true); // FP120621a //FP131205xxx
		}
	}

	public EObject getRoot() {
		return getToolRegistry();
	}

	public Palette getPalette() {
		return getToolRegistry().getPalette();
	}

	List<String[]> params = new ArrayList<String[]>();

	public GmfToolGenerator(IErrorReporter logger, String layer,
			IPaletteManager paletteManager, String pluginId, String filePath,
			EPackage ePackage, DAnnotationParser annotationParser,
			IDiagraphParser diagraphAnnotationUtils, IDiagraphRunner runner,
			String iconExtension,boolean canvasComposite) {
		super(logger, layer, pluginId, filePath, ePackage, annotationParser,
				diagraphAnnotationUtils,canvasComposite);

		String paletteData = runner.getMetadata("palette");

		try {

			String[] rows = paletteData.split(";");
			for (String row : rows) {
				String[] line = row.split("=");
				params.add(line);
				if (LOG)
					clog(line[0] + "=" + line[1]);
			}
		} catch (Exception e) {
			throw new RuntimeException("error in GmfToolGenerator");
		}

		this.runner = runner;
		this.toAdd_ = new ArrayList<TooledGraphElement>();
		this.paletteManager = paletteManager;
		paletteManager.setElements(toAdd_);
	}

	private void addSeparator(ToolGroup group, String title, String description) {
		PaletteSeparator separator = GMFToolFactory.eINSTANCE
				.createPaletteSeparator();
		separator.setDescription(description);
		separator.setTitle(title);
		group.getTools().add(separator);
	}

	private void addSortedToolsToPalette(String layer) {
		addSeparator(tgroup, "Nodes", "Nodes");
		tgroup.getTools().addAll(paletteManager.getTopNodeTools(layer));
		// est ce que le tool est associé au mapping ?? //FP121008
		addSeparator(tgroup, "Compartmented nodes", "Compartmented nodes");
		tgroup.getTools().addAll(
				paletteManager.getCompartmentedNodeTools(layer));
		addSeparator(tgroup, "Links", "Links");
		tgroup.getTools().addAll(paletteManager.getLinkTools(layer));
		addSeparator(tgroup, "References", "References");
		tgroup.getTools().addAll(paletteManager.getReferenceTools(layer));
		addExtraSection();
	}

	private void addExtraSection() {
		String name = paletteManager.getExtraToolsName();
		if (name != null) {
			addSeparator(tgroup, name, name);
			tgroup.getTools().addAll(paletteManager.getExtraTools(view));
		}
	}

	private void addUnsortedSortedToolsToPalette(String layer) {
		tgroup.getTools().addAll(paletteManager.getUnsortedTools(layer));
	}

	// FP121007
	private AbstractTool findTool(ToolGroup group, String name) {
		EList<AbstractTool> tools = group.getTools();
		for (AbstractTool abstractTool : tools) {
			if (abstractTool.getTitle().equals(name))
				return abstractTool;
		}
		return null;
	}

	// FP121007
	private void removeTool(String groupname, String name) {
		ToolGroup group = DGenHelpers.findToolGroup(toolRegistry, groupname);
		EList<AbstractTool> tools = group.getTools();
		for (Iterator iterator = tools.iterator(); iterator.hasNext();) {
			AbstractTool tool = (AbstractTool) iterator.next();
			if (tool.getTitle().equals(name)) {
				iterator.remove();
				if (LOG)
					clog("tool removed: " + tool.getTitle());
				break;
			}
		}
	}

	// FP120930
	/*
	 * private void setCustomIcons_not_used(String toolName, String imageBundle,
	 * String smallIcon, String largeIcon, String extension) { ToolGroup group =
	 * DGenHelpers.findToolGroup(toolRegistry, defaultToolgroupName);
	 * AbstractTool tool = findTool_(group, toolName); BundleImage small =
	 * GMFToolFactory.eINSTANCE.createBundleImage();
	 * small.setBundle(imageBundle); small.setPath("/icons/" + smallIcon + "." +
	 * extension); tool.setSmallIcon(small); BundleImage large =
	 * GMFToolFactory.eINSTANCE.createBundleImage();
	 * large.setBundle(imageBundle); large.setPath("/icons/" + largeIcon + "." +
	 * extension); tool.setLargeIcon(large); if (LOG_ && LogConfig.LOG_GLOBAL)
	 * clog("setCustomIcons for tool " + toolName + " - icons: " + smallIcon +
	 * " - " + largeIcon); // FP120930 }
	 */

	@Override
	public void pass4(DPhase phase) {
		// nothing
	}

	// TODO checkNotMappeDLabeledEdges

	public void checkNotMappedNodes() {
		// FP0912 remove not mapped nodes from tools
		for (DiaNode diaNode : annotationParser.getInternalModel()
				.getDiaNodes()) {
			if (diaNode.mustRemoveFromTool()) {
				ToolGroup group = DGenHelpers.findToolGroup(toolRegistry,
						DEFAULT_TOOL_GROUP_NAME);
				String toolName = diaNode.getName(); // FP121007

				// if (diaNode.getLabels() != null &&
				// !diaNode.getLabels().isEmpty())
				// toolName = diaNode.getLabels().get(0);
				AbstractTool atool = findTool(group, toolName);
				clog("must remove from tools: " + atool.getTitle());
				removeTool(DEFAULT_TOOL_GROUP_NAME, toolName);
			}

		}

	}

	/*
	 * https://gist.github.com/snursmumrik/5532388/raw/717
	 * b9726c37f782476574c2be8813a030d52f464/Dynamic+palette+tool+in+GMF Dynamic
	 * palette tool in GMF Vitaly Savickas
	 *
	 * void f(DiagramEditor diagramEditor){ PaletteRoot paletteRoot =
	 * ((DiagramEditor)
	 * diagramEditor).getDiagramGraphicalViewer().getEditDomain(
	 * ).getPaletteViewer().getPaletteRoot(); for (Object e :
	 * paletteRoot.getChildren()) { if (e instanceof PaletteToolbar) {
	 *
	 * ToolEntry entry = null;// new ToolEntry("Import", "Import a component",
	 * ComponentsDiagramEditorPlugin
	 * .findImageDescriptor("/ac.soton.fmusim.components/icons/Import.gif"),
	 * null) {};
	 *
	 * ((DiagramEditor)
	 * diagramEditor).getDiagramGraphicalViewer().getEditDomain(
	 * ).getPaletteViewer().addPaletteListener(new PaletteListener(){
	 *
	 * @Override public void activeToolChanged(PaletteViewer palette, ToolEntry
	 * tool) { if ("Import".equals(tool.getLabel())) { //
	 * System.out.println("Import"); // EditPart part = (EditPart)
	 * palette.getSelectedEditParts().get(0); // palette.deselectAll();
	 * palette.setActiveTool(activeTool); } }});
	 *
	 * ((DiagramEditor)
	 * diagramEditor).getDiagramGraphicalViewer().getEditDomain(
	 * ).getPaletteViewer().addSelectionChangedListener(new
	 * ISelectionChangedListener(){
	 *
	 *
	 * @Override public void selectionChanged(SelectionChangedEvent event) {
	 * Object source = event.getSource(); Object element =
	 * ((IStructuredSelection) event.getSelection()).getFirstElement(); if
	 * (source instanceof PaletteViewer && element instanceof ToolEntryEditPart)
	 * { PaletteEntry entry = (PaletteEntry) ((ToolEntryEditPart)
	 * element).getModel(); if ("Import".equals(entry.getLabel())) {
	 * System.out.println("Import"); PaletteViewer palette = (PaletteViewer)
	 * source; activeTool = palette.getActiveTool(); palette.deselectAll();
	 * palette.setActiveTool(activeTool); } } }});
	 *
	 * ((PaletteToolbar) e).add(entry); break; } } }
	 */
}
