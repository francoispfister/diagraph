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
package org.isoe.diagraph.layerhelper.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.isoe.diagraph.layerhelper.ILayerHelper;
import org.isoe.diagraph.views.ViewConstants;
import org.isoe.fwk.core.DParams;

/**
 *
 * @author fpfister
 *
 */
import org.isoe.diagraph.controler.IDiagraphControler;  //FP140707refactored
public class LayerHelper implements ILayerHelper {

	private static final boolean LOG = DParams.LayerHelper_LOG;
	private IDiagraphControler controler;
	private String currentView;
	private Map<String, String> viewReg = new HashMap<String, String>();

	public LayerHelper(IDiagraphControler controler) {
		this.controler = controler;
	}

	@Override
	public String getCurrentView_() {
		return currentView;
	}

	@Override
	public void setViews(Object sender, int senderId, String language,
			String[] views, String view) {
		if (LOG) {
			String log = "";
			for (String vid : views)
				log += vid + ";";
			String mesg = language + " setViews (" + senderId + ") "
					+ sender.getClass().getSimpleName() + " - " + log
					+ " current=" + view;
			clog(mesg);
		}
		//String[] layers = new String[views.length + 1];
		String[] layers = new String[views.length]; //FP140618
		int i = 0;
		for (String v : views)
			layers[i++] = v;
		//layers[i++] = ViewConstants.DIAGRAPH_ALL_VIEW_LITTERAL_;
		controler.setLayerItems(layers);
		if (!view.equals(controler.getLayer())) {
			controler.setLayer(view);
			if (LOG)
				clog("syntaxLayer.changeText=" + view);
		} else if (LOG)
			clog("syntaxLayer.notchangeText=" + view);
		currentView = view;
		registerCurrentView(language, view, 556);// FP140611b
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	@Override
	public void registerCurrentView(String languageName, String view,
			int senderId) {
		viewReg.put(languageName, view);
		if (LOG)
			clog("RCV=" + languageName + "." + view);
	}

	@Override
	public Map<String, String> getLayerMap() {
		return viewReg;
	}

	@Override
	public String getRegisterdView(String language) {
		if (LOG) {
			String logk = "";
			Set<String> ks = viewReg.keySet();
			for (String k : ks)
				logk += k + "=" + viewReg.get(k) + " ; ";
			clog("viewReg=" + logk);
		}
		String view = viewReg.get(language);
		if (view == null)
			view = ViewConstants.DIAGRAPH_DEFAULT;
		return view;
	}

}
