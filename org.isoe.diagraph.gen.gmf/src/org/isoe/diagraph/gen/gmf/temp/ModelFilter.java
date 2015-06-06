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
package org.isoe.diagraph.gen.gmf.temp;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.isoe.diagraph.runner.IDiagraphRunner;



/**
 *
 * @author fpfister
 *
 */
public class ModelFilter {

	private ResourceSet resourceSet;
	protected MetamodelMatcher matcher;

	private HashMap<EObject, EObject> f2pTrace_; // From Complete to Footprint
	private HashMap<EObject, EObject> p2fTrace_; // From Footprint to Complete

	private IDiagraphRunner runner;


	public ModelFilter(IDiagraphRunner runner)
	{
		resourceSet = new ResourceSetImpl();
		this.runner=runner;
	}

	public ModelFilter(IDiagraphRunner runner,EPackage complete, URI pruned)
	{
		this(runner);
		//registerMetamodel(complete);
		EPackage pMM = loadMetamodel(pruned);
		setMetamodels(complete, pMM);
	}

	protected void setMetamodels(EPackage c, EPackage p)
	{
		matcher = new MetamodelMatcher(runner,c,p);
		matcher.diagnose();
	}

	protected EPackage loadMetamodel(URI uri)
	{
		Resource res = resourceSet.createResource(uri);

		try {
			res.load(null);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return (EPackage) EcoreUtil.getObjectByType(res.getContents(), EcorePackage.eINSTANCE.getEPackage());
	}

	public void saveModel(Collection<EObject> model, URI uri)
	{
		Resource res = resourceSet.createResource(uri);

		for (EObject eObject : model)
		{
			if(eObject.eContainer() == null)
				res.getContents().add(eObject);
		}
		try {
			res.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void registerMetamodel(EPackage mm)
	{
		resourceSet.getPackageRegistry().put(mm.getNsURI(), mm);
	}

	public Collection<EObject> loadModel(URI uri)
	{
		Resource res = resourceSet.createResource(uri);

		try {
			res.load(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Collection<EObject> model = new ArrayList<EObject>();

		for(TreeIterator<EObject> it = res.getAllContents();it.hasNext();)
		{
			model.add(it.next());
		}
		return model;
	}

	public Collection<EObject> loadModel(EObject root)
	{
		Collection<EObject> model = new ArrayList<EObject>();

		for(TreeIterator<EObject> it = root.eAllContents();it.hasNext();)
		{
			model.add(it.next());
		}
		return model;
	}

	public void proceedModelFile(URI completeModelURI, URI prunedModelURI)
	{
		Collection<EObject> model = loadModel(completeModelURI);
		model = sliceModel(model);
		saveModel(model, prunedModelURI);
	}


	public Collection<EObject> sliceModel(Collection<EObject> model)
	{
		resetTrace();
		return setFeatures(createObjects(model));
	}


	private Collection<EObject> createObjects(Collection<EObject> model)
	{
		Collection<EObject> result = new ArrayList<EObject>();

		for (EObject cObj : model)
		{
			if(keepObject(cObj))
			{
				EClass pClass = (EClass) matcher.getFootprintCorrespondance(cObj.eClass());
				EPackage pPkg = pClass.getEPackage();
				EObject pObj = pPkg.getEFactoryInstance().create(pClass);
				result.add(pObj);
				f2pTrace_.put(cObj, pObj);
				p2fTrace_.put(pObj, cObj);
			}
		}

		return result;
	}
	@SuppressWarnings("unchecked")
	private Collection<EObject> setFeatures(Collection<EObject> model)
	{
		for(EObject fObj : model)
		{
			EObject cObj = p2fTrace_.get(fObj);

			for(EStructuralFeature pFeat : fObj.eClass().getEAllStructuralFeatures())
			{
				if(!pFeat.isDerived() && pFeat.isChangeable() && !fObj.eIsSet(pFeat))
				{
					if (pFeat instanceof EAttribute)
					{
						EAttribute pAtt = (EAttribute) pFeat;
						EAttribute cAtt = (EAttribute) matcher.getCompleteCorrespondance(pAtt);

						Object cVal = cObj.eGet(cAtt);

						fObj.eSet(pAtt,cVal);
					}
					else if (pFeat instanceof EReference)
					{

						EReference pRef = (EReference) pFeat;
						EReference cRef = (EReference) matcher.getCompleteCorrespondance(pFeat);
						if(pRef.isMany())
						{
							EList<EObject> cList = (EList<EObject>) cObj.eGet(cRef);
							EList<EObject> pList = (EList<EObject>) fObj.eGet(pRef);

							for(EObject cVal : cList)
							{
								EObject pVal = f2pTrace_.get(cVal);
								if(pVal != null)
								{
									pList.add(pVal);
								}
								else if(cVal.eIsProxy())
								{
									runner.cerror("WARNING: Proxy met.");
								}
							}
						}

						else
						{
							EObject cVal = (EObject) cObj.eGet(cRef);
							EObject pVal = f2pTrace_.get(cVal);

							if(cVal != null && pVal == null)
							{
								// EMF returns an object that is not part of the model
								// Typically, something about generic types...
								runner.cerror("WARNING: Weird EMF behavior.");
							}

							fObj.eSet(pRef, pVal);

						}
					}
				}

			}
		}

		return model;
	}

	private void resetTrace()
	{
		f2pTrace_ = new HashMap<EObject, EObject>(); // From Complete to Prune
		p2fTrace_ = new HashMap<EObject, EObject>(); // From Prune to Complete
	}

	/*
	private boolean keepObject(EObject obj)
	{
		return objectIsUsed(obj);
	}

	private boolean objectIsUsed(EObject obj)
	{
		return matcher.isClassUsed(obj.eClass());
	}*/


	private boolean keepObject(EObject obj)
	{
		if (obj instanceof EClass)
		{
			EClass claz = (EClass)obj;
			/*
			if (
					claz.getName().equals("X") ||
					claz.getName().equals("A") ||
					claz.getName().equals("K") ||
					claz.getName().equals("N99")
			)
				return true;*/
		}
		return false;
		//return objectIsNode(obj);
	}
	private boolean objectIsNode(EObject obj)
	{
		return matcher.isClassNode(obj.eClass());
	}
}
