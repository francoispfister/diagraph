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
package org.isoe.diagraph.internal.m2.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EClass;

/**
 *
 * @author pfister
 *
 */
public class Stats {

	static final String[] ponc = { ":", ",", ";", "=", ".", "-", "/", "\\",
			"(", ")", " " };

	private  static HashMap keystat = new HashMap();
	private  static HashMap<String,List> langstat = new HashMap<String,List>();


	public static void clearKeyStats(){
		keystat = new HashMap();
		langstat = new HashMap<String,List>();
	}

	/*
	public static void printStats() {
	   printKeyStats();
	   printLangStats();
	}*/

	private static void printKeyStats() {
		Iterator iterator = keystat.keySet().iterator();
		while (iterator.hasNext()) {
			String k = (String) iterator.next();
			Integer q = (Integer) keystat.get(k);
			if (q == null)
				q = new Integer(0);
			System.out.println(k + "|" + q.intValue());
		}
		/*
		 * :|23 .|97 /|1 =|301
		 */
	}


	private static boolean isFiltered(List<String> filters, String key, String value){
		for (String filter : filters) {
			if (key.equals(filter) || value.contains(":"+filter)) //argumetns ??
				return true;
		}
		return false;

	}

	public static List<String> logStats(List<String> filters) {
		List<String> result = new ArrayList<String>();
		Iterator iterator = langstat.keySet().iterator();
		while (iterator.hasNext()) {
			String k = (String) iterator.next();
			List<String> v= langstat.get(k);
			if (v==null)
				v=new ArrayList<String>();
			for (String w : v) {
				if (isFiltered(filters, k,w))
					result.add(k + "|" + w);
				//System.out.println(k + "|" + w);
			}
		}
		return result;
	}

	static void kstat(String w) {
		for (String pon : ponc) {
			if (w.contains(pon)) {
				Integer ponq = (Integer) keystat.get(pon);
				if (ponq == null)
					ponq = new Integer(0);
				keystat.put(pon, new Integer(ponq.intValue() + 1));
				// System.out.println(pon);
			}
		}
	}



	public static void addStat(EClass eClass,String[]terms, String separator){
			String righth = terms[1]; // //[pov, studview]
	

			List<String> ss=langstat.get(terms[0]);
			if (ss==null){
				ss = new ArrayList<String>();
				langstat.put(terms[0], ss);
			}
		//	ss.add(terms[0]+separator+terms[1]+" ("+ eClass.getEPackage().eResource().getURI().toString()+":"+ eClass.getEPackage().getName()+"."+eClass.getName()+")");
			ss.add(separator+terms[1]+ " "+eClass.getEPackage().getName()+"."+eClass.getName());

	}

	public  static void addStat(EClass eClass, String key) {
		List<String> ss=langstat.get(key);
		if (ss==null){
			ss = new ArrayList<String>();
			langstat.put(key, ss);
		}
		ss.add(eClass.getEPackage().getName()+"."+eClass.getName());
	}




	public static void statKey(EClass eClass,String key){
		String[] keys=key.split(":");
		if (keys.length==2)
			 addStat(eClass, keys,":");
	    else
			 addStat(eClass, key);
	}



	public static void statValue(EClass eClass,String value){
		if (value.contains("/")){
			String[] values=value.split("/");
			addStat(eClass, values,"/");
		}
		if (value.contains(":")){
			String[] dp=value.split(":");
			addStat(eClass, dp,":");
		}
		if (value.contains(".")){
			String[] sp=value.split("\\.");
			addStat(eClass, sp,":");
		}
		if (!value.contains("/") && !value.contains(":") && !value.contains(".") ){
	        addStat(eClass, value);
		}
	}



	public static void statAnnotation(EClass eClass,String  k){
		String[] eq=k.split("=");
		String ak="";
		String av="";
		ak=eq[0];
		if (eq.length==2){
		  av=eq[1];
		}
		statKey(eClass, ak);
		statValue(eClass, av);
	}




}
