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
package org.isoe.diagraph.check.release.views;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.*;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
//import swing2swt.layout.FlowLayout;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
//import swing2swt.layout.BorderLayout;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.isoe.fwk.utils.ResourceUtils;

public class CheckRelease extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "org.isoe.diagraph.check.release.views.CheckRelease";
	private Combo checkCombo;
	private Combo projectCombo;
	private Text logtext_;

	/**
	 * The constructor.
	 */
	public CheckRelease() {
	}

	private void makeActions() {

	}

	private void showMessage(String message) {
		MessageDialog.openInformation(getSite().getShell(),
				"Check Release View", message);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		// viewer.getControl().setFocus();
	}

	@Override
	public void createPartControl(Composite parent) {
		//parent.setLayout(new BorderLayout(0, 0));

		Composite panel = new Composite(parent, SWT.NONE);
		panel.setBackground(SWTResourceManager.getColor(255, 240, 245));
		//panel.setLayoutData(BorderLayout.NORTH);

		Composite toolbar = new Composite(panel, SWT.NONE);
		toolbar.setBackground(SWTResourceManager.getColor(255, 240, 245));
		toolbar.setBounds(33, 0, 474, 50);

		checkCombo = new Combo(toolbar, SWT.NONE);
		checkCombo.setBounds(255, 10, 104, 23);
		// dotifyTypeCombo.setSize(104, 23);

		Button checkButton = new Button(toolbar, SWT.NONE);
		checkButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				checkWorkspace();
			}
		});

		checkButton.setLocation(372, 8);
		checkButton.setSize(75, 25);
		checkButton.setText("Check");

		projectCombo = new Combo(toolbar, SWT.NONE);
		projectCombo.setBounds(10, 10, 228, 23);

		// repositoryCombo.setBounds(-20, 10, 214, 23);

		logtext_ = new Text(parent, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);

		// logtext_ = new Text(parent, SWT.BORDER);
		// logtext_.setLayoutData(BorderLayout.CENTER);

		makeActions();
		initialiseUI();

	}

	private List<File> projects = new ArrayList<File>();

	protected void checkConsistency() {

		for (File project : projects) {
			if (project.getName().startsWith("org.isoe") && !project.getName().endsWith(".updatesite")) {
				File[] projDetail = project.listFiles();
				boolean pluginfound_ = false;
				boolean buildfound = false;
				if (projDetail != null) {
					for (File projdetail : projDetail) {
						if (projdetail.isFile()) {
							String prodet = projdetail.getName();

							if (prodet.equals("build.properties"))
								buildfound = true;
						}
					}
				}
				if (!(buildfound))
					clog("    check " + project);
			}
		}
	}

	protected void checkProjects(File ws) {
		projects.clear();
		try {
			File[] projroots = ws.listFiles();
			for (File projroot : projroots) {
				if (projroot.isDirectory()) {
					File[] projdetails = projroot.listFiles();
					for (File projdetail : projdetails) {
						if (".project".equals(projdetail.getName()))
							projects.add(projroot);
					}
				}
			}

		} catch (Exception e) {
			clog(e.toString());
		}
	}

	protected void checkWorkspace() {
		URI uri = URI.createPlatformPluginURI("org.isoe.fwk.core", false);
		URI ruri = CommonPlugin.resolve(uri);
		File corprj = new File(ruri.toFileString());
		File workspace = corprj.getParentFile();
		checkProjects(workspace);
		checkConsistency();
	}

	private void clog(String mesg) {
		System.out.println(mesg);

	}

	private void initialiseUI() {
		// TODO Auto-generated method stub

	}
}
