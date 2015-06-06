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
package org.isoe.diagraph.generator.generic;



import org.isoe.diagraph.internal.api.DPhase;
import org.isoe.diagraph.internal.api.IDiaSyntaxElement;
import org.isoe.diagraph.internal.m2.parser.DStatement;
import org.isoe.diagraph.internal.m2.parser.DStatement.DCommand_;

/**
 *
 * @author pfister
 *
 */
public interface IDiaGenerator {



	void executeCommands(DPhase phase);

    void initTool();

	void pass2(DPhase phase);

	void pass3(DPhase phase);

	void pass4(DPhase phase);

	boolean createValidators(DPhase phase);




	void initIdGenerator();

	//Object processReference(IDiaSyntaxElement reference,boolean oriented, boolean unique);

	Object processLink(DStatement statement,boolean oriented);

	void processCanvas(DStatement statement);

	boolean processContainment(DStatement statement);

	void processLabels(IDiaSyntaxElement diagramElement);

	void validateDomainModel(DPhase map);

	void processDomainModel(DPhase phase);
	//void processDomainModel(DPhase phase);

	Object processReference_(IDiaSyntaxElement ref, boolean oriented,
			boolean unique_);

	Object processNodeTop(DStatement statement, DCommand_ force);

}
