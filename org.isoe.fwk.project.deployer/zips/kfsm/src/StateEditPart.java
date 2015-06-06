package lang.m2.kfsm.diagram_default_root.edit.parts;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lang.m2.kfsm.State;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ConstrainedToolbarLayoutEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DragDropEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.tooling.runtime.edit.policies.reparent.CreationEditPolicyWithCustomReparent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;

/**
 * @generated
 */
public class StateEditPart extends ShapeNodeEditPart {
	//AZ123
	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 1001;

	/**
	 * @generated
	 */
	protected IFigure contentPane;

	/**
	 * @generated
	 */
	protected IFigure primaryShape;

	/**
	 * @generated
	 */
	protected int foobar;

	/**
	 * @generated
	 */
	public StateEditPart(View view) {
		super(view);
	}

	/**
	 * @generated
	 */
	protected void createDefaultEditPolicies() {
		installEditPolicy(
				EditPolicyRoles.CREATION_ROLE,
				new CreationEditPolicyWithCustomReparent(
						lang.m2.kfsm.diagram_default_root.part.KfsmVisualIDRegistry.TYPED_INSTANCE));
		super.createDefaultEditPolicies();
		installEditPolicy(
				EditPolicyRoles.SEMANTIC_ROLE,
				new lang.m2.kfsm.diagram_default_root.edit.policies.StateItemSemanticEditPolicy());
		installEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE,
				new DragDropEditPolicy());
		installEditPolicy(
				EditPolicyRoles.CANONICAL_ROLE,
				new lang.m2.kfsm.diagram_default_root.edit.policies.StateCanonicalEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
		// XXX need an SCR to runtime to have another abstract superclass that would let children add reasonable editpolicies
		// removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.CONNECTION_HANDLES_ROLE);
	}

	/**
	 * @generated
	 */
	protected LayoutEditPolicy createLayoutEditPolicy() {

		ConstrainedToolbarLayoutEditPolicy lep = new ConstrainedToolbarLayoutEditPolicy() {

			protected EditPolicy createChildEditPolicy(EditPart child) {
				if (child.getEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE) == null) {
					if (child instanceof ITextAwareEditPart) {
						return new lang.m2.kfsm.diagram_default_root.edit.policies.KfsmTextSelectionEditPolicy();
					}
				}
				return super.createChildEditPolicy(child);
			}
		};
		return lep;
	}

	

	
	
	/**
	 * (DIAGRAPH uses Iaml code) Jevon modification: we extend createNodeShape to use our extended class.
	 * @generated
	 */
	protected IFigure createNodeShape() {
		StateFigure figure = new ExtendedStateFigure();
		return primaryShape = figure;
	}

	/**
	 * @generated
	 */
	public StateFigure getPrimaryShape() {
		return (StateFigure) primaryShape;
	}

	/**
	 * (DIAGRAPH uses Iaml code) Extends the normal generated Figure class with one that can handle notification events, to render changes in the model.
	 * 
	 * Unfortunately, GMF generates the Viewmap as part of the .gmfgen directly, so we cannot modify the generated code here; we have to extend it in this way. Its not ideal.
	 * @generated
	 */
	public class ExtendedStateFigure extends StateFigure {

		/**
		 * To be removed when GMF graph will support multiline labels
		 */
		/**
		 * (DIAGRAPH MODIFIED 121104)
		 * @generated
		 */
		public void add(IFigure figure, Object constraint, int index) {
			if (figure instanceof WrappingLabel) {
				((WrappingLabel) figure).setTextWrap(true);
			}
			super.add(figure, constraint, index);
		}

		/**
		 * (DIAGRAPH uses Iaml code)
		 * @generated
		 */
		private EObject resolvedObject;

		/**
		 * (DIAGRAPH uses Iaml code)
		 * @generated
		 */
		public ExtendedStateFigure() {
			// initialise parent as normal
			super();

			// find the parent object
			resolvedObject = resolveSemanticElement();

			// refresh any parent labels
			refreshLabels();

			// add a notification listener
			// based on http://dev.eclipse.org/newslists/news.eclipse.modeling.gmf/msg12297.html

			/*
			org.eclipse.emf.transaction.NotificationFilter nf = org.eclipse.emf.transaction.NotificationFilter.createFeatureFilter(
			    	org.openiaml.model.model.ModelPackage.eINSTANCE.getNamedElement_Name()
			    ).or(org.eclipse.emf.transaction.NotificationFilter.createFeatureFilter(
			    	org.eclipse.emf.ecore.EcorePackage.eINSTANCE.getENamedElement_Name()
			    ));


			org.eclipse.emf.transaction.ResourceSetListener rsl = new org.eclipse.emf.transaction.ResourceSetListenerImpl(nf) {
				EXPAND xpt::Common::generatedMemberComment('(DIAGRAPH uses Iaml code)')
				@Override
				public void resourceSetChanged(org.eclipse.emf.transaction.ResourceSetChangeEvent event) {
					refreshLabels();
				}
			};

			getEditingDomain().addResourceSetListener(rsl);

			 */

		}

		/**
		 * (DIAGRAPH uses Iaml code)
		 * @generated
		 */
		public void refreshLabels() {
			if (resolvedObject != null) {
				// labels from parent
				if (resolvedObject.eContainer() != null) {
					EObject parent = resolvedObject.eContainer();

					// permit parent labels from ENamedElements as well
					/*
					String parentName = null;
					if (parent instanceof org.openiaml.model.model.NamedElement) {
						parentName = "Container: " + ((org.openiaml.model.model.NamedElement) parent).getName();
					} else

					if (parent instanceof org.eclipse.emf.ecore.ENamedElement) {
						parentName = "Container: " + ((org.eclipse.emf.ecore.ENamedElement) parent).getName();
					}
					 */

					String containmentName = resolvedObject
							.eContainingFeature() == null ? "" : resolvedObject
							.eContainingFeature().getName();

					/* parent features */
					//{template-parents}

					/* containment features */
					//{template-containments}
				}

				// other labels from the current object

				/* type features */
				//{template-types}

				/* eType features */
				//{template-etypes}

				/* stereotype figures */
				//{template-stereotypes}
			}
		}

	}

	//AZ124	
	/**
	 * @generated
	 */
	protected boolean addFixedChild(EditPart childEditPart) {
		if (childEditPart instanceof lang.m2.kfsm.diagram_default_root.edit.parts.StateNameEditPart) {
			((lang.m2.kfsm.diagram_default_root.edit.parts.StateNameEditPart) childEditPart)
					.setLabel(getPrimaryShape().getFigureStateNameFigure());
			return true;
		}
		return false;
	}

	/**
	 * @generated
	 */
	protected boolean removeFixedChild(EditPart childEditPart) {
		if (childEditPart instanceof lang.m2.kfsm.diagram_default_root.edit.parts.StateNameEditPart) {
			return true;
		}
		return false;
	}

	/**
	 * @generated
	 */
	protected void addChildVisual(EditPart childEditPart, int index) {
		if (addFixedChild(childEditPart)) {
			return;
		}
		super.addChildVisual(childEditPart, -1);
	}

	/**
	 * @generated
	 */
	protected void removeChildVisual(EditPart childEditPart) {
		if (removeFixedChild(childEditPart)) {
			return;
		}
		super.removeChildVisual(childEditPart);
	}

	/**
	 * @generated
	 */
	protected IFigure getContentPaneFor(IGraphicalEditPart editPart) {
		return getContentPane();
	}

	//AZ125

	/**
	 * DIAGRAPH modified 4
	 * @generated
	 */
	protected NodeFigure createNodePlate() {
		DefaultSizeNodeFigure result = new DefaultSizeNodeFigure(20, 20);

		/*120702 120704a

		 self.getDiagram().isPixelMapMode()
		 AAA-- EXPAND xpt::Common::copyright FOR editorGen: 	  BBB-- getDiagram().domainDiagramElement.genPackage.prefix: Kfsm  ////
		 CCC-- getDiagram().domainDiagramElement.ecoreClass.name: FSM  ////
		 DDD-- getDiagram().domainDiagramElement.ecoreClass.references [org.eclipse.emf.ecore.impl.EReferenceImpl@10223e63 (name: ownedState) (ordered: true, unique: true, lowerBound: 0, upperBound: -1) (changeable: true, volatile: false, transient: false, defaultValueLiteral: null, unsettable: false, derived: false) (containment: true, resolveProxies: true), org.eclipse.emf.ecore.impl.EReferenceImpl@382ddddb (name: initialState) (ordered: true, unique: true, lowerBound: 1, upperBound: 1) (changeable: true, volatile: false, transient: false, defaultValueLiteral: null, unsettable: false, derived: false) (containment: false, resolveProxies: true), org.eclipse.emf.ecore.impl.EReferenceImpl@4b4071ad (name: finalState) (ordered: true, unique: true, lowerBound: 1, upperBound: -1) (changeable: true, volatile: false, transient: false, defaultValueLiteral: null, unsettable: false, derived: false) (containment: false, resolveProxies: true)]  ////
		 EEE-- getDiagram().editorGen.extension() org.isoe.diagraph.diagraph.impl.DGraphImpl@44630d66 (viewName: default, graphHandler: null) ////
		 FFF-- getDiagram().editorGen.extensionToString() org.isoe.diagraph.diagraph.impl.DGraphImpl@44630d66 (viewName: default, graphHandler: null) ////
		 GGG-- EXPAND MetaModel::QualifiedClassName FOR getDiagram().domainDiagramElement lang.m2.kfsm.FSM  ////
		 HHH-- getDocumentRoot(self) 	  III-- EXPAND foobar FOR getDiagram() 
		 //1
		 //2
		 //3lang.m2.kfsm.FSM
		 //4
		 JJJ-- getDiagram().editorGen.diagraphSize()3  //FP120703
		 KKK-- getGraphicalNodeEditPolicyQualifiedClassName() lang.m2.kfsm.diagram_default_root.edit.policies.StateGraphicalNodeEditPolicy ////
		 LLL-- editPartClassName StateEditPart ////
		 LLLx-- editPartClassName.substring(1, editPartClassName.rfind('EditPart')) State ////
		 MMM-- getDiagram().editPartsPackageName lang.m2.kfsm.diagram_default_root.edit.parts
		 NNN-- getDiagram().domainDiagramElement.ecoreClass org.eclipse.emf.ecore.impl.EClassImpl@38520280 (name: FSM) (instanceClassName: null) (abstract: false, interface: false)
		 OOO-- EXPAND tototata //a
		 //b
		
		 //e  NONE
		

		 PPP-- getDiagram().editorGen.isDiagraphPointOfView('Diagram')false  //FP120703
		 QQQ-- getDiagram().editorGen.isDiagraphPointOfView('Classroom')true  //FP120703
		 RRR-- getDiagram().editorGen.isDiagraphPointOfView(nodeName())true  //FP120703
		 TTT-- IF getDiagram().editorGen.diagraphHasNodeEdges(nodeName()
		 TTT-- has edges for:State
		 hasEdges
		 UUU-- containments for:State

		 getDiagram().editorGen.logDNode(nodeName())  [[State]]
		 getDiagram().editorGen.logBackgroundColor(nodeName())  115  for State
		 VVVV-- getBackgroundColor:error


		 SSS-- compartments for:State
		 contnm.target.name:[Action]
		
		 */

		//3

		//DIAGRAPH to modify 0704
		/*  example:
		DefaultSizeNodeFigure result = new DefaultSizeNodeFigure(600,300);
		return result;
		 */
		return result;
	}

	/**
	 * Creates figure for this edit part.
	 * 
	 * Body of this method does not depend on settings in generation model
	 * so you may safely remove <i>generated</i> tag and modify it.
	 * 
	 * @generated
	 */
	protected NodeFigure createNodeFigure() {
		NodeFigure figure = createNodePlate();
		figure.setLayoutManager(new StackLayout());
		IFigure shape = createNodeShape();
		figure.add(shape);
		contentPane = setupContentPane(shape);
		return figure;
	}

	/**
	 * Default implementation treats passed figure as content pane.
	 * Respects layout one may have set for generated figure.
	 * @param nodeShape instance of generated figure class
	 * @generated
	 */
	protected IFigure setupContentPane(IFigure nodeShape) {
		if (nodeShape.getLayoutManager() == null) {
			ConstrainedToolbarLayout layout = new ConstrainedToolbarLayout();
			layout.setSpacing(5);
			nodeShape.setLayoutManager(layout);
		}
		return nodeShape; // use nodeShape itself as contentPane
	}

	/**
	 * @generated
	 */
	public IFigure getContentPane() {
		if (contentPane != null) {
			return contentPane;
		}
		return super.getContentPane();
	}

	/**
	 * @generated
	 */
	protected void setForegroundColor(Color color) {
		if (primaryShape != null) {
			primaryShape.setForegroundColor(color);
		}
	}

	/**
	 * @generated
	 */
	protected void setBackgroundColor(Color color) {
		if (primaryShape != null) {
			primaryShape.setBackgroundColor(color);
		}
	}

	/**
	 * @generated
	 */
	protected void setLineWidth(int width) {
		if (primaryShape instanceof Shape) {
			((Shape) primaryShape).setLineWidth(width);
		}
	}

	/**
	 * @generated
	 */
	protected void setLineType(int style) {
		if (primaryShape instanceof Shape) {
			((Shape) primaryShape).setLineStyle(style);
		}
	}

	/**
	 * @generated
	 */
	public EditPart getPrimaryChildEditPart() {
		return getChildBySemanticHint(lang.m2.kfsm.diagram_default_root.part.KfsmVisualIDRegistry
				.getType(lang.m2.kfsm.diagram_default_root.edit.parts.StateNameEditPart.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	public List<IElementType> getMARelTypesOnSource() {
		ArrayList<IElementType> types = new ArrayList<IElementType>(1);
		types.add(lang.m2.kfsm.diagram_default_root.providers.KfsmElementTypes.Transition_3001);
		return types;
	}

	/**
	 * @generated
	 */
	public List<IElementType> getMARelTypesOnSourceAndTarget(
			IGraphicalEditPart targetEditPart) {
		LinkedList<IElementType> types = new LinkedList<IElementType>();
		if (targetEditPart instanceof lang.m2.kfsm.diagram_default_root.edit.parts.StateEditPart) {
			types.add(lang.m2.kfsm.diagram_default_root.providers.KfsmElementTypes.Transition_3001);
		}
		return types;
	}

	/**
	 * @generated
	 */
	public List<IElementType> getMATypesForTarget(IElementType relationshipType) {
		LinkedList<IElementType> types = new LinkedList<IElementType>();
		if (relationshipType == lang.m2.kfsm.diagram_default_root.providers.KfsmElementTypes.Transition_3001) {
			types.add(lang.m2.kfsm.diagram_default_root.providers.KfsmElementTypes.State_1001);
		}
		return types;
	}

	/**
	 * @generated
	 */
	public List<IElementType> getMARelTypesOnTarget() {
		ArrayList<IElementType> types = new ArrayList<IElementType>(1);
		types.add(lang.m2.kfsm.diagram_default_root.providers.KfsmElementTypes.Transition_3001);
		return types;
	}

	/**
	 * @generated
	 */
	public List<IElementType> getMATypesForSource(IElementType relationshipType) {
		LinkedList<IElementType> types = new LinkedList<IElementType>();
		if (relationshipType == lang.m2.kfsm.diagram_default_root.providers.KfsmElementTypes.Transition_3001) {
			types.add(lang.m2.kfsm.diagram_default_root.providers.KfsmElementTypes.State_1001);
		}
		return types;
	}

	@Override //Diagraph //FP140314
	protected void handleNotificationEvent(Notification notification) {
		if (notification.getNotifier() instanceof State) {
			getPrimaryShape().updateFace();
		}
		super.handleNotificationEvent(notification);
	}
	
	//AZ127

	//AZ128

	/**
	 * @generated
	 */
	public class StateFigure extends RoundedRectangle {

		/**
		 * @generated
		 */
		private WrappingLabel fFigureStateNameFigure;

		
		
		//Diagraph //FP140314
		public void updateFace() {
			
			State state = (State) ((org.eclipse.gmf.runtime.notation.Node) StateEditPart.this.getModel()).getElement();
			// set line width according to...
			int lineWidth = 1;
			if (state.getS_action()!=null){
				lineWidth = 5;
			}
			this.setLineWidth(lineWidth);

			// update tooltip
			String tooltipText;
			if (state.getName() != null
					&& state.getName().length() > 0) {
				tooltipText = state.getName();
			} else {
				tooltipText = "??";
			}
			if (getToolTip() == null) {
				setToolTip(new Label(tooltipText));
			} else if (getToolTip() instanceof Label) {
				((Label) getToolTip()).setText(tooltipText);
			}
		}
		
		
		
		/**
		 * @generated NOT
		 */
		public StateFigure() {

			ToolbarLayout layoutThis = new ToolbarLayout();
			layoutThis.setStretchMinorAxis(true);
			layoutThis.setMinorAlignment(ToolbarLayout.ALIGN_CENTER);

			layoutThis.setSpacing(0);
			layoutThis.setVertical(true);

			this.setLayoutManager(layoutThis);

			this.setCornerDimensions(new Dimension(getMapMode().DPtoLP(8),
					getMapMode().DPtoLP(8)));
			this.setForegroundColor(ColorConstants.black);
			this.setBackgroundColor(ColorConstants.white);

			this.setBorder(new MarginBorder(getMapMode().DPtoLP(0),
					getMapMode().DPtoLP(3), getMapMode().DPtoLP(3),
					getMapMode().DPtoLP(3)));
			
			updateFace();//Diagraph //FP140314
			
			createContents();
		}

		/**
		 * @generated
		 */
		private void createContents() {

			fFigureStateNameFigure = new WrappingLabel();

			fFigureStateNameFigure.setText("");

			fFigureStateNameFigure.setFont(FFIGURESTATENAMEFIGURE_FONT);

			this.add(fFigureStateNameFigure);

		}

		/**
		 * @generated
		 */
		public WrappingLabel getFigureStateNameFigure() {
			return fFigureStateNameFigure;
		}

	}

	/**
	 * @generated
	 */
	static final Font FFIGURESTATENAMEFIGURE_FONT = new Font(
			Display.getCurrent(), "Tahoma", 12, SWT.ITALIC);

	//AZ129		
}
