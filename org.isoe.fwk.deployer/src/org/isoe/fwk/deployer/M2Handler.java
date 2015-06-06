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
package org.isoe.fwk.deployer;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.jar.JarFile;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.isoe.fwk.core.DParams;
import org.isoe.fwk.utils.FilesUtils;
import org.isoe.fwk.utils.workspace.RuntimeWorkspaceSetup;

/**
 *
 * @author fpfister
 *
 */
public class M2Handler {

	private static final String INTERFACE_TEMPLATE = "{0} public interface {1} '{ }'"; //FP150313
	private static final String CLASS_TEMPLATE = "{0} public class {1} implements {2} '{ }'";

	private static final boolean LOG = DParams.InstanceHandler_LOG;

	private String srcModelPath;
	private IProject project;
	private String language;
	private String basepackage;
	private String javaVersion;
	private RuntimeWorkspaceSetup runtimeWorkspaceSetup;
	private URI prjUri;
	private String modelFolder;
	private String suffix;
	private URI megamodelSource;
	private URI srcModelUri;

	public M2Handler(IProject project, URI megamodelSource, URI prjUri,
			String basepackage, String javaVersion) {
		this.megamodelSource = megamodelSource;
		this.prjUri = prjUri;
		this.project = project;
		this.basepackage = basepackage;
		this.javaVersion = javaVersion;
		this.runtimeWorkspaceSetup = RuntimeWorkspaceSetup
				.getInstance(javaVersion);
	}

	public void init(String mname, String modelFolder, String suffix) {
		this.language = mname;
		this.modelFolder = modelFolder;
		this.suffix = suffix;
		this.srcModelPath = "/" + basepackage + "." + mname + "/" + modelFolder
				+ "/" + mname + (suffix ==null?"":("." + suffix));
	}

	public void generateSnippet(ModelSnippet msnpet) throws Exception {
		genJavaCodeSnippet(msnpet.getaPackage(), msnpet.getClazs(),
				msnpet.getItfs(), msnpet.getAnotherPackage(),
				msnpet.getClaz2(), msnpet.getItf2());
	}

	protected void copyModel2(EObject tosave, String plugin, String filename,
			String fileExtension) {
		if (fileExtension == null)
			throw new RuntimeException("file extension is null for " + filename);
		ResourceSet rs = new ResourceSetImpl();
		String fileFolder = ResourcesPlugin.getWorkspace().getRoot()
				.getLocation().toString();
		String path = fileFolder + '/' + plugin + '/' + filename + '.'
				+ fileExtension;
		Resource r = rs.createResource(URI.createFileURI(path), "");
		r.getContents().add(tosave);
		try {
			r.save(null);
			System.out.println(path + " saved ok");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("error saving " + filename);
		}
	}

	private void clog(String mesg) {
		if (LOG)
			System.out.println(mesg);

	}

	protected boolean existModel_nu(String plugin, String filename,
			String fileExtension) {
		try {
			File f = new File(ResourcesPlugin.getWorkspace().getRoot()
					.getLocation().toString()
					+ '/' + plugin + '/' + filename + '.' + fileExtension);
			return f.exists();
		} catch (Exception e) {
			return false;
		}
	}

	public void restoreWorkspace_() {
		RuntimeWorkspaceSetup.releaseInstance();
	}

	private void genJavaCodeSnippet(String packag1, String[] classNames_,
			String[] interfaceNames, String packag2, String className2,
			String interfaceName2) {// GenModel domainGenModel) {

		IPackageFragmentRoot srcFolder = null;
		IFile manifestFile = null;
		try {
			IJavaProject javaProject_ = JavaCore.create(project);
			if (javaProject_ == null)
				throw new RuntimeException("project is not a java project");
			IPackageFragmentRoot[] roots = javaProject_
					.getPackageFragmentRoots();
			for (int i = 0; i < roots.length && srcFolder == null; i++)
				if (!roots[i].isReadOnly())
					srcFolder = roots[i];
			manifestFile = project.getFile(JarFile.MANIFEST_NAME);
			if (srcFolder == null)
				throw new RuntimeException(
						"Writable project root not found in the generated project");
			if (!(manifestFile != null && manifestFile.exists()))
				throw new RuntimeException("Manifest was not generated");
			runtimeWorkspaceSetup.getReadyToStartAsBundle(project);
			for (int k = 0; k < classNames_.length; k++) {
				generateClass(packag1, classNames_[k], interfaceNames[k],
						srcFolder);
				generateInterface(packag1, interfaceNames[k], srcFolder);
			}
			String[] exportedPackages = { packag1 };
			runtimeWorkspaceSetup.exportPackages(project, exportedPackages);
			generateClass(packag2, className2, packag1 + "."
					+ interfaceNames[0], srcFolder);
			generateInterface(packag2, interfaceName2, srcFolder);
			String[] exportedPackages2 = { packag2 };
			runtimeWorkspaceSetup.exportPackages(project, exportedPackages2);
			runtimeWorkspaceSetup.removeExportJava(project);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("error in" + "fixInstanceClasses "
					+ e.getMessage());
		}
	}

	private void generateClass(String packageName, String className,
			String interfaceName, IPackageFragmentRoot projectRoot) {
		if (packageName == null)
			packageName = "";
		String packagePrefix = packageName;
		try {
			IPackageFragment pkgFragment = projectRoot.createPackageFragment(
					packageName, true, new NullProgressMonitor());
			if (packagePrefix.length() > 0)
				packagePrefix = "package " + packagePrefix + ";";
			pkgFragment.createCompilationUnit(
					className + ".java",
					MessageFormat.format(CLASS_TEMPLATE, new Object[] {
							packagePrefix, className, interfaceName }), true,
					new NullProgressMonitor());
		} catch (JavaModelException e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	private void generateInterface(String packageName, String itfName,
			IPackageFragmentRoot projectRoot) {
		if (packageName == null)
			packageName = "";
		String packagePrefix = packageName;
		try {
			IPackageFragment pkgFragment = projectRoot.createPackageFragment(
					packageName, true, new NullProgressMonitor());
			if (packagePrefix.length() > 0)
				packagePrefix = "package " + packagePrefix + ";";
			pkgFragment.createCompilationUnit(
					itfName + ".java",
					MessageFormat.format(INTERFACE_TEMPLATE, new Object[] {
							packagePrefix, itfName }), true,
					new NullProgressMonitor());
		} catch (JavaModelException e) {
			throw new RuntimeException(e.getMessage());
		}
	}



	public void copyM2() {
		srcModelUri = URI.createPlatformPluginURI(srcModelPath, true);
		clog("copyM2 "+srcModelUri.toString());
		copyModel();
	}

	public void copyM1(String modelName) {
		srcModelUri = megamodelSource.appendSegment(modelName);
		language = modelName;
		clog("copyM1 "+srcModelUri.toString());
		copyModel();
	}


	private File getFile(URI uri) {
		File result = null;
		URI ruri = CommonPlugin.resolve(uri);
		if (ruri != null) {
			String s = ruri.toFileString();
			if (s != null)
				result = new File(s);
			if (result != null && !result.exists())
				result = null;
		}
	   return result;
	}


	private void copyModel() {
		File src = getFile(srcModelUri);
		if (src != null)
			copyToTarget(src);
		else
			clog("copyModel : nothing to copy ("+srcModelUri.toString()+")");
	}

	private void copyToTarget(File src) {
		URI targetmodeluri = prjUri.appendSegment(modelFolder);
		URI turi = CommonPlugin.resolve(targetmodeluri);
		if (turi != null) {
			String path = turi.toFileString();
			File f = new File(path);
			if (!f.exists())
				f.mkdir();
			clog(f.getAbsolutePath());
			File target = new File(f + File.separator + language
					+ (suffix == null ? "" : ("." + suffix)));
			try {
				FilesUtils.copyFile(src, target);
				clog("copying " + src.getAbsolutePath() + " to "
						+ target.getAbsolutePath());
			} catch (IOException e) {
				System.err.println("error copying " + src.getAbsolutePath()
						+ " to " + target.getAbsolutePath());
				e.printStackTrace();
			}
		}

	}

}
