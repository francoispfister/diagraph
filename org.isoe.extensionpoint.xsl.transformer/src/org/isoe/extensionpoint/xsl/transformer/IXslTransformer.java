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
package org.isoe.extensionpoint.xsl.transformer;

import org.eclipse.emf.ecore.EPackage;

//import org.isoe.fwk.core.DiagraphService;

/**
 * 
 * @author fpfister
 *
 */
public interface IXslTransformer {
	////launcher.setInput("a0.simpleworld");
	void setLauncherName(String name);

	void run();

	void setProject(String project);

	void setModelfolder(String modelfolder);

	void setInput(String input);

	void setParametername(String parametername);

	void setParametervalue(String parametervalue);

	void setEPackage(EPackage currentPackage);

	//void setControler(IDiagraphControler controler);
	boolean isQualified();
	boolean isStub();
	void setSilent(boolean silent);

	
}
