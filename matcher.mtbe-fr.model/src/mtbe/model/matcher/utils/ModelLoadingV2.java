/*********************************************************************************
 * Copyright (c) 2007, 2008 Xavier Dolques <xavier-dolques@orange.fr>
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Xavier Dolques <xavier-dolques@orange.fr> - initial API and implementation
 *********************************************************************************/

package mtbe.model.matcher.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;










/**
 * @author xdolques
 *
 */
public final class ModelLoadingV2 {
	
	
	private static final boolean OPTION_SCHEMA_LOCATION = false;

	public static EObject read(String path,String nsuri, EPackage packg)  {
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
				.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
						new XMIResourceFactoryImpl());
		resourceSet.getPackageRegistry().put(nsuri, packg);
		File file = new File(path);
		URI uri = file.isFile() ? URI.createFileURI(file.getAbsolutePath())
				: URI.createURI(path);
		Resource resource = resourceSet.getResource(uri, true);
		System.out.println("Loaded " + uri);
		return resource.getContents().get(0);
	}

	public static Resource loadMetaModelFromModel(Resource model){
		EObject modelElement=model.getContents().get(0);
		return modelElement.eClass().eResource();
	}

	//fpfister
	public static Resource loadMetamodel(String metamodelPath) {
		ResourceSet metamodelSet = new ResourceSetImpl();
		metamodelSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
				.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
						new EcoreResourceFactoryImpl());
		URI metamodelURI = URI.createFileURI(new File(metamodelPath)
				.getAbsolutePath());
		return metamodelSet.getResource(metamodelURI, true);
	}
	
	//fpfister
	private static ResourceSet loadMetamodelRS(Resource metamodel) {
		ResourceSet modelSet = new ResourceSetImpl();
		modelSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
				Resource.Factory.Registry.DEFAULT_EXTENSION,
				new EcoreResourceFactoryImpl());
		Iterator<EObject> i = metamodel.getAllContents();
		EPackage p = (EPackage) i.next();
		modelSet.getPackageRegistry().put(p.getNsURI(), p);
		return modelSet;
	}

	//fpfister
	public static Resource loadResource(String modelPath, Resource metamodel) {
		ResourceSet modelSet = loadMetamodelRS(metamodel);
		URI modelURI = URI.createFileURI(new File(modelPath).getAbsolutePath());
		return modelSet.getResource(modelURI, true);
	}

	//fpfister
	public static void saveXmiTraceFile(String path, EObject eobj, String nsuri, EPackage packg){
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
		resourceSet.getPackageRegistry().put(nsuri, packg);
		URI uri = URI.createFileURI(new File(path).getAbsolutePath());
		Resource resource = resourceSet.createResource(uri);
		resource.getContents().add(eobj);
		Map<Object, Object> options = null;
		if (OPTION_SCHEMA_LOCATION) {
			options = new HashMap<Object, Object>();
			options.put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
		}
		try {
			resource.save(options);
			System.out.println("file saved:"+uri.toFileString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	

	
	public static void saveXmiTraceFile(URI uri, EObject eobj,String nsuri, EPackage packg){
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
		resourceSet.getPackageRegistry().put(nsuri, packg);
		if (!EMFPlugin.IS_ECLIPSE_RUNNING) 
		   uri = URI.createFileURI(new File(uri.toFileString()).getAbsolutePath());
		Resource resource = resourceSet.createResource(uri);
		resource.getContents().add(eobj);
		Map<Object, Object> options = null;
		if (OPTION_SCHEMA_LOCATION) {
			options = new HashMap<Object, Object>();
			options.put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
		}
		try {
			System.out.println("saving:"+CommonPlugin.resolve(uri));
			resource.save(options);
			System.out.println("file saved:"+CommonPlugin.resolve(uri));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	//fpfister
	public static void saveTraceFile(String path, EObject eobj,String nsuri, EPackage packg){
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION,new XMIResourceFactoryImpl());
		resourceSet.getPackageRegistry().put(nsuri, packg);
		URI uri = URI.createFileURI(new File(path).getAbsolutePath());
		Resource resource = resourceSet.createResource(uri);
		resource.getContents().add(eobj);
		try {
			resource.save(null);
			System.out.println("file saved:"+uri.toFileString());
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}		

	public static EObject loadXmiTraceFile(URI uri,String nsuri, EPackage packg) {
		ResourceSet resourceSet= new ResourceSetImpl();
		//URI fileURI= URI.createFileURI(new File(tracePath).getAbsolutePath());
		
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION,new XMIResourceFactoryImpl());
		resourceSet.getPackageRegistry().put(nsuri, packg);
		Resource trResource=resourceSet.getResource(uri, true);
      //  Resource mmr= trResource.getContents().get(0).eClass().eResource();
		System.out.println(uri.toString()+" loaded ");
		return trResource.getContents().get(0);
	}
	
	//fpfister  
	public static EObject loadXmiTraceFile(String tracePath,String nsuri, EPackage packg) {
		ResourceSet resourceSet= new ResourceSetImpl();
		URI fileURI= URI.createFileURI(new File(tracePath).getAbsolutePath());
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION,new XMIResourceFactoryImpl());
		resourceSet.getPackageRegistry().put(nsuri, packg);
		Resource trResource=resourceSet.getResource(fileURI, true);
        Resource mmr= trResource.getContents().get(0).eClass().eResource();
		System.out.println(fileURI.toFileString()+" loaded   -   metamodel uri="+mmr.getURI().toFileString());
		return trResource.getContents().get(0);
	}
	
	//fpfister
	public static EObject  loadResource(String tracePath, String metamodelPath,String nsuri, EPackage packg) {
		ResourceSet resourceSet= new ResourceSetImpl();
		URI fileURI= URI.createFileURI(new File(tracePath).getAbsolutePath());
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION,new XMLResourceFactoryImpl());
		resourceSet.getPackageRegistry().put(nsuri, packg);
		Resource trResource=resourceSet.getResource(fileURI, true);
		return trResource.getContents().get(0);

	}	
	

	public static Resource loadResource(URI modelUri, String metamodelPath) {
		ResourceSet modelSet;
		if ( modelUri.toString().endsWith(".ecore") )
			modelSet = getEcoreResourceSet();
		else
			modelSet = getGenericResourceSet(metamodelPath);
		//URI modelURI = URI.createFileURI(new File(modelPath).getAbsolutePath());
		return modelSet.getResource(modelUri, true);
	}
	
	public static Resource loadResource(URI modelUri, URI metamodelUri) {
		ResourceSet modelSet;
		if ( modelUri.toString().endsWith(".ecore") )
			modelSet = getEcoreResourceSet();
		else
			modelSet = getGenericResourceSet(metamodelUri);
		return modelSet.getResource(modelUri, true);
	}

	
	
	private static ResourceSet getGenericResourceSet(URI metamodelUri) {
		Resource metamodel = loadMetamodel(metamodelUri);
		ResourceSet modelSet = new ResourceSetImpl();
		modelSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION,new EcoreResourceFactoryImpl());
		Iterator<EObject> i = metamodel.getAllContents();
		EPackage p = (EPackage) i.next();
		modelSet.getPackageRegistry().put(p.getNsURI(), p);
		return modelSet;
	}
	
	public static Resource loadMetamodel(URI metamodelUri) {
		ResourceSet metamodelSet = new ResourceSetImpl();
		metamodelSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
				.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
						new EcoreResourceFactoryImpl());
		//URI metamodelURI = URI.createFileURI(new File(metamodelPath).getAbsolutePath());
		return metamodelSet.getResource(metamodelUri, true);
	}
	
	//jr falleri
	public static Resource loadResource(String modelPath,String metamodelPath) {
		ResourceSet modelSet;
		// Ugly workaround to ensure to use the more specialized resource set.
		if ( modelPath.endsWith(".ecore") )
			modelSet = getEcoreResourceSet();
		else
			modelSet = getGenericResourceSet(metamodelPath);
		URI modelURI = URI.createFileURI(new File(modelPath).getAbsolutePath());
		return modelSet.getResource(modelURI, true);
	}
	
	//jr falleri
	private static ResourceSet getGenericResourceSet(String metamodelPath) {
		Resource metamodel = loadMetamodel(metamodelPath);
		ResourceSet modelSet = new ResourceSetImpl();
		modelSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION,new EcoreResourceFactoryImpl());
		Iterator<EObject> i = metamodel.getAllContents();
		EPackage p = (EPackage) i.next();
		modelSet.getPackageRegistry().put(p.getNsURI(), p);
		return modelSet;
	}
	
	//jr falleri
	private static ResourceSet getEcoreResourceSet() {
		ResourceSet modelSet = new ResourceSetImpl();
		modelSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION,new EcoreResourceFactoryImpl());
		return modelSet;
	}







	
	

}
