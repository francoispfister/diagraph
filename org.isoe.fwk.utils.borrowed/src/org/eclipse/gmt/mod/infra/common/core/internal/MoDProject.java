/*******************************************************************************
 * Copyright (c) 2009, 2011 Mia-Software.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Fabien Giquel (Mia-Software)
 *******************************************************************************/
package org.eclipse.gmt.mod.infra.common.core.internal;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;


/**
 * The implementation of the MoD project nature.
 */
public class MoDProject implements IProjectNature {

	/**
	 * The platform project this <code>IJavaProject</code> is based on
	 */
	private IProject project;

	/**
	 * The nature of MoD projects
	 */
	public static final String NATURE_ID = "org.eclipse.gmt.mod.common.ProjectNature"; //$NON-NLS-1$
	
	
	/**
	 * The MoD builder id
	 */
	public static final String MOD_PROJECT_BUILDER_NAME = "org.eclipse.gmt.mod.common.core.builder";
	

	/**
	 * @see org.eclipse.core.resources.IProjectNature#configure()
	 */
	public void configure() throws CoreException {
		// Add nature-specific information
		// for the project, such as adding a builder
		// to a project's build spec.
		IProjectDescription projectDescription = this.project.getDescription();
		ICommand[] oldBuildSpec = this.project.getDescription().getBuildSpec();
		ICommand[] newBuildSpec = new ICommand[oldBuildSpec.length + 1];
		System.arraycopy(oldBuildSpec, 0, newBuildSpec, 0, oldBuildSpec.length);
		ICommand command = this.project.getDescription().newCommand();
		command.setBuilderName(MOD_PROJECT_BUILDER_NAME);
		newBuildSpec[oldBuildSpec.length] = command;
		projectDescription.setBuildSpec(newBuildSpec);
		this.project.setDescription(projectDescription, new NullProgressMonitor());
	}

	/**
	 * @see org.eclipse.core.resources.IProjectNature#deconfigure()
	 */
	public void deconfigure() throws CoreException {
		// Remove the nature-specific information here.
	}

	/**
	 * @see org.eclipse.core.resources.IProjectNature#getProject()
	 */
	public IProject getProject() {
		return this.project;
	}

	/**
	 * @see org.eclipse.core.resources.IProjectNature#setProject(org.eclipse.core.resources.IProject)
	 */
	public void setProject(final IProject value) {
		this.project = value;
	}

}
