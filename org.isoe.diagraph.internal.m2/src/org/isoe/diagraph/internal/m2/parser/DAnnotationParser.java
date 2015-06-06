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
package org.isoe.diagraph.internal.m2.parser;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.diagraph.DGraphElement;
import org.isoe.diagraph.diastyle.DStyle;
import org.isoe.diagraph.diastyle.helpers.IStyleHandler;
import org.isoe.diagraph.internal.api.IDiaConcreteSyntax;
import org.isoe.diagraph.internal.api.IDiaPlatformDelegateProvider;
import org.isoe.diagraph.internal.api.IDiaPointOfView;
import org.isoe.diagraph.internal.m2.DiaGraph;
import org.isoe.diagraph.internal.m2.api.IDiaParser;
import org.isoe.diagraph.parser.api.IDiagraphNotation;
import org.isoe.diagraph.parser.api.IDiagraphParser;
import org.isoe.diagraph.parser.api.IDiagraphView;
import org.isoe.diagraph.runner.IDiagraphRunner;
import org.isoe.fwk.core.DParams;

/**
 *
 * @author pfister
 *
 */
public class DAnnotationParser {

	private static final boolean LOG = DParams.AnnotationParser_LOG;

	private String[] resourceData;
	private List<DStatement> statements;
	private EPackage domainModel;
	private DiaParser parser1;
	private IDiagraphParser parser2;
	private DiaGraph internalModel; // abstract syntax of the concrete graphic
									// syntax
	private IDiaPlatformDelegateProvider platformProvider;
	private IDiaPointOfView pointOfView;
	private IDiaConcreteSyntax dsl;
	private DStyle dStyle;
	private DGraph dGraph;
	private IStyleHandler styleHandler;
	private IDiagraphRunner runner;

	private IDiagraphNotation notation;

	public DStyle getDStyle() {
		return dStyle;
	}

	public DGraph getDGraph() {
		return dGraph;
	}

	public List<DStatement> getDStatements() {
		return statements;
	}

	public DiaGraph getInternalModel() {
		return internalModel;
	}

	/*
	 * public void addDStatement_not_used_(DStatement pa) { statements.add(pa);
	 * }
	 */

	public void setStyleHandler_(IStyleHandler styleHandler) {
		this.styleHandler = styleHandler;
	}

	private void clog2(String mesg) {
		if (DParams.Parser_CONTAINMENT2_LOG)
			System.out.println(mesg);
	}

	public DGraph transform(DStyle style) { // FP120628 //FP120702
		if (LOG)
			clog("DAP-transform");
		internalModel.doTransform();
		internalModel.convertM2(style);
		if (LOG)
			internalModel.doLog();
		dGraph = internalModel.getRunner().getDGraph();// FP140520zz
		return dGraph;
	}

	public void checkAfterTransfo() {
		parser1.postTransform(dGraph);
	}

	public List<DGraphElement> getAllConcreteElements() {
		return dGraph.getFacade1().getAllConcreteElements();
	}


	public List<DGraphElement> getAllElements() {
		return dGraph.getFacade1().getAllElements();
	}



	public EClass getEClassMapping(DGraphElement elemt) {
		return dGraph.getFacade1().getEClassMapping(elemt);
	}

	public void prepareGeneration(String view) {
		String logstatements = "";
		parser1.disable();
		if (LOG)
			DTokenizer.simulateGeneration(internalModel);
		statements = DTokenizer.prepareGeneration(internalModel);
		for (DStatement statement : statements)
			logstatements += statement.toCsv() + "\n";
		runner.logStatements(view, logstatements);

		if (LOG)
			for (DStatement statement : statements)
				clog(statement.toCsv());
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);

	}

	public DiaParser getParser() {
		return parser1;
	}

	public IStyleHandler getStyleHandler() {
		return styleHandler;
	}

	public void setStyle(DStyle style) {
		internalModel.setStyle(style);
		dStyle = style;
	}

	public IDiaParser initParser(String view_, IProgressMonitor progressMonitor) {
		if (DParams.Parser_15_LOG)
			clog15("initParser for view: " + view_);
		parser1 = new DiaParser(domainModel, view_, runner, notation,
				platformProvider, dStyle);
		parser1.setPointOfView(pointOfView); // FP120926
		parser1.checkBeforeParsing(); // FP150325voirvoir
		return parser1;
	}

	public void setPointOfView(IDiaPointOfView pov) {// FP140615b //FP121124m
		// obsolete //FP120926
		this.pointOfView = pov;
		if (DParams.Parser_15_LOG)
			clog15("point of view: " + pointOfView.toString());
	}
/*
	public void setPointOfView_new_pb_(IDiaPointOfView pov, EPackage packag,
			List<EModelElement> modelElements, String view) {// FP140615b
																// //FP121124m
		// obsolete //FP120926
		this.pointOfView = pov;

		IDiagraphView cv1 = parser2
				.setPointOfView_(packag, modelElements, view);

		if (DParams.Parser_15_LOG)
			clog15("point of view: " + pointOfView.toString());
	}
*/

	public void endParse_2(List<EModelElement> modelElements, IProgressMonitor progressMonitor) {
		parser2.endParse_2(modelElements, progressMonitor);
	}

	public void parse_2(EPackage packag,List<EModelElement> modelElements, String view,
			IProgressMonitor progressMonitor) { // FP140505pppa
		if (DParams.Parser_15_LOG)
		    clog15("parse_2 "+packag.getName()+"."+view);
		if (LOG)
			clog("setPointOfView");

		parser2.startParse_2();
		IDiagraphView  cv1= parser2.setPointOfView_(packag,modelElements, view);

		if (LOG)
			clog("preParse");
		parser2.preParse(modelElements);

		if (LOG)
			clog("parsePov");
		parser2.parsePov();

		if (LOG)
			clog("parsePov");
		parser2.validate();//checkGeneric here
		if (LOG)
			clog("parse21");
		parser2.parse21NodesAndRelations(modelElements, progressMonitor);

		if (LOG)
			clog("parse22NodeAnnotations");
		parser2.parse22NodeAnnotations(modelElements, progressMonitor);

		if (LOG)
			clog("parse22LinkAnnotations");
		parser2.parse22LinkAnnotations(modelElements, progressMonitor);
		parser2.parse22LnkAnnotations(modelElements, progressMonitor);




		if (LOG)
			clog("postParse");
		parser2.postParse();



		if (DParams.Parser_15_LOG)
		    parser2.logAnnotations();
	//	String log = parser2.getParserLog_(view); //FP150318log
		//log += parser2.getLog()+"\n";

		if (DParams.Parser_15_LOG)
			clog15("------------\n" + parser2.getReport()
					+ "\n------------");
		internalModel = new DiaGraph(parser2, modelElements, view,
				resourceData);

		String reps = parser2.getReports();
		if (DParams.Parser_15_LOG)
			clog15("______________________________\nreports\n"+reps+"_____________________________\n");


	}



	private void clog15(String mesg) {
		if (DParams.Parser_15_LOG)
			System.out.println(mesg);
	}

	public DAnnotationParser(IDiaPlatformDelegateProvider platformProvider,
			IDiaConcreteSyntax dsl, EPackage domainModel,
			IDiagraphNotation notation, String[] resourceData) {

		this.resourceData = resourceData;
		this.dsl = dsl;
		this.domainModel = domainModel;
		this.platformProvider = platformProvider;
		this.runner = platformProvider.getRunner();
		this.parser2 = notation.getParser();
		this.notation = notation;
	}

	public DiaGraph parse_1(String view_, IProgressMonitor progressMonitor) {
		if (DParams.Parser_15_LOG)
			clog15("parse_1 " + domainModel.getName() + "." + view_);

		if (LOG) {
			clog("DG_PARSE_ALL_CLASSES");
			runner.getEcoreUtils().logAllClasses(parser1.getModelElements());
		}
		DAnnotation.checkInheritance(parser1.getModelElements(), view_);
		runner.getEcoreUtils().checkPendingReferences(
				parser1.getModelElements());
		DAnnotation.setReport("");
		DAnnotation.parse(runner, parser1, progressMonitor); // les povs sont
																// faits ici
		if (DParams.Parser_CONTAINMENT2_LOG)
			clog2("PARSE REPORT------------\n" + DAnnotation.getReport()
					+ "\n------------");
		internalModel = new DiaGraph(parser1, view_, dsl, runner,
				parser1.getDiagramElements(), parser1.getContainments(),parser1.getPointOfView(),
				resourceData, null);
		return internalModel;
	}

	public void cerror(String mesg) {
		runner.cerror(mesg);
	}
	/*
	 * public void parser1CheckBeforeParsing() { parser1.checkBeforeParsing_();
	 * }
	 */

	public boolean isNode(EClass claz) {
		return parser1.isNode(claz);
	}



	public boolean isCref(EReference ref) {
		return parser1.isCref(ref);
	}







}
