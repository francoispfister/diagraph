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
package org.isoe.diagraph.runner;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.diastyle.DStyle;
import org.isoe.diagraph.diastyle.helpers.IStyleHandler;
import org.isoe.extensionpoint.ecoreutils.IEcoreUtils;

/**
 *
 * @author fpfister
 *
 */
import org.isoe.diagraph.controler.IDiagraphControler;  //FP140707refactored
public interface IDiagraphRunner {
	//org.isoe.diagraph.runner.IDiagraphRunner

		IEcoreUtils getEcoreUtils();

		void setRunParameters(Object runParameters);

		Object getDsl();

		void setInternalModel(Object diaGraph);

		boolean isListLayout(String eclassName);

		boolean generatePlugins();

		void setDGraph(DGraph dgraph);

		DGraph getDGraph();

		DStyle getDStyle();

		void setDStyle(DStyle dstyle);

		List<String> getLogDiagraph();

		String getParseError();

		IStyleHandler getStyleHandler();

		void setStyleHandler(IStyleHandler styleHandler);

		Object getCompartment(String nodename);

		void setParseError(String error);

		int getPhase();

		int getNextNodeId_(String name);

		int getNextEdgeId_(String name);

		void clearIdGenerator();

		List<String> getLog();

		void logBeforeExec(String prj, List<String> keywordFilters);

		void logAfterExec(String log, List<String> keywordFilters);

		String execDiagraphTransformation(GenModel genmodel, String layer,
				boolean atRuntime, String plugin, boolean rcp,
				IProgressMonitor progressMonitor);

		String getMetadata(String id);

		void setEcoreUtils(IEcoreUtils ecoreUtilService);

		void execDiagraphTransformation(String diagraphDefaultViewLitteral,
				String string, boolean validate, boolean refreshArtifactsOnly, IProgressMonitor progressMonitor);

		String manageFoldersAndGenerateAllPointsOfViewCommon(String view,
				boolean atRuntime, boolean genFromDiagraph, GenModel genmodel,
				String projectName, String absoluteLocation,
				boolean deleteOldJavaSrc, boolean changeTargetFolder, boolean rcp,
				boolean validate, boolean refreshArtifactsOnly,
				IProgressMonitor progressMonitor);

		void logStatements(String view, String log);

		boolean mustThrowExceptions();

		void cerror(String mesg);

		void cinfo(String mesg);

		void cwarn(String mesg);

		boolean isLayouting();

		//void logGeneration(String string);

		//void creport(List<String> log);

		//IDiagraphControler getControler(); //FP140701a


}
