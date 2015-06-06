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
package org.isoe.fwk.jobs;

import java.io.IOException;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.ui.PartInitException;
//import org.isoe.fwk.docs.tools.DiagramRegistry.DiagramRegistryException;
//import org.isoe.fwk.diagen.DiagramRegistry.DiagramRegistryException;
import org.isoe.fwk.jobs.DiagramRegistry.DiagramRegistryException;

/**
 * 
 * @author jmwright
 * @author fpfister (adaptation to diagraph)
 *
 */
public class InitialiseDiagram {

	public static class InitializeDiagramException extends Exception {

		private static final long serialVersionUID = 1L;
		
		public InitializeDiagramException(Throwable cause) {
			super(cause.getMessage(), cause);
		}
		
	}

	public IFile initialize(IProject project, Diagram changed, IFile model, IFile diagram, String diagramExtension,boolean create,boolean open, IProgressMonitor monitor) throws PartInitException, InitializeDiagramException {
		monitor.beginTask("Finding diagram", 1);
		monitor.subTask("Initializing diagram " + model.getName());
		try {
			// get the diagram registry to open the diagram

			DiagramRegistry.initializeModelFile(model, changed, diagram, diagramExtension, create,open);
		} catch (RuntimeException e) {
			throw new InitializeDiagramException(e);
		} catch (ExecutionException e) {
			throw new InitializeDiagramException(e);
		} catch (IOException e) {
			throw new InitializeDiagramException(e);
		}catch (DiagramRegistryException e) {
         throw new InitializeDiagramException(e);
      } 
		monitor.done();
		return diagram;
	}
	
}
