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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.image.ImageFileFormat;
import org.eclipse.gmf.runtime.diagram.ui.render.util.CopyToImageUtil;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.parts.DiagramDocumentEditor;
import org.isoe.diagraph.megamodel.Dsml;
import org.isoe.diagraph.workbench.api.DiagraphNotifiable;
import org.isoe.fwk.core.DParams;

/**
 * @author fpfister // borrowed from iaml, adaptation to diagraph
 *
 */
public class ExportImagesJob extends HandleMegamodelJob {

	public ExportImagesJob(IProject project, boolean headless, String language,
			DiagraphNotifiable owner) {

		super(headless, "Export diagraph images", project, language, owner);

	}

	protected CopyToImageUtil getCopyToImageUtil() {
		return new CopyToImageUtil();
	}

	protected IPath generateImageDestination(String filename,
			String fileExtension) {
		if (!fileExtension.startsWith("."))
			throw new IllegalArgumentException(
					"File extension needs to start with '.'");
		return getProject().getFile("model/" + filename + fileExtension)
				.getLocation();
	}

	@Override
	protected void doit(String name, DiagramDocumentEditor editor,
			IProgressMonitor monitor) throws CoreException {
		DiagramEditPart part = editor.getDiagramEditPart();

		IProgressMonitor saveMonitor = new SubProgressMonitor(monitor, 2);
		saveMonitor.beginTask("Saving image for " + name, 2);
		if (LOG)
			clog_("Saving image for " + name);
		IPath destination = generateImageDestination(name, ".jpeg");
		monitor.subTask("Saving JPEG image " + destination.lastSegment());
		CopyToImageUtil img = getCopyToImageUtil();
		img.copyToImage(part, destination, ImageFileFormat.JPEG,
				new SubProgressMonitor(monitor, 1));
		Dsml dsml = addDsml(name, "exportimagesjob");  //FP140622aa

		addEcoreToolsDiagram_(dsml, destination.toPortableString());

		saveMonitor.worked(1);

		saveMonitor.done();

	}

	private void clog_(String mesg) {
		if (LOG)
			System.out.println(mesg);

	}

}
