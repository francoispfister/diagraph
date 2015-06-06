 /**
 * Copyright (c) 2014 Laboratoire de Genie Informatique et Ingenierie de Production - Ecole des Mines d'Ales
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Blazo Nastov (ISOE-LGI2P) - initial API and implementation
 */
package org.isoe.diagraph.gramgen.save.apicaller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.isoe.fwk.core.DParams;

/**
 * 
 * @author bnastov
 *
 */
public class RESTApiCaller {
	//TODO change ArrayList<ArrayList<String>>  to List<List<String>> everywhere
	
	private final static String RestEndPoint = "http://"+DParams.REST_POINT_SERVER_NAME+"/WebService/api.php?";

	
	public static ArrayList<ArrayList<String>> callGetAllModelContentByUriAction(String uri) throws IOException{
		return callAPIAllContentResponse(RestEndPoint + "action=get_all_model_content", "ns_uri=" + uri);
	}
	
	public static ArrayList<ArrayList<String>> callGetAllMetaModelContentByUriAction(String uri) throws IOException{
		return callAPIAllContentResponse(RestEndPoint + "action=get_all_meta_model_content", "ns_uri=" + uri);
	}
	
	public static ArrayList<String> callGetModelsByUriAction(String uri)throws IOException{
		return callAPIWithMultipleResponses(RestEndPoint + "action=get_models", "ns_uri=" + uri);
	}
	
	public static ArrayList<String> callGetDiagramsByUriAction(String uri)throws IOException{
		return callAPIWithMultipleResponses(RestEndPoint + "action=get_diagrams", "ns_uri=" + uri);
	}
	
	public static String callGetEcoreByUriAction(String uri) throws IOException{
		return callAPI(RestEndPoint + "action=get_ecore", "ns_uri=" + uri);
	}
	
	public static String callGetEcorediagByUriAction(String uri) throws IOException{
		return callAPI(RestEndPoint + "action=get_ecorediag", "ns_uri=" + uri);
	}
	
	public static String callGetNameByUriAction(String uri) throws IOException{
		return callAPI(RestEndPoint + "action=get_name", "ns_uri=" + uri);
	}
	
	public static String callPutAction(String uri, String prefix, String name, String description, 
			String contentEcore, String contentEcorediag) throws IOException{
		return callAPI(RestEndPoint + "action=put", 
				"ns_uri=" 				+ uri 			+ "&" + 
				"ns_prefix=" 			+ prefix 		+ "&" + 
				"name=" 				+ name 			+ "&" + 
				"description=" 			+ description 	+ "&" +
				"content_ecore=" 		+ contentEcore 	+ "&" +
				"content_ecorediag=" 	+ contentEcorediag);
	}
	
	public static String callPutModelAction(String uri, String fileName, String description, 
			String contentModel, String contentDiagram) throws IOException{
		return callAPI(RestEndPoint + "action=put_model", 
				"ns_uri=" 				+ uri 			+ "&" + 
				"file_name="				+ fileName		+ "&" + 
				"description=" 			+ description 	+ "&" +
				"content_model=" 		+ contentModel 	+ "&" +
				"content_diagram=" 		+ contentDiagram);
	}
	
	private static  ArrayList<ArrayList<String>> callAPIAllContentResponse(String uri, String parameters) throws IOException {
		URL url = new URL(uri);
        String query = parameters;
        
        //make connection
        URLConnection urlc = url.openConnection();

        //use post mode
        urlc.setDoOutput(true);
        urlc.setAllowUserInteraction(false);

        //send query
        PrintStream ps = new PrintStream(urlc.getOutputStream());
        ps.print(query);
        ps.close();
        
        //get result
        BufferedReader br = new BufferedReader(new InputStreamReader(urlc
            .getInputStream()));
        String l = null;
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        
        while ((l=br.readLine())!=null) {
        	String [] element = l.split("<elementend/>");
        	for (String str : element) {
        		String [] modelSplit = str.split("<modelend/>");
        		String model 	= modelSplit[0];
        		String [] diagSplit	= modelSplit[1].split("<diagramend/>");
        		String diagram 	= diagSplit[0];
        		String fileName = diagSplit[1].split("<elementend/>")[0];
        		
        		ArrayList<String> elements = new ArrayList<String>();
        		elements.add(model);
        		elements.add(diagram);
        		elements.add(fileName);
        		
        		result.add(elements);
			}            
        }
        br.close();
        
        if(result.size()>0)
        	return result;
        else
        	return null;
	}
	
	private static ArrayList<String> callAPIWithMultipleResponses(String uri, String parameters) throws IOException {
		URL url = new URL(uri);
        String query = parameters;
        
        //make connection
        URLConnection urlc = url.openConnection();

        //use post mode
        urlc.setDoOutput(true);
        urlc.setAllowUserInteraction(false);

        //send query
        PrintStream ps = new PrintStream(urlc.getOutputStream());
        ps.print(query);
        ps.close();
        
        //get result
        BufferedReader br = new BufferedReader(new InputStreamReader(urlc
            .getInputStream()));
        String l = null;
        ArrayList<String> result = new ArrayList<String>();
        
        while ((l=br.readLine())!=null) {
        	String [] lstr = l.split("<elementend/>");
        	for (String str : lstr) {
        		result.add(str);
			}            
        }
        br.close();
        
        if(result.size()>0)
        	return result;
        else
        	return null;
	}
	
	private static String callAPI(String uri, String parameters) throws IOException{
		URL url = new URL(uri);
        String query = parameters;
        
        //make connection
        URLConnection urlc = url.openConnection();

        //use post mode
        urlc.setDoOutput(true);
        urlc.setAllowUserInteraction(false);

        //send query
        PrintStream ps = new PrintStream(urlc.getOutputStream());
        ps.print(query);
        ps.close();
        
        //get result
        BufferedReader br = new BufferedReader(new InputStreamReader(urlc
            .getInputStream()));
        String l = null;
        String result ="";
        while ((l=br.readLine())!=null) {
            result+=l;
        }
        br.close();
        
        if(result.length()>0)
        	return result;
        else
        	return null;
	}
	
}
