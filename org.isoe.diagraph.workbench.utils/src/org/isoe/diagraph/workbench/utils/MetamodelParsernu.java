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
package org.isoe.diagraph.workbench.utils;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.isoe.diagraph.diagraph.DEdge;
import org.isoe.diagraph.diagraph.DGraph;
import org.isoe.diagraph.diagraph.DGraphElement;
import org.isoe.diagraph.diagraph.DLabeledEdge;
import org.isoe.diagraph.diagraph.DNavigationEdge;
import org.isoe.diagraph.diagraph.DNestedEdge;
import org.isoe.diagraph.diagraph.DNode;
import org.isoe.diagraph.diagraph.DOwnedEdge;
import org.isoe.diagraph.diagraph.DReference;
import org.isoe.diagraph.diagraph.DSimpleEdge;
import org.isoe.diagraph.diagraph.DiagraphFactory;
import org.isoe.diagraph.diagraph.helpers.IGraphHandler;
import org.isoe.diagraph.diastyle.DStyle;
import org.isoe.diagraph.factory.DGraphFactory;
import org.isoe.diagraph.generic.GenericConstants;
import org.isoe.diagraph.lang.DiagraphKeywords;
import org.isoe.diagraph.views.ViewConstants;
import org.isoe.diagraph.workbench.api.DiagraphNotifiable;
import org.isoe.extensionpoint.parsers.IDiagraphProvider;
import org.isoe.extensionpoint.parsers.IRuntimeDiagraphParser;
import org.isoe.diastyle.lang.StyleConstants;


/**
 *
 * @author pfister
 *
 */
import org.isoe.extensionpoint.diagraph.generator.IDiagraphGen;  //FP140707_b_refactored
public class MetamodelParsernu implements IRuntimeDiagraphParser {

	private static final boolean LOG = false;
	private List<EAnnotation> diagraphAnnotations = new ArrayList<EAnnotation>();
	private List<EAnnotation> allAnnotations = new ArrayList<EAnnotation>();
	private EPackage pakage;
	private IDiagraphGen diagraphRuntimeHandler;
	private DGraphElement currentGraphElement;
	private DNode current;
	private String views = "";
	private DGraph graph;
	private IDiagraphProvider diagraphProvider;
	private IGraphHandler genDiagraphM2;
	private DiagraphNotifiable owner;
   private String currentView;

	public MetamodelParsernu(IDiagraphGen diagraphRuntimeHandler) {
		super();
		this.diagraphRuntimeHandler = diagraphRuntimeHandler;
		//this.parser = parser;
	}


	@Override
	public String[] parseViews() {
		String s = "";
		for (EAnnotation eAnnotation : parseDiagraphAnnotations(pakage)) //FP140114
			for (Map.Entry<String, String> entry : eAnnotation.getDetails()) {
				if (entry.getKey().startsWith(ViewConstants.VIEW_EQ)) {
					String[] pars = entry.getKey().split("=");
					if (!s.contains(pars[1] + ";"))
						s += pars[1] + ";";
				}
			}
		if (s.isEmpty())
			s = ViewConstants.DIAGRAPH_DEFAULT_SEP;
		return s.split(";");
	}


	@Override
	public List<EAnnotation> parseDiagraphAnnotations(EPackage p) {
		List<EAnnotation> result = new ArrayList<EAnnotation>();
		for (EClassifier eClassifier : pakage.getEClassifiers())
			if (eClassifier instanceof EClass)
				for (EAnnotation annot : ((EClass) eClassifier)
						.getEAnnotations())
					if (annot.getSource().equals(
							GenericConstants.ANNOT_DIAGRAPH_LITTERAL))
						result.add(annot);
		return result;
	}


	private String[] getViews(boolean force) {// FP121014
		try {
			if (allAnnotations.size() == 0 || force)
				allAnnotations = parseDiagraphAnnotations(pakage); //FP140114
			parseViews(allAnnotations);
			return getViewIds();
		} catch (Exception e) {
			// throw new RuntimeException("parse error 3 !!!"); //FP121111
			clog("parse error 3c !!!"); // FP121111
			String[] result = new String[1];
			result[0] = DiagraphKeywords.DIAGRAPH_DEFAULT;
			return result; // FP121116
		}
	}



   void logDetails(EAnnotation eAnnotation){
      EMap<String, String> details = eAnnotation.getDetails();
      Set<String> keys = details.keySet();
      for (String key : keys)
         clog("MP_DET " + "key=" + key );//+ " value=" + details.get(key));
   }


   private void readDiagraphAnnotation(EPackage epakage) {
      // pakage = ep;
      EList<EClassifier> classes = epakage.getEClassifiers();
      for (EClassifier eClassifier : classes) {
         if (eClassifier instanceof EClass) {
            EClass eclaz = (EClass) eClassifier;
            clog("MP_EC " + eclaz.getName());
            eclaz.getEReferences();
            eclaz.getEAttributes();
            List<EAnnotation> eAnnots = eclaz.getEAnnotations();
            allAnnotations.addAll(eAnnots);
            for (EAnnotation eAnnotation : eAnnots) {
               String source = eAnnotation.getSource();
               if (source.equals(GenericConstants.ANNOT_DIAGRAPH_LITTERAL)){
                  diagraphAnnotations.add(eAnnotation);
                  if (LOG)
                     logDetails(eAnnotation);
               }/*
               EMap<String, String> details = eAnnotation.getDetails();
               Set<String> keys = details.keySet();
               for (String key : keys) {
                  clog("DRTP " + "key=" + key );//+ " value=" + details.get(key));
               }*/
            }
         }
      }
   }
	/*

	private void readDiagraphAnnotation(EPackage ep) {
		pakage = ep;
		EList<EClassifier> classes = ep.getEClassifiers();
		for (EClassifier eClassifier : classes) {
			if (eClassifier instanceof EClass) {
				EClass eclaz = (EClass) eClassifier;
				clog("DRTP "+eclaz.getName());
				eclaz.getEReferences();
				eclaz.getEAttributes();
				List<EAnnotation> eAnnots = eclaz.getEAnnotations();
				allAnnotations.addAll(eAnnots);
				for (EAnnotation eAnnotation : eAnnots) {
					String source = eAnnotation.getSource();
					if (source.equals(GenericConstants.ANNOT_DIAGRAPH_LITTERAL))
						diagraphAnnotations.add(eAnnotation);
					EMap<String, String> details = eAnnotation.getDetails();
					Set<String> keys = details.keySet();
					for (String key : keys) {
						clog("DRTP "+"key=" + key+" value=" + details.get(key));
					}
				}
			}
		}
	}*/

	private void save(DGraph dGraph, String view) {
		clog("DRTP "+"saving " + view);
		String path = diagraphRuntimeHandler.getMetamodelResourcepath();
		path = path.substring("platform:/resource/".length());
		path = path.substring(0, path.length() - ("ecore".length() + 1));
		path = path + "_blazo_.diagraph"; // todo adapter au nom de la vue +
											// _root :
											// _default_root etc...
		String projectName = path.substring(0, path.indexOf("/"));
		URI uri = URI.createPlatformResourceURI(path, true);
		URI urii = CommonPlugin.resolve(uri);
		Resource resource = new XMIResourceFactoryImpl().createResource(urii);
		resource.getContents().add(dGraph);
		try {
			resource.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//refresh(projectName);
		org.isoe.fwk.utils.debug.WorkspaceUtils.getInstance().refreshProject(projectName,true);
	}

	private static IProject getProject(String name) {
		IWorkspace ws = ResourcesPlugin.getWorkspace();
		org.eclipse.core.runtime.Path rootlocation = (Path) ws.getRoot()
				.getLocation();
		IProjectDescription description = null;
		IProject project = null;
		try {
			description = ResourcesPlugin.getWorkspace()
					.loadProjectDescription(
							new Path(rootlocation.toPortableString() + "/"
									+ name + "/.project"));
			project = ResourcesPlugin.getWorkspace().getRoot()
					.getProject(description.getName());
		} catch (CoreException e) {
			return null;
		}
		return project;
	}
/*
	private static void refresh(String projectName) {
		try {
			getProject(projectName).refreshLocal(IResource.DEPTH_INFINITE,
					new NullProgressMonitor());
			Job.getJobManager().join(ResourcesPlugin.FAMILY_MANUAL_REFRESH,
					null);
			Job.getJobManager().join(ResourcesPlugin.FAMILY_AUTO_REFRESH, null);
		} catch (Exception e) {
          System.out.print("error while refreshing project "+projectName);
		}
	}
*/
	boolean match(EAnnotation eAnnotation, String k) {
		for (String key : eAnnotation.getDetails().keySet())
			if (key.startsWith(k))
				return true;
		return false;
	}



	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);
	}



	@Override
	public DGraphElement parse(String from,Object o) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void parseEnd() {
		// TODO Auto-generated method stub

	}


	@Override
	public DGraph getGraph() {
		return graph;
	}


	@Override
	public DNode getCurrent() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int getPhase() {
		return diagraphProvider.getPhase();
	}


	   private List<DNode> getNodes(){ //FP140518

		   List<DNode> nodes = new ArrayList<DNode>();
		   List<DGraphElement> elements = graph.getElements();
		   for (DGraphElement element : elements) {
			 if(element instanceof DNode)
				 nodes.add((DNode) element);
		   }

		   return nodes;
	   }






	private List<DEdge> getEdges(DGraph graph) { // FP140518
		List<DEdge> result = new ArrayList<DEdge>();
		for (DGraphElement element : graph.getElements())
			if (element instanceof DEdge)
				result.add((DEdge) element);
		return result;
	}



	public List<DEdge> getEdges(DNode node) {
		List<DEdge> result = new ArrayList<DEdge>();
		for (DGraphElement element : node.getGraph().getElements()){
			if (element instanceof DNestedEdge){
				if (((DNestedEdge) element).getSource().getNode() == node)
					result.add((DEdge) element);
			} else if(element instanceof DSimpleEdge){
				 if (((DSimpleEdge) element).getSource() == node)
					result.add((DEdge) element); //FP150423b
			}
		}
		return result;
	}



	public IGraphHandler getGenDiagraphM2_nu() {
		if (genDiagraphM2 == null) {
			//genDiagraphM2 = new DGraphFactory(null,null);
			genDiagraphM2 = DGraphFactory.getInstance(null,null);//FP150601azer

		}
		return genDiagraphM2;
	}


	// réutiliser le code déjà existant
	public void generateConcreteSyntaxForAllViews(EPackage ep) {
		pakage = ep;
		getGenDiagraphM2_nu();
		readDiagraphAnnotation(ep);
		String[] viewsar = getViews(true);
		for (int i = 0; i < viewsar.length; i++) {
			graph = DiagraphFactory.eINSTANCE.createDGraph();
			graph.setViewName(viewsar[i]);
			for (EAnnotation eAnnotation : diagraphAnnotations) {
				EClass owner = (EClass) eAnnotation.eContainer();
				clog("DRTP "+owner.getClass().getName());
				if (match(eAnnotation, DiagraphKeywords.NODE)) {
					DNode nod = DiagraphFactory.eINSTANCE.createDNode();
					nod.setName(owner.getName());
					nod.setSemanticRole(owner); //FP130622
					graph.getElements().add(nod);
					nod.setGraph(graph);
				} else if (match(eAnnotation, DiagraphKeywords.LINK)) {
					DLabeledEdge link = DiagraphFactory.eINSTANCE.createDLabeledEdge();
					link.setName(owner.getName());
					link.setSemanticRole(owner);//FP130622
					// voir code déjà existant
					// nod.getEdges().add(link);
					// link.setSource(nod)
				}
				// TODO construire le modèle après analyse des annotations
			}
			save(graph, viewsar[i]);
		}

	}



	public DGraph transform(DStyle style) { // FP120628 //FP120702
		doTransform();
		convertM2(style);
		if (LOG)
			doTransform();
		graph = getGenDiagraph().getGraph();
		return graph;
	}


	private DNode getGenDiagraph() {
		// TODO Auto-generated method stub
		return null;
	}


	private void convertM2(DStyle style) {
		// TODO Auto-generated method stub

	}


	private void doTransform() {
		// TODO Auto-generated method stub

	}


	@Override
	public void parseStart() {
		// TODO Auto-generated method stub
	}


	@Override
	public String[] getViewIds() {
		if (views.isEmpty())
			views = ViewConstants.DIAGRAPH_DEFAULT_SEP;
		return views.split(";");
	}

	@Override
	public String[] parseViews(List<EAnnotation> annotations) {
		for (EAnnotation eAnnotation : annotations)
			for (Entry<String, String> entry : eAnnotation.getDetails()) {
				if (entry.getKey().startsWith(ViewConstants.VIEW_EQ)) {
					String[] pv = entry.getKey().split("=");
					if (!views.contains(pv[1] + ";"))
						views += pv[1] + ";";
				}
			}
		return views.split(";");
	}


	@Override
	public void begin(String id) { // FP121014
		graph = null;
		current = null;
		currentGraphElement = null;
		//state = null;
		views = ViewConstants.DIAGRAPH_DEFAULT_SEP;
		clog("id="+id);
	}




	@Override
	public boolean isStub() {
		return false;
	}


	@Override
	public boolean isFunctional() {
		return false;  //turn to true as soon as the implementation is ok
	}



	@Override
	public boolean isCertified() {
		return false;
	}


	@Override
	public void setGraph(DGraph dGraph) {
		// TODO Auto-generated method stub

	}

	@Override
	public void end() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(DiagraphNotifiable owner,
			IDiagraphProvider diagraphProvider) {
		this.diagraphProvider = diagraphProvider;
		this.owner = owner;
		//this.diagraphRuntimeHandler = diagraphGenerator;
	}


   @Override
   public void setCurrentView(String view) {
      this.currentView=view;
   }


@Override
public void checkAnnotations(String layer) {
	// TODO Auto-generated method stub

}



///////////// _nu /////////////////////
/*
private DNode getCurrentNode_nu(EClass eclaz){
	DGraph dGraph = diagraphProvider.getDiagraph();
	if (dGraph != null) {
		for (DNode dnod : getNodes()) { // FP120527
			if (dnod.getName().equals(eclaz.getName())) {
				currentGraphElement = dnod; // FP120615
				break;
			} else {
				for (DEdge dEdge : getEdges(dnod))
					if ((dEdge instanceof DOwnedEdge)
							&& dEdge.getName().equals(eclaz.getName())) {
						currentGraphElement = dEdge; // FP120615
						break;
					}
			}
		}
	}
	String layernameNull=null;
	String navLinkNull=null; //does not matter here
	List<String> labels=new ArrayList<String>();
	current = getGenDiagraphM2_nu().createNode(eclaz.getName(), labels,
			eclaz,  layernameNull, navLinkNull);
	return current;
}

*/

}
