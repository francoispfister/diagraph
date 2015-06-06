package org.isoe.diagraph.workbench.launcher;



import static org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants.ATTR_CLASSPATH;
import static org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS;
import static org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants.ATTR_VM_ARGUMENTS;
import static org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants.ATTR_DEFAULT_CLASSPATH;
import static org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME;
import static org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants.ATTR_WORKING_DIRECTORY;
import static org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants.ID_JAVA_APPLICATION;
import static org.eclipse.jdt.launching.IRuntimeClasspathEntry.USER_CLASSES;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.ILaunchesListener;
import org.eclipse.debug.core.ILaunchesListener2;
import org.eclipse.debug.core.IStatusHandler;
import org.eclipse.debug.core.IStreamListener;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IStreamMonitor;
import org.eclipse.debug.core.model.IStreamsProxy;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.debug.ui.ILaunchGroup;
import org.eclipse.jdt.launching.IRuntimeClasspathEntry;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IPathEditorInput;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.progress.WorkbenchJob;
import org.eclipse.ant.core.AntRunner;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.launching.IVMRunner;
import org.eclipse.jdt.launching.VMRunnerConfiguration;
import org.eclipse.debug.core.Launch;

import java.io.PrintStream; 


public class RunAction implements IWorkbenchWindowActionDelegate {
  private IWorkbenchWindow window;

  public void dispose() {
  }

  /**
   * We will cache window object in order to
   * be able to provide parent shell for the message dialog.
   * @see IWorkbenchWindowActionDelegate#init
   */
  public void init(IWorkbenchWindow window) {
    this.window = window;
  }

  public void selectionChanged(IAction action, ISelection selection) {
  }

  public void run(IAction action) {
//  System.err.println("running");
//  runByJDT(action);
//    runByProcessBuilder(action);
    runByLaunchConfiguration(window, action);
//  System.err.println("running end");
  }

  /* for Ant Test */
  /* Caveat: it is impossible to redirect stdin/stdout. */
  public void runByAnt(IAction action) {
    IPathEditorInput input = (IPathEditorInput)window.getActivePage().getActiveEditor().getEditorInput();
    IPath ipath = input.getPath();    
   String path = ipath.toString();
   String last = ipath.lastSegment();
   String classpath = path.substring(0, path.length()-last.length()-1);
   try {
     System.out.println("running");      
     AntRunner runner = new AntRunner();
     runner.setBuildFileLocation(classpath+"/build.xml");
  	 runner.setArguments("-Dmessage=Building -verbose");
  	//runner.run(monitor);
      runner.run();
      System.out.println("run");
    } catch (CoreException e) {
      e.printStackTrace(System.err);
    }
  }

  public void runByJDT(IAction action) {
    IPathEditorInput input = (IPathEditorInput)window.getActivePage().getActiveEditor().getEditorInput();
    IPath ipath = input.getPath();    
    String path = ipath.toString();
    String last = ipath.lastSegment();
    String classpath = path.substring(0, path.length()-last.length()-1);
    String classname = last.substring(0, last.length()-ipath.getFileExtension().length()-1);

    IVMInstall vmInstall =  JavaRuntime.getDefaultVMInstall();
    if (vmInstall != null) {
    	org.eclipse.jdt.launching.IVMRunner vmRunner = vmInstall.getVMRunner(ILaunchManager.RUN_MODE);
      if (vmRunner != null) {
    	  VMRunnerConfiguration vmConfig = 
          new VMRunnerConfiguration(classname, new String[] {"rt.jar", classpath});
//      vmConfig.setProgramArguments(new String[]{});
        ILaunch launch = new org.eclipse.debug.core.Launch(null, ILaunchManager.RUN_MODE, null);
        
    	try {	
        	//org.eclipse.ui.part.ViewPart v=window.getActivePage().findView("");
        	 //org.eclipse.ui.IViewPart vp=window.getActivePage().findView("");
           //  final OutputView view =(OutputView)window.getActivePage().findView(OutputView.VIEW_ID);
             final org.eclipse.ui.IViewPart view =window.getActivePage().findView("OutputView.VIEW_ID");
          String outfilename;
	
			outfilename = (String)(((IFile)input.getAdapter(IFile.class)).getSessionProperty(new QualifiedName(null, "output")));
	
          File outfile = new File(classpath, outfilename);

          final PrintStream os = new PrintStream(outfile);
         // view.pout = os;
          
          vmRunner.run(vmConfig, launch, new NullProgressMonitor(){
               @Override
               public void done() {
                 System.err.println("done");
                 os.close(); 
               }
             });
          
        
          
        DebugPlugin.getDefault().getLaunchManager().addLaunchListener(new ILaunchesListener2() {
      public void launchesTerminated(ILaunch[] launches) {
      System.err.println("terminated");
      os.close();
      }
      public void launchesAdded(ILaunch[] launches) {}
      public void launchesChanged(ILaunch[] launches) {}
      public void launchesRemoved(ILaunch[] launches) {}
      });
        IProcess process = launch.getProcesses()[0];
        final IStreamsProxy proxy = process.getStreamsProxy();
        final WorkbenchJob revealJob = getRevealJob(view);

        
 
         //copy text from the running process to IOConsole
        proxy.getOutputStreamMonitor().addListener(new IStreamListener(){
        	@Override
          public void streamAppended(String text, IStreamMonitor mon) {
            try {
              //view.out.write(text);
              os.print(text);   
              revealJob.schedule(100);
            } catch (Exception e) {
              e.printStackTrace(System.err);
            }
          }

	
        });
        // copy text from IOConsole to the running process
        (new Thread (new Runnable() {
          byte[] b = new byte[1];
          public void run() {
            try {
              while(true) {
                int count = 0;//view.in.read(b);
                // IOConsoleInputStream#read() seems to have a bug.
                if (count==-1) break;
                proxy.write(new String(b));
              }
            } catch (IOException e) {}
          }
        })).start();

         
          
          
          
          
          
 
    	} catch (CoreException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}  
          
      }
    }
  }
     
      



  private static WorkbenchJob getRevealJob(final IViewPart view) {
    final WorkbenchJob revealJob = new WorkbenchJob("Reveal End of Document") {//$NON-NLS-1$
      /* 
       * moves the caret to the last position of the text
       * copied from org.eclipse.ui.console.TextConsoleViewer  
       */
      public IStatus runInUIThread(IProgressMonitor monitor) {
    	  /*
        StyledText textWidget = view.viewer.getTextWidget();
        if (textWidget != null) {
          int charCount = textWidget.getCharCount();
          textWidget.setSelection(charCount);
        } */
        return Status.OK_STATUS;
      }
    };
    return revealJob;
  }


//  public void runByProcessBuilder(IAction action) {
//    String java = getJavaPath();
////  Process p = new ProcessBuilder(java)
//    IPathEditorInput input = (IPathEditorInput)window.getActivePage().getActiveEditor().getEditorInput();
//    IPath ipath = input.getPath();    
//    String path = ipath.toString();
//    String last = ipath.lastSegment();
//    String classpath = path.substring(0, path.length()-last.length()-1);
//    String classname = last.substring(0, last.length()-ipath.getFileExtension().length()-1);
//    final OutputView view = (OutputView)window.getActivePage().findView(OutputView.VIEW_ID);
//    ProcessBuilder pb = new ProcessBuilder(java, "-classpath", classpath, classname);
//
//    try {  
//      final Process p = pb.start();
//
//      InputStream in = p.getInputStream();
//      final OutputStream out = p.getOutputStream();
//      InputStream err = p.getErrorStream();
//      String outfilename = (String)(((IFile)input.getAdapter(IFile.class)).getSessionProperty(new QualifiedName(null, "output")));
//      File outfile = new File(classpath, outfilename);
//      final PrintStream os = new PrintStream(outfile);
//      view.pout = out;
//
//      WorkbenchJob revealJob = getRevealJob(view);   
//      new Thread(new StreamConnectorS(view.in, out)).start();
//      new Thread(new StreamConnector2(in, view.out, os, revealJob)).start();
//      new Thread(new StreamConnector(err, view.out, revealJob)).start();
//      new Thread(new Runnable() {
//        public void run() {
//          // os‚ÌŒãŽn––
//          while(true) {
//            try {
//              p.waitFor();
//              break;
//            } catch (InterruptedException e) {}
//          }
//          os.close();
//        }
//      }).start();
//
//    } catch (FileNotFoundException e) {
//      e.printStackTrace();
//    } catch (CoreException e) {
//      e.printStackTrace();
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//  }

//  private static String getJavaPath() {
//    String javahome = System.getProperty("java.home");
//    String sep      = System.getProperty("file.separator");
//    return String.format("%s%sbin%sjava", javahome, sep, sep);
//  }


@SuppressWarnings("unchecked")
  public static void runByLaunchConfiguration(IWorkbenchWindow window, IAction action) {
    // see ``Launching Java Applications Programmatically''

    ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
    ILaunchConfigurationType type =
      manager.getLaunchConfigurationType(ID_JAVA_APPLICATION);
    ILaunchConfiguration[] configurations;
    try {
      configurations = manager.getLaunchConfigurations(type);

      for (ILaunchConfiguration configuration : configurations) {
        if (configuration.getName().equals("Start Java Program")) {
          configuration.delete();
          break;
        }
      }
  
      IPathEditorInput input = (IPathEditorInput)window.getActivePage().getActiveEditor().getEditorInput();
      final IProject project = ((IFile)input.getAdapter(IFile.class)).getProject();
      final String outfilename = (String)(project.getSessionProperty(new QualifiedName(null, "output")));
      String policyfilename = (String)(project.getSessionProperty(new QualifiedName(null, "policy")));
      ArrayList<IFile> jars = (ArrayList<IFile>)project.getSessionProperty(new QualifiedName(null, "__jars"));   
      ILaunchConfigurationWorkingCopy workingCopy = type.newInstance(null, "Start Java Program");
      
//      String classname = ipath.removeFileExtension().lastSegment().toString();
      IPath rootDir = project.getLocation();
//      System.err.printf("rootDir=%s%n", rootDir);
      String classname = input.getPath().removeFileExtension().toString().substring(rootDir.toString().length()+1).replace('/', '.');
//      System.err.printf("classname=%s%n", input.getPath());
      workingCopy.setAttribute(ATTR_MAIN_TYPE_NAME, classname);
      
      ArrayList<String> classpath = new ArrayList<String>();
      
//      IVMInstall jre = JavaRuntime.getDefaultVMInstall();                 
//      File jdkHome = jre.getInstallLocation();
//      IPath toolsPath = new Path(jdkHome.getAbsolutePath()).append("lib").append("tools.jar");
//      IRuntimeClasspathEntry toolsEntry = JavaRuntime.newArchiveRuntimeClasspathEntry(toolsPath);
//      toolsEntry.setClasspathProperty(USER_CLASSES);
//      classpath.add(toolsEntry.getMemento());
      
//      IPath systemLibsPath = new Path(JavaRuntime.JRE_CONTAINER);
//      IRuntimeClasspathEntry systemLibsEntry = JavaRuntime.newRuntimeContainerClasspathEntry(systemLibsPath, STANDARD_CLASSES);
//      classpath.add(systemLibsEntry.getMemento());
     
      for(IFile jar : jars) {
        IPath jarPath = jar.getFullPath();
        IRuntimeClasspathEntry jarEntry = JavaRuntime.newArchiveRuntimeClasspathEntry(jarPath);
        jarEntry.setClasspathProperty(USER_CLASSES);
        classpath.add(jarEntry.getMemento());
      }
      
//      IPath currentPath = ipath.removeLastSegments(1);
      IPath currentPath = rootDir;
      IRuntimeClasspathEntry currentDirEntry = JavaRuntime.newArchiveRuntimeClasspathEntry(currentPath);
      classpath.add(currentDirEntry.getMemento());
  
      workingCopy.setAttribute(ATTR_CLASSPATH, classpath);
      workingCopy.setAttribute(ATTR_DEFAULT_CLASSPATH, false);
      
      workingCopy.setAttribute(ATTR_WORKING_DIRECTORY, rootDir.toString());
      workingCopy.setAttribute(IDebugUIConstants.ATTR_PRIVATE, true);
      if(outfilename!=null) {
    	  workingCopy.setAttribute(IDebugUIConstants.ATTR_CAPTURE_IN_FILE, project.getFile(outfilename).getLocation().toString());
    	  workingCopy.setAttribute(IDebugUIConstants.ATTR_APPEND_TO_FILE, false);
      }
      if(policyfilename!=null) {
    	  workingCopy.setAttribute(ATTR_VM_ARGUMENTS, String.format("-Djava.security.manager -Djava.security.policy=%s", policyfilename));
      }
      String args = (String)project.getSessionProperty(new QualifiedName(null, "__args"));
      if (args!=null) {
    	  workingCopy.setAttribute(ATTR_PROGRAM_ARGUMENTS, args);
      }
      
      final ILaunchConfiguration configuration = workingCopy.doSave();
      DebugUITools.launch(configuration, ILaunchManager.RUN_MODE);
      // TODO refresh project after ...
      
//      // ‚È‚º‚©•K—v
//      try {
//    	  Class.forName("org.eclipse.debug.ui.DebugUITools");
//      } catch (ClassNotFoundException e1) {
//      }
//      // The following code is mostly copied from org.eclipse.debug.internal.ui.DebugUIPlugin#launchInBackground.
//      final String mode = ILaunchManager.RUN_MODE;
//      Job job = new Job("Launching") { 
//    	  public IStatus run(final IProgressMonitor monitor) {
//    		  try { 
//    			  if (!monitor.isCanceled()) {
//    				  ILaunch launch = DebugUITools.buildAndLaunch(configuration, mode, monitor); 
//    			  }
//    		  } catch (CoreException e) {
//    			  final IStatus status= e.getStatus();
//    			  IStatusHandler handler = DebugPlugin.getDefault().getStatusHandler(status);
//    			  if (handler == null) {
//    				  return status;
//    			  }
//    			  final ILaunchGroup group = DebugUITools.getLaunchGroup(configuration, mode);
//    			  if (group == null) {
//    				  return status;
//    			  }
//    			  Runnable r = new Runnable() {
//    				  public void run() {
//    					  DebugUITools.openLaunchConfigurationDialogOnGroup(DebugUIPlugin.getShell(), new StructuredSelection(configuration), group.getIdentifier(), status);
//    				  }       
//    			  };
//    			  DebugUIPlugin.getStandardDisplay().asyncExec(r);
//    		  }
//    		  return Status.OK_STATUS;
//    	  }
//      };
//
//      job.addJobChangeListener(new JobChangeAdapter() {
//    	  @Override
//    	  public void done(IJobChangeEvent event) {
//    		  try {
//    			  IFile out = project.getFile(outfilename);
//    			  out.touch(null);
//    			  project.refreshLocal(IResource.DEPTH_INFINITE, null);				
//    		  } catch (CoreException e) {
//    		  }
//    	  }
//      });
//      job.setPriority(Job.INTERACTIVE);
//      job.setName("Launching Java Program");     
//      job.schedule();
    } catch (CoreException e) {
      e.printStackTrace();
    }

  }

}

