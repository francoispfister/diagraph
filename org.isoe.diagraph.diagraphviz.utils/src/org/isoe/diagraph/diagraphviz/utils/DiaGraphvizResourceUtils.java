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
package org.isoe.diagraph.diagraphviz.utils;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.isoe.extensionpoint.graphviz.GraphvizParams;
import org.isoe.extensionpoint.graphviz.IDotifier;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.platform.resolver.Activator;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.diagraph.DiagraphPackage;
import org.isoe.diagraph.views.ViewConstants;

/**
 *
 * @author pfister
 *
 */
public class DiaGraphvizResourceUtils implements GraphvizParams {

	// private Set<Integer> visitedNodes_;

	private static final boolean LOG = false;
	private Set<String> visitedNodes;
	int nodeOrder;

	// private static final String DIAGRAPH_WORKSPACE_LOCATION_FOR_TESTS___ =
	// "E:\\Apps\\eclipse-modeling-indigo-SR2-win32-a\\ws-d\\";
	private final static String SPACES = "                                                                                                     ";

	private ResourceSet resourceSet;

	private String outputFile;
	private String modelFile_;

	private String log;


	private URI diagraphM2Uri;
	private URI diagraphM1Uri;
	private URI modelM2Uri_;
	private URI modelM1Uri_;
	private DGraph dsmlConcreteSyntax;
	private EObject dsmlAbstractSyntax;
	private IDotifier handler;
	private EPackage metamodel;

	public EPackage getMetamodel() { // FP130328z
		if (metamodel==null){
			Resource m2resource = getResource(modelM2Uri_);
			metamodel = (EPackage) m2resource.getContents().get(0);
		}
		return metamodel;
	}

	public URI getModelM2Uri() {
		return modelM2Uri_;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String view_ = DEFAULT_VIEW;
		String xmlns = "hello";
		String domain = "hello";
		String eNS_URI = "http://hello.example.v0";
		String dslM2Plugin = "diagraph.zoo.hello";
		String dslM1Plugin = "diagraph.zoo.instances.hello";

		String modelName = "default";

		String thisWorkspace = Activator.getWorkspacePath();// getWorkspacePath();//E:\Apps\eclipse-diagraph-13\workspace\

		String outputPath =  DParams.MODEL_FOLDER_SEP + xmlns + "-model.dot";

		String inputPath_ = GraphvizParams.TEST_INSTANCE_FOLDER
				+ modelName + "." + xmlns;
		String dslwstest = Activator.getWorkspacePath();

		URI uriDslM2 = EMFPlugin.IS_ECLIPSE_RUNNING ? URI
				.createPlatformPluginURI(dslM2Plugin, true) : URI
				.createFileURI(dslwstest + dslM2Plugin);
		if (LOG)
			System.out.println(uriDslM2);

		String inputFile_ = thisWorkspace + dslM1Plugin + "/" + inputPath_;
		String outputFile = thisWorkspace + dslM1Plugin + "/" + outputPath;

		DiaGraphvizResourceUtils modelToGraph = new DiaGraphvizResourceUtils(
				null, inputFile_, outputFile, null);

		URI r = modelToGraph.loadDiagraphModels(uriDslM2, domain, xmlns,
				eNS_URI, view_, false);
		if (r == null)
			System.out.println("failed in main");
		// modelToGraph_.createVisualNotation();
		// modelToGraph_.toDotFile();
	}

	public DGraph getDsmlConcreteSyntax() {
		return dsmlConcreteSyntax;
	}

	public EObject getDsmlAbstractSyntax() {
		return dsmlAbstractSyntax;
	}

	public DiaGraphvizResourceUtils(ResourceSet rs, String inputFile_,
			String outputFile, IDotifier handler) {
		this.handler = handler;

		if (inputFile_!=null){
		 File ifil = new File(inputFile_);

		// E:\Apps\eclipse-diagraph-14b\workspace\diagraph.zoo.instances.hello\model\default.hello
		// E:\Apps\eclipse-diagraph-14b\workspace\diagraph.zoo.instances.hello/result/default.hello

		// M2 Location:
		// E:\Apps\eclipse-diagraph-14b\workspace\diagraph.zoo.hello\model\hello.ecore
		// E:\Apps\eclipse-diagraph-14b\workspace\diagraph.zoo.hello\model\hello.ecore

		// M1 Location:
		// E:\Apps\eclipse-diagraph-14b\workspace\diagraph.zoo.instances.hello\result\default.hello
		// E:\Apps\eclipse-diagraph-14b\workspace\diagraph.zoo.instances.hello\result\default.hello
		 if (!ifil.exists())
			throw new RuntimeException(inputFile_ + " not exists !!!");
		}

		// E:\Apps\eclipse-diagraph-13\workspace\org.isoe.diagraph.diagraphviz/model/helloworld_default_root.diagraph
		// not exists

		// E:\Apps\eclipse-diagraph-13\workspace\diagraph.sample.helloworld\model\helloworld_default_root.diagraph

		this.modelFile_ = inputFile_;
		this.outputFile = outputFile;
		if (rs == null)
			resourceSet = new ResourceSetImpl();
		else
			resourceSet = rs;
		resourceSet
				.getResourceFactoryRegistry()
				.getExtensionToFactoryMap()
				.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
						new EcoreResourceFactoryImpl());
		resourceSet.getPackageRegistry().put(DiagraphPackage.eNS_URI,
				DiagraphPackage.eINSTANCE);
	}

	// dslM2Uri=platform:/plugin/diagraph.zoo.hello

	// file:/E:/Apps/eclipse-diagraph-13/workspace/diagraph.iml5, iml5, iml5,
	// http//

	public URI loadDiagraphModels(URI dslM2Uri, String domain, String xmlns,
			String nsURI, String viewName, boolean diagraph) { // clean all the
																// redundant
																// checks
		URI result = null;
		String dslPlugin = dslM2Uri.toString();
		dslPlugin = dslPlugin.substring(dslPlugin.lastIndexOf("/") + 1);
		String dname = domain;//FP130401x xmlns;
		result = createDiagraphResources(dslPlugin, dname, viewName);
		if (result != null) {
			if (LOG)
				System.out.println("dslm2PluginURI="
						+ CommonPlugin.resolve(dslM2Uri).toFileString());

			modelM2Uri_ = dslM2Uri.appendSegments(new String[] {  DParams.MODEL_FOLDER,
					dname + ".ecore" }); ////FP130401x xmlns;
			if (!EMFPlugin.IS_ECLIPSE_RUNNING)
				checkUri(resourceSet, modelM2Uri_);

			if (modelFile_!=null){
			   modelM1Uri_ = URI.createFileURI(new File(modelFile_)
					.getAbsolutePath());
			   if (!EMFPlugin.IS_ECLIPSE_RUNNING)
				  checkFile(modelM1Uri_);
			}
			dsmlAbstractSyntax = getModel(nsURI, modelM1Uri_,
					diagraph);
			if (viewName != null)
				dsmlConcreteSyntax = loadDGraph(dslM2Uri, dname, viewName); ///FP130401x xmlns;
		}

		return result;
	}

	private void cleanResourceSet(EObject model) {

	}



	public EObject loadEmfModels(URI dslM2Uri, String domain, String xmlns,
			String nsURI, boolean diagraph) {
		String dslPlugin = dslM2Uri.toString();
		dslPlugin = dslPlugin.substring(dslPlugin.lastIndexOf("/") + 1);
		String dName = domain;//xmlns; //FP130401xx
		if (LOG)
			System.out.println("dslm2PluginURI="
					+ CommonPlugin.resolve(dslM2Uri).toFileString());

		modelM2Uri_ = dslM2Uri.appendSegments(new String[] {  DParams.MODEL_FOLDER,
				dName + ".ecore" }); //FP130401xx
		if (!EMFPlugin.IS_ECLIPSE_RUNNING)
			checkUri(resourceSet, modelM2Uri_);
		if (modelFile_!=null){
		   modelM1Uri_ = URI.createFileURI(new File(modelFile_).getAbsolutePath());
		   if (!EMFPlugin.IS_ECLIPSE_RUNNING)
			checkFile(modelM1Uri_);
		}
		// EObject result=loadModel(modelM2Uri, nsURI, modelM1Uri,diagraph);
		EObject result = getModel(nsURI, modelM1Uri_, diagraph); // FP130205
		cleanResourceSet(result); // to clean
		return result;
	}

	private EObject getModel(String nsURI_, URI uriM1,
			boolean diagraph) { // FP130205
		if (uriM1 !=null){
		   EObject model = getModelFromResourceSet_(uriM1);
		   if (model == null)
			 model = loadModel_(modelM2Uri_, nsURI_, uriM1, diagraph);
		   return model;
		}
		return null;
	}

	private EObject getModelFromResourceSet_(final URI uri) {
		for (Resource resource : resourceSet.getResources())
			if (resource.getURI().toString().equals(uri.toString()))
				if (!resource.getContents().isEmpty()) // FP130328z
					return resource.getContents().get(0);
		return null;
	}

	private Resource getMatchingResourceFromResourceSet(URI uri) { // FP130328z
		for (Resource resource : resourceSet.getResources())
			if (resource.getURI().toString().equals(uri.toString()))
				return resource;
		return null;
	}

	private Resource getResource(URI uri) {// FP130328z
		// return getMatchingResourceFromResourceSet(uri);
		EObject model = getModelFromResourceSet_(uri);
		EList<EObject> contents = null;
		if (model == null) {
			contents = loadModel(resourceSet, uri).getContents();
			if (!contents.isEmpty())
				model = contents.get(0);
		}
		return model.eResource();
	}

	private DGraph loadDGraph(URI uribase, String domain, String pov) {
		DGraph m1Diagraph = null;
		URI uriM1Diagraph = uribase.appendSegments(new String[] {DParams.MODEL_FOLDER, domain + ViewConstants.VIEW_SEPARATOR_0 + pov + ViewConstants.VIEW_SEPARATOR_1+ViewConstants.ROOT_NAME+"."+DParams.DIAGRAPH_SUFX });
		if (LOG) {
			System.out.println(CommonPlugin.resolve(uriM1Diagraph)); // jar:file:/E:/Apps/eclipse-modeling-indigo-SR2-win32-g/eclipse/plugins/org.eclipse.emf.ecore_2.7.0.v20120127-1122.jar!/model/Ecore.ecore
		}
		String diagraphloc = CommonPlugin.resolve(uriM1Diagraph).toFileString();
		if (diagraphloc != null) {
			if (LOG)
				System.out.println(diagraphloc);
			try {
				URI dlocuri = URI.createFileURI(new File(diagraphloc)
						.getAbsolutePath());
				Resource rsr = getResource(dlocuri); // FP130205
				m1Diagraph = (DGraph) rsr.getContents().get(0);
				if (LOG)
					System.out.println(m1Diagraph);
			} catch (Exception e) {
				throw new RuntimeException("error in loadDGraph");
			}
		}
		return m1Diagraph;
	}

	private EObject loadModel_(URI uriM2, String nsURI, URI uriM1,
			boolean diagraph_) {
		EPackage m2 = null;
		String m2fs = CommonPlugin.resolve(uriM2).toFileString();
		if (LOG)
			System.out.println("M2 Location: " + m2fs);
		String m1fs = CommonPlugin.resolve(uriM1).toFileString();
		if (LOG)
			System.out.println("M1 Location: " + m1fs);
		EObject m1_ = null;
		if (m2fs != null) {


			URI m2fsuri = URI.createFileURI(new File(m2fs).getAbsolutePath());
			Resource m2resource = getResource(m2fsuri);
			if (m2resource == null)
				throw new RuntimeException("M2 resource not found");
			m2 = (EPackage) getResource(m2fsuri).getContents().get(0); // FP130205

			if (LOG)
				System.out.println("M2 Loaded: package " + m2.getName());
			EList<EClassifier> classes = m2.getEClassifiers();
			if (LOG) {
				String classez = "";
				for (EClassifier eClassifier : classes)
					classez += eClassifier.getName() + ";";
				System.out.println("classifiers:" + classez);
			}

			// if (nsURI.equals(DiagraphPackage.eNS_URI))
			if (diagraph_) {
				EcorePackage theEcorePackage = (EcorePackage) EPackage.Registry.INSTANCE
						.getEPackage(EcorePackage.eNS_URI);
				resourceSet
						.getResourceFactoryRegistry()
						.getExtensionToFactoryMap()
						.put(DiagraphPackage.eNAME,
								new XMIResourceFactoryImpl());
				resourceSet.getPackageRegistry().put(EcorePackage.eNS_URI,
						EcorePackage.eINSTANCE);
				resourceSet.getPackageRegistry().put(DiagraphPackage.eNS_URI,
						DiagraphPackage.eINSTANCE);
			} else {
				metamodel = m2;
				resourceSet.getPackageRegistry().put(nsURI, m2); // FP130128 why
																	// not the
																	// same
																	// behaviour
																	// =>
																	// DiagraphPackage.eINSTANCE
																	// && m2,
																	// same
																	// eclass
																	// but are
																	// not the
																	// same
																	// objects
			}

			if (LOG)
				System.out.println("register package: " + m2.getName()
						+ " in key: " + nsURI);
			System.out.println("loading model from file: " + uriM1.toString());

			Resource rsrc_ = getResource(uriM1); // FP130205

			m1_ = rsrc_.getContents().get(0); // FP130126
			DynamicEObjectImpl dynamicEObject = null;
			if (m1_ instanceof org.eclipse.emf.ecore.impl.DynamicEObjectImpl)
				dynamicEObject = (DynamicEObjectImpl) m1_;
			if (LOG) {
				System.out.println("model: "
						+ (dynamicEObject == null ? "" : " (dynamic) ")
						+ m1_.toString());
			} else if (m1_ == null)
				throw new RuntimeException("nul error while loadModel " + nsURI);
		}
		return m1_;
	}

	/*
	 * M2 Location:
	 * E:\Apps\eclipse-diagraph-13\workspace\diagraph.iml5\model\iml5.ecore M1
	 * Location:
	 * E:\Apps\eclipse-diagraph-13\workspace\diagraph.instances.iml5\instances
	 * \default2.iml5 M2 Loaded: package iml5
	 * classifiers:Entity;Datatype;NamedElement
	 * ;Namespace;Reference;Type;Attribute; register package: iml5 in key:
	 * http://www.isoe.lgi2p.ema.fr/diagraph/example/iml5 loading model from
	 * file:
	 * file:/E:/Apps/eclipse-diagraph-13/workspace/diagraph.instances.iml5/
	 * instances/default2.iml5 model: (dynamic)
	 * org.eclipse.emf.ecore.impl.DynamicEObjectImpl@c6bf80 (eClass:
	 * org.eclipse.emf.ecore.impl.EClassImpl@17e78f7 (name: Namespace)
	 */

	private Resource loadModel(ResourceSet resourceSet, URI uri) { // FP130205
		Resource result = null;
		try {
			result = resourceSet.getResource(uri, true);
			result.load(null);
			// System.out.println("loadModel "+uri.toString()); //FP130205
			if (handler != null)
				handler.log("loadModel " + uri.toString());
			EcoreUtil.resolveAll(resourceSet);
		} catch (IOException e) {
			throw new RuntimeException(" error while loading model "
					+ uri.toString());
		}
		return result;
	}

	private URI createDiagraphResources(String samplePlugin,
			String sampleDomain, String view) {
		// boolean eclipseRunning=EMFPlugin.IS_ECLIPSE_RUNNING;
		URI result = null;
		createPluginDiagraphUri();
		if (view != null) {
			result = createPlatformPluginDGraphUri(samplePlugin, sampleDomain,
					view);
		}
		return result;
	}

	private URI createPlatformPluginDGraphUri(String dslM2Plugin,
			String dslDomain, String pointOfView) {
		// diagraphM1Uri
		String dslwstest = Activator.getWorkspacePath();

		URI diagm1uri = EMFPlugin.IS_ECLIPSE_RUNNING ? URI
				.createPlatformPluginURI(dslM2Plugin, true) : URI
				.createFileURI(dslwstest + dslM2Plugin);

		diagraphM1Uri = diagm1uri.appendSegments(new String[] {  DParams.MODEL_FOLDER,
				dslDomain + "_" + pointOfView + "_root.diagraph" });
		if (LOG)
			System.out.println(diagraphM1Uri);
		try {
			if (!EMFPlugin.IS_ECLIPSE_RUNNING) {
				checkFile(diagraphM1Uri);
				checkUri(resourceSet, diagraphM1Uri);
			}
			return diagraphM1Uri;
		} catch (Exception e) {
			return null;
		}

	}

	private void checkFile(URI uri) {

	}
	/*

	private void checkFile_old(URI uri) {
		File check = new File(uri.toFileString()); // E:\Apps\eclipse-temp-test-vincent\runtime-EclipseApplication\diagraph.zoo.hello\model\hello_default_root.diagraph
		if (!check.exists())
			throw new RuntimeException("error in checkFile "
					+ uri.toFileString() + " not found");
	}
	*/

	private void checkUri(ResourceSet resourceSet, URI uri) {

	}

	/*
	private void checkUri_old(ResourceSet resourceSet, URI uri) {
		Resource result = null;
		try {
			result = resourceSet.getResource(uri, true);
			result.load(null);
			EcoreUtil.resolveAll(resourceSet);
		} catch (Exception e) {
			if (LOG) {
				System.out.println("****URI BAD");
				System.out.println("error " + e.getMessage()
						+ " in checkuri for " + uri.toString());
				System.out.println("URI BAD****");
			}
			throw new RuntimeException("error " + e.getMessage()
					+ " in checkuri for " + uri.toString());
		}
		if (result != null) {
			if (LOG) {
				System.out.println("****URI OK");
				System.out.println(uri.toString());
				System.out.println(result.getContents().get(0));
				System.out.println("URI OK****");
			}
		}
	}

*/
	private URI createPluginDiagraphUri() {
		if (diagraphM2Uri == null) {
			diagraphM2Uri = DiagraphPackage.eINSTANCE.eResource().getURI();
		}
		return diagraphM2Uri;
	}

	public static Resource getResource(String metamodelPath) {
		ResourceSet metamodelSet = new ResourceSetImpl();
		metamodelSet
				.getResourceFactoryRegistry()
				.getExtensionToFactoryMap()
				.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
						new EcoreResourceFactoryImpl());
		URI metamodelURI = URI.createFileURI(new File(metamodelPath)
				.getAbsolutePath());
		return metamodelSet.getResource(metamodelURI, true);
	}

	public static Resource getResource2(URI metamodelURI) {
		ResourceSet metamodelSet = new ResourceSetImpl();
		metamodelSet
				.getResourceFactoryRegistry()
				.getExtensionToFactoryMap()
				.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
						new EcoreResourceFactoryImpl());
		return metamodelSet.getResource(metamodelURI, true);
	}

	public static Resource getModel(String modelPath, Resource metamodel) {
		ResourceSet modelSet = new ResourceSetImpl();
		modelSet.getResourceFactoryRegistry()
				.getExtensionToFactoryMap()
				.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
						new EcoreResourceFactoryImpl());
		Iterator<EObject> i = metamodel.getAllContents();
		EPackage p = (EPackage) i.next();
		modelSet.getPackageRegistry().put(p.getNsURI(), p);

		URI modelURI = URI.createFileURI(new File(modelPath).getAbsolutePath());
		return modelSet.getResource(modelURI, true);
	}

	public Resource getModel2(String modelPath, Resource metamodel) {
		ResourceSet modelSet = new ResourceSetImpl();
		modelSet.getResourceFactoryRegistry()
				.getExtensionToFactoryMap()
				.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
						new EcoreResourceFactoryImpl());
		Iterator<EObject> i = metamodel.getAllContents();
		EPackage p = (EPackage) i.next();
		modelSet.getPackageRegistry().put(p.getNsURI(), p);
		URI modelURI = URI.createFileURI(new File(modelPath).getAbsolutePath());
		return modelSet.getResource(modelURI, true);
	}

}

/*
 * void addLinkEdgeAndObject__old(StringBuffer buf, EObject parent, EReference
 * feature, DNode node, DLink dlink, int depth) {
 * buf.append("\naddLinkEdgeAndObject " + node.getName() + " - " +
 * dlink.getName()); try { EReference featureToAssoclass = (EReference) parent
 * .eClass() .getEStructuralFeature(
 * dlink.getSourceReference().getEOpposite().getName()); Object cref =
 * parent.eGet(featureToAssoclass); // Bonjour EAttribute lattr =
 * dlink.getLabelAttribute(); if (cref instanceof EList) { EList<EObject>
 * childcreflist = (EList<EObject>) cref; for (EObject child : childcreflist) {
 * buf.append("\n" + "handleChildLink " + child); String lab = (String)
 * child.eGet(lattr); buf.append("\n" + lab); // manage visited, avoid cycles //
 * addNodeIfNotVisited(buf,parent,child, node, depth);
 *
 * } } else if (cref instanceof EObject) { EObject child = (EObject) cref;
 * buf.append("\n" + "handleChildLink " + child); String lab = (String)
 * child.eGet(lattr); buf.append("\n" + lab); //
 * addNodeIfNotVisited(buf,parent,child, node, depth);
 *
 * // System.out.println(childcrefInstance);//addNodeIfNotVisited_(buf,(EObject)
 * // cref,eobj, node, depth); // Object lato=childcrefInstance.eGet(lattr); //
 * System.out.println(lato); }
 *
 * } catch (Exception e) { buf.append("\n******** error " + e.getMessage()); } }
 */
