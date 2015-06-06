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
package org.isoe.diagraph.lang;

public class GrammarTest {



	public String getDiagraphKeywords(String[]tokens){

		String r="{";
		for (String tok : tokens) {
			r+=tok+", ";
		}
		r=r.substring(0,r.length()-2);
		r+="}";
		return r;
	}


	public static void main(String[]a){
		GrammarTest gt = new GrammarTest();

		System.out.println(gt.getDiagraphKeywords(DiagraphKeywords.VALID_TOKENS));
		System.out.println();
		System.out.println(gt.getDiagraphKeywords(DiagraphKeywords.DIASTYLE_TOKENS));
	}

}
