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
package mtbe.model.matcher.impl;

import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import mtbe.model.matcher.Entity;
import mtbe.model.matcher.MatcherClient;
import mtbe.model.matcher.Instance;
import mtbe.model.matcher.FlatModel;


public class MatcherClientImpl implements  MatcherClient  {

	private FlatModel model;
	
	@Override
	public Resource loadMetamodel(String metamodelPath) {
		return  mtbe.model.matcher.utils.ModelLoadingV2.loadMetamodel(metamodelPath);
	}
	
	@Override
	public Resource loadModel(Resource metamodelResource, String modelpath){
		return mtbe.model.matcher.utils.ModelLoadingV2.loadResource(modelpath,metamodelResource);
	}
	
	private EObject loadTestModel(){
		Resource mmr = loadMetamodel("alignment/food.ecore");
		Resource mr = loadModel(mmr,"alignment/food.xmi");
		//Resource mmr = loadMetamodel("alignment/wikipedia.ecore");
		//Resource mr = loadModel(mmr,"alignment/wikipedia.xmi");
		return mr.getContents().get(0);
	}
	
	


	public FlatModel getModel() {
		if (model==null){
		  model = new FlatModelImpl();
		  model.setModel(loadTestModel());
		}
		return model;
	}

	public static void main(String[] args) {
		FlatModel model = new MatcherClientImpl().getModel();
		List<Entity> entities = model.getEntities();
		for (Entity entity : entities) {
			System.out.println(entity);
		}
		
		for (Iterator<Instance> i = model.getAllInstances().iterator(); i.hasNext();) {
			Instance m = i.next();
			System.out.println(m);
		}
	}





}
