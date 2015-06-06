package org.isoe.extensionpoint.gramgen;

import org.eclipse.emf.ecoretools.legacy.diagram.part.EcoreDiagramEditor;

public interface IPackageCommandHandler {
	void changeColor(String view, int color);
	void highlightAnnotation(String key);
	void initializeElement(EcoreDiagramEditor editor);
	void dropAll();
}
