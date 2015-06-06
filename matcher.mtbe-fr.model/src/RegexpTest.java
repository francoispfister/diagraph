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
import java.util.regex.Matcher;
import java.util.regex.Pattern;






public class RegexpTest {

	public static void main(String[] args) {

		String value = "Grose, T. J. K.";

		//String regex = "(\\w++),\\s\\w\\.";
		//String regex = "(\\w++),\\s(\\w)\\.\\s(\\w)\\.\\s(\\w)\\.";
		String regex = "(\\w++),(\\s(\\w)\\.)*";
		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(value);
	
		if (matcher.find()) {
			int i = matcher.groupCount();
			for (int j=1; j<=matcher.groupCount();j++)
				System.out.println("GROUP:" + matcher.group(j));
					
		}
		
		

	}

}
