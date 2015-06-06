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
package org.isoe.diagraph.megamodelhelper.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.isoe.diagraph.megamodel.Dsml;
import org.isoe.diagraph.megamodel.Megamodel;
import org.isoe.diagraph.megamodel.MegamodelFactory;
import org.isoe.diagraph.megamodel.MegamodelPackage;
import org.isoe.diagraph.megamodelhelper.IMegaModelMan;
import org.isoe.diagraph.preferences.DiagraphPreferences;
import org.isoe.extensionpoint.diagen.IDiagraphAlphabet;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.deployer.MegamodelPersistence;
//import org.isoe.fwk.megamodel.handler.MegamodelHandler;
import org.isoe.diagraph.megamodelhelper.IMegamodelHandler;
import org.isoe.fwk.pathes.PathPreferences;

/**
 *
 * @author fpfister
 *
 */
import org.isoe.diagraph.controler.IDiagraphControler;  //FP140707refactored
public class MegaModelMan implements IMegaModelMan {

	private static final boolean LOG = DParams.Megamodel_LOG;
	private Megamodel megamodel;
	private IDiagraphControler controler;
	private IMegamodelHandler megamodelHandler;

	public MegaModelMan(IDiagraphControler owner) {
		this.controler = owner;
	}




	@Override
	public void saveUniverse() {
		// if (true)
		// return;
		if (megamodel == null) {
			controler.cerror("saving but megamodel is null");
			return;
		}
		if (LOG || DParams.MegaModelBuilderJOB_LOG ) {
			String dsls = "";
			List<Dsml> dsmls = megamodel.getDsmls();  //FP140622aa
			for (Dsml dsml : dsmls)
				dsls += dsml.getName() +( dsml.getNotations_().isEmpty()?" 0 view ":(" ["+dsml.getNotations_().size()+" views] "))+"; ";
			if (LOG)
			    clog("saved dsmls =" + dsls);
			if (DParams.MegaModelBuilderJOB_LOG)
				jobclog("saved dsmls =" + dsls);
		}

		if (LOG)
			clog("saving megamodel :" + PathPreferences.getPreferences().log());
		String upn = PathPreferences.getPreferences().getUniverseProjectName();
		String p = upn + "/"
				+ PathPreferences.getPreferences().getModelInUniverseFolder()
				+ "/" + DParams.UNIVERSE_MODEL_ROOT_NAME + "."
				+ MegamodelPackage.eNAME;
		URI uri = URI.createPlatformResourceURI(p, true);
		if (LOG)
			clog(uri.toString());
		MegamodelPersistence.save(uri, megamodel, false,controler);

	}

	private void jobclog(String mesg) {
		if (DParams.MegaModelBuilderJOB_LOG)
			System.out.println(mesg);
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	private boolean loadUniverse_() {
		boolean mexists = megamodel != null;
		String upn = PathPreferences.getPreferences().getUniverseProjectName();
		String p = upn + "/"
				+ PathPreferences.getPreferences().getModelInUniverseFolder()
				+ "/" + DParams.UNIVERSE_MODEL_ROOT_NAME + "."
				+ MegamodelPackage.eNAME;

		if (LOG)
			clog("loading megamodel from PlatformResource " + p);
		else if (DParams.UI_LOG)
			clogui("loading megamodel from PlatformResource " + p);

		URI uri = URI.createPlatformResourceURI(p, true);
		//String ruri = CommonPlugin.resolve(uri).toFileString();
		//File fil = new File(ruri);
		try {
			megamodel = MegamodelPersistence.load(uri,controler);
			if (DParams.MegaModelBuilderJOB_LOG)
				jobclog("loadUniverse " + uri.toString());
			return true;
		} catch (Exception e) {

			megamodel = null;
			controler.cerror("********************  loadUniverse: megamodel is null");
			megamodel = MegamodelPersistence.createUniverse();
			return false;
		}
	}

	private void clogui(String mesg) {
		if (DParams.UI_LOG)
			 System.out.println(mesg);
	}




	@Override
	public Megamodel getMegamodel(boolean headless) {
		boolean genLanguageNo = false; //FP140622voiraaabbb
		boolean refreshOnlyYes = true;
		if (megamodel == null) {
			boolean universeLoaded = loadUniverse_();  //FP140622aa
			if (!universeLoaded) {
				if (LOG)
					clog("rebuild all megamodel");

				String[] args = new String[2];
				args[0] = IDiagraphAlphabet.MEGAMODEL_CHECK_STATUS;
				args[1] = "*"; // FP140605

				String result = controler.invokeMegamodelJob(null,headless, args,// IDiagraphAlphabet.MEGAMODEL_CHECK_STATUS
						// +
						// " *",
						genLanguageNo, refreshOnlyYes); // FP130903
			}
		}

		boolean isEmpty = megamodel.getDsmls().isEmpty();
		if (isEmpty)
			if (LOG)
				clog("megamodel is empty");
		return megamodel;
	}


	@Override
	public Megamodel getMegamodel() {
		return megamodel; //FP140622voiraaabbb
	}


	@Override
	public Megamodel getMegamodel(String name, boolean recreate_) {
		boolean mexists = megamodel != null;
		if (!mexists) {
			megamodel = MegamodelFactory.eINSTANCE.createMegamodel();
			megamodel.setName(name);
			if (LOG)
				clog("createMegamodel (" + name + ")"
						+ (recreate_ ? " recreate" : ""));
		}
		return megamodel;
	}

	@Override
	public void clearMegamodel() { //FP140622voiraaabbb
		if (LOG)
			clog("clearMegamodel");
		if (DParams.MegaModelBuilderJOB_LOG)
		 jobclog("********  clearMegamodel");
		Megamodel m = getMegamodel(false);  //FP140622aa
		if (m != null){
			m.getDsmls().clear();
			if (DParams.MegaModelBuilderJOB_LOG)
				jobclog("clearMegamodel");
			//saveUniverse();
			controler.refreshMegamodelProject();
		}
	}


	private Dsml findInDsml(String name) {   //FP140622aa
		Megamodel m = getMegamodel(false);
		for (Dsml dsml : m.getDsmls()) {
			//if (LOG)
			//	clog("findDsml " + dsml.getName());
			if (dsml.getName().equals(name)){
				//if (DParams.MegaModelBuilderJOB_LOG)
			//		jobclog("foundInDsml "+name);
				return dsml;
			}
		}
		return null;
	}

	// FP140619voiraaa
	// @Override
	private String[] prepare_old_nu_(String langs) {
		if (LOG)
			clog("prepare " + langs);
		saveUniverse(); // FP140619
		langs = langs.trim();
		String[] result;
		List<Dsml> toremove_ = new ArrayList<Dsml>();
		List<String> notfounds = new ArrayList<String>();
		if (langs.startsWith("(") && langs.endsWith(")"))
			langs = langs.substring(1, langs.length() - 1);
		langs = langs.trim();
		if (langs.contains(" "))
			result = langs.split(("\\s+"));
		else if (langs.contains(","))
			result = langs.split((","));
		else {
			result = new String[1];
			result[0] = langs;
		}
		for (String ds : result) {
			Dsml f = findInDsml(ds);
			if (f != null)
				toremove_.add(f);
			else {
				controler.cerror("language " + ds
						+ " not found in the megamodel");
				notfounds.add(ds);
			}
		}
		for (Dsml dsml : toremove_) {
			// m.getDsmls().remove(dsml);
			if (LOG)
				clog("---: " + dsml.getName());
		}
		for (String notfound : notfounds)
			for (int i = 0; i < result.length; i++)
				if (result[i].equals(notfound))
					result[i] = null;
		int resultsize = 0;
		for (int i = 0; i < result.length; i++)
			if (result[i] != null)
				resultsize++;
		String[] found = new String[resultsize];
		int k = 0;
		for (int i = 0; i < result.length; i++)
			if (result[i] != null)
				found[k++] = result[i];
		return found;
	}

	@Override
	public void close(String language, String operation, boolean save) {
		if (LOG)
			clog("close " + language + " "+operation);
		if (DParams.MegaModelBuilderJOB_LOG)
			jobclog("close " + language + " "+operation);
		if (save)
		   saveUniverse(); // FP140622aaa
	}

	@Override
	public String[] prepare(String langs, String operation) {
		if (LOG)
			clog("prepare " + langs + " "+operation);

		if (DParams.MegaModelBuilderJOB_LOG)
			jobclog("prepare " + langs + " "+operation);

		saveUniverse(); // FP140619
		langs = langs.trim();
		String[] result;

		if (langs.startsWith("(") && langs.endsWith(")"))
			langs = langs.substring(1, langs.length() - 1);
		langs = langs.trim();
		if (langs.contains(" "))
			result = langs.split(("\\s+"));
		else if (langs.contains(","))
			result = langs.split((","));
		else {
			result = new String[1];
			result[0] = langs;
		}

		String[] dsmlsInProject = getInProjects();

		for (String dsmlsIp: dsmlsInProject)
			if (findInDsml(dsmlsIp)==null)
				;//controler.cerror("not present in dsmls: "+dsmlsIp);

	    for (String arg : result) {
	    	boolean found=false;
	    	for (String dsml : dsmlsInProject) {
				if (dsml.equals(arg)){
					found=true;
					break;
				}
			}
	    	if (!found)
	    		throw new RuntimeException("language not found in projects: "+arg);
		}
		return result;
	}

	public String[] getInProjects() { //FP140620
		if (LOG)
			clog("getInProjects ");
		List<IProject> projects = controler.getDsmlProjects();
		String[] result = new String[projects.size()];
		for (int i = 0; i < projects.size(); i++) {
			String langName = projects.get(i).getName();
			langName = langName.substring(langName.lastIndexOf(".") + 1);
			result[i] = langName;
		}
		return result;
	}


	@Override
	public List<Dsml> getDsmls() {
		Megamodel m = getMegamodel(true);
		List<Dsml> dsmls = m.getDsmls();
		return dsmls;
	}


	public String[] getDsmlNames() {   //FP140622aa
		Megamodel m = getMegamodel(false);
		List<Dsml> dsmls = m.getDsmls();
		String[] result = new String[dsmls.size()];
		for (int i=0;i< dsmls.size();i++ ) {
			result[i] = dsmls.get(i).getName();
		}
		if (DParams.MegaModelBuilderJOB_LOG)
			jobclog("getInDsmlsl "+result);

		return result;
	}


	@Override
	public void setHandler(IMegamodelHandler megamodelHandler) {
		this.megamodelHandler = megamodelHandler;
	}

	@Override
	public Dsml addDsml(String projectName, EPackage model) {
		if (LOG)
			clog("addDsml " + projectName + " - " + model.getName());
		String universeNameSrc = DiagraphPreferences
				.getStringPreference(DParams.KEY_UNIVERSE_NAME);// "_megamodel";
		boolean fromBundle = false;
		Dsml dsml = megamodelHandler.addDsml(getMegamodel(false),
				universeNameSrc, model, projectName, fromBundle);
		// Dsml dsml =
		// MegamodelHandler.getInstance().addDsml(getMegamodel(false),
		// universeNameSrc, model, projectName, fromBundle);
		return dsml;
	}










}
