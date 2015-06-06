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
package org.isoe.extensionpoint.diagen;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.isoe.diagraph.workbench.api.DiagraphNotifiable;

/**
 *
 * @author fpfister
 *
 */
public interface IMegamodelService {
   void run(String cmd);
   void setLineNumbered(boolean lineNumbered);
   void setLegended(boolean legended);
   void setShowStatus(boolean showStatus);
   void setSilent(boolean value);
   boolean isQualified();
   boolean isStub();
   void setControler(DiagraphNotifiable owner);
   void setHeadless(boolean headless);
   void setSelection(List<IProject> selectedProjects);
   void setMegaModelProject(IProject project);
   DiagraphNotifiable getControler();
}
