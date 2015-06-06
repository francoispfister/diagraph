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


/**
 *
 * @author fpfister
 *
 */
public interface IDiagraphAnnotation {
	String getKey();
	String getValue();
	String getArgument2();
	boolean keyFirstSegmentEquals(String keyword);
	void setValue(String value);
	String[] parse();
	void setOther(List<IDiagraphAnnotation> anns);
	List<IDiagraphAnnotation> getOther();
	String toLog();
	String getArgument1();
	void setProblem();
}
