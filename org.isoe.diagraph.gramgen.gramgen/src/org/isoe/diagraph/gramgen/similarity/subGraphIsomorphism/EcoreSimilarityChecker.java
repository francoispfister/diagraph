 /**
 * Copyright (c) 2014 Laboratoire de Genie Informatique et Ingenierie de Production - Ecole des Mines d'Ales
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Blazo Nastov (ISOE-LGI2P) - initial API and implementation
 */
package org.isoe.diagraph.gramgen.similarity.subGraphIsomorphism;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.isoe.diagraph.common.DiagraphException;


/**
 *
 * @author bnastov
 *
 */
public class EcoreSimilarityChecker {

	private static final boolean LOG = false;
	private static Graph cachedGraph = null;
	private static StandardEcoreImporter cachedImporter_ = null;


	public static List<EClass> getVisualElements(){
		return cachedImporter_.getVisualElements();
	}

	/**
	 *
	 * @param model
	 * @param fallbackPov //if a pov cannot be calculated, take the fallback candidate
	 * @return
	 * @throws DiagraphException
	 */
	public static EClass computeVisualNodes(EPackage model, EClass fallbackPov) throws DiagraphException{
		StandardEcoreImporter targetI = null;
		Graph target = null;
		EClass result = null;
		//The graph and the importer are cached, because the operation of calculating the graph is complex
		if(cachedGraph != null && cachedImporter_ != null){
			target = cachedGraph;
			targetI = cachedImporter_;
		}else{
			//targetI = new StandardEcoreImporter(model,fallbackPov);
			targetI = new StandardEcoreImporter(model);
			result = targetI.findPov(fallbackPov);
			targetI.findVisualNodes(); //FP130611x
			cachedImporter_ = targetI;
			target = targetI.getGraph();
			cachedGraph = target;
		}
	    if (result == null)
	    	result = fallbackPov;
		return result;
	}


	public static EClass computePOV(EPackage model, EClass fallbackPov) throws DiagraphException{
		EClass result = new StandardEcoreImporter(model).findPov(fallbackPov);
	    if (result == null)
	    	result = fallbackPov;
		return result;
	}

static boolean doing=false;

/**
 *
 * @param patternModel
 * @param model
 * @param modelRoot: a previously fixed pov
 * @return
 * @throws DiagraphException
 */
	public static ArrayList<EClass> checkSimilarity(EPackage patternModel, EPackage model,EClass modelRoot) throws DiagraphException{

		StandardEcoreImporter targetI;
		Graph target;
		
		if (doing)
			System.err.println("allready checkSimilarity");
		
		doing=true;
		//The graph and the importer are cached, because the operation of calculating the graph is complex
		if(cachedGraph != null && cachedImporter_ != null){
			target = cachedGraph;
			targetI = cachedImporter_;

		}else{
			//targetI = new StandardEcoreImporter(model,modelRoot);
			targetI = new StandardEcoreImporter(model);
			targetI.findPov(modelRoot);
			targetI.findVisualNodes();

			cachedImporter_ = targetI;
			target = targetI.getGraph();
			cachedGraph = target;
		}

		//StandardEcoreImporter patternI = new StandardEcoreImporter(patternModel,modelRoot);	//TODO separate the import function from the pov finder
		StandardEcoreImporter patternI = new StandardEcoreImporter(patternModel);
		patternI.findPov(null);
		patternI.findVisualNodes();

		Graph pattern = patternI.getGraph();

		ArrayList<Vertex> mapping = Match.startMatching(target, pattern);
		ArrayList<EClass> result = new ArrayList<EClass>();

		for (Vertex vertex : mapping) {
			clog(targetI.getGraphClassMapping().get(vertex).getName());
			result.add(targetI.getGraphClassMapping().get(vertex));
		}
        doing=false;
		
		return result;
	}

	private static void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	public static void cleanCache(){
		cachedGraph = null;
		cachedImporter_= null;
	}

	public static void main(String [] arg){


//		EcoreSimilarityCheckerV3.checkSimilarity(uri1, uri2);

	}
}
