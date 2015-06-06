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
package org.isoe.fwk.basic.controler;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.part.ViewPart;
//import org.isoe.extensionpoint.diagraph.DiagraphConnector;
import org.isoe.extensionpoint.ecoreutils.EcoreUtilConnector;
//import org.isoe.extensionpoint.ecoreutils.EcoreUtilStub;
import org.isoe.extensionpoint.ecoreutils.IEcoreUtils;
import org.isoe.extensionpoint.generator.ISyntaxRefactoring;
import org.isoe.extensionpoint.generator.SyntaxGeneratorConnector;
import org.isoe.extensionpoint.gramgen.GramgenConnector;
//import org.isoe.extensionpoint.gramgen.GramgenStub;
import org.isoe.extensionpoint.gramgen.IGrammarGeneratorService;
import org.isoe.extensionpoint.remoting.DiagraphRemotingConnector;
import org.isoe.extensionpoint.remoting.IDiagraphRemotingService;
import org.isoe.extensionpoint.rules.ISyntaxRules;
import org.isoe.extensionpoint.rules.SyntaxRulesConnector;

/**
 *
 * @author pfister
 *  TODO use generics to
 *         avoid boiler plate code
 *
 */
import org.isoe.diagraph.controler.IDiagraphControler;  //FP140707refactored
import org.isoe.extensionpoint.diagraph.action.DiagraphService;  //FP140707_c_refactored
import org.isoe.extensionpoint.diagraph.generator.DiagraphGenConnector;
import org.isoe.extensionpoint.diagraph.generator.IDiagraphGen;  //FP140707_b_refactored
public class DiagraphComponentConnector {

	private static final boolean LOG = false;
	private IDiagraphControler diagraphControler;
	//private List<DiagraphService>  diagraphServices = new ArrayList<DiagraphService>();


	public DiagraphComponentConnector(IDiagraphControler diagraphControler) {
		super();
		this.diagraphControler = diagraphControler;
	}

	public ISyntaxRules getSyntaxRulesService() {
		ISyntaxRules syntaxRulesService = new SyntaxRulesConnector()
				.getService();
		if (syntaxRulesService == null)
			throw new RuntimeException("no syntaxRulesService");
		syntaxRulesService.setControler(diagraphControler);
		if (syntaxRulesService.isStub()) // is a mock object, presently
											// unusable, for further
											// implementation
			if (LOG) {
				clog("syntaxRulesService is Stub");
				String willDo = syntaxRulesService.exposeContract();
				clog("should do: " + willDo);
			}
		if (!syntaxRulesService.isQualified()) // is not a mock object but not
												// usable
			if (LOG)
				clog("syntaxRulesService is Not Qualified");
		//getDiagraphServices().add(syntaxRulesService);
		return syntaxRulesService;
	}

	public ISyntaxRefactoring getSyntaxRefactoringService() {
		ISyntaxRefactoring syntaxRefactoringService = new SyntaxGeneratorConnector()
				.getSyntaxRefactoringService();
		if (syntaxRefactoringService == null)
			throw new RuntimeException("no syntaxRefactoringService");
		syntaxRefactoringService.setControler(diagraphControler);
		if (syntaxRefactoringService.isStub()) // is a mock object, presently
												// unusable, for further
												// implementation
			if (LOG) {
				clog("syntaxRefactoringService is Stub");
				String willDo = syntaxRefactoringService.exposeContract();
				clog("should do: " + willDo);
			}
		if (!syntaxRefactoringService.isQualified()) // is not a mock object but
														// not usable
			if (LOG)
				clog("syntaxRefactoringService is Not Qualified");
		//getDiagraphServices().add(syntaxRefactoringService);
		return syntaxRefactoringService;
	}

	public IDiagraphGen getDiagraphGeneratorService() {
		IDiagraphGen diagraphGenerator = new DiagraphGenConnector()
				.getService(); // seek the diagraph plugin

		  if (diagraphGenerator.isStub()){
			  clog("******    warning: diagraphGenerator is the stub!!!");
		  }

		// through the eclipse
		// extension mechanism
	//	if (diagraphGenerator == null)
	//		diagraphGenerator = new org.isoe.extensionpoint.diagraph.DiagraphGeneratorStub(); // FP130417
																								// if
		// diagraph
		// plugin is not
		// present, use
		// a simpler
		// generator
		diagraphGenerator.setOwner((ViewPart) diagraphControler);
		//diagraphGenerator.setControler(diagraphControler);
		//diagraphServices.add(diagraphGenerator);
		//getDiagraphServices().add(diagraphGenerator);
		return diagraphGenerator;
	}

	public IDiagraphRemotingService getDiagraphRemotingService() {
		IDiagraphRemotingService service = new DiagraphRemotingConnector()
				.getService();
		if (service == null)
			throw new RuntimeException("no diagraphRemotingService");
		service.setControler(diagraphControler);
		if (service.isStub()) // is a mock object, presently
								// unusable, for further
								// implementation
			if (LOG) {
				clog("diagraphRemotingService is Stub");
				// String willDo = service.exposeContract();
				// System.out.println("should do: " + willDo);
			}
		if (!service.isQualified()) // is not a mock object but
									// not usable
			if (LOG)
				clog("diagraphRemotingService is Not Qualified");
		//getDiagraphServices().add(service);
		return service;
	}

	public IGrammarGeneratorService getGrammarGeneratorService() {
		IGrammarGeneratorService grammarGeneratorService = new GramgenConnector()
				.getService();
		// seek the gramgen plugin through the Eclipse Extension Mechanism
		if (grammarGeneratorService == null) {
			if (LOG)
				clog("No grammar generation service is available !");
			throw new RuntimeException("no Gramgen");
			// use a mock if the real service is not discovered by the
			// Eclipse Extension Mechanism
		} else if (LOG)
			clog("Using Gramgen Feature for grammar generation");
		grammarGeneratorService.setControler(diagraphControler);
		//getDiagraphServices().add(grammarGeneratorService);
		return grammarGeneratorService;
	}


	  public IEcoreUtils getEcoreUtilService() {
	     IEcoreUtils ecoreUtilService = new EcoreUtilConnector().getEcoreUtilService();
	      // seek the gramgen plugin through the Eclipse Extension Mechanism
	      if (ecoreUtilService == null) {
	         if (LOG)
	            clog("No Ecore Util service is available !");
	         throw new RuntimeException(" no EcoreUtil");
	         // use a mock if the real service is not discovered by the
	         // Eclipse Extension Mechanism
	      } else if (LOG)
	         clog("Using EcoreUtilService");
	    //  ecoreUtilService.setControler(diagraphControler);

	      return ecoreUtilService;
	   }

	private void clog(String mesg) {
		System.out.println(mesg);
	}

}
