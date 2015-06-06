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
package org.isoe.diagraph.internal.m2;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.ENamedElement;
import org.isoe.diagraph.internal.api.IDiaLabel;
import org.isoe.diagraph.internal.m2.api.IDiaParser;

/**
 * 
 * @author pfister
 * Is an upper element of aDiaNode or of a DiaLink
 */

public  class DiaGenericElement extends DiaContainedElement {

	private static boolean LOG = false;
	static String spaces = "                                                                ";
	transient private List<DiaReference> references = new ArrayList<DiaReference>();
	
	public void addReference(DiaReference referencedElement) {
		this.references.add(referencedElement);
	}

	public List<DiaReference> getReferences() {
		return references;
	}


	public DiaReference findReference(String name) {
		for (DiaReference reference : references)
			if (reference.getName().equals(name))
				return reference;
		return null;
	}

	public DiaGenericElement(IDiaParser owner, EModelElement element, String view) {
		super(owner, element,view,null); //FP130121//FP121007
	}

	public static DiaGenericElement copy(DiaContainedElement other) {
		DiaGenericElement result = new DiaGenericElement(other.parser,
				other.getEModelElement(), other.view);
		result.clone(other);
		return result;
	}

	private static void log(DiaGenericElement upperClass, DiaContainedElement lowerClass, int depth) {
		if (LOG) {
			String uc="";
			if (upperClass!=null)
				uc= upperClass.getClass().getSimpleName()
				+ ":"
				+ upperClass.getName();
			else
				uc="(top)";
			System.out.println(((depth > 0) ? spaces.substring(0, depth * 3)
					: "")
					+ uc
					+ " <-- "
					+ lowerClass.getClass().getSimpleName()
					+ ":"
					+ lowerClass.getName());
		}
	}

	public String toString() {
		String result = null;
		result += "[" + this.getClass().getSimpleName() + "] "
				+ super.toString();
		if (references.size() > 0) {
			result += " refs: ";
			for (DiaReference refs : references) {
				result += refs.getName() + " ";
			}
		}
		return result;
	}


	public List<DiaContainedElement> getLowerGenericElements() {
		return null; //TODO
	}


	@Override
	public void addInferredLabel(IDiaLabel lb) {
   //TODO	
	}



	

}
