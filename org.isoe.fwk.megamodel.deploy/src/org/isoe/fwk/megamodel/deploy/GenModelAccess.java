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
package org.isoe.fwk.megamodel.deploy;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.gmf.internal.bridge.genmodel.BasicGenModelAccess;
import org.eclipse.gmf.internal.bridge.genmodel.DummyGenModel;
//import org.eclipse.gmf.internal.bridge.genmodel.BasicGenModelAccess;
//import org.eclipse.gmf.internal.bridge.genmodel.DummyGenModel;
import org.eclipse.jdt.core.JavaConventions;
import org.eclipse.jdt.core.JavaCore;
//import org.isoe.fwk.log.LogConfig;



/**
 * @author pfister
 */
@SuppressWarnings("restriction")
public class GenModelAccess extends BasicGenModelAccess {

	private static final boolean LOG = false;
	private EPackage model_;
	private EPackage additionalModel;

	
	public GenModelAccess(EPackage aModel,EPackage additionalModel) {
		super(aModel);//,additionalModel);
      this.model_=aModel;
      this.additionalModel = additionalModel;
	}

	
	public GenModel myCreateDummy(String pluginid) {
		GenModel result =null;
		if (LOG)
		   System.out.println("creating dummy genModel for "+pluginid+" package:" + model_.getName()+" nsPrefix: "+model_.getNsPrefix()+" nsURI: "+model_.getNsURI());
		 if (!JavaConventions.validatePackageName(pluginid, JavaCore.VERSION_1_4, JavaCore.VERSION_1_4).isOK()){ //FP120612
			 String msg = "error while creating genModel (1) for "+pluginid+" illegal plugin or package name !!!!";
			 System.out.println(msg);
			 throw new RuntimeException(msg);
		 }
		try {
		   DummyGenModel gma = null;
		   if (additionalModel!=null){
		     Collection<EPackage> additionalPackages = new ArrayList<EPackage>();
		     additionalPackages.add(additionalModel);
			  gma = new DummyGenModel(model_, additionalPackages);
		   } else{
		      gma = new DummyGenModel(model_, null);
		   }
			gma.setPluginID(pluginid);
			result = gma.create();
		
			
			
		} catch (Exception e) {
			 String msg="error while creating genModel (2) for "+pluginid;
			 throw new RuntimeException(msg);
		}
	    if (result==null)
	       throw new RuntimeException("error while creating genModel (3) for "+pluginid);
        return result;
	}

}
