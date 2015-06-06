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
package org.isoe.fwk.megamodel.handler;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.isoe.diagraph.megamodel.Megamodel;
import org.isoe.diagraph.megamodel.MegamodelFactory;
import org.isoe.diagraph.megamodel.Dsml;
import org.isoe.diagraph.megamodel.Model;
import org.isoe.diagraph.megamodelhelper.IMegamodelHandler;
import org.isoe.fwk.core.DParams;


/**
 *
 * @author fpfister
 *
 */
public class MegamodelHandler  implements IMegamodelHandler{

   private static final boolean LOG = DParams.Megamodel_LOG;
   private static MegamodelHandler instance;

	private MegamodelHandler() {
	}

	public static MegamodelHandler getInstance() {
		if (instance==null)
			instance = new MegamodelHandler();
		return instance;
	}


    @Override
	public Dsml addDsml(Megamodel megaModel,String universeNameSrc,EPackage model,String targetPlugin,boolean fromBundle){
		Dsml dsml = findDsml(megaModel, model.getName());
		if (dsml == null){
		  dsml = MegamodelFactory.eINSTANCE.createDsml();
		  dsml.setName(model.getName());
		  dsml.setURI_(URI.createPlatformResourceURI(targetPlugin,false).toString());
		  dsml.setBuildIn(fromBundle);
		  megaModel.getDsmls().add(dsml);
		  if (DParams.MegaModelBuilderJOB_LOG)
		     jobclog("(3) added dsml "+dsml.getName()+" to megamodel "+megaModel.hashCode());
		  //FP140622aaa
		  if (LOG) clog("*************   dsml added "+ dsml.getName());
		} else
		   if (LOG) clog("dsml exists "+ dsml.getName());
		return dsml;
	}

	private void jobclog(String mesg) {
     if (DParams.MegaModelBuilderJOB_LOG)
        System.out.println(mesg);
   }

	private void clog(String mesg) {
     if (LOG)
        System.out.println(mesg);
    }

   @Override
   public Dsml findDsml(Megamodel megamodel, String name) {
	   if (LOG) clog("seek for "+ name);
		List<org.isoe.diagraph.megamodel.Dsml> Dsmls = megamodel.getDsmls();
		for (Dsml dsml : Dsmls)
			if (dsml.getName().equals(name))
				return dsml;
		return null;
	}

    @Override
	public Model findModel(Dsml Dsml, String name) {
		List<Model> models= Dsml.getModels();
		for (Model model : models)
			if (model.getName().equals(name))
				return model;
		return null;
	}


}
