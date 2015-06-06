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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecoretools.legacy.diagram.edit.commands.EAnnotationCreateCommand;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramCommandStack;
import org.eclipse.gmf.runtime.diagram.ui.services.layout.ILayoutNode;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.SetValueCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.FillStyle;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.Style;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.notation.impl.ShapeImpl;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.isoe.diagraph.generic.GenericConstants;
import org.isoe.diagraph.lang.DiagraphKeywords;
import org.isoe.diagraph.views.ViewConstants;
import org.isoe.fwk.core.DParams;
import org.isoe.diastyle.lang.StyleConstants;


/**
 *
 * @author fpfister
 *
 */
import org.isoe.diagraph.controler.IDiagraphControler;  //FP140707refactored
public class DiagraphLayout {

	private static final boolean LOG = DParams.DiagraphLayout_LOG;
	private static final boolean CHECK_ANNOTATION_UNICITY = false;  //FP140217
	private List<AnnotatedDiagraphClass> annotatedClasses = new ArrayList<AnnotatedDiagraphClass>();
	private String[] layers;
	private List layouts;
	private boolean allLayers;
	private IDiagraphControler controler;

	private int findLayer(String v) {
		for (int i = 0; i < layers.length; i++)
			if (layers[i].equals(v))
				return i;
		return -1;
	}

	private void snippet2(ShapeImpl si) {
		final Shape shape = (Shape) si;
		final int myColor = 10265827; // red
		TransactionalEditingDomain ted = TransactionUtil
				.getEditingDomain(shape);
		ted.getCommandStack().execute(new RecordingCommand(ted) {
			protected void doExecute() {
				shape.setFillColor(myColor);
			}
		});
	}

	/**
	 *
	 * @param selectedPart
	 * @param elementToEdit
	 * @param f1
	 * @param name
	 * @see http://dev.eclipse.org/newslists/news.eclipse.technology.gmf/msg02076.html
	 */
	void snippet1(DiagramCommandStack commandStack, EditPart selectedPart,
			final ENamedElement elementToEdit, final EStructuralFeature f1,
			final EStructuralFeature f2, String name) {
		TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) selectedPart)
				.getEditingDomain();

		SetRequest setRequest = new SetRequest(editingDomain, elementToEdit,
				f1, name);
		setRequest.setParameter("oldValue", elementToEdit.getName());

		SetValueCommand operation = new SetValueCommand(setRequest) {
			@Override
			protected IStatus doUndo(IProgressMonitor monitor, IAdaptable info) {
				elementToEdit.eSet(f1,
						this.getRequest().getParameter("oldValue"));
				return Status.OK_STATUS;
			}

			@Override
			public boolean canUndo() {
				return true;
			}

			@Override
			protected IStatus doRedo(IProgressMonitor monitor, IAdaptable info) {
				f1.eSet(f2, ((SetRequest) this.getRequest()).getValue());
				return Status.OK_STATUS;
			}

			@Override
			public boolean canRedo() {
				return true;
			}
		};
		/*
		 * try { //CommandStack stack =
		 * viewPart.getViewer().getEditDomain().getCommandStack();
		 * //CompoundCommand cmd = new CompoundCommand(""); //cmd.add(new
		 * ICommandProxy(sddss)); //commandStack.execute(new
		 * EtoolsProxyCommand(operation)); } catch (ExecutionException e) { }
		 */
	}

	private EditElementCommand createAnnotationCommand(EAnnotation ean) {
		IElementType annotationElementType = ElementTypeRegistry.getInstance()
				.getType("org.eclipse.emf.ecoretools.legacy.diagram.EAnnotation_1003");
		CreateElementRequest req = new CreateElementRequest(
				TransactionUtil.getEditingDomain(ean), ean,
				annotationElementType);
		return new EAnnotationCreateCommand(req);
	}

	private CommandResult executeCmd(Command cmd)
			throws org.eclipse.core.commands.ExecutionException {
		if (cmd.canExecute()) {
			ICommand icmd = DiagramCommandStack.getICommand(cmd);
			icmd.execute(new NullProgressMonitor(), null);
			if (!(icmd.getCommandResult().getStatus().isOK())) {
				return icmd.getCommandResult();
			}
		}
		return null;
	}

	private AnnotatedDiagraphClass findADC(EAnnotation ean) {
		for (AnnotatedDiagraphClass adc : annotatedClasses)
			if (adc.getEAnnotation() == ean)
				return adc;
		return null;
	}

	private List<AnnotatedDiagraphClass> getADC(EAnnotation ean) {
		if (!(ean.eContainer() instanceof EClass))
			return null;
		List<AnnotatedDiagraphClass> l = new ArrayList<AnnotatedDiagraphClass>();
		EClass eclass = (EClass) ean.eContainer();
		for (AnnotatedDiagraphClass adc : annotatedClasses) {
			String annotationView = parseView(ean);
			if (adc.getEClass().getName().equals(eclass.getName())
					&& (adc.getView().equals(annotationView)))
				l.add(adc);
		}
		return l;
	}

	private AnnotatedDiagraphClass findADCForOtherView(Node node) {
		for (AnnotatedDiagraphClass adc : annotatedClasses)
			if (!adc.isCurrentView() && adc.getAnnotationNode() == node)
				return adc;
		return null;
	}

	private void logAnnotationElement(Node node) {
		EObject eobj = node.getElement();
		EAnnotation eAnnotation = (EAnnotation) eobj;
		EModelElement eModelElement = eAnnotation.getEModelElement();
		if (eModelElement instanceof EClass) {
			EClass eClass = (org.eclipse.emf.ecore.EClass) eModelElement;
			clog(eClass.getName());
		}
	}

	private void clog___(String log) {
		if (LOG) {
			if (log != null)
				System.out.println(log);
			else
				System.out.println("null");
		}
	}

	public IDiagraphControler getControler() { //FP140630
		if (controler == null){
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			IViewPart view = page.findView(GenericConstants.DIAGRAPH_BASIC_CONTROLER_ID_);//FP130421 DIAGRAPH_VIEW_ID);
			controler = (IDiagraphControler) view;
		}
		return controler;
	}


	private void clog(String mesg) {
		if (LOG){ // && !silent
			if (mesg == null)
				mesg="null";
			if (DParams.LOG_ON_CONSOLE)
				getControler().logConsole(mesg);
			else
			   System.out.println( mesg);
		}
	}


	private String log2(EAnnotation an) { // FP120711
		if (an == null)
			return "null";
		EModelElement me = an.getEModelElement();
		EClass eclaz = (EClass) me;
		String pref = eclaz.getName() + " - " + "[";
		String log = "";
		EMap<String, String> details = an.getDetails();
		for (Entry<String, String> entry : details)
			log += entry.getKey() + ";";
		if (!keyExists(an, "view="))
			log = "(" + parseView(an) + ");" + log;
		log = log.substring(0, log.length() - 1);
		pref = pref + log + "]";
		return pref;
	}

	private void addAnnotatedDiagraphClass(EClass eclass,
			Bounds layoutConstraint, int height, Node semanticNode,
			boolean iscurrentView, String viewName) { // int height,

		if (LOG ) {
			clog("addAnnotatedDiagraphClass:" + viewName + " - "
					+ eclass.getName()
					+ (iscurrentView ? " & current" : " *** & not current"));
		}
		annotatedClasses
				.add(new AnnotatedDiagraphClass(eclass, layoutConstraint,
						height, semanticNode, viewName, iscurrentView));
	}

	private void clear() {
		annotatedClasses.clear();
	}

	private List<EAnnotation> getDiagraphAnnotations(EClass eClass) { // FP121010
		List<EAnnotation> result = new ArrayList<EAnnotation>();
		EList<EAnnotation> eas = eClass.getEAnnotations();
		for (EAnnotation eAnnotation : eas) {
			if (eAnnotation.getSource().equals(
					DiagraphKeywords.ANNOT_DIAGRAPH_LITTERAL))
				result.add(eAnnotation);
		}
		return result;
	}

	private String parseView(EAnnotation eAnnotation) {
		for (Map.Entry<String, String> entry : eAnnotation.getDetails())
			if (entry.getKey().startsWith(DiagraphKeywords.VIEW_EQ))
				return entry.getKey().split("=")[1];
		return ViewConstants.DIAGRAPH_DEFAULT;
	}

	private void registerDiagraphAnnotationsForEClass(String currentLayer,
			EClass eclass, Node node, int height) {

		/*
		 * List<Style> styles = node.getStyles(); for (Style style : styles) {
		 * if (style instanceof FillStyle) changeColor((FillStyle) style,
		 * 10265827); }
		 */

		// if (node instanceof FillStyle)
		// changeColor((FillStyle) node, 10265827);

		if (eclass == null)
			return;

		String classname = eclass.getName();
		List<EAnnotation> eas = getDiagraphAnnotations(eclass);
		for (EAnnotation ea : eas) {
			if (LOG ) {
				clog("registerDiagraphClasses " + classname);
				clog(log2(ea));
			}
			EMap<String, String> details = ea.getDetails();
			String viewid = parseView(ea);
			if (currentLayer.equals(viewid)) {
				addAnnotatedDiagraphClass(eclass,
						(Bounds) node.getLayoutConstraint(), height, node,
						true, viewid);// layout.getHeight(),
			} else
				addAnnotatedDiagraphClass(eclass,
						(Bounds) node.getLayoutConstraint(), height, node,
						false, viewid); // layout.getHeight(),
		}
	}

	private boolean keyExists(EAnnotation ea, String key) {
		for (Entry<String, String> entry : ea.getDetails())
			if (entry.getKey().contains(key))
				return true;
		return false;
	}

	private AnnotatedDiagraphClass link(AnnotatedDiagraphClass adc,
			EClass eClass, EAnnotation annotation, Bounds bounds,
			Node annotationNode, String viewid, boolean current) {
		adc.setEAnnotation(annotation);
		adc.setEAnnotationBounds(bounds);
		adc.setAnnotationNode(annotationNode);
		boolean adccurrent = adc.isCurrentView();
		if (adccurrent != current) {
			if (LOG ) {
				clog(adc.toString());
				clog(log2(annotation));
			}
		}
		adc.setCurrentView(current);
		// clog(adc.toString());
		return adc;
	}

	private void linkForOtherViewForEAnnotation(String currentLayer,
			EAnnotation ean, Node node) {
		if (ean == null)
			return;
		String viewid = parseView(ean);
		EClass ecl = (EClass) (ean).eContainer();
		if (LOG)
		   clog(log2(ean));

		if (!viewid.equals(currentLayer)) {
			// AnnotatedDiagraphClass aecl = findADC(ean);
			List<AnnotatedDiagraphClass> adcs = getADC(ean);
			if (adcs != null)
				for (AnnotatedDiagraphClass aecl : adcs) {
					link(aecl, ecl, ean, ((Bounds) node.getLayoutConstraint()),
							node, viewid, false);
				}

		}

	}

	private void linkForCurrentViewForEAnnotation(String currentLayer,
			EAnnotation ean, Node node) {
		if (ean == null)
			return;
		String viewid = parseView(ean);
		EClass ecl = (EClass) (ean).eContainer();
		if (LOG)
			   clog(log2(ean));
		if (viewid.equals(currentLayer)) {
			List<AnnotatedDiagraphClass> adcs = getADC(ean);
			if (adcs != null) {
				//FP140217
				if (CHECK_ANNOTATION_UNICITY && adcs.size() > 1)
					throw new RuntimeException(
							((ENamedElement) ean.getEModelElement()).getName() + // FP121116
									": multiple diagraph annotations found: only one diagraph annotation is possible for an EClass per layer !!!");


				for (AnnotatedDiagraphClass aecl : adcs) {
					AnnotatedDiagraphClass adc = link(aecl, ecl, ean,
							((Bounds) node.getLayoutConstraint()), node,
							viewid, true);
				}
			}
		}
		// }
	}

	private EAnnotation getAnnotation(Node node) {
		if (!(node.getElement() instanceof EAnnotation))
			return null;
		EAnnotation eAnnotation = (EAnnotation) node.getElement();
		if ((eAnnotation.getEModelElement() instanceof EClass)
				&& eAnnotation.getSource().equals(
						DiagraphKeywords.ANNOT_DIAGRAPH_LITTERAL)) {
			return eAnnotation;
		}
		return null;
	}

	private EClass adaptEClass(Object layout) {
		EObject el = adaptNode(layout).getElement();
		if (el != null && el instanceof EClass)
			return (EClass) el;
		return null;
	}

	private EAnnotation adaptEAnnotation(Object layout) {
		return getAnnotation(adaptNode(layout));
	}

	private Node adaptNode(Object layout) {
		return ((ILayoutNode) layout).getNode();
	}

	private void prepareLayout(String currentLayer, final List layouts) {
		for (Object layout : layouts) {
			registerDiagraphAnnotationsForEClass(currentLayer,
					adaptEClass(layout), ((ILayoutNode) layout).getNode(),
					((ILayoutNode) layout).getHeight());
		}

		for (Object layout : layouts) {
			EAnnotation ean = adaptEAnnotation(layout);
			if (ean != null)
				linkForCurrentViewForEAnnotation(currentLayer,
						adaptEAnnotation(layout), adaptNode(layout));
		}

		for (Object layout : layouts) {
			EAnnotation ean = adaptEAnnotation(layout);
			if (ean != null)
				linkForOtherViewForEAnnotation(currentLayer,
						adaptEAnnotation(layout), adaptNode(layout));
		}
	}

	private void alignCurrentViewAnnotations() {
		for (AnnotatedDiagraphClass adc : annotatedClasses) {// currentViewADC)
																// // {
			Node node = adc.getAnnotationNode();
			String v = adc.getView();
			int layer = findLayer(v);
			String id = v + "  " + adc.getEClass().getName();
			if (LOG )
				clog("alignCurrentViewAnnotations: " + id);
			if (adc.isCurrentView() && node != null) { // FP121029xxx
				EAnnotation diagraphAnnotation = adc.getEAnnotation();
				if (diagraphAnnotation != null) {
					alignAnnotation(layer, diagraphAnnotation, node,
							(Bounds) node.getLayoutConstraint());
				}
			}
		}
	}

	private void hideNotCurrentViewAnnotations() {
		for (AnnotatedDiagraphClass adc : annotatedClasses) {// otherViewsADC) {
			Node node = adc.getAnnotationNode();
			if (!adc.isCurrentView() && node != null) {
				EAnnotation diagraphAnnotation = adc.getEAnnotation();
				if (diagraphAnnotation != null) {
					Bounds align = hideAnnotations(diagraphAnnotation, node,
							(Bounds) node.getLayoutConstraint());
					if (align != null)
						node.setLayoutConstraint(align);
					else
						clog("no alignement for "
								+ node.getElement().toString());
				}

			}
		}
	}

	public List<AnnotatedDiagraphClass> layout_(final List layouts,
			String viewName, String[] allViews) {
		int n = 1, m = 1;
		clog("layout view " + viewName);
		String currentLayer = viewName != null ? viewName
				: ViewConstants.DIAGRAPH_DEFAULT;
		allLayers = false;//(currentLayer.equals(DiagraphKeywords.DIAGRAPH_ALL_VIEW_LITTERAL_));
		n = 2;
		this.layouts = layouts;
		this.layers = allViews;
		if (LOG ) {
			clog("{all annotations: ");
			for (Object layout : layouts) {
				m++;

				EAnnotation ean = adaptEAnnotation(layout);
				if (ean != null)
					clog(log2(ean));
			}
			clog("all annotations}");
		}

		if (allLayers) {
			n = 3;
			for (String layer : allViews)
				doLayout(layer);
		} else {
			n = 4;
			doLayout(currentLayer);
			hideNotCurrentViewAnnotations();
		}
		n = 5;
		bringClassesToFront();
		return null;
	}

	public List<AnnotatedDiagraphClass> doLayout(String currentLayer) {
		try {
			clear();
			prepareLayout(currentLayer, layouts);
			alignCurrentViewAnnotations();
			return annotatedClasses;
		} catch (Exception e) {
			throw new RuntimeException("(2) error during layout for layer "
					+ currentLayer + " " + e.getMessage());
		}

	}

	private void bringClassesToFront() {
		for (AnnotatedDiagraphClass adc : annotatedClasses)
			bringToFront(adc);
	}

	void check(Node node, EAnnotation eanot) {
		if (node.getElement() instanceof EClass) {
			EClass eclaz = (EClass) node.getElement();
			for (Iterator it = eclaz.eAllContents(); it.hasNext();) {
				EObject element = (EObject) it.next();
				if (element instanceof EAnnotation) {
					EAnnotation other_ = (EAnnotation) element;
					if (LOG  && other_ != eanot) {
						clog("DL8765: " + other_.getSource());
						clog(log2(other_) + " <---> "
								+ log2(eanot));
					}

				}
			}
		}
	}

	boolean done = false;

	private void changeColor(Diagram diagram) {
		List<View> pch = diagram.getChildren();
		for (View view : pch) {
			if (view instanceof Shape) {
				changeColor((Shape) view, 10265827);
				done = true;
			}
		}
	}

	void changeColor(final FillStyle styl, final int color) {
		TransactionalEditingDomain ted = TransactionUtil.getEditingDomain(styl);
		ted.getCommandStack().execute(new RecordingCommand(ted) {
			@Override
			protected void doExecute() {
				styl.setFillColor(color);
			}
		});
	}

	void changeAllDiagraphAnnotationsColor(Diagram diagram, int color) {
		List<View> views = diagram.getChildren();
		for (View view : views) {
			if (view instanceof Node) {
				Node node = (Node) view;
				if (node.getElement() instanceof EAnnotation) {
					EAnnotation eAnnotation = (EAnnotation) node.getElement();
					if (eAnnotation.getSource().equals("diagraph")) {

						if (LOG){
							  String nam="";
							  if (node.getElement() instanceof ENamedElement){
								ENamedElement ne = (ENamedElement) node.getElement();
								nam=ne.getName();
							  } else
								  nam=node.getElement().toString();
							   clog("DLA changeColor" +nam+"="+color);
							}


						List<Style> styl = node.getStyles();
						for (Style style : styl) {
							if (style instanceof FillStyle)
								changeColor((FillStyle) style, color);
						}
					}
				}
			}
		}
	}

	private void bringToFront(AnnotatedDiagraphClass aClass) {
		Node snode = aClass.getSemanticNode();
		Node anode = aClass.getAnnotationNode();
		Diagram diagram = (Diagram) snode.eContainer();
		List<View> pch = diagram.getPersistedChildren();
		int semanticIndex = pch.indexOf(snode);
		int annotationIndex = pch.indexOf(anode);
		if (semanticIndex < annotationIndex) {
			pch.remove(snode);
			pch.add(snode);
		}
	}

	void changeColor(final Shape shape, final int color) {// 10265827
		TransactionalEditingDomain ted = TransactionUtil
				.getEditingDomain(shape);
		ted.getCommandStack().execute(new RecordingCommand(ted) {
			@Override
			protected void doExecute() {
				shape.setFillColor(color);
			}
		});
	}

	private void bringToFront_snippet(AnnotatedDiagraphClass aClass) {
		Node snode = aClass.getSemanticNode();
		Node anode = aClass.getAnnotationNode();

		EAnnotation eanot = aClass.getEAnnotation();
		// check(snode,eanot);
		View view = (View) snode.eContainer();
		org.eclipse.gmf.runtime.notation.Diagram diagram = (org.eclipse.gmf.runtime.notation.Diagram) snode
				.eContainer();

		// EditPart ep = diagram.getEditPartRegistry().get(view);

		List<org.eclipse.gmf.runtime.notation.Edge> edges = diagram.getEdges();
		for (Edge edge : edges) {
			View w = edge.getSource();

		}
		List<View> ch = diagram.getChildren();

		List<View> pch = view.getPersistedChildren();
		int semanticIndex = pch.indexOf(snode);

		List<View> sch = snode.getChildren();
		for (View sc : sch) {

			if (sc.getElement() instanceof EClass) {
				EClass selment = ((EClass) sc.getElement());
				EAnnotation aean = selment.getEAnnotation("diagraph");
				View annotationv = findAnnotationView(selment, diagram);
				if (annotationv == null && aean != null) {
					ICommand cc = createAnnotationCommand(aean);
					// executeCmd((Command) cc); //TODO
				}
				List<View> s = sc.getChildren();
				for (View v : s) {
					if (v.getElement() instanceof EAttribute) {

					}
				}
			}
		}

		int annotationIndex = pch.indexOf(anode);

		if (semanticIndex < annotationIndex) {
			// view.getPersistedChildren().set(annotationIndex, snode);
			// view.getPersistedChildren().set(semanticIndex, anode);
			view.getPersistedChildren().remove(snode);
			view.getPersistedChildren().add(snode);
		}
	}

	private View findAnnotationView(EClass eClass, Diagram diagram) {
		List<View> ch = diagram.getChildren();
		for (View view : ch) {
			if (view.getElement() instanceof EAnnotation) {
				EAnnotation ean = (EAnnotation) view.getElement();
				if (ean.eContainer() == eClass)
					return view;
			}
		}
		return null;
	}

	private Bounds getHiddenBehindClassAnnotationBounds(Node nod) {
		AnnotatedDiagraphClass adc = findADCForOtherView(nod);
		if (adc != null) {
			Bounds anotbounds = (Bounds) nod.getLayoutConstraint();
			adc.hideBehindClass(anotbounds);
			return anotbounds;
		}
		return null;
	}

	private Bounds hideAnnotations(EAnnotation diagraphAnnotation, Node node,
			Bounds bounds) {
		if (node.getElement() != null
				&& node.getElement() == diagraphAnnotation) {
			// bounds.setX(10);
			// bounds.setY(1500);
			// bounds.setX(10);
			// bounds.setY(bounds.getY() + 1000);
			bounds.setY(1000);
			return bounds;
		}
		return null;
	}

	private void alignAnnotation(int layer, EAnnotation diagraphAnnotation,
			Node node, Bounds layoutConstraint) {
		if (node.getElement() != null
				&& node.getElement() == diagraphAnnotation) {
			getRedimensionedAnnotationBound(node, layer);

		}
	}

	private void getRedimensionedAnnotationBound(Node nod, int layer) {
		EAnnotation ean = getAnnotation(nod);
		AnnotatedDiagraphClass adc = findADC(ean);
		Bounds anotbounds = (Bounds) nod.getLayoutConstraint();
		adc.redimAnnotation(nod, anotbounds, layer, allLayers);
	}

}
