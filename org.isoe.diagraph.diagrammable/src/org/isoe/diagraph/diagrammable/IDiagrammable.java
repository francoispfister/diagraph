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
package org.isoe.diagraph.diagrammable;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.extensionpoint.ecoreutils.IEcoreUtils;

/**
 *
 * @author fpfister
 *
 */
import org.isoe.diagraph.controler.IDiagraphControler;  //FP140707refactored
public interface IDiagrammable {
   IEcoreUtils getEcoreService();
   List<DGraph> getConcreteSyntax();
   boolean isParsing();
   boolean isCloning();
   //List<DGraph> parseClassWithAttributesAndAnnotations(EClass source);
   void parseInheritance(EClass source, EClass target);
   void parseEReference(EReference eReference);
   List<DGraph> parseClassWithAttributesAndAnnotations(EClass source, Object sender);
   IDiagraphControler getControler();
}
