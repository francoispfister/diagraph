/*********************************************************************************
 * Copyright (c) 2007, 2008 Jean-Rémy Falleri <jr.falleri@laposte.net>
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse private License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Jean-Remy Falleri <jr.falleri@laposte.net> - initial API and implementation
 *    Francois Pfister <francois.pfister@ema.fr> - Adaptation to enhanced mtbe Trace
 *                                                 - Adaptation to Diagraph
 *********************************************************************************/

package mtbe.model.matcher;


import java.io.File;
import java.util.List;
import java.util.Set;

import mtbe.fr.trace.facade.TraceFacade;
import mtbe.model.matcher.FlatModel;
import mtbe.model.matcher.Instance;
import mtbe.model.matcher.MatchMethod;
import mtbe.model.matcher.helpers.MatchUtils;

import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

import mtbe.fr.trace.Trace;
import mtbe.fr.trace.TraceLink;

import org.isoe.extensionpoint.similarity.ISimilarityControler;
import org.isoe.extensionpoint.similarity.ISimilarityService;



public class Alignment implements ISimilarityService{
	private ISimilarityControler controler;

	private Trace trace;

	public Alignment() {
		super();
	}


	public void setControler(ISimilarityControler view) {
		this.controler = view;
	}


	int requiredSimilarity = 2; // words match required
	private MatchMethod rule = MatchMethod.WORD_INCLUSION;


	private List<Instance> getInstances(EObject model) {
		FlatModel mmp = mtbe.model.matcher.helpers.MatcherFactory
				.getFlatModel(model);
		List<Instance> els = mmp.getAllInstances();
		return els;
	}

	public Trace getTrace() {
		return trace;
	}

	private int align() {
		try {

			List<Instance>  leftInstances = getInstances(trace.getSourceModel());
			List<Instance>  rightInstances = getInstances(trace.getTargetModel());

			List<TraceLink> traces = MatchUtils.getInstance(org.isoe.fwk.core.DParams.NAME_ATTRIBUTES_REVERSED_).match(
					leftInstances,
					rightInstances,
					requiredSimilarity, rule);

			System.out.println(traces.size() + " matches");
			trace.getTraces().clear();
			trace.getTraces().addAll(traces);
			// updateMapped();
			return trace.getTraces().size();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print("error while loading ");
			return -1;
		}
	}

	private void buildTable() {
		if (controler!=null)
			controler.buildTable(trace.getTraces());
	}


	private URI createplatformResourceModelURI(String plugin, String folder,
			String traceModelName, String traceExtension) {

		URI result = null;
		if (!EMFPlugin.IS_ECLIPSE_RUNNING) {
			folder = folder.isEmpty()?"": File.separator+ folder;
			String fp= org.isoe.fwk.platform.resolver.Activator.getWorkspacePath() + plugin  +
					folder + File.separator
					+  traceModelName + "." + traceExtension;
			result = URI.createFileURI(fp);
			return result;
		} else {
			    folder = folder.isEmpty()?"": "/"+ folder;
				result = URI.createURI("platform:/resource/" + plugin
						+ folder + "/" + traceModelName + "." + traceExtension);
			return result;
		}
	}
	private static URI createplatformResourceModelURIstatic(String plugin, String folder,
			String traceModelName, String traceExtension) {
		URI result = null;
		if (!EMFPlugin.IS_ECLIPSE_RUNNING) {
			folder = folder.isEmpty()?"": File.separator+ folder;
			String fp= org.isoe.fwk.platform.resolver.Activator.getWorkspacePath() + plugin  +
					folder
					+ File.separator + traceModelName + "." + traceExtension;
			result = URI.createFileURI(fp);
			return result;
		} else {
			    folder = folder.isEmpty()?"": "/"+ folder;
				result = URI.createURI("platform:/resource/" + plugin
						+ folder + "/" + traceModelName + "." + traceExtension);
			return result;
		}
	}


	private URI createplatformPluginModelURI(String plugin, String folder,
			String traceModelName, String traceExtension) {
		URI result = null;
		if (!EMFPlugin.IS_ECLIPSE_RUNNING) {
			folder = folder.isEmpty()?"": File.separator+ folder;
			String fp= org.isoe.fwk.platform.resolver.Activator.getWorkspacePath() + plugin  +
					folder
					+ File.separator + traceModelName + "." + traceExtension;
			result = URI.createFileURI(fp);
			String s=result.toFileString();
			File f = new File(s);
			if (!f.exists())
				throw new RuntimeException("bad location:"+s);
			//E:\Apps\workspaces\ws-integr-c5-complet\org.isoe.similarity.wikipedia\\\model\wikipedia.ecore
			return result;
		} else {
			    folder = folder.isEmpty()?"": "/"+ folder;
				result = URI.createURI("platform:/plugin/" + plugin
						+ folder + "/" + traceModelName + "." + traceExtension);
			return result;
		}
	}


	private void check(URI uri){
		String furi = CommonPlugin.resolve(uri).toFileString();
		if (furi==null){
			System.out.println(" no trace model: "+uri);
			throw new RuntimeException(" no trace model: "+uri );
		}
		File check=new File(furi);
		if (!check.exists()){
			System.out.println(" no trace model: "+furi);
			throw new RuntimeException(" no trace model: "+furi );
		}
	}



	private void updateMapped() {
		// TODO Auto-generated method stub

	}


	public Trace setTrace(URI uriAlignresult) {
		//alignresultURI_=uriAlignresult;
		try {
			check(uriAlignresult);
			TraceFacade tf = new TraceFacade(uriAlignresult);
			trace = tf.readXmi();
			buildTable();
			updateMapped();
			return trace;
		} catch (Exception e) {
			return null;
		}
	}


	private static void main(String[] args) {
		testAlignmentTable();
	}


	private static void testAlignmentTable() {

		Alignment alignment = new Alignment();


		String traceModelFolder = "";

		String mmModelFolder = "model";

		String tracePlugin ="org.isoe.fwk.megamodel.deployer/torestore-repository";


		String mmPluginright="isoe.bibtex";
		String right="bibtex";

		//String mmPluginleft="org.isoe.similarity.docbook";
		String mmPluginleft="isoe.docbook";
		String left="docbook";


		String mrightext=mmPluginright.substring(mmPluginright.lastIndexOf(".")+1);
		String mleftext=mmPluginleft.substring(mmPluginleft.lastIndexOf(".")+1);

		URI leftMMUri = alignment.createplatformPluginModelURI( //platform/plugin
				mmPluginleft, mmModelFolder, mleftext, "ecore");
		URI rightMMUri = alignment.createplatformPluginModelURI(//platform/plugin
				mmPluginright, mmModelFolder, mrightext, "ecore");

		URI leftMUri = alignment.createplatformResourceModelURI( //platform/resource
				tracePlugin, traceModelFolder, left, mleftext);


		URI rightMUri = alignment.createplatformResourceModelURI(//platform/resource
				tracePlugin, traceModelFolder, right, mrightext);
		URI uriAlignresult = alignment.createplatformResourceModelURI(
				tracePlugin, traceModelFolder, left+"_"+mleftext+"-vs-"+right+"_"+mrightext, "xmi");

		alignment.setTrace(uriAlignresult);
		alignment.alignAndSave(uriAlignresult, leftMMUri, leftMUri, rightMMUri, rightMUri);


	}


	/*

	static void testold(){

		Alignment t = new Alignment(null);

		String tracePlugin = "mtbe-fr";

		String traceModelFolder_ =  "bibtex2docbook";
		String traceModelFolder =  "data";

		String traceModelFolderPath= Activator.getWorkspacePath()+tracePlugin+ File.separator+traceModelFolder + File.separator;

		String leftMM_=traceModelFolderPath + "BibTeX.ecore";
		String leftM_=traceModelFolderPath + "bibtex.xmi";
		String rightMM_=traceModelFolderPath + "DocBook.ecore";
		String rightM_=traceModelFolderPath + "docbook.xmi";

		String leftMM=traceModelFolderPath + "wikipedia.ecore";
		String leftM=traceModelFolderPath + "wikipedia.xmi";
		String rightMM=traceModelFolderPath + "food.ecore";
		String rightM=traceModelFolderPath + "food.xmi";

		URI uriAlignresult = createplatformResourceModelURIstatic(tracePlugin, traceModelFolder,
				"foodwiki", "xmi");

		t.setTrace(uriAlignresult);

		// uriAlignresult =
		// createModelURI(PLUGIN,DATA_FOLDER,"bibtex2docbook","xmi");
		t.alignAndSave(uriAlignresult, leftMM, leftM,
				rightMM, rightM);

	}
	*/


	public Trace alignAndSave(URI out, URI leftMMUri, URI leftMUri,
			URI rightMMUri, URI rightMUri) {
		//URI modelUri_ = null;
		TraceFacade tf = new TraceFacade();
		trace = tf.create(leftMMUri, leftMUri, rightMMUri, rightMUri);
		align();
		tf.saveXmi(out);
		buildTable();
		updateMapped();
		return trace;
	}


	private Trace alignAndSave(URI out, String leftmm, String leftm,
			String rightmm, String rightm) {
		URI modelUri = null;
		TraceFacade tf = new TraceFacade(modelUri);
		trace = tf.create(leftmm, leftm, rightmm, rightm);
		align();
		tf.saveXmi(out);
		buildTable();
		updateMapped();
		return trace;
	}



	public void removeAll(Set<TraceLink> toRemove) {
		getTrace().getTraces().removeAll(toRemove);
	}


	@Override
	public void setOwner(ISimilarityControler similarityControler) {
		this.controler = similarityControler;

	}


	@Override
	public boolean isStub() {
		return false;
	}

}


/*
 *
 *
 	/******************************************************************
	private Trace alignAndSave(URI out, String leftMM, URI leftMUri,
			String rightMM, URI rightMUri) {
		URI modelUri = null;
		TraceFacade tf = new TraceFacade(modelUri);
		trace = tf.create(leftMM, leftMUri, rightMM, rightMUri);
		align();
		tf.saveXmi(out);
		buildTable();
		updateMapped();
		return trace;
	}

	// "mtbe-fr","data","food-vs-wikipedia-aligment-in","food-vs-wikipedia-aligment-out","xmi"
	private Trace setTrace(String plugin, String folder,
			String modelOutput, String modelExtension) {
		// FP130326
		URI uriAlignresult = createplatformResourceModelURI(plugin, folder, modelOutput,
				modelExtension);
		check(uriAlignresult);

		System.out.println(uriAlignresult.toString());

		TraceFacade tf = new TraceFacade(uriAlignresult); // FP130328
		trace = tf.readXmi();
		buildTable();
		updateMapped();
		return trace;
	}



	private void setAlignment(Trace alignment) {
		this.trace = alignment;
		buildTable();
	}

	private static String objToString(EObject obj) {
		String result = obj.eClass().getName() + ": ";
		for (EAttribute attribute : obj.eClass().getEAllAttributes()) {
			Object attrvalue = obj.eGet(attribute);
			if (attrvalue != null)
				result += attribute.getName() + " = " + attrvalue.toString()
						+ " ";
			else
				result += attribute.getName() + " null ";
		}
		return result.trim();
	}


*******************************************************************/


