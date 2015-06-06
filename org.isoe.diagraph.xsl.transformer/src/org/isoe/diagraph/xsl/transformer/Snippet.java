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
package org.isoe.diagraph.xsl.transformer;


import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.xsd.ecore.XSDEcoreBuilder;

/**
 * 
 * @author http://stackoverflow.com/questions/671555/how-to-convert-xsd-to-ecore-emf
 * @see http://www.eclipse.org/modeling/emf/docs/overviews/XMLSchemaToEcoreMapping.pdf
 * @see https://github.com/eclipse/xpand/tree/master/examples/org.eclipse.xpand.examples.xsd.m2m.ecore2xsd
 * @see https://www.cct.lsu.edu/~rguidry/eclipse-doc36/src-html/org/eclipse/emf/servus/app/Ecore2XSD.html
 *
 */
public class Snippet {
	


    public static void main(String[] args) {
    	Snippet x2e = new Snippet();
        x2e.go("UMLVersions/V1.0.0/UML2XMI.xsd", "UMLVersions/V1.0.0/UML2100.xmi");
    }


    public void go(String sourcename, String targetname) {

        System.out.println("Starting");

        XSDEcoreBuilder xsdEcoreBuilder = new XSDEcoreBuilder();
        ResourceSet resourceSet = new ResourceSetImpl();
        Collection<EObject> eCorePackages = xsdEcoreBuilder.generate(URI.createFileURI(sourcename));
 
        
        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
        Resource resource = resourceSet.createResource(URI.createFileURI(targetname));

        for (EObject eObject : eCorePackages) {
        	 EPackage element = (EPackage) eObject;
             resource.getContents().add(element);
        }
               
        /*
        for (Iterator iter = eCorePackages.iterator(); iter.hasNext();) {
            EPackage element = (EPackage) iter.next();
            resource.getContents().add(element);
        }*/

        try {
            resource.save(null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Finished");

    }

}
