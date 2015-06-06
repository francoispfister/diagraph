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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.isoe.diagraph.runner.IDiagraphRunner;


public class MyTest {



	private void refactor_not_used(IDiagraphRunner runner,EPackage mmodel, String[] resourceData) { // FP120107 //FP120627
		mmodel.eAllContents();

		//EPackage complete = mmodel;

		// file:/D:/pfister/workspaces/ws-isoe-gmf-1/org.isoe.diagraph.test.link/model/link.ecore
		String pre = resourceData[0].substring(0,
				resourceData[0].lastIndexOf("."));
		URI compl = URI.createFileURI(resourceData[0]);
		String pruri = pre + "_pruned" + ".ecore";
		URI pruned = URI.createFileURI(pruri);
		ModelFilter mf = new ModelFilter(runner,mmodel, pruned);
		mf.proceedModelFile(compl, URI.createFileURI(pre + "_result" + ".ecore"));

	}





	private void refactor(ResourceSet resourceSet,EPackage mmodel, String[] resourceData) { // FP120107
		mmodel.eAllContents();

		EPackage complete = mmodel;

		// file:/D:/pfister/workspaces/ws-isoe-gmf-1/org.isoe.diagraph.test.link/model/link.ecore
		String pre = resourceData[0].substring(0,
				resourceData[0].lastIndexOf("."));

		save(resourceSet,mmodel, URI.createFileURI(pre + "_orig" + ".ecore"));

	}


	public void save(ResourceSet resourceSet, EPackage p, URI uri) {
		Resource res = resourceSet.createResource(uri);
		res.getContents().add(p);
		try {
			res.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void getpackages(Resource resrc) {
		for (EObject packg : resrc.getContents()) { // FP100217 plusieurs packg
			EPackage p = (EPackage) packg;

			p.getESubpackages();
		}
	}

	public void test() throws IOException {
		ResourceSet rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap()
				.put("xmi", new XMIResourceFactoryImpl());

		Resource r1 = rs.createResource(URI.createFileURI("C:/tmp/res1.xmi"));
		Resource r2 = rs.createResource(URI.createFileURI("C:/tmp/res2.xmi"));

		EClass c1 = EcoreFactory.eINSTANCE.createEClass();
		c1.setName("Class1");
		EClass c2 = EcoreFactory.eINSTANCE.createEClass();
		c2.setName("Class2");

		r1.getContents().add(c1);
		r2.getContents().add(c2);

		c1.getESuperTypes().add(c2);

		for (TreeIterator<Object> iterator = EcoreUtil.getAllProperContents(r1,
				true); iterator.hasNext();) {
			EObject next = (EObject) iterator.next();
			if (EcorePackage.Literals.ECLASS != next.eClass())
				;
		}




		EClass dynaClass = EcoreFactory.eINSTANCE.createEClass();
		dynaClass.setName("dynaClass");
		EAttribute dynaAttribute = EcoreFactory.eINSTANCE.createEAttribute();
		dynaAttribute.setEType(EcorePackage.eINSTANCE.getEJavaObject());
		dynaClass.getEStructuralFeatures().add(dynaAttribute);
		dynaAttribute.setTransient(true);
		//dynaClass.getESuperTypes().add(PlanningPackage.Literals.EPLAN);
		//xyzPackage.eINSTANCE.getEClassifiers().add(dynaClass);
		//EObject eDynaObject = xyzFactory.eINSTANCE.create(dynaClass);


	}

	public static List<EObject> getInstances(EPackage packag, EClass eclass) {
		List<EObject> result = new ArrayList<EObject>();
		iterate(packag, 0, -1, null, eclass, result);
		return result;
	}

	public static void iterate(EObject object, int depth, int maxDepth_,
			Set<EObject> visited, EClass eclass_, List<EObject> instances) {

		if (maxDepth_ < 0 && visited == null)
			visited = new HashSet<EObject>();
		if (visited != null)
			visited.add(object);
		EClass eClass = object.eClass();
		if (eClass == eclass_) {
			// ILabel lbl = MatchUtils.getLabel(object, entity);
			// Instance currentNode = new InstanceImpl(object, lbl);
			instances.add(object);
		}
		if (maxDepth_ < 0 || depth < maxDepth_) {
			for (EReference reference : eClass.getEAllReferences()) {
				Object refer = object.eGet(reference);
				if (reference.isMany()) {
					@SuppressWarnings("unchecked")
					EList<EObject> refs = (EList<EObject>) refer;
					for (EObject ref : refs) {
						if (visited == null || !visited.contains(ref)) {
							iterate(ref, depth + 1, maxDepth_, visited,
									eclass_, instances);
						}
					}
				} else if (refer != null
						&& (visited == null || !visited.contains(refer))) {
					iterate((EObject) refer, depth + 1, maxDepth_, visited,
							eclass_, instances);
				}
			}
		}
	}
}
