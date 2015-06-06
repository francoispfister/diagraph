package org.isoe.extensionpoint.gramgen;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecoretools.legacy.diagram.part.EcoreDiagramEditor;

import org.isoe.diagraph.controler.IDiagraphControler;  //FP140707refactored
public interface IDiagraphService {
	boolean checkConcreteSyntax(String view);
	Map<EClass, List<String>> createLinkAnnotation(
			Map<EClass, List<String>> annotations, String pov, EClass target);
	void dropConcreteSyntax(String view, EClass selectedClass, boolean register);
	Map<EClass, List<String>>[] getConcreteSyntax();
	IDiagraphControler getControler();
	String getDiagraphActions();
	EcoreDiagramEditor getEcoreDiagramEditor();
	String getView();
	int getViewId();
	String getViewName();
	EClass getViewRoot(String eclassName);
	void init();
	boolean isElementType(String view, String key, EClass eclaz);
	boolean isQualified();
	boolean isStub();
	List<EClass> propagateNodes(String view, EClass c);
	void registerCommand(String cmd);
	void setControler(Object controler);
	void setEModelElement(EModelElement eModelElement);
	void setSilent(boolean value);
	void setView(String view);
}
