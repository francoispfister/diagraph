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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.gmf.codegen.gmfgen.GMFGenFactory;
import org.eclipse.gmf.codegen.gmfgen.GenChildNode;
import org.eclipse.gmf.codegen.gmfgen.GenCommonBase;
import org.eclipse.gmf.codegen.gmfgen.GenCompartment;
import org.eclipse.gmf.codegen.gmfgen.GenDiagram;
import org.eclipse.gmf.codegen.gmfgen.GenEditorGenerator;
import org.eclipse.gmf.codegen.gmfgen.GenLink;
import org.eclipse.gmf.codegen.gmfgen.GenLinkLabel;
import org.eclipse.gmf.codegen.gmfgen.GenNode;
import org.eclipse.gmf.codegen.gmfgen.GenNodeLabel;
import org.eclipse.gmf.codegen.gmfgen.GenTopLevelNode;
import org.eclipse.gmf.codegen.gmfgen.OpenDiagramBehaviour;
import org.eclipse.gmf.codegen.gmfgen.ProviderPriority;
import org.eclipse.gmf.codegen.gmfgen.TypeModelFacet;
import org.eclipse.gmf.internal.bridge.NaiveIdentifierDispenser;
import org.eclipse.gmf.internal.bridge.genmodel.BasicDiagramRunTimeModelHelper;
import org.eclipse.gmf.internal.bridge.genmodel.DiagramGenModelTransformer;
import org.eclipse.gmf.internal.bridge.genmodel.InnerClassViewmapProducer;
import org.eclipse.gmf.internal.bridge.naming.gen.GenNamingMediatorImpl;
import org.eclipse.gmf.mappings.ChildReference;
import org.eclipse.gmf.mappings.Mapping;
import org.eclipse.gmf.mappings.NodeMapping;
import org.eclipse.gmf.mappings.TopNodeReference;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.diagraph.DGraphElement;
import org.isoe.diagraph.diagraph.DLabel;
import org.isoe.diagraph.diagraph.DLabeledElement;
import org.isoe.diagraph.diagraph.DNode;
import org.isoe.diagraph.diagraph.DPointOfView;
import org.isoe.diagraph.diastyle.DStyle;
import org.isoe.diagraph.internal.api.IDiaPlatformDelegateProvider;
import org.isoe.diagraph.internal.api.IDiaPointOfView;
import org.isoe.diagraph.internal.m2.DiaPointOfView;
import org.isoe.diagraph.views.ViewConstants;
import org.isoe.fwk.core.DParams;
import org.isoe.diagraph.runner.IDiagraphRunner;
import org.isoe.fwk.utils.ResourceManager;
import org.isoe.fwk.utils.eclipse.PluginResourceCopy;

/**
 *
 * @author pfister
 *
 */
public class GmfGenGenerator {

	private static final boolean LOG = DParams.GmfGenGenerator_LOG;
	private static final String SP_ = "                                                                                                                                 ";
	private static final boolean APPLY_STYLE3 = true;
	private static final String ICON_FOLDER_TARGET = "icons";
	private DGraph dgraph;
	private DStyle dstyle;
	private Mapping mapping;
	private GenModel genModel;
	private String filePath;
	private IDiagraphRunner runner;
	private List<OpenDiagramBehaviour> openDiagramBehaviours = new ArrayList<OpenDiagramBehaviour>();
	private String[] resourceData;
	private String pluginId;
	private String templateFolderTarget;
	private String templatePluginSource;
	private String templateFolderSource;
	private String iconPluginSource;
	private String iconFolderSource;

	public Mapping getMapping() {
		return mapping;
	}

	private GenEditorGenerator genEditorGenerator;
	private IDiaPlatformDelegateProvider diagGenerator;

	public GmfGenGenerator(IDiagraphRunner runner,
			IDiaPlatformDelegateProvider diagGenerator, String pluginId,
			String templateFolderTarget_, String templatePluginSource_,
			String templateFolderSource_, String iconPluginSource,
			String iconFolderSource, String filePath, // GenModel genModel,
			Mapping mapping, String[] resourceData) {
		this.runner = runner;
		this.resourceData = resourceData;
		this.pluginId = pluginId;
		this.templateFolderTarget = templateFolderTarget_;
		this.filePath = filePath;
		this.mapping = mapping;
		this.templatePluginSource = templatePluginSource_;
		this.templateFolderSource = templateFolderSource_;
		this.iconPluginSource = iconPluginSource;
		this.iconFolderSource = iconFolderSource;
		this.diagGenerator = diagGenerator;
	}

	public void setGenModel_(GenModel genModel) {
		this.genModel = genModel;
	}

	private OpenDiagramBehaviour createOpenDiagramBehaviourForChildNode(
			GenChildNode childNode, String targetViewName,
			EClass domainMetaElement) {
		String[] dcs = genNavigation(targetViewName, domainMetaElement);
		OpenDiagramBehaviour result = createOpenDiagramBehaviourForChildNode_(
				childNode, domainMetaElement.getName(), dcs[0], dcs[1], dcs[2]);
		if (LOG)
			clog("createOpenDiagramBehaviourForChildNode: " + " -> "
					+ targetViewName);
		return result;
	}

	private void addOpenDiagramBehaviorToAllTopNodePov() {
		EList<TopNodeReference> tns = getMapping().getNodes();
		for (TopNodeReference topNodeReference : tns) {
			DNode node = findDNode(topNodeReference.getOwnedChild()
					.getDomainMetaElement().getName());
			if (node.getNavigationLink() != null) {
				GenNode topLevelNode = findGenTopLevelNode(topNodeReference
						.getOwnedChild().getDomainMetaElement());
				openDiagramBehaviours
						.add(createOpenDiagramBehaviourForTopNode_(
								(GenTopLevelNode) topLevelNode, node,
								node.getNavigationLink(), topNodeReference)); // FP121124x
			}
		}
	}

	private void setupValidation() { // FP130914
		if (LOG)
			clog("setupValidation");

		// FP130914

		genEditorGenerator.getDiagram().setValidationProviderPriority(
				ProviderPriority.MEDIUM_LITERAL);
		// genEditorGenerator.getDiagram().setValidationDecoratorProviderPriority(ProviderPriority.MEDIUM_LITERAL);
		genEditorGenerator.getDiagram().setValidationEnabled(true);
		genEditorGenerator.getDiagram().setValidationDecorators(true);
		genEditorGenerator.getDiagram().setLiveValidationUIFeedback(true);
	}

	private void createOpenDiagramBehavioursToCaptionlabelsForAllTopLevelNodes_( // FP121104xxx
			IDiaPointOfView pov) {
		if (LOG)
			logPov_(pov);

		for (GenTopLevelNode topLevelNode : genEditorGenerator.getDiagram()
				.getTopLevelNodes()) { // cloneFirstOpenDiagramBehaviourToFirstLabel_
			String mi = topLevelNode.getModelFacet().getMetaClass()
					.getModelInfo();
			EClass domainMetaElement = topLevelNode.getModelFacet()
					.getMetaClass().getEcoreClass();
			DNode dnod = findDNode(topLevelNode.getModelFacet().getMetaClass()
					.getEcoreClass().getName());// name:
			if (dnod.getNavigationLink() != null) {// &&
													// !dnod.getNavigats().isEmpty())
													// {
				String targetViewName = dnod.getNavigationLink(); // FP121124x//
																	// //six_root
				EList<GenNodeLabel> genlabels = topLevelNode.getLabels();
				if (genlabels.size() >= 1) {
					// GenNodeLabel genNodeLabel_ = genlabels.get(0);
					String[] dcs = genNavigation(targetViewName,
							domainMetaElement);
					OpenDiagramBehaviour result = createOpenDiagramBehaviourForGenNodeLabel_(
							genlabels.get(0), domainMetaElement.getName(),
							dcs[0], dcs[1], dcs[2]);
					openDiagramBehaviours.add(result);
				}
			}
		}
	}

	public void createOpenDiagramBehaviors() {
		addOpenDiagramBehaviorToAllTopNodePov();
		addChildOpenDiagramBehaviorsToNodeAndLabels();
		// createOpenDiagramBehaviorForAllChildNodePov_();
	}

	/*
//FP140628zzz
DiagramKind = "Pub_process_root"
alors que "Pub_vwpublicationprocess_root"
	 */

	private void logBehaviour(OpenDiagramBehaviour odb) {
		String epqcn = "GGGpb1";;
		String gennodeName = "GGGpb2";
		String dk = odb.getDiagramKind() == null ? "" : odb.getDiagramKind();
		String eid = odb.getEditorID() == null ? "" : odb.getEditorID();
		String epcn = odb.getEditPolicyClassName() == null ? "" : odb
				.getEditPolicyClassName();
		if (odb.getSubject() != null) {
			epqcn = odb.getEditPolicyQualifiedClassName() == null ? "" : odb
					.getEditPolicyQualifiedClassName();
			GenCommonBase gen = (GenCommonBase) odb.getSubject();
			String geneditPartClassName = gen.getEditPartClassName();
			gennodeName = geneditPartClassName.substring(0,
					geneditPartClassName
							.indexOf(GenCommonBase.EDIT_PART_SUFFIX));
		}

		if (LOG)
			clog("OpenDiagramBehaviour:\n-diagramkind=" + dk + "\n-editorid="
					+ eid + "\n-epcyclassname=" + epcn + "\n-epcyqu classname="
					+ epqcn + "\n-gennodename=" + gennodeName);
	}

	@SuppressWarnings("restriction")
	protected void generateGmfGen(
			StringBuffer log, // FP130915zz
			IDiaPointOfView pointOfView, String view, boolean rcp,
			IProgressMonitor progressMonitor) {
		if (LOG)
			clog("generating gmfgen");
		if (progressMonitor != null) {
			progressMonitor.subTask("mapToGenEditorGenerator");
			progressMonitor.worked(1);
		}
		DiagramGenModelTransformer.Parameters opts = new DiagramGenModelTransformer.Parameters(
				new BasicDiagramRunTimeModelHelper(),
				new InnerClassViewmapProducer(),
				new NaiveIdentifierDispenser(), rcp); // FP130915zz // FP130507
		DiagramGenModelTransformer diagramGenModelTransformer = new DiagramGenModelTransformer(
				opts);
		try {
			diagramGenModelTransformer.setEMFGenModel(genModel);
			diagramGenModelTransformer.transform(mapping);
		} catch (Exception e) {
			String mesg = "Error while gmfgen Generation "
					+ (e.getMessage().contains("NullPointer") ? "" : e
							.getMessage()) + " !!!!";
			log.append(mesg).append('\n');
			runner.cerror(mesg);
			throw new RuntimeException(mesg);
		}
		genEditorGenerator = diagramGenModelTransformer.getResult();
		getEcoreClass(); // just for test
		validate(view, pointOfView.getName());
		String pref = genEditorGenerator.getPackageNamePrefix();
		String mesg = " mapToGenEditorGenerator ok for " + pref
				+ " - point of view=" + pointOfView.getName();
		if (LOG)
			clog(mesg);
		log.append(mesg).append('\n');
	}

	private void validate(String view, String pointOfviewName) {
		Diagnostic diag = Diagnostician.INSTANCE.validate(genEditorGenerator);
		if (diag.getSeverity() != Diagnostic.OK) {
			String diagmesg = "";
			List<Diagnostic> diags = diag.getChildren();
			for (Diagnostic diagnostic : diags) {
				diagmesg += diagnostic.getMessage() + " - ";
				for (Object data : diagnostic.getData()) {
					if (data instanceof GenTopLevelNode) {
						GenClass genClass = ((GenTopLevelNode) data)
								.getDomainMetaClass();
						String cln = genClass == null ? "" : genClass
								.getClassName();
						if (cln.contains("Impl"))
							diagmesg += " error on Class "
									+ (cln.substring(0, cln.length() - 4))
									+ " ";
					}
				}
			}
			String err = "Error while GmfGen validation  in view " + view
					+ " - " + pointOfviewName + diagmesg + " !!!!";
			clog(err);
			throw new RuntimeException(err);
		} else if (LOG)
			clog("gmfgen validation ok for view " + view + " - "
					+ pointOfviewName);
	}

	protected void modifyGenEditorGeneratorParameters(
			IDiaPointOfView pointOfView, String layer,
			IProgressMonitor progressMonitor) {
		if (progressMonitor != null) {
			progressMonitor.subTask("modifyGenEditorGeneratorParameters");
			progressMonitor.worked(1);
		}
		modifyGenEditorGenParameters(genEditorGenerator, pointOfView, layer);
	}

	protected void modifyGenEditorGeneratorModifyStyles(
			IDiaPointOfView pointOfView, String layer,
			IProgressMonitor progressMonitor) {
		if (progressMonitor != null) {
			progressMonitor.subTask("modifyGenEditorGeneratorModifyStyles");
			progressMonitor.worked(1);
		}
		// if (pointOfView.isRoot()){
		modifyCompartmentLayouts(pointOfView); // FP121018z // FP120620
		modifyShapes(pointOfView);
		// }
	}

	private void logParameters_(GenEditorGenerator gen) {
		if (LOG) {
			clog("ModelID: " + gen.getModelID());
			clog("DiagramFileExtension :" + gen.getDiagramFileExtension());
			clog("DomainFileExtension :" + gen.getDomainFileExtension());
			clog("PluginDirectory: " + gen.getPluginDirectory());
			clog("PackageNamePrefix: " + gen.getPackageNamePrefix());
			clog("PluginID: " + gen.getPlugin().getID());
			clog("PluginName: " + gen.getPlugin().getName());
			clog("EditorID: " + gen.getEditor().getID());
		}
	}

	private void modifyGenParameters(GenEditorGenerator gen,
			IDiaPointOfView pointOfView, String layer, boolean root) {
		String modlid_ = gen.getModelID();
		modlid_ += pointOfView.getSuffix();
		if (LOG)
			clog("MGP*************** ---modifyGenParameters, ModelID:  "
					+ gen.getModelID() + "  -->>  " + modlid_
					+ "--- ***************");
		gen.setModelID(modlid_);
		String domnfe = gen.getDomainFileExtension() + pointOfView.getSuffix();
		if (!root)
			gen.setDomainFileExtension(domnfe); // attention, différentier les
												// extensions
		if (LOG)
			clog("DomainFileExtension :" + gen.getDomainFileExtension());
		String diagramFileExtension = gen.getDiagramFileExtension();
		if (root) { // FP121102 diagramFileExtension multv_diagram =>
					// multv_default_root_diagram // replace _ with
					// default_root_
			diagramFileExtension = diagramFileExtension.replace(
					"_",
					ViewConstants.VIEW_SEPARATOR_0
							+ DiaPointOfView.getLayeredRootName(layer) // FP140216
							+ ViewConstants.VIEW_SEPARATOR_1); // FP121018

			// if (diagramFileExtension.endsWith("_default_root_diagram"))
			// diagramFileExtension =
			// diagramFileExtension.substring(0,diagramFileExtension.indexOf("_default_root_diagram"))+"diagram";//FP140216

			gen.setDiagramFileExtension(diagramFileExtension);
		}
		if (LOG)
			clog("DiagramFileExtension :" + gen.getDiagramFileExtension());
		String pldir = gen.getPluginDirectory();
		String spldir = pldir.substring(0, pldir.indexOf("/src"));
		pldir = spldir + pointOfView.getSuffix() + "/src";
		gen.setPluginDirectory(pldir);
		if (LOG)
			clog("PluginDirectory: " + pldir);
		String packnp = gen.getPackageNamePrefix();
		gen.setPackageNamePrefix(packnp + pointOfView.getSuffix());
		if (LOG)
			clog("PackageNamePrefix: " + gen.getPackageNamePrefix());
		String plid = gen.getPlugin().getID();
		plid += pointOfView.getSuffix();
		gen.getPlugin().setID(plid);
		if (LOG)
			clog("PluginID: " + gen.getPlugin().getID());
		String plname = gen.getPlugin().getName();
		plname += pointOfView.getSuffix();
		gen.getPlugin().setName(plname);
		if (LOG)
			clog("PluginName: " + gen.getPlugin().getName());
		String genEditorViewId = gen.getEditor().getID();
		if (LOG)
			clog("EditorID: " + genEditorViewId);
		pointOfView.setGenModelId(modlid_);
		pointOfView.setGenEditorViewId(genEditorViewId);
	}

	private void modifyGenEditorGenParameters(GenEditorGenerator gen,
			IDiaPointOfView pointOfView, String layer) { // FP121018
		modifyGenParameters(gen, pointOfView, layer, pointOfView.getName()
				.equals(DiaPointOfView.getLayeredRootName(layer))); // FP140216
	}

	// boolean done = false;

	private void logPov_(IDiaPointOfView pov) {
		if (LOG) {
			clog("[--  point of view ---");
			clog("nodeName: " + pov.getNodeName());
			clog("povName: " + pov.getName());
			clog("suffix: " + pov.getSuffix());
			clog("genModelId: " + pov.getGenModelId());
			clog("gGenEditorViewId: " + pov.getGenEditorViewId());
			clog("---  point of view --]");
		}
	}

	private DNode findNode(IDiaPointOfView pov) { // FP121022
		if (runner == null || runner.getDGraph() == null)
			return null;
		List<DNode> nods = getNodes();
		for (DNode nod : nods) {
			if (nod.getName().equals(pov.getNodeName()))
				return nod;
		}
		return null;
	}


	  private List<DNode> getNodes(){ //FP140518
		   List<DNode> nodes = new ArrayList<DNode>();
		   List<DGraphElement> elements = runner.getDGraph().getElements();
		   for (DGraphElement element : elements) {
			 if(element instanceof DNode)
				 nodes.add((DNode) element);
		   }
		   return nodes;
	   }

	private String[] genNavigation(String targetView, EClass me) {
		String[] result = new String[3];
		EPackage epak = me.getEPackage();
		String domain = epak.getName();
		Resource er = epak.eResource();
		String pfs = er.getURI().toString();
		pfs = pfs.substring(0, pfs.indexOf(me.getEPackage().getName())
				+ me.getEPackage().getName().length());
		pfs = pfs.substring(pfs.lastIndexOf("/") + 1, pfs.length());
		String packagename = pfs;
		domain = domain.substring(0, 1).toUpperCase()
				+ domain.substring(1, domain.length());
		String domainName = domain.substring(0, 1).toUpperCase()
				+ domain.substring(1, domain.length());
		// String povName = pofv.getViewName();
		result[0] = targetView;// povName;
		result[1] = domainName;
		result[2] = packagename;
		return result;
	}


	/*
	//FP140628zzz
	DiagramKind = "Pub_process_root"
	alors que "Pub_vwpublicationprocess_root"
		 */
	private OpenDiagramBehaviour createOpenDiagramBehaviourForTopNode__(
			GenTopLevelNode topLevelNode, String nodeName, String povName,
			String domainName, String packagename) { // FP121022
		OpenDiagramBehaviour odb = createodb_(povName, packagename, domainName);
		topLevelNode.getBehaviour().add(odb);
		if (LOG) {
			clog("createOpenDiagramBehaviour for: " + nodeName);
			clog("OpenDiagramEditPolicy: " + odb.getEditPolicyClassName());
			clog("DiagramKind: " + odb.getDiagramKind());
			clog("EditorID: " + odb.getEditorID());
			// logBehaviour(odb);
		}
		return odb;
	}

	private OpenDiagramBehaviour createOpenDiagramBehaviourForGenNodeLabel_(
			GenNodeLabel genNodeLabel, String nodeName, String povName,
			String domainName, String packagename) {
		OpenDiagramBehaviour odb = createodb_(povName, packagename, domainName);
		genNodeLabel.getBehaviour().add(odb);
		if (LOG) {
			clog("createOpenDiagramBehaviour for: " + nodeName);
			clog("OpenDiagramEditPolicy: " + odb.getEditPolicyClassName());
			clog("DiagramKind: " + odb.getDiagramKind());
			clog("EditorID: " + odb.getEditorID());
			// logBehaviour(odb);
		}
		return odb;
	}

	/*
	//FP140628zzz
	DiagramKind = "Pub_process_root"
	alors que "Pub_vwpublicationprocess_root"
		 */
	private OpenDiagramBehaviour cloneodb_(OpenDiagramBehaviour other) {
		OpenDiagramBehaviour cloned = GMFGenFactory.eINSTANCE
				.createOpenDiagramBehaviour();
		cloned.setEditPolicyClassName(other.getEditPolicyClassName());
		cloned.setDiagramKind(other.getDiagramKind());
		cloned.setEditorID(other.getEditorID());
		cloned.setOpenAsEclipseEditor(true);
		return cloned;
	}

	private OpenDiagramBehaviour createodb_(String povName, String packagename,
			String domainName) {
		// is       povName=process_root
		//should be povName=vwpublicationprocess_root


		OpenDiagramBehaviour odb = GMFGenFactory.eINSTANCE
				.createOpenDiagramBehaviour();
		if (LOG) clog("EditPolicyClassName="+"OpenDiagramEditPolicy" + povName);
		odb.setEditPolicyClassName("OpenDiagramEditPolicy" + povName);
		String genModelId = domainName + ViewConstants.VIEW_SEPARATOR_0
				+ povName;                          //Pub_process_root
		if (LOG) clog("DiagramKind="+ genModelId);  //DiagramKind=Pub_process_root alrs que"Pub_vwpublicationprocess_root"
		String genEditorViewId = packagename + ".diagram_" + povName + ".part."
				+ domainName + "DiagramEditorID";   //lang.m2.pub.diagram_process_root.part.PubDiagramEditorID
		if (LOG) clog("setEditorID="+ genEditorViewId);

		odb.setDiagramKind(genModelId);
		odb.setEditorID(genEditorViewId);
		odb.setOpenAsEclipseEditor(true);
		return odb;
	}


	/*
	 *
	 *
EditPolicyClassName=OpenDiagramEditPolicyprocess_root
DiagramKind=Pub_process_root
setEditorID=lang.m2.pub.diagram_process_root.part.PubDiagramEditorID


	//FP140628zzz
	DiagramKind = "Pub_process_root"
	alors que "Pub_vwpublicationprocess_root"
		 */
	private OpenDiagramBehaviour createOpenDiagramBehaviourForChildNode_(
			GenChildNode childNode, String nodeName, String povName,
			String domainName, String packagename) {

		OpenDiagramBehaviour odb = createodb_(povName, packagename, domainName);
		childNode.getBehaviour().add(odb);
		if (LOG) {
			clog("createOpenDiagramBehaviour for: " + nodeName);
			clog("OpenDiagramEditPolicy: " + odb.getEditPolicyClassName());
			clog("DiagramKind: " + odb.getDiagramKind());
			clog("EditorID: " + odb.getEditorID());
			// logBehaviour(odb);
		}
		return odb;
	}

	private GenTopLevelNode findGenTopLevelNode(EClass domainMetaElement) {
		for (GenTopLevelNode genTopLevelNode : getGenEditorGenerator()
				.getDiagram().getTopLevelNodes())
			if (genTopLevelNode.getDomainMetaClass().getEcoreClass()
					.equals(domainMetaElement))
				return genTopLevelNode;
		return null;
	}

	private GenChildNode findGenChildNode(EClass domainMetaElement) {
		for (GenChildNode genChildNode : getGenEditorGenerator().getDiagram()
				.getChildNodes())
			if (genChildNode.getDomainMetaClass().getEcoreClass()
					.equals(domainMetaElement))
				return genChildNode;
		return null;
	}

	private OpenDiagramBehaviour createOpenDiagramBehaviourForTopNode_(
			// FP121124k
			GenTopLevelNode topLevelNode, DNode nod, String targetViewName,
			TopNodeReference topNodeReference) {
		String[] dcs = genNavigation(targetViewName,
				(EClass) nod.getSemanticRole());
		OpenDiagramBehaviour result = createOpenDiagramBehaviourForTopNode__(
				topLevelNode, nod.getSemanticRole().getName(), dcs[0], dcs[1],
				dcs[2]);
		if (LOG)
			clog("createOpenDiagramBehaviourForTopNode: "
					+ nod.getGraph().getViewName() + " -> " + targetViewName);

		return result;
	}

	private OpenDiagramBehaviour createOpenDiagramBehaviourForGenNodeLabel_(
			GenNodeLabel genNodeLabel, String targetViewName,
			EClass domainMetaElement) {
		String[] dcs = genNavigation(targetViewName, domainMetaElement);
		OpenDiagramBehaviour result = createOpenDiagramBehaviourForGenNodeLabel_(
				genNodeLabel, domainMetaElement.getName(), dcs[0], dcs[1],
				dcs[2]);
		if (LOG)
			clog("createOpenDiagramBehaviourForChildNode: " + " -> "
					+ targetViewName);
		return result;
	}

	public EAttribute getFirstLabelAttribute(DLabeledElement el) { // FP130125
		/*
		 * if (el.getLabelAttributes() != null && el.getLabelAttributes().size()
		 * > 0) return el.getLabelAttributes().get(0);
		 */// FP130615ok

		List<DLabel> dlabels = el.getDlabels();
		if (dlabels.size() > 0)
			return dlabels.get(0).getAttribute();
		return null;
	}

	public List<EAttribute> getLabelAttributes(DLabeledElement el) { // FP130615ok
		List<EAttribute> result = new ArrayList<EAttribute>();
		List<DLabel> dlabels = el.getDlabels();
		for (DLabel dLabel : dlabels)
			result.add(dLabel.getAttribute());
		return result;
	}

	private GenNodeLabel findGenNodeLabel_(DNode dnod, GenChildNode genchildNode) {
		EAttribute labattrib = getFirstLabelAttribute(dnod);
		String editpartClassName_ = "";
		if (labattrib == null)
			return null;
		String editpartClassName = dnod.getName()
				+ labattrib.getName().substring(0, 1).toUpperCase()
				// + dnod.getLabelAttribute_().getName().substring(0,
				// 1).toUpperCase()
				+ labattrib.getName()
						.substring(1, labattrib.getName().length())
				+ "EditPart";
		EList<GenNodeLabel> genlabels = genchildNode.getLabels();
		for (GenNodeLabel genNodeLabel : genlabels) {
			String epclname = genNodeLabel.getEditPartClassName();
			if (epclname.equals(editpartClassName_))
				return genNodeLabel;
		}
		return null;
	}

	private void recursiveAddChildOpenDiagramBehaviorsToNodeAndLabels_(
			NodeMapping nodmap, int depth) {
		if (nodmap != null) {
			EList<ChildReference> children = nodmap.getChildren();
			for (ChildReference childReference : children) {
				NodeMapping childNodeMapping = childReference.getOwnedChild();
				DNode dnod = findDNode(childNodeMapping.getDomainMetaElement()
						.getName());// name:
				if (LOG)
					clog(SP_.substring(0, depth * 3) + "child node: "
							+ dnod.getName() + "  nav="
							+ dnod.getNavigationLink());
				if (dnod.getNavigationLink() != null) {
					// for (String nav : dnod.getNavigats()) { //FP121124x
					GenChildNode genchildNode = findGenChildNode(childNodeMapping
							.getDomainMetaElement());
					openDiagramBehaviours
							.add(createOpenDiagramBehaviourForChildNode(
									genchildNode, dnod.getNavigationLink(),
									childNodeMapping.getDomainMetaElement()));
					GenNodeLabel genNodeLabel = findGenNodeLabel_(dnod,
							genchildNode);
					if (genNodeLabel != null)
						openDiagramBehaviours
								.add(createOpenDiagramBehaviourForGenNodeLabel_(
										genNodeLabel, dnod.getNavigationLink(),
										childNodeMapping.getDomainMetaElement()));
					else if (LOG)
						clog("genNodeLabel is null"); // FP131018
					// }
				}
				recursiveAddChildOpenDiagramBehaviorsToNodeAndLabels_(
						childNodeMapping, ++depth);
			}
		}
	}

	private void addChildOpenDiagramBehaviorsToNodeAndLabels() {
		EList<TopNodeReference> tns = getMapping().getNodes();
		for (TopNodeReference topNodeReference : tns) {
			NodeMapping topnodemapping = topNodeReference.getOwnedChild();
			EClass topdme = topnodemapping.getDomainMetaElement();
			String topdmename = topdme.getName();
			DNode topnode = findDNode(topdmename);
			DGraph graph = (DGraph) topnode.eContainer();
			if (LOG)
				clog("top node: " + topnode.getName() + " nav="
						+ topnode.getNavigationLink()
						+ (topnode instanceof DPointOfView ? " **" : ""));
			recursiveAddChildOpenDiagramBehaviorsToNodeAndLabels_(
					topNodeReference.getOwnedChild(), 0);
		}
	}

	public void modifyGenEditorGeneratorCreateOpenDiagramBehavioursToTopLabels_(
			IDiaPointOfView pointOfView, String layer,
			IProgressMonitor progressMonitor) { // FP121125x
		if (progressMonitor != null) {
			progressMonitor
					.subTask("modifyGenEditorGeneratorCreateOpenDiagramBehavioursToTopLabels");
			progressMonitor.worked(1);
		}
		/*
		//FP140628zzz
		DiagramKind = "Pub_process_root"
		alors que "Pub_vwpublicationprocess_root"
			 */

		createOpenDiagramBehavioursToCaptionlabelsForAllTopLevelNodes_(pointOfView);
		if (LOG)
			for (OpenDiagramBehaviour odb : openDiagramBehaviours)
				logBehaviour(odb);
	}

	public void setupValidation(IDiaPointOfView pointOfView, String layer,
			IProgressMonitor progressMonitor) { // FP121125x
		if (progressMonitor != null) {
			progressMonitor.subTask("setupValidation");
			progressMonitor.worked(1);
		}
		setupValidation();
	}

	public void deployTemplates_() throws CoreException {
		IProject targetProject = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(pluginId);
		IFolder targetFolder = targetProject.getFolder(templateFolderTarget);
		if (!targetFolder.exists())
			targetFolder.create(true, true, null);

		PluginResourceCopy copier = PluginResourceCopy.getInstance();
		copier.copy(
				templatePluginSource, templateFolderSource, pluginId,
				templateFolderTarget, true, null, null);

		String log = copier.getLogs();// FP130518 //
															// replaceexisting
		if (LOG)
			clog(log); // FP120623c
	}

	public void setDynamicTemplates(boolean yes) {
		String templatelocation_ = pluginId + "/" + templateFolderTarget;
		// deployTemplates_();
		dgraph = runner.getDGraph();
		dstyle = runner.getDStyle();
		genEditorGenerator.setExtension( dgraph); //FP150422voir//FP140227
		genEditorGenerator.setStyleExtension(dstyle);
		if (yes) { // Patch
			genEditorGenerator.setDynamicTemplates(true); // FP130914
			genEditorGenerator.setTemplateDirectory(templatelocation_);
		}
	}

	/** extend gmfgen.ecore
    <eStructuralFeatures xsi:type="ecore:EReference" name="extension" eType="ecore:EClass http://www.eclipse.org/emf/2002/Ecore#//EModelElement">
      <eAnnotations source="http://www.eclipse.org/emf/2002/GenModel">
        <details key="documentation" value="Diagraph extension 1"/>
      </eAnnotations>
    </eStructuralFeatures>
    <eStructuralFeatures xsi:type="ecore:EReference" name="styleExtension" eType="ecore:EClass http://www.eclipse.org/emf/2002/Ecore#//EModelElement">
      <eAnnotations source="http://www.eclipse.org/emf/2002/GenModel">
        <details key="documentation" value="Diagraph extension 2"/>
      </eAnnotations>
    </eStructuralFeatures>



    //FP150423

    </eStructuralFeatures>
       <eStructuralFeatures xsi:type="ecore:EReference" name="extension" eType="ecore:EClass http://www.eclipse.org/emf/2002/Ecore#//EObject">
      <eAnnotations source="http://www.eclipse.org/emf/2002/GenModel">
        <details key="documentation" value="Diagraph extension 1"/>
      </eAnnotations>
    </eStructuralFeatures>
    <eStructuralFeatures xsi:type="ecore:EReference" name="styleExtension" eType="ecore:EClass http://www.eclipse.org/emf/2002/Ecore#//EObject">
      <eAnnotations source="http://www.eclipse.org/emf/2002/GenModel">
        <details key="documentation" value="Diagraph extension 2"/>
      </eAnnotations>


	 */


	public void deployIcons(String layer) {
		if (ViewConstants.DIAGRAPH_DEFAULT.equals(layer)) {
			try {
				deployIcons("default-nodes", true);
				deployIcons("default-edges", true);
				deployIcons("nodes", false);
				deployIcons("edges", false);
				deployIcons("labels", false);
			} catch (CoreException e) {
				throw new RuntimeException(" error while deploying icons !!!");
			}

		}
	}

	@SuppressWarnings("restriction")
	public GenDiagram createDiagram(IDiaPointOfView pointOfView,
			StringBuffer log_) {
		GenClass genClass = genEditorGenerator.getDiagram()
				.getDomainDiagramElement();
		new GenNamingMediatorImpl().traverse(genEditorGenerator);
		pointOfView.setEditorGenerator(this);
		// History h=diagramGenModelTransformer.getTrace();
		// h.findTopNode(nodeMapA);
		return genEditorGenerator.getDiagram();
	}


	public Resource save(String layer, String povSuffix, String extension) {
		GenEditorGenerator root = getGenEditorGenerator();
		String suffixedFilePath = filePath;
		if (povSuffix != null)
			suffixedFilePath += povSuffix;
		try {
			return ResourceManager.save(resourceData, suffixedFilePath + "."
					+ extension, root);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public GenEditorGenerator getGenEditorGenerator() {
		return genEditorGenerator;
	}



	private static boolean match(GenEditorGenerator gen,
			IDiaPointOfView pointOfView) {
		String diagramEditPartClassName = gen.getDiagram()
				.getEditPartClassName();
		String canvasclassname = gen
				.getDiagram()
				.getEditPartClassName()
				.substring(
						0,
						diagramEditPartClassName
								.indexOf(GenCommonBase.EDIT_PART_SUFFIX));
		return canvasclassname.equals(pointOfView.getElementName());
	}

	private static IProject deleteProjectSnippet(String path) {
		IProjectDescription description = null;
		IProject project = null;
		try {
			description = ResourcesPlugin.getWorkspace()
					.loadProjectDescription(new Path(path + "/.project"));
			project = ResourcesPlugin.getWorkspace().getRoot()
					.getProject(description.getName());
			IProject[] array = ResourcesPlugin.getWorkspace().getRoot()
					.getProjects();
			int i = array.length;
			for (int count = 0; count <= i - 1; count++) {
				if (project.equals(array[count])) {
					project.close(null);
					project.delete(true, null);
				}
			}
		} catch (CoreException exception_p) {
			exception_p.printStackTrace();
		} catch (OperationCanceledException exception_e) {
			exception_e.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException e1) {
			e1.printStackTrace();
		}
		return project;
	}

	private void deployIcons(String subfolder, boolean replace)
			throws CoreException {
		if (LOG)
			clog("replacing icons in  plugin: " + pluginId + "/"
					+ ICON_FOLDER_TARGET + " with icons in " + iconPluginSource
					+ "/" + iconFolderSource + "/" + subfolder); // FP121001
		IProject targetProject = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(pluginId);
		IFolder targetFolder = targetProject.getFolder(ICON_FOLDER_TARGET);
		if (!targetFolder.exists())
			targetFolder.create(true, true, null);


		PluginResourceCopy copier = PluginResourceCopy.getInstance();
		copier.copy(iconPluginSource,
				iconFolderSource + "/" + subfolder, pluginId,
				ICON_FOLDER_TARGET, !replace, null, null);

		String log = copier.getLogs();// FP130518
		if (LOG)
			clog(log); // FP120623c

	}

	private void logGenNode(GenNode genNode2) {
		GenClass genClass2 = genNode2.getDomainMetaClass();
		EModelElement eModelElement2 = genClass2.getEcoreModelElement();
		String em2name = ((ENamedElement) eModelElement2).getName();
		String eModelElementName2_ = "   child:" + genNode2.getVisualID() + "-"
				+ ((ENamedElement) eModelElement2).getName();
		if (LOG) {
			clog(eModelElementName2_);
		}

	}

	private void modifyShapes(IDiaPointOfView pointOfView) { // FP120620
		if (LOG)
			clog("--- modifyShapes ---");
		for (GenCompartment genCompartment : genEditorGenerator.getDiagram()
				.getCompartments()) {

		}
	}

	private void modifyCompartmentLayouts(IDiaPointOfView pointOfView) { // FP120620
																			// //FP120723
		if (LOG)
			clog("--- compartments in diagram ---");
		for (GenCompartment genCompartment : genEditorGenerator.getDiagram()
				.getCompartments()) {
			GenNode genNode1 = genCompartment.getNode();
			// clog("compartnode:"+genNode1.getVisualID());
			GenClass genClass = genNode1.getDomainMetaClass();
			EModelElement eModelElement = genClass.getEcoreModelElement();
			String eModelElementName1 = ((ENamedElement) eModelElement)
					.getName();
			if (LOG)
				clog("compartnode:" + genNode1.getVisualID() + "-"
						+ eModelElementName1);
			EList<GenNode> containedNodes = genCompartment.getContainedNodes();
			for (GenNode containedNode : containedNodes) {
				String containedNodeName = ((ENamedElement) (containedNode
						.getDomainMetaClass()).getEcoreModelElement())
						.getName();
				if (LOG)
					logGenNode(containedNode);
				if (APPLY_STYLE3)
					genCompartment.setListLayout(runner.getStyleHandler()
							.isListLayout(containedNodeName));
				else
					genCompartment.setListLayout(runner
							.isListLayout(containedNodeName));
				genCompartment.setCanCollapse(true); // FP140121 collapsible

			}
		}
	}

	private EClass getEcoreClass() {
		try {
			EObject contner = genEditorGenerator.getDiagram().getElementType()
					.eContainer();
			if (contner instanceof GenDiagram) {
				GenDiagram genDiagram = (org.eclipse.gmf.codegen.gmfgen.GenDiagram) contner;
				EClass eClass = genDiagram.getDomainDiagramElement()
						.getEcoreClass();
				if (LOG)
					clog("mapToGenEditorGenerator [" + eClass.getName() + "]");
				return eClass;
			}
		} catch (Exception e) {
			runner.cerror("getEcoreClass(): error");
		}
		return null;
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	public void generateAndValidateAndModifyGmfGen(StringBuffer log,
			IDiaPointOfView pointOfView, String layer, boolean rcp,
			IProgressMonitor progressMonitor) {
		// boolean rcp=true; //FP130507
		generateGmfGen(log, pointOfView, layer, rcp, progressMonitor); // FP130915zz
																				// //
																				// FP120620
		modifyGenEditorGeneratorParameters(pointOfView, layer, progressMonitor);
		modifyGenEditorGeneratorModifyStyles(pointOfView, layer,
				progressMonitor);
	}

	private DNode findDNode(String name) {
		for (DNode dNode : getNodes())
			if (dNode.getName().equals(name))
				return dNode;
		return null;
	}

	public void removeIconsFromNodesIfMultipleLabels( // FP130121
			IProgressMonitor progressMonitor) {
		if (progressMonitor != null) {
			progressMonitor.subTask("removeIconsFromNodesIfMultipleLabels");
			progressMonitor.worked(1);
		}
		for (GenTopLevelNode topLevelNode : genEditorGenerator.getDiagram()
				.getTopLevelNodes()) {
			// String mi = topLevelNode.getModelFacet().getMetaClass()
			// .getModelInfo();

			/*
			 * EClass domainMetaElement = topLevelNode.getModelFacet()
			 * .getMetaClass().getEcoreClass(); DNode dnod =
			 * findDNode(topLevelNode.getModelFacet().getMetaClass()
			 * .getEcoreClass().getName());
			 */// name:
				// String nodlabel = dnod.getLabel();
			for (int i = 1; i < topLevelNode.getLabels().size(); i++) {
				GenNodeLabel genNodeLabel = topLevelNode.getLabels().get(i);

				genNodeLabel.setElementIcon(false);
			}
		}
	}

	public void removeIconsFromEdgesIfMultipleLabels(
			IProgressMonitor progressMonitor) {
		if (progressMonitor != null) {
			progressMonitor.subTask("removeIconsFromEdgesIfMultipleLabels");
			progressMonitor.worked(1);
		}
		EList<GenLink> links = genEditorGenerator.getDiagram().getLinks();
		for (GenLink genLink : links) {
			if (genLink.getModelFacet() instanceof TypeModelFacet) {
				TypeModelFacet tmf = (org.eclipse.gmf.codegen.gmfgen.TypeModelFacet) genLink
						.getModelFacet();
				String mi = tmf.getMetaClass().getModelInfo();
				EClass domainMetaElement = tmf.getMetaClass().getEcoreClass();
				for (int i = 1; i < genLink.getLabels().size(); i++) {
					GenLinkLabel genLinkLabel = genLink.getLabels().get(i);
					genLinkLabel.setElementIcon(false);
				}

			}
		}
	}

}
