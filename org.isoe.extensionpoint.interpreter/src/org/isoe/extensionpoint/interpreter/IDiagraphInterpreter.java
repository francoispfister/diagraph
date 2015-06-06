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
package org.isoe.extensionpoint.interpreter;


/**
 *
 * @author fpfister
 *
 */
public interface IDiagraphInterpreter {
	void initStyler();
	void clear();
	void cloneLanguageAndGenGrammar(String resultLanguage,boolean headless);
	void mergeLanguagesAndGenGrammar(String lang, String with,
			String cloneLanguage, boolean headlesst);
	void executeCommandLine(String content);
	void buildAll(boolean generateEditors, boolean headless);
	void copyAll(boolean generateEditors, boolean headless);

}


