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
package org.isoe.diagraph.parser.api;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.isoe.extensionpoint.ecoreutils.IEcoreUtils;
import org.isoe.extensionpoint.rules.ISyntaxRules;
//FP150512



/**
 *
 * @author fpfister
 *
 */
public interface IDiagraphNotation {

	public static final int CONTAINMENT_ID = 0;
	public static final int LINK_SOURCE_ID = 1;
	public static final int LINK_TARGET_ID = 2;
	//public static final String N_ANNOT = "diafoobar";
	public static final boolean THROW_EXCEPTION = false;
	void addView(IDiagraphView diagraphview);
	List<IDiagraphView> getViews();
	IDiagraphParser getParser();
	IDiagraphView getCurrentView();
	void setEcoreUtils(IEcoreUtils ecoreUtilService);
	IEcoreUtils getEcoreUtils();
	ISyntaxRules getSyntaxRules();
	void cerror(String mesg);
	IDiagraphView getView_(String viewId);
	void initView(EPackage packag, String view);
	void setCurrentView_(IDiagraphView view);
	boolean isNodeInstanciable(EClass c);
	boolean isLinkInstanciable(EClass c);
	void creport(List<String> log);
	//void logGeneration(String mesg);
}
