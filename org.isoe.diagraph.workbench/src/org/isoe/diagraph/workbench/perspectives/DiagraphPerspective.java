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
package org.isoe.diagraph.workbench.perspectives;

import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.console.IConsoleConstants;

/**
 *
 * @author fpfister
 *
 */
public class DiagraphPerspective implements IPerspectiveFactory {

	public static final String DiagraphConsoleID_no = "org.eclipse.ui.console.ConsoleView"; //$NON-NLS-1$
	public static final String GraphVizViewID_no = "org.isoe.diagraph.diagraphviz.ui.views.GraphVizView";
	public static final String DiagraphInstanceViewID_no = "org.isoe.diagraph.instance.view.views.DiagraphInstanceView";
	public static final String BasicViewID = org.isoe.diagraph.generic.GenericConstants.DIAGRAPH_BASIC_CONTROLER_ID_;
	public static final String ProblemViewID = IPageLayout.ID_PROBLEM_VIEW;




	private static final float PAGE = 0.95f;
	private static final float H = 0.46f;
	private static final float W = 0.63f;


	private static final boolean LOG = false;

	private IPageLayout factory;

	public DiagraphPerspective() {
		super();
	}

	public void createInitialLayout(IPageLayout factory) {
		this.factory = factory;
		clog("DiagraphPerspective createInitialLayout");
		addViews();
		addActionSets();
		addNewWizardShortcuts();
		addPerspectiveShortcuts();
		addViewShortcuts();
	}

	private void addViews() {



		IFolderLayout bottom = factory.createFolder("diagraph.bottom", // NON-NLS-1
				IPageLayout.BOTTOM, PAGE-H, factory.getEditorArea());

		bottom.addView(IPageLayout.ID_PROP_SHEET); // NON-NLS-1
		bottom.addView(IConsoleConstants.ID_CONSOLE_VIEW);


		IFolderLayout bottomR_ = factory.createFolder("diagraph.bottomRight",
				IPageLayout.RIGHT, PAGE-W, "diagraph.bottom");

		try {
			clog("in diagraph perspective, adding view " + BasicViewID);
			bottomR_.addView(BasicViewID);
			clog("in diagraph perspective " + BasicViewID + " added ok");
		} catch (Exception e) {
			System.err.println("View descriptor not found " + BasicViewID);
		}

		bottomR_.addView(ProblemViewID);


		IFolderLayout topRight = factory.createFolder("diagraph.topRight", // NON-NLS-1
				IPageLayout.RIGHT, 0.85f, factory.getEditorArea());
		topRight.addView(IPageLayout.ID_OUTLINE);

		IFolderLayout topLeft = factory.createFolder("diagraph.topLeft", // NON-NLS-1
				IPageLayout.LEFT, 0.25f, factory.getEditorArea());
		topLeft.addView(JavaUI.ID_PACKAGES);
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);

	}

	private void addActionSets() {
		factory.addActionSet("org.eclipse.debug.ui.launchActionSet"); // NON-NLS-1
		factory.addActionSet("org.eclipse.debug.ui.debugActionSet"); // NON-NLS-1
		factory.addActionSet("org.eclipse.debug.ui.profileActionSet"); // NON-NLS-1
		factory.addActionSet("org.eclipse.jdt.debug.ui.JDTDebugActionSet"); // NON-NLS-1
		factory.addActionSet("org.eclipse.jdt.junit.JUnitActionSet"); // NON-NLS-1
		factory.addActionSet("org.eclipse.team.ui.actionSet"); // NON-NLS-1
		factory.addActionSet("org.eclipse.team.cvs.ui.CVSActionSet"); // NON-NLS-1
		factory.addActionSet("org.eclipse.ant.ui.actionSet.presentation"); // NON-NLS-1
		factory.addActionSet(JavaUI.ID_ACTION_SET);
		factory.addActionSet(JavaUI.ID_ELEMENT_CREATION_ACTION_SET);
		factory.addActionSet(IPageLayout.ID_NAVIGATE_ACTION_SET); // NON-NLS-1
	}

	private void addPerspectiveShortcuts() {
		factory.addPerspectiveShortcut("org.isoe.diagraph.workbench.perspectives.DiagraphPerspective"); // NON-NLS-1
		factory.addPerspectiveShortcut("org.eclipse.team.ui.TeamSynchronizingPerspective"); // NON-NLS-1
		factory.addPerspectiveShortcut("org.eclipse.team.cvs.ui.cvsPerspective"); // NON-NLS-1
		factory.addPerspectiveShortcut("org.eclipse.team.svn.ui.repository.RepositoryPerspective"); //$NON-NLS-1$
		factory.addPerspectiveShortcut("org.eclipse.ui.resourcePerspective"); // NON-NLS-1
	}

	private void addNewWizardShortcuts() {
		factory.addNewWizardShortcut("org.eclipse.team.cvs.ui.newProjectCheckout");// NON-NLS-1
		factory.addNewWizardShortcut("org.eclipse.ui.wizards.new.folder");// NON-NLS-1
		factory.addNewWizardShortcut("org.eclipse.ui.wizards.new.file");// NON-NLS-1

		factory.addNewWizardShortcut("org.eclipse.jdt.ui.wizards.NewPackageCreationWizard"); //$NON-NLS-1$
		factory.addNewWizardShortcut("org.eclipse.jdt.ui.wizards.NewClassCreationWizard"); //$NON-NLS-1$
		factory.addNewWizardShortcut("org.eclipse.jdt.ui.wizards.NewInterfaceCreationWizard"); //$NON-NLS-1$
		factory.addNewWizardShortcut("org.eclipse.jdt.ui.wizards.NewSourceFolderCreationWizard"); //$NON-NLS-1$
		factory.addNewWizardShortcut("org.eclipse.jdt.ui.wizards.NewJavaWorkingSetWizard"); //$NON-NLS-1$
		factory.addNewWizardShortcut("org.eclipse.ui.editors.wizards.UntitledTextFileWizard");//$NON-NLS-1$
	}

	private void addViewShortcuts() {
		factory.addShowViewShortcut("org.eclipse.ant.ui.views.AntView"); // NON-NLS-1
		factory.addShowViewShortcut("org.eclipse.team.ccvs.ui.AnnotateView"); // NON-NLS-1
		factory.addShowViewShortcut("org.eclipse.pde.ui.DependenciesView"); // NON-NLS-1
		factory.addShowViewShortcut("org.eclipse.jdt.junit.ResultView"); // NON-NLS-1
		factory.addShowViewShortcut("org.eclipse.team.ui.GenericHistoryView"); // NON-NLS-1
		factory.addShowViewShortcut(IConsoleConstants.ID_CONSOLE_VIEW);
		factory.addShowViewShortcut(JavaUI.ID_PACKAGES);
		// factory.addShowViewShortcut(IPageLayout.ID_RES_NAV);
		factory.addShowViewShortcut(IPageLayout.ID_PROBLEM_VIEW);
		factory.addShowViewShortcut(IPageLayout.ID_OUTLINE);

		// factory.addShowViewShortcut(DiagraphConsoleID); //FP120327
		// factory.addShowViewShortcut(DiagraphViewID_);
		// factory.addShowViewShortcut(GraphVizViewID);
		// factory.addShowViewShortcut(DiagraphInstanceViewID);
		factory.addShowViewShortcut(BasicViewID);

		// factory.addShowViewShortcut(SelectionView.ID);
		// factory.addShowViewShortcut(DiagrapPropertyView_notdeploy.ID);
		factory.addShowViewShortcut("org.eclipse.pde.runtime.LogView"); //$NON-NLS-1$
		factory.addShowViewShortcut(JavaUI.ID_TYPE_HIERARCHY);
		factory.addShowViewShortcut(JavaUI.ID_SOURCE_VIEW);
		factory.addShowViewShortcut(IPageLayout.ID_TASK_LIST);
	}

}
