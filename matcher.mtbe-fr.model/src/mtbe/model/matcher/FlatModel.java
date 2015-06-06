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
package mtbe.model.matcher;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * @model
 */
public interface FlatModel {
	
	
	
	/**
	 * @model
	 */
	List<Entity> getEntities();
	
	

	/**
	 * @model kind="operation"
	 */
	List<Instance> getAllInstances();

	/**
	 * @model kind="operation"
	 */
	void setModel(EObject model);

	/**
	 * @model kind="operation"
	 */
	void setMetamodel();
	/**
	 * @model kind="operation"
	 */
	List<Instance> getInstances(Entity entity);
	/**
	 * @model kind="operation"
	 */
	boolean isLabelAttributePertinent(Entity entity, List<Instance> instances);
	
	Entity createEntity(EClass claz);
	/**
	 * @model kind="operation"
	 */
	boolean isEmpty(Object obj);
	/**
	 * @model kind="operation"
	 */
	boolean entityIsEmpty(List<Instance> instances);
	/**
	 * @model kind="operation"
	 */
	void createEntities();
	/**
	 * @model kind="operation"
	 */
	boolean checkLabelPertinence(List<Instance> instances);
	/**
	 * @model kind="operation"
	 */
	void iterate(EObject object, int depth, int maxDepth, Set<EObject> visited,
			Entity entity, List<Instance> instances);
	/**
	 * @model kind="operation"
	 */
	Resource loadMetamodel(String path);
	/**
	 * @model kind="operation"
	 */
	Resource loadModel(Resource metamodelResource, String path);
	/**
	 * @model kind="operation"
	 */
	EObject loadModel();


	/**
	 * @model kind="operation"
	 */
	List<Instance> getContent(Entity entity);
}
