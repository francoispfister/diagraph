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
package org.isoe.fwk.integration;

import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 *
 * @author fpfister
 *
 */
public class XmlGenerator {


	protected static final String INDENT_AMOUNT = "{http://xml.apache.org/xslt}indent-amount";

	protected FeatureBase featureBase;

	public XmlGenerator(FeatureBase featureBase) {
		this.featureBase = featureBase;
	}

	public String generate() throws ParserConfigurationException,
			TransformerException {
		Document doc = createDocument();
		createContent(doc);
		String result = toXml(doc);
		return result;
	}

	protected Document createDocument() throws ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		DOMImplementation impl = builder.getDOMImplementation();
		Document doc = impl.createDocument(null, null, null);
		doc.setXmlStandalone(true);
		return doc;
	}

	protected String toXml(Document doc) throws TransformerException {
		Transformer t = TransformerFactory.newInstance().newTransformer();
		t.setOutputProperty(OutputKeys.METHOD, "xml");
		t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		t.setOutputProperty(INDENT_AMOUNT, "4");
		t.setOutputProperty(OutputKeys.INDENT, "yes");
		StreamResult result = new StreamResult(new StringWriter());
		t.transform(new DOMSource(doc), result);
		return result.getWriter().toString();
	}


	protected void createContent(Document doc) {
		Element site = doc.createElement("foo");
		doc.appendChild(site);

		Element feat1 = doc.createElement("bar");
		feat1.setAttribute("id", "org.my.id.0.1");
		feat1.setAttribute("version", featureBase.getVersion());
		feat1.setTextContent("foobar baz");
	}





}
