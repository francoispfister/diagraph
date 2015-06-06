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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author pfister
 *
 */
public class BibtexAuthorNormalizer implements StringNormalizer{

	private static BibtexAuthorNormalizer instance;
	private static final Pattern bibtexAuthor = Pattern
			.compile("(\\w++),(\\s\\w\\.)*");
	private static final Pattern bibtexAuthorInitials = Pattern
			.compile("\\s(\\w)\\.");
	
	
	public void test() {
		System.out.println(normalize("Grose, T. J. K. S."));
		System.out.println(normalize("Foobar, A. B."));
		System.out.println(normalize("Barfoo, Z. X."));
		/*
GroseTJKS
FoobarAB
BarfooZX		
		 */
	}	

	public static BibtexAuthorNormalizer getInstance() {
		if (instance == null)
			instance = new BibtexAuthorNormalizer();
		return instance;
	}

	public String toString() {
		return "BibtexAuthorNormalizer [" + bibtexAuthor.pattern() + ";"
				+ bibtexAuthorInitials.pattern() + "]";
	}

	private BibtexAuthorNormalizer() {
		super();
	}

	
	public boolean matches(String text) {
		return bibtexAuthor.matcher(text).find();
	}

	
	
	public String normalize(String text) {
		Matcher m = bibtexAuthor.matcher(text);
		String result = "";
		if (m.find()) {
			result = m.group(1);
			m = bibtexAuthorInitials.matcher(text);
			while (m.find()) {
				for (int j = 1; j <= m.groupCount(); j++) {
					result += m.group(j);
				}
			}
		}
		return result;
	}

	public static void main(String[] args) {
		BibtexAuthorNormalizer.getInstance().test();
	}

}
