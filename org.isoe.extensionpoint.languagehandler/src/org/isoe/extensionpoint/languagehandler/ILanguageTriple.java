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
package org.isoe.extensionpoint.languagehandler;

import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.isoe.diagraph.diagraph.DLabeledElement;

/**
 *
 * @author fpfister
 *
 */

public interface ILanguageTriple {

	enum TripleType {
		Classifier, Reference, Inheritance//,Object, Root, Containment,Link
	};
	//TripleType getTripleType_();
	void klone();
	EObject getSource();
	EObject getTarget();
	String getSourceAnchor();
	String getTargetAnchor();
	String ecoreEClassName(EObject eobj);
	boolean isEcoreObject(EObject eobj);
	boolean isVisible() ;
	String getName();
	String objectAsString_(EObject eObject, String[] filter);
	String objectAsString_(EObject eObject);
	String getLinkLabel(EObject eobj);
	//String getNodeLabel(DGraph concreteSyntax, EObject eobj);
	String getNodeLabel(EObject eobj);
	EAttribute getFirstLabelAttribute(DLabeledElement el);
	List<EAttribute> getLabelAttributes(DLabeledElement el);
}
