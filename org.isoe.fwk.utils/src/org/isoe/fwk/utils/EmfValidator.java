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
package org.isoe.fwk.utils;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.codegen.ecore.generator.Generator;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.generator.GenBaseGeneratorAdapter;
import org.eclipse.emf.codegen.ecore.genmodel.util.GenModelUtil;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.Diagnostician;


/**
 *
 * @author pfister
 *
 */
public class EmfValidator {


	private static final boolean LOG = false;


	public static void validate(URI genModelUri) { //FP121122
		if (LOG)
		  clog("Loading GenModel with URI: " + genModelUri);
		ResourceSet rs = new ResourceSetImpl();
		Resource resource = rs.getResource(genModelUri, true);
		GenModel genModel = (GenModel) resource.getContents().get(0);
		genModel.setValidateModel(true);
		Diagnostician diagnostician = new Diagnostician();
		for (EObject object : resource.getContents())
			diagnostician.validate(object);
		IStatus validate = genModel.validate();
		if (validate.isOK()) {
			if (LOG)
			   clog("validation ok");
		} else {
			String errors = validate.getMessage();
			for (IStatus child : validate.getChildren())
				errors += " ; " + child.getMessage();
			throw new RuntimeException("Emf errors: " + errors
					+ " !!!!");
		}
	}


	private static void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}


	public static void generate(URI genModelUri) {
		if (LOG)
		   clog("Loading GenModel with URI: " + genModelUri);
		ResourceSet rs = new ResourceSetImpl();
		Resource resource = rs.getResource(genModelUri, true);
		GenModel genModel = (GenModel) resource.getContents().get(0);
		genModel.setValidateModel(true);
		Generator generator = GenModelUtil.createGenerator(genModel);
		Diagnostician diagnostician = new Diagnostician();
		for (EObject object : resource.getContents())
			diagnostician.validate(object);
		IStatus validate = genModel.validate();
		if (validate.isOK()) {
			genModel.setCanGenerate(true);
			genModel.reconcile();
			generator.setInput(genModel);
			Diagnostic diagnostic = generator.generate(genModel,
					GenBaseGeneratorAdapter.MODEL_PROJECT_TYPE,
					new BasicMonitor());//.Printing(System.err));
			if (diagnostic.getCode() == Diagnostic.OK)
				diagnostic = generator.generate(genModel,
						GenBaseGeneratorAdapter.EDIT_PROJECT_TYPE,
						new BasicMonitor());//.Printing(System.err));
			if (diagnostic.getCode() == Diagnostic.OK)
				diagnostic = generator.generate(genModel,
						GenBaseGeneratorAdapter.EDITOR_PROJECT_TYPE,
						new BasicMonitor());//.Printing(System.err));
			if (diagnostic.getCode() == Diagnostic.OK)
				diagnostic = generator.generate(genModel,
					GenBaseGeneratorAdapter.TESTS_PROJECT_TYPE,
					new BasicMonitor());//.Printing(System.err));
			clog(diagnostic.getMessage());
		} else {
			String errors = validate.getMessage();
			for (IStatus child : validate.getChildren()){
				errors += " ; " + child.getMessage();
				if (child.getMessage().contains("The default value literal '' must be a valid literal")){

				}
			}
			throw new RuntimeException("Error in EmfGenerator: " + errors
					+ " !!!!");
		}
	}

}
