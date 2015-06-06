/**
 * Copyright (c) 2014 Laboratoire de Genie Informatique et Ingenierie de Production - Ecole des Mines d'Ales
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Francois Pfister (ISOE-LGI2P)
 *    adapted from http://javalib.wordpress.com/2010/07/05/parsing-xmi/
 */
package org.isoe.fwk.deployer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.isoe.fwk.integration.IntegrationConfiguration;
import org.isoe.mbse.sourcecleaner.Extension;
import org.isoe.mbse.sourcecleaner.ExtensionPoint;
import org.isoe.mbse.sourcecleaner.ExtensionReference;
import org.isoe.mbse.sourcecleaner.Java;
import org.isoe.mbse.sourcecleaner.Project;
import org.isoe.mbse.sourcecleaner.Schema;
import org.isoe.mbse.sourcecleaner.SourcecleanerFactory;


/**
 *
 * @author pfister
 *
 */
public class PluginParser {

	private static final boolean LOG = true;
	private static final boolean LOG2 = false;
	private static final boolean LOG3 = false;
	private static final boolean LOG4 = false;

	private static final String NAME = "name";
	private static final String CLASS = "class";
	private static final String ID = "id";
	private static final String SCHEMA = "schema";
	private static final String VALUE = "value";
	private static final String EXTENSION = "extension";
	private static final String POINT = "point";

	private static PluginParser instance;
	private String fileName;
	private String name;
	private String root;
	private IntegrationConfiguration configuration;
	private Project project;

	private XPathExpression seqrefs, choicerefs,
			popupMenuObjectContributionIdExpr, popupMenuExtension_,
			popupMenuNameFilterExpr, popupMenuObjectClassExpr,
			popupMenuActionClassExpr;

	private XPath xpath;

	private void addExtension_(Project project, Extension extension) {
		configuration.addExtension(project, extension);
	}

	// C:\workspaces\v130926\org.isoe.diagraph.notationparser\META-INF\MANIFEST.MF

	String[] DIAGRAPH_EXTENSIONS = { "org.isoe.diagraph.graphviz",
			"org.isoe.extensionpoint.diagraph.action",
			"org.isoe.extensionpoint.ecoreutils",
			"org.isoe.extensionpoint.diagraph.generator",
			"org.isoe.diagraph.gramgen", "org.isoe.diagraph.syntaxgenerator",
			"org.isoe.modelrunner", "org.isoe.diagraph.parsers",
			"org.isoe.extensionpoint.languagehandler",
			"org.isoe.diagraph.remoting", "org.isoe.diagraph.syntaxrules",
			"org.isoe.extensionpoint.similarity.diagraph_similarity",
			"org.isoe.fwk.automation", "org.isoe.transformations",
			"org.isoe.fwk.eclipse.launcher", "org.isoe.xsl.transformer",
			"org.isoe.extensionpoint.diagen",
			"org.isoe.diagraph.deploymegamodel", "org.isoe.diagraph.layout",
			"org.isoe.metamodelretriever", "org.isoe.my.sample.extension" };
	private String pluginPath;
	private boolean tb;

	/*
	 * org.isoe.diagraph.graphviz org.isoe.extensionpoint.diagraph.action
	 * org.isoe.extensionpoint.ecoreutils
	 * org.isoe.extensionpoint.diagraph.generator org.isoe.diagraph.gramgen
	 * org.isoe.diagraph.syntaxgenerator org.isoe.modelrunner
	 * org.isoe.diagraph.parsers org.isoe.extensionpoint.languagehandler
	 * org.isoe.diagraph.remoting org.isoe.diagraph.syntaxrules
	 * org.isoe.extensionpoint.similarity.diagraph_similarity
	 * org.isoe.fwk.automation org.isoe.transformations
	 * org.isoe.fwk.eclipse.launcher org.isoe.xsl.transformer
	 * org.isoe.extensionpoint.diagen org.isoe.diagraph.deploymegamodel
	 * org.isoe.diagraph.layout org.isoe.metamodelretriever
	 * org.isoe.my.sample.extension
	 *
	 * org.eclipse.core.contenttype.contentTypes org.eclipse.ui.popupMenus
	 * org.eclipse.emf.ecore.generated_package
	 * org.eclipse.emf.edit.itemProviderAdapterFactories
	 * org.eclipse.ui.newWizards org.eclipse.ui.editors org.eclipse.ui.views
	 * org.eclipse.ui.actionSets org.eclipse.ui.preferencePages
	 * org.eclipse.core.runtime.preferences org.eclipse.ui.perspectives
	 * org.eclipse.ui.startup org.eclipse.gmf.runtime.diagram.ui.layoutProviders
	 * org.eclipse.help.toc org.eclipse.debug.core.launchConfigurationTypes
	 * org.eclipse.debug.ui.launchConfigurationTypeImages
	 * org.eclipse.debug.ui.launchConfigurationTabGroups
	 * org.eclipse.debug.ui.launchShortcuts
	 */

	public String getRoot() {
		return root;
	}

	public PluginParser(IntegrationConfiguration configuration) {
		this.configuration = configuration;

		XPathFactory xFactory = XPathFactory.newInstance();
		xpath = xFactory.newXPath();
		try {
			seqrefs = xpath
					.compile("/schema/element[@name='extension']/complexType/sequence/element/@ref");
			choicerefs = xpath
					.compile("/schema/element[@name='extension']/complexType/choice/element/@ref");
			popupMenuObjectContributionIdExpr = xpath
					.compile("/plugin/extension[@point='org.eclipse.ui.popupMenus']/objectContribution/@id");
			popupMenuExtension_ = xpath
					.compile("/plugin/extension[@point='org.eclipse.ui.popupMenus']");
			popupMenuNameFilterExpr = xpath
					.compile("/plugin/extension[@point='org.eclipse.ui.popupMenus']/objectContribution/@nameFilter");
			popupMenuObjectClassExpr = xpath
					.compile("/plugin/extension[@point='org.eclipse.ui.popupMenus']/objectContribution/@objectClass");
			popupMenuActionClassExpr = xpath
					.compile("/plugin/extension[@point='org.eclipse.ui.popupMenus']/objectContribution/action/@class");

		} catch (XPathExpressionException e) {

			e.printStackTrace();
		}

	}

	public String getName() {
		return name;
	}


	public void parseSchema(Schema schma) {
		tb = false;

		if (schma.getName().equals("diagraph_similarity"))
			tb = true;

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			factory.setNamespaceAware(false);

			DocumentBuilder docBuilder = factory.newDocumentBuilder();
			Document doc = docBuilder.parse(schma.getAbsolutePath());

			doc.getDocumentElement().normalize();

			XPathExpression extensionId = xpath
					.compile("/schema/annotation/appinfo/meta.schema[@id='"
							+ schma.getName() + "']/@id");

			Node idNode = (Node) extensionId.evaluate(doc, XPathConstants.NODE);
			if (idNode != null)
				schma.setExtensionId(idNode.getNodeValue());
			else
				configuration.addError("no extension id in schema "
						+ schma.getName());

			XPathExpression extensionName = xpath
					.compile("/schema/annotation/appinfo/meta.schema[@id='"
							+ schma.getName() + "']/@name");
			Node nameNode = (Node) extensionName.evaluate(doc,
					XPathConstants.NODE);

			if (nameNode != null)
				schma.setExtensionName(nameNode.getNodeValue());
			else
				configuration.addError("no extension name in schema "
						+ schma.getName());

			XPathExpression pluginName = xpath
					.compile("/schema/annotation/appinfo/meta.schema[@id='"
							+ schma.getName() + "']/@plugin");
			Node pluginNode = (Node) pluginName.evaluate(doc,
					XPathConstants.NODE);

			if (pluginNode != null)
				schma.setPluginName(pluginNode.getNodeValue());
			else
				configuration.addError("no plugin name in schema "
						+ schma.getName());

			parseSchemaReferences(project, schma, doc);
		} catch (SAXParseException err) {
			configuration.addError("** Parsing error" + ", line "
					+ err.getLineNumber() + ", uri " + err.getSystemId());
			throw new RuntimeException(err.getMessage());
		} catch (SAXException e) {
			Exception x = e.getException();
			((x == null) ? e : x).printStackTrace();
			throw new RuntimeException(e.getMessage());
		} catch (Throwable t) {
			if (!t.toString().contains("java.io.FileNotFoundException")){
			  t.printStackTrace();
			  throw new RuntimeException(t.getMessage());
			} else
				clog("parseSchema error "+t.toString());
		}

	}

	public void initPlugin(Project project, String path) {
		fileName = path;
		this.project = project;
		name = fileName.substring(0, fileName.lastIndexOf(File.separator));
		name = name.substring(name.lastIndexOf(File.separator) + 1);
	}

	public void parsePluginAndSchema() {
		boolean tb;
		try {
			if (project.getName().equals("org.isoe.diagraph.notationparser"))
				tb = true;
			if (project.getName().equals("org.eclipse.m2m.qvt.oml"))
				tb = true;
			File fil = new File(fileName);
			String absolutePath = fileName.substring(0,
					fileName.lastIndexOf(File.separator));
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(fil);
			doc.getDocumentElement().normalize();
			Element rootElement = doc.getDocumentElement();
			root = doc.getDocumentElement().getNodeName();
			pluginPath = absolutePath;// fileName;

			List<Extension> exts = parseContributions(doc, rootElement);

			parseExtensionIfExists(rootElement);

		} catch (SAXParseException err) {
			configuration.addError("** Parsing error" + ", line "
					+ err.getLineNumber() + ", uri " + err.getSystemId());
			throw new RuntimeException(err.getMessage());
		} catch (SAXException e) {
			Exception x = e.getException();
			((x == null) ? e : x).printStackTrace();
			throw new RuntimeException(e.getMessage());
		} catch (Throwable t) {
			if (!t.toString().contains("no schemaPath")){
				  t.printStackTrace();
				  throw new RuntimeException(t.getMessage());
				} else
					clog("parsePluginAndSchema error "+t.toString());
		}
	}

	private boolean parseExtensionIfExists(Element rootElement) {
		NodeList extpoints = rootElement
				.getElementsByTagName("extension-point");
		if (extpoints.getLength() == 1) {
			clog3("----- parsing extension point " + project.getName());
			parseExtensionPointAndSchema(project, 0,extpoints.item(0));
			return true;
		} else if (extpoints.getLength() > 1){
			clog(project.getName()+" has "+extpoints.getLength()+" extension points ");
		    for (int i=0;i<extpoints.getLength();i++){
			  String s= ((Element) extpoints.item(i)).getAttribute(NAME);
			  if ("Java blackbox compilation units".equals(s))
				  tb = true;
			  clog("EP="+ s);
			  
			  parseExtensionPointAndSchema(project, i,extpoints.item(i));
		    }
			//if (!"org.eclipse.m2m.qvt.oml".equals(project.getName()))
			 //  throw new RuntimeException("should not happen in parse plugin");
		}
		return false;
	}

	private String getPluginPath() {
		return pluginPath;
	}

	private String readFile(File infile) {
		String result = "";
		try {
			InputStream in = new FileInputStream(infile);
			StringBuffer buffer = new StringBuffer();
			try {
				InputStreamReader inR = new InputStreamReader(in);
				BufferedReader buf = new BufferedReader(inR);
				String line;
				while ((line = buf.readLine()) != null) {
					result += line + "\n";
				}
			} finally {
				in.close();
			}
		} catch (IOException e) {
			configuration.addError("error " + e.toString());
		}
		return result;
	}

	private void parseExtensionPointAndSchema(Project prj, int n, Node extensionpoint) { // az14
boolean tb = true;
		Element exp = (Element) extensionpoint;
		String id = exp.getAttribute(ID);
		String name = exp.getAttribute(NAME);
		String schema = exp.getAttribute(SCHEMA);

		String schemaPath = getPluginPath() + File.separator + schema;
		schemaPath = schemaPath.replace("/", File.separator);
		Schema schma = configuration.createSchema(prj, schemaPath);

		ExtensionPoint ep = SourcecleanerFactory.eINSTANCE
				.createExtensionPoint();
		ep.setPlugin(prj.getPlugin());
		ep.setId(id);
		ep.setName(name);
		ep.setSchema(schema);
		clog("extension point id=" + id + " name=" + name + " schema="
				+ schema);

		prj.getPlugin().getExtensionPoints().add(ep); //FP141216
		// configuration.getConfiguration().getExtensionPoints().add(ep);
		clog3(epLog(ep));
        if (!"org.eclipse.m2m.qvt.oml.common".equals(prj.getName())){
		  parseSchema(schma);
		  if (!validate(schma, ep))
			tb = true;
       }

	}

	private boolean match_(ExtensionReference extensionReference) {
		tb = true;
		String schemaname = extensionReference.getSchema().getName();// org.isoe.yet.another.sample
		String projectname = extensionReference.getSchema().getProject()
				.getName();// org.isoe.another.sample
		if (schemaname.equals("org.isoe.yet.another.sample"))
			tb = true;
		if (projectname.contains("another"))
			tb = true;
		//String erjav = extensionReference.getJava(); // ISimilarityService
		//String erpak = extensionReference.getPackage();
		String referenceProject = extensionReference.getProject();
		String currentProject = extensionReference.getSchema().getProject()
				.getName();
		for (Project project : configuration.getConfiguration().getProjects()) {
			for (Java java : project.getSources()) {
				String javaname = java.getName();
				String javapack = java.getPackage();
				if (javaname.equals(extensionReference.getJava()))
					if (javapack.equals(extensionReference.getPackage())) {
						extensionReference.setJavaclass(java);
						if (!referenceProject.equals(currentProject))
							configuration.addError("+++ match " + javaname
									+ " in another project: external:"
									+ referenceProject + " vs current:"
									+ currentProject);
						return true;
					}
			}
		}
		return false;
	}

	private boolean validate(Schema schma, ExtensionPoint ep) {
		tb = true;
		boolean result = true;
		boolean allProjectsYes = true;
		// ISimilarityService does not exist in extension point
		// org.isoe.extensionpoint.gramgen

		if (schma.getName().equals("org.isoe.diagraph.gramgen"))
			tb = true;
		if (schma.getProject().getName()
				.equals("org.isoe.extensionpoint.gramgen"))
			tb = true;

		for (ExtensionReference extensionReference : schma.getReferences())
			if (!match_(extensionReference)) {
				configuration.addError("--- exsd Schema validation, "
						+ extensionReference.getJava()
						+ " does not exist in extension point "
						+ schma.getProject().getName());
				result = false;
			}

		clog4("-----schema-------");
		clog4("name=" + schma.getName()); // name=org.isoe.extensionpoint.automation
		clog4("id=" + schma.getExtensionId()); // id=org.isoe.fwk.automation
		clog4("extname=" + schma.getExtensionName()); // extname=AutomationServer

		for (ExtensionReference extensionReference : schma.getReferences()) {
			clog4("   refprj=" + extensionReference.getProject());
			clog4("   refjava=" + extensionReference.getJava());
		}

		clog4("-----extension point -------");
		clog4("id=" + ep.getId());// id=org.isoe.fwk.automation
		clog4("name=" + ep.getName()); // name=AutomationServer

		if (schma.getExtensionId() == null)
			configuration.addError("schema id is null for " + schma.getName());
		else if (!schma.getExtensionId().equals(ep.getId()))
			configuration.addError("schema id does not match extension point id: "
					+ schma.getExtensionId());

		if (schma.getExtensionName() == null)
			configuration.addError("schema name is null for " + schma.getName());
		else if (!schma.getExtensionName().equals(ep.getName()))
			configuration.addError("schema name does not match extension point name: "
							+ schma.getExtensionName());

		List<Extension> extensions = ep.getExtensions();
		for (Extension extension : extensions) {
			String clazz = extension.getClazz();
			String pid = extension.getPointId();
			Java java = extension.getImplements();
			tb = true;
		}

		return result;

	}

	private void clog4(String mesg) {
		if (LOG4)
			System.out.println(mesg);

	}

	private String epLog(ExtensionPoint ep) {
		return "id=" + ep.getId() + " name=" + ep.getName() + " schema="
				+ ep.getSchema();
	}



	private String evaluateNode(Document doc, XPathExpression xp)
			throws XPathExpressionException {
		String result = null;
		Node n = (Node) xp.evaluate(doc, XPathConstants.NODE);
		if (n != null)
			result = n.getNodeValue();
		return result;
	}

	private List<String> evaluateNodeSet(Document doc, XPathExpression xp)
			throws XPathExpressionException {
		List<String> result = new ArrayList<String>();
		NodeList refnodes = (NodeList) xp.evaluate(doc, XPathConstants.NODESET);
		for (int a = 0; a < refnodes.getLength(); a++)
			result.add(refnodes.item(a).getNodeValue());
		return result;
	}

	private List<Node> getNodeSet(Document doc, XPathExpression xp)
			throws XPathExpressionException {
		List<Node> result = new ArrayList<Node>();
		NodeList refnodes = (NodeList) xp.evaluate(doc, XPathConstants.NODESET);
		for (int a = 0; a < refnodes.getLength(); a++)
			result.add(refnodes.item(a));
		return result;
	}



	private List<Extension> parseUiActionSets(Document doc, Element ext,
			String pointId) {
		List<Extension> result = new ArrayList<Extension>();
		try {
			XPathExpression actionSetExtensionExpr = xpath
					.compile("/plugin/extension[@point='org.eclipse.ui.actionSets']");
			List<String> actionSetExtensions = evaluateNodeSet(doc,
					actionSetExtensionExpr);
			if (actionSetExtensions.size() > 0)
				clog4("parseUiActionSets for project " + project.getName());
			XPathExpression actionSetIdsExpr = xpath
					.compile("/plugin/extension[@point='org.eclipse.ui.actionSets']/actionSet/@id");
			List<String> actionSetIds = evaluateNodeSet(doc, actionSetIdsExpr);
			for (String actionSetId : actionSetIds) {
				XPathExpression actionsIdExpr = xpath
						.compile("/plugin/extension[@point='org.eclipse.ui.actionSets']/actionSet[@id='"
								+ actionSetId + "']/action/@id");
				List<String> actionsIds = evaluateNodeSet(doc, actionsIdExpr);
				for (String actionId : actionsIds) {
					XPathExpression actionsClassExpr = xpath
							.compile("/plugin/extension[@point='org.eclipse.ui.actionSets']/actionSet[@id='"
									+ actionSetId
									+ "']/action[@id='"
									+ actionId + "']/@class");
					List<String> actionsClasses = evaluateNodeSet(doc,
							actionsClassExpr);
					for (String clazz : actionsClasses) {
						clog4(clazz);
						if (clazz
								.equals("org.isoe.diagraph.menu.handlers.DiagraphWorkbenchAction"))
							tb = true;
						// org.isoe.diagraph.menu.handlers.DiagraphWorkbenchAction
						// project org.isoe.diagraph.menu

						Java impl = getImplementation(clazz);

						if (impl != null) {
							clog4("impl: " + clazz);
							Extension extension = SourcecleanerFactory.eINSTANCE
									.createExtension();
							extension.setId(actionId);
							extension.setClazz(impl.getName());
							extension.setPointId(pointId);
							result.add(extension);
						} else
							System.err
									.println("no implementation for UiActionSet "
											+ clazz
											+ " actionId="
											+ actionId
											+ " project=" + project.getName());

					}
				}
			}
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return result;
	}

	private List<Extension> parseUiPopup(Document doc, Element ext,
			String pointId) {

		List<Extension> result = new ArrayList<Extension>();
		try {
			List<String> popupMenuExtensions = evaluateNodeSet(doc,
					popupMenuExtension_);

			if (popupMenuExtensions.size() > 0) {
				clog4("parseUiPopup for project " + project.getName());
			}

			String objectContributionId = evaluateNode(doc,
					popupMenuObjectContributionIdExpr);
			String objectContributionFilter = evaluateNode(doc,
					popupMenuNameFilterExpr);
			String popupMenuObjectClass = evaluateNode(doc,
					popupMenuObjectClassExpr);
			List<String> classes = evaluateNodeSet(doc,
					popupMenuActionClassExpr);
			clog4("objectContributionId=" + objectContributionId);
			clog4("objectContributionFilter=" + objectContributionFilter);
			clog4("popupMenuObjectClass=" + popupMenuObjectClass);

			String extra = "objectContributionId=" + objectContributionId;
			extra += " objectContributionFilter=" + objectContributionFilter;
			extra += " popupMenuObjectClass=" + popupMenuObjectClass;

			project.getPlugin().setExtra(extra);

			for (String clazz : classes) {
				clog4("class=" + clazz);
				XPathExpression actionIdExpr = xpath
						.compile("/plugin/extension[@point='org.eclipse.ui.popupMenus']/objectContribution/action[@class='"
								+ clazz + "']/@id");
				String actionId = evaluateNode(doc, actionIdExpr);
				Java impl = getImplementation(clazz);
				if (impl == null)
					configuration.addError("no implementation for UiPopup " + clazz
							+ " actionId=" + actionId);
				else {
					clog4("impl: " + clazz);
					Extension extension = SourcecleanerFactory.eINSTANCE
							.createExtension();
					extension.setId(objectContributionId);
					extension.setClazz(impl.getName());
					extension.setPointId(pointId);

					result.add(extension);
				}
			}
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return result;
	}

	private Java getImplementation(String clazz) {
		tb = false;
		if (clazz.contains("DiagraphWorkbenchAction"))
			tb = true;

		int e = clazz.lastIndexOf(".");
		String javaclaz = clazz.substring(e + 1);// DiagraphWorkbenchAction
		String javapack = clazz.substring(0, e); // org.isoe.diagraph.menu.handlers
		List<Java> javas = project.getSources();

		// DiagraphWorkbenchAction

		for (Java java : javas) {
			if (java.getPackage().equals(javapack))
				if (java.getName().equals(javaclaz))
					return java;
		}
		return null;
	}

	private List<Extension> parseUiViews(Document doc, Element ext,
			String pointId) {
		clog4("parseUiViews for project " + project.getName());
		List<Extension> result = new ArrayList<Extension>();
		NodeList echs = ext.getChildNodes();
		if (echs != null) {
			String catName = null;
			String catId = null;
			for (int j = 0; j < echs.getLength(); j++) {
				Node node = echs.item(j);
				if (node instanceof Element) {
					Element elem = (Element) node;
					String nodeName = elem.getNodeName();// category
					if (nodeName.equals("category")) {
						catName = elem.getAttribute("name");
						catId = elem.getAttribute("id");
						// clog(nodeName +" name="+catName+" id="+catId);
					} else if (nodeName.equals("view")) {
						String viewName = elem.getAttribute("name");
						String viewId = elem.getAttribute("id");
						String viewClass = elem.getAttribute("class");
						// clog(nodeName +" name="+viewName+" id="+viewId
						// +" class="+viewClass);

						Extension extension = SourcecleanerFactory.eINSTANCE
								.createExtension();

						extension.setName(viewName);
						extension.setId(viewId);
						extension.setClazz(viewClass);
						extension.setPointId(pointId);

						// Extension extension = new Extension(pointId, viewId,
						// viewClass);
						extension.setExtra("category=" + catName + ";" + catId);
						clog(extoLog(extension));
						result.add(extension);
					}
				}
			}
		}
		return result;
	}

	private String extoLog(Extension extension) {
		return "id=" + extension.getId()
				// +" name="+extension.getName()
				+ " clazz=" + extension.getClazz() + " point id="
				+ extension.getPointId() + " extra=" + extension.getExtra();
	}

	private List<Extension> parseIsoeClassExtension(Document doc, Element ext,
			String pointId) {
		tb = true;
		List<Extension> result = new ArrayList<Extension>();
		clog4("parse Isoe ClassExtension for project " + project.getName());
		if (project.getName().equals("org.isoe.diagraph.diagraphviz.utils"))
			tb = true;
		try {
			XPathExpression isoeExtensionPointExpr = xpath
					.compile("/plugin/extension/@point");
			List<String> extensionPoints = evaluateNodeSet(doc,
					isoeExtensionPointExpr);
			for (String extensionPoint : extensionPoints) {
				if (extensionPoint.startsWith("org.isoe")) {
					clog4("isoe extpoint=" + extensionPoint);
					XPathExpression isoeExtensionsExpr = xpath
							.compile("/plugin/extension[@point='"
									+ extensionPoint + "']");
					List<Node> nodes = getNodeSet(doc, isoeExtensionsExpr);
					for (Node node : nodes) {
						NodeList isoecontribs = node.getChildNodes();
						for (int j = 0; j < isoecontribs.getLength(); j++) {
							Node isoecontribnod = isoecontribs.item(j);
							if (isoecontribnod instanceof Element) {
								XPathExpression contribClassExpr = xpath
										.compile("/plugin/extension[@point='"
												+ extensionPoint + "']/"
												+ isoecontribnod.getNodeName()
												+ "/@class");
								List<String> classes = evaluateNodeSet(doc,
										contribClassExpr);
								if (classes.isEmpty())
									System.err
											.println("no classes for contribution "
													+ extensionPoint
													+ " - "
													+ isoecontribnod
															.getNodeName());
								else
									for (String clazz : classes) {
										clog4("isoe contrib="
												+ isoecontribnod.getNodeName()
												+ " class=" + clazz);
										Extension extension = createExtension(
												clazz, null, pointId);
										extension.setName(isoecontribnod
												.getNodeName());
										result.add(extension);
									}
							}
						}
					}
				}
			}


		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		if (false && result.isEmpty()) // org.isoe.diagraph.diagraphviz.actions.ModelDotifyAction
			configuration.addError("no implementation for extension point "
					+ pointId + " (project=" + project.getName() + ")");
		return result;
	}

	private Extension createExtension(String extensionClass, String id,
			String pointId) {
		Extension extension = SourcecleanerFactory.eINSTANCE.createExtension();
		if (id != null && !id.isEmpty())
			extension.setId(id);
		extension.setClazz(extensionClass);
		extension.setPointId(pointId);

		clog(extoLog(extension));

		return extension;
	}



	private List<String> parseElements_nu(NodeList l) {
		List<String> result = new ArrayList<String>();
		for (int ju = 0; ju < l.getLength(); ju++) {
			NodeList elz = ((Element) l.item(ju))
					.getElementsByTagName("element");
			for (int a = 0; a < elz.getLength(); a++) {
				Element el = (Element) elz.item(a);
				String id = el.getAttribute("ref");
				result.add(id);
			}
		}
		return result;
	}



	private void parseSchemaReferences(Project project, Schema schma,
			Document doc_) throws XPathExpressionException {
		boolean tb;
		if (project.getName().equals("org.isoe.extensionpoint.automation"))
			tb = true;
		List<String> exts = new ArrayList<String>();

		NodeList refnodes = (NodeList) seqrefs.evaluate(doc_,
				XPathConstants.NODESET);
		for (int a = 0; a < refnodes.getLength(); a++) {
			Node el = refnodes.item(a);
			String id = el.getNodeValue();
			exts.add(id);
		}

		refnodes = (NodeList) choicerefs.evaluate(doc_, XPathConstants.NODESET);
		if (false && refnodes.getLength() > 0)
			configuration.addError("should not use choice in exsd for project "
					+ project.getName());
		for (int a = 0; a < refnodes.getLength(); a++) {
			Node el = refnodes.item(a);
			String id = el.getNodeValue();
			exts.add(id);
		}

		if (exts.size() > 1)
			tb = true;
		for (String ext : exts) {
			clog4("extension: " + ext);

			XPathExpression basedOnExpression = xpath
					.compile("/schema/element[@name='"
							+ ext
							+ "']/complexType/attribute[@name='class']/annotation/appinfo/meta.attribute[@kind='java']/@basedOn");

			NodeList classnodes = (NodeList) basedOnExpression.evaluate(doc_,
					XPathConstants.NODESET);
			for (int a = 0; a < classnodes.getLength(); a++) {
				Node el = classnodes.item(a);
				String basedOn = el.getNodeValue();
				basedOn = basedOn.substring(1);
				int sep = basedOn.lastIndexOf(".");
				String packName = basedOn.substring(0, sep);
				String intfName = basedOn.substring(sep + 1);
				clog4(packName + " - " + intfName);
				ExtensionReference eref = SourcecleanerFactory.eINSTANCE
						.createExtensionReference();
				schma.getReferences().add(eref);
				eref.setSchema(schma);
				eref.setJava(intfName);
				eref.setPackage(packName);
				Project p = configuration.getProject(eref);
				if (p != null)
					eref.setProject(p.getName());
				// else
				// System.err.println("no project for "+packName+"."+intfName+" in schema "+schma.getName());

				// eref.setProject(projName);

			}

		}



	}

	private List<Extension> parseContributions(Document doc, Element rootElement) {
		boolean tb; // az15
		String pn = project.getName();
		if (pn.equals("org.isoe.my.other.contrib"))
			tb = true;
		if (pn.equals("org.isoe.diagrapg.diagraphviz.actions"))
			tb = true;

		List<Extension> result = new ArrayList<Extension>();
		NodeList extensions = rootElement.getElementsByTagName(EXTENSION);
		if (extensions != null) {
			for (int i = 0; i < extensions.getLength(); i++) {
				Element ext = (Element) extensions.item(i);
				String pointId = ext.getAttribute(POINT);
				clog3("parsing extension " + pointId + " in project " + pn);

				String p1 = "org.isoe.diagraph.parsers";
				String p2 = "org.isoe.extensionpoint.languagehandler";

				if (pointId.equals(p1) || pointId.equals(p1))
					tb = true;

				if (!configuration.getExtensionPointList().contains(pointId))
					configuration.getExtensionPointList().add(pointId);

				if (pointId.startsWith("org.eclipse.ui.actionSets")) {
					List<Extension> exts = parseUiActionSets(doc, ext, pointId);
					for (Extension extension : exts) {
						result.add(extension);
						addExtension_(project, extension);
					}
				} else if (pointId.startsWith("org.eclipse.ui.popupMenus")) {
					List<Extension> exts = parseUiPopup(doc, ext, pointId);
					for (Extension extension : exts) {
						result.add(extension);
						addExtension_(project, extension);
					}
				} else if (pointId.startsWith("org.eclipse.ui.views")) {
					List<Extension> exts = parseUiViews(doc, ext, pointId);
					for (Extension extension : exts) {
						result.add(extension);
						addExtension_(project, extension);
					}
				} else if (pointId.startsWith("org.isoe")) {
					List<Extension> exts = parseIsoeClassExtension(doc, ext,
							pointId);
					for (Extension extension : exts) {
						result.add(extension);
						addExtension_(project, extension);
					}
				}

			}
		}
		return result;
	}

	private void clog3(String mesg) {
		if (LOG3)
			System.out.println(mesg);

	}

	private void clog2(String mesg) {
		if (LOG2)
			System.out.println(mesg);
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	/*
	 * public ExtensionPoint findExtensionPoint(String pointId) {
	 * for(ExtensionPoint ep:configuration.getExtension_Points()) if
	 * (ep.getId().equals(pointId)) return ep; return null; }
	 */

}
