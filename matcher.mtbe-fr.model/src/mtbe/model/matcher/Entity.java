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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;


/**
 * @model
 */
public interface Entity {
	/**
	 * @model
	 */
	EAttribute getBestLabelAttribute(); //ok
	/**
	 * @model
	 */
	EClass getEClass(); //ok
	/**
	 * @model
	 */
	int getBestLabelScore();
	
	void addAll(List<Instance> instances);
	
	/**
	 * @model
	 */	
	List<Instance> getInstances(); //ok
	
	
	void setEClass(EClass eClass);

	void setBadLabelNames(List<String> badLabelNames);
	
	void guessLabels();

	int guessDoubleLabel();

    int guessIntLabel();

	int guessStringLabel();	
	
	

}
