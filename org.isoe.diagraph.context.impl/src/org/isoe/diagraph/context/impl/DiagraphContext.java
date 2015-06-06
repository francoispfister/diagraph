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
package org.isoe.diagraph.context.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;
import org.isoe.diagraph.context.IDiagraphContext;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.diagraph.DPointOfView;
import org.isoe.diagraph.generic.GenericConstants;
import org.isoe.diagraph.views.ViewConstants;
//import org.isoe.extensionpoint.diagraph.IDiagraphConsole;
import org.isoe.fwk.core.DParams;

/**
 *
 * @author fpfister
 *
 */
import org.isoe.diagraph.controler.IDiagraphControler;  //FP140707refactored
public class DiagraphContext implements IDiagraphContext {

	private static final boolean MERGE_LOG = DParams.merge_LOG;
	private static final boolean LOG = DParams.DiagraphContext_LOG;
	private static final boolean CERROR = DParams.LOG_AT_RUNTIME;

	private IDiagraphControler controler;

	private List<DGraph> currentDiagraphs;
	private Map<String, DGraph> diagraphviews = new HashMap<String, DGraph>();

	private Diagram currentDiagram;
	private DGraph currentDiagraph;
	private EClass currentRoot;

	private Diagram toMergeDiagram;
	private DGraph toMergeDiagraph;
	private EClass toMergeRoot;

	private boolean merging;

	private DGraph sourceDiagraph;



	/*
		private IDiagraphConsole console;

	private IDiagraphConsole getConsole() {
    	if (console==null)
    	    console =(IDiagraphConsole) PlatformUI.getWorkbench().getActiveWorkbenchWindow()
    				.getActivePage().findView(GenericConstants.DIAGRAPH_BASIC_CONSOLE_ID);
		return console;
	}	 */


	@Override
	public void setSourceDiagraph(DGraph sourceDGraph) {
		this.sourceDiagraph = sourceDGraph;
	}


	@Override
	public DGraph getSourceDiagraph() {
		return sourceDiagraph;
	}



	public void setCurrentRoot(EClass currentRoot) {
		this.currentRoot = currentRoot;
		if (MERGE_LOG)
			clog("currentRoot="
					+ (currentRoot == null ? "null" : currentRoot.getName()));
	}

	@Override
	public void endMerge() {
		if (merging) {
			if (MERGE_LOG) {
				clog("end merge with current="
						+ getDGraphName_(currentDiagraph) + "."
						+ getDGraphView_(currentDiagraph));
				clog("end merge with  merge=" + getDGraphName_(toMergeDiagraph)
						+ "." + getDGraphView_(toMergeDiagraph));
			}
			merging = false;
			controler.endMerge();
			// checkPinToMerge.setSelection(false);
			toMergeRoot = null;
			toMergeDiagraph = null;
			toMergeDiagram = null;

		}
	}

	public DiagraphContext(IDiagraphControler controler) {
		this.controler = controler;
	}

	private String getDGraphName_(DGraph dgraph) {
		return dgraph == null ? "_null_" : ((EClass) dgraph.getPointOfView()
				.getSemanticRole()).getEPackage().getName();
	}

	private String getDGraphView_(DGraph dgraph) {
		return dgraph == null ? "_null_" : dgraph.getViewName();
	}

	private String getCurrentRootName_() {
		return currentRoot == null ? null : currentRoot.getName();
	}

	private String getMergeRootName_() {
		return toMergeRoot == null ? null : toMergeRoot.getName();
	}

	private String getViewName(EAnnotation anot) {
		EMap<String, String> entries = anot.getDetails();
		for (Entry<String, String> entry : entries) {
			String k = entry.getKey();
			if (k.startsWith("view")) {
				String[] kv = k.split("=");
				return kv[1];
			}
		}
		return ViewConstants.DIAGRAPH_DEFAULT;
	}

	@Override
	public List<String> getViewsForLanguage(String language) { // FP140603
		List<String> result = new ArrayList<String>();
		EPackage currentPackage = controler.getCurrentPackage();
		if (currentPackage != null && currentPackage.getName().equals(language)) {
			for (EClassifier claz : currentPackage.getEClassifiers()) {
				if (claz instanceof EClass) {
					List<EAnnotation> anots = ((EClass) claz).getEAnnotations();
					for (EAnnotation anot : anots)
						if (anot.getSource().equals(
								GenericConstants.ANNOT_DIAGRAPH_LITTERAL))
							for (Entry<String, String> entry : anot
									.getDetails())
								if (entry
										.getKey()
										.equals(org.isoe.diagraph.lang.DiagraphKeywords.POINT_OF_VIEW))
									result.add(getViewName(anot));
				}
			}

		}
		return result;
	}

	private void log1(Diagram current, Diagram ex) {
		try {
			EPackage curdPackage = (EPackage) current.getElement();
			EPackage exde = (EPackage) ex.getElement();
			clog("AKW----currentDiagraph :cpak="
					+ curdPackage.getName()
					+ " curd="
					+ curdPackage.getName()
					+ " expak="
					+ exde.getName()
					+ (curdPackage == null ? "" : " pak="
							+ curdPackage.getName()));// FP140422
		} catch (Exception e) {
			controler.cerror("error log1");
		}
	}

	private void log2(List<DGraph> cs) {
		try {
			if (!cs.isEmpty()) {
				String log = "";
				for (DGraph graph : cs)
					log += graph.getPointOfView().getEClaz().getEPackage()
							.getName()
							+ "." + graph.getViewName() + "; ";
				clog("(1) AKW result =" + log);
			} else
				clog("(1) AKW result = null");
		} catch (Exception e) {
			controler.cerror("error log2");
		}
	}




	static int errid;

	private String getErrorPath(){
	  File here = new File(".");
	  String path = here.getAbsolutePath();// C:\workspaces\vobeo1\fr.obeo.formation.ales.uml\.
	  path = path.substring(0, path.length() - 1);
	  path += "_"+errid+++"error.txt";
	 return path;
	}


	private void writeErrorInFile(String content) {
		if ( content != null
				&& !content.isEmpty()) {
			FileWriter fw;
			try {
				fw = new FileWriter(new File(getErrorPath()), false);
				fw.write(content);
				fw.close();
			} catch (IOException e) {
				System.err.println("error " + e.toString());
			}
		} else
			System.err.println("nothing to save ");
	}


	public void showDebugMessage(String message) {

			MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
					"Debug", message);

	}


	private void addSelectedDiagraph(Diagram current, String view, List<DGraph> cs) { // FP140603
		;
		boolean rcp = false;
		boolean save = true;
		boolean atRuntime = true;
		//writeErrorInFile("test " );


		if (CERROR) controler.cerror("starting addSelectedDiagraph ");
		EPackage pak = (EPackage) current.getElement();

		if (CERROR) controler.cerror("going controler addSelectedDiagraph ");
		DGraph dGraph = controler.selectDiagraphByView(pak, view, current,
				!save, atRuntime, rcp, false, controler.isHeadless(),
				new NullProgressMonitor()); // old,
		if (dGraph == null) // FP140616voir4
			controler.cerror("dGraph == null for view " + view);
		else if (!cs.contains(dGraph)){
			cs.add(dGraph);
		}

		// GCD error idx =5 ********** java.lang.RuntimeException: index=9,
		// size=9 ****************
	}

	private List<DGraph> parseCurrentDiagraphs(Diagram current, Diagram ex) {
		;
		int idx = 0;
		EPackage pak = (EPackage) current.getElement();
		//controler.cerror("parseCurrentDiagraphs "+pak.getName());
		List<DGraph> cs = new ArrayList<DGraph>();
		String cv = "";
		try {
			if (current != null && ex != null
					&& current.getElement() != ex.getElement()) {
				idx = 1;
				if (LOG)
					log1(current, ex);
				idx = 2;
				if (CERROR) controler.cerror("parseCurrentDiagraphs - updateAnnotations "+idx);
				controler.updateAnnotations();
				idx = 3;
			}
			if (CERROR) controler.cerror("parseCurrentDiagraphs - step "+idx);
			idx = 4;
			List<String> views = getViewsForLanguage(controler
					.getCurrentPackage().getName());


				idx = 5;
			if (CERROR) controler.cerror("parseCurrentDiagraphs - step "+idx);

			String l="";
			for (String view : views) { // FP140603
		      l+=view+" ";
			}
			if (CERROR) controler.cerror("parseCurrentDiagraphs - (1)views= "+l+" "+idx);

			for (String view : views) { // FP140603
				cv = view;
				addSelectedDiagraph(current, view, cs);
				idx = 6;
				// GCD error idx =5 ********** java.lang.RuntimeException:
				// index=9, size=9 ****************
			}
			if (LOG)
				log2(cs);
			if (CERROR) controler.cerror("parseCurrentDiagraphs - (2)views= "+cs+" "+idx);
			idx = 7;

		} catch (Exception e) {
			controler.log("diagraph error", e.toString()); // FP130615
			if (!(e instanceof java.lang.NullPointerException))
				controler.cerror("GCD error idx =" + idx + "view=" + cv
						+ "   **********  " + e.toString()
						+ " **************** "+idx);
		}
		if (CERROR) controler.cerror("parseCurrentDiagraphs - ok  "+idx);

		return cs;
	}

	@Override
	public void getCurrentDiagraphs(Diagram current, Diagram ex) {
		currentDiagraphs = parseCurrentDiagraphs(current, ex);
		for (DGraph dGraph : currentDiagraphs) {
			put(dGraph);// FP140626modified
		}
	}

	@Override
	public DGraph getCurrentDiagraph(Diagram current, Diagram ex, String viu) {

		DGraph curdgraph = null;// currentDiagraph_ = null;
		currentRoot = null;
		EPackage pak = null;
		if (current != null)
			pak = (EPackage) current.getElement();
		try {

			// getCurrentDiagraphs(current, ex);
			// currentDiagraphs = getCurrentDiagraphs(current, ex);

			if (currentDiagraphs.isEmpty())
			    getCurrentDiagraphs(current, ex);//FP150418az

			for (DGraph dGraph : currentDiagraphs) {
				// put(dGraph);// FP140626modified
				// .getFacade1().getLanguageName()+""+dGraph.getViewName(),dGraph);
				if (dGraph.getViewName().equals(viu))
					curdgraph = dGraph;
			}
			if (curdgraph == null)
				throw new RuntimeException("no diagraph !!!");
			else
				setCurrentDiagraph(curdgraph);

			DPointOfView curoot = currentDiagraph.getPointOfView();
			setCurrentRoot(curoot.getEClaz());
			if (LOG)
				clog("current diagraph=" + getDGraphName_(currentDiagraph)
						+ "." + getDGraphView_(currentDiagraph));
		} catch (Exception e) {
			setCurrentDiagraph(null);
			currentRoot = null;
			String errMesg = "error while getCurrentDiagraph - not diagraphed "
					+ (pak == null ? "" : pak.getName());
			controler.cerror(errMesg + " " + e.toString());
			controler.log("getCurrentDiagraph", errMesg);
		}
		if (currentRoot == null) {
			setCurrentRoot(controler.getCurrentPov()); // FP131008
			if (LOG)
				clog("default set to "
						+ (currentRoot == null ? "null" : currentRoot.getName()));
		}
		return currentDiagraph;
	}


	@Override
	public String getCurrentAsString(String langview) {
		DGraph curnt = get(langview);
		if (curnt!=null)
		  if (!controler.isMerging())
			setCurrentRoot(curnt.getPointOfView().getEClaz());

		return currentRoot == null ? "??" : (currentRoot.getEPackage().getName()
				+ "." + currentRoot.getName());
	}

	@Override
	public void startMerge(String lw) {

		if (!merging) {
			if (MERGE_LOG) {
				DGraph d = get(lw);
				setMergeDiagraph(d);
				clog("start merge with current="
						+ getDGraphName_(currentDiagraph) + "."
						+ getDGraphView_(currentDiagraph));
				clog("start merge with  merge="
						+ getDGraphName_(toMergeDiagraph) + "."
						+ getDGraphView_(toMergeDiagraph));
			}
			// checkPinToMerge.setText("merge with :");
			controler.startMerge();
			merging = true;
		}
	}

	@Override
	public String updateCurrentDiagraph(Diagram diag_, Diagram exdiag,
			String view) {
		try {
			return doUpdateCurrentDiagraph(diag_, exdiag, view);
		} catch (Exception e) {
			controler.cerror("error updateCurrentDiagraph "+e.toString());
			return "error updateCurrentDiagraph "+e.toString();
		}

	}

	private String doUpdateCurrentDiagraph(Diagram diag_, Diagram exdiag,
				String view) {
		String result = "";
		if (!isMerging())
		   currentDiagram = diag_; // FP140626voir
		else
			toMergeDiagram = diag_;
		String lang = ((EPackage) diag_.getElement()).getName();
		DGraph d = get(lang + "." + view);
		if (!isMerging())
		  setCurrentDiagraph(d);//
		else
			setMergeDiagraph(d);
		if (currentDiagraph != null) {
			if (LOG)
				clog("current language: currentDGraph="
						+ getDGraphName_(currentDiagraph) + "."
						+ getDGraphView_(currentDiagraph) + " root="
						+ getCurrentRootName_());
			//EClass currentRoot = getCurrentRoot();
			EClass mergRoot = toMergeDiagraph==null?null:toMergeDiagraph.getPointOfView().getEClaz();

			if (currentRoot != null && mergRoot!=null) {
				if (isMerging()) {

					if (MERGE_LOG) clog(mergRoot.getEPackage().getName());
					setMergeRoot(mergRoot);
					setMergeDiagraph(d);
					EClassifier toMergeRoot = getMergeRoot();
					if (MERGE_LOG) clog(toMergeRoot.getEPackage().getName());
					result = toMergeRoot.getEPackage().getName() + "."
							+ toMergeRoot.getName();
				}else
				  result = currentRoot.getEPackage().getName() + "."
						+ currentRoot.getName();
			}
		} else{
			controler.cerror("error: no current diagraph");
			result = "error: no current diagraph";
		}
		return result;
	}

	/*
	 * @Override public void cloneLanguage_(Diagram currentDiagram, Diagram
	 * exDiagram, String view) { //TODO DGraph curnt =
	 * getCurrentDiagraph(currentDiagram, exDiagram, view); //DGraph curnt =
	 * getCurrentDiagraph(); if (LOG) clog("clone languag -src: currentDGraph="
	 * + getDGraphName_(curnt) + "." + getDGraphView_(curnt) + " root=" +
	 * getCurrentRootName_()); boolean headlessNo = false; EPackage newp =
	 * controler.clone(curnt, headlessNo); String newname_ = newp == null ? null
	 * : newp.getName(); Diagram newdiag = controler.getEcoreDiagram(newname_);
	 * controler.updateLanguageToTransform(newdiag, newp); }
	 */
	private void clog(String mesg) {
		if (LOG || MERGE_LOG)
			System.out.println(mesg);
	}

	@Override
	public void setCurrentDiagram(Diagram diagram) {
		this.currentDiagram = diagram;
	}

	@Override
	public Diagram getCurrentDiagram() {
		return currentDiagram;
	}

	@Override
	public void setMergeDiagram(Diagram diagram) {
		toMergeDiagram = diagram;
	}

	@Override
	public DGraph getCurrentDiagraph() {
		return currentDiagraph;
	}

	void log() {
		if (DParams.merge_LOG) {
			clog("__MergeDiagraph= "
					+ (toMergeDiagraph == null ? "~" : toMergeDiagraph
							.getFacade1().getLanguageName()));
			clog("__CurrentDiagraph= "
					+ (currentDiagraph == null ? "~" : currentDiagraph
							.getFacade1().getLanguageName()));
		}
	}

	@Override
	public void setCurrentDiagraph(DGraph dGraph) {
		currentDiagraph = dGraph;
		put(currentDiagraph);
		if (DParams.merge_LOG)
			clog("__CurrentDiagraph= "
					+ (currentDiagraph == null ? "~" : currentDiagraph
							.getFacade1().getLanguageName()));
	}

	private void put(DGraph dGraph) {
		if (dGraph != null) {
			//if (MERGE_LOG)
			//	clog("put " + dGraph.getFacade1().getLanguageName() + "."
			//			+ dGraph.getViewName());

			dGraph.getFacade1().initGraph(dGraph);//FP150602
			//System.out.println(dGraph.getFacade1().getLanguageName() + "."
			//		+ dGraph.getViewName());
			diagraphviews.put(dGraph.getFacade1().getQualifiedLanguageName()
					//+ "."+ dGraph.getViewName()
					, dGraph);
		} else
			controler.cerror("current dgraph is null");
	}

	@Override
	public DGraph get(String language, String view) {
		System.out.println(language + "."
				+ view);
		return get(language + "." + view);
	}

	@Override
	public DGraph get(String key) {
		return diagraphviews.get(key);
	}

	@Override
	public boolean isMerging() {
		return merging;
	}

	@Override
	public DGraph getMergeDiagraph() {
		return toMergeDiagraph;
	}

	@Override
	public void setMergeDiagraph(DGraph dGraph) {
		toMergeDiagraph = dGraph;
		if (DParams.merge_LOG)
			clog("__MergeDiagraph= "
					+ (toMergeDiagraph == null ? "~" : toMergeDiagraph
							.getFacade1().getLanguageName()));
	}

	@Override
	public List<DGraph> getCurrentDiagraphs() {
		return currentDiagraphs;
	}

	@Override
	public EClass getCurrentRoot() {
		return currentRoot;
	}

	@Override
	public EClass getMergeRoot() {
		return toMergeRoot;
	}

	@Override
	public void setMergeRoot(EClass root) {
		this.toMergeRoot = root;
	}

	@Override
	public Diagram getMergeDiagram() {
		return toMergeDiagram;
	}


}
