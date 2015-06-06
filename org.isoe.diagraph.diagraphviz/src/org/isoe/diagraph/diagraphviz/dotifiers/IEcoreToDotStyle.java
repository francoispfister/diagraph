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
package org.isoe.diagraph.diagraphviz.dotifiers;

import java.util.Map;
import java.util.Set;

/**
 * 
 * @author pfister
 *
 */
public interface IEcoreToDotStyle {

	Map<String, String> replacements();

	Map<String, String> eClassColors();

	Set<String> getHiddenEClasses1();

	Set<String> getHiddenEClasses2();

	Set<String> eClassesWithVisibleAttributes();

	Set<String> getHiddenAttributes();

	boolean isReplace();

	boolean isFilterAttributes();

	boolean isHideAttributes();

}
