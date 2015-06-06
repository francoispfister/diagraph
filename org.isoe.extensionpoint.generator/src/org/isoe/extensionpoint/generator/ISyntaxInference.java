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
package org.isoe.extensionpoint.generator;

//import org.isoe.extensionpoint.diagraph.DiagraphService;
import org.isoe.extensionpoint.diagraph.action.DiagraphService;



/**
 *
 * @author fpfister
 *
 * future diagraph syntax inference extension-point,
 * this is a basis  for  refactoring
 * allready existing methods
 *
 */

public interface ISyntaxInference extends DiagraphService {

	void resolveContainmentNode();

	void resolveContainmentReferences();

	void resolveInheritedFeatures();

	void inferLinkTargetReferences();

	void inferLinkSourceReferences();

	void sortContainments();

	void inferContainments();

	void resolveTargetRefs();

	void resolveContainmentsWithName();

	void resolveMissingCRefsIfCanvas();

	void inferMissingCref();

	void propagateInheritedLabelsIfNoLabel();

	void inferMissingLabels();

	void cloneInheritedContainments();

	void propagateAllInheritedLabels();

	void resolveLinkSourceAndTarget();

	void resolveOwnLabelAttributes();

	boolean isStub();

	boolean isQualified();

	String exposeContract();

}
