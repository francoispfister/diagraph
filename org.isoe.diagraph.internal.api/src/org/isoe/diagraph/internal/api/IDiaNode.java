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
import org.eclipse.emf.ecore.EModelElement;
import org.isoe.diagraph.common.DiaColor;
import org.isoe.diagraph.common.DiaLayout;



/**
 *
 * @author pfister
 *
 */
public interface IDiaNode {
	String getName();
	void setCanvas_(boolean b);
	void setPointOfView(IDiaPointOfView diaPointOfView);
	void setBackGroundColor(DiaColor backgroundcolor) ;
	DiaColor getBackGroundColor();
	void setLayout(DiaLayout layout);
	DiaLayout getLayout();
	boolean hasDerivedSubNode();
	List<IDiaNode> getDerivedSubNodes();
	IDiaNode getDerivedSubNode(String name);
	List getNodeMappings_();
	List getChildReferences_();
	boolean isCanvas(EClass eclass, String view);
	boolean isDirectCanvas(String view);
	boolean isCanvas(String view);
	String getNavigation();
	EClass getEClass();
	boolean isAbstract();
	//List<IDiaNode> getAllConcreteSubNodes();
	void setMapped(boolean b);
	void addNodeMapping(Object nodeMapping, String name);
	int getCase();
	boolean isCanvas();
	String getElementName();
	//EClass getEModelElement();
	EClass getSemanticRole();
	void logContainment(int sender);
	String getContention_();
	List getContainments();
	EModelElement getEModelElement();
	List<IDiaNode> getSubNodes();


}
