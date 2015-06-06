 /*******************************************************************************
 * Copyright (c) 2008, Jean-Rémy Falleri.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Jean-Rémy Falleri - initial API and implementation
 *******************************************************************************/

package lirmm.dotutils;

import java.io.File;
import java.io.IOException;

//Dotify.dotityFile("/home/francois/devl/ws-modeling2/com.googlecode.mxl/test/tto.dot","svg");

public class Dotify {
	
	public static void main(String[] args) throws IOException {
		if (args.length != 2){
			args=new String[2];
			args[0] = "/home/francois/devl/ws-modeling2/com.googlecode.mxl/test/";
			args[1] = "svg";
			//return;
		}
		//else
			dotifyFolder(args[0],args[1]);
	}
	
	
	public static void writeGraphLog(String filePath) {
		File file = new File(filePath);
		String dotPath = file.getAbsolutePath();
	}
	
	public static void drawGraph(String filePath,String format) throws IOException  {
		File file_ = new File(filePath);
		String dotPath = file_.getAbsolutePath();
		String imgPath = dotPath + "." + format;
		//String charset= "charset=iso-8859-1";
		//String charset1= "charset=UTF-8";
		String charset2= "charset=latin1";
		String dotCommand = "dot -T" + format + " " + dotPath + " -o " + imgPath+ " -G"+charset2;
		System.out.println("executing " + dotCommand);
		Process p = Runtime.getRuntime().exec(dotCommand);
		try {
		p.waitFor();
		}
		catch(InterruptedException e) {
			System.out.println("error dotifying");	
		}
	}
	
	public static void dotifyFolder(String folderPath,String format) throws IOException {
		File folder = new File(folderPath);
		if ( !folder.isDirectory() )
			return;
		for(File f: folder.listFiles() ) {
			if ( f.getName().endsWith(".dot")) {
				String dotPath = f.getAbsolutePath();
				String imgPath = f.getAbsolutePath() + "." + format;
				String dotCommand = "dot -T" + format + " " + dotPath + " -o " + imgPath;
				Runtime.getRuntime().exec(dotCommand);
			}
		}
	}



}
