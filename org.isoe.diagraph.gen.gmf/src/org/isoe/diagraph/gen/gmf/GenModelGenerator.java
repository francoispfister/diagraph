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

import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.ecore.EPackage;
import org.isoe.diagraph.gen.gmf.util.DGenHelpers;
import org.isoe.fwk.utils.ResourceManager;



/**
 * @author fpfister
 */

public class GenModelGenerator {


   private EPackage packag;
   private EPackage additionalPackage;

	private String[] resourceData;

	public GenModelGenerator(EPackage ePackage,EPackage additionalPackage,  String[] resourceData) {
		this.resourceData = resourceData;
		this.packag = ePackage;
		this.additionalPackage = additionalPackage;

	}


	public GenModel getGenModel() {
	   GenModel gm = DGenHelpers.createGenModel(packag,additionalPackage,
					resourceData[ResourceManager.RSRC_PLUGIN]);
			try {
				ResourceManager.save(resourceData,
						resourceData[ResourceManager.RSRC_PATH_] + "."
								+ "genmodel", gm);
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		return gm;
	}



	  private static GenModel createGenModel_snippet(final EPackage aModel,final EPackage addModel,String pluginid_,IPath ecorePath ,String basePackage) {
	      assert pluginid_ != null;
	      GenModel genModel = new org.isoe.fwk.megamodel.deploy.GenModelAccess(aModel,addModel).myCreateDummy(pluginid_);//FP130823
	      genModel.setModelPluginID(pluginid_);
	      genModel.setModelDirectory("/" + pluginid_ + "/src/");
	      genModel.setEditDirectory("/" + pluginid_ + ".edit" + "/src/");
	       IPath genModelPath = ecorePath.removeFileExtension().addFileExtension("genmodel");
	        org.eclipse.emf.common.util.URI genModelURI = org.eclipse.emf.common.util.URI.createFileURI(genModelPath.toString());
	        genModel.setModelDirectory("/"+genModelURI.trimFileExtension().segment(0)+"/src");
	        GenPackage genPackage = genModel.getGenPackages().get(0);
	        String prefix = genModelURI.trimFileExtension().lastSegment().substring(0, 1).toUpperCase()+genModelURI.trimFileExtension().lastSegment().substring(1);
	        genModel.setModelName(prefix);
	        genPackage.setPrefix(prefix);
	        genPackage.setBasePackage(basePackage);
	        genModel.setModelPluginID(genModelURI.trimFileExtension().segment(0));
	        genModel.setEditPluginID(genModelURI.trimFileExtension().segment(0)+".edit");
	        genModel.setEditorPluginID(genModelURI.trimFileExtension().segment(0)+".editor");
	        genModel.setTestsPluginID(genModelURI.trimFileExtension().segment(0)+".test");
	        genModel.setComplianceLevel(org.eclipse.emf.codegen.ecore.genmodel.GenJDKLevel.get(org.eclipse.emf.codegen.ecore.genmodel.GenJDKLevel.values().length-1)); //Set Latest Version
	        genModel.setRuntimeVersion(org.eclipse.emf.codegen.ecore.genmodel.GenRuntimeVersion.get(org.eclipse.emf.codegen.ecore.genmodel.GenRuntimeVersion.values().length-1)); //Set Latest Version
	      genModel.setGenerateSchema(false);
	      genModel.setCanGenerate(true);
	      genModel.setCodeFormatting(true);
	      genModel.setNonNLSMarkers(true);
	      return genModel;
	   }


}
