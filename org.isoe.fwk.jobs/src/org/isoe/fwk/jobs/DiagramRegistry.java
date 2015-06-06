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
package org.isoe.fwk.jobs;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.internal.resources.Workspace;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecoretools.legacy.diagram.edit.parts.EPackageEditPart;
import org.eclipse.emf.ecoretools.legacy.diagram.part.EcoreDiagramEditorPlugin;
import org.eclipse.emf.ecoretools.legacy.diagram.part.EcoreDiagramEditorUtil;
import org.eclipse.emf.ecoretools.legacy.diagram.part.EcoreVisualIDRegistry;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.core.GMFEditingDomainFactory;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.ui.PartInitException;
import org.eclipse.emf.ecoretools.legacy.diagram.part.Messages;

/**
 * Contains a map of all Diagraph diagrams. We can use this to open up arbitrary
 * Diagraph diagram images (e.g. <code>.Diagraph_diagram</code> or
 * <code>.xxx_operation_diagram</code>) without having to refer to the diagram
 * code directly.
 *
 * <p>
 * TODO unless this is used by end-user code, this should be moved into a
 * development-only plugin; currently it is only used by modeldoc and diagram
 * tests.
 *
 * borrowed from http://iaml.googlecode.com
 *
 * @author jmwright
 * @author fpfister (adaptation to diagraph)
 *
 */
public class DiagramRegistry {

   /**
    * Contains all of the necessary information for a given diagram editor.
    *
    */
   private abstract static class DiagramRegistryOptions {

      public String modelId;
      public int visualId;
      public PreferencesHint prefHint;
      public Map<?, ?> saveOptions;
      public String initMessage;
      public String errorMessage;

      public DiagramRegistryOptions(String modelId, int visualId,
            PreferencesHint prefHint, Map<?, ?> saveOptions,
            String initMessage, String errorMessage) {
         this.modelId = modelId;
         this.visualId = visualId;
         this.prefHint = prefHint;
         this.saveOptions = saveOptions;
         this.initMessage = initMessage;
         this.errorMessage = errorMessage;
      }



	public abstract int getDiagramVisualID(IFile modelFile, EObject modelRoot);
    public abstract boolean openDiagram_(Resource diagramResource);//throws PartInitException;



   }

   private static final boolean LOG = false;

   /**
    * Initialise all of the editors.
    *
    * @return
    */
   protected static Map<String, DiagramRegistryOptions> getDiagramRegistry() {
      Map<String, DiagramRegistryOptions> diagramRegistry = new HashMap<String, DiagramRegistryOptions>();

      diagramRegistry.put("ecorediag", new DiagramRegistryOptions(
            EPackageEditPart.MODEL_ID, EPackageEditPart.VISUAL_ID,
            EcoreDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT,
            EcoreDiagramEditorUtil.getSaveOptions(),
            Messages.EcoreNewDiagramFileWizard_InitDiagramCommand,
            Messages.EcoreNewDiagramFileWizard_IncorrectRootError) {

         @Override
         public boolean openDiagram_(Resource diagramResource){ //FP131124
            try {
               return EcoreDiagramEditorUtil.openDiagram(diagramResource);
             //  return true;
            } catch (Exception e) {//FP131124 //PartInitException e
               System.err.println("exception openDiagram: "+e.toString()+" in diagramregistry");

               //e.printStackTrace();
            }
            return false;
         }

         @Override
         public int getDiagramVisualID(IFile modelFile, EObject modelRoot) {
            return EcoreVisualIDRegistry.getDiagramVisualID(modelRoot);
         }

      });


      return diagramRegistry;
   }

   protected static void clog(String mesg) {
      if (LOG)
         System.out.println(mesg);

   }

   /**
    * Initialise a model file from a source file. The
    * {@link IFile#getFileExtension() model file extension} is used to select
    * the appropriate editor.
    *
    * @see #getDiagramRegistry()
    * @see IFile#getFileExtension()
    * @param modelFile
    *           must exist
    * @param diagramFile
    *           must not exist yet
    * @param open
    *           should the new diagram file be opened?
    * @throws DiagramRegistryException
    *            if no editor could be found for the given file
    * @throws IOException
    * @throws ExecutionException
    * @throws PartInitException
    */
   public static void initializeModelFile(IFile modelFile, Diagram changed,IFile diagramFile,String diagramFileExtension,
         boolean create, boolean open) throws DiagramRegistryException,
         PartInitException, ExecutionException, IOException {

      // find the appropriate editor
      DiagramRegistryOptions opt_ = getDiagramRegistry().get(
            diagramFileExtension);
      if (opt_ == null)
         throw new DiagramRegistryException(
               "Could not find editor for file extension: "
                     + modelFile.getFileExtension());
      Resource diagramResource_ = null;
      if (create){
         diagramResource_ = createDiagramTodo(modelFile, diagramFile, diagramFileExtension, opt_);
      }else {
         diagramResource_ = diagramResource(modelFile, diagramFile, opt_);
      }

      if (open) {
            if (!opt_.openDiagram_(diagramResource_))
        	   System.err.println("unable to open diagram "+diagramFile.toString()); //FP131124
      }

   }

   private static Resource diagramResource(final IFile modelFile_,
         final IFile diagramFile_, final DiagramRegistryOptions options) {
      if (!modelFile_.exists())
         throw new IllegalArgumentException("Model file " + modelFile_
               + " does not exist");
      if (!diagramFile_.exists())
         throw new IllegalArgumentException("Diagram file " + diagramFile_
               + " does not exist");
      ResourceSet resourceSet = new ResourceSetImpl();
      URI diagramModelURI = URI.createPlatformResourceURI(diagramFile_
            .getFullPath().toString(), false);
      final Resource diagramResource = resourceSet
            .createResource(diagramModelURI);
      return diagramResource;
   }




   /**
    * Initialise the diagram file, and return the loaded resource (before it is
    * opened). Does not open the diagram file.
    */
   protected static Resource createDiagramTodo(final IFile modelFile,
         final IFile diagramFile_, final String diagramFileExtension, final DiagramRegistryOptions options)
         throws DiagramRegistryException, ExecutionException, IOException,
         PartInitException {

      if (!modelFile.exists())
         throw new IllegalArgumentException("Model file " + modelFile
               + " does not exist");
      if (diagramFile_!=null)
         throw new IllegalArgumentException("Diagram file " + diagramFile_
               + " should not exist yet");


      IFolder fo = modelFile.getProject().getFolder(org.isoe.fwk.core.DParams.MODEL_FOLDER);

      String fname=modelFile.getName();
      fname = fname.substring(0,fname.length()-".ecore".length());
      URI ri = URI.createURI("platform:/resource/"
            + fo.getFullPath().toPortableString()+"/"+fname+"."+diagramFileExtension);
      File diagramFil = new File(CommonPlugin.resolve(ri).toFileString());
      IPath path=null;
      Workspace container=null;
      IFile diagramFile = null;//new org.eclipse.core.internal.resources.File(path,container);

      // initialize model file
      // based on generated IamlNewDiagramFileWizard#performFinish
      // based on generated IamlInitDiagramFileAction#run
      ResourceSet resourceSet = new ResourceSetImpl();
      URI sourceModelURI = URI.createPlatformResourceURI(modelFile
            .getFullPath().toString(), false);
      URI diagramModelURI = URI.createPlatformResourceURI(diagramFile.getLocation().toPortableString(), false);
      final Resource modelResource = resourceSet.getResource(sourceModelURI,
            true);
      final Resource diagramResource = resourceSet
            .createResource(diagramModelURI);
      final EObject modelRoot = (EObject) modelResource.getContents().get(0);

      List<Object> affectedFiles = new LinkedList<Object>();
      affectedFiles.add(diagramFile);
      TransactionalEditingDomain myEditingDomain = GMFEditingDomainFactory.INSTANCE
            .createEditingDomain();

      AbstractTransactionalCommand command = new AbstractTransactionalCommand(
            myEditingDomain, options.initMessage, affectedFiles) {

         @Override
         protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
               IAdaptable info) throws ExecutionException {
            int diagramVID = options.getDiagramVisualID(modelFile, modelRoot);
            if (diagramVID != options.visualId) {
               return CommandResult.newErrorCommandResult(options.errorMessage);
            }
            Diagram diagram = ViewService.createDiagram(modelRoot,
                  options.modelId, options.prefHint);
            diagramResource.getContents().add(diagram);
            return CommandResult.newOKCommandResult();
         }
      };
      OperationHistoryFactory.getOperationHistory().execute(command,
            new NullProgressMonitor(), null);
      diagramResource.save(options.saveOptions);
      return diagramResource;

   }

   public static class DiagramRegistryException extends Exception {

      public DiagramRegistryException(String string) {
         super(string);
      }

      private static final long serialVersionUID = 1L;

   }

}
