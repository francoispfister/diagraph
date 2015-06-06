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
package org.isoe.fwk.utils.eclipse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.URI;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.core.Separator;
import org.osgi.framework.Bundle;
//import org.isoe.diagraph.DParams;

/**
 *
 * @author pfister
 * copy resources from the platform:/plugin space to the current
 *         workbench
 *
 */
public class PluginResourceCopy {
//FP120523z
// TODO unify with BundleFileCopier



	private static final boolean LOG  = DParams.PluginResourceCopy_LOG;

	private String sourceFolder;
	private String targetFolder;
	private static String SVN_FOLDER = ".svn/";
	private static String CVS_FOLDER_ = "CVS/";

	private List<String> log = new ArrayList<String>();

	private static PluginResourceCopy instance;
	
	private List<String> copied;




	private PluginResourceCopy() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static PluginResourceCopy getInstance() {
		if (instance==null)
			instance= new PluginResourceCopy();
		return instance;
	}

	public String getLogs() {
		String result="";
		for (String lg : log)
			result+=lg +" ";
		return "files copied: "+result;
	}

	public List<String> getCopied() {
		return copied;
	}
	
	
/*
	private static String REPLACE_SEPARATOR;
	static {
		REPLACE_SEPARATOR = File.separator;
		if (REPLACE_SEPARATOR.equals("\\"))
			REPLACE_SEPARATOR = "\\\\";
	}*/

	private String append(String location, String file) {
		if (!location.endsWith(File.separator))
			location += File.separator;
		location += file;
		location = location.replaceAll("/", Separator.SEPARATOR);
		return location;
	}

	private void copy(URL source, String targetPath) {
		if (LOG){
		  clog("copying (1) file from "+source.getPath()+" to "+targetPath);
		}
		try {
			InputStream in = new FileInputStream(new File(FileLocator.resolve(
					FileLocator.toFileURL(source)).getFile()));
			
			OutputStream out = new FileOutputStream(new File(targetPath));
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0)
				out.write(buf, 0, len);
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("(5) error while copying "
					+ source.toString() + " to " + targetPath);
		}
	}

	private boolean filtered(URL url){
		boolean result =  url.toString().endsWith(SVN_FOLDER) || url.toString().endsWith(CVS_FOLDER_);
		return result;
	}


	private boolean filtered(URL url, String [] filters){
		String urs=url.toString();
		if (filters!=null)
		  for (String filter : filters)
			if (urs.endsWith(filter))
				return true;
		return urs.endsWith(SVN_FOLDER) || urs.endsWith(CVS_FOLDER_);
	}

	private List<URL> getSubElements(Bundle bundle, String path,
			String extension, boolean absoluteURL, boolean nested, String[] filters) {
		List<URL> result = new ArrayList<URL>();
		Enumeration entryPaths = bundle.getEntryPaths(path);
		if (entryPaths == null)
			return result;
		for (Enumeration entry = entryPaths; entry.hasMoreElements();) {
			String fileName = (String) entry.nextElement();
			if (extension == null
					|| (extension != null && fileName.endsWith(extension))) {
				URL url = bundle.getEntry(fileName);
				//if (!url.toString().endsWith(SVN_FOLDER) && !url.toString().endsWith(CVS_FOLDER_) )
				if (!filtered(url,filters))
					if (absoluteURL) {
						try {
							URL furl= FileLocator.toFileURL(url);
							//if (LOG)
							//   clog(" abs "+furl.toString());
							//file:/D:/pfister/workspaces/ws-dev-pref-4/org.isoe.diagraph.gmf.templates/DiagraphTemplates/aspects/
 							result.add(furl);
						} catch (IOException e) {
							e.printStackTrace();
							throw new RuntimeException("Exception " + e.getMessage()+" !!!!");
						}
					} else{
						if (LOG)
						   clog("nabs "+url.toString());
						result.add(url);
					}
			} else if (nested) {
				List<URL> urls = getSubElements(bundle, fileName,
						extension, absoluteURL, nested,filters);
				result.addAll(urls);
			}
		}
		return result;
	}

	private List<String> getItemsFromBundle(Bundle bundle, String path, String[] filters) {
		List<String> result = new ArrayList<String>();
		for (URL url : getSubElements(bundle, path, null, true, false,filters)) {
			String urstring = url.toString();
			urstring = urstring.substring(urstring.indexOf(path));
			result.add(urstring);
		}
		return result;
	}


	private void copy(Bundle srcbundle, String sourceDir,
			File targetDir, boolean replace, String[] filters, String filext) {
		if (!targetDir.exists())
			targetDir.mkdir();
		if (LOG)
		  clog("copying from bundle "+srcbundle.getSymbolicName()+"/"+sourceDir+" to "+targetDir.getAbsolutePath());
		List<String> srcitems = getItemsFromBundle(srcbundle, sourceDir,filters);
		if (srcitems.size() == 0)
			return;
		for (String srcitem : srcitems) {
			//if (LOG)
			 // clog("/"+srcitem);


			String targetPath = targetDir.getAbsolutePath();
			targetPath = targetPath.substring(
					0,
					targetPath.lastIndexOf(targetFolder)
							+ targetFolder.length());
			String filename = append(targetPath,
					srcitem.substring(sourceFolder.length() + 1));
			if (srcitem.endsWith("/"))
				copy(
						srcbundle,
						srcitem,
						new File(filename), true,filters,filext);
			else{
				String extension = null;
				try {
					extension = srcitem.substring(srcitem.lastIndexOf(".")+1);
				} catch (Exception e) {
				}
				if (filext == null || filext.isEmpty() || (extension!=null && extension.startsWith(filext))){
				  File target = new File(filename);
				  if (!target.exists() || (target.exists() && replace)){
				    copy(srcbundle.getEntry(srcitem),filename);
				    copied.add(filename);
				    if (LOG)
				      clog("copied: "+filename);
				  } else
					 if (LOG)
					    clog(" ***   not overwritten: "+filename);
			} //else
				// if (LOG)
				      //clog("not to be copied: "+filename);

			}
		}
	}



	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}

	


	public void copy(String srcplugin, String sourcefolder,
			String targetplugin, String targetfolder, boolean replace, String[] filters, String prefix) {
		if (sourcefolder.startsWith("/"))
			sourcefolder = sourcefolder.substring(1);
		if (targetfolder.startsWith("/"))
			targetfolder = targetfolder.substring(1);
		this.sourceFolder = sourcefolder;
		this.targetFolder = targetfolder;
		File targetDir = new File(CommonPlugin.resolve(URI.createPlatformResourceURI(targetplugin + "/" + targetfolder, true)).toFileString());
		copied = new ArrayList<String>();
		copy(Platform.getBundle(srcplugin),sourcefolder,targetDir,replace,filters, prefix);
		//return getLogs();
	}

}
