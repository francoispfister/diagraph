package org.isoe.fwk.project.deployer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.IOverwriteQuery;
import org.eclipse.ui.internal.wizards.datatransfer.ZipLeveledStructureProvider;
import org.eclipse.ui.wizards.IWizardDescriptor;
import org.eclipse.ui.wizards.datatransfer.ImportOperation;
//import org.isoe.fwk.project.deployer.wizards.AbstractExampleWizard.ProjectDescriptor;

public class Temp {



	private void unzipProject(String bundleName, String zipLocation, String projectName,IProgressMonitor monitor) {


		URL interpreterZipUrl = FileLocator.find(Platform.getBundle(bundleName), new Path(zipLocation), null);

		clog("zipLocation: "+zipLocation+" interpreterZipUrl= "+(interpreterZipUrl==null?"null":interpreterZipUrl.toString()));

		if (interpreterZipUrl == null){
			 clog("failed");
			 return;
		}


		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);

		if (project.exists()) {
			return;
		}

		try {
			// We make sure that the project is created from this point forward.
			project.create(monitor);

			ZipInputStream zipFileStream = new ZipInputStream(interpreterZipUrl.openStream());
			ZipEntry zipEntry = zipFileStream.getNextEntry();

			// We derive a regexedProjectName so that the dots don't end up being
			//  interpreted as the dot operator in the regular expression language.
			String regexedProjectName = projectName.replaceAll("\\.", "\\."); //$NON-NLS-1$ //$NON-NLS-2$

			while (zipEntry != null) {
				// We will construct the new file but we will strip off the project
				//  directory from the beginning of the path because we have already
				//  created the destination project for this zip.



				File file = new File(project.getLocation().toString(), zipEntry.getName().replaceFirst("^"+regexedProjectName+"/", ""));   //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$

				if (false == zipEntry.isDirectory()) {

					/*
					 * Copy files (and make sure parent directory exist)
					 */
					File parentFile = file.getParentFile();
					if (null != parentFile && false == parentFile.exists()) {
						parentFile.mkdirs();
					}
						OutputStream os = null;

						try {
							os = new FileOutputStream(file);

							byte[] buffer = new byte[102400];
							while (true) {
								int len = zipFileStream.read(buffer);
								if (zipFileStream.available() == 0)
									break;
								os.write(buffer, 0, len);
							}
						} finally {
							if (null != os) {
								os.close();
							}
						}
				}

				zipFileStream.closeEntry();
				zipEntry = zipFileStream.getNextEntry();
			}

			project.open(monitor);
			// in order to make sure the project natures are correctly identified close and reopen the project
			project.close(monitor);
			project.open(monitor);
			project.refreshLocal(IFile.DEPTH_INFINITE, monitor);
		} catch (IOException e) {
			//getContainerPlugin().getLog().log(new Status(IStatus.ERROR, getContainerPlugin().getBundle().getSymbolicName(),IStatus.ERROR, e.getMessage(),e));
		} catch (CoreException e) {
			//getContainerPlugin().getLog().log(e.getStatus());
		}
	}


	private void clog(String string) {
		// TODO Auto-generated method stub

	}


	void importProjectf(IWorkspace workspace, String projectName) throws CoreException, IOException, InvocationTargetException, InterruptedException{
		//IWorkspace workspace;// = this.project.getWorkspace();
		IProjectDescription newProjectDescription = workspace.newProjectDescription(projectName);
		IProject newProject = workspace.getRoot().getProject(projectName);
		newProject.create(newProjectDescription, null);
		newProject.open(null);

		ZipFile zipFile = new ZipFile(workspace.getRoot().getLocation() + "/" + projectName + ".zip");
		IOverwriteQuery overwriteQuery = new IOverwriteQuery() {
		    public String queryOverwrite(String file) { return ALL; }
		};
		ZipLeveledStructureProvider provider = new ZipLeveledStructureProvider(zipFile);
		List<Object> fileSystemObjects = new ArrayList<Object>();
		Enumeration<? extends ZipEntry> entries = zipFile.entries();
		while (entries.hasMoreElements()) {
		    fileSystemObjects.add((Object)entries.nextElement());
		}
		ImportOperation importOperation = new ImportOperation(newProject.getFullPath(), new ZipEntry(projectName), provider, overwriteQuery, fileSystemObjects);
		importOperation.setCreateContainerStructure(false);
		importOperation.run(new NullProgressMonitor());
	}



	public  void openWizard(String id,Shell parentShell) {
		 // First see if this is a "new wizard".
		 IWizardDescriptor descriptor = PlatformUI.getWorkbench()
		   .getNewWizardRegistry().findWizard(id);
		 // If not check if it is an "import wizard".
		 if  (descriptor == null) {
		   descriptor = PlatformUI.getWorkbench().getImportWizardRegistry()
		   .findWizard(id);
		 }
		 // Or maybe an export wizard
		 if  (descriptor == null) {
		   descriptor = PlatformUI.getWorkbench().getExportWizardRegistry()
		   .findWizard(id);
		 }
		 try  {
		   // Then if we have a wizard, open it.
		   if  (descriptor != null) {
		     IWizard wizard = descriptor.createWizard();
		     WizardDialog wd = new  WizardDialog(parentShell, wizard);
		     wd.setTitle(wizard.getWindowTitle());
		     wd.open();

		    // wizard.


		    // wizard.performFinish();


		   }
		 } catch  (CoreException e) {
		   e.printStackTrace();
		 }
		}

}
