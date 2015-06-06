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
package org.isoe.diagraph.internal.api;


import java.util.List;

import org.eclipse.emf.ecore.EClass;





/**
 *
 * @author pfister
 *
 */
public interface IDiaPointOfView {
	void setSyntaxMapping(Object map);
	Object getSyntaxMapping();
	Object getEditorGenerator();
	void setDNode(IDiaNode node);
	String getElementName();
	String getGenModelId();
	String getGenEditorViewId();
	void setGenModelId(String id);
	void setGenEditorViewId(String id);
	void setEditorGenerator(Object editorGenerator);
	String getNodeName();
	IDiaNode getNode();
	String getSuffix();
	String getName();
	EClass getEClass();
	List getContainments();
}
