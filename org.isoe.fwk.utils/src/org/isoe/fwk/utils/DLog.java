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
package org.isoe.fwk.utils;

/**
 * 
 * @author pfister
 *
 */
public class DLog {
	
	private static String warnings = "";
	private static String infos = "";
	private static String errors = "";
	
	public static void addWarning(String warning) {
	    if (!warnings.contains( warning ))
		    warnings += warning + "\n";
	}

	public static String getWarnings() {
		return "\n"+warnings;
	}

	public static void clearWarnings() {
		warnings="";
	}

	public static String getInfos() {
		return infos;
	}

	public static void addInfo(String info) {
		infos += info + "\n";
	}
	
	public static void clearInfos() {
		infos = "";
	}

	public static String getErrors() {
		return errors;
	}
	
	public static void clearErrors() {
		errors="";
	}

	public static void addError(String error) {
		errors += error + "\n";;
	}
}
