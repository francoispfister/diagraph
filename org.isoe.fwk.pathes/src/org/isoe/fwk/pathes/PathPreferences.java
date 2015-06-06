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
package org.isoe.fwk.pathes;

import org.isoe.diagraph.preferences.DiagraphPreferences;
import org.isoe.fwk.core.DParams;
import  org.eclipse.jface.preference.FieldEditorPreferencePage;


/**
 *
 * @author "François.Pfister francois.pfister@gmail.com"
 *
 */
public class PathPreferences implements IPathPreferences {

	private static PathPreferences instance;
	//private String userName;
	//private String teamName;
	//private String teamNameSpace;

	private PathPreferences() {
		super();
	}

	public static PathPreferences getPreferences() {
		if (instance == null)
			instance = new PathPreferences();
		return instance;
	}

	@Override
	public String getInstanceRepositoryPath() {
		return "/" + getInstanceRepositoryLocation() + "/"
				+ DParams.INSTANCE_FOLDER;
	}

	@Override
	public String getInstanceRepositoryLocation() {
		return getInstanceRoot() + "_" + DParams.MODELS_FOLDER;
	}

	@Override
	public String getUniverseFolder() {
		return DiagraphPreferences
				.getStringPreference(DParams.KEY_UNIVERSE_FOLDER);
	}

	@Override
	public String getModelInUniverseFolder() {//FP130903
		//return "src/" + getUniverseFolder();
		return "src";
	}

	@Override
	public String getMegaModelName() {
		return getInstanceRoot();
	}

	@Override
	public String getInstanceRoot() {
		return getTeamNamespace()
				+ "."
				+ DiagraphPreferences
						.getStringPreference(DParams.KEY_INSTANCES_NAME);
	}


	@Override
	public String getMegamodelInstanceFolder() {
		return getTeamNamespace() + "."+DParams.MODEL_FOLDER;
	}


	@Override
	public String getUniverseProjectName() {
		String result = DiagraphPreferences
				.getStringPreference(DParams.KEY_UNIVERSE_NAME);
		return result;
	}

	@Override
	public String log() {
		String result = "\n";
		result += "                  getInstanceRepositoryLocation()="
				+ getInstanceRepositoryLocation() + "\n";
		result += "                      getInstanceRepositoryPath()="
				+ getInstanceRepositoryPath() + "\n";
		result += "                                getInstanceRoot()="
				+ getInstanceRoot() + "\n";
		result += "                              getUniverseFolder()="
				+ getUniverseFolder() + "\n";
		result += "                       getModelInUniverseFolder()="
				+ getModelInUniverseFolder() + "\n";
		result += "                               getMegaModelName()="
				+ getMegaModelName() + "\n";
		result += "                                getTeamNamespace()="
				+ getTeamNamespace() + "\n";
		result += "                     getMegamodelInstanceFolder()="
				+ getMegamodelInstanceFolder() + "\n";

		result += "                                getInitialState()="
				+ getConnectState() + "\n";

		String r = DiagraphPreferences
				.getStringPreference(DParams.KEY_UNIVERSE_NAME);
		result += "                         getUniverseProjectName()="
				+ getUniverseProjectName() + "\n";
		result += "   getStringPreference(DParams.KEY_UNIVERSE_NAME)=" + r
				+ "\n";
		result += "getStringPreference(DParams.KEY_UNIVERSE_FOLDER)="
				+ DiagraphPreferences
						.getStringPreference(DParams.KEY_UNIVERSE_FOLDER)
				+ "\n";
		result += "                 DParams.UNIVERSE_MODEL_ROOT_NAME="
				+ DParams.UNIVERSE_MODEL_ROOT_NAME + "\n";
		return result;
	}

	@Override
	public String getTeamNamespace() {
		return DiagraphPreferences.getTeamNamespace();
	}




	@Override
	public String getConnectState() {
		return DiagraphPreferences.getConnectState();
	}



	//org.isoe.fwk.pathes.PathPreferences getPreferences().getTeamNamespace()


}
