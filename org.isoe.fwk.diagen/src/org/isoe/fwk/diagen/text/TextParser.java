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
package org.isoe.fwk.diagen.text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.isoe.extensionpoint.diagen.IDiagraphAlphabet;
import org.isoe.extensionpoint.diagen.IHandleMegamodelJob;
import org.isoe.extensionpoint.diagen.ITextParser;
import org.isoe.fwk.core.DParams;

/**
 *
 * @author fpfister
 * handmade ... mock parser, todo: use xtext
 */
public class TextParser implements IDiagraphAlphabet, ITextParser {

	static final String SPACES = "                                                                           ";
	private static final boolean LOG = DParams.TextParser_LOG;
	private static final boolean THROW_EXCEPTION = false;
//	private List<String> cmds = new ArrayList<String>();
	private IHandleMegamodelJob megamodelHandler;
	private RoleManager roleManager;
	private String language;

/*
	public static void main(String[] args) {

		String workspace = "C:\\workspaces\\mintel417\\";
		String folder = "model";
		String prefix = "lang.m2";
		String suffix = DIAGEN_CST_EXT_;
		String lang = "simpleworld";
		String modelfolder = workspace + File.separator + prefix + "." + lang
				+ File.separator + folder;
		TextParser parser = new TextParser(null, false);
		String path = modelfolder + File.separator + lang + "." + suffix;
		parser.parseNotation__(path);
		// List<String> lines =
		// parser.readFile(MODEL_FOLDER+File.separator+lang+".cst");
	}
	*/




	public TextParser(IHandleMegamodelJob megamodelHandler, boolean dummy) {
		this.megamodelHandler = megamodelHandler;
		roleManager = new RoleManagerImpl(this);
	}

	@Override
	public String getLanguage() {
		return language;
	}

	@Override
	public String genConcreteSyntax(File domainFile, IProgressMonitor monitor) {
		String result = null;
		language = null;
		try {
			result = parseNotation(domainFile);
		} catch (Exception e) {
			result = e.toString();
		}
		return result;
	}

	private String parseNotation(File domainFile) { // FP140517
		if (domainFile != null)
			return parseNotation(domainFile.getAbsolutePath());
		return "";
	}


	private int getLang(List<String> cst) { //FP140601
		int l = -1;
		for (String line : cst)
			if (line.trim().startsWith("dsml(")){
				int op = line.indexOf(SEP_OPEN_PARENTH);
				int cl = line.indexOf(SEP_CLOSE_PARENTH);
				language = line.substring(op + 1, cl);
				break;
			}
			else
				l++;
		return l;
	}


	private int getStart(List<String> cst, String kw, int i) {
		int l = -1;
		for (String line : cst)
			// skip lines until start kw
			if (l>=i && line.trim().startsWith(kw))
				break;
			else
				l++;
		return l;
	}

	private List<String> readFile(String path) {
		List<String> result = new ArrayList<String>();
		File fil = new File(path);
		// StringBuffer buffer = new StringBuffer();
		try {
			InputStream in = new FileInputStream(fil);
			try {
				InputStreamReader inR = new InputStreamReader(in);
				BufferedReader buf = new BufferedReader(inR);
				String line;
				while ((line = buf.readLine()) != null) {
					if (!line.trim().isEmpty())
						result.add(line);
				}
				if (LOG)
					clog("readFile "+path+" ok");
			} finally {
				in.close();
			}
		} catch (IOException e) {
			System.err.println("no file " + path);
			return result;
		}
		return result;
	}

	private void writeFile(String path, String content) {
		if (path != null && !path.isEmpty() && content != null
				&& !content.isEmpty()) {
			File fil = new File(path);
			FileWriter fw;
			try {
				fw = new FileWriter(fil, false);
				fw.write(content);
				fw.close();
				if (LOG)
					clog("writeFile "+path+" ok");
			} catch (IOException e) {
				System.err.println("error writing file  " + e.toString());
			}
		} else
			System.err.println("nothing to save (" + path + ")");
	}


	private int parseView(List<String> cst, int l) {
		String line = cst.get(l).trim();
		if (LOG)
			clog("parseView (" + line+")");
		if (line.equals(SEP_CLOSE_BRACKET)){
			if (LOG)
				clog("view end");
			return l;
		}
		int op = line.indexOf(SEP_OPEN_PARENTH);
		int cl = line.indexOf(SEP_CLOSE_PARENTH);
		String view = line.substring(op + 1, cl);
		return parseNotationViewContent(view, cst, l + 1);
	}


	private int parseNotationViewContent(String view, List<String> cst, int l) {
		// System.out.println("-----view=" + view);
		if (LOG)
			clog("parseNotationViewContent");
		int result = -1;
		for (int i = l; i < cst.size(); i++) {
			String line = cst.get(i).trim();
			if (line.equals(SEP_CLOSE_BRACKET))
				return i;
			result = parseNotationEntry(view, cst, i);
		}
		return result;
	}

	private int parseNotationEntry(String view, List<String> cst, int l) {

		String line = cst.get(l).trim();
		if (LOG)
			clog("parseNotationEntry ("+line+")");
		int op = line.indexOf(SEP_OPEN_BRACKET);
		int cl = line.indexOf(SEP_CLOSE_BRACKET);
		String name = line.substring(0, op);
		String edges = line.substring(op + 1, cl);
		if (edges.contains(DIAGRAPH_VOCAB_LABELS_EQUAL)){
			String edgt=edges.trim();
			int ps=edgt.indexOf(DIAGRAPH_VOCAB_LABELS_EQUAL);

		}
		return parseNotationEdges(view, name, cst, l, edges);
	}

	private int parseNotationEdges(String view, String name, List<String> cst,
			int l, String content) {

		if (LOG)
			clog("parseNotationEdges ("+name+")");
		String[] edges = content.trim().split(",");
		for (String edg : edges){
			parseNotationElement(view, name, edg, cst, l);
		}
		return l;
	}

	private int parseNotationElement(String view, String element,
			String argument, List<String> cst, int l) {
		if (LOG)
			clog("parseNotationElements ("+element+(argument.isEmpty()?"":("."+argument))+")");
		roleManager.parseNotationNodeOrGeneric(view, element, argument);
		if (argument != null
				&& !argument.isEmpty()
				&& (!DIAGRAPH_VOCAB_GENERIC.equals(argument) && !DIAGRAPH_VOCAB_POV_
						.equals(argument)))
			return roleManager.parseNotationEdge(view, element, argument, cst, l);
		else
			return l;
	}


    @Override
	public void error(String mesg){
		if (THROW_EXCEPTION)
			throw new RuntimeException(mesg);
		else
			System.err.println("error <<<< "+mesg+" >>>>");
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	@Override
	public List<String> getRoles() {
		return roleManager.getRoles();
	}



	private String parseNotation(String modelPath) { //FP150513
		if (LOG)
			clog("parse notation "+modelPath);
		List<String> cst = readFile(modelPath);
		if (LOG)
		  for (String line : cst)
			clog(line);
		int currentLine = getLang(cst);
		currentLine = getStart(cst, VIEW_OPEN,currentLine + 1);
		/*
		 * for (String line : cst) //skip lines until start kw if
		 * (line.trim().startsWith(VIEW_OPEN)) break; else currentLine++;
		 */
		for (String line : cst)
			if (line.trim().startsWith(VIEW_OPEN)) // parse all views
				currentLine = parseView(cst, currentLine + 1);
		System.out.println("generate commands ------------");
		roleManager.sortCommands();
		String toSave = "";
		for (String cmd : roleManager.getRoles()) {
			if (LOG)
				clog(cmd);
			toSave += cmd + "\n";
		}
		String scriptPath = modelPath.substring(0,modelPath.indexOf("."+DIAGEN_CST_EXT));
		scriptPath += "."+DIAGEN_CMD_EXT;
		writeFile(scriptPath,toSave);
		if (LOG)
			clog("------------ end parse notation");
		return "ok";
	}





/*

	private Resource save(EObject toSave, URI uri) throws IOException {
		new File(uri.toFileString()).delete();
		Resource resource = new XMIResourceFactoryImpl().createResource(uri);
		resource.getContents().add(toSave);
		Map<String, String> options = new HashMap<String, String>();
		options.put(XMLResource.OPTION_ENCODING, "UTF-8");
		resource.save(options);
		return resource;
	}

	private EObject load(String filename) {
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet
				.getResourceFactoryRegistry()
				.getExtensionToFactoryMap()
				.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
						new XMIResourceFactoryImpl());


		File file = new File(filename);
		Resource resource = null;
		URI uri = URI.createFileURI(file.getAbsolutePath());
		try {
			resource = resourceSet.getResource(uri, true);
			System.out.println("Loaded " + uri);
		} catch (RuntimeException exception) {
			System.out.println("Problem loading " + uri);
			exception.printStackTrace();
		}
		return resource == null ? null : resource.getContents().get(0);
	}
*/



}
