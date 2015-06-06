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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import mtbe.model.matcher.Entity;
import mtbe.model.matcher.FlatModel;
import mtbe.model.matcher.Instance;
import mtbe.model.matcher.ILabel;
import mtbe.model.matcher.StringLabel;
import mtbe.model.matcher.helpers.MatchUtils;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.isoe.fwk.core.DParams;



/**
 *
 * @author fpfister flattens the model: a list of Instance wrapping EObject,
 *         sorted by EClass all references are followed, but visited only one
 *         time
 *
 */
public class FlatModelImpl implements FlatModel {



	static final String PROJECTFOLDER = "../mtbe-fr/";
	static final String DEFAULT_DATAFOLDER = PROJECTFOLDER + "bibtex2docbook/";
	static final String DEFAULT_ALIGN_RESULT = DEFAULT_DATAFOLDER
			+ "bibtex2docbook.xmi";
	static final String DEFAULT_LEFT_MM = DEFAULT_DATAFOLDER + "BibTeX.ecore";
	static final String DEFAULT_LEFT_M = DEFAULT_DATAFOLDER + "bibtex.xmi";
	static final String DEFAULT_RIGHT_MM = DEFAULT_DATAFOLDER + "DocBook.ecore";
	static final String DEFAULT_RIGHT_M = DEFAULT_DATAFOLDER + "docbook.xmi";

	List<EPackage> packages = new ArrayList<EPackage>(); // FP100217 n packages
															// vs only one root
															// package

	private EObject model;

	private List<Entity> entities;

	private List<String> labelsToExclude_ = new ArrayList<String>();

	public static boolean CHECK_REFERENCED_RESSOURCES = false;

	@Override
	public List<Entity> getEntities() {
		if (entities == null)
			createEntities();
		for (Entity entit: entities) {
			System.out.println(entit.getBestLabelAttribute().getName());
			List<Instance> instances = entit.getInstances();
			for (Instance instance : instances) {
				instance.getObject();
				ILabel lab = instance.getLabel();
				if (lab instanceof StringLabel){
					StringLabel stringLabel = (mtbe.model.matcher.StringLabel) lab;
					String svalue = stringLabel.getStringValue();
					String [] words=stringLabel.getWords();
					for (String word : words) {
						System.out.println("    -> "+word);
					}

				}
			}

		}


		return entities;
	}

	// private Instance currentNode;

	@Override
	public void setModel(EObject model) {
		this.model = model;
		setMetamodel();
	}

	public FlatModelImpl() {

	}

	@Override
	public void setMetamodel() {
		Resource resrc = mtbe.model.matcher.utils.ModelLoadingV2.loadMetaModelFromModel(model
				.eResource());
		for (EObject packg : resrc.getContents()) { // FP100217 plusieurs packg
			packages.add((EPackage) packg);
		}
	}

	@Override
	public List<Instance> getInstances(Entity entity) {
		List<Instance> result = new ArrayList<Instance>();
		iterate(model, 0, -1, null, entity, result);
		return result;
	}

	@Override
	public boolean isLabelAttributePertinent(Entity entity,
			List<Instance> instances) {
		if (entity.getBestLabelAttribute() != null
				&& !checkLabelPertinence(instances)) {
			labelsToExclude_.add(entity.getBestLabelAttribute().getName());
			return false;
		}
		return true;
	}

	@Override
	public Entity createEntity(EClass claz) {
		System.out.print("creating entity for EClass " + claz.getName() + " ");

		EPackage packg = (EPackage) claz.eContainer();

		System.out.print("  package: " + packg.getName());
		List<EObject> pcontents = packg.eContents();
		/*
		for (EObject eObject : pcontents) {
			if (eObject instanceof EClass)
				System.out.print("  class: " + ((EClass) eObject).getName());
			else
				System.out.print("  " + (eObject));

		}*/

		labelsToExclude_.clear();
		Entity entity = null;
		List<Instance> instances = null;
		int max = 50; // ‡ revoir, lire le modËle pour voir si les champs sont peuplÈs
		do {
			entity = new EntityImpl(); //EClass + bestattr +List<instances>[eobject dÈcomposÈs en mots]
			entity.setBadLabelNames(labelsToExclude_);
			entity.setEClass(claz);
			entity.guessLabels();
			instances = getInstances(entity);
			System.out.println(entity.getBestLabelAttribute().getName());
			max--;
		} while (max > 0 && !isLabelAttributePertinent(entity, instances)); //TODO statistically
		// check label pertinence
		if (!CHECK_REFERENCED_RESSOURCES || !entityIsEmpty(instances)) {
			entity.getInstances().addAll(instances);
			entities.add(entity);
			if (entity.getBestLabelAttribute() != null)
				System.out.println("with label: "
						+ entity.getBestLabelAttribute().getName()
						+ " and score " + entity.getBestLabelScore());
			else
				System.out.println("without label");
			return entity;
		} else {
			System.out
					.println("####  reference missing for "
							+ claz.getName()
							+ ", no entity created, referenced resource is missing ?? ####");
			return null;
		}
	}

	@Override
	public boolean isEmpty(Object obj) {
		boolean result = false;
		if (obj == null)
			return true;
		if (obj instanceof Integer)
			return ((Integer) obj).intValue() == 0;
		if (obj instanceof Double)
			return ((Double) obj).doubleValue() == 0.0;
		if (obj instanceof String)
			return ((String) obj).isEmpty();
		return result;
	}

	@Override
	public boolean entityIsEmpty(List<Instance> instances) {
		int emptyinstances = 0;
		for (Instance instance : instances) {
			EObject inst = instance.getObject();
			int emptyattr = 0;
			List<EAttribute> attrs = inst.eClass().getEAllAttributes();
			for (EAttribute attr : attrs) {
				Object attvalue = inst.eGet(attr);
				if (isEmpty(attvalue))
					emptyattr++;
			}
			if (emptyattr == inst.eClass().getEAllAttributes().size())
				emptyinstances++;
		}
		return emptyinstances == instances.size();
	}

	@Override
	public void createEntities() {
		// LabelledClass: association d'une classe et de son attribut
		// repr√©sentatif, ainsi que de toutes ses instances
		// ne pas cr√©er d'entit√©s pour les classes type conteneur n'ayant aucun
		// attribut
		entities = new ArrayList<Entity>();
		for (EPackage packg : packages) { // FP100217 plusieurs packages et non
											// un seul
			for (EClassifier classifier : packg.getEClassifiers()) {
				System.out.println(classifier.getName());
				if (classifier instanceof EClass
						&& ((EClass) classifier).getEAllAttributes().size() > 0) {
					createEntity((EClass) classifier);
				}
			}
		}
	}

	@Override
	public boolean checkLabelPertinence(List<Instance> instances) {
		//TODO faire des statistiques exaustives
		if (instances.size()<10)
			return true;
		int empty = 0;
		for (Instance instance : instances) {
			String val = instance.getLabel().getStringValue();
			if (val == null || val.isEmpty())
				empty++;
		}
		return (empty < instances.size() / 2);
	}

	/*
	 * public Instance getCurrentNode() { return currentNode; }
	 */

	@Override
	public void iterate(EObject object, int depth, int maxDepth,
			Set<EObject> visited, Entity entity, List<Instance> instances) {

		if (maxDepth < 0 && visited == null)
			visited = new HashSet<EObject>();
		if (visited != null)
			visited.add(object);
		EClass eClass = object.eClass();
		if (eClass == entity.getEClass()) {
			ILabel lbl = MatchUtils.getInstance(org.isoe.fwk.core.DParams.NAME_ATTRIBUTES_REVERSED_).getLabel(object, entity);
			Instance currentNode = new InstanceImpl(object, lbl);
			instances.add(currentNode);
		}
		if (maxDepth < 0 || depth < maxDepth) {
			for (EReference reference : eClass.getEAllReferences()) {
				Object refer = object.eGet(reference);
				if (reference.isMany()) {
					@SuppressWarnings("unchecked")
					EList<EObject> refs = (EList<EObject>) refer;
					for (EObject ref : refs) {
						if (visited == null || !visited.contains(ref)) {
							iterate(ref, depth + 1, maxDepth, visited, entity,
									instances);
						}
					}
				} else if (refer != null
						&& (visited == null || !visited.contains(refer))) {
					iterate((EObject) refer, depth + 1, maxDepth, visited,
							entity, instances);
				}
			}
		}
	}

	@Override
	public Resource loadMetamodel(String path) {
		return mtbe.model.matcher.utils.ModelLoadingV2.loadMetamodel(path);
	}

	@Override
	public Resource loadModel(Resource metamodelResource, String path) {
		Resource result = null;
		String modelpath = path;
		result = mtbe.model.matcher.utils.ModelLoadingV2
				.loadResource(modelpath, metamodelResource);
		return result;
	}

	public void test() {
		Resource mmr = loadMetamodel("alignment/my.example.food.ecore");
		Resource mr = loadModel(mmr, "alignment/food.xmi");
		EObject model = mr.getContents().get(0);
		model.eContents();
		ResourceSet mrst = model.eResource().getResourceSet();
		EList<Resource> mrss = mrst.getResources();
		for (Resource resource : mrss) {
			System.out.println(resource);
		}
		EList<EObject> cnt = model.eContents();
		for (EObject eObject : cnt) {
			System.out.println(eObject);
		}
		EList<Resource> rssss = model.eResource().getResourceSet()
				.getResources();
		for (Resource resource : rssss) {
			System.out.println(resource);
		}
		FlatModel flatModel = new FlatModelImpl();
		flatModel.setModel(model);

		for (Iterator<Instance> i = flatModel.getAllInstances().iterator(); i
				.hasNext();) {
			Instance m = i.next();
			System.out.println(m);
		}
	}

	@Override
	public EObject loadModel() {
		Resource mmr = loadMetamodel(DEFAULT_RIGHT_MM);
		Resource mr = loadModel(mmr, DEFAULT_RIGHT_M);
		return mr.getContents().get(0);
	}

	public void test1() {
		FlatModel mmp = new FlatModelImpl();
		mmp.setModel(loadModel());
		List<Entity> entities = mmp.getEntities();
		for (Entity entity : entities) {
			List<Instance> instances = entity.getInstances();
			for (Instance matz : instances) {
				System.out.println(matz);
			}
		}
	}

	@Override
	public List<Instance> getAllInstances() {
		List<Instance> result = new ArrayList<Instance>();
		List<Entity> entities = getEntities();
		for (Entity entity : entities)
			result.addAll(entity.getInstances());
		return result;
	}

	@Override
	public List<Instance> getContent(Entity entity) {
		return entity.getInstances();
	}

	public static void main(String[] args) {
		FlatModelImpl vvm = new FlatModelImpl();
		vvm.test1();
	}

}
