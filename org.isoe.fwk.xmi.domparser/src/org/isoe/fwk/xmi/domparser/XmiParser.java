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
package org.isoe.fwk.xmi.domparser;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * 
 * @author http://javalib.wordpress.com/2010/07/05/parsing-xmi/ - the idea
 * @author pfister adapted to Diagraph
 * 
 */
public class XmiParser {

	private static final boolean LOG = false;

	private static XmiParser instance;
	private String fileName;
	private String nsPrefix;
	private String nsUri;
	private String name;
	private String xmiVersion;
	private String root;

	public String getRoot() {
		return root;
	}

	public static XmiParser getParser() {
		if (instance == null)
			instance = new XmiParser();
		return instance;
	}

	private XmiParser() {
		super();
	}

	public String getNsPrefix() {
		return nsPrefix;
	}

	public String getNsUri() {
		return nsUri;
	}

	public String getName() {
		return name;
	}

	private void getNameSpace(NamedNodeMap namedNodeMap) {// "xmlns:"
		nsUri = null;
		nsPrefix = null;
		for (int i = 0; i < namedNodeMap.getLength(); i++) {
			Attr attr = (Attr) namedNodeMap.item(i);
			clog(attr.getName());
			if (attr.getName().equals("xmi:version"))
				xmiVersion = attr.getValue();
			else if (xmiVersion != null && attr.getName().startsWith("xmlns:")) { 
				// the first  occurrence after xmi Version fixed
				nsUri = attr.getValue();
				nsPrefix = attr.getName();
				nsPrefix = nsPrefix.substring(nsPrefix.indexOf(":") + 1);
				break;
			}
		}
	}

	public static void main(String[] a) {
		XmiParser x = new XmiParser();
		String f = "E:\\workspaces\\ws-x\\instances\\a100.v125case5";
		x.parse(f);
		System.out.println("xmiparse==============================");
		System.out.println("name= " + x.name);
		System.out.println("nsPrefix= " + x.nsPrefix);
		System.out.println("nsUri= " + x.nsUri);
		System.out.println("root= " + x.root);
	}

	/*
	 * content of
	 * E:\Apps\workspaces\runtime-ix130518b\my.team.instances_models\instances
	 * \a100.v125case5 <?xml version="1.0" encoding="UTF-8"?>
	 * <ns125case5foobar:N xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI"
	 * xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	 * xmlns:ns125case5foobar="http://my.org/my.team/case5/v125"> <ts
	 * xsi:type="ns125case5foobar:D" name="ghk"/> </ns125case5foobar:N>
	 */

	public void parse(String filname) {
		this.fileName = filname;
		try {
			File fil = new File(fileName);
			name = fil.getName();
			name = name.substring(name.indexOf(".") + 1);
			if (name.equals("xmi"))
				name = null;
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(fil);
			NamedNodeMap namedNodeMap = doc.getDocumentElement()
					.getAttributes();
			// xmlns =
			getNameSpace(namedNodeMap);
			doc.getDocumentElement().normalize();
			root = doc.getDocumentElement().getNodeName();
			if (LOG) {
				clog("====xmiparse===");
				clog("name= " + name);
				clog("nsPrefix= " + nsPrefix);
				clog("nsUri= " + nsUri);
				clog("root= " + root);
			}
		} catch (SAXParseException err) {
			System.err.println("** Parsing error" + ", line "
					+ err.getLineNumber() + ", uri " + err.getSystemId());
			throw new RuntimeException(err.getMessage());
		} catch (SAXException e) {
			Exception x = e.getException();
			((x == null) ? e : x).printStackTrace();
			throw new RuntimeException(e.getMessage());
		} catch (Throwable t) {
			t.printStackTrace();
			throw new RuntimeException(t.getMessage());
		}
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

}
