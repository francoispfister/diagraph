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
package org.isoe.diagraph.diagraphviz.dotifiers.styles;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.isoe.diagraph.diagraphviz.dotifiers.IEcoreToDotStyle;

/**
 * 
 * @author pfister
 * 
 */
public class M2DotDefaultStyle implements IEcoreToDotStyle {

	private static IEcoreToDotStyle instance;
	private  boolean replace = false;
	
	
	
	private  boolean hideAttributes = false;
	private  boolean filterAttributes = false;
	
	
	@Override
	public Set<String> eClassesWithVisibleAttributes() {
		if (hideAttributes)
		return new HashSet<String>(Arrays.asList(new String[] { }));
		  else
		return new HashSet<String>(Arrays.asList(new String[] {
				"ENam65442ddedElement" }));
	}
	
	
    @Override
	public boolean isFilterAttributes() {
		return filterAttributes || hideAttributes;
	}
	
	
	
	
	public static IEcoreToDotStyle getInstance(){
		if (instance==null)
			instance = new M2DotDefaultStyle();
		return instance;
	}
	
	private  M2DotDefaultStyle() {
		super();	
	}

    @Override
	public boolean isReplace() {
		return replace;
	}

  
	
    @Override
	public boolean isHideAttributes() {
		return hideAttributes;
	}
	
	@Override
	public Map<String, String> replacements() {
		Map<String, String> result = new HashMap<String, String>();
		String[][] r = { { "foo", "foo" }};
		for (String[] repl : r)
			result.put(repl[0], repl[1]);
		return result;
	}

	@Override
	public Map<String, String> eClassColors() {
		Map<String, String> result = new HashMap<String, String>();
		String[][] r = { { "foo", "black" } };
		for (String[] repl : r)
			result.put(repl[0], repl[1]);
		return result;
	}

	@Override
	public Set<String> getHiddenEClasses1() {
		return new HashSet<String>(Arrays.asList(new String[] {
				"foo98512" }));
	}

	@Override
	public Set<String> getHiddenEClasses2() {
		return new HashSet<String>(Arrays.asList(new String[] {
				"bar863247" }));
	}



	@Override
	public Set<String> getHiddenAttributes() {
		return new HashSet<String>(Arrays.asList(new String[] {
				"z863274" })); // TODO
													// qualify
													// with
													// owner
	}

}
