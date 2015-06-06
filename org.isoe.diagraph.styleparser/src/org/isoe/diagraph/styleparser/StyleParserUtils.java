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
package org.isoe.diagraph.styleparser;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.isoe.diagraph.common.DiaColor;
import org.isoe.diagraph.common.DiaLayout;
import org.isoe.diagraph.common.IDiagraph;
import org.isoe.diastyle.lang.StyleConstants;
import org.isoe.fwk.core.EcoreUtilsv0;




/**
 * 
 * @author pfister
 * 
 */
public class StyleParserUtils {

	private static final boolean LOG = false;
	private EPackage metaModel;
	private IDiagraph internalModel;



	public boolean setLinkStyle(String eclassname, String refName,
			String command, String commandArgument) {
		if ("oriented".equals(command) && "true".equals(commandArgument)) {
			setOriented_(eclassname, refName);
			return true;
		}
		return false;
	}

	public boolean setNodeStyle(String eclassname, Object command,
			String commandArgument) {
		if (findDiagraphedClass(metaModel, eclassname) == null)
			throw new RuntimeException("setNodeStyle - no EClass +"
					+ eclassname + " !!!");
		if (StyleConstants.STYLE_LAYOUT_.equals(command)) {
			setLayout(eclassname, commandArgument); //FP120715b
			return true;
		} else if (StyleConstants.STYLE_BACKGROUND.equals(command)) {
			setBackground(eclassname, commandArgument); //FP120715b
			return true;
		}
		return false;
	}

	

	public void setBadCommand(String eclassname, String command,
			String commandArgument) {
		String mesg="StyleParserUtils for " + eclassname
				+ " - " + "unknown command: " + command + " (" + commandArgument
				+ ")";
		System.out.print(mesg); //command: size (600,300)  command: create (firstLevel)
	}
	
	
	/*
templates in diagram plugin


*EditPart.java

      
	protected NodeFigure createNodePlate() {
		DefaultSizeNodeFigure result = new DefaultSizeNodeFigure(600,300);
		return result;
	}
	


*DiagramEditorUtil.java


	private static Diagram createInitialModel() {
		Diagram diagram = SchoolFactory.eINSTANCE.createDiagram();
		School school = SchoolFactory.eINSTANCE.createSchool();
		diagram.setSchool(school);
		return diagram;

	}
	 */
	

	private void setBackground(String eclassname, String colourCommand) {
		System.out.println("SPUSTB "+eclassname + " - "
				+ StyleConstants.STYLE_BACKGROUND + " - " + colourCommand);
		DiaColor backgroundcolor = DiaColor.getByName(colourCommand);
		if (backgroundcolor == null)
			throw new RuntimeException(" for " + eclassname + " - "
					+ "bad backround color: " + colourCommand);

		internalModel.setBackground(eclassname, backgroundcolor);
	}

	private void setOriented_(String eclassname, String refName) {
		System.out.println("SPUSTB "+eclassname + " - " + "Oriented"
				+ " - " + "oriented");
		internalModel.setReferenceOrientation(eclassname, refName);
	}

	private void setLayout(String eclassname, String layoutCommand) {
		System.out.println("SPUSTB "+eclassname + " - " + StyleConstants.STYLE_LAYOUT_
				+ " - " + layoutCommand);
		DiaLayout layout = DiaLayout.getByName(layoutCommand);
		if (layout == null)
			throw new RuntimeException(" for " + eclassname + " - "
					+ "bad layout: " + layoutCommand);
		internalModel.setLayout(eclassname, layout);
	}


	public EClass findDiagraphedClass(EPackage pak, String eClassName) {
		for (TreeIterator<EObject> it1 = pak.eAllContents(); it1.hasNext();) {
			EObject obj = it1.next();
			if ((obj instanceof EClass)){
				    EAnnotation ean = EcoreUtilsv0.getInViewDiagraphAnnotation((EClass) obj,
				    		org.isoe.diagraph.views.ViewConstants.DIAGRAPH_DEFAULT); //FP130410
				    EClass oecl=(EClass) obj;
				    if (ean!=null && oecl.getName().equals(eClassName))
				    	return oecl;
			}
		}
		return null;
	}
	
	

	public EReference findDiagraphedReference(EPackage pak, String className,
			String refName) {
		for (TreeIterator<EObject> it1 = pak.eAllContents(); it1.hasNext();) {
			EObject obj = it1.next();
			if ((obj instanceof EReference)) {
				EReference ref = (EReference) obj;
				if (refName.equals(ref.getName())) {
					EClass eclass = (EClass) ref.eContainer();
					EAnnotation ean = EcoreUtilsv0.getInViewDiagraphAnnotation(eclass,org.isoe.diagraph.views.ViewConstants.DIAGRAPH_DEFAULT);
					if (className.equals(eclass.getName()) && ean != null)
						return ref;
				}
			}
		}
		return null;
	}

	public StyleParserUtils(EPackage mmodel, IDiagraph diagraph) {
		this.metaModel = mmodel;
		this.internalModel = diagraph;
	}
	

}
