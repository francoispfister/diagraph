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
package org.isoe.diagraph.notationparser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.isoe.diagraph.diagraph.DEdge;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.diagraph.DGraphElement;
import org.isoe.diagraph.diagraph.DLabeledEdge;
import org.isoe.diagraph.diagraph.DNode;
import org.isoe.diagraph.diagraph.DOwnedEdge;
import org.isoe.diagraph.diagraph.DiagraphFactory;
import org.isoe.diagraph.diastyle.DStyle;
import org.isoe.diagraph.factory.DGraphFactory;
import org.isoe.diagraph.generic.GenericConstants;
import org.isoe.diagraph.lang.DiagraphKeywords;
import org.isoe.diagraph.parser.api.IDiagraphNotation;
import org.isoe.diagraph.views.ViewConstants;
import org.isoe.diagraph.workbench.api.DiagraphNotifiable;
import org.isoe.diagraph.workbench.api.IDiagraphFactoryClient;
import org.isoe.extensionpoint.parsers.IDiagraphProvider;
import org.isoe.extensionpoint.parsers.IRuntimeDiagraphParser;
//import org.isoe.extensionpoint.diagraph.IDiagraphGen;
import org.isoe.extensionpoint.diagraph.generator.IDiagraphGen;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.utils.debug.WorkspaceUtils;

/**
 *
 * @author pfister
 *
 */
public class DiagramParser implements IRuntimeDiagraphParser,IDiagraphFactoryClient {

	private DiagraphNotifiable owner;
	// private DGraphFactory dGraphFactory;
	private DStyle dStyle;
	private DNode current;
	private DGraphElement currentGraphElement;
	private IDiagraphProvider diagraphProvider;
	private String state;
	private String views = "";
	private String currentView = DiagraphKeywords.DIAGRAPH_DEFAULT;
	private EAnnotation currentAnnoration;
	private String graphType;
	private EClass currentEClass_;
	private List<EAnnotation> diagraphAnnotations = new ArrayList<EAnnotation>();
	private List<EAnnotation> allAnnotations = new ArrayList<EAnnotation>();
	private int callerid;
	private EPackage epakage;
	private DGraph graph;

	public static final boolean LAYOUT_LOG = false;
	private static final boolean LOG = DParams.DiagramParser_LOG;

	public enum ParserState {
		uri, pakage, eclass, diagram, diagraph, nodes, node, keyword, end, unknown
	}

	@Override
	public void setCurrentView(String currentView) {
		this.currentView = currentView;
	}

	private DGraphElement parseObj(String from, Object o) {
		DGraphElement result = parse(from, o);
		DNode current = getCurrent();
		// DGraph graph = getGraph_(); //FP140115x
		return result;
	}

	private void parseAnnotationDetails(EAnnotation anot, int target) {
		for (Map.Entry<String, String> entry : anot.getDetails()) {
			parseObj("dp1", entry.getKey());
			log(target,
					entry.getKey()
							+ (entry.getValue() != null
									&& !entry.getValue().isEmpty() ? "->"
									+ entry.getValue() : ""));
		}
	}

	public void parseNode(Node gmfnode, boolean store, int target) {
		LayoutConstraint lc = gmfnode.getLayoutConstraint();
		if (lc instanceof Bounds)
			logBounds((Bounds) lc, target);
		EObject el = gmfnode.getElement();
		if (el != null) {
			if (el instanceof EClass) {
				EClass eclaz = (EClass) el;
				log(target, eclaz.getName());
				EAnnotation diagraphanot = eclaz
						.getEAnnotation(GenericConstants.ANNOT_DIAGRAPH_LITTERAL);
				if (diagraphanot != null) {
					if (store)
						diagraphAnnotations.add(diagraphanot);
					log(target, eclaz.getName() + "{");
					currentGraphElement = parseObj("dp3", eclaz);
					parseObj("dp2", diagraphanot);
					parseAnnotationDetails(diagraphanot, target);
					log(target, "}");
					log(target, "");
				} else
					log(target, "no "
							+ GenericConstants.ANNOT_DIAGRAPH_LITTERAL
							+ " annotation");
			} else {
				if (el instanceof org.eclipse.emf.ecore.EAnnotation) {
					EAnnotation annotation = (EAnnotation) el;
					EModelElement me = annotation.getEModelElement();
					if (store)
						allAnnotations.add(annotation);
				} else
					log(target, "<<not a known node " + el.getClass().getName()
							+ ">>(1)");
			}
		} else {
			if (gmfnode instanceof Shape) {
				Shape sh = (Shape) gmfnode;
				log(target, "Shape " + sh.getDescription());
				logBounds((Bounds) sh.getLayoutConstraint(), target);
			} else {
				log(target, "[[not an semantic node " + gmfnode.toString()
						+ "]]");
			}
		}
	}

	private void log(int target, String name) {
		// TODO Auto-generated method stub

	}

	private void logBounds(Bounds lc, int target) {
		// TODO Auto-generated method stub

	}

	@Override
	public void parseEnd() {
		parse("p10", currentView, ParserState.end, null, null);
		diagraphProvider.endParsed();
	}

	@Override
	public void parseStart() {

	}

	/*
	 * public DGraph getGraph() { return dGraph; }
	 *
	 * public void setGraph(DGraph graph) { this.dGraph = graph; }
	 */

	public DNode getCurrent() {
		return current;
	}

	public void setCurrent(DNode current) {
		this.current = current;
	}

	public DGraphElement getCurrentGraphElement() {
		return currentGraphElement;
	}

	/*
	 * private DGraphFactory getDiagraphFactoryv0() { if (dGraphFactory == null)
	 * dGraphFactory = new DGraphFactory(this); return dGraphFactory; }
	 */
	/*
	 * public DGraphFactory getDiagraphFactory() { if (dGraphFactory == null) {
	 * dGraphFactory = new DGraphFactory(0); dGraphFactory.setStyle(null); //
	 * todo: implement styles } return dGraphFactory; }
	 */

	public DiagramParser() {
		super();
		clog("DiagramParser created");
	}

	protected IViewPart findView(String vid) {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().findView(vid);
	}

	@Override
	public DGraphElement parse(String from, Object o) {
		EObject eobj = null;
		if (o instanceof EObject)
			eobj = (EObject) o;
		if (eobj instanceof EList)
			parse(from + "p1", currentView, ParserState.nodes, eobj, null);
		else if (eobj instanceof EPackage)
			parse(from + "p2", currentView, ParserState.pakage, eobj, null);
		else if (o instanceof URI)
			parse(from + "p3", currentView, ParserState.uri, null, o);
		else if (eobj instanceof Diagram)
			parse(from + "p4", currentView, ParserState.diagram, eobj, null);
		else if (eobj instanceof Node)
			parse(from + "p5", currentView, ParserState.node, eobj, null);
		else if (o instanceof String)
			parse(from + "p6", currentView, ParserState.keyword, null, o);
		else if (eobj instanceof EClass)
			parse(from + "p7", currentView, ParserState.eclass, eobj, null);
		else if (eobj instanceof EAnnotation)
			parse(from + "p8", currentView, ParserState.diagraph, eobj, null);
		else
			parse(from + "p9", currentView, ParserState.unknown, null, o);
		return currentGraphElement;
	}

	private void log(String from, EObject eobject, String m) {
		if (LOG)
			clog(from + "_" + "DIDP " + m);
		if (m != null && !m.isEmpty())
			owner.log(4, m);
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	private String getGraphType(EAnnotation eAnnotation) {
		for (Map.Entry<String, String> entry : eAnnotation.getDetails()) {
			if (entry.getKey().startsWith(DiagraphKeywords.NODE))
				return DiagraphKeywords.NODE;
			else if (entry.getKey().startsWith(DiagraphKeywords.LINK))
				return DiagraphKeywords.LINK;
		}
		return DiagraphKeywords.GENERIC;
	}

	private void parse(String from, String view, ParserState parserState,
			EObject eobject, Object o) {
		String m = "";
		switch (parserState) {
		case uri:
			m = "uri:" + o.toString();
			this.owner.csvTrace("uri;" + o.toString());
			break;
		case pakage:
			EPackage pakage = (EPackage) eobject;
			m = pakage.getName() + " - " + pakage.getNsURI();
			this.owner.csvTrace("package;" + pakage.getName() + ";"
					+ pakage.getNsURI());
			break;
		case diagraph:
			// EAnnotation eAnnotation = (EAnnotation) eobject;
			currentAnnoration = (EAnnotation) eobject;
			graphType = getGraphType(currentAnnoration);
			currentEClass_ = (EClass) currentAnnoration.getEModelElement();
			// m="diagraph{"+eclzz.getName()+" is a "+graphType;
			m = currentEClass_.getName() + " is a " + graphType;
			if (graphType.equals(DiagraphKeywords.NODE))
				;
			if (graphType.equals(DiagraphKeywords.LINK))
				;
			this.owner.csvTrace("diagraph;" + currentEClass_.getName() + ";"
					+ graphType);
			break;
		case keyword:
			String det = o.toString();
			m = det;
			String[] kv = det.split("=");

			 if (kv[0].startsWith(DiagraphKeywords.PREFERENCE_OLD_NU))
					  throw new RuntimeException("keyword "+DiagraphKeywords.PREFERENCE_OLD_NU+" is obsolete");
			 else if (kv[0].startsWith(DiagraphKeywords.KREFERENCE_)//FP150512transp
					|| kv[0].equals(DiagraphKeywords.CREFERENCE_)
					|| kv[0].equals(DiagraphKeywords.AFFIXED_)) {
				String ref = kv[1];
				EReference reference = (EReference) currentEClass_
						.getEStructuralFeature(ref);
				if (reference == null) {
					m += " [view " + view + " error] no reference named "
							+ kv[1] + " for " + kv[0] + "=" + kv[1]
							+ " on EClass " + currentEClass_.getName();
				} else {
					m += ":" + reference.getEType().getName();
					this.owner.csvTrace("kw-refer;" + m);
				}
			} else
				this.owner.csvTrace("kw-other;" + m);

			if (det.startsWith(DiagraphKeywords.OPEN_DIAGRAM)) {
				String[] pv = det.split(":");
				m += "navigation to external view : " + pv[1];
				this.owner.csvTrace("nav;" + pv[1]);
			}
			break;

		case eclass:
			EClass eclaz = (EClass) eobject;
			m = "eclass:" + eclaz.getName();
			this.owner.csvTrace("eclass;" + eclaz.getName());
			getCurrentNode(from, eclaz, view);
			if (epakage == null && callerid == 0) {
				if (LOG)
					clog("csfor;" + eclaz.getEPackage());
				this.owner.csvTrace("csfor;" + eclaz.getEPackage());
				generateConcreteSyntaxForAllViews_new(eclaz.getEPackage());
			}
			// m = getClassAssociations(eclaz);
			m = "";
			break;
		case diagram:
			Diagram diag = (Diagram) eobject;
			owner.clearLog(4);
			m = "Diagram{";
			this.owner.csvTrace("diagram;");

			// state_ = "D";
			break;
		case end:
			m = "}";
			this.owner.csvTrace("end;");
			break;
		case unknown:
			m = "";
			if (!o.getClass().getSimpleName().equals("EContentsEList")) {
				m = "unknown: " + o.getClass().getName();// "unknown{";
				this.owner.csvTrace("unknown;" + o.getClass().getName());
			}
			break;

		/*
		 * case nodes: List<Node> nodes=(List) eobject; for (Node node : nodes)
		 * {
		 *
		 * } m="Nodes{"; break; case node: m = "Node{"; //state_ = "N"; Node
		 * node = (Node) eobject; break;
		 */

		default:
			throw new RuntimeException("unknown parserstate :"
					+ parserState.toString());
			// break;
		}
		log(from, eobject, m);
	}

	@Override
	public int getPhase() {
		return diagraphProvider.getPhase();
	}

	@Override
	public void init(DiagraphNotifiable owner,
			IDiagraphProvider diagraphProvider) {
		this.owner = owner;
		this.diagraphProvider = diagraphProvider;
		// this.diagraphGenerator = diagraphGenerator;
	}

	/*-----------------------------------*/

	void logDetails(EAnnotation eAnnotation) {
		EMap<String, String> details = eAnnotation.getDetails();
		Set<String> keys = details.keySet();
		for (String key : keys)
			clog("DP_DET " + "key=" + key);// + " value=" + details.get(key));
	}

	private void readDiagraphAnnotation() {
		// pakage = ep;
		if (LOG)
			clog("RDANO "
					+ (epakage == null ? " epakage==null " : epakage.getName()));
		EList<EClassifier> classes = epakage.getEClassifiers();
		for (EClassifier eClassifier : classes) {
			if (eClassifier instanceof EClass) {
				EClass eclaz = (EClass) eClassifier;
				clog("DP_EC " + eclaz.getName());
				eclaz.getEReferences();
				eclaz.getEAttributes();
				List<EAnnotation> eAnnots = eclaz.getEAnnotations();
				allAnnotations.addAll(eAnnots);
				for (EAnnotation eAnnotation : eAnnots) {
					String source = eAnnotation.getSource();
					if (source.equals(GenericConstants.ANNOT_DIAGRAPH_LITTERAL)) {
						diagraphAnnotations.add(eAnnotation);
						if (LOG)
							logDetails(eAnnotation);
					}
					/*
					 * EMap<String, String> details = eAnnotation.getDetails();
					 * Set<String> keys = details.keySet(); for (String key :
					 * keys) { clog("DRTP " + "key=" + key );//+ " value=" +
					 * details.get(key)); }
					 */
				}
			}
		}
	}

	private String[] getViews_(boolean force) {// FP121014
		try {
			if (allAnnotations.size() == 0 || force)
				allAnnotations = parseDiagraphAnnotations(epakage);// FP140113
			parseViews(allAnnotations);
			return getViewIds();
		} catch (Exception e) {
			// throw new RuntimeException("parse error 3 !!!"); //FP121111
			clog("parse error 3 !!!"); // FP121111
			String[] result = new String[1];
			result[0] = DiagraphKeywords.DIAGRAPH_DEFAULT;
			return result; // FP121116
		}
	}

	private String[] getViews() {// FP121014
		try {
			allAnnotations = parseDiagraphAnnotations(epakage); // FP140114
			parseViews(allAnnotations);
			return getViewIds();
		} catch (Exception e) {
			// throw new RuntimeException("parse error 3 !!!"); //FP121111
			clog("parse error 3 !!!"); // FP121111
			String[] result = new String[1];
			result[0] = DiagraphKeywords.DIAGRAPH_DEFAULT;
			return result; // FP121116
		}
	}

	boolean match(EAnnotation eAnnotation, String k) {
		for (String key : eAnnotation.getDetails().keySet())
			if (key.startsWith(k))
				return true;
		return false;
	}

	private void save(DGraph dGraph) {
		if (LOG)
			clog("DRTP " + "saving " + dGraph.getViewName());
		String path = ((IDiagraphGen) diagraphProvider)
				.getMetamodelResourcepath();
		path = path.substring("platform:/resource/".length());
		path = path.substring(0, path.length() - ("ecore".length() + 1));
		path = path + "_" + dGraph.getViewName() + "_.diagraph";
		String projectName = path.substring(0, path.indexOf("/"));
		URI uri = URI.createPlatformResourceURI(path, true);
		URI urii = CommonPlugin.resolve(uri);
		dGraph.getFacade1().save(urii);
		if (LOG)
			clog("DiagramParser - " + "saved; " + path);
		WorkspaceUtils.getInstance().refreshProject(projectName, false);
	}

	private static IProject getProject(String name) {
		IWorkspace ws = ResourcesPlugin.getWorkspace();
		org.eclipse.core.runtime.Path rootlocation = (Path) ws.getRoot()
				.getLocation();
		IProjectDescription description = null;
		IProject project = null;
		try {
			description = ResourcesPlugin.getWorkspace()
					.loadProjectDescription(
							new Path(rootlocation.toPortableString() + "/"
									+ name + "/.project"));
			project = ResourcesPlugin.getWorkspace().getRoot()
					.getProject(description.getName());
		} catch (CoreException e) {
			return null;
		}
		return project;
	}

	/*
	 * private static void refresh(String projectName) { try {
	 * getProject(projectName).refreshLocal(IResource.DEPTH_INFINITE, new
	 * NullProgressMonitor());
	 * Job.getJobManager().join(ResourcesPlugin.FAMILY_MANUAL_REFRESH, null);
	 * Job.getJobManager().join(ResourcesPlugin.FAMILY_AUTO_REFRESH, null); }
	 * catch (Exception e) {
	 * System.out.println("error while refreching project " + projectName); } }
	 */

	/*---------------------------------------------------*/

	@Override
	public void begin(String id) {
		if (LOG)
			clog("BEGIN " + id + " "
					+ (epakage == null ? " epakage==null " : epakage.getName()));
		setGraph(null);
		current = null;
		currentGraphElement = null;
		state = null;
		views = ViewConstants.DIAGRAPH_DEFAULT_SEP;
		currentAnnoration = null;
		graphType = null;
		currentEClass_ = null;
		diagraphAnnotations = new ArrayList<EAnnotation>();
		allAnnotations = new ArrayList<EAnnotation>();
		epakage = null;
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
	}

	@Override
	public DGraph getGraph() {
		if (!isStub() && isFunctional() && isCertified()) // a osgi based
															// architecture
															// should
															// be completed with
															// such a mechanism
			return diagraphProvider.getMockDiagraph();
		else
			return diagraphProvider.getDiagraph();
	}

	@Override
	public void setGraph(DGraph dGraph) {
		this.graph = dGraph;
		if (!isStub() && isFunctional() && isCertified())
			diagraphProvider.setMockDiagraph(dGraph);
		else
			diagraphProvider.setDiagraph(dGraph);
	}

	public DGraph getGraph(String view) {
		if (!isStub() && isFunctional() && isCertified()) // a osgi based
															// architecture
															// should
															// be completed with
															// such a mechanism
			return diagraphProvider.getDiagraph(false, view);
		else
			return diagraphProvider.getDiagraph(true, view);
	}

	public void setGraph(String view, DGraph dGraph) {
		if (!isStub() && isFunctional() && isCertified())
			diagraphProvider.setDiagraph(false, view, dGraph);
		else
			diagraphProvider.setDiagraph(true, view, dGraph);
	}

	private void getDiagraphOrCreateIfNotExists(String from_, String view) { // FP140115x
		DGraph dGraph = getGraph();
		if (dGraph == null) {
			clog(from_ + " creating a new DGraph (without a style)");
			dGraph = DiagraphFactory.eINSTANCE.createDGraph();
			//dGraph.setFacade1(new org.isoe.diagraph.factory.DGraphFactory(
			//		dGraph,this));

			dGraph.setFacade1(DGraphFactory.getInstance(dGraph,this));//FP150601azer
			dGraph.setViewName(view);
			setGraph(dGraph);
			// parse
		}
	}

	private List<DNode> getNodes(DGraph graph) { // FP140518
		// if (nodes==null){
		List<DNode> nodes = new ArrayList<DNode>();
		List<DGraphElement> elements = graph.getElements();
		for (DGraphElement element : elements) {
			if (element instanceof DNode)
				nodes.add((DNode) element);
		}
		// }
		return nodes;
	}

	private DNode getCurrentNode(String from, EClass eclaz, String view) {
		// getDiagraphFactory(); // FP130724
		getDiagraphOrCreateIfNotExists(from, view);
		if (graph != null) {
			for (DNode dnod : getNodes(getGraph())) { // FP120527
				if (dnod.getName().equals(eclaz.getName())) {
					currentGraphElement = dnod; // FP120615
					break;
				} else {
					for (DEdge dEdge : graph.getFacade1().getEdges(dnod))
						if ((dEdge instanceof DOwnedEdge)
								&& dEdge.getName().equals(eclaz.getName())) {
							currentGraphElement = dEdge; // FP120615
							break;
						}
				}
			}
		}
		String layernameNull = null;
		String navLinkNull = null; // does not matter here
		List<String> labels = new ArrayList<String>();
		current = graph.getFacade1().createNode(eclaz.getName(), labels, eclaz,
				layernameNull, navLinkNull,eclaz.isAbstract());
		return current;
	}

	@Override
	public List<EAnnotation> parseDiagraphAnnotations(EPackage pakage) {
		List<EAnnotation> result = new ArrayList<EAnnotation>(); // FP131225
		// if (LOG) clog("PDGAN "+
		// (epakage==null?" ***** should not happen ****** epakage==null ":epakage.getName()));
		epakage = pakage; // FP140114
		if (epakage == null)
			throw new RuntimeException("PDGAN should not happen  epakage==null"); // FP140114
		for (EClassifier eClassifier : epakage.getEClassifiers())
			if (eClassifier instanceof EClass)
				for (EAnnotation annot : ((EClass) eClassifier)
						.getEAnnotations())
					if (annot.getSource().equals(
							GenericConstants.ANNOT_DIAGRAPH_LITTERAL))
						result.add(annot);
		return result;
	}

	@Override
	public String[] parseViews() {
		String s = "";
		for (EAnnotation eAnnotation : parseDiagraphAnnotations(epakage))
			// FP140114
			for (Map.Entry<String, String> entry : eAnnotation.getDetails()) {
				if (entry.getKey().startsWith(ViewConstants.VIEW_EQ)) {
					String[] pars = entry.getKey().split("=");
					if (!s.contains(pars[1] + ";"))
						s += pars[1] + ";";
				}
			}
		if (s.isEmpty())
			s = ViewConstants.DIAGRAPH_DEFAULT_SEP;
		return s.split(";");
	}

	@Override
	public String[] getViewIds() {
		if (views.isEmpty())
			views = ViewConstants.DIAGRAPH_DEFAULT_SEP;
		return views.split(";");
	}

	@Override
	public String[] parseViews(List<EAnnotation> annotations) {
		for (EAnnotation eAnnotation : annotations)
			for (Entry<String, String> entry : eAnnotation.getDetails()) {
				if (entry.getKey().startsWith(ViewConstants.VIEW_EQ)) {
					String[] pv = entry.getKey().split("=");
					if (!views.contains(pv[1] + ";"))
						views += pv[1] + ";";
				}
			}
		return views.split(";");
	}

	// if (! isFunctional && isStub) => would be an unusable component

	@Override
	public boolean isStub() {
		return false;
	}

	@Override
	public boolean isFunctional() { // does what it should do
		return true;
	}

	@Override
	public boolean isCertified() { // can use it in operation
		return false;
	}

	@Override
	public void checkAnnotations(String layer) {

	}

	public boolean isDefaultView(EAnnotation anot) {
		for (Map.Entry<String, String> entry : anot.getDetails())
			if (entry.getKey().startsWith(DiagraphKeywords.VIEW_EQU))
				return false;
		return true;
	}

	public boolean isView(String viewid, EAnnotation anot) { // FP131208
		if (!anot.getSource().equals(DiagraphKeywords.DIAGRAPH_LITTERAL))
			return false;
		if (viewid == null || viewid.equals(DiagraphKeywords.DIAGRAPH_DEFAULT)
				|| viewid.isEmpty())
			return isDefaultView(anot);
		for (Map.Entry<String, String> entry : anot.getDetails())
			if (entry.getKey().equals(DiagraphKeywords.VIEW_EQU + viewid))
				return true;
		return false;
	}

	public void generateConcreteSyntaxForAllViews_old(EPackage epackage) {
		epakage = epackage;
		if (LOG)
			clog("DiagramParser - generateConcreteSyntaxForAllViews "
					+ epakage.getName());
		// getDiagraphFactory();
		readDiagraphAnnotation();
		for (String view : getViews()) {
			DGraph graph = DiagraphFactory.eINSTANCE.createDGraph();
			graph.setViewName(view);
			//graph.setFacade1(new DGraphFactory(graph,this));
			graph.setFacade1(DGraphFactory.getInstance(graph, this));//FP150601azer

			for (EAnnotation eAnnotation : diagraphAnnotations) {
				EClass owner = (EClass) eAnnotation.eContainer();
				if (match(eAnnotation, DiagraphKeywords.NODE)) {
					DNode nod = DiagraphFactory.eINSTANCE.createDNode();
					nod.setName(owner.getName());
					nod.setSemanticRole(owner);
					graph.getElements().add(nod);
					nod.setGraph(graph);
				} else if (match(eAnnotation, DiagraphKeywords.LINK)) {
					DLabeledEdge link = DiagraphFactory.eINSTANCE
							.createDLabeledEdge();
					link.setName(owner.getName());
					link.setSemanticRole(owner);
				}
			}
			save(graph);
		}
	}

	public void generateConcreteSyntaxForAllViews_new(EPackage epackage) { // FP140603modif
		epakage = epackage;
		if (LOG)
			clog("DiagramParser - generateConcreteSyntaxForAllViews "
					+ epakage.getName());
		// getDiagraphFactory();
		readDiagraphAnnotation();
		for (String view : getViews()) {
			DGraph graph = DiagraphFactory.eINSTANCE.createDGraph();
			graph.setViewName(view);
			//graph.setFacade1(new DGraphFactory(graph,this));

			graph.setFacade1(DGraphFactory.getInstance(graph,this));//FP150601azer

			for (EAnnotation eAnnotation : diagraphAnnotations) {
				if (isView(view, eAnnotation)) {
					EClass owner = (EClass) eAnnotation.eContainer();
					if (match(eAnnotation, DiagraphKeywords.NODE)) {
						DNode nod = DiagraphFactory.eINSTANCE.createDNode();
						nod.setName(owner.getName());
						nod.setSemanticRole(owner);
						graph.getElements().add(nod);
						nod.setGraph(graph);
					} else if (match(eAnnotation, DiagraphKeywords.LINK)) {
						DLabeledEdge link = DiagraphFactory.eINSTANCE
								.createDLabeledEdge();
						link.setName(owner.getName());
						link.setSemanticRole(owner);
					}
				}
			}
			save(graph);
		}
	}

	public void generateConcreteSyntaxForAllViews_AndLog(EPackage ep) {
		epakage = ep;
		if (LOG)
			clog("GCSFAVAL "
					+ (epakage == null ? " epakage==null " : epakage.getName()));
		// getDiagraphFactory();
		readDiagraphAnnotation();
		String[] viewsar = getViews();// s(true);
		for (int i = 0; i < viewsar.length; i++) {
			DGraph graph = DiagraphFactory.eINSTANCE.createDGraph();
			// graph.setViewName(view);
			// DGraph graph = dGraphFactory.createModel();//
			// DiagraphFactory.eINSTANCE.createDGraph();
			graph.setViewName(viewsar[i]);
			//graph.setFacade1(new DGraphFactory(graph,this));
			graph.setFacade1(DGraphFactory.getInstance(graph,this)); //FP150601azer

			for (EAnnotation eAnnotation : diagraphAnnotations) {
				EClass owner = (EClass) eAnnotation.eContainer();
				clog("DRTP " + owner.getClass().getName());
				if (match(eAnnotation, DiagraphKeywords.NODE)) {
					DNode nod = DiagraphFactory.eINSTANCE.createDNode();
					nod.setName(owner.getName());
					nod.setSemanticRole(owner); // FP130622
					graph.getElements().add(nod);
					nod.setGraph(graph);
				} else if (match(eAnnotation, DiagraphKeywords.LINK)) {
					DLabeledEdge link = DiagraphFactory.eINSTANCE
							.createDLabeledEdge();
					link.setName(owner.getName());
					link.setSemanticRole(owner);// FP130622
					// voir code déjà existant
					// nod.getEdges().add(link);
					// link.setSource(nod)
				}
				// TODO construire le modèle après analyse des annotations
			}
			save(graph);
		}

	}

	@Override
	public Object callBack(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

}
