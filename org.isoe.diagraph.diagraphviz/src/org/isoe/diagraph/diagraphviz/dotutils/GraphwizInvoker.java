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
package org.isoe.diagraph.diagraphviz.dotutils;

import java.io.File;
import java.io.IOException;

/**
 * 
 * @author pfister
 * 
 */
public class GraphwizInvoker {

	public static void toDot(String filePath, String format)
			throws IOException {
		File file = new File(filePath);
		String dotPath = file.getAbsolutePath();
		String imgPath = dotPath + "." + format;
		// String charset1= "charset=UTF-8";
		String charset2 = "charset=latin1";
		String dotCommand = "dot -T" + format + " " + dotPath + " -o "
				+ imgPath + " -G" + charset2;
		System.out.println("executing " + dotCommand);
		Process p = Runtime.getRuntime().exec(dotCommand);
		try {
			p.waitFor();
		} catch (Exception e) {
			System.out.println("error dotifying "+e.toString()); //FP140109
		}

	}

}

