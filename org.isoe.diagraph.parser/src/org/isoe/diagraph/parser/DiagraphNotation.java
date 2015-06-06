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
package org.isoe.diagraph.parser;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.isoe.diagraph.parser.api.IDiagraphNotation;
import org.isoe.diagraph.parser.api.IDiagraphParser;
import org.isoe.diagraph.parser.api.IDiagraphView;
import org.isoe.extensionpoint.diagraph.generator.IDiagraphGen;  //FP140707_b_refactored
import org.isoe.extensionpoint.ecoreutils.IEcoreUtils;
import org.isoe.extensionpoint.rules.ISyntaxRules;
import org.isoe.extensionpoint.rules.SyntaxRulesConnector;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.core.IDiagraphRuler;
/**
 *
 * @author fpfister
 *
 */
//FP140707_c_refactored
public class DiagraphNotation implements IDiagraphNotation, IDiagraphRuler {
//abstract //FP150602
	private List<IDiagraphView> diagraphviews = new ArrayList<IDiagraphView>();
	private IDiagraphParser parser;
	private IDiagraphView currentView;
	private IEcoreUtils ecoreUtils;
	private ISyntaxRules syntaxRules;
	private IDiagraphGen generator;

	//FP140509
	@Override
	public ISyntaxRules getSyntaxRules() {
		if (syntaxRules == null) {
			syntaxRules = new SyntaxRulesConnector().getService();
			syntaxRules.setParser(this);
		}
		return syntaxRules;
	}


	@Override
	public EPackage getDomainModel() {
		return getCurrentView().getDomainModel();
	}


	@Override
	public void addView(IDiagraphView diagraphview) {
		this.diagraphviews.add(diagraphview);
		((DiagraphView)diagraphview).setNotation(this);
	}

	public DiagraphNotation(IDiagraphGen generator) {
		super();
		this.generator = generator;
		//DiagraphView diagraphview = new DiagraphView();
		parser = DiagraphAnnotationParser.getInstance(this); //FP140507
		//((DiagraphView)diagraphview).setParser(parser2);
	}

	@Override
	public IDiagraphParser getParser() {
		return parser;
	}

	@Override
	public List<IDiagraphView> getViews() {
		return diagraphviews;
	}

	@Override
	public void initView(EPackage packag, String view){ //FP150325
		diagraphviews = new ArrayList<IDiagraphView>();
		currentView = null;
		parser.initView(packag,view);
		generator.initView(packag,view);
	}

	@Override
	public IDiagraphView getView_(String viewId){ //FP140507
		IDiagraphView result = null;
		List<IDiagraphView> views = getViews();
		for (IDiagraphView dview : views)
			if (dview.getViewId().equals(viewId)){
				result = dview;
				if (DParams.Parser_CONTAINMENT2_LOG)
					clog2("existing view "+viewId);
			}
		if (result == null){
			result = new DiagraphView();
			result.setViewId(viewId);
			((DiagraphView)result).setNotation(this);
			views.add(result);
			if (DParams.Parser_CONTAINMENT2_LOG)
				clog2("creating view "+viewId);
		}
		return result;
	}

	private void clog2(String mesg) {
		if (DParams.Parser_CONTAINMENT2_LOG)
			System.out.println(mesg);
	}

	@Override
	public IDiagraphView getCurrentView() {
		if (currentView == null)
			throw new RuntimeException("no current view !!!");
		return currentView;
	}

	@Override
	public void setCurrentView_(IDiagraphView view) {
		this.currentView = view;
	}

	@Override
	public void setEcoreUtils(IEcoreUtils ecoreUtils) {
		this.ecoreUtils = ecoreUtils;
	}

	@Override
	public IEcoreUtils getEcoreUtils() {
		return ecoreUtils;
	}

	@Override
	public void cerror(String mesg) {
		generator.cerror(mesg);
	}


	@Override
	public void creport(List<String> log) {
		generator.creport(log);
	}

	@Override
	public boolean isNodeInstanciable(EClass c) {
		return parser.isNodeInstanciable(c);
	}

	@Override
	public boolean isLinkInstanciable(EClass c) {
		return parser.isLinkInstanciable(c);
	}

	@Override
	public boolean isView(EClass c) {
		return parser.isCurrentView(c);
	}

	@Override
	public boolean isNode(EClass c) {
		return parser.isNode(c);
	}

	@Override
	public boolean isLink(EClass c) {
		return parser.isLink(c);
	}


	@Override
	public String getViewId() {
		return parser.getCurrentView();
	}




}
