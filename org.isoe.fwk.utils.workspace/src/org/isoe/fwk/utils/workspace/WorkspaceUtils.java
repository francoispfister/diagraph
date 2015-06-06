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
package org.isoe.fwk.utils.workspace;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.resource.Resource;


// utilities for workspace (IResource) operations

/**
 * 
 * borrowed from Borland
 *
 */
public class WorkspaceUtils {
	private final IProject project;

	WorkspaceUtils(IProject p) {
		//assert p != null;
		if (p == null)
			throw new RuntimeException("project should not be null");
		project = p;
	}

	void atomicSave(Resource resource) {
		atomicSave(Collections.singletonList(resource));
	}

	void atomicSave(final List<Resource> resources) {
		// Batching all the notifications from Eclipse resource subsystem.
		// Otherwise notifications will be dispatched on by one and just created
		// diagram node will be removed by CanonicalEditPolicy because
		// corresponding notification from the domain model file changes is
		// waiting to be dispatched later
		IWorkspaceRunnable runnable = new IWorkspaceRunnable() {

			public void run(IProgressMonitor monitor) throws CoreException {
				for (Resource nextResource : resources) {
					try {
						nextResource.save(Collections.EMPTY_MAP);
					} catch (IOException e) {
						throw new RuntimeException(e.getMessage());
					}
				}
			}
		};
		JobTracker jt = new JobTracker();
		try {
			jt.start();
			ResourcesPlugin.getWorkspace().run(runnable, project, IWorkspace.AVOID_UPDATE, new NullProgressMonitor());
			jt.freeze();
			// WorkspaceSynchronizer re-dispatches resource events using Jobs, let these jobs complete
			MessageUtils.assertDispatchDisplayMessages(jt.getNonEmptyCondition(), 3);
			// hence DocumentProvider.ResourceSetInfo's delegate (from Job) invoked handleResourceChanged(), which in turn 
			// posted asyncExec to invoke DocumentProvider.handleElementChanged(), thus need to give it a chance
			MessageUtils.assertDispatchDisplayMessages(3);
		} catch (CoreException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			jt.stop();
		}
	}
}
