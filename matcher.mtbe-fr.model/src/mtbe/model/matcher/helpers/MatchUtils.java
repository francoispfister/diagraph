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
package mtbe.model.matcher.helpers;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import mtbe.fr.trace.TraceFactory;
import mtbe.fr.trace.TraceLink;
import mtbe.model.matcher.Entity;
import mtbe.model.matcher.Instance;
import mtbe.model.matcher.ILabel;
import mtbe.model.matcher.Match;
import mtbe.model.matcher.MatchMethod;
import mtbe.model.matcher.Matcher;
import mtbe.model.matcher.impl.DefaultCompareMatcherImpl;
import mtbe.model.matcher.impl.DoubleLabelImpl;
import mtbe.model.matcher.impl.IntLabelImpl;
import mtbe.model.matcher.impl.LevenshteinCompareMatcherImpl;
import mtbe.model.matcher.impl.StringLabelImpl;
import mtbe.model.matcher.impl.TextLabelImpl;
import mtbe.model.matcher.impl.WordCompareMatcherImpl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;



public class MatchUtils  {

	// ms to ls
	
	private static MatchUtils instance;
	
	
	static public boolean useLevenshtein = true;

	StringNormalizer[] normalizers = { AccentRemover.getInstance() };// ,BibtexAuthorNormalizer.getInstance()


	private String[] namerules;
																			// };
	static int rul = -1;


	public static MatchUtils getInstance(String[] prefs) {
		if (instance==null)
			instance = new MatchUtils(prefs);
		return instance;
	}

	
	private  MatchUtils(String[] namerules) {
		this.namerules = namerules;
	}

	public boolean contains(String name, String[] exclude) {
		for (String ex : exclude)
			if (ex.equals(name))
				return true;
		return false;
	}

	public int bestStringLabelAttribute(EAttribute attr, String[] exclude) {
		if (!attr.getEType().getName().toLowerCase().contains("string"))
			return 0;
		else {
			String name = attr.getName().toLowerCase();
			for (int i = namerules.length - 1; i > -1; i--) {
			//for (int i = 0; i <namerules.length; i++) {
				if (!contains(name, exclude)) {
					if (name.equals(namerules[i]))
						return i + namerules.length + 1;
					if (name.contains(namerules[i]))
						return i + 1;
				}
			}
			return 0;
		}
	}

	public String convert(String src, String srcCharset,
			String targetCharset) {
		String target = null;
		if (null != src && !src.equals("")) {
			try {
				byte[] bytes = src.getBytes(srcCharset);// "ISO-8859-1");
				target = new String(bytes, targetCharset);// "UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		} else {
			target = src;
		}
		return target;
	}

	public ILabel getLabel(EObject object, Entity labelledClas) {
		ILabel result = null;
		EAttribute attr = labelledClas.getBestLabelAttribute();
		int score = labelledClas.getBestLabelScore();
		if (attr == null) // class with no attribute
			result = new StringLabelImpl(object.eClass().getName(), score);
		else {
			// @TODO add date
			if (attr.getEType().getName().toLowerCase().contains("string")) {
				String str = (String) object.eGet(attr); // FP100302
				for (int i = 0; i < normalizers.length; i++) 
					if (normalizers[i].matches(str))
						str = normalizers[i].normalize(str);
				if (useLevenshtein)
					result = new TextLabelImpl(str, score);
				else
					result = new StringLabelImpl(str, score);
			} else if (attr.getEType().getName().toLowerCase().contains(
					"double")) {
				Double dou = (Double) object.eGet(attr);
				result = new DoubleLabelImpl(dou, score);
			} else if (attr.getEType().getName().toLowerCase().contains("int")) {
				Integer ent = (Integer) object.eGet(attr);
				result = new IntLabelImpl(ent, score);
			}
		}
		return result;
	}

	private mtbe.fr.trace.TraceLink createLink(Instance src, Instance tgt,
			String sourceValue, String targetValue, int requiredSimilarity,
			int realSimilarity, String rationale, MatchMethod rule) {
		TraceLink tl = TraceFactory.eINSTANCE.createTraceLink();
		tl.setSourceValue(sourceValue);
		tl.setTargetValue(targetValue);
		tl.setSource(src.getObject());
		tl.setTarget(tgt.getObject());
		tl.setSimilarity(realSimilarity);
		tl.setRequiredSimilarity(requiredSimilarity);
		tl.setRationale(rationale);
	
		switch (rule) {
		case WORD_INCLUSION:
			rul = 1;
			break;
		case WHOLE_TEXT:
			rul = 2;
			break;
		case METHOD3:
			rul = 3;
			break;
		default:
			rul = 4;
			break;
		}
		tl.setSimilarityMethod(rul);
		return tl;
	}

	public String objToString(EObject obj) {
		String result = "";
		for (EAttribute attribute : obj.eClass().getEAllAttributes()) {
			Object attrvalue = obj.eGet(attribute);
			if (attrvalue != null)
				result += attrvalue.toString() + " ";
		}
		return result.trim();
	}

	public String objToId(EObject obj) {
		for (EAttribute attribute : obj.eClass().getEAllAttributes()) {
			if ("id".equals(attribute.getName()))
				return obj.eGet(attribute).toString();
		}
		return "";
	}

	

	public List<TraceLink> match(List<Instance> sourceObjects,
			List<Instance> targetObjects, int requiredSimilarity,
			MatchMethod rule) {
		Matcher m = null;
		if (rule == MatchMethod.WORD_INCLUSION)
			m = new WordCompareMatcherImpl(requiredSimilarity);
		else if (rule == MatchMethod.WHOLE_TEXT)
			m = new LevenshteinCompareMatcherImpl(requiredSimilarity);
		else
			m = new DefaultCompareMatcherImpl();

		List<TraceLink> result = new ArrayList<TraceLink>();
		for (Instance tgt : targetObjects) {
			for (Instance src : sourceObjects) {
				Match mat = m.match(src, tgt);
				if (mat.isTrue()) {

					result.add(createLink(src, tgt, src.getLabel()
							.getStringValue(), tgt.getLabel().getStringValue(),
							requiredSimilarity, mat.getSimilarity(), mat
									.getRationale(), rule));
				}
			}
		}
		return result;
	}


}
