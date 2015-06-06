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
/**
 *
 */
package org.isoe.fwk.diagen.jobs;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.parts.DiagramDocumentEditor;
import org.isoe.diagraph.workbench.api.DiagraphNotifiable;


/**
 * @author fpfister  // borrowed from iaml, adaptation to diagraph
 *
 */
public class HandleLanguagesJob extends HandleMegamodelJob {


   public HandleLanguagesJob(IProject project, boolean headless,String language,DiagraphNotifiable owner) {
      super(headless,"check a DSML", project, language,owner);

   }

   protected void diagraphit_(String name,IProgressMonitor monitor)
         throws CoreException {
         IProgressMonitor smonitor = new SubProgressMonitor(monitor, 2);
         smonitor.beginTask("handling for " + name, 2);
         boolean deployBuildInPlugin = true;
         boolean deployLocalPlugin = true;
         boolean deployLocalWorkspace = true;
         notifiable.diagraphMegamodel_(deployBuildInPlugin,deployLocalPlugin,deployLocalWorkspace, name);
         smonitor.worked(1);
         smonitor.done();
   }

   @Override
   protected void doit(String name_,
         DiagramDocumentEditor editor, IProgressMonitor monitor)
         throws CoreException {
      DiagramEditPart part = editor.getDiagramEditPart();

      IProgressMonitor sMonitor = new SubProgressMonitor(monitor, 2);
      sMonitor.beginTask("doit" + name_, 2);
      IFile modelfile_ = getModel(name_+".ecore");
      if (modelfile_ != null) {
         monitor.subTask("diagraphit");
         diagraphit_(getProject().getName(),new SubProgressMonitor(monitor, 40));
         sMonitor.worked(1);
      }
      sMonitor.done();

   }





}
