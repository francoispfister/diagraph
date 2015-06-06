
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
 *    adapted from http://javalib.wordpress.com/2010/07/05/parsing-xmi/
 */

package org.isoe.fwk.utils;


import java.io.File;
import org.w3c.dom.Document;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import java.lang.String;
/**
 * 
 * @author http://javalib.wordpress.com/2010/07/05/parsing-xmi/
 *
 */
public class UmlXmiParser {

    final static String ATTRIBUTES = "UML:Attribute";
    final static String METHODS = "UML:Operation";
    final static String CLASSES = "UML:Classifier.feature"; //masih salah
    final static String COUPLINGS = "UML:AssociationEnd";
    final static String CLASS_INHERITANCES = "UML:GeneralizableElement.generalization";
    final static String FILE_ADDRESS = "test.xmi";

    public static void main(String args[]) {
        try {

            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(new File(FILE_ADDRESS));

            // normalize text representation
            doc.getDocumentElement().normalize();
            System.out.println("==============================");
            System.out.println("Root element of the doc is " + doc.getDocumentElement().getNodeName());

            NodeList listClass = doc.getElementsByTagName(CLASSES);
            int totalClass = listClass.getLength();
            System.out.println("Total Class : " + totalClass);

            NodeList listAttributes = doc.getElementsByTagName(ATTRIBUTES);
            int totalAttributes = listAttributes.getLength();
            System.out.println("Total Attribute : " + totalAttributes);

            NodeList linksAtributesHiding = doc.getElementsByTagName(ATTRIBUTES);
            for (int i = 0; i < linksAtributesHiding.getLength(); i++) {

                Element link = (Element) linksAtributesHiding.item(i);

                //JIKA ATTRIBUTE PUBLIC
                if ("public".equalsIgnoreCase(link.getAttribute("visibility"))) {
                    //JUMLAH TOTAL ATTRIBUTE PUBLIC
                    System.out.println("attribute value = " + link.getAttribute("visibility"));
                }

                //JIKA ATTRIBUTE PRIVATE
                if ("private".equalsIgnoreCase(link.getAttribute("visibility"))) {
                    //JUMLAH ATTRIBUTE PRIVATE
                    System.out.println("attribute value = " + link.getAttribute("visibility"));
                }
            }

        } catch (SAXParseException err) {
            System.out.println("** Parsing error" + ", line "
                    + err.getLineNumber() + ", uri " + err.getSystemId());
            System.out.println(" " + err.getMessage());

        } catch (SAXException e) {
            Exception x = e.getException();
            ((x == null) ? e : x).printStackTrace();

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}