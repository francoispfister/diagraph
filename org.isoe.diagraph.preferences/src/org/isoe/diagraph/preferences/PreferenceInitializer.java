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
package org.isoe.diagraph.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.isoe.fwk.core.DParams;


/**
 * 
 * @author fpfister
 *
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();

		store.setDefault(DParams.KEY_ANOTHER_FOLDER, DParams.DEFAULT_ANOTHER_FOLDER);
		store.setDefault(DParams.KEY_SAVE_OLD, DParams.DEFAULT_SAVE_OLD);
		store.setDefault(DParams.KEY_DELETE_OLD_JAVA_SRC_, DParams.DEFAULT_DELETE_OLD_JAVA_SRC);
		store.setDefault(DParams.KEY_LAYOUT_AT_STARTUP, DParams.DEFAULT_LAYOUT_AT_STARTUP);
		store.setDefault(DParams.KEY_FILTER_TEST_PROJECTS, DParams.DEFAULT_FILTER_TEST_PROJECTS);

		store.setDefault(DParams.KEY_TARGET_MODEL_DIR, DParams.DEFAULT_TARGET_MODEL_DIR);
		store.setDefault(DParams.KEY_SAVED_MODEL_DIR, DParams.DEFAULT_SAVED_MODEL_DIR);
		store.setDefault(DParams.KEY_TEAM_NAMESPACE, DParams.DEFAULT_TEAM_NAMESPACE);

		store.setDefault(DParams.KEY_INSTANCES_NAME, DParams.DEFAULT_INSTANCES_NAME);

		store.setDefault(DParams.KEY_UNIVERSE_NAME, DParams.DEFAULT_UNIVERSE_NAME);
		store.setDefault(DParams.KEY_UNIVERSE_FOLDER, DParams.DEFAULT_UNIVERSE_FOLDER_);

		store.setDefault(DParams.KEY_ALLOW_PATTERN_BASED_REFACTORING, DParams.DEFAULT_ALLOW_PATTERN_BASED_REFACTORING);

		/*
		store.setDefault(DParams.KEY_DIAGRAPH_LAYOUT_VERTICAL_OFFSET, DParams.DEFAULT_DIAGRAPH_LAYOUT_VERTICAL_OFFSET);
		store.setDefault(DParams.KEY_DIAGRAPH_LAYOUT_LEFT_OFFSET, DParams.DEFAULT_DIAGRAPH_LAYOUT_LEFT_OFFSET);
		store.setDefault(DParams.KEY_DIAGRAPH_LAYOUT_WIDTH_OFFSET, DParams.DEFAULT_DIAGRAPH_LAYOUT_WIDTH_OFFSET);
	*/

		store.setDefault(DParams.KEY_REFACTOR_DUPLICATE_REFERENCES, DParams.DEFAULT_REFACTOR_DUPLICATE_REFERENCES);
		store.setDefault(DParams.KEY_REFACTOR_POOR_REFERENCES, DParams.DEFAULT_REFACTOR_POOR_REFERENCES);
		store.setDefault(DParams.KEY_REFACTOR_NAMES, DParams.DEFAULT_REFACTOR_NAMES);

		store.setDefault(DParams.KEY_SAVE_EMF_TRANSFO, DParams.DEFAULT_SAVE_EMF_TRANSFO);
		store.setDefault(DParams.KEY_SAVE_GENERATED_ANNOTATIONS_IF_DIFFERENT_TARGET, DParams.DEFAULT_SAVE_GENERATED_ANNOTATIONS_IF_DIFFERENT_TARGET);
		//store.setDefault(DParams.KEY_SAVE_M2_, DParams.DEFAULT_SAVE_M2_);
		//store.setDefault(DParams.KEY_CHANGE_LANGUAGE_VERSION, DParams.DEFAULT_CHANGE_LANGUAGE_VERSION);

		store.setDefault(DParams.KEY_ADD_MISSING_ANNOTATIONS, DParams.DEFAULT_ADD_MISSING_ANNOTATIONS);
		store.setDefault(DParams.KEY_DIAGRAPH_AUTO_GENERATION, DParams.DEFAULT_DIAGRAPH_AUTO_GENERATION);
		store.setDefault(DParams.KEY_EMFGMF_GENERATION_, DParams.DEFAULT_EMFGMF_GENERATION_);

	}

}
