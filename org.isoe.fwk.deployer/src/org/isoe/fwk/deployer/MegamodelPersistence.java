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
package org.isoe.fwk.deployer;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.isoe.diagraph.megamodel.Dsml;
import org.isoe.diagraph.megamodel.Megamodel;
import org.isoe.diagraph.megamodel.MegamodelFactory;
import org.isoe.diagraph.megamodel.MegamodelPackage;
import org.isoe.fwk.core.DParams;

/**
 *
 * @author pfister
 *
 */
public class MegamodelPersistence {

	private static final boolean LOG = DParams.MegamodelPersistence_LOG;

	static ResourceSet resourceset;

	static ResourceSet getResourceSet() {
		if (resourceset == null) {
			resourceset = new ResourceSetImpl();
			resourceset
					.getResourceFactoryRegistry()
					.getExtensionToFactoryMap()
					.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
							new XMIResourceFactoryImpl());
			resourceset.getResourceFactoryRegistry().getExtensionToFactoryMap()
					.put("ecore", new EcoreResourceFactoryImpl());
			resourceset.getPackageRegistry().put(MegamodelPackage.eNS_URI,
					MegamodelPackage.eINSTANCE);
		}
		return resourceset;
	}


	/*
	public static void main(String[] args) {
		test();
	}

	public static void test() {
		String plug = "org.isoe.diagraph.megamodel.megamodel";
		String folder = "src/org/isoe/diagraph/megamodel";
		String modelname = "megamodel";
		URI uri = null;
		if (EMFPlugin.IS_ECLIPSE_RUNNING) {
			uri = URI.createPlatformResourceURI(plug + "/" + folder + "/"
					+ modelname + "." + MegamodelPackage.eNAME, true);
			clog(uri.toString());
		} else
			uri = URI.createFileURI(folder + "/" + modelname + "."
					+ MegamodelPackage.eNAME);
		Megamodel root = MegamodelFactory.eINSTANCE.createMegamodel();
		// save(uri, root);
		Megamodel root_ = load(uri, null);
	}
*/
	public static Megamodel createUniverse() { // FP130817
		if (LOG)
			clog("creating megamodel");
		Megamodel root = MegamodelFactory.eINSTANCE.createMegamodel();
		root.setName("diagraph megamodel");
		return root;
	}

	public static void delete(URI uri) {
		URI ruri = CommonPlugin.resolve(uri);
		File f = new File(ruri.toFileString());
		f.delete();
		if (LOG)
			clog(ruri.toFileString() + " deleted");

	}

	public static Megamodel load(URI uri, Object sender) {
		Resource resource = getResourceSet().getResource(uri, true);
		if (resource != null && !resource.getContents().isEmpty()) {
			try {
				Megamodel result = (Megamodel) resource.getContents().get(0);
				if (LOG) {
					String dsls = "";
					List<Dsml> dsmls = result.getDsmls(); // FP140622aa
					for (Dsml dsml : dsmls)
						dsls += dsml.getName()
								+ (dsml.getNotations_().isEmpty() ? " (0 view) "
										: (" [" + dsml.getNotations_().size() + " views] "))
								+ "; ";
					if (LOG)
						clog("loaded dsmls from bundle :" + dsls);
				}
				return result;
			} catch (Exception e) {
				System.err.println(uri.toString()
						+ " :error while loading megamodel: " + e.toString());
				return null;
			}
		} else {
			try {
				if (resource != null) {
					URI ruri = CommonPlugin.resolve(uri);
					System.err.println(ruri.toFileString() + " is not a model");
					return null;
				}
			} catch (Exception e) {
				System.err.println(uri.toString() + " :nothing found");
				return null;
			}
			System.err.println(uri.toString() + " :nothing found");
			return null;
		}
	}

	private static void saveToLWB(URI uri, Megamodel megamodel, Object sender) { // FP130903
		resourceset = null;
		URI ruri = CommonPlugin.resolve(uri);
		String path = ruri.toFileString();
		URI fileruri = URI.createFileURI(path);
		Resource resource = getResourceSet().createResource(fileruri);
		resource.getContents().add(megamodel);
		EcoreUtil.resolveAll(resource);
		if (LOG) {
			// clog("saving " + fileruri.toString());
			clog("megamodel saveToLWB " + uri.toString());
		}
		try {
			resource.save(null);
		} catch (IOException e) {
			System.err.println("unable to save megamodel " + e.getMessage());
		}
		resourceset = null;
	}

	private static void saveLocal(URI uri, Megamodel megamodel, Object sender) { // FP130903
		URI ruri = CommonPlugin.resolve(uri);
		// File f = new File(ruri.toFileString());
		/*
		 * if (f.exists() && DParams.NOT_OVERWRITE_IN_MWB &&
		 * !f.getName().endsWith("megamodel.megamodel")){ if (LOG)
		 * clog("saveLocal; model allready exists, not overwritten: "
		 * +uri.toString()); return; }
		 */

		if (LOG)
			clog("megamodel saveLocal " + uri.toString());

		Resource resource = getResourceSet().createResource(uri);// platform:/resource/univ/repository/megamodel.megamodel
		resource.getContents().add(megamodel);
		EcoreUtil.resolveAll(resource);
		clog("saving " + uri.toString());
		try {
			resource.save(null);
		} catch (IOException e) {
			System.err.println("unable to save megamodel " + e.getMessage());
		}
	}


	// not
	// share
	// a
	// same
	// resourceset
	// between
	// 2
	// different
	// platform
	// protocols
	// (i.e.
	// plugin
	// &&
	// resource)
	public static void save(URI uri, Megamodel megamodel, boolean resetResource, Object sender) {
		if (resetResource)
			resourceset = null;
		try {
			if (uri.toString().contains("platform:/plugin/"))
				saveToLWB(uri, megamodel,sender);
			else
				saveLocal(uri, megamodel,sender);
		} catch (Exception e) {
			System.err.println("(2) error while saving megamodel");
		}
		if (resetResource)
			resourceset = null;
	}

	private static void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);

	}

}
