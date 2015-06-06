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

import java.util.Vector;

/**
 * 
 * @author pfister voir
 *         http://java.developpez.com/sources/?page=nombresDatesString
 *         #supprimmerAccents
 * 
 */
public class AccentRemover implements StringNormalizer {

	/** Index du 1er caractere accentuÃ© **/
	private static final int MIN = 192;
	/** Index du dernier caractere accentuÃ© **/
	private static final int MAX = 255;
	/** Vecteur de correspondance entre accent / sans accent **/
	private static final Vector<String> map = initMap();

	/**
	 * Initialisation du tableau de correspondance entre les caractÃ©res
	 * accentuÃ©s et leur homologues non accentuÃ©s
	 */

	private static Vector initMap() {

		Vector<String> result = new Vector<String>();
		String car = new String("A");
		result.add(car); /* '\u00C0' À alt-0192 */
		result.add(car); /* '\u00C1' Á alt-0193 */
		result.add(car); /* '\u00C2' Â alt-0194 */
		result.add(car); /* '\u00C3' Ã alt-0195 */
		result.add(car); /* '\u00C4' Ä alt-0196 */
		result.add(car); /* '\u00C5' Å alt-0197 */
		car = new String("AE");
		result.add(car); /* '\u00C6' Æ alt-0198 */
		car = new String("C");
		result.add(car); /* '\u00C7' Ç alt-0199 */
		car = new String("E");
		result.add(car); /* '\u00C8' È alt-0200 */
		result.add(car); /* '\u00C9' É alt-0201 */
		result.add(car); /* '\u00CA' Ê alt-0202 */
		result.add(car); /* '\u00CB' Ë alt-0203 */
		car = new String("I");
		result.add(car); /* '\u00CC' Ì alt-0204 */
		result.add(car); /* '\u00CD' Í alt-0205 */
		result.add(car); /* '\u00CE' Î alt-0206 */
		result.add(car); /* '\u00CF' Ï alt-0207 */
		car = new String("D");
		result.add(car); /* '\u00D0' Ð alt-0208 */
		car = new String("N");
		result.add(car); /* '\u00D1' Ñ alt-0209 */
		car = new String("O");
		result.add(car); /* '\u00D2' Ò alt-0210 */
		result.add(car); /* '\u00D3' Ó alt-0211 */
		result.add(car); /* '\u00D4' Ô alt-0212 */
		result.add(car); /* '\u00D5' Õ alt-0213 */
		result.add(car); /* '\u00D6' Ö alt-0214 */
		car = new String("*");
		result.add(car); /* '\u00D7' × alt-0215 */
		car = new String("0");
		result.add(car); /* '\u00D8' Ø alt-0216 */
		car = new String("U");
		result.add(car); /* '\u00D9' Ù alt-0217 */
		result.add(car); /* '\u00DA' Ú alt-0218 */
		result.add(car); /* '\u00DB' Û alt-0219 */
		result.add(car); /* '\u00DC' Ü alt-0220 */
		car = new String("Y");
		result.add(car); /* '\u00DD' Ý alt-0221 */
		car = new String("Þ");
		result.add(car); /* '\u00DE' Þ alt-0222 */
		car = new String("B");
		result.add(car); /* '\u00DF' ß alt-0223 */
		car = new String("a");
		result.add(car); /* '\u00E0' à alt-0224 */
		result.add(car); /* '\u00E1' á alt-0225 */
		result.add(car); /* '\u00E2' â alt-0226 */
		result.add(car); /* '\u00E3' ã alt-0227 */
		result.add(car); /* '\u00E4' ä alt-0228 */
		result.add(car); /* '\u00E5' å alt-0229 */
		car = new String("ae");
		result.add(car); /* '\u00E6' æ alt-0230 */
		car = new String("c");
		result.add(car); /* '\u00E7' ç alt-0231 */
		car = new String("e");
		result.add(car); /* '\u00E8' è alt-0232 */
		result.add(car); /* '\u00E9' é alt-0233 */
		result.add(car); /* '\u00EA' ê alt-0234 */
		result.add(car); /* '\u00EB' ë alt-0235 */
		car = new String("i");
		result.add(car); /* '\u00EC' ì alt-0236 */
		result.add(car); /* '\u00ED' í alt-0237 */
		result.add(car); /* '\u00EE' î alt-0238 */
		result.add(car); /* '\u00EF' ï alt-0239 */
		car = new String("d");
		result.add(car); /* '\u00F0' ð alt-0240 */
		car = new String("n");
		result.add(car); /* '\u00F1' ñ alt-0241 */
		car = new String("o");
		result.add(car); /* '\u00F2' ò alt-0242 */
		result.add(car); /* '\u00F3' ó alt-0243 */
		result.add(car); /* '\u00F4' ô alt-0244 */
		result.add(car); /* '\u00F5' õ alt-0245 */
		result.add(car); /* '\u00F6' ö alt-0246 */
		car = new String("/");
		result.add(car); /* '\u00F7' ÷ alt-0247 */
		car = new String("0");
		result.add(car); /* '\u00F8' ø alt-0248 */
		car = new String("u");
		result.add(car); /* '\u00F9' ù alt-0249 */
		result.add(car); /* '\u00FA' ú alt-0250 */
		result.add(car); /* '\u00FB' û alt-0251 */
		result.add(car); /* '\u00FC' ü alt-0252 */
		car = new String("y");
		result.add(car); /* '\u00FD' ý alt-0253 */
		car = new String("þ");
		result.add(car); /* '\u00FE' þ alt-0254 */
		car = new String("y");
		result.add(car); /* '\u00FF' ÿ alt-0255 */
		result.add(car); /* '\u00FF' alt-0255 */

		return result;
	}

	private static Vector<String> initMap_old() {
		Vector<String> result = new Vector<String>();
		String car = null;

		car = new String("A");
		result.add(car); /* '\u00C0' Ã€ alt-0192 */
		result.add(car); /* '\u00C1' Ã? alt-0193 */
		result.add(car); /* '\u00C2' Ã‚ alt-0194 */
		result.add(car); /* '\u00C3' Ãƒ alt-0195 */
		result.add(car); /* '\u00C4' Ã„ alt-0196 */
		result.add(car); /* '\u00C5' Ã… alt-0197 */
		car = new String("AE");
		result.add(car); /* '\u00C6' Ã† alt-0198 */
		car = new String("C");
		result.add(car); /* '\u00C7' Ã‡ alt-0199 */
		car = new String("E");
		result.add(car); /* '\u00C8' Ãˆ alt-0200 */
		result.add(car); /* '\u00C9' Ã‰ alt-0201 */
		result.add(car); /* '\u00CA' ÃŠ alt-0202 */
		result.add(car); /* '\u00CB' Ã‹ alt-0203 */
		car = new String("I");
		result.add(car); /* '\u00CC' ÃŒ alt-0204 */
		result.add(car); /* '\u00CD' Ã? alt-0205 */
		result.add(car); /* '\u00CE' ÃŽ alt-0206 */
		result.add(car); /* '\u00CF' Ã? alt-0207 */
		car = new String("D");
		result.add(car); /* '\u00D0' Ã? alt-0208 */
		car = new String("N");
		result.add(car); /* '\u00D1' Ã‘ alt-0209 */
		car = new String("O");
		result.add(car); /* '\u00D2' Ã’ alt-0210 */
		result.add(car); /* '\u00D3' Ã“ alt-0211 */
		result.add(car); /* '\u00D4' Ã” alt-0212 */
		result.add(car); /* '\u00D5' Ã• alt-0213 */
		result.add(car); /* '\u00D6' Ã– alt-0214 */
		car = new String("*");
		result.add(car); /* '\u00D7' Ã— alt-0215 */
		car = new String("0");
		result.add(car); /* '\u00D8' Ã˜ alt-0216 */
		car = new String("U");
		result.add(car); /* '\u00D9' Ã™ alt-0217 */
		result.add(car); /* '\u00DA' Ãš alt-0218 */
		result.add(car); /* '\u00DB' Ã› alt-0219 */
		result.add(car); /* '\u00DC' Ãœ alt-0220 */
		car = new String("Y");
		result.add(car); /* '\u00DD' Ã? alt-0221 */
		car = new String("Ãž");
		result.add(car); /* '\u00DE' Ãž alt-0222 */
		car = new String("B");
		result.add(car); /* '\u00DF' ÃŸ alt-0223 */
		car = new String("a");
		result.add(car); /* '\u00E0' Ã  alt-0224 */
		result.add(car); /* '\u00E1' Ã¡ alt-0225 */
		result.add(car); /* '\u00E2' Ã¢ alt-0226 */
		result.add(car); /* '\u00E3' Ã£ alt-0227 */
		result.add(car); /* '\u00E4' Ã¤ alt-0228 */
		result.add(car); /* '\u00E5' Ã¥ alt-0229 */
		car = new String("ae");
		result.add(car); /* '\u00E6' Ã¦ alt-0230 */
		car = new String("c");
		result.add(car); /* '\u00E7' Ã§ alt-0231 */
		car = new String("e");
		result.add(car); /* '\u00E8' Ã¨ alt-0232 */
		result.add(car); /* '\u00E9' Ã© alt-0233 */
		result.add(car); /* '\u00EA' Ãª alt-0234 */
		result.add(car); /* '\u00EB' Ã« alt-0235 */
		car = new String("i");
		result.add(car); /* '\u00EC' Ã¬ alt-0236 */
		result.add(car); /* '\u00ED' Ã­ alt-0237 */
		result.add(car); /* '\u00EE' Ã® alt-0238 */
		result.add(car); /* '\u00EF' Ã¯ alt-0239 */
		car = new String("d");
		result.add(car); /* '\u00F0' Ã° alt-0240 */
		car = new String("n");
		result.add(car); /* '\u00F1' Ã± alt-0241 */
		car = new String("o");
		result.add(car); /* '\u00F2' Ã² alt-0242 */
		result.add(car); /* '\u00F3' Ã³ alt-0243 */
		result.add(car); /* '\u00F4' Ã´ alt-0244 */
		result.add(car); /* '\u00F5' Ãµ alt-0245 */
		result.add(car); /* '\u00F6' Ã¶ alt-0246 */
		car = new String("/");
		result.add(car); /* '\u00F7' Ã· alt-0247 */
		car = new String("0");
		result.add(car); /* '\u00F8' Ã¸ alt-0248 */
		car = new String("u");
		result.add(car); /* '\u00F9' Ã¹ alt-0249 */
		result.add(car); /* '\u00FA' Ãº alt-0250 */
		result.add(car); /* '\u00FB' Ã» alt-0251 */
		result.add(car); /* '\u00FC' Ã¼ alt-0252 */
		car = new String("y");
		result.add(car); /* '\u00FD' Ã½ alt-0253 */
		car = new String("Ã¾");
		result.add(car); /* '\u00FE' Ã¾ alt-0254 */
		car = new String("y");
		result.add(car); /* '\u00FF' Ã¿ alt-0255 */
		result.add(car); /* '\u00FF' alt-0255 */

		return result;
	}



	/**
	 * Transforme une chaine pouvant contenir des accents dans une version sans
	 * accent
	 * 
	 * @param chaine
	 *            Chaine a convertir sans accent
	 * @return Chaine dont les accents ont Ã©tÃ© supprimÃ©
	 **/
	public String removeFrom(String chaine) {
		if (chaine == null)
			return null;
		StringBuffer Result = new StringBuffer(chaine);
		for (int bcl = 0; bcl < Result.length(); bcl++) {
			int carVal = chaine.charAt(bcl);
			if (carVal >= MIN && carVal <= MAX) { // Remplacement
				String newVal = (String) map.get(carVal - MIN);
				Result.replace(bcl, bcl + 1, newVal);
			}
		}
		return Result.toString();
	}

	@Override
	public boolean matches(String text) {
		return true;
	}

	@Override
	public String normalize(String text) {
		return removeFrom(text);
	}

	private static AccentRemover instance;

	private AccentRemover() {
		super();
	}

	public static AccentRemover getInstance() {
		if (instance == null)
			instance = new AccentRemover();
		return instance;
	}

	public static void main(String[] args) {
		// String chaine = "AccÃ©s Ã  la base";
		String chaine = "Accés à la base";
		String chaine2 = new AccentRemover().removeFrom(chaine);
		System.out.println("chaine origine          : " + chaine);
		System.out.println("chaine sans accents : " + chaine2);

	}

}
