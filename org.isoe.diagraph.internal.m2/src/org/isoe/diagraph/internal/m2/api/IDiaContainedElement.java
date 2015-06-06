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
package org.isoe.diagraph.internal.m2.api;


import java.util.List;

import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EReference;
import org.isoe.diagraph.internal.api.IDiaLabel;
import org.isoe.diagraph.internal.api.IDiaNamedElement;
import org.isoe.diagraph.internal.m2.DiaLabel;


/**
 *
 * @author pfister
 *
 */
public interface IDiaContainedElement extends IDiaNamedElement{

     String getName();
	 List<DiaLabel> getOwnLabels();
	 List<EModelElement> getInheritedContainmentsBase();
	 List<EModelElement> getInheritedContainmentsAlt();
	 EModelElement getEModelElement();
	 void addInferredLabel(IDiaLabel lb);
	 String getElementName();
	 EReference getContainmentReferenceBase();
	 EReference getContainmentReferenceAlt();
	 boolean isAbstract();
	// void propagateLower(IDiaContainedElement el);

	 List<IDiaContainedElement> getSuperElements();
     List<IDiaContainedElement> getLowerElements();
	 List<IDiaContainedElement> getSuperNodeElementsOld();
	 List<IDiaContainedElement> getSuperNodeElementsNew();
	 List<IDiaContainedElement> getAllSuperElementsOld(IDiaContainedElement element);
	 List<IDiaContainedElement> getAllSuperElementsNew(IDiaContainedElement element);
}
