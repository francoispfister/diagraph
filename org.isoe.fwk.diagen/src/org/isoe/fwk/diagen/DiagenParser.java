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
package org.isoe.fwk.diagen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EPackage;
import org.isoe.extensionpoint.diagen.IDiagenAlphabet;
import org.isoe.fwk.core.DParams;

/**
 *
 * @author fpfister megamodel annotation parser
 *
 */
public class DiagenParser implements IDiagenAlphabet{

	private static final boolean LOG = DParams.DiagenParser_LOG;
	private static final boolean THROW_EXCEPTION = false;
	private Map<String, Map.Entry<String, String>> diagenProperties;

	public boolean isValidKeyword(String keyw) {
		for (String kw : DIAGEN_KW)
			if (kw.equals(keyw))
				return true;
		return false;
	}

	public void parseProto(EPackage pakage) {
		diagenProperties = new HashMap<String, Map.Entry<String, String>>();
		EAnnotation annotation = pakage.getEAnnotation(DIAGEN_ANNOT);
		if (annotation != null)
			for (Map.Entry<String, String> entry : annotation.getDetails()) {
				String[] ks = entry.getKey().split("=");
				if (!isValidKeyword(ks[0])) {
					if (LOG)
						clog("invalid keyword:" + ks[0]);
					if (THROW_EXCEPTION)
						throw new RuntimeException("invalid keyword:" + ks[0]);
				}
				for (String kwd : DIAGEN_KW) {
					if (ks[0].equals(kwd)) {
						diagenProperties.put(kwd, entry);
						if (LOG)
							clog("keyword:" + kwd + "=" + entry);
					}
				}
			}
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);

	}

	public List<String> getDiagenProperties(String k) {
		Map.Entry<String, String> entr = diagenProperties.get(k);
		if (entr == null)
			return null;
		List<String> result = new ArrayList<String>();
		if (entr != null) {
			result.add(entr.getKey());
			String v = entr.getValue();
			if (v != null && !v.isEmpty())
				for (String vl : v.split("\\n"))
					result.add(vl.trim());
		}
		return result;

	}

	public String getPropKnownAs() {
		String result = "";
		List<String> par = getDiagenProperties(KW_AKA);
		if (par == null)
			return null;
		String[] p = par.get(0).split("=");
		result += p[1];
		return result;
	}

	public String getPropParent(boolean left) {
		String result = "";
		List<String> par = null;
		if (left)
			par = getDiagenProperties(KW_LEFT_PARENT);
		else
			par = getDiagenProperties(KW_RIGHT_PARENT);
		if (par == null)
			return null;
		String[] p = par.get(0).split("=");
		result += p[1];
		return result;
	}

	public String getPropParentDetail(boolean left) {
		String result = "";
		List<String> par = null;
		if (left)
			par = getDiagenProperties(KW_LEFT_PARENT);
		else
			par = getDiagenProperties(KW_RIGHT_PARENT);
		if (par == null)
			return null;
		for (int i = 1; i < par.size(); i++) {
			result += par.get(i);
			if (i < par.size() - 1)
				result += "\r\n";
		}
		return result;
	}

	public List<String> getPropParentDetails(boolean left) {
		List<String> result = new ArrayList<String>();
		List<String> par_ = null;
		if (left)
			par_ = getDiagenProperties(KW_LEFT_PARENT);
		else
			par_ = getDiagenProperties(KW_RIGHT_PARENT);
		if (par_!=null)
		  for (int i = 1; i < par_.size(); i++)
			result.add(par_.get(i));
		return result;
	}

	public String getPropParentAndDetails(boolean left) {// // FP130920
		String result = "";
		List<String> par = null;
		try {
			if (left)
				par = getDiagenProperties(KW_LEFT_PARENT);
			else
				par = getDiagenProperties(KW_RIGHT_PARENT);
			if (par == null)
				return null;
			String[] p = par.get(0).split("=");
			result += p[1];
			if (par.size() > 1)
				result += "\r\n";
			for (int i = 1; i < par.size(); i++) {
				result += par.get(i);
				if (i < par.size() - 1)
					result += "\r\n";
			}
			return result;
		} catch (Exception e) {
			return null; // FP130920
		}
	}

	public String getPropContextDetail() {
		String result = "";
		List<String> par = getDiagenProperties(KW_CONTEXT);
		if (par == null)
			return null;
		for (int i = 1; i < par.size(); i++) {
			result += par.get(i);
			if (i < par.size() - 1)
				result += "\r\n";
		}
		return result;
	}

	public List<String> getPropContextDetails() {
		List<String> result = new ArrayList<String>();
		List<String> par_ = getDiagenProperties(KW_CONTEXT);
		if (par_ != null)
		for (int i = 1; i < par_.size(); i++)
			result.add(par_.get(i));
		return result;
	}

	public String getPropContextAndDetailz() {
		String result = "";
		List<String> par = getDiagenProperties(KW_CONTEXT);
		if (par == null)
			return null;
		String[] p = par.get(0).split("=");
		result += p[1];
		if (par.size() > 1)
			result += "\r\n";
		for (int i = 1; i < par.size(); i++) {
			result += par.get(i);
			if (i < par.size() - 1)
				result += "\r\n";
		}
		return result;
	}

	public String getPropOrigin() {
		String result = "";
		List<String> par = getDiagenProperties(KW_ORIGIN);
		if (par == null)
			return null;
		String[] p = par.get(0).split("=");
		result += p[1];
		return result;
	}

	public String getProperties(String keyword) {// FP130830req
		String result = "";
		List<String> par = getDiagenProperties(keyword);
		if (par == null)
			return null;
		String[] p = par.get(0).split("=");
		result += p[1];
		return result;
	}

	private String listAsString(List<String> l) {
		String result = "";
		if (l == null)
			return "null";
		for (int i = 1; i < l.size(); i++) {
			result += l.get(i);
			if (i < l.size() - 1)
				result += "\r\n";
		}
		return result;
	}

	public void logWeavings() {
		clog("left-parent="
				+ (getPropParentAndDetails(true) == null ? "-"
						: getPropParentAndDetails(true)));
		clog("right-parent="
				+ (getPropParentAndDetails(false) == null ? "-"
						: getPropParentAndDetails(false)));
		clog("context="
				+ (getPropContextAndDetailz() == null ? "-"
						: getPropContextAndDetailz()));
		clog("requires="
				+ ((getDiagenProperties(DiagenParser.KW_REQUIRES_) == null || getDiagenProperties(
						DiagenParser.KW_REQUIRES_).isEmpty()) ? "-"
						: listAsString(getDiagenProperties(DiagenParser.KW_REQUIRES_))));
		clog("related="
				+ ((getDiagenProperties(DiagenParser.KW_RELATED) == null || getDiagenProperties(
						DiagenParser.KW_RELATED).isEmpty()) ? "-"
						: (" related= " + listAsString(getDiagenProperties(DiagenParser.KW_RELATED)))));
		clog("from=" + (getPropOrigin() == null ? "-" : getPropOrigin()));
		clog("knownas=" + (getPropKnownAs() == null ? "-" : getPropKnownAs()));

	}

}
