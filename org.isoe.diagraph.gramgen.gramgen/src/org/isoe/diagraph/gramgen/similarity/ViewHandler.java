 /**
 * Copyright (c) 2014 Laboratoire de Genie Informatique et Ingenierie de Production - Ecole des Mines d'Ales
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Blazo Nastov (ISOE-LGI2P) - initial API and implementation
 */
package org.isoe.diagraph.gramgen.similarity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecoretools.legacy.diagram.part.EcoreDiagramEditor;
import org.isoe.diagraph.commands.AddAnnotationDetailCommand;
import org.isoe.extensionpoint.gramgen.IAnnotationHelper;
import org.isoe.extensionpoint.gramgen.IDiagraphUtils;
import org.isoe.fwk.core.DParams;

/**
 *
 * @author bnastov
 * old class name was MultyView
 *
 */
import org.isoe.diagraph.controler.IDiagraphControler;  //FP140707refactored
public class ViewHandler implements IAnnotationHelper, IDiagraphUtils {

	private static final boolean LOG = DParams.MultyView_LOG;
	private EClass rootEClass;
	private Map<EClass, List<String>> associatedAnnotation;
	private Map<EClass, List<String>> elements_;
	private EcoreDiagramEditor editor;
	private String viewName;
	private String view = "";
	private IDiagraphUtils utils;
	
	private Map<EClass, List<String>>[] arguments = new Map[1];
	
	@Override
	public Object getArguments() {
		return arguments;
	}

	public ViewHandler(IDiagraphUtils utils,String fromview,String toview, EClass rootClass,
			Map<EClass, List<String>> associatedAnnotation,
			EcoreDiagramEditor editor) {
		this.view = fromview;
		this.viewName = toview;
		this.rootEClass = rootClass;
		this.associatedAnnotation = associatedAnnotation;
		this.elements_ = new HashMap<EClass, List<String>>();
		this.editor = editor;
		this.utils = utils;
	}



	public Map<EClass, List<String>> getElements() {
		arguments[0] = elements_;
		return arguments[0];//elements_;
	}
/*
	public void generateViewAnnotations_old() {
		// calculate the annotations
		calculateMultyViewAnnotations();
		String pov="default";
		int povid=0;
		new GefHandler(this,annotationMap).generateAll(pov, povid);
	}
*/
	private void addNavDetail() {
		AddAnnotationDetailCommand.execute(this, rootEClass, view,
				"nav:" + viewName);
	}

	public void generateSyntax() {
		addNavDetail();
		computeSyntax();
	}

	private void computeSyntax() {
		String viewAnnot = "view=" + viewName;
		if (LOG) clog("CMVA "+viewAnnot);
		ArrayList<EClass> listCls = getAllContained(rootEClass);

		ArrayList<EClass> subClasses = new ArrayList<EClass>();
		ArrayList<EClass> superClasses = new ArrayList<EClass>();

		for (EClass cls : listCls) {
			if (LOG) clog("CMVA2 ---" + cls.getName()+ " ---" );
			for (EClass scl : cls.getESuperTypes()) {
				if (!cls.equals(scl) && !superClasses.contains(scl)) {
					superClasses.add(scl);
					if (LOG) clog("CMVA2 addsuper "+cls.getName() +" --> "+scl.getName());
				}
			}
			for (EClass subCls : getSubClasses(cls)) {
				
				if (!cls.equals(subCls) && !subClasses.contains(subCls)) {
				  subClasses.add(subCls);
				  if (LOG) clog("CMVA2 addsub "+cls.getName() +" <-- "+subCls.getName());
				}
				
				
			}
		}
		// Merge the lists
		
		for (EClass eClass : superClasses)  //FP140116
			if (!listCls.contains(eClass)){
				listCls.add(eClass);
			}
		
		for (EClass eClass : subClasses) 
			if (!listCls.contains(eClass)){
				listCls.add(eClass);
			}
		
		
		//listCls.addAll(superClasses);
		//listCls.addAll(subClasses);
		for (EClass cl : listCls) {
		
			//if (cl.getName().equals("Flow"))
			//	tb = true;
			List<String> annots = associatedAnnotation.get(cl);
			if (annots != null) {
				if (LOG) clog("adding annotation "+viewAnnot+" to "+cl.getName());
				if (annots.contains(viewAnnot))// nav:
					throw new RuntimeException("should not happen in calculateMultyViewAnnotations: "+cl.getName()+" contains already "+viewAnnot);
				annots.add(viewAnnot);
				elements_.put(cl, annots);
			} else
				if (LOG) clog("no annotation "+viewAnnot+" for "+cl.getName());
		}
		elements_.get(rootEClass).add("pov");
		//return null;
	}
//old name= getAllClassesFromTheView
	private ArrayList<EClass> getAllContained(EClass claz) {
		if (LOG) clog("GACC "+claz.getName());
		ArrayList<EClass> listClasses = new ArrayList<EClass>();
		listClasses.add(claz);
		if (claz.getEAllContainments().size() == 0)
			return listClasses;
		else {
			for (EReference ref : claz.getEAllContainments()) {
				if (ref.getEType() instanceof EClass ) {
					EClass ecl = (EClass) ref.getEType();
					String  ecln=ecl.getName();
					String  eclnn= claz.getName();
					if (LOG) clog(eclnn+" *>* "+ecln);
					if ((EClass) ref.getEType() != claz){ //FP140116 //FP131118
						for (EClass cntClass : getAllContained(
								(EClass) ref.getEType())) 
							if (!listClasses.contains(cntClass)){
								listClasses.add(cntClass);
								if (LOG) clog(" adding "+cntClass.getName());
							}
					  //listClasses.addAll(getAllContained(
					//		(EClass) ref.getEType())); //, new ArrayList<EClass>()
					}
				}
			}
			return listClasses;
		}
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);

	}

	private ArrayList<EClass> getSubClasses(EClass c) {
		ArrayList<EClass> result = new ArrayList<EClass>();
		for (EClass cls : associatedAnnotation.keySet())
			if (!cls.equals(c) && c.isSuperTypeOf(cls))
				result.add(cls);
		return result;
	}

	@Override
	public String addDetailToDiagraphAnnotation(EClass parent, String view,
			String key) {
		return utils.addDetailToDiagraphAnnotation(parent, view,key);
	}


	@Override
	public IDiagraphUtils getDiagraphUtils() {
		return utils;
	}

	@Override
	public IDiagraphControler getControler() {
		return utils.getControler();
	}

	@Override
	public EcoreDiagramEditor getEditor() {
		return editor;
	}





}
