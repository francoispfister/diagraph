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
package org.isoe.fwk.integration;

import java.util.List;

import org.isoe.mbse.sourcecleaner.Configuration;
import org.isoe.mbse.sourcecleaner.Extension;
import org.isoe.mbse.sourcecleaner.ExtensionPoint;
import org.isoe.mbse.sourcecleaner.ExtensionReference;
import org.isoe.mbse.sourcecleaner.Project;
import org.isoe.mbse.sourcecleaner.Schema;

/**
 *
 * @author fpfister
 *
 */
public interface IntegrationConfiguration {
	Configuration getConfiguration();
	List<String> getExtensionPointList();
	void addExtension(Project project, Extension extension);
	Schema createSchema(Project project, String schemaPath);
	List<ExtensionPoint> getIsoeExtensionPoints();
	Project getProject(ExtensionReference extensionReference);
	void addError(String string);
}
