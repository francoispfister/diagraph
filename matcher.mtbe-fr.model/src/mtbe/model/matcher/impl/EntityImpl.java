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
import java.util.List;

import mtbe.model.matcher.Entity;
import mtbe.model.matcher.Instance;
import mtbe.model.matcher.helpers.MatchUtils;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;




/**
 *
 * @author fpfister
 *   associe une classe avec toutes ses instances, ainsi que
 *         l'attribut de nommage représentatif
 *
 */
public class EntityImpl implements Entity{



	private EClass eClass;
	private int maxLove;
	private EAttribute bestLabelAttribute_;
	private List<String> badLabelNames;// = new ArrayList<String>();

	/**
	 * liste des instances encapsulées (instance + label + contenu splitté du
	 * label)
	 */
	private List<Instance> content = new ArrayList<Instance>();


	/**
	 * toutes les instances de la classe
	 */
	@Override
	public void addAll(List<Instance> content) {
		this.content.addAll(content);
	}

	public List<Instance> getInstances() {
		return content;
	}

	public EAttribute getBestLabelAttribute() {
		return bestLabelAttribute_;
	}

	public EClass getEClass() {
		return eClass;
	}

	public int getBestLabelScore() {
		return maxLove;
	}

	public int guessDoubleLabel() {
		return 0; // @TODO
	}

	public int guessIntLabel() {
		return 0; // @TODO;
	}



	public int guessStringLabel() {
		for (EAttribute attribute : eClass.getEAllAttributes()) {
			int love = MatchUtils.getInstance(org.isoe.fwk.core.DParams.NAME_ATTRIBUTES_REVERSED_).bestStringLabelAttribute(attribute,
					badLabelNames.toArray(new String[badLabelNames.size()]));
			if (love > maxLove) {
				maxLove = love;
				bestLabelAttribute_ = attribute;
			}
			if (bestLabelAttribute_ == null){
				bestLabelAttribute_ = attribute;
			}
		}
		System.out.println("\nbestLabelAttribute:"+bestLabelAttribute_.getName()+" maxLove:"+maxLove+ " for EClass "+eClass.getName());
		return maxLove;
	}





	@Override
	public void setEClass(EClass eClass) {
		this.eClass = eClass;
	}

	@Override
	public void setBadLabelNames(List<String> badLabelNames) {
		this.badLabelNames = badLabelNames;
	}
/**
	 * on cherche l'attribut le plus signifiant pour la classe
	 */

	@Override
	public void guessLabels() {
		guessStringLabel();
		guessDoubleLabel();
		guessIntLabel();
	}

}
