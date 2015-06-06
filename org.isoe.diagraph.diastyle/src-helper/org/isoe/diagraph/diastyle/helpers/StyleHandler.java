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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.isoe.diagraph.diagraph.DAffixedEdge;
import org.isoe.diagraph.diagraph.DCompartmentEdge;
import org.isoe.diagraph.diagraph.DOwnedEdge;
import org.isoe.diagraph.diagraph.DEdge;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.diagraph.DGraphElement;
import org.isoe.diagraph.diagraph.DNavigationEdge;
import org.isoe.diagraph.diagraph.DNestedEdge;
import org.isoe.diagraph.diagraph.DLabeledEdge;
import org.isoe.diagraph.diagraph.DNode;
import org.isoe.diagraph.diagraph.DReference;
import org.isoe.diagraph.diagraph.DLabeledElement;
import org.isoe.diagraph.diagraph.DSimpleEdge;
import org.isoe.diagraph.diastyle.DBaseStyle;
import org.isoe.diagraph.diastyle.DColor;
import org.isoe.diagraph.diastyle.DDirection;
import org.isoe.diagraph.diastyle.DEdgeStyle;
import org.isoe.diagraph.diastyle.DLayout;
import org.isoe.diagraph.diastyle.DNestingEdgeStyle;
import org.isoe.diagraph.diastyle.DNodeEdgeStyle;
import org.isoe.diagraph.diastyle.DNodeStyle;
import org.isoe.diagraph.diastyle.DShape;
import org.isoe.diagraph.diastyle.DStyle;
import org.isoe.diagraph.diastyle.DStyleBridge;
import org.isoe.diagraph.diastyle.DiastyleFactory;
import org.isoe.diagraph.diastyle.keywords.BaseKeywords;
import org.isoe.diagraph.diastyle.keywords.EdgeKeywords;
import org.isoe.diagraph.diastyle.keywords.NodeEdgeKeywords;
import org.isoe.diagraph.diastyle.keywords.NodeKeywords;
import org.isoe.diagraph.diastyle.keywords.PartitionKeywords;
import org.isoe.diagraph.generic.GenericConstants;
import org.isoe.diagraph.lang.DiagraphKeywords;
//import org.isoe.diagraph.runner.IDiagraphRunner;
import org.isoe.diagraph.views.ViewConstants;
import org.isoe.diastyle.lang.StyleConstants;

/**
 *
 * @author pfister
 *
 */
public class StyleHandler implements IStyleHandler, StyleKeywords,
		BaseKeywords, EdgeKeywords, NodeEdgeKeywords, NodeKeywords,
		PartitionKeywords,IErrorReporter {

	public static final boolean HANDLE_GMF_PALETTE = true;
	private String bridgelog = "";
	private static final boolean LOG = false;

	enum StyleCase {
		node, link, containment // , compartment, error
	}

	private boolean initialized;
	private StyleUtils utils;
	private EPackage metaModel;
	private StyleFactory style3Factory;
	private String styleBridgeTrace;
	private String logStyle;
	private DGraph dgraph;
	private List<DGraphElement> styledDiagraphElements;
	private List<DStyleBridge> AllStyleBridges;
	//private IDiagraphRunner runner;

	private String pluginId = null;
	private IErrorReporter errorReporter;

	public enum Style {
		node, edge, nesting
	}
/*
	public StyleHandler() {
		super();
	}

	*/

	private void createStyleMapping(DGraph dgraph, String viewId) { // FP120715
		this.dgraph = dgraph;
		if (LOG)
			bridgelog = "";
		createStyles(viewId);
		if (LOG)
			clog(bridgelog);
	}

	private String[] getIconData(boolean isNode, int counter, String[] result) {
		if (!isNode) {
			if (counter != -1) {
				result[1] = "edge" + counter;
				result[2] = "edge" + counter + LARGE_ICON_SUFFIX;
			} else {
				result[1] = DEFAULT_ICON_EDGE;
				result[2] = DEFAULT_ICON_EDGE + LARGE_ICON_SUFFIX;
			}
		} else if (counter != -1) {
			result[1] = "node" + counter;
			result[2] = "node" + counter + LARGE_ICON_SUFFIX;
		} else {
			result[1] = DEFAULT_ICON_NODE;
			result[2] = DEFAULT_ICON_NODE + LARGE_ICON_SUFFIX;
		}

		return result;
	}

	@Override
	public String[] getIcons(DGraphElement graphElement, int imageId,
			Object caller) { // FP120930
		String[] result = new String[3];
		DBaseStyle dGraphStyle = null;
		try {
			dGraphStyle = getGraphStyle(graphElement);
			if (dGraphStyle instanceof DNodeEdgeStyle) {
				DNodeEdgeStyle ns = (DNodeEdgeStyle) dGraphStyle;
				result[0] = pluginId;
				if (ns.getIcon().equals(DEFAULT_ICON)) { // FP121002
					getIconData(graphElement instanceof DNode, imageId, result);
				} else {
					// result[0] = pluginId_;
					result[1] = ns.getIcon();
					result[2] = ns.getIcon() + LARGE_ICON_SUFFIX;
				}
			} else
				throw new RuntimeException(
						" should not happen in StyleHandler.getIcons !!!!");
		} catch (Exception e) {
			throw new RuntimeException(
					" should not happen in StyleHandler.getIcons !!!!");
		}
		if (LOG) {
			if (caller instanceof DStyle)
				clog(graphElement.getName() + ":" + dGraphStyle.getName()
						+ " - getIcons " + result[0] + "." + result[1] + "."
						+ result[2] + " called from QVTO via DStyle M2:  ");
			else
				clog(graphElement.getName() + ":" + dGraphStyle.getName()
						+ " - getIcons " + result[0] + "." + result[1] + "."
						+ result[2] + " called from "
						+ caller.getClass().getSimpleName());
		}
		return result;
	}

	@Override
	public DShape getNodeType(DGraphElement graphElement, Object caller) {
		return ((DNodeStyle) getGraphStyle(graphElement)).getShape();
	}

	@Override
	public int[] getSize(DGraphElement graphElement, Object caller) {
		int[] result = new int[2];
		DBaseStyle baseStyle = null;
		try {
			baseStyle = getGraphStyle(graphElement);
			// if (((DNodeStyle)baseStyle).getShape()!=DShape.CUSTOM)
			// throw new
			// RuntimeException("should not happen (getPolygon) !!!!");
			result[0] = ((DNodeStyle) baseStyle).getSizeX();
			result[1] = ((DNodeStyle) baseStyle).getSizeY();
		} catch (Exception e) {
			// result = "error 20";
			return null;
		}
		if (LOG) {
			if (caller instanceof DStyle)
				clog(graphElement.getName() + ":" + baseStyle.getName()
						+ " - Size " + result + " "
						+ " called from QVTO via DStyle M2:  ");
			else
				clog(graphElement.getName() + ":" + baseStyle.getName()
						+ " - Size " + result + " " + " called from "
						+ caller.getClass().getSimpleName());
		}
		return result;
	}

	private static String parseView(EAnnotation eAnnotation) {
		for (Map.Entry<String, String> entry : eAnnotation.getDetails())
			if (entry.getKey().startsWith(ViewConstants.VIEW_EQ))
				return entry.getKey().split("=")[1];
		return ViewConstants.DIAGRAPH_DEFAULT;
	}

	static EAnnotation getDiagraphAnnotation(EClass eclass, String viewId) {
		EList<EAnnotation> annots = eclass.getEAnnotations();
		EAnnotation result = null;
		int count = 0;
		for (EAnnotation annot : annots) {
			String id = parseView(annot);
			if (annot.getSource().equals(
					GenericConstants.ANNOT_DIAGRAPH_LITTERAL)) {
				if (viewId == null || id.equals(viewId)) {
					result = annot;
					count++;
				}
			}
		}
		if (count > 1)
			throw new RuntimeException(
					"more than one Diagraph Annotation for EClass: "
							+ eclass.getName());
		return result;
	}

	// TODO handle pref keyword

	private void registerStyle(String key, DGraphElement dGraphElement) { // FP121122
		if (key.startsWith(StyleConstants.STYLE) && key.contains("=")) {
			String[] k2 = key.split("=");
			if (k2[0].equals(DEFAULT_STYLE))
				throw new RuntimeException(DEFAULT_STYLE
						+ " is a reserved style !!!!");
			if (k2[0].startsWith(DiagraphKeywords.DOTTED_STYLE_LNK))
				registerLinkStyle(dGraphElement, k2[0], k2[1]);
			else if (k2[0].startsWith(DiagraphKeywords.DOTTED_STYLE_KREF))
				registerKrefStyle(dGraphElement, k2[0], k2[1]);
			else if (k2[0].startsWith(DiagraphKeywords.DOTTED_STYLE_CREF))
				registerCrefStyle(dGraphElement, k2[0], k2[1]);
			else if (k2[0].startsWith(DiagraphKeywords.DOTTED_STYLE_REF))
				registerRefStyle(dGraphElement, k2[0], k2[1]);
			else if (k2[0].equals(StyleConstants.STYLE))
				createNodeOrLinkStyle(dGraphElement, k2[0], k2[1]);
		}
	}

	private void createStyles(String id) {// FP120723xxx
		styledDiagraphElements = new ArrayList<DGraphElement>();
		for (DGraphElement dGraphElement : utils.getAllElements(dgraph)) {
			if (dGraphElement instanceof DLabeledElement) {
				EAnnotation diagraphAnnotation = getDiagraphAnnotation(
						((DLabeledElement) dGraphElement).getEClaz(), id);
				if (diagraphAnnotation == null) {
					if (LOG)
						clog(dGraphElement.getName() + " not annotated"); // FP121122
				} else
					for (Entry<String, String> entry : diagraphAnnotation
							.getDetails())
						registerStyle(entry.getKey(), dGraphElement);
			}
		}
		createDefaultStyles();
	}

	private String traceNodeBridge(DStyleBridge DStyleBridge,
			DNodeStyle dNodeStyle, String dgraphname) {
		DColor backgroundColor = dNodeStyle.getColor();
		// DLayout layout = dNodeStyle.getLayout();
		// DColor fontColor = dNodeStyle.getFontColor();
		String result = dgraphname + "-" + DStyleBridge.getName() + "-"
				+ StyleConstants.STYLE_BACKGROUND + "-"
				+ backgroundColor.getLiteral() + "\n";
		styleBridgeTrace += result;
		return result;
	}

	private String traceEdgeBridge(DStyleBridge DStyleBridge,
			DEdgeStyle dEdgeStyle, String dgraphname) {
		DColor backgroundColor = dEdgeStyle.getColor();
		DColor fontColor = dEdgeStyle.getFontColor();
		String result = dgraphname + "-" + DStyleBridge.getName() + "-"
				+ StyleConstants.STYLE_BACKGROUND + " -back:"
				+ backgroundColor.getLiteral() + " -font:"
				+ fontColor.getLiteral() + "\n";
		styleBridgeTrace += result;
		return result;
	}

	private String traceNestingEdgeBridge(DStyleBridge DStyleBridge,
			DNestingEdgeStyle partitionStyle, String dgraphname) {
		DColor backgroundColor = partitionStyle.getColor();
		String result = dgraphname + "-" + DStyleBridge.getName() + "-"
				+ StyleConstants.STYLE_BACKGROUND + "-"
				+ backgroundColor.getLiteral() + "\n";
		styleBridgeTrace += result;
		return result;
	}

	private void logBridge(DStyleBridge bridge) {
		String clazname = bridge.getDGraphElement().getClass().getSimpleName();
		if (LOG)
			clog("DGCBR " + clazname + " - " + bridge.getName() + " - "
					+ ((DBaseStyle) bridge.eContainer()).getName());
	}

	private StyleCase checkNode(DStyleBridge bridge,
			DGraphElement dGraphElement, DBaseStyle gstyl) {
		DNode dNode = (DNode) dGraphElement;
		if (!(gstyl instanceof DNodeStyle))
			throw new RuntimeException("error with style " + bridge.getName()
					+ " - " + gstyl.getClass().getSimpleName()
					+ " cannot be cast to DNodeStyle");
		if (gstyl instanceof DNodeStyle) {
			DNodeStyle dNodeStyle = (DNodeStyle) gstyl;
			String trace = traceNodeBridge(bridge, dNodeStyle, "DNode");
			if (!checkNodeStyle(dNode, StyleConstants.STYLE_BACKGROUND,
					dNodeStyle.getColor().getLiteral()))
				throw new RuntimeException("error with style " + trace);
		} else
			throw new RuntimeException("error with style " + gstyl.toString());
		if (LOG)
			logBridge(bridge);
		return StyleCase.node;
	}

	private StyleCase checkNestingEdge(DStyleBridge bridge,
			DGraphElement dGraphElement, DBaseStyle gstyl) {
		DNestedEdge nestingEdge = (DNestedEdge) dGraphElement;
		if (!(gstyl instanceof DNestingEdgeStyle))
			throw new RuntimeException("error with style " + bridge.getName()
					+ " - " + gstyl.getClass().getSimpleName()
					+ " cannot be cast to DNestingEdgeStyle");
		DNestingEdgeStyle nestingStyle = (DNestingEdgeStyle) gstyl;
		String trace = traceNestingEdgeBridge(bridge, nestingStyle,
				"DNestedEdge");
		// boolean isCompartment =...
		boolean checked = false;
		try {
			checked = checkNestingEdgeStyle_(nestingEdge,
					StyleConstants.STYLE_BACKGROUND, nestingStyle.getColor()
							.getLiteral());
		} catch (Exception e) {
			cerror("error with style " + trace); // FP121128
		}
		// if (!checked)
		// throw new RuntimeException("error with style " + trace);
		if (LOG)
			logBridge(bridge);
		return StyleCase.containment;
	}

	private StyleCase checkEdge(DStyleBridge bridge,
			DGraphElement dGraphElement, DBaseStyle gstyl) {
		DEdge dEdge = (DEdge) dGraphElement;
		if (!(gstyl instanceof DEdgeStyle))
			throw new RuntimeException("error with style " + bridge.getName()
					+ " - " + gstyl.getClass().getSimpleName()
					+ " cannot be cast to DEdgeStyle");
		DEdgeStyle dEdgeStyle = (DEdgeStyle) gstyl;
		String trace = traceEdgeBridge(bridge, dEdgeStyle, "DEdge");
		boolean isTyped = dEdge instanceof DLabeledElement;

		// FP130130
		/*
		 * if (!checkLinkEdgeStyle(dEdge, STYLE_BACKGROUND,
		 * dEdgeStyle.getColor().getLiteral())) throw new
		 * RuntimeException("error with style " + trace);
		 */
		if (LOG)
			logBridge(bridge);
		return StyleCase.link;
	}

	private StyleCase check(DStyleBridge bridge) {
		if (LOG)
			clog("check: " + bridge.getName());
		DGraphElement dGraphElement = bridge.getDGraphElement();
		DBaseStyle gstyl = (DBaseStyle) bridge.eContainer();
		if (gstyl instanceof DNodeStyle)
			return checkNode(bridge, dGraphElement, gstyl);
		else if (gstyl instanceof DNestingEdgeStyle)
			return checkNestingEdge(bridge, dGraphElement, gstyl);
		else if (gstyl instanceof DEdgeStyle)
			return checkEdge(bridge, dGraphElement, gstyl);
		else
			throw new RuntimeException("error with style " + gstyl.toString());
	}

	public List<DStyleBridge> getAllStyleBridges() {
		if (AllStyleBridges == null) {
			AllStyleBridges = new ArrayList<DStyleBridge>();
			for (DBaseStyle dGraphStyle : style3Factory.getdStyle().getStyles())
				AllStyleBridges.addAll(dGraphStyle.getStyleBridges());
		}
		return AllStyleBridges;// AllStyleBridges;
	}

	@Override
	public DStyleBridge getStylBridge(DNode dnod) {
		for (DStyleBridge DStyleBridge : getAllStyleBridges())
			if (DStyleBridge.getDGraphElement() == dnod) {
				if (LOG)
					clog("DStyleBridge ok: " + dnod.getName());
				return DStyleBridge;
			}
		return null;
	}

	@Override
	public DStyleBridge getStyleBridge2(DGraphElement el) {
		for (DStyleBridge DStyleBridge : getAllStyleBridges())
			if (DStyleBridge.getDGraphElement() == el)
				return DStyleBridge;
		return null;
	}

	@Override
	public DStyleBridge getStyleBridge(String graphElementName) {
		for (DStyleBridge DStyleBridge : getAllStyleBridges())
			if (DStyleBridge.getDGraphElement().getName()
					.equals(graphElementName))
				return DStyleBridge;
		return null;
	}

	@Override
	public DBaseStyle getGraphStyle(String graphElementName) {
		DStyleBridge styleBridge = getStyleBridge(graphElementName);
		DBaseStyle dGraphStyle = (DBaseStyle) styleBridge.eContainer();
		return dGraphStyle;
	}

	@Override
	public DBaseStyle getGraphStyle(DGraphElement el) {
		DStyleBridge result = getStyleBridge2(el);
		return result != null ? (DBaseStyle) getStyleBridge2(el).eContainer()
				: null;
	}

	@Override
	public DNodeStyle getNodeStyle(DNode dnod) {
		DStyleBridge result = getStylBridge(dnod);
		return result != null ? (DNodeStyle) getStylBridge(dnod).eContainer()
				: null;
	}

	//@Override
	/*
	private  DBaseStyle getEdgeStyle_nu(DEdge dedge) {
		DStyleBridge b = getStyleBridge2(dedge);
		if (b == null)
			return null;
		else
			return (DBaseStyle) b.eContainer();
	}
*/


	@Override
	public DBaseStyle getEdgeStyle(String dedgeName) {
		DStyleBridge b = getStyleBridge(dedgeName);
		if (b == null)
			return null;
		else
			return (DBaseStyle) b.eContainer();
	}

	/*
	@Override
	public String getBackgroundColor(DGraphElement graphElement, Object caller) {
		String result = "";
		DBaseStyle dGraphStyle = null;
		try {
			dGraphStyle = getGraphStyle(graphElement);
			result = dGraphStyle.getColor().getLiteral();
		} catch (Exception e) {
			result = "error 2";
			return result;
		}
		if (LOG) {
			if (caller instanceof DStyle)
				clog(graphElement.getName() + ":" + dGraphStyle.getName()
						+ " - BackgroundColor " + result + " "
						+ " called from QVTO via DStyle M2:  ");
			else
				clog(graphElement.getName() + ":" + dGraphStyle.getName()
						+ " - BackgroundColor " + result + " "
						+ " called from " + caller.getClass().getSimpleName());
		}
		return result;
	}*/

	public String logStyleBridges() {//FP150413a
		String log="";
		for (DStyleBridge DStyleBridge : getAllStyleBridges())
			log+=(DStyleBridge.getDGraphElement().getName())+"; ";
	   return log;
	}

	@Override
	public String getBackgroundColor(String graphElementName, Object caller) {

		String result = "";
		DBaseStyle dGraphStyle = null;
		try {

			dGraphStyle = getGraphStyle(graphElementName);
			if (dGraphStyle == null) //FP150413
			  cerror("getBackgroundColor: no graphstyle found for "+graphElementName+" in the collection "+logStyleBridges());
			else
			  clog("getBackgroundColor: graphstyle found for "+graphElementName+" in the collection "+logStyleBridges());
			result = dGraphStyle.getColor().getLiteral(); //FP150413a
		} catch (Exception e) {
			result = "error 2";
			return result;
		}

		return result;
	}

	@Override
	public int getArrowSize(DEdge dedge) {
		return ((DEdgeStyle) getEdgeStyle(dedge.getName())).getArrowSize();
	}

	//@Override
	/*
	public int getArrowSize_nu(DEdge dedge) {
		return ((DEdgeStyle) getEdgeStyle(dedge)).getArrowSize();
	}
	*/

	//@Override
	/*
	public String getOrientation_nu(DEdge dedge) {
		return ((DEdgeStyle) getEdgeStyle(dedge)).getArrowDirection()
				.getLiteral();
	}*/


	@Override
	public String getOrientation(DEdge dedge) {
		return ((DEdgeStyle) getEdgeStyle(dedge.getName())).getArrowDirection()
				.getLiteral();
	}






	@Override
	public String getArrowType(DEdge dedge) {
		return "arrow";
		// return getEdgeStyle(dedge).getArrowType();
	}

	public void bridgeToTopologyMapping(DGraph dgraph, String viewId) {
		if (LOG)
			clog("---------------- Creating Style To Topology Bridge ----------------");
		styleBridgeTrace = "";
		createStyleMapping(dgraph, viewId);
		for (DStyleBridge style2TopoMap : getAllStyleBridges()) {
			StyleCase cas = check(style2TopoMap);
			if (cas == StyleCase.node)
				;
			else if (cas == StyleCase.link)
				;
			else if (cas == StyleCase.containment)
				;
		}
		logStyle += "\nstyleBridgeTraces:\n" + styleBridgeTrace + "\n";
		if (LOG)
			clog("\n----------------\n" + logStyle + "\n----------------\n");
	}




	public StyleHandler(IErrorReporter errorReporter,EPackage mmodel, String pluginId) {
		this.pluginId = pluginId;// "idm.scg.tp4";
		this.metaModel = mmodel;
		this.utils = new StyleUtils(this,dgraph, this);// , dgraph);
		this.style3Factory = new StyleFactory(this);
		this.errorReporter = errorReporter;
		initialized = true;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

	public void setStyle(DStyle style) {
		if (initialized)
			style3Factory.setStyle(style);
	}

	public DStyle createModel() {
		if (style3Factory.getdStyle() == null) {
			style3Factory.createModel();
			style3Factory.getdStyle().setStyleHandler(this); // FP120723xxx
			createBaseStyles();
			try {
				parseStyleDeclarations();
			} catch (StyleParserException e) {
				throw new RuntimeException(e.getMessage());
			}
			resolveStyleInheritance();
		}
		return style3Factory.getdStyle();
	}

	private boolean checkNestingEdgeStyle_(DEdge dEdge, String command,
			String commandArgument) { // FP120923a



		if (dEdge instanceof DNestedEdge) {
			DNestedEdge dContainment = (DNestedEdge) dEdge;
			if (LOG){

				clog("nesting edge: " + dContainment.getName());
			}
		}
		EReference eref = null;
		//DNode src = dEdge.getSource();
		DNode src = null;

		if (dEdge instanceof DNestedEdge){ //FP150423b
			src = ((DNestedEdge)dEdge).getSource().getNode();
		} else if (dEdge instanceof DSimpleEdge){
			src = ((DSimpleEdge) dEdge).getSource();
		}

		//DNode src = dEdge.getSource().getNode();
		EClass eclazz = src.getEClaz(); // FP130615ok
		if (LOG)
			clog(eclazz.getName());
		EList<EReference> refs = eclazz.getEAllReferences();
		if (LOG)
			for (EReference eReference : refs) {
				clog(eReference.getName());
				EClass rty = (EClass) eReference.getEType();
				clog(rty.getName());
				clog(dEdge.getName());
			}

		ENamedElement rcsg = dEdge.getSemanticRole();// FP130615ok
														// //getSemanticRole();
		if (rcsg != null && rcsg instanceof EReference) // FP121128
			eref = (EReference) rcsg;
		else {
			if (rcsg == null)
				throw new RuntimeException("checkNestingEdgeStyle: DEdge "
						+ dEdge.getName() + " - " + command + " - "
						+ commandArgument + " - "
						+ "roleInCsGraph is null !!!! ");
			else
				throw new RuntimeException("checkNestingEdgeStyle: DEdge "
						+ dEdge.getName() + " - " + command + " - "
						+ commandArgument + " - "
						+ "roleInCsGraph must be an EReference " + " but is a "
						+ rcsg.getClass().getName() + " !!!!");
		}
		EClass eclaz = (EClass) eref.eContainer();
		if (utils.findDiagraphedClass(metaModel, eclaz.getName()) == null)
			throw new RuntimeException(
					"checkContainmentEdgeStyle - no EClass +" + eclaz.getName()
							+ " !!!");
		if (LOG)
			clog(command);
		if (StyleConstants.STYLE_BACKGROUND.equals(command)) {
			checkBackgroundForGraphElement(dEdge, commandArgument);
			return true;
		}


		return false;
	}

	private boolean checkLinkEdgeStyle(DEdge dEdge, String command,
			String commandArgument) {
		EClass eclaz = null;
		EReference eref = null;
		ENamedElement rcsg = dEdge.getSemanticRole();
		if (dEdge instanceof DLabeledEdge) {
			if (rcsg instanceof EClass)
				eclaz = (EClass) dEdge.getSemanticRole();
			else
				throw new RuntimeException(dEdge.getName()
						+ ": roleInCsGraph must be an EClass !!!!");
		} else if (dEdge instanceof DNestedEdge
				|| dEdge instanceof DReference) {
			if (rcsg instanceof EReference)
				eref = (EReference) dEdge.getSemanticRole();
			else
				throw new RuntimeException(dEdge.getName()
						+ ": roleInCsGraph must be an EReference !!!!");
		}
		if (eclaz != null) {
			if (utils.findDiagraphedClass(metaModel, eclaz.getName()) == null)
				throw new RuntimeException("Link has no diagraph annotation: "
						+ eclaz.getName() + " !!!");
		}
		if (eref != null) {
			if (utils.findDiagraphedReference(metaModel, eref.getName()) == null)
				throw new RuntimeException(
						"Reference has no diagraph annotation: "
								+ eref.getName() + " !!!");
		}
		if (StyleConstants.STYLE_BACKGROUND.equals(command)) {
			checkBackgroundForGraphElement(dEdge, commandArgument);
			return true;
		}
		return false;
	}

	public boolean checkNodeStyle(DNode dNode, String command,
			String commandArgument) {
		EClass eclaz = (EClass) dNode.getSemanticRole();
		if (utils.findDiagraphedClass(metaModel, eclaz.getName()) == null)
			throw new RuntimeException("Node has no diagraph annotation: "
					+ eclaz.getName() + " !!!");
		if (StyleConstants.STYLE_LAYOUT_.equals(command)) {
			checkLayout(dNode, commandArgument);
			return true;
		} else if (StyleConstants.STYLE_BACKGROUND.equals(command)) {
			checkBackgroundForGraphElement(dNode, commandArgument);
			return true;
		}
		return false;
	}

	private void checkLayout(DNode dNode, String layoutCommand) {
		if (LOG)
			clog("S3PSET " + dNode.eClass().getName() + " - "
					+ StyleConstants.STYLE_LAYOUT_ + " - " + layoutCommand);
		EClass eclaz = (EClass) dNode.getSemanticRole();
		DLayout layout = DLayout.get(layoutCommand);
		if (layout == null)
			throw new RuntimeException(" for " + eclaz.getName() + " - "
					+ "bad layout: " + layoutCommand);
	}

	private void checkBackgroundForGraphElement(DGraphElement graphElement,
			String colourCommand) {
		if (LOG)
			clog("S3PSET " + graphElement.getName() + " - "
					+ StyleConstants.STYLE_BACKGROUND + " - " + colourCommand);
		DColor backgroundcolor = DColor.get(colourCommand);
		if (backgroundcolor == null)
			throw new RuntimeException(" for " + graphElement.getName() + " - "
					+ "bad background color: " + colourCommand);
	}

	private void resolveStyleInheritance() {
		for (DBaseStyle dGraphStyle : style3Factory.getdStyle().getStyles())
			if (dGraphStyle.getParentName() != null
					&& !dGraphStyle.getParentName().equals(DEFAULT_STYLE))
				dGraphStyle.setParent(findParent(dGraphStyle.getParentName()));
	}

	private DBaseStyle findParent(String name) {
		for (DBaseStyle dGraphStyle : style3Factory.getdStyle().getStyles())
			if (dGraphStyle.getName().equals(name))
				return dGraphStyle;
		return null;
	}

	public EObject getModel() {
		return style3Factory.getdStyle();
	}

	private void createBaseStyles() {
		utils.getGraphStyle(style3Factory.getdStyle(), DEFAULT_STYLE,
				Style.node);
		utils.getGraphStyle(style3Factory.getdStyle(), DEFAULT_STYLE,
				Style.edge);
		utils.getGraphStyle(style3Factory.getdStyle(), DEFAULT_STYLE,
				Style.nesting);
	}

	@Override
	public void setGenericDefaultValues(DBaseStyle graphStyle) {
		graphStyle.setColor(DColor.get(style3Factory.parseColor(INIT_COLOR)));
	}

	@Override
	public void setSpecificDefaultValues(DNestingEdgeStyle nestingEdgeStyle) {
		// nothing
	}

	@Override
	public void setSpecificDefaultValues(DEdgeStyle edgeStyle) {
		edgeStyle.setArrowDirection(DDirection.get(style3Factory
				.parseDirection(INIT_ARROW)));
		edgeStyle.setArrowSize(Integer.parseInt(INIT_ARROW_SIZE));
		edgeStyle.setColor(DColor.get(style3Factory
				.parseColor(INIT_ARROW_COLOR))); // FP120929
		edgeStyle.setIcon(INIT_ICON);
	}

	@Override
	public void setSpecificDefaultValues(DNodeStyle styl) {
		styl.setLayout(DLayout.get(style3Factory.parseLayout(INIT_LAYOUT_)));
		// styl.setShape(DShape.get(style3Factory.parseShape(INIT_SHAPE)));
		styl.setShape(style3Factory.parseShape(INIT_SHAPE)); // FP130616
		styl.setSizeX(Integer.parseInt(INIT_SIZE_X));
		styl.setSizeY(Integer.parseInt(INIT_SIZE_Y));
		styl.setRadius(Integer.parseInt(INIT_RADIUS));
		styl.setShapeData(INIT_SHAPE_DATA);
		styl.setIcon(INIT_ICON);
	}

	private String styleAsString(Style styl) {
		return (styl == Style.node) ? "node" : ((styl == Style.edge) ? "edge"
				: ((styl == Style.nesting) ? "nesting" : (null)));
	}

	private String styleName(EAnnotation annot, Style styl) {
		switch (styl) {
		case node:
			return annot.getSource().substring(
					DOTTED_DIASTYL_NODE_LITERAL_LENGTH);
		case edge:
			return annot.getSource().substring(
					DOTTED_DIASTYL_EDGE_LITERAL_LENGTH);
		case nesting:
			return annot.getSource().substring(
					DOTTED_DIASTYL_PARTITION_LITERAL_LENGTH);
		default:
			return null;
		}
	}

	private void parseGraphTarget(EAnnotation anot, Style styl)
			throws StyleParserException {
		String stylename = styleName(anot, styl);
		if (LOG)
			clog("diastyle annotation found: " + stylename + "="
					+ styleAsString(styl));
		EMap<String, String> details = anot.getDetails();
		for (Entry<String, String> entry : details)
			parseStyleEntry(stylename, entry.getKey(), styl);
	}

	private DBaseStyle parseStyleEntry(String name, String keyval, Style styl)
			throws StyleParserException {
		if (LOG)
			clog(name + " [" + styleAsString(styl) + " " + keyval + "]");
		DBaseStyle result = null;
		String[] k2 = keyval.split("=");
		if (k2.length != 2)
			throw new StyleParserException(
					"parseStyleEntry - should have 2 arguments !!!");
		result = utils.getGraphStyle(style3Factory.getdStyle(), name, styl);
		String property = k2[0].trim();
		String value = k2[1].trim();
		if (!style3Factory.isValidProperty(property))
			throw new StyleParserException("unknown property: " + property
					+ "=" + value);
		style3Factory.prepare();

		// 1 - BaseStyle //FP120930
		style3Factory.setGenericBaseStyleProperties(property, result, value);
		// 2a - NodeEdgeStyle
		if (result instanceof DEdgeStyle)
			style3Factory.setEdgeStyleProperties(property, (DEdgeStyle) result,
					value);
		// 2b - NodeEdgeStyle
		if (result instanceof DNodeEdgeStyle)
			style3Factory.setNodeEdgeStyleProperties(property,
					(DNodeEdgeStyle) result, value);

		// 3 - DNestingEdgeStylee
		if (result instanceof DNestingEdgeStyle)
			style3Factory.setNestingEdgeStyleProperties(property,
					(DNestingEdgeStyle) result, value);

		switch (styl) {
		case node:
			setNodeSpecificProperties(property, (DNodeStyle) result, value);
			break;
		case edge:
			setEdgeSpecificProperties(property, (DEdgeStyle) result, value);
			break;
		case nesting:
			setNestingEdgeSpecificProperties(property,
					(DNestingEdgeStyle) result, value);
			break;
		default:
			throw new RuntimeException("should not happen !!!!");
		}

		if (!style3Factory.isDone())
			throw new RuntimeException("not handled property: " + property
					+ "=" + value);
		return result;
	}

	private void setNestingEdgeSpecificProperties(String property,
			DNestingEdgeStyle styl, String value) {
		if (property.equals(DIASTYLE_PARTITION_XX))
			;// style3Factory.setXX(styl, value);

	}

	private void setEdgeSpecificProperties(String property, DEdgeStyle styl,
			String value) {
		if (property.equals(DIASTYLE_EDGE_DIRECTION))
			style3Factory.setDirection(styl, value);
		else if (property.equals(DIASTYLE_EDGE_ARROWSIZE))
			style3Factory.setArrowSize(styl, value);
		else if (property.equals(DIASTYLE_EDGE_SHAPE))
			style3Factory.setEdgeShape(styl, value);
		else if (property.equals(DIASTYLE_EDGE_LINEWIDTH))
			style3Factory.setEdgeLineWidth(styl, value);
		else if (property.equals(DIASTYLE_EDGE_LINE))
			style3Factory.setEdgeLine(styl, value);
	}

	private void setNodeSpecificProperties(String property, DNodeStyle styl,
			String value) {
		if (property.equals(DIASTYLE_NODE_SIZE))
			style3Factory.setNodeSize(styl, value);
		else if (property.equals(DIASTYLE_NODE_LAYOUT_))
			style3Factory.setLayout(styl, value);
		else if (property.equals(DIASTYLE_NODE_RADIUS))
			style3Factory.setRadius(styl, value);
		else if (property.equals(DIASTYLE_NODE_SHAPE))
			style3Factory.setShape(styl, value);
		else if (property.equals(DIASTYLE_NODE_SHAPEDATA))
			style3Factory.setShapeData(styl, value);
		else if (property.equals(DIASTYLE_NODE_FIGURE))
			style3Factory.setCustomFigure(styl, value);
	}

	private void parseStyleDeclarations() throws StyleParserException { // FP120711
		for (EAnnotation packageAnnotation : metaModel.getEAnnotations()) {
			if (LOG)
				clog("SP3PRS [" + packageAnnotation.getSource() + "]");
			Style styl = null;
			if (packageAnnotation.getSource().startsWith(
					DOTTED_DIASTYLE_NODE_LITERAL))
				styl = Style.node;
			else if (packageAnnotation.getSource().startsWith(
					DOTTED_DIASTYLE_EDGE_LITERAL))
				styl = Style.edge;
			else if (packageAnnotation.getSource().startsWith(
					DOTTED_DIASTYLE_PARTITION_LITERAL))
				styl = Style.nesting;
			if (styl != null)
				parseGraphTarget(packageAnnotation, styl);
			else if (LOG)
				clog("unknown annotation " + packageAnnotation.getSource());
		}

		EList<DBaseStyle> styles = style3Factory.getdStyle().getStyles();

		logStyle = "\n All Styles \n";
		for (DBaseStyle dGraphStyle : styles) {
			String dGraphStyleAsString = utils.toString(dGraphStyle);
			logStyle += dGraphStyleAsString + "\n";
			if (LOG)
				clog(dGraphStyleAsString);
		}

	}

	private List<DGraphElement> getNotYetStyledElements(
			List<DGraphElement> styledElements) {
		List<DGraphElement> result = new ArrayList<DGraphElement>();
		for (DGraphElement dGraphElement : utils.getAllElements(dgraph))
			if (!styledElements.contains(dGraphElement))
				result.add((DGraphElement) dGraphElement);
		return result;
	}

	private DBaseStyle getStyle(DGraphElement element, String stylname) {
		if (element instanceof DNode)
			return utils.getNodeStyle(style3Factory.getdStyle(), stylname);
		else if (element instanceof DEdge) {
			if (element instanceof DLabeledEdge)
				return utils.getEdgeStyle(style3Factory.getdStyle(), stylname); // FP121001
			else if (element instanceof DAffixedEdge) // FP130617
				return utils.getEdgeStyle(style3Factory.getdStyle(), stylname); // FP121001
			else if (element instanceof DReference)
				return utils.getEdgeStyle(style3Factory.getdStyle(), stylname); // FP121001
			else if (element instanceof DNestedEdge)
				return utils.getPartitionStyle(style3Factory.getdStyle(),
						stylname);
		}
		return null;
	}

	private String getStyleTypeAsString(DBaseStyle styl) {
		if (styl instanceof DNodeStyle)
			return "Node style";
		else if (styl instanceof DEdgeStyle)
			return "Edge style";
		else if (styl instanceof DNestingEdgeStyle)
			return "Nesting Edge style";
		else
			return "unknown style type";
	}

	private void addBridge(DBaseStyle style, DStyleBridge styleBridge) {
		if (LOG) {
			bridgelog += styleBridge.getName() + " - "
					+ getStyleTypeAsString(style) + "\n";
			clog("added bridge " + bridgelog);
		}
		style.getStyleBridges().add(styleBridge);
	}

	private DBaseStyle createStyle(DGraphElement element, String stylname) {
		if (!((element instanceof DNode) || (element instanceof DEdge)))
			throw new RuntimeException("should be a DNode or a DEdge");

		DStyleBridge styleBridge = DiastyleFactory.eINSTANCE
				.createDStyleBridge();
		styleBridge.setDGraphElement(element);
		styleBridge.setName(element.getName() + "." + stylname);
		DBaseStyle graphStyle = getStyle(element, stylname); // FP121001 ok

		if (graphStyle != null) {
			addBridge(graphStyle, styleBridge);
			if (!stylname.equals(DEFAULT_STYLE))
				styledDiagraphElements.add(element);
			if (LOG)
				clog("GSDE " + getStyleTypeAsString(graphStyle) + ":  "
						+ element.getName() + " - " + stylname);
		} else {
			if (ALLOW_UNKNOWN_STYLE)
				clog("no style found: " + element.getName() + "." + stylname); // FP130616
																				// //FP130319pppb
			else
				throw new RuntimeException("no style found: "
						+ element.getName() + "." + stylname); // FP130319pppb
		}
		return graphStyle;
	}

	private static boolean ALLOW_UNKNOWN_STYLE = false;

	private void registerKrefStyle(DGraphElement dGraphElement, String key,
			String stylname) { // FP120923
		DNestedEdge dContainment = utils.findCompartment(dgraph,
				dGraphElement, key);
		if (!(dContainment instanceof DCompartmentEdge))
			throw new RuntimeException(key + " is not a compartment !!!!");
		DBaseStyle result = createEdgeStyle(dContainment, stylname);
		if (LOG) {
			if (result != null)
				clog("compartment: " + dContainment.getName() + " ...  "
						+ dContainment.getSource().getNode().getEClaz().getName()
						+ " |k--[" + dContainment.getName() + "]-->"
						+ dContainment.getTarget().getEClaz().getName()
						+ " style=" + result.getName());
		}
		if (result == null)
			throw new RuntimeException("registerKrefStyle-"
					+ " should not happen !!!!");

	}

	public List<DEdge> getEdges(DNode node) {
		List<DEdge> result = new ArrayList<DEdge>();
		for (DGraphElement element : node.getGraph().getElements()){
			if (element instanceof DNestedEdge){
				if (((DNestedEdge) element).getSource().getNode() == node)
					result.add((DEdge) element);
			} else if(element instanceof DSimpleEdge){
				 if (((DSimpleEdge) element).getSource() == node)
					result.add((DEdge) element); //FP150423b
			}
		}
		return result;
	}

	private void registerLinkStyle(DGraphElement dGraphElement, String key,
			String stylname) {
		DLabeledEdge linkElement = utils.finDLabeledEdge(dgraph, dGraphElement, key);
		DBaseStyle result = createNodeOrLinkStyle(linkElement, "link", stylname);
		if (LOG) {
			if (result != null)
				clog("link: " + linkElement.getName() + " ...  "
						+ linkElement.getSource().getEClaz().getName() //FP150413c
						+ " |l--[" + linkElement.getName() + "]-->"
						+ linkElement.getTarget().getEClaz().getName()
						+ " style=" + result.getName());
		}
		if (result == null)
			throw new RuntimeException("registerLinkStyle-"
					+ " should not happen !!!!");
	}



	private DNode getSource(DEdge edg){
		if (edg instanceof DNestedEdge) //FP150423b
			return ((DNestedEdge)edg).getSource().getNode();
		 else if (edg instanceof DSimpleEdge )
			return ((DSimpleEdge) edg).getSource();
		 else throw new RuntimeException("should not happen in getSource");
	}

	private void registerCrefStyle(DGraphElement dGraphElement, String key,
			String stylname) {
		DNestedEdge nestingEdge = utils.findContainment(dgraph,
				dGraphElement, key);
		if (nestingEdge instanceof DCompartmentEdge)
			throw new RuntimeException(key + " is a compartment !!!!");
		DBaseStyle result = createEdgeStyle(nestingEdge, stylname);
		if (LOG) {
			if (result != null){
				clog("containment: " + nestingEdge.getName() + " ...  "
						+ getSource(nestingEdge).getEClaz().getName()
						+ " |c--[" + nestingEdge.getName() + "]-->"
						+ nestingEdge.getTarget().getEClaz().getName()
						+ " style=" + result.getName());
			}
		}
		if (result == null)
			throw new RuntimeException("registerCrefStyle-"
					+ " should not happen !!!!");
	}



	private void registerRefStyle(DGraphElement dGraphElement, String key,
			String stylname) {
		DReference refEdge = utils.findReference(dgraph, dGraphElement, key);
		DBaseStyle result = createEdgeStyle(refEdge, stylname);
		if (LOG) {
			if (result != null)
				clog("containment: " + refEdge.getName() + " ...  "
						+ getSource(refEdge).getEClaz().getName() + " |r--["
						+ refEdge.getName() + "]-->"
						+ refEdge.getTarget().getEClaz().getName() + " style="
						+ result.getName());
		}
		if (result == null)
			throw new RuntimeException("registerRefStyle-"
					+ " should not happen !!!!");
	}

	private void createDefaultStyles() { // FP120715
		DBaseStyle sty = null;
		for (DGraphElement graphElement : getNotYetStyledElements(styledDiagraphElements))
			sty = createStyle(graphElement, DEFAULT_STYLE);
	}

	private DBaseStyle createNodeOrLinkStyle(DGraphElement dGraphElement,
			String key, String stylname) {
		DBaseStyle result = null;
		if (!(dGraphElement instanceof DNode || dGraphElement instanceof DLabeledEdge))
			throw new RuntimeException("should be a node or a link");
		if (!styledDiagraphElements.contains(dGraphElement))
			result = createStyle(dGraphElement, stylname);
		return result;
	}

	private DBaseStyle createEdgeStyle(DEdge edge, String stylname) {// FP120717a
		DBaseStyle result = null;
		if (!styledDiagraphElements.contains(edge))
			result = createStyle(edge, stylname);
		return result;
	}

	@Override
	public List<DNestedEdge> getContainments() {
		List<DNestedEdge> result = new ArrayList<DNestedEdge>();
		for (DGraphElement graphElement : getAllElements()) {
			if (graphElement instanceof DNestedEdge)
				result.add((DNestedEdge) graphElement);
		}
		return result;
	}

	@Override
	public List<DCompartmentEdge> getCompartments() {
		List<DCompartmentEdge> result = new ArrayList<DCompartmentEdge>();
		for (DNestedEdge containmnt : getContainments())
			if (containmnt instanceof DCompartmentEdge)
				result.add((DCompartmentEdge) containmnt);
		return result;
	}

	private List<DNode> getNodes() { // FP140518
		List<DNode> nodes = new ArrayList<DNode>();
		List<DGraphElement> elements = dgraph.getElements();
		for (DGraphElement element : elements) {
			if (element instanceof DNode)
				nodes.add((DNode) element);
		}
		return nodes;
	}

	private List<DEdge> getEdges() { // FP140518
		List<DEdge> result = new ArrayList<DEdge>();
		for (DGraphElement element : dgraph.getElements())
			if (element instanceof DEdge)
				result.add((DEdge) element);
		return result;
	}
/*
	private List<DEdge> getEdges(DNode nod) {
		List<DEdge> result = new ArrayList<DEdge>();
		List<DEdge> all = getEdges();
		for (DEdge dEdge : all)
			if (dEdge.getSource().getNode() == nod)
				result.add(dEdge);
		return result;
	}
	*/


	@Override
	public List<DGraphElement> getAllElements() {
		List<DGraphElement> result = new ArrayList<DGraphElement>();
		result.addAll(getNodes());
		List<DNode> nodes = getNodes();
		for (DNode nod : nodes) {
			List<DEdge> edges = getEdges(nod);
			for (DEdge edg : edges) {
				if (!result.contains(edg))
					result.add(edg);
			}
		}
		return result;
	}

	@Override
	public List<DNode> getAllNodes() {
		return getNodes();
	}

	@Override
	public DNode findNode(String nodeName) {
		List<DNode> nods = getNodes();
		for (DNode dNode : nods) {
			if (dNode.getName().equals(nodeName))
				return dNode;
		}
		return null;
	}

	@Override
	public DCompartmentEdge getCompartment(String eModelElementName) { // FP120722
		for (DCompartmentEdge compartment : getCompartments()) {
			if (LOG)
				clog(compartment.getTarget().getName());
			if (compartment.getTarget().getName().equals(eModelElementName))
				return compartment;
		}
		return null;
	}

	@Override
	public DCompartmentEdge getNodeCompartment(String nodeName) { // FP120722
		DNode nod = findNode(nodeName);
		for (DCompartmentEdge compartment : getCompartments())
			if (compartment.getTarget() == nod)
				return compartment;
		return null;
	}

	private boolean isInheritedListLayout(DNodeStyle nodeStyle) { // FP130317x
		if (nodeStyle.getParent() != null) {
			DNodeStyle pnodeStyle = (DNodeStyle) nodeStyle.getParent();
			String lay = pnodeStyle.getLayout().getLiteral();
			if (!lay.equals(StyleKeywords.DIASTYLE_LAYOUT_NONE)) {
				if (lay.equals(StyleKeywords.DIASTYLE_LAYOUT_FREE)) // FP130317x
					return false;
				else
					return true;
			} else
				return isInheritedListLayout(pnodeStyle);
		}
		return false;
	}

	@Override
	public boolean isListLayout(String eNodeName) { // FP120722
		DNestedEdge compart = getNodeCompartment(eNodeName);
		if (compart != null) {
			DNode nod = findNode(eNodeName);
			DNodeStyle nodeStyle = getNodeStyle(nod);
			String lay = nodeStyle.getLayout().getLiteral();
			if (!lay.equals(StyleKeywords.DIASTYLE_LAYOUT_NONE)) {
				if (lay.equals(StyleKeywords.DIASTYLE_LAYOUT_FREE)) // FP130317x
					return false;
				else
					return true;
			} else
				return isInheritedListLayout(nodeStyle);
		}
		return false;
	}

	@Override
	public String getFigure(DGraphElement element, Object caller) {
		return getPolygon(element, caller); // FP121210
	}

	@Override
	public String getPolygon(DGraphElement graphElement, Object caller) {
		String result = "";
		DBaseStyle baseStyle = null;
		try {
			baseStyle = getGraphStyle(graphElement);
			if (((DNodeStyle) baseStyle).getShape() != DShape.CUSTOM)
				throw new RuntimeException(
						"should not happen (getPolygon) !!!!");
			result = ((DNodeStyle) baseStyle).getShapeData();
		} catch (Exception e) {
			result = "error 20";
			return result;
		}
		if (LOG) {
			if (caller instanceof DStyle)
				clog(graphElement.getName() + ":" + baseStyle.getName()
						+ " - Polygon " + result + " "
						+ " called from QVTO via DStyle M2:  ");
			else
				clog(graphElement.getName() + ":" + baseStyle.getName()
						+ " - Polygon " + result + " " + " called from "
						+ caller.getClass().getSimpleName());
		}
		return result;
	}



	@Override
	public String getIcon(DGraphElement element, Object caller) {
		String result = "";
		DBaseStyle dGraphStyle = null;
		try {
			dGraphStyle = getGraphStyle(element);
			// result = dGraphStyle.getIcon().getLiteral();
			result = "error 3, todo or to remove: getIcon ";
		} catch (Exception e) {
			result = "error 3";
			return result;
		}
		if (LOG) {
			if (caller instanceof DStyle)
				clog(element.getName() + ":" + dGraphStyle.getName()
						+ " - Icon " + result + " "
						+ " called from QVTO via DStyle M2:  ");
			else
				clog(element.getName() + ":" + dGraphStyle.getName()
						+ " - Icon " + result + " " + " called from "
						+ caller.getClass().getSimpleName());
		}
		return result;
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);

	}

	@Override
	public void cerror(String mesg) {
		errorReporter.cerror(mesg);
	}

}
