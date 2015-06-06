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
package org.isoe.fwk.xmi.domparser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.isoe.fwk.core.DParams;
import org.isoe.extensionpoint.xmi.IMetamodelRetriever;

/**
 *
 * @author pfister
 *
 */
import org.isoe.diagraph.controler.IDiagraphControler;  //FP140707refactored
public class MetamodelRetriever implements IMetamodelRetriever {

	//org.isoe.fwk.xmi.domparser.MetamodelRetriever

	private static final boolean LOG = DParams.MetamodelRetriever_LOG;

	private static MetamodelRetriever instance;

	private ResourceSet resourceSet;
	private String domain;
	private String nsPrefix;
	private String nsURI;
	private String currentNsUri;
	private EPackage pakage;
	private String metamodelBase;
	private IFile currentFile;
	private XmiParser xmiParser;
	boolean interrupted=false; //TODO remonter au niveau de l'appellant

	@Override
	public String getDomain() {
		return domain;
	}

	@Override
	public String getNsURI() {
		return nsURI;
	}

	@Override
	public String getNsPrefix() {
		return nsPrefix;
	}

	@Override
	public IFile getCurrentFile() {
		return currentFile;
	}

	@Override
	public EPackage getCurrentPackage() {
		return pakage;
	}

	@Override
	public String getMetamodelBase() {
		return metamodelBase;
	}


	@Override
	public void setControler(IDiagraphControler controler) {
		this.controler = controler;
	}



	@Override
	public  EPackage findRegisteredMetamodel(IFile file) {
		java.net.URI filuri = file.getRawLocationURI();
		String filepath = filuri.toString().substring("file:/".length());
		if (LOG)
			clog(filuri.toString());
		IPath ip = file.getFullPath();
		if (LOG) {
			clog(ip.getClass().getName());
			clog(ip.toOSString());
		}
		return findRegisteredMetamodel(URI.createFileURI(filepath), new NullProgressMonitor());
	}

	private ResourceSet getResourceSet() {

		if (resourceSet == null) {
			resourceSet = new ResourceSetImpl();
			resourceSet
					.getResourceFactoryRegistry()
					.getExtensionToFactoryMap()
					.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
							new EcoreResourceFactoryImpl());

			resourceSet
			.getResourceFactoryRegistry()
			.getExtensionToFactoryMap()
			.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
					new XMIResourceFactoryImpl());

		}
		return resourceSet;
	}


	private EObject getRoot(EObject eObject) {
		EObject result = eObject.eContainer();
		if (result == null)
			return eObject;
		else
			return getRoot(result);
	}

	private String getDomain(EObject eobj) {
		URI muri = eobj.eResource().getURI();// TODO: retrieve from package
		String muris = muri.toString();
		return muris.substring(muris.lastIndexOf(".") + 1);
	}


	private void clog(String mesg) {
		if (LOG)
		  System.out.println(mesg);
	}

	private boolean languageChanges(String ns) {
		if (LOG) {
			if (this.currentNsUri == null || !this.currentNsUri.equals(ns))
				clog("ns-caze1 " + ns);
			else
				clog("ns-caze2 " + ns);
		}

		boolean result = (this.currentNsUri == null || !this.currentNsUri.equals(ns));
		this.currentNsUri = ns;
		return result;
	}

	/* to deprecate*/
	public static MetamodelRetriever getRetriever_nu() {
		if (instance == null)
			instance = new MetamodelRetriever();
		return instance;
	}

	public MetamodelRetriever() {
	}

	private void reset(){
		resourceSet = null;
		domain = null;
		nsPrefix = null;
		nsURI = null;
		currentNsUri = null;
		pakage = null;
		metamodelBase = null;
		currentFile = null;
		interrupted=false;
	}


	public XmiParser getXmiParser() {
		if (xmiParser == null)
			xmiParser =  XmiParser.getParser();
		return xmiParser;
	}


	private EPackage findRegisteredMetamodelForEObject(EObject root,IProgressMonitor progressMonitor) {
		try {
			URI muri = root.eResource().getURI();
			domain = getDomain(root);

			getXmiParser().parse(CommonPlugin.resolve(muri).toFileString());
			if (languageChanges(xmiParser.getNsUri())) //FP130520
				clog("L changed");
			nsPrefix = xmiParser.getNsPrefix();
			nsURI = xmiParser.getNsUri();
			domain = xmiParser.getName();
			if (domain == null)
				clog("domain==null");
			Resource rres = root.eResource();
			pakage = EPackage.Registry.INSTANCE.getEPackage(nsURI);
			domain = pakage.getName();
			EObject rootObject = pakage.eContents().get(0);
			clog(rootObject.toString());
			String roots = root.toString();
			metamodelBase = roots.substring(0, roots.indexOf(".impl.")); //TODO cleanup this hack please
			setCurrentFile(WorkspaceSynchronizer.getFile(rres));
		} catch (Exception e) {
			reset();
			throw new RuntimeException(e);
		}
		return pakage;
	}


	public void setCurrentFile(IFile ifile) {
		if (this.currentFile == null
				|| !ifile.getFullPath().toOSString()
						.equals(this.currentFile.getFullPath().toOSString())) {
			if (LOG) {
				String s2 = ifile.getFullPath().toOSString();
				clog("scf-1 " + s2);
			}
			this.currentFile = ifile;
		} else
			if (LOG)
			    clog("scf-2 " + ifile.getFullPath().toOSString());
	}


	private List<String> toMark=new ArrayList<String>();

	private boolean silent;

	private IDiagraphControler controler;

	private void sleep(int timeout) {
		if (Display.getCurrent() == null)
			return;
		final long start = System.currentTimeMillis();
		final long deltaMillis = timeout;
		do {
			while (Display.getCurrent().readAndDispatch()) {
				;
			}
		} while ((System.currentTimeMillis() - start) < deltaMillis);
	}

	@Override
	public void markUnregisteredModels(IProgressMonitor progressMonitor){
		for (String marked: toMark) {
			sleep(100);
			if (LOG)
				clog("markUnregisteredModels");
			progress(progressMonitor,"excluding "+marked);
			File fil = new File(marked);
			if (fil.exists()){ //FP130520
			  mark(fil);
			  String diagramPath = marked.substring(0,marked.lastIndexOf(File.separator))+(File.separator)+new File(marked).getName()+"_default_root_diagram";
			  progress(progressMonitor,"excluding "+diagramPath);
			  File diagramfil = new File(diagramPath);
			  if (diagramfil.exists())
			    mark(diagramfil);
			}
		}
		toMark.clear();
	}



	@Override
	public boolean isInterrupted() {
		return interrupted;
	}

	@Override
	public EPackage findRegisteredMetamodel(URI uri, IProgressMonitor progressMonitor) {
 		URI ruri =null;
		reset();
		try {
			ruri = CommonPlugin.resolve(uri);
			String fpath=ruri.toFileString();
			String model = fpath.substring(fpath.lastIndexOf(File.separator)+1);
			if(model.startsWith(DParams.REPOSITORY_FILTER_PREFIX_)){
				progress(progressMonitor,"no metamodel for "+model.substring(DParams.REPOSITORY_FILTER_PREFIX_.length()));
				return null;
			}
			Resource rsrc = null;
			try {
				//1) avoid presence of unregistered instances, this slows down the consolidation process
				//2) TODO: pass from LWB a list of registered metamodels, so there is not emf registry exploration
				//3) delete or mark the unknown models
				//4) Find a better method
				rsrc = getResourceSet().getResource(ruri, true);
			} catch (Exception e) {
				sleep(100);
				progress(progressMonitor,"marking "+model);
				clog(" no registered metamodel for "+model);
				toMark.add(fpath);
				return null;
			}
			EObject eobj = rsrc.getContents().get(0);
			progress(progressMonitor,"retrieving metamodel for "+model);
			EPackage result = findRegisteredMetamodelForEObject(eobj,progressMonitor);
			clog(" found registered metamodel:" + result.getName()+ " for " + model);
			System.out.print(".");
			return result;
		}

		catch (OperationCanceledException e) {
			String path=ruri.toFileString();
			path = path.substring(path.lastIndexOf(File.separator)+1);
			clog("interrupted while findRegisteredMetamodel "+path+" "+e.getClass().getName());
			interrupted=true; //TODO interrupted must be at the caller level
			return null;

		}
	}

/**
 * prefix metamodel-less with DParams.REPOSITORY_FILTER_PREFIX
 */
	private void mark(File fil) {
		try {
			//File fil = new File(filepath);
			//if (fil.exists()){
			  String filepath = fil.getAbsolutePath();
			  String targetPath = filepath.substring(0,filepath.lastIndexOf(File.separator))+File.separator+DParams.REPOSITORY_FILTER_PREFIX_+fil.getName();
			  File target = new File(targetPath);
			  if (target.exists())
				target.delete();
			 // int dot_=targetPath.lastIndexOf(".");
			  //String extension=targetPath.substring(dot);
			  //targetPath = targetPath.substring(0,dot);
			  if (!fil.renameTo(new File(targetPath)))
				clog("unable to rename file "+filepath);
			//} //else
			//	clog(filepath+" file does not exist");
		} catch (Exception e) {
			clog("unable to rename file "+e.toString());
		}
	}

	private void idle(){
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell()
					.getDisplay().readAndDispatch();
		} catch (Exception e) {
		}
	}

	private void progress(IProgressMonitor progressMonitor, String label) {
		//idle();
		if (progressMonitor.isCanceled()) {
			throw new OperationCanceledException("Operation Canceled");
		}
		progressMonitor.subTask(label);
		progressMonitor.worked(1);
         if (LOG)
			clog(label);
	}

	@Override
	public boolean isQualified() {
		return true;
	}

	@Override
	public boolean isStub() {
		return false;
	}

	@Override
	public void setSilent(boolean silent) {
		this.silent=silent;

	}








	/*******************  old hack **********************************/



	/*
	 *
	 *
	static final String EQUATS = "=\"";
	static final int EQUATS_LEN = EQUATS.length();
	static final String CLOSETAG1 = "\">";
	static final String CLOSETAG2 = "\"/>";
	static final String XSITYP = "<types xsi:type=";
	static final String XMLNSGMF = "xmlns:notation=\"http://www.eclipse.org/gmf";
	static final String SCHINST = "http://www.w3.org/2001/XMLSchema-instance";
	static final String XSISCHINST = "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance";
	static final int XSISCHINST_LEN = XSISCHINST.length();
	static final String XMLNS = "xmlns";
	static final String XMIORG = "xmlns:xmi=\"http://www.omg.org/XMI\"";
	static final int XMIORG_LEN = XMIORG.length();
private String parseNsPrefix_nu(String furi) {
		String ns = null;
		try {
			ns = "";
			FileInputStream fis = new FileInputStream(furi);
			DataInputStream dis = new DataInputStream(fis);
			BufferedReader buf = new BufferedReader(new InputStreamReader(dis));
			String line;
			while ((line = buf.readLine()) != null)
				ns += line;
			dis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ns = ns.substring(ns.indexOf("?><") + 3); // cleanup this hack please
		return ns.substring(0, ns.indexOf(":"));
	}



	private String getOneString__nu(String furi) { //FP130515
		String content = null;
		try {
			content = "";
			FileInputStream fis = new FileInputStream(furi);
			DataInputStream dis = new DataInputStream(fis);
			BufferedReader buf = new BufferedReader(new InputStreamReader(dis));
			String line;
			while ((line = buf.readLine()) != null)
				content += line;
			dis.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File error in parseNsURI1 "
					+ e.toString());
		} catch (IOException e) {
			throw new RuntimeException("File error in parseNsURI1 "
					+ e.toString());
		}
		return content;
	}


	private String parseNsURI1_nu(String furi) {
		String ns = getOneString__nu(furi);
		if (ns.contains(XMLNSGMF))
			throw new RuntimeException("error in parseNsURI1 "
					+ "not a domain model !");
		int p1 = ns.indexOf(XMIORG) + XMIORG_LEN;
		ns = ns.substring(p1);
		int p2 = ns.indexOf(CLOSETAG1);
		if (p2==-1)
		    p2 = ns.indexOf(CLOSETAG2); //total hack
		ns = ns.substring(0, p2).trim();

		if (ns.startsWith(XSISCHINST)) { // one possible format // FP130401
			ns = ns.substring(XSISCHINST_LEN + 1);
			ns = ns.trim().substring(XMLNS.length() + 1);
			ns = ns.substring(ns.indexOf(EQUATS) + EQUATS_LEN);
			return ns;
		} else { // another possible format
			int p3 = ns.indexOf(EQUATS) + 2;
			ns = ns.substring(p3);
			String result = ns.trim();
			if (result.startsWith(SCHINST))
				result = null;
			return result;
		}
	}

	private String parseNsURI2_nu(String furi) {
		String ns = getOneString__nu(furi);//FP130515


		if (ns.contains(XMLNSGMF))
			throw new RuntimeException("error in parseNsURI2 "
					+ "not a domain model !");
		ns = ns.substring(ns.indexOf(XSISCHINST) + XSISCHINST_LEN);
		ns = ns.substring(0, ns.indexOf(XSITYP));
		ns = ns.substring(ns.indexOf(EQUATS) + 2);
		int p=ns.indexOf(CLOSETAG1);
		if (p==-1)
			p=ns.indexOf(CLOSETAG2); //total hack

		ns = ns.substring(0, p);

		return ns.trim();
	}

	private String parseNsUri_nu(String furi) {
		String uri = parseNsURI1_nu(furi); // hack to be cleaned
		if (uri == null)
			uri = parseNsURI2_nu(furi); // hack to be cleaned
		if (uri.contains("<") || uri.contains(">") )
			 clog("problem while parsing "+furi);
		return uri;
	}


	private String getM2PhysicalPath(String plugin, String domain) {
		URI m2uri = URI.createPlatformPluginURI(plugin, false);
		String duri = CommonPlugin.resolve(m2uri).toString();
		duri += org.isoe.fwk.core.DParams.MODEL_FOLDER + "/" + domain+".ecore";
		if (duri.startsWith("file:/")) {
			duri = duri.substring("file:/".length());
			String fs = java.io.File.separator;
			if (fs.equals("\\"))
				fs = "\\\\";
			duri = duri.replaceAll("/", fs);
		}
		return duri;
	}


	 *
	 */






}
