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
package org.isoe.diagraph.osem.constants;

     // org.isoe.diagraph.preferences

/**
 *
 * @author fpfister
 *
 */
public interface OsemConfiguration {
	//FP140321z
	// experimental
	//org.isoe.diagraph.osem.constants.OsemConfiguration.DSL1_M1_EXAMPLES[0]

	static final String OSEM_ID = "org.isoe.fwk.project.deployer";
	static final String OSEM_ZIPS = "zips/";
	static final String OSEM_M1 = "m1/";
	static final String OSEM_M2 = "m2/";
	static final String OSEM_ZIP = ".zip";

	static final String[] DSL0_M1_EXAMPLES = { "lang.m2.kerm.foo.samplerun",
	"lang.m2.foo.samplerun" };

    static final String[] DSL0_M2_EXAMPLES_WIZ = { "lang.m2.kerm.ext.foo",
	"lang.m2.kerm.osem.foo", "lang.m2.foo", "lang.m2.osem.foo", };

    static final String[] DSL0_M2_EXAMPLES_COP = { "lang.m2.kerm.ext.foo",
"lang.m2.kerm.osem.foo", "lang.m2.osem.foo", };


	static final String[] DSL1_M1_EXAMPLES = { "lang.m2.kerm.kfsm.samplerun",
			"lang.m2.kfsm.samplerun" };

	static final String[] DSL1_M2_EXAMPLES_WIZ = { "lang.m2.kerm.ext.kfsm",
			"lang.m2.kerm.osem.kfsm", "lang.m2.kfsm", "lang.m2.osem.kfsm", };

	static final String[] DSL1_M2_EXAMPLES_COP = { "lang.m2.kerm.ext.kfsm",
		"lang.m2.kerm.osem.kfsm", "lang.m2.osem.kfsm", };


	static final String[] DSL2_M1_EXAMPLES = { "lang.m2.kerm.simpleworld.samplerun",
	"lang.m2.simpleworld.samplerun" };

    static final String[] DSL2_M2_EXAMPLES_WIZ = { "lang.m2.kerm.ext.simpleworld",
	"lang.m2.kerm.osem.simpleworld", "lang.m2.simpleworld", "lang.m2.osem.simpleworld", };

    static final String[] DSL2_M2_EXAMPLES_COP = { "lang.m2.kerm.ext.simpleworld",
    "lang.m2.kerm.osem.simpleworld", "lang.m2.osem.simpleworld", };


	static final String DSL0_FOLDER_FOO = "foo/";
	static final String DSL1_FOLDER_KSMF = "kfsm/";
	static final String DSL2_FOLDER_SW = "simpleworld/";

	static String KERMETA_CONFIGURATIONTYPE_ID = "org.kermeta.kp.launcher.eclipse.internal.KermetaLauncherID";


	static final String[] DSL0 = { "foo", KERMETA_CONFIGURATIONTYPE_ID,
		DSL0_M1_EXAMPLES[0] };

	static final String[] DSL0_DUMMY = { "thedsl", "foo.bar.TheDSLLauncherID",
	"foo.launch" };

	static final String[] DSL1 = { "kfsm", KERMETA_CONFIGURATIONTYPE_ID,
			DSL1_M1_EXAMPLES[0] };

	static final String[] DSL2 = { "simpleworld", KERMETA_CONFIGURATIONTYPE_ID,
		DSL2_M1_EXAMPLES[0] };


	static final String[][] EXEC_MAP = { DSL0, DSL1, DSL2 };

}
